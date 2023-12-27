package com.botree.retailerssfa.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.SchemePagerAdapter;
import com.botree.retailerssfa.base.BaseActivity;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.ProductsVO;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.OrderSupportUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.controller.constants.AppConstant.StockTypes.TYPE_PRODUCT;
import static com.botree.retailerssfa.controller.constants.AppConstant.StockTypes.TYPE_STOCK_FOCUS_BRAND;
import static com.botree.retailerssfa.controller.constants.AppConstant.StockTypes.TYPE_STOCK_MUST_SELL_PRODUCTS;
import static com.botree.retailerssfa.controller.constants.AppConstant.StockTypes.TYPE_STOCK_SCHEME_PRODUCT;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_VANSALES_PRODUCTS;
import static com.botree.retailerssfa.support.Globals.NAME_FOCUS_BRAND_MASTER_REPORT;
import static com.botree.retailerssfa.support.Globals.NAME_MUST_SELL_MASTER_REPORT;
import static com.botree.retailerssfa.support.Globals.NAME_PRODUCT_MASTER;
import static com.botree.retailerssfa.support.Globals.NAME_SCHEME_MASTER_REPORT;
import static com.botree.retailerssfa.support.Globals.VOICE_SEARCH;
import static com.botree.retailerssfa.util.OrderSupportUtil.ZeroResult;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;

public class ProductSchFocusMustSellMasterActivity extends BaseActivity {

    private static final String TAG = ProductSchFocusMustSellMasterActivity.class.getSimpleName();
    private OrderSupportUtil mOrderSupportUtil;
    private SFADatabase db;
    private SFASharedPref preferences;
    private RecyclerView recyclerView;
    private List<String> brandsList;
    private String tempBrandValue = "";
    private AdapterView.OnItemSelectedListener brandSpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            tempBrandValue = brandsList.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //If nothing selected
        }
    };
    private String categoryValue = "";
    private String tempCategoryValue = "";
    private String brandValue = "";
    private TextView recyclerViewEptyTxt;
    private ProductStockInHandAdapter productStockInHandAdapter;
    private List<ProductsVO> productsVOList;
    private Dialog dialog;
    private List<String> categoryList;
    private AdapterView.OnItemSelectedListener categorySpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            tempCategoryValue = categoryList.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //If nothing selected
        }
    };
    private ZeroResult zeroNonZeroResult = ZeroResult.ALL_PRODUCTS;
    private ZeroResult tempZeroNonZeroResult = null;
    RadioGroup.OnCheckedChangeListener zeroRadioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            assignZeroGroupValue(checkedId, false);
        }
    };
    private String value;
    private String searchText = "";
    private Toolbar mToolbar;
    private Menu mTopMenuActionBar;

    private String isKgRateDisabled = "N";//change to Y later

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_stock_in_hand);
        productsVOList = new ArrayList<>();
        db = SFADatabase.getInstance(this);
        preferences = SFASharedPref.getOurInstance();
        mOrderSupportUtil = OrderSupportUtil.getInstance();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = getIntent().getStringExtra("ScreenName");
        }
        setAutoScreenCount(value);
        initailizeView();


    }

    private void initailizeView() {

        mToolbar = findViewById(R.id.custom_toolbar);
        initToolbar();
        setBaseToolbarTitle(value, null);
        recyclerView = findViewById(R.id.prod_stockinhand_recyclerview);
        recyclerViewEptyTxt = findViewById(R.id.prod_stockin_empty_txt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new AsyncLoadData(ProductSchFocusMustSellMasterActivity.this).execute();
    }

    /**
     * fetch all retailer by selected route from db and load in recyclerview
     */
    public void loadProducts() {
        recyclerView.getRecycledViewPool().clear();
        productStockInHandAdapter = new ProductStockInHandAdapter(this, productsVOList);

        if (productStockInHandAdapter.getItemCount() > 0) {
            recyclerViewEptyTxt.setVisibility(View.GONE);
            recyclerView.setAdapter(productStockInHandAdapter);
            updateListCount(mTopMenuActionBar);

        } else {
            recyclerViewEptyTxt.setVisibility(View.VISIBLE);
        }

        if (value != null && value.startsWith(NAME_SCHEME_MASTER_REPORT)) {

            productStockInHandAdapter.setOnItemClickListener(new ProductStockInHandAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    showSchemeDetail(position);
                }

            });
        }
    }


    /**
     * @param pos current list position to the scheme detail
     */
    private void showSchemeDetail(int pos) {

        String desc = getString(R.string.scheme_empty);

        final Dialog schemeDialog = new Dialog(this, R.style.ThemeDialogCustom);
        if (schemeDialog.isShowing()) return;
        schemeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        schemeDialog.setCanceledOnTouchOutside(false);
        schemeDialog.setCancelable(false);
        schemeDialog.setContentView(R.layout.scheme_description_dialog);

        final TextView popupProductFullName = schemeDialog.findViewById(R.id.popup_productFullName);
        final TextView popupDesc = schemeDialog.findViewById(R.id.popup_desc);

        popupProductFullName.setText(productsVOList.get(pos).getProdShortNameCaps());

        final ViewPager pager = schemeDialog.findViewById(R.id.schemeviewpager);
        SchemePagerAdapter myAdapter;
        List<SchemeModel> schemeModelList = new ArrayList<>();
        try {
            schemeModelList = db.getSchemeByProdCode(preferences.readString(PREF_DISTRCODE),
                    productsVOList.get(pos).getProdCodeCaps());
            myAdapter = new SchemePagerAdapter(this, schemeModelList);
            if (myAdapter.getCount() > 0)
                pager.setAdapter(myAdapter);
            else
                popupDesc.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Log.e(TAG, "showSchemeDetail: " + e.getMessage(), e);
        }

        if (!schemeModelList.isEmpty()) {
            pager.setVisibility(View.VISIBLE);
            popupDesc.setVisibility(View.GONE);
        } else {
            pager.setVisibility(View.GONE);
            popupDesc.setVisibility(View.VISIBLE);
            popupDesc.setText(desc);
        }
        Button button = schemeDialog.findViewById(R.id.popup_ok);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                schemeDialog.dismiss();
            }
        });

        schemeDialog.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_proud_master, menu);

        mTopMenuActionBar = menu;

        MenuItem item = menu.findItem(R.id.product_search);
        MenuItem item1 = menu.findItem(R.id.product_filter);
        item1.setVisible(false);
        MenuItem itemfavorite = menu.findItem(R.id.favorite);
        itemfavorite.setVisible(false);

        updateListCount(menu);

        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                searchText = "";
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String text = newText.toUpperCase(Locale.getDefault());

                if (text.trim().length() >= 0) {
                    // send the filter text to order booking adapter
                    productStockInHandAdapter.applySearchFilter(text);
                    searchText = text;
                }

                return true;
            }
        });

        // At the time of orientation changed the search text is wiped out.
        // To restore the search text, its is stored into temp text and expand
        // the search text view and set the search text to it and fires the query to apply the filter.
        if (!TextUtils.isEmpty(searchText)) {
            String tempStr = searchText;
            item.expandActionView();
            searchView.setQuery(tempStr, true);

        }
        return true;
    }

    public void updateListCount(Menu menu) {

        if (menu != null) {
            MenuItem countItem = menu.findItem(R.id.prod_count);
            countItem.setActionView(R.layout.coverage_msg);
            View view = countItem.getActionView();
            TextView msg = view.findViewById(R.id.coverage_txt);
            if (productStockInHandAdapter != null)
                msg.setText(String.valueOf(productStockInHandAdapter.getItemCount()));
            else
                msg.setText(String.valueOf("00"));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.product_filter) {
            filterProducts();
        } else if (menuItem.getItemId() == R.id.product_voice_search) {
            startVoiceService();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    /**
     * Check the orientation handling temp variable is null.
     * If not null then get temp variable value and set to radio group
     * else go with zeroNonZeroResult
     *
     * @param zeroRG set this to Zero Product radio group
     */
    private void setZeroNonZeroProductRG(RadioGroup zeroRG) {
        ZeroResult result = tempZeroNonZeroResult != null ? tempZeroNonZeroResult : zeroNonZeroResult;

        switch (result) {
            case ZERO_PRODUCTS:
                zeroRG.check(R.id.zero_btn);
                break;
            case NON_ZERO_PRODUCTS:
                zeroRG.check(R.id.non_zero_btn);
                break;
            case ALL_PRODUCTS:
            default:
                zeroRG.check(R.id.all_btn);
                break;
        }
    }

    /**
     * Get the Radio Group result and store it into to zeroNonZeroResult or tempZeroNonZeroResult
     *
     * @param checkedRadioButtonId get the response of zero product group with this radio button id
     * @param isFromSubmit         If from submit button then store the result to zeroNonZeroResult
     *                             else tempZeroNonZeroResult
     */
    public void assignZeroGroupValue(int checkedRadioButtonId, boolean isFromSubmit) {
        ZeroResult result;
        switch (checkedRadioButtonId) {
            case R.id.zero_btn:
                result = ZeroResult.ZERO_PRODUCTS;
                break;
            case R.id.non_zero_btn:
                result = ZeroResult.NON_ZERO_PRODUCTS;
                break;
            case R.id.all_btn:
            default:
                result = ZeroResult.ALL_PRODUCTS;
                break;
        }
        if (isFromSubmit)
            zeroNonZeroResult = result;
        else
            tempZeroNonZeroResult = result;
    }

    /**
     * Check the orientation handling temp variable for both brand and category
     * If values are not null then set the value to responsible spinner
     * else go with brandValue & categoryValue.
     *
     * @param brandSpinner    set the brand value to this spinner
     * @param categorySpinner set the category value to this spinner
     */
    private void setBrandAndCategory(Spinner brandSpinner, Spinner categorySpinner) {

        String brandStr = tempBrandValue.length() != 0 ? tempBrandValue : brandValue;
        String categoryStr = tempCategoryValue.length() != 0 ? tempCategoryValue : categoryValue;

        if (brandStr.length() == 0) {
            brandSpinner.setSelection(0);
        } else {
            brandSpinner.setSelection(brandsList.indexOf(brandStr));
        }

        if (categoryStr.length() == 0) {
            categorySpinner.setSelection(0);
        } else {
            categorySpinner.setSelection(categoryList.indexOf(categoryStr));
        }
    }

    /**
     * Init the filter products dialog
     * Restore the data if previously selected values
     * or temp variable values for handling screen orientation.
     * <p>
     * At the time pressing submit the values are stored into
     * variable and filter values are set to order booking adapter
     */
    private void filterProducts() {

        if (dialog != null && dialog.isShowing()) return;

        dialog = new Dialog(this, R.style.ThemeDialogCustom);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.filter_dialog);
        final Button submit = dialog.findViewById(R.id.filter_submit_btn);
        final Button cancel = dialog.findViewById(R.id.filter_cancel_btn);
        final RadioGroup zeroNonZeroRadioGroup = dialog.findViewById(R.id.zero_non_zero_radio_group);
        final Spinner brandSpinner = dialog.findViewById(R.id.spinner_brand);
        final Spinner categorySpinner = dialog.findViewById(R.id.spinner_category);

        final LinearLayout categoryLayout = dialog.findViewById(R.id.cat_filter_layout);
        final TextView catNameLable = dialog.findViewById(R.id.cat_name_txt);

        categoryLayout.setVisibility(View.GONE);
        catNameLable.setVisibility(View.GONE);


        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(this, R.layout.spinner_list_item, brandsList);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(brandAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, R.layout.spinner_list_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        setZeroNonZeroProductRG(zeroNonZeroRadioGroup);

        setBrandAndCategory(brandSpinner, categorySpinner);

        brandSpinner.setOnItemSelectedListener(brandSpinnerListener);
        categorySpinner.setOnItemSelectedListener(categorySpinnerListener);
        zeroNonZeroRadioGroup.setOnCheckedChangeListener(zeroRadioGroupListener);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
                clearTempFilterFields();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                assignZeroGroupValue(zeroNonZeroRadioGroup.getCheckedRadioButtonId(), true);
                brandValue = brandSpinner.getSelectedItem().toString();
                categoryValue = categorySpinner.getSelectedItem().toString();

                // set the filter values to order booking adapter and update the recycler list
                if (productStockInHandAdapter != null) {
                    productStockInHandAdapter.applyFilter(zeroNonZeroResult, brandValue, categoryValue);
                }
                dialog.dismiss();
                clearTempFilterFields();

            }
        });

        dialog.show();
    }

    /**
     * Clear the temp variables of filter products dialog after the
     * dialog is dismissed by pressing Submit or cancel button
     */
    private void clearTempFilterFields() {
        tempZeroNonZeroResult = null;
        tempBrandValue = "";
        tempCategoryValue = "";
    }

    private void startVoiceService() {

        Intent intent = new Intent(this, VoiceToStringActivity.class);
        startActivityForResult(intent, VOICE_SEARCH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (data != null) {

                Log.d(TAG, "onActivityResult 1 : " + data.getStringExtra("voiceTxt"));

                String resultData = data.getStringExtra("voiceTxt");
                if (resultData.length() > 0) {
                    productStockInHandAdapter.applySearchFilter(resultData);
                    productStockInHandAdapter.notifyDataSetChanged();
                    searchText = resultData;
                }
            }

        } else {
            Log.d(TAG, "onActivityResult: " + data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Toolbar getBaseToolbar() {
        return mToolbar;
    }


    public static class AsyncLoadData extends AsyncTask<Void, Void, Boolean> {

        private final WeakReference<ProductSchFocusMustSellMasterActivity> activityRef;
        boolean boolResponse = false;

        public AsyncLoadData(ProductSchFocusMustSellMasterActivity activityCon) {
            activityRef = new WeakReference<>(activityCon);
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            ProductSchFocusMustSellMasterActivity activity = activityRef.get();
            if (activity.productsVOList != null) {
                activity.productsVOList.clear();
            }

            if (activity.value != null && activity.value.startsWith(NAME_PRODUCT_MASTER)) {

                activity.productsVOList = activity.db.fetchAllStockInHandProducts(activity.preferences.readString(PREF_DISTRCODE), TYPE_PRODUCT);

            } else if (activity.value != null && activity.value.startsWith(NAME_SCHEME_MASTER_REPORT)) {

                activity.productsVOList = activity.db.fetchAllStockInHandProducts(activity.preferences.readString(PREF_DISTRCODE), TYPE_STOCK_SCHEME_PRODUCT);

            } else if (activity.value != null && activity.value.startsWith(NAME_MUST_SELL_MASTER_REPORT)) {

                activity.productsVOList = activity.db.fetchAllStockInHandProducts(activity.preferences.readString(PREF_DISTRCODE), TYPE_STOCK_MUST_SELL_PRODUCTS);

            } else if (activity.value != null && activity.value.startsWith(NAME_FOCUS_BRAND_MASTER_REPORT)) {

                activity.productsVOList = activity.db.fetchAllStockInHandProducts(activity.preferences.readString(PREF_DISTRCODE), TYPE_STOCK_FOCUS_BRAND);

            } else {

                activity.productsVOList = activity.db.fetchAllStockInHandProducts(activity.preferences.readString(PREF_DISTRCODE), TYPE_PRODUCT);
            }

            try {
                setSchemeProducts(activity);

            } catch (Exception e) {
                Log.e(TAG, "doInBackground: " + e.getMessage(), e);
            }

            return boolResponse;
        }

        private void setSchemeProducts(ProductSchFocusMustSellMasterActivity activity) {
            if (activity.value != null && activity.value.startsWith("Scheme Products")) {

                for (int i = 0; i < activity.productsVOList.size(); i++) {

                    for (int j = i + 1; j < activity.productsVOList.size(); j++) {
                        if (activity.productsVOList.get(i).getProdCodeCaps().equalsIgnoreCase(
                                activity.productsVOList.get(j).getProdCodeCaps())) {
                            activity.productsVOList.remove(j);
                            j--;
                        }
                    }
                }
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProductSchFocusMustSellMasterActivity activity = activityRef.get();
            AppUtils.getOurInstance().showProgressDialog(activity, activity.getResources().getString(R.string.MSG_LOADING));

        }

        @Override
        protected void onPostExecute(Boolean result) {
            AppUtils.getOurInstance().cancleProgressDialog();
            loadAdapterData();
        }


        private void loadAdapterData() {

            ProductSchFocusMustSellMasterActivity activity = activityRef.get();
            activity.brandsList = activity.mOrderSupportUtil.loadBrandsList(TABLE_VANSALES_PRODUCTS, "");
            activity.categoryList = activity.mOrderSupportUtil.loadCategoryList();
            activity.loadProducts();
        }
    }
}
