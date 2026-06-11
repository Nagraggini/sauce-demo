package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;

import base.BaseTest;
import config.ConfigReader;
import data.UserDataProvider;
import pages.InventoryPage;
import pages.LoginPage;

class LoginTest extends BaseTest {

	@Test
	@DisplayName("Üresen hagyott input mezőkkel történi bejelentkezés.")
	void emptyInputFieldsAndTryToLogin() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage(ConfigReader.get("BASE_URL"));

		loginPage.clickOnLoginBtn();

		String expectedErrorMessage = "Epic sadface: Username is required";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertTrue(expectedErrorMessage.equals(actualErrorMessage), "Hibaüzenet nem egyezik meg.");

	}

	@Test
	@DisplayName("Csak felhasználónévvel törtéső belépési kísérlet.")
	void tryToLoginWithOnlyUsername() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage(ConfigReader.get("BASE_URL"));

		String username = "standard_user";
		String password = "";
		loginPage.fillInputs(username, password);

		loginPage.clickOnLoginBtn();

		String expectedErrorMessage = "Epic sadface: Password is required";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertTrue(expectedErrorMessage.equals(actualErrorMessage),
				"Hibaüzenet nem egyezik meg. " + actualErrorMessage);
	}

	@Test
	@DisplayName("Csak jelszóval törtéső belépési kísérlet.")
	void tryToLoginWithOnlyPassword() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage(ConfigReader.get("BASE_URL"));

		String username = "";
		String password = ConfigReader.get("PASSWORD");
		loginPage.fillInputs(username, password);

		loginPage.clickOnLoginBtn();

		String expectedErrorMessage = "Epic sadface: Username is required";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertTrue(expectedErrorMessage.equals(actualErrorMessage),
				"Hibaüzenet nem egyezik meg. " + actualErrorMessage);
	}

	@Test
	@DisplayName("Hibás felhasználónévvel történő belépés.")
	void tryToLoginWithIncorrectUsername() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage(ConfigReader.get("BASE_URL"));

		String username = "12345";
		String password = ConfigReader.get("PASSWORD");
		loginPage.fillInputs(username, password);

		loginPage.clickOnLoginBtn();

		String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertTrue(expectedErrorMessage.equals(actualErrorMessage),
				"Hibaüzenet nem egyezik meg, ha hibás a felhasználónév. " + actualErrorMessage);
	}

	@Test
	@DisplayName("Hibás jelszóval történő belépés.")
	void tryToLoginWithIncorrectPassword() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage(ConfigReader.get("BASE_URL"));

		String username = ConfigReader.get("USERNAME");
		String password = ConfigReader.get("WRONG_PASSWORD");
		loginPage.fillInputs(username, password);

		loginPage.clickOnLoginBtn();

		String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertTrue(expectedErrorMessage.equals(actualErrorMessage),
				"Hibaüzenet nem egyezik meg, ha hibás a jelszó. " + actualErrorMessage);
	}

	@ParameterizedTest
	@CsvSource({ "standard_user", "problem_user", "performance_glitch_user", "error_user", "visual_user" })
	@DisplayName("Összes felhasználónév ellenőrzése.")
	void succesfulLoginWithAllUsernamesAndPws(String username) {
		LoginPage loginPage = new LoginPage(driver);

		loginPage.openPage(ConfigReader.get("BASE_URL"));
		String password = ConfigReader.get("PASSWORD");
		loginPage.fillInputs(username, password);
		loginPage.clickOnLoginBtn();

		String expectedURL = ConfigReader.get("BASE_URL") + "/inventory.html";
		String actualURL = loginPage.getCurrentUrl();

		assertEquals(expectedURL, actualURL, "Nem sikerült bejelentkezni.");

		// Kijelentkezés.
		InventoryPage inventoryPage = new InventoryPage(driver);
		inventoryPage.openHamburgerMenu();
		inventoryPage.clickOnLogoutBtn();
	}

	@ParameterizedTest
	@ArgumentsSource(UserDataProvider.class)
	@DisplayName("Bejelentkezések ellenőrzése UserDataProvider segítségével.")
	void loginWithProvider(String username, String password, String expectedResult, boolean shouldSucceed) {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage(ConfigReader.get("BASE_URL"));

		loginPage.fillInputs(username, password);
		loginPage.clickOnLoginBtn();

		if (shouldSucceed) {
			// Ha sikeresnek kell lennie, az URL-t ellenőrizzük
			assertEquals(expectedResult, loginPage.getCurrentUrl(), "Nem sikerült a bejelentkezés: " + username);

			// Kijelentkezés a takarításhoz
			InventoryPage inventoryPage = new InventoryPage(driver);
			inventoryPage.openHamburgerMenu();
			inventoryPage.clickOnLogoutBtn();
		} else {
			// Ha sikertelen (pl. locked_out_user), a hibaüzenetet ellenőrizzük
			assertEquals(expectedResult, loginPage.getErrorMessage(), "Nem a várt hibaüzenet jelent meg: " + username);
		}
	}

	@Test
	@DisplayName("Zárolt felhasználó ellenőrzése.")
	void checkLockedOutUserLogin() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage(ConfigReader.get("BASE_URL"));

		String username = "locked_out_user";
		String password = ConfigReader.get("PASSWORD");
		loginPage.fillInputs(username, password);

		loginPage.clickOnLoginBtn();

		String notExpectedURL = ConfigReader.get("BASE_URL") + "/inventory.html";
		String actualURL = loginPage.getCurrentUrl();

		assertFalse(notExpectedURL.equals(actualURL), "Sikerült bejelentkezni a zárolt felhasználóval");
	}

	@Test
	@DisplayName("Zárolt felhasználó belépési kísérlet esetén megjelenik-e a hibaüzenet.")
	void checkErrorMessageLockedOutUserLogin() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage(ConfigReader.get("BASE_URL"));

		String username = "locked_out_user";
		String password = ConfigReader.get("PASSWORD");
		loginPage.fillInputs(username, password);

		loginPage.clickOnLoginBtn();

		String expectedErrorMessage = "Epic sadface: Sorry, this user has been locked out.";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertTrue(expectedErrorMessage.equals(actualErrorMessage),
				"Nem jelent meg a hibaüzenet, amikor a zárolt felhasználóval próbált belépni.");
	}

	@Test
	@DisplayName("Lecsekkoljuk, hogy betöltödik-e az inventory oldal, ha nem vagyunk belépve.")
	void checkLockedOutUserLoginErrorMessage() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage(ConfigReader.get("BASE_URL") + "/inventory.html");

		String expectedErrorMessage = "Epic sadface: You can only access '/inventory.html' when you are logged in.";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertEquals(ConfigReader.get("BASE_URL") + "/", loginPage.getCurrentUrl(), "Az url cím nem jó!");
		assertTrue(expectedErrorMessage.equals(actualErrorMessage), "Hibaüzenet nem egyezik meg.");
	}

}
