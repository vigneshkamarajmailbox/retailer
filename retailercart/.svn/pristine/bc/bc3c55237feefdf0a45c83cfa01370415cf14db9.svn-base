package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CHANNEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CHANNEL_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_MONTH_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_MONTH_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_YEAR_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_YEAR_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LOB_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_OF_INVOICES;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_OF_OUTLETS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_MONTH_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_MONTH_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_YEAR_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_YEAR_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ROUTE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ROUTE_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUB_CHANNEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUB_CHANNEL_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TRENDS_MONTH;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TRENDS_REPORT_WEEKS;

/**
 * Created by shantarao on 5/1/18.
 */

public class ChannelModel extends UserLevelModel implements Parcelable {

    public static final Creator<ChannelModel> CREATOR = new Creator<ChannelModel>() {
        @Override
        public ChannelModel createFromParcel(Parcel in) {
            return new ChannelModel(in);
        }

        @Override
        public ChannelModel[] newArray(int size) {
            return new ChannelModel[size];
        }
    };
    private String id = "";

    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";

    @SerializedName(TAG_DISTR_CODE)
    private String distCode = "";

    @SerializedName(TAG_LOB_CODE)
    private String lobCode = "";

    @SerializedName(TAG_CHANNEL_CODE)
    private String channelCode = "";

    @SerializedName(TAG_CHANNEL_NAME)
    private String channelName = "";

    private String salesmanCode = "";

    private String customerCode = "";

    private String customerName = "";

    private String date = "";

    @SerializedName(TAG_TRENDS_MONTH)
    private Integer month = 0;

    @SerializedName(TAG_INVOICE_VALUE)
    private Double salesValue = 0d;

    @SerializedName(value = TAG_CURRENT_MONTH_SALES_VALUE, alternate = TAG_CURRENT_YEAR_SALES_VALUE)
    private Double currSalesValue = 0d;

    @SerializedName(value = TAG_PREV_MONTH_SALES_VALUE, alternate = TAG_PREV_YEAR_SALES_VALUE)
    private Double prevSalesValue = 0d;

    @SerializedName(value = TAG_CURRENT_YEAR_SALES_QTY, alternate = TAG_CURRENT_MONTH_SALES_QTY)
    private Integer currSalesQty = 0;

    @SerializedName(value = TAG_PREV_YEAR_SALES_QTY, alternate = TAG_PREV_MONTH_SALES_QTY)
    private Integer prevSalesQty = 0;

    @SerializedName(TAG_TRENDS_REPORT_WEEKS)
    private String week = "";

    @SerializedName(TAG_SUB_CHANNEL_CODE)
    private String subChannelCode = "";

    @SerializedName(TAG_SUB_CHANNEL_NAME)
    private String subChannelName = "";

    @SerializedName(TAG_ROUTE_CODE)
    private String routeCode = "";

    @SerializedName(TAG_ROUTE_NAME)
    private String routeName = "";

    @SerializedName(TAG_NO_OF_OUTLETS)
    private String noOfOutlets = "";

    @SerializedName(TAG_NO_OF_INVOICES)
    private String noOfInvoice = "";

    private boolean isExpand;

    public ChannelModel(String channelCode, String channelName) {
        this.channelCode = channelCode;
        this.channelName = channelName;

    }

    public ChannelModel() {

    }

    protected ChannelModel(Parcel in) {
        id = in.readString();
        cmpCode = in.readString();
        distCode = in.readString();
        channelCode = in.readString();
        channelName = in.readString();
        salesmanCode = in.readString();
        customerCode = in.readString();
        customerName = in.readString();
        date = in.readString();
        month = in.readInt();
        if (in.readByte() == 0) {
            salesValue = null;
        } else {
            salesValue = in.readDouble();
        }
        if (in.readByte() == 0) {
            currSalesValue = null;
        } else {
            currSalesValue = in.readDouble();
        }
        if (in.readByte() == 0) {
            prevSalesValue = null;
        } else {
            prevSalesValue = in.readDouble();
        }
        week = in.readString();
        subChannelCode = in.readString();
        subChannelName = in.readString();
        routeCode = in.readString();
        routeName = in.readString();
        noOfOutlets = in.readString();
        noOfInvoice = in.readString();
        isExpand = in.readByte() != 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubChannelCode() {
        return subChannelCode;
    }

    public void setSubChannelCode(String subChannelCode) {
        this.subChannelCode = subChannelCode;
    }

    public String getSubChannelName() {
        return subChannelName;
    }

    public void setSubChannelName(String subChannelName) {
        this.subChannelName = subChannelName;
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

    public String getNoOfOutlets() {
        return noOfOutlets;
    }

    public void setNoOfOutlets(String noOfOutlets) {
        this.noOfOutlets = noOfOutlets;
    }

    public String getNoOfInvoice() {
        return noOfInvoice;
    }

    public void setNoOfInvoice(String noOfInvoice) {
        this.noOfInvoice = noOfInvoice;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public Double getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(Double salesValue) {
        this.salesValue = salesValue;
    }

    public Double getCurrSalesValue() {
        return currSalesValue;
    }

    public void setCurrSalesValue(Double currSalesValue) {
        this.currSalesValue = currSalesValue;
    }

    public Double getPrevSalesValue() {
        return prevSalesValue;
    }

    public void setPrevSalesValue(Double prevSalesValue) {
        this.prevSalesValue = prevSalesValue;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cmpCode);
        dest.writeString(distCode);
        dest.writeString(channelCode);
        dest.writeString(channelName);
        dest.writeString(salesmanCode);
        dest.writeString(customerCode);
        dest.writeString(customerName);
        dest.writeString(date);
        dest.writeInt(month);
        if (salesValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(salesValue);
        }
        if (currSalesValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(currSalesValue);
        }
        if (prevSalesValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(prevSalesValue);
        }
        dest.writeString(week);
        dest.writeString(subChannelCode);
        dest.writeString(subChannelName);
        dest.writeString(routeCode);
        dest.writeString(routeName);
        dest.writeString(noOfOutlets);
        dest.writeString(noOfInvoice);
        dest.writeByte((byte) (isExpand ? 1 : 0));
    }

    public String getLobCode() {
        return lobCode;
    }

    public void setLobCode(String lobCode) {
        this.lobCode = lobCode;
    }

    public Integer getCurrSalesQty() {
        return currSalesQty;
    }

    public void setCurrSalesQty(Integer currSalesQty) {
        this.currSalesQty = currSalesQty;
    }

    public Integer getPrevSalesQty() {
        return prevSalesQty;
    }

    public void setPrevSalesQty(Integer prevSalesQty) {
        this.prevSalesQty = prevSalesQty;
    }
}
