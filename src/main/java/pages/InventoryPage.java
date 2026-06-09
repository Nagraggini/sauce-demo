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
	private final By changeOrderingSelect = By.cssSelector("[class='product_sort_container']");

	// Kosár elemek.
	private final By shoppingCartBtn = By.className("shopping_cart_link");
	private final By shoppingCartBadge = By.cssSelector("span[data-test='shopping-cart-badge']");

	// Lábléc.
	private final By twitterIcon = By.linkText("Twitter");
	private final By fbIcon = By.cssSelector("[data-test='social-facebook']");
	private final By linkedinIcon = By.cssSelector("[data-test='social-linkedin']");

	private final By copyRightlbl = By.className("footer_copy");

	public void openHamburgerMenu() {
		click(hamburgerBtn);
	}

	public void clickOnLogoutBtn() {
		click(logoutBtn);
	}

	public void clickOnResetAppStateBtn() {
		click(resetAppStateBtn);
	}

	public void clickOnshoppingCartBtn() {
		click(shoppingCartBtn);
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

	public void addToCartOrRemove(String itemName) {
		By addToCartOrRemoveBtn = By.xpath(
				"//div[@class='inventory_item' and .//div[normalize-space()='"
						+ itemName + "']]//button");

		if (getText(addToCartOrRemoveBtn).equals("Add to Cart")) {
			click(addToCartOrRemoveBtn);
		} else {
			click(addToCartOrRemoveBtn);
		}
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

}
