package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;

import java.util.List;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.MyViewHolder> {
    private List chartTypes;
    Context context;
    private int selectedPosition = 0;
    private OnListItemClick itemClick;

    public ChartAdapter(Context context, List chartNames, int i) {
        this.context = context;
        this.chartTypes = chartNames;
        this.selectedPosition = i;
    }

    public void setOnItemClickListener(OnListItemClick mItemClickListener) {
        this.itemClick = mItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final int pos = i;
        myViewHolder.name.setText(chartTypes.get(i).toString());
        prodLevelFilterSelection(myViewHolder, pos);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemClick != null) {
                    selectedPosition = pos;
                    notifyDataSetChanged();
                    itemClick.onItemClick(pos);
                }

            }
        });
    }

    private void prodLevelFilterSelection(@NonNull MyViewHolder holder, int pos) {
        if (selectedPosition == pos) {
            holder.itemView.setBackground(setSelectedDrawable());
            holder.name.setEnabled(true);

        } else {

            holder.itemView.setBackground(setUnSelectedDrawable());
            holder.name.setEnabled(false);
        }
    }


    private Drawable setSelectedDrawable() {

        return ContextCompat.getDrawable(context, R.drawable.chart_list_selected);
    }


    private Drawable setUnSelectedDrawable() {

        return ContextCompat.getDrawable(context, R.drawable.chart_list_unselected);
    }

    @Override
    public int getItemCount() {
        return chartTypes.size();
    }

    public interface OnListItemClick {
        void onItemClick(int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;// init the item view's
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = itemView.findViewById(R.id.name);
        }
    }
}
