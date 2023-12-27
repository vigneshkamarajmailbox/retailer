package com.botree.retailerssfa.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.util.OrderSupportUtil;

import java.util.List;
import java.util.Locale;

public class OpenStockTempListItemAdapter extends RecyclerView.Adapter<OpenStockTempListItemAdapter.MyViewHolder> {

    private OnItemClickListener mItemClickListener;
    private OnDeleteItemListener mDeleteClickListener;

    private List<OrderBookingVO> bookingVOS;
    private Context context;

    public OpenStockTempListItemAdapter(Context con, List<OrderBookingVO> stockList) {

        this.bookingVOS = stockList;
        this.context = con;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnDeleteClickListener(OnDeleteItemListener mDeleteClickListener) {
        this.mDeleteClickListener = mDeleteClickListener;
    }

    public String getValueAt(int position) {
        return bookingVOS.get(position).getProdCode();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_stock_temp_list_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.productName.setText(bookingVOS.get(position).getProdName());
        holder.lTotal.setText(String.format(Locale.getDefault(), "%.2f",
                bookingVOS.get(position).getlGrossAmt()));
        holder.totQtyTxt.setText(String.valueOf(OrderSupportUtil.getInstance().getStringQty(bookingVOS.get(position).getQuantity()) + " " +
                replaceNullWithEmprty(bookingVOS.get(position).getUomId())));
//        if (bookingVOS.get(position).getTotInvoiceQty() == 0 && bookingVOS.get(position).getFreeQty() > 0) {
//            holder.lTotal.setText("0.0");
//        }
    }

    private String replaceNullWithEmprty(String uomId) {
        if (uomId == null)
            return "";
        else
            return uomId;
    }

    @Override
    public int getItemCount() {
        return bookingVOS.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    public interface OnDeleteItemListener {
        void onDeleteClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final TextView productName;
        private final TextView lTotal;
        private final TextView totQtyTxt;
        private ImageView mIvDelete;
        private ImageView mImgDelete;
        private ImageView textViewOptions;

        MyViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.bill_product_name_txt);
            lTotal = itemView.findViewById(R.id.bill_ltotal_txt);
            totQtyTxt = itemView.findViewById(R.id.tot_bill_qty);
            mIvDelete = itemView.findViewById(R.id.ivDelete);
            mImgDelete = itemView.findViewById(R.id.imgDelete);
            textViewOptions = itemView.findViewById(R.id.textViewOptions);
            mIvDelete.setOnClickListener(this);
            mImgDelete.setOnClickListener(this);

            textViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(context, textViewOptions);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.menu_options);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.menuEdit:
                                    //handle menu1 click
                                    if (mItemClickListener != null) {
                                        mItemClickListener.onItemClick(view, getAdapterPosition());
                                    }
                                    break;
                                case R.id.menuDelete:
                                    //handle menu2 click
                                    mDeleteClickListener.onDeleteClick(view, getAdapterPosition());
                                    break;

                                    default:
                                        break;
                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();
                }
            });

        }

        @Override
        public String toString() {
            return super.toString() + " '" + productName.getText();
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ivDelete) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } else if (v.getId() == R.id.imgDelete && mDeleteClickListener != null) {
                    mDeleteClickListener.onDeleteClick(v, getAdapterPosition());
                }
        }

    }
}
