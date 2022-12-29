package com.youvegotnigel.automation.constants;

/**
 * Define the framework constants here
 *
 * Dec 29, 2022
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class FrameworkConstants {

    /**
     * Private constructor to avoid external instantiation
     */
    private FrameworkConstants() {

    }

    private static final int EXPLICIT_WAIT = 10;
    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources";
    private static final String CONFIG_FILE_PATH = RESOURCES_PATH + "/config/config.properties";
    private static final String CUCUMBER_REPORT_FOLDER_PATH = System.getProperty("user.dir")+"/target/cucumber-reports";


    public static int getExplicitWait() {
        return EXPLICIT_WAIT;
    }

    public static String getConfigFilePath() {
        return CONFIG_FILE_PATH;
    }

    public static String getCucumberReportFolderPathPath() {
        return CUCUMBER_REPORT_FOLDER_PATH;
    }

}
