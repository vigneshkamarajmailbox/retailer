/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.async.TaskCallbacks;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.AddressModel;
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
import com.botree.retailerssfa.models.DistBalanceModel;
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
import com.botree.retailerssfa.models.PendingBillCollection;
import com.botree.retailerssfa.models.PendingBillVO;
import com.botree.retailerssfa.models.ProFilterModel;
import com.botree.retailerssfa.models.ProdBatchModel;
import com.botree.retailerssfa.models.ProdHierLvlModel;
import com.botree.retailerssfa.models.ProductMasterModel;
import com.botree.retailerssfa.models.ProductModel;
import com.botree.retailerssfa.models.ProductsVO;
import com.botree.retailerssfa.models.PurchaseInvoiceModel;
import com.botree.retailerssfa.models.ReasonVO;
import com.botree.retailerssfa.models.RetailerCategoryVO;
import com.botree.retailerssfa.models.RouteModel;
import com.botree.retailerssfa.models.SalesDBCRNoteModel;
import com.botree.retailerssfa.models.SalesReturnVO;
import com.botree.retailerssfa.models.SalesmanMasterModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.models.StockOnHandModel;
import com.botree.retailerssfa.models.SubStockistModel;
import com.botree.retailerssfa.models.SupplierModel;
import com.botree.retailerssfa.models.Sync;
import com.botree.retailerssfa.models.TaxMasterModel;
import com.botree.retailerssfa.models.TaxModel;
import com.botree.retailerssfa.models.UomMasterModel;
import com.botree.retailerssfa.models.UomMasterVO;
import com.botree.retailerssfa.models.WDInfrastructure;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.botree.retailerssfa.service.JSONConstants.NEW_APP_DOWNLOAD_URL;
import static com.botree.retailerssfa.service.JSONConstants.NEW_APP_VERSION;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ATTRIBUTE_MASTER_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ATTRIBUTE_VALUE_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_BANK_NAMES;
import static com.botree.retailerssfa.service.JSONConstants.TAG_BRANCH_STOCK;
import static com.botree.retailerssfa.service.JSONConstants.TAG_BULLETIN_BOARD;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CATEGORY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CATEGORY_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CODE_GENERATOR_HISTORY_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_COMPANY_DETAIL;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CONFIGURATION;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CONTRACT_PRICING;
import static com.botree.retailerssfa.service.JSONConstants.TAG_COVERAGE_EXISTS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_BANK;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_PRODUCT_MAPPING;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_ROUTE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_SHIP_ADDR;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_STOCK;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTRIBUTOR;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTRIBUTOR_BALANCE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTRIBUTOR_STOCK;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_LOB_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_WD_FREEZER;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_WD_STORAGE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_EDIT_RATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_FEEDBACK_MASTER;
import static com.botree.retailerssfa.service.JSONConstants.TAG_FEEDBACK_REASON_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_FOCUS_BRAND_PRODUCT_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_FOCUS_BRAND_RETAILER_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GEO_HIER_LEVEL;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GEO_HIER_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GST_STATE_MASTER_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_KEY_GENERATOR;
import static com.botree.retailerssfa.service.JSONConstants.TAG_KYC_MASTER_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LEAVE_REASON_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LOB_MASTER_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LOGISTIC_MATERIAL_STOCK;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MAPPED_COMPANY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MAPPED_CUSTOMER;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MAPPED_DISTRIBUTOR;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MTD_FIELD_WORKING_REPORT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MUST_SELL_SKU_PRODUCT_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MUST_SELL_SKU_RETAILER_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NOTIFICATION_TYPE_MASTER;
import static com.botree.retailerssfa.service.JSONConstants.TAG_OPENING_STOCK;
import static com.botree.retailerssfa.service.JSONConstants.TAG_OPENING_STOCK_MASTER;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ORDER_BOOKING_DETAILS_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ORDER_BOOKING_HEADER_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PENDING_BILL;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PENDING_BILL_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREVIOUSE_ORDER_DETAILS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREVIOUS_PURCHASE_ORDERS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PRODUCTS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PRODUCTS_DUMP;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PRODUCTS_UOM_DUMP;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PRODUCT_BATCH;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PRODUCT_HIER_LEVEL;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PRODUCT_HIER_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PRODUCT_MASTER;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PRODUCT_UOM;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PURCHASE_INVOICE_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_REASONS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_RETAILER_CATEGORY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ROUTE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALESMAN_KYC_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALESMAN_LOB_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALESMAN_MASTER_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALESMAN_ROUTE_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALES_DB_CR_NOTE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_COMBI_PRODUCTS_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_SLAB_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_SLAB_PRODUCT_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SEQUENCE_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SERVER_DATE_MATCHED;
import static com.botree.retailerssfa.service.JSONConstants.TAG_STOCK_LOADING_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_STOCK_ON_HAND;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUGGESTED_ORDER_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUPPLIER;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TAX_STRUCTURE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TAX_STRUCTURE_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TEMP_CUSTOMER;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TEMP_CUSTOMER_ROUTE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TEMP_CUSTOMER_SHIP_ADDR;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TEMP_ROUTE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UOM_MASTER;
import static com.botree.retailerssfa.service.JSONConstants.TAG_USER_ACTIVE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DAY_START_DATE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_NEWAPPDOWNLOADURL;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_NEW_APK_VERSION;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_OPENING_STOCK;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;


public class DayStartHelper {

    private static final String TAG = DayStartHelper.class.getSimpleName();
    private AppUtils appUtils;
    private String response;
    private TaskCallbacks taskCallbacks;
    private Context context;
    private Sync sync;
    private List<EncodeModel> encodeModels;

    public DayStartHelper(Context context, String response, TaskCallbacks taskCallbacks) {
        this.context = context;
        this.response = response;
        this.taskCallbacks = taskCallbacks;
        appUtils = AppUtils.getOurInstance();

    }

    public DayStartHelper(Context context) {
        this.context = context;
    }

    public static boolean isJsonNotNull(JSONObject jsonObj, String key) {
        return jsonObj.has(key) && !(jsonObj.isNull(key));
    }

    public void updateTaskCallback(TaskCallbacks taskCallbacks) {
        this.taskCallbacks = taskCallbacks;
    }

    public AsyncResponse dayStart() {

        try {
            JSONObject jsonObj = new JSONObject(response);

            if (jsonObj.has(TAG_SERVER_DATE_MATCHED) &&
                    jsonObj.get(TAG_SERVER_DATE_MATCHED) instanceof Boolean &&
                    !jsonObj.getBoolean(TAG_SERVER_DATE_MATCHED)) {

                return AsyncResponse.SERVER_DATE_MISMATCH;

            } else if (jsonObj.has(TAG_USER_ACTIVE) &&
                    jsonObj.get(TAG_USER_ACTIVE) instanceof Boolean &&
                    !jsonObj.getBoolean(TAG_USER_ACTIVE)) {
                Log.d(TAG, "jsonObj.getBoolean(TAG_USER_ACTIVE) : " + jsonObj.getBoolean(TAG_USER_ACTIVE));
                return AsyncResponse.USER_ACTIVE;

            } else if (isNewApkAvailable(context, jsonObj)) {

                return AsyncResponse.NEW_APK_AVAILABLE;

            } else if (jsonObj.has(TAG_COVERAGE_EXISTS) &&
                    jsonObj.get(TAG_COVERAGE_EXISTS) instanceof Boolean &&
                    !jsonObj.getBoolean(TAG_COVERAGE_EXISTS)) {

                return AsyncResponse.NO_COVERAGE_EXISTS;

            } else {
                taskCallbacks.onProgressUpdate(57);
                if (encodeModels != null && !encodeModels.isEmpty()) {
                    encodeModels.clear();
                }
                encodeModels = new ArrayList<>();
                sync = Sync.getInstance();

                addMappedCompany(jsonObj);
                addMappedDistributor(jsonObj);
                addMappedCustomer(jsonObj);
                addCompanyDetail(jsonObj);
                addConfiguration(jsonObj);
                addKeyGenerator(jsonObj);
                addDistributorList(jsonObj);
                addGstStateMasterList(jsonObj);
                addLobMasterList(jsonObj);
                addDistrLobList(jsonObj);
                addCustomer(jsonObj);
                addCustomerShipAddress(jsonObj);
                addOrderBookingHeader(jsonObj);
                addProductHierLevel(jsonObj);
                addProductHierValue(jsonObj);
                addProductUOMMaster(jsonObj);
                addProductTaxStructureList(jsonObj);
                addProductBatchMaster(jsonObj);
                addProductMaster(jsonObj);
                addCategoryList(jsonObj);
                addDistributorStock(jsonObj);
                addCustomerStock(jsonObj);
                taskCallbacks.onProgressUpdate(58);
                addLobMasterList(jsonObj);
                taskCallbacks.onProgressUpdate(59);
                addDistrLobList(jsonObj);
                addfeedbackMaster(jsonObj);
                addNotificationTypeMaster(jsonObj);











//                taskCallbacks.onProgressUpdate(57);
//                addProducts(jsonObj);
//                addVansalesProducts(jsonObj);
//
//                taskCallbacks.onProgressUpdate(57);
//                addBankNames(jsonObj);
//                taskCallbacks.onProgressUpdate(60);
//
//                addPendingBills(jsonObj);
//                taskCallbacks.onProgressUpdate(57);
//
//
//                taskCallbacks.onProgressUpdate(57);
//
//
//                taskCallbacks.onProgressUpdate(57);
//                addCategoryList(jsonObj);// No need for bulk insertion
//                taskCallbacks.onProgressUpdate(57);
//                addReasons(jsonObj);
//                addLeaveReasons(jsonObj);
//                addFeedbackReasons(jsonObj);
//                taskCallbacks.onProgressUpdate(57);
//                addRetailerCategoryList(jsonObj);
//
//                taskCallbacks.onProgressUpdate(57);
//                addUomMaster(jsonObj);
//                addVansalesUomMaster(jsonObj);
//                taskCallbacks.onProgressUpdate(57);
//                addTaxStructureList(jsonObj);
//
//                //Schemes Data
//                taskCallbacks.onProgressUpdate(57);
//                //Scheme Slab  List
//                addSchemeSlabList(jsonObj);
//                //Scheme Slab Product List
//                addSchemeSlabProdList(jsonObj);
//                taskCallbacks.onProgressUpdate(57);
//
//                taskCallbacks.onProgressUpdate(57);
//                // scheme combi products
//                addSchemeCombiProductsList(jsonObj);
//
//                taskCallbacks.onProgressUpdate(57);
//                //Focus Brand product List
//                addFocusBrandProductList(jsonObj);
//                taskCallbacks.onProgressUpdate(57);
//                //Must Sell Sku product List
//                addMustSellSKUProductList(jsonObj);
//
//                //Focus Brand Retailer List
//                addFocusBrandRetailerList(jsonObj);
//
//                taskCallbacks.onProgressUpdate(57);
//                //Must Sell Sku Retailer List
//                addMustSellSKURetailerList(jsonObj);
//
//                //Attribute Master list
//                addAttributeMasterList(jsonObj);
//                //Attribute Value list
//                addAttributeValuesList(jsonObj);
//                taskCallbacks.onProgressUpdate(57);
//
//                taskCallbacks.onProgressUpdate(57);
//                // MTD field working report
//                addMTDFieldWorkingReport(jsonObj);
//                //GST StateMaster list
//                addGstStateMasterList(jsonObj);
//                taskCallbacks.onProgressUpdate(57);
//
//                taskCallbacks.onProgressUpdate(57);
//
//                addSuggestProductQty(jsonObj);
//
//                addDistOpeningStockMaster(jsonObj);
//
//                addVanSalesStockLoadingList(jsonObj);

                //SMDMS
                taskCallbacks.onProgressUpdate(60);
                addDistributorList(jsonObj);
                taskCallbacks.onProgressUpdate(61);
                addDistributorBalanceList(jsonObj);
                taskCallbacks.onProgressUpdate(63);
                addBranchStockList(jsonObj);
                taskCallbacks.onProgressUpdate(65);

                taskCallbacks.onProgressUpdate(66);

                addPreviousPurchaseOrder(jsonObj);

                addKYCMasterList(jsonObj);
                taskCallbacks.onProgressUpdate(67);

                taskCallbacks.onProgressUpdate(68);
                addProductMaster(jsonObj);
                taskCallbacks.onProgressUpdate(69);
                addProductBatchMaster(jsonObj);
                taskCallbacks.onProgressUpdate(70);
                addProductUOMMaster(jsonObj);
                taskCallbacks.onProgressUpdate(71);
                addProductHierLevel(jsonObj);
                taskCallbacks.onProgressUpdate(72);
                addProductHierValue(jsonObj);
                taskCallbacks.onProgressUpdate(73);
                addProductTaxStructureList(jsonObj);
                taskCallbacks.onProgressUpdate(74);
                addStockOnHandList(jsonObj);
                taskCallbacks.onProgressUpdate(75);

                addGeoHierLevel(jsonObj);
                taskCallbacks.onProgressUpdate(76);
                addGeoHierValue(jsonObj);

                taskCallbacks.onProgressUpdate(77);
                addWDStorage(jsonObj);
                taskCallbacks.onProgressUpdate(78);
                addWDFreezer(jsonObj);
                taskCallbacks.onProgressUpdate(79);
                addRouteList(jsonObj);
                addCustomer(jsonObj);
                taskCallbacks.onProgressUpdate(80);
                addCustomerShipAddress(jsonObj);
                taskCallbacks.onProgressUpdate(81);
                addCustomerRoute(jsonObj);
                taskCallbacks.onProgressUpdate(82);
                addCustomerBank(jsonObj);
                taskCallbacks.onProgressUpdate(84);
                addSalesmanMasterList(jsonObj);
                taskCallbacks.onProgressUpdate(85);
                addSalesmanKycList(jsonObj);
                addSalesmanRouteList(jsonObj);
                addPurchaseInvoice(jsonObj);
                addSalesmanLobList(jsonObj);
                taskCallbacks.onProgressUpdate(86);
                addLogisticMaterialStock(jsonObj);
//                addOrderBookingHeader(jsonObj);
                taskCallbacks.onProgressUpdate(87);
                addOrderBookingDetails(jsonObj);
                addPendingBillCollections(jsonObj);
                addSupplierDetails(jsonObj);
                addEditRateDetails(jsonObj);

                taskCallbacks.onProgressUpdate(88);
                addCustomerProductMapping(jsonObj);
                //addContractPricing(jsonObj);
                addSalesDBCRNote(jsonObj);
                addBulletinBoardValue(jsonObj);
                taskCallbacks.onProgressUpdate(89);
                addTempCustomer(jsonObj);
                addTempCustomerRoute(jsonObj);
                addTempCustomerShipAddress(jsonObj);
                addTempRoute(jsonObj);
                addKeyGeneratorHistory(jsonObj);

                taskCallbacks.onProgressUpdate(90);
                SFADatabase database = SFADatabase.getInstance(context);
                database.insertSyncData(sync, null);
                taskCallbacks.onProgressUpdate(95);
                String today = DateUtil.getCurrentDate();

                SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);

                boolean isSyncDataAvailable = database.isDataAvailable(preferences.getString(PREF_DISTRCODE, ""), today);

                if (!isSyncDataAvailable) {
                    database.deleteSyncTableData();
                    database.insertSyncProgress(today, "Y", "N", "N");
                }

                SharedPreferences.Editor editor = SFASharedPref.getEditor(preferences);
                editor.putString(PREF_DAY_START_DATE, DateUtil.getCurrentDate());
                editor.commit();

                taskCallbacks.onProgressUpdate(97);
            }
        } catch (SQLiteException e) {
            Log.e("dayStart 1: ", e.getMessage(), e);
            if (BuildConfig.DEBUG)
                Log.w(TAG, e);
            return AsyncResponse.DAY_START_ERROR;

        } catch (JSONException e) {
            Log.e(TAG, "dayStart: " + e.getMessage(), e);
            return AsyncResponse.DAY_START_ERROR;
        }
        return AsyncResponse.DAY_START_DONE;

    }

    private void addSalesDBCRNote(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_SALES_DB_CR_NOTE))
                return;

            if (jsonObj.get(TAG_SALES_DB_CR_NOTE) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SALES_DB_CR_NOTE));
                List<SalesDBCRNoteModel> models;
                JSONArray jsonArray = new JSONArray(responseString);
                models = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, SalesDBCRNoteModel.class);
                sync.setSalesDBCRNoteModels(models);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addContractPricing(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_CONTRACT_PRICING))
                return;

            if (jsonObj.get(TAG_CONTRACT_PRICING) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_CONTRACT_PRICING));
                List<ContractPricingModel> models;
                JSONArray jsonArray = new JSONArray(responseString);
                models = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, ContractPricingModel.class);
                sync.setContractPricingModels(models);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addCustomerProductMapping(JSONObject jsonObj) {

        try {
            if (!isJsonNotNull(jsonObj, TAG_CUSTOMER_PRODUCT_MAPPING))
                return;

            if (jsonObj.get(TAG_CUSTOMER_PRODUCT_MAPPING) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_CUSTOMER_PRODUCT_MAPPING));
                List<CustProdMappingModel> models;
                JSONArray jsonArray = new JSONArray(responseString);
                models = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, CustProdMappingModel.class);
                sync.setCustProdMappingList(models);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

    private void addEditRateDetails(JSONObject jsonObj) {

        try {
            if (!isJsonNotNull(jsonObj, TAG_EDIT_RATE))
                return;

            if (jsonObj.get(TAG_EDIT_RATE) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_EDIT_RATE));
                List<EditRateModel> retailerVOS;
                JSONArray jsonArray = new JSONArray(responseString);
                retailerVOS = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, EditRateModel.class);
                sync.setEditRateList(retailerVOS);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addSupplierDetails(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_SUPPLIER))
                return;

            if (jsonObj.get(TAG_SUPPLIER) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SUPPLIER));
                List<SupplierModel> retailerVOS;
                JSONArray jsonArray = new JSONArray(responseString);
                retailerVOS = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, SupplierModel.class);
                sync.setSupplierList(retailerVOS);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addPendingBillCollections(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_PENDING_BILL))
                return;

            if (jsonObj.get(TAG_PENDING_BILL) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_PENDING_BILL));
                List<PendingBillCollection> pendingBillCollections;
                JSONArray jsonArray = new JSONArray(responseString);
                pendingBillCollections = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, PendingBillCollection.class);
                sync.setPendingBillCollections(pendingBillCollections);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addCustomerBank(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_CUSTOMER_BANK))
                return;

            if (jsonObj.get(TAG_CUSTOMER_BANK) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_CUSTOMER_BANK));
                List<CustomerBankModel> retailerVOS;
                JSONArray jsonArray = new JSONArray(responseString);
                retailerVOS = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, CustomerBankModel.class);
                sync.setCustomerBank(retailerVOS);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addCustomerRoute(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_CUSTOMER_ROUTE))
                return;

            if (jsonObj.get(TAG_CUSTOMER_ROUTE) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_CUSTOMER_ROUTE));
                List<RouteModel> retailerVOS;
                JSONArray jsonArray = new JSONArray(responseString);
                retailerVOS = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, RouteModel.class);
                sync.setCustomerRoute(retailerVOS);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addCustomerShipAddress(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_CUSTOMER_SHIP_ADDR))
                return;

            if (jsonObj.get(TAG_CUSTOMER_SHIP_ADDR) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_CUSTOMER_SHIP_ADDR));
                List<AddressModel> retailerVOS;
                JSONArray jsonArray = new JSONArray(responseString);
                retailerVOS = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, AddressModel.class);
                sync.setCustomerShipAddress(retailerVOS);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addCustomer(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_CUSTOMER))
                return;

            if (jsonObj.get(TAG_CUSTOMER) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_CUSTOMER));
                List<CustomerModel> retailerVOS;
                JSONArray jsonArray = new JSONArray(responseString);
                retailerVOS = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, CustomerModel.class);
                sync.setCustomer(retailerVOS);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addWDFreezer(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_DISTR_WD_FREEZER))
                return;

            if (jsonObj.get(TAG_DISTR_WD_FREEZER) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_DISTR_WD_FREEZER));
                List<WDInfrastructure> wdInfrastructures;
                JSONArray jsonArray = new JSONArray(responseString);
                wdInfrastructures = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, WDInfrastructure.class);
                sync.setWdInfrastructureFreezer(wdInfrastructures);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addWDStorage(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_DISTR_WD_STORAGE))
                return;

            if (jsonObj.get(TAG_DISTR_WD_STORAGE) instanceof String) {
                WDInfrastructure wdInfrastructures;
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_DISTR_WD_STORAGE));
                wdInfrastructures = new Gson().fromJson(responseString, WDInfrastructure.class);
                sync.setWdInfrastructureStorage(wdInfrastructures);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addPreviousPurchaseOrder(JSONObject jsonObj) {

        try {
            if (!isJsonNotNull(jsonObj, TAG_PREVIOUS_PURCHASE_ORDERS))
                return;

            if (jsonObj.get(TAG_PREVIOUS_PURCHASE_ORDERS) instanceof String) {

                EncodeModel model = new EncodeModel();
                model.setEnkey(TAG_PREVIOUS_PURCHASE_ORDERS);
                model.setEnValue(jsonObj.getString(TAG_PREVIOUS_PURCHASE_ORDERS));
                encodeModels.add(model);
                sync.setEncodedKeyValueList(encodeModels);

                /*String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_PREVIOUS_PURCHASE_ORDERS));
                List<PreviousPOModel> stockOnHandModels;
                JSONArray jsonArray = new JSONArray(responseString);
                stockOnHandModels = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, PreviousPOModel.class);
                sync.setPreviousPOStatusList(stockOnHandModels);*/
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addStockOnHandList(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_STOCK_ON_HAND))
                return;

            if (jsonObj.get(TAG_STOCK_ON_HAND) instanceof String) {

                /*EncodeModel model = new EncodeModel();
                model.setEnkey(TAG_STOCK_ON_HAND);
                model.setEnValue(jsonObj.getString(TAG_STOCK_ON_HAND));
                encodeModels.add(model);
                sync.setEncodedKeyValueList(encodeModels);*/


                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_STOCK_ON_HAND));
                List<StockOnHandModel> stockOnHandModels;
                JSONArray jsonArray = new JSONArray(responseString);
                stockOnHandModels = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, StockOnHandModel.class);
                sync.setStockOnHandList(stockOnHandModels);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addGeoHierValue(JSONObject jsonObj) {

        try {
            if (!isJsonNotNull(jsonObj, TAG_GEO_HIER_VALUE))
                return;

            if (jsonObj.get(TAG_GEO_HIER_VALUE) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_GEO_HIER_VALUE));
                List<GeoHierModel> geoHierModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                geoHierModelList = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, GeoHierModel.class);
                sync.setGeoHierValueList(geoHierModelList);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addSalesmanMasterList(JSONObject jsonObj) {

        try {

            if (!isJsonNotNull(jsonObj, TAG_SALESMAN_MASTER_LIST))
                return;

            if (jsonObj.get(TAG_SALESMAN_MASTER_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SALESMAN_MASTER_LIST));
                List<SalesmanMasterModel> salesmanModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                salesmanModelList = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, SalesmanMasterModel.class);
                sync.setSalesmanMasterModelList(salesmanModelList);
            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addSalesmanKycList(JSONObject jsonObj) {

        try {

            if (!isJsonNotNull(jsonObj, TAG_SALESMAN_KYC_LIST))
                return;

            if (jsonObj.get(TAG_SALESMAN_KYC_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SALESMAN_KYC_LIST));
                List<SalesmanMasterModel> salesmanModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                salesmanModelList = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, SalesmanMasterModel.class);
                sync.setSalesmanMasterKycList(salesmanModelList);
            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addSalesmanRouteList(JSONObject jsonObj) {

        try {

            if (!isJsonNotNull(jsonObj, TAG_SALESMAN_ROUTE_LIST))
                return;

            if (jsonObj.get(TAG_SALESMAN_ROUTE_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SALESMAN_ROUTE_LIST));
                List<SalesmanMasterModel> salesmanModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                salesmanModelList = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, SalesmanMasterModel.class);
                sync.setSalesmanMasterRouteList(salesmanModelList);
            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }


    private void addSalesmanLobList(JSONObject jsonObj) {

        try {

            if (!isJsonNotNull(jsonObj, TAG_SALESMAN_LOB_LIST))
                return;

            if (jsonObj.get(TAG_SALESMAN_LOB_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_SALESMAN_LOB_LIST));
                List<SalesmanMasterModel> salesmanModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                salesmanModelList = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, SalesmanMasterModel.class);
                sync.setSalesmanMasterLobList(salesmanModelList);
            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addGeoHierLevel(JSONObject jsonObj) throws JSONException {

        try {
            if (!isJsonNotNull(jsonObj, TAG_GEO_HIER_LEVEL))
                return;

            if (jsonObj.get(TAG_GEO_HIER_LEVEL) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_GEO_HIER_LEVEL));
                List<GeoHierModel> geoHierModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                geoHierModelList = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, GeoHierModel.class);
                sync.setGeoHierLevelList(geoHierModelList);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addProductBatchMaster(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_PRODUCT_BATCH))
            return;
        try {
            if (jsonObj.get(TAG_PRODUCT_BATCH) instanceof String) {


                /*EncodeModel model = new EncodeModel();
                model.setEnkey(TAG_PRODUCT_BATCH);
                model.setEnValue(jsonObj.getString(TAG_PRODUCT_BATCH));
                encodeModels.add(model);
                sync.setEncodedKeyValueList(encodeModels);*/

                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_PRODUCT_BATCH));
                List<ProdBatchModel> modelList;
                JSONArray jsonArray = new JSONArray(responseString);
                modelList = appUtils.getConvertedListFromJson(jsonArray, ProdBatchModel.class);
                sync.setProdBatchModelList(modelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addProductBatchMaster : exp : " + e);
        }
    }

    private void addProductTaxStructureList(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_TAX_STRUCTURE))
            return;
        try {

            if (jsonObj.get(TAG_TAX_STRUCTURE) instanceof String) {

                /*EncodeModel model = new EncodeModel();
                model.setEnkey(TAG_TAX_STRUCTURE);
                model.setEnValue(jsonObj.getString(TAG_TAX_STRUCTURE));
                encodeModels.add(model);
                sync.setEncodedKeyValueList(encodeModels);*/


                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_TAX_STRUCTURE));
                List<TaxMasterModel> modelList;
                JSONArray jsonArray = new JSONArray(responseString);
                modelList = appUtils.getConvertedListFromJson(jsonArray, TaxMasterModel.class);
                sync.setTaxMasterModelList(modelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addProductTaxStructureList : exp : " + e);
        }
    }

    private void addProductUOMMaster(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_PRODUCT_UOM))
            return;
        try {

            if (jsonObj.get(TAG_PRODUCT_UOM) instanceof String) {

                /*EncodeModel model = new EncodeModel();
                model.setEnkey(TAG_PRODUCT_UOM);
                model.setEnValue(jsonObj.getString(TAG_PRODUCT_UOM));
                encodeModels.add(model);
                sync.setEncodedKeyValueList(encodeModels);*/

                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_PRODUCT_UOM));
                List<UomMasterModel> modelList;
                JSONArray jsonArray = new JSONArray(responseString);
                modelList = appUtils.getConvertedListFromJson(jsonArray, UomMasterModel.class);
                sync.setUomMasterModelList(modelList);
            }

        } catch (JSONException e) {
            Log.d(TAG, "addProductUOMMaster : exp : " + e);
        }


    }

    private void addProductHierValue(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_PRODUCT_HIER_VALUE))
            return;
        try {

            if (jsonObj.get(TAG_PRODUCT_HIER_VALUE) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_PRODUCT_HIER_VALUE));
                List<ProdHierLvlModel> modelList;
                JSONArray jsonArray = new JSONArray(responseString);
                modelList = appUtils.getConvertedListFromJson(jsonArray, ProdHierLvlModel.class);
                sync.setProdHierValueList(modelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addProductHierValue : exp : " + e);
        }
    }

    private void addProductHierLevel(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_PRODUCT_HIER_LEVEL))
            return;
        try {

            if (jsonObj.get(TAG_PRODUCT_HIER_LEVEL) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_PRODUCT_HIER_LEVEL));
                List<ProdHierLvlModel> productMasterModels;
                JSONArray jsonArray = new JSONArray(responseString);
                productMasterModels = appUtils.getConvertedListFromJson(jsonArray, ProdHierLvlModel.class);
                sync.setProdHierLvlModelList(productMasterModels);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addProductHierLevel : exp : " + e);
        }
    }

    private void addProductMaster(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_PRODUCT_MASTER))
            return;
        try {

            if (jsonObj.get(TAG_PRODUCT_MASTER) instanceof String) {

               /* EncodeModel model1 = new EncodeModel();
                model1.setEnkey(TAG_PRODUCT_MASTER);
                model1.setEnValue(jsonObj.getString(TAG_PRODUCT_MASTER));
                encodeModels.add(model1);
                sync.setEncodedKeyValueList(encodeModels);*/

                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_PRODUCT_MASTER));
                List<ProductMasterModel> productMasterModels;
                JSONArray jsonArray = new JSONArray(responseString);
                productMasterModels = appUtils.getConvertedListFromJson(jsonArray, ProductMasterModel.class);

                List<ProFilterModel> productsFilterList = new ArrayList<>();

                try {

                    for (int i = 0; i < productMasterModels.size(); i++) {
                        StringBuilder hierPath = new StringBuilder(productMasterModels.get(i).getProductHierPathCode());
                        StringBuilder hierName = new StringBuilder(productMasterModels.get(i).getProductHierPathName());

                        String[] strCode = String.valueOf(hierPath).split("/");
                        String[] strName = String.valueOf(hierName).split("/");
                        Collections.reverse(Arrays.asList(strCode));
                        Collections.reverse(Arrays.asList(strName));

                        ProFilterModel model = new ProFilterModel();
                        model.setPathCode(strCode);
                        model.setPathName(strName);
                        model.setLobDivisionCode(productMasterModels.get(i).getLobDivisionCode());
                        productsFilterList.add(model);
                    }
                    sync.setProFilterModelList(productsFilterList);

                } catch (Exception e) {
                    Log.e(TAG, "addProducts: " + e.getMessage(), e);
                }

                sync.setProductMasterModelList(productMasterModels);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addProductMaster : exp : " + e);
        }
    }

    private void addKYCMasterList(JSONObject jsonObj) {
        if (!isJsonNotNull(jsonObj, TAG_KYC_MASTER_LIST))
            return;
        try {

            if (jsonObj.get(TAG_KYC_MASTER_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_KYC_MASTER_LIST));
                List<KycModel> kycModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                kycModelList = appUtils.getConvertedListFromJson(jsonArray, KycModel.class);
                sync.setKYCMasterList(kycModelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addKYCMasterList : exp : " + e);
        }
    }

    private void addCompanyDetail(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_COMPANY_DETAIL))
            return;
        try {

            if (jsonObj.get(TAG_COMPANY_DETAIL) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_COMPANY_DETAIL));
                List<CompanyModel> modelList;
                JSONArray jsonArray = new JSONArray(responseString);
                modelList = appUtils.getConvertedListFromJson(jsonArray, CompanyModel.class);
                sync.setCompanyDetail(modelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addCompanyDetail : exp : " + e);
        }
    }

    private void addMappedCompany(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_MAPPED_COMPANY))
            return;
        try {

            if (jsonObj.get(TAG_MAPPED_COMPANY) instanceof JSONArray) {
                List<String> modelList;
                JSONArray jsonArray = jsonObj.getJSONArray(TAG_MAPPED_COMPANY);
                modelList = new ArrayList<>();
                for(int i = 0 ; i < jsonArray.length(); i++){
                    modelList.add(jsonArray.getString(i));
                }
//                modelList = appUtils.getConvertedListFromJson(jsonArray, String.class);
                sync.setMappedCompany(modelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addMappedCompany : exp : " + e);
        }
    }

    private void addMappedDistributor(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_MAPPED_DISTRIBUTOR))
            return;
        try {
            if (jsonObj.get(TAG_MAPPED_DISTRIBUTOR) instanceof JSONArray) {
                List<String> modelList;
                JSONArray jsonArray = jsonObj.getJSONArray(TAG_MAPPED_DISTRIBUTOR);
                modelList = new ArrayList<>();
                for(int i = 0 ; i < jsonArray.length(); i++){
                    modelList.add(jsonArray.getString(i));
                }
                sync.setMappedUser(modelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addMappedDistributor : exp : " + e);
        }
    }

    private void addMappedCustomer(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_MAPPED_CUSTOMER))
            return;
        try {

            if (jsonObj.get(TAG_MAPPED_CUSTOMER) instanceof JSONArray) {
                List<String> modelList;
                JSONArray jsonArray = jsonObj.getJSONArray(TAG_MAPPED_CUSTOMER);
                modelList = new ArrayList<>();
                for(int i = 0 ; i < jsonArray.length(); i++){
                    modelList.add(jsonArray.getString(i));
                }
                sync.setMappedCustomer(modelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addMappedCustomer : exp : " + e);
        }
    }

    private void addKeyGenerator(JSONObject jsonObj) {
        if (!isJsonNotNull(jsonObj, TAG_KEY_GENERATOR))
            return;
        try {

            if (jsonObj.get(TAG_KEY_GENERATOR) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_KEY_GENERATOR));
                List<CodeGeneratorModel> codeGeneratorModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                codeGeneratorModelList = appUtils.getConvertedListFromJson(jsonArray, CodeGeneratorModel.class);
                sync.setCodeGeneratorModel(codeGeneratorModelList);
            }

        } catch (JSONException e) {
            Log.d(TAG, "addKeyGenerator : exp : " + e);
        }
    }

    private void addBranchStockList(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_BRANCH_STOCK))
            return;
        try {

            if (jsonObj.get(TAG_BRANCH_STOCK) instanceof String) {

                /*EncodeModel model1 = new EncodeModel();
                model1.setEnkey(TAG_BRANCH_STOCK);
                model1.setEnValue(jsonObj.getString(TAG_BRANCH_STOCK));
                encodeModels.add(model1);
                sync.setEncodedKeyValueList(encodeModels);*/

                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_BRANCH_STOCK));
                List<BranchStockModel> branchStockModels;
                JSONArray jsonArray = new JSONArray(responseString);
                branchStockModels = appUtils.getConvertedListFromJson(jsonArray, BranchStockModel.class);
                sync.setBranchStockList(branchStockModels);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addBranchStockList : exp : " + e);
        }
    }

    private void addDistributorBalanceList(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_DISTRIBUTOR_BALANCE))
            return;

        try {
            if (jsonObj.get(TAG_DISTRIBUTOR_BALANCE) instanceof String) {
                DistBalanceModel distributor;
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_DISTRIBUTOR_BALANCE));
                distributor = new Gson().fromJson(responseString, DistBalanceModel.class);

                sync.setDistBalanceList(distributor);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addDistributorBalanceList : exp : " + e);
        }
    }

    private void addSuggestProductQty(JSONObject jsonObj) throws JSONException {
        if (!isJsonNotNull(jsonObj, TAG_SUGGESTED_ORDER_LIST))
            return;
        if (jsonObj.get(TAG_SUGGESTED_ORDER_LIST) instanceof JSONArray) {
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_SUGGESTED_ORDER_LIST);
            List<ProductsVO> suggestProductList = appUtils.getConvertedListFromJson(jsonArray, ProductsVO.class);
            sync.setSuggestOrderList(suggestProductList);
        }
    }

    private void addDistOpeningStockMaster(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_OPENING_STOCK_MASTER))
            return;

        if (jsonObj.get(TAG_OPENING_STOCK_MASTER) instanceof JSONArray) {

            List<ProductsVO> productsVOList;
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_OPENING_STOCK_MASTER);
            productsVOList = appUtils.getConvertedListFromJson(jsonArray, ProductsVO.class);

            sync.setOpeningStockMaster(productsVOList);
        }

    }

    private void addVanSalesStockLoadingList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_STOCK_LOADING_LIST))
            return;

        if (jsonObj.get(TAG_STOCK_LOADING_LIST) instanceof JSONArray) {

            List<ProductsVO> productsVOList;
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_STOCK_LOADING_LIST);
            productsVOList = appUtils.getConvertedListFromJson(jsonArray, ProductsVO.class);

            sync.setVanSalesStockLoadingList(productsVOList);
        }
    }

    private void addPurchaseInvoice(JSONObject jsonObject) {
        if (!isJsonNotNull(jsonObject, TAG_PURCHASE_INVOICE_LIST))
            return;

        try {
            if (jsonObject.get(TAG_PURCHASE_INVOICE_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObject.getString(TAG_PURCHASE_INVOICE_LIST));
                List<PurchaseInvoiceModel> receiptList;
                JSONArray jsonArray = new JSONArray(responseString);
                receiptList = appUtils.getConvertedListFromJson(jsonArray, PurchaseInvoiceModel.class);
                sync.setPurchaseReceiptDetails(receiptList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addPurchaseInvoice : exp : " + e);
        }
    }

    private void addLogisticMaterialStock(JSONObject jsonObject) {
        if (!isJsonNotNull(jsonObject, TAG_LOGISTIC_MATERIAL_STOCK))
            return;

        try {
            if (jsonObject.get(TAG_LOGISTIC_MATERIAL_STOCK) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObject.getString(TAG_LOGISTIC_MATERIAL_STOCK));
                List<LogisticStockModel> logisticStockModels;
                JSONArray jsonArray = new JSONArray(responseString);
                logisticStockModels = appUtils.getConvertedListFromJson(jsonArray, LogisticStockModel.class);
                sync.setLogisticMaterialStock(logisticStockModels);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addLogisticMaterialStock : exp : " + e);
        }
    }

    private void addMTDFieldWorkingReport(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_MTD_FIELD_WORKING_REPORT))
            return;

        if (jsonObj.get(TAG_MTD_FIELD_WORKING_REPORT) instanceof JSONArray) {

            List<SubStockistModel> stockistModelList;
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_MTD_FIELD_WORKING_REPORT);
            stockistModelList = appUtils.getConvertedListFromJson(jsonArray, SubStockistModel.class);

            sync.setMtdFieldWorkingReport(stockistModelList);
        }
    }

    private void addConfiguration(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_CONFIGURATION))
            return;

        if (jsonObj.get(TAG_CONFIGURATION) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_CONFIGURATION));

            List<ConfigModel> reasonVOList;
            JSONArray jsonArray = new JSONArray(responseString);
            reasonVOList = appUtils.getConvertedListFromJson(jsonArray, ConfigModel.class);

            sync.setConfigMasterList(reasonVOList);
        }

        try {
            if (jsonObj.get(TAG_LOB_MASTER_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_LOB_MASTER_LIST));
                List<LobModel> lobModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                lobModelList = appUtils.getConvertedListFromJson(jsonArray, LobModel.class);
                sync.setLobMasterList(lobModelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addConfiguration : exp : " + e);
        }
    }

    private void addFeedbackReasons(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_FEEDBACK_REASON_LIST))
            return;

        if (jsonObj.get(TAG_FEEDBACK_REASON_LIST) instanceof JSONArray) {

            List<ReasonVO> reasonVOList;
            JSONArray jASalesReturn = jsonObj.getJSONArray(TAG_FEEDBACK_REASON_LIST);

            reasonVOList = appUtils.getConvertedListFromJson(jASalesReturn, ReasonVO.class);

            sync.setFeedbackReasonsList(reasonVOList);
        }

    }


    private void addSchemeCombiProductsList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_SCHEME_COMBI_PRODUCTS_LIST))
            return;

        List<SchemeModel> schemeSlabList;
        if (jsonObj.get(TAG_SCHEME_COMBI_PRODUCTS_LIST) instanceof JSONArray) {
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_SCHEME_COMBI_PRODUCTS_LIST);
            schemeSlabList = appUtils.getConvertedListFromJson(jsonArray, SchemeModel.class);


            sync.setSchemeCombiProductsList(schemeSlabList);
        }

    }

    private void addSchemeSlabList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_SCHEME_SLAB_LIST))
            return;

        List<SchemeModel> schemeSlabList;
        if (jsonObj.get(TAG_SCHEME_SLAB_LIST) instanceof JSONArray) {
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_SCHEME_SLAB_LIST);
            schemeSlabList = appUtils.getConvertedListFromJson(jsonArray, SchemeModel.class);


            sync.setSchemeSlabList(schemeSlabList);
        }

    }

    private void addSchemeSlabProdList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_SCHEME_SLAB_PRODUCT_LIST))
            return;

        List<SchemeModel> schemeSlabProductList;
        if (jsonObj.get(TAG_SCHEME_SLAB_PRODUCT_LIST) instanceof JSONArray) {
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_SCHEME_SLAB_PRODUCT_LIST);
            schemeSlabProductList = appUtils.getConvertedListFromJson(jsonArray, SchemeModel.class);

            sync.setSchemeSlabProductList(schemeSlabProductList);
        }

    }

    private void addPendingBills(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_PENDING_BILL_LIST))
            return;

        if (jsonObj.get(TAG_PENDING_BILL_LIST) instanceof JSONArray) {

            List<PendingBillVO> pendingBillVOList;

            JSONArray jAPendingBillList = jsonObj.getJSONArray(TAG_PENDING_BILL_LIST);
            pendingBillVOList = appUtils.getConvertedListFromJson(jAPendingBillList, PendingBillVO.class);

            sync.setPendingBillList(pendingBillVOList);
        }
    }


    private void addTaxStructureList(JSONObject jsonObj) throws JSONException {
        if (!isJsonNotNull(jsonObj, TAG_TAX_STRUCTURE_LIST))
            return;

        if (jsonObj.get(TAG_TAX_STRUCTURE_LIST) instanceof JSONArray) {

            JSONArray jASurvey = jsonObj.getJSONArray(TAG_TAX_STRUCTURE_LIST);
            List<TaxModel> taxModelList;
            taxModelList = appUtils.getConvertedListFromJson(jASurvey, TaxModel.class);


            sync.setTaxModelArrayList(taxModelList);
        }
    }

    private void addRetailerCategoryList(JSONObject jsonObj) throws JSONException {
        List<RetailerCategoryVO> retailerCategoryList = new ArrayList<>();
        try {
            if (!isJsonNotNull(jsonObj, TAG_RETAILER_CATEGORY))
                return;

            if (jsonObj.get(TAG_RETAILER_CATEGORY) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_RETAILER_CATEGORY));
                JSONArray jArrayRouteList = new JSONArray(responseString);
                retailerCategoryList.addAll(AppUtils.getOurInstance().getConvertedListFromJson(jArrayRouteList, RetailerCategoryVO.class));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        sync.setRetailerCategoryList(retailerCategoryList);
    }

    private void addUomMaster(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_UOM_MASTER))
            return;

        if (jsonObj.get(TAG_UOM_MASTER) instanceof JSONArray) {

            List<UomMasterVO> uomMasterList;
            JSONArray jAUomMaster = jsonObj.getJSONArray(TAG_UOM_MASTER);
            uomMasterList = appUtils.getConvertedListFromJson(jAUomMaster, UomMasterVO.class);
            SFASharedPref pref = SFASharedPref.getOurInstance();
            String salesmanCode = pref.readString(PREF_SALESMANCODE);
            if (uomMasterList != null)
                for (UomMasterVO uomMasterVO : uomMasterList) {
                    uomMasterVO.setSalesmanCode(salesmanCode);

                }

            sync.setUomMasterList(uomMasterList);
        }

    }

    private void addVansalesUomMaster(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_PRODUCTS_UOM_DUMP))
            return;

        if (jsonObj.get(TAG_PRODUCTS_UOM_DUMP) instanceof JSONArray) {

            List<UomMasterVO> uomMasterList;
            JSONArray jAUomMaster = jsonObj.getJSONArray(TAG_PRODUCTS_UOM_DUMP);
            uomMasterList = appUtils.getConvertedListFromJson(jAUomMaster, UomMasterVO.class);
            SFASharedPref pref = SFASharedPref.getOurInstance();
            String salesmanCode = pref.readString(PREF_SALESMANCODE);
            if (uomMasterList != null)
                for (UomMasterVO uomMasterVO : uomMasterList) {
                    uomMasterVO.setSalesmanCode(salesmanCode);

                }

            sync.setUomVansalesMasterList(uomMasterList);
        }

    }

    private void addCategoryList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_CATEGORY_LIST))
            return;

        if (jsonObj.get(TAG_CATEGORY_LIST) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_CATEGORY_LIST));
            JSONArray jAProductSuggestion = new JSONArray(responseString);
            SFADatabase sfaDatabase = SFADatabase.getInstance(context);
            for (int i = 0; i < jAProductSuggestion.length(); i++) {

                JSONObject jsonObject = jAProductSuggestion.getJSONObject(i);
                int sequenceNo = 0;
                String category = "";
                if (isJsonNotNull(jsonObject, TAG_SEQUENCE_NO) &&
                        jsonObject.get(TAG_SEQUENCE_NO) instanceof Integer) {
                    sequenceNo = jsonObject.getInt(TAG_SEQUENCE_NO);

                }
                if (isJsonNotNull(jsonObject, TAG_CATEGORY))
                    category = jsonObject.getString(TAG_CATEGORY);

                sfaDatabase.insertCategorySequence(sequenceNo, category);

            }
        }

    }


    private void addLeaveReasons(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_LEAVE_REASON_LIST))
            return;

        if (jsonObj.get(TAG_LEAVE_REASON_LIST) instanceof JSONArray) {

            List<SalesReturnVO> salesReturnList;
            JSONArray jASalesReturn = jsonObj.getJSONArray(TAG_LEAVE_REASON_LIST);

            salesReturnList = appUtils.getConvertedListFromJson(jASalesReturn, SalesReturnVO.class);

            sync.setLeaveReasonsList(salesReturnList);
        }

    }

    private void addReasons(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_REASONS))
            return;

        if (jsonObj.get(TAG_REASONS) instanceof String) {
            String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_REASONS));
            List<SalesReturnVO> salesReturnList;
            JSONArray jASalesReturn = new JSONArray(responseString);
            salesReturnList = appUtils.getConvertedListFromJson(jASalesReturn, SalesReturnVO.class);

            sync.setReasonsList(salesReturnList);
        }
//        String jsonString = AppUtils.getOurInstance().readTextFileFromLocal("reasonlist.json");
//        try {
//            jsonObj = new JSONObject(jsonString);
//
//            if (jsonObj.get(TAG_REASONS) instanceof String) {
//                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_REASONS));
//                List<SalesReturnVO> models;
//                JSONArray jsonArray = new JSONArray(responseString);
//                models = appUtils.getConvertedListFromJson(jsonArray, SalesReturnVO.class);
//                sync.setReasonsList(models);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void addFocusBrandProductList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_FOCUS_BRAND_PRODUCT_LIST))
            return;

        List<ProductModel> focusBrandProdList;
        if (jsonObj.get(TAG_FOCUS_BRAND_PRODUCT_LIST) instanceof JSONArray) {
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_FOCUS_BRAND_PRODUCT_LIST);
            focusBrandProdList = appUtils.getConvertedListFromJson(jsonArray, ProductModel.class);

            sync.setFocusBrandProductList(focusBrandProdList);

        }
    }

    private void addMustSellSKUProductList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_MUST_SELL_SKU_PRODUCT_LIST))
            return;

        List<ProductModel> mustSellSkuProdList;
        if (jsonObj.get(TAG_MUST_SELL_SKU_PRODUCT_LIST) instanceof JSONArray) {
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_MUST_SELL_SKU_PRODUCT_LIST);
            mustSellSkuProdList = appUtils.getConvertedListFromJson(jsonArray, ProductModel.class);

            sync.setMustSellSKUProductList(mustSellSkuProdList);

        }
    }

    private void addFocusBrandRetailerList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_FOCUS_BRAND_RETAILER_LIST))
            return;

        List<ProductModel> focusBrandRetailerList;
        if (jsonObj.get(TAG_FOCUS_BRAND_RETAILER_LIST) instanceof JSONArray) {
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_FOCUS_BRAND_RETAILER_LIST);

            focusBrandRetailerList = appUtils.getConvertedListFromJson(jsonArray, ProductModel.class);

            sync.setFocusBrandRetailerList(focusBrandRetailerList);

        }
    }

    private void addMustSellSKURetailerList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_MUST_SELL_SKU_RETAILER_LIST))
            return;

        List<ProductModel> mustSellSKURetailerList;
        if (jsonObj.get(TAG_MUST_SELL_SKU_RETAILER_LIST) instanceof JSONArray) {
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_MUST_SELL_SKU_RETAILER_LIST);

            mustSellSKURetailerList = appUtils.getConvertedListFromJson(jsonArray, ProductModel.class);

            sync.setMustSellSKURetailerList(mustSellSKURetailerList);

        }
    }

    private void addAttributeMasterList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_ATTRIBUTE_MASTER_LIST))
            return;

        List<ProductModel> attributeMasterList;
        if (jsonObj.get(TAG_ATTRIBUTE_MASTER_LIST) instanceof JSONArray) {
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_ATTRIBUTE_MASTER_LIST);
            attributeMasterList = appUtils.getConvertedListFromJson(jsonArray, ProductModel.class);

            sync.setAttributeMasterList(attributeMasterList);

        }
    }

    private void addAttributeValuesList(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_ATTRIBUTE_VALUE_LIST))
            return;

        List<ProductModel> attributeValueList;
        if (jsonObj.get(TAG_ATTRIBUTE_VALUE_LIST) instanceof JSONArray) {
            JSONArray jsonArray = jsonObj.getJSONArray(TAG_ATTRIBUTE_VALUE_LIST);
            attributeValueList = appUtils.getConvertedListFromJson(jsonArray, ProductModel.class);

            sync.setAttributeValuesList(attributeValueList);

        }
    }

    private void addGstStateMasterList(JSONObject jsonObj) throws JSONException {

        List<GSTModel> gstModelList = null;
        try {
            if (!isJsonNotNull(jsonObj, TAG_GST_STATE_MASTER_LIST))
                return;

            if (jsonObj.get(TAG_GST_STATE_MASTER_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_GST_STATE_MASTER_LIST));
                JSONArray jArrayRouteList = new JSONArray(responseString);
                gstModelList = AppUtils.getOurInstance().getConvertedListFromJson(jArrayRouteList, GSTModel.class);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        sync.setGstStateMasterList(gstModelList);
    }


    private void addBankNames(JSONObject jsonObj) throws JSONException {


        if (!isJsonNotNull(jsonObj, TAG_BANK_NAMES))
            return;

        if (jsonObj.get(TAG_BANK_NAMES) instanceof JSONArray) {

            List<CollectionVO> bankNamesList;
            JSONArray jABankNames = jsonObj.getJSONArray(TAG_BANK_NAMES);

            bankNamesList = appUtils.getConvertedListFromJson(jABankNames, CollectionVO.class);

            sync.setBankNamesList(bankNamesList);
        }

    }

    private void addRouteList(JSONObject jsonObj) {
        List<RouteModel> routeList = new ArrayList<>();
        try {
            if (!isJsonNotNull(jsonObj, TAG_ROUTE))
                return;

            if (jsonObj.get(TAG_ROUTE) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_ROUTE));
                JSONArray jArrayRouteList = new JSONArray(responseString);
                routeList.addAll(AppUtils.getOurInstance().getConvertedListFromJson(jArrayRouteList, RouteModel.class));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        sync.setRouteList(routeList);
    }

    private void addProducts(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_PRODUCTS))
            return;

        if (jsonObj.get(TAG_PRODUCTS) instanceof JSONArray) {
            List<ProductsVO> productsList = new ArrayList<>();
            List<ProFilterModel> productsFilterList = new ArrayList<>();
            JSONArray jAProducts = jsonObj.getJSONArray(TAG_PRODUCTS);

            try {

                productsList = appUtils.getConvertedListFromJson(jAProducts, ProductsVO.class);

                for (int i = 0; i < productsList.size(); i++) {
                    StringBuilder hierPath = new StringBuilder(productsList.get(i).getProdHierPath());
                    StringBuilder hierName = new StringBuilder(productsList.get(i).getProdHierPathName());

                    String[] strCode = String.valueOf(hierPath).split("/");
                    String[] strName = String.valueOf(hierName).split("/");
                    Collections.reverse(Arrays.asList(strCode));
                    Collections.reverse(Arrays.asList(strName));

                    ProFilterModel model = new ProFilterModel();
                    model.setPathCode(strCode);
                    model.setPathName(strName);
                    productsFilterList.add(model);
                }
                sync.setProFilterModelList(productsFilterList);

            } catch (Exception e) {
                Log.e(TAG, "addProducts: " + e.getMessage(), e);
            }

            sync.setProductsList(productsList);
        }
    }

    private void addVansalesProducts(JSONObject jsonObj) throws JSONException {

        if (!isJsonNotNull(jsonObj, TAG_PRODUCTS_DUMP))
            return;

        if (jsonObj.get(TAG_PRODUCTS_DUMP) instanceof JSONArray) {
            List<ProductsVO> productsList;
            JSONArray jAProducts = jsonObj.getJSONArray(TAG_PRODUCTS_DUMP);
            productsList = appUtils.getConvertedListFromJson(jAProducts, ProductsVO.class);

            sync.setVanSalesProductsList(productsList);
        }
    }

    public static boolean isNewApkAvailable(Context context, JSONObject jsonObj) throws JSONException {

        if (jsonObj != null && (isJsonNotNull(jsonObj, NEW_APP_DOWNLOAD_URL) ||
                isJsonNotNull(jsonObj, NEW_APP_VERSION))) {

            if (BuildConfig.DEBUG) {
                String url = jsonObj.getString(NEW_APP_DOWNLOAD_URL);
                String version = jsonObj.getString(NEW_APP_VERSION);
                Log.d(TAG, "Download URL : " + url + version);
            }
            SharedPreferences.Editor editor = SFASharedPref.getEditor(context);
            editor.putString(PREF_NEWAPPDOWNLOADURL, jsonObj.getString(NEW_APP_DOWNLOAD_URL));
            editor.putString(PREF_NEW_APK_VERSION, jsonObj.getString(NEW_APP_VERSION));
            editor.commit();
            return true;
        }

        return false;

    }

    //SMDMS

    private void addLobMasterList(JSONObject jsonObj) {
        if (!isJsonNotNull(jsonObj, TAG_LOB_MASTER_LIST))
            return;

        try {
            if (jsonObj.get(TAG_LOB_MASTER_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_LOB_MASTER_LIST));
                List<LobModel> lobModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                lobModelList = appUtils.getConvertedListFromJson(jsonArray, LobModel.class);
                sync.setLobMasterList(lobModelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addLobMasterList : exp : " + e);
        }
    }

    private void addDistrLobList(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_DISTR_LOB_LIST))
            return;

        try {

            if (jsonObj.get(TAG_DISTR_LOB_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_DISTR_LOB_LIST));
                List<LobModel> lobModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                lobModelList = appUtils.getConvertedListFromJson(jsonArray, LobModel.class);

                sync.setDistrLobList(lobModelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addDistrLobList : exp : " + e);
        }
    }

    private void addDistributorList(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_DISTRIBUTOR))
            return;

        try {

            if (jsonObj.get(TAG_DISTRIBUTOR) instanceof String) {
                List<Distributor> distributor;
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_DISTRIBUTOR));
                JSONArray jsonArray = new JSONArray(responseString);
                distributor = appUtils.getConvertedListFromJson(jsonArray, Distributor.class);

                sync.setDistributor(distributor);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addDistributorList : exp : " + e);
        }
    }

    private void addDistributorStock(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_DISTRIBUTOR_STOCK))
            return;

        try {

            if (jsonObj.get(TAG_DISTRIBUTOR_STOCK) instanceof String) {
                List<Distributor> distributor;
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_DISTRIBUTOR_STOCK));
                JSONArray jsonArray = new JSONArray(responseString);
                distributor = appUtils.getConvertedListFromJson(jsonArray, Distributor.class);

                sync.setDistributorStock(distributor);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addDistributorList : exp : " + e);
        }
    }

    private void addCustomerStock(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_CUSTOMER_STOCK))
            return;

        try {

            if (jsonObj.get(TAG_CUSTOMER_STOCK) instanceof String) {
                List<CustomerStockModel> customerStockModels;
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_CUSTOMER_STOCK));
                JSONArray jsonArray = new JSONArray(responseString);
                customerStockModels = appUtils.getConvertedListFromJson(jsonArray, CustomerStockModel.class);

                sync.setCustomerStock(customerStockModels);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addCustomerStock : exp : " + e);
        }
    }

    private void addOrderBookingHeader(JSONObject jsonObject) {
        if (!isJsonNotNull(jsonObject, TAG_PREVIOUSE_ORDER_DETAILS))
            return;

        try {

            if (jsonObject.get(TAG_PREVIOUSE_ORDER_DETAILS) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObject.getString(TAG_PREVIOUSE_ORDER_DETAILS));
                List<OrderBookingHeaderModel> models;
                JSONArray jsonArray = new JSONArray(responseString);
                models = appUtils.getConvertedListFromJson(jsonArray, OrderBookingHeaderModel.class);
                sync.setOrderBookingHeaderModels(models);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addOrderBookingHeader : exp : " + e);
        }
    }

    private void addOrderBookingDetails(JSONObject jsonObject) {
        if (!isJsonNotNull(jsonObject, TAG_ORDER_BOOKING_DETAILS_LIST))
            return;

        try {
            if (jsonObject.get(TAG_ORDER_BOOKING_DETAILS_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObject.getString(TAG_ORDER_BOOKING_DETAILS_LIST));
                List<OrderBookingDetailsModel> models;
                JSONArray jsonArray = new JSONArray(responseString);
                models = appUtils.getConvertedListFromJson(jsonArray, OrderBookingDetailsModel.class);
                sync.setOrderBookingDetailsModels(models);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addOrderBookingDetails : exp : " + e);
        }
    }

    private void addBulletinBoardValue(JSONObject jsonObj) {

        try {
            if (!isJsonNotNull(jsonObj, TAG_BULLETIN_BOARD))
                return;

            if (jsonObj.get(TAG_BULLETIN_BOARD) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_BULLETIN_BOARD));
                List<BulletinBoardModel> bulletinBoardModels;
                JSONArray jsonArray = new JSONArray(responseString);
                bulletinBoardModels = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, BulletinBoardModel.class);
                sync.setBulletinBoardModelList(bulletinBoardModels);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addTempCustomerRoute(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_TEMP_CUSTOMER_ROUTE))
                return;

            if (jsonObj.get(TAG_TEMP_CUSTOMER_ROUTE) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_TEMP_CUSTOMER_ROUTE));
                List<RouteModel> retailerVOS;
                JSONArray jsonArray = new JSONArray(responseString);
                retailerVOS = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, RouteModel.class);
                sync.setTempCustomerRoute(retailerVOS);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addTempCustomerShipAddress(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_TEMP_CUSTOMER_SHIP_ADDR))
                return;

            if (jsonObj.get(TAG_TEMP_CUSTOMER_SHIP_ADDR) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_TEMP_CUSTOMER_SHIP_ADDR));
                List<AddressModel> retailerVOS;
                JSONArray jsonArray = new JSONArray(responseString);
                retailerVOS = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, AddressModel.class);
                sync.setTempCustomerShipAddress(retailerVOS);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addTempCustomer(JSONObject jsonObj) {
        try {
            if (!isJsonNotNull(jsonObj, TAG_TEMP_CUSTOMER))
                return;

            if (jsonObj.get(TAG_TEMP_CUSTOMER) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_TEMP_CUSTOMER));
                List<CustomerModel> retailerVOS;
                JSONArray jsonArray = new JSONArray(responseString);
                retailerVOS = AppUtils.getOurInstance().getConvertedListFromJson(jsonArray, CustomerModel.class);
                sync.setTempCustomer(retailerVOS);
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void addTempRoute(JSONObject jsonObj) {
        List<RouteModel> routeList = new ArrayList<>();
        try {
            if (!isJsonNotNull(jsonObj, TAG_TEMP_ROUTE))
                return;

            if (jsonObj.get(TAG_TEMP_ROUTE) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_TEMP_ROUTE));
                JSONArray jArrayRouteList = new JSONArray(responseString);
                routeList.addAll(AppUtils.getOurInstance().getConvertedListFromJson(jArrayRouteList, RouteModel.class));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        sync.setTempRouteList(routeList);
    }

    private void addKeyGeneratorHistory(JSONObject jsonObj) {
        if (!isJsonNotNull(jsonObj, TAG_CODE_GENERATOR_HISTORY_LIST))
            return;
        try {

            if (jsonObj.get(TAG_CODE_GENERATOR_HISTORY_LIST) instanceof String) {
                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_CODE_GENERATOR_HISTORY_LIST));
                List<CodeGeneratorModel> codeGeneratorModelList;
                JSONArray jsonArray = new JSONArray(responseString);
                codeGeneratorModelList = appUtils.getConvertedListFromJson(jsonArray, CodeGeneratorModel.class);
                sync.setCodeGeneratorHistoryModel(codeGeneratorModelList);
            }

        } catch (JSONException e) {
            Log.d(TAG, "addKeyGenerator : exp : " + e);
        }
    }
    private void addfeedbackMaster(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_FEEDBACK_MASTER))
            return;
        try {
            if (jsonObj.get(TAG_FEEDBACK_MASTER) instanceof String) {

                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_FEEDBACK_MASTER));
                List<FeedBackMasterModel> modelList;
                JSONArray jsonArray = new JSONArray(responseString);
                modelList = appUtils.getConvertedListFromJson(jsonArray, FeedBackMasterModel.class);
                sync.setFeedbackMasterModelList(modelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addProductBatchMaster : exp : " + e);
        }
    }
    private void addNotificationTypeMaster(JSONObject jsonObj) {

        if (!isJsonNotNull(jsonObj, TAG_NOTIFICATION_TYPE_MASTER))
            return;
        try {
            if (jsonObj.get(TAG_NOTIFICATION_TYPE_MASTER) instanceof String) {

                String responseString = AppUtils.getOurInstance().decompressGZIP(jsonObj.getString(TAG_NOTIFICATION_TYPE_MASTER));
                List<MessageModel> modelList;
                JSONArray jsonArray = new JSONArray(responseString);
                modelList = appUtils.getConvertedListFromJson(jsonArray, MessageModel.class);
                sync.setNotificationTypeList(modelList);
            }
        } catch (JSONException e) {
            Log.d(TAG, "addProductBatchMaster : exp : " + e);
        }
    }

    /**
     * Response of the {@link AsyncTask}'s which is used in ProductSFA
     */
    public enum AsyncResponse {
        NEW_APK_AVAILABLE,
        USER_ACTIVE,
        NO_COVERAGE_EXISTS,
        DAY_START_ERROR,
        DAY_START_DONE,
        APK_DOWNLOAD_SUCCESS,
        APK_DOWNLOAD_FAILURE,
        UPLOAD_SUCCESS,
        UPLOAD_FAILED,
        SERVER_DATE_MISMATCH,
        AUTH_FAIL,
        INTERNAL_SERVER_ERROR,
        SERVER_UNREACHABLE
    }


}
