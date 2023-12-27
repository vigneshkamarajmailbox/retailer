package com.botree.retailerssfa.db.query;

interface IDBIndexQuery extends IDBColumns {
    /***********
     * Creating index
     ***********/

    // Create Index

    String CREATE_INDEX_USER_1 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_USER_1 + "' on '" +
            TABLE_USER + "' ('" +
            COLUMN_SALESMAN_CODE + "','" +
            CREDENTIAL + "', '" +
            COLUMN_DISTR_CODE + "')";

    String CREATE_INDEX_ROUTE_1 = String.format("CREATE INDEX IF NOT EXISTS '%s' on '%s' ('%s')",
            INDEX_ROUTE_1, TABLE_ROUTE, COLUMN_DISTR_CODE);

    String CREATE_INDEX_RETAILER_1 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_RETAILER_1 + "' on '" +
            TABLE_RETAILER + "' ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_ROUTE_CODE + "')";

    String CREATE_INDEX_RETAILER_2 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_RETAILER_2 + "' on '" +
            TABLE_RETAILER + "' ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_ROUTE_CODE + "', '" +
            COLUMN_CUSTOMER_CODE + "')";

    String CREATE_INDEX_RETAILER_3 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_RETAILER_3 + "' on '" +
            TABLE_RETAILER + "' ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_CHANNEL_CODE + "', '" +
            COLUMN_SUB_CHANNEL_CODE + "', '" +
            COLUMN_GROUP_CODE + "', '" +
            COLUMN_CLASS_CODE + "')";

    String CREATE_INDEX_RETAILER_4 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_RETAILER_4 + "' on '" +
            TABLE_RETAILER + "' ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_DISTR_CODE + "')";

    String CREATE_INDEX_FOCUSBRAND_RETAILERS_1 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_FOCUSBRAND_RETAILERS_1 + "' on '" +
            TABLE_FOCUSBRAND_RETAILERS + "' ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_CHANNEL_CODE + "', '" +
            COLUMN_SUB_CHANNEL_CODE + "', '" +
            COLUMN_GROUP_CODE + "', '" +
            COLUMN_CLASS_CODE + "')";

    String CREATE_INDEX_DEFINITION_LIST_1 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_SCHEME_DEFINITION_LIST_1 + "' on '" +
            TABLE_SCHEME_DEFINITION_LIST + "' ('" +
            COLUMN_CMP_CODE + "', '" +
            SCHEME_CODE + "')";

    String CREATE_INDEX_PRODUCTS = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_PRODUCTS + "' on '" +
            TABLE_PRODUCTS + "' ('" +
            COLUMN_SALESMAN_CODE_CAPS + "','" +
            COLUMN_DISTR_CODE_CAPS + "','" +
            COLUMN_CMP_CODE_CAPS + "','" +
            COLUMN_PROD_CODE_CAPS + "','" +
            COLUMN_BATCH_CODE + "')";


    String CREATE_INDEX_ORDERBOOKING = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_ORDER_BOOKING + "' on '" +
            TABLE_ORDER_BOOKING + "' ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_ROUTE_CODE + "', '" +
            COLUMN_RETLR_CODE_CAPS + "', '" +
            COLUMN_PROD_CODE_CAPS + "', '" +
            COLUMN_BATCH_CODE + "')";


    String CREATE_INDEX_VAN_SALES_PRODUCTS = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_VAN_SALES_PRODUCTS + "' on '" +
            TABLE_VANSALES_PRODUCTS + "' ('" +
            COLUMN_SALESMAN_CODE_CAPS + "','" +
            COLUMN_DISTR_CODE_CAPS + "','" +
            COLUMN_CMP_CODE_CAPS + "','" +
            COLUMN_PROD_CODE_CAPS + "','" +
            COLUMN_BATCH_CODE + "')";

    String CREATE_INDEX_VAN_SALES_UOM = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_VAN_SALES_UOM + "' on '" +
            TABLE_VANSALES_UOM_MASTER + "' ('" +
            COLUMN_SALESMAN_CODE_CAPS + "','" +
            COLUMN_DISTR_CODE_CAPS + "','" +
            COLUMN_CMP_CODE_CAPS + "','" +
            COLUMN_PROD_CODE_CAPS + "','" +
            COLUMN_UOM_CODE + "')";

    String CREATE_INDEX_PRODUCT_UOM = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_PRODUCT_UOM + "' on '" +
            TABLE_UOM_MASTER + "' ('" +
            COLUMN_SALESMAN_CODE_CAPS + "','" +
            COLUMN_DISTR_CODE_CAPS + "','" +
            COLUMN_CMP_CODE_CAPS + "','" +
            COLUMN_PROD_CODE_CAPS + "','" +
            COLUMN_UOM_CODE + "')";

    String CREATE_INDEX_PRODUCT_UOM_1 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_PRODUCT_UOM_1 + "' on '" +
            TABLE_UOM_MASTER + "' ('" +
            COLUMN_DISTR_CODE + "','" +
            COLUMN_SALESMAN_CODE + "','" +
            COLUMN_PROD_CODE + "','" +
            COLUMN_BASE_UOM + "')";

    String CREATE_INDEX_RETAILER_CATEGORY_ID = "CREATE INDEX IF NOT EXISTS '" +
            INDER_RETAILER_CATEGORY_ID + "' on '" +
            TABLE_RETAILER_CATEGORY_ID + "' ('" +
            COLUMN_DISTR_CODE_CAPS + "','" +
            COLUMN_SALESMAN_CODE_CAPS + "','" +
            COLUMN_ROUTE_CODE_CAPS + "','" +
            COLUMN_RETLR_CODE_CAPS + "')";

    String CREATE_INDEX_PRODUCTSUGGESTIONS = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_PRODUCT_SUGGESTIONS + "' on '" +
            TABLE_PRODUCT_SUGGESTIONS + "' ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_RETLR_CODE_CAPS + "', '" +
            COLUMN_PROD_CODE_CAPS + "')";

    String CREATE_INDEX_CATEGORYSEQUENCE = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_CATEGORY_SEQUENCE + "' on '" +
            TABLE_CATEGORY_SEQUENCE + "' ('" +
            SEQUENCE_NO_CAPS + "', '" +
            CATEGORY_CAPS + "')";

    String CREATE_INDEX_PRODUCTCATEGORY = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_PRODUCT_CATEGORY + "' on '" +
            TABLE_PRODUCT_CATEGORY + "' ('" +
            COLUMN_PROD_CODE_CAPS + "', '" +
            CATEGORY_CAPS + "')";

    String CREATE_INDEX_CASHCOLLECTION_1 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_CASH_COLLECTION_1 + "' on '" +
            TABLE_CASH_COLLECTION + "' ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_ROUTE_CODE + "', '" +
            COLUMN_CUSTOMER_CODE + "')";

    String CREATE_INDEX_CHEQUECOLLECTION_1 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_CHEQUE_COLLECTION_1 + "' on '" +
            TABLE_CHEQUE_COLLECTION + "' ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_ROUTE_CODE + "', '" +
            COLUMN_CUSTOMER_CODE + "')";

    String CREATE_INDEX_STOCKTAKE = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_STOCK_TAKE + "' on '" +
            TABLE_STOCK_TAKE + "' ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_ROUTE_CODE + "', '" +
            COLUMN_RETLR_CODE_CAPS + "', '" +
            COLUMN_PROD_CODE_CAPS + "', '" +
            COLUMN_MRP_CAPS + "')";

    String CREATE_INDEX_SALESRETURN = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_SALES_RETURN + "' on '" +
            TABLE_SALES_RETURN + "' ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_ROUTE_CODE + "', '" +
            COLUMN_RETLR_CODE_CAPS + "', '" +
            COLUMN_PROD_CODE_CAPS + "')";

    String CREATE_INDEX_PURCHASE_RETURN = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_PURCHASE_RETURN + "' on '" +
            TABLE_SALES_RETURN + "' ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_BATCH_CODE + "', '" +
            COLUMN_PROD_CODE_CAPS + "')";

    String CREATE_INDEX_REASONS_1 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_REASONS_1 + "' on '" +
            TABLE_REASONS + "' ('" +
            COLUMN_SCREEN_NAME_CAPS + "')";

    String CREATE_INDEX_RETAILERVISIT_1 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_RETAILER_VISIT_1 + "' on '" +
            TABLE_RETAILER_VISIT + "' ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_SALESMAN_CODE + "', '" +
            COLUMN_ROUTE_CODE + "')";


    String CREATE_INDEX_PURCHASE_ORDER_BOOKING_1 = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_PURCHASE_ORDER_BOOKING_1 + "' on '" +
            TABLE_PURCHASE_ORDER_BOOKING + "' ('" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_PURCHASE_PRICE + "', '" +
            COLUMN_BATCH_CODE + "', '" +
            COLUMN_PROD_CODE_CAPS + "', '" +
            COLUMN_MRP_CAPS + "')";


    String CREATE_INDEX_PRODUCT_MASTER = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_PRODUCT_MASTER + "' on '" +
            TABLE_PRODUCT_MASTER + "' ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_PROD_CODE + "')";

    String CREATE_INDEX_PRODUCT_BATCH_MASTER = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_PRODUCT_BATCH_MASTER + "' on '" +
            TABLE_PRODUCT_BATCH_MASTER + "' ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_PROD_CODE + "', '" +
            COLUMN_PROD_BATCH_CODE + "')";

    String CREATE_INDEX_PRODUCT_TAX_MASTER = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_PRODUCT_TAX_MASTER + "' on '" +
            TABLE_PRODUCT_TAX_MASTER + "' ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_TAX_STATE_CODE + "', '" +
            COLUMN_PROD_CODE + "')";

    String CREATE_INDEX_PRODUCT_UOM_MASTER = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_PRODUCT_UOM_MASTER + "' on '" +
            TABLE_PRODUCT_UOM_MASTER + "' ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_PROD_CODE + "', '" +
            COLUMN_UOM_CODE + "')";


    String CREATE_INDEX_STOCK_ON_HAND = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_STOCK_ON_HAND + "' on '" +
            TABLE_STOCK_ON_HAND + "' ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_DISTR_CODE + "', '" +
            COLUMN_PROD_CODE + "', '" +
            COLUMN_PROD_BATCH_CODE + "', '" +
            COLUMN_GODOWN_CODE + "')";

    String CREATE_INDEX_BRANCH_STOCK = "CREATE INDEX IF NOT EXISTS '" +
            INDEX_BRANCH_STOCK + "' on '" +
            TABLE_BRANCH_STOCK + "' ('" +
            COLUMN_CMP_CODE + "', '" +
            COLUMN_BRANCH_CODE + "', '" +
            COLUMN_PROD_CODE + "')";

}
