package com.example.sensors_m2;

import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class GlobalClass {

    public static TextView Phone;
    public static TextView Text;

    public static ArrayList<Entry> Temp_values = new ArrayList<>();

    public static ArrayList<Entry> CO2_values= new ArrayList<>();

    public static ArrayList<Entry> Humidity_values = new ArrayList<>();

}
