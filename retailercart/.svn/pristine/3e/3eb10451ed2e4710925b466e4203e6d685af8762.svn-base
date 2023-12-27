package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.SalesmanRouteCoverageModel;
import com.botree.retailerssfa.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SalesmanRouteCoverageListAdapter extends RecyclerView.Adapter<SalesmanRouteCoverageListAdapter.MyViewHolder> {
    private List<SalesmanRouteCoverageModel> salesmanRouteCoverageModels = new ArrayList<>();
    private List<SalesmanRouteCoverageModel> searchList = new ArrayList<>();
    Context context;
    SFADatabase db;

    public SalesmanRouteCoverageListAdapter(Context context, List<SalesmanRouteCoverageModel> salesmanRouteCoverageModels) {
        this.context = context;
        this.salesmanRouteCoverageModels.addAll(salesmanRouteCoverageModels);
        this.searchList.addAll(salesmanRouteCoverageModels);
        db = SFADatabase.getInstance(context);
    }

    public void setItems(List<SalesmanRouteCoverageModel> salesmanRouteCoverageModels) {
        this.salesmanRouteCoverageModels.clear();
        this.searchList.clear();

        this.salesmanRouteCoverageModels.addAll(salesmanRouteCoverageModels);
        this.searchList.addAll(salesmanRouteCoverageModels);
    }

    public void applySearchFilter(String charText) {
        applyFilterAndAdd(charText.toLowerCase(Locale.getDefault()));
    }

    @NonNull
    @Override
    public SalesmanRouteCoverageListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salesman_route_coverage_adapter_layout, parent, false);
        return new SalesmanRouteCoverageListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SalesmanRouteCoverageListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (salesmanRouteCoverageModels.get(holder.getAdapterPosition()).getRouteName() != null)
            holder.routeName.setText(salesmanRouteCoverageModels.get(holder.getAdapterPosition()).getRouteName().toUpperCase(Locale.getDefault()));
        holder.coverageDate.setText(DateUtil.convertTimestampToStringDate(Long.parseLong(
                salesmanRouteCoverageModels.get(holder.getAdapterPosition()).getCoverageDt())));
        holder.coverageDay.setText(DateUtil.getNameOfDayUsingDate(holder.coverageDate.getText().toString()));
    }

    @Override
    public int getItemCount() {
        return salesmanRouteCoverageModels.size();
    }

    private void applyFilterAndAdd(String filterText) {
        salesmanRouteCoverageModels.clear();

        if (isAllFilterEmpty(filterText) || filterText.contains("all")) {
            salesmanRouteCoverageModels.addAll(searchList);
            notifyDataSetChanged();
            return;
        }

        for (SalesmanRouteCoverageModel salesmanRouteCoverageModel : searchList) {
            if (checkFilterText(filterText, salesmanRouteCoverageModel)) {
                salesmanRouteCoverageModels.add(salesmanRouteCoverageModel);
            }
        }
        notifyDataSetChanged();
    }

    private boolean checkFilterText(String filterText, SalesmanRouteCoverageModel salesmanRouteCoverageModel) {
        return filterText.length() == 0 || (salesmanRouteCoverageModel.getRouteName().toLowerCase(Locale.getDefault()).contains(filterText));

    }

    private boolean isAllFilterEmpty(String filterText) {
        return filterText.length() == 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView routeName;
        final TextView coverageDate;
        final TextView coverageDay;

        MyViewHolder(View view) {
            super(view);

            routeName = view.findViewById(R.id.route_name);
            coverageDate = view.findViewById(R.id.coverage_date);
            coverageDay = view.findViewById(R.id.coverage_day);
        }
    }
}
