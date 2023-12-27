package com.botree.retailerssfa.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.OrderBookingHeaderModel;

import java.util.List;
import java.util.Locale;


public class SalesPanelListAdapter extends RecyclerView.Adapter<SalesPanelListAdapter.BillingViewHolder> {
    private final Context context;
    private List<OrderBookingHeaderModel> billingInfoVOS;
    private OnRecyclerItemClick onRecyclerItemClick;

    public SalesPanelListAdapter(Context context, List<OrderBookingHeaderModel> billingInfoVOS) {
        this.context = context;
        this.billingInfoVOS = billingInfoVOS;
    }


    @NonNull
    @Override
    public BillingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_panel_list_item, parent, false);
        return new BillingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillingViewHolder holder, int position) {
//        holder.orderDateTxt.setText(billingInfoVOS.get(holder.getAdapterPosition()).getOrderDate());
        holder.orderInvoiceTxt.setText(billingInfoVOS.get(holder.getAdapterPosition()).getOrderNo());
        holder.orderItemsTxt.setText(String.valueOf(billingInfoVOS.get(holder.getAdapterPosition()).getLineCount()));
        holder.orderValueTxt.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.Rs) + " "
                + "%.2f", billingInfoVOS.get(holder.getAdapterPosition()).getTotalOrderValue()));
    }

    @Override
    public int getItemCount() {
        return billingInfoVOS.size();
    }

    public void setOnRecyclerItemClick(OnRecyclerItemClick onRecyclerItemClick) {
        this.onRecyclerItemClick = onRecyclerItemClick;
    }

    class BillingViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private TextView orderDateTxt;
        private TextView orderInvoiceTxt;
        private TextView orderItemsTxt;
        private TextView orderValueTxt;

        BillingViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            orderDateTxt = itemView.findViewById(R.id.date);
            orderInvoiceTxt = itemView.findViewById(R.id.invoice_no);
            orderItemsTxt = itemView.findViewById(R.id.count);
            orderValueTxt = itemView.findViewById(R.id.order_value);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecyclerItemClick != null) {
                        onRecyclerItemClick.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
