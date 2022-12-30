package com.youvegotnigel.automation.sample_tests;

import com.youvegotnigel.automation.utils.webTableHelper.XPathHelper;
import org.testng.annotations.Test;

/**
 * This is a Test Class to verify generic methods are working as expected
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class XpathHelperTest extends XPathHelper {

    @Test
    public static void testXpath(){

        String xpath1 = XPathHelper.makeTextComparisonXPath(".", "Oranges", CompareOptions.EQUALS, false);
        String xpath2 = XPathHelper.makeTextComparisonXPath(".", "Pakis", CompareOptions.CONTAINS, false);
        String xpath3 = XPathHelper.makeTextComparisonXPath(".", "United", CompareOptions.STARTS_WITH, false);
        String xpath4 = XPathHelper.makeTextComparisonXPath(".", "zil", CompareOptions.ENDS_WITH, false);
        String xpath5 = XPathHelper.makeTextComparisonXPath(".", "India", CompareOptions.NOT_EQUAL, false);


        System.out.println(xpath1 + "\n");
        System.out.println(xpath2 + "\n");
        System.out.println(xpath3 + "\n");
        System.out.println(xpath4 + "\n");
        System.out.println(xpath5 + "\n");

    }
}
