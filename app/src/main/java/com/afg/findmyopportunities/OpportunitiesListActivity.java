package com.afg.findmyopportunities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OpportunitiesListActivity extends AppCompatActivity implements RecyclerAdapter.OnOpportunityListener, SearchView.OnQueryTextListener, Comparator<Opportunity> {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerview;

    // ArrayList of opportunities
    ArrayList<Opportunity> opportunities;

    DatabaseReference database;
    DatabaseReference opportunities_ref;
    RecyclerAdapter recyclerAdapter;

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

                    case R.id.ic_home:
                        Intent i1 = new Intent(OpportunitiesListActivity.this, MainActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.ic_search:
                        break;

                    case R.id.ic_profile:
                        Intent i3 = new Intent(OpportunitiesListActivity.this, ProfileActivity.class);
                        startActivity(i3);
                        break;
                }
                return false;
            }
        });

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
    public void sortViewByName(View view){
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
        return o1.getTitle().compareTo(o2.getTitle());
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    // Show matching opportunities based on query (while searching)
    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        ArrayList<Opportunity> newList = new ArrayList<>();

        for (Opportunity opportunity: opportunities)
        {
            if (opportunity.getTitle().toLowerCase().contains(userInput) | opportunity.getDescription().toLowerCase().contains(userInput))
            {
               newList.add(opportunity);
            }
        }

        recyclerAdapter.updateList(newList);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item2:
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Sub Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}