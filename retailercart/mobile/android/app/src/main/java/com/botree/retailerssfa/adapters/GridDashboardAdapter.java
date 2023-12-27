package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.QuickViewVo;

import java.util.List;

/**
 * Created by vinothbaskaran on 7/12/17.
 */

public class GridDashboardAdapter extends RecyclerView.Adapter<GridDashboardAdapter.GridDashboardViewHolder> {
    private final List<QuickViewVo> dashboardMenus;
    private Boolean isSequence;
    private OnRecyclerItemClick onRecyclerItemClick;

    public GridDashboardAdapter(List<QuickViewVo> dashboardMenus, Boolean isSequence) {
        this.dashboardMenus = dashboardMenus;
        this.isSequence = isSequence;
    }

    public void setOnRecyclerItemClick(OnRecyclerItemClick onRecyclerItemClick) {
        this.onRecyclerItemClick = onRecyclerItemClick;
    }

    @NonNull
    @Override
    public GridDashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_outlet_dashboard_item_view, parent, false);
        return new GridDashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridDashboardViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.ivIcon.setImageResource(dashboardMenus.get(position).getIcons());
        holder.tvName.setText(dashboardMenus.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClick != null) {
                    onRecyclerItemClick.onItemClick(position, dashboardMenus.get(position).getName());
                }
            }
        });
        if (Boolean.TRUE.equals(isSequence)) {
            if (dashboardMenus.get(position).isSeqVisible()) {
                holder.itemView.setAlpha(1f);
                holder.itemView.setEnabled(true);
            } else {
                holder.itemView.setAlpha(0.5f);
                holder.itemView.setEnabled(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dashboardMenus.size();
    }

    public interface OnRecyclerItemClick {
        void onItemClick(int position, String name);
    }

    class GridDashboardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivIcon;
        private final TextView tvName;

        GridDashboardViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvName);

        }
    }
}
