package com.afg.helpout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OpportunitiesListActivity extends AppCompatActivity implements RecyclerAdapter.OnOpportunityListener, Comparator<Opportunity>, SearchView.OnQueryTextListener {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerview;

    // ArrayList of opportunities
    ArrayList<Opportunity> opportunities;

    DatabaseReference database;
    DatabaseReference opportunities_ref;
    RecyclerAdapter recyclerAdapter;

    Toolbar toolbar;

    private static final String TAG = "OppListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunities_list);

        // BOTTOM NAVIGATION MENU
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_home_nav:
                        Intent i1 = new Intent(OpportunitiesListActivity.this, MainActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.ic_search_nav:
                        break;

                    case R.id.ic_profile_nav:
                        Intent i3 = new Intent(OpportunitiesListActivity.this, ProfileActivity.class);
                        startActivity(i3);
                        break;
                }
                return false;
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerview = findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();
        opportunities_ref = database.child("");
        opportunities = new ArrayList<>();

        readData (new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Opportunity> opportunityArrayList) {

                for (Opportunity opportunity: opportunityArrayList) {
                    Log.d(TAG, "\nTitle: " + opportunity.getTitle() +
                                    "\nDescription: " + opportunity.getDescription());
                }

                opportunities = opportunityArrayList;
                recyclerAdapter = new RecyclerAdapter(opportunities, OpportunitiesListActivity.this, OpportunitiesListActivity.this);
                recyclerview.setAdapter(recyclerAdapter);
            }
        });

    }

    //    Sort Opportunities Alphabetically
    public void sortViewByName(View view) {
       Collections.sort(opportunities, new OpportunitiesListActivity());
        recyclerAdapter = new RecyclerAdapter(opportunities, OpportunitiesListActivity.this, OpportunitiesListActivity.this);
        recyclerview.setAdapter(recyclerAdapter);
    }

    // Read Data from Firebase
    private void readData(final FirebaseCallback firebaseCallback) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String ID  = ds.getKey();
                    String title = ds.child("Title").getValue(String.class);
//                    GenericTypeIndicator<ArrayList<String>> x = new GenericTypeIndicator<ArrayList<String>>() {};
//                    ArrayList<String> address = ds.child("Address").getValue(x);
//                    ArrayList<String> contact = ds.child("Contact").getValue(x);
                    String organizer = ds.child("Organized By").getValue(String.class);
                    String location = ds.child("Where").getValue(String.class);
                    String description = ds.child("Description").getValue(String.class);

                    Opportunity opportunity = new Opportunity( ID, title, organizer, location, description );
                    opportunities.add(opportunity);
                }

                firebaseCallback.onCallback(opportunities);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d( TAG, databaseError.getMessage() );

            }
        };

        opportunities_ref.addValueEventListener(valueEventListener);
    }

    //    Open Opportunity Activity on click
    @Override
    public void onOpportunityClick(int position) {
        Intent intent = new Intent(this, DisplayOpportunityActivity.class);
        intent.putExtra("Opportunity", opportunities.get(position));
        startActivity(intent);
    }

    //    Compares two Opportunities by title
    @Override
    public int compare(Opportunity o1, Opportunity o2) {
        try { return o1.getTitle().compareTo(o2.getTitle()); }
        catch (NullPointerException ignored) { }
        return 0;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        ArrayList<Opportunity> newList = new ArrayList<>();

        try {
            for (Opportunity opportunity: opportunities)
            {
                String title = opportunity.getTitle().toLowerCase();
                String description = opportunity.getDescription().toLowerCase();
                if (title.contains(userInput) | description.contains(userInput))
                    newList.add(opportunity);
            }
        }
        catch (NullPointerException ignored) { }

        recyclerAdapter.updateList(newList);
        return true;
    }

    private interface FirebaseCallback {
        void onCallback(ArrayList<Opportunity> opportunities);
    }

    //    Search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getResources().getString(R.string.searchHint));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortByLocation:
            case R.id.action_search:
                return true;
            case R.id.sortByName:
                sortViewByName(findViewById(android.R.id.content).getRootView());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}