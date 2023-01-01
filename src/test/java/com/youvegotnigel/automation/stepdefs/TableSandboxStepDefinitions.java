package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.base.BasePage;
import com.youvegotnigel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import com.youvegotnigel.automation.pageobjects.TablePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class TableSandboxStepDefinitions {

    public static final Logger log = LogManager.getLogger(TableSandboxStepDefinitions.class.getName());
    BasePage basePage = new BasePage();
    TablePage tablePage = new TablePage();

    @Given("User has navigated {string} sandbox")
    public void navigate_to_table_sandbox(String page) {

        String url = basePage.getCurrentURL() + page;
        basePage.navigateToURL(url);
        log.info("Navigating to page ::: " + url);
    }

    @And("I type {string} in table search")
    public void table_filer(String text) {

        tablePage.table_search(text);
    }

}
