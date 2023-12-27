package com.botree.retailerssfa.adapters;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.util.OrderSupportUtil;

import java.util.List;
import java.util.Locale;

public class BillingSummaryAdapter extends RecyclerView.Adapter<BillingSummaryAdapter.BillingSummaryViewHolder> {

    private static final String LOG_TAG = BillingSummaryAdapter.class.getSimpleName();
    private OnItemClickListener mItemClickListener;

    private List<OrderBookingVO> orderBookingVOList;

    public BillingSummaryAdapter(List<OrderBookingVO> orderBookingVOList) {

        this.orderBookingVOList = orderBookingVOList;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public String getValueAt(int position) {
        return orderBookingVOList.get(position).getProdCode();
    }

    @NonNull
    @Override
    public BillingSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.billing_summary_list_item, parent, false);
        return new BillingSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BillingSummaryViewHolder holder, final int position) {

        OrderBookingVO orderBookingVO = orderBookingVOList.get(position);
        holder.productName.setText(orderBookingVO.getProdName());
        holder.freeQtyText.setText(String.valueOf(orderBookingVO.getOrderFreeQty()));

        holder.lTotal.setText(String.valueOf(orderBookingVO.getOrderValue().doubleValue()));
        holder.sellingPrice.setText(String.valueOf(orderBookingVO.getSellPrice()));
        holder.quantity.setText(OrderSupportUtil.getInstance().getStringQty(orderBookingVO.getQuantity()));

        holder.taxAmount.setText(String.format(Locale.getDefault(), "%.2f",
                orderBookingVO.getTax()));

        holder.groessTxt.setText(String.format(Locale.getDefault(), "%.2f",
                orderBookingVO.getlGrossAmt().doubleValue()));

        holder.dicountTxt.setText(String.format(Locale.getDefault(), "%.2f",
                orderBookingVO.getDicountAmt().doubleValue()));
        try {

            holder.lTotal.setText(String.valueOf(orderBookingVO.getOrderValue()));
        } catch (Exception e) {
            Log.e(LOG_TAG, "onBindViewHolder: " + e.getMessage(), e);
        }
    }

    @Override
    public int getItemCount() {
        return orderBookingVOList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class BillingSummaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        final View mView;
        final TextView productName;
        final TextView lTotal;
        final TextView sellingPrice;
        final TextView quantity;
        final TextView freeQtyText;
        final TextView groessTxt;
        final TextView dicountTxt;
        final TextView taxAmount;

        private BillingSummaryViewHolder(View view) {
            super(view);
            mView = view;
            productName = view.findViewById(R.id.billing_summary_product_name_txt);
            lTotal = view.findViewById(R.id.billing_summary_ltotal_txt);
            sellingPrice = view.findViewById(R.id.billing_summary_rate_txt);
            quantity = view.findViewById(R.id.billing_summary_qty_txt);
            freeQtyText = view.findViewById(R.id.billing_summary_free_qty_txt);
            groessTxt = view.findViewById(R.id.billing_summary_gross_txt);
            dicountTxt = view.findViewById(R.id.billing_summary_discount_txt);
            taxAmount = view.findViewById(R.id.billing_tax_txt);
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + productName.getText();
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

}
