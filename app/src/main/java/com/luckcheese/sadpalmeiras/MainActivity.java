package com.luckcheese.sadpalmeiras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.share).setOnClickListener(this);

        loadBannerAd();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupView();
    }

    private void setupView() {
        ((TextView) findViewById(R.id.titlesCount)).setText("0");
        ((TextView) findViewById(R.id.date)).setText(getDateFormatted());
    }

    private String getDateFormatted() {
        StringBuilder dateString = new StringBuilder();

        Date currentTime = new Date();
        dateString.append(DateFormat.getDateInstance(DateFormat.SHORT).format(currentTime));
        dateString.append(" ");
        dateString.append(getString(R.string.date_preposition));
        dateString.append(" ");
        dateString.append(DateFormat.getTimeInstance(DateFormat.SHORT).format(currentTime));
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
