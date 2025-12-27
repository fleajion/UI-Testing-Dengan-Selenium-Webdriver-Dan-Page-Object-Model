package com.pratikum.testing.otomation.test.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class DemoblazeE2ETest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();

        // Perbaikan 1: Gunakan ChromeOptions untuk mengatasi isu stabilitas CDP 143
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Perbaikan 2: Menaikkan timeout ke 15-20 detik karena web Demoblaze sering lambat
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Perbaikan 3: URL diperbaiki (sebelumnya ada typo "https:/11")
        driver.get("https://www.demoblaze.com/");
    }

    @Test(description = "Negative Test - Login tanpa input username & password")
    public void loginEmptyCredential_shouldShowAlert() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();

        // Pastikan modal login sudah muncul sepenuhnya sebelum klik
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModal")));

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Log in']"))).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().toLowerCase().contains("please fill out"),
                "Alert message tidak sesuai untuk login kosong");

        alert.accept();
    }

    @Test(description = "Positive Test - Login valid dan tampil nama user")
    public void loginValid_shouldSuccess() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")))
                .sendKeys("testuser"); // Pastikan username ini terdaftar

        driver.findElement(By.id("loginpassword"))
                .sendKeys("test123");

        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        // Perbaikan 4: Tunggu hingga teks "Welcome" muncul di label username
        boolean isUserLoggedIn = wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("nameofuser"), "Welcome"));

        Assert.assertTrue(isUserLoggedIn, "User tidak berhasil login atau label welcome tidak muncul");
    }

    @Test(description = "Add Product to Cart dan validasi alert")
    public void addProductToCart_shouldSuccess() {
        // Tunggu halaman katalog dimuat
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Samsung galaxy s6"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Add to cart"))).click();

        // Handle Alert "Product added"
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains("Product added"),
                "Alert add to cart tidak sesuai");

        alert.accept();
    }

    @Test(description = "Validasi Cart berisi produk")
    public void cartShouldContainProduct() {
        // Langkah 1: Tambah produk
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Samsung galaxy s6"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add to cart"))).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        // Langkah 2: Buka cart
        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();

        // Perbaikan 5: Di Demoblaze, data cart dimuat via AJAX.
        // Tunggu hingga elemen spesifik di dalam tabel muncul (bukan hanya tabelnya)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[text()='Samsung galaxy s6']")));

        WebElement cartTable = driver.findElement(By.id("tbodyid"));
        Assert.assertTrue(cartTable.getText().contains("Samsung galaxy s6"),
                "Produk tidak ditemukan di cart setelah menunggu muatan AJAX");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}