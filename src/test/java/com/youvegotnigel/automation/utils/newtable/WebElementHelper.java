package com.youvegotnigel.automation.utils.newtable;

import com.youvegotnigel.automation.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebElementHelper {


    public static List<WebElement> findChildren(WebElement parent, By findBy){

        var driver = (parent != null) ? parent : TestBase.getDriver();

        List<WebElement> elements = new ArrayList<>();

        elements = driver.findElements(findBy);

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

}
