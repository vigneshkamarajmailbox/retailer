package com.botree.retailerssfa.main;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.LobListAdapter;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.LobModel;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;
import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class DistributorMasterFragment extends SFAFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    int pageType = 0;
    DayOffSpinnerValue dayOffSpinnerValue;
    Distributor distributor = new Distributor();
    EditText address1;
    EditText address2;
    EditText address3;
    EditText mobileNumber;
    EditText contactNumber;
    EditText pincode;
    EditText contactPerson;
    EditText emailId;
    EditText branchCode;
    EditText branchName;
    EditText gstStateCode;
    EditText geoHierPath;
    EditText aadharNumber;
    EditText fssaiNUmber;
    EditText gstNumber;
    EditText panNumber;
    EditText type;
    EditText drugLicenceNumber;
    EditText drugLicenceExpiryDate;
    RadioButton sundayRadioButton;
    RadioButton mondayRadioButton;
    RadioButton tuesdayRadioButton;
    RadioButton wednesdayRadioButton;
    RadioButton thursdayRadioButton;
    RadioButton fridayRadioButton;
    RadioButton saturdayRadioButton;
    List<RadioButton> dayOffRadioButtonList = new ArrayList<>();
    private TextView listEmptyText;
    LinearLayout lobListHeader;
    private ImageView showDownFab;
    private NestedScrollView rootNestedScrollView;
    LinearLayout address2Layout;
    LinearLayout address3Layout;
    LinearLayout drugLicenceLayout;
    LinearLayout gstPanLayout;
    EditText fssaiExpiryDate;

    private RecyclerView lobList;
    private ArrayList<LobModel> lobDistrArrayList = new ArrayList<>();
    ImageView call;

    private SFADatabase sfaDatabase;
    private SFASharedPref sfaSharedPref;
    private Animation blinkAnim;
    ImageView ivDate;
    private String datePattern = "dd-MM-yyyy";
    private LinearLayout addressLayout;
    private LinearLayout otherDetailsLayout;
    private LinearLayout lobDetailsLayout;

    public DistributorMasterFragment() {
        // Required empty public constructor
    }

    public static DistributorMasterFragment newInstance(int pageType, Distributor distributor) {
        DistributorMasterFragment myFragment = new DistributorMasterFragment();

        Bundle args = new Bundle();
        args.putInt("TYPE", pageType);
        args.putParcelable("DATA", distributor);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageType = getArguments().getInt("TYPE", 0);
            distributor = getArguments().getParcelable("DATA");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_distributor_master, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);

        call.setColorFilter(ContextCompat.getColor(getActivity(), R.color.lightred), android.graphics.PorterDuff.Mode.MULTIPLY);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                if (distributor.getPhoneNumber() != null) {
                    callIntent.setData(Uri.parse("tel:" + distributor.getPhoneNumber()));
                    startActivity(callIntent);
                }
            }
        });
    }

    private void initialize(View view) {
        dayOffSpinnerValue = (DayOffSpinnerValue) getSFAFragmentActivity();

        sfaSharedPref = SFASharedPref.getOurInstance();
        sfaDatabase = SFADatabase.getInstance(getActivity());

        addressLayout = view.findViewById(R.id.address_layout);
        otherDetailsLayout = view.findViewById(R.id.other_details_layout);
        address1 = view.findViewById(R.id.address_1);
        address2 = view.findViewById(R.id.address_2);
        address3 = view.findViewById(R.id.address_3);
        mobileNumber = view.findViewById(R.id.mobile_number);
        contactNumber = view.findViewById(R.id.phone_number);
        pincode = view.findViewById(R.id.pincode);
        contactPerson = view.findViewById(R.id.contact_person);
        emailId = view.findViewById(R.id.mail_id);
        branchCode = view.findViewById(R.id.branch_code);
        branchName = view.findViewById(R.id.branch_name);
        gstStateCode = view.findViewById(R.id.gst_statecode);
        geoHierPath = view.findViewById(R.id.geo_hier_path);
        aadharNumber = view.findViewById(R.id.aadhar_number);
        fssaiNUmber = view.findViewById(R.id.fssai_number);
        gstNumber = view.findViewById(R.id.gst_number);
        panNumber = view.findViewById(R.id.pan_number);
        type = view.findViewById(R.id.type);
        drugLicenceNumber = view.findViewById(R.id.drug_license_number);
        drugLicenceExpiryDate = view.findViewById(R.id.expiry_date);
        call = view.findViewById(R.id.call);
        lobList = view.findViewById(R.id.lob_list);
        lobDetailsLayout = view.findViewById(R.id.lob_details_layout);
        sundayRadioButton = view.findViewById(R.id.sunday_day_off);
        mondayRadioButton = view.findViewById(R.id.monday_day_off);
        tuesdayRadioButton = view.findViewById(R.id.tuesday_day_off);
        wednesdayRadioButton = view.findViewById(R.id.wednesday_day_off);
        thursdayRadioButton = view.findViewById(R.id.thursday_day_off);
        fridayRadioButton = view.findViewById(R.id.friday_day_off);
        saturdayRadioButton = view.findViewById(R.id.saturday_day_off);
        listEmptyText = view.findViewById(R.id.lob_list_empty);
        lobListHeader = view.findViewById(R.id.lob_list_header);
        showDownFab = view.findViewById(R.id.show_down_fab);
        rootNestedScrollView = view.findViewById(R.id.root_scrollview);
        address2Layout = view.findViewById(R.id.address_2_layout);
        address3Layout = view.findViewById(R.id.address_3_layout);
        drugLicenceLayout = view.findViewById(R.id.drug_licence_layout);
        gstPanLayout = view.findViewById(R.id.gst_pan_layout);
        fssaiExpiryDate = view.findViewById(R.id.fssai_expiry_date);
        ivDate = view.findViewById(R.id.ivDate);
        lobList.setLayoutManager(new LinearLayoutManager(getActivity()));
        lobList.setItemAnimator(new DefaultItemAnimator());
        dayOffRadioButtonList.add(sundayRadioButton);
        dayOffRadioButtonList.add(mondayRadioButton);
        dayOffRadioButtonList.add(tuesdayRadioButton);
        dayOffRadioButtonList.add(wednesdayRadioButton);
        dayOffRadioButtonList.add(thursdayRadioButton);
        dayOffRadioButtonList.add(fridayRadioButton);
        dayOffRadioButtonList.add(saturdayRadioButton);
        blinkAnim = AppUtils.getOurInstance().loadBlinkAnim();

        Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), DistributorMasterFragment.this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

        mobileNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // ignored
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // ignored
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dayOffSpinnerValue.onMobileNumEdited(s.toString());
            }
        });

        if (pageType == 0) {
            setpageType(pageType);
        } else if (pageType == 1) {
            setpageType(pageType);
        } else {
            addressLayout.setVisibility(GONE);
            otherDetailsLayout.setVisibility(GONE);
            lobDetailsLayout.setVisibility(View.VISIBLE);
            loadlobMasterArrayList();
        }

        address1.setText(distributor.getDistributorAddress1());
        address2.setText(distributor.getDistributorAddress2());
        address3.setText(distributor.getDistributorAddress3());
        mobileNumber.setText(distributor.getMobileNumber());
        contactNumber.setText(distributor.getPhoneNumber());
        pincode.setText(distributor.getPincode());
        contactPerson.setText(distributor.getContactPerson());
        emailId.setText(distributor.getMailId());
        branchCode.setText(distributor.getBranchCode());
        branchName.setText(distributor.getBranchName());
        aadharNumber.setText(distributor.getAadharNumber());
        fssaiNUmber.setText(distributor.getFssaiNumber());
        gstNumber.setText(distributor.getGstinNumber());
        panNumber.setText(distributor.getPanNumber());
        type.setText(distributor.getDistributorTypeName());
        drugLicenceNumber.setText(distributor.getDrugLicenceNumber1());
        drugLicenceExpiryDate.setText(DateUtil.covertTimeStampIntoData(distributor.getDrugLicenceExpiryDate1(),
                datePattern));
        fssaiExpiryDate.setText(DateUtil.covertTimeStampIntoData(distributor.getFssaiExpiryDate(),
                datePattern));
        sundayRadioButton.setOnClickListener(this);
        mondayRadioButton.setOnClickListener(this);
        tuesdayRadioButton.setOnClickListener(this);
        wednesdayRadioButton.setOnClickListener(this);
        thursdayRadioButton.setOnClickListener(this);
        fridayRadioButton.setOnClickListener(this);
        saturdayRadioButton.setOnClickListener(this);

        showDownFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNestedScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        rootNestedScrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

        rootNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView scrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // We take the last son in the scrollview
                View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
                int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

                // if diff is zero, then the bottom has been reached
                if (diff == 0) {
                    // do stuff
                    showDownFab.clearAnimation();
                    showDownFab.setVisibility(GONE);
                } else {
                    showDownFab.startAnimation(blinkAnim);
                    showDownFab.setVisibility(View.VISIBLE);
                }
            }
        });

        drugLicenceExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(fssaiExpiryDate);
            }
        });
    }

    private void setpageType(int pageType) {
        if (pageType == 0) {
            addressLayout.setVisibility(View.VISIBLE);
            otherDetailsLayout.setVisibility(GONE);
            lobDetailsLayout.setVisibility(GONE);

            if (distributor.getDistributorAddress2().isEmpty()) {
                address2Layout.setVisibility(GONE);
            }
            if (distributor.getDistributorAddress3().isEmpty()) {
                address3Layout.setVisibility(GONE);
            }

            gstStateCode.setText(distributor.getGstStateCode() + " - " +
                    sfaDatabase.getGstStateNameBasedOnCode(distributor.getGstStateCode()));

            geoHierPath.setText(sfaDatabase.getGeoHierPathValue(distributor.getGeoHierPath()));

        } else if (pageType == 1) {
            addressLayout.setVisibility(GONE);
            otherDetailsLayout.setVisibility(View.VISIBLE);
            lobDetailsLayout.setVisibility(GONE);

            for (int i = 0; i < dayOffRadioButtonList.size(); i++) {
                if (i == distributor.getDayOff()) {
                    dayOffRadioButtonList.get(i).setChecked(true);
                    dayOffRadioButtonList.get(i).jumpDrawablesToCurrentState();
                } else {
                    dayOffRadioButtonList.get(i).setChecked(false);
                }
            }

            setDistrFields();
        }
    }

    private void setDistrFields() {
        if (distributor.getDrugLicenceNumber1().isEmpty()) {
            drugLicenceLayout.setVisibility(GONE);
        }
        if (!distributor.getGstDistributorType().equalsIgnoreCase("R")) {
            gstPanLayout.setVisibility(GONE);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
        // ignored
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        for (int i = 0; i < dayOffRadioButtonList.size(); i++) {
            if (dayOffRadioButtonList.get(i).getId() == id) {
                dayOffRadioButtonList.get(i).setChecked(true);
                dayOffRadioButtonList.get(i).jumpDrawablesToCurrentState();
                dayOffSpinnerValue.onDayOffSpinnerChanged(i);
            } else {
                dayOffRadioButtonList.get(i).setChecked(false);
            }
        }
    }

    private void showDateDialog(final TextView expiryTxt) {

        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Display Selected date in textbox

                String strMonth = "";
                String strDay = "";
                String monthofYear = String.valueOf(monthOfYear + 1);

                try {
                    if (String.valueOf(monthOfYear + 1).length() == 1) {
                        strMonth = String.valueOf("0" + monthofYear);
                    } else {
                        strMonth = String.valueOf(monthofYear);
                    }

                    if (String.valueOf(dayOfMonth).length() == 1) {
                        strDay = String.valueOf("0" + dayOfMonth);
                    } else {
                        strDay = String.valueOf(dayOfMonth);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onDateSet: " + e.getMessage(), e);
                }

                String date = strDay + "-" + strMonth + "-" + year;

                if (expiryTxt != null)
                    expiryTxt.setText(date);

                distributor.setFssaiExpiryDate(DateUtil.convertStringToDate(date + " 00:00"));

            }
        }, mYear, mMonth, mDay);
        dpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //ignored
            }
        });

        dpd.show();
    }

    public interface DayOffSpinnerValue {
        void onDayOffSpinnerChanged(int position);

        void onMobileNumEdited(String mobileNumber);
    }

    void loadlobMasterArrayList() {
        lobDistrArrayList.clear();
        lobDistrArrayList.addAll(sfaDatabase.getAllDistributorLob(distributor.getCompanyCode(), distributor.getDistributorCode()));

        loadRecyclerView();
    }

    void loadRecyclerView() {
        LobListAdapter lobListAdapter = new LobListAdapter(getActivity(), lobDistrArrayList);
        lobList.setAdapter(lobListAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        lobList.addItemDecoration(decoration);

        if (!lobDistrArrayList.isEmpty()) {
            lobListHeader.setVisibility(View.VISIBLE);
            lobList.setVisibility(View.VISIBLE);
            listEmptyText.setVisibility(GONE);
        } else {
            lobListHeader.setVisibility(GONE);
            lobList.setVisibility(GONE);
            listEmptyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (rootNestedScrollView.canScrollVertically(1) || rootNestedScrollView.canScrollVertically(-1)) {
            showDownFab.startAnimation(blinkAnim);
            showDownFab.setVisibility(View.VISIBLE);
        } else {
            showDownFab.setVisibility(GONE);
        }
    }

    public String getFssaiExpiryDate() {
        String strDate = fssaiExpiryDate.getText().toString() + " 00:00";
        if (!TextUtils.isEmpty(strDate)) {
            DateFormat formatter = new SimpleDateFormat(datePattern, Locale.US);
            Date date = null;
            try {
                date = formatter.parse(strDate);
            } catch (ParseException e) {
                Log.d(TAG, "getFssaiExpiryDate : exp : " + e);
            }
            if (date != null)
                return String.valueOf(date.getTime());
            else
                return "";
        } else {
            return "";
        }

    }

    public String getFssaiNumber() {
        return fssaiNUmber.getText().toString();
    }
}
