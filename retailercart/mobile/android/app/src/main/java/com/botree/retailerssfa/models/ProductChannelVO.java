package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vinothbaskaran on 5/10/17.
 */

public class ProductChannelVO implements Parcelable {

    public static final Creator<ProductChannelVO> CREATOR = new Creator<ProductChannelVO>() {
        @Override
        public ProductChannelVO createFromParcel(Parcel in) {
            return new ProductChannelVO(in);
        }

        @Override
        public ProductChannelVO[] newArray(int size) {
            return new ProductChannelVO[size];
        }
    };

    private String productCode;
    private String productName;
    private String productDesc;
    private String productValue;

    public ProductChannelVO(String productCode, String productName, int i) {
        this.productCode = productCode;
        this.productName = productName;
        this.productValue = String.valueOf(i);
    }

    protected ProductChannelVO(Parcel in) {
        productCode = in.readString();
        productName = in.readString();
        productDesc = in.readString();
        productValue = in.readString();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductValue() {
        return productValue;
    }

    public void setProductValue(String productValue) {
        this.productValue = productValue;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productCode);
        dest.writeString(productName);
        dest.writeString(productDesc);
        dest.writeString(productValue);
    }
}
