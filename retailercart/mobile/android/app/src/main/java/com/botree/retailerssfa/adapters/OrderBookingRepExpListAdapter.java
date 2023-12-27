package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.interfaces.OnExpandableItemClickTrackOrder;
import com.botree.retailerssfa.models.OrderBookingHeaderModel;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.models.OrderStatusModel;
import com.botree.retailerssfa.util.DateUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class OrderBookingRepExpListAdapter extends CommonExpListAdapter {
    private Context context;
    private Map<String, List<OrderBookingVO>> childtems;
    private Map<String, List<OrderStatusModel>> childItemsTwo;
    private LayoutInflater inflater;
    private List<String> parentItems = new ArrayList<>();
    private List<OrderBookingHeaderModel> parentlist;
    private List<OrderBookingHeaderModel> searchList = new ArrayList<>();
    TextView totGross;
    TextView totNet;
    TextView totDiscount;
    TextView totalTax;
    boolean showCompany;

    private int chileCount = 1;
    private int child = 0;
    private OnExpandableItemClickTrackOrder onExpandableItemClick, onCollapseItemClick;

    public OrderBookingRepExpListAdapter(Context context, boolean showCompany, List<String> parentdata,
                                         final Map<String, List<OrderBookingVO>> childtems,
                                         final Map<String, List<OrderStatusModel>> childItemsTwo,
                                         final List<OrderBookingHeaderModel> parentList, TextView totGrossAmtTxt, TextView totalNetAmtTxt, TextView totalDiscountAmtTxt, TextView totalTaxAmtTxt) {

        this.context = context;
        this.parentItems = parentdata;
        this.childtems = childtems;
        this.childItemsTwo = childItemsTwo;
        this.parentlist = parentList;
        this.showCompany = showCompany;
        this.searchList.addAll(parentlist);

        this.totGross = totGrossAmtTxt;
        this.totNet = totalNetAmtTxt;
        this.totDiscount = totalDiscountAmtTxt;
        this.totalTax = totalTaxAmtTxt;

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
                return parentList.size();
            }

            @Override
            public int onGetChildrenCount(int groupPosition) {
//                return childtems.get(parentItems.get(groupPosition)).size() + 1;
                return chileCount;

            }
        });
    }


    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @NonNull
    private View getParentView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.prev_order_report_header, parent, false);
        }
        TextView salesman = convertView.findViewById(R.id.salesman);
        TextView route = convertView.findViewById(R.id.route);
        TextView customer = convertView.findViewById(R.id.customer);
        TextView invoiceNo = convertView.findViewById(R.id.invoice_no);
        TextView invoiceDt = convertView.findViewById(R.id.invoice_dt);
        TextView discount = convertView.findViewById(R.id.discount);
        TextView tax = convertView.findViewById(R.id.tax);
        TextView netAmt = convertView.findViewById(R.id.net_amt);
        TextView grossAmtTxt = convertView.findViewById(R.id.total_gross_amt_txt);
        CardView headerCardView = convertView.findViewById(R.id.order_summary_header_card);
        CardView headerSelectionCardView = convertView.findViewById(R.id.order_summary_selection_card);
        Button details_btn = convertView.findViewById(R.id.details_btn);
        Button track_btn = convertView.findViewById(R.id.tracking_btn);

        ImageView details_img = convertView.findViewById(R.id.order);

        if (!parentlist.isEmpty()) {
            salesman.setText(parentlist.get(groupPosition).getCmpName());
            route.setText(String.valueOf(parentlist.get(groupPosition).getDistrName()));
//            customer.setText(String.valueOf(parentlist.get(groupPosition).getCustomerName()));
            invoiceNo.setText(String.valueOf(parentlist.get(groupPosition).getCustomerRefNo()));
            invoiceDt.setText(DateUtil.convertTimestampToStringDate(Long.valueOf(parentlist.get(groupPosition).getOrderDt())));

            String currency = " {0}";
            discount.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", parentlist.get(groupPosition).getTotalDiscount())));
            tax.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", parentlist.get(groupPosition).getTotalTax())));
            netAmt.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", parentlist.get(groupPosition).getTotalOrderValue())));
            grossAmtTxt.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", parentlist.get(groupPosition).getTotalGrossValue())));
        }

        if (showCompany) {
            salesman.setVisibility(View.VISIBLE);
        } else {
            salesman.setVisibility(View.GONE);
        }

        details_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("track ClickListener", "Track clicked");
            }
        });

//        headerCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(headerSelectionCardView.getVisibility()==View.VISIBLE)
//                {
//                    headerSelectionCardView.setVisibility(View.GONE);
//                    onCollapseItemClick.setOnCollapseListClick(groupPosition,child);
//                }else
//                {
//                    headerSelectionCardView.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });

//        details_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onExpandableItemClick.setOnExpandableListClick(groupPosition, 0);
//            }
//        });

//        details_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onExpandableItemClick.setOnExpandableListClick(groupPosition, 0);
//            }
//        });
//
//        track_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onExpandableItemClick.setOnExpandableListClick(groupPosition, 1);
//            }
//        });
        return convertView;
    }

    @NonNull
    private View getChildView(int groupPosition, int childPosition, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;


        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.prev_order_report_details, parent, false);

            childViewHolder = new ChildViewHolder();

            childViewHolder.prodCode = convertView.findViewById(R.id.prod_code_txt);
            childViewHolder.prodName = convertView.findViewById(R.id.prod_name);
            childViewHolder.quantity = convertView.findViewById(R.id.qty);
            childViewHolder.netAmtTt = convertView.findViewById(R.id.net_amt);
            childViewHolder.taxTxt = convertView.findViewById(R.id.tax);
            childViewHolder.dicountTxt = convertView.findViewById(R.id.discount);
            childViewHolder.sellRate = convertView.findViewById(R.id.sell_rate);
            childViewHolder.grossAmtTxt = convertView.findViewById(R.id.lgross_txt);
            childViewHolder.otherDiscountTxt = convertView.findViewById(R.id.other_discount);

            childViewHolder.orderStatusTimeTv = convertView.findViewById(R.id.tv_time_order_Status);
            childViewHolder.orderStatusDateTv = convertView.findViewById(R.id.tv_date_order_Status);
            childViewHolder.orderStatusTv = convertView.findViewById(R.id.tv_order_Status);

            childViewHolder.selection_rl = convertView.findViewById(R.id.selection_rl);
            childViewHolder.details_ll = convertView.findViewById(R.id.details_ll);
            childViewHolder.tracking_rl = convertView.findViewById(R.id.tracking_rl);
            childViewHolder.orderStatus_ll = convertView.findViewById(R.id.order_Status_ll);
            childViewHolder.orderStatusNoData = convertView.findViewById(R.id.tv_order_status_no_data);


            childViewHolder.trackingBtn = convertView.findViewById(R.id.tracking_btn);
            childViewHolder.detailsBtn = convertView.findViewById(R.id.details_btn);
            childViewHolder.dividerView = convertView.findViewById(R.id.divider_view);


            convertView.setTag(R.layout.prev_order_report_details, childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag(R.layout.prev_order_report_details);
        }

//        childViewHolder.selection_rl.setVisibility(View.VISIBLE);

        if (childPosition == 0) {
            childViewHolder.selection_rl.setVisibility(View.VISIBLE);
        } else {
            childViewHolder.selection_rl.setVisibility(View.GONE);
        }

        if (child == 0) {
            childViewHolder.details_ll.setVisibility(View.VISIBLE);
            childViewHolder.tracking_rl.setVisibility(View.GONE);
            if (childPosition == 0) {
                childViewHolder.prodCode.setText(context.getResources().getText(R.string.product_code));
                childViewHolder.prodName.setText(context.getResources().getText(R.string.product_name));
                childViewHolder.quantity.setText(context.getResources().getText(R.string.qty));
                childViewHolder.netAmtTt.setText(context.getResources().getText(R.string.net_amt));
                childViewHolder.taxTxt.setText(context.getResources().getText(R.string.tax_amt));
                childViewHolder.dicountTxt.setText(context.getResources().getText(R.string.scheme_discount));
                childViewHolder.sellRate.setText(context.getResources().getText(R.string.sell_rate));
                childViewHolder.grossAmtTxt.setText(context.getResources().getText(R.string.gross_amt));
                childViewHolder.otherDiscountTxt.setText(context.getResources().getText(R.string.discount));

                childViewHolder.prodCode.setTypeface(null, Typeface.BOLD);
                childViewHolder.prodName.setTypeface(null, Typeface.BOLD);
                childViewHolder.quantity.setTypeface(null, Typeface.BOLD);
                childViewHolder.netAmtTt.setTypeface(null, Typeface.BOLD);
                childViewHolder.dicountTxt.setTypeface(null, Typeface.BOLD);
                childViewHolder.taxTxt.setTypeface(null, Typeface.BOLD);
                childViewHolder.sellRate.setTypeface(null, Typeface.BOLD);
                childViewHolder.grossAmtTxt.setTypeface(null, Typeface.BOLD);
                childViewHolder.otherDiscountTxt.setTypeface(null, Typeface.BOLD);

            } else {

                childViewHolder.prodCode.setTypeface(null, Typeface.NORMAL);
                childViewHolder.prodName.setTypeface(null, Typeface.NORMAL);
                childViewHolder.quantity.setTypeface(null, Typeface.NORMAL);
                childViewHolder.netAmtTt.setTypeface(null, Typeface.NORMAL);
                childViewHolder.dicountTxt.setTypeface(null, Typeface.NORMAL);
                childViewHolder.taxTxt.setTypeface(null, Typeface.NORMAL);
                childViewHolder.sellRate.setTypeface(null, Typeface.NORMAL);
                childViewHolder.grossAmtTxt.setTypeface(null, Typeface.NORMAL);
                childViewHolder.otherDiscountTxt.setTypeface(null, Typeface.NORMAL);

                final List<OrderBookingVO> childtem = childtems.get(parentItems.get(groupPosition));

                double dicountAmt = childtem.get(childPosition - 1).getSchemeAmount().doubleValue();

                childViewHolder.prodCode.setText(childtem.get(childPosition - 1).getProdCode());
                childViewHolder.prodName.setText(childtem.get(childPosition - 1).getProdName());
                childViewHolder.quantity.setText(childtem.get(childPosition - 1).getInputStr());
                childViewHolder.sellRate.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getActualSellRate()));
                childViewHolder.taxTxt.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getTax()));
                childViewHolder.netAmtTt.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getOrderValue()));
                childViewHolder.dicountTxt.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getSchemeAmount()));
            childViewHolder.grossAmtTxt.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getlGrossAmt()));
//            childViewHolder.otherDiscountTxt.setText(String.format(Locale.ENGLISH, "%.2f", dicountAmt));

            }

        } else if (child == 1) {
            childViewHolder.details_ll.setVisibility(View.GONE);
            childViewHolder.tracking_rl.setVisibility(View.VISIBLE);
            final List<OrderStatusModel> childtem_two = childItemsTwo.get(parentItems.get(groupPosition));
            if (childtem_two.size() == 0) {
                childViewHolder.orderStatus_ll.setVisibility(View.GONE);
                childViewHolder.orderStatusNoData.setVisibility(View.VISIBLE);
            } else {
                childViewHolder.orderStatusNoData.setVisibility(View.GONE);
                childViewHolder.orderStatus_ll.setVisibility(View.VISIBLE);

                if (childPosition == 0) {
                    childViewHolder.orderStatusDateTv.setText("Order Status");
                    childViewHolder.orderStatusDateTv.setTypeface(null, Typeface.BOLD);

                    childViewHolder.orderStatusTimeTv.setText("");
                    childViewHolder.orderStatusTv.setText("");
                    childViewHolder.dividerView.setVisibility(View.GONE);
                } else {
                    String date = DateUtil.convertTimestampToStringDate(Long.parseLong(childtem_two.get(childPosition - 1).getActionTime()));
                    String time = DateUtil.convertTimestampToStringTime(Long.parseLong(childtem_two.get(childPosition - 1).getActionTime()));

                    childViewHolder.orderStatusTimeTv.setText(time);
                    childViewHolder.orderStatusDateTv.setText(date);
                    childViewHolder.orderStatusDateTv.setTypeface(null, Typeface.NORMAL);
                    childViewHolder.dividerView.setVisibility(View.VISIBLE);
                    childViewHolder.orderStatusTv.setText(childtem_two.get(childPosition - 1).getFreeText());
                }
            }
        }else{
            childViewHolder.details_ll.setVisibility(View.GONE);
            childViewHolder.tracking_rl.setVisibility(View.GONE);
        }

        childViewHolder.trackingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child = 1;
                chileCount = childItemsTwo.get(parentItems.get(groupPosition)).size() + 1;

                childViewHolder.tracking_rl.setVisibility(View.VISIBLE);
                childViewHolder.details_ll.setVisibility(View.GONE);
                notifyDataSetChanged();
            }
        });

        childViewHolder.detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child = 0;
                chileCount = childtems.get(parentItems.get(groupPosition)).size() + 1;

                childViewHolder.tracking_rl.setVisibility(View.GONE);
                childViewHolder.details_ll.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public void applyFilterValue(String catTypeValue, String filterText, String dateValue, int i) {

        String selectedDate = dateValue;

        if (i == 0) {
            parentlist.clear();
        }
        if (!catTypeValue.isEmpty() && catTypeValue.equalsIgnoreCase("All")) {

            for (OrderBookingHeaderModel model : searchList) {
                if (isStrEmpty(selectedDate)) {
                    if (model.getOrderDt().equalsIgnoreCase(selectedDate)) {
                        parentlist.add(model);
                        setChildItems(parentlist);
                    }
                } else {
                    parentlist.add(model);
                    setChildItems(parentlist);
                }
            }

            notifyDataSetChanged();
            loadFooterAmtDetails(parentlist);
            return;
        }

        filterCatTypeAll(filterText, selectedDate);

        notifyDataSetChanged();

        loadFooterAmtDetails(parentlist);
    }

    private void filterCatTypeAll(String filterText, String selectedDate) {
        for (OrderBookingHeaderModel model : searchList) {
            if (!filterText.isEmpty() && (model.getCmpCode().equalsIgnoreCase(filterText)
                    || model.getDistrCode().equalsIgnoreCase(filterText)
                    || model.getCustomerRefNo().equalsIgnoreCase(filterText))) {
                if (isStrEmpty(selectedDate)) {
                    if (model.getOrderDt().equalsIgnoreCase(selectedDate)) {
                        parentlist.add(model);
                        setChildItems(parentlist);

                    }
                } else {
                    parentlist.add(model);
                    setChildItems(parentlist);
                }
            }
        }

    }

    private void loadFooterAmtDetails(List<OrderBookingHeaderModel> parentlist) {
        double grossAmt = 0;
        double netAmt = 0;
        double schemeAmt = 0;
        double taxAmt = 0;
        if (parentlist != null && !parentlist.isEmpty()) {
            for (OrderBookingHeaderModel salesReportModel : parentlist) {
                netAmt = netAmt + salesReportModel.getTotalOrderValue();
                schemeAmt = schemeAmt + salesReportModel.getTotalDiscount();
                taxAmt = taxAmt + salesReportModel.getTotalTax();
            }
        }
        String strPattern = "{0} {1}";
        totGross.setText(MessageFormat.format(strPattern, context.getString(R.string.grossamt), String.format(Locale.getDefault(), "%.2f", grossAmt)));
        totNet.setText(MessageFormat.format(strPattern, context.getString(R.string.actual_net_amt), String.format(Locale.getDefault(), "%.2f", netAmt)));
        totDiscount.setText(MessageFormat.format(strPattern, context.getString(R.string.discamt), String.format(Locale.getDefault(), "%.2f", schemeAmt)));
        totalTax.setText(MessageFormat.format(strPattern, context.getString(R.string.taxamt), String.format(Locale.getDefault(), "%.2f", taxAmt)));

    }

    private boolean isStrEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    private void setChildItems(List<OrderBookingHeaderModel> parentlist) {
        parentItems.clear();
        for (int i = 0; parentlist.size() > i; i++) {
            List<OrderBookingVO> templist;
            templist = childtems.get(parentlist.get(i).getOrderDt());
            childtems.put(parentlist.get(i).getCustomerRefNo(), templist);
            parentItems.add(parentlist.get(i).getCustomerRefNo());
        }
    }

    public void setOnExpandableItemClick(OnExpandableItemClickTrackOrder onExpandableItemClick) {
        this.onExpandableItemClick = onExpandableItemClick;
        this.onCollapseItemClick = onExpandableItemClick;
    }

    private final class ChildViewHolder {

        private TextView prodCode;
        private TextView prodName;
        private TextView quantity;
        private TextView sellRate;
        private TextView dicountTxt;
        private TextView taxTxt;
        private TextView netAmtTt;
        private TextView grossAmtTxt;
        private TextView otherDiscountTxt;

        private TextView orderStatusTimeTv;
        private TextView orderStatusDateTv;
        private TextView orderStatusTv;

        private RelativeLayout selection_rl;
        private LinearLayout details_ll;
        private RelativeLayout tracking_rl;
        private LinearLayout orderStatus_ll;
        private View dividerView;
        private Button trackingBtn, detailsBtn;
        private TextView orderStatusNoData;


    }

    public void setChildCount(int c) {
        chileCount = c;
    }

    public void setChild(int child) {
        this.child = child;
    }

}