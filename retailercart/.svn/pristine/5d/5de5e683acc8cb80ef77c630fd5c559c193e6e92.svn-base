package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ContractPricingModel implements Parcelable {
    private String cmpCode;
    private String distrCode;
    private String cpRefCode;
    private String cpRefName;
    private String fromDate;
    private String toDate;
    private String discType;
    private String customerCode;
    private String prodCode;
    private String applyOn;
    private String discPer;
    private String flatAmt;
    private String splPrice;
    private String splPriceWithTax;
    private String netRate;
    private String uploadFlag = "";
    private String modDt = "";

    protected ContractPricingModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        cpRefCode = in.readString();
        cpRefName = in.readString();
        fromDate = in.readString();
        toDate = in.readString();
        discType = in.readString();
        customerCode = in.readString();
        prodCode = in.readString();
        applyOn = in.readString();
        discPer = in.readString();
        flatAmt = in.readString();
        splPrice = in.readString();
        splPriceWithTax = in.readString();
        netRate = in.readString();
        uploadFlag = in.readString();
        modDt = in.readString();
    }

    public static final Creator<ContractPricingModel> CREATOR = new Creator<ContractPricingModel>() {
        @Override
        public ContractPricingModel createFromParcel(Parcel in) {
            return new ContractPricingModel(in);
        }

        @Override
        public ContractPricingModel[] newArray(int size) {
            return new ContractPricingModel[size];
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

    public String getCpRefCode() {
        return cpRefCode;
    }

    public void setCpRefCode(String cpRefCode) {
        this.cpRefCode = cpRefCode;
    }

    public String getCpRefName() {
        return cpRefName;
    }

    public void setCpRefName(String cpRefName) {
        this.cpRefName = cpRefName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDiscType() {
        return discType;
    }

    public void setDiscType(String discType) {
        this.discType = discType;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getApplyOn() {
        return applyOn;
    }

    public void setApplyOn(String applyOn) {
        this.applyOn = applyOn;
    }

    public String getDiscPer() {
        return discPer;
    }

    public void setDiscPer(String discPer) {
        this.discPer = discPer;
    }

    public String getFlatAmt() {
        return flatAmt;
    }

    public void setFlatAmt(String flatAmt) {
        this.flatAmt = flatAmt;
    }

    public String getSplPrice() {
        return splPrice;
    }

    public void setSplPrice(String splPrice) {
        this.splPrice = splPrice;
    }

    public String getSplPriceWithTax() {
        return splPriceWithTax;
    }

    public void setSplPriceWithTax(String splPriceWithTax) {
        this.splPriceWithTax = splPriceWithTax;
    }

    public String getNetRate() {
        return netRate;
    }

    public void setNetRate(String netRate) {
        this.netRate = netRate;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cmpCode);
        parcel.writeString(distrCode);
        parcel.writeString(cpRefCode);
        parcel.writeString(cpRefName);
        parcel.writeString(fromDate);
        parcel.writeString(toDate);
        parcel.writeString(discType);
        parcel.writeString(customerCode);
        parcel.writeString(prodCode);
        parcel.writeString(applyOn);
        parcel.writeString(discPer);
        parcel.writeString(flatAmt);
        parcel.writeString(splPrice);
        parcel.writeString(splPriceWithTax);
        parcel.writeString(netRate);
        parcel.writeString(uploadFlag);
        parcel.writeString(modDt);
    }
}