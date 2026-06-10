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

                inventoryPage.clickOnShoppingCartBtn();

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

                checkoutStepOnePage.clickOnContinue();

                CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.clickOnContinue();

                // TODO Árak csekkolása elemenként az utolsó oldalon.
                double itemTotal = checkoutStepTwoPage.getItemTotal();

                assertEquals((firstItemPriceOnCartPage + secondItemPriceOnCartPage), itemTotal,
                                "A kosárban lévő termékek összege nem egyezik a CheckOutStepTwo oldalon lévő termékeknettó végösszegével.");

                CheckoutCompletePage checkoutCompletePage = checkoutStepTwoPage.finish();

                // TODO kell egy assert ami a thank you szöveget csekkolja.

                // Kijelentkezés.
                cleanUp(inventoryPage);

        }

}
