package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UomMasterModel implements Parcelable {

    private String cmpCode = "";
    private String prodCode = "";
    private String uomCode = "";
    private String uomDescription = "";
    private String uomConvFactor = "";
    private String baseUOM = "";
    private String defaultUOM = "";

    // Getter Methods

    protected UomMasterModel(Parcel in) {
        cmpCode = in.readString();
        prodCode = in.readString();
        uomCode = in.readString();
        uomDescription = in.readString();
        uomConvFactor = in.readString();
        baseUOM = in.readString();
        defaultUOM = in.readString();
    }

    public static final Creator<UomMasterModel> CREATOR = new Creator<UomMasterModel>() {
        @Override
        public UomMasterModel createFromParcel(Parcel in) {
            return new UomMasterModel(in);
        }

        @Override
        public UomMasterModel[] newArray(int size) {
            return new UomMasterModel[size];
        }
    };

    public UomMasterModel() {
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public String getUomCode() {
        return uomCode;
    }

    public String getUomDescription() {
        return uomDescription;
    }

    public String getUomConvFactor() {
        return uomConvFactor;
    }

    public String getBaseUOM() {
        return baseUOM;
    }

    public String getDefaultUOM() {
        return defaultUOM;
    }

    // Setter Methods

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public void setUomDescription(String uomDescription) {
        this.uomDescription = uomDescription;
    }

    public void setUomConvFactor(String uomConvFactor) {
        this.uomConvFactor = uomConvFactor;
    }

    public void setBaseUOM(String baseUOM) {
        this.baseUOM = baseUOM;
    }

    public void setDefaultUOM(String defaultUOM) {
        this.defaultUOM = defaultUOM;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(prodCode);
        dest.writeString(uomCode);
        dest.writeString(uomDescription);
        dest.writeString(uomConvFactor);
        dest.writeString(baseUOM);
        dest.writeString(defaultUOM);
    }
}
