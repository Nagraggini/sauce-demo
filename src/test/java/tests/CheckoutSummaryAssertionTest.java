package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import pages.CheckoutStepTwoPage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutSummaryAssertionTest extends BaseTest {

    @Test
    @DisplayName("Checkout összegző oldal adatainak ellenőrzése assertAll segítségével")
    public void testCheckoutSummaryDetails() {
        CheckoutStepTwoPage checkoutPage = login()
                .addToCartOrRemove("Sauce Labs Backpack")
                .shoppingCart()
                .clickOnCheckout()
                .fillInAll("Andi", "Tester", "1000")
                .clickOnContinue();

        // Ahelyett, hogy az első hibánál leállna, mindegyik ellenőrzés lefut
        assertAll("Checkout összegző oldal adatainak validálása",
                () -> assertTrue(checkoutPage.getCurrentUrl().endsWith("/checkout-step-two.html"),
                        "Az összegző oldal nem jelent meg!"),
                () -> assertEquals("SauceCard #31337", checkoutPage.getPaymentInfo(), "A fizetési mód nem egyezik!"),
                () -> assertEquals("Free Pony Express Delivery!", checkoutPage.getShippingInfo(),
                        "A szállítási mód nem egyezik!"),
                () -> assertEquals("Item total: $29.99", checkoutPage.getItemTotalText(),
                        "A részösszeg nem megfelelő!"),
                () -> assertEquals("Tax: $2.40", checkoutPage.getTaxText(), "Az adó összege nem megfelelő!"),
                () -> assertEquals("Total: $32.39", checkoutPage.getTotalPriceText(), "A végösszeg nem egyezik!"));
    }
}
