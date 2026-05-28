package saucedemoPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage extends BasePage {
	// ctrl+shift+c -> A chromeban megnyílik a dev tools és rögtön lokátort tudsz
	// keresni.
	public InventoryPage(WebDriver driver) {
		super(driver);
	}

	// Hamburger menü elemek.
	private final By hamburgerBtn = By.id("react-burger-menu-btn");
	private final By allItemsBtn = By.id("inventory_sidebar_link");
	private final By aboutBtn = By.id("about_sidebar_link");
	private final By logoutBtn = By.id("logout_sidebar_link");
	private final By resetAppStateBtn = By.id("reset_sidebar_link");
	private final By closeHamburgerMenu = By.id("react-burger-cross-btn");

	// Fejléc és logó.
	private final By appLogo = By.xpath("//div[@class='app_logo' and contains(text(),'Swag')]");
	private final By productsLbl = By.xpath("//*/span[contains(text(),'Products')]");

	// Kosár elemek.
	private final By shoppingCartBtn = By.className("shopping_cart_link");
	private final By shoppingCartBadge = By.cssSelector("span[data-test='shopping-cart-badge']");

	// TODO
	public void openHamburgerMenu() {
		WebElement menu = driver.findElement(hamburgerBtn);
		logger.info("\n menu.isDisplayed() : {}, menu.isEnabled()) : {}", menu.isDisplayed(), menu.isEnabled());
		// this.wait.until(ExpectedConditions.elementToBeClickable(hamburgerBtn));
		this.driver.findElement(hamburgerBtn).click();
	}

	public void clickonLogoutBtn() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(logoutBtn));
		this.wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
	}

	public void clickonResetAppStateBtn() {
		// wait.until(ExpectedConditions.visibilityOfElementLocated(resetAppStateBtn));

		wait.until(ExpectedConditions.elementToBeClickable(resetAppStateBtn))
				.click();
	}

	// TODO FIXME
	public void clickOnshoppingCartBtn() {
		logger.info("\n Expected: https://www.saucedemo.com/cart.html ; Current URL: {}", driver.getCurrentUrl());
		this.wait.until(ExpectedConditions.elementToBeClickable(shoppingCartBtn)).click();
	}

	public int getShoppingCartBadgeNumber() {

		// Ha üres a kosár, akkor sem lesz error.
		if (driver.findElements(shoppingCartBadge).isEmpty()) {
			return 0;
		}

		int shoppingCartBadgeNumber = Integer.parseInt(
				this.wait.until(ExpectedConditions.elementToBeClickable(shoppingCartBadge))
						.getText());
		return shoppingCartBadgeNumber;
	}

	public double getPriceofAnItem(String itemName) {
		// * // div[@data-test='inventory-list']//div[text()='Sauce Labs Backpack']
		By label = By.xpath("//div[@data-test='inventory-list']//div[text()='" + itemName + "']");

		this.wait.until(ExpectedConditions.visibilityOfElementLocated(label));

		// $29.99
		/*
		 * String priceWithDollar = this.driver.findElement(By.xpath(
		 * "//div[@data-test='inventory-list']//div[text()='" + itemName
		 * + "']/following::div/div[@class='inventory_item_price']"))
		 * .getText();
		 */

		String priceWithDollar = this.driver.findElement(By.xpath(
				"//div[@class='inventory_item' and .//div[text()='" + itemName
						+ "']]//div[@class='inventory_item_price']"))
				.getText();

		double price = Double.parseDouble(priceWithDollar.substring(1));

		return price;
	}

	// TODO FIXME
	public void addToCart(String itemName) {
		/*
		 * By addToCartBtn = By
		 * .xpath("//div[@data-test='inventory-list']//div[text()='" + itemName
		 * + "']/following::button[1][text()='Add to cart']");
		 */
		By addToCartBtn = By.xpath(
				"//div[@class='inventory_item' and .//div[normalize-space()='"
						+ itemName + "']]//button");

		logger.info("\n-- Add to cart -> itemName: {}, addToCartBtn text: {}", itemName,
				this.driver.findElement(addToCartBtn).getText());

		wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
		logger.info("\n-- Expected btn text: Remove -> itemName: {}, actual btn text: {}", itemName,
				this.driver.findElement(addToCartBtn).getText());
	}

}
