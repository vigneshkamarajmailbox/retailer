package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.MTDReportModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerWiseReportAdapter extends RecyclerView.Adapter<CustomerWiseReportAdapter.MyViewHolder> {

    private List<MTDReportModel> mtdReportModelList;
    private double totalNetAmt =0;
    private List<MTDReportModel> tempList = new ArrayList<>();

    public CustomerWiseReportAdapter(List<MTDReportModel> modelList, double totalAmt) {
        this.mtdReportModelList = modelList;
        this.totalNetAmt = totalAmt;
        this.tempList.addAll(mtdReportModelList);
    }


    @NonNull
    @Override
    public CustomerWiseReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_wise_list_item, parent, false);
        return new CustomerWiseReportAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerWiseReportAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tvRetailer.setText(mtdReportModelList.get(position).getCustomerName().toUpperCase());
        holder.tvChannel.setText(mtdReportModelList.get(position).getChannelName().toUpperCase());
        double percentValue = mtdReportModelList.get(position).getTotNetAmt() / totalNetAmt * 100;
        holder.tvPercent.setText(String.format(Locale.getDefault(), "%.1f", percentValue));
        holder.tvInvoice.setText(""+mtdReportModelList.get(position).getInvoiceCount());
        holder.tvValue.setText(String.format(Locale.getDefault(), "%.2f",
                mtdReportModelList.get(position).getTotNetAmt()));
    }

    @Override
    public int getItemCount() {
        return mtdReportModelList.size();
    }

    public void applyFilterValue(int selectedValue, String fragmentName) {
        mtdReportModelList.clear();
        for (MTDReportModel mtdReportModel : tempList) {
            if ("MTDDayWise".equalsIgnoreCase(fragmentName)) {
                if (mtdReportModel.getInvoiceDay()== selectedValue) {
                    mtdReportModelList.add(mtdReportModel);
                } else {
                    addModelToList(mtdReportModel, selectedValue);
                }
            } else if ("MTDWeekWise".equalsIgnoreCase(fragmentName)) {
                String week = mtdReportModel.getInvoiceWeek().substring(4);
                if (Integer.parseInt(week) == selectedValue) {
                    mtdReportModelList.add(mtdReportModel);
                } else {
                    addModelToList(mtdReportModel, selectedValue);
                }
            }
        }
        notifyDataSetChanged();

    }

    private void addModelToList(MTDReportModel mtdReportModel, int selectedValue) {
        if (selectedValue == 0) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRetailer;
        private TextView tvChannel;
        private TextView tvPercent;
        private TextView tvInvoice;
        private TextView tvValue;

        MyViewHolder(View view) {
            super(view);
            tvRetailer = view.findViewById(R.id.tvRetailer);
            tvChannel = view.findViewById(R.id.tvChannel);
            tvPercent = view.findViewById(R.id.tvPercent);
            tvInvoice = view.findViewById(R.id.tvInvoice);
            tvValue = view.findViewById(R.id.tvValue);

        }

    }
}