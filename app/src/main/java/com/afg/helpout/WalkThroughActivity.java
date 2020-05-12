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

public class WalkThroughActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private Button mNextButton;
    private Button mBackButton;

    private Button mStartButton;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        mBackButton = (Button) findViewById(R.id.backButton);
        mNextButton = (Button) findViewById(R.id.nextButton);
        mStartButton = (Button) findViewById(R.id.startButton);

        SliderAdapter sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        // onClick for Next Button
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        // onClick for Back Button
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        // onClick for Start Button
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalkThroughActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void addDotsIndicator(int position) {

        mDots = new TextView[5];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0) {

            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int j) {

        }

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