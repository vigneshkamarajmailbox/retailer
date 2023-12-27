package com.botree.retailerssfa.bot;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.base.BaseActivity;
import com.botree.retailerssfa.bot.backgroundbot.VoiceService;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.speechview.RecognitionProgressView;
import com.botree.retailerssfa.speechview.adapters.RecognitionListenerAdapter;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import static com.botree.retailerssfa.support.Globals.isMyServiceRunning;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_BOT_SERVICE;


public class BotActivity extends BaseActivity implements View.OnClickListener,
        TextToSpeech.OnInitListener, DataManager.APICallBack {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;
    private static final int SPEECH_REQUEST_CODE = 0;
    private static final String TAG = BotActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private int loggedInUserID;
    private ArrayList<ChatModel> chatModelList;
    private RecyclerView.LayoutManager mLayoutManager;
    private int msgCount = 0;
    private String msg = "";
    private TextToSpeech tts;
    private String[] yourArray = new String[]{"Hi", "Good Morning", "Hai", "Tell me", "Hello", "hey"};
    private String[] userNameArray = new String[]{"what is my name", "user name", "who am i", "whats my name", "my name"};
    private String[] myNameArray = new String[]{"what is your name", "you are name", "your name", "you name", "help", "guide", "guide me"};
    private String[] exitArray = new String[]{"close the app", "exit the app", "exit the application", "close the applicaiton", "exit app", "exit application", "close application"};
    private ChatAdapter chatAdapter;
    private EditText msgEditTxt;
    private Toolbar toolbar;
    private SpeechRecognizer speechRecognizer = null;
    private RelativeLayout micRLayout;
    private LinearLayout editViewlnLayout;
    private SFASharedPref sfaSharedPref;
    private String userNameStr;
    private BroadcastReceiver receiver;
    private PowerManager.WakeLock wl;

    /**
     * @param errorCode for speech error code
     * @return string message
     */
    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sfaSharedPref = SFASharedPref.getOurInstance();
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        initializeTextSpech();
        initializedToolBar();
        initialized();

        if (savedInstanceState != null) {
            chatModelList = savedInstanceState.getParcelableArrayList("key");
            Toast.makeText(this, "" + chatModelList, Toast.LENGTH_LONG).show();

            if (chatModelList != null)
                groupDataIntoHashMap(chatModelList);
        } else {
            getData(msg);
        }
    }

    private void initializeTextSpech() {

        tts = new TextToSpeech(this, this, "com.google.android.tts");
        float pitch = (float) 0.6;
        tts.setSpeechRate(1);
        tts.setPitch(pitch);
    }

    private void initialized() {

        userNameStr = sfaSharedPref.readString(SFASharedPref.PREF_USER_NAME);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        chatModelList = new ArrayList<>();

        recyclerView = findViewById(R.id.chat_recyclerviwe);
        msgEditTxt = findViewById(R.id.message_edit);
        micRLayout = findViewById(R.id.mic_view_rlayout);
        editViewlnLayout = findViewById(R.id.edit_txt_lnlayout);
        ImageView sendImgBtn = findViewById(R.id.send_img);
        ImageView micBtn = findViewById(R.id.mic_img_btn);
        sendImgBtn.setOnClickListener(this);
        micBtn.setOnClickListener(this);
        bindRecyclerView();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String stringExtra = intent.getStringExtra("message");
                if ("ami".equals(stringExtra)) {
                    stopeBotService();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        showSpeechView();
                    } else {
                        displaySpeechRecognizer();
                    }

                }
            }

        };
    }

    private void stopeBotService() {
        if (isMyServiceRunning(VoiceService.class)) {
            stopService(new Intent(getApplicationContext(), VoiceService.class));
        }
    }

    private void initializedToolBar() {
        toolbar = findViewById(R.id.custom_toolbar);
        initToolbar();
        setBaseToolbarTitle("Ami", null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(VoiceService.BROADCAST_ACTION));
    }

    private void getData(String msg) {

        String keyword = "";
        if (msg != null && msg.equalsIgnoreCase("")) {
            msg = "Hi " + userNameStr + ", I am ami,\n How can I help you...!";

        } else if (msg != null && AppUtils.getOurInstance().covertArrayValuesToLowerCase(yourArray)
                .contains(msg.toLowerCase(Locale.getDefault()))) {

            msg = "Hi " + userNameStr + "...\nWhat can i do for you.";

        } else if (msg != null && AppUtils.getOurInstance().covertArrayValuesToLowerCase(userNameArray)
                .contains(msg.toLowerCase(Locale.getDefault()))) {
            msg = "Your name is " + userNameStr;

        } else if (msg != null && AppUtils.getOurInstance().covertArrayValuesToLowerCase(myNameArray)
                .contains(msg.toLowerCase(Locale.getDefault()))) {

            msg = "My name is ami and i am here to help you. Please use the following Keywords to search the item >  Sales, Collection, Target and Achieved, Route Detail and MTD.";

        } else if (msg != null && Arrays.asList(msg.toUpperCase(Locale.getDefault()).split(" ")).contains("TIME")) {

            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("h:mm a");
            String curTime = df.format(Calendar.getInstance().getTime());

            msg = "The time is " + curTime;

        } else if (msg != null && Arrays.asList(msg.toUpperCase(Locale.getDefault()).split(" ")).contains("DATE")) {
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            msg = "The date is " + currentDateTimeString;

        } else {
            try {
                assert msg != null;
                String[] splitArray = msg.toLowerCase(Locale.getDefault()).split("\\s+");
                findMetrics(splitArray);
            } catch (PatternSyntaxException ex) {
                Log.e(TAG, "getData: " + ex.getLocalizedMessage());
            }
        }

        ChatModel chatModel = new ChatModel("" + msgCount, 1, keyword, "shan", msg, new Date());
        chatModelList.add(chatModel);
        msgCount++;
        groupDataIntoHashMap(chatModelList);

        speakOut(msg);
    }

    private void findMetrics(String[] splitArray) {
        /*String metrics = db.getMetricsKey(splitArray);
        if (metrics != null && metrics.equalsIgnoreCase("sales")) {
            if (msg != null) {
                if (msg.toLowerCase(Locale.getDefault()).contains("last")) {
                    msg = db.getLastDWMY(msg);
                } else if (!db.getSalesData(msg).isEmpty()) {
                    msg = (db.getSalesData(msg));
                } else {
                    msg = "I am unable to understand, Please type help and use the keywords";
                }
            } else {
                msg = "I am unable to understand, Please type help and use the keywords";
            }
        } else {
            msg = "I am unable to understand, Please type help and use the keywords";
        }*/
    }

    private boolean isGraph(String msg) {

        Boolean found = (Arrays.asList(msg.toUpperCase(Locale.getDefault()).split(" ")).contains("GRAPH") ||
                Arrays.asList(msg.toUpperCase(Locale.getDefault()).split(" ")).contains("CHART"));
        if (Boolean.TRUE.equals(found)) {
            Log.i(TAG, "isGraph: " + "Keyword matched the string");
        }

        return found;
    }

    private void bindRecyclerView() {
        chatAdapter = new ChatAdapter(this, null);
        chatAdapter.setUser(loggedInUserID);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatAdapter);
    }

    private void groupDataIntoHashMap(List<ChatModel> chatModelList) {
        LinkedHashMap<String, Set<ChatModel>> groupedHashMap = new LinkedHashMap<>();
        Set<ChatModel> list;
        for (ChatModel chatModel : chatModelList) {
            String hashMapKey = DateUtil.getOurInstance().covertUtilDateIntoDatebaseDateString(chatModel.getChatTime(), "dd/MM/yyyy");
            if (groupedHashMap.containsKey(hashMapKey)) {
                // The key is already in the HashMap; add the pojo object
                // against the existing key.
                groupedHashMap.get(hashMapKey).add(chatModel);
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                list = new LinkedHashSet<>();
                list.add(chatModel);
                groupedHashMap.put(hashMapKey, list);
            }
        }
        //Generate list from map
        generateListFromMap(groupedHashMap);
    }

    private void generateListFromMap(LinkedHashMap<String, Set<ChatModel>> groupedHashMap) {
        // We linearly add every item into the consolidatedList.
        List<ListObject> consolidatedList = new ArrayList<>();
        for (Iterator<String> iterator = groupedHashMap.keySet().iterator(); iterator.hasNext(); ) {
            String date = iterator.next();
            DateObject dateItem = new DateObject();
            dateItem.setDate(date);
            consolidatedList.add(dateItem);
            for (ChatModel chatModel : groupedHashMap.get(date)) {
                ChatModelObject generalItem = new ChatModelObject();
                generalItem.setChatModel(chatModel);
                consolidatedList.add(generalItem);
            }
        }
        chatAdapter.setDataChange(consolidatedList);
        mLayoutManager.scrollToPosition(consolidatedList.size() - 1);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.send_img) {
            String inputStr = msgEditTxt.getText().toString();

            if (!inputStr.isEmpty()) {

                insetDataInObject(inputStr);
            } else {
                Toast.makeText(this, "Feed the input", Toast.LENGTH_SHORT).show();
            }


        } else if (i == R.id.mic_img_btn) {
            stopeBotService();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                showSpeechView();
            } else {
                displaySpeechRecognizer();
            }
        }
    }

    private void insetDataInObject(String input) {

        loggedInUserID = 2;
        chatAdapter.setUser(loggedInUserID);
        msg = input;
        ChatModel chatModel = new ChatModel("" + msgCount, loggedInUserID, "shan", "shan", msg, new Date());
        chatModelList.add(chatModel);
        msgCount++;
        groupDataIntoHashMap(chatModelList);
        msgEditTxt.setText("");
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(msg);
            }
        }, 500);

        if (micRLayout.getVisibility() == View.VISIBLE) {
            micRLayout.setVisibility(View.GONE);
        }
        if (editViewlnLayout.getVisibility() != View.VISIBLE) {
            editViewlnLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        if (wl != null) {
            Log.v(TAG, "Releasing wakelock");
            try {
                if (wl.isHeld())
                    wl.release();
            } catch (Exception th) {
                Log.e(TAG, "onStop: " + th.getMessage(), th);
            }
        } else {
            Log.e(TAG, "Wakelock reference is null");
        }

        startBotSerive();
        unregisterReceiver(receiver);
        super.onStop();
    }

    public void showSpeechView() {
        if (editViewlnLayout.getVisibility() == View.VISIBLE) {
            editViewlnLayout.setVisibility(View.GONE);
        }
        if (micRLayout.getVisibility() != View.VISIBLE) {
            micRLayout.setVisibility(View.VISIBLE);
        }

        int[] colors = {
                ContextCompat.getColor(this, R.color.color8),
                ContextCompat.getColor(this, R.color.color9),
                ContextCompat.getColor(this, R.color.color10),
                ContextCompat.getColor(this, R.color.color11),
                ContextCompat.getColor(this, R.color.color12)
        };

        int[] heights = {20, 24, 18, 23, 16};

        final RecognitionProgressView recognitionProgressView = findViewById(R.id.bot_recognition_view);
        recognitionProgressView.setSpeechRecognizer(speechRecognizer);
        recognitionProgressView.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onResults(Bundle results) {
                showResults(results);
            }

            @Override
            public void onError(int errorCode) {
                String errorMessage = getErrorText(errorCode);
                Log.d(TAG, "FAILED " + errorMessage);
                startBotSerive();
            }

            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.i(TAG, "onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.i(TAG, "onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float rmsdB) {
                Log.i(TAG, "onRmsChanged");

            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                Log.i(TAG, "onBufferReceived");
            }

            @Override
            public void onEndOfSpeech() {
                Log.i(TAG, "onEndOfSpeech");
                recognitionProgressView.stop();
                if (micRLayout.getVisibility() == View.VISIBLE) {
                    micRLayout.setVisibility(View.GONE);
                }
                if (editViewlnLayout.getVisibility() != View.VISIBLE) {
                    editViewlnLayout.setVisibility(View.VISIBLE);
                }
                startBotSerive();
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                Log.i(TAG, "onPartialResults");
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                Log.i(TAG, "onEvent");
            }
        });
        recognitionProgressView.setColors(colors);
        recognitionProgressView.setBarMaxHeightsInDp(heights);
        recognitionProgressView.setCircleRadiusInDp(3);
        recognitionProgressView.setSpacingInDp(3);
        recognitionProgressView.setIdleStateAmplitudeInDp(3);
        recognitionProgressView.setRotationRadiusInDp(15);
        recognitionProgressView.play();

        if (ContextCompat.checkSelfPermission(BotActivity.this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            startRecognition();
        }
    }

    private void startBotSerive() {
        if (sfaSharedPref.readBoolean(PREF_BOT_SERVICE)
                && !isMyServiceRunning(VoiceService.class)) {
            startService(new Intent(getApplicationContext(), VoiceService.class));
        }
    }

    private void startRecognition() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Do something for lollipop and above versions
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 20000000);
            speechRecognizer.startListening(intent);

        } else {
            // do something for phones running an SDK before naught
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_prompt));
            try {
                startActivityForResult(intent, SPEECH_REQUEST_CODE);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.speech_not_supported),
                        Toast.LENGTH_SHORT).show();
            }
        }


    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);

            insetDataInObject(spokenText);

            Log.e("voice to string", spokenText);
            startBotSerive();
            // Do something with spokenText
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showResults(Bundle results) {

        if (micRLayout.getVisibility() == View.VISIBLE) {
            micRLayout.setVisibility(View.GONE);
        }
        if (editViewlnLayout.getVisibility() != View.VISIBLE) {
            editViewlnLayout.setVisibility(View.VISIBLE);
        }

        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        if (!matches.get(0).isEmpty() && matches.get(0).length() > 0) {
            insetDataInObject(matches.get(0));

            startBotSerive();
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(this, "Requires RECORD AUDIO permission", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION_CODE);
        }
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        if (chatModelList != null) {
            chatModelList.clear();
        }

        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut(msg);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut(String message) {

        if (tts == null) {
            tts = new TextToSpeech(this, this, "com.google.android.tts");
            float pitch = (float) 0.6;
            tts.setSpeechRate(1);
            tts.setPitch(pitch);
        }

        if (AppUtils.getOurInstance().covertArrayValuesToLowerCase(exitArray)
                .contains(message.toLowerCase(Locale.getDefault())) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finish();
            this.finishAffinity();
            System.exit(0);
        }

        tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        //ignored
    }

    @Override
    public void onSaveInstanceState(Bundle outState,
                                    PersistableBundle outPersistentState) {
        outState.putParcelableArrayList("key", chatModelList);
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public Toolbar getBaseToolbar() {
        return toolbar;
    }

    @Override
    public void onAPICallback(String message, boolean isSuccess,
                              AppConstant.RequestType requestType) {

        if (requestType == AppConstant.RequestType.CHAT_RESULTS) {
            Log.e(TAG, "onAPICallback: " + message);

            insetDataInObject(message);

        }
    }
}
