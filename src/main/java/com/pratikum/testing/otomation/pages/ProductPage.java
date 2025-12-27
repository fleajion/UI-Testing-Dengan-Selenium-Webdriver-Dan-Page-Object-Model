package com.pratikum.testing.otomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory; // Tambahkan ini
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * ProductPage - Fixed Version
 */
public class ProductPage extends BasePage {

    // =========================
    // LOCATORS
    // =========================
    @FindBy(css = ".name")
    private WebElement productName;

    @FindBy(css = ".price-container")
    private WebElement productPrice;

    @FindBy(css = "#more-information .description")
    private WebElement productDescription;

    @FindBy(css = "#imgp img")
    private WebElement productImage;

    @FindBy(linkText = "Add to cart")
    private WebElement addToCartButton;

    @FindBy(id = "tbodyid")
    private WebElement quantityInputContainer;

    @FindBy(css = ".sweet-alert.showSweetAlert.visible")
    private WebElement successNotification;

    @FindBy(css = ".confirm.btn")
    private WebElement confirmCartButton;

    // =========================
    // CONSTRUCTOR
    // =========================
    public ProductPage(WebDriver driver) {
        super(driver);
        // Penting: Inisialisasi elemen FindBy
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
    // PAGE STATE VALIDATION
    // =========================
    public boolean isOnProductDetailPage() {
        try {
            // Perbaikan: Gunakan wait standar jika BasePage.waitForVisible hanya terima 'By'
            wait.until(ExpectedConditions.visibilityOf(productName));
            logAction("On Product Detail Page: " + getProductName());
            return true;
        } catch (Exception e) {
            takeScreenshot("product_detail_page_failure");
            return false;
        }
    }

    // =========================
    // GETTERS
    // =========================
    public String getProductName() {
        // Perbaikan: Gunakan .getText() langsung dari WebElement
        return productName.getText();
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    public String getProductDescription() {
        return productDescription.getText();
    }

    public boolean isProductImageDisplayed() {
        // Perbaikan: Gunakan .isDisplayed() langsung
        return productImage.isDisplayed();
    }

    // =========================
    // ACTIONS
    // =========================
    public void setQuantity(int quantity) {
        try {
            WebElement qtyInput = quantityInputContainer.findElement(By.tagName("input"));
            qtyInput.clear();
            qtyInput.sendKeys(String.valueOf(quantity));
            logAction("Set product quantity: " + quantity);
        } catch (Exception e) {
            takeScreenshot("set_quantity_failure");
            throw new RuntimeException("Failed to set quantity", e);
        }
    }

    public void clickAddToCart() {
        // Perbaikan: Sesuai gambar, safeClick Anda hanya menerima 1 argumen
        safeClick(addToCartButton);
    }

    // =========================
    // BUSINESS FLOW
    // =========================
    public void addProductToCart(int quantity) {
        if (quantity > 0) {
            setQuantity(quantity);
        }
        clickAddToCart();
        // Perbaikan: Gunakan wait langsung
        wait.until(ExpectedConditions.visibilityOf(successNotification));
        logAction("Product added to cart: " + getProductName());
    }

    // =========================
    // VERIFICATION
    // =========================
    public boolean isAddToCartSuccessful() {
        try {
            String message = successNotification.getText().toLowerCase();
            boolean success = message.contains("added to your cart");
            logAction("Add to Cart successful: " + success);
            return success;
        } catch (Exception e) {
            takeScreenshot("add_to_cart_failure");
            return false;
        }
    }

    public String getAddToCartMessage() {
        try {
            return successNotification.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // =========================
    // NAVIGATION
    // =========================
    public void confirmCartNotification() {
        // Perbaikan: safeClick hanya terima 1 argumen
        safeClick(confirmCartButton);
        logAction("Confirmed Add to Cart Notification");
    }

    // =========================
    // EDGE CASE
    // =========================
    public boolean isAddToCartButtonEnabled() {
        return addToCartButton.isEnabled();
    }

    // Helper untuk menangani safeClick jika BasePage tidak mendukung deskripsi String
    private void safeClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void goToCart() {

    }
}