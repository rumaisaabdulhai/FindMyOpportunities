package com.afg.helpout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btnLogout;
    FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RUN START ACTIVITY ONCE AFTER NEW INSTALL
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun) {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
            Intent intent = new Intent(this, WalkThroughActivity.class);
            startActivity(intent);
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

        // BOTTOM NAVIGATION MENU
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_home:
                        break;

                    case R.id.ic_search:
                        Intent i2 = new Intent(MainActivity.this, OpportunitiesListActivity.class);
                        startActivity(i2);
                        break;

                    case R.id.ic_profile:
                        Intent i3 = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(i3);
                        break;
                }
                return false;
            }
        });

    }
}