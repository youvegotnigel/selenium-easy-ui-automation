package com.youvegotnigel.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class ListenerClass implements WebDriverListener, ITestListener, ISuiteListener {

    private static final Logger log = LogManager.getLogger(ListenerClass.class.getName());

    @Override
    public void onStart(ISuite suite) {

        log.info("****************************************************************************************");
        log.info("****************************************************************************************");
        log.info("$$$$$$$$$$$$$$$$$$   TEST SUITE : " + suite.getName() + " HAS STARTED   $$$$$$$$$$$$$$$$$$");
        log.info("****************************************************************************************");
        log.info("****************************************************************************************");

    }

    @Override
    public void onFinish(ISuite suite) {

        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.info("**************** TEST SUITE : " + suite.getName() + " HAS COMPLETED *******************");
        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

    }

    @Override
    public void onTestStart(ITestResult result) {

        //log.info("The test case " + result.getName() + " has been started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {
            //log.info(result.getMethod().getMethodName() + " is passed!");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {

            log.warn("💥 Exception Found !!!");
            //log.warn(result.getMethod().getMethodName() + " is failed!");
            log.error("Caused by : " + result.getThrowable());
            log.error(Arrays.toString(result.getThrowable().getStackTrace()));
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {

        if (result.getStatus() == ITestResult.SKIP) {
            System.out.println("Test Skipped : " + result.getName());
            log.info("The test case " + result.getName() + " has been skipped");
        }

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        log.debug("Trying to find element : " + locator.toString());
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        highlightElement(driver, result);
        log.debug("Found element : " + locator.toString());
    }

    @Override
    public void afterGetCurrentUrl(String result, WebDriver driver) {
        log.debug("Navigated to : " + driver.getCurrentUrl());
    }

    @Override
    public void beforeClick(WebElement element) {
        String path = element.toString().split("->")[1];
        log.debug("Trying to find element to click on :" + path);
    }

    @Override
    public void afterClick(WebElement element) {
        String path = element.toString().split("->")[1];
        log.debug("Found element to click on :" + path);
    }

    @Override
    public void afterIsDisplayed(WebElement element, boolean result) {
        String path = element.toString().split("->")[1];
        log.debug("Found element displayed :" + path);
    }

    @Override
    public void beforeGetText(WebElement element) {
        log.debug("Trying to find element with text: " + element.getText());
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        log.debug("Found element with text : " + element.getText());
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        log.warn("💥 Exception Found !!!");
        log.error("Caused by : " + e.getMessage());
    }

    private void highlightElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
    }
}
