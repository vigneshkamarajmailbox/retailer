package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.db.SFADatabase;
import com.botree.retailerssfa.models.OrderBookingVO;
import com.botree.retailerssfa.util.AppUtils;
import com.botree.retailerssfa.util.OrderSupportUtil;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.botree.retailerssfa.controller.constants.AppConstant.Configurations.CONFIG_IS_PTR_WITH_TAX;
import static com.botree.retailerssfa.support.Globals.NAME_INVOICE_SUMMARY;

public class OrderSummaryExpListAdapter extends CommonExpListAdapter {
    private Context context;
    private Map<String, List<OrderBookingVO>> childtems;
    private LayoutInflater inflater;
    private List<String> parentItems;
    private List<OrderBookingVO> parentlist;
    private OnExpandableItemClick onExpandableItemClick;
    private SFADatabase db;
    private boolean isPTRWithTax = false;
    private String screenName = "";
    public OrderSummaryExpListAdapter(Context context, List<String> parentdata,
                                      final Map<String, List<OrderBookingVO>> childtems,
                                      List<OrderBookingVO> parentList,String screenType) {

        this.context = context;
        this.parentItems = parentdata;
        this.childtems = childtems;
        this.parentlist = parentList;
        this.screenName = screenType;
        db = SFADatabase.getInstance(context);
        if ("Y".equalsIgnoreCase(db.getConfigDataBasedOnName(CONFIG_IS_PTR_WITH_TAX))) {
            isPTRWithTax = true;
        }

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
            convertView = inflater.inflate(R.layout.order_summary_header, parent, false);
        }
        TextView dicountLabel = convertView.findViewById(R.id.discount_amt_label);
        TextView grossLabel = convertView.findViewById(R.id.gross_amt_label);
        TextView date = convertView.findViewById(R.id.header_date_txt);
        TextView itemsLableTxt = convertView.findViewById(R.id.order_item_label_txt);
        TextView orderNo = convertView.findViewById(R.id.order_no_txt);
        TextView items = convertView.findViewById(R.id.order_items_txt);
        TextView discountTxt = convertView.findViewById(R.id.ord_sum_header_discount_txt);
        TextView taxAmtTxt = convertView.findViewById(R.id.ord_sum_header_tax_txt);
        TextView netAmtTxt = convertView.findViewById(R.id.order_net_amt_txt);
        TextView grossAmtTxt = convertView.findViewById(R.id.day_sum_gross_amt_txt);
        ImageView indicatorIcon = convertView.findViewById(R.id.ordersum_indicatorimage);
        ImageView shareIcon = convertView.findViewById(R.id.share_data_img);
        CardView headerCardview = convertView.findViewById(R.id.order_summary_header_card);
        date.setText(parentlist.get(groupPosition).getRetailerName());

        if (!parentlist.get(groupPosition).getGrnStatus().isEmpty() &&
                parentlist.get(groupPosition).getGrnStatus().equalsIgnoreCase("Y")) {
            headerCardview.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color1));
        } else {
            headerCardview.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

//        orderNo.setText(MessageFormat.format("Channel-{0}", parentlist.get(groupPosition).getChannelName()));
        orderNo.setText(parentlist.get(groupPosition).getReadableInvNo());
        items.setText(String.valueOf(parentlist.get(groupPosition).getNoOfItems()));

        double orderValue = parentlist.get(groupPosition).getOrderValue().doubleValue();
        double schemeAmount = (orderValue - parentlist.get(groupPosition).getPrimDiscOrderValue().doubleValue())
                + parentlist.get(groupPosition).getSchemeAmount().doubleValue();
        if(schemeAmount<0){
            schemeAmount=0;
        }

        String currency = "₹ {0}";
        netAmtTxt.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", parentlist.get(groupPosition).getTotalAmount())));
        taxAmtTxt.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", parentlist.get(groupPosition).getTax())));


        if(isPTRWithTax && !NAME_INVOICE_SUMMARY.equalsIgnoreCase(screenName)){

            dicountLabel.setText(context.getResources().getString(R.string.taxable_amt));
            grossLabel.setText(context.getResources().getString(R.string.dicount_amt));
            grossAmtTxt.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", schemeAmount)));
            discountTxt.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", parentlist.get(groupPosition).getlGrossAmt().doubleValue())));

        }else {
            dicountLabel.setText(context.getResources().getString(R.string.dicount_amt));
            grossLabel.setText(context.getResources().getString(R.string.gross_amt));
            discountTxt.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", schemeAmount)));
            grossAmtTxt.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", orderValue)));
        }

        indicatorIcon.setImageResource(isExpanded ? R.drawable.bottom : R.drawable.right);

        if (parentlist.get(groupPosition).getNoOfItems() == 1) {
            itemsLableTxt.setText(context.getResources().getText(R.string.item));
        } else {
            itemsLableTxt.setText(context.getResources().getText(R.string.items));
        }

        shareIcon.setOnClickListener(view -> onExpandableItemClick.setOnExpandableListClick(groupPosition));

        return convertView;
    }

    @NonNull
    private View getChildView(int groupPosition, int childPosition, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.order_summary_child_items, parent, false);

            childViewHolder = new ChildViewHolder();

            childViewHolder.prodName = convertView.findViewById(R.id.ordsum_pro_name);
            childViewHolder.quantity = convertView.findViewById(R.id.ordsum_qty_txt);
            childViewHolder.netAmtTt = convertView.findViewById(R.id.ordsum_net_amt_txt);
            childViewHolder.taxTxt = convertView.findViewById(R.id.ordsum_tax_txt);
            childViewHolder.dicountTxt = convertView.findViewById(R.id.ordsum_discount_txt);
            childViewHolder.grossAmtTxt = convertView.findViewById(R.id.ordsum_gross_amt_txt);

            convertView.setTag(R.layout.order_summary_child_items, childViewHolder);

        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag(R.layout.order_summary_child_items);
        }

        if (childPosition == 0) {
            childViewHolder.prodName.setText(context.getResources().getText(R.string.product_name));
            childViewHolder.quantity.setText(context.getResources().getText(R.string.qty));
            childViewHolder.netAmtTt.setText(context.getResources().getText(R.string.net_amt));
            childViewHolder.taxTxt.setText(context.getResources().getText(R.string.tax));

//            if(isPTRWithTax && !NAME_INVOICE_SUMMARY.equalsIgnoreCase(screenName)){
//                childViewHolder.dicountTxt.setText(context.getResources().getText(R.string.taxable_amt));
//                childViewHolder.grossAmtTxt.setText(context.getResources().getText(R.string.discount));
//            }else {
                childViewHolder.dicountTxt.setText(context.getResources().getText(R.string.discount));
                childViewHolder.grossAmtTxt.setText(context.getResources().getText(R.string.gross_amt));
//            }

            childViewHolder.prodName.setTypeface(null, Typeface.BOLD);
            childViewHolder.quantity.setTypeface(null, Typeface.BOLD);
            childViewHolder.netAmtTt.setTypeface(null, Typeface.BOLD);
            childViewHolder.dicountTxt.setTypeface(null, Typeface.BOLD);
            childViewHolder.taxTxt.setTypeface(null, Typeface.BOLD);
            childViewHolder.grossAmtTxt.setTypeface(null, Typeface.BOLD);

        } else {

            childViewHolder.prodName.setTypeface(null, Typeface.NORMAL);
            childViewHolder.quantity.setTypeface(null, Typeface.NORMAL);
            childViewHolder.netAmtTt.setTypeface(null, Typeface.NORMAL);
            childViewHolder.dicountTxt.setTypeface(null, Typeface.NORMAL);
            childViewHolder.taxTxt.setTypeface(null, Typeface.NORMAL);
            childViewHolder.grossAmtTxt.setTypeface(null, Typeface.NORMAL);

            final List<OrderBookingVO> childtem = childtems.get(parentItems.get(groupPosition));

            double orderValue = childtem.get(childPosition - 1).getOrderValue().doubleValue();
            double taxableGross = childtem.get(childPosition - 1).getlGrossAmt().doubleValue();

            childViewHolder.prodName.setText(childtem.get(childPosition - 1).getProdName());
            childViewHolder.quantity.setText(String.valueOf(OrderSupportUtil.getInstance().getStringQty(childtem.get(childPosition - 1).getQuantity())
                    + " " + childtem.get(childPosition - 1).getUomId()));
            Double pri = childtem.get(childPosition - 1).getTotalAmount().doubleValue();
            AppUtils.getOurInstance().setInrSymToValue(childViewHolder.netAmtTt, pri);

            childViewHolder.taxTxt.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getTax()));

            if(isPTRWithTax && !NAME_INVOICE_SUMMARY.equalsIgnoreCase(screenName)){
                childViewHolder.grossAmtTxt.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getSchemeAmount().doubleValue()));
                childViewHolder.dicountTxt.setText(String.format(Locale.ENGLISH, "%.2f", taxableGross));

            }else {
                childViewHolder.dicountTxt.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getSchemeAmount().doubleValue()));
                childViewHolder.grossAmtTxt.setText(String.format(Locale.ENGLISH, "%.2f", orderValue));
            }
        }
        return convertView;
    }

    public void setOnExpandableItemClick(OnExpandableItemClick onExpandableItemClick) {
        this.onExpandableItemClick = onExpandableItemClick;
    }

    private final class ChildViewHolder {

        private TextView quantity;
        private TextView prodName;
        private TextView netAmtTt;
        private TextView dicountTxt;
        private TextView taxTxt;
        private TextView grossAmtTxt;
    }
}