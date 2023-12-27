package com.botree.retailerssfa.db.query;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.db.BaseDB;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.RetailerVO;
import com.botree.retailerssfa.models.RouteModel;
import com.botree.retailerssfa.models.SalesmanRouteCoverageModel;
import com.botree.retailerssfa.models.SalesmanRouteCoveragePlanModel;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.botree.retailerssfa.db.query.DashboardQueryHelper.QUERY_ROUTES;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CONFIRM;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_COVERAGE_DT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_COVERAGE_SEQUENCE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DAY_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_END_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PLAN_TYPE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_REF_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_TYPE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALESMAN_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_START_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_TEMP_ROUTE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UPLOAD;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CUSTOMER_ROUTE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ROUTE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ROUTE_SEQUENCING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALESMAN_ROUTE_COVERAGE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALESMAN_ROUTE_COVERAGE_PLAN;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALESMAN_ROUTE_MAPPING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_TEMP_ROUTE;
import static com.botree.retailerssfa.db.query.IDBColumns.UPLOAD_FLAG;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.UPDATE_CHECK_CMP_DC_RC_CC;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;

/*
 *Created by Godlin Josheela Rani S on 16/4/19.
 */

public class RouteQueryHelper {
    private static final String TAG = RouteQueryHelper.class.getSimpleName();

    public RouteQueryHelper() {
        //Empty Private Constructor
    }

    public static void insertRoute(SFADatabase sfaDatabase, RouteModel routeModel) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "insertRoute : model : " + routeModel.toString());

        String sql = "INSERT INTO " + TABLE_ROUTE + " VALUES (?,?,?,?,?,?);";

        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
        try {
            statement.clearBindings();
            statement.bindString(1, routeModel.getCompanyCode());
            statement.bindString(2, routeModel.getDistributorCode());
            statement.bindString(3, routeModel.getRouteCode());
            statement.bindString(4, routeModel.getRouteName());
            statement.bindString(5, routeModel.getRouteType());
            statement.bindString(6, routeModel.getUploadFlag());
            statement.execute();
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w(TAG, e);
        }
    }

    public static void updateRouteBasedOnId(SFADatabase sfaDatabase, RouteModel routeModel) {
        SQLiteDatabase db = sfaDatabase.getDb();
        try {
            ContentValues contentValues = getCvForRouteUpdate(routeModel);
            String update = COLUMN_ROUTE_CODE + "=?";

            db.update(TABLE_ROUTE, contentValues, update, new String[]{routeModel.getRouteCode()});
        } finally {
            sfaDatabase.closeDb();
        }
    }

    private static ContentValues getCvForRouteUpdate(RouteModel routeModel) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROUTE_NAME, routeModel.getRouteName());
        values.put(COLUMN_UPLOAD, "N");

        return values;
    }

    public static void insertCustomerRoute(SFADatabase sfaDatabase, String cmpCode, String distCode, List<RouteModel> routeList, String customerCode) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER_ROUTE + " VALUES (?,?,?,?,?,?,?);";

        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
        for (int i = 0; i < routeList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, cmpCode);
                statement.bindString(2, distCode);
                if(routeList.get(i).getCustomerCode().isEmpty()){
                    statement.bindString(3, customerCode);
                }else {
                    statement.bindString(3, routeList.get(i).getCustomerCode());
                }
                statement.bindString(4, routeList.get(i).getRouteCode());
                statement.bindString(5, routeList.get(i).getCoverageSeq());
                statement.bindString(6, DateUtil.getCurrentYearMonthDate());
                statement.bindString(7, "N");
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.e(TAG,"insertCustomerRoute", e);
            }
        }
    }

    public List<RouteModel> getRoutes(SFADatabase sfaDatabase, String distCode) {
        List<RouteModel> routeList = new ArrayList<>();
        Cursor cursor;
        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        cursor = database.rawQuery(QUERY_ROUTES, new String[]{distCode});

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                RouteModel routeVO = new RouteModel();

                routeVO.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                routeVO.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                routeVO.setRetCount(cursor.getString(cursor.getColumnIndex("count")).trim());
                routeVO.setRouteType(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_TYPE)).trim());
                routeList.add(routeVO);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public List<RouteModel> getRouteMasterList(BaseDB baseDB, String distCode) {

        List<RouteModel> routeList = new ArrayList<>();
        Cursor cursor;
        cursor = baseDB.getDb().rawQuery(QUERY_ROUTES, new String[]{distCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                RouteModel routeModel = new RouteModel();

                routeModel.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                routeModel.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                routeModel.setRouteType(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_TYPE)).trim());
                routeModel.setRetCount(cursor.getString(cursor.getColumnIndex("count")).trim());

                routeList.add(routeModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public List<RouteModel> getRoutesForUpload(BaseDB baseDB, String distCode) {

        List<RouteModel> routeList = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT * From " + TABLE_ROUTE + " WHERE distrCode=? AND upload = 'N'";
        cursor = baseDB.getDb().rawQuery(query, new String[]{distCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                RouteModel routeModel = new RouteModel();

                routeModel.setCompanyCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)).trim());
                routeModel.setDistributorCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                routeModel.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                routeModel.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                routeModel.setRouteType(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_TYPE)).trim());
                routeModel.setUploadFlag(cursor.getString(cursor.getColumnIndex(COLUMN_UPLOAD)).trim());

                routeList.add(routeModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public List<RouteModel> getTempRoutesForUpload(BaseDB baseDB, String distCode) {

        List<RouteModel> routeList = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT * From " + TABLE_TEMP_ROUTE + " WHERE distrCode=? AND upload = 'Y'";
        cursor = baseDB.getDb().rawQuery(query, new String[]{distCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                RouteModel routeModel = new RouteModel();

                routeModel.setCompanyCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)).trim());
                routeModel.setDistributorCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                routeModel.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                routeModel.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                routeModel.setTempRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_TEMP_ROUTE_CODE)).trim());
                routeModel.setRouteType(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_TYPE)).trim());
                routeModel.setUploadFlag(cursor.getString(cursor.getColumnIndex(COLUMN_UPLOAD)).trim());

                routeList.add(routeModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public List<SalesmanRouteCoveragePlanModel> getAllRoutesForSalesman(SFADatabase sfaDatabase, String cmpCode, String salesmanCode) {
        List<SalesmanRouteCoveragePlanModel> routeList = new ArrayList<>();
        Cursor cursor;

        String query;
        query = "SELECT\n" +
                "   route.routeName,\n" +
                "   sales.routeCode, \n" +
                "   sales.salesmanCode \n" +
                "from\n" +
                "   m_salesman_route_mapping sales \n" +
                "   INNER JOIN\n" +
                "      m_route route \n" +
                "      on sales.routeCode = route.routeCode \n" +
                "where\n" +
                "   salesmanCode = ? \n" +
                "   AND sales.cmpCode = ?";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{salesmanCode, cmpCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                SalesmanRouteCoveragePlanModel salesmanRouteCoverageModel = new SalesmanRouteCoveragePlanModel();

                salesmanRouteCoverageModel.setCmpCode(cmpCode);
                salesmanRouteCoverageModel.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                salesmanRouteCoverageModel.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                salesmanRouteCoverageModel.setSalesmanCode(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_CODE)).trim());

                routeList.add(salesmanRouteCoverageModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public List<SalesmanRouteCoveragePlanModel> getAllRouteCoveragePlansForSalesman(SFADatabase sfaDatabase,
                                                                                    String cmpCode, String distrCode,
                                                                                    String salesmanCode) {
        List<SalesmanRouteCoveragePlanModel> routeList = new ArrayList<>();
        Cursor cursor;

        String query;
        query = "SELECT * from " + TABLE_SALESMAN_ROUTE_COVERAGE_PLAN + " where cmpCode = ? AND distrCode = ? AND salesmanCode = ?";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, salesmanCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                SalesmanRouteCoveragePlanModel salesmanRouteCoverageModel = new SalesmanRouteCoveragePlanModel();

                salesmanRouteCoverageModel.setCmpCode(cmpCode);
                salesmanRouteCoverageModel.setDistrCode(distrCode);
                salesmanRouteCoverageModel.setSalesmanCode(salesmanCode);
                salesmanRouteCoverageModel.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                salesmanRouteCoverageModel.setPlanType(cursor.getString(cursor.getColumnIndex(COLUMN_PLAN_TYPE)).trim());
                salesmanRouteCoverageModel.setStartDate(cursor.getString(cursor.getColumnIndex(COLUMN_START_DATE)).trim());
                salesmanRouteCoverageModel.setEndDate(cursor.getString(cursor.getColumnIndex(COLUMN_END_DATE)).trim());
                salesmanRouteCoverageModel.setDayName(cursor.getInt(cursor.getColumnIndex(COLUMN_DAY_NAME)));

                routeList.add(salesmanRouteCoverageModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public List<SalesmanRouteCoverageModel> getAllRouteCoveragesForSalesman(SFADatabase sfaDatabase, String cmpCode,
                                                                            String distrCode, String salesmanCode, String confirm) {
        List<SalesmanRouteCoverageModel> routeList = new ArrayList<>();
        Cursor cursor;

        String query = "select\n" +
                "coverage.cmpCode,\n" +
                "coverage.distrCode,\n" +
                "coverage.salesmanCode,\n" +
                "coverage.routeCode,\n" +
                "coverage.coverageDt,\n" +
                "coverage.confirm,\n" +
                "route.routeName\n" +
                "from\n" +
                "   t_salesmanRouteCoverage as coverage \n" +
                "   inner join\n" +
                "      m_route route \n" +
                "      on coverage.routeCode = route.routeCode \n" +
                "where\n" +
                "   salesmanCode = ? \n" +
                "   AND coverage.cmpCode = ?\n" +
                "   AND coverage.distrCode = ?\n" +
                "   AND coverage.confirm = ?";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{salesmanCode, cmpCode, distrCode, confirm});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                SalesmanRouteCoverageModel salesmanRouteCoverageModel = new SalesmanRouteCoverageModel();

                salesmanRouteCoverageModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)).trim());
                salesmanRouteCoverageModel.setDistrCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                salesmanRouteCoverageModel.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                salesmanRouteCoverageModel.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                salesmanRouteCoverageModel.setSalesmanCode(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_CODE)).trim());
                salesmanRouteCoverageModel.setCoverageDt(cursor.getString(cursor.getColumnIndex(COLUMN_COVERAGE_DT)).trim());
                salesmanRouteCoverageModel.setConfirm(cursor.getString(cursor.getColumnIndex(COLUMN_CONFIRM)).trim());

                routeList.add(salesmanRouteCoverageModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public boolean isAtleastOneCoverageAddedForSalesman(BaseDB baseDB, String salesmanCode) {
        Cursor cursor;
        String query = "SELECT * From " + TABLE_SALESMAN_ROUTE_COVERAGE_PLAN + " WHERE salesmanCode=?";
        cursor = baseDB.getDb().rawQuery(query, new String[]{salesmanCode});
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    public boolean insertSalesmanRouteCoveragePlans(SFADatabase sfaDatabase, List<SalesmanRouteCoveragePlanModel> salesmanRouteCoverageModels) {
        String sql = "INSERT INTO " + TABLE_SALESMAN_ROUTE_COVERAGE_PLAN + " VALUES (?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
        for (int i = 0; i < salesmanRouteCoverageModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, salesmanRouteCoverageModels.get(i).getCmpCode());
                statement.bindString(2, salesmanRouteCoverageModels.get(i).getDistrCode());
                statement.bindString(3, salesmanRouteCoverageModels.get(i).getSalesmanCode());
                statement.bindString(4, salesmanRouteCoverageModels.get(i).getRouteCode());
                statement.bindString(5, salesmanRouteCoverageModels.get(i).getPlanType());
                statement.bindString(6, salesmanRouteCoverageModels.get(i).getStartDate());
                statement.bindString(7, salesmanRouteCoverageModels.get(i).getEndDate());
                statement.bindString(8, String.valueOf(salesmanRouteCoverageModels.get(i).getDayName()));
                statement.bindString(9, "N");
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w("DUPLICATE_TAG", e);
                return false;
            }
        }
        return true;
    }

    public boolean insertSalesmanRouteCoveragePlansOnConflict(SFADatabase sfaDatabase, List<SalesmanRouteCoveragePlanModel> salesmanRouteCoverageModels) {
        for (int i = 0; i < salesmanRouteCoverageModels.size(); i++) {
            try {

                ContentValues values = new ContentValues();

                values.put(COLUMN_CMP_CODE, salesmanRouteCoverageModels.get(i).getCmpCode());
                values.put(COLUMN_DISTR_CODE, salesmanRouteCoverageModels.get(i).getDistrCode());
                values.put(COLUMN_SALESMAN_CODE, salesmanRouteCoverageModels.get(i).getSalesmanCode());
                values.put(COLUMN_ROUTE_CODE, salesmanRouteCoverageModels.get(i).getRouteCode());
                values.put(COLUMN_PLAN_TYPE, salesmanRouteCoverageModels.get(i).getPlanType());
                values.put(COLUMN_START_DATE, salesmanRouteCoverageModels.get(i).getStartDate());
                values.put(COLUMN_END_DATE, salesmanRouteCoverageModels.get(i).getEndDate());
                values.put(COLUMN_DAY_NAME, String.valueOf(salesmanRouteCoverageModels.get(i).getDayName()));
                values.put(UPLOAD_FLAG, "N");

                sfaDatabase.getDb().insertWithOnConflict(TABLE_SALESMAN_ROUTE_COVERAGE_PLAN, null,
                        values, SQLiteDatabase.CONFLICT_REPLACE);

            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w("DUPLICATE_TAG", e);
                return false;
            }
        }
        return true;
    }

    public boolean insertSalesmanRouteCoverages(SFADatabase sfaDatabase, List<SalesmanRouteCoverageModel> salesmanRouteCoverageModels) {
        String sql = "INSERT INTO " + TABLE_SALESMAN_ROUTE_COVERAGE + " VALUES (?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
        for (int i = 0; i < salesmanRouteCoverageModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, salesmanRouteCoverageModels.get(i).getCmpCode());
                statement.bindString(2, salesmanRouteCoverageModels.get(i).getDistrCode());
                statement.bindString(3, salesmanRouteCoverageModels.get(i).getSalesmanCode());
                statement.bindString(4, salesmanRouteCoverageModels.get(i).getRouteCode());
                statement.bindString(5, salesmanRouteCoverageModels.get(i).getCoverageDt());
                statement.bindString(6, salesmanRouteCoverageModels.get(i).getConfirm());
                statement.bindString(7, "N");
                statement.bindDouble(8, System.currentTimeMillis());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w("DUPLICATE_TAG", e);
                return false;
            }
        }
        return true;
    }

    public void updateConfirmInSalesmanRouteCoverages(SFADatabase sfaDatabase, List<SalesmanRouteCoverageModel> salesmanRouteCoverageModels) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONFIRM, "Y");

        String query = "cmpCode = ? and distrCode = ? and salesmanCode = ? and routeCode = ? and coverageDt = ?";

        for (SalesmanRouteCoverageModel salesmanRouteCoverageModel : salesmanRouteCoverageModels) {
            String[] updateArgs = {salesmanRouteCoverageModel.getCmpCode(), salesmanRouteCoverageModel.getDistrCode(),
                    salesmanRouteCoverageModel.getSalesmanCode(), salesmanRouteCoverageModel.getRouteCode(),
                    salesmanRouteCoverageModel.getCoverageDt()};

            sfaDatabase.getDb().update(TABLE_SALESMAN_ROUTE_COVERAGE, values, query, updateArgs);
        }
        sfaDatabase.closeDb();
    }

    public List<RouteModel> getRouteDataFromDB(SFADatabase sfaDatabase, String cmpCode, String distrCode, String salesmanCode) {
        List<RouteModel> routeList = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT route.routeName, sales.routeCode from " + TABLE_SALESMAN_ROUTE_MAPPING + " sales INNER JOIN " + TABLE_ROUTE + " route on sales.routeCode = route.routeCode  WHERE sales.cmpCode=? AND sales.distrCode=? AND sales.salesmanCode=? GROUP BY sales.routeCode";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, salesmanCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                RouteModel routeVO = new RouteModel();

                routeVO.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                routeVO.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());

                routeList.add(routeVO);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public List<RouteModel> getCustomerRouteDataFromDB(SFADatabase sfaDatabase, String customerCode, String salesmanCode) {
        List<RouteModel> routeList = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT mroute.routeCode, mroute.routeName from " + TABLE_CUSTOMER_ROUTE + " cusroute INNER JOIN " + TABLE_ROUTE + " mroute on cusroute.routeCode= mroute.routeCode LEFT JOIN " + TABLE_SALESMAN_ROUTE_MAPPING + " msalesroute on mroute.routeCode= msalesroute.routeCode WHERE cusroute.customerCode=? AND msalesroute.salesmanCode=? group by mroute.routeCode";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{customerCode, salesmanCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                RouteModel routeVO = new RouteModel();

                routeVO.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                routeVO.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());

                routeList.add(routeVO);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }

    public void insertRouteSequencingInDB(SFADatabase sfaDatabase, String cmpCode, String distrCode,
                                          String routeCode, String salesmanCode,
                                          List<RetailerVO> voList) {

        for (int i = 0; i < voList.size(); i++) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_REF_CODE, UUID.randomUUID().toString());
            values.put(COLUMN_CMP_CODE, cmpCode);
            values.put(COLUMN_DISTR_CODE, distrCode);
            values.put(COLUMN_SALESMAN_CODE, salesmanCode);
            values.put(COLUMN_ROUTE_CODE, routeCode);
            values.put(COLUMN_CUSTOMER_CODE, voList.get(i).getCustomerCode());
            values.put(COLUMN_CUSTOMER_NAME, voList.get(i).getCustomerName());
            values.put(COLUMN_COVERAGE_SEQUENCE, String.valueOf(i + 1));
            values.put(UPLOAD_FLAG, "N");
            sfaDatabase.getDb().insert(IDBColumns.TABLE_ROUTE_SEQUENCING, null, values);
        }
    }

    public void deleteRouteSeqById(SFADatabase sfaDatabase, String cmpCode, String distrCode, String routeCode, String salesmanCode, List<RetailerVO> voList) {
        for (RetailerVO retailerVO : voList) {

            String deleteOrderBooking = "cmpCode=? and distrCode=? and salesmanCode=? and routeCode=? and customerCode=? and uploadFlag=?";
            String[] deleteArgs = {cmpCode, distrCode, salesmanCode, routeCode, retailerVO.getCustomerCode(), "N"};

            sfaDatabase.getDb().delete(TABLE_ROUTE_SEQUENCING, deleteOrderBooking, deleteArgs);
            sfaDatabase.closeDb();
        }
    }

    public void deleteRouteCoveragePlanBasedOnSalesman(SFADatabase sfaDatabase, List<SalesmanRouteCoveragePlanModel> salesmanRouteCoverageModels) {
        for (SalesmanRouteCoveragePlanModel model : salesmanRouteCoverageModels) {
            sfaDatabase.getDb().execSQL("DELETE FROM " + TABLE_SALESMAN_ROUTE_COVERAGE_PLAN + " WHERE cmpCode ='" +
                    model.getCmpCode() + "' AND salesmanCode = '" + model.getSalesmanCode() + "'");
        }
        sfaDatabase.closeDb();
    }

    public void deleteRouteCoverageBasedOnSalesman(SFADatabase sfaDatabase, String cmpCode, String distrCode,
                                                   String salesmanCode) {
        sfaDatabase.getDb().execSQL("DELETE FROM " + TABLE_SALESMAN_ROUTE_COVERAGE + " WHERE cmpCode ='" +
                cmpCode + "' AND distrCode = '" + distrCode + "' AND salesmanCode = '" + salesmanCode + "'");
        sfaDatabase.closeDb();
    }

    public boolean isDayCoverageAlreadyExists(SFADatabase sfaDatabase, String cmpCode, String salesmanCode, String dayName) {
        Cursor cursor;
        String query = "SELECT * from " + TABLE_SALESMAN_ROUTE_COVERAGE + " WHERE cmpCode = ? AND salesmanCode = ? " +
                "AND dayName = ? AND confirm_status = ? AND uploadFlag = ?";
        cursor = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, salesmanCode, dayName, "Y", "N"});
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    public void updateRouteSequencingInDB(SFADatabase sfaDatabase, String cmpCode, String distrCode,
                                          String routeCode, List<RetailerVO> voList) {

        for (int i = 0; i < voList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_COVERAGE_SEQUENCE, String.valueOf(i + 1));
            values.put(COLUMN_UPLOAD, "N");

            String[] updateArgs = {cmpCode, distrCode, routeCode, voList.get(i).getCustomerCode()};

            sfaDatabase.getDb().update(TABLE_CUSTOMER_ROUTE, values, UPDATE_CHECK_CMP_DC_RC_CC, updateArgs);
            sfaDatabase.closeDb();
        }
    }

    public List<SalesmanRouteCoverageModel> getRouteCoverageForUpload(BaseDB baseDB) {
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        String distCode = preferences.readString(PREF_DISTRCODE);

        List<SalesmanRouteCoverageModel> salesmanRouteCoverageModels = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT * From " + TABLE_SALESMAN_ROUTE_COVERAGE + " WHERE cmpCode = ? AND distrCode=? " +
                "AND confirm = 'Y' AND uploadFlag = 'N'";
        cursor = baseDB.getDb().rawQuery(query, new String[]{cmpCode, distCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                SalesmanRouteCoverageModel model = new SalesmanRouteCoverageModel();

                model.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)).trim());
                model.setDistrCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                model.setSalesmanCode(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_CODE)).trim());
                model.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                model.setCoverageDt(cursor.getString(cursor.getColumnIndex(COLUMN_COVERAGE_DT)).trim());
                model.setConfirm(cursor.getString(cursor.getColumnIndex(COLUMN_CONFIRM)).trim());
                model.setModDt(String.valueOf(System.currentTimeMillis()));
                model.setUploadFlag(cursor.getString(cursor.getColumnIndex(UPLOAD_FLAG)).trim());

                salesmanRouteCoverageModels.add(model);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return salesmanRouteCoverageModels;
    }

    public List<RouteModel> getRouteList(SFADatabase sfaDatabase, String distrCode) {
        List<RouteModel> routeList = new ArrayList<>();
        Cursor cursor;
        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        String query = "SELECT * from " + TABLE_ROUTE + " where distrCode = ? AND routeCode not like '%CROU%' GROUP BY routeCode ORDER BY routeName COLLATE nocase ASC";
        cursor = database.rawQuery(query, new String[]{distrCode});

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                RouteModel routeVO = new RouteModel();

                routeVO.setRouteName(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_NAME)).trim());
                routeVO.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
                routeList.add(routeVO);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return routeList;
    }
}
