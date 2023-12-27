package com.botree.retailerssfa.backgroundservices;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.util.SFASharedPref;

public class LocationUpdateService extends Service {

    private static final String TAG = "LocationUpdateService";
    public static final String BROADCAST = "com.botree.productsfa.backgroundservices.updateUI";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 30000;
    private static final float LOCATION_DISTANCE = 5;

    SFADatabase db;
    SharedPreferences preferences;
    LocalBroadcastManager broadcastManager;

    public LocationUpdateService() {
        //required empty constructor
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class MyLocationListener implements android.location.LocationListener{

        Location mLastLocation;

        MyLocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            mLastLocation.set(location);


            sendBroadcast();
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //do nothing
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(LocationUpdateService.this, "SFA Odometer Started! Don't disable the GPS!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(LocationUpdateService.this, "Alert! SFA Odometer stopped! Kindly enable the GPS to start!", Toast.LENGTH_SHORT).show();
        }
    }

    MyLocationListener[] mLocationListeners = new MyLocationListener[]{
            new MyLocationListener(LocationManager.GPS_PROVIDER),
            new MyLocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    @Override
    public void onCreate()
    {

        Context context = getApplicationContext();
         db = SFADatabase.getInstance(context);
         preferences = SFASharedPref.getSharedPreferences(context);
         broadcastManager = LocalBroadcastManager.getInstance(context);

        initializeLocationManager();

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    public void sendBroadcast(){
        Intent intent = new Intent(BROADCAST);
        broadcastManager.sendBroadcast(intent);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mLocationManager != null) {
            for (MyLocationListener mLocationListener : mLocationListeners) {
                try {
                    mLocationManager.removeUpdates(mLocationListener);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
