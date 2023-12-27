package com.botree.retailerssfa.util;

import android.util.Log;

import com.botree.retailerssfa.BuildConfig;

/*
 *Created by Godlin Josheela Rani S on 23/4/19.
 */
public class LoggerUtil {
    private LoggerUtil() {
    }

    public static void printDebugLog(String tag, String message) {
        if (BuildConfig.DEBUG)
            Log.d(tag, message);
    }

    public static void printInfoLog(String tag, String message) {
        if (BuildConfig.DEBUG)
            Log.i(tag, message);
    }

    public static void printErrorLog(String tag, String message) {
        if (BuildConfig.DEBUG)
            Log.e(tag, message);
    }
}
