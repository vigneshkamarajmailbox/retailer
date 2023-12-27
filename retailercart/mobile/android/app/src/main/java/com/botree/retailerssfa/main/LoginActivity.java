package com.botree.retailerssfa.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.backgroundservices.LocationUpdateService;
import com.botree.retailerssfa.base.SFAActivity;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.controller.retrofit.ApiClient;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.LoginModel;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.support.LocaleHelper;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.LoggerUtil;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.PermissionUtil;
import com.botree.retailerssfa.util.SFASharedPref;
import com.crashlytics.android.Crashlytics;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

import static com.botree.retailerssfa.R.id.forgot_password;
import static com.botree.retailerssfa.R.id.notification_checkbox;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_ADV_ID;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_DEVICE_BRAND;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_DEVICE_MODEL;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_DEVICE_VERSION;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_FCM_KEY;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_LOGIN_CODE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_PASS_STR;
import static com.botree.retailerssfa.util.SFASharedPref.CURRENT_SESSION;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_AUTH_TOKEN;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_ISNOTIFICATION_ON;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LOGIN_ADV_ID;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LOGIN_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LOGIN_MESSAGE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LOGIN_STATUS;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_NEW_PASS;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_REMEMBER_CREDENTIAL;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SELECTED_LANGUAGE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_USER_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_USER_CREDENTIAL;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_USER_NAME;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_USER_STATUS;

public class LoginActivity extends SFAActivity implements DataManager.APICallBack {
    final String TAG = LoginActivity.class.getSimpleName();

    private SFADatabase db;
    private SFASharedPref sfaSharedPref;
    private PermissionUtil permissionUtil;

    private RelativeLayout fingerPrintLayout;
    private ProgressBar customProgressBar;
    private RelativeLayout trasLayout;
    private Spinner langSpinner;
    private RelativeLayout loginLayout;
    private EditText userName;
    private EditText mEdtPassword;
    private LinearLayout loginScreen;
    private CheckBox rememberPassCheckBox;
    private TextView mDebugTxt;
    TextView loginLabelTxt;
    Button fingerPrintBtn;
    Button loginBtn;
    CheckBox notificationCheckbox;
    TextView forgotPassword;

    private Animation shake;
    private String language = "en";
    LoginModel loginModel;
    private PowerManager.WakeLock wl;
    private static final String SCREEN_NAME_TAG = "screenName";
    private String screenName = "";
    private static final String ALERT_TAG = "Alert";
    private String currentTime;
    private NotifyUtil.DateTimeNotifyListener newUserDialogShowListener = new NotifyUtil.DateTimeNotifyListener() {
        @Override
        public void onOkClicked() {
            LoggerUtil.printDebugLog(TAG, "newUserDialogShowListener");
//            if (Globals.isMyServiceRunning(LocationUpdateService.class)) {
//                // TO Stop Van Sales Odometer Service while switching to different login
//                stopService(new Intent(getApplicationContext(), LocationUpdateService.class));
//                SFASharedPref.getOurInstance().writeInt(CURRENT_SESSION, 0);
//            }
//            db.removeExistingTableData();
////            db.removeExistingReportTableInfo();
//            currentTime = getCurrentTime();
//            insertUserDetails(loginModel);
//            saveLoginInfo(loginModel);
//            processScreenConfigResponse("", true);
//            uploadStart("Transaction data is uploading, Please wait...");
            db.removeExistingTableData();
            LoggerUtil.printDebugLog(TAG, "newUserDialogShowListener");
            if (Globals.isMyServiceRunning(LocationUpdateService.class)) {
                // TO Stop Van Sales Odometer Service while switching to different login
                stopService(new Intent(getApplicationContext(), LocationUpdateService.class));
                SFASharedPref.getOurInstance().writeInt(CURRENT_SESSION, 0);
            }
            LoggerUtil.printDebugLog(TAG, "newUserDialogShowListener");

//                db.removeExistingReportTableInfo();
            currentTime = getCurrentTime();
            insertUserDetails(loginModel);
            saveLoginInfo(loginModel);
            LoggerUtil.printDebugLog(TAG, "newUserDialogShowListener");
            processScreenConfigResponse("", true);
            LoggerUtil.printDebugLog(TAG, "newUserDialogShowListener");
        }

        @Override
        public void onCancelClicked() {
            //ignored
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        Fabric.with(this, new Crashlytics());
        logUser();

        sfaSharedPref = SFASharedPref.getOurInstance();
        setCurrentlySelectedLang();
        db = SFADatabase.getInstance(this);
        Globals.setUrl(LoginActivity.this);

//        LocaleHelper.setLocale(this, sfaSharedPref.readString(PREF_SELECTED_LANGUAGE));
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        changeStatusBarColor();
        initializeViews();

        if (BuildConfig.DEBUG) {
            displayFirebaseRegId();
        }

        permissionUtil = new PermissionUtil(this);
        permissionUtil.checkPermission();
    }

    private void initializeViews() {
        langSpinner = findViewById(R.id.language_spinner);
        fingerPrintBtn = findViewById(R.id.fingerprint_img_btn);
        fingerPrintLayout = findViewById(R.id.finger_print_layout);
        customProgressBar = findViewById(R.id.custom_loging_progressbar);
        trasLayout = findViewById(R.id.trans_layout);
        trasLayout.setOnClickListener(null);
        trasLayout.setOnLongClickListener(null);
        loginLayout = findViewById(R.id.rootview);
        userName = findViewById(R.id.username_edt);
        mEdtPassword = findViewById(R.id.password_edt);
        loginScreen = findViewById(R.id.login_screen_layer);
        loginBtn = findViewById(R.id.login_btn);
        TextView versionTxt = findViewById(R.id.version_txt);
        loginLabelTxt = findViewById(R.id.login_label_txt);
        rememberPassCheckBox = findViewById(R.id.rem_password_checkbox);
        mDebugTxt = findViewById(R.id.debug_txt);
        notificationCheckbox = findViewById(notification_checkbox);
        forgotPassword = findViewById(forgot_password);
        boolean isNotificationOn = sfaSharedPref.readBoolean(PREF_ISNOTIFICATION_ON);
        notificationCheckbox.setChecked(isNotificationOn);

        versionTxt.setText(String.format("%s %s", getString(R.string.app_version), BuildConfig.VERSION_NAME));
        loginLabelTxt.setText(R.string.login);

        if (sfaSharedPref.readString(PREF_SELECTED_LANGUAGE).equalsIgnoreCase("hi")) {
            langSpinner.setSelection(1);
        } else {
            langSpinner.setSelection(0);
        }

        assignClickListeners();
        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos == 0) {
                    language = "en";
                    LocaleHelper.setLocale(getBaseContext(), language);

                } else if (pos == 1) {
                    language = "hi";
                    LocaleHelper.setLocale(getBaseContext(), language);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // ignored
            }
        });

        if (loginModel != null)
            if (db.getUserCount(loginModel.getLoginCode()) > 0) {
                isFingerPrintAvailInDevice();
            }
    }

    void assignClickListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(mEdtPassword);

                if (checkFields()) {
                    performLoginButtonClick(userName.getText().toString().trim(), mEdtPassword.getText().toString().trim());
                }
            }
        });

        mEdtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean mHandled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (checkFields()) {
                        performLoginButtonClick(userName.getText().toString().trim(), mEdtPassword.getText().toString().trim());
                    }
                    mHandled = true;
                }
                return mHandled;
            }
        });

        loginScreen.setOnClickListener(null);

        notificationCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //is checkbox checked?
                if (((CheckBox) v).isChecked()) {
                    sfaSharedPref.writeBoolean(PREF_ISNOTIFICATION_ON, Boolean.TRUE);
                } else {
                    sfaSharedPref.writeBoolean(PREF_ISNOTIFICATION_ON, Boolean.FALSE);
                }
            }
        });

        fingerPrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FingerPrintActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void performLoginButtonClick(String username, String password) {
        Log.e(TAG, "performLoginButtonClick: username : " + username);
        Log.e(TAG, "performLoginButtonClick: password : " + password);
        Log.e(TAG, "performLoginButtonClick: adId : " + sfaSharedPref.readString(PREF_LOGIN_ADV_ID));
        currentTime = getCurrentTime();

//        if (!sfaSharedPref.readString(PREF_LOGIN_ADV_ID).isEmpty()) {
//            if (db.isLoggedInUserAvailable(username)) {//false - if user not available
        if (Globals.isConnected(LoginActivity.this)) {
            try {
                checkLoginFromServer(username, password);
            } catch (Exception e) {
                Log.e(TAG, "onClick: " + e.getMessage(), e);
            }
        } else {
            performOfflineLogin(username, password);
            sfaSharedPref.writeBoolean(SFASharedPref.PREF_IS_ON_OFF_USER, false);
        }
//            } else {
//                NotifyUtil.showDialog(this, ALERT_TAG, getString(R.string.logout_previous_user));
//            }
//        } else {
//            if (Globals.isConnected(LoginActivity.this)) {
//                try {
//                    checkLoginFromServer(username, password);
//                } catch (Exception e) {
//                    Log.e(TAG, "onClick: " + e.getMessage(), e);
//                }
//            } else {
//                NotifyUtil.showSnackBar(this, loginLayout, getString(R.string.check_internet_connection_try_again), Snackbar.LENGTH_LONG);
//            }
//        }
    }

    private String getCurrentTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return tf.format(c);
    }

    private void performOfflineLogin(String usrCode, String pwd) {
        if (db.isLoggedInUserAvailable(usrCode)) {//false - if user not available
            loginModel = db.getLoggedInUserBasedOnId(usrCode, pwd);
            if (loginModel != null) {
                checkOfflineLogin(loginModel);
            } else {
                loginScreen.startAnimation(shake);
                NotifyUtil.showSnackBar(this, loginLayout, getString(R.string.check_internet_connection_try_again), Snackbar.LENGTH_LONG);
            }
        } else {
            loginScreen.startAnimation(shake);
            NotifyUtil.showSnackBar(this, loginLayout, getString(R.string.check_internet_connection_try_again), Snackbar.LENGTH_LONG);
        }
    }

    private void checkOfflineLogin(LoginModel loginModel) {
        saveLoginInfo(loginModel);
        db.insertUserLoginTime(loginModel, "offline", "Login", "Success", getCurrentTime());
        String today = DateUtil.getCurrentDate();
        LoggerUtil.printInfoLog(TAG, "checkOfflineLogin: Today : " + today);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(SCREEN_NAME_TAG, screenName);
        startActivity(intent);
        finish();
    }

    /**
     * Method is used to login based on server response using retrofit method
     */
    private void checkLoginFromServer(String username, String passCode) {
        showProgress();
        String fcmToken = sfaSharedPref.readString(SFASharedPref.PREF_FCM_TOKEN);
        String loginToken = sfaSharedPref.readString(PREF_LOGIN_ADV_ID, "");
        Log.e(TAG, "checkLoginFromServer: " + fcmToken);
        DataManager.getInstance().getLoginResponse(ApiClient.LOGIN_REDIRECT, ApiClient.LOGIN,
                new String[]{REQ_LOGIN_CODE, REQ_PASS_STR, REQ_FCM_KEY, REQ_DEVICE_BRAND, REQ_DEVICE_MODEL,
                        REQ_DEVICE_VERSION, REQ_ADV_ID},
                new String[]{username, passCode, fcmToken, Build.BRAND, Build.MODEL, Build.VERSION.RELEASE, loginToken}, this);
    }

    private void showProgress() {
        if (customProgressBar.getVisibility() != View.VISIBLE) {
            customProgressBar.setVisibility(View.VISIBLE);
            trasLayout.setVisibility(View.VISIBLE);
        }
    }

    private void displayFirebaseRegId() {
        String fcmToken = sfaSharedPref.readString(SFASharedPref.PREF_FCM_TOKEN);
        Log.e(TAG, "Firebase reg id: " + fcmToken);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
        String baseUrl = getResources().getString(R.string.BASE_URL_RELEASE);
        if (BuildConfig.DEBUG) {
//            baseUrl = getResources().getString(R.string.BASE_URL_DEBUG);
            baseUrl = "Retailer SSFA - UAT";
            mDebugTxt.setText(baseUrl);
            mDebugTxt.setVisibility(View.GONE);
        } else
            mDebugTxt.setVisibility(View.GONE);
        Log.e(TAG, "URL BASE_URL_TEST: " + baseUrl + " | " + BuildConfig.FLAVOR);
    }

    public static boolean isFieldEmpty(String name) {
        return name != null && name.length() == 0;
    }

    private boolean checkFields() {
        if (isFieldEmpty(userName.getText().toString())) {
            userName.startAnimation(shake);
            NotifyUtil.showSnackBar(LoginActivity.this, loginLayout, "Enter user name", Snackbar.LENGTH_LONG);

            return false;
        } else if (isFieldEmpty(mEdtPassword.getText().toString())) {
            mEdtPassword.startAnimation(shake);
            NotifyUtil.showSnackBar(LoginActivity.this, loginLayout, "Enter Password", Snackbar.LENGTH_LONG);

            return false;
        } else {
            return true;
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * check whether the finger print hardware is available or not
     */
    private void isFingerPrintAvailInDevice() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
            if (fingerprintManager != null && fingerprintManager.isHardwareDetected()) {

                fingerPrintLayout.setVisibility(View.VISIBLE);

            } else {
                // Everything is ready for fingerprint authentication
                fingerPrintLayout.setVisibility(View.GONE);
            }
        } else {
            fingerPrintLayout.setVisibility(View.GONE);
        }
    }

    private void setCurrentlySelectedLang() {
        if (sfaSharedPref.readString(PREF_SELECTED_LANGUAGE).equalsIgnoreCase("hi")) {
            String lang = sfaSharedPref.readString(PREF_SELECTED_LANGUAGE);
            LocaleHelper.setLocale(getBaseContext(), lang);
        } else {
            LocaleHelper.setLocale(getBaseContext(), language);
        }
    }

    private void changeStatusBarColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permissionUtil == null)
            permissionUtil = new PermissionUtil(this);
        int response = permissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (response == 1) {
            NotifyUtil.showSnackBar(this, loginLayout, "All Permission is Accepted", Snackbar.LENGTH_LONG);
        } else if (response == -1) {
            NotifyUtil.showSnackBar(this, loginLayout, "Some Permission is Denied", Snackbar.LENGTH_LONG);
            finish();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onAPICallback(String message, boolean isSuccess, AppConstant.RequestType requestType) {
        if (requestType == AppConstant.RequestType.LOGIN) {
            processLoginResponse(message, isSuccess);
        } else if (requestType == AppConstant.RequestType.SCREEN_CONFIGURATION) {
            screenConfigResponse(message, isSuccess);
        }
    }

    private void screenConfigResponse(String message, boolean isSuccess) {
        if (isSuccess) {
            db.deleteScreenConfiguration();
            db.insertScreenConfig(DataManager.getInstance().getScreenConfigArrayList());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            if (loginModel.getNewPassword().equalsIgnoreCase("Y")) {
                screenName = ChangePasswordFragment.class.getSimpleName();
            }
            intent.putExtra(SCREEN_NAME_TAG, screenName);
            startActivity(intent);
            finish();
            cancelProgress();
        } else {
            cancelProgress();
            loginScreen.startAnimation(shake);
            Log.d(TAG, "onAPICallback : " + message);
            NotifyUtil.showSnackBar(getApplicationContext(), loginLayout, message, Snackbar.LENGTH_LONG);
        }
    }

    private void processLoginResponse(String message, boolean isSuccess) {
        cancelProgress();
        if (isSuccess) {
            Log.e(TAG, "processLoginResponse : message : " + message);
            loginModel = new Gson().fromJson(message, LoginModel.class);
//            loginModel = new Gson().fromJson(Utils.getJsonFromRaw(getApplicationContext(),R.raw.login_response), LoginModel.class);
            if (loginModel.isLoginStatus() && loginModel.getMessage().equalsIgnoreCase("success")) {
                checkIsUserExist(loginModel);
            } else {
                loginScreen.startAnimation(shake);
                if (loginModel.getMessage() == null || loginModel.getMessage().isEmpty()) {
                    NotifyUtil.showSnackBar(getApplicationContext(), loginLayout, "Please contact Administrator.", Snackbar.LENGTH_LONG);
                } else {
                    if (loginModel.getMessage().toLowerCase().contains("User already logged in".toLowerCase())) {
                        NotifyUtil.showDialog(this, ALERT_TAG, "User has already logged in another device.\n" +
                                "Brand : " + loginModel.getDeviceBrand() + "\n" +
                                "Model : " + loginModel.getDeviceModel() + "\n" +
                                "Logout from old device to continue login in new device.");
                    } else {
                        NotifyUtil.showSnackBar(getApplicationContext(), loginLayout, loginModel.getMessage(), Snackbar.LENGTH_LONG);
                    }
                }
            }
        } else {
            loginScreen.startAnimation(shake);
            NotifyUtil.showSnackBar(getApplicationContext(), loginLayout, getResources().getString(R.string.server_unreach_err_msg), Snackbar.LENGTH_SHORT);
            Log.e(TAG, "message failure : " + message);
        }
    }

    private void checkIsUserExist(LoginModel loginModel) {
        if (!db.istUserAvailable()) {//false - if user not available
            db.removeExistingTableData();
            insertUserDetails(loginModel);
            saveLoginInfo(loginModel);
            processScreenConfigResponse("", true);
        } else {
            if (db.getUserCount(loginModel.getLoginCode()) > 0) {
                insertUserDetails(loginModel);
                saveLoginInfo(loginModel);
                processScreenConfigResponse("", true);
            } else {
                NotifyUtil.showDateTimeDialog(this, ALERT_TAG, getString(R.string.new_user_err), newUserDialogShowListener);

//                insertUserDetails(loginModel);
//                saveLoginInfo(loginModel);
//                processScreenConfigResponse("", true);
            }
        }
    }

    private void insertUserDetails(LoginModel loginModel) {
        sfaSharedPref.writeString(PREF_LOGIN_ADV_ID, loginModel.getAdvId());
        showProgress();
        db.deleteLoggedInUser();
        db.insertLoggedInUser(loginModel);
        db.insertUserLoginTime(loginModel, "online", "Login", "Success", currentTime);

        sfaSharedPref.writeString(PREF_AUTH_TOKEN, loginModel.getToken());
        LoggerUtil.printDebugLog(TAG, "Access Token: " + loginModel.getToken());

    }

    private void saveLoginInfo(LoginModel loginModel) {
        sfaSharedPref.writeString(PREF_CMP_CODE, loginModel.getCmpCode());
        sfaSharedPref.writeString(PREF_USER_CODE, loginModel.getLoginCode());
        sfaSharedPref.writeString(PREF_LOGIN_CODE, loginModel.getLoginCode());
        sfaSharedPref.writeString(PREF_USER_CREDENTIAL, loginModel.getPassword());
        sfaSharedPref.writeString(PREF_DISTRCODE, loginModel.getDistrCode());
        sfaSharedPref.writeString(PREF_USER_NAME, loginModel.getUserName());
        sfaSharedPref.writeString(PREF_USER_STATUS, loginModel.getUserStatus());
        sfaSharedPref.writeString(PREF_NEW_PASS, loginModel.getNewPassword());
        sfaSharedPref.writeBoolean(PREF_LOGIN_STATUS, loginModel.isLoginStatus());
        sfaSharedPref.writeString(PREF_LOGIN_MESSAGE, loginModel.getMessage());

        if (rememberPassCheckBox.isChecked()) {
            sfaSharedPref.writeBoolean(PREF_REMEMBER_CREDENTIAL, Boolean.TRUE);
        } else {
            sfaSharedPref.writeBoolean(PREF_REMEMBER_CREDENTIAL, Boolean.FALSE);
        }
        LoggerUtil.printDebugLog(TAG, "saveLoginInfo : pwd : " + sfaSharedPref.readString(PREF_USER_CREDENTIAL));
    }

    private void cancelProgress() {
        if (customProgressBar.getVisibility() == View.VISIBLE) {
            customProgressBar.setVisibility(View.GONE);
            trasLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        wakeUp();

        boolean remPass = sfaSharedPref.readBoolean(PREF_REMEMBER_CREDENTIAL);
        if (remPass) {
            rememberPassCheckBox.setChecked(true);
            userName.setText(sfaSharedPref.readString(PREF_LOGIN_CODE));
            mEdtPassword.setText(sfaSharedPref.readString(PREF_USER_CREDENTIAL));
        } else {
            rememberPassCheckBox.setChecked(false);
            userName.setText(sfaSharedPref.readString(PREF_LOGIN_CODE));
            mEdtPassword.setText("");
        }
    }

    private void wakeUp() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        assert pm != null;
        boolean result = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH && pm.isInteractive()
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH && pm.isScreenOn();

        if (!result) {
            wl = pm.newWakeLock(
                    PowerManager.SCREEN_DIM_WAKE_LOCK
                            | PowerManager.ON_AFTER_RELEASE, TAG);
            wl.acquire(60 * 1000L /*10 minutes*/);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (wl != null) {
            Log.v(TAG, "Releasing wakelock");
            try {

                if (wl.isHeld())
                    wl.release();
            } catch (Exception th) {
                // ignoring this exception, probably wakeLock was already released
            }
        } else {
            // should never happen during normal workflow
            Log.e(TAG, "Wakelock reference is null");
        }
    }

    private void processScreenConfigResponse(String message, boolean isSuccess) {
//        if (isSuccess) {
//            db.deleteScreenConfiguration();
//            String jsonString = AppUtils.getOurInstance().readTextFileFromLocal("screenConfigList.json");
//            try {
//                db.insertScreenConfig(AppUtils.getOurInstance().getConvertedListFromJson(new JSONArray(jsonString),
//                        ScreenConfig.class));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            if (loginModel.getNewPassword().equalsIgnoreCase("Y")) {
//                screenName = ChangePasswordFragment.class.getSimpleName();
//            }
//            intent.putExtra(SCREEN_NAME_TAG, screenName);
//            startActivity(intent);
//            finish();
//            cancelProgress();
//        } else {
//            cancelProgress();
//            loginScreen.startAnimation(shake);
//            Log.d(TAG, "onAPICallback : " + message);
//            NotifyUtil.showSnackBar(getApplicationContext(), loginLayout, message, Snackbar.LENGTH_LONG);
//        }
        DataManager.getInstance().getScreenConfigurationDetails(sfaSharedPref.readString(PREF_AUTH_TOKEN), sfaSharedPref.readString(PREF_LOGIN_CODE),
                ApiClient.REDIRECT, ApiClient.SCREEN_CONFIGURATION,
                new String[]{REQ_LOGIN_CODE},
                new String[]{sfaSharedPref.readString(PREF_LOGIN_CODE)}, this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fingerPrintResultLogic(resultCode, data);
    }

    private void fingerPrintResultLogic(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                String username = sfaSharedPref.readString(PREF_LOGIN_CODE);
                String password = sfaSharedPref.readString(PREF_USER_CREDENTIAL);
                performLoginButtonClick(username, password);
            }
        } else {
            Log.d(TAG, "onActivityResult: " + data);
        }
    }
}
