package com.example.sensors_m2;

import android.provider.BaseColumns;

public final class SampleContract {
    private SampleContract() {}

    public static class SampleEntry implements BaseColumns {
        public static final String TABLE_NAME = "samples";
        public static final String COLUMN_NAME_VALUE = "value";
    }
}
