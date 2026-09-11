package tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import pages.InventoryPage;

public class InventorySortingTest extends BaseTest {

    @Test
    @DisplayName("Termékek rendezése ár szerint növevő sorrendben (Low to High)")
    public void testSortProductsByPriceLowToHigh() {
        InventoryPage inventoryPage = login();

        inventoryPage.changeOrderingLowtoHigh();

        List<Double> prices = inventoryPage.getAllItemnamesAndTheirPrices().values().stream().toList();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assertions.assertTrue(prices.get(i) <= prices.get(i + 1),
                    "A termékek nincsenek ár szerint növekvő sorrendbe rendezve.");
        }
    }
}
