package com.erik.example.star_goals;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by erik on 8/24/16.
 */
public class GoalDisplayer extends AppCompatActivity {
    //public static String TEST = "com.example.first_app.TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_display);
        View noGoalView = findViewById(R.id.no_goal);
        Snackbar noGoalMessage = Snackbar.make(noGoalView, R.string.no_matches, Snackbar.LENGTH_LONG);

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.GoalTable._ID,
                DatabaseContract.GoalTable.COLUMN_NAME_GOALNAME,
                DatabaseContract.GoalTable.COLUMN_NAME_CATEGORY,
                DatabaseContract.GoalTable.COLUMN_NAME_CREATIONDATE,
        };
        String selection = DatabaseContract.GoalTable.COLUMN_NAME_GOALNAME + " = ?";
        Intent intent = getIntent();
        String queryText = intent.getStringExtra(GoalCreation.QUERY_NAME);
        String[] selectionArgs = { queryText };

        String sortOrder =
                DatabaseContract.GoalTable.COLUMN_NAME_GOALNAME + " DESC";
        Cursor c = db.query(
                DatabaseContract.GoalTable.TABLE_NAME,    // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        final ArrayList<ParcelableGoal> results = new ArrayList<ParcelableGoal>();
        //ArrayList<String[]> action = new ArrayList<String[]>();
        ListView lv = (ListView) findViewById(R.id.results_list_view);

        lv.setItemsCanFocus(true);
        c.moveToFirst();
        do {
            try {
            String dbID = c.getString(
                    c.getColumnIndexOrThrow(DatabaseContract.GoalTable._ID)
            );
            String goalDisplayName = c.getString(
                c.getColumnIndexOrThrow(DatabaseContract.GoalTable.COLUMN_NAME_GOALNAME)
            );
            String categoryDisplayName = c.getString(
                c.getColumnIndexOrThrow(DatabaseContract.GoalTable.COLUMN_NAME_CATEGORY)
            );
            String dateDisplayName = c.getString(
                c.getColumnIndexOrThrow(DatabaseContract.GoalTable.COLUMN_NAME_CREATIONDATE)
            );
            ParcelableGoal PGoal = new ParcelableGoal(dbID, goalDisplayName, categoryDisplayName, dateDisplayName);
            results.add(PGoal); } catch (CursorIndexOutOfBoundsException cexept) {
                noGoalMessage.show();
            }
            } while (c.moveToNext());
        final ArrayAdapter<ParcelableGoal> arrayAdapter = new ArrayAdapter<ParcelableGoal>(
                this,
                android.R.layout.simple_list_item_1,
                results
        );
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ParcelableGoal item = (ParcelableGoal) parent.getItemAtPosition(position);
                Intent intent = new Intent(GoalDisplayer.this, RateGoal.class);
                intent.putExtra("entries", item);
                startActivity(intent);
            }
        });
    }
}