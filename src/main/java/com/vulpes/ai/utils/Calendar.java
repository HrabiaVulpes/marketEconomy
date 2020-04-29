package com.vulpes.ai.utils;

public class Calendar {
    public static Calendar instance = new Calendar();

    public static int year = 0;
    public static int month = 0;
    public static int week = 0;

    private Calendar(){ }

    public static void nextWeek(){
        week++;
        if (week >= 4){
            week = 0;
            month++;
            if (month >= 12){
                month = 0;
                year++;
            }
        }
    }
}
