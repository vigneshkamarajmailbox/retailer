package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREFIX;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCREEN_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUFFIX_NN;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUFFIX_YY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UPLOAD_FLAG;

public class CodeGeneratorModel implements Parcelable {

    public static final Creator<CodeGeneratorModel> CREATOR = new Creator<CodeGeneratorModel>() {
        @Override
        public CodeGeneratorModel createFromParcel(Parcel in) {
            return new CodeGeneratorModel(in);
        }

        @Override
        public CodeGeneratorModel[] newArray(int size) {
            return new CodeGeneratorModel[size];
        }
    };
    @SerializedName(TAG_CMP_CODE)
    private String companyCode = "";

    @SerializedName(TAG_DISTR_CODE)
    private String distributorCode = "";

    @SerializedName(TAG_SCREEN_NAME)
    private String screenName = "";

    @SerializedName(TAG_PREFIX)
    private String prefix = "";

    @SerializedName(TAG_SUFFIX_YY)
    private String suffixYy = "";

    @SerializedName(TAG_SUFFIX_NN)
    private int suffixNn;

    @SerializedName(TAG_UPLOAD_FLAG)
    private String upload;

    public CodeGeneratorModel() {
    }

    public CodeGeneratorModel(String companyCode, String distributorCode, String screenName, String prefix, String suffixYy, int suffixNn) {
        this.companyCode = companyCode;
        this.distributorCode = distributorCode;
        this.screenName = screenName;
        this.prefix = prefix;
        this.suffixYy = suffixYy;
        this.suffixNn = suffixNn;
    }

    public CodeGeneratorModel(String screenName, int suffixNn) {
        this.screenName = screenName;
        this.suffixNn = suffixNn;
    }

    private CodeGeneratorModel(Parcel in) {
        companyCode = in.readString();
        distributorCode = in.readString();
        screenName = in.readString();
        prefix = in.readString();
        suffixYy = in.readString();
        suffixNn = in.readInt();
    }

    public static Creator<CodeGeneratorModel> getCREATOR() {
        return CREATOR;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffixYy() {
        return suffixYy;
    }

    public void setSuffixYy(String suffixYy) {
        this.suffixYy = suffixYy;
    }

    public int getSuffixNn() {
        return suffixNn;
    }

    public void setSuffixNn(int suffixNn) {
        this.suffixNn = suffixNn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(companyCode);
        dest.writeString(distributorCode);
        dest.writeString(screenName);
        dest.writeString(prefix);
        dest.writeString(suffixYy);
        dest.writeInt(suffixNn);
    }

    @Override
    public String toString() {
        return "CodeGeneratorModel{" +
                "companyCode='" + companyCode + '\'' +
                ", distributorCode='" + distributorCode + '\'' +
                ", screenName='" + screenName + '\'' +
                ", prefix='" + prefix + '\'' +
                ", suffixYy='" + suffixYy + '\'' +
                ", suffixNn=" + suffixNn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeGeneratorModel that = (CodeGeneratorModel) o;

        return (Objects.equals(screenName, that.screenName)) && (Objects.equals(prefix, that.prefix)) && (Objects.equals(suffixYy, that.suffixYy));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
