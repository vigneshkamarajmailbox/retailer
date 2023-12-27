package com.botree.retailerssfa.db.query;

import androidx.annotation.NonNull;

import com.botree.retailerssfa.support.Globals;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_KEY_SCREEN_COUNT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_KEY_SCREEN_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MODULE_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SCREEN_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.ICON_IS_ACTIVE;
import static com.botree.retailerssfa.db.query.IDBColumns.KEY_ICON_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_AUTO_QUICK_ACTION_MENUS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BANNER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BILLING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CONFIGURATION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_FOCUSBRAND_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_GST_STATE_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_MESSAGES;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_MUSTSELL_SKU_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_BOOKING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_QUICK_ACTION_MENUS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_RETAILER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ROUTE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SCREEN_CONFIGURATION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_VANSALES_PRODUCTS;

public class DashboardQueryHelper {

    /* getAllRoutes */
    public static final String QUERY_ALL_ROUTES = "SELECT * From " + TABLE_ROUTE + " WHERE distrCode=? ORDER BY routeName ASC";

    /* getAllRoutes based on today beat */
    public static final String QUERY_ALL_ROUTES_TODAY_BEAT = "SELECT * From " + TABLE_ROUTE + " WHERE distrCode=? ORDER BY routeName ASC";

    /* getTodayBeatCount */
    public static final String QUERY_RETAILERS_COUNT_FOR_ROUTE = "SELECT COUNT(*) as totcount FROM " + TABLE_RETAILER + " where routeCode=?";

    /* getRouteName */
    public static final String QUERY_ROUTE_NAME = "SELECT routeName FROM " + TABLE_ROUTE + " where routeCode=?";

    /* getTodayBeatRouteCode */
    public static final String QUERY_TODAY_BEAT_ROUTE_CODE = "SELECT * FROM " + TABLE_ROUTE + " WHERE distrCode=? ORDER BY routeName DESC";

    /* getMessageCount */
    public static final String QUERY_MESSAGE_COUNT = "SELECT SUM(msgStatus) as 'count' FROM " + TABLE_MESSAGES;
    /* update check fields */
    public static final String QUERY_UPDATE_DISTR_CODE_AND_MSG_CODE = "distrCode = ? and msgCode = ?";

    /* getAppStockProudcts */
    public static final String QUERY_STOCK_SCHEME_PRODUCT = "SELECT p.ProdCode,\n" +
            "       p.ProdName,\n" +
            "       p.ProdShortName,\n" +
            "       csp.schemecode,\n" +
            "       soh.availQty,\n" +
            "       pbm.MRP,\n" +
            "       p.productHierPathName,\n" +
            "       p.productHierPathCode,\n" +
            "  (SELECT uomCode\n" +
            "   FROM m_product_uom_master\n" +
            "   WHERE DefaultUom = 'Y'\n" +
            "     AND prodCode = csp.prodCode) AS defaultUOM,\n" +
            "       pbm.SellPrice,\n" +
            "       pbm.purchasePrice\n" +
            "FROM m_customerSchemeProductList csp\n" +
            "INNER JOIN m_product_master p ON csp.cmpCode = p.cmpCode\n" +
            "AND p.prodcode = csp.prodcode\n" +
            "LEFT JOIN m_product_batch_master pbm ON p.cmpCode = pbm.cmpCode\n" +
            "AND pbm.prodcode = csp.prodcode\n" +
            "LEFT JOIN m_stockOnHand soh ON pbm.cmpCode = soh.cmpCode\n" +
            "AND pbm.distrCode = soh.distrCode\n" +
            "AND pbm.prodcode = soh.prodcode\n" +
            "AND pbm.prodBatchCode = soh.prodBatchCode\n" +
            "WHERE csp.cmpcode = ?\n" +
            "GROUP BY p.prodCode";

    /* getAppStockProudcts */
    public static final String QUERY_STOCK_MUST_SELL_PRODUCTS = "SELECT p.ProdCode as ProdCode,p.ProdShortName,\n" +
            "       p.ProdName,\n" +
            "       p.StockInHand,\n" +
            "       p.MRP,\n" +
            "       p.ProdHierValName,\n" +
            "       p.ProdHierValCode,\n" +
            "       p.DefaultUomid,\n" +
            "       p.ProdHierValName,\n" +
            "       p.SellPrice,\n" +
            "       p.purchasePrice\n" +
            "FROM   " + TABLE_VANSALES_PRODUCTS + " p\n" +
            "       INNER JOIN " + TABLE_MUSTSELL_SKU_PRODUCTS + " msp\n" +
            "               ON p.cmpcode = msp.cmpcode\n" +
            "                  AND p.distrcode = msp.distrcode\n" +
            "       WHERE p.cmpcode = msp.cmpcode\n" +
            "                  AND p.distrcode = ?\n" +
            "                  AND p.prodcode = msp.prodcode";

    /* getAppStockProudcts */
    public static final String QUERY_FOCUS_BRANDS = "SELECT p.ProdCode as ProdCode,p.ProdShortName,\n" +
            "       p.ProdName,\n" +
            "       p.StockInHand,\n" +
            "       p.MRP,\n" +
            "       p.ProdHierValName,\n" +
            "       p.ProdHierValCode,\n" +
            "       p.DefaultUomid,\n" +
            "       p.ProdHierValName,\n" +
            "       p.SellPrice,\n" +
            "       p.purchasePrice\n" +
            "FROM   " + TABLE_VANSALES_PRODUCTS + " p\n" +
            "       INNER JOIN " + TABLE_FOCUSBRAND_PRODUCTS + " msp\n" +
            "               ON p.cmpcode = msp.cmpcode\n" +
            "                  AND p.distrcode = msp.distrcode\n" +
            "       WHERE p.cmpcode = msp.cmpcode\n" +
            "                  AND p.distrcode = ?\n" +
            "                  AND p.prodcode = msp.prodcode";

    /* getAppStockProudcts */
    public static final String QUERY_STOCK_AVAILABLE_PRODUCTS = "SELECT * FROM " + TABLE_VANSALES_PRODUCTS + " where DistrCode=? And StockInHand > 0 ORDER BY StockInHand DESC";

    /* getAppStockProudcts */
    public static final String QUERY_OUT_OF_STOCK_PRODUCTS = "SELECT * FROM " + TABLE_VANSALES_PRODUCTS + " where DistrCode=? And StockInHand = 0";

    /* getAppStockProudcts */
    public static final String QUERY_ALL_PRODUCTS = "SELECT * FROM " + TABLE_VANSALES_PRODUCTS + " where DistrCode=? ORDER BY StockInHand DESC";

    /* getIsFavoriteMenu */
    public static final String QUERY_SELECT_ACTIVE_QUICK_ACTION_MENU = "SELECT * FROM " + TABLE_QUICK_ACTION_MENUS + " where " + KEY_ICON_NAME + "= ? AND " + ICON_IS_ACTIVE + "=?";

    /* getAutoQuickMenuCount */
    public static final String QUERY_SELECT_MENU_USE_COUNTS = "SELECT * FROM " + TABLE_AUTO_QUICK_ACTION_MENUS + " where " + COLUMN_KEY_SCREEN_NAME + "= ?";

    /* getStateDataFromDB */
    public static final String QUERY_SELECT_GST_MASTER = "SELECT * FROM '" + TABLE_GST_STATE_MASTER + "' where cmpCode=? Order by gstStateName Asc";

    /* getConfigData */
    public static final String QUERY_SELECT_CONFIG_DATA = "SELECT * from " + TABLE_CONFIGURATION + " WHERE " + TABLE_NAME + " =?";
    /* getConfigData */
    public static final String QUERY_IS_SCREEN_AVALABLE = "SELECT * from " + TABLE_SCREEN_CONFIGURATION + " WHERE " + COLUMN_MODULE_NAME + " =? AND " +
            COLUMN_SCREEN_NAME + " =? ";
    /* fetchAllBannerList  */
    public static final String QUERY_BANNER_LIST = "SELECT * from " + TABLE_BANNER;
    /* fetchSchemeMasterProducts */
    static final String QUERY_SCHEME_DETAIL_BY_PROD_CODE = " SELECT prodcode,\n" +
            "       prodname,\n" +
            "       schemebase,\n" +
            "       schemecode,\n" +
            "       schemedescription,\n" +
            "       starttime,\n" +
            "       endtime,\n" +
            "       payouttype\n" +
            "FROM m_customerSchemeProductList rsp\n" +
            "WHERE cmpCode = ?\n" +
            "  AND prodcode = ? GROUP  BY schemecode;";

    static final String QUERY_ROUTES = "SELECT route.routeName,\n" +
            "       route.routeCode,\n" +
            "       route.routeType,\n" +
            "       COUNT(ret.customerCode) AS count\n" +
            "FROM m_route route\n" +
            "LEFT JOIN t_customerroute ret ON route.routeCode = ret.routeCode\n" +
            "WHERE route.distrCode= ?\n" +
            "GROUP BY route.routeCode\n" +
            "ORDER BY routeName COLLATE nocase ASC";

    private DashboardQueryHelper() {
    }



    /**
     * Select Message list by checking delete status
     *
     * @param limit limit to get messages
     * @return query
     */
    @NonNull
    public static String getQuqeyMessageListWithDeleteStatus(Integer limit) {
        String query;
        if (limit > 0) {
            query = "SELECT * FROM " + TABLE_MESSAGES + " WHERE distrCode=? AND salesmanCode=? AND msgDeleteStatus=? order By date DESC limit " + limit;
        } else {
            query = "SELECT * FROM " + TABLE_MESSAGES + " WHERE distrCode=? AND salesmanCode=? AND msgDeleteStatus=? order By date DESC";
        }
        return query;
    }



    @NonNull
    public static  String getQuerySelectAllScreensUseageCount(int menucount) {

        return "SELECT * FROM " + TABLE_AUTO_QUICK_ACTION_MENUS + " ORDER BY " + COLUMN_KEY_SCREEN_COUNT + " DESC limit " + menucount + ";";
    }

    public static final String QUERY_STOCK_SCHEME_PARTICULAR_PRODUCT = "SELECT p.ProdCode,\n" +
            "       p.ProdName,\n" +
            "       p.ProdShortName,\n" +
            "       csp.schemecode,\n" +
            "       soh.availQty,\n" +
            "       pbm.MRP,\n" +
            "       p.productHierPathName,\n" +
            "       p.productHierPathCode,\n" +
            "  (SELECT uomCode\n" +
            "   FROM m_product_uom_master\n" +
            "   WHERE DefaultUom = 'Y'\n" +
            "     AND prodCode = csp.prodCode) AS defaultUOM,\n" +
            "       pbm.SellPrice,\n" +
            "       pbm.purchasePrice\n" +
            "FROM m_customerSchemeProductList csp\n" +
            "INNER JOIN m_product_master p ON csp.cmpCode = p.cmpCode\n" +
            "AND p.prodcode = csp.prodcode\n" +
            "LEFT JOIN m_product_batch_master pbm ON p.cmpCode = pbm.cmpCode\n" +
            "AND pbm.prodcode = csp.prodcode\n" +
            "LEFT JOIN m_stockOnHand soh ON pbm.cmpCode = soh.cmpCode\n" +
            "AND pbm.distrCode = soh.distrCode\n" +
            "AND pbm.prodcode = soh.prodcode\n" +
            "AND pbm.prodBatchCode = soh.prodBatchCode\n" +
            "WHERE csp.cmpcode = ?\n" +
            "  AND csp.schemecode= ?\n" +
            "GROUP BY p.prodCode";

    /**
     * @param screenName to differentiate Order booking or Billing Table
     * @return Query to fetch routes
     */
    public static String getQueryOrderedRoute(String screenName) {
        String query;
        if (screenName.equalsIgnoreCase(Globals.NAME_INVOICE_SUMMARY)) {

//            query = "select o.routeCode,r.routeName from " + TABLE_BILLING + " o\n" +
//                    "        LEFT JOIN " + TABLE_ROUTE + " r ON r.distrCode=o.distrCode AND r.salesmanCode=o.salesmanCode AND r.routeCode=o.routeCode\n" +
//                    "        WHERE o.distrCode=? AND o.salesmanCode= ? group by r.routeName\n" +
//                    "        ORDER BY r.routeName ASC";

            query = "select o.routeCode,r.routeName from " + TABLE_BILLING + " o\n" +
                    "        LEFT JOIN " + TABLE_ROUTE + " r ON r.distrCode=o.distrCode AND r.routeCode=o.routeCode\n" +
                    "        WHERE o.distrCode=? group by r.routeName\n" +
                    "        ORDER BY r.routeName ASC";

        } else {

            query = "select o.routeCode,r.routeName from " + TABLE_ORDER_BOOKING + " o\n" +
                    "        LEFT JOIN " + TABLE_ROUTE + " r ON r.distrCode=o.distrCode AND r.routeCode=o.routeCode\n" +
                    "        WHERE o.distrCode=? group by r.routeName\n" +
                    "        ORDER BY r.routeName ASC";

//            query = "select o.routeCode,r.routeName from " + TABLE_ORDER_BOOKING + " o\n" +
//                    "        LEFT JOIN " + TABLE_ROUTE + " r ON r.distrCode=o.distrCode AND r.salesmanCode=o.salesmanCode AND r.routeCode=o.routeCode\n" +
//                    "        WHERE o.distrCode=? AND o.salesmanCode= ? group by r.routeName\n" +
//                    "        ORDER BY r.routeName ASC";
        }
        return query;
    }

    /* getOtherBeatCounts */
    public static final String QUERY_ALL_OTHER_BEAT_RETAILERS_COUNT = "SELECT COUNT(distinct customerCode) as totcount, routeCode FROM " + TABLE_RETAILER + " where distrCode=? AND isTodayBeat='false' GROUP BY routeCode";

}
