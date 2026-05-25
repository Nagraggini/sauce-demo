package saucedemoPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

//https://www.youtube.com/watch?v=XyBxEnyBb0A

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	// Csak a bevitelimező "címét" (lokátorát) mentjük el az osztály tetején.
	private By usernameInput = By.xpath("//div[@class='login-box']/form/div/input[@placeholder='Username']");
	private By passwordInput = By.xpath("//div[@class='login-box']/form/div/input[@type='password']");
	private By loginBtn = By.xpath("//div[@class='login-box']/form/input[@id='login-button']");
	private By errorMessage = By.xpath("//div/form/div[@class='error-message-container error']/h3[@data-test='error']");

	public void waitsForComponents() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
		wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(loginBtn)));
	}

	public void fillInputs(String username, String password) {
		this.driver.findElement(usernameInput).sendKeys(username);
		this.driver.findElement(passwordInput).sendKeys(password);
	}

	public void clickonLoginBtn() {
		this.driver.findElement(loginBtn).click();
	}

	public String getErrorMessage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
		return this.driver.findElement(errorMessage).getText();
	}

}
