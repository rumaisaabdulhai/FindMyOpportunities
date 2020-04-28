package com.afg.findmyopportunities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerAdapter";
    private ArrayList<Opportunity> opportunities;
    private OnOpportunityListener onOpportunityListener;
    Context context;

    public RecyclerAdapter(ArrayList<Opportunity> opportunities, Context context, OnOpportunityListener onOpportunityListener) {
        this.opportunities = opportunities;
        this.context = context;
        this.onOpportunityListener = onOpportunityListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView Title;
        TextView Description;
        OnOpportunityListener onOpportunityListener;

        public MyViewHolder(@NonNull View itemView, OnOpportunityListener onOpportunityListener)
        {
            super(itemView);

            this.Title = itemView.findViewById(R.id.Title);
            this.Description = itemView.findViewById(R.id.Description);
            this.onOpportunityListener = onOpportunityListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onOpportunityListener.onOpportunityClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.opportunity, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, onOpportunityListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        TextView title = holder.Title;
        TextView description = holder.Description;

        // gets title and description from the opportunities ArrayList to display
        title.setText( opportunities.get(i).getTitle() );
        description.setText( opportunities.get(i).getDescription() );
    }

    @Override
    public int getItemCount() {
        return opportunities.size();
    }

    public interface OnOpportunityListener {

        void onOpportunityClick(int position);
    }

    public void updateList(ArrayList<Opportunity> newList)
    {
        opportunities = new ArrayList<>();
        opportunities.addAll(newList);
        notifyDataSetChanged();
    }

}