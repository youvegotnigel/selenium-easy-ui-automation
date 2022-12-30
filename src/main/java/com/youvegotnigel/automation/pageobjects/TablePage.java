package com.youvegotnigel.automation.pageobjects;

import com.youvegotnigel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import com.youvegotnigel.automation.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TablePage extends BasePage {

    private By input_search = By.xpath("(//label[contains(text(),'Search:')])[1]/input");

    private static final Logger log = LogManager.getLogger(TablePage.class.getName());

    //Constructor
    public TablePage(WebDriver driver) {
        super(driver);
    }

    public void table_search(String text){
        setText(input_search, text, WaitStrategy.VISIBLE);
    }

}
