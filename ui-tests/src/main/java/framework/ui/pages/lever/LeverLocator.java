package framework.ui.pages.lever;

import org.openqa.selenium.By;

public final class LeverLocator {


    // POSITION LIST PAGE
    public static final By INSIDER_LOGO = By.cssSelector("img[alt='Insider One logo']");
    public static final By FILTER_BAR = By.cssSelector("div.filter-bar");
    public static final By TITLE_OF_POSTGROUP = By.xpath("//div[contains(@class,'posting-category-title') and text()='Quality Assurance']");

    // FILTERS
    public static final By LOCATION_FILTER_ALL_BUTTON = By.xpath("//div[@role='button' and contains(@aria-label,'Filter by Location:')]");

    public static By filterDropdownOption(String location) {
        return By.xpath("//div[contains(@class,'filter-popup') and contains(@style,'display: block')]//a[normalize-space()='" + location + "']");
    }

    // POSTING ROWS
    public static final By POSTING_ROW = By.cssSelector("div.posting");
    public static final By POSTING_CARD_TITLE = By.cssSelector("a.posting-title > h5");
    public static final By CATEGORY_LOCATION = By.cssSelector(".posting-categories .location");

    // Position Detail
    public static final By APPLY_LINK = By.cssSelector("a[href*='/apply']");

    // Application Form
    public static final By APPLICATION_FORM = By.cssSelector("form[class*='application'], form#application-form, .application-form form");
    public static final By EMAIL_FIELD = By.cssSelector("input[type='email'], input[name='email'], input[name*='email']");


}
