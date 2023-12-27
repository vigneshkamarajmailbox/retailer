package com.botree.retailerssfa.db.query;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LOGIN_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MOBILE_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UPLOAD;
import static com.botree.retailerssfa.db.query.IDBColumns.GST_DIST_STATE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.GST_STATE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BILLING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CASH_COLLECTION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CHEQUE_COLLECTION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_COMPANY_DETAIL;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CUSTOMER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CUSTOMER_ROUTE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_DISTRIBUTOR;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_DISTR_LOB;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_KYC_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_LOADING_STOCK;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_LOB;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_LOGIN;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_NEW_RETAILER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_BOOKING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_RETAILER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_RETAILER_CATEGORY;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_RETAILER_CHANNELS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_RETAILER_CLASSES;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_RETAILER_GROUPS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ROUTE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALESMAN_LOB_MAPPING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALESMAN_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALESMAN_MASTER_KYC;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALESMAN_ROUTE_MAPPING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALES_HIERARY;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALES_HIERARY_VALUE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALES_RETURN;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCREEN_CONFIGURATION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_STOCK_TAKE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_UPDATE_LOCATION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_USER;
import static com.botree.retailerssfa.db.query.IDBColumns.USER_CODE;

public class UserDbQueryHelper {


    /* getUserDetail */
    public static final String QUERY_SELECT_USER_DETAILS = "SELECT * FROM " + TABLE_USER + " WHERE (LOWER(" + USER_CODE + ") = ? OR " + COLUMN_MOBILE_NO + "=?)";

    public static final String QUERY_SELECT_DISTRIBUTOR = "SELECT * FROM " + TABLE_DISTRIBUTOR + " WHERE " + COLUMN_DISTR_CODE + "=? LIMIT 1";

    /* insertScreenConfig */
    public static final String QUERY_INSERT_SCREEN_CONFIGS = "INSERT INTO " + TABLE_SCREEN_CONFIGURATION + " VALUES (?,?,?,?,?,?,?,?,?,?);";

    /* userLoginSuccess */
    public static final String QUERY_SELECT_LOGIN_USER = "SELECT * FROM " + TABLE_USER + " WHERE (LOWER(" + USER_CODE + ") = ? OR " + COLUMN_MOBILE_NO + "=?) AND password = ?";

    /* getUserCount */
    public static final String QUERY_SELECT_USER_COUNT_BASED_ON_ID = "SELECT count(*) FROM " + TABLE_LOGIN + " where UPPER(" + COLUMN_LOGIN_CODE + ")=? OR " + COLUMN_DISTR_CODE + "=?";

    /* getUserCount */
    public static final String QUERY_SELECT_USER_COUNT = "SELECT * FROM " + TABLE_LOGIN+" where loginCode = ?";

    /* loadChannelData */
    public static final String QUERY_SELECT_RETAILER_CHANNELS = "SELECT * FROM '" + TABLE_RETAILER_CHANNELS + "' WHERE distrCode =?";

    /* loadSubChannelData for the channel */
    public static final String QUERY_SELECT_SUB_CHANNELS = "SELECT Distinct(subChannelCode),subChannelName  FROM '" + TABLE_RETAILER_CATEGORY + "' WHERE distrCode =? AND channelCode=?";

    /* loadGroupData for the channel*/
    public static final String QUERY_SELECT_RETAILERS_GROUP = "SELECT * FROM '" + TABLE_RETAILER_GROUPS + "' WHERE distrCode = ? AND subChannelCode = ?";

    /* isTransctionAvailableInDB */
    public static final String QUERY_CHECK_IS_TRANSACTION_IN_DB = "select * from " + TABLE_USER + " r \n" +
            "  LEFT JOIN  " + TABLE_ORDER_BOOKING + " ob  ON ob.distrCode=r.distrCode AND ob.salesmanCode =r.salesmanCode \n" +
            " LEFT JOIN " + TABLE_SALES_RETURN + " sr  ON r.distrCode=sr.distrCode AND r.salesmanCode =sr.salesmanCode \n" +
            " LEFT JOIN " + TABLE_CASH_COLLECTION + " cc ON r.distrCode=cc.distrCode AND r.salesmanCode =cc.salesmanCode \n" +
            " LEFT JOIN " + TABLE_CHEQUE_COLLECTION + " cq ON r.distrCode=cq.distrCode AND r.salesmanCode =cq.salesmanCode \n" +
            " LEFT JOIN " + TABLE_BILLING + " b ON r.distrCode=b.distrCode AND r.salesmanCode =b.salesmanCode \n" +
            " LEFT JOIN " + TABLE_STOCK_TAKE + " st ON r.distrCode=st.distrCode AND r.salesmanCode =st.salesmanCode \n" +
            " LEFT JOIN " + TABLE_UPDATE_LOCATION + " ul ON r.distrCode=ul.distrCode AND r.salesmanCode =ul.salesmanCode \n" +
            " LEFT JOIN " + TABLE_LOADING_STOCK + " ls ON r.distrCode=ls.distrCode AND r.salesmanCode =ls.salesmanCode \n" +
            " LEFT JOIN " + TABLE_NEW_RETAILER + " nwr ON r.distrCode=nwr.distrCode AND r.salesmanCode =nwr.salesmanCode \n" +
            " where (ob.upload='N' OR sr.upload='N' OR cc.upload='N' OR cq.upload='N' AND\n" +
            "  sa.upload='N' OR b.upload='N' OR f.upload='N' OR st.upload='N' OR \n" +
            "  ul.upload='N' OR ls.upload='N' OR se.upload='N' OR sat.upload='N' OR nwr.upload='N') " +
            " AND r.distrCode=? AND r.salesmanCode=?";

    /* loadClassData */
    public static final String QUERY_SELECT_RETAILER_CLASS_DATA = "SELECT * FROM '" + TABLE_RETAILER_CLASSES + "' WHERE distrCode = ? AND channelCode = ? AND groupCode = ?";

    /* getLoginDistStateCode */
    public static final String QUERY_SELECT_LOGIN_DISTR_SATE_CODE = "SELECT " + GST_STATE_CODE + " FROM " + TABLE_DISTRIBUTOR;

    /* getDistStateCode */
    public static final String QUERY_SELECT_DISTR_STATE_CODE = "SELECT " + GST_DIST_STATE_CODE + " FROM " + TABLE_USER;

    /* insertSalesHierarchyData */
    public static final String QUERY_INSERT_SALES_HIERARCHY = "INSERT INTO " + TABLE_SALES_HIERARY + " VALUES (?,?,?,?,?);";


    /* insertSalesHierarchyValue */
    public static final String QUERY_INSERT_SALES_HIERARCHY_VALUE = "INSERT INTO " + TABLE_SALES_HIERARY_VALUE + " VALUES (?,?,?,?,?,?,?);";
    /* isVanSalesDataAvailable */
    public static final String QUERY_LOADING_STOCK = "SELECT * FROM " + TABLE_LOADING_STOCK;
    public static final String QUERY_SELECT_RETAILERS = "SELECT routeCode, customerCode, customerName, latitude, longitude from " + TABLE_RETAILER + " ORDER BY customerName ASC";
    /* fetchNewRetailerDetails */
    public static final String QUERY_SELECT_ALL_NEW_SALESMAN = "SELECT * from " + TABLE_SALESMAN_MASTER;

    public static final String QUERY_SELECT_NEW_SALESMAN = "SELECT * from " + TABLE_SALESMAN_MASTER + " WHERE salesmanCode =? ";

    public static final String QUERY_SELECT_NEW_SALESMAN_ROUTE_MAP = "SELECT a.routeCode, a.routeName, b.isSelected FROM " + TABLE_ROUTE + " a LEFT JOIN " + TABLE_SALESMAN_ROUTE_MAPPING + " b ON a.routeCode = b.routeCode ";

    public static final String QUERY_ALL_NEW_SALESMAN_ROUTE_MAP = "SELECT routeCode, routeName FROM " + TABLE_ROUTE;

    public static final String QUERY_SELECT_NEW_SALESMAN_LOB_MAP = "SELECT a.lobCode, a.lobName, b.isSelected FROM " + TABLE_LOB + " a LEFT JOIN " + TABLE_SALESMAN_LOB_MAPPING + " b ON a.lobCode = b.lobCode ";

    public static final String QUERY_ALL_NEW_SALESMAN_LOB_MAP = "SELECT a.lobCode, b.lobName FROM " + TABLE_DISTR_LOB + " a LEFT JOIN " + TABLE_LOB + " b ON a.lobCode = b.lobCode ";

    public static final String QUERY_NEW_SALESMAN_KYC_MAP = "SELECT * from " + TABLE_SALESMAN_MASTER_KYC + " WHERE salesmanCode =? and image!='' ";

    public static final String QUERY_FETCH_SALESMAN_FOR_UPLOAD = "SELECT * from " + TABLE_SALESMAN_MASTER + " WHERE " + COLUMN_DISTR_CODE + " = ? AND " + COLUMN_UPLOAD + " = 'N' ";

    public static final String QUERY_FETCH_SALESMAN_KYC_FOR_UPLOAD = "SELECT * from " + TABLE_SALESMAN_MASTER_KYC + " WHERE " + COLUMN_DISTR_CODE + " = ?  AND " + COLUMN_UPLOAD + " = 'N' ";

    public static final String QUERY_FETCH_SALESMAN_LOB_UPLOAD = "SELECT * from " + TABLE_SALESMAN_LOB_MAPPING + " WHERE " + COLUMN_DISTR_CODE + " = ?  AND " + COLUMN_UPLOAD + " = 'N' ";

    public static final String QUERY_FETCH_SALESMAN_ROUTE_UPLOAD = "SELECT * from " + TABLE_SALESMAN_ROUTE_MAPPING + " WHERE " + COLUMN_DISTR_CODE + " = ?  AND " + COLUMN_UPLOAD + " = 'N' ";

    public static final String QUERY_FETCH_KYC_MASTER = "SELECT * from " + TABLE_KYC_MASTER;
    public static final String QUERY_SELECT_CUSTOMERS = "SELECT cus.customerCode, cus.customerName, cus.latitude, cus.longitude, cus.channelCode, cus.approvalStatus, cus.retailerStatus, ret.channelName from " + TABLE_CUSTOMER + " cus INNER JOIN " + TABLE_RETAILER_CHANNELS + " ret on cus.channelCode = ret.channelCode ORDER BY cus.customerName ASC";

    public static final String QUERY_SELECT_SALESMAN_MASTER = "SELECT * from " + TABLE_SALESMAN_MASTER + " WHERE cmpCode =? AND distrCode=?";
    public static final String QUERY_SELECT_ALL_CUSTOMERS = "SELECT cus.customerCode, cus.customerName, cus.latitude, cus.longitude, cus.channelCode, cus.approvalStatus, cus.retailerStatus, ret.channelName from " + TABLE_CUSTOMER + " cus INNER JOIN " + TABLE_RETAILER_CHANNELS + " ret on cus.channelCode = ret.channelCode where cus.approvalStatus='A' ORDER BY cus.customerName ASC";
    public static final String QUERY_SELECT_RETAILER_BY_ROUTE = "SELECT cus.customerCode, cus.customerName, cus.latitude, cus.longitude, cus.channelCode, cus.approvalStatus, cus.retailerStatus, cusroute.coverageSequence,ret.channelName from " + TABLE_CUSTOMER + " cus INNER JOIN " + TABLE_RETAILER_CHANNELS + " ret on cus.channelCode = ret.channelCode INNER JOIN " + TABLE_CUSTOMER_ROUTE + " cusroute on cus.customerCode = cusroute.customerCode where cus.cmpCode=? AND cus.distrCode=? AND cusroute.routeCode=? AND cus.approvalStatus=? ORDER BY cus.customerName ASC";
    public static final String QUERY_SELECT_CUSTOMER_SALESMAN_MASTER = "SELECT msales.salesmanName, msales.salesmanCode from " + TABLE_CUSTOMER_ROUTE + " cusroute INNER JOIN " + TABLE_SALESMAN_ROUTE_MAPPING + " salesroute on cusroute.routeCode = salesroute.routeCode INNER JOIN " + TABLE_SALESMAN_MASTER + " msales on msales.salesmanCode = salesroute.salesmanCode where cusroute.customerCode=? AND msales.status='Y' GROUP BY msales.salesmanCode";
    public static final String QUERY_SELECT_RETAILER_FOR_ROUTE_SEQ = "SELECT cus.customerCode, cus.customerName, cus.latitude, cus.longitude, cus.channelCode, cus.approvalStatus, cus.retailerStatus, cusroute.coverageSequence,ret.channelName from " + TABLE_CUSTOMER + " cus INNER JOIN " + TABLE_RETAILER_CHANNELS + " ret on cus.channelCode = ret.channelCode INNER JOIN " + TABLE_CUSTOMER_ROUTE + " cusroute on cus.customerCode = cusroute.customerCode where cus.cmpCode=? AND cus.distrCode=? AND cusroute.routeCode=? AND cus.approvalStatus=?";
    public static final String QUERY_SELECT_ALL_CUSTOMERS_BILL = "SELECT cus.customerCode, cus.customerName, cus.latitude, cus.longitude, cus.channelCode, cus.approvalStatus, cus.retailerStatus, ret.channelName from " + TABLE_CUSTOMER + " cus INNER JOIN " + TABLE_RETAILER_CHANNELS + " ret on cus.channelCode = ret.channelCode where cus.approvalStatus!='R' AND cus.retailerStatus='Y' ORDER BY cus.customerName ASC";
    public static final String QUERY_SELECT_RETAILER_BY_ROUTE_BILL = "SELECT cus.customerCode, cus.customerName, cus.latitude, cus.longitude, cus.channelCode, cus.approvalStatus, cus.retailerStatus, cusroute.coverageSequence,ret.channelName from " + TABLE_CUSTOMER + " cus INNER JOIN " + TABLE_RETAILER_CHANNELS + " ret on cus.channelCode = ret.channelCode INNER JOIN " + TABLE_CUSTOMER_ROUTE + " cusroute on cus.customerCode = cusroute.customerCode where cus.cmpCode=? AND cus.distrCode=? AND cusroute.routeCode=? AND cus.approvalStatus!=? AND cus.retailerStatus=? ORDER BY cus.customerName ASC";
    public static final String QUERY_SELECT_SALESMAN_MASTER_ACTIVE = "SELECT * from " + TABLE_SALESMAN_MASTER + " WHERE cmpCode =? AND distrCode=? AND status=?";

    /* getDistributorInfo */
    public static final String QUERY_SELECT_DISTRIBUTOR_INFO = "SELECT * FROM " + TABLE_DISTRIBUTOR + " WHERE " + COLUMN_DISTR_CODE + "=?" + " AND " + COLUMN_CMP_CODE + "=? LIMIT 1";

    /* getDistributorList */
    public static final String GET_DISTRIBUTOR_LIST="SELECT * FROM "+TABLE_DISTRIBUTOR+" AS dis INNER JOIN "+TABLE_COMPANY_DETAIL+" AS comp ON dis.cmpCode=comp.cmpCode";

    @NonNull
    public static String queryDeleteTable(String tableName) {
        return "DELETE FROM " + tableName;
    }

    @NonNull
    public static String selectAllFormTableQuery(String tableName) {
        return "SELECT * FROM " + tableName;
    }

}
