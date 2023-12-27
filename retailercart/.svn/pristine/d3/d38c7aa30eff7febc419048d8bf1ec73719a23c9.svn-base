package com.botree.retailerssfa.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.core.app.NotificationCompat;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static android.os.Build.VERSION_CODES.N;

public class NotificationUtils {

    private static final String ANDROID_CHANNEL_ID = BuildConfig.APPLICATION_ID;
    private static final String ANDROID_CHANNEL_NAME = "Botree";
    private static final String TAG = NotificationUtils.class.getSimpleName();
    private static NotificationUtils notifyInstance = null;
    private static WeakReference<Context> contextRef;
    private NotificationManager mNotificationManager;


    private NotificationUtils() {
    }

    public static synchronized NotificationUtils getNotifyInstance() {
        if (notifyInstance == null)
            notifyInstance = new NotificationUtils();

        return notifyInstance;
    }


    public static void initNotificationUtils(Context context) {
        if (notifyInstance == null) {
            notifyInstance = new NotificationUtils();
            contextRef = new WeakReference<>(context);
        }

    }


    /**
     * Method checks if the app is in background or not
     *
     * @param context context of the calling class
     * @return true is the app is in background else false
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            assert am != null;
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                isInBackground = isInBackground(context, isInBackground, processInfo);
            }
        } else {
            assert am != null;
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    private static boolean isInBackground(Context context, boolean isInBackground, ActivityManager.RunningAppProcessInfo processInfo) {
        if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            for (String activeProcess : processInfo.pkgList) {
                if (activeProcess.equals(context.getPackageName())) {
                    isInBackground = false;
                }
            }
        }
        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancelAll();
    }

    private static long getTimeMilliSec(String timeStamp) {
        return Long.parseLong(timeStamp);
    }


    public void showNotificationMessage(String title, String message, String timeStamp, Intent intent) {
        showNotificationMessage(title, message, timeStamp, intent, null);
    }

    private void showNotificationMessage(final String title, final String message, final String timeStamp, Intent intent, String imageUrl) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;

        int currentNotificationId = (int) System.currentTimeMillis();

        Context mContext = contextRef.get();
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(mContext,currentNotificationId,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, ANDROID_CHANNEL_ID);
        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);

                if (bitmap != null) {
                    showBigNotification(bitmap, mBuilder, title, message, timeStamp, resultPendingIntent, currentNotificationId);
                } else {
                    showSmallNotification(mBuilder, title, message, timeStamp, resultPendingIntent, currentNotificationId);
                }
            }
        } else {
            showSmallNotification(mBuilder, title, message, timeStamp, resultPendingIntent, currentNotificationId);

        }
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, String title, String message, String timeStamp, PendingIntent resultPendingIntent, int currentNotificationId) {
        Notification notification;
        final int icon = R.drawable.logo;
        Context mContext = contextRef.get();
        notification = mBuilder.setSmallIcon(icon).setTicker(title)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setStyle(getNotificationStyle(message, title, timeStamp))
                .setWhen(Long.parseLong(timeStamp))
                .setSmallIcon(getSmallNotificationIcon())
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(message)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(ANDROID_CHANNEL_ID,ANDROID_CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            getManager().createNotificationChannel(notificationChannel);
        }
        getManager().notify(currentNotificationId, notification);
        playNotificationSound();
    }


    private NotificationCompat.Style getNotificationStyle(String message, String title, String timeStamp) {
        NotificationCompat.Style notificationStyle;
        if (android.os.Build.VERSION.SDK_INT >= N) {

            notificationStyle = new NotificationCompat.MessagingStyle("SFA").setConversationTitle(title)
                    .addMessage(new NotificationCompat.MessagingStyle.Message(message, getTimeMilliSec(timeStamp), ""));
        } else {
            notificationStyle = new NotificationCompat.BigTextStyle().bigText(message).setBigContentTitle(title);
        }
        return notificationStyle;
    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder,  String title, String message, String timeStamp, PendingIntent resultPendingIntent, int currentNotificationId) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(message);
        bigPictureStyle.bigPicture(bitmap);
        Notification notification;
        Context mContext = contextRef.get();
        // notification icon
        final int icon = R.drawable.logo;
        notification = mBuilder.setSmallIcon(icon).setTicker(title)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setStyle(bigPictureStyle)
                .setWhen(Long.parseLong(timeStamp))
                .setSmallIcon(getSmallNotificationIcon())
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(message)
                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(ANDROID_CHANNEL_ID,ANDROID_CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            getManager().createNotificationChannel(notificationChannel);
        }
        getManager().notify(currentNotificationId, notification);
        playNotificationSound();
    }

    private int getSmallNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_stat_sfa : R.drawable.logo;
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            Log.e(TAG, "getBitmapFromURL: "+e.getMessage(),e);
            return null;
        }
    }

    // Playing notification sound
    private void playNotificationSound() {
        try {
            Context mContext = contextRef.get();
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() +  "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            Log.e(TAG, "playNotificationSound: "+e.getMessage(),e);
        }
    }

    private NotificationManager getManager() {
        Context mContext = contextRef.get();
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }
}