package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

/** Menüpontok. */
public class MenuComponent extends BasePage {

    // Hamburger menü elemek.
    private final By hamburgerBtn = By.id("react-burger-menu-btn");
    private final By allItemsBtn = By.id("inventory_sidebar_link");
    private final By aboutBtn = By.id("about_sidebar_link");
    private final By logoutBtn = By.id("logout_sidebar_link");
    private final By resetAppStateBtn = By.id("reset_sidebar_link");
    private final By closeHamburgerMenu = By.id("react-burger-cross-btn");

    public MenuComponent(WebDriver driver) {
        super(driver);
    }

    public MenuComponent openMenu() {
        click(hamburgerBtn);
        // Várunk egy elemre, ami csak a menü megnyitása után jelenik meg.
        waitUntilVisible(logoutBtn);
        return this;
    }

    public MenuComponent closeMenu() {
        click(closeHamburgerMenu);
        return this;
    }

    public LoginPage logout() {
        openMenu();
        click(logoutBtn);
        return new LoginPage(driver);
    }

    public MenuComponent resetAppState() {
        openMenu();
        click(resetAppStateBtn);
        closeMenu();
        return this;
    }
}
