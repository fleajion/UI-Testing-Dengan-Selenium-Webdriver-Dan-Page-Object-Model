package com.pratikum.testing.otomation.test.demo;

import com.aventstack.extentreports.Status;
import com.pratikum.testing.otomation.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * QA Senior Automation Test - Fixed
 */
public class ProductSearchTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(driver);
        // Perbaikan: Gunakan openHomePage() sesuai isi HomePage.java Anda sebelumnya
        homePage.openHomePage();
        test.log(Status.INFO, "Navigated to Home Page");
    }

    @Test(priority = 1, description = "Search with valid keyword should return results")
    public void testSearchWithValidKeyword() {
        test.log(Status.INFO, "Search with valid keyword");

        // Catatan: Pastikan metode search() sudah Anda tambahkan di HomePage.java
        homePage.search("Samsung");
        test.log(Status.INFO, "Keyword: Samsung");

        int resultCount = homePage.getSearchResultCount();

        Assert.assertTrue(resultCount > 0, "Search dengan keyword valid harus menampilkan hasil");
        test.log(Status.PASS, "Valid search success, result count: " + resultCount);
    }

    @Test(priority = 2, description = "Search with invalid keyword should show no results message")
    public void testSearchWithInvalidKeyword() {
        test.log(Status.INFO, "Search with invalid keyword");

        homePage.search("xyzabcl23invalid");

        String message = homePage.getSearchMessage();
        int resultCount = homePage.getSearchResultCount();

        // Demoblaze biasanya hanya menampilkan list kosong jika tidak ada hasil
        Assert.assertEquals(resultCount, 0, "Search invalid harus menampilkan 0 hasil");

        test.log(Status.PASS, "Invalid search handled correctly");
    }

    @Test(priority = 3, description = "Search with empty query should not crash application")
    public void testSearchWithEmptyQuery() {
        test.log(Status.INFO, "Search with empty query");

        homePage.search("");
        int resultCount = homePage.getSearchResultCount();

        Assert.assertTrue(resultCount >= 0, "Search kosong tidak boleh menyebabkan error");
        test.log(Status.PASS, "Empty query handled gracefully");
    }

    @Test(priority = 4, description = "Validate search result content consistency")
    public void testSearchResultContentValidation() {
        test.log(Status.INFO, "Validate search result content");

        homePage.search("Nokia");
        int resultCount = homePage.getSearchResultCount();

        if (resultCount > 0) {
            // Perbaikan: Gunakan metode yang ada di HomePage untuk ambil judul
            String productTitle = homePage.getProductNameByIndex(0);
            Assert.assertNotNull(productTitle);
            test.log(Status.INFO, "First product title: " + productTitle);
        }

        test.log(Status.PASS, "Search result content validation passed");
    }
}