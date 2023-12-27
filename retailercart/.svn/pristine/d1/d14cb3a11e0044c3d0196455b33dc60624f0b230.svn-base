package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderBookingDetailsModel implements Parcelable {
    private String cmpCode;
    private String distrCode;
    private String orderNo;
    private String prodCode;
    private String prodBatchCode;
    private int orderQty;
    private int servicedQty;
    private String uomCode;
    private double sellRate;
    private double orderValue;
    private String uploadFlag;
    private String modDt;

    public OrderBookingDetailsModel() {
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
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

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdBatchCode() {
        return prodBatchCode;
    }

    public void setProdBatchCode(String prodBatchCode) {
        this.prodBatchCode = prodBatchCode;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public int getServicedQty() {
        return servicedQty;
    }

    public void setServicedQty(int servicedQty) {
        this.servicedQty = servicedQty;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(double orderValue) {
        this.orderValue = orderValue;
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

    protected OrderBookingDetailsModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        orderNo = in.readString();
        prodCode = in.readString();
        prodBatchCode = in.readString();
        orderQty = in.readInt();
        servicedQty = in.readInt();
        uomCode = in.readString();
        sellRate = in.readDouble();
        orderValue = in.readDouble();
        uploadFlag = in.readString();
        modDt = in.readString();
    }

    public static final Creator<OrderBookingDetailsModel> CREATOR = new Creator<OrderBookingDetailsModel>() {
        @Override
        public OrderBookingDetailsModel createFromParcel(Parcel in) {
            return new OrderBookingDetailsModel(in);
        }

        @Override
        public OrderBookingDetailsModel[] newArray(int size) {
            return new OrderBookingDetailsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cmpCode);
        parcel.writeString(distrCode);
        parcel.writeString(orderNo);
        parcel.writeString(prodCode);
        parcel.writeString(prodBatchCode);
        parcel.writeInt(orderQty);
        parcel.writeInt(servicedQty);
        parcel.writeString(uomCode);
        parcel.writeDouble(sellRate);
        parcel.writeDouble(orderValue);
        parcel.writeString(uploadFlag);
        parcel.writeString(modDt);
    }
}
