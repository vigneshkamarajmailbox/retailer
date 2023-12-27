package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.OrderBookingDetailsModel;
import com.botree.retailerssfa.models.OrderBookingHeaderModel;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class OrderReportExpListAdapter extends CommonExpListAdapter {
    private Context context;
    private Map<String, List<OrderBookingDetailsModel>> childtems;
    private LayoutInflater inflater;
    private List<String> parentItems;
    private List<OrderBookingHeaderModel> parentlist;

    public OrderReportExpListAdapter(Context context, List<String> parentdata,
                                     final Map<String, List<OrderBookingDetailsModel>> childtems,
                                     List<OrderBookingHeaderModel> parentList) {

        this.context = context;
        this.parentItems = parentdata;
        this.childtems = childtems;
        this.parentlist = parentList;

        setCommonExpListAdapterInterface(new CommonExpListAdapterInterface() {
            @Override
            public View onGetGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                return getParentView(groupPosition, isExpanded, convertView, parent);
            }

            @Override
            public View onGetChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                return getChildView(groupPosition, childPosition, convertView, parent);
            }

            @Override
            public int onGetGroupCount() {
                return parentItems.size();
            }

            @Override
            public int onGetChildrenCount(int groupPosition) {
                return childtems.get(parentItems.get(groupPosition)).size() + 1;
            }
        });
    }


    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @NonNull
    private View getParentView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.order_report_header, parent, false);
        }

        TextView orderNo = convertView.findViewById(R.id.order_no);
        TextView salesman = convertView.findViewById(R.id.salesman);
        TextView route = convertView.findViewById(R.id.route);
        TextView customer = convertView.findViewById(R.id.customer);
        TextView orderDate = convertView.findViewById(R.id.order_date);
        TextView totalOrderValue = convertView.findViewById(R.id.total_order_value);
        TextView orderStatus = convertView.findViewById(R.id.order_status);

        orderNo.setText(parentlist.get(groupPosition).getCustomerRefNo());
        salesman.setText(String.valueOf(parentlist.get(groupPosition).getCustomerName()));
        customer.setText(String.valueOf(parentlist.get(groupPosition).getCustomerCode()));
        route.setText(String.valueOf(parentlist.get(groupPosition).getCustomerShipCode()));
        orderDate.setText(String.valueOf(parentlist.get(groupPosition).getOrderDt()));
        orderStatus.setText(String.valueOf(parentlist.get(groupPosition).getOrderStatus()));

        if (!parentlist.get(groupPosition).getOrderStatus().isEmpty() &&
                parentlist.get(groupPosition).getOrderStatus().equalsIgnoreCase("A")) {
            orderStatus.setTextColor(ContextCompat.getColor(context, R.color.color_green));
        } else {
            orderStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        String currency = "₹ {0}";
        totalOrderValue.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f",
                parentlist.get(groupPosition).getTotalOrderValue())));


        return convertView;
    }

    @NonNull
    private View getChildView(int groupPosition, int childPosition, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.order_report_details_layout, parent, false);

            childViewHolder = new ChildViewHolder();

            childViewHolder.prodName = convertView.findViewById(R.id.prod_name);
            childViewHolder.orderQty = convertView.findViewById(R.id.order_qty);
            childViewHolder.servicedQty = convertView.findViewById(R.id.serviced_qty);
            childViewHolder.sellRate = convertView.findViewById(R.id.sell_rate);
            childViewHolder.orderVal = convertView.findViewById(R.id.order_value);

            convertView.setTag(R.layout.order_report_details_layout, childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag(R.layout.order_report_details_layout);
        }

        if (childPosition == 0) {
            childViewHolder.prodName.setText(context.getResources().getText(R.string.product_name));
            childViewHolder.orderQty.setText(context.getResources().getText(R.string.order_qty));
            childViewHolder.servicedQty.setText(context.getResources().getText(R.string.serviced_qty));
            childViewHolder.sellRate.setText(context.getResources().getText(R.string.sell_rate));
            childViewHolder.orderVal.setText(context.getResources().getText(R.string.order_value));

            childViewHolder.prodName.setTypeface(null, Typeface.BOLD);
            childViewHolder.orderQty.setTypeface(null, Typeface.BOLD);
            childViewHolder.servicedQty.setTypeface(null, Typeface.BOLD);
            childViewHolder.sellRate.setTypeface(null, Typeface.BOLD);
            childViewHolder.orderVal.setTypeface(null, Typeface.BOLD);

        } else {
            childViewHolder.prodName.setTypeface(null, Typeface.NORMAL);
            childViewHolder.orderQty.setTypeface(null, Typeface.NORMAL);
            childViewHolder.servicedQty.setTypeface(null, Typeface.NORMAL);
            childViewHolder.sellRate.setTypeface(null, Typeface.NORMAL);
            childViewHolder.orderVal.setTypeface(null, Typeface.NORMAL);

            final List<OrderBookingDetailsModel> childtem = childtems.get(parentItems.get(groupPosition));

            childViewHolder.prodName.setText(childtem.get(childPosition - 1).getProdCode());
            childViewHolder.orderQty.setText(childtem.get(childPosition - 1).getOrderQty()
                    + " " + childtem.get(childPosition - 1).getUomCode());
            childViewHolder.servicedQty.setText(childtem.get(childPosition - 1).getServicedQty()
                    + " " + childtem.get(childPosition - 1).getUomCode());

            childViewHolder.sellRate.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getSellRate()));
            childViewHolder.orderVal.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getOrderValue()));

        }
        return convertView;
    }

    private final class ChildViewHolder {

        private TextView prodName;
        private TextView orderQty;
        private TextView servicedQty;
        private TextView sellRate;
        private TextView orderVal;
    }
}