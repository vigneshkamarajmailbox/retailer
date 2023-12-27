package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProdBatchModel implements Parcelable {

    private String cmpCode="";
    private String consoleFlag = null;
    private String uploadFlag="";
    private long modDt;
    @SerializedName("batchLevel")
    private String distrCode="";
    private String prodCode="";
    private String prodBatchCode="";
    private String fromLevel="";
    private String toLevel="";
    private String latestBatch="";
    private double purchaseRate;
    private double mrp;
    private double sellRate;
    private long manfDate;
    private long expiryDate;
    private String availQty;
    private double sellRateWithTax;

    public String getAvailQty() {
        return availQty;
    }

    public void setAvailQty(String availQty) {
        this.availQty = availQty;
    }

    public String getLatestBatch() {
        return latestBatch;
    }

    public void setLatestBatch(String latestBatch) {
        this.latestBatch = latestBatch;
    }

    protected ProdBatchModel(Parcel in) {
        cmpCode = in.readString();
        consoleFlag = in.readString();
        uploadFlag = in.readString();
        modDt = in.readLong();
        distrCode = in.readString();
        prodCode = in.readString();
        prodBatchCode = in.readString();
        fromLevel = in.readString();
        toLevel = in.readString();
        latestBatch = in.readString();
        purchaseRate = in.readDouble();
        mrp = in.readDouble();
        sellRate = in.readDouble();
        manfDate = in.readLong();
        expiryDate = in.readLong();
    }

    public ProdBatchModel() {
    }

    public static final Creator<ProdBatchModel> CREATOR = new Creator<ProdBatchModel>() {
        @Override
        public ProdBatchModel createFromParcel(Parcel in) {
            return new ProdBatchModel(in);
        }

        @Override
        public ProdBatchModel[] newArray(int size) {
            return new ProdBatchModel[size];
        }
    };

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getConsoleFlag() {
        return consoleFlag;
    }

    public void setConsoleFlag(String consoleFlag) {
        this.consoleFlag = consoleFlag;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public long getModDt() {
        return modDt;
    }

    public void setModDt(long modDt) {
        this.modDt = modDt;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
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

    public String getFromLevel() {
        return fromLevel;
    }

    public void setFromLevel(String fromLevel) {
        this.fromLevel = fromLevel;
    }

    public String getToLevel() {
        return toLevel;
    }

    public void setToLevel(String toLevel) {
        this.toLevel = toLevel;
    }

    public double getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    public long getManfDate() {
        return manfDate;
    }

    public void setManfDate(long manfDate) {
        this.manfDate = manfDate;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(consoleFlag);
        dest.writeString(uploadFlag);
        dest.writeLong(modDt);
        dest.writeString(distrCode);
        dest.writeString(prodCode);
        dest.writeString(prodBatchCode);
        dest.writeString(fromLevel);
        dest.writeString(toLevel);
        dest.writeString(latestBatch);
        dest.writeDouble(purchaseRate);
        dest.writeDouble(mrp);
        dest.writeDouble(sellRate);
        dest.writeLong(manfDate);
        dest.writeLong(expiryDate);
    }

    public double getSellRateWithTax() {
        return sellRateWithTax;
    }

    public void setSellRateWithTax(double sellRateWithTax) {
        this.sellRateWithTax = sellRateWithTax;
    }
}
