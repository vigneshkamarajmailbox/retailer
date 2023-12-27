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
import com.botree.retailerssfa.models.RetailerVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AutoCompleteRetailerAdapter  extends ArrayAdapter<RetailerVO> {
    private static final String LOG_TAG = AutoCompleteRetailerAdapter.class.getSimpleName();
    private final Context mContext;
    private final List<RetailerVO> retailerVOs;
    private final List<RetailerVO> retailerVOAll;
    private final List<RetailerVO> retailerVOSuggestion;
    private final int mLayoutResourceId;

    public AutoCompleteRetailerAdapter(Context context, int resource, List<RetailerVO> retailerVOS) {
        super(context, resource, retailerVOS);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.retailerVOs = new ArrayList<>(retailerVOS);
        this.retailerVOAll = new ArrayList<>(retailerVOS);
        this.retailerVOSuggestion = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return retailerVOs.size();

    }

    @Override
    public RetailerVO getItem(int position) {
        return retailerVOs.get(position);
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
            RetailerVO retailerVO = getItem(position);
            TextView name = view.findViewById(R.id.autoText);
            name.setText(retailerVO != null ? retailerVO.getCustomerName() : "");
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
                return ((RetailerVO) resultValue).getCustomerName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    retailerVOSuggestion.clear();
                    return getFilterResults(constraint);
                } else {
                    return new FilterResults();
                }
            }

            @NonNull
            private FilterResults getFilterResults(CharSequence constraint) {
                for (RetailerVO retailerVO : retailerVOAll) {
                    getStockSuggestionProduct(constraint, retailerVO);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = retailerVOSuggestion;
                filterResults.count = retailerVOSuggestion.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                retailerVOs.clear();
                try {
                    if (results != null && results.count > 0) {
                        List<?> result = (List<?>) results.values;
                        for (Object object : result) {
                            objectCastToStockTake(object);
                        }
                    } else if (constraint == null) {
                        // no filter, add entire original list back in
                        retailerVOs.addAll(retailerVOAll);
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, "publishResults: " + e.getMessage(), e);
                }
                notifyDataSetChanged();
            }
        };
    }

    private void objectCastToStockTake(Object object) {
        if (object instanceof RetailerVO) {
            retailerVOs.add((RetailerVO) object);
        }
    }

    private void getStockSuggestionProduct(CharSequence constraint, RetailerVO retailerVO) {
        if (retailerVO.getCustomerName().toLowerCase(Locale.getDefault()).startsWith(constraint.toString().toLowerCase(Locale.getDefault()))) {
            retailerVOSuggestion.add(retailerVO);
        }
    }
}
