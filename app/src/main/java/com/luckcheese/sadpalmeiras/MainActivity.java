package com.luckcheese.sadpalmeiras;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.refresh).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (GooglePlayServiceHelper.check(this)) {
            AnalyticsManager.getInstance().trackPageView("Home");
            loadBannerAd();
        }

        updateView();
    }

    private void updateView() {
        new AsyncTask<Void, Void, Void>() {

            TextView loadingView = (TextView) findViewById(R.id.titlesCountLoading);

            private int mNumberToShow;
            Random rand = new Random();
            Animation in = new AlphaAnimation(0.5f, 1.0f);
            Animation out = new AlphaAnimation(1.0f, 0.5f);
            {
                out.setDuration(10);
                in.setDuration(10);

                out.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        loadingView.setText(String.valueOf(mNumberToShow));
                        loadingView.startAnimation(in);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                setComputing(true);
                loadingView.setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.date)).setText(R.string.computing_titles);
            }

            @Override
            protected Void doInBackground(Void... params) {
                long endTIme = System.currentTimeMillis() + 2000;
                while (System.currentTimeMillis() < endTIme) {
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

                mNumberToShow = rand.nextInt(9);
                loadingView.clearAnimation();
                loadingView.startAnimation(out);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                loadingView.setVisibility(View.INVISIBLE);
                setComputing(false);
                showResult(new Date(), 0);
            }
        }.execute(new Void[1]);
    }

    private void setComputing(boolean computing) {
        final List<View> viewsThatHideDuringLoading = Arrays.asList(
                findViewById(R.id.share),
                findViewById(R.id.logo),
                findViewById(R.id.titles),
                findViewById(R.id.refresh),
                findViewById(R.id.titlesCount));

        if (computing) {
            for (View v : viewsThatHideDuringLoading) {
                v.setVisibility(View.INVISIBLE);
            }
        }
        else {
            Animation lineIn = new AlphaAnimation(0.0f, 1.0f);
            lineIn.setDuration(200);
            for (View v : viewsThatHideDuringLoading) {
                v.setAnimation(lineIn);
            }

            lineIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    for (View v : viewsThatHideDuringLoading) {
                        v.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            lineIn.startNow();
        }
    }

    private void showResult(Date date, int titlesCount) {
        ((TextView) findViewById(R.id.titlesCount)).setText(String.valueOf(titlesCount));
        ((TextView) findViewById(R.id.date)).setText(getDateFormatted(date));
    }

    private String getDateFormatted(Date date) {
        Locale locale = new Locale("pt", "BR");
        String dateS = DateFormat.getDateInstance(DateFormat.SHORT, locale).format(date);
        String timeS = DateFormat.getTimeInstance(DateFormat.SHORT, locale).format(date);

        return getString(R.string.date_line, dateS, timeS);
    }

    private void loadBannerAd() {
        AdRequest.Builder builder = new AdRequest.Builder();
        if (BuildConfig.DEBUG) {
            builder = builder.addTestDevice("66E484AD30BFD2BC209FA8559D395842");
        }
        AdRequest adRequest = builder.build();

        ((AdView) findViewById(R.id.adView)).loadAd(adRequest);
    }

    // ----- View.OnClickListener ---------------------------------------------

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_link));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

            case R.id.refresh:
                updateView();
                break;
        }
    }
}
