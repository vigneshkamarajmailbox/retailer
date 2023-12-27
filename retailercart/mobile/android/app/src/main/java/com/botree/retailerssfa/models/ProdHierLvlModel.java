package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProdHierLvlModel implements Parcelable {

    private String cmpCode="";
    private String prodHierLvlCode="";
    private String prodHierLvlName = "";
    private String prodHierValCode = "";
    private String prodHierValName = "";
    private String parentCode = null;
    private String uploadFlag = "";

    public String getProdHierValCode() {
        return prodHierValCode;
    }

    public void setProdHierValCode(String prodHierValCode) {
        this.prodHierValCode = prodHierValCode;
    }

    public String getProdHierValName() {
        return prodHierValName;
    }

    public void setProdHierValName(String prodHierValName) {
        this.prodHierValName = prodHierValName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }


    // Getter Methods

    protected ProdHierLvlModel(Parcel in) {
        cmpCode = in.readString();
        prodHierLvlCode = in.readString();
        prodHierLvlName = in.readString();
        prodHierValCode = in.readString();
        prodHierValName = in.readString();
        parentCode = in.readString();
        uploadFlag = in.readString();
    }

    public static final Creator<ProdHierLvlModel> CREATOR = new Creator<ProdHierLvlModel>() {
        @Override
        public ProdHierLvlModel createFromParcel(Parcel in) {
            return new ProdHierLvlModel(in);
        }

        @Override
        public ProdHierLvlModel[] newArray(int size) {
            return new ProdHierLvlModel[size];
        }
    };

    public String getCmpCode() {
        return cmpCode;
    }

    public String getProdHierLvlCode() {
        return prodHierLvlCode;
    }

    public String getProdHierLvlName() {
        return prodHierLvlName;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    // Setter Methods

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public void setProdHierLvlCode(String prodHierLvlCode) {
        this.prodHierLvlCode = prodHierLvlCode;
    }

    public void setProdHierLvlName(String prodHierLvlName) {
        this.prodHierLvlName = prodHierLvlName;
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
        dest.writeString(prodHierLvlCode);
        dest.writeString(prodHierLvlName);
        dest.writeString(prodHierValCode);
        dest.writeString(prodHierValName);
        dest.writeString(parentCode);
        dest.writeString(uploadFlag);
    }
}
