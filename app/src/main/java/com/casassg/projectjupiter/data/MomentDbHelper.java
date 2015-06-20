package com.casassg.projectjupiter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by casassg on 19/06/15.
 *
 * @author casassg
 */
public class MomentDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "moment.db";
    private static final int DATABASE_VERSION = 1;

    public MomentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOMENT_TABLE =
                "CREATE TABLE " + MomentContract.MomentEntry.TABLE_NAME + " (" +
                        MomentContract.MomentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MomentContract.MomentEntry.COL_TITLE + " TEXT NOT NULL, " +
                        MomentContract.MomentEntry.COL_RATING + " REAL NOT NULL, " +
                        MomentContract.MomentEntry.COL_COORD_X + " REAL NULL, " +
                        MomentContract.MomentEntry.COL_COORD_Y + " REAL NULL, " +
                        MomentContract.MomentEntry.COL_DATE + " INTEGER DEFAULT (strftime('%s','now'))" +
                        ");";
        db.execSQL(SQL_CREATE_MOMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //CAN'T DROP!! MUST IMPLEMENT ALTER TABLES DEPENDING OF THE CHANGES AND VERSIONS
    }
}
