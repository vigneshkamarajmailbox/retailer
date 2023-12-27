/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.botree.retailerssfa.models.SalesHierarchy;
import com.botree.retailerssfa.models.StockistModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SFASharedPref {

    public static final String PREF_SELECTED_LANGUAGE = "SELECTED_LANGUAGE";

    public static final String PREF_NEWAPPDOWNLOADURL = "PREF_NEWAPPDOWNLOADURL";
    public static final String PREF_NEW_APK_VERSION = "PREF_NEWAPPVERSION";
    public static final String PREF_SALESMANCODE = "PREF_SALESMANCODE";
    public static final String PREF_CMP_CODE = "PREF_CMP_CODE";
    public static final String PREF_DISTRCODE = "PREF_DISTRCODE";
    public static final String PREF_DISTRBRCODE = "PREF_DISTRBRCODE";
    public static final String PREF_ROUTECODE = "PREF_ROUTECODE";
    public static final String PREF_CUSTOMERCODE = "PREF_CUSTOMERCODE";
    public static final String PREF_CUSTOMER_NAME = "PREF_CUSTOMER_NAME";
    public static final String PREF_URL = "PREF_URL";
    public static final String PREF_DAY_START_DATE = "PREF_DAYSTARTDATE";
    public static final String PREF_YEAR_MONTH_DATE = "PREF_YEAR_MONTH_DATE";
    public static final String PREF_APPNAME = "PREF_APPNAME";
    public static final String PREF_REMEMBER_CREDENTIAL = "REMEMBER_PASSWORD";
    public static final String PREF_ISNOTIFICATION_ON = "IS_NOTIFICATION_ON";
    public static final String PREF_AUTO_QUICK_ACCESS_VALUE = "PREF_VALUE";
    public static final String PREF_PREVIOUS_ORDER = "PREF_PRVIOUS_ORDER";
    public static final String PREF_CUSTOM_ORDER_SCREEN = "PREF_CUSTOM_ORDER_SCREEN";
    public static final String PREF_GEO_FENCING = "PREF_GEO_FENCING";
    public static final String PREF_CONFIG_USERCODE = "PREF_CONFIG_USERCODE";
    public static final String PREF_IS_ON_OFF_USER = "online_or_offline_user";
    public static final String PREF_USER_CODE = "pref_user_code";
    public static final String PREF_USER_CREDENTIAL = "PREF_USER_CREDENTIAL";
    public static final String PREF_USER_MOBILE_NO = "PREF_USER_MOBILE_NO";
    public static final String PREF_LOGIN_USER_NAME = "PREF_LOGINED_USER_NAME";
    public static final String PREF_USER_NAME = "pref_user_name";
    public static final String PREF_USER_TYPE = "pref_user_type";
    public static final String PREF_MAPPED_CODE = "pref_mapped_code";
    public static final String PREF_IS_LAST_LEVEL = "pref_is_last_level";
    public static final String PREF_HIER_LEVEL = "pref_hier_level";
    public static final String PREF_LAST_HIER_LEVEL = "pref_last_hier_level";
    public static final String PREF_LOGIN_STATUS = "pref_login_status";
    public static final String PREF_LOGIN_MESSAGE = "pref_login_message";
    public static final String PREF_VAN_SHOP = "PREF_VAN_SHOP";
    public static final String PREF_STOCK_UNLOADING = "PREF_STOCK_UNLOADING";
    public static final String PREF_CREDIT_NOTE = "PREF_CREDIT_NOTE";
    public static final String PREF_QUICK_ACTION = "pref_Quick_Action";
    public static final String PREF_FCM_TOKEN = "PREF_FCM_TOKEN";
    public static final String PREF_STOCKIST_POSITION = "PREF_STOCKIST_POSITION";
    public static final String PREF_STOCKIST_NAME = "PREF_STOCKIST_NAME";
    public static final String PREF_STOCKIST_SELECTED_CODE = "PREF_STOCKIST_SELECTED_CODE";
    public static final String PREF_AUTH_TOKEN = "pref_auth_token";
    public static final String PREF_DISTR_HIER_SELECTION_LEVEL = "pref_hierarchy_distr_level";
    public static final String PREF_SO_HIER_SELECTION_LEVEL = "pref_hierarchy_so_level";
    public static final String PREF_TEMP_QTY_UPDATE_ORDER_LIST = "pref_qtyUpdatedOrderBooking";
    public static final String PREF_TEMP_ORDER_BOOKING_LIST = "pref_orderBookingVOList";
    public static final String PREF_TEMP_SEARCH_LIST = "pref_tempSearchList";
    public static final String PREF_ORDER_BOOKING_OPTIONS = "pref_orderbooking_options";
    public static final String PREF_BOT_SERVICE = "pref_bot_service";
    public static final String PREF_PRODUCT_SEARCH_LEVEL = "pref_pro_search_level";
    public static final String PREF_PRODUCT_SEARCH_LEVEL_NAME = "pref_pro_search_level_name";
    public static final String CURRENT_SESSION = "CURRENT_SESSION";
    public static final String PREF_DIST_ADDRESS = "pref_dist_address";
    public static final String PREF_NEW_RETAILER_OBJECT = "pref_newRetailerObject";
    public static final String PREF_LAST_LAT = "pref_last_lat";
    public static final String PREF_LAST_LONG = "pref_last_long";
    private static final String SFA_SHARED_PREF = "sfaPrefs";
    public static final String PREF_TEMP_STOCKIST_LIST = "pref_stockist";
    public static final String PREF_INVOICE_NO = "pref_invoice_no";
    private static SFASharedPref instance;
    private SharedPreferences mPreference = null;

    public static final String PREF_LOGIN_CODE = "pref_loginCode";
    public static final String PREF_USER_STATUS = "pref_userStatus";
    public static final String PREF_NEW_PASS = "pref_newPassword";
    public static final String PREF_SALES_RETURN_LIST = "pref_sales_return_list";
    public static final String PREF_OPENING_STOCK = "pref_opening_stock";
    public static final String PREF_NEW_LIST = "pref_new_list";
    public static final String PREF_SCHEME_BUDGET_LIST = "pref_scheme_budget_list";
    public static final String PREF_SCHEME_PRODUCT_DETAIL = "pref_scheme_product_detail";
    public static final String PREF_BOOKING_VO = "pref_booking_vo";
    public static final String PREF_TEMP_BOOKING_VO = "pref_temp_booking_vo";
    public static final String PREF_LOGIN_ADV_ID = "pref_login_token";

    private SFASharedPref() {

    }

    /**
     * {@link SharedPreferences} instance returned using given context.
     *
     * @param context used to get the {@link SharedPreferences} instance
     * @return instance of {@link SharedPreferences}
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        if (context == null)
            return null;
        return context.getSharedPreferences(SFA_SHARED_PREF, Context.MODE_PRIVATE);
    }

    /**
     * Shared Preference s returned using given context.
     *
     * @param context used to get the {@link SharedPreferences} instance
     * @return instance of {@link android.content.SharedPreferences.Editor}
     */
    public static SharedPreferences.Editor getEditor(Context context) {
        if (context == null)
            return null;
        return getSharedPreferences(context).edit();
    }

    /**
     * Get the {@link android.content.SharedPreferences.Editor} from given {@link SharedPreferences}
     * instance.
     *
     * @param sharedPreferences used to get the editor
     * @return return the editor of given {@link SharedPreferences}
     */
    public static SharedPreferences.Editor getEditor(SharedPreferences sharedPreferences) {
        if (sharedPreferences == null)
            return null;
        return sharedPreferences.edit();
    }

    public static void initSFASharedPref(Context context) {
        if (instance == null) {
            instance = new SFASharedPref();
        }
        instance.setContext(context);
    }

    public static SFASharedPref getOurInstance() {
        return instance;
    }

    private void setContext(Context context) {
        mPreference = SFASharedPref.getSharedPreferences(context);
    }

    /**
     * @param tag  store the value to this tag
     * @param data to be stored
     */
    public void writeString(String tag, String data) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putString(tag, data).apply();
    }

    /**
     * @param tag used to read the stored data
     * @return return the stored data
     */
    public String readString(String tag) {
        return mPreference.getString(tag, "");
    }

    /**
     * @param tag used to read the stored data
     * @return return the stored data
     */
    public String readString(String tag, String value) {
        return mPreference.getString(tag, value);
    }

    public void writeBoolean(String tag, boolean data) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putBoolean(tag, data).apply();
    }

    public boolean readBoolean(String tag) {
        return mPreference.getBoolean(tag, false);
    }

    public boolean readBooleanDefaultTrue(String tag) {
        return mPreference.getBoolean(tag, true);
    }

    public boolean contains(String tag) {
        return mPreference.contains(tag);
    }


    public void clear(String tag) {
        mPreference.edit().remove(tag).apply();
    }

    public void saveSalesHierarchyObject(String tag, SalesHierarchy salesHierarchy) {
        SharedPreferences.Editor editor = mPreference.edit();
        Gson gson = new Gson();
        String json = gson.toJson(salesHierarchy);
        editor.putString(tag, json);
        editor.apply();
    }

    public SalesHierarchy readSalesHierarchyObj(String tag) {
        Gson gson = new Gson();
        String json = readString(tag);
        return gson.fromJson(json, SalesHierarchy.class);
    }

    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        writeString(key, json);
    }

    public void setObject(String key, Object list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        writeString(key, json);
    }

    public Object getObject(String key) {
        Gson gson = new Gson();
        String readString = readString(key);
        return gson.fromJson(readString, Object.class);
    }

    public List<Object> getList(String key) {
        Gson gson = new Gson();
        String readString = readString(key);
        Type listType = new TypeToken<Object>() {
        }.getType();
        return gson.fromJson(readString, listType);
    }

    public List<StockistModel> getListForStockist(String key) {
        Gson gson = new Gson();
        String readString = readString(key);
        Type listType = new TypeToken<List<StockistModel>>() {}.getType();
        return gson.fromJson(readString, listType);
    }

    public void writeInt(String tag, int data) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putInt(tag, data).apply();
    }

    public int readInt(String tag) {
        return mPreference.getInt(tag, 0);
    }


    public void saveMap(String key, Map<String, String> inputMap) {

        JSONObject jsonObject = new JSONObject(inputMap);
        String jsonString = jsonObject.toString();
        SharedPreferences.Editor editor = mPreference.edit();
        editor.remove(key).apply();
        editor.putString(key, jsonString);
        editor.commit();

    }

    public Map<String, String> loadMap(String key) {
        Map<String, String> outputMap = new HashMap<>();
        try {
            if (mPreference != null) {
                String jsonString = mPreference.getString(key, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while (keysItr.hasNext()) {
                    String k = keysItr.next();
                    String v = (String) jsonObject.get(k);
                    outputMap.put(k, v);
                }
            }
        } catch (Exception e) {
            Log.e("loadMap", "loadMap: " + e.getMessage(), e);
        }
        return outputMap;
    }

    public void convetAndSaveCustomObject(String key, Object list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        writeString(key, json);
    }
}
