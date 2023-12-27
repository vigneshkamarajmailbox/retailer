package com.botree.retailerssfa.adapters;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.SalesReportModel;
import com.botree.retailerssfa.util.DateUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class SalesReportExpListAdapter extends CommonExpListAdapter {
    private Context context;
    private Map<String, List<SalesReportModel>> childtems;
    private LayoutInflater inflater;
    private List<String> parentItems;
    private List<SalesReportModel> parentlist;
    private List<SalesReportModel> searchList = new ArrayList<>();
    TextView totGross;
    TextView totNet;
    TextView totDiscount;
    TextView totalTax;

    public SalesReportExpListAdapter(Context context, List<String> parentdata,
                                     final Map<String, List<SalesReportModel>> childtems,
                                     final List<SalesReportModel> parentList, TextView totGrossAmtTxt, TextView totalNetAmtTxt, TextView totalDiscountAmtTxt, TextView totalTaxAmtTxt) {

        this.context = context;
        this.parentItems = parentdata;
        this.childtems = childtems;
        this.parentlist = parentList;
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
            convertView = inflater.inflate(R.layout.sales_report_header, parent, false);
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
        if (!parentlist.isEmpty()) {
            salesman.setText(parentlist.get(groupPosition).getSalesmanName());
            route.setText(String.valueOf(parentlist.get(groupPosition).getRouteName()));
            customer.setText(String.valueOf(parentlist.get(groupPosition).getCustomerName()));
            invoiceNo.setText(String.valueOf(parentlist.get(groupPosition).getInvoiceNo()));
            invoiceDt.setText(DateUtil.convertTimestampToStringDate(Long.valueOf(parentlist.get(groupPosition).getInvoiceDt())));

            String currency = " {0}";
            discount.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", parentlist.get(groupPosition).getSchDiscAmt())));
            tax.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", parentlist.get(groupPosition).getTaxAmt())));
            netAmt.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", parentlist.get(groupPosition).getNetAmt())));
            grossAmtTxt.setText(MessageFormat.format(currency, String.format(Locale.ENGLISH, "%.2f", parentlist.get(groupPosition).getGrossAmt())));
        }
        return convertView;
    }

    @NonNull
    private View getChildView(int groupPosition, int childPosition, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.sales_report_child_items, parent, false);

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

            convertView.setTag(R.layout.sales_report_child_items, childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag(R.layout.sales_report_child_items);
        }

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

            final List<SalesReportModel> childtem = childtems.get(parentItems.get(groupPosition));

            double dicountAmt = childtem.get(childPosition - 1).getSplDiscAmt()
                    + childtem.get(childPosition - 1).getCashDiscAmt() + childtem.get(childPosition - 1).getDbDiscAmt();

            childViewHolder.prodCode.setText(childtem.get(childPosition - 1).getProdCode());
            childViewHolder.prodName.setText(childtem.get(childPosition - 1).getProdName());
            childViewHolder.quantity.setText(childtem.get(childPosition - 1).getInputStr());
            childViewHolder.sellRate.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getSellRate()));
            childViewHolder.taxTxt.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getTaxAmt()));
            childViewHolder.netAmtTt.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getNetAmt()));
            childViewHolder.dicountTxt.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getSchDiscAmt()));
            childViewHolder.grossAmtTxt.setText(String.format(Locale.ENGLISH, "%.2f", childtem.get(childPosition - 1).getGrossAmt()));
            childViewHolder.otherDiscountTxt.setText(String.format(Locale.ENGLISH, "%.2f", dicountAmt));

        }
        return convertView;
    }

    public void applyFilterValue(String catTypeValue, String filterText, String dateValue, int i) {

        String selectedDate = dateValue;

        if (i==0) {
            parentlist.clear();
        }
        if (!catTypeValue.isEmpty() && catTypeValue.equalsIgnoreCase("All")) {

            for (SalesReportModel model : searchList) {
                if (isStrEmpty(selectedDate)) {
                    if (model.getInvoiceDt().equalsIgnoreCase(selectedDate)) {
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
        for (SalesReportModel model : searchList) {
            if (!filterText.isEmpty() && (model.getSalesmanCode().equalsIgnoreCase(filterText)
                    || model.getRouteCode().equalsIgnoreCase(filterText)
                    || model.getCustomerCode().equalsIgnoreCase(filterText))) {
                if (isStrEmpty(selectedDate)) {
                    if (model.getInvoiceDt().equalsIgnoreCase(selectedDate)) {
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

    private void loadFooterAmtDetails(List<SalesReportModel> parentlist) {
        double grossAmt = 0;
        double netAmt = 0;
        double schemeAmt = 0;
        double taxAmt = 0;
        if (parentlist != null && !parentlist.isEmpty()) {
            for (SalesReportModel salesReportModel : parentlist) {
                grossAmt = grossAmt + salesReportModel.getGrossAmt();
                netAmt = netAmt + salesReportModel.getNetAmt();
                schemeAmt = schemeAmt + salesReportModel.getSchDiscAmt()
                        + salesReportModel.getCashDiscAmt()
                        + salesReportModel.getDbDiscAmt()
                        + salesReportModel.getSplDiscAmt();
                taxAmt = taxAmt + salesReportModel.getTaxAmt();
            }
        }
        String strPattern = "{0} {1}";
        totGross.setText(MessageFormat.format(strPattern, context.getString(R.string.grossamt),  String.format(Locale.getDefault(), "%.2f", grossAmt)));
        totNet.setText(MessageFormat.format(strPattern, context.getString(R.string.actual_net_amt),  String.format(Locale.getDefault(), "%.2f", netAmt)));
        totDiscount.setText(MessageFormat.format(strPattern, context.getString(R.string.discamt),  String.format(Locale.getDefault(), "%.2f", schemeAmt)));
        totalTax.setText(MessageFormat.format(strPattern, context.getString(R.string.taxamt),  String.format(Locale.getDefault(), "%.2f", taxAmt)));

    }

    private boolean isStrEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    private void setChildItems(List<SalesReportModel> parentlist) {
        parentItems.clear();
        for (int i = 0; parentlist.size() > i; i++) {
            List<SalesReportModel> templist;
            templist = childtems.get(parentlist.get(i).getInvoiceNo());
            childtems.put(parentlist.get(i).getInvoiceNo(), templist);
            parentItems.add(parentlist.get(i).getInvoiceNo());
        }
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
    }
}