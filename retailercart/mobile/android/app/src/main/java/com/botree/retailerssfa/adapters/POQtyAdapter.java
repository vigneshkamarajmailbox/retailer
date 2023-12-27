package com.botree.retailerssfa.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.util.OrderSupportUtil;

import java.util.List;

public class POQtyAdapter extends RecyclerView.Adapter<POQtyAdapter.PoViewHolder> {
    private List<OrderBookingVO> poQtyList;
    private Context con;

    public POQtyAdapter(List<OrderBookingVO> list, Context context) {
        this.poQtyList = list;
        this.con = context;
    }

    @NonNull
    @Override
    public PoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poqty_list_item, parent, false);
        return new POQtyAdapter.PoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoViewHolder holder, int position) {

        holder.qtyTxt.setText(String.valueOf(OrderSupportUtil.getInstance().getStringQty(poQtyList.get(position).getQuantity())));
        holder.uomTxt.setText(replaceNullWithEmprty(poQtyList.get(position).getUomId()));
        holder.totalTxt.setText(String.valueOf(OrderSupportUtil.getInstance().getStringQty(poQtyList.get(position).getTotQty())));

    }

    private String replaceNullWithEmprty(String uomId) {
        if (uomId == null)
            return "";
        else
            return uomId;
    }

    @Override
    public int getItemCount() {
        return poQtyList.size();
    }

    public class PoViewHolder extends RecyclerView.ViewHolder {
        private TextView qtyTxt;
        private TextView uomTxt;
        private TextView totalTxt;

        PoViewHolder(View view) {
            super(view);
            qtyTxt = view.findViewById(R.id.qty_txt);
            uomTxt = view.findViewById(R.id.uom_txt);
            totalTxt = view.findViewById(R.id.total_txt);
        }
    }
}
