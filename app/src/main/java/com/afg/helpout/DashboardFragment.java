package com.afg.helpout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * The DashboardFragment Class
 *
 * Welcomes the user and remembers their name.
 * In the future, this page will display the opportunities
 * that the user has recently viewed. This feature will
 * most likely be implemented with SharedPreferences.
 */
public class DashboardFragment extends Fragment {

    // TAG for logging
    private static final String TAG = "DashboardFragment";

    // Firebase Variables
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference users_ref;

    // User object that holds the current user's information
    User currentUser;

    // View that will hold the name of the user
    TextView UserName;

    // Information about the specific user
    String ID;
    String email;

    // Required empty public constructor
    public DashboardFragment() { }

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
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Gets Reference of the View
        UserName = (TextView) v.findViewById(R.id.userName);

        // Get Instance of Auth
        mAuth = FirebaseAuth.getInstance();

        // Gets main user information
        ID = mAuth.getCurrentUser().getUid();
        email = mAuth.getCurrentUser().getEmail();

        // Gets instance of the Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Reads from the "Users" child in the database
        users_ref = databaseReference.child("Users");

        // Initializes the User object
        currentUser = new User();

        // Calls the method to read the user data
        readUserData(new DashboardFragment.FirebaseCallback() {
            /**
             * Called after reading the userdata
             * to save the user object and make it accessible within
             * the class. Sets the Username Text View
             * @param user The User object
             */
            @Override
            public void onCallback(User user) {

                // Assigns the reference of the passed in
                // variable to the default User object.
                currentUser = user;

                // Sets the Username TextView to make the
                // dashboard seem more personalized.
                UserName.setText(currentUser.getName());
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
    private void readUserData(final DashboardFragment.FirebaseCallback firebaseCallback) {

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

                        String favorite_id = id_favorite.getValue(String.class);

                        // Append ID of favorite opportunities to the ArrayList of IDs
                        id_favorites.add(favorite_id);

                    }
                }

                // Create a new user
                User user = new User(name, username, email, ID, id_favorites);
                firebaseCallback.onCallback(user);
            }

            // If error occurs
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d( "DashFragment", databaseError.getMessage() );
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
