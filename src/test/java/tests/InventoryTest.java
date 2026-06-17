package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.BaseTest;
import config.ConfigReader;
import pages.InventoryPage;
import pages.MenuComponent;

class InventoryTest extends BaseTest {

        @Test
        @Tag("ui")
        @Tag("regression")
        @DisplayName("Ellenőrizzük, hogy a kosárba helyezett elemek darabszáma ugyanannyi-e, mint a kosár ikonon lévők.")
        void checkShoppingCartBadgeNumber() {
                assertEquals(2, login().addToCartOrRemove("Sauce Labs Backpack")
                                .addToCartOrRemove("Sauce Labs Bike Light").getShoppingCartBadgeNumber(),
                                "A kosárban lévő termékek darabszáma nem egyezik az elvárttal.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @ParameterizedTest
        @CsvSource({ "Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt",
                        "Sauce Labs Fleece Jacket", "Sauce Labs Onesie", "Test.allTheThings() T-Shirt (Red)" })
        @Tag("ui")
        @Tag("regression")
        @Tag("smoke")
        @DisplayName("Leellenőrizzük, hogy az összes terméknél megjelenik-e a Remove gomb.")
        void checkRemoveBtn(String itemName) {
                assertEquals("Remove",
                                login().addToCartOrRemove(itemName).getAddToCartOrRemoveBtnText(itemName),
                                "A gomb felirata nem Remove " + itemName + "-nél.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @ParameterizedTest
        @CsvSource({ "Sauce Labs Backpack , 29.99", "Sauce Labs Bike Light , 9.99", "Sauce Labs Bolt T-Shirt , 15.99",
                        "Sauce Labs Fleece Jacket , 49.99", "Sauce Labs Onesie , 7.99",
                        "Test.allTheThings() T-Shirt (Red) , 15.99" })
        @Tag("regression")
        @DisplayName("Leellenőrizzük az összes termék árát.")
        void checkThePriceOfItems(String itemName, double expectedPrice) {
                // 0.01 a tolerancia küszöb.
                assertEquals(expectedPrice, login().getPriceofAnItem(itemName), 0.01,
                                "A \"" + itemName + "\"-nél nem jó az ár, mert az elvárt ár.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @Test
        @Tag("ui")
        @Tag("regression")
        @DisplayName("Minden termékhez tartozik-e kosárhoz hozzáadás gomb.")
        void shouldDisplayAddToCartButtonForAllItems() {
                InventoryPage inventoryPage = login();

                List<WebElement> items = inventoryPage.getInventoryItemCards();

                // Megoldás1:
                for (WebElement item : items) {
                        WebElement btn = item.findElement(By.tagName("button"));
                        assertTrue(btn.isDisplayed(), "A" + item.getText() + "-nek nincsen hozzáadás gombja.");
                }

                // Megoldás2:
                List<WebElement> btns = inventoryPage.getAddToCartBtns();
                assertEquals(items.size(), btns.size(),
                                Math.abs(items.size() - btns.size()) + " db terméknek nincsen hozzáadás gombja.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @Test
        @Tag("smoke")
        @DisplayName("Megjelenik-e az adott termékhez tartozó hozzáadás gomb.")
        void addToCartBtnIsDisplayed() {
                assertTrue(login().getAddToCartOrRemoveBtn(
                                "Sauce Labs Backpack").isDisplayed(),
                                "Nem jelenik meg az adott termékhez tartozó hozzáadás gomb.");

                new MenuComponent(driver).resetAppState().logout();

        }

        @Test
        @Tag("regression")
        @DisplayName("Legdrágább elem megkeresése.")
        void mostExpensiveItem() {
                var itemsAndPrices = login().getAllItemnamesAndTheirPrices();

                // Ez, csak akkor működik, hacsak egy termék a legdrágább.
                String maxItemName = null;
                Double maxItemPrice = 0.0;
                for (Map.Entry<String, Double> entry : itemsAndPrices.entrySet()) {
                        if (entry.getValue() > maxItemPrice) {
                                maxItemPrice = entry.getValue();
                                maxItemName = entry.getKey();
                        }
                }

                assertEquals("Sauce Labs Fleece Jacket", maxItemName,
                                "Nem a " + "Sauce Labs Fleece Jacket" + " a legdrágább termék.");

                assertEquals(49.99,
                                maxItemPrice,
                                "Nem a $" + 49.99 + " értékű termék a legdrágább.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @Test
        @Tag("regression")
        @DisplayName("Legolcsóbb elem megkeresése.")
        void cheapestItem() {
                LinkedHashMap<String, Double> itemsAndPrices = login().getAllItemnamesAndTheirPrices();

                // Ez, csak akkor működik, hacsak egy termék a legolcsóbb.
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

                assertEquals("Sauce Labs Onesie", maxItemName,
                                "Nem a " + "Sauce Labs Onesie" + " a legolcsóbb termék.");

                assertEquals(7.99,
                                maxItemPrice,
                                "Nem a $" + maxItemPrice + " értékű termék a legolcsóbb.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @Test
        @Tag("ui")
        @Tag("regression")
        @DisplayName("Leellenőrizzük, hogy a kosár üres-e a bejelentkezéskor.")
        void checkCartBadgeNumber() {
                assertEquals(0, login().getShoppingCartBadgeNumber(), "A kosár nem üres "
                                + login().getShoppingCartBadgeNumber() + " db elem van benne.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @Test
        @Tag("ui")
        @Tag("regression")
        @DisplayName("Leellenőrizzük, hogy az ABC sorrend jó-e.")
        void shouldSortItemsAlphabetically() {
                // Sorrend átállítás és lekérjük az aktuális listát.
                List<String> allItemName = login().changeOrderingAtoZ().getAllItemName();

                // Hacsak az egyenlőség jel után teszed a változót, akkor nem hozol létre új
                // listát.
                List<String> orderedItemName = new ArrayList<>(allItemName);

                // A lista rendezése.
                Collections.sort(orderedItemName);

                assertEquals(allItemName, orderedItemName, "A termékek lista nincsen rendezve A-Z-ig.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @Test
        @Tag("ui")
        @Tag("regression")
        @DisplayName("Leellenőrizzük, hogy az ABC csökkenő sorrend jó-e.")
        void checkACSReverseSort() {
                // Sorrend átállítás és lekérjük az aktuális listát.
                List<String> allItemName = login().changeOrderingZtoA().getAllItemName();

                // Hacsak az egyenlőség jel után teszed a változót, akkor nem hozol létre új
                // listát.
                List<String> orderedItemName = new ArrayList<>(allItemName);

                // A lista rendezése.
                Collections.sort(orderedItemName, Comparator.reverseOrder());

                assertEquals(allItemName, orderedItemName, "A termékek lista nincsen rendezve Z-A-ig.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @Test
        @Tag("ui")
        @Tag("regression")
        @DisplayName("Leellenőrizzük, hogy az árszerint növekvő sorrend jó-e.")
        void checkLowToHighSort() {
                // 1. Lekérjük az aktuális, rendezettnek szánt listát.
                LinkedHashMap<String, Double> actualList = login().changeOrderingLowtoHigh()
                                .getAllItemnamesAndTheirPrices();

                // 2. Létrehozzuk az elvárt rendezett listát az eredeti lista rendezésével.
                Map<String, Double> expectedList = actualList.entrySet().stream()
                                .sorted(Map.Entry.comparingByValue())
                                .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (e1, e2) -> e1,
                                                LinkedHashMap::new));

                assertEquals(expectedList,
                                actualList,
                                "A termékek lista nincsen rendezve árszerint növekvő sorrendben.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @Test
        @Tag("ui")
        @Tag("regression")
        @DisplayName("Leellenőrizzük, hogy az ár szerinti csökkenő sorrend jó-e.")
        void checkHighToLowSort() {
                // Sorrend átállítás és lekérjük az aktuális listát az oldalról
                LinkedHashMap<String, Double> actualList = login().changeOrderingHightoLow()
                                .getAllItemnamesAndTheirPrices();

                // Létrehozzuk az elvárt listát, de itt rendezünk fordítva:
                Map<String, Double> expectedList = actualList.entrySet().stream()
                                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) // Csökkenő
                                .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (e1, e2) -> e1,
                                                LinkedHashMap::new));

                assertEquals(expectedList, actualList,
                                "A termékek lista nincsen rendezve ár szerint csökkenő sorrendben.");

                new MenuComponent(driver).resetAppState().logout();
        }

        @Test
        @Tag("ui")
        @Tag("regression")
        @DisplayName("Twitter link ellenőrzése.")
        void shouldOpenTwitterInNewTab() {
                InventoryPage inventoryPage = login();

                String originalTab = inventoryPage.getCurrentTabHandle();
                inventoryPage.clickOnTwitterlink().switchToTab();
                assertTrue(driver.getCurrentUrl().contains("twitter.com") || driver.getCurrentUrl()
                                .contains("x.com"));
                inventoryPage.closeTabAndReturnTo(originalTab);

                new MenuComponent(driver).resetAppState().logout();
                assertEquals(driver.getCurrentUrl().substring(0,
                                driver.getCurrentUrl().length() - 1),
                                ConfigReader.get("BASE_URL"),
                                "Az url cím hibás.");
        }
}
