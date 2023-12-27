package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BranchStockModel implements Parcelable {

    private String cmpCode;
    private String branchCode;
    private String prodCode;
    private String stock;
    private String updatedTime;

    // Getter Methods

    protected BranchStockModel(Parcel in) {
        cmpCode = in.readString();
        branchCode = in.readString();
        prodCode = in.readString();
        stock = in.readString();
        updatedTime = in.readString();
    }

    public static final Creator<BranchStockModel> CREATOR = new Creator<BranchStockModel>() {
        @Override
        public BranchStockModel createFromParcel(Parcel in) {
            return new BranchStockModel(in);
        }

        @Override
        public BranchStockModel[] newArray(int size) {
            return new BranchStockModel[size];
        }
    };

    public String getCmpCode() {
        return cmpCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public String getStock() {
        return stock;
    }

    public String  getUpdatedTime() {
        return updatedTime;
    }

    // Setter Methods

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(branchCode);
        dest.writeString(prodCode);
        dest.writeString(stock);
        dest.writeString(updatedTime);
    }
}
