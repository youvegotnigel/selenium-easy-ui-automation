package com.youvegotnigel.automation.factories;

import com.youvegotnigel.automation.utils.PropertyUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

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
     * @return WebDriver
     */
    public static WebDriver getDriver() {

        WebDriver driver = null;

        String browser = PropertyUtils.get("BROWSER_TYPE");
        boolean is_headless = Boolean.parseBoolean(PropertyUtils.get("IS_HEADLESS"));

        switch (browser){
            case "firefox":
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);
                firefoxOptions.setHeadless(is_headless);

                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions);
                log.debug("Initializing firefox driver");
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                log.debug("Initialize edge driver");
                break;

            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(is_headless);
                driver = new ChromeDriver(options);
                log.debug("Initialize chrome driver");
                break;
        }

        return driver;
    }

}
