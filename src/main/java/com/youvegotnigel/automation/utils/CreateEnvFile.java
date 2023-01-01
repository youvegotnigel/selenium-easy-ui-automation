package com.youvegotnigel.automation.utils;

import com.youvegotnigel.automation.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public final class CreateEnvFile {

    private static Properties properties = new Properties();
    private static final Logger log = LogManager.getLogger(CreateEnvFile.class.getName());

    /**
     * Private constructor to avoid external instantiation
     */
    private CreateEnvFile() {}

    public static void createFile() {

        properties.setProperty("Branch", FrameworkConstants.getGitBranchName());
        properties.setProperty("Browser Version", FrameworkConstants.getBrowserVersion());
        properties.setProperty("Browser", FrameworkConstants.getBrowserName());
        properties.setProperty("AUT", PropertyUtils.get("LOGIN_URL"));

        FileWriter writer = null;
        try {
            writer = new FileWriter(FrameworkConstants.getAllureEnvironmentProperties());
            properties.store(writer, "youvegotnigel");
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}