package saucedemoTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import saucedemoPages.InventoryPage;
import saucedemoPages.LoginPage;

class InventoryPageTest extends BaseTest {

    LoginPage loginPage;
    InventoryPage inventoryPage;

    @Test
    @Tag("homework")
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

        // FIXME
        inventoryPage.addToCart(firstItemName);
        inventoryPage.addToCart(secondItemName);

        logger.info("\n inventoryPage.getShoppingCartBadgeNumber(): " + inventoryPage.getShoppingCartBadgeNumber());
        // onlyForChecking();

        // assertEquals(2, inventoryPage.getShoppingCartBadgeNumber(),
        // "A kosárban lévő termékek darabszáma nem egyezik az elvárttal.");

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
