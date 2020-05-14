package com.afg.helpout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * The MainActivity Class
 *
 * Displays the Home Screen of HelpOut with a TabLayout
 * that alternates between two Fragments.
 *
 * Initiates the WalkThroughActivity if the User has opened the
 * app for the very first time after a fresh install.
 *
 * Welcomes the User who is signed in and notifies the
 * User of opportunities they have previously visited
 * (still in implementation) through the Dashboard Fragment.
 *
 * Displays the opportunities that the User has marked
 * as favorite through the Favorites Fragment.
 *
 */
public class MainActivity extends AppCompatActivity {

    // TAG for logging
    private static final String TAG = "MainActivity";

    // Index of the Activity for the BottomNavigationView
    public static final int INDEX = 0;

    // Custom Toolbar
    Toolbar toolbar;

    // For allowing sliding between Fragments
    private ViewPager viewPager;

    // For holding the different Fragments
    private TabLayout tabLayout;

    // Fragment Variables
    private DashboardFragment dashboardFragment;
    private FavoritesFragment favoritesFragment;

    /**
     * This is the onCreate Method for MainActivity.
     *
     * When called, it first checks whether the app has been opened at all.
     * If the app has not been opened, it opens the WalkThroughActivity which
     * welcomes the user and guides them to register or sign in. If the app has
     * been opened, it continues to display the MainActivity Layout which contains
     * the Dashboard and Fragment Layouts.
     *
     * It also handles clicks for the BottomNavigationView.
     *
     * @param savedInstanceState State of the UI Controller.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RUN ACTIVITY ONCE AFTER FRESH INSTALL
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun) {
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
            Intent intent = new Intent(this, WalkThroughActivity.class);
            startActivity(intent);
        }

        // Gets viewPager and tabLayout References
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // Creates the new Fragments
        dashboardFragment = new DashboardFragment();
        favoritesFragment = new FavoritesFragment();

        // Allows Sliding Functionality in the TabLayout
        tabLayout.setupWithViewPager(viewPager);

        // Creates a new ViewPagerAdapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        // Add Fragments to viewPagerAdapter and set the adapter for viewPager
        viewPagerAdapter.addFragment(dashboardFragment, "Dashboard");
        viewPagerAdapter.addFragment(favoritesFragment, "Favorites");
        viewPager.setAdapter(viewPagerAdapter);

        // Sets the Icons to be Displayed in each TabLayout Title Area
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_dashboard);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_favorites);

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

                    case R.id.ic_home_nav:
                        break;

                    // Open MainActivity
                    case R.id.ic_search_nav:
                        Intent i2 = new Intent(MainActivity.this, OpportunitiesListActivity.class);
                        startActivity(i2);
                        break;

                    // Open ProfileActivity
                    case R.id.ic_profile_nav:
                        Intent i3 = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(i3);
                        break;
                }
                // Return false if not selected
                return false;
            }
        });

    }

    /**
     * The ViewPagerAdapter that allows information from the Fragments to be Displayed.
     */
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        // Initializes Lists of Fragments and their Titles
        private List <Fragment> fragments = new ArrayList<>();
        private List <String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        /**
         * @param fragment The Fragment Object
         * @param title The title of the Fragment
         */
        public void addFragment(Fragment fragment, String title) {
            // Adds Fragments and Titles to the Two Lists
            fragments.add(fragment);
            fragmentTitles.add(title);
        }

        /**
         * @param position The Index of the Item
         * @return The Fragment object
         */
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        /**
         * @return The number of Pages in the ViewPagerAdapter
         */
        @Override
        public int getCount() {
            return fragments.size();
        }


        /**
         * @param position The Index of the Page
         * @return The Title of the Fragment as a CharSequence
         */
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}