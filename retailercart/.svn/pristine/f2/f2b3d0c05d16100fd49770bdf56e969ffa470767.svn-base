/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.ProductFilterAdapter;
import com.botree.retailerssfa.adapters.QuickOrderBookingAdapter;
import com.botree.retailerssfa.adapters.SchemePagerAdapter;
import com.botree.retailerssfa.base.SFAFragment;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.fragmentmanager.ESFAFragTags;
import com.botree.retailerssfa.fragmentmanager.SFAFragmentManager;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.ProFilterModel;
import com.botree.retailerssfa.models.SchemeDistrBudgetModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.scheme.SchemeEngin;
import com.botree.retailerssfa.support.BasicOnKeyboardActionListener;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_ENABLE_QTY_2;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_ORDER_PRODUCT_FILTERS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCT_UOM_MASTER;
import static com.botree.retailerssfa.support.Globals.NAME_ORDER_BOOKING;
import static com.botree.retailerssfa.support.Globals.ORDER_BOOKING_QUANTITY_DIALOG;
import static com.botree.retailerssfa.support.Globals.ORDER_BOOKING_STOCK_DIALOG;
import static com.botree.retailerssfa.util.OrderSupportUtil.ALL_BRANDS;
import static com.botree.retailerssfa.util.OrderSupportUtil.ALL_CATEGORY;
import static com.botree.retailerssfa.util.OrderSupportUtil.BRAND_TEXT;
import static com.botree.retailerssfa.util.OrderSupportUtil.CATEGORY_TEXT;
import static com.botree.retailerssfa.util.OrderSupportUtil.ENTERED_QTY;
import static com.botree.retailerssfa.util.OrderSupportUtil.ENTERED_REMARKS;
import static com.botree.retailerssfa.util.OrderSupportUtil.ENTERED_UOM;
import static com.botree.retailerssfa.util.OrderSupportUtil.IS_FILTER_DIALOG_SHOWING;
import static com.botree.retailerssfa.util.OrderSupportUtil.IS_QTY_DIALOG_SHOWING;
import static com.botree.retailerssfa.util.OrderSupportUtil.IS_REMARKS_FOCUSED;
import static com.botree.retailerssfa.util.OrderSupportUtil.IS_SCHEME_DIALOG_SHOWING;
import static com.botree.retailerssfa.util.OrderSupportUtil.ORDER_BOOKING_LIST;
import static com.botree.retailerssfa.util.OrderSupportUtil.QTY_LIST;
import static com.botree.retailerssfa.util.OrderSupportUtil.QTY_POSITION;
import static com.botree.retailerssfa.util.OrderSupportUtil.SCHEME_POSITION;
import static com.botree.retailerssfa.util.OrderSupportUtil.SEARCH_LIST;
import static com.botree.retailerssfa.util.OrderSupportUtil.SEARCH_TEXT;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_CMP_CODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_DISTRCODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LAST_LAT;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_LAST_LONG;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_PRODUCT_SEARCH_LEVEL_NAME;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_QUICK_ACTION;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_ROUTECODE;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_SALESMANCODE;

/**
 * Order Booking Fragment contains {@link RecyclerView} with {@link List}
 * and Inflated layout is fragment_order_booking.
 */
public class QuickOrderBookingFragment extends SFAFragment implements View.OnClickListener,
        SFALocationUpdate {

    private static final String TAG = OrderBookingFragment.class.getSimpleName();
    private static final String SIH = "SIH : ";
    private static final String SOQ = "SOQ : ";
    private OrderSupportUtil mOrderSupportUtil;
    private KeyboardView mKeyboardView;
    private TextView schemeDetailTxt;
    private TextView stockInHandTxt;
    private TextView soqTxt;
    private TextView totPriceTxt;
    private List<String> brandsList;
    private Dialog pd;
    private String invoiceNo = null;
    private QuickOrderBookingAdapter quickOrderBookingAdapter;
    private RecyclerView recyclerView;
    private TextView emptyText;
    private TextView orderCountTxt;
    private TextView orderTotalTxt;
    private SFADatabase db;
    private SFASharedPref preferences;
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
    private String brandValue = "";
    private String categoryValue = "";
    private String tempCategoryValue = "";
    private String retailerCode;
    private String retailerName;
    private List<String> uomIdSpinner;
    private CoordinatorLayout orderBookingLayout;
    private ArrayList<OrderBookingVO> qtyUpdatedOrderBooking;
    private List<SchemeDistrBudgetModel> schemeBudgetList = new ArrayList<>();
    private List<OrderBookingVO> orderBookingVOList = new ArrayList<>();
    private List<SchemeModel> schemeProductDetail = new ArrayList<>();
    private List<String> categoryList;
    AdapterView.OnItemSelectedListener categorySpinnerListener = new AdapterView.OnItemSelectedListener() {
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
    private String enteredQkOrdBkQty = "";
    private String enteredRemarks = "";
    private int enteredUOM = -1;
    private String searchText = "";
    private QuickOrderBookingFragment.ZeroResult zeroNonZeroResult = ZeroResult.ALL_PRODUCTS;
    private ZeroResult tempZeroNonZeroResult = null;
    private RadioGroup.OnCheckedChangeListener zeroRadioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            assignZeroGroupValue(checkedId, false);
        }
    };
    private String distStateCode;
    private String retailerStateCode;
    private boolean isPreviousOrder = false;
    private SchemeEngin schemeEngin;
    private List<OrderBookingVO> brandDataList = new ArrayList<>();
    private String brandCode = "";
    private NotifyUtil.NotifyFilterListener filterDialogListener = new NotifyUtil.NotifyFilterListener() {


        @Override
        public void onfilterOkClicked(int pos, String brandVal) {

            brandValue = brandVal;
            if (!brandDataList.isEmpty() && pos - 1 >= 0)
                brandCode = brandDataList.get(pos - 1).getBrandCode();

            categoryValue = "All Category";
            // set the filter values to order booking adapter and update the recycler list
            if (quickOrderBookingAdapter != null)
                quickOrderBookingAdapter.applyFilter(zeroNonZeroResult, brandValue, brandCode, categoryValue);

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
            if (quickOrderBookingAdapter != null)
                quickOrderBookingAdapter.applyFilter(zeroNonZeroResult, brandValue, brandCode, categoryValue);

            isFilterDialogShowing = false;
            clearTempFilterFields();

        }

        @Override
        public void onFilterCancelClicked() {
            //ignored
        }
    };
    private BottomSheetDialog filterDialog;
    private SearchView searchView;
    private String uomMasterTable = TABLE_PRODUCT_UOM_MASTER;
    private String productFilterTable = TABLE_ORDER_PRODUCT_FILTERS;
    private int outOfStockPos = 0;
    private int weightCheckPos = 0;
    private Menu mTopMenu;
    private String suffixYy;
    private int codeValue;
    private String invoiceType = "";
    boolean isNew;
    private String salesmanCode;
    private String routeCode;
    private String customerShipCode;
    private String invoiceDate;
    String enableQty2 = "";

    public QuickOrderBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        db = SFADatabase.getInstance(getActivity());
        preferences = SFASharedPref.getOurInstance();
        mOrderSupportUtil = OrderSupportUtil.getInstance();
        schemeEngin = new SchemeEngin(getActivity());
        enableQty2 = db.getConfigDataBasedOnName(CONFIG_ENABLE_QTY_2);

        if (getArguments() != null) {

            isNew = getArguments().getBoolean("IS_NEW");
            retailerCode = getArguments().getString("retailerCode");
            retailerName = getArguments().getString("retailerName");
            routeCode = getArguments().getString("routeCode");
            salesmanCode = getArguments().getString("salesmanCode");
            invoiceNo = getArguments().getString("invoiceNo");
            customerShipCode = getArguments().getString("customerShipCode");
            invoiceDate = getArguments().getString("invoiceDate");
            suffixYy = getArguments().getString("suffixYy");
            codeValue = getArguments().getInt("codeValue");
            invoiceType = getArguments().getString("invoiceType");

            preferences.writeString(PREF_ROUTECODE, routeCode);
            preferences.writeString(PREF_SALESMANCODE, salesmanCode);
        }
        if (qtyUpdatedOrderBooking == null)
            qtyUpdatedOrderBooking = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_custom_orderbooking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAutoScreenCount(Globals.NAME_ORDER_BOOKING);

        initialize(view);
        processSavedInstanceState(savedInstanceState);

        if (qtyUpdatedOrderBooking == null)
            qtyUpdatedOrderBooking = new ArrayList<>();

        ((MainActivity) getSFAFragmentActivity()).hideBottomNevigation();

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
            orderBookingVOList = (ArrayList<OrderBookingVO>) savedInstanceState.getSerializable("orderlist");

            savedState1(savedInstanceState);

            savedStateForOrderList(savedInstanceState);

            savedState2(savedInstanceState);

            loadAdapterData();

        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        new AsyncLoadData(QuickOrderBookingFragment.this).execute();

                    } catch (Exception e) {
                        if (Debug.isDebuggerConnected()) {
                            Log.e(TAG, "run: " + e.getMessage(), e);
                        }
                    }

                }
            }, 100);

        }

        if (quickOrderBookingAdapter != null)
            quickOrderBookingAdapter.applyFilter(zeroNonZeroResult, brandValue, brandCode, categoryValue);

        else if (isSchemeDialogShowing && schemePosition != -1)
            showSchemeDetail(schemePosition);

        else if (isFilterDialogShowing)
            filterProducts();
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

    private void savedStateForOrderList(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(QTY_LIST))
            qtyUpdatedOrderBooking = savedInstanceState.getParcelableArrayList(QTY_LIST);

        if (savedInstanceState.containsKey(ORDER_BOOKING_LIST))
            orderBookingVOList = savedInstanceState.getParcelableArrayList(ORDER_BOOKING_LIST);

        List<OrderBookingVO> searchList = new ArrayList<>();

        if (savedInstanceState.containsKey(SEARCH_LIST))
            searchList = savedInstanceState.getParcelableArrayList(SEARCH_LIST);

        if (orderBookingVOList != null && searchList != null) {
            quickOrderBookingAdapter.updateList(orderBookingVOList, searchList);
            quickOrderBookingAdapter.notifyDataSetChanged();
        }
    }

    private void savedState1(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(IS_QTY_DIALOG_SHOWING))
            isQtyDialogShowing = savedInstanceState.getBoolean(IS_QTY_DIALOG_SHOWING, false);
        if (savedInstanceState.containsKey(QTY_POSITION))
            qtyPosition = savedInstanceState.getInt(QTY_POSITION, -1);

        if (savedInstanceState.containsKey(ENTERED_QTY))
            enteredQkOrdBkQty = savedInstanceState.getString(ENTERED_QTY, "0");

        if (savedInstanceState.containsKey(ENTERED_REMARKS))
            enteredRemarks = savedInstanceState.getString(ENTERED_REMARKS, "");

        if (savedInstanceState.containsKey(IS_REMARKS_FOCUSED))
            isRemarksFocused = savedInstanceState.getBoolean(IS_REMARKS_FOCUSED, false);

        if (savedInstanceState.containsKey(ENTERED_UOM))
            enteredUOM = savedInstanceState.getInt(ENTERED_UOM);
    }

    /**
     * @param view Initializing the all views for this fragment is get from this.
     */
    private void initialize(View view) {

        Keyboard mKeyboard = new Keyboard(getSFAFragmentActivity(), R.xml.keyboard);
        mKeyboardView = view.findViewById(R.id.keyboardview);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(new BasicOnKeyboardActionListener(getSFAFragmentActivity()));

        stockInHandTxt = view.findViewById(R.id.stockin_hand_txt);
        soqTxt = view.findViewById(R.id.soq_txt);
        totPriceTxt = view.findViewById(R.id.prod_price_tot_txt);
        schemeDetailTxt = view.findViewById(R.id.scheme_prod_detail_txt);

        orderBookingLayout = view.findViewById(R.id.orderbooking_layout);

        emptyText = view.findViewById(R.id.orderbooking_empty_tag_txt);
        orderCountTxt = view.findViewById(R.id.order_count_txt);
        orderTotalTxt = view.findViewById(R.id.order_total_txt);
        recyclerView = view.findViewById(R.id.orderbooking_recyclerview);
        Button nextBtn = view.findViewById(R.id.orderbooking_submit_btn);
        Button brandFilterBtn = view.findViewById(R.id.brand_filter_btn);
        Button otherFilterBtn = view.findViewById(R.id.other_filter_btn);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        Globals.getOurInstance().setRetailerStartTime(DateUtil.getCurrentDateAndTime());

        nextBtn.setOnClickListener(this);
        brandFilterBtn.setOnClickListener(this);
        otherFilterBtn.setOnClickListener(this);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Scrolling up
                stockInHandTxt.setText("");
                soqTxt.setText("");
                totPriceTxt.setText("");
                schemeDetailTxt.setText("");

            }
        });

        if ("N".equalsIgnoreCase(db.getConfigDataBasedOnName(AppConstant.Configurations.CONFIG_SIH))) {
            stockInHandTxt.setVisibility(View.GONE);
        }
        if ("N".equalsIgnoreCase(db.getConfigDataBasedOnName(AppConstant.Configurations.CONFIG_SOQ))) {
            soqTxt.setVisibility(View.GONE);
        }

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
            recyclerView.setAdapter(quickOrderBookingAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        }

        updateListCount(mTopMenu);

        recyclerView.smoothScrollToPosition(0);
        quickOrderBookingAdapter.setOnItemClickListener(new QuickOrderBookingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, double qty, double qty2, String uomId, String uomId2, Integer edtTxtType, String dbValue, String dbPerc) {
                loadQuantitySchemeData(position, qty, qty2, uomId, uomId2, edtTxtType, dbValue, dbPerc);
            }
        });
        quickOrderBookingAdapter.setmOnFocusListener(new QuickOrderBookingAdapter.OnFocusListener() {
            @Override
            public void onFocus(int position) {
                clearSchemeData();
            }
        });

        quickOrderBookingAdapter.setOnItemLongClickListener(new QuickOrderBookingAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(View view, int position) {
                schemePosition = position;
                isSchemeDialogShowing = true;
                showSchemeDetail(position);
                return true;
            }
        });

    }

    private void clearSchemeData() {
        schemeDetailTxt.setText("");
        stockInHandTxt.setText(String.valueOf(SIH + 0));
        soqTxt.setText(String.valueOf(SOQ + 0));
        totPriceTxt.setText(String.valueOf("00"));
    }

    private void loadQuantitySchemeData(int position, double qty, double qty2, String uomId, String uomId2, Integer edtTxtType,
                                        String dbValue, String dbPerc) {

        final OrderBookingVO orderBookingVO = orderBookingVOList.get(position);
        setDataToTextView(orderBookingVO);

        if (qty > 0) {
            uomIdSpinner = new ArrayList<>();
            assert orderBookingVO != null;
            List<OrderBookingVO> orderBooking = db.getUom(orderBookingVO.getUomGroupId(), uomMasterTable);
            for (int i = 0; i < orderBooking.size(); i++) {
                uomIdSpinner.add(orderBooking.get(i).getUomGroupId());
            }

            try {
                if (edtTxtType == ORDER_BOOKING_QUANTITY_DIALOG) {
                    showSchemeMsg(qty, qty2, schemeDetailTxt, orderBookingVO, uomId, uomId2, totPriceTxt);
//                    mOrderSupportUtil.qunatityDailogClose(qty, qty2, orderBookingVO, uomId, uomId2, "", invoiceType,
//                            "0", dbValue, dbPerc, qtyUpdatedOrderBooking);
                    mOrderSupportUtil.calculateOrderTotal(orderBookingVOList, orderCountTxt, orderTotalTxt, isPreviousOrder);
                } else if (edtTxtType.equals(ORDER_BOOKING_STOCK_DIALOG)) {
                    qunatityDailogClose(qty, orderBookingVO, uomId);
                }
            } catch (Exception e) {
                Log.e(TAG, "loadQuantitySchemeData: " + e.getMessage(), e);
            }
        } else {
            schemeDetailTxt.setText("");
            totPriceTxt.setText(String.valueOf("00"));
        }
    }

    private void setDataToTextView(OrderBookingVO orderBookingVO) {
        if (orderBookingVO != null && !orderBookingVO.getProdCode().isEmpty()) {
            stockInHandTxt.setText(String.valueOf(SIH + orderBookingVO.getStockInHand()));
            soqTxt.setText(String.valueOf(SOQ + orderBookingVO.getSuggestedQty()));
        } else {
            schemeDetailTxt.setText("");
            stockInHandTxt.setText(String.valueOf(SIH + 0));
            soqTxt.setText(String.valueOf(SOQ + 0));
            totPriceTxt.setText(String.valueOf("00"));
        }
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

        final Dialog dialog = new Dialog(getSFAFragmentActivity(), R.style.ThemeDialogCustom);
        if (dialog.isShowing()) return;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.scheme_description_dialog);

        final TextView popupProductFullName = dialog.findViewById(R.id.popup_productFullName);
        final TextView popupDesc = dialog.findViewById(R.id.popup_desc);

        popupProductFullName.setText(orderBookingVOList.get(schemePosition).getProdName());

        final ViewPager pager = dialog.findViewById(R.id.schemeviewpager);
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
            Log.e(TAG, "showSchemeDetail: " + e.getMessage(), e);
        }

        if (!schemeProductDetail.isEmpty()) {
            pager.setVisibility(View.VISIBLE);
            popupDesc.setVisibility(View.GONE);
        } else {
            pager.setVisibility(View.GONE);
            popupDesc.setVisibility(View.VISIBLE);
            popupDesc.setText(desc);
        }
        Button button = dialog.findViewById(R.id.popup_ok);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isSchemeDialogShowing = false;
                schemePosition = -1;
            }
        });

        dialog.show();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.quick_order_menu, menu);

        mTopMenu = menu;

        MenuItem favorite = menu.findItem(R.id.quick_favorite);
        getDefaultTrue(favorite);
        favorite.setIcon(db.isFavoriteMenu(Globals.NAME_ORDER_BOOKING) ? R.drawable.ic_favorite_star_fill : R.drawable.ic_favorite_star);
        MenuItem filter = menu.findItem(R.id.quick_product_filter);
        filter.setVisible(false);
        MenuItem item = menu.findItem(R.id.quick_product_search);

        updateListCount(menu);

        searchView = (SearchView) item.getActionView();
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
    }

    public void updateListCount(Menu menu) {

        if (menu != null) {
            MenuItem countItem = menu.findItem(R.id.quick_ord_all_prod_count);
            countItem.setActionView(R.layout.coverage_msg);
            View view = countItem.getActionView();
            TextView msg = view.findViewById(R.id.coverage_txt);
            if (quickOrderBookingAdapter != null && quickOrderBookingAdapter.getItemCount() > 0)
                msg.setText(String.valueOf(quickOrderBookingAdapter.getItemCount()));
            else
                msg.setText(String.valueOf("00"));
        }
    }


    private void getDefaultTrue(MenuItem favorite) {
        favorite.setVisible(!preferences.readBooleanDefaultTrue(PREF_QUICK_ACTION));
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSFAFragmentActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.quick_product_filter) {
            isFilterDialogShowing = true;
            filterProducts();

        } else if (menuItem.getItemId() == R.id.quick_product_voice_search) {
            isFilterDialogShowing = true;
            hideSoftKeyboard(orderBookingLayout);
            quickOrderBookingAdapter.applySearchFilter(searchText);

        } else if (menuItem.getItemId() == R.id.quick_favorite) {
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
            outState.putString(ENTERED_QTY, enteredQkOrdBkQty);
            outState.putString(ENTERED_REMARKS, enteredRemarks);
            outState.putBoolean(IS_REMARKS_FOCUSED, isRemarksFocused);
            outState.putInt(ENTERED_UOM, enteredUOM);
        }
        if (qtyUpdatedOrderBooking != null && !qtyUpdatedOrderBooking.isEmpty()) {
            outState.putParcelableArrayList(QTY_LIST, qtyUpdatedOrderBooking);
            outState.putParcelableArrayList(ORDER_BOOKING_LIST, (ArrayList<? extends Parcelable>) orderBookingVOList);
            outState.putParcelableArrayList(SEARCH_LIST, (ArrayList<? extends Parcelable>) quickOrderBookingAdapter.getSearchList());
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

        super.onSaveInstanceState(outState);

    }


    private void showSchemeMsg(double currentQty, double currentQty2, TextView tvSlab, OrderBookingVO orderBookingVO, String uomId, String uomId2, TextView lineTot) {
        String offerMsg = "";
        if (tvSlab != null) {
            tvSlab.setVisibility(View.GONE);
            tvSlab.setText(offerMsg);
        }

        try {
            double orderValue = mOrderSupportUtil.calculateLineOrderValue(orderBookingVO, uomId, currentQty);
            if (!uomId2.isEmpty())
                orderValue = orderValue + mOrderSupportUtil.calculateLineOrderValue(orderBookingVO, uomId2, currentQty2);

            List<SchemeModel> mAppliedSlablist = new ArrayList<>();
            List<SchemeModel> mAppliedSlablist1 = mOrderSupportUtil.getAppliedSlabs(currentQty, orderBookingVO, uomId, schemeEngin, uomIdSpinner, schemeBudgetList, schemeProductDetail);
            List<SchemeModel> mAppliedSlablist2 = mOrderSupportUtil.getAppliedSlabs(currentQty2, orderBookingVO, uomId2, schemeEngin, uomIdSpinner, schemeBudgetList, schemeProductDetail);

            mAppliedSlablist.addAll(mAppliedSlablist1);
            mAppliedSlablist.addAll(mAppliedSlablist2);

            offerMsg = schemeEngin.showSchemeDetailInText(mAppliedSlablist);
            if (!offerMsg.isEmpty() && tvSlab != null) {
                tvSlab.setVisibility(View.VISIBLE);
                tvSlab.setText(offerMsg);
            }

            lineTot.setText(String.format(Locale.getDefault(), "%.2f", orderValue));
        } catch (Exception e) {
            Log.e(TAG, "showSchemeMsg: " + e.getMessage(), e);
        }


    }

    private void qunatityDailogClose(double editQty, OrderBookingVO orderBookingVO, String uomSpinnerItem) {

            orderBookingVO.setStockConversionFactor(mOrderSupportUtil.calcUomConversionQty(orderBookingVO, uomSpinnerItem, editQty));
            orderBookingVO.setStockCheckQty(editQty);
            orderBookingVO.setStockOrderValue(BigDecimal.valueOf(mOrderSupportUtil.calculateLineOrderValue(orderBookingVO, uomSpinnerItem, editQty)));
            if (editQty > 0) {
                orderBookingVO.setItemSelected(true);
                orderBookingVO.setCheckBoxEnabled(false);
            } else {
                orderBookingVO.setItemSelected(false);
                orderBookingVO.setCheckBoxEnabled(true);
            }
            orderBookingVO.setStockUomId(uomSpinnerItem);

        if (qtyUpdatedOrderBooking != null && qtyUpdatedOrderBooking.contains(orderBookingVO)) {
            qtyUpdatedOrderBooking.remove(orderBookingVO);
        }


        if (qtyUpdatedOrderBooking != null) {
            qtyUpdatedOrderBooking.add(orderBookingVO);
        }
        mOrderSupportUtil.calculateOrderTotal(orderBookingVOList, orderCountTxt, orderTotalTxt, isPreviousOrder);

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
     * Init the filter products dialog
     * Restore the data if previously selected values
     * or temp variable values for handling screen orientation.
     * <p>
     * At the time pressing submit the values are stored into
     * variable and filter values are set to order booking adapter
     */
    private void filterProducts() {
        final Dialog dialog = new Dialog(getSFAFragmentActivity(), R.style.ThemeDialogCustom);

        if (dialog.isShowing()) return;

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.filter_dialog);
        final Button submit = dialog.findViewById(R.id.filter_submit_btn);
        final Button cancel = dialog.findViewById(R.id.filter_cancel_btn);
        final RadioGroup zeroNonZeroRadioGroup = dialog.findViewById(R.id.zero_non_zero_radio_group);
        final Spinner brandSpinner = dialog.findViewById(R.id.spinner_brand);
        final Spinner categorySpinner = dialog.findViewById(R.id.spinner_category);

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

                dialog.dismiss();
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

                // set the filter values to order booking adapter and update the recycler list
                if (quickOrderBookingAdapter != null)
                    quickOrderBookingAdapter.applyFilter(zeroNonZeroResult, brandValue, brandCode, categoryValue);
                dialog.dismiss();
                isFilterDialogShowing = false;
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.orderbooking_submit_btn:

                if (Double.parseDouble(orderTotalTxt.getText().toString()) == 0) {
                    NotifyUtil.showSnackBar(getSFAFragmentActivity(), orderBookingLayout,
                            getString(R.string.err_no_valid_order), Snackbar.LENGTH_LONG);
                } else {
                    new AsyncInsertData(QuickOrderBookingFragment.this).execute();


                }
                break;

            case R.id.brand_filter_btn:

                showBrandFilterDialog();

                break;
            case R.id.other_filter_btn:

                showCategoryFilterDialog();
                break;

            default:
                break;
        }
    }

    public void showCategoryFilterDialog() {

        if (filterDialog != null && filterDialog.isShowing()) return;
        filterDialog = new BottomSheetDialog(getSFAFragmentActivity());
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.category_filter_dialog);
        filterDialog.setCancelable(false);
        final RadioGroup zeroNonZeroRadioGroup = filterDialog.findViewById(R.id.zero_non_zero_radio_group);
        ImageView cancelBtn = filterDialog.findViewById(R.id.cat_filter_cancel_btn);

        final RecyclerView filterRecyclerView = filterDialog.findViewById(R.id.cat_filter_recyclerview);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        filterRecyclerView.setLayoutManager(manager);

        setZeroNonZeroProductRG(zeroNonZeroRadioGroup);
        zeroNonZeroRadioGroup.setOnCheckedChangeListener(zeroRadioGroupListener);

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
                    Globals.getOurInstance().setOtherFilterPso(pos);

                    filterDialog.dismiss();
                    assignZeroGroupValue(zeroNonZeroRadioGroup.getCheckedRadioButtonId(), true);
                    // set the filter values to order booking adapter and update the recycler list
                    if (quickOrderBookingAdapter != null)
                        quickOrderBookingAdapter.applyFilter(zeroNonZeroResult, brandValue, brandCode, categoryValue);
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


    private void showBrandFilterDialog() {
            NotifyUtil.getOurInstance().showBrandFilterDialog(getSFAFragmentActivity(), filterBrandDialogListener,
                    "Product Filter", productFilterTable, categoryValue);
    }

    @Override
    public void newLocation(Location newLocation) {
        // New Location is updated

        Globals.getOurInstance().setStrLatitude(String.valueOf(newLocation.getLatitude()));
        Globals.getOurInstance().setStrLongitude(String.valueOf(newLocation.getLongitude()));

        //Check if Lat and Lon is 0.0.
        if (Globals.getOurInstance().getStrLatitude().equals("0.0") && Globals.getOurInstance().getStrLongitude().equals("0.0")) {
            Globals.getOurInstance().setStrLatitude(null);
            Globals.getOurInstance().setStrLongitude(null);
        }

        if (newLocation.getLatitude() > 0.0 && newLocation.getLongitude() > 0.0) {
            preferences.writeString(PREF_LAST_LAT, String.valueOf(newLocation.getLatitude()));
            preferences.writeString(PREF_LAST_LONG, String.valueOf(newLocation.getLongitude()));
        }
    }

    private void loadAdapterData() {

        try {
            List<List<OrderBookingVO>> uomList = new ArrayList<>();
            if (orderBookingVOList != null && !orderBookingVOList.isEmpty()) {
                for (OrderBookingVO orderBookingVO : orderBookingVOList) {
                    uomList.add(db.getUom(orderBookingVO.getProdCode(), uomMasterTable));
                    orderBookingVO.setUomId(orderBookingVO.getBaseUOM());
//                    orderBookingVO.setUomId2(orderBookingVO.getBaseUOM());
                }
                quickOrderBookingAdapter = new QuickOrderBookingAdapter(getActivity(), orderBookingVOList,
                        uomList, Globals.ORDER_BOOKING, mKeyboardView, recyclerView);

            }

            if (qtyUpdatedOrderBooking != null) {
                qtyUpdatedOrderBooking.clear();
                qtyUpdatedOrderBooking.addAll(orderBookingVOList);
            }

            loadRecycleViewData();
            mOrderSupportUtil.calculateOrderTotal(orderBookingVOList, orderCountTxt, orderTotalTxt, isPreviousOrder);
        } catch (Exception e) {
            Log.e(TAG, "loadAdapterData: " + e.getMessage(), e);
        }

    }

    @Override
    public void onStop() {

        if (pd != null && pd.isShowing()) {
            pd.dismiss();
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

    private void showProgressDialog(Activity activity, String message) {
        if (pd != null && pd.isShowing()) return;
        pd = mOrderSupportUtil.createDialogUsingProgressBar(activity, message);
        if (pd != null && !pd.isShowing())
            pd.show();

    }

    public enum ZeroResult {
        ALL_PRODUCTS,
        ZERO_PRODUCTS,
        NON_ZERO_PRODUCTS
    }

    private static class AsyncInsertData extends AsyncTask<Void, Void, Boolean> {
        private final WeakReference<QuickOrderBookingFragment> activityReference;
        private final QuickOrderBookingFragment activity;
        private int scrollPosition = 0;

        private AsyncInsertData(QuickOrderBookingFragment context) {

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
//                if (!checkWeightError()) {
//                    return weightOrderError();
//                } else if (!checkStockAvail()) {
                    NotifyUtil.showSnackBar(activity.getSFAFragmentActivity(), activity.orderBookingLayout,
                            activity.qtyUpdatedOrderBooking.get(activity.outOfStockPos).getProdName() + activity.getString(R.string.mrp_msg)
                                    + activity.qtyUpdatedOrderBooking.get(activity.outOfStockPos).getMrp()
                                    + activity.getString(R.string.outofstock_msg), Toast.LENGTH_SHORT);
                    scrollPosition = activity.outOfStockPos;
                    return false;
//                } else {
//
//                    return OrderSupportUtil.storeBillingDetail(activity.qtyUpdatedOrderBooking, activity.mOrderSupportUtil, activity.schemeEngin,
//                            activity.db, activity.schemeProductDetail, activity.schemeBudgetList, activity.preferences, activity.isNew,
//                            activity.salesmanCode, activity.routeCode, activity.retailerCode, activity.invoiceNo, activity.distStateCode, activity.retailerStateCode);
//                }
        }

        private boolean weightOrderError() {
            NotifyUtil.showSnackBar(activity.getSFAFragmentActivity(), activity.orderBookingLayout,
                    activity.qtyUpdatedOrderBooking.get(activity.weightCheckPos).getProdName() + activity.getString(R.string.mrp_msg)
                            + activity.qtyUpdatedOrderBooking.get(activity.weightCheckPos).getMrp()
                            + activity.getString(R.string.wrong_weight) + activity.getString(R.string.error_weight), Toast.LENGTH_SHORT);
            scrollPosition = activity.orderBookingVOList.indexOf(activity.qtyUpdatedOrderBooking.get(activity.weightCheckPos));
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            cancleProgressDialog();

            if (Boolean.TRUE.equals(aBoolean)) {
                goToSummary();
            } else {
                activity.recyclerView.smoothScrollToPosition(scrollPosition);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) activity.recyclerView.getLayoutManager();
                linearLayoutManager.scrollToPositionWithOffset(scrollPosition, 10);
            }

        }

        private void goToSummary() {
            ESFAFragTags fragTags;
            Bundle bundle = new Bundle();
            bundle.putBoolean("IS_NEW", activity.isNew);
            bundle.putString("retailerCode", activity.retailerCode);
            bundle.putString("retailerName", activity.retailerName);
            bundle.putString("routeCode", activity.routeCode);
            bundle.putString("salesmanCode", activity.salesmanCode);
            bundle.putString("screenType", NAME_ORDER_BOOKING);
            bundle.putString("invoiceNo", activity.invoiceNo);
            bundle.putString("customerShipCode", activity.customerShipCode);
            bundle.putString("invoiceDate", activity.invoiceDate);
            bundle.putString("routeCode", activity.routeCode);
            bundle.putString("salesmanCode", activity.salesmanCode);
            bundle.putString("suffixYy", activity.suffixYy);
            bundle.putString("distStateCode", activity.distStateCode);
            bundle.putString("retailerStateCode", activity.retailerStateCode);
            bundle.putInt("codeValue", activity.codeValue);
            fragTags = ESFAFragTags.BILLING_LINE_LEVEL_SUMMARY;

            SFAFragmentManager sfaFragManger = SFAFragmentManager.newInstance();
            if (!sfaFragManger.updateFragment(fragTags, true, activity.getSFAFragmentActivity(), bundle) && BuildConfig.DEBUG) {
                Log.e("Ordersummaryfragment", "Error in creating fragment");
            }

        }


        private void cancleProgressDialog() {
            try {
                if (activity.pd != null && activity.pd.isShowing()) {
                    activity.pd.dismiss();
                }
            } catch (Exception e) {
                Log.e(TAG, "cancleProgressDialog: " + e.getMessage(), e);
            }
        }


        /**
         * check the stock Available for the product in van Sales Order Only
         *
         * @return true or false
         */
        private boolean checkStockAvail() {
            if (activity.qtyUpdatedOrderBooking != null && !activity.qtyUpdatedOrderBooking.isEmpty()) {
                for (int i = 0; i < activity.qtyUpdatedOrderBooking.size(); i++) {
                    final OrderBookingVO orderBookingVO = activity.qtyUpdatedOrderBooking.get(i);
                    int conversionQty = activity.mOrderSupportUtil.calcUomConversionQty(orderBookingVO, orderBookingVO.getUomId(), orderBookingVO.getQuantity());
                    int stockOnHand = 0;
                    if (orderBookingVO.getStockInHand() > 0) {
                        stockOnHand = orderBookingVO.getStockInHand();
                    }
                    if (conversionQty > stockOnHand) {
                        activity.outOfStockPos = i;
                        return false;
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
                        if (conversionQty % 1 != 0) {
                            activity.weightCheckPos = i;
                            return false;
                        }
                    }
                }
            }
            return true;
        }

    }

    public static class AsyncLoadData extends AsyncTask<Void, Void, Boolean> {

        private final QuickOrderBookingFragment activity;
        private final WeakReference<QuickOrderBookingFragment> activityReference;
        boolean boolResponse = false;

        public AsyncLoadData(QuickOrderBookingFragment context) {
            activityReference = new WeakReference<>(context);
            this.activity = activityReference.get();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                activity.orderBookingVOList = new ArrayList<>();

                activity.distStateCode = activity.db.getDistStateCode();
                activity.retailerStateCode = activity.mOrderSupportUtil.getRetailerStateCode(2, activity.retailerCode);

                activity.schemeProductDetail = activity.db.getCustomerSchemeProducts(activity.retailerCode);
                activity.schemeBudgetList = activity.db.getSchemeBudgetList(activity.preferences.readString(PREF_DISTRCODE), activity.retailerCode);

                activity.orderBookingVOList = activity.db.getAllProductsFromAuto(activity.preferences.readString(PREF_CMP_CODE),
                        activity.preferences.readString(PREF_DISTRCODE), activity.retailerCode);
                loadPreviousOrderProducts();
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: " + e.getMessage(), e);
            }

            return boolResponse;
        }

        private void loadPreviousOrderProducts() {
            try {
                List<OrderBookingVO> previousOrderList;
                if (activity.isNew) {
                    previousOrderList = activity.db.getBilledProductsFromDB(activity.preferences.readString(PREF_DISTRCODE),
                            activity.salesmanCode, activity.routeCode, activity.retailerCode, activity.invoiceNo, "N");
                } else {
                    previousOrderList = activity.db.getBilledProductsFromDB(activity.preferences.readString(PREF_DISTRCODE),
                            activity.salesmanCode, activity.routeCode, activity.retailerCode, activity.invoiceNo, "S");
                }

                    for (OrderBookingVO allProdList : activity.orderBookingVOList) {
                        for (OrderBookingVO prevOrder : previousOrderList) {
                            if (allProdList.getProdCode().equalsIgnoreCase(prevOrder.getProdCode()) && allProdList.getProdBatchCode().equalsIgnoreCase(prevOrder.getProdBatchCode())) {
                                allProdList.setQuantity(prevOrder.getQuantity());
                                allProdList.setUomId(prevOrder.getUomId());
//                                allProdList.setQuantity2(prevOrder.getQuantity2());
//                                allProdList.setUomId2(prevOrder.getUomId2());
//                                allProdList.setDbDiscountPerc(prevOrder.getDbDiscountPerc());
//                                allProdList.setDbDiscountAmt(prevOrder.getDbDiscountAmt());
                                List<OrderBookingVO> orderBooking = new ArrayList<>(activity.db.getUom(allProdList.getUomGroupId(), TABLE_PRODUCT_UOM_MASTER));

                                activity.uomIdSpinner = activity.mOrderSupportUtil.getUomIdSpinnerList(orderBooking);
                            }
                        }
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
            activity.loadAdapterData();
            if (activity.pd != null && activity.pd.isShowing()) {
                activity.pd.dismiss();
            }
        }
    }
}
