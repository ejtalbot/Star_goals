package com.erik.example.star_goals;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by erik on 8/27/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context){
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DatabaseContract.GoalTable.SQL_CREATE_ENTRIES);
        db.execSQL(DatabaseContract.RatingTable.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DatabaseContract.GoalTable.DELETE_TABLE);
        db.execSQL(DatabaseContract.RatingTable.DELETE_TABLE);
        onCreate(db);
    }
}