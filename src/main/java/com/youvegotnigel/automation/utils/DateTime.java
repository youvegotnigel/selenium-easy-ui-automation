package com.youvegotnigel.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimeZone;

/**
 * Define the datetime values for feature files
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class DateTime extends DateTimeUtility{

    private static final String timeZone = PropertyUtils.get("TIME_ZONE");
    private static final Logger log = LogManager.getLogger(DateTime.class.getName());

    /**
     * Get date time formatted value
     *
     * @param text String to check if it's a date time to get Date/Time
     * @return the Date/Time value
     */
    public static String formatIfDateTime(String text) {

        log.info("The current TimeZone ID is: " + TimeZone.getTimeZone(timeZone).getID());
        log.debug("Setting datetime for " + text);

        switch (text) {
            case "year":
                return getCurrentYear();

            case "last year":
                return getPreviousYear(1);

            case "next year":
                return getFutureYear(1);

            case "today":
                return getCurrentDateTime(timeZone, "dd-MMM-yyyy");

            case "previous":
                return getPreviousDateTime(1,  "dd-MMM-yyyy");

            case "future":
                return getFutureDateTime(1,  "dd-MMM-yyyy");

            case "today yyyy-MM-dd":
                return getCurrentDateTime(timeZone, "yyyy-MM-dd");

            case "today d":
                return getCurrentDateTime(timeZone, "d");

            case "now HH:mm:ss":
                return getCurrentDateTime(timeZone, "HH:mm:ss");

            case "now":
                return getCurrentDateTime(timeZone,"hh:mm a");

            case "now h:mm":
                return getCurrentDateTime(timeZone,"h:mm");

            case "today+now":
                return getCurrentDateTime(timeZone,"dd-MMM-yyyy HH:mm");

            case "today+now+time":
                return getCurrentDateTime(timeZone,"dd-MMM-yyyy hh:mm:ss aa");

            case "now HH":
                return getCurrentDateTime(timeZone,"HH");

            case "now hh":
                return getCurrentDateTime(timeZone,"hh");

            case "now mm":
                return getCurrentDateTime(timeZone,"mm");

            case "now m0":
                return getCurrentDateTime(timeZone,"m0");

            default:

                if (text.matches("^previous\\+\\d$")) {
                    String[] date = text.split("\\+");
                    return getPreviousDateTime(Integer.parseInt(date[1]), "dd-MMM-yyyy");
                }

                else if (text.matches("^future\\+\\d$")) {
                    String[] date = text.split("\\+");
                    return getFutureDateTime(Integer.parseInt(date[1]), "dd-MMM-yyyy");
                }

                else if(text.matches("^now HH\\+\\d$")){
                    String[] change = text.split("now HH+");
                    return getFutureTime(Integer.parseInt(change[1]),0, timeZone,"HH");
                }

                else if(text.matches("^now HH\\-\\d$")){
                    String[] change = text.split("now HH-");
                    return getPastTime(Integer.parseInt(change[1]),0, timeZone,"HH");
                }

                else if(text.matches("^now mm\\+\\d$")){
                    String[] change = text.split("now mm+");
                    return getFutureTime(0, Integer.parseInt(change[1]), timeZone,"mm");
                }

                else if(text.matches("^now mm\\-\\d$")){
                    String[] change = text.split("now mm-");
                    return getPastTime(0, Integer.parseInt(change[1]), timeZone,"mm");
                }

                return text;
        }
    }
}
