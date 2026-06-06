package saucedemoTests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
                                "A \"" + itemName + "\"-nél nem jó az ár, mert az elvárt ár: " + expectedPrice
                                                + " , az aktuális ár: "
                                                + actualPrice);
                cleanUp(inventoryPage);
        }

        @Test
        @DisplayName("Minden termékhez tartozik-e kosárhoz hozzáadás gomb.")
        void allItemsHaveAButton() {
                loginPage = new LoginPage(driver);
                login(loginPage);

                inventoryPage = new InventoryPage(driver);

                List<WebElement> items = driver.findElements(By.className("inventory_item"));

                // Megoldás1:
                for (WebElement item : items) {

                        WebElement btn = item.findElement(By.tagName("button"));
                        assertTrue(btn.isDisplayed(), "A" + item.getText() + "-nek nincsen hozzáadás gombja.");

                }

                // Megoldás2:
                List<WebElement> btns = driver.findElements(By.cssSelector("button[data-test^='add-to-cart']"));
                assertEquals(items.size(), btns.size(),
                                Math.abs(items.size() - btns.size()) + " db terméknek nincsen hozzáadás gombja.");
                cleanUp(inventoryPage);
        }

        @Test
        @DisplayName("Megjelenik-e az adott termékhez tartozó hozzáadás gomb.")
        void addToCartBtnIsDisplayed() {
                loginPage = new LoginPage(driver);
                login(loginPage);

                inventoryPage = new InventoryPage(driver);

                String termeknev = "Sauce Labs Backpack";

                WebElement hozzaAdasGomb = driver.findElement(
                                By.xpath("//div[@class='inventory_item' and .//div[normalize-space()='" + termeknev
                                                + "']]//button"));
                assertTrue(hozzaAdasGomb.isDisplayed(), "Nem jelenik meg az adott termékhez tartozó hozzáadás gomb.");
                cleanUp(inventoryPage);

        }

        @Test
        @DisplayName("Legdrágább elem megkeresése.")
        void mostExpensiveItem() {
                loginPage = new LoginPage(driver);
                login(loginPage);

                inventoryPage = new InventoryPage(driver);
                var itemsAndPrices = inventoryPage.getAllItemnamesAndTheirPrices();

                // Ez, csak akkor működik, hacsak egy termék a legdrágább.
                String maxItemName = null;
                Double maxItemPrice = 0.0;
                for (Map.Entry<String, Double> entry : itemsAndPrices.entrySet()) {
                        if (entry.getValue() > maxItemPrice) {
                                maxItemPrice = entry.getValue();
                                maxItemName = entry.getKey();
                        }
                }
                String expectedMaxItemName = "Sauce Labs Fleece Jacket";
                Double expectedMaxItemPrice = 49.99;
                assertEquals(expectedMaxItemName, maxItemName,
                                "Nem a " + expectedMaxItemName + " a legdrágább termék.");

                assertEquals(expectedMaxItemPrice,
                                maxItemPrice,
                                "Nem a $" + maxItemPrice + " értékű termék a legdrágább.");
                cleanUp(inventoryPage);
        }

        @Test
        @DisplayName("Legolcsóbb elem megkeresése.")
        void cheapestItem() {
                loginPage = new LoginPage(driver);
                login(loginPage);

                inventoryPage = new InventoryPage(driver);
                LinkedHashMap<String, Double> itemsAndPrices = inventoryPage.getAllItemnamesAndTheirPrices();

                // Ez, csak akkor működik, hacsak egy termék a legdrágább.
                String maxItemName = null;
                // Az első elem értékét adjuk meg neki.
                Double maxItemPrice = itemsAndPrices.values().stream()
                                .findFirst()
                                .orElse(null); // Akkor fut le, ha a Map üres lenne
                for (Map.Entry<String, Double> entry : itemsAndPrices.entrySet()) {
                        if (entry.getValue() < maxItemPrice) {
                                maxItemPrice = entry.getValue();
                                maxItemName = entry.getKey();
                        }
                }
                String expectedMaxItemName = "Sauce Labs Onesie";
                Double expectedMaxItemPrice = 7.99;
                assertEquals(expectedMaxItemName, maxItemName,
                                "Nem a " + expectedMaxItemName + " a legolcsóbb termék.");

                assertEquals(expectedMaxItemPrice,
                                maxItemPrice,
                                "Nem a $" + maxItemPrice + " értékű termék a legolcsóbb.");
                cleanUp(inventoryPage);
        }

        @Test
        @DisplayName("Leellenőrizzük, hogy az ABC sorrend jó-e.")
        void checkABCSort() {
                loginPage = new LoginPage(driver);
                login(loginPage);

                inventoryPage = new InventoryPage(driver);

                inventoryPage.changeOrderingAtoZ();

                // Lekérjük az aktuális listát.
                List<String> allItemName = inventoryPage.getAllItemName();

                // Hacsak az egyenlőség jel után teszed a változót, akkor nem hozol létre új
                // listát.
                List<String> orderedItemName = new ArrayList<>(allItemName);

                // A lista rendezése.
                Collections.sort(orderedItemName);

                assertEquals(allItemName, orderedItemName, "A termékek lista nincsen rendezve A-Z-ig.");
                cleanUp(inventoryPage);
        }

        @Test
        @DisplayName("Leellenőrizzük, hogy az ABC csökkenő sorrend jó-e.")
        void checkACSReverseSort() {
                loginPage = new LoginPage(driver);
                login(loginPage);

                inventoryPage = new InventoryPage(driver);

                inventoryPage.changeOrderingZtoA();

                // Lekérjük az aktuális listát.
                List<String> allItemName = inventoryPage.getAllItemName();

                // Hacsak az egyenlőség jel után teszed a változót, akkor nem hozol létre új
                // listát.
                List<String> orderedItemName = new ArrayList<>(allItemName);

                // A lista rendezése.
                Collections.sort(orderedItemName, Comparator.reverseOrder());

                assertEquals(allItemName, orderedItemName, "A termékek lista nincsen rendezve Z-A-ig.");
                cleanUp(inventoryPage);
        }
        // TODO a sorrend változtató gomb kettő árszerinti rendezés opciójának
        // tesztelése.
}
