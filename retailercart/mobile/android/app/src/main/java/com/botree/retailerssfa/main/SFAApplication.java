/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.main;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.botree.retailerssfa.controller.retrofit.ApiClient;
import com.botree.retailerssfa.db.FileAccessUtil;
import com.botree.retailerssfa.support.LocaleHelper;
import com.botree.retailerssfa.util.SFASharedPref;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import io.fabric.sdk.android.Fabric;

import static com.botree.retailerssfa.controller.retrofit.DataManager.initDataManager;
import static com.botree.retailerssfa.support.Globals.initGlobals;
import static com.botree.retailerssfa.util.AppUtils.initAppUtils;
import static com.botree.retailerssfa.util.DateUtil.initDateUtils;
import static com.botree.retailerssfa.util.NotificationUtils.initNotificationUtils;
import static com.botree.retailerssfa.util.OrderSupportUtil.initOrderSupportUtil;
import static com.botree.retailerssfa.util.PDFCreationUtil.initPDFCreationUtil;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SELECTED_LANGUAGE;
import static com.botree.retailerssfa.util.SFASharedPref.initSFASharedPref;

public class SFAApplication extends MultiDexApplication {

    public static final String VOLUME_TYPE = "Kg";
    private static final String LOG_TAG = SFAApplication.class.getSimpleName();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Fabric.with(this, new Crashlytics());

            FileAccessUtil.overrideFont(getApplicationContext(), "SERIF", "Oswald_Regular.ttf"); // font from assets

            initGlobals(this);
            initAppUtils(this);
            initDataManager(this);
            initNotificationUtils(this);
            initSFASharedPref(this);
            initDateUtils();
            initOrderSupportUtil(this);
            initPDFCreationUtil(this);

            ApiClient apiClient = ApiClient.getApiClient();
            apiClient.init(this);
            FirebaseApp.initializeApp(this);
            initImageLoader(this);

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }

    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleHelper.setLocale(getBaseContext(), SFASharedPref.getOurInstance().readString(PREF_SELECTED_LANGUAGE,"en"));
    }
}
