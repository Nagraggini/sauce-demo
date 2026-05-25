package saucedemoPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage extends BasePage {

	public InventoryPage(WebDriver driver) {
		super(driver);
	}

	private final By hamburgerBtn = By.xpath("//div[@class='bm-burger-button']");
	private final By logoutBtn = By.id("logout_sidebar_link");

	public void clickonhamburgerBtn() {
		this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(hamburgerBtn))).click();
	}

	public void clickonLogoutBtn() {
		this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(logoutBtn))).click();
	}

	public double getPriceofAnItem(String itemName) {
		// * // div[@data-test='inventory-list']//div[text()='Sauce Labs Backpack']
		By label = By.xpath("// div[@data-test='inventory-list']//div[text()='" + itemName + "']");

		this.wait.until(ExpectedConditions.visibilityOfElementLocated(label));

		/*
		 * //div[@data-test='inventory-list']//div[text()='Sauce Labs
		 * Backpack']/following::div/div[@class='inventory_item_price']
		 */
		// $29.99
		String priceWithDollar = this.driver.findElement(By.xpath(
				"//div[@data-test='inventory-list']//div[text()='" + itemName
						+ "']/following::div/div[@class='inventory_item_price']"))
				.getText();

		double price = Double.parseDouble(priceWithDollar.substring(1));

		return price;
	}

	public void addToCart(String itemName) {
		/*
		 * // div[@data-test='inventory-list']//div[text()='Sauce Labs
		 * Backpack']/following::button
		 */
		By addToCartBtn = By
				.xpath("// div[@data-test='inventory-list']//div[text()='" + itemName + "']/following::button");

		wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();

	}

}
