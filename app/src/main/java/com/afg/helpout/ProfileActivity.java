package com.afg.helpout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * The ProfileActivity Class
 *
 * Displays the Profile for the User (to be implemented).
 *
 */
public class ProfileActivity extends AppCompatActivity {

    // TAG for logging
    private static final String TAG = "ProfileActivity";

    // Index of the Activity for the BottomNavigationView
    public static final int INDEX = 2;

    // Custom Toolbar
    Toolbar toolbar;

    /**
     * This is the onCreate Method for ProfileActivity.
     *
     * When called, it displays the Profile Information for the User that
     * is Logged in (to be implemented).
     *
     * It also handles clicks for the BottomNavigationView.
     *
     * @param savedInstanceState State of the UI Controller.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Sets the Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // BOTTOM NAVIGATION MENU
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(INDEX);
        menuItem.setChecked(true); // Changes Icon Color of Current Activity Selected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            /**
             * @param item The Item that is selected from the BottomNavigationMenu
             * @return true if the item is selected, false otherwise
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    // Open MainActivity
                    case R.id.ic_home_nav:
                        Intent i1 = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(i1);
                        break;

                    // Open OpportunitiesListActivity
                    case R.id.ic_search_nav:
                        Intent i2 = new Intent(ProfileActivity.this, OpportunitiesListActivity.class);
                        startActivity(i2);
                        break;

                    case R.id.ic_profile_nav:
                        break;
                }
                // Return false if not selected
                return false;
            }
        });

    }
}

//        btnLogout = findViewById(R.id.signOut);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });