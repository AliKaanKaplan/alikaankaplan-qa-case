package framework.ui.pages.careers;

import framework.ui.config.ConfigReader;
import framework.ui.config.SiteUrls;
import framework.ui.pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class CareersAction extends BasePage {

    private final CareersValidation careersValidation;

    public CareersAction(WebDriver driver) {
        super(driver);
        this.careersValidation = new CareersValidation(driver);
    }

    public void navigateOpenRolesPage() {
        driver.get(ConfigReader.getUrl(SiteUrls.CAREERS_OPEN_ROLES));
        careersValidation.validateExploreRolesSection();

        waitForVisibility(CareersLocator.OPEN_ROLES_CONTAINER);
        waitForVisibility(CareersLocator.EXPLORE_OPEN_ROLES_HEADING);
    }


    public void expandAllTeamCards(){
        scrollToAndClick(CareersLocator.SEE_ALL_TEAMS);
        waitForAllElementsToBeVisible(CareersLocator.ALL_TEAMS_CARDS);

        List<String> expectedCategories = List.of(
                "Customer Education",
                "Sales",
                "Finance & Business Support",
                "People and Culture",
                "Marketing",
                "Software Development",
                "Purchasing & Operations",
                "Product Management",
                "Quality Assurance",
                "Customer Success",
                "Sales Operations",
                "Business Intelligence",
                "Product Design",
                "CEO’s Executive Office"
        );

        List<WebElement> cards = waitForAllElementsToBeVisible(CareersLocator.ALL_TEAMS_CARDS);

        List<String> actualCategories = cards.stream().map(e -> e.getText().trim()).toList();

        Assert.assertTrue(actualCategories.containsAll(expectedCategories), "Missing categories");
        Assert.assertEquals(actualCategories.size(), expectedCategories.size(), "Extra or missing categories detected");
    }

    public void clickQaOpenPositions() {
        waitForAttributeContains(CareersLocator.QA_OPEN_POSITIONS_LINK,"href","lever.co");
        click(CareersLocator.QA_OPEN_POSITIONS_LINK);
    }
}
