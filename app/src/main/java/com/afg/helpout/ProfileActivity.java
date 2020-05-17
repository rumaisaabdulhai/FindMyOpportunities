package com.afg.helpout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

/**
 * The ProfileActivity Class
 *
 * Displays the Profile for the User (to be implemented).
 *
 */
public class ProfileActivity extends AppCompatActivity {

    // TAG for logging
    private static final String TAG = "ProfileActivity";

    // Index of the Activity for the BottomNavigationView
    public static final int INDEX = 2;

    // Custom Toolbar
    Toolbar toolbar;

    String ID;
    String email;

    // Firebase Variables
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference users_ref;

    // User object that holds the current user's information
    User currentUser;

    // Initializing variables
    TextView nameTV;
    TextView emailTV;
    TextView usernameTV;

    Button signOutButton;

    /**
     * This is the onCreate Method for ProfileActivity.
     *
     * When called, it displays the Profile Information for the User that
     * is Logged in (to be implemented).
     *
     * It also handles clicks for the BottomNavigationView.
     *
     * @param savedInstanceState State of the UI Controller.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Sets the Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        signOutButton = (Button) findViewById(R.id.signOutButton);
        nameTV = (TextView) findViewById(R.id.name);
        emailTV = (TextView) findViewById (R.id.email);
        usernameTV = (TextView) findViewById (R.id.userName);

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

        readUserData(new FirebaseCallback() {
            /**
             * @param user
             */
            @Override
            public void onCallback(User user) {

                currentUser = user;

                nameTV.setText(currentUser.getName());
                emailTV.setText(currentUser.getEmail());
                usernameTV.setText(currentUser.getUsername());
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
                        Intent i1 = new Intent(ProfileActivity.this, HomeActivity.class);
                        startActivity(i1);
                        break;

                    // Open OpportunitiesListActivity
                    case R.id.ic_search_nav:
                        Intent i2 = new Intent(ProfileActivity.this, OpportunitiesListActivity.class);
                        startActivity(i2);
                        break;

                    case R.id.ic_profile_nav:
                        break;
                }
                // Return false if not selected
                return false;
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Listener for the Sign Out Button
             * @param view The View
             */
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * When initializing activity, checks to see if the user is currently signed in.
     */
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(ProfileActivity.this, SignInActivity.class));
        }
    }

    /**
     * Method for reading the User's Data from Firebase.
     *
     * @param firebaseCallback The FirebaseCallback interface
     */
    private void readUserData(final FirebaseCallback firebaseCallback) {

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
                    for (DataSnapshot id_favorite: favorites_ref.getChildren()) {

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
                Toast.makeText(ProfileActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
            }
        };

        users_ref.addValueEventListener(valueEventListener);

    }

    /**
     * FirebaseCallback Interface
     */
    private interface FirebaseCallback {
        void onCallback(User user);
    }
}