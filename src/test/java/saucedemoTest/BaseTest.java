package saucedemoTest;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

class BaseTest {

	protected WebDriver driver;
	// 10 másodperces várakoztatás deklarálása.
	protected WebDriverWait wait;

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

		// Teljesen elnémítja a CDP verziókereső hibaüzeneteit
		java.util.logging.Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder")
				.setLevel(java.util.logging.Level.OFF);

		// Az összes Selenium figyelmeztetés elnémítása
		java.util.logging.Logger.getLogger("org.openqa.selenium")
				.setLevel(java.util.logging.Level.SEVERE);

		// Itt inicializáljuk a LOKÁLIS drivert.
		// Ezzel nem nyílik meg a böngésző.
		driver = new ChromeDriver(options);
		return options;
	}

	/** Ha ezt állítod be, akkor a headlessMode-ot ne hívd meg. */
	void headMode() {
		// Ezzel megnyílik a böngésző, viszont a GitHub Actions el fog hasalni!
		driver = new ChromeDriver();

		// A GitHub Actions elhasal ezzel, mert nincsen böngésző, amit maximalizálni
		// lehetne.
		driver.manage().window().maximize();
	}

	@AfterEach
	void tearDown() {

		// Bezárja az összes ablakot és teljesen leállítja a WebDriver-t.
		if (driver != null) {
			driver.quit();
		}
	}
}
