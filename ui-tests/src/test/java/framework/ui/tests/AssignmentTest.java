package framework.ui.tests;

import framework.ui.testcomponents.BaseTest;
import org.testng.annotations.Test;

public class AssignmentTest extends BaseTest {

    @Test(description = "Verify QA roles on Insider careers and reach the Lever application form")
    public void qaJobApplicationFlowTest() {
        pages.homeValidation().validateAllMainBlocksVisible();

        pages.careersAction().navigateOpenRolesPage();
        pages.careersAction().expandAllTeamCards();
        pages.careersAction().clickQaOpenPositions();

        pages.leverValidation().validateQaJobListingsPageLoaded();

        pages.leverAction().validateAllQaJobsWithLocation("Istanbul, Turkiye");

        pages.leverAction().applyToFirstPosting();
    }
}
