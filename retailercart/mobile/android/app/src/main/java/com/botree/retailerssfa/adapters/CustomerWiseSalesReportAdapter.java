package com.botree.retailerssfa.adapters;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.botree.retailerssfa.R;
import com.botree.retailerssfa.models.MTDReportModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerWiseSalesReportAdapter extends RecyclerView.Adapter<CustomerWiseSalesReportAdapter.MyViewHolder> {

    private List<MTDReportModel> mtdReportModelList;
    private List<MTDReportModel> tempList = new ArrayList<>();
    private String channelCodeStr = "";
    private String customerCodeStr = "";
    private int selectedDayInt = 0;
    public CustomerWiseSalesReportAdapter(List<MTDReportModel> modelList) {
        this.mtdReportModelList = modelList;
        this.tempList.addAll(mtdReportModelList);
    }


    @NonNull
    @Override
    public CustomerWiseSalesReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_wise_sales_report_list_item, parent, false);
        return new CustomerWiseSalesReportAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerWiseSalesReportAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.tvRetailer.setText(mtdReportModelList.get(position).getCustomerName().toUpperCase());
        holder.tvRetailerCode.setText(mtdReportModelList.get(position).getCustomerCode());
        holder.tvInvoice.setText("" + mtdReportModelList.get(position).getInvoiceCount());
        holder.tvValue.setText(String.format(Locale.getDefault(), "%.2f",
                mtdReportModelList.get(position).getTotNetAmt()));
    }

    @Override
    public int getItemCount() {
        return mtdReportModelList.size();
    }

    public void applyFilterValue(String channelCode, String subChannelCode, String groupCode, String classCode, String customerCode, int selectedDay, int i) {
        if (i==0) {
            mtdReportModelList.clear();
        }
        this.channelCodeStr = channelCode;
        this.customerCodeStr = customerCode;
        this.selectedDayInt = selectedDay;
        for (MTDReportModel mtdReportModel : tempList) {
            if (validateAllFields(channelCode, subChannelCode, groupCode, classCode, customerCode) && selectedDay ==0) {
                setFilterCondition1(mtdReportModel, channelCode, subChannelCode, groupCode, classCode, customerCode);
            } else if (channelCode != null && !channelCode.isEmpty() && subChannelCode != null && !subChannelCode.isEmpty() &&
                    groupCode != null && !groupCode.isEmpty() && classCode != null && !classCode.isEmpty() && !classCode.equals("0") && selectedDay == 0) {
                setFilterCondition2(mtdReportModel, channelCode, subChannelCode, groupCode, classCode);
            } else if (channelCode != null && !channelCode.isEmpty() && subChannelCode != null && !subChannelCode.isEmpty() &&
                    groupCode != null && !groupCode.isEmpty() && selectedDay == 0) {
                setFilterCondition3(mtdReportModel, channelCode, subChannelCode, groupCode);
            } else if (channelCode != null && !channelCode.isEmpty() && subChannelCode != null && !subChannelCode.isEmpty() && selectedDay == 0) {
                setFilterCondition4(mtdReportModel, channelCode, subChannelCode);
            } else if (channelCode != null && !channelCode.isEmpty() && selectedDay == 0) {
                setFilterCondition5(mtdReportModel, channelCode);
            } else if (channelCode == null && customerCode != null && !customerCode.isEmpty() && selectedDay == 0) {
                setFilterCondition6(mtdReportModel, customerCode);
            } else if (channelCode == null && customerCode == null && selectedDay != 0) {
                setFilterCondition7(mtdReportModel, selectedDay);
            } else if (channelCode == null && customerCode != null && selectedDay != 0) {
                setFilterCondition8(mtdReportModel, customerCode, selectedDay);
            } else if (channelCode != null && customerCode != null && selectedDay != 0) {
                setFilterCondition9(mtdReportModel, channelCode, customerCode, selectedDay);
            } else if (channelCode != null && !channelCode.isEmpty() && customerCode == null && selectedDay != 0) {
                setFilterCondition10(mtdReportModel, channelCode, selectedDay);
            } else if (validateAllFields(channelCode, subChannelCode, groupCode, classCode, customerCode) && selectedDay != 0) {
                setFilterCondition11(mtdReportModel, channelCode, subChannelCode, groupCode, classCode, customerCode, selectedDay);
            }
        }
        notifyDataSetChanged();
    }

    private boolean validateAllFields(String channelCode, String subChannelCode, String groupCode, String classCode, String customerCode) {
        return channelCode != null && !channelCode.isEmpty() && subChannelCode != null && !subChannelCode.isEmpty() &&
                groupCode != null && !groupCode.isEmpty() && classCode != null && !classCode.isEmpty() && !classCode.equals("0")
                && customerCode != null && !customerCode.isEmpty();
    }

    private void setFilterCondition11(MTDReportModel mtdReportModel, String channelCode, String subChannelCode, String groupCode, String classCode, String customerCode, int selectedDay) {

        if (mtdReportModel.getChannelCode().equalsIgnoreCase(channelCode) &&
                mtdReportModel.getSubChannelCode().equalsIgnoreCase(subChannelCode) &&
                mtdReportModel.getGroupCode().equalsIgnoreCase(groupCode) &&
                mtdReportModel.getClassCode().equalsIgnoreCase(classCode) &&
                mtdReportModel.getCustomerCode().equalsIgnoreCase(customerCode) &&
                mtdReportModel.getInvoiceDay() == selectedDay) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    private void setFilterCondition10(MTDReportModel mtdReportModel, String channelCode, int selectedDay) {
        if (mtdReportModel.getChannelCode().equalsIgnoreCase(channelCode) &&
                mtdReportModel.getInvoiceDay() == selectedDay) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    private void setFilterCondition9(MTDReportModel mtdReportModel, String channelCode, String customerCode, int selectedDay) {
        if (mtdReportModel.getCustomerCode().equalsIgnoreCase(customerCode) &&
                mtdReportModel.getInvoiceDay() == selectedDay &&
                mtdReportModel.getChannelCode().equalsIgnoreCase(channelCode)) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    private void setFilterCondition8(MTDReportModel mtdReportModel, String customerCode, int selectedDay) {
        if (mtdReportModel.getCustomerCode().equalsIgnoreCase(customerCode) &&
                mtdReportModel.getInvoiceDay() == selectedDay) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    private void setFilterCondition7(MTDReportModel mtdReportModel, int selectedDay) {
        if (mtdReportModel.getInvoiceDay() == selectedDay) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    private void setFilterCondition6(MTDReportModel mtdReportModel, String customerCode) {
        if (mtdReportModel.getCustomerCode().equalsIgnoreCase(customerCode)) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    private void setFilterCondition5(MTDReportModel mtdReportModel, String channelCode) {
        if (mtdReportModel.getChannelCode().equalsIgnoreCase(channelCode)) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    private void setFilterCondition4(MTDReportModel mtdReportModel, String channelCode, String subChannelCode) {
        if (mtdReportModel.getChannelCode().equalsIgnoreCase(channelCode) &&
                mtdReportModel.getSubChannelCode().equalsIgnoreCase(subChannelCode)) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    private void setFilterCondition3(MTDReportModel mtdReportModel, String channelCode, String subChannelCode, String groupCode) {
        if (mtdReportModel.getChannelCode().equalsIgnoreCase(channelCode) &&
                mtdReportModel.getSubChannelCode().equalsIgnoreCase(subChannelCode) &&
                mtdReportModel.getGroupCode().equalsIgnoreCase(groupCode)) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    private void setFilterCondition2(MTDReportModel mtdReportModel, String channelCode, String subChannelCode, String groupCode, String classCode) {
        if (mtdReportModel.getChannelCode().equalsIgnoreCase(channelCode) &&
                mtdReportModel.getSubChannelCode().equalsIgnoreCase(subChannelCode) &&
                mtdReportModel.getGroupCode().equalsIgnoreCase(groupCode) &&
                mtdReportModel.getClassCode().equalsIgnoreCase(classCode)) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    private void setFilterCondition1(MTDReportModel mtdReportModel, String channelCode, String subChannelCode, String groupCode, String classCode, String customerCode) {
        if (mtdReportModel.getChannelCode().equalsIgnoreCase(channelCode) &&
                mtdReportModel.getSubChannelCode().equalsIgnoreCase(subChannelCode) &&
                mtdReportModel.getGroupCode().equalsIgnoreCase(groupCode) &&
                mtdReportModel.getClassCode().equalsIgnoreCase(classCode) &&
                mtdReportModel.getCustomerCode().equalsIgnoreCase(customerCode)) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    public void applySearchFilter(String charText) {
        charText = charText.toLowerCase();
        if (validateZeroTextSearch(charText)) {
            mtdReportModelList.clear();
            mtdReportModelList.addAll(tempList);
        } else {
            if ((charText.length() != 0 && (channelCodeStr == null || channelCodeStr.isEmpty()) && (customerCodeStr == null || customerCodeStr.isEmpty()) && selectedDayInt == 0)) {
                mtdReportModelList.clear();
                for (MTDReportModel mtdReportModel : tempList) {
                    addSearchModelList(mtdReportModel, charText);
                }
            } else {
                mtdReportModelList.clear();
                filteredListSearch(charText);
            }

        }
        notifyDataSetChanged();
    }

    private boolean validateZeroTextSearch(String charText) {
        return (charText.length() == 0 && (channelCodeStr == null || channelCodeStr.isEmpty()) && (customerCodeStr == null || customerCodeStr.isEmpty()) && selectedDayInt == 0) ||
                (charText.contains("all") && (channelCodeStr == null || channelCodeStr.isEmpty())) && (customerCodeStr == null || customerCodeStr.isEmpty()) && selectedDayInt == 0;
    }

    private void filteredListSearch(String charText) {
        for (MTDReportModel mtdReportModel : tempList) {

            if (channelCodeStr != null && !channelCodeStr.isEmpty() && selectedDayInt == 0) {
                setSearchChannelCode(mtdReportModel, charText);
            } else if (channelCodeStr == null && customerCodeStr != null && !customerCodeStr.isEmpty() && selectedDayInt == 0) {
                setSearchCustomerCode(mtdReportModel, charText);
            } else if (channelCodeStr == null && customerCodeStr == null && selectedDayInt != 0) {
                setSearchDayWise(mtdReportModel, charText);
            } else if (channelCodeStr == null && customerCodeStr != null && selectedDayInt != 0) {
                setSearchCustomerAndDayWise(mtdReportModel, charText);
            } else if (channelCodeStr != null && customerCodeStr != null && selectedDayInt != 0) {
                setSearchChannelCustomerAndDayWise(mtdReportModel, charText);
            } else if (channelCodeStr != null && !channelCodeStr.isEmpty() && customerCodeStr == null && selectedDayInt != 0) {
                setSearchChannelAndDayWise(mtdReportModel, charText);
            }

        }
    }

    private void setSearchChannelAndDayWise(MTDReportModel mtdReportModel, String charText) {
        if (mtdReportModel.getChannelCode().equalsIgnoreCase(channelCodeStr) &&
                mtdReportModel.getInvoiceDay() == selectedDayInt) {

            addSearchModelList(mtdReportModel, charText);
        }
    }

    private void setSearchChannelCustomerAndDayWise(MTDReportModel mtdReportModel, String charText) {
        if (mtdReportModel.getCustomerCode().equalsIgnoreCase(customerCodeStr) &&
                mtdReportModel.getInvoiceDay() == selectedDayInt &&
                mtdReportModel.getChannelCode().equalsIgnoreCase(channelCodeStr)) {
            addSearchModelList(mtdReportModel, charText);
        }
    }

    private void setSearchCustomerAndDayWise(MTDReportModel mtdReportModel, String charText) {
        if (mtdReportModel.getCustomerCode().equalsIgnoreCase(customerCodeStr) &&
                mtdReportModel.getInvoiceDay() == selectedDayInt) {
            addSearchModelList(mtdReportModel, charText);
        }
    }

    private void setSearchDayWise(MTDReportModel mtdReportModel, String charText) {
        if (mtdReportModel.getInvoiceDay() == selectedDayInt) {
            addSearchModelList(mtdReportModel, charText);
        }
    }

    private void setSearchCustomerCode(MTDReportModel mtdReportModel, String charText) {
        if (mtdReportModel.getCustomerCode().equalsIgnoreCase(customerCodeStr)) {
            addSearchModelList(mtdReportModel, charText);
        }
    }

    private void setSearchChannelCode(MTDReportModel mtdReportModel, String charText) {
        if (mtdReportModel.getChannelCode().equalsIgnoreCase(channelCodeStr)) {
            addSearchModelList(mtdReportModel, charText);
        }
    }

    private void addSearchModelList(MTDReportModel mtdReportModel, String charText) {
        if (mtdReportModel.getCustomerCode().toLowerCase().contains(charText) ||
                mtdReportModel.getCustomerName().toLowerCase().contains(charText)) {
            mtdReportModelList.add(mtdReportModel);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRetailer;
        private TextView tvRetailerCode;
        private TextView tvInvoice;
        private TextView tvValue;

        MyViewHolder(View view) {
            super(view);
            tvRetailer = view.findViewById(R.id.tvRetailer);
            tvRetailerCode = view.findViewById(R.id.tvRetailerCode);
            tvInvoice = view.findViewById(R.id.tvInvoice);
            tvValue = view.findViewById(R.id.tvValue);

        }

    }
}