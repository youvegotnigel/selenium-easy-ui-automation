package com.youvegotnigel.automation.factories;

import com.youvegotnigel.automation.utils.PropertyUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.managers.OperaDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.lang.reflect.InvocationTargetException;

/**
 * Dec 29, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public final class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class.getName());

    /**
     * Private constructor to avoid external instantiation
     */
    private DriverFactory() {
    }

    /**
     * @return WebDriver
     */
    public static WebDriver getDriver() {

        WebDriver driver = null;

        String browser = PropertyUtils.get("BROWSER_TYPE");
        boolean is_headless = Boolean.parseBoolean(PropertyUtils.get("IS_HEADLESS"));
        log.info(String.format("Starting '%s' browser with headless mode set to %s",browser, is_headless));

        switch (browser){
            //TODO: Fix null pointer error when running on FIREFOX
            case "firefox":
//                FirefoxBinary firefoxBinary = new FirefoxBinary();
//                FirefoxOptions firefoxOptions = new FirefoxOptions();
//                firefoxOptions.setBinary(firefoxBinary);
//                firefoxOptions.setHeadless(is_headless);

                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                log.debug("Initializing firefox driver");
                break;

            case "edge":
                //TODO: Run edge on headless mode
                WebDriverManager.edgedriver().setup();
                //EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver();
                log.debug("Initializing edge driver");
                break;

            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(is_headless);
                driver = new ChromeDriver(options);
                log.debug("Initializing chrome driver");
                break;

            case "safari":
                //TODO: Test if working in safari browser
                driver = new SafariDriver();
                log.debug("Initializing safari driver");
                break;

            default:
                throw new IllegalArgumentException(browser);
        }

        return driver;
    }

}
