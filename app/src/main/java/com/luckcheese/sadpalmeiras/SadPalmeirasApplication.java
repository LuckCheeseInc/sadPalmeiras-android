package com.luckcheese.sadpalmeiras;

import android.app.Application;

public class SadPalmeirasApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AnalyticsManager.initialize(this);
    }
}
