package com.youvegotnigel.automation.sample_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class NewTableTest {


    private static final String URL = "https://automatenow.io/sandbox-automation-testing-practice-website/tables/";
    private WebDriver driver;


    @BeforeClass
    public void setup() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
    }

    @Test
    public void test() {

        // get the <table> element
        String tableXpath = "(//h4[contains(text(),'Sortable Table (countries by population)')]/following-sibling::*/table)[1]";
        WebElement tableElement = driver.findElement(By.xpath(tableXpath));
        String cellXpath = (".//tr[.//td[ position()= 1]|.//th[position()=0]]/*[(local-name()='td' or local-name()='th') and count(./preceding-sibling::*[local-name()='td' or local-name()='th'])=1]");

        System.out.println(tableElement.isDisplayed());


        List<WebElement> cellList = tableElement.findElements(By.xpath(cellXpath));
        System.out.println(cellList.size());

        List<String> cellTextList = new ArrayList<>();
        for (WebElement e: cellList){
            cellTextList.add(e.getText());
        }
        System.out.println(cellTextList.size());
        System.out.println(cellTextList);




    }

    public void explicitWaitMethod(By element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }


}
