package com.pratikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class untuk semua Page Objects
 * Mengandung reusable methods yang dipakai semua page
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Constructor untuk inisialisasi
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this); // Wajib untuk @FindBy
    }

    // Method untuk menunggu element bisa diklik
    protected void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Method untuk menunggu element terlihat
    protected void waitForVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Method untuk mengisi text field
    protected void enterText(WebElement element, String text) {
        waitForVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    // Method untuk klik element
    protected void click(WebElement element) {
        waitForClickable(element);
        element.click();
    }

    // Alias method untuk klik element - untuk konsistensi
    protected void clickElement(WebElement element) {
        click(element);
    }

    // Method untuk mendapatkan text dari element
    protected String getText(WebElement element) {
        waitForVisible(element);
        return element.getText();
    }

    // Alias method untuk getText
    protected String getElementText(WebElement element) {
        return getText(element);
    }

    // Method untuk cek element ditampilkan
    protected boolean isDisplayed(WebElement element) {
        try {
            waitForVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Alias method untuk isDisplayed
    protected boolean isElementDisplayed(WebElement element) {
        return isDisplayed(element);
    }

    // Method untuk navigasi ke URL
    protected void navigateTo(String url) {
        driver.get(url);
    }

    public void clickProduct() {

    }
}