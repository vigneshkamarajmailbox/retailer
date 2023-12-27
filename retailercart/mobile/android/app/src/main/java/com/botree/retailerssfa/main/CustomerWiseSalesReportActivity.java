package com.botree.retailerssfa.main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.CustomerWiseSalesReportAdapter;
import com.botree.retailerssfa.base.BaseActivity;
import com.botree.retailerssfa.customcalendarview.CalendarCellDecorator;
import com.botree.retailerssfa.customcalendarview.CalendarPickerView;
import com.botree.retailerssfa.customcalendarview.DefaultDayViewAdapter;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.MTDReportModel;
import com.botree.retailerssfa.support.TopSheetBehavior;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.SFASharedPref;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.botree.retailerssfa.support.Globals.NAME_CUSTOMER_WISE_REPORT_DETAIL;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;

public class CustomerWiseSalesReportActivity extends BaseActivity {
    private static String TAG = CustomerWiseSalesReportActivity.class.getSimpleName();
    private Toolbar toolbar;
    private SFADatabase db;
    private SFASharedPref preferences;
    private Spinner customerSpinner;
    private Spinner channelSpinner;
    private Spinner subChannelSpinner;
    private Spinner groupSpinner;
    private Spinner classSpinner;
    private List<MTDReportModel> arrayChannel = new ArrayList<>();
    private List<MTDReportModel> arrayClass = new ArrayList<>();
    private List<MTDReportModel> arrayGroup = new ArrayList<>();
    private List<MTDReportModel> arraySubChannel = new ArrayList<>();
    private List<MTDReportModel> arrayCustomer = new ArrayList<>();

    private List<String> arrayChannelSpinner = new ArrayList<>();
    private List<String> arrayClassSpinner = new ArrayList<>();
    private List<String> arrayGroupSpinner = new ArrayList<>();
    private List<String> arraySubChannelSpinner = new ArrayList<>();
    private List<String> arrayCustomerSpinner = new ArrayList<>();
    private String customerCode;
    private String channelCode;
    private String subChannelCode;
    private String groupCode;
    private String classCode;

    List<MTDReportModel> customerWiseReportModel;
    private RecyclerView recyclerView;
    private TextView emptyTxt;
    CustomerWiseSalesReportAdapter adapter;
    private String channelCodeStr;
    private String subChannelCodeStr;
    private String groupCodeStr;
    private String classCodeStr;
    private String customerCodeStr;
    private View topSheet;
    private Button apply;
    private Button cancel;
    private Button all;
    private int mYear = -1;
    private int mMonth = -1;
    private int mDay = -1;
    private int selectedDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_wise_sales_report);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        db = SFADatabase.getInstance(CustomerWiseSalesReportActivity.this);
        preferences = SFASharedPref.getOurInstance();
        initTitleBar();
        initialize();
        customerWiseReportModel = new ArrayList<>();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new AsyncLoadDataFromDb(CustomerWiseSalesReportActivity.this).execute();
            }
        }, 200);
    }

    private void initialize() {
        emptyTxt = findViewById(R.id.tvEmpty);
        recyclerView = findViewById(R.id.rvList);
        recyclerView.setLayoutManager(new LinearLayoutManager(CustomerWiseSalesReportActivity.this));
        recyclerView.setNestedScrollingEnabled(false);

        topSheet = findViewById(R.id.top_sheet);

        customerSpinner = findViewById(R.id.spinner_customer);
        channelSpinner = findViewById(R.id.spinner_channel);
        subChannelSpinner = findViewById(R.id.spinner_sub_channel);
        groupSpinner = findViewById(R.id.spinner_group);
        classSpinner = findViewById(R.id.spinner_class);
        apply = findViewById(R.id.apply_btn);
        cancel = findViewById(R.id.cancel_btn);
        all = findViewById(R.id.all_btn);
    }

    private void addSpinnerSelectedListener() {
        customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    customerCode = arrayCustomer.get(pos).getCustomerCode();
                } else {
                    customerCode = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // ignored
            }
        });

        channelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    channelCode = arrayChannel.get(pos).getChannelCode();
                    loadGroupSpinner("");
                    loadClassSpinner("", "");
                    loadCustomerSpinner(channelCode, "", "", "");
                    loadSubChannelSpinner(channelCode);

                } else {
                    channelCode = null;
                    subChannelCode = null;
                    groupCode = null;
                    classCode = null;
                    loadSubChannelSpinner("");
                    loadGroupSpinner("");
                    loadClassSpinner("", "");
                    loadCustomerData(CustomerWiseSalesReportActivity.this, true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // ignored
            }
        });

        subChannelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    subChannelCode = arraySubChannel.get(pos).getSubChannelCode();
                    loadGroupSpinner(subChannelCode);
                    groupSpinner.setClickable(true);
                } else {
                    if (channelCode != null && !channelCode.isEmpty()) {
                        subChannelCode = null;
                        groupCode = null;
                        classCode = null;
                        loadGroupSpinner("");
                        loadClassSpinner("", "");
                        loadCustomerSpinner(channelCode, "", "", "");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // ignored
            }
        });

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                setGroupSpinner(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // ignored
            }
        });

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                setClassSpinner(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // ignored
            }
        });
    }

    private void setGroupSpinner(int pos) {
        if (pos != 0) {

            groupCode = arrayGroup.get(pos).getGroupCode();
            loadClassSpinner(channelCode, groupCode);
            groupSpinner.setClickable(true);
        } else {
            if (channelCode != null && !channelCode.isEmpty()) {
                groupCode = null;
                classCode = null;
                loadClassSpinner("", "");
                loadCustomerSpinner(channelCode, subChannelCode, "", "");
            }
        }
    }

    private void setClassSpinner(int pos) {
        classCode = arrayClass.get(pos).getClassCode();
        if (!classCode.equals("0")) {
            loadCustomerSpinner(channelCode, subChannelCode, groupCode, classCode);
        } else if (channelCode != null && !channelCode.isEmpty() && (subChannelCode == null || subChannelCode.isEmpty())
                && (groupCode == null || groupCode.isEmpty()) && classCode.equals("0")) {
            loadCustomerSpinner(channelCode, "", "", "");
        } else if (channelCode != null && !channelCode.isEmpty() && subChannelCode != null && !subChannelCode.isEmpty() &&
                (groupCode == null || groupCode.isEmpty())) {
            loadCustomerSpinner(channelCode, subChannelCode, "", "");
        } else if (channelCode != null && !channelCode.isEmpty() && subChannelCode != null && !subChannelCode.isEmpty() &&
                groupCode != null && !groupCode.isEmpty() && classCode.equals("0")) {
            loadCustomerSpinner(channelCode, subChannelCode, groupCode, "");
        }
    }

    private void initTitleBar() {
        toolbar = findViewById(R.id.custom_toolbar);
        initToolbar();
        setBaseToolbarTitle(NAME_CUSTOMER_WISE_REPORT_DETAIL, null);
    }

    @Override
    public Toolbar getBaseToolbar() {
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();

        getMenuInflater().inflate(R.menu.menu_customer_wise_filter, menu);
        MenuItem item = menu.findItem(R.id.menu_date);
        MenuItem item1 = menu.findItem(R.id.mtd_retailer_search);
        item.setVisible(true);
        item1.setVisible(true);

        final SearchView searchView = (SearchView) menu.findItem(R.id.mtd_retailer_search).getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TopSheetBehavior.from(topSheet).getState() == TopSheetBehavior.STATE_EXPANDED) {
                    TopSheetBehavior.from(topSheet).setState(TopSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText.toLowerCase();
                if (text.trim().length() >= 0) {
                    adapter.applySearchFilter(text);
                }
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_filter:
                invalidateOptionsMenu();
                hideKeyboard();
                showFilterDialog();
                return true;

            case R.id.menu_date:
                invalidateOptionsMenu();
                hideKeyboard();
                if (TopSheetBehavior.from(topSheet).getState() == TopSheetBehavior.STATE_EXPANDED) {
                    TopSheetBehavior.from(topSheet).setState(TopSheetBehavior.STATE_COLLAPSED);
                }
                if (mDay == -1 || mMonth == -1 || mYear == -1)
                    loadCalendar();
                showCustomDatePicker();
                return true;

            default:
                return super.onOptionsItemSelected(item);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerWiseSalesReportActivity.this);
        builder.setView(customView); // Set the view of the dialog to your custom layout
        builder.setTitle("Select start date and end date");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < selectedDates.size(); i++) {
                    adapter.applyFilterValue(channelCodeStr, subChannelCodeStr, groupCodeStr, classCodeStr, customerCodeStr, selectedDates.get(i).getDate(), i);
                    updateAdapterData();
                }
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        builder.create().show();
    }

    private void loadCalendar() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    private void showFilterDialog() {

        if (TopSheetBehavior.from(topSheet).getState() == TopSheetBehavior.STATE_COLLAPSED) {
            TopSheetBehavior.from(topSheet).setState(TopSheetBehavior.STATE_EXPANDED);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new AsyncDataLoad(CustomerWiseSalesReportActivity.this).execute();
                }
            }, 200);

            topSheet.setBackgroundColor(ContextCompat.getColor(CustomerWiseSalesReportActivity.this, R.color.color_semi_transparent));
        } else {
            TopSheetBehavior.from(topSheet).setState(TopSheetBehavior.STATE_COLLAPSED);
        }

        addSpinnerSelectedListener();

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                channelCodeStr = channelCode;
                subChannelCodeStr = subChannelCode;
                groupCodeStr = groupCode;
                classCodeStr = classCode;
                customerCodeStr = customerCode;
                if (channelCodeStr != null || subChannelCodeStr != null || groupCodeStr != null || !classCodeStr.equals("0") || customerCodeStr != null || !selectedDates.isEmpty()) {
                    if (selectedDates.isEmpty()) {
                        adapter.applyFilterValue(channelCodeStr, subChannelCodeStr, groupCodeStr, classCodeStr, customerCodeStr, selectedDay, 0);
                        updateAdapterData();
                    } else {
                        for (int i = 0; i < selectedDates.size(); i++) {
                            adapter.applyFilterValue(channelCodeStr, subChannelCodeStr, groupCodeStr, classCodeStr, customerCodeStr, selectedDates.get(i).getDate(), i);
                            updateAdapterData();
                        }
                    }
                }
                TopSheetBehavior.from(topSheet).setState(TopSheetBehavior.STATE_COLLAPSED);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopSheetBehavior.from(topSheet).setState(TopSheetBehavior.STATE_COLLAPSED);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerWiseReportModel = new ArrayList<>();
                channelCodeStr = null;
                subChannelCodeStr = null;
                groupCodeStr = null;
                classCodeStr = null;
                customerCodeStr = null;
                selectedDay = 0;
                new AsyncLoadDataFromDb(CustomerWiseSalesReportActivity.this).execute();
                TopSheetBehavior.from(topSheet).setState(TopSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private class AsyncDataLoad extends AsyncTask<Void, Void, Boolean> {
        private final WeakReference<CustomerWiseSalesReportActivity> activityReference;
        boolean boolResponse = false;

        public AsyncDataLoad(CustomerWiseSalesReportActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            CustomerWiseSalesReportActivity activity = activityReference.get();
            activity.arrayChannel = activity.db.loadCustomerChannel(activity.preferences.readString(PREF_DISTRCODE));
            if (activity.channelCode == null || activity.channelCode.isEmpty()) {
                activity.arrayCustomer = activity.db.loadCustomerData(activity.preferences.readString(PREF_DISTRCODE));
            }
            return boolResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CustomerWiseSalesReportActivity activity = activityReference.get();
            AppUtils.getOurInstance().showProgressDialog(activity, activity.getResources().getString(R.string.MSG_LOADING));
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            CustomerWiseSalesReportActivity activity = activityReference.get();
            AppUtils.getOurInstance().cancleProgressDialog();
            loadChannelSpinner(activity);
        }

        private void loadChannelSpinner(CustomerWiseSalesReportActivity activity) {
            activity.arrayChannelSpinner.clear();

            MTDReportModel retailerVO = new MTDReportModel();
            retailerVO.setChannelName("Choose Channel");
            retailerVO.setChannelCode("0");
            if (activity.arrayChannel == null)
                activity.arrayChannel = new ArrayList<>();
            activity.arrayChannel.add(0, retailerVO);
            for (int i = 0; i < activity.arrayChannel.size(); i++) {
                activity.arrayChannelSpinner.add(activity.arrayChannel.get(i).getChannelName());
            }

            ArrayAdapter<String> channelAdapter = new ArrayAdapter<>(activity, R.layout.spinner_list_item, activity.arrayChannelSpinner);
            channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            activity.channelSpinner.setAdapter(channelAdapter);
            activity.channelSpinner.setSelection(0);

            for (int k = 0; k < arrayChannel.size(); k++) {

                if (channelCodeStr != null && !channelCodeStr.isEmpty() && arrayChannel.get(k).getChannelCode().trim().equalsIgnoreCase(channelCodeStr)) {
                    channelSpinner.setSelection(k);

                    loadSubChannelSpinner(arrayChannel.get(k).getChannelCode());
                }
            }
        }
    }

    private void loadCustomerData(CustomerWiseSalesReportActivity activity, boolean status) {
        activity.arrayCustomerSpinner.clear();
        if (status) {
            activity.arrayCustomer.clear();
            activity.arrayCustomer = activity.db.loadCustomerData(activity.preferences.readString(PREF_DISTRCODE));
        }
        MTDReportModel retailerVO = new MTDReportModel();
        retailerVO.setCustomerName("Choose Customer");
        retailerVO.setCustomerCode("0");
        if (activity.arrayCustomer == null)
            activity.arrayCustomer = new ArrayList<>();
        activity.arrayCustomer.add(0, retailerVO);
        for (int i = 0; i < activity.arrayCustomer.size(); i++) {
            activity.arrayCustomerSpinner.add(activity.arrayCustomer.get(i).getCustomerName());
        }

        ArrayAdapter<String> customerAdapter = new ArrayAdapter<>(activity, R.layout.spinner_list_item, activity.arrayCustomerSpinner);
        customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activity.customerSpinner.setAdapter(customerAdapter);
        activity.customerSpinner.setSelection(0);

        for (int k = 0; k < arrayCustomer.size(); k++) {

            if (customerCodeStr != null && !customerCodeStr.isEmpty() && arrayCustomer.get(k).getCustomerCode().trim().equalsIgnoreCase(customerCodeStr)) {
                customerSpinner.setSelection(k);
            }
        }
    }

    private void loadCustomerSpinner(String channelCode, String subChannelCode, String groupCode, String classCode) {
        arrayCustomerSpinner.clear();
        arrayCustomer = db.loadCustomerWise(preferences.readString(PREF_DISTRCODE), channelCode, subChannelCode, groupCode, classCode);
        MTDReportModel retailerVO = new MTDReportModel();
        retailerVO.setCustomerName("Choose Customer");
        retailerVO.setCustomerCode("0");
        if (arrayCustomer == null)
            arrayCustomer = new ArrayList<>();
        arrayCustomer.add(0, retailerVO);
        for (int i = 0; i < arrayCustomer.size(); i++) {
            arrayCustomerSpinner.add(arrayCustomer.get(i).getCustomerName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CustomerWiseSalesReportActivity.this, R.layout.spinner_list_item, arrayCustomerSpinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerSpinner.setAdapter(arrayAdapter);
        customerSpinner.setSelection(0);

        for (int k = 0; k < arrayCustomer.size(); k++) {

            if (customerCodeStr != null && !customerCodeStr.isEmpty() && arrayCustomer.get(k).getCustomerCode().trim().equalsIgnoreCase(customerCodeStr)) {
                customerSpinner.setSelection(k);
            }
        }
    }

    private void loadSubChannelSpinner(String strChannel) {

        try {
            arraySubChannelSpinner.clear();

            arraySubChannel = db.loadCustomerSubChannel(preferences.readString(PREF_DISTRCODE), strChannel);

            MTDReportModel retailerVO = new MTDReportModel();
            retailerVO.setSubChannelName("Choose Sub-Channel");
            retailerVO.setSubChannelCode("0");
            if (arraySubChannel == null)
                arraySubChannel = new ArrayList<>();
            arraySubChannel.add(0, retailerVO);

            for (int i = 0; i < arraySubChannel.size(); i++) {
                arraySubChannelSpinner.add(arraySubChannel.get(i).getSubChannelName());
            }

            ArrayAdapter<String> subChanneladapter = new ArrayAdapter<>(CustomerWiseSalesReportActivity.this, R.layout.spinner_list_item, arraySubChannelSpinner);
            subChanneladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subChannelSpinner.setAdapter(subChanneladapter);
            subChannelSpinner.setSelection(0);

            for (int k = 0; k < arraySubChannel.size(); k++) {
                if (subChannelCodeStr != null && !subChannelCodeStr.isEmpty() && arraySubChannel.get(k).getSubChannelCode().trim().equalsIgnoreCase(subChannelCodeStr)) {
                    subChannelSpinner.setSelection(k);

                    loadGroupSpinner(arraySubChannel.get(k).getSubChannelCode());
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "loadSubChannelSpinner: " + e.getMessage(), e);
        }

    }

    private void loadGroupSpinner(String strChannel) {

        arrayGroupSpinner.clear();

        arrayGroup = db.loadCustomerGroupData(preferences.readString(PREF_DISTRCODE), strChannel);

        MTDReportModel retailerVO = new MTDReportModel();
        retailerVO.setGroupName("Choose Group");
        retailerVO.setGroupCode("0");
        if (arrayGroup == null)
            arrayGroup = new ArrayList<>();
        arrayGroup.add(0, retailerVO);

        for (int i = 0; i < arrayGroup.size(); i++) {
            arrayGroupSpinner.add(arrayGroup.get(i).getGroupName());

        }

        ArrayAdapter<String> groupadapter = new ArrayAdapter<>(CustomerWiseSalesReportActivity.this, R.layout.spinner_list_item, arrayGroupSpinner);
        groupadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(groupadapter);
        groupSpinner.setSelection(0);

        for (int k = 0; k < arrayGroup.size(); k++) {
            if (groupCodeStr != null && !groupCodeStr.isEmpty() && arrayGroup.get(k).getGroupCode().trim().equalsIgnoreCase(groupCodeStr)) {
                groupSpinner.setSelection(k);

                loadClassSpinner(channelCode, arrayGroup.get(k).getGroupCode());
            }
        }
    }

    private void loadClassSpinner(String strChannel, String groupcode) {
        arrayClassSpinner.clear();
        arrayClass = db.loadCustomerClassData(preferences.readString(PREF_DISTRCODE), strChannel, groupcode);

        MTDReportModel retailerVO = new MTDReportModel();
        if (arrayClass.size() == 1) {
            retailerVO.setClassName(arrayClass.get(0).getClassName());
            retailerVO.setClassCode(arrayClass.get(0).getClassCode());
            arrayClassSpinner.add(arrayClass.get(0).getClassName());
        } else {
            retailerVO.setClassName("Choose Class");
            retailerVO.setClassCode("0");
            arrayClass.add(0, retailerVO);


            for (int i = 0; i < arrayClass.size(); i++) {
                arrayClassSpinner.add(arrayClass.get(i).getClassName());

            }
        }
        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(CustomerWiseSalesReportActivity.this, R.layout.spinner_list_item, arrayClassSpinner);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);
        classSpinner.setSelection(0);

        setRetainClassSpinner();

    }

    private void setRetainClassSpinner() {
        for (int k = 0; k < arrayClass.size(); k++) {
            if (classCodeStr != null && !classCodeStr.isEmpty() && arrayClass.get(k).getClassCode().trim().equalsIgnoreCase(classCodeStr)) {
                classSpinner.setSelection(k);

                if (!classCodeStr.equals("0")) {
                    loadCustomerSpinner(channelCode, subChannelCode, groupCode, arrayClass.get(k).getClassCode());
                }
            }
        }
    }

    private class AsyncLoadDataFromDb extends AsyncTask<Void, Void, Boolean> {
        private final WeakReference<CustomerWiseSalesReportActivity> activityReference;
        boolean boolResponse = false;

        public AsyncLoadDataFromDb(CustomerWiseSalesReportActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            CustomerWiseSalesReportActivity activity = activityReference.get();
            activity.customerWiseReportModel = activity.db.getCustomerWiseReport();
            return boolResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            CustomerWiseSalesReportActivity activity = activityReference.get();
            AppUtils.getOurInstance().showProgressDialog(activity, activity.getResources().getString(R.string.MSG_LOADING));
        }

        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            CustomerWiseSalesReportActivity activity = activityReference.get();
            AppUtils.getOurInstance().cancleProgressDialog();
            setDataToRecyclerView(activity.customerWiseReportModel);
        }

        private void setDataToRecyclerView(List<MTDReportModel> customerWiseReportModel) {
            recyclerView.getRecycledViewPool().clear();

            adapter = new CustomerWiseSalesReportAdapter(customerWiseReportModel);
            updateAdapterData();

        }
    }

    private void updateAdapterData() {
        if (adapter.getItemCount() > 0) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            emptyTxt.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            emptyTxt.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(CustomerWiseSalesReportActivity.this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
