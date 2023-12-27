/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.async;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.botree.retailerssfa.service.DayStartHelper;

/**
 * Interface used to handle the screen orientation change.
 * Implemented into activity where need to get the callback of respective methods
 */
public interface TaskCallbacks {

    /**
     * Call {@link TaskCallbacks}.{@link #onPreExecute(String, String)} inside
     * {@link AsyncTask}.onPreExecute method next to super.onPreExecute statement.
     *
     * @param title which need to show in {@link ProgressDialog}
     * @param msg   which need to show in {@link ProgressDialog}
     */
    void onPreExecute(String title, String msg);

    /**
     * Call {@link TaskCallbacks}.{@link #onProgressUpdate(int)} inside
     * {@link AsyncTask}.onProgressUpdate method next to super.onProgressUpdate statement.
     *
     * @param percent which updated in {@link ProgressDialog} progress
     */
    void onProgressUpdate(int percent);

    /**
     * Call {@link TaskCallbacks}.{@link #onCancelled()} inside
     * {@link AsyncTask}.onCancelled method next to super.onCancelled statement.
     */
    void onCancelled();

    /**
     * Call {@link TaskCallbacks}.{@link #onPostExecute(DayStartHelper.AsyncResponse)} inside
     * {@link AsyncTask}.onPostExecute method next to super.onPostExecute statement.
     *
     * @param response response returned from doInBackground method of {@link AsyncTask}
     */
    void onPostExecute(DayStartHelper.AsyncResponse response);


}
