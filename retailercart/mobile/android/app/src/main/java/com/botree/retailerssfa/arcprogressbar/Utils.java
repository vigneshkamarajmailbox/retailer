package com.botree.retailerssfa.arcprogressbar;

import android.content.Context;
import android.content.res.Resources;

import com.botree.retailerssfa.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;


public final class Utils {
    private Utils() {
    }
    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }
    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static String getJsonFromRaw(Context context, int id){
        InputStream is = context.getResources().openRawResource(id);
        Writer writer = new StringWriter();
        try {
        char[] buffer = new char[1024];
        try {
            Reader reader = null;
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return writer.toString();
    }
}
