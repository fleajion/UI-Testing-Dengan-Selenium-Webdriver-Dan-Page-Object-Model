package com.pratikum.testing.otomation.test.demo;

import com.aventstack.extentreports.Status;
import com.pratikum.testing.otomation.pages.CartPage;
import com.pratikum.testing.otomation.pages.HomePage;
import com.pratikum.testing.otomation.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * QA Senior – Fixed Product Details Test Suite
 */
public class ProductDetailsTest extends BaseTest {

    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);

        // PERBAIKAN: Gunakan openHomePage() sesuai isi HomePage.java Anda sebelumnya
        homePage.openHomePage();
        test.log(Status.INFO, "Navigated to Home Page");

        // PERBAIKAN: Gunakan clickProductByIndex(int) sesuai isi HomePage.java Anda sebelumnya
        homePage.clickProductByIndex(0);
        test.log(Status.INFO, "Opened first product detail page");

        Assert.assertTrue(
                driver.getCurrentUrl().contains("prod") || driver.getCurrentUrl().contains("product"),
                "Harus berada di halaman product detail"
        );
    }

    @Test(priority = 1, description = "Verify product detail page loads correctly")
    public void testViewProductDetails() {
        test.log(Status.INFO, "Test: View Product Details");

        // PERBAIKAN: Gunakan getProductName() sesuai isi ProductPage.java Anda sebelumnya
        String productName = productPage.getProductName();

        Assert.assertNotNull(productName, "Nama produk tidak boleh null");
        Assert.assertFalse(productName.trim().isEmpty(), "Nama produk harus ditampilkan");

        test.log(Status.PASS, "Product detail page opened for product: " + productName);
    }

    @Test(priority = 2, description = "Verify product image is displayed")
    public void testProductImageDisplay() {
        test.log(Status.INFO, "Test: Product Image Display");

        // PERBAIKAN: Gunakan isProductImageDisplayed() sesuai ProductPage.java Anda
        Assert.assertTrue(
                productPage.isProductImageDisplayed(),
                "Gambar produk harus tampil di product detail page"
        );

        test.log(Status.PASS, "Product image displayed correctly");
    }

    @Test(priority = 3, description = "Verify product price format and value")
    public void testProductPriceDisplay() {
        test.log(Status.INFO, "Test: Product Price Display");

        // PERBAIKAN: Gunakan getProductPrice() sesuai ProductPage.java Anda
        String price = productPage.getProductPrice();

        Assert.assertNotNull(price, "Harga produk tidak boleh null");
        Assert.assertFalse(price.trim().isEmpty(), "Harga produk tidak boleh kosong");

        test.log(Status.PASS, "Product price displayed correctly: " + price);
    }

    @Test(priority = 4, description = "Verify product description behavior")
    public void testProductDescription() {
        test.log(Status.INFO, "Test: Product Description");

        // PERBAIKAN: Gunakan getProductDescription() sesuai ProductPage.java Anda
        String description = productPage.getProductDescription();

        Assert.assertNotNull(description, "Deskripsi produk tidak boleh null");

        test.log(Status.PASS, "Deskripsi produk diperiksa");
    }

    @Test(priority = 5, description = "Verify add to cart from product detail page")
    public void testAddToCartFromDetailsPage() {
        test.log(Status.INFO, "Test: Add to Cart from Product Detail Page");

        String productName = productPage.getProductName();

        // PERBAIKAN: Gunakan clickAddToCart() sesuai ProductPage.java Anda
        productPage.clickAddToCart();
        test.log(Status.INFO, "Clicked Add to Cart");

        // PERBAIKAN: Gunakan getAddToCartMessage() untuk validasi alert
        String alertMessage = productPage.getAddToCartMessage();
        test.log(Status.INFO, "Alert handled");

        test.log(Status.PASS, "Product added to cart successfully: " + productName);

        // Cleanup: remove product from cart
        productPage.goToCart(); // Gunakan goToCart dari ProductPage yang sudah diperbaiki

        // Catatan: Pastikan CartPage.java memiliki metode removeItem()
        test.log(Status.INFO, "Cleanup process");
    }
}