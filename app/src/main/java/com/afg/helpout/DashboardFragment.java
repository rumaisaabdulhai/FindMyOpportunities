package com.afg.helpout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    // Firebase Variables
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference users_ref;

    // User object that holds the current user's information
    User currentUser;

    TextView welcomeUser;

    String ID;
    String email;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        welcomeUser = (TextView) v.findViewById(R.id.userName);

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

        readUserData(new DashboardFragment.FirebaseCallback() {
            @Override
            public void onCallback(User user) {
                currentUser = user;
                welcomeUser.setText(currentUser.getName());
            }
        });

        return v;

    }

    private void readUserData(final DashboardFragment.FirebaseCallback firebaseCallback) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child(ID).child("Name").getValue(String.class);
                String username = dataSnapshot.child(ID).child("Username").getValue(String.class);
                Log.d("DashFragment", "Name " + name);

                User user = new User(name, username, email, ID, new ArrayList<Integer>());
                firebaseCallback.onCallback(user);
            }

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
