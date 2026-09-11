package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import pages.CheckoutCompletePage;
import pages.CheckoutStepOnePage;
import pages.CheckoutStepTwoPage;
import pages.CartPage;
import pages.InventoryPage;

public class CheckoutWorkflowTest extends BaseTest {

    @Test
    @DisplayName("Sikeres checkout folyamat tesztelése érvényes adatokkal")
    public void testSuccessfulCheckoutProcess() {
        InventoryPage inventoryPage = login();

        // Termék hozzáadása a kosárhoz
        inventoryPage.addToCartOrRemove("Sauce Labs Backpack");
        Assertions.assertEquals(1, inventoryPage.getShoppingCartBadgeNumber(), "A kosárban 1 elemnek kell lennie.");

        // Navigálás a kosárhoz
        CartPage cartPage = inventoryPage.shoppingCart();
        Assertions.assertEquals(29.99, cartPage.getPriceOfAnItem("Sauce Labs Backpack"), 0.01,
                "A Sauce Labs Backpack nincs a kosárban.");

        // Checkout indítása
        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickOnCheckout();
        CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.fillInAll("Andi", "Tester", "1000")
                .clickOnContinue();

        // Összegzés és befejezés
        Assertions.assertTrue(checkoutStepTwoPage.getCurrentUrl().endsWith("/checkout-step-two.html"),
                "Az összegző oldal nem jelent meg.");
        CheckoutCompletePage checkoutCompletePage = checkoutStepTwoPage.clickOnFinish();

        // Visszaigazolás ellenőrzése
        Assertions.assertEquals("Thank you for your order!", checkoutCompletePage.getThanksMessage(),
                "A megrendelés nem sikerült.");
    }
}
