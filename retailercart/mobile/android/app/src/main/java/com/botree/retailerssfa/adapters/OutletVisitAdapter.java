package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.RetailerVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OutletVisitAdapter extends RecyclerView.Adapter<OutletVisitAdapter.MyViewHolder> {

    private Context con;
    private String[] color = {"#F2644C", "#006400", "#33b5e5", "#00A0FE"};
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener monItemLongClicked;
    private List<RetailerVO> retailerData;
    private List<RetailerVO> searchItem = new ArrayList<>();
    private int col = 0;
    private SFADatabase db;

    public OutletVisitAdapter(Context context, List<RetailerVO> retailerVOList) {
        this.retailerData = retailerVOList;
        con = context;
        this.searchItem.addAll(retailerVOList);
        db = SFADatabase.getInstance(con);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener mItemLongClicked) {
        this.monItemLongClicked = mItemLongClicked;
    }

    public String getValueAt(int position) {
        return retailerData.get(position).getCustomerCode();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.retName.setText(retailerData.get(position).getCustomerName().toUpperCase(Locale.getDefault()));
        holder.retName.setTextColor(ContextCompat.getColor(con, R.color.cardview_dark_background));

        holder.retStatus.setVisibility(View.VISIBLE);
        if (retailerData.get(position).getRetailerStatus().equalsIgnoreCase("Y")) {
            holder.retStatus.setText("Active");
            holder.retStatus.setTextColor(ContextCompat.getColor(con, R.color.color_green));
        } else {
            holder.retStatus.setText("InActive");
            holder.retStatus.setTextColor(ContextCompat.getColor(con, R.color.color_red));
        }

        holder.channelNameTxt.setText(String.valueOf(retailerData.get(position).getCustomerCode() + " / Channel-" + retailerData.get(position).getChannelName()));

        setUserInitialTextAndColor(holder, position);

    }

    private void setUserInitialTextAndColor(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String userInitial = "";
        if (retailerData.get(position).getApprovalStatus().equals("")) {
            userInitial = "P";
        } else {
            userInitial = retailerData.get(position).getApprovalStatus();
        }
        String init = null;
        if (userInitial != null && !userInitial.equalsIgnoreCase("")) {
            init = userInitial.substring(0, 1);
        }

        if (init != null && !init.equalsIgnoreCase("")) {
            holder.initial.setText(init.toUpperCase(Locale.getDefault()));
        }

        if (col < color.length) {
            Drawable drawable = ContextCompat.getDrawable(con, R.drawable.blue_circle_btn);
            if (drawable != null) {
                drawable.setColorFilter(Color.parseColor(color[col]), PorterDuff.Mode.SRC_ATOP);
            }
            holder.initial.setBackground(drawable);

            col++;
        } else {
            col = 0;
        }
    }

    @Override
    public int getItemCount() {
        return retailerData.size();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        retailerData.clear();

        if (charText.length() == 0) {
            retailerData.addAll(searchItem);

        } else {

            if (charText.contains("all")) {
                retailerData.addAll(searchItem);
            } else {
                for (RetailerVO p : searchItem) {

                    if (p.getCustomerName().toLowerCase(Locale.getDefault()).contains(charText) || p.getCustomerCode().toLowerCase(Locale.getDefault()).contains(charText)) {
                        Log.e("filter text: ", p.getCustomerName().toLowerCase(Locale.getDefault()));
                        retailerData.add(p);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        retailerData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, retailerData.size());
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final View mView;
        final TextView initial;
        final TextView retName;
        final ImageView pinIcon;
        final TextView channelNameTxt;
        final ImageView editImg;
        final TextView retStatus;

        MyViewHolder(View view) {
            super(view);
            mView = view;
            initial = view.findViewById(R.id.initial);
            retName = view.findViewById(R.id.retailer_name_txt);
            pinIcon = view.findViewById(R.id.pin_icon);
            editImg = view.findViewById(R.id.outlet_edit_icon);
            channelNameTxt = view.findViewById(R.id.retr_channel_name_txt);
            retStatus = view.findViewById(R.id.retr_status_txt);
            mView.setOnClickListener(this);
            mView.setOnLongClickListener(this);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + retName.getText();
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
