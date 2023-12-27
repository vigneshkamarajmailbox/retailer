package com.botree.retailerssfa.db.query;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.db.IDbCallback;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.DistrReportModel;
import com.botree.retailerssfa.models.ReportDataModel;
import com.botree.retailerssfa.models.ReportZipModel;
import com.botree.retailerssfa.service.DayStartHelper;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.AppUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_REPORT_FIELD_MAPPING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_REPORT_PROCESS_MAPPING;
import static com.botree.retailerssfa.service.DayStartHelper.isJsonNotNull;
import static com.botree.retailerssfa.service.DayStartHelper.isNewApkAvailable;
import static com.botree.retailerssfa.service.JSONConstants.TAG_COVERAGE_EXISTS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_REPORT_FIELD_MAPPING;
import static com.botree.retailerssfa.service.JSONConstants.TAG_REPORT_PROCESS_MAPPING;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SERVER_DATE_MATCHED;
import static com.botree.retailerssfa.service.JSONConstants.TAG_USER_ACTIVE;

public class ReportDownloadHelper {
    private String TAG = ReportDownloadHelper.class.getSimpleName();
    private SQLiteDatabase db;
    private AppUtils appUtils;
    private Context context;
    private ReportZipModel response;
    private ReportDataModel reportDataModel;
    private String insertInto = "INSERT INTO ";
    private SFADatabase sfaDb;
    public ReportDownloadHelper(Context context, ReportZipModel response) {
        this.context = context;
        this.response = response;

        appUtils = AppUtils.getOurInstance();
        db = SFADatabase.getInstance(context).getDb();
        sfaDb = SFADatabase.getInstance(context);
        dayStart();
    }

    private DayStartHelper.AsyncResponse dayStart() {
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
                reportDataModel = ReportDataModel.getInstance();

                addReportProcessMapping(jsonObj);
                addReportFieldMapping(jsonObj);

                reportDataInsert(reportDataModel, null);
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

    private void reportDataInsert(final ReportDataModel reportDataModel, final IDbCallback<Boolean> callback) {
        try {
            db.beginTransaction();
            sfaDb.removeExistingOnlineReportTableInfo();
            bulkInsert(reportDataModel);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "reportDataInsert: " + e.getMessage(), e);
        } finally {
            db.endTransaction();
        }

        if (callback != null) {
            callback.response(true);
            callback.onSuccess();
        }
    }

    private void bulkInsert(ReportDataModel reportDataModel) {
        if (!reportDataModel.getReportProcessMapping().isEmpty()) {
            insertReportProcessMappingList(reportDataModel.getReportProcessMapping());
        }

        if (!reportDataModel.getReportFieldMapping().isEmpty()) {
            insertReportFieldMappingList(reportDataModel.getReportFieldMapping());
        }
    }

    private void insertReportFieldMappingList(List<DistrReportModel> reportFieldMapping) {
        String sql = insertInto + TABLE_REPORT_FIELD_MAPPING + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < reportFieldMapping.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, Globals.checkNull(reportFieldMapping.get(i).getCmpCode()));
            statement.bindString(2, Globals.checkNull(reportFieldMapping.get(i).getChangeNo()));
            statement.bindString(3, Globals.checkNull(reportFieldMapping.get(i).getProcessId()));
            statement.bindString(4, Globals.checkNull(reportFieldMapping.get(i).getTableName()));
            statement.bindString(5, Globals.checkNull(reportFieldMapping.get(i).getColumnHeader()));
            statement.bindString(6, Globals.checkNull(reportFieldMapping.get(i).getColumnGroup()));
            statement.bindString(7, Globals.checkNull(reportFieldMapping.get(i).getColumnType()));
            statement.bindString(8, Globals.checkNull(reportFieldMapping.get(i).getColumnField()));
            statement.bindString(9, Globals.checkNull(reportFieldMapping.get(i).getColumnEnable()));
            statement.bindString(10, Globals.checkNull(reportFieldMapping.get(i).getChartEnable()));
            statement.bindString(11, Globals.checkNull(reportFieldMapping.get(i).getFilterEnable()));
            statement.bindString(12, Globals.checkNull(reportFieldMapping.get(i).getSequence()));
            statement.bindString(13, Globals.checkNull(reportFieldMapping.get(i).getColumnFunction()));
            statement.bindString(14, Globals.checkNull(reportFieldMapping.get(i).getConsoleFlag()));
            statement.bindString(15, Globals.checkNull(reportFieldMapping.get(i).getUploadFlag()));
            statement.bindString(16, Globals.checkNull(reportFieldMapping.get(i).getModDt()));

            statement.execute();
        }
    }

    private void insertReportProcessMappingList(List<DistrReportModel> reportProcessMapping) {
        String sql = insertInto + TABLE_REPORT_PROCESS_MAPPING + " VALUES (?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < reportProcessMapping.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, Globals.checkNull(reportProcessMapping.get(i).getCmpCode()));
            statement.bindString(2, Globals.checkNull(reportProcessMapping.get(i).getChangeNo()));
            statement.bindString(3, Globals.checkNull(reportProcessMapping.get(i).getProcessId()));
            statement.bindString(4, Globals.checkNull(reportProcessMapping.get(i).getProcessName()));
            statement.bindString(5, Globals.checkNull(reportProcessMapping.get(i).getQueryName()));
            statement.bindString(6, Globals.checkNull(reportProcessMapping.get(i).getConsoleFlag()));
            statement.bindString(7, Globals.checkNull(reportProcessMapping.get(i).getUploadFlag()));
            statement.bindString(8, Globals.checkNull(reportProcessMapping.get(i).getModDt()));

            statement.execute();
        }
    }

    private void addReportFieldMapping(JSONObject jsonObj) throws JSONException  {

        if (!isJsonNotNull(jsonObj, TAG_REPORT_FIELD_MAPPING))
            return;

        List<DistrReportModel> reportModelList;
        if (jsonObj.get(TAG_REPORT_FIELD_MAPPING) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_REPORT_FIELD_MAPPING));
            JSONArray jsonArray = new JSONArray(responseString);
            reportModelList = appUtils.getConvertedListFromJson(jsonArray, DistrReportModel.class);

            reportDataModel.setReportFieldMapping(reportModelList);
        }
    }

    private void addReportProcessMapping(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_REPORT_PROCESS_MAPPING))
            return;

        List<DistrReportModel> reportModelList;
        if (jsonObj.get(TAG_REPORT_PROCESS_MAPPING) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_REPORT_PROCESS_MAPPING));
            JSONArray jsonArray = new JSONArray(responseString);
            reportModelList = appUtils.getConvertedListFromJson(jsonArray, DistrReportModel.class);

            reportDataModel.setReportProcessMapping(reportModelList);
        }
    }
}
