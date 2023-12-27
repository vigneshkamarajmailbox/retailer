package com.botree.retailerssfa.adapters;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.PendingCollection;
import com.botree.retailerssfa.util.DateUtil;

import java.util.List;
import java.util.Locale;

public class PendingCollectionAdapter extends RecyclerView.Adapter<PendingCollectionAdapter.PendingViewHolder> {
    private List<PendingCollection> pendingCollections;
    private OnItemClickListener mItemClickListener;

    public PendingCollectionAdapter(List<PendingCollection> pendingCollectionArrayList) {
        this.pendingCollections = pendingCollectionArrayList;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public PendingCollectionAdapter.PendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_collection_list_item, parent, false);
        return new PendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PendingCollectionAdapter.PendingViewHolder holder, final int position) {
        holder.tvBillNo.setText(pendingCollections.get(position).getBillNo());
        holder.tvBillDate.setText(DateUtil.covertTimeStampIntoData(pendingCollections.get(position).getBillDate(),  "yyyy-MM-dd"));
        holder.tvAmount.setText(String.format(Locale.getDefault(), "%.2f", pendingCollections.get(position).getPendingAmount()));

        if (pendingCollections.get(holder.getAdapterPosition()).getAdjustAmount() > 0) {
            holder.tvAdjustAmount.setText(String.format(Locale.getDefault(), "%.2f", pendingCollections.get(position).getAdjustAmount()));
        } else {
            holder.tvAdjustAmount.setText("");
        }
        holder.tvAdjustBalance.setText(String.format(Locale.getDefault(), "%.2f", pendingCollections.get(position).getAdjustBalance()));
    }

    @Override
    public int getItemCount() {
        return pendingCollections.size();
    }

    public class PendingViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        final CheckBox checkbox;
        final TextView tvBillNo;
        final TextView tvBillDate;
        final TextView tvAmount;
        final TextView tvAdjustAmount;
        final TextView tvAdjustBalance;

        PendingViewHolder(final View view) {
            super(view);
            mView = view;
            checkbox = view.findViewById(R.id.checkbox);
            tvBillDate = view.findViewById(R.id.tvBillDate);
            tvBillNo = view.findViewById(R.id.tvBillNo);
            tvAmount = view.findViewById(R.id.tvAmount);
            tvAdjustAmount = view.findViewById(R.id.tvAdjustAmount);
            tvAdjustBalance = view.findViewById(R.id.tvAdjustBalance);

            tvAdjustAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null)
                        mItemClickListener.onItemClick(view, getAdapterPosition());
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
