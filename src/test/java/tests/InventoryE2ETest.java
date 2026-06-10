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
import utils.TestDataUtil;

public class InventoryE2ETest extends BaseTest {

        @Test
        @DisplayName("Ellenőrizzük, hogy a kosárban is ugyanannyi a két termék ára, mint a főoldalon és minden oldalon. Felhnév: standard_user")
        void checkPricesOnThreePages() {
                InventoryPage inventoryPage = login();

                String firstItemName = "Sauce Labs Backpack";
                String secondItemName = "Sauce Labs Bike Light";

                double firstItemPriceOnIntentoryPage = inventoryPage.getPriceofAnItem(firstItemName);
                double secondItemPriceOnIntentoryPage = inventoryPage.getPriceofAnItem(secondItemName);

                inventoryPage.addToCartOrRemove(firstItemName);
                inventoryPage.addToCartOrRemove(secondItemName);

                assertEquals(2, inventoryPage.getShoppingCartBadgeNumber(),
                                "A kosárban lévő termékek darabszáma nem egyezik az elvárttal.");

                CartPage cartPage = inventoryPage.shoppingCart();

                double firstItemPriceOnCartPage = cartPage.getPriceOfAnItem(firstItemName);
                double secondItemPriceOnCartPage = cartPage.getPriceOfAnItem(secondItemName);

                assertEquals(firstItemPriceOnIntentoryPage, firstItemPriceOnCartPage,
                                "A " + firstItemName + " ára nem egyezik meg az Inventory és a Cart oldalon.");

                assertEquals(secondItemPriceOnIntentoryPage, secondItemPriceOnCartPage,
                                "A " + secondItemName + " ára nem egyezik meg az Inventory és a Cart oldalon.");

                CheckoutStepOnePage checkoutStepOnePage = cartPage.checkout();
                TestDataUtil testDataUtil = new TestDataUtil();

                checkoutStepOnePage.fillFirstNameInput(testDataUtil.firstName());
                checkoutStepOnePage.fillLastNameInput(testDataUtil.lastName());
                checkoutStepOnePage.fillPostalCodeInput(testDataUtil.zip());

                CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.clickOnContinue();

                double firstItemPriceCheckoutStepTwoPage = checkoutStepTwoPage.getPriceOfAnItem(firstItemName);
                double secondItemPriceCheckoutStepTwoPage = checkoutStepTwoPage.getPriceOfAnItem(secondItemName);

                double summarySubtotal = checkoutStepTwoPage.getSummarySubtotal();

                assertEquals(firstItemPriceOnIntentoryPage,
                                firstItemPriceCheckoutStepTwoPage,
                                "A " + firstItemName
                                                + " ára nem egyezik meg az Inventory és a CheckoutStepTwo oldalon.");

                assertEquals(secondItemPriceOnIntentoryPage,
                                secondItemPriceCheckoutStepTwoPage,
                                "A " + secondItemName
                                                + " ára nem egyezik meg az Inventory és a CheckoutStepTwo oldalon.");

                assertEquals((firstItemPriceOnIntentoryPage + secondItemPriceOnIntentoryPage),
                                summarySubtotal,
                                "A CheckoutStepTwo oldalon lévő termékek adó nélküli végösszege nem egyezik az oldalon lévő termékek árával.");

                // TODO: getSummaryTotal összeg ellenőrzése.
                CheckoutCompletePage checkoutCompletePage = checkoutStepTwoPage.finish();

                // TODO kell egy assert ami a thank you szöveget csekkolja.

                // Kijelentkezés.
                cleanUp(inventoryPage);

        }

}
