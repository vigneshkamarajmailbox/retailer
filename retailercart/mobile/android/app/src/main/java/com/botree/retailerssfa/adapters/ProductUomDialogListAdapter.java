package com.botree.retailerssfa.adapters;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.UomMasterModel;

import java.util.ArrayList;
import java.util.List;

public class ProductUomDialogListAdapter extends RecyclerView.Adapter<ProductUomDialogListAdapter.MyViewHolder> {
    private List<UomMasterModel> uomMasterModels = new ArrayList<>();

    public ProductUomDialogListAdapter(List<UomMasterModel> uomMasterModels) {
        this.uomMasterModels.addAll(uomMasterModels);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.uom_dialog_list_adapter_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.uomCode.setText(String.valueOf(uomMasterModels.get(holder.getAdapterPosition()).getUomCode()));
        holder.uomDescription.setText(String.valueOf(uomMasterModels.get(holder.getAdapterPosition()).getUomDescription()));
        holder.conversionFactor.setText(String.valueOf(uomMasterModels.get(holder.getAdapterPosition()).getUomConvFactor()));
    }

    @Override
    public int getItemCount() {
        return uomMasterModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView uomCode;
        final TextView uomDescription;
        final TextView conversionFactor;

        MyViewHolder(View view) {
            super(view);

            uomCode = view.findViewById(R.id.uom_code);
            uomDescription = view.findViewById(R.id.uom_description);
            conversionFactor = view.findViewById(R.id.conversion_factor);
        }
    }
}
