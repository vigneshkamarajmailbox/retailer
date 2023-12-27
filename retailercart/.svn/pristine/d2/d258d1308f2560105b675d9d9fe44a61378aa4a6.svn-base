package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SalesTrendsModel implements Parcelable {
    public static final Creator<SalesTrendsModel> CREATOR = new Creator<SalesTrendsModel>() {
        @Override
        public SalesTrendsModel createFromParcel(Parcel in) {
            return new SalesTrendsModel(in);
        }

        @Override
        public SalesTrendsModel[] newArray(int size) {
            return new SalesTrendsModel[size];
        }
    };
    @SerializedName("headerList")
    private List<MTDModel> salesMonthTrendsCurrentUser =new ArrayList<>();
    @SerializedName("detailsList")
    private List<MTDModel> salesMonthTrendsNextLevelUser = new ArrayList<>();

    public SalesTrendsModel(Parcel in) {
        salesMonthTrendsCurrentUser = in.createTypedArrayList(MTDModel.CREATOR);
        salesMonthTrendsNextLevelUser = in.createTypedArrayList(MTDModel.CREATOR);
    }

    public SalesTrendsModel() {

    }

    public List<MTDModel> getSalesMonthTrendsCurrentUser() {
        return salesMonthTrendsCurrentUser;
    }

    public void setSalesMonthTrendsCurrentUser(List<MTDModel> salesMonthTrendsCurrentUser) {
        this.salesMonthTrendsCurrentUser = salesMonthTrendsCurrentUser;
    }

    public List<MTDModel> getSalesMonthTrendsNextLevelUser() {
        return salesMonthTrendsNextLevelUser;
    }

    public void setSalesMonthTrendsNextLevelUser(List<MTDModel> salesMonthTrendsNextLevelUser) {
        this.salesMonthTrendsNextLevelUser = salesMonthTrendsNextLevelUser;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(salesMonthTrendsCurrentUser);
        dest.writeTypedList(salesMonthTrendsNextLevelUser);
    }

}
