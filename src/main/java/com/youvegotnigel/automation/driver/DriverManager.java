package com.youvegotnigel.automation.driver;

import org.openqa.selenium.WebDriver;

import java.util.Objects;

/**
 * DriverManager class helps to achieve thread safety for the WebDriver instance.
 *
 * Dec 29, 2022
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public final class DriverManager {

    /**
     * Private constructor to avoid external instantiation
     */
    private DriverManager() {}

    private static ThreadLocal<WebDriver> dr = new ThreadLocal<>() ;

    /**
     * Returns the thread safe WebDriver instance fetched from ThreadLocal variable.
     *
     * @author Nigel Mulholland
     * Dec 29, 2022
     * @return WebDriver instance.
     */
    public static WebDriver getDriver() {
        return dr.get();
    }

    /**
     * Set the WebDriver instance to thread local variable
     *
     * @author Nigel Mulholland
     * Dec 29, 2022
     * @param driver WebDriver instance that needs to saved from Thread safety issues
     */
    static void setDriver(WebDriver driver) {
        if(Objects.nonNull(driver)) {
            dr.set(driver);
        }
    }

    /**
     * Calling remove method on Thread local variable ensures to set the default value to Thread local variable.
     * It is much safer than assigning null value to ThreadLocal variable.
     * @author Nigel Mulholland
     * Dec 29, 2022
     */
    static void unload() {
        dr.remove();
    }



}
