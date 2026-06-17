package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;

import base.BaseTest;
import config.ConfigReader;
import data.UserDataProvider;
import pages.LoginPage;
import pages.MenuComponent;

class LoginTest extends BaseTest {

	@Test
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Üresen hagyott input mezőkkel történi bejelentkezés.")
	void emptyInputFieldsAndTryToLogin() {
		assertEquals("Epic sadface: Username is required", new LoginPage(driver).openPage(ConfigReader.get("BASE_URL"))
				.clickOnLogin().getErrorMessage(), "Hibaüzenet nem egyezik meg.");
	}

	@Test
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Csak felhasználónévvel történő belépési kísérlet.")
	void tryToLoginWithOnlyUsername() {
		assertEquals("Epic sadface: Password is required",
				new LoginPage(driver).openPage(ConfigReader.get("BASE_URL")).fillInputs(ConfigReader
						.get("USERNAME"), "")
						.clickOnLogin().getErrorMessage(),
				"Hibaüzenet nem egyezik meg.");
	}

	@Test
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Csak jelszóval törtéső belépési kísérlet.")
	void tryToLoginWithOnlyPassword() {
		assertEquals("Epic sadface: Username is required",
				new LoginPage(driver).openPage(ConfigReader.get("BASE_URL")).fillInputs("", ConfigReader
						.get("PASSWORD"))
						.clickOnLogin().getErrorMessage(),
				"Hibaüzenet nem egyezik meg.");
	}

	@Test
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Hibás felhasználónévvel történő belépés.")
	void tryToLoginWithIncorrectUsername() {
		assertEquals("Epic sadface: Username and password do not match any user in this service",
				new LoginPage(driver).openPage(ConfigReader.get("BASE_URL")).fillInputs("12345", ConfigReader
						.get("PASSWORD"))
						.clickOnLogin().getErrorMessage(),
				"Hibaüzenet nem egyezik meg, ha hibás a felhasználónév.");
	}

	@Test
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Hibás jelszóval történő belépés.")
	void tryToLoginWithIncorrectPassword() {
		assertEquals("Epic sadface: Username and password do not match any user in this service",
				new LoginPage(driver).openPage(ConfigReader.get("BASE_URL")).fillInputs(ConfigReader
						.get("USERNAME"),
						ConfigReader.get(
								"WRONG_PASSWORD"))
						.clickOnLogin().getErrorMessage(),
				"Hibaüzenet nem egyezik meg, ha hibás a felhasználónév.");
	}

	@ParameterizedTest
	@CsvSource({ "standard_user", "problem_user", "performance_glitch_user", "error_user", "visual_user" })
	@Tag("regression")
	@DisplayName("Összes felhasználónév ellenőrzése.")
	void succesfulLoginWithAllUsernamesAndPws(String username) {
		assertEquals(ConfigReader.get("BASE_URL") + "/inventory.html", new LoginPage(driver).openPage(ConfigReader
				.get("BASE_URL")).login(username, ConfigReader.get(
						"PASSWORD"))
				.getCurrentUrl(),
				"Nem sikerült bejelentkezni.");

		// Cleanup minden teszt végén.
		new MenuComponent(driver).resetAppState().logout();
	}

	@ParameterizedTest
	@ArgumentsSource(UserDataProvider.class)
	@Tag("regression")
	@DisplayName("Bejelentkezések ellenőrzése UserDataProvider segítségével.")
	void loginWithProvider(String username, String password, String expectedResult, boolean shouldSucceed) {
		if (shouldSucceed) {
			// Ha sikeresnek kell lennie, az URL-t ellenőrizzük
			assertEquals(expectedResult,
					new LoginPage(driver).openPage(
							ConfigReader.get("BASE_URL")).login(username, password).getCurrentUrl(),
					"Nem sikerült a bejelentkezés: " + username);

			// Cleanup minden teszt végén.
			new MenuComponent(driver).resetAppState().logout();
		} else {
			// Ha sikertelen (pl. locked_out_user), a hibaüzenetet ellenőrizzük
			assertEquals(expectedResult, new LoginPage(driver).openPage(
					ConfigReader.get("BASE_URL")).fillInputs(username, password).clickOnLogin()
					.getErrorMessage(),
					"Nem a várt hibaüzenet jelent meg ennél a felhasználónál: " + username);
		}
	}

	@Test
	@Tag("regression")
	@DisplayName("Zárolt felhasználó ellenőrzése.")
	void lockedOutUserShouldNotBeAbleToLogin() {
		assertFalse(new LoginPage(driver).openPage(ConfigReader.get("BASE_URL"))
				.fillInputs("locked_out_user", ConfigReader.get("PASSWORD")).clickOnLogin().getCurrentUrl()
				.contains(
						"inventory"),
				"Sikerült bejelentkezni a zárolt felhasználóval!");
	}

	@Test
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Zárolt felhasználó belépési kísérlet esetén megjelenik-e a hibaüzenet.")
	void checkErrorMessageLockedOutUserLogin() {
		assertEquals("Epic sadface: Sorry, this user has been locked out.", new LoginPage(driver).openPage(
				ConfigReader.get("BASE_URL")).fillInputs("locked_out_user", ConfigReader.get("PASSWORD")).clickOnLogin()
				.getErrorMessage(),
				"Nem jelent meg a hibaüzenet, amikor a zárolt felhasználóval próbált belépni.");
	}

	@Test
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Lecsekkoljuk, hogy betöltödik-e az inventory oldal, ha nem vagyunk belépve.")
	void checkLockedOutUserLoginErrorMessage() {
		assertEquals(
				"Epic sadface: You can only access '/inventory.html' when you are logged in.", new LoginPage(driver)
						.openPage(
								ConfigReader.get("BASE_URL")
										+ "/inventory.html")
						.getErrorMessage(),
				"Hibaüzenet nem egyezik meg.");
	}

}
