package com.youvegotnigel.automation.utils;

import com.youvegotnigel.automation.constants.FrameworkConstants;
import com.youvegotnigel.automation.driver.DriverManager;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportHelper {

    public static void generateCucumberReport() {
        Capabilities caps = ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities();

        File reportOutputDirectory = new File("target");
        ArrayList<String> jsonFiles = new ArrayList<String>();
        jsonFiles.add("target/cucumber-reports/cucumber.json");

        String workingDir = System.getProperty("user.dir");
        String projectName = workingDir.substring(workingDir.lastIndexOf(File.separator)+1);

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.addClassifications("Platform", caps.getPlatform().toString());
        configuration.addClassifications("Browser", caps.getBrowserName());
        configuration.addClassifications("Browser Version", caps.getVersion());
        configuration.addClassifications("Branch", FrameworkConstants.getGitBranchName());

        // optionally add metadata presented on main page via properties file
        List<String> classificationFiles = new ArrayList<String>();
        classificationFiles.add(FrameworkConstants.getConfigFilePath());
        //configuration.addClassificationFiles(classificationFiles);

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
