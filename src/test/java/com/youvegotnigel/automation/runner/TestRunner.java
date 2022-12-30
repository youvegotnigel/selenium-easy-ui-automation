package com.youvegotnigel.automation.runner;

import com.youvegotnigel.automation.driver.Driver;
import com.youvegotnigel.automation.utils.ReportHelper;
import io.cucumber.testng.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

/**
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
@CucumberOptions(
        features = "src/test/resources/features/default",
        glue = {"com/youvegotnigel/automation/stepdefs"},
        tags = "@regression and not @ignore",
        dryRun = false,
        monochrome = true,
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
                "html:target/cucumber-reports/cucumber.html",
                "junit:target/cucumber-reports/cucumber.xml",
                "json:target/cucumber-reports/cucumber.json"
        })
public class TestRunner extends AbstractTestNGCucumberTests{
    private TestNGCucumberRunner testNGCucumberRunner;
    public static final Logger log = LogManager.getLogger(TestRunner.class.getName());

    @BeforeClass(alwaysRun = true)
    @Override
    public void setUpClass(){
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    @Override
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        // the 'featureWrapper' parameter solely exists to display the feature
        // file in a test report
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider(parallel = true)
    @Override
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    @Override
    public void tearDownClass() {

        testNGCucumberRunner.finish();
        try {
            ReportHelper.generateCucumberReport();
            Driver.quitDriver();
        }catch (Exception e){
            log.warn("No feature files found");
            log.error(e.getStackTrace());
        }
    }

}
