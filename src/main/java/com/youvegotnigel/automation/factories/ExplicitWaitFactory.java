package com.youvegotnigel.automation.factories;

import com.youvegotnigel.automation.constants.FrameworkConstants;
import com.youvegotnigel.automation.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Explicit wait factory produces different waits before operating on web-element
 *
 * Dec 29, 2022
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public final class ExplicitWaitFactory {

    public enum WaitStrategy {
        CLICKABLE, PRESENCE, VISIBLE, NONE
    }
    /**
     * Private constructor to avoid external instantiation
     */
    private ExplicitWaitFactory() {}

    /**
     * @param waitstrategy Strategy to be applied to find a web-element
     * @param by By locator of the web-element
     * @return web-element Locates and return the web-element
     */
    public static WebElement performExplicitWait(WaitStrategy waitstrategy, By by) {

        WebElement element = null;
        if(waitstrategy == WaitStrategy.CLICKABLE) {
            element = 	new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.getPageLoadWait())
                    .until(ExpectedConditions.elementToBeClickable(by));
        }
        else if(waitstrategy == WaitStrategy.PRESENCE) {
            element =	new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.getPageLoadWait())
                    .until(ExpectedConditions.presenceOfElementLocated(by));
        }
        else if(waitstrategy == WaitStrategy.VISIBLE) {
            element =new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.getPageLoadWait())
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
        }
        else if(waitstrategy == WaitStrategy.NONE) {
            element = DriverManager.getDriver().findElement(by);
        }
        return element;
    }

}
