package com.pratikum.testing.otomation.test.demo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locator
    private By loginMenu = By.id("login2");
    private By usernameField = By.id("loginusername");
    private By passwordField = By.id("loginpassword");
    private By loginButton = By.xpath("//button[text()='Log in']");
    private By logoutMenu = By.id("logout2");
    private By nameUser = By.id("nameofuser");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Metod yang dicari oleh Test Anda:
    public void openHomePage() {
        driver.get("https://www.demoblaze.com/");
    }

    public void login(String username, String password) {
        driver.findElement(loginMenu).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public boolean isLoginSuccessful() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(nameUser)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLogoutVisible() {
        try {
            return driver.findElement(logoutMenu).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void logout() {
        driver.findElement(logoutMenu).click();
    }

    // Menangani Alert Error (Invalid/Empty Credentials)
    public boolean isInvalidCredentialError() {
        return checkAlertText("User does not exist.") || checkAlertText("Wrong password.");
    }

    public boolean isEmptyCredentialError() {
        return checkAlertText("Please fill out Username and Password.");
    }

    private boolean checkAlertText(String expectedText) {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            return alertText.contains(expectedText);
        } catch (Exception e) {
            return false;
        }
    }
}