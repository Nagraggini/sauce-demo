package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import config.ConfigReader;
import pages.CartPage;
import pages.InventoryPage;

public class CartTest extends BaseTest {

    @Test
    @DisplayName("Leellenőrizzük, hogy a Continue Shopping gomb kiválasztása után vissza kerülünk-e az Inventory oldalra.")
    void checkContinueShoppingBtn() {
        InventoryPage inventoryPage = login();

        CartPage cartPage = inventoryPage.shoppingCart();

        cartPage.continueShopping();

        assertEquals(ConfigReader.get("BASE_URL") + "/inventory.html",
                cartPage.getCurrentUrl(),
                "A CartPage-n a Continue Shopping gomb kiválasztása után, nem kerültünk vissza az Inventory oldalra.");

        // cleanUp(inventoryPage);
    }
}
