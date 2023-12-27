package com.botree.retailerssfa.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.adapters.SchemePagerAdapter;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.controller.retrofit.DataManager;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.scheme.SchemeEngin;
import com.botree.retailerssfa.support.CertificatePinner;
import com.botree.retailerssfa.support.tooltip.SimpleTooltip;
import com.botree.retailerssfa.util.NotifyUtil;
import com.botree.retailerssfa.util.OrderSupportUtil;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.botree.retailerssfa.controller.constants.AppConstant.IntentExtras.NAME_IS_VANSALES_ORDER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_PRODUCT_UOM_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_UOM_MASTER;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_VANSALES_PRODUCTS;
import static com.botree.retailerssfa.db.query.IDBColumns.TABLE_VANSALES_UOM_MASTER;


public class ProductImageSliderFragment extends Fragment implements View.OnClickListener {

    private static final String CURRENT_POSITION = "position";
    private static final String CURRENT_PRODUCT_CODE = "productCode";
    private static final String LOG_TAG = ProductImageSliderFragment.class.getSimpleName();
    private static final String TAG = ProductImageSliderFragment.class.getSimpleName();
    private OrderSupportUtil mOrderSupportUtil;
    private Animation animation;
    private List<OrderBookingVO> orderBookingVOList = new ArrayList<>();
    private int currentPosition;
    private int mPosition;
    private OnFragmentInteractionListener mListener;
    private SFADatabase db;
    private SchemeEngin schemeEngin;
    private String productCode;
    private double availableStockQuantity;
    private String uom = "";
    private ImageView productImg;
    private TextView productNameTxt;
    private EditText editQty;
    private Spinner uomSpinner;
    private TextView tvQuantity;
    private TextView tvSlab;
    private TextView tvSlabLayout;
    private TextView lineTot;
    private TextView currentPositionCount;
    private TextView soqTxt;
    private TextView sihTxt;
    private TextView mrpTxt;
    private TextView sellingPriceTxt;
    private LinearLayout rootLayout;
    private OrderBookingVO orderBookingVO;
    private List<SchemeModel> schemeProductDetail = new ArrayList<>();
    private List<SchemeModel> orderAppliedSchemeList = new ArrayList<>();
    private ArrayList<String> uomIdSpinner = new ArrayList<>();
    private int enteredUOM = -1;
    private String enteredQty = "";
    private SimpleTooltip simpleTooltip;
    private View mLayoutSih;
    private View mLayoutSoq;
    private boolean isVanSalesOrder = false;
    private List<SchemeModel> mAppliedSlablist = new ArrayList<>();
    private List<SchemeModel> productSchemeModelList = new ArrayList<>();
    private List<SchemeModel> schemeSlabList = new ArrayList<>();
    private List<SchemeModel> freeProdSlabList = new ArrayList<>();

    public ProductImageSliderFragment() {
        // Required empty public constructor
    }

    public static ProductImageSliderFragment newInstance(int position, String productCode, boolean isVansales) {

        ProductImageSliderFragment fragment = new ProductImageSliderFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_POSITION, position);
        args.putString(CURRENT_PRODUCT_CODE, productCode);
        args.putBoolean(NAME_IS_VANSALES_ORDER, isVansales);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mPosition = getArguments().getInt(CURRENT_POSITION);
            isVanSalesOrder = getArguments().getBoolean(NAME_IS_VANSALES_ORDER);
            currentPosition = mPosition;

        }

        db = SFADatabase.getInstance(getActivity());
        schemeEngin = new SchemeEngin(getActivity());
        mOrderSupportUtil = OrderSupportUtil.getInstance();

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_image_slider, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializedView(view);
        loadProductImage();
    }

    private void initializedView(View view) {
        productImg = view.findViewById(R.id.single_product_img);
        ImageView showSchemeImg = view.findViewById(R.id.show_scheme_img);
        productNameTxt = view.findViewById(R.id.single_prod_name_txt);
        editQty = view.findViewById(R.id.qty_edtxt);
        Button submitBtn = view.findViewById(R.id.single_prod_submit_btn);
        uomSpinner = view.findViewById(R.id.spinner_uom);
        tvQuantity = view.findViewById(R.id.tvQuantity);
        lineTot = view.findViewById(R.id.line_tot_price_txt);
        currentPositionCount = view.findViewById(R.id.current_position_count);
        tvSlab = view.findViewById(R.id.tvSlab);
        tvSlabLayout = view.findViewById(R.id.tvSlab_bg);

        soqTxt = view.findViewById(R.id.single_prod_soq_txt);
        sihTxt = view.findViewById(R.id.single_prod_sih_txt);
        mrpTxt = view.findViewById(R.id.single_prod_mrp_txt);
        sellingPriceTxt = view.findViewById(R.id.single_prod_sell_pri_txt);
        rootLayout = view.findViewById(R.id.single_prod_root_layout);
        mLayoutSih = view.findViewById(R.id.layout_sih);
        mLayoutSoq = view.findViewById(R.id.layout_soq);

        submitBtn.setOnClickListener(this);
        showSchemeImg.setOnClickListener(this);

        orderBookingVOList = DataManager.getInstance().getSwipeOrderBookingVOList();

        schemeProductDetail = DataManager.getInstance().getSchemeProductDetail();
        try {
            Map<String, Object> objectHashMap = DataManager.getInstance().getImageOrderProductDetails();
            productCode = orderBookingVOList.get(currentPosition).getProdCode();
            availableStockQuantity = orderBookingVOList.get(currentPosition).getStockCheckQty();
            String callingScreen = String.valueOf(objectHashMap.get("callingScreen"));
            uom = orderBookingVOList.get(currentPosition).getUomId();
            Log.e(LOG_TAG, "onCreate: " + callingScreen);

        } catch (Exception e) {
            Log.e(LOG_TAG, "onCreate: " + e.getMessage(), e);
        }

        calculateQtyAndSchemes();

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.single_prod_submit_btn) {
            if (!editQty.getText().toString().isEmpty() && editQty.getText().length() > 0) {

                if (Double.valueOf(editQty.getText().toString()) > 0) {
                    checkOrderQty(Double.valueOf(editQty.getText().toString()), orderBookingVO, uomSpinner.getSelectedItem().toString());
                    mListener.onFragmentInteraction(mPosition);
                    Log.e("Tag", "Exec");
                }

            } else {
                mListener.onFragmentInteraction(mPosition);
            }

        } else if (i == R.id.show_scheme_img) {
            showSchemeDetail();
        }

    }


    /**
     * validate the quantity for both uom.
     *
     * @param enterQty       entered quantity fot the product
     * @param orderBookingVO product detail model
     * @param selectedUom    selected Uom for the product
     */
    private void checkOrderQty(double enterQty, OrderBookingVO orderBookingVO, String selectedUom) {
        double conversionQty = mOrderSupportUtil.calcWeightUomConversionQty(orderBookingVO, selectedUom, enterQty);
        if (!mOrderSupportUtil.isWeightUom(selectedUom) && enterQty % 1 != 0) {
            NotifyUtil.showSnackBar(getActivity(), rootLayout, "Decimal quantity not allowed for Selected UOM (" + selectedUom + ") Quantity", Toast.LENGTH_SHORT);
        } else if (conversionQty % 1 != 0) {
            NotifyUtil.showSnackBar(getActivity(), rootLayout, getString(R.string.error_weight), Toast.LENGTH_SHORT);
            removeItemFromList(orderBookingVO);
        } else if (conversionQty >= 0) {
            if ((isVanSalesOrder && conversionQty <= availableStockQuantity)) {
                qunatityDailogClose(Double.valueOf(editQty.getText().toString()), orderBookingVO, uomSpinner.getSelectedItem().toString());

            } else if (!isVanSalesOrder) {
                qunatityDailogClose(Double.valueOf(editQty.getText().toString()), orderBookingVO, uomSpinner.getSelectedItem().toString());

            } else {
                Log.e(TAG, "checkOrderQty: Stock and enter the quantity");
                NotifyUtil.showSnackBar(getActivity(), rootLayout, " Please check the Stock and enter the quantity", Toast.LENGTH_SHORT);
            }
        } else {
            NotifyUtil.showSnackBar(getActivity(), rootLayout, getString(R.string.error_weight_less_than_prod)
                    + orderBookingVO.getNetWeight() + " " + orderBookingVO.getWeightType(), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void calculateQtyAndSchemes() {

        orderBookingVO = orderBookingVOList.get(mPosition);
        int showPositionCount = mPosition + 1;
        Log.e(LOG_TAG, "showPositionCount: " + showPositionCount + "mPosition: " + mPosition);
        currentPositionCount.setText(String.format("%s of %s", String.valueOf(showPositionCount), String.valueOf(orderBookingVOList.size())));
        soqTxt.setText(String.valueOf(orderBookingVO.getSuggestedQty()));
        sihTxt.setText(String.valueOf(orderBookingVO.getStockInHand()));
        mrpTxt.setText(MessageFormat.format("{0}{1}", getString(R.string.Rs), String.format(Locale.getDefault(), "%.2f", orderBookingVO.getMrp().doubleValue())));
        sellingPriceTxt.setText(MessageFormat.format("{0}{1}", getString(R.string.Rs), String.format(Locale.getDefault(), "%.2f", orderBookingVO.getSellPrice().doubleValue())));
        setConfigVisible(AppConstant.Configurations.CONFIG_SIH, mLayoutSih);
        setConfigVisible(AppConstant.Configurations.CONFIG_SOQ, mLayoutSoq);
        productNameTxt.setText(orderBookingVO.getProdName());

        mAppliedSlablist = new ArrayList<>();
        productSchemeModelList = new ArrayList<>();
        schemeSlabList = new ArrayList<>();
        freeProdSlabList = new ArrayList<>();


        if (!isVanSalesOrder && "N".equalsIgnoreCase(db.getConfigDataBasedOnName(AppConstant.Configurations.CONFIG_SIH))) {
            sihTxt.setVisibility(View.GONE);
        }
        if ("N".equalsIgnoreCase(db.getConfigDataBasedOnName(AppConstant.Configurations.CONFIG_SOQ))) {
            soqTxt.setVisibility(View.GONE);
        }

        String defaultUomId = orderBookingVO.getDefaultUomid();
        String uomGroupId = orderBookingVO.getUomGroupId();

        updateSchemeforSameProd(orderBookingVO);
        schemeDetails(productSchemeModelList, schemeSlabList, freeProdSlabList);


        defaultUomId = setDefaultUomId(defaultUomId);

        loadUom(orderBookingVO, uomGroupId, defaultUomId, uomSpinner, enteredUOM == -1);

        if (enteredUOM != -1)
            uomSpinner.setSelection(enteredUOM);


        if (enteredQty.length() > 0 && !"0".equals(enteredQty)) {
            editQty.setText(enteredQty);
        }

        tvQuantity.setText(getString(R.string.quantity));

        if (orderBookingVO.getQuantity() > 0) {
            editQty.setText(String.valueOf(orderBookingVO.getQuantity()));
        } else {
            editQty.setText(String.valueOf(""));
        }

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
                textWatcherLogic(s);

            }
        });

        uomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enteredUOM = position;
                boolean isQtyEmpty = "".equals(editQty.getText().toString());

                double currentQty = 0;
                if (!isQtyEmpty) {
                    currentQty = Double.valueOf(editQty.getText().toString());
                }

                if (currentQty > 0) {
                    checkOrderQty(currentQty, orderBookingVO, uomSpinner.getSelectedItem().toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing selected from uomSpinner this will execute
            }
        });

        boolean isQtyEmpty = "".equals(editQty.getText().toString());

        if (!isQtyEmpty) {
            double currentQty = orderBookingVO.getQuantity();
            double orderValue = mOrderSupportUtil.calculateLineOrderValue(orderBookingVO, uomSpinner.getSelectedItem().toString(), currentQty);

            lineTot.setText(String.format(Locale.getDefault(), "%.2f", orderValue));
        }

        editQty.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return editTextActionLogic(actionId, event);
            }
        });


    }

    private String setDefaultUomId(String defaultUomId) {
        if (!uom.isEmpty()) {
            defaultUomId = uom;
        } else {
            if (orderBookingVO.getUomId() != null && orderBookingVO.getUomId().length() > 0)
                defaultUomId = orderBookingVO.getUomId();
        }
        return defaultUomId;
    }

    private void setConfigVisible(String configSih, View mLayoutSih) {
        if ("N".equalsIgnoreCase(db.getConfigDataBasedOnName(configSih))) {
            mLayoutSih.setVisibility(View.GONE);
        }
    }

    private boolean editTextActionLogic(int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
            if (!editQty.getText().toString().isEmpty()) {
                qunatityDailogClose(Double.valueOf(editQty.getText().toString()), orderBookingVO, uomSpinner.getSelectedItem().toString());

            } else {
                editQty.setError(getString(R.string.error_quantity));
            }
        }
        return false;
    }

    private void textWatcherLogic(Editable s) {
        boolean isQtyEmpty = "".equals(editQty.getText().toString());
        enteredQty = s.toString();
        double currentQty = 0;
        if (!isQtyEmpty) {
            currentQty = Double.valueOf(editQty.getText().toString());
        } else {
            // Do nothing
        }

        if (currentQty > 0) {
            checkOrderQty(currentQty, orderBookingVO, uomSpinner.getSelectedItem().toString());
        }
    }

    private void schemeDetails(List<SchemeModel> productSchemeModelList, List<SchemeModel> schemeSlabList, List<SchemeModel> freeProdSlabList) {
        try {
            for (SchemeModel schemeModel : schemeProductDetail) {
                if (orderBookingVOList.get(currentPosition).getProdCode().equalsIgnoreCase(schemeModel.getProductCode())
                        && schemeModel.getCombi().equalsIgnoreCase("N")) {
                    productSchemeModelList.add(schemeModel);
                    Log.d(LOG_TAG, "Quantity: " + schemeModel.getSchemeCode());
                }
            }
            schemeSlabList.addAll(db.getAllSchemeSlabDetail(productSchemeModelList));
            freeProdSlabList.addAll(db.getAllFreeProdList(productSchemeModelList));


        } catch (Exception e) {
            Log.e(LOG_TAG, "calculateQtyAndSchemes: " + e.getMessage(), e);
        }
    }

    private void updateSchemeforSameProd(OrderBookingVO orderBookingVO) {
        try {
            List<SchemeModel> editProdTempList = new ArrayList<>();
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
            Log.d(LOG_TAG, "afterTextChanged: " + editProdTempList.size() + " | " + orderAppliedSchemeList.size());
        } catch (Exception e) {
            Log.e(LOG_TAG, "updateSchemeforSameProd: " + e.getMessage(), e);
        }
    }

    private void loadUom(OrderBookingVO productObject, String prodCode, String defaultUomId, Spinner uomSpinner, boolean selectUOM) {


        List<OrderBookingVO> orderBooking;

        ArrayList<String> uomDescSpinner = new ArrayList<>();
        uomIdSpinner = new ArrayList<>();
        if (isVanSalesOrder)
            orderBooking = db.getUom(prodCode, TABLE_VANSALES_UOM_MASTER);
        else
            orderBooking = db.getUom(productObject.getProdCode(), TABLE_PRODUCT_UOM_MASTER);

        for (int i = 0; i < orderBooking.size(); i++) {
            uomDescSpinner.add(orderBooking.get(i).getDefaultUomid());
            uomIdSpinner.add(orderBooking.get(i).getUomGroupId());
        }
        uomIdSpinner.addAll(mOrderSupportUtil.getWeightUOMs(productObject));//for adding Weight UOM
        uomDescSpinner.addAll(mOrderSupportUtil.getWeightUOMs(productObject));//for adding Weight UOM

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, uomDescSpinner);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uomSpinner.setAdapter(spinnerArrayAdapter);
        if (selectUOM)
            for (int i = 0; i < uomIdSpinner.size(); i++) {
                if (String.valueOf(uomIdSpinner.get(i)).equals(defaultUomId)) {
                    uomSpinner.setSelection(i);
                }
            }
    }

    private void showSchemeMsg(double currentQty, OrderBookingVO orderBookingVO, Spinner uomSpinner,
                               List<SchemeModel> mAppliedSlablist, List<SchemeModel> productSchemeModelList,
                               List<SchemeModel> freeProdSlabList, List<SchemeModel> schemeSlabList) {
        String offerMsg = "";
        String suugestedOfferMsg;
        if (tvSlab != null) {
            tvSlab.setVisibility(View.GONE);
            tvSlabLayout.setVisibility(View.GONE);
            tvSlab.setText(offerMsg);
        }

        double orderValue = mOrderSupportUtil.calculateLineOrderValue(orderBookingVO, uomSpinner.getSelectedItem().toString(), currentQty);
        double primDiscOrderValue = mOrderSupportUtil.getPrimDiscAppliedOrderValue(orderValue, orderBookingVO.getPrimaryDisc());
        try {

            int conversionQty = mOrderSupportUtil.calcUomConversionQty(orderBookingVO, uomSpinner.getSelectedItem().toString(), currentQty);

            mAppliedSlablist.clear();

            if (currentQty > 0) {
                mAppliedSlablist.addAll(schemeEngin.calculateSchemes(primDiscOrderValue, conversionQty, orderBookingVO,
                        productSchemeModelList, freeProdSlabList, schemeSlabList, uomIdSpinner));

            }

            if (simpleTooltip != null && simpleTooltip.isShowing()) {
                simpleTooltip.dismiss();
                simpleTooltip = null;
            }

            offerMsg = schemeEngin.showSchemeDetailInText(mAppliedSlablist);
            if (!offerMsg.isEmpty() && tvSlab != null) {

                tvSlab.setVisibility(View.VISIBLE);
                tvSlab.setText(offerMsg);
                tvSlab.startAnimation(animation);
                tvSlabLayout.setVisibility(View.VISIBLE);

            }
            if (isVanSalesOrder) {
                suugestedOfferMsg = schemeEngin.showSuggestedSchemeDetailInText(orderBookingVO, productSchemeModelList, currentQty,
                        primDiscOrderValue, uomIdSpinner, uomSpinner.getSelectedItem().toString(), TABLE_VANSALES_PRODUCTS);
            } else {
                suugestedOfferMsg = schemeEngin.showSuggestedSchemeDetailInText(orderBookingVO, productSchemeModelList, currentQty,
                        primDiscOrderValue, uomIdSpinner, uomSpinner.getSelectedItem().toString(), TABLE_PRODUCTS);
            }

            if (!suugestedOfferMsg.isEmpty() && tvSlab != null) {

                tvSlab.setVisibility(View.VISIBLE);
                tvSlab.setText(String.valueOf(offerMsg + "\n" + suugestedOfferMsg));
                tvSlabLayout.setVisibility(View.VISIBLE);
                tvSlab.startAnimation(animation);

            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "showSchemeMsg: " + e.getMessage(), e);
        }

        lineTot.setText(String.format(Locale.getDefault(), "%.2f", orderValue));
    }

    private void qunatityDailogClose(double currentQty, OrderBookingVO orderBookingVO, String uomSpinnerItem) {
        showSchemeMsg(currentQty, orderBookingVO, uomSpinner, mAppliedSlablist, productSchemeModelList, freeProdSlabList, schemeSlabList);
        List<OrderBookingVO> tempSwipeList = DataManager.getInstance().getSwipeOrderBookingVOList();

        if (currentQty >= 0) {

            if (tempSwipeList != null && tempSwipeList.contains(orderBookingVO)) {

                OrderBookingVO tempOrderBookingVO = tempSwipeList.get(tempSwipeList.indexOf(orderBookingVO));
                tempOrderBookingVO.setQuantity(currentQty);
                tempOrderBookingVO.setTotQty(mOrderSupportUtil.calcUomConversionQty(orderBookingVO, uomSpinnerItem, currentQty));
                double orderValue = mOrderSupportUtil.calculateLineOrderValue(tempOrderBookingVO, uomSpinnerItem, currentQty);
                tempOrderBookingVO.setOrderValue(BigDecimal.valueOf(orderValue));
                tempOrderBookingVO.setPrimDiscOrderValue(BigDecimal.valueOf(mOrderSupportUtil.getPrimDiscAppliedOrderValue(orderValue, orderBookingVO.getPrimaryDisc())));
                tempOrderBookingVO.setRemarks("");
                tempOrderBookingVO.setUomId(uomSpinnerItem);
            }

            DataManager.getInstance().addSwipeOrderBookingVOList(tempSwipeList);

        }

        enteredUOM = -1;
        enteredQty = "";

    }

    private void removeItemFromList(OrderBookingVO orderBookingVO) {
        List<OrderBookingVO> tempSwipeList = DataManager.getInstance().getSwipeOrderBookingVOList();

        if (tempSwipeList != null && tempSwipeList.contains(orderBookingVO)) {

            OrderBookingVO tempOrderBookingVO = tempSwipeList.get(tempSwipeList.indexOf(orderBookingVO));
            tempOrderBookingVO.setQuantity(0);

            tempOrderBookingVO.setOrderValue(BigDecimal.valueOf(0));
            tempOrderBookingVO.setRemarks("");
            tempOrderBookingVO.setUomId("");
        }

        DataManager.getInstance().addSwipeOrderBookingVOList(tempSwipeList);

    }


    private void loadProductImage() {

        String url = getResources().getString(R.string.BASE_URL_RELEASE) + "controller/getproductimage/" + productCode + "/jpg";
        if (BuildConfig.DEBUG) {
            url = getResources().getString(R.string.BASE_URL_DEBUG) + "controller/getproductimage/" + productCode + "/jpg";
        }

        CertificatePinner.getInstCertificate().getCustomPicasso(getActivity())
                .load(url)
                .placeholder(R.drawable.sfa_placeholder)
                .error(R.drawable.sfa_placeholder)
                .into(productImg);

    }


    /**
     * Shows the scheme details dialog with scheme description
     */
    private void showSchemeDetail() {

        String desc = getString(R.string.scheme_empty);

        if (orderBookingVOList.get(currentPosition).getDescription() != null
                && !"".equalsIgnoreCase(orderBookingVOList.get(currentPosition).getDescription())) {
            desc = orderBookingVOList.get(currentPosition).getDescription();
        }

        final Dialog schemeDialog = new Dialog(getActivity(), R.style.ThemeDialogCustom);
        if (schemeDialog.isShowing()) return;
        schemeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        schemeDialog.setCanceledOnTouchOutside(false);
        schemeDialog.setCancelable(false);
        schemeDialog.setContentView(R.layout.scheme_description_dialog);

        final TextView popupProductFullName = schemeDialog.findViewById(R.id.popup_productFullName);
        final TextView popupDesc = schemeDialog.findViewById(R.id.popup_desc);

        popupProductFullName.setText(orderBookingVOList.get(currentPosition).getProdName());

        final ViewPager pager = schemeDialog.findViewById(R.id.schemeviewpager);
        SchemePagerAdapter myAdapter;
        try {
            List<SchemeModel> schemeModelList = new ArrayList<>();
            for (SchemeModel schemeModel : schemeProductDetail) {
                if (orderBookingVOList.get(currentPosition).getProdCode().equalsIgnoreCase(schemeModel.getProductCode())) {
                    schemeModelList.add(schemeModel);
                }
            }
            myAdapter = new SchemePagerAdapter(getActivity(), schemeModelList);
            if (myAdapter.getCount() > 0)
                pager.setAdapter(myAdapter);
            else
                popupDesc.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Log.e(LOG_TAG, "showSchemeDetail: " + e.getMessage(), e);
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
            }
        });

        schemeDialog.show();

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int position);
    }

}
