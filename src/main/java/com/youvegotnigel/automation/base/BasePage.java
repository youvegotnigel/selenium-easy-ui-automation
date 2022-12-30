package com.youvegotnigel.automation.base;

import com.youvegotnigel.automation.constants.FrameworkConstants;
import com.youvegotnigel.automation.factories.ExplicitWaitFactory;
import com.youvegotnigel.automation.factories.ExplicitWaitFactory.WaitStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    private static WebDriver driver;

    private By cookie_info_bar = By.cssSelector("#cookie-law-info-bar");
    private By accept_cookie = By.cssSelector("#cookie_action_close_header");

    public static final Logger log = LogManager.getLogger(BasePage.class.getName());

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver(){
        return this.driver;
    }
    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clearText(By by, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        element.clear();
    }

    public void click(By by, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        element.click();
    }

    public void submit(By by, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        element.submit();
    }

    public void setText(By by, String text, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        element.sendKeys(text);
    }

    public String getText(By by, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        return element.getText();
    }

    public boolean isDisplayed(By by, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        return element.isDisplayed();
    }

    public String getAttribute(By by, String attribute, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, by);
        return element.getAttribute(attribute);
    }

    public boolean cookieBarIsDisplayed(){
        return isDisplayed(cookie_info_bar, WaitStrategy.VISIBLE);
    }

    public void acceptCookie(){
        click(accept_cookie, WaitStrategy.CLICKABLE);
    }

    // ############################################ Generic xpath's ############################################

    public void setTextInputForLabel(String label_name, String value){

        String xpath = "(//label[contains(text(),'"+ label_name +"')])[1]/following::input[1]";
        WebElement element = driver.findElement(By.xpath(xpath));
        element.sendKeys(value);
    }

    public void setTextInputForLabel(String label_name, String index, String value){

        String xpath = "(//label[contains(text(),'"+ label_name +"')])["+ index +"]/following::input[1]";
        WebElement element = driver.findElement(By.xpath(xpath));
        element.sendKeys(value);
    }

    public void setTextAreaForLabel(String label_name, String value){

        String xpath = "//label[contains(text(),'"+ label_name +"')]/following::textarea[1]";
        WebElement element = driver.findElement(By.xpath(xpath));
        element.sendKeys(value);
    }

    public void setTextAreaForLabel(String label_name, String index, String value){

        String xpath = "(//label[contains(text(),'"+ label_name +"')])["+ index +"]/following::textarea[1]";
        WebElement element = driver.findElement(By.xpath(xpath));
        element.sendKeys(value);
    }

    public void clickOnButtonByName(String text) {
        String xpath = "(//button[contains(normalize-space(),'" + text + "')])[1]";
        WebElement element = driver.findElement(By.xpath(xpath));
        scrollIntoView(element);
        try {
            element.click();
        } catch (Exception e) {
            log.debug("Could not click on web element");
            log.debug("xpath : " + xpath);
            log.error(e.getMessage());
        }
    }

    public void clickOnButtonByName(String text, String index) {
        String xpath = "(//button[contains(normalize-space(),'" + text + "')])["+ index +"]" ;
        WebElement element = driver.findElement(By.xpath(xpath));
        scrollIntoView(element);
        try {
            element.click();
        } catch (Exception e) {
            log.debug("Could not click on web element");
            log.debug("xpath : " + xpath);
            log.error(e.getMessage());
        }
    }

    public void clickOnLinkByName(String text) {
        String xpath = "//a[contains(normalize-space(),'" + text + "')]";
        WebElement element = driver.findElement(By.xpath(xpath));
        scrollIntoView(element);
        try {
            element.click();
        } catch (Exception e) {
            log.debug("Could not click on web element");
            log.debug("xpath : " + xpath);
            log.error(e.getMessage());
        }
    }

    public void clickOnLinkByName(String text, String index) {
        String xpath = "(//a[contains(normalize-space(),'" + text + "')])["+ index +"]" ;
        WebElement element = driver.findElement(By.xpath(xpath));
        scrollIntoView(element);
        try {
            element.click();
        } catch (Exception e) {
            log.debug("Could not click on web element");
            log.debug("xpath : " + xpath);
            log.error(e.getMessage());
        }
    }

    public boolean isDisplayedInNormalizeSpace(String text) {
        String xpath = "//*[normalize-space()='" + text + "']";
        WebElement element = driver.findElement(By.xpath(xpath));
        scrollIntoView(element);
        return element.isDisplayed();
    }

    public boolean isDisplayedInNormalizeSpace(String text, String index) {
        String xpath = "(//*[normalize-space()='" + text + "'])["+ index +"]" ;
        WebElement element = driver.findElement(By.xpath(xpath));
        scrollIntoView(element);
        return element.isDisplayed();
    }

    public void clickOnNormalizeSpace(String text) {
        String xpath = "//*[normalize-space()='" + text + "']";
        WebElement element = driver.findElement(By.xpath(xpath));
        scrollIntoView(element);
        try {
            element.click();
        } catch (Exception e) {
            log.debug("Could not click on web element");
            log.debug("xpath : " + xpath);
            log.error(e.getMessage());
        }
    }

    public void clickOnNormalizeSpace(String text, String index) {
        String xpath = "(//*[normalize-space()='" + text + "'])["+ index +"]" ;
        WebElement element = driver.findElement(By.xpath(xpath));
        scrollIntoView(element);
        try {
            element.click();
        } catch (Exception e) {
            log.debug("Could not click on web element");
            log.debug("xpath : " + xpath);
            log.error(e.getMessage());
        }
    }

    public void setRadioForLabel(String label_name, String value){

        String xpath = "//label[contains(text(),'"+ label_name +"')]/following::input[@value='"+ value +"']";
        WebElement element = driver.findElement(By.xpath(xpath));
        scrollIntoView(element);
        try {
            element.click();
        } catch (Exception e) {
            log.debug("Could not click on web element");
            log.debug("xpath : " + xpath);
            log.error(e.getMessage());
        }
    }

    public void setRadioForLabel(String label_name, String index, String value){

        String xpath = "(//label[contains(text(),'"+ label_name +"')])["+ index +"]/following::input[@value='"+ value +"']";
        WebElement element = driver.findElement(By.xpath(xpath));
        scrollIntoView(element);
        try {
            element.click();
        } catch (Exception e) {
            log.debug("Could not click on web element");
            log.debug("xpath : " + xpath);
            log.error(e.getMessage());
        }
    }

    /**
     *
     * @param label_name name of the label
     * @param visibleText select by visible text
     */
    public void selectFromDropdownByVisibleText(String label_name, String visibleText){
        String xpath = "(//label[contains(text(),'"+ label_name +"')])[1]/following::select";
        WebElement element = driver.findElement(By.xpath(xpath));
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    /**
     *
     * @param label_name name of the label
     * @param visibleText select by visible text
     * @param element_index element index of label
     */
    public void selectFromDropdownByVisibleText(String label_name, String element_index, String visibleText){
        String xpath = "(//label[contains(text(),'"+ label_name +"')])[" + element_index +"]/following::select";
        WebElement element = driver.findElement(By.xpath(xpath));
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    /**
     *
     * @param label_name name of the label
     * @param value select by value
     */
    public void selectFromDropdownByValue(String label_name, String value){
        String xpath = "//label[contains(text(),'"+ label_name +"')]/following::select";
        WebElement element = driver.findElement(By.xpath(xpath));
        Select select = new Select(element);
        select.selectByValue(value);
    }

    /**
     *
     * @param label_name name of the label
     * @param value select by value
     * @param element_index element index of label
     */
    public void selectFromDropdownByValue(String label_name, String element_index, String value){
        String xpath = "(//label[contains(text(),'"+ label_name +"')])[" + element_index +"]/following::select";
        WebElement element = driver.findElement(By.xpath(xpath));
        Select select = new Select(element);
        select.selectByValue(value);
    }

    /**
     *
     * @param label_name name of the label
     * @param index select by index
     */
    public void selectFromDropdownByIndex(String label_name, int index){
        String xpath = "//label[contains(text(),'"+ label_name +"')]/following::select";
        WebElement element = driver.findElement(By.xpath(xpath));
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    /**
     *
     * @param label_name name of the label
     * @param index select by index
     * @param element_index element index of label
     */
    public void selectFromDropdownByIndex(String label_name, String element_index, int index){
        String xpath = "(//label[contains(text(),'"+ label_name +"')])[" + element_index +"]/following::select";
        WebElement element = driver.findElement(By.xpath(xpath));
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    public static void scrollIntoView(WebElement element){
        try{

            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();

        }catch(Exception e){
            log.error(String.format("Fail to scroll element '%s' into view due to error: %s", element, e.getMessage()));
        }
    }
}
