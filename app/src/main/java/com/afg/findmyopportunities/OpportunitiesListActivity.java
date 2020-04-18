package com.afg.findmyopportunities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OpportunitiesListActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerview;

    // ArrayList of opportunities
    ArrayList<Opportunity> opportunities;

    DatabaseReference database;
    DatabaseReference opportunities_ref;
    CustomAdapter customAdapter;

    // Tag associated with class
    private static final String TAG = "OppListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunities_list);
        recyclerview = (RecyclerView)findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance().getReference();
        opportunities_ref = database.child("");
        opportunities = new ArrayList<>();

        readData (new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Opportunity> opportunities) {

                for (Opportunity opportunity: opportunities) {
                    Log.d(TAG, "\nTitle: " + opportunity.getTitle() +
                                    "\nDescription: " + opportunity.getDescription());
                }

                customAdapter = new CustomAdapter(opportunities, OpportunitiesListActivity.this);
                recyclerview.setAdapter(customAdapter);
            }
        });
    }

    private void readData(final FirebaseCallback firebaseCallback) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String title = ds.child("Title").getValue(String.class);
                    String description = ds.child("Description").getValue(String.class);

                    Opportunity x = new Opportunity();
                    x.setTitle(title);
                    x.setDescription(description);
                    opportunities.add(x);
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

    private interface FirebaseCallback {
        void onCallback(ArrayList<Opportunity> opportunities);
    }

}