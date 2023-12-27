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

import java.util.ArrayList;
import java.util.List;

public class ProductTaxDialogListAdapter extends RecyclerView.Adapter<ProductTaxDialogListAdapter.MyViewHolder> {
    private List<OrderBookingVO> taxMasterModels = new ArrayList<>();

    public ProductTaxDialogListAdapter(List<OrderBookingVO> taxMasterModels) {
        this.taxMasterModels.addAll(taxMasterModels);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tax_dialog_list_adapter_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.cgstTxt.setText(""+taxMasterModels.get(position).getCgstperc());
        holder.sgstTxt.setText(""+taxMasterModels.get(position).getSgstPerc());
        holder.igstTxt.setText(""+taxMasterModels.get(position).getIgstPerc());
        holder.cessTxt.setText(""+taxMasterModels.get(position).getUtgstPerc());
    }

    @Override
    public int getItemCount() {
        return taxMasterModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView cgstTxt;
        private TextView sgstTxt;
        private TextView igstTxt;
        private TextView cessTxt;

        MyViewHolder(View view) {
            super(view);

            cgstTxt = view.findViewById(R.id.cgst_txt);
            sgstTxt = view.findViewById(R.id.sgst_txt);
            igstTxt = view.findViewById(R.id.igst_txt);
            cessTxt = view.findViewById(R.id.cess_txt);
        }
    }
}
