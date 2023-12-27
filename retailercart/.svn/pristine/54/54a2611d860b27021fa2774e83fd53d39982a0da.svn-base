package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class OrderBookingHeaderModel implements Parcelable {
    private String cmpCode;
    private String cmpName;
    private String distrCode;
    private String distrName;
    private String orderNo;
    private String customerRefNo;
    private String customerShipCode;
    private String customerCode;
    private String orderDt;
    private double totalDiscount;
    private double totalTax;
    private double totalOrderValue;
    private double totalGrossValue;
    private String remarks;
    private String latitude;
    private String longitude;
    private String startTime;
    private String endTime;
    private String orderStatus;
    private String orderType;
    private String newCustomerFlag;
    private String uploadFlag;
    private String modDt;
    private List<OrderBookingVO> orderBookingDetailsList = new ArrayList<>();
    private List<SchemeModel> orderBookingSchemeProductRuleList = new ArrayList<>();
    private List<OrderStatusModel> actionOrderStatusList = new ArrayList<>();
    private String customerName;
    private int lineCount;

    public OrderBookingHeaderModel() {
    }

    protected OrderBookingHeaderModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        orderNo = in.readString();
        customerRefNo = in.readString();
        customerShipCode = in.readString();
        customerCode = in.readString();
        orderDt = in.readString();
        totalDiscount = in.readDouble();
        totalTax = in.readDouble();
        totalOrderValue = in.readDouble();
        remarks = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        orderStatus = in.readString();
        orderType = in.readString();
        newCustomerFlag = in.readString();
        uploadFlag = in.readString();
        modDt = in.readString();
        orderBookingDetailsList = in.createTypedArrayList(OrderBookingVO.CREATOR);
        orderBookingSchemeProductRuleList = in.createTypedArrayList(SchemeModel.CREATOR);
        customerName = in.readString();
        lineCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(orderNo);
        dest.writeString(customerRefNo);
        dest.writeString(customerShipCode);
        dest.writeString(customerCode);
        dest.writeString(orderDt);
        dest.writeDouble(totalDiscount);
        dest.writeDouble(totalTax);
        dest.writeDouble(totalOrderValue);
        dest.writeString(remarks);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(orderStatus);
        dest.writeString(orderType);
        dest.writeString(newCustomerFlag);
        dest.writeString(uploadFlag);
        dest.writeString(modDt);
        dest.writeTypedList(orderBookingDetailsList);
        dest.writeTypedList(orderBookingSchemeProductRuleList);
        dest.writeString(customerName);
        dest.writeInt(lineCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderBookingHeaderModel> CREATOR = new Creator<OrderBookingHeaderModel>() {
        @Override
        public OrderBookingHeaderModel createFromParcel(Parcel in) {
            return new OrderBookingHeaderModel(in);
        }

        @Override
        public OrderBookingHeaderModel[] newArray(int size) {
            return new OrderBookingHeaderModel[size];
        }
    };

    public List<OrderStatusModel> getActionOrderStatusList() {
        return actionOrderStatusList;
    }

    public void setActionOrderStatusList(List<OrderStatusModel> actionOrderStatusList) {
        this.actionOrderStatusList = actionOrderStatusList;
    }

    public double getTotalGrossValue() {
        return totalGrossValue;
    }

    public void setTotalGrossValue(double totalGrossValue) {
        this.totalGrossValue = totalGrossValue;
    }

    public String getCmpName() {
        return cmpName;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = cmpName;
    }

    public String getDistrName() {
        return distrName;
    }

    public void setDistrName(String distrName) {
        this.distrName = distrName;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerRefNo() {
        return customerRefNo;
    }

    public void setCustomerRefNo(String customerRefNo) {
        this.customerRefNo = customerRefNo;
    }

    public String getCustomerShipCode() {
        return customerShipCode;
    }

    public void setCustomerShipCode(String customerShipCode) {
        this.customerShipCode = customerShipCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getOrderDt() {
        return orderDt;
    }

    public void setOrderDt(String orderDt) {
        this.orderDt = orderDt;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public double getTotalOrderValue() {
        return totalOrderValue;
    }

    public void setTotalOrderValue(double totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getNewCustomerFlag() {
        return newCustomerFlag;
    }

    public void setNewCustomerFlag(String newCustomerFlag) {
        this.newCustomerFlag = newCustomerFlag;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    public List<OrderBookingVO> getOrderBookingDetailsList() {
        return orderBookingDetailsList;
    }

    public void setOrderBookingDetailsList(List<OrderBookingVO> orderBookingDetailsList) {
        this.orderBookingDetailsList = orderBookingDetailsList;
    }

    public List<SchemeModel> getOrderBookingSchemeProductRuleList() {
        return orderBookingSchemeProductRuleList;
    }

    public void setOrderBookingSchemeProductRuleList(List<SchemeModel> orderBookingSchemeProductRuleList) {
        this.orderBookingSchemeProductRuleList = orderBookingSchemeProductRuleList;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }
}
