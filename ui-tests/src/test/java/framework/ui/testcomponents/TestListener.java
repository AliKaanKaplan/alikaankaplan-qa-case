package framework.ui.testcomponents;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import framework.ui.base.DriverFactory;
import framework.ui.utils.ScreenshotUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        if (description == null || description.isBlank()) {
            description = "(no description)";
        }
        LOGGER.info("Test STARTED: {}", methodName);

        ExtentTest test = ExtentReportManager.getInstance()
                .createTest(methodName, description);
        ExtentReportManager.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test PASSED: {}", result.getMethod().getMethodName());
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS,
                    MarkupHelper.createLabel("PASSED", ExtentColor.GREEN));
        }
        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        LOGGER.error("Test FAILED: {}", testName);

        ScreenshotUtil.takeScreenshot(testName);

        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.FAIL,
                    MarkupHelper.createLabel("FAILED", ExtentColor.RED));
            test.fail(result.getThrowable());
            attachScreenshot(test, testName);
        }
        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.warn("Test SKIPPED: {}", result.getMethod().getMethodName());
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

    private void attachScreenshot(ExtentTest test, String testName) {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver == null) return;
            String base64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(base64, testName + " - failure screenshot");
        } catch (Exception e) {
            LOGGER.warn("Could not attach screenshot to ExtentReport: {}", e.getMessage());
        }
    }
}
