package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TaxMasterModel implements Parcelable {

    private String cmpCode = "";
    private String taxStateCode = "";
    private String prodCode = "";
    private float cgst;
    private float sgst;
    private float igst;
    private float cess;
    private float additionalTax;
    private String uploadFlag = "";


    // Getter Methods

    public TaxMasterModel(Parcel in) {
        cmpCode = in.readString();
        taxStateCode = in.readString();
        prodCode = in.readString();
        cgst = in.readFloat();
        sgst = in.readFloat();
        igst = in.readFloat();
        cess = in.readFloat();
        additionalTax = in.readFloat();
        uploadFlag = in.readString();
    }

    public static final Creator<TaxMasterModel> CREATOR = new Creator<TaxMasterModel>() {
        @Override
        public TaxMasterModel createFromParcel(Parcel in) {
            return new TaxMasterModel(in);
        }

        @Override
        public TaxMasterModel[] newArray(int size) {
            return new TaxMasterModel[size];
        }
    };

    public TaxMasterModel() {

    }

    public String getCmpCode() {
        return cmpCode;
    }

    public String getTaxStateCode() {
        return taxStateCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public float getCgst() {
        return cgst;
    }

    public float getSgst() {
        return sgst;
    }

    public float getIgst() {
        return igst;
    }

    public float getCess() {
        return cess;
    }

    public float getAdditionalTax() {
        return additionalTax;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    // Setter Methods

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public void setTaxStateCode(String taxStateCode) {
        this.taxStateCode = taxStateCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public void setCgst(float cgst) {
        this.cgst = cgst;
    }

    public void setSgst(float sgst) {
        this.sgst = sgst;
    }

    public void setIgst(float igst) {
        this.igst = igst;
    }

    public void setCess(float cess) {
        this.cess = cess;
    }

    public void setAdditionalTax(float additionalTax) {
        this.additionalTax = additionalTax;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(taxStateCode);
        dest.writeString(prodCode);
        dest.writeFloat(cgst);
        dest.writeFloat(sgst);
        dest.writeFloat(igst);
        dest.writeFloat(cess);
        dest.writeFloat(additionalTax);
        dest.writeString(uploadFlag);
    }
}
