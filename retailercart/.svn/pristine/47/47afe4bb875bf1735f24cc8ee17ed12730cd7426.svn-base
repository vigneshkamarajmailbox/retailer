package com.botree.retailerssfa.adapters;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.botree.retailerssfa.R;

import java.util.List;


public class StockLedgerReportAdapterDistrubutorName extends RecyclerView.Adapter<StockLedgerReportAdapterDistrubutorName.StockViewHolder> {

    private final List<String> reportList;

    public StockLedgerReportAdapterDistrubutorName(List<String> list) {
        this.reportList = list;
    }
    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_ledger_report_distname, parent, false);
        return new StockLedgerReportAdapterDistrubutorName.StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StockViewHolder holder, int position) {

        holder.distrubutornamelistItem.setText(reportList.get(position));
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }


    class StockViewHolder extends RecyclerView.ViewHolder {

        private TextView distrubutornamelistItem;

        private StockViewHolder(View itemView) {
            super(itemView);

            distrubutornamelistItem = itemView.findViewById(R.id.distrubutor_name_listItem);

        }
    }


}

