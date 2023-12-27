package com.botree.retailerssfa.main;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.OrderBookingRepExpListAdapter;
import com.botree.retailerssfa.base.BaseActivity;
import com.botree.retailerssfa.customcalendarview.CalendarCellDecorator;
import com.botree.retailerssfa.customcalendarview.CalendarPickerView;
import com.botree.retailerssfa.customcalendarview.DefaultDayViewAdapter;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.db.query.IDBColumns;
import com.botree.retailerssfa.interfaces.OnExpandableItemClickTrackOrder;
import com.botree.retailerssfa.models.OrderBookingHeaderModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.OrderStatusModel;
import com.botree.retailerssfa.models.ReasonVO;
import com.botree.retailerssfa.stikyheaderexpandablelistview.WrapperExpandableListAdapter;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.support.Globals.NAME_ORDER_DETAILS;

public class PreviousOrderBookingReportActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = PreviousOrderBookingReportActivity.class.getSimpleName();
    private Toolbar toolbar;
    private SFADatabase db;
    private SFASharedPref preferences;
    private ExpandableListView ordsumList;
    private TextView cartEmpty;
    private List<OrderBookingHeaderModel> parentList = new ArrayList<>();
    private LinkedHashMap<String, List<OrderBookingVO>> childItems = new LinkedHashMap<>();
    private LinkedHashMap<String, List<OrderStatusModel>> childItemsTwo = new LinkedHashMap<>();

    private ArrayList<String> parentdata = new ArrayList<>();
    List<OrderBookingHeaderModel> orderBookingModels = new ArrayList<>();
    double totalNet = 0;
    private TextView totGrossAmtTxt;
    private TextView totalDiscountAmtTxt;
    private TextView totalTaxAmtTxt;
    private TextView totalNetAmtTxt;
    List<ReasonVO> valueList = new ArrayList<>();
    List<String> spinnerValueList = new ArrayList<>();
    private Spinner catSpinner;
    private Spinner valSpinner;
    LinearLayout dateFilter;
    private boolean showCompany = true;

    private int mYear = -1;
    private int mMonth = -1;
    private int mDay = -1;
    private OrderBookingRepExpListAdapter listAdapter;
    private String catValueName = "";
    private String subCatCode = "";
    private String dateFormat = "yyyy-MM-dd";
    private TextView companyLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order_booking_report);
        db = SFADatabase.getInstance(this);
        preferences = SFASharedPref.getOurInstance();
        initTitleBar();
        new AsyncLoadData(this).execute();

    }

    private void initTitleBar() {
        toolbar = findViewById(R.id.custom_toolbar_with_spinner);
        initToolbar();
        setBaseToolbarTitle(getResources().getString(R.string.screen_name_order_details), null);

        companyLabel = findViewById(R.id.companyLabel);
        cartEmpty = findViewById(R.id.po_order_status_txtEmpty);
        dateFilter = findViewById(R.id.date_filter);
        ordsumList = findViewById(R.id.sales_report_header_recyclerview);
        totGrossAmtTxt = findViewById(R.id.total_gross_amt_txt);
        totalDiscountAmtTxt = findViewById(R.id.total_discount_amt_txt);
        totalTaxAmtTxt = findViewById(R.id.total_tax_amt_txt);
        totalNetAmtTxt = findViewById(R.id.total_net_amt_txt);

        catSpinner = toolbar.findViewById(R.id.cat_spinner);
        valSpinner = toolbar.findViewById(R.id.val_spinner);
        ImageView datePickerImg = toolbar.findViewById(R.id.date_picker_img);
        datePickerImg.setOnClickListener(this);

        if (mDay == -1 || mMonth == -1 || mYear == -1)
            loadCalendar();

        loadSpinner();

//        dateFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Collections.sort(new ArrayList<>(orderBookingModels), Collections.reverseOrder());
////                Collections.sort(orderBookingModels, new Comparator<OrderBookingHeaderModel>() {
////                    @Override public int compare(OrderBookingHeaderModel p1, OrderBookingHeaderModel p2) {
////                        return p1.getOrderDt()> p2.getOrderDt(); // Ascending
////                    }
////
////                });
//
//
//                for (OrderBookingHeaderModel model : orderBookingModels) {
//                    List<OrderBookingVO> subList = new ArrayList<>();
//                    if (childItems.containsKey(model.getCustomerRefNo())) {
//                        List<OrderBookingVO> temp = childItems.get(model.getCustomerRefNo());
//                        temp.addAll(model.getOrderBookingDetailsList());
//                        subList.clear();
//                        subList.addAll(temp);
//                    } else {
//                        subList.clear();
//                        subList.addAll(model.getOrderBookingDetailsList());
//                    }
//                    childItems.put(model.getCustomerRefNo(), subList);
//                    parentList.add(model);
//                    parentdata.add(model.getCustomerRefNo());
//                }
//
//                loadDataInRecylcerView();
//            }
//        });
    }

    /* get current date month and year */

    private void loadCalendar() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    private void loadSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_sales_report_arr, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(adapter);

        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                try {
                    catValueName = catSpinner.getSelectedItem().toString();
                    if (catValueName.equalsIgnoreCase("All")) {
                        subCatCode = "";
                        selectedDates.clear();
                        valSpinner.setAdapter(null);
                        if (!spinnerValueList.isEmpty())
                            spinnerValueList.clear();
                        listAdapter.applyFilterValue(catValueName, subCatCode, "", 0);
                    } else {
                        loadValSpinner(catValueName);
                    }
                    ((TextView) view).setTextColor(Color.WHITE);

                } catch (Exception e) {
                    Log.e(TAG, "onItemSelected: " + e.getMessage(), e);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //ignore
            }
        });
    }

    private void loadValSpinner(String catName) {
        spinnerValueList.clear();
        valueList.clear();
        valueList = db.getFilterDataByName(catName);

        for (int i = 0; i < valueList.size(); i++) {
            spinnerValueList.add(valueList.get(i).getReasonName());
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValueList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        valSpinner.setAdapter(adapter1);


        valSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                subCatCode = valueList.get(pos).getReasonCode();
                if (!selectedDates.isEmpty()) {
                    for (int i = 0; i < selectedDates.size(); i++) {
                        String strDate = DateUtil.covertDateToString(selectedDates.get(i), dateFormat);
                        listAdapter.applyFilterValue(catValueName, subCatCode,
                                String.valueOf(DateUtil.convertStringDateToTimestamp(strDate, dateFormat)), i);
                    }
                } else {
                    listAdapter.applyFilterValue(catValueName, subCatCode, "", 0);
                }
                ((TextView) view).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //ignore
            }
        });

    }

    @Override
    public Toolbar getBaseToolbar() {
        return toolbar;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.date_picker_img) {
            showCustomDatePicker();
        }
    }

    Calendar startCalendar = null;
    List<Date> selectedDates = new ArrayList<>();

    private void showCustomDatePicker() {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.date_picker_dialog_layout, null);

        final CalendarPickerView calendarView = customView.findViewById(R.id.calendarView);

        calendarView.setCustomDayView(new DefaultDayViewAdapter());

        startCalendar = Calendar.getInstance();
        int year = startCalendar.get(Calendar.YEAR);
        int month = startCalendar.get(Calendar.MONTH);
        startCalendar.set(year, month, 1);

        Calendar endCalendar = Calendar.getInstance();
        int nYear = endCalendar.get(Calendar.YEAR);
        int nMonth = endCalendar.get(Calendar.MONTH);
        int numOfDays = endCalendar.get(Calendar.DATE);
        endCalendar.set(nYear, nMonth, numOfDays + 1);

        ArrayList<Date> dates = new ArrayList<>();
        dates.add(startCalendar.getTime());
        dates.add(endCalendar.getTime());
        calendarView.setDecorators(Collections.<CalendarCellDecorator>emptyList());

        calendarView.init(startCalendar.getTime(), endCalendar.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);

        calendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                selectedDates.clear();
                selectedDates.addAll(calendarView.getSelectedDates());

            }

            @Override
            public void onDateUnselected(Date date) {
                //Do nothing
            }
        });

        calendarView.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
            @Override
            public void onInvalidDateSelected(Date date) {
                //Do Nothing
            }
        });

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(PreviousOrderBookingReportActivity.this);
        builder.setView(customView); // Set the view of the dialog to your custom layout
        builder.setTitle("Select start date and end date");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!selectedDates.isEmpty()) {
                    for (int i = 0; i < selectedDates.size(); i++) {
                        String strDate = DateUtil.covertDateToString(selectedDates.get(i), dateFormat);
                        listAdapter.applyFilterValue(catValueName, subCatCode,
                                String.valueOf(DateUtil.convertStringDateToTimestamp(strDate, dateFormat)), i);
                    }
                } else {
                    listAdapter.applyFilterValue(catValueName, subCatCode, "", 0);
                }

                dialog.dismiss();
            }
        });

        // Create and show the dialog
        builder.create().show();
    }

    public static class AsyncLoadData extends AsyncTask<Void, Void, Boolean> {

        private final WeakReference<PreviousOrderBookingReportActivity> fragmentRef;
        private final PreviousOrderBookingReportActivity activity;
        boolean boolResponse = false;

        public AsyncLoadData(PreviousOrderBookingReportActivity activity1) {
            fragmentRef = new WeakReference<>(activity1);
            activity = fragmentRef.get();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            activity.orderBookingModels.clear();
            activity.parentList.clear();
            activity.childItems.clear();
            activity.childItemsTwo.clear();
            activity.parentdata.clear();
            activity.totalNet = 0;

            try {
                activity.orderBookingModels = activity.db.fetchAllOrderBookingReport(activity.preferences.readString(SFASharedPref.PREF_LOGIN_CODE));
                activity.showCompany = activity.db.getMappedCount(IDBColumns.TABLE_MAPPED_COMPANY) > 1;

                for (OrderBookingHeaderModel model : activity.orderBookingModels) {
                    List<OrderBookingVO> subList = new ArrayList<>();
                    List<OrderStatusModel> subListTwo = new ArrayList<>();
                    if (activity.childItems.containsKey(model.getCmpCode()+model.getDistrCode()+model.getOrderNo())) {
                        List<OrderBookingVO> temp = activity.childItems.get(model.getCmpCode()+model.getDistrCode()+model.getOrderNo());
                        temp.addAll(model.getOrderBookingDetailsList());
                        subList.clear();
                        subList.addAll(temp);
                    } else {
                        subList.clear();
                        subList.addAll(model.getOrderBookingDetailsList());
                    }

                    if (activity.childItemsTwo.containsKey(model.getCmpCode()+model.getDistrCode()+model.getOrderNo())) {
                        List<OrderStatusModel> temp_two = activity.childItemsTwo.get(model.getCmpCode()+model.getDistrCode()+model.getOrderNo());
                        temp_two.addAll(model.getActionOrderStatusList());
                        subListTwo.clear();
                        subListTwo.addAll(temp_two);
                    } else {
                        subListTwo.clear();
                        subListTwo.addAll(model.getActionOrderStatusList());
                    }

                    activity.childItems.put(model.getCmpCode()+model.getDistrCode()+model.getOrderNo(), subList);
                    activity.childItemsTwo.put(model.getCmpCode()+model.getDistrCode()+model.getOrderNo(), subListTwo);

                    activity.parentList.add(model);
                    activity.parentdata.add(model.getCmpCode()+model.getDistrCode()+model.getOrderNo());
                }

                for (OrderBookingHeaderModel headerModel : activity.parentList) {
                    activity.totalNet = activity.totalNet + headerModel.getTotalOrderValue();
                }

            } catch (Exception e) {
                Log.d(TAG, "AsyncLoadData : exp : " + e);
            }
            return boolResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppUtils.getOurInstance().showProgressDialog(activity, activity.getResources().getString(R.string.MSG_LOADING));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            activity.loadDataInRecylcerView();
            AppUtils.getOurInstance().cancleProgressDialog();
            calculateGrossAmt(activity.orderBookingModels);
            if (activity.showCompany) {
                if (activity.companyLabel != null)
                    activity.companyLabel.setVisibility(View.VISIBLE);
            } else {
                if (activity.companyLabel != null)
                    activity.companyLabel.setVisibility(View.GONE);
            }
        }

        private void calculateGrossAmt(List<OrderBookingHeaderModel> orderBookingModels) {

            double grossAmt = 0;
            double netAmt = 0;
            double schemeAmt = 0;
            double taxAmt = 0;

            for (int i = 0; i < orderBookingModels.size(); i++) {
                netAmt = netAmt + orderBookingModels.get(i).getTotalOrderValue();
                schemeAmt = schemeAmt + orderBookingModels.get(i).getTotalDiscount();
                taxAmt = taxAmt + orderBookingModels.get(i).getTotalTax();
            }
            String strPattern = "{0} {1}";
//            activity.totGrossAmtTxt.setText(MessageFormat.format(strPattern, activity.getResources().getString(R.string.grossamt), String.format(Locale.getDefault(), "%.2f", grossAmt)));
            activity.totalNetAmtTxt.setText(MessageFormat.format(strPattern, activity.getResources().getString(R.string.actual_net_amt), String.format(Locale.getDefault(), "%.2f", netAmt)));
            activity.totalDiscountAmtTxt.setText(MessageFormat.format(strPattern, activity.getResources().getString(R.string.discamt), String.format(Locale.getDefault(), "%.2f", schemeAmt)));
            activity.totalTaxAmtTxt.setText(MessageFormat.format(strPattern, activity.getResources().getString(R.string.taxamt), String.format(Locale.getDefault(), "%.2f", taxAmt)));

        }
    }

    private void loadDataInRecylcerView() {

        listAdapter = new OrderBookingRepExpListAdapter(this, showCompany, parentdata, childItems, childItemsTwo, parentList, totGrossAmtTxt, totalNetAmtTxt, totalDiscountAmtTxt, totalTaxAmtTxt);
        listAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(listAdapter);

        listAdapter.setOnExpandableItemClick(new OnExpandableItemClickTrackOrder() {
            int previousGroup = -1;

            @Override
            public void setOnExpandableListClick(int groupPosition, int child) {
                if (child == 0) {
                    listAdapter.setChildCount(childItems.get(parentdata.get(groupPosition)).size() + 1);
                    listAdapter.setChild(child);
                    if (groupPosition == previousGroup) {
                        ordsumList.collapseGroup(previousGroup);
                        previousGroup = -1;
                    } else {
                        ordsumList.expandGroup(groupPosition);
                        previousGroup = groupPosition;
                    }
                } else if(child == 1){
                    listAdapter.setChildCount(childItemsTwo.get(parentdata.get(groupPosition)).size() + 1);
                    listAdapter.setChild(child);
                    if (groupPosition == previousGroup) {
                        ordsumList.collapseGroup(previousGroup);
                        previousGroup = -1;
                    } else {
                        ordsumList.expandGroup(groupPosition);
                        previousGroup = groupPosition;
                    }
                }

//                ordsumList.expandGroup(groupPosition);
            }

            @Override
            public void setOnCollapseListClick(int groupPosition, int child) {
                ordsumList.collapseGroup(groupPosition);
            }
        });


        ordsumList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                listAdapter.setChildCount(1);
                listAdapter.setChild(999);
                if (groupPosition != previousGroup) {
                    ordsumList.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }
        });

//        ordsumList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            int previousGroup = -1;
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                listAdapter.setChildCount(1);
//                if (groupPosition == previousGroup) {
//                    ordsumList.collapseGroup(previousGroup);
//                    previousGroup=-1;
//                }else
//                {
//                    ordsumList.expandGroup(groupPosition);
//                    previousGroup = groupPosition;
//                }
//
//                return true;
//            }
//        });

        if (wrapperAdapter.getGroupCount() > 0) {
            ordsumList.setAdapter(wrapperAdapter);
            ordsumList.setVisibility(View.VISIBLE);
            cartEmpty.setVisibility(View.GONE);

        } else {
            cartEmpty.setVisibility(View.VISIBLE);
            ordsumList.setVisibility(View.GONE);
        }
    }

}
