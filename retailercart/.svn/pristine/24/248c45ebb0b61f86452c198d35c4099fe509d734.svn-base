package com.botree.retailerssfa.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.library.baseAdapters.BuildConfig;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.CommonViewPagerAdapter;
import com.botree.retailerssfa.base.SFAActivity;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.main.DistributorMasterFragment;
import com.botree.retailerssfa.support.CustomViewPager;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;


public class DistributorMasterActivity extends SFAActivity implements DistributorMasterFragment.DayOffSpinnerValue {
    private static final String TAG = DistributorMasterActivity.class.getSimpleName();
    private String title;
    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private CustomViewPager masterViewpager;
    private EditText distributorName;
    EditText distributorCode;
    private Button submitButton;
    private Distributor distr;
    private SFASharedPref sfaSharedPref;
    private int tabPosition = 0;
    int dayOff = 0;
    String mobileNumber;
    SFADatabase db;
    CommonViewPagerAdapter pagerAdapter;
    LinearLayout rootLayout;


    private String distr_code = "";
    private String company_code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_master);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            title = getIntent().getStringExtra("ScreenName");
            distr_code = getIntent().getStringExtra("selected_distr_code");
            company_code = getIntent().getStringExtra("selected_distr_company_code");

        }
        setAutoScreenCount(title);
        initailize();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    disableSubmitButton();
                } else if (position == 1) {
                    enableSubmitButton();
                } else {
                    submitButton.setVisibility(View.GONE);
                }
                tabPosition = position;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Ignore
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Ignore
            }
        });
    }

    void disableSubmitButton() {
//        submitButton.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.GONE);
        if (submitButton.isEnabled()) {
            submitButton.setAlpha(0.6f);
            submitButton.setEnabled(false);
        }
    }

    void enableSubmitButton() {
//        submitButton.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.GONE);
        if (!submitButton.isEnabled()) {
            submitButton.setAlpha(1);
            submitButton.setEnabled(true);
        }
    }

    private void initailize() {
        sfaSharedPref = SFASharedPref.getOurInstance();
        db = SFADatabase.getInstance(this);

        mToolbar = findViewById(R.id.custom_toolbar);
        tabLayout = findViewById(R.id.tracker_sliding_tabs);
        masterViewpager = findViewById(R.id.distributor_master_viewpager);
        distributorName = findViewById(R.id.distributor_name);
        distributorCode = findViewById(R.id.distributor_code);
        submitButton = findViewById(R.id.submit_button);
        rootLayout = findViewById(R.id.root_layout);

        initToolbar1();
        if (tabPosition == 0) {
            disableSubmitButton();
        }

        distr = loadDistributorData();
        setViewPager(distr);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pagerAdapter != null && pagerAdapter.getItem(1) != null) {
                    if (mobileNumber.length() == 10) {
                        setDistrValues();
                    } else {
                        NotifyUtil.showSnackBar(DistributorMasterActivity.this, rootLayout, "Enter Valid Mobile Number", Snackbar.LENGTH_LONG);
                    }
                }
            }
        });
    }

    private void setDistrValues() {
        DistributorMasterFragment fragment = (DistributorMasterFragment) pagerAdapter.getItem(1);
        if (fragment != null) {
            distr.setFssaiExpiryDate(fragment.getFssaiExpiryDate());
            if (fragment.getFssaiNumber().length() == 0 || fragment.getFssaiNumber().length() == 14) {
                distr.setFssaiNumber(fragment.getFssaiNumber());

                distr.setDayOff(dayOff);
                distr.setMobileNumber(mobileNumber);
                //Update Distributor Table
                db.updateDistributorWithId(distr);
                onBackPressed();
            } else {
                NotifyUtil.showSnackBar(DistributorMasterActivity.this, rootLayout, "Please enter valid FSSAI Number.", Snackbar.LENGTH_LONG);
            }
        } else {
            NotifyUtil.showSnackBar(DistributorMasterActivity.this, rootLayout, "Some error occurred.", Snackbar.LENGTH_LONG);
        }
    }

    private void initToolbar1() {
        TextView toolbarTitle = mToolbar.findViewById(R.id.custom_toolbar_title);

        toolbarTitle.setText(Globals.NAME_MASTER_DISTR);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setViewPager(Distributor distributor) {
        if (distributor != null) {
            distributorCode.setText(distributor.getDistributorCode());
            distributorName.setText(distributor.getDistributorName());

            SFAFragment distributorAddressFragment = DistributorMasterFragment.newInstance(0, distributor);
            SFAFragment distributorOtherDetailsFragment = DistributorMasterFragment.newInstance(1, distributor);//1 = Other details layout will be visible
            SFAFragment distributorLobFragment = DistributorMasterFragment.newInstance(2, distributor);//1 = LOB details layout will be visible

            pagerAdapter = new CommonViewPagerAdapter(getSupportFragmentManager());
            pagerAdapter.addFragment(distributorAddressFragment, "Contact Info");
//            pagerAdapter.addFragment(distributorOtherDetailsFragment, "Others");
            pagerAdapter.addFragment(distributorLobFragment, "LOB");


            masterViewpager.setAdapter(pagerAdapter);
            masterViewpager.setOffscreenPageLimit(pagerAdapter.getCount());
            tabLayout.setupWithViewPager(masterViewpager);
            pagerAdapter.notifyDataSetChanged();
        }
    }

    private Distributor loadDistributorData() {
//        Distributor distributor = db.getDistributor(sfaSharedPref.readString(PREF_DISTRCODE));
        Distributor distributor = db.getDistributorInfo(distr_code, company_code);
        if (BuildConfig.DEBUG)
            Log.d(TAG, "loadDistributorData : " + new Gson().toJson(distributor));
        return distributor;
    }

    @Override
    public void onDayOffSpinnerChanged(int position) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "onDayOffSpinnerChanged : position : " + position);
        dayOff = position;
    }

    @Override
    public void onMobileNumEdited(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }
}
