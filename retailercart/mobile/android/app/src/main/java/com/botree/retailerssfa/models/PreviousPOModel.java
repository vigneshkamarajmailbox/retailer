package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PreviousPOModel implements Parcelable {

    private String cmpCode;
    private String distrCode;
    private String poRefNo;
    private String poDate;
    private String prodCode;
    private String prodName;
    private String prodBatchCode;
    private double purchaseRate;
    private double orderQty;
    private String uomCode;
    private double taxAmt;
    private double cgstPerc;
    private double cgstAmt;
    private double sgstPerc;
    private double sgstAmt;
    private double ugstPerc;
    private double ugstAmt;
    private double igstPerc;
    private double igstAmt;
    private double orderValue;
    private String approvalStatus;

    protected PreviousPOModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        poRefNo = in.readString();
        poDate = in.readString();
        prodCode = in.readString();
        prodName = in.readString();
        prodBatchCode = in.readString();
        purchaseRate = in.readDouble();
        orderQty = in.readDouble();
        uomCode = in.readString();
        taxAmt = in.readDouble();
        cgstPerc = in.readDouble();
        cgstAmt = in.readDouble();
        sgstPerc = in.readDouble();
        sgstAmt = in.readDouble();
        ugstPerc = in.readDouble();
        ugstAmt = in.readDouble();
        igstPerc = in.readDouble();
        igstAmt = in.readDouble();
        orderValue = in.readDouble();
        approvalStatus = in.readString();
    }

    public static final Creator<PreviousPOModel> CREATOR = new Creator<PreviousPOModel>() {
        @Override
        public PreviousPOModel createFromParcel(Parcel in) {
            return new PreviousPOModel(in);
        }

        @Override
        public PreviousPOModel[] newArray(int size) {
            return new PreviousPOModel[size];
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

    public String getPoRefNo() {
        return poRefNo;
    }

    public void setPoRefNo(String poRefNo) {
        this.poRefNo = poRefNo;
    }

    public String getPoDate() {
        return poDate;
    }

    public void setPoDate(String poDate) {
        this.poDate = poDate;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdBatchCode() {
        return prodBatchCode;
    }

    public void setProdBatchCode(String prodBatchCode) {
        this.prodBatchCode = prodBatchCode;
    }

    public double getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(double orderQty) {
        this.orderQty = orderQty;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public double getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(double taxAmt) {
        this.taxAmt = taxAmt;
    }

    public double getCgstPerc() {
        return cgstPerc;
    }

    public void setCgstPerc(double cgstPerc) {
        this.cgstPerc = cgstPerc;
    }

    public double getCgstAmt() {
        return cgstAmt;
    }

    public void setCgstAmt(double cgstAmt) {
        this.cgstAmt = cgstAmt;
    }

    public double getSgstPerc() {
        return sgstPerc;
    }

    public void setSgstPerc(double sgstPerc) {
        this.sgstPerc = sgstPerc;
    }

    public double getSgstAmt() {
        return sgstAmt;
    }

    public void setSgstAmt(double sgstAmt) {
        this.sgstAmt = sgstAmt;
    }

    public double getUgstPerc() {
        return ugstPerc;
    }

    public void setUgstPerc(double ugstPerc) {
        this.ugstPerc = ugstPerc;
    }

    public double getUgstAmt() {
        return ugstAmt;
    }

    public void setUgstAmt(double ugstAmt) {
        this.ugstAmt = ugstAmt;
    }

    public double getIgstPerc() {
        return igstPerc;
    }

    public void setIgstPerc(double igstPerc) {
        this.igstPerc = igstPerc;
    }

    public double getIgstAmt() {
        return igstAmt;
    }

    public void setIgstAmt(double igstAmt) {
        this.igstAmt = igstAmt;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(double orderValue) {
        this.orderValue = orderValue;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(poRefNo);
        dest.writeString(poDate);
        dest.writeString(prodCode);
        dest.writeString(prodName);
        dest.writeString(prodBatchCode);
        dest.writeDouble(purchaseRate);
        dest.writeDouble(orderQty);
        dest.writeString(uomCode);
        dest.writeDouble(taxAmt);
        dest.writeDouble(cgstPerc);
        dest.writeDouble(cgstAmt);
        dest.writeDouble(sgstPerc);
        dest.writeDouble(sgstAmt);
        dest.writeDouble(ugstPerc);
        dest.writeDouble(ugstAmt);
        dest.writeDouble(igstPerc);
        dest.writeDouble(igstAmt);
        dest.writeDouble(orderValue);
        dest.writeString(approvalStatus);
    }
}
