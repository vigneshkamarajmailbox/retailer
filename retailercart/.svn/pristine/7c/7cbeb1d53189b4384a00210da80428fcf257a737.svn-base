package com.botree.retailerssfa.firebase;

import android.util.Log;

import com.botree.retailerssfa.util.SFASharedPref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = "";
        if (FirebaseInstanceId.getInstance().getToken() != null)
            refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SFASharedPref.getOurInstance().writeString(SFASharedPref.PREF_FCM_TOKEN, refreshedToken);
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
    }
}
