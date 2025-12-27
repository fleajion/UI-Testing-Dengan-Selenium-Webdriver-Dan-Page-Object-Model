package com.pratikum.testing.otomation.test.demo;

import com.aventstack.extentreports.Status;
import com.pratikum.testing.otomation.pages.HomePage;
import com.pratikum.testing.otomation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * QA Senior User Login Test Suite – Demoblaze
 * Coverage:
 * - Positive login
 * - Negative login (invalid username/password)
 * - Empty fields validation
 * - Session persistence
 * - Logout & security behavior
 */
public class UserLoginTest extends BaseTest {

    @Test(priority = 1, description = "Login sukses dengan kredensial valid")
    public void loginSuccess() {
        test.log(Status.INFO, "Mulai test login sukses");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        loginPage.goToHomePage();
        test.log(Status.INFO, "Buka halaman login");

        loginPage.login("tomsmith", "SuperSecretPassword!");
        test.log(Status.INFO, "Input credentials valid");

        Assert.assertTrue(loginPage.isLoginSuccess(), "Login harus berhasil");
        Assert.assertTrue(homePage.isUserLoggedIn(), "User harus terdeteksi login");

        test.log(Status.PASS, "LOGIN SUCCESS - User berhasil login");

        // Cleanup
        loginPage.logout();
        test.log(Status.INFO, "Logout untuk cleanup");
    }

    @Test(priority = 2, description = "Login gagal dengan password salah")
    public void loginFailedInvalidPassword() {
        test.log(Status.INFO, "Mulai test login gagal - password salah");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToHomePage();
        loginPage.login("tomsmith", "WrongPassword");
        test.log(Status.INFO, "Input password salah");

        String errorMessage = loginPage.openLoginPopup();
        Assert.assertFalse(errorMessage.isEmpty(), "Pesan error harus muncul");
        Assert.assertTrue(errorMessage.toLowerCase().contains("invalid"),
                "Pesan error harus menjelaskan login gagal");

        test.log(Status.PASS, "LOGIN FAILED sesuai ekspektasi: " + errorMessage);
    }

    @Test(priority = 3, description = "Login gagal dengan username kosong")
    public void loginWithEmptyUsername() {
        test.log(Status.INFO, "Mulai test username kosong");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToHomePage();
        loginPage.login("", "SuperSecretPassword!");
        test.log(Status.INFO, "Username dikosongkan");

        String errorMessage = loginPage.getLoginError();
        Assert.assertFalse(errorMessage.isEmpty(), "Error validasi harus muncul");

        test.log(Status.PASS, "VALIDATION WORKING - Username kosong ditolak");
    }

    @Test(priority = 4, description = "Login gagal dengan password kosong")
    public void loginWithEmptyPassword() {
        test.log(Status.INFO, "Mulai test password kosong");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();
        loginPage.login("tomsmith", "");
        test.log(Status.INFO, "Password dikosongkan");

        Assert.assertFalse(loginPage.isLoginSuccess(), "Login tidak boleh berhasil");

        test.log(Status.PASS, "VALIDATION WORKING - Password kosong ditolak");
    }

    @Test(priority = 5, description = "Login gagal dengan username & password kosong")
    public void loginWithEmptyCredentials() {
        test.log(Status.INFO, "Mulai test semua field kosong");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();
        loginPage.login("", "");
        test.log(Status.INFO, "Username & password kosong");

        String errorMessage = loginPage.getLoginError();
        Assert.assertFalse(errorMessage.isEmpty(), "Error harus muncul");

        test.log(Status.PASS, "VALIDATION WORKING - Semua field kosong ditolak");
    }

    @Test(priority = 6, description = "Verifikasi session tetap aktif setelah navigasi")
    public void sessionPersistenceTest() {
        test.log(Status.INFO, "Mulai test session persistence");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        loginPage.goToLoginPage();
        loginPage.login("tomsmith", "SuperSecretPassword!");
        test.log(Status.INFO, "Login sukses");

        homePage.goToHomePage();
        test.log(Status.INFO, "Navigasi ke home");

        Assert.assertTrue(homePage.isUserLoggedIn(), "Session harus tetap aktif setelah navigasi");

        test.log(Status.PASS, "SESSION OK - User tetap login");

        loginPage.logout();
        test.log(Status.INFO, "Logout cleanup");
    }

    @Test(priority = 7, description = "Logout berhasil dan session berakhir")
    public void logoutFunctionalityTest() {
        test.log(Status.INFO, "Mulai test logout");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        loginPage.goToLoginPage();
        loginPage.login("tomsmith", "SuperSecretPassword!");
        Assert.assertTrue(loginPage.isLoginSuccess(), "Login harus sukses");

        loginPage.logout();
        test.log(Status.INFO, "Klik logout");

        Assert.assertTrue(loginPage.isLogoutSuccess(), "Logout harus berhasil");
        Assert.assertFalse(homePage.isUserLoggedIn(), "User harus logout");

        test.log(Status.PASS, "LOGOUT SUCCESS - Session berakhir");
    }

    @Test(priority = 8, description = "Security test: akses halaman protected tanpa login")
    public void accessProtectedPageWithoutLogin() {
        test.log(Status.INFO, "Mulai test akses halaman protected tanpa login");

        HomePage homePage = new HomePage(driver);
        homePage.goToHomePage();

        Assert.assertFalse(homePage.isUserLoggedIn(), "User tidak boleh login tanpa autentikasi");

        test.log(Status.PASS, "SECURITY OK - Akses tanpa login dibatasi");
    }
}
