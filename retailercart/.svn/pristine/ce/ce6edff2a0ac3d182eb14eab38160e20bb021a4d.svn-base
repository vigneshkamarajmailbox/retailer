package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_SHIP_ADDR1;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_SHIP_ADDR2;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_SHIP_ADDR3;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_SHIP_CITY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_SHIP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_SHIP_DEF_ADDR;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_SHIP_GEOHIERPATH;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_SHIP_GSTSTATECODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_SHIP_PHONENO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MOD_DT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UPLOAD_FLAG;

public class AddressModel implements Parcelable {
    @SerializedName(TAG_CUSTOMER_SHIP_ADDR1)
    private String shippingAddress1 = "";
    @SerializedName(TAG_CUSTOMER_SHIP_ADDR2)
    private String shippingAddress2 = "";
    @SerializedName(TAG_CUSTOMER_SHIP_ADDR3)
    private String shippingAddress3 = "";
    private String shippingZipCode = "";
    @SerializedName(TAG_CUSTOMER_SHIP_GSTSTATECODE)
    private String shippingState = "";
    private String shippingLatitude = "";
    private String shippingLongitude = "";
    @SerializedName(TAG_CUSTOMER_SHIP_DEF_ADDR)
    private String isDefault = "N";
    @SerializedName(TAG_CUSTOMER_SHIP_GEOHIERPATH)
    private String shippingGeoHierPath = "";
    @SerializedName(TAG_CUSTOMER_SHIP_PHONENO)
    private String shippingPhoneNumber = "";
    @SerializedName(TAG_CUSTOMER_SHIP_CODE)
    private String shipCode = "";

    private String shipGeography = "";
    @SerializedName(TAG_CUSTOMER_SHIP_CITY)
    private String shipCityTown = "";

    @SerializedName(TAG_CUSTOMER_CODE)
    private String customerCode = "";
    private String tempCustomerCode = "";
    private String tempShipCode = "";

    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";

    @SerializedName(TAG_DISTR_CODE)
    private String distrCode = "";


    @SerializedName(TAG_MOD_DT)
    private String modDt = "";

    @SerializedName(TAG_UPLOAD_FLAG)
    private String uploadFlag = "";

    public AddressModel() {
    }

    protected AddressModel(Parcel in) {
        shippingAddress1 = in.readString();
        shippingAddress2 = in.readString();
        shippingAddress3 = in.readString();
        shippingZipCode = in.readString();
        shippingState = in.readString();
        shippingLatitude = in.readString();
        shippingLongitude = in.readString();
        isDefault = in.readString();
        shippingGeoHierPath = in.readString();
        shippingPhoneNumber = in.readString();
        shipCode = in.readString();
        shipGeography = in.readString();
        shipCityTown = in.readString();
        customerCode = in.readString();
        tempCustomerCode = in.readString();
        cmpCode = in.readString();
        distrCode = in.readString();
        modDt = in.readString();
        uploadFlag = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shippingAddress1);
        dest.writeString(shippingAddress2);
        dest.writeString(shippingAddress3);
        dest.writeString(shippingZipCode);
        dest.writeString(shippingState);
        dest.writeString(shippingLatitude);
        dest.writeString(shippingLongitude);
        dest.writeString(isDefault);
        dest.writeString(shippingGeoHierPath);
        dest.writeString(shippingPhoneNumber);
        dest.writeString(shipCode);
        dest.writeString(shipGeography);
        dest.writeString(shipCityTown);
        dest.writeString(customerCode);
        dest.writeString(tempCustomerCode);
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(modDt);
        dest.writeString(uploadFlag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressModel> CREATOR = new Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel in) {
            return new AddressModel(in);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };

    public String getTempShipCode() {
        return tempShipCode;
    }

    public void setTempShipCode(String tempShipCode) {
        this.tempShipCode = tempShipCode;
    }

    public String getShippingAddress1() {
        return shippingAddress1;
    }

    public void setShippingAddress1(String shippingAddress1) {
        this.shippingAddress1 = shippingAddress1;
    }

    public String getShippingAddress2() {
        return shippingAddress2;
    }

    public void setShippingAddress2(String shippingAddress2) {
        this.shippingAddress2 = shippingAddress2;
    }

    public String getShippingAddress3() {
        return shippingAddress3;
    }

    public void setShippingAddress3(String shippingAddress3) {
        this.shippingAddress3 = shippingAddress3;
    }

    public String getShippingZipCode() {
        return shippingZipCode;
    }

    public void setShippingZipCode(String shippingZipCode) {
        this.shippingZipCode = shippingZipCode;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getShippingLatitude() {
        return shippingLatitude;
    }

    public void setShippingLatitude(String shippingLatitude) {
        this.shippingLatitude = shippingLatitude;
    }

    public String getShippingLongitude() {
        return shippingLongitude;
    }

    public void setShippingLongitude(String shippingLongitude) {
        this.shippingLongitude = shippingLongitude;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getShippingGeoHierPath() {
        return shippingGeoHierPath;
    }

    public void setShippingGeoHierPath(String shippingGeoHierPath) {
        this.shippingGeoHierPath = shippingGeoHierPath;
    }

    public String getShippingPhoneNumber() {
        return shippingPhoneNumber;
    }

    public void setShippingPhoneNumber(String shippingPhoneNumber) {
        this.shippingPhoneNumber = shippingPhoneNumber;
    }

    public String getTempCustomerCode() {
        return tempCustomerCode;
    }

    public void setTempCustomerCode(String tempCustomerCode) {
        this.tempCustomerCode = tempCustomerCode;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }

    public String getShipGeography() {
        return shipGeography;
    }

    public void setShipGeography(String shipGeography) {
        this.shipGeography = shipGeography;
    }

    public String getShipCityTown() {
        return shipCityTown;
    }

    public void setShipCityTown(String shipCityTown) {
        this.shipCityTown = shipCityTown;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    @Override
    public String toString() {
        return "AddressModel{" +
                "shippingAddress1='" + shippingAddress1 + '\'' +
                ", shippingAddress2='" + shippingAddress2 + '\'' +
                ", shippingAddress3='" + shippingAddress3 + '\'' +
                ", shippingZipCode='" + shippingZipCode + '\'' +
                ", shippingState='" + shippingState + '\'' +
                ", shippingLatitude='" + shippingLatitude + '\'' +
                ", shippingLongitude='" + shippingLongitude + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", shippingCode='" + shipCode + '\'' +
                ", shippingGeoHierPath='" + shippingGeoHierPath + '\'' +
                '}';
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

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }
}
