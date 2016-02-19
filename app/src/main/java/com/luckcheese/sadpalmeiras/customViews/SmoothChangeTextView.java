package com.luckcheese.sadpalmeiras.customViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class SmoothChangeTextView extends TextView {

    private String textToShow;
    private Animation in;
    private Animation out;



    public SmoothChangeTextView(Context context) {
        super(context);
        init();
    }

    public SmoothChangeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmoothChangeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SmoothChangeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        out = new AlphaAnimation(1.0f, 0.5f);
        out.setDuration(10);

        in = new AlphaAnimation(0.5f, 1.0f);
        in.setDuration(10);

        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setText(textToShow);
                startAnimation(in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void changeText(String toText) {
        textToShow = toText;
        startAnimation(out);
    }
}
