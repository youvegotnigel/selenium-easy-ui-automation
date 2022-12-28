package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.base.PageBase;
import com.youvegotnigel.automation.base.TestBase;
import com.youvegotnigel.automation.pageobjects.TablePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableSandboxStepDefinitions extends TestBase {

    public static final Logger log = LogManager.getLogger(TableSandboxStepDefinitions.class.getName());
    PageBase pageBase = new PageBase(eventFiringWebDriver);
    TablePage tablePage = new TablePage(eventFiringWebDriver);

    @Given("User has navigated {string} sandbox")
    public void navigate_to_table_sandbox(String page) {

        pageBase.clickOnLinkByName(page);
        log.info("Navigating to page ::: " + page);
    }

    @And("I type {string} in table search")
    public void table_filer(String text) {

        tablePage.table_search(text);
    }

}
