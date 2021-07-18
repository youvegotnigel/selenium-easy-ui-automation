package com.youvegotnigel.automation.pageobjects;

import com.youvegotnigel.automation.base.PageBase;
import com.youvegotnigel.automation.pageobjects.forms.InputFormDemoPage;
import com.youvegotnigel.automation.pageobjects.forms.SimpleFormDemoPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LandingPage extends PageBase {

    public static final Logger log = LogManager.getLogger(LandingPage.class.getName());

    //Constructor
    public LandingPage(WebDriver driver) {
        super(driver);
    }



    //pass the driver for the next page
    public SimpleFormDemoPage goToSimpleFormDemoPage() {
        return new SimpleFormDemoPage(driver);
    }

    public InputFormDemoPage goToInputFormDemoPage() {
        return new InputFormDemoPage(driver);
    }
}
