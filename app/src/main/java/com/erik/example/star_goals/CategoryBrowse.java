package com.erik.example.star_goals;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by erik on 9/21/16.
 */
public class CategoryBrowse extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_category);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final ArrayList<String> currentCategories = new ArrayList<>();
        final ListView lvCategory = (ListView) findViewById(R.id.category_list_view);
        lvCategory.setItemsCanFocus(true);
        String categoryQuery = "SELECT DISTINCT " + DatabaseContract.GoalTable.COLUMN_NAME_CATEGORY + " FROM " + DatabaseContract.GoalTable.TABLE_NAME;
        Cursor c = db.rawQuery(categoryQuery, null);
        c.moveToFirst();
        do {
            String currentCategory = c.getString(
                    c.getColumnIndexOrThrow(DatabaseContract.GoalTable.COLUMN_NAME_CATEGORY));
            currentCategories.add(currentCategory);
        } while (c.moveToNext());
        db.close();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                currentCategories
        );
        lvCategory.setAdapter(arrayAdapter);
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String selectedCategory = (String) parent.getItemAtPosition(position);
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.GoalTable.TABLE_NAME +
                        " WHERE " + DatabaseContract.GoalTable.COLUMN_NAME_CATEGORY +
                        " = '" + selectedCategory +"'", null);
                final ArrayList<ParcelableGoal> results = new ArrayList<ParcelableGoal>();
                c.moveToFirst();
                do {
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
                    results.add(PGoal);
                } while (c.moveToNext());
                db.close();
                final ArrayAdapter<ParcelableGoal> arrayAdapterGoal = new ArrayAdapter<ParcelableGoal>(
                        CategoryBrowse.this,
                        android.R.layout.simple_list_item_1,
                        results
                );
                lvCategory.setVisibility(View.GONE);
                ListView lvGoal = (ListView) findViewById(R.id.goal_list_view);
                lvGoal.setAdapter(arrayAdapterGoal);
                lvGoal.setVisibility(View.VISIBLE);
                lvGoal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        ParcelableGoal item = (ParcelableGoal) parent.getItemAtPosition(position);
                        Intent intent = new Intent(CategoryBrowse.this, RateGoal.class);
                        intent.putExtra("goaltorate", item);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}