package com.botree.retailerssfa.main;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.arcprogressbar.ArcProgress;
import com.botree.retailerssfa.base.SFAActivity;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.controller.retrofit.ApiClient;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.FgtPwdOTPModel;
import com.botree.retailerssfa.util.LoggerUtil;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.PasswordStrengthChecker;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_CMPCODE_CODE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_LOGIN_CODE;
import static com.botree.retailerssfa.controller.constants.AppConstant.Request.REQ_PASS_STR;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LOGIN_USER_NAME;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_USER_CREDENTIAL;

/**
 *
 */
public class ForgotPasswordActivity extends SFAActivity implements View.OnClickListener, DataManager.APICallBack {
    private static final String TAG = ForgotPasswordActivity.class.getSimpleName();
    private EditText distrCode;
    EditText newPassword;
    EditText confirmNewPassword;
    EditText otp;
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
    private ProgressBar customProgressBar;
    private RelativeLayout trasLayout;
    private RelativeLayout rootView;
    private LinearLayout inputLayout;
    private Animation shake;
    SFASharedPref sfaSharedPref;
    Button skipButton;
    SFADatabase sfaDatabase;
    FgtPwdOTPModel fgtPwdOTPModel;
    private TextView refMsgTxt;
    private String resendOTP = "RESEND OTP";

    public ForgotPasswordActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forgot_password);

        initViews();
        sfaDatabase = SFADatabase.getInstance(this);

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

        distrCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Ignore
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setGenerateOtpButtonVisibility();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Ignore
            }
        });

        otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Ignore
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp.getText().toString().length() > 3) {
                    if (fgtPwdOTPModel != null && otp.getText().toString().trim().equals(fgtPwdOTPModel.getOtpCode())) {
                        otpLayout.setVisibility(View.VISIBLE);
                        refMsgTxt.setVisibility(View.GONE);
                        otp.setEnabled(false);
                        distrCode.setEnabled(false);
                        NotifyUtil.showSnackBar(getApplicationContext(), rootView, "OTP Validated Successfully", Snackbar.LENGTH_LONG);
                        generateOtpButton.setText(resendOTP);
                        generateOtpButton.setVisibility(View.VISIBLE);
                        generateOtpProgress.setVisibility(View.GONE);
                        if (countDownTimer != null)
                            countDownTimer.cancel();
                    } else {
                        otpLayout.setVisibility(View.GONE);
                        refMsgTxt.setVisibility(View.VISIBLE);
                        otp.setEnabled(true);
                        distrCode.setEnabled(false);
                        NotifyUtil.showSnackBar(getApplicationContext(), rootView, "Please enter valid OTP", Snackbar.LENGTH_LONG);
                    }
                } else if (otp.getText().toString().length() == 0) {
                    otpLayout.setVisibility(View.GONE);
                } else {
                    otpLayout.setVisibility(View.GONE);
                    refMsgTxt.setVisibility(View.VISIBLE);
                    distrCode.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Ignore
            }
        });

        skipButton.setVisibility(View.GONE);
    }

    void initViews() {
        distrCode = findViewById(R.id.distr_code);
        newPassword = findViewById(R.id.new_password);
        otp = findViewById(R.id.otp);
        confirmNewPassword = findViewById(R.id.confirm_new_password);
        generateOtpButton = findViewById(R.id.generate_otp_button);
        Button validateOtpButton = findViewById(R.id.validate_otp_button);
        changePasswordButton = findViewById(R.id.change_password_button);
        otpLayout = findViewById(R.id.otp_layout);
        newPasswordProgressBar = findViewById(R.id.new_password_progress_bar);
        confirmPasswordProgressBar = findViewById(R.id.confirm_password_progress_bar);
        newPasswordStrength = findViewById(R.id.new_password_strength);
        confirmPasswordStrength = findViewById(R.id.confirm_password_strength);
        generateOtpProgress = findViewById(R.id.generate_otp_progress);
        generateOtpButtonLayout = findViewById(R.id.generate_otp_button_layout);
        skipButton = findViewById(R.id.skip_button);
        customProgressBar = findViewById(R.id.custom_progressbar);
        trasLayout = findViewById(R.id.trans_layout);
        rootView = findViewById(R.id.rootview);
        inputLayout = findViewById(R.id.input_layout);
        refMsgTxt = findViewById(R.id.ref_id_message_text);

        trasLayout.setOnClickListener(null);
        trasLayout.setOnLongClickListener(null);
        otpLayout.setVisibility(View.GONE);
        generateOtpButtonLayout.setVisibility(View.GONE);
        confirmPasswordStrength.setVisibility(View.GONE);
        newPasswordStrength.setVisibility(View.GONE);
        generateOtpButton.setOnClickListener(this);
        changePasswordButton.setOnClickListener(this);
        skipButton.setOnClickListener(this);
        validateOtpButton.setOnClickListener(this);
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        sfaSharedPref = SFASharedPref.getOurInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.generate_otp_button:
                otpResendCounter();
                DataManager.getInstance().sendFgtPwdOTP(ApiClient.LOGIN_REDIRECT, ApiClient.SEND_OTP,
                        new String[]{REQ_LOGIN_CODE},
                        new String[]{distrCode.getText().toString().trim()}, ForgotPasswordActivity.this);
                break;
            case R.id.validate_otp_button:
                if (fgtPwdOTPModel != null && otp.getText().toString().trim().equals(fgtPwdOTPModel.getOtpCode())) {
                    otpLayout.setVisibility(View.VISIBLE);
                    NotifyUtil.showSnackBar(getApplicationContext(), rootView, "OTP Validated Successfully", Snackbar.LENGTH_LONG);
                } else {
                    NotifyUtil.showSnackBar(getApplicationContext(), rootView, "Please enter valid OTP", Snackbar.LENGTH_LONG);
                }
                break;
            case R.id.change_password_button:
                if (validateInputs()) {
                    hideKeyboard();
                    showProgress();
                    DataManager.getInstance().getForgotPasswordResponse(ApiClient.LOGIN_REDIRECT, ApiClient.CHANGE_PASS,
                            new String[]{REQ_CMPCODE_CODE, REQ_LOGIN_CODE, REQ_PASS_STR},
                            new String[]{fgtPwdOTPModel.getCmpCode(), fgtPwdOTPModel.getLoginCode(),
                                    confirmNewPassword.getText().toString()}, ForgotPasswordActivity.this);
                }
                break;
            default:
                break;
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getApplicationContext());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

        if (distrCode.getText().toString().trim().length() == 0) {
            inputLayout.startAnimation(shake);
            NotifyUtil.showSnackBar(getApplicationContext(), rootView, "Please enter Distributor Code", Snackbar.LENGTH_LONG);
        } else if (otp.getText().toString().trim().length() == 0) {
            inputLayout.startAnimation(shake);
            NotifyUtil.showSnackBar(getApplicationContext(), rootView, "Please enter OTP", Snackbar.LENGTH_LONG);
        } else if (newPassword.getText().toString().trim().length() == 0) {
            inputLayout.startAnimation(shake);
            NotifyUtil.showSnackBar(getApplicationContext(), rootView, "Please enter New Password", Snackbar.LENGTH_LONG);
        } else if (confirmNewPassword.getText().toString().trim().length() == 0) {
            inputLayout.startAnimation(shake);
            NotifyUtil.showSnackBar(getApplicationContext(), rootView, "Please enter Confirm New Password", Snackbar.LENGTH_LONG);
        } else if (!newPassword.getText().toString().equals(confirmNewPassword.getText().toString())) {
            inputLayout.startAnimation(shake);
            NotifyUtil.showSnackBar(getApplicationContext(), rootView, "New password and Confirm new password fields does not match.", Snackbar.LENGTH_LONG);
        } else {
            return true;
        }
        return false;
    }

    private void setGenerateOtpButtonVisibility() {
        if (distrCode.getText().toString().length() > 0) {
            generateOtpButtonLayout.setVisibility(View.VISIBLE);
            generateOtpButton.setVisibility(View.VISIBLE);
            generateOtpButton.setText("GENERATE OTP");
            generateOtpProgress.setVisibility(View.GONE);
        } else {
            generateOtpButtonLayout.setVisibility(View.GONE);
        }
    }

    private void otpResendCounter() {
        generateOtpButtonLayout.setVisibility(View.VISIBLE);
        generateOtpButton.setVisibility(View.GONE);
        generateOtpProgress.setVisibility(View.VISIBLE);
        refMsgTxt.setVisibility(View.GONE);
        otp.setEnabled(true);
        otp.setText("");
        otpLayout.setVisibility(View.GONE);
        newPassword.setText("");
        confirmNewPassword.setText("");
        distrCode.setEnabled(false);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(120000, 1) {
            public void onTick(long millisUntilFinished) {
                generateOtpProgress.setProgress((int) (millisUntilFinished / 1000));
            }

            public void onFinish() {
                generateOtpButton.setText(resendOTP);
                generateOtpButton.setVisibility(View.VISIBLE);
                generateOtpProgress.setVisibility(View.GONE);
                otp.setEnabled(false);
                refMsgTxt.setVisibility(View.GONE);
                otpLayout.setVisibility(View.GONE);
                otp.setText("");
                newPassword.setText("");
                confirmNewPassword.setText("");
                distrCode.setEnabled(true);
            }
        };
        countDownTimer.start();
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
        passwordStrength.setText(passwordStrengthChecker.getText(getApplicationContext()));
        passwordStrength.setTextColor(passwordStrengthChecker.getColor());

        progressBar.getProgressDrawable().setColorFilter(passwordStrengthChecker.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (passwordStrengthChecker.getText(getApplicationContext()).equals(getString(R.string.password_strength_weak))) {
            progressBar.setProgress(25);
        } else if (passwordStrengthChecker.getText(getApplicationContext()).equals(getString(R.string.password_strength_medium))) {
            progressBar.setProgress(50);
        } else if (passwordStrengthChecker.getText(getApplicationContext()).equals(getString(R.string.password_strength_strong))) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }

    @Override
    public void onAPICallback(String message, boolean isSuccess, AppConstant.RequestType requestType) {
        LoggerUtil.printDebugLog(TAG, "onAPICallback : isSuccess : " + isSuccess);
        if (requestType == AppConstant.RequestType.CHANGE_PASSWORD) {
            processChangePasswordResponse(message, isSuccess);
        } else if (requestType == AppConstant.RequestType.FGT_PWD_OTP) {
            otpResponse(message, isSuccess);
        }
    }

    private void otpResponse(String message, boolean isSuccess) {
        if (isSuccess) {
            Log.e(TAG, " otpRes " + message);
            fgtPwdOTPModel = new Gson().fromJson(message, FgtPwdOTPModel.class);
            if (fgtPwdOTPModel != null) {
                if (fgtPwdOTPModel.getRefCode() != null && !fgtPwdOTPModel.getRefCode().isEmpty()) {
                    refMsgTxt.setVisibility(View.VISIBLE);
                    String mob = fgtPwdOTPModel.getMobileNo().substring(0, 3) + "****" + fgtPwdOTPModel.getMobileNo().substring(fgtPwdOTPModel.getMobileNo().length() - 3);
                    refMsgTxt.setText("Enter Received OTP to the Mobile Number " + mob + " and Ref Id " + fgtPwdOTPModel.getRefCode());
                } else {
                    NotifyUtil.showSnackBar(getApplicationContext(), rootView, fgtPwdOTPModel.getMessage(), Snackbar.LENGTH_LONG);
                    generateOtpButton.setText(resendOTP);
                    generateOtpButton.setVisibility(View.VISIBLE);
                    generateOtpProgress.setVisibility(View.GONE);
                    otp.setEnabled(false);
                    distrCode.setEnabled(true);
                    refMsgTxt.setVisibility(View.GONE);
                    otpLayout.setVisibility(View.GONE);
                    otp.setText("");
                    newPassword.setText("");
                    confirmNewPassword.setText("");
                }
            }
        } else {
            NotifyUtil.showSnackBar(getApplicationContext(), rootView, message, Snackbar.LENGTH_LONG);
            generateOtpButton.setText(resendOTP);
            generateOtpButton.setVisibility(View.VISIBLE);
            generateOtpProgress.setVisibility(View.GONE);
            otp.setEnabled(false);
            distrCode.setEnabled(true);
            refMsgTxt.setVisibility(View.GONE);
            otpLayout.setVisibility(View.GONE);
            otp.setText("");
            newPassword.setText("");
            confirmNewPassword.setText("");
        }
    }

    private void processChangePasswordResponse(String message, boolean isSuccess) {
        if (isSuccess) {
            cancelProgress();
            final Dialog infoDialog = new Dialog(ForgotPasswordActivity.this, R.style.ThemeDialogCustom);
            infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            infoDialog.setCanceledOnTouchOutside(false);
            infoDialog.setCancelable(false);
            infoDialog.setContentView(R.layout.msg_singlebtn_dialog);
            TextView textView = infoDialog.findViewById(R.id.textView);
            textView.setText("Distributor Password Updated Successfully");
            final Button submit = infoDialog.findViewById(R.id.msg_ok_btn);

            submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    sfaSharedPref.writeString(PREF_LOGIN_USER_NAME, "");
                    sfaSharedPref.writeString(PREF_USER_CREDENTIAL, "");
                    sfaDatabase.deleteLoggedInUser();
                    Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    pullInLeft();
                    finish();
                    infoDialog.dismiss();

                }
            });

            infoDialog.show();
        } else {
            NotifyUtil.showSnackBar(getApplicationContext(), rootView, message, Snackbar.LENGTH_LONG);
            cancelProgress();
        }
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }
}
