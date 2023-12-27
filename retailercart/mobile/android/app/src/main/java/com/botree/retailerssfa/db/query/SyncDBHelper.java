/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.db.query;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.db.BaseDB;
import com.botree.retailerssfa.db.IDbCallback;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.AddressModel;
import com.botree.retailerssfa.models.BannerModel;
import com.botree.retailerssfa.models.BranchStockModel;
import com.botree.retailerssfa.models.BulletinBoardModel;
import com.botree.retailerssfa.models.CodeGeneratorModel;
import com.botree.retailerssfa.models.CollectionVO;
import com.botree.retailerssfa.models.CompanyModel;
import com.botree.retailerssfa.models.ConfigModel;
import com.botree.retailerssfa.models.ContractPricingModel;
import com.botree.retailerssfa.models.CustProdMappingModel;
import com.botree.retailerssfa.models.CustomerBankModel;
import com.botree.retailerssfa.models.CustomerModel;
import com.botree.retailerssfa.models.CustomerStockModel;
import com.botree.retailerssfa.models.DistAddressModel;
import com.botree.retailerssfa.models.DistBalanceModel;
import com.botree.retailerssfa.models.DistrReportModel;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.EditRateModel;
import com.botree.retailerssfa.models.EncodeModel;
import com.botree.retailerssfa.models.FeedBackMasterModel;
import com.botree.retailerssfa.models.GSTModel;
import com.botree.retailerssfa.models.GeoHierModel;
import com.botree.retailerssfa.models.KycModel;
import com.botree.retailerssfa.models.LobModel;
import com.botree.retailerssfa.models.LogisticStockModel;
import com.botree.retailerssfa.models.MessageModel;
import com.botree.retailerssfa.models.OrderBookingDetailsModel;
import com.botree.retailerssfa.models.OrderBookingHeaderModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.OrderStatusModel;
import com.botree.retailerssfa.models.PendingBillCollection;
import com.botree.retailerssfa.models.PendingBillVO;
import com.botree.retailerssfa.models.PreviousPOModel;
import com.botree.retailerssfa.models.ProFilterModel;
import com.botree.retailerssfa.models.ProdBatchModel;
import com.botree.retailerssfa.models.ProdHierLvlModel;
import com.botree.retailerssfa.models.ProductMasterModel;
import com.botree.retailerssfa.models.ProductModel;
import com.botree.retailerssfa.models.ProductsVO;
import com.botree.retailerssfa.models.PurchaseInvoiceModel;
import com.botree.retailerssfa.models.ReasonVO;
import com.botree.retailerssfa.models.RetailerCategoryVO;
import com.botree.retailerssfa.models.RetailerVO;
import com.botree.retailerssfa.models.RouteModel;
import com.botree.retailerssfa.models.SalesDBCRNoteModel;
import com.botree.retailerssfa.models.SalesReturnVO;
import com.botree.retailerssfa.models.SalesmanMasterModel;
import com.botree.retailerssfa.models.Scheme;
import com.botree.retailerssfa.models.SchemeCustomerZipModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.models.StockOnHandModel;
import com.botree.retailerssfa.models.StockledgerReportModel;
import com.botree.retailerssfa.models.SubStockistModel;
import com.botree.retailerssfa.models.SupplierModel;
import com.botree.retailerssfa.models.Sync;
import com.botree.retailerssfa.models.TaxMasterModel;
import com.botree.retailerssfa.models.TaxModel;
import com.botree.retailerssfa.models.TimeCaptureModel;
import com.botree.retailerssfa.models.UomMasterModel;
import com.botree.retailerssfa.models.UomMasterVO;
import com.botree.retailerssfa.models.WDInfrastructure;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.botree.retailerssfa.db.BaseDB.checkLong;
import static com.botree.retailerssfa.db.BaseDB.checkString;
import static com.botree.retailerssfa.db.query.IDBColumns.*;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TOTAL_DISCOUNT;
import static com.botree.retailerssfa.util.SFASharedPref.CURRENT_SESSION;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRBRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DIST_ADDRESS;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;
import static com.botree.retailerssfa.util.SFASharedPref.getOurInstance;

public class SyncDBHelper {

    private static final String TAG = SyncDBHelper.class.getSimpleName();
    private static final String DUPLICATE_TAG = "Duplicate";

    private SQLiteDatabase db;

    public SyncDBHelper() {

    }

    SyncDBHelper(BaseDB baseDB) {
        db = baseDB.getDb();
    }

    public void syncDataInsert(final Sync sync, final BaseDB baseDB, final IDbCallback<Boolean> callback) {
        db = baseDB.getDb();
        try {
            db.beginTransaction();
            bulkInsert(sync);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "syncDataInsert: " + e.getMessage(), e);
        } finally {
            db.endTransaction();
        }

        baseDB.closeDb();
        if (callback != null) {
            callback.response(true);
            callback.onSuccess();
        }
    }


    public void syncDataInsertProductUomTaxBranchBatchMaster(final Sync sync, final BaseDB baseDB, final IDbCallback<Boolean> callback) {
        db = baseDB.getDb();
        try {
            db.beginTransaction();
            bulkInsertProductMaster(sync);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "syncDataInsert: " + e.getMessage(), e);
        } finally {
            db.endTransaction();
        }

        baseDB.closeDb();
        if (callback != null) {
            callback.response(true);
            callback.onSuccess();
        }
    }

    private void bulkInsertProductMaster(Sync sync) {
        if (sync.getProductMasterModelList() != null) {
            insertProductMasterList(sync.getProductMasterModelList());
        }

        if (sync.getBranchStockList() != null) {
            insertBranchStockList(sync.getBranchStockList());
        }

        if (sync.getUomMasterModelList() != null) {
            insertProductUomMasterList(sync.getUomMasterModelList());
        }

        if (sync.getTaxMasterModelList() != null) {
            insertTaxMasterList(sync.getTaxMasterModelList());
        }

        if (sync.getProdBatchModelList() != null) {
            insertProductBatchMasterList(sync.getProdBatchModelList());
        }

        if (sync.getStockOnHandList() != null) {
            insertStockOnHandList(sync.getStockOnHandList());
        }

        if (sync.getProFilterModelList() != null) {
            insertProductsFilterList(sync.getProFilterModelList());
        }
    }

    public void insertSyncReportData(final Sync sync, final BaseDB baseDB, final IDbCallback<Boolean> callback) {
        db = baseDB.getDb();
        try {
            db.beginTransaction();
            bulkReportInsert(sync);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "insertSyncReportData: " + e.getMessage(), e);
        } finally {
            db.endTransaction();
        }


        baseDB.closeDb();
        if (callback != null) {
            callback.response(true);
            callback.onSuccess();
        }
    }

    public void insertRoute(List<RouteModel> list) {
        String sql = "INSERT INTO " + TABLE_ROUTE + " VALUES (?,?,?,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, list.get(i).getCompanyCode());
                statement.bindString(2, list.get(i).getDistributorCode());
                statement.bindString(3, list.get(i).getRouteCode());
                statement.bindString(4, list.get(i).getRouteName());
                statement.bindString(5, list.get(i).getRouteType());
                statement.bindString(6, "Y");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    public void insertMappedData(List<String> list, String tableName) {
        String sql = "INSERT INTO " + tableName + " VALUES (?);";

        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, list.get(i));

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    public void insertRoute(SFADatabase db, List<RouteModel> list) {
        String sql = "INSERT INTO " + TABLE_ROUTE + " VALUES (?,?,?,?,?,?);";

        SQLiteStatement statement = db.getDb().compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, list.get(i).getCompanyCode());
                statement.bindString(2, list.get(i).getDistributorCode());
                statement.bindString(3, list.get(i).getRouteCode());
                statement.bindString(4, list.get(i).getRouteName());
                statement.bindString(5, list.get(i).getRouteType());
                statement.bindString(6, "N");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }


    private void insertTaxStructureData(List<TaxModel> taxModels) {
        String sql = "INSERT INTO " + TABLE_TAX_STRUCTURE + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < taxModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, taxModels.get(i).getCmpCode());
                statement.bindString(2, taxModels.get(i).getDistrCode());
                statement.bindString(3, taxModels.get(i).getProdCode());
                statement.bindString(4, taxModels.get(i).getProdHierPath());
                statement.bindString(5, taxModels.get(i).getProdHierPathName());
                statement.bindString(6, taxModels.get(i).getTaxState());
                statement.bindString(7, taxModels.get(i).getTaxType());
                statement.bindString(8, taxModels.get(i).getTaxCode());
                statement.bindString(9, taxModels.get(i).getTaxDesc());
                statement.bindString(10, taxModels.get(i).getTaxName());
                statement.bindString(11, taxModels.get(i).getTaxEffectiveFrom());
                statement.bindString(12, taxModels.get(i).getSchemeReduce());
                statement.bindString(13, taxModels.get(i).getCashDiscount());
                statement.bindString(14, taxModels.get(i).getDbDiscount());
                statement.bindString(15, String.valueOf(taxModels.get(i).getInputTaxPerc()));
                statement.bindString(16, taxModels.get(i).getInputApplyOn());
                statement.bindString(17, String.valueOf(taxModels.get(i).getOutputTaxPerc()));
                statement.bindString(18, taxModels.get(i).getInputApplyOn());
                statement.bindString(19, taxModels.get(i).getUploadFlag());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertPendingBill(List<PendingBillVO> list) {
        String sql = "INSERT INTO " + TABLE_PENDING_BILL + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, list.get(i).getCmpCode());
                statement.bindString(2, list.get(i).getDistrCode());
                statement.bindString(3, list.get(i).getSalesmanCode());
                statement.bindString(4, list.get(i).getSalesmanName());
                statement.bindString(5, list.get(i).getCustomerCode());
                statement.bindString(6, list.get(i).getCustomerName());
                statement.bindString(7, list.get(i).getInvoiceNo());
                statement.bindString(8, list.get(i).getInvoiceDate());
                statement.bindDouble(9, list.get(i).getTotNetAmt());
                statement.bindDouble(10, list.get(i).getPendingAmount());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    /*private void insertRetailer(List<RetailerVO> list) {
        String sql = "INSERT INTO " + TABLE_RETAILER + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();

                statement.bindString(1, list.get(i).getCmpCode());
                statement.bindString(2, list.get(i).getDistrCode());
                statement.bindString(3, list.get(i).getSalesmanCode());
                statement.bindString(4, list.get(i).getRouteCode());
                statement.bindString(5, String.valueOf(list.get(i).isTodayBeat()));
                statement.bindString(6, list.get(i).getCustomerCode());
                statement.bindString(7, list.get(i).getCustomerName());
                statement.bindString(8, list.get(i).getRetailerAddr1());
                statement.bindString(9, list.get(i).getChannelCode());
                statement.bindString(10, list.get(i).getSubChannelCode());
                statement.bindString(11, list.get(i).getGroupCode());
                statement.bindString(12, list.get(i).getClassCode());
                statement.bindLong(13, list.get(i).getCreditDays());
                statement.bindDouble(14, list.get(i).getCreditLimit());
                statement.bindString(15, list.get(i).getIsVisit());
                statement.bindString(16, list.get(i).getLatitude());
                statement.bindString(17, list.get(i).getLongitude());
                statement.bindString(18, list.get(i).getMobile());
                statement.bindString(19, list.get(i).getSeqNo());
                statement.bindString(20, list.get(i).getGstNo());
                statement.bindString(21, list.get(i).getGstStateCode());
                statement.bindString(22, list.get(i).getPanNo());
                statement.bindString(23, list.get(i).getGstType());
                statement.bindString(24, list.get(i).getUnLockCode());
                statement.bindString(25, "N");
                statement.bindString(26, "");
                statement.bindString(27, "N");
                statement.bindString(28, "");
                statement.bindString(29, list.get(i).getLicenseExpDate());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }*/

    private void insertRetailerVisit(List<RetailerVO> list) {
        String sql = "INSERT INTO " + TABLE_RETAILER_VISIT + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();

                statement.bindString(1, list.get(i).getDistrCode());
                statement.bindString(2, list.get(i).getSalesmanCode());
                statement.bindString(3, list.get(i).getRouteCode());
                statement.bindString(4, list.get(i).getCustomerCode());
                statement.bindString(5, list.get(i).getTodayBeat());
                statement.bindString(6, list.get(i).getOrderVisit());
                statement.bindString(7, list.get(i).getReturnVisit());
                statement.bindString(8, list.get(i).getStockVisit());
                statement.bindString(9, list.get(i).getBillVisit());
                statement.bindString(10, list.get(i).getCollectionVisit());
                statement.bindString(11, list.get(i).getSurveyVisit());
                statement.bindString(12, list.get(i).getSyncVisit());
                statement.bindString(13, list.get(i).getLatitude());
                statement.bindString(14, list.get(i).getLongitude());
                statement.bindString(15, "");
                statement.bindString(16, "");
                statement.bindString(17, "");
                statement.bindString(18, "");
                statement.bindString(19, "");
                statement.bindString(20, list.get(i).getCoverageDate());
                statement.bindString(21, list.get(i).getIsVisit());
                statement.bindString(22, "N");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }


        }
    }

    private void insertCustomerUploadStatus(List<RetailerVO> list) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER_UPLOAD_STATUS + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, list.get(i).getDistrCode());
                statement.bindString(2, list.get(i).getSalesmanCode());
                statement.bindString(3, list.get(i).getRouteCode());
                statement.bindString(4, list.get(i).getCustomerCode());
                statement.bindString(5, list.get(i).getOrderUploaded());
                statement.bindString(6, list.get(i).getReturnsUploaded());
                statement.bindString(7, list.get(i).getStockCaptureUploaded());
                statement.bindString(8, list.get(i).getCollectionsUploaded());
                statement.bindString(9, list.get(i).getBillUploaded());
                statement.bindString(10, list.get(i).getSurveyUploaded());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }


        }
    }

    private void insertSalesmanTracker(List<RetailerVO> list) {
        String sql = "INSERT INTO " + TABLE_SALESMAN_TRACKER + " VALUES (?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, list.get(i).getDistrCode());
                statement.bindString(2, list.get(i).getSalesmanCode());
                statement.bindString(3, list.get(i).getRouteCode());
                statement.bindString(4, list.get(i).getCustomerCode());
                statement.bindLong(5, list.get(i).getStartTime());
                statement.bindLong(6, list.get(i).getEndTime());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertRetailerCategoryId(List<RetailerVO> list) {
        String sql = "INSERT INTO " + TABLE_RETAILER_CATEGORY_ID + " VALUES (?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();

                statement.bindString(1, list.get(i).getDistrCode());
                statement.bindString(2, list.get(i).getSalesmanCode());
                statement.bindString(3, list.get(i).getRouteCode());
                statement.bindString(4, list.get(i).getClassCode());
                statement.bindString(5, list.get(i).getCustomerCode());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertProducts(List<ProductsVO> productsList) {
        String sql = "INSERT INTO " + TABLE_PRODUCTS + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < productsList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, productsList.get(i).getSalesmanCodeCaps());
                statement.bindString(2, productsList.get(i).getDistrCodeCaps());
                statement.bindString(3, productsList.get(i).getCmpCodeCaps());
                statement.bindString(4, productsList.get(i).getProdBatchCode());
                statement.bindString(5, productsList.get(i).getProdCodeCaps());
                statement.bindString(6, productsList.get(i).getProdHierValCodeCaps());
                statement.bindString(7, productsList.get(i).getProdHierValNameCaps());
                statement.bindString(8, productsList.get(i).getProdHierPath());
                statement.bindString(9, productsList.get(i).getProdHierPathName());
                statement.bindString(10, productsList.get(i).getProdNameCaps());
                statement.bindString(11, productsList.get(i).getProdShortNameCaps());
                statement.bindDouble(12, productsList.get(i).getSellPriceCaps());
                statement.bindDouble(13, productsList.get(i).getmRPCaps());
                statement.bindDouble(14, productsList.get(i).getPrimaryDisc());
                statement.bindDouble(15, productsList.get(i).getStockInHandCaps());
                statement.bindString(16, productsList.get(i).getProdCodeCaps());
                statement.bindString(17, productsList.get(i).getBrandCode());
                statement.bindString(18, productsList.get(i).getBrandName());
                statement.bindString(19, productsList.get(i).getCategoryCode());
                statement.bindString(20, productsList.get(i).getCategoryName());
                statement.bindString(21, productsList.get(i).getProdNetWgt());
                statement.bindString(22, productsList.get(i).getProdWgtType());
                statement.bindDouble(23, productsList.get(i).getPurchPrice());
                statement.bindString(24, productsList.get(i).getDefaultUomidCaps());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertVanSalesProducts(List<ProductsVO> productsList) {
        String sql = "INSERT INTO " + TABLE_VANSALES_PRODUCTS + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < productsList.size(); i++) {
            try {
                String[] hierPathCode = productsList.get(i).getProdHierPath().split("/");
                String[] hierPathName = productsList.get(i).getProdHierPathName().split("/");

                statement.clearBindings();
                statement.bindString(1, productsList.get(i).getSalesmanCodeCaps());
                statement.bindString(2, productsList.get(i).getDistrCodeCaps());
                statement.bindString(3, productsList.get(i).getCmpCodeCaps());
                statement.bindString(4, productsList.get(i).getProdBatchCode());
                statement.bindString(5, productsList.get(i).getProdCodeCaps());
                statement.bindString(6, productsList.get(i).getProdHierValCodeCaps());
                statement.bindString(7, productsList.get(i).getProdHierValNameCaps());
                statement.bindString(8, productsList.get(i).getProdHierPath());
                statement.bindString(9, productsList.get(i).getProdHierPathName());
                statement.bindString(10, productsList.get(i).getProdNameCaps());
                statement.bindString(11, productsList.get(i).getProdShortNameCaps());
                statement.bindDouble(12, productsList.get(i).getSellPriceCaps());
                statement.bindDouble(13, productsList.get(i).getmRPCaps());
                statement.bindDouble(14, productsList.get(i).getPrimaryDisc());
                statement.bindDouble(15, productsList.get(i).getStockInHandCaps());
                statement.bindString(16, productsList.get(i).getProdCodeCaps());
                statement.bindString(17, hierPathCode[hierPathCode.length - 2]);//Brand Code
                statement.bindString(18, hierPathName[hierPathCode.length - 2]);//Brand Name
                statement.bindString(19, hierPathCode[hierPathCode.length - 3]);//Category Code
                statement.bindString(20, hierPathName[hierPathCode.length - 3]);//Category Name
                statement.bindString(21, productsList.get(i).getProdNetWgt());
                statement.bindString(22, productsList.get(i).getProdWgtType());
                statement.bindDouble(23, productsList.get(i).getPurchPrice());
                statement.bindString(24, productsList.get(i).getDefaultUomidCaps());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertProductsFilterList(List<ProFilterModel> productsList) {
        String sql = "INSERT INTO " + TABLE_ORDER_PRODUCT_FILTERS + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        if (productsList.get(0).getPathCode().length > 2)
            for (int i = 0; i < productsList.size(); i++) {
                try {
                    statement.clearBindings();
                    statement.bindString(2, productsList.get(i).getPathCode()[3]);
                    statement.bindString(3, productsList.get(i).getPathName()[3]);
                    statement.bindString(4, productsList.get(i).getPathCode()[2]);
                    statement.bindString(5, productsList.get(i).getPathName()[2]);
                    statement.bindString(6, productsList.get(i).getPathCode()[1]);
                    statement.bindString(7, productsList.get(i).getPathName()[1]);
//                    statement.bindString(8, productsList.get(i).getPathCode()[1]);
//                    statement.bindString(9, productsList.get(i).getPathName()[1]);
                    statement.bindString(10, productsList.get(i).getLobDivisionCode());

                    statement.execute();
                } catch (SQLiteException e) {
                    if (BuildConfig.DEBUG)
                        Log.w(DUPLICATE_TAG, e);
                }
            }
    }

    private void insertPurchaseReceipt(List<PurchaseInvoiceModel> purchaseReceiptList) {
        String sql = "INSERT INTO " + TABLE_PURCHASE_INVOICE + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < purchaseReceiptList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(purchaseReceiptList.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(purchaseReceiptList.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(purchaseReceiptList.get(i).getInvoiceNo()));
                statement.bindString(4, Globals.checkNull(purchaseReceiptList.get(i).getGrnNo()));
                statement.bindString(5, Globals.checkNull(purchaseReceiptList.get(i).getInvoiceDt()));
                statement.bindString(6, Globals.checkNull(purchaseReceiptList.get(i).getGrnDt()));
                statement.bindString(7, Globals.checkNull(purchaseReceiptList.get(i).getSupplierCode()));
                statement.bindString(8, Globals.checkNull(purchaseReceiptList.get(i).getProdCode()));
                statement.bindString(9, Globals.checkNull(purchaseReceiptList.get(i).getProdBatchCode()));
                statement.bindString(10, Globals.checkNull(String.valueOf(purchaseReceiptList.get(i).getPurchPrice())));
                statement.bindString(11, Globals.checkNull(String.valueOf(purchaseReceiptList.get(i).getMrp())));
                statement.bindString(12, Globals.checkNull(String.valueOf(purchaseReceiptList.get(i).getInvoiceQty())));
                statement.bindString(13, Globals.checkNull(String.valueOf(purchaseReceiptList.get(i).getReceivedQty())));
                statement.bindString(14, Globals.checkNull(purchaseReceiptList.get(i).getUomCode()));
                statement.bindString(15, Globals.checkNull(String.valueOf(purchaseReceiptList.get(i).getLineLevelGrossAmt())));
                statement.bindString(16, Globals.checkNull(String.valueOf(purchaseReceiptList.get(i).getLineLevelTaxAmt())));
                statement.bindString(17, Globals.checkNull(String.valueOf(purchaseReceiptList.get(i).getLineLevelDiscAmt())));
                statement.bindString(18, Globals.checkNull(String.valueOf(purchaseReceiptList.get(i).getLineLevelNetAmt())));
                statement.bindString(19, Globals.checkNull(purchaseReceiptList.get(i).getGrnStatus()));
                statement.bindString(20, "N");
                statement.bindString(21, Globals.checkNull(purchaseReceiptList.get(i).getConfirmDate()));
                statement.bindString(22, Globals.checkNull(purchaseReceiptList.get(i).getModDt()));
//                statement.bindString(21, ApUtils.checkNull(purchaseReceiptList.get(i).getUploadFlag()));
                statement.bindString(23, "Y");
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertLogisticMaterialStock(List<LogisticStockModel> logisticStockModels) {
        String sql = "INSERT INTO " + TABLE_LOGISTIC_MATERIAL_STOCK + " VALUES (?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < logisticStockModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(logisticStockModels.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(logisticStockModels.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(logisticStockModels.get(i).getInvoiceNo()));
                statement.bindString(4, Globals.checkNull(logisticStockModels.get(i).getMaterialCode()));
                statement.bindString(5, Globals.checkNull(logisticStockModels.get(i).getMaterialName()));
                statement.bindString(6, Globals.checkNull(logisticStockModels.get(i).getReceivedQty()));
                statement.bindString(7, Globals.checkNull(logisticStockModels.get(i).getReturnedQty()));
                statement.bindString(8, Globals.checkNull(logisticStockModels.get(i).getModDt()));
//                statement.bindString(9, AppUtils.checkNull(logisticStockModels.get(i).getUploadFlag()));
                statement.bindString(9, "N");
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertBankNames(List<CollectionVO> list) {
        String sql = "INSERT INTO " + TABLE_BANK_NAMES + " VALUES (?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        SFASharedPref pref = SFASharedPref.getOurInstance();
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(2, list.get(i).getCmpCode());
                statement.bindString(3, list.get(i).getDistrCode());
                statement.bindString(4, pref.readString(PREF_SALESMANCODE));
                statement.bindString(5, list.get(i).getBankCode());
                statement.bindString(6, list.get(i).getBankName());
                statement.bindString(7, list.get(i).getBankBranchCode());
                statement.bindString(8, list.get(i).getBankBranchName());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertProductSuggestions(List<ProductsVO> productsList) {
        String sql = "INSERT INTO " + TABLE_PRODUCT_SUGGESTIONS + " VALUES (?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < productsList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, productsList.get(i).getCmpCodeCaps());
                statement.bindString(2, productsList.get(i).getDistrCodeCaps());
                statement.bindString(3, productsList.get(i).getRetlrCodeCaps());
                statement.bindString(4, productsList.get(i).getProdCodeCaps());
                statement.bindLong(5, productsList.get(i).getSuggestedQuantityCaps());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }


    private void insertSchemeCombiProductsList(List<SchemeModel> schemeModels) {

        String sql = "INSERT INTO " + TABLE_SCHEME_COMBI_PRODUCTS + " VALUES (?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < schemeModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, schemeModels.get(i).getCmpCode());
            statement.bindString(2, schemeModels.get(i).getSchemeCode());
            statement.bindString(3, schemeModels.get(i).getSlabNo());
            statement.bindString(4, schemeModels.get(i).getProductCode());
            statement.bindString(5, schemeModels.get(i).getProdName());
            statement.bindString(6, String.valueOf(schemeModels.get(i).getMinValue()));
            statement.bindString(7, schemeModels.get(i).getIsMandatory());
            statement.bindString(8, schemeModels.get(i).getUploadFlag());

            statement.execute();
        }
    }

    private void insertFocusBrandProductList(List<ProductModel> productModels) {

        String sql = "INSERT INTO " + TABLE_FOCUSBRAND_PRODUCTS + " VALUES (?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < productModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(2, productModels.get(i).getCmpCode());
            statement.bindString(3, productModels.get(i).getDistCode());
            statement.bindString(4, productModels.get(i).getRefrenceNo());
            statement.bindString(5, productModels.get(i).getProdCode());
            statement.bindString(6, productModels.get(i).getUploadFlag());

            statement.execute();
        }
    }

    private void insertMustSellSKUProductList(List<ProductModel> productModels) {

        String sql = "INSERT INTO " + TABLE_MUSTSELL_SKU_PRODUCTS + " VALUES (?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < productModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(2, productModels.get(i).getCmpCode());
            statement.bindString(3, productModels.get(i).getDistCode());
            statement.bindString(4, productModels.get(i).getRefrenceNo());
            statement.bindString(5, productModels.get(i).getProdCode());
            statement.bindString(6, productModels.get(i).getUploadFlag());

            statement.execute();
        }
    }

    private void insertFocusBrandRetailerList(List<ProductModel> productModels) {

        String sql = "INSERT INTO " + TABLE_FOCUSBRAND_RETAILERS + " VALUES (?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < productModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(2, productModels.get(i).getCmpCode());
            statement.bindString(3, productModels.get(i).getDistCode());
            statement.bindString(4, productModels.get(i).getRefrenceNo());
            statement.bindString(5, productModels.get(i).getChannelCode());
            statement.bindString(6, productModels.get(i).getSubChannelCode());
            statement.bindString(7, productModels.get(i).getGroupCode());
            statement.bindString(8, productModels.get(i).getClassCode());
            statement.bindString(9, productModels.get(i).getUploadFlag());

            statement.execute();
        }
    }

    private void insertMustSellSKURetailerList(List<ProductModel> productModels) {

        String sql = "INSERT INTO " + TABLE_MUSTSELL_RETAILERS + " VALUES (?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < productModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(2, productModels.get(i).getCmpCode());
            statement.bindString(3, productModels.get(i).getDistCode());
            statement.bindString(4, productModels.get(i).getRefrenceNo());
            statement.bindString(5, productModels.get(i).getChannelCode());
            statement.bindString(6, productModels.get(i).getSubChannelCode());
            statement.bindString(7, productModels.get(i).getGroupCode());
            statement.bindString(8, productModels.get(i).getClassCode());
            statement.bindString(9, productModels.get(i).getUploadFlag());

            statement.execute();
        }
    }

    private void insertGstStateMaster(List<GSTModel> gstModels) {

        String sql = "INSERT INTO " + TABLE_GST_STATE_MASTER + " VALUES (?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < gstModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(2, Globals.checkNull(gstModels.get(i).getCmpCode()));
            statement.bindString(3, gstModels.get(i).getGstStateCode());
            statement.bindString(4, gstModels.get(i).getGstStateName());
            statement.bindString(5, gstModels.get(i).getUnionTerritoryFlag());

            statement.execute();
        }
    }

    private void insertAttributeMasterList(List<ProductModel> productModels) {

        String sql = "INSERT INTO " + TABLE_ATTRIBUTE_MASTER + " VALUES (?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < productModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, productModels.get(i).getCmpCode());
            statement.bindString(2, productModels.get(i).getDistCode());
            statement.bindString(3, productModels.get(i).getAttributeCode());
            statement.bindString(4, productModels.get(i).getAttributeName());
            statement.bindString(5, productModels.get(i).getRemarks());

            statement.execute();
        }
    }

    private void insertAttributeValuesList(List<ProductModel> productModels) {

        String sql = "INSERT INTO " + TABLE_ATTRIBUTE_VALUES + " VALUES (?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < productModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, productModels.get(i).getCmpCode());
            statement.bindString(2, productModels.get(i).getDistCode());
            statement.bindString(3, productModels.get(i).getAttributeCode());
            statement.bindString(4, productModels.get(i).getAttributeValueCode());
            statement.bindString(5, productModels.get(i).getAttributeValueName());

            statement.execute();
        }
    }

    private void insertSchemeSlabProducts(List<SchemeModel> surveyVOS) {

        String sql = "INSERT INTO " + TABLE_SCHEME_SLAB_PRODUCT_LIST + " VALUES (?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < surveyVOS.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, surveyVOS.get(i).getCmpCode());
            statement.bindString(2, surveyVOS.get(i).getDistrCode());
            statement.bindString(3, surveyVOS.get(i).getSchemeCode());
            statement.bindString(4, surveyVOS.get(i).getSlabNo());
            statement.bindString(5, surveyVOS.get(i).getProductCode());
            statement.bindString(6, String.valueOf(surveyVOS.get(i).getQuantity()));
            statement.bindString(7, surveyVOS.get(i).getIsMandatory());
            statement.bindString(8, surveyVOS.get(i).getUploadFlag());

            statement.execute();
        }
    }

    private void insertSchemeSlabList(List<SchemeModel> schemeModels) {

        String sql = "INSERT INTO " + TABLE_SCHEME_SLAB_LIST + " VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < schemeModels.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, schemeModels.get(i).getCmpCode());
            statement.bindString(2, schemeModels.get(i).getDistrCode());
            statement.bindString(3, schemeModels.get(i).getSchemeCode());
            statement.bindString(4, schemeModels.get(i).getSlabNo());
            statement.bindString(5, String.valueOf(schemeModels.get(i).getSlabFrom()));
            statement.bindString(6, String.valueOf(schemeModels.get(i).getSlabTo()));
            statement.bindString(7, String.valueOf(schemeModels.get(i).getPayoutValue()));
            statement.bindString(8, schemeModels.get(i).getSlabRange());
            statement.bindString(9, schemeModels.get(i).getUom());
            statement.bindString(10, String.valueOf(schemeModels.get(i).getForEvery()));
            statement.bindString(11, schemeModels.get(i).getUploadFlag());

            statement.execute();
        }
    }

    private void insertReasons(List<SalesReturnVO> list) {

        String sql = "INSERT INTO " + TABLE_REASONS + " VALUES (?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, list.get(i).getReasonCode());
                statement.bindString(2, list.get(i).getReasonName());
                statement.bindString(3, list.get(i).getScreenName());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }


    private void insertLeaveReasons(List<SalesReturnVO> list) {

        String sql = "INSERT INTO " + TABLE_LEAVE_REASONS + " VALUES (?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, list.get(i).getReasonCode());
                statement.bindString(2, list.get(i).getReasonName());
                statement.bindString(3, list.get(i).getReasonType());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertRetailerCategory(List<RetailerCategoryVO> retailerCategoryList) {

        SFASharedPref pref = SFASharedPref.getOurInstance();
        String sql = "INSERT INTO " + TABLE_RETAILER_CATEGORY + " VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < retailerCategoryList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(2, retailerCategoryList.get(i).getStrCmpCode());
                statement.bindString(3, pref.readString(PREF_DISTRCODE));
                statement.bindString(4, retailerCategoryList.get(i).getStrChannelCode());
                statement.bindString(5, retailerCategoryList.get(i).getStrChannelName());
                statement.bindString(6, retailerCategoryList.get(i).getStrSubChannelCode());
                statement.bindString(7, retailerCategoryList.get(i).getStrSubChannelName());
                statement.bindString(8, retailerCategoryList.get(i).getStrGroupCode());
                statement.bindString(9, retailerCategoryList.get(i).getStrGroupName());
                statement.bindString(10, retailerCategoryList.get(i).getStrClassCode());
                statement.bindString(11, retailerCategoryList.get(i).getStrClassName());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertUomMaster(List<UomMasterVO> uomMasterList) {
        String sql = "INSERT INTO " + TABLE_UOM_MASTER + " VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < uomMasterList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(2, uomMasterList.get(i).getCmpCode());
                statement.bindString(3, uomMasterList.get(i).getDistrCode());
                statement.bindString(4, uomMasterList.get(i).getSalesmanCode());
                statement.bindString(5, uomMasterList.get(i).getProdCode());
                statement.bindString(6, uomMasterList.get(i).getProdHierPath());
                statement.bindString(7, uomMasterList.get(i).getProdHierPathName());
                statement.bindString(8, uomMasterList.get(i).getUomCode());
                statement.bindString(9, uomMasterList.get(i).getUomDescription());
                statement.bindString(10, uomMasterList.get(i).getBaseUom());
                statement.bindLong(11, uomMasterList.get(i).getConversionFactor());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertVansalesUomMaster(List<UomMasterVO> uomMasterList) {
        String sql = "INSERT INTO " + TABLE_VANSALES_UOM_MASTER + " VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < uomMasterList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(2, uomMasterList.get(i).getCmpCode());
                statement.bindString(3, uomMasterList.get(i).getDistrCode());
                statement.bindString(4, uomMasterList.get(i).getSalesmanCode());
                statement.bindString(5, uomMasterList.get(i).getProdCode());
                statement.bindString(6, uomMasterList.get(i).getProdHierPath());
                statement.bindString(7, uomMasterList.get(i).getProdHierPathName());
                statement.bindString(8, uomMasterList.get(i).getUomCode());
                statement.bindString(9, uomMasterList.get(i).getUomDescription());
                statement.bindString(10, uomMasterList.get(i).getBaseUom());
                statement.bindLong(11, uomMasterList.get(i).getConversionFactor());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    public void insertCategorySequence(BaseDB baseDB, Integer sequenceNoCaps, String categoryCaps) {

        SQLiteDatabase database = baseDB.getDb();
        ContentValues values = new ContentValues();
        values.put(SEQUENCE_NO_CAPS, sequenceNoCaps);
        values.put(CATEGORY_CAPS, categoryCaps);

        // Inserting Row
        try {

            database.insert(TABLE_CATEGORY_SEQUENCE, null, values);
        } finally {
            baseDB.closeDb();
        }

    }

    private void bulkInsert(Sync sync) {

        if (!sync.getMappedCompany().isEmpty()) {
            insertMappedData(sync.getMappedCompany(), TABLE_MAPPED_COMPANY);
        }
        if (!sync.getMappedUser().isEmpty()) {
            insertMappedData(sync.getMappedUser(), TABLE_MAPPED_USER);
        }
        if (!sync.getMappedCustomer().isEmpty()) {
            insertMappedData(sync.getMappedCustomer(), TABLE_MAPPED_CUSTOMER);
        }
        if (sync.getCompanyDetail() != null) {
            insertCompanyDetailList(sync.getCompanyDetail());
        }
        if (!sync.getConfigMasterList().isEmpty()) {
            insertConfigurationData(sync.getConfigMasterList());
        }
        if (sync.getCodeGeneratorModel() != null) {
            insertKeyGenerator(sync.getCodeGeneratorModel());
        }
        if (!sync.getGstStateMasterList().isEmpty()) {
            insertGstStateMaster(sync.getGstStateMasterList());
        }
        if (!sync.getLobMasterList().isEmpty()) {
            insertLobMasterList(sync.getLobMasterList());
        }

        if (!sync.getDistrLobList().isEmpty()) {
            insertDistributorLobList(sync.getDistrLobList());
        }
        if (sync.getCustomer() != null) {
            insertCustomer(sync.getCustomer());
        }

        if (sync.getCustomerShipAddress() != null) {
            insertCustomerShipAddress(sync.getCustomerShipAddress());
        }
        if (sync.getProductMasterModelList() != null) {
            insertProductMasterList(sync.getProductMasterModelList());
        }
        if (sync.getProdHierLvlModelList() != null) {
            insertProductHierLevelList(sync.getProdHierLvlModelList());
        }

        if (sync.getProdHierValueList() != null) {
            insertProductHierValueList(sync.getProdHierValueList());
        }

        if (sync.getUomMasterModelList() != null) {
            insertProductUomMasterList(sync.getUomMasterModelList());
        }

        if (sync.getTaxMasterModelList() != null) {
            insertTaxMasterList(sync.getTaxMasterModelList());
        }

        if (sync.getProdBatchModelList() != null) {
            insertProductBatchMasterList(sync.getProdBatchModelList());
        }

        Log.d(TAG, "bulkDistInsert orderBookingHeader : " + sync.getOrderBookingHeaderModels().size());
        if (!sync.getOrderBookingHeaderModels().isEmpty()) {
            insertOrderBookingHeader(sync.getOrderBookingHeaderModels());
        }

        if (!sync.getProFilterModelList().isEmpty()) {
            insertProductsFilterList(sync.getProFilterModelList());
        }

        if (sync.getDistributor() != null) {
            insertDistributorList(sync.getDistributor());
        }

        if (sync.getDistributorStock() != null) {
            insertDistributorStockList(sync.getDistributorStock());
        }

        if (sync.getCustomerStock() != null) {
            insertCustomerStockList(sync.getCustomerStock());
        }

        if (sync.getFeedbackMasterModelList() != null) {
            insertFeedbackMasterList(sync.getFeedbackMasterModelList());
        }

        if (sync.getNotificationTypeList() != null) {
            insertNotificationTypeList(sync.getNotificationTypeList());
        }


















        if (!sync.getRouteList().isEmpty()) {
            insertRoute(sync.getRouteList());
        }
        if (!sync.getPendingBillList().isEmpty()) {
            insertPendingBill(sync.getPendingBillList());
        }
        /*if (!sync.getRetailerList().isEmpty()) {
            insertRetailer(sync.getRetailerList());
        }*/

        if (!sync.getRetailerVisitList().isEmpty()) {
            insertRetailerVisit(sync.getRetailerVisitList());
        }

        if (!sync.getCustomerUploadStatusList().isEmpty()) {
            insertCustomerUploadStatus(sync.getCustomerUploadStatusList());
        }
        if (!sync.getSalesmanTrackerList().isEmpty()) {
            insertSalesmanTracker(sync.getSalesmanTrackerList());
        }
        if (!sync.getRetailerCategoryIdList().isEmpty()) {
            insertRetailerCategoryId(sync.getRetailerCategoryIdList());
        }

        if (!sync.getProductsList().isEmpty()) {
            insertProducts(sync.getProductsList());
        }

        if (!sync.getVanSalesProductsList().isEmpty()) {
            insertVanSalesProducts(sync.getVanSalesProductsList());
        }
        if (!sync.getBankNamesList().isEmpty()) {
            insertBankNames(sync.getBankNamesList());
        }

        if (!sync.getReasonsList().isEmpty()) {
            insertReasons(sync.getReasonsList());
        }

        if (!sync.getLeaveReasonsList().isEmpty()) {
            insertLeaveReasons(sync.getLeaveReasonsList());
        }

        if (!sync.getFeedbackReasonsList().isEmpty()) {
            insertFeedbackReasons(sync.getFeedbackReasonsList());
        }


        if (!sync.getVanSalesStockLoadingList().isEmpty()) {
            insertVanSalesStocksForConfirm(sync.getVanSalesStockLoadingList());
        }

        subBulkInsertMaster1(sync);

        subBulkInsertMaster2(sync);

//        insertRetailerCategoryInfo();

        //SMDMS


        if (sync.getDistBalanceList() != null && sync.getDistBalanceList().getCmpCode() != null) {
            insertDistributorBalanceList(sync.getDistBalanceList());
        }

//        if (sync.getBranchStockList() != null) {
//            insertBranchStockList(sync.getBranchStockList());
//        }
//        if (sync.getGeoHierLevelList() != null) {
//            insertGeoHierLevel(sync.getGeoHierLevelList());
//        }
//        if (sync.getGeoHierValueList() != null) {
//            insertGeoHierValue(sync.getGeoHierValueList());
//        }
//        if (sync.getKYCMasterList() != null) {
//            insertKycMasterList(sync.getKYCMasterList());
//        }
//
//
//        if (sync.getStockOnHandList() != null) {
//            insertStockOnHandList(sync.getStockOnHandList());
//        }
//
//        if (sync.getWdInfrastructureStorage() != null && sync.getWdInfrastructureStorage().getCmpCode() != null) {
//            insertWDStorage(sync.getWdInfrastructureStorage());
//        }
//
//        if (sync.getWdInfrastructureFreezer() != null) {
//            insertWDFreezer(sync.getWdInfrastructureFreezer());
//        }
//
//        if (sync.getCustomerRoute() != null) {
//            insertCustomerRoute(sync.getCustomerRoute());
//        }
//
//        if (sync.getCustomerBank() != null) {
//            insertCustomerBank(sync.getCustomerBank());
//        }
//
//        if (sync.getPreviousPOStatusList() != null) {
//            insertPreviousPurchaseOrderList(sync.getPreviousPOStatusList());
//        }
//        if (sync.getSalesmanMasterModelList() != null) {
//            insertSalesmanMasterList(sync.getSalesmanMasterModelList());
//        }
//
//        if (sync.getSalesmanMasterKycList() != null) {
//            insertSalesmanKycList(sync.getSalesmanMasterKycList());
//        }
//
//        if (sync.getSalesmanMasterRouteList() != null) {
//            insertSalesmanRouteList(sync.getSalesmanMasterRouteList());
//        }
//
//        if (sync.getSalesmanMasterLobList() != null) {
//            insertSalesmanLobList(sync.getSalesmanMasterLobList());
//        }
//
//        if (sync.getEncodedKeyValueList() != null) {
//            insertEnCodingKeyValueList(sync.getEncodedKeyValueList());
//        }
//
//        if (sync.getPendingBillCollections() != null) {
//            insertPendingBillCollections(sync.getPendingBillCollections());
//        }
//
//        if (sync.getEditRateList() != null) {
//            insertEditRateList(sync.getEditRateList());
//        }
//
//        /* Customer Product Mapping list */
//        if (sync.getCustProdMappingList() != null) {
//            insertCustomerProductMappingList(sync.getCustProdMappingList());
//        }
//
//        if (sync.getSupplierList() != null) {
//            insertSupplierList(sync.getSupplierList());
//        }
//
//        if (sync.getContractPricingModels() != null) {
//            insertContractPricing(sync.getContractPricingModels());
//        }
//
//        if (sync.getSalesDBCRNoteModels() != null) {
//            insertSalesDBCRNote(sync.getSalesDBCRNoteModels());
//        }
//
//        if (sync.getBulletinBoardModelList() != null) {
//            insertBulletinBoard(sync.getBulletinBoardModelList());
//        }
//
//        if (sync.getTempRouteList() != null) {
//            insertTempRoute(sync.getTempRouteList());
//        }
//
//        if (sync.getTempCustomer() != null) {
//            insertTempCustomer(sync.getTempCustomer());
//        }
//
//        if (sync.getTempCustomerRoute() != null) {
//            insertTempCustomerRoute(sync.getTempCustomerRoute());
//        }
//
//        if (sync.getTempCustomerShipAddress() != null) {
//            insertTempCustomerShipAddress(sync.getTempCustomerShipAddress());
//        }
//
//        if (sync.getCodeGeneratorHistoryModel() != null) {
//            insertKeyGeneratorHistory(sync.getCodeGeneratorHistoryModel());
//        }
    }

    private void insertSalesDBCRNote(List<SalesDBCRNoteModel> salesDBCRNoteModels) {
        String sql = "INSERT INTO " + TABLE_SALES_DB_CRNOTE + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < salesDBCRNoteModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(salesDBCRNoteModels.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(salesDBCRNoteModels.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(salesDBCRNoteModels.get(i).getCustomerCode()));
                statement.bindString(4, Globals.checkNull(salesDBCRNoteModels.get(i).getDbCRType()));
                statement.bindString(5, Globals.checkNull(salesDBCRNoteModels.get(i).getSalesDBCRRefNo()));
                statement.bindString(6, Globals.checkNull(salesDBCRNoteModels.get(i).getDbCRDt()));
                statement.bindString(7, Globals.checkNull(salesDBCRNoteModels.get(i).getDbCRReason()));
                statement.bindDouble(8, salesDBCRNoteModels.get(i).getDbCRBudget());
                statement.bindDouble(9, salesDBCRNoteModels.get(i).getDbCRTaxAmt());
                statement.bindDouble(10, salesDBCRNoteModels.get(i).getDbCRBal());
                statement.bindDouble(11, salesDBCRNoteModels.get(i).getDbCRBal());
                statement.bindString(12, Globals.checkNull(salesDBCRNoteModels.get(i).getIsSettled()));
                statement.bindString(13, Globals.checkNull(salesDBCRNoteModels.get(i).getRemarks()));
                statement.bindString(14, Globals.checkNull(salesDBCRNoteModels.get(i).getIsActive()));
                statement.bindString(15, Globals.checkNull(salesDBCRNoteModels.get(i).getIsClaimable()));
                statement.bindString(16, Globals.checkNull(salesDBCRNoteModels.get(i).getReferenceType()));
                statement.bindString(17, Globals.checkNull(salesDBCRNoteModels.get(i).getReferenceNo()));
                statement.bindString(18, Globals.checkNull(salesDBCRNoteModels.get(i).getModDt()));
                statement.bindString(19, Globals.checkNull(salesDBCRNoteModels.get(i).getUploadFlag()));
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertContractPricing(List<ContractPricingModel> contractPricingModels) {

        String sql = "INSERT INTO " + TABLE_CONTRACT_PRICING + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < contractPricingModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(contractPricingModels.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(contractPricingModels.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(contractPricingModels.get(i).getCpRefCode()));
                statement.bindString(4, Globals.checkNull(contractPricingModels.get(i).getCpRefName()));
                statement.bindString(5, Globals.checkNull(contractPricingModels.get(i).getFromDate()));
                statement.bindString(6, Globals.checkNull(contractPricingModels.get(i).getToDate()));
                statement.bindString(7, Globals.checkNull(contractPricingModels.get(i).getDiscType()));
                statement.bindString(8, Globals.checkNull(contractPricingModels.get(i).getCustomerCode()));
                statement.bindString(9, Globals.checkNull(contractPricingModels.get(i).getProdCode()));
                statement.bindString(10, Globals.checkNull(contractPricingModels.get(i).getApplyOn()));
                statement.bindString(11, Globals.checkNull(contractPricingModels.get(i).getDiscPer()));
                statement.bindString(12, Globals.checkNull(contractPricingModels.get(i).getFlatAmt()));
                statement.bindString(13, Globals.checkNull(contractPricingModels.get(i).getSplPrice()));
                statement.bindString(14, Globals.checkNull(contractPricingModels.get(i).getSplPriceWithTax()));
                statement.bindString(15, Globals.checkNull(contractPricingModels.get(i).getNetRate()));
                statement.bindString(16, Globals.checkNull(contractPricingModels.get(i).getModDt()));
                statement.bindString(17, Globals.checkNull(contractPricingModels.get(i).getUploadFlag()));
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }


    private void insertCustomerProductMappingList(List<CustProdMappingModel> editRateList) {

        String sql = "INSERT INTO " + TABLE_CUSTOMER_PRODUCT_MAPPING + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < editRateList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(editRateList.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(editRateList.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(editRateList.get(i).getCustomerLevel()));
                statement.bindString(4, Globals.checkNull(editRateList.get(i).getChannelCode()));
                statement.bindString(5, Globals.checkNull(editRateList.get(i).getSubChannelCode()));
                statement.bindString(6, Globals.checkNull(editRateList.get(i).getGroupCode()));
                statement.bindString(7, Globals.checkNull(editRateList.get(i).getClassCode()));
                statement.bindString(8, Globals.checkNull(editRateList.get(i).getCustomerCode()));
                statement.bindString(9, Globals.checkNull(editRateList.get(i).getProductLevel()));
                statement.bindString(10, Globals.checkNull(editRateList.get(i).getProdCode()));
                statement.bindString(11, Globals.checkNull(editRateList.get(i).getStartDt()));
                statement.bindString(12, Globals.checkNull(editRateList.get(i).getEndDt()));
                statement.bindString(13, Globals.checkNull(editRateList.get(i).getAllowProduct()));
                statement.bindString(14, Globals.checkNull(editRateList.get(i).getType()));
                statement.bindString(15, Globals.checkNull(editRateList.get(i).getModDt()));
                statement.bindString(16, Globals.checkNull(editRateList.get(i).getUploadFlag()));
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertEditRateList(List<EditRateModel> editRateList) {

        String sql = "INSERT INTO " + TABLE_RATE_EDIT + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < editRateList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(editRateList.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(editRateList.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(editRateList.get(i).getCustomerLevel()));
                statement.bindString(4, Globals.checkNull(editRateList.get(i).getChannelCode()));
                statement.bindString(5, Globals.checkNull(editRateList.get(i).getSubChannelCode()));
                statement.bindString(6, Globals.checkNull(editRateList.get(i).getGroupCode()));
                statement.bindString(7, Globals.checkNull(editRateList.get(i).getClassCode()));
                statement.bindString(8, Globals.checkNull(editRateList.get(i).getCustomerCode()));
                statement.bindString(9, Globals.checkNull(editRateList.get(i).getProductLevel()));
                statement.bindString(10, Globals.checkNull(editRateList.get(i).getProdCode()));
                statement.bindString(11, Globals.checkNull(editRateList.get(i).getAllowEdit()));
                statement.bindString(12, Globals.checkNull(editRateList.get(i).getModDt()));
                statement.bindString(13, Globals.checkNull(editRateList.get(i).getUploadFlag()));
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertSupplierList(List<SupplierModel> supplierList) {

        String sql = "INSERT INTO " + TABLE_SUPPLIER + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < supplierList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(supplierList.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(supplierList.get(i).getSupplierCode()));
                statement.bindString(3, Globals.checkNull(supplierList.get(i).getSupplierName()));
                statement.bindString(4, Globals.checkNull(supplierList.get(i).getSupplierAddr()));
                statement.bindString(5, Globals.checkNull(supplierList.get(i).getPhone()));
                statement.bindString(6, Globals.checkNull(supplierList.get(i).getEmailId()));
                statement.bindString(7, Globals.checkNull(supplierList.get(i).getContactPerson()));
                statement.bindString(8, Globals.checkNull(supplierList.get(i).getGstStateCode()));
                statement.bindString(9, Globals.checkNull(supplierList.get(i).getSupplierGSTIN()));
                statement.bindString(10, Globals.checkNull(supplierList.get(i).getSupplierStatus()));
                statement.bindString(11, Globals.checkNull(String.valueOf(supplierList.get(i).getModDt())));
                statement.bindString(12, Globals.checkNull(supplierList.get(i).getUploadFlag()));
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }

    }

    private void insertPendingBillCollections(List<PendingBillCollection> pendingBillCollections) {
        String sql = "INSERT INTO " + TABLE_PENDING_BILLS + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < pendingBillCollections.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(pendingBillCollections.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(pendingBillCollections.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(pendingBillCollections.get(i).getInvoiceNo()));
                statement.bindString(4, Globals.checkNull(pendingBillCollections.get(i).getInvoiceDt()));
                statement.bindString(5, Globals.checkNull(pendingBillCollections.get(i).getSalesmanCode()));
                statement.bindString(6, Globals.checkNull(pendingBillCollections.get(i).getCustomerCode()));
                statement.bindString(7, Globals.checkNull(pendingBillCollections.get(i).getTotNetAmt()));
                statement.bindString(8, Globals.checkNull(pendingBillCollections.get(i).getBalanceOs()));
                statement.bindString(9, String.valueOf(System.currentTimeMillis()));
                statement.bindString(10, "Y");
                statement.execute();
            } catch (Exception e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertEnCodingKeyValueList(List<EncodeModel> encodedKeyValueList) {

        String sql = "INSERT INTO " + TABLE_KEY_VALUE_ENCODE + " VALUES (?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < encodedKeyValueList.size(); i++) {
            statement.clearBindings();
            statement.bindString(2, Globals.checkNull(encodedKeyValueList.get(i).getEnkey()));
            statement.bindString(3, Globals.checkNull(encodedKeyValueList.get(i).getEnValue()));
            statement.execute();
        }
    }

    private void insertCustomerBank(List<CustomerBankModel> customerBank) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER_BANK + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < customerBank.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, Globals.checkNull(customerBank.get(i).getCmpCode()));
            statement.bindString(2, Globals.checkNull(customerBank.get(i).getDistrCode()));
            statement.bindString(3, Globals.checkNull(customerBank.get(i).getCustomerCode()));
            statement.bindString(4, Globals.checkNull(customerBank.get(i).getBankCode()));
            statement.bindString(5, Globals.checkNull(customerBank.get(i).getBankName()));
            statement.bindString(6, Globals.checkNull(customerBank.get(i).getBankBranchName()));
            statement.bindString(7, Globals.checkNull(customerBank.get(i).getIfscCode()));
            statement.bindString(8, Globals.checkNull(customerBank.get(i).getAccountType()));
            statement.bindString(9, Globals.checkNull(customerBank.get(i).getAccountNumber()));
            statement.bindLong(10, customerBank.get(i).getModDt());
            statement.bindString(11, "Y");
            statement.execute();
        }
    }

    private void insertCustomerRoute(List<RouteModel> routeList) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER_ROUTE + " VALUES (?,?,?,?,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < routeList.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, Globals.checkNull(routeList.get(i).getCompanyCode()));
            statement.bindString(2, Globals.checkNull(routeList.get(i).getDistributorCode()));
            statement.bindString(3, Globals.checkNull(routeList.get(i).getCustomerCode()));
            statement.bindString(4, Globals.checkNull(routeList.get(i).getRouteCode()));
            statement.bindString(5, Globals.checkNull(routeList.get(i).getCoverageSeq()));
            statement.bindString(6, Globals.checkNull(routeList.get(i).getModDt()));
            statement.bindString(7, "Y");
            statement.execute();
        }
    }

    private void insertCustomerShipAddress(List<AddressModel> customerShipAddress) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER_SHIP_ADDRESS + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < customerShipAddress.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(customerShipAddress.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(customerShipAddress.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(customerShipAddress.get(i).getCustomerCode()));
                statement.bindString(4, Globals.checkNull(customerShipAddress.get(i).getShipCode()));
                statement.bindString(5, Globals.checkNull(customerShipAddress.get(i).getShippingAddress1()));
                statement.bindString(6, Globals.checkNull(customerShipAddress.get(i).getShippingAddress2()));
                statement.bindString(7, Globals.checkNull(customerShipAddress.get(i).getShippingAddress3()));
                statement.bindString(8, Globals.checkNull(customerShipAddress.get(i).getShipCityTown()));
                statement.bindString(9, Globals.checkNull(customerShipAddress.get(i).getShippingGeoHierPath()));
                statement.bindString(10, Globals.checkNull(customerShipAddress.get(i).getShippingState()));
                statement.bindString(11, Globals.checkNull(customerShipAddress.get(i).getShippingPhoneNumber()));
                statement.bindString(12, Globals.checkNull(customerShipAddress.get(i).getIsDefault()));
                statement.bindString(13, Globals.checkNull(customerShipAddress.get(i).getModDt()));
                statement.bindString(14, "Y");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }

    }

    private void insertCustomer(List<CustomerModel> customer) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < customer.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(customer.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(customer.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(customer.get(i).getCustomerCode()));
                statement.bindString(4, Globals.checkNull(customer.get(i).getCompanyCustomerCode()));
                statement.bindString(5, Globals.checkNull(customer.get(i).getCustomerName()));
                statement.bindString(6, Globals.checkNull(customer.get(i).getPinCode()));
                statement.bindString(7, Globals.checkNull(customer.get(i).getPhoneNo()));
                statement.bindString(8, Globals.checkNull(customer.get(i).getMobileNo()));
                statement.bindString(9, Globals.checkNull(customer.get(i).getContactPerson()));
                statement.bindString(10, Globals.checkNull(customer.get(i).getEmailID()));
                statement.bindString(11, Globals.checkNull(String.valueOf(customer.get(i).getDayOff())));
                statement.bindString(12, Globals.checkNull(customer.get(i).getRetailerStatus()));
                statement.bindString(13, Globals.checkNull(customer.get(i).getFssaiNo()));
                statement.bindString(14, Globals.checkNull(customer.get(i).getDrugLicNo()));
                statement.bindString(15, Globals.checkNull(customer.get(i).getDrugLicExpiryDate()));
                statement.bindString(16, String.valueOf(customer.get(i).getCreditBills()));
                statement.bindString(17, String.valueOf(customer.get(i).getCreditDays()));
                statement.bindDouble(18, customer.get(i).getCreditLimit());
                statement.bindDouble(19, customer.get(i).getCashDiscPerc());
                statement.bindString(20, Globals.checkNull(customer.get(i).getChannelCode()));
                statement.bindString(21, Globals.checkNull(customer.get(i).getSubChannelCode()));
                statement.bindString(22, Globals.checkNull(customer.get(i).getGroupCode()));
                statement.bindString(23, Globals.checkNull(customer.get(i).getClassCode()));
                statement.bindString(24, Globals.checkNull(customer.get(i).getStoreType()));
                statement.bindString(25, Globals.checkNull(customer.get(i).getParentCustomerCode()));
                statement.bindString(26, Globals.checkNull(customer.get(i).getLatitude()));
                statement.bindString(27, Globals.checkNull(customer.get(i).getLongitude()));
                statement.bindString(28, Globals.checkNull(customer.get(i).getCustomerType()));
                statement.bindString(29, Globals.checkNull(customer.get(i).getGstTinNo()));
                statement.bindString(30, Globals.checkNull(customer.get(i).getPanNo()));
                statement.bindString(31, Globals.checkNull(customer.get(i).getApprovalStatus()));
                statement.bindLong(32, customer.get(i).getModDt());
                statement.bindString(33, "Y");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertWDFreezer(List<WDInfrastructure> wdInfrastructureFreezer) {
        String sql = "INSERT INTO " + TABLE_WD_FREEZER + " VALUES (?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < wdInfrastructureFreezer.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, wdInfrastructureFreezer.get(i).getCmpCode());
                statement.bindString(2, wdInfrastructureFreezer.get(i).getDistrCode());
                statement.bindString(3, wdInfrastructureFreezer.get(i).getFreezerCapacity());
                statement.bindString(4, wdInfrastructureFreezer.get(i).getUnit());
                statement.bindString(5, wdInfrastructureFreezer.get(i).getTotalCapacity());
                statement.bindString(6, wdInfrastructureFreezer.get(i).getModDt());
                statement.bindString(7, "Y");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertWDStorage(WDInfrastructure wdInfrastructureStorage) {
        String sql = "INSERT INTO " + TABLE_WD_STORAGE + " VALUES (?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        try {
            statement.clearBindings();
            statement.bindString(1, String.valueOf(wdInfrastructureStorage.getCmpCode()));
            statement.bindString(2, wdInfrastructureStorage.getDistrCode());
            statement.bindString(3, String.valueOf(wdInfrastructureStorage.getWetsqft()));
            statement.bindString(4, wdInfrastructureStorage.getFrozensqft());
            statement.bindString(5, wdInfrastructureStorage.getModDt());
            statement.bindString(6, "Y");
            statement.execute();
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w(DUPLICATE_TAG, e);
        }
    }

    public void insertPreviousPurchaseOrderList(List<PreviousPOModel> previousPOStatusList) {

        String sql = "INSERT INTO " + TABLE_PREVIOUS_PURCHASE_ORDERS + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < previousPOStatusList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, previousPOStatusList.get(i).getCmpCode());
                statement.bindString(2, previousPOStatusList.get(i).getDistrCode());
                statement.bindString(3, previousPOStatusList.get(i).getPoRefNo());
                statement.bindString(4, previousPOStatusList.get(i).getPoDate());
                statement.bindString(5, previousPOStatusList.get(i).getProdCode());
                statement.bindString(6, previousPOStatusList.get(i).getProdName());
                statement.bindString(7, previousPOStatusList.get(i).getProdBatchCode());
                statement.bindDouble(8, previousPOStatusList.get(i).getPurchaseRate());
                statement.bindDouble(9, previousPOStatusList.get(i).getOrderQty());
                statement.bindString(10, previousPOStatusList.get(i).getUomCode());
                statement.bindDouble(11, previousPOStatusList.get(i).getTaxAmt());
                statement.bindDouble(12, previousPOStatusList.get(i).getCgstPerc());
                statement.bindDouble(13, previousPOStatusList.get(i).getCgstAmt());
                statement.bindDouble(14, previousPOStatusList.get(i).getSgstPerc());
                statement.bindDouble(15, previousPOStatusList.get(i).getSgstAmt());
                statement.bindDouble(16, previousPOStatusList.get(i).getIgstPerc());
                statement.bindDouble(17, previousPOStatusList.get(i).getIgstAmt());
                statement.bindDouble(18, previousPOStatusList.get(i).getUgstPerc());
                statement.bindDouble(19, previousPOStatusList.get(i).getUgstAmt());
                statement.bindDouble(20, previousPOStatusList.get(i).getOrderValue());
                statement.bindString(21, previousPOStatusList.get(i).getApprovalStatus());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertSalesmanMasterList(List<SalesmanMasterModel> salesmanMasterModels) {

        String sql = "INSERT INTO " + TABLE_SALESMAN_MASTER + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < salesmanMasterModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, salesmanMasterModels.get(i).getCmpCode());
                statement.bindString(2, salesmanMasterModels.get(i).getDistrCode());
                statement.bindString(3, salesmanMasterModels.get(i).getSalesmanCode());
                statement.bindString(4, salesmanMasterModels.get(i).getSalesmanName());
                statement.bindString(5, Globals.checkNull(salesmanMasterModels.get(i).getMobileNo()));
                statement.bindString(6, Globals.checkNull(salesmanMasterModels.get(i).getEmailId()));
                statement.bindLong(7, salesmanMasterModels.get(i).getMonthlySalary());
                statement.bindString(8, Globals.checkNull(salesmanMasterModels.get(i).getSalesmanStatus()));
                statement.bindString(9, Globals.checkNull(salesmanMasterModels.get(i).getSsfaEnabled()));
                statement.bindString(10, Globals.checkNull(salesmanMasterModels.get(i).getBankName()));
                statement.bindString(11, Globals.checkNull(salesmanMasterModels.get(i).getAccNo()));
                statement.bindString(12, Globals.checkNull(salesmanMasterModels.get(i).getIfscCode()));
                statement.bindString(13, salesmanMasterModels.get(i).getBranchName());
                statement.bindString(14, salesmanMasterModels.get(i).getAdharNo());
                statement.bindString(15, Globals.checkNull(salesmanMasterModels.get(i).getSalesmanProfileImage()));
                statement.bindString(16, Globals.checkNull(salesmanMasterModels.get(i).getCancelledChequeImage()));


//                statement.bindString(5, salesmanMasterModels.get(i).getMobileNo());
//                statement.bindString(6, salesmanMasterModels.get(i).getEmailId());
//                statement.bindLong(7, salesmanMasterModels.get(i).getMonthlySalary());
//                statement.bindString(8, salesmanMasterModels.get(i).getSalesmanStatus());
//                statement.bindString(9, salesmanMasterModels.get(i).getSsfaEnabled());
//                statement.bindString(10, salesmanMasterModels.get(i).getBankName());
//                statement.bindString(11, salesmanMasterModels.get(i).getAccNo());
//                statement.bindString(12, salesmanMasterModels.get(i).getIfscCode());
//                statement.bindString(13, salesmanMasterModels.get(i).getBranchName());
//                statement.bindString(14, salesmanMasterModels.get(i).getAdharNo());
//                statement.bindString(15, salesmanMasterModels.get(i).getSalesmanProfileImage());
//                statement.bindString(16, salesmanMasterModels.get(i).getCancelledChequeImage());
                statement.bindString(17, salesmanMasterModels.get(i).getModDt());
                statement.bindString(18, "Y");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertSalesmanKycList(List<SalesmanMasterModel> salesmanMasterModels) {

        String sql = "INSERT INTO " + TABLE_SALESMAN_MASTER_KYC + " VALUES (?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < salesmanMasterModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, salesmanMasterModels.get(i).getCmpCode());
                statement.bindString(2, salesmanMasterModels.get(i).getDistrCode());
                statement.bindString(3, salesmanMasterModels.get(i).getSalesmanCode());
                statement.bindString(4, salesmanMasterModels.get(i).getSelectedKYC());
                statement.bindString(5, salesmanMasterModels.get(i).getKycImage());
                statement.bindString(6, salesmanMasterModels.get(i).getModDt());
                statement.bindString(7, "Y");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertSalesmanRouteList(List<SalesmanMasterModel> salesmanMasterModels) {

        String sql = "INSERT INTO " + TABLE_SALESMAN_ROUTE_MAPPING + " VALUES (?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < salesmanMasterModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, salesmanMasterModels.get(i).getCmpCode());
                statement.bindString(2, salesmanMasterModels.get(i).getDistrCode());
                statement.bindString(3, salesmanMasterModels.get(i).getSalesmanCode());
                statement.bindString(4, salesmanMasterModels.get(i).getRouteCode());
                statement.bindString(5, "Y");
                statement.bindString(6, salesmanMasterModels.get(i).getModDt());
                statement.bindString(7, "Y");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertSalesmanLobList(List<SalesmanMasterModel> salesmanMasterModels) {

        String sql = "INSERT INTO " + TABLE_SALESMAN_LOB_MAPPING + " VALUES (?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < salesmanMasterModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, salesmanMasterModels.get(i).getCmpCode());
                statement.bindString(2, salesmanMasterModels.get(i).getDistrCode());
                statement.bindString(3, salesmanMasterModels.get(i).getSalesmanCode());
                statement.bindString(4, salesmanMasterModels.get(i).getLobCode());
                statement.bindString(5, "Y");
                statement.bindString(6, salesmanMasterModels.get(i).getModDt());
                statement.bindString(7, "Y");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertStockOnHandList(List<StockOnHandModel> stockOnHandList) {

        String sql = "INSERT INTO " + TABLE_STOCK_ON_HAND + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < stockOnHandList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, stockOnHandList.get(i).getCmpCode());
                statement.bindString(2, stockOnHandList.get(i).getDistrCode());
                statement.bindString(3, stockOnHandList.get(i).getProdCode());
                statement.bindString(4, stockOnHandList.get(i).getProdBatchCode());
                statement.bindString(5, stockOnHandList.get(i).getGodownCode());
                statement.bindString(6, String.valueOf(stockOnHandList.get(i).getSaleableQty()));
//                statement.bindString(6, String.valueOf(100));
                statement.bindString(7, String.valueOf(stockOnHandList.get(i).getUnSaleableQty()));
                statement.bindString(8, String.valueOf(stockOnHandList.get(i).getOfferQty()));
                statement.bindString(9, String.valueOf(stockOnHandList.get(i).getResvSaleableQty()));
                statement.bindString(10, String.valueOf(stockOnHandList.get(i).getResvUnSaleableQty()));
                statement.bindString(11, String.valueOf(stockOnHandList.get(i).getResvOfferQty()));
//                if (isLatestBatch(stockOnHandList.get(i).getProdCode(), stockOnHandList.get(i).getProdBatchCode()))
//                    statement.bindString(12, String.valueOf(1000));
//                else
                statement.bindString(12, String.valueOf(stockOnHandList.get(i).getSaleableQty()));
                statement.bindString(13, String.valueOf(stockOnHandList.get(i).getLastOrdQty()));
                statement.bindString(14, stockOnHandList.get(i).getUploadFlag());
                statement.bindLong(15, stockOnHandList.get(i).getModDt());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private boolean isLatestBatch(String prodCode, String batchCode) {
        String latest = "N";
        String query = "Select latestBatch from m_product_batch_master where prodCode = ? and prodBatchCode = ?";

        Cursor cursorSalesRtn = db.rawQuery(query, new String[]{prodCode, batchCode});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0) {
            while (cursorSalesRtn.moveToNext()) {
                latest = cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_LATEST_BATCH));
            }
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }
        return latest.equals("Y");
    }

    private void insertGeoHierValue(List<GeoHierModel> geoHierModelList) {
        String sql = "INSERT INTO " + TABLE_GEO_HIER_VALUE + " VALUES (?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < geoHierModelList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, geoHierModelList.get(i).getCmpCode());
                statement.bindString(2, geoHierModelList.get(i).getUploadFlag());
                statement.bindString(3, geoHierModelList.get(i).getModDt());
                statement.bindString(4, geoHierModelList.get(i).getGeoHierLevelCode());
                statement.bindString(5, geoHierModelList.get(i).getGeoCode());
                statement.bindString(6, geoHierModelList.get(i).getGeoName());
                if (geoHierModelList.get(i).getParentCode() != null && !geoHierModelList.get(i).getParentCode().isEmpty()) {
                    statement.bindString(7, geoHierModelList.get(i).getParentCode());
                } else {
                    statement.bindString(7, "");
                }

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertGeoHierLevel(List<GeoHierModel> geoHierModelList) {

        String sql = "INSERT INTO " + TABLE_GEO_HIER_LEVEL + " VALUES (?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < geoHierModelList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, geoHierModelList.get(i).getCmpCode());
                statement.bindString(2, geoHierModelList.get(i).getUploadFlag());
                statement.bindString(3, geoHierModelList.get(i).getModDt());
                statement.bindString(4, geoHierModelList.get(i).getGeoHierLevelCode());
                statement.bindString(5, geoHierModelList.get(i).getGeoLevelName());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertProductBatchMasterList(List<ProdBatchModel> prodBatchModelList) {

        String sql = "INSERT INTO " + TABLE_PRODUCT_BATCH_MASTER + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < prodBatchModelList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(prodBatchModelList.get(i).getCmpCode()));
                statement.bindString(2, prodBatchModelList.get(i).getDistrCode());
                statement.bindString(3, prodBatchModelList.get(i).getProdCode());
                statement.bindString(4, prodBatchModelList.get(i).getProdBatchCode());
                statement.bindString(5, prodBatchModelList.get(i).getFromLevel());
                statement.bindString(6, prodBatchModelList.get(i).getToLevel());
                statement.bindString(7, prodBatchModelList.get(i).getLatestBatch());
                statement.bindDouble(8, prodBatchModelList.get(i).getPurchaseRate());
                statement.bindDouble(9, prodBatchModelList.get(i).getMrp());
                statement.bindDouble(10, prodBatchModelList.get(i).getSellRate());
                statement.bindLong(11, prodBatchModelList.get(i).getManfDate());
                statement.bindLong(12, prodBatchModelList.get(i).getExpiryDate());
                statement.bindDouble(13, prodBatchModelList.get(i).getSellRateWithTax());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertTaxMasterList(List<TaxMasterModel> taxMasterModelList) {

        String sql = "INSERT INTO " + TABLE_PRODUCT_TAX_MASTER + " VALUES (?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < taxMasterModelList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(taxMasterModelList.get(i).getCmpCode()));
                statement.bindString(2, taxMasterModelList.get(i).getTaxStateCode());
                statement.bindString(3, taxMasterModelList.get(i).getProdCode());
                statement.bindDouble(4, taxMasterModelList.get(i).getCgst());
                statement.bindDouble(5, taxMasterModelList.get(i).getSgst());
                statement.bindDouble(6, taxMasterModelList.get(i).getIgst());
                statement.bindDouble(7, taxMasterModelList.get(i).getCess());
                statement.bindDouble(8, taxMasterModelList.get(i).getAdditionalTax());
                statement.bindString(9, taxMasterModelList.get(i).getUploadFlag());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertProductUomMasterList(List<UomMasterModel> uomMasterModelList) {

        String sql = "INSERT INTO " + TABLE_PRODUCT_UOM_MASTER + " VALUES (?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < uomMasterModelList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(uomMasterModelList.get(i).getCmpCode()));
                statement.bindString(2, uomMasterModelList.get(i).getProdCode());
                statement.bindString(3, uomMasterModelList.get(i).getUomCode());
                statement.bindString(4, uomMasterModelList.get(i).getUomDescription());
                statement.bindString(5, uomMasterModelList.get(i).getUomConvFactor());
                statement.bindString(6, uomMasterModelList.get(i).getBaseUOM());
                statement.bindString(7, uomMasterModelList.get(i).getDefaultUOM());
                statement.bindString(8, "N");
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertProductHierValueList(List<ProdHierLvlModel> prodHierValueList) {

        String sql = "INSERT INTO " + TABLE_PRODUCT_HIER_VALUE + " VALUES (?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < prodHierValueList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(prodHierValueList.get(i).getCmpCode()));
                statement.bindString(2, prodHierValueList.get(i).getProdHierLvlCode());
                statement.bindString(3, prodHierValueList.get(i).getProdHierValCode());
                statement.bindString(4, prodHierValueList.get(i).getProdHierValName());

                if (prodHierValueList.get(i).getParentCode() != null &&
                        !prodHierValueList.get(i).getParentCode().isEmpty()) {
                    statement.bindString(5, prodHierValueList.get(i).getParentCode());
                } else {
                    statement.bindString(5, "");
                }
                statement.bindString(6, prodHierValueList.get(i).getUploadFlag());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertProductHierLevelList(List<ProdHierLvlModel> prodHierLvlModelList) {

        String sql = "INSERT INTO " + TABLE_PRODUCT_HIER_LEVEL + " VALUES (?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < prodHierLvlModelList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(prodHierLvlModelList.get(i).getCmpCode()));
                statement.bindString(2, prodHierLvlModelList.get(i).getProdHierLvlCode());
                statement.bindString(3, prodHierLvlModelList.get(i).getProdHierLvlName());
                statement.bindString(4, prodHierLvlModelList.get(i).getUploadFlag());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertProductMasterList(List<ProductMasterModel> productMasterModelList) {

        String sql = "INSERT INTO " + TABLE_PRODUCT_MASTER + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < productMasterModelList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(productMasterModelList.get(i).getCmpCode()));
                statement.bindString(2, productMasterModelList.get(i).getProdCode());
                statement.bindString(3, productMasterModelList.get(i).getProdName());
                statement.bindString(4, productMasterModelList.get(i).getProdShortName());
                statement.bindString(5, productMasterModelList.get(i).getProdStatus());
                statement.bindString(6, productMasterModelList.get(i).getProdType());
                statement.bindDouble(7, productMasterModelList.get(i).getProdShelfLife());
                statement.bindDouble(8, productMasterModelList.get(i).getProdGrossWgt());
                statement.bindDouble(9, productMasterModelList.get(i).getProdNetWgt());
                statement.bindString(10, productMasterModelList.get(i).getEanCode());
                statement.bindString(11, productMasterModelList.get(i).getProdHierLastValCode());
                statement.bindString(12, productMasterModelList.get(i).getProdWgtType());
                statement.bindString(13, productMasterModelList.get(i).getLobDivisionCode());
                statement.bindString(14, productMasterModelList.get(i).getLobSubDivisionCode());
                statement.bindString(15, productMasterModelList.get(i).getHsnCode());
                statement.bindString(16, productMasterModelList.get(i).getProductHierPathCode());
                statement.bindString(17, productMasterModelList.get(i).getProductHierPathName());
                statement.bindString(18, productMasterModelList.get(i).getUploadFlag());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertCompanyDetailList(List<CompanyModel> companyDetail) {

        String sql = "INSERT INTO " + TABLE_COMPANY_DETAIL + " VALUES (?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < companyDetail.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, companyDetail.get(i).getCmpCode());
                statement.bindString(2, companyDetail.get(i).getCmpName());
                statement.bindString(3, companyDetail.get(i).getCmpAddr1());
                statement.bindString(4, companyDetail.get(i).getCmpAddr2());
                statement.bindString(5, "");
                statement.bindString(6, companyDetail.get(i).getCity());
                statement.bindString(7, companyDetail.get(i).getState());
                statement.bindString(8, companyDetail.get(i).getCountry());
                statement.bindString(9, companyDetail.get(i).getPostalCode());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertBranchStockList(List<BranchStockModel> branchStockList) {

        String sql = "INSERT INTO " + TABLE_BRANCH_STOCK + " VALUES (?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < branchStockList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, branchStockList.get(i).getCmpCode());
                statement.bindString(2, branchStockList.get(i).getBranchCode());
                statement.bindString(3, branchStockList.get(i).getProdCode());
                statement.bindString(4, branchStockList.get(i).getStock());
                statement.bindString(5, branchStockList.get(i).getUpdatedTime());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }

    }

    private void insertKycMasterList(List<KycModel> branchStockList) {

        String sql = "INSERT INTO " + TABLE_KYC_MASTER + " VALUES (?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < branchStockList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, branchStockList.get(i).getCmpCode());
                statement.bindString(2, branchStockList.get(i).getKycCode());
                statement.bindString(3, branchStockList.get(i).getKycDesc());
                statement.bindString(4, branchStockList.get(i).getUpload());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }

    }

    private void insertKeyGenerator(List<CodeGeneratorModel> codeGeneratorModels) {

        String sql = "INSERT INTO " + TABLE_CODE_GENERATOR + " VALUES (?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < codeGeneratorModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(codeGeneratorModels.get(i).getCompanyCode()));
                statement.bindString(2, codeGeneratorModels.get(i).getDistributorCode());
                statement.bindString(3, codeGeneratorModels.get(i).getScreenName());
                statement.bindString(4, codeGeneratorModels.get(i).getPrefix());
                statement.bindString(5, codeGeneratorModels.get(i).getSuffixYy());
                statement.bindLong(6, codeGeneratorModels.get(i).getSuffixNn());
                statement.bindString(7, codeGeneratorModels.get(i).getUpload());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }

    }

    private void insertDistributorBalanceList(DistBalanceModel distBalanceList) {

        String sql = "INSERT INTO " + TABLE_DISTRIBUTOR_BALANCE + " VALUES (?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        try {
            statement.clearBindings();
            statement.bindString(1, String.valueOf(distBalanceList.getCmpCode()));
            statement.bindString(2, distBalanceList.getDistrCode());
            statement.bindDouble(3, distBalanceList.getBalance());
            statement.bindString(4, distBalanceList.getUploadFlag());
            statement.execute();
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w(DUPLICATE_TAG, e);
        }

    }

    private void insertDistributorAddress(List<DistAddressModel> distAddressModel) {

        SFASharedPref sfaSharedPref = getOurInstance();
        Map<String, String> aMap = new HashMap<>();
        aMap.put("distCode", distAddressModel.get(0).getDistrCode());
        aMap.put("distrBrName", distAddressModel.get(0).getDistrBrName());
        aMap.put("distAddress1", distAddressModel.get(0).getDistrBrAddr1());
        aMap.put("distAddress2", distAddressModel.get(0).getDistrBrAddr2());
        aMap.put("distAddress3", distAddressModel.get(0).getDistrBrAddr3());
        aMap.put("city", distAddressModel.get(0).getCity());
        aMap.put("state", distAddressModel.get(0).getDistrBrState());
        aMap.put("country", distAddressModel.get(0).getCountry());
        aMap.put("postalCode", String.valueOf(distAddressModel.get(0).getPostalCode()));
        aMap.put("tinNo", distAddressModel.get(0).getTinNo());
        aMap.put("pinNo", distAddressModel.get(0).getPinNo());
        aMap.put("cstNo", distAddressModel.get(0).getCstNo());
        aMap.put("phone", distAddressModel.get(0).getPhone());
        aMap.put("emailId", distAddressModel.get(0).getEmailId());
        aMap.put("pestLicNo", distAddressModel.get(0).getPestLicNo());
        sfaSharedPref.saveMap(PREF_DIST_ADDRESS, aMap);

    }

    private void insertVanSalesStocksForConfirm(List<ProductsVO> list) {

        SFASharedPref sfaSharedPref = getOurInstance();
        String invoiceNo = UUID.randomUUID().toString();
        String strDate = DateUtil.getCurrentYearMonthDate();

        String sql = "INSERT INTO " + TABLE_LOADING_STOCK + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(2, invoiceNo);
                statement.bindString(3, sfaSharedPref.readString(PREF_DISTRBRCODE));
                statement.bindString(4, sfaSharedPref.readString(PREF_SALESMANCODE));
                statement.bindString(5, strDate);
                statement.bindString(6, list.get(i).getProdCodeCaps());
                statement.bindString(7, list.get(i).getProdNameCaps());
                statement.bindString(8, list.get(i).getProdBatchCode());
                statement.bindDouble(9, list.get(i).getLoadQty());
                statement.bindDouble(10, list.get(i).getLoadQty());
                statement.bindDouble(11, list.get(i).getLoadQty());
                statement.bindDouble(12, list.get(i).getLoadQty());
                statement.bindDouble(13, 0);
                statement.bindDouble(14, 0);
                statement.bindDouble(15, list.get(i).getSellPriceCaps());
                statement.bindDouble(16, 0);
                statement.bindString(17, "P");
                statement.bindString(18, list.get(i).getDefaultUomidCaps());
                statement.bindString(19, "N");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void subBulkInsertMaster1(Sync sync) {
        if (!sync.getRetailerCategoryList().isEmpty()) {
            insertRetailerCategory(sync.getRetailerCategoryList());
        }
        if (!sync.getUomMasterList().isEmpty()) {
            insertUomMaster(sync.getUomMasterList());
        }

        if (!sync.getUomVansalesMasterList().isEmpty()) {
            insertVansalesUomMaster(sync.getUomVansalesMasterList());
        }

        if (!sync.getTaxModelArrayList().isEmpty()) {
            insertTaxStructureData(sync.getTaxModelArrayList());
        }
        if (!sync.getSchemeSlabList().isEmpty()) {
            insertSchemeSlabList(sync.getSchemeSlabList());
        }
    }

    private void subBulkInsertMaster2(Sync sync) {

        if (!sync.getSchemeCombiProductsList().isEmpty()) {
            insertSchemeCombiProductsList(sync.getSchemeCombiProductsList());
        }


        Log.d(TAG, "bulkInsert getSchemeSlabProductList :  " + sync.getSchemeSlabProductList().size());
        if (!sync.getSchemeSlabProductList().isEmpty()) {
            insertSchemeSlabProducts(sync.getSchemeSlabProductList());
        }


        Log.d(TAG, "bulkInsert getFocusBrandProductList :  " + sync.getFocusBrandProductList().size());
        if (!sync.getFocusBrandProductList().isEmpty()) {
            insertFocusBrandProductList(sync.getFocusBrandProductList());
        }
        Log.d(TAG, "bulkInsert getFocusBrandRetailerList :  " + sync.getFocusBrandRetailerList().size());
        if (!sync.getFocusBrandRetailerList().isEmpty()) {
            insertFocusBrandRetailerList(sync.getFocusBrandRetailerList());
        }
        Log.d(TAG, "bulkInsert getMustSellSKUProductList :  " + sync.getMustSellSKUProductList().size());
        if (!sync.getMustSellSKUProductList().isEmpty()) {
            insertMustSellSKUProductList(sync.getMustSellSKUProductList());
        }
        Log.d(TAG, "bulkInsert getMustSellSKURetailerList :  " + sync.getMustSellSKURetailerList().size());
        if (!sync.getMustSellSKURetailerList().isEmpty()) {
            insertMustSellSKURetailerList(sync.getMustSellSKURetailerList());
        }

        Log.d(TAG, "bulkInsert getGstStateMasterList :  " + sync.getGstStateMasterList().size());

        Log.d(TAG, "bulkInsert getAttributeMasterList :  " + sync.getAttributeMasterList().size());
        if (!sync.getAttributeMasterList().isEmpty()) {
            insertAttributeMasterList(sync.getAttributeMasterList());
        }
        Log.d(TAG, "bulkInsert getAttributeValuesList :  " + sync.getAttributeValuesList().size());
        if (!sync.getAttributeValuesList().isEmpty()) {
            insertAttributeValuesList(sync.getAttributeValuesList());
        }

        Log.d(TAG, "bulkDistInsert getOpeningStockMaster : " + sync.getOpeningStockMaster().size());
        if (!sync.getOpeningStockMaster().isEmpty()) {
            insertOpeningStockMaster(sync.getOpeningStockMaster());
        }

        Log.d(TAG, "bulkDistInsert getPurchaseReceiptDetails : " + sync.getPurchaseReceiptDetails().size());
        if (!sync.getPurchaseReceiptDetails().isEmpty()) {
            insertPurchaseReceipt(sync.getPurchaseReceiptDetails());
        }

        Log.d(TAG, "bulkDistInsert getSuggestOrderList : " + sync.getSuggestOrderList().size());
        if (!sync.getSuggestOrderList().isEmpty()) {
            insertProductSuggestions(sync.getSuggestOrderList());
        }

        Log.d(TAG, "bulkDistInsert getLogisticMaterialStock : " + sync.getLogisticMaterialStock().size());
        if (!sync.getLogisticMaterialStock().isEmpty()) {
            insertLogisticMaterialStock(sync.getLogisticMaterialStock());
        }


    }

    private void insertOpeningStockMaster(List<ProductsVO> list) {

        String sql = "INSERT INTO " + TABLE_OPENING_STOCK_MASTER + " VALUES (?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(2, list.get(i).getCmpCodeCaps());
                statement.bindString(3, list.get(i).getDistrCodeCaps());
                statement.bindString(4, list.get(i).getProdCodeCaps());
                statement.bindString(5, list.get(i).getProdNameCaps());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertFieldMTDWorkingReport(List<SubStockistModel> list) {

        String sql = "INSERT INTO " + TABLE_FIELD_ACTIVITY_REPORT + " VALUES (?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(2, list.get(i).getCmpCode());
                statement.bindString(3, list.get(i).getDistCode());
                statement.bindString(4, list.get(i).getDistName());
                statement.bindString(5, list.get(i).getSalesmanCode());
                statement.bindString(6, String.valueOf(list.get(i).getOpeningStock()));
                statement.bindString(7, String.valueOf(list.get(i).getClosingStock()));
                statement.bindString(8, String.valueOf(list.get(i).getOrderValue()));
                statement.bindString(9, String.valueOf(list.get(i).getStockDate()));
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertConfigurationData(List<ConfigModel> list) {

        String sql = "INSERT INTO " + TABLE_CONFIGURATION + " VALUES (?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(list.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(list.get(i).getTableName()));
                statement.bindString(3, Globals.checkNull(list.get(i).getCode().trim()));
                statement.bindString(4, Globals.checkNull(list.get(i).getDescription()));
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }

    }

    private void insertFeedbackReasons(List<ReasonVO> list) {

        String sql = "INSERT INTO " + TABLE_FEEDBACK_REASON + " VALUES (?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, list.get(i).getReasonCode());
                statement.bindString(2, list.get(i).getReasonName());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void bulkReportInsert(Sync sync) {


        if (!sync.getMtdFieldWorkingReport().isEmpty()) {
            insertFieldMTDWorkingReport(sync.getMtdFieldWorkingReport());
        }

        if (!sync.getBannerList().isEmpty()) {
            insertBannerList(sync.getBannerList());
        }

        /* Distributor address list */
        if (!sync.getDistAddressModel().isEmpty()) {
            insertDistributorAddress(sync.getDistAddressModel());
        }

        if (!sync.getStockLedgerReportList().isEmpty()) {
            insertStockLedgerReports(sync.getStockLedgerReportList());
        }
    }

    private void insertStockLedgerReports(List<StockledgerReportModel> stockledgerReportModel) {
        String sql = "INSERT INTO " + TABLE_STOCK_LEDGER_REPORT + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        db.beginTransaction();
        for (int i = 0; i < stockledgerReportModel.size(); i++) {
            statement.clearBindings();
            statement.bindString(1, stockledgerReportModel.get(i).getDistCode());
            statement.bindString(2, stockledgerReportModel.get(i).getDistCode());
            statement.bindString(3, stockledgerReportModel.get(i).getTransDate());
            statement.bindString(4, stockledgerReportModel.get(i).getProductCode());
            statement.bindString(5, stockledgerReportModel.get(i).getProductName());
            statement.bindString(6, String.valueOf(stockledgerReportModel.get(i).getOpeningSalableQty()));
            statement.bindString(7, String.valueOf(stockledgerReportModel.get(i).getOpeningUnSalableQty()));
            statement.bindString(8, String.valueOf(stockledgerReportModel.get(i).getOpeningOfferQty()));
            statement.bindString(9, String.valueOf(stockledgerReportModel.get(i).getPurchaseSalableQty()));
            statement.bindString(10, String.valueOf(stockledgerReportModel.get(i).getPurchaseRetUnSalableQty()));
            statement.bindString(11, String.valueOf(stockledgerReportModel.get(i).getPurchaseOfferQty()));
            statement.bindString(12, String.valueOf(stockledgerReportModel.get(i).getPurchaseRetSalableQty()));
            statement.bindString(13, String.valueOf(stockledgerReportModel.get(i).getPurchaseRetUnSalableQty()));
            statement.bindString(14, String.valueOf(stockledgerReportModel.get(i).getPurchaseRetOfferQty()));
            statement.bindString(15, String.valueOf(stockledgerReportModel.get(i).getSalesSalableQty()));
            statement.bindString(16, String.valueOf(stockledgerReportModel.get(i).getSalesUnSalableQty()));
            statement.bindString(17, String.valueOf(stockledgerReportModel.get(i).getSalesOfferQty()));
            statement.bindString(18, String.valueOf(stockledgerReportModel.get(i).getSalesRetSalableQty()));
            statement.bindString(19, String.valueOf(stockledgerReportModel.get(i).getSalesRetUnSalableQty()));
            statement.bindString(20, String.valueOf(stockledgerReportModel.get(i).getSalesRetOfferQty()));
            statement.bindString(21, String.valueOf(stockledgerReportModel.get(i).getAdjInSalableQty()));
            statement.bindString(22, String.valueOf(stockledgerReportModel.get(i).getAdjInUnSalableQty()));
            statement.bindString(23, String.valueOf(stockledgerReportModel.get(i).getAdjOutSalableQty()));
            statement.bindString(24, String.valueOf(stockledgerReportModel.get(i).getAdjOutUnSalableQty()));
            statement.bindString(25, String.valueOf(stockledgerReportModel.get(i).getAdjOutOfferQty()));
            statement.bindString(26, String.valueOf(stockledgerReportModel.get(i).getAdjInOfferQty()));
            statement.bindString(27, String.valueOf(stockledgerReportModel.get(i).getClosingStkSalableQty()));
            statement.bindString(28, String.valueOf(stockledgerReportModel.get(i).getClosingStkUnSalableQty()));
            statement.bindString(29, String.valueOf(stockledgerReportModel.get(i).getClosingStkOfferQty()));
            statement.execute();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private void insertBannerList(List<BannerModel> bannerList) {

        String sql = "INSERT INTO " + TABLE_BANNER + " VALUES (?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < bannerList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(2, bannerList.get(i).getCmpCode());
                statement.bindString(3, bannerList.get(i).getBannerDesc().trim());
                statement.bindString(4, bannerList.get(i).getFileName().trim());
                statement.bindString(5, bannerList.get(i).getOriginalFileName().trim());
                statement.bindString(6, bannerList.get(i).getType().trim());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }


    private void insertRetailerCategoryInfo() {
        //
        try {
            db.execSQL("INSERT INTO " + TABLE_RETAILER_CHANNELS + "(cmpCode, distrCode, channelCode, channelName) SELECT DISTINCT r.cmpCode, r.distrCode, r.channelCode, r.channelName FROM " + TABLE_RETAILER_CATEGORY + " r");
            db.execSQL("INSERT INTO " + TABLE_RETAILER_SUBCHANNELS + "(cmpCode, distrCode, channelCode, subChannelCode, subChannelName) SELECT DISTINCT r.cmpCode, r.distrCode, r.channelCode, r.subChannelCode, r.subChannelName FROM " + TABLE_RETAILER_CATEGORY + " r");
            db.execSQL("INSERT INTO " + TABLE_RETAILER_GROUPS + "(cmpCode, distrCode, channelCode, subChannelCode, groupCode, groupName) SELECT DISTINCT r.cmpCode, r.distrCode, r.channelCode, r.subChannelCode, r.groupCode, r.groupName FROM " + TABLE_RETAILER_CATEGORY + " r");
            db.execSQL("INSERT INTO " + TABLE_RETAILER_CLASSES + "(cmpCode, distrCode, channelCode, subChannelCode, groupCode, classCode, className) SELECT DISTINCT r.cmpCode, r.distrCode, r.channelCode, r.subChannelCode, r.groupCode, r.classCode, r.className FROM " + TABLE_RETAILER_CATEGORY + " r");
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w(TAG, e);
        }

    }

    /**
     * This method is used to check previous dayclose is done or not.
     *
     * @param distrCode distributor code
     * @param yesterday previous date
     * @return cursor
     */
    public boolean checkPreviousDayCloseFail(BaseDB baseDB, String distrCode, String yesterday) {
        String query = "SELECT * FROM " + TABLE_SYNC_PROGRESS + " WHERE loginCode = '" + distrCode + "' AND dayClose='N'  AND dayStart='Y'";//AND date <= '" + yesterday + "'
        db = baseDB.getDb();
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
        } finally {
            baseDB.closeDb();
        }

        return false;
    }

    public boolean isSyncDataAvailable(BaseDB baseDB, String distrCode) {
        db = baseDB.getDb();
        String query = "SELECT * FROM " + TABLE_SYNC_PROGRESS + " WHERE loginCode = '" + distrCode + "'";
        Cursor c = db.rawQuery(query, null);
        try {
            if (c != null && c.getCount() > 0) {
                c.close();
                return true;
            }
        } finally {
            baseDB.closeDb();
        }
        return false;
    }

    public boolean isDataAvailable(BaseDB baseDB, String distrCode, String today) {
        db = baseDB.getDb();
        String query = "SELECT * FROM " + TABLE_SYNC_PROGRESS + " WHERE loginCode = '" + distrCode + "' AND date = '" + today + "'";
        try {
            Cursor c = db.rawQuery(query, null);
            if (c != null && c.getCount() > 0) {
                c.close();

                return true;
            }
        } finally {
            baseDB.closeDb();
        }

        return false;
    }

    /**
     * This method is used to check the day start is performed or not.
     *
     * @param distrCode distributor code
     * @param today     current date
     * @return cursor
     */
    public boolean checkDayStartPerformed(BaseDB baseDB, String distrCode, String today) {

        String query = "SELECT * FROM " + TABLE_SYNC_PROGRESS + " WHERE loginCode = '" + distrCode + "' AND date = '" + today + "'";
        db = baseDB.getDb();
        Cursor c = db.rawQuery(query, null);
        try {
            if (c != null && c.getCount() > 0) {
                c.close();
                return true;
            }
        } finally {
            baseDB.closeDb();
        }
        return false;
    }


    public void insertSyncProgress(BaseDB baseDB, String date,
                                   String dayStart, String upload, String dayClose) {

        SFASharedPref preference = SFASharedPref.getOurInstance();
        String distrCode = preference.readString(PREF_DISTRCODE);
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN_CODE, distrCode);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_DAY_START, dayStart);
        values.put(COLUMN_UPLOAD, upload);
        values.put(COLUMN_DAY_CLOSE, dayClose);
        db = baseDB.getDb();
        try {
            db.insert(TABLE_SYNC_PROGRESS, null, values);
        } finally {
            baseDB.closeDb();
        }
    }


    private JSONObject getSchemeReqJson(Cursor c) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            jsonObject.put(TAG_CMP_CODE, c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
            jsonObject.put(TAG_DISTR_CODE, c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
        }
        return jsonObject;
    }

    /**
     * This method is used to delete the existing info from all the tables for
     * the logged in user
     */
    public void removeExistingTableInfo(BaseDB baseDB, String distrCode, String cmpCode, boolean deleteOnlyMaster) {
        db = baseDB.getDb();
        if (!deleteOnlyMaster) {
            db.execSQL("DELETE FROM " + TABLE_ORDER_BOOKING);
            db.execSQL("DELETE FROM " + TABLE_ORDERED_APPLIED_SCHEME);
            db.execSQL("DELETE FROM " + TABLE_CONFIGURATION);
            db.execSQL("DELETE FROM " + TABLE_LOGIN_TIME_CAPTURE + " WHERE Upload='Y'");
            db.execSQL("DELETE FROM " + TABLE_BANNER);





//            db.execSQL("DELETE FROM " + TABLE_BILL_INVOIC_HEADER + " WHERE distrCode = '" + distrCode + "'  AND uploadFlag='Y'");
//            db.execSQL("DELETE FROM " + TABLE_BILL_INVOICE_DETAILS + " WHERE distrCode = '" + distrCode + "' AND uploadFlag='Y'");
//            db.execSQL("DELETE FROM " + TABLE_BILLING_APPLIED_SCHEME + " WHERE distrCode = '" + distrCode + "' AND Upload='Y'");
//            //This  is to refresh odometer current session preference
//            SFASharedPref.getOurInstance().writeInt(CURRENT_SESSION, 0);
//            db.execSQL("DELETE FROM " + TABLE_PURCHASE_RECEIPT_CONFIRMATION + " WHERE distrCode = '" + distrCode + "' AND Upload='Y' AND confirm_status='Y'");
//            db.execSQL("DELETE FROM " + TABLE_BILLING + " WHERE distrCode = '" + distrCode + "'");
//            db.execSQL("DELETE FROM " + TABLE_STOCK_TAKE + " WHERE distrCode = '" + distrCode + "'");
//            db.execSQL("DELETE FROM " + TABLE_SALES_RETURN + " WHERE distrCode = '" + distrCode + "'");
//            db.execSQL("DELETE FROM " + TABLE_CASH_COLLECTION + " WHERE distrCode = '" + distrCode + "'");
//            db.execSQL("DELETE FROM " + TABLE_CHEQUE_COLLECTION + " WHERE distrCode = '" + distrCode + "'");
//            db.execSQL("DELETE FROM " + TABLE_RETAILER_VISIT + " WHERE distrCode = '" + distrCode + "'");
//            db.execSQL("DELETE FROM " + TABLE_CUSTOMER_UPLOAD_STATUS + " WHERE distrCode = '" + distrCode + "'");
//            db.execSQL("DELETE FROM " + TABLE_NEW_RETAILER);
//            db.execSQL("DELETE FROM " + TABLE_SALESMAN_TRACKER + " WHERE distrCode = '" + distrCode + "'");
//            db.execSQL("DELETE FROM " + TABLE_ORDER_BOOKING_TRACKER + " WHERE distrCode = '" + distrCode + "'");
//            db.execSQL("DELETE FROM " + TABLE_BILLING_COLLECTION + " WHERE distrCode = '" + distrCode + "'");
//            if (SFASharedPref.getOurInstance().readBoolean(SFASharedPref.PREF_STOCK_UNLOADING))
//                db.execSQL("DELETE FROM " + TABLE_LOADING_STOCK);
//            db.execSQL("DELETE FROM " + TABLE_PURCHASE_PARTIAL_RETURN + " WHERE distrCode = '" + distrCode
//                    + "' AND Upload='Y'");
//            db.execSQL("DELETE FROM " + TABLE_PURCHASE_FULL_RETURN + " WHERE distrCode = '" + distrCode
//                    + "' AND Upload='Y'");
//            db.execSQL("DELETE FROM " + TABLE_SALES_HIERARY);
//
//            db.execSQL("DELETE FROM " + TABLE_VAN_SALES_PRODUCT_FILTERS);
//
//            db.execSQL("DELETE FROM " + TABLE_NEW_RETAILER_IMAGES);
//
//            //CustomerModel Master
//            db.execSQL("DELETE FROM " + TABLE_PURCHASE_ORDER_BOOKING);
//            db.execSQL("DELETE FROM " + TABLE_ROUTE_SEQUENCING);
//
//            db.execSQL("DELETE FROM " + TABLE_STOCK_ON_HAND);
//            db.execSQL("DELETE FROM " + TABLE_PURCHASE_INVOICE);

//            db.execSQL("DELETE FROM " + TABLE_STOCK_ADJ_HEADER + " WHERE distrCode = '" + distrCode + "' AND uploadFlag='Y'");
//            db.execSQL("DELETE FROM " + TABLE_STOCK_ADJ_DETAILS + " WHERE distrCode = '" + distrCode + "' AND uploadFlag='Y'");
//            db.execSQL("DELETE FROM " + TABLE_SALES_RETURN_HEADER + " WHERE distrCode = '" + distrCode + "' AND uploadFlag='Y'");
//            db.execSQL("DELETE FROM " + TABLE_SALES_RETURN_DETAILS + " WHERE distrCode = '" + distrCode + "' AND uploadFlag='Y'");
//            db.execSQL("DELETE FROM " + TABLE_SALES_DB_CRNOTE);
//            db.execSQL("DELETE FROM " + TABLE_INVOICE_CR_DB_ADJUSTMENT);
//            db.execSQL("DELETE FROM " + TABLE_BILL_INVOIC_HEADER);
//            db.execSQL("DELETE FROM " + TABLE_BILL_INVOICE_DETAILS);
//            db.execSQL("DELETE FROM " + TABLE_BILL_INVOICE_SAVED_DETAILS);
//            db.execSQL("DELETE FROM " + TABLE_DISTR_REPORT);
        }


        db.execSQL("DELETE FROM " + TABLE_QUICK_ACTION_MENUS);
        db.execSQL("DELETE FROM " + TABLE_MAPPED_COMPANY);
        db.execSQL("DELETE FROM " + TABLE_MAPPED_USER);
        db.execSQL("DELETE FROM " + TABLE_MAPPED_CUSTOMER);
        db.execSQL("DELETE FROM " + TABLE_AUTO_QUICK_ACTION_MENUS);
        db.execSQL("DELETE FROM " + TABLE_PREVIOUS_ORDER_BOOKING_REPORT_HEADER);
        db.execSQL("DELETE FROM " + TABLE_PREVIOUS_ORDER_BOOKING_REPORT_DETAILS);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER);
        db.execSQL("DELETE FROM " + TABLE_MESSAGES);
        db.execSQL("DELETE FROM " + TABLE_NOTIFICATION_TYPE);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_SHIP_ADDRESS);
        db.execSQL("DELETE FROM " + TABLE_CATEGORY_SEQUENCE);
        db.execSQL("DELETE FROM " + TABLE_GST_STATE_MASTER);
        db.execSQL("DELETE FROM " + TABLE_SCHEME_DEFINITION_LIST);
        db.execSQL("DELETE FROM " + TABLE_SCHEME_SLAB_LIST);
        db.execSQL("DELETE FROM " + TABLE_SCHEME_SLAB_PRODUCT_LIST);
        db.execSQL("DELETE FROM " + TABLE_SCHEME_COMBI_PRODUCTS);
        db.execSQL("DELETE FROM " + TABLE_ORDER_PRODUCT_FILTERS);
        db.execSQL("DELETE FROM " + TABLE_DISTRIBUTOR);
        db.execSQL("DELETE FROM " + TABLE_DISTR_LOB);
        db.execSQL("DELETE FROM " + TABLE_DISTRIBUTOR_STOCK);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_STOCK);
        db.execSQL("DELETE FROM " + TABLE_LOB);
        db.execSQL("DELETE FROM " + TABLE_UPDATE_LOCATION);
        db.execSQL("DELETE FROM " + TABLE_CODE_GENERATOR);
        db.execSQL("DELETE FROM " + TABLE_COMPANY_DETAIL);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_MASTER);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_BATCH_MASTER);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_HIER_LEVEL);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_HIER_VALUE);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_UOM_MASTER);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_TAX_MASTER);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_SHIP_ADDRESS);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST);
        db.execSQL("DELETE FROM " + TABLE_FEEDBACK);
        db.execSQL("DELETE FROM " + TABLE_BRANCH_STOCK);
        db.execSQL("DELETE FROM " + TABLE_FEEDBACK_MASTER);
        db.execSQL("DELETE FROM " + TABLE_PREVIOUS_ORDER_BOOKING_STATUS);






//        db.execSQL("DELETE FROM " + TABLE_KEY_VALUE_ENCODE);
//        db.execSQL("DELETE FROM " + TABLE_LEAVE_REASONS);
//        db.execSQL("DELETE FROM " + TABLE_FEEDBACK_REASON);
//
//        db.execSQL("DELETE FROM " + TABLE_ROUTE + " WHERE distrCode = '" + distrCode + "'");
//        db.execSQL("DELETE FROM " + TABLE_RETAILER + " WHERE distrCode = '" + distrCode + "'");
//        db.execSQL("DELETE FROM " + TABLE_PENDING_BILL + " WHERE distrCode = '" + distrCode + "'");
//
//        db.execSQL("DELETE FROM " + TABLE_VANSALES_PRODUCTS);
//        db.execSQL("DELETE FROM " + TABLE_PRODUCT_SUGGESTIONS);
//
//        db.execSQL("DELETE FROM " + TABLE_PRODUCT_CATEGORY);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_CATEGORY_ID + " WHERE DistrCode = '" + distrCode + "'");
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_CATEGORY);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_CHANNELS);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_SUBCHANNELS);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_GROUPS);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_CLASSES);
//
//        db.execSQL("DELETE FROM " + TABLE_BANK_NAMES + " WHERE distrCode = '" + distrCode + "'");
//
//        db.execSQL("DELETE FROM " + TABLE_REASONS);
//        db.execSQL("DELETE FROM " + TABLE_UOM_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_VANSALES_UOM_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_TAX_STRUCTURE);
//
//        db.execSQL("DELETE FROM " + TABLE_FOCUSBRAND_PRODUCTS);
//        db.execSQL("DELETE FROM " + TABLE_FOCUSBRAND_RETAILERS);
//        db.execSQL("DELETE FROM " + TABLE_MUSTSELL_RETAILERS);
//        db.execSQL("DELETE FROM " + TABLE_MUSTSELL_SKU_PRODUCTS);
//        db.execSQL("DELETE FROM " + TABLE_ATTRIBUTE_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_ATTRIBUTE_VALUES);

//        db.execSQL("DELETE FROM " + TABLE_SALES_HIERARY);
//        db.execSQL("DELETE FROM " + TABLE_FIELD_ACTIVITY_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_OPENING_STOCK_MASTER);
//
//        db.execSQL("DELETE FROM " + TABLE_PURCHASE_RECEIPT_CONFIRMATION + " WHERE distrCode = '" + distrCode
//                + "' AND Upload='N' AND confirm_status='N'");
        //db.execSQL("DELETE FROM " + TABLE_LOGIN_TIME_CAPTURE + " WHERE Upload='Y'");

//        db.execSQL("DELETE FROM " + TABLE_RETAILER_SCHEME_PRODUCTS_LIST);
//        db.execSQL("DELETE FROM " + TABLE_ROUTE_SCHEME_ZIP_LIST);

        //SMDMS

        //Distrbutors
//        db.execSQL("DELETE FROM " + TABLE_ROUTE);
//
//        //CodeGenerator
//        db.execSQL("DELETE FROM " + TABLE_CODE_GENERATOR_HISTORY);
//
//        //WD Infrastructure
//        db.execSQL("DELETE FROM " + TABLE_WD_STORAGE);
//        db.execSQL("DELETE FROM " + TABLE_WD_FREEZER);
//
//        //BranchStock
//        db.execSQL("DELETE FROM " + TABLE_DISTRIBUTOR_BALANCE);
//
//
//        db.execSQL("DELETE FROM " + TABLE_PURCHASE_INVOICE);
//        db.execSQL("DELETE FROM " + TABLE_LOGISTIC_MATERIA
//        db.execSQL("DELETE FROM " + TABLE_STOCK_ON_HAND);
//        db.execSQL("DELETE FROM " + TABLE_GEO_HIER_VALUE);
//        db.execSQL("DELETE FROM " + TABLE_GEO_HIER_LEVEL);

//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_ROUTE + " WHERE distrCode = '" + distrCode + "' AND upload='Y' AND cmpCode='" + cmpCode + "'");
//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_BANK + " WHERE distrCode = '" + distrCode + "' AND upload='Y' AND cmpCode='" + cmpCode + "'");
//
//        //Salesman Table
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_MASTER + " WHERE distrCode = '" + distrCode + "' AND upload='Y' AND cmpCode='" + cmpCode + "'");
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_MASTER_KYC + " WHERE distrCode = '" + distrCode + "' AND upload='Y' AND cmpCode='" + cmpCode + "'");
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_LOB_MAPPING + " WHERE distrCode = '" + distrCode + "' AND upload='Y' AND cmpCode='" + cmpCode + "'");
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_ROUTE_MAPPING + " WHERE distrCode = '" + distrCode + "' AND upload='Y' AND cmpCode='" + cmpCode + "'");
//        db.execSQL("DELETE FROM " + TABLE_KYC_MASTER);
//
//        db.execSQL("DELETE FROM " + TABLE_PREVIOUS_PURCHASE_ORDERS);
//        db.execSQL("DELETE FROM " + TABLE_RATE_EDIT);
//        db.execSQL("DELETE FROM " + TABLE_SUPPLIER);
//        db.execSQL("DELETE FROM " + TABLE_PENDING_BILLS);
//        db.execSQL("DELETE FROM " + TABLE_ORDER_BOOKING_REPORT_DETAILS);
//        db.execSQL("DELETE FROM " + TABLE_ORDER_BOOKING_REPORT_HEADER);

//        db.execSQL("DELETE FROM " + TABLE_SCHEME_DISTRIBUTOR_BUDGET);
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_CUSTOMER_LIST);
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_ZIP_LIST);
//
//        db.execSQL("DELETE FROM " + TABLE_COLLECTION);
//        db.execSQL("DELETE FROM " + TABLE_SALES_DB_CRNOTE);
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_ROUTE_COVERAGE);
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_ROUTE_COVERAGE_PLAN);
//        db.execSQL("DELETE FROM " + TABLE_CONTRACT_PRICING);
//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_PRODUCT_MAPPING);
//        db.execSQL("DELETE FROM " + TABLE_BILL_ADJUSTMENT);
//        db.execSQL("DELETE FROM " + TABLE_DISTR_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_SALES_REPORT_DETAIL);
//        db.execSQL("DELETE FROM " + TABLE_SALES_DAY_WISE_REPORT_DETAIL);
//        db.execSQL("DELETE FROM " + TABLE_SALES_WEEK_WISE_REPORT_DETAIL);
//        db.execSQL("DELETE FROM " + TABLE_SALES_CUSTOMER_WISE_REPORT_DETAIL);
//        db.execSQL("DELETE FROM " + TABLE_SALES_CHANNEL_WISE_REPORT_DETAIL);
//        db.execSQL("DELETE FROM " + TABLE_PURCHASE_INVOICE_REPORT_DETAIL);
//        db.execSQL("DELETE FROM " + TABLE_SALES_SALESMAN_WISE_REPORT_DETAIL);
//        db.execSQL("DELETE FROM " + TABLE_SALES_ROUTE_WISE_REPORT_DETAIL);
//        db.execSQL("DELETE FROM " + TABLE_YTD_ROUTE_WISE_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_YTD_SALESMAN_WISE_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_YTD_PRODUCT_WISE_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_YTD_CUSTOMER_WISE_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_MTD_SALES_PRODHIER_PRODUCT_WISE_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_REPORT_PROCESS_MAPPING);
//        db.execSQL("DELETE FROM " + TABLE_REPORT_FIELD_MAPPING);
//        db.execSQL("DELETE FROM " + TABLE_BULLETIN_BOARD);
//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_APPROVAL_DATA + " WHERE distrCode = '" + distrCode + "' AND uploadFlag='Y' AND cmpCode='" + cmpCode + "'");
//        db.execSQL("DELETE FROM " + TABLE_TEMP_CUSTOMER);
//        db.execSQL("DELETE FROM " + TABLE_TEMP_CUSTOMER_ROUTE);
//        db.execSQL("DELETE FROM " + TABLE_TEMP_CUSTOMER_SHIP_ADDRESS);
//        db.execSQL("DELETE FROM " + TABLE_TEMP_ROUTE);
//
//        db.execSQL("DELETE FROM " + TABLE_BATCH_TRANSFER_DETAILS + " WHERE distrCode = '" + distrCode + "' AND uploadFlag='Y' AND cmpCode='" + cmpCode + "'");
        baseDB.closeDb();
    }


    public void removeExistingTableData(BaseDB baseDB) {
        db = baseDB.getDb();

        //Transaction
        db.execSQL("DELETE FROM " + TABLE_ORDER_BOOKING);
        db.execSQL("DELETE FROM " + TABLE_UPDATE_LOCATION);
        db.execSQL("DELETE FROM " + TABLE_ORDERED_APPLIED_SCHEME);
        db.execSQL("DELETE FROM " + TABLE_CONFIGURATION);
        db.execSQL("DELETE FROM " + TABLE_LOGIN_TIME_CAPTURE);
        db.execSQL("DELETE FROM " + TABLE_BANNER);

        //Master
        db.execSQL("DELETE FROM " + TABLE_MAPPED_COMPANY);
        db.execSQL("DELETE FROM " + TABLE_MAPPED_USER);
        db.execSQL("DELETE FROM " + TABLE_MAPPED_CUSTOMER);
        db.execSQL("DELETE FROM " + TABLE_QUICK_ACTION_MENUS);
        db.execSQL("DELETE FROM " + TABLE_AUTO_QUICK_ACTION_MENUS);
        db.execSQL("DELETE FROM " + TABLE_PREVIOUS_ORDER_BOOKING_REPORT_HEADER);
        db.execSQL("DELETE FROM " + TABLE_PREVIOUS_ORDER_BOOKING_REPORT_DETAILS);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER);
        db.execSQL("DELETE FROM " + TABLE_MESSAGES);
        db.execSQL("DELETE FROM " + TABLE_NOTIFICATION_TYPE);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_SHIP_ADDRESS);
        db.execSQL("DELETE FROM " + TABLE_CATEGORY_SEQUENCE);
        db.execSQL("DELETE FROM " + TABLE_GST_STATE_MASTER);
        db.execSQL("DELETE FROM " + TABLE_SCHEME_DEFINITION_LIST);
        db.execSQL("DELETE FROM " + TABLE_SCHEME_SLAB_LIST);
        db.execSQL("DELETE FROM " + TABLE_SCHEME_SLAB_PRODUCT_LIST);
        db.execSQL("DELETE FROM " + TABLE_SCHEME_COMBI_PRODUCTS);
        db.execSQL("DELETE FROM " + TABLE_ORDER_PRODUCT_FILTERS);
        db.execSQL("DELETE FROM " + TABLE_DISTRIBUTOR);
        db.execSQL("DELETE FROM " + TABLE_DISTR_LOB);
        db.execSQL("DELETE FROM " + TABLE_DISTRIBUTOR_STOCK);
        db.execSQL("DELETE FROM " + TABLE_LOB);
        db.execSQL("DELETE FROM " + TABLE_CODE_GENERATOR);
        db.execSQL("DELETE FROM " + TABLE_COMPANY_DETAIL);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_MASTER);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_BATCH_MASTER);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_HIER_LEVEL);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_HIER_VALUE);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_UOM_MASTER);
        db.execSQL("DELETE FROM " + TABLE_PRODUCT_TAX_MASTER);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_SHIP_ADDRESS);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST);
        db.execSQL("DELETE FROM " + TABLE_FEEDBACK);
        db.execSQL("DELETE FROM " + TABLE_BRANCH_STOCK);
        db.execSQL("DELETE FROM " + TABLE_FEEDBACK_MASTER);
        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_STOCK);
        db.execSQL("DELETE FROM " + TABLE_PREVIOUS_ORDER_BOOKING_STATUS);





//        db.execSQL("DELETE FROM " + TABLE_ORDER_BOOKING);
//        db.execSQL("DELETE FROM " + TABLE_PURCHASE_RECEIPT_CONFIRMATION);
//        db.execSQL("DELETE FROM " + TABLE_BILLING);
//        db.execSQL("DELETE FROM " + TABLE_STOCK_TAKE);
//        db.execSQL("DELETE FROM " + TABLE_SALES_RETURN);
//        db.execSQL("DELETE FROM " + TABLE_CASH_COLLECTION);
//        db.execSQL("DELETE FROM " + TABLE_CHEQUE_COLLECTION);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_VISIT);
//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_UPLOAD_STATUS);
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_TRACKER);
//        db.execSQL("DELETE FROM " + TABLE_NEW_RETAILER);
//        db.execSQL("DELETE FROM " + TABLE_ORDER_BOOKING_TRACKER);
//        db.execSQL("DELETE FROM " + TABLE_UPDATE_LOCATION);
//        db.execSQL("DELETE FROM " + TABLE_MESSAGES);
//        db.execSQL("DELETE FROM " + TABLE_BILLING_COLLECTION);
//        db.execSQL("DELETE FROM " + TABLE_ORDERED_APPLIED_SCHEME);
//        db.execSQL("DELETE FROM " + TABLE_BILLING_APPLIED_SCHEME);
//        db.execSQL("DELETE FROM " + TABLE_LOADING_STOCK);
//        db.execSQL("DELETE FROM " + TABLE_PURCHASE_PARTIAL_RETURN);
//        db.execSQL("DELETE FROM " + TABLE_PURCHASE_FULL_RETURN);
//        db.execSQL("DELETE FROM " + TABLE_LEAVE_REASONS);
//        db.execSQL("DELETE FROM " + TABLE_FEEDBACK_REASON);
//        db.execSQL("DELETE FROM " + TABLE_ROUTE);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER);
//        db.execSQL("DELETE FROM " + TABLE_PENDING_BILL);
//        db.execSQL("DELETE FROM " + TABLE_PRODUCTS);
//        db.execSQL("DELETE FROM " + TABLE_VANSALES_PRODUCTS);
//        db.execSQL("DELETE FROM " + TABLE_PRODUCT_SUGGESTIONS);
//        db.execSQL("DELETE FROM " + TABLE_CATEGORY_SEQUENCE);
//        db.execSQL("DELETE FROM " + TABLE_PRODUCT_CATEGORY);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_CATEGORY_ID);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_CATEGORY);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_CHANNELS);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_SUBCHANNELS);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_GROUPS);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_CLASSES);
//        db.execSQL("DELETE FROM " + TABLE_BANK_NAMES);
//        db.execSQL("DELETE FROM " + TABLE_REASONS);
//        db.execSQL("DELETE FROM " + TABLE_UOM_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_VANSALES_UOM_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_TAX_STRUCTURE);
//        db.execSQL("DELETE FROM " + TABLE_GST_STATE_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_FOCUSBRAND_PRODUCTS);
//        db.execSQL("DELETE FROM " + TABLE_FOCUSBRAND_RETAILERS);
//        db.execSQL("DELETE FROM " + TABLE_MUSTSELL_RETAILERS);
//        db.execSQL("DELETE FROM " + TABLE_MUSTSELL_SKU_PRODUCTS);
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_DEFINITION_LIST);
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_SLAB_LIST);
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_SLAB_PRODUCT_LIST);
//        db.execSQL("DELETE FROM " + TABLE_ATTRIBUTE_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_ATTRIBUTE_VALUES);
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_COMBI_PRODUCTS);
//        db.execSQL("DELETE FROM " + TABLE_SYNC_PROGRESS);
//        db.execSQL("DELETE FROM " + TABLE_CONFIGURATION);
//        db.execSQL("DELETE FROM " + TABLE_SALES_HIERARY);
//        db.execSQL("DELETE FROM " + TABLE_SALES_HIERARY_VALUE);
//        db.execSQL("DELETE FROM " + TABLE_FIELD_ACTIVITY_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_OPENING_STOCK_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_LOGIN_TIME_CAPTURE);
//        db.execSQL("DELETE FROM " + TABLE_QUICK_ACTION_MENUS);
//        db.execSQL("DELETE FROM " + TABLE_AUTO_QUICK_ACTION_MENUS);
//        db.execSQL("DELETE FROM " + TABLE_RETAILER_SCHEME_PRODUCTS_LIST);
//        db.execSQL("DELETE FROM " + TABLE_ROUTE_SCHEME_ZIP_LIST);
//        db.execSQL("DELETE FROM " + TABLE_VAN_SALES_PRODUCT_FILTERS);
//        db.execSQL("DELETE FROM " + TABLE_ORDER_PRODUCT_FILTERS);
//        db.execSQL("DELETE FROM " + TABLE_NEW_RETAILER_IMAGES);
//
//        //SMDMS
//        db.execSQL("DELETE FROM " + TABLE_KEY_VALUE_ENCODE);
//        //Distrbutors
//        db.execSQL("DELETE FROM " + TABLE_DISTRIBUTOR);
//        db.execSQL("DELETE FROM " + TABLE_DISTRIBUTOR_STOCK);
//        //Lob
//        db.execSQL("DELETE FROM " + TABLE_LOB);
//        db.execSQL("DELETE FROM " + TABLE_DISTR_LOB);
//        //Route
//        db.execSQL("DELETE FROM " + TABLE_ROUTE);
//        //CodeGenerator
//        db.execSQL("DELETE FROM " + TABLE_CODE_GENERATOR);
//        db.execSQL("DELETE FROM " + TABLE_CODE_GENERATOR_HISTORY);
//        //WD Infrastructure
//        db.execSQL("DELETE FROM " + TABLE_WD_STORAGE);
//        db.execSQL("DELETE FROM " + TABLE_WD_FREEZER);
//        //BranchStock
//        db.execSQL("DELETE FROM " + TABLE_DISTRIBUTOR_BALANCE);
//        db.execSQL("DELETE FROM " + TABLE_BRANCH_STOCK);
//
//        db.execSQL("DELETE FROM " + TABLE_COMPANY_DETAIL);
//        db.execSQL("DELETE FROM " + TABLE_PRODUCT_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_PRODUCT_BATCH_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_PRODUCT_HIER_LEVEL);
//        db.execSQL("DELETE FROM " + TABLE_PRODUCT_HIER_VALUE);
//        db.execSQL("DELETE FROM " + TABLE_PRODUCT_UOM_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_PRODUCT_TAX_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_STOCK_ON_HAND);
//        db.execSQL("DELETE FROM " + TABLE_GEO_HIER_VALUE);
//        db.execSQL("DELETE FROM " + TABLE_GEO_HIER_LEVEL);
//        //CustomerModel Master
//        db.execSQL("DELETE FROM " + TABLE_PURCHASE_ORDER_BOOKING);
//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER);
//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_SHIP_ADDRESS);
//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_ROUTE);
//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_BANK);
//
//        //Salesman Table
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_MASTER);
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_MASTER_KYC);
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_LOB_MAPPING);
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_ROUTE_MAPPING);
//        db.execSQL("DELETE FROM " + TABLE_KYC_MASTER);
//
//        db.execSQL("DELETE FROM " + TABLE_PREVIOUS_PURCHASE_ORDERS);
//        db.execSQL("DELETE FROM " + TABLE_ROUTE_SEQUENCING);
//        db.execSQL("DELETE FROM " + TABLE_RATE_EDIT);
//        db.execSQL("DELETE FROM " + TABLE_SUPPLIER);
//        db.execSQL("DELETE FROM " + TABLE_PENDING_BILLS);
//        db.execSQL("DELETE FROM " + TABLE_BILL_INVOIC_HEADER);
//        db.execSQL("DELETE FROM " + TABLE_BILL_INVOICE_DETAILS);
//        db.execSQL("DELETE FROM " + TABLE_BILL_INVOICE_SAVED_DETAILS);
//
//        db.execSQL("DELETE FROM " + TABLE_COLLECTION);
//        db.execSQL("DELETE FROM " + TABLE_SALES_DB_CRNOTE);
//        db.execSQL("DELETE FROM " + TABLE_STOCK_ADJ_HEADER);
//        db.execSQL("DELETE FROM " + TABLE_STOCK_ADJ_DETAILS);
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_ROUTE_COVERAGE);
//        db.execSQL("DELETE FROM " + TABLE_SALESMAN_ROUTE_COVERAGE_PLAN);
//        db.execSQL("DELETE FROM " + TABLE_SALES_RETURN_HEADER);
//        db.execSQL("DELETE FROM " + TABLE_SALES_RETURN_DETAILS);
//        db.execSQL("DELETE FROM " + TABLE_PREVIOUS_ORDER_BOOKING_REPORT_DETAILS);
//        db.execSQL("DELETE FROM " + TABLE_PREVIOUS_ORDER_BOOKING_REPORT_HEADER);
//
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_DEFINITION_LIST);
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_SLAB_LIST);
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_SLAB_PRODUCT_LIST);
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_DISTRIBUTOR_BUDGET);
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_CUSTOMER_LIST);
//        db.execSQL("DELETE FROM " + TABLE_SCHEME_ZIP_LIST);
//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST);
//        db.execSQL("DELETE FROM " + TABLE_CONTRACT_PRICING);
//        db.execSQL("DELETE FROM " + TABLE_INVOICE_CR_DB_ADJUSTMENT);
//        db.execSQL("DELETE FROM " + TABLE_BILL_ADJUSTMENT);
//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_PRODUCT_MAPPING);
//        db.execSQL("DELETE FROM " + TABLE_LOGISTIC_MATERIAL_STOCK);
//        db.execSQL("DELETE FROM " + TABLE_YTD_ROUTE_WISE_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_YTD_SALESMAN_WISE_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_YTD_PRODUCT_WISE_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_YTD_CUSTOMER_WISE_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_SALES_SALESMAN_WISE_REPORT_DETAIL);
//        db.execSQL("DELETE FROM " + TABLE_SALES_ROUTE_WISE_REPORT_DETAIL);
//        db.execSQL("DELETE FROM " + TABLE_MTD_SALES_PRODHIER_PRODUCT_WISE_REPORT);
//        db.execSQL("DELETE FROM " + TABLE_REPORT_PROCESS_MAPPING);
//        db.execSQL("DELETE FROM " + TABLE_REPORT_FIELD_MAPPING);
//        db.execSQL("DELETE FROM " + TABLE_BULLETIN_BOARD);
//        db.execSQL("DELETE FROM " + TABLE_CUSTOMER_APPROVAL_DATA);
//        db.execSQL("DELETE FROM " + TABLE_TEMP_CUSTOMER);
//        db.execSQL("DELETE FROM " + TABLE_TEMP_CUSTOMER_ROUTE);
//        db.execSQL("DELETE FROM " + TABLE_TEMP_CUSTOMER_SHIP_ADDRESS);
//        db.execSQL("DELETE FROM " + TABLE_TEMP_ROUTE);db.execSQL("DELETE FROM " + TABLE_FEEDBACK);
//        db.execSQL("DELETE FROM " + TABLE_BATCH_TRANSFER_DETAILS);
        baseDB.closeDb();
    }

    /**
     * This method is used to delete the existing Report Table info from all the tables for
     * the logged in user
     */
    public void removeExistingReportTableInfo(BaseDB baseDB) {
        db = baseDB.getDb();
        db.execSQL("DELETE FROM " + TABLE_FIELD_ACTIVITY_REPORT);
        db.execSQL("DELETE FROM " + TABLE_BANNER);
        db.execSQL("DELETE FROM " + TABLE_STOCK_LEDGER_REPORT);
        baseDB.closeDb();
    }

    public void deleteSyncTable(BaseDB baseDB) {
        db = baseDB.getDb();
        db.execSQL("DELETE FROM " + TABLE_SYNC_PROGRESS + " WHERE dayStart = 'Y' AND DayClose = 'Y'");
        baseDB.closeDb();
    }

    public List<OrderBookingVO> getPurchaseOrderBookingLineItem(BaseDB baseDB, String cmpCode, String distCode) {
        db = baseDB.getDb();

        String query = " SELECT ob.cmpCode,ob.distrCode,ob.invoiceno,\n" +
                "       ob.conversionfactor,\n" +
                "       ob.baseUom,\n" +
                "       ob.batchcode,\n" +
                "       ob.prodcode,\n" +
                "       ob.OrderQty as OrderQty,\n" +
                "       ob.purchasePrice,\n" +
                "       p.prodName,\n" +
                "       ob.mrp,\n" +
                "       ob.taxPerProduct,\n" +
                "       ob.orderdate,\n" +
                "       ob.ordervalue,\n" +
                "       ob.CGSTValue,\n" +
                "       ob.CGSTPerc,\n" +
                "       ob.SGSTValue,\n" +
                "       ob.SGSTPerc,\n" +
                "       ob.UTGSTValue,\n" +
                "       ob.UTGSTPerc,\n" +
                "       ob.IGSTValue,\n" +
                "       ob.IGSTPerc,\n" +
                "       ob.uomid,\n" +
                "       ob.upload,\n" +
                "       ob.taxCode \n" +
                "FROM   " + TABLE_PURCHASE_ORDER_BOOKING + " ob\n" +
                "INNER JOIN " + TABLE_PRODUCT_MASTER + " p\n" +
                "ON p.cmpCode = ob.cmpCode \n" +
                "AND p.ProdCode = ob.ProdCode " +
                "WHERE  ob.cmpCode = ? \n" +
                "       AND ob.distrcode =? \n" +
                "       AND ob.confirm_status ='Y' \n" +
                "       AND ob.upload = 'N'";

        Cursor c = db.rawQuery(query, new String[]{cmpCode, distCode});
        List<OrderBookingVO> orderBookingList = new ArrayList<>();
        try {
            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {

                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    orderBookingVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    orderBookingVO.setOrderDate(c.getString(c.getColumnIndex(COLUMN_ORDER_DATE_CAPS)));
                    orderBookingVO.setOrderInvoiceNo(c.getString(c.getColumnIndex(INVOICE_NO)));
                    orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_BATCH_CODE)));
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)));
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    orderBookingVO.setQuantity(Double.valueOf(c.getString(c.getColumnIndex(COLUMN_ORDER_QTY_CAPS))));
                    orderBookingVO.setMrp(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_MRP_CAPS))));
                    orderBookingVO.setPurchasePrice(c.getDouble(c.getColumnIndex(COLUMN_PURCHASE_PRICE)));
                    orderBookingVO.setOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                    orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)));
                    orderBookingVO.setBaseUOM(c.getString(c.getColumnIndex(COLUMN_BASE_UOM)));
                    orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
//                    orderBookingVO.setTaxCode(c.getString(c.getColumnIndex(TAX_CODE)));
                    orderBookingVO.setTax(c.getDouble(c.getColumnIndex(TAX_PER_PRODUCT)));
                    orderBookingVO.setCgstperc(c.getDouble(c.getColumnIndex(CGST_PERCENT)));
                    orderBookingVO.setCgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(CGST_VALUE))));
                    orderBookingVO.setSgstPerc(c.getDouble(c.getColumnIndex(SGST_PERCENT)));
                    orderBookingVO.setSgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(SGST_VALUE))));
                    orderBookingVO.setUtgstPerc(c.getDouble(c.getColumnIndex(UTGST_PERCENT)));
                    orderBookingVO.setUtgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(UTGST_VALUE))));
                    orderBookingVO.setIgstPerc(c.getDouble(c.getColumnIndex(IGST_PERCENT)));
                    orderBookingVO.setIgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(IGST_VALUE))));

                    orderBookingList.add(orderBookingVO);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getPurchaseOrderBookingLineItem: " + e.getMessage(), e);
        } finally {
            if (c != null)
                c.close();
        }

        baseDB.closeDb();
        return orderBookingList;
    }

    public boolean isStockCaptureAvailable(BaseDB baseDB, String distrCode, String salesmanCode,
                                           String routeCode, String retlrCode) {
        db = baseDB.getDb();

        String query = "SELECT * FROM " + TABLE_STOCK_TAKE + " WHERE " + COLUMN_DISTR_CODE + " = ? AND " + COLUMN_SALESMAN_CODE + " =? AND " +
                COLUMN_ROUTE_CODE + " =? AND " + COLUMN_RETLR_CODE_CAPS + " =? ";
        try {
            Cursor c = db.rawQuery(query, new String[]{distrCode, salesmanCode, routeCode, retlrCode});
            if (c != null && c.getCount() > 0) {
                c.close();

                return true;
            }
        } finally {
            baseDB.closeDb();
        }

        return false;
    }


    public void uploadRetailerWorkStatus(BaseDB baseDB, String customerCode, String tableName,
                                         String col, String status) {

        db = baseDB.getDb();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UPLOAD, status);
        String update = col + " = ?";
        String[] updateArgs = {customerCode};
        db.update(tableName, values, update, updateArgs);
        baseDB.closeDb();
    }


    public void updateRetailerVisit(BaseDB baseDB, String col, String val,
                                    String distrCode, String salesmanCode,
                                    String routeCode, String retlrCode) {
        db = baseDB.getDb();
        long endTime = Globals.getOurInstance().getEndTime();
        if (endTime == 0) {
            endTime = new Date().getTime();
        }

        ContentValues values = new ContentValues();
        values.put(col, val);
        values.put(COLUMN_START_TIME, Globals.getOurInstance().getStartTime());
        values.put(COLUMN_END_TIME, String.valueOf(endTime));
        values.put(COLUMN_LATITUDE, Globals.getOurInstance().getStrLatitude());
        values.put(COLUMN_LONGITUDE, Globals.getOurInstance().getStrLongitude());
        values.put(COLUMN_RETLR_LATITUDE, String.valueOf(Globals.getOurInstance().getRetrlatitude()));
        values.put(COLUMN_RETLR_LONGITUDE, String.valueOf(Globals.getOurInstance().getRettlongitude()));
        values.put(COLUMN_OUT_DISTANCE, String.valueOf(Globals.getOurInstance().getStrRetrDistance()));
        values.put(COLUMN_COVERAGE_DATE, DateUtil.getCurrentYearMonthDate());
        String appendAnd = " = ? and ";
        String update = COLUMN_DISTR_CODE + appendAnd + COLUMN_SALESMAN_CODE + appendAnd + COLUMN_ROUTE_CODE + appendAnd + COLUMN_CUSTOMER_CODE + " = ?";
        String[] updateArgs = {distrCode, salesmanCode, routeCode, retlrCode};
        db.update(TABLE_RETAILER_VISIT, values, update, updateArgs);
        baseDB.closeDb();
    }

    public void updateSyncProgress(BaseDB baseDB, String col, String val, String distrCode) {
        db = baseDB.getDb();
        String dayStartDate = null;
        String query = "SELECT date  FROM " + TABLE_SYNC_PROGRESS + "";
        Cursor c = db.rawQuery(query, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            dayStartDate = c.getString(c.getColumnIndex("date"));
            c.close();
        }
        ContentValues values = new ContentValues();
        values.put(col, val);
        String appendAnd = " = ? and ";
        String update = COLUMN_LOGIN_CODE + appendAnd + COLUMN_DATE + " = ?";
        String[] updateArgs = {distrCode, dayStartDate};
        db.update(TABLE_SYNC_PROGRESS, values, update, updateArgs);
        baseDB.closeDb();
    }


    public void deleteSyncTableData(SFADatabase sfaDatabase) {

        db = sfaDatabase.getDb();
        db.execSQL("DELETE FROM " + TABLE_SYNC_PROGRESS);
        sfaDatabase.closeDb();

    }

    public void insertSchemeZipList(final Scheme scheme, final BaseDB baseDB, final IDbCallback<Boolean> callback) {
        String sql = "INSERT INTO " + TABLE_ROUTE_SCHEME_ZIP_LIST + " VALUES (?,?,?,?,?);";
        db = baseDB.getDb();
        SQLiteStatement statement = db.compileStatement(sql);
        db.beginTransaction();
        List<SchemeCustomerZipModel> schemeZipModels = scheme.getSchemeCustomerMappingZip();
        int listSize = schemeZipModels.size();
        for (int i = 0; i < listSize; i++) {

            try {
                statement.clearBindings();
                statement.bindString(1, SFASharedPref.getOurInstance().readString(PREF_CMP_CODE));
                statement.bindString(2, SFASharedPref.getOurInstance().readString(PREF_DISTRCODE));
                statement.bindString(3, SFASharedPref.getOurInstance().readString(PREF_SALESMANCODE));
                statement.bindString(4, schemeZipModels.get(i).getKey());
                statement.bindString(5, schemeZipModels.get(i).getValue());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
        db.setTransactionSuccessful();
        db.endTransaction();

        baseDB.closeDb();
        if (callback != null) {
            callback.response(true);
            callback.onSuccess();
        }
    }

    public void insertRetailerSchemeProductList(final Sync sync, final BaseDB baseDB, String routeCode) {
        String sql = "INSERT INTO " + TABLE_RETAILER_SCHEME_PRODUCTS_LIST + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        db = baseDB.getDb();
        SQLiteStatement statement = db.compileStatement(sql);
        db.beginTransaction();
        List<SchemeModel> schemeProductDetail = sync.getSchemeDetailProductList();
        int listSize = schemeProductDetail.size();
        for (int i = 0; i < listSize; i++) {

            try {
                statement.clearBindings();
                statement.bindString(1, schemeProductDetail.get(i).getCmpCode());
                statement.bindString(2, schemeProductDetail.get(i).getDistrCode());
                statement.bindString(3, SFASharedPref.getOurInstance().readString(PREF_SALESMANCODE));
                statement.bindString(4, schemeProductDetail.get(i).getCustomerCode());
                statement.bindString(5, routeCode);
                statement.bindString(6, schemeProductDetail.get(i).getProductCode());
                statement.bindString(7, schemeProductDetail.get(i).getProdName());
                statement.bindString(8, schemeProductDetail.get(i).getSchemeCode());
                statement.bindString(9, schemeProductDetail.get(i).getSchemeBase());
                statement.bindString(10, schemeProductDetail.get(i).getPayoutType());
                statement.bindString(11, schemeProductDetail.get(i).getSchemeFromDt());
                statement.bindString(12, schemeProductDetail.get(i).getSchemeToDt());
                statement.bindString(13, schemeProductDetail.get(i).getCombi());
                statement.bindString(14, schemeProductDetail.get(i).getSchemeDescription());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
        db.setTransactionSuccessful();
        db.endTransaction();

        baseDB.closeDb();
    }

    private void insertDistributorList(List<Distributor> distributorList) {
        String sql = "INSERT INTO " + TABLE_DISTRIBUTOR + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        db.beginTransaction();

        try {
            for (int i = 0; i < distributorList.size(); i++) {
                Distributor distributor = distributorList.get(i);
                statement.clearBindings();

                statement.bindString(1, Globals.checkNull(distributor.getCompanyCode()));
                statement.bindString(2, Globals.checkNull(distributor.getDistributorCode()));
                statement.bindString(3, Globals.checkNull(distributor.getDistributorName()));
                statement.bindString(4, Globals.checkNull(distributor.getDistributorAddress1()));
                statement.bindString(5, Globals.checkNull(distributor.getDistributorAddress2()));
                statement.bindString(6, Globals.checkNull(distributor.getDistributorAddress3()));
                statement.bindString(7, Globals.checkNull(distributor.getPincode()));
                statement.bindString(8, Globals.checkNull(distributor.getPhoneNumber()));
                statement.bindString(9, Globals.checkNull(distributor.getMobileNumber()));
                statement.bindString(10, Globals.checkNull(distributor.getContactPerson()));
                statement.bindString(11, Globals.checkNull(distributor.getMailId()));
                statement.bindString(12, Globals.checkNull(distributor.getBranchCode()));
                statement.bindString(13, Globals.checkNull(distributor.getBranchName()));
                statement.bindString(14, Globals.checkNull(distributor.getGstStateCode()));
                statement.bindString(15, Globals.checkNull(distributor.getGeoStateCode()));
                statement.bindString(16, Globals.checkNull(distributor.getGeoHierPath()));
                statement.bindString(17, Globals.checkNull(distributor.getDrugLicenceNumber1()));
                statement.bindString(18, Globals.checkNull(distributor.getDrugLicenceExpiryDate1()));
                statement.bindString(19, Globals.checkNull(String.valueOf(distributor.getDayOff())));
                statement.bindString(20, Globals.checkNull(distributor.getFssaiNumber()));
                statement.bindString(21, Globals.checkNull(distributor.getFssaiExpiryDate()));
                statement.bindString(22, Globals.checkNull(distributor.getPdaDistributor()));
                statement.bindString(23, Globals.checkNull(distributor.getDistrStatus()));
                statement.bindString(24, Globals.checkNull(distributor.getDistributorTypeName()));
                statement.bindString(25, Globals.checkNull(distributor.getGstDistributorType()));
                statement.bindString(26, Globals.checkNull(distributor.getGstinNumber()));
                statement.bindString(27, Globals.checkNull(distributor.getPanNumber()));
                statement.bindString(28, Globals.checkNull(distributor.getAadharNumber()));
                statement.bindString(29, Globals.checkNull(distributor.getLoadStockProd()));
                statement.bindString(30, "Y");
                statement.execute();
            }
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w(DUPLICATE_TAG, e);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private void insertDistributorStockList(List<Distributor> distributorList) {
        String sql = "INSERT INTO " + TABLE_DISTRIBUTOR_STOCK + " VALUES (?,?,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        db.beginTransaction();

        try {
            for (int i = 0; i < distributorList.size(); i++) {
                Distributor distributor = distributorList.get(i);
                statement.clearBindings();

                statement.bindString(1, Globals.checkNull(distributor.getCompanyCode()));
                statement.bindString(2, Globals.checkNull(distributor.getDistributorCode()));
                statement.bindString(3, Globals.checkNull(distributor.getProdCode()));
                statement.bindString(4, Globals.checkNull(String.valueOf(distributor.getSaleableQty())));
                statement.bindString(5, Globals.checkNull(distributor.getSaleableStock()));

                statement.execute();
            }
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w(DUPLICATE_TAG, e);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private void insertCustomerStockList(List<CustomerStockModel> customerStockModels) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER_STOCK + " VALUES (?,?,?,?,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        db.beginTransaction();

        try {
            for (int i = 0; i < customerStockModels.size(); i++) {
                CustomerStockModel customerStockModel = customerStockModels.get(i);
                statement.clearBindings();

                statement.bindString(1, Globals.checkNull(customerStockModel.getCmpCode()));
                statement.bindString(2, Globals.checkNull(customerStockModel.getDistrCode()));
                statement.bindString(3, Globals.checkNull(customerStockModel.getCustomerCode()));
                statement.bindString(4, Globals.checkNull(customerStockModel.getStock()));
                statement.bindString(5, Globals.checkNull(customerStockModel.getProdCode()));
                statement.bindString(6, Globals.checkNull(String.valueOf(customerStockModel.getSoq())));
                statement.bindString(7, Globals.checkNull(String.valueOf(customerStockModel.getPpq())));

                statement.execute();
            }
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w(DUPLICATE_TAG, e);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private void insertLobMasterList(List<LobModel> lobModelList) {
        Log.d(TAG, "insertLobMasterList");
        String sql = "INSERT INTO " + TABLE_LOB + " VALUES (?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < lobModelList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(lobModelList.get(i).getCompanyCode()));
                statement.bindString(2, lobModelList.get(i).getLobCode());
                statement.bindString(3, lobModelList.get(i).getLobName());
                statement.bindString(4, "Y");
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    void insertDistributorLobList(List<LobModel> lobModelList) {
        Log.d(TAG, "insertDistributorLobList");

        String sql = "INSERT INTO " + TABLE_DISTR_LOB + " VALUES (?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < lobModelList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(lobModelList.get(i).getCompanyCode()));
                statement.bindString(2, lobModelList.get(i).getDistributorCode());
                statement.bindString(3, lobModelList.get(i).getLobCode());
                statement.bindString(4, "Y");
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    public List<RetailerVO> getRetailerList(BaseDB baseDB, String cmpCode, String distCode) {
        db = baseDB.getDb();
        String query;

        query = "SELECT *\n" +
                "FROM t_customer\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'\n" +
                "  AND upload='N'\n" +
                "ORDER BY customerCode ASC";

        if (BuildConfig.DEBUG)
            Log.i("getRetailerList()", query);

        List<RetailerVO> retailerList = new ArrayList<>();
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    RetailerVO retailerVO = new RetailerVO();
                    retailerVO.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                    retailerVO.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                    retailerVO.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerVO.setCmpCustomerCode(checkString(c, COLUMN_COMPANY_CUSTOMER_CODE));
                    retailerVO.setCustomerName(checkString(c, COLUMN_CUSTOMER_NAME));
                    retailerVO.setPostalCode(checkString(c, COLUMN_PIN_CODE));
                    retailerVO.setPhoneNo(checkString(c, COLUMN_CUSTOMER_SHIP_PHONENO));
                    retailerVO.setMobile(checkString(c, COLUMN_MOBILE_NO));
                    retailerVO.setContactPerson(checkString(c, COLUMN_CONTACT_PERSON));
                    retailerVO.setEmail(checkString(c, COLUMN_EMAIL_ID));
                    retailerVO.setSpinDayOffCount(Integer.parseInt(checkString(c, COLUMN_DAY_OFF)));
                    retailerVO.setRetailerStatus(checkString(c, COLUMN_RETAILER_STATUS));
                    retailerVO.setFfsaLcNo(checkString(c, COLUMN_FFSAI_NO));
                    retailerVO.setDrugLcNo(checkString(c, COLUMN_DRUG_LIC_NO));
                    retailerVO.setExpiryDateTimeStamp(checkString(c, COLUMN_DRUG_LIC_EXPIRY_DATE));
                    retailerVO.setCrBills(checkString(c, COLUMN_CREDIT_BILLS));
                    retailerVO.setCrDays(checkString(c, COLUMN_CREDIT_DAYS));
                    retailerVO.setCrLimit(checkString(c, COLUMN_CREDIT_LIMIT));
                    retailerVO.setCashDisc(checkString(c, COLUMN_CASH_DISC_PERC));
                    retailerVO.setChannelCode(checkString(c, COLUMN_CHANNEL_CODE));
                    retailerVO.setSubChannelCode(checkString(c, COLUMN_SUB_CHANNEL_CODE));
                    retailerVO.setGroupCode(checkString(c, COLUMN_GROUP_CODE));
                    retailerVO.setClassCode(checkString(c, COLUMN_CLASS_CODE));
                    retailerVO.setRelationStatus(checkString(c, COLUMN_STORE_TYPE));
                    retailerVO.setParentMap(checkString(c, COLUMN_PARENT_CUSTOMER_CODE));
                    retailerVO.setLatitude(checkString(c, COLUMN_LATITUDE));
                    retailerVO.setLongitude(checkString(c, COLUMN_LONGITUDE));
                    retailerVO.setGstType(checkString(c, COLUMN_CUSTOMER_TYPE));
                    retailerVO.setGstNo(checkString(c, GSTNO));
                    retailerVO.setPanNo(checkString(c, COLUMN_PAN_NO));
                    retailerVO.setApprovalStatus(checkString(c, COLUMN_APPROVAL_STATUS));
                    retailerList.add(retailerVO);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getRetailerList: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public List<RetailerVO> getRetailerBankList(BaseDB baseDB, String cmpCode, String distCode) {
        db = baseDB.getDb();
        String query;

        query = "SELECT *\n" +
                "FROM t_customerbank\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'\n" +
                "  AND upload='N'";


        if (BuildConfig.DEBUG)
            Log.i("getRetailerBankList()", query);

        List<RetailerVO> retailerList = new ArrayList<>();
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    RetailerVO retailerVO = new RetailerVO();
                    retailerVO.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                    retailerVO.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                    retailerVO.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerVO.setBankID(checkString(c, COLUMN_BANK_Code));
                    retailerVO.setBankName(checkString(c, COLUMN_BANK_NAME));
                    retailerVO.setBranchName(checkString(c, COLUMN_BANK_BRANCH_NAME));
                    retailerVO.setIfscCode(checkString(c, COLUMN_IFSC_CODE));
                    retailerVO.setAccountType(checkString(c, COLUMN_ACC_TYPE));
                    retailerVO.setAccountNo(checkString(c, COLUMN_ACC_NUMBER));

                    retailerList.add(retailerVO);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getRetailerBankList: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public List<RouteModel> getRetailerRouteList(BaseDB baseDB, String cmpCode, String distCode) {
        db = baseDB.getDb();
        String query;

//        query = "SELECT *\n" +
//                "FROM t_customerroute\n" +
//                "WHERE cmpCode= '" + cmpCode + "'\n" +
//                "  AND distrCode='" + distCode + "'\n" +
//                "  AND upload='N'";

        query = "SELECT route.*\n" +
                "FROM t_customer cus inner join t_customerroute route on\n" +
                "cus.customerCode = route.customerCode\n" +
                "WHERE route.cmpCode= '" + cmpCode + "'\n" +
                "  AND route.distrCode='" + distCode + "'\n" +
                "  AND cus.upload='N'";


        if (BuildConfig.DEBUG)
            Log.i("getRetailerRouteList()", query);

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
                    retailerList.add(retailerVO);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getRetailerRouteList: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public List<AddressModel> getRetailerShipAddrList(BaseDB baseDB, String cmpCode, String distCode) {
        db = baseDB.getDb();
        String query;

        query = "SELECT *\n" +
                "FROM t_customershipaddress\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'\n" +
                "  AND upload='N'";

        if (BuildConfig.DEBUG)
            Log.i("getRetailerShip()", query);

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
            Log.e(TAG, "getRetailerShipAddrList: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public void uploadCustomerStatus(BaseDB baseDB, String customerCode, String tableName, String columnUpload, String status) {
        db = baseDB.getDb();
        ContentValues values = new ContentValues();
        values.put(columnUpload, status);
        String update = COLUMN_CUSTOMER_CODE + " = ?";
        String[] updateArgs = {customerCode};
        db.update(tableName, values, update, updateArgs);
        baseDB.closeDb();
    }

    public void uploadCustomerBankStatus(BaseDB baseDB, String customerCode, String bankId, String tableName, String columnUpload, String status) {
        db = baseDB.getDb();
        ContentValues values = new ContentValues();
        values.put(columnUpload, status);

        String appendAnd = " = ? and ";
        String update = COLUMN_CUSTOMER_CODE + appendAnd + COLUMN_BANK_Code + " = ?";
        String[] updateArgs = {customerCode, bankId};
        db.update(tableName, values, update, updateArgs);
        baseDB.closeDb();
    }

    public void uploadCustomerRouteStatus(BaseDB baseDB, String customerCode, String routeCode, String tableName, String columnUpload, String status) {
        db = baseDB.getDb();
        ContentValues values = new ContentValues();
        values.put(columnUpload, status);
        String appendAnd = " = ? and ";
        String update = COLUMN_CUSTOMER_CODE + appendAnd + COLUMN_ROUTE_CODE + " = ?";
        String[] updateArgs = {customerCode, routeCode};
        db.update(tableName, values, update, updateArgs);
        baseDB.closeDb();
    }

    public void uploadCustomerShipAddrStatus(BaseDB baseDB, String customerCode, String customerShipCode, String tableName, String columnUpload, String status) {
        db = baseDB.getDb();
        ContentValues values = new ContentValues();
        values.put(columnUpload, status);
        String appendAnd = " = ? and ";
        String update = COLUMN_CUSTOMER_CODE + appendAnd + COLUMN_CUSTOMER_SHIP_CODE + " = ?";
        String[] updateArgs = {customerCode, customerShipCode};
        db.update(tableName, values, update, updateArgs);
        baseDB.closeDb();
    }

    public void uploadWDFreezerStatus(BaseDB baseDB, String distCode, String freezerCapacity, String tableName, String columnUpload, String status) {
        db = baseDB.getDb();
        ContentValues values = new ContentValues();
        values.put(columnUpload, status);

        String appendAnd = " = ? and ";
        String update = COLUMN_DISTR_CODE + appendAnd + COLUMN_FREEZER_CAPACITY + " = ?";

        String[] updateArgs = {distCode, freezerCapacity};

        db.update(tableName, values, update, updateArgs);
        baseDB.closeDb();

    }

    public void uploadWDStorageStatus(BaseDB baseDB, String distCode, String tableName, String columnUpload, String status) {
        db = baseDB.getDb();
        ContentValues values = new ContentValues();
        values.put(columnUpload, status);
        String update = COLUMN_DISTR_CODE + " = ?";
        String[] updateArgs = {distCode};
        db.update(tableName, values, update, updateArgs);
        baseDB.closeDb();
    }

    public void updatePurchaseOrderStatus(BaseDB baseDB, String cmpCode, String distCode, String invoiceNo, String prodCode,
                                          String batchCode, String tableName, String columnUpload, String status) {
        db = baseDB.getDb();
        ContentValues values = new ContentValues();
        values.put(columnUpload, status);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_INVOICE_NO + appendAnd + COLUMN_PROD_CODE_CAPS + appendAnd + COLUMN_BATCH_CODE + " = ?";
        String[] updateArgs = {cmpCode, distCode, invoiceNo, prodCode, batchCode};
        db.update(tableName, values, update, updateArgs);
        baseDB.closeDb();
    }

    private void insertOrderBookingHeader(List<OrderBookingHeaderModel> models) {
        String sql = "INSERT INTO " + TABLE_PREVIOUS_ORDER_BOOKING_REPORT_HEADER + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < models.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(models.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(models.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(models.get(i).getOrderNo()));
                statement.bindString(4, Globals.checkNull(models.get(i).getCustomerRefNo()));
                statement.bindString(5, Globals.checkNull(models.get(i).getCustomerShipCode()));
                statement.bindString(6, Globals.checkNull(models.get(i).getCustomerCode()));
                statement.bindString(7, Globals.checkNull(models.get(i).getOrderDt()));
                statement.bindString(8, Globals.checkNull(String.valueOf(models.get(i).getTotalOrderValue())));
                statement.bindString(9, Globals.checkNull(String.valueOf(models.get(i).getTotalGrossValue())));
                statement.bindString(10, Globals.checkNull(models.get(i).getRemarks()));
                statement.bindString(11, Globals.checkNull(models.get(i).getLatitude()));
                statement.bindString(12, Globals.checkNull(models.get(i).getLongitude()));
                statement.bindString(13, Globals.checkNull(models.get(i).getStartTime()));
                statement.bindString(14, Globals.checkNull(models.get(i).getEndTime()));
                statement.bindString(15, Globals.checkNull(models.get(i).getOrderStatus()));
                statement.bindString(16, Globals.checkNull(String.valueOf(models.get(i).getTotalDiscount())));
                statement.bindString(17, Globals.checkNull(String.valueOf(models.get(i).getTotalTax())));
                statement.bindString(18, Globals.checkNull(models.get(i).getUploadFlag()));

                statement.execute();

                insertOrderBookingDetails(models.get(i).getOrderBookingDetailsList());
                insertOrderStatusDetails(models.get(i).getActionOrderStatusList());
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }
    private void insertOrderStatusDetails(List<OrderStatusModel> models) {

        String sql = "INSERT INTO " + TABLE_PREVIOUS_ORDER_BOOKING_STATUS+ " VALUES (?,?,?,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);

        for (int i = 0; i < models.size(); i++) {

            try {

                statement.clearBindings();

                statement.bindString(1, Globals.checkNull(models.get(i).getCmpCode()));

                statement.bindString(2, Globals.checkNull(models.get(i).getDistrCode()));

                statement.bindString(3, Globals.checkNull(models.get(i).getOrderNo()));

                statement.bindString(4, Globals.checkNull(models.get(i).getOrderStatus()));

                statement.bindString(5, Globals.checkNull(models.get(i).getActionTime()));

                statement.bindString(6, Globals.checkNull(models.get(i).getFreeText()));

                statement.execute();

            } catch (SQLiteException e) {

                if (BuildConfig.DEBUG)

                    Log.w(DUPLICATE_TAG, e);

            }



        }

    }

    private void insertOrderBookingDetails(List<OrderBookingVO> models) {
        String sql = "INSERT INTO " + TABLE_PREVIOUS_ORDER_BOOKING_REPORT_DETAILS + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < models.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(models.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(models.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(models.get(i).getOrderNo()));
                statement.bindString(4, Globals.checkNull(models.get(i).getProdCode()));
                statement.bindString(5, Globals.checkNull(models.get(i).getProdName()));
                statement.bindString(6, Globals.checkNull(models.get(i).getProdBatchCode()));
                statement.bindString(7, Globals.checkNull(String.valueOf(models.get(i).getQuantity())));
                statement.bindString(8, Globals.checkNull(String.valueOf(models.get(i).getQuantity())));
                statement.bindString(9, Globals.checkNull(models.get(i).getUomId()));
                statement.bindString(10, Globals.checkNull(models.get(i).getInputStr()));
                statement.bindString(11, Globals.checkNull(String.valueOf(models.get(i).getSellPrice())));
                statement.bindString(12, Globals.checkNull(String.valueOf(models.get(i).getActualSellRate())));
                statement.bindString(13, Globals.checkNull(String.valueOf(models.get(i).getSchemeAmount())));
                statement.bindString(14, Globals.checkNull(String.valueOf(models.get(i).getTax())));
                statement.bindString(15, Globals.checkNull(models.get(i).getTaxCode()));
                statement.bindString(16, Globals.checkNull(String.valueOf(models.get(i).getCgstperc())));
                statement.bindString(17, Globals.checkNull(String.valueOf(models.get(i).getCgstvalue())));
                statement.bindString(18, Globals.checkNull(String.valueOf(models.get(i).getSgstPerc())));
                statement.bindString(19, Globals.checkNull(String.valueOf(models.get(i).getSgstValue())));
                statement.bindString(20, Globals.checkNull(String.valueOf(models.get(i).getUtgstPerc())));
                statement.bindString(21, Globals.checkNull(String.valueOf(models.get(i).getUtgstValue())));
                statement.bindString(22, Globals.checkNull(String.valueOf(models.get(i).getIgstPerc())));
                statement.bindString(23, Globals.checkNull(String.valueOf(models.get(i).getIgstvalue())));
                statement.bindString(24, Globals.checkNull(String.valueOf(models.get(i).getlGrossAmt())));
                statement.bindString(25, Globals.checkNull(String.valueOf(models.get(i).getOrderValue())));
                statement.bindString(26, Globals.checkNull(models.get(i).getProdType()));
                statement.bindString(27,"N");
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    public void updateStockOnHandUploadStatus(SFADatabase sfaDatabase, String cmpCode, String distCode,
                                              String prodCode, String batchCode, String tableStockOnHand,
                                              String columnUpload, String status) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(columnUpload, status);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_PROD_CODE + appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";
        String[] updateArgs = {cmpCode, distCode, prodCode, batchCode};
        db.update(tableStockOnHand, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void uploadCollection(SFADatabase sfaDatabase, String cmpCode, String distCode, String collectionNo, String tableName, String columnUpload, String status) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(columnUpload, status);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_COLLECTION_NO + " = ?";
        String[] updateArgs = {cmpCode, distCode, collectionNo};
        db.update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void uploadBillAdjustment(SFADatabase sfaDatabase, String cmpCode, String distCode, String collectionNo, String tableName, String uploadFlag, String uploaded) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(uploadFlag, uploaded);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_COLLECTION_NO + " = ?";
        String[] updateArgs = {cmpCode, distCode, collectionNo};
        db.update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void uploadPendingBills(SFADatabase sfaDatabase, String cmpCode, String distCode, String invoiceNo, String tableName, String uploadFlag, String uploaded) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(uploadFlag, uploaded);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_INVOICE_NO + " = ?";
        String[] updateArgs = {cmpCode, distCode, invoiceNo};
        db.update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void uploadStockAdjustmentHeader(SFADatabase sfaDatabase, String cmpCode, String distrCode, String godownCode, String stockAdjustNo, String tableName, String uploadFlag, String uploaded) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(uploadFlag, uploaded);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_GODOWN_CODE + appendAnd + COLUMN_STOCK_ADJUSTNO + " = ?";
        String[] updateArgs = {cmpCode, distrCode, godownCode, stockAdjustNo};
        db.update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void uploadStockAdjustmentDetail(SFADatabase sfaDatabase, String cmpCode, String distrCode, String godownCode, String stockAdjustNo, String tableName, String uploadFlag, String uploaded) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(uploadFlag, uploaded);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_GODOWN_CODE + appendAnd + COLUMN_STOCK_ADJUSTNO + " = ?";
        String[] updateArgs = {cmpCode, distrCode, godownCode, stockAdjustNo};
        db.update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void uploadSalesReturnHeader(SFADatabase sfaDatabase, String cmpCode, String distrCode, String salesReturnNo, String tableName, String uploadFlag, String uploaded) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(uploadFlag, uploaded);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_SALES_RETURNNO + " = ?";
        String[] updateArgs = {cmpCode, distrCode, salesReturnNo};
        db.update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void updateSchemeDistrBudget(SFADatabase sfaDatabase, String cmpCode, String distrCode, String schemeCode, String tableName, String uploadFlag, String uploaded) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(uploadFlag, uploaded);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + SCHEME_CODE + " = ?";
        String[] updateArgs = {cmpCode, distrCode, schemeCode};
        db.update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void uploadSalesReturnLineDetail(SFADatabase sfaDatabase, String cmpCode, String distrCode, String salesReturnNo, String tableName, String uploadFlag, String uploaded) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(uploadFlag, uploaded);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_SALES_RETURNNO + " = ?";
        String[] updateArgs = {cmpCode, distrCode, salesReturnNo};
        db.update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void insertCustomerSchemeProductDetails(final BaseDB baseDB, List<SchemeModel> schemeProductDetail) {
        String sql = "INSERT INTO " + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST + " VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        db = baseDB.getDb();
        SQLiteStatement statement = db.compileStatement(sql);
        db.beginTransaction();

        int listSize = schemeProductDetail.size();
        for (int i = 0; i < listSize; i++) {

            try {
                statement.clearBindings();
                statement.bindString(1, schemeProductDetail.get(i).getCmpCode());
                statement.bindString(2, schemeProductDetail.get(i).getCustomerCode());
                statement.bindString(3, schemeProductDetail.get(i).getProductCode());
                statement.bindString(4, schemeProductDetail.get(i).getProdName());
                statement.bindString(5, schemeProductDetail.get(i).getSchemeCode());
                statement.bindString(6, schemeProductDetail.get(i).getSchemeBase());
                statement.bindString(7, schemeProductDetail.get(i).getPayoutType());
                statement.bindString(8, schemeProductDetail.get(i).getSchemeFromDt());
                statement.bindString(9, schemeProductDetail.get(i).getSchemeToDt());
                statement.bindString(10, schemeProductDetail.get(i).getCombi());
                statement.bindString(11, schemeProductDetail.get(i).getSchemeDescription());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
        db.setTransactionSuccessful();
        db.endTransaction();

        baseDB.closeDb();
    }

    public void uploadInvoiceCRDBAdjustment(SFADatabase sfaDatabase, String cmpCode, String distrCode, String invoiceNo, String salesDBCRRefNo, String tableName, String uploadFlag, String uploaded) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(uploadFlag, uploaded);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_INVOICE_NO + appendAnd + COLUMN_SALES_DB_CRREFNO + " = ?";
        String[] updateArgs = {cmpCode, distrCode, invoiceNo, salesDBCRRefNo};
        db.update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void uploadSalesDBCRNoteDetails(SFADatabase sfaDatabase, String cmpCode, String customerCode, String salesDBCRRefNo, String tableName, String uploadFlag, String uploaded) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(uploadFlag, uploaded);
        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_CUSTOMER_CODE + appendAnd + COLUMN_SALES_DB_CRREFNO + " = ?";
        String[] updateArgs = {cmpCode, customerCode, salesDBCRRefNo};
        db.update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void updateSyncLogData(TimeCaptureModel sync, SFADatabase sfaDatabase) {
        db = sfaDatabase.getDb();
        ContentValues values = new ContentValues();
        values.put(COLUMN_UPLOAD, "Y");
        String appendAnd = " = ? and ";
        String update = USER_CODE + appendAnd + COLUMN_MODE + appendAnd +
                COLUMN_PROCESS + appendAnd + COLUMN_STATUS + appendAnd + COLUMN_SYNC_DATE + appendAnd + COLUMN_SYNC_TIME + appendAnd + COLUMN_SYNC_END_TIME + " = ?";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(Long.parseLong(sync.getSyncDate()));
        String[] updateArgs = {sync.getUserCode(), sync.getMode(),
                sync.getProcessName(), sync.getStatus(), strDate,
                sync.getSyncTime(), sync.getSyncEndTime()};
        db.update(IDBColumns.TABLE_LOGIN_TIME_CAPTURE, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void removeExistingDistrReportTableInfo(BaseDB baseDB) {
        db = baseDB.getDb();
        db.execSQL("DELETE FROM " + TABLE_DISTR_REPORT);
        baseDB.closeDb();
    }

    public void insertSyncDistrReportData(Sync sync, BaseDB baseDB, IDbCallback<Boolean> callback) {
        db = baseDB.getDb();
        try {
            db.beginTransaction();
            bulkDistrReportInsert(sync);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "insertSyncDistrReportData: " + e.getMessage(), e);
        } finally {
            db.endTransaction();
        }


        baseDB.closeDb();
        if (callback != null) {
            callback.response(true);
            callback.onSuccess();
        }
    }

    private void bulkDistrReportInsert(Sync sync) {

        if (!sync.getDistrReportModelList().isEmpty()) {
            insertDistrReport(sync.getDistrReportModelList());
        }
    }

    private void insertDistrReport(List<DistrReportModel> list) {
        String sql = "INSERT INTO " + TABLE_DISTR_REPORT + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, list.get(i).getCmpCode());
                statement.bindString(2, list.get(i).getUserCode());
                statement.bindString(3, list.get(i).getProcessName());
                statement.bindString(4, list.get(i).getProcessData());
                statement.bindString(5, list.get(i).getProcessType());
                statement.bindString(6, list.get(i).getProcessEnable());
                if (list.get(i).getChangeNo() != null) {
                    statement.bindString(7, list.get(i).getChangeNo());
                } else {
                    statement.bindString(7, "");
                }
                if (list.get(i).getConsoleFlag() != null) {
                    statement.bindString(8, list.get(i).getConsoleFlag());
                } else {
                    statement.bindString(8, "");
                }
                statement.bindString(9, list.get(i).getModDt());
                statement.bindString(10, list.get(i).getUploadFlag());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertBulletinBoard(List<BulletinBoardModel> bulletinBoardModels) {
        String sql = "INSERT INTO " + TABLE_BULLETIN_BOARD + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < bulletinBoardModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(bulletinBoardModels.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(bulletinBoardModels.get(i).getMessageCode()));
                statement.bindString(3, Globals.checkNull(bulletinBoardModels.get(i).getChannelCode()));
                statement.bindString(4, Globals.checkNull(bulletinBoardModels.get(i).getSubChannelCode()));
                statement.bindString(5, Globals.checkNull(bulletinBoardModels.get(i).getGroupCode()));
                statement.bindString(6, Globals.checkNull(bulletinBoardModels.get(i).getClassCode()));
                statement.bindString(7, Globals.checkNull(bulletinBoardModels.get(i).getDistrCode()));
                statement.bindString(8, bulletinBoardModels.get(i).getExpiryDate());
                statement.bindString(9, bulletinBoardModels.get(i).getSubject());
                statement.bindString(10, bulletinBoardModels.get(i).getMessageDesc());
                statement.bindString(11, bulletinBoardModels.get(i).getAttachment());
                statement.bindString(12, Globals.checkNull(bulletinBoardModels.get(i).getNotificationType()));
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    public void removeExistingOnlineReportTableInfo(BaseDB baseDB) {
        db = baseDB.getDb();
        db.execSQL("DELETE FROM " + TABLE_REPORT_PROCESS_MAPPING);
        db.execSQL("DELETE FROM " + TABLE_REPORT_FIELD_MAPPING);
        baseDB.closeDb();
    }

    public List<RetailerVO> getRetailerApprovalStatusList(BaseDB baseDB, String cmpCode, String distCode) {
        db = baseDB.getDb();
        String query;

        query = "SELECT *\n" +
                "FROM t_customerapprovaldata\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'\n" +
                "  AND uploadFlag='N'";

        List<RetailerVO> retailerList = new ArrayList<>();
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    RetailerVO retailerVO = new RetailerVO();
                    retailerVO.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                    retailerVO.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                    retailerVO.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerVO.setApprovalStatus(checkString(c, COLUMN_APPROVAL_STATUS));
                    retailerVO.setCustomerData(checkString(c, COLUMN_CUSTOMER_DATA));

                    retailerList.add(retailerVO);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getRetailerApprovalList: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    private void insertTempRoute(List<RouteModel> list) {
        String sql = "INSERT INTO " + TABLE_TEMP_ROUTE + " VALUES (?,?,?,?,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < list.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, list.get(i).getCompanyCode());
                statement.bindString(2, list.get(i).getDistributorCode());
                statement.bindString(3, list.get(i).getTempRouteCode());
                statement.bindString(4, list.get(i).getRouteName());
                statement.bindString(5, list.get(i).getRouteType());
                statement.bindString(6, list.get(i).getRouteCode());
                statement.bindString(7, "N");

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }

        }
    }

    private void insertTempCustomerRoute(List<RouteModel> routeList) {
        String sql = "INSERT INTO " + TABLE_TEMP_CUSTOMER_ROUTE + " VALUES (?,?,?,?,?,?,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < routeList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(routeList.get(i).getCompanyCode()));
                statement.bindString(2, Globals.checkNull(routeList.get(i).getDistributorCode()));
                statement.bindString(3, Globals.checkNull(routeList.get(i).getTempCustomerCode()));
                statement.bindString(4, Globals.checkNull(routeList.get(i).getTempRouteCode()));
                statement.bindString(5, Globals.checkNull(routeList.get(i).getCoverageSeq()));
                statement.bindString(6, Globals.checkNull(routeList.get(i).getCustomerCode()));
                statement.bindString(7, Globals.checkNull(routeList.get(i).getRouteCode()));
                statement.bindString(8, Globals.checkNull(routeList.get(i).getModDt()));
                statement.bindString(9, routeList.get(i).getUploadFlag());
                statement.execute();
            } catch (Exception e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    private void insertTempCustomerShipAddress(List<AddressModel> customerShipAddress) {
        String sql = "INSERT INTO " + TABLE_TEMP_CUSTOMER_SHIP_ADDRESS + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < customerShipAddress.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(customerShipAddress.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(customerShipAddress.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(customerShipAddress.get(i).getTempCustomerCode()));
                statement.bindString(4, Globals.checkNull(customerShipAddress.get(i).getTempShipCode()));
                statement.bindString(5, Globals.checkNull(customerShipAddress.get(i).getShippingAddress1()));
                statement.bindString(6, Globals.checkNull(customerShipAddress.get(i).getShippingAddress2()));
                statement.bindString(7, Globals.checkNull(customerShipAddress.get(i).getShippingAddress3()));
                statement.bindString(8, Globals.checkNull(customerShipAddress.get(i).getShipCityTown()));
                statement.bindString(9, Globals.checkNull(customerShipAddress.get(i).getShippingGeoHierPath()));
                statement.bindString(10, Globals.checkNull(customerShipAddress.get(i).getShippingState()));
                statement.bindString(11, Globals.checkNull(customerShipAddress.get(i).getShippingPhoneNumber()));
                statement.bindString(12, Globals.checkNull(customerShipAddress.get(i).getIsDefault()));
                statement.bindString(13, Globals.checkNull(customerShipAddress.get(i).getCustomerCode()));
                statement.bindString(14, Globals.checkNull(customerShipAddress.get(i).getShipCode()));
                statement.bindString(15, Globals.checkNull(customerShipAddress.get(i).getModDt()));
                statement.bindString(16, customerShipAddress.get(i).getUploadFlag());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }

    }

    private void insertTempCustomer(List<CustomerModel> customer) {
        String sql = "INSERT INTO " + TABLE_TEMP_CUSTOMER + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < customer.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(customer.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(customer.get(i).getDistrCode()));
                statement.bindString(3, Globals.checkNull(customer.get(i).getTempCustomerCode()));
                statement.bindString(4, Globals.checkNull(customer.get(i).getCustomerName()));
                statement.bindString(5, Globals.checkNull(customer.get(i).getPinCode()));
                statement.bindString(6, Globals.checkNull(customer.get(i).getPhoneNo()));
                statement.bindString(7, Globals.checkNull(customer.get(i).getMobileNo()));
                statement.bindString(8, Globals.checkNull(customer.get(i).getContactPerson()));
                statement.bindString(9, Globals.checkNull(customer.get(i).getEmailID()));
                statement.bindString(10, Globals.checkNull(String.valueOf(customer.get(i).getDayOff())));
                statement.bindString(11, Globals.checkNull(customer.get(i).getRetailerStatus()));
                statement.bindString(12, Globals.checkNull(customer.get(i).getFssaiNo()));
                statement.bindString(13, Globals.checkNull(customer.get(i).getDrugLicNo()));
                statement.bindString(14, Globals.checkNull(customer.get(i).getDrugLicExpiryDate()));
                statement.bindString(15, String.valueOf(customer.get(i).getCreditBills()));
                statement.bindString(16, String.valueOf(customer.get(i).getCreditDays()));
                statement.bindDouble(17, customer.get(i).getCreditLimit());
                statement.bindDouble(18, customer.get(i).getCashDiscPerc());
                statement.bindString(19, Globals.checkNull(customer.get(i).getChannelCode()));
                statement.bindString(20, Globals.checkNull(customer.get(i).getSubChannelCode()));
                statement.bindString(21, Globals.checkNull(customer.get(i).getGroupCode()));
                statement.bindString(22, Globals.checkNull(customer.get(i).getClassCode()));
                statement.bindString(23, Globals.checkNull(customer.get(i).getStoreType()));
                statement.bindString(24, Globals.checkNull(customer.get(i).getParentCustomerCode()));
                statement.bindString(25, Globals.checkNull(customer.get(i).getLatitude()));
                statement.bindString(26, Globals.checkNull(customer.get(i).getLongitude()));
                statement.bindString(27, Globals.checkNull(customer.get(i).getCustomerType()));
                statement.bindString(28, Globals.checkNull(customer.get(i).getGstTinNo()));
                statement.bindString(29, Globals.checkNull(customer.get(i).getPanNo()));
                statement.bindString(30, Globals.checkNull(customer.get(i).getApprovalStatus()));
                statement.bindString(31, Globals.checkNull(customer.get(i).getCustomerCode()));
                statement.bindLong(32, customer.get(i).getModDt());
                statement.bindString(33, customer.get(i).getUploadFlag());

                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }

    public List<RetailerVO> getTempRetailerList(BaseDB baseDB, String cmpCode, String distCode) {
        db = baseDB.getDb();
        String query;

        query = "SELECT *\n" +
                "FROM m_temp_customer\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'\n" +
                "  AND upload='Y'\n" +
                "ORDER BY customerCode ASC";

        if (BuildConfig.DEBUG)
            Log.i("getRetailerList()", query);

        List<RetailerVO> retailerList = new ArrayList<>();
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    RetailerVO retailerVO = new RetailerVO();
                    retailerVO.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                    retailerVO.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                    retailerVO.setTempCustomerCode(checkString(c, COLUMN_TEMP_CUSTOMER_CODE));
                    retailerVO.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerVO.setCustomerName(checkString(c, COLUMN_CUSTOMER_NAME));
                    retailerVO.setPostalCode(checkString(c, COLUMN_PIN_CODE));
                    retailerVO.setPhoneNo(checkString(c, COLUMN_CUSTOMER_SHIP_PHONENO));
                    retailerVO.setMobile(checkString(c, COLUMN_MOBILE_NO));
                    retailerVO.setContactPerson(checkString(c, COLUMN_CONTACT_PERSON));
                    retailerVO.setEmail(checkString(c, COLUMN_EMAIL_ID));
                    retailerVO.setSpinDayOffCount(Integer.parseInt(checkString(c, COLUMN_DAY_OFF)));
                    retailerVO.setRetailerStatus(checkString(c, COLUMN_RETAILER_STATUS));
                    retailerVO.setFfsaLcNo(checkString(c, COLUMN_FFSAI_NO));
                    retailerVO.setDrugLcNo(checkString(c, COLUMN_DRUG_LIC_NO));
                    retailerVO.setExpiryDateTimeStamp(checkString(c, COLUMN_DRUG_LIC_EXPIRY_DATE));
                    retailerVO.setCrBills(checkString(c, COLUMN_CREDIT_BILLS));
                    retailerVO.setCrDays(checkString(c, COLUMN_CREDIT_DAYS));
                    retailerVO.setCrLimit(checkString(c, COLUMN_CREDIT_LIMIT));
                    retailerVO.setCashDisc(checkString(c, COLUMN_CASH_DISC_PERC));
                    retailerVO.setChannelCode(checkString(c, COLUMN_CHANNEL_CODE));
                    retailerVO.setSubChannelCode(checkString(c, COLUMN_SUB_CHANNEL_CODE));
                    retailerVO.setGroupCode(checkString(c, COLUMN_GROUP_CODE));
                    retailerVO.setClassCode(checkString(c, COLUMN_CLASS_CODE));
                    retailerVO.setRelationStatus(checkString(c, COLUMN_STORE_TYPE));
                    retailerVO.setParentMap(checkString(c, COLUMN_PARENT_CUSTOMER_CODE));
                    retailerVO.setLatitude(checkString(c, COLUMN_LATITUDE));
                    retailerVO.setLongitude(checkString(c, COLUMN_LONGITUDE));
                    retailerVO.setGstType(checkString(c, COLUMN_CUSTOMER_TYPE));
                    retailerVO.setGstNo(checkString(c, GSTNO));
                    retailerVO.setPanNo(checkString(c, COLUMN_PAN_NO));
                    retailerVO.setApprovalStatus(checkString(c, COLUMN_APPROVAL_STATUS));
                    retailerList.add(retailerVO);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getRetailerList: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public List<RouteModel> getTempRetailerRouteList(BaseDB baseDB, String cmpCode, String distCode) {
        db = baseDB.getDb();
        String query;

        query = "SELECT *\n" +
                "FROM m_temp_customerroute\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'\n" +
                "  AND upload='Y'";


        if (BuildConfig.DEBUG)
            Log.i("getRetailerRouteList()", query);

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
                    retailerVO.setTempCustomerCode(checkString(c, COLUMN_TEMP_CUSTOMER_CODE));
                    retailerVO.setTempRouteCode(checkString(c, COLUMN_TEMP_ROUTE_CODE));
                    retailerVO.setCoverageSeq(checkString(c, COLUMN_COVERAGE_SEQUENCE));
                    retailerList.add(retailerVO);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getRetailerRouteList: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public List<AddressModel> getTempRetailerShipAddrList(BaseDB baseDB, String cmpCode, String distCode) {
        db = baseDB.getDb();
        String query;

        query = "SELECT *\n" +
                "FROM m_temp_customershipaddress\n" +
                "WHERE cmpCode= '" + cmpCode + "'\n" +
                "  AND distrCode='" + distCode + "'\n" +
                "  AND upload='Y'";

        if (BuildConfig.DEBUG)
            Log.i("getRetailerShip()", query);

        List<AddressModel> retailerList = new ArrayList<>();
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    AddressModel retailerVO = new AddressModel();
                    retailerVO.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerVO.setShipCode(checkString(c, COLUMN_CUSTOMER_SHIP_CODE));
                    retailerVO.setTempCustomerCode(checkString(c, COLUMN_TEMP_CUSTOMER_CODE));
                    retailerVO.setTempShipCode(checkString(c, COLUMN_TEMP_CUSTOMER_SHIP_CODE));
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
            Log.e(TAG, "getRetailerShipAddrList: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public void uploadBTRStatus(BaseDB baseDB, String batchTransferNo, String stockType, String prodCode, String fromBatchCode, String toBatchCode, String tableName, String columnUpload, String status) {
        db = baseDB.getDb();
        ContentValues values = new ContentValues();
        values.put(columnUpload, status);
        String appendAnd = " = ? and ";
        String update = COLUMN_BATCH_TRANSFERNO + appendAnd + COLUMN_STOCK_TYPE + appendAnd + COLUMN_PROD_CODE + appendAnd + FROM_PROD_BATCH_CODE + appendAnd + TO_PROD_BATCH_CODE + " = ?";
        String[] updateArgs = {batchTransferNo, stockType, prodCode, fromBatchCode, toBatchCode};
        db.update(tableName, values, update, updateArgs);
        baseDB.closeDb();
    }

    private void insertKeyGeneratorHistory(List<CodeGeneratorModel> codeGeneratorModels) {

        String sql = "INSERT INTO " + TABLE_CODE_GENERATOR_HISTORY + " VALUES (?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < codeGeneratorModels.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, codeGeneratorModels.get(i).getCompanyCode());
                statement.bindString(2, codeGeneratorModels.get(i).getDistributorCode());
                statement.bindString(3, codeGeneratorModels.get(i).getScreenName());
                statement.bindString(4, codeGeneratorModels.get(i).getPrefix());
                statement.bindString(5, codeGeneratorModels.get(i).getSuffixYy());
                statement.bindLong(6, codeGeneratorModels.get(i).getSuffixNn());
                statement.bindString(7, codeGeneratorModels.get(i).getUpload());
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }

    }

    public List<RetailerVO> getOrderBookingRetailerDetails(BaseDB baseDB, String distrCode, String salesmanCode,
                                                           String dayStartDate, String tableName) {

        db = baseDB.getDb();
        String query = "";
/*
        if (tableName.equalsIgnoreCase(TABLE_ORDER_BOOKING)) {

            query = "SELECT r.customercode AS customerCode,\n" +
                    "       r.cmpcode AS cmpCode,\n" +
                    "       r.distrcode AS distrCode,\n" +
                    "       r.salesmancode AS salesmanCode,\n" +
                    "       r.routecode AS routeCode,\n" +
                    "       r.menureason AS menuReason,\n" +
                    "       rv.latitude AS latitude,\n" +
                    "       rv.longitude AS longitude,\n" +
                    "       obt.starttime AS startTime,\n" +
                    "       obt.endtime AS endTime,\n" +
                    "       r.digitalsignature AS digitalSignature,\n" +
                    "       ob.invoiceno AS invoiceNo,\n" +
                    "       ob.orderdate AS OrderDate,\n" +
                    "       ob.isExistRetailer AS isExistRetailer,\n" +
                    "       sum(ob.primaryDiscOrderValue)AS primaryDiscOrderValue,\n" +
                    "       sum(ob.OrderValue)AS OrderValue,\n" +
                    "       sum(ob.schemeAmount)AS discountAmount,\n" +
                    "       sum(ob.taxPerProduct)AS taxPerProduct,\n" +
                    "       sum(ob.totalAmount) AS totalAmount,\n" +
                    "       sum(ob.netAmount) AS netAmount,\n" +
                    "       r.gstno AS gstNo,\n" +
                    "       r.panno AS panNo,\n" +
                    "       r.gsttype AS gstType,\n" +
                    "       r.customerShipCode AS customerShipCode\n" +
                    "FROM " + TABLE_RETAILER + " r\n" +
                    "INNER JOIN " + TABLE_RETAILER_VISIT + " rv ON r.distrcode = rv.distrcode\n" +
                    "AND r.salesmancode = rv.salesmancode\n" +
                    "AND r.routecode = rv.routecode\n" +
                    "AND r.customercode = rv.customercode\n" +
                    "LEFT JOIN " + TABLE_ORDER_BOOKING_TRACKER + " obt ON r.distrcode = obt.distrcode\n" +
                    "AND r.salesmancode = obt.salesmancode\n" +
                    "AND r.routecode = obt.routecode\n" +
                    "AND r.customercode = obt.customercode\n" +
                    "AND obt.upload = 'N'\n" +
                    "INNER JOIN " + TABLE_ORDER_BOOKING + " ob ON r.distrcode = ob.distrcode\n" +
                    "AND r.salesmancode = ob.salesmancode\n" +
                    "AND r.routecode = ob.routecode\n" +
                    "AND r.customercode = ob.retlrcode\n" +
                    "WHERE r.distrcode = '" + distrCode + "'\n" +
                    "  AND r.salesmancode ='" + salesmanCode + "'\n" +
                    "  AND ob.completeFlag = 'Y'\n" +
                    "  AND ob.upload = 'N'\n" +
                    "GROUP BY r.customercode\n" +
                    "HAVING SUM(ob.OrderValue) > 0\n" +
                    "UNION\n" +
                    "SELECT obt.customercode AS customerCode,\n" +
                    "       r.cmpcode AS cmpCode,\n" +
                    "       r.distrcode AS distrCode,\n" +
                    "       r.salesmancode AS salesmanCode,\n" +
                    "       obt.routecode AS routeCode,\n" +
                    "       r.menureason AS menuReason,\n" +
                    "       r.latitude AS latitude,\n" +
                    "       r.longitude AS longitude,\n" +
                    "       obt.starttime AS startTime,\n" +
                    "       obt.endtime AS endTime,\n" +
                    "       r.digitalsignature AS digitalSignature,\n" +
                    "       ob.invoiceno AS invoiceNo,\n" +
                    "       ob.orderdate AS OrderDate,\n" +
                    "       ob.isExistRetailer AS isExistRetailer,\n" +
                    "       sum(ob.primaryDiscOrderValue)AS primaryDiscOrderValue,\n" +
                    "       sum(ob.OrderValue)AS OrderValue,\n" +
                    "       sum(ob.schemeAmount)AS discountAmount,\n" +
                    "       sum(ob.taxPerProduct)AS taxPerProduct,\n" +
                    "       sum(ob.totalAmount) AS totalAmount,\n" +
                    "       sum(ob.netAmount) AS netAmount,\n" +
                    "       r.gstno AS gstNo,\n" +
                    "       r.panno AS panNo,\n" +
                    "       r.gsttype AS gstType,\n" +
                    "       r.customerShipCode AS customerShipCode\n" +
                    "FROM " + TABLE_NEW_RETAILER + " r\n" +
                    "LEFT JOIN " + TABLE_ORDER_BOOKING_TRACKER + " obt ON  ((r.customerSharedFlag != 'Z' AND r.customercode = obt.customercode) \n" +
                    " OR (r.customerSharedFlag = 'Z' AND r.cmpCustomerCode = obt.customercode))  \n" +
                    "AND obt.upload = 'N'\n" +
                    "AND r.customercode NOT NULL\n" +
                    "INNER JOIN " + TABLE_ORDER_BOOKING + " ob ON obt.distrcode = ob.distrcode\n" +
                    "AND obt.salesmancode = ob.salesmancode\n" +
                    "AND obt.routecode = ob.routecode\n" +
                    "AND ((r.customerSharedFlag != 'Z' AND r.customercode = ob.retlrcode) \n" +
                    " OR (r.customerSharedFlag = 'Z' AND r.cmpCustomerCode = ob.retlrcode)) \n" +
                    "WHERE r.distrcode = '" + distrCode + "'\n" +
                    "  AND r.salesmancode ='" + salesmanCode + "'\n" +
                    "  AND ob.completeFlag = 'Y'\n" +
                    "  AND ob.upload = 'N'\n" +
                    "GROUP BY r.customercode\n" +
                    "HAVING SUM(ob.OrderValue) > 0";


        } else if (tableName.equalsIgnoreCase(TABLE_PREVIOUS_ORDER_BOOKED_PROD_DETAILS)) {

        } else {

            query = "SELECT r.customercode AS customerCode,\n" +
                    "       r.cmpcode AS cmpCode,\n" +
                    "       r.distrcode AS distrCode,\n" +
                    "       r.salesmancode AS salesmanCode ,\n" +
                    "       r.routecode AS routeCode,\n" +
                    "       r.menureason AS menuReason,\n" +
                    "       rv.latitude AS latitude,\n" +
                    "       rv.longitude AS longitude,\n" +
                    "       obt.starttime AS startTime,\n" +
                    "       obt.endtime AS endTime,\n" +
                    "       r.digitalsignature AS digitalSignature,\n" +
                    "       ob.invoiceno AS invoiceNo,\n" +
                    "       ob.orderdate AS OrderDate,\n" +
                    "       ob.isExistRetailer  AS isExistRetailer,\n" +
                    "       sum(ob.primaryDiscOrderValue)AS primaryDiscOrderValue,\n" +
                    "       sum(ob.OrderValue)AS OrderValue,\n" +
                    "       sum(ob.discountAmount)AS discountAmount,\n" +
                    "       sum(ob.taxPerProduct)AS taxPerProduct,\n" +
                    "       sum(ob.totalAmount) AS totalAmount,\n" +
                    "       sum(ob.netAmount) AS netAmount,\n" +
                    "       r.gstno AS gstNo,\n" +
                    "       r.panno AS panNo,\n" +
                    "       ob.upload,\n" +
                    "       r.gsttype AS gstType,\n" +
                    "       r.customerShipCode AS customerShipCode\n" +
                    "FROM " + TABLE_RETAILER + " r\n" +
                    "INNER JOIN " + TABLE_RETAILER_VISIT + " rv ON r.distrcode = rv.distrcode\n" +
                    "AND r.salesmancode = rv.salesmancode\n" +
                    "AND r.routecode = rv.routecode\n" +
                    "AND r.customercode = rv.customercode\n" +
                    "LEFT JOIN " + TABLE_BILLING_TRACKER + " obt ON r.distrcode = obt.distrcode\n" +
                    "AND r.salesmancode = obt.salesmancode\n" +
                    "AND r.routecode = obt.routecode\n" +
                    "AND r.customercode = obt.customercode\n" +
                    "AND obt.upload = 'N'\n" +
                    "INNER JOIN " + TABLE_BILLING + " ob ON r.distrcode = ob.distrcode\n" +
                    "AND r.salesmancode = ob.salesmancode\n" +
                    "AND r.routecode = ob.routecode\n" +
                    "AND r.customercode = ob.retlrcode\n" +
                    "WHERE r.distrcode = '" + distrCode + "'\n" +
                    "  AND r.salesmancode ='" + salesmanCode + "'\n" +
                    "  AND ob.completeFlag = 'Y'\n" +
                    "  AND ob.upload='N'\n" +
                    "GROUP BY r.customercode\n" +
                    "HAVING SUM(ob.OrderValue) > 0\n" +
                    "UNION\n" +
                    "SELECT r.customerCode AS customerCode,\n" +
                    "       r.cmpCode AS cmpCode,\n" +
                    "       ob.distrCode AS distrCode,\n" +
                    "       ob.salesmanCode AS salesmanCode,\n" +
                    "       ob.routeCode AS routeCode,\n" +
                    "       r.menureason AS menuReason,\n" +
                    "       r.latitude AS latitude,\n" +
                    "       r.longitude AS longitude,\n" +
                    "       obt.starttime AS startTime,\n" +
                    "       obt.endtime AS endTime,\n" +
                    "       r.digitalsignature AS digitalSignature,\n" +
                    "       ob.invoiceNo AS invoiceNo,\n" +
                    "       ob.orderdate AS OrderDate,\n" +
                    "       ob.isExistRetailer  AS isExistRetailer,\n" +
                    "       sum(ob.primaryDiscOrderValue)AS primaryDiscOrderValue,\n" +
                    "       sum(ob.OrderValue)AS OrderValue,\n" +
                    "       sum(ob.discountAmount)AS discountAmount,\n" +
                    "       sum(ob.taxPerProduct)AS taxPerProduct,\n" +
                    "       sum(ob.totalAmount) AS totalAmount,\n" +
                    "       sum(ob.netAmount) AS netAmount,\n" +
                    "       r.gstno AS gstNo,\n" +
                    "       r.panno AS panNo,\n" +
                    "       ob.upload,\n" +
                    "       r.gsttype AS gstType,\n" +
                    "       r.customerShipCode AS customerShipCode\n" +
                    "FROM " + TABLE_NEW_RETAILER + " r\n" +
                    "LEFT JOIN " + TABLE_BILLING_TRACKER + " obt ON  ((r.customerSharedFlag != 'Z' AND r.customercode = obt.customercode) \n" +
                    " OR (r.customerSharedFlag = 'Z' AND r.cmpCustomerCode = obt.customercode))  \n" +
                    "AND obt.upload = 'N'\n" +
                    "AND r.customercode NOT NULL\n" +
                    "INNER JOIN " + TABLE_BILLING + " ob ON obt.distrcode = ob.distrcode\n" +
                    "AND obt.salesmancode = ob.salesmancode\n" +
                    "AND obt.routecode = ob.routecode\n" +
                    "AND ((r.customerSharedFlag != 'Z' AND r.customercode = ob.retlrcode) \n" +
                    " OR (r.customerSharedFlag = 'Z' AND r.cmpCustomerCode = ob.retlrcode)) \n" +
                    "WHERE r.distrcode = '" + distrCode + "'\n" +
                    "  AND r.salesmancode ='" + salesmanCode + "'\n" +
                    "  AND ob.completeFlag = 'Y'\n" +
                    "  AND ob.upload = 'N'\n" +
                    "GROUP BY r.customercode\n" +
                    "HAVING SUM(ob.OrderValue) > 0";
        }*/
        query = "SELECT ob.cmpCode            AS cmpCode,\n" +
                "       ob.distrCode          AS distrCode,\n" +
                "       ob.retlrcode          AS customerCode,\n" +
                "       ob.invoiceNo          AS invoiceNo,\n" +
                "       ob.readableInvNo      AS readableInvNo,\n" +
                "       ob.OrderDate          AS OrderDate,\n" +
                "       ob.remarks            AS Remarks,\n" +
                "       ob.OrderStatus        AS OrderStatus,\n" +
                "        ob.latitude,\n" +
                "       ob.longitude,\n" +
                "       ob.startTime,\n" +
                "       ob.endTime,\n" +
                "       SUM(ob.OrderValue)    AS OrderValue,\n" +
                "       SUM(ob.schemeAmount)  AS discountAmount,\n" +
                "       SUM(ob.taxPerProduct) AS taxPerProduct,\n" +
                "       SUM(ob.totalAmount)   AS totalAmount,\n" +
                "       SUM(ob.netAmount)     AS netAmount,\n" +
                "       SUM(ob.grossAmount)     AS grossAmount,\n" +
                "       ob.customerShipCode\n" +
                "FROM t_OrderBooking ob\n" +
                "WHERE ob.upload = 'N' and ob.completeFlag = 'Y' \n" +
                "GROUP BY ob.cmpcode, ob.distrcode, ob.invoiceNo;";

        if (BuildConfig.DEBUG)
            Log.i("getRetailerDetails()", query);

        List<RetailerVO> retailerList = new ArrayList<>();
        Cursor c = null;
        try {
            c = db.rawQuery(query, null);

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    RetailerVO retailerVO = new RetailerVO();
                    retailerVO.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                    retailerVO.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                    retailerVO.setCustomerCode(checkString(c, COLUMN_CUSTOMER_CODE));
                    retailerVO.setCustomerShipCode(checkString(c, COLUMN_CUSTOMER_SHIP_CODE));
                    retailerVO.setCustomerRefNo(checkString(c, READABLE_INV_NO));
                    retailerVO.setLatitude(checkString(c, COLUMN_LATITUDE));
                    retailerVO.setLongitude(checkString(c, COLUMN_LONGITUDE));
                    retailerVO.getOrderBookingVO().setStartTime(checkString(c, COLUMN_START_TIME));
                    retailerVO.getOrderBookingVO().setEndTime(checkString(c, COLUMN_END_TIME));
                    retailerVO.getOrderBookingVO().setDicountAmt(BigDecimal.valueOf(BaseDB.checkDouble(c, DISCOUNT_AMOUNT)));
                    retailerVO.getOrderBookingVO().setTax(BaseDB.checkDouble(c, TAX_PER_PRODUCT));
                    retailerVO.getOrderBookingVO().setTotalAmount(BigDecimal.valueOf(BaseDB.checkDouble(c, TOTAL_AMOUNT)));
                    retailerVO.getOrderBookingVO().setlGrossAmt(BigDecimal.valueOf(BaseDB.checkDouble(c, GROSS_AMOUNT)));
                    retailerVO.getOrderBookingVO().setSchemeAmount(BigDecimal.valueOf(BaseDB.checkDouble(c, DISCOUNT_AMOUNT)));
                    retailerVO.getOrderBookingVO().setOrderStatus(checkString(c, COLUMN_ORDER_STATUS_CAPS));
                    retailerVO.getOrderBookingVO().setRemarks(checkString(c, COLUMN_REMARKS_CAPS));
                    retailerVO.setOrderNo(checkString(c, INVOICE_NO));
                    retailerVO.setOrderDt(checkString(c, COLUMN_ORDER_DATE_CAPS));

                    retailerList.add(retailerVO);
                }

            }
        } catch (Exception e) {
            Log.e(TAG, "getOrderBookingRetailerDetails: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        baseDB.closeDb();
        return retailerList;
    }

    public List<OrderBookingVO> getOrderBookingLineItem(BaseDB baseDB, String distrCode, String salesmanCode,
                                                        String routeCode, String customerCode) {
        db = baseDB.getDb();

        String query = "SELECT ob.cmpCode,\n" +
                "       ob.distrCode,\n" +
                "       ob.invoiceno,\n" +
                "       ob.orderdate,\n" +
                "       ob.RetlrCode,\n" +
                "       ob.prodcode,\n" +
                "       p.prodName as ProdName,\n" +
                "       ob.batchcode,\n" +
                "       ob.orderqty,\n" +
                "       ob.mrp,\n" +
                "       pbm.SellPrice,\n" +
                "       ob.ordervalue,\n" +
                "       ob.primaryDiscOrderValue,\n" +
                "       ob.schemeAmount as discountAmount,\n" +
                "       ob.totalAmount,\n" +
                "       ob.netAmount,\n" +
                "       ob.grossAmount,\n" +
                "       ob.actualSellRate,\n" +
                "       ob.CGSTValue,\n" +
                "       ob.CGSTPerc,\n" +
                "       ob.SGSTValue,\n" +
                "       ob.SGSTPerc,\n" +
                "       ob.UTGSTValue,\n" +
                "       ob.UTGSTPerc,\n" +
                "       ob.IGSTValue,\n" +
                "       ob.IGSTPerc,\n" +
                "       ob.uomid,\n" +
                "       ob.conversionfactor,\n" +
                "       ob.defaultuomid,\n" +
                "       ob.taxCode,\n" +
                "       ob.prodType,\n" +
                "       ob.isExistRetailer\n" +
                "FROM t_OrderBooking ob\n" +
                "         INNER JOIN m_product_master p\n" +
                "                    ON p.cmpCode = ob.cmpCode\n" +
                "                        AND p.ProdCode = ob.ProdCode\n" +
                "         INNER JOIN m_product_batch_master pbm\n" +
                "                    ON pbm.cmpCode = ob.cmpCode\n" +
                "                        AND pbm.distrCode = ob.distrCode\n" +
                "                        AND pbm.ProdCode = ob.ProdCode\n" +
                "                        AND pbm.ProdBatchCode = ob.BatchCode" +
                " where ob.cmpCode = ? and ob.distrCode = ? and ob.invoiceNo = ?";

        Cursor c = db.rawQuery(query, new String[]{ distrCode, salesmanCode,routeCode});
        List<OrderBookingVO> orderBookingList = new ArrayList<>();
        try {
            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {
                    Double taxValue = Double.valueOf(c.getString(c.getColumnIndex(CGST_VALUE)))
                            + Double.valueOf(c.getString(c.getColumnIndex(SGST_VALUE)))
                            + Double.valueOf(c.getString(c.getColumnIndex(UTGST_VALUE)))
                            + Double.valueOf(c.getString(c.getColumnIndex(IGST_VALUE)));
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                    orderBookingVO.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                    orderBookingVO.setRetailerCode(checkString(c, COLUMN_RETLR_CODE_CAPS));
                    orderBookingVO.setOrderNo(checkString(c, INVOICE_NO));
                    orderBookingVO.setProdCode(checkString(c, COLUMN_PROD_CODE_CAPS));
                    orderBookingVO.setProdName(checkString(c, COLUMN_PROD_NAME_CAPS));
                    orderBookingVO.setProdBatchCode(checkString(c, COLUMN_BATCH_CODE));
                    orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));
                    orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
                    orderBookingVO.setUomId(checkString(c, COLUMN_UOM_ID));
                    orderBookingVO.setDefaultUomid(checkString(c, COLUMN_DEFAULT_UOMID_CAPS));
                    orderBookingVO.setSellPrice(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                    orderBookingVO.setDicountAmt(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(DISCOUNT_AMOUNT))));
                    orderBookingVO.setTax(taxValue);
                    orderBookingVO.setTaxCode(c.getString(c.getColumnIndex(TAX_CODE)));
                    orderBookingVO.setCgstperc(c.getDouble(c.getColumnIndex(CGST_PERCENT)));
                    orderBookingVO.setCgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(CGST_VALUE))));
                    orderBookingVO.setSgstPerc(c.getDouble(c.getColumnIndex(SGST_PERCENT)));
                    orderBookingVO.setSgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(SGST_VALUE))));
                    orderBookingVO.setUtgstPerc(c.getDouble(c.getColumnIndex(UTGST_PERCENT)));
                    orderBookingVO.setUtgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(UTGST_VALUE))));
                    orderBookingVO.setIgstPerc(c.getDouble(c.getColumnIndex(IGST_PERCENT)));
                    orderBookingVO.setIgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(IGST_VALUE))));
                    orderBookingVO.setOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                    orderBookingVO.setProdType(c.getString(c.getColumnIndex(COLUMN_PROD_TYPE)));
                    orderBookingVO.setlGrossAmt(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(GROSS_AMOUNT))));
                    orderBookingVO.setActualSellRate(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_ACTUAL_SELL_RATE))));


//                    orderBookingVO.setOrderDate(checkString(c, COLUMN_ORDER_DATE_CAPS));
//
//                    orderBookingVO.setMrp(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_MRP_CAPS))));
//
//
//                    orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_DEFAULT_UOMID_CAPS)));
//                    orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
//                    orderBookingVO.setIsNewRetailer(c.getString(c.getColumnIndex(IS_EXISTING_RETAILER)));
//                    orderBookingVO.setPrimDiscOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE))));
//                    orderBookingVO.setTotalAmount(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(TOTAL_AMOUNT))));
//                    orderBookingVO.setNetAmount(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(NET_AMOUNT))));

                    orderBookingList.add(orderBookingVO);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getOrderBookingLineItem: " + e.getMessage(), e);
        } finally {
            if (c != null)
                c.close();
        }

        baseDB.closeDb();
        return orderBookingList;
    }
    private void insertFeedbackMasterList(List<FeedBackMasterModel> feedBackMasterModelList) {

        String sql = "INSERT INTO " + TABLE_FEEDBACK_MASTER + " VALUES (?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < feedBackMasterModelList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(feedBackMasterModelList.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(feedBackMasterModelList.get(i).getFeedbackType()));
                statement.bindString(3, Globals.checkNull(feedBackMasterModelList.get(i).getFeedbackName()));
                statement.bindString(4, Globals.checkNull(feedBackMasterModelList.get(i).getConsoleFlag()));
                statement.bindString(5, Globals.checkNull(feedBackMasterModelList.get(i).getUploadFlag()));
                statement.bindString(6, Globals.checkNull(feedBackMasterModelList.get(i).getModDt()));
                statement.bindString(7, Globals.checkNull(feedBackMasterModelList.get(i).getChangeNo()));
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }
    private void insertNotificationTypeList(List<MessageModel> feedBackMasterModelList) {

        String sql = "INSERT INTO " + TABLE_NOTIFICATION_TYPE + " VALUES (?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        for (int i = 0; i < feedBackMasterModelList.size(); i++) {
            try {
                statement.clearBindings();
                statement.bindString(1, Globals.checkNull(feedBackMasterModelList.get(i).getCmpCode()));
                statement.bindString(2, Globals.checkNull(feedBackMasterModelList.get(i).getNotificationType()));
                statement.bindString(3, Globals.checkNull(feedBackMasterModelList.get(i).getNotificationName()));
                statement.execute();
            } catch (SQLiteException e) {
                if (BuildConfig.DEBUG)
                    Log.w(DUPLICATE_TAG, e);
            }
        }
    }
}


