package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_ACTUAL_ROUTE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ACTUAL_ROUTE_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_SALESMAN_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_SALESMAN_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_FLAG;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LATITUDE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LONGITUDE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_ACTUAL_OUTLETS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_OF_INVOICE_TAKEN;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_OF_ORDERS_TAKEN;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_PLANNED_OUTLETS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ORDER_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PHONE_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PLANNED_ROUTE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PLANNED_ROUTE_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SYNC_DATE;

/**
 * Created by vinothbaskaran on 19/1/18.
 */

public class SalesmanTrackerModel implements Parcelable {
    public static final Creator<SalesmanTrackerModel> CREATOR = new Creator<SalesmanTrackerModel>() {
        @Override
        public SalesmanTrackerModel createFromParcel(Parcel in) {
            return new SalesmanTrackerModel(in);
        }

        @Override
        public SalesmanTrackerModel[] newArray(int size) {
            return new SalesmanTrackerModel[size];
        }
    };
    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";

    @SerializedName(TAG_DISTR_CODE)
    private String distrCode = "";
    @SerializedName(TAG_DISTR_NAME)
    private String distrName = "";
    @SerializedName(TAG_DISTR_SALESMAN_CODE)
    private String salesmanCode = "";
    @SerializedName(TAG_DISTR_SALESMAN_NAME)
    private String salesmanName = "";
    private String salesmanStatus = "";
    @SerializedName(TAG_LATITUDE)
    private String latitude = "";
    @SerializedName(TAG_LONGITUDE)
    private String longitude = "";
    @SerializedName(TAG_PLANNED_ROUTE_CODE)
    private String plannedRouteCode = "-";
    @SerializedName(TAG_ACTUAL_ROUTE_CODE)
    private String actualRouteCode = "-";
    @SerializedName(TAG_PLANNED_ROUTE_NAME)
    private String plannedRouteName = "-";
    @SerializedName(TAG_ACTUAL_ROUTE_NAME)
    private String actualRouteName = "-";
    @SerializedName(TAG_NO_PLANNED_OUTLETS)
    private int outletToBeCovered = 0;
    @SerializedName(TAG_NO_ACTUAL_OUTLETS)
    private int coveredOutlets = 0;
    @SerializedName(TAG_NO_OF_ORDERS_TAKEN)
    private int noOfOrderTaken = 0;
    @SerializedName(TAG_ORDER_VALUE)
    private Double orderValue = 0d;

    @SerializedName(TAG_NO_OF_INVOICE_TAKEN)
    private int noOfInvoiceTaken = 0;
    @SerializedName(TAG_INVOICE_VALUE)
    private Double invoiceValue = 0d;
    @SerializedName(TAG_FLAG)
    private int flag = 1;

    @SerializedName(TAG_SYNC_DATE)
    private long syncDt;
    @SerializedName(TAG_PHONE_NO)
    private long phoneNumber = 0;

    public SalesmanTrackerModel() {

    }

    protected SalesmanTrackerModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        salesmanCode = in.readString();
        salesmanName = in.readString();
        salesmanStatus = in.readString();
        if (in.readByte() == 0) {
            orderValue = null;
        } else {
            orderValue = in.readDouble();
        }
        latitude = in.readString();
        longitude = in.readString();
        plannedRouteCode = in.readString();
        actualRouteCode = in.readString();
        outletToBeCovered = in.readInt();
        coveredOutlets = in.readInt();
        noOfOrderTaken = in.readInt();
    }

    public SalesmanTrackerModel(String distrCode, String all) {
        this.distrCode = distrCode;
        this.distrName = all;
    }

    public String getDistrName() {
        return distrName;
    }

    public void setDistrName(String distrName) {
        this.distrName = distrName;
    }

    public String getPlannedRouteName() {
        return plannedRouteName;
    }

    public void setPlannedRouteName(String plannedRouteName) {
        this.plannedRouteName = plannedRouteName;
    }

    public String getActualRouteName() {
        return actualRouteName;
    }

    public void setActualRouteName(String actualRouteName) {
        this.actualRouteName = actualRouteName;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSalesmanStatus() {
        return salesmanStatus;
    }

    public void setSalesmanStatus(String salesmanStatus) {
        this.salesmanStatus = salesmanStatus;
    }

    public Double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Double orderValue) {
        this.orderValue = orderValue;
    }

    public String getPlannedRouteCode() {
        return plannedRouteCode;
    }

    public void setPlannedRouteCode(String plannedRouteCode) {
        this.plannedRouteCode = plannedRouteCode;
    }

    public String getActualRouteCode() {
        return actualRouteCode;
    }

    public void setActualRouteCode(String actualRouteCode) {
        this.actualRouteCode = actualRouteCode;
    }

    public int getOutletToBeCovered() {
        return outletToBeCovered;
    }

    public void setOutletToBeCovered(int outletToBeCovered) {
        this.outletToBeCovered = outletToBeCovered;
    }

    public int getCoveredOutlets() {
        return coveredOutlets;
    }

    public void setCoveredOutlets(int coveredOutlets) {
        this.coveredOutlets = coveredOutlets;
    }

    public int getNoOfOrderTaken() {
        return noOfOrderTaken;
    }

    public void setNoOfOrderTaken(int noOfOrderTaken) {
        this.noOfOrderTaken = noOfOrderTaken;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(salesmanCode);
        dest.writeString(salesmanName);
        dest.writeString(salesmanStatus);
        if (orderValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(orderValue);
        }
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(plannedRouteCode);
        dest.writeString(actualRouteCode);
        dest.writeInt(outletToBeCovered);
        dest.writeInt(coveredOutlets);
        dest.writeInt(noOfOrderTaken);
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getSyncDt() {
        return syncDt;
    }

    public void setSyncDt(long syncDt) {
        this.syncDt = syncDt;
    }

    public int getNoOfInvoiceTaken() {
        return noOfInvoiceTaken;
    }

    public void setNoOfInvoiceTaken(int noOfInvoiceTaken) {
        this.noOfInvoiceTaken = noOfInvoiceTaken;
    }

    public Double getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(Double invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
