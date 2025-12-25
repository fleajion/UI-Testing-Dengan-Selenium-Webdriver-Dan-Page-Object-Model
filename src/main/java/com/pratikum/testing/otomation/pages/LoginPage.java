package com.pratikum.testing.otomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model for Login Page
 * URL: https://the-internet.herokuapp.com/login
 */
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By flashMessage = By.id("flash");
    private By pageHeading = By.tagName("h2");
    private By logoutButton = By.cssSelector("a.button.secondary.radius");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Page Actions
    public void navigateToLoginPage() {
        driver.get("https://the-internet.herokuapp.com/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(usernameField));
    }

    public void enterUsername(String username) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        element.clear();
        element.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        element.click();
    }

    // Combined action method
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    // Method untuk login dengan remember me (dummy implementation untuk testing)
    public void loginWithRememberMe(String username, String password) {
        // Karena website ini tidak punya remember me, kita gunakan login biasa
        login(username, password);
    }

    // Method untuk logout
    public void logout() {
        try {
            WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
            logoutBtn.click();
        } catch (Exception e) {
            // Jika tidak ada tombol logout, mungkin sudah logout
        }
    }

    // Method untuk goToLoginPage (alias untuk navigateToLoginPage)
    public void goToLoginPage() {
        navigateToLoginPage();
    }

    // Verification methods
    public String getFlashMessage() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessage));
        return element.getText();
    }

    public String getPageHeading() {
        return driver.findElement(pageHeading).getText();
    }

    public boolean isLoginButtonDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Method untuk cek apakah login berhasil
    public boolean isLoginSuccess() {
        try {
            String flashMessageText = getFlashMessage().toLowerCase();
            return flashMessageText.contains("you logged into a secure area");
        } catch (Exception e) {
            return false;
        }
    }

    // Method untuk cek apakah logout berhasil
    public boolean isLogoutSuccess() {
        try {
            String flashMessageText = getFlashMessage().toLowerCase();
            return flashMessageText.contains("you logged out of the secure area") ||
                    flashMessageText.contains("login page");
        } catch (Exception e) {
            return false;
        }
    }

    // Method untuk mendapatkan error message login
    public String getLoginError() {
        try {
            return getFlashMessage();
        } catch (Exception e) {
            return "";
        }
    }

    // Method untuk mendapatkan error email (untuk website ini berarti error username)
    public String getEmailError() {
        try {
            String flashMessageText = getFlashMessage().toLowerCase();
            if (flashMessageText.contains("your username is invalid")) {
                return "Username is invalid";
            }
            return flashMessageText;
        } catch (Exception e) {
            return "";
        }
    }
}