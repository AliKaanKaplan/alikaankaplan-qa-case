package framework.ui.testcomponents;

import framework.ui.base.DriverFactory;
import framework.ui.config.ConfigReader;
import framework.ui.config.SiteUrls;
import framework.ui.managers.PageObjectManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected PageObjectManager pages;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        LOGGER.info("Browser: {}", browser);

        DriverFactory.initDriver(browser);
        this.driver = DriverFactory.getDriver();
        this.pages = new PageObjectManager(driver);

        driver.get(ConfigReader.getBaseUrl() + SiteUrls.HOME);
        pages.commonAction().acceptCookieBannerIfPresent();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
