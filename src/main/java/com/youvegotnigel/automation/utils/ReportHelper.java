package com.youvegotnigel.automation.utils;

import com.youvegotnigel.automation.constants.FrameworkConstants;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportHelper {

    public static void generateCucumberReport() {

        File reportOutputDirectory = new File("target");
        ArrayList<String> jsonFiles = new ArrayList<String>();
        jsonFiles.add("target/cucumber-reports/cucumber.json");

        String workingDir = System.getProperty("user.dir");
        String projectName = workingDir.substring(workingDir.lastIndexOf(File.separator)+1);

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.addClassifications("Platform", FrameworkConstants.getCucumberReportPlatformName());
        configuration.addClassifications("Browser", FrameworkConstants.getCucumberReportBrowserName());
        configuration.addClassifications("Browser Version", FrameworkConstants.getCucumberReportBrowserVersion());
        configuration.addClassifications("Branch", FrameworkConstants.getGitBranchName());

        // optionally add metadata presented on main page via properties file
        List<String> classificationFiles = new ArrayList<String>();
        classificationFiles.add(FrameworkConstants.getConfigFilePath());
        //configuration.addClassificationFiles(classificationFiles);

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
