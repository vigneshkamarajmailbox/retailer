package com.botree.retailerssfa.db.query;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.botree.retailerssfa.db.BaseDB;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.LoginModel;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.LoggerUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.botree.retailerssfa.db.SFADatabase.queryDeleteTable;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LATITUDE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LOGIN_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LOGIN_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LONGITUDE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_NEW_PASSWORD;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROCESS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SYNC_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SYNC_END_TIME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SYNC_TIME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UPLOAD;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_USER_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_USER_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.CREDENTIAL;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_LOGIN;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_LOGIN_TIME_CAPTURE;
import static com.botree.retailerssfa.db.query.IDBColumns.USER_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.USER_TYPE;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_USER_COUNT;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_USER_COUNT_BASED_ON_ID;

/*
 *Created by Godlin Josheela Rani S on 23/4/19.
 */
public class LoginQueryHelper {
    final String TAG = LoginQueryHelper.class.getSimpleName();

    private static final String QUERY_SELECT_PASSWORD_BASED_ON_LOGIN_ID = "SELECT password FROM " + TABLE_LOGIN + " WHERE (LOWER(" + COLUMN_LOGIN_CODE + ") = ?)";
    private static final String QUERY_SELECT_LOGGED_IN_USER_BASED_ON_ID = "SELECT * FROM " + TABLE_LOGIN + " WHERE (LOWER(" + COLUMN_LOGIN_CODE + ") = ? AND " + CREDENTIAL + "=?)";

    public void deleteLoggedInUser(BaseDB baseDB) {
        baseDB.openWritableDb();
        baseDB.getDb().execSQL(queryDeleteTable(TABLE_LOGIN));
        baseDB.closeDb();
    }

    public void insertUser(LoginModel loginModel, BaseDB baseDB) {
        LoggerUtil.printDebugLog("insertUser", "loginModel : " + loginModel.toString());
        baseDB.openWritableDb();
        SQLiteDatabase db = baseDB.getDb();
        try {
            ContentValues contentValues = getContentValues(loginModel);
            db.insert(TABLE_LOGIN, null, contentValues);
        } finally {
            baseDB.closeDb();
        }
    }

    public LoginModel getLoggedInUserBasedOnId(BaseDB baseDB, String userCode, String password) {
        LoginModel loginModel = null;
        baseDB.openWritableDb();

        try {
            Cursor cm = baseDB.getDb().rawQuery(QUERY_SELECT_LOGGED_IN_USER_BASED_ON_ID,
                    new String[]{userCode.toLowerCase(Locale.getDefault()), password});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
                loginModel = getLoginModelFromCursor(cm);
//                loginModel.setNewPassword("Y");
            }
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            LoggerUtil.printErrorLog(TAG, "getUserDetail: " + e.getMessage());
        }
        return loginModel;
    }

    public void updatePasswordChangedInLogin(BaseDB baseDB, String userCode, String password){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NEW_PASSWORD, "N");

        String appendAnd = " = ? and ";
        String update = COLUMN_LOGIN_CODE + appendAnd + CREDENTIAL +" = ?";
        String[] updateArgs = {userCode, password};
        baseDB.getDb().update(TABLE_LOGIN, values, update, updateArgs);
        baseDB.closeDb();
    }

    public String getPasswordBasedOnLoginId(BaseDB baseDB, String userCode) {
        String password = null;
        baseDB.openWritableDb();

        try {
            Cursor cm = baseDB.getDb().rawQuery(QUERY_SELECT_PASSWORD_BASED_ON_LOGIN_ID,
                    new String[]{userCode.toLowerCase(Locale.getDefault())});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
                password = cm.getString(cm.getColumnIndex(CREDENTIAL));
            }
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            LoggerUtil.printErrorLog(TAG, "getUserDetail: " + e.getMessage());
        }
        return password;
    }

    private LoginModel getLoginModelFromCursor(Cursor cm) {
        return new LoginModel(
                cm.getString(cm.getColumnIndex(COLUMN_LOGIN_CODE)),
                cm.getString(cm.getColumnIndex(CREDENTIAL)),
                cm.getString(cm.getColumnIndex(COLUMN_USER_NAME)),
                cm.getString(cm.getColumnIndex(COLUMN_USER_STATUS)),
                cm.getString(cm.getColumnIndex(COLUMN_NEW_PASSWORD)),
                Boolean.getBoolean(cm.getString(cm.getColumnIndex(COLUMN_LOGIN_STATUS))));
    }

    public boolean isLoggedInUserAvailable(SFADatabase sfaDatabase, String loginCode) {
        sfaDatabase.openWritableDb();
        String getCountOfLoginTableBasedOnId = "SELECT count(*) FROM " + TABLE_LOGIN + " WHERE " + COLUMN_LOGIN_CODE + "=?";
        Cursor cursor = sfaDatabase.getDb().rawQuery(getCountOfLoginTableBasedOnId, new String[]{loginCode});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        LoggerUtil.printDebugLog(TAG, "isLoggedInUserAvailable : count : " + count);
        return count > 0;
    }

    private ContentValues getContentValues(LoginModel loginModel) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN_CODE, loginModel.getLoginCode());
        values.put(CREDENTIAL, loginModel.getPassword());
        values.put(COLUMN_USER_NAME, loginModel.getUserName());
        values.put(COLUMN_USER_STATUS, loginModel.getUserStatus());
        values.put(COLUMN_NEW_PASSWORD,loginModel.getNewPassword());
        values.put(COLUMN_LOGIN_STATUS, loginModel.isLoginStatus());
        return values;
    }

    public void insertLoginTime(LoginModel loginModel, String onlineOrOffline, String processName, BaseDB baseDB, String status, String currentTime) {
        baseDB.openWritableDb();
        SQLiteDatabase db = baseDB.getDb();
        try {
            ContentValues contentValues = insertLoginDateTime(loginModel, onlineOrOffline, processName, status, currentTime);
            db.insert(TABLE_LOGIN_TIME_CAPTURE, null, contentValues);
        } finally {
            baseDB.closeDb();
        }
    }


    private ContentValues insertLoginDateTime(LoginModel loginModel, String onlineOrOffline, String processName, String status, String currentTime) {

        SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
        String strLatitude = sfaSharedPref.readString(SFASharedPref.PREF_LAST_LAT);
        String strLongitude = sfaSharedPref.readString(SFASharedPref.PREF_LAST_LONG);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String formattedDate = df.format(c);
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String formattedTime = tf.format(c);

        ContentValues values = new ContentValues();
        values.put(USER_CODE, loginModel.getLoginCode());
        values.put(USER_TYPE, "DISTR");
        values.put(COLUMN_DATE, String.valueOf(new Date().getTime()));
        values.put(COLUMN_MODE, onlineOrOffline);
        values.put(COLUMN_PROCESS, processName);
        values.put(COLUMN_LATITUDE, strLatitude);
        values.put(COLUMN_LONGITUDE, strLongitude);
        values.put(COLUMN_UPLOAD, "N");
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_SYNC_DATE, DateUtil.getCurrentYearMonthDate());
        if(currentTime != null && !currentTime.isEmpty()) {
            values.put(COLUMN_SYNC_TIME, currentTime);
        }else{
            values.put(COLUMN_SYNC_TIME, formattedTime);
        }
        values.put(COLUMN_SYNC_END_TIME, formattedTime);
        return values;
    }

    public int getUserCountBasedOnId(BaseDB baseDB, String userName) {
        int count = 0;
        baseDB.openWritableDb();

        try {
            Cursor cm = baseDB.getDb().rawQuery(QUERY_SELECT_USER_COUNT_BASED_ON_ID, new String[]{userName.toUpperCase(Locale.getDefault()), userName});
            if (cm != null ) {
                count =  cm.getCount();
                cm.close();
            }
            baseDB.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "getUserCountBasedOnId: " + e.getMessage(), e);
            return count;
        }
        Log.d(TAG, "getUserCountBasedOnId: " + count);

        return count;
    }

    public int getUserCount(BaseDB baseDB, String loginCode) {
        int count = 0;
        baseDB.openWritableDb();

        try {
            Cursor cm = baseDB.getDb().rawQuery(QUERY_SELECT_USER_COUNT, new String[]{loginCode});
            if (cm != null ) {
                count =  cm.getCount();
                cm.close();
            }
            baseDB.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "getUserCount: " + e.getMessage(), e);
            return count;
        }
        Log.d(TAG, "getUserCount: " + count);

        return count;
    }
}
