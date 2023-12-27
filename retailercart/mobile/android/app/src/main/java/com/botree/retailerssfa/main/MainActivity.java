/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.async.AsyncDayStart;
import com.botree.retailerssfa.async.AsyncUpload;
import com.botree.retailerssfa.async.AutoUpdateApk;
import com.botree.retailerssfa.async.CodificationAsyncTask;
import com.botree.retailerssfa.async.ScreenOrientation;
import com.botree.retailerssfa.async.TaskCallbacks;
import com.botree.retailerssfa.base.SFAActivity;
import com.botree.retailerssfa.bot.BotActivity;
import com.botree.retailerssfa.bot.backgroundbot.VoiceService;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.controller.retrofit.ApiClient;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.db.FileAccessUtil;
import com.botree.retailerssfa.db.IDbCallback;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.db.query.CodificationHelper;
import com.botree.retailerssfa.firebase.MyFirebaseMessagingService;
import com.botree.retailerssfa.fragmentmanager.ESFAFragTags;
import com.botree.retailerssfa.fragmentmanager.SFAFragmentManager;
import com.botree.retailerssfa.interfaces.SyncUpdateInterface;
import com.botree.retailerssfa.models.LoginModel;
import com.botree.retailerssfa.models.Sync;
import com.botree.retailerssfa.models.TimeCaptureModel;
import com.botree.retailerssfa.models.UserModel;
import com.botree.retailerssfa.service.DayCloseHelper;
import com.botree.retailerssfa.service.DayStartHelper;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.LoggerUtil;
import com.botree.retailerssfa.util.NotificationUtils;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.SFALocation;
import com.botree.retailerssfa.util.SFASharedPref;
import com.crashlytics.android.Crashlytics;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;
import me.leolin.shortcutbadger.ShortcutBadger;

import static androidx.core.content.FileProvider.getUriForFile;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_IS_AUTO_UPDATE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_ADV_ID;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_APP_VERSION;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_CMPCODE_CODE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_DISTR_CODE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_LOGIN_CODE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_SYSTEM_DATE;
import static com.botree.retailerssfa.controller.constants.AppConstant.USER_TYPE_DSR;
import static com.botree.retailerssfa.controller.constants.AppConstant.USER_TYPE_ISR;
import static com.botree.retailerssfa.db.query.IDBColumns.UPLOAD_FLAG;
import static com.botree.retailerssfa.service.JSONConstants.TAG_APP_VERSION;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LOGIN_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LOGIN_TIME_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MESSAGE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MOD_DT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROCESS_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SYNC_DT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SYNC_END_TIME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SYNC_START_TIME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_USER_NAME;
import static com.botree.retailerssfa.support.Globals.NAME_FOCUS_BRAND_MASTER_REPORT;
import static com.botree.retailerssfa.support.Globals.NAME_MUST_SELL_MASTER_REPORT;
import static com.botree.retailerssfa.support.Globals.NAME_PRODUCT_MASTER;
import static com.botree.retailerssfa.support.Globals.NAME_SCHEME_PRODUCTS_REPORT;
import static com.botree.retailerssfa.support.Globals.SCREEN_NAME_PURCHASE_ORDER;
import static com.botree.retailerssfa.util.NotifyUtil.DateTimeNotifyListener;
import static com.botree.retailerssfa.util.NotifyUtil.NotifyListener;
import static com.botree.retailerssfa.util.NotifyUtil.showSnackBar;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_AUTH_TOKEN;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_BOT_SERVICE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CUSTOMER_NAME;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LOGIN_ADV_ID;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LOGIN_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LOGIN_USER_NAME;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_NEWAPPDOWNLOADURL;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_URL;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_USER_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_USER_CREDENTIAL;


public class MainActivity extends SFAActivity implements
        View.OnClickListener, TaskCallbacks, DataManager.APICallBack, MessageFragment.MessageFragmentListener,
        ChangePasswordFragment.EventListener, CodificationAsyncTask.CodificationCompleteListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PENDING_RETAILER_REASON = 1001;
    private static final int SEARCH_SCREEN_NAME = 1002;
    private static final String FRAGMENT_POSITION = "fragmentPosition";
    private static final String IS_TASK_RUNNING = "isTaskRunning";
    private static final String IS_VERSION_DIALOG_SHOWING = "isVersionDialogShowing";
    private static final String IS_NO_COVERAGE_DIALOG_SHOWING = "isNoCoverageDialogShowing";
    private static final String IS_DAY_START_DIALOG_SHOWING = "isDayStartDialogShowing";
    private static final String DOWNLOAD_PROCESS = "Download";
    private static final String UPLOAD_PROCESS = "Upload";
    private static final String LOGOUT_UPLOAD_PROCESS = "LogoutUpload";
    private static final String SCHEME_DOWNLOAD_PROCESS = "Scheme Download";
    private BottomNavigationView navigation;
    private String currentScreenName = "screenName";
    CodificationHelper codificationHelper;
    CodificationAsyncTask.CodificationCompleteListener codificationCompleteListener;
    boolean isPasswordChanged = false;

    private NotifyListener userActiveDialog = new NotifyListener() {
        @Override
        public void onOkClicked() {
            goLogin();
        }

        @Override
        public void onCancelClicked() {
            goLogin();
        }
    };

    private DateTimeNotifyListener datetimeDialog = new DateTimeNotifyListener() {
        @Override
        public void onOkClicked() {
            startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
        }

        @Override
        public void onCancelClicked() {
            goLogin();
        }
    };

    private CoordinatorLayout rootLayout;
    private SFADatabase db;
    private int fragmentPosition;
    private Toolbar toolbar;
    private boolean isDayStartPerformed;
    private boolean isDataAvailable = false;
    private ProgressDialog progressDialog;
    private WeakReference<ScreenOrientation> asyncTask;
    private boolean isTaskRunning = false;
    private boolean isVersionDialogShowing = false;
    private String currentDialogTitle = "";
    private int currentPercentage = 0;

    private NotifyListener versionDialogListener = new NotifyListener() {
        @Override
        public void onOkClicked() {

            if (db.getConfigDataBasedOnName(CONFIG_IS_AUTO_UPDATE).equalsIgnoreCase("Y")) {
                updateAPKFromPlayStore();
                isVersionDialogShowing = false;
            } else {
                SharedPreferences preferences = SFASharedPref.getSharedPreferences(MainActivity.this);
                String url = preferences.getString(PREF_NEWAPPDOWNLOADURL, "");
                new AutoUpdateApk(MainActivity.this, url, MainActivity.this).execute();
                isVersionDialogShowing = false;
            }
        }

        private void updateAPKFromPlayStore() {

            final String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));

            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));

            }
        }

        @Override
        public void onCancelClicked() {
            isVersionDialogShowing = false;
            goLogin();
        }
    };
    private boolean isNoCoverageDialogShowing = false;
    private NotifyListener coverageDialogListener = new NotifyListener() {
        @Override
        public void onOkClicked() {
            onCoverageDialogClose();
        }

        private void onCoverageDialogClose() {
            isNoCoverageDialogShowing = false;
            goLogin();
        }

        @Override
        public void onCancelClicked() {
            onCoverageDialogClose();
        }
    };
    private boolean isDayStartDialogShowing = false;
    private NotifyListener logoutDialogShowListener = new NotifyListener() {
        @Override
        public void onOkClicked() {
//            if (Globals.isConnected(MainActivity.this)) {
                uploadStart(getResources().getString(R.string.uploading_msg), LOGOUT_UPLOAD_PROCESS);
                //            if (!isTaskRunning) {
//                processName = UPLOAD_PROCESS;
//                currTime = getCurrentTime();
//                AsyncUpload asyncUpload = new AsyncUpload(MainActivity.this, MainActivity.this,
//                        sfaSharedPref.readString(PREF_DISTRCODE), isDayStartPerformed, uploadLabel);
//                asyncTask = new WeakReference<ScreenOrientation>(asyncUpload);
//                asyncUpload.execute();
//            }
//            } else {
//
//            }

        }

        /**
         * there is no cancel button in this view
         */
        @Override
        public void onCancelClicked() {
            Log.e(TAG, "onCancelClicked: ");
        }
    };

    private Bundle savedInstanceState;
    private SFASharedPref sfaSharedPref;
    private ProgressDialog pd;
    private String userType;
    private String currTime;
    private String processName;

    private NotifyListener dayStartDialogListener = new NotifyListener() {
        @Override
        public void onOkClicked() {
            isDayStartDialogShowing = false;
            currTime = getCurrentTime();
            performSync();
        }

        @Override
        public void onCancelClicked() {
            isDayStartDialogShowing = false;
            checkDayStartActivity();
            if (!db.isProdAvailable() || !isDayStartPerformed) {
                goLogin();
            }
        }

    };

    /**
     * This method is used to check connection availability and call day start
     */
    private void checkDayStartNetworkConnection() {
        if (Globals.isConnected(MainActivity.this)) {
            dayStart();
        } else {
            showSnackBar(MainActivity.this, rootLayout, getResources().getString(R.string.MSG_NONETCONNECTION), Toast.LENGTH_SHORT);
        }
    }

    /**
     * This method will check the sync is by GPRS or USB connection. If net
     * connection failed show the message. Else call the sync data formation
     * process.
     */
    private void sync() {

        if (isDayStartPerformed) {
            if (Globals.isConnected(MainActivity.this)) {
                uploadStart(getResources().getString(R.string.uploading_msg), UPLOAD_PROCESS);
            } else {
                showSnackBar(MainActivity.this, rootLayout, getResources().getString(R.string.check_internet_connection_try_again),
                        Toast.LENGTH_SHORT);
            }

        } else {
            // if PJP is not planed close the day
            uploadStart(getResources().getString(R.string.uploading_msg), UPLOAD_PROCESS);

        }

    }

    private void performSync() {

        SharedPreferences preferences = SFASharedPref.getSharedPreferences(MainActivity.this);
        String distrCode = preferences.getString(PREF_DISTRCODE, "");
        boolean syncData = db.isSyncDataAvailable(distrCode);

        if (syncData) {
            LoggerUtil.printDebugLog(TAG, "isDayStartPerformed : " + isDayStartPerformed);
            if (isDayStartPerformed) {
                if (Globals.isConnected(MainActivity.this)) {
                    sync();
                } else {
                    showSnackBar(MainActivity.this, rootLayout,
                            getResources().getString(R.string.check_internet_connection_try_again),
                            Snackbar.LENGTH_SHORT);
                }
            } else {
                if (!isPreviousDayClosed()) {
                    //Previous day close performed
                    checkDayStartNetworkConnection();
                } else {
                    // Previous day close not performed
                    sync();
                }
            }
        } else {
            checkDayStartNetworkConnection();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            ESFAFragTags fragTag;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragTag = ESFAFragTags.DASHBOARD_NAVIGATION;
                    fragmentPosition = 0;
                    callFragment(fragTag);
                    return true;

                case R.id.navigation_sync:

                    fragmentPosition = 1;
                    showDayStartDialog();
                    return false;

                case R.id.navigation_settings:

                    fragTag = ESFAFragTags.SETTINGS;
                    fragmentPosition = 2;
                    callFragment(fragTag);
                    return true;

                case R.id.navigation_notifications:

                    fragTag = ESFAFragTags.MESSAGES;
                    callFragment(fragTag);
                    fragmentPosition = 3;
                    return true;

                case R.id.navigation_logout:

                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivityForResult(intent, SEARCH_SCREEN_NAME);
                    overridePendingTransition(R.anim.slide_up, R.anim.stay);
                    fragmentPosition = 4;

                    return false;
                default:
                    break;
            }

            return false;
        }

        private void callFragment(ESFAFragTags fragTags) {
            if (fragTags != null) {
                SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();
                sfaFragManger.clearBackStack(MainActivity.this);
                sfaFragManger.updateFragment(fragTags, MainActivity.this);
            }
        }

    };
    private String screenName = "";
    private BroadcastReceiver mMessageReceiver;
    private BroadcastReceiver mMessageCountRefresh;

    private void dayStart() {
        if (!isTaskRunning) {
            currTime = getCurrentTime();
            processName = DOWNLOAD_PROCESS;
            AsyncDayStart asyncDayStart = new AsyncDayStart(MainActivity.this, MainActivity.this,
                    "DISTR", isDayStartPerformed);
            asyncTask = new WeakReference<ScreenOrientation>(asyncDayStart);
            asyncDayStart.execute();
        }
    }

    private void uploadStart(String uploadLabel, String processName) {
        processAsyncUpload(uploadLabel, processName);

//        if (Globals.isConnected(MainActivity.this)) {
//            if (!isTaskRunning) {
//                processName = UPLOAD_PROCESS;
//                currTime = getCurrentTime();
//                AsyncUpload asyncUpload = new AsyncUpload(MainActivity.this, MainActivity.this,
//                        sfaSharedPref.readString(PREF_DISTRCODE), isDayStartPerformed, uploadLabel);
//                asyncTask = new WeakReference<ScreenOrientation>(asyncUpload);
//                asyncUpload.execute();
//            }
//        } else {
//            showSnackBar(MainActivity.this, rootLayout, getResources().getString(R.string.check_internet_connection_try_again),
//                    Toast.LENGTH_SHORT);
//        }
    }

    void processAsyncUpload(String uploadLabel, String process) {
        if (Globals.isConnected(MainActivity.this)) {
            if (!isTaskRunning) {
                processName = process;
                currTime = getCurrentTime();
                AsyncUpload asyncUpload = new AsyncUpload(MainActivity.this, MainActivity.this,
                        sfaSharedPref.readString(PREF_LOGIN_CODE), isDayStartPerformed, uploadLabel);
                asyncTask = new WeakReference<ScreenOrientation>(asyncUpload);
                asyncUpload.execute();
            }
        } else {
            showSnackBar(MainActivity.this, rootLayout, getResources().getString(R.string.check_internet_connection_try_again),
                    Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        logUser();
        setContentView(R.layout.activity_main);

        setScreenOrientation();
        db = SFADatabase.getInstance(this);
        sfaSharedPref = SFASharedPref.getOurInstance();
        sfaSharedPref.readString(PREF_URL);
        codificationHelper = CodificationHelper.getInstance();
        userType = sfaSharedPref.readString(SFASharedPref.PREF_USER_TYPE);
        codificationCompleteListener = this;

        if (getIntent() != null && getIntent().getStringExtra(currentScreenName) != null) {
            screenName = getIntent().getStringExtra(currentScreenName);
        }
        clearRetailerName();

        initialize();
        initializeBroadcast();
        checkDayStartActivity();

        this.savedInstanceState = savedInstanceState;
        if (savedInstanceState == null) {
            doSavedInstanceEmpty();
        } else {
            LoggerUtil.printDebugLog(TAG, "Inside else condition");
            if (screenName.equalsIgnoreCase(ChangePasswordFragment.class.getSimpleName()) &&
                    Globals.isConnected(this)) {
                displayView(7);
            } else if (screenName.equalsIgnoreCase(MyFirebaseMessagingService.class.getSimpleName())) {
                ESFAFragTags fragTag = ESFAFragTags.MESSAGES;
                SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();
                sfaFragManger.clearBackStack(MainActivity.this);
                sfaFragManger.updateFragment(fragTag, MainActivity.this);
                fragmentPosition = 3;
                navigation.setSelected(true);
            } else {

                if (getScreenOrientation() == 1) {
                    super.goBack();
                }
                fragmentPosition = savedInstanceState.getInt(FRAGMENT_POSITION);
                setSavedInstanceBundle(savedInstanceState);
                processSavedInstanceState(savedInstanceState);
            }
            Log.e("Dashboard Activity", getString(R.string.err_creating_fragment));
        }

        if (SFAFragmentManager.newInstance().getCurrentFragment(this) != null)
            setToolbarTitle(SFAFragmentManager.newInstance().getCurrentFragment(this).getTag());
    }

    /**
     * auto or manual date change clear the retailer name from date change dialog
     */
    private void clearRetailerName() {
        /* auto or manual date change clear the retailer name from date change dialog*/
        String dateChange = "dateChange";
        if (getIntent() != null && getIntent().getStringExtra(dateChange) != null) {
            String close = getIntent().getStringExtra(dateChange);
            if (close.equalsIgnoreCase("closeApp")) {
                sfaSharedPref.writeString(PREF_CUSTOMER_NAME, "");
            }
        }
    }

    private void setScreenOrientation() {
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void doSavedInstanceEmpty() {
        LoggerUtil.printDebugLog(TAG, "screenName : " + screenName);
        if (screenName.equalsIgnoreCase(ChangePasswordFragment.class.getSimpleName()) &&
                Globals.isConnected(this)) {
            displayView(7);
        } else if (screenName.equalsIgnoreCase(MyFirebaseMessagingService.class.getSimpleName())) {
            ESFAFragTags fragTag = ESFAFragTags.MESSAGES;
            SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();
            sfaFragManger.clearBackStack(MainActivity.this);
            sfaFragManger.updateFragment(fragTag, MainActivity.this);

            fragmentPosition = 3;
            navigation.setSelected(true);
        } else {
            if (((!db.isProdAvailable() && !db.isUomAvailable()))) {
                displayView(3);
            } else {

                if (Globals.isConnected(this)) {
                    displayView(3);
                } else {
                    displayView(0);
                }
            }
        }

    }

    private void initializeBroadcast() {
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null && intent.getAction().equals(Globals.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    showBottonMenuCount();
                }

            }
        };
        mMessageCountRefresh = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null && intent.getAction().equals(Globals.PUSH_NOTIFICATION_COUNT)) {
                    // new push notification is received
                    showBottonMenuCount();
                }

            }
        };
    }

    private void processSavedInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState == null)
            return;

        if (savedInstanceState.containsKey(IS_TASK_RUNNING)) {
            isTaskRunning = savedInstanceState.getBoolean(IS_TASK_RUNNING);
            if (isTaskRunning) {
                //noinspection uncheck
                this.asyncTask = (WeakReference<ScreenOrientation>) getLastCustomNonConfigurationInstance();
                if (checkAsynStatusFinished()) {
                    this.asyncTask.get().attach(this);
                }
            }
        } else if (savedInstanceState.containsKey(IS_VERSION_DIALOG_SHOWING)) {

            isVersionDialogShowing = savedInstanceState.getBoolean(IS_VERSION_DIALOG_SHOWING);
            setVersionDialog();
        } else if (savedInstanceState.containsKey(IS_NO_COVERAGE_DIALOG_SHOWING)) {
            isNoCoverageDialogShowing = savedInstanceState.getBoolean(IS_NO_COVERAGE_DIALOG_SHOWING);
            showCoverageDialog();
        } else if (savedInstanceState.containsKey(IS_DAY_START_DIALOG_SHOWING)) {

            if (isUserDsrOrIsr()) {
                setDayStartDialog(savedInstanceState);
            } else {
                displayView(0);
            }
        }

    }

    private void setDayStartDialog(Bundle savedInstanceState) {
        isDayStartDialogShowing = savedInstanceState.getBoolean(IS_DAY_START_DIALOG_SHOWING);
        if (isDayStartDialogShowing)
            showDayStartDialog();
    }

    private void showCoverageDialog() {
        if (isNoCoverageDialogShowing)
            showNoCoverage("No Coverage", "No coverage mapped to you today");
    }

    private void setVersionDialog() {
        if (isVersionDialogShowing)
            versionDialog();
    }

    private boolean checkAsynStatusFinished() {
        return asyncTask != null && this.asyncTask.get() != null
                && !((AsyncTask) this.asyncTask.get()).getStatus().equals(AsyncTask.Status.FINISHED);
    }

    private boolean isUserDsrOrIsr() {
        return !userType.isEmpty() && (isDsrOrIsr());
    }

    private void initialize() {

        if (sfaSharedPref.readBoolean(PREF_BOT_SERVICE)
                && !Globals.isMyServiceRunning(VoiceService.class)) {
            startService(new Intent(MainActivity.this, VoiceService.class));
        }
        // Screen configuration
        DataManager.getInstance().setScreenConfigArrayList(
                db.getOfflineScreenConfig(sfaSharedPref.readString(SFASharedPref.PREF_CMP_CODE)));

        navigation = findViewById(R.id.navigation);
        navigation.getMenu().removeItem(R.id.navigation_logout);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rootLayout = findViewById(R.id.root_main_layout);
        ImageView appImage = findViewById(R.id.menu_icon);
        ImageView appSearchImage = findViewById(R.id.m_app_icon);
        ImageView menuBackBtn = findViewById(R.id.menu_back_icon);
        toolbar = findViewById(R.id.toolbar);
        initToolbar(false);
        appImage.setOnClickListener(this);
        appSearchImage.setOnClickListener(this);
        menuBackBtn.setOnClickListener(this);

        showBottonMenuCount();
    }

    public void showBottonMenuCount() {

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this).inflate(R.layout.bottom_count_item, bottomNavigationMenuView, false);

        TextView msgcountTxt = badge.findViewById(R.id.notifications_badge_txt);

        Integer badgeCount = db.getAllMessageCount();
        msgcountTxt.setText(String.valueOf(badgeCount));

        itemView.addView(badge);
        if (badgeCount != 0 && badgeCount > 0) {
            ShortcutBadger.applyCount(this, badgeCount);
        } else {
            ShortcutBadger.removeCount(this);
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.m_app_icon) {
            Intent intent = new Intent(MainActivity.this, BotActivity.class);
            startActivity(intent);

        } else if (i == R.id.menu_back_icon) {
            goBack();
        }
    }


    @Override
    protected void onPause() {

        if (progressDialog != null && progressDialog.isShowing()
                && (currentDialogTitle.equalsIgnoreCase("Downloading") ||
                currentDialogTitle.equalsIgnoreCase("uploading"))) {
            addProgressDialog(currentDialogTitle, "Loading...");
            progressDialog.dismiss();
            progressDialog = null;
        }

        super.onPause();

    }

    @Override
    protected void onStop() {
        progressDialog = null;
        super.onStop();
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }


    private void addProgressDialog(String title, String msg) {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle(title);
                progressDialog.setMessage(msg);
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setIcon(R.drawable.download);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                // Get the Drawable custom_progressbar
                Drawable customDrawable = ContextCompat.getDrawable(this, R.drawable.custom_progressbar);
                // set the drawable as progress drawavle
                progressDialog.setProgressDrawable(customDrawable);
            }
            if (!progressDialog.isShowing())
                progressDialog.show();


            isTaskRunning = true;
        } catch (Exception e) {
            Log.w("addProgressDialogError", e);
        }
    }

    private void removeProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        isTaskRunning = false;
        asyncTask = null;
    }

    @Override
    public void onPreExecute(String title, String msg) {
        currentDialogTitle = title;
        addProgressDialog(title, msg);

    }

    @Override
    public void onProgressUpdate(int percent) {

        currentPercentage = percent;

        if (progressDialog != null)
            progressDialog.setProgress(percent);
    }

    @Override
    public void onCancelled() {
        removeProgressDialog();
        currentDialogTitle = "";
        currentPercentage = 0;
    }

    @Override
    public void onPostExecute(DayStartHelper.AsyncResponse response) {
        removeProgressDialog();
        currentDialogTitle = "";
        currentPercentage = 0;
        processAsyncResponse(response);
    }

    private void processAsyncResponse(DayStartHelper.AsyncResponse response) {

        try {
            LoggerUtil.printDebugLog(TAG, "processAsyncResponse : response : " + new Gson().toJson(response));
            switch (response) {

                case SERVER_DATE_MISMATCH:
                    insertSyncCaptureTime(processName, getString(R.string.success), getCurrentTime());
                    showDateTimeDialog();
                    break;

                case USER_ACTIVE:
                    Log.d(TAG, "USER_ACTIVE");
                    insertSyncCaptureTime(processName, getString(R.string.success), getCurrentTime());
                    showInActivieUserDialog();
                    break;

                case NEW_APK_AVAILABLE:
                    insertSyncCaptureTime(processName, getString(R.string.success), getCurrentTime());
                    versionDialog();
                    break;
                case NO_COVERAGE_EXISTS:
                    insertSyncCaptureTime(processName, getString(R.string.success), getCurrentTime());
                    showNoCoverage("No Coverage", "No coverage mapped to you today");
                    break;
                case DAY_START_DONE:
                    showSnackBar(this, rootLayout, getResources().getString(R.string.MSG_DLSUCCESS), Snackbar.LENGTH_SHORT);
                    checkDayStartActivity();
                    SFASharedPref.getOurInstance().writeBoolean(SFASharedPref.PREF_STOCK_UNLOADING, false);
                    displayView(0);
                    insertSyncCaptureTime(DOWNLOAD_PROCESS, getString(R.string.success), currTime);

                    break;
                case APK_DOWNLOAD_SUCCESS:
                    installNewApk();
                    break;
                case APK_DOWNLOAD_FAILURE:
                    showSnackBar(this, rootLayout, getResources().getString(R.string.MSG_APK_DL_FAILED), Snackbar.LENGTH_SHORT);
                    goLogin();
                    break;
                case UPLOAD_SUCCESS:
                    showSnackBar(this, rootLayout, getResources().getString(R.string.MSG_UPLOADSUCCESS), Snackbar.LENGTH_SHORT);
                    String process = processName;
                    // send saved login and sync time to server
                    if (!LOGOUT_UPLOAD_PROCESS.equalsIgnoreCase(process)) {
                        process = UPLOAD_PROCESS;
                    }
                    insertSyncCaptureTime(process, getString(R.string.success), currTime);
                    break;
                case UPLOAD_FAILED:
                    showSnackBar(this, rootLayout, getResources().getString(R.string.ERR_SYNCFAIL), Snackbar.LENGTH_SHORT);
                    insertSyncCaptureTime(UPLOAD_PROCESS, getString(R.string.failure), currTime);
                    break;
                case INTERNAL_SERVER_ERROR:
                    insertSyncCaptureTime(processName, getString(R.string.failure), getCurrentTime());
                    showNoCoverage(getResources().getString(R.string.ERR_INTERNAL_SERVER), getResources().getString(R.string.msg_contact_distributor));
                    break;

                case SERVER_UNREACHABLE:
                    insertSyncCaptureTime(processName, getString(R.string.failure), getCurrentTime());
                    if (!isPreviousDayClosed()) {
                        showNoCoverage(getResources().getString(R.string.server_unreachable),
                                getResources().getString(R.string.server_unreach_err_msg));
                    } else {
                        NotifyUtil.showSharedUserExistWithDistributor(this,
                                getResources().getString(R.string.server_unreachable),
                                getResources().getString(R.string.server_unreach_err_msg));
                    }

                    break;

                case DAY_START_ERROR:
                    insertSyncCaptureTime(DOWNLOAD_PROCESS, getString(R.string.failure), currTime);
                    break;
                case AUTH_FAIL:
                    insertSyncCaptureTime(processName, getString(R.string.failure), getCurrentTime());
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.putExtra("AuthError", "401");
                    startActivity(i);
                    pullInLeft();
                    finish();

                    break;
                default:
                    insertSyncCaptureTime(processName, getString(R.string.failure), getCurrentTime());
                    showSnackBar(this, rootLayout, getResources().getString(R.string.ERR_SYNCFAIL), Snackbar.LENGTH_SHORT);
                    displayView(0);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            insertSyncCaptureTime(processName, getString(R.string.failure), getCurrentTime());
        }
    }

    private void downloadDistrReportsJson(String distrDetailsJson) {
        final String currentTime = getCurrentTime();
        showProgressDialog(this, getResources().getString(R.string.MSG_DOWNLOAD_REPORT));
        DataManager.getInstance().getReportDetails(sfaSharedPref.readString(PREF_AUTH_TOKEN), ApiClient.REDIRECT,
                ApiClient.REPORTS_DOWNLOAD, distrDetailsJson, new DataManager.APICallBack() {
                    @Override
                    public void onAPICallback(final String message, boolean isSuccess, AppConstant.RequestType requestType) {
                        if (isSuccess) {
                            deleteDistrReportsTable();
                            Sync sync = Sync.getInstance();
                            SFADatabase database = SFADatabase.getInstance(getApplicationContext());
                            database.insertSyncDistrReportData(sync, new IDbCallback<Boolean>() {
                                @Override
                                public void onSuccess() {
                                    cancleProgressDialog();
                                    NotifyUtil.showSnackBar(getApplicationContext(), rootLayout, "Report Download Success", Snackbar.LENGTH_LONG);
                                }

                                @Override
                                public void onFailed(Exception e) {
                                    cancleProgressDialog();
                                    NotifyUtil.showSnackBar(getApplicationContext(), rootLayout, getString(R.string.report_download_failed), Snackbar.LENGTH_LONG);
                                }

                                @Override
                                public Boolean response(Boolean response) {
                                    return response;
                                }
                            });


                        } else {
                            Log.e(TAG, "JSON Report Download onError:  " + message);
                            cancleProgressDialog();
                            NotifyUtil.showSnackBar(getApplicationContext(), rootLayout, getString(R.string.report_download_failed), Snackbar.LENGTH_LONG);
                        }
                        String strStatus;
                        if (isSuccess) {
                            strStatus = getString(R.string.success);
                        } else {
                            strStatus = getString(R.string.failure);
                        }
                        insertSyncCaptureTime("Report Download", strStatus, currentTime);
                        if (codificationHelper.isCodificationAvailable(db)) {
                            new CodificationAsyncTask(MainActivity.this, codificationCompleteListener).execute();
                        }
                    }
                });
    }

    private void deleteDistrReportsTable() {
        db.removeExistingDistrReportTableInfo();
    }

    public void updateSyncDateTime() {
        if (fragTags == null) {
            displayView(0);
        }
        if (syncUpdateInterface != null)
            syncUpdateInterface.onUpdate(getLastSyncDate());

    }

    private String getLastSyncDate() {
        LoginModel loginModel = db.getSyncDateTime(sfaSharedPref.readString(PREF_CMP_CODE), sfaSharedPref.readString(PREF_LOGIN_CODE));
        try {
            if (loginModel != null) {
                String date = DateUtil.convertTimestampToStringDate(Long.parseLong(loginModel.getSyncDate()));
                String time = DateUtil.convertTimestampToStringTime(Long.parseLong(loginModel.getSyncDate()));

                final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.US);
                final Date dateObj = sdf.parse(time);
                return " " + date + " " + new SimpleDateFormat("hh:mm aa", Locale.US).format(dateObj) + ", " + loginModel.getUserStatus();
            } else {
                return "";
            }
        } catch (final Exception e) {
            Log.d(TAG, "getLastSyncDate : exp : " + e);
            return "";
        }
    }

    private void insertSyncCaptureTime(final String processType, final String status, String currentTime) {
        UserModel userModel = new UserModel();
        userModel.setUserCode(sfaSharedPref.readString(PREF_LOGIN_CODE));

        db.insertUserLoginTime(userModel, "online", processType, status, currentTime);
        updateSyncDateTime();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TAG_LOGIN_TIME_LIST, checkJSONArrayNotNull(addSalesmanLoginTimeList()));
        } catch (Exception e) {
            Log.e(TAG, "insertSyncCaptureTime: " + e.getMessage(), e);
        }

        DataManager.getInstance().getUploadProcessCaptureTime(sfaSharedPref.readString(PREF_AUTH_TOKEN),sfaSharedPref.readString(PREF_LOGIN_CODE), ApiClient.CONTROLLER,
                ApiClient.UPLOAD, jsonObject, new DataManager.APICallBack() {
                    @Override
                    public void onAPICallback(String message, boolean isSuccess, AppConstant.RequestType requestType) {
                        if (isSuccess) {
                            TimeCaptureModel timeCaptureModel = new Gson().fromJson(message, TimeCaptureModel.class);
                            updateSyncLogStatus(timeCaptureModel);
                        } else {
                            Log.e(TAG, " Upload onError:  " + message);
                        }
                        if (UPLOAD_PROCESS.equalsIgnoreCase(processType) && getString(R.string.success).equalsIgnoreCase(status)) {
                            dayStart();
                        }
                        if (DOWNLOAD_PROCESS.equalsIgnoreCase(processType) && getString(R.string.success).equalsIgnoreCase(status)) {
                            downloadSchemeDetails(getDistrDetailsJson());
                        }
//                        if (SCHEME_DOWNLOAD_PROCESS.equalsIgnoreCase(processType)) {
//                            downloadDistrReportsJson(getDistrDetailsJson());
//                        }
                        if (LOGOUT_UPLOAD_PROCESS.equalsIgnoreCase(processType)) {
                            DataManager.getInstance().performLogout(ApiClient.LOGIN_REDIRECT, ApiClient.LOGOUT,
                                    new String[]{REQ_LOGIN_CODE, REQ_ADV_ID},
                                    new String[]{sfaSharedPref.readString(PREF_LOGIN_CODE), sfaSharedPref.readString(PREF_LOGIN_ADV_ID, "")}, new DataManager.APICallBack() {
                                        @Override
                                        public void onAPICallback(String message, boolean isSuccess, AppConstant.RequestType requestType) {
//                                            if (isSuccess) {
//                                                LoginModel model = new Gson().fromJson(message, LoginModel.class);
//                                                if ("success".equalsIgnoreCase(model.getMessage())) {
//                                                    if (isPasswordChanged) {
//                                                        sfaSharedPref.writeString(PREF_LOGIN_USER_NAME, "");
//                                                        sfaSharedPref.writeString(PREF_USER_CREDENTIAL, "");
//                                                        db.deleteLoggedInUser();
//                                                    }sfaSharedPref.writeString(PREF_LOGIN_ADV_ID, "");
//                                                    goLogin();
//                                                } else {
//                                                    showSnackBar(MainActivity.this, rootLayout, getResources().getString(R.string.logout_failure), Toast.LENGTH_SHORT);
//                                                }
//                                                LoggerUtil.printDebugLog(TAG, " Logout Success:  " + message);
//                                            } else {
//                                                showSnackBar(MainActivity.this, rootLayout, getResources().getString(R.string.logout_failure), Toast.LENGTH_SHORT);
//                                                LoggerUtil.printErrorLog(TAG, " Logout onError:  " + message);
//                                            }
                                            if (isPasswordChanged) {
                                                sfaSharedPref.writeString(PREF_LOGIN_USER_NAME, "");
                                                sfaSharedPref.writeString(PREF_USER_CREDENTIAL, "");
                                                db.deleteLoggedInUser();
                                            }sfaSharedPref.writeString(PREF_LOGIN_ADV_ID, "");
                                            goLogin();
                                        }
                                    });
                        }
                    }
                });
    }

    private String checkJSONArrayNotNull(JSONArray jsonArray) throws IOException {
        if (jsonArray != null)
            return AppUtils.getOurInstance().compressGZIP(jsonArray.toString());
        else
            return "";
    }

    private boolean isPreviousDayClosed() {
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(MainActivity.this);
        String yesterday = DateUtil.getPreviousDate();
        return db.checkPreviousDayCloseFail(preferences.getString(PREF_DISTRCODE, ""), yesterday);

    }

    private String getDistrDetailsJson() {
        try {
            String loginCode = sfaSharedPref.readString(PREF_LOGIN_CODE);
            String appVersion = BuildConfig.VERSION_NAME;
            String systemDate = android.text.format.DateFormat.format("yyyy-MM-dd", new Date()).toString();

            JSONObject jsonObject = new JSONObject();

            jsonObject.put(REQ_LOGIN_CODE, loginCode);
            jsonObject.put(REQ_APP_VERSION, appVersion);
            jsonObject.put(REQ_SYSTEM_DATE, systemDate);

            if (jsonObject.length() == 0) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.ERR_SYNCFAIL), Toast.LENGTH_SHORT).show();
                return null;
            }

            return jsonObject.toString();

        } catch (Exception e) {
            Log.w(TAG, e);
        }

        return null;
    }

    /**
     * used to fetch the retailer scheme in customer wise GZIP Data
     *
     * @param userDetailsJson request json
     */
    private void downloadSchemeDetails(final String userDetailsJson) {
        final String currentTime = getCurrentTime();
        showProgressDialog(this, getResources().getString(R.string.MSG_DOWNLOAD_SHEMES));
        DataManager.getInstance().getSchemeDetails(sfaSharedPref.readString(PREF_AUTH_TOKEN),sfaSharedPref.readString(PREF_LOGIN_CODE), ApiClient.REDIRECT,
                ApiClient.DOWNLOAD_SCHEME_DETAILS, userDetailsJson, new DataManager.APICallBack() {
                    @Override
                    public void onAPICallback(final String message, boolean isSuccess, AppConstant.RequestType requestType) {
                        if (!isSuccess) {
                            Log.e(TAG, "Scheme Download onError:  " + message);
                            NotifyUtil.showSnackBar(getApplicationContext(), rootLayout, message, Snackbar.LENGTH_LONG);
                        }
                        cancleProgressDialog();
                        String strStatus;
                        if (isSuccess) {
                            strStatus = getString(R.string.success);
                        } else {
                            strStatus = getString(R.string.failure);
                        }
                        insertSyncCaptureTime(SCHEME_DOWNLOAD_PROCESS, strStatus, currentTime);
                    }
                });

    }

    private String getCurrentTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return tf.format(c);
    }

    private void updateSyncLogStatus(TimeCaptureModel responseFromServer) {
        for (TimeCaptureModel timeCaptureModel : responseFromServer.getSyncLogList()) {
            db.uploadSyncLog(timeCaptureModel);
        }
    }

    // Fetch the login capture date time list Array
    private JSONArray addSalesmanLoginTimeList() throws JSONException {

        JSONArray jsonArray = new JSONArray();

        List<TimeCaptureModel> modelList = db.getSalesmanLoginTime(sfaSharedPref.readString(PREF_CMP_CODE), sfaSharedPref.readString(PREF_LOGIN_CODE));

        for (TimeCaptureModel loginModel : modelList) {
            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, loginModel.getCmpCode());
            jObj.put(TAG_LOGIN_CODE, loginModel.getUserCode());
            jObj.put(TAG_SYNC_DT, loginModel.getSyncDate());
            jObj.put(TAG_SYNC_START_TIME, loginModel.getSyncTime());
            jObj.put(TAG_MODE, loginModel.getMode());
            jObj.put(TAG_PROCESS_NAME, loginModel.getProcessName());
            jObj.put(TAG_USER_NAME, loginModel.getUserName());
            jObj.put(TAG_MESSAGE, loginModel.getStatus());
            jObj.put(TAG_APP_VERSION, BuildConfig.VERSION_NAME);
            jObj.put(TAG_MOD_DT, System.currentTimeMillis());
            jObj.put(UPLOAD_FLAG, "N");
            jObj.put(TAG_SYNC_END_TIME, loginModel.getSyncEndTime());
            jsonArray.put(jObj);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray getJsonArrayLength(JSONArray jsonArray) {
        if (jsonArray.length() > 0)
            return jsonArray;
        else
            return null;
    }

    private void cancleProgressDialog() {
        try {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void showProgressDialog(Activity activity, String message) {
        try {
            pd = new ProgressDialog(activity);
            pd.setMessage(message);
            pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            if (!pd.isShowing()) {
                pd.show();
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }


    private void installNewApk() {
        ComponentName comp;
        if (android.os.Build.VERSION.SDK_INT < 23) {
            comp = new
                    ComponentName("com.android.packageinstaller",
                    "com.android.packageinstaller.PackageInstallerActivity");

        } else {
            comp = new
                    ComponentName("com.google.android.packageinstaller",
                    "com.android.packageinstaller.PackageInstallerActivity");
        }
        Intent intent;
        if (android.os.Build.VERSION.SDK_INT > 25) {
            intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        } else {
            intent = new Intent(Intent.ACTION_VIEW);
        }

        if (android.os.Build.VERSION.SDK_INT >= 24) {

            Uri contentUri = getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", createImageFile());
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            File sdcard = FileAccessUtil.getInstance().getExternalStorageDirectory();
            intent.setDataAndType(Uri.fromFile(new File(sdcard, AutoUpdateApk.INSTALL_NEW_APK_LOCATION)),
                    "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!

        }

        if (android.os.Build.VERSION.SDK_INT < 25) {
            intent.setComponent(comp);
        }

        startActivity(intent);
    }

    private File createImageFile() {

        File sdcard = FileAccessUtil.getInstance().getExternalStorageDirectory();
        File myDir = new File(sdcard, AutoUpdateApk.NEW_APK_LOCATION);
        return new File(myDir, "Sfa.apk");
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {

        WeakReference<ScreenOrientation> weakReference1 = null;
        if (checkAsynStatusFinished()) {
            weakReference1 = this.asyncTask;
        }
        return weakReference1;
    }

    ESFAFragTags fragTags = null;

    private void displayView(int position) {

        switch (position) {
            case 0:
                fragTags = ESFAFragTags.DASHBOARD_NAVIGATION;
                fragmentPosition = 0;
                break;
            case 1:
                fragTags = ESFAFragTags.NEW_OUTLET_LIST;
                fragmentPosition = 1;
                break;
            case 3:
                fragmentPosition = 3;
                performSync();
                return;
            case 4:
                fragTags = ESFAFragTags.SETTINGS;
                fragmentPosition = 4;
                break;
            case 5:
                fragTags = ESFAFragTags.MESSAGES;
                fragmentPosition = 5;
                break;

            case 6:
                fragmentPosition = 6;
                showLogoutDialog();
                return;

            case 7:
                fragTags = ESFAFragTags.CHANGE_PASSWORD;
                fragmentPosition = 7;
                break;

            default:
                break;
        }

        if (fragTags != null) {

            SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();
            sfaFragManger.clearBackStack(MainActivity.this);
            if (fragmentPosition == 7) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("FROM_LOGIN", true);
                sfaFragManger.updateFragment(fragTags, false, MainActivity.this, bundle);
            } else {
                sfaFragManger.updateFragment(fragTags, MainActivity.this);
            }
        }
        updateSyncDateTime();
    }

    private void goLogin() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        pullInLeft();
        finish();
    }


    private void checkDayStartActivity() {
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(this);
        String distrCode = preferences.getString(PREF_DISTRCODE, "");
        String today = DateUtil.getCurrentDate();
        if (db.isDataAvailable(distrCode, today)) {
            isDayStartPerformed = db.checkDayStartPerformed(distrCode, today);
        }

        isDataAvailable = ((db.isProdAvailable() && db.isUomAvailable()));
    }

    private void showDayStartDialog() {
        isDayStartDialogShowing = true;
        NotifyUtil.showDialog(this, "Sync", getString(R.string.MSG_Perform_sync),
                dayStartDialogListener, isDayStartPerformed, isDataAvailable);
    }

    @Override
    public void onBackPressed() {

        goBack();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (progressDialog == null && (currentDialogTitle.equalsIgnoreCase("Downloading") ||
                currentDialogTitle.equalsIgnoreCase("uploading"))) {
            addProgressDialog(currentDialogTitle, "Loading...");
            progressDialog.setProgress(currentPercentage);
        }

        showBottonMenuCount();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Globals.PUSH_NOTIFICATION));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageCountRefresh,
                new IntentFilter(Globals.PUSH_NOTIFICATION_COUNT));

        NotificationUtils.clearNotifications(getApplicationContext());
    }

    public int getScreenOrientation() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        int orientation;
        if (screenWidth == screenHeight) {
            orientation = Configuration.ORIENTATION_UNDEFINED;
        } else {
            if (screenWidth < screenHeight) {
                orientation = Configuration.ORIENTATION_PORTRAIT;
            } else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        Log.d("orientation", Integer.toString(orientation));
        return orientation;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int i = item.getItemId();
        if (i == R.id.action_login) {
            showLogoutDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SFALocation.REQUEST_CHECK_SETTINGS) {

            SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();

            sfaFragManger.getCurrentFragment(this).onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (requestCode == PENDING_RETAILER_REASON) {
            if (resultCode == RESULT_OK) {
                uploadStart(getResources().getString(R.string.uploading_msg), UPLOAD_PROCESS);
                return;
            } else {
                goLogin();
                return;
            }
        }

        if (requestCode == SEARCH_SCREEN_NAME) {
            startSearchScreenIntent(resultCode, data);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startSearchScreenIntent(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            screenName = data.getStringExtra("MESSAGE");
            startSearchScreenIntent1();
        }
    }

    private void startSearchScreenIntent1() {
        if (screenName.equalsIgnoreCase(NAME_PRODUCT_MASTER)) {
            Intent in = new Intent(this, ProductSchFocusMustSellMasterActivity.class);
            in.putExtra(SCREEN_NAME_PURCHASE_ORDER, NAME_PRODUCT_MASTER);
            startActivity(in);
            overridePendingTransition(R.anim.slide_up, R.anim.stay);
        } else if (screenName.equalsIgnoreCase(NAME_SCHEME_PRODUCTS_REPORT)) {
            Intent in = new Intent(this, ProductSchFocusMustSellMasterActivity.class);
            in.putExtra(SCREEN_NAME_PURCHASE_ORDER, NAME_SCHEME_PRODUCTS_REPORT);
            startActivity(in);
            overridePendingTransition(R.anim.slide_up, R.anim.stay);
        } else if (screenName.equalsIgnoreCase(NAME_FOCUS_BRAND_MASTER_REPORT)) {
            Intent in = new Intent(this, ProductSchFocusMustSellMasterActivity.class);
            in.putExtra(SCREEN_NAME_PURCHASE_ORDER, NAME_FOCUS_BRAND_MASTER_REPORT);
            startActivity(in);
            overridePendingTransition(R.anim.slide_up, R.anim.stay);
        } else if (screenName.equalsIgnoreCase(NAME_MUST_SELL_MASTER_REPORT)) {
            Intent in = new Intent(this, ProductSchFocusMustSellMasterActivity.class);
            in.putExtra(SCREEN_NAME_PURCHASE_ORDER, NAME_MUST_SELL_MASTER_REPORT);
            startActivity(in);
            overridePendingTransition(R.anim.slide_up, R.anim.stay);
        }
    }

    private boolean isDsrOrIsr() {
        return USER_TYPE_DSR.equalsIgnoreCase(userType) || USER_TYPE_ISR.equalsIgnoreCase(userType);
    }

    private void updateFragment(ESFAFragTags fragTags, String fragmentName) {
        if (fragTags != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(FRAGMENT_POSITION, fragmentPosition);
            Log.d(TAG, "updateFragment: " + fragmentName);
            bundle.putString("fragmentName", fragmentName);
            bundle.putString(currentScreenName, DashboardNavigationFragment.class.getSimpleName());
            SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();
            if (sfaFragManger.updateFragment(fragTags, false, MainActivity.this, bundle)) {
                Log.e("DashboardActivity", "creating fragment");
            } else {
                Log.e("DashboardActivity", "Error in creating fragment");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(FRAGMENT_POSITION, fragmentPosition);
        outState.putBoolean(IS_TASK_RUNNING, isTaskRunning);
        outState.putBoolean(IS_VERSION_DIALOG_SHOWING, isTaskRunning);
        outState.putBoolean(IS_NO_COVERAGE_DIALOG_SHOWING, isNoCoverageDialogShowing);
        outState.putBoolean(IS_DAY_START_DIALOG_SHOWING, isDayStartDialogShowing);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragmentPosition = savedInstanceState.getInt(FRAGMENT_POSITION);
        isTaskRunning = savedInstanceState.getBoolean(IS_TASK_RUNNING);

        isVersionDialogShowing = savedInstanceState.getBoolean(IS_VERSION_DIALOG_SHOWING);
        isNoCoverageDialogShowing = savedInstanceState.getBoolean(IS_NO_COVERAGE_DIALOG_SHOWING);
        isDayStartDialogShowing = savedInstanceState.getBoolean(IS_DAY_START_DIALOG_SHOWING);

    }

    /**
     * New apk download dialog
     */
    private void versionDialog() {
        isVersionDialogShowing = true;
        NotifyUtil.showDialog(this, "New Apk", getResources().getString(R.string.MSG_NEW_APK), versionDialogListener, isDayStartPerformed, isDataAvailable);

    }

    private void showNoCoverage(String errorTitle, String errorMessage) {
        isNoCoverageDialogShowing = true;
        NotifyUtil.showDialog(this, errorTitle, errorMessage, coverageDialogListener, isDayStartPerformed, isDataAvailable);

    }

    private void showInActivieUserDialog() {
        NotifyUtil.showDialog(this, "Alert", "User is inactive, Please contact your distributor", userActiveDialog, isDayStartPerformed, isDataAvailable);

    }


    private void showDateTimeDialog() {
        NotifyUtil.showDateTimeDialog(this, "Alert", "Please set current date and time in your mobile and try again.", datetimeDialog);

    }

    public void showLogoutDialog() {
        if (Globals.isConnected(MainActivity.this)) {
            NotifyUtil.showDialog(MainActivity.this, "Logout", getString(R.string.MSG_Logout), logoutDialogShowListener, isDayStartPerformed, isDataAvailable);
        } else {
            goLogin();
//            NotifyUtil.showSnackBar(this, rootLayout, getString(R.string.check_internet_connection_try_again), Snackbar.LENGTH_LONG);
        }
    }

    public void hideBottomNevigation() {

        if (navigation != null) {
            navigation.setVisibility(View.GONE);
        }
    }

    public void showBottomNevigation() {

        if (navigation != null) {
            navigation.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.e("fragment changed", "");
    }


    @Override
    public void onAPICallback(String message, boolean isSuccess, AppConstant.RequestType requestType) {

        if (requestType == AppConstant.RequestType.SALES_HIERARCHY_DATA) {
            saveSalesHierarchyData(message, isSuccess);


        } else if (requestType == AppConstant.RequestType.SALES_HIERARCHY_VALUE) {
            saveSalesHierarchyValue(message, isSuccess);
        }

    }


    private void saveSalesHierarchyData(String message, boolean isSuccess) {

        if (isSuccess) {

            db.deleteSalesHierachyData();
            db.insertSalesHierarchyData(DataManager.getInstance().getSalesHierarchyArrayList());
        } else {
            Log.d(TAG, "onAPICallback : " + message);
            NotifyUtil.showSnackBar(getApplicationContext(), rootLayout, message, Snackbar.LENGTH_LONG);
        }
    }

    private void saveSalesHierarchyValue(String message, boolean isSuccess) {
        if (isSuccess) {
            db.deleteSalesHierachyValue();
            db.insertSalesHierarchyValue(DataManager.getInstance().getSalesHierarchyValueList());
            cancleProgressDialog();
        } else {
            cancleProgressDialog();
            Log.d(TAG, "onAPICallback : " + message);
            NotifyUtil.showSnackBar(getApplicationContext(), rootLayout, message, Snackbar.LENGTH_LONG);
        }
    }

    @Override
    public void onMessageHandled() {
        showBottonMenuCount();
    }


    @Override
    public void onPasswordSkipCompleted() {
        if (db.isLoggedInUserAvailable(sfaSharedPref.readString(PREF_LOGIN_CODE))) {//false - if user not available
            LoginModel loginModel = db.getLoggedInUserBasedOnId(sfaSharedPref.readString(PREF_LOGIN_CODE),
                    sfaSharedPref.readString(PREF_USER_CREDENTIAL));
            if (loginModel != null && loginModel.getNewPassword().equals("N")) {
                checkDayStartActivity();
                processSavedInstanceState(savedInstanceState);

//                if (!db.isProdAvailable()) {
                    performSync();
//                }
            }
        }
    }

    @Override
    public void onPasswordChangeCompleted() {
        isPasswordChanged = true;
        uploadStart(getResources().getString(R.string.uploading_msg), LOGOUT_UPLOAD_PROCESS);
    }

    private SyncUpdateInterface syncUpdateInterface = null;

    public void setOnSyncStatusUpdateListener(SyncUpdateInterface syncStatusUpdateListener) {
        syncUpdateInterface = syncStatusUpdateListener;
    }

    @Override
    public void onCodificationCompleted(boolean success) {
        if (success) {
            final DayCloseHelper dayCloseHelper = new DayCloseHelper(getApplicationContext());
            String request = dayCloseHelper.getCodificationUploadJson(sfaSharedPref.readString(PREF_DISTRCODE)).toString();

            DataManager.getInstance().getUploadCodification(sfaSharedPref.readString(PREF_AUTH_TOKEN), ApiClient.CONTROLLER,
                    ApiClient.UPLOAD, request, new DataManager.APICallBack() {
                        @Override
                        public void onAPICallback(String message, boolean isSuccess, AppConstant.RequestType requestType) {
                            if (isSuccess) {
                                DayStartHelper.AsyncResponse response = dayCloseHelper.updateCodificationUploadStatus(message, true);
                                if (response == DayStartHelper.AsyncResponse.UPLOAD_SUCCESS) {
                                    showSnackBar(MainActivity.this, rootLayout, getResources().getString(R.string.codification_success), Toast.LENGTH_SHORT);
                                } else {
                                    showSnackBar(MainActivity.this, rootLayout, getResources().getString(R.string.codification_failure), Toast.LENGTH_SHORT);
                                }
                            } else {
                                Log.e(TAG, " Upload onError:  " + message);
                            }
                        }
                    });
        }
    }
}