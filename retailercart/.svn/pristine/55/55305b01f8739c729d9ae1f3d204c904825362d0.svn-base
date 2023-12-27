package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SF_LEVEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SF_LEVEL_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SF_VALUE_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TYPE;

public class SalesHierarchy implements Parcelable {

    public static final Creator<SalesHierarchy> CREATOR = new Creator<SalesHierarchy>() {
        @Override
        public SalesHierarchy createFromParcel(Parcel in) {
            return new SalesHierarchy(in);
        }

        @Override
        public SalesHierarchy[] newArray(int size) {
            return new SalesHierarchy[size];
        }
    };
    private String id = "";
    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";
    @SerializedName(TAG_SF_LEVEL_CODE)
    private Integer sflevelCode;
    @SerializedName(value = TAG_SF_VALUE_NAME, alternate = TAG_SF_LEVEL_NAME)
    private String sflevelName = "";
    private String salesForceCode = "";
    private String salesForceName = "";
    private String lobCode = "";
    private String parentCode = "";
    @SerializedName(TAG_TYPE)
    private String type = "";
    private boolean isEnabled = false;

    public SalesHierarchy(Parcel in) {
        id = in.readString();
        cmpCode = in.readString();
        if (in.readByte() == 0) {
            sflevelCode = null;
        } else {
            sflevelCode = in.readInt();
        }
        sflevelName = in.readString();
        salesForceCode = in.readString();
        salesForceName = in.readString();
        lobCode = in.readString();
        parentCode = in.readString();
        isEnabled = in.readByte() != 0;
    }

    public SalesHierarchy() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public Integer getSflevelCode() {
        return sflevelCode;
    }

    public void setSflevelCode(Integer sflevelCode) {
        this.sflevelCode = sflevelCode;
    }

    public String getSflevelName() {
        return sflevelName;
    }

    public void setSflevelName(String sflevelName) {
        this.sflevelName = sflevelName;
    }

    public String getSalesForceCode() {
        return salesForceCode;
    }

    public void setSalesForceCode(String salesForceCode) {
        this.salesForceCode = salesForceCode;
    }

    public String getSalesForceName() {
        return salesForceName;
    }

    public void setSalesForceName(String salesForceName) {
        this.salesForceName = salesForceName;
    }

    public String getLobCode() {
        return lobCode;
    }

    public void setLobCode(String lobCode) {
        this.lobCode = lobCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cmpCode);
        if (sflevelCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sflevelCode);
        }
        dest.writeString(sflevelName);
        dest.writeString(salesForceCode);
        dest.writeString(salesForceName);
        dest.writeString(lobCode);
        dest.writeString(parentCode);
        dest.writeByte((byte) (isEnabled ? 1 : 0));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
