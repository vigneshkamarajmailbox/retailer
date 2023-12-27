package com.botree.retailerssfa.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.util.AppUtils;

import java.util.List;

/**
 * Created by vinothbaskaran on 2/4/18.
 */

class OrderSummarySchemeAdapter extends RecyclerView.Adapter<OrderSummarySchemeAdapter.OrderSummarySchemeViewHolder> {
    private final List<SchemeModel> schemeModels;

    OrderSummarySchemeAdapter(List<SchemeModel> schemeModels) {
        this.schemeModels = schemeModels;
    }

    @NonNull
    @Override
    public OrderSummarySchemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_scheme_summary_list_item, parent, false);
        return new OrderSummarySchemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummarySchemeViewHolder holder, int position) {
        holder.tvSchemeDesc.setText(String.valueOf(schemeModels.get(position).getSchemeDescription()));
        holder.tvSlabNo.setText(String.valueOf(schemeModels.get(position).getSlabNo()));
        holder.tvSlNo.setText(String.valueOf((position + 1) + "."));
        AppUtils.getOurInstance().setInrSymToValue(holder.tvSchemeAmt, schemeModels.get(position).getFlatAmount());
    }

    @Override
    public int getItemCount() {
        return schemeModels.size();
    }

    class OrderSummarySchemeViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSchemeAmt;
        private final TextView tvSlabNo;
        private final TextView tvSchemeDesc;
        private final TextView tvSlNo;

        OrderSummarySchemeViewHolder(View itemView) {
            super(itemView);
            tvSchemeAmt = itemView.findViewById(R.id.tvSchemeAmt);
            tvSlabNo = itemView.findViewById(R.id.tvSlabNo);
            tvSchemeDesc = itemView.findViewById(R.id.tvSchemeDesc);
            tvSlNo = itemView.findViewById(R.id.tvSlNo);
        }
    }
}
