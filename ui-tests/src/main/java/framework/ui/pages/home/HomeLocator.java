package framework.ui.pages.home;

import org.openqa.selenium.By;

public final class HomeLocator {

    private HomeLocator() {}

    public static final By HERO_SECTION =
            By.cssSelector("section.homepage-hero");

    public static final By SOCIAL_PROOF_SECTION =
            By.cssSelector("section.homepage-social-proof");

    public static final By CORE_DIFFERENTIATORS_SECTION =
            By.cssSelector("section.homepage-core-differentiators__style-2");

    public static final By CAPABILITIES_SECTION =
            By.cssSelector("section.homepage-capabilities");

    public static final By INSIDER_AI_SECTION =
            By.cssSelector("section.homepage-insider-one-ai");

    public static final By CHANNELS_SECTION =
            By.cssSelector("section.homepage-channels");

    public static final By CASE_STUDY_SECTION =
            By.cssSelector("section.homepage-case-study");

    public static final By ANALYST_SECTION =
            By.cssSelector("section.homepage-analyst");

    public static final By INTEGRATIONS_SECTION =
            By.cssSelector("section.homepage-integrations");

    public static final By RESOURCES_SECTION =
            By.cssSelector("section.homepage-resources");

    public static final By CALL_TO_ACTION_SECTION =
            By.cssSelector("section.homepage-call-to-action");
}