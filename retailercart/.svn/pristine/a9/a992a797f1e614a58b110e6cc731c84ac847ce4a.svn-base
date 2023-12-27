package com.botree.retailerssfa.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.ReasonVO;
import com.botree.retailerssfa.models.SalesReturnVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FullReturnAdapter extends RecyclerView.Adapter<FullReturnAdapter.FullReturnViewHolder> {


    private SFADatabase db;
    private Context con;
    private FullReturnAdapter.OnItemClickListener mItemClickListener;
    private FullReturnAdapter.OnItemLongClickListener monItemLongClicked;

    private List<SalesReturnVO> salesReturnList;

    public FullReturnAdapter(Context context, List<SalesReturnVO> salesReturnList) {

        this.salesReturnList = salesReturnList;
        con = context;

        db = SFADatabase.getInstance(context);
    }

    public void setOnItemClickListener(final FullReturnAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(final FullReturnAdapter.OnItemLongClickListener mItemLongClicked) {
        this.monItemLongClicked = mItemLongClicked;
    }

    public String getValueAt(int position) {
        return salesReturnList.get(position).getProdCode();
    }

    @NonNull
    @Override
    public FullReturnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_return_list_item, parent, false);
        return new FullReturnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FullReturnViewHolder holder, final int position) {

        holder.dateTxt.setText(salesReturnList.get(position).getReturnDate());
        holder.invoiceAmtTxt.setText(String.format(Locale.getDefault(), "%.2f",
                salesReturnList.get(position).getSellPrice()));
        holder.grnNoTxt.setText(String.valueOf(salesReturnList.get(position).getGrnNo()));
        holder.invoiceNoTxt.setText(String.valueOf(salesReturnList.get(position).getInvoiceNo()));

        loadspinner(holder.reasonSpinner);
    }

    @Override
    public int getItemCount() {
        return salesReturnList.size();
    }

    /**
     * fetch the sales return reasons and load them in reason spinner
     */
    private void loadspinner(Spinner mSpinnerSalesReason) {

        List<String> reasonName = new ArrayList<>();
        final List<String> reasonCode;
        reasonCode = new ArrayList<>();
        List<ReasonVO> reasons = db.loadReasons("salesreturn");

        reasonName.add("Choose Reason");
        reasonCode.add("0");

        for (int i = 0; i < reasons.size(); i++) {
            reasonName.add(reasons.get(i).getReasonName());
            reasonCode.add(reasons.get(i).getReasonCode());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(con, R.layout.spinner_list_item, reasonName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSalesReason.setAdapter(adapter);

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(View view, int position);
    }

    public class FullReturnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {


        private TextView dateTxt;
        private Spinner reasonSpinner;
        private ImageView mIvDelete;
        private TextView grnNoTxt;
        private TextView invoiceNoTxt;
        private TextView invoiceAmtTxt;

        FullReturnViewHolder(View view) {
            super(view);
            dateTxt = view.findViewById(R.id.return_date_txt);
            grnNoTxt = view.findViewById(R.id.return_grn_no_txt);
            invoiceNoTxt = view.findViewById(R.id.return_invoice_no_txt);
            invoiceAmtTxt = view.findViewById(R.id.return_invoice_amt_txt);
            reasonSpinner = view.findViewById(R.id.return_reason_spinner);
            mIvDelete = view.findViewById(R.id.return_ivDelete);
            mIvDelete.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + dateTxt.getText();
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (monItemLongClicked != null) {
                monItemLongClicked.onItemLongClicked(view, getAdapterPosition());
            }
            return false;
        }
    }
}
