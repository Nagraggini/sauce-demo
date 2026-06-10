package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private final By checkoutBtn = By.cssSelector("#checkout");
    private final By continueShoppingBtn = By.id("continue-shopping");

    public void clickOnCheckout() {
        click(checkoutBtn);
    }

    public double getPriceOfAnItem(String itemName) {
        /* .[] -> Pont az aktuálisre hivatkozik a kapcsoszárójelben van a feltétel. */
        double itemPrice = Double
                .parseDouble(getText(
                        By.xpath(
                                "//div[@class='cart_item'][.//div[@class='inventory_item_name' and normalize-space()='"
                                        + itemName + "']]//div[@class='inventory_item_price']"))
                        .replace("$", ""));

        return itemPrice;
    }

    public InventoryPage continueShopping() {
        click(continueShoppingBtn);
        return new InventoryPage(driver);
    }

    public CheckoutStepOnePage checkout() {
        click(checkoutBtn);
        return new CheckoutStepOnePage(driver);
    }
}
