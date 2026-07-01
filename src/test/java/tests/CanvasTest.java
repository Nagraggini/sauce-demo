package tests;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.JavascriptExecutor;

import base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import pages.LoginPage;

public class CanvasTest extends BaseTest {

    @Epic("Map Rendering")
    @Feature("Canvas")
    @Story("Canvas Pixel Verification")
    @Tag("ui")
    @Tag("canvas")
    @Tag("javascript")
    @DisplayName("Canvas-on lévő pixel kiolvasása és ellenőrzése.")
    void readCanvasPixel() {
        // https://developer.mozilla.org/en-US/docs/Web/API/Canvas_API
        new LoginPage(driver)
                .openPage("https://kitchen.applitools.com/ingredients/canvas");

        // JavaScript API használata
        /*
         * A Seleniumból JavaScript-kódot futtatunk a böngészőben, és azon keresztül
         * érjük
         * el a webalkalmazás vagy egy JavaScript-könyvtár objektumait.
         */
        JavascriptExecutor js = (JavascriptExecutor) driver;

        int x = 100;
        int y = 100;

        List<Long> pixel = (List<Long>) js.executeScript("""
                const canvas = document.getElementById("burger_canvas");
                const ctx = canvas.getContext("2d");

                return Array.from(
                    ctx.getImageData(arguments[0], arguments[1], 1, 1).data
                );
                """, x, y);

        System.out.println("---------------------------");
        System.out.println("Pixel coordinate : (" + x + "," + y + ")");
        System.out.println("Red   : " + pixel.get(0));
        System.out.println("Green : " + pixel.get(1));
        System.out.println("Blue  : " + pixel.get(2));
        System.out.println("Alpha : " + pixel.get(3));
        System.out.println("RGBA  : " + pixel); // RGBA : [237, 242, 247, 255]
        System.out.println("---------------------------");

        List<Long> expectedColor = List.of(237L, 242L, 247L, 255L);

        assertEquals(expectedColor, pixel);
    }

}