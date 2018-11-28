package com.example.hashi.weatherproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class AppDataBase {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private KarimAppsDataBase karimAppsDataBase;

    public AppDataBase(Context context) {
        this.context = context;
    }

    /*DATA BASE NAME AND VERSION , CHANGE VERSION WHILE CHNAGE IN TABLE*/
    private static final String DATA_BASE_NAME = "weather.db";
    private static final int DATA_BASE_VERSION = 1;

    /*  1 table name */
    private static final String TABLE_NAME_WEATHER = "weather";
    /*2 columns of table*/
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_TEMPERATURE = "temperature";
    private static final String COLUMN_WINDSPEED = "windspeed";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_COUNTRY = "country";

    /* 3to drop a table*/
    /**/
    private static final String DROP_TABLE_WEATHER = "DROP TABLE IF EXISTS " + TABLE_NAME_WEATHER;

    /* 4 to creta a table*/

    private static final String CREATE_TABLE_WEATHER =
            "CREATE TABLE " + TABLE_NAME_WEATHER + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_TEMPERATURE + " TEXT," +
                    COLUMN_WINDSPEED + " TEXT," +
                    COLUMN_CITY + " TEXT," +
                    COLUMN_COUNTRY + " TEXT)";

    /*5 th*/
    public long insertWeather(String description, String temperature, String windspeed, String city, String country) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_TEMPERATURE, temperature);
        contentValues.put(COLUMN_WINDSPEED, windspeed);
        contentValues.put(COLUMN_CITY, city);
        contentValues.put(COLUMN_COUNTRY, country);
        long insertedId = sqLiteDatabase.insert(TABLE_NAME_WEATHER, null, contentValues);
        return insertedId;
    }


    public ArrayList<MyWeather> getAllWeather() {

        ArrayList<MyWeather> myWeatherArrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_WEATHER, null, null, null, null, null, COLUMN_ID);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            String temperature = cursor.getString(cursor.getColumnIndex(COLUMN_TEMPERATURE));
            String windspeed = cursor.getString(cursor.getColumnIndex(COLUMN_WINDSPEED));
            String city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
            String country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY));

            MyWeather myWeather = new MyWeather(id, description, temperature, windspeed, city, country);
            myWeatherArrayList.add(myWeather);
        }
        return myWeatherArrayList;
    }


    public void deleteItem(String id) {
        sqLiteDatabase.delete(TABLE_NAME_WEATHER, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }


    public AppDataBase open() throws android.database.SQLException {
        karimAppsDataBase = new KarimAppsDataBase(context);
        sqLiteDatabase = karimAppsDataBase.getWritableDatabase();
        return AppDataBase.this;
    }

    public void close() {
        karimAppsDataBase.close();
    }


    private class KarimAppsDataBase extends SQLiteOpenHelper {

        public KarimAppsDataBase(Context context) {
            super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_WEATHER);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_TABLE_WEATHER);
        }
    }

}
