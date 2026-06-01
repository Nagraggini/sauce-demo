package saucedemoTests;

import java.io.File;
import java.io.PrintStream;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import saucedemoPages.InventoryPage;
import saucedemoPages.LoginPage;

class BaseTest {

	protected WebDriver driver;
	// 10 másodperces várakoztatás deklarálása.
	protected WebDriverWait wait;

	// Logoláshoz.
	protected static final Logger logger = LogManager.getLogger(BaseTest.class);

	@BeforeEach
	void setUp() {
		ChromeOptions options = new ChromeOptions();
		headlessMode(options);
		// headMode(); // Elhasal a GitHub Actions.

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	ChromeOptions headlessMode(ChromeOptions options) {
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--headless=new");
		options.addArguments("--incognito");

		// Teljesen elnémítja a CDP verziókereső hibaüzeneteit
		java.util.logging.Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder")
				.setLevel(java.util.logging.Level.OFF);

		// Az összes Selenium figyelmeztetés elnémítása
		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);

		// Itt inicializáljuk a LOKÁLIS drivert.
		// Ezzel nem nyílik meg a böngésző.
		driver = new ChromeDriver(options);
		return options;
	}

	/** Ha ezt állítod be, akkor a headlessMode-ot ne hívd meg. */
	void headMode() {
		// Ezzel megnyílik a böngésző, viszont a GitHub Actions el fog hasalni!

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");

		driver = new ChromeDriver(options);

		// A GitHub Actions elhasal ezzel, mert nincsen böngésző, amit maximalizálni
		// lehetne.
		driver.manage().window().maximize();

		// Log fájl létrehozása.
		try {
			// Létrehozunk egy log fájlt a projekt gyökerében
			PrintStream fileOut = new PrintStream(new File("test_output.log"));

			// Átirányítjuk a System.out-ot a fájlra.
			System.setOut(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Bejelentkezés. Felhnév: standard_user */
	public void login(LoginPage loginPage) {
		loginPage.openPage("https://www.saucedemo.com");
		String username = "standard_user";
		String password = "secret_sauce";
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

	void cleanUp(InventoryPage inventoryPage) {
		logger.info("\n -- Before clean up current URL: {}", driver.getCurrentUrl());

		// Takarítás.
		inventoryPage.openHamburgerMenu();
		inventoryPage.clickonResetAppStateBtn();

		// Kijelentkezés
		inventoryPage.clickonLogoutBtn();

	}

	void onlyForChecking() {
		// Csak ellenőrzéshez.
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
