package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.driver.DriverManager;
import com.youvegotnigel.automation.pageobjects.FormPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class FormPageStepDefinitions {

    public static final Logger log = LogManager.getLogger(FormPageStepDefinitions.class.getName());

    FormPage formPage = new FormPage(DriverManager.getDriver());


    @And("I click on Submit form button")
    public void submit_form() {
        log.info("Submitting form");
        formPage.submitForm();
    }

    @And("Application should display success message {string}")
    public void verify_success_message_text(String text) {
        Assert.assertEquals(formPage.getSuccessHeaderText(), text);
    }

    @And("Application should display form submission values as below:")
    public void verify_form_submissions(DataTable dataTable) {

        List<Map<String, String>> values = dataTable.asMaps(String.class, String.class);

        values.forEach(
                map -> map.entrySet().forEach(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    System.out.println("KEY   ::: " + key);
                    System.out.println("VALUE ::: " + value);

                    Assert.assertEquals(formPage.getFormSubmissionResponse(key),value);

                })
        );
    }


}
