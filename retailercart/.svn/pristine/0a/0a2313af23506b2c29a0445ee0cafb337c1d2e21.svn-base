package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.BillingInfoVO;
import com.botree.retailerssfa.util.OrderSupportUtil;

import java.util.List;
import java.util.Locale;


public class BillingListAdapter extends RecyclerView.Adapter<BillingListAdapter.BillingViewHolder> {
    private final Context context;
    private List<BillingInfoVO> billingInfoVOS;
    private OnRecyclerItemClick onRecyclerItemClick;

    public BillingListAdapter(Context context, List<BillingInfoVO> billingInfoVOS) {
        this.context = context;
        this.billingInfoVOS = billingInfoVOS;
    }


    @NonNull
    @Override
    public BillingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.billing_list_item, parent, false);
        return new BillingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillingViewHolder holder, int position) {

        holder.orderDateTxt.setText(billingInfoVOS.get(holder.getAdapterPosition()).getInvoiceDt());
        holder.orderInvoiceTxt.setText(billingInfoVOS.get(holder.getAdapterPosition()).getInvoiceNo());
        holder.orderItemsTxt.setText(String.valueOf(billingInfoVOS.get(holder.getAdapterPosition()).getLineCount()));

        holder.grossAmtTxt.setText(String.format(Locale.ENGLISH,"%.2f",Double.valueOf(billingInfoVOS.get(holder.getAdapterPosition()).getTotGrossAmt())));
        holder.taxAmtTxt.setText(String.format(Locale.ENGLISH,"%.2f",Double.valueOf(billingInfoVOS.get(holder.getAdapterPosition()).getTotAddition())));
        holder.discountAmtTxt.setText(String.format(Locale.ENGLISH,"%.2f",Double.valueOf(billingInfoVOS.get(holder.getAdapterPosition()).getTotDeduction())));

        if (billingInfoVOS.get(holder.getAdapterPosition()).getTotCrNoteAmt() !=null &&
                    !billingInfoVOS.get(holder.getAdapterPosition()).getTotCrNoteAmt().isEmpty()) {
            double crNoteAmt = Double.valueOf(billingInfoVOS.get(holder.getAdapterPosition()).getTotNetAmt()) - Double.valueOf(billingInfoVOS.get(holder.getAdapterPosition()).getTotCrNoteAmt());
            holder.orderValueTxt.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.Rs) + " "
                    + "%.2f", OrderSupportUtil.getRoundedOffNetAmount(crNoteAmt)));
        } else {
            holder.orderValueTxt.setText(String.format(Locale.ENGLISH, context.getResources().getString(R.string.Rs) + " "
                    + "%.2f", OrderSupportUtil.getRoundedOffNetAmount(Double.parseDouble(billingInfoVOS.get(holder.getAdapterPosition()).getTotNetAmt()))));
        }
        holder.retailerName.setText(billingInfoVOS.get(holder.getAdapterPosition()).getCustomerName());

        if(billingInfoVOS.get(holder.getAdapterPosition()).getCompleteFlag().equals("S")){
            holder.parentLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }else{
            holder.parentLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.color0));
        }
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
        private TextView retailerName;

        private TextView grossAmtTxt;
        private TextView discountAmtTxt;
        private TextView taxAmtTxt;
        private LinearLayout parentLayout;

        BillingViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            orderDateTxt = itemView.findViewById(R.id.date);
            orderInvoiceTxt = itemView.findViewById(R.id.invoice_no);
            orderItemsTxt = itemView.findViewById(R.id.count);
            orderValueTxt = itemView.findViewById(R.id.order_value);
            retailerName = itemView.findViewById(R.id.retailer_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            grossAmtTxt = itemView.findViewById(R.id.inv_gross_amt_txt);
            discountAmtTxt = itemView.findViewById(R.id.inv_disc_amt_txt);
            taxAmtTxt = itemView.findViewById(R.id.inv_tax_amt_txt);

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
