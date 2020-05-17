package com.afg.helpout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The RecyclerAdapter Class
 *
 * Custom Adapter that holds the RecyclerView.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    // TAG for logging
    private static final String TAG = "RecyclerAdapter";

    // The Opportunity ArrayList
    private ArrayList<Opportunity> opportunities;

    // The onOpportunityListener
    private OnOpportunityListener onOpportunityListener;
    Context context;

    /**
     * Constructor for the RecyclerAdapter.
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
     * The RecyclerViewHolder Class
     *
     * Gets the references for the title & description and
     * Initializes the onOpportunityListener.
     *
     */
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        // Variables
        TextView Title;
        TextView Description;
        OnOpportunityListener onOpportunityListener;

        /**
         * Constructor for the RecyclerViewHolder
         *
         * @param itemView The View object
         * @param onOpportunityListener The OnOpportunityListener
         */
        public RecyclerViewHolder(@NonNull View itemView, OnOpportunityListener onOpportunityListener)
        {
            super(itemView);
            this.Title = itemView.findViewById(R.id.Title);
            this.Description = itemView.findViewById(R.id.Description);
            this.onOpportunityListener = onOpportunityListener;

            itemView.setOnClickListener(this);

        }

        /**
         * Handles a click on an opportunity in the RecyclerView.
         *
         * @param view The View object
         */
        @Override
        public void onClick(View view) {
            onOpportunityListener.onOpportunityClick(getAdapterPosition());
        }
    }

    /**
     *
     * Constructor for the RecyclerViewHolder.
     *
     * @param parent The ViewGroup
     * @param viewType The view type
     * @return
     */
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.opportunity, parent, false);
        RecyclerViewHolder myViewHolder = new RecyclerViewHolder(view, onOpportunityListener);
        return myViewHolder;
    }

    /**
     * Sets each volunteer card in the RecyclerView
     * with the title and description information.
     *
     * @param holder The RecyclerViewHolder Object.
     * @param i The Index of the opportunity.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i) {

        TextView title = holder.Title;
        TextView description = holder.Description;

        // Gets title and description from the opportunities ArrayList to display
        title.setText( opportunities.get(i).getTitle() );
        description.setText( opportunities.get(i).getDescription() );
    }

    /**
     * Gets the Number of Items in the RecyclerView.
     *
     * @return The size of the Opportunities ArrayList.
     */
    @Override
    public int getItemCount() {

        if (opportunities == null) {
            return 0;
        }
        else {
            return opportunities.size();
        }
    }

    /**
     * The OnOpportunityListener Interface
     */
    public interface OnOpportunityListener {
        void onOpportunityClick(int position);
    }

    /**
     * Updates the List when User Searches in Search Bar.
     *
     * @param newList The Opportunities that match the Search Query of the User.
     */
    public void updateList(ArrayList<Opportunity> newList)
    {
        opportunities = new ArrayList<>();
        opportunities.addAll(newList);
        notifyDataSetChanged();
    }

}