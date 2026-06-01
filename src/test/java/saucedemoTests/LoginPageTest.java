package saucedemoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import saucedemoPages.InventoryPage;
import saucedemoPages.LoginPage;

//https://www.youtube.com/watch?v=XyBxEnyBb0A

class LoginPageTest extends BaseTest {

	LoginPage loginPage;

	@Test
	@DisplayName("Üresen hagyott input mezőkkel történi bejelentkezés.")
	void emptyImputFieldsAndTryToLogin() {
		loginPage = new LoginPage(driver);
		loginPage.openPage("https://www.saucedemo.com/");

		loginPage.clickOnLoginBtn();

		String expectedErrorMessage = "Epic sadface: Username is required";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertTrue(expectedErrorMessage.equals(actualErrorMessage), "Hibaüzenet nem egyezik meg.");

	}

	@Test
	@DisplayName("Csak felhasználónévvel törtéső belépési kísérlet.")
	void tryToLoginWithOnlyUsername() {
		loginPage = new LoginPage(driver);
		loginPage.openPage("https://www.saucedemo.com/");

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
		loginPage = new LoginPage(driver);
		loginPage.openPage("https://www.saucedemo.com/");

		String username = "";
		String password = System.getenv("PASSWORD");
		loginPage.fillInputs(username, password);

		loginPage.clickOnLoginBtn();

		String expectedErrorMessage = "Epic sadface: Username is required";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertTrue(expectedErrorMessage.equals(actualErrorMessage),
				"Hibaüzenet nem egyezik meg. " + actualErrorMessage);
	}

	@ParameterizedTest
	@CsvSource({ "standard_user", "problem_user", "performance_glitch_user", "error_user", "visual_user" })
	@DisplayName("Összes felhasználónév ellenőrzése.")
	void succesfulLoginWithAllUsernamesAndPws(String username) {
		loginPage = new LoginPage(driver);

		loginPage.openPage("https://www.saucedemo.com");
		String password = System.getenv("PASSWORD");
		loginPage.fillInputs(username, password);
		loginPage.clickOnLoginBtn();

		String expectedURL = "https://www.saucedemo.com/inventory.html";
		String actualURL = loginPage.getURL();

		assertEquals(expectedURL, actualURL, "Nem sikerült bejelentkezni.");

		// Kijelentkezés.
		InventoryPage inventoryPage = new InventoryPage(driver);
		inventoryPage.openHamburgerMenu();
		inventoryPage.clickonLogoutBtn();
	}

	@Test
	@DisplayName("Hibás felhasználónévvel történő belépés.")
	void tryToLoginWithIncorrectUsername() {
		loginPage = new LoginPage(driver);
		loginPage.openPage("https://www.saucedemo.com/");

		String username = "12345";
		String password = System.getenv("PASSWORD");
		loginPage.fillInputs(username, password);

		loginPage.clickOnLoginBtn();

		String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertTrue(expectedErrorMessage.equals(actualErrorMessage),
				"Hibaüzenet nem egyezik meg. " + actualErrorMessage);
	}

	@Test
	@DisplayName("Lecsekkoljuk, hogy betöltödik-e az inventory oldal, ha nem vagyunk belépve.")
	void checkLockedOutUserLoginRefreshAndErrorMessage() {
		loginPage = new LoginPage(driver);
		loginPage.openPage("https://www.saucedemo.com/inventory.html");

		String expectedErrorMessage = "Epic sadface: You can only access '/inventory.html' when you are logged in.";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertTrue(expectedErrorMessage.equals(actualErrorMessage), "Hibaüzenet nem egyezik meg.");
	}

	@Test
	@DisplayName("Zárolt felhasználó ellenőrzése.")
	void checkLockedOutUserLogin() {
		loginPage = new LoginPage(driver);
		loginPage.openPage("https://www.saucedemo.com");

		String username = "locked_out_user";
		String password = System.getenv("PASSWORD");
		loginPage.fillInputs(username, password);

		loginPage.clickOnLoginBtn();

		String notExpectedURL = "https://www.saucedemo.com/inventory.html";
		String actualURL = loginPage.getURL();

		assertFalse(notExpectedURL.equals(actualURL), "Sikerült bejelentkezni a zárolt felhasználóval");
	}

	@Test
	@DisplayName("Zárolt felhasználó belépési kísérlet esetén megjelenik-e a hibaüzenet.")
	void checkErrorMessageLockedOutUserLogin() {
		loginPage = new LoginPage(driver);
		loginPage.openPage("https://www.saucedemo.com");

		String username = "locked_out_user";
		String password = System.getenv("PASSWORD");
		loginPage.fillInputs(username, password);

		loginPage.clickOnLoginBtn();

		String expectedErrorMessage = "Epic sadface: Sorry, this user has been locked out.";
		String actualErrorMessage = loginPage.getErrorMessage();

		assertTrue(expectedErrorMessage.equals(actualErrorMessage),
				"Nem jelent meg a hibaüzenet, amikor a zárolt felhasználóval próbált belépni.");
	}

}
