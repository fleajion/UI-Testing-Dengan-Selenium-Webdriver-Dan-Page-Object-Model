package com.pratikum.testing.otomation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage
 * =====================================================
 * QA Senior Style Base Class
 * - Centralized reusable actions
 * - Strong waiting strategy
 * - Stable, readable, maintainable automation
 * - Logging + screenshot on failure
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final int DEFAULT_TIMEOUT = 15;

    // =========================
    // CONSTRUCTOR
    // =========================
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    // =========================
    // WAIT HELPERS
    // =========================
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForPageTitle(String title) {
        wait.until(ExpectedConditions.titleContains(title));
    }

    protected void waitForUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }

    // =========================
    // ACTION HELPERS
    // =========================
    protected void click(By locator) {
        try {
            waitForClickable(locator).click();
            logAction("Click on element: " + locator);
        } catch (Exception e) {
            takeScreenshot("click_failure");
            throw new RuntimeException("Failed to click on element: " + locator, e);
        }
    }

    protected void type(By locator, String text) {
        try {
            WebElement element = waitForVisible(locator);
            element.clear();
            element.sendKeys(text);
            logAction("Typed '" + text + "' into element: " + locator);
        } catch (Exception e) {
            takeScreenshot("type_failure");
            throw new RuntimeException("Failed to type into element: " + locator, e);
        }
    }

    protected String getText(By locator) {
        try {
            String text = waitForVisible(locator).getText();
            logAction("Get text '" + text + "' from element: " + locator);
            return text;
        } catch (Exception e) {
            takeScreenshot("gettext_failure");
            throw new RuntimeException("Failed to get text from element: " + locator, e);
        }
    }

    protected boolean isDisplayed(By locator) {
        try {
            boolean displayed = waitForVisible(locator).isDisplayed();
            logAction("Element displayed check: " + locator + " -> " + displayed);
            return displayed;
        } catch (TimeoutException e) {
            logAction("Element not displayed (timeout): " + locator);
            return false;
        }
    }

    // =========================
    // ALERT HANDLING
    // =========================
    protected String acceptAlertAndGetText() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String message = alert.getText();
            alert.accept();
            logAction("Accepted alert with message: " + message);
            return message;
        } catch (TimeoutException e) {
            takeScreenshot("alert_failure");
            throw new RuntimeException("No alert present", e);
        }
    }

    protected void dismissAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
            logAction("Dismissed alert");
        } catch (TimeoutException e) {
            takeScreenshot("alert_failure");
            throw new RuntimeException("No alert present", e);
        }
    }

    // =========================
    // NAVIGATION
    // =========================
    protected void navigateTo(String url) {
        driver.get(url);
        logAction("Navigated to URL: " + url);
    }

    protected String getCurrentUrl() {
        String currentUrl = driver.getCurrentUrl();
        logAction("Current URL: " + currentUrl);
        return currentUrl;
    }

    // =========================
    // ASSERTION SUPPORT
    // =========================
    protected boolean containsIgnoreCase(String actual, String expected) {
        boolean result = actual != null && actual.toLowerCase().contains(expected.toLowerCase());
        logAction("Contains ignore case check: actual='" + actual + "', expected='" + expected + "' -> " + result);
        return result;
    }

    // =========================
    // STABILITY & RETRY UTILITY
    // =========================
    protected void safeClick(By locator) {
        try {
            click(locator);
        } catch (Exception e) {
            logAction("Retry click for element: " + locator);
            click(locator); // retry once
        }
    }

    // =========================
    // SCREENSHOT UTILITY
    // =========================
    protected void takeScreenshot(String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            // Integrate with report or Allure
            System.out.println("Screenshot captured: " + name);
        } catch (WebDriverException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    // =========================
    // LOGGING UTILITY
    // =========================
    protected void logAction(String message) {
        // Bisa diganti dengan logger profesional seperti log4j atau slf4j
        System.out.println("[ACTION] " + message);
    }

    public abstract void search(String samsung);

    public abstract int getSearchResultCount();

    public abstract String getSearchMessage();

    public abstract String getProductNameByIndex(int i);

    public abstract void goToHomePage();

    public abstract void addToCart(int i);

    public abstract String getCartItemCount();

    public abstract String getTotal();

    public abstract boolean search();

    public abstract void continueShopping();

    public abstract boolean isEmpty();

    public abstract void checkout();

    public abstract void fillBillingAddress(String name, String country, String city, String card, String month, String year);

    public abstract void placeOrder();

    public abstract void continueFromBilling();

    public abstract String getFirstValidationError();

    public abstract void completePurchase(String finalTester, String s);

    public abstract String getConfirmation();

    public void addToCart() {
    }
}
