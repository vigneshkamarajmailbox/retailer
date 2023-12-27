package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SchemeZipModel implements Parcelable {
    private String schemeDefinition;
    private String schemeSlab;
    private String schemeSlabProduct;
    private String schemeCombiProduct;
    private String schemeDistributorBudget;
    private String schemeCustomerMapping;

    public SchemeZipModel() {
    }

    private SchemeZipModel(Parcel in) {
        schemeDefinition = in.readString();
        schemeSlab = in.readString();
        schemeSlabProduct = in.readString();
        schemeDistributorBudget = in.readString();
        schemeCustomerMapping = in.readString();
        schemeCombiProduct = in.readString();
    }

    public static final Creator<SchemeZipModel> CREATOR = new Creator<SchemeZipModel>() {
        @Override
        public SchemeZipModel createFromParcel(Parcel in) {
            return new SchemeZipModel(in);
        }

        @Override
        public SchemeZipModel[] newArray(int size) {
            return new SchemeZipModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(schemeDefinition);
        dest.writeString(schemeSlab);
        dest.writeString(schemeSlabProduct);
        dest.writeString(schemeDistributorBudget);
        dest.writeString(schemeCustomerMapping);
        dest.writeString(schemeCombiProduct);
    }
}
