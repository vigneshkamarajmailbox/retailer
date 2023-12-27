package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.FreeProdModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.support.Globals;
import com.botree.retailerssfa.util.OrderSupportUtil;

import java.util.List;
import java.util.Locale;

import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_STOCK_TAKE;
import static com.botree.retailerssfa.support.Globals.NAME_ORDER_BOOKING;

public class BillPreviewAdapter extends RecyclerView.Adapter<BillPreviewAdapter.BillPreviewViewHolder> {

    private static final String TAG = OrderSummaryAdapter.class.getSimpleName();
    private final int classType;
    private final List<List<SchemeModel>> schemeOrdersList;
    private final List<List<FreeProdModel>> allFreeProdList;
    private final LinearLayoutManager linearLayoutManager;
    private final String screenType;
    private Context con;
    private OrderSummaryAdapter.OnItemClickListener mItemClickListener;

    private List<OrderBookingVO> orderBookingVOList;
    private OrderBookingVO mLastExpand;
    private String enableQty2;

    public BillPreviewAdapter(Context context, List<OrderBookingVO> orderBookingVOList,
                              List<List<SchemeModel>> schemeOrdersList, int classType, List<List<FreeProdModel>> freeProdList,
                              LinearLayoutManager linearLayoutManager, String screenType) {

        this.orderBookingVOList = orderBookingVOList;
        this.schemeOrdersList = schemeOrdersList;
        this.allFreeProdList = freeProdList;
        this.linearLayoutManager = linearLayoutManager;
        this.con = context;
        this.screenType = screenType;
        this.classType = classType;
        enableQty2 = SFADatabase.getInstance(con).getConfigDataBasedOnName(CONFIG_STOCK_TAKE);
    }

    public void setOnItemClickListener(final OrderSummaryAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public String getValueAt(int position) {
        return orderBookingVOList.get(position).getProdCode();
    }

    @NonNull
    @Override
    public BillPreviewAdapter.BillPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_preview_adapter_layout, parent, false);
        return new BillPreviewAdapter.BillPreviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BillPreviewAdapter.BillPreviewViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            if (orderBookingVOList.get(position).isFreeProdAvail()) {
                holder.freeLnlayout.removeAllViews();
                for (FreeProdModel uniqueFreeProdList : allFreeProdList.get(position)) {
                    String freeMsg = "Free - " + " " + uniqueFreeProdList.getFreeProdName() + "\t\t\t\t\t\t " + uniqueFreeProdList.getFreeQty();

                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView tv = new TextView(con);
                    tv.setLayoutParams(lparams);
                    tv.setText(freeMsg);
                    tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    changeDiscountColor(tv);
                    holder.freeLnlayout.addView(tv);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: " + e.getMessage(), e);
        }

        holder.productName.setText(orderBookingVOList.get(position).getProdName());
        holder.lTotal.setText(String.format(Locale.getDefault(), "%.2f", getOrderValue(position)));
        holder.sellingPrice.setText(String.valueOf(getSellingPrice(position)));
//        holder.quantity.setText(String.valueOf(OrderSupportUtil.getInstance().getStringQty(orderBookingVOList.get(position).getQuantity2()) + " " +
//                replaceNullWithEmprty(orderBookingVOList.get(position).getUomId2())));
//        holder.stockQuantity.setText(String.valueOf(OrderSupportUtil.getInstance().getStringQty(orderBookingVOList.get(position).getQuantity()) + " " +
//                replaceNullWithEmprty(orderBookingVOList.get(position).getUomId())));
//
//        double grossDiscountAmt = getOrderValue(position) - orderBookingVOList.get(position).getDbDiscountAmt() - orderBookingVOList.get(position).getCashDiscAmt().doubleValue() - orderBookingVOList.get(position).getSchemeAmount().doubleValue();

        double totTaxPer = getTotTaxPer(position);

        Double totTxtAmt = getTotalTaxValue(position);

//        double netAmt = grossDiscountAmt + totTxtAmt;
//
//        holder.tvDisValue.setText(String.format(Locale.getDefault(), "%.2f", orderBookingVOList.get(position).getSchemeAmount()));
//        holder.freeQtyTxt.setText(String.valueOf(orderBookingVOList.get(position).getFreeQty()));
//        holder.dbDiscountAmtTxt.setText(String.format(Locale.getDefault(), "%.2f", orderBookingVOList.get(position).getDbDiscountAmt()));
//        holder.tvTaxAmt.setText(String.format(Locale.getDefault(), "%.2f", totTxtAmt));
//        if (orderBookingVOList.get(position).getCashDiscPerc() > 0) {
//            holder.cashDiscLbl.setText(String.valueOf("Cash Disc\n(" + String.format(Locale.getDefault(), "%.2f", orderBookingVOList.get(position).getCashDiscPerc()) + " %)"));
//        }
//        if (orderBookingVOList.get(position).getCessPerc() > 0) {
//            holder.tvTaxLabel.setText(String.valueOf("Tax(" + totTaxPer + " %\n + " + orderBookingVOList.get(position).getCessPerc() + " %)"));
//        } else {
//            holder.tvTaxLabel.setText(String.valueOf("Tax(" + totTaxPer + " %)"));
//        }
//        holder.tvTentativeLineTotal.setText(String.format(Locale.getDefault(), "%.2f", netAmt));
//        holder.cashDiscountAmt.setText(String.format(Locale.getDefault(), "%.2f", orderBookingVOList.get(position).getCashDiscAmt()));
        screenTypeVisibility(holder, position);


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
            if (Boolean.TRUE.equals(orderBookingVOList.get(position).getExpand()))
                holder.layoutSchemeDetails.setVisibility(View.VISIBLE);
            else
                holder.layoutSchemeDetails.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e(TAG, "onClick: " + e.getMessage(), e);
        }
    }

    private double getSellingPrice(int position) {
        double sellingPrice = orderBookingVOList.get(position).getSellPrice().doubleValue();
        if (sellingPrice > 0) {
            sellingPrice = orderBookingVOList.get(position).getSellPrice().doubleValue();
        } else {
//            sellingPrice = orderBookingVOList.get(position).getActualSellRate();
        }
        return sellingPrice;
    }

    private double getOrderValue(int position) {
        double orderValue;

        if (classType == 3) {
            orderValue = orderBookingVOList.get(position).getStockOrderValue().doubleValue();
        } else {
            orderValue = orderBookingVOList.get(position).getOrderValue().doubleValue();
        }

        return orderValue;
    }

    private void screenTypeVisibility(@NonNull BillPreviewAdapter.BillPreviewViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (classType == 3) {
            holder.stockQuantity.setText(String.valueOf(OrderSupportUtil.getInstance().getStringQty(orderBookingVOList.get(position).getStockCheckQty()) + " " +
                    replaceNullWithEmprty(orderBookingVOList.get(position).getStockUomId())));
            holder.quantity.setVisibility(View.INVISIBLE);
            holder.mView.findViewById(R.id.tvOrder).setVisibility(View.INVISIBLE);
            holder.layoutDiscount.setVisibility(View.GONE);
        }
        if (screenType.equalsIgnoreCase(Globals.NAME_ORDER_BOOKING)) {
            ((TextView) holder.mView.findViewById(R.id.tvStk)).setText(String.valueOf("Qty1"));
            ((TextView) holder.mView.findViewById(R.id.tvOrder)).setText(String.valueOf("Qty2"));
        } else {
            ((TextView) holder.mView.findViewById(R.id.tvStk)).setText(String.valueOf("STK"));
            ((TextView) holder.mView.findViewById(R.id.tvOrder)).setText(String.valueOf("CNF"));
        }

        if ("Y".equalsIgnoreCase(enableQty2)) {
            holder.qty2Layout.setVisibility(View.VISIBLE);
        } else {
            holder.qty2Layout.setVisibility(View.GONE);
        }
    }

    @NonNull
    private Double getTotalTaxValue(int position) {
        Double cgstValue = Double.valueOf(String.valueOf(orderBookingVOList.get(position).getCgstvalue()));
        Double sgstValue = Double.valueOf(String.valueOf(orderBookingVOList.get(position).getSgstValue()));
        Double utgstValue = Double.valueOf(String.valueOf(orderBookingVOList.get(position).getUtgstValue()));
        Double igstValue = Double.valueOf(String.valueOf(orderBookingVOList.get(position).getIgstvalue()));
//        Double cessValue = Double.valueOf(String.valueOf(orderBookingVOList.get(position).getCessValue()));
        return cgstValue + sgstValue + utgstValue + igstValue ;
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

    private void checkExpand(View view, int pos, BillPreviewAdapter.BillPreviewViewHolder holder) {
        OrderBookingVO selectedItems = orderBookingVOList.get(pos);
        View child = view.findViewById(R.id.layoutSchemeDetails);
        if ((mLastExpand != null) && (mLastExpand != selectedItems)) {
            mLastExpand.setExpand(false);
            notifyItemChanged(orderBookingVOList.indexOf(mLastExpand));
        }
        if (Boolean.TRUE.equals(selectedItems.getExpand())) {
            selectedItems.setExpand(false);
            child.setVisibility(View.GONE);
        } else {
            try {
                selectedItems.setExpand(true);
                mLastExpand = selectedItems;
                OrderSummarySchemeAdapter orderSummarySchemeAdapter = new OrderSummarySchemeAdapter(schemeOrdersList.get(pos));
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

    public class BillPreviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final View mView;
        private final TextView productName;
        private final TextView lTotal;
        private final TextView sellingPrice;
        private final TextView quantity;
        private final TextView freeQty;
        private final TextView stockQuantity;
        private final TextView tvDisValue;
        private final TextView tvTentativeLineTotal;
        private final TextView dbDiscountAmtTxt;
        private final TextView freeQtyTxt;
        private final ImageView ivDelete;
        private final RecyclerView rvSchemeDetails;
        private LinearLayout layoutDiscount;
        private LinearLayout layoutSchemeDetails;
        private LinearLayout layoutScheme;
        private TextView tvTaxAmt;
        private final TextView cashDiscLbl;
        private TextView tvTaxLabel;
        private LinearLayout qty2Layout;
        private TextView tvFreeQtyValue;
        private LinearLayout freeLnlayout;
        private LinearLayout layoutStockTake;
        private final TextView cashDiscountAmt;

        BillPreviewViewHolder(View view) {
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
            tvTentativeLineTotal = view.findViewById(R.id.tvTentativeLineTotal);
            tvTaxAmt = view.findViewById(R.id.tvTaxValue);
            tvTaxLabel = view.findViewById(R.id.tvTaxLabel);
            qty2Layout = view.findViewById(R.id.qty_2_layout);
            tvFreeQtyValue = view.findViewById(R.id.tvFreeQtyValue);
            cashDiscLbl = view.findViewById(R.id.cash_disc_label);
            ivDelete = view.findViewById(R.id.ivDelete);
            layoutDiscount = view.findViewById(R.id.layoutDiscount);
            freeLnlayout = view.findViewById(R.id.free_lnlayout);
            layoutScheme = view.findViewById(R.id.layoutScheme);
            rvSchemeDetails = view.findViewById(R.id.rvSchemeDetails);
            layoutSchemeDetails = view.findViewById(R.id.layoutSchemeDetails);
            dbDiscountAmtTxt = view.findViewById(R.id.db_discount_amt_txt);
            freeQtyTxt = view.findViewById(R.id.free_ord_qty_txt);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(con, LinearLayoutManager.VERTICAL, false);
            rvSchemeDetails.setLayoutManager(layoutManager);

            freeQty.setVisibility(View.GONE);

            productName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            lTotal.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            sellingPrice.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            quantity.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            stockQuantity.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            freeQty.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tvFreeQtyValue.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            freeQtyTxt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            cashDiscountAmt = view.findViewById(R.id.cd_discount_amt_txt);

            ivDelete.setOnClickListener(this);

            if (screenType.equalsIgnoreCase(NAME_ORDER_BOOKING) && SFADatabase.getInstance(con).getConfigDataBasedOnName(CONFIG_STOCK_TAKE).contains("N"))
                layoutStockTake.setVisibility(View.GONE);
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

