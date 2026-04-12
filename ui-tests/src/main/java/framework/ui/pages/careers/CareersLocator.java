package framework.ui.pages.careers;

import org.openqa.selenium.By;

public final class CareersLocator {

    public static final By OPEN_ROLES_CONTAINER = By.xpath("//div[contains(@class,'insiderone-icon-cards-heading')]/../..//div[contains(@class,'inso-container')]");
    public static final By EXPLORE_OPEN_ROLES_HEADING = By.xpath("//div[contains(@class,'insiderone-icon-cards-heading')]//h2[contains(normalize-space(),'Explore open roles')]");
    public static final By SEE_ALL_TEAMS = By.cssSelector("a.inso-btn.see-more");
    public static final By ALL_TEAMS_CARDS = By.cssSelector("div.insiderone-icon-cards-grid-item h3");
    public static final By QA_OPEN_POSITIONS_LINK = By.cssSelector("[data-department='Quality Assurance'] a.insiderone-icon-cards-grid-item-btn");

}
