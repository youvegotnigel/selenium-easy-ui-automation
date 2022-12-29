package com.youvegotnigel.automation.utils.webTableHelper;

import com.youvegotnigel.automation.base.TestBase;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class WebElementHelper {

    private static final Logger log = LogManager.getLogger(WebElementHelper.class.getName());


    public static List<WebElement> findChildren(WebElement parent, By findBy){

        var driver = (parent != null) ? parent : TestBase.getDriver();
        List<WebElement> elements = driver.findElements(findBy);
        return elements;
    }

    public static WebElement findChild(WebElement parent, By findBy){
        List<WebElement> elements = findChildren(parent, findBy);
        return !elements.isEmpty() ? elements.get(0) : null;
    }

    public static String getTextContent(WebElement element){
        var driver= TestBase.getDriver();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String text = (String) jsExecutor.executeScript("return arguments[0].textContent;",element);
        return !text.isEmpty() ? text : "";
    }

    public static void jsScrollIntoView(WebElement element){
        try{
            if(element == null){
                Assert.fail("Can not move to NULL element");
            }
            WebDriver driver= TestBase.getDriver();
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            // Get the dimensions of the screen
            int screenWidth = driver.manage().window().getSize().width;
            int screenHeight = driver.manage().window().getSize().height;

            // Calculate the new x-coordinate
            int newX = (screenWidth/2) - element.getSize().width/2;

            // Calculate the new y-coordinate
            int newY = (screenHeight/2) - element.getSize().height/2;

            jsExecutor.executeScript(
                    "arguments[0].style.top = arguments[1] + 'px';" +
                       "arguments[0].style.left = arguments[2] + 'px';", element, newY, newX);
        }catch(Exception e){
            log.error(String.format("Fail to scroll element '%s' into view using javascript due to error: %s", element, e.getMessage()));
        }
    }

    public static void moveToElement(WebElement element){
        try {
            if(element == null){
                Assert.fail("Can not move to NULL element");
            }
            var driver= TestBase.getDriver();
            jsScrollIntoView(element);
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();

        }catch (Exception e){
            log.error(String.format("Fail to move to element '%s' due to error: %s", element, e.getMessage()));
        }
    }

    public static void clickInView(WebElement element){
        try {
            moveToElement(element);
            element.click();

        }catch (Exception e){
            log.error(String.format("Fail to click on element '%s' due to error: %s", element, e.getMessage()));
        }
    }

    public static void selectOptionByLabel(WebElement element, String labelText, boolean isRegex){
        try {
            log.error(String.format("Selecting an option by its label. Label='%s'. Using Regular Expression: '%s'", labelText, isRegex));
            String optionElementXpath = String.format(".//option[normalize-space(text())='%s']", labelText);
            if(isRegex){
                String DELIMETER1 ="~!@1";
                String DELIMETER2 ="~!@2";
                String DELIMETER3 ="~!@3";
                String regLabel = DELIMETER1 + labelText + DELIMETER2;
                String outerHtml = element.getAttribute("outerHTML");
                outerHtml = outerHtml.replaceAll("</option>", DELIMETER2+DELIMETER3).replaceAll("<option.*?>",DELIMETER2+DELIMETER1).replaceAll("</select>", DELIMETER2+DELIMETER3);
                String[] match = outerHtml.split(labelText);

                if(match.length<=1){
                    throw new Exception("Could not find option with label " + labelText + " (regex=true) in select box");
                }

                int index = StringUtils.countMatches(match[0], DELIMETER1);
                optionElementXpath = ".//option[" + (index+1) + "]";
                new Select(element).selectByIndex(index);

            }else{
                new Select(element).selectByVisibleText(labelText);
            }

        }catch (Exception e){
            log.error(String.format("Could not find option with label '%s' (regex=%s) in select box due to error: %s", labelText, isRegex, e.getMessage()));
        }
    }

}
