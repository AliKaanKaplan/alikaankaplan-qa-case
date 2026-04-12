package framework.ui.managers;

import framework.ui.pages.careers.CareersAction;
import framework.ui.pages.careers.CareersValidation;
import framework.ui.pages.common.CommonAction;
import framework.ui.pages.home.HomeValidation;
import framework.ui.pages.lever.LeverAction;
import framework.ui.pages.lever.LeverValidation;
import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    private final WebDriver driver;

    private HomeValidation homeValidation;
    private CareersAction careersAction;
    private CareersValidation careersValidation;
    private CommonAction commonAction;
    private LeverAction leverAction;
    private LeverValidation leverValidation;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public HomeValidation homeValidation() {
        if (homeValidation == null) homeValidation = new HomeValidation(driver);
        return homeValidation;
    }

    public CareersAction careersAction() {
        if (careersAction == null) careersAction = new CareersAction(driver);
        return careersAction;
    }

    public CareersValidation careersValidation() {
        if (careersValidation == null) careersValidation = new CareersValidation(driver);
        return careersValidation;
    }

    public CommonAction commonAction() {
        if (commonAction == null) commonAction = new CommonAction(driver);
        return commonAction;
    }

    public LeverAction leverAction() {
        if (leverAction == null) leverAction = new LeverAction(driver);
        return leverAction;
    }

    public LeverValidation leverValidation() {
        if (leverValidation == null) leverValidation = new LeverValidation(driver);
        return leverValidation;
    }
}
