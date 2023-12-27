/*
 * Copyright (C) 2016 Botree Software International Private Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.botree.retailerssfa.main;

import android.app.Dialog;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.OrderSummaryAdapter;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.db.query.CodeGeneratorQueryHelper;
import com.botree.retailerssfa.fragmentmanager.ESFAFragTags;
import com.botree.retailerssfa.fragmentmanager.SFAFragmentManager;
import com.botree.retailerssfa.models.CodeGeneratorModel;
import com.botree.retailerssfa.models.FreeProdModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.print.BTWrapperActivity;
import com.botree.retailerssfa.print.PrintGlobals;
import com.botree.retailerssfa.print.Printing;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.OrderSupportUtil;
import com.botree.retailerssfa.util.SFALocation;
import com.botree.retailerssfa.util.SFALocationUpdate;
import com.botree.retailerssfa.util.SFASharedPref;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_IS_PTR_WITH_TAX;
import static com.botree.retailerssfa.controller.constants.AppConstant.USER_TYPE_ISR;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_BOOKING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_CONFIRMATION;
import static com.botree.retailerssfa.support.Globals.NAME_ORDER_BOOKING;
import static com.botree.retailerssfa.support.Globals.NAME_PREVIOUS_ORDERS_CONFIRMATION;
import static com.botree.retailerssfa.support.Globals.NAME_STOCK_TAKE;
import static com.botree.retailerssfa.support.Globals.ORDER_BOOKING_NAME;
import static com.botree.retailerssfa.support.Globals.SCREEN_NAME_ROUTE;
import static com.botree.retailerssfa.util.NotifyUtil.showSnackBar;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LAST_LAT;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LAST_LONG;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_ROUTECODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_USER_TYPE;


public class RetailerOrderSummaryFragment extends SFAFragment implements
        View.OnClickListener, SFALocationUpdate {

    private static final String TAG = RetailerOrderSummaryFragment.class.getSimpleName();
    private BluetoothSocket mbtSocket;
    private OutputStream mbtOutputStream;
    private OrderSummaryAdapter orderSummaryAdapter;
    private RecyclerView recyclerView;
    private TextView emptyText;
    private TextView orderSummaryTotalTxt;
    private TextView msg = null;
    private CoordinatorLayout summaryParent;
    private String retlrCode;
    private String retilerName;
    private int newRetailer = 0;
    private SFADatabase db;
    private SFASharedPref preferences;
    private List<OrderBookingVO> orderBookingVOList;
    private int deletePos = 0;
    private boolean isDeleteDialogShowing = false;
    private LinearLayoutManager linearLayoutManager;
    private String screenName = NAME_ORDER_BOOKING;
    private TextView totalDiscountTxt;
    private TextView totalTaxTxt;
    private List<List<SchemeModel>> schemeOrdersList = new ArrayList<>();
    private List<List<FreeProdModel>> freeProdList = new ArrayList<>();
    private String readableInvNo;

    private boolean isLocationReady;
    private SFALocation sfaLocation;

    private NotifyUtil.NotifyListener deleteDialogListener = new NotifyUtil.NotifyListener() {
        @Override
        public void onOkClicked() {

            if (orderBookingVOList != null) {


                OrderBookingVO orderBookingVO = orderBookingVOList.get(deletePos);

                SFADatabase database = SFADatabase.getInstance(getSFAFragmentActivity());

                if (newRetailer == 3) {

                    database.deleteStockTakeProductById(
                            preferences.readString(PREF_DISTRCODE),
                            preferences.readString(PREF_SALESMANCODE),
                            preferences.readString(PREF_ROUTECODE),
                            retlrCode, orderBookingVO);

                } else {

                    database.deleteProductById(orderBookingVO.getDistrCode(),
                            orderBookingVO.getCmpCode(),
                            preferences.readString(PREF_ROUTECODE),
                            orderBookingVO.getRetailerCode(), orderBookingVO);
                    database.deleteExistFreeProductById(orderBookingVO.getDistrCode(),
                            orderBookingVO.getCmpCode(),
                            preferences.readString(PREF_ROUTECODE),
                            orderBookingVO.getRetailerCode(), orderBookingVO, TABLE_ORDER_BOOKING);

//                    database.deleteStockTakeProductById(preferences.readString(PREF_DISTRCODE),
//                            preferences.readString(PREF_SALESMANCODE),
//                            preferences.readString(PREF_ROUTECODE),
//                            retlrCode, orderBookingVO);
                }


                orderBookingVOList.remove(deletePos);
                if(deletePos< schemeOrdersList.size()) {
                    schemeOrdersList.remove(deletePos);
                    freeProdList.remove(deletePos);
                }
                orderSummaryAdapter.notifyItemRemoved(deletePos);
                orderSummaryAdapter.notifyDataSetChanged();
                calculateOrderTotal(orderBookingVOList, orderSummaryTotalTxt);
                timerDelayShowCount();
                isDeleteDialogShowing = false;
            }

        }

        private void timerDelayShowCount() {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    try {
                        String count = String.valueOf(orderBookingVOList.size());
                        msg.setText(count);

                    } catch (Exception e) {
                        Log.d("update", e.getMessage(), e);
                    }
                }
            }, 100);
        }

        @Override
        public void onCancelClicked() {
            isDeleteDialogShowing = false;
        }
    };
    private boolean isPTRWithTax = false;

    public RetailerOrderSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        db = SFADatabase.getInstance(getActivity());
        preferences = SFASharedPref.getOurInstance();
        setRetainInstance(true);
        sfaLocation = new SFALocation(getSFAFragmentActivity(),
                getSFAFragmentActivity().getSavedInstanceBundle(), this);
        if (getArguments() != null) {
            retlrCode = getArguments().getString("retailerCode");
            retilerName = getArguments().getString("retailerName");
            newRetailer = getArguments().getInt("NewRetailer");
            screenName = getArguments().getString("screenType");
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer_order_summary, container, false);

        initialize(view);

        if (orderBookingVOList != null && !orderBookingVOList.isEmpty()) {
            setOrderSummaryAdapter(screenName);
            calculateOrderTotal(orderBookingVOList, orderSummaryTotalTxt);
        } else {
            loadOrderBookingData();
        }
        if (isDeleteDialogShowing)
            showDeleteDialog();

        return view;
    }

    private void showSingPad() {

        final Dialog dialog = new Dialog(getSFAFragmentActivity(), R.style.ThemeDialogCustom);
        if (dialog.isShowing()) return;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.sign_pad_dialog);
        final Button mSaveButton = dialog.findViewById(R.id.sign_save_button);
        final Button mClearButton = dialog.findViewById(R.id.sign_clear_button);
        final ImageView closePad = dialog.findViewById(R.id.close_pad_img);
        final TextView grossTotal = dialog.findViewById(R.id.sign_pad_gross_total_txt);

        final SignaturePad mSignaturePad = dialog.findViewById(R.id.signature_pad);

        grossTotal.setText(String.valueOf("Total : " + getResources().getString(R.string.Rs) + " " + orderSummaryTotalTxt.getText().toString()));

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //ignored
            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });
        closePad.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bitmap newBitmap;
                Bitmap bmp = mSignaturePad.getSignatureBitmap();

                if (bmp != null) {

                    newBitmap = Bitmap.createScaledBitmap(bmp, 120, 120, true);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();
                    String base64img = Base64.encodeToString(b, Base64.DEFAULT);

                    bmp.recycle();
                    if (base64img != null && !base64img.isEmpty()) {
                        updateRetailerSign(base64img);
                    }
                }

                dialog.dismiss();
            }
        });

        mClearButton.setOnClickListener(v -> mSignaturePad.clear());

        dialog.show();

    }

    private void updateRetailerSign(String base64img) {

        if (newRetailer == 1) {

            if (db.updateNewRetailerSignature(preferences.readString(PREF_DISTRCODE),
                    preferences.readString(PREF_SALESMANCODE),
                    preferences.readString(PREF_ROUTECODE),
                    retlrCode, base64img)) {

                showSnackBar(getSFAFragmentActivity(), summaryParent, "Signature saved", Snackbar.LENGTH_SHORT);

            } else {

                showSnackBar(getSFAFragmentActivity(), summaryParent, "Signature not save", Snackbar.LENGTH_SHORT);
            }

        } else {

            if (db.updateRetailerSignature(preferences.readString(PREF_DISTRCODE),
                    preferences.readString(PREF_SALESMANCODE),
                    preferences.readString(PREF_ROUTECODE),
                    retlrCode, base64img)) {

                showSnackBar(getSFAFragmentActivity(), summaryParent, "Signature saved", Snackbar.LENGTH_SHORT);

            } else {

                showSnackBar(getSFAFragmentActivity(), summaryParent, "Signature not save", Snackbar.LENGTH_SHORT);
            }
        }
    }

    public void showDeleteDialog() {
        String prodShortName = orderBookingVOList.get(deletePos).getProdShortName();

        String text = "<font color=#3B4463>Are you sure you want to delete this entry ?</font> <font color=#FF8075>" + prodShortName + "</font>";

        NotifyUtil.showDialog(getSFAFragmentActivity(), "Delete",
                text, deleteDialogListener, "Yes", "No");

    }


    private void initialize(View view) {
        LinearLayout taxDiscountLayout = view.findViewById(R.id.discount_tax_layout);
        Button signBtn = view.findViewById(R.id.signature_btn);
        TextView retailerNameTxt = view.findViewById(R.id.ordersummary_retailer_name_title);
        TextView prtLabelTxt = view.findViewById(R.id.order_summary_ptr_txt);
        orderSummaryTotalTxt = view.findViewById(R.id.order_summary_total_txt);
        totalDiscountTxt = view.findViewById(R.id.order_summary_total_discount_txt);
        totalTaxTxt = view.findViewById(R.id.order_summary_total_tax_txt);
        emptyText = view.findViewById(R.id.retailer_ordersummary_empty_tag_txt);
        recyclerView = view.findViewById(R.id.retailer_ordersummary_recyclerview);
        Button submitBtn = view.findViewById(R.id.retailer_ordsummary_submit_btn);
        Button printBtn = view.findViewById(R.id.printer_btn);
        summaryParent = view.findViewById(R.id.summary_parent);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        printBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
//        retailerNameTxt.setText(retilerName.toUpperCase(Locale.getDefault()));
        signBtn.setOnClickListener(this);

        if (newRetailer == 3) {
            taxDiscountLayout.setVisibility(View.INVISIBLE);
        }

        if (NAME_STOCK_TAKE.equalsIgnoreCase(screenName)) {
            signBtn.setVisibility(View.GONE);
        }

        if ("Y".equalsIgnoreCase(db.getConfigDataBasedOnName(CONFIG_IS_PTR_WITH_TAX))) {
            isPTRWithTax = true;
        }

        if (isPTRWithTax) {
            prtLabelTxt.setText(getResources().getString(R.string.ptr_with_tax));
        }
    }

    /**
     * load the order booking details in recyclerview adapter
     *
     * @param screenName calling screen name
     */
    private void setOrderSummaryAdapter(String screenName) {

        schemeOrdersList.clear();
        freeProdList.clear();
//        if (isPreviousOrderTrue(screenName)) {
//            for (OrderBookingVO orderBookingVO : orderBookingVOList) {
//                schemeOrdersList.add(db.getSingleSchemeProdList(preferences.readString(PREF_DISTRCODE),
//                        preferences.readString(PREF_SALESMANCODE),
//                        preferences.readString(PREF_ROUTECODE), retlrCode, orderBookingVO,
//                        TABLE_ORDER_CONFIRMATION));
//
//            }
//            for (OrderBookingVO orderBookingVO : orderBookingVOList) {
//                freeProdList.add(db.fetchSingleProdFreeProds(preferences.readString(PREF_DISTRCODE), preferences.readString(PREF_SALESMANCODE),
//                        preferences.readString(PREF_ROUTECODE), retlrCode, orderBookingVO, TABLE_ORDER_CONFIRMATION));
//                setFreeProdAvailable(freeProdList, orderBookingVO);
//            }
//        } else {
            for (OrderBookingVO orderBookingVO : orderBookingVOList) {
                schemeOrdersList.add(db.getSingleSchemeProdList(orderBookingVO.getDistrCode(),
                        orderBookingVO.getCmpCode(),
                        preferences.readString(PREF_ROUTECODE), orderBookingVO.getRetailerCode(), orderBookingVO,
                        TABLE_ORDER_BOOKING));
            }
            for (OrderBookingVO orderBookingVO : orderBookingVOList) {
                freeProdList.add(db.fetchSingleProdFreeProds(preferences.readString(PREF_DISTRCODE), preferences.readString(PREF_SALESMANCODE),
                        preferences.readString(PREF_ROUTECODE), retlrCode, orderBookingVO, TABLE_ORDER_BOOKING));
                if (!freeProdList.isEmpty()) {
                    orderBookingVO.setFreeProdAvail(true);
                }
            }
//        }

        filterSchemeData();

        orderSummaryAdapter = new OrderSummaryAdapter(getActivity(), orderBookingVOList,
                schemeOrdersList, newRetailer, freeProdList, linearLayoutManager, screenName);

        if (orderSummaryAdapter.getItemCount() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            recyclerView.setAdapter(orderSummaryAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        }

        orderSummaryAdapter.setOnItemClickListener(new OrderSummaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                deletePos = position;
                isDeleteDialogShowing = true;
                showDeleteDialog();
            }
        });

    }

    private void filterSchemeData() {
        try {
            List<List<SchemeModel>> tempList = new ArrayList<>(schemeOrdersList);

            for (int i = 0; i < schemeOrdersList.size(); i++) {
                List<SchemeModel> schemeModels = schemeOrdersList.get(i);
                for (int j = 0; j < schemeModels.size(); j++) {
                    SchemeModel schemeModel = schemeModels.get(j);
                    String primDiscValue = AppUtils.getOurInstance().decimalFormatWithTwoDigit(schemeModel.getFlatAmount());
                    if ("0".equalsIgnoreCase(primDiscValue)) {
                        tempList.get(i).remove(j);
                        j--;
                    }
                }
            }

            schemeOrdersList.clear();
            schemeOrdersList.addAll(tempList);

        } catch (Exception e) {
            Log.e(TAG, "filterSchemeData: " + e.getMessage(), e);
        }
    }

    private void setFreeProdAvailable(List<List<FreeProdModel>> freeProdList, OrderBookingVO schemeOrder) {
        if (!freeProdList.isEmpty()) {
            schemeOrder.setFreeProdAvail(true);
        }
    }

    /**
     * fetch order details from db and set in arraylist
     */
    private void loadOrderBookingData() {
        orderBookingVOList = new ArrayList<>();

//        if (newRetailer == 3) {
//
//            orderBookingVOList = db.fetchStockTakeProducts(preferences.readString(PREF_DISTRCODE),
//                    preferences.readString(PREF_SALESMANCODE), preferences.readString(PREF_ROUTECODE), retlrCode);
//
//        } else {
            List<OrderBookingVO> orderFreeProductList;
//
//            if (screenName.equalsIgnoreCase(NAME_PREVIOUS_ORDERS_CONFIRMATION)) {
//                orderFreeProductList = db.getOrderedFreeProductFromDB(
//                        preferences.readString(PREF_DISTRCODE),
//                        preferences.readString(PREF_SALESMANCODE),
//                        preferences.readString(PREF_ROUTECODE),
//                        retlrCode, TABLE_ORDER_CONFIRMATION);
//                orderBookingVOList = db.fetchConfirmOrderedProduct(preferences.readString(PREF_DISTRCODE),
//                        preferences.readString(PREF_SALESMANCODE), preferences.readString(PREF_ROUTECODE), retlrCode);
//            } else {
                orderFreeProductList = db.getOrderedFreeProductFromDB(
                        preferences.readString(PREF_DISTRCODE),
                        preferences.readString(PREF_SALESMANCODE),
                        preferences.readString(PREF_ROUTECODE),
                        AppUtils.generateCode(db,ORDER_BOOKING_NAME), TABLE_ORDER_BOOKING);

                orderBookingVOList = db.fetchOrderedProduct(AppUtils.generateCode(db, ORDER_BOOKING_NAME));
//            }


            for (int i = 0; i < orderBookingVOList.size(); i++) {
                readableInvNo = orderBookingVOList.get(i).getReadableInvNo();

                ArrayList<FreeProdModel> freeProdModelList = new ArrayList<>();

                for (int j = 0; j < orderFreeProductList.size(); j++) {

                    if (orderBookingVOList.get(i).getProdCode().equalsIgnoreCase(orderFreeProductList.get(j).getProdCode()) &&
                            orderBookingVOList.get(i).getProdBatchCode().equalsIgnoreCase(orderFreeProductList.get(j).getProdBatchCode())) {

                        FreeProdModel freeProdModel = new FreeProdModel();
                        freeProdModel.setProdCode(orderFreeProductList.get(j).getProdCode());
                        freeProdModel.setFreeProdCode(orderFreeProductList.get(j).getFreeProdCode());
                        freeProdModel.setFreeQty(String.valueOf(orderFreeProductList.get(j).getOrderFreeQty()));
                        freeProdModel.setFreeProdName(String.valueOf(orderFreeProductList.get(j).getProdName()));
                        freeProdModelList.add(freeProdModel);
                    }
                }
                orderBookingVOList.get(i).setFreeProdModelList(freeProdModelList);
            }

//        }

        setOrderSummaryAdapter(screenName);
        calculateOrderTotal(orderBookingVOList, orderSummaryTotalTxt);

    }

    protected void calculateOrderTotal(List<OrderBookingVO> orderBookingVOList, TextView orderTotalTxt) {
        int itemsCount = 0;
        double itemsValue = 0.0d;
        double discountValue = 0.0d;
        double taxValue = 0.0d;
        if (orderBookingVOList != null) {

            for (OrderBookingVO orderBookingVO : orderBookingVOList) {
                if (newRetailer == 3) {
                    if (orderBookingVO.getStockCheckQty() > 0) {
                        itemsValue = itemsValue
                                + orderBookingVO.getStockOrderValue().doubleValue();

                        discountValue = discountValue + orderBookingVO.getSchemeAmount().doubleValue()
                                + (orderBookingVO.getOrderValue().doubleValue() - orderBookingVO.getPrimDiscOrderValue().doubleValue());

                        itemsCount = itemsCount + 1;
                    }
                } else {
                    if (orderBookingVO.getQuantity() > 0) {

                        itemsValue = itemsValue
                                + orderBookingVO.getTotalAmount().doubleValue();

                        discountValue = discountValue + orderBookingVO.getSchemeAmount().doubleValue()
                                + (orderBookingVO.getOrderValue().doubleValue() - orderBookingVO.getPrimDiscOrderValue().doubleValue());
                        taxValue = taxValue + orderBookingVO.getTax();

                        itemsCount = itemsCount + 1;
                    }
                }
            }
        }

        setValuesToView(orderTotalTxt, itemsValue, discountValue, taxValue);
    }

    private void setValuesToView(TextView orderTotalTxt, double itemsValue, double discountValue, double taxValue) {
        if (!screenName.isEmpty() && NAME_STOCK_TAKE.equalsIgnoreCase(screenName)) {
            totalDiscountTxt.setVisibility(View.INVISIBLE);
        } else {
            if (totalDiscountTxt != null)
                totalDiscountTxt.setText(MessageFormat.format("{0} {1}", getString(R.string.total_discount), String.format(Locale.getDefault(), "%.2f", discountValue)));
            if (totalTaxTxt != null)
                totalTaxTxt.setText(MessageFormat.format("{0} {1}", getString(R.string.total_tax), String.format(Locale.getDefault(), "%.2f", taxValue)));

        }

        if (orderTotalTxt != null)
            orderTotalTxt.setText(String.format(Locale.getDefault(), "%.2f", itemsValue));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.menu_count, menu);
        MenuItem item1 = menu.findItem(R.id.count_bubble);
        item1.setActionView(R.layout.coverage_msg);
        View view1 = item1.getActionView();
        msg = view1.findViewById(R.id.coverage_txt);
        msg.setText(String.valueOf(orderSummaryAdapter.getItemCount()));

    }

    public static class AsyncLoadData extends AsyncTask<Void, Void, Boolean> {

        private final WeakReference<RetailerOrderSummaryFragment> activityReference;
        private final RetailerOrderSummaryFragment activity;
        boolean boolResponse = false;
        String orderValue;
        public AsyncLoadData(RetailerOrderSummaryFragment context,String orderVal) {
            activityReference = new WeakReference<>(context);
            activity = activityReference.get();
            this.orderValue = orderVal;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Globals.getOurInstance().setRetailerEndTime(new Date());
            String routeCode = activity.preferences.readString(PREF_ROUTECODE);
            String distrCode = activity.preferences.readString(PREF_DISTRCODE);
            String salesmanCode = activity.preferences.readString(PREF_SALESMANCODE);

//            if (activity.newRetailer == 3) {
                /*update salesman tracker end time*/
//                activity.db.updateSalesmanTracker(distrCode, salesmanCode, routeCode,
//                        activity.retlrCode, orderValue);
//                activity.db.updateRetailerVisit("stockVisit", "Y", distrCode,
//                        salesmanCode,
//                        routeCode, activity.retlrCode);
//                activity.db.updateRetailerVisitComplete(distrCode, salesmanCode, routeCode, activity.retlrCode);
//                activity.db.updateRetailerMenuReason(distrCode, salesmanCode, routeCode, activity.retlrCode);

//            } else {

//                if (!activity.screenName.equalsIgnoreCase(NAME_PREVIOUS_ORDERS_CONFIRMATION)) {

//                    if (activity.db.isStockCaptured(distrCode, salesmanCode, routeCode, activity.retlrCode)) {
//                        activity.db.updateRetailerVisit("stockVisit", "Y", distrCode,
//                                salesmanCode,
//                                routeCode, activity.retlrCode);
//                    }
                    /*update salesman tracker end time*/
//                    activity.db.updateSalesmanTracker(distrCode, salesmanCode, routeCode, activity.retlrCode, orderValue);

                    activity.db.updateOrderPendingState(TABLE_ORDER_BOOKING, "Y", activity.readableInvNo);
//                    activity.db.updateRetailerVisit("orderVisit", "Y", distrCode, salesmanCode, routeCode, activity.retlrCode);
//                    activity.db.updateRetailerVisit("syncVisit", "Y", distrCode, salesmanCode, routeCode, activity.retlrCode);
//
//                    activity.db.updateCustomerUploadWorkStatus("orderUploaded", "N", distrCode,
//                            salesmanCode, routeCode, activity.retlrCode);
//
//                    activity.db.updateRetailer(distrCode, salesmanCode, routeCode, activity.retlrCode);
//                    activity.db.updateRetailerVisitComplete(distrCode, salesmanCode, routeCode, activity.retlrCode);

//                    if (activity.db.checkOrderBookingTracker(distrCode, salesmanCode, routeCode, activity.retlrCode)) {
//                        activity.db.updateOrderBookingTracker(Globals.getOurInstance().getRetailerStartTime(),
//                                distrCode, salesmanCode, routeCode, activity.retlrCode);
//                    } else {
//
//                        activity.db.insertOrderBookingTracker(distrCode, salesmanCode, routeCode, activity.retlrCode,
//                                Globals.getOurInstance().getRetailerStartTime(), "N");
//                    }
//                }
//            }
//            activity.db.updateSuperVisorVisitTime(distrCode, salesmanCode, routeCode, activity.retlrCode, orderValue);

           List<String> orderNoList = activity.db.fetchAllOrderNos();
           if(!orderNoList.isEmpty()) {
               int value = Integer.parseInt(orderNoList.get(0).substring(orderNoList.get(0).length() - 5)) + 1;
               CodeGeneratorModel codeGeneratorModel = new CodeGeneratorModel(
                       ORDER_BOOKING_NAME,
                       value);
               CodeGeneratorQueryHelper.updateCode(activity.db, codeGeneratorModel);
           }

            return boolResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppUtils.getOurInstance().showProgressDialog(activity.getActivity(),
                    activity.getResources().getString(R.string.MSG_LOADING));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            AppUtils.getOurInstance().cancleProgressDialog();

            if (activity.newRetailer == 3) {
                SFAFragmentManager.newInstance().popBackStack(activity.getSFAFragmentActivity(), ESFAFragTags.STOCK_TAKE);
            }else {
                    SFAFragmentManager.newInstance().popBackStack(activity.getSFAFragmentActivity(), ESFAFragTags.ORDER_BOOKING);
            }
        }
    }

    private void updateTracker() {

        String orderValue = orderSummaryTotalTxt.getText().toString();

        Globals.getOurInstance().setRetailerEndTime(new Date());
        String routeCode = preferences.readString(PREF_ROUTECODE);
        String distrCode = preferences.readString(PREF_DISTRCODE);
        String salesmanCode = preferences.readString(PREF_SALESMANCODE);

//        if (newRetailer == 3) {
//            /*update salesman tracker end time*/
//            db.updateSalesmanTracker(distrCode, salesmanCode, routeCode, retlrCode, orderValue);
//            db.updateRetailerVisit("stockVisit", "Y", distrCode,
//                    salesmanCode,
//                    routeCode, retlrCode);
//            db.updateRetailerVisitComplete(distrCode, salesmanCode, routeCode, retlrCode);
//            db.updateRetailerMenuReason(distrCode, salesmanCode, routeCode, retlrCode);
//            SFAFragmentManager.newInstance().popBackStack(getSFAFragmentActivity(), ESFAFragTags.STOCK_TAKE);
//
//        } else {
//
//            if (screenName.equalsIgnoreCase(NAME_PREVIOUS_ORDERS_CONFIRMATION)) {
//                SFAFragmentManager.newInstance().popBackStack(getSFAFragmentActivity(), ESFAFragTags.PREVIOUS_ORDERS_CONFIRMATION);
//            } else {
//                if (db.isStockCaptured(distrCode, salesmanCode, routeCode, retlrCode)) {
//                    db.updateRetailerVisit("stockVisit", "Y", distrCode,
//                            salesmanCode,
//                            routeCode, retlrCode);
//                }
                /*update salesman tracker end time*/
//                db.updateSalesmanTracker(distrCode, salesmanCode, routeCode, retlrCode, orderValue);

//                db.updateOrderPendingState(TABLE_ORDER_BOOKING, "Y", distrCode,
//                        salesmanCode, routeCode, retlrCode);
//                db.updateRetailerVisit("orderVisit", "Y", distrCode, salesmanCode, routeCode, retlrCode);
//                db.updateRetailerVisit("syncVisit", "Y", distrCode, salesmanCode, routeCode, retlrCode);

//                db.updateCustomerUploadWorkStatus("orderUploaded", "N", distrCode,
//                        salesmanCode, routeCode, retlrCode);

//                db.updateRetailer(distrCode, salesmanCode, routeCode, retlrCode);
//                db.updateRetailerVisitComplete(distrCode, salesmanCode, routeCode, retlrCode);

//                if (db.checkOrderBookingTracker(distrCode, salesmanCode, routeCode, retlrCode)) {
//                    db.updateOrderBookingTracker(Globals.getOurInstance().getRetailerStartTime(), distrCode, salesmanCode, routeCode, retlrCode);
//                } else {
//
//                    db.insertOrderBookingTracker(distrCode, salesmanCode, routeCode, retlrCode,
//                            Globals.getOurInstance().getRetailerStartTime(), "N");
//                }

//                if(USER_TYPE_ISR.equalsIgnoreCase(preferences.readString(PREF_USER_TYPE))
//                        && "com.botree.retailerssfa.dabur".equalsIgnoreCase(BuildConfig.APPLICATION_ID)){
//                    // update loading stock qty
//                    db.updateVanStockLoadingData(preferences.readString(PREF_DISTRCODE),
//                            preferences.readString(PREF_SALESMANCODE), orderBookingVOList);
//                }

                SFAFragmentManager.newInstance().popBackStack(getSFAFragmentActivity(), ESFAFragTags.ORDER_BOOKING);
//            }
//        }
    }

    @Override
    public void onClick(View view) {

        int v = view.getId();

        switch (v) {
            case R.id.printer_btn:
                startBluetoothConnection();
                break;

            case R.id.retailer_ordsummary_submit_btn:

                if (!orderBookingVOList.isEmpty()) {
//                    updateTracker();
                    new AsyncLoadData(RetailerOrderSummaryFragment.this,orderSummaryTotalTxt.getText().toString()).execute();

                } else {
                    NotifyUtil.showSnackBar(getSFAFragmentActivity(), summaryParent, "There is no order, go back to place order.", Snackbar.LENGTH_LONG);
                }

                break;

            case R.id.signature_btn:

                showSingPad();

                break;

            default:
                break;
        }
    }


    protected void startBluetoothConnection() {
        if (mbtSocket == null) {
            Intent btintent = new Intent(getActivity(),
                    BTWrapperActivity.class);
            this.startActivityForResult(btintent,
                    BTWrapperActivity.REQUEST_CONNECT_BT);
        } else {
            try {
                mbtOutputStream = mbtSocket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "startBluetoothConnection: " + e.getMessage(), e);
            }
            sendDataToPrinter();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BTWrapperActivity.REQUEST_CONNECT_BT) {
            try {
                mbtSocket = BTWrapperActivity.getMbtSocket();
                if (mbtSocket != null) {
                    mbtOutputStream = mbtSocket.getOutputStream();
                    sendDataToPrinter();
                }
            } catch (Exception e) {
                Log.e(TAG, "onActivityResult: " + e.getMessage(), e);
            }
        }
    }


    public byte[] concatenateByteArrays(List<byte[]> blocks) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (byte[] b : blocks) {
            os.write(b, 0, b.length);
        }
        return os.toByteArray();
    }

    private StringBuilder getPrintTotalStringBuilder() {
        return new StringBuilder("Total Items : ")
                .append(msg.getText().toString())
                .append(" Total Amount : ")
                .append(orderSummaryTotalTxt.getText().toString());
    }


    private byte[] printHeader() {
        return Printing.printText(retilerName + "\n",
                PrintGlobals.PRT_ALIGN_CENTER,
                PrintGlobals.PRT_DOUBLE_WDT,
                PrintGlobals.PRT_DOUBLE_HGT,
                PrintGlobals.PRT_FONTSIZE_COMPRESSED,
                PrintGlobals.PRT_FONTSTYLE_NORMAL,
                PrintGlobals.PRT_LINE_HGT,
                PrintGlobals.PRT_RIGHT_SPACE,
                mbtOutputStream);


    }

    private StringBuilder getPrintSingleLineStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 48; i++) {
            stringBuilder.append("-");
        }
        return stringBuilder;
    }

    private byte[] printSingleLine() {


        return Printing.printText(getPrintSingleLineStringBuilder().toString(),
                PrintGlobals.PRT_ALIGN_LEFT,
                PrintGlobals.PRT_NORMAL_WDT,
                PrintGlobals.PRT_NORMAL_HGT,
                PrintGlobals.PRT_FONTSIZE_STD,
                PrintGlobals.PRT_FONTSTYLE_BOLD,
                PrintGlobals.PRT_LINE_HGT,
                PrintGlobals.PRT_RIGHT_SPACE,
                mbtOutputStream);

    }

    public String center(String s, int length) {
        if (s.length() > length) {
            return s.substring(0, length) + "\n" + s.substring(length);
        } else if (s.length() == length) {
            return s;
        } else {
            int leftPadding = (length - s.length()) / 2;
            StringBuilder leftBuilder = new StringBuilder();
            for (int i = 0; i < leftPadding; i++) {
                leftBuilder.append(" ");
            }

            int rightPadding = length - s.length() - leftPadding;
            StringBuilder rightBuilder = new StringBuilder();
            for (int i = 0; i < rightPadding; i++)
                rightBuilder.append(" ");

            return leftBuilder.toString() + s
                    + rightBuilder.toString();
        }
    }

    public String right(String s, int length) {
        if (s.length() > length) {
            return s.substring(0, length) + "\n" + s.substring(length);
        } else if (s.length() == length) {
            return s;
        } else {
            int leftPadding = (length - s.length());
            StringBuilder leftBuilder = new StringBuilder();
            for (int i = 0; i < leftPadding; i++) {
                leftBuilder.append(" ");
            }

            return leftBuilder.toString() + s;
        }
    }

    public String left(String s, int length) {
        if (s.length() > length) {
            return s.substring(0, length) + "\n" + s.substring(length);
        } else if (s.length() == length) {
            return s;
        } else {

            int rightPadding = length - s.length();
            StringBuilder rightBuilder = new StringBuilder();
            for (int i = 0; i < rightPadding; i++)
                rightBuilder.append(" ");

            return s + rightBuilder.toString();
        }
    }

    private String formPrintingText(String product, String qty, String rate, String lTotal, AlignmentType type) {
        switch (type) {
            case CENTER:
                return center(product, 20) + "|" + center(qty, 7) + "|" + center(rate, 8) + "|" + center(lTotal, 10) + "\n";
            case LEFT:
                if (product.length() <= 30)
                    return left(product, 30) + "|" + center(qty, 11) + "|" + right(rate, 12) + "|" + right(lTotal, 12) + "\n";
                else
                    return left(product.substring(0, 30), 30) + "|" + center(qty, 11) + "|" + right(rate, 12) + "|" + right(lTotal, 12) + "\n"
                            + left(product.substring(30), (product.length() - 30)) + right("", 0) + "\n";

            default:
                return center(product, 20) + "|" + center(qty, 7) + "|" + center(rate, 8) + "|" + center(lTotal, 12) + "\n";
        }
    }

    private StringBuilder getPrintTitlesStringBuilder() {
        return new StringBuilder(formPrintingText("Product", "Qty", "Rate", "L.Total", AlignmentType.CENTER));
    }

    private byte[] printTitles() {
        return Printing.printText(getPrintTitlesStringBuilder().toString(),
                PrintGlobals.PRT_ALIGN_LEFT,
                PrintGlobals.PRT_NORMAL_WDT,
                PrintGlobals.PRT_NORMAL_HGT,
                PrintGlobals.PRT_FONTSIZE_STD,
                PrintGlobals.PRT_FONTSTYLE_BOLD,
                PrintGlobals.PRT_LINE_HGT,
                PrintGlobals.PRT_RIGHT_SPACE,
                mbtOutputStream);
    }

    private StringBuilder getLineItemsStringBuilder() {
        StringBuilder printText = new StringBuilder();
        for (OrderBookingVO orderBookingVO : orderBookingVOList) {

            printText.append(formPrintingText(orderBookingVO.getProdShortName(),
                    OrderSupportUtil.getInstance().getStringQty(orderBookingVO.getQuantity()),
                    String.valueOf(orderBookingVO.getSellPrice()),
                    String.valueOf(orderBookingVO.getOrderValue()),
                    AlignmentType.LEFT));

        }
        return printText;
    }

    private byte[] printLineItems() {
        if (orderBookingVOList != null && !orderBookingVOList.isEmpty()) {
            return Printing.printText(getLineItemsStringBuilder().toString(),
                    PrintGlobals.PRT_ALIGN_LEFT,
                    PrintGlobals.PRT_NORMAL_WDT,
                    PrintGlobals.PRT_NORMAL_HGT,
                    PrintGlobals.PRT_FONTSIZE_COMPRESSED,
                    PrintGlobals.PRT_FONTSTYLE_BOLD,
                    PrintGlobals.PRT_LINE_HGT,
                    PrintGlobals.PRT_RIGHT_SPACE,
                    mbtOutputStream);
        } else {
            return new byte[0];
        }
    }

    private byte[] printTotal() {

        return Printing.printText(getPrintTotalStringBuilder().toString() + "\n",
                PrintGlobals.PRT_ALIGN_LEFT,
                PrintGlobals.PRT_NORMAL_WDT,
                PrintGlobals.PRT_NORMAL_HGT,
                PrintGlobals.PRT_FONTSIZE_STD,
                PrintGlobals.PRT_FONTSTYLE_BOLD,
                PrintGlobals.PRT_LINE_HGT,
                PrintGlobals.PRT_RIGHT_SPACE,
                mbtOutputStream);
    }

    private void sendDataToPrinter() {

        byte[] paperFeedBytes = Printing.printPaperFeed(mbtOutputStream);
        byte[] singleLineBytes = printSingleLine();
        List<byte[]> bytesList = new ArrayList<>();
        bytesList.add(printHeader());
        bytesList.add(printTitles());
        bytesList.add(printLineItems());
        bytesList.add(singleLineBytes);
        bytesList.add(paperFeedBytes);
        bytesList.add(printTotal());
        bytesList.add(singleLineBytes);
        bytesList.add(paperFeedBytes);

        Printing.printWholeTextLines(concatenateByteArrays(bytesList), mbtOutputStream);

        Printing.printPaperFeed(mbtOutputStream);

    }

    @Override
    public void newLocation(Location newLocation) {
        if (isLocationReady) {
            if (newRetailer == 1) {
                updateUI(newLocation);
            }

        }
    }

    @Override
    public void onConnectionSuccess() {
        isLocationReady = true;
    }

    @Override
    public void onConnectionError() {
        isLocationReady = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sfaLocation != null)
            sfaLocation.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (sfaLocation != null)
            sfaLocation.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        if (sfaLocation != null)
            sfaLocation.onStart();

        super.onStart();
    }

    @Override
    public void onPause() {

        if (sfaLocation != null) {
            sfaLocation.onPause();
        }

        super.onPause();
    }

    @Override
    public void onStop() {
        if (sfaLocation != null)
            sfaLocation.onStop();

        super.onStop();
    }

    private void updateUI(Location newLocation) {
        try {
            Log.e(TAG, "updateUI: lat long " + newLocation.getLatitude() + " | " + newLocation.getLongitude());

            Globals.getOurInstance().setStrLatitude(String.valueOf(newLocation.getLatitude()));
            Globals.getOurInstance().setStrLongitude(String.valueOf(newLocation.getLongitude()));

            Location startPoint = new Location("locationA");
            startPoint.setLatitude(newLocation.getLatitude());
            startPoint.setLongitude(newLocation.getLongitude());

            Location endPoint = new Location("locationB");
            endPoint.setLatitude(Globals.getOurInstance().getRetrlatitude());
            endPoint.setLongitude(Globals.getOurInstance().getRettlongitude());
            double distance = startPoint.distanceTo(endPoint);

            Globals.getOurInstance().setStrRetrDistance(distance);

            if (newLocation.getLatitude() > 0.0 && newLocation.getLongitude() > 0.0) {
                preferences.writeString(PREF_LAST_LAT, String.valueOf(newLocation.getLatitude()));
                preferences.writeString(PREF_LAST_LONG, String.valueOf(newLocation.getLongitude()));
            }

        } catch (Exception e) {
            Log.e(TAG, "updateUI: " + e.getMessage(), e);
        }
    }

    private enum AlignmentType {
        CENTER,
        LEFT
    }
}




