package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DistBalanceModel implements Parcelable {

    private String cmpCode;
    private String distrCode;
    private float balance;
    private float updatedTime;
    private String uploadFlag;

    // Getter Methods

    protected DistBalanceModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        balance = in.readFloat();
        updatedTime = in.readFloat();
        uploadFlag = in.readString();
    }

    public static final Creator<DistBalanceModel> CREATOR = new Creator<DistBalanceModel>() {
        @Override
        public DistBalanceModel createFromParcel(Parcel in) {
            return new DistBalanceModel(in);
        }

        @Override
        public DistBalanceModel[] newArray(int size) {
            return new DistBalanceModel[size];
        }
    };

    public DistBalanceModel() {

    }

    public String getCmpCode() {
        return cmpCode;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public float getBalance() {
        return balance;
    }

    public float getUpdatedTime() {
        return updatedTime;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    // Setter Methods

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setUpdatedTime(float updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeFloat(balance);
        dest.writeFloat(updatedTime);
        dest.writeString(uploadFlag);
    }
}
