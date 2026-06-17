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
		driver = DriverFactory.createDriver(System.getProperty("CI") != null); // headless CI-ben
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// POM alapelve szerint az oldalváltó műveletek adják vissza eredményül a
	// következő oldal Page objektumát.
	/**
	 * Bejelentkezés alapértelmezett standard felhasználóval.
	 * Visszaadja az InventoryPage-et a könnyebb láncolhatóságért.
	 */
	public InventoryPage login() {
		return new LoginPage(driver).openPage(ConfigReader.getBaseUrl()).login(ConfigReader.getUsername(),
				ConfigReader.getPassword());
	}

	@AfterEach
	void tearDown() {
		// Bezárja az összes ablakot és teljesen leállítja a WebDriver-t.
		if (driver != null) {
			driver.quit();
		}
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
