package com.luckcheese.sadpalmeiras;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public final class AnalyticsManager {

    private final Tracker tracker;

    private AnalyticsManager(Context context) {
        GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(context);
        if (BuildConfig.DEBUG) {
            googleAnalytics.setLocalDispatchPeriod(2);
            googleAnalytics.setDryRun(true);
        } else {
            googleAnalytics.setLocalDispatchPeriod(30);
            googleAnalytics.setDryRun(false);
        }

        tracker = googleAnalytics.newTracker(R.xml.app_tracker);
    }

    public synchronized Tracker get() {
        return tracker;
    }

    // ----- Singleton Pattern ------------------------------------------------

    private static AnalyticsManager sInstance;

    public static synchronized void initialize(Context context) {
        if (sInstance != null) {
            throw new IllegalStateException("Extra call to initialize analytics trackers");
        }

        sInstance = new AnalyticsManager(context);
    }

    public static synchronized AnalyticsManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Call initialize() before getInstance()");
        }

        return sInstance;
    }
}
