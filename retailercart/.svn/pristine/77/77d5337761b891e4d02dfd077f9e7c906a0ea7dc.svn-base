package com.botree.retailerssfa.backgroundservices;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.botree.retailerssfa.async.AsyncUpload;
import com.botree.retailerssfa.async.TaskCallbacks;
import com.botree.retailerssfa.service.DayStartHelper;
import com.botree.retailerssfa.util.SFASharedPref;

import static com.botree.retailerssfa.util.AppUtils.getOurInstance;

public class BackgroundSyncService extends Service implements TaskCallbacks {

    private static final String TAG = BackgroundSyncService.class.getSimpleName();
    private boolean isTaskRunning = false;
    private Handler handler;
    private Runnable runnable;
    private String userType;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SFASharedPref mPreferences = SFASharedPref.getOurInstance();
        userType = mPreferences.readString(SFASharedPref.PREF_USER_TYPE);
        Log.i(TAG, "Background sync Service started");
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                Log.i(TAG, "Background sync Service is still running");

                try {

                    if (getOurInstance().isNetworkConnected()) {
                        uploadStart();
                    }

                } catch (Exception e) {
                    Log.e(TAG, "run: " + e.getMessage(), e);
                }

                handler.postDelayed(runnable, 60 * 1000L);
            }
        };

        handler.postDelayed(runnable, 60 * 1000L);
        return Service.START_STICKY;
    }

    private void uploadStart() {
        if (!isTaskRunning) {
            Log.i(TAG, "Background sync Service started...");
            AsyncUpload asyncUpload = new AsyncUpload(BackgroundSyncService.this,
                    getApplicationContext(), userType, Boolean.TRUE,"Uploading...");
            asyncUpload.execute();
        }
    }

    @Override
    public void onPreExecute(String title, String msg) {
        isTaskRunning = true;
    }

    @Override
    public void onProgressUpdate(int percent) {
        //ignored
    }

    @Override
    public void onCancelled() {
        //ignored
    }

    @Override
    public void onPostExecute(DayStartHelper.AsyncResponse response) {
        isTaskRunning = false;
        stopSelf();

    }

}
