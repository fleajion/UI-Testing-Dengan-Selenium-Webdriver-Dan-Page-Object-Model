package com.pratikum.testing.otomation.test.demo;

import com.pratikum.testing.otomation.pages.LoginPage;
import com.pratikum.testing.otomation.pages.SecurePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTestWithPOM {
    private WebDriver driver;
    private LoginPage loginPage;
    private SecurePage securePage;

    @BeforeMethod
    public void setup() {
        // Setup ChromeDriver secara manual (tanpa WebDriverManager)
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Initialize page objects
        loginPage = new LoginPage(driver);
        securePage = new SecurePage(driver);
    }

    @Test(priority = 1)
    public void testSuccessfulLogin() {
        System.out.println("\n=== TEST: Successful Login with POM ===");
        // Navigate to login page
        loginPage.navigateToLoginPage();
        System.out.println(" Navigated to login page");

        // Verify login page elements
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be displayed");
        Assert.assertEquals(loginPage.getPageHeading(), "Login Page");
        System.out.println(" Login page verified");

        // Perform login
        loginPage.login("tomsmith", "SuperSecretPassword!");
        System.out.println(" Entered credentials and clicked login");

        // Verify navigation to secure page
        Assert.assertTrue(securePage.isOnSecurePage(), "Should navigate to secure page");
        System.out.println(" Navigated to secure page");

        // Verify secure page content
        Assert.assertEquals(securePage.getPageHeading(), "Secure Area");
        Assert.assertTrue(securePage.getFlashMessage().contains("You logged into a secure area!"));
        Assert.assertTrue(securePage.isLogoutButtonDisplayed());
        System.out.println(" Secure page verified");

        System.out.println(" Test PASSED\n");
    }

    @Test(priority = 2)
    public void testInvalidUsername() {
        System.out.println("\n=== TEST: Invalid Username with POM ===");
        loginPage.navigateToLoginPage();
        loginPage.login("InvalidUser", "SuperSecretPassword!");

        // Verify error message
        String flashMessage = loginPage.getFlashMessage();
        Assert.assertTrue(flashMessage.contains("Your username is invalid!"));
        Assert.assertTrue(loginPage.getCurrentUrl().contains("/login"));
        System.out.println(" Error message verified: " + flashMessage);

        System.out.println(" Test PASSED\n");
    }

    @Test(priority = 3)
    public void testInvalidPassword() {
        System.out.println("\n=== TEST: Invalid Password with POM ===");
        loginPage.navigateToLoginPage();
        loginPage.login("tomsmith", "wrongpassword");

        // Verify error message
        String flashMessage = loginPage.getFlashMessage();
        Assert.assertTrue(flashMessage.contains("Your password is invalid!"));
        System.out.println(" Error message verified: " + flashMessage);

        System.out.println(" Test PASSED\n");
    }

    @Test(priority = 4)
    public void testEmptyCredentials() {
        System.out.println("\n=== TEST: Empty Credentials with POM ===");
        loginPage.navigateToLoginPage();
        loginPage.login("", "");

        // Verify error message
        String flashMessage = loginPage.getFlashMessage();
        Assert.assertTrue(flashMessage.contains("Your username is invalid"));
        System.out.println(" Error message verified");
        System.out.println(" Test PASSED\n");
    }

    @Test(priority = 5)
    public void testLoginLogoutFlow() {
        System.out.println("\n=== TEST: Complete Login-Logout Flow with POM ===");
        // Login
        loginPage.navigateToLoginPage();
        loginPage.login("tomsmith", "SuperSecretPassword!");
        Assert.assertTrue(securePage.isOnSecurePage());
        System.out.println(" Successfully logged in");

        // Logout
        securePage.clickLogoutButton();
        System.out.println(" Clicked logout button");

        // Verify back to login page
        Assert.assertTrue(loginPage.getCurrentUrl().contains("/login"));
        String flashMessage = loginPage.getFlashMessage();
        Assert.assertTrue(flashMessage.contains("You logged out of the secure area!"));
        System.out.println(" Successfully logged out");
        System.out.println(" Flash message: " + flashMessage);
        System.out.println(" Test PASSED\n");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Menggunakan logging yang lebih baik daripada printStackTrace
                System.err.println("InterruptedException during teardown: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
            driver.quit();
        }
    }
}