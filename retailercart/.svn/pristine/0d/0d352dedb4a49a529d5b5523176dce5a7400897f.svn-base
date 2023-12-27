/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.OrderBookingVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AutoCompleteAdapter extends ArrayAdapter<OrderBookingVO> {

    private static final String LOG_TAG = AutoCompleteAdapter.class.getSimpleName();
    private final Context mContext;
    private final List<OrderBookingVO> stockTakeVOs;
    private final List<OrderBookingVO> stockTakeVOAll;
    private final List<String> productNames;
    private final List<OrderBookingVO> stockTakeVOSuggestion;
    private final int mLayoutResourceId;

    public AutoCompleteAdapter(Context context, int resource, List<OrderBookingVO> bookingVOS) {
        super(context, resource, bookingVOS);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.stockTakeVOs = new ArrayList<>(bookingVOS);
        this.stockTakeVOAll = new ArrayList<>(bookingVOS);
        this.stockTakeVOSuggestion = new ArrayList<>();

        productNames = new ArrayList<>();
        for(OrderBookingVO orderBookingVO : bookingVOS){
            if(orderBookingVO.getProdName()!=null){
                productNames.add(orderBookingVO.getProdName());
            }
        }
    }

    @Override
    public int getCount() {
        return stockTakeVOs.size();

    }

    public List<String> getAllItems(){
        return productNames;
    }

    @Override
    public OrderBookingVO getItem(int position) {
        return stockTakeVOs.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (view == null) {
                LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                view = inflater.inflate(mLayoutResourceId, parent, false);
            }
            OrderBookingVO stockTakeVO = getItem(position);
            TextView name = view.findViewById(R.id.autoText);
            name.setText(stockTakeVO != null ? stockTakeVO.getProdName() : "");
        } catch (Exception e) {
            Log.e(LOG_TAG, "getView: " + e.getMessage(), e);
        }
        return view;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((OrderBookingVO) resultValue).getProdName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    stockTakeVOSuggestion.clear();
                    return getFilterResults(constraint);
                } else {
                    return new FilterResults();
                }
            }

            @NonNull
            private FilterResults getFilterResults(CharSequence constraint) {
                for (OrderBookingVO stockTakeVO : stockTakeVOAll) {
                    getStockSuggestionProduct(constraint, stockTakeVO);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = stockTakeVOSuggestion;
                filterResults.count = stockTakeVOSuggestion.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                stockTakeVOs.clear();
                try {
                    if (results != null && results.count > 0) {
                        List<?> result = (List<?>) results.values;
                        for (Object object : result) {
                            objectCastToStockTake(object);
                        }
                    } else if (constraint == null) {
                        // no filter, add entire original list back in
                        stockTakeVOs.addAll(stockTakeVOAll);
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, "publishResults: " + e.getMessage(), e);
                }
                notifyDataSetChanged();
            }
        };
    }

    private void objectCastToStockTake(Object object) {
        if (object instanceof OrderBookingVO) {
            stockTakeVOs.add((OrderBookingVO) object);
        }
    }

    private void getStockSuggestionProduct(CharSequence constraint, OrderBookingVO stockTakeVO) {
        if (stockTakeVO.getProdName().toLowerCase(Locale.getDefault())
                .contains(constraint.toString().toLowerCase(Locale.getDefault()))
                || stockTakeVO.getProdCode().toLowerCase(Locale.getDefault())
                        .contains(constraint.toString().toLowerCase(Locale.getDefault()))) {
            stockTakeVOSuggestion.add(stockTakeVO);
        }
    }
}