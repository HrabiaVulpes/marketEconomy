package com.vulpes.ai.utils;


class UtilityMethodsTest {

    public static void main(String[] args) {
        Integer min = 3;
        Integer max = 7;
        int[] amounts = new int[10];

        for (int i = 0; i < 1000; i++){
            amounts[UtilityMethods.randomBetween(min, max)]++;
        }

        for (int i = 0; i < 10; i++){
            System.out.println(i + " = " + amounts[i]);
        }
    }

}