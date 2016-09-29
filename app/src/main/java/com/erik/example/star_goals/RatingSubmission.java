package com.erik.example.star_goals;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by erik on 9/14/16.
 */
public class RatingSubmission extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        int  submittedValue = intent.getIntExtra("rating_value", 0);
        values.put(DatabaseContract.RatingTable.RATING_VALUE, submittedValue);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date());
        values.put(DatabaseContract.RatingTable.RATING_DATE, strDate);
        int ratedGoal = intent.getIntExtra("rated_goal", 0);
        values.put(DatabaseContract.RatingTable.RATED_GOAL, ratedGoal);
        long newRowId = db.insert(DatabaseContract.RatingTable.TABLE_NAME, null, values);
        db.close();
    }
}
