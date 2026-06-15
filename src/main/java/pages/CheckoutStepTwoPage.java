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
                getText(summarySubtotalLbl).replace("$", "")
                        .replace("Item total: ", ""));
    }

    public double getSummaryTotal() {
        return Double.parseDouble(
                getText(summaryTotalLbl).replace("$", "")
                        .replace("Item total: ", ""));
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

}
