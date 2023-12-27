package com.botree.retailerssfa.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Download implements Parcelable {

    public Download(){

    }

    private float progress;
    private float currentFileSize;
    private float totalFileSize;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public float getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(float currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public float getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(float totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeFloat(progress);
        dest.writeFloat(currentFileSize);
        dest.writeFloat(totalFileSize);
    }

    private Download(Parcel in) {

        progress = in.readFloat();
        currentFileSize = in.readFloat();
        totalFileSize = in.readFloat();
    }

    public static final Creator<Download> CREATOR = new Creator<Download>() {
        public Download createFromParcel(Parcel in) {
            return new Download(in);
        }

        public Download[] newArray(int size) {
            return new Download[size];
        }
    };
}
