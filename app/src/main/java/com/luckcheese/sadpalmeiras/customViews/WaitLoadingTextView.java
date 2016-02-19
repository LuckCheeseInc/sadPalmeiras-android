package com.luckcheese.sadpalmeiras.customViews;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.luckcheese.sadpalmeiras.R;

import java.util.Date;
import java.util.Random;

public class WaitLoadingTextView extends SmoothChangeTextView {

    private boolean running;

    public WaitLoadingTextView(Context context) {
        super(context);
    }

    public WaitLoadingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaitLoadingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WaitLoadingTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void start() {
        if (running) return;
        setVisibility(View.VISIBLE);
        running = true;

        new AsyncTask<Void, Void, Void>() {

            Random rand = new Random();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                while (running) {
                    publishProgress(new Void[1]);
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);

                changeText(String.valueOf(rand.nextInt(9)));
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute(new Void[1]);
    }

    public void stop() {
        running = false;
        setVisibility(View.GONE);
    }
}
