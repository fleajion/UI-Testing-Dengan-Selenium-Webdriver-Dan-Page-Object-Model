package com.pratikum.testing.otomation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * LoginPage - Fixed Version
 * Perubahan: Hapus 'abstract' dan sesuaikan nama method agar cocok dengan Test Suite.
 */
public class LoginPage extends BasePage { // HAPUS 'abstract' di sini

    private final By loginMenu     = By.id("login2");
    private final By usernameField = By.id("loginusername");
    private final By passwordField = By.id("loginpassword");
    private final By loginButton   = By.xpath("//button[text()='Log in']");
    private final By logoutMenu    = By.id("logout2");
    private final By welcomeUser   = By.id("nameofuser");

    public LoginPage(WebDriver driver) {
        super(driver);
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

    // Method yang dibutuhkan oleh UserLoginTest.java:
    public void goToHomePage() {
        navigateTo("https://www.demoblaze.com/");
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

    public void goToLoginPage() {
        goToHomePage();
        openLoginPopup();
    }

    public String openLoginPopup() {
        safeClick(loginMenu);
        waitForVisible(usernameField);
        return "Login Popup Opened";
    }

    public void login(String username, String password) {
        // Jika popup belum terbuka, buka dulu
        if (!isDisplayed(usernameField)) {
            openLoginPopup();
        }
        enterTextSafe(usernameField, username, "Username");
        enterTextSafe(passwordField, password, "Password");
        safeClick(loginButton);
    }

    // Method yang dipanggil oleh UserLoginTest: loginPage.isLoginSuccess()
    public boolean isLoginSuccess() {
        try {
            return waitForVisible(logoutMenu).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Method yang dipanggil oleh UserLoginTest: loginPage.getLoginError()
    public String getLoginError() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String message = alert.getText();
            alert.accept();
            return message;
        } catch (TimeoutException e) {
            return "";
        }
    }

    // Method yang dipanggil oleh UserLoginTest: loginPage.isLogoutSuccess()
    public boolean isLogoutSuccess() {
        try {
            return waitForVisible(loginMenu).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void logout() {
        if (isDisplayed(logoutMenu)) {
            safeClick(logoutMenu);
        }
    }

    // =========================
    // HELPER METHODS (PRIVATE)
    // =========================
    private void enterTextSafe(By locator, String text, String fieldName) {
        WebElement element = waitForVisible(locator);
        element.clear();
        if (text != null && !text.isEmpty()) {
            element.sendKeys(text);
        }
    }
}