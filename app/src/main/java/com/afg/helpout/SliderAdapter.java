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

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {

        this.context = context;

    }

    // Arrays
    public int[] slide_images = {
            R.drawable.ic_helping_hand,
            R.drawable.ic_finder,
            R.drawable.ic_save,
            R.drawable.ic_network,
            R.drawable.ic_empty
    };

    public String[] large_headings = {
            "",
            "",
            "",
            "",
            "Ready to \nHelp Out?",
    };

    public String[] slide_headings = {
            "Welcome to Help Out",
            "Find Your Cause",
            "Save For Later",
            "Network with Organizations",
            "",
    };

    public String[] slide_descriptions = {
            "Here you can find community service opportunities.",
            "You can search opportunities by location that match your interests and needs.",
            "You can also favorite opportunities that interest you.",
            "Reach out to organizations to begin your volunteering journey. Good luck!",
            "",
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        TextView largeHeading = (TextView) view.findViewById(R.id.large_heading);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descriptions[position]);

        largeHeading.setText(large_headings[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout) object);
    }
}
