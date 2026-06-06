package base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.ConfigReader;
import driver.DriverFactory;
import pages.InventoryPage;
import pages.LoginPage;

public class BaseTest {

	protected WebDriver driver;
	// 10 másodperces várakoztatás deklarálása.
	protected WebDriverWait wait;

	// Logoláshoz.
	protected static final Logger logger = LogManager.getLogger(BaseTest.class);

	@BeforeEach
	void setUp() {
		driver = DriverFactory.createDriver(true); // headless CI-ben
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	/** Bejelentkezés. Felhnév: standard_user */
	public void login(LoginPage loginPage) {
		// TODO mindenhol a base url-t kéne használni.
		loginPage.openPage("https://www.saucedemo.com");
		String username = "standard_user";
		String password = ConfigReader.get("PASSWORD");
		loginPage.fillInputs(username, password);
		loginPage.clickOnLoginBtn();
	}

	@AfterEach
	void tearDown() {

		// Bezárja az összes ablakot és teljesen leállítja a WebDriver-t.
		if (driver != null) {
			driver.quit();
		}
	}

	protected void cleanUp(InventoryPage inventoryPage) {
		// logger.info("\n -- Before clean up current URL: {}", driver.getCurrentUrl());

		// Takarítás.
		inventoryPage.openHamburgerMenu();
		inventoryPage.clickonResetAppStateBtn();

		// Kijelentkezés
		inventoryPage.clickonLogoutBtn();

	}

	protected void onlyForChecking() {
		// Csak ellenőrzéshez.
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
