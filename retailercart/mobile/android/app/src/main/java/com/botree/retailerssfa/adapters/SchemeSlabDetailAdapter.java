package com.botree.retailerssfa.adapters;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.SchemeModel;

import java.util.List;


public class SchemeSlabDetailAdapter extends RecyclerView.Adapter<SchemeSlabDetailAdapter.AnswerAdapterViewHolder> {

    private final List<SchemeModel> slabList;

    SchemeSlabDetailAdapter(List<SchemeModel> list) {
        this.slabList = list;
    }

    @NonNull
    @Override
    public AnswerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheme_slab_detail, parent, false);
        return new AnswerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnswerAdapterViewHolder holder, int position) {

        holder.slabNo.setText(slabList.get(position).getSlabNo());
        holder.slabFrom.setText(String.valueOf(slabList.get(position).getSlabFrom()));
        holder.slabTo.setText(String.valueOf(slabList.get(position).getSlabTo()));
        holder.slabForEveryOne.setText(String.valueOf(slabList.get(position).getForEvery()));
        holder.slabPayOut.setText(String.valueOf(slabList.get(position).getPayoutValue()));

    }

    @Override
    public int getItemCount() {
        return slabList.size();
    }

    class AnswerAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView slabNo;
        private TextView slabFrom;
        private TextView slabTo;
        private TextView slabForEveryOne;
        private TextView slabPayOut;


        private AnswerAdapterViewHolder(View itemView) {
            super(itemView);
            slabNo = itemView.findViewById(R.id.slabNo);
            slabFrom = itemView.findViewById(R.id.slabFrom);
            slabTo = itemView.findViewById(R.id.slabTo);
            slabForEveryOne = itemView.findViewById(R.id.slabForEveryOne);
            slabPayOut = itemView.findViewById(R.id.slabPayOut);

        }
    }

}
