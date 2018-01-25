package com.example.leonardo.testapplication.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by leonardo on 24/01/18.
 */

public final class InfoContract {

    private static final String TEXT_TYPE = " TEXT";
    private static final String LONG_TYPE = " FLOAT";
    private static final String COMMA_SEP = ", ";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + InfoEntry.TABLE_NAME + " (" +
            InfoEntry.ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            InfoEntry.DATA + TEXT_TYPE + COMMA_SEP +
            InfoEntry.FECHA + LONG_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + InfoEntry.TABLE_NAME;

    private InfoContract() { }

    public static class InfoEntry implements BaseColumns {
        public static final String ID = BaseColumns._ID;    // ID por convención

        public static final String TABLE_NAME = "infoPet";
        public static final String DATA = "data";
        public static final String FECHA = "fecha";
    }

    public static class InfoDbHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "infoPet.db";
        public static final int DATABASE_VERSION = 1;

        public InfoDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
