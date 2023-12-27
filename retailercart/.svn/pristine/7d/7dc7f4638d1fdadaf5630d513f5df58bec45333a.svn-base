package com.botree.retailerssfa.db.query;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.support.Globals;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BANK_NAMES;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BILLING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BILLING_COLLECTION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BILLING_TRACKER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BILL_INVOICE_DETAILS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CASH_COLLECTION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CATEGORY_SEQUENCE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_CHEQUE_COLLECTION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_COLLECTION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_FOCUSBRAND_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_FOCUSBRAND_RETAILERS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_LOADING_STOCK;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_MUSTSELL_RETAILERS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_MUSTSELL_SKU_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_OPENING_STOCK;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_OPENING_STOCK_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_BOOKING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_BOOKING_REPORT_DETAILS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_BOOKING_TRACKER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_CONFIRMATION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCT_BATCH_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCT_CATEGORY;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCT_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCT_SUGGESTIONS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCT_UOM_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PURCHASE_ORDER_BOOKING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PURCHASE_PARTIAL_RETURN;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PURCHASE_RECEIPT_CONFIRMATION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_REASONS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_RETAILER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_RETAILER_CATEGORY_ID;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ROUTE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALES_RETURN;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALES_RETURN_DETAILS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALES_RETURN_HEADER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_STOCK_TAKE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_UOM_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_VANSALES_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_VANSALES_UOM_MASTER;

public class OrderBookingQueryHelper {
    public static final String QUERY_SELECT_ALL_DISTR_PURCHASE_PRODUCT = "SELECT p.prodcode, \n" +
            "       pbm.prodbatchcode, \n" +
            "       p.prodname, \n" +
            "       p.prodshortname, \n" +
            "       p.producthierpathname, \n" +
            "       p.prodhierlastvalcode, \n" +
            "       p.productHierPathCode, \n" +
            "       p.lobDivisionCode, \n" +
            "       p.lobSubDivisionCode, \n" +
            "       pbm.sellprice, \n" +
            "       pbm.purchaseprice, \n" +
            "       pbm.mrp, \n" +
            "       p.prodnetwgt, \n" +
            "       p.prodwgttype, \n" +
            "       ob.orderqty, \n" +
            "       ob.uomid, \n" +
            "       ob.orderstatus, \n" +
            "       ob.remarks, \n" +
            "       ob.ordervalue, \n" +
            "       bst.stock, \n" +
            "       st.availQty, \n" +
            "       um.uomcode AS baseUom, \n" +
            "       CASE \n" +
            "         WHEN ob.uomid IS NOT NULL \n" +
            "              AND ob.uomid IN ( 'GM', 'KG', 'ML', 'LT' ) THEN um.uomCode\n" +
            "         WHEN ob.uomid IS NOT NULL THEN ob.uomid \n" +
            "         ELSE um.uomCode\n" +
            "       END        AS uomCode \n" +
            "FROM   m_product_master p \n" +
            "       left JOIN m_product_batch_master pbm \n" +
            "               ON pbm.cmpcode = p.cmpcode \n" +
            "                  AND pbm.prodcode = p.prodcode \n" +
            "                  AND pbm.latestBatch ='Y' \n" +
            "       LEFT JOIN t_purchaseorderbooking ob \n" +
            "              ON ob.prodcode = p.prodcode \n" +
            "              AND ob.BatchCode = pbm.ProdBatchCode \n" +
            "                 AND ob.cmpcode = p.cmpcode \n" +
            "                 AND ob.confirm_status = 'N' \n" +
            "                 AND ob.upload = 'N' \n" +
            "       LEFT JOIN m_product_uom_master um \n" +
            "              ON um.cmpcode = p.cmpcode \n" +
            "                 AND um.prodcode = p.prodcode \n" +
            "                 AND um.baseuom = 'Y' \n" +
            "                 AND um.defaultUOM = 'Y' \n" +
            "       LEFT JOIN m_branch_stock bst \n" +
            "              ON bst.cmpcode = p.cmpcode \n" +
            "                 AND bst.prodcode = p.prodcode \n" +
            "       LEFT JOIN m_stockonhand st \n" +
            "              ON st.cmpcode = p.cmpcode \n" +
            "                 AND st.prodcode = p.prodcode \n" +
            "                 AND st.ProdBatchCode = pbm.ProdBatchCode \n" +
            "WHERE  p.cmpcode = ? COLLATE nocase ";


    /* getAllProducts */
    public static final String QUERY_SELECT_ALL_PRODUCTS ="select pbm.cmpcode,\n" +
            "pbm.distrcode,\n" +
            "p.prodcode,\n" +
            "p.prodname,\n" +
            "p.prodshortname,\n" +
            "p.prodcode AS prodhiervalname,\n" +
            "p.producthierpathcode,\n" +
            "p.producthierpathname,\n" +
            "'' AS brandname,\n" +
            "'' AS brandCode,\n" +
            "pbm.prodbatchcode,\n" +
            "pbm.sellprice,\n" +
            "pbm.mrp,\n" +
            "d.gstStateCode AS distrStateCode,\n" +
            "cs.gstStateCode AS retailerStateCode,\n" +
            "cs.customerShipCode,\n" +
            "c.customerCode,\n" +
            "0 AS primaryDisc,\n" +
            "ds.saleableQty AS stockinhand,\n" +
            "p.prodnetwgt,\n" +
            "p.prodwgttype,\n" +
            "cst.soq as suggestedquantity,\n" +
            "cst.ppq,\n" +
            "0 AS MssQty,\n" +
            "0 AS topSKU,\n" +
            "ob.invoiceno,\n" +
            "ob.orderqty,\n" +
            "ob.uomid,\n" +
            "ob.orderstatus,\n" +
            "ob.remarks,\n" +
            "ob.actualSellRate,\n" +
            "ob.netAmount,\n" +
            "ob.ordervalue,\n" +
            "ob.primarydiscordervalue,\n" +
            "ob.schemeAmount,\n" +
            "ob.conversionfactor,\n" +
            "ob.CGSTValue,\n" +
            "ob.cgstperc,\n" +
            "ob.sgstperc,\n" +
            "ob.SGSTValue,\n" +
            "ob.utgstperc,\n" +
            "ob.UTGSTValue,\n" +
            "ob.igstperc,\n" +
            "ob.IGSTValue,\n" +
            "ob.readableInvNo,\n" +
            "'' as category,\n" +
            "ds.saleableQty AS stockqty,\n" +
            "um.uomcode,\n" +
            "0 AS StockOrderValue,\n" +
            "um.uomcode AS StockUomId,\n" +
            "um.uomcode AS StockDefaultUomId,\n" +
            "1 AS convfactor,\n" +
            "p.prodcode AS uomgroupid,\n" +
            "CASE\n" +
            "WHEN ob.uomid IS NOT NULL\n" +
            "AND ob.uomid IN ('GM', 'KG', 'ML', 'LT') THEN um.uomcode\n" +
            "WHEN ob.uomid IS NOT NULL THEN ob.uomid\n" +
            "ELSE um.uomcode\n" +
            "END AS DefaultUomid\n" +
            "FROM m_product_master p\n" +
            "INNER JOIN m_product_uom_master um\n" +
            "ON p.cmpcode = um.cmpcode\n" +
            "AND p.prodcode = um.prodcode\n" +
            "AND um.baseuom = 'Y'\n" +
            "INNER JOIN m_product_batch_master pbm\n" +
            "ON p.cmpcode = pbm.cmpcode\n" +
            "AND p.prodcode = pbm.prodcode\n" +
            "INNER JOIN m_distributor d\n" +
            "ON pbm.cmpCode = d.cmpCode\n" +
            "AND pbm.distrCode = d.distrCode\n" +
            "INNER JOIN t_customer c\n" +
            "ON d.cmpcode = c.cmpcode\n" +
            "AND d.distrCode = c.distrCode\n" +
            "INNER JOIN t_customershipaddress cs\n" +
            "ON c.cmpCode = cs.cmpCode\n" +
            "AND c.distrCode = cs.distrCode\n" +
            "AND c.customercode = cs.customercode\n" +
            "AND defaultShipAddr = 'Y'\n" +
            "LEFT JOIN t_orderbooking ob\n" +
            "ON pbm.cmpcode = ob.cmpcode\n" +
            "AND pbm.distrcode = ob.distrcode\n" +
            "AND pbm.prodcode = ob.prodcode\n" +
            "AND pbm.prodbatchcode = ob.batchcode\n" +
            "AND ob.upload = 'N'\n" +
            "left JOIN m_distributorstock ds\n" +
            "ON pbm.cmpcode = ds.cmpcode\n" +
            "AND pbm.distrcode = ds.distrcode\n" +
            "AND pbm.prodcode = ds.prodcode\n" +
            "left JOIN m_customerStock cst\n" +
            "ON pbm.cmpcode = cst.cmpcode\n" +
            "AND pbm.distrcode = cst.distrcode\n" +
            "AND pbm.prodcode = cst.prodcode\n" +
            "WHERE (d.loadStockProd = 'N' OR (d.loadStockProd = 'Y' AND ds.saleableQty > 0))\n" +
            "ORDER BY ob.orderqty DESC, p.prodname COLLATE nocase ASC;";

    /* getAllBillingProducts */
    public static final String QUERY_SELECT_ALL_BILLING_PRODUCTS = "SELECT DISTINCT(p.batchcode),\n" +
            "               p.prodcode,\n" +
            "               p.prodname,\n" +
            "               p.prodshortname,\n" +
            "               p.prodhiervalname,\n" +
            "               p.productHierPath,\n" +
            "               p.productHierPathName,\n" +
            "               p.brandName,\n" +
            "               p.brandCode,\n" +
            "               p.sellprice,\n" +
            "               p.mrp,\n" +
            "               p.primaryDisc,\n" +
            "               p.prodNetWgt,\n" +
            "               p.prodWgtType,\n" +
            "               ob.OrderQty,\n" +
            "               ob.uomid,\n" +
            "               ob.orderstatus,\n" +
            "               ob.remarks,\n" +
            "               ob.ordervalue,\n" +
            "               ob.primaryDiscOrderValue,\n" +
            "               ob.conversionfactor,\n" +
            "               ob.freeqty,\n" +
            "               um.uomCode,\n" +
            "               ls.availQty   AS loadstock,\n" +
            "               pc.category,\n" +
            "               p.uomgroupid,\n" +
            "               CASE\n" +
            "                 WHEN ob.uomid IS NOT NULL AND ob.uomid   IN ('GM','KG','ML','LT') THEN p.defaultuomid\n" +
            "                 WHEN ob.uomid IS NOT NULL THEN ob.uomid\n" +
            "                 ELSE p.defaultuomid\n" +
            "               END           AS DefaultUomid\n" +
            "FROM   " + TABLE_VANSALES_PRODUCTS + " p\n" +
            "       \n" +
            "       INNER JOIN " + TABLE_LOADING_STOCK + " ls\n" +
            "               ON ls.prodcode = p.prodcode\n" +
            "                  AND ls.batchcode = p.batchcode\n" +
            "                  AND ls.distrcode = p.distrcode\n" +
            "                  AND ls.salesmancode = p.salesmancode\n" +
            "                  AND ls.StockStatus = 'C'\n" +
            "                  AND ls.upload = 'N'\n" +
            "       LEFT JOIN " + TABLE_BILLING + " ob\n" +
            "              ON ob.prodcode = p.prodcode\n" +
            "                 AND ob.batchcode = p.batchcode\n" +
            "                 AND ob.distrcode = p.distrcode\n" +
            "                 AND ob.salesmancode = p.salesmancode\n" +
            "                 AND ob.routecode = ? \n" +
            "                 AND ob.retlrcode = ? \n" +
            "                 AND ob.completeFlag = 'N' \n" +
            "                 AND ob.upload = 'N'\n" +
            "       LEFT JOIN " + TABLE_PRODUCT_CATEGORY + " pc\n" +
            "              ON pc.prodcode = p.prodcode\n" +
            "       LEFT JOIN " + TABLE_RETAILER_CATEGORY_ID + " rc\n" +
            "              ON rc.distrcode = p.distrcode\n" +
            "                 AND rc.salesmancode = p.salesmancode\n" +
            "                 AND rc.routecode = ? \n" +
            "                 AND rc.retlrcode = ? \n" +
            "       LEFT JOIN " + TABLE_VANSALES_UOM_MASTER + " um\n" +
            "              ON um.distrcode = p.distrcode\n" +
            "                 AND um.salesmancode = p.salesmancode\n" +
            "                 AND um.prodCode = p.prodCode\n" +
            "                 AND um.baseUom = 'Y'\n" +
            "WHERE  p.distrcode = ? \n" +
            "       AND p.salesmancode = ?  ORDER By p.prodname COLLATE nocase ASC";

    /* getStockSummaryData */
    public static final String QUERY_SELECT_STOCK_SUMMARY_DATA = "SELECT DISTINCT( p.ProdCode ),\n" +
            "               ls.batchcode,\n" +
            "               p.prodname,\n" +
            "               p.prodshortname,\n" +
            "               ls.mrp,\n" +
            "               ls.SellPrice,\n" +
            "               ls.OrderValue,\n" +
            "               ls.availQty   AS availQty,\n" +
            "               ls.StockInHand   AS StockInHand\n" +
            "FROM   " + TABLE_VANSALES_PRODUCTS + " p\n" +
            "       INNER JOIN " + TABLE_LOADING_STOCK + " ls\n" +
            "               ON ls.prodcode = p.prodcode\n" +
            "                  AND ls.batchcode = p.batchcode\n" +
            "                  AND ls.distrcode = p.distrcode\n" +
            "                  AND ls.salesmanCode = p.salesmanCode\n" +
            "                  AND ls.upload = 'N'\n" +
            "WHERE  p.distrcode =? and p.salesmanCode =?";

    public static final String QUERY_SELECT_TAX_MASTER_DATA = "SELECT prod.prodName, tax.cgst, tax.igst, tax.sgst, tax.cess from" +
            " m_product_master prod INNER JOIN m_product_tax_master tax ON prod.prodCode = tax.prodCode group by prod.prodCode";


    /* fetchCategory */
    public static final String QUERY_SELECT_CATEGORY = "SELECT 'Focus' AS Category UNION ALL SELECT 'Must Sell' UNION ALL SELECT 'Promo'";

    /* getOpeningStockUOMFromDB */
    public static final String QUERY_SELECT_OPENING_STOCK_UOM = "SELECT Distinct uomCode, uomDescription, conversionFactor FROM "
            + TABLE_VANSALES_UOM_MASTER + " where prodCode =? AND baseUom =? ";

    /* checkOrderBookingTracker */
    public static final String QUERY_CHECK_ORDER_BOOKING_TRACKER = "SELECT * FROM " + TABLE_ORDER_BOOKING_TRACKER + " o WHERE  o.distrCode= ? AND o.cmpCode= ?" +
            " AND o.customerCode= ? AND upload = 'N'";

    /* checkBillingsTracker */
    public static final String QUERY_CHECK_BILLING_TRACKER = "SELECT * FROM " + TABLE_BILLING_TRACKER + " o WHERE  o.distrCode= ? AND o.salesmanCode= ?" +
            " AND o.routeCode = ? AND o.customerCode= ? AND upload = 'N'";


    /* isPurchaseProductAdded */
    public static final String QUERY_IS_PURCHASE_ORDER_ADDED = "SELECT * FROM " + TABLE_PURCHASE_ORDER_BOOKING + " WHERE distrCode=? AND ProdCode=? AND BatchCode=? AND invoiceno=?";


    /* getPurchaseOrderedSummaryFromDB */
    public static final String QUERY_PURCHASE_ORDER_SUMMARY = "SELECT o.batchcode, \n" +
            "       o.prodcode, \n" +
            "       p.prodname, \n" +
            "       p.prodshortname, \n" +
            "       o.orderqty, \n" +
            "       o.uomid, \n" +
            "       o.taxperproduct, \n" +
            "       o.schemeamount, \n" +
            "       o.netamount, \n" +
            "       o.grossamount, \n" +
            "       o.mrp, \n" +
            "       o.purchaseprice, \n" +
            "       o.CGSTPerc, \n" +
            "       o.SGSTPerc, \n" +
            "       o.UTGSTPerc, \n" +
            "       o.IGSTPerc, \n" +
            "       pbm.SellPrice, \n" +
            "       o.ordervalue \n" +
            "FROM   t_purchaseorderbooking o \n" +
            "       LEFT JOIN m_product_batch_master pbm \n" +
            "               ON pbm.cmpcode = o.cmpcode \n" +
            "                  AND pbm.prodcode = o.prodcode \n" +
            "                  AND pbm.prodbatchcode = o.batchcode \n" +
            "       LEFT JOIN m_product_master p \n" +
            "              ON p.prodcode = o.prodcode \n" +
            "                 AND p.cmpcode = o.cmpcode \n" +
            "WHERE o.upload = 'N' \n" +
            "       AND o.invoiceno = ? ";

    /* getOrderedProductFromDB */
    public static final String QUERY_ORDERED_PRODUCT = "SELECT Distinct(pbm.prodbatchcode), p.prodcode, \n" +
            "       p.ProdName, \n" +
            "       p.prodshortname, \n" +
            "       o.orderqty, \n" +
            "       o.cmpCode, \n" +
            "       o.distrCode, \n" +
            "       o.invoiceNo, \n" +
            "       o.RetlrCode, \n" +
            "       o.uomid, \n" +
            "       o.readableInvNo, \n" +
            "       o.netamount, \n" +
            "       o.grossamount, \n" +
            "       o.schemeamount, \n" +
            "       o.totalamount, \n" +
            "       o.cgstvalue, \n" +
            "       o.cgstperc, \n" +
            "       o.sgstperc, \n" +
            "       o.sgstvalue, \n" +
            "       o.utgstperc, \n" +
            "       o.utgstvalue, \n" +
            "       o.igstperc, \n" +
            "       o.igstvalue, \n" +
            "       o.taxperproduct, \n" +
            "       o.actualSellRate as SellPrice, \n" +
            "       pbm.MRP, \n" +
            "       o.ordervalue, \n" +
            "       o.primarydiscordervalue, \n" +
            "       o.conversionFactor, \n" +
            "       0 as availQty, \n" +
            "       0 as lastOrdQty, \n" +
            "       0 as totOrdQty, \n" +
            "       0 as stockqty, \n" +
            "       o.uomid      AS stockUomId, \n" +
            "       o.totalAmount AS StockOrderValue \n" +
            "FROM   m_product_master p \n" +
            "left join m_product_batch_master pbm \n" +
            "on p.prodCode = pbm.prodCode\n" +
            "and p.cmpCode = pbm.cmpCode\n" +
            "       LEFT JOIN t_OrderBooking o \n" +
            "              ON o.prodcode = p.prodcode \n" +
            "                 AND pbm.prodbatchcode = o.batchcode \n" +
            "                 AND o.upload = 'N' \n" +
            "WHERE  o.orderqty > 0";


    /* isBilledProductAdded */
    public static final String QUERY_CHECK_BILLED_PRODUCT_ADDED = "SELECT * FROM " + TABLE_BILL_INVOICE_DETAILS + " WHERE distrCode=? " +
            "AND salesmanCode=? AND routeCode=? AND customerCode=? AND invoiceNo=? AND prodCode=? AND ProdBatchCode=? " +
            "and parentProdCode = ? and parentProdBatchCode = ?";

    /* getBankDetailFromDB */
    public static final String QUERY_SELECT_BANK_DETAILS = "SELECT * FROM " + TABLE_BANK_NAMES + " WHERE distrCode =? AND salesmanCode=? ORDER BY " + IDBColumns.COLUMN_BANK_NAME + " ASC";

    /* loadCashCollection */
    public static final String QUERY_SELECT_CASH_COLLECTION = "SELECT * FROM '" + TABLE_CASH_COLLECTION + "' WHERE distrCode =?  AND salesmanCode =? AND routeCode =? AND customerCode = ? AND upload = 'N'";

    /* loadChequeCollection */
    public static final String QUERY_SELECT_CHEQUE_COLLECTION = "SELECT * FROM '" + TABLE_CHEQUE_COLLECTION + "' WHERE distrCode =?  AND salesmanCode =? AND routeCode =? AND customerCode = ? AND upload = 'N'";

    /* loadBillingCashCollection */
    public static final String QUERY_SELECT_COLLECTION_AMOUNT = "SELECT * FROM '" + TABLE_BILLING_COLLECTION + "' WHERE distrCode =?  AND salesmanCode =? AND routeCode =? AND customerCode = ? AND upload = 'N'";

    /* getSalesReturnReasons */
    public static final String QUERY_SELECT_REASON = "SELECT * FROM " + TABLE_REASONS + " WHERE ScreenName = ? COLLATE NOCASE";

    /* loadExistingPurchaseReturns */
    public static final String QUERY_SELECT_EXISTING_PURCHASE_RETURN = "SELECT DISTINCT sr.retlrCode,sr.prodCode,p.BatchCode,sr.salQty,sr.unSalQty,p.prodShortName,r.reasonName,p.sellPrice,sr.ReasonCode,"
            + "r.ReasonName from " + TABLE_SALES_RETURN + " sr INNER JOIN " + TABLE_PRODUCTS + " p ON sr.prodCode=p.prodCode LEFT JOIN " + TABLE_REASONS + " r ON "
            + "sr.reasonCode=r.reasonCode WHERE p.cmpCode=? AND sr.distrCode = ? AND upload = 'N' group by sr.prodCode";

    /* Update Check condition for distributor code ,salesman code,route code, customer code.     */
    public static final String UPDATE_CHECK_DC_SC_RC_CC = "distrCode = ? and salesmanCode = ? and routeCode = ? and customerCode = ?";

    /* Update Check condition distrbutor code, salesman code, route code,retailer code,product code, uploadd.*/
    public static final String UPDATE_CHECK_DC_SC_RC_RTLC_PC_U = "distrCode=? and  cmpCode=? and RetlrCode=? and ProdCode=? and BatchCode=? and upload=?";

    /* getAllReturnProductFromAuto */
    public static final String QUERY_SELECT_ALL_RETURN_PRODUCT = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE cmpCode=? AND DistrCode=?";

    /* isStockProductAdded */
    public static final String QUERY_CHECK_IS_STOCK_PRODUCT_ADDED = "SELECT * FROM " + TABLE_STOCK_TAKE + " WHERE distrCode=? " +
            "AND salesmanCode=? AND routeCode=? AND RetlrCode=? AND ProdCode=? AND BatchCode=?";

    /* isOpeningStockProductAdded */
    public static final String QUERY_CHECK_IS_OPENING_STOCK_PROD_ADDED = "SELECT * FROM " + TABLE_OPENING_STOCK + " WHERE distrCode=? " +
            "AND invoiceNo=? AND ProdCode=?";

    /* getOpeningStockSummaryProducts */
    public static final String QUERY_SELECT_OPENING_STOCK_SUMMARY_PRODUCT = "SELECT * from " + TABLE_OPENING_STOCK + " WHERE  distrcode = ? ";

    /* fetchAllOpeningStockProducts */
    public static final String QUERY_SELECT_ALL_OPENING_STOCK_PRODUCT = " SELECT p.prodcode,\n" +
            "               p.prodname,\n" +
            "               ob.freeQty,\n" +
            "               ob.freeUOM,\n" +
            "               ob.UnSalQty,\n" +
            "               ob.UnSalUom,\n" +
            "               ob.stockqty,\n" +
            "               vp.prodshortname,\n" +
            "               vp.prodhiervalname,\n" +
            "               vp.productHierPath,\n" +
            "               vp.productHierPathName,\n" +
            "               vp.brandname,\n" +
            "               vp.brandCode,\n" +
            "               um.uomCode\n" +
            "FROM   " + TABLE_OPENING_STOCK_MASTER + " p\n" +
            "       LEFT JOIN " + TABLE_VANSALES_PRODUCTS + " vp\n" +
            "              ON vp.prodcode = p.prodcode\n" +
            "                 AND vp.distrcode = p.distrcode\n" +
            "                 AND vp.cmpCode = p.cmpCode\n" +
            "       LEFT JOIN " + TABLE_OPENING_STOCK + " ob\n" +
            "              ON ob.prodcode = p.prodcode\n" +
            "                 AND ob.distrcode = p.distrcode\n" +
            "                 AND ob.cmpCode = p.cmpCode\n" +
            "                 AND ob.upload = 'N'\n" +
            "       LEFT JOIN " + TABLE_VANSALES_UOM_MASTER + " um\n" +
            "              ON um.distrcode = p.distrcode\n" +
            "                 AND um.prodcode = p.prodcode\n" +
            "                 AND um.baseuom = 'Y'\n" +
            "WHERE  p.distrcode = ? COLLATE NOCASE \n" +
            "       AND p.cmpCode = ? ";

    /* getAllStockTakeProducts */
    public static final String QUERY_SELECT_ALL_STOCK_TAKE_PRODUCTS = "SELECT DISTINCT( p.BatchCode ), \n" +
            "               p.prodname, \n" +
            "               p.prodcode, \n" +
            "               p.prodshortname, \n" +
            "               p.prodhiervalname, \n" +
            "               p.productHierPath, \n" +
            "               p.brandcode, \n" +
            "               p.brandname, \n" +
            "               p.brandCode, \n" +
            "               p.sellprice, \n" +
            "               p.mrp, \n" +
            "               p.stockinhand, \n" +
            "               p.prodNetWgt,\n" +
            "               p.prodWgtType,\n" +
            "               ps.suggestedquantity, \n" +
            "               ob.stockqty, \n" +
            "               ob.stockstatus, \n" +
            "               ob.uomid, \n" +
            "               ob.ordervalue, \n" +
            "               ob.StockChecked, \n" +
            "               ob.conversionFactor, \n" +
            "               pc.category, \n" +
            "               um.uomCode, \n" +
            "               p.uomgroupid, \n" +
            "               CASE \n" +
            "                 WHEN ob.uomid IS NOT NULL AND ob.uomid   IN ('GM','KG','ML','LT') THEN p.defaultuomid\n" +
            "                 WHEN ob.uomid IS NOT NULL THEN ob.uomid \n" +
            "                 ELSE p.defaultuomid \n" +
            "               END AS DefaultUomid \n" +
            "FROM   " + TABLE_PRODUCTS + " p \n" +
            "       LEFT JOIN " + TABLE_PRODUCT_SUGGESTIONS + " ps \n" +
            "              ON ps.prodcode = p.prodcode \n" +
            "                 AND ps.retlrcode = ? \n" +
            "       LEFT JOIN " + TABLE_STOCK_TAKE + " ob \n" +
            "              ON ob.prodcode = p.prodcode \n" +
            "                 AND ob.BatchCode = p.BatchCode \n" +
            "                 AND ob.distrcode = p.distrcode \n" +
            "                 AND ob.salesmancode = p.salesmancode \n" +
            "                 AND ob.routecode = ? \n" +
            "                 AND ob.retlrcode = ? \n" +
            "                 AND ob.upload = 'N' \n" +
            "       LEFT JOIN " + TABLE_PRODUCT_CATEGORY + " pc \n" +
            "              ON pc.prodcode = p.prodcode \n" +
            "       LEFT JOIN " + TABLE_RETAILER_CATEGORY_ID + " rc \n" +
            "              ON rc.distrcode = p.distrcode \n" +
            "                 AND rc.salesmancode = p.salesmancode \n" +
            "                 AND rc.routecode = ? \n" +
            "                 AND rc.retlrcode = ? \n" +
            "\t\t LEFT JOIN " + TABLE_UOM_MASTER + " um \n" +
            "              ON um.distrcode = p.distrcode \n" +
            "                 AND um.salesmancode = p.salesmancode \n" +
            "                 AND um.prodCode= p.prodCode\n" +
            "                 AND um.baseUom = 'Y' \n" +
            "WHERE  p.distrcode = ? \n" +
            "       AND p.salesmancode = ? ";

    /* getStockTakeProductsFromDB combine with product table*/
    public static final String QUERY_SELECT_STOCK_TAKE_PRODUCT = "SELECT o.prodCode,o.BatchCode,p.prodName,p.prodShortName,o.StockQty,o.uomId,p.sellPrice,p.mrp," +
            " o.OrderValue,o.StockChecked,o.conversionFactor FROM " + TABLE_STOCK_TAKE + " o," + TABLE_PRODUCTS + " p "
            + "where o.prodCode=p.prodCode and p.BatchCode=o.BatchCode and o.distrCode=p.distrCode " +
            "and p.salesmanCode=o.salesmanCode and o.distrCode=? and o.salesmanCode= ? and o.routeCode= ? and o.RetlrCode= ? and o.StockQty >= 0  and o.stockChecked='Y' AND upload = 'N'";

    /* getStockTakeProductsOnlyWithQtyFromDB */
    public static final String QUERY_SELECT_STOCK_TAKE_PRODUCT_ONLY_WITH_QUANTITY = "SELECT o.prodCode,p.prodName,p.BatchCode,p.prodShortName,o.StockQty,o.uomId,p.sellPrice,p.mrp," +
            " o.OrderValue,o.StockChecked FROM " + TABLE_STOCK_TAKE + " o," + TABLE_PRODUCTS + " p "
            + "where o.prodCode=p.prodCode and p.BatchCode=o.BatchCode and o.distrCode=p.distrCode " +
            "and p.salesmanCode=o.salesmanCode and o.distrCode=? and o.salesmanCode= ? and o.routeCode= ? and o.RetlrCode= ? and o.StockQty > 0  and o.stockChecked='Y' AND upload = 'N'";

    /* fetchPreviousOrderDetails */
    public static final String QUERY_SELECT_PREVIOUS_ORDER_DETAILS = "SELECT * FROM r_orderBookingDetails where cmpCode =? AND distrCode = ? and orderNo =?";

    /* getAllReceiptProducts */
    public static final String QUERY_SELECT_ALL_RECEIPT_PRODUCTS = "SELECT\n" +
            "   ob.prodCode,\n" +
            "   p.prodName,\n" +
            "   ob.purchPrice,\n" +
            "   ob.mrp,\n" +
            "   ob.cmpCode,\n" +
            "   ob.distrCode,\n" +
            "   ob.invoiceNo,\n" +
            "   ob.invoiceQty,\n" +
            "   ob.receivedQty,\n" +
            "   ob.uomCode,\n" +
            "   ob.invoiceDt,\n" +
            "   ob.grnStatus,\n" +
            "   ob.grnDt,\n" +
            "   ob.ProdBatchCode,\n" +
            "   ob.lineLevelDiscAmt,\n" +
            "   ob.lineLevelTaxAmt,\n" +
            "   (select sum(lineLevelNetAmt) from t_purchaseinvoice where invoiceNo = ?) as orderValue\n" +
            "FROM\n" +
            "      t_purchaseinvoice ob \n" +
            "   LEFT JOIN\n" +
            "   m_product_master p \n" +
            "   on p.prodCode = ob.prodCode\n" +
            "WHERE\n" +
            "   ob.invoiceNo = ? AND \n" +
            "ob.cmpCode = ? AND ob.distrCode = ? " +
            "AND uploadFlag = ?";

    /* getRouteName */
    public static final String QUERY_SELECT_ROUTE_NAME = " SELECT  * FROM " + TABLE_ROUTE + " WHERE " + COLUMN_CMP_CODE + "=? AND " + COLUMN_DISTR_CODE + "=? AND "
            + COLUMN_ROUTE_CODE + " =? ";


    /* fetchProductOrderBrands */
    @NonNull
    public static String getQueryProductOrderBrands(String tableName) {
        return "SELECT Distinct brandCode, brandName FROM " + tableName + " p " +
                "where cmpCode=? and cmpCode=? Order By brandName ASC";
    }


    /* fetchBrands */
    @NonNull
    public static String getQueryForBrand(String salesmanCode, String tableName) {
        String brandQuery;
        if (salesmanCode.isEmpty()) {
            brandQuery = "SELECT Distinct brandCode, brandName FROM " + tableName + " p " +
                    "where DistrCode=? Order By brandName ASC";
        } else {
            brandQuery = "SELECT Distinct brandCode, brandName FROM " + tableName + " p " +
                    "where DistrCode=? and SalesmanCode=? Order By brandName ASC";
        }
        return brandQuery;
    }


    /* fetchVanSalesBrands */
    @NonNull
    public static String getVanSalesBrandQuery(String tableName) {
        return "SELECT Distinct p.brandName, p.brandCode FROM " + tableName + " p " +
                "INNER JOIN " + TABLE_LOADING_STOCK + " ls ON ls.ProdCode = p.ProdCode AND ls.BatchCode=p.BatchCode " +
                "where p.DistrCode=? and p.SalesmanCode=? Order By p.brandName ASC";
    }


    /* getConversionFactor */
    @NonNull
    public static String getQueryUOMConversionFactor(String tableName) {
        String tableUomMaster = TABLE_UOM_MASTER;
        if (tableName.equalsIgnoreCase(TABLE_VANSALES_PRODUCTS) || tableName.equalsIgnoreCase(TABLE_VANSALES_UOM_MASTER)) {
            tableUomMaster = TABLE_VANSALES_UOM_MASTER;
        } else if (tableName.equalsIgnoreCase(TABLE_PRODUCT_UOM_MASTER)) {
            tableUomMaster = TABLE_PRODUCT_UOM_MASTER;
        }
        return "select uomCode,conversionFactor from " + tableUomMaster + " where prodCode = ?";
    }


    /* getUOMFromDB for product*/
    @NonNull
    public static String getQueryUomForProd(String tableName) {
        return "SELECT Distinct uomCode, uomDescription, conversionFactor FROM "
                + tableName + " where prodCode = ?";
    }


    /* getUOMForProd */
    public static String getQueryUOMCodeListProduct(String prodCode, String tableName) {
        String tableUomMaster = TABLE_PRODUCT_UOM_MASTER;
        if (tableName.equalsIgnoreCase(TABLE_VANSALES_PRODUCTS)) {
            tableUomMaster = TABLE_VANSALES_UOM_MASTER;
        }
        return "SELECT Distinct  uomCode, uomDescription, conversionFactor FROM " +
                tableUomMaster + " where prodCode = '" + prodCode + "'";
    }

    /* getUOMForCombiProduct */
    public static String getQueryUOMCodeListCombiProduct(String prodCode, String tableName) {
        String tableUomMaster = TABLE_PRODUCT_UOM_MASTER;
        String tableProductMaster = TABLE_PRODUCT_MASTER;
        return "SELECT Distinct  uomCode, uomDescription, conversionFactor  from " + tableUomMaster + " \n" +
                "where prodCode in (select prodCode from " + tableProductMaster + " where productHierPath like '%" + prodCode + "%' )";

    }


    @NonNull
    public static String getQueryToCheckProductAdded(String screenType) {
        String query;
//        if (screenType.equalsIgnoreCase(Globals.NAME_ORDER_BOOKING)) {
            query = "SELECT * FROM " + TABLE_ORDER_BOOKING + " WHERE cmpCode=? " +
                    "AND distrCode=? AND RetlrCode=? AND invoiceNo=? AND ProdCode=? AND BatchCode=?";
//        } else {
//            query = "SELECT * FROM " + TABLE_ORDER_CONFIRMATION + " WHERE distrCode=? " +
//                    "AND salesmanCode=? AND routeCode=? AND RetlrCode=? AND ProdCode=? AND BatchCode=?";
//        }
        return query;
    }

    /* fetchInvoiceNo */
    @NonNull
    public static String getQueryInvoiceNumber(String tableNamme) {
        String query;
        if (tableNamme.equalsIgnoreCase(TABLE_CASH_COLLECTION) ||
                tableNamme.equalsIgnoreCase(TABLE_CHEQUE_COLLECTION) ||
                tableNamme.equalsIgnoreCase(TABLE_BILLING_COLLECTION)) {

            query = "SELECT invoiceNo FROM " + tableNamme + " WHERE distrCode =? AND salesmanCode=? AND routeCode=? AND customerCode=? AND upload=?";

        } else if (tableNamme.equalsIgnoreCase(TABLE_LOADING_STOCK)) {

            query = "SELECT invoiceNo FROM " + tableNamme + " WHERE distrCode =? AND salesmanCode=? AND upload=?";


        } else if (tableNamme.equalsIgnoreCase(TABLE_PURCHASE_ORDER_BOOKING) ) {

            query = "SELECT invoiceNo FROM " + tableNamme + " WHERE distrCode =? AND confirm_status=? AND upload=?";

        }  else if (tableNamme.equalsIgnoreCase(TABLE_PURCHASE_RECEIPT_CONFIRMATION)) {

                query = "SELECT invoiceNo FROM " + tableNamme + " WHERE distrCode =? AND upload=?";


        } else if (tableNamme.equalsIgnoreCase(TABLE_OPENING_STOCK)) {

            query = "SELECT invoiceNo FROM " + tableNamme + " WHERE distrCode =? AND cmpcode=? AND upload=?";

        } else if (tableNamme.equalsIgnoreCase(TABLE_BILLING)) {

            query = "SELECT invoiceNo FROM " + tableNamme + " WHERE distrCode =? AND salesmanCode=? AND routeCode=? AND RetlrCode=? AND upload=? AND completeFlag =?";

        } else if (tableNamme.equalsIgnoreCase(TABLE_COLLECTION)) {

            query = "SELECT collectionNo FROM " + tableNamme + " WHERE distrCode =? AND salesmanCode=? AND routeCode=? AND customerCode=? AND uploadFlag=?";

        } else {

            query = "SELECT invoiceNo FROM " + tableNamme + " WHERE cmpCode = ? distrCode =? AND routeCode=? AND RetlrCode=? AND upload=?";

        }
        return query;
    }

    /* fetchMustsellProducts */
    @NonNull
    public static String getQueryMustSellProducts(String tableName) {
        return "SELECT r.channelcode,\n"
                + "       r.subchannelcode,\n" +
                "       r.groupcode,\n" +
                "       r.classcode,\n" +
                "       msp.prodcode\n" +
                "FROM   " + TABLE_RETAILER + " r\n" +
                "       INNER JOIN " + TABLE_MUSTSELL_RETAILERS + " msr\n" +
                "               ON r.cmpcode = msr.cmpcode\n" +
                "                  AND r.distrcode = msr.distrcode\n" +
                "                  AND r.channelcode = msr.channelcode\n" +
                "                  AND r.subchannelcode = msr.subchannelcode\n" +
                "                  AND r.groupcode = msr.groupcode\n" +
                "                  AND r.classcode = msr.classcode\n" +
                "       INNER JOIN " + TABLE_MUSTSELL_SKU_PRODUCTS + " msp\n" +
                "               ON msr.cmpcode = msp.cmpcode\n" +
                "                  AND msr.distrcode = msp.distrcode\n" +
                "                  AND msr.refno = msp.refno\n" +
                "       INNER JOIN " + tableName + " p\n" +
                "               ON p.cmpcode = msp.cmpcode\n" +
                "                  AND p.distrcode = msp.distrcode\n" +
                "                   AND p.productHierPath like ( '%/' || msp.prodcode ||  '/%' )" +
                "WHERE  r.customercode =?";
    }

    /* fetchFocusBrandProducts  */
    @NonNull
    public static String getQueryFocusBrandProducts(String tableName) {
        return "SELECT r.channelcode,\n"
                + "       r.subchannelcode,\n" +
                "       r.groupcode,\n" +
                "       r.classcode,\n" +
                "       msp.prodcode\n" +
                "FROM   " + TABLE_RETAILER + " r\n" +
                "       INNER JOIN " + TABLE_FOCUSBRAND_RETAILERS + " msr\n" +
                "               ON r.cmpcode = msr.cmpcode\n" +
                "                  AND r.distrcode = msr.distrcode\n" +
                "                  AND r.channelcode = msr.channelcode\n" +
                "                  AND r.subchannelcode = msr.subchannelcode\n" +
                "                  AND r.groupcode = msr.groupcode\n" +
                "                  AND r.classcode = msr.classcode\n" +
                "       INNER JOIN " + TABLE_FOCUSBRAND_PRODUCTS + " msp\n" +
                "               ON msr.cmpcode = msp.cmpcode\n" +
                "                  AND msr.distrcode = msp.distrcode\n" +
                "                  AND msr.refno = msp.refno\n" +
                "       INNER JOIN " + tableName + " p\n" +
                "               ON p.cmpcode = msp.cmpcode\n" +
                "                  AND p.distrcode = msp.distrcode\n" +
                "                   AND p.productHierPath like ( '%/' || msp.prodcode ||  '/%' )" +
                "WHERE  r.customercode =?;";
    }


    /* getSalReturnWithRefInvoiceNo */
    public static String getQuerySalesReturnWithRefInvoiceNo(String tableName, String referenceNo, boolean state) {
        String query = null;
        if (state) {

            if (referenceNo != null && !referenceNo.isEmpty()) {
                query = "SELECT invoiceNo FROM " + tableName + " WHERE distrCode =? AND salesmanCode=? AND routeCode=? AND RetlrCode=? AND SalesReturnRefNo=? AND upload=?";
            } else {
                query = "SELECT invoiceNo FROM " + tableName + " WHERE distrCode =? AND salesmanCode=? AND routeCode=? AND RetlrCode=? AND SalesReturnRefNo='' AND upload=?";
            }
        } else {
            if (tableName.equalsIgnoreCase(TABLE_PURCHASE_PARTIAL_RETURN) && referenceNo != null && !referenceNo.isEmpty()) {
                query = "SELECT invoiceNo FROM " + tableName + " WHERE distrCode =? AND SalesReturnRefNo=? AND upload=?";
            } else if (tableName.equalsIgnoreCase(TABLE_PURCHASE_PARTIAL_RETURN) &&
                    referenceNo != null && referenceNo.isEmpty() &&
                    referenceNo.equalsIgnoreCase("")) {
                query = "SELECT invoiceNo FROM " + tableName + " WHERE distrCode =? AND SalesReturnRefNo='' AND upload=?";

            }
        }
        return query;
    }

    public static final String QUERY_SELECT_ALL_PRODUCT = "SELECT p.prodcode,\n" +
            "       pbm.prodbatchcode,\n" +
            "       p.prodname,\n" +
            "       p.prodshortname,\n" +
            "       p.producthierpathname,\n" +
            "       p.prodhierlastvalcode,\n" +
            "       p.productHierPathCode,\n" +
            "       p.lobDivisionCode,\n" +
            "       p.lobSubDivisionCode,\n" +
            "       pbm.sellprice,\n" +
            "       pbm.purchaseprice,\n" +
            "       pbm.mrp,\n" +
            "       p.prodnetwgt,\n" +
            "       p.prodwgttype,\n" +
            "       st.saleableQty,\n" +
            "       um.uomcode AS baseUom,\n" +
            "       st.availQty AS loadstock,\n" +
            "\t   (select conversionFactor from m_product_uom_master pum where pum.prodCode = p.prodCode) as conversionFactor,\n" +
            "       CASE\n" +
            "           WHEN um.uomCode IS NOT NULL\n" +
            "                AND um.uomCode IN ('GM',\n" +
            "                                 'KG',\n" +
            "                                 'ML',\n" +
            "                                 'LT') THEN um.uomCode\n" +
            "           WHEN um.uomCode IS NOT NULL THEN um.uomCode\n" +
            "           ELSE um.uomCode\n" +
            "       END AS uomCode\n" +
            "FROM m_product_master p\n" +
            "INNER JOIN m_product_batch_master pbm ON pbm.cmpcode = p.cmpcode\n" +
            "AND pbm.prodcode = p.prodcode\n" +
            "LEFT JOIN m_product_uom_master um ON um.cmpcode = p.cmpcode\n" +
            "AND um.prodcode = p.prodcode\n" +
            "AND um.baseuom = 'Y'\n" +
            "AND um.defaultUOM = 'Y'\n" +
            "LEFT JOIN m_stockonhand st ON st.cmpcode = p.cmpcode COLLATE nocase \n" +
            "AND st.prodcode = p.prodcode COLLATE nocase\n" +
            "AND st.ProdBatchCode = pbm.ProdBatchCode COLLATE nocase " +
            "where p.cmpCode = ? and st.availQty>0";

    /* Update Check condition for distributor code ,salesman code,route code, customer code.     */
    public static final String UPDATE_CHECK_CMP_DC_RC_CC = "cmpCode = ? and distrCode = ? and routeCode = ? and customerCode = ?";

    public static final String QUERY_SELECT_PENDING_AMOUNT = "select invoiceNo, invoiceDt, balanceOS from m_pendingBills where cmpCode = ? and distrCode = ? and salesmanCode = ? and customerCode = ?";

    public static final String QUERY_SELECT_COLLECTION = "SELECT * FROM '" + TABLE_COLLECTION + "' WHERE distrCode =?  AND salesmanCode =? AND routeCode =? AND customerCode = ? AND uploadFlag = 'N'";

    /* getExistingReturns */
    public static final String QUERY_SELECT_EXISTING_RETURNS = "SELECT DISTINCT sh.customerCode,\n" +
            "                sd.prodCode,\n" +
            "                b.ProdBatchCode,\n" +
            "                sd.returnQty,\n" +
            "                sd.returnAmt,\n" +
            "                p.hsnCode,\n" +
            "                p.ProdShortName,\n" +
            "                sd.image,\n" +
            "                sd.prodName,\n" +
            "                sd.ReasonCode,\n" +
            "                sd.ReasonName,\n" +
            "                sd.mrp,\n" +
            "                sd.stockType,\n" +
            "                sd.batchDetail,\n" +
            "                sd.salesReturnNo,\n" +
            "                sh.remarks,\n" +
            "                b.Sellprice\n" +
            "FROM " + TABLE_SALES_RETURN_HEADER + " sh\n" +
            "INNER JOIN " + TABLE_SALES_RETURN_DETAILS + " sd ON sh.salesReturnNo = sd.salesReturnNo\n" +
            "INNER JOIN " + TABLE_PRODUCT_MASTER + " p ON sd.prodCode=p.prodCode\n" +
            "INNER JOIN " + TABLE_PRODUCT_BATCH_MASTER + " b ON p.prodCode=b.prodCode\n" +
            "AND sd.prodbatchCode = b.prodbatchCode\n" +
            "LEFT JOIN " + TABLE_REASONS + " r ON sd.reasonCode=r.reasonCode\n" +
            "WHERE sh.distrCode = ?\n" +
            "  AND sh.salesmanCode = ?\n" +
            "  AND sh.routeCode = ?\n" +
            "  AND sh.customerCode = ?\n" +
            "  AND sh.uploadFlag = ?";

    /* getAppProductsSales */
    public static final String QUERY_SELECT_APP_PRODUCTS = "SELECT b.ProdBatchCode, p.prodCode, p.prodName, p.ProdShortName, b.SellPrice FROM " + TABLE_PRODUCT_MASTER + " p INNER JOIN " + TABLE_PRODUCT_BATCH_MASTER + " b ON p.prodCode=b.prodCode WHERE p.cmpCode=? group by p.prodCode";

    public static final String QUERY_SELECT_PRODUCT_BATCH_CODE = "SELECT ProdBatchCode, prodCode, MRP, SellPrice, sellRateWithTax FROM " + TABLE_PRODUCT_BATCH_MASTER + " WHERE cmpCode=? AND prodCode=?";

    /* getSalesReturns */
    public static final String QUERY_SELECT_SALES_RETURNS = "SELECT DISTINCT sh.customerCode,\n" +
            "                sd.prodCode,\n" +
            "                b.ProdBatchCode,\n" +
            "                sd.returnQty,\n" +
            "                sd.returnAmt,\n" +
            "                p.hsnCode,\n" +
            "                p.ProdShortName,\n" +
            "                sd.image,\n" +
            "                sd.prodName,\n" +
            "                sd.ReasonCode,\n" +
            "                sd.ReasonName,\n" +
            "                sd.mrp,\n" +
            "                sd.stockType,\n" +
            "                sd.batchDetail,\n" +
            "                sd.salesReturnNo,\n" +
            "                sh.remarks,\n" +
            "                b.Sellprice\n" +
            "FROM " + TABLE_SALES_RETURN_HEADER + " sh\n" +
            "INNER JOIN " + TABLE_SALES_RETURN_DETAILS + " sd ON sh.salesReturnNo = sd.salesReturnNo\n" +
            "INNER JOIN " + TABLE_PRODUCT_MASTER + " p ON sd.prodCode=p.prodCode\n" +
            "INNER JOIN " + TABLE_PRODUCT_BATCH_MASTER + " b ON p.prodCode=b.prodCode\n" +
            "AND sd.prodbatchCode = b.prodbatchCode\n" +
            "LEFT JOIN " + TABLE_REASONS + " r ON sd.reasonCode=r.reasonCode\n" +
            "WHERE sh.distrCode = ?\n" +
            "  AND sh.salesmanCode = ?\n" +
            "  AND sh.routeCode = ?\n" +
            "  AND sh.customerCode = ?";


    public static final String QUERY_SELECT_PREVIOUS_ORDER_QTY_PRODUCTS = " SELECT ob.batchCode , \n" +
            "               ob.prodCode, \n" +
            "               ob.conversionFactor, \n" +
            "               st.availQty \n" +
            "FROM   " + TABLE_LOADING_STOCK + " st \n" +
            "       LEFT JOIN " + TABLE_ORDER_BOOKING + " ob \n" +
            "              ON ob.prodcode = st.prodcode \n" +
            "                 AND ob.batchcode = st.batchcode \n" +
            "                 AND ob.distrcode = st.distrcode \n" +
            "                 AND ob.salesmancode = st.salesmancode \n" +
            "WHERE  ob.distrcode = ? \n" +
            "       AND ob.salesmancode = ? \n" +
            "       AND ob.retlrcode = ? \n" +
            "       AND ob.invoiceno = ? \n" +
            "       AND ob.completeflag = 'Y' \n" +
            "       AND ob.upload = 'N'";
}
