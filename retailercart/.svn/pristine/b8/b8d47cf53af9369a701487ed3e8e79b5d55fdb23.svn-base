package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_DB_CRAMT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DB_CRBAL;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DB_CRBUDGET;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DB_CRDT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DB_CRREASON;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DB_CRTAXAMT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DB_CRTYPE;

public class SalesDBCRNoteModel implements Parcelable {
    private String cmpCode;
    private String distrCode;
    private String customerCode;
    @SerializedName(TAG_DB_CRTYPE)
    private String dbCRType;
    private String salesDBCRRefNo;
    @SerializedName(TAG_DB_CRDT)
    private String dbCRDt;
    @SerializedName(TAG_DB_CRREASON)
    private String dbCRReason;
    @SerializedName(TAG_DB_CRAMT)
    private double dbCRAmt;
    @SerializedName(TAG_DB_CRBAL)
    private double dbCRBal;
    private String isSettled;
    private String remarks;
    private String isActive;
    private String isClaimable;
    private String referenceType;
    private String referenceNo;
    private String uploadFlag = "";
    private String modDt = "";
    private double adjAmt;
    private String invoiceNo;
    @SerializedName(TAG_DB_CRBUDGET)
    private double dbCRBudget;
    @SerializedName(TAG_DB_CRTAXAMT)
    private double dbCRTaxAmt;

    public double getDbCRBudget() {
        return dbCRBudget;
    }

    public void setDbCRBudget(double dbCRBudget) {
        this.dbCRBudget = dbCRBudget;
    }

    public double getDbCRTaxAmt() {
        return dbCRTaxAmt;
    }

    public void setDbCRTaxAmt(double dbCRTaxAmt) {
        this.dbCRTaxAmt = dbCRTaxAmt;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public double getAdjAmt() {
        return adjAmt;
    }

    public void setAdjAmt(double adjAmt) {
        this.adjAmt = adjAmt;
    }

    public SalesDBCRNoteModel() {
    }

    public SalesDBCRNoteModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        customerCode = in.readString();
        dbCRType = in.readString();
        salesDBCRRefNo = in.readString();
        dbCRDt = in.readString();
        dbCRReason = in.readString();
        isSettled = in.readString();
        remarks = in.readString();
        isActive = in.readString();
        isClaimable = in.readString();
        referenceType = in.readString();
        referenceNo = in.readString();
        uploadFlag = in.readString();
        modDt = in.readString();
    }

    public static final Creator<SalesDBCRNoteModel> CREATOR = new Creator<SalesDBCRNoteModel>() {
        @Override
        public SalesDBCRNoteModel createFromParcel(Parcel in) {
            return new SalesDBCRNoteModel(in);
        }

        @Override
        public SalesDBCRNoteModel[] newArray(int size) {
            return new SalesDBCRNoteModel[size];
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

    public String getDbCRType() {
        return dbCRType;
    }

    public void setDbCRType(String dbCRType) {
        this.dbCRType = dbCRType;
    }

    public String getSalesDBCRRefNo() {
        return salesDBCRRefNo;
    }

    public void setSalesDBCRRefNo(String salesDBCRRefNo) {
        this.salesDBCRRefNo = salesDBCRRefNo;
    }

    public String getDbCRDt() {
        return dbCRDt;
    }

    public void setDbCRDt(String dbCRDt) {
        this.dbCRDt = dbCRDt;
    }

    public String getDbCRReason() {
        return dbCRReason;
    }

    public void setDbCRReason(String dbCRReason) {
        this.dbCRReason = dbCRReason;
    }

    public double getDbCRAmt() {
        return dbCRAmt;
    }

    public void setDbCRAmt(double dbCRAmt) {
        this.dbCRAmt = dbCRAmt;
    }

    public double getDbCRBal() {
        return dbCRBal;
    }

    public void setDbCRBal(double dbCRBal) {
        this.dbCRBal = dbCRBal;
    }

    public String getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(String isSettled) {
        this.isSettled = isSettled;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsClaimable() {
        return isClaimable;
    }

    public void setIsClaimable(String isClaimable) {
        this.isClaimable = isClaimable;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
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
        parcel.writeString(customerCode);
        parcel.writeString(dbCRType);
        parcel.writeString(salesDBCRRefNo);
        parcel.writeString(dbCRDt);
        parcel.writeString(dbCRReason);
        parcel.writeString(isSettled);
        parcel.writeString(remarks);
        parcel.writeString(isActive);
        parcel.writeString(isClaimable);
        parcel.writeString(referenceType);
        parcel.writeString(referenceNo);
        parcel.writeString(uploadFlag);
        parcel.writeString(modDt);
    }
}
