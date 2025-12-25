package com.pratikum.testing.otomation.test.demo;

import com.aventstack.extentreports.Status;
import com.pratikum.testing.otomation.pages.RegistrationPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Random;

public class DemoWebShopRegistrationTest extends BaseTest {

    private String generateRandomEmail() {
        Random random = new Random();
        return "testuser" + System.currentTimeMillis() +
                random.nextInt(1000) + "@test.com";
    }

    @Test(priority = 1, description = "Test successful user registration with valid data")
    public void testSuccessfulRegistration() {
        test.log(Status.INFO, "Starting successful registration test");

        RegistrationPage registerPage = new RegistrationPage(driver);

        // Navigate to registration page
        registerPage.navigateToRegisterPage();
        test.log(Status.INFO, "Navigated to registration page");

        // Verify page title
        String pageTitle = registerPage.getPageTitle();
        Assert.assertEquals(pageTitle, "Register", "Page title should be 'Register'");
        test.log(Status.PASS, "Verified registration page title: " + pageTitle);

        // Generate unique email
        String email = generateRandomEmail();
        test.log(Status.INFO, "Generated test email: " + email);

        // Fill registration form
        registerPage.selectGender("Male");
        test.log(Status.INFO, "Selected gender: Male");

        registerPage.enterFirstName("John");
        test.log(Status.INFO, "Entered first name: John");

        registerPage.enterLastName("Doe");
        test.log(Status.INFO, "Entered last name: Doe");

        registerPage.enterEmail(email);
        test.log(Status.INFO, "Entered email: " + email);

        registerPage.enterPassword("Test@123");
        test.log(Status.INFO, "Entered password");

        registerPage.enterConfirmPassword("Test@123");
        test.log(Status.INFO, "Confirmed password");

        // Submit registration
        registerPage.clickRegisterButton();
        test.log(Status.INFO, "Clicked register button");

        // Verify success
        Assert.assertTrue(registerPage.isRegistrationSuccessful(),
                "Registration should be successful");

        String successMsg = registerPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Your registration completed"),
                "Success message should be displayed");
        test.log(Status.PASS, "Registration successful: " + successMsg);

        System.out.println("\n/ SUCCESSFUL REGISTRATION TEST PASSED");
        System.out.println("  Registered with email: " + email + "\n");
    }

    @Test(priority = 2, description = "Test registration with empty required fields")
    public void testRegistrationWithEmptyFields() {
        test.log(Status.INFO, "Starting empty fields validation test");

        RegistrationPage registerPage = new RegistrationPage(driver);

        registerPage.navigateToRegisterPage();
        test.log(Status.INFO, "Navigated to registration page");

        // Click register without filling any field
        registerPage.clickRegisterButton();
        test.log(Status.INFO, "Clicked register with empty fields");

        // Verify validation errors
        String firstNameError = registerPage.getFirstNameError();
        Assert.assertFalse(firstNameError.isEmpty(),
                "First name validation error should be displayed");
        test.log(Status.PASS, "First name error: " + firstNameError);

        System.out.println("\n/ EMPTY FIELDS VALIDATION TEST PASSED");
        System.out.println("  Validation working correctly\n");
    }

    @Test(priority = 3, description = "Test registration with invalid email format")
    public void testRegisterActionWithInvalidEmail() {
        test.log(Status.INFO, "Starting invalid email format test");

        RegistrationPage registerPage = new RegistrationPage(driver);

        registerPage.navigateToRegisterPage();

        // Fill form with invalid email
        registerPage.registerUser("Waie", "Test", "User",
                "invalidemail", "Test6123");
        test.log(Status.INFO, "Submitted form with invalid email: invalidemail");

        // Verify error
        String emailError = registerPage.getEmailError();
        Assert.assertFalse(emailError.isEmpty(),
                "Email validation error should be displayed");
        test.log(Status.PASS, "Email validation error: " + emailError);

        System.out.println("\n/ INVALID EMAIL TEST PASSED\n");
    }

    @Test(priority = 4, description = "Test registration with mismatched passwords")
    public void testRegistrationWithMismatchedPasswords() {
        test.log(Status.INFO, "Starting password mismatch test");

        RegistrationPage registerPage = new RegistrationPage(driver);

        registerPage.navigateToRegisterPage();

        // Fill all fields
        registerPage.selectGender("Female");
        registerPage.enterFirstName("Test");
        registerPage.enterLastName("User");
        registerPage.enterEmail(generateRandomEmail());
        registerPage.enterPassword("Test@123");
        registerPage.enterConfirmPassword("Test@456"); // Different password
        registerPage.clickRegisterButton();
        test.log(Status.INFO, "Submitted with mismatched passwords");

        // Should stay on registration page or show error
        Assert.assertTrue(registerPage.hasValidationErrors() || driver.getCurrentUrl().contains("/register"),
                "Should show validation errors or stay on registration page");
        test.log(Status.PASS, "Password mismatch validation working correctly");

        System.out.println("\n/ PASSWORD MISMATCH TEST PASSED\n");
    }
}