package com.afg.findmyopportunities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class OpportunitiesListActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerview;

    ArrayList<Opportunity> opportunityArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunities_list);

        recyclerview= (RecyclerView)findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);

        prepare_news();
    }

    public void prepare_news()
    {
        opportunityArrayList = new ArrayList<>();

        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));

        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));

        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));

        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));

        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));

        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));
        opportunityArrayList.add(new Opportunity("cnn.com", "USA", "Coronavirus"));

        CustomAdapter customAdapter = new CustomAdapter(opportunityArrayList, OpportunitiesListActivity.this);
        recyclerview.setAdapter(customAdapter);
    }


}
