package com.youvegotnigel.automation.pageobjects;

import com.youvegotnigel.automation.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FormPage extends BasePage {

    private static final Logger log = LogManager.getLogger(FormPage.class.getName());

    private By form_success_header = By.cssSelector("#contact-form-success-header");
    private By form_submit = By.xpath("(//button[@class='pushbutton-wide' and @type='submit'])[1]");

    //Constructor
    public FormPage(WebDriver driver) {
        super(driver);
    }


    public void submitForm(){
        submit(form_submit);
    }

    public String getSuccessHeaderText(){
       return getText(form_success_header).trim();
    }

    public String getFormSubmissionResponse(String question){
        String xpath = String.format("//div[@class='field-name' and text()='%s']/following-sibling::div[@class='field-value'][1]", question);
        return getText(By.xpath(xpath)).trim();
    }
}
