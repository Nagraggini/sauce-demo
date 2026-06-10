package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class CheckoutStepTwoPage extends BasePage {

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    private final By summarySubtotalLbl = By.className("summary_subtotal_label");
    private final By summaryTotalLbl = By.className("summary_total_label");

    private final By cancelBtn = By.id("cancel");
    private final By finishBtn = By.id("finish");

    public double getItemTotal() {
        return Double.parseDouble(
                getText(summarySubtotalLbl).replace("$", "")
                        .replace("Item total: ", ""));
    }

    public InventoryPage cancel() {
        click(cancelBtn);
        return new InventoryPage(driver);
    }

    public CheckoutCompletePage finish() {
        click(finishBtn);
        return new CheckoutCompletePage(driver);
    }
}
