package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomerModel implements Parcelable {

    private String cmpCode;
    private String consoleFlag = null;
    private String uploadFlag;
    private long modDt;
    private String distrCode;
    private String customerCode;
    private String tempCustomerCode;
    private String companyCustomerCode;
    private String customerName;
    private String pinCode;
    private String phoneNo;
    private String mobileNo;
    private String contactPerson;
    private String emailID;
    private int dayOff;
    private String retailerStatus;
    private String fssaiNo;
    private String drugLicNo;
    private String drugLicExpiryDate = null;
    private int creditBills;
    private int creditDays;
    private double creditLimit;
    private double cashDiscPerc;
    private String channelCode;
    private String subChannelCode;
    private String groupCode;
    private String classCode;
    private String storeType;
    private String parentCustomerCode;
    private String latitude;
    private String longitude;
    private String customerType;
    private String gstTinNo;
    private String panNo;
    private String approvalStatus = null;

    public String getDistrName() {
        return distrName;
    }

    public void setDistrName(String distrName) {
        this.distrName = distrName;
    }

    public String getCmpName() {
        return cmpName;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = cmpName;
    }

    private String distrName;
    private String cmpName;

    public CustomerModel() {
    }

    protected CustomerModel(Parcel in) {
        cmpCode = in.readString();
        consoleFlag = in.readString();
        uploadFlag = in.readString();
        modDt = in.readLong();
        distrCode = in.readString();
        customerCode = in.readString();
        tempCustomerCode = in.readString();
        companyCustomerCode = in.readString();
        customerName = in.readString();
        pinCode = in.readString();
        phoneNo = in.readString();
        mobileNo = in.readString();
        contactPerson = in.readString();
        emailID = in.readString();
        dayOff = in.readInt();
        retailerStatus = in.readString();
        fssaiNo = in.readString();
        drugLicNo = in.readString();
        drugLicExpiryDate = in.readString();
        creditBills = in.readInt();
        creditDays = in.readInt();
        creditLimit = in.readDouble();
        cashDiscPerc = in.readDouble();
        channelCode = in.readString();
        subChannelCode = in.readString();
        groupCode = in.readString();
        classCode = in.readString();
        storeType = in.readString();
        parentCustomerCode = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        customerType = in.readString();
        gstTinNo = in.readString();
        panNo = in.readString();
        approvalStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(consoleFlag);
        dest.writeString(uploadFlag);
        dest.writeLong(modDt);
        dest.writeString(distrCode);
        dest.writeString(customerCode);
        dest.writeString(tempCustomerCode);
        dest.writeString(companyCustomerCode);
        dest.writeString(customerName);
        dest.writeString(pinCode);
        dest.writeString(phoneNo);
        dest.writeString(mobileNo);
        dest.writeString(contactPerson);
        dest.writeString(emailID);
        dest.writeInt(dayOff);
        dest.writeString(retailerStatus);
        dest.writeString(fssaiNo);
        dest.writeString(drugLicNo);
        dest.writeString(drugLicExpiryDate);
        dest.writeInt(creditBills);
        dest.writeInt(creditDays);
        dest.writeDouble(creditLimit);
        dest.writeDouble(cashDiscPerc);
        dest.writeString(channelCode);
        dest.writeString(subChannelCode);
        dest.writeString(groupCode);
        dest.writeString(classCode);
        dest.writeString(storeType);
        dest.writeString(parentCustomerCode);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(customerType);
        dest.writeString(gstTinNo);
        dest.writeString(panNo);
        dest.writeString(approvalStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomerModel> CREATOR = new Creator<CustomerModel>() {
        @Override
        public CustomerModel createFromParcel(Parcel in) {
            return new CustomerModel(in);
        }

        @Override
        public CustomerModel[] newArray(int size) {
            return new CustomerModel[size];
        }
    };

    public String getTempCustomerCode() {
        return tempCustomerCode;
    }

    public void setTempCustomerCode(String tempCustomerCode) {
        this.tempCustomerCode = tempCustomerCode;
    }

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

    public String getDistrCode() {
        return distrCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getCompanyCustomerCode() {
        return companyCustomerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getEmailID() {
        return emailID;
    }

    public float getDayOff() {
        return dayOff;
    }

    public String getRetailerStatus() {
        return retailerStatus;
    }

    public String getFssaiNo() {
        return fssaiNo;
    }

    public String getDrugLicNo() {
        return drugLicNo;
    }

    public String getDrugLicExpiryDate() {
        return drugLicExpiryDate;
    }

    public int getCreditBills() {
        return creditBills;
    }

    public int getCreditDays() {
        return creditDays;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getCashDiscPerc() {
        return cashDiscPerc;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public String getSubChannelCode() {
        return subChannelCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getStoreType() {
        return storeType;
    }

    public String getParentCustomerCode() {
        return parentCustomerCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getGstTinNo() {
        return gstTinNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public String getApprovalStatus() {
        return approvalStatus;
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

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public void setCompanyCustomerCode(String companyCustomerCode) {
        this.companyCustomerCode = companyCustomerCode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public void setDayOff(int dayOff) {
        this.dayOff = dayOff;
    }

    public void setRetailerStatus(String retailerStatus) {
        this.retailerStatus = retailerStatus;
    }

    public void setFssaiNo(String fssaiNo) {
        this.fssaiNo = fssaiNo;
    }

    public void setDrugLicNo(String drugLicNo) {
        this.drugLicNo = drugLicNo;
    }

    public void setDrugLicExpiryDate(String drugLicExpiryDate) {
        this.drugLicExpiryDate = drugLicExpiryDate;
    }

    public void setCreditBills(int creditBills) {
        this.creditBills = creditBills;
    }

    public void setCreditDays(int creditDays) {
        this.creditDays = creditDays;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setCashDiscPerc(double cashDiscPerc) {
        this.cashDiscPerc = cashDiscPerc;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public void setSubChannelCode(String subChannelCode) {
        this.subChannelCode = subChannelCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public void setParentCustomerCode(String parentCustomerCode) {
        this.parentCustomerCode = parentCustomerCode;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public void setGstTinNo(String gstTinNo) {
        this.gstTinNo = gstTinNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
