package com.pratikum.testing.otomation.test.demo;

import com.pratikum.testing.otomation.utils.ScreenshotUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * QA Senior – JavaScript Executor Tests
 * Coverage:
 * - Login via JS
 * - Add to cart via JS
 * - Empty field login validation
 * - UI elements verification
 * - Screenshot capture for QA review
 */
public class JavaScriptExecutorDemoblazeTest {

    private WebDriver driver;
    private JavascriptExecutor js;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.demoblaze.com/");
    }

    // =========================
    // POSITIVE TEST – LOGIN & ADD TO CART
    // =========================
    @Test(description = "Login with valid credentials and add product to cart using JS")
    public void loginAndAddToCart_shouldSuccess() {
        System.out.println("\n=== POSITIVE TEST: LOGIN & ADD TO CART ===");

        // Open login modal
        driver.findElement(By.id("login2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));

        // Input credentials via JS
        js.executeScript("document.getElementById('loginusername').value='testuser'");
        js.executeScript("document.getElementById('loginpassword').value='testpass'");

        ScreenshotUtil.captureScreenshot(driver, "login_filled");

        // Click login via JS
        WebElement loginBtn = driver.findElement(By.xpath("//button[text()='Log in']"));
        js.executeScript("arguments[0].click();", loginBtn);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
        ScreenshotUtil.captureScreenshot(driver, "login_success");

        Assert.assertTrue(
                driver.findElement(By.id("nameofuser")).getText().contains("Welcome"),
                "Login failed: Welcome message not displayed"
        );

        // Scroll to product & open
        WebElement product = driver.findElement(By.linkText("Samsung galaxy s6"));
        js.executeScript("arguments[0].scrollIntoView(true);", product);
        product.click();

        // Add to cart via JS
        WebElement addToCart = driver.findElement(By.linkText("Add to cart"));
        js.executeScript("arguments[0].click();", addToCart);

        // Validate alert
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        Assert.assertTrue(alert.getText().contains("Product added"), "Alert message incorrect");
        alert.accept();

        ScreenshotUtil.captureScreenshot(driver, "add_to_cart_success");
        System.out.println("TEST PASSED: Login & Add to Cart");
    }

    // =========================
    // NEGATIVE TEST – EMPTY LOGIN
    // =========================
    @Test(description = "Login attempt with empty fields should show alert")
    public void loginWithEmptyFields_shouldFail() {
        System.out.println("\n=== NEGATIVE TEST: EMPTY LOGIN ===");

        driver.findElement(By.id("login2")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));

        WebElement loginBtn = driver.findElement(By.xpath("//button[text()='Log in']"));
        js.executeScript("arguments[0].click();", loginBtn);

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();

        ScreenshotUtil.captureScreenshot(driver, "login_empty_failed");

        Assert.assertTrue(
                alert.getText().contains("Please fill"),
                "Validation message not shown for empty login"
        );

        alert.accept();
        System.out.println("NEGATIVE TEST PASSED");
    }

    // =========================
    // UI VALIDATION – PRODUCT LIST & TITLE
    // =========================
    @Test(description = "Validate UI elements are present and product list not empty")
    public void validateUIElements_shouldExist() {
        System.out.println("\n=== UI VALIDATION TEST ===");

        // Page title
        String title = (String) js.executeScript("return document.title;");
        Assert.assertEquals(title, "STORE", "Page title mismatch");

        // Product count
        Long productCount = (Long) js.executeScript("return document.querySelectorAll('.card').length;");
        Assert.assertTrue(productCount > 0, "Product list is empty");

        ScreenshotUtil.captureScreenshot(driver, "ui_validation");
        System.out.println("UI VALIDATION PASSED");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
