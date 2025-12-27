package com.pratikum.testing.otomation.test.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class AlertHandlingDemoblazeTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        // 1. Menggunakan WebDriverManager versi otomatis & Headless mode (opsional) agar lebih stabil di CI/CD
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // Mengatasi isu CDP pada Chrome versi baru

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // 2. Menaikkan timeout sedikit untuk mengantisipasi jaringan lambat
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://www.demoblaze.com/");
    }

    @Test(description = "Negative Test: Login with empty fields should trigger alert")
    public void login_emptyCredential_shouldShowAlert() {
        // Tunggu hingga landing page stabil
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();

        // Pastikan modal muncul sepenuhnya sebelum klik login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModal")));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Log in']"))).click();

        // Tunggu alert muncul
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText().toLowerCase();

        Assert.assertTrue(text.contains("please fill"), "Alert text mismatch: " + text);
        alert.accept();
    }

    @Test(description = "Negative Test: Login with invalid credentials should trigger alert")
    public void login_invalidCredential_shouldShowAlert() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();

        // Gunakan explicit wait pada setiap input field
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername"))).sendKeys("wrongUser12345");
        driver.findElement(By.id("loginpassword")).sendKeys("wrongPass");

        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText().toLowerCase();

        // Gabungkan kemungkinan pesan error dari server
        boolean isErrorMatch = alertText.contains("user does not exist") || alertText.contains("wrong password");
        Assert.assertTrue(isErrorMatch, "Unexpected alert text: " + alertText);

        alert.accept();
    }

    @Test(description = "Positive Test: Adding product to cart shows success alert")
    public void addToCart_shouldShowSuccessAlert() {
        // Tunggu elemen spesifik muncul (home page loading)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Samsung galaxy s6"))).click();

        // Pastikan sudah pindah ke detail produk
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add to cart']"))).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().toLowerCase().contains("product added"), "Success alert not shown");

        alert.accept();
    }

    @Test(description = "Test: Alert disappears after acceptance")
    public void alert_shouldDisappear_afterAccepted() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();

        // Pastikan tombol login di modal benar-benar clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Log in']"))).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        // Verifikasi alert benar-benar hilang sebelum test selesai
        boolean isAlertGone = wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
        Assert.assertTrue(isAlertGone, "Alert should be dismissed");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            // Gunakan quit() untuk membersihkan semua instance session
            driver.quit();
        }
    }
}