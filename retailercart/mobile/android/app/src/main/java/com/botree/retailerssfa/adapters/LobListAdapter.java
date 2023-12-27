package com.botree.retailerssfa.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.LobModel;

import java.util.List;

/**
 * Created by shantarao on 12/1/18.
 */

public class LobListAdapter extends RecyclerView.Adapter<LobListAdapter.LobViewHolder> {
    private List<LobModel> lobModelList;
    Context context;

    public LobListAdapter(Context context, List<LobModel> lobModelList) {
        this.context = context;
        this.lobModelList = lobModelList;
    }

    @NonNull
    @Override
    public LobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lob_list_adapter_layout, parent, false);
        return new LobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LobViewHolder holder, int position) {
        holder.lobCode.setText(lobModelList.get(position).getLobCode());
        holder.lobName.setText(String.valueOf(lobModelList.get(position).getLobName()));
        holder.serialNo.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return lobModelList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class LobViewHolder extends RecyclerView.ViewHolder {
        TextView lobName;
        TextView lobCode;
        TextView serialNo;

        LobViewHolder(View view) {
            super(view);
            lobName = view.findViewById(R.id.lob_name);
            lobCode = view.findViewById(R.id.lob_code);
            serialNo = view.findViewById(R.id.serial_no);
        }
    }

}


