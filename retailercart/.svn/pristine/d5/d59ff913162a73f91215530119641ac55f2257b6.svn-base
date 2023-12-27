package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class StockOnHandModel implements Parcelable {

    private String cmpCode;
    private String uploadFlag;
    private long modDt;
    private String distrCode;
    private String godownCode;
    private String prodCode;
    private String prodBatchCode;
    private Integer saleableQty;
    private Integer unSaleableQty;
    private Integer offerQty;
    private Integer resvSaleableQty;
    private Integer resvUnSaleableQty;
    private Integer resvOfferQty;
    private Integer availQty = 0;
    private Integer lastOrdQty = 0;

    public StockOnHandModel(){}

    public StockOnHandModel(Parcel in) {
        cmpCode = in.readString();
        uploadFlag = in.readString();
        modDt = in.readLong();
        distrCode = in.readString();
        godownCode = in.readString();
        prodCode = in.readString();
        prodBatchCode = in.readString();
        saleableQty = in.readInt();
        unSaleableQty = in.readInt();
        offerQty = in.readInt();
        resvSaleableQty = in.readInt();
        resvUnSaleableQty = in.readInt();
        resvOfferQty = in.readInt();
        availQty = in.readInt();
        lastOrdQty = in.readInt();
    }

    public static final Creator<StockOnHandModel> CREATOR = new Creator<StockOnHandModel>() {
        @Override
        public StockOnHandModel createFromParcel(Parcel in) {
            return new StockOnHandModel(in);
        }

        @Override
        public StockOnHandModel[] newArray(int size) {
            return new StockOnHandModel[size];
        }
    };

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public long getModDt() {
        return modDt;
    }

    public void setModDt(long modDt) {
        this.modDt = modDt;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getGodownCode() {
        return godownCode;
    }

    public void setGodownCode(String godownCode) {
        this.godownCode = godownCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdBatchCode() {
        return prodBatchCode;
    }

    public void setProdBatchCode(String prodBatchCode) {
        this.prodBatchCode = prodBatchCode;
    }

    public Integer getSaleableQty() {
        return saleableQty;
    }

    public void setSaleableQty(Integer saleableQty) {
        this.saleableQty = saleableQty;
    }

    public Integer getUnSaleableQty() {
        return unSaleableQty;
    }

    public void setUnSaleableQty(Integer unSaleableQty) {
        this.unSaleableQty = unSaleableQty;
    }

    public Integer getOfferQty() {
        return offerQty;
    }

    public void setOfferQty(Integer offerQty) {
        this.offerQty = offerQty;
    }

    public Integer getResvSaleableQty() {
        return resvSaleableQty;
    }

    public void setResvSaleableQty(Integer resvSaleableQty) {
        this.resvSaleableQty = resvSaleableQty;
    }

    public Integer getResvUnSaleableQty() {
        return resvUnSaleableQty;
    }

    public void setResvUnSaleableQty(Integer resvUnSaleableQty) {
        this.resvUnSaleableQty = resvUnSaleableQty;
    }

    public Integer getResvOfferQty() {
        return resvOfferQty;
    }

    public void setResvOfferQty(Integer resvOfferQty) {
        this.resvOfferQty = resvOfferQty;
    }

    public Integer getAvailQty() {
        return availQty;
    }

    public void setAvailQty(Integer availQty) {
        this.availQty = availQty;
    }

    public Integer getLastOrdQty() {
        return lastOrdQty;
    }

    public void setLastOrdQty(Integer lastOrdQty) {
        this.lastOrdQty = lastOrdQty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(uploadFlag);
        dest.writeLong(modDt);
        dest.writeString(distrCode);
        dest.writeString(godownCode);
        dest.writeString(prodCode);
        dest.writeString(prodBatchCode);
        dest.writeInt(saleableQty);
        dest.writeInt(unSaleableQty);
        dest.writeInt(offerQty);
        dest.writeInt(resvSaleableQty);
        dest.writeInt(resvUnSaleableQty);
        dest.writeInt(resvOfferQty);
        dest.writeInt(availQty);
        dest.writeInt(lastOrdQty);
    }
}
