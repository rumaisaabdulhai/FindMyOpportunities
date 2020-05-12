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

public class MainActivity extends AppCompatActivity {

    //  Variables
    Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private DashboardFragment dashboardFragment;
    private FavoritesFragment favoritesFragment;

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

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        dashboardFragment = new DashboardFragment();
        favoritesFragment = new FavoritesFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(dashboardFragment, "Dashboard");
        viewPagerAdapter.addFragment(favoritesFragment, "Favorites");
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_dashboard);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_favorites);

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

                    case R.id.ic_home_nav:
                        break;

                    case R.id.ic_search_nav:
                        Intent i2 = new Intent(MainActivity.this, OpportunitiesListActivity.class);
                        startActivity(i2);
                        break;

                    case R.id.ic_profile_nav:
                        Intent i3 = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(i3);
                        break;
                }
                return false;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List <Fragment> fragments = new ArrayList<>();
        private List <String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {

            fragments.add(fragment);
            fragmentTitles.add(title);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}