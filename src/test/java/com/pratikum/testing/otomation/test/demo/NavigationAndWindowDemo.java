package com.pratikum.testing.otomation.test.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Set;
import java.util.logging.Logger;

public class NavigationAndWindowDemo {

    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(NavigationAndWindowDemo.class.getName());

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void demonstrateNavigation() {
        logger.info("\n=== NAVIGATION COMMANDS ===");

        // Navigate to page
        driver.get("https://the-internet.herokuapp.com/");
        logger.info(" Navigated to homepage");
        logger.info(" Current URL: " + driver.getCurrentUrl());

        // Click on a link
        driver.findElement(By.linkText("Form Authentication")).click();
        logger.info(" Clicked Form Authentication link");
        logger.info(" Current URL: " + driver.getCurrentUrl());

        // Navigate back
        driver.navigate().back();
        logger.info(" Navigated back");
        logger.info(" Current URL: " + driver.getCurrentUrl());

        // Navigate forward
        driver.navigate().forward();
        logger.info(" Navigated forward");
        logger.info(" Current URL: " + driver.getCurrentUrl());

        // Refresh page
        driver.navigate().refresh();
        logger.info(" Page refreshed");

        // Navigate to specific URL
        driver.navigate().to("https://the-internet.herokuapp.com/dropdown");
        logger.info(" Navigated to dropdown page");
        logger.info(" Current URL: " + driver.getCurrentUrl());

        logger.info("\n Navigation test PASSED\n");
    }

    @Test
    public void demonstrateMultipleWindows() {
        logger.info("\n=== MULTIPLE WINDOWS HANDLING ===");

        // Navigate to multiple windows page
        driver.get("https://the-internet.herokuapp.com/windows");

        // Store original window handle
        String originalWindow = driver.getWindowHandle();
        logger.info(" Original window handle: " + originalWindow);

        // Verify only one window is open
        Assert.assertEquals(driver.getWindowHandles().size(), 1);

        // Click link to open new window
        driver.findElement(By.linkText("Click Here")).click();
        logger.info(" Clicked to open new window");

        // Wait for new window
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.warning("Thread sleep interrupted: " + e.getMessage());
        }

        // Get all window handles
        Set<String> allWindows = driver.getWindowHandles();
        logger.info(" Total windows open: " + allWindows.size());

        // Switch to new window
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                logger.info(" Switched to new window");
                break;
            }
        }

        // Verify new window content
        String newWindowText = driver.findElement(By.tagName("h3")).getText();
        logger.info(" New window heading: " + newWindowText);
        Assert.assertEquals(newWindowText, "New Window");

        // Close new window
        driver.close();
        logger.info(" Closed new window");

        // Switch back to original window
        driver.switchTo().window(originalWindow);
        logger.info(" Switched back to original window");

        // Verify we're back on original page
        String originalHeading = driver.findElement(By.tagName("h3")).getText();
        Assert.assertEquals(originalHeading, "Opening a new window");

        logger.info("\n Multiple windows test PASSED\n");
    }

    @Test
    public void demonstrateIframes() {
        logger.info("\n=== IFRAME HANDLING ===");
        driver.get("https://the-internet.herokuapp.com/iframe");

        // Find and switch to iframe
        WebElement iframe = driver.findElement(By.id("mce_0_ifr"));
        driver.switchTo().frame(iframe);
        logger.info(" Switched to iframe");

        // Interact with content inside iframe
        WebElement editor = driver.findElement(By.id("tinymce"));
        String originalText = editor.getText();
        logger.info(" Original text: " + originalText);

        // Clear and type new text
        editor.clear();
        editor.sendKeys("Testing iframe interaction with Selenium!");
        logger.info(" Entered new text in iframe");

        // Verify text changed
        String newText = editor.getText();
        Assert.assertEquals(newText, "Testing iframe interaction with Selenium!");

        // Switch back to main content
        driver.switchTo().defaultContent();
        logger.info(" Switched back to main content");

        // Verify we can interact with main page
        WebElement heading = driver.findElement(By.tagName("h3"));
        Assert.assertEquals(heading.getText(), "An iframe containing the TinyMCE WYSIWYG Editor");

        logger.info("\n Iframe test PASSED\n");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.warning("Thread sleep interrupted in tearDown: " + e.getMessage());
            } finally {
                driver.quit();
            }
        }
    }
}