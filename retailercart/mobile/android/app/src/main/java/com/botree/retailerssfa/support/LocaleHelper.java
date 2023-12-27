package com.botree.retailerssfa.support;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import com.botree.retailerssfa.util.SFASharedPref;

import java.util.Locale;

import static com.botree.retailerssfa.util.SFASharedPref.PREF_SELECTED_LANGUAGE;

public class LocaleHelper {


    private LocaleHelper() {

    }

    public static Context onAttach(Context context) {
        String lang = getPersistedData(Locale.getDefault().getLanguage());
        return setLocale(context, lang);
    }

    public static Context onAttach(Context context, String defaultLanguage) {
        String lang = getPersistedData(defaultLanguage);
        return setLocale(context, lang);
    }

    public static Context setLocale(Context context, String language) {
        persist(language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }

        return updateResourcesLegacy(context, language);
    }

    private static String getPersistedData(String defaultLanguage) {
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        return preferences.readString(PREF_SELECTED_LANGUAGE, defaultLanguage);
    }

    private static void persist(String language) {
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        preferences.writeString(PREF_SELECTED_LANGUAGE, language);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        context.getResources().updateConfiguration(configuration, dm);

        return context.createConfigurationContext(configuration);
    }

    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }

}
