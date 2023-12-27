package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_ACTION_TIME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_FREE_TEXT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ORDER_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ORDER_STATUS;

/**
 * Created by shantarao on 6/2/17.
 */

public class OrderStatusModel implements Parcelable {

    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";

    @SerializedName(TAG_DISTR_CODE)
    private String distrCode = "";

    @SerializedName(TAG_ORDER_NO)
    private String orderNo = "";

    @SerializedName(TAG_ORDER_STATUS)
    private String orderStatus = "";

    @SerializedName(TAG_ACTION_TIME)
    private String actionTime = "";

    @SerializedName(TAG_FREE_TEXT)
    private String freeText = "";


    public OrderStatusModel() {

    }

    protected OrderStatusModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        orderNo = in.readString();
        orderStatus= in.readString();
        actionTime = in.readString();
        freeText = in.readString();
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public static final Creator<OrderStatusModel> CREATOR = new Creator<OrderStatusModel>() {
        @Override
        public OrderStatusModel createFromParcel(Parcel in) {
            return new OrderStatusModel(in);
        }

        @Override
        public OrderStatusModel[] newArray(int size) {
            return new OrderStatusModel[size];
        }
    };

    public static Creator<OrderStatusModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderStatusModel)) return false;

        OrderStatusModel class1 = (OrderStatusModel) o;

        return orderNo.equals(class1.orderNo);
    }

    @Override
    public int hashCode() {
        return orderNo.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(orderNo);
        dest.writeString(orderStatus);
        dest.writeString(actionTime);
        dest.writeString(freeText);
    }
}
