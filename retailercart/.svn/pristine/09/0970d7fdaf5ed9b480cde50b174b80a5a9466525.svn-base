package com.botree.retailerssfa.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.OnRecyclerItemClick;
import com.botree.retailerssfa.adapters.SchemeDistributorListAdapter;
import com.botree.retailerssfa.base.BaseActivity;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.util.SFASharedPref;

import java.util.ArrayList;

public class SchemeDistributorListActivity extends BaseActivity implements View.OnClickListener, OnRecyclerItemClick {

    private RecyclerView mRecyclerSchemeDistributorList;
    private SFASharedPref preferences;
    private ConstraintLayout rootLayout;
    private TextView no_data_list_tv;
//    private ArrayList<SchemeModel> schemeModelArrayList = new ArrayList<>();
    private ArrayList<Distributor> distributorArrayList = new ArrayList<>();

    private SFADatabase db;

    private String value;
    private Toolbar mToolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_distributor_list);

        initViews();
        getData();
        initRecyclerView();

    }

    private void initViews() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = getIntent().getStringExtra("ScreenName");
        }
        setAutoScreenCount(value);

        mToolbar = findViewById(R.id.custom_toolbar);
        initToolbar();
        setBaseToolbarTitle(value, null);

        db = SFADatabase.getInstance(this);
        preferences = SFASharedPref.getOurInstance();
        rootLayout = findViewById(R.id.root_layout);
        no_data_list_tv = findViewById(R.id.tv_no_data);
        mRecyclerSchemeDistributorList = findViewById(R.id.rv_scheme_distributor_list);
    }

    private void initRecyclerView() {
        mRecyclerSchemeDistributorList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerSchemeDistributorList.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerSchemeDistributorList.addItemDecoration(decoration);
        SchemeDistributorListAdapter schemeDistributorListAdapter = new SchemeDistributorListAdapter(this, distributorArrayList, this);
        mRecyclerSchemeDistributorList.setAdapter(schemeDistributorListAdapter);
    }

    private void getData() {
//        schemeModelArrayList = new ArrayList<>();
        distributorArrayList = new ArrayList<>();
        if (db.isCustomerSchemeAvailable()) {
            no_data_list_tv.setVisibility(View.GONE);
            mRecyclerSchemeDistributorList.setVisibility(View.VISIBLE);
//            schemeModelArrayList = db.getCustomerSchemeProductsList();
            distributorArrayList = db.getSchemeDistrList();
            System.out.println("distributorArrayList"+distributorArrayList.toString());

        } else {
            no_data_list_tv.setVisibility(View.VISIBLE);
            mRecyclerSchemeDistributorList.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public Toolbar getBaseToolbar() {
        return mToolbar;
    }


    @Override
    public void onItemClick(int position) {
//        String cmp_code = schemeModelArrayList.get(position).getCmpCode();
//        String distr_code = schemeModelArrayList.get(position).getDistrCode();

        String cmp_code = distributorArrayList.get(position).getCompanyCode();
        String distr_code = distributorArrayList.get(position).getDistributorCode();

        Intent in = new Intent(this, RetailerSchemeUtilizationActivity.class);
        in.putExtra("screen", value);
        in.putExtra("cmp_code", cmp_code);
        in.putExtra("distr_code", distr_code);
        startActivity(in);
    }
}
