package com.youvegotnigel.automation.utils.newtable;

import com.youvegotnigel.automation.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class WebElementHelper {


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
            var driver= TestBase.getDriver();
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeAsyncScript("arguments[0].scrollIntoView(true);", element);
        }catch(Exception e){
            System.out.printf("Fail to scroll element '%s' into view using javascript due to error: %s", element, e.getMessage());
        }
    }

    public static void moveToElement(WebElement element){
        try {
            var driver= TestBase.getDriver();
            jsScrollIntoView(element);
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();

        }catch (Exception e){
            System.out.printf("Fail to move to element '%s' due to error: %s", element, e.getMessage());
        }
    }

    public static void clickInView(WebElement element){
        try {
            moveToElement(element);
            element.click();

        }catch (Exception e){
            System.out.printf("Fail to click on element '%s' due to error: %s", element, e.getMessage());
        }
    }

}
