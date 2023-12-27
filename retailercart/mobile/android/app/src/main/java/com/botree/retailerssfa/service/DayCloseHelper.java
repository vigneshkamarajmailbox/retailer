/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.botree.retailerssfa.async.TaskCallbacks;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.db.query.IDBColumns;
import com.botree.retailerssfa.models.AddressModel;
import com.botree.retailerssfa.models.BillingInfoVO;
import com.botree.retailerssfa.models.CodeGeneratorModel;
import com.botree.retailerssfa.models.CollectionVO;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.FeedbackModel;
import com.botree.retailerssfa.models.LogisticStockModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.PendingBillCollection;
import com.botree.retailerssfa.models.PendingCollection;
import com.botree.retailerssfa.models.PurchaseInvoiceModel;
import com.botree.retailerssfa.models.RetailerDetailVO;
import com.botree.retailerssfa.models.RetailerVO;
import com.botree.retailerssfa.models.RouteModel;
import com.botree.retailerssfa.models.SalesDBCRNoteModel;
import com.botree.retailerssfa.models.SalesReturnVO;
import com.botree.retailerssfa.models.SalesmanMasterModel;
import com.botree.retailerssfa.models.SalesmanRouteCoverageModel;
import com.botree.retailerssfa.models.SchemeDistrBudgetModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.models.StockOnHandModel;
import com.botree.retailerssfa.models.WDInfrastructure;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.LoggerUtil;
import com.botree.retailerssfa.util.OrderSupportUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.db.query.IDBColumns.*;
import static com.botree.retailerssfa.scheme.SchemeEngin.SCHEME_PAYOUT_DISCOUNT;
import static com.botree.retailerssfa.scheme.SchemeEngin.SCHEME_PAYOUT_FREE_PROD;
import static com.botree.retailerssfa.service.DayStartHelper.isJsonNotNull;
import static com.botree.retailerssfa.service.JSONConstants.*;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DAY_START_DATE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LOGIN_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;
import static com.itextpdf.text.Chunk.IMAGE;

public class DayCloseHelper {

    private static final String TAG = DayCloseHelper.class.getSimpleName();
    private final Context context;
    private TaskCallbacks taskCallbacks;
    private List<RetailerDetailVO> retailerDetailVOs;

    public DayCloseHelper(Context context) {
this.context = context;
    }

    public DayCloseHelper(Context context, TaskCallbacks taskCallbacks) {

        this.context = context;
        this.taskCallbacks = taskCallbacks;
        retailerDetailVOs = new ArrayList<>();
    }

    public void updateTaskCallback(TaskCallbacks taskCallbacks) {
        this.taskCallbacks = taskCallbacks;
    }

    private void updateProgress(int progress) {
        if (taskCallbacks != null) {
            taskCallbacks.onProgressUpdate(progress);
        }
    }

    /**
     * method is used for preparing the upload json datas
     *
     * @return JsonObject for upload
     */
    public JSONObject getUploadJson(String userCode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TAG_ORDER_BOOKING_HEADER_LIST, checkJSONArrayNotNull(addOrderBookingHeaderList()));
            jsonObject.put(TAG_FEEDBACK_LIST, checkJSONArrayNotNull(addRetailerFeedbackList()));
            jsonObject.put(TAG_LOCATION_UPDATE, checkJSONArrayNotNull(addLocationUpdateList()));
//            jsonObject.put(TAG_DISTRIBUTOR, checkJSONObjectNotNull(addDistributorDetails(userCode)));
//            jsonObject.put(TAG_ROUTE, checkJSONArrayNotNull(addRouteDetails(userCode)));
//
            jsonObject.put(TAG_CODE_GENERATOR, checkJSONArrayNotNull(getAllGeneratedCodesForUpload(userCode, TABLE_CODE_GENERATOR)));
//            jsonObject.put(TAG_CUSTOMER, checkJSONArrayNotNull(addRetailerMasterInfo()));
//            jsonObject.put(TAG_CUSTOMER_BANK, checkJSONArrayNotNull(addRetailerBank()));
//            jsonObject.put(TAG_CUSTOMER_ROUTE, checkJSONArrayNotNull(addRetailerRoute()));
//            jsonObject.put(TAG_CUSTOMER_SHIP_ADDR, checkJSONArrayNotNull(addRetailerShipAddress()));
//
//            jsonObject.put(TAG_DISTR_WD_STORAGE, checkJSONObjectNotNull(addDistrWDStorage()));
//            jsonObject.put(TAG_DISTR_WD_FREEZER, checkJSONArrayNotNull(addDistrWDFreezer()));
//
//            jsonObject.put(TAG_SALESMAN, checkJSONArrayNotNull(addSalesmanDetails(userCode)));
//            jsonObject.put(TAG_SALESMAN_KYC, checkJSONArrayNotNull(addSalesmanKYC(userCode)));
//            jsonObject.put(TAG_SALESMAN_LOB_MAPPING, checkJSONArrayNotNull(addSalesmanLobMapping(userCode)));
//            jsonObject.put(TAG_SALESMAN_ROUTE_MAPPING, checkJSONArrayNotNull(addSalesmanRouteMapping(userCode)));
//
//            jsonObject.put(TAG_PURCHASE_ORDER_LIST, checkJSONArrayNotNull(addPurchaseOrderBookingDetialsList()));
//            jsonObject.put(TAG_PURCHASE_INVOICE, checkJSONArrayNotNull(addPendingPurchaseDetails()));
//            jsonObject.put(TAG_LOGISTIC_MATERIAL_STOCK, checkJSONArrayNotNull(addLogisticMaterialStock()));
//
//            jsonObject.put(TAG_ORDER_BOOKING_HEADER_LIST, checkJSONArrayNotNull(addPendingInvoiceDetails()));
//            jsonObject.put(TAG_SALESMAN_ROUTE_COVERAGE, checkJSONArrayNotNull(addSalesmanRouteCoverages()));
//            jsonObject.put(TAG_STOCK_ON_HAND, checkJSONArrayNotNull(addAvailableStockOnHand()));
//
//            jsonObject.put(TAG_COLLECTION, checkJSONArrayNotNull(addCollection()));
//            jsonObject.put(TAG_BILL_ADJUSTMENT_LIST, checkJSONArrayNotNull(addBillAdjustmentList()));
//            jsonObject.put(TAG_UPDATE_PENDING_BILL, checkJSONArrayNotNull(addPendingBills()));
//            jsonObject.put(TAG_STOCK_ADJUSTMENT_HEADER, checkJSONArrayNotNull(addStockAdjustmentList()));
//            jsonObject.put(TAG_SALES_RETURN_HEADER, checkJSONArrayNotNull(addSalesReturnList()));
//            jsonObject.put(TAG_SCHEME_DISTR_BUDGET_LIST, checkJSONArrayNotNull(addSchemeDistrBudgetDetails()));
//            jsonObject.put(TAG_INVOICE_CR_DB_ADJUSTMENT, checkJSONArrayNotNull(addInvoiceCRDBAdjustment()));
//            jsonObject.put(TAG_SALES_DB_CR_NOTE, checkJSONArrayNotNull(addSalesDBCRNoteDetails()));
//            jsonObject.put(TAG_CUSTOMER_APPROVAL_DATA, checkJSONArrayNotNull(addRetailerApprovalData()));
////            jsonObject.put(TAG_TEMP_ROUTE, checkJSONArrayNotNull(addTempRouteDetails(userCode)));
////            jsonObject.put(TAG_TEMP_CUSTOMER, checkJSONArrayNotNull(addTempRetailerMasterInfo()));
////            jsonObject.put(TAG_TEMP_CUSTOMER_ROUTE, checkJSONArrayNotNull(addTempRetailerRoute()));
////            jsonObject.put(TAG_TEMP_CUSTOMER_SHIP_ADDR, checkJSONArrayNotNull(addTempRetailerShipAddress()));
//            jsonObject.put(TAG_BATCH_TRANSFER_DATA, checkJSONArrayNotNull(addBatchTransferData()));
//            jsonObject.put(TAG_CODE_GENERATOR_HISTORY_LIST, checkJSONArrayNotNull(getAllGeneratedCodesForUpload(userCode, TABLE_CODE_GENERATOR_HISTORY)));
        } catch (Exception e) {
            Log.e(TAG, "UPLOAD EXp : " + e);
        }
        logLargeString(jsonObject.toString());
        return jsonObject;
    }

    public void logLargeString(String str) {
        if(str.length() > 3000) {
            Log.i(TAG, str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.i(TAG, str); // continuation
        }
    }

    private JSONArray addRetailerFeedbackList() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        JSONArray jsonArray = new JSONArray();
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        String salesmanCode = preferences.readString(PREF_SALESMANCODE);

        List<FeedbackModel> feedbackList = database.getRetailerFeedbackList(cmpCode, distCode, salesmanCode);

        for (FeedbackModel feedbackModel : feedbackList) {
            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, feedbackModel.getCmpCode());
            jObj.put(TAG_DISTR_CODE, feedbackModel.getDistrCode());
            jObj.put(TAG_CUSTOMER_CODE, feedbackModel.getCustomerCode());
            jObj.put(TAG_FEEDBACK_NO, feedbackModel.getFeedbackNo());
            jObj.put(TAG_FEEDBACK_TYPE, feedbackModel.getFeedbackType());
            jObj.put(TAG_FEEDBACK_MESSAGE, feedbackModel.getMessage());
            jObj.put(TAG_FEEDBACK_IMAGE, feedbackModel.getFeedbackImage());
            jObj.put(TAG_FEEDBACK_DATE, feedbackModel.getFeedbackDate());
            jObj.put(TAG_MOD_DT, System.currentTimeMillis());
            jObj.put(UPLOAD_FLAG, "N");
            jsonArray.put(jObj);

        }
        return jsonArray;
    }

    private JSONArray addLocationUpdateList() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        JSONArray jsonArray = new JSONArray();
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        String salesmanCode = preferences.readString(PREF_SALESMANCODE);

        List<RetailerVO> feedbackList = database.getLocationUpdateList(cmpCode, distCode, salesmanCode);

        for (RetailerVO feedbackModel : feedbackList) {
            JSONObject jObj = new JSONObject();
            jObj.put(TAG_LOGIN_CODE, feedbackModel.getCustomerCode());
            jObj.put(UPDATE_DT, feedbackModel.getCoverageDate());
            jObj.put(TAG_LATITUDE, feedbackModel.getLatitude());
            jObj.put(TAG_LONGITUDE, feedbackModel.getLongitude());
            jObj.put(COLUMN_IMAGE, feedbackModel.getAccountNo());
            jObj.put(IMAGE_PATH, "");
            jObj.put(COLUMN_POSTAL_CODE, feedbackModel.getPostalCode());
            jObj.put(COLUMN_UPLOAD_TO, feedbackModel.getAccountType());
            jObj.put(TAG_MOD_DT, System.currentTimeMillis());
            jObj.put(UPLOAD_FLAG, "N");
            jsonArray.put(jObj);

        }
        return jsonArray;
    }


    private JSONArray addOrderBookingHeaderList() throws JSONException {

        SFADatabase database = SFADatabase.getInstance(context);

        SFASharedPref preferences = SFASharedPref.getOurInstance();
        List<RetailerVO> retailerList = database.getOrderBookingRetailerDetails("", "",
                preferences.readString(PREF_DAY_START_DATE), IDBColumns.TABLE_ORDER_BOOKING);

        JSONArray jsonArray = new JSONArray();
        for (RetailerVO retailerVO : retailerList) {
            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, retailerVO.getCmpCode());
            jObj.put(TAG_DISTR_CODE, retailerVO.getDistrCode());
            jObj.put(TAG_ORDER_NO, retailerVO.getOrderNo());
            jObj.put("customerRefNo", retailerVO.getCustomerRefNo());
            jObj.put(TAG_CUSTOMER_CODE, retailerVO.getCustomerCode());
            jObj.put(TAG_CUSTOMER_SHIP_CODE, retailerVO.getCustomerShipCode());
            jObj.put(TAG_SYNC_ORDER_DATE, retailerVO.getOrderDt());
            jObj.put(TAG_REMARKS, retailerVO.getOrderBookingVO().getRemarks());
            jObj.put(TAG_LATITUDE, retailerVO.getLatitude());
            jObj.put(TAG_LONGITUDE, retailerVO.getLongitude());
            jObj.put(TAG_START_TIME, retailerVO.getOrderBookingVO().getStartTime());
            jObj.put(TAG_END_TIME, retailerVO.getOrderBookingVO().getEndTime());
            jObj.put(TAG_ORDER_STATUS, "P");
            double discountValue = (retailerVO.getOrderBookingVO().getOrderValue().doubleValue() - retailerVO.getOrderBookingVO().getPrimDiscOrderValue().doubleValue()) + retailerVO.getOrderBookingVO().getDicountAmt().doubleValue();
            if (discountValue > 0) {
                jObj.put(TAG_TOTAL_DISCOUNT, discountValue);
            } else {
                jObj.put(TAG_TOTAL_DISCOUNT, "0");
            }

            jObj.put(TAG_TOTAL_DISCOUNT, discountValue);
            jObj.put(TAG_TOTAL_TAX, retailerVO.getOrderBookingVO().getTax());

            jObj.put(TAG_MOD_DT, System.currentTimeMillis());
            jObj.put(UPLOAD_FLAG, "N");

            jObj.put(TAG_TOTAL_GROSS_VALUE, retailerVO.getOrderBookingVO().getlGrossAmt());
            jObj.put(TAG_TOTAL_ORDER_VALUE, retailerVO.getOrderBookingVO().getTotalAmount());

            jObj.put(TAG_ORDER_BOOKING_DETAILS_LIST, addOrderBookingDetialsList(retailerVO.getCmpCode(), retailerVO.getDistrCode(), retailerVO.getOrderNo()));
            jObj.put(TAG_ORDER_BOOKING_SCHEME_PROD_LIST, addInvoiceSChemeProdRuleList(retailerVO.getCmpCode(), retailerVO.getDistrCode(), retailerVO.getCustomerCode(), retailerVO.getOrderNo()));
            jsonArray.put(jObj);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo("", retailerVO.getCmpCode(), retailerVO.getOrderNo(), retailerVO.getOrderNo());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return jsonArray;
    }

    private JSONArray addOrderBookingDetialsList(String routeCode, String customerCode, String orderNo) throws JSONException {

        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();

        List<OrderBookingVO> orderBookingList = database.getOrderBookingLineItem
                (routeCode, customerCode, orderNo,"");
        JSONArray jALineItem = new JSONArray();
        for (OrderBookingVO orderBooking : orderBookingList) {
            JSONObject jObjLineItem = new JSONObject();
            jObjLineItem.put(TAG_CMP_CODE, orderBooking.getCmpCode());
            jObjLineItem.put(TAG_DISTR_CODE, orderBooking.getDistrCode());
            jObjLineItem.put(TAG_ORDER_NO, orderBooking.getOrderNo());
            jObjLineItem.put(TAG_PROD_BATCH_CODE, orderBooking.getProdBatchCode());
            jObjLineItem.put(TAG_PROD_CODE, orderBooking.getProdCode());
            jObjLineItem.put(TAG_PROD_NAME, orderBooking.getProdName());
            jObjLineItem.put(TAG_INPUT_STR, OrderSupportUtil.getInstance().getStringQty(orderBooking.getQuantity()) + orderBooking.getUomId());
            jObjLineItem.put(TAG_ORDER_QTY, orderBooking.getTotQty());
            jObjLineItem.put(TAG_UOM_CODE, orderBooking.getDefaultUomid());
            jObjLineItem.put(COLUMN_SERVICED_QTY, 0);
            jObjLineItem.put(TAG_SELL_RATE, orderBooking.getSellPrice());
            jObjLineItem.put(COLUMN_ACTUAL_SELL_RATE, orderBooking.getActualSellRate());

            jObjLineItem.put(TAG_ORDER_VALUE, orderBooking.getTotalAmount());
            jObjLineItem.put(COLUMN_GROSS_VALUE, orderBooking.getlGrossAmt());

            jObjLineItem.put(JSONConstants.TAG_TAX_CODE, orderBooking.getTaxCode());
            jObjLineItem.put(TAG_TAX_AMT, orderBooking.getTax());
//            double discountValue = (orderBooking.getOrderValue().doubleValue() - orderBooking.getPrimDiscOrderValue().doubleValue()) + orderBooking.getDicountAmt().doubleValue();

//            if(discountValue>0){
                jObjLineItem.put("schAmt", orderBooking.getSchemeAmount());
//            }else {
//                jObjLineItem.put("schAmt", "0");
//            }

            jObjLineItem.put(TAG_CGST_PERC, orderBooking.getCgstperc());
            jObjLineItem.put(TAG_CGST_VALUE, orderBooking.getCgstvalue());
            jObjLineItem.put(TAG_SGST_PERC, orderBooking.getSgstPerc());
            jObjLineItem.put(TAG_SGST_VALUE, orderBooking.getSgstValue());
            jObjLineItem.put(TAG_UTGST_PERC, orderBooking.getUtgstPerc());
            jObjLineItem.put(TAG_UTGST_VALUE, orderBooking.getUtgstValue());
            jObjLineItem.put(TAG_IGST_PERC, orderBooking.getIgstPerc());
            jObjLineItem.put(TAG_IGST_VALUE, orderBooking.getIgstvalue());
            jObjLineItem.put(TAG_PROD_TYPE, orderBooking.getProdType());
            jObjLineItem.put(TAG_ORDER_VALUE, orderBooking.getOrderValue());
            jObjLineItem.put(COLUMN_SCH_AMT, orderBooking.getDicountAmt());
            jObjLineItem.put(TAG_MOD_DT, System.currentTimeMillis());
            jObjLineItem.put(UPLOAD_FLAG, "N");
            jALineItem.put(jObjLineItem);
        }
        return jALineItem;
    }

    private JSONArray addBatchTransferData() throws JSONException {

        SFADatabase database = SFADatabase.getInstance(context);
        JSONArray jsonArray = new JSONArray();
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<OrderBookingVO> voList = database.getBatchTransferData(cmpCode, distCode);

        for (OrderBookingVO bookingVO : voList) {
            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, bookingVO.getCmpCode());
            jObj.put(TAG_DISTR_CODE, bookingVO.getDistrCode());
//            jObj.put(TAG_GODOWN_CODE, bookingVO.getGoDownCode());
//            jObj.put(TAG_BATCH_TRANSFER_NO, bookingVO.getBatchTransferNo());
//            jObj.put(TAG_BATCH_TRANSFER_DT, bookingVO.getOrderDate());
//            jObj.put(TAG_STOCK_TYPE, bookingVO.getStockType());
//            jObj.put(TAG_PROD_CODE, bookingVO.getProdCode());
//            jObj.put(TAG_FROM_BATCH_CODE, bookingVO.getFromProdBatchCode());
//            jObj.put(TAG_TO_BATCH_CODE, bookingVO.getToProdBatchCode());
//            jObj.put(TAG_TRANSFER_QTY, bookingVO.getTotInvoiceQty());
//            jObj.put(TAG_INPUT_STR, bookingVO.getInputStr());
//            jObj.put(TAG_REASON_CODE, bookingVO.getReasonCode());
//            jObj.put(TAG_REASON_NAME, bookingVO.getReasonName());
//            jObj.put(TAG_DOC_REFNO, bookingVO.getDocRefNo());
            jObj.put(TAG_REMARKS, bookingVO.getRemarks());
            jObj.put(TAG_UPLOAD_FLAG, "N");
            jObj.put(TAG_MOD_DT, System.currentTimeMillis());

            jsonArray.put(jObj);

//            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, cmpCode, "", bookingVO.getBatchTransferNo());
//            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    public JSONObject getCodificationUploadJson(String userCode) {
        JSONObject jsonObject = new JSONObject();
        try {
            retailerDetailVOs = new ArrayList<>();
            jsonObject.put(TAG_TEMP_ROUTE, checkJSONArrayNotNull(addTempRouteDetails(userCode)));
            jsonObject.put(TAG_TEMP_CUSTOMER, checkJSONArrayNotNull(addTempRetailerMasterInfo()));
            jsonObject.put(TAG_TEMP_CUSTOMER_ROUTE, checkJSONArrayNotNull(addTempRetailerRoute()));
            jsonObject.put(TAG_TEMP_CUSTOMER_SHIP_ADDR, checkJSONArrayNotNull(addTempRetailerShipAddress()));
            jsonObject.put(TAG_ROUTE, checkJSONArrayNotNull(addRouteDetails(userCode)));
            jsonObject.put(TAG_CODE_GENERATOR, checkJSONArrayNotNull(getAllGeneratedCodesForUpload(userCode, TABLE_CODE_GENERATOR)));
            jsonObject.put(TAG_CUSTOMER, checkJSONArrayNotNull(addRetailerMasterInfo()));
            jsonObject.put(TAG_CUSTOMER_ROUTE, checkJSONArrayNotNull(addRetailerRoute()));
            jsonObject.put(TAG_CUSTOMER_SHIP_ADDR, checkJSONArrayNotNull(addRetailerShipAddress()));
        }catch (Exception e) {
            Log.e(TAG, "UPLOAD EXp : " + e);
        }
        return jsonObject;
    }

    public JSONObject performLogoutReqjson(String userCode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TAG_TEMP_ROUTE, checkJSONArrayNotNull(addTempRouteDetails(userCode)));
        }catch (Exception e) {
            Log.e(TAG, "UPLOAD EXp : " + e);
        }
        return jsonObject;
    }

    private JSONArray addRetailerApprovalData() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<RetailerVO> retailerList = database.getRetailerApprovalStatusList(cmpCode, distCode);

        JSONArray jsonArray = new JSONArray();

        for (RetailerVO retailerVO : retailerList) {
            JSONObject jObjLineItem = new JSONObject();
            jObjLineItem.put(TAG_CMP_CODE, cmpCode);
            jObjLineItem.put(TAG_DISTR_CODE, distCode);
            jObjLineItem.put(TAG_CUSTOMER_CODE, retailerVO.getCustomerCode());
            jObjLineItem.put(TAG_APPROVAL_STATUS, retailerVO.getApprovalStatus());
            jObjLineItem.put(TAG_CUSTOMER_DATA, retailerVO.getCustomerData());
            jObjLineItem.put(TAG_UPLOAD_FLAG, "N");
            jObjLineItem.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jObjLineItem);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", cmpCode, retailerVO.getCustomerCode());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addSalesDBCRNoteDetails() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        String distrCode = preferences.readString(PREF_DISTRCODE);

        List<SalesDBCRNoteModel> infoVOList = database.getSalesDBCRNoteDetails(cmpCode, distrCode);

        JSONArray jsonArray = new JSONArray();

        for (SalesDBCRNoteModel retailerVO : infoVOList) {

            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, retailerVO.getCmpCode());
            jObj.put(TAG_DISTR_CODE, retailerVO.getDistrCode());
            jObj.put(TAG_CUSTOMER_CODE, retailerVO.getCustomerCode());
            jObj.put(TAG_SALES_DB_CRREFNO, retailerVO.getSalesDBCRRefNo());
            jObj.put(TAG_DB_CRTYPE, retailerVO.getDbCRType());
            jObj.put(TAG_DB_CRDT, retailerVO.getDbCRDt());
            jObj.put(TAG_DB_CRREASON, retailerVO.getDbCRReason());
            jObj.put(TAG_DB_CRBUDGET, retailerVO.getDbCRBudget());
            jObj.put(TAG_DB_CRTAXAMT, retailerVO.getDbCRTaxAmt());
            jObj.put(TAG_DB_CRAMT, retailerVO.getDbCRAmt());
            jObj.put(TAG_DB_CRBAL, retailerVO.getDbCRBal());
            jObj.put(TAG_IS_SETTLED, retailerVO.getIsSettled());
            jObj.put(TAG_REMARKS, retailerVO.getRemarks());
            jObj.put(TAG_IS_ACTIVE, retailerVO.getIsActive());
            jObj.put(TAG_IS_CLAIMABLE, retailerVO.getIsClaimable());
            jObj.put(TAG_REFERENCE_TYPE, retailerVO.getReferenceType());
            jObj.put(TAG_REFERENCE_NO, retailerVO.getReferenceNo());
            jObj.put(TAG_MOD_DT, retailerVO.getModDt());
            jObj.put(TAG_UPLOAD_FLAG, "N");

            jsonArray.put(jObj);

            retailerDetailVOs.add(addBasicDetToRetDetVo(retailerVO.getCmpCode(), "", retailerVO.getCustomerCode(), retailerVO.getSalesDBCRRefNo()));
        }

        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addSalesReturnList() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<SalesReturnVO> salesReturnVOList = database.getSalesReturnHeaderDetail(cmpCode, distCode);

        JSONArray jsonArray = new JSONArray();

        for (SalesReturnVO salesReturnVO : salesReturnVOList) {

            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, cmpCode);
            jObj.put(TAG_DISTR_CODE, salesReturnVO.getDistrCode());
            jObj.put(TAG_SALES_RETURNNO, salesReturnVO.getSalesreturnNo());
            jObj.put(TAG_SALESMAN_CODE, salesReturnVO.getSalesmanCode());
            jObj.put(TAG_ROUTE_CODE, salesReturnVO.getRouteCode());
            jObj.put(TAG_CUSTOMER_CODE, salesReturnVO.getRetlrCode());
            jObj.put(TAG_SALES_RETURN_DATE, salesReturnVO.getReturnDate());
            jObj.put(TAG_INVOICE_NUMBER, salesReturnVO.getInvoiceNo());
            jObj.put(TAG_RETURN_TYPE, salesReturnVO.getReturnType());
            jObj.put(TAG_REMARKS, salesReturnVO.getRemarks());
            jObj.put(TAG_MOD_DT, System.currentTimeMillis());
            jObj.put(TAG_UPLOAD_FLAG, "N");

            jObj.put(TAG_SALES_RETURN_DETAILS, addSalesReturnDetailList(cmpCode, salesReturnVO.getDistrCode(), salesReturnVO.getSalesreturnNo()));

            jsonArray.put(jObj);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(cmpCode, distCode, "", salesReturnVO.getSalesreturnNo());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addSalesReturnDetailList(String cmpCode, String distrCode, String salesreturnNo) throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        List<SalesReturnVO> salesReturnVOList = database.getSalesReturnDetail(distrCode, salesreturnNo);

        JSONArray jsonArray = new JSONArray();
        for (SalesReturnVO salesReturnVO : salesReturnVOList) {

            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, cmpCode);
            jObj.put(TAG_DISTR_CODE, salesReturnVO.getDistrCode());
            jObj.put(TAG_SALES_RETURNNO, salesReturnVO.getSalesreturnNo());
            jObj.put(TAG_INVOICE_NO, salesReturnVO.getInvoiceNo());
            jObj.put(TAG_PROD_CODE, salesReturnVO.getProdCode());
            jObj.put(TAG_PROD_NAME, salesReturnVO.getProdName());
            jObj.put(TAG_PROD_BATCH_CODE, salesReturnVO.getProdBatchCode());
            jObj.put(TAG_BATCH_DETAIL, salesReturnVO.getBatchDetail());
            jObj.put(TAG_RETURN_QTY, salesReturnVO.getReturnQty());
            jObj.put(TAG_STOCK_TYPE, salesReturnVO.getStockType());
            jObj.put(TAG_REASON_CODE, salesReturnVO.getReasonCode());
            jObj.put(TAG_REASON_NAME, salesReturnVO.getReasonName());
            jObj.put(TAG_MRP, salesReturnVO.getMrp());
            jObj.put(TAG_SELL_RATE, salesReturnVO.getSellPrice());
            jObj.put(TAG_RETURN_AMOUNT, salesReturnVO.getReturnAmt());
            jObj.put(TAG_IMAGE, salesReturnVO.getReturnImage());
            jObj.put(TAG_MOD_DT, System.currentTimeMillis());
            jObj.put(TAG_UPLOAD_FLAG, "N");

            jsonArray.put(jObj);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addStockAdjustmentList() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<OrderBookingVO> orderBookingVOList = database.getStockAdjustmentHeaderDetail(cmpCode, distCode);

        JSONArray jsonArray = new JSONArray();

        for (OrderBookingVO orderBookingVO : orderBookingVOList) {

            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, cmpCode);
            jObj.put(TAG_DISTR_CODE, orderBookingVO.getDistrCode());
//            jObj.put(TAG_GODOWN_CODE, orderBookingVO.getGoDownCode());
//            jObj.put(TAG_STOCK_ADJUSTNO, orderBookingVO.getStockAdjustNo());
//            jObj.put(TAG_TRANS_TYPE, orderBookingVO.getTransType());
//            jObj.put(TAG_ADJUST_DT, orderBookingVO.getAdjustDt());
//            jObj.put(TAG_DOC_REFNO, orderBookingVO.getDocRefNo());
//            jObj.put(TAG_REMARKS, orderBookingVO.getRemarks());
//            jObj.put(TAG_ISMANUAL, orderBookingVO.getIsManual());
//            jObj.put(TAG_SOURCE, orderBookingVO.getSource());
            jObj.put(TAG_MOD_DT, System.currentTimeMillis());
            jObj.put(TAG_UPLOAD_FLAG, "N");

//            jObj.put(TAG_STOCK_ADJUSTMENT_DETAILS, addStockAdjustmentDetailList(cmpCode, orderBookingVO.getDistrCode(), orderBookingVO.getGoDownCode(), orderBookingVO.getStockAdjustNo()));

            jsonArray.put(jObj);

//            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(cmpCode, distCode, orderBookingVO.getGoDownCode(), orderBookingVO.getStockAdjustNo());
//            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addStockAdjustmentDetailList(String cmpCode, String distrCode, String goDownCode, String stockAdjustNo) throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        List<OrderBookingVO> orderBookingVOList = database.getStockAdjustmentDetail(distrCode, goDownCode, stockAdjustNo);

        JSONArray jsonArray = new JSONArray();
        for (OrderBookingVO orderBookingVO : orderBookingVOList) {

            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, cmpCode);
            jObj.put(TAG_DISTR_CODE, orderBookingVO.getDistrCode());
//            jObj.put(TAG_GODOWN_CODE, orderBookingVO.getGoDownCode());
//            jObj.put(TAG_STOCK_ADJUSTNO, orderBookingVO.getStockAdjustNo());
//            jObj.put(TAG_PROD_CODE, orderBookingVO.getProdCode());
//            jObj.put(TAG_PROD_BATCH_CODE, orderBookingVO.getProdBatchCode());
//            jObj.put(TAG_FROM_STOCK_TYPE, orderBookingVO.getFromStockType());
//            jObj.put(TAG_TO_STOCK_TYPE, orderBookingVO.getToStockType());
//            jObj.put(TAG_ADJUST_QTY, orderBookingVO.getAdjustQty());
//            jObj.put(TAG_REASON_CODE, orderBookingVO.getReasonCode());
//            jObj.put(TAG_REASON_NAME, orderBookingVO.getReasonName());
//            jObj.put(TAG_INPUT_STR, orderBookingVO.getInputStr());
//            jObj.put(TAG_PURCHASE_RATE, orderBookingVO.getPurchaseRate());
//            jObj.put(TAG_AMOUNT, orderBookingVO.getAmount());
//            jObj.put(TAG_MOD_DT, System.currentTimeMillis());
            jObj.put(TAG_UPLOAD_FLAG, "N");

            jsonArray.put(jObj);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addPendingBills() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        JSONArray jsonArray = new JSONArray();
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<PendingBillCollection> billCollections = database.getPendingBillCollectionList(cmpCode, distCode);

        for (PendingBillCollection collection : billCollections) {
            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, cmpCode);
            jObj.put(TAG_DISTR_CODE, distCode);
            jObj.put(TAG_BALANCE_OS, collection.getBalanceOs());
            jObj.put(TAG_INVOICE_NO, collection.getInvoiceNo());

            jsonArray.put(jObj);
//            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", "", collection.getInvoiceNo());
//            retailerDetailVOs.add(retailerDetailVO);
        }

        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addCollection() throws JSONException {

        SFADatabase database = SFADatabase.getInstance(context);
        JSONArray jsonArray = new JSONArray();
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<CollectionVO> collectionList = database.getCollectionList(cmpCode, distCode);

        for (CollectionVO collectionVO : collectionList) {
            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, cmpCode);
            jObj.put(TAG_DISTR_CODE, distCode);
            jObj.put(TAG_COLLECTION_NO, collectionVO.getCollectionNo());
            jObj.put(TAG_COLLECTION_MODE, collectionVO.getMode());
            jObj.put(TAG_SALESMAN_CODE, collectionVO.getSalesmanCode());
            jObj.put(TAG_ROUTE_CODE, collectionVO.getRouteCode());
            jObj.put(TAG_CUSTOMER_CODE, collectionVO.getRetailerCode());
            jObj.put(TAG_COLLECTION_DT, collectionVO.getCollectionDate());
            jObj.put(TAG_COLLECTON_AMOUNT, collectionVO.getAmount());
            jObj.put(TAG_BILL_ADJ_AMT, collectionVO.getBillAdjAmt());
            jObj.put(TAG_CR_DB_ADJ_AMT, collectionVO.getCrDbAdjAmt());
            jObj.put(TAG_CANCELLED_STATUS, collectionVO.getCancelledStatus());
            jObj.put(TAG_CHEQUE_INSTRUMENT_NO, collectionVO.getCheque());
            jObj.put(TAG_CHEQUE_INSTRUMENT_DATE, collectionVO.getDate());
            jObj.put(TAG_BANK_CODE, collectionVO.getBankCode());
            jObj.put(TAG_RTR_BANK_NAME, collectionVO.getBankName());
            jObj.put(TAG_BANK_BRANCH_CODE, collectionVO.getBankBranchCode());
            jObj.put(TAG_DISTR_BANK_CODE, collectionVO.getDistrBankCode());
            jObj.put(TAG_DISTR_BANK_NAME, collectionVO.getDistrBankName());
            jObj.put(TAG_DISTR_BANK_BR_CODE, collectionVO.getDistrBankBrCode());
            jObj.put(TAG_COLLECTION_TYPE, collectionVO.getCollectionType());
            jObj.put(TAG_REMARKS, collectionVO.getRemarks());
            jObj.put(TAG_UPLOAD_FLAG, "N");
            jObj.put(TAG_MOD_DT, System.currentTimeMillis());

            jsonArray.put(jObj);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", "", collectionVO.getCollectionNo());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addBillAdjustmentList() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        List<PendingCollection> collections = database.getBillAdjustmentDetail(cmpCode, distCode);

        JSONArray jsonArray = new JSONArray();

        for (PendingCollection collection : collections) {
            JSONObject jObjLineItem = new JSONObject();
            jObjLineItem.put(TAG_CMP_CODE, cmpCode);
            jObjLineItem.put(TAG_DISTR_CODE, distCode);
            jObjLineItem.put(TAG_COLLECTION_NO, collection.getCollectionNo());
            jObjLineItem.put(TAG_COLLECTION_MODE, collection.getCollectionMode());
            jObjLineItem.put(TAG_ADJUSTMENT_MODE, collection.getAdjustmentMode());
            jObjLineItem.put(TAG_ADJUST_REF_N0, collection.getBillNo());
            jObjLineItem.put(TAG_ADJUSTMENT_DT, collection.getAdjustDt());
            jObjLineItem.put(TAG_ADJUSTMENT_AMOUNT, collection.getAdjustAmount());
            jObjLineItem.put(TAG_ADJUSTMENT_BALANCE, collection.getAdjustBalance());
            jObjLineItem.put(TAG_UPLOAD_FLAG, "N");
            jObjLineItem.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jObjLineItem);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, cmpCode, "", collection.getCollectionNo());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addPendingInvoiceDetails() throws JSONException {

        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<BillingInfoVO> infoVOList = new ArrayList<>();
//        List<BillingInfoVO> infoVOList = database.getInvoiceHeaderDetail(cmpCode, distCode,
//                IDBColumns.TABLE_BILL_INVOIC_HEADER);

        JSONArray jsonArray = new JSONArray();

        for (BillingInfoVO retailerVO : infoVOList) {

            String customerCode = retailerVO.getCustomerCode();

            JSONObject jObj = new JSONObject();
            jObj.put(COLUMN_CMP_CODE, retailerVO.getCmpCode());
            jObj.put(COLUMN_DISTR_CODE, retailerVO.getDistrCode());
            jObj.put(COLUMN_INVOICE_NUM, retailerVO.getInvoiceNo());
            jObj.put(COLUMN_GODOWN_CODE, retailerVO.getGodownCode());
            jObj.put(COLUMN_INVOICE_PATTERN, retailerVO.getInvoicePattern());
            jObj.put(COLUMN_SALESMAN_CODE, retailerVO.getSalesmanCode());
            jObj.put(COLUMN_ROUTE_CODE, retailerVO.getRouteCode());
            jObj.put(COLUMN_CUSTOMER_CODE, retailerVO.getCustomerCode());

            jObj.put(COLUMN_CUSTOMER_SHIP_CODE, retailerVO.getCustomerShipCode());

            jObj.put(COLUMN_INVOICE_DT, DateUtil.parseDateToddMMyyyy(retailerVO.getInvoiceDt()));
            jObj.put(COLUMN_SHIP_DT, DateUtil.parseDateToddMMyyyy(retailerVO.getShipDt()));
            jObj.put(COLUMN_DELIVERY_DT, DateUtil.parseDateToddMMyyyy(retailerVO.getDeliveryDt()));

            jObj.put(COLUMN_INVOICE_TYPE, retailerVO.getInvoiceType());
            jObj.put(COLUMN_INVOICE_MODE, retailerVO.getInvoiceMode());
            jObj.put(COLUMN_PAYMENT_MODE, retailerVO.getPaymentMode());
            jObj.put(COLUMN_TOT_SPL_DISC_AMT, retailerVO.getTotSplDiscAmt());
            jObj.put(COLUMN_TOT_SCH_DISC_AMT, retailerVO.getTotSchDiscAmt());
            jObj.put(COLUMN_TOT_CASH_DISC_AMT, retailerVO.getTotCashDiscAmt());
            jObj.put(COLUMN_TOT_DB_DISC_AMT, retailerVO.getTotDBDiscAmt());
            jObj.put(COLUMN_TOT_WD_DISC_AMT, retailerVO.getTotWDDiscAmt());
            jObj.put(COLUMN_TOT_CR_NOTE_AMT, retailerVO.getTotCrNoteAmt());
            jObj.put(COLUMN_TOT_DB_NOTE_AMT, retailerVO.getTotDbNoteAmt());
            jObj.put(COLUMN_TOT_ON_ACCOUNT_AMT, retailerVO.getTotOnAccountAmt());
            jObj.put(COLUMN_TOT_MARKET_RETURN_AMT, retailerVO.getTotMarketReturnAmt());
            jObj.put(COLUMN_TOT_REPLACEMENT_AMT, retailerVO.getTotReplacementAmt());
            jObj.put(COLUMN_TOT_OTHER_CHARGES_AMT, retailerVO.getTotOtherChargesAmt());
            jObj.put(COLUMN_INV_LEVEL_DISC_AMT, retailerVO.getInvLevelDiscAmt());
            jObj.put(COLUMN_INV_LEVEL_DISC_PERC, retailerVO.getInvLevelDiscPerc());
            jObj.put(COLUMN_TOT_GROSS_AMT, retailerVO.getTotGrossAmt());
            jObj.put(COLUMN_TOT_ADDITION, retailerVO.getTotAddition());
            jObj.put(COLUMN_TOT_DEDUCTION, retailerVO.getTotDeduction());
            jObj.put(COLUMN_TOT_TAX_AMT, retailerVO.getTotTaxAmt());
            jObj.put(COLUMN_ROUND_OFF_AMT, retailerVO.getRoundOffAmt());
            if (retailerVO.getTotCrNoteAmt() != null && !retailerVO.getTotCrNoteAmt().isEmpty()) {
                double crNoteAmt = Double.valueOf(retailerVO.getTotNetAmt()) - Double.valueOf(retailerVO.getTotCrNoteAmt());
                jObj.put(COLUMN_TOT_NET_AMT, crNoteAmt);
            } else {
                jObj.put(COLUMN_TOT_NET_AMT, retailerVO.getTotNetAmt());
            }

            jObj.put(COLUMN_ORDER_NO, retailerVO.getOrderNo());
            jObj.put(COLUMN_INVOICE_STATUS, retailerVO.getInvoiceStatus());
            jObj.put(COLUMN_REMARKS, retailerVO.getRemarks());
            jObj.put(COLUMN_LATITUDE, retailerVO.getLatitude());
            jObj.put(COLUMN_LONGITUDE, retailerVO.getLongitude());
            jObj.put(COLUMN_START_TIME, retailerVO.getStartTime());
            jObj.put(COLUMN_END_TIME, retailerVO.getEndTime());
            jObj.put(COLUMN_LINE_COUNT, retailerVO.getLineCount());
            jObj.put(TAG_BALANCE_OS, retailerVO.getBalanceOS());
            jObj.put(COLUMN_MOD_DT, retailerVO.getModeDate());
            jObj.put(UPLOAD_FLAG, "N");

            jObj.put(TAG_ORDER_BOOKING_DETAILS_LIST, addPendingInvoiceDetialList(retailerVO.getInvoiceNo(),
                    retailerVO.getDistrCode(), retailerVO.getSalesmanCode(), retailerVO.getRouteCode(), customerCode));

            jObj.put(TAG_ORDER_BOOKING_SCHEME_PROD_LIST, addInvoiceSChemeProdRuleList(retailerVO.getSalesmanCode(), retailerVO.getRouteCode(), customerCode, retailerVO.getInvoiceNo()));
            jObj.put(TAG_INVOICE_SCHEME_DETAILS_LIST, addInvoiceSchemeDetialsList(retailerVO.getSalesmanCode(), retailerVO.getRouteCode(), customerCode, retailerVO.getInvoiceNo()));
            jsonArray.put(jObj);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "",
                    retailerVO.getInvoiceNo(), cmpCode);
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addInvoiceCRDBAdjustment() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        String distrCode = preferences.readString(PREF_DISTRCODE);
        List<SalesDBCRNoteModel> infoVOList = database.getInvoiceCRDBAdjustmentDetails(cmpCode, distrCode);

        JSONArray jsonArray = new JSONArray();

        for (SalesDBCRNoteModel retailerVO : infoVOList) {

            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, retailerVO.getCmpCode());
            jObj.put(TAG_DISTR_CODE, retailerVO.getDistrCode());
            jObj.put(TAG_INVOICE_NO, retailerVO.getInvoiceNo());
            jObj.put(TAG_SALES_DB_CRREFNO, retailerVO.getSalesDBCRRefNo());
            jObj.put(TAG_DB_CRTYPE, retailerVO.getDbCRType());
            jObj.put(TAG_ADJUSTED_AMT, retailerVO.getAdjAmt());
            jObj.put(TAG_MOD_DT, retailerVO.getModDt());
            jObj.put(UPLOAD_FLAG, "N");

            jsonArray.put(jObj);

            retailerDetailVOs.add(addBasicDetToRetDetVo(retailerVO.getCmpCode(), retailerVO.getDistrCode(), retailerVO.getInvoiceNo(), retailerVO.getSalesDBCRRefNo()));
        }

        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addInvoiceSchemeDetialsList(String salesmanCode, String routeCode, String customerCode, String invoiceNo) throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        List<SchemeModel> orderBookingList = database.getInvoiceSchemeDetialsList(salesmanCode, routeCode, customerCode, invoiceNo, SCHEME_PAYOUT_FREE_PROD);
        JSONArray jALineItem = new JSONArray();
        for (SchemeModel orderBooking : orderBookingList) {

            JSONObject jObjLineItem = new JSONObject();

            jObjLineItem.put(TAG_CMP_CODE, orderBooking.getCmpCode());
            jObjLineItem.put(TAG_DISTR_CODE, orderBooking.getDistrCode());
            jObjLineItem.put(TAG_INVOICE_NO, orderBooking.getInvoiceNo());
            jObjLineItem.put(TAG_PROD_CODE, orderBooking.getProductCode());
            jObjLineItem.put(TAG_PROD_BATCH_CODE, orderBooking.getProdBatchCode());
            jObjLineItem.put(TAG_INVOICE_QTY, orderBooking.getQuantity());
            jObjLineItem.put(TAG_INVOICE_NO, orderBooking.getInvoiceNo());
            jObjLineItem.put(TAG_FREE_PROD_CODE, orderBooking.getFreeProdCode());
            jObjLineItem.put(FREE_PROD_BATCH_CODE, orderBooking.getFreeProdBatchCode());
            jObjLineItem.put(TAG_FREE_QTY, orderBooking.getFreeQty());
            jObjLineItem.put(TAG_SCHEME_CODE, orderBooking.getSchemeCode());
            jObjLineItem.put(TAG_SCHEME_SLAB_NO, orderBooking.getSlabNo());
            jObjLineItem.put(TAG_SCHEME_PAYOUT, orderBooking.getPayoutValue());
            jObjLineItem.put(TAG_UPLOAD_FLAG, "N");
            jObjLineItem.put(TAG_MOD_DT, System.currentTimeMillis());

            jALineItem.put(jObjLineItem);
        }
        return jALineItem;
    }

    private JSONArray addInvoiceSChemeProdRuleList(String salesmanCode, String routeCode, String customerCode, String invoiceNo) throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        List<SchemeModel> orderBookingList = database.getInvoiceSchemeDetialsList(salesmanCode, routeCode, customerCode, invoiceNo, SCHEME_PAYOUT_DISCOUNT);
        JSONArray jALineItem = new JSONArray();
        for (SchemeModel orderBooking : orderBookingList) {
            JSONObject jObjLineItem = new JSONObject();
            jObjLineItem.put(TAG_CMP_CODE, orderBooking.getCmpCode());
            jObjLineItem.put(TAG_DISTR_CODE, orderBooking.getDistrCode());
            jObjLineItem.put(TAG_ORDER_NO, orderBooking.getInvoiceNo());
            jObjLineItem.put(TAG_SCHEME_CODE, orderBooking.getSchemeCode());
            jObjLineItem.put(TAG_SCHEME_SLAB_NO, orderBooking.getSlabNo());
            jObjLineItem.put(TAG_PROD_CODE, orderBooking.getProductCode());
//            jObjLineItem.put(TAG_PROD_BATCH_CODE, orderBooking.getProdBatchCode());
//            jObjLineItem.put(TAG_INVOICE_QTY, orderBooking.getQuantity());
//            jObjLineItem.put(TAG_SCHEME_PAYOUT, orderBooking.getDicountAmount());
            jObjLineItem.put(TAG_DISCOUNT_AMOUNT, orderBooking.getDicountAmount());
            jObjLineItem.put(TAG_DISCOUNT_PERCENTAGE, orderBooking.getDicountPercentage());
            jObjLineItem.put(TAG_UPLOAD_FLAG, "N");
            jObjLineItem.put(TAG_MOD_DT, System.currentTimeMillis());

            jALineItem.put(jObjLineItem);
        }
        return jALineItem;
    }

    private JSONArray addPendingInvoiceDetialList(String invoiceNo, String distrCode,
                                                  String salesmanCode, String routeCode,
                                                  String customerCode) throws JSONException {

        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<OrderBookingVO> infoVOList = database.getInvoiceLineLevelDetail(cmpCode, distrCode,
                salesmanCode, routeCode, customerCode, invoiceNo);

        JSONArray jsonArray = new JSONArray();

        for (OrderBookingVO retailerVO : infoVOList) {

            JSONObject jObj = new JSONObject();
            jObj.put(COLUMN_CMP_CODE, retailerVO.getCmpCode());
            jObj.put(COLUMN_DISTR_CODE, retailerVO.getDistrCode());

//            jObj.put(COLUMN_SALESMAN_CODE, retailerVO.getSalesmanCode());
//            jObj.put(COLUMN_ROUTE_CODE, retailerVO.getRouteCode());
//            jObj.put(COLUMN_CUSTOMER_CODE, retailerVO.getRetailerCode());

//            jObj.put(COLUMN_INVOICE_NUM, retailerVO.getInvoiceNo());
//            jObj.put(COLUMN_PROD_CODE, retailerVO.getProdCode());
//            jObj.put(PROD_BATCH_CODE, retailerVO.getProdBatchCode());
//            jObj.put(COLUMN_TOTAL_ORD_QTY, retailerVO.getTotOrderQty());
//            jObj.put(COLUMN_TOTAL_INVOICE_QTY, retailerVO.getTotInvoiceQty());
//            jObj.put(FREE_QTY, retailerVO.getFreeQty());
//            jObj.put(COLUMN_INVOICE_QTY1, retailerVO.getQuantity());
//            jObj.put(COLUMN_UOM_CODE1, retailerVO.getUomId());
//            jObj.put(COLUMN_INVOICE_QTY2, retailerVO.getQuantity2());
//            jObj.put(COLUMN_UOM_CODE2, retailerVO.getUomId2());
            jObj.put(COLUMN_MRP, retailerVO.getMrp());

//            if (retailerVO.getSellPrice().doubleValue() > 0) {
//                jObj.put(COLUMN_SELL_RATE, retailerVO.getSellPrice());
//                jObj.put(COLUMN_ACTUAL_SELL_RATE, retailerVO.getActualSellRate());
//            } else {
//                jObj.put(COLUMN_SELL_RATE, retailerVO.getActualSellRate());
//                jObj.put(COLUMN_ACTUAL_SELL_RATE, retailerVO.getActualSellRate());
//            }
//
//            jObj.put(COLUMN_GROSS_AMT, retailerVO.getlGrossAmt());
//            jObj.put(COLUMN_SPL_DISC_AMT, retailerVO.getSplDiscAmt());
//            jObj.put(COLUMN_SPL_DISC_PERC, retailerVO.getSplDiscPerc());
//            jObj.put(COLUMN_CASH_DISC_PERC, retailerVO.getCashDiscPerc());
//            jObj.put(COLUMN_CASH_DISC_AMT, retailerVO.getCashDiscAmt());
//            jObj.put(COLUMN_SCH_DISC_AMT, retailerVO.getSchemeAmount());
//            jObj.put(COLUMN_SCH_DISC_PERC, retailerVO.getSchemePercentage());
//
//            jObj.put(COLUMN_DB_DISC_AMT, retailerVO.getDbDiscountAmt());
//            jObj.put(COLUMN_DB_DISC_PERC, retailerVO.getDbDiscountPerc());
//            jObj.put(COLUMN_INPUT_STR, retailerVO.getInputStr());
//            jObj.put(TAG_CGST_PERCENT, retailerVO.getCgstperc());
//            jObj.put(TAG_CGST_AMT, retailerVO.getCgstvalue());
//            jObj.put(TAG_SGST_PERCENT, retailerVO.getSgstPerc());
//            jObj.put(TAG_SGST_AMT, retailerVO.getSgstValue());
//            jObj.put(TAG_IGST_PERCENT, retailerVO.getIgstPerc());
//            jObj.put(TAG_IGST_AMT, retailerVO.getIgstvalue());

//            jObj.put(UTGST_PERCENT, retailerVO.getUtgstPerc());
//            jObj.put(UTGST_VALUE, retailerVO.getUtgstValue());

//            jObj.put(TAG_CESS_PERCENT, retailerVO.getCessPerc());
//            jObj.put(TAG_CESS_AMT, retailerVO.getCessValue());

            jObj.put(COLUMN_TAX_AMT, retailerVO.getTax());
            jObj.put(COLUMN_NET_AMT, retailerVO.getNetAmount());
            jObj.put(COLUMN_MOD_DT, retailerVO.getConfirmDate());
            jObj.put(UPLOAD_FLAG, "N");

            jsonArray.put(jObj);
        }

        return getJsonArrayLength(jsonArray);
    }


    /**
     * used to check upload Json String is not Null
     *
     * @param jsonObject upload json String
     * @return upload String or empty
     */
    private String checkJSONObjectNotNull(JSONObject jsonObject) throws IOException {
        if (jsonObject != null)
            return AppUtils.getOurInstance().compressGZIP(jsonObject.toString());
        else
            return "";
    }

    /**
     * used to check upload Json String is not Null
     *
     * @param jsonArray upload json String
     * @return upload String or empty
     */
    private String checkJSONArrayNotNull(JSONArray jsonArray) throws IOException {
        if (jsonArray != null)
            return AppUtils.getOurInstance().compressGZIP(jsonArray.toString());
        else
            return "";
    }


    private JSONArray getJsonArrayLength(JSONArray jsonArray) {
        if (jsonArray.length() > 0)
            return jsonArray;
        else
            return null;
    }

    private JSONArray addDistrWDFreezer() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        JSONArray jsonArray = new JSONArray();
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<WDInfrastructure> wdInfrastructures = database.getWDFreezer(cmpCode, distCode);

        for (WDInfrastructure wdInfrastructure : wdInfrastructures) {
            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, cmpCode);
            jObj.put(TAG_DISTR_CODE, distCode);
            jObj.put(TAG_CAPACITY, wdInfrastructure.getFreezerCapacity());
            jObj.put(TAG_UNIT, wdInfrastructure.getUnit());
            jObj.put(TAG_TOT_CAPACITY, wdInfrastructure.getTotalCapacity());
            jObj.put(TAG_UPLOAD_FLAG, "N");
            jObj.put(TAG_MOD_DT, System.currentTimeMillis());

            jsonArray.put(jObj);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", "", wdInfrastructure.getFreezerCapacity());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONObject addDistrWDStorage() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<WDInfrastructure> wdInfrastructures = database.getWDStorage(cmpCode, distCode);
        if (!wdInfrastructures.isEmpty()) {
            jsonObject.put(TAG_CMP_CODE, cmpCode);
            jsonObject.put(TAG_DISTR_CODE, distCode);
            jsonObject.put(TAG_WET_SQFT, wdInfrastructures.get(0).getWetsqft());
            jsonObject.put(TAG_FROZEN_SQFT, wdInfrastructures.get(0).getFrozensqft());
            jsonObject.put(TAG_UPLOAD_FLAG, "N");
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", "", "");
            retailerDetailVOs.add(retailerDetailVO);
            return jsonObject;
        }
        return null;
    }

    private JSONArray addRetailerMasterInfo() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        JSONArray jsonArray = new JSONArray();
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<RetailerVO> retailerList = database.getRetailerList(cmpCode, distCode);

        for (RetailerVO retailerVO : retailerList) {
            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, cmpCode);
            jObj.put(TAG_DISTR_CODE, distCode);
            jObj.put(TAG_CUSTOMER_CODE, retailerVO.getCustomerCode());
            jObj.put(TAG_COMPANY_CUSTOMER_CODE, retailerVO.getCmpCustomerCode());
            jObj.put(TAG_CUSTOMER_NAME, retailerVO.getCustomerName());
            jObj.put(TAG_PINCODE, retailerVO.getPostalCode());
            jObj.put(TAG_PHONE_NO, retailerVO.getPhoneNo());
            jObj.put(TAG_MOBILE_NO, retailerVO.getMobile());
            jObj.put(TAG_CONTACT_PERSON, retailerVO.getContactPerson());
            jObj.put(TAG_EMAIL, retailerVO.getEmail());
            jObj.put(TAG_DAYOFF, retailerVO.getSpinDayOffCount());
            jObj.put(TAG_RETAILER_STATUS, retailerVO.getRetailerStatus());
            jObj.put(TAG_FSSAI_NO, retailerVO.getFfsaLcNo());
            jObj.put(TAG_DRUG_LIC_NO, retailerVO.getDrugLcNo());
            jObj.put(TAG_DRUG_EXPIRY_DATE, retailerVO.getExpiryDateTimeStamp());
            jObj.put(TAG_CREDIT_BILLS, retailerVO.getCrBills());
            jObj.put(TAG_CREDIT_DAYS, retailerVO.getCrDays());
            jObj.put(TAG_CREDIT_LIMIT, retailerVO.getCrLimit());
            jObj.put(TAG_CASH_DISC_PERC, retailerVO.getCashDisc());
            jObj.put(TAG_CHANNEL_CODE, retailerVO.getChannelCode());
            jObj.put(TAG_SUB_CHANNEL_CODE, retailerVO.getSubChannelCode());
            jObj.put(TAG_GROUP_CODE, retailerVO.getGroupCode());
            jObj.put(TAG_CLASS_CODE, retailerVO.getClassCode());
            jObj.put(TAG_STORE_TYPE, retailerVO.getRelationStatus());
            jObj.put(TAG_PARENT_CUSTOMER_CODE, retailerVO.getParentMap());
            jObj.put(TAG_LATITUDE, retailerVO.getLatitude());
            jObj.put(TAG_LONGITUDE, retailerVO.getLongitude());
            jObj.put(TAG_CUSTOMER_TYPE, retailerVO.getGstType());
            jObj.put(TAG_GSTNO, retailerVO.getGstNo());
            jObj.put(TAG_PANNO, retailerVO.getPanNo());
            jObj.put(TAG_APPROVAL_STATUS, retailerVO.getApprovalStatus());
            jObj.put(TAG_UPLOAD_FLAG, "N");
            jObj.put(TAG_MOD_DT, System.currentTimeMillis());

            jsonArray.put(jObj);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", "", retailerVO.getCustomerCode());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addRetailerShipAddress() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<AddressModel> addrList = database.getRetailerShipAddrList(cmpCode, distCode);

        JSONArray jsonArray = new JSONArray();

        for (AddressModel addressModel : addrList) {
            JSONObject jObjLineItem = new JSONObject();
            jObjLineItem.put(TAG_CMP_CODE, cmpCode);
            jObjLineItem.put(TAG_DISTR_CODE, distCode);
            jObjLineItem.put(TAG_CUSTOMER_CODE, addressModel.getCustomerCode());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_CODE, addressModel.getShipCode());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_ADDR1, addressModel.getShippingAddress1());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_ADDR2, addressModel.getShippingAddress2());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_ADDR3, addressModel.getShippingAddress3());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_CITY, addressModel.getShipCityTown());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_GEOHIERPATH, addressModel.getShippingGeoHierPath());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_GSTSTATECODE, addressModel.getShippingState());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_PHONENO, addressModel.getShippingPhoneNumber());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_DEF_ADDR, addressModel.getIsDefault());
            jObjLineItem.put(TAG_UPLOAD_FLAG, "N");
            jObjLineItem.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jObjLineItem);


            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", addressModel.getShipCode(), addressModel.getCustomerCode());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addRetailerRoute() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        List<RouteModel> routeList = database.getRetailerRouteList(cmpCode, distCode);

        JSONArray jsonArray = new JSONArray();

        for (RouteModel routeModel : routeList) {
            JSONObject jObjLineItem = new JSONObject();
            jObjLineItem.put(TAG_CMP_CODE, cmpCode);
            jObjLineItem.put(TAG_DISTR_CODE, distCode);
            jObjLineItem.put(TAG_CUSTOMER_CODE, routeModel.getCustomerCode());
            jObjLineItem.put(TAG_ROUTE_CODE, routeModel.getRouteCode());
            jObjLineItem.put(TAG_COVERAGE_SEQ, routeModel.getCoverageSeq());
            jObjLineItem.put(TAG_UPLOAD_FLAG, "N");
            jObjLineItem.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jObjLineItem);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", routeModel.getRouteCode(), routeModel.getCustomerCode());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addRetailerBank() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        List<RetailerVO> retailerList = database.getRetailerBankList(cmpCode, distCode);

        JSONArray jsonArray = new JSONArray();

        for (RetailerVO retailerVO : retailerList) {
            JSONObject jObjLineItem = new JSONObject();
            jObjLineItem.put(TAG_CMP_CODE, cmpCode);
            jObjLineItem.put(TAG_DISTR_CODE, distCode);
            jObjLineItem.put(TAG_CUSTOMER_CODE, retailerVO.getCustomerCode());
            jObjLineItem.put(TAG_CUSTOMER_BANK_CODE, retailerVO.getBankID());
            jObjLineItem.put(TAG_CUSTOMER_BANK_NAME, retailerVO.getBankName());
            jObjLineItem.put(TAG_CUSTOMER_BRANCH_NAME, retailerVO.getBranchName());
            jObjLineItem.put(TAG_CUSTOMER_IFSC_CODE, retailerVO.getIfscCode());
            jObjLineItem.put(TAG_CUSTOMER_ACC_NUM, retailerVO.getAccountNo());
            jObjLineItem.put(TAG_CUSTOMER_ACC_TYPE, retailerVO.getAccountType());
            jObjLineItem.put(TAG_UPLOAD_FLAG, "N");
            jObjLineItem.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jObjLineItem);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", retailerVO.getBankID(), retailerVO.getCustomerCode());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    public DayStartHelper.AsyncResponse updateCustomerUploadStatus(String response, boolean isUpload) {

        SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
        SFADatabase database = SFADatabase.getInstance(context);

        try {
            JSONObject jsonObj = new JSONObject(response);

            if (jsonObj.has(TAG_USER_STATUS) &&
                    jsonObj.get(TAG_USER_STATUS) instanceof Boolean &&
                    !jsonObj.getBoolean(TAG_USER_STATUS)) {

                return DayStartHelper.AsyncResponse.USER_ACTIVE;
            }

            updateProgress(65);

            updateOrderUploadStatus(database, jsonObj, "", "");
//
//            /*Distributor upload Status*/
//            updateDistributorStatus(database, jsonObj);
//
//            updateRouteStatus(database, jsonObj);
//            updateCustomerStatus(database, jsonObj, distCode);
//            updateCustomerBankStatus(database, jsonObj, distCode);
//            updateCustomerRouteStatus(database, jsonObj, distCode);
//            updateCustomerShipAddressStatus(database, jsonObj, distCode);
//
//            updateWDStorage(database, jsonObj, distCode);
//            updateWDFreezer(database, jsonObj, distCode);
//            updateSalesmanStatus(database, jsonObj);
//            updateSalesmanRouteMappingStatus(database, jsonObj);
//            updateSalesmanLOBMappingStatus(database, jsonObj);
//            updateSalesmanKYCStatus(database, jsonObj);
//            updatePurchaseOrderStatus(database, jsonObj);
//            updatePurchaseInvoiceUploadStatus(database, jsonObj);
//            updateBillingInvoiceUploadStatus(database, jsonObj);
//            updateLogisticMaterialUploadStatus(database, jsonObj);
//            /* update stock on hand  status*/
//            updateStockOnHandUploadStatus(database, jsonObj);
//            updateSalesmanRouteCoverageUploadStatus(database, jsonObj);
//
//            updateCollection(database, jsonObj);
//            updateBillingAdjustment(database, jsonObj);
//            updatePendingBills(database, jsonObj);
//            updateStockAdjustment(database, jsonObj);
//            updateSalesReturn(database, jsonObj);
////            updateSchemeDistrBudget(database, jsonObj);
//            updateInvoiceCRDBAdjustment(database, jsonObj);
//            updateSalesDBCRNoteDetails(database, jsonObj);
//            updateCustomerApprovalStatus(database, jsonObj, distCode);
//            //Temp Codification tables
////            updateTempRouteStatus(database, jsonObj);
////            updateTempCustomerStatus(database, jsonObj, distCode);
////            updateTempCustomerRouteStatus(database, jsonObj, distCode);
////            updateTempCustomerShipAddressStatus(database, jsonObj, distCode);
//            updateBatchTransferStatus(database, jsonObj, distCode);
//            updateCodeGeneratorHistoryStatus(database, jsonObj);
            updateProgress(87);
            String syncUpdateColumn = "upload";
            if (!isUpload) {
                syncUpdateColumn = "dayClose";
            }

            database.updateSyncProgress(syncUpdateColumn, "Y", preferences.getString(PREF_LOGIN_CODE,""));
            updateProgress(90);
            Log.e(TAG, " retailerDetailVOs " + retailerDetailVOs);
            return retailerDetailVOs.isEmpty() ? DayStartHelper.AsyncResponse.UPLOAD_SUCCESS
                    : DayStartHelper.AsyncResponse.UPLOAD_FAILED;
        } catch (JSONException e) {
            Log.w(TAG, e);
            return DayStartHelper.AsyncResponse.UPLOAD_FAILED;
        }
    }

    private void updateOrderUploadStatus(SFADatabase database, JSONObject jsonObj, String distCode, String salesmanCode) throws JSONException {

        if (isJsonNotNull(jsonObj, TAG_ORDER_UPLOAD_STATUS_LIST) &&
                jsonObj.get(TAG_ORDER_UPLOAD_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_ORDER_UPLOAD_STATUS_LIST);
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_CMP_CODE)&& isJsonNotNull(object, TAG_DISTR_CODE)
                        && isJsonNotNull(object, TAG_ORDER_NO) && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distrCode = object.getString(TAG_DISTR_CODE);
                    String orderNo = object.getString(TAG_ORDER_NO);
                    orderUploaded = "Y";
                    database.uploadRetailerWorkStatus(orderNo, IDBColumns.TABLE_ORDER_BOOKING, COLUMN_INVOICE_NO, orderUploaded);
                    database.uploadRetailerWorkStatus(orderNo, IDBColumns.TABLE_ORDERED_APPLIED_SCHEME, COLUMN_ORDER_NO, orderUploaded);

                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, cmpCode, orderNo, orderNo));
                }
            }
        }
    }

    private void updateBatchTransferStatus(SFADatabase database, JSONObject jsonObj, String distCode) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_BATCH_TRANSFER_STATUS_LIST) &&
                jsonObj.get(TAG_BATCH_TRANSFER_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_BATCH_TRANSFER_STATUS_LIST);
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_BATCH_TRANSFER_NO)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String batchTransferNo = object.getString(TAG_BATCH_TRANSFER_NO);
                    String stockType = object.getString(TAG_STOCK_TYPE);
                    String prodCode = object.getString(TAG_PROD_CODE);
                    String fromBatchCode = object.getString(TAG_FROM_BATCH_CODE);
                    String toBatchCode = object.getString(TAG_TO_BATCH_CODE);
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    orderUploaded = "Y";
                    database.uploadBTRStatus(batchTransferNo, stockType, prodCode, fromBatchCode, toBatchCode, IDBColumns.TABLE_BATCH_TRANSFER_DETAILS, IDBColumns.UPLOAD_FLAG, orderUploaded);

                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, cmpCode,"", batchTransferNo));
                }
            }
        }
    }

    public DayStartHelper.AsyncResponse updateCodificationUploadStatus(String response, boolean isUpload) {
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
        SFADatabase database = SFADatabase.getInstance(context);

        try {
            JSONObject jsonObj = new JSONObject(response);

            updateProgress(65);

            String distCode = preferences.getString(PREF_DISTRCODE, "");

            /*Distributor upload Status*/
            updateRouteStatus(database, jsonObj);
            updateCustomerStatus(database, jsonObj, distCode);
            updateCustomerRouteStatus(database, jsonObj, distCode);
            updateCustomerShipAddressStatus(database, jsonObj, distCode);
            //Temp Codification tables
            updateTempRouteStatus(database, jsonObj);
            updateTempCustomerStatus(database, jsonObj, distCode);
            updateTempCustomerRouteStatus(database, jsonObj, distCode);
            updateTempCustomerShipAddressStatus(database, jsonObj, distCode);
            updateProgress(87);
            String syncUpdateColumn = "upload";
            if (!isUpload) {
                syncUpdateColumn = "dayClose";
            }

            database.updateSyncProgress(syncUpdateColumn, "Y", distCode);
            updateProgress(90);
            Log.e(TAG, " retailerDetailVOs " + retailerDetailVOs);
            return retailerDetailVOs.isEmpty() ? DayStartHelper.AsyncResponse.UPLOAD_SUCCESS
                    : DayStartHelper.AsyncResponse.UPLOAD_FAILED;
        } catch (JSONException e) {
            Log.w(TAG, e);
            return DayStartHelper.AsyncResponse.UPLOAD_FAILED;
        }
    }

    private void updateCustomerApprovalStatus(SFADatabase database, JSONObject jsonObj, String distCode) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_CUSTOMER_APPROVAL_STATUS_LIST) &&
                jsonObj.get(TAG_CUSTOMER_APPROVAL_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_CUSTOMER_APPROVAL_STATUS_LIST);
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_CUSTOMER_CODE)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String customerCode = object.getString(TAG_CUSTOMER_CODE);
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    orderUploaded = "Y";
                    database.uploadCustomerStatus(customerCode, IDBColumns.TABLE_CUSTOMER_APPROVAL_DATA, IDBColumns.UPLOAD_FLAG, orderUploaded);

                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", cmpCode, customerCode));
                }
            }
        }
    }

    private void updateSalesDBCRNoteDetails(SFADatabase database, JSONObject jsonObj) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_SALES_DBCRNOTE_STATUS) &&
                jsonObj.get(TAG_SALES_DBCRNOTE_STATUS) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_SALES_DBCRNOTE_STATUS);
            for (int i = 0; i < array.length(); i++) {
                String uploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_SALES_DB_CRREFNO)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String salesDBCRRefNo = object.getString(TAG_SALES_DB_CRREFNO);
                    String customerCode = object.getString(TAG_CUSTOMER_CODE);
                    String cmpCode = object.getString(TAG_CMP_CODE);

                    uploaded = "Y";
                    database.uploadSalesDBCRNoteDetails(cmpCode, customerCode, salesDBCRRefNo, TABLE_SALES_DB_CRNOTE, UPLOAD_FLAG, uploaded);
                    removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, "", customerCode, salesDBCRRefNo));
                }
            }
        }
    }

    private void updateInvoiceCRDBAdjustment(SFADatabase database, JSONObject jsonObj) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_INVOICE_CRDBADJUSTMENT_STATUS) &&
                jsonObj.get(TAG_INVOICE_CRDBADJUSTMENT_STATUS) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_INVOICE_CRDBADJUSTMENT_STATUS);
            for (int i = 0; i < array.length(); i++) {
                String uploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_SALES_DB_CRREFNO)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String salesDBCRRefNo = object.getString(TAG_SALES_DB_CRREFNO);
                    String invoiceNo = object.getString(TAG_INVOICE_NO);
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distrCode = object.getString(TAG_DISTR_CODE);
                    uploaded = "Y";
                    database.uploadInvoiceCRDBAdjustment(cmpCode, distrCode, invoiceNo, salesDBCRRefNo, TABLE_INVOICE_CR_DB_ADJUSTMENT, UPLOAD_FLAG, uploaded);
                    removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distrCode, invoiceNo, salesDBCRRefNo));
                }
            }
        }
    }

    private void updateSalesReturn(SFADatabase database, JSONObject jsonObj) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_SALES_RETURN_STATUS) &&
                jsonObj.get(TAG_SALES_RETURN_STATUS) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_SALES_RETURN_STATUS);
            for (int i = 0; i < array.length(); i++) {
                String uploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_SALES_RETURN_NO)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String salesReturnNo = object.getString(TAG_SALES_RETURN_NO);
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distrCode = object.getString(TAG_DISTR_CODE);
                    uploaded = "Y";
                    database.uploadSalesReturnHeader(cmpCode, distrCode, salesReturnNo, TABLE_SALES_RETURN_HEADER, UPLOAD_FLAG, uploaded);
                    database.uploadSalesReturnLineDetail(cmpCode, distrCode, salesReturnNo, TABLE_SALES_RETURN_DETAILS, UPLOAD_FLAG, uploaded);
                    removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distrCode, "", salesReturnNo));
                }
            }
        }
    }

    private void updateStockAdjustment(SFADatabase database, JSONObject jsonObj) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_STOCK_ADJUSTMENT_STATUS) &&
                jsonObj.get(TAG_STOCK_ADJUSTMENT_STATUS) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_STOCK_ADJUSTMENT_STATUS);
            for (int i = 0; i < array.length(); i++) {
                String uploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_STOCK_ADJUSTNO)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String stockAdjustNo = object.getString(TAG_STOCK_ADJUSTNO);
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distrCode = object.getString(TAG_DISTR_CODE);
                    String godownCode = object.getString(TAG_GODOWN_CODE);
                    uploaded = "Y";
                    database.uploadStockAdjustmentHeader(cmpCode, distrCode, godownCode, stockAdjustNo, TABLE_STOCK_ADJ_HEADER, UPLOAD_FLAG, uploaded);
                    database.uploadStockAdjustmentDetail(cmpCode, distrCode, godownCode, stockAdjustNo, TABLE_STOCK_ADJ_DETAILS, UPLOAD_FLAG, uploaded);
                    removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distrCode, godownCode, stockAdjustNo));
                }
            }
        }
    }

    private void updatePendingBills(SFADatabase database, JSONObject jsonObj) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_UPDATE_PENDING_BILL) &&
                jsonObj.get(TAG_UPDATE_PENDING_BILL) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_UPDATE_PENDING_BILL);
            for (int i = 0; i < array.length(); i++) {
                String uploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_INVOICE_NO)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String invoiceNo = object.getString(TAG_INVOICE_NO);
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distCode = object.getString(TAG_DISTR_CODE);
                    uploaded = "Y";
                    database.uploadPendingBills(cmpCode, distCode, invoiceNo, TABLE_PENDING_BILLS, UPLOAD_FLAG, uploaded);
//                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", "", invoiceNo));
                }
            }
        }
    }

    private void updateBillingAdjustment(SFADatabase database, JSONObject jsonObj) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_BILL_ADJUSTMENT_STATUS_LIST) &&
                jsonObj.get(TAG_BILL_ADJUSTMENT_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_BILL_ADJUSTMENT_STATUS_LIST);
            for (int i = 0; i < array.length(); i++) {
                String uploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_COLLECTION_NO)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String collectionNo = object.getString(TAG_COLLECTION_NO);
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distCode = object.getString(TAG_DISTR_CODE);
                    uploaded = "Y";
                    database.uploadBillAdjustment(cmpCode, distCode, collectionNo, TABLE_BILL_ADJUSTMENT, UPLOAD_FLAG, uploaded);
                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, cmpCode, "", collectionNo));
                }
            }
        }
    }

    private void updateCollection(SFADatabase database, JSONObject jsonObj) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_COLLECTION_STATUS_LIST) &&
                jsonObj.get(TAG_COLLECTION_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_COLLECTION_STATUS_LIST);
            for (int i = 0; i < array.length(); i++) {
                String uploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_COLLECTION_NO)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String collectionNo = object.getString(TAG_COLLECTION_NO);
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distCode = object.getString(TAG_DISTR_CODE);
                    uploaded = "Y";
                    database.uploadCollection(cmpCode, distCode, collectionNo, IDBColumns.TABLE_COLLECTION, IDBColumns.UPLOAD_FLAG, uploaded);
                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", "", collectionNo));
                }
            }
        }
    }

    private void updateStockOnHandUploadStatus(SFADatabase database, JSONObject jsonObj) throws JSONException {

        if (isJsonNotNull(jsonObj, TAG_STOCK_ON_HAND_STATUS_LIST) &&
                jsonObj.get(TAG_STOCK_ON_HAND_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_STOCK_ON_HAND_STATUS_LIST);

            String orderUploaded;
            for (int i = 0; i < array.length(); i++) {

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_PROD_CODE)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distCode = object.getString(TAG_DISTR_CODE);
                    String prodCode = object.getString(TAG_PROD_CODE);
                    String batchCode = object.getString(TAG_PROD_BATCH_CODE);
                    orderUploaded = "Y";
                    database.updateStockOnHandUploadStatus(cmpCode, distCode, prodCode, batchCode, TABLE_STOCK_ON_HAND, IDBColumns.COLUMN_UPLOAD, orderUploaded);

                    removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distCode, prodCode, batchCode));
                }
            }
        }

    }

    private void updatePurchaseOrderStatus(SFADatabase database, JSONObject jsonObj) throws JSONException {

        if (isJsonNotNull(jsonObj, TAG_PURCHASE_ORDER_STATUS_UPDATE_LIST) &&
                jsonObj.get(TAG_PURCHASE_ORDER_STATUS_UPDATE_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_PURCHASE_ORDER_STATUS_UPDATE_LIST);
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_PO_REF_NO)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distCode = object.getString(TAG_DISTR_CODE);
                    String invoiceNo = object.getString(TAG_PO_REF_NO);
                    String prodCode = object.getString(TAG_PROD_CODE);
                    String batchCode = object.getString(TAG_PROD_BATCH_CODE);
                    orderUploaded = "Y";
                    database.updatePurchaseOrderStatus(cmpCode, distCode, invoiceNo, prodCode, batchCode, IDBColumns.TABLE_PURCHASE_ORDER_BOOKING, IDBColumns.COLUMN_UPLOAD, orderUploaded);

                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, invoiceNo, prodCode, batchCode));
                }
            }
        }
    }

    private void updatePurchaseInvoiceUploadStatus(SFADatabase database, JSONObject jsonObj) {

        try {
            if (isJsonNotNull(jsonObj, TAG_PURCHASE_INVOICE_STATUS_LIST) &&
                    jsonObj.get(TAG_PURCHASE_INVOICE_STATUS_LIST) instanceof JSONArray) {
                JSONArray array = jsonObj.getJSONArray(TAG_PURCHASE_INVOICE_STATUS_LIST);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distrCode = object.getString(TAG_DISTR_CODE);
                    String invoiceNo = object.getString(COLUMN_INVOICE_NO);
                    String prodCode = object.getString(COLUMN_PROD_CODE);
                    String batchCode = object.getString(PROD_BATCH_CODE);

                    if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_DISTR_CODE) && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                        String uploaded = "Y";

                        String[] parameters = new String[]{cmpCode, distrCode, invoiceNo, prodCode, batchCode};
                        String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_INVOICE_NO,
                                COLUMN_PROD_CODE, PROD_BATCH_CODE};
                        database.updateUploadFlagStatus(TABLE_PURCHASE_INVOICE, uploaded, parameters, columns);

                        removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, invoiceNo, prodCode, batchCode));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updatePurchaseInvoiceUploadStatus: " + e.getMessage(), e);
        }
    }

    private void updateBillingInvoiceUploadStatus(SFADatabase database, JSONObject jsonObj) {

        try {
            if (isJsonNotNull(jsonObj, TAG_INVOICE_STATUS_LIST) &&
                    jsonObj.get(TAG_INVOICE_STATUS_LIST) instanceof JSONArray) {
                JSONArray array = jsonObj.getJSONArray(TAG_INVOICE_STATUS_LIST);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distrCode = object.getString(TAG_DISTR_CODE);
                    String invoiceNo = object.getString(COLUMN_INVOICE_NO);

                    if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_DISTR_CODE) &&
                            (object.getBoolean(TAG_UPLOAD_STATUS))) {
                        String uploaded = "Y";

                        String[] parameters = new String[]{cmpCode, distrCode, invoiceNo};
                        String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_INVOICE_NO};
                        database.updateUploadFlagStatus(TABLE_BILL_INVOIC_HEADER, uploaded, parameters, columns);
                        database.updateUploadFlagStatus(TABLE_BILL_INVOICE_DETAILS, uploaded, parameters, columns);

                        removeRetailerDetailsVo(addBasicDetToRetDetVo(distrCode, "", invoiceNo, cmpCode));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updatePurchaseInvoiceUploadStatus: " + e.getMessage(), e);
        }
    }

    private void updateLogisticMaterialUploadStatus(SFADatabase database, JSONObject jsonObj) throws JSONException {

        try {
            if (isJsonNotNull(jsonObj, TAG_LOGISTIC_MATERIAL_STOCK_STATUS) &&
                    jsonObj.get(TAG_LOGISTIC_MATERIAL_STOCK_STATUS) instanceof JSONArray) {
                JSONArray array = jsonObj.getJSONArray(TAG_LOGISTIC_MATERIAL_STOCK_STATUS);

                for (int i = 0; i < array.length(); i++) {

                    JSONObject object = array.getJSONObject(i);

                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distrCode = object.getString(TAG_DISTR_CODE);
                    String invoiceNo = object.getString(COLUMN_INVOICE_NO);
                    String prodCode = object.getString(COLUMN_MATERIAL_CODE);

                    if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_DISTR_CODE) && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                        String uploaded = "N";

                        String[] parameters = new String[]{cmpCode, distrCode, invoiceNo, prodCode};
                        String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_INVOICE_NO,
                                COLUMN_MATERIAL_CODE};
                        database.updateUploadFlagStatus(TABLE_LOGISTIC_MATERIAL_STOCK, uploaded, parameters, columns);

                        removeRetailerDetailsVo(addBasicDetToRetDetVo(distrCode, invoiceNo, "", ""));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updateLogisticMaterialUploadStatus: " + e.getMessage(), e);
        }
    }

    private void updateSalesmanRouteCoverageUploadStatus(SFADatabase database, JSONObject jsonObj) {

        try {
            if (isJsonNotNull(jsonObj, TAG_SALESMAN_ROUTE_COVERAGE_STATUS) &&
                    jsonObj.get(TAG_SALESMAN_ROUTE_COVERAGE_STATUS) instanceof JSONArray) {
                JSONArray array = jsonObj.getJSONArray(TAG_SALESMAN_ROUTE_COVERAGE_STATUS);

                for (int i = 0; i < array.length(); i++) {

                    JSONObject object = array.getJSONObject(i);

                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distrCode = object.getString(TAG_DISTR_CODE);
                    String salesmanCode = object.getString(COLUMN_SALESMAN_CODE);
                    String coverageDt = object.getString(COLUMN_COVERAGE_DT);

                    if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_DISTR_CODE) &&
                            (object.getBoolean(TAG_UPLOAD_STATUS))) {
                        String uploaded = "Y";

                        String[] parameters = new String[]{cmpCode, distrCode, salesmanCode, coverageDt};
                        String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_SALESMAN_CODE,
                                COLUMN_COVERAGE_DT};
                        database.updateUploadFlagStatus(TABLE_SALESMAN_ROUTE_COVERAGE, uploaded, parameters, columns);

                        removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distrCode, salesmanCode, coverageDt));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updateSalesmanRouteCoverageUploadStatus: " + e, e);
        }
    }

    private void updateCustomerShipAddressStatus(SFADatabase database, JSONObject jsonObj, String distCode) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_CUSTOMER_SHIP_ADDRESS_STATUS_LIST) &&
                jsonObj.get(TAG_CUSTOMER_SHIP_ADDRESS_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_CUSTOMER_SHIP_ADDRESS_STATUS_LIST);
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_CUSTOMER_CODE)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String customerCode = object.getString(TAG_CUSTOMER_CODE);
                    String customerShipCode = object.getString(TAG_CUSTOMER_SHIP_CODE);
                    orderUploaded = "Y";
                    database.uploadCustomerShipAddrStatus(customerCode, customerShipCode, IDBColumns.TABLE_CUSTOMER_SHIP_ADDRESS, IDBColumns.COLUMN_UPLOAD, orderUploaded);

                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", customerShipCode, customerCode));
                }
            }
        }
    }

    private void updateCustomerRouteStatus(SFADatabase database, JSONObject jsonObj, String distCode) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_CUSTOMER_ROUTE_MAPPING_STATUS_LIST) &&
                jsonObj.get(TAG_CUSTOMER_ROUTE_MAPPING_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_CUSTOMER_ROUTE_MAPPING_STATUS_LIST);
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_CUSTOMER_CODE)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String customerCode = object.getString(TAG_CUSTOMER_CODE);
                    String routeCode = object.getString(TAG_ROUTE_CODE);
                    orderUploaded = "Y";
                    database.uploadCustomerRouteStatus(customerCode, routeCode, IDBColumns.TABLE_CUSTOMER_ROUTE, IDBColumns.COLUMN_UPLOAD, orderUploaded);

                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", routeCode, customerCode));
                }
            }
        }
    }

    private void updateCustomerBankStatus(SFADatabase database, JSONObject jsonObj, String distCode) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_CUSTOMER_BANK_STATUS_LIST) &&
                jsonObj.get(TAG_CUSTOMER_BANK_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_CUSTOMER_BANK_STATUS_LIST);
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_CUSTOMER_CODE)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String customerCode = object.getString(TAG_CUSTOMER_CODE);
                    String bankID = object.getString(TAG_CUSTOMER_BANK_CODE);
                    orderUploaded = "Y";

                    database.uploadCustomerBankStatus(customerCode, bankID, IDBColumns.TABLE_CUSTOMER_BANK, IDBColumns.COLUMN_UPLOAD, orderUploaded);

                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", bankID, customerCode));
                }
            }
        }
    }

    private void updateDistributorStatus(SFADatabase database, JSONObject jsonObj) {
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
        String distCode = preferences.getString(PREF_DISTRCODE, "");
        String cmpCode = preferences.getString(PREF_CMP_CODE, "");

        try {
            if (isJsonNotNull(jsonObj, TAG_DISTRBUTOR_STATUS) &&
                    jsonObj.get(TAG_DISTRBUTOR_STATUS) instanceof JSONArray) {
                JSONObject object = jsonObj.getJSONObject(TAG_DISTRBUTOR_STATUS);

                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_DISTR_CODE) && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String uploaded = "Y";

                    database.updateDistrbutorUploadStatus(distCode, TABLE_DISTRIBUTOR,
                            COLUMN_DISTR_CODE, uploaded);

//                    removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distCode, "", ""));
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updateDistributorStatus: " + e.getMessage(), e);
        }
    }

    private void updateRouteStatus(SFADatabase database, JSONObject jsonObj) {
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
        String distCode = preferences.getString(PREF_DISTRCODE, "");
        String cmpCode = preferences.getString(PREF_CMP_CODE, "");

        try {
            if (isJsonNotNull(jsonObj, TAG_ROUTE_STATUS) &&
                    jsonObj.get(TAG_ROUTE_STATUS) instanceof JSONArray) {
                JSONArray jsonArray = jsonObj.getJSONArray(TAG_ROUTE_STATUS);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_DISTR_CODE) && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                        String uploaded = "Y";
                        String routeCode = object.getString(TAG_ROUTE_CODE);
                        String[] parameters = new String[]{cmpCode, distCode, routeCode};
                        String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_ROUTE_CODE};
                        database.updateUploadStatus(TABLE_ROUTE, uploaded, parameters, columns);

                        removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distCode, routeCode, ""));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updateDistributorStatus: " + e.getMessage(), e);
        }
    }

    private void updateSalesmanStatus(SFADatabase database, JSONObject jsonObj) {
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
        String distCode = preferences.getString(PREF_DISTRCODE, "");
        String cmpCode = preferences.getString(PREF_CMP_CODE, "");

        try {
            if (isJsonNotNull(jsonObj, TAG_SALESMAN_STATUS_LIST) &&
                    jsonObj.get(TAG_SALESMAN_STATUS_LIST) instanceof JSONArray) {
                JSONArray jsonArray = jsonObj.getJSONArray(TAG_SALESMAN_STATUS_LIST);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_DISTR_CODE) && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                        String uploaded = "Y";
                        String salesmanCode = object.getString(TAG_SALESMAN_CODE);

                        String[] parameters = new String[]{cmpCode, distCode, salesmanCode};
                        String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_SALESMAN_CODE};
                        database.updateUploadStatus(TABLE_SALESMAN_MASTER, uploaded, parameters, columns);

                        removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distCode, salesmanCode, ""));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updateDistributorStatus: " + e.getMessage(), e);
        }
    }

    private void updateSalesmanRouteMappingStatus(SFADatabase database, JSONObject jsonObj) {
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
        String distCode = preferences.getString(PREF_DISTRCODE, "");
        String cmpCode = preferences.getString(PREF_CMP_CODE, "");

        try {
            if (isJsonNotNull(jsonObj, TAG_SALESMAN_ROUTE_MAPPING_STATUS_LIST) &&
                    jsonObj.get(TAG_SALESMAN_ROUTE_MAPPING_STATUS_LIST) instanceof JSONArray) {
                JSONArray jsonArray = jsonObj.getJSONArray(TAG_SALESMAN_ROUTE_MAPPING_STATUS_LIST);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_DISTR_CODE) && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                        String uploaded = "Y";
                        String salesmanCode = object.getString(TAG_SALESMAN_CODE);
                        String routeCode = object.getString(TAG_ROUTE_CODE);

                        String[] parameters = new String[]{cmpCode, distCode, salesmanCode, routeCode};
                        String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_SALESMAN_CODE, COLUMN_ROUTE_CODE};
                        database.updateUploadStatus(TABLE_SALESMAN_ROUTE_MAPPING, uploaded, parameters, columns);

                        removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distCode, salesmanCode, routeCode));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updateDistributorStatus: " + e.getMessage(), e);
        }
    }

    private void updateSalesmanKYCStatus(SFADatabase database, JSONObject jsonObj) {
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
        String distCode = preferences.getString(PREF_DISTRCODE, "");
        String cmpCode = preferences.getString(PREF_CMP_CODE, "");

        try {
            if (isJsonNotNull(jsonObj, TAG_SALESMAN_KYC_STATUS_LIST) &&
                    jsonObj.get(TAG_SALESMAN_KYC_STATUS_LIST) instanceof JSONArray) {
                JSONArray jsonArray = jsonObj.getJSONArray(TAG_SALESMAN_KYC_STATUS_LIST);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_DISTR_CODE) && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                        String uploaded = "Y";
                        String salesmanCode = object.getString(TAG_SALESMAN_CODE);
                        String documentType = object.getString(TAG_TYPE);

                        String[] parameters = new String[]{cmpCode, distCode, salesmanCode, documentType};
                        String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_SALESMAN_CODE, COLUMN_TYPE};
                        database.updateUploadStatus(TABLE_SALESMAN_MASTER_KYC, uploaded, parameters, columns);

                        removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distCode, salesmanCode, documentType));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updateDistributorStatus: " + e.getMessage(), e);
        }
    }

    private void updateSalesmanLOBMappingStatus(SFADatabase database, JSONObject jsonObj) {
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
        String distCode = preferences.getString(PREF_DISTRCODE, "");
        String cmpCode = preferences.getString(PREF_CMP_CODE, "");

        try {
            if (isJsonNotNull(jsonObj, TAG_SALESMAN_LOB_MAPPING_STATUS_LIST) &&
                    jsonObj.get(TAG_SALESMAN_LOB_MAPPING_STATUS_LIST) instanceof JSONArray) {
                JSONArray jsonArray = jsonObj.getJSONArray(TAG_SALESMAN_LOB_MAPPING_STATUS_LIST);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_DISTR_CODE) && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                        String uploaded = "Y";
                        String salesmanCode = object.getString(TAG_SALESMAN_CODE);
                        String lobCode = object.getString(TAG_LOB_CODE);

                        String[] parameters = new String[]{cmpCode, distCode, salesmanCode, lobCode};
                        String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_SALESMAN_CODE, COLUMN_LOB_CODE};
                        database.updateUploadStatus(TABLE_SALESMAN_LOB_MAPPING, uploaded, parameters, columns);

                        removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distCode, salesmanCode, lobCode));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updateDistributorStatus: " + e.getMessage(), e);
        }
    }

    private void updateWDFreezer(SFADatabase database, JSONObject jsonObj, String distCode) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_DISTR_WD_FREEZER_STATUS) &&
                jsonObj.get(TAG_DISTR_WD_FREEZER_STATUS) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_DISTR_WD_FREEZER_STATUS);
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_CAPACITY)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String freezerCapacity = object.getString(TAG_CAPACITY);
                    orderUploaded = "Y";
                    database.uploadWDFreezerStatus(distCode, freezerCapacity, IDBColumns.TABLE_WD_FREEZER, IDBColumns.COLUMN_UPLOAD, orderUploaded);

                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", "", freezerCapacity));
                }
            }
        }
    }

    private void updateWDStorage(SFADatabase database, JSONObject jsonObj, String distCode) throws JSONException {

        if (isJsonNotNull(jsonObj, TAG_DISTR_WD_STORAGE_STATUS) &&
                jsonObj.get(TAG_DISTR_WD_STORAGE_STATUS) instanceof JSONObject) {
            JSONObject object = jsonObj.getJSONObject(TAG_DISTR_WD_STORAGE_STATUS);

            if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                String orderUploaded = "Y";
                database.uploadWDStorageStatus(distCode, IDBColumns.TABLE_WD_STORAGE, IDBColumns.COLUMN_UPLOAD, orderUploaded);

                removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", "", ""));
            }
        }

    }

    private void updateCustomerStatus(SFADatabase database, JSONObject jsonObj, String distCode) throws JSONException {

        if (isJsonNotNull(jsonObj, TAG_CUSTOMER_UPLOAD_STATUS_LIST) &&
                jsonObj.get(TAG_CUSTOMER_UPLOAD_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_CUSTOMER_UPLOAD_STATUS_LIST);
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_CUSTOMER_CODE)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String customerCode = object.getString(TAG_CUSTOMER_CODE);
                    orderUploaded = "Y";
                    database.uploadCustomerStatus(customerCode, IDBColumns.TABLE_CUSTOMER, IDBColumns.COLUMN_UPLOAD, orderUploaded);

                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", "", customerCode));
                }
            }
        }

    }


    private RetailerDetailVO addBasicDetToRetDetVo(String distCode, String salesmanCode, String routeCode, String customerCode) {
        RetailerDetailVO retailerDetailVO = new RetailerDetailVO();
        retailerDetailVO.setDistCode(distCode);
        retailerDetailVO.setSalesmanCode(salesmanCode);
        retailerDetailVO.setRouteCode(routeCode);
        retailerDetailVO.setCustomerCode(customerCode);
        return retailerDetailVO;
    }

    private void removeRetailerDetailsVo(RetailerDetailVO retailerDetailVO) {
        if (retailerDetailVOs.contains(retailerDetailVO)) {
            int idx = retailerDetailVOs.indexOf(retailerDetailVO);

            Log.d(TAG, "removeRetailerDetailsVo: " + idx);
            retailerDetailVOs.remove(idx);
        }
    }

    //   Add Distributor Details
    private JSONObject addDistributorDetails(String distributorCode) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        SFADatabase database = SFADatabase.getInstance(context);

        Distributor distributor = database.getDistributor(distributorCode);

        if (distributor.getUploadFlag().equals("N")) {

            jsonObject.put(TAG_CMP_CODE, distributor.getCompanyCode());
            jsonObject.put(TAG_DISTR_CODE, distributor.getDistributorCode());
            jsonObject.put(TAG_DISTR_NAME, distributor.getDistributorName());
            jsonObject.put(TAG_DISTR_ADDRESS1, distributor.getDistributorAddress1());
            jsonObject.put(TAG_DISTR_ADDRESS2, distributor.getDistributorAddress2());
            jsonObject.put(TAG_DISTR_ADDRESS3, distributor.getDistributorAddress3());
            jsonObject.put(TAG_PINCODE, distributor.getPincode());
            jsonObject.put(TAG_PHONE_NO, distributor.getPhoneNumber());
            jsonObject.put(TAG_MOBILE_NO, distributor.getMobileNumber());
            jsonObject.put(TAG_CONTACT_PERSON, distributor.getContactPerson());
            jsonObject.put(TAG_MAIL_ID, distributor.getMailId());
            jsonObject.put(TAG_BRANCH_CODE, distributor.getBranchCode());
            jsonObject.put(TAG_BRANCH_NAME, distributor.getBranchName());
            jsonObject.put(TAG_GST_STATE_CODE, distributor.getGstStateCode());
            jsonObject.put(TAG_GEO_STATE_CODE, distributor.getGeoStateCode());
            jsonObject.put(TAG_GEO_HIER_PATH, distributor.getGeoHierPath());
            jsonObject.put(TAG_DRUG_LICENCE_NO1, distributor.getDrugLicenceNumber1());
            jsonObject.put(TAG_DRUG_LICENCE_EXPIRY1, distributor.getDrugLicenceExpiryDate1());
            jsonObject.put(TAG_DAY_OFF, distributor.getDayOff());
            jsonObject.put(TAG_FSSAI_NO, distributor.getFssaiNumber());
            jsonObject.put(TAG_FSSAI_EXPIRY_DATE, distributor.getFssaiExpiryDate());
            jsonObject.put(TAG_DISTR_TYPE_NAME, distributor.getDistributorTypeName());
            jsonObject.put(TAG_PDA_DISTRIBUTOR, distributor.getPdaDistributor());
            jsonObject.put(TAG_DISTR_STATUS, distributor.getDistrStatus());
            jsonObject.put(TAG_GST_DISTR_TYPE, distributor.getGstDistributorType());
            jsonObject.put(TAG_GSTIN_NO, distributor.getGstinNumber());
            jsonObject.put(TAG_PAN_NO, distributor.getPanNumber());
            jsonObject.put(TAG_AADHAR_NO, distributor.getAadharNumber());
            jsonObject.put(TAG_UPLOAD_FLAG, distributor.getUploadFlag());
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());

//            retailerDetailVOs.add(addBasicDetToRetDetVo(distributor.getCompanyCode(), distributor.getDistributorCode(), "", ""));
            return getJsonObjectOrNull(jsonObject);
        }
        return null;
    }

    private JSONObject getJsonObjectOrNull(JSONObject jsonObject) {
        if (jsonObject != null)
            return jsonObject;
        else
            return null;
    }

    //   Add Route Details
    private JSONArray addRouteDetails(String distributorCode) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        SFADatabase database = SFADatabase.getInstance(context);

        List<RouteModel> routeModels = database.getRoutesForUpload(distributorCode);

        for (RouteModel routeModel : routeModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_CMP_CODE, routeModel.getCompanyCode());
            jsonObject.put(TAG_DISTR_CODE, routeModel.getDistributorCode());
            jsonObject.put(TAG_ROUTE_CODE, routeModel.getRouteCode());
            jsonObject.put(TAG_ROUTE_NAME, routeModel.getRouteName());
            jsonObject.put(TAG_ROUTE_TYPE, routeModel.getRouteType());
            jsonObject.put(TAG_UPLOAD_FLAG, routeModel.getUploadFlag());
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jsonObject);
            retailerDetailVOs.add(addBasicDetToRetDetVo(routeModel.getCompanyCode(), routeModel.getDistributorCode(), routeModel.getRouteCode(), ""));

        }
        Log.d(TAG, "addRouteDetails : " + jsonArray.toString());
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addSalesmanDetails(String distributorCode) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        SFADatabase sfaDatabase = SFADatabase.getInstance(context);
        List<SalesmanMasterModel> salesmanMasterModelList = sfaDatabase.getAllSalemansForUpload(distributorCode);

        for (SalesmanMasterModel salesmanMasterModel : salesmanMasterModelList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_CMP_CODE, salesmanMasterModel.getCmpCode());
            jsonObject.put(TAG_DISTR_CODE, salesmanMasterModel.getDistrCode());
            jsonObject.put(TAG_SALESMAN_CODE, salesmanMasterModel.getSalesmanCode());
            jsonObject.put(TAG_SALESMAN_NAME, salesmanMasterModel.getSalesmanName());
            jsonObject.put(TAG_MOBILE_NO, salesmanMasterModel.getMobileNo());
            jsonObject.put(TAG_MAIL_ID, salesmanMasterModel.getEmailId());
            jsonObject.put(TAG_SALARY, salesmanMasterModel.getMonthlySalary());
            jsonObject.put(TAG_STATUS, salesmanMasterModel.getSalesmanStatus());
            jsonObject.put(TAG_SSFA_ENABLED, salesmanMasterModel.getSsfaEnabled());
            jsonObject.put(TAG_BANK_NAME, salesmanMasterModel.getBankName());
            jsonObject.put(TAG_ACCOUNT_NUMBER, salesmanMasterModel.getAccNo());
            jsonObject.put(TAG_IFSC_CODE, salesmanMasterModel.getIfscCode());
            jsonObject.put(TAG_SALESMAN_IMAGE, salesmanMasterModel.getSalesmanProfileImage());
            jsonObject.put(TAG_CANCELLED_CHECK_IMAGE, salesmanMasterModel.getCancelledChequeImage());
            jsonObject.put(TAG_UPLOAD_FLAG, salesmanMasterModel.getUpload());
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jsonObject);
            retailerDetailVOs.add(addBasicDetToRetDetVo(salesmanMasterModel.getCmpCode(), salesmanMasterModel.getDistrCode(), salesmanMasterModel.getSalesmanCode(), ""));
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addSalesmanKYC(String distributorCode) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        SFADatabase sfaDatabase = SFADatabase.getInstance(context);
        List<SalesmanMasterModel> salesmanMasterModelList = sfaDatabase.getSalemanKYC(distributorCode);

        for (SalesmanMasterModel salesmanMasterModel : salesmanMasterModelList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_CMP_CODE, salesmanMasterModel.getCmpCode());
            jsonObject.put(TAG_DISTR_CODE, salesmanMasterModel.getDistrCode());
            jsonObject.put(TAG_SALESMAN_CODE, salesmanMasterModel.getSalesmanCode());
            jsonObject.put(TAG_TYPE, salesmanMasterModel.getSelectedKYC());
            jsonObject.put(TAG_IMAGE, salesmanMasterModel.getKycImage());
            jsonObject.put(TAG_UPLOAD_FLAG, salesmanMasterModel.getUpload());
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jsonObject);
            retailerDetailVOs.add(addBasicDetToRetDetVo(salesmanMasterModel.getCmpCode(), salesmanMasterModel.getDistrCode(), salesmanMasterModel.getSalesmanCode(), salesmanMasterModel.getSelectedKYC()));
        }
        return getJsonArrayLength(jsonArray);
    }


    private JSONArray addSalesmanLobMapping(String distributorCode) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        SFADatabase sfaDatabase = SFADatabase.getInstance(context);
        List<SalesmanMasterModel> salesmanMasterModelList = sfaDatabase.getSalesmanLobMapping(distributorCode);

        for (SalesmanMasterModel salesmanMasterModel : salesmanMasterModelList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_CMP_CODE, salesmanMasterModel.getCmpCode());
            jsonObject.put(TAG_DISTR_CODE, salesmanMasterModel.getDistrCode());
            jsonObject.put(TAG_SALESMAN_CODE, salesmanMasterModel.getSalesmanCode());
            jsonObject.put(TAG_LOB_CODE, salesmanMasterModel.getLobCode());
            jsonObject.put(TAG_UPLOAD_FLAG, salesmanMasterModel.getUpload());
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jsonObject);
            retailerDetailVOs.add(addBasicDetToRetDetVo(salesmanMasterModel.getCmpCode(), salesmanMasterModel.getDistrCode(), salesmanMasterModel.getSalesmanCode(), salesmanMasterModel.getLobCode()));
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addSalesmanRouteMapping(String distributorCode) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        SFADatabase sfaDatabase = SFADatabase.getInstance(context);
        List<SalesmanMasterModel> salesmanMasterModelList = sfaDatabase.getSalesmanRouteMapping(distributorCode);

        for (SalesmanMasterModel salesmanMasterModel : salesmanMasterModelList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_CMP_CODE, salesmanMasterModel.getCmpCode());
            jsonObject.put(TAG_DISTR_CODE, salesmanMasterModel.getDistrCode());
            jsonObject.put(TAG_SALESMAN_CODE, salesmanMasterModel.getSalesmanCode());
            jsonObject.put(TAG_ROUTE_CODE, salesmanMasterModel.getRouteCode());
            jsonObject.put(TAG_UPLOAD_FLAG, salesmanMasterModel.getUpload());
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jsonObject);
            retailerDetailVOs.add(addBasicDetToRetDetVo(salesmanMasterModel.getCmpCode(), salesmanMasterModel.getDistrCode(), salesmanMasterModel.getSalesmanCode(), salesmanMasterModel.getRouteCode()));
        }
        return getJsonArrayLength(jsonArray);
    }

    //   Add Code Generator Details
    private JSONArray getAllGeneratedCodesForUpload(String distributorCode, String tableName) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        SFADatabase database = SFADatabase.getInstance(context);

        List<CodeGeneratorModel> codeGeneratorModels = database.getAllGeneratedCodesForUpload(distributorCode, tableName);

        for (CodeGeneratorModel codeGeneratorModel : codeGeneratorModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_LOGIN_CODE, distributorCode);
            jsonObject.put(TAG_SCREEN_NAME, codeGeneratorModel.getScreenName());
            jsonObject.put(TAG_PREFIX, codeGeneratorModel.getPrefix());
            jsonObject.put(TAG_SUFFIX_YY, codeGeneratorModel.getSuffixYy());
            jsonObject.put(TAG_SUFFIX_NN, codeGeneratorModel.getSuffixNn());
            jsonObject.put(TAG_UPLOAD_FLAG, codeGeneratorModel.getUpload());
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jsonObject);
            //retailerDetailVOs.add(addBasicDetToRetDetVo(codeGeneratorModel.getCompanyCode(), codeGeneratorModel.getDistributorCode(), codeGeneratorModel.getPrefix(), ""));

        }
        LoggerUtil.printDebugLog(TAG, "getAllGeneratedCodesForUpload : " + jsonArray.toString());
        return getJsonArrayLength(jsonArray);
    }

    // purchase order booking send to interdb

    private JSONArray addPurchaseOrderBookingDetialsList() throws JSONException {

        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        String distCode = preferences.readString(PREF_DISTRCODE);

        List<OrderBookingVO> orderBookingList = database.getPurchaseOrderBookingLineItem(cmpCode, distCode);
        JSONArray jALineItem = new JSONArray();
        for (OrderBookingVO orderBooking : orderBookingList) {
            JSONObject jObjLineItem = new JSONObject();

            jObjLineItem.put(TAG_CMP_CODE, orderBooking.getCmpCode());
            jObjLineItem.put(TAG_DISTR_CODE, orderBooking.getDistrCode());
            jObjLineItem.put(TAG_PO_REF_NO, orderBooking.getOrderInvoiceNo());
            jObjLineItem.put(TAG_PROD_BATCH_CODE, orderBooking.getProdBatchCode());
            jObjLineItem.put(TAG_PROD_CODE, orderBooking.getProdCode());
            jObjLineItem.put(TAG_PROD_NAME, orderBooking.getProdName());
            jObjLineItem.put(TAG_ORDER_QTY, orderBooking.getQuantity());
            jObjLineItem.put(TAG_PURCHASE_RATE, orderBooking.getPurchasePrice());
            jObjLineItem.put(TAG_UOM_CODE, orderBooking.getUomId());
            jObjLineItem.put(TAG_ORDER_VALUE, orderBooking.getOrderValue());
            jObjLineItem.put(TAG_TAX_AMT, orderBooking.getTax());
            jObjLineItem.put(TAG_CGST_PERC, orderBooking.getCgstperc());
            jObjLineItem.put(TAG_CGST_VALUE, orderBooking.getCgstvalue());
            jObjLineItem.put(TAG_SGST_PERC, orderBooking.getSgstPerc());
            jObjLineItem.put(TAG_SGST_VALUE, orderBooking.getSgstValue());
            jObjLineItem.put(TAG_UTGST_PERC, orderBooking.getUtgstPerc());
            jObjLineItem.put(TAG_UTGST_VALUE, orderBooking.getUtgstValue());
            jObjLineItem.put(TAG_IGST_PERC, orderBooking.getIgstPerc());
            jObjLineItem.put(TAG_IGST_VALUE, orderBooking.getIgstvalue());
            jObjLineItem.put(TAG_PO_DATE, orderBooking.getOrderDate());
            jObjLineItem.put(TAG_MOD_DT, System.currentTimeMillis());
            jObjLineItem.put(TAG_UPLOAD_FLAG, "N");
            jALineItem.put(jObjLineItem);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, orderBooking.getOrderInvoiceNo(), orderBooking.getProdCode(), orderBooking.getProdBatchCode());
            retailerDetailVOs.add(retailerDetailVO);
        }

        return getJsonArrayLength(jALineItem);
    }

    private JSONArray addPendingPurchaseDetails() {
        SFADatabase database = SFADatabase.getInstance(context);
        JSONArray jsonArray = new JSONArray();

        List<PurchaseInvoiceModel> codeGeneratorModels = database.getPurchaseOrderBookingLineItemForUpload();

        for (PurchaseInvoiceModel purchaseInvoiceModel : codeGeneratorModels) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(TAG_CMP_CODE, purchaseInvoiceModel.getCmpCode());
                jsonObject.put(TAG_DISTR_CODE, purchaseInvoiceModel.getDistrCode());
                jsonObject.put(COLUMN_INVOICE_NO, purchaseInvoiceModel.getInvoiceNo());
                jsonObject.put(COLUMN_GRN_NO, purchaseInvoiceModel.getGrnNo());
                jsonObject.put(COLUMN_INVOICE_DT, purchaseInvoiceModel.getInvoiceDt());
                jsonObject.put(COLUMN_GRN_DATE, purchaseInvoiceModel.getGrnDt());
                jsonObject.put(COLUMN_SUPPLIER_CODE, purchaseInvoiceModel.getSupplierCode());
                jsonObject.put(COLUMN_PROD_CODE, purchaseInvoiceModel.getProdCode());
                jsonObject.put(PROD_BATCH_CODE, purchaseInvoiceModel.getProdBatchCode());
                jsonObject.put(COLUMN_PURCH_PRICE, purchaseInvoiceModel.getPurchPrice());
                jsonObject.put(COLUMN_MRP, purchaseInvoiceModel.getMrp());
                jsonObject.put(COLUMN_INVOICE_QTY, purchaseInvoiceModel.getInvoiceQty());
                jsonObject.put(COLUMN_RECEIVED_QTY, purchaseInvoiceModel.getReceivedQty());
                jsonObject.put(COLUMN_UOM_CODE, purchaseInvoiceModel.getUomCode());
                jsonObject.put(COLUMN_LINE_LEVEL_GROSS_AMT, purchaseInvoiceModel.getLineLevelGrossAmt());
                jsonObject.put(COLUMN_LINE_LEVEL_TAX_AMT, purchaseInvoiceModel.getLineLevelTaxAmt());
                jsonObject.put(COLUMN_LINE_LEVEL_DISC_AMT, purchaseInvoiceModel.getLineLevelDiscAmt());
                jsonObject.put(COLUMN_LINE_LEVEL_NET_AMT, purchaseInvoiceModel.getLineLevelNetAmt());
                jsonObject.put(COLUMN_GRN_STATUS, purchaseInvoiceModel.getGrnStatus());
                jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());
                jsonObject.put(UPLOAD_FLAG, purchaseInvoiceModel.getUploadFlag());
            } catch (JSONException e) {
                LoggerUtil.printErrorLog(TAG, "addPendingPurchaseDetails : " + e.getMessage());
            }
            jsonArray.put(jsonObject);
            retailerDetailVOs.add(addBasicDetToRetDetVo(purchaseInvoiceModel.getCmpCode(), purchaseInvoiceModel.getInvoiceNo(),
                    purchaseInvoiceModel.getProdCode(), purchaseInvoiceModel.getProdBatchCode()));

        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addAvailableStockOnHand() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        String distCode = preferences.readString(PREF_DISTRCODE);

        List<StockOnHandModel> stockList = database.getAvailStockOnHandForUpload(cmpCode, distCode);

        for (StockOnHandModel stock : stockList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(COLUMN_CMP_CODE, stock.getCmpCode());
            jsonObject.put(COLUMN_DISTR_CODE, stock.getDistrCode());
            jsonObject.put(COLUMN_PROD_CODE, stock.getProdCode());
            jsonObject.put(TAG_PROD_BATCH_CODE, stock.getProdBatchCode());
            jsonObject.put(COLUMN_GODOWN_CODE, stock.getGodownCode());
            jsonObject.put(COLUMN_SALEABLE_QTY, stock.getAvailQty());
            jsonObject.put(COLUMN_UNSALEABLE_QTY, stock.getUnSaleableQty());
            jsonObject.put(COLUMN_OFFER_QTY, stock.getOfferQty());
            jsonObject.put(COLUMN_RESV_SALEABLE_QTY, stock.getResvSaleableQty());
            jsonObject.put(COLUMN_RESV_UNSALEABLE_QTY, stock.getResvUnSaleableQty());
            jsonObject.put(COLUMN_RESV_OFFER_QTY, stock.getResvOfferQty());
            jsonObject.put(COLUMN_MOD_DT, System.currentTimeMillis());
            jsonObject.put(UPLOAD_FLAG, stock.getUploadFlag());
            jsonArray.put(jsonObject);

            retailerDetailVOs.add(addBasicDetToRetDetVo(stock.getCmpCode(), stock.getDistrCode(),
                    stock.getProdCode(), stock.getProdBatchCode()));

        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addLogisticMaterialStock() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        SFADatabase database = SFADatabase.getInstance(context);

        List<LogisticStockModel> logisticStockModels = database.getLogisticStockForUpload();
        for (LogisticStockModel logisticStockModel : logisticStockModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_CMP_CODE, logisticStockModel.getCmpCode());
            jsonObject.put(TAG_DISTR_CODE, logisticStockModel.getDistrCode());
            jsonObject.put(COLUMN_INVOICE_NO, logisticStockModel.getInvoiceNo());
            jsonObject.put(COLUMN_MATERIAL_CODE, logisticStockModel.getMaterialCode());
            jsonObject.put(COLUMN_MATERIAL_NAME, logisticStockModel.getMaterialName());
            jsonObject.put(COLUMN_RECEIVED_QTY, logisticStockModel.getReceivedQty());
            jsonObject.put(COLUMN_RETURNED_QTY, logisticStockModel.getReturnedQty());
            jsonObject.put(TAG_UPLOAD_FLAG, logisticStockModel.getUploadFlag());
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jsonObject);
            retailerDetailVOs.add(addBasicDetToRetDetVo(logisticStockModel.getDistrCode(), logisticStockModel.getInvoiceNo(),
                    "", ""));

        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addSalesmanRouteCoverages() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        SFADatabase database = SFADatabase.getInstance(context);

        List<SalesmanRouteCoverageModel> modelList = database.getRouteCoverageForUpload();
        for (SalesmanRouteCoverageModel model : modelList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_CMP_CODE, model.getCmpCode());
            jsonObject.put(TAG_DISTR_CODE, model.getDistrCode());
            jsonObject.put(COLUMN_SALESMAN_CODE, model.getSalesmanCode());
            jsonObject.put(COLUMN_ROUTE_CODE, model.getRouteCode());
            jsonObject.put(COLUMN_COVERAGE_DT, model.getCoverageDt());
            jsonObject.put(TAG_UPLOAD_FLAG, "N");
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jsonObject);
            retailerDetailVOs.add(addBasicDetToRetDetVo(model.getCmpCode(), model.getDistrCode(), model.getSalesmanCode(),
                    model.getCoverageDt()));
        }
        return getJsonArrayLength(jsonArray);
    }


    private JSONArray addSchemeDistrBudgetDetails() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        SFADatabase database = SFADatabase.getInstance(context);

        SFASharedPref pref = SFASharedPref.getOurInstance();
        List<SchemeDistrBudgetModel> routeModels = database.getSchemeDistrBudgetForUpload(pref.readString(PREF_DISTRCODE));

        for (SchemeDistrBudgetModel routeModel : routeModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_CMP_CODE, routeModel.getCmpCode());
            jsonObject.put(TAG_DISTR_CODE, routeModel.getDistrCode());
            jsonObject.put(SCHEME_CODE, routeModel.getSchemeCode());
            jsonObject.put(COLUMN_IS_UNLIMITED, routeModel.getIsUnlimited());
            jsonObject.put(COLUMN_BUDGET, routeModel.getBudget());
            jsonObject.put(COLUMN_IS_ACTIVE, routeModel.getIsActive());
            jsonObject.put(COLUMN_UTILIZED_AMT, routeModel.getUtilizedAmount());
            jsonObject.put(COLUMN_BUDGET_OS, routeModel.getBudgetOs());
            jsonObject.put(TAG_UPLOAD_FLAG, routeModel.getUploadFlag());
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jsonObject);
            //retailerDetailVOs.add(addBasicDetToRetDetVo(routeModel.getCmpCode(), routeModel.getDistrCode(), "", routeModel.getSchemeCode()));

        }
        Log.d(TAG, "addSchemeDistrBudgetDetails : " + jsonArray.toString());
        return getJsonArrayLength(jsonArray);
    }

    private void updateCodeGeneratorHistoryStatus(SFADatabase database, JSONObject jsonObj) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_CODE_GENERATOR_HISTORY_STATUS_LIST) &&
                jsonObj.get(TAG_CODE_GENERATOR_HISTORY_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_CODE_GENERATOR_HISTORY_STATUS_LIST);
            for (int i = 0; i < array.length(); i++) {
                String uploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_CODE_GENERATOR_HISTORY_STATUS_LIST)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distrCode = object.getString(TAG_DISTR_CODE);
                    String screenName = object.getString(COLUMN_SCREEN_NAME);
                    String prefix = object.getString(TAG_PREFIX);
                    String suffixYy = object.getString(TAG_SUFFIX_YY);
                    database.updateCodeGeneratorHistoryStatus(distrCode, screenName, prefix,suffixYy);
//                    removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distrCode, "", salesReturnNo));
                }
            }
        }
    }

    private void updateSchemeDistrBudget(SFADatabase database, JSONObject jsonObj) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_SCHEME_DISTR_BUDGET_STATUS) &&
                jsonObj.get(TAG_SCHEME_DISTR_BUDGET_STATUS) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_SCHEME_DISTR_BUDGET_STATUS);
            for (int i = 0; i < array.length(); i++) {
                String uploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_CMP_CODE) && isJsonNotNull(object, TAG_DISTR_CODE)
                        && (isJsonNotNull(object, TAG_SCHEME_CODE))) {

                    String schemeCode = object.getString(TAG_SCHEME_CODE);
                    String cmpCode = object.getString(TAG_CMP_CODE);
                    String distrCode = object.getString(TAG_DISTR_CODE);
                    uploaded = "Y";
                    database.updateSchemeDistrBudget(cmpCode, distrCode, schemeCode, TABLE_SCHEME_DISTRIBUTOR_BUDGET, UPLOAD_FLAG, uploaded);
                    removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distrCode, "", schemeCode));
                }
            }
        }
    }

    private JSONArray addTempRouteDetails(String distributorCode) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        SFADatabase database = SFADatabase.getInstance(context);

        List<RouteModel> routeModels = database.getTempRoutesForUpload(distributorCode);

        for (RouteModel routeModel : routeModels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_CMP_CODE, routeModel.getCompanyCode());
            jsonObject.put(TAG_DISTR_CODE, routeModel.getDistributorCode());
            jsonObject.put(COLUMN_TEMP_ROUTE_CODE, routeModel.getTempRouteCode());
            jsonObject.put(TAG_ROUTE_NAME, routeModel.getRouteName());
            jsonObject.put(TAG_ROUTE_TYPE, routeModel.getRouteType());
            jsonObject.put(TAG_ROUTE_CODE, routeModel.getRouteCode());
            jsonObject.put(TAG_UPLOAD_FLAG, routeModel.getUploadFlag());
            jsonObject.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jsonObject);
            retailerDetailVOs.add(addBasicDetToRetDetVo(routeModel.getCompanyCode(), routeModel.getDistributorCode(), routeModel.getTempRouteCode(), ""));

        }
        Log.d(TAG, "addRouteDetails : " + jsonArray.toString());
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addTempRetailerMasterInfo() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        JSONArray jsonArray = new JSONArray();
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<RetailerVO> retailerList = database.getTempRetailerList(cmpCode, distCode);

        for (RetailerVO retailerVO : retailerList) {
            JSONObject jObj = new JSONObject();
            jObj.put(TAG_CMP_CODE, cmpCode);
            jObj.put(TAG_DISTR_CODE, distCode);
            jObj.put(COLUMN_TEMP_CUSTOMER_CODE, retailerVO.getTempCustomerCode());
//            jObj.put(TAG_COMPANY_CUSTOMER_CODE, retailerVO.getCmpCustomerCode());
            jObj.put(TAG_CUSTOMER_NAME, retailerVO.getCustomerName());
            jObj.put(TAG_PINCODE, retailerVO.getPostalCode());
            jObj.put(TAG_PHONE_NO, retailerVO.getPhoneNo());
            jObj.put(TAG_MOBILE_NO, retailerVO.getMobile());
            jObj.put(TAG_CONTACT_PERSON, retailerVO.getContactPerson());
            jObj.put(TAG_EMAIL, retailerVO.getEmail());
            jObj.put(TAG_DAYOFF, retailerVO.getSpinDayOffCount());
            jObj.put(TAG_RETAILER_STATUS, retailerVO.getRetailerStatus());
            jObj.put(TAG_FSSAI_NO, retailerVO.getFfsaLcNo());
            jObj.put(TAG_DRUG_LIC_NO, retailerVO.getDrugLcNo());
            jObj.put(TAG_DRUG_EXPIRY_DATE, retailerVO.getExpiryDateTimeStamp());
            jObj.put(TAG_CREDIT_BILLS, retailerVO.getCrBills());
            jObj.put(TAG_CREDIT_DAYS, retailerVO.getCrDays());
            jObj.put(TAG_CREDIT_LIMIT, retailerVO.getCrLimit());
            jObj.put(TAG_CASH_DISC_PERC, retailerVO.getCashDisc());
            jObj.put(TAG_CHANNEL_CODE, retailerVO.getChannelCode());
            jObj.put(TAG_SUB_CHANNEL_CODE, retailerVO.getSubChannelCode());
            jObj.put(TAG_GROUP_CODE, retailerVO.getGroupCode());
            jObj.put(TAG_CLASS_CODE, retailerVO.getClassCode());
            jObj.put(TAG_STORE_TYPE, retailerVO.getRelationStatus());
            jObj.put(TAG_PARENT_CUSTOMER_CODE, retailerVO.getParentMap());
            jObj.put(TAG_LATITUDE, retailerVO.getLatitude());
            jObj.put(TAG_LONGITUDE, retailerVO.getLongitude());
            jObj.put(TAG_CUSTOMER_TYPE, retailerVO.getGstType());
            jObj.put(TAG_GSTNO, retailerVO.getGstNo());
            jObj.put(TAG_PANNO, retailerVO.getPanNo());
            jObj.put(TAG_APPROVAL_STATUS, retailerVO.getApprovalStatus());
            jObj.put(TAG_CUSTOMER_CODE, retailerVO.getCustomerCode());
            jObj.put(TAG_UPLOAD_FLAG, "N");
            jObj.put(TAG_MOD_DT, System.currentTimeMillis());

            jsonArray.put(jObj);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", "", retailerVO.getTempCustomerCode());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addTempRetailerShipAddress() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);

        List<AddressModel> addrList = database.getTempRetailerShipAddrList(cmpCode, distCode);

        JSONArray jsonArray = new JSONArray();

        for (AddressModel addressModel : addrList) {
            JSONObject jObjLineItem = new JSONObject();
            jObjLineItem.put(TAG_CMP_CODE, cmpCode);
            jObjLineItem.put(TAG_DISTR_CODE, distCode);
            jObjLineItem.put(TAG_CUSTOMER_CODE, addressModel.getCustomerCode());
//            jObjLineItem.put(TAG_CUSTOMER_SHIP_CODE, addressModel.getShipCode());
            jObjLineItem.put(COLUMN_TEMP_CUSTOMER_CODE, addressModel.getTempCustomerCode());
            jObjLineItem.put(COLUMN_TEMP_CUSTOMER_SHIP_CODE, addressModel.getTempShipCode());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_ADDR1, addressModel.getShippingAddress1());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_ADDR2, addressModel.getShippingAddress2());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_ADDR3, addressModel.getShippingAddress3());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_CITY, addressModel.getShipCityTown());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_GEOHIERPATH, addressModel.getShippingGeoHierPath());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_GSTSTATECODE, addressModel.getShippingState());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_PHONENO, addressModel.getShippingPhoneNumber());
            jObjLineItem.put(TAG_CUSTOMER_SHIP_DEF_ADDR, addressModel.getIsDefault());
            jObjLineItem.put(TAG_UPLOAD_FLAG, "N");
            jObjLineItem.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jObjLineItem);


            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", addressModel.getTempShipCode(), addressModel.getTempCustomerCode());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private JSONArray addTempRetailerRoute() throws JSONException {
        SFADatabase database = SFADatabase.getInstance(context);
        SFASharedPref preferences = SFASharedPref.getOurInstance();
        String distCode = preferences.readString(PREF_DISTRCODE);
        String cmpCode = preferences.readString(PREF_CMP_CODE);
        List<RouteModel> routeList = database.getTempRetailerRouteList(cmpCode, distCode);

        JSONArray jsonArray = new JSONArray();

        for (RouteModel routeModel : routeList) {
            JSONObject jObjLineItem = new JSONObject();
            jObjLineItem.put(TAG_CMP_CODE, cmpCode);
            jObjLineItem.put(TAG_DISTR_CODE, distCode);
            jObjLineItem.put(TAG_CUSTOMER_CODE, routeModel.getCustomerCode());
            jObjLineItem.put(TAG_ROUTE_CODE, routeModel.getRouteCode());
            jObjLineItem.put(COLUMN_TEMP_CUSTOMER_CODE, routeModel.getTempCustomerCode());
            jObjLineItem.put(COLUMN_TEMP_ROUTE_CODE, routeModel.getTempRouteCode());
            jObjLineItem.put(TAG_COVERAGE_SEQ, routeModel.getCoverageSeq());
            jObjLineItem.put(TAG_UPLOAD_FLAG, "N");
            jObjLineItem.put(TAG_MOD_DT, System.currentTimeMillis());
            jsonArray.put(jObjLineItem);

            RetailerDetailVO retailerDetailVO = addBasicDetToRetDetVo(distCode, "", routeModel.getTempRouteCode(), routeModel.getTempCustomerCode());
            retailerDetailVOs.add(retailerDetailVO);
        }
        return getJsonArrayLength(jsonArray);
    }

    private void updateTempRouteStatus(SFADatabase database, JSONObject jsonObj) {
        SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
        String distCode = preferences.getString(PREF_DISTRCODE, "");
        String cmpCode = preferences.getString(PREF_CMP_CODE, "");

        try {
            if (isJsonNotNull(jsonObj, TAG_TEMP_ROUTE_STATUS) &&
                    jsonObj.get(TAG_TEMP_ROUTE_STATUS) instanceof JSONArray) {
                JSONArray jsonArray = jsonObj.getJSONArray(TAG_TEMP_ROUTE_STATUS);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, TAG_DISTR_CODE) && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                        String uploaded = "N";
                        String routeCode = object.getString(COLUMN_TEMP_ROUTE_CODE);
                        String[] parameters = new String[]{cmpCode, distCode, routeCode};
                        String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_TEMP_ROUTE_CODE};
                        database.updateUploadStatus(TABLE_TEMP_ROUTE, uploaded, parameters, columns);

                        removeRetailerDetailsVo(addBasicDetToRetDetVo(cmpCode, distCode, routeCode, ""));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updateDistributorStatus: " + e.getMessage(), e);
        }
    }

    private void updateTempCustomerStatus(SFADatabase database, JSONObject jsonObj, String distCode) throws JSONException {

        if (isJsonNotNull(jsonObj, TAG_TEMP_CUSTOMER_UPLOAD_STATUS_LIST) &&
                jsonObj.get(TAG_TEMP_CUSTOMER_UPLOAD_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_TEMP_CUSTOMER_UPLOAD_STATUS_LIST);
            SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
            String cmpCode = preferences.getString(PREF_CMP_CODE, "");
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, COLUMN_TEMP_CUSTOMER_CODE)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String customerCode = object.getString(COLUMN_TEMP_CUSTOMER_CODE);
                    orderUploaded = "N";
                    String[] parameters = new String[]{cmpCode, distCode, customerCode};
                    String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_TEMP_CUSTOMER_CODE};
                    database.updateUploadStatus(TABLE_TEMP_CUSTOMER, orderUploaded, parameters, columns);
                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", "", customerCode));
                }
            }
        }
    }

    private void updateTempCustomerRouteStatus(SFADatabase database, JSONObject jsonObj, String distCode) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_TEMP_CUSTOMER_ROUTE_MAPPING_STATUS_LIST) &&
                jsonObj.get(TAG_TEMP_CUSTOMER_ROUTE_MAPPING_STATUS_LIST) instanceof JSONArray) {
            JSONArray array = jsonObj.getJSONArray(TAG_TEMP_CUSTOMER_ROUTE_MAPPING_STATUS_LIST);
            SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
            String cmpCode = preferences.getString(PREF_CMP_CODE, "");
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, COLUMN_TEMP_CUSTOMER_CODE)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String customerCode = object.getString(COLUMN_TEMP_CUSTOMER_CODE);
                    String routeCode = object.getString(COLUMN_TEMP_ROUTE_CODE);
                    orderUploaded = "N";

                    String[] parameters = new String[]{cmpCode, distCode, customerCode, routeCode};
                    String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_TEMP_CUSTOMER_CODE, COLUMN_TEMP_ROUTE_CODE};
                    database.updateUploadStatus(TABLE_TEMP_CUSTOMER_ROUTE, orderUploaded, parameters, columns);
                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", routeCode, customerCode));
                }
            }
        }
    }

    private void updateTempCustomerShipAddressStatus(SFADatabase database, JSONObject jsonObj, String distCode) throws JSONException {
        if (isJsonNotNull(jsonObj, TAG_TEMP_CUSTOMER_SHIP_ADDRESS_STATUS_LIST) &&
                jsonObj.get(TAG_TEMP_CUSTOMER_SHIP_ADDRESS_STATUS_LIST) instanceof JSONArray) {
            SharedPreferences preferences = SFASharedPref.getSharedPreferences(context);
            String cmpCode = preferences.getString(PREF_CMP_CODE, "");
            JSONArray array = jsonObj.getJSONArray(TAG_TEMP_CUSTOMER_SHIP_ADDRESS_STATUS_LIST);
            for (int i = 0; i < array.length(); i++) {
                String orderUploaded;

                JSONObject object = array.getJSONObject(i);
                if (isJsonNotNull(object, TAG_UPLOAD_STATUS) && isJsonNotNull(object, COLUMN_TEMP_CUSTOMER_CODE)
                        && (object.getBoolean(TAG_UPLOAD_STATUS))) {
                    String customerCode = object.getString(COLUMN_TEMP_CUSTOMER_CODE);
                    String customerShipCode = object.getString(COLUMN_TEMP_CUSTOMER_SHIP_CODE);
                    orderUploaded = "N";

                    String[] parameters = new String[]{cmpCode, distCode, customerCode, customerShipCode};
                    String[] columns = new String[]{COLUMN_CMP_CODE, COLUMN_DISTR_CODE, COLUMN_TEMP_CUSTOMER_CODE, COLUMN_TEMP_CUSTOMER_SHIP_CODE};
                    database.updateUploadStatus(TABLE_TEMP_CUSTOMER_SHIP_ADDRESS, orderUploaded, parameters, columns);
                    removeRetailerDetailsVo(addBasicDetToRetDetVo(distCode, "", customerShipCode, customerCode));
                }
            }
        }
    }
}
