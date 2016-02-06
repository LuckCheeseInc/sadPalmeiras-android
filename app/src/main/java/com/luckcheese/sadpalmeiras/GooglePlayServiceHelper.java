package com.luckcheese.sadpalmeiras;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class GooglePlayServiceHelper {

    private static final int REQUEST_CODE_UPDATE_SERVICES = 0x55FF;

    public static boolean check(Activity activity) {
        int availability = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (availability == ConnectionResult.SUCCESS) {
            return true;
        }
        else if (canUpdate(availability)) {
            GooglePlayServicesUtil.getErrorDialog(availability, activity, REQUEST_CODE_UPDATE_SERVICES).show();
            return false;
        }
        else {
            Toast.makeText(activity, R.string.google_play_services_unsupported, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private static boolean canUpdate(int availability) {
        return availability == ConnectionResult.SERVICE_MISSING ||
                availability == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED ||
                availability == ConnectionResult.SERVICE_DISABLED;
    }
}
