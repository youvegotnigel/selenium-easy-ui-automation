package com.youvegotnigel.automation.factories;

import com.youvegotnigel.automation.constants.FrameworkConstants;
import com.youvegotnigel.automation.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Explicit wait factory produces different waits before operating on web-element
 * Dec 29, 2022
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public final class ExplicitWaitFactory {

    private static final Logger log = LogManager.getLogger(ExplicitWaitFactory.class.getName());

    public enum WaitStrategy {
        CLICKABLE, PRESENCE, VISIBLE, HANDLE_STALE_ELEMENT, NONE
    }

    /**
     * Private constructor to avoid external instantiation
     */
    private ExplicitWaitFactory() {
    }

    /**
     * @param waitstrategy Strategy to be applied to find a web-element
     * @param by           By locator of the web-element
     * @return web-element Locates and return the web-element
     */
    public static WebElement performExplicitWait(WaitStrategy waitstrategy, By by) {

        WebElement element = null;
        switch (waitstrategy) {
            case CLICKABLE:
                log.debug("Wait for Element Clickable...");
                element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.getPageLoadWait()))
                        .until(ExpectedConditions.elementToBeClickable(by));
                break;

            case PRESENCE:
                log.debug("Wait for Element Presence...");
                element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.getPageLoadWait()))
                        .until(ExpectedConditions.presenceOfElementLocated(by));
                break;

            case VISIBLE:
                log.debug("Wait for Element Visible...");
                element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.getPageLoadWait()))
                        .until(ExpectedConditions.visibilityOfElementLocated(by));
                break;

            case HANDLE_STALE_ELEMENT:
                element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.getPageLoadWait()))
                        .until(d-> {
                            log.debug("Wait for Handling Stale Element...");
                            d.navigate().refresh();
                            return d.findElement(by);
                        });
                break;

            case NONE:
                element = DriverManager.getDriver().findElement(by);
                break;
        }

        return element;
    }

}
