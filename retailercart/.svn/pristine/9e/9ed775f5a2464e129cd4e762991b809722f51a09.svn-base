package com.botree.retailerssfa.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.botree.retailerssfa.models.DistrReportModel;
import com.botree.retailerssfa.models.MTDReportModel;
import com.botree.retailerssfa.models.OrderBookingHeaderModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.OrderStatusModel;
import com.botree.retailerssfa.models.ReasonVO;
import com.botree.retailerssfa.models.RetailerVO;
import com.botree.retailerssfa.models.SalesReportModel;
import com.botree.retailerssfa.models.YtdSalesReportModel;
import com.botree.retailerssfa.support.Globals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.db.query.IDBColumns.*;

class InventoryHelper {

    private static final String TAG = InventoryHelper.class.getSimpleName();


    InventoryHelper() {
    }

    void deleteExitePurchaseProductById(SFADatabase sfaDatabase, String userCode, String prodCode, String prodBatchCode) {

        String deleteOrderBooking = "distrCode=? and ProdCode=? and BatchCode=? and upload=?";
        String[] deleteArgs = {userCode, prodCode, prodBatchCode, "N"};

        sfaDatabase.getDb().delete(TABLE_PURCHASE_ORDER_BOOKING, deleteOrderBooking, deleteArgs);
        sfaDatabase.closeDb();
    }

    List<RetailerVO> getPendingRetailerFromDB(SFADatabase sfaDatabase, String userCode) {

        List<RetailerVO> retailerVOList = new ArrayList<>();
        try {
            SQLiteDatabase database = sfaDatabase.getReadableDatabase();

            String brandQuery = "Select * from " + TABLE_RETAILER + " where " + COLUMN_DISTR_CODE + "=?";
            Cursor c = database.rawQuery(brandQuery, new String[]{userCode});

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    RetailerVO model = new RetailerVO();
                    model.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                    model.setCustomerName(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_NAME)));
                    model.setRetailerAddr1(c.getString(c.getColumnIndex("retailerAddr1")));
                    retailerVOList.add(model);
                }
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getPendingRetailerFromDB: " + e.getMessage(), e);
        }
        return retailerVOList;
    }

    List<SalesReportModel> fetchAllSalesReportDetail(SFADatabase sfaDatabase, String userCode) {

        List<SalesReportModel> retailerVOList = new ArrayList<>();
        try {
            SQLiteDatabase database = sfaDatabase.getReadableDatabase();

            String brandQuery = "Select * from " + TABLE_SALES_REPORT_DETAIL + " where " + COLUMN_DISTR_CODE + "=? order by invoiceNo desc";
            Cursor c = database.rawQuery(brandQuery, new String[]{userCode});

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    SalesReportModel model = new SalesReportModel();
                    model.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    model.setCustomerName(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    model.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)));
                    model.setSalesmanName(c.getString(c.getColumnIndex(COLUMN_SALESMAN_NAME)));
                    model.setRouteCode(c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE)));
                    model.setRouteName(c.getString(c.getColumnIndex(COLUMN_ROUTE_NAME)));
                    model.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                    model.setCustomerName(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_NAME)));
                    model.setInvoiceNo(c.getString(c.getColumnIndex(COLUMN_INVOICE_NUM)));
                    model.setInvoiceDt(c.getString(c.getColumnIndex(COLUMN_INVOICE_DT)));
                    model.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                    model.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    model.setProdBatchCode(c.getString(c.getColumnIndex(COLUMN_PROD_BATCH_CODE)));
                    model.setTotalInvoiceQty(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));
                    model.setMRP(c.getDouble(c.getColumnIndex(COLUMN_MRP_CAPS)));
                    model.setSellRate(c.getDouble(c.getColumnIndex(COLUMN_SELL_RATE)));
                    model.setActualSellRate(c.getDouble(c.getColumnIndex(COLUMN_ACTUAL_SELL_RATE)));
                    model.setGrossAmt(c.getDouble(c.getColumnIndex(COLUMN_GROSS_AMT)));
                    model.setSplDiscAmt(c.getDouble(c.getColumnIndex(COLUMN_SPL_DISC_AMT)));
                    model.setSchDiscAmt(c.getDouble(c.getColumnIndex(COLUMN_SCH_DISC_AMT)));
                    model.setCashDiscAmt(c.getDouble(c.getColumnIndex(COLUMN_CASH_DISC_AMT)));
                    model.setDbDiscAmt(c.getDouble(c.getColumnIndex(COLUMN_DB_DISC_AMT)));
                    model.setInputStr(c.getString(c.getColumnIndex(COLUMN_INPUT_STR)));
                    model.setCgstAmt(c.getDouble(c.getColumnIndex(CGST_AMT)));
                    model.setSgstAmt(c.getDouble(c.getColumnIndex(SGST_AMT)));
                    model.setIgstAmt(c.getDouble(c.getColumnIndex(IGST_AMT)));
                    model.setTaxAmt(c.getDouble(c.getColumnIndex(COLUMN_TAX_AMT)));
                    model.setNetAmt(c.getDouble(c.getColumnIndex(COLUMN_NET_AMT)));
                    retailerVOList.add(model);
                }
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "fetchAllSalesReportDetail: " + e.getMessage(), e);
        }
        return retailerVOList;
    }

    List<OrderBookingHeaderModel> fetchAllOrderBookingReport(SFADatabase sfaDatabase, String userCode) {

        List<OrderBookingHeaderModel> retailerVOList = new ArrayList<>();
        try {
            SQLiteDatabase database = sfaDatabase.getReadableDatabase();

            String brandQuery = "select cd.cmpName,d.distrName,pob.cmpCode, pob.distrCode, pob.orderNo, pob.customerRefNo, pob.customerShipCode, pob.customerCode, pob.orderDt, \n" +
                    "pob.totalOrderValue,pob.totalGrossValue, pob.remarks, pob.latitude,\n" +
                    "pob.longitude, pob.startTime, pob.endTime, pob.orderStatus, pob.totalDiscount, \n" +
                    "pob.totalTax from r_previousOrderBookingHeader pob left join m_company_detail cd on cd.cmpCode = pob.cmpCode\n" +
                    "left join m_distributor d on pob.cmpCode = d.cmpCode and pob.distrCode = d.distrCode order By customerRefNo desc";
            Cursor c = database.rawQuery(brandQuery, new String[]{});

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    OrderBookingHeaderModel model = new OrderBookingHeaderModel();
                    model.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    model.setDistrName(c.getString(c.getColumnIndex(COLUMN_DISTR_NAME)));
                    model.setCmpName(c.getString(c.getColumnIndex(COLUMN_CMP_NAME)));
                    model.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    model.setOrderNo(c.getString(c.getColumnIndex(COLUMN_ORDER_NO)));
                    model.setCustomerRefNo(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_REF_NO)));
                    model.setCustomerShipCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_SHIP_CODE)));
                    model.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                    model.setOrderDt(c.getString(c.getColumnIndex(COLUMN_ORDER_DT)));
                    model.setTotalOrderValue(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_ORDER_VALUE)));
                    model.setTotalGrossValue(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_GROSS_VALUE)));
                    model.setRemarks(c.getString(c.getColumnIndex(COLUMN_REMARKS)));
                    model.setLatitude(c.getString(c.getColumnIndex(COLUMN_LATITUDE)));
                    model.setLongitude(c.getString(c.getColumnIndex(COLUMN_LONGITUDE)));
                    model.setStartTime(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
                    model.setEndTime(c.getString(c.getColumnIndex(COLUMN_END_TIME)));
                    model.setOrderStatus(c.getString(c.getColumnIndex(COLUMN_ORDER_STATUS)));
                    model.setTotalDiscount(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_DISCOUNT)));
                    model.setTotalTax(c.getDouble(c.getColumnIndex(COLUMN_TOTAL_TAX)));
                    model.setOrderBookingDetailsList(fetchAllOrderBookingDetails(sfaDatabase, model.getCmpCode(), model.getDistrCode(), model.getOrderNo()));
                    model.setActionOrderStatusList(fetchAllOrderStatus(sfaDatabase, model.getCmpCode(), model.getDistrCode(), model.getOrderNo()));
                    retailerVOList.add(model);
                }
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "fetchAllSalesReportDetail: " + e.getMessage(), e);
        }
        return retailerVOList;
    }

    List<OrderStatusModel> fetchAllOrderStatus(SFADatabase sfaDatabase, String cmpCode, String distrCde, String orderNo) {


        List<OrderStatusModel> orderStatusModelList = new ArrayList<>();

        try {

            SQLiteDatabase database = sfaDatabase.getReadableDatabase();


//            String brandQuery = "Select * from " + TABLE_PREVIOUS_ORDER_BOOKING_STATUS +" where cmpCode = ? and distrCode = ? and orderNo = ?" ;

//            Cursor c = database.rawQuery(brandQuery, new String[]{cmpCode, distrCde, orderNo});


            String brandQuery = "Select * from " + TABLE_PREVIOUS_ORDER_BOOKING_STATUS + " where cmpCode = ? and distrCode = ? and orderNo = ? order by actionTime desc";

            Cursor c = database.rawQuery(brandQuery, new String[]{cmpCode, distrCde, orderNo});


            if (c != null && c.getCount() > 0) {

                while (c.moveToNext()) {

                    OrderStatusModel model = new OrderStatusModel();

                    model.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));

                    model.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));

                    model.setOrderNo(c.getString(c.getColumnIndex(COLUMN_ORDER_NO)));

                    model.setOrderStatus(c.getString(c.getColumnIndex(COLUMN_ORDER_STATUS)));

                    model.setActionTime(c.getString(c.getColumnIndex(COLUMN_ACTION_TIME)));

                    model.setFreeText(c.getString(c.getColumnIndex(COLUMN_FREE_TEXT)));

                    orderStatusModelList.add(model);

                }

                c.close();

            }

        } catch (Exception e) {

            Log.e(TAG, "fetchAllSalesReportDetail: " + e.getMessage(), e);

        }

        return orderStatusModelList;

    }


    List<OrderBookingVO> fetchAllOrderBookingDetails(SFADatabase sfaDatabase, String cmpCode, String distrCde, String orderNo) {

        List<OrderBookingVO> retailerVOList = new ArrayList<>();
        try {
            SQLiteDatabase database = sfaDatabase.getReadableDatabase();

            String brandQuery = "Select * from " + TABLE_PREVIOUS_ORDER_BOOKING_REPORT_DETAILS + " where cmpCode = ? and distrCode = ? and orderNo = ?";
            Cursor c = database.rawQuery(brandQuery, new String[]{cmpCode, distrCde, orderNo});

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    OrderBookingVO model = new OrderBookingVO();
                    model.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    model.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    model.setOrderNo(c.getString(c.getColumnIndex(COLUMN_ORDER_NO)));
                    model.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                    model.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    model.setProdBatchCode(c.getString(c.getColumnIndex(PROD_BATCH_CODE)));
                    model.setQuantity(c.getDouble(c.getColumnIndex(COLUMN_ORDER_QTY)));
                    model.setUomId(c.getString(c.getColumnIndex(COLUMN_UOM_CODE)));
                    model.setInputStr(c.getString(c.getColumnIndex(COLUMN_INPUT_STR)));
                    model.setSellPrice(new BigDecimal(c.getDouble(c.getColumnIndex(COLUMN_SELL_RATE))));
                    model.setActualSellRate(new BigDecimal(c.getDouble(c.getColumnIndex(COLUMN_ACTUAL_SELL_RATE))));
                    model.setSchemeAmount(new BigDecimal(c.getDouble(c.getColumnIndex(COLUMN_SCH_AMT))));
                    model.setTax(c.getDouble(c.getColumnIndex(COLUMN_TAX_AMT)));
                    model.setTaxCode(c.getString(c.getColumnIndex(COLUMN_TAX_CODE)));
                    model.setCgstperc(c.getDouble(c.getColumnIndex(CGST_PERCENT)));
                    model.setCgstvalue(new BigDecimal(c.getDouble(c.getColumnIndex(CGST_AMT))));
                    model.setSgstPerc(c.getDouble(c.getColumnIndex(SGST_PERCENT)));
                    model.setSgstValue(new BigDecimal(c.getDouble(c.getColumnIndex(SGST_AMT))));
                    model.setUtgstPerc(c.getDouble(c.getColumnIndex(UTGST_PERCENT)));
                    model.setUtgstValue(new BigDecimal(c.getDouble(c.getColumnIndex(UTGST_VALUE))));
                    model.setIgstPerc(c.getDouble(c.getColumnIndex(IGST_PERCENT)));
                    model.setIgstvalue(new BigDecimal(c.getDouble(c.getColumnIndex(IGST_VALUE))));
                    model.setOrderValue(new BigDecimal(c.getDouble(c.getColumnIndex(COLUMN_ORDER_VALUE))));
                    model.setlGrossAmt(new BigDecimal(c.getDouble(c.getColumnIndex(COLUMN_GROSS_VALUE))));
                    model.setProdType(c.getString(c.getColumnIndex(COLUMN_PROD_TYPE)));

                    retailerVOList.add(model);
                }
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "fetchAllSalesReportDetail: " + e.getMessage(), e);
        }
        return retailerVOList;
    }

    public void insertSalesReportDetail(SFADatabase sfaDatabase, List<SalesReportModel> list) {

        try {
            for (SalesReportModel report : list) {

                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_SALESMAN_CODE, report.getSalesmanCode());
                values.put(COLUMN_SALESMAN_NAME, report.getSalesmanName());
                values.put(COLUMN_ROUTE_CODE, report.getRouteCode());
                values.put(COLUMN_ROUTE_NAME, report.getRouteName());
                values.put(COLUMN_CUSTOMER_CODE, report.getCustomerCode());
                values.put(COLUMN_CUSTOMER_NAME, report.getCustomerName());
                values.put(COLUMN_INVOICE_NUM, report.getInvoiceNo());
                values.put(COLUMN_INVOICE_DT, report.getInvoiceDt());
                values.put(COLUMN_PROD_CODE, report.getProdCode());
                values.put(COLUMN_PROD_NAME, report.getProdName());
                values.put(COLUMN_PROD_BATCH_CODE, report.getProdBatchCode());
                values.put(COLUMN_TOTAL_INVOICE_QTY, report.getTotalInvoiceQty());
                values.put(COLUMN_MRP_CAPS, report.getMRP());
                values.put(COLUMN_SELL_RATE, report.getSellRate());
                values.put(COLUMN_ACTUAL_SELL_RATE, report.getActualSellRate());
                values.put(COLUMN_GROSS_AMT, report.getGrossAmt());
                values.put(COLUMN_SPL_DISC_AMT, report.getSplDiscAmt());
                values.put(COLUMN_SPL_DISC_PERC, report.getSplDiscPerc());
                values.put(COLUMN_SCH_DISC_AMT, report.getSchDiscAmt());
                values.put(COLUMN_SCH_DISC_PERC, report.getSchDiscPerc());
                values.put(COLUMN_CASH_DISC_AMT, report.getCashDiscAmt());
                values.put(COLUMN_CASH_DISC_PERC, report.getCashDiscPerc());
                values.put(COLUMN_DB_DISC_AMT, report.getDbDiscAmt());
                values.put(COLUMN_DB_DISC_PERC, report.getDbDiscPerc());
                values.put(COLUMN_INPUT_STR, report.getInputStr());
                values.put(CGST_PERCENT, report.getCgstPerc());
                values.put(CGST_AMT, report.getCgstAmt());
                values.put(SGST_PERCENT, report.getSgstPerc());
                values.put(SGST_AMT, report.getSgstAmt());
                values.put(IGST_PERCENT, report.getIgstPerc());
                values.put(IGST_AMT, report.getIgstAmt());
                values.put(COLUMN_TAX_AMT, report.getTaxAmt());
                values.put(COLUMN_NET_AMT, report.getNetAmt());
                sfaDatabase.getDb().insert(TABLE_SALES_REPORT_DETAIL, null, values);
            }
            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertSalesReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        }
    }

    public List<ReasonVO> fetchFilterDataByName(SFADatabase sfaDatabase, String catName) {

        List<ReasonVO> reasonVOList = new ArrayList<>();
        try {
            SQLiteDatabase database = sfaDatabase.getReadableDatabase();

            String brandQuery = "";

            if (catName.equalsIgnoreCase("salesman")) {
                brandQuery = "Select distinct(salesmanCode),salesmanName from " + TABLE_SALES_REPORT_DETAIL + " order by salesmanName ASC";
            } else if (catName.equalsIgnoreCase("Route")) {
                brandQuery = "Select distinct(routeCode),routeName from " + TABLE_SALES_REPORT_DETAIL + " order by routeName ASC ";
            } else if (catName.equalsIgnoreCase("Retailer")) {
                brandQuery = "Select distinct(customerCode),customerName from " + TABLE_SALES_REPORT_DETAIL + " order by customerName ASC ";
            }

            Cursor c = database.rawQuery(brandQuery, null);

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    ReasonVO model = new ReasonVO();
                    if (catName.equalsIgnoreCase("salesman")) {
                        model.setReasonCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)));
                        model.setReasonName(c.getString(c.getColumnIndex(COLUMN_SALESMAN_NAME)));
                    } else if (catName.equalsIgnoreCase("Route")) {
                        model.setReasonCode(c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE)));
                        model.setReasonName(c.getString(c.getColumnIndex(COLUMN_ROUTE_NAME)));
                    } else if (catName.equalsIgnoreCase("Retailer")) {
                        model.setReasonCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                        model.setReasonName(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_NAME)));
                    }
                    reasonVOList.add(model);
                }
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getPendingRetailerFromDB: " + e.getMessage(), e);
        }
        return reasonVOList;

    }

    public void insertSalesDayWiseReportDetail(SFADatabase sfaDatabase, List<MTDReportModel> dayWiseReportModel) {
        try {
            for (MTDReportModel report : dayWiseReportModel) {

                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_MONTH_NAME, report.getMonthName());
                values.put(COLUMN_INVOICE_DAY, report.getInvoiceDay());
                values.put(COLUMN_TOT_NET_AMT, report.getTotNetAmt());
                sfaDatabase.getDb().insert(TABLE_SALES_DAY_WISE_REPORT_DETAIL, null, values);
            }

            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertSalesDayWiseReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        }
    }

    public void insertSalesWeekWiseReportDetail(SFADatabase sfaDatabase, List<MTDReportModel> weekWiseReportModel) {
        try {
            for (MTDReportModel report : weekWiseReportModel) {

                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_MONTH_NAME, report.getMonthName());
                values.put(COLUMN_INVOICE_WEEK, report.getInvoiceWeek());
                values.put(COLUMN_TOT_NET_AMT, report.getTotNetAmt());
                sfaDatabase.getDb().insert(TABLE_SALES_WEEK_WISE_REPORT_DETAIL, null, values);
            }

            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertSalesWeekWiseReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        }
    }

    public void insertSalesCustomerWiseReportDetail(SFADatabase sfaDatabase, List<MTDReportModel> customerWiseReportModel) {
        try {
            for (MTDReportModel report : customerWiseReportModel) {

                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_CUSTOMER_CODE, report.getCustomerCode());
                values.put(COLUMN_CUSTOMER_NAME, report.getCustomerName());
                values.put(COLUMN_CHANNEL_CODE, report.getChannelCode());
                values.put(COLUMN_CHANNEL_NAME, report.getChannelName());
                values.put(COLUMN_SUB_CHANNEL_CODE, report.getSubChannelCode());
                values.put(COLUMN_SUB_CHANNEL_NAME, report.getSubChannelName());
                values.put(COLUMN_GROUP_CODE, report.getGroupCode());
                values.put(COLUMN_GROUP_NAME, report.getGroupName());
                values.put(COLUMN_CLASS_CODE, report.getClassCode());
                values.put(COLUMN_CLASS_NAME, report.getClassName());
                values.put(COLUMN_MONTH_NAME, report.getMonthName());
                values.put(COLUMN_INVOICE_WEEK, report.getInvoiceWeek());
                values.put(COLUMN_INVOICE_DAY, report.getInvoiceDay());
                values.put(COLUMN_TOT_NET_AMT, report.getTotNetAmt());
                values.put(COLUMN_INVOICE_COUNT, report.getInvoiceCount());
                sfaDatabase.getDb().insert(TABLE_SALES_CUSTOMER_WISE_REPORT_DETAIL, null, values);
            }

            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertSalesCustomerWiseReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        }
    }

    public void insertSalesChannelWiseReportDetail(SFADatabase sfaDatabase, List<MTDReportModel> channelWiseReportModel) {
        try {
            for (MTDReportModel report : channelWiseReportModel) {

                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_CHANNEL_CODE, report.getChannelCode());
                values.put(COLUMN_CHANNEL_NAME, report.getChannelName());
                values.put(COLUMN_SUB_CHANNEL_CODE, report.getSubChannelCode());
                values.put(COLUMN_SUB_CHANNEL_NAME, report.getSubChannelName());
                values.put(COLUMN_GROUP_CODE, report.getGroupCode());
                values.put(COLUMN_GROUP_NAME, report.getGroupName());
                values.put(COLUMN_CLASS_CODE, report.getClassCode());
                values.put(COLUMN_CLASS_NAME, report.getClassName());
                values.put(COLUMN_NO_OF_CUSTOMERS, report.getNoOfCustomer());
                values.put(COLUMN_TOT_NET_AMT, report.getTotNetAmt());

                sfaDatabase.getDb().insert(TABLE_SALES_CHANNEL_WISE_REPORT_DETAIL, null, values);
            }

            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertSalesChannelWiseReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        }
    }

    public List<MTDReportModel> getCustomerWiseReport(SFADatabase sfaDatabase) {
        List<MTDReportModel> dayWiseReport = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SALES_CUSTOMER_WISE_REPORT_DETAIL + " order by channelName";
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, null);

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel mtdReportModel = new MTDReportModel();
                    mtdReportModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    mtdReportModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    mtdReportModel.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                    mtdReportModel.setCustomerName(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_NAME)));
                    mtdReportModel.setChannelCode(c.getString(c.getColumnIndex(COLUMN_CHANNEL_CODE)));
                    mtdReportModel.setChannelName(c.getString(c.getColumnIndex(COLUMN_CHANNEL_NAME)));
                    mtdReportModel.setSubChannelCode(c.getString(c.getColumnIndex(COLUMN_SUB_CHANNEL_CODE)));
                    mtdReportModel.setSubChannelName(c.getString(c.getColumnIndex(COLUMN_SUB_CHANNEL_NAME)));
                    mtdReportModel.setGroupCode(c.getString(c.getColumnIndex(COLUMN_GROUP_CODE)));
                    mtdReportModel.setGroupName(c.getString(c.getColumnIndex(COLUMN_GROUP_NAME)));
                    mtdReportModel.setClassCode(c.getString(c.getColumnIndex(COLUMN_CLASS_CODE)));
                    mtdReportModel.setClassName(c.getString(c.getColumnIndex(COLUMN_CLASS_NAME)));
                    mtdReportModel.setMonthName(c.getString(c.getColumnIndex(COLUMN_MONTH_NAME)));
                    mtdReportModel.setInvoiceWeek(c.getString(c.getColumnIndex(COLUMN_INVOICE_WEEK)));
                    mtdReportModel.setInvoiceDay(c.getInt(c.getColumnIndex(COLUMN_INVOICE_DAY)));
                    mtdReportModel.setTotNetAmt(c.getDouble(c.getColumnIndex(COLUMN_TOT_NET_AMT)));
                    mtdReportModel.setInvoiceCount(c.getInt(c.getColumnIndex(COLUMN_INVOICE_COUNT)));
                    dayWiseReport.add(mtdReportModel);

                } while (c.moveToNext());
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getCustomerWiseReport: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return dayWiseReport;
    }

    public List<MTDReportModel> getDayWiseReport(SFADatabase sfaDatabase) {
        List<MTDReportModel> dayWiseReport = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SALES_DAY_WISE_REPORT_DETAIL;
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, null);

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel mtdReportModel = new MTDReportModel();
                    mtdReportModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    mtdReportModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    mtdReportModel.setMonthName(c.getString(c.getColumnIndex(COLUMN_MONTH_NAME)));
                    mtdReportModel.setInvoiceDay(c.getInt(c.getColumnIndex(COLUMN_INVOICE_DAY)));
                    mtdReportModel.setTotNetAmt(c.getDouble(c.getColumnIndex(COLUMN_TOT_NET_AMT)));
                    dayWiseReport.add(mtdReportModel);

                } while (c.moveToNext());
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getDayWiseReport: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return dayWiseReport;
    }

    public List<MTDReportModel> getWeekWiseReport(SFADatabase sfaDatabase) {
        List<MTDReportModel> weekWiseReport = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SALES_WEEK_WISE_REPORT_DETAIL;
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, null);

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel mtdReportModel = new MTDReportModel();
                    mtdReportModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    mtdReportModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    mtdReportModel.setMonthName(c.getString(c.getColumnIndex(COLUMN_MONTH_NAME)));
                    mtdReportModel.setInvoiceWeek(c.getString(c.getColumnIndex(COLUMN_INVOICE_WEEK)));
                    mtdReportModel.setTotNetAmt(c.getDouble(c.getColumnIndex(COLUMN_TOT_NET_AMT)));
                    weekWiseReport.add(mtdReportModel);

                } while (c.moveToNext());
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getWeekWiseReport: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return weekWiseReport;
    }

    public List<MTDReportModel> getChannelWiseReport(SFADatabase sfaDatabase) {
        List<MTDReportModel> channelWiseReport = new ArrayList<>();
        String query = "SELECT SUM(noOfCustomer) AS noOfCustomer,\n" +
                "       SUM(totNetAmt) AS totNetAmt,\n" +
                "       channelCode,\n" +
                "       channelName\n" +
                "FROM r_sales_channel_wise_report_detail\n" +
                "GROUP BY channelCode";
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, null);

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel mtdReportModel = new MTDReportModel();
                    mtdReportModel.setChannelCode(c.getString(c.getColumnIndex(COLUMN_CHANNEL_CODE)));
                    mtdReportModel.setChannelName(c.getString(c.getColumnIndex(COLUMN_CHANNEL_NAME)));
                    mtdReportModel.setTotNetAmt(c.getDouble(c.getColumnIndex(COLUMN_TOT_NET_AMT)));
                    mtdReportModel.setNoOfCustomer(c.getInt(c.getColumnIndex(COLUMN_NO_OF_CUSTOMERS)));
                    channelWiseReport.add(mtdReportModel);

                } while (c.moveToNext());
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getChannelWiseReport: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return channelWiseReport;
    }

    public void insertYtdRouteWiseReportDetail(SFADatabase sfaDatabase, List<YtdSalesReportModel> reportModels) {
        try {
            for (YtdSalesReportModel report : reportModels) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_ROUTE_CODE, report.getRouteCode());
                values.put(COLUMN_ROUTE_NAME, report.getRouteName());
                values.put(COLUMN_QTY_1, Globals.checkNull(String.valueOf(report.getQty1()), "0"));
                values.put(COLUMN_QTY_2, Globals.checkNull(String.valueOf(report.getQty2()), "0"));
                values.put(COLUMN_QTY_3, Globals.checkNull(String.valueOf(report.getQty3()), "0"));
                values.put(COLUMN_QTY_4, Globals.checkNull(String.valueOf(report.getQty4()), "0"));
                values.put(COLUMN_QTY_5, Globals.checkNull(String.valueOf(report.getQty5()), "0"));
                values.put(COLUMN_QTY_6, Globals.checkNull(String.valueOf(report.getQty6()), "0"));
                values.put(COLUMN_QTY_7, Globals.checkNull(String.valueOf(report.getQty7()), "0"));
                values.put(COLUMN_QTY_8, Globals.checkNull(String.valueOf(report.getQty8()), "0"));
                values.put(COLUMN_QTY_9, Globals.checkNull(String.valueOf(report.getQty9()), "0"));
                values.put(COLUMN_QTY_10, Globals.checkNull(String.valueOf(report.getQty10()), "0"));
                values.put(COLUMN_QTY_11, Globals.checkNull(String.valueOf(report.getQty11()), "0"));
                values.put(COLUMN_QTY_12, Globals.checkNull(String.valueOf(report.getQty12()), "0"));
                values.put(COLUMN_VALUE_1, Globals.checkNull(String.valueOf(report.getValue1()), "0"));
                values.put(COLUMN_VALUE_2, Globals.checkNull(String.valueOf(report.getValue2()), "0"));
                values.put(COLUMN_VALUE_3, Globals.checkNull(String.valueOf(report.getValue3()), "0"));
                values.put(COLUMN_VALUE_4, Globals.checkNull(String.valueOf(report.getValue4()), "0"));
                values.put(COLUMN_VALUE_5, Globals.checkNull(String.valueOf(report.getValue5()), "0"));
                values.put(COLUMN_VALUE_6, Globals.checkNull(String.valueOf(report.getValue6()), "0"));
                values.put(COLUMN_VALUE_7, Globals.checkNull(String.valueOf(report.getValue7()), "0"));
                values.put(COLUMN_VALUE_8, Globals.checkNull(String.valueOf(report.getValue8()), "0"));
                values.put(COLUMN_VALUE_9, Globals.checkNull(String.valueOf(report.getValue9()), "0"));
                values.put(COLUMN_VALUE_10, Globals.checkNull(String.valueOf(report.getValue10()), "0"));
                values.put(COLUMN_VALUE_11, Globals.checkNull(String.valueOf(report.getValue11()), "0"));
                values.put(COLUMN_VALUE_12, Globals.checkNull(String.valueOf(report.getValue12()), "0"));
                sfaDatabase.getDb().insert(TABLE_YTD_ROUTE_WISE_REPORT, null, values);
            }

            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertYtdRouteWiseReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        }
    }

    public void insertYtdCustomerWiseReportDetail(SFADatabase sfaDatabase, List<YtdSalesReportModel> reportModels) {
        try {
            for (YtdSalesReportModel report : reportModels) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_CUSTOMER_CODE, report.getCustomerCode());
                values.put(COLUMN_CUSTOMER_NAME, report.getCustomerName());
                values.put(COLUMN_QTY_1, Globals.checkNull(String.valueOf(report.getQty1()), "0"));
                values.put(COLUMN_QTY_2, Globals.checkNull(String.valueOf(report.getQty2()), "0"));
                values.put(COLUMN_QTY_3, Globals.checkNull(String.valueOf(report.getQty3()), "0"));
                values.put(COLUMN_QTY_4, Globals.checkNull(String.valueOf(report.getQty4()), "0"));
                values.put(COLUMN_QTY_5, Globals.checkNull(String.valueOf(report.getQty5()), "0"));
                values.put(COLUMN_QTY_6, Globals.checkNull(String.valueOf(report.getQty6()), "0"));
                values.put(COLUMN_QTY_7, Globals.checkNull(String.valueOf(report.getQty7()), "0"));
                values.put(COLUMN_QTY_8, Globals.checkNull(String.valueOf(report.getQty8()), "0"));
                values.put(COLUMN_QTY_9, Globals.checkNull(String.valueOf(report.getQty9()), "0"));
                values.put(COLUMN_QTY_10, Globals.checkNull(String.valueOf(report.getQty10()), "0"));
                values.put(COLUMN_QTY_11, Globals.checkNull(String.valueOf(report.getQty11()), "0"));
                values.put(COLUMN_QTY_12, Globals.checkNull(String.valueOf(report.getQty12()), "0"));
                values.put(COLUMN_VALUE_1, Globals.checkNull(String.valueOf(report.getValue1()), "0"));
                values.put(COLUMN_VALUE_2, Globals.checkNull(String.valueOf(report.getValue2()), "0"));
                values.put(COLUMN_VALUE_3, Globals.checkNull(String.valueOf(report.getValue3()), "0"));
                values.put(COLUMN_VALUE_4, Globals.checkNull(String.valueOf(report.getValue4()), "0"));
                values.put(COLUMN_VALUE_5, Globals.checkNull(String.valueOf(report.getValue5()), "0"));
                values.put(COLUMN_VALUE_6, Globals.checkNull(String.valueOf(report.getValue6()), "0"));
                values.put(COLUMN_VALUE_7, Globals.checkNull(String.valueOf(report.getValue7()), "0"));
                values.put(COLUMN_VALUE_8, Globals.checkNull(String.valueOf(report.getValue8()), "0"));
                values.put(COLUMN_VALUE_9, Globals.checkNull(String.valueOf(report.getValue9()), "0"));
                values.put(COLUMN_VALUE_10, Globals.checkNull(String.valueOf(report.getValue10()), "0"));
                values.put(COLUMN_VALUE_11, Globals.checkNull(String.valueOf(report.getValue11()), "0"));
                values.put(COLUMN_VALUE_12, Globals.checkNull(String.valueOf(report.getValue12()), "0"));
                sfaDatabase.getDb().insert(TABLE_YTD_CUSTOMER_WISE_REPORT, null, values);
            }

            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertYtdCustomerWiseReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        }
    }

    public void insertYtdSalesmanWiseReportDetail(SFADatabase sfaDatabase, List<YtdSalesReportModel> reportModels) {
        try {
            for (YtdSalesReportModel report : reportModels) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_SALESMAN_CODE, report.getSalesmanCode());
                values.put(COLUMN_SALESMAN_NAME, report.getSalesmanName());
                values.put(COLUMN_QTY_1, Globals.checkNull(String.valueOf(report.getQty1()), "0"));
                values.put(COLUMN_QTY_2, Globals.checkNull(String.valueOf(report.getQty2()), "0"));
                values.put(COLUMN_QTY_3, Globals.checkNull(String.valueOf(report.getQty3()), "0"));
                values.put(COLUMN_QTY_4, Globals.checkNull(String.valueOf(report.getQty4()), "0"));
                values.put(COLUMN_QTY_5, Globals.checkNull(String.valueOf(report.getQty5()), "0"));
                values.put(COLUMN_QTY_6, Globals.checkNull(String.valueOf(report.getQty6()), "0"));
                values.put(COLUMN_QTY_7, Globals.checkNull(String.valueOf(report.getQty7()), "0"));
                values.put(COLUMN_QTY_8, Globals.checkNull(String.valueOf(report.getQty8()), "0"));
                values.put(COLUMN_QTY_9, Globals.checkNull(String.valueOf(report.getQty9()), "0"));
                values.put(COLUMN_QTY_10, Globals.checkNull(String.valueOf(report.getQty10()), "0"));
                values.put(COLUMN_QTY_11, Globals.checkNull(String.valueOf(report.getQty11()), "0"));
                values.put(COLUMN_QTY_12, Globals.checkNull(String.valueOf(report.getQty12()), "0"));
                values.put(COLUMN_VALUE_1, Globals.checkNull(String.valueOf(report.getValue1()), "0"));
                values.put(COLUMN_VALUE_2, Globals.checkNull(String.valueOf(report.getValue2()), "0"));
                values.put(COLUMN_VALUE_3, Globals.checkNull(String.valueOf(report.getValue3()), "0"));
                values.put(COLUMN_VALUE_4, Globals.checkNull(String.valueOf(report.getValue4()), "0"));
                values.put(COLUMN_VALUE_5, Globals.checkNull(String.valueOf(report.getValue5()), "0"));
                values.put(COLUMN_VALUE_6, Globals.checkNull(String.valueOf(report.getValue6()), "0"));
                values.put(COLUMN_VALUE_7, Globals.checkNull(String.valueOf(report.getValue7()), "0"));
                values.put(COLUMN_VALUE_8, Globals.checkNull(String.valueOf(report.getValue8()), "0"));
                values.put(COLUMN_VALUE_9, Globals.checkNull(String.valueOf(report.getValue9()), "0"));
                values.put(COLUMN_VALUE_10, Globals.checkNull(String.valueOf(report.getValue10()), "0"));
                values.put(COLUMN_VALUE_11, Globals.checkNull(String.valueOf(report.getValue11()), "0"));
                values.put(COLUMN_VALUE_12, Globals.checkNull(String.valueOf(report.getValue12()), "0"));
                sfaDatabase.getDb().insert(TABLE_YTD_SALESMAN_WISE_REPORT, null, values);
            }

            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertYtdSalesmanWiseReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        }
    }

    public void insertYtdProductWiseReportDetail(SFADatabase sfaDatabase, List<YtdSalesReportModel> reportModels) {
        try {
            for (YtdSalesReportModel report : reportModels) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_PROD_CODE, report.getProdCode());
                values.put(COLUMN_PROD_NAME, report.getProdName());
                values.put(COLUMN_QTY_1, Globals.checkNull(String.valueOf(report.getQty1()), "0"));
                values.put(COLUMN_QTY_2, Globals.checkNull(String.valueOf(report.getQty2()), "0"));
                values.put(COLUMN_QTY_3, Globals.checkNull(String.valueOf(report.getQty3()), "0"));
                values.put(COLUMN_QTY_4, Globals.checkNull(String.valueOf(report.getQty4()), "0"));
                values.put(COLUMN_QTY_5, Globals.checkNull(String.valueOf(report.getQty5()), "0"));
                values.put(COLUMN_QTY_6, Globals.checkNull(String.valueOf(report.getQty6()), "0"));
                values.put(COLUMN_QTY_7, Globals.checkNull(String.valueOf(report.getQty7()), "0"));
                values.put(COLUMN_QTY_8, Globals.checkNull(String.valueOf(report.getQty8()), "0"));
                values.put(COLUMN_QTY_9, Globals.checkNull(String.valueOf(report.getQty9()), "0"));
                values.put(COLUMN_QTY_10, Globals.checkNull(String.valueOf(report.getQty10()), "0"));
                values.put(COLUMN_QTY_11, Globals.checkNull(String.valueOf(report.getQty11()), "0"));
                values.put(COLUMN_QTY_12, Globals.checkNull(String.valueOf(report.getQty12()), "0"));
                values.put(COLUMN_VALUE_1, Globals.checkNull(String.valueOf(report.getValue1()), "0"));
                values.put(COLUMN_VALUE_2, Globals.checkNull(String.valueOf(report.getValue2()), "0"));
                values.put(COLUMN_VALUE_3, Globals.checkNull(String.valueOf(report.getValue3()), "0"));
                values.put(COLUMN_VALUE_4, Globals.checkNull(String.valueOf(report.getValue4()), "0"));
                values.put(COLUMN_VALUE_5, Globals.checkNull(String.valueOf(report.getValue5()), "0"));
                values.put(COLUMN_VALUE_6, Globals.checkNull(String.valueOf(report.getValue6()), "0"));
                values.put(COLUMN_VALUE_7, Globals.checkNull(String.valueOf(report.getValue7()), "0"));
                values.put(COLUMN_VALUE_8, Globals.checkNull(String.valueOf(report.getValue8()), "0"));
                values.put(COLUMN_VALUE_9, Globals.checkNull(String.valueOf(report.getValue9()), "0"));
                values.put(COLUMN_VALUE_10, Globals.checkNull(String.valueOf(report.getValue10()), "0"));
                values.put(COLUMN_VALUE_11, Globals.checkNull(String.valueOf(report.getValue11()), "0"));
                values.put(COLUMN_VALUE_12, Globals.checkNull(String.valueOf(report.getValue12()), "0"));
                sfaDatabase.getDb().insert(TABLE_YTD_PRODUCT_WISE_REPORT, null, values);
            }

            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertYtdProductWiseReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        }
    }

    public void insertMtdSalesmanWiseReportDetail(SFADatabase sfaDatabase, List<MTDReportModel> channelWiseReportModel) {
        try {
            for (MTDReportModel report : channelWiseReportModel) {

                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_SALESMAN_NAME, report.getSalesmanName());
                values.put(COLUMN_SALESMAN_CODE, report.getSalesmanCode());
                values.put(COLUMN_MONTH_NAME, report.getMonthName());
                values.put(COLUMN_INVOICE_WEEK, report.getInvoiceWeek());
                values.put(COLUMN_INVOICE_DAY, report.getInvoiceDay());
                values.put(COLUMN_TOT_NET_AMT, report.getTotNetAmt());
                values.put(COLUMN_INVOICE_COUNT, report.getInvoiceCount());

                sfaDatabase.getDb().insert(TABLE_SALES_SALESMAN_WISE_REPORT_DETAIL, null, values);
            }

            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertMtdSalesmanWiseReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        }
    }

    public void insertMtdSalesRouteWiseReportDetail(SFADatabase sfaDatabase, List<MTDReportModel> channelWiseReportModel) {
        try {
            for (MTDReportModel report : channelWiseReportModel) {

                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_ROUTE_NAME, report.getRouteName());
                values.put(COLUMN_ROUTE_CODE, report.getRouteCode());
                values.put(COLUMN_MONTH_NAME, report.getMonthName());
                values.put(COLUMN_INVOICE_WEEK, report.getInvoiceWeek());
                values.put(COLUMN_INVOICE_DAY, report.getInvoiceDay());
                values.put(COLUMN_TOT_NET_AMT, report.getTotNetAmt());
                values.put(COLUMN_INVOICE_COUNT, report.getInvoiceCount());

                sfaDatabase.getDb().insert(TABLE_SALES_ROUTE_WISE_REPORT_DETAIL, null, values);
            }

            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertMtdSalesRouteWiseReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        }
    }

    public List<MTDReportModel> getMtdSalesmanWiseReport(SFADatabase sfaDatabase) {
        List<MTDReportModel> channelWiseReport = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SALES_SALESMAN_WISE_REPORT_DETAIL;
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, null);

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel mtdReportModel = new MTDReportModel();
                    mtdReportModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    mtdReportModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    mtdReportModel.setSalesmanCode(c.getString(c.getColumnIndex(COLUMN_SALESMAN_CODE)));
                    mtdReportModel.setSalesmanName(c.getString(c.getColumnIndex(COLUMN_SALESMAN_NAME)));
                    mtdReportModel.setMonthName(c.getString(c.getColumnIndex(COLUMN_MONTH_NAME)));
                    mtdReportModel.setInvoiceWeek(c.getString(c.getColumnIndex(COLUMN_INVOICE_WEEK)));
                    mtdReportModel.setInvoiceDay(c.getInt(c.getColumnIndex(COLUMN_INVOICE_DAY)));
                    mtdReportModel.setTotNetAmt(c.getDouble(c.getColumnIndex(COLUMN_TOT_NET_AMT)));
                    mtdReportModel.setInvoiceCount(c.getInt(c.getColumnIndex(COLUMN_INVOICE_COUNT)));
                    channelWiseReport.add(mtdReportModel);

                } while (c.moveToNext());
            }
            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "getMtdSalesmanWiseReport: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return channelWiseReport;
    }

    public List<MTDReportModel> getMtdRouteWiseReport(SFADatabase sfaDatabase) {
        List<MTDReportModel> channelWiseReport = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SALES_ROUTE_WISE_REPORT_DETAIL;
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, null);

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel mtdReportModel = new MTDReportModel();
                    mtdReportModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    mtdReportModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    mtdReportModel.setRouteName(c.getString(c.getColumnIndex(COLUMN_ROUTE_NAME)));
                    mtdReportModel.setRouteCode(c.getString(c.getColumnIndex(COLUMN_ROUTE_CODE)));
                    mtdReportModel.setMonthName(c.getString(c.getColumnIndex(COLUMN_MONTH_NAME)));
                    mtdReportModel.setInvoiceWeek(c.getString(c.getColumnIndex(COLUMN_INVOICE_WEEK)));
                    mtdReportModel.setInvoiceDay(c.getInt(c.getColumnIndex(COLUMN_INVOICE_DAY)));
                    mtdReportModel.setTotNetAmt(c.getDouble(c.getColumnIndex(COLUMN_TOT_NET_AMT)));
                    mtdReportModel.setInvoiceCount(c.getInt(c.getColumnIndex(COLUMN_INVOICE_COUNT)));
                    channelWiseReport.add(mtdReportModel);

                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "getMtdRouteWiseReport: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return channelWiseReport;
    }

    public List<MTDReportModel> loadCustomerChannel(SFADatabase sfaDatabase, String distrCode) {
        List<MTDReportModel> retailerCategoryList = new ArrayList<>();
        Cursor c = null;
        sfaDatabase.openWritableDb();
        try {
            String query = "SELECT rChannel.channelCode,\n" +
                    "       rChannel.channelName\n" +
                    "FROM m_RetailerChannels mChannel\n" +
                    "INNER JOIN r_sales_customer_wise_report_detail rChannel ON mChannel.channelCode = rChannel.channelCode\n" +
                    "WHERE mChannel.distrCode =?\n" +
                    "GROUP BY rChannel.channelCode";

            c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode});
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel retailerCategoryvo = new MTDReportModel();
                    retailerCategoryvo.setChannelName(c.getString(c.getColumnIndex(COLUMN_CHANNEL_NAME)).trim());
                    retailerCategoryvo.setChannelCode(c.getString(c.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                    retailerCategoryList.add(retailerCategoryvo);
                } while (c.moveToNext());
            }

            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "loadCustomerChannel: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return retailerCategoryList;
    }

    public List<MTDReportModel> loadCustomerSubChannel(SFADatabase sfaDatabase, String distrCode, String subChannelCode) {
        List<MTDReportModel> retailerCategoryList = new ArrayList<>();
        Cursor c = null;
        sfaDatabase.openWritableDb();
        try {
            String query = "SELECT subChannelCode,\n" +
                    "       subChannelName\n" +
                    "FROM r_sales_customer_wise_report_detail\n" +
                    "WHERE distrCode =?\n" +
                    "  AND channelCode=?\n" +
                    "GROUP BY subChannelCode";

            c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, subChannelCode});
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel retailerCategoryvo = new MTDReportModel();
                    retailerCategoryvo.setSubChannelCode(c.getString(c.getColumnIndex(COLUMN_SUB_CHANNEL_CODE)).trim());
                    retailerCategoryvo.setSubChannelName(c.getString(c.getColumnIndex(COLUMN_SUB_CHANNEL_NAME)).trim());
                    retailerCategoryList.add(retailerCategoryvo);
                } while (c.moveToNext());
            }

            if (c != null) {
                c.close();
            }

        } catch (Exception e) {
            Log.e(TAG, "loadCustomerSubChannel: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return retailerCategoryList;
    }

    public List<MTDReportModel> loadCustomerGroupData(SFADatabase sfaDatabase, String distrCode, String channelCode) {

        List<MTDReportModel> retailerCategoryList = new ArrayList<>();
        Cursor c = null;
        sfaDatabase.openWritableDb();
        try {
            String query = "SELECT channelCode,\n" +
                    "       groupCode,\n" +
                    "       groupName\n" +
                    "FROM r_sales_customer_wise_report_detail\n" +
                    "WHERE distrCode =?\n" +
                    "  AND subChannelCode=?\n" +
                    "GROUP BY groupCode";

            c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, channelCode});
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel retailerCategoryvo = new MTDReportModel();
                    retailerCategoryvo.setChannelCode(c.getString(c.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                    retailerCategoryvo.setGroupCode(c.getString(c.getColumnIndex("groupCode")).trim());
                    retailerCategoryvo.setGroupName(c.getString(c.getColumnIndex("groupName")).trim());
                    retailerCategoryList.add(retailerCategoryvo);
                } while (c.moveToNext());
            }

            if (c != null) {
                c.close();
            }

        } catch (Exception e) {
            Log.e(TAG, "loadCustomerGroupData: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return retailerCategoryList;
    }

    public List<MTDReportModel> loadCustomerClassData(SFADatabase sfaDatabase, String distrCode, String channelCode, String groupCode) {

        List<MTDReportModel> retailerCategoryList = new ArrayList<>();
        Cursor c = null;
        sfaDatabase.openWritableDb();
        try {
            String query = "SELECT channelCode,\n" +
                    "       groupCode,\n" +
                    "       classCode,\n" +
                    "       className\n" +
                    "FROM r_sales_customer_wise_report_detail\n" +
                    "WHERE distrCode =?\n" +
                    "  AND channelCode=?\n" +
                    "  AND groupCode=?\n" +
                    "GROUP BY classCode";

            c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, channelCode, groupCode});
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel retailerCategoryvo = new MTDReportModel();
                    retailerCategoryvo.setChannelCode(c.getString(c.getColumnIndex(COLUMN_CHANNEL_CODE)).trim());
                    retailerCategoryvo.setGroupCode(c.getString(c.getColumnIndex("groupCode")).trim());
                    retailerCategoryvo.setClassName(c.getString(c.getColumnIndex("className")).trim());
                    retailerCategoryvo.setClassCode(c.getString(c.getColumnIndex("classCode")).trim());
                    retailerCategoryList.add(retailerCategoryvo);
                } while (c.moveToNext());
            }

            if (c != null) {
                c.close();
            }

        } catch (Exception e) {
            Log.e(TAG, "loadCustomerClassData: " + e.getMessage(), e);
            if (c != null) {
                c.close();
            }
        }

        return retailerCategoryList;
    }

    public List<MTDReportModel> loadCustomerData(SFADatabase sfaDatabase, String distrCode) {
        List<MTDReportModel> customerData = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SALES_CUSTOMER_WISE_REPORT_DETAIL + " WHERE " + COLUMN_DISTR_CODE + "=? GROUP BY " + COLUMN_CUSTOMER_CODE;
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, new String[]{distrCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel mtdReportModel = new MTDReportModel();
                    mtdReportModel.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)));
                    mtdReportModel.setCustomerName(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_NAME)));
                    customerData.add(mtdReportModel);

                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "loadCustomerData: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return customerData;
    }

    public List<MTDReportModel> loadCustomerWise(SFADatabase sfaDatabase, String distrCode, String channelCode, String subChannelCode, String groupCode, String classCode) {
        List<MTDReportModel> retailerCategoryList = new ArrayList<>();
        sfaDatabase.openWritableDb();
        String query = "";
        Cursor c = null;
        try {
            if (!classCode.equals("0") && !classCode.isEmpty()) {
                query = "SELECT *\n" +
                        "FROM r_sales_customer_wise_report_detail\n" +
                        "WHERE distrCode =?\n" +
                        "  AND channelCode=?\n" +
                        "  AND subChannelCode=?\n" +
                        "  AND groupCode=?\n" +
                        "  AND classCode=?\n" +
                        "GROUP BY customerCode";
                c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, channelCode, subChannelCode, groupCode, classCode});

            } else if (channelCode != null && !channelCode.isEmpty() && (subChannelCode == null || subChannelCode.isEmpty())
                    && (groupCode == null || groupCode.isEmpty()) && (classCode.equals("0") || classCode.isEmpty())) {
                query = "SELECT *\n" +
                        "FROM r_sales_customer_wise_report_detail\n" +
                        "WHERE distrCode =?\n" +
                        "  AND channelCode=?\n" +
                        "GROUP BY customerCode";
                c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, channelCode});

            } else if (channelCode != null && !channelCode.isEmpty() && subChannelCode != null && !subChannelCode.isEmpty() &&
                    (groupCode == null || groupCode.isEmpty()) && (classCode.equals("0") || classCode.isEmpty())) {
                query = "SELECT *\n" +
                        "FROM r_sales_customer_wise_report_detail\n" +
                        "WHERE distrCode =?\n" +
                        "  AND channelCode=?\n" +
                        "  AND subChannelCode=?\n" +
                        "GROUP BY customerCode";
                c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, channelCode, subChannelCode});

            } else if (channelCode != null && !channelCode.isEmpty() && subChannelCode != null && !subChannelCode.isEmpty() &&
                    groupCode != null && !groupCode.isEmpty() && (classCode.equals("0") || classCode.isEmpty())) {
                query = "SELECT *\n" +
                        "FROM r_sales_customer_wise_report_detail\n" +
                        "WHERE distrCode =?\n" +
                        "  AND channelCode=?\n" +
                        "  AND subChannelCode=?\n" +
                        "  AND groupCode=?\n" +
                        "GROUP BY customerCode";
                c = sfaDatabase.getDb().rawQuery(query, new String[]{distrCode, channelCode, subChannelCode, groupCode});
            }

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel retailerCategoryvo = new MTDReportModel();
                    retailerCategoryvo.setCustomerName(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_NAME)).trim());
                    retailerCategoryvo.setCustomerCode(c.getString(c.getColumnIndex(COLUMN_CUSTOMER_CODE)).trim());
                    retailerCategoryList.add(retailerCategoryvo);
                } while (c.moveToNext());
            }

            if (c != null) {
                c.close();
            }

        } catch (Exception e) {
            Log.e(TAG, "loadCustomerWise: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return retailerCategoryList;
    }

    public void insertMtdProdHierProdWiseReportDetail(SFADatabase sfaDatabase, List<MTDReportModel> reportModels) {
        try {
            for (MTDReportModel report : reportModels) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_CMP_CODE, report.getCmpCode());
                values.put(COLUMN_DISTR_CODE, report.getDistrCode());
                values.put(COLUMN_PROD_CODE, report.getProdCode());
                values.put(COLUMN_PROD_NAME, report.getProdName());
                values.put(COLUMN_MONTH_NAME, report.getMonthName());
                values.put(COLUMN_INVOICE_WEEK, report.getInvoiceWeek());
                values.put(COLUMN_INVOICE_DAY, report.getInvoiceDay());
                values.put(COLUMN_TOTAL_INVOICE_QTY, report.getTotalInvoiceQty());
                values.put(COLUMN_TOT_NET_AMT, report.getTotNetAmt());
                values.put(COLUMN_LEVEL_CODE_1, Globals.checkNull(String.valueOf(report.getLevelCode1())));
                values.put(COLUMN_LEVEL_CODE_2, Globals.checkNull(String.valueOf(report.getLevelCode2())));
                values.put(COLUMN_LEVEL_CODE_3, Globals.checkNull(String.valueOf(report.getLevelCode3())));
                values.put(COLUMN_LEVEL_CODE_4, Globals.checkNull(String.valueOf(report.getLevelCode4())));
                values.put(COLUMN_LEVEL_CODE_5, Globals.checkNull(String.valueOf(report.getLevelCode5())));
                values.put(COLUMN_LEVEL_CODE_6, Globals.checkNull(String.valueOf(report.getLevelCode6())));
                values.put(COLUMN_LEVEL_CODE_7, Globals.checkNull(String.valueOf(report.getLevelCode7())));
                values.put(COLUMN_LEVEL_CODE_8, Globals.checkNull(String.valueOf(report.getLevelCode8())));
                values.put(COLUMN_LEVEL_CODE_9, Globals.checkNull(String.valueOf(report.getLevelCode9())));
                values.put(COLUMN_LEVEL_CODE_10, Globals.checkNull(String.valueOf(report.getLevelCode10())));
                values.put(COLUMN_LEVEL_CODE_11, Globals.checkNull(String.valueOf(report.getLevelCode11())));
                values.put(COLUMN_LEVEL_CODE_12, Globals.checkNull(String.valueOf(report.getLevelCode12())));
                values.put(COLUMN_LEVEL_CODE_13, Globals.checkNull(String.valueOf(report.getLevelCode13())));
                values.put(COLUMN_LEVEL_CODE_14, Globals.checkNull(String.valueOf(report.getLevelCode14())));
                values.put(COLUMN_LEVEL_CODE_15, Globals.checkNull(String.valueOf(report.getLevelCode15())));
                values.put(COLUMN_LEVEL_NAME_1, Globals.checkNull(String.valueOf(report.getLevelName1())));
                values.put(COLUMN_LEVEL_NAME_2, Globals.checkNull(String.valueOf(report.getLevelName2())));
                values.put(COLUMN_LEVEL_NAME_3, Globals.checkNull(String.valueOf(report.getLevelName3())));
                values.put(COLUMN_LEVEL_NAME_4, Globals.checkNull(String.valueOf(report.getLevelName4())));
                values.put(COLUMN_LEVEL_NAME_5, Globals.checkNull(String.valueOf(report.getLevelName5())));
                values.put(COLUMN_LEVEL_NAME_6, Globals.checkNull(String.valueOf(report.getLevelName6())));
                values.put(COLUMN_LEVEL_NAME_7, Globals.checkNull(String.valueOf(report.getLevelName7())));
                values.put(COLUMN_LEVEL_NAME_8, Globals.checkNull(String.valueOf(report.getLevelName8())));
                values.put(COLUMN_LEVEL_NAME_9, Globals.checkNull(String.valueOf(report.getLevelName9())));
                values.put(COLUMN_LEVEL_NAME_10, Globals.checkNull(String.valueOf(report.getLevelName10())));
                values.put(COLUMN_LEVEL_NAME_11, Globals.checkNull(String.valueOf(report.getLevelName11())));
                values.put(COLUMN_LEVEL_NAME_12, Globals.checkNull(String.valueOf(report.getLevelName12())));
                values.put(COLUMN_LEVEL_NAME_13, Globals.checkNull(String.valueOf(report.getLevelName13())));
                values.put(COLUMN_LEVEL_NAME_14, Globals.checkNull(String.valueOf(report.getLevelName14())));
                values.put(COLUMN_LEVEL_NAME_15, Globals.checkNull(String.valueOf(report.getLevelName15())));
                sfaDatabase.getDb().insert(TABLE_MTD_SALES_PRODHIER_PRODUCT_WISE_REPORT, null, values);
            }

            sfaDatabase.closeDb();
        } catch (Exception e) {
            Log.e(TAG, "insertMtdProdHierProdWiseReportDetail: " + e.getMessage(), e);
            sfaDatabase.closeDb();
        } finally {
            sfaDatabase.closeDb();
        }
    }

    public List<MTDReportModel> getMtdProdHierProdWiseReport(SFADatabase sfaDatabase) {
        List<MTDReportModel> channelWiseReport = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MTD_SALES_PRODHIER_PRODUCT_WISE_REPORT;
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, null);

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel mtdReportModel = new MTDReportModel();
                    mtdReportModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    mtdReportModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    mtdReportModel.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                    mtdReportModel.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    mtdReportModel.setMonthName(c.getString(c.getColumnIndex(COLUMN_MONTH_NAME)));
                    mtdReportModel.setInvoiceWeek(c.getString(c.getColumnIndex(COLUMN_INVOICE_WEEK)));
                    mtdReportModel.setInvoiceDay(c.getInt(c.getColumnIndex(COLUMN_INVOICE_DAY)));
                    mtdReportModel.setTotNetAmt(c.getDouble(c.getColumnIndex(COLUMN_TOT_NET_AMT)));
                    mtdReportModel.setTotalInvoiceQty(c.getInt(c.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));
                    mtdReportModel.setLevelCode1(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_1)));
                    mtdReportModel.setLevelCode2(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_2)));
                    mtdReportModel.setLevelCode3(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_3)));
                    mtdReportModel.setLevelCode4(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_4)));
                    mtdReportModel.setLevelCode5(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_5)));
                    mtdReportModel.setLevelCode6(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_6)));
                    mtdReportModel.setLevelCode7(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_7)));
                    mtdReportModel.setLevelCode8(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_8)));
                    mtdReportModel.setLevelCode9(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_9)));
                    mtdReportModel.setLevelCode10(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_10)));
                    mtdReportModel.setLevelCode11(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_11)));
                    mtdReportModel.setLevelCode12(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_12)));
                    mtdReportModel.setLevelCode13(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_13)));
                    mtdReportModel.setLevelCode14(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_14)));
                    mtdReportModel.setLevelCode15(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_15)));
                    mtdReportModel.setLevelName1(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_1)));
                    mtdReportModel.setLevelName2(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_2)));
                    mtdReportModel.setLevelName3(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_3)));
                    mtdReportModel.setLevelName4(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_4)));
                    mtdReportModel.setLevelName5(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_5)));
                    mtdReportModel.setLevelName6(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_6)));
                    mtdReportModel.setLevelName7(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_7)));
                    mtdReportModel.setLevelName8(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_8)));
                    mtdReportModel.setLevelName9(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_9)));
                    mtdReportModel.setLevelName10(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_10)));
                    mtdReportModel.setLevelName11(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_11)));
                    mtdReportModel.setLevelName12(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_12)));
                    mtdReportModel.setLevelName13(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_13)));
                    mtdReportModel.setLevelName14(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_14)));
                    mtdReportModel.setLevelName15(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_15)));
                    mtdReportModel.setLevelName15(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_15)));
                    mtdReportModel.setConversionFactor(new OrderBookingHelper().getConversionFactor(sfaDatabase, mtdReportModel.getProdCode(), TABLE_PRODUCT_UOM_MASTER));
                    channelWiseReport.add(mtdReportModel);

                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "getMtdProdHierProdWiseReport: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return channelWiseReport;
    }

    public List<MTDReportModel> getFilteredProductLevels(SFADatabase sfaDatabase, String[] levels, String where) {
        List<MTDReportModel> channelWiseReport = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MTD_SALES_PRODHIER_PRODUCT_WISE_REPORT + " where " + where;
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, levels);

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    MTDReportModel mtdReportModel = new MTDReportModel();
                    mtdReportModel.setCmpCode(c.getString(c.getColumnIndex(COLUMN_CMP_CODE)));
                    mtdReportModel.setDistrCode(c.getString(c.getColumnIndex(COLUMN_DISTR_CODE)));
                    mtdReportModel.setProdCode(c.getString(c.getColumnIndex(COLUMN_PROD_CODE)));
                    mtdReportModel.setProdName(c.getString(c.getColumnIndex(COLUMN_PROD_NAME)));
                    mtdReportModel.setMonthName(c.getString(c.getColumnIndex(COLUMN_MONTH_NAME)));
                    mtdReportModel.setInvoiceWeek(c.getString(c.getColumnIndex(COLUMN_INVOICE_WEEK)));
                    mtdReportModel.setInvoiceDay(c.getInt(c.getColumnIndex(COLUMN_INVOICE_DAY)));
                    mtdReportModel.setTotNetAmt(c.getDouble(c.getColumnIndex(COLUMN_TOT_NET_AMT)));
                    mtdReportModel.setInvoiceCount(c.getInt(c.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));
                    mtdReportModel.setTotalInvoiceQty(c.getInt(c.getColumnIndex(COLUMN_TOTAL_INVOICE_QTY)));
                    mtdReportModel.setLevelCode1(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_1)));
                    mtdReportModel.setLevelCode2(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_2)));
                    mtdReportModel.setLevelCode3(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_3)));
                    mtdReportModel.setLevelCode4(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_4)));
                    mtdReportModel.setLevelCode5(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_5)));
                    mtdReportModel.setLevelCode6(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_6)));
                    mtdReportModel.setLevelCode7(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_7)));
                    mtdReportModel.setLevelCode8(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_8)));
                    mtdReportModel.setLevelCode9(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_9)));
                    mtdReportModel.setLevelCode10(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_10)));
                    mtdReportModel.setLevelCode11(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_11)));
                    mtdReportModel.setLevelCode12(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_12)));
                    mtdReportModel.setLevelCode13(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_13)));
                    mtdReportModel.setLevelCode14(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_14)));
                    mtdReportModel.setLevelCode15(c.getString(c.getColumnIndex(COLUMN_LEVEL_CODE_15)));
                    mtdReportModel.setLevelName1(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_1)));
                    mtdReportModel.setLevelName2(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_2)));
                    mtdReportModel.setLevelName3(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_3)));
                    mtdReportModel.setLevelName4(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_4)));
                    mtdReportModel.setLevelName5(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_5)));
                    mtdReportModel.setLevelName6(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_6)));
                    mtdReportModel.setLevelName7(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_7)));
                    mtdReportModel.setLevelName8(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_8)));
                    mtdReportModel.setLevelName9(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_9)));
                    mtdReportModel.setLevelName10(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_10)));
                    mtdReportModel.setLevelName11(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_11)));
                    mtdReportModel.setLevelName12(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_12)));
                    mtdReportModel.setLevelName13(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_13)));
                    mtdReportModel.setLevelName14(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_14)));
                    mtdReportModel.setLevelName15(c.getString(c.getColumnIndex(COLUMN_LEVEL_NAME_15)));
                    mtdReportModel.setConversionFactor(new OrderBookingHelper().getConversionFactor(sfaDatabase, mtdReportModel.getProdCode(), TABLE_PRODUCT_UOM_MASTER));
                    channelWiseReport.add(mtdReportModel);

                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "getFilteredProductLevels: " + e.getMessage(), e);
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return channelWiseReport;
    }

    public String getChannelName(SFADatabase sfaDatabase, String channelCode) {
        String channelName = "";
        String query = "SELECT * FROM " + TABLE_SALES_CHANNEL_WISE_REPORT_DETAIL + " where " + COLUMN_CHANNEL_CODE + " =?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{channelCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                channelName = c.getString(c.getColumnIndex(COLUMN_CHANNEL_NAME));
            } while (c.moveToNext());

        }
        if (c != null && !c.isClosed()) {
            c.close();
        }

        return channelName;
    }

    public String getBrandName(SFADatabase sfaDatabase, String brandCode, int brandLevel) {
        String channelName = "";
        String query = "SELECT levelName" + (brandLevel + 1) + " FROM " + TABLE_MTD_SALES_PRODHIER_PRODUCT_WISE_REPORT + " where levelCode" + (brandLevel + 1) + " =?";

        Cursor c = sfaDatabase.getDb().rawQuery(query, new String[]{brandCode});
        if (c != null && c.getCount() > 0 && c.moveToFirst()) {
            do {
                channelName = c.getString(c.getColumnIndex("levelName" + (brandLevel + 1)));
            } while (c.moveToNext());

        }
        if (c != null && !c.isClosed()) {
            c.close();
        }

        return channelName;
    }

    public List<DistrReportModel> loadProcessData(SFADatabase sfaDatabase, String cmpCode) {
        List<DistrReportModel> processData = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_REPORT_PROCESS_MAPPING + " WHERE " + COLUMN_CMP_CODE + "=?";
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, new String[]{cmpCode});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    DistrReportModel distrReportModel = new DistrReportModel();
                    distrReportModel.setProcessName(c.getString(c.getColumnIndex(COLUMN_PROCESS_NAME)));
                    distrReportModel.setProcessId(c.getString(c.getColumnIndex(COLUMN_PROCESS_ID)));
                    distrReportModel.setQueryName(c.getString(c.getColumnIndex(COLUMN_QUERY_NAME)));
                    processData.add(distrReportModel);

                } while (c.moveToNext());
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return processData;
    }

    public List<DistrReportModel> loadProcessMenu(SFADatabase sfaDatabase, String cmpCode, String processId) {
        List<DistrReportModel> processMenu = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_REPORT_FIELD_MAPPING + " WHERE " + COLUMN_CMP_CODE + "=? AND " + COLUMN_PROCESS_ID + " =? GROUP BY " + COLUMN_GROUP;
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, new String[]{cmpCode, processId});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    DistrReportModel distrReportModel = new DistrReportModel();
                    distrReportModel.setColumnGroup(c.getString(c.getColumnIndex(COLUMN_GROUP)));
                    processMenu.add(distrReportModel);

                } while (c.moveToNext());
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return processMenu;
    }

    public List<DistrReportModel> loadProcessSubMenu(SFADatabase sfaDatabase, String cmpCode, String processId, String columnGroup) {
        List<DistrReportModel> processMenu = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_REPORT_FIELD_MAPPING + " WHERE " + COLUMN_CMP_CODE + "=? AND " + COLUMN_PROCESS_ID + " =? AND " + COLUMN_GROUP + " =? AND " + COLUMN_ENABLE + " =? ORDER BY " + SEQUENCE;
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, new String[]{cmpCode, processId, columnGroup, "Y"});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    DistrReportModel distrReportModel = new DistrReportModel();
                    distrReportModel.setColumnHeader(c.getString(c.getColumnIndex(COLUMN_HEADER)));
                    distrReportModel.setColumnGroup(c.getString(c.getColumnIndex(COLUMN_GROUP)));
                    distrReportModel.setColumnField(c.getString(c.getColumnIndex(COLUMN_FIELD)));
                    distrReportModel.setColumnType(c.getString(c.getColumnIndex(COLUMN_TYPE_)));
                    distrReportModel.setTableName(c.getString(c.getColumnIndex(COLUMN_TABLE_NAME)));
                    distrReportModel.setColumnFunction(c.getString(c.getColumnIndex(COLUMN_FUNCTION)));
                    processMenu.add(distrReportModel);

                } while (c.moveToNext());
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return processMenu;
    }

    public List<DistrReportModel> getHashMapKey(SFADatabase sfaDatabase) {
        List<DistrReportModel> keyValue = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_REPORT_FIELD_MAPPING + " WHERE " + COLUMN_GROUP + " !=? GROUP BY " + COLUMN_GROUP;
        Cursor c = null;
        try {
            c = sfaDatabase.getReadableDatabase().rawQuery(query, new String[]{"Measure"});

            if (c != null && c.getCount() > 0 && c.moveToFirst()) {
                do {
                    DistrReportModel distrReportModel = new DistrReportModel();
                    distrReportModel.setColumnGroup(c.getString(c.getColumnIndex(COLUMN_GROUP)));
                    keyValue.add(distrReportModel);

                } while (c.moveToNext());
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }

        return keyValue;
    }
}
