package saucedemoTests;

import java.util.List;

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
        }

        @Test
        @DisplayName("Megjelenik-e az adott termékhez tartozó hozzáadás gomb.")
        void addToCartBtnIsDisplayed() {
                loginPage = new LoginPage(driver);
                login(loginPage);

                String termeknev = "Sauce Labs Backpack";

                WebElement hozzaAdasGomb = driver.findElement(
                                By.xpath("//div[@class='inventory_item' and .//div[normalize-space()='" + termeknev
                                                + "']]//button"));
                assertTrue(hozzaAdasGomb.isDisplayed(), "Nem jelenik meg az adott termékhez tartozó hozzáadás gomb.");
        }
        // TODO a sorrend változtató gomb mind a négy opciójának tesztelése.
        // TODO legdrágább és legolcsóbb termék neve
}
