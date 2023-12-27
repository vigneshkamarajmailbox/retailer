package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ManualClaimVO implements Parcelable {

    private String company;
    private String currdate;
    private String jcYear;
    private String fromJCMonth;
    private String toJCMonth;
    private String refNo;
    private String claimAmt;
    private String claimDesc;
    private String remarks;

    public ManualClaimVO() {

    }

    protected ManualClaimVO(Parcel in) {
        company = in.readString();
        currdate = in.readString();
        jcYear = in.readString();
        fromJCMonth = in.readString();
        toJCMonth = in.readString();
        refNo = in.readString();
        claimAmt = in.readString();
        claimDesc = in.readString();
        remarks = in.readString();
    }

    public static final Creator<ManualClaimVO> CREATOR = new Creator<ManualClaimVO>() {
        @Override
        public ManualClaimVO createFromParcel(Parcel in) {
            return new ManualClaimVO(in);
        }

        @Override
        public ManualClaimVO[] newArray(int size) {
            return new ManualClaimVO[size];
        }
    };

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCurrdate() {
        return currdate;
    }

    public void setCurrdate(String currdate) {
        this.currdate = currdate;
    }

    public String getJcYear() {
        return jcYear;
    }

    public void setJcYear(String jcYear) {
        this.jcYear = jcYear;
    }

    public String getFromJCMonth() {
        return fromJCMonth;
    }

    public void setFromJCMonth(String fromJCMonth) {
        this.fromJCMonth = fromJCMonth;
    }

    public String getToJCMonth() {
        return toJCMonth;
    }

    public void setToJCMonth(String toJCMonth) {
        this.toJCMonth = toJCMonth;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getClaimAmt() {
        return claimAmt;
    }

    public void setClaimAmt(String claimAmt) {
        this.claimAmt = claimAmt;
    }

    public String getClaimDesc() {
        return claimDesc;
    }

    public void setClaimDesc(String claimDesc) {
        this.claimDesc = claimDesc;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(company);
        parcel.writeString(currdate);
        parcel.writeString(jcYear);
        parcel.writeString(fromJCMonth);
        parcel.writeString(toJCMonth);
        parcel.writeString(refNo);
        parcel.writeString(claimAmt);
        parcel.writeString(claimDesc);
        parcel.writeString(remarks);
    }
}
