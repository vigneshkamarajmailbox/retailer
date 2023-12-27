package com.botree.retailerssfa.bot;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by shantarao on 26/12/17.
 */

public class ChatModel implements Parcelable {
    private String messageId;
    private int userId;
    private String firstName;
    private String userName;
    private String message;
    private Date chatTime;


    public ChatModel(String msgId, int userId, String firstName, String userName, String message, Date date){

        this.messageId = msgId;
        this.userId=userId;
        this.firstName=firstName;
        this.userName=userName;
        this.message=message;
        this.chatTime = date;

    }

    private ChatModel(Parcel in) {
        messageId = in.readString();
        userId = in.readInt();
        firstName = in.readString();
        userName = in.readString();
        message = in.readString();
    }

    public static final Creator<ChatModel> CREATOR = new Creator<ChatModel>() {
        @Override
        public ChatModel createFromParcel(Parcel in) {
            return new ChatModel(in);
        }

        @Override
        public ChatModel[] newArray(int size) {
            return new ChatModel[size];
        }
    };

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getChatTime() {
        return chatTime;
    }

    public void setChatTime(Date chatTime) {
        this.chatTime = chatTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(messageId);
        dest.writeInt(userId);
        dest.writeString(firstName);
        dest.writeString(userName);
        dest.writeString(message);
    }
}
