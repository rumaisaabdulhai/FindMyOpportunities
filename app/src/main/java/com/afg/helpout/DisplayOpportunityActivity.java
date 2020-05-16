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

/**
 * The DisplayOpportunityActivity Class
 *
 * Displays a Specific Opportunity Passed From OpportunityListActivity.
 *
 * Processes data from OpportunityListActivity.
 * Displays the Opportunity's name, description, contact info, etc.
 */
public class DisplayOpportunityActivity extends AppCompatActivity {

    // TAG for logging
    private static final String TAG = "DisplayOppActivity";

    // Index of the Activity for the BottomNavigationView
    public static final int INDEX = 1;

    /**
     * This is the onCreate Method for DisplayOpportunityActivity.
     *
     * When called, it processes data from the OpportunityListActivity
     * and displays the appropriate information for the Opportunity that was
     * clicked in the RecyclerView.
     *
     * It also handles clicks for the BottomNavigationView.
     *
     * @param savedInstanceState State of the UI Controller.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_opportunity);

        // If the Intent has a message called "Opportunity"
        if (getIntent().hasExtra("Opportunity"))
        {
            // Passes in object from OpportunityListActivity
            Opportunity opportunity = getIntent().getParcelableExtra("Opportunity");
            Log.d(TAG, "onCreate: " + opportunity.getTitle());

            // Get Title Reference and Set Proper Title
            TextView title = findViewById(R.id.titleText);
            title.setText(opportunity.getTitle());

            // Get Description Reference and Set Proper Description
            TextView description = findViewById(R.id.descriptionText);
            description.setText(opportunity.getDescription());

            // Get Organizer Reference and Set Proper Organizer
            TextView organizer = findViewById(R.id.organizerText);
            organizer.setText(opportunity.getOrganizer());

            // Get Location Reference and Set Proper Location
            TextView location = findViewById(R.id.locationText);
            location.setText(opportunity.getLocation());
        }

        // BOTTOM NAVIGATION MENU
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(INDEX);
        menuItem.setChecked(true);  // Changes Icon Color of Current Activity Selected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            /**
             * @param item The Item that is selected from the BottomNavigationMenu
             * @return true if the item is selected, false otherwise
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Switch cases on the ID of the Item
                switch (item.getItemId()) {

                    // Open HomeActivity
                    case R.id.ic_home_nav:
                        Intent i1 = new Intent(DisplayOpportunityActivity.this, HomeActivity.class);
                        startActivity(i1);
                        break;

                    // Open OpportunitiesListActivity
                    case R.id.ic_search_nav:
                        Intent i2 = new Intent(DisplayOpportunityActivity.this, OpportunitiesListActivity.class);
                        startActivity(i2);
                        break;

                    // Open ProfileActivity
                    case R.id.ic_profile_nav:
                        Intent i3 = new Intent(DisplayOpportunityActivity.this, ProfileActivity.class);
                        startActivity(i3);
                        break;
                }
                // Return false if not selected
                return false;
            }
        });

    }

}
