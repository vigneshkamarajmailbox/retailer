package com.botree.retailerssfa.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.PreviousPOModel;
import com.botree.retailerssfa.models.ProFilterModel;
import com.botree.retailerssfa.models.PurchaseInvoiceModel;
import com.botree.retailerssfa.models.StockOnHandModel;
import com.botree.retailerssfa.util.SFASharedPref;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.db.query.IDBColumns.CGST_VALUE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_APPROVAL_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_AVAIL_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CONVERSION_FACTOR;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_GRN_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_GRN_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_GRN_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_INVOICE_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_INVOICE_DT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_INVOICE_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_INVOICE_NUM;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_INVOICE_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LINE_LEVEL_DISC_AMT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LINE_LEVEL_GROSS_AMT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LINE_LEVEL_NET_AMT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_LINE_LEVEL_TAX_AMT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_MRP_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ORDER_QTY_CAPS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ORDER_VALUE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_BATCH_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PROD_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PURCHASE_ORDER_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_PURCH_PRICE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_RECEIVED_QTY;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_RETURN_DT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALESMAN_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALESMAN_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALES_RETURNNO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SUPPLIER_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_TAX_AMT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UOM_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_UPLOAD;
import static com.botree.retailerssfa.db.query.IDBColumns.CONFIRM_DATE;
import static com.botree.retailerssfa.db.query.IDBColumns.CONFIRM_STATUS;
import static com.botree.retailerssfa.db.query.IDBColumns.GRN_DT;
import static com.botree.retailerssfa.db.query.IDBColumns.GRN_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.IGST_VALUE;
import static com.botree.retailerssfa.db.query.IDBColumns.INVOICE_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.PROD_BATCH_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.SGST_VALUE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PREVIOUS_PURCHASE_ORDERS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PURCHASE_INVOICE;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PURCHASE_INVOICE_REPORT_DETAIL;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PURCHASE_ORDER_BOOKING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PURCHASE_RECEIPT_CONFIRMATION;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_STOCK_ON_HAND;
import static com.botree.retailerssfa.db.query.IDBColumns.UPLOAD_FLAG;
import static com.botree.retailerssfa.db.query.IDBColumns.UTGST_VALUE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;

/**
 * Created by shantarao on 5/1/18.
 */

class DistrPurchaseHelper {

    private static final String TAG = DistrPurchaseHelper.class.getSimpleName();
    private static final String APPEND_AND = " = ? and ";

    DistrPurchaseHelper() {
        // default Constructor
    }

    List<PurchaseInvoiceModel> getPurchaseReceiptHeader(SFADatabase sfaDatabase, String cmpCode) {
        List<PurchaseInvoiceModel> purchaseHeader = new ArrayList<>();
        try {
            String brandQuery;
            Cursor c;
            SQLiteDatabase database = sfaDatabase.getReadableDatabase();
            brandQuery = "Select " + COLUMN_DISTR_CODE + " , " + INVOICE_NO + "," + COLUMN_INVOICE_DT + ", count(*) as totalLines,sum(" + COLUMN_LINE_LEVEL_NET_AMT + ") as totalValue from "
                    + TABLE_PURCHASE_INVOICE + " where " + COLUMN_CMP_CODE + "=? AND " + CONFIRM_STATUS + " = ? AND " + UPLOAD_FLAG + " = ? group by " + INVOICE_NO;
            c = database.rawQuery(brandQuery, new String[]{cmpCode, "N", "Y"});

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    PurchaseInvoiceModel receipt = new PurchaseInvoiceModel();
                    receipt.setCmpCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    receipt.setInvoiceNo(c.getString(c.getColumnIndex(INVOICE_NO)));
                    receipt.setInvoiceDt(c.getString(c.getColumnIndex(COLUMN_INVOICE_DT)));
                    receipt.setItemCount(c.getInt(c.getColumnIndex("totalLines")));
                    receipt.setOrderValue(BigDecimal.valueOf(c.getDouble(c.getColumnIndex("totalValue"))));
                    purchaseHeader.add(receipt);
                }
                c.close();
            }

        } catch (Exception e) {
            Log.e(TAG, "getPurchaseReceiptHeader: " + e.getMessage(), e);
        }
        return purchaseHeader;
    }

    void updatePurchaseReceivedQty(SFADatabase sfaDatabase, String grnCode, List<OrderBookingVO> purchaseReceiptList) {

        for (OrderBookingVO receipt : purchaseReceiptList) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_RECEIVED_QTY, receipt.getReceivedQty());
            values.put(CONFIRM_DATE, System.currentTimeMillis());
            values.put(CONFIRM_STATUS, "Y");
            values.put(COLUMN_GRN_STATUS, "Y");
            values.put(UPLOAD_FLAG, "N");
            values.put(COLUMN_GRN_NO, grnCode);
            values.put(COLUMN_GRN_DATE, System.currentTimeMillis());

            sfaDatabase.getDb().update(TABLE_PURCHASE_INVOICE, values, INVOICE_NO + "=? AND " +
                            PROD_BATCH_CODE + " = ? AND " + COLUMN_PROD_CODE + " = ?",
                    new String[]{String.valueOf(receipt.getOrderInvoiceNo()), receipt.getProdBatchCode(), receipt.getProdCode()});
        }
        sfaDatabase.closeDb();
    }

    void updateStockOnHand(SFADatabase sfaDatabase, List<OrderBookingVO> purchaseReceiptList) {
        Cursor c = null;
        for (OrderBookingVO vo : purchaseReceiptList) {
            String q = "Select prodCode, ProdBatchCode, availQty from m_stockOnHand " +
                    "WHERE\n" +
                    "   cmpCode = ? \n" +
                    "   AND distrCode = ? \n" +
                    "   AND prodCode = ? \n" +
                    "   AND ProdBatchCode = ?";
            c = sfaDatabase.getDb().rawQuery(q, new String[]{
                    vo.getCmpCode(), vo.getDistrCode(),
                    vo.getProdCode(), vo.getProdBatchCode()});

            StockOnHandModel stock = new StockOnHandModel();
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    stock.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                    stock.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                    stock.setAvailQty(c.getInt(c.getColumnIndex(COLUMN_AVAIL_QTY)));
                } while (c.moveToNext());

                String query = "select conversionFactor from m_product_uom_master where " +
                        "   cmpCode = ? \n" +
                        " AND prodCode = ? and uomCode = ?";
                Cursor cursor = sfaDatabase.getDb().rawQuery(query, new String[]{
                        vo.getCmpCode(),
                        vo.getProdCode(), vo.getUomId()});

                int convFactor = 1;
                if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                    convFactor = cursor.getInt(cursor.getColumnIndex(COLUMN_CONVERSION_FACTOR));
                }
                stock.setAvailQty(stock.getAvailQty() +
                        (convFactor * vo.getNoOfItems()));
                if (cursor != null) {
                    cursor.close();
                }
            }

            ContentValues values = new ContentValues();
            values.put(COLUMN_AVAIL_QTY, stock.getAvailQty());
            values.put(COLUMN_UPLOAD, "N");

            String appendAnd = APPEND_AND;
            String update = COLUMN_CMP_CODE + appendAnd + COLUMN_PROD_CODE +
                    appendAnd + COLUMN_PROD_BATCH_CODE + " = ?";
            String[] updateArgs = new String[]{
                    vo.getCmpCode(),
                    vo.getProdCode(), vo.getProdBatchCode()};
            sfaDatabase.getDb().update(TABLE_STOCK_ON_HAND, values, update, updateArgs);
            sfaDatabase.closeDb();
        }
        if (c != null) {
            c.close();
        }
    }

    List<OrderBookingVO> getPurchaseReceiptConfirmData(SFADatabase sfaDatabase, String cmpCode, String distCode) {


        List<OrderBookingVO> bookingVOList = new ArrayList<>();
        String query = "SELECT * FROM '" + TABLE_PURCHASE_RECEIPT_CONFIRMATION + "' WHERE cmpCode =? AND distrCode =? AND confirm_status=? AND upload =?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode, "Y", "N"});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                OrderBookingVO bookingVO = new OrderBookingVO();
                bookingVO.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                bookingVO.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                bookingVO.setOrderNo(c.getString(c.getColumnIndex(INVOICE_NO)));
                bookingVO.setOrderDate(c.getString(c.getColumnIndex(COLUMN_INVOICE_DATE)));
                bookingVO.setGrnDate(c.getString(c.getColumnIndex(GRN_DT)));
                bookingVO.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                bookingVO.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                bookingVO.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                bookingVO.setConfirmQuantity(c.getInt(c.getColumnIndex(COLUMN_INVOICE_QTY)));
                bookingVO.setReceivedQty(c.getDouble(c.getColumnIndex(COLUMN_RECEIVED_QTY)));
                bookingVO.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                bookingVO.setConfirmDate(c.getString(c.getColumnIndex(CONFIRM_DATE)));
                bookingVOList.add(bookingVO);
            } while (c.moveToNext());

        }
        if (c != null) {
            c.close();
        }
        return bookingVOList;
    }

    public List<ProFilterModel> getLOBDataFromDB(SFADatabase sfaDatabase, String cmpCode) {

        List<ProFilterModel> bookingVOList = new ArrayList<>();
        /*ProFilterModel model = new ProFilterModel();
        model.setHierLelevelName1(ALL_CATEGORY);
        bookingVOList.add(model);*/

        /*String query = "SELECT * FROM '" + TABLE_PRODUCT_MASTER + "' WHERE cmpCode =? group by " + COLUMN_PROD_LOB_DIVISTION_CODE;
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                ProFilterModel bookingVO = new ProFilterModel();
                bookingVO.setHierLelevelName1(c.getString(c.getColumnIndex(COLUMN_PROD_LOB_DIVISTION_CODE)));
                bookingVOList.add(bookingVO);
            } while (c.moveToNext());
        }*/

        String query = " SELECT  distinct(lb.lobCode) , lb.lobName\n" +
                "FROM   m_product_master pm \n" +
                "\tINNER JOIN m_lob lb\n" +
                "              ON pm.cmpCode = lb.cmpCode \n" +
                "                 AND pm.lobDivisionCode =  lb.lobCode group by lb.lobCode";

        Cursor c = sfaDatabase.getDb().rawQuery(query, null);
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                ProFilterModel bookingVO = new ProFilterModel();
                bookingVO.setHierLelevelCode1(c.getString(c.getColumnIndex("lobCode")));
                bookingVO.setHierLelevelName1(c.getString(c.getColumnIndex("lobName")));
                bookingVOList.add(bookingVO);
            } while (c.moveToNext());
        }

        if (c != null) {
            c.close();
        }
        return bookingVOList;
    }

    public String[] getTaxBreakUps(SFADatabase sfaDatabase, String invoiceNo) {
        String[] tax = new String[2];
        Double cgst;
        Double sgst;
        Double igst;
        Double utgst;

        String query = "SELECT sum(CGSTValue) as CGSTValue,sum(SGSTValue) as SGSTValue, sum(UTGSTValue) as UTGSTValue, sum(IGSTValue) as IGSTValue FROM '" + TABLE_PURCHASE_ORDER_BOOKING + "' WHERE invoiceNo =?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{invoiceNo});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                cgst = c.getDouble(c.getColumnIndex(CGST_VALUE));
                sgst = c.getDouble(c.getColumnIndex(SGST_VALUE));
                utgst = c.getDouble(c.getColumnIndex(UTGST_VALUE));
                igst = c.getDouble(c.getColumnIndex(IGST_VALUE));
                tax[0] = String.valueOf(cgst + igst);
                tax[1] = String.valueOf(sgst + utgst);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return tax;
    }

    public List<OrderBookingVO> getPreviousPurchaseOrderHeaderDetail(SFADatabase sfaDatabase, String cmpcode, String distCode) {
        List<OrderBookingVO> retailerCreditList = new ArrayList<>();

        String query = "select purchaseOrderNo, date, SUM(orderValue) as totalOrderVal , SUM(taxAmt) as taxAmt, SUM(OrderQty) as OrderQty ,approvalStatus  from r_previous_purchase_orders where cmpCode=? AND distrCode=? group by purchaseOrderNo";

        Cursor cursorOrder = sfaDatabase.getDb().rawQuery(query, new String[]{cmpcode, distCode});
        if (cursorOrder != null && cursorOrder.getCount() > 0) {
            while (cursorOrder.moveToNext()) {
                OrderBookingVO orderData = new OrderBookingVO();

                orderData.setOrderDate(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_DATE)));
                orderData.setOrderInvoiceNo(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_PURCHASE_ORDER_NO)));
                orderData.setTax(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_TAX_AMT)));
                orderData.setQuantity(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));
                orderData.setTotalAmount(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex("totalOrderVal"))));
                orderData.setProductStatus(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_APPROVAL_STATUS)));

                double netAmt = cursorOrder.getDouble(cursorOrder.getColumnIndex("totalOrderVal")) + cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_TAX_AMT));
                orderData.setNetAmount(BigDecimal.valueOf(netAmt));

                retailerCreditList.add(orderData);
            }
        }
        closeCursor(cursorOrder);
        sfaDatabase.closeDb();
        return retailerCreditList;
    }


    public List<OrderBookingVO> fetchPreviousPurchaseOrderLineItem(SFADatabase sfaDatabase, String cmpCode, String distCode, String invoiceNo) {
        List<OrderBookingVO> retailerCreditList = new ArrayList<>();

        String query = "select * from r_previous_purchase_orders where cmpCode=? AND distrCode=? AND purchaseOrderNo=?";

        Cursor cursorOrder = sfaDatabase.getDb().rawQuery(query, new String[]{cmpCode, distCode, invoiceNo});
        if (cursorOrder != null && cursorOrder.getCount() > 0) {
            while (cursorOrder.moveToNext()) {
                OrderBookingVO orderData = new OrderBookingVO();

                orderData.setProdCode(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_PROD_CODE)));
                orderData.setProdName(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_PROD_NAME)));
                orderData.setUomId(cursorOrder.getString(cursorOrder.getColumnIndex(COLUMN_UOM_CODE)));
                orderData.setQuantity(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ORDER_QTY_CAPS)));
                orderData.setTax(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_TAX_AMT)));
                orderData.setOrderValue(BigDecimal.valueOf(cursorOrder.getDouble(cursorOrder.getColumnIndex(COLUMN_ORDER_VALUE))));
                retailerCreditList.add(orderData);
            }
        }
        closeCursor(cursorOrder);
        sfaDatabase.closeDb();
        return retailerCreditList;
    }

    private void closeCursor(Cursor c) {
        if (c != null) {
            c.close();
        }
    }

    public void insertPreviousPurchaseOrderData(SFADatabase sfaDatabase, List<PreviousPOModel> previousPOStatusList) {
        String sql = "INSERT INTO " + TABLE_PREVIOUS_PURCHASE_ORDERS + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = sfaDatabase.getDb().compileStatement(sql);
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
                    Log.w("DUPLICATE_TAG", e);
            }
        }
    }

    public List<PurchaseInvoiceModel> getPurchaseOrderBookingLineItem(BaseDB baseDB) {
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        String distCode = preferences.readString(PREF_DISTRCODE);

        List<PurchaseInvoiceModel> purchaseInvoiceModels = new ArrayList<>();
        Cursor cursor;
        String query = "SELECT * From " + TABLE_PURCHASE_INVOICE + " WHERE cmpCode = ? AND distrCode=? AND " +
                "confirm_status = 'Y' AND uploadFlag = 'N'";
        cursor = baseDB.getDb().rawQuery(query, new String[]{cmpCode, distCode});
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                PurchaseInvoiceModel purchaseInvoiceModel = new PurchaseInvoiceModel();

                purchaseInvoiceModel.setCmpCode(cursor.getString(cursor.getColumnIndex(COLUMN_CMP_CODE)).trim());
                purchaseInvoiceModel.setDistrCode(cursor.getString(cursor.getColumnIndex(COLUMN_DISTR_CODE)).trim());
                purchaseInvoiceModel.setInvoiceNo(cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_NO)).trim());
                purchaseInvoiceModel.setGrnNo(cursor.getString(cursor.getColumnIndex(COLUMN_GRN_NO)).trim());
                purchaseInvoiceModel.setInvoiceDt(cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_DT)).trim());
                purchaseInvoiceModel.setGrnDt(cursor.getString(cursor.getColumnIndex(COLUMN_GRN_DATE)).trim());
                purchaseInvoiceModel.setSupplierCode(cursor.getString(cursor.getColumnIndex(COLUMN_SUPPLIER_CODE)).trim());
                purchaseInvoiceModel.setProdCode(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_CODE)).trim());
                purchaseInvoiceModel.setProdBatchCode(cursor.getString(cursor.getColumnIndex(PROD_BATCH_CODE)).trim());
                purchaseInvoiceModel.setPurchPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_PURCH_PRICE)));
                purchaseInvoiceModel.setMrp(new BigDecimal(cursor.getString(cursor.getColumnIndex(COLUMN_MRP_CAPS)).trim()));
                purchaseInvoiceModel.setInvoiceQty(cursor.getDouble(cursor.getColumnIndex(COLUMN_INVOICE_QTY)));
                purchaseInvoiceModel.setReceivedQty(cursor.getDouble(cursor.getColumnIndex(COLUMN_RECEIVED_QTY)));
                purchaseInvoiceModel.setUomCode(cursor.getString(cursor.getColumnIndex(COLUMN_UOM_CODE)).trim());
                purchaseInvoiceModel.setLineLevelGrossAmt(cursor.getDouble(cursor.getColumnIndex(COLUMN_LINE_LEVEL_GROSS_AMT)));
                purchaseInvoiceModel.setLineLevelTaxAmt(cursor.getDouble(cursor.getColumnIndex(COLUMN_LINE_LEVEL_TAX_AMT)));
                purchaseInvoiceModel.setLineLevelDiscAmt(cursor.getDouble(cursor.getColumnIndex(COLUMN_LINE_LEVEL_DISC_AMT)));
                purchaseInvoiceModel.setLineLevelNetAmt(cursor.getDouble(cursor.getColumnIndex(COLUMN_LINE_LEVEL_NET_AMT)));
                purchaseInvoiceModel.setGrnStatus(cursor.getString(cursor.getColumnIndex(COLUMN_GRN_STATUS)).trim());
                purchaseInvoiceModel.setConfirmStatus(cursor.getString(cursor.getColumnIndex(CONFIRM_STATUS)).trim());
                purchaseInvoiceModel.setConfirmDate(cursor.getString(cursor.getColumnIndex(CONFIRM_DATE)).trim());
                purchaseInvoiceModel.setModDt(String.valueOf(System.currentTimeMillis()));
                purchaseInvoiceModel.setUploadFlag(cursor.getString(cursor.getColumnIndex(UPLOAD_FLAG)).trim());

                purchaseInvoiceModels.add(purchaseInvoiceModel);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return purchaseInvoiceModels;
    }

    public void deletePurchaseOrderByInvoiceNo(SFADatabase sfaDatabase, String invoiceNo) {

        String deleteOrderBooking = "invoiceNo=?";
        String[] deleteArgs = {invoiceNo};
        sfaDatabase.getDb().delete(TABLE_PURCHASE_ORDER_BOOKING, deleteOrderBooking, deleteArgs);
        sfaDatabase.closeDb();
    }

    public void deletePurchaseOrderIfNotComplete(SFADatabase sfaDatabase) {

        String deleteOrderBooking = "confirm_status=?";
        String[] deleteArgs = {"N"};
        sfaDatabase.getDb().delete(TABLE_PURCHASE_ORDER_BOOKING, deleteOrderBooking, deleteArgs);
        sfaDatabase.closeDb();
    }

    public void insertPurchaseInvoiceReportDetail(SFADatabase sfaDatabase, List<PurchaseInvoiceModel> purchaseInvoiceModel) {
        try {
            for (PurchaseInvoiceModel report : purchaseInvoiceModel) {

                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_INVOICE_NUM, report.getInvoiceNo());
                values.put(COLUMN_INVOICE_DT, report.getInvoiceDt());
                values.put(GRN_NO, report.getGrnNo());
                values.put(GRN_DT, report.getGrnDt());
                values.put(COLUMN_GRN_STATUS, report.getGrnStatus());
                values.put(COLUMN_SUPPLIER_CODE, report.getSupplierCode());
                values.put(COLUMN_PROD_CODE, report.getProdCode());
                values.put(COLUMN_PROD_NAME, report.getProdName());
                values.put(PROD_BATCH_CODE, report.getProdBatchCode());
                values.put(COLUMN_PURCH_PRICE, report.getPurchPrice());
                values.put(COLUMN_MRP_CAPS, String.valueOf(report.getMrp()));
                values.put(COLUMN_INVOICE_QTY, report.getInvoiceQty());
                values.put(COLUMN_RECEIVED_QTY, report.getReceivedQty());
                values.put(COLUMN_UOM_CODE, report.getUomCode());
                values.put(COLUMN_LINE_LEVEL_GROSS_AMT, report.getLineLevelGrossAmt());
                values.put(COLUMN_LINE_LEVEL_TAX_AMT, report.getLineLevelTaxAmt());
                values.put(COLUMN_LINE_LEVEL_DISC_AMT, report.getLineLevelDiscAmt());
                values.put(COLUMN_LINE_LEVEL_NET_AMT, report.getLineLevelNetAmt());

                sfaDatabase.getDb().insert(TABLE_PURCHASE_INVOICE_REPORT_DETAIL, null, values);
            }

        } finally {
            sfaDatabase.closeDb();
        }
    }

    public List<PurchaseInvoiceModel> fetchPurchaseInvoiceReportDetail(SFADatabase sfaDatabase, String distrCode) {
        List<PurchaseInvoiceModel> arrayList = new ArrayList<>();
        try {
            SQLiteDatabase database = sfaDatabase.getReadableDatabase();

            String brandQuery = "Select * from " + TABLE_PURCHASE_INVOICE_REPORT_DETAIL + " where " + COLUMN_DISTR_CODE + "=? order by invoiceDt desc";
            Cursor c = database.rawQuery(brandQuery, new String[]{distrCode});

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    PurchaseInvoiceModel model = new PurchaseInvoiceModel();
                    model.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    model.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    model.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NUM)));
                    model.setInvoiceDt(c.getString(c.getColumnIndex(COLUMN_INVOICE_DT)));
                    model.setGrnNo(c.getString(c.getColumnIndex(COLUMN_GRN_NO)));
                    model.setGrnDt(c.getString(c.getColumnIndex(COLUMN_GRN_DATE)));
                    model.setGrnStatus(c.getString(c.getColumnIndex(COLUMN_GRN_STATUS)));
                    model.setSupplierCode(c.getString(c.getColumnIndex(COLUMN_SUPPLIER_CODE)));
                    model.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                    model.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    model.setProdBatchCode(c.getString(c.getColumnIndex(PROD_BATCH_CODE)));
                    model.setPurchPrice(c.getDouble(c.getColumnIndex(COLUMN_PURCH_PRICE)));
                    model.setMrp(BigDecimal.valueOf(c.getDouble(c.getColumnIndex(COLUMN_MRP_CAPS))));
                    model.setInvoiceQty(c.getDouble(c.getColumnIndex(COLUMN_INVOICE_QTY)));
                    model.setReceivedQty(c.getDouble(c.getColumnIndex(COLUMN_RECEIVED_QTY)));
                    model.setUomCode(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    model.setLineLevelGrossAmt(c.getDouble(c.getColumnIndex(COLUMN_LINE_LEVEL_GROSS_AMT)));
                    model.setLineLevelDiscAmt(c.getDouble(c.getColumnIndex(COLUMN_LINE_LEVEL_DISC_AMT)));
                    model.setLineLevelTaxAmt(c.getDouble(c.getColumnIndex(COLUMN_LINE_LEVEL_TAX_AMT)));
                    model.setLineLevelNetAmt(c.getDouble(c.getColumnIndex(COLUMN_LINE_LEVEL_NET_AMT)));
                    arrayList.add(model);
                }
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "fetchAllSalesReportDetail: " + e.getMessage(), e);
        }
        return arrayList;
    }

    public List<OrderBookingVO> getSalesReturnHeaderDetail(SFADatabase sfaDatabase, String cmpcode, String distCode) {
        List<OrderBookingVO> salesReturnVOList = new ArrayList<>();

        String query = "SELECT\n" +
                "  (SELECT sum(returnAmt)\n" +
                "   FROM t_salesreturndetails srd\n" +
                "   WHERE srd.salesreturnNo = srh.salesreturnNo)amount,\n" +
                "       c.customerName,\n" +
                "       s.salesmanName,\n" +
                "       srh.returnDt,\n" +
                "       srh.salesmanCode,\n" +
                "       srh.customerCode,\n" +
                "       srh.routeCode,\n" +
                "       srh.salesReturnNo\n" +
                "FROM t_salesreturnheader srh\n" +
                "INNER JOIN t_customer c ON c.customerCode = srh.customerCode\n" +
                "INNER JOIN m_salesman_master s ON s.salesmanCode = srh.salesmanCode" +
                " where srh.cmpCode=? and srh.distrCode=?";
        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{cmpcode, distCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {

                OrderBookingVO salesReturnVO = new OrderBookingVO();
                salesReturnVO.setDistrCode(distCode);
//                salesReturnVO.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_SALES_RETURNNO)));
//                salesReturnVO.setSalesmanName(c.getString(c.getColumnIndex(COLUMN_SALESMAN_NAME)));
//                salesReturnVO.setRetailerName(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_NAME)));
//                salesReturnVO.setRetailerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
//                salesReturnVO.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)));
//                salesReturnVO.setRouteCode(c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE)));
//                salesReturnVO.setOrderDate(c.getString(c.getColumnIndex(COLUMN_RETURN_DT)));
//                salesReturnVO.setGrossAmt(c.getString(c.getColumnIndex("amount")));

                salesReturnVOList.add(salesReturnVO);

            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return salesReturnVOList;
    }
    /**
     * @param sfaDatabase   db instance
     * @param cmpCode       company code
     * @param distStateCode state code
     * @return list of product code
     */
    public List<String> fetchTop10SKUList(SFADatabase sfaDatabase, String cmpCode, String distStateCode) {

        List<String> top10List = new ArrayList<>();

//        String query = "SELECT tp.prodCode as prodCode FROM " + TABLE_PRODUCTS + " p \n" +
//                "                       INNER JOIN " + TABLE_TOP_10_SKU + " tp \n" +
//                "                               ON tp.cmpCode = p.cmpCode \n" +
//                "                                 AND tp.prodCode = p.prodCode where tp.cmpCode =? and tp.stateCode =?";

        Cursor c = sfaDatabase.getDb().rawQuery("", new String[]{cmpCode, distStateCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                top10List.add(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return top10List;
    }
}
