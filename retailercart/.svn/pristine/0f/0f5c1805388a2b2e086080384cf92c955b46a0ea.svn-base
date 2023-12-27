package com.botree.retailerssfa.firebase;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.main.LoginActivity;
import com.botree.retailerssfa.main.MainActivity;
import com.botree.retailerssfa.models.MessageModel;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.NotificationUtils;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;

import static com.botree.retailerssfa.service.DayStartHelper.isJsonNotNull;
import static com.botree.retailerssfa.service.JSONConstants.TAG_BODY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DETAIL;
import static com.botree.retailerssfa.service.JSONConstants.TAG_FEEDBACK_MESSAGE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_FILE_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ID;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TITLE;
import static com.botree.retailerssfa.support.Globals.fromHtml;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    public static final String MESSAGE = "message";
    public static final String SCREEN_NAME = "screenName";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                handleDataMessage(remoteMessage);
                Log.e(TAG, "Message data payload: " + remoteMessage.getData());

            }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }
        } catch (Exception e) {
            Log.e(TAG, "onMessageReceived: " + e.getMessage(), e);
        }


    }


    private void handleDataMessage(RemoteMessage remoteMessage) {

        try {
            String timestamp = String.valueOf(remoteMessage.getSentTime());

            String id = "";
            String title = "";
            String message = "";
            String filename = "";
            String messageDetails = "";
            JSONObject jsonObject = new JSONObject(remoteMessage.getData().get(TAG_BODY));
            if (isJsonNotNull(jsonObject, TAG_ID))
                id = jsonObject.getString(TAG_ID);
            if (isJsonNotNull(jsonObject, TAG_TITLE))
                title = jsonObject.getString(TAG_TITLE);
            if (isJsonNotNull(jsonObject, TAG_FEEDBACK_MESSAGE))
                message = String.valueOf(fromHtml(jsonObject.getString(TAG_FEEDBACK_MESSAGE)));
            if (isJsonNotNull(jsonObject, TAG_FILE_NAME))
                filename = jsonObject.getString(TAG_FILE_NAME);
            if (isJsonNotNull(jsonObject, TAG_DETAIL))
                messageDetails = jsonObject.getString(TAG_DETAIL);
            SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
            MessageModel messageModels = new MessageModel();
            messageModels.setCmpCode(sfaSharedPref.readString(SFASharedPref.PREF_CMP_CODE));
            messageModels.setDistCode(sfaSharedPref.readString(SFASharedPref.PREF_DISTRCODE));
            messageModels.setSalesmanCode(sfaSharedPref.readString(SFASharedPref.PREF_SALESMANCODE));
            messageModels.setMsgCode(id);
            messageModels.setMsgTitle(title);
            messageModels.setMsgBody(message);
            messageModels.setMessageDetail(messageDetails);
            messageModels.setFileName(filename);
            messageModels.setMsgDate(timestamp);
            messageModels.setMsgStatus("1");
            SFADatabase.getInstance(this).insertMessage(messageModels);

            refreshShortCutBadgeCount();

            Intent resultIntent;
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message

                List<ActivityManager.RunningTaskInfo> runningActivity = isRunningActivity(getApplicationContext());
                if (runningActivity.isEmpty()) {
                    resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                    resultIntent.putExtra(MESSAGE, message);
                    resultIntent.putExtra(SCREEN_NAME, MyFirebaseMessagingService.class.getSimpleName());
                } else {
                    // Get the info we need for comparison.
                    ComponentName componentInfo = runningActivity.get(0).topActivity;
                    Log.e(TAG, "handleDataMessage: " + componentInfo.getPackageName());
                    if (componentInfo.getClassName().equals("com.botree.productsfa.main.LoginActivity")) {
                        resultIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        resultIntent.putExtra(MESSAGE, message);
                        resultIntent.putExtra(SCREEN_NAME, MyFirebaseMessagingService.class.getSimpleName());

                    } else {
                        resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                        resultIntent.putExtra(MESSAGE, message);
                        resultIntent.putExtra(SCREEN_NAME, MyFirebaseMessagingService.class.getSimpleName());
                    }
                    Intent pushNotification = new Intent(Globals.PUSH_NOTIFICATION);
                    pushNotification.putExtra("NotificationData", messageModels);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                }
            } else {
                // app is in background, show the notification in notification tray
                resultIntent = new Intent(getApplicationContext(), LoginActivity.class);
                resultIntent.putExtra(MESSAGE, message);
                resultIntent.putExtra(SCREEN_NAME, MyFirebaseMessagingService.class.getSimpleName());

            }
            // check for image attachment
            showNotificationMessage(title, message, timestamp, resultIntent);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.getMessage(), e);
        }
    }

    private void refreshShortCutBadgeCount() {
        Integer badgeCount = SFADatabase.getInstance(this).getAllMessageCount();
        if (badgeCount != 0 && badgeCount > 0) {
            ShortcutBadger.applyCount(this, badgeCount);
        } else {
            ShortcutBadger.removeCount(this);
        }
    }

    public List<ActivityManager.RunningTaskInfo> isRunningActivity(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

        assert activityManager != null;
        return activityManager.getRunningTasks(Integer.MAX_VALUE);
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(String title, String message, String timeStamp, Intent intent) {
        NotificationUtils notificationUtils = NotificationUtils.getNotifyInstance();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }
}
