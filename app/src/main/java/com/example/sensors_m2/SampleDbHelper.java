package com.example.sensors_m2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SampleDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SampleDB.db";

    private static final String SQL_CREATE_SAMPLES_TABLE =
            "CREATE TABLE " + SampleContract.SampleEntry.TABLE_NAME + " (" +
                    SampleContract.SampleEntry._ID + " INTEGER PRIMARY KEY," +
                    SampleContract.SampleEntry.COLUMN_NAME_VALUE + " REAL)";

    private static final String SQL_DELETE_SAMPLES_TABLE =
            "DROP TABLE IF EXISTS " + SampleContract.SampleEntry.TABLE_NAME;

    public SampleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SAMPLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_SAMPLES_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
