package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import pages.CheckoutStepOnePage;

public class CheckoutValidationTest extends BaseTest {

    @Test
    @DisplayName("Checkout hibaüzenet ellenőrzése hiányzó irányítószám esetén")
    public void testCheckoutWithMissingPostalCode() {
        CheckoutStepOnePage checkoutPage = login()
                .addToCartOrRemove("Sauce Labs Bike Light")
                .shoppingCart()
                .clickOnCheckout();

        // Irányítószám nélkül próbáljuk meg a folytatást
        checkoutPage.fillInAll("Andi", "Tester", "").submit();

        String errorMessage = checkoutPage.getErrorMessage();
        Assertions.assertEquals("Error: Postal Code is required", errorMessage,
                "Nem a megfelelő hibaüzenet jelent meg.");
    }
}
