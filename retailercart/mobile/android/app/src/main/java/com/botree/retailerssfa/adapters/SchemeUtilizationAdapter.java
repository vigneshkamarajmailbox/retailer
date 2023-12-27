package com.botree.retailerssfa.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.SchemeModel;
import com.botree.retailerssfa.util.DateUtil;

import java.util.List;


/**
 * Created by shantarao on 8/11/17.
 */

public class SchemeUtilizationAdapter extends RecyclerView.Adapter<SchemeUtilizationAdapter.SchemeUtilizarionViewHolder> {
    private final List<SchemeModel> schemelList;
    private OnItemClickListener onItemClickListener;

    public SchemeUtilizationAdapter(List<SchemeModel> schemeModelList) {
        this.schemelList = schemeModelList;
    }

    @NonNull
    @Override
    public SchemeUtilizarionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheme_util_list_item, parent, false);
        return new SchemeUtilizarionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemeUtilizarionViewHolder holder, int position) {
        holder.schemeNameTxt.setText(schemelList.get(position).getSchemeDescription());
        holder.schemeCodeTxt.setText(schemelList.get(position).getSchemeCode());
        holder.schemeTypeTxt.setText("Type : "+schemelList.get(position).getSchemeBase());
        holder.fromDate.setText("From : "+ DateUtil.convertTimestampToStringDate(Long.parseLong(schemelList.get(position).getStartDate()))+" - ");
        holder.toDate.setText("To : "+DateUtil.convertTimestampToStringDate(Long.parseLong(schemelList.get(position).getEndDate())));

        if (schemelList.get(position).getIsSchemeUtilize().equalsIgnoreCase("true")) {

            holder.schemeUtilImg.setImageResource(R.drawable.ic_add_scheme);
        } else {

            holder.schemeUtilImg.setImageResource(R.drawable.ic_scheme_util);
        }
    }

    @Override
    public int getItemCount() {
        return schemelList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int adapterPosition);
    }

    class SchemeUtilizarionViewHolder extends RecyclerView.ViewHolder {
        private final TextView schemeNameTxt;
        private final TextView schemeCodeTxt;
        private final TextView schemeTypeTxt;
        private final TextView fromDate;
        private final TextView toDate;
        private final ImageView schemeUtilImg;

        SchemeUtilizarionViewHolder(View itemView) {
            super(itemView);
            schemeNameTxt = itemView.findViewById(R.id.scheme_name_txt);
            schemeCodeTxt = itemView.findViewById(R.id.scheme_code_txt);
            schemeUtilImg = itemView.findViewById(R.id.scheme_util_img);
            schemeTypeTxt = itemView.findViewById(R.id.scheme_type_txt);
            fromDate = itemView.findViewById(R.id.from_date_txt);
            toDate = itemView.findViewById(R.id.to_date_txt);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }

            });
        }

    }
}
