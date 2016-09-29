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
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by erik on 9/5/16.
 */
public class RateGoal extends AppCompatActivity {
    static RatingBar ratingBar;
    static NumberPicker numberPicker;
    static ParcelableGoal goal_to_rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_rate);

        Intent intent = getIntent();
        ParcelableGoal goal_to_rate = intent.getParcelableExtra("goaltorate");
        TextView selected_goal_view = (TextView) findViewById(R.id.goal_to_rate);
        selected_goal_view.setText("Goal Name: " + goal_to_rate.getName());
        TextView selected_goal_category_view = (TextView) findViewById(R.id.goal_category_to_rate);
        selected_goal_category_view.setText("Goal Category: " + goal_to_rate.getCategory());
        TextView selected_goal_date_view = (TextView) findViewById(R.id.goal_date_to_rate);
        selected_goal_date_view.setText("Creation Date:  " + goal_to_rate.getDate());
        String goal_to_rate_id = goal_to_rate.getID();
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //Cursor c = db.rawQuery("SELECT " + DatabaseContract.RatingTable.RATING_VALUE + " FROM "
        //        + DatabaseContract.RatingTable.TABLE_NAME + " WHERE "
        //        + DatabaseContract.RatingTable.RATED_GOAL + " = ?", new String[] {goal_to_rate.getID()});
        //Cursor c = db.rawQuery("SELECT " + DatabaseContract.RatingTable.RATING_VALUE + " FROM "
        //        + DatabaseContract.RatingTable.TABLE_NAME + " WHERE "
        //        + DatabaseContract.RatingTable.RATED_GOAL + "=1", null);
        //c.moveToFirst();
        //String this_rating = c.getString(
        //        c.getColumnIndexOrThrow(DatabaseContract.RatingTable.RATING_VALUE));
        db.close();
        numberPicker = (NumberPicker) findViewById(R.id.search_range);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
    }

    public void submitRating(View view){
        View ratingSubmittedView = (View) findViewById(R.id.goal_rate_success);
        Snackbar ratingSubmitted = Snackbar.make(ratingSubmittedView, "Rating Submitted", Snackbar.LENGTH_LONG);
        Integer rating = Math.round(ratingBar.getRating());
        Intent intent = getIntent();
        ParcelableGoal goal_to_rate = intent.getParcelableExtra("goaltorate");
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
        db.close();
    }

    public void viewRatingHistory(View view){
        Intent intent = getIntent();
        ParcelableGoal goal_to_rate = intent.getParcelableExtra("goaltorate");
        numberPicker = (NumberPicker) findViewById(R.id.search_range);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        int range_to_search = numberPicker.getValue();
        System.out.println(range_to_search);
        Intent viewRatingIntent = new Intent(this, RatingHistory.class);
        viewRatingIntent.putExtra("searchvalue", range_to_search);
        viewRatingIntent.putExtra("selectedgoal", goal_to_rate);
        startActivity(viewRatingIntent);
    }
}