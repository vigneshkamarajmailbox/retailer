package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BulletinBoardModel implements Parcelable {
    private String cmpCode;
    private String messageCode;
    private String channelCode;
    private String subChannelCode;
    private String groupCode;
    private String classCode;
    private String distrCode;
    private String expiryDate;
    private String subject;
    private String messageDesc;
    private String attachment;
    private String notificationType;


    protected BulletinBoardModel(Parcel in) {
        cmpCode = in.readString();
        messageCode = in.readString();
        channelCode = in.readString();
        subChannelCode = in.readString();
        groupCode = in.readString();
        classCode = in.readString();
        distrCode = in.readString();
        expiryDate = in.readString();
        subject = in.readString();
        messageDesc = in.readString();
        attachment = in.readString();
        notificationType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(messageCode);
        dest.writeString(channelCode);
        dest.writeString(subChannelCode);
        dest.writeString(groupCode);
        dest.writeString(classCode);
        dest.writeString(distrCode);
        dest.writeString(expiryDate);
        dest.writeString(subject);
        dest.writeString(messageDesc);
        dest.writeString(attachment);
        dest.writeString(notificationType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BulletinBoardModel> CREATOR = new Creator<BulletinBoardModel>() {
        @Override
        public BulletinBoardModel createFromParcel(Parcel in) {
            return new BulletinBoardModel(in);
        }

        @Override
        public BulletinBoardModel[] newArray(int size) {
            return new BulletinBoardModel[size];
        }
    };

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getSubChannelCode() {
        return subChannelCode;
    }

    public void setSubChannelCode(String subChannelCode) {
        this.subChannelCode = subChannelCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageDesc() {
        return messageDesc;
    }

    public void setMessageDesc(String messageDesc) {
        this.messageDesc = messageDesc;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
