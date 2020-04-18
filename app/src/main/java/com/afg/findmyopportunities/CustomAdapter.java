package com.afg.findmyopportunities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<Opportunity> opportunities;
    Context context;

    public CustomAdapter(ArrayList<Opportunity> opportunities, Context context) {
        this.opportunities = opportunities;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView Title;
        TextView Description;

        MyViewHolder(View itemview)
        {
            super(itemview);
            this.Title = (TextView) itemview.findViewById(R.id.Title);
            this.Description = (TextView) itemview.findViewById(R.id.Description);
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.opportunity, parent, false);
        MyViewHolder myViewHolder= new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView title = holder.Title;
        TextView description = holder.Description;

        // gets title and description from the opportunities ArrayList to display
        title.setText( opportunities.get(position).getTitle() );
        description.setText( opportunities.get(position).getDescription() );
    }

    @Override
    public int getItemCount() {
        return opportunities.size();
    }
}