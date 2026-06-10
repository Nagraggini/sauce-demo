package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import pages.CartPage;
import pages.CheckoutStepOnePage;
import pages.InventoryPage;
import utils.TestDataUtil;

public class CheckoutStepOneTest extends BaseTest {

    @Test
    @Tag("homework")
    @DisplayName("A checkout részen minden mezőt üresen hagyva.")
    void checkoutWithAllFieldsEmpty() {
        InventoryPage inventoryPage = login();
        CartPage cartPage = new CartPage(driver);
        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage(driver);

        // A bejelentkezéstől a Checkout gombig.
        fromLoginUntilCheckout();

        // Üresen hagyjuk a három input mezőt.
        // Rákattintunk a Continue gombra.
        checkoutStepOnePage.clickOnContinue();

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
        InventoryPage inventoryPage = login();
        CartPage cartPage = new CartPage(driver);
        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage(driver);

        // A bejelentkezéstől a Checkout gombig.
        fromLoginUntilCheckout();

        // Csak a keresztnév beviteli mező marad üresen.
        TestDataUtil testDataUtil = new TestDataUtil();

        checkoutStepOnePage.fillLastNameInput(testDataUtil.lastName());
        checkoutStepOnePage.fillPostalCodeInput(testDataUtil.zip());

        // Rákattintunk a Continue gombra.
        checkoutStepOnePage.clickOnContinue();

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
        InventoryPage inventoryPage = login();
        CartPage cartPage = new CartPage(driver);
        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage(driver);

        // A bejelentkezéstől a Checkout gombig.
        fromLoginUntilCheckout();

        // Csak a vezetélnév beviteli mező marad üresen.
        TestDataUtil testDataUtil = new TestDataUtil();
        checkoutStepOnePage.fillFirstNameInput(testDataUtil.firstName());
        checkoutStepOnePage.fillPostalCodeInput(testDataUtil.zip());

        // Rákattintunk a Continue gombra.
        checkoutStepOnePage.clickOnContinue();

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
        InventoryPage inventoryPage = login();
        CartPage cartPage = new CartPage(driver);
        CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage(driver);

        // A bejelentkezéstől a Checkout gombig.
        fromLoginUntilCheckout();

        // Csak az irányítószám beviteli mező marad üresen.
        TestDataUtil testDataUtil = new TestDataUtil();

        checkoutStepOnePage.fillFirstNameInput(testDataUtil.firstName());
        checkoutStepOnePage.fillLastNameInput(testDataUtil.lastName());

        // Rákattintunk a Continue gombra.
        checkoutStepOnePage.clickOnContinue();

        String expectedErrorMessage = "Error: Postal Code is required";
        String actualErrorMessage = checkoutStepOnePage.getErrorMessage();

        assertEquals(expectedErrorMessage,
                actualErrorMessage, "Az üresen hagyott irányítószám beviteli mező esetén, nem megfelelő a hibaüzenet.");

        // Adatok törlése és kijelentkezés.
        cleanUp(inventoryPage);
    }

    public void fromLoginUntilCheckout() {
        InventoryPage inventoryPage = login();

        // Egy elemez rakunk a kosárba.
        inventoryPage.addToCartOrRemove("Sauce Labs Bike Light");

        // Rákattintunk a koásr ikonra.
        inventoryPage.clickOnShoppingCartBtn();
        CartPage cartPage = new CartPage(driver);

        // A Checkout gombra kattintunk.
        cartPage.clickOnCheckout();
    }
}
