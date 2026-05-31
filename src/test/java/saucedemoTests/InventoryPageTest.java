package saucedemoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import saucedemoPages.*;

class InventoryPageTest extends BaseTest {

    LoginPage loginPage;
    InventoryPage inventoryPage;

    /** Bejelentkezés. Felhnév: standard_user */
    public void login(LoginPage loginPage) {
        loginPage.openPage("https://www.saucedemo.com");
        String username = "standard_user";
        String password = "secret_sauce";
        loginPage.fillInputs(username, password);
        loginPage.clickOnLoginBtn();
    }

    @Test
    @Tag("shoppingCart")
    @DisplayName("Ellenőrizzük, hogy a kosárba helyezett elemek dararbszáma ugyanannyi-e, mint a kosár ikonon lévők.")
    void checkShoppingCartBadgeNumber() {
        loginPage = new LoginPage(driver);
        login(loginPage);

        inventoryPage = new InventoryPage(driver);

        String firstItemName = "Sauce Labs Backpack";
        String secondItemName = "Sauce Labs Bike Light";

        inventoryPage.addToCartOrRemove(firstItemName);
        inventoryPage.addToCartOrRemove(secondItemName);

        assertEquals(2, inventoryPage.getShoppingCartBadgeNumber(),
                "A kosárban lévő termékek darabszáma nem egyezik az elvárttal.");

        // Kijelentkezés.
        cleanUp(inventoryPage);
    }

    @ParameterizedTest
    @CsvSource({ "Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Fleece Jacket", "Sauce Labs Onesie", "Test.allTheThings() T-Shirt (Red)" })
    @Tag("homework")
    @DisplayName("Leellenőrizzük, hogy az összes terméknél megjelenik-e a Remove gomb.")
    void checkRemoveBtn(String itemName) {
        loginPage = new LoginPage(driver);
        login(loginPage);

        inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCartOrRemove(itemName);
        String expectedRemoveBtnText = "Remove";
        String actualRemoveBtnText = inventoryPage.getAddToCartOrRemoveBtnText(itemName);

        assertTrue(expectedRemoveBtnText.equals(
                actualRemoveBtnText),
                "A gomb felirata nem Remove " + itemName + "-nél.");
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
