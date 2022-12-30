package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import com.youvegotnigel.automation.pageobjects.LandingPage;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class LandingPageStepDefinitions {

    private static final Logger log = LogManager.getLogger(LandingPageStepDefinitions.class.getName());
    LandingPage landingPage = new LandingPage();

    @And("I select main menu {string} and select sub menu {string}")
    public void select_menu_from_tree(String mainMenu, String subMenu) {

        landingPage.selectFromTreeMenu(mainMenu, WaitStrategy.CLICKABLE);
        landingPage.selectFromTreeMenu(subMenu, WaitStrategy.CLICKABLE);
    }


}
