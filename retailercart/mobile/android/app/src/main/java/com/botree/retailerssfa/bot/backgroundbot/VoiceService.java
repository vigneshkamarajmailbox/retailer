package com.botree.retailerssfa.bot.backgroundbot;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.botree.retailerssfa.bot.BotActivity;
import com.botree.retailerssfa.main.LoginActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;


public class VoiceService extends Service implements
        RecognitionListener {

    public static final String BROADCAST_ACTION = "com.botree.broadcast";
    private static final String LOG_TAG = VoiceService.class.getSimpleName();
    /* Named searches allow to quickly reconfigure the decoder */
    private static final String KWS_SEARCH = "wakeup";
    /* Keyword we are looking for to activate menu */
    private static final String KEYPHRASE = "ami";
    private static final String TAG = VoiceService.class.getSimpleName();
    Intent intent;
    private SpeechRecognizer recognizer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            runRecognizerSetup();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("StaticFieldLeak")
    private void runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(VoiceService.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result == null) {
                    switchSearch(KWS_SEARCH);
                }
            }
        }.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }

    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;
        String inputText = hypothesis.getHypstr();
        if (inputText.equals(KEYPHRASE)) {
            recognizer.stop();
        }
    }

    /**
     * This callback is called when we stop the recognizer.
     */
    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            String inputText = hypothesis.getHypstr();
            if (inputText.equals(KEYPHRASE)) {
                playNotificationSound();
                switchSearch(KWS_SEARCH);

                try {

                    boolean foregroud = new ForegroundCheckTask().execute(VoiceService.this).get();

                    ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                    assert am != null;
                    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

                    if (!foregroud) {
                        Intent dialogIntent = new Intent(this, LoginActivity.class);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(dialogIntent);

                    } else if (cn.getClassName().equalsIgnoreCase("com.botree.productsfa.bot.BotActivity")) {

                        sendResult(inputText);

                    } else {

                        callIntent(inputText, cn);

                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResult: " + e.getMessage(),e);
                }
            }
        }
    }

    /**
     * method helps to call intent based on the call name
     * @param inputText result input text
     * @param componentName current component name
     */
    private void callIntent(String inputText, ComponentName componentName) {
        if (!componentName.getClassName().equalsIgnoreCase("com.botree.productsfa.main.LoginActivity")) {
            Intent dialogIntent = new Intent(this, BotActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(dialogIntent);
        } else if (componentName.getClassName().equalsIgnoreCase("com.botree.productsfa.bot.BotActivity")) {
            sendResult(inputText);
        } else {
            Toast.makeText(getApplicationContext(), "Please Login and Search", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBeginningOfSpeech() {
         //ignore
    }

    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech() {
        if (!recognizer.getSearchName().contains(KWS_SEARCH)) {
            switchSearch(KWS_SEARCH);
        }
    }

    private void switchSearch(String searchName) {
        recognizer.stop();
        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        if (searchName.contains(KWS_SEARCH))
            recognizer.startListening(searchName);
        else
            recognizer.startListening(searchName, 10000);
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them
        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                .setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                .setKeywordThreshold(1e-1f)  // 1e-45f  Threshold to tune for keyphrase to balance between false alarms and misses
                .setBoolean("-allphone_ci", true) // Use context-independent phonetic search, context-dependent is too slow for mobile
                .getRecognizer();

        recognizer.addListener(this);

        /* In your application you might not need to add all those searches.
         * They are added here for demonstration. You can leave just one.
         */

        // Create keyword-activation search.
        File digitsGrammar = new File(assetsDir, "keywords.gram");
        recognizer.addKeywordSearch(KWS_SEARCH, digitsGrammar);

    }

    @Override
    public void onError(Exception error) {
        Log.i(LOG_TAG, "onError " + error.getMessage());
    }

    @Override
    public void onTimeout() {
        switchSearch(KWS_SEARCH);
    }


    // Playing notification sound
    private void playNotificationSound() {
        try {

            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + VoiceService.this.getPackageName() + "/raw/voicetone");
            Ringtone r = RingtoneManager.getRingtone(VoiceService.this, alarmSound);
            r.play();
        } catch (Exception e) {
            Log.e(TAG, "playNotificationSound: " + e.getMessage(),e);
        }
    }

    public void sendResult(String message) {
        intent.putExtra("message", message);
        sendBroadcast(intent);
    }

    @SuppressLint("StaticFieldLeak")
    class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... params) {
            final Context context = params[0].getApplicationContext();
            return isAppOnForeground(context);
        }

        private boolean isAppOnForeground(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            assert activityManager != null;
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            final String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }
}
