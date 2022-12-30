package com.youvegotnigel.automation.pageobjects;

import com.youvegotnigel.automation.base.BasePage;
import com.youvegotnigel.automation.factories.ExplicitWaitFactory;
import com.youvegotnigel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LandingPage extends BasePage {

    private static final Logger log = LogManager.getLogger(LandingPage.class.getName());

    //Constructor
    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public void selectFromTreeMenu(String text, WaitStrategy waitStrategy) {
        String xpath = "//ul[@id='treemenu']/descendant-or-self::a[contains(text(),'" + text +"')]";
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, By.xpath(xpath));
        try {
            element.click();
        } catch (Exception e) {
            log.debug("Could not click on web element");
            log.debug("xpath : " + xpath);
            log.error(e.getMessage());
        }
    }
}
