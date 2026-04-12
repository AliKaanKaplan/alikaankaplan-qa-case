package framework.ui.pages.careers;

import framework.ui.pages.base.BasePage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class CareersValidation extends BasePage {

    public CareersValidation(WebDriver driver) {
        super(driver);
    }

    public void validateExploreRolesSection() {
        wait.until(ExpectedConditions.urlContains("careers"));

        try {
            waitForVisibility(CareersLocator.OPEN_ROLES_CONTAINER);
        } catch (TimeoutException e) {
            Assert.fail("Explore Roles section should be visible");
        }
    }
}