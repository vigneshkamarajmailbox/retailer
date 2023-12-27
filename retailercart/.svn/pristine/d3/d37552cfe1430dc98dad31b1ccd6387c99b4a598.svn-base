/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

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
import android.widget.Toast;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.RetailerVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RetailerListAdapter extends RecyclerView.Adapter<RetailerListAdapter.PendingViewHolder> {

    private Context con;
    private String[] color = {"#F2644C", "#006400", "#33b5e5", "#00A0FE"};
    private OnItemClickListener mItemClickListener;
    private OnLocationClickListener mlocationClickListener;
    private OnEditClickListener mEditClickListener;
    private OnItemLongClickListener monItemLongClicked;
    private List<RetailerVO> retailerData;
    private List<RetailerVO> searchItem = new ArrayList<>();
    private boolean status;
    private int col = 0;
    private SFADatabase db;

    public RetailerListAdapter(Context context, List<RetailerVO> retailerVOList, boolean state) {
        this.retailerData = retailerVOList;
        con = context;
        this.searchItem.addAll(retailerVOList);
        this.status = state;
        db = SFADatabase.getInstance(con);
    }

    public void setOnEditClickListener(final OnEditClickListener mItemClickListener) {
        this.mEditClickListener = mItemClickListener;
    }


    public void setOnLocationClickListener(final OnLocationClickListener mItemClickListener) {
        this.mlocationClickListener = mItemClickListener;
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
    public PendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
        return new PendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PendingViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.retName.setText(retailerData.get(position).getCustomerName().toUpperCase(Locale.getDefault()));
        holder.retName.setTextColor(ContextCompat.getColor(con, R.color.cardview_dark_background));
        holder.retStatus.setVisibility(View.VISIBLE);
        holder.statusTxt.setVisibility(View.VISIBLE);

        if (retailerData.get(position).getApprovalStatus().equalsIgnoreCase("P")) {
            holder.retStatus.setText("Pending");
            holder.retStatus.setTextColor(ContextCompat.getColor(con, R.color.ob_mustsell));
            setActiveInActive(holder, position);
        } else if (retailerData.get(position).getApprovalStatus().equalsIgnoreCase("A")) {
            holder.retStatus.setText("Approved");
            holder.retStatus.setTextColor(ContextCompat.getColor(con, R.color.color_green));
            setActiveInActive(holder, position);
        } else if (retailerData.get(position).getApprovalStatus().equalsIgnoreCase("R")) {
            holder.retStatus.setText("Rejected");
            holder.retStatus.setTextColor(ContextCompat.getColor(con, R.color.color_red));
            setActiveInActive(holder, position);
        }

        holder.channelNameTxt.setText(String.valueOf(retailerData.get(position).getCustomerCode()+" / Channel-" + retailerData.get(position).getChannelName()));

        if (status) {

            setClassNameToInitialAndColor(holder, position);

            holder.initial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(con, retailerData.get(position).getClassName(), Toast.LENGTH_SHORT).show();

                }
            });


            if (retailerData.get(position).getLatitude().length() > 4
                    && retailerData.get(position).getLongitude().length() > 4) {
                holder.pinIcon.setVisibility(View.VISIBLE);
                holder.pinIcon.setColorFilter(ContextCompat.getColor(con, R.color.color_green));

            } else {
                holder.pinIcon.setVisibility(View.INVISIBLE);
                holder.pinIcon.setColorFilter(ContextCompat.getColor(con, R.color.color_red));

            }

        } else {

            setUserInitialTextAndColor(holder, position);

            holder.editImg.setVisibility(View.VISIBLE);
            holder.editImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mEditClickListener != null) {
                        mEditClickListener.onItemClick(view, position);
                    }
                }
            });

        }

    }

    private void setActiveInActive(PendingViewHolder holder, int position) {
        if (retailerData.get(position).getRetailerStatus().equalsIgnoreCase("Y")) {
            holder.statusTxt.setText(" - Active");
            holder.statusTxt.setTextColor(ContextCompat.getColor(con, R.color.color_green));
        } else {
            holder.statusTxt.setText(" - InActive");
            holder.statusTxt.setTextColor(ContextCompat.getColor(con, R.color.color_red));
        }
    }

    private void setClassNameToInitialAndColor(@NonNull PendingViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Drawable drawable = ContextCompat.getDrawable(con, R.drawable.blue_circle_btn);
        assert drawable != null;
        drawable.setColorFilter(ContextCompat.getColor(con, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        String lastChar = retailerData.get(position).getClassName().substring(retailerData.get(position).getClassName().length() - 1);

        holder.initial.setBackground(drawable);

        if (lastChar.equalsIgnoreCase("A") || lastChar.equalsIgnoreCase("B")
                || lastChar.equalsIgnoreCase("C")) {
            holder.initial.setText(lastChar.toUpperCase(Locale.getDefault()));
        } else {
            if (retailerData.get(position).getClassName() != null &&
                    !retailerData.get(position).getClassName().isEmpty()) {
                holder.initial.setText(String.valueOf(retailerData.get(position).getClassName().charAt(0) + ""
                        + getStringChar(retailerData.get(position).getClassName())));
            }
        }
    }

    private void setUserInitialTextAndColor(@NonNull PendingViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String userInitial = "";
        userInitial = String.valueOf(retailerData.get(position).getCustomerName().charAt(0));
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

                    if (p.getCustomerName().toLowerCase(Locale.getDefault()).contains(charText)) {
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

    private String getStringChar(String input) {

        String charcter = null;
        String[] myName = input.split(" ");
        for (String aMyName : myName) {
            charcter = String.valueOf(aMyName.charAt(0));
        }
        return charcter;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnLocationClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnEditClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(View view, int position);
    }

    public class PendingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final View mView;
        final TextView initial;
        final TextView retName;
        final ImageView pinIcon;
        final TextView channelNameTxt;
        final ImageView editImg;
        final TextView retStatus;
        final TextView statusTxt;

        PendingViewHolder(View view) {
            super(view);
            mView = view;
            initial = view.findViewById(R.id.initial);
            retName = view.findViewById(R.id.retailer_name_txt);
            pinIcon = view.findViewById(R.id.pin_icon);
            editImg = view.findViewById(R.id.outlet_edit_icon);
            channelNameTxt = view.findViewById(R.id.retr_channel_name_txt);
            retStatus  = view.findViewById(R.id.retr_status_txt);
            statusTxt  = view.findViewById(R.id.status_txt);
            mView.setOnClickListener(this);
            mView.setOnLongClickListener(this);
            pinIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mlocationClickListener != null) {
                        mlocationClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });

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
