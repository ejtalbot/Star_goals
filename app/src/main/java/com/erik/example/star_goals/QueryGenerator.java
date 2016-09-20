package com.erik.example.star_goals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by erik on 8/30/16.
 */
public class QueryGenerator extends AppCompatActivity {
    public static String QUERY_NAME = "com.example.first_app.QUERY";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_generator);
    }

    public void displayEntry(View view){
        Intent intent = new Intent(this, GoalDisplayer.class);
        EditText queryEntryField = (EditText) findViewById(R.id.query);
        String queryEntry = queryEntryField.getText().toString();
        intent.putExtra(QUERY_NAME, queryEntry);
        startActivity(intent);
    }
}
