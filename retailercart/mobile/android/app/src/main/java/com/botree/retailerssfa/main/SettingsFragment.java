/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.main;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.async.AsyncLoadUsbSyncData;
import com.botree.retailerssfa.async.TaskCallbacks;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.db.FileAccessUtil;
import com.botree.retailerssfa.fragmentmanager.ESFAFragTags;
import com.botree.retailerssfa.fragmentmanager.SFAFragmentManager;
import com.botree.retailerssfa.models.ExtendedCurrency;
import com.botree.retailerssfa.support.CurrencyPicker;
import com.botree.retailerssfa.support.CurrencyPickerListener;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.support.LocaleHelper;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.SFASharedPref;
import com.botree.retailerssfa.util.Usbservice;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.botree.retailerssfa.controller.constants.AppConstant.USER_TYPE_DSR;
import static com.botree.retailerssfa.controller.constants.AppConstant.USER_TYPE_ISR;
import static com.botree.retailerssfa.support.Globals.fromHtml;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_AUTO_QUICK_ACCESS_VALUE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_BOT_SERVICE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CUSTOM_ORDER_SCREEN;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_GEO_FENCING;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_ORDER_BOOKING_OPTIONS;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_PREVIOUS_ORDER;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_PRODUCT_SEARCH_LEVEL;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_PRODUCT_SEARCH_LEVEL_NAME;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_QUICK_ACTION;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SELECTED_LANGUAGE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_VAN_SHOP;

public class SettingsFragment extends SFAFragment
        implements View.OnClickListener, CurrencyPickerListener {
    private TextView companyTitle;
    private LinearLayout contactDetailLayout;
    private String versionname;
    private SFASharedPref preferences;
    private CurrencyPicker mCurrencyPicker;
    private Spinner langSpinner;
    private Spinner prodSearchSpinner;
    private String language = "en";
    private RadioGroup radioGroupOrderBtn;
    private RadioButton radioCustomBtn;
    private RadioButton radioQuickBtn;
    private RadioButton radioNormalBtn;
    private String userType;
    private ImageButton showFile;
    private ImageButton createFile;
    private Dialog dialog;
    private static final String VERSION_TAG = "Version";


    private LinearLayout filterLayout;
    private LinearLayout previousOrderLayout;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String usbMessage = intent.getStringExtra("UsbStatus");
            String fileMessage = intent.getStringExtra("FileStatus");


            if (usbMessage.equalsIgnoreCase("connected")) {

                createFile.setVisibility(View.VISIBLE);
            } else {
                createFile.setVisibility(View.GONE);
            }

            if (fileMessage.equalsIgnoreCase("NotDeleted")) {
                showFile.setVisibility(View.VISIBLE);

            } else {
                showFile.setVisibility(View.INVISIBLE);

            }

        }
    };
    private View mViewPrevOrderLine;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = SFASharedPref.getOurInstance();
        setHasOptionsMenu(true);
        userType = preferences.readString(SFASharedPref.PREF_USER_TYPE);

        getActivity().startService(new Intent(getSFAFragmentActivity(), Usbservice.class));
        LocalBroadcastManager.getInstance(getSFAFragmentActivity()).registerReceiver(mMessageReceiver, new IntentFilter("USBUpdates"));


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initialize(view);
        setCompanytitle();
        return view;
    }

    private void initialize(View view) {

        filterLayout = view.findViewById(R.id.filter_layout);
        previousOrderLayout = view.findViewById(R.id.previous_orders_layout);
        LinearLayout versionLayout = view.findViewById(R.id.version_layout);
        mViewPrevOrderLine = view.findViewById(R.id.viewPrevOrderLine);

        radioGroupOrderBtn = view.findViewById(R.id.ord_book_setting_radio_grp);
        radioNormalBtn = view.findViewById(R.id.normal_ord_radio_btn);
        radioCustomBtn = view.findViewById(R.id.custom_ord_radio_btn);
        radioQuickBtn = view.findViewById(R.id.quick_ord_radio_btn);

        companyTitle = view.findViewById(R.id.company_name);
        TextView version = view.findViewById(R.id.setting_version_txt);
        LinearLayout faqsLayout = view.findViewById(R.id.faqs_layout);
        LinearLayout contactLayout = view.findViewById(R.id.contact_layout);
        contactDetailLayout = view.findViewById(R.id.contact_details_layout);
        LinearLayout changePasswordLayout = view.findViewById(R.id.change_password_layout);
        LinearLayout clearDataLayout = view.findViewById(R.id.cleardata_layout);
        LinearLayout currencyLayout = view.findViewById(R.id.currency_lnlayout);
        LinearLayout aboutUsLayout = view.findViewById(R.id.about_us_lnlayout);
        LinearLayout usbSyncLayout = view.findViewById(R.id.usbsync_lnlayout);
        createFile = view.findViewById(R.id.createFile);
        showFile = view.findViewById(R.id.showFile);

        SwitchCompat vanShopSwitch = view.findViewById(R.id.van_shop_switch);
        SwitchCompat creditNoteSwitch = view.findViewById(R.id.creditNoteSwitch);
        SwitchCompat switchAutoQuickAction = view.findViewById(R.id.switchAutoQuickAction);
        SwitchCompat switchPreviousOrder = view.findViewById(R.id.switchPreviousOrder);
        SwitchCompat switchCustomScreen = view.findViewById(R.id.switchCustomScreen);
        SwitchCompat switchGeofencing = view.findViewById(R.id.switchGeofencing);
        SwitchCompat switchBotService = view.findViewById(R.id.bot_service_switch);
        final Spinner spinnerQuickAccess = view.findViewById(R.id.spinnerQuickAccess);
        final ImageView ivQuickAccess = view.findViewById(R.id.ivQuickAccess);
        langSpinner = view.findViewById(R.id.language_spinner);
        prodSearchSpinner = view.findViewById(R.id.prod_search_spinner);
        version.setText(getAppVersion());
        faqsLayout.setOnClickListener(this);
        contactLayout.setOnClickListener(this);
        clearDataLayout.setOnClickListener(this);
        currencyLayout.setOnClickListener(this);
        aboutUsLayout.setOnClickListener(this);
        usbSyncLayout.setOnClickListener(this);
        changePasswordLayout.setOnClickListener(this);
        createFile.setOnClickListener(this);
        showFile.setOnClickListener(this);
        versionLayout.setOnClickListener(this);

        setOrderBookingScreenType();
        boolean switchState = preferences.readBooleanDefaultTrue(PREF_VAN_SHOP);
        vanShopSwitch.setChecked(switchState);

        vanShopSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                preferences.writeBoolean(PREF_VAN_SHOP, b);

            }
        });
        boolean creditSwitch = preferences.readBooleanDefaultTrue("PREF_CREDIT_NOTE");

        creditNoteSwitch.setChecked(creditSwitch);

        creditNoteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                preferences.writeBoolean("PREF_CREDIT_NOTE", b);

            }
        });

        boolean previousOrderSwitchState = preferences.readBoolean(PREF_PREVIOUS_ORDER);

        switchPreviousOrder.setChecked(previousOrderSwitchState);

        switchPreviousOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                preferences.writeBoolean(PREF_PREVIOUS_ORDER, b);


            }
        });


        boolean customOrderSwitchState = preferences.readBoolean(PREF_CUSTOM_ORDER_SCREEN);

        switchCustomScreen.setChecked(customOrderSwitchState);

        switchCustomScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                preferences.writeBoolean(PREF_CUSTOM_ORDER_SCREEN, b);
            }
        });

        boolean geofencingSwitchState = preferences.readBoolean(PREF_GEO_FENCING);
        switchGeofencing.setChecked(geofencingSwitchState);
        switchGeofencing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                preferences.writeBoolean(PREF_GEO_FENCING, b);
            }
        });

        boolean botService = preferences.readBoolean(PREF_BOT_SERVICE);
        switchBotService.setChecked(botService);
        switchBotService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                preferences.writeBoolean(PREF_BOT_SERVICE, b);
            }
        });

        boolean switchQuickAct = preferences.readBoolean(PREF_QUICK_ACTION);
        switchAutoQuickAction.setChecked(switchQuickAct);
        if (switchQuickAct) {
            spinnerQuickAccess.setVisibility(View.VISIBLE);
            ivQuickAccess.setImageResource(R.drawable.ic_favorite_star);
            ivQuickAccess.setColorFilter(ContextCompat.getColor(getSFAFragmentActivity(), R.color.colorPrimary));
        }

        switchAutoQuickAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    preferences.writeBoolean(PREF_QUICK_ACTION, true);

                    spinnerQuickAccess.setVisibility(View.VISIBLE);
                    ivQuickAccess.setImageResource(R.drawable.ic_favorite_star);
                    ivQuickAccess.setColorFilter(ContextCompat.getColor(getSFAFragmentActivity(), R.color.colorPrimary));
                } else {
                    preferences.writeBoolean(PREF_QUICK_ACTION, false);

                    spinnerQuickAccess.setVisibility(View.GONE);
                    ivQuickAccess.setImageResource(R.drawable.ic_favorite_star_fill);
                    ivQuickAccess.setColorFilter(ContextCompat.getColor(getSFAFragmentActivity(), R.color.colorPrimary));
                }
            }
        });
        int selectedPosition = 0;
        String menucount = preferences.readString(SFASharedPref.PREF_AUTO_QUICK_ACCESS_VALUE, "");
        for (int i = 0; i < spinnerQuickAccess.getAdapter().getCount(); i++) {
            if (spinnerQuickAccess.getItemAtPosition(i).equals(menucount))
                selectedPosition = i;
        }
        spinnerQuickAccess.setSelection(selectedPosition);
        spinnerQuickAccess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "initialize: " + spinnerQuickAccess.getItemAtPosition(position));
                preferences.writeString(PREF_AUTO_QUICK_ACCESS_VALUE, (String.valueOf(spinnerQuickAccess.getItemAtPosition(position))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // select nothing
            }
        });

        setCurrentlySelectedLang();

        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) {
                    language = "en";
                    LocaleHelper.setLocale(getActivity(), language);

                } else if (pos == 1) {
                    language = "hi";
                    LocaleHelper.setLocale(getActivity(), language);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // nothing is selected
            }

        });
        removeSyncButtonBasedOnConfig();

        loadSetProductSearchOptions();
    }


    /**
     * remove sync button based on configuration
     */
    private void removeSyncButtonBasedOnConfig() {
        if (!userType.isEmpty() && (USER_TYPE_DSR.equalsIgnoreCase(userType) || USER_TYPE_ISR.equalsIgnoreCase(userType))) {
            filterLayout.setVisibility(View.VISIBLE);
            previousOrderLayout.setVisibility(View.VISIBLE);
            mViewPrevOrderLine.setVisibility(View.VISIBLE);
        } else {
            filterLayout.setVisibility(View.GONE);
            previousOrderLayout.setVisibility(View.GONE);
            mViewPrevOrderLine.setVisibility(View.GONE);
        }
    }


    private void loadSetProductSearchOptions() {

        int selectedSearchPosition = 4;
        try {
            String searchCount = preferences.readString(SFASharedPref.PREF_PRODUCT_SEARCH_LEVEL, "");
            if (searchCount != null && !searchCount.isEmpty()) {
                selectedSearchPosition = Integer.valueOf(searchCount);
            } else {
                selectedSearchPosition = Integer.parseInt(getResources().getString(R.string.filter_level));
            }
        } catch (Exception e) {
            Log.e(TAG, "loadSetProductSearchOptions: " + e.getMessage(), e);
        }
        prodSearchSpinner.setSelection(selectedSearchPosition - 1);

        prodSearchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                preferences.writeString(PREF_PRODUCT_SEARCH_LEVEL, (String.valueOf(
                        prodSearchSpinner.getSelectedItemPosition() + 1)));
                preferences.writeString(PREF_PRODUCT_SEARCH_LEVEL_NAME, (String.valueOf(
                        prodSearchSpinner.getSelectedItem())));
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // nothing is selected
            }
        });
    }

    private void setOrderBookingScreenType() {
        String orderOption = preferences.readString(PREF_ORDER_BOOKING_OPTIONS);
        if (orderOption.equalsIgnoreCase(radioQuickBtn.getText().toString())) {
            radioQuickBtn.setChecked(true);
        } else if (orderOption.equalsIgnoreCase(radioCustomBtn.getText().toString())) {
            radioCustomBtn.setChecked(true);
        } else {
            radioNormalBtn.setChecked(true);
        }

        RadioButton rbLocal = radioGroupOrderBtn.findViewById(radioGroupOrderBtn.getCheckedRadioButtonId());
        preferences.writeString(PREF_ORDER_BOOKING_OPTIONS, rbLocal.getText().toString());

        radioGroupOrderBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb) {
                    preferences.writeString(PREF_ORDER_BOOKING_OPTIONS, rb.getText().toString());
                }
            }
        });
    }

    private void setCurrentlySelectedLang() {
        if (preferences.readString(PREF_SELECTED_LANGUAGE).equalsIgnoreCase("hi")) {
            langSpinner.setSelection(1);
        } else {
            langSpinner.setSelection(0);
        }
    }


    private String getAppVersion() {
        try {
            versionname = getSFAFragmentActivity().getPackageManager().getPackageInfo(getSFAFragmentActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getAppVersion: " + e.getMessage(), e);
        }
        return versionname;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_login);

        item.setVisible(false);
    }

    /**
     * To show the FAQs fragment
     *
     * @param fragTags for fragment
     */
    private void callFAQ(ESFAFragTags fragTags) {
        SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();
        if (!sfaFragManger.updateFragment(fragTags, true, getSFAFragmentActivity())) {
            Log.e("Settings Fragment", "Error in creating fragment");
        }
    }

    /**
     * show and hind the contact description view
     */
    private void showOrHideContact() {
        if (contactDetailLayout.getVisibility() != View.VISIBLE) {
            contactDetailLayout.setVisibility(View.VISIBLE);
        } else {
            contactDetailLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        ESFAFragTags fragTags;
        switch (view.getId()) {
            case R.id.faqs_layout:
                fragTags = ESFAFragTags.FAQ;
                callFAQ(fragTags);
                break;

            case R.id.about_us_lnlayout:
                fragTags = ESFAFragTags.ABOUT_US;
                callFAQ(fragTags);
                break;

            case R.id.contact_layout:
                showOrHideContact();
                break;
            case R.id.cleardata_layout:
                break;
            case R.id.currency_lnlayout:
                showCurrencyDialog();
                break;
            case R.id.version_layout:
                NotifyUtil.showListDialog(getActivity(), "What's new", getResources().getStringArray(R.array.version_changes));
                break;
            case R.id.createFile:
                if (Globals.isConnected(getActivity())) {
                    showDialogUsbSyncFolderPath(getActivity(), "Connected In Network", "Kindly Disconnect Network For Usb Sync", "Ok", "");
                } else {
                    showDialogUsbSyncFolderPath(getActivity(), "Create File", "Proceed To Create a File in Below path" + "\n" + "/UsbSyncFolder/UploadJson.txt", "Ok", "Cancel");
                }
                break;
            case R.id.showFile:
                showEncryptFile();
                break;
            case R.id.change_password_layout:
                fragTags = ESFAFragTags.CHANGE_PASSWORD;
                callFAQ(fragTags);
                break;
            default:
                break;
        }

    }


    private void showEncryptFile() {
        try {
            showFile.setVisibility(View.VISIBLE);
            Uri selectedUri = Uri.parse(FileAccessUtil.getInstance().getExternalStorageDirectory() + "/UsbSyncFolder/");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(selectedUri, "resource/folder");
            getActivity().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "kindly Download Filemanager To Move For UsbSyncFolder", Toast.LENGTH_LONG).show();
        }
    }


    private void showCurrencyDialog() {
        FragmentManager fm = this.getChildFragmentManager();

        mCurrencyPicker = CurrencyPicker.newInstance("Select Currency");

        List<CharSequence> availableCurrencyNames = new ArrayList<>();
        List<CharSequence> availableCurrencyCodes = new ArrayList<>();

        // You can limit the displayed countries
        ArrayList<ExtendedCurrency> nc = new ArrayList<>();
        for (ExtendedCurrency c : ExtendedCurrency.getOurInstance().getAllCurrencies()) {
            nc.add(c);

            availableCurrencyCodes.add(c.getCode());
            availableCurrencyNames.add(c.getName());

        }

        mCurrencyPicker.show(fm, "");

        mCurrencyPicker.setCurrenciesList(nc);
        mCurrencyPicker.setListener(SettingsFragment.this);
    }

    /**
     * set the company name in the view
     */
    private void setCompanytitle() {

        String text = "<font color=#FF8075> © 2019.</font> <font color=#3B4463>Botree Software. All rights reserved.</font>";
        companyTitle.setText(fromHtml(text));
    }

    @Override
    public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {

        Log.e(TAG, "onSelectCurrency: " + name + " " + code + " " + symbol);

        if (mCurrencyPicker != null && mCurrencyPicker.isVisible()) {
            mCurrencyPicker.dismiss();
        }
    }


    public void showDialogUsbSyncFolderPath(final Context context, String title, final String msg,
                                            String positiveStr, final String negativeStr) {


        if (dialog != null && dialog.isShowing()) return;
        dialog = new Dialog(context, R.style.ThemeDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.msg_dialog);
        final TextView txtMsg = dialog.findViewById(R.id.alert_msg);
        TextView txtTitle = dialog.findViewById(R.id.alert_title);
        final Button ok = dialog.findViewById(R.id.alert_ok_btn);
        final Button cancel = dialog.findViewById(R.id.alert_cancel_btn);

        if (negativeStr.equalsIgnoreCase("")) {
            cancel.setVisibility(View.GONE);
        }

        txtTitle.setText(title);
        txtMsg.setText(msg);

        ok.setText(positiveStr);
        cancel.setText(negativeStr);


        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!negativeStr.equalsIgnoreCase("")) {
                    AsyncLoadUsbSyncData asyncUpload = new AsyncLoadUsbSyncData((TaskCallbacks) getActivity(), getActivity(), userType);
                    asyncUpload.execute();
                    showFile.setVisibility(View.VISIBLE);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        if (!dialog.isShowing())
            dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getSFAFragmentActivity()).showBottomNevigation();
    }
}
