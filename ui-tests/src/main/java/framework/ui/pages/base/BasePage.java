package framework.ui.pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final JavascriptExecutor js;
    protected final Actions actions;

    protected static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }

    // =========================
    // WAIT METHODS
    // =========================

    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public List<WebElement> waitForAllElementsToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForClickability(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    public void waitForAttributeContains(By locator, String attribute, String expectedValue) {
        wait.until(driver -> {
            String actualValue = driver.findElement(locator).getDomAttribute(attribute);
            return actualValue != null && actualValue.contains(expectedValue);
        });
    }


    // =========================
    // CLICK METHODS
    // =========================

    public void click(By locator) {
        WebElement element = waitForClickability(locator);
        try {
            element.click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            clickUsingJs(locator);
        }
    }

    public void click(WebElement element) {
        WebElement clickableElement = waitForClickability(element);
        try {
            clickableElement.click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            clickUsingJs(clickableElement);
        }
    }

    public void scrollToAndClick(By locator) {
        this.scrollUntilVisible(locator);

        try {
            click(locator);
        } catch (WebDriverException e) {
            clickUsingJs(locator);
        }
    }

    public void clickUsingJs(By locator) {
        WebElement element = waitForVisibility(locator);
        scrollIntoView(element);
        js.executeScript("arguments[0].click();", element);
    }

    public void clickUsingJs(WebElement element) {
        waitForVisibility(element);
        scrollIntoView(element);
        js.executeScript("arguments[0].click();", element);
    }

    public void clickIfPresent(By locator) {
        try {
            waitForClickability(locator);
            click(locator);
        } catch (TimeoutException ignored) {
            // Intended only for optional UI elements such as cookie banners or popups.
        }
    }

    // =========================
    // SCROLL METHODS
    // =========================

    public void scrollIntoView(By locator) {
        WebElement element = waitForVisibility(locator);
        scrollIntoView(element);
    }

    public void scrollIntoView(WebElement element) {
        waitForVisibility(element);
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
    }

    public void scrollUntilVisible(By locator) {
        scrollUntilPresent(locator); // checking DOM
        waitForVisibility(locator); // checking UI visibility
        scrollIntoView(locator); // make element center
    }

    private void scrollUntilPresent(By locator) {
        for (int i = 0; i < 10; i++) {
            if (isElementPresent(locator)) {
                return;
            }
            js.executeScript("window.scrollBy(0, 800);");
        }
    }

    // =========================
    // DISPLAY / PRESENCE METHODS
    // =========================

    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    // =========================
    // GENERIC ASSERTION HELPERS
    // =========================

    public void assertWithWait(ExpectedCondition<?> condition, String errorMessage) {
        try {
            wait.until(condition);
        } catch (TimeoutException e) {
            Assert.fail(errorMessage);
        }
    }

    public void assertElementVisible(By locator, String errorMessage) {
        scrollUntilVisible(locator);
        assertWithWait(ExpectedConditions.visibilityOfElementLocated(locator), errorMessage);
    }

    public void assertUrlContains(String expectedText, String errorMessage) {
        assertWithWait(ExpectedConditions.urlContains(expectedText), errorMessage);
    }

    public void assertAttributeContains(By locator, String attribute, String expectedValue, String errorMessage) {
        try {
            wait.until(driver -> {
                String actualValue = driver.findElement(locator).getDomAttribute(attribute);
                return actualValue != null && actualValue.contains(expectedValue);
            });
        } catch (TimeoutException e) {
            Assert.fail(errorMessage);
        }
    }
}