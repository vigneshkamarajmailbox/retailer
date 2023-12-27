/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.db.query;

import static com.botree.retailerssfa.service.JSONConstants.TAG_MOD_DT;

interface IDBQuery extends IDBIndexQuery {

    // Create User Table Query
    String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS '" + TABLE_USER + "' ('"
            + COLUMN_CMP_CODE + "' TEXT NOT NULL, '"
            + COLUMN_SALESMAN_CODE + "' " + "TEXT NOT NULL, '"
            + CONFIG_USER_CODE + "' " + "TEXT NOT NULL, '"
            + USER_CODE + "' TEXT NOT NULL, '"
            + USER_NAME + "' TEXT NOT NULL, '"
            + USER_TYPE + "' TEXT NOT NULL, '"
            + MAPPED_CODE + "' TEXT NOT NULL, '"
            + IS_LAST_LEVEL + "' TEXT NOT NULL, '"
            + HIER_LEVEL + "' TEXT , '"
            + COLUMN_DISTR_CODE + "' TEXT NOT NULL, '"
            + COLUMN_DISTR_BR_CODE + "' TEXT NOT NULL, '"
            + SH_LAST_LEVEL_CODE + "' TEXT NOT NULL, '"
            + SH_LAST_LEVEL_NAME + "' TEXT NOT NULL, '"
            + LOB + "' TEXT NOT NULL, '"
            + GST_DIST_STATE_CODE + "' TEXT NOT NULL, '"
            + CREDENTIAL + "' TEXT NOT NULL ,'"
            + COLUMN_MOBILE_NO + "' TEXT NOT NULL ,"
            + "PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + USER_CODE + "', '" + COLUMN_DISTR_CODE + "'))";

    String CREATE_MAPPED_COMPANY = "CREATE TABLE IF NOT EXISTS '" + TABLE_MAPPED_COMPANY + "' ('"
            + COLUMN_NAME + "' TEXT NOT NULL)";

    String CREATE_MAPPED_DISTRIBUTOR = "CREATE TABLE IF NOT EXISTS '" + TABLE_MAPPED_USER + "' ('"
            + COLUMN_NAME + "' TEXT NOT NULL)";

    String CREATE_MAPPED_CUSTOMER = "CREATE TABLE IF NOT EXISTS '" + TABLE_MAPPED_CUSTOMER + "' ('"
            + COLUMN_NAME + "' TEXT NOT NULL)";

    String CREATE_STOCKIST_TABLE = "CREATE TABLE IF NOT EXISTS '"
            + TABLE_STOCKIST + "' ('"
            + COLUMN_CMP_CODE + "' TEXT NOT NULL, '"
            + ISR_CODE + "' " + "TEXT NOT NULL, '"
            + ISR_NAME + "' " + "TEXT NOT NULL, '"
            + COLUMN_SUPER_STOCKIST_CODE + "' TEXT NOT NULL, '"
            + COLUMN_SUPER_STOCKIST_NAME + "' TEXT NOT NULL, '"
            + COLUMN_STOCKIST_CODE + "' TEXT NOT NULL, '"
            + COLUMN_STOCKIST_NAME + "' TEXT NOT NULL, '"
            + COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '"
            + COLUMN_SALESMAN_NAME + "' TEXT , '"
            + SH_LAST_LEVEL_CODE + "' TEXT NOT NULL, '"
            + SH_LAST_LEVEL_NAME + "' TEXT NOT NULL, '"
            + LOB + "' TEXT NOT NULL, '"
            + GST_DIST_STATE_CODE + "' TEXT NOT NULL, "
            + "PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + ISR_CODE + "', '" + COLUMN_SUPER_STOCKIST_CODE + "', '" + COLUMN_STOCKIST_CODE + "', '" + COLUMN_SALESMAN_CODE + "'))";


    String CREATE_CONFIGURATION = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CONFIGURATION + "' ('" +
            COLUMN_CMP_CODE + "' TEXT , '" +
            TABLE_NAME + "' TEXT, '" +
            COLUMN_CODE + "' TEXT, '" +
            COLUMN_DESCRIPTION_CAPS + "'TEXT )";

    String CREATE_SCREEN_CONFIGURATION = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCREEN_CONFIGURATION + "' ('" +
            COLUMN_LOGIN_CODE + "' TEXT , '" +
            COLUMN_MODULE_NO + "' NUMBER, '" +
            COLUMN_MODULE_NAME + "' TEXT, '" +
            COLUMN_SCREEN_NO + "' NUMBER, '" +
            COLUMN_SCREEN_NAME + "' TEXT, '" +
            COLUMN_CHECKED + "'TEXT,'" +
            SEQUENCE + "'NUMBER,'" +
            COLUMN_GROUP_CODE + "' TEXT , '" +
            COLUMN_GROUP_NAME + "' TEXT , '" +
            COLUMN_SCREEN_TYPE + "' TEXT , " +
            "PRIMARY KEY ('" + COLUMN_LOGIN_CODE + "', '" + COLUMN_MODULE_NO + "', '" + COLUMN_SCREEN_NO + "'))";

    String CREATE_OUTLET_VISIT_SEQUENCE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_OUTLET_VISIT_SEQUENCE + "' ('" +
            COLUMN_CUSTOMER_CODE + "' TEXT , '" +
            COLUMN_SCREEN_NAME + "' TEXT, '" +
            SEQUENCE + "' NUMBER, '" +
            IS_SEQUENCE_VISIBLE + "'TEXT," +
            "PRIMARY KEY ('" + COLUMN_CUSTOMER_CODE + "', '" + COLUMN_SCREEN_NAME + "', '" + SEQUENCE + "'))";

    // Create Route Table Query
    String CREATE_ROUTE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ROUTE + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL," + " '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL," + " '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_NAME + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_TYPE + "' TEXT DEFAULT 'S' , '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL DEFAULT 'N' , " +
            "PRIMARY KEY ('" + COLUMN_DISTR_CODE + "', '" + COLUMN_ROUTE_CODE + "'))";


    // Create Retailer Table Query
    String CREATE_RETAILER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_RETAILER + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_IS_TODAY_BEAT + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL,'" +
            COLUMN_CUSTOMER_NAME + "' TEXT NOT NULL, '" +
            COLUMN_RETAILER_ADDR1 + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CREDIT_DAYS + "' INTEGER NOT NULL, " + "'" +
            COLUMN_CREDIT_LIMIT + "' INTEGER NOT NULL, '" +
            COLUMN_IS_VISIT + "' TEXT, '" +
            COLUMN_LATITUDE + "' TEXT, '" +
            COLUMN_LONGITUDE + "' TEXT, '" +
            COLUMN_RTR_MOBILE_NO + "' TEXT, '" +
            RTR_SEQUENCE_NO_CAPS + "' TEXT, '" +
            GSTNO + "' TEXT, '" +
            GST_STATECODE + "' TEXT, '" +
            PANNO + "' TEXT, '" +
            GST_TYPE + "' TEXT, '" +
            COLUMN_UNLOCK_CODE + "' TEXT, '" +
            COLUMN_IS_BY_PASS_GEO + "' TEXT, '" +
            COLUMN_MENU_REASON + "' TEXT, '" +
            COLUMN_IS_SINGLE_REASON + "' TEXT, '" +
            COLUMN_DIGITAL_SIGNATURE + "' TEXT, '" +
            COLUMN_LICENSE_EXP_DT + "' TEXT, " +
            " PRIMARY KEY ('" + COLUMN_DISTR_CODE + "', '" + COLUMN_SALESMAN_CODE + "', '" + COLUMN_ROUTE_CODE + "', '" + COLUMN_CUSTOMER_CODE + "', '"
            + COLUMN_CHANNEL_CODE + "', '" + COLUMN_SUB_CHANNEL_CODE + "', '" + COLUMN_GROUP_CODE + "', '" + COLUMN_CLASS_CODE + "'))";


    // Create PendingBill Table Query
    String CREATE_PENDING_BILL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PENDING_BILL + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALESMAN_NAME + "' TEXT, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_NAME + "' TEXT, '" +
            COLUMN_INVOICE_NO + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_DATE + "' TEXT NOT NULL, '" +
            NET_AMOUNT + "' NUMBER NOT NULL, '" +
            COLUMN_PENDING_AMOUNT + "' NUMBER NOT NULL," +
            " PRIMARY KEY ('" + COLUMN_DISTR_CODE + "', '" + COLUMN_SALESMAN_CODE + "', '" + COLUMN_CUSTOMER_CODE + "', '" + COLUMN_INVOICE_NO + "'))";


    // Create Products Table Query
    String CREATE_PRODUCTS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PRODUCTS + "' ('" +
            COLUMN_SALESMAN_CODE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_CMP_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_VAL_CODE_CAPS + "' INTEGER, " + "'" +
            COLUMN_PRODHIER_VAL_NAME_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_PATH + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_PATH_NAME + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_PROD_SHORT_NAME_CAPS + "' TEXT, '" +
            COLUMN_SELL_PRICE_CAPS + "' NUMBER, " + "'" +
            COLUMN_MRP_CAPS + "' NUMBER, '" +
            COLUMN_PRIMARY_DISC + "' NUMBER, '" +
            COLUMN_STOCK_IN_HAND_CAPS + "' Number,  '" +
            COLUMN_UOM_GROUPID_CAPS + "' TEXT, '" +
            COLUMN_BRAND_CODE + "' TEXT, '" +
            COLUMN_BRAND_NAME + "' TEXT, '" +
            COLUMN_CATEGORY_CODE + "' TEXT, '" +
            COLUMN_CATEGORY_NAME + "' TEXT, '" +
            COLUMN_PRODUCT_NET_WGT + "' TEXT, '" +
            COLUMN_PRODUCT_WGT_TYPE + "' TEXT, '" +
            COLUMN_PURCHASE_PRICE + "' TEXT, '" +
            COLUMN_DEFAULT_UOMID_CAPS + "' TEXT, " +
            "PRIMARY KEY ('" + COLUMN_SALESMAN_CODE_CAPS + "', '" + COLUMN_DISTR_CODE_CAPS + "', '" + COLUMN_CMP_CODE_CAPS
            + "', '" + COLUMN_PROD_CODE_CAPS + "', '" + COLUMN_BATCH_CODE + "'))";

    // Create Vansles Products Table Query
    String CREATE_VANSALES_PRODUCTS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_VANSALES_PRODUCTS + "' ('" +
            COLUMN_SALESMAN_CODE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_CMP_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_VAL_CODE_CAPS + "' INTEGER, " + "'" +
            COLUMN_PRODHIER_VAL_NAME_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_PATH + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_PATH_NAME + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_PROD_SHORT_NAME_CAPS + "' TEXT, '" +
            COLUMN_SELL_PRICE_CAPS + "' NUMBER, " + "'" +
            COLUMN_MRP_CAPS + "' NUMBER, '" +
            COLUMN_PRIMARY_DISC + "' NUMBER, '" +
            COLUMN_STOCK_IN_HAND_CAPS + "' Number,  '" +
            COLUMN_UOM_GROUPID_CAPS + "' TEXT, '" +
            COLUMN_BRAND_CODE + "' TEXT, '" +
            COLUMN_BRAND_NAME + "' TEXT, '" +
            COLUMN_CATEGORY_CODE + "' TEXT, '" +
            COLUMN_CATEGORY_NAME + "' TEXT, '" +
            COLUMN_PRODUCT_NET_WGT + "' TEXT, '" +
            COLUMN_PRODUCT_WGT_TYPE + "' TEXT, '" +
            COLUMN_PURCHASE_PRICE + "' TEXT, '" +
            COLUMN_DEFAULT_UOMID_CAPS + "' TEXT," +
            " PRIMARY KEY ('" + COLUMN_SALESMAN_CODE_CAPS + "', '" + COLUMN_DISTR_CODE_CAPS + "', '" + COLUMN_CMP_CODE_CAPS + "', '" + COLUMN_PROD_CODE_CAPS + "', '" + COLUMN_BATCH_CODE + "'))";

    // Create ProductSuggestions Table Query
    String CREATE_PRODUCT_SUGGESTIONS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PRODUCT_SUGGESTIONS + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_RETLR_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE_CAPS + "' TEXT NOT NULL, '" +
            SUGGESTED_QUANTITY_CAPS + "' INTEGER, " +
            "PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + COLUMN_DISTR_CODE + "', '" + COLUMN_RETLR_CODE_CAPS + "', '" + COLUMN_PROD_CODE_CAPS + "'))";

    // Create CategorySequence Table Query
    String CREATE_CATEGORY_SEQUENCE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CATEGORY_SEQUENCE + "' ('" +
            SEQUENCE_NO_CAPS + "' INTEGER NOT NULL, '" +
            CATEGORY_CAPS + "' TEXT NOT NULL)";

    String CREATE_UPDATELOCATION = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_UPDATE_LOCATION + "' ('" +
            COLUMN_LOCATION_REF_NO + "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'" +
            COLUMN_LOGIN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_LATITUDE + "' TEXT, '" +
            COLUMN_LONGITUDE + "' TEXT, " + "'" +
            COLUMN_IMAGE + "' TEXT, " + "'" +
            COLUMN_DATE + "' TEXT, " + "'" +
            COLUMN_POSTAL_CODE + "' TEXT, " + "'" +
            COLUMN_UPLOAD_TO + "' TEXT, " + "'" +
            COLUMN_MOD_DT + "' TEXT, " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL)";

    // Create ProductCategory Table Query
    String CREATE_PRODUCT_CATEGORY = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PRODUCT_CATEGORY + "' ('" +
            COLUMN_PROD_CODE_CAPS + "' TEXT NOT NULL, '" +
            CATEGORY_CAPS + "' TEXT, '" +
            COLUMN_DESCRIPTION_CAPS + "' TEXT)";


    String CREATE_FEEDBACK_MASTER_TABLE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_FEEDBACK_MASTER + "' ('" +
            COLUMN_CMP_CODE + "' TEXT , '" +
            COLUMN_FEEDBACK_TYPE + "' TEXT, '" +
            COLUMN_FEEDBACK_NAME + "' TEXT, '" +
            COLUMN_CONSOLE_FLAG + "' TEXT, '" +
            UPLOAD_FLAG + "' TEXT, '" +
            COLUMN_MOD_DT + "' TEXT, '" +
            COLUMN_CHANGE_NO + "'TEXT," +
            "PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + COLUMN_FEEDBACK_TYPE + "', '" + COLUMN_FEEDBACK_NAME + "'))";


    /****
     * For Collection Screen
     ****/

    // Create CashCollection Table Query
    String CREATE_CASH_COLLECTION = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CASH_COLLECTION + "' ('" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL,'" +
            COLUMN_COLLECTION_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CASH_AMT + "' NUMBER NOT NULL, '" +
            COLUMN_SCREEN_NAME_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_REMARKS_CAPS + "' TEXT, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL)";

    // Create ChequeCollection Table Query
    String CREATE_CHEQUE_COLLECTION = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CHEQUE_COLLECTION + "' ('" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL,'" +
            COLUMN_COLLECTION_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            COLUMN_BANK_NAME + "' TEXT NOT NULL, '" +
            COLUMN_BANK_CODE + "' TEXT NOT NULL, '" +
            COLUMN_BANK_BRANCH_CODE + "' TEXT NOT NULL, '" +
            COLUMN_INSTRUMENT + "' TEXT NOT NULL, '" +
            COLUMN_INSTRUMENT_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INSTRUMENT_AMT + "' NUMBER NOT NULL, '" +
            COLUMN_REMARKS_CAPS + "' TEXT, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL)";

    // Create OrderBooking Table Query
    String CREATE_ORDER_BOOKING = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ORDER_BOOKING + "' ('" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            READABLE_INV_NO + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ORDER_REF_NO_CAPS + "' INTEGER , '" +
            COLUMN_RETLR_CODE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_SHIP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ORDER_DATE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT, '" +
            COLUMN_PROD_CODE_CAPS + "' TEXT, '" +
            COLUMN_ORDER_QTY_CAPS + "' NUMBER, '" +
            COLUMN_MRP_CAPS + "' NUMBER, " + "'" +
            COLUMN_ORDER_STATUS_CAPS + "' TEXT, '" +
            COLUMN_REMARKS_CAPS + "' TEXT, '" +
            COLUMN_ORDER_VALUE_CAPS + "' NUMBER, '" +
            COLUMN_PRIM_DISC_ORDER_VALUE + "' NUMBER, '" +
            COLUMN_UOM_ID + "' TEXT, '" +
            SCHEME_AMOUNT + "' NUMBER, '" +
            NET_AMOUNT + "' NUMBER, '" +
            TOTAL_AMOUNT + "' NUMBER, '" +
            COLUMN_ACTUAL_SELL_RATE + "' NUMBER, '" +
            GROSS_AMOUNT + "' NUMBER, '" +
            TAX_PER_PRODUCT + "' NUMBER, '" +
            CGST_PERCENT + "' NUMBER, '" +
            CGST_VALUE + "' NUMBER, '" +
            SGST_PERCENT + "' NUMBER, '" +
            SGST_VALUE + "' NUMBER, '" +
            UTGST_PERCENT + "' NUMBER, '" +
            UTGST_VALUE + "' NUMBER, '" +
            IGST_PERCENT + "' NUMBER, '" +
            IGST_VALUE + "' NUMBER, '" +
            TAX_CODE + "' TEXT, '" +
            COLUMN_LATITUDE + "' TEXT, '" +
            COLUMN_LONGITUDE + "' TEXT, '" +
            COLUMN_START_TIME + "' TEXT, '" +
            COLUMN_END_TIME + "' TEXT, '" +
            IS_EXISTING_RETAILER + "' TEXT, '" +
            COLUMN_CONVERSION_FACTOR + "' TEXT, '" +
            COLUMN_DEFAULT_UOMID_CAPS + "' TEXT, '" +
            COLUMN_SOQ + "' TEXT NOT NULL, '" +
            COLUMN_COMPLETE_FLAG + "' TEXT NOT NULL, '" +
            COLUMN_PROD_TYPE + "' TEXT, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL DEFAULT 'N', " +
            " PRIMARY KEY ('" + INVOICE_NO + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_CMP_CODE + "' , '"
            + COLUMN_UPLOAD + "' , '" + COLUMN_PROD_CODE_CAPS + "' , '" + COLUMN_RETLR_CODE_CAPS + "' , '" + COLUMN_ORDER_DATE_CAPS + "' , '"
            + COLUMN_BATCH_CODE + "'))";

    // Create Order Conformation Table Query
    String CREATE_PURCHASE_RECEIPT_CONFIRMATION = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PURCHASE_RECEIPT_CONFIRMATION + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_DATE + "' TEXT NOT NULL, '" +
            GRN_DT + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL , '" +
            COLUMN_PROD_NAME + "' TEXT, '" +
            COLUMN_PROD_BATCH_CODE + "' TEXT, '" +
            COLUMN_PURCHASE_PRICE + "' NUMBER, '" +
            COLUMN_MRP_CAPS + "' NUMBER, '" +
            COLUMN_INVOICE_QTY + "' NUMBER, '" +
            COLUMN_RECEIVED_QTY + "' NUMBER, '" +
            COLUMN_RECEIVED_UOM + "' TEXT, '" +
            COLUMN_UOM_CODE + "' TEXT, '" +
            COLUMN_ORDER_VALUE_CAPS + "' NUMBER, '" +
            GRN_STATUS + "' TEXT, '" +
            CONFIRM_STATUS + "' TEXT, '" +
            CONFIRM_DATE + "' TEXT, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_INVOICE_DATE + "' , '" + COLUMN_PROD_CODE + "'))";

    // Create Order Conformation Table Query
    String CREATE_ORDER_CONFIRMATION = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ORDER_CONFIRMATION + "' ('" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_RETLR_CODE_CAPS + "' TEXT NOT NULL, " + "'" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            ORDER_NO + "' TEXT NOT NULL, '" +
            COLUMN_ORDER_DATE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT, '" +
            COLUMN_PROD_CODE_CAPS + "' TEXT, '" +
            COLUMN_ORDER_QTY_CAPS + "' NUMBER, '" +
            COLUMN_UOM_ID + "' TEXT, '" +
            COLUMN_CONFIRM_QTY + "' NUMBER, '" +
            COLUMN_CONFIRM_UOM_ID + "' TEXT, '" +
            COLUMN_MRP_CAPS + "' NUMBER, " + "'" +
            COLUMN_ORDER_VALUE_CAPS + "' NUMBER, '" +
            COLUMN_PRIM_DISC_ORDER_VALUE + "' NUMBER, '" +
            SCHEME_AMOUNT + "' NUMBER, '" +
            NET_AMOUNT + "' NUMBER, '" +
            TOTAL_AMOUNT + "' NUMBER, '" +
            TAX_PER_PRODUCT + "' NUMBER, '" +
            CGST_PERCENT + "' NUMBER, '" +
            CGST_VALUE + "' NUMBER, '" +
            SGST_PERCENT + "' NUMBER, '" +
            SGST_VALUE + "' NUMBER, '" +
            UTGST_PERCENT + "' NUMBER, '" +
            UTGST_VALUE + "' NUMBER, '" +
            IGST_PERCENT + "' NUMBER, '" +
            IGST_VALUE + "' NUMBER, '" +
            TAX_CODE + "' TEXT, '" +
            COLUMN_COVERSION_QTY + "' TEXT, '" +
            COLUMN_CONVERSION_UOMID + "' TEXT, '" +
            COLUMN_ORDER_STATUS_CAPS + "' TEXT, '" +
            COLUMN_REMARKS_CAPS + "' TEXT, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, PRIMARY KEY ('" + COLUMN_DISTR_CODE + "', '" + COLUMN_SALESMAN_CODE + "', '" + COLUMN_ROUTE_CODE + "', '" +
            COLUMN_RETLR_CODE_CAPS + "' , '" + INVOICE_NO + "', '" + COLUMN_ORDER_DATE_CAPS + "', '" + COLUMN_PROD_CODE_CAPS + "'))";


    // Create Purchase OrderBooking Table Query
    String CREATE_PURCHASE_ORDER_BOOKING = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PURCHASE_ORDER_BOOKING + "' ('" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ORDER_REF_NO_CAPS + "' INTEGER PRIMARY KEY, '" +
            COLUMN_ORDER_DATE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT, '" +
            COLUMN_PROD_CODE_CAPS + "' TEXT, '" +
            COLUMN_ORDER_QTY_CAPS + "' NUMBER, '" +
            COLUMN_MRP_CAPS + "' NUMBER, " + "'" +
            COLUMN_PURCHASE_PRICE + "' NUMBER, " + "'" +
            COLUMN_ORDER_STATUS_CAPS + "' TEXT, '" +
            COLUMN_REMARKS_CAPS + "' TEXT, '" +
            COLUMN_ORDER_VALUE_CAPS + "' NUMBER, '" +
            COLUMN_UOM_ID + "' TEXT, '" +
            COLUMN_BASE_UOM + "' TEXT, '" +
            SCHEME_AMOUNT + "' NUMBER, '" +
            NET_AMOUNT + "' NUMBER, '" +
            GROSS_AMOUNT + "' NUMBER, '" +
            TAX_PER_PRODUCT + "' NUMBER, '" +
            CGST_PERCENT + "' NUMBER, '" +
            CGST_VALUE + "' NUMBER, '" +
            SGST_PERCENT + "' NUMBER, '" +
            SGST_VALUE + "' NUMBER, '" +
            UTGST_PERCENT + "' NUMBER, '" +
            UTGST_VALUE + "' NUMBER, '" +
            IGST_PERCENT + "' NUMBER, '" +
            IGST_VALUE + "' NUMBER, '" +
            TAX_CODE + "' TEXT, '" +
            COLUMN_CONVERSION_FACTOR + "' TEXT, '" +
            CONFIRM_STATUS + "' TEXT NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL)";


    // billing table added
    // Create Billing Table Query
    String CREATE_BILLING = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BILLING + "' ('" +
            ORDER_NO + "' TEXT, '" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ORDER_REF_NO_CAPS + "' INTEGER , '" +
            COLUMN_RETLR_CODE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_ORDER_DATE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT, '" +
            COLUMN_ORDER_QTY_CAPS + "' NUMBER, '" +
            COLUMN_MRP_CAPS + "' NUMBER, " + "'" +
            COLUMN_ORDER_STATUS_CAPS + "' TEXT, '" +
            COLUMN_REMARKS_CAPS + "' TEXT, '" +
            COLUMN_ORDER_VALUE_CAPS + "' NUMBER, '" +
            COLUMN_PRIM_DISC_ORDER_VALUE + "' NUMBER, '" +
            COLUMN_UOM_ID + "' TEXT, '" +
            DISCOUNT_AMOUNT + "' NUMBER, '" +
            NET_AMOUNT + "' NUMBER, '" +
            GROSS_AMOUNT + "' NUMBER, '" +
            TOTAL_AMOUNT + "' NUMBER, '" +
            FREE_QTY + "' NUMBER, '" +
            TAX_PER_PRODUCT + "' NUMBER, '" +
            CGST_PERCENT + "' NUMBER, '" +
            CGST_VALUE + "' NUMBER, '" +
            SGST_PERCENT + "' NUMBER, '" +
            SGST_VALUE + "' NUMBER, '" +
            UTGST_PERCENT + "' NUMBER, '" +
            UTGST_VALUE + "' NUMBER, '" +
            IGST_PERCENT + "' NUMBER, '" +
            IGST_VALUE + "' NUMBER, '" +
            TAX_CODE + "' TEXT, '" +
            IS_EXISTING_RETAILER + "' TEXT, '" +
            COLUMN_CONVERSION_FACTOR + "' TEXT, '" +
            COLUMN_DEFAULT_UOMID_CAPS + "' TEXT, '" +
            COLUMN_BILL_STATUS + "' TEXT NOT NULL, '" +
            COLUMN_COMPLETE_FLAG + "' TEXT NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL , " +
            "PRIMARY KEY ('" + ORDER_NO + "', '" + INVOICE_NO + "', '" + COLUMN_DISTR_CODE + "', '" + COLUMN_SALESMAN_CODE + "' , '"
            + COLUMN_ROUTE_CODE + "', '" + COLUMN_ORDER_REF_NO_CAPS + "', '" + COLUMN_RETLR_CODE_CAPS + "', '" + COLUMN_PROD_CODE_CAPS + "'))";


    // Create StockTake Table Query
    String CREATE_STOCK_TAKE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_STOCK_TAKE + "' ('" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_STOCK_REF_NO_CAPS + "' INTEGER , '" +
            COLUMN_RETLR_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_STOCK_TAKE_DATE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE_CAPS + "' TEXT NOT NULL , '" +
            COLUMN_BATCH_CODE + "' TEXT NOT NULL , '" +
            COLUMN_STOCK_QTY_CAPS + "' NUMBER, '" +
            STOCK_CHECKED + "' TEXT NOT NULL, '" +
            STOCKTAKE_REASON + "' TEXT NOT NULL, '" +
            COLUMN_MRP_CAPS + "' NUMBER, '" +
            COLUMN_ORDER_VALUE_CAPS + "' NUMBER, '" +
            STOCK_STATUS_CAPS + "' TEXT, '" +
            COLUMN_UOM_ID + "' TEXT, '" +
            COLUMN_CONVERSION_FACTOR + "' TEXT, '" +
            COLUMN_DEFAULT_UOMID_CAPS + "' TEXT, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL , " +
            "PRIMARY KEY ('" + INVOICE_NO + "', '" + COLUMN_DISTR_CODE + "', '" + COLUMN_SALESMAN_CODE + "' , '" + COLUMN_ROUTE_CODE + "', '"
            + COLUMN_STOCK_REF_NO_CAPS + "', '" + COLUMN_RETLR_CODE_CAPS + "', '" + COLUMN_STOCK_TAKE_DATE_CAPS + "', '" + COLUMN_PROD_CODE_CAPS + "'))";


    // Create StockTake Table Query
    String CREATE_LOADING_STOCK = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_LOADING_STOCK + "' ('" +
            COLUMN_STOCK_REF_NO_CAPS + "' INTEGER PRIMARY KEY, '" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_STOCK_TAKE_DATE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT NOT NULL, '" +
            COLUMN_RECIEVED_QTY + "' NUMBER, '" +
            COLUMN_STOCK_QTY_CAPS + "' NUMBER, '" +
            COLUMN_STOCK_IN_HAND_CAPS + "' NUMBER, '" +
            COLUMN_AVAIL_QTY + "' NUMBER, '" +
            COLUMN_LAST_ORD_QTY + "' NUMBER, '" +
            COLUMN_MRP_CAPS + "' NUMBER, '" +
            COLUMN_SELL_PRICE_CAPS + "' NUMBER, '" +
            COLUMN_ORDER_VALUE_CAPS + "' NUMBER, '" +
            STOCK_STATUS_CAPS + "' TEXT, '" +
            COLUMN_UOM_ID + "' TEXT, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL)";

    // Create StockTake Table Query
    String CREATE_OPENING_STOCK = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_OPENING_STOCK + "' ('" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_STOCK_REF_NO_CAPS + "' INTEGER PRIMARY KEY, '" +
            COLUMN_STOCK_TAKE_DATE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_STOCK_QTY_CAPS + "' NUMBER, '" +
            COLUMN_UOM_CODE + "' TEXT, '" +
            FREE_QTY + "' NUMBER, '" +
            FREE_UOM + "' TEXT, '" +
            COLUMN_UNSAL_QTY_CAPS + "' NUMBER, '" +
            COLUMN_UNSAL_UOM + "' TEXT, '" +
            COLUMN_MRP_CAPS + "' NUMBER, '" +
            COLUMN_PURCHASE_PRICE + "' NUMBER, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL)";

    // Create SalesReturn Table Query
    String CREATE_SALES_RETURN = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_RETURN + "'('" +
            SNO + "' INTEGER PRIMARY KEY, '" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALES_RETURN_REF_NO_CAPS + "' TEXT, '" +
            COLUMN_RETLR_CODE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_RETURN_DATE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_REASON_CODE_CAPS + "' TEXT , " + "'" +
            COLUMN_REASON_NAME_CAPS + "' TEXT , " + "'" +
            COLUMN_SAL_QTY_CAPS + "' INTEGER , '" +
            COLUMN_UNSAL_REASON_CODE_CAPS + "' TEXT , " + "'" +
            COLUMN_UNSAL_REASON_NAME_CAPS + "' TEXT , " + "'" +
            COLUMN_UNSAL_QTY_CAPS + "' INTEGER, '" +
            COLUMN_RETURN_TYPE + "' TEXT NOT NULL, '" +
            COLUMN_IMAGE + "' TEXT , '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL)";


    String CREATE_PURCHASE_PARTIAL_RETURN = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PURCHASE_PARTIAL_RETURN + "'('" +
            SNO + "' INTEGER PRIMARY KEY, '" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            GRN_NO + "' TEXT NOT NULL, '" +
            COLUMN_UOM_CODE + "' TEXT NOT NULL, '" +
            COLUMN_RETURN_REF_NO_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_RETURN_DATE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_REASON_CODE_CAPS + "' TEXT , " + "'" +
            COLUMN_REASON_NAME_CAPS + "' TEXT , " + "'" +
            COLUMN_SAL_QTY_CAPS + "' INTEGER , '" +
            COLUMN_SAL_UOM + "' TEXT , '" +
            COLUMN_UNSAL_REASON_CODE_CAPS + "' TEXT , " + "'" +
            COLUMN_UNSAL_REASON_NAME_CAPS + "' TEXT , " + "'" +
            COLUMN_UNSAL_QTY_CAPS + "' INTEGER, '" +
            COLUMN_UNSAL_UOM + "' TEXT, '" +
            COLUMN_RETURN_TYPE + "' TEXT NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL)";

    String CREATE_PURCHASE_FULL_RETURN = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PURCHASE_FULL_RETURN + "'('" +
            SNO + "' INTEGER PRIMARY KEY, '" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            GRN_NO + "' TEXT NOT NULL, '" +
            COLUMN_RETURN_REF_NO_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_RETURN_DATE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_REASON_CODE_CAPS + "' TEXT , " + "'" +
            COLUMN_REASON_NAME_CAPS + "' TEXT , " + "'" +
            COLUMN_SALES_VALUE + "' TEXT NOT NULL , '" +
            COLUMN_REMARKS_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL)";


    // Create SalesReturn Table Query
    String CREATE_REASONS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_REASONS + "'('" +
            COLUMN_REASON_CODE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_REASON_NAME_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_SCREEN_NAME_CAPS + "' TEXT NOT NULL)";


    // Create SalesReturn Table Query
    String CREATE_LEAVE_REASONS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_LEAVE_REASONS + "'('" +
            COLUMN_REASON_CODE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_REASON_NAME_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_REASON_TYPE + "' TEXT NOT NULL)";

    // Create SalesReturn Table Query
    String CREATE_FEEDBACK_REASON = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_FEEDBACK_REASON + "'('" +
            COLUMN_REASON_CODE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_REASON_NAME_CAPS + "' TEXT NOT NULL)";


    /****
     * Front End Purpose
     ****/

    // Create RetailerVisit Table Query
    String CREATE_RETAILER_VISIT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_RETAILER_VISIT + "' ('" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            COLUMN_IS_TODAY_BEAT + "' TEXT NOT NULL, '" +
            COLUMN_ORDER_VISIT + "' TEXT, " + "'" +
            COLUMN_RETURN_VISIT + "' TEXT, '" +
            COLUMN_STOCK_VISIT + "' TEXT, '" +
            COLUMN_COLLECTION_VISIT + "' TEXT, '" +
            COLUMN_SURVEY_VISIT + "' TEXT, '" +
            COLUMN_SYNC_VISIT + "' TEXT, '" +
            COLUMN_BILL_VISIT + "' TEXT, '" +
            COLUMN_LATITUDE + "' TEXT, '" +
            COLUMN_LONGITUDE + "' TEXT, '" +
            COLUMN_RETLR_LATITUDE + "' TEXT, '" +
            COLUMN_RETLR_LONGITUDE + "' TEXT, '" +
            COLUMN_OUT_DISTANCE + "' TEXT, '" +
            COLUMN_START_TIME + "' TEXT, '" +
            COLUMN_END_TIME + "' TEXT, '" +
            COLUMN_COVERAGE_DATE + "' TEXT, '" +
            COLUMN_IS_VISIT + "' TEXT, '" +
            COLUMN_IS_NEWOUTLET + "' TEXT, PRIMARY KEY ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_ROUTE_CODE + "', '" +
            COLUMN_CUSTOMER_CODE + "'))";

    // Create SyncProgress Table Query
    String CREATE_SYNC_PROGRESS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SYNC_PROGRESS + "' ('" +
            COLUMN_LOGIN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, '" +
            COLUMN_DAY_START + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, '" +
            COLUMN_DAY_CLOSE + "' TEXT NOT NULL)";

    /****
     * NewRetailer
     ****/

    // Create NewRetailer Table Query  + COLUMN_RETLR_CODE_CAPS + "' TEXT, '"
    String CREATE_NEW_RETAILER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_NEW_RETAILER + "' ('" +
            COLUMN_NEW_RETAILER_REF_NO + "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_NAME + "' TEXT , '" +
            COLUMN_CUSTOMER_NAME + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT, '" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ADDRESS1 + "' TEXT NOT NULL, '" +
            COLUMN_ADDRESS2 + "' TEXT, '" +
            COLUMN_ADDRESS3 + "' TEXT, '" +
            COLUMN_PHONE_NUMBER + "' TEXT NOT NULL, '" +
            COLUMN_MOBILE_NUMBER + "' TEXT NOT NULL, '" +
            COLUMN_TIN_NUMBER + "' TEXT , " + "'" +
            COLUMN_LATITUDE + "' TEXT, '" +
            COLUMN_LONGITUDE + "' TEXT, '" +
            COLUMN_IMAGE + "' TEXT, '" +
            COLUMN_CONTACT_PERSON + "' TEXT, '" +
            COLUMN_POSTAL_CODE + "' TEXT, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT, '" +
            GSTNO + "' TEXT, '" +
            PANNO + "' TEXT, '" +
            GST_TYPE + "' TEXT, '" +
            COLUMN_DATE + "' TEXT, '" +
            COLUMN_MENU_REASON + "' TEXT, '" +
            COLUMN_DIGITAL_SIGNATURE + "' TEXT, '" +
            GST_STATE_CODE + "' TEXT, '" +
            COLUMN_CHANNEL_NAME + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_NAME + "' TEXT NOT NULL, '" +
            COLUMN_GROUP_NAME + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_NAME + "' TEXT NOT NULL, '" +
            COLUMN_OTP_VERIFIED_FLAG + "' TEXT NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT, '" +
            COLUMN_CMP_CUSTOMER_CODE + "' TEXT, '" +
            COLUMN_CUSTOMER_SHARED_FLAG + "' TEXT)";

    /****
     * CustomerModel UploadStatus
     ****/
    // Create CustomerUploadStatus Table Query

    String CREATE_CUSTOMER_UPLOAD_STATUS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CUSTOMER_UPLOAD_STATUS + "' ('" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ORDER_UPLOADED + "' TEXT NOT NULL, " + "'" +
            COLUMN_RETURNS_UPLOADED + "' TEXT NOT NULL, '" +
            COLUMN_STOCK_CAPTURE_UPLOADED + "' TEXT NOT NULL, '" +
            COLUMN_COLLECTIONS_UPLOADED + "' TEXT NOT NULL, " + "'" +
            COLUMN_BILL_UPLOADED + "' TEXT NOT NULL, " + "'" +
            COLUMN_SURVEY_UPLOADED + "' TEXT NOT NULL, PRIMARY KEY ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_ROUTE_CODE + "', '" +
            COLUMN_CUSTOMER_CODE + "'))";

    /****
     * Retailer Category
     ****/

    // Create RetailerCategory Table Query
    String CREATE_RETAILER_CATEGORY = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_RETAILER_CATEGORY + "' ('" +
            COLUMN_RET_CATEGORY_REF_NO + "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_NAME + "' TEXT, '" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_NAME + "' TEXT, '" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GROUP_NAME + "' TEXT, '" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_NAME + "' TEXT)";

    // Create RetailerChannels Table Query
    String CREATE_RETAILER_CHANNELS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_RETAILER_CHANNELS + "' ('" +
            COLUMN_RET_CHANNEL_REF_NO + "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_NAME + "' TEXT)";

    // Create RetailerSubChannels Table Query
    String CREATE_RETAILER_SUB_CHANNELS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_RETAILER_SUBCHANNELS + "' ('" +
            COLUMN_RET_SUB_CHANNEL_REF_NO + "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT, '" +
            COLUMN_SUB_CHANNEL_NAME + "' TEXT)";

    // Create RetailerGroups Table Query
    String CREATE_RETAILER_GROUPS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_RETAILER_GROUPS + "' ('" +
            COLUMN_RET_GRPS_REF_NO + "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT, " + "'" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_GROUP_NAME + "' TEXT)";

    // Create RetailerClasses Table Query
    String CREATE_RETAILER_CLASSES = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_RETAILER_CLASSES + "' ('" +
            COLUMN_RET_CLASS_REF_NO + "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_NAME + "' TEXT)";

    /****
     * Bank Names
     ****/

    // Create BankNames Table Query
    String CREATE_BANK_NAMES = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BANK_NAMES + "'('" +
            COLUMN_BANK_NAME_REF_NO + "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_BANK_CODE + "' TEXT NOT NULL, '" +
            COLUMN_BANK_NAME + "' TEXT NOT NULL, '" +
            COLUMN_BANK_BRANCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_BANK_BRANCH_NAME + "' TEXT NOT NULL)";

    // Create RetailerCategoryId Table Query
    String CREATE_RETAILER_CATEGORY_ID = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_RETAILER_CATEGORY_ID + "'('" +
            COLUMN_DISTR_CODE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_ID_CAPS + "' TEXT, '" +
            COLUMN_RETLR_CODE_CAPS + "'  TEXT)";

    // Create Order Booking Tracker
    String CREATE_ORDER_BOOKING_TRACKER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ORDER_BOOKING_TRACKER + "' ('" +
            COLUMN_ORDER_BOOKING_REF_NO + "' INTEGER PRIMARY KEY AUTOINCREMENT,'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            COLUMN_START_TIME + "' TEXT,'" +
            COLUMN_END_TIME + "' TEXT, upload TEXT NOT NULL)";


    String CREATE_FEEDBACK = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_FEEDBACK + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_FEEDBACK_NO + "' TEXT NOT NULL, '" +
            COLUMN_FEEDBACK_DATE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_LABLE_VALUE + "' TEXT NOT NULL, '" +
            MSG_BODY + "' TEXT NOT NULL, '" +
            COLUMN_IMAGE + "' TEXT, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL)";

    // Create Order Booking Tracker
    String CREATE_BILLING_TRACKER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BILLING_TRACKER + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT,'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            COLUMN_START_TIME + "' TEXT,'" +
            COLUMN_END_TIME + "' TEXT, upload TEXT NOT NULL)";


    /****
     * Image
     ****/
    // Create Image Table Query
    String CREATE_IMAGE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_IMAGE + "'('" +
            COLUMN_BASE_64_IMG + "' TEXT)";

    // Create UOM Master Table Query
    String CREATE_UOM_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_UOM_MASTER + "' ('" +
            COLUMN_UOM_REF_NO + "' INTEGER , " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_PATH + "' TEXT , '" +
            COLUMN_PROD_HIER_PATH_NAME + "' TEXT , '" +
            COLUMN_UOM_CODE + "' TEXT NOT NULL, '" +
            COLUMN_UOM_DESCRIPTION + "' TEXT NOT NULL, '" +
            COLUMN_BASE_UOM + "' TEXT NOT NULL, " + "'" +
            COLUMN_CONVERSION_FACTOR + "' INTEGER NOT NULL , " +
            "PRIMARY KEY ('" + COLUMN_UOM_REF_NO + "', '" + COLUMN_CMP_CODE + "', '" + COLUMN_DISTR_CODE + "', '" + COLUMN_SALESMAN_CODE + "' , '"
            + COLUMN_SALESMAN_CODE + "', '" + COLUMN_PROD_CODE + "', '" + COLUMN_UOM_CODE + "', '" + COLUMN_BASE_UOM + "', '" + COLUMN_CONVERSION_FACTOR + "'))";

    // Create VanSales Uom Master  Table Query
    String CREATE_VANSALES_UOM_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_VANSALES_UOM_MASTER + "' ('" +
            COLUMN_UOM_REF_NO + "' INTEGER , " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_PATH + "' TEXT , '" +
            COLUMN_PROD_HIER_PATH_NAME + "' TEXT , '" +
            COLUMN_UOM_CODE + "' TEXT NOT NULL, '" +
            COLUMN_UOM_DESCRIPTION + "' TEXT NOT NULL, '" +
            COLUMN_BASE_UOM + "' TEXT NOT NULL, " + "'" +
            COLUMN_CONVERSION_FACTOR + "' INTEGER NOT NULL , " +
            "PRIMARY KEY ('" + COLUMN_UOM_REF_NO + "', '" + COLUMN_CMP_CODE + "', '" + COLUMN_DISTR_CODE + "', '" + COLUMN_SALESMAN_CODE + "' , '"
            + COLUMN_SALESMAN_CODE + "', '" + COLUMN_PROD_CODE + "', '" + COLUMN_UOM_CODE + "', '" + COLUMN_BASE_UOM + "', '" + COLUMN_CONVERSION_FACTOR + "'))";

    // Create table for  must sell products
    String CREATE_MUSTSELL_SKU_PRODUCTS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_MUSTSELL_SKU_PRODUCTS + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            REF_NO + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT)";

    // Create table for must sell retailer class based
    String CREATE_MUSTSELL_RETAILERS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_MUSTSELL_RETAILERS + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            REF_NO + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT)";


    // Create table for  focus brand products
    String CREATE_FOCUSBRAND_PRODUCTS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_FOCUSBRAND_PRODUCTS + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            REF_NO + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT)";

    // Create table for focus brand retailer class based
    String CREATE_FOCUSBRAND_RETAILERS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_FOCUSBRAND_RETAILERS + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            REF_NO + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT)";


    // Create table for  GST STATE master
    String CREATE_GST_STATE_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_GST_STATE_MASTER + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
//            .append(COLUMN_DISTR_CODE).append("' TEXT NOT NULL, '")
            GST_STATE_CODE + "' TEXT NOT NULL, '" +
            GST_STATE_NAME + "' TEXT NOT NULL, '" +
            IS_UNION_TERRITORY + "' TEXT)";


    // Create table for  attribute master
    String CREATE_ATTRIBUTE_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ATTRIBUTE_MASTER + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            ATTRIBUTE_CODE + "' TEXT NOT NULL, '" +
            ATTRIBUTE_NAME + "' TEXT NOT NULL, '" +
            COLUMN_REMARKS_CAPS + "' TEXT , " +
            "PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + COLUMN_DISTR_CODE + "', '" + ATTRIBUTE_CODE + "'))";


    // Create table for  attribute value master
    String CREATE_ATTRIBUTE_VALUES = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ATTRIBUTE_VALUES + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            ATTRIBUTE_CODE + "' TEXT NOT NULL, '" +
            ATTRIBUTE_VALUE_CODE + "' TEXT NOT NULL, '" +
            ATTRIBUTE_VALUE_NAME + "' TEXT NOT NULL , " +
            "PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + COLUMN_DISTR_CODE + "', '" + ATTRIBUTE_CODE + "' , '" + ATTRIBUTE_VALUE_CODE + "'))";


    // Create table for  attribute value master
    String CREATE_OTHER_ATTRIBUTES = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_OTHER_ATTRIBUTES + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            ATTRIBUTE_CODE + "' TEXT NOT NULL, '" +
            ATTRIBUTE_VALUE_CODE + "' TEXT NOT NULL, '" +
            ATTRIBUTE_INPUT_VALUES + "' TEXT NOT NULL, '" +
            REF_NO + "' TEXT NOT NULL, '" +
            SNO + "' TEXT NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT  , " +
            "PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + COLUMN_DISTR_CODE + "', '" + ATTRIBUTE_CODE + "' , '"
            + ATTRIBUTE_VALUE_CODE + "' , '" + REF_NO + "' , '" + SNO + "'))";


    /**
     * Product Tagging table creation
     */
    String CREATE_PRODUCT_TAGGING_TABLE = "create table if not exists " +
            PRODUCT_TAGGING_TABLENAME + " (" +
            PRODUCT_TAGGING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PRODUCT_TAGGING_PRODUCT_CODE + " text, " +
            PRODUCT_TAGGING_TAGGED_PRODUCT_CODE +
            " text)";

    // Create RetailerClasses Table Query
    String CREATE_MESSAGES = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_MESSAGES + "' ('" +
            COLUMN_CMP_CODE + "' TEXT  NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALESMAN_CODE + "' TEXT , '" +
            MSG_CODE + "' INTEGER NOT NULL, '" +
            MSG_TITLE + "' TEXT NOT NULL, '" +
            MSG_BODY + "' TEXT NOT NULL, '" +
            MSG_DETAIL + "' TEXT NOT NULL , '" +
            ATTACH_FILE_NAME + "' TEXT, '" +
            COLUMN_DATE + "' TEXT NOT NULL, '" +
            DOWNLOAD_STATUS + "' TEXT NOT NULL, '" +
            MSG_STATUS + "' TEXT  NOT NULL, '" +
            MSG_DELETE_STATUS + "' TEXT  NOT NULL)";

    String CREATE_NOTIFICATION_TYPE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_NOTIFICATION_TYPE + "' ('" +
            COLUMN_CMP_CODE + "' TEXT, '" +
            COLUMN_NOTIFICATION_TYPE + "' TEXT NOT NULL, '" +
            COLUMN_NOTIFICATION_NAME + "' TEXT  NOT NULL)";


    /*  ***************************************************************************************  */


    // Create SalesmanTracker Table Query
    String CREATE_SALES_MAN_TRACKER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALESMAN_TRACKER + "' ('" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            COLUMN_START_TIME + "' NUMBER, '" +
            COLUMN_END_TIME + "' NUMBER, " +
            "PRIMARY KEY ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_ROUTE_CODE + "', '" +
            COLUMN_CUSTOMER_CODE + "'))";


    // Create Purchase Order Report Table create Query
    String CREATE_PURCHASE_ORDER_REPORT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PURCHASE_ORDER_REPORT + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PURCHASE_ORDER_NO + "' TEXT NOT NULL, '" +
            COLUMN_PURCHASE_ORDER_VALUE + "' NUMBER NOT NULL, '" +
            COLUMN_ORDER_DATE + "' TEXT NOT NULL, '" +
            COLUMN_NO_OF_ITEMS + "' TEXT NOT NULL )";


    // Create Purchase Receipt Report Table create Query
    String CREATE_PURCHASE_RECEIPT_REPORT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PURCHASE_RECEIPT_REPORT + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_PURCHASE_ORDER_VALUE + "' NUMBER NOT NULL, '" +
            COLUMN_ORDER_DATE + "' TEXT NOT NULL, '" +
            COLUMN_NO_OF_ITEMS + "' TEXT NOT NULL )";


    // Create distributor stock products Report Table create Query
    String CREATE_DISTRIBUTOR_STOCK_REPORT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_DISTRIBUTOR_STOCK_REPORT + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_NAME + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_MRP_CAPS + "' NUMBER NOT NULL, '" +
            COLUMN_PURCHASE_PRICE + "' NUMBER NOT NULL, '" +
            COLUMN_SELL_PRICE_CAPS + "' NUMBER NOT NULL, '" +
            COLUMN_DEFAULT_UOMID_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_STOCK_IN_HAND_CAPS + "' NUMBER NOT NULL, '" +
            COLUMN_NO_OF_DAYS + "' NUMBER NOT NULL, '" +
            COLUMN_LAST_BILL_DATE + "' TEXT NOT NULL," +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + COLUMN_DISTR_CODE + "', '" + COLUMN_PROD_CODE + "'))";

    // Create Manual Quick Action Table create Query
    String CREATE_QUICK_ACTION_MENU_TABLE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_QUICK_ACTION_MENUS + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            KEY_ICON_NAME + "' TEXT NOT NULL, '" +
            ICON_IS_ACTIVE + "' TEXT NOT NULL )";

    // Create Auto Quick Action Report Table create Query
    String CREATE_AUTO_QUICK_ACTION_MENU_TABLE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_AUTO_QUICK_ACTION_MENUS + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_KEY_SCREEN_NAME + "' TEXT NOT NULL, '" +
            COLUMN_KEY_SCREEN_COUNT + "' INTEGER )";

    // Create billing Collection Table Query
    String CREATE_BILLING_COLLECTION = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BILLING_COLLECTION + "' ('" +
            INVOICE_NO + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL,'" +
            COLUMN_COLLECTION_DATE + "' NUMBER NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CASH_AMT + "' NUMBER NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL)";


    // Create Previous Order Booking Product Details Table Query
    String CREATE_PREVIOUS_ORDER_BOOKING = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PREVIOUS_ORDER_BOOKED_PROD_DETAILS + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            ORDER_NO + "' TEXT NOT NULL, '" +
            COLUMN_ORDER_DATE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT, '" +
            COLUMN_ORDER_VALUE + "' NUMBER, '" +
            COLUMN_ORDER_QTY_CAPS + "' NUMBER, '" +
            COLUMN_UOM_ID + "' TEXT NOT NULL ," +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_SALESMAN_CODE + "','" + COLUMN_CUSTOMER_CODE + "','"
            + ORDER_NO + "','" + COLUMN_PROD_CODE + "' ))";

    // Create Previous Orders Invoices Table Query
    String CREATE_REATILER_PREVIOUS_ORDER_INVOICES = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_REATILER_PREVIOUS_ORDER_INVOICES + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            ORDER_NO + "' TEXT NOT NULL, '" +
            COLUMN_ORDER_DATE + "' TEXT NOT NULL, '" +
            COLUMN_ORDER_VALUE + "' NUMBER, '" +
            COLUMN_NO_OF_ITEMS + "' NUMBER, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_SALESMAN_CODE + "','" + COLUMN_CUSTOMER_CODE + "','"
            + ORDER_NO + "'))";

    // Create Tax Structure Table Query
    String CREATE_TAX_STRUCTURE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_TAX_STRUCTURE + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_PATH + "' TEXT , '" +
            COLUMN_PROD_HIER_PATH_NAME + "' TEXT , '" +
            TAX_STATE + "' TEXT NOT NULL, '" +
            TAX_TYPE + "' TEXT NOT NULL, " + "'" +
            TAX_CODE + "' TEXT NOT NULL, '" +
            TAX_DESC + "' TEXT NOT NULL, '" +
            TAX_NAME + "' TEXT NOT NULL, '" +
            TAX_EFFECTIVE_FROM + "' TEXT , '" +
            SCHEME_REDUCE + "' TEXT, '" +
            CASH_DISCOUNT + "' TEXT, '" +
            DB_DISCOUNT + "' TEXT, '" +
            INPUT_TAX_PERC + "' NUMBER, '" +
            INPUT_APPLY_ON + "' TEXT, '" +
            OUTPUT_TAX_PERC + "' NUMBER, '" +
            OUTPUT_APPLY_ON + "' TEXT, '" +
            UPLOAD_FLAG + "' TEXT NOT NULL DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + TAX_STATE + "','" + TAX_TYPE + "','"
            + TAX_CODE + "','" + TAX_EFFECTIVE_FROM + "','" + TAX_NAME + "','" + COLUMN_PROD_CODE + "'))";

    // Create Scheme Definition List Table Query
    String CREATE_SCHEME_DEFINITION_LIST = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_DEFINITION_LIST + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            SCHEME_CODE + "' TEXT NOT NULL, '" +
            SCHEME_DESC + "' TEXT, '" +
            SCHEME_BASE + "' TEXT NOT NULL, '" +
            SCHEME_FROM_DT + "' TEXT , '" +
            SCHEME_TO_DT + "' TEXT , '" +
            SCHEME_PAYOUT_TYPE + "' TEXT  NOT NULL DEFAULT 'N', '" +
            COLUMN_IS_SKU_LEVEL + "' TEXT  NOT NULL DEFAULT 'N', '" +
            COLUMN_IS_ACTIVE + "' TEXT  NOT NULL DEFAULT 'N', '" +
            COMBI + "' TEXT  NOT NULL DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + SCHEME_CODE + "','" + SCHEME_BASE + "'))";


    // Create Scheme Combi Products  Table Query
    String CREATE_SCHEME_COMBI_PRODUCTS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_COMBI_PRODUCTS + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            SCHEME_CODE + "' TEXT NOT NULL, '" +
            SCHEME_SLAB_NO + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME + "' TEXT, '" +
            MIN_VALUE + "' TEXT DEFAULT 'N', '" +
            COLUMN_ISMANDATORY + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + SCHEME_CODE + "','" + SCHEME_SLAB_NO + "','" + COLUMN_PROD_CODE + "'))";


    // Create Scheme Combi Products  Table Query
    String CREATE_SCHEME_ATTRIBUTES = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_ATTRIBUTES + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            ATTRIBUTE_CODE + "' TEXT NOT NULL, '" +
            ATTRIBUTE_VALUE_CODE + "' TEXT NOT NULL, '" +
            ATTRIBUTE_INPUT_VALUES + "' TEXT , '" +
            REF_NO + "' TEXT NOT NULL, '" +
            SNO + "' NUMBER NOT NULL, '" +
            UPLOAD_FLAG + "' TEXT DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + ATTRIBUTE_CODE + "','" + REF_NO + "','" + SNO + "'))";

    // Create Scheme Retailer Category List Table Query
    String CREATE_SCHEME_RETAILER_CATRGORY_LIST = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_RETAILER_CATRGORY_LIST + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            SCHEME_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL , '" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, '" +
            UPLOAD_FLAG + "' TEXT DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + SCHEME_CODE + "','" + COLUMN_CHANNEL_CODE + "','"
            + COLUMN_SUB_CHANNEL_CODE + "','" + COLUMN_GROUP_CODE + "','" + COLUMN_CLASS_CODE + "'))";


    // Create Scheme CustomerModel List Table Query
    String CREATE_SCHEME_CUSTOMER_LIST = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_CUSTOMER_LIST + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            SCHEME_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            UPLOAD_FLAG + "' TEXT DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + SCHEME_CODE + "','" + COLUMN_CUSTOMER_CODE + "'))";

    // Create Scheme Retailer Category List Table Query
    String CREATE_SCHEME_SLAB_LIST = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_SLAB_LIST + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            SCHEME_CODE + "' TEXT NOT NULL, '" +
            SCHEME_SLAB_NO + "' NUMBER NOT NULL, '" +
            SCHEME_SLAB_FROM + "' NUMBER  NOT NULL , '" +
            SCHEME_SLAB_TO + "' NUMBER   NOT NULL, '" +
            SCHEME_SLAB_PAYOUT + "' NUMBER   NOT NULL, '" +
            SCHEME_SLAB_RANGE + "' TEXT DEFAULT 'N', '" +
            COLUMN_UOM_CODE + "' TEXT, '" +
            FOR_EVERY + "' TEXT, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + SCHEME_CODE + "','" + SCHEME_SLAB_NO + "'))";

    // Create SchemeProducts List Table Query
    String CREATE_SCHEME_PRODUCTS_LIST = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_PRODUCT_LIST + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            SCHEME_CODE + "' TEXT NOT NULL , '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME + "' TEXT , " + "'" +
            UPLOAD_FLAG + "' TEXT DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + SCHEME_CODE + "','" + COLUMN_PROD_CODE + "'))";

    // Create Scheme Slab Products List Table Query
    String CREATE_SCHEME_SLAB_PRODUCTS_LIST = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_SLAB_PRODUCT_LIST + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            SCHEME_CODE + "' TEXT NOT NULL, '" +
            SCHEME_SLAB_NO + "' NUMBER  NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME + "' TEXT , " + "'" +
            SCHEME_QTY + "' NUMBER NOT NULL , '" +
            COLUMN_ISMANDATORY + "' TEXT DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + SCHEME_CODE + "', '" + COLUMN_PROD_CODE + "', '" + SCHEME_SLAB_NO + "'))";


    // Create SalesmanTracker Table Query
    String CREATE_ORDERED_APPLIED_SCHEME = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ORDERED_APPLIED_SCHEME + "' ('" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            READABLE_INV_NO + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            ORDER_NO + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT NOT NULL, '" +
            SCHEME_CODE + "' TEXT NOT NULL , '" +
            SCHEME_SLAB_NO + "' TEXT NOT NULL, '" +
            FREE_PROD_CODE + "' TEXT , '" +
            FREE_PROD_NAME + "' TEXT , '" +
            FREE_QTY + "' TEXT , '" +
            DISCOUNT_AMOUNT + "' TEXT , '" +
            DISCOUNT_PERCENTAGE + "' TEXT , '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL ," +
            " PRIMARY KEY ('" + COLUMN_DISTR_CODE + "', '" + COLUMN_CMP_CODE + "', '"+ COLUMN_CUSTOMER_CODE + "', '" + ORDER_NO + "', '" + COLUMN_PROD_CODE + "', '" + COLUMN_BATCH_CODE
            + "', '" + SCHEME_CODE + "', '" + SCHEME_SLAB_NO + "'))";


    // Create SalesmanTracker Table Query
    String CREATE_BILLING_APPLIED_SCHEME = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BILLING_APPLIED_SCHEME + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            ORDER_NO + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT NOT NULL, '" +
            SCHEME_CODE + "' TEXT NOT NULL , '" +
            SCHEME_SLAB_NO + "' TEXT NOT NULL, '" +
            FREE_PROD_CODE + "' TEXT , '" +
            FREE_PROD_BATCH_CODE + "' TEXT , '" +
            FREE_PROD_NAME + "' TEXT , '" +
            FREE_QTY + "' NUMBER , '" +
            COLUMN_INVOICE_QTY + "' NUMBER , '" +
            DISCOUNT_AMOUNT + "' NUMBER , '" +
            DISCOUNT_PERCENTAGE + "' NUMBER , '" +
            COLUMN_COMPLETE_FLAG + "' TEXT DEFAULT 'N' , '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL ) ";

    // Create SalesmanTracker Table Query
    String CREATE_CONFIRM_OREDR_APPLIED_SCHEME = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CONFIRM_ORDER_APPLIED_SCHEME + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '" +
            ORDER_NO + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_BATCH_CODE + "' TEXT NOT NULL, '" +
            SCHEME_CODE + "' TEXT NOT NULL , '" +
            SCHEME_SLAB_NO + "' TEXT NOT NULL, '" +
            FREE_PROD_CODE + "' TEXT , '" +
            FREE_PROD_NAME + "' TEXT , '" +
            FREE_QTY + "' NUMBER , '" +
            DISCOUNT_AMOUNT + "' NUMBER , '" +
            DISCOUNT_PERCENTAGE + "' NUMBER , '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL ) ";

    // Create SchemeProducts List Table Query
    String CREATE_SCHEME_PRODUCTS_CATRGORY_LIST = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_PRODUCT_CATRGORY_LIST + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            SCHEME_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_LVL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_VAL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_HIER_VALUE_NAME + "' TEXT, '" +
            UPLOAD_FLAG + "' TEXT DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + SCHEME_CODE + "', '" + COLUMN_PROD_HIER_LVL_CODE + "', '" + COLUMN_PROD_HIER_VAL_CODE + "'))";


    // Create RetailerClasses Table Query
    String CREATE_SALES_HIERARCHY = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_HIERARY + "' ('" +
            KEY_ID + "' TEXT NOT NULL, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            SF_TYPE + "' TEXT NOT NULL, '" +
            SH_LAST_LEVEL_CODE + "' TEXT NOT NULL, '" +
            SH_LAST_LEVEL_NAME + "' TEXT NOT NULL)";

    // Create RetailerClasses Table Query
    String CREATE_SALES_HIERARCHY_VALUE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_HIERARY_VALUE + "' ('" +
            KEY_ID + "' TEXT NOT NULL, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALES_FORCE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALES_FORCE_LEVEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALES_FORCE_NAME + "' TEXT NOT NULL, '" +
            LOB_CODE + "' TEXT NOT NULL, '" +
            PARENT_CODE + "' TEXT NOT NULL)";

    // Create RetailerClasses Table Query
    String CREATE_FIELD_ACTIVITY_REPORT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_FIELD_ACTIVITY_REPORT + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_NAME + "' TEXT, '" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            OPENING_STOCK_VAL + "' NUMBER, '" +
            CLOSING_STOCK_VAL + "' NUMBER, '" +
            COLUMN_ORDER_VALUE + "' NUMBER, '" +
            COLUMN_DATE + "' NUMBER )";

    // Create StockTake Table Query
    String CREATE_OPENING_STOCK_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_OPENING_STOCK_MASTER + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE_CAPS + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME_CAPS + "' TEXT NOT NULL )";

    String CREATE_STOCK_LEDGER_REPORT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_STOCK_LEDGER_REPORT + "'('" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_NAME + "' TEXT , '" +
            COLUMN_TRANS_DATE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME + "' TEXT NOT NULL, '" +
            COLUMN_OPENING_SALES_QUANTITY + "' NUMBER, '" +
            COLUMN_OPENING_UNSALABLE_QUANTITY + "'NUMBER, '" +
            COLUMN_OPENING_OFFER_QTY + "' NUMBER,'" +
            COLUMN_PURCHASE_SALABLEQTY + "' NUMBER,'" +
            COLUMN_PURCHASE_UNSALABLEQTY + "'NUMBER,'" +
            COLUMN_PURCHASE_OFFERQTY + "' NUMBER, '" +
            COLUMN_PURCHASE_RET_SALABLE_QTY + "' NUMBER,'" +
            COLUMN_PURCHASE_RET_UNSALABLE_Qty + "' NUMBER, '" +
            COLUMN_PURCHASE_RET_OFFER_QTY + "' NUMBER, '" +
            COLUMN_SALES_SALABLE_QTY + "' NUMBER, '" +
            COLUMN_SALES_UNSALABLE_QTY + "' NUMBER, '" +
            COLUMN_SALES_OFFER_QTY + "' NUMBER, '" +
            COLUMN_SALES_RET_SALABLE_QTY + "' NUMBER, '" +
            COLUMN_SALES_RET_UNSALABLE_QTY + "' NUMBER, '" +
            COLUMN_SALES_RET_OFFER_QTY + "' NUMBER, '" +
            COLUMN_ADJIN_SALABLE_QTY + "' NUMBER, '" +
            COLUMN_ADJIN_UNSALABLE_QTY + "' NUMBER, '" +
            COLUMN_ADJIN_OFFER_QTY + "' NUMBER, '" +
            COLUMN_ADJOUT_SALABLE_QTY + "' NUMBER, '" +
            COLUMN_ADJOUT_UNSALABLE_QTY + "' NUMBER, '" +
            COLUMN_ADJOUT_OFFER_QTY + "' NUMBER, '" +
            COLUMN_CLOSINGSTK_SALABLEQTY + "' NUMBER, '" +
            COLUMN_CLOSINGSTK_UNSALABLE_QTY + "' NUMBER, '" +
            COLUMN_CLOSINGSTK_OFFER_QTY + "' NUMBER)";

    String DROP_TABLE = "DROP TABLE IF EXISTS ";

    String CREATE_LOGIN_TIME_CAPTURE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_LOGIN_TIME_CAPTURE + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT , '" +
            USER_CODE + "' TEXT NOT NULL, '" +
            USER_TYPE + "' TEXT NOT NULL, '" +
            COLUMN_DATE + "' TEXT NOT NULL, '" +
            COLUMN_MODE + "' TEXT NOT NULL, '" +
            COLUMN_PROCESS + "' TEXT NOT NULL, '" +
            COLUMN_LATITUDE + "' TEXT, '" +
            COLUMN_LONGITUDE + "' TEXT, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, '" +
            COLUMN_STATUS + "'TEXT NOT NULL, '" +
            COLUMN_SYNC_DATE + "'DATE, '" +
            COLUMN_SYNC_TIME + "'TEXT, '" +
            COLUMN_SYNC_END_TIME + "'TEXT)";

    // Create Banner Table Query
    String CREATE_BANNER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BANNER + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_BANNER_DESC + "' TEXT NOT NULL, '" +
            COLUMN_FILE_NAME + "' TEXT NOT NULL, '" +
            COLUMN_ORG_FILE_NAME + "' TEXT NOT NULL, '" +
            COLUMN_FILE_TYPE + "' TEXT NOT NULL)";

    // Create ORDER_PRODUCT_FILTERS Table Query
    String CREATE_ORDER_PRODUCT_FILTERS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ORDER_PRODUCT_FILTERS + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_HIER_LEVEL_CODE_1 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_NAME_1 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_CODE_2 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_NAME_2 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_CODE_3 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_NAME_3 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_CODE_4 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_NAME_4 + "' TEXT, '" +
            COLUMN_LOB_CODE + "' TEXT)";

    // Create VAN_SALES_PRODUCT_FILTERS Table Query
    String CREATE_VAN_SALES_PRODUCT_FILTERS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_VAN_SALES_PRODUCT_FILTERS + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_HIER_LEVEL_CODE_1 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_NAME_1 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_CODE_2 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_NAME_2 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_CODE_3 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_NAME_3 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_CODE_4 + "' TEXT, '" +
            COLUMN_HIER_LEVEL_NAME_4 + "' TEXT)";


    // Create Scheme Route Zip List Table Query
    String CREATE_ROUTE_SCHEME_ZIP_LIST = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ROUTE_SCHEME_ZIP_LIST + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GZIP_SCHEME_DATA + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_SALESMAN_CODE + "','" + COLUMN_ROUTE_CODE + "'))";


    // Create Scheme Route Zip List Table Query
    String CREATE_SCHEME_ZIP_LIST = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_ZIP_LIST + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GZIP_SCHEME_DATA + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_CUSTOMER_CODE + "'))";


    // Create Scheme Retailer Category List Table Query
    String CREATE_RETAILER_SCHEME_PRODUCTS_LIST = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_RETAILER_SCHEME_PRODUCTS_LIST + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME + "' TEXT , '" +
            SCHEME_CODE + "' TEXT NOT NULL, '" +
            SCHEME_BASE + "' TEXT NOT NULL, '" +
            SCHEME_PAYOUT_TYPE + "' TEXT NOT NULL, '" +
            COLUMN_START_TIME + "' TEXT , '" +
            COLUMN_END_TIME + "' TEXT , '" +
            COMBI + "' TEXT, '" +
            SCHEME_DESC + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_SALESMAN_CODE + "','" + COLUMN_ROUTE_CODE + "','"
            + COLUMN_CUSTOMER_CODE + "','" + COLUMN_PROD_CODE + "','" + SCHEME_CODE + "'))";

    // Create Scheme Route Zip List Table Query
    String CREATE_NEW_RETAILER_IMAGE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_NEW_RETAILER_IMAGES + "'('" +
            COLUMN_REF_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_IMAGE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_REF_CODE + "','" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_CUSTOMER_CODE + "'))";

    String CREATE_CODE_GENERATOR = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CODE_GENERATOR + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SCREEN_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_PREFIX + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUFFIX_YY + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUFFIX_NN + "' INTEGER NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_SCREEN_NAME + "'))";

    String CREATE_DISTRIBUTOR_TABLE = "CREATE TABLE IF NOT EXISTS '" + TABLE_DISTRIBUTOR + "' ('"
            + COLUMN_CMP_CODE + "' TEXT NOT NULL, '"
            + COLUMN_DISTR_CODE + "' TEXT NOT NULL, '"
            + COLUMN_DISTR_NAME + "' TEXT NOT NULL, '"
            + COLUMN_DISTR_ADDRESS1 + "' TEXT NOT NULL DEFAULT '', '"
            + COLUMN_DISTR_ADDRESS2 + "' TEXT DEFAULT '',"
            + COLUMN_DISTR_ADDRESS3 + "'' TEXT DEFAULT '', '"
            + COLUMN_PINCODE + "' TEXT NOT NULL, '"
            + COLUMN_PHONE_NUMBER + "' TEXT DEFAULT '', '"
            + COLUMN_MOBILE_NO + "' TEXT  DEFAULT '', '"
            + COLUMN_CONTACT_PERSON + "' TEXT DEFAULT '', '"
            + COLUMN_MAIL_ID + "' TEXT DEFAULT '', '"
            + COLUMN_BRANCH_CODE + "' TEXT NOT NULL, '"
            + COLUMN_BRANCH_NAME + "' TEXT NOT NULL, '"
            + COLUMN_GST_STATE_CODE + "' TEXT NOT NULL, '"
            + COLUMN_GEO_STATE_CODE + "' TEXT NOT NULL, '"
            + COLUMN_GEO_HIER_PATH + "' TEXT NOT NULL, '"
            + COLUMN_DRUG_LICENCE_NO1 + "' TEXT DEFAULT '','"
            + COLUMN_DRUG_LICENCE_EXPIRY1 + "' TEXT  DEFAULT '','"
            + COLUMN_DAY_OFF + "' int NOT NULL ,'"
            + COLUMN_FSSAI_NO + "' TEXT DEFAULT '','"
            + COLUMN_FSSAI_EXPIRY_DATE + "' TEXT  DEFAULT '','"
            + COLUMN_PDA_DISTRIBUTOR + "' TEXT DEFAULT '','"
            + COLUMN_DISTR_STATUS + "' TEXT DEFAULT '','"
            + COLUMN_DISTR_TYPE_NAME + "' TEXT NOT NULL  ,'"
            + COLUMN_GST_DISTR_TYPE + "' TEXT DEFAULT '', '"
            + COLUMN_GSTIN_NO + "' TEXT  DEFAULT '','"
            + COLUMN_PAN_NO + "' TEXT DEFAULT '','"
            + COLUMN_AADHAR_NO + "' TEXT DEFAULT '','"
            + COLUMN_LOAD_STOCK_PROD + "' TEXT DEFAULT 'N','"
            + COLUMN_UPLOAD + "' TEXT NOT NULL DEFAULT 'N' ,"
            + "PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + COLUMN_DISTR_CODE + "'))";

    String CREATE_DISTRIBUTOR_STOCK = "CREATE TABLE IF NOT EXISTS '" + TABLE_DISTRIBUTOR_STOCK + "' ('"
            + COLUMN_CMP_CODE + "' TEXT NOT NULL, '"
            + COLUMN_DISTR_CODE + "' TEXT NOT NULL, '"
            + COLUMN_PROD_CODE + "' TEXT NOT NULL, '"
            + COLUMN_SALEABLE_QTY + "' TEXT NOT NULL DEFAULT '', '"
            + COLUMN_SALEABLE_STOCK + "' TEXT,"
            + "PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + COLUMN_DISTR_CODE +"' , '" + COLUMN_PROD_CODE + "'))";

    String CREATE_CUSTOMER_STOCK = "CREATE TABLE IF NOT EXISTS '" + TABLE_CUSTOMER_STOCK + "' ('"
            + COLUMN_CMP_CODE + "' TEXT NOT NULL, '"
            + COLUMN_DISTR_CODE + "' TEXT NOT NULL, '"
            + COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, '"
            + COLUMN_STOCK + "' TEXT NOT NULL, '"
            + COLUMN_PROD_CODE + "' TEXT NOT NULL, '"
            + COLUMN_SOQ + "' NUMBER DEFAULT 0, '"
            + COLUMN_PPQ + "' NUMBER DEFAULT 0,"
            + "PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + COLUMN_DISTR_CODE +"' , '" + COLUMN_CUSTOMER_CODE +"' , '" + COLUMN_PROD_CODE + "'))";

    String CREATE_WD_STORAGE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_WD_STORAGE + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_WET_SQFT + "' INTEGER, " + "'" +
            COLUMN_FROZEN_SQFT + "' INTEGER, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "'))";

    String CREATE_WD_FREEZER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_WD_FREEZER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_FREEZER_CAPACITY + "' INTEGER NOT NULL, " + "'" +
            COLUMN_UNIT + "' INTEGER NOT NULL, " + "'" +
            COLUMN_TOTAL_CAPACITY + "' INTEGER NOT NULL, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_FREEZER_CAPACITY + "'))";

    String CREATE_CUSTOMER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CUSTOMER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_COMPANY_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_PIN_CODE + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_SHIP_PHONENO + "' TEXT, " + "'" +
            COLUMN_MOBILE_NO + "' TEXT, " + "'" +
            COLUMN_CONTACT_PERSON + "' TEXT, " + "'" +
            COLUMN_EMAIL_ID + "' TEXT, " + "'" +
            COLUMN_DAY_OFF + "' INTEGER NOT NULL, " + "'" +
            COLUMN_RETAILER_STATUS + "' TEXT, " + "'" +
            COLUMN_FFSAI_NO + "' TEXT, " + "'" +
            COLUMN_DRUG_LIC_NO + "' TEXT, " + "'" +
            COLUMN_DRUG_LIC_EXPIRY_DATE + "' TEXT, " + "'" +
            COLUMN_CREDIT_BILLS + "' TEXT, " + "'" +
            COLUMN_CREDIT_DAYS + "' TEXT, " + "'" +
            COLUMN_CREDIT_LIMIT + "' TEXT, " + "'" +
            COLUMN_CASH_DISC_PERC + "' TEXT, " + "'" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_STORE_TYPE + "' TEXT, " + "'" +
            COLUMN_PARENT_CUSTOMER_CODE + "' TEXT, " + "'" +
            COLUMN_LATITUDE + "' TEXT, " + "'" +
            COLUMN_LONGITUDE + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_TYPE + "' TEXT, " + "'" +
            GSTNO + "' TEXT, " + "'" +
            PANNO + "' TEXT, " + "'" +
            COLUMN_APPROVAL_STATUS + "' TEXT, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_CUSTOMER_CODE + "'))";

    String CREATE_CUSTOMER_SHIP_ADDRESS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CUSTOMER_SHIP_ADDRESS + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_SHIP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_SHIP_ADDR1 + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_SHIP_ADDR2 + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_SHIP_ADDR3 + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_SHIP_CITY + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_DEFAULT_SHIP_GEOHIERPATH + "' TEXT NOT NULL, " + "'" +
            GST_STATE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_SHIP_PHONENO + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_DEFAULT_SHIP_ADDR + "' TEXT, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_CUSTOMER_CODE + "','" + COLUMN_CUSTOMER_SHIP_CODE + "'))";

    String CREATE_CUSTOMER_ROUTE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CUSTOMER_ROUTE + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_COVERAGE_SEQUENCE + "' INTEGER NOT NULL, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_CUSTOMER_CODE + "','" + COLUMN_ROUTE_CODE + "'))";

    String CREATE_CUSTOMER_BANK = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CUSTOMER_BANK + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_BANK_Code + "' TEXT NOT NULL, " + "'" +
            COLUMN_BANK_NAME + "' TEXT, " + "'" +
            COLUMN_BANK_BRANCH_NAME + "' TEXT, " + "'" +
            COLUMN_IFSC_CODE + "' TEXT, " + "'" +
            COLUMN_ACC_TYPE + "' TEXT, " + "'" +
            COLUMN_ACC_NUMBER + "' TEXT, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_CUSTOMER_CODE + "','" + COLUMN_BANK_Code + "'))";

    String CREATE_KYC_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_KYC_MASTER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_KYC_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_KYC_DESC + "' TEXT , " + "'" +
            COLUMN_UPLOAD + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_KYC_CODE + "'))";

    String CREATE_LOB_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_LOB + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_LOB_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_LOB_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_LOB_CODE + "'))";

    String CREATE_DISTR_LOB = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_DISTR_LOB + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_LOB_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_LOB_CODE + "'))";

    String CREATE_SALESMAN_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALESMAN_MASTER + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SALESMAN_NAME + "' TEXT NOT NULL, '" +
            COLUMN_MOBILE_NO + "' TEXT , '" +
            COLUMN_EMAIL_ID + "' TEXT , " + "'" +
            COLUMN_SALARY + "' INTEGER , '" +
            COLUMN_STATUS + "' TEXT , '" +
            COLUMN_SSFA_ENABLED + "' TEXT , " + "'" +
            COLUMN_BANK_NAME + "' TEXT , " + "'" +
            COLUMN_ACC_NO + "' TEXT , " + "'" +
            COLUMN_IFSC_CODE + "' TEXT , " + "'" +
            COLUMN_BRANCH_NAME + "' TEXT , " + "'" +
            COLUMN_AADHAR_NO + "' TEXT , " + "'" +
            COLUMN_SALESMAN_IMAGE + "' TEXT , " + "'" +
            COLUMN_CANCELLED_CHEQUE_IMAGE + "' TEXT , " + "'" +
            TAG_MOD_DT + "' TEXT NOT NULL , " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, PRIMARY KEY ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "'))";


    String CREATE_SALESMAN_LOB_MAPPING = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALESMAN_LOB_MAPPING + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_LOB_CODE + "' TEXT NOT NULL, '" +
            //COLUMN_IS_SELECTED + "' TEXT DEFAULT 'Y' , '" +
            COLUMN_IS_SELECTED + "' TEXT  , '" +
            TAG_MOD_DT + "' TEXT NOT NULL , " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, PRIMARY KEY ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_LOB_CODE + "'))";

    String CREATE_SALESMAN_ROUTE_MAPPING = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALESMAN_ROUTE_MAPPING + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_IS_SELECTED + "' TEXT  , '" +
            TAG_MOD_DT + "' TEXT NOT NULL , " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, PRIMARY KEY ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_ROUTE_CODE + "'))";


    String CREATE_SALESMAN_KYC = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALESMAN_MASTER_KYC + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, '" +
            COLUMN_TYPE + "' TEXT NOT NULL, '" +
            COLUMN_KYC_IMAGE + "' TEXT  , '" +
            TAG_MOD_DT + "' TEXT NOT NULL , " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, PRIMARY KEY ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_TYPE + "', '" +
            COLUMN_DISTR_CODE + "'))";


    String CREATE_LOGIN = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_LOGIN + "'('" +
            COLUMN_LOGIN_CODE + "' TEXT NOT NULL, " + "'" +
            CREDENTIAL + "' TEXT NOT NULL, " + "'" +
            COLUMN_USER_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_USER_STATUS + "' TEXT NOT NULL, " + "'" +
            COLUMN_NEW_PASSWORD + "' TEXT NOT NULL, " + "'" +
            COLUMN_LOGIN_STATUS + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_LOGIN_CODE + "'))";


    String CREATE_DISTRIBUTOR_BALANCE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_DISTRIBUTOR_BALANCE + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_BALANCE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "'))";

    String CREATE_BRANCH_STOCK = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BRANCH_STOCK + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_BRANCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_STOCK + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPDATED_TIME + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_BRANCH_CODE + "','" + COLUMN_PROD_CODE + "'))";

    String CREATE_PREVIOUS_ORDER_BOOKING_STATUS = "CREATE TABLE IF NOT EXISTS '" +

            TABLE_PREVIOUS_ORDER_BOOKING_STATUS + "' ('" +

            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +

            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +

            COLUMN_ORDER_NO + "' TEXT NOT NULL, '" +

            COLUMN_ORDER_STATUS + "' TEXT NOT NULL, '" +

            COLUMN_ACTION_TIME + "' TEXT NOT NULL, '" +

            COLUMN_FREE_TEXT + "' TEXT NOT NULL , " +

            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_ORDER_STATUS + "' , '" + COLUMN_ORDER_NO + "'))";

    String CREATE_COMPANY_DETAIL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_COMPANY_DETAIL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CMP_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_CMP_ADDR1 + "' TEXT, " + "'" +
            COLUMN_CMP_ADDR2 + "' TEXT, " + "'" +
            COLUMN_CMP_ADDR3 + "' TEXT, " + "'" +
            COLUMN_CITY + "' TEXT NOT NULL, " + "'" +
            COLUMN_STATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_COUNTRY + "' TEXT NOT NULL, " + "'" +
            COLUMN_POSTAL_CODE + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "'))";

    String CREATE_PRODUCT_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PRODUCT_MASTER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_NAME + "' TEXT, " + "'" +
            COLUMN_PROD_SHORT_NAME_CAPS + "' TEXT, " + "'" +
            COLUMN_PROD_STATUS + "' TEXT, " + "'" +
            COLUMN_PROD_TYPE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_SHELF_LIFE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_GROSS_WGT + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_NET_WGT + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_EAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_HIER_LAST_VAL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_WGT_TYPE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_LOB_DIVISTION_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_LOB_SUB_DIVISION_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_HSN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_HIER_PATH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_HIER_PATH_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_PROD_CODE + "'))";

    String CREATE_PRODUCT_BATCH_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PRODUCT_BATCH_MASTER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT, " + "'" +
            COLUMN_PROD_BATCH_CODE + "' TEXT, " + "'" +
            COLUMN_FROM_LEVEL + "' TEXT, " + "'" +
            COLUMN_TO_LEVEL + "' TEXT NOT NULL, " + "'" +
            COLUMN_LATEST_BATCH + "' TEXT NOT NULL, " + "'" +
            COLUMN_PURCHASE_PRICE + "' TEXT NOT NULL, " + "'" +
            COLUMN_MRP_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_SELL_PRICE_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_MANF_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_EXPIRY_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SELL_RATE_WITH_TAX + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_PROD_CODE + "','" + COLUMN_PROD_BATCH_CODE + "'))";

    String CREATE_PRODUCT_HIER_LEVEL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PRODUCT_HIER_LEVEL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_HIER_LVL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_HIER_LEVEL_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_PROD_HIER_LVL_CODE + "'))";


    String CREATE_PRODUCT_HIER_VALUE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PRODUCT_HIER_VALUE + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_HIER_LVL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_HIER_VAL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_HIER_VALUE_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_PARENT_CODE + "' TEXT, " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_PROD_HIER_LVL_CODE + "','" + COLUMN_PROD_HIER_VAL_CODE + "'))";

    String CREATE_PRODUCT_UOM_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PRODUCT_UOM_MASTER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UOM_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UOM_DESCRIPTION + "' TEXT NOT NULL, " + "'" +
            COLUMN_CONVERSION_FACTOR + "' TEXT NOT NULL, " + "'" +
            COLUMN_BASE_UOM + "' TEXT NOT NULL, " + "'" +
            COLUMN_DEFAULT_UOM + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_PROD_CODE + "','" + COLUMN_UOM_CODE + "'))";

    String CREATE_PRODUCT_TAX_MASTER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PRODUCT_TAX_MASTER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TAX_STATE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CGST + "' TEXT NOT NULL, " + "'" +
            COLUMN_SGST + "' TEXT NOT NULL, " + "'" +
            COLUMN_IGST + "' TEXT NOT NULL, " + "'" +
            COLUMN_CESS + "' TEXT NOT NULL, " + "'" +
            COLUMN_ADDITIONAL_TAX + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_TAX_STATE_CODE + "','" + COLUMN_PROD_CODE + "'))";

    String CREATE_GEO_HIER_LEVEL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_GEO_HIER_LEVEL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT NOT NULL, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            COLUMN_GEO_HIER_LEVEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GEO_LEVEL_NAME + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_GEO_HIER_LEVEL_CODE + "'))";


    String CREATE_GEO_HIER_VALUE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_GEO_HIER_VALUE + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT NOT NULL, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            COLUMN_GEO_HIER_LEVEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GEO_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GEO_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_PARENT_CODE + "' TEXT, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_GEO_HIER_LEVEL_CODE + "','" + COLUMN_GEO_CODE + "'))";

    String CREATE_STOCK_ON_HAND = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_STOCK_ON_HAND + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GODOWN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALEABLE_QTY + "' INTEGER, " + "'" +
            COLUMN_UNSALEABLE_QTY + "' INTEGER, " + "'" +
            COLUMN_OFFER_QTY + "' TEXT NOT NULL, " + "'" +
            COLUMN_RESV_SALEABLE_QTY + "' TEXT NOT NULL, " + "'" +
            COLUMN_RESV_UNSALEABLE_QTY + "' TEXT NOT NULL, " + "'" +
            COLUMN_RESV_OFFER_QTY + "' TEXT NOT NULL, " + "'" +
            COLUMN_AVAIL_QTY + "' INTEGER, '" +
            COLUMN_LAST_ORD_QTY + "' INTEGER, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_PROD_CODE + "','" + COLUMN_PROD_BATCH_CODE + "','" + COLUMN_GODOWN_CODE + "'))";

    String CREATE_SALESMAN_ROUTE_COVERAGE_PLAN = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALESMAN_ROUTE_COVERAGE_PLAN + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PLAN_TYPE + "' TEXT NOT NULL, " + "'" +
            COLUMN_START_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_END_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DAY_NAME + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT NOT NULL DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_ROUTE_CODE + "','" +
            COLUMN_SALESMAN_CODE + "','" + COLUMN_DAY_NAME + "'))";

    String CREATE_SALESMAN_ROUTE_COVERAGE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALESMAN_ROUTE_COVERAGE + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_COVERAGE_DT + "' TEXT NOT NULL, " + "'" +
            COLUMN_CONFIRM + "' TEXT NOT NULL DEFAULT 'N', " + "'" +
            UPLOAD_FLAG + "'N TEXT NOT NULL, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_ROUTE_CODE + "','" + COLUMN_SALESMAN_CODE + "','" + COLUMN_COVERAGE_DT + "'))";

    String CREATE_SPREVIOUS_PURCHASE_ORDERS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PREVIOUS_PURCHASE_ORDERS + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PURCHASE_ORDER_NO + "' TEXT NOT NULL, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PURCHASE_PRICE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ORDER_QTY_CAPS + "' TEXT NOT NULL, " + "'" +
            COLUMN_UOM_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TAX_AMT + "' TEXT NOT NULL, " + "'" +
            CGST_PERCENT + "' TEXT NOT NULL, " + "'" +
            CGST_VALUE + "' TEXT NOT NULL, " + "'" +
            SGST_PERCENT + "' TEXT NOT NULL, " + "'" +
            SGST_VALUE + "' TEXT NOT NULL, " + "'" +
            IGST_PERCENT + "' TEXT NOT NULL, " + "'" +
            IGST_VALUE + "' TEXT NOT NULL, " + "'" +
            UTGST_PERCENT + "' TEXT NOT NULL, " + "'" +
            UTGST_VALUE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ORDER_VALUE + "' TEXT NOT NULL, " + "'" +
            COLUMN_APPROVAL_STATUS + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_PURCHASE_ORDER_NO + "','" + COLUMN_PROD_CODE + "','" + COLUMN_PROD_BATCH_CODE + "'))";

    // Create the Table to save the encrypted keya and values
    String CREATE_KEY_VALUE_ENCODE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_KEY_VALUE_ENCODE + "' ('" +
            KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '" +
            COLUMN_MASTER_NAME + "' TEXT, '" +
            COLUMN_MASTER_VALUE + "' TEXT)";


    String CREATE_ROUTE_SEQUENCING = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_ROUTE_SEQUENCING + "'('" +
            COLUMN_REF_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_COVERAGE_SEQUENCE + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','"
            + COLUMN_DISTR_CODE + "','"
            + COLUMN_SALESMAN_CODE + "','"
            + COLUMN_ROUTE_CODE + "','"
            + COLUMN_CUSTOMER_CODE + "','"
            + COLUMN_COVERAGE_SEQUENCE + "'))";

    String CREATE_BILL_ADJUSTMENT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BILL_ADJUSTMENT + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_COLLECTION_NO + "' TEXT NOT NULL, " + "'" +
            COLUMN_COLLECTION_MODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ADJUSTMENT_MODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ADJUST_REF_NO + "' TEXT NOT NULL, " + "'" +
            COLUMN_ADJUSTMENT_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ADJUSTMENT_AMT + "' TEXT, " + "'" +
            COLUMN_ADJUST_BALANCE + "' TEXT, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_COLLECTION_NO + "'," +
            "'" + COLUMN_COLLECTION_MODE + "', '" + COLUMN_ADJUSTMENT_MODE + "', '" + COLUMN_ADJUST_REF_NO + "'))";

    String CREATE_SUPPLIER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SUPPLIER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUPPLIER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUPPLIER_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUPPLIER_ADDR + "' TEXT, " + "'" +
            COLUMN_SUPPLIER_PHONE + "' TEXT, " + "'" +
            COLUMN_SUPPLIER_EMAILID + "' TEXT, " + "'" +
            COLUMN_SUPPLIER_CONTACT_PERSON + "' TEXT, " + "'" +
            COLUMN_SUPPLIER_GST_STATE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUPPLIER_GSTIN + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUPPLIER_STATUS + "' TEXT, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_SUPPLIER_CODE + "'))";

    String CREATE_PURCHASE_INVOICE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PURCHASE_INVOICE + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_NUM + "' TEXT NOT NULL, " + "'" +
            COLUMN_GRN_NO + "' TEXT, " + "'" +
            COLUMN_INVOICE_DT + "' TEXT NOT NULL, " + "'" +
            COLUMN_GRN_DATE + "' TEXT, " + "'" +
            COLUMN_SUPPLIER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PURCH_PRICE + "' TEXT, " + "'" +
            COLUMN_MRP_CAPS + "' TEXT, " + "'" +
            COLUMN_INVOICE_QTY + "' INTEGER, " + "'" +
            COLUMN_RECEIVED_QTY + "' INTEGER, " + "'" +
            COLUMN_UOM_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_LINE_LEVEL_GROSS_AMT + "' TEXT, " + "'" +
            COLUMN_LINE_LEVEL_TAX_AMT + "' TEXT, " + "'" +
            COLUMN_LINE_LEVEL_DISC_AMT + "' TEXT, " + "'" +
            COLUMN_LINE_LEVEL_NET_AMT + "' TEXT, " + "'" +
            COLUMN_GRN_STATUS + "' TEXT, " + "'" +
            CONFIRM_STATUS + "' TEXT DEFAULT 'N', " + "'" +
            CONFIRM_DATE + "' TEXT, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_INVOICE_NUM + "'," +
            "'" + COLUMN_PROD_CODE + "', '" + PROD_BATCH_CODE + "'))";

    String CREATE_LOGISTIC_MATERIAL_STOCK = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_LOGISTIC_MATERIAL_STOCK + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_NUM + "' TEXT NOT NULL, " + "'" +
            COLUMN_MATERIAL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_MATERIAL_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_RECEIVED_QTY + "' INTEGER DEFAULT 0, " + "'" +
            COLUMN_RETURNED_QTY + "' INTEGER DEFAULT 0, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_INVOICE_NUM + "', '" + COLUMN_MATERIAL_CODE + "'))";

    String CREATE_BILL_INVOICE_HEADER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BILL_INVOIC_HEADER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_NUM + "' TEXT NOT NULL, " + "'" +
            COLUMN_GODOWN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_PATTERN + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_SHIP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_DT + "' DATE NOT NULL, " + "'" +
            COLUMN_SHIP_DT + "' DATE NOT NULL, " + "'" +
            COLUMN_DELIVERY_DT + "' DATE NOT NULL, " + "'" +
            COLUMN_INVOICE_TYPE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_MODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PAYMENT_MODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TOT_SPL_DISC_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_SCH_DISC_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_CASH_DISC_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_DB_DISC_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_WD_DISC_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_CR_NOTE_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_DB_NOTE_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_ON_ACCOUNT_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_MARKET_RETURN_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_REPLACEMENT_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_OTHER_CHARGES_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_INV_LEVEL_DISC_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_INV_LEVEL_DISC_PERC + "' TEXT DEFAULT '0', " + "'" +
            COLUMN_TOT_GROSS_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_ADDITION + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_DEDUCTION + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_TAX_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_ROUND_OFF_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_TOT_NET_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_BALANCE_AMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_ORDER_NO + "' TEXT, " + "'" +
            COLUMN_INVOICE_STATUS + "' TEXT NOT NULL, " + "'" +
            COLUMN_REMARKS + "' TEXT, " + "'" +
            COLUMN_LATITUDE + "' TEXT, " + "'" +
            COLUMN_LONGITUDE + "' TEXT, " + "'" +
            COLUMN_START_TIME + "' TEXT, " + "'" +
            COLUMN_END_TIME + "' TEXT, " + "'" +
            COLUMN_LINE_COUNT + "' int, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            COLUMN_COMPLETE_FLAG + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', '" + COLUMN_INVOICE_NUM + "'))";

    String CREATE_BILL_INVOICE_DETAILS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BILL_INVOICE_DETAILS + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_NUM + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TOTAL_ORD_QTY + "' int, " + "'" +
            COLUMN_TOTAL_INVOICE_QTY + "' int, " + "'" +
            FREE_QTY + "' int, " + "'" +
            COLUMN_PARENT_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PARENT_PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_QTY1 + "' int, " + "'" +
            COLUMN_UOM_CODE1 + "' TEXT, " + "'" +
            COLUMN_INVOICE_QTY2 + "' int, " + "'" +
            COLUMN_UOM_CODE2 + "' TEXT, " + "'" +
            COLUMN_MRP_CAPS + "' TEXT, " + "'" +
            COLUMN_SELL_RATE + "' TEXT, " + "'" +
            COLUMN_ACTUAL_SELL_RATE + "' TEXT, " + "'" +
            COLUMN_GROSS_AMT + "' TEXT, " + "'" +
            COLUMN_SPL_DISC_AMT + "' TEXT, " + "'" +
            COLUMN_SPL_DISC_PERC + "' TEXT, " + "'" +
            COLUMN_SCH_DISC_AMT + "' TEXT, " + "'" +
            COLUMN_SCH_DISC_PERC + "' TEXT, " + "'" +
            COLUMN_CASH_DISC_AMT + "' TEXT, " + "'" +
            COLUMN_CASH_DISC_PERC + "' TEXT, " + "'" +
            COLUMN_DB_DISC_AMT + "' TEXT, " + "'" +
            COLUMN_DB_DISC_PERC + "' TEXT, " + "'" +
            COLUMN_INPUT_STR + "' TEXT NOT NULL, " + "'" +
            CGST_PERCENT + "' TEXT, " + "'" +
            CGST_AMT + "' TEXT, " + "'" +
            SGST_PERCENT + "' TEXT, " + "'" +
            SGST_AMT + "' TEXT, " + "'" +
            IGST_PERCENT + "' TEXT, " + "'" +
            IGST_AMT + "' TEXT, " + "'" +
            UTGST_PERCENT + "' TEXT, " + "'" +
            UTGST_VALUE + "' TEXT, " + "'" +
            CESS_PERCENT + "' TEXT, " + "'" +
            CESS_VALUE + "' TEXT, " + "'" +
            COLUMN_TAX_AMT + "' TEXT, " + "'" +
            COLUMN_NET_AMT + "' TEXT, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            COLUMN_COMPLETE_FLAG + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', '" + COLUMN_INVOICE_NUM + "','" + COLUMN_PROD_CODE + "', '" + COLUMN_PROD_BATCH_CODE + "', '" + COLUMN_PARENT_PROD_CODE + "','" + COLUMN_PARENT_PROD_BATCH_CODE + "'))";


    String CREATE_BILL_INVOICE_SAVED_DETAILS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BILL_INVOICE_SAVED_DETAILS + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_NUM + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TOTAL_INVOICE_QTY + "' int, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', '" + COLUMN_INVOICE_NUM + "','" + COLUMN_PROD_CODE + "', '" + COLUMN_PROD_BATCH_CODE + "'))";

    String CREATE_RATE_EDIT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_RATE_EDIT + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_LEVEL + "' TEXT, " + "'" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PRODUCT_LEVEL + "' TEXT, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ALLOW_EDIT + "' TEXT, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', '" + COLUMN_CUSTOMER_CODE + "','" + COLUMN_PROD_CODE + "'))";

    String CREATE_CUSTOMER_PRODUCT_MAPPING = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CUSTOMER_PRODUCT_MAPPING + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_LEVEL + "' TEXT, " + "'" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PRODUCT_LEVEL + "' TEXT, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_START_DT + "' TEXT NOT NULL, " + "'" +
            COLUMN_END_DT + "' TEXT NOT NULL, " + "'" +
            COLUMN_ALLOW_EDIT + "' TEXT, " + "'" +
            COLUMN_PROD_TYPE + "' TEXT, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_CUSTOMER_CODE + "','" + COLUMN_PROD_CODE + "'))";

    String CREATE_CONTRACT_PRICING = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CONTRACT_PRICING + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CP_REF_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CP_REF_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_FROM_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TO_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISC_TYPE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_APPLY_ON + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISC_PERC + "' TEXT NULL, " + "'" +
            COLUMN_FLAT_AMT + "' TEXT NULL, " + "'" +
            COLUMN_SPL_PRICE + "' TEXT NULL, " + "'" +
            COLUMN_SPL_PRICE_WITH_TAX + "' TEXT NULL, " + "'" +
            COLUMN_NET_RATE + "' TEXT NULL, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', '" + COLUMN_CP_REF_CODE + "', '" + COLUMN_DISC_TYPE + "', '" + COLUMN_CUSTOMER_CODE + "', '" + COLUMN_PROD_CODE + "'))";

    // Create OrderBooking Report Header Table Query
    String CREATE_PREVIOUS_ORDER_BOOKING_REPORT_HEADER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PREVIOUS_ORDER_BOOKING_REPORT_HEADER + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ORDER_NO + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_REF_NO + "' TEXT NOT NULL, '" +
            COLUMN_CUSTOMER_SHIP_CODE + "' TEXT NOT NULL , '" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ORDER_DT + "' TEXT NOT NULL, '" +
            COLUMN_TOTAL_ORDER_VALUE + "' TEXT, '" +
            COLUMN_TOTAL_GROSS_VALUE + "' TEXT, '" +
            COLUMN_REMARKS + "' TEXT, '" +
            COLUMN_LATITUDE + "' TEXT, '" +
            COLUMN_LONGITUDE + "' TEXT, " + "'" +
            COLUMN_START_TIME + "' TEXT, '" +
            COLUMN_END_TIME + "' TEXT, '" +
            COLUMN_ORDER_STATUS + "' TEXT NOT NULL, '" +
            COLUMN_TOTAL_DISCOUNT + "' TEXT NOT NULL, '" +
            COLUMN_TOTAL_TAX + "' TEXT NOT NULL, '" +
            UPLOAD_FLAG + "' TEXT NOT NULL DEFAULT 'N' , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_ORDER_NO + "'))";

    // Create OrderBooking Report Header Table Query
    String CREATE_PREVIOUS_ORDER_BOOKING_REPORT_DETAILS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PREVIOUS_ORDER_BOOKING_REPORT_DETAILS + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ORDER_NO + "' TEXT NOT NULL, '" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME + "' TEXT NOT NULL, '" +
            PROD_BATCH_CODE + "' TEXT NOT NULL , '" +
            COLUMN_ORDER_QTY + "' INTEGER  NOT NULL, " + "'" +
            COLUMN_SERVICED_QTY + "' INTEGER, '" +
            COLUMN_UOM_CODE + "' TEXT NOT NULL, '" +
            COLUMN_INPUT_STR + "' TEXT NOT NULL, '" +
            COLUMN_SELL_RATE + "' TEXT NOT NULL, '" +
            COLUMN_ACTUAL_SELL_RATE + "' TEXT NOT NULL, '" +
            COLUMN_SCH_AMT + "' TEXT, '" +
            COLUMN_TAX_AMT + "' TEXT, '" +
            COLUMN_TAX_CODE + "' TEXT, '" +
            CGST_PERCENT + "' TEXT, '" +
            CGST_AMT + "' TEXT, '" +
            SGST_PERCENT + "' TEXT, '" +
            SGST_AMT + "' TEXT, '" +
            UTGST_PERCENT + "' TEXT, '" +
            UTGST_VALUE + "' TEXT, '" +
            IGST_PERCENT + "' TEXT, '" +
            IGST_VALUE + "' TEXT, '" +
            COLUMN_GROSS_VALUE + "' TEXT, '" +
            COLUMN_ORDER_VALUE + "' TEXT, '" +
            COLUMN_PROD_TYPE + "' TEXT, '" +
            UPLOAD_FLAG + "' TEXT NOT NULL DEFAULT 'N' , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_ORDER_NO + "'," +
            " '" + COLUMN_PROD_CODE + "', '" + PROD_BATCH_CODE + "'))";

    String CREATE_COLLECTION = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_COLLECTION + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_COLLECTION_NO + "' TEXT NOT NULL, " + "'" +
            COLUMN_COLLECTION_MODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_COLLECTION_DT + "' TEXT NOT NULL, " + "'" +
            COLUMN_COLLECTION_AMT + "' TEXT NOT NULL, " + "'" +
            COLUMN_BILL_ADJ_AMT + "' TEXT, " + "'" +
            COLUMN_CR_DB_ADJ_AMT + "' TEXT, " + "'" +
            COLUMN_CANCELLED_STAUTS + "' TEXT, " + "'" +
            COLUMN_INSTRUMENT_NO + "' TEXT NOT NULL, " + "'" +
            COLUMN_INSTRUMENT_DT + "' TEXT, " + "'" +
            COLUMN_RTR_BANK_CODE + "' TEXT, " + "'" +
            COLUMN_RTR_BANK_BR_CODE + "' TEXT, " + "'" +
            COLUMN_RTR_BANK_NAME + "' TEXT, " + "'" +
            COLUMN_DISTR_BANK_CODE + "' TEXT, " + "'" +
            COLUMN_DISTR_BANK_BR_CODE + "' TEXT, " + "'" +
            COLUMN_DISTR_BANK_NAME + "' TEXT, " + "'" +
            COLUMN_COLLECTION_TYPE + "' TEXT, " + "'" +
            COLUMN_REMARKS + "' TEXT, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_COLLECTION_NO + "'," +
            " '" + COLUMN_COLLECTION_MODE + "', '" + COLUMN_INSTRUMENT_NO + "'))";

    String CREATE_PENDING_BILLS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PENDING_BILLS + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_NUM + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_DT + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TOT_NET_AMT + "' TEXT, '" +
            COLUMN_BALANCE_OS + "' TEXT, " + "'" +
            COLUMN_MOD_DT + "' TEXT  NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT)";

    String CREATE_SALES_DB_CRNOTE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_DB_CRNOTE + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DB_CRTYPE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALES_DB_CRREFNO + "' TEXT NOT NULL, " + "'" +
            COLUMN_DB_CRDT + "' TEXT NULL, " + "'" +
            COLUMN_DB_CRREASON + "' TEXT NULL, " + "'" +
            COLUMN_DB_CRBUDGET + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_DB_CRTAXAMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_DB_CRAMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_DB_CRBAL + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_IS_SETTLED + "' TEXT, " + "'" +
            COLUMN_REMARKS + "' TEXT NOT NULL, " + "'" +
            COLUMN_IS_ACTIVE + "' TEXT NOT NULL, " + "'" +
            COLUMN_IS_CLAIMABLE + "' TEXT, " + "'" +
            COLUMN_REFERENCE_TYPE + "' TEXT NULL, " + "'" +
            COLUMN_REFERENCE_NO + "' TEXT NULL, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "', '" + COLUMN_DISTR_CODE + "', '" + COLUMN_CUSTOMER_CODE + "' , '" + COLUMN_DB_CRTYPE + "', '" + COLUMN_SALES_DB_CRREFNO + "'))";

    String CREATE_STOCK_ADJ_HEADER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_STOCK_ADJ_HEADER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GODOWN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_STOCK_ADJUSTNO + "' TEXT NOT NULL, " + "'" +
            COLUMN_TRANS_TYPE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ADJUST_DT + "' TEXT NULL, " + "'" +
            COLUMN_DOC_REFNO + "' TEXT NULL, " + "'" +
            COLUMN_REMARKS + "' TEXT NULL, " + "'" +
            COLUMN_ISMANUAL + "' TEXT NOT NULL, " + "'" +
            COLUMN_SOURCE + "' TEXT NOT NULL, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " + "'" +
            COLUMN_COMPLETE_FLAG + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_GODOWN_CODE + "', '" + COLUMN_STOCK_ADJUSTNO + "'))";

    String CREATE_STOCK_ADJ_DETAILS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_STOCK_ADJ_DETAILS + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GODOWN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_STOCK_ADJUSTNO + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_FROM_STOCK_TYPE + "' TEXT NULL, " + "'" +
            COLUMN_TO_STOCK_TYPE + "' TEXT NULL, " + "'" +
            COLUMN_ADJUST_QTY + "' INTEGER, " + "'" +
            COLUMN_REASON_CODE_CAPS + "' TEXT NULL, " + "'" +
            COLUMN_REASON_NAME_CAPS + "' TEXT NULL, " + "'" +
            COLUMN_INPUT_STR + "' TEXT NOT NULL, " + "'" +
            COLUMN_PURCHASE_RATE + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_AMOUNT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " + "'" +
            COLUMN_COMPLETE_FLAG + "' TEXT NOT NULL, " + "'" +
            COLUMN_UOM_ID + "' TEXT, " + "'" +
            COLUMN_ORDER_QTY + "' INTEGER, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_GODOWN_CODE + "', " +
            "'" + COLUMN_STOCK_ADJUSTNO + "','" + COLUMN_PROD_CODE + "' , '" + PROD_BATCH_CODE + "'))";

    String CREATE_SALES_RETURN_HEADER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_RETURN_HEADER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALES_RETURNNO + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_RETURN_DT + "' TEXT NULL, " + "'" +
            COLUMN_INVOICE_NO + "' TEXT NULL, " + "'" +
            COLUMN_RETURN_TYPE + "' TEXT NOT NULL, " + "'" +
            COLUMN_REMARKS + "' TEXT NULL, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_SALES_RETURNNO + "'))";

    String CREATE_SALES_RETURN_DETAILS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_RETURN_DETAILS + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALES_RETURNNO + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_NO + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_NAME + "' TEXT NULL, " + "'" +
            COLUMN_PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_BATCH_DETAIL + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_RETURN_QTY + "' INTEGER DEFAULT 0, " + "'" +
            COLUMN_STOCK_TYPE + "' TEXT NOT NULL, " + "'" +
            COLUMN_MRP + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_SELL_RATE + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_RETURN_AMOUNT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_REASON_CODE_CAPS + "' TEXT NULL, " + "'" +
            COLUMN_REASON_NAME_CAPS + "' TEXT NULL, " + "'" +
            COLUMN_IMAGE + "' TEXT NULL, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_SALES_RETURNNO + "', " +
            "'" + COLUMN_INVOICE_NO + "', '" + COLUMN_PROD_CODE + "', '" + COLUMN_PROD_BATCH_CODE + "', '" + COLUMN_BATCH_DETAIL + "', '" + COLUMN_STOCK_TYPE + "'))";

    // Create Scheme Distributor Widget  Table Query
    String CREATE_SCHEME_DISTRIBUTOR_BUDGET = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_DISTRIBUTOR_BUDGET + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, '" +
            SCHEME_CODE + "' TEXT NOT NULL, '" +
            COLUMN_IS_UNLIMITED + "' TEXT NOT NULL, '" +
            COLUMN_BUDGET + "' TEXT , '" +
            COLUMN_IS_ACTIVE + "' TEXT NOT NULL DEFAULT 'Y', '" +
            COLUMN_UTILIZED_AMOUNT + "' TEXT , '" +
            COLUMN_BUDGET_OS + "' TEXT , '" +
            UPLOAD_FLAG + "' TEXT DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + SCHEME_CODE + "'))";

    // Create Scheme Master Control Table Query
    String CREATE_SCHEME_MASTER_CONTROL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SCHEME_MASTER_CONTROL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            SCHEME_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CHANGE_TYPE + "' TEXT NOT NULL, '" +
            COLUMN_DESCRIPTION + "' TEXT NOT NULL, '" +
            COLUMN_FROM_VALUE + "' TEXT , '" +
            COLUMN_TO_VALUE + "' TEXT , '" +
            UPLOAD_FLAG + "' TEXT DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + SCHEME_CODE + "','" + COLUMN_CHANGE_TYPE + "','" + COLUMN_DESCRIPTION + "'))";

    String CREATE_INVOICE_CR_DB_ADJUSTMENT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_INVOICE_CR_DB_ADJUSTMENT + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_NO + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALES_DB_CRREFNO + "' TEXT NOT NULL, " + "'" +
            COLUMN_DB_CRTYPE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ADJUSTED_AMT + "' TEXT NOT NULL, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT NULL, " + "'" +
            COLUMN_DB_CRAMT + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_DB_CRBAL + "' TEXT DEFAULT '0.0000', " + "'" +
            COLUMN_DB_CRDT + "' TEXT NULL, " + "'" +
            COLUMN_DB_CRREASON + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_INVOICE_NO + "','" + COLUMN_SALES_DB_CRREFNO + "'))";

    String CREATE_DISTR_REPORT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_DISTR_REPORT + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROCESS_NAME + "' TEXT NOT NULL, '" +
            COLUMN_PROCESS_DATA + "' TEXT NOT NULL, '" +
            COLUMN_PROCESS_TYPE + "' TEXT, '" +
            COLUMN_PROCESS_ENABLE + "' TEXT, '" +
            COLUMN_CHANGE_NO + "' TEXT NULL, '" +
            COLUMN_CONSOLE_FLAG + "' TEXT NULL, '" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_PROCESS_NAME + "', '" + COLUMN_PROCESS_TYPE + "'))";


    String CREATE_SALES_REPORT_DETAIL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_REPORT_DETAIL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_NUM + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_DT + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TOTAL_INVOICE_QTY + "' int, " + "'" +
            COLUMN_MRP_CAPS + "' TEXT, " + "'" +
            COLUMN_SELL_RATE + "' TEXT, " + "'" +
            COLUMN_ACTUAL_SELL_RATE + "' TEXT, " + "'" +
            COLUMN_GROSS_AMT + "' TEXT, " + "'" +
            COLUMN_SPL_DISC_AMT + "' TEXT, " + "'" +
            COLUMN_SPL_DISC_PERC + "' TEXT, " + "'" +
            COLUMN_SCH_DISC_AMT + "' TEXT, " + "'" +
            COLUMN_SCH_DISC_PERC + "' TEXT, " + "'" +
            COLUMN_CASH_DISC_AMT + "' TEXT, " + "'" +
            COLUMN_CASH_DISC_PERC + "' TEXT, " + "'" +
            COLUMN_DB_DISC_AMT + "' TEXT, " + "'" +
            COLUMN_DB_DISC_PERC + "' TEXT, " + "'" +
            COLUMN_INPUT_STR + "' TEXT NOT NULL, " + "'" +
            CGST_PERCENT + "' TEXT, " + "'" +
            CGST_AMT + "' TEXT, " + "'" +
            SGST_PERCENT + "' TEXT, " + "'" +
            SGST_AMT + "' TEXT, " + "'" +
            IGST_PERCENT + "' TEXT, " + "'" +
            IGST_AMT + "' TEXT, " + "'" +
            COLUMN_TAX_AMT + "' TEXT, " + "'" +
            COLUMN_NET_AMT + "' TEXT, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', '" + COLUMN_INVOICE_NUM + "','" + COLUMN_PROD_CODE + "', '" + COLUMN_PROD_BATCH_CODE + "'))";

    String CREATE_SALES_DAY_WISE_REPORT_DETAIL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_DAY_WISE_REPORT_DETAIL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_MONTH_NAME + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_DAY + "' TEXT NOT NULL, '" +
            COLUMN_TOT_NET_AMT + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_MONTH_NAME + "', '" + COLUMN_INVOICE_DAY + "'))";

    String CREATE_SALES_WEEK_WISE_REPORT_DETAIL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_WEEK_WISE_REPORT_DETAIL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_MONTH_NAME + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_WEEK + "' TEXT NOT NULL, '" +
            COLUMN_TOT_NET_AMT + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_MONTH_NAME + "', '" + COLUMN_INVOICE_WEEK + "'))";

    String CREATE_SALES_CUSTOMER_WISE_REPORT_DETAIL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_CUSTOMER_WISE_REPORT_DETAIL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL,'" +
            COLUMN_CUSTOMER_NAME + "' TEXT NOT NULL, '" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CHANNEL_NAME + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_NAME + "' TEXT NOT NULL, '" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_GROUP_NAME + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_NAME + "' TEXT NOT NULL, '" +
            COLUMN_MONTH_NAME + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_WEEK + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_DAY + "' TEXT NOT NULL, '" +
            COLUMN_TOT_NET_AMT + "' TEXT , '" +
            COLUMN_INVOICE_COUNT + "' NUMBER, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', '" + COLUMN_CUSTOMER_CODE + "', " +
            "'" + COLUMN_CHANNEL_CODE + "', '" + COLUMN_SUB_CHANNEL_CODE + "', '" + COLUMN_GROUP_CODE + "', '" + COLUMN_CLASS_CODE + "', " +
            "'" + COLUMN_MONTH_NAME + "', '" + COLUMN_INVOICE_WEEK + "', '" + COLUMN_INVOICE_DAY + "'))";

    String CREATE_SALES_CHANNEL_WISE_REPORT_DETAIL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_CHANNEL_WISE_REPORT_DETAIL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CHANNEL_NAME + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, '" +
            COLUMN_SUB_CHANNEL_NAME + "' TEXT NOT NULL, '" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, '" +
            COLUMN_GROUP_NAME + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, '" +
            COLUMN_CLASS_NAME + "' TEXT NOT NULL, '" +
            COLUMN_NO_OF_CUSTOMERS + "' NUMBER, '" +
            COLUMN_TOT_NET_AMT + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', " +
            "'" + COLUMN_CHANNEL_CODE + "', '" + COLUMN_SUB_CHANNEL_CODE + "', '" + COLUMN_GROUP_CODE + "', '" + COLUMN_CLASS_CODE + "'))";

    String CREATE_PURCHASE_INVOICE_REPORT_DETAIL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_PURCHASE_INVOICE_REPORT_DETAIL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_NUM + "' TEXT NOT NULL, " + "'" +
            COLUMN_INVOICE_DT + "' TEXT NOT NULL, " + "'" +
            GRN_NO + "' TEXT NULL, '" +
            GRN_DT + "' TEXT NULL, '" +
            COLUMN_GRN_STATUS + "' TEXT, " + "'" +
            COLUMN_SUPPLIER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_NAME + "' TEXT NOT NULL, " + "'" +
            PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PURCH_PRICE + "' TEXT, '" +
            COLUMN_MRP_CAPS + "' TEXT, " + "'" +
            COLUMN_INVOICE_QTY + "' NUMBER, '" +
            COLUMN_RECEIVED_QTY + "' NUMBER, '" +
            COLUMN_UOM_CODE + "' TEXT, '" +
            COLUMN_LINE_LEVEL_GROSS_AMT + "' TEXT, " + "'" +
            COLUMN_LINE_LEVEL_TAX_AMT + "' TEXT, " + "'" +
            COLUMN_LINE_LEVEL_DISC_AMT + "' TEXT, " + "'" +
            COLUMN_LINE_LEVEL_NET_AMT + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_INVOICE_NUM + "', '" + COLUMN_PROD_CODE + "', '" + PROD_BATCH_CODE + "'))";

    String CREATE_SALES_SALESMAN_WISE_REPORT_DETAIL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_SALESMAN_WISE_REPORT_DETAIL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_NAME + "' TEXT NOT NULL, '" +
            COLUMN_MONTH_NAME + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_WEEK + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_DAY + "' TEXT NOT NULL, '" +
            COLUMN_TOT_NET_AMT + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_COUNT + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', " +
            "'" + COLUMN_SALESMAN_CODE + "', '" + COLUMN_INVOICE_WEEK + "', '" + COLUMN_INVOICE_DAY + "'))";

    String CREATE_SALES_ROUTE_WISE_REPORT_DETAIL = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_SALES_ROUTE_WISE_REPORT_DETAIL + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_NAME + "' TEXT NOT NULL, '" +
            COLUMN_MONTH_NAME + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_WEEK + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_DAY + "' TEXT NOT NULL, '" +
            COLUMN_TOT_NET_AMT + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_COUNT + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', " +
            "'" + COLUMN_ROUTE_CODE + "', '" + COLUMN_INVOICE_WEEK + "', '" + COLUMN_INVOICE_DAY + "'))";

    String CREATE_YTD_ROUTE_WISE_REPORT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_YTD_ROUTE_WISE_REPORT + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_ROUTE_NAME + "' TEXT NOT NULL, '" +
            COLUMN_QTY_1 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_1 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_2 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_2 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_3 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_3 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_4 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_4 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_5 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_5 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_6 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_6 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_7 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_7 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_8 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_8 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_9 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_9 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_10 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_10 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_11 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_11 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_12 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_12 + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', " +
            "'" + COLUMN_ROUTE_CODE + "', '" + COLUMN_ROUTE_NAME + "'))";

    String CREATE_YTD_SALESMAN_WISE_REPORT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_YTD_SALESMAN_WISE_REPORT + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SALESMAN_NAME + "' TEXT NOT NULL, '" +
            COLUMN_QTY_1 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_1 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_2 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_2 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_3 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_3 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_4 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_4 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_5 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_5 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_6 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_6 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_7 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_7 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_8 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_8 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_9 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_9 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_10 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_10 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_11 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_11 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_12 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_12 + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', " +
            "'" + COLUMN_SALESMAN_CODE + "', '" + COLUMN_SALESMAN_NAME + "'))";

    String CREATE_YTD_PRODUCT_WISE_REPORT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_YTD_PRODUCT_WISE_REPORT + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_NAME + "' TEXT NOT NULL, '" +
            COLUMN_QTY_1 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_1 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_2 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_2 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_3 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_3 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_4 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_4 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_5 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_5 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_6 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_6 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_7 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_7 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_8 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_8 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_9 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_9 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_10 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_10 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_11 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_11 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_12 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_12 + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', " +
            "'" + COLUMN_PROD_CODE + "', '" + COLUMN_PROD_NAME + "'))";

    String CREATE_YTD_CUSTOMER_WISE_REPORT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_YTD_CUSTOMER_WISE_REPORT + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_NAME + "' TEXT NOT NULL, '" +
            COLUMN_QTY_1 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_1 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_2 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_2 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_3 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_3 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_4 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_4 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_5 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_5 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_6 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_6 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_7 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_7 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_8 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_8 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_9 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_9 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_10 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_10 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_11 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_11 + "' TEXT NOT NULL, '" +
            COLUMN_QTY_12 + "' TEXT NOT NULL, '" +
            COLUMN_VALUE_12 + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', " +
            "'" + COLUMN_CUSTOMER_CODE + "', '" + COLUMN_CUSTOMER_NAME + "'))";

    String CREATE_MTD_SALES_PRODHIER_PRODUCT_WISE_REPORT = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_MTD_SALES_PRODHIER_PRODUCT_WISE_REPORT + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_NAME + "' TEXT NOT NULL, '" +
            COLUMN_MONTH_NAME + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_WEEK + "' TEXT NOT NULL, '" +
            COLUMN_INVOICE_DAY + "' TEXT NOT NULL, '" +
            COLUMN_TOT_NET_AMT + "' TEXT NOT NULL, '" +
            COLUMN_TOTAL_INVOICE_QTY + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_1 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_1 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_2 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_2 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_3 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_3 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_4 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_4 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_5 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_5 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_6 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_6 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_7 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_7 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_8 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_8 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_9 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_9 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_10 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_10 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_11 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_11 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_12 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_12 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_13 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_13 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_14 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_14 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_CODE_15 + "' TEXT NOT NULL, '" +
            COLUMN_LEVEL_NAME_15 + "' TEXT NOT NULL, " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "', " +
            "'" + COLUMN_PROD_CODE + "', '" + COLUMN_PROD_NAME + "'))";

    String CREATE_REPORT_PROCESS_MAPPING = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_REPORT_PROCESS_MAPPING + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CHANGE_NO + "' TEXT  NULL, " + "'" +
            COLUMN_PROCESS_ID + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROCESS_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_QUERY_NAME + "' TEXT NOT NULL, '" +
            COLUMN_CONSOLE_FLAG + "' TEXT , '" +
            UPLOAD_FLAG + "' TEXT , '" +
            COLUMN_MOD_DT + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_PROCESS_ID + "'))";

    String CREATE_REPORT_FIELD_MAPPING = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_REPORT_FIELD_MAPPING + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CHANGE_NO + "' TEXT  NULL, " + "'" +
            COLUMN_PROCESS_ID + "' TEXT NOT NULL, " + "'" +
            COLUMN_TABLE_NAME + "' TEXT NULL, " + "'" +
            COLUMN_HEADER + "' TEXT NULL, '" +
            COLUMN_GROUP + "' TEXT NULL, " + "'" +
            COLUMN_TYPE_ + "' TEXT NULL, '" +
            COLUMN_FIELD + "' TEXT NULL, '" +
            COLUMN_ENABLE + "' TEXT NULL, '" +
            COLUMN_CHART_ENABLE + "' TEXT NULL, '" +
            COLUMN_FILTER_ENABLE + "' TEXT NULL, '" +
            SEQUENCE + "' TEXT NULL, '" +
            COLUMN_FUNCTION + "' TEXT NULL, '" +
            COLUMN_CONSOLE_FLAG + "' TEXT , '" +
            UPLOAD_FLAG + "' TEXT , '" +
            COLUMN_MOD_DT + "' TEXT )";

    String CREATE_BULLETIN_BOARD = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BULLETIN_BOARD + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_MESSAGE_CODE + "' TEXT  NULL, " + "'" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NULL, " + "'" +
            COLUMN_GROUP_CODE + "' TEXT NULL, '" +
            COLUMN_CLASS_CODE + "' TEXT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NULL, '" +
            COLUMN_EXPIRY_DATE + "' TEXT NULL, '" +
            COLUMN_SUBJECT + "' TEXT NULL, '" +
            COLUMN_MESSAGE_DESC + "' TEXT NULL, '" +
            COLUMN_ATTACHMENT + "' TEXT NULL, '" +
            COLUMN_NOTIFICATION_TYPE + "' TEXT )";

    String CREATE_CUSTOMER_APPROVAL_DATA = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CUSTOMER_APPROVAL_DATA + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_APPROVAL_STATUS + "' TEXT , " + "'" +
            COLUMN_CUSTOMER_DATA + "' TEXT , " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_CUSTOMER_CODE + "'))";

    // Create Route Table Query
    String CREATE_TEMP_ROUTE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_TEMP_ROUTE + "' ('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL," + " '" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL," + " '" +
            COLUMN_TEMP_ROUTE_CODE + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_NAME + "' TEXT NOT NULL, '" +
            COLUMN_ROUTE_TYPE + "' TEXT DEFAULT 'S' , '" +
            COLUMN_ROUTE_CODE + "' TEXT DEFAULT NULL, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL DEFAULT 'N' , " +
            "PRIMARY KEY ('" + COLUMN_DISTR_CODE + "', '" + COLUMN_TEMP_ROUTE_CODE + "'))";

    String CREATE_TEMP_CUSTOMER = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_TEMP_CUSTOMER + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TEMP_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_PIN_CODE + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_SHIP_PHONENO + "' TEXT, " + "'" +
            COLUMN_MOBILE_NO + "' TEXT, " + "'" +
            COLUMN_CONTACT_PERSON + "' TEXT, " + "'" +
            COLUMN_EMAIL_ID + "' TEXT, " + "'" +
            COLUMN_DAY_OFF + "' INTEGER NOT NULL, " + "'" +
            COLUMN_RETAILER_STATUS + "' TEXT, " + "'" +
            COLUMN_FFSAI_NO + "' TEXT, " + "'" +
            COLUMN_DRUG_LIC_NO + "' TEXT, " + "'" +
            COLUMN_DRUG_LIC_EXPIRY_DATE + "' TEXT, " + "'" +
            COLUMN_CREDIT_BILLS + "' TEXT, " + "'" +
            COLUMN_CREDIT_DAYS + "' TEXT, " + "'" +
            COLUMN_CREDIT_LIMIT + "' TEXT, " + "'" +
            COLUMN_CASH_DISC_PERC + "' TEXT, " + "'" +
            COLUMN_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUB_CHANNEL_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GROUP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CLASS_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_STORE_TYPE + "' TEXT, " + "'" +
            COLUMN_PARENT_CUSTOMER_CODE + "' TEXT, " + "'" +
            COLUMN_LATITUDE + "' TEXT, " + "'" +
            COLUMN_LONGITUDE + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_TYPE + "' TEXT, " + "'" +
            GSTNO + "' TEXT, " + "'" +
            PANNO + "' TEXT, " + "'" +
            COLUMN_APPROVAL_STATUS + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT DEFAULT NULL, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_TEMP_CUSTOMER_CODE + "'))";

    String CREATE_TEMP_CUSTOMER_SHIP_ADDRESS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_TEMP_CUSTOMER_SHIP_ADDRESS + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TEMP_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TEMP_CUSTOMER_SHIP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_SHIP_ADDR1 + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_SHIP_ADDR2 + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_SHIP_ADDR3 + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_SHIP_CITY + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_DEFAULT_SHIP_GEOHIERPATH + "' TEXT NOT NULL, " + "'" +
            GST_STATE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_SHIP_PHONENO + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_DEFAULT_SHIP_ADDR + "' TEXT, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT DEFAULT NULL, " + "'" +
            COLUMN_CUSTOMER_SHIP_CODE + "' TEXT DEFAULT NULL, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_TEMP_CUSTOMER_CODE + "','" + COLUMN_TEMP_CUSTOMER_SHIP_CODE + "'))";

    String CREATE_TEMP_CUSTOMER_ROUTE = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_TEMP_CUSTOMER_ROUTE + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TEMP_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_TEMP_ROUTE_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_COVERAGE_SEQUENCE + "' INTEGER NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT DEFAULT NULL, " + "'" +
            COLUMN_ROUTE_CODE + "' TEXT DEFAULT NULL, " + "'" +
            COLUMN_DATE + "' TEXT NOT NULL, " + "'" +
            COLUMN_UPLOAD + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_TEMP_CUSTOMER_CODE + "','" + COLUMN_TEMP_ROUTE_CODE + "'))";

    String CREATE_BATCH_TRANSFER_DETAILS = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_BATCH_TRANSFER_DETAILS + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_GODOWN_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_BATCH_TRANSFERNO + "' TEXT NOT NULL, " + "'" +
            COLUMN_TRANSFER_DT + "' TEXT NULL, " + "'" +
            COLUMN_STOCK_TYPE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, " + "'" +
            FROM_PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            TO_PROD_BATCH_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_QTY + "' INTEGER, " + "'" +
            COLUMN_UOM_ID + "' TEXT, " + "'" +
            COLUMN_TRANSFER_QTY + "' INTEGER, " + "'" +
            COLUMN_INPUT_STR + "' TEXT NOT NULL, " + "'" +
            COLUMN_REASON_CODE_CAPS + "' TEXT NULL, " + "'" +
            COLUMN_REASON_NAME_CAPS + "' TEXT NULL, " + "'" +
            COLUMN_DOC_REFNO + "' TEXT NULL, " + "'" +
            COLUMN_REMARKS + "' TEXT NULL, " + "'" +
            COLUMN_COMPLETE_FLAG + "' TEXT NOT NULL, " + "'" +
            COLUMN_MOD_DT + "' TEXT NOT NULL, " + "'" +
            UPLOAD_FLAG + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "' , '" + COLUMN_GODOWN_CODE + "', " +
            "'" + COLUMN_BATCH_TRANSFERNO + "', '" + COLUMN_STOCK_TYPE + "' , '" + COLUMN_PROD_CODE + "' , '" + FROM_PROD_BATCH_CODE + "', '" + TO_PROD_BATCH_CODE + "'))";

    String CREATE_CODE_GENERATOR_HISTORY = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CODE_GENERATOR_HISTORY + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_SCREEN_NAME + "' TEXT NOT NULL, " + "'" +
            COLUMN_PREFIX + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUFFIX_YY + "' TEXT NOT NULL, " + "'" +
            COLUMN_SUFFIX_NN + "' INTEGER NOT NULL, '" +
            COLUMN_UPLOAD + "' TEXT NOT NULL DEFAULT 'N', " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_SCREEN_NAME + "','" + COLUMN_PREFIX + "','" + COLUMN_SUFFIX_YY + "'))";

    // Create Scheme Retailer Category List Table Query
    String CREATE_CUSTOMER_SCHEME_PRODUCTS_LIST = "CREATE TABLE IF NOT EXISTS '" +
            TABLE_CUSTOMER_SCHEME_PRODUCTS_LIST + "'('" +
            COLUMN_CMP_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_DISTR_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_CUSTOMER_CODE + "' TEXT NOT NULL, " + "'" +
            COLUMN_PROD_CODE + "' TEXT NOT NULL, '" +
            COLUMN_PROD_NAME + "' TEXT , '" +
            SCHEME_CODE + "' TEXT NOT NULL, '" +
            SCHEME_BASE + "' TEXT NOT NULL, '" +
            SCHEME_PAYOUT_TYPE + "' TEXT NOT NULL, '" +
            COLUMN_START_TIME + "' TEXT , '" +
            COLUMN_END_TIME + "' TEXT , '" +
            COMBI + "' TEXT, '" +
            SCHEME_DESC + "' TEXT , " +
            " PRIMARY KEY ('" + COLUMN_CMP_CODE + "','" + COLUMN_DISTR_CODE + "','" + COLUMN_CUSTOMER_CODE + "','" + COLUMN_PROD_CODE + "','" + SCHEME_CODE + "'))";

}