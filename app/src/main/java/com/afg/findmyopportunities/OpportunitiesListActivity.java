package com.afg.findmyopportunities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpportunitiesListActivity extends AppCompatActivity implements CustomAdapter.OnOpportunityListener {

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
                customAdapter = new CustomAdapter(opportunities, OpportunitiesListActivity.this, OpportunitiesListActivity.this);
                recyclerview.setAdapter(customAdapter);
            }
        });
    }

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
                    String organizer = ds.child("Organizer").getValue(String.class);
                    String location = ds.child("Location").getValue(String.class);
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

    @Override
    public void onOpportunityClick(int position) {
        Intent intent = new Intent(this, DisplayOpportunityActivity.class);
        intent.putExtra("Opportunity", opportunities.get(position));
        startActivity(intent);
    }

    private interface FirebaseCallback {
        void onCallback(ArrayList<Opportunity> opportunities);
    }

}