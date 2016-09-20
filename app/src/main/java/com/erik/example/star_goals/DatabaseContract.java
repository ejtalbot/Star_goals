package com.erik.example.star_goals;

import android.provider.BaseColumns;

/**
 * Created by erik on 8/27/16.
 */
public final class DatabaseContract {
    public static final  int    DATABASE_VERSION   = 3;
    public static final  String DATABASE_NAME      = "database.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String COMMA_SEP          = ",";
    private static final String INTEGER_TYPE       = " INTEGER";
    private DatabaseContract() {}

    public static class GoalTable implements BaseColumns {
        public static final String TABLE_NAME = "allGoals";
        public static final String COLUMN_NAME_GOALNAME = "name";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_CREATIONDATE = "creationdate";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + GoalTable.TABLE_NAME + " (" +
                        GoalTable._ID + " INTEGER PRIMARY KEY," +
                        GoalTable.COLUMN_NAME_GOALNAME + TEXT_TYPE + COMMA_SEP +
                        GoalTable.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                        GoalTable.COLUMN_NAME_CREATIONDATE + " DEFAULT CURRENT_TIMESTAMP )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    public static class RatingTable implements BaseColumns {
        public static final String TABLE_NAME = "allRatings";
        public static final String RATING_VALUE = "value";
        public static final String RATING_DATE = "daterated";
        public static final String RATED_GOAL = "goal";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + RatingTable.TABLE_NAME + " (" +
                        RatingTable._ID + " INTEGER PRIMARY KEY," +
                        RatingTable.RATING_VALUE + INTEGER_TYPE + COMMA_SEP +
                        RatingTable.RATING_DATE + " DEFAULT CURRENT_TIMESTAMP" + COMMA_SEP +
                        RatingTable.RATED_GOAL + INTEGER_TYPE + COMMA_SEP +
                        " FOREIGN KEY("+RatingTable.RATED_GOAL+") REFERENCES GoalTable(" + _ID + "))";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
