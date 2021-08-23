package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.base.TestBase;
import com.youvegotnigel.automation.pageobjects.LandingPage;
import io.cucumber.java.en.And;

public class LandingPageStepDefinitions extends TestBase {

    LandingPage landingPage = new LandingPage(eventFiringWebDriver);

    @And("I select main menu {string} and select sub menu {string}")
    public void select_menu_from_tree(String mainMenu, String subMenu) {

        landingPage.selectFromTreeMenu(mainMenu);
        implicitWait(5);
        landingPage.selectFromTreeMenu(subMenu);
    }


}
