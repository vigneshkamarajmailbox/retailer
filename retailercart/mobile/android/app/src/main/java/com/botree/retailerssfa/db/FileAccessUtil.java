package com.botree.retailerssfa.db;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;

import com.botree.retailerssfa.support.Globals;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;

public class FileAccessUtil {
    private static FileAccessUtil fileAccessUtil;
    private static final String TAG = FileAccessUtil.class.getSimpleName();

    public static FileAccessUtil getInstance() {
        if (fileAccessUtil == null) {
            fileAccessUtil = new FileAccessUtil();
        }
        return fileAccessUtil;
    }

    /**
     * Using reflection to override default typeface
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
     *
     * @param context                    to work with assets
     * @param defaultFontNameToOverride  for example "monospace"
     * @param customFontFileNameInAssets file name of the font from assets
     */
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);
            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            //ignored
        }
    }

    /**
     * This method is used to encrypt the password.
     *
     * @param password password to be encrypted
     * @return encrypted password
     */
    public static String md5Encryption(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Log.e(Globals.TAG, e.getMessage(), e);
        }
        assert md != null;
        md.update(password.getBytes());

        byte[] byteData = md.digest();
        // convert the byte to hex format method 2
        StringBuilder hexString = new StringBuilder();
        for (byte aByteData : byteData) {
            String hex = Integer.toHexString(0xff & aByteData);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static boolean isUsbConnected(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"));
        if (intent != null) {
            return intent.getExtras().getBoolean("connected");
        }
        return false;
    }

    public File getExternalStoragePublicDirectory(String type) {
        return Environment.getExternalStoragePublicDirectory(type);
    }

    public File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    public Cipher getCipherInstance(String transformation) throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(transformation);
    }

    public Pattern compilePattern(String pattern) {
        return Pattern.compile(pattern);
    }

    public void setField(Object context, Field field, Object value, boolean isAccessible) throws IllegalAccessException {
        field.setAccessible(isAccessible);
        field.set(context, value);
    }

    public void setAccessibleField(Field field, boolean isAccessible) {
        field.setAccessible(isAccessible);
    }

    public void setAccessibleMethod(Method method, boolean isAccessible) {
        method.setAccessible(isAccessible);
    }

    public void setBooleanField(Field field, BottomNavigationMenuView bottomNavigationMenuView, boolean isAccessible) throws IllegalAccessException {
        field.setBoolean(bottomNavigationMenuView, isAccessible);
    }

    public SecretKeyFactory getSecretKeyFactoryInstance(String instance) throws NoSuchAlgorithmException {
        return SecretKeyFactory.getInstance(instance);
    }

    public String removeUrlFromAddress(String addressStr) {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(addressStr);
        int i = 0;
        while (m.find()) {
            addressStr = addressStr.replaceAll(m.group(i), "").trim();
            i++;
        }
        return addressStr;
    }

    public static LinearLayout.LayoutParams createWrapWrap() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static void deleteFileIfExist(File filePath) {
        try {
            if (filePath.delete()) {
                // Old File Deleted
            }
        } catch (Exception e) {
            Log.e(TAG, "createMatchMatchParam: " + e.getMessage(), e);
        }

    }
}
