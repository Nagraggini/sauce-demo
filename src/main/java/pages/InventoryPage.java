package pages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import base.BasePage;

public class InventoryPage extends BasePage {

	// Fejléc és logó.
	private final By appLogo = By.xpath("//div[@class='app_logo' and contains(text(),'Swag')]");
	private final By title = By.className("title");
	private final By changeOrderingSelect = By.cssSelector("[class='product_sort_container']");

	// Kosár elemek.
	private final By shoppingCartBtn = By.className("shopping_cart_link");
	private final By shoppingCartBadge = By.cssSelector("span[data-test='shopping-cart-badge']");

	// Lábléc.
	private final By twitterLink = By.linkText("Twitter");
	private final By fbLink = By.cssSelector("[data-test='social-facebook']");
	private final By linkedinLink = By.cssSelector("[data-test='social-linkedin']");

	private final By copyRightlbl = By.className("footer_copy");

	public InventoryPage(WebDriver driver) {
		super(driver);
		waitUntilTextToBe(title, "Products");
	}

	public int getShoppingCartBadgeNumber() {
		// Ha üres a kosár, akkor sem lesz error.
		if (driver.findElements(shoppingCartBadge).isEmpty()) {
			return 0;
		}

		return Integer.parseInt(
				getText(shoppingCartBadge));
	}

	public double getPriceofAnItem(String itemName) {
		return Double.parseDouble(getText(By.xpath(
				"//div[@class='inventory_item' and .//div[text()='" + itemName
						+ "']]//div[@class='inventory_item_price']"))
				.substring(1));
	}

	public InventoryPage addToCartOrRemove(String itemName) {
		By btn = By.xpath(
				"//div[@class='inventory_item' and .//div[normalize-space()='"
						+ itemName + "']]//button");
		click(btn);
		return this;
	}

	public String getAddToCartOrRemoveBtnText(String itemName) {
		return getText(By.xpath(
				"//div[@class='inventory_item' and .//div[normalize-space()='"
						+ itemName + "']]//button"));
	}

	public void changeOrderingAtoZ() {
		waitUntilClickable(changeOrderingSelect);
		Select changeOrderingSel = new Select(find(changeOrderingSelect));
		changeOrderingSel.selectByValue("az");
	}

	public void changeOrderingZtoA() {
		waitUntilClickable(changeOrderingSelect);
		Select changeOrderingSel = new Select(find(changeOrderingSelect));
		changeOrderingSel.selectByValue("za");
	}

	public void changeOrderingLowtoHigh() {
		waitUntilClickable(changeOrderingSelect);
		Select changeOrderingSel = new Select(find(changeOrderingSelect));
		changeOrderingSel.selectByValue("lohi");
	}

	public void changeOrderingHightoLow() {
		waitUntilClickable(changeOrderingSelect);
		Select changeOrderingSel = new Select(find(changeOrderingSelect));
		changeOrderingSel.selectByValue("hilo");
	}

	public LinkedHashMap<String, Double> getAllItemnamesAndTheirPrices() {

		// Megvárjuk, hogy megjelenjenek az árak.
		waitForAllElementsPresent(By.className("inventory_item_price"));

		// Lekérjük az összes termék nevét.
		List<WebElement> itemNames = findAll(By.xpath("//div[@class='inventory_item_name ']"));

		// Beszúrási sorrend meg marad a LinkedHashMap-el.
		LinkedHashMap<String, Double> itemsAndPrices = new LinkedHashMap<>();

		for (WebElement itemName : itemNames) {
			itemsAndPrices.put(itemName.getText(), getPriceofAnItem(itemName.getText()));
		}

		return itemsAndPrices;
	}

	public List<String> getAllItemName() {
		// Megvárjuk, hogy megjelenjenek az árak.
		// wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("inventory_item_name
		// ")));
		List<WebElement> allItemName = findAll(By.cssSelector("[class='inventory_item_name']"));

		List<String> onlyAllItemName = new ArrayList<>();
		for (WebElement itemName : allItemName) {
			onlyAllItemName.add(itemName.getText());
		}
		return onlyAllItemName;
	}

	public CartPage shoppingCart() {
		click(shoppingCartBtn);
		return new CartPage(driver);
	}

	public InventoryPage clickOnTwitterlink() {
		click(twitterLink);
		return this;
	}

	public String getCurrentTabHandle() {
		return getCurrentTab();
	}

	public InventoryPage switchToTab() {
		switchToNewTab();
		return this;
	}

	/** Bezárja az aktuális tabot és visszavált a megadott értékre. */
	public InventoryPage closeTabAndReturnTo(String original) {
		closeCurrentTabAndSwitchBack(original);
		return this;
	}
}
