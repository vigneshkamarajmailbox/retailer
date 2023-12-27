/*
 * Copyright (C) 2016 Botree Software International Private Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.botree.retailerssfa.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.OrderBookingAdapter;
import com.botree.retailerssfa.adapters.ProductFilterAdapter;
import com.botree.retailerssfa.adapters.SchemePagerAdapter;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.fragmentmanager.ESFAFragTags;
import com.botree.retailerssfa.fragmentmanager.SFAFragmentManager;
import com.botree.retailerssfa.models.EditQtyModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.ProFilterModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.models.TaxMasterModel;
import com.botree.retailerssfa.scheme.SchemeEngin;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.DateUtil;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.OrderSupportUtil;
import com.botree.retailerssfa.util.SFALocationUpdate;
import com.botree.retailerssfa.util.SFASharedPref;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import static android.app.Activity.RESULT_OK;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_ENABLE_OTHER_FILTER;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_IS_PTR_WITH_TAX;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_PRODUCT_PRIORITY;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_STOCK_TAKE;
import static com.botree.retailerssfa.controller.constants.AppConstant.ORDER_SCREEN_TYPE_CUSTOM;
import static com.botree.retailerssfa.controller.constants.AppConstant.ScreenTitles.SCREEN_TITLE_ORDER_BOOKING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_BOOKING;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_PRODUCT_FILTERS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCT_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCT_UOM_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_UOM_MASTER;
import static com.botree.retailerssfa.support.Globals.IMAGE_SCREEN_QTY;
import static com.botree.retailerssfa.support.Globals.NAME_ORDER_BOOKING;
import static com.botree.retailerssfa.support.Globals.ORDER_BOOKING_QUANTITY_DIALOG;
import static com.botree.retailerssfa.support.Globals.VOICE_SEARCH;
import static com.botree.retailerssfa.util.OrderSupportUtil.ALL_BRANDS;
import static com.botree.retailerssfa.util.OrderSupportUtil.ALL_CATEGORY;
import static com.botree.retailerssfa.util.OrderSupportUtil.BRAND_TEXT;
import static com.botree.retailerssfa.util.OrderSupportUtil.CATEGORY_TEXT;
import static com.botree.retailerssfa.util.OrderSupportUtil.ENTERED_QTY;
import static com.botree.retailerssfa.util.OrderSupportUtil.ENTERED_REMARKS;
import static com.botree.retailerssfa.util.OrderSupportUtil.ENTERED_UOM;
import static com.botree.retailerssfa.util.OrderSupportUtil.FOCUS_BRAND;
import static com.botree.retailerssfa.util.OrderSupportUtil.IS_FILTER_DIALOG_SHOWING;
import static com.botree.retailerssfa.util.OrderSupportUtil.IS_QTY_DIALOG_SHOWING;
import static com.botree.retailerssfa.util.OrderSupportUtil.IS_REMARKS_FOCUSED;
import static com.botree.retailerssfa.util.OrderSupportUtil.IS_SCHEME_DIALOG_SHOWING;
import static com.botree.retailerssfa.util.OrderSupportUtil.MUST_SELL;
import static com.botree.retailerssfa.util.OrderSupportUtil.ORDER_BOOKING_LIST;
import static com.botree.retailerssfa.util.OrderSupportUtil.PROMO;
import static com.botree.retailerssfa.util.OrderSupportUtil.QTY_LIST;
import static com.botree.retailerssfa.util.OrderSupportUtil.QTY_POSITION;
import static com.botree.retailerssfa.util.OrderSupportUtil.SCHEME_POSITION;
import static com.botree.retailerssfa.util.OrderSupportUtil.SEARCH_LIST;
import static com.botree.retailerssfa.util.OrderSupportUtil.SEARCH_TEXT;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LAST_LAT;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LAST_LONG;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_ORDER_BOOKING_OPTIONS;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_PRODUCT_SEARCH_LEVEL_NAME;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_ROUTECODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_TEMP_ORDER_BOOKING_LIST;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_TEMP_QTY_UPDATE_ORDER_LIST;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_TEMP_SEARCH_LIST;

/**
 * Order Booking Fragment contains {@link RecyclerView} with {@link List}
 * and Inflated layout is fragment_order_booking.
 */
public class OrderBookingFragment extends SFAFragment implements View.OnClickListener,
        SFALocationUpdate {

    private static final String RETAILER_CODE = "retailerCode";
    private static final String TAG = OrderBookingFragment.class.getSimpleName();
    private BottomSheetDialog filterDialog;
    private List<String> brandsList = new ArrayList<>();
    private Dialog progressDialog;
    private String invoiceNo = null;
    private int newRetailer = 0;
    private OrderBookingAdapter orderBookingAdapter;
    private RecyclerView recyclerView;
    private TextView emptyText;
    private TextView orderCountTxt;
    private TextView orderTotalTxt;
    private SFADatabase db;
    private SFASharedPref preferences;
    private String tempBrandValue = "";
    private Dialog dialog;
    private TextView perUnitLabel;
    private TextView perUnitLabelPrice;

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

    private String brandValue = "";
    private String categoryValue = "";
    private String tempCategoryValue = "";
    private String retailerName;
    private List<String> uomIdSpinner = new ArrayList<>();
    private CoordinatorLayout orderBookingLayout;
    private List<OrderBookingVO> qtyUpdatedOrderBooking;
    private List<OrderBookingVO> orderBookingVOList = new ArrayList<>();
    private List<String> focusBrandProduct = new ArrayList<>();
    private List<String> mustSellProduct = new ArrayList<>();
    private List<String> mustSellBilledProduct = new ArrayList<>();
    private List<SchemeModel> schemeProductDetail = new ArrayList<>();//retailer scheme products list
    private List<SchemeModel> orderAppliedSchemeList = new ArrayList<>();
    private List<SchemeModel> editProdTempList = new ArrayList<>();
    private List<String> categoryList;
    private List<String> top10Products = new ArrayList<>();
    List<String> orderNoList = new ArrayList<>();
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

    private boolean isQtyDialogShowing = false;
    private boolean isSchemeDialogShowing = false;
    private boolean isFilterDialogShowing = false;
    private boolean isRemarksFocused = false;
    private int qtyPosition = -1;
    private int schemePosition = -1;
    private String enteredQty = "";
    private String enteredRemarks = "";
    private int enteredUOM = -1;
    private String searchText = "";
    private OrderSupportUtil.ZeroResult zeroNonZeroResult = OrderSupportUtil.ZeroResult.ALL_PRODUCTS;
    private OrderSupportUtil.ZeroResult tempZeroNonZeroResult = null;

    private RadioGroup.OnCheckedChangeListener zeroRadioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            assignZeroGroupValue(checkedId, false);
        }
    };

    private Spinner mainBrandSpinner;
    private boolean isPreviousOrder = false;
    private SchemeEngin schemeEngin;
    private List<OrderBookingVO> previousOrderList = new ArrayList<>();
    private List<OrderBookingVO> brandDataList = new ArrayList<>();
    private String brandCode = "";

    // For Schemes
    private List<SchemeModel> mAppliedSlablist = new ArrayList<>();
    private List<SchemeModel> productSchemeModelList = new ArrayList<>();
    private List<SchemeModel> schemeSlabList = new ArrayList<>();
    private List<SchemeModel> freeProdSlabList = new ArrayList<>();
    private Globals globalsInstance = Globals.getOurInstance();
    private OrderSupportUtil mOrderSupportUtil;
    private int outOfStockPos = 0;
    private String mCallingFragment;
    private View rootLayout;
    private String newRetlrRouteCode;
    private int weightCheckPos = 0;
    private int weightStockCheckPos = 0;
    private Menu mTopMenuActionBar;
    private NotifyUtil.NotifyFilterListener filterDialogListener = new NotifyUtil.NotifyFilterListener() {

        @Override
        public void onfilterOkClicked(int pos, String brandVal) {

            brandValue = brandVal;
            if (!brandDataList.isEmpty() && pos - 1 >= 0)
                brandCode = brandDataList.get(pos - 1).getBrandCode();
            categoryValue = ALL_CATEGORY;
            // set the filter values to order booking adapter and update the recycler list
            setApplyFilter();

            isFilterDialogShowing = false;
            clearTempFilterFields();
        }

        @Override
        public void onFilterCancelClicked() {
            //ignored
        }
    };
    private NotifyUtil.NotifyBrandFilterListener filterBrandDialogListener = new NotifyUtil.NotifyBrandFilterListener() {


        @Override
        public void onfilterOkClicked(int pos, String strBrandCode, String strBrandVal) {

            if (strBrandCode != null && !strBrandCode.isEmpty()) {
                brandCode = strBrandCode;
                brandValue = strBrandVal;
            } else {
                brandValue = ALL_BRANDS;
            }
            categoryValue = ALL_CATEGORY;

            // set the filter values to order booking adapter and update the recycler list
            setApplyFilter();

            isFilterDialogShowing = false;
            clearTempFilterFields();

        }

        @Override
        public void onFilterCancelClicked() {
            //ignored
        }
    };

    private EditText editQty;
    private Spinner uomSpinner;
    private OrderBookingVO orderBookingVO;
    private int editDialogType;
    private EditText editRemarks;
    private NotifyUtil.DateTimeNotifyListener mustSellListener = new NotifyUtil.DateTimeNotifyListener() {
        @Override
        public void onOkClicked() {
            setOnSubmitClick(editQty, orderBookingVO, uomSpinner, editRemarks, qtyPosition, editDialogType, dialog);
        }

        @Override
        public void onCancelClicked() {

        }
    };


    private String retailerType = "";
    private boolean isPTRWithTax = false;

    public OrderBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
        db = SFADatabase.getInstance(getActivity());
        preferences = SFASharedPref.getOurInstance();
        schemeEngin = new SchemeEngin(getActivity());
        mOrderSupportUtil = OrderSupportUtil.getInstance();

        if (getArguments() != null) {
            retailerName = getArguments().getString("retailerName");
            newRetlrRouteCode = getArguments().getString("newRetailerRouteCode");
//            mCallingFragment = getArguments().getString("callingFragment");
            mCallingFragment = NAME_ORDER_BOOKING;
            newRetailer = getArguments().getInt("NewRetailer");
            isPreviousOrder = getArguments().getBoolean("isPreviousOrder");
            retailerType = getArguments().getString("retailerType");
        }

        checkLocalConfig();
    }

    private void checkLocalConfig() {
        if ("Y".equalsIgnoreCase(db.getConfigDataBasedOnName(CONFIG_IS_PTR_WITH_TAX))) {
            isPTRWithTax = true;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_order_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAutoScreenCount(Globals.NAME_ORDER_BOOKING);

        initialize(view);
//        processSavedInstanceState(savedInstanceState);

        if (qtyUpdatedOrderBooking == null)
            qtyUpdatedOrderBooking = new ArrayList<>();

        ((MainActivity) getSFAFragmentActivity()).hideBottomNevigation();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        processSavedInstanceState(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        Globals.getOurInstance().isDateChanged(getSFAFragmentActivity());
        SFAFragmentManager.newInstance().setCurrentFragmentTitle(getSFAFragmentActivity(), SCREEN_TITLE_ORDER_BOOKING);
    }

    /**
     * Restore the savedInstanceState with appropriate variable and check any dialog showing boolean
     * variable is true. If true then display that dialog with restored values
     *
     * @param savedInstanceState restoring values are retrieved from this bundle.
     */
    private void processSavedInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            //probably orientation change
            orderBookingVOList = (List<OrderBookingVO>) savedInstanceState.getSerializable("orderlist");

            savedState1(savedInstanceState);
            savedStateForOrderList(savedInstanceState);
            savedState2(savedInstanceState);
            savedState3(savedInstanceState);

            loadAdapterData();

        } else {
            new AsyncLoadData(OrderBookingFragment.this).execute();
        }

        setApplyFilter();

        if (isQtyDialogShowing && qtyPosition != -1)
            enterOrderQuantity(qtyPosition, ORDER_BOOKING_QUANTITY_DIALOG);

        else if (isSchemeDialogShowing && schemePosition != -1)
            showSchemeDetail(schemePosition);

        else if (isFilterDialogShowing)
            filterProducts();
    }

    private void savedState3(Bundle outState) {

        if (outState.containsKey("retailerType"))
            retailerType = outState.getString("retailerType");

        if (outState.containsKey("retailerName"))
            retailerName = outState.getString("retailerName");

        if (outState.containsKey("newRetailerRouteCode"))
            newRetlrRouteCode = outState.getString("newRetailerRouteCode");

        if (outState.containsKey("callingFragment"))
            mCallingFragment = outState.getString("callingFragment");

        if (outState.containsKey("NewRetailer"))
            newRetailer = outState.getInt("NewRetailer");

        if (outState.containsKey("isPreviousOrder"))
            isPreviousOrder = outState.getBoolean("isPreviousOrder");
    }

    private void setApplyFilter() {
        if (orderBookingAdapter != null)
            orderBookingAdapter.applyFilter(zeroNonZeroResult, brandValue, brandCode, categoryValue);
        if (mTopMenuActionBar != null) {
            updateListCount(mTopMenuActionBar);
        }
    }

    private void savedState1(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(IS_QTY_DIALOG_SHOWING))
            isQtyDialogShowing = savedInstanceState.getBoolean(IS_QTY_DIALOG_SHOWING, false);
        if (savedInstanceState.containsKey(QTY_POSITION))
            qtyPosition = savedInstanceState.getInt(QTY_POSITION, -1);

        if (savedInstanceState.containsKey(ENTERED_QTY))
            enteredQty = savedInstanceState.getString(ENTERED_QTY, "0");

        if (savedInstanceState.containsKey(ENTERED_REMARKS))
            enteredRemarks = savedInstanceState.getString(ENTERED_REMARKS, "");

        if (savedInstanceState.containsKey(IS_REMARKS_FOCUSED))
            isRemarksFocused = savedInstanceState.getBoolean(IS_REMARKS_FOCUSED, false);

        if (savedInstanceState.containsKey(ENTERED_UOM))
            enteredUOM = savedInstanceState.getInt(ENTERED_UOM);
    }

    private void savedStateForOrderList(Bundle savedInstanceState) {
        List<OrderBookingVO> searchList = new ArrayList<>();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            if (savedInstanceState.containsKey(QTY_LIST))
                qtyUpdatedOrderBooking = savedInstanceState.getParcelableArrayList(QTY_LIST);

            if (savedInstanceState.containsKey(ORDER_BOOKING_LIST))
                orderBookingVOList = savedInstanceState.getParcelableArrayList(ORDER_BOOKING_LIST);


            if (savedInstanceState.containsKey(SEARCH_LIST))
                searchList = savedInstanceState.getParcelableArrayList(SEARCH_LIST);

        } else {
            List<Object> tempQtyUpdatedOrderBooking = preferences.getList(PREF_TEMP_QTY_UPDATE_ORDER_LIST);
            List<Object> tempOrderBookingVOList = preferences.getList(PREF_TEMP_ORDER_BOOKING_LIST);
            List<Object> tempSearchList = preferences.getList(PREF_TEMP_SEARCH_LIST);
            qtyUpdatedOrderBooking = mOrderSupportUtil.typeSafeCheckCast(tempQtyUpdatedOrderBooking);
            orderBookingVOList = mOrderSupportUtil.typeSafeCheckCast(tempOrderBookingVOList);


            if (savedInstanceState.containsKey(SEARCH_LIST))
                searchList = mOrderSupportUtil.typeSafeCheckCast(tempSearchList);

        }
        if (orderBookingVOList != null && searchList != null) {
            orderBookingAdapter.updateList(orderBookingVOList, searchList);
            orderBookingAdapter.notifyDataSetChanged();
        }
    }

    private void savedState2(Bundle savedInstanceState) {

        if (savedInstanceState.containsKey(SEARCH_TEXT)) {
            searchText = savedInstanceState.getString(SEARCH_TEXT, "");
        }

        if (savedInstanceState.containsKey(BRAND_TEXT))
            brandValue = savedInstanceState.getString(BRAND_TEXT, "");

        if (savedInstanceState.containsKey(CATEGORY_TEXT))
            categoryValue = savedInstanceState.getString(CATEGORY_TEXT, "");

        if (savedInstanceState.containsKey(IS_SCHEME_DIALOG_SHOWING))
            isSchemeDialogShowing = savedInstanceState.getBoolean(IS_SCHEME_DIALOG_SHOWING, false);
        if (savedInstanceState.containsKey(IS_FILTER_DIALOG_SHOWING))
            isFilterDialogShowing = savedInstanceState.getBoolean(IS_FILTER_DIALOG_SHOWING, false);

        if (savedInstanceState.containsKey(SCHEME_POSITION))
            schemePosition = savedInstanceState.getInt(SCHEME_POSITION);
    }

    /**
     * @param view Initializing the all views for this fragment is get from this.
     */
    private void initialize(View view) {

        TextView productNameTitleTxt = view.findViewById(R.id.product_name_title_label_txt);
        TextView mrpTitleTxt = view.findViewById(R.id.mrp_title_label_txt);
        TextView ptrLabelTxt = view.findViewById(R.id.ptr_label_txt);
        orderBookingLayout = view.findViewById(R.id.orderbooking_layout);
        mainBrandSpinner = view.findViewById(R.id.spinner_mian_brand);
        emptyText = view.findViewById(R.id.orderbooking_empty_tag_txt);
        orderCountTxt = view.findViewById(R.id.order_count_txt);
        orderTotalTxt = view.findViewById(R.id.order_total_txt);
        TextView sovLabel = view.findViewById(R.id.sov_label_text);
        TextView sovValueText = view.findViewById(R.id.sov_value_txt);
        TextView stockLabelTxt = view.findViewById(R.id.stock_label_txt);
        recyclerView = view.findViewById(R.id.orderbooking_recyclerview);
        Button nextBtn = view.findViewById(R.id.orderbooking_submit_btn);
        Button brandFilterBtn = view.findViewById(R.id.brand_filter_btn);
        Button otherFilterBtn = view.findViewById(R.id.other_filter_btn);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        globalsInstance.setRetailerStartTime(""+System.currentTimeMillis());

//        double sovValue = db.getRetailerSOV(preferences.readString(PREF_DISTRCODE),
//                preferences.readString(PREF_SALESMANCODE),
//                preferences.readString(PREF_ROUTECODE), retailerCode);


//        if ("Y".equalsIgnoreCase(db.getConfigDataBasedOnName(AppConstant.Configurations.CONFIG_SOV))) {
//            sovLabel.setVisibility(View.VISIBLE);
//            sovValueText.setVisibility(View.VISIBLE);
//            sovValueText.setText("" + sovValue);
//        } else {
        sovLabel.setVisibility(View.GONE);
        sovValueText.setVisibility(View.GONE);
//        }

        nextBtn.setOnClickListener(this);
        brandFilterBtn.setOnClickListener(this);
        otherFilterBtn.setOnClickListener(this);

        if (preferences.readString(PREF_ORDER_BOOKING_OPTIONS).equalsIgnoreCase(ORDER_SCREEN_TYPE_CUSTOM)) {
            mrpTitleTxt.setVisibility(View.GONE);
        }

        if ("false".equalsIgnoreCase(getResources().getString(R.string.ordbooking_is_ptr_show))) {
            ptrLabelTxt.setVisibility(View.GONE);
        }

        if ("Y".equalsIgnoreCase(db.getConfigDataBasedOnName(CONFIG_IS_PTR_WITH_TAX))) {
            ptrLabelTxt.setText(getResources().getString(R.string.ptr_with_tax));
        }

//        if (db.getConfigDataBasedOnName(CONFIG_STOCK_TAKE).equalsIgnoreCase("N"))
        stockLabelTxt.setVisibility(View.GONE);

        setProductFilterLabel(brandFilterBtn);

    }

    private void setProductFilterLabel(Button brandFilterBtn) {
        if (!preferences.readString(PREF_PRODUCT_SEARCH_LEVEL_NAME).isEmpty()) {
            brandFilterBtn.setText(preferences.readString(PREF_PRODUCT_SEARCH_LEVEL_NAME));
        } else {
            if ("4".equalsIgnoreCase(getResources().getString(R.string.filter_level))) {
                brandFilterBtn.setText(R.string.filter_four);
            } else if ("3".equalsIgnoreCase(getResources().getString(R.string.filter_level))) {
                brandFilterBtn.setText(R.string.filter_three);
            } else if ("2".equalsIgnoreCase(getResources().getString(R.string.filter_level))) {
                brandFilterBtn.setText(R.string.filter_two);
            } else {
                brandFilterBtn.setText(R.string.filter_one);
            }
        }
    }

    private void showBrandFilterDialog() {

//        int productHierarchy = 0;
//        if (db.getConfigDataBasedOnName("HierarchySelection") != null && !db.getConfigDataBasedOnName("HierarchySelection").isEmpty())
//            productHierarchy = Integer.parseInt(db.getConfigDataBasedOnName("HierarchySelection"));
//
//        if (productHierarchy < 5) {
//            NotifyUtil.getOurInstance().showFilterDialog(getSFAFragmentActivity(), filterDialogListener,
//                    "Brands Filter", brandsList);
//        } else {
            NotifyUtil.getOurInstance().showBrandFilterDialog(getSFAFragmentActivity(), filterBrandDialogListener,
                    "Product Filter", TABLE_ORDER_PRODUCT_FILTERS, "");
//        }
    }

    /**
     * Fetch the order booking data from database and added that into order booking list
     * Init the order booking adapter with order booking list.
     * <p>
     * If the order booking adapter item count is 0 then shows the empty text view
     * else it shows the order booking recycler list
     */
    private void loadRecycleViewData() {

        if (!orderBookingVOList.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            recyclerView.setAdapter(orderBookingAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        }
        orderBookingAdapter.setOnItemClickListener(new OrderBookingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                enterOrderQuantity(position, ORDER_BOOKING_QUANTITY_DIALOG);
            }
        });
        orderBookingAdapter.setOnStockItemClickListener(new OrderBookingAdapter.OnStockItemClickListener() {
            @Override
            public void onStockItemClick(View view, int position) {
                enterOrderQuantity(position, Globals.ORDER_BOOKING_STOCK_DIALOG);
            }
        });

        orderBookingAdapter.setOnItemLongClickListener(new OrderBookingAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(View view, int position) {
                schemePosition = position;
                isSchemeDialogShowing = true;
                showSchemeDetail(position);
                return true;
            }
        });

        if (preferences.readString(PREF_ORDER_BOOKING_OPTIONS).equalsIgnoreCase(ORDER_SCREEN_TYPE_CUSTOM)) {
            orderBookingAdapter.setOnImageItemClickListener(new OrderBookingAdapter.OnImageItemClickListener() {
                @Override
                public void onImageItemClicked(View v, int position) {
                    try {
                        DataManager.getInstance().setImageOrderProductDetails(getHashMap(position));
                        DataManager.getInstance().setOrderBookingVOList(orderBookingAdapter.getOrderBookingVOList(), schemeProductDetail);
                        Intent intent = new Intent(v.getContext(), OrderBookProductActivity.class);
                        intent.putExtra("clickedPosition", position);
                        startActivityForResult(intent, IMAGE_SCREEN_QTY);
                    } catch (Exception e) {
                        Log.e(TAG, "onImageItemClicked: " + e.getMessage(), e);
                    }
                }
            });
        }

    }

    @NonNull
    private Map<String, Object> getHashMap(int position) {

        String prodCode = orderBookingVOList.get(position).getProdCode();
        String prodName = orderBookingVOList.get(position).getProdName();
        double quantity = orderBookingVOList.get(position).getQuantity();
        double stockQuantity = orderBookingVOList.get(position).getStockCheckQty();
        String uom = orderBookingVOList.get(position).getUomId();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("position", position);
        hashMap.put(RETAILER_CODE, orderBookingVOList.get(position).getRetailerCode());
        hashMap.put("productCode", prodCode);
        hashMap.put("productName", prodName);
        hashMap.put("distStateCode", orderBookingVOList.get(position).getDistrStateCode());
        hashMap.put("retailerStateCode", orderBookingVOList.get(position).getRetailerStateCode());
        hashMap.put("newRetailer", newRetailer);
        hashMap.put("quantity", quantity);
        hashMap.put("stockQuantity", stockQuantity);
        hashMap.put("callingScreen", "OrderBooking");
        hashMap.put("uom", uom);
        return hashMap;
    }


    /**
     * Shows the scheme details dialog with scheme description
     */
    private void showSchemeDetail(int pos) {

        String desc = getString(R.string.scheme_empty);

        if (orderBookingVOList.get(schemePosition).getDescription() != null
                && !"".equalsIgnoreCase(orderBookingVOList.get(schemePosition).getDescription())) {
            desc = orderBookingVOList.get(schemePosition).getDescription();
        }

        final Dialog schemeDialog = new Dialog(getSFAFragmentActivity(), R.style.ThemeDialogCustom);
        if (schemeDialog.isShowing()) return;
        schemeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        schemeDialog.setCanceledOnTouchOutside(false);
        schemeDialog.setCancelable(false);
        schemeDialog.setContentView(R.layout.scheme_description_dialog);

        final TextView popupProductFullName = schemeDialog.findViewById(R.id.popup_productFullName);
        final TextView popupDesc = schemeDialog.findViewById(R.id.popup_desc);

        popupProductFullName.setText(orderBookingVOList.get(schemePosition).getProdName());

        final ViewPager pager = schemeDialog.findViewById(R.id.schemeviewpager);
        SchemePagerAdapter myAdapter;
        try {
            List<SchemeModel> schemeModelList = new ArrayList<>();
            for (SchemeModel schemeModel : schemeProductDetail) {
                if (orderBookingVOList.get(pos).getProdCode().equalsIgnoreCase(schemeModel.getProductCode())) {
                    schemeModelList.add(schemeModel);
                }
            }
            myAdapter = new SchemePagerAdapter(getActivity(), schemeModelList);
            if (myAdapter.getCount() > 0)
                pager.setAdapter(myAdapter);
            else
                popupDesc.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.e(TAG, "showSchemeDetail: e" + e.getMessage(), e);
        }

        if (!schemeProductDetail.isEmpty()) {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);
        mTopMenuActionBar = menu;
        MenuItem favorite = menu.findItem(R.id.favorite);
        favorite.setVisible(false);

        if (db.isFavoriteMenu(Globals.NAME_ORDER_BOOKING)) {
            favorite.setIcon(R.drawable.ic_favorite_star_fill);
        } else {
            favorite.setIcon(R.drawable.ic_favorite_star);
        }

        MenuItem filter = menu.findItem(R.id.product_filter);
        filter.setVisible(false);
        updateListCount(menu);

        MenuItem item = menu.findItem(R.id.product_search);
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
                    if (orderBookingAdapter != null)
                        orderBookingAdapter.applySearchFilter(text);
                    searchText = text;
                }

                if (orderBookingAdapter != null)
                    updateListCount(menu);

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
    }

    public void updateListCount(Menu menu) {

        try {

            if (menu != null) {
                MenuItem countItem = menu.findItem(R.id.all_prod_count);
                countItem.setActionView(R.layout.coverage_msg);
                View view = countItem.getActionView();
                TextView msg = view.findViewById(R.id.coverage_txt);
                if (orderBookingAdapter != null && orderBookingAdapter.getItemCount() > 0)
                    msg.setText(String.valueOf(orderBookingAdapter.getItemCount()));
                else
                    msg.setText(String.valueOf("00"));
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != null)
            if (menuItem.getItemId() == R.id.product_filter) {
                isFilterDialogShowing = true;
                filterProducts();
            } else if (menuItem.getItemId() == R.id.product_voice_search) {
                isFilterDialogShowing = true;
                displaySpeechRecognizer();

            } else if (menuItem.getItemId() == R.id.favorite) {
                if (!db.isFavoriteMenu(Globals.NAME_ORDER_BOOKING)) {
                    menuItem.setIcon(R.drawable.ic_favorite_star_fill);
                    db.deleteQuickActions(Globals.NAME_ORDER_BOOKING);
                    db.insertQuickAction(Globals.NAME_ORDER_BOOKING, true);
                } else {
                    menuItem.setIcon(R.drawable.ic_favorite_star);
                    db.deleteQuickActions(Globals.NAME_ORDER_BOOKING);
                    db.insertQuickAction(Globals.NAME_ORDER_BOOKING, false);
                }
            }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        if (qtyPosition != -1) {
            outState.putBoolean(IS_QTY_DIALOG_SHOWING, isQtyDialogShowing);
            outState.putInt(QTY_POSITION, qtyPosition);
            outState.putString(ENTERED_QTY, enteredQty);
            outState.putString(ENTERED_REMARKS, enteredRemarks);
            outState.putBoolean(IS_REMARKS_FOCUSED, isRemarksFocused);
            outState.putInt(ENTERED_UOM, enteredUOM);

        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            if (qtyUpdatedOrderBooking != null && !qtyUpdatedOrderBooking.isEmpty()) {
                outState.putParcelableArrayList(QTY_LIST, (ArrayList<OrderBookingVO>) qtyUpdatedOrderBooking);
                outState.putParcelableArrayList(ORDER_BOOKING_LIST, (ArrayList<? extends Parcelable>) orderBookingVOList);
                if (orderBookingAdapter != null && orderBookingAdapter.getSearchList() != null)
                    outState.putParcelableArrayList(SEARCH_LIST, (ArrayList<? extends Parcelable>) orderBookingAdapter.getSearchList());
            }
        } else {
            preferences.setList(PREF_TEMP_QTY_UPDATE_ORDER_LIST, qtyUpdatedOrderBooking);
            preferences.setList(PREF_TEMP_ORDER_BOOKING_LIST, orderBookingVOList);
            if (orderBookingAdapter != null && orderBookingAdapter.getSearchList() != null)
                preferences.setList(PREF_TEMP_SEARCH_LIST, orderBookingAdapter.getSearchList());
        }
        if (searchText.length() > 0) {
            outState.putString(SEARCH_TEXT, searchText);
        }

        if (brandValue.length() > 0)
            outState.putString(BRAND_TEXT, brandValue);

        if (categoryValue.length() > 0)
            outState.putString(CATEGORY_TEXT, categoryValue);

        if (schemePosition != -1) {
            outState.putInt(SCHEME_POSITION, schemePosition);
            outState.putBoolean(IS_SCHEME_DIALOG_SHOWING, isSchemeDialogShowing);
        }

        outState.putBoolean(IS_FILTER_DIALOG_SHOWING, isFilterDialogShowing);

        outState.putSerializable("orderlist", (Serializable) orderBookingVOList);

        outState.putString("retailerType", retailerType);
        outState.putString("retailerName", retailerName);
        outState.putString("newRetailerRouteCode", newRetlrRouteCode);
        outState.putString("callingFragment", mCallingFragment);
        outState.putInt("NewRetailer", newRetailer);
        outState.putBoolean("isPreviousOrder", isPreviousOrder);

        super.onSaveInstanceState(outState);

    }

    /**
     * Init the dialog with it views.
     * Restore the data if previously any thing stored into order booking model
     * or temp variable for handling screen orientation.
     * At the time of pressing submit button the data will updated into order booking list
     * and updated into adapter's list and update into recycler list.
     *
     * @param dialogType used to Check stock or Orderbooking dialog
     * @param position   get the particular Order Booking Model from Order Booking
     */

    private void enterOrderQuantity(final int position, final int dialogType) {

        if (dialog != null && dialog.isShowing()) return;
        dialog = new Dialog(getSFAFragmentActivity(), R.style.ThemeDialogCustom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.quantity_dialog);

        orderBookingVO = orderBookingVOList.get(position);
        final TextView productName = dialog.findViewById(R.id.qty_dialog_title);
        final TextView lineTot = dialog.findViewById(R.id.line_tot_price_txt);
        perUnitLabel = dialog.findViewById(R.id.per_unit_label);
        perUnitLabelPrice = dialog.findViewById(R.id.per_unit_label_price_txt);
        final TextView soq = dialog.findViewById(R.id.qty_soq_txt);
        final TextView sih = dialog.findViewById(R.id.qty_sih_txt);
        final TextView tvQuantity = dialog.findViewById(R.id.tvQuantity);
        final TextView tvSlab = dialog.findViewById(R.id.tvSlab);
        final TextView mssQtyTxt = dialog.findViewById(R.id.mss_qty_txt);
        editQty = dialog.findViewById(R.id.qty_edt);
        final Button submit = dialog.findViewById(R.id.qty_submit_btn);
        final Button cancel = dialog.findViewById(R.id.qty_cancel_btn);
        uomSpinner = dialog.findViewById(R.id.spinner_uom);
        editRemarks = dialog.findViewById(R.id.edt_remarks);
        final LinearLayout layout = dialog.findViewById(R.id.stock_layout);
        rootLayout = dialog.findViewById(R.id.qty_dialog_layout);
        layout.setVisibility(View.VISIBLE);
        productName.setText(orderBookingVO.getProdName());
        if ("N".equalsIgnoreCase(db.getConfigDataBasedOnName(AppConstant.Configurations.CONFIG_SIH))) {
            sih.setVisibility(View.GONE);
        }
        if ("N".equalsIgnoreCase(db.getConfigDataBasedOnName(AppConstant.Configurations.CONFIG_SOQ))) {
            soq.setVisibility(View.GONE);
        }
        if(orderBookingVOList.get(position).getPpq() == null || orderBookingVOList.get(position).getPpq().isEmpty()){
            mssQtyTxt.setText("PPQ : -");
        }else {
            mssQtyTxt.setText(String.valueOf("PPQ : " + orderBookingVOList.get(position).getPpq()).replaceAll("~", ","));
        }
        sih.setText(String.valueOf("SIH : " + orderBookingVOList.get(position).getStockInHand()));
        soq.setText(String.valueOf("SOQ : " + orderBookingVOList.get(position).getSuggestedQty()));
        String uomGroupId = orderBookingVO.getUomGroupId();
        String defaultUomId = orderBookingVO.getDefaultUomid();

        // For Schemes
        mAppliedSlablist = new ArrayList<>();
        productSchemeModelList = new ArrayList<>();
        schemeSlabList = new ArrayList<>();
        freeProdSlabList = new ArrayList<>();

        editDialogType = dialogType;

        if (dialogType == ORDER_BOOKING_QUANTITY_DIALOG) {

            updateSchemeforSameProd(orderBookingVO);
            productSchemeModelList.addAll(new ArrayList<>(mOrderSupportUtil.getNonCombiSchemeList(orderBookingVOList.get(position).getProdCode(), schemeProductDetail)));
            schemeSlabList.addAll(db.getAllSchemeSlabDetail(productSchemeModelList));
            freeProdSlabList.addAll(db.getAllFreeProdList(productSchemeModelList));

            defaultUomId = mOrderSupportUtil.getDefaultUomId(defaultUomId, orderBookingVO.getUomId());
            loadUom(orderBookingVO, uomGroupId, defaultUomId, uomSpinner, enteredUOM == -1);

            tvQuantity.setText(getSFAFragmentActivity().getString(R.string.quantity));

            if (orderBookingVO.getQuantity() > 0) {
                editQty.setText(mOrderSupportUtil.getStringQty(orderBookingVO.getQuantity()));
                double currentQty = orderBookingVO.getQuantity();
                showSchemeMsg(currentQty, tvSlab, orderBookingVO, uomSpinner, lineTot);
            }

        } else {
            defaultUomId = mOrderSupportUtil.getDefaultUomId(defaultUomId, orderBookingVO.getStockUomId());
            loadUom(orderBookingVO, uomGroupId, defaultUomId, uomSpinner, enteredUOM == -1);

            if (orderBookingVO.getStockCheckQty() > 0) {
                editQty.setText(OrderSupportUtil.getInstance().getStringQty(orderBookingVO.getStockCheckQty()));
            }
            tvQuantity.setText(getSFAFragmentActivity().getString(R.string.stock));
        }

        mOrderSupportUtil.setUomSpinner(enteredUOM, uomSpinner);
        mOrderSupportUtil.setEnteredQty(enteredQty, editQty);
        editRemarks.setText(mOrderSupportUtil.getRemarks(enteredRemarks, orderBookingVO));
        mOrderSupportUtil.setFocusToRemarks(isRemarksFocused, editRemarks);

        editRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // before the editRemarks text changed this will execute
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // At the time of editRemarks text change this will execute
            }

            @Override
            public void afterTextChanged(Editable s) {
                enteredRemarks = s.toString();
            }
        });

        editRemarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isRemarksFocused = hasFocus;
            }
        });


        editQty.setSelection(editQty.getText().toString().length());

        editQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // before the editQty text changed this will execute
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // At the time of editQty text change this will execute
            }

            @Override
            public void afterTextChanged(Editable s) {
                enteredQty = s.toString();
                double currentQty = mOrderSupportUtil.getCurrentQuantityWithError(editQty);

                calculateSchemeOnTextChange(currentQty, dialogType, orderBookingVO, uomSpinner, tvSlab, lineTot, editQty.getText().toString());
            }
        });

        uomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enteredUOM = position;
                double currentQty = mOrderSupportUtil.getCurrentQty(editQty);
                calculateSchemeOnTextChange(currentQty, dialogType, orderBookingVO, uomSpinner, tvSlab, lineTot, String.valueOf(currentQty));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing selected from uomSpinner this will execute
            }
        });

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            orderAppliedSchemeList.addAll(editProdTempList);
            isQtyDialogShowing = false;
            qtyPosition = -1;
            enteredUOM = -1;
            enteredQty = "";
            enteredRemarks = "";
            isRemarksFocused = false;
        });

        mOrderSupportUtil.setLineTotalVale(orderBookingVO, lineTot, editQty, uomSpinner);

        editQty.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (isKeyboardActionTrue(actionId, event)) {

                    if (editQty.getText() != null && !editQty.getText().toString().isEmpty()) {
                        double qty = Double.valueOf(editQty.getText().toString());
                        if (orderBookingVO.getMustSellQty() > 0 && qty > 0) {
                            setOnSubmitClick(editQty, orderBookingVO, uomSpinner, editRemarks, position, dialogType, dialog);
                        } else if (orderBookingVO.getMustSellQty() == 0) {
                            setOnSubmitClick(editQty, orderBookingVO, uomSpinner, editRemarks, position, dialogType, dialog);

                        } else {
                            NotifyUtil.showDateTimeDialog(getActivity(), "Must Sell",
                                    "No Order taken for this MSS product. Do you want to proceed?", mustSellListener);
                        }
                    }

//                    setOnSubmitClick(editQty, orderBookingVO, uomSpinner, editRemarks, position, dialogType, dialog);
                }
                return false;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (editQty.getText() != null && !editQty.getText().toString().isEmpty()) {
                    double qty = Double.valueOf(editQty.getText().toString());
                    if (orderBookingVO.getMustSellQty() > 0 && qty > 0) {
                        setOnSubmitClick(editQty, orderBookingVO, uomSpinner, editRemarks, position, dialogType, dialog);
                    } else if (orderBookingVO.getMustSellQty() == 0) {
                        setOnSubmitClick(editQty, orderBookingVO, uomSpinner, editRemarks, position, dialogType, dialog);
                    } else {
                        NotifyUtil.showDateTimeDialog(getActivity(), "Must Sell",
                                "No Order taken for this MSS product. Do you want to proceed?", mustSellListener);
                    }
                }
            }
        });

        dialog.show();

        try {
            InputMethodManager inputManager = (InputMethodManager) getSFAFragmentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputManager != null;
            inputManager.hideSoftInputFromWindow(new View(getSFAFragmentActivity()).getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            Log.e(TAG, "enterOrderQuantity: " + e.getMessage(), e);
        }

        editQty.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSFAFragmentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        isQtyDialogShowing = true;
        qtyPosition = position;

    }

    private void calculateSchemeOnTextChange(double currentQty, int dialogType, OrderBookingVO orderBookingVO, Spinner uomSpinner, TextView tvSlab, TextView lineTot, String string) {
        if (dialogType == ORDER_BOOKING_QUANTITY_DIALOG) {
            showSchemeMsg(currentQty, tvSlab, orderBookingVO, uomSpinner, lineTot);
        }
    }

    private boolean isKeyboardActionTrue(int actionId, KeyEvent event) {
        return (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE);
    }

    private void setOnSubmitClick(EditText editQty, OrderBookingVO orderBookingVO, Spinner uomSpinner, EditText editRemarks, int position, int dialogType, Dialog dialog) {

        String uom = "";
        try {
            uom = uomSpinner.getSelectedItem().toString();
        } catch (Exception e) {
            Log.e(TAG, "setOnSubmitClick: " + e.getMessage(), e);
        }

        if (!uom.isEmpty()) {
            if (editQty.getText() != null && !editQty.getText().toString().isEmpty()) {
                checkOrderQty(Double.parseDouble(editQty.getText().toString()), orderBookingVO, uomSpinner.getSelectedItem().toString(), editRemarks, position, dialogType, dialog);
            } else {
                editQty.setError(getString(R.string.error_quantity));
            }
        } else {
            NotifyUtil.showSnackBar(getSFAFragmentActivity(), rootLayout, getString(R.string.error_uom), Toast.LENGTH_SHORT);
        }

    }

    private void checkOrderQty(double enterQty, OrderBookingVO orderBookingVO, String selectedItem, EditText editRemarks, int position, int dialogType, Dialog dialog) {
        double conversionQty = mOrderSupportUtil.calcWeightUomConversionQty(orderBookingVO, selectedItem, enterQty);
        if (!mOrderSupportUtil.isWeightUom(selectedItem) && enterQty % 1 != 0) {
            NotifyUtil.showSnackBar(getSFAFragmentActivity(), rootLayout, "Decimal quantity not allowed for Selected UOM (" + selectedItem + ") Quantity", Toast.LENGTH_SHORT);
        } else if (conversionQty < 0 || conversionQty % 1 != 0) {
            NotifyUtil.showSnackBar(getSFAFragmentActivity(), rootLayout, getString(R.string.error_weight), Toast.LENGTH_SHORT);
        } else if (mOrderSupportUtil.calcUomConversionQty(orderBookingVO, selectedItem, enterQty) >= 0) {
            qunatityDailogClose(enterQty, orderBookingVO, selectedItem, editRemarks.getText().toString(), position, dialogType);
            dialog.dismiss();
        } else {
            NotifyUtil.showSnackBar(getSFAFragmentActivity(), rootLayout, getString(R.string.error_weight_less_than_prod) + orderBookingVO.getNetWeight() + " " + orderBookingVO.getWeightType(), Toast.LENGTH_SHORT);
        }
    }

    private void showSchemeMsg(double currentQty, TextView tvSlab, OrderBookingVO orderBookingVO, Spinner uomSpinner, TextView lineTot) {
        String offerMsg = "";
        String suugestedOfferMsg;
        if (tvSlab != null) {
            tvSlab.setVisibility(View.GONE);
            tvSlab.setText(offerMsg);
        }
        double orderValue = 0.d;
        try {
            orderValue = mOrderSupportUtil.calculateLineOrderValue(orderBookingVO, uomSpinner.getSelectedItem().toString(), currentQty);
            double primaryDiscOrderValue = mOrderSupportUtil.getPrimDiscAppliedOrderValue(orderValue, orderBookingVO.getPrimaryDisc());

            int conversionQty = mOrderSupportUtil.calcUomConversionQty(orderBookingVO, uomSpinner.getSelectedItem().toString(), currentQty);

            perUnitLabel.setText("Per " + uomSpinner.getSelectedItem().toString() + " price");

            double labelPrice = orderBookingVO.getConversionFactor().get(uomSpinner.getSelectedItem().toString()) * orderBookingVO.getSellPrice().doubleValue();

            perUnitLabelPrice.setText(String.format(Locale.ENGLISH, "%.2f", labelPrice));
            orderBookingVO.setActualSellRate(new BigDecimal(labelPrice));

            mAppliedSlablist.clear();
            if (currentQty > 0) {
                mAppliedSlablist.addAll(schemeEngin.calculateSchemes(primaryDiscOrderValue, conversionQty, orderBookingVO,
                        productSchemeModelList, freeProdSlabList, schemeSlabList, uomIdSpinner));
            }
            offerMsg = schemeEngin.showSchemeDetailInText(mAppliedSlablist);
            if (!offerMsg.isEmpty() && tvSlab != null) {
                tvSlab.setVisibility(View.VISIBLE);
                tvSlab.setText(offerMsg);
            }
                suugestedOfferMsg = schemeEngin.showSuggestedSchemeDetailInText(orderBookingVO, productSchemeModelList, currentQty,
                        primaryDiscOrderValue, uomIdSpinner, uomSpinner.getSelectedItem().toString(), TABLE_PRODUCT_MASTER);

            Log.e(TAG, "showSchemeMsg: " + suugestedOfferMsg);

            if (!suugestedOfferMsg.isEmpty() && tvSlab != null) {
                tvSlab.setVisibility(View.VISIBLE);
                tvSlab.setText(String.valueOf(offerMsg + "\n" + suugestedOfferMsg));
            }

        } catch (Exception e) {
            Log.e(TAG, "showSchemeMsg: " + e.getMessage(), e);
        }


        lineTot.setText(String.format(Locale.getDefault(), "%.2f", orderValue));
    }
    private boolean isStockAvailable(OrderBookingVO orderBookingVO, String currentqty, String uom) {

        if (currentqty.equalsIgnoreCase("")) {
            currentqty = "0";
        }
        double currentQty = Double.parseDouble(currentqty);

        int conversionQty = mOrderSupportUtil.calcUomConversionQty(orderBookingVO, uom, currentQty);

        return orderBookingVO.getAvailQty() >= conversionQty;
    }

    private void updateSchemeforSameProd(OrderBookingVO orderBookingVO) {
        try {
            editProdTempList = new ArrayList<>();
            List<SchemeModel> tempOrderList = new ArrayList<>();
            for (SchemeModel orderAppliedSchemeProdlist : orderAppliedSchemeList) {
                if (orderAppliedSchemeProdlist.getProductCode().equalsIgnoreCase(orderBookingVO.getProdCode())) {
                    editProdTempList.add(orderAppliedSchemeProdlist);
                } else {
                    tempOrderList.add(orderAppliedSchemeProdlist);
                }
            }
            orderAppliedSchemeList.clear();
            orderAppliedSchemeList = tempOrderList;
            Log.d(TAG, "afterTextChanged: " + editProdTempList.size() + " | " + orderAppliedSchemeList.size());
        } catch (Exception e) {
            Log.e(TAG, "updateSchemeforSameProd: " + e.getMessage(), e);
        }
    }

    private void qunatityDailogClose(double currentQty, OrderBookingVO orderBookingVO, String uomSpinnerItem, String remarks, int position, int dialogType) {

        if (dialogType == ORDER_BOOKING_QUANTITY_DIALOG) {

            orderBookingVO.setQuantity(currentQty);

            orderBookingVO.setTotQty(mOrderSupportUtil.calcUomConversionQty(orderBookingVO, uomSpinnerItem, currentQty));

            // get the conversion factor and calculate with selling price and
            // multiplied with current quantity
            double orderValue = mOrderSupportUtil.calculateLineOrderValue(orderBookingVO, uomSpinnerItem, currentQty);

            orderBookingVO.setOrderValue(BigDecimal.valueOf(orderValue));
            orderBookingVO.setlGrossAmt(BigDecimal.valueOf(orderValue));
            orderBookingVO.setPrimDiscOrderValue(BigDecimal.valueOf(mOrderSupportUtil.getPrimDiscAppliedOrderValue(orderValue, orderBookingVO.getPrimaryDisc())));
            orderBookingVO.setRemarks(remarks);
            orderBookingVO.setUomId(uomSpinnerItem);
            orderBookingAdapter.updateQtyToUI(position, currentQty, orderValue, remarks, uomSpinnerItem);

        } else {
            orderBookingVO.setStockConversionFactor(mOrderSupportUtil.calcUomConversionQty(orderBookingVO, uomSpinnerItem, currentQty));
            orderBookingVO.setStockCheckQty(currentQty);
            // get the conversion factor and calculate with selling price and
            // multiplied with current quantity
            orderBookingVO.setStockOrderValue(BigDecimal.valueOf(mOrderSupportUtil.calculateLineOrderValue(orderBookingVO, uomSpinnerItem, currentQty)));
            orderBookingVO.setRemarks(remarks);
            orderBookingVO.setStockUomId(uomSpinnerItem);

            if (currentQty > 0) {
                orderBookingVO.setItemSelected(true);
                orderBookingVO.setCheckBoxEnabled(false);
            } else {
                orderBookingVO.setItemSelected(false);
                orderBookingVO.setCheckBoxEnabled(true);
            }

            orderBookingAdapter.updateStockQtyToUI(position, currentQty, uomSpinnerItem);
        }

        if (qtyUpdatedOrderBooking != null && qtyUpdatedOrderBooking.contains(orderBookingVO)) {
            qtyUpdatedOrderBooking.remove(orderBookingVO);
        }


        if (qtyUpdatedOrderBooking != null) {
            qtyUpdatedOrderBooking.add(orderBookingVO);
        }

        mOrderSupportUtil.calculateOrderTotal(qtyUpdatedOrderBooking, orderCountTxt, orderTotalTxt, isPreviousOrder);
        isQtyDialogShowing = false;
        qtyPosition = -1;
        enteredUOM = -1;
        enteredQty = "";
        enteredRemarks = "";
        isRemarksFocused = false;

    }

    private void loadUom(OrderBookingVO productObject, String prodCode, String defaultUomId, Spinner uomSpinner, boolean selectUOM) {
        List<OrderBookingVO> orderBooking;

        ArrayList<String> uomDescSpinner = new ArrayList<>();
        uomIdSpinner = new ArrayList<>();

        orderBooking = db.getUom(productObject.getProdCode(), TABLE_PRODUCT_UOM_MASTER);

        int listSize = orderBooking.size();
        for (int i = 0; i < listSize; i++) {
            uomDescSpinner.add(orderBooking.get(i).getDefaultUomid());
            uomIdSpinner.add(orderBooking.get(i).getUomGroupId());
        }
        uomIdSpinner.addAll(mOrderSupportUtil.getWeightUOMs(productObject));//for adding Weight UOM
        uomDescSpinner.addAll(mOrderSupportUtil.getWeightUOMs(productObject));//for adding Weight UOM

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getSFAFragmentActivity(), android.R.layout.simple_spinner_item, uomDescSpinner);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uomSpinner.setAdapter(spinnerArrayAdapter);
        if (selectUOM) {
            int uomListSize = uomIdSpinner.size();
            for (int i = 0; i < uomListSize; i++) {
                if (String.valueOf(uomIdSpinner.get(i)).equals(defaultUomId)) {
                    uomSpinner.setSelection(i);
                }
            }
        }
    }

    /**
     * Check the orientation handling temp variable is null.
     * If not null then get temp variable value and set to radio group
     * else go with zeroNonZeroResult
     *
     * @param zeroRG set this to Zero Product radio group
     */
    private void setZeroNonZeroProductRG(RadioGroup zeroRG) {
        OrderSupportUtil.ZeroResult result = tempZeroNonZeroResult != null ? tempZeroNonZeroResult : zeroNonZeroResult;

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
     * Get the Radio Group result and store it into to zeroNonZeroResult or tempZeroNonZeroResult
     *
     * @param checkedRadioButtonId get the response of zero product group with this radio button id
     * @param isFromSubmit         If from submit button then store the result to zeroNonZeroResult
     *                             else tempZeroNonZeroResult
     */
    void assignZeroGroupValue(int checkedRadioButtonId, boolean isFromSubmit) {
        OrderSupportUtil.ZeroResult result;
        switch (checkedRadioButtonId) {
            case R.id.zero_btn:
                result = OrderSupportUtil.ZeroResult.ZERO_PRODUCTS;
                break;
            case R.id.non_zero_btn:
                result = OrderSupportUtil.ZeroResult.NON_ZERO_PRODUCTS;
                break;
            case R.id.all_btn:
            default:
                result = OrderSupportUtil.ZeroResult.ALL_PRODUCTS;
                break;
        }
        if (isFromSubmit)
            zeroNonZeroResult = result;
        else
            tempZeroNonZeroResult = result;
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

        final Dialog proFilterDialog = new Dialog(getSFAFragmentActivity(), R.style.ThemeDialogCustom);

        if (proFilterDialog.isShowing()) return;

        proFilterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        proFilterDialog.setCanceledOnTouchOutside(false);
        proFilterDialog.setCancelable(false);
        proFilterDialog.setContentView(R.layout.filter_dialog);
        final Button submit = proFilterDialog.findViewById(R.id.filter_submit_btn);
        final Button cancel = proFilterDialog.findViewById(R.id.filter_cancel_btn);
        final RadioGroup zeroNonZeroRadioGroup = proFilterDialog.findViewById(R.id.zero_non_zero_radio_group);
        final Spinner brandSpinner = proFilterDialog.findViewById(R.id.spinner_brand);
        final Spinner categorySpinner = proFilterDialog.findViewById(R.id.spinner_category);

        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(getSFAFragmentActivity(), R.layout.spinner_list_item, brandsList);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(brandAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getSFAFragmentActivity(), R.layout.spinner_list_item, categoryList);
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

                proFilterDialog.dismiss();
                isFilterDialogShowing = false;
                clearTempFilterFields();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                assignZeroGroupValue(zeroNonZeroRadioGroup.getCheckedRadioButtonId(), true);
                brandValue = brandSpinner.getSelectedItem().toString();
                categoryValue = categorySpinner.getSelectedItem().toString();

                setApplyFilter();
                proFilterDialog.dismiss();
                isFilterDialogShowing = false;
                clearTempFilterFields();
            }
        });

        proFilterDialog.show();
    }


    public void showCategoryFilterDialog() {

        if (filterDialog != null && filterDialog.isShowing()) return;
        filterDialog = new BottomSheetDialog(getSFAFragmentActivity());
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.category_filter_dialog);
        filterDialog.setCancelable(false);

        final RadioGroup zeroNonZeroRadioGroup = filterDialog.findViewById(R.id.zero_non_zero_radio_group);
        ImageView cancelBtn = filterDialog.findViewById(R.id.cat_filter_cancel_btn);
        LinearLayout stockFilterLayout = filterDialog.findViewById(R.id.stock_filter_layout);
        View lineDivider = filterDialog.findViewById(R.id.line_divider);


        final RecyclerView filterRecyclerView = filterDialog.findViewById(R.id.cat_filter_recyclerview);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        filterRecyclerView.setLayoutManager(manager);

        setZeroNonZeroProductRG(zeroNonZeroRadioGroup);
        zeroNonZeroRadioGroup.setOnCheckedChangeListener(zeroRadioGroupListener);

        if (db.getConfigDataBasedOnName(CONFIG_ENABLE_OTHER_FILTER).equalsIgnoreCase("Y")) {
            stockFilterLayout.setVisibility(View.VISIBLE);
            lineDivider.setVisibility(View.VISIBLE);
        } else {
            stockFilterLayout.setVisibility(View.GONE);
            lineDivider.setVisibility(View.GONE);
        }

        List<ProFilterModel> level1 = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            ProFilterModel model = new ProFilterModel();
            model.setHierLelevelName1(categoryList.get(i));
            level1.add(model);
        }

        final ProductFilterAdapter adapter = new ProductFilterAdapter(getSFAFragmentActivity(), level1,
                Globals.getOurInstance().getOtherFilterPso(), false, null);

        if (adapter.getItemCount() > 0) {
            filterRecyclerView.setAdapter(adapter);
            adapter.setOnBrandItemClickListener(new ProductFilterAdapter.OnListBrandItemClick() {
                @Override
                public void onItemClick(int pos, String brandVal) {
                    categoryValue = brandVal;
                    globalsInstance.setOtherFilterPso(pos);

                    assignZeroGroupValue(zeroNonZeroRadioGroup.getCheckedRadioButtonId(), true);

                    setApplyFilter();
                    filterDialog.dismiss();
                    isFilterDialogShowing = false;
                    clearTempFilterFields();
                }
            });
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
                isFilterDialogShowing = false;
                clearTempFilterFields();
            }
        });

        if (filterDialog != null && !filterDialog.isShowing())
            filterDialog.show();
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

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.orderbooking_submit_btn) {
            if (Double.parseDouble(orderTotalTxt.getText().toString()) == 0) {
                NotifyUtil.showSnackBar(getSFAFragmentActivity(), orderBookingLayout,
                        getString(R.string.err_no_valid_order), Snackbar.LENGTH_LONG);
            } else {
                new AsyncInsertData(OrderBookingFragment.this).execute();

            }
        } else if (i == R.id.brand_filter_btn) {
            showBrandFilterDialog();
        } else if (i == R.id.other_filter_btn) {
            showCategoryFilterDialog();
        }
    }

    @Override
    public void newLocation(Location newLocation) {
        // New Location is updated
        globalsInstance.setStrLatitude(String.valueOf(newLocation.getLatitude()));
        globalsInstance.setStrLongitude(String.valueOf(newLocation.getLongitude()));

        //Check if Lat and Lon is 0.0.
        if (globalsInstance.getStrLatitude().equals("0.0") && globalsInstance.getStrLongitude().equals("0.0")) {
            globalsInstance.setStrLatitude(null);
            globalsInstance.setStrLongitude(null);
        }

        if (newLocation.getLatitude() > 0.0 && newLocation.getLongitude() > 0.0) {
            preferences.writeString(PREF_LAST_LAT, String.valueOf(newLocation.getLatitude()));
            preferences.writeString(PREF_LAST_LONG, String.valueOf(newLocation.getLongitude()));
        }
    }

    private void loadAdapterData() {

        try {
            if (orderBookingVOList != null && !orderBookingVOList.isEmpty()) {
                orderBookingAdapter = new OrderBookingAdapter(getActivity(), orderBookingVOList,
                        mCallingFragment, "OrderBookingFragment");

            }

            if (qtyUpdatedOrderBooking != null) {
                qtyUpdatedOrderBooking.clear();
                qtyUpdatedOrderBooking.addAll(orderBookingVOList);
            }

//            brandsList = mOrderSupportUtil.loadBrandsList(TABLE_PRODUCTS, preferences.readString(PREF_SALESMANCODE));
//            brandDataList = mOrderSupportUtil.getBrandDataList(TABLE_PRODUCTS, preferences.readString(PREF_SALESMANCODE));

            categoryList = mOrderSupportUtil.loadCategoryList();
            loadRecycleViewData();
            mOrderSupportUtil.calculateOrderTotal(qtyUpdatedOrderBooking, orderCountTxt, orderTotalTxt, isPreviousOrder);

        } catch (Exception e) {
            if (orderBookingVOList != null && !orderBookingVOList.isEmpty()) {
                orderBookingAdapter = new OrderBookingAdapter(getActivity(), orderBookingVOList,
                        mCallingFragment, "OrderBookingFragment");
            }
            Log.e(TAG, "loadAdapterData 2: " + e.getMessage(), e);

        }
        if (mTopMenuActionBar != null) {
            updateListCount(mTopMenuActionBar);
        }
    }

    @Override
    public void onStop() {

        globalsInstance.setCategorPos(0);
        globalsInstance.setBrandPos(0);
        globalsInstance.setOtherFilterPso(0);

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        super.onStop();
    }

    @Override
    public void onConnectionSuccess() {
        // On Connection Success
    }

    @Override
    public void onConnectionError() {
        // On Connection Error
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {

        Intent intent = new Intent(getActivity(), VoiceToStringActivity.class);
        startActivityForResult(intent, VOICE_SEARCH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == VOICE_SEARCH && resultCode == RESULT_OK) {
            ifLogic(data);

        } else if (requestCode == IMAGE_SCREEN_QTY && resultCode == RESULT_OK) {
            if (data != null) {
                Integer position = data.getIntExtra("position", 0);
                Integer qty = data.getIntExtra("quantity", 0);
                String uom = data.getStringExtra("uom");

                qunatityDailogClose(qty, orderBookingVOList.get(position), uom, "", position, ORDER_BOOKING_QUANTITY_DIALOG);
                orderBookingAdapter.notifyDataSetChanged();
            } else {
                Log.e("RESULT_OKelse", "orderBookingVOList" + orderBookingVOList.size());

                List<OrderBookingVO> tempOrderBookingVOList = DataManager.getInstance().getSwipeOrderBookingVOList();

                for (int i = 0; i < tempOrderBookingVOList.size(); i++) {
                    if (tempOrderBookingVOList.get(i).getQuantity() > 0) {
                        qunatityDailogClose(tempOrderBookingVOList.get(i).getQuantity(),
                                tempOrderBookingVOList.get(i), tempOrderBookingVOList.get(i).getUomId(), "", i, ORDER_BOOKING_QUANTITY_DIALOG);

                        Log.e("tempOrderBookingVOList", "tempOrderBookingVOList" + tempOrderBookingVOList.get(i).getProdName());
                    }
                }

                orderBookingAdapter.notifyDataSetChanged();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ifLogic(Intent data) {
        if (data != null) {
            String resultData = data.getStringExtra("voiceTxt");
            if (resultData.length() > 0) {
                setApplyVoiceSearchFilter(resultData);
                searchText = resultData;
            }
        }
    }

    private void setApplyVoiceSearchFilter(String resultData) {
        if (orderBookingAdapter != null)
            orderBookingAdapter.applySearchFilter(resultData);

    }

    private void showProgressDialog(Activity fragActivity, String tvMessage) {
        if (progressDialog != null && progressDialog.isShowing()) return;
        progressDialog = new Dialog(fragActivity, R.style.ThemeDialogCustom);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.layout_loading_spinner);
        TextView tvLoading = progressDialog.findViewById(R.id.tvLoading);
        RelativeLayout layoutLoding = progressDialog.findViewById(R.id.layoutLoading);
        layoutLoding.setVisibility(View.VISIBLE);
        layoutLoding.setBackgroundColor(ContextCompat.getColor(fragActivity, R.color.transparent));
        tvLoading.setText(tvMessage);
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void cancleProgressDialog() {
        try {
            if (progressDialog != null)
                progressDialog.dismiss();

        } catch (Exception e) {
            Log.e(TAG, "cancleProgressDialog: " + e.getMessage(), e);
        }
    }

    private static class AsyncInsertData extends AsyncTask<Void, Void, Boolean> {
        private final WeakReference<OrderBookingFragment> activityReference;
        private final OrderBookingFragment activity;
        private int scrollPosition = 0;

        private AsyncInsertData(OrderBookingFragment context) {
            activityReference = new WeakReference<>(context);
            activity = activityReference.get();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.showProgressDialog(activity.getActivity(), activity.getResources().getString(R.string.MSG_ORDER_PROCESSING));
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            boolean isStockTakeEnable = activity.db.getConfigDataBasedOnName(CONFIG_STOCK_TAKE).equalsIgnoreCase("Y");

            if ((isStockTakeEnable && checkWeightError() && checkStockTakeError()) || (!isStockTakeEnable && checkWeightError())) {
                return storeOrderBooking();
            } else if (isStockTakeEnable && !checkStockTakeError()) {
                return weightStockError();
            } else {
                return weightOrderError();
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            activity.cancleProgressDialog();
            if (aBoolean) {
                goToSummary();
            } else {
                activity.recyclerView.getLayoutManager().scrollToPosition(scrollPosition);
            }
        }

        private boolean weightOrderError() {
            NotifyUtil.showSnackBar(activity.getSFAFragmentActivity(), activity.orderBookingLayout,
                    activity.qtyUpdatedOrderBooking.get(activity.weightCheckPos).getProdName() + activity.getString(R.string.mrp_msg)
                            + activity.qtyUpdatedOrderBooking.get(activity.weightCheckPos).getMrp()
                            + activity.getString(R.string.wrong_weight) + activity.getString(R.string.error_weight), Toast.LENGTH_SHORT);
            scrollPosition = activity.orderBookingVOList.indexOf(activity.qtyUpdatedOrderBooking.get(activity.weightCheckPos));
            return false;
        }

        private boolean weightStockError() {
            NotifyUtil.showSnackBar(activity.getSFAFragmentActivity(), activity.orderBookingLayout,
                    activity.qtyUpdatedOrderBooking.get(activity.weightStockCheckPos).getProdName() + activity.getString(R.string.mrp_msg)
                            + activity.qtyUpdatedOrderBooking.get(activity.weightStockCheckPos).getMrp()
                            + activity.getString(R.string.wrong_weight) + activity.getString(R.string.error_stock_weight), Toast.LENGTH_SHORT);
            scrollPosition = activity.orderBookingVOList.indexOf(activity.qtyUpdatedOrderBooking.get(activity.weightStockCheckPos));
            return false;
        }

        /**
         * validate the entered weight of the stock product and get the position of the wrong stock product
         *
         * @return true or false
         */
        private boolean checkStockTakeError() {
            if (activity.qtyUpdatedOrderBooking != null && !activity.qtyUpdatedOrderBooking.isEmpty()) {
                for (int i = 0; i < activity.qtyUpdatedOrderBooking.size(); i++) {
                    final OrderBookingVO orderBookingVO = activity.qtyUpdatedOrderBooking.get(i);
                    if (activity.qtyUpdatedOrderBooking.get(i).getStockCheckQty() > 0) {
                        double conversionQty = activity.mOrderSupportUtil.calcWeightUomConversionQty(orderBookingVO, orderBookingVO.getStockUomId(), orderBookingVO.getStockCheckQty());
                        if (conversionQty < 0 || conversionQty % 1 != 0) {
                            activity.weightStockCheckPos = i;
                            return false;
                        }
                    }
                }
            }
            return true;
        }


        /**
         * validate the entered weight of the order product and get the position of the wrong Order product
         *
         * @return true or false
         */
        private boolean checkWeightError() {
            if (activity.qtyUpdatedOrderBooking != null && !activity.qtyUpdatedOrderBooking.isEmpty()) {
                for (int i = 0; i < activity.qtyUpdatedOrderBooking.size(); i++) {
                    final OrderBookingVO orderBookingVO = activity.qtyUpdatedOrderBooking.get(i);
                    if (activity.qtyUpdatedOrderBooking.get(i).getQuantity() > 0) {
                        double conversionQty = activity.mOrderSupportUtil.calcWeightUomConversionQty(orderBookingVO, orderBookingVO.getUomId(), orderBookingVO.getQuantity());
                        if (conversionQty < 0 || conversionQty % 1 != 0) {
                            activity.weightCheckPos = i;
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        /**
         * check the stock Available for the product in van Sales Order Only
         *
         * @return true or false
         */

        private void goToSummary() {

            ESFAFragTags fragTags;
            Bundle bundle = new Bundle();
            bundle.putString("retailerName", activity.retailerName);
            bundle.putBoolean("quickOrder", false);
            bundle.putInt("NewRetailer", activity.newRetailer);
            bundle.putString("screenType", NAME_ORDER_BOOKING);
            fragTags = ESFAFragTags.RETAILER_ORDER_BOOKING_SUMMARY;

            SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();
            if (!sfaFragManger.updateFragment(fragTags, true, activity.getSFAFragmentActivity(), bundle) && BuildConfig.DEBUG) {

                Log.e("Ordersummaryfragment", "Error in creating fragment");
            }

        }


        /**
         * Store the taken Order Booking details into DB
         */
        private boolean storeOrderBooking() {
//
            String routeCode = "";
//            if (activity.newRetailer == 1) {
//                routeCode = activity.newRetlrRouteCode;
//                activity.preferences.writeString(PREF_ROUTECODE, routeCode);
//            } else {
//                routeCode = activity.preferences.readString(PREF_ROUTECODE);
//            }
//

            if (activity.qtyUpdatedOrderBooking != null && !activity.qtyUpdatedOrderBooking.isEmpty()) {

                //Apply All schemes and get List
                List<SchemeModel> allSchemeAppliedList = applyAllSchemeAndTaxAtInvoiceLevel();
                activity.isPreviousOrder = false;

                for(OrderBookingVO orderBookingVO : activity.qtyUpdatedOrderBooking){
                    if(orderBookingVO.getQuantity()>0){
                        activity.invoiceNo = orderBookingVO.getReadableInvNo();
                        break;
                    }
                }

                if(activity.invoiceNo!=null && !activity.invoiceNo.isEmpty()) {
                    activity.db.insertOrderBooking(activity.invoiceNo, routeCode, "", activity.qtyUpdatedOrderBooking, activity.retailerType, activity.newRetailer);

                    activity.db.deleteAllSchemeProdRetailer(routeCode, "", activity.qtyUpdatedOrderBooking, TABLE_ORDER_BOOKING);

                    activity.db.insertOrderedProductSchemeList(activity.invoiceNo, routeCode, "", allSchemeAppliedList, TABLE_ORDER_BOOKING);

//                activity.db.insertStockTakeData(activity.invoiceNo, routeCode, activity.retailerCode, DateUtil.getCurrentYearMonthDate(), activity.qtyUpdatedOrderBooking);
                }
            }
            return true;
        }

        private List<SchemeModel> applyAllSchemeAndTaxAtInvoiceLevel() {

            String masterTableName;

            masterTableName = TABLE_PRODUCT_MASTER;

            List<OrderBookingVO> qtyProdList = activity.mOrderSupportUtil.filterQtyAvailOProdList(activity.orderNoList, activity.qtyUpdatedOrderBooking);
            List<SchemeModel> allSchemeAppliedList = new ArrayList<>();
            try {
                allSchemeAppliedList.clear();
                List<SchemeModel> appliedCombiScheme = activity.schemeEngin.fetchCombiProdlist(qtyProdList, activity.schemeProductDetail, masterTableName);//combi scheme Applied List
                List<SchemeModel> appliedMultiProdScheme = activity.schemeEngin.fetchSchemeMultiProdlist(qtyProdList, activity.schemeProductDetail, masterTableName);// Multiple prod scheme applied List
                allSchemeAppliedList.addAll(appliedCombiScheme);
                allSchemeAppliedList.addAll(appliedMultiProdScheme);
                activity.schemeProductDetail.removeAll(allSchemeAppliedList);//remove applied scheme from schemeProduct
                List<SchemeModel> lineLevelScemeList = activity.schemeEngin.fetchLineLevelSchemeList(qtyProdList, activity.schemeProductDetail, masterTableName);//line level scheme applied list

                allSchemeAppliedList.addAll(lineLevelScemeList);

                int qtyProdListSize = qtyProdList.size();
                int appliedCombiSchemeSize = appliedCombiScheme.size();
                int lineLevelScemeListSize = lineLevelScemeList.size();
                int appliedMultiProdSchemeSize = appliedMultiProdScheme.size();
                for (int i = 0; i < qtyProdListSize; i++) {
                    double normalSchemeAmount = 0;
                    double combiSchemeAmount = 0;
                    double multiProdSchemeAmount = 0;

                    for (int j = 0; j < appliedCombiSchemeSize; j++) {
                        combiSchemeAmount = activity.mOrderSupportUtil.getSchemeAmount(qtyProdList.get(i).getProdCode(), qtyProdList.get(i).getProdBatchCode(), appliedCombiScheme, combiSchemeAmount, j);
                    }
                    for (int j = 0; j < lineLevelScemeListSize; j++) {
                        normalSchemeAmount = activity.mOrderSupportUtil.getSchemeAmount(qtyProdList.get(i).getProdCode(), qtyProdList.get(i).getProdBatchCode(), lineLevelScemeList, normalSchemeAmount, j);
                    }
                    for (int j = 0; j < appliedMultiProdSchemeSize; j++) {
                        multiProdSchemeAmount = activity.mOrderSupportUtil.getSchemeAmount(qtyProdList.get(i).getProdCode(), qtyProdList.get(i).getProdBatchCode(), appliedMultiProdScheme, multiProdSchemeAmount, j);
                    }

                    setTaxPercentage(normalSchemeAmount, combiSchemeAmount, multiProdSchemeAmount, qtyProdList.get(i));

                }
                Log.d(TAG, "storeOrderBooking: " + appliedCombiScheme.size());

            } catch (Exception e) {
                Log.e(TAG, "applyAllSchemeAndTaxAtInvoiceLevel: " + e.getMessage(), e);
            }
            return allSchemeAppliedList;
        }

        private void setTaxPercentage(double normalSchemeAmount, double combiSchemeAmount, double multiProdSchemeAmount,
                                      OrderBookingVO qtyProdList) {

            int i = activity.qtyUpdatedOrderBooking.indexOf(qtyProdList);
            double schemeAmount;
//            double primDiscOrderValue = activity.qtyUpdatedOrderBooking.get(i).getPrimDiscOrderValue().doubleValue();
//            schemeAmount = normalSchemeAmount + combiSchemeAmount + multiProdSchemeAmount;
//            activity.qtyUpdatedOrderBooking.get(i).setSchemeAmount(BigDecimal.valueOf(schemeAmount));
//            activity.qtyUpdatedOrderBooking.get(i).setNetAmount(BigDecimal.valueOf(primDiscOrderValue));
//            activity.qtyUpdatedOrderBooking.get(i).setTotalAmount(BigDecimal.valueOf(primDiscOrderValue));
//
//            List<TaxModel> taxModelList = activity.mOrderSupportUtil.fetchProductTaxList(activity.qtyUpdatedOrderBooking.get(i),
//                    activity.qtyUpdatedOrderBooking.get(i).getDistrStateCode(), activity.qtyUpdatedOrderBooking.get(i).getRetailerStateCode());
//
//            double cgstValue = 0;
//            double cgstPrec = 0;
//            double sgstValue = 0;
//            double sgstPrec = 0;
//            double utgstValue = 0;
//            double utgstPrec = 0;
//            double igstValue = 0;
//            double igstPrec = 0;
//            for (TaxModel taxModel : taxModelList) {
//                double calcualteOrderValue = activity.mOrderSupportUtil.applySchemeReduceToOrderValue(schemeAmount, primDiscOrderValue, taxModel.getSchemeReduce());
//                activity.qtyUpdatedOrderBooking.get(i).setNetAmount(BigDecimal.valueOf(calcualteOrderValue));
//                if (taxModel.getTaxName().equalsIgnoreCase(TAX_CGST)) {
//                    cgstValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getOutputTaxPerc());
//                    cgstPrec = taxModel.getOutputTaxPerc();
//                    activity.qtyUpdatedOrderBooking.get(i).setCgstperc(taxModel.getOutputTaxPerc());
//                    activity.qtyUpdatedOrderBooking.get(i).setCgstvalue(BigDecimal.valueOf(cgstValue));
//
//                }
//                if (taxModel.getTaxName().equalsIgnoreCase(TAX_SGST)) {
//                    sgstValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getOutputTaxPerc());
//                    sgstPrec = taxModel.getOutputTaxPerc();
//                    activity.qtyUpdatedOrderBooking.get(i).setSgstPerc(taxModel.getOutputTaxPerc());
//                    activity.qtyUpdatedOrderBooking.get(i).setSgstValue(BigDecimal.valueOf(sgstValue));
//                }
//                if (taxModel.getTaxName().equalsIgnoreCase(TAX_UTGST)) {
//                    utgstValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getOutputTaxPerc());
//                    utgstPrec = taxModel.getOutputTaxPerc();
//                    activity.qtyUpdatedOrderBooking.get(i).setUtgstPerc(taxModel.getOutputTaxPerc());
//                    activity.qtyUpdatedOrderBooking.get(i).setUtgstValue(BigDecimal.valueOf(utgstValue));
//                }
//                if (taxModel.getTaxName().equalsIgnoreCase(TAX_IGST)) {
//                    igstValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getOutputTaxPerc());
//                    igstPrec = taxModel.getOutputTaxPerc();
//                    activity.qtyUpdatedOrderBooking.get(i).setIgstPerc(taxModel.getOutputTaxPerc());
//                    activity.qtyUpdatedOrderBooking.get(i).setIgstvalue(BigDecimal.valueOf(igstValue));
//                }
//            }
//
//            activity.qtyUpdatedOrderBooking.get(i).setTotalAmount(BigDecimal.valueOf(primDiscOrderValue + cgstValue + sgstValue + utgstValue + igstValue - schemeAmount));

            String distStateCode = activity.qtyUpdatedOrderBooking.get(i).getDistrStateCode();
            String retailerStateCode = activity.qtyUpdatedOrderBooking.get(i).getRetailerStateCode();

            boolean isCessTaxApplicable = false;
            if (activity.schemeEngin.isCessTaxApplicable(distStateCode)) {
                isCessTaxApplicable = true;
            }
            double primDiscOrderValue = activity.qtyUpdatedOrderBooking.get(i).getPrimDiscOrderValue().doubleValue();
            double grossValue = activity.qtyUpdatedOrderBooking.get(i).getQuantity()*activity.qtyUpdatedOrderBooking.get(i).getActualSellRate().doubleValue();
            schemeAmount = normalSchemeAmount + combiSchemeAmount + multiProdSchemeAmount;
            activity.qtyUpdatedOrderBooking.get(i).setSchemeAmount(BigDecimal.valueOf(schemeAmount));
            activity.qtyUpdatedOrderBooking.get(i).setNetAmount(BigDecimal.valueOf(primDiscOrderValue));
            activity.qtyUpdatedOrderBooking.get(i).setlGrossAmt(BigDecimal.valueOf(grossValue));
            activity.qtyUpdatedOrderBooking.get(i).setTotalAmount(BigDecimal.valueOf(primDiscOrderValue));
            List<TaxMasterModel> taxModelList = activity.mOrderSupportUtil.fetchProductTaxMasterList(activity.qtyUpdatedOrderBooking.get(i), distStateCode, retailerStateCode);

            double cgstValue = 0;
            double sgstValue = 0;//For union territory UTGST tax % value will come in sgst column in DB
            double igstValue = 0;
            double cessValue = 0;
            double cgstPrec = 0;
            double sgstPrec = 0;
            double cessPerc = 0;
            double igstPrec = 0;
            for (TaxMasterModel taxModel : taxModelList) {
                double calcualteOrderValue = activity.mOrderSupportUtil.applySchemeReduceToOrderValue(schemeAmount, primDiscOrderValue, "R");
                activity.qtyUpdatedOrderBooking.get(i).setNetAmount(BigDecimal.valueOf(calcualteOrderValue));

                if ((distStateCode != null && retailerStateCode != null)
                        && (distStateCode.equalsIgnoreCase(retailerStateCode))) {

                    cgstValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getCgst());
                    cgstPrec = taxModel.getCgst();
                    activity.qtyUpdatedOrderBooking.get(i).setCgstperc(taxModel.getCgst());
                    activity.qtyUpdatedOrderBooking.get(i).setCgstvalue(BigDecimal.valueOf(cgstValue));

                    boolean isUnionTerritory = activity.mOrderSupportUtil.isDistrUnionTerritory(activity.mOrderSupportUtil.db, distStateCode);

                    //For union territory UTGST tax % value will come in sgst column in DB
                    sgstValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getSgst());
                    sgstPrec = taxModel.getSgst();
                    if (isUnionTerritory) {
                        activity.qtyUpdatedOrderBooking.get(i).setUtgstPerc(taxModel.getSgst());
                        activity.qtyUpdatedOrderBooking.get(i).setUtgstValue(BigDecimal.valueOf(sgstValue));
                    } else {
                        activity.qtyUpdatedOrderBooking.get(i).setSgstPerc(taxModel.getSgst());
                        activity.qtyUpdatedOrderBooking.get(i).setSgstValue(BigDecimal.valueOf(sgstValue));
                    }

                } else {

                    igstValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getIgst());
                    igstPrec = taxModel.getIgst();
                    activity.qtyUpdatedOrderBooking.get(i).setIgstPerc(taxModel.getIgst());
                    activity.qtyUpdatedOrderBooking.get(i).setIgstvalue(BigDecimal.valueOf(igstValue));
                }

                // If Distr is from Kerala and Retailer is active set cess tax
                if (isCessTaxApplicable) {
                    cessValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getCess());
                    cessPerc = taxModel.getCess();
                    activity.qtyUpdatedOrderBooking.get(i).setCessPerc(taxModel.getCess());
                    activity.qtyUpdatedOrderBooking.get(i).setCessValue(BigDecimal.valueOf(cessValue));
                }
            }
            activity.qtyUpdatedOrderBooking.get(i).setTotalAmount(BigDecimal.valueOf(primDiscOrderValue + cgstValue + sgstValue + igstValue + cessValue - schemeAmount));


            /*  PTR With tax calculation */
            if (activity.isPTRWithTax) {

                double lTax = cgstPrec + sgstPrec + cessPerc + igstPrec;
                double taxableAmt = (activity.qtyUpdatedOrderBooking.get(i).getOrderValue().doubleValue() - schemeAmount) / (1 + lTax / 100);
                ptrWithTaxCalculation(i, taxModelList, taxableAmt);
            }
        }

        private void ptrWithTaxCalculation(int i, List<TaxMasterModel> taxModelList, double calcualteOrderValue) {

            activity.qtyUpdatedOrderBooking.get(i).setlGrossAmt(BigDecimal.valueOf(calcualteOrderValue));

            double cgstValue = 0;
            double sgstValue = 0;//For union territory UTGST tax % value will come in sgst column in DB
            double igstValue = 0;
            double cessValue = 0;
            String distStateCode = activity.qtyUpdatedOrderBooking.get(i).getDistrStateCode();
            String retailerStateCode = activity.qtyUpdatedOrderBooking.get(i).getRetailerStateCode();

            boolean isCessTaxApplicable = false;
            if (activity.schemeEngin.isCessTaxApplicable(distStateCode)) {
                isCessTaxApplicable = true;
            }

            for (TaxMasterModel taxModel : taxModelList) {
                activity.qtyUpdatedOrderBooking.get(i).setNetAmount(BigDecimal.valueOf(calcualteOrderValue));

                if ((distStateCode != null && retailerStateCode != null)
                        && (distStateCode.equalsIgnoreCase(retailerStateCode))) {

                    cgstValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getCgst());
                    activity.qtyUpdatedOrderBooking.get(i).setCgstperc(taxModel.getCgst());
                    activity.qtyUpdatedOrderBooking.get(i).setCgstvalue(BigDecimal.valueOf(cgstValue));

                    boolean isUnionTerritory = activity.mOrderSupportUtil.isDistrUnionTerritory(activity.mOrderSupportUtil.db, distStateCode);

                    //For union territory UTGST tax % value will come in sgst column in DB
                    sgstValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getSgst());

                    if (isUnionTerritory) {
                        activity.qtyUpdatedOrderBooking.get(i).setUtgstPerc(taxModel.getSgst());
                        activity.qtyUpdatedOrderBooking.get(i).setUtgstValue(BigDecimal.valueOf(sgstValue));
                    } else {
                        activity.qtyUpdatedOrderBooking.get(i).setSgstPerc(taxModel.getSgst());
                        activity.qtyUpdatedOrderBooking.get(i).setSgstValue(BigDecimal.valueOf(sgstValue));
                    }

                } else {

                    igstValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getIgst());
                    activity.qtyUpdatedOrderBooking.get(i).setIgstPerc(taxModel.getIgst());
                    activity.qtyUpdatedOrderBooking.get(i).setIgstvalue(BigDecimal.valueOf(igstValue));
                }

                // If Distr is from Kerala and Retailer is active set cess tax
                if (isCessTaxApplicable) {
                    cessValue = activity.mOrderSupportUtil.calculateTaxValue(calcualteOrderValue, taxModel.getCess());
                    activity.qtyUpdatedOrderBooking.get(i).setCessPerc(taxModel.getCess());
                    activity.qtyUpdatedOrderBooking.get(i).setCessValue(BigDecimal.valueOf(cessValue));
                }
            }

            /*for (TaxMasterModel taxModel : taxModelList) {
                activity.qtyUpdatedOrderBooking.get(i).setNetAmount(BigDecimal.valueOf(taxableAmt));
                if (taxModel.getTaxName().equalsIgnoreCase(TAX_CGST)) {
                    cgstValue1 = activity.mOrderSupportUtil.calculateTaxValue(taxableAmt, taxModel.getOutputTaxPerc());
                    activity.qtyUpdatedOrderBooking.get(i).setCgstperc(taxModel.getOutputTaxPerc());
                    activity.qtyUpdatedOrderBooking.get(i).setCgstvalue(BigDecimal.valueOf(cgstValue1));
                }
                if (taxModel.getTaxName().equalsIgnoreCase(TAX_SGST)) {
                    sgstValue1 = activity.mOrderSupportUtil.calculateTaxValue(taxableAmt, taxModel.getOutputTaxPerc());
                    activity.qtyUpdatedOrderBooking.get(i).setSgstPerc(taxModel.getOutputTaxPerc());
                    activity.qtyUpdatedOrderBooking.get(i).setSgstValue(BigDecimal.valueOf(sgstValue1));
                }
                if (taxModel.getTaxName().equalsIgnoreCase(TAX_UTGST)) {
                    utgstValue1 = activity.mOrderSupportUtil.calculateTaxValue(taxableAmt, taxModel.getOutputTaxPerc());
                    activity.qtyUpdatedOrderBooking.get(i).setUtgstPerc(taxModel.getOutputTaxPerc());
                    activity.qtyUpdatedOrderBooking.get(i).setUtgstValue(BigDecimal.valueOf(utgstValue1));
                }
                if (taxModel.getTaxName().equalsIgnoreCase(TAX_IGST)) {
                    igstValue1 = activity.mOrderSupportUtil.calculateTaxValue(taxableAmt, taxModel.getOutputTaxPerc());
                    activity.qtyUpdatedOrderBooking.get(i).setIgstPerc(taxModel.getOutputTaxPerc());
                    activity.qtyUpdatedOrderBooking.get(i).setIgstvalue(BigDecimal.valueOf(igstValue1));
                }
            }*/

            activity.qtyUpdatedOrderBooking.get(i).setTotalAmount(BigDecimal.valueOf(calcualteOrderValue + cgstValue + sgstValue + cessValue + igstValue));
        }
    }

    public static class AsyncLoadData extends AsyncTask<Void, Void, Boolean> {

        private final OrderBookingFragment activity;
        List<EditQtyModel> vsSevices = new ArrayList<>();
        boolean boolResponse = false;
        private WeakReference<OrderBookingFragment> activityReference;

        public AsyncLoadData(OrderBookingFragment context) {
            activityReference = new WeakReference<>(context);
            activity = activityReference.get();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                activity.orderBookingVOList = new ArrayList<>();
                activity.mustSellProduct = new ArrayList<>();
                activity.focusBrandProduct = new ArrayList<>();

                activity.orderNoList = activity.db.fetchAllOrderNos();

                activity.orderBookingVOList = activity.db.fetchAllProducts(activity.preferences.readString(PREF_DISTRCODE),
                        activity.preferences.readString(PREF_SALESMANCODE), activity.preferences.readString(PREF_ROUTECODE),
                        "");

                loadPreviousOrderProducts();

                if (!activity.orderBookingVOList.isEmpty()) {
//                    activity.mustSellProduct = activity.db.getMustsellProduuct(activity.retailerCode, masterTableName);

//                    activity.focusBrandProduct = activity.db.getFocusBrandProducts(activity.retailerCode, masterTableName);
//
                    activity.schemeProductDetail = activity.db.getRetailerSchemeProducts();
//
//                    activity.mustSellBilledProduct = activity.db.getAllMustSellBilledProducts(activity.preferences.readString(PREF_CMP_CODE), activity.preferences.readString(PREF_DISTRCODE), activity.retailerCode);
//
//                    activity.top10Products = activity.db.getTop10SKUList(activity.preferences.readString(PREF_CMP_CODE), activity.distStateCode);
                    activity.orderBookingVOList = activity.mOrderSupportUtil.mapFilteredProducts(activity.orderBookingVOList, activity.mustSellProduct, activity.mustSellBilledProduct, activity.focusBrandProduct, activity.schemeProductDetail, activity.top10Products);
//
//                    loadMustSellQtyProducts(activity.orderBookingVOList);

                    for (OrderBookingVO orderBookingVO : activity.orderBookingVOList) {
                        List<SchemeModel> schemeModelList = new ArrayList<>();
                        for (SchemeModel schemeModel : activity.schemeProductDetail) {
                            if (orderBookingVO.getProdCode().equalsIgnoreCase(schemeModel.getProductCode())) {
                                schemeModelList.add(schemeModel);
                            }
                        }
                        orderBookingVO.setSchemeModelList(schemeModelList);
                    }

                    for (OrderBookingVO orderBookingVO : activity.orderBookingVOList) {

                        String prodType = "";
                        if (orderBookingVO.getFocusCategory().equalsIgnoreCase(FOCUS_BRAND)
                                && orderBookingVO.getMustcategory().equalsIgnoreCase(MUST_SELL)
                                && orderBookingVO.getProductStatus().equalsIgnoreCase(PROMO)) {
                            prodType = "F,M,S";
                            orderBookingVO.setProdType(prodType);

                        } else if (orderBookingVO.getFocusCategory().equalsIgnoreCase(FOCUS_BRAND)
                                && orderBookingVO.getProductStatus().equalsIgnoreCase(PROMO)) {
                            prodType = "F,S";
                            orderBookingVO.setProdType(prodType);

                        } else if (orderBookingVO.getMustcategory().equalsIgnoreCase(MUST_SELL)
                                && orderBookingVO.getProductStatus().equalsIgnoreCase(PROMO)) {
                            prodType = "M,S";
                            orderBookingVO.setProdType(prodType);

                        } else if (orderBookingVO.getProductStatus().equalsIgnoreCase(PROMO)) {
                            prodType = "S";
                            orderBookingVO.setProdType(prodType);

                        } else {
                            prodType = "N";
                            orderBookingVO.setProdType(prodType);
                        }

                        if (!orderBookingVO.getSchemeModelList().isEmpty()) {
                            List<SchemeModel> schemeslablist = activity.db.getSchemeSlabDetails(orderBookingVO.getSchemeModelList().get(0).getSchemeCode());
                            orderBookingVO.setSchemeslablist(schemeslablist);
                        }
                    }
                }
                sequencingArrayList();
                sequencingQuantityArrayList();

                Collections.sort(activity.orderBookingVOList, activity.new SortByMustSellFocus());
                loadPreviousOrderQtyStock();


            } catch (Exception e) {
                Log.e(TAG, "doInBackground: " + e.getMessage(), e);
            }
            return boolResponse;
        }

        private void loadPreviousOrderQtyStock() {
            // capture previous order qty and stocks
            if (activity.orderBookingVOList.get(0).getOrderInvoiceNo() != null &&
                    !activity.orderBookingVOList.get(0).getOrderInvoiceNo().isEmpty()) {
                vsSevices = activity.db.fetchPreviousOrderQtyDetails(
                        activity.preferences.readString(PREF_DISTRCODE),
                        activity.preferences.readString(PREF_SALESMANCODE),
                        "",
                        activity.orderBookingVOList.get(0).getOrderInvoiceNo());

                Globals.getOurInstance().setPreviousOrderQty(this.vsSevices);
            }
        }

        /**
         * Set the sequencing based on quantity
         */
        private void sequencingQuantityArrayList() {

            for (int i = 0; i < activity.orderBookingVOList.size(); i++) {
                if (activity.orderBookingVOList.get(i).getQuantity() > 0) {
                    activity.orderBookingVOList.get(i).setMustSellFocusSort(0);
                }
            }
        }

        private void sequencingArrayList() {
            String configValue = activity.db.getConfigDataBasedOnName(CONFIG_PRODUCT_PRIORITY);
            if (configValue != null && !configValue.isEmpty()) {
                final String[] arrayOfString = configValue.trim().split(",");
                int k = 0;
                for (String anArrayOfString : arrayOfString) {
                    k++;
                    for (int i = 0; i < activity.orderBookingVOList.size(); i++) {
                        if (anArrayOfString.trim().equalsIgnoreCase(activity.orderBookingVOList.get(i).getProductStatus())
                                && activity.orderBookingVOList.get(i).getMustSellFocusSort() == 0) {
                            activity.orderBookingVOList.get(i).setMustSellFocusSort(k);

                        } else if (anArrayOfString.trim().equalsIgnoreCase(activity.orderBookingVOList.get(i).getMustcategory())
                                && activity.orderBookingVOList.get(i).getMustSellFocusSort() == 0) {
                            activity.orderBookingVOList.get(i).setMustSellFocusSort(k);

                        } else if (anArrayOfString.trim().equalsIgnoreCase(activity.orderBookingVOList.get(i).getFocusCategory())
                                && activity.orderBookingVOList.get(i).getMustSellFocusSort() == 0) {
                            activity.orderBookingVOList.get(i).setMustSellFocusSort(k);

                        } else if (anArrayOfString.trim().equalsIgnoreCase(activity.orderBookingVOList.get(i).getTop10ProductCategory())
                                && activity.orderBookingVOList.get(i).getMustSellFocusSort() == 0) {
                            activity.orderBookingVOList.get(i).setMustSellFocusSort(k);

                        } else if (anArrayOfString.trim().equalsIgnoreCase("Others")
                                && activity.orderBookingVOList.get(i).getMustSellFocusSort() == 0) {
                            activity.orderBookingVOList.get(i).setMustSellFocusSort(k);

                        }
                    }
                }
            }
        }

        private void loadMustSellQtyProducts(List<OrderBookingVO> orderBookingVOList) {
            try {

                for (OrderBookingVO bookingVO : orderBookingVOList) {

                    if (bookingVO.getQuantity() == 0 && bookingVO.getMustSellQty() > 0) {

                        double currentQty = bookingVO.getMustSellQty();
                        bookingVO.setQuantity(currentQty);
                        bookingVO.setTotQty(activity.mOrderSupportUtil.calcUomConversionQty(bookingVO, bookingVO.getBaseUOM(), currentQty));
                        // get the conversion factor and calculate with selling price and
                        // multiplied with current quantity
                        double orderValue = activity.mOrderSupportUtil.calculateLineOrderValue(bookingVO, bookingVO.getBaseUOM(), currentQty);

                        bookingVO.setOrderValue(BigDecimal.valueOf(orderValue));
                        bookingVO.setlGrossAmt(BigDecimal.valueOf(orderValue));
                        bookingVO.setPrimDiscOrderValue(BigDecimal.valueOf(activity.mOrderSupportUtil.getPrimDiscAppliedOrderValue(orderValue, bookingVO.getPrimaryDisc())));

                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "loadMustSellQtyProducts: " + e.getMessage(), e);
            }
        }

        private void loadPreviousOrderProducts() {
            try {
                if (activity.isPreviousOrder) {
                    activity.previousOrderList = activity.db.fetchPreviousOrderDetails(activity.preferences.readString(PREF_DISTRCODE),
                            activity.preferences.readString(PREF_SALESMANCODE), "");
                    for (OrderBookingVO allProdList : activity.orderBookingVOList) {
                        for (OrderBookingVO prevOrder : activity.previousOrderList) {

                            if (allProdList.getProdCode().equalsIgnoreCase(prevOrder.getProdCode())) {

                                allProdList.setQuantity(prevOrder.getQuantity());
                                allProdList.setUomId(prevOrder.getUomId());
                                double currentQty = prevOrder.getQuantity();
                                double orderValue = activity.mOrderSupportUtil.calculateLineOrderValue(allProdList, prevOrder.getUomId(), currentQty);
                                allProdList.setOrderValue(BigDecimal.valueOf(orderValue));
                                allProdList.setPrimDiscOrderValue(BigDecimal.valueOf(activity.mOrderSupportUtil.getPrimDiscAppliedOrderValue(orderValue, allProdList.getPrimaryDisc())));
                                allProdList.setTotQty(activity.mOrderSupportUtil.calcUomConversionQty(allProdList, prevOrder.getUomId(), currentQty));

                                List<OrderBookingVO> orderBooking = new ArrayList<>(activity.db.getUom(allProdList.getUomGroupId(), TABLE_UOM_MASTER));

                                activity.uomIdSpinner = activity.mOrderSupportUtil.getUomIdSpinnerList(orderBooking);
                            }

                        }
                    }
                    /* sort based on must sell focus brand */
                    Collections.sort(activity.orderBookingVOList, activity.new SortByQuantity());
                }
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: " + e.getMessage(), e);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.showProgressDialog(activity.getActivity(), activity.getResources().getString(R.string.MSG_LOADING));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            activity.cancleProgressDialog();
            activity.loadAdapterData();
            loadBrandsSpinner();
            Log.e("orderBookingVOList", "" + activity.orderBookingVOList.size());

        }

        private void loadBrandsSpinner() {
            ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(activity.getSFAFragmentActivity(), R.layout.spinner_list_item, activity.brandsList);
            brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            activity.mainBrandSpinner.setAdapter(brandAdapter);

            activity.mainBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                    activity.brandValue = activity.mainBrandSpinner.getSelectedItem().toString();
                    if (!activity.brandDataList.isEmpty() && pos - 1 >= 0)
                        activity.brandCode = activity.brandDataList.get(pos - 1).getBrandCode();
                    activity.categoryValue = ALL_CATEGORY;

                    activity.setApplyFilter();
                    activity.isFilterDialogShowing = false;
                    activity.clearTempFilterFields();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //default implementation ignored
                }
            });
        }
    }

    private class SortByQuantity implements Comparator<OrderBookingVO> {
        @Override
        public int compare(OrderBookingVO o1, OrderBookingVO o2) {
            return (int) o2.getQuantity() - (int) o1.getQuantity();
        }
    }

    /* sort based on must sell focus brand */
    private class SortByMustSellFocus implements Comparator<OrderBookingVO> {
        @Override
        public int compare(OrderBookingVO o2, OrderBookingVO o1) {
            return o2.getMustSellFocusSort() - o1.getMustSellFocusSort();
        }
    }

}
