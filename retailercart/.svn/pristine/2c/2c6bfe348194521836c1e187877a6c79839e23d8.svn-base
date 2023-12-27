package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SalesmanRouteCoverageModel implements Parcelable {
    String cmpCode;
    String distrCode;
    String salesmanCode;
    String routeName;
    String routeCode;
    String coverageDt;
    String uploadFlag;
    String modDt;
    String confirm;

    public SalesmanRouteCoverageModel() {
    }

    protected SalesmanRouteCoverageModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        salesmanCode = in.readString();
        routeCode = in.readString();
        coverageDt = in.readString();
        uploadFlag = in.readString();
        modDt = in.readString();
    }

    public static final Creator<SalesmanRouteCoverageModel> CREATOR = new Creator<SalesmanRouteCoverageModel>() {
        @Override
        public SalesmanRouteCoverageModel createFromParcel(Parcel in) {
            return new SalesmanRouteCoverageModel(in);
        }

        @Override
        public SalesmanRouteCoverageModel[] newArray(int size) {
            return new SalesmanRouteCoverageModel[size];
        }
    };

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
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

    public String getCoverageDt() {
        return coverageDt;
    }

    public void setCoverageDt(String coverageDt) {
        this.coverageDt = coverageDt;
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
        parcel.writeString(salesmanCode);
        parcel.writeString(routeCode);
        parcel.writeString(coverageDt);
        parcel.writeString(uploadFlag);
        parcel.writeString(modDt);
    }
}
