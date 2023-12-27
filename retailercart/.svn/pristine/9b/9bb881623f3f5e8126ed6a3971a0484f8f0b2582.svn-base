package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_SCREEN_ACCESS_CMPCODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCREEN_ACCESS_ISACCESS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCREEN_ACCESS_SEQ;

public class ScreenConfig implements Parcelable{
    private String id = "";
    @SerializedName(TAG_SCREEN_ACCESS_CMPCODE)
    private String cmpCode = "";
    private int ModuleNo = 0;
    private int ScreenNo = 0;
    private String applicationType = "";
    private String ModuleName = "";
    private String ScreenName = "";
    @SerializedName(TAG_SCREEN_ACCESS_ISACCESS)
    private String checked = "";
    @SerializedName(TAG_SCREEN_ACCESS_SEQ)
    private Integer sequence = 0;
    private String Logincode = "";
    private String Distrcode = "";
    private String GroupCode = "";
    private String GroupName = "";
    private String screenAccess = "";
    private String ScreenType = "";

    public ScreenConfig(Parcel in) {
        id = in.readString();
        cmpCode = in.readString();
        ModuleNo = in.readInt();
        ScreenNo = in.readInt();
        applicationType = in.readString();
        ModuleName = in.readString();
        ScreenName = in.readString();
        checked = in.readString();
    }

    public static final Creator<ScreenConfig> CREATOR = new Creator<ScreenConfig>() {
        @Override
        public ScreenConfig createFromParcel(Parcel in) {
            return new ScreenConfig(in);
        }

        @Override
        public ScreenConfig[] newArray(int size) {
            return new ScreenConfig[size];
        }
    };

    public ScreenConfig() {

    }

    public String getScreenType() {
        return ScreenType;
    }

    public void setScreenType(String screenType) {
        this.ScreenType = screenType;
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

    public int getModuleNo() {
        return ModuleNo;
    }

    public void setModuleNo(int moduleNo) {
        this.ModuleNo = moduleNo;
    }

    public int getScreenNo() {
        return ScreenNo;
    }

    public void setScreenNo(int screenNo) {
        this.ScreenNo = screenNo;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        this.ModuleName = moduleName;
    }

    public String getScreenName() {
        return ScreenName;
    }

    public void setScreenName(String screenName) {
        this.ScreenName = screenName;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(cmpCode);
        dest.writeInt(ModuleNo);
        dest.writeInt(ScreenNo);
        dest.writeString(applicationType);
        dest.writeString(ModuleName);
        dest.writeString(ScreenName);
        dest.writeString(checked);
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getScreenAccess() {
        return screenAccess;
    }

    public void setScreenAccess(String screenAccess) {
        this.screenAccess = screenAccess;
    }

    public String getLogincode() {
        return Logincode;
    }

    public void setLogincode(String logincode) {
        Logincode = logincode;
    }

    public String getDistrcode() {
        return Distrcode;
    }

    public void setDistrcode(String distrcode) {
        Distrcode = distrcode;
    }

    public String getGroupCode() {
        return GroupCode;
    }

    public void setGroupCode(String groupCode) {
        GroupCode = groupCode;
    }

    public String getGroupName() {
        return GroupCode;
    }

    public void setGroupName(String groupName) {
        GroupCode = groupName;
    }
}
