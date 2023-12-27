/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.db.query.IDBColumns;
import com.botree.retailerssfa.db.query.OrderBookingSchemeHelper;
import com.botree.retailerssfa.models.BillingInfoVO;
import com.botree.retailerssfa.models.CollectionVO;
import com.botree.retailerssfa.models.EditQtyModel;
import com.botree.retailerssfa.models.OrderBookingDetailsModel;
import com.botree.retailerssfa.models.OrderBookingHeaderModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.PendingBillCollection;
import com.botree.retailerssfa.models.PendingCollection;
import com.botree.retailerssfa.models.ReasonVO;
import com.botree.retailerssfa.models.SalesDBCRNoteModel;
import com.botree.retailerssfa.models.SalesReturnVO;
import com.botree.retailerssfa.models.SchemeDistrBudgetModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.models.StockTakeVO;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.botree.retailerssfa.db.BaseDB.checkDouble;
import static com.botree.retailerssfa.db.BaseDB.checkString;
import static com.botree.retailerssfa.db.query.IDBColumns.*;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_CHECK_BILLING_TRACKER;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_CHECK_IS_OPENING_STOCK_PROD_ADDED;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_CHECK_IS_STOCK_PRODUCT_ADDED;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_CHECK_ORDER_BOOKING_TRACKER;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_IS_PURCHASE_ORDER_ADDED;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_ORDERED_PRODUCT;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_PURCHASE_ORDER_SUMMARY;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_ALL_BILLING_PRODUCTS;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_ALL_DISTR_PURCHASE_PRODUCT;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_ALL_OPENING_STOCK_PRODUCT;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_ALL_PRODUCT;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_ALL_PRODUCTS;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_ALL_RECEIPT_PRODUCTS;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_ALL_RETURN_PRODUCT;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_ALL_STOCK_TAKE_PRODUCTS;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_APP_PRODUCTS;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_BANK_DETAILS;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_CASH_COLLECTION;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_CATEGORY;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_CHEQUE_COLLECTION;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_COLLECTION;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_COLLECTION_AMOUNT;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_EXISTING_PURCHASE_RETURN;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_EXISTING_RETURNS;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_OPENING_STOCK_SUMMARY_PRODUCT;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_OPENING_STOCK_UOM;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_PENDING_AMOUNT;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_PREVIOUS_ORDER_DETAILS;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_PREVIOUS_ORDER_QTY_PRODUCTS;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_PRODUCT_BATCH_CODE;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_REASON;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_ROUTE_NAME;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_SALES_RETURNS;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_STOCK_SUMMARY_DATA;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_STOCK_TAKE_PRODUCT;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_STOCK_TAKE_PRODUCT_ONLY_WITH_QUANTITY;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.QUERY_SELECT_TAX_MASTER_DATA;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.UPDATE_CHECK_DC_SC_RC_CC;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.UPDATE_CHECK_DC_SC_RC_RTLC_PC_U;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.getQueryFocusBrandProducts;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.getQueryForBrand;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.getQueryInvoiceNumber;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.getQueryMustSellProducts;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.getQueryProductOrderBrands;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.getQuerySalesReturnWithRefInvoiceNo;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.getQueryToCheckProductAdded;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.getQueryUOMCodeListProduct;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.getQueryUOMConversionFactor;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.getQueryUomForProd;
import static com.botree.retailerssfa.db.query.OrderBookingQueryHelper.getVanSalesBrandQuery;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;


class OrderBookingHelper extends OrderBookingSchemeHelper {

    private static final String TAG = OrderBookingHelper.class.getSimpleName();
    private static final String APPEND_AND = " = ? and ";
    private final String salesmanCode = "";
    private final String distrCode;
    private final String cmpCode;

    OrderBookingHelper() {
        SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
        distrCode = sfaSharedPref.readString(PREF_DISTRCODE);
        cmpCode = sfaSharedPref.readString(SFASharedPref.PREF_CMP_CODE);
    }

    /**
     * Get all the products from products master UOM master order master based and scheme master on params
     *
     * @param sfaDatabase db instance to read data from the database
     * @param distcode    device configure distributor code
     * @return all products
     */
    List<OrderBookingVO> getAllDistPurchaseProducts(SFADatabase sfaDatabase, String distcode) {

        List<OrderBookingVO> orderBookingList = new ArrayList<>();
        Cursor c = null;
        try {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ALL_DISTR_PURCHASE_PRODUCT, new String[]{distcode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setProdBatchCode(checkString(c, COLUMN_PROD_BATCH_CODE).trim());
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim());
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)).trim());
                    orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)).trim());
                    orderBookingVO.setSellPrice(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS)), "0")));
                    orderBookingVO.setMrp(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS)), "0")));
                    orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));
                    orderBookingVO.setProdHierValName(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                    orderBookingVO.setPurchasePrice(c.getDouble(c.getColumnIndex(COLUMN_PURCHASE_PRICE)));
                    orderBookingVO.setWeightType(c.getString(c.getColumnIndex(COLUMN_PRODUCT_WGT_TYPE)));
                    orderBookingVO.setNetWeight(c.getDouble(c.getColumnIndex(COLUMN_PRODUCT_NET_WGT)));
                    orderBookingVO.setUomGroupId(Globals.checkNull(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim()));
                    orderBookingVO.setUomId(Globals.checkNull(c.getString(c.getColumnIndex(COLUMN_UOM_ID))));
                    orderBookingVO.setProductHierPath(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
//                    orderBookingVO.setBrandName(c.getString(c.getColumnIndex(COLUMN_BRAND_NAME)));
//                    orderBookingVO.setBrandCode(c.getString(c.getColumnIndex(COLUMN_BRAND_CODE)));
//                    orderBookingVO.setLobDivisionCode(c.getString(c.getColumnIndex(COLUMN_PROD_LOB_DIVISTION_CODE)));
//                    orderBookingVO.setLobSubDivisionCode(c.getString(c.getColumnIndex(COLUMN_PROD_LOB_SUB_DIVISION_CODE)));
//                    orderBookingVO.setProdHierLastValCode(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));

                    orderBookingVO.setBaseUOM(c.getString(c.getColumnIndex(COLUMN_BASE_UOM)));
                    orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));

                    orderBookingVO.setStockInHand(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));

//                    if (c.getString(c.getColumnIndex(COLUMN_STOCK)) != null
//                            && !c.getString(c.getColumnIndex(COLUMN_STOCK)).isEmpty()) {
//                        orderBookingVO.setIsStockAvail(c.getString(c.getColumnIndex(COLUMN_STOCK)));
//                    } else {
//                        orderBookingVO.setIsStockAvail("N");
//                    }

                    if (null != c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS)) && !c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS)).equals("")) {
                        orderBookingVO.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                    }

                    orderBookingVO.setConversionFactor(getConversionFactor(sfaDatabase, orderBookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));
                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }
        return orderBookingList;
    }

    private void closeCursor(Cursor c) {
        if (c != null && !c.isClosed()) {
            c.close();
        }
    }

    /**
     * Get all the products from products master UOM master order master based and scheme master on params
     *
     * @param sfaDatabase  db instance to read data from the database
     * @param distcode     device configure distributor code
     * @param salesmancode dist mapped salesman code
     * @param routecode    in which route the retailer is mapped
     * @param retrCode     retailer id to map with all the orders
     * @return all products
     */
    List<OrderBookingVO> getAllProducts(SFADatabase sfaDatabase, String distcode, String salesmancode, String routecode, String retrCode) {

        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        Cursor c = null;

        SQLiteDatabase database = sfaDatabase.getReadableDatabase();

        try {
            c = database.rawQuery(QUERY_SELECT_ALL_PRODUCTS, new String[]{});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)).trim());
                    orderBookingVO.setProductHierPath(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                    orderBookingVO.setProductHierPathName(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_NAME)));
                    orderBookingVO.setSellPrice(new BigDecimal(checkDouble(c, COLUMN_SELL_PRICE_CAPS)));
                    orderBookingVO.setActualSellRate(new BigDecimal(checkDouble(c, COLUMN_ACTUAL_SELL_RATE)));
                    orderBookingVO.setMrp(new BigDecimal(checkDouble(c, COLUMN_MRP_CAPS)));
                    orderBookingVO.setNetWeight(c.getDouble(c.getColumnIndex(COLUMN_PRODUCT_NET_WGT)));
                    orderBookingVO.setWeightType(c.getString(c.getColumnIndex(COLUMN_PRODUCT_WGT_TYPE)));
                    orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));
                    orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)));
                    orderBookingVO.setNetAmount(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(NET_AMOUNT)), "0")));

                    orderBookingVO.setSchemeAmount(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(SCHEME_AMOUNT)), "0")));

                    orderBookingVO.setCgstvalue(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(CGST_VALUE)), "0")));
                    orderBookingVO.setCgstperc(c.getDouble(c.getColumnIndex(CGST_PERCENT)));
                    orderBookingVO.setSgstPerc(c.getDouble(c.getColumnIndex(SGST_PERCENT)));
                    orderBookingVO.setSgstValue(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(SGST_VALUE)), "0")));
                    orderBookingVO.setUtgstPerc(c.getDouble(c.getColumnIndex(UTGST_PERCENT)));
                    orderBookingVO.setUtgstValue(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(UTGST_VALUE)), "0")));
                    orderBookingVO.setIgstPerc(c.getDouble(c.getColumnIndex(IGST_PERCENT)));
                    orderBookingVO.setIgstvalue(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(IGST_VALUE)), "0")));

                    orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_DEFAULT_UOMID_CAPS)));
                    orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
                    orderBookingVO.setBaseUOM(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setSuggestedQty(c.getInt(c.getColumnIndex(SUGGESTED_QUANTITY)));
                    orderBookingVO.setPpq(c.getString(c.getColumnIndex(COLUMN_PPQ)));
                    orderBookingVO.setStockInHand(c.getInt(c.getColumnIndex("stockinhand")));
                    orderBookingVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    orderBookingVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    orderBookingVO.setRetailerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                    orderBookingVO.setAddress(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_SHIP_CODE)));
                    orderBookingVO.setDistrStateCode(c.getString(c.getColumnIndex(COLUMN_DISTR_STATE_CODE)));
                    orderBookingVO.setRetailerStateCode(c.getString(c.getColumnIndex(COLUMN_RETAILER_STATE_CODE)));
                    orderBookingVO.setReadableInvNo(c.getString(c.getColumnIndex(READABLE_INV_NO)));

                    if (null != c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS)) && !c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS)).equals("")) {
                        orderBookingVO.setOrderValue(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS)), "0")));
                    }
                    if (null != c.getString(c.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE)) && !c.getString(c.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE)).equals("")) {
                        orderBookingVO.setPrimDiscOrderValue(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE)), "0")));
                    }
                    orderBookingVO.setConversionFactor(getConversionFactor(sfaDatabase, orderBookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));

                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }
        return orderBookingList;
    }

    List<String> fetchAllOrderNos(SFADatabase sfaDatabase) {

        List<String> invoiceList = new ArrayList<>();

        Cursor c = null;

        String query = "select distinct(invoiceNo) from " + TABLE_ORDER_BOOKING + " where upload = 'N'";
        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        try {
            c = database.rawQuery(query, new String[]{});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    invoiceList.add(c.getString(c.getColumnIndex(COLUMN_INVOICE_NO)));
                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }
        return invoiceList;
    }

    /**
     * Get all the products from products master UOM master order master based and scheme master on params
     *
     * @param sfaDatabase  db instance to read data from the database
     * @param distcode     device configure distributor code
     * @param salesmancode dist mapped salesman code
     * @param routecode    in which route the retailer is mapped
     * @param retrCode     retailer id to map with all the orders
     * @return all billing products
     */
    List<OrderBookingVO> getAllBillingProducts(SFADatabase sfaDatabase, String distcode, String salesmancode, String routecode, String retrCode) {

        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(QUERY_SELECT_ALL_BILLING_PRODUCTS, new String[]{routecode, retrCode, routecode, retrCode, distcode, salesmancode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_BATCH_CODE)).trim());
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)).trim());
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME_CAPS)).trim());
                    orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)).trim());
                    orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                    orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                    orderBookingVO.setPrimaryDisc((c.getDouble(c.getColumnIndex(COLUMN_PRIMARY_DISC))));
                    orderBookingVO.setNetWeight(c.getDouble(c.getColumnIndex(COLUMN_PRODUCT_NET_WGT)));
                    orderBookingVO.setWeightType(c.getString(c.getColumnIndex(COLUMN_PRODUCT_WGT_TYPE)));
                    orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));
                    orderBookingVO.setOrderFreeQty(c.getInt(c.getColumnIndex(FREE_QTY)));
                    orderBookingVO.setProdHierValName(c.getString(c.getColumnIndex(COLUMN_PRODHIER_VAL_NAME_CAPS)));
                    orderBookingVO.setProductHierPath(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH)));
                    orderBookingVO.setProductHierPathName(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_NAME)));
                    orderBookingVO.setBrandName(c.getString(c.getColumnIndex(COLUMN_BRAND_NAME)));
                    orderBookingVO.setBrandCode(c.getString(c.getColumnIndex(COLUMN_BRAND_CODE)));
                    orderBookingVO.setStockInHand(c.getInt(c.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));
                    orderBookingVO.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));
                    orderBookingVO.setStockCheckQty(c.getDouble(c.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));
                    orderBookingVO.setCategory(c.getString(c.getColumnIndex(CATEGORY_CAPS)));
                    orderBookingVO.setUomGroupId(c.getString(c.getColumnIndex(COLUMN_UOM_GROUPID_CAPS)));
                    orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)));
                    orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_DEFAULT_UOMID_CAPS)));
                    orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
                    orderBookingVO.setBaseUOM(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));

                    if (null != c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS)) && !c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS)).equals("")) {
                        orderBookingVO.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                    }
                    if (null != c.getString(c.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE)) && !c.getString(c.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE)).equals("")) {
                        orderBookingVO.setPrimDiscOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE))));
                    }
                    orderBookingVO.setConversionFactor(getConversionFactor(sfaDatabase, orderBookingVO.getProdCode(), TABLE_VANSALES_UOM_MASTER));

                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }

        return orderBookingList;
    }

    /**
     * Get all the products from products master UOM master order master based and scheme master on params
     *
     * @param sfaDatabase db instance to read data from the database
     * @param distcode    device configure distributor code
     * @return all billing products
     */
    List<OrderBookingVO> getStockSummaryData(SFADatabase sfaDatabase, String distcode, String salesmanCode) {

        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(QUERY_SELECT_STOCK_SUMMARY_DATA, new String[]{distcode, salesmanCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_BATCH_CODE)));
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)));
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME_CAPS)));
                    orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
                    orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                    orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                    orderBookingVO.setStockInHand(c.getInt(c.getColumnIndex(COLUMN_STOCK_IN_HAND_CAPS)));
                    orderBookingVO.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                    orderBookingVO.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }

        return orderBookingList;
    }

    /**
     * Get all the products from products master UOM master order master based and scheme master on params
     *
     * @param sfaDatabase db instance to read data from the database
     * @return all taxmaster data
     */
    List<OrderBookingVO> getTaxMaster(SFADatabase sfaDatabase) {

        List<OrderBookingVO> taxMasterList = new ArrayList<>();

        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(QUERY_SELECT_TAX_MASTER_DATA, null);

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    orderBookingVO.setCgstperc(c.getDouble(c.getColumnIndex(COLUMN_CGST)));
                    orderBookingVO.setSgstPerc(c.getDouble(c.getColumnIndex(COLUMN_SGST)));
                    orderBookingVO.setIgstPerc(c.getDouble(c.getColumnIndex(COLUMN_IGST)));
                    orderBookingVO.setUtgstPerc(c.getDouble(c.getColumnIndex(COLUMN_CESS)));
                    taxMasterList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }

        return taxMasterList;
    }


    /**
     * Get all the brands details from database based on below params
     *
     * @param baseDB    db instance to read data from db
     * @param distrCode device configured distributor
     * @param cmpCode   mapped with distributor
     * @param tableName calling tableName
     * @return list of brands data
     */
    List<OrderBookingVO> fetchProductOrderBrands(BaseDB baseDB, String distrCode, String cmpCode, String tableName) {
        List<OrderBookingVO> brandsList = new ArrayList<>();
        SQLiteDatabase database = baseDB.getReadableDatabase();
        String brandQuery = getQueryProductOrderBrands(tableName);
        Cursor c = database.rawQuery(brandQuery, new String[]{distrCode, cmpCode});

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setBrandName(c.getString(c.getColumnIndex(IDBColumns.COLUMN_BRAND_NAME)).trim());
                orderBookingVO.setBrandCode(c.getString(c.getColumnIndex(IDBColumns.COLUMN_BRAND_CODE)).trim());
                brandsList.add(orderBookingVO);
            }
            c.close();
        }

        return brandsList;
    }

    /**
     * Get all the brands details from database based on below params
     *
     * @param baseDB       db instance to read data from db
     * @param distrCode    device configured distributor
     * @param salesmanCode mapped with distributor
     * @param tableName    calling tableName
     * @return list of brands data
     */
    List<OrderBookingVO> fetchBrands(BaseDB baseDB, String distrCode, String salesmanCode, String tableName) {
        List<OrderBookingVO> brandsList = new ArrayList<>();
        SQLiteDatabase database = baseDB.getReadableDatabase();
        String brandQuery;
        Cursor c;
        brandQuery = getQueryForBrand(salesmanCode, tableName);
        if (salesmanCode.isEmpty()) {
            c = database.rawQuery(brandQuery, new String[]{distrCode});
        } else {
            c = database.rawQuery(brandQuery, new String[]{distrCode, salesmanCode});
        }


        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setBrandName(c.getString(c.getColumnIndex(IDBColumns.COLUMN_BRAND_NAME)).trim());
                orderBookingVO.setBrandCode(c.getString(c.getColumnIndex(IDBColumns.COLUMN_BRAND_CODE)).trim());
                brandsList.add(orderBookingVO);
            }
            c.close();
        }
        return brandsList;
    }

    /**
     * Get all the brands details from database based on below params
     *
     * @param baseDB       db instance to read data from db
     * @param distrCode    device configured distributor
     * @param salesmanCode mapped with distributor
     * @param tableName    calling tableName
     * @return list of brands data
     */
    List<OrderBookingVO> fetchVanSalesBrands(BaseDB baseDB, String distrCode, String salesmanCode, String tableName) {
        List<OrderBookingVO> brandsList = new ArrayList<>();
        SQLiteDatabase database = baseDB.getReadableDatabase();
        String brandQuery = getVanSalesBrandQuery(tableName);
        Cursor c = database.rawQuery(brandQuery, new String[]{distrCode, salesmanCode});

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setBrandName(c.getString(c.getColumnIndex(IDBColumns.COLUMN_BRAND_NAME)).trim());
                orderBookingVO.setBrandCode(c.getString(c.getColumnIndex(IDBColumns.COLUMN_BRAND_CODE)).trim());
                brandsList.add(orderBookingVO);
            }
            c.close();
        }

        return brandsList;


    }

    /**
     * Get all the categories data from db
     *
     * @param baseDB db instance to read data from db
     * @return list of category data
     */
    List<String> fetchCategory(BaseDB baseDB) {

        List<String> categoryList = new ArrayList<>();
        SQLiteDatabase database = baseDB.getReadableDatabase();
        Cursor c = database.rawQuery(QUERY_SELECT_CATEGORY, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                categoryList.add(c.getString(c.getColumnIndex(IDBColumns.CATEGORY_CAPS)).trim());
            }
            c.close();
        }
        return categoryList;
    }

    /**
     * Get the conversion factor based on the uom group id
     *
     * @param sfaDatabase db instance to read the data from db
     * @param uomGroupId  get to particular uom group conversion factor
     * @param tableName   have table name
     * @return hash map
     */
    public HashMap<String, Integer> getConversionFactor(SFADatabase sfaDatabase, String uomGroupId, String tableName) {

        HashMap<String, Integer> conversionFactorMap = new HashMap<>();
        Cursor c = null;
        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        try {
            String query = getQueryUOMConversionFactor(tableName);
            c = database.rawQuery(query, new String[]{uomGroupId});

            int uomidx = c.getColumnIndex(COLUMN_UOM_CODE);
            int factoridx = c.getColumnIndex(COLUMN_CONVERSION_FACTOR);
            if (c.getCount() > 0 && c.moveToFirst()) {
                do {
                    conversionFactorMap.put(c.getString(uomidx), Integer.valueOf(c.getString(factoridx)));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "getConversionFactor: " + e.getMessage(), e);
        } finally {
            if (c != null)
                c.close();
        }

        return conversionFactorMap;
    }

    /**
     * Get uom from db based on the uom group id
     *
     * @param sfaDatabase to read the data from table
     * @param uomgid      to get the uom id and uom code fom the db
     * @return uom id and code
     */
    List<OrderBookingVO> getOpeningStockUOMFromDB(SFADatabase sfaDatabase, String uomgid) {

        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_OPENING_STOCK_UOM, new String[]{uomgid, "Y"});
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();

                    orderBookingVO.setUomGroupId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));

                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
            c.close();
        }

        return orderBookingList;
    }

    /**
     * Get uom from db based on the uom group id
     *
     * @param sfaDatabase to read the data from table
     * @param prodCode    to get the uom id and uom code fom the db
     * @param tableName   calling tableName
     * @return uom id and code
     */
    List<OrderBookingVO> getUOMFromDB(SFADatabase sfaDatabase, String prodCode, String tableName) {

        List<OrderBookingVO> orderBookingList = new ArrayList<>();
        String query = getQueryUomForProd(tableName);

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{prodCode});
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();

                    orderBookingVO.setUomGroupId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));

                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
            c.close();
        }

        return orderBookingList;
    }

    /**
     * Get uom from db based on the uom group id
     *
     * @param sfaDatabase to read the data from table
     * @param prodCode    to get the uom id and uom code fom the db
     * @param tableName   calling table name
     * @return uom id for indivisualProd
     */
    List<String> getUOMForProd(SFADatabase sfaDatabase, String prodCode, String tableName) {

        List<String> uomList = new ArrayList<>();
        String query = getQueryUOMCodeListProduct(prodCode, tableName);

        Cursor c = sfaDatabase.getDb().rawQuery(query, null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    uomList.add(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                } while (c.moveToNext());
            }
            c.close();
        }

        return uomList;
    }

    /**
     * Get uom from db based on the uom group id
     *
     * @param sfaDatabase to read the data from table
     * @param prodCode    to get the uom id and uom code fom the db
     * @param tableName   calling table name
     * @return uom id for indivisualProd
     */
    List<String> getUOMForCombiProduct(SFADatabase sfaDatabase, String prodCode, String tableName) {

        List<String> uomList = new ArrayList<>();
        String query = "SELECT Distinct  uomCode, uomDescription, conversionFactor  from m_product_uom_master \n" +
                "    where prodCode='" + prodCode + "'";

        Cursor c = sfaDatabase.getDb().rawQuery(query, null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    uomList.add(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                } while (c.moveToNext());
            }
            c.close();
        }
        return uomList;
    }

    /**
     * Update retailer pending state to completed state after process is complete
     *
     * @param baseDB       access db to write data into table
     * @param distrCode    device mapped distributor
     * @param salesmanCode mapped with the distributor
     * @param routeCode    retailer who is tagged with the particular route
     * @param customerCode order placed retailer
     */
    void updateRetailer(BaseDB baseDB, String distrCode, String salesmanCode,
                        String routeCode, String customerCode) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_VISIT, "C");

        String[] updateArgs = {distrCode, salesmanCode, routeCode, customerCode};

        baseDB.getDb().update(TABLE_RETAILER, values, UPDATE_CHECK_DC_SC_RC_CC, updateArgs);
        baseDB.closeDb();
    }

    /**
     * If the selected retailer finish the order process update the state to "C"
     *
     * @param baseDB       db instance to write the data in table
     * @param distrCode    device id mapped distributor
     * @param salesmanCode mapped with distributor
     * @param routeCode    mapped with the salesman
     * @param customerCode retailer mapped with salesman
     */
    void updateRetailerVisitComplete(BaseDB baseDB, String distrCode, String salesmanCode,
                                     String routeCode, String customerCode) {

        ContentValues values1 = new ContentValues();
        values1.put(COLUMN_IS_VISIT, "C");
        String[] updateArgs = {distrCode, salesmanCode, routeCode, customerCode};

        baseDB.getDb().update(TABLE_RETAILER_VISIT, values1, UPDATE_CHECK_DC_SC_RC_CC, updateArgs);
        baseDB.closeDb();

    }

    void insertBillingTracker(BaseDB baseDB, String routeCode, String customerCode, Long startTime,
                              Long endTime, String upload) {

        SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DISTR_CODE, sfaSharedPref.readString(PREF_DISTRCODE));
        values.put(COLUMN_SALESMAN_CODE, sfaSharedPref.readString(SFASharedPref.PREF_SALESMANCODE));
        values.put(COLUMN_ROUTE_CODE, routeCode);
        values.put(COLUMN_CUSTOMER_CODE, customerCode);
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_END_TIME, endTime);
        values.put(COLUMN_UPLOAD, upload);
        // Inserting Row
        long ack = baseDB.getDb().insert(TABLE_BILLING_TRACKER, null, values);
        baseDB.closeDb();
        if (BuildConfig.DEBUG)
            if (ack > 0) {
                Log.i(TAG, "insertBillingTracker inserted CustomerCode" + customerCode);
            } else {
                Log.i(TAG, "insertBillingTracker failed CustomerCode" + customerCode);
            }
    }


    void insertOrderBookingTracker(BaseDB baseDB, String routeCode, String customerCode, Long startTime,
                                   Long endTime, String upload) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_SALESMAN_CODE, salesmanCode);
        values.put(COLUMN_ROUTE_CODE, routeCode);
        values.put(COLUMN_CUSTOMER_CODE, customerCode);
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_END_TIME, endTime);
        values.put(COLUMN_UPLOAD, upload);
        // Inserting Row
        long ack = baseDB.getDb().insert(TABLE_ORDER_BOOKING_TRACKER, null, values);
        baseDB.closeDb();
        if (BuildConfig.DEBUG)
            if (ack > 0) {
                Log.i(TAG, "insertOrderBookingTracker inserted CustomerCode" + customerCode);
            } else {
                Log.i(TAG, "insertOrderBookingTracker failed CustomerCode" + customerCode);
            }
    }

    /**
     * Update orderbooking tracker table order booking start and end time
     *
     * @param baseDB       db instance to write data in table
     * @param startTime    order starting time
     * @param endTime      order prdcess end time
     * @param distrCode    device mapped distributor
     * @param salesmanCode dist mapped salesman
     * @param routeCode    salesman tagged route
     * @param retlrCode    salesman mapped retailer
     */
    void updateBillingsTracker(BaseDB baseDB, Long startTime, Long endTime,
                               String distrCode, String salesmanCode,
                               String routeCode, String retlrCode) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_END_TIME, endTime);

        String[] updateArgs = {distrCode, salesmanCode, routeCode, retlrCode};

        baseDB.getDb().update(TABLE_BILLING_TRACKER, values, UPDATE_CHECK_DC_SC_RC_CC, updateArgs);
        baseDB.closeDb();
    }

    /**
     * Update orderbooking tracker table order booking start and end time
     *
     * @param baseDB       db instance to write data in table
     * @param startTime    order starting time
     * @param endTime      order prdcess end time
     * @param distrCode    device mapped distributor
     * @param salesmanCode dist mapped salesman
     * @param routeCode    salesman tagged route
     * @param retlrCode    salesman mapped retailer
     */
    void updateOrderBookingTracker(BaseDB baseDB, Long startTime, Long endTime,
                                   String distrCode, String salesmanCode,
                                   String routeCode, String retlrCode) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_END_TIME, endTime);

        String[] updateArgs = {distrCode, salesmanCode, routeCode, retlrCode};

        baseDB.getDb().update(TABLE_ORDER_BOOKING_TRACKER, values, UPDATE_CHECK_DC_SC_RC_CC, updateArgs);
        baseDB.closeDb();
    }

    /**
     * check wheather the retailer took order or not
     *
     * @return true or false
     */
    boolean checkOrderBookingTracker(BaseDB baseDB, String distrCode, String salesmanCode,
                                     String routeCode, String retlrCode) {
        Cursor c = baseDB.getDb().rawQuery(QUERY_CHECK_ORDER_BOOKING_TRACKER, new String[]{distrCode, salesmanCode, retlrCode});
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

    /**
     * check wheather the retailer took order or not
     *
     * @return true or false
     */
    boolean checkBillingsTracker(BaseDB baseDB, String distrCode, String salesmanCode,
                                 String routeCode, String retlrCode) {
        Cursor c = baseDB.getDb().rawQuery(QUERY_CHECK_BILLING_TRACKER, new String[]{distrCode, salesmanCode, routeCode, retlrCode});
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


    /**
     * Insert order booking details in orderbooking table (if product is already is added delete and insert data)
     *
     * @param baseDB          db instance for writing data in the table
     * @param distrCode       device mapped distributor
     * @param date            order date
     * @param orderBookingVOs order details insert in the table
     */
    void insertPurchaseOrderBooking(BaseDB baseDB, String invoiceNo, String cmpCode, String distrCode,
                                    String date, List<OrderBookingVO> orderBookingVOs) {

        try {
            SQLiteDatabase db = baseDB.getDb();
            for (OrderBookingVO bookingVO : orderBookingVOs) {

                if (isPurchaseProductAdded(baseDB, distrCode, invoiceNo, bookingVO.getProdCode(), bookingVO.getProdBatchCode())) {

                    deleteExitePurchaseProductById(baseDB, distrCode, invoiceNo, bookingVO.getProdCode(), bookingVO.getProdBatchCode());

                }

                if (!db.isOpen())
                    db = baseDB.getDb();

                if (bookingVO.getQuantity() > 0) {

                    Double taxAmt = bookingVO.getCgstvalue().doubleValue() + bookingVO.getSgstValue().doubleValue() + bookingVO.getIgstvalue().doubleValue()
                            + bookingVO.getUtgstValue().doubleValue();

                    ContentValues values = new ContentValues();
                    values.put(INVOICE_NO, invoiceNo);
                    values.put(COLUMN_CMP_CODE, cmpCode);
                    values.put(COLUMN_DISTR_CODE, distrCode);
                    values.put(COLUMN_ORDER_DATE_CAPS, date);
                    values.put(COLUMN_BATCH_CODE, bookingVO.getProdBatchCode());
                    values.put(COLUMN_PROD_CODE_CAPS, bookingVO.getProdCode());
                    values.put(COLUMN_ORDER_QTY_CAPS, bookingVO.getQuantity());
                    values.put(COLUMN_MRP_CAPS, bookingVO.getMrp().doubleValue());
                    values.put(IDBColumns.COLUMN_PURCHASE_PRICE, bookingVO.getPurchasePrice());
                    values.put(COLUMN_ORDER_STATUS_CAPS, "i");
                    values.put(IDBColumns.COLUMN_REMARKS_CAPS, bookingVO.getRemarks());
                    values.put(COLUMN_ORDER_VALUE_CAPS, bookingVO.getOrderValue().doubleValue());
                    values.put(COLUMN_UOM_ID, bookingVO.getUomId());
                    values.put(COLUMN_BASE_UOM, bookingVO.getBaseUOM());

                    values.put(SCHEME_AMOUNT, String.valueOf(bookingVO.getSchemeAmount()));
                    values.put(NET_AMOUNT, String.valueOf(bookingVO.getTotalAmount()));
                    values.put(GROSS_AMOUNT, String.valueOf(bookingVO.getNetAmount()));
                    values.put(TAX_PER_PRODUCT, taxAmt);
                    values.put(CGST_PERCENT, String.valueOf(bookingVO.getCgstperc()));
                    values.put(CGST_VALUE, String.valueOf(bookingVO.getCgstvalue()));
                    values.put(SGST_PERCENT, String.valueOf(bookingVO.getSgstPerc()));
                    values.put(SGST_VALUE, String.valueOf(bookingVO.getSgstValue()));
                    values.put(IGST_PERCENT, String.valueOf(bookingVO.getIgstPerc()));
                    values.put(IGST_VALUE, String.valueOf(bookingVO.getIgstvalue()));
                    values.put(UTGST_PERCENT, String.valueOf(bookingVO.getUtgstPerc()));
                    values.put(UTGST_VALUE, String.valueOf(bookingVO.getUtgstValue()));
                    values.put(TAX_CODE, String.valueOf(bookingVO.getTaxCode()));
                    values.put(COLUMN_CONVERSION_FACTOR, String.valueOf(bookingVO.getTotQty()));
                    values.put(CONFIRM_STATUS, "N");
                    values.put(COLUMN_UPLOAD, "N");
                    // Inserting Row
                    long ack = db.insert(IDBColumns.TABLE_PURCHASE_ORDER_BOOKING, null, values);
                    if (BuildConfig.DEBUG)
                        if (ack > 0) {
                            Log.i(TAG, " insertPurchaseOrderBooking inserted Qty > " + bookingVO.getQuantity());
                        } else {
                            Log.i(TAG, "insertPurchaseOrderBooking failed Qty > " + bookingVO.getQuantity());
                        }
                }
            }

        } finally {

            baseDB.closeDb();

        }
    }

    private void deleteExitePurchaseProductById(BaseDB baseDB, String distrCode, String invoiceNo, String prodCode, String batchCode) {

        String deleteOrderBooking = "distrCode=? and ProdCode=?  and BatchCode=? and upload=? and invoiceNo=?";
        String[] deleteArgs = {distrCode, prodCode, batchCode, "N", invoiceNo};

        baseDB.getDb().delete(TABLE_PURCHASE_ORDER_BOOKING, deleteOrderBooking, deleteArgs);
        baseDB.closeDb();
    }

    private boolean isPurchaseProductAdded(BaseDB baseDB, String distrCode, String invoiceNo, String prodCode, String batchCode) {

        Cursor c = baseDB.getDb().rawQuery(QUERY_IS_PURCHASE_ORDER_ADDED, new String[]{distrCode, prodCode, batchCode, invoiceNo});
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

    /**
     * Insert order booking details in orderbooking table (if product is already is added delete and insert data)
     *
     * @param baseDB          db instance for writing data in the table
     * @param orderBookingVOs order details insert in the table
     */
    public void insertOrderBooking(BaseDB baseDB, String invoiceNo,
                                   String routecode, String retrCode,
                                   List<OrderBookingVO> orderBookingVOs, String retailerType, int newRetailer) {
        try {
            SQLiteDatabase db = baseDB.getDb();
            if (invoiceNo != null && !invoiceNo.isEmpty()) {
                deleteExiteProductByOrderId(baseDB, invoiceNo);
            }
            for (OrderBookingVO bookingVO : orderBookingVOs) {

//                if (isProductAdded(baseDB, distrCode, salesmanCode, routecode, retrCode, bookingVO, Globals.NAME_ORDER_BOOKING)) {
//                    deleteExiteProductById(baseDB, bookingVO.getDistrCode(), bookingVO.getCmpCode(), routecode, bookingVO.getRetailerCode(), bookingVO);
//                }

                if (!db.isOpen())
                    db = baseDB.getDb();

                if (bookingVO.getQuantity() > 0) {

                    Double taxAmt = bookingVO.getCgstvalue().doubleValue() + bookingVO.getSgstValue().doubleValue() + bookingVO.getIgstvalue().doubleValue()
                            + bookingVO.getUtgstValue().doubleValue() + bookingVO.getCessValue().doubleValue();

                    ContentValues values = new ContentValues();
                    values.put(INVOICE_NO, bookingVO.getOrderInvoiceNo());
                    values.put(COLUMN_DISTR_CODE, bookingVO.getDistrCode());
                    values.put(COLUMN_CMP_CODE, bookingVO.getCmpCode());
                    values.put(COLUMN_ROUTE_CODE, routecode);
                    values.put(COLUMN_RETLR_CODE_CAPS, bookingVO.getRetailerCode());
                    values.put(COLUMN_ORDER_DATE_CAPS, DateUtil.getCurrentYearMonthDate());
                    values.put(COLUMN_BATCH_CODE, bookingVO.getProdBatchCode());
                    values.put(COLUMN_PROD_CODE_CAPS, bookingVO.getProdCode());
                    values.put(COLUMN_ORDER_QTY_CAPS, bookingVO.getQuantity());
                    values.put(COLUMN_CUSTOMER_SHIP_CODE, bookingVO.getAddress());
                    values.put(COLUMN_MRP_CAPS, String.valueOf(bookingVO.getMrp()));
                    values.put(COLUMN_ORDER_STATUS_CAPS, "i");
                    values.put(IDBColumns.COLUMN_REMARKS_CAPS, bookingVO.getRemarks());
                    values.put(COLUMN_ORDER_VALUE_CAPS, bookingVO.getOrderValue().doubleValue());
                    values.put(COLUMN_PRIM_DISC_ORDER_VALUE, bookingVO.getPrimDiscOrderValue().doubleValue());
                    values.put(COLUMN_UOM_ID, bookingVO.getUomId());
                    values.put(SCHEME_AMOUNT, String.valueOf(bookingVO.getSchemeAmount()));
                    if (bookingVO.getNetAmount().doubleValue() > 0) {
                        values.put(NET_AMOUNT, String.valueOf(bookingVO.getTotalAmount()));
                    } else {
                        values.put(NET_AMOUNT, "0");
                    }
                    if (bookingVO.getActualSellRate().doubleValue() > 0) {
                        values.put(COLUMN_ACTUAL_SELL_RATE, String.valueOf(bookingVO.getActualSellRate()));
                    } else {
                        values.put(COLUMN_ACTUAL_SELL_RATE, "0");
                    }
                    values.put(TOTAL_AMOUNT, String.valueOf(bookingVO.getTotalAmount()));
                    values.put(GROSS_AMOUNT, String.valueOf(bookingVO.getlGrossAmt()));
                    values.put(COLUMN_LATITUDE, String.valueOf(bookingVO.getLatitude()));
                    values.put(COLUMN_LONGITUDE, String.valueOf(bookingVO.getLongitude()));
                    values.put(COLUMN_START_TIME, String.valueOf(bookingVO.getStartTime()));
                    values.put(COLUMN_END_TIME, String.valueOf(bookingVO.getEndTime()));
                    values.put(TAX_PER_PRODUCT, taxAmt);
                    values.put(CGST_PERCENT, String.valueOf(bookingVO.getCgstperc()));
                    values.put(CGST_VALUE, String.valueOf(bookingVO.getCgstvalue()));
                    values.put(SGST_PERCENT, String.valueOf(bookingVO.getSgstPerc()));
                    values.put(SGST_VALUE, String.valueOf(bookingVO.getSgstValue()));
                    values.put(IGST_PERCENT, String.valueOf(bookingVO.getIgstPerc()));
                    values.put(IGST_VALUE, String.valueOf(bookingVO.getIgstvalue()));
                    values.put(UTGST_PERCENT, String.valueOf(bookingVO.getUtgstPerc()));
                    values.put(UTGST_VALUE, String.valueOf(bookingVO.getUtgstValue()));
                    values.put(TAX_CODE, String.valueOf(bookingVO.getTaxCode()));

                    if ((retailerType != null && !retailerType.isEmpty()) &&
                            (retailerType.equalsIgnoreCase("E") ||
                                    retailerType.equalsIgnoreCase("Z"))) {

                        values.put(IS_EXISTING_RETAILER, String.valueOf("Y"));
                    } else {
                        if (newRetailer == 1) {
                            values.put(IS_EXISTING_RETAILER, String.valueOf("N"));
                        } else {
                            values.put(IS_EXISTING_RETAILER, String.valueOf("Y"));
                        }

                    }
                    values.put(COLUMN_CONVERSION_FACTOR, String.valueOf(bookingVO.getTotQty()));
                    values.put(COLUMN_DEFAULT_UOMID_CAPS, String.valueOf(bookingVO.getBaseUOM()));
                    values.put(COLUMN_SOQ, String.valueOf(bookingVO.getSuggestedQty()));
                    values.put(COLUMN_COMPLETE_FLAG, "N");
                    values.put(COLUMN_PROD_TYPE, bookingVO.getProdType());
                    values.put(READABLE_INV_NO, bookingVO.getReadableInvNo());
                    values.put(COLUMN_UPLOAD, "N");

                    // Inserting Row
                    db.insert(IDBColumns.TABLE_ORDER_BOOKING, null, values);

                }
            }

        } finally {

            baseDB.closeDb();
        }
    }


    /**
     * Insert ordered product scheme details in Order Applied scheme table (if product is already is added delete and insert data)
     *
     * @param baseDB                   db instance for writing data in the table
     * @param routecode                beat tagged salesman
     * @param retrCode                 salesman mapped retailer
     * @param orderedProdSchemeDetails order product scheme details insert in the table
     */
    public void insertOrderedProductSchemeList(BaseDB baseDB, String invoiceNo, String routecode, String retrCode,
                                               List<SchemeModel> orderedProdSchemeDetails,
                                               String tableName) {
        try {
            SQLiteDatabase db = baseDB.getDb();
            for (SchemeModel prodScheme : orderedProdSchemeDetails) {

                OrderBookingVO bookingVO = new OrderBookingVO();
                bookingVO.setProdCode(prodScheme.getProductCode());
                bookingVO.setProdBatchCode(prodScheme.getProdBatchCode());

                deleteExistFreeProductById(baseDB, distrCode, prodScheme.getCmpCode(), routecode, retrCode, bookingVO, tableName);
            }

            for (SchemeModel prodScheme : orderedProdSchemeDetails) {
                if (!db.isOpen())
                    db = baseDB.getDb();

                ContentValues values = new ContentValues();
                values.put(COLUMN_DISTR_CODE, prodScheme.getDistrCode());
                values.put(COLUMN_CMP_CODE, prodScheme.getCmpCode());
                values.put(READABLE_INV_NO, prodScheme.getReadableInvNo());
                values.put(COLUMN_CUSTOMER_CODE, prodScheme.getCustomerCode());
                values.put(ORDER_NO, prodScheme.getInvoiceNo());
                values.put(COLUMN_PROD_CODE, prodScheme.getProductCode());
                values.put(COLUMN_BATCH_CODE, prodScheme.getProdBatchCode());
                values.put(SCHEME_CODE, prodScheme.getSchemeCode());
                values.put(SCHEME_SLAB_NO, prodScheme.getSlabNo());
                values.put(FREE_PROD_CODE, prodScheme.getFreeProdCode());
                values.put(FREE_PROD_NAME, prodScheme.getFreeProdName());
                values.put(FREE_QTY, prodScheme.getFreeQty());
                values.put(DISCOUNT_AMOUNT, prodScheme.getFlatAmount());
                values.put(DISCOUNT_PERCENTAGE, prodScheme.getPercentage());
                values.put(COLUMN_UPLOAD, "N");

                // Inserting Row
                if (tableName != null && tableName.equalsIgnoreCase(TABLE_BILLING)) {

                    db.insert(IDBColumns.TABLE_BILLING_APPLIED_SCHEME, null, values);
                } else if (tableName != null && tableName.equalsIgnoreCase(TABLE_ORDER_CONFIRMATION)) {
                    db.insert(IDBColumns.TABLE_CONFIRM_ORDER_APPLIED_SCHEME, null, values);
                } else {

                    db.insert(IDBColumns.TABLE_ORDERED_APPLIED_SCHEME, null, values);
                }
            }

        } finally {

            baseDB.closeDb();
        }
    }

    /**
     * delete ordered product scheme details in Order Applied scheme table (if product is already is added delete and insert data)
     *
     * @param baseDB                   db instance for writing data in the table
     * @param routecode                beat tagged salesman
     * @param retrCode                 salesman mapped retailer
     * @param orderedProdSchemeDetails order product scheme details insert in the table
     */
    public void deleteAllSchemeProdRetailer(BaseDB baseDB, String routecode, String retrCode, List<OrderBookingVO> orderedProdSchemeDetails,
                                            String tableName) {

        try {
            for (OrderBookingVO orderBookingVO : orderedProdSchemeDetails) {
                deleteExistFreeProductById(baseDB, orderBookingVO.getDistrCode(), orderBookingVO.getCmpCode(), routecode, retrCode, orderBookingVO, tableName);
            }

        } finally {

            baseDB.closeDb();
        }
    }

    /**
     * Insert order booking details in orderbooking table (if product is already is added delete and insert data)
     *
     * @param baseDB          db instance for writing data in the table
     * @param routecode       beat tagged salesman
     * @param retrCode        salesman mapped retailer
     * @param date            order date
     * @param orderBookingVOs order details insert in the table
     */
    void insertBilling(BaseDB baseDB, String routecode, String retrCode, String salesmanCode,
                       String date, List<OrderBookingVO> orderBookingVOs, String invoiceNo, String freeQty, String completeFlag) {

        try {
            SQLiteDatabase db = baseDB.getDb();
            for (OrderBookingVO bookingVO : orderBookingVOs) {

                if (isBilledProductAdded(baseDB, distrCode, salesmanCode, routecode, retrCode, invoiceNo, bookingVO)) {
                    deleteBillingProductById(baseDB, distrCode, salesmanCode, routecode, retrCode, invoiceNo, bookingVO);
                }

                if (!db.isOpen())
                    db = baseDB.getDb();

//                if (bookingVO.getTotInvoiceQty() > 0) {
//
//                    Double taxAmt = bookingVO.getCgstvalue().doubleValue() + bookingVO.getSgstValue().doubleValue() + bookingVO.getIgstvalue().doubleValue()
//                            + bookingVO.getUtgstValue().doubleValue() + bookingVO.getCessValue().doubleValue();
//
//                    Double netAmt = bookingVO.getlGrossAmt().doubleValue() - bookingVO.getDbDiscountAmt() + taxAmt - bookingVO.getSchemeAmount().doubleValue();
//
//                    String inputStr = "";
//                    if (bookingVO.getQuantity() > 0) {
//                        inputStr = ((int) bookingVO.getQuantity()) + " " + bookingVO.getUomId() + " ";
//                    }
//                    if (bookingVO.getQuantity2() > 0) {
//                        inputStr = inputStr + ((int) bookingVO.getQuantity2()) + " " + bookingVO.getUomId2();
//                    }
//
//                    ContentValues values = new ContentValues();
//                    values.put(COLUMN_CMP_CODE, cmpCode);
//                    values.put(COLUMN_DISTR_CODE, distrCode);
//                    values.put(COLUMN_SALESMAN_CODE, salesmanCode);
//                    values.put(COLUMN_ROUTE_CODE, routecode);
//                    values.put(COLUMN_CUSTOMER_CODE, retrCode);
//                    values.put(COLUMN_INVOICE_NUM, invoiceNo);
//                    values.put(COLUMN_PROD_CODE, bookingVO.getProdCode());
//                    values.put(COLUMN_PROD_BATCH_CODE, bookingVO.getProdBatchCode());
//                    values.put(COLUMN_TOTAL_ORD_QTY, bookingVO.getTotOrderQty());
//                    values.put(COLUMN_TOTAL_INVOICE_QTY, bookingVO.getTotInvoiceQty());
//                    values.put(FREE_QTY, bookingVO.getFreeQty());
//                    values.put(COLUMN_INVOICE_QTY1, bookingVO.getQuantity());
//                    values.put(COLUMN_UOM_CODE1, bookingVO.getUomId());
//                    values.put(COLUMN_INVOICE_QTY2, bookingVO.getQuantity2());
//                    values.put(COLUMN_UOM_CODE2, bookingVO.getUomId2());
//                    values.put(COLUMN_MRP_CAPS, bookingVO.getMrp().toString());
//
//                    if (bookingVO.getEditRate() > 0) {
//                        values.put(COLUMN_SELL_RATE, bookingVO.getEditRate());
//                        values.put(COLUMN_ACTUAL_SELL_RATE, bookingVO.getCurrentSellPrice().toString());
//                    } else {
//                        values.put(COLUMN_SELL_RATE, "0.0");
//                        values.put(COLUMN_ACTUAL_SELL_RATE, bookingVO.getSellPrice().toString());
//                    }
//
//                    values.put(COLUMN_GROSS_AMT, bookingVO.getlGrossAmt().toString());
//                    values.put(COLUMN_SPL_DISC_AMT, "0");
//                    values.put(COLUMN_SPL_DISC_PERC, "0");
//                    values.put(COLUMN_SCH_DISC_AMT, String.valueOf(bookingVO.getSchemeAmount()));
//                    values.put(COLUMN_SCH_DISC_PERC, "0");
//                    values.put(COLUMN_CASH_DISC_AMT, "0");
//                    values.put(COLUMN_CASH_DISC_PERC, "0");
//                    values.put(COLUMN_DB_DISC_AMT, bookingVO.getDbDiscountAmt());
//                    values.put(COLUMN_DB_DISC_PERC, bookingVO.getDbDiscountPerc());
//                    values.put(COLUMN_INPUT_STR, inputStr);
//                    values.put(CGST_PERCENT, bookingVO.getCgstperc());
//                    values.put(CGST_AMT, String.valueOf(bookingVO.getCgstvalue()));
//                    values.put(SGST_PERCENT, bookingVO.getSgstPerc());
//                    values.put(SGST_AMT, String.valueOf(bookingVO.getSgstValue()));
//                    values.put(IGST_PERCENT, bookingVO.getIgstPerc());
//                    values.put(IGST_AMT, String.valueOf(bookingVO.getIgstvalue()));
//                    values.put(UTGST_PERCENT, String.valueOf(bookingVO.getUtgstPerc()));
//                    values.put(UTGST_VALUE, String.valueOf(bookingVO.getUtgstValue()));
//                    values.put(CESS_PERCENT, String.valueOf(bookingVO.getCessPerc()));
//                    values.put(CESS_VALUE, String.valueOf(bookingVO.getCessValue()));
//                    values.put(COLUMN_TAX_AMT, taxAmt);
//                    values.put(COLUMN_NET_AMT, netAmt);
//                    values.put(COLUMN_MOD_DT, System.currentTimeMillis());
//                    values.put(COLUMN_COMPLETE_FLAG, completeFlag);
//                    values.put(UPLOAD_FLAG, "N");
//                    values.put(COLUMN_PARENT_PROD_CODE, bookingVO.getParentProdCode());
//                    values.put(COLUMN_PARENT_PROD_BATCH_CODE, bookingVO.getParentProdBatchCode());
                // Inserting Row
//                    long ack = db.insert(TABLE_BILL_INVOICE_DETAILS, null, values);
//                    if (BuildConfig.DEBUG)
//                        if (ack > 0) {
//                            Log.i(TAG, "insertBilling inserted Qty > " + bookingVO.getQuantity());
//                            Log.i(TAG, "insertBilling inserted invoiceNo > " + invoiceNo);
//                        } else {
//                            Log.i(TAG, "insertBilling failed Qty > " + bookingVO.getQuantity());
//                        }
//                } else if (bookingVO.getFreeQty() > 0) {

                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, cmpCode);
                values.put(COLUMN_DISTR_CODE, distrCode);
                values.put(COLUMN_SALESMAN_CODE, salesmanCode);
                values.put(COLUMN_ROUTE_CODE, routecode);
                values.put(COLUMN_CUSTOMER_CODE, retrCode);
                values.put(COLUMN_INVOICE_NUM, invoiceNo);
//                    values.put(COLUMN_PROD_CODE, bookingVO.getProdCode());
//                    values.put(COLUMN_PROD_BATCH_CODE, bookingVO.getProdBatchCode());
//                    values.put(FREE_QTY, bookingVO.getFreeQty());
//                    values.put(COLUMN_UOM_CODE1, "");
//                    values.put(COLUMN_INPUT_STR, "");
//                    values.put(COLUMN_MOD_DT, System.currentTimeMillis());
//                    values.put(COLUMN_COMPLETE_FLAG, completeFlag);
//                    values.put(UPLOAD_FLAG, "N");
//                    values.put(COLUMN_MRP_CAPS, bookingVO.getMrp().toString());
//                    values.put(COLUMN_SELL_RATE, "0");
//                    values.put(COLUMN_ACTUAL_SELL_RATE, bookingVO.getSellPrice().toString());
                values.put(COLUMN_GROSS_AMT, "0");
                values.put(COLUMN_SCH_DISC_AMT, "0");
                values.put(COLUMN_NET_AMT, "0");
                values.put(CGST_AMT, "0");
                values.put(SGST_AMT, "0");
                values.put(IGST_AMT, "0");
                values.put(UTGST_VALUE, "0");
                values.put(COLUMN_TOTAL_ORD_QTY, "0");
                values.put(COLUMN_CASH_DISC_AMT, "0");
                values.put(COLUMN_TOTAL_INVOICE_QTY, "0");
                values.put(COLUMN_INVOICE_QTY1, "0");
                values.put(COLUMN_INVOICE_QTY2, "0");
                values.put(COLUMN_UOM_CODE2, "");
                values.put(COLUMN_SPL_DISC_AMT, "0");
                values.put(COLUMN_SPL_DISC_PERC, "0");
                values.put(COLUMN_SCH_DISC_PERC, "0");
                values.put(COLUMN_CASH_DISC_PERC, "0");
                values.put(COLUMN_DB_DISC_PERC, "0");
                values.put(COLUMN_DB_DISC_AMT, "0");
                values.put(CGST_PERCENT, "0");
                values.put(SGST_PERCENT, "0");
                values.put(IGST_PERCENT, "0");
                values.put(UTGST_PERCENT, "0");
                values.put(CESS_PERCENT, "0");
                values.put(CESS_VALUE, "0");
                values.put(COLUMN_TAX_AMT, "0");
//                    values.put(COLUMN_PARENT_PROD_CODE, bookingVO.getParentProdCode());
//                    values.put(COLUMN_PARENT_PROD_BATCH_CODE, bookingVO.getParentProdBatchCode());
                db.insert(TABLE_BILL_INVOICE_DETAILS, null, values);
//                }
            }

        } finally {

            baseDB.closeDb();
        }
    }

    /**
     * Insert order booking details in orderbooking table (if product is already is added delete and insert data)
     *
     * @param baseDB          db instance for writing data in the table
     * @param routecode       beat tagged salesman
     * @param retrCode        salesman mapped retailer
     * @param date            order date
     * @param orderBookingVOs order details insert in the table
     */
    void insertFreeProductInInvioiceDetails(BaseDB baseDB, String routecode, String retrCode, String salesmanCode,
                                            String date, List<OrderBookingVO> orderBookingVOs, String invoiceNo, String completeFlag) {

        try {
            SQLiteDatabase db = baseDB.getDb();
            for (OrderBookingVO bookingVO : orderBookingVOs) {

                if (isBilledProductAdded(baseDB, distrCode, salesmanCode, routecode, retrCode, invoiceNo, bookingVO)) {

                    updateBillingFreeQty(baseDB, cmpCode, distrCode, salesmanCode, routecode, retrCode, invoiceNo, bookingVO);

                } else {

                    ContentValues values = new ContentValues();
                    values.put(COLUMN_CMP_CODE, cmpCode);
                    values.put(COLUMN_DISTR_CODE, distrCode);
                    values.put(COLUMN_SALESMAN_CODE, salesmanCode);
                    values.put(COLUMN_ROUTE_CODE, routecode);
                    values.put(COLUMN_CUSTOMER_CODE, retrCode);
                    values.put(COLUMN_INVOICE_NUM, invoiceNo);
                    values.put(COLUMN_PROD_CODE, bookingVO.getProdCode());
                    values.put(COLUMN_PROD_BATCH_CODE, bookingVO.getProdBatchCode());
                    values.put(FREE_QTY, bookingVO.getFreeQty());
                    values.put(COLUMN_UOM_CODE1, "");
                    values.put(COLUMN_INPUT_STR, "");
                    values.put(COLUMN_MOD_DT, System.currentTimeMillis());
                    values.put(COLUMN_COMPLETE_FLAG, completeFlag);
                    values.put(UPLOAD_FLAG, "N");
                    values.put(COLUMN_MRP_CAPS, bookingVO.getMrp().toString());
                    values.put(COLUMN_SELL_RATE, "0");
                    values.put(COLUMN_ACTUAL_SELL_RATE, bookingVO.getSellPrice().toString());
                    values.put(COLUMN_GROSS_AMT, "0");
                    values.put(COLUMN_SCH_DISC_AMT, "0");
                    values.put(COLUMN_NET_AMT, "0");
                    values.put(CGST_AMT, "0");
                    values.put(SGST_AMT, "0");
                    values.put(IGST_AMT, "0");
                    values.put(UTGST_VALUE, "0");
                    values.put(COLUMN_TOTAL_ORD_QTY, "0");
                    values.put(COLUMN_CASH_DISC_AMT, "0");
                    values.put(COLUMN_TOTAL_INVOICE_QTY, "0");
                    values.put(COLUMN_INVOICE_QTY1, "0");
                    values.put(COLUMN_INVOICE_QTY2, "0");
                    values.put(COLUMN_UOM_CODE2, "");
                    values.put(COLUMN_SPL_DISC_AMT, "0");
                    values.put(COLUMN_SPL_DISC_PERC, "0");
                    values.put(COLUMN_SCH_DISC_PERC, "0");
                    values.put(COLUMN_CASH_DISC_PERC, "0");
                    values.put(COLUMN_DB_DISC_PERC, "0");
                    values.put(COLUMN_DB_DISC_AMT, "0");
                    values.put(CGST_PERCENT, "0");
                    values.put(SGST_PERCENT, "0");
                    values.put(IGST_PERCENT, "0");
                    values.put(UTGST_PERCENT, "0");
                    values.put(COLUMN_TAX_AMT, "0");
//                    values.put(COLUMN_PARENT_PROD_CODE, bookingVO.getParentProdCode());
//                    values.put(COLUMN_PARENT_PROD_BATCH_CODE, bookingVO.getParentProdBatchCode());
                    db.insert(TABLE_BILL_INVOICE_DETAILS, null, values);
                }
            }

        } finally {

            baseDB.closeDb();
        }
    }

    /**
     * Get all the order product for that particular retailer for summary
     *
     * @param sfaDatabase   db instance to read data from db
     * @param prefInvoiceNo device config distributor code
     * @return list of ordered products
     */

    List<OrderBookingVO> getPurchaseOrderedSummaryFromDB(SFADatabase sfaDatabase,
                                                         String prefInvoiceNo) {

        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_PURCHASE_ORDER_SUMMARY, new String[]{prefInvoiceNo});

        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)).trim());
                orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)).trim());
                orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)).trim());
                orderBookingVO.setSellPrice(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS)), "0")));
                orderBookingVO.setMrp(new BigDecimal(Globals.checkNull(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS)), "0")));
                orderBookingVO.setPurchasePrice(Double.valueOf(Globals.checkNull(c.getString(c.getColumnIndex(COLUMN_PURCHASE_PRICE)), "0")));
                orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));
                orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)));
                orderBookingVO.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                orderBookingVO.setTax(c.getDouble(c.getColumnIndex("taxPerProduct")));
                orderBookingVO.setSchemeAmount(new BigDecimal(c.getString(c.getColumnIndex(SCHEME_AMOUNT))));
                orderBookingVO.setNetAmount(new BigDecimal(c.getString(c.getColumnIndex(NET_AMOUNT))));
                orderBookingVO.setTotalAmount(new BigDecimal(c.getString(c.getColumnIndex("grossAmount"))));


                double cgst = c.getDouble(c.getColumnIndex("CGSTPerc"));
                double sgst = c.getDouble(c.getColumnIndex("SGSTPerc"));
                double utgst = c.getDouble(c.getColumnIndex("UTGSTPerc"));
                double igst = c.getDouble(c.getColumnIndex("IGSTPerc"));

                double taxPerc = cgst + sgst + utgst + igst;

//                orderBookingVO.setlTaxPerc(taxPerc);

                /*orderBookingVO.setCgstperc(c.getDouble(c.getColumnIndex("CGSTPerc")));
                orderBookingVO.setSgstPerc(c.getDouble(c.getColumnIndex("SGSTPerc")));
                orderBookingVO.setUtgstPerc(c.getDouble(c.getColumnIndex("UTGSTPerc")));
                orderBookingVO.setIgstPerc(c.getDouble(c.getColumnIndex("IGSTPerc")));*/

                orderBookingList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingList;
    }

    /**
     * Get all the order product for that particular retailer for summary
     *
     * @param sfaDatabase db instance to read data from db
     * @return list of ordered products
     */

    List<OrderBookingVO> getOrderedProductFromDB(SFADatabase sfaDatabase,
                                                 String code) {

        List<OrderBookingVO> orderBookingList = new ArrayList<>();


        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_ORDERED_PRODUCT, new String[]{});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                orderBookingVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                orderBookingVO.setRetailerCode(c.getString(c.getColumnIndex(COLUMN_RETLR_CODE_CAPS)).trim());
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim());
                orderBookingVO.setOrderNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NO)).trim());
                orderBookingVO.setReadableInvNo(c.getString(c.getColumnIndex(READABLE_INV_NO)).trim());
                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)).trim());
                orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)).trim());
                orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                orderBookingVO.setSellPrice(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));

                orderBookingVO.setSchemeAmount(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(SCHEME_AMOUNT))));
                orderBookingVO.setNetAmount(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(NET_AMOUNT))));
                orderBookingVO.setTotalAmount(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(TOTAL_AMOUNT))));
                orderBookingVO.setlGrossAmt(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(GROSS_AMOUNT))));

                orderBookingVO.setCgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(CGST_VALUE))));
                orderBookingVO.setCgstperc(c.getDouble(c.getColumnIndex(CGST_PERCENT)));
                orderBookingVO.setSgstPerc(c.getDouble(c.getColumnIndex(SGST_PERCENT)));
                orderBookingVO.setSgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(SGST_VALUE))));
                orderBookingVO.setUtgstPerc(c.getDouble(c.getColumnIndex(UTGST_PERCENT)));
                orderBookingVO.setUtgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(UTGST_VALUE))));
                orderBookingVO.setIgstPerc(c.getDouble(c.getColumnIndex(IGST_PERCENT)));
                orderBookingVO.setIgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(IGST_VALUE))));

                orderBookingVO.setMrp(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_MRP_CAPS))));
                orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));
                orderBookingVO.setStockCheckQty(c.getDouble(c.getColumnIndex("stockqty")));
                orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)));
                orderBookingVO.setStockUomId(c.getString(c.getColumnIndex("stockUomId")));
                orderBookingVO.setOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                orderBookingVO.setPrimDiscOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_PRIM_DISC_ORDER_VALUE))));
                orderBookingVO.setTax(c.getDouble(c.getColumnIndex("taxPerProduct")));

                orderBookingVO.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
                orderBookingVO.setLastOrdQty(c.getInt(c.getColumnIndex(COLUMN_LAST_ORD_QTY)));
                orderBookingVO.setOrderFreeQty(c.getInt(c.getColumnIndex(COLUMN_TOT_ORDER_QTY)));

                orderBookingList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingList;
    }


    /**
     * Check whether product is added or not
     *
     * @return added return true or false
     */
    private boolean isProductAdded(BaseDB sfaDatabase, String distrCode, String salesmanCode,
                                   String routeCode, String retlrCode, OrderBookingVO bookingVO, String screenType) {
        Boolean result = false;
        String query;
        query = getQueryToCheckProductAdded(screenType);
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{bookingVO.getCmpCode(), bookingVO.getDistrCode(), bookingVO.getRetailerCode(),
                bookingVO.getOrderInvoiceNo(), bookingVO.getProdCode(), bookingVO.getProdBatchCode()});
        try {
            if (c != null && c.getCount() > 0) {
                result = true;

            }
        } catch (Exception e) {
            Log.e(TAG, "isProductAdded: " + e.getMessage(), e);
        } finally {
            if (c != null) {
                c.close();
            }
            sfaDatabase.closeDb();
        }
        return result;
    }


    private boolean isBilledProductAdded(BaseDB sfaDatabase, String distrCode, String salesmanCode,
                                         String routeCode, String retlrCode, String invoiceNo, OrderBookingVO bookingVO) {

        boolean result = false;
//        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_CHECK_BILLED_PRODUCT_ADDED, new String[]{distrCode, salesmanCode,
//                routeCode, retlrCode, invoiceNo, bookingVO.getProdCode(), bookingVO.getProdBatchCode(),
//                bookingVO.getParentProdCode(), bookingVO.getParentProdBatchCode()});
//        try {
//            if (c != null && c.getCount() > 0) {
//                result = true;
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "isBilledProductAdded: " + e.getMessage(), e);
//        } finally {
//            if (c != null) {
//                c.close();
//            }
//            sfaDatabase.closeDb();
//        }
        return result;
    }

    /**
     * Delete product if user want to delete based on below params
     *
     * @param baseDB           db instance to write the data in table
     * @param prefDistrcode    device id mapped distributor
     * @param prefSalesmancode mapped with distributor
     * @param prefRoutecode    mapped with the salesman
     * @param retrCode         retailer mapped with salesman
     * @param bookingVO        products for the retailer
     */
    void deleteBillingProductById(BaseDB baseDB, String prefDistrcode, String prefSalesmancode, String prefRoutecode, String retrCode, String invoiceNo, OrderBookingVO bookingVO) {

        String deleteOrderBooking = "distrCode=? and  salesmanCode=? and routeCode=? and customerCode=? and invoiceNo=? and prodCode=? and ProdBatchCode=? and uploadFlag=?";
        String[] deleteArgs = {prefDistrcode, prefSalesmancode, prefRoutecode, retrCode, invoiceNo, bookingVO.getProdCode(), bookingVO.getProdBatchCode(), "N"};
        baseDB.getDb().delete(TABLE_BILL_INVOICE_DETAILS, deleteOrderBooking, deleteArgs);

        if (bookingVO.getFreeQty() > 0) {
            SchemeModel freeModel = getFreeProductDetails(baseDB, prefDistrcode, prefSalesmancode, prefRoutecode, retrCode, invoiceNo, bookingVO.getProdCode(), bookingVO.getProdBatchCode());
            if (freeModel != null) {
                String[] args = {prefDistrcode, prefSalesmancode, prefRoutecode, retrCode, invoiceNo, freeModel.getFreeProdCode(), freeModel.getFreeProdBatchCode(), "N"};
                baseDB.getDb().delete(TABLE_BILL_INVOICE_DETAILS, deleteOrderBooking, args);
            }
        }

        String deleteAppliedScheme = "distrCode=? and  salesmanCode=? and routeCode=? and customerCode=? and orderNo=? and prodCode=? and batchCode=? and upload=?";
        baseDB.getDb().delete(TABLE_BILLING_APPLIED_SCHEME, deleteAppliedScheme, deleteArgs);
        baseDB.closeDb();

    }

    /**
     * Delete product if user want to delete based on below params
     *
     * @param sfaDatabase   db instance to write the data in table
     * @param prefDistrcode device id mapped distributor
     * @param bookingVO     products for the retailer
     */
    public void deleteExiteProductById(BaseDB sfaDatabase, String prefDistrcode, String cmpCode, String prefRoutecode, String retrCode, OrderBookingVO bookingVO) {

        String[] deleteArgs = {prefDistrcode, cmpCode, retrCode, bookingVO.getProdCode(), bookingVO.getProdBatchCode(), "N"};

        sfaDatabase.getDb().delete(TABLE_ORDER_BOOKING, UPDATE_CHECK_DC_SC_RC_RTLC_PC_U, deleteArgs);
        sfaDatabase.closeDb();

    }

    public void deleteExiteProductByOrderId(BaseDB sfaDatabase, String orderId) {

        String[] deleteArgs = {orderId, "N"};

        sfaDatabase.getDb().delete(TABLE_ORDER_BOOKING, "readableInvNo = ? and upload = ?", deleteArgs);
        sfaDatabase.getDb().delete(TABLE_ORDERED_APPLIED_SCHEME, "readableInvNo = ? and upload = ?", deleteArgs);
        sfaDatabase.closeDb();

    }

    /**
     * Delete product if user want to delete based on below params
     *
     * @param sfaDatabase      db instance to write the data in table
     * @param prefDistrcode    device id mapped distributor
     * @param prefSalesmancode mapped with distributor
     * @param prefRoutecode    mapped with the salesman
     * @param retrCode         retailer mapped with salesman
     * @param bookingVO        products for the retailer
     */
    public void deleteExistFreeProductById(BaseDB sfaDatabase, String prefDistrcode, String prefSalesmancode,
                                           String prefRoutecode, String retrCode, OrderBookingVO bookingVO, String tableName) {

        String deleteOrderBooking = "distrCode=? and  cmpCode=? and readableInvNo=? and customerCode=? and ProdCode=? and BatchCode=? and upload=?";
        String[] deleteArgs = {prefDistrcode, prefSalesmancode, prefRoutecode, retrCode, bookingVO.getProdCode(), bookingVO.getProdBatchCode(), "N"};

        if (tableName != null && tableName.equalsIgnoreCase(TABLE_BILLING)) {

            sfaDatabase.getDb().delete(TABLE_BILLING_APPLIED_SCHEME, deleteOrderBooking, deleteArgs);
        } else if (tableName != null && tableName.equalsIgnoreCase(TABLE_ORDER_CONFIRMATION)) {
            sfaDatabase.getDb().delete(TABLE_CONFIRM_ORDER_APPLIED_SCHEME, deleteOrderBooking, deleteArgs);
        } else {
            sfaDatabase.getDb().delete(TABLE_ORDERED_APPLIED_SCHEME, deleteOrderBooking, deleteArgs);
        }


        sfaDatabase.closeDb();

    }

    /**
     * Get the bank details for cheque details
     *
     * @param sfaDatabase  db instance to write the data in table
     * @param distrcode    device id mapped distributor
     * @param salesmancode mapped with distributor
     * @return backname and bank branch id
     */
    List<CollectionVO> getBankDetailFromDB(SFADatabase sfaDatabase, String distrcode, String salesmancode) {

        List<CollectionVO> bankList = new ArrayList<>();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_BANK_DETAILS, new String[]{distrcode, salesmancode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                CollectionVO bankDetail = new CollectionVO();
                bankDetail.setBankName(c.getString(c.getColumnIndex("bankName")));
                bankDetail.setBankCode(c.getString(c.getColumnIndex("bankCode")));
                bankDetail.setBankBranchCode(c.getString(c.getColumnIndex("bankBranchCode")));
                bankList.add(bankDetail);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return bankList;
    }

    /**
     * Delete cash details if salesman wants to
     */
    void deteleCashDetail(SFADatabase sfaDatabase, String distrcode, String salesmancode,
                          String routecode, String retrCode) {
        String deleteCash = "distrCode=? and  salesmanCode=? and routeCode=? AND customerCode=? AND upload = ?";
        String[] deleteArgs = {distrcode, salesmancode, routecode, retrCode, "N"};
        sfaDatabase.getDb().delete(TABLE_CASH_COLLECTION, deleteCash, deleteArgs);
    }

    void deteleChequeDetail(SFADatabase sfaDatabase, String distrcode, String salesmancode, String routecode, String retrCode) {
        String deleteCheque = "distrCode=? and  salesmanCode=? and routeCode=? AND customerCode=? AND upload = ?";
        String[] deleteArgs = {distrcode, salesmancode, routecode, retrCode, "N"};
        sfaDatabase.getDb().delete(TABLE_CHEQUE_COLLECTION, deleteCheque, deleteArgs);
    }

    /* insert cash details for particular retailer */
    void insertCashDetailsInDB(SFADatabase sfaDatabase, String invoiceNo, String routeCode, String retrCode,
                               double amount, String remarks, String state, String salesmanCode) {
        ContentValues values = new ContentValues();
        values.put(INVOICE_NO, invoiceNo);
        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_SALESMAN_CODE, salesmanCode);
        values.put(COLUMN_ROUTE_CODE, routeCode);
        values.put(IDBColumns.COLUMN_COLLECTION_DATE, DateUtil.getCurrentYearMonthDate());
        values.put(COLUMN_CUSTOMER_CODE, retrCode);
        values.put(IDBColumns.COLUMN_CASH_AMT, amount);
        values.put(IDBColumns.COLUMN_SCREEN_NAME_CAPS, state);
        values.put(IDBColumns.COLUMN_REMARKS_CAPS, remarks);
        values.put(COLUMN_UPLOAD, "N");
        sfaDatabase.getDb().insert(TABLE_CASH_COLLECTION, null, values);
    }

    /* insert cheque details for particular retailer */
    void insertChequeDetailsInDB(SFADatabase sfaDatabase, String invoiceNo,
                                 String routecode, String date, String retrCode, CollectionVO collectionVOS, String remarks, String salesmanCode) {

        ContentValues values = new ContentValues();
        values.put(INVOICE_NO, invoiceNo);
        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_SALESMAN_CODE, salesmanCode);
        values.put(COLUMN_ROUTE_CODE, routecode);
        values.put(IDBColumns.COLUMN_COLLECTION_DATE, date);
        values.put(COLUMN_CUSTOMER_CODE, retrCode);
        values.put(IDBColumns.COLUMN_BANK_NAME, collectionVOS.getBankName());
        values.put(IDBColumns.COLUMN_BANK_CODE, collectionVOS.getBankCode());
        values.put(IDBColumns.COLUMN_BANK_BRANCH_CODE, collectionVOS.getBankBranchCode());
        values.put(IDBColumns.COLUMN_INSTRUMENT, collectionVOS.getCheque());
        values.put(IDBColumns.COLUMN_INSTRUMENT_DATE, collectionVOS.getDate());
        values.put(IDBColumns.COLUMN_INSTRUMENT_AMT, collectionVOS.getAmount());
        values.put(IDBColumns.COLUMN_REMARKS_CAPS, remarks);
        values.put(COLUMN_UPLOAD, "N");
        sfaDatabase.getDb().insert(TABLE_CHEQUE_COLLECTION, null, values);
    }

    void updateRetailerUploadWordStatus(SFADatabase sfaDatabase, String collectionsUploaded, String state, String distrcode, String salesmancode, String routecode, String retrCode) {

        ContentValues values = new ContentValues();
        values.put(collectionsUploaded, state);

        String[] updateArgs = {distrcode, salesmancode, routecode, retrCode};

        sfaDatabase.getDb().update(IDBColumns.TABLE_CUSTOMER_UPLOAD_STATUS, values, UPDATE_CHECK_DC_SC_RC_CC, updateArgs);

    }

    /**
     * fetch cash details for that particular retailer based on the below params
     *
     * @param sfaDatabase  db instance to read data from db
     * @param distrcode    device mapped distributor
     * @param salesmancode dist mapped salesman
     * @param routecode    salesman mapped route
     * @param retrCode     salesman mapped retailer
     * @return list of cash details
     */
    List<CollectionVO> loadCashCollection(SFADatabase sfaDatabase, String distrcode, String salesmancode, String routecode, String retrCode) {
        List<CollectionVO> collectionList = new ArrayList<>();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CASH_COLLECTION, new String[]{distrcode, salesmancode, routecode, retrCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                CollectionVO collectionvo = new CollectionVO();
                collectionvo.setBankName("");
                collectionvo.setCheque("-");
                collectionvo.setAmount(c.getInt(c.getColumnIndex("cashAmt")));
                collectionvo.setDate(c.getString(c.getColumnIndex("collectionDate")));
                collectionvo.setMode("Cash");
                collectionList.add(collectionvo);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return collectionList;

    }

    /**
     * fetch cheque details for that particular retailer based on the below params
     *
     * @param sfaDatabase  db instance to read data from db
     * @param distrcode    device mapped distributor
     * @param salesmancode dist mapped salesman
     * @param routecode    salesman mapped route
     * @param retrCode     salesman mapped retailer
     * @return list of cheque details
     */
    List<CollectionVO> loadChequeCollection(SFADatabase sfaDatabase, String distrcode, String salesmancode, String routecode, String retrCode) {

        List<CollectionVO> collectionList = new ArrayList<>();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_CHEQUE_COLLECTION, new String[]{distrcode, salesmancode, routecode, retrCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                CollectionVO collectionvo = new CollectionVO();
                collectionvo.setBankName(c.getString(c.getColumnIndex("bankName")));
                collectionvo.setCheque(c.getString(c.getColumnIndex("instrument")));
                collectionvo.setAmount(c.getInt(c.getColumnIndex("instrumentAmt")));
                collectionvo.setDate(c.getString(c.getColumnIndex("instrumentDate")));
                collectionvo.setMode("Cheque");
                collectionList.add(collectionvo);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return collectionList;

    }

    /**
     * fetch cash details for that particular retailer based on the below params
     *
     * @param sfaDatabase  db instance to read data from db
     * @param distrcode    device mapped distributor
     * @param salesmancode dist mapped salesman
     * @param routecode    salesman mapped route
     * @param retrCode     salesman mapped retailer
     * @return list of cash details
     */
    String loadBillingCashCollection(SFADatabase sfaDatabase, String distrcode, String salesmancode, String routecode, String retrCode) {

        String collection = null;

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_COLLECTION_AMOUNT, new String[]{distrcode, salesmancode, routecode, retrCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                collection = String.valueOf(c.getInt(c.getColumnIndex("cashAmt")));

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return collection;

    }

    /**
     * Delete existing location details for that particular retailer based on the params
     *
     * @param sfaDatabase  db instance
     * @param distrcode    distributor Code
     * @param salesmancode salesman Code
     * @param retrCode     current retailer Code
     */
    void deleteRetailerExistingLocation(SFADatabase sfaDatabase, String distrcode, String salesmancode, String retrCode) {

        String deleteReturns = "distrCode=? AND salesmanCode=? AND customerCode=?";
        String[] deleteArgs = {distrcode, salesmancode, retrCode};
        sfaDatabase.getDb().delete(IDBColumns.TABLE_UPDATE_LOCATION, deleteReturns, deleteArgs);
    }

    /**
     * Insert the new updated location details in tables
     *
     * @param sfaDatabase db instance to write the data in db
     * @param retrCode    salesman mapped retailer
     * @param latitude    updated latitude
     * @param longitude   updated longitude
     * @param strPinCode  upload status
     */
    void insertRetailerLocaitonDetail(SFADatabase sfaDatabase, String routeCode, String retrCode, String latitude,
                                      String longitude, String strPinCode) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_SALESMAN_CODE, salesmanCode);
        values.put(COLUMN_ROUTE_CODE, routeCode);
        values.put(COLUMN_CUSTOMER_CODE, retrCode);
        values.put(IDBColumns.COLUMN_LATITUDE, latitude);
        values.put(IDBColumns.COLUMN_LONGITUDE, longitude);
        values.put(COLUMN_DATE, DateUtil.getCurrentYearMonthDate());
        values.put(IDBColumns.COLUMN_IMAGE, Globals.getOurInstance().getStrimage());
        values.put(IDBColumns.COLUMN_POSTAL_CODE, strPinCode);
        values.put(COLUMN_UPLOAD, "N");
        sfaDatabase.getDb().insert(IDBColumns.TABLE_UPDATE_LOCATION, null, values);
    }
    /*
     * Fetch sales returns reason list from the db
     * */

    /**
     * Update the existing latitude and longitude details for the particular retailer
     */
    void updateRetailerGeoLocation(SFADatabase sfaDatabase, String latitude, String longitude,
                                   String distrcode, String salesmancode, String routecode,
                                   String retrCode) {

        ContentValues values = new ContentValues();
        values.put(IDBColumns.COLUMN_LATITUDE, latitude);
        values.put(IDBColumns.COLUMN_LONGITUDE, longitude);
        String[] updateArgs = {distrcode, salesmancode, routecode, retrCode};
        sfaDatabase.getDb().update(TABLE_RETAILER, values, UPDATE_CHECK_DC_SC_RC_CC, updateArgs);

    }

    List<ReasonVO> getSalesReturnReasons(SFADatabase sfaDatabase, String screeName) {

        List<ReasonVO> reasonList = new ArrayList<>();
        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_REASON, new String[]{screeName});

        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    ReasonVO reasonVO = new ReasonVO();
                    reasonVO.setReasonCode(c.getString(c.getColumnIndex(COLUMN_REASON_CODE_CAPS)));
                    reasonVO.setReasonName(c.getString(c.getColumnIndex(COLUMN_REASON_NAME_CAPS)).trim());
                    reasonList.add(reasonVO);
                } while (c.moveToNext());
            }
            c.close();

        }

        return reasonList;
    }

    /*
     * Fetch the existing purchase returns data from db based on route and retailer code
     * */
    List<SalesReturnVO> loadExistingPurchaseReturns(SFADatabase sfaDatabase, String cmpCode, String distrCode) {


        List<SalesReturnVO> salesReturnList = new ArrayList<>();

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(QUERY_SELECT_EXISTING_PURCHASE_RETURN, new String[]{cmpCode, distrCode});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                SalesReturnVO salesReturnVO = new SalesReturnVO();
                salesReturnVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BATCH_CODE)));
                salesReturnVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE_CAPS)));
                salesReturnVO.setProdShortName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
                salesReturnVO.setReasonCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_REASON_CODE_CAPS)));
                salesReturnVO.setReasonName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_REASON_NAME_CAPS)));
                salesReturnVO.setRetlrCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex("RetlrCode")));
                salesReturnVO.setSalQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex("SalQty")));
                salesReturnVO.setUnSalQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_UNSAL_QTY_CAPS)));
                salesReturnVO.setSellPrice(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS)));
                salesReturnList.add(salesReturnVO);

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }
        return salesReturnList;


    }

    /*
     * Return all the products based on distributor and salesman code
     * */
    List<StockTakeVO> getAllReturnProductFromAuto(SFADatabase sfaDatabase, String cmpCode, String distrcode) {

        List<StockTakeVO> stockTakeList = new ArrayList<>();

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ALL_RETURN_PRODUCT, new String[]{cmpCode, distrcode});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                StockTakeVO stockTakeVO = new StockTakeVO();
                stockTakeVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BATCH_CODE)));
                stockTakeVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE_CAPS)));
                stockTakeVO.setProdShortName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
                stockTakeVO.setSellPrice(BigDecimal.valueOf(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                stockTakeList.add(stockTakeVO);

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }
        return stockTakeList;
    }

    void deletePurchaseReturn(SFADatabase sfaDatabase, String distrcode, String
            prodCode, String batchCode, String state) {

        String deleteReturns = "distrCode=? and  prodCode=? and batchCode=? AND upload = ?";
        String[] deleteArgs = {distrcode, prodCode, batchCode, state};
        sfaDatabase.getDb().delete(IDBColumns.TABLE_PURCHASE_PARTIAL_RETURN, deleteReturns, deleteArgs);

    }

    void insertPurchaseReturn(SFADatabase sfaDatabase, String inoviceNo, String distrcode,
                              String date, SalesReturnVO salesReturnVO, String returnType) {

        ContentValues values = new ContentValues();
        values.put(INVOICE_NO, salesReturnVO.getReferenceNo());
        values.put(GRN_NO, salesReturnVO.getReferenceNo());
        values.put(COLUMN_RETURN_REF_NO_CAPS, inoviceNo);
        values.put(COLUMN_DISTR_CODE, distrcode);
        values.put(IDBColumns.COLUMN_RETURN_DATE_CAPS, date);
        values.put(COLUMN_BATCH_CODE, salesReturnVO.getProdBatchCode());
        values.put(COLUMN_PROD_CODE_CAPS, salesReturnVO.getProdCode());
        values.put(COLUMN_REASON_CODE_CAPS, salesReturnVO.getReasonCode());
        values.put(COLUMN_REASON_NAME_CAPS, salesReturnVO.getReasonName());
        values.put(COLUMN_SAL_QTY_CAPS, salesReturnVO.getSalQty());
        values.put(COLUMN_SAL_UOM, salesReturnVO.getUomCode());
        values.put(COLUMN_UNSAL_REASON_CODE_CAPS, salesReturnVO.getUnSalReasonCode());
        values.put(COLUMN_UNSAL_REASON_NAME_CAPS, salesReturnVO.getUnSalReasonName());
        values.put(COLUMN_UNSAL_QTY_CAPS, salesReturnVO.getUnSalQty());
        values.put(COLUMN_UNSAL_UOM, salesReturnVO.getUnSalUomCode());
        values.put(COLUMN_RETURN_TYPE, returnType);
        values.put(COLUMN_UOM_CODE, salesReturnVO.getUomCode());
        values.put(COLUMN_UPLOAD, "N");
        // Inserting Row
        sfaDatabase.getDb().insert(IDBColumns.TABLE_PURCHASE_PARTIAL_RETURN, null, values);

    }

    /*
     * Delete the sales return based on the product code and upload state
     * */

    void deletePurchaseReturnById(SFADatabase sfaDatabase, String distrcode, String prodCode, String state) {

        String deleteReturns = "distrCode =? AND ProdCode =? AND upload =?";
        String[] deleteArgs = {distrcode, prodCode, state};
        sfaDatabase.getDb().delete(IDBColumns.TABLE_PURCHASE_PARTIAL_RETURN, deleteReturns, deleteArgs);
    }

    /* Delete collection (cash or cheque) record based on cheque number   */
    void deleteCollctionById(SFADatabase sfaDatabase, String distrcode, String salesmancode, String routecode, String cheque, String retrCode, String state) {

        if (cheque != null && cheque.equalsIgnoreCase("-")) {

            /*String deleteReturns = "distrCode =? AND salesmanCode =? AND customerCode =? AND routeCode =? AND upload =?";
            String[] deleteArgs = {distrcode, salesmancode, retrCode, routecode, state};
            sfaDatabase.getDb().delete(IDBColumns.TABLE_CASH_COLLECTION, deleteReturns, deleteArgs);*/

            String deleteReturns = "distrCode =? AND salesmanCode =? AND customerCode =? AND routeCode =? AND uploadFlag =?";
            String[] deleteArgs = {distrcode, salesmancode, retrCode, routecode, state};
            sfaDatabase.getDb().delete(IDBColumns.TABLE_COLLECTION, deleteReturns, deleteArgs);
        } else {

            /*String deleteReturns = "distrCode =? AND salesmanCode =? AND customerCode =? AND routeCode =? AND instrument =? AND upload =?";
            String[] deleteArgs = {distrcode, salesmancode, retrCode, routecode, cheque, state};
            sfaDatabase.getDb().delete(IDBColumns.TABLE_CHEQUE_COLLECTION, deleteReturns, deleteArgs);*/

            String deleteReturns = "distrCode =? AND salesmanCode =? AND customerCode =? AND routeCode =? AND instrumentNo =? AND uploadFlag =?";
            String[] deleteArgs = {distrcode, salesmancode, retrCode, routecode, cheque, state};
            sfaDatabase.getDb().delete(IDBColumns.TABLE_COLLECTION, deleteReturns, deleteArgs);
        }
    }

    String fetchOpeningStockInvoiceNo(SFADatabase sfaDatabase, String tableNamme, String distcode, String cmpCode) {
        String invoiceNo = "";

        String query = "SELECT invoiceNo FROM " + tableNamme + " WHERE distrCode =? AND cmpcode=? AND upload=?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{distcode, cmpCode, "N"});

        if (c != null && c.getCount() > 0 && c.moveToFirst())
            invoiceNo = c.getString(c.getColumnIndex(COLUMN_INVOICE_NO));
        if (c != null) {
            c.close();
        }
        return invoiceNo;
    }

    String fetchInvoiceNo(SFADatabase sfaDatabase, String tableNamme, String distcode, String salesmancode,
                          String routecode, String retailerCode) {
        String invoiceNo = "";
        Cursor c;
        String query = getQueryInvoiceNumber(tableNamme);
        if (tableNamme.equalsIgnoreCase(TABLE_CASH_COLLECTION) ||
                tableNamme.equalsIgnoreCase(TABLE_CHEQUE_COLLECTION) ||
                tableNamme.equalsIgnoreCase(TABLE_BILLING_COLLECTION)) {

            c = sfaDatabase.getDb().rawQuery(query, new String[]{distcode, salesmancode, routecode, retailerCode, "N"});

        } else if (tableNamme.equalsIgnoreCase(TABLE_LOADING_STOCK)) {

            c = sfaDatabase.getDb().rawQuery(query, new String[]{distcode, salesmancode, "N"});


        } else if (tableNamme.equalsIgnoreCase(TABLE_PURCHASE_RECEIPT_CONFIRMATION)) {

            c = sfaDatabase.getDb().rawQuery(query, new String[]{distcode, "N"});

        } else if (tableNamme.equalsIgnoreCase(TABLE_PURCHASE_ORDER_BOOKING)) {

            c = sfaDatabase.getDb().rawQuery(query, new String[]{distcode, "N", "N"});

        } else if (tableNamme.equalsIgnoreCase(TABLE_OPENING_STOCK)) {

            c = sfaDatabase.getDb().rawQuery(query, new String[]{distcode, salesmancode, "N"});

        } else if (tableNamme.equalsIgnoreCase(TABLE_BILLING)) {

            c = sfaDatabase.getDb().rawQuery(query, new String[]{distcode, salesmancode, routecode, retailerCode, "N", "N"});

        } else {
            c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distcode, routecode, retailerCode, "N"});

        }

        if (c != null && c.getCount() > 0 && c.moveToFirst())
            invoiceNo = c.getString(c.getColumnIndex("invoiceNo"));
        if (c != null) {
            c.close();
        }
        return invoiceNo;
    }


    boolean updateRetailerSign(SFADatabase sfaDatabase, String distCode, String salesmanCode,
                               String routecode, String retlrCode, String base64img) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_DIGITAL_SIGNATURE, base64img);
        String update = "distrCode= ? and salesmanCode= ? and routeCode= ? and customerCode= ?";
        String[] updateArgs = {distCode, salesmanCode, routecode, retlrCode};
        long ack = sfaDatabase.getDb().update(TABLE_RETAILER, values, update, updateArgs);
        return ack == 1;
    }

    /**
     * Check whether product is added or not
     *
     * @return added return true or false
     */
    private boolean isStockProductAdded(BaseDB sfaDatabase, String distrCode, String salesmanCode,
                                        String routeCode, String retlrCode, OrderBookingVO bookingVO) {

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_CHECK_IS_STOCK_PRODUCT_ADDED, new String[]{distrCode, salesmanCode, routeCode, retlrCode, bookingVO.getProdCode(), bookingVO.getProdBatchCode()});
        try {
            if (c != null && c.getCount() > 0) {
                return true;
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (c != null) {
                c.close();
            }
            sfaDatabase.closeDb();
        }
        return false;
    }

    /**
     * Check whether product is added or not
     *
     * @return added return true or false
     */
    private boolean isOpeningStockProductAdded(BaseDB sfaDatabase, String distrCode, String invoiceNo,
                                               String prodCode) {


        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_CHECK_IS_OPENING_STOCK_PROD_ADDED, new String[]{distrCode, invoiceNo, prodCode});
        try {
            if (c != null && c.getCount() > 0) {

                return true;
            }
        } finally {
            closeCursor(c);
            sfaDatabase.closeDb();
        }
        return false;
    }


    void insertOpeningStockData(SFADatabase baseDB, String invoiceNo, String cmpCode,
                                String distrCode, String date,
                                List<OrderBookingVO> orderBookingVOs) {

        try {
            SQLiteDatabase db = baseDB.getDb();
            for (OrderBookingVO bookingVO : orderBookingVOs) {

                if (isOpeningStockProductAdded(baseDB, distrCode, invoiceNo, bookingVO.getProdCode())) {

                    deleteExiteOpeningStockProductById(baseDB, distrCode, invoiceNo, bookingVO.getProdCode());

                }
                db = getSqLiteDatabase(baseDB, db);

                if (bookingVO.getStockCheckQty() > 0 ||
                        bookingVO.getFreeQty() > 0 ||
                        bookingVO.getUnSalQty() > 0) {
                    ContentValues values = new ContentValues();
                    values.put(INVOICE_NO, invoiceNo);
                    values.put(COLUMN_CMP_CODE, cmpCode);
                    values.put(COLUMN_DISTR_CODE, distrCode);
                    values.put(COLUMN_STOCK_TAKE_DATE_CAPS, date);
                    values.put(COLUMN_PROD_CODE_CAPS, bookingVO.getProdCode());
                    values.put(COLUMN_PROD_NAME_CAPS, bookingVO.getProdName());
                    values.put(COLUMN_STOCK_QTY_CAPS, bookingVO.getStockCheckQty());
                    values.put(COLUMN_UOM_CODE, bookingVO.getStockUomId());
                    values.put(FREE_QTY, bookingVO.getFreeQty());
                    values.put(FREE_UOM, bookingVO.getFreeUOM());
                    values.put(COLUMN_UNSAL_QTY_CAPS, bookingVO.getUnSalQty());
                    values.put(COLUMN_UNSAL_UOM, bookingVO.getUnSalUOM());
                    values.put(COLUMN_MRP_CAPS, bookingVO.getMrp().doubleValue());
                    values.put(COLUMN_PURCHASE_PRICE, bookingVO.getPurchasePrice());
                    values.put(COLUMN_UPLOAD, "N");

                    // Inserting Row
                    db.insert(IDBColumns.TABLE_OPENING_STOCK, null, values);

                }
            }

        } finally {

            baseDB.closeDb();
        }
    }

    void updateVanStockLoadingData(SFADatabase baseDB, String distrCode, String salesmanCode,
                                   List<OrderBookingVO> qtyUpdatedOrderBooking) {
        for (int i = 0; i < qtyUpdatedOrderBooking.size(); i++) {


            int availQty = qtyUpdatedOrderBooking.get(i).getAvailQty() - qtyUpdatedOrderBooking.get(i).getTotQty();

            ContentValues values = new ContentValues();
            values.put(COLUMN_AVAIL_QTY, availQty);
            values.put(COLUMN_LAST_ORD_QTY, qtyUpdatedOrderBooking.get(i).getTotQty());
            values.put(COLUMN_UPLOAD, "N");

            String appendAnd = APPEND_AND;
            /*String update = COLUMN_DISTR_CODE + appendAnd + COLUMN_SALESMAN_CODE + appendAnd + COLUMN_PROD_CODE + appendAnd + COLUMN_BATCH_CODE + " = ?";

            String[] updateArgs = {distrCode, salesmanCode, qtyUpdatedOrderBooking.get(i).getProdCode(),
                    qtyUpdatedOrderBooking.get(i).getProdBatchCode()};

            baseDB.getDb().update(TABLE_LOADING_STOCK, values, update, updateArgs);*/

            String update = COLUMN_DISTR_CODE + appendAnd + COLUMN_PROD_CODE + appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";

            String[] updateArgs = {distrCode, qtyUpdatedOrderBooking.get(i).getProdCode(),
                    qtyUpdatedOrderBooking.get(i).getProdBatchCode()};
            baseDB.getDb().update(TABLE_STOCK_ON_HAND, values, update, updateArgs);
            baseDB.closeDb();
        }

    }

    void insertStockTake(SFADatabase baseDB, String invoiceNo, String routecode, String retrCode, String date,
                         List<OrderBookingVO> orderBookingVOs) {

        try {
            SQLiteDatabase db = baseDB.getDb();
            for (OrderBookingVO bookingVO : orderBookingVOs) {

                if (isStockProductAdded(baseDB, distrCode, salesmanCode, routecode, retrCode, bookingVO)) {
                    deleteExistStockTakeProductById(baseDB, distrCode, salesmanCode, routecode, retrCode, bookingVO);
                }

                db = getSqLiteDatabase(baseDB, db);
                if (bookingVO.getStockCheckQty() > 0 && bookingVO.isItemSelected()) {
                    ContentValues values = new ContentValues();
                    values.put(INVOICE_NO, invoiceNo);
                    values.put(COLUMN_DISTR_CODE, distrCode);
                    values.put(COLUMN_SALESMAN_CODE, salesmanCode);
                    values.put(COLUMN_ROUTE_CODE, routecode);
                    values.put(COLUMN_RETLR_CODE_CAPS, retrCode);
                    values.put(IDBColumns.COLUMN_STOCK_TAKE_DATE_CAPS, date);
                    values.put(COLUMN_PROD_CODE_CAPS, bookingVO.getProdCode());
                    values.put(COLUMN_BATCH_CODE, bookingVO.getProdBatchCode());
                    values.put(IDBColumns.COLUMN_STOCK_QTY_CAPS, bookingVO.getStockCheckQty());
                    values.put(STOCK_CHECKED, getStockCheckSelected(bookingVO));
                    values.put(IDBColumns.STOCKTAKE_REASON, bookingVO.getStockTakeReason());
                    values.put(COLUMN_MRP_CAPS, bookingVO.getMrp().doubleValue());
                    values.put(IDBColumns.STOCK_STATUS_CAPS, "i");

                    values.put(COLUMN_ORDER_VALUE_CAPS, getStockOrderValue(bookingVO));
                    values.put(COLUMN_UOM_ID, bookingVO.getStockUomId());
                    values.put(COLUMN_CONVERSION_FACTOR, bookingVO.getStockConversionFactor());
                    values.put(COLUMN_DEFAULT_UOMID_CAPS, bookingVO.getBaseUOM());
                    values.put(COLUMN_UPLOAD, "N");
                    // Inserting Row
                    long ack = db.insert(IDBColumns.TABLE_STOCK_TAKE, null, values);
                    if (BuildConfig.DEBUG)
                        if (ack > 0) {
                            Log.i(TAG, "insertStockTake inserted Qty > " + bookingVO.getQuantity());
                        } else {
                            Log.i(TAG, "insertStockTake failed Qty > " + bookingVO.getQuantity());
                        }
                }
            }

        } finally {

            baseDB.closeDb();
        }
    }

    private double getStockOrderValue(OrderBookingVO bookingVO) {
        if (bookingVO.getStockOrderValue() == null) {
            return 0.00;
        } else {
            return bookingVO.getStockOrderValue().doubleValue();
        }
    }

    private String getStockCheckSelected(OrderBookingVO bookingVO) {
        if (bookingVO.isItemSelected() && bookingVO.getStockCheckQty() > 0) {
            return "Y";
        } else {
            return "N";
        }
    }

    private SQLiteDatabase getSqLiteDatabase(SFADatabase baseDB, SQLiteDatabase db) {
        if (!db.isOpen())
            db = baseDB.getDb();
        return db;
    }

    private void deleteExiteOpeningStockProductById(BaseDB sfaDatabase, String prefDistrcode, String invoiceNo, String prodCode) {

        String deleteOrderBooking = "distrCode=? and  invoiceNo=? and ProdCode=? and upload=?";
        String[] deleteArgs = {prefDistrcode, invoiceNo, prodCode, "N"};

        sfaDatabase.getDb().delete(TABLE_OPENING_STOCK, deleteOrderBooking, deleteArgs);
        sfaDatabase.closeDb();

    }

    List<OrderBookingVO> getOpeningStockSummaryProducts(SFADatabase sfaDatabase, String distcode) {

        List<OrderBookingVO> orderBookingList = new ArrayList<>();
        Cursor c = null;
        try {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_OPENING_STOCK_SUMMARY_PRODUCT, new String[]{distcode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                    orderBookingVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                    orderBookingVO.setOrderInvoiceNo(c.getString(c.getColumnIndex(COLUMN_STOCK_REF_NO_CAPS)).trim());
                    orderBookingVO.setOrderDate(c.getString(c.getColumnIndex(COLUMN_STOCK_TAKE_DATE_CAPS)).trim());
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)).trim());
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME_CAPS)).trim());
                    orderBookingVO.setStockCheckQty(c.getDouble(c.getColumnIndex(COLUMN_STOCK_QTY_CAPS)));
                    orderBookingVO.setStockUomId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setFreeQty(c.getInt(c.getColumnIndex(FREE_QTY)));
                    orderBookingVO.setFreeUOM(c.getString(c.getColumnIndex(FREE_UOM)));
                    orderBookingVO.setUnSalQty(c.getInt(c.getColumnIndex(COLUMN_UNSAL_QTY_CAPS)));
                    orderBookingVO.setUnSalUOM(c.getString(c.getColumnIndex(COLUMN_UNSAL_UOM)));
                    orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                    orderBookingVO.setPurchasePrice(c.getDouble(c.getColumnIndex(COLUMN_PURCHASE_PRICE)));
                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }
        return orderBookingList;
    }


    List<OrderBookingVO> fetchAllOpeningStockProducts(SFADatabase sfaDatabase, String distcode, String cmpCode) {
        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        Cursor c = null;

        try {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ALL_OPENING_STOCK_PRODUCT, new String[]{distcode, cmpCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)).trim());
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME_CAPS)).trim());
                    orderBookingVO.setStockCheckQty(c.getDouble(c.getColumnIndex(COLUMN_STOCK_QTY_CAPS)));
                    orderBookingVO.setStockUomId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setUomGroupId(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)));
                    orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setFreeQty(c.getInt(c.getColumnIndex(FREE_QTY)));
                    orderBookingVO.setFreeUOM(c.getString(c.getColumnIndex("freeUOM")));
                    orderBookingVO.setUnSalQty(c.getInt(c.getColumnIndex(COLUMN_UNSAL_QTY_CAPS)));
                    orderBookingVO.setUnSalUOM(c.getString(c.getColumnIndex("UnSalUom")));

                    orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)).trim());
                    orderBookingVO.setProdHierValName(c.getString(c.getColumnIndex(COLUMN_PRODHIER_VAL_NAME_CAPS)));
                    orderBookingVO.setProductHierPath(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH)));
                    orderBookingVO.setProductHierPathName(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_NAME)));
                    orderBookingVO.setBrandName(c.getString(c.getColumnIndex(COLUMN_BRAND_NAME)));
                    orderBookingVO.setBrandCode(c.getString(c.getColumnIndex(COLUMN_BRAND_CODE)));

                    orderBookingVO.setConversionFactor(getConversionFactor(sfaDatabase, orderBookingVO.getProdCode(), TABLE_VANSALES_UOM_MASTER));
                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }
        return orderBookingList;
    }

    List<OrderBookingVO> getAllStockTakeProducts(SFADatabase sfaDatabase, String distcode, String salesmancode,
                                                 String routecode, String retrCode) {
        List<OrderBookingVO> orderBookingList = new ArrayList<>();


        Cursor c = null;

        try {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ALL_STOCK_TAKE_PRODUCTS, new String[]{retrCode, routecode, retrCode, routecode, retrCode, distcode, salesmancode});
            boolean isCursorTrue = (c != null && c.getCount() > 0 && c.moveToFirst());
            if (isCursorTrue) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_BATCH_CODE)).trim());
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)).trim());
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME_CAPS)).trim());
                    orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)).trim());
                    orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                    orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                    orderBookingVO.setStockCheckQty(c.getDouble(c.getColumnIndex(COLUMN_STOCK_QTY_CAPS)));
                    orderBookingVO.setProdHierValName(c.getString(c.getColumnIndex(COLUMN_PRODHIER_VAL_NAME_CAPS)));
                    orderBookingVO.setProductHierPath(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH)));
                    orderBookingVO.setSuggestedQty(c.getInt(c.getColumnIndex(SUGGESTED_QUANTITY_CAPS)));
                    orderBookingVO.setStockInHand(c.getInt(c.getColumnIndex(COLUMN_STOCK_IN_HAND_CAPS)));
                    orderBookingVO.setNetWeight(c.getDouble(c.getColumnIndex(COLUMN_PRODUCT_NET_WGT)));
                    orderBookingVO.setWeightType(c.getString(c.getColumnIndex(COLUMN_PRODUCT_WGT_TYPE)));
                    orderBookingVO.setCategory(c.getString(c.getColumnIndex(CATEGORY_CAPS)));
                    orderBookingVO.setStockUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)));
                    orderBookingVO.setUomGroupId(c.getString(c.getColumnIndex(COLUMN_UOM_GROUPID_CAPS)));
                    orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_DEFAULT_UOMID_CAPS)));
                    orderBookingVO.setBrandName(c.getString(c.getColumnIndex(COLUMN_BRAND_NAME)));
                    orderBookingVO.setBrandCode(c.getString(c.getColumnIndex(COLUMN_BRAND_CODE)));
                    orderBookingVO.setBaseUOM(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setStockOrderValue(getOrderValue(c));
                    orderBookingVO.setItemSelected(getIsItemSelected(c));
                    orderBookingVO.setStockConversionFactor(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));

                    if (c.getInt(c.getColumnIndex(COLUMN_STOCK_QTY_CAPS)) > 0) {
                        orderBookingVO.setItemSelected(true);
                        orderBookingVO.setCheckBoxEnabled(false);
                    }

                    orderBookingVO.setConversionFactor(getConversionFactor(sfaDatabase, orderBookingVO.getProdCode(), TABLE_UOM_MASTER));

                    if (orderBookingList.contains(orderBookingVO)) {
                        OrderBookingVO orderBookingVO1 = orderBookingList.get(orderBookingList.indexOf(orderBookingVO));
                        String category = orderBookingVO1.getCategory();
                        List<String> categoryList = orderBookingVO1.getCategoryList();
                        if (categoryList == null) {
                            categoryList = new ArrayList<>();
                            categoryList.add(category);
                        }
                        categoryList.add(orderBookingVO.getCategory());

                        orderBookingVO1.setContainsMultipleCategory(true);
                        orderBookingVO1.setCategoryList(categoryList);
                    } /*else {

                        orderBookingList.add(orderBookingVO);
                    }*/
                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }
        return orderBookingList;
    }

    private boolean getIsItemSelected(Cursor c) {
        return c.getString(c.getColumnIndex(STOCK_CHECKED)) != null && c.getString(c.getColumnIndex(STOCK_CHECKED)).equalsIgnoreCase("Y");
    }

    private BigDecimal getOrderValue(Cursor c) {
        if (null != c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS)) && !c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS)).equals("")) {
            return new BigDecimal(c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS)));
        }
        return null;
    }

    List<OrderBookingVO> getStockTakeProductsFromDB(SFADatabase sfaDatabase, String prefDistrcode,
                                                    String prefSalesmancode, String prefRoutecode,
                                                    String retrCode) {
        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_STOCK_TAKE_PRODUCT, new String[]{prefDistrcode, prefSalesmancode, prefRoutecode, retrCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)).trim());
                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_BATCH_CODE)).trim());
                orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)).trim());
                orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME_CAPS)).trim());
                orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                orderBookingVO.setStockCheckQty(c.getDouble(c.getColumnIndex(COLUMN_STOCK_QTY_CAPS)));
                orderBookingVO.setStockUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)).trim());
                orderBookingVO.setStockOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                if (c.getString(c.getColumnIndex(STOCK_CHECKED)) != null && c.getString(c.getColumnIndex(STOCK_CHECKED)).equalsIgnoreCase("Y")) {
                    orderBookingVO.setItemSelected(true);
                }
                if (c.getInt(c.getColumnIndex(COLUMN_STOCK_QTY_CAPS)) > 0) {
                    orderBookingVO.setItemSelected(true);
                    orderBookingVO.setCheckBoxEnabled(false);
                }
                orderBookingVO.setStockConversionFactor(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));

                orderBookingList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingList;
    }

    List<OrderBookingVO> getStockTakeProductsOnlyWithQtyFromDB(SFADatabase sfaDatabase, String prefDistrcode,
                                                               String prefSalesmancode, String prefRoutecode,
                                                               String retrCode) {
        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_STOCK_TAKE_PRODUCT_ONLY_WITH_QUANTITY, new String[]{prefDistrcode, prefSalesmancode, prefRoutecode, retrCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)).trim());
                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_BATCH_CODE)).trim());
                orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)).trim());
                orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME_CAPS)).trim());
                orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                orderBookingVO.setStockCheckQty(c.getDouble(c.getColumnIndex(COLUMN_STOCK_QTY_CAPS)));
                orderBookingVO.setStockUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)).trim());
                orderBookingVO.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE_CAPS))));
                if (c.getString(c.getColumnIndex(STOCK_CHECKED)) != null && c.getString(c.getColumnIndex(STOCK_CHECKED)).equalsIgnoreCase("Y")) {
                    orderBookingVO.setItemSelected(true);
                }
                if (c.getInt(c.getColumnIndex(COLUMN_STOCK_QTY_CAPS)) > 0) {
                    orderBookingVO.setItemSelected(true);
                    orderBookingVO.setCheckBoxEnabled(false);
                }
                orderBookingList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingList;
    }

    /**
     * Delete product if user want to delete based on below params
     *
     * @param sfaDatabase      db instance
     * @param prefDistrcode    distr code
     * @param prefSalesmancode salesman code
     * @param prefRoutecode    current route code
     * @param retrCode         current Retailer Code
     */
    void deleteExistStockTakeProductById(BaseDB sfaDatabase, String prefDistrcode, String prefSalesmancode,
                                         String prefRoutecode, String retrCode, OrderBookingVO bookingVO) {

        String[] deleteArgs = {prefDistrcode, prefSalesmancode, prefRoutecode, retrCode, bookingVO.getProdCode(), bookingVO.getProdBatchCode(), "N"};

        sfaDatabase.getDb().delete(TABLE_STOCK_TAKE, UPDATE_CHECK_DC_SC_RC_RTLC_PC_U, deleteArgs);
        sfaDatabase.closeDb();

    }

    List<OrderBookingVO> fetchPreviousOrderDetails(SFADatabase baseDB, String cmpCode, String distrCode, String orderNo) {
        List<OrderBookingVO> previousOrders = new ArrayList<>();
        SQLiteDatabase database = baseDB.getReadableDatabase();
        Cursor c = database.rawQuery(QUERY_SELECT_PREVIOUS_ORDER_DETAILS, new String[]{cmpCode, distrCode, orderNo});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                OrderBookingVO previousOrder = new OrderBookingVO();
                previousOrder.setOrderNo(c.getString(c.getColumnIndex(ORDER_NO)));
                previousOrder.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE))));
                previousOrder.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                previousOrder.setProdBatchCode(c.getString(c.getColumnIndex(PROD_BATCH_CODE)));
                previousOrder.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                previousOrder.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY)));
                previousOrders.add(previousOrder);
            }
            c.close();
        }
        return previousOrders;
    }


    void updateRetailerMenuReasonToEmpty(SFADatabase sfaDatabase, String distrCode,
                                         String salesmanCode, String routeCode, String retlrCode) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_MENU_REASON, "");
        String update = "distrCode= ? and salesmanCode= ? and routeCode= ? and customerCode= ?";
        String[] updateArgs = {distrCode, salesmanCode, routeCode, retlrCode};
        sfaDatabase.getDb().update(TABLE_RETAILER, values, update, updateArgs);

    }

    List<String> fetchMustsellProducts(SFADatabase sfaDatabase, String retailerCode, String tableName) {

        String brandQuery = getQueryMustSellProducts(tableName);
        List<String> brandsList = new ArrayList<>();
        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{retailerCode});

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                brandsList.add(c.getString(c.getColumnIndex(IDBColumns.COLUMN_PROD_CODE)).trim());
            }
            c.close();
        }
        return brandsList;

    }

    List<String> fetchFocusBrandProducts(SFADatabase sfaDatabase, String retailerCode, String tableName) {

        String brandQuery = getQueryFocusBrandProducts(tableName);

        List<String> brandsList = new ArrayList<>();
        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        Cursor c = database.rawQuery(brandQuery, new String[]{retailerCode});

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                brandsList.add(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
            }
            c.close();
        }

        return brandsList;

    }

    String getSalReturnWithRefInvoiceNo(SFADatabase sfaDatabase, String tableName, String salesmanCode,
                                        String routeCode, String retrCode, String referenceNo, boolean state) {

        String invoiceNo = "";
        String query;
        Cursor c = null;

        query = getQuerySalesReturnWithRefInvoiceNo(tableName, referenceNo, state);

        if (query != null && state) {

            if (referenceNo != null && !referenceNo.isEmpty()) {
                c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, salesmanCode, routeCode, retrCode, referenceNo, "N"});

            } else {
                c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, salesmanCode, routeCode, retrCode, "N"});

            }
        } else {

            if (tableName.equalsIgnoreCase(TABLE_PURCHASE_PARTIAL_RETURN) && referenceNo != null && !referenceNo.isEmpty() && query != null) {
                c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, referenceNo, "N"});

            } else if (tableName.equalsIgnoreCase(TABLE_PURCHASE_PARTIAL_RETURN) &&
                    referenceNo != null && referenceNo.isEmpty() &&
                    referenceNo.equalsIgnoreCase("") && query != null) {
                c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, "N"});

            }
        }

        if (c != null && c.getCount() > 0 && c.moveToFirst())
            invoiceNo = c.getString(c.getColumnIndex("invoiceNo"));
        if (c != null) {
            c.close();
        }
        return invoiceNo;
    }

    List<OrderBookingVO> getAllReceiptProducts(SFADatabase sfaDatabase, String invoiceNo) {
        SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
        String distr = sfaSharedPref.readString(PREF_DISTRCODE);
        String cmp = sfaSharedPref.readString(SFASharedPref.PREF_CMP_CODE);
        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        Cursor c = null;

        try {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ALL_RECEIPT_PRODUCTS, new String[]{invoiceNo, invoiceNo, cmp, distr, "Y"});
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                    orderBookingVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                    orderBookingVO.setOrderInvoiceNo(c.getString(c.getColumnIndex(INVOICE_NO)).trim());
                    orderBookingVO.setOrderDate(c.getString(c.getColumnIndex(COLUMN_INVOICE_DT)).trim());
                    orderBookingVO.setGrnDate(c.getString(c.getColumnIndex(GRN_DT)).trim());
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim());
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                    orderBookingVO.setPurchasePrice(c.getDouble(c.getColumnIndex(COLUMN_PURCH_PRICE)));
                    orderBookingVO.setNoOfItems(c.getInt(c.getColumnIndex(COLUMN_INVOICE_QTY)));
                    orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(PROD_BATCH_CODE)));

                    int tempCount = c.getInt(c.getColumnIndex(COLUMN_RECEIVED_QTY));
                    if (tempCount > 0) {
                        orderBookingVO.setReceivedQty(c.getDouble(c.getColumnIndex(COLUMN_RECEIVED_QTY)));
                    } else {
                        orderBookingVO.setReceivedQty(c.getDouble(c.getColumnIndex(COLUMN_INVOICE_QTY)));
                    }

                    orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE))));
                    orderBookingVO.setGrnStatus(c.getString(c.getColumnIndex(GRN_STATUS)));
                    orderBookingVO.setTax(c.getDouble(c.getColumnIndex(COLUMN_LINE_LEVEL_TAX_AMT)));
                    orderBookingVO.setDicountAmt(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_LINE_LEVEL_DISC_AMT))));

                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }
        return orderBookingList;

    }

    String getRouteName(SFADatabase baseDb, String cmpCode, String distrCode, String routeCode) {

        String routeName = "";

        Cursor c = baseDb.getDb().rawQuery(QUERY_SELECT_ROUTE_NAME, new String[]{cmpCode, distrCode, routeCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                routeName = c.getString(c.getColumnIndex(COLUMN_ROUTE_NAME));
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return routeName;
    }

    void updatePendingOrderStatus(SFADatabase sfaDatabase, String tableName,
                                  String status, String invNo) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLETE_FLAG, status);
        String appendAnd = APPEND_AND;
        String update = READABLE_INV_NO + " = ?";
        String[] updateArgs = {invNo};
        sfaDatabase.getDb().update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    void deleteExistOpeningStock(SFADatabase sfaDatabase, String prodCode) {

        String deleteOrderBooking = "ProdCode=? and upload=?";
        String[] deleteArgs = {prodCode, "N"};

        sfaDatabase.getDb().delete(TABLE_OPENING_STOCK, deleteOrderBooking, deleteArgs);
        sfaDatabase.closeDb();

    }

    boolean updateNewRetailerSign(SFADatabase sfaDatabase, String distCode, String salesmanCode,
                                  String routeCode, String retlrCode, String base64img) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_DIGITAL_SIGNATURE, base64img);
        String update = "distrCode= ? and salesmanCode= ? and routeCode= ? and customerCode= ?";
        String[] updateArgs = {distCode, salesmanCode, routeCode, retlrCode};
        long ack = sfaDatabase.getDb().update(TABLE_NEW_RETAILER, values, update, updateArgs);
        return ack == 1;
    }

    public boolean updatePOStatus(SFADatabase sfaDatabase, String invoiceNo) {

        ContentValues values = new ContentValues();
        values.put(CONFIRM_STATUS, "Y");
        String update = "invoiceNo= ?";
        String[] updateArgs = {invoiceNo};
        long ack = sfaDatabase.getDb().update(TABLE_PURCHASE_ORDER_BOOKING, values, update, updateArgs);
        return ack > 0;
    }

    public List<OrderBookingVO> getBillingProducts(SFADatabase sfaDatabase, String cmpCode, String distrCode, String salesmanCode, String routecode, String retrCode) {
        List<OrderBookingVO> orderBookingList = new ArrayList<>();
        Cursor c = null;
        try {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ALL_PRODUCT, new String[]{cmpCode});
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)).trim());
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim());
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)).trim());
                    orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)).trim());

                    orderBookingVO.setProductHierPathName(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_NAME)));
                    orderBookingVO.setProductHierPath(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                    orderBookingVO.setProdHierValName(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));

                    orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                    orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                    //orderBookingVO.setPrimaryDisc((c.getDouble(c.getColumnIndex(COLUMN_PRIMARY_DISC))));
//                    orderBookingVO.setOrderFreeQty(c.getInt(c.getColumnIndex(FREE_QTY)));
                    orderBookingVO.setNetWeight(c.getDouble(c.getColumnIndex(COLUMN_PRODUCT_NET_WGT)));
                    orderBookingVO.setWeightType(c.getString(c.getColumnIndex(COLUMN_PRODUCT_WGT_TYPE)));
//                    orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY)));

//                    orderBookingVO.setCgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(CGST_VALUE))));
//                    orderBookingVO.setCgstperc(c.getDouble(c.getColumnIndex(CGST_PERCENT)));
//                    orderBookingVO.setSgstPerc(c.getDouble(c.getColumnIndex(SGST_PERCENT)));
//                    orderBookingVO.setSgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(SGST_VALUE))));
//                    orderBookingVO.setUtgstPerc(c.getDouble(c.getColumnIndex(UTGST_PERCENT)));
//                    orderBookingVO.setUtgstValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(UTGST_VALUE))));
//                    orderBookingVO.setIgstPerc(c.getDouble(c.getColumnIndex(IGST_PERCENT)));
//                    orderBookingVO.setIgstvalue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(IGST_VALUE))));

                    //orderBookingVO.setBrandName(c.getString(c.getColumnIndex(COLUMN_BRAND_NAME)));
                    //orderBookingVO.setBrandCode(c.getString(c.getColumnIndex(COLUMN_BRAND_CODE)));
                    orderBookingVO.setStockInHand(c.getInt(c.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));
                    orderBookingVO.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));
                    orderBookingVO.setStockCheckQty(c.getDouble(c.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));
//                    orderBookingVO.setCategory(c.getString(c.getColumnIndex(CATEGORY_CAPS)));
                    orderBookingVO.setUomGroupId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
                    orderBookingVO.setBaseUOM(c.getString(c.getColumnIndex(COLUMN_BASE_UOM)));

//
//                    if (null != c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE)) && !c.getString(c.getColumnIndex(COLUMN_ORDER_VALUE)).equals("")) {
//                        orderBookingVO.setOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_ORDER_VALUE))));
//                    }
                    orderBookingVO.setConversionFactor(getConversionFactor(sfaDatabase, orderBookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));

                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }
        return orderBookingList;
    }

    public List<PendingCollection> getCustomerCollectionAmount(SFADatabase sfaDatabase, String cmpCode, String distrCode, String salesmanCode, String routeCode, String customerCode) {
        List<PendingCollection> pendingCollections = new ArrayList<>();
        Cursor c = null;
        try {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_PENDING_AMOUNT, new String[]{cmpCode, distrCode, salesmanCode, customerCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    PendingCollection pendingCollection = new PendingCollection();
                    pendingCollection.setBillDate(c.getString(c.getColumnIndex(COLUMN_INVOICE_DT)));
                    pendingCollection.setBillNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NUM)));
                    pendingCollection.setPendingAmount(c.getDouble(c.getColumnIndex(COLUMN_BALANCE_OS)));

                    pendingCollections.add(pendingCollection);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }
        return pendingCollections;
    }

    /*
     * Return all the products based on distributor and salesman code
     * */
    public List<OrderBookingVO> getAppProducts(SFADatabase sfaDatabase, String cmpCode, String distrCode, String customerCode) {

        List<OrderBookingVO> bookingVOList = new ArrayList<>();

        String query = "SELECT  p.cmpcode, \n" +
                "       p.prodcode, \n" +
                "       pbm.prodbatchcode, \n" +
                "       p.prodname, \n" +
                "       p.producthierpathname, \n" +
                "       p.prodhierlastvalcode, \n" +
                "       p.producthierpathcode, \n" +
                "       p.prodWgtType, \n" +
                "       p.prodNetWgt, \n" +
                "       p.prodGrossWgt, \n" +
                "       um.uomcode  AS baseUom, \n" +
                "       pbm.sellprice, \n" +
                "       pbm.purchaseprice, \n" +
                "       pbm.mrp, \n" +
                "       pbm.sellRateWithTax, \n" +
                "       st.availqty AS loadstock, \n" +
                "       st.resvsaleableqty, \n" +
                "       st.saleableqty\n" +
                "FROM   m_product_master p \n" +
                "       INNER JOIN m_product_batch_master pbm \n" +
                "               ON pbm.cmpcode = p.cmpcode \n" +
                "                  AND pbm.prodcode = p.prodcode \n" +
                "       INNER JOIN m_product_uom_master um \n" +
                "               ON um.cmpcode = p.cmpcode \n" +
                "                  AND um.prodcode = p.prodcode \n" +
                "                  AND um.baseuom = 'Y' \n" +
                "                  AND um.defaultuom = 'Y' \n" +
                "       INNER JOIN m_stockonhand st \n" +
                "               ON st.cmpcode = p.cmpcode \n" +
                "                  AND st.prodcode = p.prodcode \n" +
                "                  AND st.ProdBatchCode = pbm.ProdBatchCode \n" +
                "                  AND st.availQty > 0 \n" +
                "       Left JOIN m_customerproductmapping cpm \n" +
                "               ON cpm.cmpcode = p.cmpcode \n" +
                "                  AND cpm.prodcode = p.prodcode \n" +
                "\t\t  AND cpm.customercode = ? \n" +
                "\t\t  AND cpm.prodType = 'Billing' \n" +
                "\t\t  AND cpm.allowEdit='Y'\n" +
                "WHERE  p.cmpcode = ? \n" +
                "       AND pbm.distrcode = ? \n" +
                "       AND cpm.prodcode IS NULL COLLATE nocase ";

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(query, new String[]{customerCode, cmpCode, distrCode});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                OrderBookingVO bookingVO = new OrderBookingVO();
                bookingVO.setCmpCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_CMP_CODE)));
                bookingVO.setDistrCode(distrCode);
                bookingVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                bookingVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE)));
                bookingVO.setProdName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NAME)));
                bookingVO.setSellPrice(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                bookingVO.setMrp(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_MRP_CAPS))));

                bookingVO.setProductHierPathName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_NAME)));
                bookingVO.setProductHierPath(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                bookingVO.setProdHierValName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                bookingVO.setBaseUOM(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
                bookingVO.setWeightType(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_WGT_TYPE)));
                bookingVO.setNetWeight(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NET_WGT)));
//                bookingVO.setGrossWeight(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_PROD_GROSS_WGT)));

                Integer stockQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)) -
                        cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_SALEABLE_QTY));
                if (stockQty <= 0) {
                    stockQty = 0;
                }
                bookingVO.setStockInHand(stockQty);
                bookingVO.setAvailQty(stockQty);
                bookingVO.setStockCheckQty(stockQty);
                bookingVO.setConversionFactor(getConversionFactor(sfaDatabase, bookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));
//                bookingVO.setSellRateWithTax(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_SELL_RATE_WITH_TAX))));
                if (stockQty > 0) {
                    bookingVOList.add(bookingVO);
                }

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }
        return bookingVOList;
    }

    void updateRetailerUploadStatus(SFADatabase sfaDatabase, String collectionsUploaded, String state,
                                    String cmpCode, String distrCode, String customerCode) {

        ContentValues values = new ContentValues();
        values.put(collectionsUploaded, state);

        String[] updateArgs = {cmpCode, distrCode, customerCode};
        String tag = "cmpCode = ? and distrCode = ? and customerCode = ?";

        sfaDatabase.getDb().update(TABLE_CUSTOMER, values, tag, updateArgs);

    }

    public void insertVoiceHeader(BaseDB baseDB, BillingInfoVO billingInfoVO) {
        try {
            SQLiteDatabase db = baseDB.getDb();

            if (!db.isOpen())
                db = baseDB.getDb();

            ContentValues values = new ContentValues();
            values.put(COLUMN_CMP_CODE, billingInfoVO.getCmpCode());
            values.put(COLUMN_DISTR_CODE, billingInfoVO.getDistrCode());
            values.put(COLUMN_INVOICE_NUM, billingInfoVO.getInvoiceNo());
            values.put(COLUMN_ORDER_NO, billingInfoVO.getOrderNo());
            values.put(COLUMN_GODOWN_CODE, billingInfoVO.getGodownCode());
            values.put(COLUMN_INVOICE_PATTERN, billingInfoVO.getInvoicePattern());
            values.put(COLUMN_SALESMAN_CODE, billingInfoVO.getSalesmanCode());
            values.put(COLUMN_ROUTE_CODE, billingInfoVO.getRouteCode());
            values.put(COLUMN_CUSTOMER_CODE, billingInfoVO.getCustomerCode());
            values.put(COLUMN_CUSTOMER_SHIP_CODE, billingInfoVO.getCustomerShipCode());
            values.put(COLUMN_INVOICE_DT, billingInfoVO.getInvoiceDt());
            values.put(COLUMN_SHIP_DT, billingInfoVO.getShipDt());
            values.put(COLUMN_DELIVERY_DT, billingInfoVO.getDeliveryDt());
            values.put(COLUMN_INVOICE_TYPE, billingInfoVO.getInvoiceType());
            values.put(COLUMN_INVOICE_MODE, billingInfoVO.getInvoiceMode());
            values.put(COLUMN_PAYMENT_MODE, billingInfoVO.getPaymentMode());
            values.put(COLUMN_INVOICE_STATUS, billingInfoVO.getInvoiceStatus());
            values.put(COLUMN_REMARKS, billingInfoVO.getRemarks());
            values.put(COLUMN_MOD_DT, System.currentTimeMillis());
            values.put(COLUMN_COMPLETE_FLAG, "N");
            values.put(UPLOAD_FLAG, "N");

            // Inserting Row
            long ack = db.insert(IDBColumns.TABLE_BILL_INVOIC_HEADER, null, values);


        } finally {

            baseDB.closeDb();
        }

    }

    public String getGoDownCode(SFADatabase sfaDatabase, String cmpCode, String distrCode) {
        String goDownCode = "";
        String query = "SELECT * FROM " + TABLE_STOCK_ON_HAND + " where " + COLUMN_CMP_CODE + " =? and " + COLUMN_DISTR_CODE + " =?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode});
        if (isCursorTrue(c)) {
            do {
                goDownCode = c.getString(c.getColumnIndex(COLUMN_GODOWN_CODE));
            } while (c.moveToNext());

        }
        closeCursor(c);

        return goDownCode;
    }

    private boolean isCursorTrue(Cursor cursorPending) {
        return cursorPending != null && cursorPending.getCount() > 0 && cursorPending.moveToFirst();
    }

    public BillingInfoVO getInvoiceHeader(BaseDB baseDB, String invoiceNo) {
        baseDB.openWritableDb();
        BillingInfoVO billingInfoVO = new BillingInfoVO();
        String query = "SELECT ih.invoiceNo,\n" +
                "       ih.salesmanCode,\n" +
                "       ih.routeCode,\n" +
                "       ih.customerCode,\n" +
                "       ih.customerShipCode,\n" +
                "       ih.invoiceDt,\n" +
                "       ih.shipDt,\n" +
                "       ih.invoiceMode,\n" +
                "       ih.remarks,\n" +
                "       route.routeName,\n" +
                "       salesman.salesmanName,\n" +
                "       customer.customerName,\n" +
                "       cusship.customerShipAddr1,\n" +
                "       cusship.customerShipAddr2,\n" +
                "       cusship.customerShipAddr3,\n" +
                "       gst.gstStateName\n" +
                "FROM t_invoiceheader ih\n" +
                "INNER JOIN m_route route ON route.routeCode = ih.routeCode\n" +
                "INNER JOIN m_salesman_master salesman ON salesman.salesmanCode= ih.salesmanCode\n" +
                "INNER JOIN t_customer customer ON customer.customerCode=ih.customerCode\n" +
                "INNER JOIN t_customershipaddress cusship ON cusship.customerShipCode = ih.customerShipCode\n" +
                "INNER JOIN m_gstStateMaster gst ON gst.gstStateCode = cusship.gstStateCode\n" +
                "WHERE ih.invoiceNo='" + invoiceNo + "'";

        try {
            Cursor cm = baseDB.getDb().rawQuery(query, null);

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
                billingInfoVO.setInvoiceNo(cm.getString(cm.getColumnIndex(COLUMN_INVOICE_NUM)));
                billingInfoVO.setSalesmanCode(cm.getString(cm.getColumnIndex(COLUMN_SALESMAN_CODE)));
                billingInfoVO.setRouteCode(cm.getString(cm.getColumnIndex(COLUMN_ROUTE_CODE)));
                billingInfoVO.setCustomerCode(cm.getString(cm.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                billingInfoVO.setCustomerShipCode(cm.getString(cm.getColumnIndex(COLUMN_CUSTOMER_SHIP_CODE)));
                billingInfoVO.setInvoiceDt(cm.getString(cm.getColumnIndex(COLUMN_INVOICE_DT)));
                billingInfoVO.setShipDt(cm.getString(cm.getColumnIndex(COLUMN_SHIP_DT)));
                billingInfoVO.setInvoiceMode(cm.getString(cm.getColumnIndex(COLUMN_INVOICE_MODE)));
                billingInfoVO.setRemarks(cm.getString(cm.getColumnIndex(COLUMN_REMARKS)));
                billingInfoVO.setRouteName(cm.getString(cm.getColumnIndex(COLUMN_ROUTE_NAME)));
                billingInfoVO.setSalesmanName(cm.getString(cm.getColumnIndex(COLUMN_SALESMAN_NAME)));
                billingInfoVO.setCustomerName(cm.getString(cm.getColumnIndex(COLUMN_CUSTOMER_NAME)));
                billingInfoVO.setCustomerAddress(cm.getString(cm.getColumnIndex(COLUMN_CUSTOMER_SHIP_ADDR1)) + " " + cm.getString(cm.getColumnIndex(COLUMN_CUSTOMER_SHIP_ADDR2))
                        + " " + cm.getString(cm.getColumnIndex(GST_STATE_NAME)) + " " + cm.getString(cm.getColumnIndex(COLUMN_CUSTOMER_SHIP_ADDR3)));

            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getInvoiceHeader: " + e.getMessage(), e);
        }
        return billingInfoVO;
    }

    public void deleteInvoiceHeader(BaseDB baseDB, String cmpCode, String distrCode, String invoiceNum) {
        SQLiteDatabase db = baseDB.getDb();
        db.execSQL("DELETE FROM " + TABLE_BILL_INVOIC_HEADER + " WHERE cmpCode ='" + cmpCode + "' AND distrCode = '" + distrCode + "' AND invoiceNo = '" + invoiceNum + "'");
        baseDB.closeDb();
    }

    public List<OrderBookingVO> getBilledProductsFromDataBase(SFADatabase sfaDatabase, String distCode,
                                                              String salesmanCode, String routeCode, String
                                                                      retailerCode, String invoiceNo,
                                                              String status) {
        List<OrderBookingVO> bookingVOList = new ArrayList<>();

        String QUERY_SELECT_ALL_PRODUCT = "SELECT p.cmpCode,p.prodcode, \n" +
                "       pbm.prodbatchcode, \n" +
                "       p.prodname, \n" +
                "       p.producthierpathname,\n" +
                "       p.prodhierlastvalcode,\n" +
                "       p.prodWgtType, \n" +
                "       p.prodNetWgt, \n" +
                "       p.prodGrossWgt, \n" +
                "       p.productHierPathCode,\n" +
                "       um.uomcode AS baseUom,\n" +
                "       pbm.sellprice, \n" +
                "       pbm.purchaseprice, \n" +
                "       pbm.mrp, \n" +
                "       inv.totalOrdQty, \n" +
                "       inv.totalInvoiceQty, \n" +
                "       inv.freeQty, \n" +
                "       inv.invoiceQty1, \n" +
                "       inv.uomCode1, \n" +
                "       inv.invoiceQty2, \n" +
                "       inv.uomCode2, \n" +
                "       inv.actualSellRate, \n" +
                "       inv.grossAmt, \n" +
                "       inv.dBDiscAmt, \n" +
                "       inv.dBDiscPerc, \n" +
                "       inv.inputStr, \n" +
                "       inv.parentProdCode, \n" +
                "       inv.parentProdBatchCode, \n" +
                "       inv.netAmt, \n" +
                "       inv.sellRate as editRate, \n" +
                "       st.availQty AS loadstock,\n" +
                "       st.saleableqty \n" +
                "FROM   m_product_master p \n" +
                "       INNER JOIN m_product_batch_master pbm \n" +
                "               ON pbm.cmpcode = p.cmpcode \n" +
                "                  AND pbm.prodcode = p.prodcode \n" +
                "       INNER JOIN m_product_uom_master um \n" +
                "              ON um.cmpcode = p.cmpcode \n" +
                "                 AND um.prodcode = p.prodcode \n" +
                "                 AND um.baseuom = 'Y' \n" +
                "                 AND um.defaultuom = 'Y' \n" +
                "       INNER JOIN m_stockonhand st \n" +
                "              ON st.cmpcode = p.cmpcode \n" +
                "                 AND st.prodcode = p.prodcode \n" +
                "                 AND st.prodbatchcode = pbm.prodbatchcode \n" +
                "       INNER JOIN t_invoicedetails inv \n" +
                "              ON inv.cmpcode = p.cmpcode \n" +
                "                 AND inv.prodcode = p.prodcode \n" +
                "                 AND inv.prodbatchcode = pbm.prodbatchcode \n" +
                "WHERE  inv.cmpcode = ? \n" +
                "       AND inv.distrcode = ?  and inv.salesmanCode=? and inv.routeCode=? and inv.customerCode=? and inv.invoiceNo=? and completeFlag=? and uploadFlag='N' COLLATE nocase";


        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ALL_PRODUCT, new String[]{cmpCode, distrCode, salesmanCode, routeCode, retailerCode, invoiceNo, status});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                OrderBookingVO bookingVO = new OrderBookingVO();
                bookingVO.setCmpCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_CMP_CODE)));
                bookingVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                bookingVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE)));

//                bookingVO.setParentProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PARENT_PROD_CODE)));
//
//                bookingVO.setParentProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PARENT_PROD_BATCH_CODE)));
                bookingVO.setProdName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NAME)));
                bookingVO.setSellPrice(BigDecimal.valueOf(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                bookingVO.setMrp(BigDecimal.valueOf(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_MRP_CAPS))));

                bookingVO.setProductHierPathName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_NAME)));
                bookingVO.setProductHierPath(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                bookingVO.setProdHierValName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                bookingVO.setBaseUOM(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
                bookingVO.setStockInHand(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));
                bookingVO.setAvailQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));
                bookingVO.setStockCheckQty(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));

//                bookingVO.setTotOrderQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_TOTAL_ORD_QTY)));
//                bookingVO.setTotInvoiceQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));
//                bookingVO.setFreeQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(FREE_QTY)));
//                bookingVO.setQuantity(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_INVOICE_QTY1)));
//                bookingVO.setQuantity2(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_INVOICE_QTY2)));
//                bookingVO.setUomId(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_UOM_CODE1)));
//                bookingVO.setUomId2(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_UOM_CODE2)));
//                bookingVO.setlGrossAmt(new BigDecimal(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_GROSS_AMT))));
//                bookingVO.setDbDiscountAmt(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_DB_DISC_AMT)));
//                bookingVO.setDbDiscountPerc(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_DB_DISC_PERC)));
//                bookingVO.setActualSellRate(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_ACTUAL_SELL_RATE)));
//                bookingVO.setInputStr(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_INPUT_STR)));
//                bookingVO.setNetAmount(new BigDecimal(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_NET_AMT))));
//                bookingVO.setEditRate(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex("editRate")));
//                bookingVO.setWeightType(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_WGT_TYPE)));
//                bookingVO.setNetWeight(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NET_WGT)));
//                bookingVO.setGrossWeight(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_PROD_GROSS_WGT)));

                bookingVO.setConversionFactor(getConversionFactor(sfaDatabase, bookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));
                bookingVO.setTotQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));
                bookingVO.setOrderValue(new BigDecimal(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_GROSS_AMT))));
                bookingVO.setPrimDiscOrderValue(BigDecimal.valueOf(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_GROSS_AMT))));

                bookingVOList.add(bookingVO);

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }
        return bookingVOList;
    }

    public List<OrderBookingVO> fetchBillingSummaryProduct(SFADatabase sfaDatabase, String cmpCode,
                                                           String distCode, String salesmanCode,
                                                           String routeCode, String retlrCode,
                                                           String invoiceNo) {

        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        String query = " SELECT p.prodcode, \n" +
                "       o.prodbatchcode, \n" +
                "       p.prodname, \n" +
                "       p.hsnCode, \n" +
                "       p.producthierpathcode,\n" +
                "       p.prodhierlastvalcode,\n" +
                "       p.prodWgtType, \n" +
                "       p.prodNetWgt, \n" +
                "       p.prodGrossWgt, \n" +
                "       p.productHierPathCode,\n" +
                "       um.uomcode AS baseUom,\n" +
                "       pb.sellprice, \n" +
                "       pb.purchaseprice, \n" +
                "       pb.mrp, \n" +
                "       p.prodshortname, \n" +
                "    o.parentProdCode,\n" +
                "    o.parentProdBatchCode,       o.invoiceqty1, \n" +
                "       o.invoiceqty2, \n" +
                "       o.uomcode1, \n" +
                "       o.uomcode2, \n" +
                "       o.netamt, \n" +
                "       o.schdiscamt, \n" +
                "       o.grossamt, \n" +
                "       o.cgstamt, \n" +
                "       o.cgstperc, \n" +
                "       o.sgstperc, \n" +
                "       o.sgstamt, \n" +
                "       o.igstperc, \n" +
                "       o.igstamt, \n" +
                "       o.utgstperc, \n" +
                "       o.utgstvalue, \n" +
                "       o.cessperc, \n" +
                "       o.cessvalue, \n" +
                "       o.taxamt, \n" +
                "       o.sellrate, \n" +
                "       o.actualSellRate, \n" +
                "       o.totalInvoiceQty, \n" +
                "       o.totalOrdQty, \n" +
                "       o.freeQty, \n" +
                "       o.dbDiscAmt, \n" +
                "       o.dbDiscPerc, \n" +
                "       o.cashDiscAmt, \n" +
                "       o.cashDiscPerc, \n" +
                "       o.invoiceNo, \n" +
                "       o.freeQty, \n" +
                "       o.mrp, \n" +
                "       o.grossamt, \n" +
                "       iv.totCrNoteAmt,  \n" +
                "       iv.roundOffAmt  \n" +
                "FROM   m_product_master p \n" +
                "       INNER JOIN m_product_batch_master pb \n" +
                "               ON pb.prodcode = p.prodcode \n" +
                "                  AND pb.cmpcode = p.cmpcode \n" +
                "       INNER JOIN m_product_uom_master um \n" +
                "              ON um.cmpcode = p.cmpcode \n" +
                "                 AND um.prodcode = p.prodcode \n" +
                "                 AND um.baseuom = 'Y' \n" +
                "                 AND um.defaultuom = 'Y' \n" +
                "       INNER JOIN t_invoicedetails o \n" +
                "               ON o.prodcode = p.prodcode \n" +
                "                  AND o.cmpcode = p.cmpcode \n" +
                "                  AND o.distrcode = pb.distrcode \n" +
                "                  AND o.prodbatchcode = pb.prodbatchcode \n" +
                "       INNER JOIN t_invoiceheader iv \n" +
                "                  ON iv.cmpcode = p.cmpcode \n" +
                "                  AND iv.distrcode = pb.distrcode \n" +
                "                  AND iv.invoiceNo = ?\n" +
                "WHERE o.cmpcode = ?\n" +
                "       AND o.distrcode = ? \n" +
                "       AND o.salesmancode = ?\n" +
                "       AND o.routecode = ? \n" +
                "       AND o.customercode = ?\n" +
                "       AND o.invoiceno =?";
//                "       AND o.uploadflag = 'N' ";

        boolean isCessTaxApplicable = false;
        if (sfaDatabase.getStateNameByCode(sfaDatabase.getDistStateCode()).equalsIgnoreCase("kerala") &&
                !sfaDatabase.isCustomerRegistered(retlrCode).equalsIgnoreCase("R")) {
            isCessTaxApplicable = true;
        }

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{invoiceNo, cmpCode, distCode, salesmanCode, routeCode, retlrCode, invoiceNo});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim());
//                orderBookingVO.setHsnCode(c.getString(c.getColumnIndex(COLUMN_PROD_HSN_CODE)).trim());
                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)).trim());
                orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                orderBookingVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
//                orderBookingVO.setParentProdCode(c.getString(c.getColumnIndex(COLUMN_PARENT_PROD_CODE)));
//                orderBookingVO.setParentProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PARENT_PROD_BATCH_CODE)));
                orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                orderBookingVO.setProductHierPathName(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                orderBookingVO.setProductHierPath(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                orderBookingVO.setProdHierValName(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                orderBookingVO.setBaseUOM(c.getString(c.getColumnIndex(COLUMN_BASE_UOM)));
                orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
//                orderBookingVO.setTotInvoiceQty(c.getInt(c.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));
//                orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));
//                orderBookingVO.setTotOrderQty(c.getInt(c.getColumnIndex(COLUMN_TOTAL_ORD_QTY)));

                orderBookingVO.setSchemeAmount(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SCH_DISC_AMT))));
                orderBookingVO.setNetAmount(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_NET_AMT))));
                orderBookingVO.setTotalAmount(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_GROSS_AMT))));
                orderBookingVO.setlGrossAmt(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_GROSS_AMT))));

                orderBookingVO.setCgstvalue(new BigDecimal(c.getString(c.getColumnIndex(CGST_AMT))));
                orderBookingVO.setCgstperc(c.getDouble(c.getColumnIndex(CGST_PERCENT)));
                orderBookingVO.setSgstPerc(c.getDouble(c.getColumnIndex(SGST_PERCENT)));
                orderBookingVO.setSgstValue(new BigDecimal(c.getString(c.getColumnIndex(SGST_AMT))));
                orderBookingVO.setUtgstPerc(c.getDouble(c.getColumnIndex(UTGST_PERCENT)));
                orderBookingVO.setUtgstValue(new BigDecimal(c.getString(c.getColumnIndex(UTGST_VALUE))));

                if (isCessTaxApplicable) {
//                    orderBookingVO.setCessPerc(c.getDouble(c.getColumnIndex(CESS_PERCENT)));
//                    orderBookingVO.setCessValue(new BigDecimal(c.getString(c.getColumnIndex(CESS_VALUE))));
                }
                orderBookingVO.setIgstPerc(c.getDouble(c.getColumnIndex(IGST_PERCENT)));
                orderBookingVO.setIgstvalue(new BigDecimal(c.getString(c.getColumnIndex(IGST_AMT))));
//                orderBookingVO.setDbDiscountPerc(c.getDouble(c.getColumnIndex(COLUMN_DB_DISC_PERC)));
//                orderBookingVO.setWeightType(c.getString(c.getColumnIndex(COLUMN_PROD_WGT_TYPE)));
//                orderBookingVO.setNetWeight(c.getDouble(c.getColumnIndex(COLUMN_PROD_NET_WGT)));
//                orderBookingVO.setGrossWeight(c.getDouble(c.getColumnIndex(COLUMN_PROD_GROSS_WGT)));
                orderBookingVO.setConversionFactor(getConversionFactor(sfaDatabase, orderBookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));

                orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_INVOICE_QTY1)));
//                orderBookingVO.setQuantity2(c.getDouble(c.getColumnIndex(COLUMN_INVOICE_QTY2)));
//                orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE1)));
//                orderBookingVO.setUomId2(c.getString(c.getColumnIndex(COLUMN_UOM_CODE2)));
                orderBookingVO.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_GROSS_AMT))));
//                orderBookingVO.setPrimDiscOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SCH_DISC_AMT))));
                orderBookingVO.setPrimDiscOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_GROSS_AMT))));

                orderBookingVO.setTax(c.getDouble(c.getColumnIndex(COLUMN_TAX_AMT)));
                orderBookingVO.setFreeQty(c.getInt(c.getColumnIndex(FREE_QTY)));
//                orderBookingVO.setDbDiscountAmt(c.getDouble(c.getColumnIndex(COLUMN_DB_DISC_AMT)));
//                orderBookingVO.setActualSellRate(c.getDouble(c.getColumnIndex(COLUMN_ACTUAL_SELL_RATE)));
//                orderBookingVO.setEditRate(c.getDouble(c.getColumnIndex(COLUMN_SELL_RATE)));
//                orderBookingVO.setSellRate(c.getString(c.getColumnIndex(COLUMN_SELL_RATE)));
//                orderBookingVO.setCashDiscAmt(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_CASH_DISC_AMT))));
//                orderBookingVO.setCashDiscPerc(c.getDouble(c.getColumnIndex(COLUMN_CASH_DISC_PERC)));
//                orderBookingVO.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NO)));
//                orderBookingVO.setCrNoteAmt(c.getDouble(c.getColumnIndex(COLUMN_TOT_CR_NOTE_AMT)));
//                orderBookingVO.setRoundOffAmt(c.getDouble(c.getColumnIndex(COLUMN_ROUND_OFF_AMT)));
                orderBookingList.add(orderBookingVO);

//                bookingVO.setStockInHand(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));
//                bookingVO.setAvailQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));
//                bookingVO.setStockCheckQty(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)));


            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingList;

    }

    public void updateInvoiceHeaderDataInDB(SFADatabase sfaDatabase, BillingInfoVO billingInfoVO, int totCount) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TOT_GROSS_AMT, billingInfoVO.getTotGrossAmt());
        values.put(COLUMN_TOT_ADDITION, billingInfoVO.getTotAddition());
        values.put(COLUMN_TOT_DEDUCTION, billingInfoVO.getTotDeduction());
        values.put(COLUMN_TOT_SCH_DISC_AMT, billingInfoVO.getTotSchDiscAmt());
        values.put(COLUMN_TOT_TAX_AMT, billingInfoVO.getTotAddition());
        values.put(COLUMN_TOT_NET_AMT, billingInfoVO.getTotNetAmt());
        values.put(COLUMN_BALANCE_AMT, billingInfoVO.getTotNetAmt());
        values.put(COLUMN_TOT_DB_DISC_AMT, billingInfoVO.getTotDBDiscAmt());
        values.put(COLUMN_LINE_COUNT, totCount);
        values.put(COLUMN_REMARKS, billingInfoVO.getRemarks());
        values.put(COLUMN_ROUND_OFF_AMT, billingInfoVO.getRoundOffAmt());
        String update = "cmpCode=? and distrCode=? and salesmanCode=? and routeCode=? and customerCode=? and invoiceNo=?";
        String[] updateArgs = {billingInfoVO.getCmpCode(), billingInfoVO.getDistrCode(), billingInfoVO.getSalesmanCode(),
                billingInfoVO.getRouteCode(), billingInfoVO.getCustomerCode(), billingInfoVO.getInvoiceNo()};
        sfaDatabase.getDb().update(TABLE_BILL_INVOIC_HEADER, values, update, updateArgs);
    }

    public OrderBookingVO getBillSummaryFromInvoiceHeader(BaseDB baseDB, String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String customerShipCode, String invoiceNo) {
        baseDB.openWritableDb();
        OrderBookingVO orderBookingVO = new OrderBookingVO();
        String query = "SELECT invoiceDt,\n" +
                "       totTaxAmt,\n" +
                "       totDeduction,\n" +
                "       totGrossAmt,\n" +
                "       totDBDiscAmt,\n" +
                "       totCashDiscAmt,\n" +
                "       roundOffAmt,\n" +
                "       totNetAmt\n" +
                "FROM t_invoiceheader\n" +
                "WHERE cmpCode=?\n" +
                "  AND distrCode=?\n" +
                "  AND salesmanCode=?\n" +
                "  AND routeCode=?\n" +
                "  AND customerCode=?\n" +
                "  AND invoiceNo=?\n" +
                "GROUP BY invoiceNo";

        try {

            Cursor cm = baseDB.getDb().rawQuery(query, new String[]{cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
                orderBookingVO.setTax(cm.getDouble(cm.getColumnIndex(COLUMN_TOT_TAX_AMT)));
                orderBookingVO.setNetAmount(new BigDecimal(cm.getString(cm.getColumnIndex(COLUMN_TOT_NET_AMT))));
                orderBookingVO.setSchemeAmount(new BigDecimal(cm.getString(cm.getColumnIndex(COLUMN_TOT_DEDUCTION))));
//                orderBookingVO.setTotGrossAmt(new BigDecimal(cm.getString(cm.getColumnIndex(COLUMN_TOT_GROSS_AMT))));
//                orderBookingVO.setDbDiscountAmt(cm.getDouble(cm.getColumnIndex(COLUMN_TOT_DB_DISC_AMT)));
//                orderBookingVO.setRoundOffAmt(cm.getDouble(cm.getColumnIndex(COLUMN_ROUND_OFF_AMT)));
//                orderBookingVO.setCashDiscAmt(new BigDecimal(cm.getString(cm.getColumnIndex(COLUMN_TOT_CASH_DISC_AMT))));


            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getBillSummaryFromInvoiceHeader: " + e.getMessage(), e);
        }
        return orderBookingVO;
    }

    void updateCashDiscAmt(BaseDB baseDB, String cmpCode, String distrCode, String salesmanCode, String routeCode,
                           String retlrCode, String invoiceNo, String totCashDiscAmt, String latitude,
                           String longitude, String completeFlag) {

        long endTime = Globals.getOurInstance().getEndTime();
        if (endTime == 0) {
            endTime = new Date().getTime();
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_TOT_CASH_DISC_AMT, totCashDiscAmt);
        values.put(COLUMN_START_TIME, Globals.getOurInstance().getStartTime());
        values.put(COLUMN_END_TIME, String.valueOf(endTime));
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        values.put(COLUMN_COMPLETE_FLAG, completeFlag);

        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_SALESMAN_CODE + appendAnd + COLUMN_ROUTE_CODE + appendAnd + COLUMN_CUSTOMER_CODE + appendAnd + COLUMN_INVOICE_NUM + " = ?";

        String[] updateArgs = {cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo};

        baseDB.getDb().update(TABLE_BILL_INVOIC_HEADER, values, update, updateArgs);
        baseDB.closeDb();
    }

    public List<OrderBookingVO> fetchTotalInvoiceQty(SFADatabase sfaDatabase, String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo) {
        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        String query = "select\n" +
                "   invoice.prodCode,\n" +
                "   invoice.ProdBatchCode,\n" +
                "   invoice.totalInvoiceQty,\n" +
                "   invoice.freeQty,\n" +
                "   stock.availQty, \n" +
                "   stock.resvSaleableQty \n" +
                "from\n" +
                "   t_invoicedetails invoice \n" +
                "   inner join\n" +
                "      m_stockOnHand stock \n" +
                "      on invoice.ProdBatchCode = stock.ProdBatchCode \n" +
                "where\n" +
                "   invoice.cmpCode = ? \n" +
                "   and invoice.distrCode = ? \n" +
                "   and invoice.salesmanCode = ? \n" +
                "   and invoice.routeCode = ? \n" +
                "   and invoice.customerCode = ? \n" +
                "   and invoice.invoiceno = ?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim());
                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)).trim());
//                orderBookingVO.setTotInvoiceQty(c.getInt(c.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));
                orderBookingVO.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                orderBookingVO.setFreeQty(c.getInt(c.getColumnIndex(FREE_QTY)));
//                orderBookingVO.setResvSaleableQty(c.getInt(c.getColumnIndex(COLUMN_RESV_SALEABLE_QTY)));

                orderBookingList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }

        return orderBookingList;
    }

    void updateStockOnHandQty(BaseDB baseDB, String cmpCode, String distrCode, boolean status, OrderBookingVO orderBookingVO) {

//        int availableQty = orderBookingVO.getAvailQty() - orderBookingVO.getTotInvoiceQty();
//        int resSaleableQty = orderBookingVO.getResvSaleableQty() + orderBookingVO.getTotInvoiceQty();

        ContentValues values = new ContentValues();
//        values.put(COLUMN_LAST_ORD_QTY, String.valueOf(orderBookingVO.getTotInvoiceQty()));
        values.put(COLUMN_UPLOAD, "N");

//        if (status) {
//            values.put(COLUMN_RESV_SALEABLE_QTY, String.valueOf(resSaleableQty));
//        } else {
//            if (orderBookingVO.getTotInvoiceQty() > 0) {
//                values.put(COLUMN_AVAIL_QTY, String.valueOf(availableQty));
//            }
//        }

        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_PROD_CODE + appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";

        String[] updateArgs = {cmpCode, distrCode, orderBookingVO.getProdCode(), orderBookingVO.getProdBatchCode()};

        baseDB.getDb().update(TABLE_STOCK_ON_HAND, values, update, updateArgs);
        baseDB.closeDb();
//        }
    }

    public String getTotalDueAmount(SFADatabase sfaDatabase, String cmpCode, String distrCode, String retrCode, String routeCode, String salesmanCode) {
        String dueAmt = "";
        String query = "SELECT sum(balanceOS) AS netAmt\n" +
                "FROM m_pendingBills\n" +
                "WHERE cmpCode=?\n" +
                "  AND distrCode=?\n" +
                "  AND salesmanCode=?\n" +
                "  AND customerCode=?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, salesmanCode, retrCode});
        if (isCursorTrue(c)) {
            do {
                dueAmt = c.getString(c.getColumnIndex("netAmt"));
                if (dueAmt == null) {
                    dueAmt = "0";
                }
            } while (c.moveToNext());

        }
        closeCursor(c);

        return dueAmt;
    }

    public List<OrderBookingVO> fetchLineLevelGrossAmt(SFADatabase sfaDatabase, String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo) {
        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        String query = "SELECT invoicedetails.grossAmt,\n" +
                "       invoicedetails.prodCode,\n" +
                "       invoicedetails.ProdBatchCode,\n" +
                "       invoicedetails.schDiscAmt,\n" +
                "       invoicedetails.dbDiscAmt,\n" +
                "       invoiceheader.totSchDiscAmt,\n" +
                "       invoiceheader.totGrossAmt,\n" +
                "       invoiceheader.totDBDiscAmt\n" +
                "FROM t_invoicedetails invoicedetails\n" +
                "INNER JOIN t_invoiceheader invoiceheader ON invoiceheader.invoiceNo = invoicedetails.invoiceNo\n" +
                "WHERE invoicedetails.cmpCode=?\n" +
                "  AND invoicedetails.distrCode=?\n" +
                "  AND invoicedetails.salesmanCode=?\n" +
                "  AND invoicedetails.routeCode=?\n" +
                "  AND invoicedetails.customerCode=?\n" +
                "  AND invoicedetails.invoiceNo=?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim());
                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)).trim());
                orderBookingVO.setlGrossAmt(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_GROSS_AMT))));
                orderBookingVO.setSchemeAmount(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SCH_DISC_AMT))));
                double totGrossAmt = c.getDouble(c.getColumnIndex(COLUMN_TOT_GROSS_AMT));
                double totDBDiscAmt = c.getDouble(c.getColumnIndex(COLUMN_TOT_DB_DISC_AMT));
                double totSchDiscAmt = c.getDouble(c.getColumnIndex(COLUMN_TOT_SCH_DISC_AMT));
                double totAmt = totGrossAmt - totDBDiscAmt - totSchDiscAmt;
//                orderBookingVO.setTotGrossAmt(BigDecimal.valueOf(totAmt));
//                orderBookingVO.setDbDiscountAmt(c.getDouble(c.getColumnIndex(COLUMN_DB_DISC_AMT)));

                orderBookingList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }

        return orderBookingList;
    }

    public void updateLineLevelCashDisc(BaseDB baseDB, String cmpCode, String distrCode, String salesmanCode,
                                        String routeCode, String retlrCode, String invoiceNo,
                                        Double totCashDiscAmt, OrderBookingVO orderBookingVO,
                                        Double totCashDiscPerc, String completeFlag) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CASH_DISC_AMT, totCashDiscAmt);
        values.put(COLUMN_COMPLETE_FLAG, completeFlag);
        values.put(COLUMN_CASH_DISC_PERC, totCashDiscPerc);

        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_SALESMAN_CODE + appendAnd +
                COLUMN_ROUTE_CODE + appendAnd + COLUMN_CUSTOMER_CODE + appendAnd + COLUMN_INVOICE_NUM + appendAnd +
                COLUMN_PROD_CODE + appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";

        String[] updateArgs = {cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, orderBookingVO.getProdCode(), orderBookingVO.getProdBatchCode()};

        baseDB.getDb().update(TABLE_BILL_INVOICE_DETAILS, values, update, updateArgs);
        baseDB.closeDb();
    }

    public List<BillingInfoVO> getPendingInvoiceHeaderDetail(SFADatabase sfaDatabase, String cmpCode,
                                                             String distCode, String tableName) {

        List<BillingInfoVO> infoVOList = new ArrayList<>();

        String query = "Select * from " + TABLE_ORDER_BOOKING + " where uploadFlag='N'";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                BillingInfoVO infoVO = new BillingInfoVO();
                infoVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                infoVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                infoVO.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NUM)));
                infoVO.setGodownCode(c.getString(c.getColumnIndex(COLUMN_GODOWN_CODE)));
                infoVO.setInvoicePattern(c.getString(c.getColumnIndex(COLUMN_INVOICE_PATTERN)));
                infoVO.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)));
                infoVO.setRouteCode(c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE)));
                infoVO.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                infoVO.setCustomerShipCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_SHIP_CODE)));
                infoVO.setInvoiceDt(c.getString(c.getColumnIndex(COLUMN_INVOICE_DT)));
                infoVO.setShipDt(c.getString(c.getColumnIndex(COLUMN_SHIP_DT)));
                infoVO.setDeliveryDt(c.getString(c.getColumnIndex(COLUMN_DELIVERY_DT)));
                infoVO.setInvoiceType(c.getString(c.getColumnIndex(COLUMN_INVOICE_TYPE)));
                infoVO.setInvoiceMode(c.getString(c.getColumnIndex(COLUMN_INVOICE_MODE)));
                infoVO.setPaymentMode(c.getString(c.getColumnIndex(COLUMN_PAYMENT_MODE)));
                infoVO.setTotSplDiscAmt(c.getString(c.getColumnIndex(COLUMN_TOT_SPL_DISC_AMT)));
                infoVO.setTotCashDiscAmt(c.getString(c.getColumnIndex(COLUMN_TOT_CASH_DISC_AMT)));
                infoVO.setTotDBDiscAmt(c.getString(c.getColumnIndex(COLUMN_TOT_DB_DISC_AMT)));
                infoVO.setTotWDDiscAmt(c.getString(c.getColumnIndex(COLUMN_TOT_WD_DISC_AMT)));
                infoVO.setTotCrNoteAmt(c.getString(c.getColumnIndex(COLUMN_TOT_CR_NOTE_AMT)));
                infoVO.setTotDbNoteAmt(c.getString(c.getColumnIndex(COLUMN_TOT_DB_NOTE_AMT)));
                infoVO.setTotOnAccountAmt(c.getString(c.getColumnIndex(COLUMN_TOT_ON_ACCOUNT_AMT)));
                infoVO.setTotMarketReturnAmt(c.getString(c.getColumnIndex(COLUMN_TOT_MARKET_RETURN_AMT)));
                infoVO.setTotReplacementAmt(c.getString(c.getColumnIndex(COLUMN_TOT_REPLACEMENT_AMT)));
                infoVO.setTotOtherChargesAmt(c.getString(c.getColumnIndex(COLUMN_TOT_OTHER_CHARGES_AMT)));
                infoVO.setInvLevelDiscAmt(c.getString(c.getColumnIndex(COLUMN_INV_LEVEL_DISC_AMT)));
                infoVO.setInvLevelDiscPerc(c.getString(c.getColumnIndex(COLUMN_INV_LEVEL_DISC_PERC)));
                infoVO.setTotGrossAmt(c.getString(c.getColumnIndex(COLUMN_TOT_GROSS_AMT)));
                infoVO.setTotAddition(c.getString(c.getColumnIndex(COLUMN_TOT_ADDITION)));
                infoVO.setTotDeduction(c.getString(c.getColumnIndex(COLUMN_TOT_DEDUCTION)));
                infoVO.setTotTaxAmt(c.getString(c.getColumnIndex(COLUMN_TOT_TAX_AMT)));
                infoVO.setRoundOffAmt(c.getString(c.getColumnIndex(COLUMN_ROUND_OFF_AMT)));
                infoVO.setTotNetAmt(c.getString(c.getColumnIndex(COLUMN_TOT_NET_AMT)));
                infoVO.setOrderNo(c.getString(c.getColumnIndex(COLUMN_ORDER_NO)));
                infoVO.setInvoiceStatus(c.getString(c.getColumnIndex(COLUMN_INVOICE_STATUS)));
                infoVO.setRemarks(c.getString(c.getColumnIndex(COLUMN_REMARKS)));
                infoVO.setLatitude(c.getString(c.getColumnIndex(COLUMN_LATITUDE)));
                infoVO.setLongitude(c.getString(c.getColumnIndex(COLUMN_LONGITUDE)));
                infoVO.setStartTime(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
                infoVO.setEndTime(c.getString(c.getColumnIndex(COLUMN_END_TIME)));
                infoVO.setLineCount(c.getString(c.getColumnIndex(COLUMN_LINE_COUNT)));
                infoVO.setBalanceOS(c.getDouble(c.getColumnIndex(COLUMN_BALANCE_AMT)));
                infoVO.setModeDate(c.getString(c.getColumnIndex(COLUMN_MOD_DT)));
                infoVOList.add(infoVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return infoVOList;
    }

    public List<OrderBookingVO> getInvoiceLineLevelDetail(SFADatabase sfaDatabase, String cmpCode, String distrCode, String salesmanCode, String routeCode, String customerCode, String invoiceNo) {

        List<OrderBookingVO> infoVOList = new ArrayList<>();

        String query = "Select * from " + TABLE_BILL_INVOICE_DETAILS + " where cmpCode=? and distrCode=? and salesmanCode=? and routeCode=? and customerCode=? and invoiceNo=? and completeFlag ='Y' and uploadFlag='N'";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, salesmanCode, routeCode, customerCode, invoiceNo});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                OrderBookingVO infoVO = new OrderBookingVO();
                infoVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                infoVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                infoVO.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)));
                infoVO.setRouteCode(c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE)));
                infoVO.setRetailerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
//                infoVO.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NUM)));
//                infoVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
//                infoVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
//                infoVO.setTotOrderQty(c.getInt(c.getColumnIndex(COLUMN_TOTAL_ORD_QTY)));
//                infoVO.setTotInvoiceQty(c.getInt(c.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));
//                infoVO.setFreeQty(c.getInt(c.getColumnIndex(FREE_QTY)));
//                infoVO.setQuantity(c.getInt(c.getColumnIndex(COLUMN_INVOICE_QTY1)));
//                infoVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE1)));
//                infoVO.setQuantity2(c.getInt(c.getColumnIndex(COLUMN_INVOICE_QTY2)));
//                infoVO.setUomId2(c.getString(c.getColumnIndex(COLUMN_UOM_CODE2)));
                infoVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                infoVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_RATE))));
//                infoVO.setActualSellRate(c.getDouble(c.getColumnIndex(COLUMN_ACTUAL_SELL_RATE)));
//                infoVO.setlGrossAmt(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_GROSS_AMT))));
//                infoVO.setSplDiscAmt(c.getDouble(c.getColumnIndex(COLUMN_SPL_DISC_AMT)));
//                infoVO.setSplDiscPerc(c.getDouble(c.getColumnIndex(COLUMN_SPL_DISC_PERC)));
//                infoVO.setCashDiscPerc(c.getDouble(c.getColumnIndex(COLUMN_CASH_DISC_PERC)));
//                infoVO.setCashDiscAmt(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_CASH_DISC_AMT))));
//                infoVO.setSchemeAmount(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SCH_DISC_AMT))));
//                infoVO.setSchemePercentage(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SCH_DISC_PERC))));
//                infoVO.setDbDiscountAmt(c.getDouble(c.getColumnIndex(COLUMN_DB_DISC_AMT)));
//                infoVO.setDbDiscountPerc(c.getDouble(c.getColumnIndex(COLUMN_DB_DISC_PERC)));
                infoVO.setInputStr(c.getString(c.getColumnIndex(COLUMN_INPUT_STR)));
                infoVO.setCgstperc(c.getDouble(c.getColumnIndex(CGST_PERCENT)));
                infoVO.setCgstvalue(new BigDecimal(c.getString(c.getColumnIndex(CGST_AMT))));
                infoVO.setSgstPerc(c.getDouble(c.getColumnIndex(SGST_PERCENT)));
                infoVO.setSgstValue(new BigDecimal(c.getString(c.getColumnIndex(SGST_AMT))));
                infoVO.setIgstPerc(c.getDouble(c.getColumnIndex(IGST_PERCENT)));
                infoVO.setIgstvalue(new BigDecimal(c.getString(c.getColumnIndex(IGST_AMT))));
                infoVO.setUtgstPerc(c.getDouble(c.getColumnIndex(UTGST_PERCENT)));
                infoVO.setUtgstValue(new BigDecimal(c.getString(c.getColumnIndex(UTGST_VALUE))));

                infoVO.setTax(c.getDouble(c.getColumnIndex(COLUMN_TAX_AMT)));
                infoVO.setNetAmount(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_NET_AMT))));
                infoVO.setConfirmDate(c.getString(c.getColumnIndex(COLUMN_MOD_DT)));
//                infoVO.setParentProdCode(c.getString(c.getColumnIndex(COLUMN_PARENT_PROD_CODE)));
//                infoVO.setParentProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PARENT_PROD_BATCH_CODE)));
//
//                infoVO.setCessPerc(c.getDouble(c.getColumnIndex(CESS_PERCENT)));
//                infoVO.setCessValue(new BigDecimal(c.getString(c.getColumnIndex(CESS_VALUE))));
                infoVOList.add(infoVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }

        //Filtering all details to be sent in a single line irrespective of its free products and updating free qty
        List<OrderBookingVO> detailsList = new ArrayList<>(infoVOList);

        for (int i = 0; i < infoVOList.size(); i++) {
            OrderBookingVO orderBookingVO = infoVOList.get(i);
//            if (orderBookingVO.getProdCode().equals(orderBookingVO.getParentProdCode()) && orderBookingVO.getProdBatchCode().equals(orderBookingVO.getParentProdBatchCode())) {
//                int freeQty = 0;
//                for (OrderBookingVO freeVO : infoVOList) {
//                    if (orderBookingVO.getParentProdCode().equals(freeVO.getParentProdCode()) && orderBookingVO.getParentProdBatchCode().equals(freeVO.getParentProdBatchCode())) {
//                        freeQty = freeQty + freeVO.getFreeQty();
//                    }
//                }
//                detailsList.get(i).setFreeQty(freeQty);
//            }
        }

        for (int i = 0; i < infoVOList.size(); i++) {
            OrderBookingVO orderBookingVO = infoVOList.get(i);
//            if (orderBookingVO.getTotInvoiceQty() == 0) {
//                detailsList.remove(orderBookingVO);
//            }
        }

        return detailsList;
    }

    public List<OrderBookingVO> getOrderBookingProductsFromDataBase(SFADatabase sfaDatabase,
                                                                    String cmpCode, String distCode,
                                                                    String invoiceNo) {

        List<OrderBookingVO> bookingVOList = new ArrayList<>();

        String QUERY_SELECT_ALL_PRODUCT = "SELECT p.cmpCode,p.prodcode, \n" +
                "       pbm.prodbatchcode, \n" +
                "       p.prodname, \n" +
                "       p.producthierpathname,\n" +
                "       p.prodhierlastvalcode,\n" +
                "       p.productHierPathCode,\n" +
                "       ord.uomCode1 AS baseUom,\n" +
                "       ord.uomCode1 AS uomCode1,\n" +
                "       ord.uomCode2 AS uomCode2,\n" +
                "       ord.uomCode2 AS uomCode2,\n" +
                "       pbm.sellprice, \n" +
                "       pbm.purchaseprice, \n" +
                "       pbm.mrp, \n" +
                "       ord.orderQty, \n" +
                "       ord.servicedQty, \n" +
                "       ord.uomCode, \n" +
                "       ord.sellRate, \n" +
                "       ord.orderValue, \n" +
                "       st.availQty AS loadstock,\n" +
                "       st.resvSaleableQty,\n" +
                "       st.saleableqty \n" +
                "FROM   m_product_master p \n" +
                "       INNER JOIN m_product_batch_master pbm \n" +
                "               ON pbm.cmpcode = p.cmpcode \n" +
                "                  AND pbm.prodcode = p.prodcode \n" +
                "       LEFT JOIN m_product_uom_master um \n" +
                "              ON um.cmpcode = p.cmpcode \n" +
                "                 AND um.prodcode = p.prodcode \n" +
                "                 AND um.baseuom = 'Y' \n" +
                "                 AND um.defaultuom = 'Y' \n" +
                "       LEFT JOIN m_stockonhand st \n" +
                "              ON st.cmpcode = p.cmpcode \n" +
                "                 AND st.prodcode = p.prodcode \n" +
                "                 AND st.prodbatchcode = pbm.prodbatchcode \n" +
                "       LEFT JOIN t_invoicedetails ord \n" +
                "              ON ord.cmpcode = p.cmpcode \n" +
                "                 AND ord.prodcode = p.prodcode \n" +
                "                 AND ord.prodbatchcode = pbm.prodbatchcode \n" +
                "                 AND ord.distrCode = st.distrCode \n" +
                "WHERE  p.cmpcode = ? AND pbm.distrcode = ?  AND  invoiceNo = ? COLLATE nocase";

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(QUERY_SELECT_ALL_PRODUCT, new String[]{cmpCode, distCode, invoiceNo});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                OrderBookingVO bookingVO = new OrderBookingVO();
                bookingVO.setCmpCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_CMP_CODE)));
                bookingVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                bookingVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE)));
                bookingVO.setProdName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NAME)));
                bookingVO.setSellPrice(BigDecimal.valueOf(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                bookingVO.setMrp(BigDecimal.valueOf(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_MRP_CAPS))));

                bookingVO.setProductHierPathName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_NAME)));
                bookingVO.setProductHierPath(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                bookingVO.setProdHierValName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                bookingVO.setBaseUOM(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
                bookingVO.setQuantity(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_ORDER_QTY)));
                bookingVO.setUomId(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_UOM_CODE)));
//                bookingVO.setServicedQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex("servicedQty")));
                bookingVO.setOrderValue(BigDecimal.valueOf(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex("orderValue"))));

                int stockQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)) -
                        cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_SALEABLE_QTY));
                if (stockQty <= 0) {
                    stockQty = 0;
                }
                bookingVO.setStockInHand(stockQty);
                bookingVO.setAvailQty(stockQty);
                bookingVO.setStockCheckQty(stockQty);
                bookingVO.setConversionFactor(getConversionFactor(sfaDatabase, bookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));

                bookingVOList.add(bookingVO);

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }
        return bookingVOList;
    }

    public List<OrderBookingHeaderModel> fetchReportOrderHeaders(SFADatabase sfaDatabase, String cmpCode, String distrCode) {
        List<OrderBookingHeaderModel> models = new ArrayList<>();

        String query = "select obh.orderNo, obh.orderDate, obh.totalOrderValue,obh.orderStatus, \n" +
                "sm.salesmanName , r.routeName, c.customerName\n" +
                "from r_orderBookingHeader obh\n" +
                "left join m_salesman_master sm on\n" +
                "obh.salesmanCode = sm.salesmanCode\n" +
                "left join m_route r on\n" +
                "obh.routeCode = r.routeCode\n" +
                "left join t_customer c on\n" +
                "obh.customerCode = c.customerCode\n" +
                "where obh.cmpCode = ? AND obh.distrCode = ?";

        Cursor cursorOrder = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, cmpCode, distrCode});
        if (cursorOrder != null && cursorOrder.getCount() > 0) {
            while (cursorOrder.moveToNext()) {
                OrderBookingHeaderModel model = new OrderBookingHeaderModel();

                model.setOrderNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ORDER_NO)));
//                model.setSalesmanCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_SALESMAN_NAME)));
//                model.setRouteCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ROUTE_NAME)));
                model.setCustomerCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                model.setCustomerName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_NAME)));
//                model.setOrderDate(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ORDER_DATE)));
                model.setTotalOrderValue(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_TOTAL_ORDER_VALUE)));
                model.setOrderStatus(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ORDER_STATUS)));
                model.setLineCount(cursorOrder.getInt(cursorOrder.getColumnIndex(COLUMN_LINE_COUNT)));

                models.add(model);
            }
        }
        closeCursor(cursorOrder);
        sfaDatabase.closeDb();
        return models;
    }

    public List<OrderBookingHeaderModel> fetchReportOrderHeadersForCustomer(SFADatabase sfaDatabase, String cmpCode, String distrCode,
                                                                            String salesmanCode, String routeCode, String customerCode) {
        List<OrderBookingHeaderModel> models = new ArrayList<>();

        String query = "SELECT obh.orderNo,\n" +
                "       obh.orderDate,\n" +
                "       obh.totalOrderValue,\n" +
                "       obh.orderStatus,\n" +
                "       sm.salesmanName,\n" +
                "       r.routeName,\n" +
                "       obh.customerCode,\n" +
                "       c.customerName,\n" +
                "       sum(obh.totalOrderValue) AS totalVal,\n" +
                "  (SELECT count(*)\n" +
                "   FROM r_orderBookingDetails\n" +
                "   WHERE cmpCode = ?\n" +
                "     AND distrCode = ?\n" +
                "     AND orderNo = obh.orderNo) AS lineCount\n" +
                "FROM r_orderBookingHeader obh\n" +
                "LEFT JOIN m_salesman_master sm ON obh.salesmanCode = sm.salesmanCode\n" +
                "LEFT JOIN m_route r ON obh.routeCode = r.routeCode\n" +
                "LEFT JOIN t_customer c ON obh.customerCode = c.customerCode\n" +
                "WHERE obh.cmpCode = ?\n" +
                "  AND obh.distrCode = ? " +
                "  AND obh.salesmanCode = ? " +
                "  AND obh.routeCode = ? " +
                "  AND obh.customerCode = ?";

        Cursor cursorOrder = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, cmpCode, distrCode, salesmanCode, routeCode, customerCode});
        if (cursorOrder != null && cursorOrder.getCount() > 0) {
            while (cursorOrder.moveToNext()) {
                OrderBookingHeaderModel model = new OrderBookingHeaderModel();

                model.setLineCount(cursorOrder.getInt(cursorOrder.getColumnIndex(COLUMN_LINE_COUNT)));
                if (model.getLineCount() > 0) {
                    model.setOrderNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ORDER_NO)));
//                    model.setSalesmanCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_SALESMAN_NAME)));
//                    model.setRouteCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ROUTE_NAME)));
                    model.setCustomerCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                    model.setCustomerName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_CUSTOMER_NAME)));
//                    model.setOrderDate(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ORDER_DATE)));
                    model.setTotalOrderValue(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_TOTAL_ORDER_VALUE)));
                    model.setOrderStatus(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ORDER_STATUS)));

                    models.add(model);
                }
            }
        }
        closeCursor(cursorOrder);
        sfaDatabase.closeDb();
        return models;
    }

    public List<OrderBookingDetailsModel> fetchReportOrderDetails(SFADatabase sfaDatabase, String cmpCode,
                                                                  String distCode, String orderNo) {
        List<OrderBookingDetailsModel> models = new ArrayList<>();

        String query = "select\n" +
                "   orderNo,\n" +
                "   orderQty,\n" +
                "   servicedQty,\n" +
                "   orderValue,\n" +
                "   sellRate,\n" +
                "   uomCode,\n" +
                "   prodBatchCode,\n" +
                "   p.prodName \n" +
                "from\n" +
                "   r_orderBookingDetails obd \n" +
                "   left join\n" +
                "      m_product_master p \n" +
                "      on obd.prodCode = p.prodCode " +
                "where obd.cmpCode = ? AND" +
                " obd.distrCode = ? AND " +
                "obd.orderNo = ?";

        Cursor cursorOrder = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode, orderNo});
        if (cursorOrder != null && cursorOrder.getCount() > 0) {
            while (cursorOrder.moveToNext()) {
                OrderBookingDetailsModel model = new OrderBookingDetailsModel();

                model.setOrderNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_ORDER_NO)));
                model.setProdCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_PROD_NAME)));
                model.setProdBatchCode(cursorOrder.getString(cursorOrder.getColumnIndex(PROD_BATCH_CODE)));
                model.setOrderQty(cursorOrder.getInt(cursorOrder.getColumnIndex(COLUMN_ORDER_QTY)));
                model.setServicedQty(cursorOrder.getInt(cursorOrder.getColumnIndex(COLUMN_SERVICED_QTY)));
                model.setUomCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_UOM_CODE)));
                model.setSellRate(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_SELL_RATE)));
                model.setOrderValue(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ORDER_VALUE)));

                models.add(model);
            }
        }
        closeCursor(cursorOrder);
        sfaDatabase.closeDb();
        return models;
    }

    public void deleteBillAdjustment(SFADatabase sfaDatabase, String cmpCode, String distrCode, String collectionNo, PendingCollection pendingCollection) {

        String deleteCash = "cmpCode=? and  distrCode=? and collectionNo=? AND adjustRefNo=? AND uploadFlag = ?";
        String[] deleteArgs = {cmpCode, distrCode, collectionNo, pendingCollection.getBillNo(), "N"};
        sfaDatabase.getDb().delete(TABLE_BILL_ADJUSTMENT, deleteCash, deleteArgs);

    }

    public void insertBillAdjustment(SFADatabase sfaDatabase, String cmpCode, String distrCode, String collectionNo, PendingCollection pendingCollection) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_COLLECTION_NO, collectionNo);
        values.put(COLUMN_COLLECTION_MODE, pendingCollection.getCollectionMode());
        values.put(COLUMN_ADJUSTMENT_MODE, pendingCollection.getAdjustmentMode());
        values.put(COLUMN_ADJUST_REF_NO, pendingCollection.getBillNo());
        values.put(COLUMN_ADJUSTMENT_DATE, pendingCollection.getAdjustDt());
        values.put(COLUMN_ADJUSTMENT_AMT, pendingCollection.getAdjustAmount());
        values.put(COLUMN_ADJUST_BALANCE, pendingCollection.getAdjustBalance());
        values.put(COLUMN_MOD_DT, DateUtil.getCurrentYearMonthDate());
        values.put(UPLOAD_FLAG, "N");

        if (Double.valueOf(pendingCollection.getAdjustAmount()) != 0) {
            sfaDatabase.getDb().insert(IDBColumns.TABLE_BILL_ADJUSTMENT, null, values);
        }

        sfaDatabase.closeDb();
    }

    public void updateInvoiceAdjustBalAmt(SFADatabase baseDB, String cmpCode, String distrCode, String routeCode, String salesmanCode, String retrCode, List<PendingCollection> pendingCollectionArrayList) {
        for (int i = 0; i < pendingCollectionArrayList.size(); i++) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_BALANCE_AMT, pendingCollectionArrayList.get(i).getAdjustBalance());

            String appendAnd = APPEND_AND;

            String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_ROUTE_CODE + appendAnd + COLUMN_SALESMAN_CODE + appendAnd + COLUMN_CUSTOMER_CODE + appendAnd + COLUMN_INVOICE_NUM + " = ?";

            String[] updateArgs = {cmpCode, distrCode, routeCode, salesmanCode, retrCode,
                    pendingCollectionArrayList.get(i).getBillNo()};
            baseDB.getDb().update(TABLE_BILL_INVOIC_HEADER, values, update, updateArgs);
            baseDB.closeDb();
        }
    }

    public void deleteCollection(SFADatabase sfaDatabase, String distrCode, String salesmanCode, String routeCode, String retrCode) {
        String deleteCash = "distrCode=? and  salesmanCode=? and routeCode=? AND customerCode=? AND uploadFlag = ?";
        String[] deleteArgs = {distrCode, salesmanCode, routeCode, retrCode, "N"};
        sfaDatabase.getDb().delete(TABLE_COLLECTION, deleteCash, deleteArgs);
    }

    public List<CollectionVO> getCollectionList(SFADatabase sfaDatabase, String cmpCode, String distCode) {
        List<CollectionVO> collectionVOList = new ArrayList<>();

        String query = "SELECT *\n" +
                "FROM t_collection\n" +
                "WHERE cmpCode= ?\n" +
                "  AND distrCode=?\n" +
                "  AND uploadFlag=?";

        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode, "N"});

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                CollectionVO collection = new CollectionVO();

                collection.setCollectionNo(cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_NO)));
                collection.setMode(cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_MODE)));
                collection.setSalesmanCode(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_CODE)));
                collection.setRouteCode(cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE)));
                collection.setRetailerCode(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                collection.setCollectionDate(cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_DT)));
                collection.setAmount(cursor.getInt(cursor.getColumnIndex(COLUMN_COLLECTION_AMT)));
                collection.setBillAdjAmt(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_ADJ_AMT)));
                collection.setCrDbAdjAmt(cursor.getString(cursor.getColumnIndex(COLUMN_CR_DB_ADJ_AMT)));
                collection.setCancelledStatus(cursor.getString(cursor.getColumnIndex(COLUMN_CANCELLED_STAUTS)));
                collection.setCheque(cursor.getString(cursor.getColumnIndex(COLUMN_INSTRUMENT_NO)));
                collection.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_INSTRUMENT_DT)));
                collection.setBankName(cursor.getString(cursor.getColumnIndex(COLUMN_RTR_BANK_NAME)));
                collection.setBankCode(cursor.getString(cursor.getColumnIndex(COLUMN_RTR_BANK_CODE)));
                collection.setBankBranchCode(cursor.getString(cursor.getColumnIndex(COLUMN_RTR_BANK_BR_CODE)));

                collection.setDistrBankName(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_BANK_NAME)));
                collection.setDistrBankCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_BANK_CODE)));
                collection.setDistrBankBrCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_BANK_BR_CODE)));
                collection.setCollectionType(cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_TYPE)));
                collection.setRemarks(cursor.getString(cursor.getColumnIndex(COLUMN_REMARKS)));


                collectionVOList.add(collection);
            }
        }
        closeCursor(cursor);
        sfaDatabase.closeDb();
        return collectionVOList;
    }

    public void insertCollection(SFADatabase sfaDatabase, String invoiceNo, String routeCode, String retrCode, CollectionVO collectionVO, String remarks, String salesmanCode) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_COLLECTION_NO, invoiceNo);
        values.put(COLUMN_SALESMAN_CODE, salesmanCode);
        values.put(COLUMN_ROUTE_CODE, routeCode);
        values.put(COLUMN_CUSTOMER_CODE, retrCode);
        values.put(COLUMN_COLLECTION_DT, DateUtil.getCurrentYearMonthDate());
        values.put(COLUMN_BILL_ADJ_AMT, "0");
        values.put(COLUMN_CR_DB_ADJ_AMT, "0");
        values.put(COLUMN_CANCELLED_STAUTS, "N");
        values.put(COLUMN_COLLECTION_TYPE, "D");
        values.put(COLUMN_REMARKS, remarks);
        values.put(COLUMN_MOD_DT, DateUtil.getCurrentYearMonthDate());
        values.put(UPLOAD_FLAG, "N");

        if ("Cash".equalsIgnoreCase(collectionVO.getMode())) {
            values.put(COLUMN_COLLECTION_MODE, "CA");
            values.put(COLUMN_COLLECTION_AMT, collectionVO.getAmount());
            values.put(COLUMN_INSTRUMENT_NO, "");
            values.put(COLUMN_RTR_BANK_NAME, "");
        } else {

            /*String rtrBankCode = "";
            String rtrBankBrCode = "";
            String rtrBankName = "";

            Cursor cursor = sfaDatabase.getDb().rawQuery(QUERY_RETAILER_BANK_BY_CUSTOMER_CODE, new String[]{distrCode, retrCode});

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    rtrBankCode = cursor.getString(cursor.getColumnIndex(COLUMN_BANK_CODE));
                    rtrBankBrCode = cursor.getString(cursor.getColumnIndex(COLUMN_BANK_BRANCH_NAME));
                    rtrBankName = cursor.getString(cursor.getColumnIndex(COLUMN_BANK_NAME));
                }
            }
            closeCursor(cursor);*/

            values.put(COLUMN_COLLECTION_MODE, "CH");
            values.put(COLUMN_COLLECTION_AMT, collectionVO.getAmount());
            values.put(COLUMN_INSTRUMENT_NO, collectionVO.getCheque());
            values.put(COLUMN_INSTRUMENT_DT, collectionVO.getDate());

            values.put(COLUMN_RTR_BANK_CODE, collectionVO.getBankCode());
            values.put(COLUMN_RTR_BANK_BR_CODE, collectionVO.getBankBranchCode());
            values.put(COLUMN_RTR_BANK_NAME, collectionVO.getBankName());

            values.put(COLUMN_DISTR_BANK_CODE, "");
            values.put(COLUMN_DISTR_BANK_BR_CODE, "");
            values.put(COLUMN_DISTR_BANK_NAME, "");
        }

        sfaDatabase.getDb().insert(TABLE_COLLECTION, null, values);
        sfaDatabase.closeDb();
    }

    public List<PendingCollection> getBillAdjustmentDetail(SFADatabase sfaDatabase, String cmpCode, String distCode) {
        List<PendingCollection> pendingCollections = new ArrayList<>();

        String query = "SELECT *\n" +
                "FROM t_billadjustment\n" +
                "WHERE cmpCode=?\n" +
                "  AND distrCode=?\n" +
                "  AND uploadFlag=?";

        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode, "N"});
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                PendingCollection collection = new PendingCollection();

                collection.setCollectionNo(cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_NO)));
                collection.setCollectionMode(cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_MODE)));
                collection.setAdjustmentMode(cursor.getString(cursor.getColumnIndex(COLUMN_ADJUSTMENT_MODE)));
                collection.setBillNo(cursor.getString(cursor.getColumnIndex(COLUMN_ADJUST_REF_NO)));
                collection.setAdjustDt(cursor.getString(cursor.getColumnIndex(COLUMN_ADJUSTMENT_DATE)));
                collection.setAdjustAmount(cursor.getDouble(cursor.getColumnIndex(COLUMN_ADJUSTMENT_AMT)));
                collection.setAdjustBalance(cursor.getDouble(cursor.getColumnIndex(COLUMN_ADJUST_BALANCE)));

                pendingCollections.add(collection);
            }
        }
        closeCursor(cursor);
        sfaDatabase.closeDb();
        return pendingCollections;
    }

    public List<CollectionVO> loadCollection(SFADatabase sfaDatabase, String distrCode, String salesmanCode, String routeCode, String retrCode) {

        List<CollectionVO> collectionList = new ArrayList<>();

        Cursor c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_COLLECTION, new String[]{distrCode, salesmanCode, routeCode, retrCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                CollectionVO collectionvo = new CollectionVO();
                collectionvo.setBankName(c.getString(c.getColumnIndex(COLUMN_RTR_BANK_NAME)));
                collectionvo.setCheque(c.getString(c.getColumnIndex(COLUMN_INSTRUMENT_NO)));
                collectionvo.setAmount(c.getInt(c.getColumnIndex(COLUMN_COLLECTION_AMT)));
                if ("CA".equalsIgnoreCase(c.getString(c.getColumnIndex(COLUMN_COLLECTION_MODE)))) {
                    collectionvo.setMode("Cash");
                    collectionvo.setDate(c.getString(c.getColumnIndex(COLUMN_COLLECTION_DT)));
                } else {
                    collectionvo.setMode("Cheque");
                    collectionvo.setDate(c.getString(c.getColumnIndex(COLUMN_INSTRUMENT_DT)));
                }

                collectionList.add(collectionvo);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return collectionList;
    }

    public String getCollectionNo(SFADatabase sfaDatabase, String tableName, String distrCode, String salesmanCode, String routeCode, String retrCode) {
        String invoiceNo = "";
        Cursor c = null;
        String query = getQueryInvoiceNumber(tableName);
        if (tableName.equalsIgnoreCase(TABLE_COLLECTION)) {

            c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, salesmanCode, routeCode, retrCode, "N"});

        }
        if (c != null && c.getCount() > 0 && c.moveToFirst())
            invoiceNo = c.getString(c.getColumnIndex("collectionNo"));
        if (c != null) {
            c.close();
        }
        return invoiceNo;
    }

    public void updatePendingBillAmount(SFADatabase baseDB, String cmpCode, String distrCode, String retrCode, String salesmanCode, List<PendingCollection> pendingCollectionArrayList) {
        for (int i = 0; i < pendingCollectionArrayList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_BALANCE_OS, pendingCollectionArrayList.get(i).getAdjustBalance());
            values.put(UPLOAD_FLAG, "N");
            values.put(COLUMN_MOD_DT, System.currentTimeMillis());

            String appendAnd = APPEND_AND;

            String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_CUSTOMER_CODE + appendAnd + COLUMN_SALESMAN_CODE + appendAnd + COLUMN_INVOICE_NUM + " = ?";

            String[] updateArgs = {cmpCode, distrCode, retrCode, salesmanCode, pendingCollectionArrayList.get(i).getBillNo()};
            baseDB.getDb().update(TABLE_PENDING_BILLS, values, update, updateArgs);
            baseDB.closeDb();
        }
    }

    public List<PendingBillCollection> getPendingBillCollectionList(SFADatabase sfaDatabase, String cmpCode, String distCode) {
        List<PendingBillCollection> pendingBillCollectionList = new ArrayList<>();

        String query = "SELECT *\n" +
                "FROM m_pendingBills\n" +
                "WHERE cmpCode= ?\n" +
                "  AND distrCode=?\n" +
                "  AND uploadFlag=?";

        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode, "N"});

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                PendingBillCollection collection = new PendingBillCollection();

                collection.setBalanceOs(cursor.getString(cursor.getColumnIndex(COLUMN_BALANCE_OS)));
                collection.setInvoiceNo(cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_NO)));
                collection.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                collection.setDistrCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)));

                pendingBillCollectionList.add(collection);
            }
        }
        closeCursor(cursor);
        sfaDatabase.closeDb();
        return pendingBillCollectionList;
    }

    public List<BillingInfoVO> getAllInvoiceHeader(BaseDB baseDB) {
        SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
        String distr = sfaSharedPref.readString(PREF_DISTRCODE);
        String cmp = sfaSharedPref.readString(SFASharedPref.PREF_CMP_CODE);

        baseDB.openWritableDb();
        List<BillingInfoVO> list = new ArrayList<>();
        String query = "SELECT ih.invoiceNo,\n" +
                "       ih.invoiceDt,\n" +
                "       ih.lineCount,\n" +
                "       ih.salesmanCode,\n" +
                "       ih.routeCode,\n" +
                "       ih.customerCode,\n" +
                "       ih.customerShipCode,\n" +
                "       ih.remarks,\n" +
                "       ih.invoiceMode,\n" +
                "       ih.invoiceType,\n" +
                "       ih.shipDt,\n" +
                "       ih.totNetAmt,\n" +
                "       ih.totCrNoteAmt,\n" +
                "       ih.totNetAmt,\n" +
                "       ih.totGrossAmt,\n" +
                "       ih.totAddition,\n" +
                "       ih.totDeduction,\n" +
                "       r.customerName,\n" +
                "       ih.completeFlag,\n" +
                "       ih.uploadFlag\n" +
                "FROM t_invoiceheader ih " +
                "LEFT JOIN t_customer r ON ih.customerCode = r.customerCode " +
                "where ih.cmpCode = ? AND ih.distrCode = ? AND completeFlag = 'Y' OR completeFlag = 'S'";

        try {
            Cursor cm = baseDB.getDb().rawQuery(query, new String[]{cmp, distr});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
                BillingInfoVO billingInfoVO = new BillingInfoVO();
                billingInfoVO.setInvoiceNo(cm.getString(cm.getColumnIndex(COLUMN_INVOICE_NUM)));
                billingInfoVO.setInvoiceDt(cm.getString(cm.getColumnIndex(COLUMN_INVOICE_DT)));
                billingInfoVO.setLineCount(cm.getString(cm.getColumnIndex(COLUMN_LINE_COUNT)));
                billingInfoVO.setSalesmanCode(cm.getString(cm.getColumnIndex(COLUMN_SALESMAN_CODE)));
                billingInfoVO.setRouteCode(cm.getString(cm.getColumnIndex(COLUMN_ROUTE_CODE)));
                billingInfoVO.setCustomerCode(cm.getString(cm.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                billingInfoVO.setCustomerShipCode(cm.getString(cm.getColumnIndex(COLUMN_CUSTOMER_SHIP_CODE)));
                billingInfoVO.setRemarks(cm.getString(cm.getColumnIndex(COLUMN_REMARKS)));
                billingInfoVO.setInvoiceMode(cm.getString(cm.getColumnIndex(COLUMN_INVOICE_MODE)));
                billingInfoVO.setInvoiceType(cm.getString(cm.getColumnIndex(COLUMN_INVOICE_TYPE)));
                billingInfoVO.setShipDt(cm.getString(cm.getColumnIndex(COLUMN_SHIP_DT)));
                billingInfoVO.setTotNetAmt(cm.getString(cm.getColumnIndex(COLUMN_TOT_NET_AMT)));
                billingInfoVO.setCustomerName(cm.getString(cm.getColumnIndex(COLUMN_CUSTOMER_NAME)));
                billingInfoVO.setCompleteFlag(cm.getString(cm.getColumnIndex(COLUMN_COMPLETE_FLAG)));
                billingInfoVO.setUploadFlag(cm.getString(cm.getColumnIndex(UPLOAD_FLAG)));
                billingInfoVO.setTotCrNoteAmt(cm.getString(cm.getColumnIndex(COLUMN_TOT_CR_NOTE_AMT)));

                billingInfoVO.setTotGrossAmt(cm.getString(cm.getColumnIndex(COLUMN_TOT_GROSS_AMT)));
                billingInfoVO.setTotAddition(cm.getString(cm.getColumnIndex(COLUMN_TOT_ADDITION)));
                billingInfoVO.setTotDeduction(cm.getString(cm.getColumnIndex(COLUMN_TOT_DEDUCTION)));

                list.add(billingInfoVO);
            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getInvoiceHeader: " + e.getMessage(), e);
        }
        return list;
    }

    public List<OrderBookingVO> getBillingInvoiceDetails(BaseDB baseDB, String cmpCode, String distrCode, String invoiceNo) {
        baseDB.openWritableDb();
        List<OrderBookingVO> list = new ArrayList<>();
        String query = "SELECT cmpCode,\n" +
                "       distrCode,\n" +
                "       salesmanCode,\n" +
                "       routeCode,\n" +
                "       customerCode,\n" +
                "       invoiceNo,\n" +
                "       prodCode,\n" +
                "       ProdBatchCode,\n" +
                "       totalInvoiceQty \n" +
                "FROM t_invoicedetails " +
                "WHERE cmpCode = ? AND distrCode = ? AND invoiceNo = ?";

        try {
            Cursor cm = baseDB.getDb().rawQuery(query, new String[]{cmpCode, distrCode, invoiceNo});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
                OrderBookingVO billingInfoVO = new OrderBookingVO();
                billingInfoVO.setCmpCode(cm.getString(cm.getColumnIndex(COLUMN_CMP_CODE)));
                billingInfoVO.setDistrCode(cm.getString(cm.getColumnIndex(COLUMN_DISTR_CODE)));
                billingInfoVO.setSalesmanCode(cm.getString(cm.getColumnIndex(COLUMN_SALESMAN_CODE)));
                billingInfoVO.setRouteCode((cm.getString(cm.getColumnIndex(COLUMN_ROUTE_CODE))));
                billingInfoVO.setRetailerCode((cm.getString(cm.getColumnIndex(COLUMN_CUSTOMER_CODE))));
//                billingInfoVO.setInvoiceNo((cm.getString(cm.getColumnIndex(COLUMN_INVOICE_NO))));
//                billingInfoVO.setProdCode(cm.getString(cm.getColumnIndex(COLUMN_PROD_CODE)));
//                billingInfoVO.setProdBatchCode(cm.getString(cm.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
//                billingInfoVO.setTotInvoiceQty(cm.getInt(cm.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));

                list.add(billingInfoVO);
            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getInvoiceHeader: " + e.getMessage(), e);
        }
        return list;
    }

    boolean isStockAvailableForReceipt(BaseDB baseDB, List<OrderBookingVO> receiptList) {
        boolean check = true;
        baseDB.openWritableDb();
        String query = "SELECT availQty \n" +
                "FROM m_stockOnHand where prodCode = ?  and ProdBatchCode = ?";

        for (int i = 0; i < receiptList.size(); i++) {

            try {
                Cursor cm = baseDB.getDb().rawQuery(query, new String[]{receiptList.get(i).getProdCode(), receiptList.get(i).getProdBatchCode()});
                check = cm != null && cm.getCount() > 0;

                if (cm != null) {
                    cm.close();
                }
            } catch (Exception e) {
                Log.e(TAG, "getInvoiceHeader: " + e.getMessage(), e);
                check = false;
            }

            if (!check) {
                break;
            }
        }
        return check;
    }

    public void insertSavedSavedBillingDetails(SFADatabase sfaDatabase, List<OrderBookingVO> list) {
        String cmpCode = "";
        String distrCode = "";
        String invoiceNo = "";
        if (list.size() > 0) {
            cmpCode = list.get(0).getCmpCode();
            distrCode = list.get(0).getDistrCode();
//            invoiceNo = list.get(0).getInvoiceNo();
        }

        String query = "cmpCode = ? AND distrCode = ?  AND invoiceNo = ?";
        sfaDatabase.getDb().delete(TABLE_BILL_INVOICE_SAVED_DETAILS, query, new String[]{cmpCode, distrCode, invoiceNo});

        for (OrderBookingVO model : list) {
            ContentValues values = new ContentValues();

            values.put(COLUMN_CMP_CODE, model.getCmpCode());
            values.put(COLUMN_DISTR_CODE, model.getDistrCode());
            values.put(COLUMN_SALESMAN_CODE, model.getSalesmanCode());
            values.put(COLUMN_ROUTE_CODE, model.getRouteCode());
            values.put(COLUMN_CUSTOMER_CODE, model.getRetailerCode());
//            values.put(COLUMN_INVOICE_NO, model.getInvoiceNo());
            values.put(COLUMN_PROD_CODE, model.getProdCode());
            values.put(COLUMN_PROD_BATCH_CODE, model.getProdBatchCode());
//            values.put(COLUMN_TOTAL_INVOICE_QTY, model.getTotInvoiceQty());

            sfaDatabase.getDb().insert(TABLE_BILL_INVOICE_SAVED_DETAILS, null, values);
            sfaDatabase.closeDb();
        }
    }

    public void deleteUnconfirmedTransaction(SFADatabase sfaDatabase, String cmpCode, String distCode,
                                             String status) {

        String deleteCash = "cmpCode= ? AND distrCode=? AND completeFlag = ? and uploadFlag =?";
        String deleteSchemes = "distrCode=? AND completeFlag = ? and upload =?";
        String[] deleteArgs = {cmpCode, distCode, status, status};
        String[] deleteSchemeArgs = {distCode, status, status};
        sfaDatabase.getDb().delete(TABLE_BILL_INVOIC_HEADER, deleteCash, deleteArgs);
        sfaDatabase.getDb().delete(TABLE_BILL_INVOICE_DETAILS, deleteCash, deleteArgs);
        sfaDatabase.getDb().delete(TABLE_BILLING_APPLIED_SCHEME, deleteSchemes, deleteSchemeArgs);
    }

    public void updateInvoiceLineLevelTaxAmt(BaseDB baseDB, String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo, OrderBookingVO bookingVO) {

//        Double taxAmt = bookingVO.getCgstvalue().doubleValue() + bookingVO.getSgstValue().doubleValue() + bookingVO.getIgstvalue().doubleValue() + bookingVO.getUtgstValue().doubleValue() + bookingVO.getCessValue().doubleValue();
//
//        BigDecimal netAmt = bookingVO.getlGrossAmt().subtract(new BigDecimal(bookingVO.getDbDiscountAmt())).subtract(bookingVO.getCashDiscAmt()).subtract(bookingVO.getSchemeAmount()).add(new BigDecimal(taxAmt));

        ContentValues values = new ContentValues();
        values.put(CGST_PERCENT, bookingVO.getCgstperc());
        values.put(CGST_AMT, String.valueOf(bookingVO.getCgstvalue()));
        values.put(SGST_PERCENT, bookingVO.getSgstPerc());
        values.put(SGST_AMT, String.valueOf(bookingVO.getSgstValue()));
        values.put(IGST_PERCENT, bookingVO.getIgstPerc());
        values.put(IGST_AMT, String.valueOf(bookingVO.getIgstvalue()));
        values.put(UTGST_PERCENT, String.valueOf(bookingVO.getUtgstPerc()));
        values.put(UTGST_VALUE, String.valueOf(bookingVO.getUtgstValue()));
//        values.put(CESS_PERCENT, String.valueOf(bookingVO.getCessPerc()));
//        values.put(CESS_VALUE, String.valueOf(bookingVO.getCessValue()));
//        values.put(COLUMN_TAX_AMT, taxAmt);
//        values.put(COLUMN_NET_AMT, netAmt.toString());

        String appendAnd = " = ? and ";
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_SALESMAN_CODE + appendAnd +
                COLUMN_ROUTE_CODE + appendAnd + COLUMN_CUSTOMER_CODE + appendAnd + COLUMN_INVOICE_NUM + appendAnd +
                COLUMN_PROD_CODE + appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";

        String[] updateArgs = {cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, bookingVO.getProdCode(), bookingVO.getProdBatchCode()};

        baseDB.getDb().update(TABLE_BILL_INVOICE_DETAILS, values, update, updateArgs);
        baseDB.closeDb();
    }

    public void deleteOldSavedInvoiceDetails(BaseDB sfaDatabase, String cmpCode, String distrcode, String invoiceNo) {
        String deleteReturns = "cmpCode = ? and distrCode=? and invoiceNo=?";
        String[] deleteArgs = {cmpCode, distrcode, invoiceNo};
        sfaDatabase.getDb().delete(TABLE_BILL_INVOICE_DETAILS, deleteReturns, deleteArgs);
    }

    public void deleteAllSavedBillingDetails(SFADatabase baseDB, String companyCode, String distrCode, String invoiceNo) {

        List<OrderBookingVO> list = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT * From t_invoiceSavedDetails WHERE cmpCode=? AND distrCode = ? AND invoiceNo = ?";
        cursor = baseDB.getDb().rawQuery(query, new String[]{companyCode, distrCode, invoiceNo});

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                OrderBookingVO billingInfoVO = new OrderBookingVO();

                billingInfoVO.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                billingInfoVO.setDistrCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)));
                billingInfoVO.setSalesmanCode(cursor.getString(cursor.getColumnIndex(COLUMN_SALESMAN_CODE)));
                billingInfoVO.setRouteCode((cursor.getString(cursor.getColumnIndex(COLUMN_ROUTE_CODE))));
                billingInfoVO.setRetailerCode((cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_CODE))));
//                billingInfoVO.setInvoiceNo((cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_NO))));
                billingInfoVO.setProdCode(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_CODE)));
                billingInfoVO.setProdBatchCode(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
//                billingInfoVO.setTotInvoiceQty(cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));

                list.add(billingInfoVO);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        if (list.size() > 0) {
            for (OrderBookingVO orderBookingVO : list) {
                int resrvedQty = 0;

//                int savedInvoiceQty = orderBookingVO.getTotInvoiceQty();
//                int prevResSaleableQty = baseDB.getResvSaleableQtyForProduct(cmpCode, distrCode, orderBookingVO.getProdCode(), orderBookingVO.getProdBatchCode());
//
//                if (prevResSaleableQty > 0) {
//                    resrvedQty = prevResSaleableQty - savedInvoiceQty;
//                }
//
                ContentValues values = new ContentValues();
//                values.put(COLUMN_LAST_ORD_QTY, String.valueOf(orderBookingVO.getTotInvoiceQty()));
                values.put(COLUMN_RESV_SALEABLE_QTY, String.valueOf(resrvedQty));
                values.put(COLUMN_UPLOAD, "N");
                String appendAnd = " = ? and ";
                String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_PROD_CODE + appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";

                String[] updateArgs = {cmpCode, distrCode, orderBookingVO.getProdCode(), orderBookingVO.getProdBatchCode()};

                baseDB.getDb().update(TABLE_STOCK_ON_HAND, values, update, updateArgs);

                String deleteReturns = "cmpCode = ? and distrCode=? and invoiceNo=? and prodCode = ? and prodBatchCode = ?";
                String[] deleteArgs = {cmpCode, distrCode, invoiceNo, orderBookingVO.getProdCode(), orderBookingVO.getProdBatchCode()};
                baseDB.getDb().delete(TABLE_BILL_INVOICE_SAVED_DETAILS, deleteReturns, deleteArgs);

            }
        }
    }

    public int getResvSaleableQtyForProduct(SFADatabase sfaDatabase, String cmpCode, String distrCode, String prodCode, String prodBatchCode) {
        int quantity = 0;
        String query = "SELECT resvSaleableQty FROM " + TABLE_STOCK_ON_HAND + " where " + COLUMN_CMP_CODE + " =? and " + COLUMN_DISTR_CODE + " =? and " + COLUMN_PROD_CODE + " =? and " + COLUMN_PROD_BATCH_CODE + " =?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode});
        if (isCursorTrue(c)) {
            do {
                quantity = c.getInt(c.getColumnIndex(COLUMN_RESV_SALEABLE_QTY));
            } while (c.moveToNext());

        }
        closeCursor(c);

        return quantity;
    }

    /*
     * Fetch the existing sales returns data from db based on route and retailer code
     * */
    public List<SalesReturnVO> getExistingReturns(SFADatabase sfaDatabase, String distrcode,
                                                  String salesmancode, String routecode, String retrCode) {


        List<SalesReturnVO> salesReturnList = new ArrayList<>();

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(QUERY_SELECT_EXISTING_RETURNS, new String[]{distrcode, salesmancode, routecode, retrCode, "N"});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                SalesReturnVO salesReturnVO = new SalesReturnVO();
                salesReturnVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                salesReturnVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE)));
                salesReturnVO.setHsnCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HSN_CODE)));
                salesReturnVO.setProdName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NAME)));
                salesReturnVO.setProdShortName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
                salesReturnVO.setReasonCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_REASON_CODE_CAPS)));
                salesReturnVO.setReasonName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_REASON_NAME_CAPS)));
                salesReturnVO.setRetlrCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                salesReturnVO.setReturnQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RETURN_QTY)));
                salesReturnVO.setReturnAmt(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_RETURN_AMOUNT)));
                salesReturnVO.setReturnImage(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_IMAGE)));
                salesReturnVO.setSellPrice(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS)));
                salesReturnVO.setMrp(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_MRP)));
                salesReturnVO.setBatchDetail(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_BATCH_DETAIL)));
                salesReturnVO.setStockType(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_STOCK_TYPE)));
                salesReturnVO.setSalesreturnNo(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_SALES_RETURNNO)));
                salesReturnVO.setRemarks(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_REMARKS)));
                salesReturnList.add(salesReturnVO);

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }
        return salesReturnList;


    }

    /*
     * Return all the products based on distributor and salesman code
     * */
    public List<StockTakeVO> getAppProductsSales(SFADatabase sfaDatabase, String cmpCode) {

        List<StockTakeVO> stockTakeList = new ArrayList<>();

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(QUERY_SELECT_APP_PRODUCTS, new String[]{cmpCode});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                StockTakeVO stockTakeVO = new StockTakeVO();
                stockTakeVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                stockTakeVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE)));
                stockTakeVO.setProdShortName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
                stockTakeVO.setProdName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NAME)));
                stockTakeVO.setSellPrice(BigDecimal.valueOf(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                stockTakeList.add(stockTakeVO);

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }
        return stockTakeList;
    }

    List<OrderBookingVO> getStockData(SFADatabase sfaDatabase, String transType, String stockType) {
        String QUERY_SELECT_STOCK_DATA = "";
        if (transType.equalsIgnoreCase("I")) {

            QUERY_SELECT_STOCK_DATA = "SELECT prod.prodCode,\n" +
                    "       prod.prodName,\n" +
                    "       prod.prodnetwgt,\n" +
                    "       prod.prodwgttype,\n" +
                    "       prod.lobDivisionCode,\n" +
                    "       prod.prodHierLastValCode,\n" +
                    "       prod.productHierPathCode,\n" +
                    "       batch.ProdBatchCode,\n" +
                    "       batch.mrp,\n" +
                    "       batch.purchasePrice,\n" +
                    "       batch.sellPrice,\n" +
                    "       stock.availQty,\n" +
                    "       stock.unSaleableQty,\n" +
                    "       stock.offerQty,\n" +
                    "       uom.conversionFactor,\n" +
                    "       uom.uomCode\n" +
                    "FROM " + TABLE_PRODUCT_MASTER + " prod\n" +
                    "INNER JOIN " + TABLE_PRODUCT_BATCH_MASTER + " batch ON prod.prodCode=batch.prodCode\n" +
                    "INNER JOIN " + TABLE_STOCK_ON_HAND + " stock ON stock.prodCode=batch.prodCode and stock.ProdBatchCode=batch.ProdBatchCode\n" +
                    "LEFT JOIN " + TABLE_PRODUCT_UOM_MASTER + " uom ON uom.cmpCode=prod.cmpCode\n" +
                    "AND uom.prodCode=prod.prodCode\n" +
                    "AND uom.baseUom='Y'\n" +
                    "AND uom.defaultUOM='Y'\n" +
                    "WHERE prod.cmpCode=?\n" +
                    "  AND batch.distrCode=?";

        } else if (transType.equalsIgnoreCase("O")) {
            String columnName = COLUMN_AVAIL_QTY;
            if (stockType.equalsIgnoreCase("S")) {
                columnName = COLUMN_AVAIL_QTY;
            } else if (stockType.equalsIgnoreCase("U")) {
                columnName = COLUMN_UNSALEABLE_QTY;
            } else if (stockType.equalsIgnoreCase("O")) {
                columnName = COLUMN_OFFER_QTY;
            }

            QUERY_SELECT_STOCK_DATA = "SELECT prod.prodCode,\n" +
                    "       prod.prodName,\n" +
                    "       prod.prodnetwgt,\n" +
                    "       prod.prodwgttype,\n" +
                    "       prod.prodHierLastValCode,\n" +
                    "       prod.productHierPathCode,\n" +
                    "       prod.lobDivisionCode,\n" +
                    "       batch.ProdBatchCode,\n" +
                    "       batch.mrp,\n" +
                    "       batch.purchasePrice,\n" +
                    "       batch.sellPrice,\n" +
                    "       stock.availQty,\n" +
                    "       stock.unSaleableQty,\n" +
                    "       stock.offerQty,\n" +
                    "       uom.conversionFactor,\n" +
                    "       uom.uomCode\n" +
                    "FROM " + TABLE_PRODUCT_MASTER + " prod\n" +
                    "INNER JOIN " + TABLE_PRODUCT_BATCH_MASTER + " batch ON prod.prodCode=batch.prodCode\n" + // and batch.latestBatch ='Y'
                    "INNER JOIN " + TABLE_STOCK_ON_HAND + " stock ON stock.prodCode=batch.prodCode and stock.ProdBatchCode=batch.ProdBatchCode\n" +
                    "LEFT JOIN " + TABLE_PRODUCT_UOM_MASTER + " uom ON uom.cmpCode=prod.cmpCode\n" +
                    "AND uom.prodCode=prod.prodCode\n" +
                    "AND uom.baseUom='Y'\n" +
                    "AND uom.defaultUOM='Y'\n" +
                    "WHERE prod.cmpCode=?\n" +
                    "  AND batch.distrCode=?\n" +
                    "  AND stock." + columnName + " >0";

        }


        List<OrderBookingVO> stockList = new ArrayList<>();

        Cursor c = null;
        try {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_STOCK_DATA, new String[]{cmpCode, distrCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                    orderBookingVO.setPurchasePrice(c.getDouble(c.getColumnIndex(COLUMN_PURCHASE_PRICE)));
                    orderBookingVO.setUomGroupId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setProductHierPath(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                    orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
//                    orderBookingVO.setLobDivisionCode(c.getString(c.getColumnIndex(COLUMN_PROD_LOB_DIVISTION_CODE)));
                    orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
                    orderBookingVO.setBaseUOM(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_PURCHASE_PRICE))));
                    orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                    orderBookingVO.setNetWeight(c.getDouble(c.getColumnIndex(COLUMN_PRODUCT_NET_WGT)));
                    orderBookingVO.setWeightType(c.getString(c.getColumnIndex(COLUMN_PRODUCT_WGT_TYPE)));
                    orderBookingVO.setConversionFactor(getConversionFactor(sfaDatabase, orderBookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));
//                    orderBookingVO.setSaleableStock(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
//                    orderBookingVO.setUnsaleableStock(c.getInt(c.getColumnIndex(COLUMN_UNSALEABLE_QTY)));
//                    orderBookingVO.setOfferStock(c.getInt(c.getColumnIndex(COLUMN_OFFER_QTY)));
//                    orderBookingVO.setProdHierLastValCode(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                    orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                    stockList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
            sfaDatabase.closeDb();
        }

        return stockList;
    }

    public void insertStockAdjustmentHeaderDetail(SFADatabase sfaDatabase, String cmpCode, String distrCode, String goDownCode, String stockAdjustNo, String transType, String remarks) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_GODOWN_CODE, goDownCode);
        values.put(COLUMN_STOCK_ADJUSTNO, stockAdjustNo);
        values.put(COLUMN_TRANS_TYPE, transType);
        values.put(COLUMN_ADJUST_DT, DateUtil.getCurrentYearMonthDate());
        values.put(COLUMN_DOC_REFNO, "");
        values.put(COLUMN_REMARKS, remarks);
        values.put(COLUMN_ISMANUAL, "Y");
        values.put(COLUMN_SOURCE, "SA");
        values.put(UPLOAD_FLAG, "N");
        values.put(COLUMN_MOD_DT, DateUtil.getCurrentYearMonthDate());
        values.put(COLUMN_COMPLETE_FLAG, "Y");
        // Inserting Row
        sfaDatabase.getDb().insert(IDBColumns.TABLE_STOCK_ADJ_HEADER, null, values);

    }

    public void insertStockAdjustmentLineLevelDetail(SFADatabase sfaDatabase, String cmpCode, String distrCode, String goDownCode, String stockAdjustNo, OrderBookingVO qtyUpdatedOrderBooking, String stockType) {
        String inputStr = ((int) qtyUpdatedOrderBooking.getQuantity()) + " " + qtyUpdatedOrderBooking.getUomId() + " ";

        String query = "select conversionFactor from m_product_uom_master where " +
                "   cmpCode = ? \n" +
                " AND prodCode = ? and uomCode = ?";
        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{
                cmpCode,
                qtyUpdatedOrderBooking.getProdCode(), qtyUpdatedOrderBooking.getUomId()});

        int convFactor = 1;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            convFactor = cursor.getInt(cursor.getColumnIndex(COLUMN_CONVERSION_FACTOR));
        }
        int qty = ((int) qtyUpdatedOrderBooking.getQuantity());
//        qtyUpdatedOrderBooking.setAdjustQty(convFactor * qty);
        if (cursor != null) {
            cursor.close();
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_GODOWN_CODE, goDownCode);
        values.put(COLUMN_STOCK_ADJUSTNO, stockAdjustNo);
        values.put(COLUMN_PROD_CODE, qtyUpdatedOrderBooking.getProdCode());
        values.put(PROD_BATCH_CODE, qtyUpdatedOrderBooking.getProdBatchCode());
        values.put(COLUMN_FROM_STOCK_TYPE, stockType);
        values.put(COLUMN_TO_STOCK_TYPE, stockType);
//        values.put(COLUMN_ADJUST_QTY, qtyUpdatedOrderBooking.getAdjustQty());
//        values.put(COLUMN_REASON_CODE_CAPS, qtyUpdatedOrderBooking.getReasonCode());
//        values.put(COLUMN_REASON_NAME_CAPS, qtyUpdatedOrderBooking.getReasonName());
        values.put(COLUMN_INPUT_STR, inputStr);
        values.put(COLUMN_PURCHASE_RATE, String.valueOf(qtyUpdatedOrderBooking.getSellPrice()));
        values.put(COLUMN_AMOUNT, String.valueOf(qtyUpdatedOrderBooking.getOrderValue()));
        values.put(UPLOAD_FLAG, "N");
        values.put(COLUMN_MOD_DT, DateUtil.getCurrentYearMonthDate());
        values.put(COLUMN_COMPLETE_FLAG, "Y");
        values.put(COLUMN_UOM_ID, qtyUpdatedOrderBooking.getUomId());
        values.put(COLUMN_ORDER_QTY, qtyUpdatedOrderBooking.getQuantity());
        // Inserting Row
        sfaDatabase.getDb().insert(IDBColumns.TABLE_STOCK_ADJ_DETAILS, null, values);
    }

    public List<OrderBookingVO> getStockAdjustmentHeaderDetail(SFADatabase sfaDatabase, String cmpCode, String distCode) {
        List<OrderBookingVO> orderBookingVOList = new ArrayList<>();

        String query = "Select * from " + TABLE_STOCK_ADJ_HEADER + " where cmpCode=? and distrCode=? and completeFlag='Y' and uploadFlag='N'";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                OrderBookingVO orderBookingVO = new OrderBookingVO();

                orderBookingVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
//                orderBookingVO.setGoDownCode(c.getString(c.getColumnIndex(COLUMN_GODOWN_CODE)));
//                orderBookingVO.setStockAdjustNo(c.getString(c.getColumnIndex(COLUMN_STOCK_ADJUSTNO)));
//                orderBookingVO.setTransType(c.getString(c.getColumnIndex(COLUMN_TRANS_TYPE)));
//                orderBookingVO.setAdjustDt(c.getString(c.getColumnIndex(COLUMN_ADJUST_DT)));
//                orderBookingVO.setDocRefNo(c.getString(c.getColumnIndex(COLUMN_DOC_REFNO)));
//                orderBookingVO.setRemarks(c.getString(c.getColumnIndex(COLUMN_REMARKS)));
//                orderBookingVO.setIsManual(c.getString(c.getColumnIndex(COLUMN_ISMANUAL)));
//                orderBookingVO.setSource(c.getString(c.getColumnIndex(COLUMN_SOURCE)));
                orderBookingVOList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingVOList;
    }

    public List<OrderBookingVO> getStockAdjustmentDetail(SFADatabase sfaDatabase, String distrCode, String goDownCode, String stockAdjustNo) {
        List<OrderBookingVO> orderBookingVOList = new ArrayList<>();
        String query = "Select * from " + TABLE_STOCK_ADJ_DETAILS + " where distrCode=? and godownCode=? and stockAdjustNo=? and completeFlag='Y' and uploadFlag='N'";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, goDownCode, stockAdjustNo});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                OrderBookingVO orderBookingVO = new OrderBookingVO();

                orderBookingVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
//                orderBookingVO.setGoDownCode(c.getString(c.getColumnIndex(COLUMN_GODOWN_CODE)));
//                orderBookingVO.setStockAdjustNo(c.getString(c.getColumnIndex(COLUMN_STOCK_ADJUSTNO)));
//                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
//                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(PROD_BATCH_CODE)));
//                orderBookingVO.setFromStockType(c.getString(c.getColumnIndex(COLUMN_FROM_STOCK_TYPE)));
//                orderBookingVO.setToStockType(c.getString(c.getColumnIndex(COLUMN_TO_STOCK_TYPE)));
//                orderBookingVO.setAdjustQty(c.getInt(c.getColumnIndex(COLUMN_ADJUST_QTY)));
//                orderBookingVO.setReasonCode(c.getString(c.getColumnIndex(COLUMN_REASON_CODE_CAPS)));
//                orderBookingVO.setReasonName(c.getString(c.getColumnIndex(COLUMN_REASON_NAME_CAPS)));
//                orderBookingVO.setInputStr(c.getString(c.getColumnIndex(COLUMN_INPUT_STR)));
//                orderBookingVO.setPurchaseRate(c.getString(c.getColumnIndex(COLUMN_PURCHASE_RATE)));
//                orderBookingVO.setAmount(c.getString(c.getColumnIndex(COLUMN_AMOUNT)));
                orderBookingVOList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingVOList;
    }

    String isInvoiceGeneratedForOrder(BaseDB baseDB, String cmpCode, String distCode, String salesmanCode, String routeCode, String customerCode, String orderNo) {
        String invoiceNo = "";
        baseDB.openWritableDb();
        String query = "select invoiceNo from t_invoiceheader where cmpCode = ? and distrCode  = ? and salesmanCode = ? and \n" +
                "routeCode = ? and customerCode = ? and orderNo  = ?";

        try {
            Cursor cm = baseDB.getDb().rawQuery(query, new String[]{cmpCode, distCode, salesmanCode, routeCode, customerCode, orderNo});

            if (isCursorTrue(cm)) {
                do {
                    invoiceNo = cm.getString(cm.getColumnIndex(COLUMN_INVOICE_NO));
                } while (cm.moveToNext());

            }
            closeCursor(cm);
        } catch (Exception e) {
            Log.e(TAG, "isInvoiceGeneratedForOrder: " + e.getMessage(), e);
            invoiceNo = "";
        }
        return invoiceNo;
    }

    List<OrderBookingVO> fetchPreviousOrderInvoiceDetails(SFADatabase baseDB, String cmpCode, String distrCode, String orderNo) {
        List<OrderBookingVO> previousOrders = new ArrayList<>();
        SQLiteDatabase database = baseDB.getReadableDatabase();
        String query = "select * from t_invoicedetails where cmpCode = ? and distrCode  = ? and invoiceNo = ?";
        Cursor c = database.rawQuery(query, new String[]{cmpCode, distrCode, orderNo});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                OrderBookingVO previousOrder = new OrderBookingVO();
                previousOrder.setOrderNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NO)));
                previousOrder.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                previousOrder.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                previousOrder.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_INVOICE_QTY1)));
                previousOrder.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE1)));
                previousOrder.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_NET_AMT))));
                previousOrders.add(previousOrder);
            }
            c.close();
        }
        return previousOrders;
    }

    public void deleteInvoiceDetails(BaseDB baseDB, String cmpCode, String distrCode, String invoiceNum) {
        SQLiteDatabase db = baseDB.getDb();
        db.execSQL("DELETE FROM " + TABLE_BILL_INVOICE_DETAILS + " WHERE cmpCode ='" + cmpCode + "' AND distrCode = '" + distrCode + "' AND invoiceNo = '" + invoiceNum + "'");
        baseDB.closeDb();
    }


    public List<StockTakeVO> getProductBatchCode(SFADatabase sfaDatabase, String cmpCode, String prodCode) {
        List<StockTakeVO> stockTakeList = new ArrayList<>();

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(QUERY_SELECT_PRODUCT_BATCH_CODE, new String[]{cmpCode, prodCode});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                StockTakeVO stockTakeVO = new StockTakeVO();
                stockTakeVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                stockTakeVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE)));
                stockTakeVO.setMrp(BigDecimal.valueOf(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_MRP_CAPS))));
                stockTakeVO.setSellPrice(BigDecimal.valueOf(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                stockTakeVO.setSellRateWithTax(BigDecimal.valueOf(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_SELL_RATE_WITH_TAX))));
                stockTakeList.add(stockTakeVO);

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }
        return stockTakeList;
    }

    public String getSalesReturnNo(SFADatabase sfaDatabase, String distrCode, String salesmanCode, String routeCode, String retrCode, String state) {
        sfaDatabase.openWritableDb();

        String query = "SELECT " + COLUMN_SALES_RETURNNO + " FROM " + TABLE_SALES_RETURN_HEADER + " WHERE distrCode=? and salesmanCode =? and routeCode=? and customerCode=? and uploadFlag=?";
        Cursor cm = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, salesmanCode, routeCode, retrCode, state});
        if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
            String salRetNo = cm.getString(cm.getColumnIndex(COLUMN_SALES_RETURNNO));
            cm.close();
            return salRetNo;
        }
        if (cm != null) {
            cm.close();
        }
        sfaDatabase.closeDb();
        return "";
    }

    public void insertSalesReturnHeaderDetails(SFADatabase sfaDatabase, String distrCode, String salesNo, String salesmanCode, String routeCode, String retrCode, String remarks) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_SALES_RETURNNO, salesNo);
        values.put(COLUMN_SALESMAN_CODE, salesmanCode);
        values.put(COLUMN_ROUTE_CODE, routeCode);
        values.put(COLUMN_CUSTOMER_CODE, retrCode);
        values.put(COLUMN_RETURN_DT, DateUtil.getCurrentYearMonthDate());
        values.put(COLUMN_INVOICE_NO, salesNo);
        values.put(COLUMN_RETURN_TYPE, "N");
        values.put(COLUMN_REMARKS, remarks);
        values.put(COLUMN_MOD_DT, DateUtil.getCurrentYearMonthDate());
        values.put(UPLOAD_FLAG, "N");
        // Inserting Row
        sfaDatabase.getDb().insert(IDBColumns.TABLE_SALES_RETURN_HEADER, null, values);
    }

    public void insertSalesReturnLineDetails(SFADatabase sfaDatabase, String distrCode, String salesNo, SalesReturnVO salesReturnVO) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_SALES_RETURNNO, salesNo);
        values.put(COLUMN_INVOICE_NO, salesNo);
        values.put(COLUMN_PROD_CODE, salesReturnVO.getProdCode());
        values.put(COLUMN_PROD_NAME, salesReturnVO.getProdName());
        values.put(COLUMN_PROD_BATCH_CODE, salesReturnVO.getProdBatchCode());
        values.put(COLUMN_BATCH_DETAIL, salesReturnVO.getBatchDetail());
        values.put(COLUMN_RETURN_QTY, salesReturnVO.getReturnQty());
        values.put(COLUMN_STOCK_TYPE, salesReturnVO.getStockType());
        values.put(COLUMN_MRP, salesReturnVO.getMrp());
        values.put(COLUMN_SELL_RATE, salesReturnVO.getSellPrice());
        values.put(COLUMN_RETURN_AMOUNT, Double.parseDouble(String.valueOf(salesReturnVO.getReturnQty())) * salesReturnVO.getSellPrice());
        values.put(COLUMN_REASON_CODE_CAPS, salesReturnVO.getReasonCode());
        values.put(COLUMN_REASON_NAME_CAPS, salesReturnVO.getReasonName());
        values.put(COLUMN_IMAGE, salesReturnVO.getReturnImage());
        values.put(COLUMN_MOD_DT, DateUtil.getCurrentYearMonthDate());
        values.put(UPLOAD_FLAG, "N");
        // Inserting Row
        sfaDatabase.getDb().insert(IDBColumns.TABLE_SALES_RETURN_DETAILS, null, values);
    }

    public void deleteSalesReturnHeader(SFADatabase sfaDatabase, String distrCode, String salesmanCode, String routeCode, String retrCode, String status) {
        String deleteReturns = "distrCode=? and  salesmanCode=? and routeCode=? AND customerCode=? AND uploadFlag = ?";
        String[] deleteArgs = {distrCode, salesmanCode, routeCode, retrCode, status};
        sfaDatabase.getDb().delete(IDBColumns.TABLE_SALES_RETURN_HEADER, deleteReturns, deleteArgs);
    }

    public void deleteSalesReturnLineDetails(SFADatabase sfaDatabase, String distrCode, String salesNo, String status) {
        String deleteReturns = "distrCode=? and  salesReturnNo=? and uploadFlag = ?";
        String[] deleteArgs = {distrCode, salesNo, status};
        sfaDatabase.getDb().delete(IDBColumns.TABLE_SALES_RETURN_DETAILS, deleteReturns, deleteArgs);
    }

    public void deleteSalesReturnTableById(SFADatabase sfaDatabase, String distrCode, String salesmanCode, String routeCode, String retrCode, String deleteSalesReturnNo, SalesReturnVO salesReturnVO, String status) {
        String query = "select * from " + TABLE_SALES_RETURN_DETAILS + " where " +
                "   cmpCode = ? \n" +
                " AND distrCode = ? and salesReturnNo = ? and uploadFlag=?";

        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, deleteSalesReturnNo, status});

        if (cursor != null && cursor.getCount() == 1) {

            deleteSalesReturnLineDetails(sfaDatabase, distrCode, deleteSalesReturnNo, status);
            deleteSalesReturnHeader(sfaDatabase, distrCode, salesmanCode, routeCode, retrCode, status);

        } else {
            String deleteReturns = "distrCode=? and  salesReturnNo=? and prodCode=? and ProdBatchCode=? and stockType=? and uploadFlag = ?";
            String[] deleteArgs = {distrCode, deleteSalesReturnNo, salesReturnVO.getProdCode(), salesReturnVO.getProdBatchCode(), salesReturnVO.getStockType(), status};
            sfaDatabase.getDb().delete(IDBColumns.TABLE_SALES_RETURN_DETAILS, deleteReturns, deleteArgs);
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    public List<SalesReturnVO> getSalesReturnHeaderDetail(SFADatabase sfaDatabase, String cmpCode, String distCode) {
        List<SalesReturnVO> salesReturnVOList = new ArrayList<>();

        String query = "Select * from " + TABLE_SALES_RETURN_HEADER + " where cmpCode=? and distrCode=? and uploadFlag='N'";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                SalesReturnVO salesReturnVO = new SalesReturnVO();
                salesReturnVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                salesReturnVO.setSalesreturnNo(c.getString(c.getColumnIndex(COLUMN_SALES_RETURNNO)));
                salesReturnVO.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)));
                salesReturnVO.setRouteCode(c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE)));
                salesReturnVO.setRetlrCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                salesReturnVO.setReturnDate(c.getString(c.getColumnIndex(COLUMN_RETURN_DT)));
                salesReturnVO.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NO)));
                salesReturnVO.setReturnType(c.getString(c.getColumnIndex(COLUMN_RETURN_TYPE)));
                salesReturnVO.setRemarks(c.getString(c.getColumnIndex(COLUMN_REMARKS)));

                salesReturnVOList.add(salesReturnVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return salesReturnVOList;
    }

    public List<SalesReturnVO> getSalesReturnDetail(SFADatabase sfaDatabase, String distrCode, String salesreturnNo) {
        List<SalesReturnVO> salesReturnVOList = new ArrayList<>();
        String query = "Select * from " + TABLE_SALES_RETURN_DETAILS + " where distrCode=? and salesReturnNo=? and uploadFlag='N'";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, salesreturnNo});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                SalesReturnVO salesReturnVO = new SalesReturnVO();

                salesReturnVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                salesReturnVO.setSalesreturnNo(c.getString(c.getColumnIndex(COLUMN_SALES_RETURNNO)));
                salesReturnVO.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NO)));
                salesReturnVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                salesReturnVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                salesReturnVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                salesReturnVO.setBatchDetail(c.getDouble(c.getColumnIndex(COLUMN_BATCH_DETAIL)));
                salesReturnVO.setReturnQty(c.getInt(c.getColumnIndex(COLUMN_RETURN_QTY)));
                salesReturnVO.setStockType(c.getString(c.getColumnIndex(COLUMN_STOCK_TYPE)));
                salesReturnVO.setReasonCode(c.getString(c.getColumnIndex(COLUMN_REASON_CODE_CAPS)));
                salesReturnVO.setReasonName(c.getString(c.getColumnIndex(COLUMN_REASON_NAME_CAPS)));
                salesReturnVO.setMrp(c.getDouble(c.getColumnIndex(COLUMN_MRP)));
                salesReturnVO.setSellPrice(c.getDouble(c.getColumnIndex(COLUMN_SELL_RATE)));
                salesReturnVO.setReturnAmt(c.getDouble(c.getColumnIndex(COLUMN_RETURN_AMOUNT)));
                salesReturnVO.setReturnImage(c.getString(c.getColumnIndex(COLUMN_IMAGE)));

                salesReturnVOList.add(salesReturnVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return salesReturnVOList;
    }

    public void updateStockOnHandAdjusmentQty(SFADatabase sfaDatabase, String cmpCode, String distrCode, String goDownCode, OrderBookingVO orderBookingVO, String stockType, String transType) {
        String stockValue = "";
        if (stockType.equalsIgnoreCase("S")) {
            stockValue = COLUMN_AVAIL_QTY;
        } else if (stockType.equalsIgnoreCase("U")) {
            stockValue = COLUMN_UNSALEABLE_QTY;
        } else if (stockType.equalsIgnoreCase("O")) {
            stockValue = COLUMN_OFFER_QTY;
        }

        String query = "Select " + stockValue + " from " + TABLE_STOCK_ON_HAND + " where cmpCode=? and distrCode=? and godownCode=? and prodCode=? and ProdBatchCode=?";
        Log.e(TAG, " updateStockOnHandAdjusmentQty query " + query);
        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{
                cmpCode, distrCode, goDownCode,
                orderBookingVO.getProdCode(), orderBookingVO.getProdBatchCode()});

        int value = 1;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            value = cursor.getInt(cursor.getColumnIndex(stockValue));
        }

        if (cursor != null) {
            cursor.close();
        }

        int stockOnHandQty = 0;
//        if (transType.equalsIgnoreCase("I")) {
//            stockOnHandQty = value + orderBookingVO.getAdjustQty();
//        } else if (transType.equalsIgnoreCase("O")) {
//            stockOnHandQty = value - orderBookingVO.getAdjustQty();
//        }

        ContentValues values = new ContentValues();

        values.put(stockValue, stockOnHandQty);
        values.put(COLUMN_UPLOAD, "N");

        String appendAnd = APPEND_AND;
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_GODOWN_CODE + appendAnd + COLUMN_PROD_CODE +
                appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";
        String[] updateArgs = new String[]{
                cmpCode, distrCode, goDownCode,
                orderBookingVO.getProdCode(), orderBookingVO.getProdBatchCode()};
        sfaDatabase.getDb().update(TABLE_STOCK_ON_HAND, values, update, updateArgs);
        sfaDatabase.closeDb();

    }

    public void updateStockonHandSalesReturn(SFADatabase sfaDatabase, String cmpCode, String distrCode, String goDownCode, SalesReturnVO salesReturnVO, boolean state) {
        String stockValue = "";
        if (salesReturnVO.getStockType().equalsIgnoreCase("S")) {
            stockValue = COLUMN_AVAIL_QTY;
        } else if (salesReturnVO.getStockType().equalsIgnoreCase("U")) {
            stockValue = COLUMN_UNSALEABLE_QTY;
        } else if (salesReturnVO.getStockType().equalsIgnoreCase("O")) {
            stockValue = COLUMN_OFFER_QTY;
        }

        String query = "Select " + stockValue + " from " + TABLE_STOCK_ON_HAND + " where cmpCode=? and distrCode=? and godownCode=? and prodCode=? and ProdBatchCode=?";
        Log.e(TAG, " updateStockonHandSalesReturn " + query);
        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{
                cmpCode, distrCode, goDownCode,
                salesReturnVO.getProdCode(), salesReturnVO.getProdBatchCode()});

        int value = 1;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            value = cursor.getInt(cursor.getColumnIndex(stockValue));
        }

        if (cursor != null) {
            cursor.close();
        }

        int stockOnHandQty = 0;
        if (state) {
            stockOnHandQty = value + salesReturnVO.getReturnQty();
        } else {
            stockOnHandQty = value - salesReturnVO.getReturnQty();
        }

        ContentValues values = new ContentValues();

        values.put(stockValue, stockOnHandQty);
        values.put(COLUMN_UPLOAD, "N");

        String appendAnd = APPEND_AND;
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_GODOWN_CODE + appendAnd + COLUMN_PROD_CODE +
                appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";
        String[] updateArgs = new String[]{
                cmpCode, distrCode, goDownCode,
                salesReturnVO.getProdCode(), salesReturnVO.getProdBatchCode()};
        sfaDatabase.getDb().update(TABLE_STOCK_ON_HAND, values, update, updateArgs);
        sfaDatabase.closeDb();

    }

    public double getConversionUomValue(SFADatabase sfaDatabase, double enteredQty, String uomCode, String prodCode) {
        double value;
        String query = "select conversionFactor from m_product_uom_master where " +
                "   cmpCode = ? \n" +
                " AND prodCode = ? and uomCode = ?";

        Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, prodCode, uomCode});

        int convFactor = 1;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            convFactor = cursor.getInt(cursor.getColumnIndex(COLUMN_CONVERSION_FACTOR));
        }
        value = convFactor * enteredQty;
        if (cursor != null) {
            cursor.close();
        }
        return value;
    }

    public List<OrderBookingVO> getStockReportData(SFADatabase sfaDatabase, boolean isCheck) {

        String QUERY_SELECT_STOCK_REPORT_DATA = "";

//        if (isCheck) {
//            QUERY_SELECT_STOCK_REPORT_DATA = "SELECT prod.prodName,\n" +
//                    "       prod.prodCode,\n" +
//                    "       prod.prodHierLastValCode,\n" +
//                    "       prod.productHierPathCode,\n" +
//                    "       prod.lobDivisionCode,\n" +
//                    "       batch.prodBatchCode,\n" +
//                    "       stock.availQty\n" +
//                    "FROM m_product_master prod\n" +
//                    "INNER JOIN m_product_batch_master batch ON batch.prodCode=prod.prodCode\n" +
//                    "INNER JOIN m_stockOnHand stock ON stock.prodCode=prod.prodCode\n" +
//                    "AND stock.prodBatchCode=batch.prodBatchCode\n" +
//                    "WHERE prod.cmpCode=?\n" +
//                    "  AND stock.distrCode=?\n" +
//                    "  AND stock.availQty > 0";
//        } else {
//            QUERY_SELECT_STOCK_REPORT_DATA = "SELECT prod.prodName,\n" +
//                    "       prod.prodCode,\n" +
//                    "       prod.prodHierLastValCode,\n" +
//                    "       prod.productHierPathCode,\n" +
//                    "       prod.lobDivisionCode,\n" +
//                    "       stock.prodBatchCode,\n" +
//                    "       SUM(stock.availQty) as availQty\n" +
//                    "FROM m_product_master prod\n" +
//                    "INNER JOIN m_stockOnHand stock ON stock.prodCode=prod.prodCode\n" +
//                    "WHERE prod.cmpCode=?\n" +
//                    "  AND stock.distrCode=?\n" +
//                    "  AND stock.availQty > 0\n" +
//                    "GROUP BY stock.prodCode";
//
//        }

        QUERY_SELECT_STOCK_REPORT_DATA = "SELECT prod.prodName,\n" +
                "       prod.prodCode,\n" +
                "       prod.prodHierLastValCode,\n" +
                "       prod.productHierPathCode,\n" +
                "       prod.lobDivisionCode,\n" +
                "       batch.prodBatchCode,\n" +
                "       batch.mrp,\n" +
                "       batch.sellprice,\n" +
                "       SUM(stock.availQty) AS availQty,\n" +
                "       SUM(stock.unSaleableQty) AS unSaleableQty,\n" +
                "       SUM(stock.offerQty) AS offerQty,\n" +
                "       SUM(stock.resvSaleableQty) AS resvSaleableQty,\n" +
                "       SUM(stock.resvUnSaleableQty) AS resvUnSaleableQty,\n" +
                "       SUM(stock.resvOfferQty) AS resvOfferQty\n" +
                "FROM m_product_master prod\n" +
                "INNER JOIN m_stockOnHand stock ON stock.prodCode=prod.prodCode\n" +
                "INNER JOIN m_product_batch_master batch ON batch.prodCode=prod.prodCode and batch.prodBatchCode=stock.prodBatchCode\n" +
                "WHERE prod.cmpCode=?\n" +
                "  AND stock.distrCode=?\n" +
                "  AND stock.availQty > 0 OR stock.unSaleableQty > 0 OR stock.offerQty > 0\n" +
                "GROUP BY stock.prodCode";

        List<OrderBookingVO> stockList = new ArrayList<>();

        Cursor c = null;
        try {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_STOCK_REPORT_DATA, new String[]{cmpCode, distrCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                    orderBookingVO.setProductHierPath(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
//                    orderBookingVO.setLobDivisionCode(c.getString(c.getColumnIndex(COLUMN_PROD_LOB_DIVISTION_CODE)));
//
//                    orderBookingVO.setProdHierLastValCode(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                    orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                    orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));


                    orderBookingVO.setConversionFactor(getConversionFactor(sfaDatabase, orderBookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));

                    int saleableQty = c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)) - c.getInt(c.getColumnIndex(COLUMN_RESV_SALEABLE_QTY));
                    int unsaleableQty = c.getInt(c.getColumnIndex(COLUMN_UNSALEABLE_QTY)) - c.getInt(c.getColumnIndex(COLUMN_RESV_UNSALEABLE_QTY));
                    int offerQty = c.getInt(c.getColumnIndex(COLUMN_OFFER_QTY)) - c.getInt(c.getColumnIndex(COLUMN_RESV_OFFER_QTY));
                    if (saleableQty <= 0) {
                        saleableQty = 0;
                    }
                    if (unsaleableQty <= 0) {
                        unsaleableQty = 0;
                    }
                    if (offerQty <= 0) {
                        offerQty = 0;
                    }
                    orderBookingVO.setAvailQty(saleableQty);
                    orderBookingVO.setUnSalQty(unsaleableQty);
//                    orderBookingVO.setOfferStock(offerQty);

                    stockList.add(orderBookingVO);

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
            sfaDatabase.closeDb();
        }

        return stockList;
    }

    public SchemeDistrBudgetModel selectSchemeUtilizedBudget(SQLiteDatabase db, String schemeCode) {

        String query = "select budget,utilizedAmount from m_schemeDistrBudget where schemeCode = ?";
        SchemeDistrBudgetModel model = null;

        Cursor c = null;
        try {
            c = db.rawQuery(query, new String[]{schemeCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    model = new SchemeDistrBudgetModel();
                    model.setUtilizedAmount(Double.valueOf(c.getString(c.getColumnIndex(COLUMN_UTILIZED_AMOUNT))));
                    model.setBudget(Double.valueOf(c.getString(c.getColumnIndex(COLUMN_BUDGET))));
                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }

        return model;
    }

    void updateSchemeDistrBudget(BaseDB baseDB, List<SchemeModel> schemeModels) {

        for (SchemeModel schemeModel : schemeModels) {
            ContentValues values = null;
            SchemeDistrBudgetModel model = selectSchemeUtilizedBudget(baseDB.getDb(), schemeModel.getSchemeCode());
            if (model != null)
                if ((model.getBudget() - model.getUtilizedAmount()) >= schemeModel.getFlatAmount()) {
                    values = new ContentValues();
                    values.put(COLUMN_UTILIZED_AMOUNT, model.getUtilizedAmount() + schemeModel.getFlatAmount());
                    values.put(UPLOAD_FLAG, "N");
                }

            String update = SCHEME_CODE + " = ?";

            String[] updateArgs = {schemeModel.getSchemeCode()};

            if (values != null)
                baseDB.getDb().update(TABLE_SCHEME_DISTRIBUTOR_BUDGET, values, update, updateArgs);
        }
        baseDB.closeDb();
    }

    public List<SchemeModel> getInvoiceSchemeLineItem(BaseDB baseDB, String salesmanCode, String routeCode, String customerCode, String invoiceNo,
                                                      String payoutType) {

        SFASharedPref preference = SFASharedPref.getOurInstance();
        String query = "";
//        if (payoutType.equals(SCHEME_PAYOUT_DISCOUNT) || payoutType.equals(SCHEME_PAYOUT_AMOUNT)) {
        query = "Select SUM(discountAmount) as discountAmount, SUM(freeQty) as freeQty,orderNo,prodCode,BatchCode,cmpCode, distrCode,freeProdCode,schemeCode,slabNo,freeQty,discountPercen from " + TABLE_ORDERED_APPLIED_SCHEME + " where distrCode=? and cmpCode=? and customerCode=? and orderNo=? and discountAmount>0.0 group by prodCode";
//        } else if (payoutType.equals(SCHEME_PAYOUT_FREE_PROD)) {
//            query = "Select SUM(freeQty) as freeQty,orderNo,prodCode,BatchCode,freeProdCode,schemeCode,slabNo,discountAmount,discountPercen from " + TABLE_BILLING_APPLIED_SCHEME + " where distrCode=? and cmpCode=? and customerCode=? and orderNo=? and freeQty>0 group by freeProdCode";
//        }

        Cursor c = baseDB.getDb().rawQuery(query, new String[]{routeCode, salesmanCode, customerCode, invoiceNo});
        List<SchemeModel> orderBookingList = new ArrayList<>();
        try {
            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {

                    SchemeModel schemeModel = new SchemeModel();

                    schemeModel.setCmpCode(checkString(c, COLUMN_CMP_CODE));
                    schemeModel.setDistrCode(checkString(c, COLUMN_DISTR_CODE));
                    schemeModel.setInvoiceNo(checkString(c, ORDER_NO));
                    schemeModel.setProductCode(checkString(c, COLUMN_PROD_CODE));
                    schemeModel.setProdBatchCode(checkString(c, COLUMN_BATCH_CODE));
                    schemeModel.setFreeProdCode(c.getString(c.getColumnIndex(FREE_PROD_CODE)));
                    schemeModel.setSchemeCode(c.getString(c.getColumnIndex(SCHEME_CODE)));
                    schemeModel.setSlabNo(c.getString(c.getColumnIndex(SCHEME_SLAB_NO)));
                    schemeModel.setFreeQty(c.getInt(c.getColumnIndex(FREE_QTY)));
                    schemeModel.setDicountAmount(c.getString(c.getColumnIndex(DISCOUNT_AMOUNT)));
                    schemeModel.setDicountPercentage(c.getString(c.getColumnIndex(DISCOUNT_PERCENTAGE)));
                    orderBookingList.add(schemeModel);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getVANSalesSchemeLineItem: " + e.getMessage(), e);
        } finally {
            if (c != null)
                c.close();
        }

        baseDB.closeDb();
        return orderBookingList;
    }

    OrderBookingVO getBatchCodeOfFreeProduct(BaseDB baseDB, String prodCode, int offerQty, boolean isMandatory) {

        OrderBookingVO order = null;
        SFASharedPref preference = SFASharedPref.getOurInstance();
        String distrCode = preference.readString(PREF_DISTRCODE);
        String query = "SELECT soh.prodBatchCode,\n" +
                "       soh.offerQty,\n" +
                "       soh.availQty,\n" +
                "       pbm.MRP,\n" +
                "       pbm.SellPrice\n" +
                "FROM m_stockOnHand soh\n" +
                "INNER JOIN m_product_batch_master pbm ON soh.prodCode = pbm.prodCode\n" +
                "AND soh.prodBatchCode = pbm.prodBatchCode\n" +
                "WHERE soh.distrCode = ?    \n" +
                "  AND soh.prodCode = ?";

        List<OrderBookingVO> offerProducts = new ArrayList<>();
        try (Cursor c = baseDB.getDb().rawQuery(query, new String[]{distrCode, prodCode})) {
            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    OrderBookingVO product = new OrderBookingVO();

                    product.setProdCode(prodCode);
                    product.setProdBatchCode(checkString(c, COLUMN_PROD_BATCH_CODE));
//                    product.setOfferStock(c.getInt(c.getColumnIndex(COLUMN_OFFER_QTY)));
                    product.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                    product.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                    product.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));

                    offerProducts.add(product);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getBatchCodeOfFreeProduct: " + e.getMessage(), e);
        }

        baseDB.closeDb();

        if (isMandatory) {
            for (OrderBookingVO orderBookingVO : offerProducts) {
                order = new OrderBookingVO();
                order.setProdBatchCode(orderBookingVO.getProdBatchCode());
                order.setSellPrice(orderBookingVO.getSellPrice());
                order.setMrp(orderBookingVO.getMrp());
            }
        } else {
            for (OrderBookingVO orderBookingVO : offerProducts) {
//                if (orderBookingVO.getOfferStock() > 0) {
//                    if (orderBookingVO.getOfferStock() >= offerQty) {
//                        order = new OrderBookingVO();
//                        order.setProdBatchCode(orderBookingVO.getProdBatchCode());
//                        order.setSellPrice(orderBookingVO.getSellPrice());
//                        order.setMrp(orderBookingVO.getMrp());
//                        break;
//                    } else if (orderBookingVO.getOfferStock() + orderBookingVO.getAvailQty() >= offerQty) {
//                        order = new OrderBookingVO();
//                        order.setProdBatchCode(orderBookingVO.getProdBatchCode());
//                        order.setSellPrice(orderBookingVO.getSellPrice());
//                        order.setMrp(orderBookingVO.getMrp());
//                        break;
//                    }
//                } else if (orderBookingVO.getAvailQty() > 0) {
//                    if (orderBookingVO.getAvailQty() >= offerQty) {
//                        order = new OrderBookingVO();
//                        order.setProdBatchCode(orderBookingVO.getProdBatchCode());
//                        order.setSellPrice(orderBookingVO.getSellPrice());
//                        order.setMrp(orderBookingVO.getMrp());
//                        break;
//                    }
//                }
            }
        }

        return order;
    }

    void updateStockOnHandOfferQty(BaseDB baseDB, String prodCode, String prodBatchCode, int offerQty) {

        SFASharedPref preference = SFASharedPref.getOurInstance();
        String distrCode = preference.readString(PREF_DISTRCODE);
        String query = "select offerQty, availQty from m_stockOnHand where distrCode = ? and prodCode = ? and prodBatchCode = ?\n";

        OrderBookingVO free = null;
        List<OrderBookingVO> offerProducts = new ArrayList<>();
        try (Cursor c = baseDB.getDb().rawQuery(query, new String[]{distrCode, prodCode, prodBatchCode})) {
            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    OrderBookingVO product = new OrderBookingVO();

                    product.setProdCode(prodCode);
                    product.setProdBatchCode(prodBatchCode);
//                    product.setOfferStock(c.getInt(c.getColumnIndex(COLUMN_OFFER_QTY)));
                    product.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                    offerProducts.add(product);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getBatchCodeOfFreeProduct: " + e.getMessage(), e);
        }

        baseDB.closeDb();

        for (OrderBookingVO orderBookingVO : offerProducts) {

//            if (orderBookingVO.getOfferStock() > 0) {
//                if (orderBookingVO.getOfferStock() >= offerQty) {
//                    free = new OrderBookingVO();
//                    free.setProdCode(orderBookingVO.getProdCode());
//                    free.setProdBatchCode(orderBookingVO.getProdBatchCode());
//                    free.setOfferStock(orderBookingVO.getOfferStock());
//                    free.setAvailQty(0);
//                    break;
//                } else if ((orderBookingVO.getOfferStock() + orderBookingVO.getAvailQty()) >= offerQty) {
//                    free = new OrderBookingVO();
//                    free.setProdCode(orderBookingVO.getProdCode());
//                    free.setProdBatchCode(orderBookingVO.getProdBatchCode());
//                    free.setOfferStock(orderBookingVO.getOfferStock());
//                    free.setAvailQty(orderBookingVO.getAvailQty());
//
//                    int adjustQty = orderBookingVO.getOfferStock() + orderBookingVO.getAvailQty() - offerQty;
//                    free.setAdjustQty(adjustQty);
//                    break;
//                }
//            } else if (orderBookingVO.getAvailQty() > 0) {
//                if (orderBookingVO.getAvailQty() >= offerQty) {
//                    free = orderBookingVO;
//                    break;
//                }
//            }
        }

        if (free != null) {
            ContentValues values = null;
//            if (free.getAdjustQty() > 0) {
//
//                values = new ContentValues();
//                values.put(COLUMN_OFFER_QTY, 0);
//                values.put(COLUMN_AVAIL_QTY, free.getAdjustQty());
//                values.put(COLUMN_UPLOAD, "N");
//
//            } else if (free.getAvailQty() > 0) {
//
//                values = new ContentValues();
//                values.put(COLUMN_AVAIL_QTY, free.getAvailQty() - offerQty);
//                values.put(COLUMN_UPLOAD, "N");
//
//            } else if (free.getOfferStock() > 0) {
//
//                values = new ContentValues();
//                values.put(COLUMN_OFFER_QTY, free.getOfferStock() - offerQty);
//                values.put(COLUMN_UPLOAD, "N");
//            }

            String update = COLUMN_DISTR_CODE + " = ? and " + COLUMN_PROD_CODE + " = ? and " + PROD_BATCH_CODE + " = ?";
            String[] updateArgs = {distrCode, prodCode, prodBatchCode};
            if (values != null)
                baseDB.getDb().update(TABLE_STOCK_ON_HAND, values, update, updateArgs);
        }
    }

    List<SchemeDistrBudgetModel> getSchemeDistrBudgetForUpload(BaseDB baseDB, String distributorCode) {
        List<SchemeDistrBudgetModel> budgetModels = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT * From " + TABLE_SCHEME_DISTRIBUTOR_BUDGET + " WHERE distrCode=? AND uploadFlag = 'N'";
        cursor = baseDB.getDb().rawQuery(query, new String[]{distributorCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                SchemeDistrBudgetModel routeModel = new SchemeDistrBudgetModel();

                routeModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)).trim());
                routeModel.setDistrCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                routeModel.setSchemeCode(cursor.getString(cursor.getColumnIndex(SCHEME_CODE)).trim());
                routeModel.setIsUnlimited(cursor.getString(cursor.getColumnIndex(COLUMN_IS_UNLIMITED)).trim());
                routeModel.setBudget(cursor.getDouble(cursor.getColumnIndex(COLUMN_BUDGET)));
                routeModel.setIsActive(cursor.getString(cursor.getColumnIndex(COLUMN_IS_ACTIVE)).trim());
                routeModel.setUtilizedAmount(cursor.getDouble(cursor.getColumnIndex(COLUMN_UTILIZED_AMOUNT)));
                routeModel.setBudgetOs(cursor.getDouble(cursor.getColumnIndex(COLUMN_BUDGET_OS)));
                routeModel.setUploadFlag("N");

                budgetModels.add(routeModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return budgetModels;
    }

    SchemeModel getFreeProductDetails(BaseDB baseDB, String distrCode, String salesmanCode, String routeCode,
                                      String customerCode, String orderNo, String prodCode, String BatchCode) {
        Cursor cursor;
        String query = "select * from t_billingAppliedSchemes where  distrCode = ? and salesmanCode = ?" +
                " and routeCode = ? and customerCode = ? and orderNo = ? and prodCode = ? and BatchCode = ?";
        cursor = baseDB.getDb().rawQuery(query, new String[]{distrCode, salesmanCode, routeCode, customerCode, orderNo, prodCode, BatchCode});
        SchemeModel model = null;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                model = new SchemeModel();

                model.setFreeProdCode(cursor.getString(cursor.getColumnIndex(FREE_PROD_CODE)).trim());
                model.setFreeProdBatchCode(cursor.getString(cursor.getColumnIndex(FREE_PROD_BATCH_CODE)).trim());

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return model;
    }

    List<SalesDBCRNoteModel> getSalesDBCRForCustomer(BaseDB baseDB, String cmpCode, String distrCode, String customerCode) {
        List<SalesDBCRNoteModel> salesDBCRNoteModelArrayList = new ArrayList<>();
        Cursor cursor;
        String query = "select * from m_salesdbcrnote where cmpCode = ? and distrCode = ? and customerCode = ? and isSettled = ? and dbCRType = ? order by dbCRDt asc";
        cursor = baseDB.getDb().rawQuery(query, new String[]{cmpCode, distrCode, customerCode, "N", "C"});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                SalesDBCRNoteModel salesDBCRNoteModel = new SalesDBCRNoteModel();

                salesDBCRNoteModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                salesDBCRNoteModel.setDistrCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)));
                salesDBCRNoteModel.setCustomerCode(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                salesDBCRNoteModel.setDbCRType(cursor.getString(cursor.getColumnIndex(COLUMN_DB_CRTYPE)));
                salesDBCRNoteModel.setSalesDBCRRefNo(cursor.getString(cursor.getColumnIndex(COLUMN_SALES_DB_CRREFNO)));
                salesDBCRNoteModel.setDbCRDt(cursor.getString(cursor.getColumnIndex(COLUMN_DB_CRDT)));
                salesDBCRNoteModel.setDbCRReason(cursor.getString(cursor.getColumnIndex(COLUMN_DB_CRREASON)));
                salesDBCRNoteModel.setDbCRAmt(cursor.getDouble(cursor.getColumnIndex(COLUMN_DB_CRAMT)));
                salesDBCRNoteModel.setDbCRBal(cursor.getDouble(cursor.getColumnIndex(COLUMN_DB_CRBAL)));
                salesDBCRNoteModel.setIsSettled(cursor.getString(cursor.getColumnIndex(COLUMN_IS_SETTLED)));
                salesDBCRNoteModel.setRemarks(cursor.getString(cursor.getColumnIndex(COLUMN_REMARKS)));
                salesDBCRNoteModel.setIsActive(cursor.getString(cursor.getColumnIndex(COLUMN_IS_ACTIVE)));
                salesDBCRNoteModel.setIsClaimable(cursor.getString(cursor.getColumnIndex(COLUMN_IS_CLAIMABLE)));
                salesDBCRNoteModel.setReferenceType(cursor.getString(cursor.getColumnIndex(COLUMN_REFERENCE_TYPE)));
                salesDBCRNoteModel.setReferenceNo(cursor.getString(cursor.getColumnIndex(COLUMN_REFERENCE_NO)));

                salesDBCRNoteModelArrayList.add(salesDBCRNoteModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return salesDBCRNoteModelArrayList;
    }

    public void updateSalesDBCRNote(SFADatabase sfaDatabase, String cmpCode, String distrCode, String customerCode, SalesDBCRNoteModel salesDBCRNoteModel) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DB_CRBAL, salesDBCRNoteModel.getDbCRBal());
        values.put(UPLOAD_FLAG, "N");
        values.put(COLUMN_IS_SETTLED, salesDBCRNoteModel.getIsSettled());

        String appendAnd = APPEND_AND;
        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_CUSTOMER_CODE + appendAnd + COLUMN_SALES_DB_CRREFNO + " = ?";
        String[] updateArgs = new String[]{cmpCode, distrCode, customerCode, salesDBCRNoteModel.getSalesDBCRRefNo()};
        sfaDatabase.getDb().update(TABLE_SALES_DB_CRNOTE, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void updateInvoiceHeaderTotCrNoteAmt(SFADatabase sfaDatabase, String cmpCode, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo, double totCrNoteAmt) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOT_CR_NOTE_AMT, totCrNoteAmt);
        String update = "cmpCode=? and distrCode=? and salesmanCode=? and routeCode=? and customerCode=? and invoiceNo=?";
        String[] updateArgs = {cmpCode, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo};
        sfaDatabase.getDb().update(TABLE_BILL_INVOIC_HEADER, values, update, updateArgs);
    }

    public void insertInvoiceCRDBAdjustment(SFADatabase sfaDatabase, String cmpCode, String distrCode, String invoiceNo, SalesDBCRNoteModel salesDBCRNoteModel) {
        double crAmt = salesDBCRNoteModel.getAdjAmt() + salesDBCRNoteModel.getDbCRBal();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_INVOICE_NO, invoiceNo);
        values.put(COLUMN_SALES_DB_CRREFNO, salesDBCRNoteModel.getSalesDBCRRefNo());
        values.put(COLUMN_DB_CRTYPE, salesDBCRNoteModel.getDbCRType());
        values.put(COLUMN_ADJUSTED_AMT, salesDBCRNoteModel.getAdjAmt());
        values.put(UPLOAD_FLAG, "N");
        values.put(COLUMN_MOD_DT, System.currentTimeMillis());
        values.put(COLUMN_DB_CRDT, salesDBCRNoteModel.getDbCRDt());
        values.put(COLUMN_DB_CRAMT, crAmt);
        values.put(COLUMN_DB_CRBAL, salesDBCRNoteModel.getDbCRBal());
        values.put(COLUMN_DB_CRREASON, salesDBCRNoteModel.getDbCRReason());

        // Inserting Row
        sfaDatabase.getDb().insert(TABLE_INVOICE_CR_DB_ADJUSTMENT, null, values);
    }

    public List<SalesDBCRNoteModel> getInvoiceCRDBAdjustmentDetails(SFADatabase sfaDatabase, String cmpCode, String distrCode) {
        List<SalesDBCRNoteModel> infoVOList = new ArrayList<>();

        String query = "Select * from " + TABLE_INVOICE_CR_DB_ADJUSTMENT + " where cmpCode=? and distrCode=? and uploadFlag=?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, "N"});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                SalesDBCRNoteModel infoVO = new SalesDBCRNoteModel();
                infoVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                infoVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                infoVO.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NO)));
                infoVO.setSalesDBCRRefNo(c.getString(c.getColumnIndex(COLUMN_SALES_DB_CRREFNO)));
                infoVO.setDbCRType(c.getString(c.getColumnIndex(COLUMN_DB_CRTYPE)));
                infoVO.setAdjAmt(c.getDouble(c.getColumnIndex(COLUMN_ADJUSTED_AMT)));
                infoVO.setModDt(c.getString(c.getColumnIndex(COLUMN_MOD_DT)));
                infoVO.setUploadFlag(c.getString(c.getColumnIndex(UPLOAD_FLAG)));

                infoVOList.add(infoVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return infoVOList;
    }

    public List<SalesDBCRNoteModel> getSalesDBCRNoteDetails(SFADatabase sfaDatabase, String cmpCode, String distrCode) {
        List<SalesDBCRNoteModel> infoVOList = new ArrayList<>();

        String query = "Select * from " + TABLE_SALES_DB_CRNOTE + " where cmpCode=? and distrCode=? and uploadFlag=?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, "N"});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                SalesDBCRNoteModel infoVO = new SalesDBCRNoteModel();
                infoVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
                infoVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                infoVO.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                infoVO.setSalesDBCRRefNo(c.getString(c.getColumnIndex(COLUMN_SALES_DB_CRREFNO)));
                infoVO.setDbCRType(c.getString(c.getColumnIndex(COLUMN_DB_CRTYPE)));
                infoVO.setDbCRDt(c.getString(c.getColumnIndex(COLUMN_DB_CRDT)));
                infoVO.setDbCRReason(c.getString(c.getColumnIndex(COLUMN_DB_CRREASON)));
                infoVO.setDbCRBudget(c.getDouble(c.getColumnIndex(COLUMN_DB_CRBUDGET)));
                infoVO.setDbCRTaxAmt(c.getDouble(c.getColumnIndex(COLUMN_DB_CRTAXAMT)));
                infoVO.setDbCRAmt(c.getDouble(c.getColumnIndex(COLUMN_DB_CRAMT)));
                infoVO.setDbCRBal(c.getDouble(c.getColumnIndex(COLUMN_DB_CRBAL)));
                infoVO.setIsSettled(c.getString(c.getColumnIndex(COLUMN_IS_SETTLED)));
                infoVO.setRemarks(c.getString(c.getColumnIndex(COLUMN_REMARKS)));
                infoVO.setIsActive(c.getString(c.getColumnIndex(COLUMN_IS_ACTIVE)));
                infoVO.setIsClaimable(c.getString(c.getColumnIndex(COLUMN_IS_CLAIMABLE)));
                infoVO.setReferenceType(c.getString(c.getColumnIndex(COLUMN_REFERENCE_TYPE)));
                infoVO.setReferenceNo(c.getString(c.getColumnIndex(COLUMN_REFERENCE_NO)));
                infoVO.setModDt(c.getString(c.getColumnIndex(COLUMN_MOD_DT)));
                infoVO.setUploadFlag(c.getString(c.getColumnIndex(UPLOAD_FLAG)));

                infoVOList.add(infoVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return infoVOList;
    }

    List<OrderBookingVO> getDelProdById(SFADatabase sfaDatabase, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo, OrderBookingVO orderBookingVO) {
        List<OrderBookingVO> infoVOList = new ArrayList<>();

        String query = "Select * from " + TABLE_BILL_INVOICE_DETAILS + " where distrCode=? and salesmanCode=? and routeCode=? and customerCode=? and invoiceNo=? and parentProdCode=? and ParentProdBatchCode=? and uploadFlag=?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, orderBookingVO.getProdCode(), orderBookingVO.getProdBatchCode(), "N"});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                OrderBookingVO infoVO = new OrderBookingVO();
//                infoVO.setTotOrderQty(c.getInt(c.getColumnIndex(COLUMN_TOTAL_ORD_QTY)));
//                infoVO.setFreeQty(c.getInt(c.getColumnIndex(FREE_QTY)));
//                infoVO.setParentProdCode(c.getString(c.getColumnIndex(COLUMN_PARENT_PROD_CODE)));
//                infoVO.setParentProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PARENT_PROD_BATCH_CODE)));
                infoVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                infoVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));

                infoVOList.add(infoVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return infoVOList;
    }

    public void toDeleteAndUpdateBillingProduct(SFADatabase baseDB, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo, OrderBookingVO orderBookingVO) {

//        if (orderBookingVO.getProdCode().equalsIgnoreCase(orderBookingVO.getParentProdCode()) &&
//                orderBookingVO.getProdBatchCode().equalsIgnoreCase(orderBookingVO.getParentProdBatchCode())) {
//            String deleteOrderBooking = "distrCode=? and  salesmanCode=? and routeCode=? and customerCode=? and invoiceNo=? and parentProdCode=? and parentProdBatchCode=? and uploadFlag=?";
//            String[] deleteArgs = {distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, orderBookingVO.getParentProdCode(), orderBookingVO.getParentProdBatchCode(), "N"};
//            baseDB.getDb().delete(TABLE_BILL_INVOICE_DETAILS, deleteOrderBooking, deleteArgs);
//
//            String deleteAppliedScheme = "distrCode=? and  salesmanCode=? and routeCode=? and customerCode=? and orderNo=? and prodCode=? and batchCode=? and upload=?";
//            baseDB.getDb().delete(TABLE_BILLING_APPLIED_SCHEME, deleteAppliedScheme, deleteArgs);
//            baseDB.closeDb();
//        } else {
//
//            if (orderBookingVO.getTotOrderQty() > 0) {
        ContentValues values = new ContentValues();
        values.put(FREE_QTY, 0);

        String appendAnd = APPEND_AND;
        String update = COLUMN_DISTR_CODE + appendAnd + COLUMN_SALESMAN_CODE + appendAnd + COLUMN_ROUTE_CODE + appendAnd
                + COLUMN_CUSTOMER_CODE + appendAnd + COLUMN_INVOICE_NO + appendAnd + COLUMN_PARENT_PROD_CODE +
                appendAnd + COLUMN_PARENT_PROD_BATCH_CODE + " = ?";
//                String[] updateArgs = {distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, orderBookingVO.getParentProdCode(), orderBookingVO.getParentProdBatchCode()};
//
//                baseDB.getDb().update(TABLE_BILL_INVOICE_DETAILS, values, update, updateArgs);
        baseDB.closeDb();
//            } else {

//                String deleteOrderBooking = "distrCode=? and  salesmanCode=? and routeCode=? and customerCode=? and invoiceNo=? and parentProdCode=? and parentProdBatchCode=? and uploadFlag=?";
//                String[] deleteArgs = {distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, orderBookingVO.getParentProdCode(), orderBookingVO.getParentProdBatchCode(), "N"};
//                baseDB.getDb().delete(TABLE_BILL_INVOICE_DETAILS, deleteOrderBooking, deleteArgs);

//            if (orderBookingVO.getFreeQty() > 0) {
//                SchemeModel freeModel = getFreeProductDetails(baseDB, distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, bookingVO.getProdCode(), bookingVO.getProdBatchCode());
//                if (freeModel != null) {
//                    String[] args = {distrCode, salesmanCode, routeCode, retlrCode, invoiceNo, freeModel.getFreeProdCode(), freeModel.getFreeProdBatchCode(), "N"};
//                    baseDB.getDb().delete(TABLE_BILL_INVOICE_DETAILS, deleteOrderBooking, args);
//                }
//            }

//                String deleteAppliedScheme = "distrCode=? and  salesmanCode=? and routeCode=? and customerCode=? and orderNo=? and prodCode=? and batchCode=? and upload=?";
//                baseDB.getDb().delete(TABLE_BILLING_APPLIED_SCHEME, deleteAppliedScheme, deleteArgs);
//                baseDB.closeDb();
//            }
//        }
    }

    void updateBillingAppliedSchemesStatus(BaseDB baseDB, String distrCode, String salesmanCode, String routeCode, String customerCode, String invoiceNo) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLETE_FLAG, "Y");

        String update = "distrCode = ? and salesmanCode = ?  and routeCode = ? and customerCode = ? and orderNo = ?";

        String[] updateArgs = {distrCode, salesmanCode, routeCode, customerCode, invoiceNo};

        baseDB.getDb().update(TABLE_BILLING_APPLIED_SCHEME, values, update, updateArgs);
        baseDB.closeDb();
    }

    public List<SchemeModel> getSchemeFreeProducts(SFADatabase sfaDatabase, String cmpCode, String schemeCode, String slabNo) {
        List<SchemeModel> infoVOList = new ArrayList<>();

//        String query = "select prodCode from m_schemeSlabProductList where cmpCode = 'AMUL' and schemeCode = 'SCH06058' and slabNo = '1'";
//        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, "N"});
//        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
//            do {
//
//                SalesDBCRNoteModel infoVO = new SalesDBCRNoteModel();
//                infoVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)).trim());
//                infoVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
//                infoVO.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
//                infoVO.setSalesDBCRRefNo(c.getString(c.getColumnIndex(COLUMN_SALES_DB_CRREFNO)));
//                infoVO.setDbCRType(c.getString(c.getColumnIndex(COLUMN_DB_CRTYPE)));
//                infoVO.setDbCRDt(c.getString(c.getColumnIndex(COLUMN_DB_CRDT)));
//                infoVO.setDbCRReason(c.getString(c.getColumnIndex(COLUMN_DB_CRREASON)));
//                infoVO.setDbCRBudget(c.getDouble(c.getColumnIndex(COLUMN_DB_CRBUDGET)));
//                infoVO.setDbCRTaxAmt(c.getDouble(c.getColumnIndex(COLUMN_DB_CRTAXAMT)));
//                infoVO.setDbCRAmt(c.getDouble(c.getColumnIndex(COLUMN_DB_CRAMT)));
//                infoVO.setDbCRBal(c.getDouble(c.getColumnIndex(COLUMN_DB_CRBAL)));
//                infoVO.setIsSettled(c.getString(c.getColumnIndex(COLUMN_IS_SETTLED)));
//                infoVO.setRemarks(c.getString(c.getColumnIndex(COLUMN_REMARKS)));
//                infoVO.setIsActive(c.getString(c.getColumnIndex(COLUMN_IS_ACTIVE)));
//                infoVO.setIsClaimable(c.getString(c.getColumnIndex(COLUMN_IS_CLAIMABLE)));
//                infoVO.setReferenceType(c.getString(c.getColumnIndex(COLUMN_REFERENCE_TYPE)));
//                infoVO.setReferenceNo(c.getString(c.getColumnIndex(COLUMN_REFERENCE_NO)));
//                infoVO.setModDt(c.getString(c.getColumnIndex(COLUMN_MOD_DT)));
//                infoVO.setUploadFlag(c.getString(c.getColumnIndex(UPLOAD_FLAG)));
//
//                infoVOList.add(infoVO);
//
//            } while (c.moveToNext());
//        }
//        if (c != null) {
//            c.close();
//        }
        return infoVOList;
    }

    public void updateBillingFreeQty(BaseDB sfaDatabase, String cmpCode, String distrCode,
                                     String salesmanCode, String routeCode, String retrCode, String invoiceNo, OrderBookingVO billingInfoVO) {

        ContentValues values = new ContentValues();
        values.put(FREE_QTY, billingInfoVO.getFreeQty());

        String update = "cmpCode=? and distrCode=? and salesmanCode=? and routeCode=? and customerCode=? and invoiceNo=? and prodCode = ? and prodBatchCode = ?";
        String[] updateArgs = {cmpCode, distrCode, salesmanCode, routeCode, retrCode,
                invoiceNo, billingInfoVO.getProdCode(), billingInfoVO.getProdBatchCode()};

        sfaDatabase.getDb().update(TABLE_BILL_INVOICE_DETAILS, values, update, updateArgs);
    }

    public List<SalesDBCRNoteModel> fetchCreditNoteForInvoice(BaseDB baseDB, String cmpCode, String distrCode, String invoiceNo) {
        List<SalesDBCRNoteModel> salesDBCRNoteModelArrayList = new ArrayList<>();
        Cursor cursor;
        String query = "select * from t_invoicecrdbadjustment where cmpCode = ? and distrCode = ? and invoiceNo = ?";
        cursor = baseDB.getDb().rawQuery(query, new String[]{cmpCode, distrCode, invoiceNo});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                SalesDBCRNoteModel salesDBCRNoteModel = new SalesDBCRNoteModel();

                salesDBCRNoteModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)));
                salesDBCRNoteModel.setDistrCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)));
                salesDBCRNoteModel.setInvoiceNo(cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_NO)));
                salesDBCRNoteModel.setDbCRType(cursor.getString(cursor.getColumnIndex(COLUMN_DB_CRTYPE)));
                salesDBCRNoteModel.setSalesDBCRRefNo(cursor.getString(cursor.getColumnIndex(COLUMN_SALES_DB_CRREFNO)));
                salesDBCRNoteModel.setAdjAmt(cursor.getDouble(cursor.getColumnIndex(COLUMN_ADJUSTED_AMT)));
                salesDBCRNoteModel.setDbCRDt(cursor.getString(cursor.getColumnIndex(COLUMN_DB_CRDT)));
                salesDBCRNoteModel.setDbCRAmt(cursor.getDouble(cursor.getColumnIndex(COLUMN_DB_CRAMT)));
                salesDBCRNoteModel.setDbCRBal(cursor.getDouble(cursor.getColumnIndex(COLUMN_DB_CRBAL)));
                salesDBCRNoteModel.setDbCRReason(cursor.getString(cursor.getColumnIndex(COLUMN_DB_CRREASON)));

                salesDBCRNoteModelArrayList.add(salesDBCRNoteModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return salesDBCRNoteModelArrayList;
    }

    public List<OrderBookingVO> getlinelevelTaxDetail(BaseDB baseDB, String distrCode, String salesmanCode, String routeCode, String retlrCode, String invoiceNo) {
        List<OrderBookingVO> orderBookingVOList = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT invoiceno as invoiceNo,\n" +
                "       grossAmt AS grossAmt,\n" +
                "       CGSTPerc              AS CGSTPerc,\n" +
                "       CGSTAmt            AS CGSTValue,\n" +
                "       SGSTPerc              AS SGSTPerc,\n" +
                "       SGSTAmt              AS SGSTValue,\n" +
                "       UTGSTPerc              AS UTGSTPerc,\n" +
                "       UTGSTValue              AS UTGSTValue,\n" +
                "       IGSTPerc              AS IGSTPerc,\n" +
                "       IGSTAmt              AS IGSTValue,\n" +
                "       CESSPerc              AS CESSPerc,\n" +
                "       CESSValue              AS CESSValue,\n" +
                "       schDiscAmt AS discountAmt,\n" +
                "       cashDiscAmt AS cashDiscountAmt,\n" +
                "       dbDiscAmt AS dbDiscountAmt,\n" +
                "       taxAmt AS taxAmt\n" +
                "FROM   t_invoicedetails \n" +
                "WHERE  distrcode = '" + distrCode + "' \n" +
                "       AND salesmancode = '" + salesmanCode + "' \n" +
                "       AND routecode = '" + routeCode + "' \n" +
                "       AND customerCode = '" + retlrCode + "' \n" +
                "       AND invoiceNo ='" + invoiceNo + "'";
//                "       AND uploadFlag = 'N'";
        cursor = baseDB.getDb().rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setOrderValue(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("grossAmt"))));
                orderBookingVO.setTotalAmount(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("grossAmt"))));
                orderBookingVO.setCgstvalue(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex(CGST_VALUE))));
                orderBookingVO.setSgstPerc(cursor.getDouble(cursor.getColumnIndex(SGST_PERCENT)));
                orderBookingVO.setSgstValue(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex(SGST_VALUE))));
                orderBookingVO.setIgstPerc(cursor.getDouble(cursor.getColumnIndex(IGST_PERCENT)));
                orderBookingVO.setIgstvalue(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex(IGST_VALUE))));
                orderBookingVO.setUtgstPerc(cursor.getDouble(cursor.getColumnIndex(UTGST_PERCENT)));
                orderBookingVO.setUtgstValue(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex(UTGST_VALUE))));
//                orderBookingVO.setCessPerc(cursor.getDouble(cursor.getColumnIndex(CESS_PERCENT)));
//                orderBookingVO.setCessValue(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex(CESS_VALUE))));
//                orderBookingVO.setSchemeAmount(BigDecimal.valueOf(cursor.getDouble(cursor.getColumnIndex("discountAmt"))));
//                orderBookingVO.setTax(cursor.getDouble(cursor.getColumnIndex("taxAmt")));
//                orderBookingVO.setCashDiscAmt(new BigDecimal(cursor.getString(cursor.getColumnIndex("cashDiscountAmt"))));
//                orderBookingVO.setDbDiscountAmt(cursor.getDouble(cursor.getColumnIndex("dbDiscountAmt")));
                orderBookingVOList.add(orderBookingVO);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return orderBookingVOList;
    }

    public List<OrderBookingVO> getAppProductsForOpeningStock(SFADatabase sfaDatabase, String cmpCode, String distrCode, String openStockNo) {
        List<OrderBookingVO> bookingVOList = new ArrayList<>();

        String query = "SELECT  p.cmpcode, \n" +
                "       p.prodcode, \n" +
                "       pbm.prodbatchcode, \n" +
                "       p.prodname, \n" +
                "       p.producthierpathname, \n" +
                "       p.prodhierlastvalcode, \n" +
                "       p.producthierpathcode, \n" +
                "       p.prodWgtType, \n" +
                "       p.prodNetWgt, \n" +
                "       p.prodGrossWgt, \n" +
                "       um.uomcode  AS baseUom, \n" +
                "       pbm.sellprice, \n" +
                "       pbm.purchaseprice, \n" +
                "       pbm.mrp, \n" +
                "       pbm.sellRateWithTax, \n" +
                "       o.adjustQty, \n" +
                "       o.uomid, \n" +
                "       o.orderQty, \n" +
                "       st.availqty AS loadstock, \n" +
                "       st.resvsaleableqty, \n" +
                "       st.saleableqty\n" +
                "FROM   m_product_master p \n" +
                "       INNER JOIN m_product_batch_master pbm \n" +
                "               ON pbm.cmpcode = p.cmpcode \n" +
                "                  AND pbm.prodcode = p.prodcode \n" +
                "       INNER JOIN m_product_uom_master um \n" +
                "               ON um.cmpcode = p.cmpcode \n" +
                "                  AND um.prodcode = p.prodcode \n" +
                "                  AND um.baseuom = 'Y' \n" +
                "                  AND um.defaultuom = 'Y' \n" +
                "       INNER JOIN m_stockonhand st \n" +
                "               ON st.cmpcode = p.cmpcode \n" +
                "                  AND st.prodcode = p.prodcode \n" +
                "                  AND st.ProdBatchCode = pbm.ProdBatchCode \n" +
                "       LEFT JOIN t_stockadjustmentdetails o \n" +
                "               ON o.prodcode = p.prodcode \n" +
                "                  AND o.cmpcode = p.cmpcode \n" +
                "                  AND o.distrcode = pbm.distrcode \n" +
                "                  AND o.prodbatchcode = pbm.prodbatchcode \n" +
                "       LEFT JOIN t_stockadjustmentheader iv \n" +
                "                  ON iv.cmpcode = p.cmpcode \n" +
                "                  AND iv.distrcode = pbm.distrcode \n" +
                "                  AND iv.stockadjustno = ?\n" +
                "WHERE  p.cmpcode = ? \n" +
                "       AND pbm.distrcode = ? ";

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(query, new String[]{openStockNo, cmpCode, distrCode});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                OrderBookingVO bookingVO = new OrderBookingVO();
                bookingVO.setCmpCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_CMP_CODE)));
                bookingVO.setDistrCode(distrCode);
                bookingVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                bookingVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE)));
                bookingVO.setProdName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NAME)));
                bookingVO.setSellPrice(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                bookingVO.setMrp(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_MRP_CAPS))));

                bookingVO.setProductHierPathName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_NAME)));
                bookingVO.setProductHierPath(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                bookingVO.setProdHierValName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                bookingVO.setBaseUOM(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
                bookingVO.setUomGroupId(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
                //bookingVO.setUomId(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
                bookingVO.setDefaultUomid(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
                bookingVO.setWeightType(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_WGT_TYPE)));
                bookingVO.setNetWeight(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NET_WGT)));
//                bookingVO.setGrossWeight(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_PROD_GROSS_WGT)));

                Integer stockQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_ALICE_LOAD_STOCK)) -
                        cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_SALEABLE_QTY));
                if (stockQty <= 0) {
                    stockQty = 0;
                }
                bookingVO.setStockInHand(stockQty);
                bookingVO.setAvailQty(stockQty);
                bookingVO.setStockCheckQty(stockQty);
                bookingVO.setConversionFactor(getConversionFactor(sfaDatabase, bookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));

//                bookingVO.setTotInvoiceQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_ADJUST_QTY)));
//                bookingVO.setTotQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_ADJUST_QTY)));
//                bookingVO.setTotOrderQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_ADJUST_QTY)));
                bookingVO.setQuantity(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_ORDER_QTY)));
                if (cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_ORDER_QTY)) > 0) {
                    bookingVO.setUomId(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_UOM_ID)));
                } else {
                    bookingVO.setUomId(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
                }
//                bookingVO.setSellRateWithTax(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_SELL_RATE_WITH_TAX))));
                bookingVOList.add(bookingVO);


            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }
        return bookingVOList;
    }

    public void deleteOpenStockList(SFADatabase sfaDatabase, String cmpCode, String distrCode, String openStockNo, String tableName) {
        sfaDatabase.openWritableDb();
        sfaDatabase.getDb().execSQL("DELETE FROM " + tableName + " WHERE cmpCode = '" + cmpCode + "' AND distrCode = '" + distrCode + "' AND stockAdjustNo = '" + openStockNo + "'");
        sfaDatabase.closeDb();
    }

    public void insertOpenStockHeaderList(SFADatabase sfaDatabase, String cmpCode, String distrCode, String openStockNo, String remarks, String tableName, String completeFlag, String goDownCode) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_GODOWN_CODE, goDownCode);
        values.put(COLUMN_STOCK_ADJUSTNO, openStockNo);
        values.put(COLUMN_TRANS_TYPE, "N");
        values.put(COLUMN_ADJUST_DT, DateUtil.getCurrentYearMonthDate());
        values.put(COLUMN_DOC_REFNO, "");
        values.put(COLUMN_REMARKS, remarks);
        values.put(COLUMN_ISMANUAL, "Y");
        values.put(COLUMN_SOURCE, "SA");
        values.put(UPLOAD_FLAG, "N");
        values.put(COLUMN_MOD_DT, DateUtil.getCurrentYearMonthDate());
        values.put(COLUMN_COMPLETE_FLAG, completeFlag);
        // Inserting Row
        sfaDatabase.getDb().insert(tableName, null, values);
    }

    public void insertOpenStockDetailList(SFADatabase sfaDatabase, String cmpCode, String distrCode, String openStockNo, OrderBookingVO qtyUpdatedOrderBooking, String tableName, String completeFlag, String goDownCode) {
        String inputStr = "";
        if (qtyUpdatedOrderBooking.getQuantity() > 0) {
            inputStr = ((int) qtyUpdatedOrderBooking.getQuantity()) + " " + qtyUpdatedOrderBooking.getUomId() + " ";
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_GODOWN_CODE, goDownCode);
        values.put(COLUMN_STOCK_ADJUSTNO, openStockNo);
        values.put(COLUMN_PROD_CODE, qtyUpdatedOrderBooking.getProdCode());
        values.put(PROD_BATCH_CODE, qtyUpdatedOrderBooking.getProdBatchCode());
        values.put(COLUMN_FROM_STOCK_TYPE, "S");
        values.put(COLUMN_TO_STOCK_TYPE, "S");
        values.put(COLUMN_ADJUST_QTY, qtyUpdatedOrderBooking.getTotQty());
        values.put(COLUMN_REASON_CODE_CAPS, "6");
        values.put(COLUMN_REASON_NAME_CAPS, "Addition of Product");
        values.put(COLUMN_INPUT_STR, inputStr);
        values.put(COLUMN_PURCHASE_RATE, String.valueOf(qtyUpdatedOrderBooking.getSellPrice()));
        values.put(COLUMN_AMOUNT, String.valueOf(qtyUpdatedOrderBooking.getOrderValue()));
        values.put(UPLOAD_FLAG, "N");
        values.put(COLUMN_MOD_DT, DateUtil.getCurrentYearMonthDate());
        values.put(COLUMN_COMPLETE_FLAG, completeFlag);
        values.put(COLUMN_UOM_ID, qtyUpdatedOrderBooking.getUomId());
        values.put(COLUMN_ORDER_QTY, qtyUpdatedOrderBooking.getQuantity());
        // Inserting Row
        sfaDatabase.getDb().insert(tableName, null, values);
    }

    public void updateOpenStockHeaderList(SFADatabase sfaDatabase, String cmpCode, String distrCode, String openStockNo, String remarks, String tableName, String completeFlag) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_REMARKS, remarks);
        values.put(COLUMN_COMPLETE_FLAG, completeFlag);

        String appendAnd = APPEND_AND;

        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_STOCK_ADJUSTNO + " = ?";

        String[] updateArgs = {cmpCode, distrCode, openStockNo};
        sfaDatabase.getDb().update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void updateOpenStockDetailList(SFADatabase sfaDatabase, String cmpCode, String distrCode, String openStockNo, OrderBookingVO qtyUpdatedOrderBooking, String tableName, String completeFlag) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLETE_FLAG, completeFlag);

        String appendAnd = APPEND_AND;

        String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_STOCK_ADJUSTNO + appendAnd + COLUMN_PROD_CODE + appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";

        String[] updateArgs = {cmpCode, distrCode, openStockNo, qtyUpdatedOrderBooking.getProdCode(),
                qtyUpdatedOrderBooking.getProdBatchCode()};
        sfaDatabase.getDb().update(tableName, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public List<OrderBookingVO> fetchOpeningStockSummary(SFADatabase sfaDatabase, String cmpCode, String distrCode, String openStockNo) {
        List<OrderBookingVO> orderBookingList = new ArrayList<>();

        String query = " SELECT p.prodcode, \n" +
                "       o.prodbatchcode, \n" +
                "       p.prodname, \n" +
                "       pb.mrp, \n" +
                "       um.uomcode  AS baseUom, \n" +
                "       o.adjustQty, \n" +
                "       o.uomid, \n" +
                "       o.orderQty, \n" +
                "       o.inputstr, \n" +
                "       o.amount, \n" +
                "       o.stockadjustno, \n" +
                "       o.reasoncode, \n" +
                "       o.reasonname, \n" +
                "       st.availqty, \n" +
                "       o.purchaserate \n" +
                "FROM   m_product_master p \n" +
                "       INNER JOIN m_product_batch_master pb \n" +
                "               ON pb.prodcode = p.prodcode \n" +
                "                  AND pb.cmpcode = p.cmpcode \n" +
                "       INNER JOIN m_product_uom_master um \n" +
                "              ON um.cmpcode = p.cmpcode \n" +
                "                 AND um.prodcode = p.prodcode \n" +
                "                 AND um.baseuom = 'Y' \n" +
                "                 AND um.defaultuom = 'Y' \n" +
                "       INNER JOIN m_stockonhand st \n" +
                "              ON st.cmpcode = p.cmpcode \n" +
                "                 AND st.prodcode = p.prodcode \n" +
                "                 AND st.prodbatchcode = pb.prodbatchcode \n" +
                "       INNER JOIN t_stockadjustmentdetails o \n" +
                "               ON o.prodcode = p.prodcode \n" +
                "                  AND o.cmpcode = p.cmpcode \n" +
                "                  AND o.distrcode = pb.distrcode \n" +
                "                  AND o.prodbatchcode = pb.prodbatchcode \n" +
                "       INNER JOIN t_stockadjustmentheader iv \n" +
                "                  ON iv.cmpcode = p.cmpcode \n" +
                "                  AND iv.distrcode = pb.distrcode \n" +
                "                  AND iv.stockadjustno = ?\n" +
                "WHERE o.cmpcode = ?\n" +
                "       AND o.distrcode = ? \n" +
                "       AND o.stockadjustno =? \n" +
                "       AND o.uploadflag = 'N' ";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{openStockNo, cmpCode, distrCode, openStockNo});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim());
                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(PROD_BATCH_CODE)).trim());
                orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_PURCHASE_RATE))));
//                orderBookingVO.setTotInvoiceQty(c.getInt(c.getColumnIndex(COLUMN_ADJUST_QTY)));
//                orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_ADJUST_QTY)));
//                orderBookingVO.setTotOrderQty(c.getInt(c.getColumnIndex(COLUMN_ADJUST_QTY)));
                orderBookingVO.setTotalAmount(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_AMOUNT))));
                orderBookingVO.setlGrossAmt(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_AMOUNT))));
                orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
                orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY)));
                orderBookingVO.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_AMOUNT))));
//                orderBookingVO.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_STOCK_ADJUSTNO)));
//                orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)));
//                orderBookingVO.setReasonCode(c.getString(c.getColumnIndex(COLUMN_REASON_CODE_CAPS)));
//                orderBookingVO.setReasonName(c.getString(c.getColumnIndex(COLUMN_REASON_NAME_CAPS)));
                orderBookingVO.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                orderBookingVO.setBaseUOM(c.getString(c.getColumnIndex(COLUMN_BASE_UOM)));
                orderBookingVO.setUomGroupId(c.getString(c.getColumnIndex(COLUMN_BASE_UOM)));
                orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_BASE_UOM)));
                orderBookingList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingList;
    }

    public List<OrderBookingVO> getStockListFromDB(SFADatabase sfaDatabase, String cmpCode, String distrCode, String openStockNo) {
        List<OrderBookingVO> bookingVOList = new ArrayList<>();

        String query = "SELECT p.cmpCode,p.prodcode, \n" +
                "       pbm.prodbatchcode, \n" +
                "       p.prodname, \n" +
                "       pbm.mrp, \n" +
                "       um.uomcode  AS baseUom, \n" +
                "       o.adjustQty, \n" +
                "       o.uomid, \n" +
                "       o.orderQty, \n" +
                "       o.inputstr, \n" +
                "       o.amount, \n" +
                "       o.stockadjustno, \n" +
                "       o.reasoncode, \n" +
                "       o.reasonname, \n" +
                "       st.availqty, \n" +
                "       o.purchaserate \n" +
                "FROM   m_product_master p \n" +
                "       INNER JOIN m_product_batch_master pbm \n" +
                "               ON pbm.cmpcode = p.cmpcode \n" +
                "                  AND pbm.prodcode = p.prodcode \n" +
                "       INNER JOIN m_product_uom_master um \n" +
                "              ON um.cmpcode = p.cmpcode \n" +
                "                 AND um.prodcode = p.prodcode \n" +
                "                 AND um.baseuom = 'Y' \n" +
                "                 AND um.defaultuom = 'Y' \n" +
                "       INNER JOIN m_stockonhand st \n" +
                "              ON st.cmpcode = p.cmpcode \n" +
                "                 AND st.prodcode = p.prodcode \n" +
                "                 AND st.prodbatchcode = pbm.prodbatchcode \n" +
                "       INNER JOIN t_stockadjustmentdetails o \n" +
                "              ON o.cmpcode = p.cmpcode \n" +
                "                 AND o.prodcode = p.prodcode \n" +
                "                 AND o.prodbatchcode = pbm.prodbatchcode \n" +
                "WHERE  o.cmpcode = ? \n" +
                "       AND o.distrcode = ? and o.stockadjustno=? and o.uploadFlag='N' COLLATE nocase";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, openStockNo});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO orderBookingVO = new OrderBookingVO();
                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)).trim());
                orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)).trim());
                orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_PURCHASE_RATE))));
//                orderBookingVO.setTotInvoiceQty(c.getInt(c.getColumnIndex(COLUMN_ADJUST_QTY)));
//                orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_ADJUST_QTY)));
//                orderBookingVO.setTotOrderQty(c.getInt(c.getColumnIndex(COLUMN_ADJUST_QTY)));
//                orderBookingVO.setTotalAmount(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_AMOUNT))));
//                orderBookingVO.setlGrossAmt(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_AMOUNT))));
//                orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
//                orderBookingVO.setTotQty(c.getInt(c.getColumnIndex(COLUMN_ADJUST_QTY)));
//                orderBookingVO.setTotOrderQty(c.getInt(c.getColumnIndex(COLUMN_ADJUST_QTY)));
//                orderBookingVO.setTotInvoiceQty(c.getInt(c.getColumnIndex(COLUMN_ADJUST_QTY)));
//                orderBookingVO.setOrderValue(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_AMOUNT))));
//                orderBookingVO.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_STOCK_ADJUSTNO)));
                orderBookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_ID)));
//                orderBookingVO.setReasonCode(c.getString(c.getColumnIndex(COLUMN_REASON_CODE_CAPS)));
//                orderBookingVO.setReasonName(c.getString(c.getColumnIndex(COLUMN_REASON_NAME_CAPS)));
                orderBookingVO.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                orderBookingVO.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY)));
                orderBookingVO.setBaseUOM(c.getString(c.getColumnIndex(COLUMN_BASE_UOM)));
                orderBookingVO.setUomGroupId(c.getString(c.getColumnIndex(COLUMN_BASE_UOM)));
                orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_BASE_UOM)));
                bookingVOList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return bookingVOList;
    }

    public void deleteOpenStockById(SFADatabase sfaDatabase, String cmpCode, String distrCode, String openStockNo, OrderBookingVO orderBookingVO, String tableName) {
        sfaDatabase.openWritableDb();
        sfaDatabase.getDb().execSQL("DELETE FROM " + tableName + " WHERE cmpCode = '" + cmpCode + "' AND distrCode = '" + distrCode + "' AND stockAdjustNo = '" + openStockNo + "' AND prodCode = '" + orderBookingVO.getProdCode() + "' AND prodBatchCode='" + orderBookingVO.getProdBatchCode() + "'");
        sfaDatabase.closeDb();
    }

    public void updateStockonHandOpeningStock(SFADatabase sfaDatabase, String cmpCode, String distrCode, OrderBookingVO orderBookingVO) {
        int availQty = orderBookingVO.getAvailQty() + orderBookingVO.getTotQty();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AVAIL_QTY, availQty);
        values.put(COLUMN_UPLOAD, "N");

        String appendAnd = APPEND_AND;

        String update = COLUMN_DISTR_CODE + appendAnd + COLUMN_PROD_CODE + appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";

        String[] updateArgs = {distrCode, orderBookingVO.getProdCode(),
                orderBookingVO.getProdBatchCode()};
        sfaDatabase.getDb().update(TABLE_STOCK_ON_HAND, values, update, updateArgs);
        sfaDatabase.closeDb();
    }

    public void deletePendingOpenStockList(SFADatabase sfaDatabase, String cmpCode, String distrCode, String tableName) {
        sfaDatabase.openWritableDb();
        sfaDatabase.getDb().execSQL("DELETE FROM " + tableName + " WHERE cmpCode = '" + cmpCode + "' AND distrCode = '" + distrCode + "' AND uploadFlag = 'N' AND completeFlag = 'N'");
        sfaDatabase.closeDb();
    }

    public String getOpenStockNo(SFADatabase sfaDatabase, String cmpCode, String distrCode, String transType, String uploadFlag) {
        String openStockNo = "";
        String query = "select * from t_stockadjustmentheader where cmpCode = ? and distrCode = ? and transType = ? and uploadFlag = ?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, transType, uploadFlag});
        if (isCursorTrue(c)) {
            do {
                openStockNo = c.getString(c.getColumnIndex(COLUMN_STOCK_ADJUSTNO));
            } while (c.moveToNext());

        }
        closeCursor(c);

        return openStockNo;
    }

    public List<OrderBookingVO> getStockReportLineItems(SFADatabase sfaDatabase, String cmpCode, String distrCode, String prodCode) {

        String QUERY_SELECT_STOCK_REPORT_DATA = "SELECT prod.prodName,\n" +
                "       prod.prodCode,\n" +
                "       prod.prodHierLastValCode,\n" +
                "       prod.productHierPathCode,\n" +
                "       prod.lobDivisionCode,\n" +
                "       batch.prodBatchCode,\n" +
                "       batch.mrp,\n" +
                "       batch.sellprice,\n" +
                "       batch.purchasePrice,\n" +
                "       stock.availQty,\n" +
                "       stock.unSaleableQty,\n" +
                "       stock.offerQty,\n" +
                "       stock.resvSaleableQty,\n" +
                "       stock.resvUnSaleableQty,\n" +
                "       stock.resvOfferQty\n" +
                "FROM m_product_master prod\n" +
                "INNER JOIN m_product_batch_master batch ON batch.prodCode=prod.prodCode\n" +
                "INNER JOIN m_stockOnHand stock ON stock.prodCode=prod.prodCode\n" +
                "AND stock.prodBatchCode=batch.prodBatchCode\n" +
                "WHERE prod.cmpCode=?\n" +
                "  AND stock.distrCode=?\n" +
                "  AND prod.prodCode=?";

        List<OrderBookingVO> stockList = new ArrayList<>();

        Cursor c = null;
        try {
            c = sfaDatabase.getDb().rawQuery(QUERY_SELECT_STOCK_REPORT_DATA, new String[]{cmpCode, distrCode, prodCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                    orderBookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    orderBookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                    orderBookingVO.setProductHierPath(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
//                    orderBookingVO.setLobDivisionCode(c.getString(c.getColumnIndex(COLUMN_PROD_LOB_DIVISTION_CODE)));
//                    orderBookingVO.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
//                    orderBookingVO.setProdHierLastValCode(c.getString(c.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
//                    orderBookingVO.setSellPrice(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
//                    orderBookingVO.setMrp(new BigDecimal(c.getString(c.getColumnIndex(COLUMN_MRP_CAPS))));
//                    orderBookingVO.setUnSalQty(c.getInt(c.getColumnIndex(COLUMN_UNSALEABLE_QTY)));
//                    orderBookingVO.setOfferStock(c.getInt(c.getColumnIndex(COLUMN_OFFER_QTY)));
                    orderBookingVO.setPurchasePrice(c.getDouble(c.getColumnIndex(COLUMN_PURCHASE_PRICE)));
                    int saleableQty = c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)) - c.getInt(c.getColumnIndex(COLUMN_RESV_SALEABLE_QTY));
                    int unsaleableQty = c.getInt(c.getColumnIndex(COLUMN_UNSALEABLE_QTY)) - c.getInt(c.getColumnIndex(COLUMN_RESV_UNSALEABLE_QTY));
                    int offerQty = c.getInt(c.getColumnIndex(COLUMN_OFFER_QTY)) - c.getInt(c.getColumnIndex(COLUMN_RESV_OFFER_QTY));
                    orderBookingVO.setConversionFactor(getConversionFactor(sfaDatabase, orderBookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));

                    if (saleableQty <= 0) {
                        saleableQty = 0;
                    }
                    if (unsaleableQty <= 0) {
                        unsaleableQty = 0;
                    }
                    if (offerQty <= 0) {
                        offerQty = 0;
                    }
                    if (saleableQty > 0 || unsaleableQty > 0 || offerQty > 0) {
                        stockList.add(orderBookingVO);
                    }

                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
            sfaDatabase.closeDb();
        }

        return stockList;
    }

    public boolean isTaxAvailable(SFADatabase sfaDatabase, String prodCode) {
        sfaDatabase.openWritableDb();
        String query = "SELECT * FROM " + TABLE_PRODUCT_TAX_MASTER + " WHERE prodCode = '" + prodCode + "'";
        Cursor cm = sfaDatabase.getDb().rawQuery(query, null);
        if (cm != null && cm.getCount() > 0) {
            return true;
        }
        if (cm != null) {
            cm.close();
        }
        sfaDatabase.closeDb();

        return false;
    }

    public OrderBookingVO getTaxForProducts(BaseDB baseDB, String prodCode) {
        baseDB.openWritableDb();
        OrderBookingVO orderBookingVO = new OrderBookingVO();
        String query = "SELECT *\n" +
                "FROM m_product_tax_master\n" +
                "WHERE prodCode= '" + prodCode + "'";

        try {
            Cursor cm = baseDB.getDb().rawQuery(query, null);

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
                orderBookingVO.setCmpCode(cm.getString(cm.getColumnIndex(COLUMN_CMP_CODE)));
                orderBookingVO.setProdCode(cm.getString(cm.getColumnIndex(COLUMN_PROD_CODE)));
                orderBookingVO.setCgstperc(cm.getDouble(cm.getColumnIndex(COLUMN_CGST)));
                orderBookingVO.setSgstPerc(cm.getDouble(cm.getColumnIndex(COLUMN_SGST)));
                orderBookingVO.setIgstPerc(cm.getDouble(cm.getColumnIndex(COLUMN_IGST)));
//                orderBookingVO.setCessPerc(cm.getDouble(cm.getColumnIndex(COLUMN_CESS)));
            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getTaxForProducts: " + e.getMessage(), e);
        }
        return orderBookingVO;
    }

    public List<SalesReturnVO> getSalesReturnDetailFromDB(SFADatabase sfaDatabase, String distrCode, String salesreturnNo) {
        List<SalesReturnVO> salesReturnVOList = new ArrayList<>();
        String query = "SELECT srd.distrCode,\n" +
                "       srd.salesReturnNo,\n" +
                "       srd.invoiceNo,\n" +
                "       srd.ProdBatchCode,\n" +
                "       srd.prodCode,\n" +
                "       srd.prodName,\n" +
                "       srd.batchDetail,\n" +
                "       srd.returnQty,\n" +
                "       srd.stockType,\n" +
                "       srd.ReasonCode,\n" +
                "       srd.ReasonName,\n" +
                "       srd.mrp,\n" +
                "       srd.sellRate,\n" +
                "       srd.returnAmt,\n" +
                "       srd.image,\n" +
                "       p.ProdShortName\n" +
                "FROM t_salesreturndetails srd\n" +
                "INNER JOIN m_product_master p ON srd.prodCode = p.prodCode where distrCode=? and salesReturnNo=?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, salesreturnNo});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                SalesReturnVO salesReturnVO = new SalesReturnVO();

                salesReturnVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                salesReturnVO.setSalesreturnNo(c.getString(c.getColumnIndex(COLUMN_SALES_RETURNNO)));
                salesReturnVO.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NO)));
                salesReturnVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                salesReturnVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                salesReturnVO.setProdShortName(c.getString(c.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
                salesReturnVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                salesReturnVO.setBatchDetail(c.getDouble(c.getColumnIndex(COLUMN_BATCH_DETAIL)));
                salesReturnVO.setReturnQty(c.getInt(c.getColumnIndex(COLUMN_RETURN_QTY)));
                salesReturnVO.setStockType(c.getString(c.getColumnIndex(COLUMN_STOCK_TYPE)));
                salesReturnVO.setReasonCode(c.getString(c.getColumnIndex(COLUMN_REASON_CODE_CAPS)));
                salesReturnVO.setReasonName(c.getString(c.getColumnIndex(COLUMN_REASON_NAME_CAPS)));
                salesReturnVO.setMrp(c.getDouble(c.getColumnIndex(COLUMN_MRP)));
                salesReturnVO.setSellPrice(c.getDouble(c.getColumnIndex(COLUMN_SELL_RATE)));
                salesReturnVO.setReturnAmt(c.getDouble(c.getColumnIndex(COLUMN_RETURN_AMOUNT)));
                salesReturnVO.setReturnImage(c.getString(c.getColumnIndex(COLUMN_IMAGE)));

                salesReturnVOList.add(salesReturnVO);
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return salesReturnVOList;
    }

    public List<SalesReturnVO> getSalesReturns(SFADatabase sfaDatabase, String distrcode,
                                               String salesmancode, String routecode, String retrCode) {


        List<SalesReturnVO> salesReturnList = new ArrayList<>();

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(QUERY_SELECT_SALES_RETURNS, new String[]{distrcode, salesmancode, routecode, retrCode});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                SalesReturnVO salesReturnVO = new SalesReturnVO();
                salesReturnVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                salesReturnVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE)));
                salesReturnVO.setHsnCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HSN_CODE)));
                salesReturnVO.setProdName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NAME)));
                salesReturnVO.setProdShortName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_SHORT_NAME_CAPS)));
                salesReturnVO.setReasonCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_REASON_CODE_CAPS)));
                salesReturnVO.setReasonName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_REASON_NAME_CAPS)));
                salesReturnVO.setRetlrCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                salesReturnVO.setReturnQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RETURN_QTY)));
                salesReturnVO.setReturnAmt(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_RETURN_AMOUNT)));
                salesReturnVO.setReturnImage(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_IMAGE)));
                salesReturnVO.setSellPrice(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS)));
                salesReturnVO.setMrp(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_MRP)));
                salesReturnVO.setBatchDetail(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_BATCH_DETAIL)));
                salesReturnVO.setStockType(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_STOCK_TYPE)));
                salesReturnVO.setSalesreturnNo(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_SALES_RETURNNO)));
                salesReturnVO.setRemarks(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_REMARKS)));
                salesReturnList.add(salesReturnVO);

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }
        return salesReturnList;


    }

    public List<OrderBookingVO> getAllProductsForBatchTransfer(SFADatabase sfaDatabase, String cmpCode, String distrCode, String stockType) {
        List<OrderBookingVO> bookingVOList = new ArrayList<>();

        String columnName = COLUMN_AVAIL_QTY;
        if (stockType.equalsIgnoreCase("S")) {
            columnName = COLUMN_AVAIL_QTY;
        } else if (stockType.equalsIgnoreCase("U")) {
            columnName = COLUMN_UNSALEABLE_QTY;
        } else if (stockType.equalsIgnoreCase("O")) {
            columnName = COLUMN_OFFER_QTY;
        }

        String query = "SELECT  p.cmpcode, \n" +
                "       p.prodcode, \n" +
                "       pbm.prodbatchcode, \n" +
                "       p.prodname, \n" +
                "       p.producthierpathname, \n" +
                "       p.prodhierlastvalcode, \n" +
                "       p.producthierpathcode, \n" +
                "       p.prodWgtType, \n" +
                "       p.prodNetWgt, \n" +
                "       p.prodGrossWgt, \n" +
                "       um.uomcode  AS baseUom, \n" +
                "       pbm.sellprice, \n" +
                "       pbm.purchaseprice, \n" +
                "       pbm.mrp, \n" +
                "       pbm.sellRateWithTax, \n" +
                "       st.availqty, \n" +
                "       st.resvSaleableqty, \n" +
                "       st.resvUnSaleableqty, \n" +
                "       st.resvOfferqty, \n" +
                "       st.saleableqty, \n" +
                "       st.unSaleableQty, \n" +
                "       st.offerQty\n" +
                "FROM   m_product_master p \n" +
                "       INNER JOIN m_product_batch_master pbm \n" +
                "               ON pbm.cmpcode = p.cmpcode \n" +
                "                  AND pbm.prodcode = p.prodcode \n" +
                "       INNER JOIN m_product_uom_master um \n" +
                "               ON um.cmpcode = p.cmpcode \n" +
                "                  AND um.prodcode = p.prodcode \n" +
                "                  AND um.baseuom = 'Y' \n" +
                "                  AND um.defaultuom = 'Y' \n" +
                "       INNER JOIN m_stockonhand st \n" +
                "               ON st.cmpcode = p.cmpcode \n" +
                "                  AND st.prodcode = p.prodcode \n" +
                "                  AND st.ProdBatchCode = pbm.ProdBatchCode \n" +
//                "                  AND st." + columnName + " > 0 \n" +
                "WHERE  p.cmpcode = ? \n" +
                "       AND pbm.distrcode = ? group by p.prodCode";

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                OrderBookingVO bookingVO = new OrderBookingVO();
                bookingVO.setCmpCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_CMP_CODE)));
                bookingVO.setDistrCode(distrCode);
                bookingVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                bookingVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE)));
                bookingVO.setProdName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NAME)));
                bookingVO.setSellPrice(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                bookingVO.setMrp(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_MRP_CAPS))));

                bookingVO.setProductHierPathName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_NAME)));
                bookingVO.setProductHierPath(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                bookingVO.setProdHierValName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                bookingVO.setBaseUOM(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
                bookingVO.setWeightType(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_WGT_TYPE)));
                bookingVO.setNetWeight(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NET_WGT)));
//                bookingVO.setGrossWeight(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_PROD_GROSS_WGT)));
//                bookingVO.setConversionFactor(getConversionFactor(sfaDatabase, bookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));
//                bookingVO.setSellRateWithTax(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_SELL_RATE_WITH_TAX))));

                int chkQty = 0;
                if (columnName.equalsIgnoreCase(COLUMN_AVAIL_QTY)) {
                    chkQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(columnName)) -
                            cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_SALEABLE_QTY));
                } else if (columnName.equalsIgnoreCase(COLUMN_UNSALEABLE_QTY)) {
                    chkQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(columnName)) -
                            cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_UNSALEABLE_QTY));
                } else if (columnName.equalsIgnoreCase(COLUMN_OFFER_QTY)) {
                    chkQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(columnName)) -
                            cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_OFFER_QTY));
                }

                if (chkQty <= 0) {
                    chkQty = 0;
                }
                bookingVO.setStockInHand(chkQty);
                bookingVO.setAvailQty(chkQty);

                bookingVOList.add(bookingVO);

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }

        return bookingVOList;
    }

    public String getBatchTransferNo(SFADatabase sfaDatabase, String cmpCode, String distrCode, String goDownCode, String completedFlag, String uploadFlag) {
        sfaDatabase.openWritableDb();

        String query = "SELECT " + COLUMN_BATCH_TRANSFERNO + " FROM " + TABLE_BATCH_TRANSFER_DETAILS + " WHERE cmpCode=? and distrCode =? and godownCode=? and completeFlag=? and uploadFlag=?";
        Cursor cm = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, goDownCode, completedFlag, uploadFlag});
        if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) {
            String btrNo = cm.getString(cm.getColumnIndex(COLUMN_BATCH_TRANSFERNO));
            cm.close();
            return btrNo;
        }
        if (cm != null) {
            cm.close();
        }
        sfaDatabase.closeDb();
        return "";
    }

    public void deleteBatchTransfer(SFADatabase sfaDatabase, String cmpCode, String distrCode, String goDownCode, String batchTransferNo, String completedFlag, String uploadFlag) {
        String deleteColumns = "cmpCode=? and distrCode =? and godownCode=? and batchTransferNo=? and completeFlag=? and uploadFlag=?";
        String[] deleteArgs = {cmpCode, distrCode, goDownCode, batchTransferNo, completedFlag, uploadFlag};
        sfaDatabase.getDb().delete(IDBColumns.TABLE_BATCH_TRANSFER_DETAILS, deleteColumns, deleteArgs);
    }

    public void insertBatchTransferDetails(SFADatabase sfaDatabase, String distrCode, String cmpCode, String goDownCode, String batchTransferNo, OrderBookingVO orderBookingVO) {

        String inputStr = "";
        inputStr = ((int) orderBookingVO.getQuantity()) + " " + orderBookingVO.getUomId() + " ";

        ContentValues values = new ContentValues();
        values.put(COLUMN_CMP_CODE, cmpCode);
        values.put(COLUMN_DISTR_CODE, distrCode);
        values.put(COLUMN_GODOWN_CODE, goDownCode);
        values.put(COLUMN_BATCH_TRANSFERNO, batchTransferNo);
        values.put(COLUMN_TRANSFER_DT, DateUtil.getCurrentYearMonthDate());
//        values.put(COLUMN_STOCK_TYPE, orderBookingVO.getStockType());
//        values.put(COLUMN_PROD_CODE, orderBookingVO.getProdCode());
//        values.put(FROM_PROD_BATCH_CODE, orderBookingVO.getFromProdBatchCode());
//        values.put(TO_PROD_BATCH_CODE, orderBookingVO.getToProdBatchCode());
//        values.put(COLUMN_TRANSFER_QTY, orderBookingVO.getTotInvoiceQty());
//        values.put(COLUMN_QTY, orderBookingVO.getQuantity());
//        values.put(COLUMN_UOM_ID, orderBookingVO.getUomId());
//        values.put(COLUMN_INPUT_STR, inputStr);
//        values.put(COLUMN_REASON_CODE_CAPS, orderBookingVO.getReasonCode());
//        values.put(COLUMN_REASON_NAME_CAPS, orderBookingVO.getReasonName());
        values.put(COLUMN_DOC_REFNO, "");
        values.put(COLUMN_REMARKS, "");
        values.put(COLUMN_COMPLETE_FLAG, "N");
        values.put(COLUMN_MOD_DT, DateUtil.getCurrentYearMonthDate());
        values.put(UPLOAD_FLAG, "N");
        // Inserting Row
        sfaDatabase.getDb().insert(IDBColumns.TABLE_BATCH_TRANSFER_DETAILS, null, values);
    }

    public List<OrderBookingVO> getBTRProductsFromDB(SFADatabase sfaDatabase, String cmpCode, String distrCode, String batchTransferNo, String completedFlag, String uploadFlag) {
        List<OrderBookingVO> bookingVOList = new ArrayList<>();

        String QUERY_SELECT_BTR_PRODUCT = "SELECT p.cmpCode,p.prodcode, \n" +
                "       pbm.prodbatchcode, \n" +
                "       p.prodname, \n" +
                "       p.producthierpathname,\n" +
                "       p.prodhierlastvalcode,\n" +
                "       p.productHierPathCode,\n" +
                "       ord.uomId AS baseUom,\n" +
                "       ord.uomId AS uomCode1,\n" +
                "       pbm.sellprice, \n" +
                "       pbm.purchaseprice, \n" +
                "       pbm.mrp, \n" +
                "       ord.transferQty, \n" +
                "       ord.qty, \n" +
                "       ord.uomId, \n" +
                "       ord.frombatchCode, \n" +
                "       ord.tobatchCode, \n" +
                "       ord.stockType, \n" +
                "       ord.reasonCode, \n" +
                "       ord.reasonName, \n" +
                "       st.availQty,\n" +
                "       st.resvSaleableQty,\n" +
                "       st.resvUnSaleableQty,\n" +
                "       st.resvOfferQty,\n" +
                "       st.saleableqty, \n" +
                "       st.unSaleableQty, \n" +
                "       st.offerQty\n" +
                "FROM   m_product_master p \n" +
                "       INNER JOIN m_product_batch_master pbm \n" +
                "               ON pbm.cmpcode = p.cmpcode \n" +
                "                  AND pbm.prodcode = p.prodcode \n" +
                "       INNER JOIN m_product_uom_master um \n" +
                "              ON um.cmpcode = p.cmpcode \n" +
                "                 AND um.prodcode = p.prodcode \n" +
                "                 AND um.baseuom = 'Y' \n" +
                "                 AND um.defaultuom = 'Y' \n" +
                "       INNER JOIN m_stockonhand st \n" +
                "              ON st.cmpcode = p.cmpcode \n" +
                "                 AND st.prodcode = p.prodcode \n" +
                "                 AND st.prodbatchcode = pbm.prodbatchcode \n" +
                "       INNER JOIN t_batchtransferdetails ord \n" +
                "              ON ord.cmpcode = p.cmpcode \n" +
                "                 AND ord.prodcode = p.prodcode \n" +
                "                 AND ord.frombatchcode = pbm.prodbatchcode \n" +
                "                 AND ord.distrCode = st.distrCode \n" +
                "WHERE  p.cmpcode = ? AND pbm.distrcode = ?  AND  ord.batchTransferNo = ? AND ord.completeFlag = ? AND ord.uploadFlag = ? COLLATE nocase";

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(QUERY_SELECT_BTR_PRODUCT, new String[]{cmpCode, distrCode, batchTransferNo, completedFlag, uploadFlag});


        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                OrderBookingVO bookingVO = new OrderBookingVO();
                bookingVO.setCmpCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_CMP_CODE)));
                bookingVO.setDistrCode(distrCode);
                bookingVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE)));
                bookingVO.setProdName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NAME)));
//                bookingVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(FROM_PROD_BATCH_CODE)));
//                bookingVO.setFromProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(FROM_PROD_BATCH_CODE)));
//                bookingVO.setToProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(TO_PROD_BATCH_CODE)));
                bookingVO.setSellPrice(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                bookingVO.setMrp(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_MRP_CAPS))));
                bookingVO.setProductHierPathName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_NAME)));
                bookingVO.setProductHierPath(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                bookingVO.setProdHierValName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                bookingVO.setBaseUOM(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
                bookingVO.setConversionFactor(getConversionFactor(sfaDatabase, bookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));
                bookingVO.setQuantity(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_QTY)));
                bookingVO.setTotQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_TRANSFER_QTY)));
//                bookingVO.setTotInvoiceQty(cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_TRANSFER_QTY)));
//                bookingVO.setUomId(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
//                bookingVO.setStockType(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_STOCK_TYPE)));
//                bookingVO.setReasonCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_REASON_CODE_CAPS)));
//                bookingVO.setReasonName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_REASON_NAME_CAPS)));


                String stockType = cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_STOCK_TYPE));
                String columnName = COLUMN_AVAIL_QTY;
                if (stockType.equalsIgnoreCase("S")) {
                    columnName = COLUMN_AVAIL_QTY;
                } else if (stockType.equalsIgnoreCase("U")) {
                    columnName = COLUMN_UNSALEABLE_QTY;
                } else if (stockType.equalsIgnoreCase("O")) {
                    columnName = COLUMN_OFFER_QTY;
                }

                int chkQty = 0;
                if (columnName.equalsIgnoreCase(COLUMN_AVAIL_QTY)) {
                    chkQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(columnName)) -
                            cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_SALEABLE_QTY));
                } else if (columnName.equalsIgnoreCase(COLUMN_UNSALEABLE_QTY)) {
                    chkQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(columnName)) -
                            cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_UNSALEABLE_QTY));
                } else if (columnName.equalsIgnoreCase(COLUMN_OFFER_QTY)) {
                    chkQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(columnName)) -
                            cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_OFFER_QTY));
                }

                if (chkQty <= 0) {
                    chkQty = 0;
                }
                bookingVO.setStockInHand(chkQty);
                bookingVO.setAvailQty(chkQty);

                String prodCode = cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE));
                String toBatchCode = cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(TO_PROD_BATCH_CODE));

                OrderBookingVO orderBookingVO = getToBatchCodeData(sfaDatabase, cmpCode, distrCode, stockType, prodCode, toBatchCode);

//                bookingVO.setToMrp(orderBookingVO.getToMrp());
//                bookingVO.setToSellPrice(orderBookingVO.getToSellPrice());
//                bookingVO.setToStockInHand(orderBookingVO.getToStockInHand());

                bookingVOList.add(bookingVO);

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }

        return bookingVOList;
    }

    private OrderBookingVO getToBatchCodeData(SFADatabase sfaDatabase, String cmpCode, String distrCode, String stockType, String prodCode, String toBatchCode) {
        OrderBookingVO orderBookingVO = new OrderBookingVO();

        String QUERY_SELECT_BTR_PRODUCT = "SELECT \n" +
                "       pbm.sellprice, \n" +
                "       pbm.mrp, \n" +
                "       stk.availQty,\n" +
                "       stk.resvSaleableQty,\n" +
                "       stk.resvUnSaleableQty,\n" +
                "       stk.resvOfferQty,\n" +
                "       stk.unSaleableQty, \n" +
                "       stk.offerQty\n" +
                "FROM  m_stockonhand stk \n" +
                "       INNER JOIN m_product_batch_master pbm \n" +
                "               ON pbm.prodCode = ? \n" +
                "                  AND pbm.prodBatchCode = ? \n" +
                "WHERE  stk.prodCode = ? and stk.prodBatchCode= ? AND pbm.cmpcode = ? AND pbm.distrcode = ?";

        try {

            Cursor cm = sfaDatabase.getDb().rawQuery(QUERY_SELECT_BTR_PRODUCT, new String[]{prodCode, toBatchCode, prodCode, toBatchCode, cmpCode, distrCode});

            if (cm != null && cm.getCount() > 0 && cm.moveToFirst()) do {
//                orderBookingVO.setToSellPrice(new BigDecimal(cm.getString(cm.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
//                orderBookingVO.setToMrp(new BigDecimal(cm.getString(cm.getColumnIndex(COLUMN_MRP_CAPS))));

                String columnName = COLUMN_AVAIL_QTY;
                int chkQty = 0;
                if (stockType.equalsIgnoreCase("S")) {
                    columnName = COLUMN_AVAIL_QTY;
                    chkQty = cm.getInt(cm.getColumnIndex(columnName)) -
                            cm.getInt(cm.getColumnIndex(COLUMN_RESV_SALEABLE_QTY));
                } else if (stockType.equalsIgnoreCase("U")) {
                    columnName = COLUMN_UNSALEABLE_QTY;
                    chkQty = cm.getInt(cm.getColumnIndex(columnName)) -
                            cm.getInt(cm.getColumnIndex(COLUMN_RESV_UNSALEABLE_QTY));
                } else if (stockType.equalsIgnoreCase("O")) {
                    columnName = COLUMN_OFFER_QTY;
                    chkQty = cm.getInt(cm.getColumnIndex(columnName)) -
                            cm.getInt(cm.getColumnIndex(COLUMN_RESV_OFFER_QTY));
                }

                if (chkQty <= 0) {
                    chkQty = 0;
                }
//                orderBookingVO.setToStockInHand(chkQty);

            } while (cm.moveToNext());
            if (cm != null) {
                cm.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getToBatchCodeData: " + e.getMessage(), e);
        }
        return orderBookingVO;

    }

    public void deleteBatchTransferById(SFADatabase sfaDatabase, String cmpCode, String distrCode, String goDownCode, String btrNo, OrderBookingVO orderBookingVO) {

        String deleteColumns = "cmpCode=? and distrCode =? and godownCode=? and batchTransferNo=? and stockType = ? and prodCode = ? and fromBatchCode = ? and toBatchCode = ? and completeFlag=? and uploadFlag=?";
//        String[] deleteArgs = {cmpCode, distrCode, goDownCode, btrNo, orderBookingVO.getStockType(), orderBookingVO.getProdCode(), orderBookingVO.getFromProdBatchCode(), orderBookingVO.getToProdBatchCode(), "N", "N"};
//        sfaDatabase.getDb().delete(IDBColumns.TABLE_BATCH_TRANSFER_DETAILS, deleteColumns, deleteArgs);
    }

    public void updateStockOnHandBTR(SFADatabase sfaDatabase, String cmpCode, String distrCode, String goDownCode, String btrNo, List<OrderBookingVO> batchTransferList) {

        for (OrderBookingVO bookingVO : batchTransferList) {

            String stockValue = "";
//            if (bookingVO.getStockType().equalsIgnoreCase("S")) {
//                stockValue = COLUMN_AVAIL_QTY;
//            } else if (bookingVO.getStockType().equalsIgnoreCase("U")) {
//                stockValue = COLUMN_UNSALEABLE_QTY;
//            } else if (bookingVO.getStockType().equalsIgnoreCase("O")) {
//                stockValue = COLUMN_OFFER_QTY;
//            }
//
//            String query = "Select " + stockValue + " from " + TABLE_STOCK_ON_HAND + " where cmpCode=? and distrCode=? and godownCode=? and prodCode=? and ProdBatchCode=?";
//            Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{
//                    cmpCode, distrCode, goDownCode,
//                    bookingVO.getProdCode(), bookingVO.getFromProdBatchCode()});
//
//            int value = 1;
//            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
//                value = cursor.getInt(cursor.getColumnIndex(stockValue));
//            }

//            if (cursor != null) {
//                cursor.close();
//            }

            String toBatchQuery = "Select " + stockValue + " from " + TABLE_STOCK_ON_HAND + " where cmpCode=? and distrCode=? and godownCode=? and prodCode=? and ProdBatchCode=?";
//            Cursor cur = sfaDatabase.getDb().rawQuery(toBatchQuery, new String[]{
//                    cmpCode, distrCode, goDownCode,
//                    bookingVO.getProdCode(), bookingVO.getToProdBatchCode()});
//
//            int toValue = 1;
//            if (cur != null && cur.getCount() > 0 && cur.moveToFirst()) {
//                toValue = cur.getInt(cur.getColumnIndex(stockValue));
//            }
//
//            if (cur != null) {
//                cur.close();
//            }

            int fromStockOnHandQty = 0;
            int toStockOnHandQty = 0;
//            fromStockOnHandQty = value - bookingVO.getTotInvoiceQty();
//            toStockOnHandQty = toValue + bookingVO.getTotInvoiceQty();

            ContentValues values = new ContentValues();

            values.put(stockValue, fromStockOnHandQty);
            values.put(COLUMN_UPLOAD, "N");

            String appendAnd = APPEND_AND;
            String update = COLUMN_CMP_CODE + appendAnd + COLUMN_DISTR_CODE + appendAnd + COLUMN_GODOWN_CODE + appendAnd + COLUMN_PROD_CODE +
                    appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";
//            String[] updateArgs = new String[]{
//                    cmpCode, distrCode, goDownCode,
//                    bookingVO.getProdCode(), bookingVO.getFromProdBatchCode()};
//            sfaDatabase.getDb().update(TABLE_STOCK_ON_HAND, values, update, updateArgs);
//
//            ContentValues toValues = new ContentValues();
//
//            toValues.put(stockValue, toStockOnHandQty);
//            toValues.put(COLUMN_UPLOAD, "N");
//
//            String toAppendAnd = APPEND_AND;
//            String toUpdate = COLUMN_CMP_CODE + toAppendAnd + COLUMN_DISTR_CODE + toAppendAnd + COLUMN_GODOWN_CODE + toAppendAnd + COLUMN_PROD_CODE +
//                    toAppendAnd + COLUMN_PROD_BATCH_CODE + " = ?";
//            String[] toUpdateArgs = new String[]{
//                    cmpCode, distrCode, goDownCode,
//                    bookingVO.getProdCode(), bookingVO.getToProdBatchCode()};
//            sfaDatabase.getDb().update(TABLE_STOCK_ON_HAND, toValues, toUpdate, toUpdateArgs);
            sfaDatabase.closeDb();

        }

    }

    public List<OrderBookingVO> getBatchTransferData(SFADatabase sfaDatabase, String cmpCode, String distCode) {
        List<OrderBookingVO> orderBookingVOList = new ArrayList<>();

        String query = "Select * from " + TABLE_BATCH_TRANSFER_DETAILS + " where cmpCode=? and distrCode=? and completeFlag='Y' and uploadFlag='N'";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                OrderBookingVO orderBookingVO = new OrderBookingVO();

                orderBookingVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                orderBookingVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)).trim());
//                orderBookingVO.setGoDownCode(c.getString(c.getColumnIndex(COLUMN_GODOWN_CODE)));
//                orderBookingVO.setBatchTransferNo(c.getString(c.getColumnIndex(COLUMN_BATCH_TRANSFERNO)));
//                orderBookingVO.setOrderDate(c.getString(c.getColumnIndex(COLUMN_TRANSFER_DT)));
//                orderBookingVO.setStockType(c.getString(c.getColumnIndex(COLUMN_STOCK_TYPE)));
//                orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
//                orderBookingVO.setFromProdBatchCode(c.getString(c.getColumnIndex(FROM_PROD_BATCH_CODE)));
//                orderBookingVO.setToProdBatchCode(c.getString(c.getColumnIndex(TO_PROD_BATCH_CODE)));
//                orderBookingVO.setTotInvoiceQty(c.getInt(c.getColumnIndex(COLUMN_TRANSFER_QTY)));
//                orderBookingVO.setInputStr(c.getString(c.getColumnIndex(COLUMN_INPUT_STR)));
//                orderBookingVO.setReasonCode(c.getString(c.getColumnIndex(COLUMN_REASON_CODE_CAPS)));
//                orderBookingVO.setReasonName(c.getString(c.getColumnIndex(COLUMN_REASON_NAME_CAPS)));
//                orderBookingVO.setDocRefNo(c.getString(c.getColumnIndex(COLUMN_DOC_REFNO)));
                orderBookingVO.setRemarks(c.getString(c.getColumnIndex(COLUMN_REMARKS)));
                orderBookingVOList.add(orderBookingVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return orderBookingVOList;
    }

    public List<OrderBookingVO> getProductBatchCodeForBTR(SFADatabase sfaDatabase, String cmpCode, String prodCode, String stockType) {
        List<OrderBookingVO> bookingVOList = new ArrayList<>();

        String columnName = COLUMN_AVAIL_QTY;
        if (stockType.equalsIgnoreCase("S")) {
            columnName = COLUMN_AVAIL_QTY;
        } else if (stockType.equalsIgnoreCase("U")) {
            columnName = COLUMN_UNSALEABLE_QTY;
        } else if (stockType.equalsIgnoreCase("O")) {
            columnName = COLUMN_OFFER_QTY;
        }

        String query = "SELECT  p.cmpcode, \n" +
                "       p.prodcode, \n" +
                "       pbm.prodbatchcode, \n" +
                "       p.prodname, \n" +
                "       p.producthierpathname, \n" +
                "       p.prodhierlastvalcode, \n" +
                "       p.producthierpathcode, \n" +
                "       p.prodWgtType, \n" +
                "       p.prodNetWgt, \n" +
                "       p.prodGrossWgt, \n" +
                "       um.uomcode  AS baseUom, \n" +
                "       pbm.sellprice, \n" +
                "       pbm.purchaseprice, \n" +
                "       pbm.mrp, \n" +
                "       pbm.sellRateWithTax, \n" +
                "       st.availqty, \n" +
                "       st.resvSaleableqty, \n" +
                "       st.resvUnSaleableqty, \n" +
                "       st.resvOfferqty, \n" +
                "       st.saleableqty, \n" +
                "       st.unSaleableQty, \n" +
                "       st.offerQty\n" +
                "FROM   m_product_master p \n" +
                "       INNER JOIN m_product_batch_master pbm \n" +
                "               ON pbm.cmpcode = p.cmpcode \n" +
                "                  AND pbm.prodcode = p.prodcode \n" +
                "       INNER JOIN m_product_uom_master um \n" +
                "               ON um.cmpcode = p.cmpcode \n" +
                "                  AND um.prodcode = p.prodcode \n" +
                "                  AND um.baseuom = 'Y' \n" +
                "                  AND um.defaultuom = 'Y' \n" +
                "       INNER JOIN m_stockonhand st \n" +
                "               ON st.cmpcode = p.cmpcode \n" +
                "                  AND st.prodcode = p.prodcode \n" +
                "                  AND st.ProdBatchCode = pbm.ProdBatchCode \n" +
//                "                  AND st." + columnName + " > 0 \n" +
                "WHERE  p.cmpcode = ? \n" +
                "       AND pbm.distrcode = ? AND pbm.prodCode = ?";

        Cursor cursorSalesRtn = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distrCode, prodCode});
        if (cursorSalesRtn != null && cursorSalesRtn.getCount() > 0 && cursorSalesRtn.moveToFirst()) {
            do {
                OrderBookingVO bookingVO = new OrderBookingVO();
                bookingVO.setCmpCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_CMP_CODE)));
                bookingVO.setDistrCode(distrCode);
                bookingVO.setProdBatchCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                bookingVO.setProdCode(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_CODE)));
                bookingVO.setProdName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NAME)));
                bookingVO.setSellPrice(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_SELL_PRICE_CAPS))));
                bookingVO.setMrp(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_MRP_CAPS))));

                bookingVO.setProductHierPathName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_NAME)));
                bookingVO.setProductHierPath(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_PATH_CODE)));
                bookingVO.setProdHierValName(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_HIER_LAST_VAL_CODE)));
                bookingVO.setBaseUOM(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_BASE_UOM)));
                bookingVO.setWeightType(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_PROD_WGT_TYPE)));
                bookingVO.setNetWeight(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_PROD_NET_WGT)));
//                bookingVO.setGrossWeight(cursorSalesRtn.getDouble(cursorSalesRtn.getColumnIndex(COLUMN_PROD_GROSS_WGT)));
//                bookingVO.setConversionFactor(getConversionFactor(sfaDatabase, bookingVO.getProdCode(), TABLE_PRODUCT_UOM_MASTER));
//                bookingVO.setSellRateWithTax(new BigDecimal(cursorSalesRtn.getString(cursorSalesRtn.getColumnIndex(COLUMN_SELL_RATE_WITH_TAX))));

                int chkQty = 0;
                if (columnName.equalsIgnoreCase(COLUMN_AVAIL_QTY)) {
                    chkQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(columnName)) -
                            cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_SALEABLE_QTY));
                } else if (columnName.equalsIgnoreCase(COLUMN_UNSALEABLE_QTY)) {
                    chkQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(columnName)) -
                            cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_UNSALEABLE_QTY));
                } else if (columnName.equalsIgnoreCase(COLUMN_OFFER_QTY)) {
                    chkQty = cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(columnName)) -
                            cursorSalesRtn.getInt(cursorSalesRtn.getColumnIndex(COLUMN_RESV_OFFER_QTY));
                }

                if (chkQty <= 0) {
                    chkQty = 0;
                }
                bookingVO.setStockInHand(chkQty);
                bookingVO.setAvailQty(chkQty);

                bookingVOList.add(bookingVO);

            } while (cursorSalesRtn.moveToNext());
        }
        if (cursorSalesRtn != null) {
            cursorSalesRtn.close();
        }

        return bookingVOList;
    }

    public List<OrderBookingVO> getUomForBTR(SFADatabase sfaDatabase, String prodCode, String tableName) {
        List<OrderBookingVO> orderBookingList = new ArrayList<>();
        String query = "select * from " + TABLE_PRODUCT_UOM_MASTER + " where prodCode = ? order by conversionFactor asc limit 1";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{prodCode});
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    OrderBookingVO orderBookingVO = new OrderBookingVO();

                    orderBookingVO.setUomGroupId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    orderBookingVO.setDefaultUomid(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));

                    orderBookingList.add(orderBookingVO);

                } while (c.moveToNext());
            }
            c.close();
        }

        return orderBookingList;
    }


    public List<EditQtyModel> getPreviousOrderQtyDetails(SFADatabase sfaDatabase, String distCode,
                                                         String salesmanCode,
                                                         String retailerCode, String orderInvoiceNo) {

        List<EditQtyModel> orderBookingList = new ArrayList<>();
        Cursor c = null;
        SQLiteDatabase database = sfaDatabase.getReadableDatabase();
        try {
            c = database.rawQuery(QUERY_SELECT_PREVIOUS_ORDER_QTY_PRODUCTS, new String[]{distCode, salesmanCode, retailerCode, orderInvoiceNo});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    EditQtyModel orderBookingVO = new EditQtyModel();
                    orderBookingVO.setBatchCode(c.getString(c.getColumnIndex(COLUMN_BATCH_CODE)).trim());
                    orderBookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE_CAPS)).trim());
                    orderBookingVO.setPreviousQty(c.getInt(c.getColumnIndex(COLUMN_CONVERSION_FACTOR)));
                    orderBookingVO.setAvailableQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                    orderBookingList.add(orderBookingVO);
                } while (c.moveToNext());
            }
        } finally {
            closeCursor(c);
        }
        return orderBookingList;
    }


    /**
     * Insert the new updated location details in tables
     *
     * @param sfaDatabase db instance to write the data in db
     * @param loginCode   retailer login code
     * @param latitude    updated latitude
     * @param longitude   updated longitude
     * @param strPinCode  upload status
     */

    void insertRetailerLocaitonUpdate(SFADatabase sfaDatabase, String loginCode, String updateTo, String latitude,

                                      String longitude, String strPinCode) {


        ContentValues values = new ContentValues();

        values.put(COLUMN_LOGIN_CODE, loginCode);

        values.put(IDBColumns.COLUMN_LATITUDE, latitude);

        values.put(IDBColumns.COLUMN_LONGITUDE, longitude);

        values.put(COLUMN_DATE, DateUtil.getCurrentYearMonthDate());

        values.put(IDBColumns.COLUMN_IMAGE, Globals.getOurInstance().getStrimage());

        values.put(IDBColumns.COLUMN_POSTAL_CODE, strPinCode);

        values.put(COLUMN_UPLOAD_TO, updateTo);

        values.put(COLUMN_MOD_DT, "");

        values.put(COLUMN_UPLOAD, "N");

        sfaDatabase.getDb().insert(IDBColumns.TABLE_UPDATE_LOCATION, null, values);

    }


    /**
     * Delete existing location details for that particular retailer based on the params
     *
     * @param sfaDatabase db instance
     * @param loginCode   current retailer loginCode
     */

    void deleteRetailerExistingLocation(SFADatabase sfaDatabase, String loginCode) {


        String deleteReturns = "loginCode=?";

        String[] deleteArgs = {loginCode};

        sfaDatabase.getDb().delete(IDBColumns.TABLE_UPDATE_LOCATION, deleteReturns, deleteArgs);

    }
}