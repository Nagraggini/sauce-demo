package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import config.ConfigReader;
import pages.LoginPage;

public class LoginLockoutTest extends BaseTest {

    @Test
    @DisplayName("Kizárt felhasználó (locked_out_user) bejelentkezési hibaüzenetének ellenőrzése")
    public void testLockedOutUserLogin() {
        LoginPage loginPage = new LoginPage(driver).openPage(ConfigReader.getBaseUrl());
        loginPage.fillInputs("locked_out_user", ConfigReader.getPassword()).clickOnLogin();

        String errorMessage = loginPage.getErrorMessage();
        Assertions.assertTrue(errorMessage.contains("Epic sadface: Sorry, this user has been locked out."),
                "Nem jelenik meg a kizárt felhasználóra vonatkozó hibaüzenet.");
    }
}
