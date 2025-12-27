package com.pratikum.testing.otomation.test.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class DemoblazeFormAndFlowTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
    }

    @Test(description = "Negative Test - Login dengan form kosong")
    public void loginEmptyForm_shouldShowAlert() {
        driver.findElement(By.id("login2")).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Log in']"))).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains("Please fill out"),
                "Alert tidak muncul saat login kosong");

        alert.accept();
    }

    @Test(description = "Negative Test - Login dengan credential invalid")
    public void loginInvalidCredential_shouldFail() {
        driver.findElement(By.id("login2")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")))
                .sendKeys("invalidUser");

        driver.findElement(By.id("loginpassword"))
                .sendKeys("wrongPassword");

        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().toLowerCase().contains("wrong"),
                "Alert error login tidak sesuai");

        alert.accept();
    }

    @Test(description = "Positive Test - Login berhasil")
    public void loginValidCredential_shouldSuccess() {
        driver.findElement(By.id("login2")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")))
                .sendKeys("testuser");

        driver.findElement(By.id("loginpassword"))
                .sendKeys("test123");

        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        WebElement userLabel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));

        Assert.assertTrue(userLabel.isDisplayed(),
                "User tidak berhasil login");
    }

    @Test(description = "Add product ke cart dan validasi alert")
    public void addProductToCart_shouldSuccess() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Samsung galaxy s6"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Add to cart"))).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "Product added",
                "Alert add to cart tidak sesuai");

        alert.accept();
    }

    @Test(description = "Validasi cart berisi produk yang ditambahkan")
    public void cartShouldContainProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Samsung galaxy s6"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Add to cart"))).click();

        wait.until(ExpectedConditions.alertIsPresent()).accept();

        driver.findElement(By.id("cartur")).click();

        WebElement cartContent = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        Assert.assertTrue(cartContent.getText().contains("Samsung"),
                "Produk tidak ditemukan di cart");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
