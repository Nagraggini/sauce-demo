package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class CheckoutCompletePage extends BasePage {

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    private By completeheaderLbl = By.className("complete-header");
    private By backHomeBtn = By.id("back-to-products");

    public InventoryPage backHome() {
        click(backHomeBtn);
        return new InventoryPage(driver);
    }

}
