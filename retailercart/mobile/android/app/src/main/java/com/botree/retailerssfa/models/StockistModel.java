package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class StockistModel implements Parcelable {
    private String cmpCode="";
    private String isrCode="";
    private String isrName="";
    private String superStockistCode="";
    private String superStockistName="";
    private String stockistCode="";
    private String stockistName="";
    private String distrSalesmanCode="";
    private String distrSalesmanName="";
    private String shLastLevelCode="";
    private String shLastLevelName="";
    private String lobCode="";
    private String gstDistrStateCode="";
    private String dmsEnable="";
    private boolean alreadySelectedStockist = false;

    protected StockistModel(Parcel in) {
        cmpCode = in.readString();
        superStockistCode = in.readString();
        superStockistName = in.readString();
        stockistCode = in.readString();
        stockistName = in.readString();
        distrSalesmanCode = in.readString();
        distrSalesmanName = in.readString();
        shLastLevelCode = in.readString();
        shLastLevelName = in.readString();
        lobCode = in.readString();
        gstDistrStateCode = in.readString();
        dmsEnable = in.readString();
    }

    public static final Creator<StockistModel> CREATOR = new Creator<StockistModel>() {
        @Override
        public StockistModel createFromParcel(Parcel in) {
            return new StockistModel(in);
        }

        @Override
        public StockistModel[] newArray(int size) {
            return new StockistModel[size];
        }
    };

    public StockistModel() {

    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getSuperStockistCode() {
        return superStockistCode;
    }

    public void setSuperStockistCode(String superStockistCode) {
        this.superStockistCode = superStockistCode;
    }

    public String getSuperStockistName() {
        return superStockistName;
    }

    public void setSuperStockistName(String superStockistName) {
        this.superStockistName = superStockistName;
    }

    public String getStockistCode() {
        return stockistCode;
    }

    public void setStockistCode(String stockistCode) {
        this.stockistCode = stockistCode;
    }

    public String getStockistName() {
        return stockistName;
    }

    public void setStockistName(String stockistName) {
        this.stockistName = stockistName;
    }

    public String getDistrSalesmanCode() {
        return distrSalesmanCode;
    }

    public void setDistrSalesmanCode(String distrSalesmanCode) {
        this.distrSalesmanCode = distrSalesmanCode;
    }

    public String getDistrSalesmanName() {
        return distrSalesmanName;
    }

    public void setDistrSalesmanName(String distrSalesmanName) {
        this.distrSalesmanName = distrSalesmanName;
    }

    public String getShLastLevelCode() {
        return shLastLevelCode;
    }

    public void setShLastLevelCode(String shLastLevelCode) {
        this.shLastLevelCode = shLastLevelCode;
    }

    public String getShLastLevelName() {
        return shLastLevelName;
    }

    public void setShLastLevelName(String shLastLevelName) {
        this.shLastLevelName = shLastLevelName;
    }

    public String getLobCode() {
        return lobCode;
    }

    public void setLobCode(String lobCode) {
        this.lobCode = lobCode;
    }

    public String getGstDistrStateCode() {
        return gstDistrStateCode;
    }

    public void setGstDistrStateCode(String gstDistrStateCode) {
        this.gstDistrStateCode = gstDistrStateCode;
    }


    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(superStockistCode);
        dest.writeString(superStockistName);
        dest.writeString(stockistCode);
        dest.writeString(stockistName);
        dest.writeString(distrSalesmanCode);
        dest.writeString(distrSalesmanName);
        dest.writeString(shLastLevelCode);
        dest.writeString(shLastLevelName);
        dest.writeString(lobCode);
        dest.writeString(gstDistrStateCode);
        dest.writeString(dmsEnable);
    }

    @Override
    public String toString() {
        return "StockistModel{" +
                "cmpCode='" + cmpCode + '\'' +
                ", superStockistCode='" + superStockistCode + '\'' +
                ", superStockistName='" + superStockistName + '\'' +
                ", stockistCode='" + stockistCode + '\'' +
                ", stockistName='" + stockistName + '\'' +
                ", distrSalesmanCode='" + distrSalesmanCode + '\'' +
                ", distrSalesmanName='" + distrSalesmanName + '\'' +
                ", shLastLevelCode='" + shLastLevelCode + '\'' +
                ", shLastLevelName='" + shLastLevelName + '\'' +
                ", lobCode='" + lobCode + '\'' +
                ", gstDistrStateCode='" + gstDistrStateCode + '\'' +
                '}';
    }

    public String getIsrCode() {
        return isrCode;
    }

    public void setIsrCode(String isrCode) {
        this.isrCode = isrCode;
    }

    public String getIsrName() {
        return isrName;
    }

    public void setIsrName(String isrName) {
        this.isrName = isrName;
    }

    public boolean isAlreadySelectedStockist() {
        return alreadySelectedStockist;
    }

    public void setAlreadySelectedStockist(boolean alreadySelectedStockist) {
        this.alreadySelectedStockist = alreadySelectedStockist;
    }

    public String getDmsEnable() {
        return dmsEnable;
    }

    public void setDmsEnable(String dmsEnable) {
        this.dmsEnable = dmsEnable;
    }

}
