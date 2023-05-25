package com.example.sensors_m2;

import android.widget.EditText;
import android.widget.Switch;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class GlobalClass {
    public static String PHONE_NUMBER="";
    public static ArrayList<Entry> Temp_values = new ArrayList<>();

    public static ArrayList<Entry> CO2_values= new ArrayList<>();

    public static ArrayList<Entry> Humidity_values = new ArrayList<>();

    public static ArrayList<Entry> Smoke_values = new ArrayList<>();

    public static void clear(){
        Temp_values.clear();
        CO2_values.clear();
        Humidity_values.clear();
        Smoke_values.clear();
    }

    public static int value_smoke = 116;
    public static int value_temp;
    public static int value_humid;
    public static int value_CO2;
    public static boolean initialize=false;
    public static boolean isUserLoggedIn = false;

    public static String relai1;
    public static String relai2;
    public static String relai3;
    public static String relai4;

    public static Switch switch1;
    public static Switch switch2;
    public static Switch switch3;
    public static Switch switch4;

    public static boolean isSwitchOn1;
    public static boolean isSwitchOn2;
    public static boolean isSwitchOn3;
    public static boolean isSwitchOn4;

}
