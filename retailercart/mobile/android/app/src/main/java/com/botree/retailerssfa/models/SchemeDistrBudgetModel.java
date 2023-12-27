package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SchemeDistrBudgetModel implements Parcelable {
    private String cmpCode;
    private String distrCode;
    private String schemeCode;
    private String isUnlimited;
    private double budget;
    private String isActive;
    private double utilizedAmount;
    private double budgetOs;
    private String uploadFlag;

    public SchemeDistrBudgetModel() {
    }

    protected SchemeDistrBudgetModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        schemeCode = in.readString();
        isUnlimited = in.readString();
        budget = in.readDouble();
        isActive = in.readString();
        utilizedAmount = in.readDouble();
        budgetOs = in.readDouble();
        uploadFlag = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(schemeCode);
        dest.writeString(isUnlimited);
        dest.writeDouble(budget);
        dest.writeString(isActive);
        dest.writeDouble(utilizedAmount);
        dest.writeDouble(budgetOs);
        dest.writeString(uploadFlag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SchemeDistrBudgetModel> CREATOR = new Creator<SchemeDistrBudgetModel>() {
        @Override
        public SchemeDistrBudgetModel createFromParcel(Parcel in) {
            return new SchemeDistrBudgetModel(in);
        }

        @Override
        public SchemeDistrBudgetModel[] newArray(int size) {
            return new SchemeDistrBudgetModel[size];
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

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getIsUnlimited() {
        return isUnlimited;
    }

    public void setIsUnlimited(String isUnlimited) {
        this.isUnlimited = isUnlimited;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public double getUtilizedAmount() {
        return utilizedAmount;
    }

    public void setUtilizedAmount(double utilizedAmount) {
        this.utilizedAmount = utilizedAmount;
    }

    public double getBudgetOs() {
        return budgetOs;
    }

    public void setBudgetOs(double budgetOs) {
        this.budgetOs = budgetOs;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }
}
