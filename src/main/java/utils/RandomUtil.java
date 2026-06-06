package utils;

import java.util.Random;

/** Random adatok generálása. */
public class RandomUtil {
    // TODO
    public static String randomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(new Random().nextInt(chars.length())));
        }

        return sb.toString();
    }
}
