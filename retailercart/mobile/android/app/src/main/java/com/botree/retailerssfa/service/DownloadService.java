package com.botree.retailerssfa.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.db.FileAccessUtil;
import com.botree.retailerssfa.models.Download;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.AppUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import okhttp3.ResponseBody;

import static com.botree.retailerssfa.controller.retrofit.ApiClient.GET_NOTIFICATION;
import static com.botree.retailerssfa.controller.retrofit.ApiClient.REDIRECT;


public class DownloadService extends IntentService {

    private static final String TAG = DownloadService.class.getSimpleName();
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    public DownloadService() {
        super("Download Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent.getExtras() != null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String filename = intent.getStringExtra("FILE");
            String accessToken = intent.getStringExtra("accessToken");
            int position = intent.getIntExtra("Position", -1);
            initDownload(filename, accessToken, position);
        }

    }


    private void initDownload(final String filename, String accessToken, final int position) {

        final String newFileName = filename.replace("/", ".");
        DataManager.getInstance().downloadFile(accessToken, REDIRECT, GET_NOTIFICATION, filename, new DataManager.FileDownloadCallBack() {
            @Override
            public void onFileDownloadCallback(String message, ResponseBody responseBody, boolean isSuccess, AppConstant.RequestType requestType) {
                if (isSuccess) {
                    int icon = R.drawable.logo;
                    int iconFile;

                    File file = new File(FileAccessUtil.getInstance().getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                            newFileName);
                    Log.e("file", "" + file);
                    String fileName = file.toString().toLowerCase(Locale.getDefault());
                    iconFile = getIconFile(fileName);

                    Intent fileIntent = AppUtils.getOurInstance().openFile(file, getApplicationContext());
                    final PendingIntent resultPendingIntent =
                            PendingIntent.getActivity(getApplicationContext(), position,
                                    fileIntent, 0);
                    notificationBuilder = new NotificationCompat.Builder(DownloadService.this, BuildConfig.APPLICATION_ID)
                            .setSmallIcon(R.drawable.ic_download)
                            .setContentTitle(filename)
                            .setContentText("Downloading File")
                            .setSmallIcon(iconFile)
                            .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), icon))
                            .setContentIntent(resultPendingIntent)
                            .setAutoCancel(false);

                    notificationManager.notify(position, notificationBuilder.build());
                    writeResponseBodyToDisk(responseBody, newFileName, position);
                } else {
                    sendFailedIntent(position);
                    Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
                    stopSelf();
                }
            }
        });

    }


    private int getIconFile(String fileName) {
        int iconFile;
        if (fileName.contains(".jpg") || fileName.contains(".jpeg") || fileName.contains(".png")) {
            iconFile = R.drawable.ic_file_image_white;
        } else if (fileName.contains(".wav") || fileName.contains(".mp3")) {
            iconFile = R.drawable.ic_file_music_white;
        } else if (fileName.contains(".doc") || fileName.contains(".docx")) {
            iconFile = R.drawable.ic_file_word_white;
        } else if (fileName.contains(".3gp") || fileName.contains(".mpg") || fileName.contains(".mpeg") ||
                fileName.contains(".mpe") || fileName.contains(".mp4") || fileName.contains(".avi")) {
            iconFile = R.drawable.ic_file_video_white;
        } else if (fileName.contains(".pdf")) {
            iconFile = R.drawable.ic_file_pdf_white;
        } else if (fileName.contains(".xls") || fileName.contains(".xlsx")) {
            iconFile = R.drawable.ic_file_excel_white;
        } else {
            iconFile = R.drawable.ic_file_doc_white;
        }
        return iconFile;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("downloadservice", "ondestroy");
    }

    private void writeResponseBodyToDisk(ResponseBody body, String newFileName, int position) {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File outputFile = new File(FileAccessUtil.getInstance().getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), newFileName);

            byte[] fileReader = new byte[4096];
            int read;
            long fileSize = body.contentLength();
            long fileSizeDownloaded = 0;

            inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);
            outputStream = new FileOutputStream(outputFile);

            Download download = new Download();
            float totalFileSize = (float) (fileSize / (Math.pow(1024, 2)));

            download.setTotalFileSize(totalFileSize);
            int value = 10;
            float tempTotalFileSize = 0;

            while ((read = inputStream.read(fileReader)) != -1) {

                outputStream.write(fileReader, 0, read);

                fileSizeDownloaded += read;

                float progress = ((fileSizeDownloaded * 100) / totalFileSize);
                float current = (float) (fileSizeDownloaded / (Math.pow(1024, 2)));
                if (current > tempTotalFileSize) {
                    download.setCurrentFileSize(current);
                    download.setProgress(progress);
                    tempTotalFileSize = totalFileSize / value;
                    value--;
                }
            }

            onDownloadComplete(newFileName, position);
            outputStream.flush();

        } catch (IOException e) {
            Log.e(TAG, "writeResponseToDisk: " + e.getMessage(), e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "writeResponseBodyToDisk: " + e.getMessage(), e);
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (Exception e) {
                Log.e(TAG, "writeResponseBodyToDisk: " + e.getMessage(), e);
            }
        }
    }

    private void sendIntent(Download download, int position) {

        Intent intent = new Intent(Globals.MESSAGE_PROGRESS);
        intent.putExtra("download", download);
        intent.putExtra("downloadPosition", position);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void sendFailedIntent(int position) {

        Intent intent = new Intent(Globals.MESSAGE_UNPROGRESS);
        intent.putExtra("downloadPosition", position);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(String newFileName, int position) {

        Download download = new Download();
        download.setProgress(100);
        sendIntent(download, position);

        notificationManager.cancel(position);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("File Downloaded");
        notificationBuilder.setContentTitle(newFileName);
        notificationManager.notify(position, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //ignored
    }

}
