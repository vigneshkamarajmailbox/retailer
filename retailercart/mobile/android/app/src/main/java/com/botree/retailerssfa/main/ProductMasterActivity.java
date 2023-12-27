package com.botree.retailerssfa.main;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.ProductBatchDialogListAdapter;

import com.botree.retailerssfa.adapters.ProductMasterListAdapter;
import com.botree.retailerssfa.adapters.ProductTaxDialogListAdapter;
import com.botree.retailerssfa.adapters.ProductUomDialogListAdapter;
import com.botree.retailerssfa.base.BaseActivity;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.main.VoiceToStringActivity;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.ProdBatchModel;
import com.botree.retailerssfa.models.ProductMasterModel;
import com.botree.retailerssfa.models.UomMasterModel;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.OrderSupportUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_PRODUCT_FILTERS;
import static com.botree.retailerssfa.support.Globals.NAME_PRODUCT;
import static com.botree.retailerssfa.support.Globals.VOICE_SEARCH;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;

public class ProductMasterActivity extends BaseActivity implements ProductMasterListAdapter.OnBatchClickListener,
        ProductMasterListAdapter.OnUomClickListener, ProductMasterListAdapter.OnTaxClickListener, View.OnClickListener {
    private SFADatabase db;
    private SFASharedPref preferences;
    private RecyclerView productList;
    private TextView productListEptyTxt;
    private ProductMasterListAdapter productListAdapter;
    private List<ProductMasterModel> productMasterModels;
    private String value;
    private Toolbar mToolbar;
    private Menu mTopMenuActionBar;
    private ProductMasterListAdapter.OnBatchClickListener batchClickListener;
    private ProductMasterListAdapter.OnUomClickListener uomClickListener;
    Dialog batchDialog;
    Dialog uomDialog;
    private ProductMasterListAdapter.OnTaxClickListener taxClickListener;
    Dialog taxDialog;

    private String brandCode = "";
    private String brandValue = "";
    private String categoryValue = "";
    private OrderSupportUtil.ZeroResult zeroNonZeroResult = OrderSupportUtil.ZeroResult.ALL_PRODUCTS;
    private OrderSupportUtil.ZeroResult tempZeroNonZeroResult = null;

    private NotifyUtil.NotifyBrandFilterListener filterBrandDialogListener = new NotifyUtil.NotifyBrandFilterListener() {

        @Override
        public void onfilterOkClicked(int pos, String strBrandCode, String strBrandVal) {

            if (strBrandCode != null && !strBrandCode.isEmpty()) {
                brandCode = strBrandCode;
                brandValue = strBrandVal;
            } else {
                brandValue = "";
                brandCode = "all";
            }

            // set the filter values to order booking adapter and update the recycler list
            setApplyFilter();

            clearTempFilterFields();
            updateListCount(mTopMenuActionBar);
        }

        private void setApplyFilter() {
            if (productListAdapter != null)
                productListAdapter.applyFilter(zeroNonZeroResult, brandValue, brandCode, categoryValue);
        }

        @Override
        public void onFilterCancelClicked() {
            //ignored
        }
    };

    private NotifyUtil.NotifyOtherFilterListener otherFilterDialogListener = new NotifyUtil.NotifyOtherFilterListener() {

        @Override
        public void onfilterOkClicked(int pos, String brandVal, OrderSupportUtil.ZeroResult zeroNonZero) {
            categoryValue = brandVal;
            zeroNonZeroResult = zeroNonZero;
            brandCode = "";
            brandValue = "";

            Globals.getOurInstance().setOtherFilterPso(pos);
            // set the filter values to order booking adapter and update the recycler list
            if (productListAdapter != null) {
                productListAdapter.applyFilter(zeroNonZeroResult, brandValue, brandCode, categoryValue);
                updateListCount(mTopMenuActionBar);
            }
            clearTempFilterFields();

        }

        @Override
        public void onFilterCancelClicked() {
            clearTempFilterFields();
        }
    };

    private void clearTempFilterFields() {
        tempZeroNonZeroResult = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_master);

        productMasterModels = new ArrayList<>();
        db = SFADatabase.getInstance(this);
        preferences = SFASharedPref.getOurInstance();

        Globals.clearFilter();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = getIntent().getStringExtra("ScreenName");
        }
        value = getResources().getString(R.string.screen_name_product);

//        setAutoScreenCount(value);
        initailizeView();
    }

    private void initailizeView() {
        mToolbar = findViewById(R.id.custom_toolbar);
        initToolbar();
        setBaseToolbarTitle(value, null);

        productList = findViewById(R.id.products_list);
        productListEptyTxt = findViewById(R.id.products_empty_txt);
        Button brandFilterButton = findViewById(R.id.brand_filter_btn);
        Button lobFilterButton = findViewById(R.id.lob_filter_btn);
        batchClickListener = this;
        uomClickListener = this;
        taxClickListener = this;
        lobFilterButton.setOnClickListener(this);
        brandFilterButton.setOnClickListener(this);

        productList.setLayoutManager(new LinearLayoutManager(this));
        new AsyncLoadData(ProductMasterActivity.this).execute();
    }

    /**
     * fetch all unique products from batch table
     */
    public void loadProducts() {
        productList.getRecycledViewPool().clear();
        productListAdapter = new ProductMasterListAdapter(this, productMasterModels,
                batchClickListener, uomClickListener, taxClickListener);

        if (productListAdapter.getItemCount() > 0) {
            productListEptyTxt.setVisibility(View.GONE);
            productList.setAdapter(productListAdapter);
            updateListCount(mTopMenuActionBar);

        } else {
            productListEptyTxt.setVisibility(View.VISIBLE);
        }

        VerticalSpaceItemDecoration decoration = new VerticalSpaceItemDecoration(20);
        productList.addItemDecoration(decoration);
    }

    private void showBatchDialog(String name, String prodCode, List<ProdBatchModel> batchModels) {
        if (batchDialog != null) {
            batchDialog.dismiss();
        }

        batchDialog = new Dialog(this, R.style.ThemeDialogCustom);
        batchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        batchDialog.setContentView(R.layout.batch_dialog_layout);

        RecyclerView batchList = batchDialog.findViewById(R.id.batch_list);
        TextView productName = batchDialog.findViewById(R.id.product_name);
        LinearLayout batchListHeader = batchDialog.findViewById(R.id.batch_list_header);
        TextView emptyBatchList = batchDialog.findViewById(R.id.batch_list_list_empty);

        batchList.setLayoutManager(new LinearLayoutManager(this));

        if (!batchModels.isEmpty()) {
            ProductBatchDialogListAdapter productBatchDialogListAdapter = new ProductBatchDialogListAdapter(batchModels);
            batchList.setAdapter(productBatchDialogListAdapter);

            DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            batchList.addItemDecoration(decoration);

            batchList.setVisibility(View.VISIBLE);
            batchListHeader.setVisibility(View.VISIBLE);
            emptyBatchList.setVisibility(View.GONE);
        } else {
            batchList.setVisibility(View.GONE);
            batchListHeader.setVisibility(View.GONE);
            emptyBatchList.setVisibility(View.VISIBLE);
        }

        productName.setText(prodCode+ " - "+name);

        batchDialog.show();
    }

    private void showUomDialog(String name, String prodCode, List<UomMasterModel> uomMasterModels) {
        if (uomDialog != null) {
            uomDialog.dismiss();
        }

        uomDialog = new Dialog(this, R.style.ThemeDialogCustom);
        uomDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        uomDialog.setContentView(R.layout.uom_dialog_layout);

        RecyclerView uomList = uomDialog.findViewById(R.id.uom_list);
        TextView productName = uomDialog.findViewById(R.id.product_name);
        LinearLayout uomListHeader = uomDialog.findViewById(R.id.uom_list_header);
        TextView emptyUomList = uomDialog.findViewById(R.id.uom_list_list_empty);

        uomList.setLayoutManager(new LinearLayoutManager(this));

        if (!uomMasterModels.isEmpty()) {
            ProductUomDialogListAdapter productUomDialogListAdapter = new ProductUomDialogListAdapter(uomMasterModels);
            uomList.setAdapter(productUomDialogListAdapter);

            DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            uomList.addItemDecoration(decoration);

            uomList.setVisibility(View.VISIBLE);
            uomListHeader.setVisibility(View.VISIBLE);
            emptyUomList.setVisibility(View.GONE);
        } else {
            uomList.setVisibility(View.GONE);
            uomListHeader.setVisibility(View.GONE);
            emptyUomList.setVisibility(View.VISIBLE);
        }

        productName.setText(prodCode+ " - "+name);

        uomDialog.show();
    }

    @Override
    public void onBatchClick(int position) {
        showBatchDialog(productMasterModels.get(position).getProdName(), productMasterModels.get(position).getProdCode(),
                db.fetchAllBatchesOfProduct(productMasterModels.get(position).getProdCode()));
    }

    @Override
    public void onUomClick(int position) {
        showUomDialog(productMasterModels.get(position).getProdName(), productMasterModels.get(position).getProdCode(),
                db.fetchAllUomsOfProduct(productMasterModels.get(position).getProdCode()));
    }

    @Override
    public void onTaxClick(int position) {
        showTaxDialog(productMasterModels.get(position).getProdName(), productMasterModels.get(position).getProdCode(),
                db.fetchAllTaxofProduct(productMasterModels.get(position).getProdCode()));
    }

    private void showTaxDialog(String prodName, String prodCode, List<OrderBookingVO> taxModel) {
        if (taxDialog != null) {
            taxDialog.dismiss();
        }

        taxDialog = new Dialog(this, R.style.ThemeDialogCustom);
        taxDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        taxDialog.setContentView(R.layout.tax_dialog_layout);

        RecyclerView taxList = taxDialog.findViewById(R.id.tax_list);
        TextView productName = taxDialog.findViewById(R.id.product_name);
        LinearLayout taxListHeader = taxDialog.findViewById(R.id.tax_list_header);
        TextView emptyTaxList = taxDialog.findViewById(R.id.tax_list_empty);

        taxList.setLayoutManager(new LinearLayoutManager(this));

        if (!taxModel.isEmpty()) {
            ProductTaxDialogListAdapter productUomDialogListAdapter = new ProductTaxDialogListAdapter(taxModel);
            taxList.setAdapter(productUomDialogListAdapter);

            DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            taxList.addItemDecoration(decoration);

            taxList.setVisibility(View.VISIBLE);
            taxListHeader.setVisibility(View.VISIBLE);
            emptyTaxList.setVisibility(View.GONE);
        } else {
            taxList.setVisibility(View.GONE);
            taxListHeader.setVisibility(View.GONE);
            emptyTaxList.setVisibility(View.VISIBLE);
        }

        productName.setText(prodCode+ " - "+prodName);

        taxDialog.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.brand_filter_btn) {
            invalidateOptionsMenu();
            NotifyUtil.getOurInstance().showBrandFilterDialog(this, filterBrandDialogListener,
                    "Product Filter", TABLE_ORDER_PRODUCT_FILTERS, categoryValue);
        } else if (id == R.id.lob_filter_btn) {
            invalidateOptionsMenu();
            NotifyUtil.getOurInstance().showOtherFilterDialog(this, otherFilterDialogListener, "LOB Filter",
                    false, tempZeroNonZeroResult, zeroNonZeroResult,db,preferences, true);
        }
    }

    class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_master, menu);

        mTopMenuActionBar = menu;

        MenuItem item = menu.findItem(R.id.product_search);
        MenuItem voiceSearch = menu.findItem(R.id.product_voice_search);
        voiceSearch.setVisible(false);

        updateListCount(menu);

        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String text = newText.toUpperCase(Locale.getDefault());

                if (text.trim().length() >= 0) {
                    // send the filter text to order booking adapter
                    productListAdapter.applySearchFilter(text);
                    updateListCount(mTopMenuActionBar);
                }

                return true;
            }
        });

        // At the time of orientation changed the search text is wiped out.
        // To restore the search text, its is stored into temp text and expand
        // the search text view and set the search text to it and fires the query to apply the filter.
        return true;
    }

    public void updateListCount(Menu menu) {
        if (menu != null) {
            MenuItem countItem = menu.findItem(R.id.prod_count);
            countItem.setActionView(R.layout.coverage_msg);
            View view = countItem.getActionView();
            TextView msg = view.findViewById(R.id.coverage_txt);
            if (productListAdapter != null)
                msg.setText(String.valueOf(productListAdapter.getItemCount()));
            else
                msg.setText("00");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.product_voice_search) {
            startVoiceService();
        } else if (menuItem.getItemId() == R.id.product_filter) {
            invalidateOptionsMenu();
            NotifyUtil.getOurInstance().showBrandFilterDialog(this, filterBrandDialogListener,
                    "Product Filter", TABLE_ORDER_PRODUCT_FILTERS, categoryValue);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void startVoiceService() {
        Intent intent = new Intent(this, VoiceToStringActivity.class);
        startActivityForResult(intent, VOICE_SEARCH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            String resultData = data.getStringExtra("voiceTxt");
            if (resultData.length() > 0) {
                productListAdapter.applySearchFilter(resultData);
                productListAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Toolbar getBaseToolbar() {
        return mToolbar;
    }


    public static class AsyncLoadData extends AsyncTask<Void, Void, Boolean> {

        private final WeakReference<ProductMasterActivity> activityRef;
        boolean boolResponse = false;

        public AsyncLoadData(ProductMasterActivity activityCon) {
            activityRef = new WeakReference<>(activityCon);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ProductMasterActivity activity = activityRef.get();
            if (activity.productMasterModels != null) {
                activity.productMasterModels.clear();
            }
            if (activity.value != null && activity.value.startsWith(NAME_PRODUCT) && activity.productMasterModels != null) {
                activity.productMasterModels.addAll(activity.db.fetchAllBatchProducts());
            }
            return boolResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProductMasterActivity activity = activityRef.get();
            AppUtils.getOurInstance().showProgressDialog(activity, activity.getResources().getString(R.string.MSG_LOADING));

        }

        @Override
        protected void onPostExecute(Boolean result) {
            AppUtils.getOurInstance().cancleProgressDialog();
            loadAdapterData();
        }


        private void loadAdapterData() {
            ProductMasterActivity activity = activityRef.get();
            activity.loadProducts();
        }
    }
}
