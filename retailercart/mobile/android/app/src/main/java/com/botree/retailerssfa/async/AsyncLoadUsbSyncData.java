/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.async;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.botree.retailerssfa.db.FileAccessUtil;
import com.botree.retailerssfa.service.DayCloseHelper;
import com.botree.retailerssfa.service.DayStartHelper;
import com.botree.retailerssfa.util.AESEncryption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;


public class AsyncLoadUsbSyncData extends AsyncTask<Void, Integer, DayStartHelper.AsyncResponse>  {
    private static final String TAG = AsyncLoadUsbSyncData.class.getSimpleName();
    private final WeakReference<Context> contextRef;
    private TaskCallbacks taskCallbacks;
    private String userType;
    public static final  String FILENAME = "uploadJson.txt";
    private String json="";
    public static final  String PATH = FileAccessUtil.getInstance().getExternalStorageDirectory().getAbsolutePath() + "/UsbSyncFolder/";


    public AsyncLoadUsbSyncData(TaskCallbacks taskCallbacks, Context context, String userType) {
        this.taskCallbacks = taskCallbacks;
        this.userType = userType;
        contextRef = new WeakReference<>(context);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        publishProgress(70);

        if (taskCallbacks != null)
            taskCallbacks.onPreExecute("Creating UsbSync Folder", "Loading & Encrypting Data To File");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (taskCallbacks != null)
            taskCallbacks.onProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(DayStartHelper.AsyncResponse response) {
        super.onPostExecute(response);
        // call the taskCallbacks.onPostExecute to dismiss the progress dialog
        // and process the response which is returned from doInBackground method
        try {
            String secretKey = "secretkey";
            AESEncryption aesEncryption = new AESEncryption(secretKey);
            saveToFile(aesEncryption.encrypt(json));
        } catch (Exception e) {
            Log.e(TAG, "onPostExecute: "+e.getMessage(),e );
        }

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
    protected DayStartHelper.AsyncResponse doInBackground(Void... params) {


        final Context context = contextRef.get();

        DayCloseHelper dayCloseHelper = new DayCloseHelper(context, taskCallbacks);
        json= dayCloseHelper.getUploadJson(userType).toString();

        return null;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void saveToFile(String data) throws IOException {
        File filePath = new File(PATH + FILENAME);

        if (!filePath.exists()) {
            if (filePath.createNewFile()) {
                //New File Created
            }
        } else {
            if (filePath.delete()) {
                // Old File Deleted
            }
            if (filePath.createNewFile()) {
                //New File Created
            }
        }
        if(new File(PATH).mkdir()){
            //ignored

        }
        try (FileOutputStream fos = new FileOutputStream(filePath, true)) {

            fos.write((data + System.getProperty("line.separator")).getBytes());
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage(), ex);
        } catch (IOException e) {

            Log.e("error exception", e.getMessage(), e);
        }

    }

}




