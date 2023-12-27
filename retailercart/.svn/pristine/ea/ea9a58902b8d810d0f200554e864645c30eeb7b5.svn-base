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
    import android.graphics.Typeface;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.core.content.ContextCompat;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.botree.retailerssfa.R;
    import com.botree.retailerssfa.db.FileAccessUtil;
    import com.botree.retailerssfa.db.SFADatabase;
    import com.botree.retailerssfa.models.FreeProdModel;
    import com.botree.retailerssfa.models.OrderBookingVO;
    import com.botree.retailerssfa.models.SchemeModel;
    import com.botree.retailerssfa.support.Globals;
    import com.botree.retailerssfa.util.OrderSupportUtil;

    import java.util.List;
    import java.util.Locale;

    import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_IS_PTR_WITH_TAX;
    import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_NET_RETAILER_MARGIN;
    import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_STOCK_TAKE;
    import static com.botree.retailerssfa.support.Globals.NAME_ORDER_BOOKING;

    public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.OrderSummaryViewHolder> {

        private static final String TAG = OrderSummaryAdapter.class.getSimpleName();
        private final int classType;
        private final List<List<SchemeModel>> schemeOrdersList;
        private final List<List<FreeProdModel>> allFreeProdList;
        private final LinearLayoutManager linearLayoutManager;
        private final String screenType;
        private Context con;
        private OnItemClickListener mItemClickListener;

        private List<OrderBookingVO> orderBookingVOList;
        private OrderBookingVO mLastExpand;
        private SFADatabase db;
        private boolean isPTRWithTax = false;

        public OrderSummaryAdapter(Context context, List<OrderBookingVO> orderBookingVOList,
                                   List<List<SchemeModel>> schemeOrdersList, int classType, List<List<FreeProdModel>> freeProdList,
                                   LinearLayoutManager linearLayoutManager, String screenType) {

            this.orderBookingVOList = orderBookingVOList;
            this.schemeOrdersList = schemeOrdersList;
            this.allFreeProdList = freeProdList;
            this.linearLayoutManager = linearLayoutManager;
            this.con = context;
            this.screenType = screenType;
            this.classType = classType;
            this.db = SFADatabase.getInstance(context);

            if("Y".equalsIgnoreCase(db.getConfigDataBasedOnName(CONFIG_IS_PTR_WITH_TAX))){
                isPTRWithTax = true;
            }
        }

        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;
        }

        public String getValueAt(int position) {
            return orderBookingVOList.get(position).getProdCode();
        }

        @NonNull
        @Override
        public OrderSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_summary_list_item, parent, false);
            return new OrderSummaryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final OrderSummaryViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            try {
                holder.layoutScheme.setVisibility(View.GONE);
                if (orderBookingVOList.get(position).isFreeProdAvail()) {
                    holder.freeLnlayout.removeAllViews();
                    for (FreeProdModel uniqueFreeProdList : allFreeProdList.get(position)) {
                        String freeMsg = "Free - " + " " + uniqueFreeProdList.getFreeProdName() + "\t\t\t\t\t\t " + uniqueFreeProdList.getFreeQty();

                        TextView tv = new TextView(con);
                        tv.setLayoutParams(FileAccessUtil.createWrapWrap());
                        tv.setText(freeMsg);
                        tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        changeDiscountColor(tv);
                        holder.freeLnlayout.addView(tv);
                    }
                }

            } catch (Exception e) {
                Log.e(TAG, "onBindViewHolder: " + e.getMessage(), e);
            }

            Double sellingPrice = orderBookingVOList.get(position).getSellPrice().doubleValue();
            Double orderValue;

            if (classType == 3) {
                orderValue = orderBookingVOList.get(position).getStockOrderValue().doubleValue();
            } else {
                orderValue = orderBookingVOList.get(position).getOrderValue().doubleValue();
            }

            holder.productName.setText(orderBookingVOList.get(position).getProdName());
            holder.lTotal.setText(String.format(Locale.getDefault(), "%.2f", orderValue));
            holder.sellingPrice.setText(String.valueOf(sellingPrice));
            holder.quantity.setText(String.valueOf(OrderSupportUtil.getInstance().getStringQty(orderBookingVOList.get(position).getQuantity()) + " " +
                    replaceNullWithEmprty(orderBookingVOList.get(position).getUomId())));
            holder.stockQuantity.setText(String.valueOf(OrderSupportUtil.getInstance().getStringQty(orderBookingVOList.get(position).getStockCheckQty()) + " " +
                    replaceNullWithEmprty(orderBookingVOList.get(position).getStockUomId())));

            double totTaxPer = getTotTaxPer(position);

            Double totTxtAmt = getTotalTaxValue(position);

            double schemeAmount = (orderValue - orderBookingVOList.get(position).getPrimDiscOrderValue().doubleValue()) + orderBookingVOList.get(position).getSchemeAmount().doubleValue();
            holder.tvDisValue.setText(String.format(Locale.getDefault(), "%.2f", schemeAmount));

            holder.tvTaxAmt.setText(String.format(Locale.getDefault(), "%.2f", totTxtAmt));
            holder.tvTaxLabel.setText(String.valueOf("Tax Percentage (" + totTaxPer + " %)"));

            holder.tvWithTaxLabel.setText(String.valueOf("Tax Percentage (" + totTaxPer + " %)"));
            holder.tvWithTaxValue.setText(String.format(Locale.getDefault(), "%.2f", totTxtAmt));

            holder.tvTentativeLineTotal.setText(String.format(Locale.getDefault(), "%.2f", orderBookingVOList.get(position).getTotalAmount()));

            holder.taxableValTxt.setText(String.format(Locale.getDefault(), "%.2f", orderBookingVOList.get(position).getlGrossAmt()));

            screenTypeVisibility(holder, position);

            setRetailerMarginValue(holder, position);

            try {

                if (!schemeOrdersList.get(position).isEmpty()) {
                    holder.layoutScheme.setVisibility(View.VISIBLE);
                    holder.layoutScheme.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                checkExpand(holder.itemView, position, holder);
                            } catch (Exception e) {
                                Log.e(TAG, "onClick: " + e.getMessage(), e);
                            }
                        }
                    });
                }
                if (orderBookingVOList.get(position).getExpand())
                    holder.layoutSchemeDetails.setVisibility(View.VISIBLE);
                else
                    holder.layoutSchemeDetails.setVisibility(View.GONE);
            } catch (Exception e) {
                Log.e(TAG, "onClick: " + e.getMessage(), e);
            }
        }

        private void setRetailerMarginValue(@NonNull OrderSummaryViewHolder holder, @SuppressLint("RecyclerView") int position) {
            try {

                double netRetailerMargin = (((orderBookingVOList.get(position).getMrp().doubleValue()
                        - (orderBookingVOList.get(position).getSellPrice().doubleValue()
                        - orderBookingVOList.get(position).getSchemeAmount().doubleValue())) * 100)
                        / (orderBookingVOList.get(position).getSellPrice().doubleValue()
                        - orderBookingVOList.get(position).getSchemeAmount().doubleValue()));

                holder.netMarginValTxt.setText(String.format(Locale.getDefault(), "%.2f", netRetailerMargin));

            }catch (Exception e){
                Log.e(TAG, "onBindViewHolder: "+e.getMessage(),e );
            }
        }

        private void screenTypeVisibility(@NonNull OrderSummaryViewHolder holder, @SuppressLint("RecyclerView") int position) {
            if (classType == 3) {
                holder.stockQuantity.setText(String.valueOf(OrderSupportUtil.getInstance().getStringQty(orderBookingVOList.get(position).getStockCheckQty()) + " " +
                        replaceNullWithEmprty(orderBookingVOList.get(position).getStockUomId())));
                holder.quantity.setVisibility(View.INVISIBLE);
                holder.mView.findViewById(R.id.tvOrder).setVisibility(View.INVISIBLE);
                holder.layoutDiscount.setVisibility(View.GONE);
            }
            if (screenType.equalsIgnoreCase(Globals.NAME_ORDER_BOOKING)) {
                ((TextView) holder.mView.findViewById(R.id.tvStk)).setText(String.valueOf("STK"));
                ((TextView) holder.mView.findViewById(R.id.tvOrder)).setText(String.valueOf("ORD"));
            } else {
                ((TextView) holder.mView.findViewById(R.id.tvStk)).setText(String.valueOf("STK"));
                ((TextView) holder.mView.findViewById(R.id.tvOrder)).setText(String.valueOf("CNF"));
            }
        }

        @NonNull
        private Double getTotalTaxValue(int position) {
            Double cgstValue = Double.valueOf(String.valueOf(orderBookingVOList.get(position).getCgstvalue()));
            Double sgstValue = Double.valueOf(String.valueOf(orderBookingVOList.get(position).getSgstValue()));
            Double utgstValue = Double.valueOf(String.valueOf(orderBookingVOList.get(position).getUtgstValue()));
            Double igstValue = Double.valueOf(String.valueOf(orderBookingVOList.get(position).getIgstvalue()));
            return cgstValue + sgstValue + utgstValue + igstValue;
        }

        private double getTotTaxPer(int position) {
            double cgstPerc = orderBookingVOList.get(position).getCgstperc();
            double sgstPerc = orderBookingVOList.get(position).getSgstPerc();
            double utgstPerc = orderBookingVOList.get(position).getUtgstPerc();
            double igstPerc = orderBookingVOList.get(position).getIgstPerc();
            return cgstPerc + sgstPerc + utgstPerc + igstPerc;
        }

        private void changeDiscountColor(TextView... tvDisValue) {
            for (TextView text : tvDisValue) {
                text.setTextColor(ContextCompat.getColor(con, R.color.color_blue_maps_line));
            }
        }

        private String replaceNullWithEmprty(String uomId) {
            if (uomId == null)
                return "";
            else
                return uomId;
        }

        @Override
        public int getItemCount() {
            return orderBookingVOList.size();
        }

        private void checkExpand(View view, int pos, OrderSummaryViewHolder holder) {
            OrderBookingVO selectedItems = orderBookingVOList.get(pos);
            View child = view.findViewById(R.id.layoutSchemeDetails);
            if ((mLastExpand != null) && (mLastExpand != selectedItems)) {
                mLastExpand.setExpand(false);
                notifyItemChanged(orderBookingVOList.indexOf(mLastExpand));
            }
            if (selectedItems.getExpand()) {
                selectedItems.setExpand(false);
                child.setVisibility(View.GONE);
            } else {
                try {
                    selectedItems.setExpand(true);
                    mLastExpand = selectedItems;
                    OrderSummarySchemeAdapter orderSummarySchemeAdapter = new OrderSummarySchemeAdapter(
                            schemeOrdersList.get(pos));
                    if (orderSummarySchemeAdapter.getItemCount() > 0) {
                        child.setVisibility(View.VISIBLE);
                        holder.rvSchemeDetails.setAdapter(orderSummarySchemeAdapter);
                    }
                    linearLayoutManager.scrollToPositionWithOffset(pos, 1);
                } catch (Exception e) {
                    Log.e(TAG, "checkExpand: " + e.getMessage(), e);
                }
            }
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public class OrderSummaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            private final View mView;
            private final TextView productName;
            private final TextView lTotal;
            private final TextView sellingPrice;
            private final TextView quantity;
            private final TextView freeQty;
            private final TextView stockQuantity;
            private final TextView tvDisValue;
            private final TextView tvTentative;
            private final TextView tvTentativeLineTotal;
            private final TextView tvTentativeLineValue;
            private final ImageView ivDelete;
            private final RecyclerView rvSchemeDetails;
            private LinearLayout layoutDiscount;
            private LinearLayout layoutSchemeDetails;
            private LinearLayout layoutScheme;
            private TextView tvTaxAmt;
            private TextView tvTaxLabel;
            private TextView tvFreeQtyValue;
            private LinearLayout freeLnlayout;
            private LinearLayout layoutStockTake;

            private LinearLayout taxableLayout;
            private LinearLayout ptrWithLayout;
            private LinearLayout ptrWithOutLayout;

            private TextView taxableValTxt;
            private TextView taxableLabelTxt;
            private TextView tvWithTaxValue;
            private TextView tvWithTaxLabel;

            private TextView netMarginValTxt;

            OrderSummaryViewHolder(View view) {
                super(view);
                mView = view;
                productName = view.findViewById(R.id.order_product_name_txt);
                lTotal = view.findViewById(R.id.order_ltotal_txt);
                sellingPrice = view.findViewById(R.id.order_rate_txt);
                quantity = view.findViewById(R.id.order_qty_txt);
                freeQty = view.findViewById(R.id.free_qty_txt);
                stockQuantity = view.findViewById(R.id.stock_qty_txt);
                layoutStockTake = view.findViewById(R.id.layoutStockTake);
                tvDisValue = view.findViewById(R.id.tvDisValue);
                tvTentative = view.findViewById(R.id.tvTentative);
                tvTentativeLineValue = view.findViewById(R.id.tvTentativeLine);
                tvTentativeLineTotal = view.findViewById(R.id.tvTentativeLineTotal);
                tvTaxAmt = view.findViewById(R.id.tvTaxValue);
                tvTaxLabel = view.findViewById(R.id.tvTaxLabel);
                tvFreeQtyValue = view.findViewById(R.id.tvFreeQtyValue);
                ivDelete = view.findViewById(R.id.ivDelete);
                layoutDiscount = view.findViewById(R.id.layoutDiscount);
                freeLnlayout = view.findViewById(R.id.free_lnlayout);
                layoutScheme = view.findViewById(R.id.layoutScheme);
                rvSchemeDetails = view.findViewById(R.id.rvSchemeDetails);
                layoutSchemeDetails = view.findViewById(R.id.layoutSchemeDetails);

                taxableLayout = view.findViewById(R.id.taxable_layout);
                ptrWithLayout = view.findViewById(R.id.ptr_with_txt_layout);
                ptrWithOutLayout = view.findViewById(R.id.ptr_without_txt_layout);

                taxableValTxt = view.findViewById(R.id.taxable_val_txt);
                taxableLabelTxt = view.findViewById(R.id.taxable_label_txt);

                tvWithTaxLabel = view.findViewById(R.id.tvWithTaxLabel);
                tvWithTaxValue = view.findViewById(R.id.tvWithTaxValue);

                netMarginValTxt = view.findViewById(R.id.net_margin_val_txt);
                LinearLayout netMarginLayout = view.findViewById(R.id.retailer_net_margin_layout);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(con, RecyclerView.VERTICAL, false);
                rvSchemeDetails.setLayoutManager(layoutManager);

                freeQty.setVisibility(View.GONE);

                productName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                lTotal.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                sellingPrice.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                quantity.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                stockQuantity.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                freeQty.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvDisValue.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTentative.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                tvTentativeLineValue.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTentativeLineTotal.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTaxAmt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvTaxLabel.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvFreeQtyValue.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                taxableValTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                taxableLabelTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                tvWithTaxLabel.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvWithTaxValue.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                netMarginValTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                ivDelete.setOnClickListener(this);

                if (screenType.equalsIgnoreCase(NAME_ORDER_BOOKING)
                        && SFADatabase.getInstance(con).getConfigDataBasedOnName(CONFIG_STOCK_TAKE).contains("N")) {
                    layoutStockTake.setVisibility(View.GONE);
                }

                if(screenType.equalsIgnoreCase(NAME_ORDER_BOOKING) &&
                        "Y".equalsIgnoreCase(SFADatabase.getInstance(con).getConfigDataBasedOnName(CONFIG_NET_RETAILER_MARGIN))){
                    netMarginLayout.setVisibility(View.VISIBLE);
                }else {
                    netMarginLayout.setVisibility(View.GONE);
                }

                if(isPTRWithTax){
                    taxableLayout.setVisibility(View.VISIBLE);
                    lTotal.setVisibility(View.INVISIBLE);
                    ptrWithOutLayout.setVisibility(View.GONE);
                    ptrWithLayout.setVisibility(View.VISIBLE);
                }else {
                    taxableLayout.setVisibility(View.GONE);
                    ptrWithLayout.setVisibility(View.GONE);
                    ptrWithOutLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public String toString() {
                return super.toString() + " '" + productName.getText();
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.ivDelete && mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        }

    }
