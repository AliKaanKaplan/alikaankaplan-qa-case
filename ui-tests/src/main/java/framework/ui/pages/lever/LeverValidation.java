package framework.ui.pages.lever;

import framework.ui.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;

import java.util.List;
import java.util.Locale;

public class LeverValidation extends BasePage {

    public LeverValidation(WebDriver driver) {
        super(driver);
    }

    public void assertAllVisibleQaPostingsWithLocation(String location) {
        wait.until(ExpectedConditions.presenceOfElementLocated(LeverLocator.POSTING_CARD_TITLE));

        List<WebElement> postings = driver.findElements(LeverLocator.POSTING_ROW);
        Assert.assertFalse(postings.isEmpty(), "There is no QA Job post");

        for (int i = 0; i < postings.size(); i++) {
            WebElement row = postings.get(i);
            int displayIndex = i + 1;

            String title = extractText(row, LeverLocator.POSTING_CARD_TITLE);
            Assert.assertTrue(isQaTitle(title),
                    "Posting " + displayIndex + ". title is not QA related: " + title);

            String visibleLocation = extractText(row, LeverLocator.CATEGORY_LOCATION);
            Assert.assertTrue(visibleLocation.toLowerCase(Locale.ROOT).contains(location.toLowerCase(Locale.ROOT)),
                    "Posting " + displayIndex + ". has incorrect location: " + location + " vs " + visibleLocation);
        }
    }

    public void assertApplicationFormVisible() {
        assertUrlContains("/apply", "URL should contain /apply");

        assertWithWait(driver -> isElementDisplayed(LeverLocator.APPLICATION_FORM) || isElementDisplayed(LeverLocator.EMAIL_FIELD),
                "Application form or email field should be visible"
        );
    }

    private String extractText(WebElement parent, By childLocator) {
        List<WebElement> found = parent.findElements(childLocator);
        return found.isEmpty() ? "" : found.get(0).getText().trim();
    }

    private boolean isQaTitle(String title) {
        String lower = title.toLowerCase(Locale.ROOT);

        return lower.contains("qa")
                || lower.contains("quality assurance")
                || lower.contains("tester")
                || lower.contains("test engineer")
                || lower.contains("quality engineer");
    }


    public void validateQaJobListingsPageLoaded() {
        wait.until(ExpectedConditions.urlContains("jobs.lever.co"));
        this.assertElementVisible(LeverLocator.INSIDER_LOGO, "İnsider logo is not visible");
        this.assertElementVisible(LeverLocator.FILTER_BAR, "Filter bar should be visible");
        this.assertElementVisible(LeverLocator.TITLE_OF_POSTGROUP, "Title of group should be visible");
    }


}