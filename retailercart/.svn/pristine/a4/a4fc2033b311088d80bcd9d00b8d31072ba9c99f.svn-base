/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.FileAccessUtil;
import com.botree.retailerssfa.service.DayStartHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Download the new apk from server and store into the file. And new apk file will installed by the
 * Package manager.
 * <p>
 * All override method of {@link AsyncTask} is redirected to {@link TaskCallbacks} interface
 * methods, which need to be implemented in respective calling activity or fragment.
 * </p>
 * <p>
 * {@link #attach(TaskCallbacks)} is used to attach the new reference of {@link TaskCallbacks}
 * after screen orientation changed in implemented activity or fragment
 * </p>
 */
public class AutoUpdateApk extends AsyncTask<String, Integer, DayStartHelper.AsyncResponse>
        implements ScreenOrientation {

    public static final String NEW_APK_LOCATION = "Android/data/" + BuildConfig.APPLICATION_ID + "/temp";
    public static final String INSTALL_NEW_APK_LOCATION = "Android/data/" + BuildConfig.APPLICATION_ID + "/temp/Sfa.apk";
    private static final String TAG = AutoUpdateApk.class.getSimpleName();
    private final WeakReference<Context> contextRef;

    private String urlDownload;
    private TaskCallbacks taskCallbacks;
    private int currentProgress;


    public AutoUpdateApk(Context context, String url, TaskCallbacks taskCallbacks) {
        this.urlDownload = url;
        this.taskCallbacks = taskCallbacks;
        contextRef = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = contextRef.get();
        // call the taskCallbacks.onPreExecute to show the progress dialog
        taskCallbacks.onPreExecute(context.getResources().getString(R.string.title_downloading),
                context.getResources().getString(R.string.MSG_LOADING));
    }


    @Override
    protected DayStartHelper.AsyncResponse doInBackground(String... params) {
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            URL url = new URL(urlDownload);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.connect();

            File sdcard = FileAccessUtil.getInstance().getExternalStorageDirectory();
            File myDir = new File(sdcard, NEW_APK_LOCATION);
            myDir.mkdirs();
            File outputFile = new File(myDir, "Sfa.apk");
            if (outputFile.exists()) {
                boolean isDelete = outputFile.delete();
                if (isDelete)
                    Log.i(TAG, "doInBackground: deleted successfully");
            }


            int lengthOfFile = c.getContentLength();
            is = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1;
            long total = 0;
            fos = new FileOutputStream(outputFile);
            while ((len1 = is.read(buffer)) != -1) {
                total += len1;
                // publishing the progress....
                publishProgress((int) ((total * 100) / lengthOfFile));
                fos.write(buffer, 0, len1);
            }
            fos.flush();
            fos.close();
            is.close();
            return DayStartHelper.AsyncResponse.APK_DOWNLOAD_SUCCESS;
        } catch (FileNotFoundException fnfe) {
            Log.e("File", "FileNotFoundException! " + fnfe);
            return DayStartHelper.AsyncResponse.APK_DOWNLOAD_FAILURE;
        } catch (Exception e) {
            Log.e("UpdateAPP", "Exception " + e);
            return DayStartHelper.AsyncResponse.APK_DOWNLOAD_FAILURE;
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: " + e.getMessage(), e);
            }
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        currentProgress = values[0];
        // call the taskCallbacks.onProgressUpdate to update the percent in progress dialog
        taskCallbacks.onProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(DayStartHelper.AsyncResponse result) {
        super.onPostExecute(result);
        // call the taskCallbacks.onPostExecute to dismiss the progress dialog
        // and process the response which is returned from doInBackground method
        taskCallbacks.onPostExecute(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        // call the taskCallbacks.onCancelled to dismiss thetemp progress dialog
        if (taskCallbacks != null)
            taskCallbacks.onCancelled();
    }

    @Override
    public void attach(TaskCallbacks taskCallbacks) {
        // replace the old taskCallbacks instance with new one
        this.taskCallbacks = taskCallbacks;
        publishProgress(currentProgress);
    }
}
