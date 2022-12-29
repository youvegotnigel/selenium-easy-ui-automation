package com.youvegotnigel.automation.factories;

import com.youvegotnigel.automation.utils.PropertyUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Dec 29, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public final class DriverFactory {

    public static final Logger log = LogManager.getLogger(DriverFactory.class.getName());

    /**
     * Private constructor to avoid external instantiation
     */
    private DriverFactory() {
    }

    /**
     * @return
     * @throws MalformedURLException TODO Remove hardcoded value of grid url
     */
    public static WebDriver getDriver() throws MalformedURLException {

        WebDriver driver = null;

        String browser = PropertyUtils.get("BROWSER_TYPE");

        if (browser.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("firefox")) {

            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        }
        return driver;
    }

}
