package com.erik.example.star_goals;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by erik on 9/24/16.
 */
public class RatingHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_history);
        GridView gridView = (GridView) findViewById(R.id.results_grid);

        Intent intent = getIntent();
        int search_range = intent.getIntExtra("searchvalue", 1);
        String search_range_string = Integer.toString(search_range);
        ParcelableGoal Pgoal = intent.getParcelableExtra("selectedgoal");
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT " + DatabaseContract.RatingTable.RATING_DATE
                        + ", " + DatabaseContract.RatingTable.RATING_VALUE
                        + " FROM " + DatabaseContract.RatingTable.TABLE_NAME
                        + " WHERE " + DatabaseContract.RatingTable.RATED_GOAL + " = " + Pgoal.getID()
                        + " ORDER BY " + DatabaseContract.RatingTable.RATING_DATE + " DESC"
                        + " LIMIT " + search_range_string, null
        );
        final ArrayList<String> results = new ArrayList<>();
        c.moveToFirst();
        do {
            String rating_date = c.getString(
                    c.getColumnIndexOrThrow(DatabaseContract.RatingTable.RATING_DATE));
            results.add(rating_date);
            String rating_value = c.getString(
                    c.getColumnIndexOrThrow(DatabaseContract.RatingTable.RATING_VALUE));
            results.add(rating_value);
        } while(c.moveToNext());
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                results
        );
        gridView.setAdapter(arrayAdapter);
        db.close();

    }
    public class Tuple<A, B> {

        public final A a;
        public final B b;

        public Tuple(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }
}