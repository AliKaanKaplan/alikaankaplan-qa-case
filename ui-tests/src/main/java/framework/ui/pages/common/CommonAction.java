package framework.ui.pages.common;

import framework.ui.pages.base.BasePage;
import org.openqa.selenium.WebDriver;

public class CommonAction extends BasePage {

    public CommonAction(WebDriver driver) {
        super(driver);
    }

    public void acceptCookieBannerIfPresent() {
        clickIfPresent(CommonLocator.COOKIE_ACCEPT_ALL);
    }
}