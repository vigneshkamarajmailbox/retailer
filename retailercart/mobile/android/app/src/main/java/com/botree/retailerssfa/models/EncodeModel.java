package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class EncodeModel implements Parcelable {

    String enkey = "";
    String enValue = "";

    public EncodeModel(Parcel in) {
        enkey = in.readString();
        enValue = in.readString();
    }

    public static final Creator<EncodeModel> CREATOR = new Creator<EncodeModel>() {
        @Override
        public EncodeModel createFromParcel(Parcel in) {
            return new EncodeModel(in);
        }

        @Override
        public EncodeModel[] newArray(int size) {
            return new EncodeModel[size];
        }
    };

    public EncodeModel() {

    }

    public String getEnkey() {
        return enkey;
    }

    public void setEnkey(String enkey) {
        this.enkey = enkey;
    }

    public String getEnValue() {
        return enValue;
    }

    public void setEnValue(String enValue) {
        this.enValue = enValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(enkey);
        dest.writeString(enValue);
    }
}
