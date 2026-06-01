package saucedemoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import saucedemoPages.InventoryPage;
import saucedemoPages.LoginPage;

public class InventoryPageE2ETest extends BaseTest {
    LoginPage loginPage;
    InventoryPage inventoryPage;

    @Test
    @DisplayName("Ellenőrizzük, hogy a kosárban is ugyanannyi a két termék ára. Felhnév: standard_user")
    void checkCheckoutPrices() {
        logger.info("\ncheckCheckoutPrices(): ");
        loginPage = new LoginPage(driver);

        loginPage.openPage("https://www.saucedemo.com");
        String username = "standard_user";
        String password = "secret_sauce";
        loginPage.fillInputs(username, password);
        loginPage.clickOnLoginBtn();

        inventoryPage = new InventoryPage(driver);

        String firstItemName = "Sauce Labs Backpack";
        String secondItemName = "Sauce Labs Bike Light";

        double firstItemPrice = inventoryPage.getPriceofAnItem(firstItemName);
        double secondItemPrice = inventoryPage.getPriceofAnItem(secondItemName);

        logger.info("\nfirst item: {} second item: {}", firstItemPrice, secondItemPrice);

        inventoryPage.addToCartOrRemove(firstItemName);
        inventoryPage.addToCartOrRemove(secondItemName);

        assertEquals(2, inventoryPage.getShoppingCartBadgeNumber(),
                "A kosárban lévő termékek darabszáma nem egyezik az elvárttal.");

        // inventoryPage.clickOnshoppingCartBtn();

        // TODO
        // A kosárban lévő elemet összegét kell ellenőrizni.
        // Kijelentkezés.
        cleanUp(inventoryPage);

    }

}
