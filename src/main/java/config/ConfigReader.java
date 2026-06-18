package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*A felhazsnálónév, jelszó és url-hez. */
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input != null) {
                properties.load(input);
            }

        } catch (IOException e) {
            throw new RuntimeException("Nem sikerült betölteni a config.properties fájlt.", e);
        }
    }

    public static String get(String key) {

        // Először környezeti változó (GitHub Secrets)
        String envValue = System.getenv(key);

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        // Ha nincs, akkor config.properties
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {

        String key = "BASE_URL";
        // Először környezeti változó (GitHub Secrets)
        String envValue = System.getenv(key);

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        // Ha nincs, akkor config.properties
        return properties.getProperty(key);
    }

    public static String getUsername() {

        String key = "USERNAME";
        // Először környezeti változó (GitHub Secrets)
        String envValue = System.getenv(key);

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        // Ha nincs, akkor config.properties
        return properties.getProperty(key);
    }

    public static String getPassword() {

        String key = "PASSWORD";
        // Először környezeti változó (GitHub Secrets)
        String envValue = System.getenv(key);

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        // Ha nincs, akkor config.properties
        return properties.getProperty(key);
    }

    public static String getWrongPassword() {

        String key = "WRONG_PASSWORD";
        // Először környezeti változó (GitHub Secrets)
        String envValue = System.getenv(key);

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        // Ha nincs, akkor config.properties
        return properties.getProperty(key);
    }
}