package data;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import config.ConfigReader;

public class UserDataProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        String validUsername = ConfigReader.get("USERNAME");
        String validPassword = ConfigReader.get("PASSWORD");
        String invalidPassword = ConfigReader.get("WRONG_PASSWORD");
        String baseURL = ConfigReader.get("BASE_URL");

        return Stream.of(
                // Formátum: Arguments.of(felhasználónév, jelszó, elvártURL/hibaüzenet,
                // sikerült-e)
                Arguments.of(
                        validUsername, validPassword, baseURL + "/inventory.html", true),
                Arguments.of("problem_user", validPassword, baseURL + "/inventory.html", true),
                Arguments.of("performance_glitch_user", validPassword, baseURL
                        + "/inventory.html",
                        true),
                Arguments.of("error_user", validPassword, baseURL
                        + "/inventory.html", true),
                Arguments.of("visual_user", validPassword, baseURL
                        + "/inventory.html", true),
                // Negatív tesztekhez.
                Arguments.of("locked_out_user", validPassword, "Epic sadface: Sorry, this user has been locked out.",
                        false));
    }
}