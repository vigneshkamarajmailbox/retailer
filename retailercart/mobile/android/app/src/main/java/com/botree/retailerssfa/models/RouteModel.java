/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_NAME;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_ROUTE_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_COVERAGE_SEQ;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MOD_DT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UPLOAD_FLAG;

public class RouteModel implements Parcelable {
    @SerializedName(COLUMN_CMP_CODE)
    private String companyCode;
    @SerializedName(COLUMN_DISTR_CODE)
    private String distributorCode;
    @SerializedName(COLUMN_ROUTE_CODE)
    private String routeCode = "";
    private String tempRouteCode = "";
    @SerializedName(COLUMN_ROUTE_NAME)
    private String routeName;
    @SerializedName(COLUMN_ROUTE_TYPE)
    private String routeType;
    @SerializedName(TAG_UPLOAD_FLAG)
    private String uploadFlag;
    private int color;

    private boolean isRouteChecked = false;
    private String retCount = "0";
    @SerializedName(TAG_COVERAGE_SEQ)
    private String coverageSeq = "1";
    @SerializedName(TAG_CUSTOMER_CODE)
    private String customerCode="";
    private String tempCustomerCode="";
    @SerializedName(TAG_MOD_DT)
    private String modDt = "";


    public RouteModel(String companyCode, String distributorCode, String routeCode, String routeName,
                      String routeType, String uploadFlag) {
        this.companyCode = companyCode;
        this.distributorCode = distributorCode;
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.routeType = routeType;
        this.uploadFlag = uploadFlag;
    }

    protected RouteModel(Parcel in) {
        companyCode = in.readString();
        distributorCode = in.readString();
        routeCode = in.readString();
        tempRouteCode = in.readString();
        routeName = in.readString();
        routeType = in.readString();
        uploadFlag = in.readString();
        color = in.readInt();
        isRouteChecked = in.readByte() != 0;
        retCount = in.readString();
        coverageSeq = in.readString();
        customerCode = in.readString();
        tempCustomerCode = in.readString();
        modDt = in.readString();
    }

    @Override
    public String toString() {
        return "RouteModel{" +
                "companyCode='" + companyCode + '\'' +
                ", distributorCode='" + distributorCode + '\'' +
                ", routeCode='" + routeCode + '\'' +
                ", tempRouteCode='" + tempRouteCode + '\'' +
                ", routeName='" + routeName + '\'' +
                ", routeType='" + routeType + '\'' +
                ", uploadFlag='" + uploadFlag + '\'' +
                ", color=" + color +
                ", isRouteChecked=" + isRouteChecked +
                ", retCount='" + retCount + '\'' +
                ", coverageSeq='" + coverageSeq + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", tempCustomerCode='" + tempCustomerCode + '\'' +
                ", modDt='" + modDt + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(companyCode);
        dest.writeString(distributorCode);
        dest.writeString(routeCode);
        dest.writeString(tempRouteCode);
        dest.writeString(routeName);
        dest.writeString(routeType);
        dest.writeString(uploadFlag);
        dest.writeInt(color);
        dest.writeByte((byte) (isRouteChecked ? 1 : 0));
        dest.writeString(retCount);
        dest.writeString(coverageSeq);
        dest.writeString(customerCode);
        dest.writeString(tempCustomerCode);
        dest.writeString(modDt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RouteModel> CREATOR = new Creator<RouteModel>() {
        @Override
        public RouteModel createFromParcel(Parcel in) {
            return new RouteModel(in);
        }

        @Override
        public RouteModel[] newArray(int size) {
            return new RouteModel[size];
        }
    };

    public String getTempRouteCode() {
        return tempRouteCode;
    }

    public void setTempRouteCode(String tempRouteCode) {
        this.tempRouteCode = tempRouteCode;
    }

    public String getTempCustomerCode() {
        return tempCustomerCode;
    }

    public void setTempCustomerCode(String tempCustomerCode) {
        this.tempCustomerCode = tempCustomerCode;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public void setRetCount(String retCount) {
        this.retCount = retCount;
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

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isRouteChecked() {
        return isRouteChecked;
    }

    public void setRouteChecked(boolean routeChecked) {
        isRouteChecked = routeChecked;
    }

    public String getRetCount() {
        return retCount;
    }

    public String getCoverageSeq() {
        return coverageSeq;
    }

    public void setCoverageSeq(String coverageSeq) {
        this.coverageSeq = coverageSeq;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    public RouteModel() {

    }
}
