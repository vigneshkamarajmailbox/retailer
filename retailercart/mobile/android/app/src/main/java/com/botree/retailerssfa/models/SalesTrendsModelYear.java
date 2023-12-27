package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SalesTrendsModelYear implements Parcelable {
    public static final Creator<SalesTrendsModelYear> CREATOR = new Creator<SalesTrendsModelYear>() {
        @Override
        public SalesTrendsModelYear createFromParcel(Parcel in) {
            return new SalesTrendsModelYear(in);
        }

        @Override
        public SalesTrendsModelYear[] newArray(int size) {
            return new SalesTrendsModelYear[size];
        }
    };
    @SerializedName("headerList")
    private List<MTDModel> salesYearTrendsCurrentUser = new ArrayList<>();
    @SerializedName("detailsList")
    private List<MTDModel> salesYearTrendsNextLevelUser = new ArrayList<>();

    public SalesTrendsModelYear(Parcel in) {
        salesYearTrendsCurrentUser = in.createTypedArrayList(MTDModel.CREATOR);
        salesYearTrendsNextLevelUser = in.createTypedArrayList(MTDModel.CREATOR);
    }

    public SalesTrendsModelYear() {

    }

    public List<MTDModel> getSalesYearTrendsCurrentUser() {
        return salesYearTrendsCurrentUser;
    }

    public void setSalesYearTrendsCurrentUser(List<MTDModel> salesYearTrendsCurrentUser) {
        this.salesYearTrendsCurrentUser = salesYearTrendsCurrentUser;
    }

    public List<MTDModel> getSalesYearTrendsNextLevelUser() {
        return salesYearTrendsNextLevelUser;
    }

    public void setSalesYearTrendsNextLevelUser(List<MTDModel> salesYearTrendsNextLevelUser) {
        this.salesYearTrendsNextLevelUser = salesYearTrendsNextLevelUser;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(salesYearTrendsCurrentUser);
        dest.writeTypedList(salesYearTrendsNextLevelUser);
    }

}
