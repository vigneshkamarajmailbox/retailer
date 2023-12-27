package com.botree.retailerssfa.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.controller.constants.AppConstant.FILE_DOWNLOAD;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MSG_BODY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MSG_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MSG_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MSG_TITLE;

public class MessageModel implements Parcelable {

    public static final Creator<MessageModel> CREATOR = new Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(Parcel in) {
            return new MessageModel(in);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };
    private String salesmanCode = "";

    @SerializedName(TAG_MSG_TITLE)
    private String msgTitle = "";

    @SerializedName(TAG_MSG_BODY)
    private String msgBody = "";

    @SerializedName(TAG_MSG_CODE)
    private String msgCode = "";
    private String msgStatus = "1";
    private String distCode = "";

    @SerializedName(TAG_MSG_DATE)
    private String msgDate = "";
    private String cmpCode = "";
    private String notificationType = "";
    private String notificationName = "";
    private String fileName = "";
    private String messageDetail = "";
    private int downloadStatus = FILE_DOWNLOAD;
    private int downloadPercent = 0;

    public MessageModel(String s, String s1, String s2) {
        this.msgTitle = s;
        this.msgBody = s1;
        this.msgDate = s2;
    }

    public MessageModel() {

    }

    protected MessageModel(Parcel in) {
        salesmanCode = in.readString();
        msgTitle = in.readString();
        msgBody = in.readString();
        msgCode = in.readString();
        msgStatus = in.readString();
        distCode = in.readString();
        msgDate = in.readString();
        cmpCode = in.readString();
        fileName = in.readString();
        downloadStatus = in.readInt();
        messageDetail = in.readString();
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }


    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }


    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(salesmanCode);
        dest.writeString(msgTitle);
        dest.writeString(msgBody);
        dest.writeString(msgCode);
        dest.writeString(msgStatus);
        dest.writeString(distCode);
        dest.writeString(msgDate);
        dest.writeString(cmpCode);
        dest.writeString(fileName);
        dest.writeInt(downloadStatus);
        dest.writeString(messageDetail);
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public int getDownloadPercent() {
        return downloadPercent;
    }

    public void setDownloadPercent(int downloadPercent) {
        this.downloadPercent = downloadPercent;
    }
}
