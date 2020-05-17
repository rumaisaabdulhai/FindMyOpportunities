package com.afg.helpout;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * The FavoritesFragment Class
 *
 * Displays the Favorited Items
 * from the User.
 */
public class FavoritesFragment extends Fragment implements RecyclerAdapter.OnOpportunityListener {

    // TAG for logging
    private static final String TAG = "FavoritesFragment";

    // Firebase Variables
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference users_ref;
    DatabaseReference opportunities_ref;

    // User object that holds the current user's information
    User currentUser;

    ArrayList<Opportunity> opportunities;

    // Information about the specific user
    String ID;
    String email;

    // RecyclerView Variables
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerview;
    RecyclerAdapter recyclerAdapter;

    /**
     * Required empty public constructor
     */
    public FavoritesFragment() { }

    /**
     * Similar to onCreate where the layout is initialized.
     * The method for reading the data is called here.
     *
     * @param inflater The LayoutInflater that makes the layout in the HomeActivity
     * @param container The ViewGroup that holds the layout
     * @param savedInstanceState The Bundle that holds the saved state of the layout
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        // Get Instance of Auth
        mAuth = FirebaseAuth.getInstance();

        // Gets main user information
        ID = mAuth.getCurrentUser().getUid();
        email = mAuth.getCurrentUser().getEmail();

        // Sets the RecyclerView in the Layout
        recyclerview = v.findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);

        // Gets instance of the Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Reads from the "Users" child in the database
        users_ref = databaseReference.child("Users");
        opportunities_ref = databaseReference.child("Opportunities");

        // Initializes the User object
        currentUser = new User();

        // Calls the method to read the user data
        readUserData(new FavoritesFragment.FirebaseCallback() {
            /**
             * Called after reading data to save the Opportunity ArrayList
             * and to set the RecyclerView.
             * @param opportunityArrayList The opportunities ArrayList
             */
            @Override
            public void onCallback(ArrayList<Opportunity> opportunityArrayList) {
                opportunities = opportunityArrayList;

                // Creates a new RecyclerAdapter with the Opportunity ArrayList
                recyclerAdapter = new RecyclerAdapter(opportunities, getActivity(), FavoritesFragment.this);
                recyclerview.setAdapter(recyclerAdapter);
            }

        });

        // Returns the View
        return v;
    }

    /**
     * Method for reading the User's Data from Firebase.
     *
     * @param firebaseCallback The FirebaseCallback interface
     */
    private void readUserData(final FavoritesFragment.FirebaseCallback firebaseCallback) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            /**
             * Listens for changes on the User's data from Firebase and creates
             * a new User. Passes the User object to the onCallback method where
             * it can be used by the whole class.
             *
             * @param dataSnapshot The DataSnapshot at an instance in time
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Initialize an opportunities list
                ArrayList<Opportunity> opportunityArrayList = new ArrayList<>();

                DataSnapshot users_ref = dataSnapshot.child("Users");
                DataSnapshot opportunities_ref = dataSnapshot.child("Opportunities");

                String name = users_ref.child(ID).child("Name").getValue(String.class);
                String username = users_ref.child(ID).child("Username").getValue(String.class);

                Log.d(TAG, "NAME: " + name);
                Log.d(TAG, "ID: " + ID);

                // Initialize a favorites list
                ArrayList<String> id_favorites = new ArrayList<>();

                // If a favorites folder has been created
                if (users_ref.child(ID).child("Favorites").exists()) {

                    // Get reference for the favorites folder
                    DataSnapshot favorites_ref = users_ref.child(ID).child("Favorites");

                    // Loop through each child in the favorites
                    for (DataSnapshot id_favorite: favorites_ref.getChildren()) {

                        String favorite_id = id_favorite.getValue(String.class);

                        // Append ID of favorite opportunities to the ArrayList of IDs
                        id_favorites.add(favorite_id);

                    }

                }

                // For each id in the IDs of favorite opportunities
                for (String id: id_favorites) {

                    // Get the DataSnapshot reference
                    DataSnapshot ds = opportunities_ref.child(id);

                    // Convert to String for storing
                    String ID  = ds.getKey();
                    String title = ds.child("Title").getValue(String.class);
                    String address = ds.child("Address").getValue(String.class);
                    String contact = ds.child("Contact").getValue(String.class);
                    String organizer = ds.child("Organized By").getValue(String.class);
                    String location = ds.child("Where").getValue(String.class);
                    String description = ds.child("Description").getValue(String.class);
                    String latitude = ds.child("Latitude").getValue(String.class);
                    String longitude = ds.child("Longitude").getValue(String.class);

                    // Default opportunity
                    Opportunity opportunity = new Opportunity();

                    // Checks for null
                    if (latitude==null)
                        latitude = "0";
                    if (longitude==null)
                        longitude = "0";

                    // Used setters and getters over constructor as
                    // not all opportunities will have these attributes.
                    opportunity.setID(ID);
                    opportunity.setTitle(title);
                    opportunity.setAddress(address);
                    opportunity.setContact(contact);
                    opportunity.setOrganizer(organizer);
                    opportunity.setDescription(description);
                    opportunity.setLocation(location);
                    opportunity.setLatitude(Double.parseDouble(latitude));
                    opportunity.setLongitude(Double.parseDouble(longitude));

                    // Adds the Opportunity to the ArrayList
                    opportunityArrayList.add(opportunity);
                }

                // Sends the opportunityArrayList to the onCallback method
                firebaseCallback.onCallback(opportunityArrayList);
            }

            /**
             * Called when an error occurs.
             *
             * @param databaseError The DatabaseError
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
                Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_SHORT).show();
            }
        };

        // Starts the valueEventListener, will keep checking from Firebase
        databaseReference.addValueEventListener(valueEventListener);
    }

    /**
     * Opens DisplayOpportunityActivity when clicked.
     *
     * @param index The Index of the Opportunity in the ArrayList.
     */
    public void onOpportunityClick(int index) {
        Intent intent = new Intent(getActivity(), DisplayOpportunityActivity.class);
        intent.putExtra("Opportunity", opportunities.get(index));
        startActivity(intent);
    }

    /**
     * FirebaseCallback Interface
     */
    private interface FirebaseCallback {
        void onCallback(ArrayList<Opportunity> opportunityArrayList);
    }

}