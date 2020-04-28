package com.afg.findmyopportunities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayOpportunityActivity extends AppCompatActivity {

    private static final String TAG = "DisplayOppActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_opportunity);

        if (getIntent().hasExtra("Opportunity"))
        {
            Opportunity opportunity = getIntent().getParcelableExtra("Opportunity");
            Log.d(TAG, "onCreate: " + opportunity.getTitle());

            TextView title = findViewById(R.id.titleText);
            title.setText(opportunity.getTitle());

            TextView description = findViewById(R.id.descriptionText);
            description.setText(opportunity.getDescription());

            TextView organizer = findViewById(R.id.organizerText);
            organizer.setText(opportunity.getOrganizer());

            TextView location = findViewById(R.id.locationText);
            location.setText(opportunity.getLocation());
        }

    }
}