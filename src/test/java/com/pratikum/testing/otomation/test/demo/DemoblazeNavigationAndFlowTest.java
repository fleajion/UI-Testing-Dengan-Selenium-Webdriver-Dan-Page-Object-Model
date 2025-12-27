package com.pratikum.testing.otomation.test.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.logging.Logger;

public class DemoblazeNavigationAndFlowTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger =
            Logger.getLogger(DemoblazeNavigationAndFlowTest.class.getName());

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.demoblaze.com/");
    }

    @Test(description = "Navigation Test - Menu Category & Product Detail")
    public void navigationCategoryAndProduct() {
        logger.info("=== Navigation Category & Product ===");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Laptops"))).click();

        WebElement product =
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.linkText("Sony vaio i5")));

        Assert.assertTrue(product.isDisplayed(),
                "Produk laptop tidak tampil");

        product.click();

        WebElement title =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.className("name")));

        Assert.assertEquals(title.getText(), "Sony vaio i5",
                "Judul produk tidak sesuai");

        logger.info("Navigation test PASSED");
    }

    @Test(description = "Negative Test - Login dengan data kosong")
    public void loginEmptyForm() {
        logger.info("=== Login Empty Form ===");

        driver.findElement(By.id("login2")).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Log in']"))).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains("Please fill out"),
                "Alert login kosong tidak muncul");

        alert.accept();
    }

    @Test(description = "Add To Cart dan Validasi Alert")
    public void addToCartFlow() {
        logger.info("=== Add To Cart Flow ===");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Samsung galaxy s6"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Add to cart"))).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertEquals(alert.getText(), "Product added",
                "Alert add to cart tidak sesuai");

        alert.accept();
    }

    @Test(description = "Cart Page Validation")
    public void cartValidation() {
        logger.info("=== Cart Validation ===");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Samsung galaxy s6"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Add to cart"))).click();

        wait.until(ExpectedConditions.alertIsPresent()).accept();

        driver.findElement(By.id("cartur")).click();

        WebElement cartTable =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.id("tbodyid")));

        Assert.assertTrue(cartTable.getText().contains("Samsung"),
                "Produk tidak ada di cart");
    }

    @Test(description = "Checkout / Place Order - Negative Testing")
    public void placeOrderEmptyForm() {
        logger.info("=== Place Order Empty Form ===");

        driver.findElement(By.id("cartur")).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Place Order']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Purchase']"))).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().length() > 0,
                "Alert checkout kosong tidak muncul");

        alert.accept();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
