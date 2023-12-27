package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SchemeCustomerZipModel implements Parcelable {
    private String key;
    private String value;

    protected SchemeCustomerZipModel(Parcel in) {
        key = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SchemeCustomerZipModel> CREATOR = new Creator<SchemeCustomerZipModel>() {
        @Override
        public SchemeCustomerZipModel createFromParcel(Parcel in) {
            return new SchemeCustomerZipModel(in);
        }

        @Override
        public SchemeCustomerZipModel[] newArray(int size) {
            return new SchemeCustomerZipModel[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
