/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.db.query;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.location.Location;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.db.BaseDB;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.AddressModel;
import com.botree.retailerssfa.models.CodeGeneratorModel;
import com.botree.retailerssfa.models.CustomerApprovalModel;
import com.botree.retailerssfa.models.CustomerModel;
import com.botree.retailerssfa.models.FeedbackModel;
import com.botree.retailerssfa.models.GeoHierModel;
import com.botree.retailerssfa.models.LoginModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.ParentMapModel;
import com.botree.retailerssfa.models.PendingBillVO;
import com.botree.retailerssfa.models.PendingVO;
import com.botree.retailerssfa.models.RetailerVO;
import com.botree.retailerssfa.models.RouteModel;
import com.botree.retailerssfa.models.StockOnHandModel;
import com.botree.retailerssfa.models.TaxMasterModel;
import com.botree.retailerssfa.models.TaxModel;
import com.botree.retailerssfa.models.TimeCaptureModel;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.db.BaseDB.checkString;
import static com.botree.retailerssfa.db.query.IDBColumns.*;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.QUERY_RETAILER_BY_CUSTOMER_CODE;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.QUERY_RETAILER_BY_RETLR_CODE;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.QUERY_SELECT_CHANNEL_BY_CODE;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.QUERY_SELECT_CUSTOMER;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.QUERY_SELECT_CUSTOMER_BANK_COUNT;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.QUERY_SELECT_CUSTOMER_DET;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.QUERY_SELECT_GEOGRAPHY;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.QUERY_SELECT_GEOGRAPHY_PATH;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.QUERY_SELECT_PARENT;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.QUERY_SELECT_PENDING_BILLS;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.getQueryTaxCalPercent;
import static com.botree.retailerssfa.db.query.RetailerDetailQueryHelper.getQueryVanSalesDaySummary;
import static com.botree.retailerssfa.support.Globals.SCREEN_NAME_CUSTOMER_BANK;
import static com.botree.retailerssfa.support.Globals.SCREEN_NAME_CUTOMER_SHIP_ADDRESS;


public class RetailerDetailHelper {
    private static final String TAG = RetailerDetailHelper.class.getSimpleName();
    private SQLiteDatabase db;

    public RetailerDetailHelper() {

    }


    private String getMobileNo(Cursor cursorPending) {
        if ((!cursorPending.getString(cursorPending.getColumnIndex("rtrMobileNo")).trim().equalsIgnoreCase("null"))) {
            return cursorPending.getString(cursorPending.getColumnIndex("rtrMobileNo")).trim();
        } else {
            return "";
        }
    }

    public PendingVO getRetailerByRetailerCode(SFADatabase sfaDatabase, String retailerCode) {

        sfaDatabase.openWritableDb();

        PendingVO pendingData1 = new PendingVO();

        Cursor cursorPending = sfaDatabase.getDb().rawQuery(QUERY_RETAILER_BY_RETLR_CODE, new String[]{retailerCode});
        if (isCursorTrue(cursorPending)) {

            pendingData1.setStrCustomerCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
            pendingData1.setStrRetailerName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_NAME)).trim());
            pendingData1.setStrCreditDays(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CREDIT_DAYS)).trim());
            pendingData1.setRetailerAddress(cursorPending.getString(cursorPending.getColumnIndex("retailerAddr1")).trim());
            pendingData1.setGstTinNo(cursorPending.getString(cursorPending.getColumnIndex("gstNo")).trim());
            pendingData1.setPanNo(cursorPending.getString(cursorPending.getColumnIndex("panNo")).trim());
            pendingData1.setGstType(cursorPending.getString(cursorPending.getColumnIndex("gstType")).trim());
            pendingData1.setIsRetailerByPassGeo(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_IS_BY_PASS_GEO)).trim());
            pendingData1.setGeoUnlockCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_UNLOCK_CODE)).trim());
            if ((cursorPending.getLong(cursorPending.getColumnIndex(COLUMN_LICENSE_EXP_DT))) > 0)
                pendingData1.setLicenseExpDate(DateUtil.covertLongValueIntoDate(cursorPending.getLong(cursorPending.getColumnIndex(COLUMN_LICENSE_EXP_DT))));

            String chCode = getChannelNameBasedonCode(sfaDatabase, cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
            pendingData1.setChannelCode(chCode);

            pendingData1.setStrMobileNo(getMobileNo(cursorPending));
        }
        closeCursor(cursorPending);
        sfaDatabase.closeDb();
        return pendingData1;
    }

    private String getChannelNameBasedonCode(SFADatabase sfaDatabase, String channelcode) {

        String chName = "0";

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CHANNEL_BY_CODE, new String[]{channelcode});

        if (isCursorTrue(c)) {
            chName = c.getString(c.getColumnIndex("channelName"));
        }
        closeCursor(c);
        sfaDatabase.closeDb();
        return chName;
    }

    public List<PendingBillVO> getPendingBills(SFADatabase sfaDatabase, String distrcode, String salesmancode, String retrCode) {

        List<PendingBillVO> pendingBillList = new ArrayList<>();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_PENDING_BILLS, new String[]{distrcode, salesmancode, retrCode});
        if (isCursorTrue(c)) {
            do {
                PendingBillVO pendingBillVO = new PendingBillVO();
                pendingBillVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                pendingBillVO.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)));
                pendingBillVO.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                pendingBillVO.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NO)));
                pendingBillVO.setInvoiceDate(c.getString(c.getColumnIndex("invoiceDate")));
                pendingBillVO.setPendingAmount(c.getDouble(c.getColumnIndex(COLUMN_PENDING_AMOUNT)));
                pendingBillList.add(pendingBillVO);
            } while (c.moveToNext());
        }
        closeCursor(c);
        sfaDatabase.closeDb();

        return pendingBillList;
    }


    private void closeCursor(Cursor c) {
        if (c != null) {
            c.close();
        }
    }

    public List<PendingVO> getAllOutletsBasedRoute(SFADatabase sfaDatabase, String distrcode, String salesmancode,
                                                   String routeCode, String lat, String lng, String retailerCode) {

        List<PendingVO> pendingList = new ArrayList<>();
        sfaDatabase.openWritableDb();
        String query;
        Cursor cursorPending;
        if (retailerCode != null && !retailerCode.isEmpty()) {

            query = "SELECT r.routeCode, r.customerCode, r.customerName, r.retailerAddr1, "
                    + "r.cmpCode, r.channelCode, r.subChannelCode, r.groupCode, r.classCode, r.creditDays, "
                    + " r.latitude, r.longitude, r.rtrMobileNo FROM " + TABLE_RETAILER + " r WHERE r.distrCode=? "
                    + "AND r.salesmanCode= ? AND r.routeCode=? AND r.customerCode=? ORDER BY  CAST(seqNo AS INTEGER),customerName COLLATE NOCASE ASC";

            cursorPending = sfaDatabase.getDb().rawQuery(query, new String[]{distrcode, salesmancode, routeCode, retailerCode});

        } else {
            query = "SELECT r.routeCode, r.customerCode, r.customerName, r.retailerAddr1, "
                    + "r.cmpCode, r.channelCode, r.subChannelCode, r.groupCode, r.classCode, r.creditDays, "
                    + "rv.isVisit, rv.orderVisit,rv.returnVisit, "
                    + "rv.stockVisit, rv.collectionVisit, rv.surveyVisit, rv.syncVisit, r.latitude, r.longitude, r.rtrMobileNo FROM " + TABLE_RETAILER + " r INNER JOIN " + TABLE_RETAILER_VISIT + " rv "
                    + "ON (r.distrCode = rv.distrCode AND r.salesmanCode = rv.salesmanCode AND r.routeCode = rv.routeCode "
                    + "AND r.customerCode = rv.customerCode) WHERE r.distrCode=? "
                    + "AND r.salesmanCode= ? AND r.routeCode=? ORDER BY  CAST(seqNo AS INTEGER),customerName COLLATE NOCASE ASC";
            cursorPending = sfaDatabase.getDb().rawQuery(query, new String[]{distrcode, salesmancode, routeCode});
        }


        if (isCursorTrue(cursorPending)) {
            do {
                if ((retailerCode != null && !retailerCode.isEmpty() && !cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LATITUDE)).trim().isEmpty()
                        && cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LATITUDE)).length() != 0) ||
                        ((cursorPending.getString(cursorPending.getColumnIndex(COLUMN_IS_VISIT)).trim().equals("P") ||
                                cursorPending.getString(cursorPending.getColumnIndex(COLUMN_IS_VISIT)).trim().equals("L"))
                                && !cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LATITUDE)).trim().isEmpty()
                                && cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LATITUDE)).length() != 0)) {
                    pendingList.add(getPendingDetails(lat, lng, cursorPending));
                }


            } while (cursorPending.moveToNext());
        }
        closeCursor(cursorPending);
        sfaDatabase.closeDb();
        return pendingList;
    }

    private PendingVO getPendingDetails(String lat, String lng, Cursor cursorPending) {
        PendingVO pendingData1 = new PendingVO();
        pendingData1.setStrRouteCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_ROUTE_CODE)).trim());
        pendingData1.setStrCustomerCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
        pendingData1.setStrRetailerName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_NAME)).trim());
        pendingData1.setLatitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LATITUDE)));
        pendingData1.setLongitude(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_LONGITUDE)));

        double latitude = cursorPending.getDouble(cursorPending.getColumnIndex(COLUMN_LATITUDE));
        double longitude = cursorPending.getDouble(cursorPending.getColumnIndex(COLUMN_LONGITUDE));

        pendingData1.setLatitude(String.valueOf(latitude));
        pendingData1.setLongitude(String.valueOf(longitude));

        Location loc1 = new Location("");
        loc1.setLatitude(Double.valueOf(lat));
        loc1.setLongitude(Double.valueOf(lng));

        Location loc2 = new Location("");
        loc2.setLatitude(latitude);
        loc2.setLongitude(longitude);

        float distanceInMeters = loc1.distanceTo(loc2) / 1000;

        pendingData1.setAmount((double) distanceInMeters);

        return pendingData1;
    }

    private boolean isCursorTrue(Cursor cursorPending) {
        return cursorPending != null && cursorPending.getCount() > 0 && cursorPending.moveToFirst();
    }


    public List<RetailerVO> getRetailerTrackerList(SFADatabase sfaDatabase, String distCode, String salesmanCode) {


        List<RetailerVO> retailerVOList = new ArrayList<>();

        String query = "SELECT * FROM '" + TABLE_RETAILER_VISIT + "' WHERE distrCode =? " +
                "AND salesmanCode =? AND (orderVisit='Y' OR ReturnVisit='Y' OR stockVisit='Y' OR collectionVisit='Y' OR " +
                " SurveyVisit='Y' OR BillVisit='Y' OR isNewOutlet = 'Y')"; /*AND syncVisit = 'N'*/

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{distCode, salesmanCode});
        if (isCursorTrue(c)) {
            do {
                RetailerVO feedbackModel = new RetailerVO();
                feedbackModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                feedbackModel.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)));
                feedbackModel.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                feedbackModel.setRouteCode(c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE)));

                feedbackModel.setCoverageDate(c.getString(c.getColumnIndex(COLUMN_COVERAGE_DATE)));
                feedbackModel.setTodayBeat(c.getString(c.getColumnIndex(COLUMN_IS_TODAY_BEAT)));

                feedbackModel.setOrderVisit(c.getString(c.getColumnIndex(COLUMN_ORDER_VISIT)));
                feedbackModel.setReturnVisit(c.getString(c.getColumnIndex(COLUMN_RETURN_VISIT)));
                feedbackModel.setStockVisit(c.getString(c.getColumnIndex(COLUMN_STOCK_VISIT)));
                feedbackModel.setCollectionVisit(c.getString(c.getColumnIndex(COLUMN_COLLECTION_VISIT)));
                feedbackModel.setSurveyVisit(c.getString(c.getColumnIndex(COLUMN_SURVEY_VISIT)));
                feedbackModel.setSyncVisit(c.getString(c.getColumnIndex(COLUMN_SYNC_VISIT)));
                feedbackModel.setBillVisit(c.getString(c.getColumnIndex(COLUMN_BILL_VISIT)));

                feedbackModel.setLatitude(c.getString(c.getColumnIndex(COLUMN_LATITUDE)));
                feedbackModel.setLongitude(c.getString(c.getColumnIndex(COLUMN_LONGITUDE)));
                feedbackModel.setStartTime(c.getLong(c.getColumnIndex(COLUMN_START_TIME)));
                feedbackModel.setEndTime(c.getLong(c.getColumnIndex(COLUMN_END_TIME)));

                feedbackModel.setDistance(c.getString(c.getColumnIndex(COLUMN_OUT_DISTANCE)));
                feedbackModel.setRetrLatitude(c.getString(c.getColumnIndex(COLUMN_RETLR_LATITUDE)));
                feedbackModel.setRetrLongitude(c.getString(c.getColumnIndex(COLUMN_RETLR_LONGITUDE)));
                feedbackModel.setIsNewOutlet(c.getString(c.getColumnIndex(COLUMN_IS_NEWOUTLET)));

                retailerVOList.add(feedbackModel);
            } while (c.moveToNext());

        }
        closeCursor(c);

        return retailerVOList;
    }


    public boolean isOutletDataSync(SFADatabase sfaDatabase, String strCustomerCode) {

        String query = "SELECT * FROM " + TABLE_NEW_RETAILER + " WHERE customerCode=? and upload =?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{strCustomerCode, "N"});

        if (c != null && c.getCount() > 0) {
            return true;
        }
        closeCursor(c);
        return false;
    }

    public boolean isSharedCustomer(SFADatabase sfaDatabase, String strCustomerCode) {

        String query = "SELECT * FROM " + TABLE_NEW_RETAILER + " WHERE customerCode=? and customerSharedFlag =?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{strCustomerCode, "Y"});

        if (c != null && c.getCount() > 0) {
            return true;
        }
        closeCursor(c);
        return false;
    }

    public String getRetaielrStateCode(SFADatabase baseDB, String cmpCode, String distCode, String retailerCode) {
        baseDB.openWritableDb();
        String query = "SELECT " + GST_STATE_CODE + " FROM " + TABLE_CUSTOMER_SHIP_ADDRESS + " WHERE " +
                COLUMN_CMP_CODE + "=? AND " + COLUMN_DISTR_CODE + " =? AND " + COLUMN_CUSTOMER_CODE + " =? ";
        SQLiteDatabase database = baseDB.getReadableDatabase();
        Cursor cm = database.rawQuery(query, new String[]{cmpCode, distCode, retailerCode});
        if (isCursorTrue(cm)) {
            String value = cm.getString(cm.getColumnIndex(GST_STATE_CODE));
            cm.close();
            return value;
        }
        closeCursor(cm);

        return "";
    }

    public String getNewRetaielrStateCode(SFADatabase baseDB, String distrCode, String salesmanCode, String retailerCode) {
        baseDB.openWritableDb();
        String query = "SELECT " + GST_STATE_CODE + " FROM " + TABLE_NEW_RETAILER + " WHERE " +
                COLUMN_DISTR_CODE + "=? AND " + COLUMN_SALESMAN_CODE + " =? AND " + COLUMN_CUSTOMER_CODE + " =? ";
        SQLiteDatabase database = baseDB.getReadableDatabase();
        Cursor cm = database.rawQuery(query, new String[]{distrCode, salesmanCode, retailerCode});
        if (isCursorTrue(cm)) {
            String value = cm.getString(cm.getColumnIndex(GST_STATE_CODE));
            cm.close();
            return value;
        }
        closeCursor(cm);

        return "";
    }

    public Boolean isDistUnionTeritory(SFADatabase sfaDatabase, String distStateCode) {

        String query = "select * from " + TABLE_GST_STATE_MASTER + " where " + GST_STATE_CODE + " =? AND isUnionTerritory='Y';";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{distStateCode});

        if (c != null && c.getCount() > 0) {
            return true;
        }
        closeCursor(c);
        return false;
    }

    public List<TaxModel> getTaxCalPercentForUT(SFADatabase baseDB, String distCode, String prodCode, String retailerStateCode,
                                                boolean isUnionTerritory) {
        List<TaxModel> taxModelList = new ArrayList<>();
        baseDB.openWritableDb();
        String query;
        query = getQueryTaxCalPercent(prodCode, isUnionTerritory);

        Cursor c = baseDB.getDb().rawQuery(query, new String[]{distCode, String.valueOf(retailerStateCode)});
        if (isCursorTrue(c)) {
            do {
                TaxModel taxModel = new TaxModel();
                taxModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                taxModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                taxModel.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                taxModel.setTaxState(c.getString(c.getColumnIndex(TAX_STATE)));
                taxModel.setTaxCode(c.getString(c.getColumnIndex(TAX_CODE)));
                taxModel.setTaxName(c.getString(c.getColumnIndex(TAX_NAME)));
                taxModel.setTaxEffectiveFrom(c.getString(c.getColumnIndex(TAX_EFFECTIVE_FROM)));
                taxModel.setSchemeReduce(c.getString(c.getColumnIndex(SCHEME_REDUCE)));
                taxModel.setInputTaxPerc(c.getDouble(c.getColumnIndex(INPUT_TAX_PERC)));
                taxModel.setOutputTaxPerc(c.getDouble(c.getColumnIndex(OUTPUT_TAX_PERC)));

                taxModelList.add(taxModel);
            } while (c.moveToNext());

        }
        closeCursor(c);

        return taxModelList;
    }

    public List<TaxModel> getTaxCalPercentStates(SFADatabase baseDB, String distCode, String prodCode, String retailerStateCode) {
        List<TaxModel> taxModelList = new ArrayList<>();
        baseDB.openWritableDb();
        String query;
        query = "SELECT * FROM '" + TABLE_PRODUCT_TAX_MASTER + "' WHERE " + COLUMN_CMP_CODE + " =? " +
                "AND " + COLUMN_PROD_CODE + " like  ( '%/' || '" + prodCode + "' ||  '/%' )  AND " + TAX_STATE + "=? " +
                "AND taxName = 'IGST'  GROUP BY " + TAX_NAME;


        Cursor c = baseDB.getDb().rawQuery(query, new String[]{distCode, String.valueOf(retailerStateCode)});
        if (isCursorTrue(c)) {
            do {
                TaxModel taxModel = new TaxModel();
                taxModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                taxModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                taxModel.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                taxModel.setTaxState(c.getString(c.getColumnIndex(TAX_STATE)));
                taxModel.setTaxCode(c.getString(c.getColumnIndex(TAX_CODE)));
                taxModel.setTaxName(c.getString(c.getColumnIndex(TAX_NAME)));
                taxModel.setTaxEffectiveFrom(c.getString(c.getColumnIndex(TAX_EFFECTIVE_FROM)));
                taxModel.setSchemeReduce(c.getString(c.getColumnIndex(SCHEME_REDUCE)));
                taxModel.setInputTaxPerc(c.getDouble(c.getColumnIndex(INPUT_TAX_PERC)));
                taxModel.setOutputTaxPerc(c.getDouble(c.getColumnIndex(OUTPUT_TAX_PERC)));

                taxModelList.add(taxModel);
            } while (c.moveToNext());
        }
        closeCursor(c);

        return taxModelList;
    }

    public List<TaxMasterModel> getPOTaxCalPercentStates(SFADatabase baseDB, String cmpCode, String prodCode, String retailerStateCode) {
        List<TaxMasterModel> taxModelList = new ArrayList<>();
        baseDB.openWritableDb();
        String query;
        query = "SELECT * FROM '" + TABLE_PRODUCT_TAX_MASTER + "' WHERE " + COLUMN_CMP_CODE + " =? " +
                "AND " + COLUMN_PROD_CODE + "=? " + "  AND " + COLUMN_TAX_STATE_CODE + "=? ";

        Cursor c = baseDB.getDb().rawQuery(query, new String[]{cmpCode, prodCode, String.valueOf(retailerStateCode)});
        if (isCursorTrue(c)) {
            do {
                TaxMasterModel taxModel = new TaxMasterModel();
                taxModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                taxModel.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                taxModel.setTaxStateCode(c.getString(c.getColumnIndex(COLUMN_TAX_STATE_CODE)));
                taxModel.setCgst(c.getFloat(c.getColumnIndex(COLUMN_CGST)));
                taxModel.setSgst(c.getFloat(c.getColumnIndex(COLUMN_SGST)));
                taxModel.setSgst(c.getFloat(c.getColumnIndex(COLUMN_SGST)));
                taxModel.setIgst(c.getFloat(c.getColumnIndex(COLUMN_IGST)));
                taxModel.setCess(c.getFloat(c.getColumnIndex(COLUMN_CESS)));

                taxModelList.add(taxModel);
            } while (c.moveToNext());
        }
        closeCursor(c);

        return taxModelList;
    }

    public List<TimeCaptureModel> fetchSalesmanLoginTime(SFADatabase sfaDatabase, String cmpCode, String userCode) {
        List<TimeCaptureModel> modelList = new ArrayList<>();

//        String query = "SELECT * FROM '" + TABLE_LOGIN_TIME_CAPTURE + "' WHERE cmpCode = ? and user_Code =? " +
//                " AND upload='N'";
        String query = "SELECT *\n" +
                "FROM c_loginTimeCapture logTime\n" +
                "INNER JOIN c_login login ON logTime.user_code = login.loginCode\n" +
                "WHERE logTime.user_Code =?\n" +
                "  AND logTime.upload='N'";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{ userCode});
        if (isCursorTrue(c)) {
            do {
                TimeCaptureModel model = new TimeCaptureModel();
                model.setUserCode(c.getString(c.getColumnIndex(USER_CODE)));
                model.setUserType(c.getString(c.getColumnIndex(USER_TYPE)));
                model.setMode(c.getString(c.getColumnIndex(COLUMN_MODE)));
                model.setProcessName(c.getString(c.getColumnIndex(COLUMN_PROCESS)));
                model.setSyncDate(c.getString(c.getColumnIndex(COLUMN_SYNC_DATE)));
                model.setLatitude(c.getString(c.getColumnIndex(COLUMN_LATITUDE)));
                model.setLongitude(c.getString(c.getColumnIndex(COLUMN_LONGITUDE)));
                model.setStatus(c.getString(c.getColumnIndex(COLUMN_STATUS)));
                model.setSyncTime(c.getString(c.getColumnIndex(COLUMN_SYNC_TIME)));
                model.setUserName(c.getString(c.getColumnIndex(COLUMN_USER_NAME)));
                model.setSyncEndTime(c.getString(c.getColumnIndex(COLUMN_SYNC_END_TIME)));
                model.setUpload("N");

                modelList.add(model);
            } while (c.moveToNext());
        }
        closeCursor(c);

        return modelList;
    }


    public String getStateNameByCode(SFADatabase sfaDatabase, String stateCode) {
        String distrStateName = "";
        String query = "SELECT * FROM " + TABLE_GST_STATE_MASTER + " where " + GST_STATE_CODE + " =?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{stateCode});
        if (isCursorTrue(c)) {
            do {
                distrStateName = c.getString(c.getColumnIndex(GST_STATE_NAME));
            } while (c.moveToNext());

        }
        closeCursor(c);

        return distrStateName;
    }


    public String isCustomerRegistered(SFADatabase sfaDatabase, String customerCode) {
        String customerType = "";
        String query = "select customerType from t_customer where customerCode = ?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{customerCode});
        if (isCursorTrue(c)) {
            do {
                customerType = c.getString(c.getColumnIndex(COLUMN_CUSTOMER_TYPE));
            } while (c.moveToNext());

        }
        closeCursor(c);

        return customerType;
    }


    public String getRouteCodeFromTempRouteCode(SFADatabase sfaDatabase, String tempRouteCode) {
        String routeCode = "";
        String query = "select routeCode from m_temp_route where tempRouteCode = ?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{tempRouteCode});
        if (isCursorTrue(c)) {
            do {
                routeCode = c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE));
            } while (c.moveToNext());

        }
        closeCursor(c);

        return routeCode;
    }

    public String getCustomerCodeFromTempCustomerCode(SFADatabase sfaDatabase, String tempRouteCode) {
        String customerCode = "";
        String query = "select customerCode from m_temp_customer where tempCustomerCode = ?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{tempRouteCode});
        if (isCursorTrue(c)) {
            do {
                customerCode = c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE));
            } while (c.moveToNext());

        }
        closeCursor(c);

        return customerCode;
    }

    public String getMaxCodeValueUsed(SFADatabase sfaDatabase, String tableName, String columnName) {
        String code = "";
        String query = "select " + columnName + " from " + tableName + " order by " + columnName + " desc limit 1";

        Cursor c = sfaDatabase.getDb().rawQuery(query, null);
        if (isCursorTrue(c)) {
            do {
                code = c.getString(c.getColumnIndex(columnName));
            } while (c.moveToNext());

        }
        closeCursor(c);

        return code;
    }


    public void insertBankDetail(SFADatabase sfaDatabase, RetailerVO retailerList, String cmpCode, String distCode, String customerCode) {
        String customerBankID = "";
        if (retailerList.getBankID().isEmpty()) {
            String suffixYy = sfaDatabase.getSuffixYyForScreen(SCREEN_NAME_CUSTOMER_BANK);
            String code = AppUtils.generateCode(sfaDatabase, SCREEN_NAME_CUSTOMER_BANK);
            String prefix = sfaDatabase.getPrefixForScreen(SCREEN_NAME_CUSTOMER_BANK);
            customerBankID = prefix + suffixYy + code;
            int value = Integer.parseInt(code) + 1;
            Log.e(TAG, "CustomerBank : " + customerBankID);

            CodeGeneratorModel codeGeneratorModel = new CodeGeneratorModel(cmpCode,
                    distCode,
                    SCREEN_NAME_CUSTOMER_BANK, customerBankID, suffixYy,
                    value);

            if (value == 1) {
                CodeGeneratorQueryHelper.insertCode(sfaDatabase, codeGeneratorModel);
            } else {
                CodeGeneratorQueryHelper.updateCode(sfaDatabase, codeGeneratorModel);
            }
        } else {
            customerBankID = retailerList.getBankID();
        }


        String sql = "INSERT INTO " + TABLE_CUSTOMER_BANK + " VALUES (?,?,?,?,?,?,?,?,?,?,?);";

        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, cmpCode);
        statement.bindString(2, distCode);
        statement.bindString(3, customerCode);
        statement.bindString(4, customerBankID);
        statement.bindString(5, retailerList.getBankName());
        statement.bindString(6, retailerList.getBranchName());
        statement.bindString(7, retailerList.getIfscCode());
        statement.bindString(8, retailerList.getAccountType());
        statement.bindString(9, retailerList.getAccountNo());
        statement.bindString(10, DateUtil.getCurrentYearMonthDate());
        statement.bindString(11, "N");
        statement.execute();

    }

    public void insertCustomerDetail(SFADatabase sfaDatabase, RetailerVO retailerList, String cmpCode, String distCode, String customerCode) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, cmpCode);
        statement.bindString(2, distCode);
        statement.bindString(3, customerCode);
        statement.bindString(4, customerCode);
        statement.bindString(5, retailerList.getCustomerName());
        statement.bindString(6, retailerList.getPostalCode());
        statement.bindString(7, retailerList.getPhoneNo());
        statement.bindString(8, retailerList.getMobile());
        statement.bindString(9, retailerList.getContactPerson());
        statement.bindString(10, retailerList.getEmail());
        statement.bindString(11, String.valueOf(retailerList.getSpinDayOffCount()));
        statement.bindString(12, retailerList.getRetailerStatus());
        statement.bindString(13, retailerList.getFfsaLcNo());
        statement.bindString(14, retailerList.getDrugLcNo());
        statement.bindString(15, retailerList.getExpiryDateTimeStamp());
        if (retailerList.getCrBills().equals("")) {
            statement.bindString(16, "0");
        } else {
            statement.bindString(16, retailerList.getCrBills());
        }
        if (retailerList.getCrDays().equals("")) {
            statement.bindString(17, "0");
        } else {
            statement.bindString(17, retailerList.getCrDays());
        }
        if (retailerList.getCrLimit().equals("")) {
            statement.bindString(18, "0.0");
        } else {
            statement.bindString(18, retailerList.getCrLimit());
        }
        if (retailerList.getCashDisc().equals("")) {
            statement.bindString(19, "0.0");
        } else {
            statement.bindString(19, retailerList.getCashDisc());
        }
        statement.bindString(20, retailerList.getChannelCode());
        statement.bindString(21, retailerList.getSubChannelCode());
        statement.bindString(22, retailerList.getGroupCode());
        statement.bindString(23, retailerList.getClassCode());

        if (retailerList.getRelationStatus().equalsIgnoreCase("Independent")) {
            statement.bindString(24, "I");
            statement.bindString(25, "");
        } else if (retailerList.getRelationStatus().equalsIgnoreCase("Parent")) {
            statement.bindString(24, "P");
            statement.bindString(25, "");
        } else if (retailerList.getRelationStatus().equalsIgnoreCase("Child")) {
            statement.bindString(24, "C");
            statement.bindString(25, retailerList.getParentMap());
        }

        statement.bindString(26, retailerList.getLatitude());
        statement.bindString(27, retailerList.getLongitude());
        statement.bindString(28, retailerList.getGstType());
        statement.bindString(29, retailerList.getGstNo());
        statement.bindString(30, retailerList.getPanNo());
        statement.bindString(31, retailerList.getApprovalStatus());
        statement.bindString(32, DateUtil.getCurrentYearMonthDate());
        statement.bindString(33, "N");
        statement.execute();
    }

    public void insertCustomerShipAddrDetail(SFADatabase sfaDatabase, List<AddressModel> shippingAddressList, String cmpCode, String distCode, String customerCode) {

        String sql = "INSERT INTO " + TABLE_CUSTOMER_SHIP_ADDRESS + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        String customerShipCode = "";
        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
        for (int i = 0; i < shippingAddressList.size(); i++) {
            if (shippingAddressList.get(i).getShipCode().isEmpty()) {
                String suffixYy = sfaDatabase.getSuffixYyForScreen(SCREEN_NAME_CUTOMER_SHIP_ADDRESS);
                String code = AppUtils.generateCode(sfaDatabase, SCREEN_NAME_CUTOMER_SHIP_ADDRESS);
                String prefix = sfaDatabase.getPrefixForScreen(SCREEN_NAME_CUTOMER_SHIP_ADDRESS);
                customerShipCode = prefix + suffixYy + code;
                int value = Integer.parseInt(code) + 1;
                CodeGeneratorModel codeGeneratorModel = new CodeGeneratorModel(cmpCode,
                        distCode,
                        SCREEN_NAME_CUTOMER_SHIP_ADDRESS, customerShipCode, suffixYy,
                        value);

                if (value == 1) {
                    CodeGeneratorQueryHelper.insertCode(sfaDatabase, codeGeneratorModel);
                } else {
                    CodeGeneratorQueryHelper.updateCode(sfaDatabase, codeGeneratorModel);
                }
            } else {
                customerShipCode = shippingAddressList.get(i).getShipCode();
            }

            try {
                statement.clearBindings();
                statement.bindString(1, cmpCode);
                statement.bindString(2, distCode);
                if (shippingAddressList.get(i).getCustomerCode().isEmpty()) {
                    statement.bindString(3, customerCode);
                } else {
                    statement.bindString(3, shippingAddressList.get(i).getCustomerCode());
                }
                statement.bindString(4, customerShipCode);
                statement.bindString(5, shippingAddressList.get(i).getShippingAddress1());
                statement.bindString(6, shippingAddressList.get(i).getShippingAddress2());
                statement.bindString(7, shippingAddressList.get(i).getShippingAddress3());
                statement.bindString(8, shippingAddressList.get(i).getShipCityTown());
                statement.bindString(9, shippingAddressList.get(i).getShippingGeoHierPath());
                statement.bindString(10, shippingAddressList.get(i).getShippingState());
                statement.bindString(11, shippingAddressList.get(i).getShippingPhoneNumber());
                statement.bindString(12, shippingAddressList.get(i).getIsDefault());
                statement.bindString(13, DateUtil.getCurrentYearMonthDate());
                statement.bindString(14, "N");
                statement.execute();
            } catch (Exception e) {
                Log.e(TAG, "insertCustomerShipAddrDetail", e);
            }
        }
    }

    public RetailerVO getCustomerDetail(SFADatabase sfaDatabase, String cmpCode, String distCode, String customerCode) {
        RetailerVO retailerModel = new RetailerVO();
        sfaDatabase.openWritableDb();
        Cursor c;
        Cursor count = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CUSTOMER_BANK_COUNT, new String[]{cmpCode, distCode, customerCode});
        if (count.getCount() > 0) {

            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CUSTOMER, new String[]{cmpCode, distCode, customerCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {

                    retailerModel.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                    retailerModel.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                    retailerModel.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerModel.setCmpCustomerCode(checkString(c, COLUMN_COMPANY_CUSTOMER_CODE));
                    retailerModel.setCustomerName(checkString(c, COLUMN_CUSTOMER_NAME));
                    retailerModel.setPostalCode(checkString(c, COLUMN_PIN_CODE));
                    retailerModel.setPhoneNo(checkString(c, COLUMN_CUSTOMER_SHIP_PHONENO));
                    retailerModel.setMobile(checkString(c, COLUMN_MOBILE_NO));
                    retailerModel.setContactPerson(checkString(c, COLUMN_CONTACT_PERSON));
                    retailerModel.setEmail(checkString(c, COLUMN_EMAIL_ID));
                    retailerModel.setSpinDayOffCount(Integer.parseInt(checkString(c, COLUMN_DAY_OFF)));
                    retailerModel.setRetailerStatus(checkString(c, COLUMN_RETAILER_STATUS));
                    retailerModel.setFfsaLcNo(checkString(c, COLUMN_FFSAI_NO));
                    retailerModel.setDrugLcNo(checkString(c, COLUMN_DRUG_LIC_NO));
                    retailerModel.setExpiryDateTimeStamp(checkString(c, COLUMN_DRUG_LIC_EXPIRY_DATE));
                    retailerModel.setCrBills(checkString(c, COLUMN_CREDIT_BILLS));
                    retailerModel.setCrDays(checkString(c, COLUMN_CREDIT_DAYS));
                    retailerModel.setCrLimit(checkString(c, COLUMN_CREDIT_LIMIT));
                    retailerModel.setCashDisc(checkString(c, COLUMN_CASH_DISC_PERC));
                    retailerModel.setChannelCode(checkString(c, COLUMN_CHANNEL_CODE));
                    retailerModel.setSubChannelCode(checkString(c, COLUMN_SUB_CHANNEL_CODE));
                    retailerModel.setGroupCode(checkString(c, COLUMN_GROUP_CODE));
                    retailerModel.setClassCode(checkString(c, COLUMN_CLASS_CODE));
                    retailerModel.setRelationStatus(checkString(c, COLUMN_STORE_TYPE));
                    retailerModel.setParentMap(checkString(c, COLUMN_PARENT_CUSTOMER_CODE));
                    retailerModel.setLatitude(checkString(c, COLUMN_LATITUDE));
                    retailerModel.setLongitude(checkString(c, COLUMN_LONGITUDE));
                    retailerModel.setGstType(checkString(c, COLUMN_CUSTOMER_TYPE));
                    retailerModel.setGstNo(checkString(c, GSTNO));
                    retailerModel.setPanNo(checkString(c, COLUMN_PAN_NO));
                    retailerModel.setApprovalStatus(checkString(c, COLUMN_APPROVAL_STATUS));

                    retailerModel.setBankName(checkString(c, COLUMN_BANK_NAME));
                    retailerModel.setBranchName(checkString(c, COLUMN_BANK_BRANCH_NAME));
                    retailerModel.setIfscCode(checkString(c, COLUMN_IFSC_CODE));
                    retailerModel.setAccountType(checkString(c, COLUMN_ACC_TYPE));
                    retailerModel.setAccountNo(checkString(c, COLUMN_ACC_NUMBER));


                } while (c.moveToNext());
            }

        } else {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CUSTOMER_DET, new String[]{cmpCode, distCode, customerCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {

                    retailerModel.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                    retailerModel.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                    retailerModel.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerModel.setCmpCustomerCode(checkString(c, COLUMN_COMPANY_CUSTOMER_CODE));
                    retailerModel.setCustomerName(checkString(c, COLUMN_CUSTOMER_NAME));
                    retailerModel.setPostalCode(checkString(c, COLUMN_PIN_CODE));
                    retailerModel.setPhoneNo(checkString(c, COLUMN_CUSTOMER_SHIP_PHONENO));
                    retailerModel.setMobile(checkString(c, COLUMN_MOBILE_NO));
                    retailerModel.setContactPerson(checkString(c, COLUMN_CONTACT_PERSON));
                    retailerModel.setEmail(checkString(c, COLUMN_EMAIL_ID));
                    retailerModel.setSpinDayOffCount(Integer.parseInt(checkString(c, COLUMN_DAY_OFF)));
                    retailerModel.setRetailerStatus(checkString(c, COLUMN_RETAILER_STATUS));
                    retailerModel.setFfsaLcNo(checkString(c, COLUMN_FFSAI_NO));
                    retailerModel.setDrugLcNo(checkString(c, COLUMN_DRUG_LIC_NO));
                    retailerModel.setExpiryDateTimeStamp(checkString(c, COLUMN_DRUG_LIC_EXPIRY_DATE));
                    retailerModel.setCrBills(checkString(c, COLUMN_CREDIT_BILLS));
                    retailerModel.setCrDays(checkString(c, COLUMN_CREDIT_DAYS));
                    retailerModel.setCrLimit(checkString(c, COLUMN_CREDIT_LIMIT));
                    retailerModel.setCashDisc(checkString(c, COLUMN_CASH_DISC_PERC));
                    retailerModel.setChannelCode(checkString(c, COLUMN_CHANNEL_CODE));
                    retailerModel.setSubChannelCode(checkString(c, COLUMN_SUB_CHANNEL_CODE));
                    retailerModel.setGroupCode(checkString(c, COLUMN_GROUP_CODE));
                    retailerModel.setClassCode(checkString(c, COLUMN_CLASS_CODE));
                    retailerModel.setRelationStatus(checkString(c, COLUMN_STORE_TYPE));
                    retailerModel.setParentMap(checkString(c, COLUMN_PARENT_CUSTOMER_CODE));
                    retailerModel.setLatitude(checkString(c, COLUMN_LATITUDE));
                    retailerModel.setLongitude(checkString(c, COLUMN_LONGITUDE));
                    retailerModel.setGstType(checkString(c, COLUMN_CUSTOMER_TYPE));
                    retailerModel.setGstNo(checkString(c, GSTNO));
                    retailerModel.setPanNo(checkString(c, COLUMN_PAN_NO));
                    retailerModel.setApprovalStatus(checkString(c, COLUMN_APPROVAL_STATUS));

                    retailerModel.setBankName("");
                    retailerModel.setBranchName("");
                    retailerModel.setIfscCode("");
                    retailerModel.setAccountType("");
                    retailerModel.setAccountNo("");


                } while (c.moveToNext());
            }
        }


        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return retailerModel;
    }


    public CustomerModel getCustomerDetails(SFADatabase sfaDatabase, String cmpCode, String distCode, String customerCode) {
        CustomerModel retailerModel = new CustomerModel();
        sfaDatabase.openWritableDb();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CUSTOMER_DET, new String[]{cmpCode, distCode, customerCode});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                retailerModel.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                retailerModel.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                retailerModel.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                retailerModel.setCustomerName(checkString(c, COLUMN_CUSTOMER_NAME));
                retailerModel.setPhoneNo(checkString(c, COLUMN_CUSTOMER_SHIP_PHONENO));
                retailerModel.setContactPerson(checkString(c, COLUMN_CONTACT_PERSON));
                retailerModel.setRetailerStatus(checkString(c, COLUMN_RETAILER_STATUS));
                retailerModel.setChannelCode(checkString(c, COLUMN_CHANNEL_CODE));
                retailerModel.setSubChannelCode(checkString(c, COLUMN_SUB_CHANNEL_CODE));
                retailerModel.setGroupCode(checkString(c, COLUMN_GROUP_CODE));
                retailerModel.setClassCode(checkString(c, COLUMN_CLASS_CODE));
                retailerModel.setLatitude(checkString(c, COLUMN_LATITUDE));
                retailerModel.setLongitude(checkString(c, COLUMN_LONGITUDE));
                retailerModel.setCustomerType(checkString(c, COLUMN_CUSTOMER_TYPE));
                retailerModel.setPanNo(checkString(c, COLUMN_PAN_NO));
                retailerModel.setApprovalStatus(checkString(c, COLUMN_APPROVAL_STATUS));


            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return retailerModel;
    }

    public List<AddressModel> getCustomerShipAddrList(BaseDB baseDB, String cmpCode, String distCode, String customerCode) {
        db = baseDB.getDb();
        String query;

        query = "SELECT *\n" +
                "FROM t_customershipaddress\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'\n" +
                "  AND customerCode='" + customerCode + "'";

        if (BuildConfig.DEBUG)
            Log.i("getCusShipAddrList()", query);

        List<AddressModel> retailerList = new ArrayList<>();
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    AddressModel retailerVO = new AddressModel();
                    retailerVO.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerVO.setShipCode(checkString(c, COLUMN_CUSTOMER_SHIP_CODE));
                    retailerVO.setShippingAddress1(checkString(c, COLUMN_CUSTOMER_SHIP_ADDR1));
                    retailerVO.setShippingAddress2(checkString(c, COLUMN_CUSTOMER_SHIP_ADDR2));
                    retailerVO.setShippingAddress3(checkString(c, COLUMN_CUSTOMER_SHIP_ADDR3));
                    retailerVO.setShipCityTown(checkString(c, COLUMN_CUSTOMER_SHIP_CITY));
                    retailerVO.setShippingGeoHierPath(checkString(c, COLUMN_CUSTOMER_DEFAULT_SHIP_GEOHIERPATH));
                    retailerVO.setShippingState(checkString(c, GST_STATE_CODE));
                    retailerVO.setShippingPhoneNumber(checkString(c, COLUMN_CUSTOMER_SHIP_PHONENO));
                    retailerVO.setIsDefault(checkString(c, COLUMN_CUSTOMER_DEFAULT_SHIP_ADDR));
                    retailerList.add(retailerVO);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getCustomerShipAddrList: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public List<RouteModel> getCustomerRouteList(BaseDB baseDB, String cmpCode, String distCode, String customerCode) {
        db = baseDB.getDb();
        String query;

        query = " SELECT *\n" +
                "FROM   " + TABLE_ROUTE + " mr\n" +
                "INNER JOIN " + TABLE_CUSTOMER_ROUTE + " cr\n" +
                "ON mr.routeCode = cr.routeCode \n" +
                "WHERE cr.cmpCode= '" + cmpCode + "'\n" +
                "  AND cr.distrCode='" + distCode + "'\n" +
                "  AND cr.customerCode='" + customerCode + "'";


        if (BuildConfig.DEBUG)
            Log.i("getCustomerRouteList()", query);

        List<RouteModel> retailerList = new ArrayList<>();
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    RouteModel retailerVO = new RouteModel();
                    retailerVO.setCompanyCode(checkString(c, COLUMN_CMP_CODE));
                    retailerVO.setDistributorCode(checkString(c, COLUMN_DISTR_CODE));
                    retailerVO.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerVO.setRouteCode(checkString(c, COLUMN_ROUTE_CODE));
                    retailerVO.setCoverageSeq(checkString(c, COLUMN_COVERAGE_SEQUENCE));
                    retailerVO.setRouteName(checkString(c, COLUMN_ROUTE_NAME));
                    retailerList.add(retailerVO);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getCustomerRouteList: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public void deleteCustomerDetail(BaseDB baseDB, String cmpCode, String distCode, String customerCode) {
        db = baseDB.getDb();
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER + " WHERE cmpCode ='" + cmpCode + "' AND distrCode = '" + distCode + "' AND customerCode = '" + customerCode + "'");
        baseDB.closeDb();
    }

    public void deleteCustomerRoute(BaseDB baseDB, String cmpCode, String distCode, String customerCode) {
        db = baseDB.getDb();
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_ROUTE + " WHERE cmpCode ='" + cmpCode + "' AND distrCode = '" + distCode + "' AND customerCode = '" + customerCode + "'");
        baseDB.closeDb();
    }

    public void deleteCustomerShipAddressDetail(BaseDB baseDB, String cmpCode, String distCode, String customerCode) {
        db = baseDB.getDb();
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_SHIP_ADDRESS + " WHERE cmpCode ='" + cmpCode + "' AND distrCode = '" + distCode + "' AND customerCode = '" + customerCode + "'");
        baseDB.closeDb();
    }

    public void deleteBankDetail(BaseDB baseDB, String cmpCode, String distCode, String customerCode) {
        db = baseDB.getDb();
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_BANK + " WHERE cmpCode ='" + cmpCode + "' AND distrCode = '" + distCode + "' AND customerCode = '" + customerCode + "'");
        baseDB.closeDb();
    }

    public List<ParentMapModel> loadParentMap(SFADatabase sfaDatabase, String cmpCode, String distrCode, String relationStatus) {
        List<ParentMapModel> parentMapModelArrayList = new ArrayList<>();

        sfaDatabase.openWritableDb();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_PARENT, new String[]{cmpCode, distrCode, relationStatus});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                ParentMapModel parentMapModel = new ParentMapModel();
                parentMapModel.setParentCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
                parentMapModel.setParentName(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_NAME)).trim());
                parentMapModelArrayList.add(parentMapModel);
            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return parentMapModelArrayList;
    }

    public List<GeoHierModel> getGeographyLevel(SFADatabase sfaDatabase, String cmpCode, String geoLevel) {
        List<GeoHierModel> geoHierModelArrayList = new ArrayList<>();

        sfaDatabase.openWritableDb();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_GEOGRAPHY, new String[]{cmpCode, geoLevel});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                GeoHierModel geoHierModel = new GeoHierModel();
                geoHierModel.setGeoCode(c.getString(c.getColumnIndex(COLUMN_GEO_CODE)).trim());
                geoHierModel.setGeoName(c.getString(c.getColumnIndex(COLUMN_GEO_NAME)).trim());
                geoHierModel.setParentCode(c.getString(c.getColumnIndex(COLUMN_PARENT_CODE)).trim());

                geoHierModelArrayList.add(geoHierModel);
            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return geoHierModelArrayList;
    }

    public String getGeoHierLevelCode(SFADatabase sfaDatabase, String cmpCode) {
        String hierLevelCode = "";
        String query = "SELECT * FROM " + TABLE_GEO_HIER_LEVEL + " where " + COLUMN_CMP_CODE + " =?" + " order by " + COLUMN_GEO_HIER_LEVEL_CODE + " desc limit 1";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode});
        if (isCursorTrue(c)) {
            do {
                hierLevelCode = c.getString(c.getColumnIndex(COLUMN_GEO_HIER_LEVEL_CODE));
            } while (c.moveToNext());

        }
        closeCursor(c);

        return hierLevelCode;
    }

    public GeoHierModel loadGeographyPath(SFADatabase sfaDatabase, String geoCode) {
        GeoHierModel geoHierModel = new GeoHierModel();

        sfaDatabase.openWritableDb();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_GEOGRAPHY_PATH, new String[]{geoCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                geoHierModel.setGeoCode(c.getString(c.getColumnIndex(COLUMN_GEO_CODE)).trim());
                geoHierModel.setGeoName(c.getString(c.getColumnIndex(COLUMN_GEO_NAME)).trim());
                geoHierModel.setParentCode(c.getString(c.getColumnIndex(COLUMN_PARENT_CODE)).trim());

            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();

        return geoHierModel;
    }

    public PendingVO getCustomerByCode(SFADatabase sfaDatabase, String retailerCode) {

        sfaDatabase.openWritableDb();

        PendingVO pendingData1 = new PendingVO();

        Cursor cursorPending = sfaDatabase.getDb().rawQuery(QUERY_RETAILER_BY_CUSTOMER_CODE, new String[]{retailerCode, "Y"});
        if (isCursorTrue(cursorPending)) {

            pendingData1.setStrCustomerCode(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
            pendingData1.setStrRetailerName(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_NAME)).trim());
            pendingData1.setStrCreditDays(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CREDIT_DAYS)).trim());
            pendingData1.setRetailerAddress(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_SHIP_ADDR1)).trim());
            pendingData1.setGstTinNo(cursorPending.getString(cursorPending.getColumnIndex(GSTNO)).trim());
            pendingData1.setPanNo(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_PAN_NO)).trim());
            pendingData1.setGstType(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CUSTOMER_TYPE)).trim());

            String chCode = getChannelNameBasedonCode(sfaDatabase, cursorPending.getString(cursorPending.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
            pendingData1.setChannelCode(chCode);

            pendingData1.setStrMobileNo(cursorPending.getString(cursorPending.getColumnIndex(COLUMN_MOBILE_NO)).trim());
        }
        closeCursor(cursorPending);
        sfaDatabase.closeDb();
        return pendingData1;
    }

    public OrderBookingVO fetchBillSummaryDataRetailerWise(SFADatabase sfaDatabase, String distCode,
                                                           String salesmanCode, String routeCode,
                                                           String retlrCode, String invoiceNo) {

        OrderBookingVO orderData = new OrderBookingVO();

        String query = "SELECT r.customercode as customerCode,\n" +
                "       r.customername as customerName,\n" +
                "       s.salesmanName as salesmanName,\n" +
                "       r.fssaiNo as retFssaiNum,\n" +
                "       d.fssaiNo,\n" +
                "       r.gstNo,\n" +
                "       ih.roundOffAmt,\n" +
                "       rsh.customerShipAddr1       AS customerShipAddr1,\n" +
                "       rsh.customerShipAddr2       AS customerShipAddr2,\n" +
                "       rsh.customerShipAddr3       AS customerShipAddr3,\n" +
                "       r.channelcode as channelCode,\n" +
                "       o.invoiceno as invoiceNo,\n" +
                "       rt.routeCode as routeCode,       rt.routename         AS routeName,\n" +
                "       r.mobileNo       AS rtrMobileNo,\n" +
                "       Sum(o.grossAmt) AS grossAmt,       Sum(o.netAmt) AS netAmt,\n" +
                "       Sum(o.schDiscAmt)   AS schDiscAmt,\n" +
                "       Sum(o.totalInvoiceQty)   AS OrderQty,\n" +
                "       o.uomCode1 as uomId,       Sum(o.CGSTPerc)              AS CGSTPerc,\n" +
                "       Sum(o.CGSTAmt)             AS CGSTValue,\n" +
                "       Sum(o.SGSTPerc)              AS SGSTPerc,\n" +
                "       Sum(o.SGSTAmt)              AS SGSTValue,\n" +
                "       Sum(o.UTGSTPerc)              AS UTGSTPerc,\n" +
                "       Sum(o.UTGSTValue)              AS UTGSTValue,\n" +
                "       Sum(o.IGSTPerc)              AS IGSTPerc,\n" +
                "       Sum(o.IGSTAmt)              AS IGSTValue,       o.modDt              AS OrderDate,\n" +
                "       Sum(o.schDiscAmt) AS discountAmt,\n" +
                "       Sum(o.cashDiscAmt) AS cashDiscountAmt,\n" +
                "       Sum(o.dbDiscAmt) AS dbDiscountAmt,\n" +
                "       Sum(o.taxAmt) AS taxAmt,\n" +
                "       ih.totCrNoteAmt AS creditNoteAmt,\n" +
                "       ih.remarks,\n" +
                "       Count(*)          AS productcount\n" +
                "FROM   t_invoicedetails o\n" +
                "       INNER JOIN t_customer r\n" +
                "               ON o.customerCode = r.customercode\n" +
                "                  AND o.distrcode = r.distrcode\n" +
                "       INNER JOIN m_salesman_master s\n" +
                "               ON o.salesmanCode = s.salesmanCode\n" +
                "                  AND o.distrcode = s.distrcode\n" +
                "       INNER JOIN m_distributor d\n" +
                "               ON o.distrcode = d.distrcode\n" +
                "       INNER JOIN t_customershipaddress rsh\n" +
                "               ON rsh.customerCode = o.customercode\n" +
                "                  AND rsh.distrcode = o.distrcode\n" +
                "                  AND rsh.defaultShipAddr = 'Y'\n" +
                "       INNER JOIN m_route rt\n" +
                "               ON rt.distrcode = r.distrcode\n" +
                "                  AND rt.routecode = '" + routeCode + "'\n" +
                "       INNER JOIN t_invoiceheader ih\n" +
                "               ON o.distrcode = ih.distrcode\n" +
                "                  AND o.invoiceNo = ih.invoiceNo " +
                "WHERE  o.distrcode = '" + distCode + "' \n" +
                "       AND o.salesmancode = '" + salesmanCode + "' \n" +
                "       AND o.routecode = '" + routeCode + "' \n" +
                "       AND o.customerCode = '" + retlrCode + "' \n" +
                "       AND o.invoiceNo ='" + invoiceNo + "'";
//                "       AND o.completeFlag = 'Y' \n" +
//                "       AND o.uploadFlag = 'N'";

        Cursor cursorOrder = sfaDatabase.getDb().rawQuery(query, null);
        if (cursorOrder != null && cursorOrder.getCount() > 0 && cursorOrder.moveToFirst()) {

            orderData.setRetailerCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_CODE)));
            orderData.setRetailerName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_NAME)));
//            orderData.setSalesmanName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_SALESMAN_NAME)));
            orderData.setRouteCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ROUTE_CODE)));
            orderData.setRouteName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ROUTE_NAME)));
            orderData.setAddress(cursorOrder.getString(cursorOrder.getColumnIndex("customerShipAddr1")));
            orderData.setMobileNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_RTR_MOBILE_NO)));
            orderData.setOrderDate(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ORDER_DATE_CAPS)));
            orderData.setOrderValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex("grossAmt"))));
            orderData.setTotalAmount(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex("grossAmt"))));
            orderData.setPrimDiscOrderValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_SCH_DISC_AMT))));
            orderData.setUomId(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_UOM_ID)));
            orderData.setCgstperc(cursorOrder.getDouble(cursorOrder.getColumnIndex(CGST_PERCENT)));
//            orderData.setRoundOffAmt(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ROUND_OFF_AMT)));
            orderData.setCgstvalue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(CGST_VALUE))));
            orderData.setSgstPerc(cursorOrder.getDouble(cursorOrder.getColumnIndex(SGST_PERCENT)));
            orderData.setSgstValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(SGST_VALUE))));
            orderData.setIgstPerc(cursorOrder.getDouble(cursorOrder.getColumnIndex(IGST_PERCENT)));
            orderData.setIgstvalue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(IGST_VALUE))));
            orderData.setUtgstPerc(cursorOrder.getDouble(cursorOrder.getColumnIndex(UTGST_PERCENT)));
            orderData.setUtgstValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(UTGST_VALUE))));
            orderData.setNoOfItems(cursorOrder.getInt(cursorOrder.getColumnIndex("productcount")));
            orderData.setOrderInvoiceNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_INVOICE_NO)));
//            orderData.setRetailerFssaiNo(cursorOrder.getString(cursorOrder.getColumnIndex("retFssaiNum")));
//            orderData.setFssaiNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_FSSAI_NO)));
//            orderData.setGstNo(cursorOrder.getString(cursorOrder.getColumnIndex(GSTNO)));
//            orderData.setSchemeAmount(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex("discountAmt"))));
//            orderData.setTax(cursorOrder.getDouble(cursorOrder.getColumnIndex("taxAmt")));
////                String chCode = getChannelNameBasedonCode(sfaDatabase, cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
//            orderData.setCashDiscAmt(new BigDecimal(cursorOrder.getDouble(cursorOrder.getColumnIndex("cashDiscountAmt"))));
//            orderData.setDbDiscountAmt(cursorOrder.getDouble(cursorOrder.getColumnIndex("dbDiscountAmt")));
//            orderData.setNetAmount(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex("netAmt"))));
//            orderData.setCrNoteAmt(cursorOrder.getDouble(cursorOrder.getColumnIndex("creditNoteAmt")));
            orderData.setChannelName("");
            orderData.setRemarks(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_REMARKS)));

        }
        closeCursor(cursorOrder);
        sfaDatabase.closeDb();
        return orderData;
    }

    public List<StockOnHandModel> getAvailStockOnHandForUpload(SFADatabase sfaDatabase, String cmpCode,
                                                               String distCode) {

        List<StockOnHandModel> stockOnHandModelList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_STOCK_ON_HAND + " WHERE cmpCode =? AND distrCode = ? AND upload =?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode, "N"});
        if (isCursorTrue(c)) {
            do {
                StockOnHandModel stock = new StockOnHandModel();
                stock.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                stock.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                stock.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                stock.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                stock.setGodownCode(c.getString(c.getColumnIndex(COLUMN_GODOWN_CODE)));
                stock.setSaleableQty(c.getInt(c.getColumnIndex(COLUMN_SALEABLE_QTY)));
                stock.setUnSaleableQty(c.getInt(c.getColumnIndex(COLUMN_UNSALEABLE_QTY)));
                stock.setOfferQty(c.getInt(c.getColumnIndex(COLUMN_OFFER_QTY)));
                stock.setResvSaleableQty(c.getInt(c.getColumnIndex(COLUMN_RESV_SALEABLE_QTY)));
                stock.setResvUnSaleableQty(c.getInt(c.getColumnIndex(COLUMN_RESV_UNSALEABLE_QTY)));
                stock.setResvOfferQty(c.getInt(c.getColumnIndex(COLUMN_RESV_OFFER_QTY)));
                stock.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                stock.setModDt(c.getLong(c.getColumnIndex(COLUMN_MOD_DT)));
                stock.setUploadFlag(c.getString(c.getColumnIndex(COLUMN_UPLOAD)));

                stockOnHandModelList.add(stock);
            } while (c.moveToNext());
        }
        closeCursor(c);
        sfaDatabase.closeDb();

        return stockOnHandModelList;

    }

    public boolean isRateEditableForCustomer(SFADatabase sfaDatabase, String distCode, String retailerCode,
                                             String prodCode) {

        boolean editState = false;
        String query = "SELECT * FROM " + TABLE_RATE_EDIT + " where distrCode=? AND customerCode=? AND prodCode=? and allowEdit='Y'";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{distCode, retailerCode, prodCode});
        if (isCursorTrue(c)) {
            editState = true;

        }
        closeCursor(c);

        return editState;
    }

    public String getCustomerShipAddress(BaseDB baseDB, String cmpCode, String distCode, String customerCode) {
        db = baseDB.getDb();
        String query;

        query = "SELECT customerShipCode \n" +
                "FROM t_customershipaddress \n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'\n" +
                "  AND customerCode='" + customerCode + "'\n" +
                "  AND defaultShipAddr='Y'";

        if (BuildConfig.DEBUG)
            Log.i("getCustShipAddress()", query);

        String shipCode = "";
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    shipCode = checkString(c, COLUMN_CUSTOMER_SHIP_CODE);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getCustomerShipAddress: " + e.getMessage(), e);
            shipCode = "";
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return shipCode;
    }

    public LoginModel getSyncDateTime(BaseDB baseDB, String cmpCode, String distCode) {
        baseDB.openWritableDb();
        LoginModel loginModel = new LoginModel();
        String query = "SELECT *\n" +
                "FROM c_loginTimeCapture \n" +
                " WHERE user_code= ? \n" +
                "  AND processType!=? order by date desc limit 1";

        try {
            Cursor cm = baseDB.getDb().rawQuery(query, new String[]{ distCode, "Login"});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
                loginModel.setSyncDate(cm.getString(cm.getColumnIndex(COLUMN_DATE)));
                loginModel.setUserStatus(cm.getString(cm.getColumnIndex(COLUMN_STATUS)));
            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getSyncDateTime: " + e.getMessage(), e);
        }
        return loginModel;
    }

    public OrderBookingVO fetchSalesReturnDetails(SFADatabase sfaDatabase, String distCode, String salesmanCode, String routeCode, String retrCode, String salesNo) {
        OrderBookingVO orderData = new OrderBookingVO();

        String query = "SELECT r.customercode AS customerCode,\n" +
                "       r.customername AS customerName,\n" +
                "       s.salesmanName AS salesmanName,\n" +
                "       r.fssaiNo AS retFssaiNum,\n" +
                "       d.fssaiNo,\n" +
                "       o.returnDt,\n" +
                "       r.gstNo,\n" +
                "       rsh.customerShipAddr1 AS customerShipAddr1,\n" +
                "       rsh.customerShipAddr2 AS customerShipAddr2,\n" +
                "       rsh.customerShipAddr3 AS customerShipAddr3,\n" +
                "       rt.routeCode AS routeCode,\n" +
                "       rt.routename AS routeName,\n" +
                "       r.mobileNo AS rtrMobileNo,\n" +
                "       o.salesReturnNo,\n" +
                "       Count(*) AS productcount\n" +
                "FROM t_salesreturndetails ih\n" +
                "INNER JOIN t_salesreturnheader o ON o.distrcode = ih.distrcode\n" +
                "AND o.salesReturnNo = ih.salesReturnNo\n" +
                "INNER JOIN t_customer r ON o.customerCode = r.customercode\n" +
                "AND o.distrcode = r.distrcode\n" +
                "INNER JOIN m_salesman_master s ON o.salesmanCode = s.salesmanCode\n" +
                "AND o.distrcode = s.distrcode\n" +
                "INNER JOIN m_distributor d ON o.distrcode = d.distrcode\n" +
                "INNER JOIN t_customershipaddress rsh ON rsh.customerCode = o.customercode\n" +
                "AND rsh.distrcode = o.distrcode\n" +
                "AND rsh.defaultShipAddr = 'Y'\n" +
                "INNER JOIN m_route rt ON rt.distrcode = r.distrcode\n" +
                "AND rt.routecode = '" + routeCode + "'\n" +
                "WHERE o.distrcode = '" + distCode + "'\n" +
                "  AND o.salesmancode = '" + salesmanCode + "'\n" +
                "  AND o.routecode = '" + routeCode + "'\n" +
                "  AND o.customerCode = '" + retrCode + "'\n" +
                "  AND o.salesReturnNo = '" + salesNo + "'";

        Cursor cursorOrder = sfaDatabase.getDb().rawQuery(query, null);
        if (cursorOrder != null && cursorOrder.getCount() > 0 && cursorOrder.moveToFirst()) {

            orderData.setRetailerCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_CODE)));
            orderData.setRetailerName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_NAME)));
//            orderData.setSalesmanName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_SALESMAN_NAME)));
            orderData.setRouteCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ROUTE_CODE)));
            orderData.setRouteName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ROUTE_NAME)));
            orderData.setAddress(cursorOrder.getString(cursorOrder.getColumnIndex("customerShipAddr1")));
            orderData.setMobileNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_RTR_MOBILE_NO)));
            orderData.setNoOfItems(cursorOrder.getInt(cursorOrder.getColumnIndex("productcount")));
            orderData.setOrderInvoiceNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_SALES_RETURNNO)));
            orderData.setOrderDate(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_RETURN_DT)));
//            orderData.setRetailerFssaiNo(cursorOrder.getString(cursorOrder.getColumnIndex("retFssaiNum")));
//            orderData.setFssaiNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_FSSAI_NO)));
//            orderData.setGstNo(cursorOrder.getString(cursorOrder.getColumnIndex(GSTNO)));

        }
        closeCursor(cursorOrder);
        sfaDatabase.closeDb();
        return orderData;
    }

    public boolean isRequestCustomerDataAvailable(SFADatabase sfaDatabase, String cmpCode, String distCode, String customerCode) {

        sfaDatabase.openWritableDb();
        String query = "SELECT *\n" +
                "FROM t_customerapprovaldata \n" +
                " WHERE cmpCode= ? \n" +
                "  AND distrCode= ? \n" +
                "  AND customerCode= ? \n" +
                "  AND uploadFlag=? ";
        Cursor cm = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode, customerCode, "N"});
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        sfaDatabase.closeDb();

        return false;
    }

    public void insertCustomerApprovalDetail(SFADatabase sfaDatabase, String distCode, String cmpCode, String customerCode, String approvalStatus, String customerData) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER_APPROVAL_DATA + " VALUES (?,?,?,?,?,?,?);";

        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, cmpCode);
        statement.bindString(2, distCode);
        statement.bindString(3, customerCode);
        statement.bindString(4, approvalStatus);
        statement.bindString(5, customerData);
        statement.bindString(6, DateUtil.getCurrentYearMonthDate());
        statement.bindString(7, "N");
        statement.execute();
    }

    public CustomerApprovalModel getCustomerDetailsForApproval(SFADatabase sfaDatabase, String cmpCode, String distCode, String customerCode) {
        CustomerApprovalModel retailerModel = new CustomerApprovalModel();
        sfaDatabase.openWritableDb();
        Cursor c;
        c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CUSTOMER_DET, new String[]{cmpCode, distCode, customerCode});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                retailerModel.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                retailerModel.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                retailerModel.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                retailerModel.setCompanyCustomerCode(checkString(c, COLUMN_COMPANY_CUSTOMER_CODE));
                retailerModel.setCustomerName(checkString(c, COLUMN_CUSTOMER_NAME));
                retailerModel.setPinCode(checkString(c, COLUMN_PIN_CODE));
                retailerModel.setPhoneNo(checkString(c, COLUMN_CUSTOMER_SHIP_PHONENO));
                retailerModel.setMobileNo(checkString(c, COLUMN_MOBILE_NO));
                retailerModel.setContactPerson(checkString(c, COLUMN_CONTACT_PERSON));
                retailerModel.setEmailID(checkString(c, COLUMN_EMAIL_ID));
                retailerModel.setDayOff(checkString(c, COLUMN_DAY_OFF));
                retailerModel.setRetailerStatus(checkString(c, COLUMN_RETAILER_STATUS));
                retailerModel.setFssaiNo(checkString(c, COLUMN_FFSAI_NO));
                retailerModel.setDrugLicNo(checkString(c, COLUMN_DRUG_LIC_NO));
                retailerModel.setDrugLicExpiryDate(checkString(c, COLUMN_DRUG_LIC_EXPIRY_DATE));
                retailerModel.setCreditBills(checkString(c, COLUMN_CREDIT_BILLS));
                retailerModel.setCreditDays(checkString(c, COLUMN_CREDIT_DAYS));
                retailerModel.setCreditLimit(checkString(c, COLUMN_CREDIT_LIMIT));
                retailerModel.setCashDiscPerc(checkString(c, COLUMN_CASH_DISC_PERC));
                retailerModel.setChannelCode(checkString(c, COLUMN_CHANNEL_CODE));
                retailerModel.setSubChannelCode(checkString(c, COLUMN_SUB_CHANNEL_CODE));
                retailerModel.setGroupCode(checkString(c, COLUMN_GROUP_CODE));
                retailerModel.setClassCode(checkString(c, COLUMN_CLASS_CODE));
                retailerModel.setStoreType(checkString(c, COLUMN_STORE_TYPE));
                retailerModel.setParentCustomerCode(checkString(c, COLUMN_PARENT_CUSTOMER_CODE));
                retailerModel.setLatitude(checkString(c, COLUMN_LATITUDE));
                retailerModel.setLongitude(checkString(c, COLUMN_LONGITUDE));
                retailerModel.setCustomerType(checkString(c, COLUMN_CUSTOMER_TYPE));
                retailerModel.setGstTinNo(checkString(c, GSTNO));
                retailerModel.setPanNo(checkString(c, COLUMN_PAN_NO));
                retailerModel.setApprovalStatus(checkString(c, COLUMN_APPROVAL_STATUS));


            } while (c.moveToNext());
        }


        if (c != null) {
            c.close();
        }
        sfaDatabase.closeDb();
        return retailerModel;
    }

    public List<RetailerVO> getCustomerData(SFADatabase sfaDatabase, String cmpCode, String distrCode, String routeCode) {
        List<RetailerVO> retailerVOList = new ArrayList<>();
        String query = "";
        Cursor c;

        if (routeCode != null && !routeCode.isEmpty()) {

            query = "SELECT cus.customercode,\n" +
                    "       cus.customername,\n" +
                    "       cusAddr.customershipaddr1\n" +
                    "FROM   t_customer cus\n" +
                    "       INNER JOIN t_customershipaddress cusAddr\n" +
                    "               ON cus.customercode = cusAddr.customercode\n" +
                    "       LEFT JOIN t_customerroute cusRoute\n" +
                    "               ON cus.customercode = cusRoute.customercode\n" +
                    "WHERE  cus.cmpcode = ?\n" +
                    "       AND cus.distrcode = ?\n" +
                    "       AND cus.customerCode != ?\n" +
                    "       AND cusAddr.defaultShipAddr = ?\n" +
                    "       AND cusRoute.routeCode = ?\n" +
                    "       GROUP BY cus.customerCode\n" +
                    "ORDER  BY cus.customername  ";

            c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, distrCode + AppUtils.formatCode(0), "Y", routeCode});

        } else {

            query = "SELECT cus.customercode,\n" +
                    "       cus.customername,\n" +
                    "       cusAddr.customershipaddr1\n" +
                    "FROM   t_customer cus\n" +
                    "       INNER JOIN t_customershipaddress cusAddr\n" +
                    "               ON cus.customercode = cusAddr.customercode\n" +
                    "WHERE  cus.cmpcode = ?\n" +
                    "       AND cus.distrcode = ?\n" +
                    "       AND cus.customerCode != ?\n" +
                    "       AND cusAddr.defaultShipAddr = ?\n" +
                    "       GROUP BY cus.customerCode\n" +
                    "ORDER  BY cus.customername  ";

            c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, distrCode + AppUtils.formatCode(0), "Y"});
        }


        if (isCursorTrue(c)) {
            do {
                RetailerVO retailerVO = new RetailerVO();
                retailerVO.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                retailerVO.setCustomerName(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_NAME)));
                retailerVO.setRetailerAddr1(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_SHIP_ADDR1)));

                retailerVOList.add(retailerVO);
            } while (c.moveToNext());

        }
        closeCursor(c);

        return retailerVOList;
    }

    public boolean isRouteExistForCustomer(SFADatabase sfaDatabase, String cmpCode, String distrCode, String customerCode, String routeCode) {
        String query = "SELECT * FROM " + TABLE_CUSTOMER_ROUTE + " WHERE cmpCode = ? and distrCode = ? and customerCode = ? and routeCode = ?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, customerCode, routeCode});

        if (c != null && c.getCount() > 0) {
            return true;
        }
        closeCursor(c);
        return false;
    }

    public void updateCustomerState(SFADatabase sfaDatabase, String cmpCode, String distrCode, String customerCode) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UPLOAD, "N");
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_CUSTOMER_CODE + " = ?";
        String[] updateArgs = {cmpCode, distrCode, customerCode};
        db.update(TABLE_CUSTOMER, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void insertCustomerRouteAssigning(SFADatabase sfaDatabase, String cmpCode, String distrCode, String customerCode, String routeCode) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER_ROUTE + " VALUES (?,?,?,?,?,?,?);";

        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);

        try {
            statement.clearBindings();
            statement.bindString(1, cmpCode);
            statement.bindString(2, distrCode);
            statement.bindString(3, customerCode);
            statement.bindString(4, routeCode);
            statement.bindString(5, "1");
            statement.bindString(6, DateUtil.getCurrentYearMonthDate());
            statement.bindString(7, "N");
            statement.execute();
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.e(TAG, "insertCustomerRouteAssigning", e);
        }

    }

    public void deleteCustomerRouteAssigning(SFADatabase sfaDatabase, String cmpCode, String distrCode, String customerCode, String routeCode) {
        db = sfaDatabase.getDb();
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_ROUTE + " WHERE cmpCode ='" + cmpCode + "' AND distrCode = '" + distrCode + "' AND customerCode = '" + customerCode + "' AND routeCode = '" + routeCode + "' ");
        sfaDatabase.closeDb();
    }

    public void updateBatchTransferDetails(SFADatabase sfaDatabase, String cmpCode, String distrCode, String goDownCode, String btrNo, String remarks) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REMARKS, remarks);
        values.put(COLUMN_COMPLETE_FLAG, "Y");
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_GODOWN_CODE + appendAnd + COLUMN_BATCH_TRANSFERNO + " = ?";
        String[] updateArgs = {cmpCode, distrCode, goDownCode, btrNo};
        db.update(TABLE_BATCH_TRANSFER_DETAILS, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public double getRetailerSOV(SFADatabase sfaDatabase, String distrcode, String salesmancode, String routeCode, String customerCode) {

        double value = 0;
        String query= "";
        Cursor c;
//        query = "SELECT sov FROM " + TABLE_RETAILER_DASHBOARD + " WHERE " + COLUMN_DISTR_CODE + "=? AND " + COLUMN_SALESMAN_CODE + "=? AND " + COLUMN_ROUTE_CODE + "=? AND " + COLUMN_CUSTOMER_CODE + "=?";

        c = sfaDatabase.getDb().rawQuery(query, new String[]{distrcode, salesmancode, routeCode, customerCode});

        if (isCursorTrue(c)) {
            do {
                value = c.getDouble(c.getColumnIndex("sov"));
            } while (c.moveToNext());

        }

        closeCursor(c);
        sfaDatabase.closeDb();

        return value;
    }

    public List<RetailerVO> getAllRetailersLocationList(SFADatabase sfaDatabase, String cmpCode,
                                                           String distCode, String salesmanCode) {

        List<RetailerVO> feedbackModelList = new ArrayList<>();
        String query = "SELECT * FROM '" + TABLE_UPDATE_LOCATION + "' WHERE upload=?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{"N"});
        if (isCursorTrue(c)) {
            do {
                RetailerVO feedbackModel = new RetailerVO();
                feedbackModel.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_LOGIN_CODE)));
                feedbackModel.setLatitude(c.getString(c.getColumnIndex(COLUMN_LATITUDE)));
                feedbackModel.setLongitude(c.getString(c.getColumnIndex(COLUMN_LONGITUDE)));
                feedbackModel.setAccountNo(c.getString(c.getColumnIndex(COLUMN_IMAGE)));
                feedbackModel.setPostalCode(c.getString(c.getColumnIndex(COLUMN_POSTAL_CODE)));
                feedbackModel.setCoverageDate(c.getString(c.getColumnIndex(COLUMN_DATE)));
                feedbackModel.setAccountType(c.getString(c.getColumnIndex(COLUMN_UPLOAD_TO)));
                feedbackModelList.add(feedbackModel);
            } while (c.moveToNext());

        }
        closeCursor(c);
        return feedbackModelList;
    }

    public List<FeedbackModel> getAllRetailersFeedbackList(SFADatabase sfaDatabase, String cmpCode,
                                                           String distCode, String salesmanCode) {

        List<FeedbackModel> feedbackModelList = new ArrayList<>();
        String query = "SELECT * FROM '" + TABLE_FEEDBACK + "' WHERE upload=?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{"N"});
        if (isCursorTrue(c)) {
            do {
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

        }
        closeCursor(c);
        return feedbackModelList;
    }

    public String getTotalCash(SFADatabase sfaDatabase, String distCode, String salesmanCode, int i) {



        String strQuery = "";

        Cursor c = null;

        String tot = "0";



        if (i == 1) {



            strQuery = "Select SUM(Cashamt) as totalcashvalue from " + TABLE_CASH_COLLECTION + " WHERE distrCode=? AND salesmanCode=?";

            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});

        } else if (i == 2) {



            strQuery = "Select SUM(instrumentAmt) as totalchequevalue from " + TABLE_CHEQUE_COLLECTION + " WHERE distrCode=? AND salesmanCode=?";

            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});

        } else if (i == 3) {



            strQuery = "Select count(DISTINCT(readableInvNo)) as submittedOrder from " + TABLE_ORDER_BOOKING + " WHERE upload=?";

            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{"Y"});

        } else if (i == 4) {

            strQuery = "select  count(DISTINCT(readableInvNo)) as totalOrder from t_OrderBooking";

            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{});

        } else if (i == 5) {

            strQuery = "Select SUM(cashAmt) as totalCash from " + TABLE_CASH_COLLECTION + " WHERE distrCode=? AND salesmanCode=? and ScreenName='Billing'";

            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});

        } else if (i == 6) {



            strQuery = "Select COUNT(*) as submittedOrder from " + TABLE_BILLING_TRACKER + " WHERE distrCode=? AND salesmanCode=? AND upload = 'Y'";

            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});

        } else if (i == 7) {



            strQuery = "Select COUNT(*) as submittedOrder from " + TABLE_RETAILER_VISIT + " WHERE distrCode=? AND salesmanCode=? AND ShelfInfoVisit = 'Y' OR compShelfVisit='Y' OR stockVisit='Y'";

            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});

        } else if (i == 8) {



            strQuery = "Select COUNT(*) as submittedOrder from " + TABLE_RETAILER_VISIT + " WHERE distrCode=? AND salesmanCode=? AND stockVisit='Y'";

            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});

        } else if (i == 9) {

            strQuery = "Select COUNT(*) as totalOrder from " + TABLE_BILLING_TRACKER + " WHERE distrCode=? AND salesmanCode=?";

            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});

        }





        if (isCursorTrue(c)) {



            if (i == 1) {



                tot = String.format(Locale.getDefault(), "%.2f", c.getDouble(c.getColumnIndex("totalcashvalue")));



            } else if (i == 2) {



                tot = String.format(Locale.getDefault(), "%.2f", c.getDouble(c.getColumnIndex("totalchequevalue")));



            } else if (i == 3) {

                tot = String.valueOf(c.getInt(c.getColumnIndex("submittedOrder")));



            } else if (i == 4 || i == 9) {

                tot = String.valueOf(c.getInt(c.getColumnIndex("totalOrder")));



            } else if (i == 5) {

                tot = String.valueOf(c.getInt(c.getColumnIndex("totalCash")));

            } else if (i == 6) {

                tot = String.valueOf(c.getInt(c.getColumnIndex("submittedOrder")));



            } else if (i == 7 || i == 8) {

                tot = String.valueOf(c.getInt(c.getColumnIndex("submittedOrder")));

            }



        }

        closeCursor(c);

        sfaDatabase.closeDb();

        return tot;



    }



    public String[] getOrderCounts(SFADatabase sfaDatabase, String tableName, String distCode, String salesmanCode, String routeCode) {

        Cursor c;
        String strOrderVal = "0";
        String strNoOfLines = "0";

        String strQuery;

        if (routeCode != null && !routeCode.equalsIgnoreCase("0")) {

//            strQuery = "Select SUM(totalAmount) as totalordervalue,COUNT(*) as productcount from " + tableName + " WHERE distrCode=? AND salesmanCode=? and routeCode=? and completeFlag='Y'";
            strQuery = "Select SUM(totalAmount) as totalOrderValue,COUNT(*) as productcount from " + tableName;
        } else {

//            strQuery = "Select SUM(totalAmount) as totalordervalue,COUNT(*) as productcount from " + tableName + " WHERE distrCode=? AND salesmanCode=? and completeFlag='Y'";
            strQuery = "Select SUM(totalAmount) as totalOrderValue,COUNT(*) as productcount from " + tableName;
        }
        if (routeCode != null && !routeCode.equalsIgnoreCase("0")) {

//            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode, routeCode});
            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{});
        } else {
//            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});
            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{});
        }


        if (isCursorTrue(c)) {

            strOrderVal = String.format(Locale.getDefault(), "%.2f", c.getDouble(c.getColumnIndex(COLUMN_TOTAL_ORDER_VALUE)));
            strNoOfLines = String.valueOf(c.getInt(c.getColumnIndex(COLUMN_PRODUCT_COUNT)));
        }
        closeCursor(c);
        sfaDatabase.closeDb();
        return new String[]{strOrderVal, strNoOfLines};

    }


    public String getOrderUniqueLines(SFADatabase sfaDatabase, String tableName, String distCode, String salesmanCode, String routeCode) {

        Cursor c;
        String strNoOfUniqueLines = "0";
        String strQuery;
        if (routeCode != null && !routeCode.equalsIgnoreCase("0")) {
            strQuery="SELECT COUNT(DISTINCT ProdCode) as uniqueLinesCount FROM "+ tableName ;
        } else {
            strQuery="SELECT COUNT(DISTINCT ProdCode) as uniqueLinesCount FROM "+ tableName;
        }
        if (routeCode != null && !routeCode.equalsIgnoreCase("0")) {
            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{});
        } else {
            c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{});
        }

        if (isCursorTrue(c)) {

            strNoOfUniqueLines = String.valueOf(c.getInt(c.getColumnIndex(COLUMN_UNIQUE_LINES_COUNT)));
        }
        closeCursor(c);
        sfaDatabase.closeDb();
        return strNoOfUniqueLines;

    }

    public String getCoveredRetailerCount(SFADatabase sfaDatabase, String distCode, String salesmanCode) {

        String strQuery = "Select COUNT(*) as totalCompletedRetailer from " + TABLE_RETAILER_VISIT + " WHERE distrCode=? " +
                "AND salesmanCode =? AND (orderVisit='Y' OR returnVisit='Y' OR stockVisit='Y' OR  collectionVisit='Y' OR surveyVisit='Y' OR isVisit='C' OR billVisit='Y' OR sosVisit='Y' OR ShelfInfoVisit='Y' OR compShelfVisit='Y')";
        String strCompletedRtlr = "0";
        Cursor c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});

        if (isCursorTrue(c)) {
            strCompletedRtlr = String.valueOf(c.getInt(c.getColumnIndex("totalCompletedRetailer")));
        }
        closeCursor(c);
        sfaDatabase.closeDb();
        return strCompletedRtlr;

    }

    public String getCompletedRetailerCount(SFADatabase sfaDatabase, String distCode, String salesmanCode, String columnName) {

        String strQuery = "Select COUNT(*) as totalCompletedRetailer from " + TABLE_RETAILER_VISIT + " WHERE " + columnName + "='Y' AND distrCode=? AND salesmanCode =?";
        String strCompletedRtlr = "0";
        Cursor c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});

        if (isCursorTrue(c)) {
            strCompletedRtlr = String.valueOf(c.getInt(c.getColumnIndex("totalCompletedRetailer")));
        }
        closeCursor(c);
        sfaDatabase.closeDb();
        return strCompletedRtlr;

    }

    public String getTotalRetailers(SFADatabase sfaDatabase, String distCode, String salesmanCode, Boolean isTodayBeat) {

        String tot = "0";
        Cursor c;
        String strQuery;
        if (isTodayBeat) {
            strQuery = "Select COUNT(*) as totalRetailer from " + TABLE_RETAILER + " WHERE distrCode =? AND salesmanCode =? AND routeCode=(select routeCode from " + TABLE_ROUTE + " where isTodayBeat='" + isTodayBeat + "') AND (approvedFlag='N' OR approvedFlag='E')";
        } else {
            strQuery = "Select COUNT(*) as totalRetailer from " + TABLE_RETAILER + " WHERE distrCode =? AND salesmanCode =? AND (approvedFlag='N' OR approvedFlag='E')";
        }
        c = sfaDatabase.getDb().rawQuery(strQuery, new String[]{distCode, salesmanCode});

        if (isCursorTrue(c)) {
            tot = String.valueOf(c.getInt(c.getColumnIndex("totalRetailer")));
        }
        closeCursor(c);
        sfaDatabase.closeDb();
        return tot;

    }

    public List<OrderBookingVO> getDaySummaryOrderTabValue(SFADatabase sfaDatabase, String distrcode, String salesmancode, String strretailerCode) {



        List<OrderBookingVO> orderedProdList = new ArrayList<>();

        String query;



        query = "SELECT o.cmpCode AS cmpCode,\n" +

                "                o.distrCode AS distrCode,\n" +

                "                o.readableInvNo AS readableInvNo,\n" +

                "                o.invoiceno AS invoiceNo,\n" +

                "                o.ProdCode,\n" +

                "                o.Batchcode,\n" +

                "                p.ProdName,\n" +

                "                p.Prodshortname,\n" +

                "                pb.MRP,\n" +

                "                o.actualSellRate as SellPrice,\n" +

                "                o.orderqty AS OrderQty,\n" +

                "                o.uomid,\n" +

                "                o.ordervalue AS OrderValue,\n" +

                "                o.primaryDiscOrderValue AS primaryDiscOrderValue,\n" +

                "                o.taxperproduct AS taxPerProduct,\n" +

                "                o.schemeamount AS schemeAmount,\n" +

                "                o.cgstperc AS CGSTPerc,\n" +

                "                o.cgstvalue AS CGSTValue,\n" +

                "                o.sgstperc AS SGSTPerc,\n" +

                "                o.sgstvalue AS SGSTValue,\n" +

                "                o.grossAmount AS grossAmount,\n" +

                "                o.totalamount AS totalAmount\n" +

                "FROM   " + TABLE_ORDER_BOOKING + " o" +

                "       INNER JOIN " + TABLE_PRODUCT_MASTER + " p\n" +

                "               ON o.cmpCode = p.cmpCode\n" +

                "               AND o.ProdCode = p.prodcode\n" +

                "       INNER JOIN " + TABLE_PRODUCT_BATCH_MASTER + " pb\n" +

                "               ON o.cmpCode = pb.cmpCode\n" +

                "               AND o.ProdCode = pb.prodCode\n" +

                "               AND o.BatchCode = pb.ProdBatchCode\n" +

                "WHERE o.readableInvNo = ?";





        Cursor cursorOrder = sfaDatabase.getDb().rawQuery(query, new String[]{distrcode});

        if (cursorOrder != null && cursorOrder.getCount() > 0) {

            while (cursorOrder.moveToNext()) {

                OrderBookingVO orderData = new OrderBookingVO();



                double orderValue = cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ORDER_VALUE_CAPS));

                double primaryDiscValue = cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE));

                double schDisc = cursorOrder.getDouble(cursorOrder.getColumnIndex(SCHEME_AMOUNT));



                double schemeAmount = (orderValue - primaryDiscValue) + schDisc;



                orderData.setProdName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_PROD_NAME)));

                orderData.setProdCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_PROD_CODE_CAPS)));

                orderData.setProdBatchCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_BATCH_CODE)));

                orderData.setMrp(BigDecimal.valueOf(0));

                orderData.setQuantity(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));

                orderData.setUomId(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_UOM_ID)));

                orderData.setSellPrice(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));

                orderData.setOrderValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));

                orderData.setPrimDiscOrderValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE))));

                orderData.setTax(cursorOrder.getDouble(cursorOrder.getColumnIndex(TAX_PER_PRODUCT)));



                if (schemeAmount > 0) {

                    orderData.setSchemeAmount(BigDecimal.valueOf(schemeAmount));

                } else {

                    orderData.setSchemeAmount(BigDecimal.valueOf(0.00));

                }



                orderData.setCgstperc(cursorOrder.getDouble(cursorOrder.getColumnIndex(CGST_PERCENT)));

                orderData.setCgstvalue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(CGST_VALUE))));

                orderData.setSgstPerc(cursorOrder.getDouble(cursorOrder.getColumnIndex(SGST_PERCENT)));

                orderData.setSgstValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(SGST_VALUE))));

                orderData.setTotalAmount(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(TOTAL_AMOUNT))));

                orderData.setlGrossAmt(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(GROSS_AMOUNT))));

                orderedProdList.add(orderData);

            }

        }

        closeCursor(cursorOrder);

        sfaDatabase.closeDb();



        return orderedProdList;



    }

    public List<OrderBookingVO> getInvoiceSummary(SFADatabase sfaDatabase,
                                                  String distrcode, String salesmancode, String strretailerCode) {

        List<OrderBookingVO> orderedProdList = new ArrayList<>();

        String query;
        query = "SELECT p.Prodname,\n" +
                "                p.Prodcode,\n" +
                "                p.Prodshortname,\n" +
                "                p.Batchcode,\n" +
                "                P.MRP,\n" +
                "                Sum(o.orderqty)    AS OrderQty,\n" +
                "                o.uomid,\n" +
                "                p.sellprice,\n" +
                "                Sum(o.ordervalue) AS OrderValue,\n" +
                "                Sum(o.primaryDiscOrderValue) AS primaryDiscOrderValue,\n" +
                "                Sum(o.taxperproduct) AS taxPerProduct,\n" +
                "                Sum(o.discountAmount) AS discountAmount,\n" +
                "                Sum(o.cgstperc) AS CGSTPerc,\n" +
                "                Sum(o.cgstvalue) AS CGSTValue,\n" +
                "                Sum(o.sgstperc) AS SGSTPerc,\n" +
                "                Sum(o.sgstvalue) AS SGSTValue,\n" +
                "                Sum(o.totalamount) AS totalAmount\n" +
                "FROM   " + TABLE_BILLING + " o" +
                "       INNER JOIN " + TABLE_VANSALES_PRODUCTS + " p\n" +
                "               ON o.prodcode = p.prodcode\n" +
                "               AND o.BatchCode = p.BatchCode\n" +
                "                  AND o.distrcode = p.distrcode\n" +
                "WHERE  o.retlrcode =  '" + strretailerCode + "' \n" +
                "       AND o.distrcode = '" + distrcode + "'\n" +
                "       AND o.completeFlag = 'Y'\n" +
                "GROUP  BY o.prodcode,o.uomId,o.BatchCode";

        Cursor cursorOrder = sfaDatabase.getDb().rawQuery(query, null);
        if (cursorOrder != null && cursorOrder.getCount() > 0) {
            while (cursorOrder.moveToNext()) {
                OrderBookingVO orderData = new OrderBookingVO();

                orderData.setProdName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_PROD_NAME_CAPS)));
                orderData.setProdCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_PROD_CODE_CAPS)));
                orderData.setProdBatchCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_BATCH_CODE)));
                orderData.setMrp(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_MRP_CAPS))));
                orderData.setQuantity(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));
                orderData.setUomId(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_UOM_ID)));
                orderData.setSellPrice(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                orderData.setOrderValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                orderData.setPrimDiscOrderValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE))));
                orderData.setTax(cursorOrder.getDouble(cursorOrder.getColumnIndex(TAX_PER_PRODUCT)));
                orderData.setSchemeAmount(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(DISCOUNT_AMOUNT))));
                orderData.setCgstperc(cursorOrder.getDouble(cursorOrder.getColumnIndex(CGST_PERCENT)));
                orderData.setCgstvalue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(CGST_VALUE))));
                orderData.setSgstPerc(cursorOrder.getDouble(cursorOrder.getColumnIndex(SGST_PERCENT)));
                orderData.setSgstValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(SGST_VALUE))));
                orderData.setTotalAmount(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(TOTAL_AMOUNT))));
                orderedProdList.add(orderData);
            }
        }
        closeCursor(cursorOrder);
        sfaDatabase.closeDb();
        return orderedProdList;
    }


    public List<OrderBookingVO> getDaySummaryOrderAllValue(SFADatabase sfaDatabase,

                                                           String distrcode, String salesmancode,

                                                           String strRouteCode) {



        List<OrderBookingVO> retailerCreditList = new ArrayList<>();



        String query;



        if (strRouteCode != null && !strRouteCode.equalsIgnoreCase("0")) {

            query = " SELECT r.customercode       AS customerCode,\n" +

                    "       r.customername       AS customerName,\n" +

                    "       r.retailerAddr1       AS retailerAddr1,\n" +

                    "       r.channelcode        AS channelCode,\n" +

                    "       o.invoiceno          AS invoiceNo,\n" +

                    "       o.distrCode as distrCode,\n" +

                    "       r.routecode          AS routeCode,\n" +

                    "       rt.routename         AS routeName,\n" +

                    "       r.rtrMobileNo       AS rtrMobileNo,\n" +

                    "       Sum(o.OrderValue)     AS OrderValue,\n" +

                    "       Sum(o.totalamount)   AS totalordervalue,\n" +

                    "       Sum(o.grossAmount)   AS grossAmount,\n" +

                    "       Sum(o.primaryDiscOrderValue)   AS primaryDiscOrderValue,\n" +

                    "       Sum(o.orderqty)      AS OrderQty,\n" +

                    "       o.uomid              AS uomId,\n\n" +

                    "       o.CGSTPerc              AS CGSTPerc,\n" +

                    "       Sum(o.CGSTValue)             AS CGSTValue,\n" +

                    "       o.SGSTPerc              AS SGSTPerc,\n" +

                    "       Sum(o.SGSTValue)              AS SGSTValue,\n" +

                    "       o.UTGSTPerc              AS UTGSTPerc,\n" +

                    "       Sum(o.UTGSTValue)              AS UTGSTValue,\n" +

                    "       o.IGSTPerc              AS IGSTPerc,\n" +

                    "       Sum(o.IGSTValue)              AS IGSTValue," +

                    "       o.OrderDate              AS OrderDate,\n" +

                    "       o.upload              AS upload,\n" +

                    "       Sum(o.schemeamount)  AS discountAmt,\n" +

                    "       Sum(o.taxperproduct) AS taxAmt,\n" +

                    "       Count(*)             AS productcount\n" +

                    "FROM   " + TABLE_ORDER_BOOKING + " o\n" +

                    "       INNER JOIN " + TABLE_RETAILER + " r\n" +

                    "               ON o.retlrcode\n" +

                    "                  AND o.distrcode\n" +

                    "       INNER JOIN " + TABLE_ROUTE + " rt\n" +

                    "               ON rt.distrcode\n" +

                    "                  AND rt.routecode\n" +

                    "GROUP  BY r.customercode \n" +

                    "UNION\n" +

                    "SELECT r.customercode       AS customerCode,\n" +

                    "       r.customername       AS customerName,\n\n" +

                    "       r.address1       AS retailerAddr1,\n" +

                    "       r.channelcode        AS channelCode,\n" +

                    "       o.invoiceno          AS invoiceNo,\n" +

                    "       o.distrCode as distrCode,\n" +

                    "       r.routecode          AS routeCode,\n" +

                    "       rt.routename         AS routeName,\n" +

                    "       r.mobileNumber       AS rtrMobileNo,\n" +

                    "       Sum(o.OrderValue)     AS OrderValue,\n" +

                    "       Sum(o.totalamount)   AS totalordervalue,\n" +

                    "       Sum(o.grossAmount)   AS grossAmount,\n" +

                    "       Sum(o.primaryDiscOrderValue)   AS primaryDiscOrderValue,\n" +

                    "       Sum(o.orderqty)      AS OrderQty,\n" +

                    "       o.uomid              AS uomId,\n" +

                    "       o.CGSTPerc              AS CGSTPerc,\n" +

                    "       Sum(o.CGSTValue)             AS CGSTValue,\n" +

                    "       o.SGSTPerc              AS SGSTPerc,\n" +

                    "       Sum(o.SGSTValue)              AS SGSTValue,\n" +

                    "       o.UTGSTPerc              AS UTGSTPerc,\n" +

                    "       Sum(o.UTGSTValue)              AS UTGSTValue,\n" +

                    "       o.IGSTPerc              AS IGSTPerc,\n" +

                    "       Sum(o.IGSTValue)              AS IGSTValue," +

                    "       o.OrderDate              AS OrderDate,\n" +

                    "       o.upload              AS upload,\n" +

                    "       Sum(o.schemeamount)  AS discountAmt,\n" +

                    "       Sum(o.taxperproduct) AS taxAmt,\n" +

                    "       Count(*)             AS productcount\n" +

                    "FROM   " + TABLE_ORDER_BOOKING + " o\n" +

                    "       INNER JOIN " + TABLE_NEW_RETAILER + " r\n" +

                    "               ON o.retlrcode\n" +

                    "                  AND o.distrcode\n" +

                    "       INNER JOIN " + TABLE_ROUTE + " rt\n" +

                    "               ON rt.distrcode\n" +

                    "                  AND rt.routecode\n" +

                    "GROUP  BY r.customercode  ORDER BY upload ASC";



        } else {



            query = " SELECT o.cmpCode AS cmpCode,\n" +

                    "       o.distrCode AS distrCode,\n" +

                    "       o.OrderRefNo AS OrderRefNo,\n" +

                    "       o.readableInvNo AS readableInvNo,\n" +

                    "       o.invoiceno AS invoiceNo,\n" +

                    "       o.OrderDate AS OrderDate,\n" +

                    "       r.customercode AS customerCode,\n" +

                    "       r.customername AS customerName,\n" +

                    "       r.channelcode AS channelCode,\n" +

                    "       r.MobileNo AS rtrMobileNo,\n" +

                    "       csa.customerShipAddr1 AS retailerAddr1,\n" +

                    "       SUM(o.orderqty) AS OrderQty,\n" +

                    "       o.uomid AS uomId,\n" +

                    "       SUM(o.OrderValue) AS OrderValue,\n" +

                    "       SUM(o.totalAmount) AS totalOrderValue,\n" +

                    "       SUM(o.grossAmount) AS grossAmount,\n" +

                    "       SUM(o.primaryDiscOrderValue) AS primaryDiscOrderValue,\n" +

                    "       o.CGSTPerc AS CGSTPerc,\n" +

                    "       SUM(o.CGSTValue) AS CGSTValue,\n" +

                    "       o.SGSTPerc AS SGSTPerc,\n" +

                    "       SUM(o.SGSTValue) AS SGSTValue,\n" +

                    "       o.UTGSTPerc AS UTGSTPerc,\n" +

                    "       Sum(o.UTGSTValue) AS UTGSTValue,\n" +

                    "        o.IGSTPerc AS IGSTPerc,\n" +

                    "       SUM(o.IGSTValue) AS IGSTValue,\n" +

                    "       SUM(o.schemeamount) AS discountAmt,\n" +

                    "       SUM(o.taxperproduct) AS taxAmt,\n" +

                    "       COUNT(*)AS productcount,\n" +

                    "       o.upload AS upload\n" +

                    "FROM   " + TABLE_ORDER_BOOKING + " o\n" +

                    "       INNER JOIN " + TABLE_CUSTOMER + " r\n" +

                    "               ON o.cmpCode = r.cmpCode\n" +

                    "                  AND o.distrcode = r.distrcode\n" +

                    "                  AND o.retlrcode = r.customercode\n" +

                    "       INNER JOIN " + TABLE_CUSTOMER_SHIP_ADDRESS + " csa\n" +

                    "               ON o.cmpCode = csa.cmpCode\n" +

                    "                  AND o.distrCode = csa.distrCode\n" +

                    "                  AND o.retlrcode = csa.customercode\n" +

                    "                  AND o.customershipcode = csa.customershipcode\n" +

                    "WHERE  o.completeflag = 'Y'\n" +

                    "GROUP  BY o.cmpCode ,o.distrCode ,o.readableInvNo ,o.invoiceno ,o.OrderDate ,r.customercode";



        }



        Cursor cursorOrder = sfaDatabase.getDb().rawQuery(query, null);

        if (cursorOrder != null && cursorOrder.getCount() > 0) {

            while (cursorOrder.moveToNext()) {

                OrderBookingVO orderData = new OrderBookingVO();

                orderData.setDistrCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_DISTR_CODE)));

                orderData.setRetailerCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_CODE)));

                orderData.setRetailerName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_NAME)));

                orderData.setRouteCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CHANNEL_CODE)));

                orderData.setRouteName("");

                orderData.setAddress(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_RETAILER_ADDR1)));

                orderData.setMobileNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_RTR_MOBILE_NO)));

                orderData.setOrderDate(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ORDER_DATE_CAPS)));

                orderData.setOrderValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));

                orderData.setTotalAmount(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_TOTAL_ORDER_VALUE))));

                orderData.setPrimDiscOrderValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE))));

                orderData.setUomId(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_UOM_ID)));

                orderData.setCgstperc(cursorOrder.getInt(cursorOrder.getColumnIndex(CGST_PERCENT)));

                orderData.setCgstvalue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(CGST_VALUE))));

                orderData.setSgstPerc(cursorOrder.getInt(cursorOrder.getColumnIndex(SGST_PERCENT)));

                orderData.setSgstValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(SGST_VALUE))));

                orderData.setIgstPerc(cursorOrder.getInt(cursorOrder.getColumnIndex(IGST_PERCENT)));

                orderData.setIgstvalue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(IGST_VALUE))));

                orderData.setUtgstPerc(cursorOrder.getInt(cursorOrder.getColumnIndex(UTGST_PERCENT)));

                orderData.setUtgstValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(UTGST_VALUE))));

                orderData.setNoOfItems(cursorOrder.getInt(cursorOrder.getColumnIndex(COLUMN_PRODUCT_COUNT)));

                orderData.setOrderInvoiceNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_INVOICE_NO)));

                orderData.setSchemeAmount(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex("discountAmt"))));

                orderData.setTax(cursorOrder.getDouble(cursorOrder.getColumnIndex("taxAmt")));

                orderData.setGrnStatus("");

                orderData.setlGrossAmt(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(GROSS_AMOUNT))));
                orderData.setReadableInvNo(cursorOrder.getString(cursorOrder.getColumnIndex(READABLE_INV_NO)));


                orderData.setOrderNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ORDER_REF_NO_CAPS)));

                orderData.setCmpCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CMP_CODE)));





//                String chCode = getChannelNameBasedonCode(sfaDatabase, cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());

                orderData.setChannelName("");



                retailerCreditList.add(orderData);

            }

        }

        closeCursor(cursorOrder);

        sfaDatabase.closeDb();

        return retailerCreditList;

    }


    public List<OrderBookingVO> getVANSalesDaySummaryData(SFADatabase sfaDatabase, String distrcode, String salesmancode, String strRouteCode) {

        List<OrderBookingVO> retailerCreditList = new ArrayList<>();

        String query;

        query = getQueryVanSalesDaySummary(distrcode, salesmancode, strRouteCode);

        Cursor cursorOrder = sfaDatabase.getDb().rawQuery(query, null);
        if (cursorOrder != null && cursorOrder.getCount() > 0) {
            while (cursorOrder.moveToNext()) {
                OrderBookingVO orderData = new OrderBookingVO();

                orderData.setDistrCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_DISTR_CODE)));
                orderData.setRetailerCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                orderData.setRetailerName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_NAME)));
                orderData.setRouteCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ROUTE_CODE)));
                orderData.setRouteName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ROUTE_NAME)));
                orderData.setAddress(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_RETAILER_ADDR1)));
                orderData.setMobileNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_RTR_MOBILE_NO)));
                orderData.setOrderDate(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ORDER_DATE_CAPS)));
                orderData.setOrderValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                orderData.setTotalAmount(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_TOTAL_ORDER_VALUE))));
                orderData.setPrimDiscOrderValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE))));
                orderData.setUomId(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_UOM_ID)));
                orderData.setCgstperc(cursorOrder.getInt(cursorOrder.getColumnIndex(CGST_PERCENT)));
                orderData.setCgstvalue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(CGST_VALUE))));
                orderData.setSgstPerc(cursorOrder.getInt(cursorOrder.getColumnIndex(SGST_PERCENT)));
                orderData.setSgstValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(SGST_VALUE))));
                orderData.setIgstPerc(cursorOrder.getInt(cursorOrder.getColumnIndex(IGST_PERCENT)));
                orderData.setIgstvalue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(IGST_VALUE))));
                orderData.setUtgstPerc(cursorOrder.getInt(cursorOrder.getColumnIndex(UTGST_PERCENT)));
                orderData.setUtgstValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(UTGST_VALUE))));
                orderData.setNoOfItems(cursorOrder.getInt(cursorOrder.getColumnIndex(COLUMN_PRODUCT_COUNT)));
                orderData.setOrderInvoiceNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_INVOICE_NO)));
                orderData.setSchemeAmount(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex("discountAmt"))));
                orderData.setTax(cursorOrder.getDouble(cursorOrder.getColumnIndex("taxAmt")));
                orderData.setGrnStatus(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_UPLOAD)));

                String chCode = getChannelNameBasedonCode(sfaDatabase, cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                orderData.setChannelName(chCode);

                retailerCreditList.add(orderData);
            }
        }
        closeCursor(cursorOrder);
        sfaDatabase.closeDb();
        return retailerCreditList;
    }
}
