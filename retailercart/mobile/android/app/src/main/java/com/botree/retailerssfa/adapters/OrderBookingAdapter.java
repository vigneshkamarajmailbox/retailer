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

package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.botree.retailerssfa.BuildConfig;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.controller.constants.AppConstant;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.support.CertificatePinner;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.OrderSupportUtil;
import com.botree.retailerssfa.util.SFASharedPref;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_FOCUS_PRODUCT;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_MUSTSELL_PRODUCT;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_NEW_PRODUCT;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_OUT_OF_STOCK_PRODUCT;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_SCHEME_PRODUCT;
import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_STOCK_TAKE;
import static com.botree.retailerssfa.controller.constants.AppConstant.ORDER_SCREEN_TYPE_CUSTOM;
import static com.botree.retailerssfa.support.Globals.NAME_ORDER_BOOKING;
import static com.botree.retailerssfa.support.Globals.NAME_STOCK_TAKE;
import static com.botree.retailerssfa.support.Globals.NAME_VAN_SALES;
import static com.botree.retailerssfa.util.OrderSupportUtil.ALL_BRANDS;
import static com.botree.retailerssfa.util.OrderSupportUtil.ALL_CATEGORY;
import static com.botree.retailerssfa.util.OrderSupportUtil.FOCUS_BRAND;
import static com.botree.retailerssfa.util.OrderSupportUtil.MUST_SELL;
import static com.botree.retailerssfa.util.OrderSupportUtil.PROMO;
import static com.botree.retailerssfa.util.SFASharedPref.PREF_ORDER_BOOKING_OPTIONS;

public class OrderBookingAdapter extends RecyclerView.Adapter<OrderBookingAdapter.OrderBookingViewHolder> {

    private final OrderSupportUtil orderSupportUtil = OrderSupportUtil.getInstance();
    private final String callingFragment;
    private final boolean isStockTakeVisible;
    private final String mOrderBookingScreenType;
    private final SFADatabase db;
    private String url;
    private Map<String, String> configColorList;
    private Context con;
    private OrderSupportUtil.ZeroResult zeroNonZeroResult;
    private String brandValue;
    private String categoryValue;
    private OnItemClickListener mItemClickListener;
    private OnStockItemClickListener mStockItemClickListener;
    private OnCheckBoxClickListener mCheckBoxClickListener;
    private OnItemLongClickListener monItemLongClicked;
    private List<OrderBookingVO> orderBookingVOList;
    private List<OrderBookingVO> checkedOrderList = new ArrayList<>();
    private List<OrderBookingVO> searchList = new ArrayList<>();
    private String brandCode;
    private String callingFragmentName;
    private OnImageItemClickListener imageItemClickedListener;


    public OrderBookingAdapter(Context context, List<OrderBookingVO> orderBookingVOLists,
                                String callingFragment, String fragmentName) {
        con = context;
        this.orderBookingVOList = orderBookingVOLists;
        this.searchList.addAll(orderBookingVOList);
        this.callingFragment = callingFragment;
        this.mOrderBookingScreenType = SFASharedPref.getOurInstance().readString(PREF_ORDER_BOOKING_OPTIONS);
        configColorList = AppUtils.getOurInstance().getConfigColorList(context, new String[]{CONFIG_NEW_PRODUCT, CONFIG_OUT_OF_STOCK_PRODUCT, CONFIG_FOCUS_PRODUCT,
                CONFIG_MUSTSELL_PRODUCT, CONFIG_SCHEME_PRODUCT});

        for (int i = 0; i < orderBookingVOList.size(); i++) {
            if (orderBookingVOList.get(i).getUomId() == null)
                orderBookingVOList.get(i).setUomId(orderBookingVOList.get(i).getDefaultUomid());
            if (orderBookingVOList.get(i).getStockUomId() == null)
                orderBookingVOList.get(i).setStockUomId(orderBookingVOList.get(i).getDefaultUomid());
        }
        if ((NAME_ORDER_BOOKING.equalsIgnoreCase(callingFragment) || NAME_VAN_SALES.equalsIgnoreCase(callingFragment))
                && ORDER_SCREEN_TYPE_CUSTOM.equalsIgnoreCase(mOrderBookingScreenType)) {
            url = context.getResources().getString(R.string.BASE_URL_RELEASE) + "controller/getproductimage/";
            if (BuildConfig.DEBUG) {
                url = context.getResources().getString(R.string.BASE_URL_DEBUG) + "controller/getproductimage/";
            }
        }
        db = SFADatabase.getInstance(context);
        isStockTakeVisible = db.getConfigDataBasedOnName(CONFIG_STOCK_TAKE).equalsIgnoreCase("Y");

        callingFragmentName = fragmentName;

    }


    public void setOnImageItemClickListener(OnImageItemClickListener listener) {
        this.imageItemClickedListener = listener;
    }

    public void setOnStockItemClickListener(OnStockItemClickListener mStockItemClickListener) {
        this.mStockItemClickListener = mStockItemClickListener;

    }

    public void setOnCheckBoxClickListener(OnCheckBoxClickListener mCheckBoxClickListener) {
        this.mCheckBoxClickListener = mCheckBoxClickListener;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener mItemLongClicked) {
        this.monItemLongClicked = mItemLongClicked;
    }

    public String getValueAt(int position) {
        return orderBookingVOList.get(position).getProdCode();
    }

    public void updateQtyToUI(final int position, double qty, double orderValue, String remarks, String uomId) {

        OrderBookingVO orderBookingVO = orderBookingVOList.get(position);

        int idx = searchList.indexOf(orderBookingVO);
        searchList.remove(idx);
        orderBookingVO.setQuantity(qty);
        orderBookingVO.setStockCheckQty(orderBookingVO.getStockCheckQty());
        orderBookingVO.setStockUomId(orderBookingVO.getStockUomId());
        orderBookingVO.setOrderValue(BigDecimal.valueOf(orderValue));
        orderBookingVO.setRemarks(remarks);
        orderBookingVO.setUomId(uomId);

        searchList.add(idx, orderBookingVO);
        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "updateQtyToUI: " + e.getMessage(), e);
        }
    }

    public void updateStockQtyToUI(final int position, double qty, String stockUomId) {

        OrderBookingVO orderBookingVO = orderBookingVOList.get(position);

        int idx = searchList.indexOf(orderBookingVO);
        searchList.remove(idx);
        orderBookingVO.setQuantity(orderBookingVO.getQuantity());
        orderBookingVO.setStockCheckQty(qty);
        orderBookingVO.setStockUomId(stockUomId);
        orderBookingVO.setOrderValue(orderBookingVO.getOrderValue());
        orderBookingVO.setRemarks(orderBookingVO.getRemarks());
        orderBookingVO.setUomId(orderBookingVO.getUomId());

        if (qty > 0) {
            orderBookingVO.setItemSelected(true);
            orderBookingVO.setCheckBoxEnabled(false);
        } else {
            orderBookingVO.setItemSelected(false);
            orderBookingVO.setCheckBoxEnabled(true);
        }

        searchList.add(idx, orderBookingVO);
        try {
            notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "updateStockQtyToUI: " + e.getMessage(), e);
        }
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
        View view;
        if ((NAME_ORDER_BOOKING.equalsIgnoreCase(callingFragment) || NAME_VAN_SALES.equalsIgnoreCase(callingFragment)) && ORDER_SCREEN_TYPE_CUSTOM.equalsIgnoreCase(mOrderBookingScreenType)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderbooking_imagebase_list_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderbooking_list_item, parent, false);
        }
        return new OrderBookingViewHolder(view);
    }

    private void setBackgroundColor(final OrderBookingViewHolder holder, final int color) {
        holder.productName.setTextColor(color);
        holder.productName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderBookingViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        setViewStyles(holder, position);

        holder.productName.setText(orderBookingVOList.get(position).getProdName()+" ("+orderBookingVOList.get(position).getProdBatchCode()+")");
        holder.mrp.setText(String.valueOf(orderBookingVOList.get(position).getMrp()));
        if(orderBookingVOList.get(position).getPpq() == null || orderBookingVOList.get(position).getPpq().isEmpty()){
            holder.ppq.setText("PPQ : -");
        }else {
            holder.ppq.setText(String.valueOf("PPG : "+orderBookingVOList.get(position).getPpq().replaceAll("~",",")));
        }
        holder.sellingPrice.setText(MessageFormat.format("{0} {1}", con.getString(R.string.Rs), String.valueOf(orderBookingVOList.get(position).getSellPrice())));
        holder.quantity.setText(String.valueOf(orderSupportUtil.getStringQty(orderBookingVOList.get(position).getQuantity())));
        holder.stockChk.setText(String.valueOf(orderSupportUtil.getStringQty(orderBookingVOList.get(position).getStockCheckQty())));

        holder.mTvUom.setText(getOrderQtyUom(position));
        holder.mTvStockUom.setText(getStockQtyUom(position));

        if ((callingFragment.equalsIgnoreCase(NAME_ORDER_BOOKING)
                || callingFragment.equalsIgnoreCase(NAME_VAN_SALES))
                && mOrderBookingScreenType.equalsIgnoreCase(ORDER_SCREEN_TYPE_CUSTOM)) {
            String localUrl = url + orderBookingVOList.get(position).getProdCode() + "/jpg";
            holder.stockInHandTxt.setText(MessageFormat.format("SIH : {0}", String.valueOf(orderBookingVOList.get(position).getStockInHand())));
            holder.soqTxt.setText(MessageFormat.format("SOQ : {0}", String.valueOf(orderBookingVOList.get(position).getSuggestedQty())));

            CertificatePinner.getInstCertificate().getCustomPicasso(con)
                    .load(localUrl)
                    .fit()
                    .placeholder(R.drawable.sfa_placeholder)
                    .error(R.drawable.sfa_placeholder)
                    .into(holder.productImg);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageItemClickedListener != null) {
                        imageItemClickedListener.onImageItemClicked(v, position);
                    }
                }
            });

            siqAndSihConfig(holder);

        } else {
            setCheckBoxItemSelected(position);
            holder.stockCheckbox.setChecked(orderBookingVOList.get(position).isItemSelected());
            holder.stockCheckbox.setEnabled(orderBookingVOList.get(position).isCheckBoxEnabled());
            holder.stockCheckbox.setTag(position);

            holder.stockCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBoxLogic(holder.getAdapterPosition());
                    notifyItemChanged(position);
                }
            });

            if (callingFragment.equalsIgnoreCase(NAME_STOCK_TAKE)) {
                holder.quantity.setVisibility(View.GONE);
                holder.mTvUom.setVisibility(View.GONE);
                if (callingFragmentName.equalsIgnoreCase("StockTakeFragment")) {
                    holder.stockCheckbox.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void siqAndSihConfig(@NonNull OrderBookingViewHolder holder) {
        if ("N".equalsIgnoreCase(db.getConfigDataBasedOnName(AppConstant.Configurations.CONFIG_SOQ))) {
            holder.soqTxt.setVisibility(View.GONE);
        }
    }

    private String getStockQtyUom(@SuppressLint("RecyclerView") int position) {
        if (orderBookingVOList.get(position).getStockUomId() != null
                && !orderBookingVOList.get(position).getStockUomId().isEmpty()) {

            return String.valueOf(orderBookingVOList.get(position).getStockUomId());
        } else {
            return String.valueOf(orderBookingVOList.get(position).getDefaultUomid());
        }
    }

    private String getOrderQtyUom(@SuppressLint("RecyclerView") int position) {
        if (orderBookingVOList.get(position).getUomId() != null
                && !orderBookingVOList.get(position).getUomId().isEmpty()) {
            return String.valueOf(orderBookingVOList.get(position).getUomId());
        } else {
            return String.valueOf(orderBookingVOList.get(position).getDefaultUomid());
        }
    }

    private void setCheckBoxItemSelected(@SuppressLint("RecyclerView") int position) {
        if (orderBookingVOList.get(position).isItemSelected()) {

            if (!checkedOrderList.contains(orderBookingVOList.get(position))) {
                checkedOrderList.add(orderBookingVOList.get(position));
            }

            if (mCheckBoxClickListener != null) {
                mCheckBoxClickListener.onCheckBoxClick(checkedOrderList);
            }

        } else {

            if (checkedOrderList.contains(orderBookingVOList.get(position))) {
                checkedOrderList.remove(orderBookingVOList.get(position));
                Log.e("setCheckBoxItemctedelse", orderBookingVOList.get(position).getBrandName());
            }

            if (mCheckBoxClickListener != null) {
                mCheckBoxClickListener.onCheckBoxClick(checkedOrderList);
            }

        }
    }

    private void setViewStyles(@NonNull OrderBookingViewHolder holder, int position) {
        if ((callingFragment.equalsIgnoreCase(NAME_ORDER_BOOKING) || callingFragment.equalsIgnoreCase(NAME_VAN_SALES))) {

            setBackgroundColor(holder,
                    ContextCompat.getColor(con, R.color.transparent));

            if (PROMO.equalsIgnoreCase(orderBookingVOList.get(position)
                    .getProductStatus())) {
                setBackgroundColor(holder, Color.parseColor(getColorCode(configColorList.get(CONFIG_SCHEME_PRODUCT))));

            } else if (orderBookingVOList.get(position).getStockInHand() <= 0) {
                setBackgroundColor(holder, Color.parseColor(getColorCode(configColorList.get(CONFIG_OUT_OF_STOCK_PRODUCT))));

            } else if (MUST_SELL.equalsIgnoreCase(orderBookingVOList.get(position)
                    .getMustcategory())) {
                setBackgroundColor(holder, Color.parseColor(getColorCode(configColorList.get(CONFIG_MUSTSELL_PRODUCT))));

            } else if (FOCUS_BRAND.equalsIgnoreCase(orderBookingVOList.get(position)
                    .getFocusCategory())) {
                setBackgroundColor(holder, Color.parseColor(getColorCode(configColorList.get(CONFIG_FOCUS_PRODUCT))));

            } else {
                setBackgroundColor(holder, ContextCompat.getColor(con, R.color.black)); // light black
            }

        }
    }

    private void checkBoxLogic(Integer pos) {
        if (orderBookingVOList.get(pos).isItemSelected()) {

            //uncheck
            orderBookingVOList.get(pos).setItemSelected(false);

            OrderBookingVO orderBookingVO = orderBookingVOList.get(pos);
            setCheckedQtyToEditText(orderBookingVO, 0);

            if (checkedOrderList.contains(orderBookingVOList.get(pos))) {
                int removeIndex = checkedOrderList.indexOf(orderBookingVOList.get(pos));
                checkedOrderList.remove(removeIndex);
            }

            if (mCheckBoxClickListener != null) {
                mCheckBoxClickListener.onCheckBoxClick(checkedOrderList);
            }

        } else {
            orderBookingVOList.get(pos).setItemSelected(true);
            OrderBookingVO orderBookingVO = orderBookingVOList.get(pos);
            setCheckedQtyToEditText(orderBookingVO, 1);

            if (checkedOrderList.contains(orderBookingVOList.get(pos))) {
                int removeIndex = checkedOrderList.indexOf(orderBookingVOList.get(pos));
                checkedOrderList.remove(removeIndex);

            }

            checkedOrderList.add(orderBookingVOList.get(pos));

            if (mCheckBoxClickListener != null) {
                mCheckBoxClickListener.onCheckBoxClick(checkedOrderList);
            }
        }
    }

    private void setCheckedQtyToEditText(OrderBookingVO orderBookingVO, int qty) {
        Integer orderConvertedQty = OrderSupportUtil.getInstance().calcUomConversionQty(orderBookingVO, orderBookingVO.getDefaultUomid(), qty);
        orderBookingVO.setStockCheckQty(qty);
        orderBookingVO.setStockConversionFactor(orderConvertedQty);
        double orderValue = OrderSupportUtil.getInstance().calculateLineOrderValue(orderBookingVO, orderBookingVO.getDefaultUomid(), qty);
        orderBookingVO.setStockOrderValue(BigDecimal.valueOf(orderValue));
        orderBookingVO.setStockUomId(orderBookingVO.getDefaultUomid());
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

            if ((bookingVO.getFocusCategory() != null && bookingVO.getFocusCategory().equalsIgnoreCase(categoryValue))
                    || (bookingVO.getMustcategory() != null && bookingVO.getMustcategory().equalsIgnoreCase(categoryValue))
                    || (bookingVO.getProductStatus() != null && bookingVO.getProductStatus().equalsIgnoreCase(categoryValue))) {
                return false;
            }

            Log.d(TAG, "isBrandValidationSuccess: " + bookingVO.getFocusCategory() + " | " + categoryValue);

            return true;
        }

        return false;
    }

    private void applyFilterAndAdd(String filterText) {
        orderBookingVOList.clear();

        if (isAllFilterEmpty(filterText) || filterText.contains("all")) {
            orderBookingVOList.addAll(searchList);
            notifyDataSetChanged();
            return;
        }

        for (OrderBookingVO bookingVO : searchList) {

            if (checkFilterText(filterText, bookingVO)) {
                if (getBrandCatFilterResult(bookingVO)) continue;
                zeroNonzeroFilterResult(bookingVO);
            }
        }
        notifyDataSetChanged();
    }

    private boolean getBrandCatFilterResult(OrderBookingVO bookingVO) {
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


    private void zeroNonzeroFilterResult(OrderBookingVO bookingVO) {
        if (zeroNonZeroResult == OrderSupportUtil.ZeroResult.ALL_PRODUCTS) {
            orderBookingVOList.add(bookingVO);
        } else if (zeroNonZeroResult == OrderSupportUtil.ZeroResult.ZERO_PRODUCTS) {
            if (bookingVO.getStockInHand() == 0)
                orderBookingVOList.add(bookingVO);
        } else {
            if (bookingVO.getStockInHand() > 0)
                orderBookingVOList.add(bookingVO);
        }
    }


    private boolean checkFilterText(String filterText, OrderBookingVO orderBookingVO) {

        return filterText.length() == 0 || (orderBookingVO.getProdName().toLowerCase(Locale.getDefault()).contains(filterText));

    }

    /**
     * Method helps to check Not Null for Color
     *
     * @param color currunt color
     * @return if color null then user #000000(black Color)
     */
    private String getColorCode(String color) {
        if (color != null && !color.isEmpty())
            return color;
        else
            return "#000000";
    }

    // Filter Class
    public void applySearchFilter(String charText) {
        applyFilterAndAdd(charText.toLowerCase(Locale.getDefault()));
    }

    public void applyFilter(OrderSupportUtil.ZeroResult zeroNonZeroResult, String brandValue, String brandCode, String categoryValue) {
        this.zeroNonZeroResult = zeroNonZeroResult;
        this.brandValue = brandValue;
        this.brandCode = brandCode;
        this.categoryValue = categoryValue;
        applyFilterAndAdd("");
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull OrderBookingViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if ((callingFragment.equalsIgnoreCase(NAME_ORDER_BOOKING) || callingFragment.equalsIgnoreCase(NAME_VAN_SALES))
                && mOrderBookingScreenType.equalsIgnoreCase(ORDER_SCREEN_TYPE_CUSTOM))
            unbindDrawables(holder.productImg);
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    public List<OrderBookingVO> getOrderBookingVOList() {
        return orderBookingVOList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnImageItemClickListener {
        void onImageItemClicked(View v, int position);
    }

    public interface OnStockItemClickListener {
        void onStockItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(View view, int position);
    }

    public interface OnCheckBoxClickListener {
        void onCheckBoxClick(List<OrderBookingVO> checkedBookingList);
    }

    class OrderBookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView productName;
        private final TextView mrp;
        private final TextView ppq;
        private final TextView sellingPrice;
        private final TextView quantity;
        private final TextView mTvUom;
        private final TextView mTvStockUom;
        private final TextView stockChk;
        private View stockLineView;
        private CheckBox stockCheckbox;
        private TextView stockInHandTxt;
        private TextView soqTxt;
        private ImageView productImg;
        private LinearLayout stockLayout;
        private LinearLayout orderLayout;

        OrderBookingViewHolder(View view) {
            super(view);

            productName = view.findViewById(R.id.product_name_txt);
            mrp = view.findViewById(R.id.mrp_txt);
            ppq = view.findViewById(R.id.ppq_txt);
            sellingPrice = view.findViewById(R.id.selling_price_txt);
            quantity = view.findViewById(R.id.quantity_txt);
            mTvUom = view.findViewById(R.id.tvUom);
            stockChk = view.findViewById(R.id.stock_chk);
            mTvStockUom = view.findViewById(R.id.tvStockUom);

            if ((NAME_ORDER_BOOKING.equalsIgnoreCase(callingFragment) || NAME_VAN_SALES.equalsIgnoreCase(callingFragment))
                    && ORDER_SCREEN_TYPE_CUSTOM.equalsIgnoreCase(mOrderBookingScreenType)) {
                stockLineView = view.findViewById(R.id.stockLineView);
                productImg = view.findViewById(R.id.product_img);
                stockLayout = view.findViewById(R.id.stock_take_lnlayout);
                orderLayout = view.findViewById(R.id.order_take_lnlayout);
                stockInHandTxt = view.findViewById(R.id.single_prod_stoch_in_hand_txt);
                soqTxt = view.findViewById(R.id.single_prod_soq_txt);
                orderLayout.setOnClickListener(this);
                stockLayout.setOnClickListener(this);
                stockChk.setVisibility(View.GONE);
                mTvStockUom.setVisibility(View.GONE);
                stockLineView.setVisibility(View.GONE);
                if ((NAME_ORDER_BOOKING.equalsIgnoreCase(callingFragment) && isStockTakeVisible)) {
                    stockChk.setVisibility(View.VISIBLE);
                    mTvStockUom.setVisibility(View.VISIBLE);
                    stockLineView.setVisibility(View.VISIBLE);
                }
            } else {
                if (NAME_ORDER_BOOKING.equalsIgnoreCase(callingFragment)) {
                    if (isStockTakeVisible) {
                        stockChk.setVisibility(View.VISIBLE);
                        mTvStockUom.setVisibility(View.VISIBLE);
                    } else {
                        stockChk.setVisibility(View.GONE);
                        mTvStockUom.setVisibility(View.GONE);
                    }
                } else if (callingFragment.equalsIgnoreCase(NAME_VAN_SALES)) {
                    stockChk.setVisibility(View.GONE);
                    mTvStockUom.setVisibility(View.GONE);
                }
                stockCheckbox = view.findViewById(R.id.stock_checkbox);

                quantity.setOnClickListener(this);
                stockChk.setOnClickListener(this);

            }
            view.setOnLongClickListener(this);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + productName.getText();
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.stock_chk || i == R.id.stock_take_lnlayout) {
                if (mStockItemClickListener != null) {
                    mStockItemClickListener.onStockItemClick(v, getAdapterPosition());
                }

            } else if ((i == R.id.quantity_txt || i == R.id.order_take_lnlayout) && mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }

        }

        @Override
        public boolean onLongClick(View view) {

            if (monItemLongClicked != null) {
                monItemLongClicked.onItemLongClicked(view, getAdapterPosition());
            }
            return false;
        }
        
    }
}

