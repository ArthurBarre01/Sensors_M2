package com.example.sensors_m2;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class GlobalClass {
    public static String PHONE_NUMBER="";
    public static ArrayList<Entry> Temp_values = new ArrayList<>();

    public static ArrayList<Entry> CO2_values= new ArrayList<>();

    public static ArrayList<Entry> Humidity_values = new ArrayList<>();

    public static ArrayList<Entry> Smoke_values = new ArrayList<>();


    public static double value_smoke = 116;
    public static int value_temp;
    public static int value_humid;
    public static int value_CO2;


}
