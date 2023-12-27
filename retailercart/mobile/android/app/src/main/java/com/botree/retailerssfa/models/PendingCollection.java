package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PendingCollection implements Parcelable {

    private String billNo;
    private String billDate;
    private double pendingAmount;
    private double adjustAmount;
    private double adjustBalance;
    private String adjustDt;
    private String adjustmentMode="BL";
    private String collectionMode="";
    private String collectionNo="";

    public PendingCollection() {
    }

    protected PendingCollection(Parcel in) {
        billNo = in.readString();
        billDate = in.readString();
        pendingAmount = in.readDouble();
        adjustAmount = in.readDouble();
        adjustBalance = in.readDouble();
        adjustDt = in.readString();
        collectionMode = in.readString();
    }

    public static final Creator<PendingCollection> CREATOR = new Creator<PendingCollection>() {
        @Override
        public PendingCollection createFromParcel(Parcel in) {
            return new PendingCollection(in);
        }

        @Override
        public PendingCollection[] newArray(int size) {
            return new PendingCollection[size];
        }
    };

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public double getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(double pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(billNo);
        dest.writeString(billDate);
        dest.writeDouble(pendingAmount);
        dest.writeDouble(adjustAmount);
        dest.writeDouble(adjustBalance);
        dest.writeString(adjustDt);
        dest.writeString(collectionMode);
    }

    public double getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(double adjustAmount) {
        this.adjustAmount = adjustAmount;
    }

    public double getAdjustBalance() {
        return adjustBalance;
    }

    public void setAdjustBalance(double adjustBalance) {
        this.adjustBalance = adjustBalance;
    }

    public String getAdjustDt() {
        return adjustDt;
    }

    public void setAdjustDt(String adjustDt) {
        this.adjustDt = adjustDt;
    }

    public String getAdjustmentMode() {
        return adjustmentMode;
    }

    public void setAdjustmentMode(String adjustmentMode) {
        this.adjustmentMode = adjustmentMode;
    }

    public String getCollectionMode() {
        return collectionMode;
    }

    public void setCollectionMode(String collectionMode) {
        this.collectionMode = collectionMode;
    }

    public String getCollectionNo() {
        return collectionNo;
    }

    public void setCollectionNo(String collectionNo) {
        this.collectionNo = collectionNo;
    }
}
