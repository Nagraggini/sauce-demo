package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class CheckoutStepTwoPage extends BasePage {

    private final By title = By.className("title");

    private final By summarySubtotalLbl = By.className("summary_subtotal_label");
    private final By summaryTotalLbl = By.className("summary_total_label");

    private final By cancelBtn = By.id("cancel");
    private final By finishBtn = By.id("finish");

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
        waitUntilTextToBe(title, "Checkout: Overview");
    }

    public double getSummarySubtotal() {
        return Double.parseDouble(
                getText(summarySubtotalLbl)
                        .replace("Item total: ", "")
                        .replace("$", "")
                        .trim());
    }

    public double getSummaryTotal() {
        return Double.parseDouble(
                getText(summaryTotalLbl)
                        .replace("Total: ", "")
                        .replace("$", "")
                        .trim());
    }

    public InventoryPage clickOnCancel() {
        click(cancelBtn);
        return new InventoryPage(driver);
    }

    // A getPriceOfAnItem(String itemName) metódus itt is működik.

    public CheckoutCompletePage clickOnFinish() {
        click(finishBtn);
        return new CheckoutCompletePage(driver);
    }

    public double getPriceOfAnItem(String itemName) {
        return Double.parseDouble(
                getText(By.xpath(
                        "//div[@class='cart_item'][.//div[@class='inventory_item_name' and normalize-space()='"
                                + itemName + "']]//div[@class='inventory_item_price']"))
                        .replace("$", ""));
    }
}
