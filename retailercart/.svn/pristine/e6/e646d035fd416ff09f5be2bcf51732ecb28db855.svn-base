package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SalesmanRouteCoveragePlanModel implements Parcelable {
    String cmpCode;
    String distrCode;
    String salesmanCode;
    String routeCode;
    String routeName;
    String planType = "Weekly";
    String startDate;
    String endDate;
    String uploadFlag;
    String modDt;
    int dayName;//1 for Sunday to 7 for Saturday

    public SalesmanRouteCoveragePlanModel() {
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public int getDayName() {
        return dayName;
    }

    public void setDayName(int dayName) {
        this.dayName = dayName;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUpload() {
        return uploadFlag;
    }

    public void setUpload(String upload) {
        this.uploadFlag = upload;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    protected SalesmanRouteCoveragePlanModel(Parcel in) {
        salesmanCode = in.readString();
        routeCode = in.readString();
        routeName = in.readString();
        planType = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        uploadFlag = in.readString();
        modDt = in.readString();
    }

    public static final Creator<SalesmanRouteCoveragePlanModel> CREATOR = new Creator<SalesmanRouteCoveragePlanModel>() {
        @Override
        public SalesmanRouteCoveragePlanModel createFromParcel(Parcel in) {
            return new SalesmanRouteCoveragePlanModel(in);
        }

        @Override
        public SalesmanRouteCoveragePlanModel[] newArray(int size) {
            return new SalesmanRouteCoveragePlanModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(salesmanCode);
        parcel.writeString(routeCode);
        parcel.writeString(routeName);
        parcel.writeString(planType);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeString(uploadFlag);
        parcel.writeString(modDt);
    }
}
