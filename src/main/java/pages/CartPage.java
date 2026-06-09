package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private final By checkoutBtn = By.cssSelector("#checkout");

    public void clickOnCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
    }

    public double getPriceOfAnItem(String itemName) {
        /* .[] -> Pont az aktuálisre hivatkozik a kapcsoszárójelben van a feltétel. */
        double itemPrice = Double
                .parseDouble(wait
                        .until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath(
                                        "//div[@class='cart_item'][.//div[@class='inventory_item_name' and normalize-space()='"
                                                + itemName + "']]//div[@class='inventory_item_price']")))
                        .getText()
                        .replace("$", ""));

        return itemPrice;
    }
}
