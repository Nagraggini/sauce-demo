package saucedemoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import saucedemoPages.CartPage;
import saucedemoPages.CheckoutStepOnePage;
import saucedemoPages.InventoryPage;
import saucedemoPages.LoginPage;

public class CheckoutStepOnePageTest extends BaseTest {

    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutStepOnePage checkoutStepOnePage;

    @Test
    @Tag("homework")
    @DisplayName("A checkout részen minden mezőt üresen hagyva.")
    void checkoutWithAllFieldsEmpty() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);

        // A bejelentkezéstől a Checkout gombig.
        fromLoginUntilCheckout(loginPage, inventoryPage, cartPage);

        // Üresen hagyjuk a három input mezőt.
        // Rákattintunk a Continue gombra.
        checkoutStepOnePage.clickOnContinueBtn();

        String errorMessage = checkoutStepOnePage.getErrorMessage();

        assertNotNull(errorMessage, "Üresen hagyott beviteli mezők esetén nem kapunk hibaüzenetet.");

        assertFalse(errorMessage.isBlank(),
                "Üresen hagyott beviteli mezők esetén nem kapunk hibaüzenetet, a whitespace-k leszedve.");

        // Adatok törlése és kijelentkezés.
        cleanUp(inventoryPage);
    }

    @Test
    @Tag("homework")
    @DisplayName("Ellenőrizzük a hibaüzenet szövegét keresztnév beviteli mező kihagyása esetén.")
    void shouldDisplayErrorMessageForFirstName() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);

        // A bejelentkezéstől a Checkout gombig.
        fromLoginUntilCheckout(loginPage, inventoryPage, cartPage);

        // Csak a keresztnév beviteli mező marad üresen.
        checkoutStepOnePage.fillLastNameInput("Doe");
        checkoutStepOnePage.fillPostalCodeInput("9999");

        // Rákattintunk a Continue gombra.
        checkoutStepOnePage.clickOnContinueBtn();

        String expectedErrorMessage = "Error: First Name is required";
        String actualErrorMessage = checkoutStepOnePage.getErrorMessage();

        assertEquals(expectedErrorMessage,
                actualErrorMessage, "Az üresen hagyott keresztnév beviteli mező esetén, nem megfelelő a hibaüzenet.");

        // Adatok törlése és kijelentkezés.
        cleanUp(inventoryPage);
    }

    @Test
    @Tag("homework")
    @DisplayName("Ellenőrizzük a hibaüzenet szövegét vezetéknév beviteli mező kihagyása esetén.")
    void shouldDisplayErrorMessageForLastName() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);

        // A bejelentkezéstől a Checkout gombig.
        fromLoginUntilCheckout(loginPage, inventoryPage, cartPage);

        // Csak a vezetélnév beviteli mező marad üresen.
        checkoutStepOnePage.fillFirstNameInput("Jane");
        checkoutStepOnePage.fillPostalCodeInput("9999");

        // Rákattintunk a Continue gombra.
        checkoutStepOnePage.clickOnContinueBtn();

        String expectedErrorMessage = "Error: Last Name is required";
        String actualErrorMessage = checkoutStepOnePage.getErrorMessage();

        assertEquals(expectedErrorMessage,
                actualErrorMessage, "Az üresen hagyott vezetéknév beviteli mező esetén, nem megfelelő a hibaüzenet.");

        // Adatok törlése és kijelentkezés.
        cleanUp(inventoryPage);
    }

    @Test
    @Tag("homework")
    @DisplayName("Ellenőrizzük a hibaüzenet szövegét irányítószám beviteli mező kihagyása esetén.")
    void shouldDisplayErrorMessageForPostalCode() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);

        // A bejelentkezéstől a Checkout gombig.
        fromLoginUntilCheckout(loginPage, inventoryPage, cartPage);

        // Csak az irányítószám beviteli mező marad üresen.
        checkoutStepOnePage.fillFirstNameInput("Jane");
        checkoutStepOnePage.fillLastNameInput("Doe");

        // Rákattintunk a Continue gombra.
        checkoutStepOnePage.clickOnContinueBtn();

        String expectedErrorMessage = "Error: Postal Code is required";
        String actualErrorMessage = checkoutStepOnePage.getErrorMessage();

        assertEquals(expectedErrorMessage,
                actualErrorMessage, "Az üresen hagyott irányítószám beviteli mező esetén, nem megfelelő a hibaüzenet.");

        // Adatok törlése és kijelentkezés.
        cleanUp(inventoryPage);
    }

    public void fromLoginUntilCheckout(LoginPage loginPage,
            InventoryPage inventoryPage,
            CartPage cartPage) {
        // Bejelentkezés.
        login(loginPage);

        // Egy elemez rakunk a kosárba.
        inventoryPage.addToCartOrRemove("Sauce Labs Bike Light");

        // Rákattintunk a koásr ikonra.
        inventoryPage.clickOnshoppingCartBtn();

        // A Checkout gombra kattintunk.
        cartPage.clickOnCheckout();
    }
}
