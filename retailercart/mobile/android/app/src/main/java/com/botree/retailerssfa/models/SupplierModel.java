package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SupplierModel implements Parcelable {

    private String cmpCode;
    private String consoleFlag="";
    private String uploadFlag;
    private long modDt;
    private String supplierCode;
    private String supplierName;
    private String supplierAddr;
    private String phone;
    private String emailId;
    private String contactPerson;
    private String gstStateCode;
    private String supplierGSTIN;
    private String supplierStatus;


    // Getter Methods

    protected SupplierModel(Parcel in) {
        cmpCode = in.readString();
        consoleFlag = in.readString();
        uploadFlag = in.readString();
        modDt = in.readLong();
        supplierCode = in.readString();
        supplierName = in.readString();
        supplierAddr = in.readString();
        phone = in.readString();
        emailId = in.readString();
        contactPerson = in.readString();
        gstStateCode = in.readString();
        supplierGSTIN = in.readString();
        supplierStatus = in.readString();
    }

    public static final Creator<SupplierModel> CREATOR = new Creator<SupplierModel>() {
        @Override
        public SupplierModel createFromParcel(Parcel in) {
            return new SupplierModel(in);
        }

        @Override
        public SupplierModel[] newArray(int size) {
            return new SupplierModel[size];
        }
    };

    public String getCmpCode() {
        return cmpCode;
    }

    public String getConsoleFlag() {
        return consoleFlag;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public long getModDt() {
        return modDt;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierAddr() {
        return supplierAddr;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getGstStateCode() {
        return gstStateCode;
    }

    public String getSupplierGSTIN() {
        return supplierGSTIN;
    }

    public String getSupplierStatus() {
        return supplierStatus;
    }

    // Setter Methods

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public void setConsoleFlag(String consoleFlag) {
        this.consoleFlag = consoleFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public void setModDt(long modDt) {
        this.modDt = modDt;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setSupplierAddr(String supplierAddr) {
        this.supplierAddr = supplierAddr;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setGstStateCode(String gstStateCode) {
        this.gstStateCode = gstStateCode;
    }

    public void setSupplierGSTIN(String supplierGSTIN) {
        this.supplierGSTIN = supplierGSTIN;
    }

    public void setSupplierStatus(String supplierStatus) {
        this.supplierStatus = supplierStatus;
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
        dest.writeString(supplierCode);
        dest.writeString(supplierName);
        dest.writeString(supplierAddr);
        dest.writeString(phone);
        dest.writeString(emailId);
        dest.writeString(contactPerson);
        dest.writeString(gstStateCode);
        dest.writeString(supplierGSTIN);
        dest.writeString(supplierStatus);
    }
}
