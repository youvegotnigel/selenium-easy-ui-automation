package com.youvegotnigel.automation.constants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Define the framework constants here
 *
 * Dec 29, 2022
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

    private static final int ELEMENT_LOAD_WAIT = 3;
    private static final int PAGE_LOAD_WAIT = 10;
    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources";
    private static final String CONFIG_FILE_PATH = RESOURCES_PATH + "/config/config.properties";
    private static final String ALLURE_Environment_PROPERTIES_PATH = "/allure-results/environment.properties";
    private static final String CUCUMBER_REPORT_FOLDER_PATH = System.getProperty("user.dir")+"/target/cucumber-reports";
    private static String GIT_BRANCH_NAME = "";


    public static int getPageLoadWait() {
        return PAGE_LOAD_WAIT;
    }
    public static int getElementLoadWait(){
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

    public static String getGitBranchName(){
        if(GIT_BRANCH_NAME.isEmpty()){
            GIT_BRANCH_NAME = findGitBranchName();
        }
        return GIT_BRANCH_NAME;
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
