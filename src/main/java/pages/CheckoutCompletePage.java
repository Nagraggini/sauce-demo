package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class CheckoutCompletePage extends BasePage {

    private By title = By.className("title");
    private By thanksMessage = By.className("complete-header");
    private By backHomeBtn = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        waitUntilTextToBe(title, "Checkout: Complete!");
    }

    public String getThanksMessage() {
        return getText(thanksMessage);
    }

    public InventoryPage backHome() {
        click(backHomeBtn);
        return new InventoryPage(driver);
    }

}
