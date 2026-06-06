package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/** Képernyőkép készítés a böngészőről. */
public class ScreenshotUtil {

    /*
     * Ezt így lehet használni, pl: ScreenshotUtil.takeScreenshot(driver,
     * "login-page");
     * Headless módban is működik.
     */
    public static String takeScreenshot(WebDriver driver, String name) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "logs/" + name + ".png";

        try {
            Files.copy(src.toPath(), Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}
