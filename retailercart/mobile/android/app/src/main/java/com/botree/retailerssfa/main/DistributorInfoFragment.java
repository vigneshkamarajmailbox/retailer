package com.botree.retailerssfa.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.OnRecyclerItemClick;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.main.MainActivity;
import com.botree.retailerssfa.models.Distributor;
import com.botree.retailerssfa.main.DistributorMasterActivity;
import com.botree.retailerssfa.adapters.DistributorInfoAdapter;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.SFASharedPref;

import java.util.ArrayList;

public class DistributorInfoFragment extends SFAFragment implements OnRecyclerItemClick {
    private RecyclerView mRecyclerDistributorInfo;
    private SFASharedPref preferences;
    private ConstraintLayout rootLayout;
    private TextView no_distributors_tv;
    private ArrayList<Distributor> distributorArrayList = new ArrayList<>();
    private SFADatabase db;

    public DistributorInfoFragment() {
        // Requires empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        preferences = SFASharedPref.getOurInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_distributor_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAutoScreenCount(Globals.NAME_DISTRIBUTOR_INFO);

        initViews(view);
        getData();
        initRecyclerView();

        ((MainActivity) getSFAFragmentActivity()).showBottomNevigation();
    }

    @Override
    public void onItemClick(int position) {
        Intent in = new Intent(getActivity(), DistributorMasterActivity.class);
        in.putExtra("selected_distr_company_code", distributorArrayList.get(position).getCompanyCode());
        in.putExtra("selected_distr_code", distributorArrayList.get(position).getDistributorCode());
        in.putExtra("ScreenName", Globals.NAME_MASTER_DISTR);
        startActivity(in);
    }


    private void initViews(View view) {
        db = SFADatabase.getInstance(getActivity());
        rootLayout = view.findViewById(R.id.root_layout);
        no_distributors_tv = view.findViewById(R.id.tv_no_distributors);
        mRecyclerDistributorInfo = view.findViewById(R.id.rv_distributor_info);

    }

    private void initRecyclerView() {
        mRecyclerDistributorInfo.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        mRecyclerDistributorInfo.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mRecyclerDistributorInfo.addItemDecoration(decoration);
        DistributorInfoAdapter distributorInfoAdapter = new DistributorInfoAdapter(getContext(),distributorArrayList, this);
        mRecyclerDistributorInfo.setAdapter(distributorInfoAdapter);
    }

    private void getData() {
        distributorArrayList = new ArrayList<>();
        if (db.isProdAvailable()) {
            no_distributors_tv.setVisibility(View.GONE);
            mRecyclerDistributorInfo.setVisibility(View.VISIBLE);
            distributorArrayList = db.getDistributorList();
        } else {
            no_distributors_tv.setVisibility(View.VISIBLE);
            mRecyclerDistributorInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_login);
        item.setVisible(false);
    }

}
