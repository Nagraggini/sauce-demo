package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class CheckoutStepOnePage extends BasePage {

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }
    // Fejléc.

    // Fejléc és logó.
    private final By appLogo = By.xpath("//div[@class='app_logo' and contains(text(),'Swag')]");
    private final By checkoutLbl = By.cssSelector("[data-test='title']");

    // Kosár elemek.
    private final By shoppingCartBtn = By.className("shopping_cart_link");
    private final By shoppingCartBadge = By.cssSelector("span[data-test='shopping-cart-badge']");

    // Beviteli mezők.
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");

    // Error üzenet.
    private final By errorMessage = By.className("error-message-container");

    // Gombok.
    private final By cancelBtn = By.id("cancel");
    private final By continueBtn = By.id("continue");

    public void fillFirstNameInput(String firstName) {
        type(firstNameInput, firstName);
    }

    public void fillLastNameInput(String lastName) {
        type(lastNameInput, lastName);
    }

    public void fillPostalCodeInput(String postalCode) {
        type(postalCodeInput, postalCode);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public CartPage cancel(){
        click(cancelBtn);
        return new CartPage(driver);
    }
      
    public CheckoutStepTwoPage continue() {
        click(continueBtn);
        return new CheckoutStepTwoPage(driver);
    }

    

}
