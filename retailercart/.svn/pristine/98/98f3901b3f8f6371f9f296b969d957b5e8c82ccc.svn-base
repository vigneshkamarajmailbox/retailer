package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ReportZipModel implements Parcelable {
    private String reportProcessMapping;
    private String reportFieldMapping;

    protected ReportZipModel(Parcel in) {
        reportProcessMapping = in.readString();
        reportFieldMapping = in.readString();
    }

    public static final Creator<ReportZipModel> CREATOR = new Creator<ReportZipModel>() {
        @Override
        public ReportZipModel createFromParcel(Parcel in) {
            return new ReportZipModel(in);
        }

        @Override
        public ReportZipModel[] newArray(int size) {
            return new ReportZipModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reportProcessMapping);
        parcel.writeString(reportFieldMapping);
    }
}
