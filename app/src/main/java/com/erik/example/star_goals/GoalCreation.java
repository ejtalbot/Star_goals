package com.erik.example.star_goals;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by erik on 8/24/16.
 */
public class GoalCreation extends AppCompatActivity {
    public static String QUERY_NAME = "com.example.first_app.QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_display);

        Intent intent = getIntent();
        String goal = intent.getStringExtra(MainActivity.GOAL_NAME);
        String category = intent.getStringExtra(MainActivity.CATEGORY_NAME);

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.GoalTable.COLUMN_NAME_GOALNAME, goal);
        values.put(DatabaseContract.GoalTable.COLUMN_NAME_CATEGORY, category);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date());
        values.put(DatabaseContract.GoalTable.COLUMN_NAME_CREATIONDATE, strDate);
        long newRowId = db.insert(DatabaseContract.GoalTable.TABLE_NAME, null, values);
    }
    public void displayEntry(View view){
        Intent intent = new Intent(this, GoalDisplayer.class);
        EditText queryEntryField = (EditText) findViewById(R.id.query);
        String queryEntry = queryEntryField.getText().toString();
        intent.putExtra(QUERY_NAME, queryEntry);
        startActivity(intent);
    }
}