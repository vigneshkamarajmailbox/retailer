package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.db.query.IDBColumns.USER_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MESSAGE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SYNC_DT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SYNC_LOG_ENTITY_STATUS_LIST;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SYNC_START_TIME;

public class TimeCaptureModel {

    private String cmpCode="";
    private String loginCode="";
    @SerializedName(USER_TYPE)
    private String userType="";
    @SerializedName(TAG_SYNC_DT)
    private String syncDate="";
    private String mode="";
    private String processName="";
    @SerializedName(TAG_MESSAGE)
    private String status="";
    private String upload="";
    private String latitude="0";
    private String longitude="0";
    private String userName ="";
    @SerializedName(TAG_SYNC_START_TIME)
    private String syncTime="";

    @SerializedName(TAG_SYNC_LOG_ENTITY_STATUS_LIST)
    private List<TimeCaptureModel> syncLogList = new ArrayList<>();

    public List<TimeCaptureModel> getSyncLogList() {
        return syncLogList;
    }

    public void setSyncLogList(List<TimeCaptureModel> syncLogList) {
        this.syncLogList = syncLogList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getUserCode() {
        return loginCode;
    }

    public void setUserCode(String userCode) {
        this.loginCode = userCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(String syncDate) {
        this.syncDate = syncDate;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    private String syncEndTime="";

    public String getSyncEndTime() {
        return syncEndTime;
    }

    public void setSyncEndTime(String syncEndTime) {
        this.syncEndTime = syncEndTime;
    }
}
