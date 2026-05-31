package saucedemoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import saucedemoPages.*;

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
        loginPage.clickonLoginBtn();

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

    void cleanUp(InventoryPage inventoryPage) {
        logger.info("\n Before clean up current URL: {}", driver.getCurrentUrl());

        // Takarítás.
        inventoryPage.openHamburgerMenu();
        inventoryPage.clickonResetAppStateBtn();

        // Kijelentkezés
        inventoryPage.clickonLogoutBtn();

    }
}
