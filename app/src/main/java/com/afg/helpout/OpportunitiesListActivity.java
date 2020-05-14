package com.afg.helpout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afg.helpout.mapObjects.PlaceData;
import com.afg.helpout.mapObjects.Tasks.MapQuestAPITask;
import com.afg.helpout.mapObjects.Tasks.MapQuestHelper;
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

/**
 * The OpportunitiesListActivity Class
 *
 * Displays the Opportunities from Firebase in the RecyclerView.
 *
 * Reads information from Firebase,
 * Handles Searches with the Search Bar,
 * Handles Clicks on Each Opportunity
 * Implements Sorting by Name and Location
 */
public class OpportunitiesListActivity extends AppCompatActivity implements RecyclerAdapter.OnOpportunityListener,
                                                                            Comparator<Opportunity>,
                                                                            SearchView.OnQueryTextListener,
                                                                            LocationDialog.LocationDialogListener {

    // TAG for logging
    private static final String TAG = "OppListActivity";

    // Index of the Activity for the BottomNavigationView
    public static final int INDEX = 1;

    // Custom Toolbar
    Toolbar toolbar;

    // RecyclerView Variables
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerview;
    RecyclerAdapter recyclerAdapter;

    // Firebase Variables
    DatabaseReference database;
    DatabaseReference opportunities_ref;

    // ArrayList that holds the opportunities
    ArrayList<Opportunity> opportunities;

    // User Information
    String userAddress = "";
    PlaceData place;

    /**
     * This is the onCreate Method for OpportunitiesListActivity.
     *
     * When called, it sets the RecyclerView,
     * Reads data from Firebase, and
     * Displays the title and description of
     * each opportunity in a list format.
     *
     * It also handles clicks for the BottomNavigationView.
     *
     * @param savedInstanceState State of the UI Controller.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunities_list);

        // Sets the Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Sets the RecyclerView in the Layout
        recyclerview = findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        // Gets instance of the Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        // Reads from the "Opportunities" child in the database
        opportunities_ref = database.child("Opportunities");

        // Initializes the Opportunity ArrayList
        opportunities = new ArrayList<>();

        // Read Data from the Database
        readData (new FirebaseCallback() {
            /**
             * Callback Method implementation of the FirebaseCallback interface.
             *
             * Populates the Opportunity ArrayList and displays the Opportunities by
             * creating and setting the RecyclerAdapter.
             *
             * @param opportunityArrayList The Opportunity ArrayList passed in from the readData method
             */
            @Override
            public void onCallback(ArrayList<Opportunity> opportunityArrayList) {

                for (Opportunity opportunity: opportunityArrayList) {
                    Log.d(TAG, "onCallback:" +
                                    "\nTitle: " + opportunity.getTitle() +
                                    "\nDescription: " + opportunity.getDescription());
                }

                // Populates the opportunityArrayList
                opportunities = opportunityArrayList;

                // Creates a new RecyclerAdapter with the Opportunity ArrayList
                recyclerAdapter = new RecyclerAdapter(opportunities, OpportunitiesListActivity.this, OpportunitiesListActivity.this);
                recyclerview.setAdapter(recyclerAdapter);
            }
        });

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

    }

    // Read Data from Firebase
    // Load the first 10 opportunities and run a lightweight async to keep uploading
    private void readData(final FirebaseCallback firebaseCallback) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String ID  = ds.getKey();
                    String title = ds.child("Title").getValue(String.class);
                    String address = ds.child("Address").getValue(String.class);
                    String contact = ds.child("Contact").getValue(String.class);
                    String organizer = ds.child("Organized By").getValue(String.class);
                    String location = ds.child("Where").getValue(String.class);
                    String description = ds.child("Description").getValue(String.class);
                    String latitude = ds.child("latitude").getValue(String.class);
                    String longitude = ds.child("longitude").getValue(String.class);

                    Opportunity opportunity = new Opportunity(ID, title, address, contact, organizer, location,
                            description, Double.parseDouble(latitude), Double.parseDouble(longitude));
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

    @Override
    public int compare(Opportunity o1, Opportunity o2) {
        return 0;
    }

    private interface FirebaseCallback {
        void onCallback(ArrayList<Opportunity> opportunities);
    }

    //Search
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
                sortViewByLocation(findViewById(android.R.id.content).getRootView());
            case R.id.action_search:
                return true;
            case R.id.sortByName:
                sortViewByName(findViewById(android.R.id.content).getRootView());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    //BELOW METHODS ARE USED FOR SORTING THE RECYCLER VIEW

    //    Sort Opportunities Alphabetically
    public void sortViewByName(View view) {
        Collections.sort(opportunities, new TitleSorter());
        recyclerAdapter = new RecyclerAdapter(opportunities, OpportunitiesListActivity.this, OpportunitiesListActivity.this);
        recyclerview.setAdapter(recyclerAdapter);
    }


    // Use the information from the dialog to sort the RecyclerView by distance.
    @Override
    public void applyTexts(String town, String state) {
        Log.v("MyActivity", "Town and state: " + town + " " + state);
        userAddress = MapQuestHelper.formatAddress(town + ","+state);
        userAddress = userAddress.replaceAll(", ", ",");
        userAddress = userAddress.replaceAll(" ,", ",");
        userAddress = userAddress.replaceAll(" ", "+");
        try {
            MapQuestAPITask mpTask = new MapQuestAPITask(this);

            place = mpTask.execute(userAddress).get();
            for(Opportunity o: opportunities){
                double lat1 = place.getLatitude(); double long1 = place.getLongitude();
                double lat2 = o.getLatitude(); double long2 = o.getLongitude();
                o.setDistance(PlaceData.distance(lat1, long1, lat2, long2));
            }

            Collections.sort(opportunities, new DistanceSorter());
            recyclerAdapter = new RecyclerAdapter(opportunities, OpportunitiesListActivity.this, OpportunitiesListActivity.this);
            recyclerview.setAdapter(recyclerAdapter);
        } catch (Exception e) {
            Log.v("MyActivity", "Setting distances error" + e.toString());
        }
    }


    public void sortViewByLocation(View view) {
        openDialog();
        Collections.sort(opportunities, new DistanceSorter());
        recyclerAdapter = new RecyclerAdapter(opportunities, OpportunitiesListActivity.this, OpportunitiesListActivity.this);
        recyclerview.setAdapter(recyclerAdapter);
    }


    public void openDialog(){
        LocationDialog locationDialog = new LocationDialog();
        locationDialog.show(getSupportFragmentManager(), "Location Dialog");

    }

}