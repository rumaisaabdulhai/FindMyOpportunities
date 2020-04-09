package com.afg.findmyopportunities;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Write a message to the database

        Log.d("Main Activity", "Before get instance");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("Main Activity", "After get instance");
        DatabaseReference myRef = database.getReference("message");

        if (myRef == null)
            Log.d("Main Activity", "NULL");
        else
            Log.d("Main Activity", "NOT NULL");

        myRef.setValue("Hello, World!");
    }

    public void openOpportunities(View v) {
        Intent intent = new Intent(this, OpportunitiesListActivity.class);
        startActivity(intent);
    }

}
