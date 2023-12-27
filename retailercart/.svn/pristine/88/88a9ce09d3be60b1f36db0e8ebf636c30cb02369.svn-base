package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class EditQtyModel implements Parcelable {

    private String prodCode;
    private String batchCode;
    private int previousQty=0;
    private int orderQty=0;
    private int minusStockQty=0;
    private int plusStockQty=0;
    private int availableQty=0;
    private int currStockQty=0;

    public int getCurrStockQty() {
        return currStockQty;
    }

    public void setCurrStockQty(int currStockQty) {
        this.currStockQty = currStockQty;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public int getMinusStockQty() {
        return minusStockQty;
    }

    public void setMinusStockQty(int minusStockQty) {
        this.minusStockQty = minusStockQty;
    }

    public int getPlusStockQty() {
        return plusStockQty;
    }

    public void setPlusStockQty(int plusStockQty) {
        this.plusStockQty = plusStockQty;
    }

    public EditQtyModel(){}

    protected EditQtyModel(Parcel in) {
        prodCode = in.readString();
        batchCode = in.readString();
        previousQty = in.readInt();
        minusStockQty = in.readInt();
        plusStockQty = in.readInt();
        orderQty = in.readInt();
        availableQty = in.readInt();
        currStockQty = in.readInt();
    }

    public static final Creator<EditQtyModel> CREATOR = new Creator<EditQtyModel>() {
        @Override
        public EditQtyModel createFromParcel(Parcel in) {
            return new EditQtyModel(in);
        }

        @Override
        public EditQtyModel[] newArray(int size) {
            return new EditQtyModel[size];
        }
    };

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public int getPreviousQty() {
        return previousQty;
    }

    public void setPreviousQty(int previousQty) {
        this.previousQty = previousQty;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prodCode);
        dest.writeString(batchCode);
        dest.writeInt(previousQty);
        dest.writeInt(plusStockQty);
        dest.writeInt(minusStockQty);
        dest.writeInt(orderQty);
        dest.writeInt(availableQty);
        dest.writeInt(currStockQty);
    }
}
