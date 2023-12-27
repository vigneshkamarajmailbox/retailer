package com.botree.retailerssfa.adapters;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.OrderBookingVO;

import java.util.List;
import java.util.Locale;

public class StockSummaryAdapter extends RecyclerView.Adapter<StockSummaryAdapter.StockSummaryViewHolder> {

    private List<OrderBookingVO> stockSummaryList;

    public StockSummaryAdapter(List<OrderBookingVO> orderBookingVOS) {
        this.stockSummaryList = orderBookingVOS;
    }

    public String getValueAt(int position) {

        return stockSummaryList.get(position).getOrderNo();
    }

    @NonNull
    @Override
    public StockSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_summary_list_item, parent, false);
        return new StockSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StockSummaryViewHolder holder, final int position) {

        holder.skuNameTxt.setText(stockSummaryList.get(position).getProdName());
        holder.openingStockQtyTxt.setText(String.valueOf(stockSummaryList.get(position).getStockInHand()));
        holder.openingStockValueTxt.setText(String.format(Locale.getDefault(), "%.2f",
                stockSummaryList.get(position).getOrderValue()));

        Double closeAmt = (stockSummaryList.get(position).getSellPrice().doubleValue() * stockSummaryList.get(position).getAvailQty());
        Double otdVal = stockSummaryList.get(position).getOrderValue().doubleValue() - closeAmt;

        int orderQty = stockSummaryList.get(position).getStockInHand() - stockSummaryList.get(position).getAvailQty();

        holder.orderQtyTxt.setText(String.valueOf(orderQty));
        holder.closingStockQtyTxt.setText(String.valueOf(stockSummaryList.get(position).getAvailQty()));
        holder.closingStockValueTxt.setText(String.format(Locale.getDefault(), "%.2f", closeAmt));

        if (otdVal > 0) {
            holder.orderValueTxt.setText(String.format(Locale.getDefault(), "%.2f", otdVal));
        } else {
            holder.orderValueTxt.setText(R.string.double_zero);
        }

    }

    @Override
    public int getItemCount() {

        return stockSummaryList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class StockSummaryViewHolder extends RecyclerView.ViewHolder {

        private final TextView skuNameTxt;
        private final TextView openingStockQtyTxt;
        private final TextView closingStockQtyTxt;
        private final TextView orderQtyTxt;
        private final TextView openingStockValueTxt;
        private final TextView orderValueTxt;
        private final TextView closingStockValueTxt;

        StockSummaryViewHolder(View view) {
            super(view);
            skuNameTxt = view.findViewById(R.id.stock_skus_name_txt);
            openingStockQtyTxt = view.findViewById(R.id.opening_stock_qty_txt);
            openingStockValueTxt = view.findViewById(R.id.opening_stock_value_txt);
            closingStockValueTxt = view.findViewById(R.id.closing_stock_value_txt);
            closingStockQtyTxt = view.findViewById(R.id.closing_stock_Qty_txt);
            orderValueTxt = view.findViewById(R.id.stock_order_value_txt);
            orderQtyTxt = view.findViewById(R.id.stock_order_qty_txt);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + skuNameTxt.getText();
        }

    }
}
