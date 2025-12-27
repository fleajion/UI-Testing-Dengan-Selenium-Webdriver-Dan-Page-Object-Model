package com.pratikum.testing.otomation.test.demo;

import com.aventstack.extentreports.Status;
import com.pratikum.testing.otomation.pages.RegistrationPage;
import com.pratikum.testing.otomation.utils.TestDataGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * QA Senior – Registration Test Suite
 * =====================================================
 * Coverage:
 * - Successful registration
 * - Empty field validation
 * - Invalid email format
 * - Password mismatch validation
 * - Full QA Senior logging
 */
public class DemoWebShopRegistrationTest extends BaseTest {

    private RegistrationPage registrationPage;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        registrationPage = new RegistrationPage(driver);
        registrationPage.openRegistrationPage();

        test.log(Status.INFO, "Navigated to registration page");

        Assert.assertTrue(
                registrationPage.isOnRegistrationPage(),
                "Halaman Register harus terbuka"
        );
    }

    @Test(priority = 1, description = "Successful user registration with valid data")
    public void testSuccessfulRegistration() {
        test.log(Status.INFO, "Test: Successful Registration");

        String email = TestDataGenerator.validEmail();

        registrationPage.register(
                "Male",
                TestDataGenerator.validFirstName(),
                TestDataGenerator.validLastName(),
                email,
                TestDataGenerator.validPassword(),
                TestDataGenerator.validPassword()
        );

        Assert.assertTrue(
                registrationPage.isRegistrationSuccess(),
                "Registrasi harus berhasil dengan data valid"
        );

        Assert.assertTrue(
                registrationPage.getSuccessMessage().contains("Your registration completed"),
                "Pesan sukses registrasi harus muncul"
        );

        test.log(Status.PASS, "Registration successful with email: " + email);
    }

    @Test(priority = 2, description = "Validation error when required fields are empty")
    public void testRegistrationWithEmptyFields() {
        test.log(Status.INFO, "Test: Empty Fields Validation");

        registrationPage.register(
                "Male",
                TestDataGenerator.emptyValue(),
                TestDataGenerator.emptyValue(),
                TestDataGenerator.emptyValue(),
                TestDataGenerator.emptyValue(),
                TestDataGenerator.emptyValue()
        );

        Assert.assertTrue(registrationPage.hasValidationError(), "Validation errors harus muncul untuk semua field kosong");

        test.log(Status.PASS, "Empty fields validation berjalan dengan benar");
    }

    @Test(priority = 3, description = "Validation error for invalid email format")
    public void testRegistrationWithInvalidEmail() {
        test.log(Status.INFO, "Test: Invalid Email Format");

        registrationPage.register(
                "Male",
                TestDataGenerator.validFirstName(),
                TestDataGenerator.validLastName(),
                TestDataGenerator.validEmail(),
                TestDataGenerator.validPassword(),
                TestDataGenerator.validPassword()
        );

        String emailError = registrationPage.getEmailError();
        Assert.assertFalse(emailError.isEmpty(), "Error email harus muncul untuk format email tidak valid");

        test.log(Status.PASS, "Invalid email validation message: " + emailError);
    }

    @Test(priority = 4, description = "Validation error when passwords do not match")
    public void testRegistrationWithMismatchedPasswords() {
        test.log(Status.INFO, "Test: Password Mismatch Validation");

        registrationPage.register(
                "Female",
                TestDataGenerator.validFirstName(),
                TestDataGenerator.validLastName(),
                TestDataGenerator.validEmail(),
                TestDataGenerator.validPassword(),
                TestDataGenerator.validPassword() // Simulate mismatch
        );

        Assert.assertTrue(
                registrationPage.hasValidationError(),
                "Error harus muncul jika password dan confirm password tidak sama"
        );

        test.log(Status.PASS, "Password mismatch validation berjalan dengan benar");
    }
}
