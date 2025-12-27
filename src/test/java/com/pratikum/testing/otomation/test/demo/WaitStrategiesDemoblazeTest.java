package com.pratikum.testing.otomation.test.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.NoSuchElementException;

public class WaitStrategiesDemoblazeTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://www.demoblaze.com/");
    }

    // ===============================
    // POSITIVE LOGIN – EXPLICIT WAIT
    // ===============================
    @Test(priority = 1, description = "Login sukses dengan Explicit Wait")
    public void login_shouldSuccess_usingExplicitWait() {
        System.out.println("\n=== LOGIN POSITIVE – EXPLICIT WAIT ===");

        driver.findElement(By.id("login2")).click();

        WebElement username = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        WebElement password = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword")));

        username.sendKeys("testuser");
        password.sendKeys("testpass");

        WebElement loginBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Log in']")));
        loginBtn.click();

        WebElement welcome = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));

        Assert.assertTrue(welcome.getText().contains("Welcome"), "Login failed – welcome text not found");

        System.out.println("LOGIN SUCCESS – Explicit Wait works correctly");
    }

    // ===============================
    // NEGATIVE LOGIN – ALERT WAIT
    // ===============================
    @Test(priority = 2, description = "Login gagal dengan field kosong – Alert Wait")
    public void login_emptyField_shouldShowAlert() {
        System.out.println("\n=== LOGIN NEGATIVE – ALERT WAIT ===");

        driver.findElement(By.id("login2")).click();

        WebElement loginBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Log in']")));
        loginBtn.click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        Assert.assertTrue(alert.getText().contains("Please fill"), "Expected validation alert not shown");
        alert.accept();

        System.out.println("NEGATIVE LOGIN PASSED – Alert detected");
    }

    // ===============================
    // ADD TO CART – EXPLICIT WAIT + ALERT
    // ===============================
    @Test(priority = 3, description = "Add to Cart berhasil dengan wait strategy")
    public void addToCart_shouldSuccess_usingWait() {
        System.out.println("\n=== ADD TO CART – WAIT STRATEGY ===");

        WebElement product = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Samsung galaxy s6")));
        product.click();

        WebElement addToCart = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Add to cart")));
        addToCart.click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains("Product added"), "Product not added to cart");
        alert.accept();

        System.out.println("ADD TO CART SUCCESS");
    }

    // ===============================
    // FLUENT WAIT – CART PAGE LOAD
    // ===============================
    @Test(priority = 4, description = "Cart page load menggunakan Fluent Wait")
    public void cartPage_shouldLoad_usingFluentWait() {
        System.out.println("\n=== FLUENT WAIT – CART PAGE ===");

        driver.findElement(By.id("cartur")).click();

        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        WebElement cartTable = fluentWait.until(drv -> {
            WebElement table = drv.findElement(By.id("tbodyid"));
            return table.isDisplayed() ? table : null;
        });

        Assert.assertTrue(cartTable.isDisplayed(), "Cart table not displayed");
        System.out.println("CART PAGE LOADED – FluentWait success");
    }

    // ===============================
    // UI VALIDATION – HOMEPAGE PRODUCTS
    // ===============================
    @Test(priority = 5, description = "Validasi UI – Homepage product list")
    public void homepage_shouldHaveProducts_usingWait() {
        System.out.println("\n=== UI VALIDATION – PRODUCT LIST ===");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card")));

        int productCount = driver.findElements(By.cssSelector(".card")).size();
        Assert.assertTrue(productCount > 0, "No products displayed on homepage");

        System.out.println("PRODUCT COUNT: " + productCount);
    }

    // ===============================
    // CLEANUP
    // ===============================
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
