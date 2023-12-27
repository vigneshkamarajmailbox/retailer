package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.RetailerVO;

import java.util.List;
import java.util.Locale;


public class PendingRetailerAdapter extends RecyclerView.Adapter<PendingRetailerAdapter.PendingRetailerViewHolder> {

    private static final String TAG = PendingRetailerAdapter.class.getSimpleName();
    private final Context context;
    private final List<RetailerVO> retailerList;

    private OnRecyclerItemClick onRecyclerItemClick;

    public PendingRetailerAdapter(Context context, List<RetailerVO> retailerVOs) {
        this.context = context;
        this.retailerList = retailerVOs;
    }

    public void setOnRecyclerItemClick(OnRecyclerItemClick onRecyclerItemClick) {
        this.onRecyclerItemClick = onRecyclerItemClick;
    }

    @NonNull
    @Override
    public PendingRetailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_retailer_list_item, parent, false);
        return new PendingRetailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PendingRetailerViewHolder holder, int position) {
        try {

            String userInitial = retailerList.get(holder.getAdapterPosition()).getCustomerName();
            String init = userInitial.substring(0, 1);

            holder.initial.setText(init.toUpperCase(Locale.getDefault()));

            holder.initial.setBackground(getDrawable(holder.getAdapterPosition()));

            holder.customerNameTxt.setText(retailerList.get(holder.getAdapterPosition()).getCustomerName().toUpperCase(Locale.getDefault()));
            holder.customerAddTxt.setText(retailerList.get(holder.getAdapterPosition()).getRetailerAddr1());
//

        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.getMessage(),e);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClick != null) {
                    onRecyclerItemClick.onItemClick(holder.getAdapterPosition());
                }
            }

        });

    }

    private Drawable getDrawable(int adapterPosition) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.blue_circle_btn);
        try {
            if (drawable != null) {
                drawable.setColorFilter(retailerList.get(adapterPosition).getBarColor(), PorterDuff.Mode.SRC_ATOP);
            }
        } catch (Exception e) {
            Log.e(TAG, "getDrawable: " + e.getMessage(), e);
            return drawable;
        }
        return drawable;
    }

    @Override
    public int getItemCount() {
        return retailerList.size();
    }

    class PendingRetailerViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView initial;
        private TextView customerNameTxt;
        private TextView customerAddTxt;

        PendingRetailerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            initial = itemView.findViewById(R.id.pending_retr_info_initial);
            customerNameTxt = itemView.findViewById(R.id.pending_retr_name_txt);
            customerAddTxt = itemView.findViewById(R.id.pending_retr_address_txt);
        }
    }
}
