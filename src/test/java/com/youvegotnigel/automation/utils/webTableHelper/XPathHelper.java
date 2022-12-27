package com.youvegotnigel.automation.utils.webTableHelper;

import org.apache.commons.lang.StringUtils;

public class XPathHelper {

    public enum CompareOptions {
        EQUALS, NOT_EQUAL, STARTS_WITH, ENDS_WITH, CONTAINS
    }

    public static String makeTextComparisonXPath(String attribute, String value, CompareOptions compareOptions, boolean caseSensitive) {

        String xpath = "";
        String originalText = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00A0";
        String replacement = "abcdefghijklmnopqrstuvwxyz";
        String compareValue = StringUtils.normalizeSpace(value);

        String compareThing = "normalize-space(" + attribute + ")";

        if(!caseSensitive){
            compareValue = compareValue.toLowerCase();
            compareThing = String.format("translate(%s, '%s', '%s')", compareThing, originalText, replacement);
        }

        compareValue = String.format("'%s'", compareValue);

        switch(compareOptions){
            case EQUALS:
                xpath = String.format("%s = %s", compareThing, compareValue);
                break;

            case STARTS_WITH:
                xpath = String.format("starts-with(%s, %s)", compareThing, compareValue);
                break;

            case ENDS_WITH:
                xpath = String.format("substring(%s, string-length(%s) - string-length(%s) + 1) = %s", compareThing, compareThing, compareValue, compareValue);
                break;

            case CONTAINS:
                xpath = String.format("contains(%s, %s)", compareThing, compareValue);
                break;

            case NOT_EQUAL:
                xpath = String.format("not(%s = %s)", compareThing, compareValue);
                break;
        }

        return xpath;
    }


}
