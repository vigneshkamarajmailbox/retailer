package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DELIVERY_DT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LOB_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ORDER_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ORDER_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ORDER_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_LEVEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_VALUE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ROUTE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ROUTE_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALES_FORCE_CODE;

/**
 * Created by vinothbaskaran on 17/1/18.
 */

public class MTDPendingOrderModel extends SalesmanModel implements Parcelable {

    public static final Creator<MTDPendingOrderModel> CREATOR = new Creator<MTDPendingOrderModel>() {
        @Override
        public MTDPendingOrderModel createFromParcel(Parcel in) {
            return new MTDPendingOrderModel(in);
        }

        @Override
        public MTDPendingOrderModel[] newArray(int size) {
            return new MTDPendingOrderModel[size];
        }
    };

    @SerializedName(TAG_ROUTE_CODE)
    private String routeCode = "";

    @SerializedName(TAG_ROUTE_NAME)
    private String routeName = "";

    @SerializedName(TAG_SALES_FORCE_CODE)
    private String salesForceCode = "";

    @SerializedName(TAG_LOB_CODE)
    private String lobCode = "";

    @SerializedName(TAG_PROD_HIER_LEVEL_CODE)
    private String prodHierLvlCode = "";

    @SerializedName(TAG_PROD_HIER_VALUE_CODE)
    private String prodHierValCode = "";

    @SerializedName(TAG_CUSTOMER_CODE)
    private String customerCode = "";

    @SerializedName(TAG_CUSTOMER_NAME)
    private String customerName = "";

    private Double salesValue = 0.0d;

    @SerializedName(value = TAG_ORDER_VALUE, alternate = TAG_INVOICE_VALUE)
    private Double orderValue = 0.0d;

    @SerializedName(value = TAG_ORDER_DATE, alternate = TAG_INVOICE_DATE)
    private String orderDate = "";

    @SerializedName(value = TAG_ORDER_NO, alternate = TAG_INVOICE_NO)
    private String orderNo = "";

    @SerializedName(TAG_DELIVERY_DT)
    private String deliveryDate = "";

    private int invoiceCount = 0;

    public MTDPendingOrderModel(Parcel in) {
        customerCode = in.readString();
        customerName = in.readString();
        salesValue = in.readDouble();
        orderNo = in.readString();
    }

    public MTDPendingOrderModel() {

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

    public Double getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(Double salesValue) {
        this.salesValue = salesValue;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(customerCode);
        dest.writeString(customerName);
        dest.writeDouble(salesValue);
        dest.writeString(orderNo);
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getProdHierLvlCode() {
        return prodHierLvlCode;
    }

    public void setProdHierLvlCode(String prodHierLvlCode) {
        this.prodHierLvlCode = prodHierLvlCode;
    }

    public String getProdHierValCode() {
        return prodHierValCode;
    }

    public void setProdHierValCode(String prodHierValCode) {
        this.prodHierValCode = prodHierValCode;
    }

    public String getSalesForceCode() {
        return salesForceCode;
    }

    public void setSalesForceCode(String salesForceCode) {
        this.salesForceCode = salesForceCode;
    }

    public String getLobCode() {
        return lobCode;
    }

    public void setLobCode(String lobCode) {
        this.lobCode = lobCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Double orderValue) {
        this.orderValue = orderValue;
    }

    public int getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(int invoiceCount) {
        this.invoiceCount = invoiceCount;
    }
}
