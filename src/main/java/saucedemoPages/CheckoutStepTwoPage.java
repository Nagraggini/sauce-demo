package saucedemoPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutStepTwoPage extends BasePage {

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    private final By itemTotal = By.className("summary_subtotal_label");

    public double getItemTotal() {
        return Double.parseDouble(
                wait.until(ExpectedConditions.visibilityOfElementLocated(itemTotal)).getText().replace("$", "")
                        .replace("Item total: ", ""));
    }
}
