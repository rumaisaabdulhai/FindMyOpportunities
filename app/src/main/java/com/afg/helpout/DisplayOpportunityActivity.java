package com.afg.helpout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        // BOTTOM NAVIGATION MENU
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_home_nav:
                        Intent i1 = new Intent(DisplayOpportunityActivity.this, MainActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.ic_search_nav:
                        Intent i2 = new Intent(DisplayOpportunityActivity.this, OpportunitiesListActivity.class);
                        startActivity(i2);
                        break;

                    case R.id.ic_profile_nav:
                        Intent i3 = new Intent(DisplayOpportunityActivity.this, ProfileActivity.class);
                        startActivity(i3);
                        break;
                }
                return false;
            }
        });

    }
}