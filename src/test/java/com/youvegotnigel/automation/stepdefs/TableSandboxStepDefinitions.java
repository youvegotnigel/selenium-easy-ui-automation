package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.base.PageBase;
import com.youvegotnigel.automation.base.TestBase;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableSandboxStepDefinitions extends TestBase {

    public static final Logger log = LogManager.getLogger(TableSandboxStepDefinitions.class.getName());
    PageBase pageBase = new PageBase(eventFiringWebDriver);

    @Given("User has navigated {string} sandbox")
    public void navigate_to_table_sandbox(String page) {

        pageBase.clickOnLinkByName(page);
        log.info("Navigating to page ::: " + page);
    }

}
