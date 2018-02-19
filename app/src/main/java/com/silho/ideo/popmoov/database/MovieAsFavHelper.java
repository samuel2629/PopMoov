package com.silho.ideo.popmoov.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Samuel on 18/02/2018.
 */

public class MovieAsFavHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";

    public MovieAsFavHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieAsFavContract.MovieAsFavEntry.TABLE_NAME
                + " (" + MovieAsFavContract.MovieAsFavEntry.COLUMN_ID + " INTEGER PRIMARY KEY, "
                + MovieAsFavContract.MovieAsFavEntry.COLUMN_PATH_POSTER + " TEXT UNIQUE, "
                + MovieAsFavContract.MovieAsFavEntry.COLUMN_TITLE + " TEXT UNIQUE)";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
