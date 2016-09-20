package com.erik.example.star_goals;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by erik on 9/5/16.
 */
public class RateGoal extends AppCompatActivity {
    static RatingBar ratingBar;
    static ParcelableGoal goal_to_rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_rate);

        Intent intent = getIntent();
        ParcelableGoal goal_to_rate = intent.getParcelableExtra("entries");
        TextView selected_goal_view = (TextView) findViewById(R.id.goal_to_rate);
        selected_goal_view.setText("Goal Name: " + goal_to_rate.getName());
        TextView selected_goal_category_view = (TextView) findViewById(R.id.goal_category_to_rate);
        selected_goal_category_view.setText("Goal Category: " + goal_to_rate.getCategory());
        TextView selected_goal_date_view = (TextView) findViewById(R.id.goal_date_to_rate);
        selected_goal_date_view.setText("Creation Date:  " + goal_to_rate.getDate());
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT value FROM allRatings WHERE goal = ?", new String[] {goal_to_rate.getID()});
        c.moveToLast();
        String this_rating = c.getString(
                c.getColumnIndexOrThrow(DatabaseContract.RatingTable.RATING_VALUE));
        System.out.println(this_rating);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
    }

    public void submitRating(View view){
        View ratingSubmittedView = (View) findViewById(R.id.goal_rate_success);
        Snackbar ratingSubmitted = Snackbar.make(ratingSubmittedView, "Rating Submitted", Snackbar.LENGTH_LONG);
        Integer rating = Math.round(ratingBar.getRating());
        Intent intent = getIntent();
        ParcelableGoal goal_to_rate = intent.getParcelableExtra("entries");
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.RatingTable.RATING_VALUE, rating);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date());
        values.put(DatabaseContract.RatingTable.RATING_DATE, strDate);
        System.out.println(goal_to_rate.getCategory());
        int ratedGoal = Integer.parseInt(goal_to_rate.getID());
        values.put(DatabaseContract.RatingTable.RATED_GOAL, ratedGoal);
        long newRowId = db.insert(DatabaseContract.RatingTable.TABLE_NAME, null, values);
        ratingSubmitted.show();
    }
}