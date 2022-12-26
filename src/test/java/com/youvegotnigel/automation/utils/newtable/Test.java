package com.youvegotnigel.automation.utils.newtable;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) {


        Date endTime = Date.from(Instant.ofEpochMilli(0));

        Date currentTime = Calendar.getInstance().getTime();

        System.out.println(endTime.after(currentTime));

    }
}
