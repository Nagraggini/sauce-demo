package saucedemoPages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	// KONSTRUKTOR: Ezen keresztül adjuk át a futó böngészőt a tesztből.
	public BasePage(WebDriver driver) {
		this.driver = driver;
		//Mivel a driver már nem null, most már biztonságosan létrehozhatjuk
		// a wait-et is!
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	public void openPage(String url) {
		this.driver.get(url);
		driver.manage().deleteAllCookies(); //Töröljük a sütiket.
	}

	public String getURL() {
		return this.driver.getCurrentUrl();
	}
	
	public void refreshPage() {
		this.driver.navigate().refresh();
	}

}
