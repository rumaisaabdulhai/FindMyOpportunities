package com.afg.helpout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The WalkThroughActivity Class
 *
 * The Screen the User sees when first
 * installing and opening the App.
 *
 */
public class WalkThroughActivity extends AppCompatActivity {

    // Variables
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private Button mNextButton;
    private Button mBackButton;

    private Button mStartButton;

    private int mCurrentPage;

    /**
     * This is the onCreate Method for WalkThroughActivity.
     *
     * When called, it initializes the SliderAdapter with the introductory
     * screens for the app. The back, next, and start buttons are also
     * initialized and have listeners implemented.
     *
     * @param savedInstanceState State of the UI Controller.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        // Getting references for the following attributes
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        mBackButton = (Button) findViewById(R.id.backButton);
        mNextButton = (Button) findViewById(R.id.nextButton);
        mStartButton = (Button) findViewById(R.id.startButton);

        // Creating a new SliderAdapter
        SliderAdapter sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        // Add dots at the bottom to show the User their location in the slides.
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        // onClick for Next Button
        mNextButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Sets the current item to be the next slide in the
             * SlideViewPager when the next button is clicked.
             * @param view The View object.
             */
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        // onClick for Back Button
        mBackButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Sets the current item to be the previous slide in the
             * SlideViewPager when the back button is clicked.
             * @param view The View object.
             */
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        // onClick for Start Button
        mStartButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Opens the SignInActivity when the "Get Started" Button
             * is clicked by the user.
             * @param view The View object.
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalkThroughActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * Adds the dots at the bottom of the WalkThroughActivity
     * and makes the dot of the current page a lighter and more visible
     * color to indicate to the User about their location in the Slides.
     *
     * @param index The index of the dot (also index of the current page).
     */
    public void addDotsIndicator(int index) {

        // creates a TextView list for storing dots
        mDots = new TextView[5];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {

            // Sets each Dot to be Transparent White
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }

        // Sets the color of the dot that indicates the current page to be a pure white.
        if (mDots.length > 0) {

            mDots[index].setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int j) { }

        /**
         * Called when a Page is Selected. Sets the visibility
         * and text of each button based on the index of the page passed in.
         *
         * @param i The index of the Page.
         */
        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;

            if (i == 0) {
                mNextButton.setEnabled(true);
                mBackButton.setEnabled(false);
                mStartButton.setEnabled(false);

                mBackButton.setVisibility(View.INVISIBLE);
                mStartButton.setVisibility(View.INVISIBLE);
                mNextButton.setVisibility(View.VISIBLE);

                mBackButton.setText("");
            }

            else if (i == mDots.length - 1) {
                mNextButton.setEnabled(false);
                mBackButton.setEnabled(true);
                mStartButton.setEnabled(true);

                mBackButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);
                mStartButton.setVisibility(View.VISIBLE);

                mBackButton.setText(R.string.backText);
            }

            else {
                mNextButton.setEnabled(true);
                mBackButton.setEnabled(true);
                mStartButton.setEnabled(false);

                mBackButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.VISIBLE);
                mStartButton.setVisibility(View.INVISIBLE);

                mNextButton.setText(R.string.nextText);
                mBackButton.setText(R.string.backText);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) { }
    };

}