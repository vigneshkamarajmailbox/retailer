package com.botree.retailerssfa.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.SchemePagerAdapter;
import com.botree.retailerssfa.adapters.SchemeUtilizationAdapter;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.util.ArrayList;
import java.util.List;

public class RetailerSchemeUtilizationActivity extends AppCompatActivity {
    private static final String TAG = RetailerSchemeUtilizationActivity.class.getSimpleName();
    private SFADatabase db;
    private SFASharedPref preferences;
    private String retrCode;
    private List<SchemeModel> models;
    private String screenName;
    private TextView emptyTxt;
    private RecyclerView schemeRecyclerView;
    private TextView schemeCodeTxt;
    private TextView schemeDescTxt;
    private TextView schemeUtiInvoiceTxt;
    private TextView schemeUtiValueTxt;
    private TextView schemePayoutTxt;
    private TextView schemeTypeTxt;
    private TextView schemeEndDateTxt;
    private TextView schemeStartDateTxt;

    private CardView cardViewRoot;

    private String cmp_code = "", distr_code = "";

    private int schemePosition = -1;
    private List<SchemeModel> uniqueSchemes;
    private boolean isSchemeDialogShowing = false;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_scheme_utilization);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        db = SFADatabase.getInstance(this);
        preferences = SFASharedPref.getOurInstance();

        try {
            if (getIntent().getExtras() != null) {
                screenName = getIntent().getStringExtra("screen");
                cmp_code = getIntent().getStringExtra("cmp_code");
                distr_code = getIntent().getStringExtra("distr_code");

//                retrCode = getIntent().getStringExtra("retailerCode");
//                retilerName = getIntent().getStringExtra("retailerName");
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage(), e);
        }
        initToolBar();
        initailizeView();

        loadSchemeList();
    }

    private void initailizeView() {

        cardViewRoot = findViewById(R.id.scheme_detail_card);

        schemeUtiInvoiceTxt = findViewById(R.id.scheme_util_nofoInvoice_txt);
        schemeUtiValueTxt = findViewById(R.id.scheme_util_total_value_txt);
        emptyTxt = findViewById(R.id.scheme_util_empty_txt);
        schemeDescTxt = findViewById(R.id.scheme_name_txt);
        schemeCodeTxt = findViewById(R.id.scheme_code_txt);
        schemePayoutTxt = findViewById(R.id.scheme_payout_txt);
        schemeTypeTxt = findViewById(R.id.scheme_type_txt);
        schemeStartDateTxt = findViewById(R.id.scheme_start_date_txt);
        schemeEndDateTxt = findViewById(R.id.scheme_end_date_txt);

        schemeRecyclerView = findViewById(R.id.scheme_util_recyclerview);
        schemeRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void initToolBar() {

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        TextView title = toolbar.findViewById(R.id.custom_toolbar_title);

        title.setText(screenName);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void loadSchemeList() {

        models = new ArrayList<>();
        List<SchemeModel> utilizedSchemeList;


        utilizedSchemeList = new ArrayList<>();
//        utilizedSchemeList = db.getRetailerUtilSchemes(preferences.readString(PREF_DISTRCODE),
//                preferences.readString(PREF_SALESMANCODE), retrCode);

//        models = db.getRetailerSchemeProducts(retrCode);

        models = db.getRetailerSchemeProductsList(distr_code, cmp_code);


        for (int i = 0; i < models.size(); i++) {

            for (int j = 0; j < utilizedSchemeList.size(); j++) {
                if (models.get(i).getSchemeCode().equalsIgnoreCase(utilizedSchemeList.get(j).getSchemeCode())) {
                    models.get(i).setSchemeUtilizeAmt(utilizedSchemeList.get(j).getSchemeUtilizeAmt());
                    models.get(i).setIsFreeApplied(utilizedSchemeList.get(j).getIsFreeApplied());
                    models.get(i).setNoOfInvoice(utilizedSchemeList.get(j).getNoOfInvoice());
                    models.get(i).setIsSchemeUtilize("true");
                }
            }
        }
        uniqueSchemes = removeDuplicateFromArray();
        SchemeUtilizationAdapter schemeUtilizationAdapter = new SchemeUtilizationAdapter(uniqueSchemes);

        if (schemeUtilizationAdapter.getItemCount() > 0) {
            schemeRecyclerView.setAdapter(schemeUtilizationAdapter);
            emptyTxt.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.VISIBLE);
            schemeRecyclerView.setVisibility(View.GONE);
        }
        schemeUtilizationAdapter.setOnItemClickListener(new SchemeUtilizationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int adapterPosition) {

//                cardViewRoot.setVisibility(View.VISIBLE);
//
//                if (uniqueSchemes.get(adapterPosition).getSchemeUtilizeAmt() > 0) {
//
//                    schemeUtiInvoiceTxt.setText(String.valueOf(uniqueSchemes.get(adapterPosition).getNoOfInvoice()));
//                    schemeUtiValueTxt.setText(String.format("%s %s", getResources().getString(R.string.Rs), uniqueSchemes.get(adapterPosition).getSchemeUtilizeAmt()));
//
//                } else {
//                    schemeUtiInvoiceTxt.setText("0");
//                    schemeUtiValueTxt.setText(String.format("%s %s", getResources().getString(R.string.Rs), "0.00"));
//
//                }
//
//                schemeCodeTxt.setText(uniqueSchemes.get(adapterPosition).getSchemeCode());
//                schemeDescTxt.setText(uniqueSchemes.get(adapterPosition).getSchemeDescription());
//                schemePayoutTxt.setText(String.valueOf(uniqueSchemes.get(adapterPosition).getPayoutValue()));
//                schemeTypeTxt.setText(uniqueSchemes.get(adapterPosition).getSchemeBase());
//                schemeStartDateTxt.setText(DateUtil.covertTimeStampIntoData(uniqueSchemes.get(adapterPosition).getStartDate(), "dd/MM/yyyy"));
//                schemeEndDateTxt.setText(DateUtil.covertTimeStampIntoData(uniqueSchemes.get(adapterPosition).getEndDate(), "dd/MM/yyyy"));

                showSchemeDetail(adapterPosition);

            }
        });
    }

    /**
     * Shows the scheme details dialog with scheme description
     */
    private void showSchemeDetail(int pos) {
        schemePosition = pos;
        isSchemeDialogShowing = true;
        String desc = getString(R.string.scheme_empty);

        if (uniqueSchemes.get(schemePosition).getSchemeDescription() != null
                && !"".equalsIgnoreCase(uniqueSchemes.get(schemePosition).getSchemeDescription())) {
            desc = uniqueSchemes.get(schemePosition).getSchemeDescription();
        }

        final Dialog schemeDialog = new Dialog(this, R.style.ThemeDialogCustom);
        if (schemeDialog.isShowing()) return;
        schemeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        schemeDialog.setCanceledOnTouchOutside(false);
        schemeDialog.setCancelable(false);
        schemeDialog.setContentView(R.layout.scheme_description_dialog);

        final TextView popupProductFullName = schemeDialog.findViewById(R.id.popup_productFullName);
        final TextView popupDesc = schemeDialog.findViewById(R.id.popup_desc);

        popupProductFullName.setText(uniqueSchemes.get(schemePosition).getProdName());

        final ViewPager pager = schemeDialog.findViewById(R.id.schemeviewpager);
        SchemePagerAdapter myAdapter;
        try {
            List<SchemeModel> schemeModelList = new ArrayList<>();
            for (SchemeModel schemeModel : uniqueSchemes) {
//                if (uniqueSchemes.get(pos).getProductCode().equalsIgnoreCase(schemeModel.getProductCode())) {
//                    schemeModelList.add(schemeModel);
//                }
                if (uniqueSchemes.get(pos).getSchemeCode().equalsIgnoreCase(schemeModel.getSchemeCode())) {
                    schemeModelList.add(schemeModel);
                }
            }
            myAdapter = new SchemePagerAdapter(this, schemeModelList);
            if (myAdapter.getCount() > 0)
                pager.setAdapter(myAdapter);
            else
                popupDesc.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.e(TAG, "showSchemeDetail: e" + e.getMessage(), e);
        }

        if (!uniqueSchemes.isEmpty()) {
            pager.setVisibility(View.VISIBLE);
            popupDesc.setVisibility(View.GONE);
        } else {
            pager.setVisibility(View.INVISIBLE);
            popupDesc.setVisibility(View.VISIBLE);
            popupDesc.setText(desc);
        }
        Button button = schemeDialog.findViewById(R.id.popup_ok);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                schemeDialog.dismiss();
                isSchemeDialogShowing = false;
                schemePosition = -1;
            }
        });

        schemeDialog.show();

    }


    @NonNull
    private List<SchemeModel> removeDuplicateFromArray() {
        List<String> stringList = new ArrayList<>();
        List<SchemeModel> uniqueScheme = new ArrayList<>();
        for (SchemeModel allScheme : models) {
            if (!stringList.contains(allScheme.getSchemeCode())) {
                stringList.add(allScheme.getSchemeCode());
                uniqueScheme.add(allScheme);
            }
        }
        return uniqueScheme;
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
}
