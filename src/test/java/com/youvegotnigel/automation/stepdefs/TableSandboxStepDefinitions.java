package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.base.TestBase;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TableSandboxStepDefinitions extends TestBase {

    public static final Logger log = LogManager.getLogger(TableSandboxStepDefinitions.class.getName());

    @Given("The Application has navigated tables sandbox")
    public void navigate_to_table_sandbox() {

        String url = "https://automatenow.io/sandbox-automation-testing-practice-website/tables/";
        log.debug("Navigating to URL ::: " + url);
        navigateTo(url);
    }

}
