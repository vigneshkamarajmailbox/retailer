/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.inputmethodservice.KeyboardView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.main.QuickOrderBookingFragment;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.OrderSupportUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_FOCUS_PRODUCT;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_MUSTSELL_PRODUCT;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_NEW_PRODUCT;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_OUT_OF_STOCK_PRODUCT;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_SCHEME_PRODUCT;
import static com.botree.retailerssfa.support.Globals.ORDER_BOOKING_QUANTITY_DIALOG;
import static com.botree.retailerssfa.util.OrderSupportUtil.ALL_BRANDS;
import static com.botree.retailerssfa.util.OrderSupportUtil.ALL_CATEGORY;
import static com.botree.retailerssfa.util.OrderSupportUtil.FOCUS_BRAND;
import static com.botree.retailerssfa.util.OrderSupportUtil.MUST_SELL;
import static com.botree.retailerssfa.util.OrderSupportUtil.PROMO;

public class QuickOrderBookingAdapter extends RecyclerView.Adapter<QuickOrderBookingAdapter.OrderBookingViewHolder> {

    private static final String TAG = QuickOrderBookingAdapter.class.getSimpleName();
    private final OrderSupportUtil orderSupportUtil = OrderSupportUtil.getInstance();
    private Context con;
    private QuickOrderBookingFragment.ZeroResult zeroNonZeroResult;
    private String brandValue;
    private String brandCode;
    private String categoryValue;
    private QuickOrderBookingAdapter.OnItemClickListener mItemClickListener;
    private QuickOrderBookingAdapter.OnItemLongClickListener monItemLongClicked;
    private OnFocusListener mOnFocusListener;
    private List<OrderBookingVO> orderBookingVOList;
    private List<OrderBookingVO> searchList;

    private List<List<OrderBookingVO>> uomList;

    private KeyboardView mKeyboardView;
    private RecyclerView recyclerView;
    private Map<String, String> configColorList;

    public QuickOrderBookingAdapter(Context context, List<OrderBookingVO> orderBookingVOLists, List<List<OrderBookingVO>> uomList,
                                    int callingFragment, KeyboardView mKeyboardView, RecyclerView recyclerView) {

        con = context;
        this.orderBookingVOList = orderBookingVOLists;
        this.searchList = new ArrayList<>();
        this.uomList = uomList;
        this.searchList.addAll(orderBookingVOList);
        this.recyclerView = recyclerView;


        configColorList = AppUtils.getOurInstance().getConfigColorList(context, new String[]{CONFIG_NEW_PRODUCT, CONFIG_OUT_OF_STOCK_PRODUCT, CONFIG_FOCUS_PRODUCT,
                CONFIG_MUSTSELL_PRODUCT, CONFIG_SCHEME_PRODUCT});
        this.mKeyboardView = mKeyboardView;
        for (int i = 0; i < orderBookingVOList.size(); i++) {
            if (orderBookingVOList.get(i).getUomId() == null)
                orderBookingVOList.get(i).setUomId(orderBookingVOList.get(i).getDefaultUomid());
            if (orderBookingVOList.get(i).getStockUomId() == null)
                orderBookingVOList.get(i).setStockUomId(orderBookingVOList.get(i).getDefaultUomid());

            uomList.get(i).addAll(OrderSupportUtil.getInstance().getWeightUOMsList(orderBookingVOList.get(i)));//for adding Weight UOM
        }
    }

    public void setOnItemClickListener(QuickOrderBookingAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(final QuickOrderBookingAdapter.OnItemLongClickListener mItemLongClicked) {
        this.monItemLongClicked = mItemLongClicked;
    }

    public String getValueAt(int position) {
        return orderBookingVOList.get(position).getProdCode();
    }

    public List<OrderBookingVO> getSearchList() {
        return searchList;
    }

    public void updateList(List<OrderBookingVO> orderBookingVOs, List<OrderBookingVO> searchList) {
        this.orderBookingVOList = orderBookingVOs;
        if (searchList.isEmpty()) {
            this.searchList = new ArrayList<>();
            this.searchList.addAll(orderBookingVOs);
        } else {
            this.searchList = searchList;
        }
    }

    @NonNull
    @Override
    public OrderBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quick_orderbooking_list_item, parent, false);
        return new OrderBookingViewHolder(view, new QuantityEditTextListener(), new Quantity2EditTextListener(),
                new DbValEditTextListener(), new DbPercEditTextListener(),
                new QuantityItemSelectedListener(), new Quantity2ItemSelectedListener());
    }

    private void setBackgroundColor(final QuickOrderBookingAdapter.OrderBookingViewHolder holder, final int color) {
        holder.productName.setTextColor(color);
        holder.productName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final QuickOrderBookingAdapter.OrderBookingViewHolder holder, int position) {


        try {

            setBackgroundColor(holder,
                    ContextCompat.getColor(con, R.color.transparent));

            if (PROMO.equalsIgnoreCase(orderBookingVOList.get(holder.getAdapterPosition())
                    .getProductStatus())) {
                setBackgroundColor(holder, Color.parseColor(getColorCode(configColorList.get(CONFIG_SCHEME_PRODUCT))));

            } else if (orderBookingVOList.get(holder.getAdapterPosition()).getStockInHand() <= 0) {
                setBackgroundColor(holder, Color.parseColor(getColorCode(configColorList.get(CONFIG_OUT_OF_STOCK_PRODUCT))));

            } else if (MUST_SELL.equalsIgnoreCase(orderBookingVOList.get(holder.getAdapterPosition())
                    .getMustcategory())) {
                setBackgroundColor(holder, Color.parseColor(getColorCode(configColorList.get(CONFIG_MUSTSELL_PRODUCT))));

            } else if (FOCUS_BRAND.equalsIgnoreCase(orderBookingVOList.get(holder.getAdapterPosition())
                    .getFocusCategory())) {
                setBackgroundColor(holder, Color.parseColor(getColorCode(configColorList.get(CONFIG_FOCUS_PRODUCT))));

            } else {

                setBackgroundColor(holder, ContextCompat.getColor(con, R.color.black)); // light black
            }

            holder.productName.setText(orderBookingVOList.get(holder.getAdapterPosition()).getProdName());
            holder.mrp.setText(String.format(Locale.getDefault(), "%.2f", orderBookingVOList.get(holder.getAdapterPosition()).getMrp()));
            holder.sellingPrice.setText(String.valueOf(orderBookingVOList.get(holder.getAdapterPosition()).getSellPrice()));


            holder.myQuantityEditTextListener.updatePosition(holder.getAdapterPosition());
            if (orderBookingVOList.get(holder.getAdapterPosition()).getQuantity() > 0) {
                holder.quantity.setText(String.valueOf(orderSupportUtil.getStringQty(orderBookingVOList.get(holder.getAdapterPosition()).getQuantity())));
            } else {
                holder.quantity.setText("");
            }

//            holder.qty2EditTextListener.updatePosition(holder.getAdapterPosition());
//            if (orderBookingVOList.get(holder.getAdapterPosition()).getQuantity2() > 0) {
//                holder.qty2EditText.setText(String.valueOf(orderSupportUtil.getStringQty(orderBookingVOList.get(holder.getAdapterPosition()).getQuantity2())));
//            } else {
//                holder.qty2EditText.setText("");
//            }
//
//            holder.dbValEditTextListener.updatePosition(holder.getAdapterPosition());
//            if (orderBookingVOList.get(holder.getAdapterPosition()).getDbDiscountAmt() > 0) {
//                holder.dbDiscValue.setText(String.valueOf(orderSupportUtil.getStringQty(orderBookingVOList.get(holder.getAdapterPosition()).getDbDiscountAmt())));
//            } else {
//                holder.dbDiscValue.setText("");
//            }
//
//            holder.dbPercEditTextListener.updatePosition(holder.getAdapterPosition());
//            if (orderBookingVOList.get(holder.getAdapterPosition()).getDbDiscountPerc() > 0) {
//                holder.dbDiscPerc.setText(String.valueOf(orderSupportUtil.getStringQty(orderBookingVOList.get(holder.getAdapterPosition()).getDbDiscountPerc())));
//                holder.dbDiscValue.setText("");
//            } else {
//                holder.dbDiscPerc.setText("");
//            }
//
//            setQuentityUom(holder, holder.getAdapterPosition());

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage(), e);
        }
    }

    private void setQuentityUom(@NonNull OrderBookingViewHolder holder, int position) {
        try {
            holder.quantityItemSelectedListener.updatePosition(holder.getAdapterPosition());
            holder.quantity2ItemSelectedListener.updatePosition(holder.getAdapterPosition());
            loadUom(uomList.get(position), orderBookingVOList.get(position).getUomId(), holder.orderSpinner);
//            loadUom(uomList.get(position), orderBookingVOList.get(position).getUomId2(), holder.qty2Spinner);

        } catch (Exception e) {

            Log.e(TAG, "onBindViewHolder: " + e.getMessage(), e);
        }
    }

    /**
     * Method helps to check Not Null for Color
     *
     * @param color current color
     * @return if color null then user #000000(black Color)
     */
    private String getColorCode(String color) {
        if (color != null && !color.isEmpty())
            return color;
        else
            return "#000000";
    }

    private void loadUom(List<OrderBookingVO> orderBooking, String defaultUomId, Spinner uomSpinner) {

        ArrayList<String> uomDescSpinner = new ArrayList<>();
        ArrayList<String> uomIdSpinner = new ArrayList<>();
        try {
            for (int i = 0; i < orderBooking.size(); i++) {
                uomDescSpinner.add(orderBooking.get(i).getDefaultUomid());
                uomIdSpinner.add(orderBooking.get(i).getUomGroupId());
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(con, android.R.layout.simple_spinner_item, uomDescSpinner);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            uomSpinner.setAdapter(spinnerArrayAdapter);
            for (int i = 0; i < uomIdSpinner.size(); i++) {
                if (String.valueOf(uomIdSpinner.get(i)).equals(defaultUomId)) {
                    uomSpinner.setSelection(i);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "loadUom: " + e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return orderBookingVOList.size();
    }

    private boolean isAllFilterEmpty(String filterText) {
        return filterText.length() == 0
                && zeroNonZeroResult == null
                && isBrandFilterEmpty()
                && isCategoryFilterEmpty();

    }

    private boolean isCategoryFilterEmpty() {
        return categoryValue != null && (categoryValue.length() == 0 || categoryValue.equals(ALL_CATEGORY));
    }

    private boolean isBrandFilterEmpty() {
        return brandValue != null && (brandValue.length() == 0 || brandValue.equals(ALL_BRANDS));
    }

    private boolean isBrandValidationSuccess(OrderBookingVO bookingVO) {
        return isStrEmpty(brandValue) && isStrEmpty(brandCode) && !brandValue.equals(ALL_BRANDS)
                && !"".equals(bookingVO.getProductHierPath())
                && !bookingVO.getProductHierPath().contains("/" + brandCode + "/");
    }

    private boolean isStrEmpty(String str) {
        return str != null && str.length() != 0;
    }

    private boolean isCategoryValidationSuccess(OrderBookingVO bookingVO) {
        if (isStrEmpty(categoryValue) &&
                !categoryValue.equals(ALL_CATEGORY) /*&&
                !("".equals(bookingVO.getProductStatus()) &&
                        "".equals(bookingVO.getCategory()))*/) {

            if (bookingVO.getProductStatus() == null &&
                    bookingVO.getMustcategory() == null && bookingVO.getFocusCategory() == null)
                return true;

            return (bookingVO.getFocusCategory() == null || !bookingVO.getFocusCategory().equalsIgnoreCase(categoryValue)) &&
                    (bookingVO.getMustcategory() == null || !bookingVO.getMustcategory().equalsIgnoreCase(categoryValue)) && (bookingVO.getProductStatus() == null || !bookingVO.getProductStatus().equalsIgnoreCase(categoryValue));

        }

        return false;
    }

    private void applyFilterAndAdd(String filterText) {
        orderBookingVOList.clear();

        if (isAllFilterEmpty(filterText)) {
            orderBookingVOList.addAll(searchList);
            notifyDataSetChanged();
            return;
        }

        for (OrderBookingVO bookingVO : searchList) {

            if (checkFilterText(filterText, bookingVO)) {

                if (brandCatFilterResult(bookingVO)) continue;

                zeroNonZeroFilterResult(bookingVO);
            }
        }
        notifyDataSetChanged();
    }

    private void zeroNonZeroFilterResult(OrderBookingVO bookingVO) {
        if (zeroNonZeroResult == QuickOrderBookingFragment.ZeroResult.ALL_PRODUCTS) {
            orderBookingVOList.add(bookingVO);
        } else if (zeroNonZeroResult == QuickOrderBookingFragment.ZeroResult.ZERO_PRODUCTS) {
            if (bookingVO.getStockInHand() == 0)
                orderBookingVOList.add(bookingVO);
        } else {
            if (bookingVO.getStockInHand() > 0)
                orderBookingVOList.add(bookingVO);
        }
    }

    private boolean brandCatFilterResult(OrderBookingVO bookingVO) {
        if (isBrandValidationSuccess(bookingVO))
            return true;

        if (isCategoryValidationSuccess(bookingVO))
            return true;

        if (zeroNonZeroResult == null) {
            orderBookingVOList.add(bookingVO);
            return true;
        }
        return false;
    }

    private boolean checkFilterText(String filterText, OrderBookingVO orderBookingVO) {

        return filterText.length() == 0 || (orderBookingVO.getProdName().toLowerCase(Locale.getDefault()).contains(filterText));

    }

    // Filter Class
    public void applySearchFilter(String charText) {
        applyFilterAndAdd(charText.toLowerCase(Locale.getDefault()));
    }

    public void applyFilter(QuickOrderBookingFragment.ZeroResult zeroNonZeroResult, String brandValue, String brandCode, String categoryValue) {
        this.zeroNonZeroResult = zeroNonZeroResult;
        this.brandValue = brandValue;
        this.brandCode = brandCode;
        this.categoryValue = categoryValue;
        applyFilterAndAdd("");
    }


    public void setmOnFocusListener(OnFocusListener mOnFocusListener) {
        this.mOnFocusListener = mOnFocusListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, double qty, double qty2, String uom, String uom2, Integer edtTxtType, String dbValue, String dbPerc);
    }

    public interface OnFocusListener {
        void onFocus(int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(View view, int position);
    }

    class OrderBookingViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnTouchListener {


        private final TextView productName;
        private final TextView mrp;
        private final TextView sellingPrice;
        private final EditText quantity;
        private final EditText qty2EditText;
        private final EditText dbDiscPerc;
        private final EditText dbDiscValue;
        private final Spinner qty2Spinner;
        private final Spinner orderSpinner;
        private QuantityEditTextListener myQuantityEditTextListener;
        private Quantity2EditTextListener qty2EditTextListener;
        private DbValEditTextListener dbValEditTextListener;
        private DbPercEditTextListener dbPercEditTextListener;
        private QuantityItemSelectedListener quantityItemSelectedListener;
        private Quantity2ItemSelectedListener quantity2ItemSelectedListener;

        @SuppressLint("ClickableViewAccessibility")
        OrderBookingViewHolder(View view, QuantityEditTextListener myQuantityEditTextListener, Quantity2EditTextListener quantity2EditTextListener,
                               DbValEditTextListener dbValEditTextListener, DbPercEditTextListener dbPercEditTextListener,
                               final QuantityItemSelectedListener quantityItemSelectedListener,
                               final Quantity2ItemSelectedListener quantity2ItemSelectedListener) {
            super(view);
            productName = view.findViewById(R.id.product_name_txt);
            mrp = view.findViewById(R.id.mrp_txt);
            sellingPrice = view.findViewById(R.id.selling_price_txt);
            quantity = view.findViewById(R.id.quantity_txt);
            orderSpinner = view.findViewById(R.id.order_uom_spinner);
            qty2EditText = view.findViewById(R.id.qty_2_text);
            dbDiscPerc = view.findViewById(R.id.db_disc_perc);
            dbDiscValue = view.findViewById(R.id.db_disc_value);
            qty2Spinner = view.findViewById(R.id.qty_2_uom);


            this.myQuantityEditTextListener = myQuantityEditTextListener;
            this.dbValEditTextListener = dbValEditTextListener;
            this.dbPercEditTextListener = dbPercEditTextListener;
            this.qty2EditTextListener = quantity2EditTextListener;
            this.quantityItemSelectedListener = quantityItemSelectedListener;
            this.quantity2ItemSelectedListener = quantity2ItemSelectedListener;
            this.quantity.addTextChangedListener(myQuantityEditTextListener);
            this.dbDiscValue.addTextChangedListener(dbValEditTextListener);
            this.dbDiscPerc.addTextChangedListener(dbPercEditTextListener);
            this.qty2EditText.addTextChangedListener(quantity2EditTextListener);
            this.orderSpinner.setOnItemSelectedListener(quantityItemSelectedListener);
            this.qty2Spinner.setOnItemSelectedListener(quantity2ItemSelectedListener);

            this.dbValEditTextListener.setPercEditText(this.dbDiscPerc);
            this.dbPercEditTextListener.setValEditText(this.dbDiscValue);

            this.quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    focusChangeLogic(hasFocus);
                }
            });

            this.qty2EditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    focusChangeLogic(hasFocus);
                }
            });

            quantity.setOnTouchListener(this);
            qty2EditText.setOnTouchListener(this);
            dbDiscValue.setOnTouchListener(this);
            dbDiscPerc.setOnTouchListener(this);
            view.setOnLongClickListener(this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideCustomKeyboard();
                }
            });

            quantity.setOnKeyListener(new View.OnKeyListener() {

                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == 100) {
                        int pos = getAdapterPosition() + 1;

                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        // you may want to play with the offset parameter
                        layoutManager.scrollToPositionWithOffset(pos, 0);
                        quantity.setFocusableInTouchMode(true);
                        quantity.post(new Runnable() {
                            @Override
                            public void run() {
                                quantity.requestFocus();
                            }
                        });

                        return true;

                    }
                    return false;
                }
            });

        }

        private void focusChangeLogic(boolean hasFocus) {
            try {
                if (hasFocus) {
                    if (mOnFocusListener != null) {
                        mOnFocusListener.onFocus(getAdapterPosition());
                    }
                    if (mItemClickListener != null) {
//                        mItemClickListener.onItemClick(getAdapterPosition(), orderBookingVOList.get(getAdapterPosition()).getQuantity(),
//                                orderBookingVOList.get(getAdapterPosition()).getQuantity2(), orderBookingVOList.get(getAdapterPosition()).getUomId(),
//                                orderBookingVOList.get(getAdapterPosition()).getUomId2(), ORDER_BOOKING_QUANTITY_DIALOG,
//                                String.valueOf(orderBookingVOList.get(getAdapterPosition()).getDbDiscountAmt()),
//                                String.valueOf(orderBookingVOList.get(getAdapterPosition()).getDbDiscountPerc()));
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "onFocusChange: " + e.getMessage(), e);
            }
        }

        private void hideCustomKeyboard() {

            mKeyboardView.setVisibility(View.GONE);
            mKeyboardView.setEnabled(false);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + productName.getText();
        }

        @Override
        public boolean onLongClick(View view) {

            if (monItemLongClicked != null) {
                monItemLongClicked.onItemLongClicked(view, getAdapterPosition());
            }
            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int i = v.getId();
            if (i == R.id.quantity_txt || i == R.id.qty_2_text || i == R.id.db_disc_perc || i == R.id.db_disc_value) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler

                float x = event.getX();
                float y = event.getY();
                int touchPosition = edittext.getOffsetForPosition(x, y);
                if (touchPosition > 0) {
                    edittext.setSelection(touchPosition);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showCustomKeyboard(v);
                }
                edittext.setInputType(inType);              // Restore input type


            } else if (i == R.id.stock_chk) {
                EditText edittextStock = (EditText) v;
                int inTypeStock = edittextStock.getInputType();       // Backup the input type
                edittextStock.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittextStock.onTouchEvent(event);               // Call native handler

                float xStock = event.getX();
                float yStock = event.getY();
                int touchPositionStock = edittextStock.getOffsetForPosition(xStock, yStock);
                if (touchPositionStock > 0) {
                    edittextStock.setSelection(touchPositionStock);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showCustomKeyboard(v);
                }
                edittextStock.setInputType(inTypeStock);              // Restore input type


            }
            return true;
        }


        private void showCustomKeyboard(final View v) {

            mKeyboardView.setVisibility(View.VISIBLE);
            mKeyboardView.setEnabled(true);
            if (v != null) {
                EditText edittext = (EditText) v;
                edittext.setSelection(edittext.getText().length());
                v.requestFocusFromTouch();
                ((InputMethodManager) con
                        .getSystemService(Activity.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
    }

    private class QuantityItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private int position;

        private void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int spinnerPos, long l) {

            if (orderBookingVOList.get(position).getQuantity() > 0) {
                orderBookingVOList.get(position).setUomId(uomList.get(position).get(spinnerPos).getDefaultUomid());
                if (mItemClickListener != null) {
//                    mItemClickListener.onItemClick(position, orderBookingVOList.get(position).getQuantity(),
//                            orderBookingVOList.get(position).getQuantity2(), orderBookingVOList.get(position).getUomId(),
//                            orderBookingVOList.get(position).getUomId2(), ORDER_BOOKING_QUANTITY_DIALOG,
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountAmt()),
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountPerc()));
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            // nothing selected
        }
    }

    private class Quantity2ItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private int position;

        private void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int spinnerPos, long l) {

//            if (orderBookingVOList.get(position).getQuantity2() > 0) {
//                orderBookingVOList.get(position).setUomId2(uomList.get(position).get(spinnerPos).getDefaultUomid());
//                if (mItemClickListener != null) {
//                    mItemClickListener.onItemClick(position, orderBookingVOList.get(position).getQuantity(),
//                            orderBookingVOList.get(position).getQuantity2(), orderBookingVOList.get(position).getUomId(),
//                            orderBookingVOList.get(position).getUomId2(), ORDER_BOOKING_QUANTITY_DIALOG,
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountAmt()),
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountPerc()));
//                }
//            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            // nothing selected
        }
    }

    private class QuantityEditTextListener implements TextWatcher {
        private int position;

        private void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            try {
                if (!charSequence.toString().isEmpty()) {
                    orderBookingVOList.get(position).setQuantity(Double.parseDouble(charSequence.toString()));
                } else {
                    orderBookingVOList.get(position).setQuantity(0);
                }
                if (mItemClickListener != null) {
//                    mItemClickListener.onItemClick(position, orderBookingVOList.get(position).getQuantity(), orderBookingVOList.get(position).getQuantity2(),
//                            orderBookingVOList.get(position).getUomId(), orderBookingVOList.get(position).getUomId2(), ORDER_BOOKING_QUANTITY_DIALOG,
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountAmt()),
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountPerc()));
                }
            } catch (NumberFormatException e) {
                orderBookingVOList.get(position).setQuantity(0);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    private class Quantity2EditTextListener implements TextWatcher {
        private int position;

        private void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

//            try {
//                if (!charSequence.toString().isEmpty()) {
//                    orderBookingVOList.get(position).setQuantity2(Double.parseDouble(charSequence.toString()));
//                } else {
//                    orderBookingVOList.get(position).setQuantity2(0);
//                }
//                if (mItemClickListener != null) {
//                    mItemClickListener.onItemClick(position, orderBookingVOList.get(position).getQuantity(), orderBookingVOList.get(position).getQuantity2(),
//                            orderBookingVOList.get(position).getUomId(), orderBookingVOList.get(position).getUomId2(), ORDER_BOOKING_QUANTITY_DIALOG,
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountAmt()),
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountPerc()));
//                }
//            } catch (NumberFormatException e) {
//                orderBookingVOList.get(position).setQuantity2(0);
//            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    private class DbValEditTextListener implements TextWatcher {
        private int position;
        private EditText percEditText;

        public void setPercEditText(EditText percEditText) {
            this.percEditText = percEditText;
        }

        private void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

//            try {
//                if (!charSequence.toString().isEmpty() && !charSequence.toString().equals(".")) {
//                    orderBookingVOList.get(position).setDbDiscountAmt(Double.parseDouble(charSequence.toString()));
//                    if (orderBookingVOList.get(position).getDbDiscountAmt() > 0) {
//                        percEditText.setEnabled(false);
//                        percEditText.setBackgroundColor(ContextCompat.getColor(con, R.color.color1));
//                    } else {
//                        percEditText.setEnabled(true);
//                        percEditText.setBackground(ContextCompat.getDrawable(con, R.drawable.txt_underline));
//                    }
//                } else {
//                    orderBookingVOList.get(position).setDbDiscountAmt(0);
//                    percEditText.setEnabled(true);
//                    percEditText.setBackground(ContextCompat.getDrawable(con, R.drawable.txt_underline));
//                }
//                if (mItemClickListener != null) {
//                    mItemClickListener.onItemClick(position, orderBookingVOList.get(position).getQuantity(), orderBookingVOList.get(position).getQuantity2(),
//                            orderBookingVOList.get(position).getUomId(), orderBookingVOList.get(position).getUomId2(), ORDER_BOOKING_QUANTITY_DIALOG,
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountAmt()),
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountPerc()));
//                }
//            } catch (NumberFormatException e) {
//                orderBookingVOList.get(position).setDbDiscountAmt(0);
//            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    private class DbPercEditTextListener implements TextWatcher {
        private int position;
        private EditText valEditText;

        public void setValEditText(EditText valEditText) {
            this.valEditText = valEditText;
        }

        private void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

//            try {
//                if (!charSequence.toString().isEmpty() && !charSequence.toString().equals(".")) {
//                    orderBookingVOList.get(position).setDbDiscountPerc(Double.parseDouble(charSequence.toString()));
//                    if (orderBookingVOList.get(position).getDbDiscountPerc() > 0) {
//                        valEditText.setEnabled(false);
//                        valEditText.setBackgroundColor(ContextCompat.getColor(con, R.color.color1));
//                    } else {
//                        valEditText.setEnabled(true);
//                        valEditText.setBackground(ContextCompat.getDrawable(con, R.drawable.txt_underline));
//                    }
//                } else {
//                    orderBookingVOList.get(position).setDbDiscountPerc(0);
//                    valEditText.setEnabled(true);
//                    valEditText.setBackground(ContextCompat.getDrawable(con, R.drawable.txt_underline));
//                }
//                if (mItemClickListener != null) {
//                    mItemClickListener.onItemClick(position, orderBookingVOList.get(position).getQuantity(), orderBookingVOList.get(position).getQuantity2(),
//                            orderBookingVOList.get(position).getUomId(), orderBookingVOList.get(position).getUomId2(), ORDER_BOOKING_QUANTITY_DIALOG,
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountAmt()),
//                            String.valueOf(orderBookingVOList.get(position).getDbDiscountPerc()));
//                }
//            } catch (NumberFormatException e) {
//                orderBookingVOList.get(position).setDbDiscountPerc(0);
//            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

}

