package com.youvegotnigel.automation.utils.newtable;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) {


        String textXpath = "ABCD";
        String columnXpath = String.format(".//tr/td[%s]|.//tr/th[%s]", textXpath, textXpath);

        System.out.println(columnXpath);

    }
}
