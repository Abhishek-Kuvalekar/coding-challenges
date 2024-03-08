package org.example.util;

import java.util.Random;

public class NumberUtils {
    public static int getRandomInteger() {
        Random random = new Random();
        return Math.abs(random.nextInt());
    }
}
