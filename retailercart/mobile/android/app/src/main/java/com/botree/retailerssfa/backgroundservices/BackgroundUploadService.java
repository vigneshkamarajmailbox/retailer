package com.botree.retailerssfa.backgroundservices;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.botree.retailerssfa.async.AsyncUpload;
import com.botree.retailerssfa.async.ScreenOrientation;
import com.botree.retailerssfa.async.TaskCallbacks;
import com.botree.retailerssfa.service.DayStartHelper;
import com.botree.retailerssfa.util.SFASharedPref;

import java.lang.ref.WeakReference;

import static com.botree.retailerssfa.util.AppUtils.getOurInstance;

public class BackgroundUploadService extends IntentService implements TaskCallbacks {

    private static final String TAG = BackgroundSyncService.class.getSimpleName();
    private boolean isTaskRunning = false;
    WeakReference<ScreenOrientation> asyncTask;
    SFASharedPref mPreferences;
    private String userType;

    public BackgroundUploadService (){
        super(BackgroundUploadService.class.getName());

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mPreferences = SFASharedPref.getOurInstance();
        userType = mPreferences.readString(SFASharedPref.PREF_USER_TYPE);
        Log.i(TAG, "Background sync Service started");
        if (getOurInstance().isNetworkConnected()) {
            uploadStart();
        }
    }

    private void uploadStart() {
        if (!isTaskRunning) {
            Log.i(TAG, "Background sync Service started...");
            AsyncUpload asyncUpload = new AsyncUpload(BackgroundUploadService.this,
                    getApplicationContext(), userType, Boolean.TRUE,"Uploading...");
            asyncTask = new WeakReference<ScreenOrientation>(asyncUpload);
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
        asyncTask = null;
    }
}
