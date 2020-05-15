package com.afg.helpout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * SliderAdapter Class
 *
 * Custom Adapter for the WalkThroughActivity.
 * Allows the User to slide between the pages about
 * the app after a fresh install.
 */
public class SliderAdapter extends PagerAdapter {

    // Variables
    Context context;
    LayoutInflater layoutInflater;

    /**
     * Constructor for the SliderAdapter
     * @param context
     */
    public SliderAdapter(Context context) {

        this.context = context;

    }

    // Array that holds the IDs for the Icons Used
    public int[] slide_images = {
            R.drawable.ic_helping_hand,
            R.drawable.ic_finder,
            R.drawable.ic_save,
            R.drawable.ic_network,
            R.drawable.ic_empty
    };

    // Array that holds the IDs for the Large Headings Used
    public String[] large_headings = {
            "",
            "",
            "",
            "",
            "Ready to \nHelp Out?",
    };

    // Array that holds the IDs for the Slide Headings Used
    public String[] slide_headings = {
            "Welcome to HelpOut",
            "Find Your Cause",
            "Save For Later",
            "Network with Organizations",
            "",
    };

    // Array that holds the IDs for the Slide Descriptions Used
    public String[] slide_descriptions = {
            "Here you can find community service opportunities.",
            "You can search opportunities by location that match your interests and needs.",
            "You can also favorite opportunities that interest you.",
            "Reach out to organizations to begin your volunteering journey. Good luck!",
            "",
    };

    /**
     * Gets the number of pages in the SliderAdapter.
     *
     * @return the length of slide_headings.
     */
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    /**
     * TODO: Complete Documentation
     *
     * @param view
     * @param o
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    /**
     * Creates a Slide.
     *
     * @param container The ViewGroup object.
     * @param index The index of the slide.
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int index) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        // Get references for each View
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);
        TextView largeHeading = (TextView) view.findViewById(R.id.large_heading);

        slideImageView.setImageResource(slide_images[index]);
        slideHeading.setText(slide_headings[index]);
        slideDescription.setText(slide_descriptions[index]);
        largeHeading.setText(large_headings[index]);

        // Adds the View
        container.addView(view);

        return view;
    }

    /**
     * Removes the Slide from view to make
     * way for the next Slide.
     *
     * @param container The ViewGroup object.
     * @param position The position of the slide.
     * @param object The .
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout) object);
    }
}