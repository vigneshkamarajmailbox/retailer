package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ShelfInfoModel implements Parcelable {

    public static final Creator<ShelfInfoModel> CREATOR = new Creator<ShelfInfoModel>() {
        @Override
        public ShelfInfoModel createFromParcel(Parcel in) {
            return new ShelfInfoModel(in);
        }

        @Override
        public ShelfInfoModel[] newArray(int size) {
            return new ShelfInfoModel[size];
        }
    };
    private String cmpCode = "";
    private String distrCode = "";
    private String salesmanCode = "";
    private String routeCode = "";
    private String refCode = "";
    private String date = "";
    private String prodName = "";
    private String prodCode = "";
    private String prodFilterType = "";
    private String primaryShelf = "";
    private String secondaryShelf = "";
    private String newShelfProdName = "";
    private String newShelfProdlength = "";
    private String newShelfProdDepth = "";
    private String newShelfProdCapacity = "";

    private String compPrimaryShelf = "";
    private String compSecondaryShelf = "";
    private String compShelfProdName = "";

    private String compShelfProdlength = "";
    private String compShelfProdDepth = "";
    private String compShelfProdCapacity = "";
    private List<String> compShelfTempImageList;


    public ShelfInfoModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        salesmanCode = in.readString();
        routeCode = in.readString();
        refCode = in.readString();
        date = in.readString();
        prodName = in.readString();
        prodCode = in.readString();
        prodFilterType = in.readString();
        primaryShelf = in.readString();
        secondaryShelf = in.readString();
        newShelfProdName = in.readString();
        newShelfProdlength = in.readString();
        newShelfProdDepth = in.readString();
        newShelfProdCapacity = in.readString();
        compPrimaryShelf = in.readString();
        compSecondaryShelf = in.readString();
        compShelfProdName = in.readString();
        compShelfProdlength = in.readString();
        compShelfProdDepth = in.readString();
        compShelfProdCapacity = in.readString();
        compShelfTempImageList = in.createStringArrayList();
    }

    public ShelfInfoModel() {

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

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdFilterType() {
        return prodFilterType;
    }

    public void setProdFilterType(String prodFilterType) {
        this.prodFilterType = prodFilterType;
    }

    public String getPrimaryShelf() {
        return primaryShelf;
    }

    public void setPrimaryShelf(String primaryShelf) {
        this.primaryShelf = primaryShelf;
    }

    public String getSecondaryShelf() {
        return secondaryShelf;
    }

    public void setSecondaryShelf(String secondaryShelf) {
        this.secondaryShelf = secondaryShelf;
    }

    public String getNewShelfProdName() {
        return newShelfProdName;
    }

    public void setNewShelfProdName(String newShelfProdName) {
        this.newShelfProdName = newShelfProdName;
    }

    public String getNewShelfProdlength() {
        return newShelfProdlength;
    }

    public void setNewShelfProdlength(String newShelfProdlength) {
        this.newShelfProdlength = newShelfProdlength;
    }

    public String getNewShelfProdDepth() {
        return newShelfProdDepth;
    }

    public void setNewShelfProdDepth(String newShelfProdDepth) {
        this.newShelfProdDepth = newShelfProdDepth;
    }

    public String getNewShelfProdCapacity() {
        return newShelfProdCapacity;
    }

    public void setNewShelfProdCapacity(String newShelfProdCapacity) {
        this.newShelfProdCapacity = newShelfProdCapacity;
    }

    public String getCompPrimaryShelf() {
        return compPrimaryShelf;
    }

    public void setCompPrimaryShelf(String compPrimaryShelf) {
        this.compPrimaryShelf = compPrimaryShelf;
    }

    public String getCompSecondaryShelf() {
        return compSecondaryShelf;
    }

    public void setCompSecondaryShelf(String compSecondaryShelf) {
        this.compSecondaryShelf = compSecondaryShelf;
    }

    public String getCompShelfProdName() {
        return compShelfProdName;
    }

    public void setCompShelfProdName(String compShelfProdName) {
        this.compShelfProdName = compShelfProdName;
    }

    public String getCompShelfProdlength() {
        return compShelfProdlength;
    }

    public void setCompShelfProdlength(String compShelfProdlength) {
        this.compShelfProdlength = compShelfProdlength;
    }

    public String getCompShelfProdDepth() {
        return compShelfProdDepth;
    }

    public void setCompShelfProdDepth(String compShelfProdDepth) {
        this.compShelfProdDepth = compShelfProdDepth;
    }

    public String getCompShelfProdCapacity() {
        return compShelfProdCapacity;
    }

    public void setCompShelfProdCapacity(String compShelfProdCapacity) {
        this.compShelfProdCapacity = compShelfProdCapacity;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public List<String> getCompShelfTempImageList() {
        return compShelfTempImageList;
    }

    public void setCompShelfTempImageList(List<String> compShelfTempImageList) {
        this.compShelfTempImageList = compShelfTempImageList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(salesmanCode);
        dest.writeString(routeCode);
        dest.writeString(refCode);
        dest.writeString(date);
        dest.writeString(prodName);
        dest.writeString(prodCode);
        dest.writeString(prodFilterType);
        dest.writeString(primaryShelf);
        dest.writeString(secondaryShelf);
        dest.writeString(newShelfProdName);
        dest.writeString(newShelfProdlength);
        dest.writeString(newShelfProdDepth);
        dest.writeString(newShelfProdCapacity);
        dest.writeString(compPrimaryShelf);
        dest.writeString(compSecondaryShelf);
        dest.writeString(compShelfProdName);
        dest.writeString(compShelfProdlength);
        dest.writeString(compShelfProdDepth);
        dest.writeString(compShelfProdCapacity);
        dest.writeStringList(compShelfTempImageList);
    }
}
