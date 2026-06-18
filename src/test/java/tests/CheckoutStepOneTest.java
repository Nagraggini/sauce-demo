package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import base.BaseTest;

public class CheckoutStepOneTest extends BaseTest {

    @Test
    @Tag("homework")
    @DisplayName("A checkout részen minden mezőt üresen hagyva.")
    void checkoutWithAllFieldsEmpty() {
        /*
         * CheckoutStepOnePage checkoutStepOnePage =
         * login().addToCartOrRemove("Sauce Labs Backpack").shoppingCart()
         * .clickOnCheckout();
         * 
         * String errorMessage = checkoutStepOnePage.getErrorMessage();
         * 
         * assertNotNull(errorMessage,
         * "Üresen hagyott beviteli mezők esetén nem kapunk hibaüzenetet.");
         * 
         * assertFalse(errorMessage.isBlank(),
         * "Üresen hagyott beviteli mezők esetén nem kapunk hibaüzenetet, a whitespace-k leszedve."
         * );
         */
        // Adatok törlése és kijelentkezés.
        // checkoutStepOnePage.open
    }

    @Test
    @Tag("homework")
    @DisplayName("Ellenőrizzük a hibaüzenet szövegét keresztnév beviteli mező kihagyása esetén.")
    void shouldDisplayErrorMessageForFirstName() {
        /*
         * InventoryPage inventoryPage = login();
         * CartPage cartPage = new CartPage(driver);
         * CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage(driver);
         * 
         * // Csak a keresztnév beviteli mező marad üresen.
         * TestDataUtil testDataUtil = new TestDataUtil();
         * 
         * checkoutStepOnePage.fillLastNameInput(testDataUtil.lastName());
         * checkoutStepOnePage.fillPostalCodeInput(testDataUtil.zip());
         * 
         * // Rákattintunk a Continue gombra.
         * checkoutStepOnePage.clickOnContinue();
         * 
         * String expectedErrorMessage = "Error: First Name is required";
         * String actualErrorMessage = checkoutStepOnePage.getErrorMessage();
         * 
         * assertEquals(expectedErrorMessage,
         * actualErrorMessage,
         * "Az üresen hagyott keresztnév beviteli mező esetén, nem megfelelő a hibaüzenet."
         * );
         */
        // Adatok törlése és kijelentkezés.
        // cleanUp(inventoryPage);
    }

    @Test
    @Tag("homework")
    @DisplayName("Ellenőrizzük a hibaüzenet szövegét vezetéknév beviteli mező kihagyása esetén.")
    void shouldDisplayErrorMessageForLastName() {
        /*
         * InventoryPage inventoryPage = login();
         * CartPage cartPage = new CartPage(driver);
         * CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage(driver);
         * 
         * // Csak a vezetélnév beviteli mező marad üresen.
         * TestDataUtil testDataUtil = new TestDataUtil();
         * checkoutStepOnePage.fillFirstNameInput(testDataUtil.firstName());
         * checkoutStepOnePage.fillPostalCodeInput(testDataUtil.zip());
         * 
         * // Rákattintunk a Continue gombra.
         * checkoutStepOnePage.clickOnContinue();
         * 
         * String expectedErrorMessage = "Error: Last Name is required";
         * String actualErrorMessage = checkoutStepOnePage.getErrorMessage();
         * 
         * assertEquals(expectedErrorMessage,
         * actualErrorMessage,
         * "Az üresen hagyott vezetéknév beviteli mező esetén, nem megfelelő a hibaüzenet."
         * );
         */
        // Adatok törlése és kijelentkezés.
        // cleanUp(inventoryPage);
    }

    @Test
    @Tag("homework")
    @DisplayName("Ellenőrizzük a hibaüzenet szövegét irányítószám beviteli mező kihagyása esetén.")
    void shouldDisplayErrorMessageForPostalCode() {
        /*
         * InventoryPage inventoryPage = login();
         * CartPage cartPage = new CartPage(driver);
         * CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage(driver);
         * 
         * // Csak az irányítószám beviteli mező marad üresen.
         * TestDataUtil testDataUtil = new TestDataUtil();
         * 
         * checkoutStepOnePage.fillFirstNameInput(testDataUtil.firstName());
         * checkoutStepOnePage.fillLastNameInput(testDataUtil.lastName());
         * 
         * // Rákattintunk a Continue gombra.
         * checkoutStepOnePage.clickOnContinue();
         * 
         * String expectedErrorMessage = "Error: Postal Code is required";
         * String actualErrorMessage = checkoutStepOnePage.getErrorMessage();
         * 
         * assertEquals(expectedErrorMessage,
         * actualErrorMessage,
         * "Az üresen hagyott irányítószám beviteli mező esetén, nem megfelelő a hibaüzenet."
         * );
         */
        // Adatok törlése és kijelentkezés.
        // cleanUp(inventoryPage);
    }

}
