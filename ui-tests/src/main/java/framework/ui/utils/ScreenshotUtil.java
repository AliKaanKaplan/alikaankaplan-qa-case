package framework.ui.utils;

import framework.ui.base.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    private static final Path DIR = Paths.get("target", "screenshots");

    public static void takeScreenshot(String testName) {
        try {
            Files.createDirectories(DIR);
            File src = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Files.copy(src.toPath(), DIR.resolve(testName + "_" + timestamp + ".png"));
        } catch (IOException e) {
            LoggerFactory.getLogger(ScreenshotUtil.class).error("Screenshot failed", e);
        }
    }
}
