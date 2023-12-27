package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.util.OrderSupportUtil;

import java.util.List;
import java.util.Locale;

public class NewOpeningStockSummaryAdapter extends RecyclerView.Adapter<NewOpeningStockSummaryAdapter.OrderSummaryViewHolder> {
    private OnItemClickListener mItemClickListener;
    private List<OrderBookingVO> orderBookingVOList;

    public NewOpeningStockSummaryAdapter(List<OrderBookingVO> orderBookingVOList) {
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
    public OrderSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_stock_summary_list_item, parent, false);
        return new OrderSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderSummaryViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.productName.setText(orderBookingVOList.get(holder.getAdapterPosition()).getProdName());
        holder.batchCode.setText(orderBookingVOList.get(holder.getAdapterPosition()).getProdBatchCode());
        holder.quantity.setText(String.valueOf(OrderSupportUtil.getInstance().getStringQty(orderBookingVOList.get(position).getQuantity()) + " " +
                replaceNullWithEmprty(orderBookingVOList.get(position).getUomId())));
        holder.lTotal.setText(String.format(Locale.getDefault(), "%.2f", orderBookingVOList.get(holder.getAdapterPosition()).getTotalAmount()));
        holder.mrp.setText(String.format(Locale.getDefault(), "%.2f", orderBookingVOList.get(holder.getAdapterPosition()).getMrp()));
        holder.sellingPrice.setText(String.format(Locale.getDefault(), "%.2f", orderBookingVOList.get(holder.getAdapterPosition()).getSellPrice()));
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

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class OrderSummaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView productName;
        private final TextView batchCode;
        private final TextView quantity;
        private final TextView mrp;
        private final TextView sellingPrice;
        private final TextView lTotal;
        private final ImageView ivDelete;

        OrderSummaryViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.order_product_name_txt);
            batchCode = view.findViewById(R.id.batch_code_txt);
            quantity = view.findViewById(R.id.order_qty_txt);
            mrp = view.findViewById(R.id.order_mrp_txt);
            sellingPrice = view.findViewById(R.id.order_rate_txt);
            lTotal = view.findViewById(R.id.order_ltotal_txt);
            ivDelete = view.findViewById(R.id.ivDelete);

            productName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            lTotal.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            batchCode.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            mrp.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            sellingPrice.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            quantity.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ivDelete.setOnClickListener(this);

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
