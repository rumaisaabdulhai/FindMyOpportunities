package com.afg.helpout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

/**
 * The DisplayOpportunityActivity Class
 *
 * Displays a Specific Opportunity Passed From OpportunityListActivity.
 *
 * Processes data from OpportunityListActivity.
 * Displays the Opportunity's name, description, contact info, etc.
 *
 * Allows for opportunities to be favorite.
 */
public class DisplayOpportunityActivity extends AppCompatActivity {

    // TAG for logging
    private static final String TAG = "DisplayOppActivity";

    // Index of the Activity for the BottomNavigationView
    public static final int INDEX = 1;

    // Custom Toolbar
    Toolbar toolbar;

    // Firebase Variables
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference users_ref;

    String ID;
    String email;

    // User object that holds the current user's information
    User currentUser;

    // The opportunity being displayed
    Opportunity opportunity;

    /**
     * This is the onCreate Method for DisplayOpportunityActivity.
     * <p>
     * When called, it processes data from the OpportunityListActivity
     * and displays the appropriate information for the Opportunity that was
     * clicked in the RecyclerView.
     * <p>
     * It also handles clicks for the BottomNavigationView.
     *
     * @param savedInstanceState State of the UI Controller.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_opportunity);

        // If the Intent has a message called "Opportunity"
        if (getIntent().hasExtra("Opportunity")) {
            // Passes in object from OpportunityListActivity
            opportunity = getIntent().getParcelableExtra("Opportunity");
            Log.d(TAG, "onCreate: " + opportunity.getTitle());

            // Get Title Reference and Set Proper Title
            TextView title = findViewById(R.id.titleText);
            title.setText(opportunity.getTitle());

            // Get Description Reference and Set Proper Description
            TextView description = findViewById(R.id.descriptionText);
            description.setText(opportunity.getDescription());

            // Get Organizer Reference and Set Proper Organizer
            TextView organizer = findViewById(R.id.organizerText);
            organizer.setText(opportunity.getOrganizer());

            // Get Location Reference and Set Proper Location
            TextView location = findViewById(R.id.locationText);
            location.setText(opportunity.getLocation());
        }

        // Sets the Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get Instance of Auth
        mAuth = FirebaseAuth.getInstance();

        ID = mAuth.getCurrentUser().getUid();
        email = mAuth.getCurrentUser().getEmail();

        // Gets instance of the Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Reads from the "Users" child in the database
        users_ref = databaseReference.child("Users");

        // Initializes the Opportunity ArrayList
        currentUser = new User();

        readUserData(new DisplayOpportunityActivity.FirebaseCallback() {
            /**
             * Called after reading the userdata
             * to save the user object and make it accessible within
             * the class. Sets the Username Text View
             * @param user The User object
             */
            @Override
            public void onCallback(User user) {
                currentUser = user;
            }
        });

        // BOTTOM NAVIGATION MENU
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(INDEX);
        menuItem.setChecked(true);  // Changes Icon Color of Current Activity Selected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            /**
             * @param item The Item that is selected from the BottomNavigationMenu
             * @return true if the item is selected, false otherwise
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Switch cases on the ID of the Item
                switch (item.getItemId()) {

                    // Open HomeActivity
                    case R.id.ic_home_nav:
                        Intent i1 = new Intent(DisplayOpportunityActivity.this, HomeActivity.class);
                        startActivity(i1);
                        break;

                    // Open OpportunitiesListActivity
                    case R.id.ic_search_nav:
                        Intent i2 = new Intent(DisplayOpportunityActivity.this, OpportunitiesListActivity.class);
                        startActivity(i2);
                        break;

                    // Open ProfileActivity
                    case R.id.ic_profile_nav:
                        Intent i3 = new Intent(DisplayOpportunityActivity.this, ProfileActivity.class);
                        startActivity(i3);
                        break;
                }
                // Return false if not selected
                return false;
            }
        });

    }

    /**
     * Inflates the Toolbar Menu for
     * the DisplayOpportunityActivity.
     *
     * @param menu The Menu Object
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opportunity_toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_favorite);

        // If the opportunity has been marked favorite before,
        // make the favorite icon look selected
        if (markedFavorite()) {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_selected));
            Log.d(TAG, "Reloaded status of Opportunity: Was marked Favorite");
        }

        // If not marked favorite before,
        // make the favorite icon look unselected
        else {
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_unselected));
            Log.d(TAG, "Reloaded status of Opportunity: Was NOT marked Favorite");
        }

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
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {

            // If the favorite icon is clicked
            case R.id.action_favorite:

                Log.d(TAG, "Favorite Icon was clicked.");

                // If previously not marked favorite
                if (!markedFavorite()) {

                    Log.d(TAG, "Not marked Favorite: " + opportunity.getTitle());

                    // Change appearance of icon to look selected
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_selected));

                    Toast.makeText(DisplayOpportunityActivity.this, "Saved to favorites.", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Selected: " + opportunity.getTitle());

                    ArrayList<String> favorites = currentUser.getFavorites();

                    favorites.add(opportunity.getID());
                    currentUser.setFavorites(favorites);

                    // Append to Firebase
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(ID);
                    DatabaseReference favorites_ref = current_user_db.child("Favorites");
                    favorites_ref.push().setValue(opportunity.getID());

                    Log.d(TAG, "Appended to Firebase: " + opportunity.getTitle());

                }

                // If previously marked favorite
                else {

                    Log.d(TAG, "Marked Favorite: " + opportunity.getTitle());

                    // Change appearance of icon to look unselected
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_unselected));

                    Toast.makeText(DisplayOpportunityActivity.this, "Removed from favorites.", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "Unselected: " + opportunity.getTitle());

                    ValueEventListener valueEventListener = new ValueEventListener() {
                        /**
                         * When called, removes the opportunity from favorites.
                         *
                         * @param dataSnapshot The DataSnapshot of the Users information
                         */
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            // Get reference for the favorites folder
                            DataSnapshot favorites_ref = dataSnapshot.child(ID).child("Favorites");

                            // Loop through each child in the favorites
                            for (DataSnapshot id_favorite : favorites_ref.getChildren()) {
                                if (id_favorite.getValue(String.class).equals(opportunity.getID())) {

                                    ArrayList<String> favorites = currentUser.getFavorites();
                                    favorites.remove(opportunity.getID());
                                    currentUser.setFavorites(favorites);

                                    id_favorite.getRef().removeValue();
                                    Log.d(TAG, "Removed from Firebase: " + opportunity.getTitle());
                                }
                            }
                        }

                        /**
                         * Called when an error occurs.
                         *
                         * @param databaseError The DatabaseError
                         */
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, databaseError.getMessage());
                            Toast.makeText(DisplayOpportunityActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                        }
                    };
                    users_ref.addListenerForSingleValueEvent(valueEventListener);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Method for reading the User's Data from Firebase.
     *
     * @param firebaseCallback The FirebaseCallback interface
     */
    private void readUserData(final DisplayOpportunityActivity.FirebaseCallback firebaseCallback) {

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

                String name = dataSnapshot.child(ID).child("Name").getValue(String.class);
                String username = dataSnapshot.child(ID).child("Username").getValue(String.class);

                // Initialize a favorites list
                ArrayList<String> id_favorites = new ArrayList<>();

                // If a favorites folder has been created
                if (dataSnapshot.child(ID).child("Favorites").exists()) {

                    // Get reference for the favorites folder
                    DataSnapshot favorites_ref = dataSnapshot.child(ID).child("Favorites");

                    // Loop through each child in the favorites
                    for (DataSnapshot id_favorite : favorites_ref.getChildren()) {

                        // Append ID of favorite opportunities to the ArrayList of IDs
                        id_favorites.add(id_favorite.getValue(String.class));

                    }
                }

                // Create a new user
                User user = new User(name, username, email, ID, id_favorites);
                firebaseCallback.onCallback(user);
            }

            /**
             * Called when an error occurs.
             *
             * @param databaseError The DatabaseError
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
                Toast.makeText(DisplayOpportunityActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        };
        users_ref.addValueEventListener(valueEventListener);
    }

    /**
     * Checks if the ID of the opportunity displayed
     * has already been marked favorite.
     *
     * @return
     */
    private boolean markedFavorite() {

        // The opportunity is initially assumed
        // to not be marked as favorite
        boolean isFavorite = false;

        // For each ID in the list of favorite opportunity IDs
        for (String id : currentUser.getFavorites()) {

            // If the ID of the displayed opportunity equals
            // an ID existing in the user's favorites
            if (opportunity.getID().equals(id)) {
                isFavorite = true;
            }
        }

        return isFavorite;
    }

    /**
     * FirebaseCallback Interface
     */
    private interface FirebaseCallback {
        void onCallback(User user);
    }

}