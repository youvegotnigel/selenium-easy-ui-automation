package com.youvegotnigel.automation.utils.htmlTableHelper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class SeleniumTableTest {


    private static final String URL = "https://automatenow.io/sandbox-automation-testing-practice-website/tables/";
    private WebDriver driver;


    @BeforeClass
    public void setup(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
    }

    @Test
    public void test(){






    }

    public void explicitWaitMethod(By element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    @AfterClass
    public void teardown(){
        driver.quit();
    }

    public static String identifyTableByTableHeaders(String table_name, String element_index){

        String xpath = "(//div[@class='headerNamePanel' and contains(text(),'"+table_name+"')]/../following-sibling::table)["+element_index+"]";
        return xpath;
    }

    public static String identifyTableByColumnHeaders(Map<String,String> cell_info, String element_index){

        String row_xpath = "(//thead/tr[" + prepareTableHeaderXpath(cell_info) + "]/ancestor::table)["+element_index+"]";
        return row_xpath;
    }



}
