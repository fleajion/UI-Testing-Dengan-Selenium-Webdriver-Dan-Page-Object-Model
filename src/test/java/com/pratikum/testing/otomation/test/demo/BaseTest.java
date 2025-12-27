package com.pratikum.testing.otomation.test.demo;

import com.aventstack.extentreports.*;
import com.pratikum.testing.otomation.utils.ExtentReportManager;
import com.pratikum.testing.otomation.utils.ScreenshotUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions; // Tambahkan ini
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        extent = ExtentReportManager.createInstance("test-output/ExtentReport.html");
        extent.setSystemInfo("Project", "DemoBlaze E-Commerce Automation");
        extent.setSystemInfo("Tester", "QA Automation");
        extent.setSystemInfo("Environment", "Demo");
        System.out.println("=== TEST SUITE STARTED ===");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(Method method) {
        // 1. Pastikan WebDriverManager mengambil versi yang paling cocok secara otomatis
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // 2. Tambahkan argument stabilitas untuk mengatasi masalah CDP 143 dan memory
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-notifications");

        // Opsional: Aktifkan headless jika ingin berjalan lebih cepat di server/CI
        // options.addArguments("--headless=new");

        driver = new ChromeDriver(options);

        // 3. Pengaturan Timeout yang lebih seimbang
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
        // Hindari mencampur implicit wait besar dengan explicit wait. Gunakan nilai kecil saja (2-3 detik).
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        test = extent.createTest(method.getName());
        test.log(Status.INFO, "=== TEST STARTED: " + method.getName() + " ===");

        openDemoblaze();
    }

    protected void openDemoblaze() {
        int retry = 0;
        while (retry < 3) {
            try {
                driver.get("https://www.demoblaze.com/");

                // 4. Perbaikan sinkronisasi: Tunggu elemen spesifik muncul, bukan hanya document.readyState
                // Ini memastikan halaman benar-benar bisa digunakan sebelum test mulai
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navbarExample")));

                test.log(Status.INFO, "Demoblaze loaded successfully");
                return;
            } catch (Exception e) {
                retry++;
                test.log(Status.WARNING, "Retry loading Demoblaze: attempt " + retry + ". Error: " + e.getMessage());
            }
        }
        throw new RuntimeException("Demoblaze unreachable after retries");
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                test.log(Status.FAIL, "TEST FAILED");
                test.log(Status.FAIL, result.getThrowable());

                String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
                if (screenshotPath != null) {
                    test.addScreenCaptureFromPath(screenshotPath);
                }
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.log(Status.PASS, "TEST PASSED");
            }
        } catch (Exception e) {
            System.out.println("Error during afterTest teardown: " + e.getMessage());
        } finally {
            // 5. Pastikan quit() dipanggil untuk membersihkan resource memory
            if (driver != null) {
                driver.quit();
            }
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (extent != null) {
            extent.flush();
        }
        System.out.println("=== TEST SUITE COMPLETED ===");
    }
}