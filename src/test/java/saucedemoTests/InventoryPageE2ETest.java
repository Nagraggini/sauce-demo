package saucedemoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import saucedemoPages.CartPage;
import saucedemoPages.CheckoutStepOnePage;
import saucedemoPages.CheckoutStepTwoPage;
import saucedemoPages.InventoryPage;
import saucedemoPages.LoginPage;

public class InventoryPageE2ETest extends BaseTest {
    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutStepOnePage checkoutStepOnePage;
    CheckoutStepTwoPage checkoutStepTwoPage;

    @Test
    @DisplayName("Ellenőrizzük, hogy a kosárban is ugyanannyi a két termék ára, mint a főoldalon, meg a legvégén. Felhnév: standard_user")
    void checkPricesOnThreePages() {
        loginPage = new LoginPage(driver);

        loginPage.openPage("https://www.saucedemo.com");
        String username = ConfigReader.get("USERNAME");
        String password = ConfigReader.get("PASSWORD");
        loginPage.fillInputs(username, password);
        loginPage.clickOnLoginBtn();

        inventoryPage = new InventoryPage(driver);

        String firstItemName = "Sauce Labs Backpack";
        String secondItemName = "Sauce Labs Bike Light";

        double firstItemPriceOnIntentoryPage = inventoryPage.getPriceofAnItem(firstItemName);
        double secondItemPriceOnIntentoryPage = inventoryPage.getPriceofAnItem(secondItemName);

        inventoryPage.addToCartOrRemove(firstItemName);
        inventoryPage.addToCartOrRemove(secondItemName);

        assertEquals(2, inventoryPage.getShoppingCartBadgeNumber(),
                "A kosárban lévő termékek darabszáma nem egyezik az elvárttal.");

        inventoryPage.clickOnshoppingCartBtn();

        cartPage = new CartPage(driver);
        double firstItemPriceOnCartPage = cartPage.getPriceofAnItem(firstItemName);
        double secondItemPriceOnCartPage = cartPage.getPriceofAnItem(secondItemName);

        assertEquals(firstItemPriceOnIntentoryPage, firstItemPriceOnCartPage,
                "A" + firstItemName + " ára nem egyezik meg az Inventory és a Cart oldalon.");

        assertEquals(secondItemPriceOnIntentoryPage, secondItemPriceOnCartPage,
                "A" + secondItemName + " ára nem egyezik meg az Inventory és a Cart oldalon.");

        cartPage.clickOnCheckout();

        checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutStepOnePage.fillFirstNameInput("Jane");
        checkoutStepOnePage.fillLastNameInput("Doe");
        checkoutStepOnePage.fillPostalCodeInput("9999");

        checkoutStepOnePage.clickOnContinueBtn();

        checkoutStepTwoPage = new CheckoutStepTwoPage(driver);

        // TODO Árak csekkolása elemenként az utolsó oldalon.
        double itemTotal = checkoutStepTwoPage.getItemTotal();

        assertEquals((firstItemPriceOnCartPage + secondItemPriceOnCartPage), itemTotal,
                "A kosárban lévő termékek összege nem egyezik a CheckOutStepTwo oldalon lévő termékeknettó végösszegével.");

        // Kijelentkezés.
        cleanUp(inventoryPage);

    }

}
