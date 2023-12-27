package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductMasterModel implements Parcelable {

    private String cmpCode = "";
    private String prodCode = "";
    private String prodName = "";
    private String prodShortName = "";
    private String prodStatus = "";
    private String prodType = "";
    private double prodShelfLife;
    private double prodGrossWgt;
    private double prodNetWgt;
    private String eanCode = "";
    private String prodHierLastValCode = "";
    private String prodWgtType = "";
    @SerializedName("lobCode")
    private String lobDivisionCode = "";
    private String lobSubDivisionCode = "";
    private String hsnCode = "";
    private String productHierPathCode = "";
    private String productHierPathName = "";
    private String uploadFlag = "";
    private String branchStock="";
    private String defaultUom = "";
    private int saleableQty;

    public ProductMasterModel() {
    }
// Getter Methods

    private ProductMasterModel(Parcel in) {
        cmpCode = in.readString();
        prodCode = in.readString();
        prodName = in.readString();
        prodShortName = in.readString();
        prodStatus = in.readString();
        prodType = in.readString();
        prodShelfLife = in.readFloat();
        prodGrossWgt = in.readFloat();
        prodNetWgt = in.readFloat();
        eanCode = in.readString();
        prodHierLastValCode = in.readString();
        prodWgtType = in.readString();
        lobDivisionCode = in.readString();
        lobSubDivisionCode = in.readString();
        hsnCode = in.readString();
        productHierPathCode = in.readString();
        productHierPathName = in.readString();
        uploadFlag = in.readString();
    }

    public static final Creator<ProductMasterModel> CREATOR = new Creator<ProductMasterModel>() {
        @Override
        public ProductMasterModel createFromParcel(Parcel in) {
            return new ProductMasterModel(in);
        }

        @Override
        public ProductMasterModel[] newArray(int size) {
            return new ProductMasterModel[size];
        }
    };

    public int getSaleableQty() {
        return saleableQty;
    }

    public void setSaleableQty(int saleableQty) {
        this.saleableQty = saleableQty;
    }

    public String getBranchStock() {
        return branchStock;
    }

    public void setBranchStock(String branchStock) {
        this.branchStock = branchStock;
    }

    public String getDefaultUom() {
        return defaultUom;
    }

    public void setDefaultUom(String defaultUom) {
        this.defaultUom = defaultUom;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public String getProdName() {
        return prodName;
    }

    public String getProdShortName() {
        return prodShortName;
    }

    public String getProdStatus() {
        return prodStatus;
    }

    public String getProdType() {
        return prodType;
    }

    public double getProdShelfLife() {
        return prodShelfLife;
    }

    public double getProdGrossWgt() {
        return prodGrossWgt;
    }

    public double getProdNetWgt() {
        return prodNetWgt;
    }

    public String getEanCode() {
        return eanCode;
    }

    public String getProdHierLastValCode() {
        return prodHierLastValCode;
    }

    public String getProdWgtType() {
        return prodWgtType;
    }

    public String getLobDivisionCode() {
        return lobDivisionCode;
    }

    public String getLobSubDivisionCode() {
        return lobSubDivisionCode;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public String getProductHierPathCode() {
        return productHierPathCode;
    }

    public String getProductHierPathName() {
        return productHierPathName;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    // Setter Methods

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public void setProdShortName(String prodShortName) {
        this.prodShortName = prodShortName;
    }

    public void setProdStatus(String prodStatus) {
        this.prodStatus = prodStatus;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public void setProdShelfLife(double prodShelfLife) {
        this.prodShelfLife = prodShelfLife;
    }

    public void setProdGrossWgt(double prodGrossWgt) {
        this.prodGrossWgt = prodGrossWgt;
    }

    public void setProdNetWgt(double prodNetWgt) {
        this.prodNetWgt = prodNetWgt;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode;
    }

    public void setProdHierLastValCode(String prodHierLastValCode) {
        this.prodHierLastValCode = prodHierLastValCode;
    }

    public void setProdWgtType(String prodWgtType) {
        this.prodWgtType = prodWgtType;
    }

    public void setLobDivisionCode(String lobDivisionCode) {
        this.lobDivisionCode = lobDivisionCode;
    }

    public void setLobSubDivisionCode(String lobSubDivisionCode) {
        this.lobSubDivisionCode = lobSubDivisionCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public void setProductHierPathCode(String productHierPathCode) {
        this.productHierPathCode = productHierPathCode;
    }

    public void setProductHierPathName(String productHierPathName) {
        this.productHierPathName = productHierPathName;
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
        dest.writeString(prodCode);
        dest.writeString(prodName);
        dest.writeString(prodShortName);
        dest.writeString(prodStatus);
        dest.writeString(prodType);
        dest.writeDouble(prodShelfLife);
        dest.writeDouble(prodGrossWgt);
        dest.writeDouble(prodNetWgt);
        dest.writeString(eanCode);
        dest.writeString(prodHierLastValCode);
        dest.writeString(prodWgtType);
        dest.writeString(lobDivisionCode);
        dest.writeString(lobSubDivisionCode);
        dest.writeString(hsnCode);
        dest.writeString(productHierPathCode);
        dest.writeString(productHierPathName);
        dest.writeString(uploadFlag);
    }
}
