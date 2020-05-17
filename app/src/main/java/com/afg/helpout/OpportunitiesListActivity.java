package com.afg.helpout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afg.helpout.mapObjects.PlaceData;
import com.afg.helpout.mapObjects.Tasks.MapQuestAPITask;
import com.afg.helpout.mapObjects.Tasks.MapQuestHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private SwipeRefreshLayout mRefreshLayout;

    // Firebase Variables
    DatabaseReference databaseReference;
    DatabaseReference opportunitiesRef;

    // ArrayList that holds the opportunities
    ArrayList<Opportunity> opportunities;

    // User Information
    String userAddress = "";
    PlaceData place;
    private static int TOTAL_ITEMS_TO_LOAD = 7;
    private int mCurrentPage = 1;

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
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.opportunitiesRefreshLayout);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        // Gets instance of the Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Reads from the "Opportunities" child in the database
        //opportunities_ref = databaseReference.child("Opportunities");

        opportunitiesRef = databaseReference.child("Opportunities");

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
             * @param opportunityArrayList The Opportunity ArrayList passed in from the readData method.
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

                    // Open HomeActivity
                    case R.id.ic_home_nav:
                        Intent i1 = new Intent(OpportunitiesListActivity.this, HomeActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.ic_search_nav:
                        break;

                    // Open ProfileActivity
                    case R.id.ic_profile_nav:
                        Intent i3 = new Intent(OpportunitiesListActivity.this, ProfileActivity.class);
                        startActivity(i3);
                        break;
                }
                // Return false if not selected
                return false;
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage++;
                opportunities.clear();

                readData (new FirebaseCallback() {
                    /**
                     * Callback Method implementation of the FirebaseCallback interface.
                     *
                     * Populates the Opportunity ArrayList and displays the Opportunities by
                     * creating and setting the RecyclerAdapter.
                     *
                     * @param opportunityArrayList The Opportunity ArrayList passed in from the readData method.
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

            }
        });

    }

    /**
     * Reads Data from Firebase and passes the created
     * Opportunity ArrayList to the onCallback Method.
     *
     * @param firebaseCallback The FirebaseCallback
     */
    private void readData(final FirebaseCallback firebaseCallback) {
        Query opportunities_ref_query = opportunitiesRef.limitToFirst(mCurrentPage * TOTAL_ITEMS_TO_LOAD);
        ValueEventListener valueEventListener = new ValueEventListener() {
            /**
             * Reads Data from Firebase and adds to an ArrayList of
             * Opportunities
             *
             * @param dataSnapshot The DataSnapshot
             */
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
                    String latitude = ds.child("Latitude").getValue(String.class);
                    String longitude = ds.child("Longitude").getValue(String.class);

                    if(latitude==null || latitude.equals("")) {
                        latitude = "0";
                    }
                    if(longitude==null || longitude.equals("")) {
                        longitude = "0";
                    }

                    Opportunity opportunity = new Opportunity();
                    if(latitude==null)
                        latitude = "0";
                    if(longitude==null)
                        longitude = "0";
                    opportunity.setID(ID);
                    opportunity.setTitle(title);
                    opportunity.setAddress(address);
                    opportunity.setContact(contact);
                    opportunity.setOrganizer(organizer);
                    opportunity.setDescription(description);
                    opportunity.setLocation(location);
                    opportunity.setLatitude(Double.parseDouble(latitude));
                    opportunity.setLongitude(Double.parseDouble(longitude));

                    opportunities.add(0, opportunity);

                }

                firebaseCallback.onCallback(opportunities);
                mRefreshLayout.setRefreshing(false);

            }

            /**
             * If there is an error when reading data.
             *
             * @param databaseError The DatabaseError
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d( TAG, databaseError.getMessage() );Log.d(TAG, databaseError.getMessage());
                Toast.makeText(OpportunitiesListActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();

            }
        };

        opportunities_ref_query.addValueEventListener(valueEventListener);
    }

    /**
     * Opens DisplayOpportunityActivity when clicked.
     *
     * @param index The Index of the Opportunity in the ArrayList.
     */
    @Override
    public void onOpportunityClick(int index) {
        Intent intent = new Intent(this, DisplayOpportunityActivity.class);
        intent.putExtra("Opportunity", opportunities.get(index));
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * Filters and Updates the RecyclerView when User Searches.
     *
     * @param newText The user input
     * @return true
     */
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

    /**
     * For Implementing Compare Methods
     *
     * @param o1 The first Opportunity
     * @param o2 The second Opportunity
     * @return
     */
    @Override
    public int compare(Opportunity o1, Opportunity o2) { return 0; }

    /**
     * FirebaseCallback Interface
     */
    private interface FirebaseCallback {
        void onCallback(ArrayList<Opportunity> opportunities);
    }

    /**
     * Inflates the Toolbar Menu for
     * 1. The Overflow Menu containing the Sort By Items
     * 2. The Search Item
     *
     * @param menu The Menu Object
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getResources().getString(R.string.searchHint));
        return true;
    }

    /**
     * Handles Clicks on the Toolbar Menu.
     * Calls the Appropriate Sort Methods when Item is Clicked.
     *
     * @param item The chosen MenuItem.
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortByLocation:
                sortViewByLocation(findViewById(android.R.id.content).getRootView());
            case R.id.sortByName:
                sortViewByName(findViewById(android.R.id.content).getRootView());
                return true;
            case R.id.sortByOrganizer:
                sortViewByOrganizer(findViewById(android.R.id.content).getRootView());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Sorts Opportunities Alphabetically by Name
     * and Updates the RecyclerView.
     *
     * @param view The View object.
     */
    public void sortViewByName(View view) {
        Collections.sort(opportunities, new TitleSorter());
        recyclerAdapter = new RecyclerAdapter(opportunities, OpportunitiesListActivity.this, OpportunitiesListActivity.this);
        recyclerview.setAdapter(recyclerAdapter);
    }

    /**
     * Sorts Opportunities Alphabetically by Organizer
     * and Updates the RecyclerView.
     *
     * @param view The View object.
     */
    public void sortViewByOrganizer(View view) {
        Collections.sort(opportunities, new OrganizerSorter());
        recyclerAdapter = new RecyclerAdapter(opportunities, OpportunitiesListActivity.this, OpportunitiesListActivity.this);
        recyclerview.setAdapter(recyclerAdapter);
    }

    /**
     * Uses the information from the dialog to sort the RecyclerView by distance.
     *
     * @param town The desired Town input from the User.
     * @param state The desired State input from the User.
     */
    @Override
    public void applyTexts(String town, String state) {

        userAddress = MapQuestHelper.formatAddress(town + "," + state);
        userAddress = userAddress.replaceAll(", ", ",");
        userAddress = userAddress.replaceAll(" ,", ",");
        userAddress = userAddress.replaceAll(" ", "+");
        Log.d(TAG, "Town and state: " + userAddress);
        try {
            MapQuestAPITask mpTask = new MapQuestAPITask(this);

            place = mpTask.execute(userAddress).get();

            Log.d(TAG, "Got place");

            // For each opportunity, calculates distance by latitude and
            // longitude using the PlaceData Class.
            for (Opportunity o: opportunities) {

                double lat1 = place.getLatitude(); double long1 = place.getLongitude();
                double lat2 = o.getLatitude(); double long2 = o.getLongitude();
                o.setDistance(PlaceData.distance(lat1, long1, lat2, long2));

                Log.v(TAG, "OppDistances: " + o.getTitle());
                Log.v(TAG, "Distances: " + o.getDistance());
            }

            // Sorts by Distance
            Collections.sort(opportunities, new DistanceSorter());

            // Update the RecyclerView
            recyclerAdapter = new RecyclerAdapter(opportunities, OpportunitiesListActivity.this, OpportunitiesListActivity.this);
            recyclerview.setAdapter(recyclerAdapter);

        // Catches error
        } catch (Exception e) {
            Log.v(TAG, "Setting distances error" + e.toString());
        }
    }

    /**
     * Sorts Opportunities by Location
     * and Updates the RecyclerView.
     *
     * @param view the View object.
     */
    public void sortViewByLocation(View view) {
        Log.d(TAG, "Before the location dialogue.");
        openDialog();
        Collections.sort(opportunities, new DistanceSorter());
        recyclerAdapter = new RecyclerAdapter(opportunities, OpportunitiesListActivity.this, OpportunitiesListActivity.this);
        recyclerview.setAdapter(recyclerAdapter);
    }

    /**
     * Opens the Location Dialog.
     */
    public void openDialog() {
        LocationDialog locationDialog = new LocationDialog();
        locationDialog.show(getSupportFragmentManager(), "Location Dialog");
    }

}