package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class EditRateModel implements Parcelable {

    private String cmpCode = "";
    private String consoleFlag = "";
    private String uploadFlag = "";
    private String modDt = "";
    private String distrCode = "";
    private String customerLevel = "";
    private String channelCode = "";
    private String subChannelCode = "";
    private String groupCode = "";
    private String classCode = "";
    private String customerCode;
    private String productLevel = "";
    private String prodCode = "";
    private String allowEdit = "";


    // Getter Methods

    protected EditRateModel(Parcel in) {
        cmpCode = in.readString();
        consoleFlag = in.readString();
        uploadFlag = in.readString();
        modDt = in.readString();
        distrCode = in.readString();
        customerLevel = in.readString();
        channelCode = in.readString();
        subChannelCode = in.readString();
        groupCode = in.readString();
        classCode = in.readString();
        customerCode = in.readString();
        productLevel = in.readString();
        prodCode = in.readString();
        allowEdit = in.readString();
    }

    public static final Creator<EditRateModel> CREATOR = new Creator<EditRateModel>() {
        @Override
        public EditRateModel createFromParcel(Parcel in) {
            return new EditRateModel(in);
        }

        @Override
        public EditRateModel[] newArray(int size) {
            return new EditRateModel[size];
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

    public String getModDt() {
        return modDt;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public String getCustomerLevel() {
        return customerLevel;
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

    public String getCustomerCode() {
        return customerCode;
    }

    public String getProductLevel() {
        return productLevel;
    }

    public String getProdCode() {
        return prodCode;
    }

    public String getAllowEdit() {
        return allowEdit;
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

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
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

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public void setProductLevel(String productLevel) {
        this.productLevel = productLevel;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public void setAllowEdit(String allowEdit) {
        this.allowEdit = allowEdit;
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
        dest.writeString(modDt);
        dest.writeString(distrCode);
        dest.writeString(customerLevel);
        dest.writeString(channelCode);
        dest.writeString(subChannelCode);
        dest.writeString(groupCode);
        dest.writeString(classCode);
        dest.writeString(customerCode);
        dest.writeString(productLevel);
        dest.writeString(prodCode);
        dest.writeString(allowEdit);
    }
}
