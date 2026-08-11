package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class CheckoutStepOnePage extends BasePage {

    // Fejléc.

    // Fejléc és logó.
    private final By appLogo = By.xpath("//div[@class='app_logo' and contains(text(),'Swag')]");
    private final By checkoutLbl = By.cssSelector("[data-test='title']");
    private final By title = By.className("title");

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

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
        waitUntilTextToBe(title, "Checkout: Your Information");
    }

    public CheckoutStepOnePage fillFirstNameInput(String firstName) {
        type(firstNameInput, firstName);
        return this;
    }

    public CheckoutStepOnePage fillLastNameInput(String lastName) {
        type(lastNameInput, lastName);
        return this;
    }

    public CheckoutStepOnePage fillPostalCodeInput(String postalCode) {
        type(postalCodeInput, postalCode);
        return this;
    }

    public CheckoutStepOnePage fillInAll(String first, String last, String zip) {
        type(firstNameInput, first);
        type(lastNameInput, last);
        type(postalCodeInput, zip);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public CartPage cancel() {
        click(cancelBtn);
        return new CartPage(driver);
    }

    public CheckoutStepTwoPage clickOnContinue() {
        click(continueBtn);
        return new CheckoutStepTwoPage(driver);
    }

}
