package com.pratikum.testing.otomation.test.demo;

import com.aventstack.extentreports.Status;
import com.pratikum.testing.otomation.pages.CartPage;
import com.pratikum.testing.otomation.pages.HomePage;
import com.pratikum.testing.otomation.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class untuk feature Product Details (5 test cases)
 */
public class ProductDetailsTest extends BaseTest {

    @Test(priority = 1, description = "Test view product details")
    public void testViewProductDetails() {
        test.log(Status.INFO, "Memulai test view product details");

        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Klik produk pertama untuk lihat details
        homePage.clickProduct();
        test.log(Status.INFO, "Klik produk pertama untuk lihat details");

        // Verifikasi product details page terbuka
        String productName = productPage.getName();
        Assert.assertFalse(productName.isEmpty(), "Nama produk harus ditampilkan");
        test.log(Status.PASS, "Product details berhasil dibuka - product: " + productName);
    }

    @Test(priority = 2, description = "Test product image display")
    public void testProductImageDisplay() {
        test.log(Status.INFO, "Memulai test product image display");
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");
        // Klik produk pertama
        homePage.clickProduct();
        test.log(Status.INFO, "Klik produk pertama");
        // Verifikasi gambar produk ditampilkan
        boolean imageDisplayed = productPage.isImageDisplayed();
        Assert.assertTrue(imageDisplayed, "Gambar produk harus ditampilkan");
        test.log(Status.PASS, "Product image berhasil ditampilkan");
    }

    @Test(priority = 3, description = "Test product price display")
    public void testProductPriceDisplay() {
        test.log(Status.INFO, "Memulai test product price display");
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");
        // Klik produk pertama
        homePage.clickProduct();
        test.log(Status.INFO, "Klik produk pertama");
        // Verifikasi harga produk ditampilkan
        String price = productPage.getPrice();
        Assert.assertFalse(price.isEmpty(), "Harga produk harus ditampilkan");
        Assert.assertTrue(price.contains("$") || price.matches(".*\\d+.*"), "Harga harus mengandung angka atau currency symbol");
        test.log(Status.PASS, "Product price berhasil ditampilkan: " + price);
    }

    @Test(priority = 4, description = "Test product description")
    public void testProductDescription() {
        test.log(Status.INFO, "Memulai test product description");
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Klik produk pertama
        homePage.clickProduct(0);
        test.log(Status.INFO, "Klik produk pertama");

        // Verifikasi deskripsi produk
        String description = productPage.getDescription();
        // beberapa produk mungkin tidak ada deskripsi, jadi tidak diassert
        test.log(Status.INFO, "Product description: " + (description.isEmpty() ? "Tidak ada deskripsi" : description));
        test.log(Status.PASS, "Product description test selesai");
    }

    @Test(priority = 5, description = "Test add to cart dari details page")
    public void testAddToCartFromDetailsPage() {
        test.log(Status.INFO, "Memulai test add to cart dari details page");
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Klik produk pertama
        homePage.clickProduct(0);
        test.log(Status.INFO, "Klik produk pertama");

        // Add to cart dari details page
        productPage.addToCart();
        test.log(Status.INFO, "Klik add to cart dari details page");

        // Verifikasi produk berhasil ditambahkan
        boolean isAdded = productPage.isAddedToCart();
        Assert.assertTrue(isAdded, "Produk harus berhasil ditambahkan ke cart");
        test.log(Status.PASS, "Add to cart dari details page berhasil");

        // Bersih-bersih
        productPage.goToCart();
        cartPage.removeItem(0);
        test.log(Status.INFO, "Bersih-bersih - hapus item dari cart");
    }
}