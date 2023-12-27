package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.MTDReportModel;

import java.util.ArrayList;
import java.util.List;

public class ReportProductFilterAdapter extends RecyclerView.Adapter<ReportProductFilterAdapter.BrandFilterViewHolder> {

    private int selectedPosition = -1;
    private Context context;
    private List<MTDReportModel> filterModelsList;
    private List<MTDReportModel> searchItem = new ArrayList<>();
    private OnListItemClick onItemClick;

    public ReportProductFilterAdapter(Context context, List<MTDReportModel> brandList, int pos, int level, boolean state) {
        searchItem.clear();
        this.filterModelsList = brandList;
        this.context = context;
        this.searchItem.addAll(brandList);
        if (state) {
            this.selectedPosition = pos;
        }
    }

    public void updateRecyclerView(List<MTDReportModel> brandList, int pos, int level, boolean state) {
        searchItem.clear();
        this.filterModelsList = brandList;
        this.searchItem.addAll(brandList);
        if (state) {
            this.selectedPosition = pos;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReportProductFilterAdapter.BrandFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_filter_list_item, parent, false);
        return new ReportProductFilterAdapter.BrandFilterViewHolder(view);
    }

    public void setOnItemClickListener(OnListItemClick mItemClickListener) {
        this.onItemClick = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReportProductFilterAdapter.BrandFilterViewHolder holder, int position) {

        final int pos = position;
        holder.brandNameTxt.setText(filterModelsList.get(position).getLevelName1());
        holder.brandIconImg.setVisibility(View.GONE);

            prodLevelFilterSelection(holder, pos);

            holder.mview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClick != null) {
                        selectedPosition = pos;
                        notifyDataSetChanged();
                        onItemClick.onItemClick(pos, filterModelsList.get(pos).getLevelCode1(),
                                filterModelsList.get(pos).getLevelName1());
                    }
                }
            });
    }

    private void prodLevelFilterSelection(@NonNull BrandFilterViewHolder holder, int pos) {
        if (selectedPosition == pos) {
            setBackgroundDrawableToView(holder);
            holder.brandNameTxt.setEnabled(true);
            holder.brandNameTxt.setTypeface(Typeface.SERIF, Typeface.BOLD);

        } else {

            holder.itemView.setBackground(setUnSelectedDrawable());
            holder.brandNameTxt.setEnabled(false);
            holder.brandNameTxt.setTypeface(Typeface.SERIF, Typeface.NORMAL);
        }
    }

    private void setBackgroundDrawableToView(@NonNull ReportProductFilterAdapter.BrandFilterViewHolder holder) {
        holder.itemView.setBackground(setSelectedDrawable());
    }

    private Drawable setSelectedDrawable() {

        return ContextCompat.getDrawable(context, R.drawable.filter_list_selected);
    }


    private Drawable setUnSelectedDrawable() {

        return ContextCompat.getDrawable(context, R.drawable.filter_list_unselected);
    }

    @Override
    public int getItemCount() {
        return filterModelsList.size();
    }

    public void clearData() {
        final int size = filterModelsList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                filterModelsList.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

    public interface OnListItemClick {
        void onItemClick(int pos, String brandCode, String brandName);
    }


    public interface OnListBrandItemClick {
        void onItemClick(int pos, String brandName);
    }

    class BrandFilterViewHolder extends RecyclerView.ViewHolder {

        private final TextView brandNameTxt;
        private final ImageView brandIconImg;
        private View mview;

        private BrandFilterViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
            brandNameTxt = itemView.findViewById(R.id.brand_name_txt);
            brandIconImg = itemView.findViewById(R.id.brand_icon_img);
        }
    }

}
