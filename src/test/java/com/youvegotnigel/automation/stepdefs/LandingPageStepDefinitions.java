package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.driver.DriverManager;
import com.youvegotnigel.automation.pageobjects.LandingPage;
import io.cucumber.java.en.And;

public class LandingPageStepDefinitions{

    LandingPage landingPage = new LandingPage(DriverManager.getDriver());

    @And("I select main menu {string} and select sub menu {string}")
    public void select_menu_from_tree(String mainMenu, String subMenu) {

        landingPage.selectFromTreeMenu(mainMenu);
        landingPage.selectFromTreeMenu(subMenu);
    }


}
