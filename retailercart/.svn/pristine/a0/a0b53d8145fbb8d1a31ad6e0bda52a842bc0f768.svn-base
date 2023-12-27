package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_OTP;

public class OTPModel implements Parcelable {

    public static final Creator<OTPModel> CREATOR = new Creator<OTPModel>() {
        @Override
        public OTPModel createFromParcel(Parcel in) {
            return new OTPModel(in);
        }

        @Override
        public OTPModel[] newArray(int size) {
            return new OTPModel[size];
        }
    };
    private String cmpCode;
    private String distrCode;
    private String distrSalesmanCode;
    private String customerName;
    private String mobileNo;
    private String refDate;

    private String refCode;

    @SerializedName(TAG_OTP)
    private String otp = "";

    @SerializedName(TAG_CUSTOMER_LIST)
    private List<RetailerVO> retailerVOList = new ArrayList<>();

    // Getter Methods

    public OTPModel() {
    }

    public OTPModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        distrSalesmanCode = in.readString();
        customerName = in.readString();
        mobileNo = in.readString();
        refDate = in.readString();
        refCode = in.readString();
        otp = in.readString();
    }

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

    public String getDistrSalesmanCode() {
        return distrSalesmanCode;
    }

    public void setDistrSalesmanCode(String distrSalesmanCode) {
        this.distrSalesmanCode = distrSalesmanCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Setter Methods

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(distrSalesmanCode);
        dest.writeString(customerName);
        dest.writeString(mobileNo);
        dest.writeString(refDate);
        dest.writeString(refCode);
        dest.writeString(otp);
    }

    public List<RetailerVO> getRetailerVOList() {
        return retailerVOList;
    }

    public void setRetailerVOList(List<RetailerVO> retailerVOList) {
        this.retailerVOList = retailerVOList;
    }
}