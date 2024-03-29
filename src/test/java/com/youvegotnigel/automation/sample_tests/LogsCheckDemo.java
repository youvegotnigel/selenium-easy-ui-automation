package com.youvegotnigel.automation.sample_tests;
/**
 * This Class is created to check the log4j2 functionality, it is an independent class.
 * The log4j2.xml file is located under the resources' folder, this should be added to the project build path.
 * Log level order : TRACE > DEBUG > INFO > WARN > ERROR > FATAL > OFF, use the appropriate level in the log4j2.xml file.
 * Check the log file to see if the log message are getting added into the file.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

/**
 * This is a Test Class to verify generic methods are working as expected
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class LogsCheckDemo {

    final Logger log = LogManager.getLogger(LogsCheckDemo.class.getName());

    @Test
    public void TEST_Logging() {

        log.trace("Trace Logged message.");
        log.debug("Debug Logged message.");
        log.info("Info Logged message.");
        log.warn("Warning Logged message.");
        log.error("Error Logged message.");
        log.fatal("Fatal Logged message.");

        String name = "Jon";
        int age = 26;

        log.debug(String.format("%s is now %d years old", name, age));
        System.out.println("Execution completed.");
    }
}
