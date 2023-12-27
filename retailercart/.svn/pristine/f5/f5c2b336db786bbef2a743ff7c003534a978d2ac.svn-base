package com.botree.retailerssfa.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.models.CustomerModel;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.FeedBackMasterModel;
import com.botree.retailerssfa.models.FeedbackModel;
import com.botree.retailerssfa.models.KycModel;
import com.botree.retailerssfa.models.NewOutletImageList;
import com.botree.retailerssfa.models.PendingVO;
import com.botree.retailerssfa.models.RetailerCategoryVO;
import com.botree.retailerssfa.models.RetailerVO;
import com.botree.retailerssfa.models.SalesHierarchy;
import com.botree.retailerssfa.models.SalesmanMasterCheckBoxModel;
import com.botree.retailerssfa.models.SalesmanMasterModel;
import com.botree.retailerssfa.models.ScreenConfig;
import com.botree.retailerssfa.models.StockistModel;
import com.botree.retailerssfa.models.UserModel;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.LoggerUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.botree.retailerssfa.controller.constants.AppConstant.USER_TYPE_ISR;
import static com.botree.retailerssfa.db.query.IDBColumns.*;
import static com.botree.retailerssfa.db.query.ISFADB.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.ISFADB.COLUMN_DISTR_BR_CODE;
import static com.botree.retailerssfa.db.query.ISFADB.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.ISFADB.COLUMN_SALESMAN_CODE;
import static com.botree.retailerssfa.db.query.ISFADB.CREDENTIAL;
import static com.botree.retailerssfa.db.query.ISFADB.TABLE_USER;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.GET_DISTRIBUTOR_LIST;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_ALL_NEW_SALESMAN_LOB_MAP;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_ALL_NEW_SALESMAN_ROUTE_MAP;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_CHECK_IS_TRANSACTION_IN_DB;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_FETCH_KYC_MASTER;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_FETCH_SALESMAN_FOR_UPLOAD;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_FETCH_SALESMAN_KYC_FOR_UPLOAD;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_FETCH_SALESMAN_LOB_UPLOAD;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_FETCH_SALESMAN_ROUTE_UPLOAD;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_INSERT_SALES_HIERARCHY;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_INSERT_SALES_HIERARCHY_VALUE;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_INSERT_SCREEN_CONFIGS;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_LOADING_STOCK;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_NEW_SALESMAN_KYC_MAP;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_ALL_CUSTOMERS;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_ALL_CUSTOMERS_BILL;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_ALL_NEW_SALESMAN;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_CUSTOMERS;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_CUSTOMER_SALESMAN_MASTER;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_DISTRIBUTOR;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_DISTRIBUTOR_INFO;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_LOGIN_DISTR_SATE_CODE;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_LOGIN_USER;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_NEW_SALESMAN;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_RETAILERS;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_RETAILERS_GROUP;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_RETAILER_BY_ROUTE;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_RETAILER_BY_ROUTE_BILL;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_RETAILER_CHANNELS;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_RETAILER_CLASS_DATA;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_RETAILER_FOR_ROUTE_SEQ;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_SALESMAN_MASTER;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_SALESMAN_MASTER_ACTIVE;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_SUB_CHANNELS;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.QUERY_SELECT_USER_DETAILS;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.queryDeleteTable;
import static com.botree.retailerssfa.db.query.UserDbQueryHelper.selectAllFormTableQuery;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MOD_DT;

class UserDbHelper {
    private static final String TAG = UserDbHelper.class.getSimpleName();

    private static final String DUPLICATE_TAG = "Duplicate";

    UserDbHelper() {
    }

    void insertUser(UserModel userModel, BaseDB baseDB) {

        baseDB.openWritableDb();
        SQLiteDatabase db = baseDB.getDb();
        try {
            ContentValues contentValues = getContentValues(userModel);
            db.insert(TABLE_USER, null, contentValues);
        } finally {
            baseDB.closeDb();
        }

    }


    void updateDistributor(Distributor distributor, BaseDB baseDB) {
        baseDB.openWritableDb();
        SQLiteDatabase db = baseDB.getDb();
        try {
            ContentValues contentValues = updateDistributor(distributor);
            String update = COLUMN_DISTR_CODE + "=?";

            db.update(TABLE_DISTRIBUTOR, contentValues, update, new String[]{distributor.getDistributorCode()});
        } finally {
            baseDB.closeDb();
        }
    }

    void insertLoginTime(UserModel userModel, String onlineOrOffline, String processName, BaseDB baseDB, String status, String currentTime) {

        baseDB.openWritableDb();
        SQLiteDatabase db = baseDB.getDb();
        try {
            ContentValues contentValues = insertLoginDateTime(userModel, onlineOrOffline, processName, status, currentTime);
            db.insert(TABLE_LOGIN_TIME_CAPTURE, null, contentValues);
        } finally {
            baseDB.closeDb();
        }
    }

    void insertStockistList(BaseDB baseDB, UserModel userModel) {

        baseDB.openWritableDb();
        SQLiteDatabase db = baseDB.getDb();
        try {
            if (userModel.getStockistModelList() != null) {
                for (StockistModel stockistModel : userModel.getStockistModelList()) {
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_CMP_CODE, stockistModel.getCmpCode());
                    values.put(ISR_CODE, stockistModel.getIsrCode());
                    values.put(ISR_NAME, stockistModel.getIsrName());
                    values.put(COLUMN_SUPER_STOCKIST_CODE, stockistModel.getSuperStockistCode());
                    values.put(COLUMN_SUPER_STOCKIST_NAME, stockistModel.getSuperStockistName());
                    values.put(COLUMN_STOCKIST_CODE, stockistModel.getStockistCode());
                    values.put(COLUMN_STOCKIST_NAME, stockistModel.getStockistName());
                    values.put(COLUMN_SALESMAN_CODE, stockistModel.getDistrSalesmanCode());
                    values.put(COLUMN_SALESMAN_NAME, stockistModel.getDistrSalesmanName());
                    values.put(SH_LAST_LEVEL_CODE, stockistModel.getShLastLevelCode());
                    values.put(SH_LAST_LEVEL_NAME, stockistModel.getShLastLevelName());
                    values.put(LOB, stockistModel.getLobCode());
                    values.put(GST_DIST_STATE_CODE, stockistModel.getGstDistrStateCode());

                    db.insert(TABLE_STOCKIST, null, values);
                }

            }
        } finally {
            baseDB.closeDb();
        }
    }

    private ContentValues getContentValues(UserModel users) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, users.getCmpCode());
        values.put(USER_CODE, users.getUserCode());
        values.put(USER_NAME, users.getUserName());
        values.put(USER_TYPE, users.getUserType());
        values.put(IS_LAST_LEVEL, users.getLastLevel());
        values.put(HIER_LEVEL, users.getHierLevel());
        values.put(CREDENTIAL, users.getCredential());
        values.put(COLUMN_MOBILE_NO, users.getMobileNo());

        if (users.getUserType().equalsIgnoreCase(USER_TYPE_ISR)) {

            values.put(COLUMN_SALESMAN_CODE, users.getStockistModel().getDistrSalesmanCode());
            values.put(CONFIG_USER_CODE, users.getUsers().getConfigUserCode());
            values.put(COLUMN_DISTR_CODE, users.getStockistModel().getStockistCode());
            values.put(COLUMN_DISTR_BR_CODE, users.getStockistModel().getStockistCode());
            values.put(SH_LAST_LEVEL_CODE, users.getStockistModel().getShLastLevelCode());
            values.put(SH_LAST_LEVEL_NAME, users.getStockistModel().getShLastLevelName());
            values.put(LOB, users.getStockistModel().getLobCode());
            values.put(GST_DIST_STATE_CODE, users.getStockistModel().getGstDistrStateCode());
            values.put(MAPPED_CODE, users.getStockistModel().getDistrSalesmanCode());

        } else {
            values.put(COLUMN_SALESMAN_CODE, users.getMappedCode());
            values.put(CONFIG_USER_CODE, users.getUsers().getConfigUserCode());
            values.put(COLUMN_DISTR_CODE, users.getUsers().getDistrCode());
            values.put(COLUMN_DISTR_BR_CODE, users.getUsers().getDistrCode());
            values.put(SH_LAST_LEVEL_CODE, users.getUsers().getShLastLevelCode());
            values.put(SH_LAST_LEVEL_NAME, users.getUsers().getShLastLevelName());
            values.put(LOB, users.getUsers().getLobCode());
            values.put(GST_DIST_STATE_CODE, users.getUsers().getGstDistrStateCode());
            values.put(MAPPED_CODE, users.getMappedCode());
        }
        return values;
    }

    private ContentValues insertLoginDateTime(UserModel users, String onlineOrOffline, String processName, String status, String currentTime) {

        SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
        String strLatitude = sfaSharedPref.readString(SFASharedPref.PREF_LAST_LAT);
        String strLongitude = sfaSharedPref.readString(SFASharedPref.PREF_LAST_LONG);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String formattedDate = df.format(c);
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String formattedTime = tf.format(c);

        ContentValues values = new ContentValues();
        values.put(USER_CODE, users.getUserCode());
        values.put(USER_TYPE, users.getUserType());
        values.put(COLUMN_DATE, String.valueOf(new Date().getTime()));
        values.put(COLUMN_MODE, onlineOrOffline);
        values.put(COLUMN_PROCESS, processName);
        values.put(COLUMN_LATITUDE, strLatitude);
        values.put(COLUMN_LONGITUDE, strLongitude);
        values.put(COLUMN_UPLOAD, "N");
        values.put(COLUMN_STATUS, status);
        values.put(COLUMN_SYNC_DATE, DateUtil.getCurrentYearMonthDate());
        values.put(COLUMN_SYNC_TIME, currentTime);
        values.put(COLUMN_SYNC_END_TIME, formattedTime);
        return values;
    }

    private ContentValues updateDistributor(Distributor distributor) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, distributor.getCompanyCode());
        values.put(COLUMN_DISTR_NAME, distributor.getDistributorName());
        values.put(COLUMN_DISTR_ADDRESS1, distributor.getDistributorAddress1());
        values.put(COLUMN_DISTR_ADDRESS2, distributor.getDistributorAddress2());
        values.put(COLUMN_DISTR_ADDRESS3, distributor.getDistributorAddress3());
        values.put(COLUMN_PINCODE, distributor.getPincode());
        values.put(COLUMN_PHONE_NUMBER, distributor.getPhoneNumber());
        values.put(COLUMN_MOBILE_NO, distributor.getMobileNumber());
        values.put(COLUMN_CONTACT_PERSON, distributor.getContactPerson());
        values.put(COLUMN_MAIL_ID, distributor.getMailId());
        values.put(COLUMN_BRANCH_CODE, distributor.getBranchCode());
        values.put(COLUMN_BRANCH_NAME, distributor.getBranchName());
        values.put(COLUMN_GST_STATE_CODE, distributor.getGstStateCode());
        values.put(COLUMN_GEO_HIER_PATH, distributor.getGeoHierPath());
        values.put(COLUMN_DRUG_LICENCE_NO1, distributor.getDrugLicenceNumber1());
        values.put(COLUMN_DRUG_LICENCE_EXPIRY1, System.currentTimeMillis());
        values.put(COLUMN_DAY_OFF, distributor.getDayOff());
        values.put(COLUMN_FSSAI_NO, distributor.getFssaiNumber());
        values.put(COLUMN_DISTR_TYPE_NAME, distributor.getDistributorTypeName());
        values.put(COLUMN_GST_DISTR_TYPE, distributor.getGstDistributorType());
        values.put(COLUMN_GSTIN_NO, distributor.getGstinNumber());
        values.put(COLUMN_PAN_NO, distributor.getPanNumber());
        values.put(COLUMN_AADHAR_NO, distributor.getAadharNumber());
        values.put(COLUMN_UPLOAD, "N");
        values.put(COLUMN_FSSAI_EXPIRY_DATE, distributor.getFssaiExpiryDate());
        return values;
    }

    void deleteUser(BaseDB baseDB) {
        baseDB.openWritableDb();
        baseDB.getDb().execSQL(queryDeleteTable(TABLE_USER));
        baseDB.closeDb();
    }

    void deleteStockist(BaseDB baseDB) {
        baseDB.openWritableDb();
        baseDB.getDb().execSQL(queryDeleteTable(TABLE_STOCKIST));
        baseDB.closeDb();
    }


    private UserModel getUSerModel(Cursor cm) {
        UserModel users = new UserModel();
        users.setCmpCode(cm.getString(cm.getColumnIndex(COLUMN_CMP_CODE)));
        users.getUsers().setDistrCode(cm.getString(cm.getColumnIndex(COLUMN_DISTR_CODE)));
        users.getUsers().setShLastLevelCode(cm.getString(cm.getColumnIndex(SH_LAST_LEVEL_CODE)));
        users.getUsers().setSalesmanName(cm.getString(cm.getColumnIndex(SH_LAST_LEVEL_NAME)));
        users.getUsers().setLobCode(cm.getString(cm.getColumnIndex(LOB)));
        users.getUsers().setSalesmanCode(cm.getString(cm.getColumnIndex(COLUMN_SALESMAN_CODE)));
        users.getUsers().setSalesmanName(cm.getString(cm.getColumnIndex(USER_NAME)));
        users.setUserCode(cm.getString(cm.getColumnIndex(USER_CODE)));
        users.setUserType(cm.getString(cm.getColumnIndex(USER_TYPE)));
        users.setLastLevel(Boolean.valueOf(cm.getString(cm.getColumnIndex(IS_LAST_LEVEL))));
        users.getUsers().setGstDistrStateCode(cm.getString(cm.getColumnIndex(GST_DIST_STATE_CODE)));
        users.setHierLevel(cm.getString(cm.getColumnIndex(HIER_LEVEL)));
        return users;
    }

    private Distributor getDistributorModel(Cursor cm) {
        Distributor distributor = new Distributor();

        distributor.setCompanyCode(cm.getString(cm.getColumnIndex(COLUMN_CMP_CODE)));
        distributor.setDistributorCode(cm.getString(cm.getColumnIndex(COLUMN_DISTR_CODE)));
        distributor.setDistributorName(cm.getString(cm.getColumnIndex(COLUMN_DISTR_NAME)));
        distributor.setDistributorAddress1(cm.getString(cm.getColumnIndex(COLUMN_DISTR_ADDRESS1)));
        distributor.setDistributorAddress2(cm.getString(cm.getColumnIndex(COLUMN_DISTR_ADDRESS2)));
        distributor.setDistributorAddress3(cm.getString(cm.getColumnIndex(COLUMN_DISTR_ADDRESS3)));
        distributor.setPincode(cm.getString(cm.getColumnIndex(COLUMN_PINCODE)));
        distributor.setPhoneNumber(cm.getString(cm.getColumnIndex(COLUMN_PHONE_NUMBER)));
        distributor.setMobileNumber(cm.getString(cm.getColumnIndex(COLUMN_MOBILE_NO)));
        distributor.setContactPerson(cm.getString(cm.getColumnIndex(COLUMN_CONTACT_PERSON)));
        distributor.setMailId(cm.getString(cm.getColumnIndex(COLUMN_MAIL_ID)));
        distributor.setBranchCode(cm.getString(cm.getColumnIndex(COLUMN_BRANCH_CODE)));
        distributor.setBranchName(cm.getString(cm.getColumnIndex(COLUMN_BRANCH_NAME)));
        distributor.setGstStateCode(cm.getString(cm.getColumnIndex(COLUMN_GST_STATE_CODE)));
        distributor.setGeoStateCode(cm.getString(cm.getColumnIndex(COLUMN_GEO_STATE_CODE)));
        distributor.setGeoHierPath(cm.getString(cm.getColumnIndex(COLUMN_GEO_HIER_PATH)));
        distributor.setDrugLicenceNumber1(cm.getString(cm.getColumnIndex(COLUMN_DRUG_LICENCE_NO1)));
        distributor.setDrugLicenceExpiryDate1(cm.getString(cm.getColumnIndex(COLUMN_DRUG_LICENCE_EXPIRY1)));
        distributor.setDayOff(cm.getInt(cm.getColumnIndex(COLUMN_DAY_OFF)));
        distributor.setFssaiNumber(cm.getString(cm.getColumnIndex(COLUMN_FSSAI_NO)));
        distributor.setDistributorTypeName(cm.getString(cm.getColumnIndex(COLUMN_DISTR_TYPE_NAME)));
        distributor.setGstDistributorType(cm.getString(cm.getColumnIndex(COLUMN_GST_DISTR_TYPE)));
        distributor.setGstinNumber(cm.getString(cm.getColumnIndex(COLUMN_GSTIN_NO)));
        distributor.setPanNumber(cm.getString(cm.getColumnIndex(COLUMN_PAN_NO)));
        distributor.setAadharNumber(cm.getString(cm.getColumnIndex(COLUMN_AADHAR_NO)));
        distributor.setPdaDistributor(cm.getString(cm.getColumnIndex(COLUMN_PDA_DISTRIBUTOR)));
        distributor.setDistrStatus(cm.getString(cm.getColumnIndex(COLUMN_DISTR_STATUS)));
        distributor.setUploadFlag(cm.getString(cm.getColumnIndex(COLUMN_UPLOAD)));
        distributor.setFssaiExpiryDate(cm.getString(cm.getColumnIndex(COLUMN_FSSAI_EXPIRY_DATE)));

        return distributor;
    }

    List<UserModel> getUserDetail(BaseDB baseDB, String userCode) {


        List<UserModel> userList = new ArrayList<>();
        baseDB.openWritableDb();

        try {
            Cursor cm = baseDB.getDb().rawQuery(QUERY_SELECT_USER_DETAILS, new String[]{userCode.toLowerCase(Locale.getDefault()), userCode.toLowerCase(Locale.getDefault())});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
                UserModel users = getUSerModel(cm);
                users.setCredential(cm.getString(cm.getColumnIndex(CREDENTIAL)));
                userList.add(users);
            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getUserDetail: " + e.getMessage(), e);
        }
        return userList;
    }

    Distributor getDistributor(BaseDB baseDB, String userCode) {

        Distributor distributor = null;
        baseDB.openWritableDb();

        try {
            Cursor cm = baseDB.getDb().rawQuery(QUERY_SELECT_DISTRIBUTOR, new String[]{userCode});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
                distributor = getDistributorModel(cm);
            }
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getDistributor: " + e.getMessage(), e);
        }
        return distributor;
    }

    void insertScreenConfig(List<ScreenConfig> configModel, BaseDB db) {
        SQLiteStatement statement = db.getDb().compileStatement(QUERY_INSERT_SCREEN_CONFIGS);
        for (int i = 0; i < configModel.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, configModel.get(i).getLogincode());
            statement.bindString(2, String.valueOf(configModel.get(i).getModuleNo()));
            statement.bindString(3, configModel.get(i).getModuleName());
            statement.bindString(4, String.valueOf(configModel.get(i).getScreenNo()));
            statement.bindString(5, configModel.get(i).getScreenName());
            statement.bindString(6, String.valueOf(configModel.get(i).getChecked()));
            statement.bindString(7, String.valueOf(configModel.get(i).getSequence()));
            statement.bindString(8, String.valueOf(configModel.get(i).getGroupCode()));
            statement.bindString(9, String.valueOf(configModel.get(i).getGroupName()));
            statement.bindString(10, String.valueOf(configModel.get(i).getScreenType()));

            statement.execute();
        }
    }

    void deleteScreenConfiguration(BaseDB baseDB) {
        baseDB.openWritableDb();
        baseDB.getDb().execSQL(queryDeleteTable(TABLE_SCREEN_CONFIGURATION));
        baseDB.closeDb();
    }

    List<UserModel> userLoginSuccess(BaseDB baseDB, String username, String pass) {
        List<UserModel> userList = new ArrayList<>();
        baseDB.openWritableDb();

        Cursor cm = baseDB.getDb().rawQuery(QUERY_SELECT_LOGIN_USER, new String[]{username.toLowerCase(Locale.getDefault()), username, pass});
        if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
            UserModel users = new UserModel();
            users.setCmpCode(cm.getString(cm.getColumnIndex(COLUMN_CMP_CODE)));
            users.getUsers().setDistrCode(cm.getString(cm.getColumnIndex(COLUMN_DISTR_CODE)));
            users.getUsers().setShLastLevelCode(cm.getString(cm.getColumnIndex(SH_LAST_LEVEL_CODE)));
            users.getUsers().setShLastLevelName(cm.getString(cm.getColumnIndex(SH_LAST_LEVEL_NAME)));
            users.getUsers().setLobCode(cm.getString(cm.getColumnIndex(LOB)));
            users.getUsers().setSalesmanCode(cm.getString(cm.getColumnIndex(COLUMN_SALESMAN_CODE)));
            users.getUsers().setSalesmanName(cm.getString(cm.getColumnIndex(USER_NAME)));
            users.setUserName(cm.getString(cm.getColumnIndex(USER_NAME)));
            users.getUsers().setConfigUserCode(cm.getString(cm.getColumnIndex(CONFIG_USER_CODE)));
            users.setUserType(cm.getString(cm.getColumnIndex(USER_TYPE)));
            users.setLastLevel(Boolean.valueOf(cm.getString(cm.getColumnIndex(IS_LAST_LEVEL))));
            users.getUsers().setGstDistrStateCode(cm.getString(cm.getColumnIndex(GST_DIST_STATE_CODE)));
            users.setHierLevel(cm.getString(cm.getColumnIndex(HIER_LEVEL)));
            users.setMappedCode(cm.getString(cm.getColumnIndex(COLUMN_SALESMAN_CODE)));
            users.setCredential(cm.getString(cm.getColumnIndex(CREDENTIAL)));
            users.setUserCode(cm.getString(cm.getColumnIndex(USER_CODE)));
            users.setMobileNo(cm.getString(cm.getColumnIndex(COLUMN_MOBILE_NO)));
            userList.add(users);

        } while (cm.moveToNext());
        if (cm != null) {
            cm.close();
        }
        baseDB.closeDb();

        return userList;
    }

    Boolean istUserAvailable(BaseDB baseDB) {

        baseDB.openWritableDb();

        String query = selectAllFormTableQuery(TABLE_LOGIN);
        Cursor cm = baseDB.getDb().rawQuery(query, null);
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        baseDB.closeDb();

        return false;
    }


    boolean isProductAvailable(BaseDB baseDB) {

        baseDB.openWritableDb();
        String query = selectAllFormTableQuery(TABLE_DISTRIBUTOR);
        Cursor cm = baseDB.getDb().rawQuery(query, null);
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        baseDB.closeDb();

        return false;
    }


    boolean isProductVanSalesAvailable(BaseDB baseDB) {

        baseDB.openWritableDb();
        String query = selectAllFormTableQuery(TABLE_VANSALES_PRODUCTS);
        Cursor cm = baseDB.getDb().rawQuery(query, null);
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        baseDB.closeDb();

        return false;
    }


    boolean isUOMAvailable(BaseDB baseDB) {

        baseDB.openWritableDb();
        String query = selectAllFormTableQuery(TABLE_DISTRIBUTOR);
        Cursor cm = baseDB.getDb().rawQuery(query, null);
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        baseDB.closeDb();

        return false;
    }


    boolean isUOMVanSalesAvailable(BaseDB baseDB) {

        baseDB.openWritableDb();
        String query = selectAllFormTableQuery(TABLE_VANSALES_UOM_MASTER);
        Cursor cm = baseDB.getDb().rawQuery(query, null);
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        baseDB.closeDb();

        return false;
    }


    boolean isVanSalesDataAvailable(BaseDB baseDB) {

        baseDB.openWritableDb();
        Cursor cm = baseDB.getDb().rawQuery(QUERY_LOADING_STOCK, null);
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        baseDB.closeDb();

        return false;
    }


    List<RetailerCategoryVO> loadChannelData(SFADatabase sfaDatabase, String prefDistrcode) {

        List<RetailerCategoryVO> retailerCategoryList = new ArrayList<>();

        sfaDatabase.openWritableDb();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_RETAILER_CHANNELS, new String[]{prefDistrcode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                RetailerCategoryVO retailerCategoryvo = new RetailerCategoryVO();
                retailerCategoryvo.setStrChannelName(c.getString(c.getColumnIndex(COLUMN_CHANNEL_NAME)).trim());
                retailerCategoryvo.setStrChannelCode(c.getString(c.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                retailerCategoryList.add(retailerCategoryvo);
            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return retailerCategoryList;

    }

    List<RetailerCategoryVO> loadSubChannelData(SFADatabase sfaDatabase, String prefDistrcode, String channelCode) {

        List<RetailerCategoryVO> retailerCategoryList = new ArrayList<>();

        sfaDatabase.openWritableDb();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_SUB_CHANNELS, new String[]{prefDistrcode, channelCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                RetailerCategoryVO retailerCategoryvo = new RetailerCategoryVO();
                retailerCategoryvo.setStrSubChannelCode(c.getString(c.getColumnIndex(COLUMN_SUB_CHANNEL_CODE)).trim());
                retailerCategoryvo.setStrSubChannelName(c.getString(c.getColumnIndex(COLUMN_SUB_CHANNEL_NAME)).trim());
                retailerCategoryList.add(retailerCategoryvo);
            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return retailerCategoryList;

    }

    List<RetailerCategoryVO> loadGroupData(SFADatabase sfaDatabase, String prefDistrcode, String strSubChannel) {

        List<RetailerCategoryVO> retailerCategoryList = new ArrayList<>();

        sfaDatabase.openWritableDb();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_RETAILERS_GROUP, new String[]{prefDistrcode, strSubChannel});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                RetailerCategoryVO retailerCategoryvo = new RetailerCategoryVO();
                retailerCategoryvo.setStrChannelCode(c.getString(c.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                retailerCategoryvo.setStrGroupCode(c.getString(c.getColumnIndex("groupCode")).trim());
                retailerCategoryvo.setStrGroupName(c.getString(c.getColumnIndex("groupName")).trim());
                retailerCategoryList.add(retailerCategoryvo);
            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return retailerCategoryList;

    }

    List<RetailerCategoryVO> loadClassData(SFADatabase sfaDatabase, String prefDistrcode, String chennalCode, String groupCode) {

        List<RetailerCategoryVO> retailerCategoryList = new ArrayList<>();

        sfaDatabase.openWritableDb();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_RETAILER_CLASS_DATA, new String[]{prefDistrcode, chennalCode, groupCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                RetailerCategoryVO retailerCategoryvo = new RetailerCategoryVO();
                retailerCategoryvo.setStrChannelCode(c.getString(c.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                retailerCategoryvo.setStrGroupCode(c.getString(c.getColumnIndex("groupCode")).trim());
                retailerCategoryvo.setStrClassName(c.getString(c.getColumnIndex("className")).trim());
                retailerCategoryvo.setStrClassCode(c.getString(c.getColumnIndex("classCode")).trim());
                retailerCategoryList.add(retailerCategoryvo);
            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return retailerCategoryList;

    }

    void deleteRetailerByID(SFADatabase sfaDatabase, String rtrcode) {

        sfaDatabase.openWritableDb();
        sfaDatabase.getDb().execSQL("DELETE FROM " + TABLE_NEW_RETAILER + " WHERE customerCode ='" + rtrcode + "'");
        sfaDatabase.getDb().execSQL("DELETE FROM " + TABLE_NEW_RETAILER_IMAGES + " WHERE customerCode ='" + rtrcode + "'");
        sfaDatabase.closeDb();
    }

    void deleteRetailerDataByID(SFADatabase sfaDatabase, String rtrcode) {

        sfaDatabase.openWritableDb();
        sfaDatabase.getDb().execSQL("DELETE FROM " + TABLE_RETAILER_VISIT + " WHERE customerCode ='" + rtrcode + "'");
        sfaDatabase.getDb().execSQL("DELETE FROM " + TABLE_ORDER_BOOKING + " WHERE RetlrCode  ='" + rtrcode + "'");
        sfaDatabase.getDb().execSQL("DELETE FROM " + TABLE_ORDER_BOOKING_TRACKER + " WHERE customerCode ='" + rtrcode + "'");
        sfaDatabase.closeDb();
    }


    String getLoginDistStateCode(SFADatabase baseDB) {
        baseDB.openWritableDb();
        Cursor cm = baseDB.getDb().rawQuery(QUERY_SELECT_LOGIN_DISTR_SATE_CODE, null);
        if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
            String value = cm.getString(cm.getColumnIndex(GST_STATE_CODE));
            cm.close();
            return value;
        }
        if (cm != null) {
            cm.close();
        }
        baseDB.closeDb();

        return "";
    }

    String getDistStateCode(SFADatabase baseDB) {
        baseDB.openWritableDb();
        SQLiteDatabase database = baseDB.getReadableDatabase();
        Cursor cm = database.rawQuery(QUERY_SELECT_LOGIN_DISTR_SATE_CODE, null);
        if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
            String value = cm.getString(cm.getColumnIndex(GST_STATE_CODE));
            cm.close();
            return value;
        }
        if (cm != null) {
            cm.close();
        }
        return "";
    }

    boolean isTransctionAvailableInDB(SFADatabase sfaDatabase, String distrcode, String salesmanCode) {

        sfaDatabase.openWritableDb();

        Cursor cm = null;
        try {
            cm = sfaDatabase.getDb().rawQuery(QUERY_CHECK_IS_TRANSACTION_IN_DB, new String[]{distrcode, salesmanCode});
            if (cm != null && cm.getCount() > 0) {
                return true;
            }

        } catch (Exception e) {
            Log.e(TAG, "isTransctionAvailableInDB: " + e.getMessage(), e);
        } finally {
            if (cm != null) {
                cm.close();
            }
            sfaDatabase.closeDb();
        }

        return false;
    }

    /**
     * @param sfaDatabase      db instance
     * @param salesHierarchies sales hierarchy data based on login user
     */
    void insertSalesHierarchyData(SFADatabase sfaDatabase, List<SalesHierarchy> salesHierarchies) {

        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(QUERY_INSERT_SALES_HIERARCHY);
        for (int i = 0; i < salesHierarchies.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, salesHierarchies.get(i).getId());
            statement.bindString(2, salesHierarchies.get(i).getCmpCode());
            statement.bindString(3, salesHierarchies.get(i).getType());
            statement.bindString(4, String.valueOf(salesHierarchies.get(i).getSflevelCode()));
            statement.bindString(5, salesHierarchies.get(i).getSflevelName());

            statement.execute();
        }
    }

    /**
     * @param sfaDatabase      db instance
     * @param salesHierarchies sales hierarchy Values based on login user
     */
    void insertSalesHierarchyValue(SFADatabase sfaDatabase, List<SalesHierarchy> salesHierarchies) {

        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(QUERY_INSERT_SALES_HIERARCHY_VALUE);
        sfaDatabase.getDb().beginTransaction();
        for (int i = 0; i < salesHierarchies.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, salesHierarchies.get(i).getId());
            statement.bindString(2, salesHierarchies.get(i).getCmpCode());
            statement.bindString(3, String.valueOf(salesHierarchies.get(i).getSalesForceCode()));
            statement.bindString(4, String.valueOf(salesHierarchies.get(i).getSflevelCode()));
            statement.bindString(5, salesHierarchies.get(i).getSalesForceName());
            statement.bindString(6, salesHierarchies.get(i).getLobCode());
            statement.bindString(7, salesHierarchies.get(i).getParentCode());

            statement.execute();
        }
        sfaDatabase.getDb().setTransactionSuccessful();
        sfaDatabase.getDb().endTransaction();
    }


    void deleteSalesHierachyData(SFADatabase sfaDatabase) {
        sfaDatabase.openWritableDb();
        sfaDatabase.getDb().execSQL(queryDeleteTable(TABLE_SALES_HIERARY));
        sfaDatabase.closeDb();
    }

    void deleteSalesHierachyValue(SFADatabase sfaDatabase) {
        sfaDatabase.openWritableDb();
        sfaDatabase.getDb().execSQL(queryDeleteTable(TABLE_SALES_HIERARY_VALUE));
        sfaDatabase.closeDb();
    }

    List<StockistModel> getStockistDetails(SFADatabase sfaDatabase) {
        List<StockistModel> stockistModels = new ArrayList<>();
        sfaDatabase.openWritableDb();

        String query = selectAllFormTableQuery(TABLE_STOCKIST);

        Cursor cursor = sfaDatabase.getDb().rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {

                StockistModel stockistModel = new StockistModel();

                stockistModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                stockistModel.setIsrCode(cursor.getString(cursor.getColumnIndex(ISR_CODE)));
                stockistModel.setIsrName(cursor.getString(cursor.getColumnIndex(ISR_NAME)));
                stockistModel.setSuperStockistCode(cursor.getString(cursor.getColumnIndex(COLUMN_SUPER_STOCKIST_CODE)));
                stockistModel.setSuperStockistName(cursor.getString(cursor.getColumnIndex(COLUMN_SUPER_STOCKIST_NAME)));
                stockistModel.setStockistCode(cursor.getString(cursor.getColumnIndex(COLUMN_STOCKIST_CODE)));
                stockistModel.setStockistName(cursor.getString(cursor.getColumnIndex(COLUMN_STOCKIST_NAME)));
                stockistModel.setDistrSalesmanCode(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_CODE)));
                stockistModel.setDistrSalesmanName(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_NAME)));
                stockistModel.setShLastLevelCode(cursor.getString(cursor.getColumnIndex(SH_LAST_LEVEL_CODE)));
                stockistModel.setShLastLevelName(cursor.getString(cursor.getColumnIndex(SH_LAST_LEVEL_NAME)));
                stockistModel.setLobCode(cursor.getString(cursor.getColumnIndex(LOB)));
                stockistModel.setGstDistrStateCode(cursor.getString(cursor.getColumnIndex(GST_DIST_STATE_CODE)));
                stockistModels.add(stockistModel);

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        sfaDatabase.closeDb();
        return stockistModels;
    }

    List<StockistModel> getSuperStockistDetails(SFADatabase sfaDatabase) {
        List<StockistModel> stockistModels = new ArrayList<>();
        sfaDatabase.openWritableDb();

        String query = " SELECT * FROM " + TABLE_STOCKIST + " GROUP BY " + COLUMN_SUPER_STOCKIST_CODE;

        Cursor cursor = sfaDatabase.getDb().rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {

                StockistModel stockistModel = new StockistModel();

                stockistModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                stockistModel.setIsrCode(cursor.getString(cursor.getColumnIndex(ISR_CODE)));
                stockistModel.setIsrName(cursor.getString(cursor.getColumnIndex(ISR_NAME)));
                stockistModel.setSuperStockistCode(cursor.getString(cursor.getColumnIndex(COLUMN_SUPER_STOCKIST_CODE)));
                stockistModel.setSuperStockistName(cursor.getString(cursor.getColumnIndex(COLUMN_SUPER_STOCKIST_NAME)));
                stockistModel.setStockistCode(cursor.getString(cursor.getColumnIndex(COLUMN_STOCKIST_CODE)));
                stockistModel.setStockistName(cursor.getString(cursor.getColumnIndex(COLUMN_STOCKIST_NAME)));
                stockistModel.setDistrSalesmanCode(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_CODE)));
                stockistModel.setDistrSalesmanName(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_NAME)));
                stockistModel.setShLastLevelCode(cursor.getString(cursor.getColumnIndex(SH_LAST_LEVEL_CODE)));
                stockistModel.setShLastLevelName(cursor.getString(cursor.getColumnIndex(SH_LAST_LEVEL_NAME)));
                stockistModel.setLobCode(cursor.getString(cursor.getColumnIndex(LOB)));
                stockistModel.setGstDistrStateCode(cursor.getString(cursor.getColumnIndex(GST_DIST_STATE_CODE)));
                stockistModels.add(stockistModel);

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        sfaDatabase.closeDb();
        return stockistModels;
    }

    List<StockistModel> getSubStockistDetails(SFADatabase sfaDatabase) {
        List<StockistModel> stockistModels = new ArrayList<>();
        sfaDatabase.openWritableDb();

        String query = " SELECT * FROM " + TABLE_STOCKIST + " GROUP BY " + COLUMN_STOCKIST_CODE;

        Cursor cursor = sfaDatabase.getDb().rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {

                StockistModel stockistModel = new StockistModel();

                stockistModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                stockistModel.setIsrCode(cursor.getString(cursor.getColumnIndex(ISR_CODE)));
                stockistModel.setIsrName(cursor.getString(cursor.getColumnIndex(ISR_NAME)));
                stockistModel.setSuperStockistCode(cursor.getString(cursor.getColumnIndex(COLUMN_SUPER_STOCKIST_CODE)));
                stockistModel.setSuperStockistName(cursor.getString(cursor.getColumnIndex(COLUMN_SUPER_STOCKIST_NAME)));
                stockistModel.setStockistCode(cursor.getString(cursor.getColumnIndex(COLUMN_STOCKIST_CODE)));
                stockistModel.setStockistName(cursor.getString(cursor.getColumnIndex(COLUMN_STOCKIST_NAME)));
                stockistModel.setDistrSalesmanCode(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_CODE)));
                stockistModel.setDistrSalesmanName(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_NAME)));
                stockistModel.setShLastLevelCode(cursor.getString(cursor.getColumnIndex(SH_LAST_LEVEL_CODE)));
                stockistModel.setShLastLevelName(cursor.getString(cursor.getColumnIndex(SH_LAST_LEVEL_NAME)));
                stockistModel.setLobCode(cursor.getString(cursor.getColumnIndex(LOB)));
                stockistModel.setGstDistrStateCode(cursor.getString(cursor.getColumnIndex(GST_DIST_STATE_CODE)));
                stockistModels.add(stockistModel);

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        sfaDatabase.closeDb();
        return stockistModels;
    }

    List<StockistModel> getSubStockistDetails(SFADatabase sfaDatabase, String superstockistCode) {
        List<StockistModel> stockistModels = new ArrayList<>();
        sfaDatabase.openWritableDb();

        String query = " SELECT * FROM " + TABLE_STOCKIST + " WHERE " + COLUMN_SUPER_STOCKIST_CODE + " =? ";

        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{superstockistCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {

                StockistModel stockistModel = new StockistModel();

                stockistModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                stockistModel.setIsrCode(cursor.getString(cursor.getColumnIndex(ISR_CODE)));
                stockistModel.setIsrName(cursor.getString(cursor.getColumnIndex(ISR_NAME)));
                stockistModel.setSuperStockistCode(cursor.getString(cursor.getColumnIndex(COLUMN_SUPER_STOCKIST_CODE)));
                stockistModel.setSuperStockistName(cursor.getString(cursor.getColumnIndex(COLUMN_SUPER_STOCKIST_NAME)));
                stockistModel.setStockistCode(cursor.getString(cursor.getColumnIndex(COLUMN_STOCKIST_CODE)));
                stockistModel.setStockistName(cursor.getString(cursor.getColumnIndex(COLUMN_STOCKIST_NAME)));
                stockistModel.setDistrSalesmanCode(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_CODE)));
                stockistModel.setDistrSalesmanName(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_NAME)));
                stockistModel.setShLastLevelCode(cursor.getString(cursor.getColumnIndex(SH_LAST_LEVEL_CODE)));
                stockistModel.setShLastLevelName(cursor.getString(cursor.getColumnIndex(SH_LAST_LEVEL_NAME)));
                stockistModel.setLobCode(cursor.getString(cursor.getColumnIndex(LOB)));
                stockistModel.setGstDistrStateCode(cursor.getString(cursor.getColumnIndex(GST_DIST_STATE_CODE)));
                stockistModels.add(stockistModel);

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        sfaDatabase.closeDb();
        return stockistModels;
    }


    List<StockistModel> getSelectedStockistDetails(SFADatabase sfaDatabase, String distrCode) {
        List<StockistModel> stockistModels = new ArrayList<>();
        sfaDatabase.openWritableDb();

        String query = "Select * from " + TABLE_STOCKIST + " where stockistCode =? ";

        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {

                StockistModel stockistModel = new StockistModel();

                stockistModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                stockistModel.setIsrCode(cursor.getString(cursor.getColumnIndex(ISR_CODE)));
                stockistModel.setIsrName(cursor.getString(cursor.getColumnIndex(ISR_NAME)));
                stockistModel.setSuperStockistCode(cursor.getString(cursor.getColumnIndex(COLUMN_SUPER_STOCKIST_CODE)));
                stockistModel.setSuperStockistName(cursor.getString(cursor.getColumnIndex(COLUMN_SUPER_STOCKIST_NAME)));
                stockistModel.setStockistCode(cursor.getString(cursor.getColumnIndex(COLUMN_STOCKIST_CODE)));
                stockistModel.setStockistName(cursor.getString(cursor.getColumnIndex(COLUMN_STOCKIST_NAME)));
                stockistModel.setDistrSalesmanCode(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_CODE)));
                stockistModel.setDistrSalesmanName(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_NAME)));
                stockistModel.setShLastLevelCode(cursor.getString(cursor.getColumnIndex(SH_LAST_LEVEL_CODE)));
                stockistModel.setShLastLevelName(cursor.getString(cursor.getColumnIndex(SH_LAST_LEVEL_NAME)));
                stockistModel.setLobCode(cursor.getString(cursor.getColumnIndex(LOB)));
                stockistModel.setGstDistrStateCode(cursor.getString(cursor.getColumnIndex(GST_DIST_STATE_CODE)));
                stockistModels.add(stockistModel);

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        sfaDatabase.closeDb();
        return stockistModels;
    }


    List<NewOutletImageList> fetchNewRetailerAllImages(SFADatabase sfaDatabase, String retailerCode) {

        List<NewOutletImageList> imageLists = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NEW_RETAILER_IMAGES + " WHERE customerCode=? and upload =?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{retailerCode, "N"});

        try {
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    NewOutletImageList list = new NewOutletImageList();

                    list.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    list.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    list.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                    list.setImage(c.getString(c.getColumnIndex(COLUMN_IMAGE)));
                    list.setImageId(c.getString(c.getColumnIndex(COLUMN_REF_CODE)));
                    imageLists.add(list);

                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "fetchNewRetailerAllImages: " + e.getMessage(), e);
        } finally {
            if (c != null)
                c.close();
        }
        sfaDatabase.closeDb();
        return imageLists;
    }

    void deleteRetailerIamgeByID(SFADatabase sfaDatabase, String imgId) {

        sfaDatabase.openWritableDb();
        sfaDatabase.getDb().execSQL("DELETE FROM " + TABLE_NEW_RETAILER_IMAGES + " WHERE refCode ='" + imgId + "'");
        sfaDatabase.closeDb();
    }

    Boolean isOutletAvailable(SFADatabase sfaDatabase, String cmpCustomerCode) {

        sfaDatabase.openWritableDb();

        String query = "SELECT * FROM " + TABLE_NEW_RETAILER + " Where cmpCustomerCode=?";
        Cursor cm = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCustomerCode});
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        sfaDatabase.closeDb();

        return false;
    }

    List<PendingVO> fetchRetailerDetails(SFADatabase sfaDatabase) {

        List<PendingVO> pendingList = new ArrayList<>();
        sfaDatabase.openWritableDb();
        Cursor cursorPending = sfaDatabase.getDb().rawQuery(QUERY_SELECT_RETAILERS, null);
        if (cursorPending != null && cursorPending.getCount() > 0 && cursorPending.moveToFirst()) {
            do {
                PendingVO pendingData1 = new PendingVO();
                pendingData1.setStrRouteCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                pendingData1.setStrCustomerCode(cursorPending.getString(cursorPending.getColumnIndex("customerCode")).trim());
                pendingData1.setStrRetailerName(cursorPending.getString(cursorPending.getColumnIndex("customerName")).trim());
                pendingData1.setLatitude(cursorPending.getString(cursorPending.getColumnIndex("latitude")).trim());
                pendingData1.setLongitude(cursorPending.getString(cursorPending.getColumnIndex("longitude")).trim());
                pendingList.add(pendingData1);

            } while (cursorPending.moveToNext());
        }
        if (cursorPending != null) {
            cursorPending.close();
        }
        sfaDatabase.closeDb();
        return pendingList;
    }

    void insertBankDetail(SFADatabase sfaDatabase, RetailerVO retailerList, String cmpCode, String distCode) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER_BANK + " VALUES (?,?,?,?,?,?,?,?,?,?,?);";

        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, cmpCode);
        statement.bindString(2, distCode);
        statement.bindString(3, "12345");
        statement.bindString(4, "1");
        statement.bindString(5, retailerList.getBankName());
        statement.bindString(6, retailerList.getBranchName());
        statement.bindString(7, retailerList.getIfscCode());
        statement.bindString(8, retailerList.getAccountType());
        statement.bindString(9, retailerList.getAccountNo());
        statement.bindString(10, DateUtil.getCurrentYearMonthDate());
        statement.bindString(11, "N");
        statement.execute();

    }

    void updateDistrbutorUploadStatus(SFADatabase sfaDatabase, String customerCode, String tableName, String col, String status) {

        SQLiteDatabase db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UPLOAD, status);
        String update = col + " = ?";
        String[] updateArgs = {customerCode};
        db.update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    void updateUploadStatus(SFADatabase sfaDatabase, String tableName, String uploadStatus, String[] parameters, String[] columns) {

        SQLiteDatabase db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UPLOAD, uploadStatus);
        StringBuilder updateWhereClause = new StringBuilder();

        for (int i = 0; i < columns.length; i++) {
            if (i == columns.length - 1) {
                updateWhereClause.append(columns[i]).append("=?");
            } else {
                updateWhereClause.append(columns[i]).append("=? AND ");
            }

        }
        db.update(tableName, values, updateWhereClause.toString(), parameters);
        sfaDatabase.closeDb();
    }


    void insertSalesmanDetail(SFADatabase baseDB, SalesmanMasterModel salesmanMasterModel) {

        deleteNewSalesmanByCode(baseDB, salesmanMasterModel.getSalesmanCode(), TABLE_SALESMAN_MASTER);

        ContentValues values = new ContentValues();

        values.put(COLUMN_CMP_CODE, salesmanMasterModel.getCmpCode());
        values.put(COLUMN_DISTR_CODE, salesmanMasterModel.getDistrCode());
        values.put(COLUMN_SALESMAN_CODE, salesmanMasterModel.getSalesmanCode());
        values.put(COLUMN_SALESMAN_NAME, salesmanMasterModel.getSalesmanName());
        values.put(COLUMN_MOBILE_NO, salesmanMasterModel.getMobileNo());

        values.put(COLUMN_EMAIL_ID, salesmanMasterModel.getEmailId());
        values.put(COLUMN_SALARY, salesmanMasterModel.getMonthlySalary());
        values.put(COLUMN_STATUS, salesmanMasterModel.getSalesmanStatus());
        values.put(COLUMN_SSFA_ENABLED, salesmanMasterModel.getSsfaEnabled());

        values.put(COLUMN_BANK_NAME, salesmanMasterModel.getBankName());
        values.put(COLUMN_ACC_NO, salesmanMasterModel.getAccNo());

        values.put(COLUMN_IFSC_CODE, salesmanMasterModel.getIfscCode());
        values.put(COLUMN_BRANCH_NAME, salesmanMasterModel.getBranchName());
        values.put(COLUMN_AADHAR_NO, salesmanMasterModel.getAdharNo());

        values.put(COLUMN_SALESMAN_IMAGE, salesmanMasterModel.getSalesmanProfileImage());

        values.put(COLUMN_CANCELLED_CHEQUE_IMAGE, salesmanMasterModel.getCancelledChequeImage());

        values.put(TAG_MOD_DT, System.currentTimeMillis());

        values.put(COLUMN_UPLOAD, "N");

        // Inserting Row
        long ack = baseDB.getDb().insert(TABLE_SALESMAN_MASTER, null, values);

        if (BuildConfig.DEBUG) {
            if (ack < 0) {
                Log.e(TABLE_NEW_RETAILER, "failed inserting into table: NewSalesmanRefNo" + salesmanMasterModel.getSalesmanCode());
            }
        }

    }


    List<SalesmanMasterModel> fetchAllNewSalesmanDetails(SFADatabase sfaDatabase) {

        List<SalesmanMasterModel> masterModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ALL_NEW_SALESMAN, null);

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesmanMasterModel masterModel = new SalesmanMasterModel();
                masterModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                masterModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                masterModel.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)).trim());
                masterModel.setSalesmanName(c.getString(c.getColumnIndex(COLUMN_SALESMAN_NAME)).trim());
                masterModel.setMobileNo(c.getString(c.getColumnIndex(COLUMN_MOBILE_NO)).trim());
                masterModel.setEmailId(c.getString(c.getColumnIndex(COLUMN_EMAIL_ID)).trim());
                masterModel.setMonthlySalary(c.getInt(c.getColumnIndex(COLUMN_SALARY)));
                masterModel.setSalesmanStatus(c.getString(c.getColumnIndex(COLUMN_STATUS)).trim());
                masterModel.setSsfaEnabled(c.getString(c.getColumnIndex(COLUMN_SSFA_ENABLED)).trim());
                masterModel.setBankName(c.getString(c.getColumnIndex(COLUMN_BANK_NAME)).trim());
                masterModel.setAccNo(c.getString(c.getColumnIndex(COLUMN_ACC_NO)).trim());
                masterModel.setIfscCode(c.getString(c.getColumnIndex(COLUMN_IFSC_CODE)).trim());
                masterModel.setBranchName(c.getString(c.getColumnIndex(COLUMN_BRANCH_NAME)).trim());
                masterModel.setAdharNo(c.getString(c.getColumnIndex(COLUMN_AADHAR_NO)).trim());
                masterModel.setSalesmanProfileImage(c.getString(c.getColumnIndex(COLUMN_SALESMAN_IMAGE)).trim());
                masterModel.setCancelledChequeImage(c.getString(c.getColumnIndex(COLUMN_CANCELLED_CHEQUE_IMAGE)).trim());

                masterModelList.add(masterModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return masterModelList;
    }


    SalesmanMasterModel fetchNewSalesmanDetails(SFADatabase sfaDatabase, String salesmanCode) {

        //  List<SalesmanMasterModel> masterModelList    = new ArrayList<>();
        SalesmanMasterModel masterModel = new SalesmanMasterModel();
        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_NEW_SALESMAN, new String[]{salesmanCode});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                masterModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                masterModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                masterModel.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)).trim());
                masterModel.setSalesmanName(c.getString(c.getColumnIndex(COLUMN_SALESMAN_NAME)).trim());
                masterModel.setMobileNo(c.getString(c.getColumnIndex(COLUMN_MOBILE_NO)).trim());
                masterModel.setEmailId(c.getString(c.getColumnIndex(COLUMN_EMAIL_ID)).trim());
                masterModel.setMonthlySalary(c.getInt(c.getColumnIndex(COLUMN_SALARY)));
                masterModel.setSalesmanStatus(c.getString(c.getColumnIndex(COLUMN_STATUS)).trim());
                masterModel.setSsfaEnabled(c.getString(c.getColumnIndex(COLUMN_SSFA_ENABLED)).trim());
                masterModel.setBankName(c.getString(c.getColumnIndex(COLUMN_BANK_NAME)).trim());
                masterModel.setAccNo(c.getString(c.getColumnIndex(COLUMN_ACC_NO)).trim());
                masterModel.setIfscCode(c.getString(c.getColumnIndex(COLUMN_IFSC_CODE)).trim());
                masterModel.setBranchName(c.getString(c.getColumnIndex(COLUMN_BRANCH_NAME)).trim());
                masterModel.setAdharNo(c.getString(c.getColumnIndex(COLUMN_AADHAR_NO)).trim());
                masterModel.setSalesmanProfileImage(c.getString(c.getColumnIndex(COLUMN_SALESMAN_IMAGE)).trim());
                masterModel.setCancelledChequeImage(c.getString(c.getColumnIndex(COLUMN_CANCELLED_CHEQUE_IMAGE)).trim());

                //masterModelList.add(masterModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return masterModel;
    }


    public void deleteNewSalesmanByCode(SFADatabase sfaDatabase, String salesmanCode, String tableName) {
        sfaDatabase.openWritableDb();
        sfaDatabase.getDb().execSQL("DELETE FROM " + tableName + " WHERE salesmanCode = '" + salesmanCode + "'");
        sfaDatabase.closeDb();
    }

    public void deleteSalesmanKycByCode(SFADatabase sfaDatabase, String salesmanCode, String kycType, String tableName) {
        sfaDatabase.openWritableDb();
//        sfaDatabase.getDb().execSQL("DELETE FROM " + tableName + " WHERE salesmanCode = '" + salesmanCode + "' AND type = '" + kycType + "'");
//        sfaDatabase.closeDb();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UPLOAD, "N");
        values.put(COLUMN_KYC_IMAGE, "");
        String appendAnd = " = ? and ";
        String update = COLUMN_SALESMAN_CODE + appendAnd + COLUMN_TYPE + " = ?";

        String[] updateArgs = {salesmanCode, kycType};
        sfaDatabase.getDb().update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }


    public void insertSalesmanRouteMappingDetail(SFADatabase baseDB, SalesmanMasterModel salesmanMasterModel) {

        deleteNewSalesmanByCode(baseDB, salesmanMasterModel.getSalesmanCode(), TABLE_SALESMAN_ROUTE_MAPPING);

        List<SalesmanMasterCheckBoxModel> selectedRouteList = salesmanMasterModel.getRouteMappedList();

        for (int i = 0; i < selectedRouteList.size(); i++) {

            ContentValues values = new ContentValues();

            values.put(COLUMN_CMP_CODE, salesmanMasterModel.getCmpCode());
            values.put(COLUMN_DISTR_CODE, salesmanMasterModel.getDistrCode());
            values.put(COLUMN_SALESMAN_CODE, salesmanMasterModel.getSalesmanCode());
            values.put(COLUMN_ROUTE_CODE, selectedRouteList.get(i).getMappedCode());
            values.put(COLUMN_IS_SELECTED, "Y");
            values.put(TAG_MOD_DT, System.currentTimeMillis());
            values.put(COLUMN_UPLOAD, "N");

            long ack = baseDB.getDb().insert(TABLE_SALESMAN_ROUTE_MAPPING, null, values);

            if (BuildConfig.DEBUG) {
                if (ack < 0) {
                    Log.e("m_salesman_rte_mapping", "failed inserting into table: NewSalesmanRouteCode " + selectedRouteList.get(i).getMappedName());
                }
            }

        }

    }


    List<SalesmanMasterCheckBoxModel> fetchNewSalesmanRouteMapDetails(SFADatabase sfaDatabase, String salesmanCode) {

        List<SalesmanMasterCheckBoxModel> masterModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();

        String QUERY_SELECT_NEW_SALESMAN_ROUTE_MAP = "SELECT a.routeCode, a.routeName, b.isSelected FROM " + TABLE_ROUTE + " a LEFT JOIN " + TABLE_SALESMAN_ROUTE_MAPPING + " b ON a.routeCode = b.routeCode AND b.salesmanCode = '" + salesmanCode + "'";

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_NEW_SALESMAN_ROUTE_MAP, null);

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesmanMasterCheckBoxModel masterModel = new SalesmanMasterCheckBoxModel();
                masterModel.setMappedCode(c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE)));
                masterModel.setMappedName(c.getString(c.getColumnIndex(COLUMN_ROUTE_NAME)));

                String isChecked = c.getString(c.getColumnIndex(COLUMN_IS_SELECTED));

                if (isChecked != null && isChecked.equalsIgnoreCase("Y")) {
                    masterModel.setCheckBoxEnabled(true);
                } else {
                    masterModel.setCheckBoxEnabled(false);
                }


                masterModelList.add(masterModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return masterModelList;
    }

    List<SalesmanMasterCheckBoxModel> fetchNewSalesmanAllRouteMap(SFADatabase sfaDatabase) {

        List<SalesmanMasterCheckBoxModel> masterModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_ALL_NEW_SALESMAN_ROUTE_MAP, null);

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesmanMasterCheckBoxModel masterModel = new SalesmanMasterCheckBoxModel();
                masterModel.setMappedCode(c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                masterModel.setMappedName(c.getString(c.getColumnIndex(COLUMN_ROUTE_NAME)).trim());

                masterModelList.add(masterModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return masterModelList;
    }


    public void insertSalesmanLobMappingDetail(SFADatabase baseDB, SalesmanMasterModel salesmanMasterModel) {

        deleteNewSalesmanByCode(baseDB, salesmanMasterModel.getSalesmanCode(), TABLE_SALESMAN_LOB_MAPPING);

        List<SalesmanMasterCheckBoxModel> selectedRouteList = salesmanMasterModel.getLobMappedList();

        for (int i = 0; i < selectedRouteList.size(); i++) {

            ContentValues values = new ContentValues();

            values.put(COLUMN_CMP_CODE, salesmanMasterModel.getCmpCode());
            values.put(COLUMN_DISTR_CODE, salesmanMasterModel.getDistrCode());
            values.put(COLUMN_SALESMAN_CODE, salesmanMasterModel.getSalesmanCode());
            values.put(COLUMN_LOB_CODE, selectedRouteList.get(i).getMappedCode());
            values.put(COLUMN_IS_SELECTED, "Y");
            values.put(TAG_MOD_DT, System.currentTimeMillis());
            values.put(COLUMN_UPLOAD, "N");

            long ack = baseDB.getDb().insert(TABLE_SALESMAN_LOB_MAPPING, null, values);

            if (BuildConfig.DEBUG) {
                if (ack < 0) {
                    Log.e("m_salesman_lob_mapping", "failed inserting into table: NewSalesmanLobCode " + selectedRouteList.get(i).getMappedName());
                }
            }

        }

    }


    List<SalesmanMasterCheckBoxModel> fetchNewSalesmanLOBMapDetails(SFADatabase sfaDatabase, String salesmanCode) {

        List<SalesmanMasterCheckBoxModel> masterModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();

        String QUERY_SELECT_NEW_SALESMAN_LOB_MAP = "SELECT a.lobCode, b.lobName, c.isSelected FROM " + TABLE_DISTR_LOB + " a LEFT JOIN " + TABLE_LOB + " b ON a.lobCode = b.lobCode " + "  LEFT JOIN " + TABLE_SALESMAN_LOB_MAPPING + " c ON a.lobCode = c.lobCode AND c.salesmanCode = '" + salesmanCode + "'";

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_NEW_SALESMAN_LOB_MAP, null);

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesmanMasterCheckBoxModel masterModel = new SalesmanMasterCheckBoxModel();
                masterModel.setMappedCode(c.getString(c.getColumnIndex(COLUMN_LOB_CODE)));
                masterModel.setMappedName(c.getString(c.getColumnIndex(COLUMN_LOB_NAME)));

                String isChecked = c.getString(c.getColumnIndex(COLUMN_IS_SELECTED));

                if (isChecked != null && isChecked.equalsIgnoreCase("Y")) {
                    masterModel.setCheckBoxEnabled(true);
                } else {
                    masterModel.setCheckBoxEnabled(false);
                }


                masterModelList.add(masterModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return masterModelList;
    }

    List<SalesmanMasterCheckBoxModel> fetchNewSalesmanAllLOBMap(SFADatabase sfaDatabase) {

        List<SalesmanMasterCheckBoxModel> masterModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_ALL_NEW_SALESMAN_LOB_MAP, null);

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesmanMasterCheckBoxModel masterModel = new SalesmanMasterCheckBoxModel();
                masterModel.setMappedCode(c.getString(c.getColumnIndex(COLUMN_LOB_CODE)));
                masterModel.setMappedName(c.getString(c.getColumnIndex(COLUMN_LOB_NAME)));


                masterModelList.add(masterModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return masterModelList;
    }


    public void insertSalesmanKycDetail(SFADatabase baseDB, SalesmanMasterModel salesmanMasterModel, HashMap<String, String> kycImageMap) {

        //  deleteSalesmanKycByCode(baseDB, salesmanMasterModel.getSalesmanCode(), salesmanMasterModel.getSelectedKYC(), TABLE_SALESMAN_MASTER_KYC);

        deleteNewSalesmanByCode(baseDB, salesmanMasterModel.getSalesmanCode(), TABLE_SALESMAN_MASTER_KYC);

        for (Map.Entry<String, String> entry : kycImageMap.entrySet()) {

            ContentValues values = new ContentValues();

            values.put(COLUMN_CMP_CODE, salesmanMasterModel.getCmpCode());
            values.put(COLUMN_DISTR_CODE, salesmanMasterModel.getDistrCode());
            values.put(COLUMN_SALESMAN_CODE, salesmanMasterModel.getSalesmanCode());
            values.put(COLUMN_TYPE, entry.getKey());
            values.put(COLUMN_KYC_IMAGE, entry.getValue());
            values.put(TAG_MOD_DT, System.currentTimeMillis());
            values.put(COLUMN_UPLOAD, "N");

            long ack = baseDB.getDb().insert(TABLE_SALESMAN_MASTER_KYC, null, values);

            if (BuildConfig.DEBUG) {
                if (ack < 0) {
                    Log.e("m_salesman_lob_mapping", "failed inserting into table: NewSalesmanKYC " + salesmanMasterModel.getSalesmanCode());
                }
            }
        }


    }

    HashMap<String, String> fetchNewSalesmanKycDetails(SFADatabase sfaDatabase, String salesmanCode) {

        HashMap<String, String> kycImageMap = new HashMap<>();

        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_NEW_SALESMAN_KYC_MAP, new String[]{salesmanCode});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                kycImageMap.put(c.getString(c.getColumnIndex(COLUMN_TYPE)), c.getString(c.getColumnIndex(COLUMN_KYC_IMAGE)));

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return kycImageMap;
    }


    public List<SalesmanMasterModel> getAllSalemansForUpload(SFADatabase sfaDatabase, String distributorCode) {
        List<SalesmanMasterModel> salesmanMasterModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_FETCH_SALESMAN_FOR_UPLOAD, new String[]{distributorCode});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesmanMasterModel masterModel = new SalesmanMasterModel();
                masterModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                masterModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                masterModel.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)).trim());
                masterModel.setSalesmanName(c.getString(c.getColumnIndex(COLUMN_SALESMAN_NAME)).trim());
                masterModel.setMobileNo(c.getString(c.getColumnIndex(COLUMN_MOBILE_NO)).trim());
                masterModel.setEmailId(c.getString(c.getColumnIndex(COLUMN_EMAIL_ID)).trim());
                masterModel.setMonthlySalary(c.getInt(c.getColumnIndex(COLUMN_SALARY)));
                masterModel.setSalesmanStatus(c.getString(c.getColumnIndex(COLUMN_STATUS)).trim());
                masterModel.setSsfaEnabled(c.getString(c.getColumnIndex(COLUMN_SSFA_ENABLED)).trim());
                masterModel.setBankName(c.getString(c.getColumnIndex(COLUMN_BANK_NAME)).trim());
                masterModel.setAccNo(c.getString(c.getColumnIndex(COLUMN_ACC_NO)).trim());
                masterModel.setIfscCode(c.getString(c.getColumnIndex(COLUMN_IFSC_CODE)).trim());
                masterModel.setBranchName(c.getString(c.getColumnIndex(COLUMN_BRANCH_NAME)).trim());
                masterModel.setAdharNo(c.getString(c.getColumnIndex(COLUMN_AADHAR_NO)).trim());
                masterModel.setSalesmanProfileImage(c.getString(c.getColumnIndex(COLUMN_SALESMAN_IMAGE)).trim());
                masterModel.setCancelledChequeImage(c.getString(c.getColumnIndex(COLUMN_CANCELLED_CHEQUE_IMAGE)).trim());
                masterModel.setUpload(c.getString(c.getColumnIndex(COLUMN_UPLOAD)).trim());

                salesmanMasterModelList.add(masterModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return salesmanMasterModelList;
    }

    public List<SalesmanMasterModel> getSalesmanKYC(SFADatabase sfaDatabase, String distributorCode) {
        List<SalesmanMasterModel> salesmanMasterModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_FETCH_SALESMAN_KYC_FOR_UPLOAD, new String[]{distributorCode});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesmanMasterModel masterModel = new SalesmanMasterModel();
                masterModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                masterModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                masterModel.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)).trim());
                masterModel.setSelectedKYC(c.getString(c.getColumnIndex(COLUMN_TYPE)).trim());
                masterModel.setKycImage(c.getString(c.getColumnIndex(COLUMN_IMAGE)).trim());
                masterModel.setUpload(c.getString(c.getColumnIndex(COLUMN_UPLOAD)).trim());

                salesmanMasterModelList.add(masterModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return salesmanMasterModelList;
    }

    public List<SalesmanMasterModel> getSalesmanLobMapping(SFADatabase sfaDatabase, String distributorCode) {
        List<SalesmanMasterModel> salesmanMasterModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_FETCH_SALESMAN_LOB_UPLOAD, new String[]{distributorCode});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesmanMasterModel masterModel = new SalesmanMasterModel();
                masterModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                masterModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                masterModel.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)).trim());
                masterModel.setLobCode(c.getString(c.getColumnIndex(COLUMN_LOB_CODE)).trim());
                masterModel.setUpload(c.getString(c.getColumnIndex(COLUMN_UPLOAD)).trim());

                salesmanMasterModelList.add(masterModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return salesmanMasterModelList;
    }


    public List<SalesmanMasterModel> getSalesmanRouteMapping(SFADatabase sfaDatabase, String distributorCode) {
        List<SalesmanMasterModel> salesmanMasterModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_FETCH_SALESMAN_ROUTE_UPLOAD, new String[]{distributorCode});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesmanMasterModel masterModel = new SalesmanMasterModel();
                masterModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                masterModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                masterModel.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)).trim());
                masterModel.setRouteCode(c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                masterModel.setUpload(c.getString(c.getColumnIndex(COLUMN_UPLOAD)).trim());

                salesmanMasterModelList.add(masterModel);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return salesmanMasterModelList;
    }

    List<RetailerVO> fetchCustomerDetails(SFADatabase sfaDatabase) {
        List<RetailerVO> retailerVOList = new ArrayList<>();
        sfaDatabase.openWritableDb();
        Cursor cursorPending = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CUSTOMERS, null);
        if (cursorPending != null && cursorPending.getCount() > 0 && cursorPending.moveToFirst()) {
            do {
                RetailerVO retailerVO = new RetailerVO();
                retailerVO.setCustomerCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
                retailerVO.setCustomerName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_NAME)).trim());
                retailerVO.setChannelCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                retailerVO.setLatitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LATITUDE)).trim());
                retailerVO.setLongitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LONGITUDE)).trim());
                retailerVO.setChannelName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_NAME)).trim());
                retailerVO.setApprovalStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_APPROVAL_STATUS)).trim());
                retailerVO.setRetailerStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_RETAILER_STATUS)).trim());
                retailerVOList.add(retailerVO);

            } while (cursorPending.moveToNext());
        }
        if (cursorPending != null) {
            cursorPending.close();
        }
        sfaDatabase.closeDb();
        return retailerVOList;
    }

    List<KycModel> getKYCMaster(SFADatabase sfaDatabase) {
        List<KycModel> kycModelList = new ArrayList<>();
        sfaDatabase.openWritableDb();
        Cursor cursorPending = sfaDatabase.getDb().rawQuery(QUERY_FETCH_KYC_MASTER, null);
        if (cursorPending != null && cursorPending.getCount() > 0 && cursorPending.moveToFirst()) {
            do {
                KycModel kycModel = new KycModel();
                kycModel.setCmpCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CMP_CODE)).trim());
                kycModel.setKycCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_KYC_CODE)).trim());
                kycModel.setKycDesc(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_KYC_DESC)).trim());
                kycModelList.add(kycModel);

            } while (cursorPending.moveToNext());
        }
        if (cursorPending != null) {
            cursorPending.close();
        }
        sfaDatabase.closeDb();
        return kycModelList;
    }

    String getPrefixForScreen(SFADatabase sfaDatabase, String screenName) {

        String code = "";
        String query = "SELECT " + COLUMN_PREFIX + " FROM " + TABLE_CODE_GENERATOR +
                " WHERE " + COLUMN_SCREEN_NAME + " = ? LIMIT 1";

        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{screenName});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            code = c.getString(c.getColumnIndex(COLUMN_PREFIX));
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return code;
    }


    public boolean isSalesmanDataSync(SFADatabase sfaDatabase, String salesmanCode) {

        String query = "SELECT * FROM " + TABLE_SALESMAN_MASTER + " WHERE salesmanCode=? and upload =?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{salesmanCode, "N"});

        if (c != null && c.getCount() > 0) {
            return true;
        }
        if (c != null) {
            c.close();
        }
        return false;
    }

    String getGstStateNameBasedOnCode(SFADatabase sfaDatabase, String gstStateCode) {
        String stateName = "";
        sfaDatabase.openWritableDb();
        String query = "SELECT gstStateName from m_gstStateMaster where gstStateCode = ?";

        try {
            Cursor cm = sfaDatabase.getDb().rawQuery(query,
                    new String[]{gstStateCode});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
                stateName = cm.getString(cm.getColumnIndex(GST_STATE_NAME));
            }
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            LoggerUtil.printErrorLog(TAG, "getGstStateNameBasedOnCode: " + e.getMessage());
        }
        return stateName;
    }

    String getGeoHierPathValue(SFADatabase sfaDatabase, String geoHierPathCode) {
        StringBuilder geoPathValue = new StringBuilder();
        String query = "SELECT geoName from m_GeoHierValue where geoCode = ?";
        sfaDatabase.openWritableDb();

        String[] codeArray = geoHierPathCode.split("/");
        for (String code : codeArray) {
            if (code != null && !code.isEmpty())
                try {
                    Cursor cm = sfaDatabase.getDb().rawQuery(query,
                            new String[]{code});

                    if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
                        if (!geoPathValue.toString().isEmpty()) {
                            geoPathValue.append(" > ");
                        }
                        geoPathValue.append(cm.getString(cm.getColumnIndex(COLUMN_GEO_NAME)));
                    }
                    if (cm != null) {
                        cm.close();
                    }
                } catch (Exception e) {
                    LoggerUtil.printErrorLog(TAG, "getGeoHierPathValue: " + e.getMessage());
                }
        }
        return geoPathValue.toString();
    }

    public boolean isDataAvailableInDB(SFADatabase sfaDatabase, String tableName) {

        sfaDatabase.openWritableDb();
        String query = selectAllFormTableQuery(tableName);
        Cursor cm = sfaDatabase.getDb().rawQuery(query, null);
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        sfaDatabase.closeDb();

        return false;
    }

    public String isfetchDataByTagName(SFADatabase sfaDatabase, String tagName) {

        String strValue = "";
        sfaDatabase.openWritableDb();
        String query = "SELECT masterValue from " + TABLE_KEY_VALUE_ENCODE + " where masterName = ?";

        try {
            Cursor cm = sfaDatabase.getDb().rawQuery(query,
                    new String[]{tagName});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
                strValue = cm.getString(cm.getColumnIndex(COLUMN_MASTER_VALUE));
            }
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            LoggerUtil.printErrorLog(TAG, "getGstStateNameBasedOnCode: " + e.getMessage());
        }
        return strValue;


    }

    public List<SalesmanMasterModel> getSalesmanDataFromDB(SFADatabase sfaDatabase, String cmpCode, String distCode, boolean state) {
        List<SalesmanMasterModel> salesmanMasterModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();

        Cursor c;
        if (state) {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_SALESMAN_MASTER_ACTIVE, new String[]{cmpCode, distCode, "Y"});
        } else {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_SALESMAN_MASTER, new String[]{cmpCode, distCode});
        }
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesmanMasterModel salesmanMasterModel = new SalesmanMasterModel();
                salesmanMasterModel.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)).trim());
                salesmanMasterModel.setSalesmanName(c.getString(c.getColumnIndex(COLUMN_SALESMAN_NAME)).trim());

                salesmanMasterModelList.add(salesmanMasterModel);
            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return salesmanMasterModelList;
    }

    public List<RetailerVO> getRetailerData(SFADatabase sfaDatabase, String cmpCode, String distrCode, String routeCode) {
        List<RetailerVO> retailerVOList = new ArrayList<>();
        sfaDatabase.openWritableDb();
        int count = 0;
        Cursor cursorPending = sfaDatabase.getDb().rawQuery(QUERY_SELECT_RETAILER_BY_ROUTE, new String[]{cmpCode, distrCode, routeCode, "A"});
        if (cursorPending != null && cursorPending.getCount() > 0 && cursorPending.moveToFirst()) {
            do {
                RetailerVO retailerVO = new RetailerVO();
                retailerVO.setCustomerCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
                retailerVO.setCustomerName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_NAME)).trim());
                retailerVO.setChannelCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                retailerVO.setLatitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LATITUDE)).trim());
                retailerVO.setLongitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LONGITUDE)).trim());
                retailerVO.setChannelName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_NAME)).trim());
                retailerVO.setApprovalStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_APPROVAL_STATUS)).trim());
                retailerVO.setRetailerStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_RETAILER_STATUS)).trim());
                retailerVO.setSeqNo(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_COVERAGE_SEQUENCE)).trim());
                count = count + 1;
                retailerVO.setSeqNo(String.valueOf(count));
                retailerVOList.add(retailerVO);

            } while (cursorPending.moveToNext());
        }
        if (cursorPending != null) {
            cursorPending.close();
        }
        sfaDatabase.closeDb();
        return retailerVOList;
    }

    public List<RetailerVO> getRetailerDataFromDB(SFADatabase sfaDatabase, String cmpCode, String distrCode) {
        List<RetailerVO> retailerVOList = new ArrayList<>();
        sfaDatabase.openWritableDb();
        Cursor cursorPending = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ALL_CUSTOMERS, null);
        if (cursorPending != null && cursorPending.getCount() > 0 && cursorPending.moveToFirst()) {
            do {
                RetailerVO retailerVO = new RetailerVO();
                retailerVO.setCustomerCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
                retailerVO.setCustomerName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_NAME)).trim());
                retailerVO.setChannelCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                retailerVO.setLatitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LATITUDE)).trim());
                retailerVO.setLongitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LONGITUDE)).trim());
                retailerVO.setChannelName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_NAME)).trim());
                retailerVO.setApprovalStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_APPROVAL_STATUS)).trim());
                retailerVO.setRetailerStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_RETAILER_STATUS)).trim());
                retailerVOList.add(retailerVO);

            } while (cursorPending.moveToNext());
        }
        if (cursorPending != null) {
            cursorPending.close();
        }
        sfaDatabase.closeDb();
        return retailerVOList;
    }

    public List<SalesmanMasterModel> getCustomerSalesmanDataFromDB(SFADatabase sfaDatabase, String customerCode) {
        List<SalesmanMasterModel> salesmanMasterModelList = new ArrayList<>();

        sfaDatabase.openWritableDb();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CUSTOMER_SALESMAN_MASTER, new String[]{customerCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesmanMasterModel salesmanMasterModel = new SalesmanMasterModel();
                salesmanMasterModel.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)).trim());
                salesmanMasterModel.setSalesmanName(c.getString(c.getColumnIndex(COLUMN_SALESMAN_NAME)).trim());

                salesmanMasterModelList.add(salesmanMasterModel);
            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return salesmanMasterModelList;
    }

    public RetailerVO getRouteAndSalesManCount(BaseDB baseDB, String customerCode) {
        baseDB.openWritableDb();
        RetailerVO retailerVO = new RetailerVO();
        String query = "select COUNT(DISTINCT cusroute.routeCode) customerroutecount, COUNT(msalesroute.salesmanCode) salesmancount, cusroute.routeCode, msalesroute.salesmanCode, mroute.routeName, msales.salesmanName from t_customerroute cusroute LEFT JOIN \n" +
                "m_salesman_route_mapping msalesroute on msalesroute.routeCode = cusroute.routeCode INNER JOIN t_customer cus ON cus.customerCode=cusroute.customerCode\n" +
                "INNER JOIN m_route mroute ON mroute.routeCode=cusroute.routeCode\n" +
                "INNER JOIN m_salesman_master msales ON msales.salesmanCode=msalesroute.salesmanCode\n" +
                "where cus.customerCode='" + customerCode + "' AND cus.approvalStatus='A'";

        try {
            Cursor cm = baseDB.getDb().rawQuery(query, null);

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
                retailerVO.setRouteCount(cm.getInt(cm.getColumnIndex("customerroutecount")));
                retailerVO.setSalesmanCount(cm.getInt(cm.getColumnIndex("salesmancount")));
                retailerVO.setRouteCode(cm.getString(cm.getColumnIndex("routeCode")));
                retailerVO.setSalesmanCode(cm.getString(cm.getColumnIndex("salesmanCode")));
                retailerVO.setSalesmanName(cm.getString(cm.getColumnIndex("salesmanName")));
                retailerVO.setRouteName(cm.getString(cm.getColumnIndex("routeName")));
            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getRouteAndSalesManCount: " + e.getMessage(), e);
        }
        return retailerVO;
    }

    void updateUploadFlagStatus(SFADatabase sfaDatabase, String tableName, String uploadStatus, String[] parameters, String[] columns) {

        SQLiteDatabase db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(UPLOAD_FLAG, uploadStatus);
        StringBuilder updateWhereClause = new StringBuilder();

        for (int i = 0; i < columns.length; i++) {
            if (i == columns.length - 1) {
                updateWhereClause.append(columns[i]).append("=?");
            } else {
                updateWhereClause.append(columns[i]).append("=? AND ");
            }

        }
        db.update(tableName, values, updateWhereClause.toString(), parameters);
        sfaDatabase.closeDb();
    }

    public String getsupplierStateCode(SFADatabase sfaDatabase) {
        sfaDatabase.openWritableDb();

        String query = "SELECT " + GST_STATE_CODE + " FROM " + TABLE_SUPPLIER;
        Cursor cm = sfaDatabase.getDb().rawQuery(query, null);
        if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
            String value = cm.getString(cm.getColumnIndex(GST_STATE_CODE));
            cm.close();
            return value;
        }
        if (cm != null) {
            cm.close();
        }
        sfaDatabase.closeDb();
        return "";
    }

    public List<RetailerVO> getRetailerDataForRouteSeq(SFADatabase sfaDatabase, String cmpCode, String distrCode, String routeCode) {
        List<RetailerVO> retailerVOList = new ArrayList<>();
        sfaDatabase.openWritableDb();
        int count = 0;
        Cursor cursorPending = sfaDatabase.getDb().rawQuery(QUERY_SELECT_RETAILER_FOR_ROUTE_SEQ, new String[]{cmpCode, distrCode, routeCode, "A"});
        if (cursorPending != null && cursorPending.getCount() > 0 && cursorPending.moveToFirst()) {
            do {
                RetailerVO retailerVO = new RetailerVO();
                retailerVO.setCustomerCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
                retailerVO.setCustomerName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_NAME)).trim());
                retailerVO.setChannelCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                retailerVO.setLatitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LATITUDE)).trim());
                retailerVO.setLongitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LONGITUDE)).trim());
                retailerVO.setChannelName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_NAME)).trim());
                retailerVO.setApprovalStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_APPROVAL_STATUS)).trim());
                retailerVO.setRetailerStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_RETAILER_STATUS)).trim());
                retailerVO.setSeqNo(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_COVERAGE_SEQUENCE)).trim());
                count = count + 1;
                retailerVO.setSeqNo(String.valueOf(count));
                retailerVOList.add(retailerVO);

            } while (cursorPending.moveToNext());
        }
        if (cursorPending != null) {
            cursorPending.close();
        }
        sfaDatabase.closeDb();
        return retailerVOList;
    }


    public String getEncryptedDataByProcessName(SFADatabase sfaDatabase, String tagName) {

        String strValue = "";
        sfaDatabase.openWritableDb();
        String query = "SELECT processData from " + TABLE_DISTR_REPORT + " where processName = ?";

        try {
            Cursor cm = sfaDatabase.getDb().rawQuery(query,
                    new String[]{tagName});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
                strValue = cm.getString(cm.getColumnIndex(COLUMN_PROCESS_DATA));
            }
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            LoggerUtil.printErrorLog(TAG, "getEncryptedDataByProcessName: " + e.getMessage());
        }
        return strValue;
    }

    public List<RetailerVO> getRetailerDataBilling(SFADatabase sfaDatabase, String cmpCode, String distrCode, String routeCode) {
        List<RetailerVO> retailerVOList = new ArrayList<>();
        sfaDatabase.openWritableDb();
        int count = 0;
        Cursor cursorPending = sfaDatabase.getDb().rawQuery(QUERY_SELECT_RETAILER_BY_ROUTE_BILL, new String[]{cmpCode, distrCode, routeCode, "R", "Y"});
        if (cursorPending != null && cursorPending.getCount() > 0 && cursorPending.moveToFirst()) {
            do {
                RetailerVO retailerVO = new RetailerVO();
                retailerVO.setCustomerCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
                retailerVO.setCustomerName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_NAME)).trim());
                retailerVO.setChannelCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                retailerVO.setLatitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LATITUDE)).trim());
                retailerVO.setLongitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LONGITUDE)).trim());
                retailerVO.setChannelName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_NAME)).trim());
                retailerVO.setApprovalStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_APPROVAL_STATUS)).trim());
                retailerVO.setRetailerStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_RETAILER_STATUS)).trim());
                retailerVO.setSeqNo(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_COVERAGE_SEQUENCE)).trim());
                count = count + 1;
                retailerVO.setSeqNo(String.valueOf(count));
                retailerVOList.add(retailerVO);

            } while (cursorPending.moveToNext());
        }
        if (cursorPending != null) {
            cursorPending.close();
        }
        sfaDatabase.closeDb();
        return retailerVOList;
    }

    public List<RetailerVO> getRetailerDataBillingFromDB(SFADatabase sfaDatabase, String cmpCode, String distrCode) {
        List<RetailerVO> retailerVOList = new ArrayList<>();
        sfaDatabase.openWritableDb();
        Cursor cursorPending = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ALL_CUSTOMERS_BILL, null);
        if (cursorPending != null && cursorPending.getCount() > 0 && cursorPending.moveToFirst()) {
            do {
                RetailerVO retailerVO = new RetailerVO();
                retailerVO.setCustomerCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
                retailerVO.setCustomerName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_NAME)).trim());
                retailerVO.setChannelCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                retailerVO.setLatitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LATITUDE)).trim());
                retailerVO.setLongitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LONGITUDE)).trim());
                retailerVO.setChannelName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_NAME)).trim());
                retailerVO.setApprovalStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_APPROVAL_STATUS)).trim());
                retailerVO.setRetailerStatus(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_RETAILER_STATUS)).trim());
                retailerVOList.add(retailerVO);

            } while (cursorPending.moveToNext());
        }
        if (cursorPending != null) {
            cursorPending.close();
        }
        sfaDatabase.closeDb();
        return retailerVOList;
    }

    public RetailerVO getRouteAndSalesManCountBilling(BaseDB baseDB, String customerCode) {
        baseDB.openWritableDb();
        RetailerVO retailerVO = new RetailerVO();
        String query = "select COUNT(DISTINCT cusroute.routeCode) customerroutecount, COUNT(msalesroute.salesmanCode) salesmancount, cusroute.routeCode, msalesroute.salesmanCode, mroute.routeName, msales.salesmanName from t_customerroute cusroute LEFT JOIN \n" +
                "m_salesman_route_mapping msalesroute on msalesroute.routeCode = cusroute.routeCode INNER JOIN t_customer cus ON cus.customerCode=cusroute.customerCode\n" +
                "INNER JOIN m_route mroute ON mroute.routeCode=cusroute.routeCode\n" +
                "INNER JOIN m_salesman_master msales ON msales.salesmanCode=msalesroute.salesmanCode\n" +
                "where cus.customerCode='" + customerCode + "'  AND cus.approvalStatus!='R'";

        try {
            Cursor cm = baseDB.getDb().rawQuery(query, null);

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
                retailerVO.setRouteCount(cm.getInt(cm.getColumnIndex("customerroutecount")));
                retailerVO.setSalesmanCount(cm.getInt(cm.getColumnIndex("salesmancount")));
                retailerVO.setRouteCode(cm.getString(cm.getColumnIndex("routeCode")));
                retailerVO.setSalesmanCode(cm.getString(cm.getColumnIndex("salesmanCode")));
                retailerVO.setSalesmanName(cm.getString(cm.getColumnIndex("salesmanName")));
                retailerVO.setRouteName(cm.getString(cm.getColumnIndex("routeName")));
            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getRouteAndSalesManCountBilling: " + e.getMessage(), e);
        }
        return retailerVO;
    }

    String getSuffixYyForScreen(SFADatabase sfaDatabase, String screenName) {

        String code = "";
        String query = "SELECT " + COLUMN_SUFFIX_YY + " FROM " + TABLE_CODE_GENERATOR +
                " WHERE " + COLUMN_SCREEN_NAME + " = ? LIMIT 1";

        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{screenName});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            code = c.getString(c.getColumnIndex(COLUMN_SUFFIX_YY));
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return code;
    } //New Distributors Info
    public ArrayList<Distributor> getDistributorList(BaseDB baseDB) {
        Distributor distributor = null;
        ArrayList<Distributor> distributors = new ArrayList<>();
        baseDB.openWritableDb();
        String query = selectAllFormTableQuery(TABLE_DISTRIBUTOR);
        Cursor cm = baseDB.getDb().rawQuery(GET_DISTRIBUTOR_LIST, null);

        if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
            distributor=getDistributorModel(cm);
            distributor.setCompanyName(cm.getString(cm.getColumnIndex(COLUMN_CMP_NAME)));
            distributors.add(distributor);
        } while (cm.moveToNext());

        if (cm != null) {
            cm.close();
        }
        baseDB.closeDb();
        return distributors;
    }
    public ArrayList<CustomerModel> getUniqueCustomerList(BaseDB baseDB) {

        CustomerModel distributor = null;

        ArrayList<CustomerModel> distributors = new ArrayList<>();

        baseDB.openWritableDb();

//        String query = "select DISTINCT(customerCode),cmpcode, distrCode from t_customer";

        String query = "select DISTINCT(c.customerCode),c.cmpcode, c.distrCode, com.cmpName, dis.distrName from t_customer AS c INNER JOIN m_company_detail AS com ON c.cmpcode=com.cmpCode INNER JOIN m_distributor AS dis ON c.cmpcode=dis.cmpCode AND c.distrCode=dis.distrCode";        Cursor cm = baseDB.getDb().rawQuery(query, null);



        if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {

            distributor = new CustomerModel();

            distributor.setCmpCode(cm.getString(cm.getColumnIndex(COLUMN_CMP_CODE)));

            distributor.setDistrCode(cm.getString(cm.getColumnIndex(COLUMN_DISTR_CODE)));

            distributor.setCustomerCode(cm.getString(cm.getColumnIndex(COLUMN_CUSTOMER_CODE)));

            distributor.setCmpName(cm.getString(cm.getColumnIndex(COLUMN_CMP_NAME)));

            distributor.setDistrName(cm.getString(cm.getColumnIndex(COLUMN_DISTR_NAME)));

            distributors.add(distributor);

        } while (cm.moveToNext());



        if (cm != null) {

            cm.close();

        }

        baseDB.closeDb();

        return distributors;

    }
    //New Distributors Info
    Distributor getDistributorInfo(BaseDB baseDB, String userCode,String cmpCode) {

        Distributor distributor = null;
        baseDB.openWritableDb();

        try {
            Cursor cm = baseDB.getDb().rawQuery(QUERY_SELECT_DISTRIBUTOR_INFO, new String[]{userCode,cmpCode});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
                distributor = getDistributorModel(cm);
            }
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getDistributor: " + e.getMessage(), e);
        }
        return distributor;
    }

    boolean isCustomerSchemeAvailable(BaseDB baseDB) {

        baseDB.openWritableDb();
        String query = selectAllFormTableQuery(TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST);
        Cursor cm = baseDB.getDb().rawQuery(query, null);
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        baseDB.closeDb();

        return false;
    }

    public ArrayList<FeedBackMasterModel> getFeedbackList(BaseDB baseDB) {
        ArrayList<FeedBackMasterModel> feedBackMasterModelArrayList = new ArrayList<>();
        baseDB.openWritableDb();
        String query = selectAllFormTableQuery(TABLE_FEEDBACK_MASTER);
        Cursor cm = baseDB.getDb().rawQuery(query, null);

        if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
            FeedBackMasterModel feedBackMasterModel = new FeedBackMasterModel();
            feedBackMasterModel.setCmpCode(cm.getString(cm.getColumnIndex(COLUMN_CMP_CODE)));
            feedBackMasterModel.setFeedbackType(cm.getString(cm.getColumnIndex(COLUMN_FEEDBACK_TYPE)));
            feedBackMasterModel.setFeedbackName(cm.getString(cm.getColumnIndex(COLUMN_FEEDBACK_NAME)));
            feedBackMasterModel.setConsoleFlag(cm.getString(cm.getColumnIndex(COLUMN_CONSOLE_FLAG)));
            feedBackMasterModel.setUploadFlag(cm.getString(cm.getColumnIndex(UPLOAD_FLAG)));
            feedBackMasterModel.setModDt(cm.getString(cm.getColumnIndex(COLUMN_MOD_DT)));
            feedBackMasterModel.setChangeNo(cm.getString(cm.getColumnIndex(COLUMN_CHANGE_NO)));
            feedBackMasterModelArrayList.add(feedBackMasterModel);
        } while (cm.moveToNext());

        if (cm != null) {
            cm.close();
        }
        baseDB.closeDb();

        return feedBackMasterModelArrayList;
    }

    public void insertFeedback(BaseDB baseDB, FeedbackModel feedbackModel) {
        SQLiteDatabase db = baseDB.getDb();
        String sql = "INSERT INTO " + TABLE_FEEDBACK + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        try {
            statement.clearBindings();
            statement.bindString(2, Globals.checkNull(feedbackModel.getCmpCode()));
            statement.bindString(3, Globals.checkNull(feedbackModel.getFeedbackNo()));
            statement.bindString(4, Globals.checkNull(feedbackModel.getFeedbackDate()));
            statement.bindString(5, Globals.checkNull(feedbackModel.getDistrCode()));
            statement.bindString(6, Globals.checkNull(feedbackModel.getCustomerCode()));
            statement.bindString(7, Globals.checkNull(feedbackModel.getFeedbackType()));
            statement.bindString(8, Globals.checkNull(feedbackModel.getMessage()));
            statement.bindString(9, Globals.checkNull(feedbackModel.getFeedbackImage()));
            statement.bindString(10, "N");

            statement.execute();
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w(DUPLICATE_TAG, e);
        }

    }

    public ArrayList<FeedbackModel> getAllFeedback(BaseDB baseDB) {
        ArrayList<FeedbackModel> feedbackModelList = new ArrayList<>();
        baseDB.openWritableDb();
        String query = selectAllFormTableQuery(TABLE_FEEDBACK);
        Cursor c = baseDB.getDb().rawQuery(query, null);

        if (c != null && c.getCount() > 0 && c.moveToFirst()) do {
            FeedbackModel feedbackModel = new FeedbackModel();
            feedbackModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
            feedbackModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
            feedbackModel.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
            feedbackModel.setFeedbackNo(c.getString(c.getColumnIndex(COLUMN_FEEDBACK_NO)));
            feedbackModel.setFeedbackType(c.getString(c.getColumnIndex(COLUMN_LABLE_VALUE)));
            feedbackModel.setMessage(c.getString(c.getColumnIndex(MSG_BODY)));
            feedbackModel.setFeedbackImage(c.getString(c.getColumnIndex(COLUMN_IMAGE)));
            feedbackModel.setFeedbackDate(c.getString(c.getColumnIndex(COLUMN_FEEDBACK_DATE)));
            feedbackModelList.add(feedbackModel);
        } while (c.moveToNext());

        if (c != null) {
            c.close();
        }
        baseDB.closeDb();

        return feedbackModelList;
    }

    public List<String> getAllMappedCompany(BaseDB baseDB) {
        ArrayList<String> feedbackModelList = new ArrayList<>();
        baseDB.openWritableDb();
        String query = selectAllFormTableQuery(TABLE_MAPPED_COMPANY);
        Cursor c = baseDB.getDb().rawQuery(query, null);

        if (c != null && c.getCount() > 0 && c.moveToFirst()) do {
            feedbackModelList.add(c.getString(c.getColumnIndex(COLUMN_NAME)));
        } while (c.moveToNext());

        if (c != null) {
            c.close();
        }
        baseDB.closeDb();

        return feedbackModelList;
    }

    public List<String> getAllMappedUsers(BaseDB baseDB) {
        ArrayList<String> feedbackModelList = new ArrayList<>();
        baseDB.openWritableDb();
        String query = selectAllFormTableQuery(TABLE_MAPPED_USER);
        Cursor c = baseDB.getDb().rawQuery(query, null);

        if (c != null && c.getCount() > 0 && c.moveToFirst()) do {
            feedbackModelList.add(c.getString(c.getColumnIndex(COLUMN_NAME)));
        } while (c.moveToNext());

        if (c != null) {
            c.close();
        }
        baseDB.closeDb();

        return feedbackModelList;
    }

}
