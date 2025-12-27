package com.pratikum.testing.otomation.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            // 1. Pastikan tidak ada alert aktif
            try {
                driver.switchTo().alert().accept();
            } catch (Exception ignored) {}

            // 2. Tunggu page ready
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                    wd -> ((JavascriptExecutor) wd)
                            .executeScript("return document.readyState")
                            .equals("complete")
            );

            // 3. Take screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destination = System.getProperty("user.dir")
                    + "/src/test/resources/screenshots/"
                    + screenshotName + "_" + timestamp + ".png";

            File destFile = new File(destination);
            destFile.getParentFile().mkdirs();
            FileUtils.copyFile(source, destFile);

            return destination;

        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
            return null;
        }
    }
}
