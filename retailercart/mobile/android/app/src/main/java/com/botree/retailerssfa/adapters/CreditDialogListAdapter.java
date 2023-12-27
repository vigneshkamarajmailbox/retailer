package com.botree.retailerssfa.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.SalesDBCRNoteModel;
import com.botree.retailerssfa.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreditDialogListAdapter  extends RecyclerView.Adapter<CreditDialogListAdapter.MyViewHolder> {
    private List<SalesDBCRNoteModel> salesDBCRNoteModels = new ArrayList<>();
    private String dateFormat = "dd-MM-yyyy";

    public CreditDialogListAdapter(List<SalesDBCRNoteModel> salesDBCRNoteModelList) {
        this.salesDBCRNoteModels.addAll(salesDBCRNoteModelList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_dialog_list_adapter_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.crNo.setText(String.valueOf(salesDBCRNoteModels.get(holder.getAdapterPosition()).getSalesDBCRRefNo()) + " - " + salesDBCRNoteModels.get(holder.getAdapterPosition()).getDbCRReason());
        holder.crDate.setText(DateUtil.covertTimeStampIntoData(String.valueOf(salesDBCRNoteModels.get(holder.getAdapterPosition()).getDbCRDt()), dateFormat));
        holder.crAdjAmt.setText(String.format(Locale.getDefault(), "%.2f", salesDBCRNoteModels.get(holder.getAdapterPosition()).getAdjAmt()));
        holder.crAmt.setText(String.format(Locale.getDefault(), "%.2f", salesDBCRNoteModels.get(holder.getAdapterPosition()).getDbCRAmt()));
        holder.crBal.setText(String.format(Locale.getDefault(), "%.2f", salesDBCRNoteModels.get(holder.getAdapterPosition()).getDbCRBal()));
        holder.checkbox.setChecked(true);
        holder.checkbox.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return salesDBCRNoteModels.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView crNo;
        final TextView crDate;
        final TextView crAmt;
        final TextView crBal;
        final TextView crAdjAmt;
        final CheckBox checkbox;

        MyViewHolder(View view) {
            super(view);

            crNo = view.findViewById(R.id.cr_no_desc);
            crDate = view.findViewById(R.id.cr_date);
            crAmt = view.findViewById(R.id.tot_amt);
            crBal = view.findViewById(R.id.balance);
            crAdjAmt = view.findViewById(R.id.adj_amt);
            checkbox = view.findViewById(R.id.checkbox);
        }
    }
}
