package com.botree.retailerssfa.db.query;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.db.BaseDB;
import com.botree.retailerssfa.db.IDbCallback;

import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.Scheme;
import com.botree.retailerssfa.models.SchemeCustomerZipModel;
import com.botree.retailerssfa.models.SchemeDistrBudgetModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.models.SchemeZipModel;
import com.botree.retailerssfa.service.DayStartHelper;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCHEME_COMBI_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCHEME_DEFINITION_LIST;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCHEME_DISTRIBUTOR_BUDGET;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCHEME_SLAB_LIST;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCHEME_SLAB_PRODUCT_LIST;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCHEME_ZIP_LIST;
import static com.botree.retailerssfa.service.DayStartHelper.isJsonNotNull;
import static com.botree.retailerssfa.service.DayStartHelper.isNewApkAvailable;
import static com.botree.retailerssfa.service.JSONConstants.TAG_COVERAGE_EXISTS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_COMBI_PRODUCT_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_CUSTOMER_MAPPING;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_DEFINITION_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_DISTR_BUDGET_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_SLAB_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_SLAB_PRODUCT_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SERVER_DATE_MATCHED;
import static com.botree.retailerssfa.service.JSONConstants.TAG_USER_ACTIVE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;

public class SchemeDownloadHelper {
    private static final String DUPLICATE_TAG = "Duplicate tag";
    private String insertInto = "INSERT INTO ";
    private String TAG = SchemeDownloadHelper.class.getSimpleName();
    private SQLiteDatabase db;
    private AppUtils appUtils;
    private Scheme scheme;
    private SchemeZipModel response;
    private Context context;

    public SchemeDownloadHelper(Context context, SchemeZipModel response) {
        this.context = context;
        this.response = response;

        appUtils = AppUtils.getOurInstance();
        db = SFADatabase.getInstance(context).getDb();

        dayStart();
    }

    private void schemeDataInsert(final Scheme scheme, final IDbCallback<Boolean> callback) {
        try {
            db.beginTransaction();
            bulkInsert(scheme);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "schemeDataInsert: " + e.getMessage(), e);
        } finally {
            db.endTransaction();
        }

        if (callback != null) {
            callback.response(true);
            callback.onSuccess();
        }
    }

    private void bulkInsert(Scheme scheme) {
        if (!scheme.getSchemeDefinitionList().isEmpty()) {
            insertSchemeDefinitionList(scheme.getSchemeDefinitionList());
        }

        if (!scheme.getSchemeSlabList().isEmpty()) {
            insertSchemeSlabList(scheme.getSchemeSlabList());
        }

        if (!scheme.getSchemeSlabProductList().isEmpty()) {
            insertSchemeSlabProducts(scheme.getSchemeSlabProductList());
        }

        if (!scheme.getSchemeDistrBudgetList().isEmpty()) {
            insertSchemeDistrBudget(scheme.getSchemeDistrBudgetList());
        }

        if (!scheme.getSchemeCustomerMapping().isEmpty()) {
            insertCustomerSchemeProductDetails(scheme.getSchemeCustomerMapping());
        }

        if (!scheme.getSchemeCombiProductList().isEmpty()) {
            insertSchemeCombiProducts(scheme.getSchemeCombiProductList());
        }
    }

    public void insertCustomerSchemeProductDetails(List<SchemeModel> schemeProductDetail) {

        String sql = "INSERT INTO " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < schemeProductDetail.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, schemeProductDetail.get(i).getCmpCode());
                statement.bindString(2, schemeProductDetail.get(i).getDistrCode());
                statement.bindString(3, schemeProductDetail.get(i).getCustomerCode());
                statement.bindString(4, schemeProductDetail.get(i).getProductCode());
                statement.bindString(5, schemeProductDetail.get(i).getProdName());
                statement.bindString(6, schemeProductDetail.get(i).getSchemeCode());
                statement.bindString(7, schemeProductDetail.get(i).getSchemeBase());
                statement.bindString(8, schemeProductDetail.get(i).getPayoutType());
                statement.bindString(9, schemeProductDetail.get(i).getSchemeFromDt());
                statement.bindString(10, schemeProductDetail.get(i).getSchemeToDt());
                statement.bindString(11, schemeProductDetail.get(i).getCombi());
                statement.bindString(12, schemeProductDetail.get(i).getSchemeDescription());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }



    private void addSchemeDefinitionList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_SCHEME_DEFINITION_LIST))
            return;

        List<SchemeModel> schemeDefinitionList;
        if (jsonObj.get(TAG_SCHEME_DEFINITION_LIST) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SCHEME_DEFINITION_LIST));
            JSONArray jsonArray = new JSONArray(responseString);
            schemeDefinitionList = appUtils.getConvertedListFromJson(jsonArray, SchemeModel.class);

            scheme.setSchemeDefinitionList(schemeDefinitionList);
        }
    }

    private void addSchemeSlabList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_SCHEME_SLAB_LIST))
            return;

        List<SchemeModel> schemeSlabList;
        if (jsonObj.get(TAG_SCHEME_SLAB_LIST) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SCHEME_SLAB_LIST));
            JSONArray jsonArray = new JSONArray(responseString);
            schemeSlabList = appUtils.getConvertedListFromJson(jsonArray, SchemeModel.class);


            scheme.setSchemeSlabList(schemeSlabList);
        }

    }

    private void addSchemeSlabProdList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_SCHEME_SLAB_PRODUCT_LIST))
            return;

        List<SchemeModel> schemeSlabProductList;
        if (jsonObj.get(TAG_SCHEME_SLAB_PRODUCT_LIST) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SCHEME_SLAB_PRODUCT_LIST));
            JSONArray jsonArray = new JSONArray(responseString);
            schemeSlabProductList = appUtils.getConvertedListFromJson(jsonArray, SchemeModel.class);

            scheme.setSchemeSlabProductList(schemeSlabProductList);
        }

    }

    private void addSchemeDistrBudgetList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_SCHEME_DISTR_BUDGET_LIST))
            return;

        List<SchemeDistrBudgetModel> schemeDistrBudgetList;
        if (jsonObj.get(TAG_SCHEME_DISTR_BUDGET_LIST) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SCHEME_DISTR_BUDGET_LIST));
            JSONArray jsonArray = new JSONArray(responseString);
            schemeDistrBudgetList = appUtils.getConvertedListFromJson(jsonArray, SchemeDistrBudgetModel.class);

            scheme.setSchemeDistrBudgetList(schemeDistrBudgetList);
        }
    }

    private void addSchemeCustomerMappingList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_SCHEME_CUSTOMER_MAPPING))
            return;

        List<SchemeModel> schemeList;
        if (jsonObj.get(TAG_SCHEME_CUSTOMER_MAPPING) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SCHEME_CUSTOMER_MAPPING));
            JSONArray jsonArray = new JSONArray(responseString);
            schemeList = appUtils.getConvertedListFromJson(jsonArray, SchemeModel.class);

            scheme.setSchemeCustomerMapping(schemeList);
        }
    }

    private void addSchemeCombiProdList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_SCHEME_COMBI_PRODUCT_LIST))
            return;

        List<SchemeModel> schemeCombiProductList;
        if (jsonObj.get(TAG_SCHEME_COMBI_PRODUCT_LIST) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SCHEME_COMBI_PRODUCT_LIST));
            JSONArray jsonArray = new JSONArray(responseString);
            schemeCombiProductList = appUtils.getConvertedListFromJson(jsonArray, SchemeModel.class);

            scheme.setSchemeCombiProductList(schemeCombiProductList);
        }

    }

    private void insertSchemeDefinitionList(List<SchemeModel> schemeModels) {

        String sql = insertInto + TABLE_SCHEME_DEFINITION_LIST + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < schemeModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, Globals.checkNull(schemeModels.get(i).getCmpCode()));
            statement.bindString(2, Globals.checkNull(schemeModels.get(i).getSchemeCode()));
            statement.bindString(3, Globals.checkNull(schemeModels.get(i).getSchemeDescription()));
            statement.bindString(4, Globals.checkNull(schemeModels.get(i).getSchemeBase()));
            statement.bindString(5, Globals.checkNull(schemeModels.get(i).getSchemeFromDt()));
            statement.bindString(6, Globals.checkNull(schemeModels.get(i).getSchemeToDt()));
            statement.bindString(7, Globals.checkNull(schemeModels.get(i).getPayoutType()));
            statement.bindString(8, Globals.checkNull(schemeModels.get(i).getIsSkuLevel()));
            statement.bindString(9, Globals.checkNull(schemeModels.get(i).getIsActive()));
            statement.bindString(10, Globals.checkNull(schemeModels.get(i).getCombi()));

            statement.execute();
        }
    }

    private void insertSchemeSlabProducts(List<SchemeModel> surveyVOS) {

        String sql = insertInto + TABLE_SCHEME_SLAB_PRODUCT_LIST + " VALUES (?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < surveyVOS.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, Globals.checkNull(surveyVOS.get(i).getCmpCode()));
            statement.bindString(2, Globals.checkNull(surveyVOS.get(i).getSchemeCode()));
            statement.bindString(3, Globals.checkNull(surveyVOS.get(i).getSlabNo()));
            statement.bindString(4, Globals.checkNull(surveyVOS.get(i).getProductCode()));
            statement.bindString(5, Globals.checkNull(surveyVOS.get(i).getProdName()));
            statement.bindString(6, Globals.checkNull(String.valueOf(surveyVOS.get(i).getQuantity())));
            statement.bindString(7, Globals.checkNull(surveyVOS.get(i).getIsMandatory(),"N"));

            statement.execute();
        }
    }

    private void insertSchemeSlabList(List<SchemeModel> schemeModels) {

        String sql = insertInto + TABLE_SCHEME_SLAB_LIST + " VALUES (?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < schemeModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, Globals.checkNull(schemeModels.get(i).getCmpCode()));
            statement.bindString(2, Globals.checkNull(schemeModels.get(i).getSchemeCode()));
            statement.bindString(3, Globals.checkNull(schemeModels.get(i).getSlabNo()));
            statement.bindString(4, Globals.checkNull(String.valueOf(schemeModels.get(i).getSlabFrom())));
            statement.bindString(5, Globals.checkNull(String.valueOf(schemeModels.get(i).getSlabTo())));
            statement.bindString(6, Globals.checkNull(String.valueOf(schemeModels.get(i).getPayoutValue())));
            statement.bindString(7, Globals.checkNull(schemeModels.get(i).getSlabRange()));
            statement.bindString(8, Globals.checkNull(schemeModels.get(i).getUom()));
            statement.bindString(9, Globals.checkNull(String.valueOf(schemeModels.get(i).getForEvery())));

            statement.execute();
        }
    }

    private void insertSchemeDistrBudget(List<SchemeDistrBudgetModel> schemeModels) {

        String sql = insertInto + TABLE_SCHEME_DISTRIBUTOR_BUDGET + " VALUES (?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < schemeModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, Globals.checkNull(schemeModels.get(i).getCmpCode()));
            statement.bindString(2, Globals.checkNull(schemeModels.get(i).getDistrCode()));
            statement.bindString(3, Globals.checkNull(schemeModels.get(i).getSchemeCode()));
            statement.bindString(4, Globals.checkNull(schemeModels.get(i).getIsUnlimited()));
            statement.bindString(5, Globals.checkNull(String.valueOf(schemeModels.get(i).getBudget())));
            statement.bindString(6, Globals.checkNull(schemeModels.get(i).getIsActive()));
            statement.bindString(7, Globals.checkNull(String.valueOf(schemeModels.get(i).getUtilizedAmount())));
            statement.bindString(8, Globals.checkNull(String.valueOf(schemeModels.get(i).getBudgetOs())));
            statement.bindString(9, Globals.checkNull(schemeModels.get(i).getUploadFlag(),"Y"));

            statement.execute();
        }
    }

    public void insertSchemeCustomerMappingZip(List<SchemeCustomerZipModel> schemeZipModels) {
        String sql = insertInto + TABLE_SCHEME_ZIP_LIST + " VALUES (?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);

        int listSize = schemeZipModels.size();
        for (int i = 0; i < listSize; i++) {
            statement.clearBindings();
            statement.bindString(1, SFASharedPref.getOurInstance().readString(PREF_CMP_CODE));
            statement.bindString(2, SFASharedPref.getOurInstance().readString(PREF_DISTRCODE));
            Log.d(TAG,"key : "+i+" : "+ schemeZipModels.get(i).getKey());
            Log.d(TAG,"value : "+i+" : "+ schemeZipModels.get(i).getValue());
            statement.bindString(3, schemeZipModels.get(i).getKey());
            statement.bindString(4,schemeZipModels.get(i).getValue());

            statement.execute();
        }
    }

    private void insertSchemeCombiProducts(List<SchemeModel> surveyVOS) {

        String sql = insertInto + TABLE_SCHEME_COMBI_PRODUCTS + " VALUES (?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < surveyVOS.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, Globals.checkNull(surveyVOS.get(i).getCmpCode()));
            statement.bindString(2, Globals.checkNull(surveyVOS.get(i).getSchemeCode()));
            statement.bindString(3, Globals.checkNull(surveyVOS.get(i).getSlabNo()));
            statement.bindString(4, Globals.checkNull(surveyVOS.get(i).getProductCode()));
            statement.bindString(5, Globals.checkNull(surveyVOS.get(i).getProdName()));
            statement.bindString(6, String.valueOf(surveyVOS.get(i).getMinValue()));
            if(surveyVOS.get(i).getIsMandatory().equals("1")) {
                statement.bindString(7, Globals.checkNull(surveyVOS.get(i).getIsMandatory(), "Y"));
            }else {
                statement.bindString(7, Globals.checkNull(surveyVOS.get(i).getIsMandatory(), "N"));
            }

            statement.execute();
        }
    }

    public DayStartHelper.AsyncResponse dayStart() {
        try {
            String json = new Gson().toJson(response);
            JSONObject jsonObj = new JSONObject(json);

            if (jsonObj.has(TAG_SERVER_DATE_MATCHED) &&
                    jsonObj.get(TAG_SERVER_DATE_MATCHED) instanceof Boolean &&
                    !jsonObj.getBoolean(TAG_SERVER_DATE_MATCHED)) {

                return DayStartHelper.AsyncResponse.SERVER_DATE_MISMATCH;

            } else if (jsonObj.has(TAG_USER_ACTIVE) &&
                    jsonObj.get(TAG_USER_ACTIVE) instanceof Boolean &&
                    !jsonObj.getBoolean(TAG_USER_ACTIVE)) {
                Log.d(TAG, "jsonObj.getBoolean(TAG_USER_ACTIVE) : " + jsonObj.getBoolean(TAG_USER_ACTIVE));
                return DayStartHelper.AsyncResponse.USER_ACTIVE;

            } else if (isNewApkAvailable(context, jsonObj)) {
                return DayStartHelper.AsyncResponse.NEW_APK_AVAILABLE;
            } else if (jsonObj.has(TAG_COVERAGE_EXISTS) &&
                    jsonObj.get(TAG_COVERAGE_EXISTS) instanceof Boolean &&
                    !jsonObj.getBoolean(TAG_COVERAGE_EXISTS)) {

                return DayStartHelper.AsyncResponse.NO_COVERAGE_EXISTS;

            } else {
                scheme = Scheme.getInstance();

                addSchemeDefinitionList(jsonObj);
                addSchemeSlabList(jsonObj);
                addSchemeSlabProdList(jsonObj);
                addSchemeDistrBudgetList(jsonObj);
                addSchemeCustomerMappingList(jsonObj);
                addSchemeCombiProdList(jsonObj);

                schemeDataInsert(scheme, null);
            }
        } catch (SQLiteException e) {
            Log.e("dayStart 1: ", e.getMessage(), e);
            if (BuildConfig.DEBUG)
                Log.w(TAG, e);
            return DayStartHelper.AsyncResponse.DAY_START_ERROR;

        } catch (JSONException e) {
            Log.e(TAG, "dayStart: " + e.getMessage(), e);
            return DayStartHelper.AsyncResponse.DAY_START_ERROR;
        }
        return DayStartHelper.AsyncResponse.DAY_START_DONE;
    }
}
