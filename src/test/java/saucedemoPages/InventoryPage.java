package saucedemoPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage extends BasePage {

	public InventoryPage(WebDriver driver) {
		super(driver);
	}

	private By hamburgerBtn = By.xpath("//div[@class='bm-burger-button']");
	private By logoutBtn = By.id("logout_sidebar_link");

	public void clickonhamburgerBtn() {
		this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(hamburgerBtn))).click();
	}

	public void clickonLogoutBtn() {
		this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(logoutBtn))).click();
	}

}
