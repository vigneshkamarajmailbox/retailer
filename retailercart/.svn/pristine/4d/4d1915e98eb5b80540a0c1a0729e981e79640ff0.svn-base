package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_MONTH_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_MONTH_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_YEAR_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_YEAR_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_MONTH_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_MONTH_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_YEAR_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_YEAR_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_REPORT_DAY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_REPORT_MONTH;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TARGET_SALES;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TARGET_VAL;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TARGET_VOL;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TRENDS_MONTH;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TRENDS_YEAR;


public class MTDModel extends SalesmanModel implements Parcelable {


    public static final Parcelable.Creator<MTDModel> CREATOR = new Creator<MTDModel>() {
        public MTDModel createFromParcel(Parcel source) {
            MTDModel mtdModel = new MTDModel();
            mtdModel.channelCode = source.readString();
            mtdModel.channelCode = source.readString();
            mtdModel.channelName = source.readString();
            mtdModel.routeCode = source.readString();
            mtdModel.routeName = source.readString();
            mtdModel.customerCode = source.readString();
            mtdModel.customerName = source.readString();
            mtdModel.salesValues = source.readDouble();
            mtdModel.lastbillDate = source.readString();
            mtdModel.weeks = source.readString();
            mtdModel.barColor = source.readInt();
            mtdModel.months = source.readInt();
            mtdModel.reportDay = source.readInt();
            mtdModel.year = source.readInt();
            return mtdModel;
        }

        public MTDModel[] newArray(int size) {
            return new MTDModel[size];
        }
    };

    private String channelCode = "";

    private String channelName = "";

    private String routeCode = "";

    private String routeName = "";

    @SerializedName(TAG_CUSTOMER_CODE)
    private String customerCode = "";

    @SerializedName(TAG_CUSTOMER_NAME)
    private String customerName = "";

    @SerializedName(TAG_SALES_VALUE)
    private Double salesValues = 0.00;

    @SerializedName(value = TAG_TARGET_SALES, alternate = TAG_TARGET_VAL)
    private Double targetSales = 0.00;

    @SerializedName(TAG_TARGET_VOL)
    private int targetVol = 0;

    @SerializedName(value = TAG_CURRENT_MONTH_SALES_VALUE, alternate = TAG_CURRENT_YEAR_SALES_VALUE)
    private Double currentSalesValue = 0.00;

    @SerializedName(value = TAG_PREV_MONTH_SALES_VALUE, alternate = TAG_PREV_YEAR_SALES_VALUE)
    private Double previousSalesValue = 0.00;

    @SerializedName(value = TAG_CURRENT_MONTH_SALES_QTY, alternate = TAG_CURRENT_YEAR_SALES_QTY)
    private Integer currentSalesQty = 0;

    @SerializedName(value = TAG_PREV_MONTH_SALES_QTY, alternate = TAG_PREV_YEAR_SALES_QTY)
    private Integer previousSalesQty = 0;

    private String lastbillDate = "";

    private String weeks = "";

    private int barColor = 0;

    @SerializedName(TAG_REPORT_DAY)
    private Integer reportDay = 0;

    @SerializedName(value = TAG_REPORT_MONTH, alternate = TAG_TRENDS_MONTH)
    private Integer months = 0;

    @SerializedName(TAG_TRENDS_YEAR)
    private int year = 0;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLastbillDate() {
        return lastbillDate;
    }

    public void setLastbillDate(String lastbillDate) {
        this.lastbillDate = lastbillDate;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public int getBarColor() {
        return barColor;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(channelCode);
        parcel.writeString(channelName);
        parcel.writeString(routeCode);
        parcel.writeString(routeName);
        parcel.writeString(customerCode);
        parcel.writeString(customerName);
        parcel.writeString(lastbillDate);
        parcel.writeString(weeks);
        parcel.writeInt(barColor);
        parcel.writeInt(reportDay);
        parcel.writeInt(months);
        parcel.writeInt(year);
    }


    public Double getSalesValues() {
        return salesValues;
    }

    public void setSalesValues(Double salesValues) {
        this.salesValues = salesValues;
    }

    public Double getCurrentSalesValue() {
        return currentSalesValue;
    }

    public void setCurrentSalesValue(Double currentSalesValue) {
        this.currentSalesValue = currentSalesValue;
    }

    public Double getPreviousSalesValue() {
        return previousSalesValue;
    }

    public void setPreviousSalesValue(Double previousSalesValue) {
        this.previousSalesValue = previousSalesValue;
    }

    public Integer getReportDay() {
        return reportDay;
    }

    public void setReportDay(Integer reportDay) {
        this.reportDay = reportDay;
    }


    @Override
    public String toString() {
        return "MTDModel{" +
                ", channelCode='" + channelCode + '\'' +
                ", channelName='" + channelName + '\'' +
                ", routeCode='" + routeCode + '\'' +
                ", routeName='" + routeName + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", salesValues=" + salesValues +
                ", currentSalesValue=" + currentSalesValue +
                ", previousSalesValue=" + previousSalesValue +
                ", lastbillDate='" + lastbillDate + '\'' +
                ", weeks='" + weeks + '\'' +
                ", barColor=" + barColor +
                ", reportDay=" + reportDay +
                ", months=" + months +
                ", year='" + year + '\'' +
                '}';
    }

    public Integer getCurrentSalesQty() {
        return currentSalesQty;
    }

    public void setCurrentSalesQty(Integer currentSalesQty) {
        this.currentSalesQty = currentSalesQty;
    }

    public Integer getPreviousSalesQty() {
        return previousSalesQty;
    }

    public void setPreviousSalesQty(Integer previousSalesQty) {
        this.previousSalesQty = previousSalesQty;
    }

    public Double getTargetSales() {
        return targetSales;
    }

    public void setTargetSales(Double targetSales) {
        this.targetSales = targetSales;
    }

    public int getTargetVol() {
        return targetVol;
    }

    public void setTargetVol(int targetVol) {
        this.targetVol = targetVol;
    }

}
