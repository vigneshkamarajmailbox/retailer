/*
 * Copyright (C) 2016 Botree Software International Private Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.botree.retailerssfa.adapters;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.ReasonVO;
import com.botree.retailerssfa.models.SalesReturnVO;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SalesReturnAdapter extends RecyclerView.Adapter {


    private final int holderType;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener monItemLongClicked;

    private List<SalesReturnVO> salesReturnList;
    private Context context;
    private SFADatabase db;
    private String reasonUnsalecode;
    private String reasonSalecode;
    private int sQty;
    private int uQty;
    private Spinner spinnerReasonUnsale;
    private Spinner spinnerReasonSale;
    private EditText editUnsaledtQuantity;
    private EditText editsaledQuantity;
    private CalculateSalesReturnTotalOrder calculateSalesReturnTotalOrder;


    public SalesReturnAdapter(List<SalesReturnVO> salesReturnList, int holderType, Context con, CalculateSalesReturnTotalOrder calculateSalesReturnTotalOrder) {

        this.salesReturnList = salesReturnList;
        this.holderType = holderType;
        this.context = con;
        db = SFADatabase.getInstance(context);
        this.calculateSalesReturnTotalOrder = calculateSalesReturnTotalOrder;

    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener mItemLongClicked) {
        this.monItemLongClicked = mItemLongClicked;
    }

    public String getValueAt(int position) {
        return salesReturnList.get(position).getProdCode();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (holderType == SalesReturnVO.SALES_RETURN_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_sales_return, parent, false);
            return new SalesReturnViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_return_list_item, parent, false);
            return new PurchaseReturnViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holderType == SalesReturnVO.SALES_RETURN_TYPE) {
            ((SalesReturnViewHolder) holder).productName.setText(salesReturnList.get(position).getProdShortName());
            ((SalesReturnViewHolder) holder).lTotal.setText(String.format(Locale.getDefault(), "%.2f",
                    salesReturnList.get(position).getSellPrice()));
            ((SalesReturnViewHolder) holder).unsalqty.setText(String.valueOf(salesReturnList.get(position).getUnSalQty()));
            ((SalesReturnViewHolder) holder).salqty.setText(String.valueOf(salesReturnList.get(position).getSalQty()));


            ((SalesReturnViewHolder) holder).linearLayoutEditQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    editDialog(position);
                }
            });


        } else if (holderType == SalesReturnVO.PURCHASE_RETURN_TYPE) {
            ((PurchaseReturnViewHolder) holder).productName.setText(salesReturnList.get(position).getProdShortName());
            ((PurchaseReturnViewHolder) holder).tvSellPrice.setText(String.format(Locale.getDefault(), "%.2f",
                    salesReturnList.get(position).getSellPrice()));
            ((PurchaseReturnViewHolder) holder).unsalqty.setText(String.valueOf(salesReturnList.get(position).getUnSalQty()));
            ((PurchaseReturnViewHolder) holder).unsalqtyUom.setText(String.valueOf(salesReturnList.get(position).getUnSalUomCode()));
            ((PurchaseReturnViewHolder) holder).salQty.setText(String.valueOf(salesReturnList.get(position).getSalQty()));
            ((PurchaseReturnViewHolder) holder).salQtyUom.setText(String.valueOf(salesReturnList.get(position).getUomCode()));
        }

    }

    private void editDialog(final int position) {
        final Dialog dialog = new Dialog((context), R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogeditsalesreturnproduct);


        TextView salesreturnProductShortNameTxt = dialog.findViewById(R.id.salesreturn_productShort_name_txt);
        Button submit = dialog.findViewById(R.id.editSalesreturn_submit_btn);
        Button cancel = dialog.findViewById(R.id.editSalesreturn_cancel_btn);
        spinnerReasonUnsale = dialog.findViewById(R.id.spinnerSalesReasonUnsaleQty);
        spinnerReasonSale = dialog.findViewById(R.id.SaleablespinnerSalesReason);
        LinearLayout linearLayoutSalQty = dialog.findViewById(R.id.linaerLayoutSalQty);
        LinearLayout linearLayoutUnsalableQty = dialog.findViewById(R.id.linearLayoutUnsalableQty);
        editUnsaledtQuantity = dialog.findViewById(R.id.editUnsaledtQuantity);
        editsaledQuantity = dialog.findViewById(R.id.edtsaledQuantitySalQty);

        editsaledQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editsaledQuantity.setCursorVisible(true);

            }
        });

        editUnsaledtQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUnsaledtQuantity.setCursorVisible(true);

            }
        });


        // product short name
        String prodShortName = salesReturnList.get(position).getProdShortName();

        sQty = salesReturnList.get(position).getSalQty();
        uQty = salesReturnList.get(position).getUnSalQty();

        editUnsaledtQuantity.setText(String.valueOf(salesReturnList.get(position).getUnSalQty()));
        editsaledQuantity.setText(String.valueOf(salesReturnList.get(position).getSalQty()));


        if (sQty == 0) {
            linearLayoutSalQty.setVisibility(View.GONE);
        } else if (uQty == 0) {
            linearLayoutUnsalableQty.setVisibility(View.GONE);
        } else {
            linearLayoutSalQty.setVisibility(View.VISIBLE);
            linearLayoutUnsalableQty.setVisibility(View.VISIBLE);
        }

        salesreturnProductShortNameTxt.setText(prodShortName);

        List<String> reasonname = new ArrayList<>();
        final List<String> reasonCode;
        reasonCode = new ArrayList<>();
        List<ReasonVO> reasons = db.loadReasons("Sales Return");

        reasonname.add("Choose Reason");
        reasonCode.add("0");

        for (int i = 0; i < reasons.size(); i++) {
            reasonname.add(reasons.get(i).getReasonName());
            reasonCode.add(reasons.get(i).getReasonCode());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_list_item, reasonname);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReasonSale.setAdapter(adapter);
        spinnerReasonUnsale.setAdapter(adapter);

        spinnerReasonSale.setSelection(adapter.getPosition(salesReturnList.get(position).getReasonName()));
        spinnerReasonUnsale.setSelection(adapter.getPosition(salesReturnList.get(position).getUnSalReasonName()));


        spinnerReasonUnsale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                reasonUnsalecode = reasonCode.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //ignored

            }
        });


        spinnerReasonSale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                reasonSalecode = reasonCode.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //ignored

            }
        });


        // if button is clicked, close the custom dialog
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SalesReturnVO salesReturnVO = new SalesReturnVO();
                salesReturnVO.setProdBatchCode(salesReturnList.get(position).getProdBatchCode());
                salesReturnVO.setProdCode(salesReturnList.get(position).getProdCode());
                salesReturnVO.setProdShortName(salesReturnList.get(position).getProdShortName());
                salesReturnVO.setRetlrCode(salesReturnList.get(position).getRetlrCode());
                salesReturnVO.setSellPrice(salesReturnList.get(position).getSellPrice());

                removeAt(position);

                if (sQty != 0 && uQty == 0) {
                    salesReturnVO.setReasonCode(reasonSalecode);
                    salesReturnVO.setReasonName(spinnerReasonSale.getSelectedItem().toString());
                    salesReturnVO.setSalQty(Integer.parseInt(editsaledQuantity.getText().toString()));
                } else if (uQty != 0 && sQty == 0) {
                    salesReturnVO.setUnSalReasonCode(reasonUnsalecode);
                    salesReturnVO.setUnSalReasonName(spinnerReasonUnsale.getSelectedItem().toString());
                    salesReturnVO.setUnSalQty(Integer.parseInt(editUnsaledtQuantity.getText().toString()));
                } else {
                    salesReturnVO.setReasonCode(reasonSalecode);
                    salesReturnVO.setReasonName(spinnerReasonSale.getSelectedItem().toString());
                    salesReturnVO.setSalQty(Integer.parseInt(editsaledQuantity.getText().toString()));
                    salesReturnVO.setUnSalReasonCode(reasonUnsalecode);
                    salesReturnVO.setUnSalReasonName(spinnerReasonUnsale.getSelectedItem().toString());
                    salesReturnVO.setUnSalQty(Integer.parseInt(editUnsaledtQuantity.getText().toString()));
                }
                salesReturnList.add(salesReturnVO);
                notifyItemRangeChanged(position, salesReturnList.size());
                dialog.dismiss();
                calculateSalesReturnTotalOrder.calculateTotalOrder();


            }

        });


        dialog.show();
    }


    private void removeAt(int position) {

        salesReturnList.remove(position);
        notifyItemRemoved(position);

    }


    @Override
    public int getItemViewType(int position) {
        switch (holderType) {
            case 0:
                return SalesReturnVO.SALES_RETURN_TYPE;
            case 1:
                return SalesReturnVO.PURCHASE_RETURN_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return salesReturnList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(View view, int position);
    }

    public class SalesReturnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


        private final TextView productName;
        private final TextView lTotal;
        private final TextView salqty;
        private final TextView unsalqty;
        private ImageView mIvDelete;
        private LinearLayout linearLayoutEditQty;

        SalesReturnViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.order_product_name_txt);
            lTotal = view.findViewById(R.id.order_ltotal_txt);
            unsalqty = view.findViewById(R.id.order_rate_txt);
            salqty = view.findViewById(R.id.order_qty_txt);
            mIvDelete = view.findViewById(R.id.ivDelete);
            linearLayoutEditQty = view.findViewById(R.id.linearLayoutEditQty);
            mIvDelete.setOnClickListener(this);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + productName.getText();
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

    public class PurchaseReturnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


        private final TextView tvSellPrice;
        private final TextView productName;
        private final TextView salQty;
        private final TextView unsalqty;
        private final TextView salQtyUom;
        private final TextView unsalqtyUom;
        private ImageView mIvDelete;


        PurchaseReturnViewHolder(View view) {
            super(view);
            productName = view.findViewById(R.id.order_product_name_txt);
            salQty = view.findViewById(R.id.sales_qty_txt);
            salQtyUom = view.findViewById(R.id.sales_qty_uom);
            unsalqty = view.findViewById(R.id.unsales_qty_txt);
            unsalqtyUom = view.findViewById(R.id.unsales_qty_uom);
            mIvDelete.setOnClickListener(this);
            mIvDelete = view.findViewById(R.id.ivDelete);
            tvSellPrice = view.findViewById(R.id.tvSellPrice);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + productName.getText();
        }

        @Override
        public boolean onLongClick(View view) {
            if (monItemLongClicked != null) {
                monItemLongClicked.onItemLongClicked(view, getAdapterPosition());
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

    }
}


