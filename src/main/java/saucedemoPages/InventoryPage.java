package saucedemoPages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

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
		WebElement menu = driver.findElement(hamburgerBtn);
		this.driver.findElement(hamburgerBtn).click();
	}

	public void clickonLogoutBtn() {
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(logoutBtn)).click();
	}

	public void clickonResetAppStateBtn() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(resetAppStateBtn))
				.click();
	}

	public void clickOnshoppingCartBtn() {
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
		By label = By.xpath("//div[@data-test='inventory-list']//div[text()='" + itemName + "']");

		this.wait.until(ExpectedConditions.visibilityOfElementLocated(label));

		String priceWithDollar = this.driver.findElement(By.xpath(
				"//div[@class='inventory_item' and .//div[text()='" + itemName
						+ "']]//div[@class='inventory_item_price']"))
				.getText();

		double price = Double.parseDouble(priceWithDollar.substring(1));

		return price;
	}

	public void addToCartOrRemove(String itemName) {

		By addToCartOrRemoveBtn = By.xpath(
				"//div[@class='inventory_item' and .//div[normalize-space()='"
						+ itemName + "']]//button");

		if (this.driver.findElement(addToCartOrRemoveBtn).getText().equals("Add to Cart")) {
			wait.until(ExpectedConditions.elementToBeClickable(addToCartOrRemoveBtn)).click();
		} else {
			wait.until(ExpectedConditions.elementToBeClickable(addToCartOrRemoveBtn)).click();
		}
	}

	public String getAddToCartOrRemoveBtnText(String itemName) {
		By addToCartOrRemoveBtn = By.xpath(
				"//div[@class='inventory_item' and .//div[normalize-space()='"
						+ itemName + "']]//button");

		return this.driver.findElement(addToCartOrRemoveBtn).getText();
	}

	public void changeOrderingAtoZ() {
		wait.until(ExpectedConditions.elementToBeClickable(changeOrderingSelect));
		Select changeOrderingSel = new Select(this.driver.findElement(changeOrderingSelect));
		changeOrderingSel.selectByValue("az");
	}

	public void changeOrderingZtoA() {
		wait.until(ExpectedConditions.elementToBeClickable(changeOrderingSelect));
		Select changeOrderingSel = new Select(this.driver.findElement(changeOrderingSelect));
		changeOrderingSel.selectByValue("za");
	}

	public void changeOrderingLowtoHigh() {
		wait.until(ExpectedConditions.elementToBeClickable(changeOrderingSelect));
		Select changeOrderingSel = new Select(this.driver.findElement(changeOrderingSelect));
		changeOrderingSel.selectByValue("lohi");
	}

	public void changeOrderingHightoLow() {
		wait.until(ExpectedConditions.elementToBeClickable(changeOrderingSelect));
		Select changeOrderingSel = new Select(this.driver.findElement(changeOrderingSelect));
		changeOrderingSel.selectByValue("hilo");
	}

	public LinkedHashMap<String, Double> getAllItemnamesAndTheirPrices() {

		// Megvárjuk, hogy megjelenjenek az árak.
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("inventory_item_price")));

		// Lekérjük az összes termék nevét.
		List<WebElement> itemNames = this.driver.findElements(By.xpath("//div[@class='inventory_item_name ']"));

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
		List<WebElement> allItemName = this.driver.findElements(By.cssSelector("[class='inventory_item_name']"));

		List<String> onlyAllItemName = new ArrayList<>();
		for (WebElement itemName : allItemName) {
			onlyAllItemName.add(itemName.getText());

		}
		return onlyAllItemName;
	}

}
