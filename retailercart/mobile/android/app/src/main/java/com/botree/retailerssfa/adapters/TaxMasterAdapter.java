package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
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
import java.util.Locale;

public class TaxMasterAdapter extends RecyclerView.Adapter<TaxMasterAdapter.MyViewHolder> {

    private List<OrderBookingVO> taxMasterlist;
    private List<OrderBookingVO> searchItem = new ArrayList<>();

    public TaxMasterAdapter(List<OrderBookingVO> taxMaster) {
        this.taxMasterlist = taxMaster;
        this.searchItem.addAll(taxMaster);
    }


    @NonNull
    @Override
    public TaxMasterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tax_master_list_item, parent, false);
        return new TaxMasterAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaxMasterAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.prodnameTxt.setText(taxMasterlist.get(position).getProdName().toUpperCase(Locale.getDefault()));
        holder.cgstTxt.setText(""+taxMasterlist.get(position).getCgstperc());
        holder.sgstTxt.setText(""+taxMasterlist.get(position).getSgstPerc());
        holder.igstTxt.setText(""+taxMasterlist.get(position).getIgstPerc());
        holder.cessTxt.setText(""+taxMasterlist.get(position).getUtgstPerc());
    }

    @Override
    public int getItemCount() {
        return taxMasterlist.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        taxMasterlist.clear();

        if (charText.length() == 0) {
            taxMasterlist.addAll(searchItem);

        } else {

            if (charText.contains("all")) {
                taxMasterlist.addAll(searchItem);
            } else {
                for (OrderBookingVO p : searchItem) {

                    if (p.getProdName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        taxMasterlist.add(p);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView prodnameTxt;
        private TextView cgstTxt;
        private TextView sgstTxt;
        private TextView igstTxt;
        private TextView cessTxt;

        MyViewHolder(View view) {
            super(view);
            prodnameTxt = view.findViewById(R.id.prodname_txt);
            cgstTxt = view.findViewById(R.id.cgst_txt);
            sgstTxt = view.findViewById(R.id.sgst_txt);
            igstTxt = view.findViewById(R.id.igst_txt);
            cessTxt = view.findViewById(R.id.cess_txt);
        }

    }
}
