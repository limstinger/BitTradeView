package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trades.db"; // 데이터베이스 이름
    private static final int DATABASE_VERSION = 1; // 데이터베이스 버전
    private static final String TABLE_NAME = "trades"; // 테이블 이름
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATA = "data"; // JSON 데이터를 저장할 컬럼

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성 SQL
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DATA + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 기존 테이블 삭제 및 새 테이블 생성
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // 데이터를 저장하는 메서드
    public void saveTrades(String jsonData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATA, jsonData);

        // 기존 데이터 삭제 후 새 데이터 삽입
        db.delete(TABLE_NAME, null, null); // 기존 데이터 삭제
        db.insert(TABLE_NAME, null, values); // 새 데이터 삽입
        db.close();
    }

    // 데이터를 불러오는 메서드
    public String getTrades() {
        SQLiteDatabase db = this.getReadableDatabase();
        String jsonData = null;

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_DATA},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            jsonData = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATA));
            cursor.close();
        }

        db.close();
        return jsonData;
    }
}

