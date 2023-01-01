package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.base.BasePage;
import com.youvegotnigel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import com.youvegotnigel.automation.utils.webTableHelper.HTMLTableHelper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class BasePageStepDefinitions {

    public static final Logger log = LogManager.getLogger(BasePageStepDefinitions.class.getName());

    BasePage basePage = new BasePage();

    @Given("The Application has been launched")
    public void application_is_launched() {
        if (basePage.cookieBarIsDisplayed()) {
            basePage.acceptCookie();
        }
        basePage.refreshPage();
        Assert.assertEquals(basePage.getPageTitle(), "Automation Testing Practice Website | automateNow |");
    }

    @And("I wait for {int} seconds")
    public void wait_time(int time) {
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @And("I click on {string} button")
    public void click_on_button(String text) {

        if (text.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(text);
            basePage.clickOnButtonByName(valueAndIndex[0], valueAndIndex[1], WaitStrategy.CLICKABLE);
        } else {
            basePage.clickOnButtonByName(text, WaitStrategy.CLICKABLE);
        }
    }

    @And("I click on {string} link")
    public void click_on_link(String text) {

        if (text.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(text);
            basePage.clickOnLinkByName(valueAndIndex[0], valueAndIndex[1], WaitStrategy.CLICKABLE);
        } else {
            basePage.clickOnLinkByName(text, WaitStrategy.CLICKABLE);
        }
    }

    @And("^I click on the '(.+)' (?: |button|link|text)$")
    public void click_on_normalize_space(String text) {

        if (text.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(text);
            basePage.clickOnNormalizeSpace(text, valueAndIndex[1], WaitStrategy.CLICKABLE);
        } else {
            basePage.clickOnNormalizeSpace(text, WaitStrategy.CLICKABLE);
        }
    }

    @And("I should see the text {string} displayed")
    public void text_is_displayed(String text) {

        if (text.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(text);
            Assert.assertTrue(basePage.isDisplayedInNormalizeSpace(valueAndIndex[0], valueAndIndex[1], WaitStrategy.VISIBLE), "Not found text ::: " + text);
        } else {
            Assert.assertTrue(basePage.isDisplayedInNormalizeSpace(text, WaitStrategy.VISIBLE), "Not found text ::: " + text);
        }
    }

    @And("I should not see the text {string} displayed")
    public void text_is_not_displayed(String text) {

        if (text.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(text);
            Assert.assertFalse(basePage.isDisplayedInNormalizeSpace(valueAndIndex[0], valueAndIndex[1], WaitStrategy.VISIBLE), "Found text ::: " + text);
        } else {
            Assert.assertFalse(basePage.isDisplayedInNormalizeSpace(text, WaitStrategy.VISIBLE), "Found text ::: " + text);
        }
    }

    @And("^I set value \"(.+)\" for \"(.+)\"$")
    public void set_text_for_label(String answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            basePage.setTextInputForLabel(valueAndIndex[0], valueAndIndex[1], answer, WaitStrategy.VISIBLE);
        } else {
            basePage.setTextInputForLabel(question, answer, WaitStrategy.VISIBLE);
        }
    }

    @And("^I set value \"(.+)\" for textarea \"(.+)\"$")
    public void set_text_for_textarea(String answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            basePage.setTextAreaForLabel(valueAndIndex[0], valueAndIndex[1], answer, WaitStrategy.VISIBLE);
        } else {
            basePage.setTextAreaForLabel(getValueAndIndex(question)[0], answer, WaitStrategy.VISIBLE);
        }
    }

    @And("^I set (?:|radio|checkbox) value \"(.+)\" for label \"(.+)\"$")
    public void set_radio_button_for_label(String answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            basePage.setRadioForLabel(valueAndIndex[0], valueAndIndex[1], answer, WaitStrategy.CLICKABLE);
        } else {
            basePage.setRadioForLabel(question, answer, WaitStrategy.CLICKABLE);
        }
    }

    @And("^I select visible text from dropdown \"(.+)\" for label \"(.+)\"$")
    public void select_from_dropdown_by_visible_text(String answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            basePage.selectFromDropdownByVisibleText(valueAndIndex[0], valueAndIndex[1], answer, WaitStrategy.CLICKABLE);
        } else {
            basePage.selectFromDropdownByVisibleText(question, answer, WaitStrategy.CLICKABLE);
        }
    }

    @And("^I select value from dropdown \"(.+)\" for label \"(.+)\"$")
    public void select_from_dropdown_by_value(String answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            basePage.selectFromDropdownByValue(valueAndIndex[0], valueAndIndex[1], answer, WaitStrategy.CLICKABLE);
        } else {
            basePage.selectFromDropdownByValue(question, answer, WaitStrategy.CLICKABLE);
        }
    }

    @And("I select index from dropdown {int} for label {string}")
    public void select_from_dropdown_by_index(int answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            basePage.selectFromDropdownByIndex(valueAndIndex[0], valueAndIndex[1], answer, WaitStrategy.CLICKABLE);
        } else {
            basePage.selectFromDropdownByIndex(question, answer, WaitStrategy.CLICKABLE);
        }
    }

    @And("Get values for {string} column in {string} table and verify strings in descending order")
    public void validate_string_sorting_descending(String column, String tableHeader) {

        WebElement table = HTMLTableHelper.identifyTable(tableHeader,1);
        List<String> SortedValuesAccordingToApplication = HTMLTableHelper.getCellsValueByColumnHeader(table, column);

        log.debug("Sorted Values According To Application are = " + SortedValuesAccordingToApplication);
        List<String> sortedValuesAccordingToPrograming = new ArrayList<>();

        sortedValuesAccordingToPrograming.addAll(SortedValuesAccordingToApplication);

        //Sort List
        try {
            sortedValuesAccordingToPrograming.sort(String.CASE_INSENSITIVE_ORDER.reversed());
        } catch (ClassCastException e) {
            sortedValuesAccordingToPrograming.sort(Collections.reverseOrder());
        }

        log.debug("Sorted Values According To Programing are = " + sortedValuesAccordingToPrograming);

        //Assert.assertEquals(SortedValuesAccordingToApplication , sortedValuesAccordingToPrograming);
        Assert.assertTrue(SortedValuesAccordingToApplication.equals(sortedValuesAccordingToPrograming));
    }

    @And("Get values for {string} column in {string} table and verify strings in ascending order")
    public void validate_string_sorting_ascending(String column, String tableHeader) {

        WebElement table = HTMLTableHelper.identifyTable(tableHeader,1);
        List<String> SortedValuesAccordingToApplication = HTMLTableHelper.getCellsValueByColumnHeader(table, column);

        log.debug("Sorted Values According To Application are = " + SortedValuesAccordingToApplication);
        List<String> sortedValuesAccordingToPrograming = new ArrayList<>();

        sortedValuesAccordingToPrograming.addAll(SortedValuesAccordingToApplication);

        //Sort List
        try {
            sortedValuesAccordingToPrograming.sort(String.CASE_INSENSITIVE_ORDER);
        } catch (ClassCastException e) {
            log.error("Error in sorting strings in ascending order");
            log.error(e.getMessage());
        }

        log.debug("Sorted Values According To Programing are = " + sortedValuesAccordingToPrograming);

        //Assert.assertEquals(SortedValuesAccordingToApplication , sortedValuesAccordingToPrograming);
        Assert.assertTrue(SortedValuesAccordingToApplication.equals(sortedValuesAccordingToPrograming));
    }

    @And("Get values for {string} column in {string} table and verify numbers in descending order")
    public void validate_int_sorting_descending(String column, String tableHeader) {

        WebElement table = HTMLTableHelper.identifyTable(tableHeader,1);
        List<String> SortedValuesAccordingToApplication = HTMLTableHelper.getCellsValueByColumnHeader(table, column);

        log.debug("Sorted Values According To Application are = " + SortedValuesAccordingToApplication);

        List<String> sortedValuesAccordingToPrograming = new ArrayList<>();

        sortedValuesAccordingToPrograming.addAll(SortedValuesAccordingToApplication);

        //Sort sortedValuesAccordingToPrograming in descending order
        Collections.sort(sortedValuesAccordingToPrograming, Collections.reverseOrder());

        log.debug("Sorted Values According To Programing are = " + sortedValuesAccordingToPrograming);
        Assert.assertTrue(SortedValuesAccordingToApplication.equals(sortedValuesAccordingToPrograming));
    }

    @And("Get values for {string} column in {string} table and verify numbers in ascending order")
    public void validate_int_sorting_ascending(String column, String tableHeader) {

        WebElement table = HTMLTableHelper.identifyTable(tableHeader,1);
        List<String> SortedValuesAccordingToApplication = HTMLTableHelper.getCellsValueByColumnHeader(table, column);

        log.debug("Sorted Values According To Application are = " + SortedValuesAccordingToApplication);

        List<String> sortedValuesAccordingToPrograming = new ArrayList<>();

        sortedValuesAccordingToPrograming.addAll(SortedValuesAccordingToApplication);

        //Sort sortedValuesAccordingToPrograming in ascending order
        Collections.sort(sortedValuesAccordingToPrograming);

        log.debug("Sorted Values According To Programing are = " + sortedValuesAccordingToPrograming);
        Assert.assertTrue(SortedValuesAccordingToApplication.equals(sortedValuesAccordingToPrograming));
    }

    @And("^Table (?:|should be|is) displayed as below:$")
    public void verify_table_value(DataTable dataTable) {

        List<Map<String, String>> values = dataTable.asMaps(String.class, String.class);

        for(Map<String,String> map : values){
            WebElement table = HTMLTableHelper.identifyTable(map,1);
            Assert.assertTrue(HTMLTableHelper.verifyRowDisplayed(table, map));
        }

    }

    @And("^Table (?:|should not be|is not) displayed as below:$")
    public void verify_table_value_not_displayed(DataTable dataTable) {

        List<Map<String, String>> values = dataTable.asMaps(String.class, String.class);

        for(Map<String,String> map : values){
            WebElement table = HTMLTableHelper.identifyTable(map,1);
            Assert.assertFalse(HTMLTableHelper.verifyRowDisplayed(table, map));
        }

    }

    @And("^I click on header \"(.+)\" column of below table$")
    public void click_on_table_column_header(String columnHeader, DataTable dataTable) {

        List<Map<String, String>> values = dataTable.asMaps(String.class, String.class);

        for(Map<String,String> map : values){
            WebElement table = HTMLTableHelper.identifyTable(map,1);
            HTMLTableHelper.clickOnColumn(table, columnHeader);
        }

    }

    @And("^\"(.+)\" Table should filer \"(.+)\" as below:$")
    public void click_on_table_column_header(String tableName, String columnHeader, DataTable dataTable) {

        WebElement table = HTMLTableHelper.identifyTable(tableName,1);
        List<List<String>> values = dataTable.asLists();
        List<String> expectedValueList = values.stream().flatMap(List::stream).collect(Collectors.toList());
        List<String> actualValueList = HTMLTableHelper.getCellsValueByColumnHeader(table, columnHeader);

        Assert.assertTrue(actualValueList.equals(expectedValueList), String.format("Actual List:::'%s', Expected List:::'%s'",actualValueList, expectedValueList));
    }

    @And("^\"(.+)\" table should be displayed as below:$")
    public void verify_table_value(String tableHeader, DataTable dataTable) {

        List<Map<String, String>> values = dataTable.asMaps(String.class, String.class);
        WebElement table = HTMLTableHelper.identifyTable(tableHeader, 1);

        for(Map<String,String> map : values){
            Assert.assertTrue(HTMLTableHelper.verifyRowDisplayed(table, map));
        }
    }

    @And("^I click on \"(.+)\" cell in \"(.+)\" Table$")
    public void click_on_table_cell(String cellValue, String tableHeader) {

        WebElement table = HTMLTableHelper.identifyTable(tableHeader, 1);
        HTMLTableHelper.clickOnLinkInCell(table, cellValue);
    }

    @And("^(?:|I )(?:Enter|enter) (?:|.* )(?:values|details) as below:$")
    public void enter_values(DataTable dataTable) {

        List<Map<String, String>> values = dataTable.asMaps(String.class, String.class);

        values.forEach(
                map -> map.entrySet().forEach(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    System.out.println("KEY   ::: " + key);
                    System.out.println("VALUE ::: " + value);

                    enterValue(value, key);
                })
        );
    }

    public void enterValue(String answer, String question) {

        switch (tokenize(question, "[")) {
            case "something":
                break;

            default:

                if (question.matches("(.*)suggest].*")) {
                    break;
                } else if (question.matches("(.*)textarea].*")) {
                    set_text_for_textarea(answer, question);
                    break;
                } else if(question.matches("(.*)select].*")){
                    select_from_dropdown_by_visible_text(answer, question.split("\\[")[0]);
                    break;
                }else {
                    set_text_for_label(answer, question);
                    break;
                }

        }
    }


    public String decodeText(String text){
        if(text.equals("")|| text.equals(null)){
            return " ";
        }
        byte[] actualByte = Base64.getDecoder().decode(text);
        String actualString = new String(actualByte);
        return actualString;
    }

    public String[] getValueAndIndex(String value) {
        String[] values = value.split(Pattern.quote("["));
        values[1] = values[1].replaceAll("[^\\d.]", "");
        return values;
    }

    public String tokenize(String text, String regex){

        String [] tokenizer = new String[0];

        try {
            tokenizer = text.split(Pattern.quote(regex));
            return tokenizer[1];
        }catch (ArrayIndexOutOfBoundsException e){
            return tokenizer[0];
        }
    }


}
