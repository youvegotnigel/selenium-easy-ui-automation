package com.youvegotnigel.automation.constants;

import com.youvegotnigel.automation.driver.DriverManager;
import com.youvegotnigel.automation.utils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * Define the framework constants here
 * Dec 29, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class FrameworkConstants {

    private static final Logger log = LogManager.getLogger(FrameworkConstants.class.getName());

    /**
     * Private constructor to avoid external instantiation
     */
    private FrameworkConstants() {

    }

    //Fields
    private static final int ELEMENT_LOAD_WAIT = 3;
    private static final int PAGE_LOAD_WAIT = 10;
    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources";
    private static final String CONFIG_FILE_NAME = System.getProperty("configFile");
    private static final String CONFIG_FILE_PATH = RESOURCES_PATH + "/config/" + CONFIG_FILE_NAME + ".properties";
    private static final String ALLURE_Environment_PROPERTIES_PATH = "allure-results\\environment.properties";
    private static final String CUCUMBER_REPORT_FOLDER_PATH = System.getProperty("user.dir") + "/target/cucumber-reports";
    private static final String CUCUMBER_HTML_FILE_PATH = "html:target/cucumber-reports/cucumber.html";
    private static final String CUCUMBER_XML_FILE_PATH = "junit:target/cucumber-reports/cucumber.xml";
    private static final String CUCUMBER_JSON_FILE_PATH = "json:target/cucumber-reports/cucumber.json";
    private static String GIT_BRANCH_NAME = "";
    private static final String CUCUMBER_REPORT_PLATFORM_NAME = System.getProperty("os.name");
    private static String BROWSER_VERSION = "";


    //Methods
    public static int getPageLoadWait() {
        return PAGE_LOAD_WAIT;
    }

    public static int getElementLoadWait() {
        return ELEMENT_LOAD_WAIT;
    }

    public static String getConfigFilePath() {
        return CONFIG_FILE_PATH;
    }

    public static String getAllureEnvironmentProperties() {
        return ALLURE_Environment_PROPERTIES_PATH;
    }

    public static String getCucumberReportFolderPathPath() {
        return CUCUMBER_REPORT_FOLDER_PATH;
    }

    public static String getGitBranchName() {
        if (GIT_BRANCH_NAME.isEmpty()) {
            GIT_BRANCH_NAME = findGitBranchName();
        }
        return GIT_BRANCH_NAME;
    }

    public static String getCucumberJsonFilePath() {
        return CUCUMBER_JSON_FILE_PATH;
    }

    public static String getCucumberHtmlFilePath() {
        return CUCUMBER_HTML_FILE_PATH;
    }

    public static String getCucumberXmlFilePath() {
        return CUCUMBER_XML_FILE_PATH;
    }

    public static String getCucumberReportPlatformName() {
        return CUCUMBER_REPORT_PLATFORM_NAME;
    }

    public static String getBrowserName() {
        return PropertyUtils.get("BROWSER_TYPE");
    }

    public static String getBrowserVersion() {
        if (BROWSER_VERSION.equals("")) {
            extractBrowserVersion();
        }
        return BROWSER_VERSION;
    }

    /**
     * This method will extract the browser version using JavascriptExecutor
     */
    public static void extractBrowserVersion() {

        String script = "return navigator.userAgent;";
        String userAgent = ((JavascriptExecutor) DriverManager.getDriver()).executeScript(script).toString();

        switch (getBrowserName()) {
            case "firefox":
                BROWSER_VERSION = userAgent.split("Firefox/")[1].trim();
                break;
            case "chrome":
                BROWSER_VERSION = userAgent.split("Chrome/")[1].trim().split(" ")[0].trim();
                break;
            case "edge":
                BROWSER_VERSION = userAgent.split("Edg/")[1].trim();
                break;
            //TODO: Need to implement logic to extract safari browser version
            case "safari":
                BROWSER_VERSION = "Loading...";
                break;
            default:
                BROWSER_VERSION = "NOT FOUND!";
        }

    }

    private static String findGitBranchName() {
        try {
            // Run the 'git symbolic-ref --short HEAD' command
            Process process = Runtime.getRuntime().exec("git symbolic-ref --short HEAD");

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String branchName = reader.readLine();

            // Print the branch name
            log.debug("Current Git branch: " + branchName);
            return branchName;

        } catch (IOException e) {
            e.printStackTrace();
            return "*** GIT_BRANCH_NAME_NOT_FOUND :( ***";
        }
    }

    /**
     * This method will return the value of the variable defined in the FrameworkConstants class
     * For this to work the variable must have public access modifier
     *
     * @param variable name of the variable
     * @return value of the variable
     */
    public static String getGlobalVariable(String variable) {

        log.debug(String.format("Searching for global variable value of '%s'", variable));
        if (variable.startsWith("_")) {
            try {
                Field field = FrameworkConstants.class.getField(variable);
                String value = field.get(null).toString();
                log.debug(String.format("Found value='%s' for variable='%s'", value, variable));
                return value;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                return variable;
            }
        }
        log.debug(String.format("'%s' is not defined as a Global variable", variable));
        return variable;
    }

}
