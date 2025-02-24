package com.ratchanon.lab6_sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import android.Manifest;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper"; // Tag for logging
    private static final String DB_NAME = "friend_db";
    private static final int DB_VERSION = 3;
    public static final String TABLE_NAME = "tbl_bio";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_LASTNAME = "lastname";
    public static final String COL_YEAR = "year";
    public static final String COL_SEX = "sex";
    public static final String COL_BOOK = "book";
    public static final String COL_GAME = "gaming";
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        Log.i(TAG,"DatabaseHelper initialized");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public Cursor getFriendById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?",new String[]{id});
    }

    public void importDatabase() {
        @SuppressLint("SdCardPath") String url = "/data/data/" + this.context.getPackageName() + "/databases/friend_db";
        File f = new File(url);
        if (f.exists()) {
            boolean deleted = context.deleteDatabase(DB_NAME);
            if (deleted) {
                Log.i(TAG, "Database '" + DB_NAME + "' deleted successfully");
            } else {
                Log.e(TAG, "Failed to delete database '" + DB_NAME + "'");
            }
        }
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase mDb;
            mDb = databaseHelper.getWritableDatabase();
            mDb.close();
            databaseHelper.close();
            InputStream in = this.context.getAssets().open("friend_db");
            OutputStream out = new FileOutputStream(url);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            out.write(buffer, 0, buffer.length);
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, "Import database complete.", Toast.LENGTH_SHORT).show();
    }

    public void exportDatabase(){
        try {
            File sd = context.getExternalFilesDir(null); // ใช้ app-specific directory
            File data = Environment.getDataDirectory();

            String currentDBPath = "/data/" + context.getPackageName() + "/databases/friend_db";
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            String backupDBPath = "friend_db_" + currentDateTimeString;
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            Toast.makeText(context, "Exported to: " + backupDB.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "Export failed: " + e.toString(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Export error: ", e);
        }
    }
}
