package com.erik.example.star_goals;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static String GOAL_NAME = "com.example.first_app.GOAL";
    public static String CATEGORY_NAME = "com.example.first_app.CATEGORY";
    public static String QUERY_NAME = "com.example.first_app.QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_query:
                Intent queryIntent = new Intent(MainActivity.this, QueryGenerator.class);
                startActivity(queryIntent);
                return true;
            case R.id.add_new_goal:
                Intent goalCreationIntent = new Intent(MainActivity.this, GoalCreator.class);
                startActivity(goalCreationIntent);
                return true;
            default:
                return false;
        }
    }

    public void createGoal(View view){
        Intent intent = new Intent(this, GoalCreation.class);
        EditText editGoalText = (EditText) findViewById(R.id.goal_entry);
        String goal = editGoalText.getText().toString();
        intent.putExtra(GOAL_NAME, goal);
        EditText editCategoryText = (EditText) findViewById(R.id.category_entry);
        String category = editCategoryText.getText().toString();
        intent.putExtra(CATEGORY_NAME, category);
        startActivity(intent);
    }
}
