package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.base.PageBase;
import com.youvegotnigel.automation.base.TestBase;
import com.youvegotnigel.automation.utils.webTableHelper.HTMLTableHelper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class BasePageStepDefinitions extends TestBase {

    PageBase pageBase = new PageBase(eventFiringWebDriver);
    public static final Logger log = LogManager.getLogger(BasePageStepDefinitions.class.getName());

    @Given("The Application has been launched")
    public void application_is_launched() {
//        if (pageBase.adIsDisplayed()) {
//            pageBase.closeAd();
//        }
        //Assert.assertEquals(pageBase.getPageTitle(), "Selenium Framework | Practiceform");
    }

    @And("^I wait for \"(.+)\" seconds$")
    public void wait_time(String time) {
        implicitWait(Integer.parseInt(time));
    }

    @And("I click on {string} button")
    public void click_on_button(String text) {

        if (text.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(text);
            pageBase.clickOnButtonByName(valueAndIndex[0], valueAndIndex[1]);
        } else {
            pageBase.clickOnButtonByName(text);
        }
    }

    @And("I click on {string} link")
    public void click_on_link(String text) {

        if (text.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(text);
            pageBase.clickOnLinkByName(valueAndIndex[0], valueAndIndex[1]);
        } else {
            pageBase.clickOnLinkByName(text);
        }
    }

    @And("^I click on the '(.+)' (?: |button|link|text)$")
    public void click_on_normalize_space(String text) {

        if (text.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(text);
            pageBase.clickOnNormalizeSpace(text, valueAndIndex[1]);
        } else {
            pageBase.clickOnNormalizeSpace(text);
        }
    }

    @And("I should see the text {string} displayed")
    public void text_is_displayed(String text) {

        if (text.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(text);
            Assert.assertTrue(pageBase.isDisplayedInNormalizeSpace(valueAndIndex[0], valueAndIndex[1]), "Not found text ::: " + text);
        } else {
            Assert.assertTrue(pageBase.isDisplayedInNormalizeSpace(text), "Not found text ::: " + text);
        }
    }

    @And("I should not see the text {string} displayed")
    public void text_is_not_displayed(String text) {

        if (text.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(text);
            Assert.assertFalse(pageBase.isDisplayedInNormalizeSpace(valueAndIndex[0], valueAndIndex[1]), "Found text ::: " + text);
        } else {
            Assert.assertFalse(pageBase.isDisplayedInNormalizeSpace(text), "Found text ::: " + text);
        }
    }

    @And("^I set value \"(.+)\" for \"(.+)\"$")
    public void set_text_for_label(String answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            setTextInputForLabel(valueAndIndex[0], valueAndIndex[1], answer);
        } else {
            setTextInputForLabel(question, answer);
        }
    }

    @And("^I set value \"(.+)\" for textarea \"(.+)\"$")
    public void set_text_for_textarea(String answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            setTextAreaForLabel(valueAndIndex[0], valueAndIndex[1], answer);
        } else {
            setTextAreaForLabel(getValueAndIndex(question)[0], answer);
        }
    }

    @And("^I set radio value \"(.+)\" for label \"(.+)\"$")
    public void set_radio_button_for_label(String answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            pageBase.setRadioForLabel(valueAndIndex[0], valueAndIndex[1], answer);
        } else {
            pageBase.setRadioForLabel(question, answer);
        }
    }

    @And("^I select visible text from dropdown \"(.+)\" for label \"(.+)\"$")
    public void select_from_dropdown_by_visible_text(String answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            pageBase.selectFromDropdownByVisibleText(valueAndIndex[0], valueAndIndex[1], answer);
        } else {
            pageBase.selectFromDropdownByVisibleText(question, answer);
        }
    }

    @And("^I select value from dropdown \"(.+)\" for label \"(.+)\"$")
    public void select_from_dropdown_by_value(String answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            pageBase.selectFromDropdownByValue(valueAndIndex[0], valueAndIndex[1], answer);
        } else {
            pageBase.selectFromDropdownByValue(question, answer);
        }
    }

    @And("I select index from dropdown {int} for label {string}")
    public void select_from_dropdown_by_index(int answer, String question) {

        if (question.matches(".*\\[[\\d.]]")) {
            var valueAndIndex = getValueAndIndex(question);
            pageBase.selectFromDropdownByIndex(valueAndIndex[0], valueAndIndex[1], answer);
        } else {
            pageBase.selectFromDropdownByIndex(question, answer);
        }
    }

    public List<String> get_table_list_in_application(String column) {

        List<String> applicationList = new ArrayList<>();
//
//        def tot_table_row_count =  WebUI.callTestCase(findTestCase('Test Cases/TS UI Tests/Page Objects/Base/Get_Table_Row_Count'), [('get_column_heder'):column, ('column_header'):column])
//        KeywordUtil.logInfo("Total row count ::: ${tot_table_row_count}")
//
//        for(def no=1;no<tot_table_row_count;no+=2)
//        {
//            def Value = WebUI.callTestCase(findTestCase('Test Cases/TS UI Tests/Page Objects/Base/Get_Table_Value'), [('get_column_heder'):column, ('column_header'):column, ('response_code_value'):("row${no}")])
//            KeywordUtil.logInfo("Current Row ${no} Value is = ${Value}")
//
//            try{
//                applicationList.add(Integer.parseInt(Value))
//
//            }catch (NumberFormatException e){
//                applicationList.add(Value)
//
//            }
//
//        }
//
        return applicationList;
    }

    @And("Get values for {string} column in table and verify strings in descending order")
    public void validate_string_sorting_descending(String column) {

        List<String> SortedValuesAccordingToApplication = get_table_list_in_application(column);

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

    @And("Get values for {string} column in table and verify strings in ascending order")
    public void validate_string_sorting_ascending(String column) {

        List<String> SortedValuesAccordingToApplication = get_table_list_in_application(column);

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

    @And("Get values for {string} column in table and verify numbers in descending order")
    public void validate_int_sorting_descending(String column) {

        List<String> SortedValuesAccordingToApplication = get_table_list_in_application(column);

        log.debug("Sorted Values According To Application are = " + SortedValuesAccordingToApplication);

        List<String> sortedValuesAccordingToPrograming = new ArrayList<>();

        sortedValuesAccordingToPrograming.addAll(SortedValuesAccordingToApplication);

        //Sort sortedValuesAccordingToPrograming in descending order
        Collections.sort(sortedValuesAccordingToPrograming, Collections.reverseOrder());

        log.debug("Sorted Values According To Programing are = " + sortedValuesAccordingToPrograming);
        Assert.assertTrue(SortedValuesAccordingToApplication.equals(sortedValuesAccordingToPrograming));
    }

    @And("Get values for {string} column in table and verify numbers in ascending order")
    public void validate_int_sorting_ascending(String column) {

        List<String> SortedValuesAccordingToApplication = get_table_list_in_application(column);

        log.debug("Sorted Values According To Application are = " + SortedValuesAccordingToApplication);

        List<String> sortedValuesAccordingToPrograming = new ArrayList<>();

        sortedValuesAccordingToPrograming.addAll(SortedValuesAccordingToApplication);

        //Sort sortedValuesAccordingToPrograming in ascending order
        Collections.sort(sortedValuesAccordingToPrograming);

        log.debug("Sorted Values According To Programing are = " + sortedValuesAccordingToPrograming);
        Assert.assertTrue(SortedValuesAccordingToApplication.equals(sortedValuesAccordingToPrograming));
    }

    @And("^Table should be displayed as below:$")
    public void verify_table_value(DataTable dataTable) {

//        Map<String, String> map = dataTable.asMap(String.class, String.class);
//        var table = HTMLTableHelper.identifyTable(map, 1);
//        Assert.assertTrue(HTMLTableHelper.verifyRowDisplayed(table, map));

        List<Map<String, String>> values = dataTable.asMaps(String.class, String.class);

        for(Map<String,String> map : values){
            WebElement table = HTMLTableHelper.identifyTable(map,1);
            Assert.assertTrue(HTMLTableHelper.verifyRowDisplayed(table, map));
        }


    }


    @And("^\"(.+)\" table should be displayed as below:$")
    public void verify_table_value(String tableHeader, DataTable dataTable) {

        List<Map<String, String>> values = dataTable.asMaps(String.class, String.class);
        WebElement table = HTMLTableHelper.identifyTable(tableHeader, 1);

//        values.forEach(map ->
//                Assert.assertTrue(HTMLTableHelper.verifyRowDisplayed(table, map))
//        );

        for(Map<String,String> map : values){
            Assert.assertTrue(HTMLTableHelper.verifyRowDisplayed(table, map));
        }
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
                } else {
                    set_text_for_label(answer, question);
                    break;
                }

        }
    }


}
