/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.async;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.arcprogressbar.Utils;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.service.DayStartHelper;
import com.botree.retailerssfa.service.WebServices;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Date;

import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_APP_VERSION;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_CMPCODE_CODE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_DISTR_CODE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_LOGIN_CODE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_SYSTEM_DATE;
import static com.botree.retailerssfa.controller.constants.AppConstant.USER_TYPE_DISTR;
import static com.botree.retailerssfa.service.DayStartHelper.isJsonNotNull;
import static com.botree.retailerssfa.service.JSONConstants.NEW_APP_DOWNLOAD_URL;
import static com.botree.retailerssfa.service.JSONConstants.NEW_APP_VERSION;
import static com.botree.retailerssfa.service.JSONConstants.TAG_COVERAGE_EXISTS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SERVER_DATE_MATCHED;
import static com.botree.retailerssfa.service.JSONConstants.TAG_USER_ACTIVE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DAY_START_DATE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LOGIN_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_NEWAPPDOWNLOADURL;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_NEW_APK_VERSION;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_YEAR_MONTH_DATE;

/**
 * Do the day start process and delete the tables from db.
 * <p>
 * <li>If first time of day start on a particular day then
 * delete all the db tables data(including master and previously
 * order taken data).</li>
 * <li>If not first time of day start on a particular day then
 * delete only master tables data</li>
 * </p>
 * <p>
 * All override method of {@link AsyncTask} is redirected to {@link TaskCallbacks} interface
 * methods, which need to be implemented in respective calling activity or fragment.
 * </p>
 * <p>
 * {@link #attach(TaskCallbacks)} is used to attach the new reference of {@link TaskCallbacks}
 * after screen orientation changed in implemented activity or fragment
 * </p>
 */

public class AsyncDayStart extends AsyncTask<Void, Integer, DayStartHelper.AsyncResponse> implements ScreenOrientation {

    private static final String TAG = AsyncDayStart.class.getSimpleName();
    private final boolean deleteOnlyMaster;
    private final WeakReference<Activity> activityRef;
    private DayStartHelper dayStartHelper;
    private TaskCallbacks taskCallbacks;
    private String strUrl;
    private SFADatabase db;

    public AsyncDayStart(TaskCallbacks taskCallbacks, Activity context, String userType,
                         boolean deleteOnlyMaster) {
        this.taskCallbacks = taskCallbacks;
        this.deleteOnlyMaster = deleteOnlyMaster;
        activityRef = new WeakReference<>(context);

        SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
        if (userType.equalsIgnoreCase(USER_TYPE_DISTR)) {

            strUrl = preferences.getString(SFASharedPref.PREF_URL, "") +
                    context.getString(R.string.download_distMaster_url);

        } else {
            strUrl = preferences.getString(SFASharedPref.PREF_URL, "") +
                    context.getString(R.string.download_local_url);
        }
        Log.d(TAG, "strUrl : " + strUrl);
        db = SFADatabase.getInstance(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // call the taskCallbacks.onPreExecute to show the progress dialog
        Activity activity = activityRef.get();
        if (taskCallbacks != null)
            taskCallbacks.onPreExecute(activity.getString(R.string.downloading), activity.getString(R.string.MSG_LOADING));

    }

    @Override
    protected DayStartHelper.AsyncResponse doInBackground(Void... params) {
        try {
            Activity activity = activityRef.get();
            publishProgress(0);
            String json = getDistrDetailsJson();

            if (BuildConfig.DEBUG)
                Log.e(TAG, "download request " + json);
            publishProgress(10);

            publishProgress(15);
            String response = WebServices.callSettingWS(activity, json, strUrl);
//            String response = activity.getResources().getString(R.string.download_response);
//            String response = Utils.getJsonFromRaw(activity,R.raw.download_response);
            JSONObject jsonObj = new JSONObject(response);

            Log.d("download response", response);

            if (response == null || response.isEmpty()) {

                return DayStartHelper.AsyncResponse.SERVER_UNREACHABLE;

            } else if (WebServices.isValidResponse(response) &&
                    (response.equalsIgnoreCase("401") ||
                            response.equalsIgnoreCase("410")))
                return DayStartHelper.AsyncResponse.AUTH_FAIL;
            else if (response.equalsIgnoreCase("500"))
                return DayStartHelper.AsyncResponse.INTERNAL_SERVER_ERROR;

            else if (!WebServices.isValidResponse(response))
                return DayStartHelper.AsyncResponse.DAY_START_ERROR;

            else if (jsonObj.has(TAG_SERVER_DATE_MATCHED) &&
                    jsonObj.get(TAG_SERVER_DATE_MATCHED) instanceof Boolean &&
                    !jsonObj.getBoolean(TAG_SERVER_DATE_MATCHED)) {

                return DayStartHelper.AsyncResponse.SERVER_DATE_MISMATCH;

            } else if (jsonObj.has(TAG_USER_ACTIVE) &&
                    jsonObj.get(TAG_USER_ACTIVE) instanceof Boolean &&
                    !jsonObj.getBoolean(TAG_USER_ACTIVE)) {

                return DayStartHelper.AsyncResponse.USER_ACTIVE;

            } else if (isNewApkAvailable(activity, jsonObj)) {

                return DayStartHelper.AsyncResponse.NEW_APK_AVAILABLE;

            } else if (jsonObj.has(TAG_COVERAGE_EXISTS) &&
                    jsonObj.get(TAG_COVERAGE_EXISTS) instanceof Boolean &&
                    !jsonObj.getBoolean(TAG_COVERAGE_EXISTS)) {

                return DayStartHelper.AsyncResponse.NO_COVERAGE_EXISTS;
            }

            Log.d(TAG, "DayStartHelper input resp : " + response);
            updateDayStartDate();
            deleteTables();
            dayStartHelper = new DayStartHelper(activity, response, taskCallbacks);
            DayStartHelper.AsyncResponse resp = checkAppVersion();
            dayStartHelper = null;
            return resp;
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: " + e.getMessage(), e);
            return null;
        }
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
        Activity activity = activityRef.get();
        this.taskCallbacks = taskCallbacks;
        // replace the old taskCallbacks instance with new one
        if (taskCallbacks != null)
            this.taskCallbacks.onPreExecute(activity.getString(R.string.downloading), activity.getString(R.string.MSG_LOADING));
        // call the dayStartHelper.updateTaskCallback to update the taskCallbacks instance in DayStartHelper
        if (dayStartHelper != null) {
            dayStartHelper.updateTaskCallback(taskCallbacks);
        }
    }

    /**
     * This method is used to check new apk version is available or not. If new
     * version available call the download dialog Else call the daystart helper
     * to insert the downloaded information
     */
    private DayStartHelper.AsyncResponse checkAppVersion() {
        return dayStartHelper.dayStart();

    }

    /**
     * Return the current salesman details as json
     *
     * @return user details json as String
     */
    private String getDistrDetailsJson() {
        try {
            Activity activity = activityRef.get();
            SharedPreferences preferences = SFASharedPref.getSharedPreferences(activity);

            String loginCode = preferences.getString(PREF_LOGIN_CODE,"");
            String appVersion = BuildConfig.VERSION_NAME;
            String systemDate = android.text.format.DateFormat.format("yyyy-MM-dd", new Date()).toString();

            JSONObject jsonObject = new JSONObject();

            jsonObject.put(REQ_LOGIN_CODE, loginCode);
            jsonObject.put(REQ_APP_VERSION, appVersion);
            jsonObject.put(REQ_SYSTEM_DATE, systemDate);

            if (jsonObject.length() == 0) {

                Toast.makeText(activity, activity.getResources().getString(R.string.ERR_SYNCFAIL), Toast.LENGTH_SHORT).show();
                return null;
            }

            return jsonObject.toString();

        } catch (Exception e) {
            Log.w(TAG, e);
        }

        return null;
    }

    /**
     * Delete the old tables rows in {@link SFADatabase}
     */
    private void deleteTables() {
        Activity activity = activityRef.get();
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(activity);
        db.removeExistingTableInfo(preferences.getString(PREF_DISTRCODE, ""),
                preferences.getString(PREF_CMP_CODE, ""), deleteOnlyMaster);

        // If the first time processing day start for a current date then delete
        // sync table row from SFADatabase
        if (!deleteOnlyMaster)
            db.deleteSyncTable();
    }

    /**
     * Update the day start date in {@link SharedPreferences}
     */
    private void updateDayStartDate() {

        Activity activity = activityRef.get();
        String today = DateUtil.getCurrentDate();
        String yearMonthDate = DateUtil.getCurrentYearMonthDate();
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(activity);

        if (!preferences.contains(PREF_DAY_START_DATE)) {
            SharedPreferences.Editor editor = SFASharedPref.getEditor(preferences);
            editor.putString(PREF_DAY_START_DATE, today);
            editor.putString(PREF_YEAR_MONTH_DATE, yearMonthDate);
            editor.apply();
        }
    }


    public static boolean isNewApkAvailable(Context context, JSONObject jsonObj) throws JSONException {

        if (jsonObj != null && (isJsonNotNull(jsonObj, NEW_APP_DOWNLOAD_URL) ||
                isJsonNotNull(jsonObj, NEW_APP_VERSION))) {

            if (BuildConfig.DEBUG) {
                String url = jsonObj.getString(NEW_APP_DOWNLOAD_URL);
                String version = jsonObj.getString(NEW_APP_VERSION);
                Log.d(TAG, "Download URL : " + url + version);
            }
            SharedPreferences.Editor editor = SFASharedPref.getEditor(context);
            editor.putString(PREF_NEWAPPDOWNLOADURL, jsonObj.getString(NEW_APP_DOWNLOAD_URL));
            editor.putString(PREF_NEW_APK_VERSION, jsonObj.getString(NEW_APP_VERSION));
            editor.commit();
            return true;
        }

        return false;

    }

}
