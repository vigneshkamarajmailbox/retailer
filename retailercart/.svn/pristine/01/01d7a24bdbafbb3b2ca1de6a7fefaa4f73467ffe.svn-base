package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerBankModel implements Parcelable {

    private String cmpCode;
    private String uploadFlag;
    private long modDt;
    private String distrCode;
    private String customerCode;
    private String bankCode;
    private String bankName;
    private String bankBranchName;
    private String ifscCode;
    private String accountType;
    private String accountNumber;


    // Getter Methods

    protected CustomerBankModel(Parcel in) {
        cmpCode = in.readString();
        uploadFlag = in.readString();
        modDt = in.readLong();
        distrCode = in.readString();
        customerCode = in.readString();
        bankCode = in.readString();
        bankName = in.readString();
        bankBranchName = in.readString();
        ifscCode = in.readString();
        accountType = in.readString();
        accountNumber = in.readString();
    }

    public static final Creator<CustomerBankModel> CREATOR = new Creator<CustomerBankModel>() {
        @Override
        public CustomerBankModel createFromParcel(Parcel in) {
            return new CustomerBankModel(in);
        }

        @Override
        public CustomerBankModel[] newArray(int size) {
            return new CustomerBankModel[size];
        }
    };

    public String getCmpCode() {
        return cmpCode;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public long getModDt() {
        return modDt;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // Setter Methods

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public void setModDt(long modDt) {
        this.modDt = modDt;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(uploadFlag);
        dest.writeLong(modDt);
        dest.writeString(distrCode);
        dest.writeString(customerCode);
        dest.writeString(bankCode);
        dest.writeString(bankName);
        dest.writeString(bankBranchName);
        dest.writeString(ifscCode);
        dest.writeString(accountType);
        dest.writeString(accountNumber);
    }
}
