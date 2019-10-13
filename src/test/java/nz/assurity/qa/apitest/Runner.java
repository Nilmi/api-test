package nz.assurity.qa.apitest;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import nz.assurity.qa.apitest.util.ConfigurationManager;


@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {
                "nz.assurity.qa.apitest.stepdefinitions"
        },
        tags = {"@Regression"},
        plugin = {"nz.assurity.qa.apitest.extension.ExtentReportsFormatter:"}
)
public class Runner extends AbstractTestNGCucumberTests {

    static {
        init();
    }

    /**
     * Initializes configurations manager
     */
    public static void init() {
        ConfigurationManager.init();
    }
}
