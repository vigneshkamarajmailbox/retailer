/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.base;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.fragmentmanager.SFAFragmentManager;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.support.LocaleHelper;
import com.botree.retailerssfa.util.SFASharedPref;
import com.crashlytics.android.Crashlytics;

import static com.botree.retailerssfa.fragmentmanager.ESFAFragTags.DASHBOARD_NAVIGATION;
import static com.botree.retailerssfa.fragmentmanager.ESFAFragTags.MESSAGES;
import static com.botree.retailerssfa.fragmentmanager.ESFAFragTags.SETTINGS;
import static com.botree.retailerssfa.fragmentmanager.ESFAFragTags.SYNC;
import static com.botree.retailerssfa.util.AppUtils.isUserOnline;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CUSTOMER_NAME;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SELECTED_LANGUAGE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_USER_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_USER_NAME;

/**
 * Base Activity for SFA Application. Which includes the setting toolbar
 * (Material Toolbar above Kitkat versions) and title.
 * Animation of Push In Left for forward and Push In Right for backward.
 * setting the Activity Bundle of savedinstancestate which is used for fragments too.
 */
public abstract class SFAActivity extends AppCompatActivity {

    private static final String TAG = SFAActivity.class.getSimpleName();
    private Bundle bundle;
    private TextView toolbarTitle;
    private TextView toolbarSubTitle;
    private ImageView menuBackBtn;
    private ImageView botIconBtn;

    public void logUser() {
        Crashlytics.setUserIdentifier(SFASharedPref.getOurInstance().readString(PREF_USER_CODE));
        Crashlytics.setUserName(SFASharedPref.getOurInstance().readString(PREF_USER_NAME));
    }


    /**
     * This Toolbar is used to set in the action bar.
     *
     * @return {@link Toolbar}
     */
    public abstract Toolbar getToolbar();

    /**
     * Toolbar is added to SupportActionBar of [@link {@link AppCompatActivity}].
     *
     * @param enableBackIcon If {@code true} then back icon in toolbar is visible else invisible
     */
    public void initToolbar(boolean enableBackIcon) {

        pushInRight();
        Toolbar toolbar = getToolbar();
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarSubTitle = findViewById(R.id.toolbar_subtitle);
        menuBackBtn = findViewById(R.id.menu_back_icon);
        botIconBtn = findViewById(R.id.m_app_icon);

        setSupportActionBar(toolbar);
        if (toolbar != null && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (enableBackIcon) {

                if (toolbarTitle != null) {
                    toolbarTitle.setText(String.valueOf("Filter"));
                }
                botIconBtn.setVisibility(View.GONE);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        }
        initMaterialToolbar();
    }

    /**
     * Set or clear status bar color based on above KITKAT or not}
     */
    private void initMaterialToolbar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * @param text set as Toolbar title text
     */
    public void setToolbarTitle(String text) {

        SFASharedPref preferences = SFASharedPref.getOurInstance();

        if (toolbarTitle != null && text != null && !text.equalsIgnoreCase("null")) {
            toolbarTitle.setText(text);

        }
        if (!isUserOnline() && toolbarSubTitle != null) {

            if (Boolean.TRUE.equals(Globals.getOurInstance().getIsSubTitleShow())) {
//                String subTitle = preferences.readString(PREF_CUSTOMER_NAME);

//                if (!subTitle.equalsIgnoreCase("")) {
                    toolbarSubTitle.setText("RETAILER");
                    toolbarSubTitle.setVisibility(View.VISIBLE);
//                } else {
//                    toolbarSubTitle.setVisibility(View.GONE);
//                }
            } else {
                toolbarSubTitle.setVisibility(View.GONE);
            }
        }


        if (toolbarTitle != null && text != null && (text.equalsIgnoreCase(DASHBOARD_NAVIGATION.getValue()) ||
                text.equalsIgnoreCase(MESSAGES.getValue()) ||
                text.equalsIgnoreCase(SYNC.getValue()) ||
                text.equalsIgnoreCase(SETTINGS.getValue()) ||
                text.equalsIgnoreCase("Search"))) {
            menuBackBtn.setVisibility(View.GONE);
            botIconBtn.setVisibility(View.VISIBLE);
        } else {
            menuBackBtn.setVisibility(View.VISIBLE);
            botIconBtn.setVisibility(View.GONE);
        }
    }


    public void setAutoScreenCount(String screenName) {
        try {

            SFASharedPref preferences = SFASharedPref.getOurInstance();
            SFADatabase db = SFADatabase.getInstance(this);
            assert preferences != null;
            int screenCount = db.getAutoQuickMenuCount(screenName);
            db.insertOrUpdateAutoQuickActions(screenName, screenCount);
        } catch (Exception e) {
            Log.e(TAG, "setAutoScreenCount: " + e.getMessage(), e);
        }
    }

    /**
     * Remove the fragment backstack of {@link SFAFragmentManager}
     */
    @SuppressLint("RestrictedApi")
    public void goBack() {

        FragmentManager fm = getSupportFragmentManager();

        int count = fm.getBackStackEntryCount();

        if (count != 0) {
            SFAFragmentManager.newInstance().popBackStack(this, null);
            Log.e(TAG, "fragment count = " + fm.getBackStackEntryCount());
        }
    }

    /**
     * Get the SavedInstanceBundle which is already set using setSavedInstanceBundle
     *
     * @return {@link Bundle}
     */
    public Bundle getSavedInstanceBundle() {
        return bundle;
    }

    /**
     * By setting the Saved Instance Bundle here, we can get the bundle from SFAFragments
     *
     * @param bundle saved Instance Bundle of Activity
     */
    public void setSavedInstanceBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    /**
     * Transition from right to left
     */
    protected void pushInRight() {
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    /**
     * Transition from left to right
     */
    public void pullInLeft() {
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleHelper.setLocale(getBaseContext(), SFASharedPref.getOurInstance().readString(PREF_SELECTED_LANGUAGE,"en"));
    }
}
