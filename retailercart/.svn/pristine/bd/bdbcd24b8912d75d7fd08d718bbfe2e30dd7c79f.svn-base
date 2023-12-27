package com.botree.retailerssfa.main;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.arcprogressbar.ArcProgress;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.controller.retrofit.ApiClient;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.fragmentmanager.ESFAFragTags;
import com.botree.retailerssfa.fragmentmanager.SFAFragmentManager;
import com.botree.retailerssfa.util.LoggerUtil;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.PasswordStrengthChecker;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.android.material.snackbar.Snackbar;

import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_CMPCODE_CODE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_LOGIN_CODE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_PASS_STR;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_AUTH_TOKEN;

/**
 *
 */
public class ChangePasswordFragment extends SFAFragment implements View.OnClickListener, DataManager.APICallBack {
    private static final String TAG = ChangePasswordFragment.class.getSimpleName();
    private EditText existingPassword;
    EditText newPassword;
    EditText confirmNewPassword;
    private Button generateOtpButton;
    Button changePasswordButton;
    private LinearLayout otpLayout;
    private ProgressBar newPasswordProgressBar;
    ProgressBar confirmPasswordProgressBar;
    private TextView newPasswordStrength;
    TextView confirmPasswordStrength;
    private ArcProgress generateOtpProgress;
    private CountDownTimer countDownTimer;
    private FrameLayout generateOtpButtonLayout;
    private boolean fromLoginScreen = false;
    private ProgressBar customProgressBar;
    private RelativeLayout trasLayout;
    private RelativeLayout rootView;
    private LinearLayout inputLayout;
    private Animation shake;
    SFASharedPref sfaSharedPref;
    private boolean isSkipClicked;
    Button skipButton;
    String oldPassword = "";
    SFADatabase sfaDatabase;
    EventListener eventListener;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initViews(view);
        sfaDatabase = SFADatabase.getInstance(getSFAFragmentActivity());
        oldPassword = sfaDatabase.getPasswordBasedOnLoginId(SFASharedPref.getOurInstance().readString(SFASharedPref.PREF_LOGIN_CODE));

        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Ignore
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatePasswordStrengthView(charSequence.toString(), newPasswordProgressBar, newPasswordStrength);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Ignore
            }
        });

        confirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Ignore
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updatePasswordStrengthView(charSequence.toString(), confirmPasswordProgressBar, confirmPasswordStrength);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Ignore
            }
        });

        if (getArguments() != null)
            fromLoginScreen = getArguments().getBoolean("FROM_LOGIN");

        if (fromLoginScreen) {
            skipButton.setVisibility(View.VISIBLE);
            if (getSFAFragmentActivity().getSupportActionBar() != null)
                getSFAFragmentActivity().getSupportActionBar().hide();
        } else {
            skipButton.setVisibility(View.GONE);
            if (getSFAFragmentActivity().getSupportActionBar() != null)
                getSFAFragmentActivity().getSupportActionBar().show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventListener) {
            eventListener = (EventListener) context;
        } else {
            // Throw an error!
        }
    }

    void initViews(View view) {
        existingPassword = view.findViewById(R.id.existing_password);
        newPassword = view.findViewById(R.id.new_password);
        confirmNewPassword = view.findViewById(R.id.confirm_new_password);
        generateOtpButton = view.findViewById(R.id.generate_otp_button);
        changePasswordButton = view.findViewById(R.id.change_password_button);
        otpLayout = view.findViewById(R.id.otp_layout);
        newPasswordProgressBar = view.findViewById(R.id.new_password_progress_bar);
        confirmPasswordProgressBar = view.findViewById(R.id.confirm_password_progress_bar);
        newPasswordStrength = view.findViewById(R.id.new_password_strength);
        confirmPasswordStrength = view.findViewById(R.id.confirm_password_strength);
        generateOtpProgress = view.findViewById(R.id.generate_otp_progress);
        generateOtpButtonLayout = view.findViewById(R.id.generate_otp_button_layout);
        skipButton = view.findViewById(R.id.skip_button);
        customProgressBar = view.findViewById(R.id.custom_progressbar);
        trasLayout = view.findViewById(R.id.trans_layout);
        rootView = view.findViewById(R.id.rootview);
        inputLayout = view.findViewById(R.id.input_layout);

        trasLayout.setOnClickListener(null);
        trasLayout.setOnLongClickListener(null);

        generateOtpButtonLayout.setVisibility(View.GONE);
        confirmPasswordStrength.setVisibility(View.GONE);
        newPasswordStrength.setVisibility(View.GONE);
        generateOtpButton.setOnClickListener(this);
        changePasswordButton.setOnClickListener(this);
        skipButton.setOnClickListener(this);
        shake = AnimationUtils.loadAnimation(getSFAFragmentActivity(), R.anim.shake);
        sfaSharedPref = SFASharedPref.getOurInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.generate_otp_button:
                otpLayout.setVisibility(View.VISIBLE);
                otpResendCounter();
                break;
            case R.id.change_password_button:
                isSkipClicked = false;
                if (validateInputs()) {
                    showProgress();
                    DataManager.getInstance().getChangePasswordResponse(sfaSharedPref.readString(PREF_AUTH_TOKEN),
                            ApiClient.LOGIN_REDIRECT, ApiClient.CHANGE_PASS,
                            new String[]{REQ_CMPCODE_CODE, REQ_LOGIN_CODE, REQ_PASS_STR},
                            new String[]{SFASharedPref.getOurInstance().readString(SFASharedPref.PREF_CMP_CODE),
                                    SFASharedPref.getOurInstance().readString(SFASharedPref.PREF_LOGIN_CODE),
                                    confirmNewPassword.getText().toString()}, ChangePasswordFragment.this);
                }
                break;
            case R.id.skip_button:
                isSkipClicked = true;
                showProgress();
                LoggerUtil.printDebugLog(TAG, "Skip Button Clicked");
                DataManager.getInstance().getChangePasswordResponse(sfaSharedPref.readString(PREF_AUTH_TOKEN),
                        ApiClient.LOGIN_REDIRECT, ApiClient.CHANGE_PASS,
                        new String[]{REQ_CMPCODE_CODE, REQ_LOGIN_CODE, REQ_PASS_STR},
                        new String[]{SFASharedPref.getOurInstance().readString(SFASharedPref.PREF_CMP_CODE),
                                SFASharedPref.getOurInstance().readString(SFASharedPref.PREF_LOGIN_CODE),
                                oldPassword},
                        ChangePasswordFragment.this);
                break;
            default:
                break;
        }
    }

    private void showProgress() {
        if (customProgressBar.getVisibility() != View.VISIBLE) {
            customProgressBar.setVisibility(View.VISIBLE);
            trasLayout.setVisibility(View.VISIBLE);
        }
    }

    private void cancelProgress() {
        if (customProgressBar.getVisibility() == View.VISIBLE) {
            customProgressBar.setVisibility(View.GONE);
            trasLayout.setVisibility(View.GONE);
        }
    }

    private boolean validateInputs() {

        if (existingPassword.getText().toString().trim().length() == 0) {
            inputLayout.startAnimation(shake);
            NotifyUtil.showSnackBar(getContext(), rootView, "Please enter Existing Password", Snackbar.LENGTH_LONG);
        } else if (newPassword.getText().toString().trim().length() == 0) {
            inputLayout.startAnimation(shake);
            NotifyUtil.showSnackBar(getContext(), rootView, "Please enter New Password", Snackbar.LENGTH_LONG);
        } else if (confirmNewPassword.getText().toString().trim().length() == 0) {
            inputLayout.startAnimation(shake);
            NotifyUtil.showSnackBar(getContext(), rootView, "Please enter Confirm New Password", Snackbar.LENGTH_LONG);
        } else if (!existingPassword.getText().toString().equals(oldPassword)) {
            inputLayout.startAnimation(shake);
            NotifyUtil.showSnackBar(getContext(), rootView, "Please enter valid existing password", Snackbar.LENGTH_LONG);
        } else if (!newPassword.getText().toString().equals(confirmNewPassword.getText().toString())) {
            inputLayout.startAnimation(shake);
            NotifyUtil.showSnackBar(getContext(), rootView, "New password and Confirm new password fields does not match.", Snackbar.LENGTH_LONG);
        } else {
            return true;
        }
        return false;
    }

    private void otpResendCounter() {
        generateOtpButtonLayout.setVisibility(View.VISIBLE);
        generateOtpButton.setVisibility(View.GONE);
        generateOtpProgress.setVisibility(View.VISIBLE);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(60000, 1) {
            public void onTick(long millisUntilFinished) {
                generateOtpProgress.setProgress((int) (millisUntilFinished / 1000));
            }

            public void onFinish() {
                generateOtpButton.setText("RESEND OTP");
                generateOtpButton.setVisibility(View.VISIBLE);
                generateOtpProgress.setVisibility(View.GONE);
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_login);
        item.setVisible(false);
    }

    private void updatePasswordStrengthView(String password, ProgressBar progressBar, TextView passwordStrength) {
        if (password.length() > 0) {
            passwordStrength.setVisibility(View.VISIBLE);
        } else {
            passwordStrength.setVisibility(View.GONE);
        }

        if (password.isEmpty()) {
            passwordStrength.setText("");
            progressBar.setProgress(0);
            return;
        }

        PasswordStrengthChecker passwordStrengthChecker = PasswordStrengthChecker.calculateStrength(password);
        passwordStrength.setText(passwordStrengthChecker.getText(getActivity()));
        passwordStrength.setTextColor(passwordStrengthChecker.getColor());

        progressBar.getProgressDrawable().setColorFilter(passwordStrengthChecker.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (passwordStrengthChecker.getText(getActivity()).equals(getString(R.string.password_strength_weak))) {
            progressBar.setProgress(25);
        } else if (passwordStrengthChecker.getText(getActivity()).equals(getString(R.string.password_strength_medium))) {
            progressBar.setProgress(50);
        } else if (passwordStrengthChecker.getText(getActivity()).equals(getString(R.string.password_strength_strong))) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getSFAFragmentActivity()).hideBottomNevigation();
    }

    @Override
    public void onAPICallback(String message, boolean isSuccess, AppConstant.RequestType requestType) {
        LoggerUtil.printDebugLog(TAG, "onAPICallback : isSuccess : " + isSuccess);
        if (requestType == AppConstant.RequestType.CHANGE_PASSWORD) {
            processChangePasswordResponse(message, isSuccess);
        }
    }

    private void processChangePasswordResponse(String message, boolean isSuccess) {
        if (isSuccess) {
            if (!isSkipClicked) {
//                sfaSharedPref.writeString(PREF_LOGIN_USER_NAME, "");
//                sfaSharedPref.writeString(PREF_USER_CREDENTIAL, "");
//                sfaDatabase.deleteLoggedInUser();
//                Intent i = new Intent(getSFAFragmentActivity(), LoginActivity.class);
//                startActivity(i);
//                getSFAFragmentActivity().pullInLeft();
//                getSFAFragmentActivity().finish();
                cancelProgress();
                eventListener.onPasswordChangeCompleted();
            } else {
                sfaDatabase.updatePasswordChangedInLogin(SFASharedPref.getOurInstance().readString(SFASharedPref.PREF_LOGIN_CODE), oldPassword);
                ESFAFragTags fragTags = ESFAFragTags.DASHBOARD_NAVIGATION;
                SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();
                sfaFragManger.clearBackStack(getSFAFragmentActivity());
                sfaFragManger.updateFragment(fragTags, getSFAFragmentActivity());

                eventListener.onPasswordSkipCompleted();
                cancelProgress();
            }

        } else {
            NotifyUtil.showSnackBar(getSFAFragmentActivity(), rootView, message, Snackbar.LENGTH_LONG);
            cancelProgress();
        }
    }

    public interface EventListener {
        void onPasswordSkipCompleted();

        void onPasswordChangeCompleted();
    }
}
