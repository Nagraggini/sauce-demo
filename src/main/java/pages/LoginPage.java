package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	// Jobb a driver.getTitle();
	private final By pageTitleInTheHead = By.xpath("//head//title[text()='Swag Labs']");
	private final By pageTitleInTheBody = By.xpath("//body//div[text()='Swag Labs' and @class='login_logo']");

	// Csak a bevitelimező "címét" (lokátorát) mentjük el az osztály tetején.
	private final By usernameInput = By.xpath("//div[@class='login-box']//input[@placeholder='Username']");

	private final By passwordInput = By.xpath("//div[@class='login-box']//input[@type='password']");
	private final By loginBtn = By.xpath("//div[@class='login-box']/form/input[@id='login-button']");
	private final By errorMessage = By
			.xpath("//div/form/div[@class='error-message-container error']/h3[@data-test='error']");

	public LoginPage fillInputs(String username, String password) {
		type(usernameInput, username);
		type(passwordInput, password);
		return this;
	}

	public LoginPage clickOnLogin() {
		click(loginBtn);
		return this;
	}

	public String getErrorMessage() {
		return getText(errorMessage);
	}

	// POM alapelve szerint az oldalváltó műveletek adják vissza eredményül a
	// következő oldal Page objektumát.

	public InventoryPage login(String username, String password) {
		fillInputs(username, password);
		click(loginBtn);

		return new InventoryPage(driver);
	}

}
