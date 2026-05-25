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
    @Tag("now")
    @DisplayName("Ellenőrizzük, hogy a kosárban is ugyanannyi a két termék ára.")
    void checkCheckoutPrices() {
        loginPage = new LoginPage(driver);

        loginPage.openPage("https://www.saucedemo.com");
        String username = "standard_user";
        String password = "secret_sauce";
        loginPage.fillInputs(username, password);
        loginPage.clickonLoginBtn();

        inventoryPage = new InventoryPage(driver);

        String firstItemname = "Sauce Labs Backpack";
        String secondItemname = "Sauce Labs Bike Light";

        double firstItemPrice = inventoryPage.getPriceofAnItem(firstItemname);
        double secondItemPrice = inventoryPage.getPriceofAnItem(secondItemname);

        // inventoryPage.addToCart(firstItemname);
        // inventoryPage.addToCart(secondItemname);

        // TODO
        // Kivesszük a két terméket a kosárból.
        // Kijelentkezés.

        inventoryPage.clickonhamburgerBtn();
        inventoryPage.clickonLogoutBtn();
    }

}
