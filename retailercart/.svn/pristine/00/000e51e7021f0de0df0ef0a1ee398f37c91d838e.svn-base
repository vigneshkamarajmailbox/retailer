/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.botree.retailerssfa.db.query.CodeGeneratorQueryHelper;
import com.botree.retailerssfa.db.query.CodificationHelper;
import com.botree.retailerssfa.db.query.LobQueryHelper;
import com.botree.retailerssfa.db.query.LoginQueryHelper;
import com.botree.retailerssfa.db.query.OrderBookingSchemeHelper;
import com.botree.retailerssfa.db.query.RetailerDetailHelper;
import com.botree.retailerssfa.db.query.RouteQueryHelper;
import com.botree.retailerssfa.db.query.SyncDBHelper;
import com.botree.retailerssfa.db.query.WDInfrastructureHelper;
import com.botree.retailerssfa.models.AddressModel;
import com.botree.retailerssfa.models.BillingInfoVO;
import com.botree.retailerssfa.models.CodeGeneratorModel;
import com.botree.retailerssfa.models.CollectionVO;
import com.botree.retailerssfa.models.CustomerApprovalModel;
import com.botree.retailerssfa.models.CustomerModel;
import com.botree.retailerssfa.models.DistrReportModel;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.EditQtyModel;
import com.botree.retailerssfa.models.FeedBackMasterModel;
import com.botree.retailerssfa.models.FeedbackModel;
import com.botree.retailerssfa.models.FreeProdModel;
import com.botree.retailerssfa.models.GSTModel;
import com.botree.retailerssfa.models.GeoHierModel;
import com.botree.retailerssfa.models.KycModel;
import com.botree.retailerssfa.models.LobModel;
import com.botree.retailerssfa.models.LoginModel;
import com.botree.retailerssfa.models.LogisticStockModel;
import com.botree.retailerssfa.models.MTDReportModel;
import com.botree.retailerssfa.models.MessageModel;
import com.botree.retailerssfa.models.NewOutletImageList;
import com.botree.retailerssfa.models.OrderBookingDetailsModel;
import com.botree.retailerssfa.models.OrderBookingHeaderModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.ParentMapModel;
import com.botree.retailerssfa.models.PendingBillCollection;
import com.botree.retailerssfa.models.PendingBillVO;
import com.botree.retailerssfa.models.PendingCollection;
import com.botree.retailerssfa.models.PendingVO;
import com.botree.retailerssfa.models.PreviousPOModel;
import com.botree.retailerssfa.models.ProFilterModel;
import com.botree.retailerssfa.models.ProdBatchModel;
import com.botree.retailerssfa.models.ProductMasterModel;
import com.botree.retailerssfa.models.ProductsVO;
import com.botree.retailerssfa.models.PurchaseInvoiceModel;
import com.botree.retailerssfa.models.QuickViewVo;
import com.botree.retailerssfa.models.ReasonVO;
import com.botree.retailerssfa.models.RetailerCategoryVO;
import com.botree.retailerssfa.models.RetailerVO;
import com.botree.retailerssfa.models.RouteModel;
import com.botree.retailerssfa.models.SalesDBCRNoteModel;
import com.botree.retailerssfa.models.SalesHierarchy;
import com.botree.retailerssfa.models.SalesReportModel;
import com.botree.retailerssfa.models.SalesReturnVO;
import com.botree.retailerssfa.models.SalesmanMasterCheckBoxModel;
import com.botree.retailerssfa.models.SalesmanMasterModel;
import com.botree.retailerssfa.models.SalesmanRouteCoverageModel;
import com.botree.retailerssfa.models.SalesmanRouteCoveragePlanModel;
import com.botree.retailerssfa.models.SchemeDistrBudgetModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.models.ScreenConfig;
import com.botree.retailerssfa.models.StockOnHandModel;
import com.botree.retailerssfa.models.StockTakeVO;
import com.botree.retailerssfa.models.StockistModel;
import com.botree.retailerssfa.models.StockledgerReportModel;
import com.botree.retailerssfa.models.Sync;
import com.botree.retailerssfa.models.TaxMasterModel;
import com.botree.retailerssfa.models.TaxModel;
import com.botree.retailerssfa.models.TimeCaptureModel;
import com.botree.retailerssfa.models.UomMasterModel;
import com.botree.retailerssfa.models.UserModel;
import com.botree.retailerssfa.models.WDInfrastructure;
import com.botree.retailerssfa.models.YtdSalesReportModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SFADatabase extends BaseDB {

    private static SFADatabase ourInstance;
    private static DashboardHelper dashboardHelper;

    private SFADatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static SFADatabase getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SFADatabase(context);
        }
        dashboardHelper = new DashboardHelper();
        return ourInstance;
    }

    @NonNull
    public static String queryDeleteTable(String tableName) {
        return "DELETE FROM " + tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users Table and Index Creation
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_MAPPED_COMPANY);
        db.execSQL(CREATE_MAPPED_DISTRIBUTOR);
        db.execSQL(CREATE_MAPPED_CUSTOMER);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CONFIGURATION);
        db.execSQL(CREATE_SCREEN_CONFIGURATION);
        db.execSQL(CREATE_CATEGORY_SEQUENCE);
        db.execSQL(CREATE_FEEDBACK);
        db.execSQL(CREATE_FEEDBACK_MASTER_TABLE);
        db.execSQL(CREATE_ORDER_BOOKING);
        db.execSQL(CREATE_SYNC_PROGRESS);
        db.execSQL(CREATE_UPDATELOCATION);
        db.execSQL(CREATE_ORDERED_APPLIED_SCHEME);
        db.execSQL(CREATE_DISTRIBUTOR_TABLE);
        db.execSQL(CREATE_DISTRIBUTOR_STOCK);
        db.execSQL(CREATE_CUSTOMER_STOCK);
        db.execSQL(CREATE_CODE_GENERATOR);
        db.execSQL(CREATE_CUSTOMER);
        db.execSQL(CREATE_CUSTOMER_SHIP_ADDRESS);
        db.execSQL(CREATE_LOB_MASTER);
        db.execSQL(CREATE_DISTR_LOB);
        db.execSQL(CREATE_LOGIN);
        db.execSQL(CREATE_COMPANY_DETAIL);
        db.execSQL(CREATE_PRODUCT_MASTER);
        db.execSQL(CREATE_PRODUCT_BATCH_MASTER);
        db.execSQL(CREATE_PRODUCT_HIER_LEVEL);
        db.execSQL(CREATE_PRODUCT_HIER_VALUE);
        db.execSQL(CREATE_PRODUCT_UOM_MASTER);
        db.execSQL(CREATE_PRODUCT_TAX_MASTER);
        db.execSQL(CREATE_PREVIOUS_ORDER_BOOKING_REPORT_HEADER);
        db.execSQL(CREATE_PREVIOUS_ORDER_BOOKING_REPORT_DETAILS);
        db.execSQL(CREATE_SCHEME_DEFINITION_LIST);
        db.execSQL(CREATE_CUSTOMER_SCHEME_PRODUCTS_LIST);
        db.execSQL(CREATE_SCHEME_PRODUCTS_LIST);
        db.execSQL(CREATE_SCHEME_SLAB_PRODUCTS_LIST);
        db.execSQL(CREATE_SCHEME_SLAB_LIST);
        db.execSQL(CREATE_LOGIN_TIME_CAPTURE);
        db.execSQL(CREATE_ORDER_PRODUCT_FILTERS);
        db.execSQL(CREATE_MESSAGES);
        db.execSQL(CREATE_NOTIFICATION_TYPE);
        db.execSQL(CREATE_QUICK_ACTION_MENU_TABLE);
        db.execSQL(CREATE_AUTO_QUICK_ACTION_MENU_TABLE);
        db.execSQL(CREATE_BANNER);
        db.execSQL(CREATE_GST_STATE_MASTER);
        db.execSQL(CREATE_SCHEME_COMBI_PRODUCTS);
        db.execSQL(CREATE_BRANCH_STOCK);
        db.execSQL(CREATE_PREVIOUS_ORDER_BOOKING_STATUS);

        //Index tables
        db.execSQL(CREATE_INDEX_USER_1);
        db.execSQL(CREATE_INDEX_CATEGORYSEQUENCE);
        db.execSQL(CREATE_INDEX_ORDERBOOKING);
        db.execSQL(CREATE_INDEX_PRODUCT_MASTER);
        db.execSQL(CREATE_INDEX_PRODUCT_BATCH_MASTER);
        db.execSQL(CREATE_INDEX_PRODUCT_TAX_MASTER);
        db.execSQL(CREATE_INDEX_PRODUCT_UOM_MASTER);
        db.execSQL(CREATE_INDEX_DEFINITION_LIST_1);


//        db.execSQL(CREATE_STOCKIST_TABLE);
//        db.execSQL(CREATE_OUTLET_VISIT_SEQUENCE);
//        db.execSQL(CREATE_ROUTE);
//        db.execSQL(CREATE_RETAILER);
//        db.execSQL(CREATE_PENDING_BILL);
//        db.execSQL(CREATE_PRODUCTS);
//        db.execSQL(CREATE_VANSALES_PRODUCTS);
//        db.execSQL(CREATE_PRODUCT_SUGGESTIONS);
//        db.execSQL(CREATE_PRODUCT_CATEGORY);
//
//        // Create Collection Table
//        db.execSQL(CREATE_CASH_COLLECTION);
//        db.execSQL(CREATE_CHEQUE_COLLECTION);
//
//        db.execSQL(CREATE_ORDER_CONFIRMATION);
//        db.execSQL(CREATE_PURCHASE_RECEIPT_CONFIRMATION);
//        db.execSQL(CREATE_STOCK_TAKE);
//        db.execSQL(CREATE_SALES_RETURN);
//        db.execSQL(CREATE_REASONS);
//        db.execSQL(CREATE_LEAVE_REASONS);
//        db.execSQL(CREATE_FEEDBACK_REASON);
//
//        // Create Front End Purpose Table
//        db.execSQL(CREATE_RETAILER_VISIT);
//        // Create Salesmanv Tracker Table
//        db.execSQL(CREATE_SALES_MAN_TRACKER);
//
//        // Create New Retailer Table
//        db.execSQL(CREATE_NEW_RETAILER);
//        // Create Customer Upload Status Table
//        db.execSQL(CREATE_CUSTOMER_UPLOAD_STATUS);
//
//        // Create Retailer Category Table
//        db.execSQL(CREATE_RETAILER_CATEGORY);
//        db.execSQL(CREATE_RETAILER_CHANNELS);
//        db.execSQL(CREATE_RETAILER_SUB_CHANNELS);
//        db.execSQL(CREATE_RETAILER_GROUPS);
//        db.execSQL(CREATE_RETAILER_CLASSES);
//
//        // Create Bank Names Table
//        db.execSQL(CREATE_BANK_NAMES);
//        db.execSQL(CREATE_RETAILER_CATEGORY_ID);
//
//        db.execSQL(CREATE_ORDER_BOOKING_TRACKER);
//        db.execSQL(CREATE_UOM_MASTER);
//        db.execSQL(CREATE_VANSALES_UOM_MASTER);
//
//        // Create Image Table
//        db.execSQL(CREATE_IMAGE);
//
//        db.execSQL(CREATE_PRODUCT_TAGGING_TABLE);
//
//        db.execSQL(CREATE_CONFIRM_OREDR_APPLIED_SCHEME);
//
//        //Display products
//        db.execSQL(CREATE_BILLING);
//        db.execSQL(CREATE_BILLING_APPLIED_SCHEME);
//

//
//        // Report table created
//        db.execSQL(CREATE_PURCHASE_ORDER_REPORT);
//        db.execSQL(CREATE_PURCHASE_RECEIPT_REPORT);
//        db.execSQL(CREATE_DISTRIBUTOR_STOCK_REPORT);
//
//        db.execSQL(CREATE_LOADING_STOCK);
//        db.execSQL(CREATE_PURCHASE_ORDER_BOOKING);
//        //Billing tracker
//        db.execSQL(CREATE_BILLING_TRACKER);
//
//        db.execSQL(CREATE_BILLING_COLLECTION);
//        db.execSQL(CREATE_PREVIOUS_ORDER_BOOKING);
//        db.execSQL(CREATE_REATILER_PREVIOUS_ORDER_INVOICES);
//
//        //Tax Structure
//        db.execSQL(CREATE_TAX_STRUCTURE);
//
//        // must sell and focus brand product tables
//        db.execSQL(CREATE_MUSTSELL_RETAILERS);
//        db.execSQL(CREATE_MUSTSELL_SKU_PRODUCTS);
//        db.execSQL(CREATE_FOCUSBRAND_RETAILERS);
//        db.execSQL(CREATE_FOCUSBRAND_PRODUCTS);
//
//        db.execSQL(CREATE_ATTRIBUTE_MASTER);
//        db.execSQL(CREATE_ATTRIBUTE_VALUES);
//        db.execSQL(CREATE_OTHER_ATTRIBUTES);
//
//        db.execSQL(CREATE_PURCHASE_PARTIAL_RETURN);
//        db.execSQL(CREATE_PURCHASE_FULL_RETURN);
//        db.execSQL(CREATE_SALES_HIERARCHY);
//        db.execSQL(CREATE_SALES_HIERARCHY_VALUE);
//        db.execSQL(CREATE_OPENING_STOCK);
//        db.execSQL(CREATE_FIELD_ACTIVITY_REPORT);
//        db.execSQL(CREATE_OPENING_STOCK_MASTER);
//
//        // Index
//        db.execSQL(CREATE_INDEX_RETAILER_1);
//        db.execSQL(CREATE_INDEX_RETAILER_2);
//        db.execSQL(CREATE_INDEX_RETAILER_3);
//
//        db.execSQL(CREATE_INDEX_PRODUCTS);
//        db.execSQL(CREATE_INDEX_VAN_SALES_PRODUCTS);
//        db.execSQL(CREATE_INDEX_PRODUCT_UOM);
//        db.execSQL(CREATE_INDEX_PRODUCT_UOM_1);
//        db.execSQL(CREATE_INDEX_RETAILER_CATEGORY_ID);
//        db.execSQL(CREATE_INDEX_VAN_SALES_UOM);
//        db.execSQL(CREATE_INDEX_PRODUCTSUGGESTIONS);
//        db.execSQL(CREATE_INDEX_PRODUCTCATEGORY);
//
//        db.execSQL(CREATE_INDEX_CASHCOLLECTION_1);
//        db.execSQL(CREATE_INDEX_CHEQUECOLLECTION_1);
//        db.execSQL(CREATE_INDEX_STOCKTAKE);
//        db.execSQL(CREATE_INDEX_SALESRETURN);
//        db.execSQL(CREATE_INDEX_REASONS_1);
//
//        db.execSQL(CREATE_INDEX_RETAILERVISIT_1);
//
//        db.execSQL(CREATE_INDEX_PURCHASE_ORDER_BOOKING_1);
//        db.execSQL(CREATE_INDEX_PURCHASE_RETURN);
//
//        db.execSQL(CREATE_INDEX_RETAILER_4);
//        db.execSQL(CREATE_INDEX_FOCUSBRAND_RETAILERS_1);
//        db.execSQL(CREATE_STOCK_LEDGER_REPORT);
//
//
//        db.execSQL(CREATE_VAN_SALES_PRODUCT_FILTERS);
//        db.execSQL(CREATE_ROUTE_SCHEME_ZIP_LIST);
//        db.execSQL(CREATE_RETAILER_SCHEME_PRODUCTS_LIST);
//        db.execSQL(CREATE_NEW_RETAILER_IMAGE);
//
//        //SMDMS
//
//
//
//        db.execSQL(CREATE_WD_STORAGE);
//        db.execSQL(CREATE_WD_FREEZER);
//
//        db.execSQL(CREATE_CUSTOMER_ROUTE);
//        db.execSQL(CREATE_CUSTOMER_BANK);
//        db.execSQL(CREATE_SALESMAN_MASTER);
//        db.execSQL(CREATE_SALESMAN_LOB_MAPPING);
//        db.execSQL(CREATE_SALESMAN_ROUTE_MAPPING);
//        db.execSQL(CREATE_SALESMAN_KYC);
//        db.execSQL(CREATE_DISTRIBUTOR_BALANCE);
//        db.execSQL(CREATE_BRANCH_STOCK);
//        db.execSQL(CREATE_KYC_MASTER);
//        db.execSQL(CREATE_GEO_HIER_LEVEL);
//        db.execSQL(CREATE_GEO_HIER_VALUE);
//        db.execSQL(CREATE_STOCK_ON_HAND);
//        db.execSQL(CREATE_SALESMAN_ROUTE_COVERAGE_PLAN);
//        db.execSQL(CREATE_SALESMAN_ROUTE_COVERAGE);
//
//        //Purchase order confirmation report table
//        db.execSQL(CREATE_SPREVIOUS_PURCHASE_ORDERS);
//        db.execSQL(CREATE_KEY_VALUE_ENCODE);
//        db.execSQL(CREATE_ROUTE_SEQUENCING);
//
//        db.execSQL(CREATE_BILL_ADJUSTMENT);
//        db.execSQL(CREATE_SUPPLIER);
//        db.execSQL(CREATE_PURCHASE_INVOICE);
//        db.execSQL(CREATE_LOGISTIC_MATERIAL_STOCK);
//        db.execSQL(CREATE_BILL_INVOICE_HEADER);
//        db.execSQL(CREATE_BILL_INVOICE_DETAILS);
////        db.execSQL(CREATE_PREVIOUS_ORDER_BOOKING_REPORT_SCHEMES);
//        db.execSQL(CREATE_RATE_EDIT);
//        db.execSQL(CREATE_CUSTOMER_PRODUCT_MAPPING);
//        db.execSQL(CREATE_CONTRACT_PRICING);
//        db.execSQL(CREATE_COLLECTION);
//        db.execSQL(CREATE_PENDING_BILLS);
//        db.execSQL(CREATE_BILL_INVOICE_SAVED_DETAILS);
//        db.execSQL(CREATE_SALES_DB_CRNOTE);
//        db.execSQL(CREATE_STOCK_ADJ_HEADER);
//        db.execSQL(CREATE_STOCK_ADJ_DETAILS);
//        db.execSQL(CREATE_SALES_RETURN_HEADER);
//        db.execSQL(CREATE_SALES_RETURN_DETAILS);
////      Scheme Tables
//
//        db.execSQL(CREATE_SCHEME_RETAILER_CATRGORY_LIST);
//        db.execSQL(CREATE_SCHEME_PRODUCTS_CATRGORY_LIST);
//        db.execSQL(CREATE_SCHEME_CUSTOMER_LIST);
//        db.execSQL(CREATE_SCHEME_ATTRIBUTES);
//
//        db.execSQL(CREATE_SCHEME_DISTRIBUTOR_BUDGET);
//        db.execSQL(CREATE_SCHEME_MASTER_CONTROL);
//        db.execSQL(CREATE_SCHEME_ZIP_LIST);
//        db.execSQL(CREATE_INVOICE_CR_DB_ADJUSTMENT);
//        db.execSQL(CREATE_DISTR_REPORT);
//        db.execSQL(CREATE_SALES_REPORT_DETAIL);
//        db.execSQL(CREATE_SALES_DAY_WISE_REPORT_DETAIL);
//        db.execSQL(CREATE_SALES_WEEK_WISE_REPORT_DETAIL);
//        db.execSQL(CREATE_SALES_CUSTOMER_WISE_REPORT_DETAIL);
//        db.execSQL(CREATE_SALES_CHANNEL_WISE_REPORT_DETAIL);
//        db.execSQL(CREATE_PURCHASE_INVOICE_REPORT_DETAIL);
//        db.execSQL(CREATE_SALES_SALESMAN_WISE_REPORT_DETAIL);
//        db.execSQL(CREATE_SALES_ROUTE_WISE_REPORT_DETAIL);
//        db.execSQL(CREATE_YTD_ROUTE_WISE_REPORT);
//        db.execSQL(CREATE_YTD_SALESMAN_WISE_REPORT);
//        db.execSQL(CREATE_YTD_PRODUCT_WISE_REPORT);
//        db.execSQL(CREATE_YTD_CUSTOMER_WISE_REPORT);
//        db.execSQL(CREATE_MTD_SALES_PRODHIER_PRODUCT_WISE_REPORT);
//        db.execSQL(CREATE_REPORT_PROCESS_MAPPING);
//        db.execSQL(CREATE_REPORT_FIELD_MAPPING);
//        db.execSQL(CREATE_BULLETIN_BOARD);
//        db.execSQL(CREATE_CUSTOMER_APPROVAL_DATA);
//        db.execSQL(CREATE_TEMP_ROUTE);
//        db.execSQL(CREATE_TEMP_CUSTOMER);
//        db.execSQL(CREATE_TEMP_CUSTOMER_SHIP_ADDRESS);
//        db.execSQL(CREATE_TEMP_CUSTOMER_ROUTE);
//        db.execSQL(CREATE_BATCH_TRANSFER_DETAILS);
//        db.execSQL(CREATE_CODE_GENERATOR_HISTORY);
//
//        //smdms index table
//        db.execSQL(CREATE_INDEX_STOCK_ON_HAND);
//        db.execSQL(CREATE_INDEX_BRANCH_STOCK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            /*try {
                db.execSQL(" ALTER TABLE " + TABLE_NEW_RETAILER + " ADD COLUMN " + COLUMN_CMP_CUSTOMER_CODE + " TEXT "); // Upgrade Column in 2.3.1

            } catch (Exception e) {
                Log.e("SFADB", "onUpgrade: " + e.getMessage(), e);
            }*/
        }
    }

    private void dropExistingTableAndCreate(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {
            db.execSQL(DROP_TABLE + TABLE_SYNC_PROGRESS);
            db.execSQL(DROP_TABLE + TABLE_STOCKIST);
            db.execSQL(DROP_TABLE + TABLE_SCREEN_CONFIGURATION);
            db.execSQL(DROP_TABLE + TABLE_CONFIGURATION);
            db.execSQL(DROP_TABLE + TABLE_OUTLET_VISIT_SEQUENCE);
            db.execSQL(DROP_TABLE + TABLE_ROUTE);
            db.execSQL(DROP_TABLE + TABLE_RETAILER);
            db.execSQL(DROP_TABLE + TABLE_PENDING_BILL);
            db.execSQL(DROP_TABLE + TABLE_PRODUCTS);
            db.execSQL(DROP_TABLE + TABLE_VANSALES_PRODUCTS);
            db.execSQL(DROP_TABLE + TABLE_PRODUCT_SUGGESTIONS);
            db.execSQL(DROP_TABLE + TABLE_CATEGORY_SEQUENCE);
            db.execSQL(DROP_TABLE + TABLE_PRODUCT_CATEGORY);
            db.execSQL(DROP_TABLE + TABLE_CASH_COLLECTION);
            db.execSQL(DROP_TABLE + TABLE_CHEQUE_COLLECTION);
            db.execSQL(DROP_TABLE + TABLE_ORDER_BOOKING);
            db.execSQL(DROP_TABLE + TABLE_ORDER_CONFIRMATION);
            db.execSQL(DROP_TABLE + TABLE_PURCHASE_RECEIPT_CONFIRMATION);
            db.execSQL(DROP_TABLE + TABLE_PREVIOUS_ORDER_BOOKED_PROD_DETAILS);
            db.execSQL(DROP_TABLE + TABLE_REATILER_PREVIOUS_ORDER_INVOICES);
            db.execSQL(DROP_TABLE + TABLE_TAX_STRUCTURE);
            db.execSQL(DROP_TABLE + TABLE_STOCK_TAKE);
            db.execSQL(DROP_TABLE + TABLE_SALES_RETURN);
            db.execSQL(DROP_TABLE + TABLE_PURCHASE_PARTIAL_RETURN);
            db.execSQL(DROP_TABLE + TABLE_PURCHASE_FULL_RETURN);
            db.execSQL(DROP_TABLE + TABLE_REASONS);
            db.execSQL(DROP_TABLE + TABLE_LEAVE_REASONS);
            db.execSQL(DROP_TABLE + TABLE_RETAILER_VISIT);
            db.execSQL(DROP_TABLE + TABLE_SALESMAN_TRACKER);
            db.execSQL(DROP_TABLE + TABLE_NEW_RETAILER);
            db.execSQL(DROP_TABLE + TABLE_CUSTOMER_UPLOAD_STATUS);
            db.execSQL(DROP_TABLE + TABLE_RETAILER_CATEGORY);
            db.execSQL(DROP_TABLE + TABLE_RETAILER_CHANNELS);
            db.execSQL(DROP_TABLE + TABLE_RETAILER_SUBCHANNELS);
            db.execSQL(DROP_TABLE + TABLE_RETAILER_GROUPS);
            db.execSQL(DROP_TABLE + TABLE_RETAILER_CLASSES);
            db.execSQL(DROP_TABLE + TABLE_MUSTSELL_SKU_PRODUCTS);
            db.execSQL(DROP_TABLE + TABLE_MUSTSELL_RETAILERS);
            db.execSQL(DROP_TABLE + TABLE_FOCUSBRAND_PRODUCTS);
            db.execSQL(DROP_TABLE + TABLE_FOCUSBRAND_RETAILERS);
            db.execSQL(DROP_TABLE + TABLE_GST_STATE_MASTER);
            db.execSQL(DROP_TABLE + TABLE_ATTRIBUTE_MASTER);
            db.execSQL(DROP_TABLE + TABLE_ATTRIBUTE_VALUES);
            db.execSQL(DROP_TABLE + TABLE_OTHER_ATTRIBUTES);
            db.execSQL(DROP_TABLE + TABLE_BANK_NAMES);
            db.execSQL(DROP_TABLE + TABLE_SCHEME_PRODUCT_LIST);
            db.execSQL(DROP_TABLE + TABLE_SCHEME_SLAB_PRODUCT_LIST);
            db.execSQL(DROP_TABLE + TABLE_SCHEME_CUSTOMER_LIST);
            db.execSQL(DROP_TABLE + TABLE_SCHEME_DEFINITION_LIST);
            db.execSQL(DROP_TABLE + TABLE_SCHEME_RETAILER_CATRGORY_LIST);
            db.execSQL(DROP_TABLE + TABLE_SCHEME_PRODUCT_CATRGORY_LIST);
            db.execSQL(DROP_TABLE + TABLE_SCHEME_SLAB_LIST);
            db.execSQL(DROP_TABLE + TABLE_RETAILER_CATEGORY_ID);
            db.execSQL(DROP_TABLE + TABLE_SCHEME_COMBI_PRODUCTS);
            db.execSQL(DROP_TABLE + TABLE_SCHEME_ATTRIBUTES);
            db.execSQL(DROP_TABLE + TABLE_ORDERED_APPLIED_SCHEME);
            db.execSQL(DROP_TABLE + TABLE_BILLING_APPLIED_SCHEME);
            db.execSQL(DROP_TABLE + TABLE_CONFIRM_ORDER_APPLIED_SCHEME);
            db.execSQL(DROP_TABLE + TABLE_ORDER_BOOKING_TRACKER);
            db.execSQL(DROP_TABLE + TABLE_BILLING_TRACKER);
            db.execSQL(DROP_TABLE + TABLE_IMAGE);
            db.execSQL(DROP_TABLE + TABLE_UOM_MASTER);
            db.execSQL(DROP_TABLE + TABLE_VANSALES_UOM_MASTER);
            db.execSQL(DROP_TABLE + TABLE_UPDATE_LOCATION);
            db.execSQL(DROP_TABLE + TABLE_BILLING);
            db.execSQL(DROP_TABLE + TABLE_BILLING_COLLECTION);
            db.execSQL(DROP_TABLE + TABLE_PURCHASE_ORDER_REPORT);
            db.execSQL(DROP_TABLE + TABLE_PURCHASE_RECEIPT_REPORT);
            db.execSQL(DROP_TABLE + TABLE_DISTRIBUTOR_STOCK_REPORT);
            db.execSQL(DROP_TABLE + TABLE_QUICK_ACTION_MENUS);
            db.execSQL(DROP_TABLE + TABLE_AUTO_QUICK_ACTION_MENUS);
            db.execSQL(DROP_TABLE + TABLE_LOADING_STOCK);
            db.execSQL(DROP_TABLE + TABLE_OPENING_STOCK);
            db.execSQL(DROP_TABLE + TABLE_PURCHASE_ORDER_BOOKING);
            db.execSQL(DROP_TABLE + TABLE_FEEDBACK_REASON);
            db.execSQL(DROP_TABLE + PRODUCT_TAGGING_TABLENAME);
            db.execSQL(DROP_TABLE + TABLE_MESSAGES);
            db.execSQL(DROP_TABLE + TABLE_SALES_HIERARY);
            db.execSQL(DROP_TABLE + TABLE_SALES_HIERARY_VALUE);
            db.execSQL(DROP_TABLE + TABLE_FIELD_ACTIVITY_REPORT);
            db.execSQL(DROP_TABLE + TABLE_OPENING_STOCK_MASTER);
            db.execSQL(DROP_TABLE + TABLE_BANNER);

            db.execSQL(DROP_TABLE + TABLE_ROUTE_SCHEME_ZIP_LIST);
            db.execSQL(DROP_TABLE + TABLE_RETAILER_SCHEME_PRODUCTS_LIST);
            db.execSQL(DROP_TABLE + TABLE_NEW_RETAILER_IMAGES);
            db.execSQL(DROP_TABLE + TABLE_SALESMAN_MASTER);
            db.execSQL(DROP_TABLE + TABLE_SALESMAN_LOB_MAPPING);
            db.execSQL(DROP_TABLE + TABLE_SALESMAN_ROUTE_MAPPING);
            db.execSQL(DROP_TABLE + TABLE_SALESMAN_MASTER_KYC);

            db.execSQL(DROP_TABLE + TABLE_WD_STORAGE);
            db.execSQL(DROP_TABLE + TABLE_WD_FREEZER);

            db.execSQL(DROP_TABLE + TABLE_CUSTOMER);
            db.execSQL(DROP_TABLE + TABLE_CUSTOMER_SHIP_ADDRESS);
            db.execSQL(DROP_TABLE + TABLE_CUSTOMER_ROUTE);
            db.execSQL(DROP_TABLE + TABLE_CUSTOMER_BANK);
            db.execSQL(DROP_TABLE + TABLE_KYC_MASTER);
            db.execSQL(DROP_TABLE + TABLE_DISTRIBUTOR_BALANCE);
            db.execSQL(DROP_TABLE + TABLE_BRANCH_STOCK);
            db.execSQL(DROP_TABLE + TABLE_COMPANY_DETAIL);
            db.execSQL(DROP_TABLE + TABLE_PRODUCT_MASTER);

            db.execSQL(DROP_TABLE + TABLE_GEO_HIER_LEVEL);
            db.execSQL(DROP_TABLE + TABLE_GEO_HIER_VALUE);
            db.execSQL(DROP_TABLE + TABLE_STOCK_ON_HAND);
            db.execSQL(DROP_TABLE + TABLE_PREVIOUS_PURCHASE_ORDERS);
            db.execSQL(DROP_TABLE + TABLE_KEY_VALUE_ENCODE);

            db.execSQL(DROP_TABLE + TABLE_PURCHASE_INVOICE);
            db.execSQL(DROP_TABLE + TABLE_PENDING_BILLS);
            db.execSQL(DROP_TABLE + TABLE_COLLECTION);
            db.execSQL(DROP_TABLE + TABLE_SALES_DB_CRNOTE);
            db.execSQL(DROP_TABLE + TABLE_STOCK_ADJ_HEADER);
            db.execSQL(DROP_TABLE + TABLE_STOCK_ADJ_DETAILS);
            db.execSQL(DROP_TABLE + TABLE_SALES_RETURN_HEADER);
            db.execSQL(DROP_TABLE + TABLE_SALES_RETURN_DETAILS);
            db.execSQL(DROP_TABLE + TABLE_DISTR_REPORT);
            db.execSQL(DROP_TABLE + TABLE_SALES_REPORT_DETAIL);
            db.execSQL(DROP_TABLE + TABLE_SALES_DAY_WISE_REPORT_DETAIL);
            db.execSQL(DROP_TABLE + TABLE_SALES_WEEK_WISE_REPORT_DETAIL);
            db.execSQL(DROP_TABLE + TABLE_SALES_CUSTOMER_WISE_REPORT_DETAIL);
            db.execSQL(DROP_TABLE + TABLE_SALES_CHANNEL_WISE_REPORT_DETAIL);
            db.execSQL(DROP_TABLE + TABLE_SALES_SALESMAN_WISE_REPORT_DETAIL);
            db.execSQL(DROP_TABLE + TABLE_SALES_ROUTE_WISE_REPORT_DETAIL);

            db.execSQL(DROP_TABLE + TABLE_INVOICE_CR_DB_ADJUSTMENT);
            db.execSQL(DROP_TABLE + TABLE_SCHEME_DISTRIBUTOR_BUDGET);
            db.execSQL(DROP_TABLE + TABLE_CONTRACT_PRICING);
            db.execSQL(DROP_TABLE + TABLE_CUSTOMER_PRODUCT_MAPPING);
            db.execSQL(DROP_TABLE + TABLE_RATE_EDIT);
            db.execSQL(DROP_TABLE + TABLE_BILL_INVOICE_SAVED_DETAILS);
            db.execSQL(DROP_TABLE + TABLE_BILL_INVOICE_DETAILS);
            db.execSQL(DROP_TABLE + TABLE_BILL_INVOIC_HEADER);
            db.execSQL(DROP_TABLE + TABLE_LOGISTIC_MATERIAL_STOCK);
            db.execSQL(DROP_TABLE + TABLE_BILL_ADJUSTMENT);
            db.execSQL(DROP_TABLE + TABLE_SUPPLIER);
            db.execSQL(DROP_TABLE + TABLE_ROUTE_SEQUENCING);
            db.execSQL(DROP_TABLE + TABLE_SALESMAN_ROUTE_COVERAGE);
            db.execSQL(DROP_TABLE + TABLE_SALESMAN_ROUTE_COVERAGE_PLAN);
            db.execSQL(DROP_TABLE + TABLE_PRODUCT_HIER_VALUE);
            db.execSQL(DROP_TABLE + TABLE_PRODUCT_HIER_LEVEL);
            db.execSQL(DROP_TABLE + TABLE_PRODUCT_TAX_MASTER);
            db.execSQL(DROP_TABLE + TABLE_PRODUCT_UOM_MASTER);
            db.execSQL(DROP_TABLE + TABLE_PRODUCT_BATCH_MASTER);
            db.execSQL(DROP_TABLE + TABLE_DISTRIBUTOR);
            db.execSQL(DROP_TABLE + TABLE_DISTR_LOB);
            db.execSQL(DROP_TABLE + TABLE_LOB);
            db.execSQL(DROP_TABLE + TABLE_ORDER_PRODUCT_FILTERS);
            db.execSQL(DROP_TABLE + TABLE_VAN_SALES_PRODUCT_FILTERS);
            db.execSQL(DROP_TABLE + TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST);
            db.execSQL(DROP_TABLE + TABLE_SCHEME_ZIP_LIST);
            db.execSQL(DROP_TABLE + TABLE_ORDER_BOOKING_REPORT_DETAILS);
            db.execSQL(DROP_TABLE + TABLE_ORDER_BOOKING_REPORT_HEADER);
            db.execSQL(DROP_TABLE + TABLE_STOCK_LEDGER_REPORT);

            db.execSQL(DROP_TABLE + TABLE_PURCHASE_INVOICE_REPORT_DETAIL);
            db.execSQL(DROP_TABLE + TABLE_YTD_ROUTE_WISE_REPORT);
            db.execSQL(DROP_TABLE + TABLE_YTD_SALESMAN_WISE_REPORT);
            db.execSQL(DROP_TABLE + TABLE_YTD_PRODUCT_WISE_REPORT);
            db.execSQL(DROP_TABLE + TABLE_YTD_CUSTOMER_WISE_REPORT);
            db.execSQL(DROP_TABLE + TABLE_MTD_SALES_PRODHIER_PRODUCT_WISE_REPORT);
            db.execSQL(DROP_TABLE + TABLE_REPORT_PROCESS_MAPPING);
            db.execSQL(DROP_TABLE + TABLE_REPORT_FIELD_MAPPING);
            db.execSQL(DROP_TABLE + TABLE_BULLETIN_BOARD);
            db.execSQL(DROP_TABLE + TABLE_CUSTOMER_APPROVAL_DATA);
            db.execSQL(DROP_TABLE + TABLE_TEMP_CUSTOMER);
            db.execSQL(DROP_TABLE + TABLE_TEMP_CUSTOMER_ROUTE);
            db.execSQL(DROP_TABLE + TABLE_TEMP_CUSTOMER_SHIP_ADDRESS);
            db.execSQL(DROP_TABLE + TABLE_TEMP_ROUTE);
            db.execSQL(DROP_TABLE + TABLE_CODE_GENERATOR_HISTORY);
            db.execSQL(DROP_TABLE + TABLE_BATCH_TRANSFER_DETAILS);
            onCreate(db);
        }
    }

    public void insertUser(UserModel userModel) {
        new UserDbHelper().insertUser(userModel, this);
    }

    public void insertUserLoginTime(UserModel userModel, String onlineOrOffline, String processName, String status, String currentTime) {
        new UserDbHelper().insertLoginTime(userModel, onlineOrOffline, processName, this, status, currentTime);

    }

    public void insertSalesHierarchyData(List<SalesHierarchy> salesHierarchies) {
        new UserDbHelper().insertSalesHierarchyData(this, salesHierarchies);

    }

    public void insertStockist(UserModel userModel) {
        new UserDbHelper().insertStockistList(this, userModel);
    }

    public void deleteUser() {
        new UserDbHelper().deleteUser(this);
    }

    public void deleteStockist() {
        new UserDbHelper().deleteStockist(this);
    }

    public void insertScreenConfig(List<ScreenConfig> screenConfigList) {
        new UserDbHelper().insertScreenConfig(screenConfigList, this);
    }

    public void insertSalesHierarchyValue(List<SalesHierarchy> salesHierarchies) {
        new UserDbHelper().insertSalesHierarchyValue(this, salesHierarchies);
    }

    public void deleteScreenConfiguration() {
        new UserDbHelper().deleteScreenConfiguration(this);
    }

    public void deleteSalesHierachyData() {
        new UserDbHelper().deleteSalesHierachyData(this);
    }

    public void deleteSalesHierachyValue() {
        new UserDbHelper().deleteSalesHierachyValue(this);
    }

    public List<UserModel> getUserDetail(String userCode) {
        return new UserDbHelper().getUserDetail(this, userCode);
    }

    public List<UserModel> getUserLoginAccess(String username, String pass) {
        return new UserDbHelper().userLoginSuccess(this, username, pass);
    }

    public int getUserCountBasedOnId(String userName) {
        return new LoginQueryHelper().getUserCountBasedOnId(this, userName);
    }

    public Boolean istUserAvailable() {
        return new UserDbHelper().istUserAvailable(this);
    }

    public boolean isTransctionAvailable(String distrcode, String salesmanCode) {
        return new UserDbHelper().isTransctionAvailableInDB(this, distrcode, salesmanCode);
    }

    public void insertSyncData(Sync sync, IDbCallback<Boolean> callback) {
        new SyncDBHelper().syncDataInsert(sync, this, callback);
    }

    public void insertSyncReportData(Sync sync, IDbCallback<Boolean> callback) {
        new SyncDBHelper().insertSyncReportData(sync, this, callback);
    }

    public void insertCategorySequence(Integer sequenceNoCaps, String categoryCaps) {
        new SyncDBHelper().insertCategorySequence(this, sequenceNoCaps, categoryCaps);
    }

    public void removeExistingTableInfo(String distrCode, String cmpCode,
                                        boolean deleteOnlyMaster) {
        new SyncDBHelper().removeExistingTableInfo(this, distrCode, cmpCode, deleteOnlyMaster);
    }

    public void removeExistingTableData() {
        new SyncDBHelper().removeExistingTableData(this);
    }

    public void removeExistingReportTableInfo() {
        new SyncDBHelper().removeExistingReportTableInfo(this);
    }

    public boolean isDataAvailable(String distrCode, String today) {
        return new SyncDBHelper().isDataAvailable(this, distrCode, today);
    }

    public boolean checkDayStartPerformed(String distrCode, String today) {
        return new SyncDBHelper().checkDayStartPerformed(this, distrCode, today);
    }

    public boolean isSyncDataAvailable(String distrCode) {
        return new SyncDBHelper().isSyncDataAvailable(this, distrCode);
    }

    public boolean checkPreviousDayCloseFail(String distrCode, String yesterday) {
        return new SyncDBHelper().checkPreviousDayCloseFail(this, distrCode, yesterday);
    }

    public void deleteSyncTableData() {
        new SyncDBHelper().deleteSyncTableData(this);
    }

    public void insertSyncProgress(String date, String dayStart, String upload, String dayClose) {

        new SyncDBHelper().insertSyncProgress(this, date, dayStart, upload, dayClose);
    }

    public void deleteSyncTable() {
        new SyncDBHelper().deleteSyncTable(this);
    }

    public boolean isProdAvailable() {
        return new UserDbHelper().isProductAvailable(this);

    }

    public boolean isProductVanSalesAvailable() {
        return new UserDbHelper().isProductVanSalesAvailable(this);

    }

    public boolean isUomAvailable() {
        return new UserDbHelper().isUOMAvailable(this);

    }

    public boolean isUOMVanSalesAvailable() {
        return new UserDbHelper().isUOMVanSalesAvailable(this);
    }

    public boolean isVanSalesDataAvailable() {
        return new UserDbHelper().isVanSalesDataAvailable(this);
    }

    public List<RouteModel> getAllRoutes(String distCode) {
        return dashboardHelper.getAllRoutes(this, distCode);
    }

    public PendingVO getRetailerByCode(String retailerCode) {

        return new RetailerDetailHelper().getRetailerByRetailerCode(this, retailerCode);

    }

    public List<OrderBookingVO> fetchAllProducts(String distrCode, String salesmanCode,
                                                 String prefRoutecode,
                                                 String retrCode) {

        return new OrderBookingHelper().getAllProducts(this, distrCode, salesmanCode, prefRoutecode, retrCode);
    }

    public List<OrderBookingVO> fetchAllReceiptProducts(String invoiceNo) {

        return new OrderBookingHelper().getAllReceiptProducts(this, invoiceNo);
    }

    public List<OrderBookingVO> fetchDistPurchaseProducts(String distrCode) {

        return new OrderBookingHelper().getAllDistPurchaseProducts(this, distrCode);
    }

    public List<OrderBookingVO> fetchAllBillingProducts(String distrCode, String salesmanCode,
                                                        String prefRoutecode,
                                                        String retrCode) {

        return new OrderBookingHelper().getAllBillingProducts(this, distrCode, salesmanCode, prefRoutecode, retrCode);
    }

    public List<OrderBookingVO> fetchStockSummaryData(String distrCode, String salesmanCode) {

        return new OrderBookingHelper().getStockSummaryData(this, distrCode, salesmanCode);
    }

    public List<OrderBookingVO> fetchBrands(String distrCode, String salesmanCode, String tableName) {
        return new OrderBookingHelper().fetchBrands(this, distrCode, salesmanCode, tableName);
    }

    public List<OrderBookingVO> fetchProductOrderBrands(String distrCode, String cmpCode, String tableName) {
        return new OrderBookingHelper().fetchProductOrderBrands(this, distrCode, cmpCode, tableName);
    }

    public List<OrderBookingVO> fetchVanSalesBrands(String distrCode, String salesmanCode, String tableName) {
        return new OrderBookingHelper().fetchVanSalesBrands(this, distrCode, salesmanCode, tableName);
    }

    public List<String> fetchCategory() {
        return new OrderBookingHelper().fetchCategory(this);
    }

    public List<OrderBookingVO> getUom(String prodCode, String tableName) {

        return new OrderBookingHelper().getUOMFromDB(this, prodCode, tableName);
    }

    public List<OrderBookingVO> getOpeningStockUOMFromDB(String uomgid) {

        return new OrderBookingHelper().getOpeningStockUOMFromDB(this, uomgid);
    }

    public List<String> getUOMForProd(String prodCode, String className) {

        return new OrderBookingHelper().getUOMForProd(this, prodCode, className);
    }

    public List<String> getUOMForCombiProduct(String prodCode, String className) {

        return new OrderBookingHelper().getUOMForCombiProduct(this, prodCode, className);
    }

    public void insertOrderBookingTracker(String routeCode, String customerCode, Long startTime, Long endTime, String upload) {
        new OrderBookingHelper().insertOrderBookingTracker(this, routeCode, customerCode, startTime, endTime, upload);
    }

    public void insertBillTracker(String routeCode, String customerCode, Long startTime, Long endTime, String upload) {
        new OrderBookingHelper().insertBillingTracker(this, routeCode, customerCode, startTime, endTime, upload);
    }

    public void insertOrderBooking(String invoiceno, String routecode, String retrCode,
                                   List<OrderBookingVO> orderBookingVOs, String retailerType, int newRetailer) {
        new OrderBookingHelper().insertOrderBooking(this, invoiceno, routecode, retrCode, orderBookingVOs, retailerType, newRetailer);
    }

    public void deleteAllSchemeProdRetailer(String routecode, String retrCode, List<OrderBookingVO> orderBookingVOs,
                                            String tableName) {
        new OrderBookingHelper().deleteAllSchemeProdRetailer(this, routecode, retrCode, orderBookingVOs, tableName);
    }

    public void insertOrderedProductSchemeList(String invoiceno, String routecode, String retrCode,
                                               List<SchemeModel> orderedProdSchemeDetails, String tableName) {
        new OrderBookingHelper().insertOrderedProductSchemeList(this, invoiceno, routecode,
                retrCode, orderedProdSchemeDetails, tableName);
    }

    public void insertPurchaseOrderBooking(String invoiceno, String cmpCode, String distrCoce,
                                           String date, List<OrderBookingVO> orderBookingVOs) {
        new OrderBookingHelper().insertPurchaseOrderBooking(this, invoiceno, cmpCode, distrCoce, date, orderBookingVOs);
    }

    public void insertBilling(String routecode, String salesmanCode, String retrCode,
                              String date, List<OrderBookingVO> orderBookingVOs, String invoiceno, String freeqty, String completeFlag) {
        new OrderBookingHelper().insertBilling(this, routecode, retrCode, salesmanCode, date, orderBookingVOs, invoiceno, freeqty, completeFlag);
    }

    public List<RetailerCategoryVO> loadChannel(String prefDistrcode) {

        return new UserDbHelper().loadChannelData(this, prefDistrcode);
    }

    public List<RetailerCategoryVO> loadSubChannel(String prefDistrcode, String channelCode) {

        return new UserDbHelper().loadSubChannelData(this, prefDistrcode, channelCode);
    }

    public List<RetailerCategoryVO> loadGroupData(String prefDistrcode,
                                                  String strSubChannel) {

        return new UserDbHelper().loadGroupData(this, prefDistrcode, strSubChannel);
    }

    public List<RetailerCategoryVO> loadClassData(String prefDistrcode, String strChannel,
                                                  String groupCode) {

        return new UserDbHelper().loadClassData(this, prefDistrcode, strChannel, groupCode);
    }

    public void addNewSalesmanDetails(SalesmanMasterModel salesmanMasterModel) {
        UserDbHelper userDbHelper = new UserDbHelper();
        userDbHelper.insertSalesmanDetail(this, salesmanMasterModel);
        userDbHelper.insertSalesmanRouteMappingDetail(this, salesmanMasterModel);
        userDbHelper.insertSalesmanLobMappingDetail(this, salesmanMasterModel);
    }

    public void insertSalesmanKyc(SalesmanMasterModel salesmanMasterModel, HashMap<String, String> kycImageMap) {

        new UserDbHelper().insertSalesmanKycDetail(this, salesmanMasterModel, kycImageMap);
    }

    public List<SalesmanMasterModel> getAllNewSalesmanDetailsFromDB() {

        return new UserDbHelper().fetchAllNewSalesmanDetails(this);


    }

    public SalesmanMasterModel getNewSalesmanDetailsFromDB(String salesmanCode) {

        return new UserDbHelper().fetchNewSalesmanDetails(this, salesmanCode);


    }

    public void deleteNewSalesmanDetailsFromDB(String salesmanCode) {

        new UserDbHelper().deleteNewSalesmanByCode(this, salesmanCode, TABLE_SALESMAN_MASTER);

    }

    public List<SalesmanMasterCheckBoxModel> getNewSalesmanRouteMappingFromDB(String salesmanCode) {

        return new UserDbHelper().fetchNewSalesmanRouteMapDetails(this, salesmanCode);


    }

    public List<SalesmanMasterCheckBoxModel> getNewSalesmanAllRouteMapping() {

        return new UserDbHelper().fetchNewSalesmanAllRouteMap(this);


    }

    public List<SalesmanMasterCheckBoxModel> getNewSalesmanLobMappingFromDB(String salesmanCode) {

        return new UserDbHelper().fetchNewSalesmanLOBMapDetails(this, salesmanCode);


    }

    public List<SalesmanMasterCheckBoxModel> getNewSalesmanAllLobMapping() {

        return new UserDbHelper().fetchNewSalesmanAllLOBMap(this);

    }

    public HashMap<String, String> getKycDetailsFromDb(String salesmanCode) {

        return new UserDbHelper().fetchNewSalesmanKycDetails(this, salesmanCode);

    }

    public boolean isNewSalesmanSync(String salesmanCode) {

        return new UserDbHelper().isSalesmanDataSync(this, salesmanCode);
    }

    public void deteleRetailerById(String rtrcode) {

        new UserDbHelper().deleteRetailerByID(this, rtrcode);
    }

    public void deteleRetailerDetailsById(String rtrcode) {

        new UserDbHelper().deleteRetailerDataByID(this, rtrcode);
    }

    public List<OrderBookingVO> fetchOrderedProduct(String code) {

        return new OrderBookingHelper().getOrderedProductFromDB(this, code);
    }

    public List<OrderBookingVO> fetchPurchaseOrderedSummaryData(String prefInvoiceNo) {

        return new OrderBookingHelper().getPurchaseOrderedSummaryFromDB(this, prefInvoiceNo);
    }

    public List<OrderBookingVO> getBillingProductAndFreeProdFromDB(String prefDistrcode,
                                                                   String prefSalesmancode,
                                                                   String retrCode, String className) {

        return new OrderBookingHelper().getBillingProductAndFreeProdFromDB(this, prefDistrcode, prefSalesmancode, retrCode, className);
    }

    public void deleteProductById(String prefDistrcode, String prefSalesmancode,
                                  String prefRoutecode, String retrCode,
                                  OrderBookingVO bookingVO) {

        new OrderBookingHelper().deleteExiteProductById(this, prefDistrcode, prefSalesmancode, prefRoutecode, retrCode, bookingVO);
    }

    public void deleteExistFreeProductById(String prefDistrcode, String prefSalesmancode,
                                           String prefRoutecode, String retrCode,
                                           OrderBookingVO bookingVO, String tableName) {

        new OrderBookingHelper().deleteExistFreeProductById(this, prefDistrcode, prefSalesmancode, prefRoutecode, retrCode, bookingVO, tableName);
    }

    public List<PendingBillVO> getPendingBillsFromDB(String distrcode,
                                                     String salesmancode,
                                                     String retrCode) {

        return new RetailerDetailHelper().getPendingBills(this, distrcode, salesmancode, retrCode);
    }

    public List<OrderBookingVO> getPurchaseOrderBookingLineItem(String cmpCode, String distCode) {
        return new SyncDBHelper().getPurchaseOrderBookingLineItem(this, cmpCode, distCode);
    }

    public void uploadRetailerWorkStatus(String customerCode, String tableName,
                                         String col, String status) {
        new SyncDBHelper().uploadRetailerWorkStatus(this, customerCode, tableName, col, status);
    }

    public void updateRetailerVisit(String col, String val,
                                    String distrCode, String salesmanCode,
                                    String routeCode, String retlrCode) {
        new SyncDBHelper().updateRetailerVisit(this, col, val, distrCode, salesmanCode,
                routeCode, retlrCode);
    }

    public Boolean isStockCaptured(String distrCode, String salesmanCode,
                                   String routeCode, String retlrCode) {
        return new SyncDBHelper().isStockCaptureAvailable(this, distrCode, salesmanCode,
                routeCode, retlrCode);
    }

    public void updateRetailer(String distrCode, String salesmanCode, String routeCode, String customerCode) {

        new OrderBookingHelper().updateRetailer(this, distrCode, salesmanCode, routeCode, customerCode);

    }

    public void updateRetailerVisitComplete(String distrCode, String salesmanCode, String routeCode, String customerCode) {

        new OrderBookingHelper().updateRetailerVisitComplete(this, distrCode, salesmanCode, routeCode, customerCode);

    }

    public boolean checkOrderBookingTracker(String distrCode, String salesmanCode, String routeCode, String retlrCode) {
        return new OrderBookingHelper().checkOrderBookingTracker(this, distrCode, salesmanCode, routeCode, retlrCode);
    }

    public boolean checkBillingTracker(String distrCode, String salesmanCode, String routeCode, String retlrCode) {
        return new OrderBookingHelper().checkBillingsTracker(this, distrCode, salesmanCode, routeCode, retlrCode);
    }

    public void updateOrderBookingTracker(Long startTime, Long endTime, String distrCode, String salesmanCode, String routeCode, String retlrCode) {

        new OrderBookingHelper().updateOrderBookingTracker(this, startTime, endTime, distrCode, salesmanCode, routeCode, retlrCode);
    }

    public void updateBillingTracker(Long startTime, Long endTime, String distrCode, String salesmanCode, String routeCode, String retlrCode) {

        new OrderBookingHelper().updateBillingsTracker(this, startTime, endTime, distrCode, salesmanCode, routeCode, retlrCode);
    }

    public void updateSyncProgress(String col, String val, String distrCode) {
        new SyncDBHelper().updateSyncProgress(this, col, val, distrCode);
    }

    public List<CollectionVO> loadBankNamesFromDB(String distrcode, String salesmancode) {

        return new OrderBookingHelper().getBankDetailFromDB(this, distrcode, salesmancode);
    }

    public void deleteCashCollection(String distrcode, String salesmancode, String routecode, String retrCode) {

        new OrderBookingHelper().deteleCashDetail(this, distrcode, salesmancode, routecode, retrCode);
    }

    public void deleteChequeCollection(String distrcode, String salesmancode, String routecode, String retrCode) {

        new OrderBookingHelper().deteleChequeDetail(this, distrcode, salesmancode, routecode, retrCode);
    }

    public void insertCashCollection(String invoiceNo, String routecode,
                                     String retrCode, double amount, String remarks, String state, String salesmanCode) {

        new OrderBookingHelper().insertCashDetailsInDB(this, invoiceNo,
                routecode, retrCode, amount, remarks, state, salesmanCode);
    }

    public void insertChequeCollection(String invoiceNo, String routecode, String date, String retrCode, CollectionVO collectionVOS, String remarks, String salesmanCode) {

        new OrderBookingHelper().insertChequeDetailsInDB(this, invoiceNo, routecode, date, retrCode, collectionVOS, remarks, salesmanCode);
    }

    public void updateCustomerUploadWorkStatus(String collectionsUploaded, String state, String distrcode, String salesmancode, String routecode, String retrCode) {

        new OrderBookingHelper().updateRetailerUploadWordStatus(this, collectionsUploaded, state, distrcode, salesmancode, routecode, retrCode);
    }

    public List<CollectionVO> loadCashCollection(String distrcode, String salesmancode, String routecode, String retrCode) {
        return new OrderBookingHelper().loadCashCollection(this, distrcode, salesmancode, routecode, retrCode);
    }

    public List<CollectionVO> loadChequeCollection(String distrcode, String salesmancode, String routecode, String retrCode) {
        return new OrderBookingHelper().loadChequeCollection(this, distrcode, salesmancode, routecode, retrCode);
    }

    public String loadBillingCashCollection(String distrcode, String salesmancode, String routecode, String retrCode) {
        return new OrderBookingHelper().loadBillingCashCollection(this, distrcode, salesmancode, routecode, retrCode);
    }

    public void deleteUpdateLocation(String distrcode, String salesmancode, String retrCode) {

        new OrderBookingHelper().deleteRetailerExistingLocation(this, distrcode, salesmancode, retrCode);
    }

    public void updateRetailerGeo(String latitude, String longitude, String distrcode, String salesmancode, String routecode, String retrCode) {

        new OrderBookingHelper().updateRetailerGeoLocation(this, latitude, longitude, distrcode, salesmancode, routecode, retrCode);
    }

    public List<ReasonVO> loadReasons(String screenName) {
        return new OrderBookingHelper().getSalesReturnReasons(this, screenName);
    }

    public List<SalesReturnVO> loadExistingPurchaseReturns(String cmpCode, String distrcode) {

        return new OrderBookingHelper().loadExistingPurchaseReturns(this, cmpCode, distrcode);
    }

    public List<StockTakeVO> getAllReturnProductFromAuto(String cmpCode, String distrcode) {


        return new OrderBookingHelper().getAllReturnProductFromAuto(this, cmpCode, distrcode);
    }

    public void deletePurchaseReturn(String distrcode, String prodCode, String batchCode, String state) {

        new OrderBookingHelper().deletePurchaseReturn(this, distrcode, prodCode, batchCode, state);
    }

    public void insertPurchaseReturn(String invoiceNo, String distrcode, String date, SalesReturnVO salesReturnVO, String returnType) {

        new OrderBookingHelper().insertPurchaseReturn(this, invoiceNo, distrcode, date, salesReturnVO, returnType);

    }

    public void deletePurchaseReturnById(String distrcode, String prodCode, String state) {

        new OrderBookingHelper().deletePurchaseReturnById(this, distrcode, prodCode, state);
    }

    public void deleteCollctionById(String distrcode, String salesmancode, String routecode, String cheque, String retrCode, String state) {

        new OrderBookingHelper().deleteCollctionById(this, distrcode, salesmancode, routecode, cheque, retrCode, state);
    }

    public List<FreeProdModel> fetchSingleProdFreeProds(String distrCode, String salesmanCode, String routeCode, String retlrCode, OrderBookingVO bookingVO, String tableName) {
        return new OrderBookingHelper().fetchSingleProdFreeProds(this, distrCode, salesmanCode, routeCode, retlrCode, bookingVO, tableName);
    }

    public String getInvoiceNo(String tableName, String distcode, String salesmancode, String routecode, String retailerCode) {
        return new OrderBookingHelper().fetchInvoiceNo(this, tableName, distcode, salesmancode, routecode, retailerCode);
    }

    public String fetchOpeningStockInvoiceNo(String tableName, String distcode, String cmpCode) {
        return new OrderBookingHelper().fetchOpeningStockInvoiceNo(this, tableName, distcode, cmpCode);
    }

    public Map<String, String> getTodayBeat(String routeCode) {

        return dashboardHelper.getTodayBeatCount(this, routeCode);
    }

    public String getTodayBeatRouteCode(String distrcode, String salesmancode, String state) {
        return dashboardHelper.getTodayBeatRouteCode(this, distrcode, salesmancode, state);
    }

    public boolean updateRetailerSignature(String distCode, String salesmanCode, String routecode, String retlrCode, String base64img) {

        return new OrderBookingHelper().updateRetailerSign(this, distCode, salesmanCode, routecode, retlrCode, base64img);
    }

    public List<MessageModel> getAllMessages(String prefDistrcode, String salesmanCode, Integer limit, boolean state) {
        return dashboardHelper.getAllMessagesFromDB(this, prefDistrcode, salesmanCode, limit, state);
    }

    public void insertMessage(MessageModel messageModel) {

        dashboardHelper.insertMessage(this, messageModel);
    }

    public Integer getAllMessageCount() {

        return dashboardHelper.getMessageCount(this);
    }

    public void updateMessageStatus(String distrcode, String msgCode) {

        dashboardHelper.updateMessageStatusInDB(this, distrcode, msgCode);
    }

    public void updateMessageDownloadStatus(String distrcode, String msgCode, String downloadStatus) {

        dashboardHelper.updateMessageDownloadStatus(this, distrcode, msgCode, downloadStatus);
    }

    public List<OrderBookingVO> getPurchaseReceiptConfirmData(String cmpCode, String distCode) {

        return new DistrPurchaseHelper().getPurchaseReceiptConfirmData(this, cmpCode, distCode);
    }

    public void insertStockTakeData(String invoiceno, String routecode, String retrCode,
                                    String date, List<OrderBookingVO> orderBookingVOs) {
        new OrderBookingHelper().insertStockTake(this, invoiceno, routecode, retrCode, date, orderBookingVOs);
    }

    public void insertOpeningStockData(String invoiceno, String cmpCode, String distrCoce,
                                       String date, List<OrderBookingVO> orderBookingVOs) {

        new OrderBookingHelper().insertOpeningStockData(this, invoiceno, cmpCode, distrCoce, date, orderBookingVOs);
    }

    public List<OrderBookingVO> fetchAllStocktakeProducts(String distrCode, String salesmanCode,
                                                          String routecode,
                                                          String retrCode) {

        return new OrderBookingHelper().getAllStockTakeProducts(this, distrCode, salesmanCode, routecode, retrCode);
    }

    public List<OrderBookingVO> getOpeningStockSummaryProducts(String distrCode) {

        return new OrderBookingHelper().getOpeningStockSummaryProducts(this, distrCode);
    }

    public List<OrderBookingVO> fetchAllOpeningStockProducts(String distrCode, String cmpCode) {

        return new OrderBookingHelper().fetchAllOpeningStockProducts(this, distrCode, cmpCode);
    }

    public List<OrderBookingVO> fetchStockTakeProducts(String prefDistrcode,
                                                       String prefSalesmancode,
                                                       String prefRoutecode,
                                                       String retrCode) {

        return new OrderBookingHelper().getStockTakeProductsFromDB(this, prefDistrcode, prefSalesmancode, prefRoutecode, retrCode);
    }

    public List<OrderBookingVO> fetchStockTakeProductsQuantityOnly(String prefDistrcode,
                                                                   String prefSalesmancode,
                                                                   String prefRoutecode,
                                                                   String retrCode) {

        return new OrderBookingHelper().getStockTakeProductsOnlyWithQtyFromDB(this, prefDistrcode, prefSalesmancode, prefRoutecode, retrCode);
    }

    public void deleteStockTakeProductById(String prefDistrcode, String prefSalesmancode, String prefRoutecode, String retlrCode, OrderBookingVO bookingVO) {


        new OrderBookingHelper().deleteExistStockTakeProductById(this, prefDistrcode, prefSalesmancode, prefRoutecode, retlrCode, bookingVO);
    }

    public List<PendingVO> getAllOutletsBasedRoute(String distrcode, String salesmancode, String routeCode, String lat, String lng, String retCode) {
        return new RetailerDetailHelper().getAllOutletsBasedRoute(this, distrcode, salesmancode, routeCode, lat, lng, retCode);
    }

    public List<ProductsVO> fetchAllStockInHandProducts(String distCode, int state, String schemeCode) {
        return dashboardHelper.getAppStockProudcts(this, distCode, state, schemeCode);
    }

    public List<ProductsVO> fetchAllStockInHandProducts(String distCode, int state) {
        return dashboardHelper.getAppStockProudcts(this, distCode, state);
    }

    public void deleteQuickActions(String menuName) {
        dashboardHelper.deleteQuickActions(this, menuName);
    }

    public Boolean insertQuickAction(String menuName, boolean isAcive) {
        return dashboardHelper.insertQuickActions(this, menuName, isAcive);
    }

    public List<QuickViewVo> getActiveQuickMenus() {
        return dashboardHelper.getActiveQuickMenus(this);
    }

    public boolean isFavoriteMenu(String screenName) {
        return dashboardHelper.getIsFavoriteMenu(this, screenName);
    }

    public void insertOrUpdateAutoQuickActions(String menuName, int screenCount) {
        dashboardHelper.insertOrUpdateAutoQuickActions(this, menuName, screenCount);
    }

    public int getAutoQuickMenuCount(String menuName) {
        return dashboardHelper.getAutoQuickMenuCount(this, menuName);
    }

    public List<QuickViewVo> getAllAutoQuickMenus(int menucount) {
        return dashboardHelper.getAllAutoQuickMenus(this, menucount);
    }

    public List<OrderBookingVO> fetchPreviousOrderDetails(String cmpCode, String distrCode, String orderNo) {
        return new OrderBookingHelper().fetchPreviousOrderDetails(this, cmpCode, distrCode, orderNo);
    }

    public List<StockledgerReportModel> getStockLedgerReportDetails() {
        return new ProductHelper().getStockLedgerReportDetails(this);
    }

    public void updateRetailerMenuReason(String distrCode, String salesmanCode, String routeCode, String retlrCode) {

        new OrderBookingHelper().updateRetailerMenuReasonToEmpty(this, distrCode, salesmanCode, routeCode, retlrCode);
    }

    public boolean isNewOutletSync(String strCustomerCode) {

        return new RetailerDetailHelper().isOutletDataSync(this, strCustomerCode);
    }

    public String getDistStateCode() {
        return new UserDbHelper().getDistStateCode(this);
    }

    public String getLoginDistStateCode() {
        return new UserDbHelper().getLoginDistStateCode(this);
    }

    public String getRetaielrStateCode(String cmpCode, String distCode, String retailerCode) {
        return new RetailerDetailHelper().getRetaielrStateCode(this, cmpCode, distCode, retailerCode);
    }

    public String getNewRetaielrStateCode(String distrCode, String salesmanCode, String retailerCode) {
        return new RetailerDetailHelper().getNewRetaielrStateCode(this, distrCode, salesmanCode, retailerCode);
    }

    public Boolean isDistUnionTeritory(String distStateCode) {
        return new RetailerDetailHelper().isDistUnionTeritory(this, distStateCode);
    }

    public List<TaxModel> getTaxCalPercentForUT(String distCode, String prodCode, String retailerStateCode, boolean isUnionTerritory) {
        return new RetailerDetailHelper().getTaxCalPercentForUT(this, distCode, prodCode, retailerStateCode, isUnionTerritory);
    }

    public List<TaxModel> getTaxCalPercentStates(String distCode, String prodCode, String retailerStateCode) {
        return new RetailerDetailHelper().getTaxCalPercentStates(this, distCode, prodCode, retailerStateCode);
    }

    public List<TaxMasterModel> getPOTaxCalPercentForStates(String cmpCode, String prodCode, String retailerStateCode) {
        return new RetailerDetailHelper().getPOTaxCalPercentStates(this, cmpCode, prodCode, retailerStateCode);
    }

    public List<String> getMustsellProduuct(String retailerCode, String tableName) {

        return new OrderBookingHelper().fetchMustsellProducts(this, retailerCode, tableName);
    }

    public List<String> getFocusBrandProducts(String retailerCode, String tableName) {

        return new OrderBookingHelper().fetchFocusBrandProducts(this, retailerCode, tableName);
    }

    public List<SchemeModel> getAllSchemeSlabDetail(List<SchemeModel> schemeModelList) {

        return new OrderBookingHelper().fetchSchemeSlabDetail(this, schemeModelList);

    }

    public List<SchemeModel> fetchSchemeSlabList(SchemeModel schemeModelList) {

        return new OrderBookingHelper().fetchSchemeSlabList(this, schemeModelList);

    }

    public List<SchemeModel> getCombiSchemeSlabList(String distCode, String schemeCode) {

        return new OrderBookingHelper().getCombiSchemeSlabList(this, distCode, schemeCode);

    }

    public SchemeModel getSchemeDefinition(String distCode, String schemeCode) {

        return new OrderBookingHelper().getSchemeDefinition(this, distCode, schemeCode);

    }

    public SchemeModel getSuggestedSchemeSlabDetail(SchemeModel schemeModelList, double orderValue) {

        return new OrderBookingHelper().fetchSuggestedSchemeSlabDetail(this, schemeModelList, orderValue);

    }

    public String getUomCodeForScheme(SchemeModel schemeModel) {

        return new OrderBookingHelper().getUomCodeForScheme(this, schemeModel);

    }

    public List<SchemeModel> getSchemeSlabDetails(String schemecode) {

        return new OrderBookingHelper().getSchemaSlabDetails(this, schemecode);

    }

    public List<SchemeModel> fetchSuggestedFreeProdList(SchemeModel schemeModelList, double orderValue, String className) {

        return new OrderBookingHelper().fetchSuggestedFreeProdList(this, schemeModelList, orderValue, className);

    }

    public List<SchemeModel> getAllFreeProdList(List<SchemeModel> schemeModelList) {

        return new OrderBookingHelper().getAllFreeProdList(this, schemeModelList);

    }

    public List<SchemeModel> getFreeProdSingleScheme(String schemeCode) {

        return new OrderBookingHelper().getFreeProdSingleScheme(this, schemeCode);

    }

    public List<OrderBookingVO> getOrderedFreeProductFromDB(String distrCode, String salesmanCode,
                                                            String routeCode, String customerCode, String tableName) {

        return new OrderBookingHelper().getOrderedFreeProductFromDB(this, distrCode, salesmanCode, routeCode, customerCode, tableName);
    }

    public List<SchemeModel> getSingleSchemeProdList(String distrCode, String salesmanCode, String routeCode, String customerCode, OrderBookingVO bookingVO, String tableType) {

        return new OrderBookingHelper().getSingleSchemeProdList(this, distrCode, salesmanCode, routeCode, customerCode, bookingVO, tableType);
    }

    public List<SchemeModel> getCombiProductList(String distrCode, String schemeCode) {

        return new OrderBookingHelper().getCombiProductList(this, distrCode, schemeCode);
    }

    public void updateVanStockLoadingData(String distCode, String salesmanCode, List<OrderBookingVO> qtyUpdatedOrderBooking) {


        new OrderBookingHelper().updateVanStockLoadingData(this, distCode, salesmanCode, qtyUpdatedOrderBooking);
    }

    public void deleteMsgById(String prefDistrcode, String msgCode) {

        dashboardHelper.deleteMsgById(this, prefDistrcode, msgCode);
    }

    public List<GSTModel> loadStateData(String cmpCode) {

        return dashboardHelper.getStateDataFromDB(this, cmpCode);

    }

    public String getSalReturnWithRefInvoiceNo(String tableName, String salesmanCode, String routeCode, String retrCode, String referenceNo, boolean state) {


        return new OrderBookingHelper().getSalReturnWithRefInvoiceNo(this, tableName, salesmanCode, routeCode, retrCode, referenceNo, state);
    }

    public List<ScreenConfig> getOfflineScreenConfig(String cmpCode) {
        return new MTDDashboardHelper().getOfflineScreenConfig(this, cmpCode);
    }

    public String getRouteName(String cmpCode, String distrCode, String routeCode) {
        return new OrderBookingHelper().getRouteName(this, cmpCode, distrCode, routeCode);
    }

    public String getConfigDataBasedOnName(String updateLocation) {
        return dashboardHelper.getConfigData(this, updateLocation);
    }

    public String getConfigDescriptionBasedOnName(String updateLocation) {
        return dashboardHelper.getConfigDescription(this, updateLocation);
    }

    public Map<String, String> getConfigColors(String[] tableNames) {

        return dashboardHelper.getConfigColors(this, tableNames);
    }

    public List<PurchaseInvoiceModel> getPurchaseReceiptHeader(String cmpCode) {
        return new DistrPurchaseHelper().getPurchaseReceiptHeader(this, cmpCode);
    }

    public List<OrderBookingVO> getTaxMaster() {
        return new OrderBookingHelper().getTaxMaster(this);
    }

    public void updatePurchaseReceivedQty(String grnCode, List<OrderBookingVO> purchaseReceiptList) {
        new DistrPurchaseHelper().updatePurchaseReceivedQty(this, grnCode, purchaseReceiptList);
    }

    public List<StockistModel> getStockistDetails() {
        return new UserDbHelper().getStockistDetails(this);
    }

    public List<StockistModel> getSuperStockistDetails() {
        return new UserDbHelper().getSuperStockistDetails(this);
    }

    public List<StockistModel> getSubStockistDetails() {
        return new UserDbHelper().getSubStockistDetails(this);
    }

    public List<StockistModel> getSubStockistDetails(String superstockistCode) {
        return new UserDbHelper().getSubStockistDetails(this, superstockistCode);
    }

    public void deleteExitePurchaseProductById(String userCode, String prodCode, String prodBatchCode) {

        new InventoryHelper().deleteExitePurchaseProductById(this, userCode, prodCode, prodBatchCode);
    }

    public void deleteExistOpeningStock(String prodCode) {

        new OrderBookingHelper().deleteExistOpeningStock(this, prodCode);
    }

    public List<RetailerVO> getPendingRetailerList(String userCode) {
        return new InventoryHelper().getPendingRetailerFromDB(this, userCode);
    }

    public void updateOrderPendingState(String tableName, String status, String invNo) {

        new OrderBookingHelper().updatePendingOrderStatus(this, tableName, status,invNo);
    }

    public List<TimeCaptureModel> getSalesmanLoginTime(String cmpCode, String distCode) {
        return new RetailerDetailHelper().fetchSalesmanLoginTime(this, cmpCode, distCode);
    }

    public List<String> getBannerList() {
        return new DashboardHelper().fetchAllBannerList(this);
    }

    public List<ProFilterModel> getFirstLevel(String tableName, String levelCode,
                                              String nextLevelName, String lobCode) {

        return new ProductHelper().getFirstHierLevel(this, tableName, levelCode, nextLevelName, lobCode);
    }

    public List<ProFilterModel> getNexttLevelData(String tableName, String brandVal, String brandColumn,
                                                  String levelCode,
                                                  String levelName, String lobCode) {

        return new ProductHelper().getNextLevelData(this, tableName, brandVal, brandColumn,
                levelCode, levelName, lobCode);
    }

    public boolean isCustomerSchemeExist(String cmpCode, String customerCode) {
        return new OrderBookingSchemeHelper().isCustomerSchemeExist(this, cmpCode, customerCode);
    }

    public String getCustomerZipScheme(String distrCode, String customerCode) {
        return new OrderBookingSchemeHelper().getCustomerZipScheme(this, distrCode, customerCode);
    }

    public void insertRetailerSchemeDetails(Sync sync, String routeCode) {
        new SyncDBHelper().insertRetailerSchemeProductList(sync, this, routeCode);
    }

    public List<SchemeModel> getRetailerSchemeProducts() {
        return new OrderBookingSchemeHelper().getRetailerSchemeProducts(this);
    }

    public List<String> getAllMustSellBilledProducts(String cmpCode, String distrCode, String customerCode) {
        return new ProductHelper().getAllMustSellBilledProducts(this, cmpCode, distrCode, customerCode);
    }

    public List<SchemeModel> getSchemeByProdCode(String cmpCode, String prodCodeCaps) {
        return new OrderBookingSchemeHelper().getSchemeDetailByProdCode(this, cmpCode, prodCodeCaps);
    }

    public List<StockistModel> getSelectedStockistDetails(String distCode) {
        return new UserDbHelper().getSelectedStockistDetails(this, distCode);
    }

    public List<NewOutletImageList> getNewOutletAllImages(String retailerCode) {
        return new UserDbHelper().fetchNewRetailerAllImages(this, retailerCode);
    }

    public void deleteImageById(String imageId) {

        new UserDbHelper().deleteRetailerIamgeByID(this, imageId);
    }

    public boolean updateNewRetailerSignature(String distCode, String salesmanCode, String routeCode,
                                              String retlrCode, String base64img) {

        return new OrderBookingHelper().updateNewRetailerSign(this, distCode, salesmanCode,
                routeCode, retlrCode, base64img);
    }

    public String getStateNameByCode(String stateCode) {
        return new RetailerDetailHelper().getStateNameByCode(this, stateCode);
    }

    public Boolean isOutletAvailable(String cmpCustomerCode) {
        return new UserDbHelper().isOutletAvailable(this, cmpCustomerCode);
    }

    public Integer getNewOutletTotalCounts(String distCode, String salesmanCode, String tableName) {

        return new DashboardHelper().fetchNewOutletTotalCount(this, distCode, salesmanCode, tableName);
    }


    public List<PendingVO> getRetailerFromDB() {
        return new UserDbHelper().fetchRetailerDetails(this);
    }

    public List<RouteModel> getRoutes(String distCode) {
        return new RouteQueryHelper().getRouteMasterList(this, distCode);
    }

    public List<RouteModel> getRoutesForUpload(String distCode) {
        return new RouteQueryHelper().getRoutesForUpload(this, distCode);
    }

    public Distributor getDistributor(String userCode) {
        return new UserDbHelper().getDistributor(this, userCode);
    }

    public void updateDistributorWithId(Distributor distributor) {
        new UserDbHelper().updateDistributor(distributor, this);
    }

    public List<LobModel> getAllMasterLob(String companyCode) {
        return new LobQueryHelper().getAllMasterLob(this, companyCode);
    }

    public List<LobModel> getAllDistributorLob(String companyCode, String distributorCode) {
        return new LobQueryHelper().getAllDistributorLob(this, companyCode, distributorCode);
    }

    public void resetAllDistributorLob(List<LobModel> distributorLob) {
        new LobQueryHelper().resetAllDistributorLob(distributorLob, this);
    }

    public void insertWDStorage(String cmpCode, String distCode, WDInfrastructure infrastructure) {
        new WDInfrastructureHelper().insertStorage(this, cmpCode, distCode, infrastructure);
    }

    public void insertWDFreezer(String cmpCode, String distCode, WDInfrastructure infrastructure) {
        new WDInfrastructureHelper().insertFreezer(this, cmpCode, distCode, infrastructure);
    }

    public void insertBankDetail(RetailerVO retailerList, String cmpCode, String distCode, String customerCode) {
        new RetailerDetailHelper().insertBankDetail(this, retailerList, cmpCode, distCode, customerCode);
    }

    public void insertCustomerDetail(RetailerVO retailerList, String cmpCode, String distCode, String customerCode) {
        new RetailerDetailHelper().insertCustomerDetail(this, retailerList, cmpCode, distCode, customerCode);
    }

    public void insertCustomerShipAddressDetail(List<AddressModel> shippingAddressList, String cmpCode, String distCode, String customerCode) {
        new RetailerDetailHelper().insertCustomerShipAddrDetail(this, shippingAddressList, cmpCode, distCode, customerCode);
    }

    public List<RetailerVO> getRetailerList(String cmpCode, String distCode) {
        return new SyncDBHelper().getRetailerList(this, cmpCode, distCode);
    }

    public List<RetailerVO> getRetailerBankList(String cmpCode, String distCode) {
        return new SyncDBHelper().getRetailerBankList(this, cmpCode, distCode);
    }

    public List<RouteModel> getRetailerRouteList(String cmpCode, String distCode) {
        return new SyncDBHelper().getRetailerRouteList(this, cmpCode, distCode);
    }

    public List<AddressModel> getRetailerShipAddrList(String cmpCode, String distCode) {
        return new SyncDBHelper().getRetailerShipAddrList(this, cmpCode, distCode);
    }

    public void uploadCustomerStatus(String customerCode, String tableName, String columnUpload, String status) {
        new SyncDBHelper().uploadCustomerStatus(this, customerCode, tableName, columnUpload, status);
    }

    public void uploadCustomerBankStatus(String customerCode, String bankID, String tableName, String columnUpload, String status) {
        new SyncDBHelper().uploadCustomerBankStatus(this, customerCode, bankID, tableName, columnUpload, status);
    }

    public void uploadCustomerRouteStatus(String customerCode, String routeCode, String tableName, String columnUpload, String status) {
        new SyncDBHelper().uploadCustomerRouteStatus(this, customerCode, routeCode, tableName, columnUpload, status);
    }

    public void uploadCustomerShipAddrStatus(String customerCode, String customerShipCode, String tableName, String columnUpload, String status) {
        new SyncDBHelper().uploadCustomerShipAddrStatus(this, customerCode, customerShipCode, tableName, columnUpload, status);
    }

    public List<WDInfrastructure> getWDStorage(String cmpCode, String distCode) {
        return new WDInfrastructureHelper().getStorageDetail(this, cmpCode, distCode);
    }

    public List<WDInfrastructure> getWDFreezer(String cmpCode, String distCode) {
        return new WDInfrastructureHelper().getFreezerDetail(this, cmpCode, distCode);
    }

    public boolean isLoggedInUserAvailable(String loginCode) {
        return new LoginQueryHelper().isLoggedInUserAvailable(this, loginCode);
    }

    public LoginModel getLoggedInUserBasedOnId(String loginCode, String loginPassword) {
        return new LoginQueryHelper().getLoggedInUserBasedOnId(this, loginCode, loginPassword);
    }

    public void deleteLoggedInUser() {
        new LoginQueryHelper().deleteLoggedInUser(this);
    }

    public void insertLoggedInUser(LoginModel loginModel) {
        new LoginQueryHelper().insertUser(loginModel, this);
    }

    public void insertUserLoginTime(LoginModel loginModel, String onlineOrOffline, String processName, String status, String currentTime) {
        new LoginQueryHelper().insertLoginTime(loginModel, onlineOrOffline, processName, this, status, currentTime);

    }

    public void updateDistrbutorUploadStatus(String customerCode, String tableName,
                                             String col, String status) {
        new UserDbHelper().updateDistrbutorUploadStatus(this, customerCode, tableName, col, status);
    }

    public void uploadWDFreezerStatus(String distCode, String freezerCapacity, String tableName, String columnUpload, String status) {
        new SyncDBHelper().uploadWDFreezerStatus(this, distCode, freezerCapacity, tableName, columnUpload, status);
    }

    public void uploadWDStorageStatus(String distCode, String tableName, String columnUpload, String status) {
        new SyncDBHelper().uploadWDStorageStatus(this, distCode, tableName, columnUpload, status);
    }

    public List<RetailerVO> getCustomerFromDB() {
        return new UserDbHelper().fetchCustomerDetails(this);
    }

    public boolean updatePOStatus(String invoiceNo) {
        return new OrderBookingHelper().updatePOStatus(this, invoiceNo);

    }

    public List<SalesmanMasterModel> getAllSalemansForUpload(String distributorCode) {
        return new UserDbHelper().getAllSalemansForUpload(this, distributorCode);
    }

    public List<SalesmanMasterModel> getSalemanKYC(String distributorCode) {
        return new UserDbHelper().getSalesmanKYC(this, distributorCode);
    }

    public List<SalesmanMasterModel> getSalesmanLobMapping(String distributorCode) {
        return new UserDbHelper().getSalesmanLobMapping(this, distributorCode);
    }

    public List<SalesmanMasterModel> getSalesmanRouteMapping(String distributorCode) {
        return new UserDbHelper().getSalesmanRouteMapping(this, distributorCode);
    }

    public void updateUploadStatus(String tableName, String uploadStatus,
                                   String[] parameters, String[] columns) {
        new UserDbHelper().updateUploadStatus(this, tableName, uploadStatus, parameters, columns);
    }

    public String getDistrBalance(String tableName, String columnName, String whereColumnName, String distrCode) {
        return new DashboardHelper().getDistrBalance(this, tableName, columnName, whereColumnName, distrCode);
    }

    public List<CodeGeneratorModel> getAllGeneratedCodesForUpload(String distCode, String tableName) {
        return new CodeGeneratorQueryHelper().getAllGeneratedCodesForUpload(this, distCode, tableName);
    }

    public List<KycModel> getKYCMasterList() {
        return new UserDbHelper().getKYCMaster(this);
    }

    public String getPrefixForScreen(String screenNameSalesman) {
        return new UserDbHelper().getPrefixForScreen(this, screenNameSalesman);
    }

    public RetailerVO getCustomerDetailsFromDB(String distCode, String cmpCode, String customerCode) {
        return new RetailerDetailHelper().getCustomerDetail(this, cmpCode, distCode, customerCode);
    }

    public CustomerModel getCustomerDetails(String distCode, String cmpCode, String customerCode) {
        return new RetailerDetailHelper().getCustomerDetails(this, cmpCode, distCode, customerCode);
    }

    public List<AddressModel> getCustomerShipAddrList(String distCode, String cmpCode, String customerCode) {
        return new RetailerDetailHelper().getCustomerShipAddrList(this, cmpCode, distCode, customerCode);
    }

    public List<RouteModel> getCustomerRouteList(String distCode, String cmpCode, String customerCode) {
        return new RetailerDetailHelper().getCustomerRouteList(this, cmpCode, distCode, customerCode);
    }

    public void deleteCustomerDetail(String cmpCode, String distCode, String customerCode) {
        new RetailerDetailHelper().deleteCustomerDetail(this, cmpCode, distCode, customerCode);
    }

    public void deleteCustomerRoute(String cmpCode, String distCode, String customerCode) {
        new RetailerDetailHelper().deleteCustomerRoute(this, cmpCode, distCode, customerCode);
    }

    public void deleteCustomerShipAddressDetail(String cmpCode, String distCode, String customerCode) {
        new RetailerDetailHelper().deleteCustomerShipAddressDetail(this, cmpCode, distCode, customerCode);
    }

    public void deleteBankDetail(String cmpCode, String distCode, String customerCode) {
        new RetailerDetailHelper().deleteBankDetail(this, cmpCode, distCode, customerCode);
    }

    public List<ParentMapModel> loadParentMap(String cmpCode, String distrCode, String relationStatus) {

        return new RetailerDetailHelper().loadParentMap(this, cmpCode, distrCode, relationStatus);
    }

    public List<ProFilterModel> getLOBFilterData(String cmpCode) {
        return new DistrPurchaseHelper().getLOBDataFromDB(this, cmpCode);
    }

    public List<GeoHierModel> loadGeography(String cmpCode, String geoLevel) {
        return new RetailerDetailHelper().getGeographyLevel(this, cmpCode, geoLevel);
    }

    public String getGeoHierLevelCode(String cmpCode) {
        return new RetailerDetailHelper().getGeoHierLevelCode(this, cmpCode);
    }

    public GeoHierModel loadGeographyPath(String geoCode) {
        return new RetailerDetailHelper().loadGeographyPath(this, geoCode);
    }

    public List<ProductMasterModel> fetchAllBatchProducts() {
        return new ProductHelper().fetchAllBatchProducts(this);
    }

    public List<ProdBatchModel> fetchAllBatchesOfProduct(String productCode) {
        return new ProductHelper().fetchAllBatchesOfProduct(this, productCode);
    }

    public List<UomMasterModel> fetchAllUomsOfProduct(String productCode) {
        return new ProductHelper().fetchAllUomsOfProduct(this, productCode);
    }

    public String[] getTaxBreakUpsData(String invoiceNo) {
        return new DistrPurchaseHelper().getTaxBreakUps(this, invoiceNo);
    }

    public void updatePurchaseOrderStatus(String cmpCode, String distCode, String invoiceNo, String prodCode, String batchCode, String tableName, String columnUpload, String status) {
        new SyncDBHelper().updatePurchaseOrderStatus(this, cmpCode, distCode, invoiceNo, prodCode, batchCode, tableName, columnUpload, status);
    }

    public String getPasswordBasedOnLoginId(String loginCode) {
        return new LoginQueryHelper().getPasswordBasedOnLoginId(this, loginCode);
    }

    public String getGstStateNameBasedOnCode(String gstCode) {
        return new UserDbHelper().getGstStateNameBasedOnCode(this, gstCode);
    }

    public String getGeoHierPathValue(String geoHierPathCode) {
        return new UserDbHelper().getGeoHierPathValue(this, geoHierPathCode);
    }

    public int getUserCount(String loginCode) {
        return new LoginQueryHelper().getUserCount(this, loginCode);
    }

    public WDInfrastructure getWDInfrastructureStorage(String cmpCode, String distCode) {
        return new WDInfrastructureHelper().getWDInfrastructureStorage(this, cmpCode, distCode);
    }

    public List<WDInfrastructure> getWDInfrastructureFreezer(String cmpCode, String distCode) {
        return new WDInfrastructureHelper().getWDInfrastructureFreezer(this, cmpCode, distCode);
    }

    public List<RouteModel> getRoutesWithRetCount(String distCode) {
        return new RouteQueryHelper().getRoutes(this, distCode);
    }

    public List<OrderBookingVO> fetchPreviousPODetail(String cmpcode, String distCode) {

        return new DistrPurchaseHelper().getPreviousPurchaseOrderHeaderDetail(this, cmpcode, distCode);

    }

    public List<OrderBookingVO> fetchPreviousPOLineItems(String cmpCode, String distCode, String invoiceNo) {
        return new DistrPurchaseHelper().fetchPreviousPurchaseOrderLineItem(this, cmpCode, distCode, invoiceNo);
    }

    public boolean isRequestDataAvailable(String tableName) {
        return new UserDbHelper().isDataAvailableInDB(this, tableName);
    }

    public String getDataByTagName(String tagName) {
        return new UserDbHelper().isfetchDataByTagName(this, tagName);
    }

    public void insertPreviousPOValues(List<PreviousPOModel> stockOnHandModels) {
        new DistrPurchaseHelper().insertPreviousPurchaseOrderData(this, stockOnHandModels);
    }

    public void insertSyncProductsAndOtherMastersData(Sync sync, IDbCallback<Boolean> callback) {
        new SyncDBHelper().syncDataInsertProductUomTaxBranchBatchMaster(sync, this, callback);
    }

    public List<SalesmanMasterModel> loadSalesmanData(String cmpCode, String distrCode, boolean state) {
        return new UserDbHelper().getSalesmanDataFromDB(this, cmpCode, distrCode, state);
    }

    public List<RouteModel> loadRouteData(String cmpCode, String distrCode, String salesmanCode) {
        return new RouteQueryHelper().getRouteDataFromDB(this, cmpCode, distrCode, salesmanCode);
    }

    public List<RetailerVO> loadRetailerData(String cmpCode, String distrCode, String routeCode) {
        return new UserDbHelper().getRetailerData(this, cmpCode, distrCode, routeCode);
    }

    public List<RetailerVO> loadRetailerDataFromDB(String cmpCode, String distrCode) {
        return new UserDbHelper().getRetailerDataFromDB(this, cmpCode, distrCode);
    }

    public List<SalesmanRouteCoveragePlanModel> getAllRoutesForSalesman(String cmpCode, String salesmanCode) {
        return new RouteQueryHelper().getAllRoutesForSalesman(this, cmpCode, salesmanCode);
    }

    public List<SalesmanRouteCoveragePlanModel> getAllRouteCoveragePlansForSalesman(String cmpCode, String distrCode, String salesmanCode) {
        return new RouteQueryHelper().getAllRouteCoveragePlansForSalesman(this, cmpCode, distrCode, salesmanCode);
    }

    public List<SalesmanRouteCoverageModel> getAllRouteCoveragesForSalesman(String cmpCode, String distrCode, String salesmanCode, String confirm) {
        return new RouteQueryHelper().getAllRouteCoveragesForSalesman(this, cmpCode, distrCode, salesmanCode, confirm);
    }

    public boolean insertSalesmanRouteCoveragePlans(List<SalesmanRouteCoveragePlanModel> salesmanRouteCoverageModels) {
        return new RouteQueryHelper().insertSalesmanRouteCoveragePlans(this, salesmanRouteCoverageModels);
    }

    public boolean insertSalesmanRouteCoveragePlansOnConflict(List<SalesmanRouteCoveragePlanModel> salesmanRouteCoverageModels) {
        return new RouteQueryHelper().insertSalesmanRouteCoveragePlansOnConflict(this, salesmanRouteCoverageModels);
    }

    public boolean insertSalesmanRouteCoverages(List<SalesmanRouteCoverageModel> salesmanRouteCoverageModels) {
        return new RouteQueryHelper().insertSalesmanRouteCoverages(this, salesmanRouteCoverageModels);
    }

    public void deleteRouteCoveragePlanBasedOnSalesman(List<SalesmanRouteCoveragePlanModel> salesmanRouteCoverageModels) {
        new RouteQueryHelper().deleteRouteCoveragePlanBasedOnSalesman(this, salesmanRouteCoverageModels);
    }


    public void deleteRouteCoverageBasedOnSalesman(String cmpCode, String distrCode, String salesmanCode) {
        new RouteQueryHelper().deleteRouteCoverageBasedOnSalesman(this, cmpCode, distrCode,
                salesmanCode);
    }

    public boolean isDayCoverageAlreadyExists(String cmpCode, String salesmanCode, String dayName) {
        return new RouteQueryHelper().isDayCoverageAlreadyExists(this, cmpCode, salesmanCode, dayName);
    }

    public List<SalesmanMasterModel> loadCustomerSalesmanData(String customerCode) {
        return new UserDbHelper().getCustomerSalesmanDataFromDB(this, customerCode);
    }

    public List<RouteModel> loadCustomerRouteData(String customerCode, String salesmanCode) {
        return new RouteQueryHelper().getCustomerRouteDataFromDB(this, customerCode, salesmanCode);
    }

    public RetailerVO getRouteAndSalesManCount(String customerCode) {
        return new UserDbHelper().getRouteAndSalesManCount(this, customerCode);
    }

    public List<RouteModel> getSalesmanAllRoutes(String cmpCode, String distrCode, String salesmanCode) {

        return dashboardHelper.getSalesmanAllRoutes(this, cmpCode, distrCode, salesmanCode);

    }

    public void insertRouteSequencingData(String cmpCode, String distrCode, String routeCode, String salesmanCode, List<RetailerVO> voList) {
        new RouteQueryHelper().insertRouteSequencingInDB(this, cmpCode, distrCode, routeCode, salesmanCode, voList);
    }

    public void deleteRouteSeqIfExist(String cmpCode, String distrCode, String routeCode, String salesmanCode, List<RetailerVO> voList) {
        new RouteQueryHelper().deleteRouteSeqById(this, cmpCode, distrCode, routeCode, salesmanCode, voList);
    }

    public List<OrderBookingVO> getBillingProductsFromDB(String cmpCode, String distrCode, String salesmanCode, String routecode, String retrCode) {
        return new OrderBookingHelper().getBillingProducts(this, cmpCode, distrCode, salesmanCode, routecode, retrCode);
    }

    public List<OrderBookingVO> getBillingProductListAndFreeProdFromDB(String prefDistrcode,
                                                                       String prefSalesmancode,
                                                                       String retrCode, String className) {

        return new OrderBookingHelper().getBillingProductListAndFreeProdFromDB(this, prefDistrcode, prefSalesmancode, retrCode, className);
    }

    public void updateCustomerRouteSequencing(String cmpCode, String distrCode, String routeCode, List<RetailerVO> voList) {

        new RouteQueryHelper().updateRouteSequencingInDB(this, cmpCode, distrCode, routeCode, voList);


    }

    public PendingVO getCustomerByCode(String retailerCode) {

        return new RetailerDetailHelper().getCustomerByCode(this, retailerCode);

    }

    public List<PendingCollection> getCustomerCollectionAmount(String cmpCode, String distrCode, String salesmanCode, String routeCode, String customerCode) {
        return new OrderBookingHelper().getCustomerCollectionAmount(this, cmpCode, distrCode, salesmanCode, routeCode, customerCode);
    }

    public void updateConfirmInSalesmanRouteCoverages(List<SalesmanRouteCoverageModel> salesmanRouteCoverageModels) {
        new RouteQueryHelper().updateConfirmInSalesmanRouteCoverages(this, salesmanRouteCoverageModels);
    }

    public void updatePasswordChangedInLogin(String loginCode, String loginPassword) {
        new LoginQueryHelper().updatePasswordChangedInLogin(this, loginCode, loginPassword);
    }

    public void updateStockOnHand(List<OrderBookingVO> purchaseReceiptList) {
        new DistrPurchaseHelper().updateStockOnHand(this, purchaseReceiptList);
    }

    public List<LogisticStockModel> fetchLogisticMaterialStockBasedOnInvoice(String cmpCode, String invoiceCode) {
        return new ProductHelper().fetchLogisticMaterialStockBasedOnInvoice(this, cmpCode, invoiceCode);
    }

    public List<LogisticStockModel> fetchAllLogisticMaterialStock(String cmpCode, String distrCode) {
        return new ProductHelper().fetchAllLogisticMaterialStock(this, cmpCode, distrCode);
    }

    public void updateLogisticMaterialStock(List<LogisticStockModel> logisticStockModels) {
        new ProductHelper().updateLogisticMaterialStock(this, logisticStockModels);
    }

    public List<PurchaseInvoiceModel> getPurchaseOrderBookingLineItemForUpload() {
        return new DistrPurchaseHelper().getPurchaseOrderBookingLineItem(this);
    }

    public List<LogisticStockModel> getLogisticStockForUpload() {
        return new ProductHelper().getLogisticStockForUpload(this);
    }


    public List<OrderBookingVO> getAllProductsFromAuto(String distrcode, String salesmancode, String customerCode) {

        return new OrderBookingHelper().getAppProducts(this, distrcode, salesmancode, customerCode);

    }

    public void updateCustomerUploadStatus(String collectionsUploaded, String state, String cmpCode, String distCode, String customerCode) {

        new OrderBookingHelper().updateRetailerUploadStatus(this, collectionsUploaded, state, cmpCode, distCode, customerCode);
    }

    public void insertInvoiceHeader(BillingInfoVO billingInfoVO) {
        new OrderBookingHelper().insertVoiceHeader(this, billingInfoVO);
    }

    public String getGoDownCode(String cmpCode, String distrCode) {
        return new OrderBookingHelper().getGoDownCode(this, cmpCode, distrCode);
    }

    public BillingInfoVO getInvoiceHeader(String invoiceNo) {
        return new OrderBookingHelper().getInvoiceHeader(this, invoiceNo);
    }

    public void deleteInvoiceHeader(String cmpCode, String distrCode, String invoiceNum) {
        new OrderBookingHelper().deleteInvoiceHeader(this, cmpCode, distrCode, invoiceNum);
    }

    public List<OrderBookingVO> getBilledProductsFromDB(String distCode, String salesmanCode, String routeCode, String retailerCode, String invoiceNo, String completeFlag) {

        return new OrderBookingHelper().getBilledProductsFromDataBase(this, distCode, salesmanCode, routeCode, retailerCode, invoiceNo, completeFlag);

    }

    public List<OrderBookingVO> fetchBillingSummaryProduct(String cmpCode, String distCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo) {
        return new OrderBookingHelper().fetchBillingSummaryProduct(this, cmpCode, distCode, salesmanCode, routeCode, retlrCode, invoiceNo);

    }

    public void updateInvoiceHeaderDataInDB(BillingInfoVO billingInfoVO, int totCount) {

        new OrderBookingHelper().updateInvoiceHeaderDataInDB(this, billingInfoVO, totCount);
    }

    public OrderBookingVO getBillSummaryFromInvoiceHeader(String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String customerShipCode, String invoiceNo) {
        return new OrderBookingHelper().getBillSummaryFromInvoiceHeader(this, cmpCode, distrCode, salesmanCode, routeCode, retlrCode, customerShipCode, invoiceNo);
    }

    public void updateCashDiscAmt(String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo, String totCashDiscAmt, String latitude, String longitude, String completeFlag) {
        new OrderBookingHelper().updateCashDiscAmt(this, cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, totCashDiscAmt, latitude, longitude, completeFlag);
    }

    public List<OrderBookingVO> fetchTotalInvoiceQty(String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo) {
        return new OrderBookingHelper().fetchTotalInvoiceQty(this, cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo);
    }

    public void updateStockOnHandQty(String cmpCode, String distrCode, boolean status, OrderBookingVO orderBookingVO) {
        new OrderBookingHelper().updateStockOnHandQty(this, cmpCode, distrCode, status, orderBookingVO);
    }

    public String getTotalDueAmount(String cmpCode, String distrCode, String retrCode, String routeCode, String salesmanCode) {
        return new OrderBookingHelper().getTotalDueAmount(this, cmpCode, distrCode, retrCode, routeCode, salesmanCode);
    }


    public List<OrderBookingVO> fetchLineLevelGrossAmt(String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo) {
        return new OrderBookingHelper().fetchLineLevelGrossAmt(this, cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo);
    }

    public void updateLineLevelCashDisc(String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo, Double totCashDiscAmt, OrderBookingVO orderBookingVO, Double totCashDiscPerc, String completeFlag) {
        new OrderBookingHelper().updateLineLevelCashDisc(this, cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, totCashDiscAmt, orderBookingVO, totCashDiscPerc, completeFlag);
    }

//    public List<OrderBookingVO> getOrderBookingDetails(String cmpCode, String distCode, String tableName) {
//
//        return new OrderBookingHelper().getPendingInvoiceHeaderDetail(this, cmpCode, distCode, tableName);
//    }

    public List<OrderBookingVO> getInvoiceLineLevelDetail(String cmpCode, String distrCode, String salesmanCode, String routeCode, String customerCode, String invoiceNo) {
        return new OrderBookingHelper().getInvoiceLineLevelDetail(this, cmpCode, distrCode, salesmanCode, routeCode, customerCode, invoiceNo);
    }

    public List<OrderBookingVO> getOrderProductsFromDB(String cmpCode, String distCode, String invoiceNo) {

        return new OrderBookingHelper().getOrderBookingProductsFromDataBase(this, cmpCode, distCode, invoiceNo);

    }

    public OrderBookingVO loadBillSummaryData(String distCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo) {

        return new RetailerDetailHelper().fetchBillSummaryDataRetailerWise(this, distCode, salesmanCode, routeCode, retlrCode, invoiceNo);
    }

    public List<OrderBookingHeaderModel> fetchReportOrderHeaders(String cmpCode, String distCode) {
        return new OrderBookingHelper().fetchReportOrderHeaders(this, cmpCode, distCode);
    }

    public List<OrderBookingDetailsModel> fetchReportOrderDetails(String cmpCode, String distCode, String orderNo) {
        return new OrderBookingHelper().fetchReportOrderDetails(this, cmpCode, distCode, orderNo);
    }

    public List<SalesmanRouteCoverageModel> getRouteCoverageForUpload() {
        return new RouteQueryHelper().getRouteCoverageForUpload(this);
    }

    public void updateUploadFlagStatus(String tableName, String uploadStatus,
                                       String[] parameters, String[] columns) {
        new UserDbHelper().updateUploadFlagStatus(this, tableName, uploadStatus, parameters, columns);
    }

    public List<StockOnHandModel> getAvailStockOnHandForUpload(String cmpCode, String distCode) {
        return new RetailerDetailHelper().getAvailStockOnHandForUpload(this, cmpCode, distCode);
    }

    public void updateStockOnHandUploadStatus(String cmpCode, String distCode, String prodCode, String batchCode, String tableStockOnHand, String columnUpload, String status) {

        new SyncDBHelper().updateStockOnHandUploadStatus(this, cmpCode, distCode, prodCode, batchCode, tableStockOnHand, columnUpload, status);
    }

    public void deleteBillAdjustment(String cmpCode, String distrCode, String collectionNo, PendingCollection pendingCollection) {
        new OrderBookingHelper().deleteBillAdjustment(this, cmpCode, distrCode, collectionNo, pendingCollection);
    }

    public void insertBillAdjustment(String cmpCode, String distrCode, String collectionNo, PendingCollection pendingCollection) {
        new OrderBookingHelper().insertBillAdjustment(this, cmpCode, distrCode, collectionNo, pendingCollection);
    }

    public void updateInvoiceAdjustBalAmt(String cmpCode, String distrCode, String routeCode, String salesmanCode, String retrCode, List<PendingCollection> pendingCollectionArrayList) {
        new OrderBookingHelper().updateInvoiceAdjustBalAmt(this, cmpCode, distrCode, routeCode, salesmanCode, retrCode, pendingCollectionArrayList);
    }

    public void deleteCollection(String distrCode, String salesmanCode, String routeCode, String retrCode) {
        new OrderBookingHelper().deleteCollection(this, distrCode, salesmanCode, routeCode, retrCode);
    }

    public void insertCollection(String invoiceNo, String routeCode, String retrCode, CollectionVO collectionVO, String remarks, String salesmanCode) {
        new OrderBookingHelper().insertCollection(this, invoiceNo, routeCode, retrCode, collectionVO, remarks, salesmanCode);
    }

    public List<CollectionVO> getCollectionList(String cmpCode, String distCode) {
        return new OrderBookingHelper().getCollectionList(this, cmpCode, distCode);
    }

    public List<PendingCollection> getBillAdjustmentDetail(String cmpCode, String distCode) {
        return new OrderBookingHelper().getBillAdjustmentDetail(this, cmpCode, distCode);
    }

    public void uploadCollection(String cmpCode, String distCode, String collectionNo, String tableName, String columnUpload, String status) {
        new SyncDBHelper().uploadCollection(this, cmpCode, distCode, collectionNo, tableName, columnUpload, status);
    }

    public List<CollectionVO> loadCollection(String distrCode, String salesmanCode, String routeCode, String retrCode) {
        return new OrderBookingHelper().loadCollection(this, distrCode, salesmanCode, routeCode, retrCode);
    }

    public String getCollectionNo(String tableName, String distrCode, String salesmanCode, String routeCode, String retrCode) {
        return new OrderBookingHelper().getCollectionNo(this, tableName, distrCode, salesmanCode, routeCode, retrCode);
    }

    public void uploadBillAdjustment(String cmpCode, String distCode, String collectionNo, String tableName, String uploadFlag, String uploaded) {
        new SyncDBHelper().uploadBillAdjustment(this, cmpCode, distCode, collectionNo, tableName, uploadFlag, uploaded);
    }

    public void updatePendingBillAmount(String cmpCode, String distrCode, String retrCode, String salesmanCode, List<PendingCollection> pendingCollectionArrayList) {
        new OrderBookingHelper().updatePendingBillAmount(this, cmpCode, distrCode, retrCode, salesmanCode, pendingCollectionArrayList);
    }

    public List<PendingBillCollection> getPendingBillCollectionList(String cmpCode, String distCode) {
        return new OrderBookingHelper().getPendingBillCollectionList(this, cmpCode, distCode);
    }

    public void uploadPendingBills(String cmpCode, String distCode, String invoiceNo, String tableName, String uploadFlag, String uploaded) {
        new SyncDBHelper().uploadPendingBills(this, cmpCode, distCode, invoiceNo, tableName, uploadFlag, uploaded);
    }

    public boolean isRateEditForCustomer(String distCode, String retailerCode, String prodCode) {
        return new RetailerDetailHelper().isRateEditableForCustomer(this, distCode, retailerCode, prodCode);
    }

    public String getsupplierStateCode() {
        return new UserDbHelper().getsupplierStateCode(this);
    }

    public List<BillingInfoVO> getAllInvoiceHeader() {
        return new OrderBookingHelper().getAllInvoiceHeader(this);
    }

    public List<OrderBookingVO> getBillingInvoiceDetails(String cmpCode, String distrCode, String invoiceNo) {
        return new OrderBookingHelper().getBillingInvoiceDetails(this, cmpCode, distrCode, invoiceNo);
    }

    public void insertSavedBillingDetails(List<OrderBookingVO> list) {
        new OrderBookingHelper().insertSavedSavedBillingDetails(this, list);
    }

    public void deleteUnconfirmedTransaction(String cmpCode, String distCode, String status) {
        new OrderBookingHelper().deleteUnconfirmedTransaction(this, cmpCode, distCode, status);
    }

    public void updateInvoiceLineLevelTaxAmt(String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo, OrderBookingVO bookingVO) {
        new OrderBookingHelper().updateInvoiceLineLevelTaxAmt(this, cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, bookingVO);
    }

    public List<RetailerVO> loadRetailerDataForRouteSeq(String cmpCode, String distrCode, String routeCode) {
        return new UserDbHelper().getRetailerDataForRouteSeq(this, cmpCode, distrCode, routeCode);
    }

    public void deleteOldSavedInvoiceDetails(String cmpCode, String distrCode, String invoiceNo) {
        new OrderBookingHelper().deleteOldSavedInvoiceDetails(this, cmpCode, distrCode, invoiceNo);
    }

    public void deleteAllSavedBillingDetails(String companyCode, String distrCode, String invoiceNo) {
        new OrderBookingHelper().deleteAllSavedBillingDetails(this, companyCode, distrCode, invoiceNo);
    }

    int getResvSaleableQtyForProduct(String cmpCode, String distrCode, String prodCode, String prodBatchCode) {
        return new OrderBookingHelper().getResvSaleableQtyForProduct(this, cmpCode, distrCode, prodCode, prodBatchCode);
    }

    public boolean isStockAvailableForReceipt(List<OrderBookingVO> receiptList) {
        return new OrderBookingHelper().isStockAvailableForReceipt(this, receiptList);
    }

    public List<SalesReturnVO> loadExistingReturns(String distrcode, String salesmancode, String routecode, String retrCode) {

        return new OrderBookingHelper().getExistingReturns(this, distrcode, salesmancode, routecode, retrCode);
    }

    public List<StockTakeVO> getAllProductsFromAutoSales(String cmpcode) {


        return new OrderBookingHelper().getAppProductsSales(this, cmpcode);

    }

    public List<OrderBookingVO> getStockData(String transType, String stockType) {
        return new OrderBookingHelper().getStockData(this, transType, stockType);
    }

    public void insertStockAdjustmentHeaderDetail(String cmpCode, String distrCode, String goDownCode, String stockAdjustNo, String transType, String remarks) {
        new OrderBookingHelper().insertStockAdjustmentHeaderDetail(this, cmpCode, distrCode, goDownCode, stockAdjustNo, transType, remarks);
    }

    public void insertStockAdjustmentLineLevelDetail(String cmpCode, String distrCode, String goDownCode, String stockAdjustNo, OrderBookingVO qtyUpdatedOrderBooking, String stockType) {
        new OrderBookingHelper().insertStockAdjustmentLineLevelDetail(this, cmpCode, distrCode, goDownCode, stockAdjustNo, qtyUpdatedOrderBooking, stockType);
    }

    public List<OrderBookingVO> getStockAdjustmentHeaderDetail(String cmpCode, String distCode) {
        return new OrderBookingHelper().getStockAdjustmentHeaderDetail(this, cmpCode, distCode);
    }

    public List<OrderBookingVO> getStockAdjustmentDetail(String distrCode, String goDownCode, String stockAdjustNo) {
        return new OrderBookingHelper().getStockAdjustmentDetail(this, distrCode, goDownCode, stockAdjustNo);
    }

    public void uploadStockAdjustmentHeader(String cmpCode, String distrCode, String godownCode, String stockAdjustNo, String tableName, String uploadFlag, String uploaded) {
        new SyncDBHelper().uploadStockAdjustmentHeader(this, cmpCode, distrCode, godownCode, stockAdjustNo, tableName, uploadFlag, uploaded);
    }

    public void uploadStockAdjustmentDetail(String cmpCode, String distrCode, String godownCode, String stockAdjustNo, String tableName, String uploadFlag, String uploaded) {
        new SyncDBHelper().uploadStockAdjustmentDetail(this, cmpCode, distrCode, godownCode, stockAdjustNo, tableName, uploadFlag, uploaded);
    }

    public List<StockTakeVO> getProductBatchCode(String cmpCode, String prodCode) {
        return new OrderBookingHelper().getProductBatchCode(this, cmpCode, prodCode);
    }

    public String getSalesReturnNo(String distrCode, String salesmanCode, String routeCode, String retrCode, String state) {
        return new OrderBookingHelper().getSalesReturnNo(this, distrCode, salesmanCode, routeCode, retrCode, state);
    }

    public void insertSalesReturnHeaderDetails(String distrCode, String salesNo, String salesmanCode, String routeCode, String retrCode, String remarks) {
        new OrderBookingHelper().insertSalesReturnHeaderDetails(this, distrCode, salesNo, salesmanCode, routeCode, retrCode, remarks);
    }

    public void insertSalesReturnLineDetails(String distrCode, String salesNo, SalesReturnVO salesReturnVO) {
        new OrderBookingHelper().insertSalesReturnLineDetails(this, distrCode, salesNo, salesReturnVO);
    }

    public void deleteSalesReturnHeader(String distrCode, String salesmanCode, String routeCode, String retrCode, String status) {
        new OrderBookingHelper().deleteSalesReturnHeader(this, distrCode, salesmanCode, routeCode, retrCode, status);
    }

    public void deleteSalesReturnLineDetails(String distrCode, String salesNo, String status) {
        new OrderBookingHelper().deleteSalesReturnLineDetails(this, distrCode, salesNo, status);
    }

    public void deleteSalesReturnTableById(String distrCode, String salesmanCode, String routeCode, String retrCode, String deleteSalesReturnNo, SalesReturnVO salesReturnVO, String status) {
        new OrderBookingHelper().deleteSalesReturnTableById(this, distrCode, salesmanCode, routeCode, retrCode, deleteSalesReturnNo, salesReturnVO, status);
    }

    public List<SalesReturnVO> getSalesReturnHeaderDetail(String cmpCode, String distCode) {
        return new OrderBookingHelper().getSalesReturnHeaderDetail(this, cmpCode, distCode);
    }

    public List<SalesReturnVO> getSalesReturnDetail(String distrCode, String salesreturnNo) {
        return new OrderBookingHelper().getSalesReturnDetail(this, distrCode, salesreturnNo);
    }

    public void uploadSalesReturnHeader(String cmpCode, String distrCode, String salesReturnNo, String tableName, String uploadFlag, String uploaded) {
        new SyncDBHelper().uploadSalesReturnHeader(this, cmpCode, distrCode, salesReturnNo, tableName, uploadFlag, uploaded);
    }

    public void uploadSalesReturnLineDetail(String cmpCode, String distrCode, String salesReturnNo, String tableName, String uploadFlag, String uploaded) {
        new SyncDBHelper().uploadSalesReturnLineDetail(this, cmpCode, distrCode, salesReturnNo, tableName, uploadFlag, uploaded);
    }

    public void updateStockOnHandAdjusmentQty(String cmpCode, String distrCode, String goDownCode, OrderBookingVO orderBookingVO, String stockType, String transType) {
        new OrderBookingHelper().updateStockOnHandAdjusmentQty(this, cmpCode, distrCode, goDownCode, orderBookingVO, stockType, transType);
    }

    public void updateStockonHandSalesReturn(String cmpCode, String distrCode, String goDownCode, SalesReturnVO salesReturnVO, boolean state) {
        new OrderBookingHelper().updateStockonHandSalesReturn(this, cmpCode, distrCode, goDownCode, salesReturnVO, state);
    }

    public double getConversionUomValue(double enteredQty, String uomCode, String prodCode) {
        return new OrderBookingHelper().getConversionUomValue(this, enteredQty, uomCode, prodCode);
    }

    public List<OrderBookingVO> getStockReportData(boolean isCheck) {
        return new OrderBookingHelper().getStockReportData(this, isCheck);
    }

    public List<OrderBookingHeaderModel> fetchReportOrderHeadersForCustomer(String cmpCode, String distCode, String salesmanCode, String routeCode, String customerCode) {
        return new OrderBookingHelper().fetchReportOrderHeadersForCustomer(this, cmpCode, distCode, salesmanCode, routeCode, customerCode);
    }

    public String isInvoiceGeneratedForOrder(String cmpCode, String distCode, String salesmanCode, String routeCode, String customerCode, String orderNo) {
        return new OrderBookingHelper().isInvoiceGeneratedForOrder(this, cmpCode, distCode, salesmanCode, routeCode, customerCode, orderNo);
    }

    public List<OrderBookingVO> fetchPreviousOrderInvoiceDetails(String cmpCode, String distrCode, String orderNo) {
        return new OrderBookingHelper().fetchPreviousOrderInvoiceDetails(this, cmpCode, distrCode, orderNo);
    }

    public String getCustomerShipAddress(String cmpCode, String distCode, String customerCode) {
        return new RetailerDetailHelper().getCustomerShipAddress(this, cmpCode, distCode, customerCode);
    }

    public void deleteInvoiceDetails(String cmpCode, String distrCode, String invoiceNum) {
        new OrderBookingHelper().deleteInvoiceDetails(this, cmpCode, distrCode, invoiceNum);
    }

    public List<ProdBatchModel> fetchAllOtherBatchesOfProduct(String prodCode) {
        return new ProductHelper().fetchAllOtherBatchesOfProduct(this, prodCode);
    }

    public void insertCustomerSchemeProductDetails(List<SchemeModel> schemeProductDetail) {
        new SyncDBHelper().insertCustomerSchemeProductDetails(this, schemeProductDetail);
    }

    public List<SchemeModel> getCustomerSchemeProducts(String retailerCode) {
        return new OrderBookingSchemeHelper().getCustomerSchemeProducts(this, retailerCode);
    }

    public List<SchemeDistrBudgetModel> getSchemeBudgetList(String distrCode, String customerCode) {
        return new OrderBookingSchemeHelper().getSchemeBudgetList(this, distrCode, customerCode);
    }

    public void updateSchemeDistrBudget(List<SchemeModel> schemeModels) {
        new OrderBookingHelper().updateSchemeDistrBudget(this, schemeModels);
    }

    public List<SchemeModel> getInvoiceSchemeDetialsList(String salesmanCode, String routeCode, String customerCode, String invoiceNo, String payoutType) {
        return new OrderBookingHelper().getInvoiceSchemeLineItem(this, salesmanCode, routeCode, customerCode, invoiceNo, payoutType);
    }

    public OrderBookingVO getBatchCodeOfFreeProduct(String prodCode, int offerQty, boolean isMandatory) {
        return new OrderBookingHelper().getBatchCodeOfFreeProduct(this, prodCode, offerQty, isMandatory);
    }

    public void updateStockOnHandOfferQty(String prodCode, String prodBatchCode, int offerQty) {
        new OrderBookingHelper().updateStockOnHandOfferQty(this, prodCode, prodBatchCode, offerQty);
    }

    public List<SchemeDistrBudgetModel> getSchemeDistrBudgetForUpload(String distrCode) {
        return new OrderBookingHelper().getSchemeDistrBudgetForUpload(this, distrCode);
    }

    public void updateSchemeDistrBudget(String cmpCode, String distrCode, String schemeCode, String tableName, String uploadFlag, String uploaded) {
        new SyncDBHelper().updateSchemeDistrBudget(this, cmpCode, distrCode, schemeCode, tableName, uploadFlag, uploaded);
    }

    public List<SalesDBCRNoteModel> getSalesDBCRForCustomer(String cmpCode, String distrCode, String customerCode) {
        return new OrderBookingHelper().getSalesDBCRForCustomer(this, cmpCode, distrCode, customerCode);
    }

    public void updateSalesDBCRNote(String cmpCode, String distrCode, String customerCode, SalesDBCRNoteModel salesDBCRNoteModel) {
        new OrderBookingHelper().updateSalesDBCRNote(this, cmpCode, distrCode, customerCode, salesDBCRNoteModel);
    }

    public void updateInvoiceHeaderTotCrNoteAmt(String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo, double totCrNoteAmt) {
        new OrderBookingHelper().updateInvoiceHeaderTotCrNoteAmt(this, cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, totCrNoteAmt);
    }

    public void insertInvoiceCRDBAdjustment(String cmpCode, String distrCode, String invoiceNo, SalesDBCRNoteModel salesDBCRNoteModel) {
        new OrderBookingHelper().insertInvoiceCRDBAdjustment(this, cmpCode, distrCode, invoiceNo, salesDBCRNoteModel);
    }

    public List<SalesDBCRNoteModel> getInvoiceCRDBAdjustmentDetails(String cmpCode, String distrCode) {
        return new OrderBookingHelper().getInvoiceCRDBAdjustmentDetails(this, cmpCode, distrCode);
    }

    public List<SalesDBCRNoteModel> getSalesDBCRNoteDetails(String cmpCode, String distrCode) {
        return new OrderBookingHelper().getSalesDBCRNoteDetails(this, cmpCode, distrCode);
    }

    public void uploadInvoiceCRDBAdjustment(String cmpCode, String distrCode, String invoiceNo, String salesDBCRRefNo, String tableName, String uploadFlag, String uploaded) {
        new SyncDBHelper().uploadInvoiceCRDBAdjustment(this, cmpCode, distrCode, invoiceNo, salesDBCRRefNo, tableName, uploadFlag, uploaded);
    }

    public void uploadSalesDBCRNoteDetails(String cmpCode, String customerCode, String salesDBCRRefNo, String tableName, String uploadFlag, String uploaded) {
        new SyncDBHelper().uploadSalesDBCRNoteDetails(this, cmpCode, customerCode, salesDBCRRefNo, tableName, uploadFlag, uploaded);
    }

    public List<OrderBookingVO> fetchAllTaxofProduct(String prodCode) {
        return new ProductHelper().fetchAllTaxofProduct(this, prodCode);
    }

    public List<OrderBookingVO> getDelProdById(String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo, OrderBookingVO orderBookingVO) {
        return new OrderBookingHelper().getDelProdById(this, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, orderBookingVO);
    }

    public void toDeleteAndUpdateBillingProduct(String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo, OrderBookingVO orderBookingVO) {
        new OrderBookingHelper().toDeleteAndUpdateBillingProduct(this, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, orderBookingVO);
    }

    public void updateBillingAppliedSchemesStatus(String distrCode, String salesmanCode, String routeCode, String customerCode, String invoiceNo) {
        new OrderBookingHelper().updateBillingAppliedSchemesStatus(this, distrCode, salesmanCode, routeCode, customerCode, invoiceNo);
    }

    public List<SchemeModel> getSchemeFreeProducts(String cmpCode, String schemeCode, String slabNo) {
        return new OrderBookingHelper().getSchemeFreeProducts(this, cmpCode, schemeCode, slabNo);
    }

    public void insertFreeProductInInvioiceDetails(String routeCode, String salesmanCode, String retailerCode, String currentYearMonthDate, List<OrderBookingVO> freeProds, String invoiceNo, String status) {


        new OrderBookingHelper().insertFreeProductInInvioiceDetails(this, routeCode, retailerCode, salesmanCode, currentYearMonthDate, freeProds, invoiceNo, status);


    }

    public List<SalesDBCRNoteModel> fetchCreditNoteForInvoice(String cmpCode, String distrCode, String invoiceNo) {
        return new OrderBookingHelper().fetchCreditNoteForInvoice(this, cmpCode, distrCode, invoiceNo);
    }

    public LoginModel getSyncDateTime(String cmpCode, String distCode) {
        return new RetailerDetailHelper().getSyncDateTime(this, cmpCode, distCode);
    }

    public List<SchemeModel> fetchSchemeSlabProductList(SchemeModel schemeModelList) {
        return new OrderBookingHelper().fetchSchemeSlabProductList(this, schemeModelList);
    }

    public List<OrderBookingVO> getlinelevelTaxDetail(String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo) {
        return new OrderBookingHelper().getlinelevelTaxDetail(this, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo);
    }

    public void uploadSyncLog(TimeCaptureModel sync) {
        new SyncDBHelper().updateSyncLogData(sync, this);
    }

    public void deleteSalesmanKycByCode(String salesmanCode, String kycType) {

        new UserDbHelper().deleteSalesmanKycByCode(this, salesmanCode, kycType, TABLE_SALESMAN_MASTER_KYC);

    }

    public List<OrderBookingVO> getAllProductsForOpeningStock(String cmpCode, String distrCode, String openStockNo) {
        return new OrderBookingHelper().getAppProductsForOpeningStock(this, cmpCode, distrCode, openStockNo);
    }

    public void deleteOpenStockList(String cmpCode, String distrCode, String openStockNo, String tableName) {
        new OrderBookingHelper().deleteOpenStockList(this, cmpCode, distrCode, openStockNo, tableName);
    }

    public void insertOpenStockHeaderList(String cmpCode, String distrCode, String openStockNo, String remarks, String tableName, String completeFlag, String goDownCode) {
        new OrderBookingHelper().insertOpenStockHeaderList(this, cmpCode, distrCode, openStockNo, remarks, tableName, completeFlag, goDownCode);
    }

    public void insertOpenStockDetailList(String cmpCode, String distrCode, String openStockNo, OrderBookingVO qtyUpdatedOrderBooking, String tableName, String completeFlag, String goDownCode) {
        new OrderBookingHelper().insertOpenStockDetailList(this, cmpCode, distrCode, openStockNo, qtyUpdatedOrderBooking, tableName, completeFlag, goDownCode);
    }

    public void updateOpenStockHeaderList(String cmpCode, String distrCode, String openStockNo, String remarks, String tableName, String completeFlag) {
        new OrderBookingHelper().updateOpenStockHeaderList(this, cmpCode, distrCode, openStockNo, remarks, tableName, completeFlag);
    }

    public void updateOpenStockDetailList(String cmpCode, String distrCode, String openStockNo, OrderBookingVO qtyUpdatedOrderBooking, String tableName, String completeFlag) {
        new OrderBookingHelper().updateOpenStockDetailList(this, cmpCode, distrCode, openStockNo, qtyUpdatedOrderBooking, tableName, completeFlag);
    }

    public List<OrderBookingVO> fetchOpeningStockSummary(String cmpCode, String distrCode, String openStockNo) {
        return new OrderBookingHelper().fetchOpeningStockSummary(this, cmpCode, distrCode, openStockNo);
    }

    public List<OrderBookingVO> getStockListFromDB(String cmpCode, String distrCode, String openStockNo) {
        return new OrderBookingHelper().getStockListFromDB(this, cmpCode, distrCode, openStockNo);
    }

    public void deleteOpenStockById(String cmpCode, String distrCode, String openStockNo, OrderBookingVO orderBookingVO, String tableName) {
        new OrderBookingHelper().deleteOpenStockById(this, cmpCode, distrCode, openStockNo, orderBookingVO, tableName);
    }

    public void updateStockonHandOpeningStock(String cmpCode, String distrCode, OrderBookingVO orderBookingVO) {
        new OrderBookingHelper().updateStockonHandOpeningStock(this, cmpCode, distrCode, orderBookingVO);
    }

    public void deletePendingOpenStockList(String cmpCode, String distrCode, String tableName) {
        new OrderBookingHelper().deletePendingOpenStockList(this, cmpCode, distrCode, tableName);
    }

    public void removeExistingDistrReportTableInfo() {
        new SyncDBHelper().removeExistingDistrReportTableInfo(this);
    }

    public void insertSyncDistrReportData(Sync sync, IDbCallback<Boolean> callback) {
        new SyncDBHelper().insertSyncDistrReportData(sync, this, callback);
    }

    public void deletePurchaseOrderByInvoiceNo(String invoiceNo) {

        new DistrPurchaseHelper().deletePurchaseOrderByInvoiceNo(this, invoiceNo);
    }

    public void deletePurchaseOrderIfNotComplete() {

        new DistrPurchaseHelper().deletePurchaseOrderIfNotComplete(this);
    }

    public String getEncryptedDataByProcessName(String tagName) {
        return new UserDbHelper().getEncryptedDataByProcessName(this, tagName);
    }

    public void insertSalesReportDetail(List<SalesReportModel> bookingVOS) {
        new InventoryHelper().insertSalesReportDetail(this, bookingVOS);
    }

    public String getOpenStockNo(String cmpCode, String distrCode, String transType, String uploadFlag) {
        return new OrderBookingHelper().getOpenStockNo(this, cmpCode, distrCode, transType, uploadFlag);
    }

    public List<RetailerVO> loadRetailerDataBilling(String cmpCode, String distrCode, String routeCode) {
        return new UserDbHelper().getRetailerDataBilling(this, cmpCode, distrCode, routeCode);
    }

    public List<RetailerVO> loadRetailerDataBillingFromDB(String cmpCode, String distrCode) {
        return new UserDbHelper().getRetailerDataBillingFromDB(this, cmpCode, distrCode);
    }

    public RetailerVO getRouteAndSalesManCountBilling(String customerCode) {
        return new UserDbHelper().getRouteAndSalesManCountBilling(this, customerCode);
    }

    public List<OrderBookingVO> getStockReportLineItems(String cmpCode, String distrCode, String prodCode) {
        return new OrderBookingHelper().getStockReportLineItems(this, cmpCode, distrCode, prodCode);
    }

    public List<SchemeModel> getAllSchemeDefinition(String cmpCode) {
        return new OrderBookingHelper().getAllSchemeDefinition(this, cmpCode);
    }

    public List<SalesReportModel> fetchAllSalesReportDetail(String distrCode) {
        return new InventoryHelper().fetchAllSalesReportDetail(this, distrCode);
    }

    public List<OrderBookingHeaderModel> fetchAllOrderBookingReport(String distrCode) {
        return new InventoryHelper().fetchAllOrderBookingReport(this, distrCode);
    }

    public boolean isTaxAvailable(String prodCode) {
        return new OrderBookingHelper().isTaxAvailable(this, prodCode);
    }

    public List<ReasonVO> getFilterDataByName(String catName) {

        return new InventoryHelper().fetchFilterDataByName(this, catName);
    }

    public boolean isLogisticAvailable(String invoiceNo) {
        return new ProductHelper().isLogisticAvailable(this, invoiceNo);
    }

    public OrderBookingVO getTaxForProducts(String prodCode) {
        return new OrderBookingHelper().getTaxForProducts(this, prodCode);
    }

    public void insertSalesDayWiseReportDetail(List<MTDReportModel> dayWiseReportModel) {
        new InventoryHelper().insertSalesDayWiseReportDetail(this, dayWiseReportModel);
    }

    public void insertSalesWeekWiseReportDetail(List<MTDReportModel> weekWiseReportModel) {
        new InventoryHelper().insertSalesWeekWiseReportDetail(this, weekWiseReportModel);
    }

    public void insertSalesCustomerWiseReportDetail(List<MTDReportModel> customerWiseReportModel) {
        new InventoryHelper().insertSalesCustomerWiseReportDetail(this, customerWiseReportModel);
    }

    public void insertSalesChannelWiseReportDetail(List<MTDReportModel> channelWiseReportModel) {
        new InventoryHelper().insertSalesChannelWiseReportDetail(this, channelWiseReportModel);
    }

    public List<MTDReportModel> getCustomerWiseReport() {
        return new InventoryHelper().getCustomerWiseReport(this);
    }

    public List<MTDReportModel> getDayWiseReport() {
        return new InventoryHelper().getDayWiseReport(this);
    }

    public List<MTDReportModel> getWeekWiseReport() {
        return new InventoryHelper().getWeekWiseReport(this);
    }

    public List<MTDReportModel> getChannelWiseReport() {
        return new InventoryHelper().getChannelWiseReport(this);
    }

    public List<MTDReportModel> loadCustomerChannel(String distrCode) {
        return new InventoryHelper().loadCustomerChannel(this, distrCode);
    }

    public List<MTDReportModel> loadCustomerSubChannel(String distrCode, String channelCode) {
        return new InventoryHelper().loadCustomerSubChannel(this, distrCode, channelCode);
    }

    public List<MTDReportModel> loadCustomerGroupData(String distrCode, String channelCode) {
        return new InventoryHelper().loadCustomerGroupData(this, distrCode, channelCode);
    }

    public List<MTDReportModel> loadCustomerClassData(String distrCode, String channelCode, String groupCode) {
        return new InventoryHelper().loadCustomerClassData(this, distrCode, channelCode, groupCode);
    }

    public List<MTDReportModel> loadCustomerData(String distrCode) {
        return new InventoryHelper().loadCustomerData(this, distrCode);
    }

    public List<MTDReportModel> loadCustomerWise(String distrCode, String channelCode, String subChannelCode, String groupCode, String classCode) {
        return new InventoryHelper().loadCustomerWise(this, distrCode, channelCode, subChannelCode, groupCode, classCode);
    }

    public void insertPurchaseInvoiceReportDetail(List<PurchaseInvoiceModel> purchaseInvoiceModel) {
        new DistrPurchaseHelper().insertPurchaseInvoiceReportDetail(this, purchaseInvoiceModel);
    }

    public void insertYtdRouteWiseReportDetail(List<YtdSalesReportModel> reportModels) {
        new InventoryHelper().insertYtdRouteWiseReportDetail(this, reportModels);
    }

    public void insertYtdCustomerWiseReportDetail(List<YtdSalesReportModel> reportModels) {
        new InventoryHelper().insertYtdCustomerWiseReportDetail(this, reportModels);
    }

    public void insertYtdSalesmanWiseReportDetail(List<YtdSalesReportModel> reportModels) {
        new InventoryHelper().insertYtdSalesmanWiseReportDetail(this, reportModels);
    }

    public void insertYtdProductWiseReportDetail(List<YtdSalesReportModel> reportModels) {
        new InventoryHelper().insertYtdProductWiseReportDetail(this, reportModels);
    }

    public void insertMtdSalesmanWiseReportDetail(List<MTDReportModel> reportModels) {
        new InventoryHelper().insertMtdSalesmanWiseReportDetail(this, reportModels);
    }

    public void insertMtdSalesRouteWiseReportDetail(List<MTDReportModel> reportModels) {
        new InventoryHelper().insertMtdSalesRouteWiseReportDetail(this, reportModels);
    }

    public List<MTDReportModel> getMtdSalesmanWiseReport() {
        return new InventoryHelper().getMtdSalesmanWiseReport(this);
    }

    public List<MTDReportModel> getMtdRouteWiseReport() {
        return new InventoryHelper().getMtdRouteWiseReport(this);
    }

    public void insertMtdProdHierProdWiseReportDetail(List<MTDReportModel> reportModels) {
        new InventoryHelper().insertMtdProdHierProdWiseReportDetail(this, reportModels);
    }

    public List<MTDReportModel> getMtdProdHierProdWiseReport() {
        return new InventoryHelper().getMtdProdHierProdWiseReport(this);
    }

    public ProFilterModel getHierLevelByName(String levelName) {
        return new ProductHelper().getHierLevelByName(this, levelName);
    }

    public String getChannelName(String channelCode) {
        return new InventoryHelper().getChannelName(this, channelCode);
    }

    public List<PurchaseInvoiceModel> fetchPurchaseInvoiceReportDetail(String distrCode) {
        return new DistrPurchaseHelper().fetchPurchaseInvoiceReportDetail(this, distrCode);
    }

    public List<MTDReportModel> getUniqueProductLevels(String[] levels, String codeColumn, String nameColumn, String groupBy, String where) {
        return new ProductHelper().getUniqueProductLevels(this, levels, codeColumn, nameColumn, groupBy, where);
    }

    public List<MTDReportModel> getFilteredProductLevels(String[] levels, String where) {
        return new InventoryHelper().getFilteredProductLevels(this, levels, where);
    }

    public String getBrandName(String brandCode, int brandLevel) {
        return new InventoryHelper().getBrandName(this, brandCode, brandLevel);
    }

    public List<DistrReportModel> loadProcessData(String cmpCode) {
        return new InventoryHelper().loadProcessData(this, cmpCode);
    }

    public List<DistrReportModel> loadProcessMenu(String cmpCode, String processId) {
        return new InventoryHelper().loadProcessMenu(this, cmpCode, processId);
    }

    public List<DistrReportModel> loadProcessSubMenu(String cmpCode, String processId, String columnGroup) {
        return new InventoryHelper().loadProcessSubMenu(this, cmpCode, processId, columnGroup);
    }

    public void removeExistingOnlineReportTableInfo() {
        new SyncDBHelper().removeExistingOnlineReportTableInfo(this);
    }

    public String getNotifDescriptionBasedRetailer(String cmpCode, String channelCode, String subChannelCode, String groupCode, String classCode) {
        return dashboardHelper.getNotifDescriptionBasedRetailer(this, cmpCode, channelCode, subChannelCode, groupCode, classCode);
    }

    public List<DistrReportModel> getHashMapKey() {

        return new InventoryHelper().getHashMapKey(this);
    }

    public OrderBookingVO loadSalesReturnDetails(String distCode, String salesmanCode, String routeCode, String retrCode, String salesNo) {
        return new RetailerDetailHelper().fetchSalesReturnDetails(this, distCode, salesmanCode, routeCode, retrCode, salesNo);
    }

    public boolean isRequestCustomerDataAvailable(String distCode, String cmpCode, String customerCode) {
        return new RetailerDetailHelper().isRequestCustomerDataAvailable(this, cmpCode, distCode, customerCode);
    }

    public void insertCustomerApprovalDetail(String distCode, String cmpCode, String customerCode, String approvalStatus, String customerData) {
        new RetailerDetailHelper().insertCustomerApprovalDetail(this, distCode, cmpCode, customerCode, approvalStatus, customerData);
    }

    public List<RetailerVO> getRetailerApprovalStatusList(String cmpCode, String distCode) {
        return new SyncDBHelper().getRetailerApprovalStatusList(this, cmpCode, distCode);
    }

    public CustomerApprovalModel getCustomerDetailsForApproval(String distCode, String cmpCode, String customerCode) {
        return new RetailerDetailHelper().getCustomerDetailsForApproval(this, cmpCode, distCode, customerCode);
    }

    public int getTempRouteCount() {
        return CodificationHelper.getInstance().getTempRouteCount(this);
    }

    public int getTempCustomerCount() {
        return CodificationHelper.getInstance().getTempCustomerCount(this);
    }

    public int getTempCustomerRouteCount() {
        return CodificationHelper.getInstance().getTempCustomerRouteCount(this);
    }

    public int getTempCustomerShipAddrCount() {
        return CodificationHelper.getInstance().getTempCustomerShipAddrCount(this);
    }

    public List<RouteModel> getAllTempRoutes(String distCode) {
        return CodificationHelper.getInstance().getAllTempRoutes(this, distCode);
    }

    public List<CustomerModel> getAllTempCustomers(String distCode) {
        return CodificationHelper.getInstance().getAllTempCustomers(this, distCode);
    }

    public List<AddressModel> getAllTempCustomerShipAddress(String cmpCode, String distCode) {
        return CodificationHelper.getInstance().getAllTempCustomerShipAddress(this, cmpCode, distCode);
    }

    public List<RouteModel> getAllTempCustomerRoute(String cmpCode, String distCode) {
        return CodificationHelper.getInstance().getAllTempCustomerRoute(this, cmpCode, distCode);
    }

    public List<RouteModel> getTempRoutesForUpload(String distCode) {
        return new RouteQueryHelper().getTempRoutesForUpload(this, distCode);
    }

    public List<RetailerVO> getTempRetailerList(String cmpCode, String distCode) {
        return new SyncDBHelper().getTempRetailerList(this, cmpCode, distCode);
    }

    public List<RouteModel> getTempRetailerRouteList(String cmpCode, String distCode) {
        return new SyncDBHelper().getTempRetailerRouteList(this, cmpCode, distCode);
    }

    public List<AddressModel> getTempRetailerShipAddrList(String cmpCode, String distCode) {
        return new SyncDBHelper().getTempRetailerShipAddrList(this, cmpCode, distCode);
    }

    public void updateTempCodificationTables(List<RouteModel> tempRoutes, List<CustomerModel> tempCustomers,
                                             List<AddressModel> tempCustomerAddress, List<RouteModel> tempCustomerRoutes) {
        CodificationHelper.getInstance().updateTempCodificationTables(this, tempRoutes, tempCustomers, tempCustomerAddress, tempCustomerRoutes);
    }

    public void insertCustomerDetail(List<CustomerModel> retailerList) {
        CodificationHelper.getInstance().insertCustomerDetail(this, retailerList);
    }

    public void insertRoutes(List<RouteModel> list) {
        new SyncDBHelper().insertRoute(this, list);
    }

    public void insertCustomerRoute(String cmpCode, String distrCode, List<RouteModel> list, String customerCode) {
        RouteQueryHelper.insertCustomerRoute(this, cmpCode, distrCode, list, customerCode);
    }

    public List<OrderBookingVO> fetchSalesReturnHeaderDetail(String cmpcode, String distCode) {
        return new DistrPurchaseHelper().getSalesReturnHeaderDetail(this, cmpcode, distCode);

    }

    public List<SalesReturnVO> getSalesReturnDetailFromDB(String distrCode, String salesreturnNo) {
        return new OrderBookingHelper().getSalesReturnDetailFromDB(this, distrCode, salesreturnNo);
    }

    public List<SalesReturnVO> loadSalesReturns(String distrcode, String salesmancode, String routecode, String retrCode) {

        return new OrderBookingHelper().getSalesReturns(this, distrcode, salesmancode, routecode, retrCode);
    }

    public String isCustomerRegistered(String customerCode) {
        return new RetailerDetailHelper().isCustomerRegistered(this, customerCode);
    }

    public String getRouteCodeFromTempRouteCode(String tempRouteCode) {
        return new RetailerDetailHelper().getRouteCodeFromTempRouteCode(this, tempRouteCode);
    }

    public String getMaxCodeValueUsed(String tableName, String columnName) {
        return new RetailerDetailHelper().getMaxCodeValueUsed(this, tableName, columnName);
    }

    public String getCustomerCodeFromTempCustomerCode(String tempCustomerCode) {
        return new RetailerDetailHelper().getCustomerCodeFromTempCustomerCode(this, tempCustomerCode);
    }

    public List<RetailerVO> getCustomerData(String cmpCode, String distrCode, String routeCode) {
        return new RetailerDetailHelper().getCustomerData(this, cmpCode, distrCode, routeCode);
    }

    public List<OrderBookingVO> getAllProductsForBatchTransfer(String cmpCode, String distrCode, String stkType) {
        return new OrderBookingHelper().getAllProductsForBatchTransfer(this, cmpCode, distrCode, stkType);
    }

    public boolean isRouteExistForCustomer(String cmpCode, String distrCode, String customerCode, String routeCode) {
        return new RetailerDetailHelper().isRouteExistForCustomer(this, cmpCode, distrCode, customerCode, routeCode);
    }

    public void updateCustomerState(String cmpCode, String distrCode, String customerCode) {
        new RetailerDetailHelper().updateCustomerState(this, cmpCode, distrCode, customerCode);
    }

    public void insertCustomerRouteAssigning(String cmpCode, String distrCode, String customerCode, String routeCode) {
        new RetailerDetailHelper().insertCustomerRouteAssigning(this, cmpCode, distrCode, customerCode, routeCode);
    }

    public List<RouteModel> getRouteList(String distrCode) {
        return new RouteQueryHelper().getRouteList(this, distrCode);
    }

    public void deleteCustomerRouteAssigning(String cmpCode, String distrCode, String customerCode, String routeCode) {
        new RetailerDetailHelper().deleteCustomerRouteAssigning(this, cmpCode, distrCode, customerCode, routeCode);
    }

    public String getBatchTransferNo(String cmpCode, String distrCode, String goDownCode, String completedFlag, String uploadFlag) {
        return new OrderBookingHelper().getBatchTransferNo(this, cmpCode, distrCode, goDownCode, completedFlag, uploadFlag);
    }

    public void deleteBatchTransfer(String cmpCode, String distrCode, String goDownCode, String batchTransferNo, String completedFlag, String uploadFlag) {
        new OrderBookingHelper().deleteBatchTransfer(this, cmpCode, distrCode, goDownCode, batchTransferNo, completedFlag, uploadFlag);
    }

    public void insertBatchTransferDetails(String distrCode, String cmpCode, String goDownCode, String batchTransferNo, OrderBookingVO orderBookingVO) {
        new OrderBookingHelper().insertBatchTransferDetails(this, distrCode, cmpCode, goDownCode, batchTransferNo, orderBookingVO);
    }

    public List<OrderBookingVO> getBTRProductsFromDB(String cmpCode, String distrCode, String batchTransferNo, String completedFlag, String uploadFlag) {
        return new OrderBookingHelper().getBTRProductsFromDB(this, cmpCode, distrCode, batchTransferNo, completedFlag, uploadFlag);
    }

    public void deleteBatchTransferById(String cmpCode, String distrCode, String goDownCode, String btrNo, OrderBookingVO orderBookingVO) {
        new OrderBookingHelper().deleteBatchTransferById(this, cmpCode, distrCode, goDownCode, btrNo, orderBookingVO);
    }

    public void updateBatchTransferDetails(String cmpCode, String distrCode, String goDownCode, String btrNo, String remarks) {
        new RetailerDetailHelper().updateBatchTransferDetails(this, cmpCode, distrCode, goDownCode, btrNo, remarks);
    }

    public void updateStockOnHandBTR(String cmpCode, String distrCode, String goDownCode, String btrNo, List<OrderBookingVO> batchTransferList) {
        new OrderBookingHelper().updateStockOnHandBTR(this, cmpCode, distrCode, goDownCode, btrNo, batchTransferList);
    }

    public List<OrderBookingVO> getBatchTransferData(String cmpCode, String distCode) {
        return new OrderBookingHelper().getBatchTransferData(this, cmpCode, distCode);
    }

    public void uploadBTRStatus(String batchTransferNo, String stockType, String prodCode, String fromBatchCode, String toBatchCode, String tableName, String columnUpload, String status) {
        new SyncDBHelper().uploadBTRStatus(this, batchTransferNo, stockType, prodCode, fromBatchCode, toBatchCode, tableName, columnUpload, status);
    }

    public List<CodeGeneratorModel> getAllGeneratedCodes(String distCode, String tableName) {
        return new CodeGeneratorQueryHelper().getAllGeneratedCodes(this, distCode, tableName);
    }

    public void insertCodeGenerator(List<CodeGeneratorModel> codeGeneratorModels, String tableName) {
        CodeGeneratorQueryHelper.getInstance().insertCodeGenerator(this, codeGeneratorModels, tableName);
    }

    public void updateCodeGeneratorHistoryStatus(String distrCode, String screenName, String prefix, String suffixYy) {
        CodeGeneratorQueryHelper.getInstance().updateCodeGeneratorHistoryStatus(this, distrCode, screenName, prefix, suffixYy);
    }

    public void updateCodeGenerator(String cmpCode, String distCode, List<CodeGeneratorModel> codeGeneratorModels) {
        CodeGeneratorQueryHelper.getInstance().updateCodeGenerator(this, cmpCode, distCode, codeGeneratorModels);
    }

    public String getSuffixYyForScreen(String screenNameSalesman) {
        return new UserDbHelper().getSuffixYyForScreen(this, screenNameSalesman);
    }

    public List<OrderBookingVO> getProductBatchCodeForBTR(String cmpCode, String prodCode, String stockType) {
        return new OrderBookingHelper().getProductBatchCodeForBTR(this, cmpCode, prodCode, stockType);
    }

    public List<OrderBookingVO> getUomForBTR(String prodCode, String tableName) {

        return new OrderBookingHelper().getUomForBTR(this, prodCode, tableName);
    }

    public double getRetailerSOV(String distrcode, String salesmancode, String routeCode, String customerCode) {
        return new RetailerDetailHelper().getRetailerSOV(this, distrcode, salesmancode, routeCode, customerCode);
    }

    public List<String> getTop10SKUList(String cmpCode, String distStateCode) {

        return new DistrPurchaseHelper().fetchTop10SKUList(this, cmpCode, distStateCode);
    }

    public List<EditQtyModel> fetchPreviousOrderQtyDetails(String distCode, String salesmanCode,
                                                           String retailerCode, String orderInvoiceNo) {

        return new OrderBookingHelper().getPreviousOrderQtyDetails(this, distCode, salesmanCode, retailerCode, orderInvoiceNo);
    }

    //New Distributors Info
    public ArrayList<Distributor> getDistributorList() {
        return new UserDbHelper().getDistributorList(this);
    }

    public ArrayList<CustomerModel> getUniqueCustomerList() {
        return new UserDbHelper().getUniqueCustomerList(this);
    }

    //New Distributors Info
    public Distributor getDistributorInfo(String distributor_code, String company_code) {
        return new UserDbHelper().getDistributorInfo(this, distributor_code, company_code);

    }

    public List<String> fetchAllOrderNos() {
        return new OrderBookingHelper().fetchAllOrderNos(this);
    }

    public List<RetailerVO> getOrderBookingRetailerDetails(String distrCode, String salesmanCode,
                                                           String dayStartDate, String tableName) {
        return new SyncDBHelper().getOrderBookingRetailerDetails(this, distrCode, salesmanCode,
                dayStartDate, tableName);
    }

    public List<OrderBookingVO> getOrderBookingLineItem(String distrCode, String salesmanCode,
                                                        String routeCode, String customerCode) {
        return new SyncDBHelper().getOrderBookingLineItem(this, distrCode, salesmanCode, routeCode, customerCode);
    }

    public List<FeedbackModel> getRetailerFeedbackList(String cmpCode, String distCode, String salesmanCode) {

        return new RetailerDetailHelper().getAllRetailersFeedbackList(this, cmpCode, distCode, salesmanCode);
    }

    public List<RetailerVO> getLocationUpdateList(String cmpCode, String distCode, String salesmanCode) {

        return new RetailerDetailHelper().getAllRetailersLocationList(this, cmpCode, distCode, salesmanCode);
    }

    //New Scheme
    public ArrayList<SchemeModel> getCustomerSchemeProductsList() {
        return new OrderBookingSchemeHelper().getCustomerSchemeProductsList(this);
    }

    //New Scheme
    public Boolean isCustomerSchemeAvailable() {
        return new UserDbHelper().isCustomerSchemeAvailable(this);
    }

    //New Scheme
    public List<SchemeModel> getRetailerSchemeProductsList(String distr_code, String cmp_code) {
        return new OrderBookingSchemeHelper().getRetailerSchemeProductsList(this, distr_code, cmp_code);
    }

    public List<RetailerVO> getOrderedRoute(String distCode, String salesmanCode, String screenName) {
        return dashboardHelper.getOrderedRoute(this, distCode, salesmanCode, screenName);
    }

    public List<OrderBookingVO> loadVANSalesDaySummaryData(String distCode, String salesmanCode, String routecode) {

        return new RetailerDetailHelper().getVANSalesDaySummaryData(this, distCode, salesmanCode, routecode);

    }

    public List<OrderBookingVO> loadDaySummaryOrderAllValue(String distrcode, String salesmancode,
                                                            String strRouteCode) {
        return new RetailerDetailHelper().getDaySummaryOrderAllValue(this, distrcode, salesmancode,
                strRouteCode);
    }


    public List<OrderBookingVO> loadInvoiceSummaryBillValue(String distrcode, String salesmancode, String strretailerCode) {


        return new RetailerDetailHelper().getInvoiceSummary(this, distrcode, salesmancode, strretailerCode);

    }

    public List<OrderBookingVO> loadDaySummaryOrderTabValue(String distrcode, String salesmancode, String strretailerCode) {


        return new RetailerDetailHelper().getDaySummaryOrderTabValue(this, distrcode, salesmancode, strretailerCode);

    }

    public String getTotalRetailerFromDB(String distCode, String salesmanCode, Boolean isTodayBeat) {

        return new RetailerDetailHelper().getTotalRetailers(this, distCode, salesmanCode, isTodayBeat);
    }

    public List<Map<String, String>> getOtherBeat(String distCode) {

        return dashboardHelper.getOtherBeatCount(this, distCode);
    }

    public String getCompletedRetailerFromDB(String distCode, String salesmanCode, String columnName) {

        return new RetailerDetailHelper().getCompletedRetailerCount(this, distCode, salesmanCode, columnName);
    }

    public String getCoveredRetailerCount(String distCode, String salesmanCode) {

        return new RetailerDetailHelper().getCoveredRetailerCount(this, distCode, salesmanCode);
    }

    public String getNewOutletCounts(String distCode, String salesmanCode, String tableName) {

        return new DashboardHelper().fetchNewOutletCount(this, distCode, salesmanCode, tableName);
    }

    public String[] getOrderCountFromDB(String tableName, String distCode, String salesmanCode, String routeCode) {
        return new RetailerDetailHelper().getOrderCounts(this, tableName, distCode, salesmanCode, routeCode);
    }

    public String getOrderUniqueLines(String tableName, String distCode, String salesmanCode, String routeCode) {
        return new RetailerDetailHelper().getOrderUniqueLines(this, tableName, distCode, salesmanCode, routeCode);
    }

    public String[] getSalesReturnSalableUnsalableCount(String tableSalesReturn, String distCode, String salesmanCode) {

        return new DashboardHelper().fetchSalesReturnSalableUnsalableCount(this, tableSalesReturn, distCode, salesmanCode);
    }

    public String getTotalCashFromDB(String distCode, String salesmanCode, int i) {

        return new RetailerDetailHelper().getTotalCash(this, distCode, salesmanCode, i);
    }

    public boolean isScreenAvailable(String moduleName, String screenName) {
        return new DashboardHelper().isScreenAvailable(this, moduleName, screenName);
    }

    public Integer getMappedCount(String tableName) {
        return new DashboardHelper().getMappedCount(this,tableName);
    }
    public ArrayList<FeedBackMasterModel> getFeedbackList() {
        return new UserDbHelper().getFeedbackList(this);

    }

    public void insertFeedback(FeedbackModel feedbackModel) {
        new UserDbHelper().insertFeedback(this, feedbackModel);
    }

    public ArrayList<FeedbackModel> getAllFeedback() {
        return new UserDbHelper().getAllFeedback(this);

    }

    public ArrayList<Distributor> getSchemeDistrList() {
        return new OrderBookingSchemeHelper().getSchemeDistrList(this);
    }

    public int getSchemeDistrListCount() {
        return new OrderBookingSchemeHelper().getSchemeDistrListCount(this);
    }

    public List<String> getAllMappedUsers() {
        return new UserDbHelper().getAllMappedUsers(this);
    }

    public List<String> getAllMappedCompany() {
        return new UserDbHelper().getAllMappedCompany(this);
    }

    public void insertUpdateLocation(String loginCode, String updateTo,String latitude, String longitude, String strPinCode) {
        new OrderBookingHelper().insertRetailerLocaitonUpdate(this, loginCode, updateTo,
                latitude, longitude, strPinCode);
    }

    public void deleteUpdateLocation(String loginCode) {
        new OrderBookingHelper().deleteRetailerExistingLocation(this, loginCode);
    }
}

