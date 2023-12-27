package com.botree.retailerssfa.main;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.arcprogressbar.ArcProgress;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.main.MainActivity;
import com.botree.retailerssfa.util.SFASharedPref;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.botree.retailerssfa.controller.constants.AppConstant.USER_TYPE_ISR;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_BILLING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_NEW_RETAILER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_BOOKING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_SALES_RETURN;
import static com.botree.retailerssfa.support.Globals.NAME_DAY_SUMMARY;
import static com.botree.retailerssfa.support.Globals.NAME_INVOICE_SUMMARY;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;

public class SalesmanDaySummary extends SFAFragment implements View.OnClickListener {

    private static final String LOG_TAG = SalesmanDaySummary.class.getSimpleName();
    private static final String MSG_TAG = "{0} {1}";
    private SFADatabase db;

    private TextView orderValueTxt;
    private TextView cashTotalTxt;
    private TextView chequeTotalTxt;
    private TextView collectionTotalTxt;
    private TextView unbilledRetailerTxt;
    private TextView productiveIndexBtn;
    private TextView noOfLinesBtn;
    private TextView orderSubmittedBtn;
    private TextView noOfUniqueLines;

    private ArcProgress coverageProgress;
    private ArcProgress producitivityProgress;
    private TextView unbillOutletsTxt;
    private TextView orderSubmittedTxt;

    private SFASharedPref sfaSharedPref;
    private String userType;
    private TextView toDayBeatNameTxt;
    private CardView beatLayout;
    private String fragmentName;
    private String columnName;
    private String tableName;
    private TextView mTvCollectionInvoiceTotal;
    private TextView salesReturnsCount;
    private TextView newOutletCount;

    public SalesmanDaySummary() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        sfaSharedPref = SFASharedPref.getOurInstance();
        db = SFADatabase.getInstance(getActivity());
        ((MainActivity) getSFAFragmentActivity()).hideBottomNevigation();
        userType = sfaSharedPref.readString(SFASharedPref.PREF_USER_TYPE);

        if (getArguments() != null) {
            fragmentName = getArguments().getString("fragmentName");
        }
        if (fragmentName != null && fragmentName.equalsIgnoreCase(NAME_DAY_SUMMARY)) {
            columnName = "orderVisit";
            tableName = TABLE_ORDER_BOOKING;
        } else {
            columnName = "billVisit";
            tableName = TABLE_BILLING;
        }
        setAutoScreenCount(fragmentName);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_salesman_day_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_favorite, menu);
        MenuItem favorite = menu.findItem(R.id.favorite);
        setFavoriteIconBasedOnPref(favorite, fragmentName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.favorite) {
            if (!db.isFavoriteMenu(fragmentName)) {
                menuItem.setIcon(R.drawable.ic_favorite_star_fill);
                db.deleteQuickActions(fragmentName);
                db.insertQuickAction(fragmentName, true);
            } else {
                menuItem.setIcon(R.drawable.ic_favorite_star);
                db.deleteQuickActions(fragmentName);
                db.insertQuickAction(fragmentName, false);
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void initialize(View view) {

        producitivityProgress = view.findViewById(R.id.productivity_arc_progress);
        coverageProgress = view.findViewById(R.id.coverage_arc_progress);

        orderSubmittedTxt = view.findViewById(R.id.day_sum_order_submit_txt);
        unbillOutletsTxt = view.findViewById(R.id.day_sum_unbilled_outlets_txt);
        orderValueTxt = view.findViewById(R.id.sales_order_value_txt);
        cashTotalTxt = view.findViewById(R.id.total_cash_coll_txt);
        chequeTotalTxt = view.findViewById(R.id.total_cheque_coll_txt);
        collectionTotalTxt = view.findViewById(R.id.total_collections_txt);
        mTvCollectionInvoiceTotal = view.findViewById(R.id.total_collections_invoice);
        unbilledRetailerTxt = view.findViewById(R.id.unproductive_calls_txt);

        salesReturnsCount = view.findViewById(R.id.sales_return_count_txt);
        newOutletCount = view.findViewById(R.id.new_outlet_count_txt);

        toDayBeatNameTxt = view.findViewById(R.id.today_beat_name_txt);
        productiveIndexBtn = view.findViewById(R.id.sales_productive_calls_txt);
        noOfLinesBtn = view.findViewById(R.id.sales_no_of_line_txt);
        noOfUniqueLines = view.findViewById(R.id.sales_no_of_unique_line_txt);
        orderSubmittedBtn = view.findViewById(R.id.order_submitt_txt);
        Button showOrdersBtn = view.findViewById(R.id.view_order_summary_btn);
        beatLayout = view.findViewById(R.id.beat_name_layout);

        View mLayoutCollection = view.findViewById(R.id.layoutCollection);
        View mLayoutInvCollection = view.findViewById(R.id.layoutInvCollection);

        CardView newOutletCard = view.findViewById(R.id.sales_return_card);
        CardView salesReturnCard = view.findViewById(R.id.new_outlet_card);

        showHideViewBasedOnCongf(newOutletCard, "Add Outlet", "Add Outlet");
        showHideViewBasedOnCongf(salesReturnCard, "Outlet Visit", "Sales Return");

        TextView mTvOrderSync = view.findViewById(R.id.tvOrderSync);
        TextView mTvOrderTaken = view.findViewById(R.id.tvOrderTaken);
        TextView mTvOrderNotTaken = view.findViewById(R.id.tvOrderNotTaken);

        if (fragmentName.equalsIgnoreCase(NAME_INVOICE_SUMMARY)) {
            mLayoutCollection.setVisibility(View.GONE);
            mTvOrderNotTaken.setText(String.valueOf(getSFAFragmentActivity().getResources().getString(R.string.invoice_not_taken)));
            mTvOrderTaken.setText(String.valueOf(getSFAFragmentActivity().getResources().getString(R.string.invoice_taken)));
            mTvOrderSync.setText(String.valueOf(getSFAFragmentActivity().getResources().getString(R.string.invoice_synced)));
        } else {
            mLayoutInvCollection.setVisibility(View.GONE);
            mTvOrderNotTaken.setText(String.valueOf(getSFAFragmentActivity().getResources().getString(R.string.order_not_taken)));
            mTvOrderTaken.setText(String.valueOf(getSFAFragmentActivity().getResources().getString(R.string.order_taken)));
            mTvOrderSync.setText(String.valueOf(getSFAFragmentActivity().getResources().getString(R.string.order_synced)));
        }
        showOrdersBtn.setOnClickListener(this);

        loadSummaryDataFromDB();

    }

    private void showHideViewBasedOnCongf(CardView newOutletCard, String moduleName, String confiName) {

        if ((db.isScreenAvailable(moduleName, confiName))) {
            newOutletCard.setVisibility(View.VISIBLE);
        } else {
            newOutletCard.setVisibility(View.GONE);
        }
    }

    private String getRouteCode() {

        return db.getTodayBeatRouteCode(sfaSharedPref.readString(PREF_DISTRCODE),
                sfaSharedPref.readString(PREF_SALESMANCODE), String.valueOf(true));
    }

    /* fetch day summary data from db and show in views */
    private void loadSummaryDataFromDB() {

        String salesmanCode = sfaSharedPref.readString(PREF_SALESMANCODE);
        String distCode = sfaSharedPref.readString(PREF_DISTRCODE);
        int totalRetailer = 0;
        try {

            if (!userType.isEmpty() && USER_TYPE_ISR.equalsIgnoreCase(userType)
                    && getRouteCode().isEmpty()) {

//                List<Map<String, String>> mapList = db.getOtherBeat(distCode);
//                toDayBeatNameTxt.setText(mapList.get(0).get("toDayRoute"));

//                beatLayout.setVisibility(View.GONE);

//                for (int i = 0; i < mapList.size(); i++) {
//                    totalRetailer = totalRetailer + Integer.valueOf(mapList.get(i).get("totCount"));
//                }

            } else {

//                if (getRouteCode() != null && !getRouteCode().isEmpty()) {
//                    Map<String, String> map = db.getTodayBeat(getRouteCode());
//                    toDayBeatNameTxt.setText(map.get("toDayRoute"));
//                   totalRetailer = Integer.valueOf(db.getTotalRetailerFromDB(distCode, salesmanCode, true));
//                } else {
//                    toDayBeatNameTxt.setText(getResources().getString(R.string.all_route));
//                   totalRetailer = Integer.valueOf(db.getTotalRetailerFromDB(distCode, salesmanCode, false));
//                }
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "loadSummaryDataFromDB: " + e.getMessage(), e);
        }

//        String strCompletedRtlr = db.getCompletedRetailerFromDB(distCode, salesmanCode, columnName);
//       String strCoveredRtlr = db.getCoveredRetailerCount(distCode, salesmanCode);

//        String newOutletsCount = db.getNewOutletCounts(distCode, salesmanCode, TABLE_NEW_RETAILER);

        String[] report = db.getOrderCountFromDB(tableName, distCode, salesmanCode, "0");
        String uniqueLinesCount = db.getOrderUniqueLines(tableName, distCode, salesmanCode, "0");
//       String[] salesReturnreport = db.getSalesReturnSalableUnsalableCount(TABLE_SALES_RETURN, distCode, salesmanCode);

        orderValueTxt.setText(MessageFormat.format(MSG_TAG, getString(R.string.Rs), report[0]));
        noOfLinesBtn.setText(report[1]);
        noOfUniqueLines.setText(uniqueLinesCount);

//        unbilledRetailerTxt.setText(MessageFormat.format("{0}/{1}", strCompletedRtlr, strCoveredRtlr));

//       newOutletCount.setText(newOutletsCount);

//        salesReturnsCount.setText(MessageFormat.format("Salable: {0} / Unsalable: {1}", salesReturnreport[0], salesReturnreport[1]));

//        int orderNoTakenCount = totalRetailer - Integer.valueOf(strCompletedRtlr);
//        if (orderNoTakenCount > 0) {
//            unbillOutletsTxt.setText(String.valueOf(totalRetailer - Integer.valueOf(strCompletedRtlr)));
//        } else {
//            unbillOutletsTxt.setText("0");
//        }


//        onProgress((double) totalRetailer, Double.valueOf(strCoveredRtlr), coverageProgress);
//        onProgress(Double.valueOf(strCoveredRtlr), Double.valueOf(strCompletedRtlr), producitivityProgress);


        if (fragmentName.equalsIgnoreCase(NAME_DAY_SUMMARY)) {

            String strTotalOrder = db.getTotalCashFromDB(distCode, salesmanCode, 4);
            orderSubmittedTxt.setText(strTotalOrder);

//            //total Cash
//            String totalCash = db.getTotalCashFromDB(distCode, salesmanCode, 1);
//            cashTotalTxt.setText(MessageFormat.format(MSG_TAG, getString(R.string.Rs), totalCash));
//            //Total Cheque
//            String totalCheque = db.getTotalCashFromDB(distCode, salesmanCode, 2);
//            chequeTotalTxt.setText(MessageFormat.format(MSG_TAG, getString(R.string.Rs), totalCheque));
//
//            //-------- Collection Total ---------//
//            Double total = Double.valueOf(totalCash) + Double.valueOf(totalCheque);
//            collectionTotalTxt.setText(MessageFormat.format(MSG_TAG, getString(R.string.Rs), String.format(Locale.getDefault(), "%.2f", total)));


//            strCompletedRtlr = strCoveredRtlr + "/" + totalRetailer;
//            productiveIndexBtn.setText(strCompletedRtlr);

            String strSubmittedOrder = db.getTotalCashFromDB(distCode, salesmanCode, 3);

            String strOrderSubmitted = strSubmittedOrder;

            orderSubmittedBtn.setText(strOrderSubmitted);
        } else {

//            String strTotalOrder = db.getTotalCashFromDB(distCode, salesmanCode, 9);
//            orderSubmittedTxt.setText(strTotalOrder);
//
//            String strSubmittedOrder = db.getTotalCashFromDB(distCode, salesmanCode, 6);
//            String strTotalCash = db.getTotalCashFromDB(distCode, salesmanCode, 5);
//            cashTotalTxt.setText(String.format("%s %s", getResources().getText(R.string.Rs), strTotalCash));
//            mTvCollectionInvoiceTotal.setText(String.format("%s %s", getResources().getText(R.string.Rs), strTotalCash));
//
//            String strOrderSubmitted;
//
//            strOrderSubmitted = strSubmittedOrder + "/" + strCompletedRtlr;
//            orderSubmittedBtn.setText(strOrderSubmitted);
//
//            strCompletedRtlr = strCoveredRtlr + "/" + totalRetailer;
//            productiveIndexBtn.setText(strCompletedRtlr);
        }

    }


    public void onProgress(Double tot, Double fraction, ArcProgress arcProgress) {

        int progCount = (int) ((fraction / tot) * 100.00);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(arcProgress, "progress", 0, progCount);
        progressAnimator.setDuration(800);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.view_order_summary_btn) {
            String amount = orderValueTxt.getText().toString();

            Intent intent = new Intent(getActivity(), DaySummaryDetailReportActivity.class);
            intent.putExtra("screenName", fragmentName);
            intent.putExtra("totalValue", amount);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_up, R.anim.stay);
        }
    }
}