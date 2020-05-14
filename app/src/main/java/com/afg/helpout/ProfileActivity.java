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
 * Displays the Profile for the User (to be implemented)
 */
public class ProfileActivity extends AppCompatActivity {

    // TAG for logging
    private static final String TAG = "ProfileActivity";

    // Index of the Activity for the BottomNavigationView
    public static final int INDEX = 2;

    // Custom Toolbar
    Toolbar toolbar;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // BOTTOM NAVIGATION MENU
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(INDEX);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            /**
             * @param item
             * @return
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_home_nav:
                        Intent i1 = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.ic_search_nav:
                        Intent i2 = new Intent(ProfileActivity.this, OpportunitiesListActivity.class);
                        startActivity(i2);
                        break;

                    case R.id.ic_profile_nav:
                        break;
                }
                return false;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
