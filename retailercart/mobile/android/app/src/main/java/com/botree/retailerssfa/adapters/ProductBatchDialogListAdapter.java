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
import com.botree.retailerssfa.models.ProdBatchModel;
import com.botree.retailerssfa.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductBatchDialogListAdapter extends RecyclerView.Adapter<ProductBatchDialogListAdapter.MyViewHolder> {
    private static final String TAG = ProductBatchDialogListAdapter.class.getSimpleName();
    private List<ProdBatchModel> prodBatchModels = new ArrayList<>();
    private String dateFormat = "dd-MM-yyyy";

    public ProductBatchDialogListAdapter(List<ProdBatchModel> prodBatchModels) {
        this.prodBatchModels.addAll(prodBatchModels);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.batch_dialog_list_adapter_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        holder.serialNo.setText(DateUtil.covertTimeStampIntoData(String.valueOf(prodBatchModels.get(holder.getAdapterPosition()).getManfDate()), dateFormat));
        holder.purchasePrice.setText(String.format(Locale.getDefault(),"%.2f", prodBatchModels.get(holder.getAdapterPosition()).getPurchaseRate()));
        holder.mrp.setText(String.format(Locale.getDefault(),"%.2f",prodBatchModels.get(holder.getAdapterPosition()).getMrp()));
        holder.sellingPrice.setText(String.format(Locale.getDefault(),"%.2f",prodBatchModels.get(holder.getAdapterPosition()).getSellRate()));
        holder.stock.setText(prodBatchModels.get(holder.getAdapterPosition()).getAvailQty());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount : " + prodBatchModels.size());
        return prodBatchModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView purchasePrice;
        final TextView mrp;
        final TextView sellingPrice;
        final TextView serialNo;
        TextView stock;

        MyViewHolder(View view) {
            super(view);

            serialNo = view.findViewById(R.id.serial_no);
            purchasePrice = view.findViewById(R.id.purchase_price);
            mrp = view.findViewById(R.id.mrp);
            sellingPrice = view.findViewById(R.id.selling_price);
            stock = view.findViewById(R.id.stock);
        }
    }
}
