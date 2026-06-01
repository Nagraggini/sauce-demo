package saucedemoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import saucedemoPages.InventoryPage;
import saucedemoPages.LoginPage;

class InventoryPageTest extends BaseTest {

    LoginPage loginPage;
    InventoryPage inventoryPage;

    @Test
    @Tag("shoppingCart")
    @DisplayName("Ellenőrizzük, hogy a kosárba helyezett elemek darabszáma ugyanannyi-e, mint a kosár ikonon lévők.")
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

    @ParameterizedTest
    @CsvSource({ "Sauce Labs Backpack , 29.99", "Sauce Labs Bike Light , 9.99", "Sauce Labs Bolt T-Shirt , 15.99",
            "Sauce Labs Fleece Jacket , 49.99", "Sauce Labs Onesie , 7.99",
            "Test.allTheThings() T-Shirt (Red) , 15.99" })
    @Tag("homework")
    @DisplayName("Leellenőrizzük az összes termék árát.")
    void checkThePriceOfItems(String itemName, double expectedPrice) {
        loginPage = new LoginPage(driver);
        login(loginPage);

        inventoryPage = new InventoryPage(driver);

        double actualPrice = inventoryPage.getPriceofAnItem(itemName);

        // 0.01 a tolerancia küszöb.
        assertEquals(expectedPrice, actualPrice, 0.01,
                "A \"" + itemName + "\"-nél nem jó az ár, mert az elvárt ár: " + expectedPrice + " , az aktuális ár: "
                        + actualPrice);
        cleanUp(inventoryPage);
    }

}
