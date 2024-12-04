package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class TradeDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TradeDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TRADE = "trade";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATA = "data";

    public TradeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_TRADE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATA + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRADE);
        onCreate(db);
    }

    public void saveTrades(String jsonData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TRADE); // 기존 데이터 삭제
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATA, jsonData);
        db.insert(TABLE_TRADE, null, values);
    }

    public String getTrades() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TRADE, new String[]{COLUMN_DATA},
                null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String data = cursor.getString(0);
            cursor.close();
            return data;
        }
        return null;
    }
}

