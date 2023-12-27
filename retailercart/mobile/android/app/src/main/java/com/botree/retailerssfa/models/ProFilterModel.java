package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProFilterModel implements Parcelable {

    private String hierLelevelName1="";
    private String hierLelevelCode1="";

    private String hierLelevelName2="";
    private String hierLelevelCode2="";

    private String hierLelevelName3="";
    private String hierLelevelCode3="";

    private String hierLelevelName4="";
    private String hierLelevelCode4="";

    private String[] pathCode;
    private String[] pathName;

    private String lobDivisionCode="";
    private String lobDivisionName="";

    public String getLobDivisionCode() {
        return lobDivisionCode;
    }

    public void setLobDivisionCode(String lobDivisionCode) {
        this.lobDivisionCode = lobDivisionCode;
    }

    public String getLobDivisionName() {
        return lobDivisionName;
    }

    public void setLobDivisionName(String lobDivisionName) {
        this.lobDivisionName = lobDivisionName;
    }

    public String[] getPathName() {
        return pathName;
    }

    public void setPathName(String[] pathName) {
        this.pathName = pathName;
    }

    public ProFilterModel(){

    }

    private ProFilterModel(Parcel in) {

        hierLelevelName1 = in.readString();
        hierLelevelCode1 = in.readString();
        hierLelevelName2 = in.readString();
        hierLelevelCode2 = in.readString();
        hierLelevelName3 = in.readString();
        hierLelevelCode3 = in.readString();
        hierLelevelName4 = in.readString();
        hierLelevelCode4 = in.readString();
    }

    public static final Creator<ProFilterModel> CREATOR = new Creator<ProFilterModel>() {
        @Override
        public ProFilterModel createFromParcel(Parcel in) {
            return new ProFilterModel(in);
        }

        @Override
        public ProFilterModel[] newArray(int size) {
            return new ProFilterModel[size];
        }
    };

    public String getHierLelevelName1() {
        return hierLelevelName1;
    }

    public void setHierLelevelName1(String hierLelevelName1) {
        this.hierLelevelName1 = hierLelevelName1;
    }

    public String getHierLelevelCode1() {
        return hierLelevelCode1;
    }

    public void setHierLelevelCode1(String hierLelevelCode1) {
        this.hierLelevelCode1 = hierLelevelCode1;
    }


    public String getHierLelevelName2() {
        return hierLelevelName2;
    }

    public void setHierLelevelName2(String hierLelevelName2) {
        this.hierLelevelName2 = hierLelevelName2;
    }

    public String getHierLelevelCode2() {
        return hierLelevelCode2;
    }

    public void setHierLelevelCode2(String hierLelevelCode2) {
        this.hierLelevelCode2 = hierLelevelCode2;
    }

    public String getHierLelevelName3() {
        return hierLelevelName3;
    }

    public void setHierLelevelName3(String hierLelevelName3) {
        this.hierLelevelName3 = hierLelevelName3;
    }

    public String getHierLelevelCode3() {
        return hierLelevelCode3;
    }

    public void setHierLelevelCode3(String hierLelevelCode3) {
        this.hierLelevelCode3 = hierLelevelCode3;
    }

    public String getHierLelevelName4() {
        return hierLelevelName4;
    }

    public void setHierLelevelName4(String hierLelevelName4) {
        this.hierLelevelName4 = hierLelevelName4;
    }

    public String getHierLelevelCode4() {
        return hierLelevelCode4;
    }

    public void setHierLelevelCode4(String hierLelevelCode4) {
        this.hierLelevelCode4 = hierLelevelCode4;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(hierLelevelName1);
        dest.writeString(hierLelevelCode1);
        dest.writeString(hierLelevelName2);
        dest.writeString(hierLelevelCode2);
        dest.writeString(hierLelevelName3);
        dest.writeString(hierLelevelCode3);
        dest.writeString(hierLelevelName4);
        dest.writeString(hierLelevelCode4);
    }

    public String[] getPathCode() {
        return pathCode;
    }

    public void setPathCode(String[] pathCode) {
        this.pathCode = pathCode;
    }
}
