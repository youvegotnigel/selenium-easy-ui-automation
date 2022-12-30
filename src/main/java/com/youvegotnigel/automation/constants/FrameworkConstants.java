package com.youvegotnigel.automation.constants;

import com.youvegotnigel.automation.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Define the framework constants here
 * <p>
 * Dec 29, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class FrameworkConstants {

    public static final Logger log = LogManager.getLogger(FrameworkConstants.class.getName());

    /**
     * Private constructor to avoid external instantiation
     */
    private FrameworkConstants() {

    }

    //Fields
    private static final int ELEMENT_LOAD_WAIT = 3;
    private static final int PAGE_LOAD_WAIT = 10;
    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources";
    private static final String CONFIG_FILE_PATH = RESOURCES_PATH + "/config/config.properties";
    private static final String ALLURE_Environment_PROPERTIES_PATH = "allure-results\\environment.properties";
    private static final String CUCUMBER_REPORT_FOLDER_PATH = System.getProperty("user.dir") + "/target/cucumber-reports";
    private static final String CUCUMBER_HTML_FILE_PATH = "html:target/cucumber-reports/cucumber.html";
    private static final String CUCUMBER_XML_FILE_PATH = "junit:target/cucumber-reports/cucumber.xml";
    private static final String CUCUMBER_JSON_FILE_PATH = "json:target/cucumber-reports/cucumber.json";
    private static String GIT_BRANCH_NAME = "";
    private static String CUCUMBER_REPORT_PLATFORM_NAME = "";
    private static String CUCUMBER_REPORT_BROWSER_NAME = "";
    private static String CUCUMBER_REPORT_BROWSER_VERSION = "";


    //Methods
    public static int getPageLoadWait() {
        return PAGE_LOAD_WAIT;
    }
    public static int getElementLoadWait() {return ELEMENT_LOAD_WAIT;}
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
        if (CUCUMBER_REPORT_PLATFORM_NAME.equals("")) {
            createReportData();
        }
        return CUCUMBER_REPORT_PLATFORM_NAME;
    }
    public static String getCucumberReportBrowserName() {
        if (CUCUMBER_REPORT_BROWSER_NAME.equals("")) {
            createReportData();
        }
        return CUCUMBER_REPORT_BROWSER_NAME;
    }
    public static String getCucumberReportBrowserVersion() {
        if (CUCUMBER_REPORT_BROWSER_VERSION.equals("")) {
            createReportData();
        }
        return CUCUMBER_REPORT_BROWSER_VERSION;
    }
    public static void createReportData() {
        if (CUCUMBER_REPORT_PLATFORM_NAME.equals("") || CUCUMBER_REPORT_BROWSER_NAME.equals("") || CUCUMBER_REPORT_BROWSER_VERSION.equals("")) {
            Capabilities caps = ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities();
            CUCUMBER_REPORT_PLATFORM_NAME = caps.getPlatform().toString();
            CUCUMBER_REPORT_BROWSER_NAME = caps.getBrowserName();
            CUCUMBER_REPORT_BROWSER_VERSION = caps.getVersion();
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

}
