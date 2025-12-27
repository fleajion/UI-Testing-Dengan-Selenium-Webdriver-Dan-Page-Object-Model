package com.pratikum.testing.otomation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * CartPage - QA Senior Style
 * =====================================================
 * - Centralized reusable actions for Cart Page
 * - Strong waiting strategy
 * - Logging + retry + stability for automation
 */
public class CartPage extends BasePage {

    // =========================
    // LOCATORS
    // =========================
    @FindBy(css = "tr.success") // update sesuai struktur Demoblaze
    private List<WebElement> cartRows;

    @FindBy(css = ".success input[type='checkbox']")
    private List<WebElement> removeCheckboxes;

    @FindBy(xpath = "//button[text()='Update cart']")
    private WebElement updateCartButton;

    @FindBy(xpath = "//button[text()='Continue shopping']")
    private WebElement continueShoppingButton;

    @FindBy(css = "input.qty-input")
    private List<WebElement> quantityInputs;

    @FindBy(css = ".product-unit-price")
    private List<WebElement> unitPrices;

    @FindBy(css = ".product-subtotal")
    private List<WebElement> subTotals;

    @FindBy(css = ".cart-total-right .value-summary")
    private WebElement cartTotal;

    @FindBy(css = ".order-summary-content")
    private WebElement emptyCartMessage;

    @FindBy(id = "termsofservice")
    private WebElement termsCheckbox;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    // =========================
    // CONSTRUCTOR
    // =========================
    public CartPage(WebDriver driver) {
        super(driver);
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
    // NAVIGATION
    // =========================
    public void openCartPage() {
        navigateTo("https://www.demoblaze.com/cart.html");
        waitForUrlContains("/cart");
        logAction("Navigated to Cart page");
    }

    // =========================
    // CART INFO
    // =========================
    public int getItemCount() {
        return cartRows.size();
    }

    public boolean isCartEmpty() {
        try {
            boolean empty = emptyCartMessage.isDisplayed() &&
                    emptyCartMessage.getText().toLowerCase().contains("empty");
            logAction("Cart empty check: " + empty);
            return empty;
        } catch (Exception e) {
            logAction("Cart not empty");
            return false;
        }
    }

    public String getCartTotal() {
        waitForVisible(By.cssSelector(".cart-total-right .value-summary"));
        String total = cartTotal.getText();
        logAction("Cart total: " + total);
        return total;
    }

    // =========================
    // CART ACTIONS
    // =========================
    public void updateQuantity(int index, int quantity) {
        validateIndex(index);
        WebElement qtyInput = quantityInputs.get(index);

        try {
            qtyInput.clear();
            qtyInput.sendKeys(String.valueOf(quantity));
            safeClick(By.xpath("//button[text()='Update cart']"));
            wait.until(ExpectedConditions.stalenessOf(qtyInput));
            logAction("Updated quantity of item index " + index + " to " + quantity);
        } catch (Exception e) {
            takeScreenshot("update_quantity_failure");
            throw new RuntimeException("Failed to update quantity at index: " + index, e);
        }
    }

    public void removeItem(int index) {
        validateIndex(index);
        WebElement checkbox = removeCheckboxes.get(index);

        try {
            if (!checkbox.isSelected()) checkbox.click();
            safeClick(By.xpath("//button[text()='Update cart']"));
            wait.until(ExpectedConditions.stalenessOf(checkbox));
            logAction("Removed item at index " + index);
        } catch (Exception e) {
            takeScreenshot("remove_item_failure");
            throw new RuntimeException("Failed to remove item at index: " + index, e);
        }
    }

    public void removeAllItems() {
        try {
            for (WebElement checkbox : removeCheckboxes) {
                if (!checkbox.isSelected()) checkbox.click();
            }
            safeClick(By.xpath("//button[text()='Update cart']"));
            waitForVisible(By.cssSelector(".order-summary-content"));
            logAction("Removed all items from cart");
        } catch (Exception e) {
            takeScreenshot("remove_all_failure");
            throw new RuntimeException("Failed to remove all items from cart", e);
        }
    }

    // =========================
    // BUSINESS VALIDATION
    // =========================
    public double getUnitPrice(int index) {
        validateIndex(index);
        return parsePrice(unitPrices.get(index).getText());
    }

    public double getSubTotal(int index) {
        validateIndex(index);
        return parsePrice(subTotals.get(index).getText());
    }

    public boolean isSubtotalCalculatedCorrectly(int index) {
        validateIndex(index);
        int qty = Integer.parseInt(quantityInputs.get(index).getAttribute("value"));
        double unitPrice = getUnitPrice(index);
        double subTotal = getSubTotal(index);
        boolean correct = Math.abs((unitPrice * qty) - subTotal) < 0.01;
        logAction("Subtotal check for index " + index + ": " + correct);
        return correct;
    }

    // =========================
    // CHECKOUT
    // =========================
    public boolean isCheckoutEnabledWithoutTerms() {
        boolean enabled = checkoutButton.isEnabled();
        logAction("Checkout enabled without terms: " + enabled);
        return enabled;
    }

    public void acceptTermsAndCheckout() {
        waitForClickable(By.id("termsofservice"));
        if (!termsCheckbox.isSelected()) {
            termsCheckbox.click();
        }
        checkoutButton.click();
        logAction("Accepted terms and clicked Checkout");
    }

    // =========================
    // HELPER METHODS
    // =========================
    private void validateIndex(int index) {
        if (index < 0 || index >= cartRows.size()) {
            throw new IllegalArgumentException("Invalid cart index: " + index + ". Current size: " + cartRows.size());
        }
    }

    private double parsePrice(String priceText) {
        return Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));
    }

    public void clickPlaceOrder() {

    }
}
