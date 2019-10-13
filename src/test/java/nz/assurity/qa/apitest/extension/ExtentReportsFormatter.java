package nz.assurity.qa.apitest.extension;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import nz.assurity.qa.apitest.util.ConfigurationManager;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Cucumber framework related reporting listener.
 * This class generates the extent report.
 *
 * @author Nilmi
 */
public class ExtentReportsFormatter implements Reporter, Formatter {

    private static final ThreadLocal<ExtentTest> featureTest = new InheritableThreadLocal<>();
    private static final ThreadLocal<ExtentTest> scenarioOutline = new InheritableThreadLocal<>();
    private static final ThreadLocal<ExtentTest> scenario = new InheritableThreadLocal<>();
    private static final ThreadLocal<LinkedList<Step>> stepList = new InheritableThreadLocal<>();
    private static final ThreadLocal<ExtentTest> stepTest = new InheritableThreadLocal<>();
    private static ExtentReports extentReports;
    private boolean scenarioOutlineFlag;

    public ExtentReportsFormatter() {
        extentReports = new ExtentReports();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(new File(ConfigurationManager.REPORT_FOLDER_PATH, ConfigurationManager.REPORT_FILE_NAME));
        htmlReporter.loadXMLConfig(ConfigurationManager.REPORT_CONFIG_PATH);
        extentReports.attachReporter(htmlReporter);
        stepList.set(new LinkedList<>());
    }

    public static ThreadLocal<ExtentTest> getStepTest() {
        return stepTest;
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {

    }

    @Override
    public void uri(String uri) {

    }

    @Override
    public void feature(Feature feature) {
        ExtentTest extentTest = extentReports.createTest(com.aventstack.extentreports.gherkin.model.Feature.class,
                feature.getName());
        feature.getTags().stream().map(Tag::getName).forEach(extentTest::assignCategory);
        featureTest.set(extentTest);
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        scenarioOutlineFlag = true;
        ExtentTest node = featureTest.get()
                .createNode(com.aventstack.extentreports.gherkin.model.ScenarioOutline.class, scenarioOutline.getName());
        ExtentReportsFormatter.scenarioOutline.set(node);
    }

    @Override
    public void examples(Examples examples) {

    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        if (scenarioOutlineFlag) {
            scenarioOutlineFlag = false;
        }
        ExtentTest scenarioNode;
        if (scenarioOutline.get() != null && "Scenario Outline".equalsIgnoreCase(scenario.getKeyword().trim())) {
            scenarioNode = scenarioOutline.get().createNode(com.aventstack.extentreports.gherkin.model.Scenario.class,
                    scenario.getName());
        } else {
            scenarioNode = featureTest.get().createNode(com.aventstack.extentreports.gherkin.model.Scenario.class,
                    scenario.getName());
        }
        scenario.getTags().stream().map(Tag::getName).forEach(scenarioNode::assignCategory);
        ExtentReportsFormatter.scenario.set(scenarioNode);
    }

    @Override
    public void background(Background background) {

    }

    @Override
    public void scenario(Scenario scenario) {

    }

    @Override
    public void step(Step step) {
        if (!scenarioOutlineFlag) {
            stepList.get().add(step);
        }
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {

    }

    @Override
    public void done() {
        extentReports.flush();
    }

    @Override
    public void close() {

    }

    @Override
    public void eof() {

    }

    @Override
    public void before(Match match, Result result) {

    }

    @Override
    public void result(Result result) {
        if (scenarioOutlineFlag) {
            return;
        }
        if (Result.PASSED.equals(result.getStatus())) {
            stepTest.get().pass(Result.PASSED);
        } else if (Result.FAILED.equals(result.getStatus())) {
            stepTest.get().fail(result.getError());
        } else if (Result.SKIPPED.equals(result)) {
            stepTest.get().skip(Result.SKIPPED.getStatus());
        } else if (Result.UNDEFINED.equals(result)) {
            stepTest.get().skip(Result.UNDEFINED.getStatus());
        }
    }

    @Override
    public void after(Match match, Result result) {

    }

    @Override
    public void match(Match match) {
        Step step = stepList.get().poll();
        String[][] data = null;
        if ((step != null ? step.getRows() : null) != null) {
            List<DataTableRow> rows = step.getRows();
            int rowSize = rows.size();
            for (int i = 0; i < rowSize; i++) {
                DataTableRow dataTableRow = rows.get(i);
                List<String> cells = dataTableRow.getCells();
                int cellSize = cells.size();
                if (data == null) {
                    data = new String[rowSize][cellSize];
                }
                for (int j = 0; j < cellSize; j++) {
                    data[i][j] = cells.get(j);
                }
            }
        }

        ExtentTest scenarioTest = scenario.get();
        ExtentTest stepTest = null;

        try {
            if (step != null) {
                stepTest = scenarioTest.createNode(new GherkinKeyword(step.getKeyword()), step.getKeyword() + step.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (data != null) {
            Markup table = MarkupHelper.createTable(data);
            assert stepTest != null;
            stepTest.info(table);
        }

        ExtentReportsFormatter.stepTest.set(stepTest);
    }

    @Override
    public void embedding(String mimeType, byte[] data) {

    }

    @Override
    public void write(String text) {

    }

}
