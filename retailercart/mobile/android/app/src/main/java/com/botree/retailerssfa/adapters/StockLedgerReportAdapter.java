package com.botree.retailerssfa.adapters;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.StockledgerReportModel;
import java.util.List;

public class StockLedgerReportAdapter extends RecyclerView.Adapter<StockLedgerReportAdapter.StockViewHolder> {

    private final List<StockledgerReportModel> reportList;

    public StockLedgerReportAdapter(List<StockledgerReportModel> list) {
        this.reportList = list;
    }
    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_ledger_report_list_item, parent, false);
        return new StockLedgerReportAdapter.StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StockViewHolder holder, int position) {

        holder.transDatelistItem.setText(reportList.get(position).getTransDate());
        holder.prodnamelistItem.setText(reportList.get(position).getProductName());
        holder.openingSalableQtylistItem.setText(reportList.get(position).getOpeningSalableQty());
        holder.openingUnSalableQtylistItem.setText(reportList.get(position).getOpeningUnSalableQty());
        holder.openingOfferQtylistItem.setText(reportList.get(position).getOpeningOfferQty());
        holder.purchaseSalableQtylistItem.setText(reportList.get(position).getPurchaseRetSalableQty());
        holder.purchaseUnSalableQtylistItem.setText(reportList.get(position).getPurchaseUnSalableQty());
        holder.purchaseOfferQtylistItem.setText(reportList.get(position).getPurchaseOfferQty());
        holder.purchaseRetSalableQtylistItem.setText(reportList.get(position).getPurchaseRetSalableQty());
        holder.purchaseRetUnSalableQtylistItem.setText(reportList.get(position).getPurchaseRetUnSalableQty());
        holder.purchaseRetOfferQtylistItem.setText(reportList.get(position).getPurchaseRetOfferQty());
        holder.salesSalableQtylistItem.setText(reportList.get(position).getSalesSalableQty());
        holder.salesUnSalableQtylistItem.setText(reportList.get(position).getSalesUnSalableQty());
        holder.salesOfferQtylistItem.setText(reportList.get(position).getSalesOfferQty());
        holder.salesRetSalableQtylistitem.setText(reportList.get(position).getSalesRetSalableQty());
        holder.salesRetUnSalableQtylistItem.setText(reportList.get(position).getSalesRetUnSalableQty());
        holder.salesRetOfferQtylistItem.setText(reportList.get(position).getSalesRetOfferQty());
        holder.adjInSalableQtylistItem.setText(reportList.get(position).getAdjInSalableQty());
        holder.adjInUnSalableQty.setText(reportList.get(position).getAdjInUnSalableQty());
        holder.adjInOfferQtylistItem.setText(reportList.get(position).getAdjInOfferQty());
        holder.adjOutUnSalableQtylistItem.setText(reportList.get(position).getAdjOutUnSalableQty());
        holder.adjOutOfferQtylistItem.setText(reportList.get(position).getAdjOutOfferQty());
        holder.closingStkUnSalableQtylistItem.setText(reportList.get(position).getClosingStkUnSalableQty());
        holder.closingStkOfferQtylistItem.setText(reportList.get(position).getClosingStkOfferQty());
        holder.closingStkSalableQtylistItem.setText(reportList.get(position).getClosingStkSalableQty());
        holder.adjOutSalableQtylistItem.setText(reportList.get(position).getAdjOutSalableQty());


    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    class StockViewHolder extends RecyclerView.ViewHolder {

        private TextView transDatelistItem;
        private TextView prodnamelistItem;
        private TextView openingSalableQtylistItem;
        private TextView openingUnSalableQtylistItem;
        private TextView openingOfferQtylistItem;
        private TextView purchaseSalableQtylistItem;
        private TextView purchaseUnSalableQtylistItem;
        private TextView purchaseOfferQtylistItem;
        private TextView purchaseRetSalableQtylistItem;
        private TextView purchaseRetUnSalableQtylistItem;
        private TextView purchaseRetOfferQtylistItem;
        private TextView salesSalableQtylistItem;
        private TextView salesUnSalableQtylistItem;
        private TextView salesOfferQtylistItem;

        private TextView salesRetSalableQtylistitem;
        private TextView salesRetUnSalableQtylistItem;
        private TextView salesRetOfferQtylistItem;
        private TextView adjInSalableQtylistItem;
        private TextView adjInUnSalableQty;
        private TextView adjInOfferQtylistItem;
        private TextView adjOutSalableQtylistItem;
        private TextView adjOutUnSalableQtylistItem;
        private TextView adjOutOfferQtylistItem;
        private TextView closingStkUnSalableQtylistItem;
        private TextView closingStkSalableQtylistItem;
        private TextView closingStkOfferQtylistItem;

        private StockViewHolder(View itemView) {
            super(itemView);

            transDatelistItem=itemView.findViewById(R.id.trans_Date_listItem);
            prodnamelistItem=itemView.findViewById(R.id.prodname_listItem);
            openingSalableQtylistItem=itemView.findViewById(R.id.Opening_SalableQty_listItem);
            openingUnSalableQtylistItem=itemView.findViewById(R.id.Opening_UnSalableQty_listItem);
            openingOfferQtylistItem=itemView.findViewById(R.id.Opening_OfferQty_listItem);
            purchaseSalableQtylistItem=itemView.findViewById(R.id.Purchase_SalableQty_listItem);
            purchaseUnSalableQtylistItem=itemView.findViewById(R.id.Purchase_UnSalableQty_listItem);
            purchaseOfferQtylistItem=itemView.findViewById(R.id.Purchase_OfferQty_listItem);
            purchaseRetSalableQtylistItem=itemView.findViewById(R.id.PurchaseRet_SalableQty_listItem);
            purchaseRetUnSalableQtylistItem=itemView.findViewById(R.id.PurchaseRet_UnSalableQty_listItem);
            purchaseRetOfferQtylistItem=itemView.findViewById(R.id.PurchaseRet_OfferQty_listItem);
            salesSalableQtylistItem=itemView.findViewById(R.id.Sales_SalableQty_listItem);
            salesUnSalableQtylistItem=itemView.findViewById(R.id.Sales_UnSalableQty_listItem);
            salesOfferQtylistItem=itemView.findViewById(R.id.Sales_OfferQty_listItem);
            salesRetSalableQtylistitem=itemView.findViewById(R.id.SalesRet_SalableQty_list_item);
            salesRetUnSalableQtylistItem=itemView.findViewById(R.id.SalesRet_UnSalableQty_listItem);
            salesRetOfferQtylistItem=itemView.findViewById(R.id.SalesRet_OfferQty_listItem);
            adjInSalableQtylistItem=itemView.findViewById(R.id.AdjIn_SalableQty_listItem);
            adjInUnSalableQty=itemView.findViewById(R.id.AdjInUnSalableQty);
            adjInOfferQtylistItem=itemView.findViewById(R.id.AdjInOfferQty_listItem);
            adjOutSalableQtylistItem=itemView.findViewById(R.id.AdjOutSalableQty_listItem);
            adjOutUnSalableQtylistItem=itemView.findViewById(R.id.AdjOutUnSalableQty_listItem);
            adjOutOfferQtylistItem=itemView.findViewById(R.id.AdjOutOfferQty_listItem);
            closingStkUnSalableQtylistItem=itemView.findViewById(R.id.ClosingStkUnSalableQty_listItem);
            closingStkSalableQtylistItem=itemView.findViewById(R.id.ClosingStkSalableQty_listItem);
            closingStkOfferQtylistItem=itemView.findViewById(R.id.ClosingStkOfferQty_listItem);
        }
    }


}

