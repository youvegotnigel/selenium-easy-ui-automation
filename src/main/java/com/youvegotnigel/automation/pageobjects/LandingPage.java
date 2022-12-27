package com.youvegotnigel.automation.pageobjects;

import com.youvegotnigel.automation.base.PageBase;
import com.youvegotnigel.automation.pageobjects.forms.FormPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LandingPage extends PageBase {

    public static final Logger log = LogManager.getLogger(LandingPage.class.getName());

    //Constructor
    public LandingPage(WebDriver driver) {
        super(driver);
    }

    public void selectFromTreeMenu(String text) {
        String xpath = "//ul[@id='treemenu']/descendant-or-self::a[contains(text(),'" + text +"')]";
        WebElement element = driver.findElement(By.xpath(xpath));
        try {
            element.click();
        } catch (Exception e) {
            log.debug("Could not click on web element");
            log.debug("xpath : " + xpath);
            log.error(e.getMessage());
        }
    }


    public FormPage goToInputFormDemoPage() {
        return new FormPage(driver);
    }
}
