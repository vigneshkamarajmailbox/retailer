package com.botree.retailerssfa.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.support.LocaleHelper;
import com.botree.retailerssfa.util.SFASharedPref;

import static com.botree.retailerssfa.util.SFASharedPref.PREF_SELECTED_LANGUAGE;

/**
 * Created by shantarao on 8/2/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private TextView toolbarTitle;
    private TextView toolbarSubTitle;

    /**
     * used to hide keyboard from view
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * This Toolbar is used to set in the action bar.
     *
     * @return {@link Toolbar}
     */
    public abstract Toolbar getBaseToolbar();

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.slide_up, R.anim.stay);

    }

    protected void initToolbar() {

        Toolbar toolbar = getBaseToolbar();
        toolbarTitle = findViewById(R.id.custom_toolbar_title);
        toolbarSubTitle = findViewById(R.id.custom_toolbar_subtitle);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        initMaterialToolbar();
    }

    /**
     * Set or clear status bar color based on above KITKAT or not}
     */
    private void initMaterialToolbar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    /**
     * @param text set as Toolbar title text
     */
    public void setBaseToolbarTitle(String text, String subTitle) {

        if (toolbarTitle != null && text != null && !text.equalsIgnoreCase("null")) {
            toolbarTitle.setText(text);
        }

        if (toolbarSubTitle != null && subTitle != null && !subTitle.equalsIgnoreCase("null")) {
            toolbarSubTitle.setVisibility(View.VISIBLE);
            toolbarSubTitle.setText(subTitle);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleHelper.setLocale(getBaseContext(), SFASharedPref.getOurInstance().readString(PREF_SELECTED_LANGUAGE,"en"));
    }
}
