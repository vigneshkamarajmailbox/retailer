package com.botree.retailerssfa.db.query;


import androidx.annotation.NonNull;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_HIER_PATH;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BILLING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CUSTOMER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CUSTOMER_BANK;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_GEO_HIER_VALUE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_NEW_RETAILER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PENDING_BILL;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_RETAILER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_RETAILER_CHANNELS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ROUTE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_TAX_STRUCTURE;
import static com.botree.retailerssfa.db.query.IDBColumns.TAX_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.TAX_STATE;

public class RetailerDetailQueryHelper {

    /* getRetailerByRetailerCode */
    static final String QUERY_RETAILER_BY_RETLR_CODE = "select * from " + TABLE_RETAILER + " where " + COLUMN_CUSTOMER_CODE + " =?";

    /* getChannelNameBasedonCode */
    static final String QUERY_SELECT_CHANNEL_BY_CODE = "Select channelName from " + TABLE_RETAILER_CHANNELS + " WHERE channelCode =?";

    /* getPendingBills */
    static final String QUERY_SELECT_PENDING_BILLS = "SELECT * FROM " + TABLE_PENDING_BILL + " WHERE distrCode =? AND salesmanCode = ?  AND customerCode = ? AND pendingAmount > 0";

    /*query for  getTaxCalPercentForUT mehtod*/
    @NonNull
    static String getQueryTaxCalPercent(String prodCode, boolean isUnionTerritory) {
        String query;
        if (isUnionTerritory) {
            query = "SELECT * FROM '" + TABLE_TAX_STRUCTURE + "' WHERE " + COLUMN_DISTR_CODE + " =? " +
                    "AND " + COLUMN_PROD_HIER_PATH + " like  ( '%/' || '" + prodCode + "' ||  '/%' )   AND " + TAX_STATE + "=? " +
                    "AND (taxName = 'CGST' OR taxName ='UTGST') GROUP BY " + TAX_NAME;
        } else {
            query = "SELECT * FROM '" + TABLE_TAX_STRUCTURE + "' WHERE " + COLUMN_DISTR_CODE + " =? " +
                    "AND " + COLUMN_PROD_HIER_PATH + " like  ( '%/' || '" + prodCode + "' ||  '/%' ) AND " + TAX_STATE + "=? " +
                    "AND (taxName = 'CGST' OR taxName ='SGST') GROUP BY " + TAX_NAME;
        }
        return query;
    }

    static final String QUERY_SELECT_CUSTOMER_BANK_COUNT = "select * from " + TABLE_CUSTOMER_BANK + " where cmpCode = ? AND distrCode= ? AND customerCode=?";
    static final String QUERY_SELECT_CUSTOMER_DET = "select * from " + TABLE_CUSTOMER + " where cmpCode = ? AND distrCode= ? AND customerCode=?";
    static final String QUERY_SELECT_CUSTOMER = "SELECT c.*, b.bankName, b.bankBranchName, b.IFSCCode, b.accountType, b.accountNumber FROM " + TABLE_CUSTOMER + " c INNER JOIN " + TABLE_CUSTOMER_BANK + " b on c.customerCode= b.customerCode WHERE c.cmpCode = ? AND c.distrCode= ? AND c.customerCode=?";

    static final String QUERY_SELECT_PARENT = "SELECT customerCode, customerName FROM " + TABLE_CUSTOMER + " WHERE cmpCode = ? AND distrCode= ? AND storeType = ?";

    static final String QUERY_SELECT_GEOGRAPHY = "SELECT parentCode, geoName, geoCode FROM " + TABLE_GEO_HIER_VALUE + " WHERE cmpCode = ? AND geoHierLevelCode= ?";

    static final String QUERY_SELECT_GEOGRAPHY_PATH = "SELECT parentCode, geoName, geoCode FROM " + TABLE_GEO_HIER_VALUE + " WHERE geoCode = ?";

    static final String QUERY_RETAILER_BY_CUSTOMER_CODE = "select cus.customerCode, cus.customerName, cus.creditDays, cusship.customerShipAddr1, cusship.customerShipAddr2, cusship.customerShipAddr3, cus.gstNo, cus.panNo, cus.customerType, cus.channelCode, cus.mobileNo from t_customer cus INNER JOIN t_customershipaddress cusship on cus.customerCode= cusship.customerCode where cus.customerCode=? and cusship.defaultShipAddr =?";

    public static final String QUERY_RETAILER_BANK_BY_CUSTOMER_CODE = "select * from " + TABLE_CUSTOMER_BANK + " where distrCode =? and customerCode=?";

    /* query for getVANSalesDaySummaryData method*/
    @NonNull
    static String getQueryVanSalesDaySummary(String distrcode, String salesmancode, String strRouteCode) {
        String query;
        if (strRouteCode != null && !strRouteCode.equalsIgnoreCase("0")) {
            query = "SELECT r.customercode as customerCode,\n" +
                    "       r.customername as customerName,\n" +
                    "       r.retailerAddr1       AS retailerAddr1,\n" +
                    "       r.channelcode as channelCode,\n" +
                    "       o.invoiceno as invoiceNo,\n" +
                    "       o.distrCode as distrCode,\n" +
                    "       r.routeCode as routeCode," +
                    "       rt.routename         AS routeName,\n" +
                    "       r.rtrMobileNo       AS rtrMobileNo,\n" +
                    "       Sum(o.OrderValue) AS OrderValue," +
                    "       Sum(o.totalAmount) AS totalordervalue,\n" +
                    "       Sum(o.primaryDiscOrderValue)   AS primaryDiscOrderValue,\n" +
                    "       Sum(o.orderqty)   AS OrderQty,\n" +
                    "       o.uomId as uomId," +
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
                    "       Sum(o.discountAmount) AS discountAmt,\n" +
                    "       Sum(o.taxPerProduct) AS taxAmt,\n" +
                    "       Count(*)          AS productcount\n" +
                    "FROM   " + TABLE_BILLING + " o\n" +
                    "       INNER JOIN " + TABLE_RETAILER + " r\n" +
                    "               ON o.retlrcode = r.customercode\n" +
                    "                  AND o.distrcode = r.distrcode\n" +
                    "       INNER JOIN " + TABLE_ROUTE + " rt\n" +
                    "               ON rt.distrcode = r.distrcode\n" +
                    "                  AND rt.routecode = r.routecode\n" +
                    "WHERE  o.distrcode = '" + distrcode + "'\n" +
                    "       AND o.routecode ='" + strRouteCode + "'\n" +
                    "       AND o.completeFlag = 'Y'\n" +
                    "GROUP  BY r.customercode " +
                    "UNION\n" +
                    "SELECT r.customercode as customerCode,\n" +
                    "       r.customername as customerName,\n" +
                    "       r.address1       AS retailerAddr1,\n" +
                    "       r.channelcode as channelCode,\n" +
                    "       o.invoiceno as invoiceNo,\n" +
                    "       o.distrCode as distrCode,\n" +
                    "       r.routeCode as routeCode," +
                    "       rt.routename         AS routeName,\n" +
                    "       r.mobileNumber       AS rtrMobileNo,\n" +
                    "       Sum(o.OrderValue) AS OrderValue," +
                    "       Sum(o.totalAmount) AS totalordervalue,\n" +
                    "       Sum(o.primaryDiscOrderValue)   AS primaryDiscOrderValue,\n" +
                    "       Sum(o.orderqty)   AS OrderQty,\n" +
                    "       o.uomId as uomId," +
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
                    "       Sum(o.discountAmount) AS discountAmt,\n" +
                    "       Sum(o.taxPerProduct) AS taxAmt,\n" +
                    "       Count(*)          AS productcount\n" +
                    "FROM   " + TABLE_BILLING + " o\n" +
                    "       INNER JOIN " + TABLE_NEW_RETAILER + " r\n" +
                    "               ON o.retlrcode = r.customercode\n" +
                    "                  AND o.distrcode = r.distrcode\n" +
                    "       INNER JOIN " + TABLE_ROUTE + " rt\n" +
                    "               ON rt.distrcode = r.distrcode\n" +
                    "                  AND rt.routecode = r.routecode\n" +
                    "WHERE  o.distrcode = '" + distrcode + "'\n" +
                    "       AND o.routecode = '" + strRouteCode + "'\n" +
                    "       AND o.completeFlag = 'Y'\n" +
                    "GROUP  BY r.customercode  ORDER BY upload ASC";


        } else {
            query = "SELECT r.customercode as customerCode,\n" +
                    "       r.customername as customerName,\n" +
                    "       r.retailerAddr1       AS retailerAddr1,\n" +
                    "       r.channelcode as channelCode,\n" +
                    "       o.invoiceno as invoiceNo,\n" +
                    "       o.distrCode as distrCode,\n" +
                    "       r.routeCode as routeCode," +
                    "       rt.routename         AS routeName,\n" +
                    "       r.rtrMobileNo       AS rtrMobileNo,\n" +
                    "       Sum(o.OrderValue) AS OrderValue," +
                    "       Sum(o.totalAmount) AS totalordervalue,\n" +
                    "       Sum(o.primaryDiscOrderValue)   AS primaryDiscOrderValue,\n" +
                    "       Sum(o.orderqty) AS OrderQty,\n" +
                    "       o.uomId as uomId," +
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
                    "       Sum(o.discountAmount) AS discountAmt,\n" +
                    "       Sum(o.taxPerProduct) AS taxAmt,\n" +
                    "       Count(*) AS productcount\n" +
                    "FROM   " + TABLE_BILLING + " o\n" +
                    "       INNER JOIN " + TABLE_RETAILER + " r\n" +
                    "               ON o.retlrcode = r.customercode\n" +
                    "                  AND o.distrcode = r.distrcode\n" +
                    "       INNER JOIN " + TABLE_ROUTE + " rt\n" +
                    "               ON rt.distrcode = r.distrcode\n" +
                    "                  AND rt.routecode = r.routecode\n" +
                    "WHERE  o.distrcode = '" + distrcode + "'\n" +
                    "       AND o.completeFlag = 'Y'\n" +
                    "GROUP  BY r.customercode " +
                    "UNION\n" +
                    "SELECT r.customercode as customerCode,\n" +
                    "       r.customername as customerName,\n" +
                    "       r.address1       AS retailerAddr1,\n" +
                    "       r.channelcode as channelCode,\n" +
                    "       o.invoiceno as invoiceNo,\n" +
                    "       o.distrCode as distrCode,\n" +
                    "       r.routeCode as routeCode," +
                    "       rt.routename         AS routeName,\n" +
                    "       r.mobileNumber       AS rtrMobileNo,\n" +
                    "       Sum(o.OrderValue) AS OrderValue," +
                    "       Sum(o.totalAmount) AS totalordervalue,\n" +
                    "       Sum(o.primaryDiscOrderValue)   AS primaryDiscOrderValue,\n" +
                    "       Sum(o.orderqty)   AS OrderQty,\n" +
                    "       o.uomId as uomId," +
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
                    "       Sum(o.discountAmount) AS discountAmt,\n" +
                    "       Sum(o.taxPerProduct) AS taxAmt,\n" +
                    "       Count(*)          AS productcount\n" +
                    "FROM   " + TABLE_BILLING + " o\n" +
                    "       INNER JOIN " + TABLE_NEW_RETAILER + " r\n" +
                    "               ON o.retlrcode = r.customercode\n" +
                    "                  AND o.distrcode = r.distrcode\n" +
                    "       INNER JOIN " + TABLE_ROUTE + " rt\n" +
                    "               ON rt.distrcode = r.distrcode\n" +
                    "                  AND rt.routecode = r.routecode\n" +
                    "WHERE  o.distrcode = '" + distrcode + "'\n" +
                    "       AND o.completeFlag = 'Y'\n" +
                    "GROUP  BY r.customercode  ORDER BY upload ASC";

        }
        return query;
    }


}
