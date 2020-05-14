package com.afg.helpout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The RecyclerAdapter Class
 *
 * TODO: Complete Documentation
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    // TAG for logging
    private static final String TAG = "RecyclerAdapter";

    // The Opportunity ArrayList
    private ArrayList<Opportunity> opportunities;

    // The onOpportunityListener
    private OnOpportunityListener onOpportunityListener;
    Context context;

    /**
     * TODO: Complete Documentation
     *
     * @param opportunities
     * @param context
     * @param onOpportunityListener
     */
    public RecyclerAdapter(ArrayList<Opportunity> opportunities, Context context, OnOpportunityListener onOpportunityListener) {
        this.opportunities = opportunities;
        this.context = context;
        this.onOpportunityListener = onOpportunityListener;
    }

    /**
     * TODO: Complete Documentation
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView Title;
        TextView Description;
        OnOpportunityListener onOpportunityListener;

        /**
         * TODO: Complete Documentation
         *
         * @param itemView
         * @param onOpportunityListener
         */
        public MyViewHolder(@NonNull View itemView, OnOpportunityListener onOpportunityListener)
        {
            super(itemView);
            this.Title = itemView.findViewById(R.id.Title);
            this.Description = itemView.findViewById(R.id.Description);
            this.onOpportunityListener = onOpportunityListener;

            itemView.setOnClickListener(this);

        }

        /**
         * TODO: Complete Documentation
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            onOpportunityListener.onOpportunityClick(getAdapterPosition());
        }
    }

    /**
     *
     * TODO: Complete Documentation
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.opportunity, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, onOpportunityListener);
        return myViewHolder;
    }

    /**
     * TODO: Complete Documentation
     *
     * @param holder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        TextView title = holder.Title;
        TextView description = holder.Description;

        // gets title and description from the opportunities ArrayList to display
        title.setText( opportunities.get(i).getTitle() );
        description.setText( opportunities.get(i).getDescription() );
    }

    /**
     * TODO: Complete Documentation
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return opportunities.size();
    }

    public interface OnOpportunityListener {

        /**
         * TODO: Complete Documentation
         *
         * @param position
         */
        void onOpportunityClick(int position);
    }

    /**
     * TODO: Complete Documentation
     *
     * @param newList
     */
    public void updateList(ArrayList<Opportunity> newList)
    {
        opportunities = new ArrayList<>();
        opportunities.addAll(newList);
        notifyDataSetChanged();
    }

}