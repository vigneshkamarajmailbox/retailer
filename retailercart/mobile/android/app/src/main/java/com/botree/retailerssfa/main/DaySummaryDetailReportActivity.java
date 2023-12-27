package com.botree.retailerssfa.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.OnExpandableItemClick;
import com.botree.retailerssfa.adapters.OrderSummaryExpListAdapter;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.RetailerVO;
import com.botree.retailerssfa.stikyheaderexpandablelistview.WrapperExpandableListAdapter;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.PDFCreationUtil;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_IS_PTR_WITH_TAX;
import static com.botree.retailerssfa.support.Globals.NAME_INVOICE_SUMMARY;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;


public class DaySummaryDetailReportActivity extends AppCompatActivity {
    public static final Integer CUSTOMER = 20;
    public static final Integer SALESMAN = 30;
    private SFADatabase db;
    private SFASharedPref preferences;
    private List<OrderBookingVO> parentList;
    private HashMap<String, List<OrderBookingVO>> childItems;

    private ArrayList<String> parentdata;
    private ExpandableListView ordsumList;
    private TextView cartEmpty;
    private TextView orderValueTxt;
    private Spinner routeSpinner;
    private String strRouteCode;
    private TextView routeTotalTxt;
    private String screenName;
    private String totValue;
    private View mRootLayout;
    private boolean isPTRWithTax = false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_summary_detail_report);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        db = SFADatabase.getInstance(this);
        preferences = SFASharedPref.getOurInstance();

        Intent intent = getIntent();
        screenName = intent.getStringExtra("screenName");
        totValue = intent.getStringExtra("totalValue");
        Log.e("screen name", screenName);

        if ("Y".equalsIgnoreCase(db.getConfigDataBasedOnName(CONFIG_IS_PTR_WITH_TAX))) {
            isPTRWithTax = true;
        }

        initialize();
    }

    private void initialize() {

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        TextView title = toolbar.findViewById(R.id.custom_toolbar_title);
        TextView subtitle = toolbar.findViewById(R.id.custom_toolbar_subtitle);
        subtitle.setVisibility(View.VISIBLE);
        if (screenName.equalsIgnoreCase(NAME_INVOICE_SUMMARY))
            title.setText(String.valueOf("Order Taking Summary"));
        else
            title.setText(String.valueOf("Day Summary"));
        subtitle.setText(DateUtil.getCurrentDateMonthYear());
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mRootLayout = findViewById(R.id.rootLayout);
        routeSpinner = findViewById(R.id.summary_route_spinner);
        cartEmpty = findViewById(R.id.day_sum_ordersum_txtEmpty);
        routeTotalTxt = findViewById(R.id.route_total_txt);
        orderValueTxt = findViewById(R.id.day_total_order_txt);
        ordsumList = findViewById(R.id.day_sum_ordsum_explistview);

        loadSpinnerData();
    }


    /* fetch route data from db and load in route spinner */
    private void loadSpinnerData() {


        ArrayList<String> routeName = new ArrayList<>();

        final ArrayList<String> routeCode = new ArrayList<>();

        List<RetailerVO> routeList;


//        routeList = db.getOrderedRoute(preferences.readString(PREF_DISTRCODE),preferences.readString(PREF_SALESMANCODE), screenName);
//
//        routeName.add("All Routes");
//
//        routeCode.add("0");
//
//
//
//        for (int i = 0; i < routeList.size(); i++) {
//
//
//
//            routeName.add(routeList.get(i).getRouteName());
//
//            routeCode.add(routeList.get(i).getRouteCode());
//
//        }
//
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(DaySummaryDetailReportActivity.this, android.R.layout.simple_spinner_item, routeName);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        routeSpinner.setAdapter(adapter);


        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override

            public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {


                try {

                    strRouteCode = routeCode.get(pos);

                    loadDataFromDB(strRouteCode);

                    ((TextView) v).setTextColor(Color.WHITE);

                } catch (Exception e) {

                    Log.e("onItemSelected", "onItemSelected: " + e.getMessage(), e);

                }

            }


            @Override

            public void onNothingSelected(AdapterView<?> parent) {

                // ignored

            }

        });

        loadDataFromDB(strRouteCode);


    }

    private void loadDataFromDB(String routecode) {

        String salesmanCode = preferences.readString(PREF_SALESMANCODE);
        String distCode = preferences.readString(PREF_DISTRCODE);

        parentList = db.loadDaySummaryOrderAllValue(distCode, salesmanCode, routecode);


        childItems = new HashMap<>();
        parentdata = new ArrayList<>();
        for (int i = 0; parentList.size() > i; i++) {
            List<OrderBookingVO> sukList;

            sukList = db.loadDaySummaryOrderTabValue(parentList.get(i).getReadableInvNo(), preferences.readString(PREF_SALESMANCODE),
                    parentList.get(i).getRetailerCode());

            childItems.put(parentList.get(i).getReadableInvNo(), sukList);
            parentdata.add(parentList.get(i).getReadableInvNo());
        }


        orderValueTxt.setText(totValue);

        routeTotalTxt.setText(totValue);

        loadDataInRecylcerView();
    }

    private void loadDataInRecylcerView() {

        OrderSummaryExpListAdapter listAdapter = new OrderSummaryExpListAdapter(this, parentdata,
                childItems, parentList, screenName);

        listAdapter.setOnExpandableItemClick(new OnExpandableItemClick() {
            @Override
            public void setOnExpandableListClick(int groupPosition) {

                new AsynFileCreation(DaySummaryDetailReportActivity.this, CUSTOMER, groupPosition).execute();
            }
        });

        listAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(listAdapter);

        ordsumList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    ordsumList.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        if (wrapperAdapter.getGroupCount() > 0) {
            ordsumList.setAdapter(wrapperAdapter);
            ordsumList.setVisibility(View.VISIBLE);
            cartEmpty.setVisibility(View.GONE);

        } else {
            cartEmpty.setVisibility(View.VISIBLE);
            ordsumList.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int i = item.getItemId();
        if (i == R.id.action_share) {
            if (!parentList.isEmpty()) {

                new AsynFileCreation(this, SALESMAN, 0).execute();

            } else {
                NotifyUtil.showSnackBar(this, mRootLayout, "No Orders to Share", Snackbar.LENGTH_SHORT);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class AsynFileCreation extends AsyncTask<Void, Void, Boolean> {
        private final WeakReference<DaySummaryDetailReportActivity> activityRef;
        private final Integer type;
        private final int groupPosition;
        AppUtils appUtils = AppUtils.getOurInstance();
        File outputFile = null;
        SFASharedPref sfaSharedPref = SFASharedPref.getOurInstance();
        boolean isPrimaryDiscount = false;

        AsynFileCreation(DaySummaryDetailReportActivity activity, Integer type, int groupPosition) {
            activityRef = new WeakReference<>(activity);
            this.type = type;
            this.groupPosition = groupPosition;
            if ("Y".equalsIgnoreCase(activity.db.getConfigDataBasedOnName(AppConstant.Configurations.CONFIG_PRIMARY_DISC))) {
                this.isPrimaryDiscount = true;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            DaySummaryDetailReportActivity activity = activityRef.get();
            appUtils.showProgressDialog(activity, activity.getResources().getString(R.string.FILE_GENRATING));
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            DaySummaryDetailReportActivity activity = activityRef.get();
            if (type.equals(CUSTOMER)) {

                List<OrderBookingVO> childtem = activity.childItems.get(activity.parentdata.get(groupPosition));

                outputFile = PDFCreationUtil.getInstance().createCustomerSummaryPdf(activity.screenName, childtem,
                        activity.parentList.get(groupPosition), activity.isPTRWithTax, isPrimaryDiscount);

            } else {
                outputFile = PDFCreationUtil.getInstance().createSalesmanSummaryPdf(activity.screenName,
                        sfaSharedPref.readString(PREF_SALESMANCODE), activity.childItems,
                        activity.parentdata, activity.parentList, activity.isPTRWithTax, isPrimaryDiscount);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            DaySummaryDetailReportActivity activity = activityRef.get();
            appUtils.cancleProgressDialog();
            if (outputFile != null) {
                if (type.equals(CUSTOMER)) {
                    appUtils.onShareClick(activity, outputFile, activity.parentList.get(groupPosition));
                } else {
                    appUtils.onShareClick(activity, outputFile, null);
                }
            }
        }
    }
}
