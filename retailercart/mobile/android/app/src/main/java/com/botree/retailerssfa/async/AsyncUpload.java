/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.service.DayCloseHelper;
import com.botree.retailerssfa.service.DayStartHelper;
import com.botree.retailerssfa.service.WebServices;
import com.botree.retailerssfa.util.LoggerUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.lang.ref.WeakReference;

/**
 * {@link AsyncUpload} is used to upload the order taken data, New Retailer data, Collections, Sales Return,
 * Location update etc., to server. If {@link #(TaskCallbacks, Context, boolean)} is boolean variable
 * is true then update the status as 'upload' else 'dayclose' in SyncProgress table in db.
 * <p>
 * <p>
 * All override method of {@link AsyncTask} is redirected to {@link TaskCallbacks} interface
 * methods, which need to be implemented in respective calling activity or fragment.
 * </p>
 * <p>
 * {@link #attach(TaskCallbacks)} is used to attach the new reference of {@link TaskCallbacks}
 * after screen orientation changed in implemented activity or fragment
 * </p>PowerManager pm;
 * PowerManager.WakeLock wl;
 */
public class AsyncUpload extends AsyncTask<Void, Integer, DayStartHelper.AsyncResponse> implements ScreenOrientation {
    private static final String TAG = AsyncUpload.class.getSimpleName();
    private final boolean isUpload;
    private final String strUrl;
    private final WeakReference<Context> contextRef;
    private TaskCallbacks taskCallbacks;
    private DayCloseHelper dayCloseHelper;
    private String userCode;
    private Handler handler = new Handler();
    private Runnable runnable;
    private String uploadingLabel;

    public AsyncUpload(TaskCallbacks taskCallbacks, Context context, String userCode,
                       boolean isUpload, String strLabel) {
        this.taskCallbacks = taskCallbacks;
        this.userCode = userCode;
        this.isUpload = isUpload;
        this.uploadingLabel = strLabel;
        contextRef = new WeakReference<>(context);

        SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
        strUrl = preferences.getString(SFASharedPref.PREF_URL, "") +
                context.getString(R.string.upload_local_url);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // call the taskCallbacks.onPreExecute to show the progress dialog
        if (taskCallbacks != null)
            taskCallbacks.onPreExecute("Uploading", uploadingLabel);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        // call the taskCallbacks.onProgressUpdate to update the percent in progress dialog
        if (taskCallbacks != null)
            taskCallbacks.onProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(DayStartHelper.AsyncResponse response) {
        super.onPostExecute(response);
        // call the taskCallbacks.onPostExecute to dismiss the progress dialog
        // and process the response which is returned from doInBackground method

        if (taskCallbacks != null)
            taskCallbacks.onPostExecute(response);


    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        // call the taskCallbacks.onCancelled to dismiss the progress dialog
        if (taskCallbacks != null)
            taskCallbacks.onCancelled();
    }

    @Override
    public void attach(TaskCallbacks taskCallbacks) {
        this.taskCallbacks = taskCallbacks;
        Context context = contextRef.get();
        // replace the old taskCallbacks instance with new one
        if (taskCallbacks != null)
            taskCallbacks.onPreExecute(context.getString(R.string.uploading), uploadingLabel);
        // call the dayCloseHelper.updateTaskCallback to update the taskCallbacks instance in DayCloseHelper
        if (dayCloseHelper != null) {
            dayCloseHelper.updateTaskCallback(taskCallbacks);
        }
    }

    @Override
    protected DayStartHelper.AsyncResponse doInBackground(Void... params) {
        LoggerUtil.printDebugLog(TAG, "doInBackground");
        publishProgress(0);
        Context context = contextRef.get();
        dayCloseHelper = new DayCloseHelper(context, taskCallbacks);
        String json = dayCloseHelper.getUploadJson(userCode).toString();
        LoggerUtil.printDebugLog(TAG, "Request json : json : " + json);
        publishProgress(15);
        if (BuildConfig.DEBUG)  {
            Log.e(TAG, "Upload json : " + json);
        }
        updateProgressbar();// Update progress bar manually till upload process complete

        String response = WebServices.callSettingWS(context, json, strUrl);
        handler.removeCallbacks(runnable);
        handler.removeCallbacks(runnable);
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Upload response : " + response);
        }

        if (response == null || response.isEmpty()) {

            return DayStartHelper.AsyncResponse.SERVER_UNREACHABLE;

        } else if (WebServices.isValidResponse(response) &&
                (response.equalsIgnoreCase("401") ||
                        response.equalsIgnoreCase("410")))
            return DayStartHelper.AsyncResponse.AUTH_FAIL;

        else if (response.equalsIgnoreCase("500"))
            return DayStartHelper.AsyncResponse.INTERNAL_SERVER_ERROR;

        if (!WebServices.isValidResponse(response))
            return DayStartHelper.AsyncResponse.UPLOAD_FAILED;

        return dayCloseHelper.updateCustomerUploadStatus(response, isUpload);
    }

    /**
     * Update progress base manually till upload process complete
     */
    private void updateProgressbar() {

        try {
            final int[] count = {15};
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (count[0] < 60) {
                        count[0] = count[0] + 1;
                        publishProgress(count[0]);
                        handler.postDelayed(runnable, 1000);
                    }
                }
            };

            handler.postDelayed(runnable, 1000);
        } catch (Exception e) {
            Log.e(TAG, "updateProgressbar: " + e.getMessage(), e);
        }
    }

}




