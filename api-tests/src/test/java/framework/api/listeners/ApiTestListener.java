package framework.api.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import framework.api.report.ExtentReportManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ApiTestListener implements ITestListener {

    private static final Logger LOG = LoggerFactory.getLogger(ApiTestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        if (description == null || description.isBlank()) {
            description = "(no description)";
        }
        String groups = formatGroups(result);
        LOG.info("=== SCENARIO START: {}.{} | {} | groups=[{}] ===", className, methodName, description, groups);

        ExtentTest test = ExtentReportManager.getInstance()
                .createTest(methodName, description)
                .assignCategory(result.getMethod().getGroups());
        ExtentReportManager.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("Test PASSED: {}", result.getMethod().getMethodName());
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS,
                    MarkupHelper.createLabel("PASSED", ExtentColor.GREEN));
        }
        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("Test FAILED: {}", result.getMethod().getMethodName());
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.FAIL,
                    MarkupHelper.createLabel("FAILED", ExtentColor.RED));
            test.fail(result.getThrowable());
        }
        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("Test SKIPPED: {}", result.getMethod().getMethodName());
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.SKIP,
                    MarkupHelper.createLabel("SKIPPED", ExtentColor.ORANGE));
            if (result.getThrowable() != null) {
                test.skip(result.getThrowable());
            }
        }
        ExtentReportManager.removeTest();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flush();
    }

    private static String formatGroups(ITestResult result) {
        String[] groups = result.getMethod().getGroups();
        if (groups == null || groups.length == 0) {
            return "none";
        }
        return String.join(", ", groups);
    }
}
