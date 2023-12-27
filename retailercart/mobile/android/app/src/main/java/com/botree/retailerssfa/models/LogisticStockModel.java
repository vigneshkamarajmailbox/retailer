package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class LogisticStockModel implements Parcelable {
    private String cmpCode;
    private String consoleFlag;
    private String uploadFlag;
    private String modDt;
    private String distrCode;
    private String invoiceNo;
    private String materialCode;
    private String materialName;
    private String receivedQty;
    private String returnedQty;

    public LogisticStockModel() {
    }

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

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(String receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getReturnedQty() {
        return returnedQty;
    }

    public void setReturnedQty(String returnedQty) {
        this.returnedQty = returnedQty;
    }

    private LogisticStockModel(Parcel in) {
        cmpCode = in.readString();
        consoleFlag = in.readString();
        uploadFlag = in.readString();
        modDt = in.readString();
        distrCode = in.readString();
        invoiceNo = in.readString();
        materialCode = in.readString();
        materialName = in.readString();
        receivedQty = in.readString();
        returnedQty = in.readString();
    }

    public static final Creator<LogisticStockModel> CREATOR = new Creator<LogisticStockModel>() {
        @Override
        public LogisticStockModel createFromParcel(Parcel in) {
            return new LogisticStockModel(in);
        }

        @Override
        public LogisticStockModel[] newArray(int size) {
            return new LogisticStockModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cmpCode);
        parcel.writeString(consoleFlag);
        parcel.writeString(uploadFlag);
        parcel.writeString(modDt);
        parcel.writeString(distrCode);
        parcel.writeString(invoiceNo);
        parcel.writeString(materialCode);
        parcel.writeString(materialName);
        parcel.writeString(receivedQty);
        parcel.writeString(returnedQty);
    }
}
