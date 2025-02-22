package com.ratchanon.lab6_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "friend_db";
    private static final int DB_VERSION = 3;
    public static final String TABLE_NAME = "tbl_bio";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_LASTNAME = "lastname";
    public static final String COL_YEAR = "year";
    public static final String COL_SEX = "sex";
    public static final String COL_BOOK = "book";
    public static final String COL_SWIMMING = "swimming";

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
