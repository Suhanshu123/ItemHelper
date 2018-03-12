package com.example.android.itemrecorder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Suhanshu on 11-03-2018.
 */

public class DbHelperClass extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "itemlist.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold waitlist data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + ContractClass.WaitlistEntry.TABLE_NAME + " (" +
                ContractClass.WaitlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ContractClass.WaitlistEntry.COLUMN_PERSON_NAME + " TEXT NOT NULL, " +
                ContractClass.WaitlistEntry.COLUMN_ITEM_BOUGHT + " TEXT NOT NULL, " +
                ContractClass.WaitlistEntry.COLUMN_ITEM_AMOUNT + " FLOAT NOT NULL, " +
                ContractClass.WaitlistEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContractClass.WaitlistEntry.TABLE_NAME);
        onCreate(db);
    }

}
