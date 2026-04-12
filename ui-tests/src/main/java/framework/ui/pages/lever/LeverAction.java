package framework.ui.pages.lever;

import framework.ui.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.Assert;

import java.util.List;

public class LeverAction extends BasePage {

    private final LeverValidation leverValidation;

    public LeverAction(WebDriver driver) {
        super(driver);
        this.leverValidation = new LeverValidation(driver);
    }

    public void validateAllQaJobsWithLocation(String location) {
        this.selectLocation(location);
        leverValidation.assertAllVisibleQaPostingsWithLocation(location);
    }

    private void selectLocation(String location) {
        click(LeverLocator.LOCATION_FILTER_ALL_BUTTON);
        click(LeverLocator.filterDropdownOption(location));
        assertAttributeContains(LeverLocator.LOCATION_FILTER_ALL_BUTTON,"aria-label", location, "Aria-label attribute doesn't contains "+location);
    }

    public void applyToFirstPosting() {
        List<WebElement> postings = waitForAllElementsToBeVisible(LeverLocator.POSTING_ROW);
        Assert.assertFalse(postings.isEmpty(), "No job postings found on Lever board");
        click(postings.get(0));

        click(LeverLocator.APPLY_LINK);
        leverValidation.assertApplicationFormVisible();
    }

}
