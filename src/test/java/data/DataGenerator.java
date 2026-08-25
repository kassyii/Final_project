package data;

import java.util.Random;

public class DataGenerator {

    private static final Random RANDOM = new Random();

    public static String getRandomEmail() {
        return "user_test" + RANDOM.nextInt(1000) + "@yandex.ru";
    }

    public static String getRandomPassword() {
        return "Pass!_" + RANDOM.nextInt(100000);
    }

    public static String getRandomTitle() {
        return "Title" + RANDOM.nextInt(100000);
    }

}