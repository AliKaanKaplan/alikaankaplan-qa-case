package framework.ui.testcomponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public final class ExtentReportManager {

    private static ExtentReports EXTENT;
    private static final ThreadLocal<ExtentTest> CURRENT_TEST = new ThreadLocal<>();

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (EXTENT == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
            spark.config().setDocumentTitle("UI Test Report");
            spark.config().setReportName("Insider UI Test Results");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setEncoding("UTF-8");

            EXTENT = new ExtentReports();
            EXTENT.setSystemInfo("Project", "Insider UI Tests");
            EXTENT.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
            EXTENT.setSystemInfo("Java Version", System.getProperty("java.version"));
            EXTENT.attachReporter(spark);
        }
        return EXTENT;
    }

    public static void setTest(ExtentTest test) {
        CURRENT_TEST.set(test);
    }

    public static ExtentTest getTest() {
        return CURRENT_TEST.get();
    }

    public static void removeTest() {
        CURRENT_TEST.remove();
    }

    public static synchronized void flush() {
        if (EXTENT != null) {
            EXTENT.flush();
        }
    }
}
