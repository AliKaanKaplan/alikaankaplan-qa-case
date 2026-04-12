package framework.ui.pages.home;

import framework.ui.pages.base.BasePage;
import org.openqa.selenium.WebDriver;

public class HomeValidation extends BasePage {

    public HomeValidation(WebDriver driver) {
        super(driver);
    }

    public void validateAllMainBlocksVisible() {
        this.assertElementVisible(HomeLocator.HERO_SECTION, "Hero should be visible");
        this.assertElementVisible(HomeLocator.SOCIAL_PROOF_SECTION, "Social proof should be visible");
        this.assertElementVisible(HomeLocator.CORE_DIFFERENTIATORS_SECTION, "Core differentiators should be visible");
        this.assertElementVisible(HomeLocator.CAPABILITIES_SECTION, "Capabilities should be visible");
        this.assertElementVisible(HomeLocator.INSIDER_AI_SECTION, "Insider AI should be visible");
        this.assertElementVisible(HomeLocator.CHANNELS_SECTION, "Channels should be visible");
        this.assertElementVisible(HomeLocator.CASE_STUDY_SECTION, "Case study should be visible");
        this.assertElementVisible(HomeLocator.ANALYST_SECTION, "Analyst should be visible");
        this.assertElementVisible(HomeLocator.INTEGRATIONS_SECTION, "Integrations should be visible");
        this.assertElementVisible(HomeLocator.RESOURCES_SECTION, "Resources should be visible");
        this.assertElementVisible(HomeLocator.CALL_TO_ACTION_SECTION, "Call to action should be visible");
    }

}
