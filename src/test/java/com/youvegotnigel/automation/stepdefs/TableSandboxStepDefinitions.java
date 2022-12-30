package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.base.BasePage;
import com.youvegotnigel.automation.driver.DriverManager;
import com.youvegotnigel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import com.youvegotnigel.automation.pageobjects.TablePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableSandboxStepDefinitions {

    public static final Logger log = LogManager.getLogger(TableSandboxStepDefinitions.class.getName());
    BasePage basePage = new BasePage(DriverManager.getDriver());
    TablePage tablePage = new TablePage(DriverManager.getDriver());

    @Given("User has navigated {string} sandbox")
    public void navigate_to_table_sandbox(String page) {

        basePage.clickOnLinkByName(page, WaitStrategy.CLICKABLE);
        log.info("Navigating to page ::: " + page);
    }

    @And("I type {string} in table search")
    public void table_filer(String text) {

        tablePage.table_search(text);
    }

}
