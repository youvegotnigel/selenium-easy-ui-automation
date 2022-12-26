package com.youvegotnigel.automation.sample_tests;

import com.youvegotnigel.automation.utils.htmlTableHelper.SeleniumTable;
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
        WebElement tableElement = driver.findElement(By.xpath("//*[@id=\"table1\"]"));
        // get an instance of SeleniumTable
        SeleniumTable table = new SeleniumTable(tableElement);




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
