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
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import pages.LoginPage;
import pages.MenuComponent;

class LoginTest extends BaseTest {

	@Test
	@Epic("E-Commerce Application")
	@Feature("Authentication")
	@Story("Invalid Login")
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Üresen hagyott input mezőkkel történi bejelentkezés.")
	void emptyInputFieldsAndTryToLogin() {
		assertEquals("Epic sadface: Username is required", new LoginPage(driver).openPage(ConfigReader
				.getBaseUrl())
				.clickOnLogin().getErrorMessage(), "Hibaüzenet nem egyezik meg.");
	}

	@Test
	@Epic("E-Commerce Application")
	@Feature("Authentication")
	@Story("Invalid Login")
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Csak felhasználónévvel történő belépési kísérlet.")
	void tryToLoginWithOnlyUsername() {
		assertEquals("Epic sadface: Password is required",
				new LoginPage(driver).openPage(ConfigReader
						.getBaseUrl()).fillInputs(ConfigReader
								.getUsername(), "")
						.clickOnLogin().getErrorMessage(),
				"Hibaüzenet nem egyezik meg.");
	}

	@Test
	@Epic("E-Commerce Application")
	@Feature("Authentication")
	@Story("Invalid Login")
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Csak jelszóval törtéső belépési kísérlet.")
	void tryToLoginWithOnlyPassword() {
		assertEquals("Epic sadface: Username is required",
				new LoginPage(driver).openPage(ConfigReader
						.getBaseUrl()).fillInputs("", ConfigReader
								.get("PASSWORD"))
						.clickOnLogin().getErrorMessage(),
				"Hibaüzenet nem egyezik meg.");
	}

	@Test
	@Epic("E-Commerce Application")
	@Feature("Authentication")
	@Story("Invalid Login")
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Hibás felhasználónévvel történő belépés.")
	void tryToLoginWithIncorrectUsername() {
		assertEquals("Epic sadface: Username and password do not match any user in this service",
				new LoginPage(driver).openPage(ConfigReader
						.getBaseUrl()).fillInputs("12345", ConfigReader
								.getPassword())
						.clickOnLogin().getErrorMessage(),
				"Hibaüzenet nem egyezik meg, ha hibás a felhasználónév.");
	}

	@Test
	@Epic("E-Commerce Application")
	@Feature("Authentication")
	@Story("Invalid Login")
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Hibás jelszóval történő belépés.")
	void tryToLoginWithIncorrectPassword() {
		assertEquals("Epic sadface: Username and password do not match any user in this service",
				new LoginPage(driver).openPage(ConfigReader
						.getBaseUrl()).fillInputs(ConfigReader
								.get("USERNAME"),
								ConfigReader.get(
										"WRONG_PASSWORD"))
						.clickOnLogin().getErrorMessage(),
				"Hibaüzenet nem egyezik meg, ha hibás a felhasználónév.");
	}

	@ParameterizedTest
	@CsvSource({ "standard_user", "problem_user", "performance_glitch_user", "error_user", "visual_user" })
	@Epic("E-Commerce Application")
	@Feature("Authentication")
	@Story("Successful Login")
	@Tag("regression")
	@Tag("smoke")
	@DisplayName("Összes felhasználónév ellenőrzése.")
	void succesfulLoginWithAllUsernamesAndPws(String username) {
		assertEquals(ConfigReader.getBaseUrl() + "/inventory.html", new LoginPage(driver).openPage(ConfigReader
				.getBaseUrl()).login(username, ConfigReader
						.getPassword())
				.getCurrentUrl(),
				"Nem sikerült bejelentkezni.");

		new MenuComponent(driver).resetAppState().logout();
	}

	@ParameterizedTest
	@ArgumentsSource(UserDataProvider.class)
	@Epic("E-Commerce Application")
	@Feature("Authentication")
	@Story("Successful Login")
	@Tag("regression")
	@DisplayName("Bejelentkezések ellenőrzése UserDataProvider segítségével.")
	void loginWithProvider(String username, String password, String expectedResult, boolean shouldSucceed) {
		if (shouldSucceed) {
			// Ha sikeresnek kell lennie, az URL-t ellenőrizzük
			assertEquals(expectedResult,
					new LoginPage(driver).openPage(
							ConfigReader
									.getBaseUrl())
							.login(username, password).getCurrentUrl(),
					"Nem sikerült a bejelentkezés: " + username);

			new MenuComponent(driver).resetAppState().logout();
		} else {
			// Ha sikertelen (pl. locked_out_user), a hibaüzenetet ellenőrizzük
			assertEquals(expectedResult, new LoginPage(driver).openPage(
					ConfigReader
							.getBaseUrl())
					.fillInputs(username, password).clickOnLogin()
					.getErrorMessage(),
					"Nem a várt hibaüzenet jelent meg ennél a felhasználónál: " + username);
		}
	}

	@Test
	@Epic("E-Commerce Application")
	@Feature("Authentication")
	@Story("Locked User Login")
	@Tag("regression")
	@DisplayName("Zárolt felhasználó ellenőrzése.")
	void lockedOutUserShouldNotBeAbleToLogin() {
		assertFalse(new LoginPage(driver).openPage(ConfigReader
				.getBaseUrl())
				.fillInputs("locked_out_user", ConfigReader.get("PASSWORD")).clickOnLogin().getCurrentUrl()
				.contains(
						"inventory"),
				"Sikerült bejelentkezni a zárolt felhasználóval!");
	}

	@Test
	@Epic("E-Commerce Application")
	@Feature("Authentication")
	@Story("Locked User Login")
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Zárolt felhasználó belépési kísérlet esetén megjelenik-e a hibaüzenet.")
	void checkErrorMessageLockedOutUserLogin() {
		assertEquals("Epic sadface: Sorry, this user has been locked out.", new LoginPage(driver).openPage(
				ConfigReader
						.getBaseUrl())
				.fillInputs("locked_out_user", ConfigReader.get("PASSWORD")).clickOnLogin()
				.getErrorMessage(),
				"Nem jelent meg a hibaüzenet, amikor a zárolt felhasználóval próbált belépni.");
	}

	@Test
	@Epic("E-Commerce Application")
	@Feature("Authentication")
	@Story("Invalid Login")
	@Tag("ui")
	@Tag("regression")
	@DisplayName("Lecsekkoljuk, hogy betöltödik-e az inventory oldal, ha nem vagyunk belépve.")
	void checkLockedOutUserLoginErrorMessage() {
		assertEquals(
				"Epic sadface: You can only access '/inventory.html' when you are logged in.", new LoginPage(driver)
						.openPage(
								ConfigReader.getBaseUrl()
										+ "/inventory.html")
						.getErrorMessage(),
				"Hibaüzenet nem egyezik meg.");
	}

}
