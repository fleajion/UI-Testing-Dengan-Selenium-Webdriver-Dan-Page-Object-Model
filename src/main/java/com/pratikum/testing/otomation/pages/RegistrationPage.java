package com.pratikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory; // Tambahkan ini
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * RegistrationPage - Fixed Version
 */
public class RegistrationPage extends BasePage {

    // =========================
    // LOCATORS
    // =========================
    @FindBy(linkText = "Register")
    private WebElement registerLink;

    @FindBy(css = "div.page-title h1")
    private WebElement pageTitle;

    @FindBy(id = "gender-male")
    private WebElement maleRadio;

    @FindBy(id = "gender-female")
    private WebElement femaleRadio;

    @FindBy(id = "FirstName")
    private WebElement firstNameInput;

    @FindBy(id = "LastName")
    private WebElement lastNameInput;

    @FindBy(id = "Email")
    private WebElement emailInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(id = "ConfirmPassword")
    private WebElement confirmPasswordInput;

    @FindBy(id = "register-button")
    private WebElement registerButton;

    @FindBy(className = "result")
    private WebElement successMessage;

    @FindBy(id = "FirstName-error")
    private WebElement firstNameError;

    @FindBy(id = "LastName-error")
    private WebElement lastNameError;

    @FindBy(id = "Email-error")
    private WebElement emailError;

    @FindBy(id = "Password-error")
    private WebElement passwordError;

    @FindBy(id = "ConfirmPassword-error")
    private WebElement confirmPasswordError;

    // =========================
    // CONSTRUCTOR
    // =========================
    public RegistrationPage(WebDriver driver) {
        super(driver);
        // PENTING: Inisialisasi elemen FindBy agar tidak null
        PageFactory.initElements(driver, this);
    }

    /**
     * @param samsung
     */
    @Override
    public void search(String samsung) {

    }

    /**
     * @return
     */
    @Override
    public int getSearchResultCount() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public String getSearchMessage() {
        return "";
    }

    /**
     * @param i
     * @return
     */
    @Override
    public String getProductNameByIndex(int i) {
        return "";
    }

    /**
     *
     */
    @Override
    public void goToHomePage() {

    }

    /**
     * @param i
     */
    @Override
    public void addToCart(int i) {

    }

    /**
     * @return
     */
    @Override
    public String getCartItemCount() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public String getTotal() {
        return "";
    }

    /**
     * @return
     */
    @Override
    public boolean search() {
        return false;
    }

    /**
     *
     */
    @Override
    public void continueShopping() {

    }

    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     *
     */
    @Override
    public void checkout() {

    }

    /**
     * @param name
     * @param country
     * @param city
     * @param card
     * @param month
     * @param year
     */
    @Override
    public void fillBillingAddress(String name, String country, String city, String card, String month, String year) {

    }

    /**
     *
     */
    @Override
    public void placeOrder() {

    }

    /**
     *
     */
    @Override
    public void continueFromBilling() {

    }

    /**
     * @return
     */
    @Override
    public String getFirstValidationError() {
        return "";
    }

    /**
     * @param finalTester
     * @param s
     */
    @Override
    public void completePurchase(String finalTester, String s) {

    }

    /**
     * @return
     */
    @Override
    public String getConfirmation() {
        return "";
    }

    // =========================
    // NAVIGATION & STATE
    // =========================
    public void openRegistrationPage() {
        navigateTo("https://www.demoblaze.com/");
        safeClick(registerLink); // Perbaikan: Hapus deskripsi String
        wait.until(ExpectedConditions.visibilityOf(pageTitle)); // Gunakan wait langsung
        logAction("Opened Registration Page");
    }

    public boolean isOnRegistrationPage() {
        try {
            // Gunakan .getText() langsung dari WebElement
            boolean result = pageTitle.getText().equalsIgnoreCase("Register");
            logAction("On Registration Page: " + result);
            return result;
        } catch (Exception e) {
            takeScreenshot("registration_page_not_loaded");
            return false;
        }
    }

    // =========================
    // ACTIONS
    // =========================
    public void selectGender(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            safeClick(maleRadio);
        } else if ("female".equalsIgnoreCase(gender)) {
            safeClick(femaleRadio);
        }
    }

    public void enterFirstName(String value) {
        enterTextSafe(firstNameInput, value, "First Name");
    }

    public void enterLastName(String value) {
        enterTextSafe(lastNameInput, value, "Last Name");
    }

    public void enterEmail(String value) {
        enterTextSafe(emailInput, value, "Email");
    }

    public void enterPassword(String value) {
        enterTextSafe(passwordInput, value, "Password");
    }

    public void enterConfirmPassword(String value) {
        enterTextSafe(confirmPasswordInput, value, "Confirm Password");
    }

    public void submitRegistration() {
        safeClick(registerButton);
    }

    // =========================
    // BUSINESS FLOW
    // =========================
    public void register(String gender, String firstName, String lastName,
                         String email, String password, String confirmPassword) {
        selectGender(gender);
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        submitRegistration();
        logAction("Submitted Registration form for: " + email);
    }

    // =========================
    // VERIFICATION
    // =========================
    public boolean isRegistrationSuccess() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successMessage));
            // Gunakan .isDisplayed() dan .getText() standar
            boolean success = successMessage.isDisplayed()
                    && successMessage.getText().contains("Your registration completed");
            logAction("Registration success: " + success);
            return success;
        } catch (Exception e) {
            takeScreenshot("registration_failed");
            return false;
        }
    }

    public String getSuccessMessage() {
        return safeGetText(successMessage, "Success Message");
    }

    public boolean hasValidationError() {
        return !getFirstNameError().isEmpty()
                || !getLastNameError().isEmpty()
                || !getEmailError().isEmpty()
                || !getPasswordError().isEmpty()
                || !getConfirmPasswordError().isEmpty();
    }

    public String getFirstNameError() {
        return safeGetText(firstNameError, "First Name Error");
    }

    public String getLastNameError() {
        return safeGetText(lastNameError, "Last Name Error");
    }

    public String getEmailError() {
        return safeGetText(emailError, "Email Error");
    }

    public String getPasswordError() {
        return safeGetText(passwordError, "Password Error");
    }

    public String getConfirmPasswordError() {
        return safeGetText(confirmPasswordError, "Confirm Password Error");
    }

    // =========================
    // HELPER METHODS
    // =========================
    private void enterTextSafe(WebElement element, String text, String fieldName) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
            logAction("Entered '" + text + "' into field: " + fieldName);
        } catch (Exception e) {
            takeScreenshot(fieldName + "_input_failure");
            throw new RuntimeException("Failed to enter text into field: " + fieldName, e);
        }
    }

    private String safeGetText(WebElement element, String elementName) {
        try {
            return element.getText(); // Langsung ambil text dari element
        } catch (Exception e) {
            return "";
        }
    }

    private void safeClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            takeScreenshot("click_failure");
            throw e;
        }
    }
}