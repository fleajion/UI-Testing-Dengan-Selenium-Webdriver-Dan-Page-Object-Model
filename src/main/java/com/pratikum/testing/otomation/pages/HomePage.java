package com.pratikum.testing.otomation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * HomePage - Fixed Version
 */
public class HomePage extends BasePage {

    // =========================
    // HEADER & NAVIGATION
    // =========================
    @FindBy(className = "navbar-brand")
    private WebElement logo;

    @FindBy(id = "login2")
    private WebElement loginLink;

    @FindBy(id = "signin2")
    private WebElement registerLink;

    @FindBy(id = "nameofuser")
    private WebElement accountLink;

    @FindBy(id = "logout2")
    private WebElement logoutLink;

    // =========================
    // PRODUCT LIST
    // =========================
    @FindBy(css = ".card-title a")
    private List<WebElement> productTitles;

    @FindBy(css = ".card-footer .btn")
    private List<WebElement> addToCartButtons;

    @FindBy(id = "cartur")
    private WebElement cartLink;

    // =========================
    // CONSTRUCTOR
    // =========================
    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * @param samsung
     */
    @Override
    public void search(String samsung) {

    }

    /**
     * @return
     */
    @Override
    public int getSearchResultCount() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public String getSearchMessage() {
        return "";
    }

    /**
     * @param i
     * @return
     */
    @Override
    public String getProductNameByIndex(int i) {
        return "";
    }

    /**
     *
     */
    @Override
    public void goToHomePage() {

    }

    /**
     * @param i
     */
    @Override
    public void addToCart(int i) {

    }

    /**
     * @return
     */
    @Override
    public String getCartItemCount() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public String getTotal() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public boolean search() {
        return false;
    }

    /**
     *
     */
    @Override
    public void continueShopping() {

    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     *
     */
    @Override
    public void checkout() {

    }

    /**
     * @param name
     * @param country
     * @param city
     * @param card
     * @param month
     * @param year
     */
    @Override
    public void fillBillingAddress(String name, String country, String city, String card, String month, String year) {

    }

    /**
     *
     */
    @Override
    public void placeOrder() {

    }

    /**
     *
     */
    @Override
    public void continueFromBilling() {

    }

    /**
     * @return
     */
    @Override
    public String getFirstValidationError() {
        return "";
    }

    /**
     * @param finalTester
     * @param s
     */
    @Override
    public void completePurchase(String finalTester, String s) {

    }

    /**
     * @return
     */
    @Override
    public String getConfirmation() {
        return "";
    }

    // =========================
    // PAGE NAVIGATION
    // =========================
    public void openHomePage() {
        navigateTo("https://www.demoblaze.com/");
        // Perbaikan: Pastikan BasePage memiliki waitForVisible(By) atau gunakan wait langsung
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("navbar-brand")));
        logAction("Opened Home Page");
    }

    public boolean isHomePageLoaded() {
        // Perbaikan: Gunakan .isDisplayed() langsung dari WebElement
        boolean loaded = false;
        try {
            wait.until(ExpectedConditions.visibilityOf(logo));
            loaded = logo.isDisplayed();
        } catch (Exception e) {
            loaded = false;
        }
        logAction("Home Page loaded: " + loaded);
        return loaded;
    }

    // =========================
    // PRODUCT ACTIONS
    // =========================
    public void clickProductByIndex(int index) {
        validateIndex(index, productTitles.size());
        // Gunakan safeClick yang menerima WebElement
        safeClick(productTitles.get(index), "Product Click Index " + index);
    }

    public void addProductToCartByIndex(int index) {
        validateIndex(index, addToCartButtons.size());
        safeClick(addToCartButtons.get(index), "Add to Cart Index " + index);
        logAction("Clicked Add to Cart for product index " + index);
        waitUntilAlertPresent();
    }

    public void goToCart() {
        safeClick(cartLink, "Go to Cart");
    }

    // =========================
    // AUTHENTICATION
    // =========================
    public boolean isUserLoggedIn() {
        // Perbaikan: Jangan gunakan isDisplayed(accountLink) jika BasePage minta 'By'
        try {
            return accountLink.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickLogin() {
        safeClick(loginLink, "Login Link");
    }

    public void clickRegister() {
        safeClick(registerLink, "Register Link");
    }

    public void logout() {
        // Perbaikan logika pengecekan display
        try {
            if (logoutLink.isDisplayed()) {
                safeClick(logoutLink, "Logout Link");
            }
        } catch (Exception e) {
            logAction("Logout link not visible or already logged out");
        }
    }

    // =========================
    // UTILITY METHODS
    // =========================
    private void validateIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Invalid index: " + index + ". Size: " + size);
        }
    }

    private void waitUntilAlertPresent() {
        try {
            // Perbaikan: Pastikan metode di BasePage sesuai atau gunakan driver.switchTo()
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            logAction("Alert handled with text: " + alertText);
        } catch (TimeoutException e) {
            logAction("No alert present after Add to Cart");
        }
    }

    // Penambahan helper method agar konsisten dengan class sebelumnya
    private void safeClick(WebElement element, String actionName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logAction("Clicked: " + actionName);
        } catch (Exception e) {
            takeScreenshot(actionName + "_click_failure");
            throw new RuntimeException("Failed to click: " + actionName, e);
        }
    }
}