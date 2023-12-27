package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CompanyModel implements Parcelable {

    private String cmpCode;
    private String cmpName = "";
    private String cmpAddr1 = "";
    private String cmpAddr2 = "";
    private String cmpAddr3 = "";
    private String city = "";
    private String state = "";
    private String country = "";
    private String postalCode = "";

    // Getter Methods

    protected CompanyModel(Parcel in) {
        cmpCode = in.readString();
        cmpName = in.readString();
        cmpAddr1 = in.readString();
        cmpAddr2 = in.readString();
        cmpAddr3 = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        postalCode = in.readString();
    }

    public static final Creator<CompanyModel> CREATOR = new Creator<CompanyModel>() {
        @Override
        public CompanyModel createFromParcel(Parcel in) {
            return new CompanyModel(in);
        }

        @Override
        public CompanyModel[] newArray(int size) {
            return new CompanyModel[size];
        }
    };

    public String getCmpCode() {
        return cmpCode;
    }

    public String getCmpName() {
        return cmpName;
    }

    public String getCmpAddr1() {
        return cmpAddr1;
    }

    public String getCmpAddr2() {
        return cmpAddr2;
    }

    public String getCmpAddr3() {
        return cmpAddr3;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    // Setter Methods

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = cmpName;
    }

    public void setCmpAddr1(String cmpAddr1) {
        this.cmpAddr1 = cmpAddr1;
    }

    public void setCmpAddr2(String cmpAddr2) {
        this.cmpAddr2 = cmpAddr2;
    }

    public void setCmpAddr3(String cmpAddr3) {
        this.cmpAddr3 = cmpAddr3;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(cmpName);
        dest.writeString(cmpAddr1);
        dest.writeString(cmpAddr2);
        dest.writeString(cmpAddr3);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(postalCode);
    }
}
