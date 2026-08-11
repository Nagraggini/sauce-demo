package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutStepOnePage;
import pages.CheckoutStepTwoPage;
import pages.InventoryPage;
import pages.MenuComponent;
import utils.TestDataUtil;

public class InventoryE2ETest extends BaseTest {

        @Test
        @DisplayName("Két termék ára minden rendelési oldalon megegyezik – standard_user")
        void checkPricesOnThreePages() {
                InventoryPage inventoryPage = login();
                String firstItemName = "Sauce Labs Backpack";
                String secondItemName = "Sauce Labs Bike Light";

                double firstPriceOnInventory = inventoryPage.getPriceofAnItem(firstItemName);
                double secondPriceOnInventory = inventoryPage.getPriceofAnItem(secondItemName);

                inventoryPage.addToCartOrRemove(firstItemName)
                                .addToCartOrRemove(secondItemName);

                assertEquals(2, inventoryPage.getShoppingCartBadgeNumber(),
                                "A kosárban lévő termékek darabszáma nem egyezik az elvárttal.");

                CartPage cartPage = inventoryPage.shoppingCart();
                assertEquals(firstPriceOnInventory, cartPage.getPriceOfAnItem(firstItemName),
                                "A(z) " + firstItemName + " ára eltér az Inventory és a Cart oldalon.");
                assertEquals(secondPriceOnInventory, cartPage.getPriceOfAnItem(secondItemName),
                                "A(z) " + secondItemName + " ára eltér az Inventory és a Cart oldalon.");

                TestDataUtil testData = new TestDataUtil();
                CheckoutStepTwoPage checkoutStepTwoPage = cartPage.clickOnCheckout()
                                .fillInAll(testData.firstName(), testData.lastName(), testData.zip())
                                .clickOnContinue();

                assertEquals(firstPriceOnInventory, checkoutStepTwoPage.getPriceOfAnItem(firstItemName),
                                "A(z) " + firstItemName + " ára eltér az Inventory és a Checkout oldalon.");
                assertEquals(secondPriceOnInventory, checkoutStepTwoPage.getPriceOfAnItem(secondItemName),
                                "A(z) " + secondItemName + " ára eltér az Inventory és a Checkout oldalon.");

                double expectedSubtotal = firstPriceOnInventory + secondPriceOnInventory;
                assertEquals(expectedSubtotal, checkoutStepTwoPage.getSummarySubtotal(), 0.001,
                                "Az adó nélküli részösszeg nem egyezik a termékek árának összegével.");

                double expectedTotal = 43.18;
                assertEquals(expectedTotal, checkoutStepTwoPage.getSummaryTotal(), 0.001,
                                "Az adóval növelt végösszeg nem egyezik az elvárt összeggel.");

                CheckoutCompletePage checkoutCompletePage = checkoutStepTwoPage.clickOnFinish();
                assertEquals("Thank you for your order!", checkoutCompletePage.getThanksMessage(),
                                "A sikeres rendelést visszaigazoló szöveg nem megfelelő.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @Test
        @DisplayName("Üres kosárral is befejezhető a rendelési folyamat – standard_user")
        void checkoutWithEmptyCart() {
                InventoryPage inventoryPage = login();

                assertEquals(0, inventoryPage.getShoppingCartBadgeNumber(),
                                "A kosár a teszt kezdetekor nem üres.");

                CheckoutStepOnePage checkoutStepOnePage = inventoryPage.shoppingCart().clickOnCheckout();
                TestDataUtil testData = new TestDataUtil();

                CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage
                                .fillInAll(testData.firstName(), testData.lastName(), testData.zip())
                                .clickOnContinue();

                assertEquals(0.0, checkoutStepTwoPage.getSummarySubtotal(), 0.001,
                                "Üres kosár esetén az adó nélküli részösszeg nem nulla.");
                assertEquals(0.0, checkoutStepTwoPage.getSummaryTotal(), 0.001,
                                "Üres kosár esetén a végösszeg nem nulla.");

                CheckoutCompletePage checkoutCompletePage = checkoutStepTwoPage.clickOnFinish();
                assertEquals("Thank you for your order!", checkoutCompletePage.getThanksMessage(),
                                "Üres kosár esetén nem jelent meg a sikeres rendelést visszaigazoló szöveg.");

                new MenuComponent(driver).resetAppState().logout();
        }

}