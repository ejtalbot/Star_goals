package com.erik.example.star_goals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by erik on 9/1/16.
 */
public class GoalCreator extends AppCompatActivity {
    public static String GOAL_NAME = "com.example.first_app.GOAL";
    public static String CATEGORY_NAME = "com.example.first_app.CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_creator);
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
