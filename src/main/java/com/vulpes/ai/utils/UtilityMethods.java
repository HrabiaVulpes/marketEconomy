package com.vulpes.ai.utils;

import java.util.Random;

public class UtilityMethods {
    static Random random = new Random();
    public static Integer randomBetween(Integer min, Integer max) {
        return random.nextInt(max-min+1) + min;
    }
}
