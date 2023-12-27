package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BannerModel implements Parcelable {

    private String cmpCode="";
    private String bannerDesc="";
    private String fileName="";
    private String originalFileName="";
    private String type="";

    private BannerModel(Parcel in) {
        cmpCode = in.readString();
        bannerDesc = in.readString();
        fileName = in.readString();
        originalFileName = in.readString();
        type = in.readString();
    }

    public static final Creator<BannerModel> CREATOR = new Creator<BannerModel>() {
        @Override
        public BannerModel createFromParcel(Parcel in) {
            return new BannerModel(in);
        }

        @Override
        public BannerModel[] newArray(int size) {
            return new BannerModel[size];
        }
    };

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getBannerDesc() {
        return bannerDesc;
    }

    public void setBannerDesc(String bannerDesc) {
        this.bannerDesc = bannerDesc;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(bannerDesc);
        dest.writeString(fileName);
        dest.writeString(originalFileName);
        dest.writeString(type);
    }
}
