package com.youvegotnigel.automation.driver;

import com.youvegotnigel.automation.factories.DriverFactory;
import com.youvegotnigel.automation.utils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.util.Objects;

/**
 *
 * Driver class is responsible for invoking and closing the browsers.
 *
 * @author Nigel Mulholland
 * Dec 29, 2022
 * @version 1.0
 * @since 1.0
 */
public final class Driver {

    private static final Logger log = LogManager.getLogger(Driver.class.getName());

    /**
     * Private constructor to avoid external instantiation
     */
    private Driver() {}

    /**
     * Gets the browser value and initialise the browser based on that
     */
    public static void initDriver(){

        if(Objects.isNull(DriverManager.getDriver())) {
            try {
                DriverManager.setDriver(DriverFactory.getDriver());
            } catch (Exception e) {
                log.error("Please check the capabilities of browser");
                log.error(e.getMessage());
            }
            DriverFactory.getDriver().manage().window().maximize();
            log.debug("Maximizing Browser Window");
            DriverFactory.getDriver().manage().deleteAllCookies();
            log.debug("Deleting All Cookies");
            DriverManager.getDriver().get(PropertyUtils.get("LOGIN_URL"));
            log.debug("Navigating to Application URL + " + PropertyUtils.get("LOGIN_URL"));

        }
    }

    /**
     * Terminates the browser instance.
     * Sets the thread local to default value, i.e null.
     */
    public static void quitDriver() {
        if(Objects.nonNull(DriverManager.getDriver())) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }

}
