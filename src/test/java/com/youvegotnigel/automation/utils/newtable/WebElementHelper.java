package com.youvegotnigel.automation.utils.newtable;

import com.youvegotnigel.automation.base.TestBase;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

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

    public static void selectOptionByLabel(WebElement element, String labelText, boolean isRegex){
        try {
            System.out.printf("Selecting an option by its label. Label='%s'. Using Regular Expression: '%s'", labelText, isRegex);
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
            System.out.printf("Could not find option with label '%s' (regex=%s) in select box due to error: %s", labelText, isRegex, e.getMessage());
        }
    }

}
