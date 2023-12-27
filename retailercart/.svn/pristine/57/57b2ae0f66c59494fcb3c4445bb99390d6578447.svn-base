package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerStockModel implements Parcelable {
    private String cmpCode;
    private String distrCode;
    private String customerCode;
    private String stock;
    private String prodCode;
    private int soq;
    private String ppq= "";

    protected CustomerStockModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        customerCode = in.readString();
        stock = in.readString();
        prodCode = in.readString();
        soq = in.readInt();
        ppq = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(customerCode);
        dest.writeString(stock);
        dest.writeString(prodCode);
        dest.writeInt(soq);
        dest.writeString(ppq);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomerStockModel> CREATOR = new Creator<CustomerStockModel>() {
        @Override
        public CustomerStockModel createFromParcel(Parcel in) {
            return new CustomerStockModel(in);
        }

        @Override
        public CustomerStockModel[] newArray(int size) {
            return new CustomerStockModel[size];
        }
    };

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

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public int getSoq() {
        return soq;
    }

    public void setSoq(int soq) {
        this.soq = soq;
    }

    public String getPpq() {
        return ppq;
    }

    public void setPpq(String ppq) {
        this.ppq = ppq;
    }
}
