package com.youvegotnigel.automation.pageobjects;

import com.youvegotnigel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import com.youvegotnigel.automation.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

/**
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class TablePage extends BasePage {

    private static final By input_search = By.xpath("(//label[contains(text(),'Search:')])[1]/input");

    private static final Logger log = LogManager.getLogger(TablePage.class.getName());

    //Constructor
    public TablePage() {
        super();
    }

    public void table_search(String text){
        setText(input_search, text, WaitStrategy.VISIBLE);
    }

}
