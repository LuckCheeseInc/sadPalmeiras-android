package com.luckcheese.sadpalmeiras;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTitlesCountView;
    private TextView mDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitlesCountView = (TextView) findViewById(R.id.titlesCount);
        mDateView = (TextView) findViewById(R.id.date);
        findViewById(R.id.share).setOnClickListener(this);

        loadBannerAd();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateView();
    }

    private void updateView() {
        new AsyncTask<Void, Void, Void>() {

            Random rand = new Random();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showLines(false);
                mDateView.setText(R.string.computing_titles);
            }

            @Override
            protected Void doInBackground(Void... params) {
                long endTIme = System.currentTimeMillis() + rand.nextInt(3000) + 2000;
                while (System.currentTimeMillis() < endTIme) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    publishProgress(new Void[1]);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);

                int numberToShow = rand.nextInt(9);
                mTitlesCountView.setText(String.valueOf(numberToShow));
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                showLines(true);
                showResult(new Date(), 0);
            }
        }.execute(new Void[1]);
    }

    private void showLines(boolean show) {
        if (show) {
            findViewById(R.id.line1).setVisibility(View.VISIBLE);
            findViewById(R.id.line3).setVisibility(View.VISIBLE);
            findViewById(R.id.line5).setVisibility(View.VISIBLE);
        }
        else {
            findViewById(R.id.line1).setVisibility(View.INVISIBLE);
            findViewById(R.id.line3).setVisibility(View.INVISIBLE);
            findViewById(R.id.line5).setVisibility(View.INVISIBLE);
        }
    }

    private void showResult(Date date, int titlesCount) {
        mTitlesCountView.setText(String.valueOf(titlesCount));
        mDateView.setText(getDateFormatted(date));
    }

    private String getDateFormatted(Date date) {
        StringBuilder dateString = new StringBuilder();

        dateString.append(DateFormat.getDateInstance(DateFormat.SHORT).format(date));
        dateString.append(" ");
        dateString.append(getString(R.string.date_preposition));
        dateString.append(" ");
        dateString.append(DateFormat.getTimeInstance(DateFormat.SHORT).format(date));
        dateString.append(",");

        return dateString.toString();
    }

    private void loadBannerAd() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    // ----- View.OnClickListener ---------------------------------------------

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_link));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }
}
