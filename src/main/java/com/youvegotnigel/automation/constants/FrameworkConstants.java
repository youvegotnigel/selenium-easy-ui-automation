package com.youvegotnigel.automation.constants;

public class FrameworkConstants {

    /**
     * Private constructor to avoid external instantiation
     */
    private FrameworkConstants() {

    }

    private static final int EXPLICIT_WAIT = 10;
    private static final String RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources";
    private static final String CONFIG_FILE_PATH = RESOURCES_PATH + "/config/config.properties";


    public static int getExplicitwait() {
        return EXPLICIT_WAIT;
    }

    public static String getConfigFilePath() {
        return CONFIG_FILE_PATH;
    }

}
