package com.youvegotnigel.automation.pageobjects;

import com.youvegotnigel.automation.base.BasePage;
import com.youvegotnigel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

/**
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class FormPage extends BasePage {

    private static final Logger log = LogManager.getLogger(FormPage.class.getName());

    private static final By form_success_header = By.cssSelector("#contact-form-success-header");
    private static final By form_submit = By.xpath("(//button[@class='pushbutton-wide' and @type='submit'])[1]");

    //Constructor
    public FormPage() {
        super();
    }


    public void submitForm(){
        submit(form_submit, WaitStrategy.VISIBLE);
    }

    public String getSuccessHeaderText(){
       return getText(form_success_header, WaitStrategy.VISIBLE).trim();
    }

    public String getFormSubmissionResponse(String question){
        String xpath = String.format("//div[@class='field-name' and text()='%s']/following-sibling::div[@class='field-value'][1]", question);
        return getText(By.xpath(xpath), WaitStrategy.VISIBLE).trim();
    }
}
