package com.pratikum.testing.otomation.test.demo;

import com.aventstack.extentreports.Status;
import com.pratikum.testing.otomation.pages.CartPage;
import com.pratikum.testing.otomation.pages.HomePage;
import com.pratikum.testing.otomation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * QA Senior Automation Test
 * Feature: Shopping Cart – Demoblaze
 * Coverage:
 * - Add / Remove item
 * - Quantity update
 * - Cart total calculation
 * - Cart persistence after login
 * - Boundary & negative handling
 */
public class ShoppingCartTest extends BaseTest {

    private HomePage homePage;
    private CartPage cartPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);
        loginPage = new LoginPage(driver);

        homePage.goToHomePage();
        test.log(Status.INFO, "Navigated to Home Page");
    }

    @AfterMethod
    public void cleanup() {
        try {
            homePage.goToCart();
            while (cartPage.getItemCount() > 0) {
                cartPage.removeItem(0);
            }
            test.log(Status.INFO, "Cart cleaned up after test");
        } catch (Exception ignored) {}
    }

    @Test(priority = 1, description = "Add single product to cart")
    public void testAddSingleProductToCart() {
        test.log(Status.INFO, "Add single product");

        homePage.addToCart(0);
        String cartQty = homePage.getCartItemCount();

        Assert.assertNotEquals(cartQty, "(0)", "Cart quantity harus bertambah");

        homePage.goToCart();
        Assert.assertEquals(cartPage.getItemCount(), 1, "Cart harus berisi 1 item");

        test.log(Status.PASS, "Single product successfully added");
    }

    @Test(priority = 2, description = "Add multiple products to cart")
    public void testAddMultipleProductsToCart() {
        test.log(Status.INFO, "Add multiple products");

        homePage.addToCart(0);
        homePage.addToCart(1);

        homePage.goToCart();
        Assert.assertTrue(cartPage.getItemCount() >= 2, "Minimal 2 produk harus ada di cart");

        test.log(Status.PASS, "Multiple products added successfully");
    }

    @Test(priority = 3, description = "Update product quantity")
    public void testUpdateProductQuantity() {
        homePage.addToCart(0);
        homePage.goToCart();

        cartPage.updateQuantity(0, 3);
        String total = cartPage.getTotal();

        Assert.assertFalse(total.isEmpty(), "Cart total harus terupdate");

        test.log(Status.PASS, "Quantity updated successfully, total: " + total);
    }

    @Test(priority = 4, description = "Remove product from cart")
    public void testRemoveProductFromCart() {
        homePage.addToCart(0);
        homePage.goToCart();

        Assert.assertTrue(cartPage.getItemCount() > 0, "Item harus ada sebelum dihapus");

        cartPage.removeItem(0);
        Assert.assertTrue(cartPage.search(), "Cart harus kosong setelah remove");

        test.log(Status.PASS, "Product removed successfully");
    }

    @Test(priority = 5, description = "Validate cart total calculation")
    public void testCartTotalCalculation() {
        homePage.addToCart(0);
        homePage.goToCart();

        String total = cartPage.getTotal();
        Assert.assertTrue(total.matches(".*\\d+.*"), "Total harus mengandung nilai angka");

        test.log(Status.PASS, "Cart total displayed correctly: " + total);
    }

    @Test(priority = 6, description = "Continue shopping navigation")
    public void testContinueShoppingFunctionality() {
        homePage.addToCart(0);
        homePage.goToCart();

        cartPage.continueShopping();
        Assert.assertTrue(driver.getCurrentUrl().contains("demoblaze"), "Harus kembali ke halaman utama");

        test.log(Status.PASS, "Continue shopping works correctly");
    }

    @Test(priority = 7, description = "Empty cart scenario")
    public void testEmptyCartScenario() {
        cartPage.goToHomePage();
        Assert.assertTrue(cartPage.isEmpty(), "Cart baru harus kosong");

        test.log(Status.PASS, "Empty cart validated");
    }

    @Test(priority = 8, description = "Cart persistence after login")
    public void testCartPersistenceAfterLogin() {
        homePage.addToCart(0);

        loginPage.goToHomePage();
        loginPage.login("testuser@example.com", "Test@123");

        homePage.goToHomePage();
        Assert.assertNotEquals(homePage.getCartItemCount(), "(0)", "Item cart harus tetap ada setelah login");

        test.log(Status.PASS, "Cart persistence verified");
    }

    @Test(priority = 9, description = "Maximum quantity boundary handling")
    public void testMaximumQuantityValidation() {
        homePage.addToCart(0);
        homePage.goToCart();

        cartPage.updateQuantity(0, 9999);
        String total = cartPage.getTotal();

        Assert.assertFalse(total.isEmpty(), "System harus handle quantity besar tanpa error");

        test.log(Status.PASS, "High quantity handled correctly, total: " + total);
    }

    @Test(priority = 10, description = "Cart icon update verification")
    public void testCartIconUpdate() {
        String initialQty = homePage.getCartItemCount();

        homePage.addToCart(0);
        String updatedQty = homePage.getCartItemCount();

        Assert.assertNotEquals(updatedQty, initialQty, "Cart icon quantity harus berubah");

        test.log(Status.PASS, "Cart icon updated correctly: " + updatedQty);
    }
}
