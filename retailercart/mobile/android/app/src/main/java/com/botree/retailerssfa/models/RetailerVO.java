/*
 * Copyright (C) 2016 Botree Software International Private Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.botree.retailerssfa.service.JSONConstants.TAG_ADDRESS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_APPROVAL_STATUS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CASH_DISC_PERC;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CHANNEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CHANNEL_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CITY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CLASS_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CLASS_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_COMPANY_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CONTACT_PERSON;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CREDIT_BILLS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CREDIT_DAYS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CREDIT_LIMIT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_ACC_NUM;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_ACC_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_BANK_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_BANK_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_BRANCH_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_IFSC_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DAYOFF;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DRUG_EXPIRY_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DRUG_LIC_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_EMAIL;
import static com.botree.retailerssfa.service.JSONConstants.TAG_FSSAI_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GROUP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GROUP_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GSTNO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LATITUDE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LONGITUDE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MOBILE_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MOD_DT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PANNO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PARENT_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PHONE_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PINCODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_RETAILER_STATUS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ROUTE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ROUTE_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SHARED_FLAG;
import static com.botree.retailerssfa.service.JSONConstants.TAG_STATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_STORE_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUB_CHANNEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUB_CHANNEL_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UPLOAD_FLAG;

public class RetailerVO extends SalesmanModel implements Parcelable {

    public static final Creator<RetailerVO> CREATOR = new Creator<RetailerVO>() {
        @Override
        public RetailerVO createFromParcel(Parcel in) {
            return new RetailerVO(in);
        }

        @Override
        public RetailerVO[] newArray(int size) {
            return new RetailerVO[size];
        }
    };

    @SerializedName(TAG_CMP_CODE)
    private transient String cmpCode;

    @SerializedName(TAG_DISTR_CODE)
    private transient String distrCode = "";
    OrderBookingVO orderBookingVO = new OrderBookingVO();

    @SerializedName(TAG_ROUTE_CODE)
    private String routeCode = "";
    private String customerShipCode = "";

    @SerializedName(TAG_ROUTE_NAME)
    private String routeName = "";

    @SerializedName(TAG_CUSTOMER_CODE)
    private String customerCode = "";
    private String customerRefNo = "";

    @SerializedName(TAG_CUSTOMER_NAME)
    private String customerName = "";
    private int spinChannelCount = 0;
    private int spinSubChannelCount = 0;
    private int spinGroupCount = 0;
    private int spinClassCount = 0;
    private int spinSubStockistCount = 0;
    private int spinRoutePosition = 0;
    private int spinStatePosition = 0;
    private String tempCustomerCode = "";

    @SerializedName(TAG_ADDRESS)
    private String retailerAddr1 = "";

    @SerializedName(TAG_CHANNEL_CODE)
    private String channelCode = "";

    @SerializedName(TAG_SUB_CHANNEL_CODE)
    private String subChannelCode = "";

    @SerializedName(TAG_GROUP_CODE)
    private String groupCode = "";

    @SerializedName(TAG_CLASS_CODE)
    private String classCode = "";

    private String orderNo = "";
    private String orderDt = "";
    private String salesReturnNo = "";
    private String salesReturnDt = "";
    private Double monthSales = 0.0;
    private String isVisit = "";
    private String menuReason = "";

    @SerializedName(TAG_LATITUDE)
    private String latitude = "0.0";

    @SerializedName(TAG_LONGITUDE)
    private String longitude = "0.0";

    @SerializedName(TAG_MOBILE_NO)
    private String mobile = "";

    private String seqNo = "";

    @SerializedName(TAG_CITY)
    private String retailerAddr2 = "";

    @SerializedName(TAG_STATE)
    private String retailerAddr3 = "";

    @SerializedName(TAG_PHONE_NO)
    private String phoneNo = "";

    private String tinNo = "";
    private String photo = "";

    @SerializedName(TAG_CONTACT_PERSON)
    private String contactPerson = "";

    @SerializedName(TAG_PINCODE)
    private String postalCode = "";
    private String signature = "";

    @SerializedName(TAG_GSTNO)
    private String gstNo = "";

    @SerializedName(TAG_PANNO)
    private String panNo = "";

    @SerializedName(TAG_CUSTOMER_TYPE)
    private String gstType = "";
    private String gstStateCode = "";
    /* Used for retailerVisitList */
    private String orderVisit = "";
    private String returnVisit = "";
    private String stockVisit = "";
    private String collectionVisit = "";
    private String surveyVisit = "";
    private String syncVisit = "";
    private String billVisit = "";
    /* Used for customerUploadStatusList */
    private String orderUploaded = "";
    private String returnsUploaded = "";
    private String stockCaptureUploaded = "";
    private String collectionsUploaded = "";
    private String surveyUploaded = "";
    private String billUploaded = "";
    /* Used for salesmanTrackerList */
    private Long startTime;
    private Long endTime;
    private int barColor = 0;
    private Date date;
    private boolean isTodayBeat = false;


    private String todayBeat = "";
    private String isNewRetailer = "";
    private String coverageDate = "";
    private String returnType = "";
    private String invoiceNo = "";
    private String retrLatitude = "0.0";
    private String retrLongitude = "0.0";
    private String distance = "";

    @SerializedName(TAG_CHANNEL_NAME)
    private String channelName = "";

    @SerializedName(TAG_SUB_CHANNEL_NAME)
    private String subChannelName = "";

    @SerializedName(TAG_GROUP_NAME)
    private String groupName = "";

    @SerializedName(TAG_CLASS_NAME)
    private String className = "";
    private String isSingleReason = "";
    private String unLockCode = "";
    private String otpVerified = "";
    private String licenseExpDate = "";

    @SerializedName(TAG_COMPANY_CUSTOMER_CODE)
    private String cmpCustomerCode = "";

    @SerializedName(TAG_SHARED_FLAG)
    private String sharedFlag = "N";

    private String isNewOutlet = "N";

    @SerializedName(TAG_EMAIL)
    private String email = "";
    private String geography = "";

    @SerializedName(TAG_DRUG_LIC_NO)
    private String drugLcNo = "";

    private String expiryDate = "";
    @SerializedName(TAG_CREDIT_BILLS)
    private String crBills = "";
    @SerializedName(TAG_CREDIT_DAYS)
    private String crDays = "";
    @SerializedName(TAG_CREDIT_LIMIT)
    private String crLimit = "";
    @SerializedName(TAG_CASH_DISC_PERC)
    private String cashDisc = "";

    @SerializedName(TAG_FSSAI_NO)
    private String ffsaLcNo = "";

    private List<RouteModel> temprouteList = new ArrayList<>();

    @SerializedName(TAG_STORE_TYPE)
    private String relationStatus = "";
    @SerializedName(TAG_PARENT_CUSTOMER_CODE)
    private String parentMap = "";

    private int spinRelationCount = 0;
    private int spinParentMapCount = 0;

    private String cityTown = "";
    private int spinCityCount = 0;

    private boolean isSwitch = false;

    private List<AddressModel> shippingAddressList = new ArrayList<>();

    private String dayOffValue = "";

    @SerializedName(TAG_DAYOFF)
    private int spinDayOffCount = 0;

    @SerializedName(TAG_CUSTOMER_BANK_CODE)
    private String bankID = "";
    @SerializedName(TAG_CUSTOMER_BANK_NAME)
    private String bankName = "";
    @SerializedName(TAG_CUSTOMER_BRANCH_NAME)
    private String branchName = "";
    @SerializedName(TAG_CUSTOMER_IFSC_CODE)
    private String ifscCode = "";
    @SerializedName(TAG_CUSTOMER_ACC_NUM)
    private String accountNo = "";
    @SerializedName(TAG_CUSTOMER_ACC_TYPE)
    private String accountType = "";

    @SerializedName(TAG_RETAILER_STATUS)
    private String retailerStatus = "Y";

    @SerializedName(TAG_APPROVAL_STATUS)
    private String approvalStatus = "P";
    @SerializedName(TAG_DRUG_EXPIRY_DATE)
    private String expiryDateTimeStamp = "";



    @SerializedName(TAG_MOD_DT)
    private String modDt = "";

    @SerializedName(TAG_UPLOAD_FLAG)
    private String uploadFlag = "";

    private int routeCount=0;
    private int salesmanCount=0;
    private String customerData="";
    private boolean isEnable = false;

    protected RetailerVO(Parcel in) {
        routeCode = in.readString();
        routeName = in.readString();
        customerCode = in.readString();
        customerName = in.readString();
        retailerAddr1 = in.readString();
        channelCode = in.readString();
        subChannelCode = in.readString();
        groupCode = in.readString();
        classCode = in.readString();
        isVisit = in.readString();
        menuReason = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        mobile = in.readString();
        seqNo = in.readString();
        retailerAddr2 = in.readString();
        retailerAddr3 = in.readString();
        phoneNo = in.readString();
        tinNo = in.readString();
        photo = in.readString();
        contactPerson = in.readString();
        postalCode = in.readString();
        signature = in.readString();
        gstNo = in.readString();
        panNo = in.readString();
        gstType = in.readString();
        gstStateCode = in.readString();
        orderVisit = in.readString();
        returnVisit = in.readString();
        stockVisit = in.readString();
        collectionVisit = in.readString();
        surveyVisit = in.readString();
        syncVisit = in.readString();
        billVisit = in.readString();
        orderUploaded = in.readString();
        returnsUploaded = in.readString();
        stockCaptureUploaded = in.readString();
        collectionsUploaded = in.readString();
        surveyUploaded = in.readString();
        billUploaded = in.readString();
        coverageDate = in.readString();
        barColor = in.readInt();
        todayBeat = in.readString();
        invoiceNo = in.readString();
        returnType = in.readString();
        channelName = in.readString();
        subChannelName = in.readString();
        groupName = in.readString();
        className = in.readString();
        isSingleReason = in.readString();
        isTodayBeat = in.readByte() != 0;

        spinChannelCount = in.readInt();
        spinSubChannelCount = in.readInt();
        spinGroupCount = in.readInt();
        spinClassCount = in.readInt();
        spinSubStockistCount = in.readInt();
        spinRoutePosition = in.readInt();
        spinStatePosition = in.readInt();

        spinRelationCount = in.readInt();
        spinParentMapCount = in.readInt();

        spinCityCount = in.readInt();
        spinDayOffCount = in.readInt();
        cmpCode = in.readString();
    }

    public RetailerVO(int pastelColor) {
        this.barColor = pastelColor;
    }


    public RetailerVO() {

    }

    public String getTempCustomerCode() {
        return tempCustomerCode;
    }

    public void setTempCustomerCode(String tempCustomerCode) {
        this.tempCustomerCode = tempCustomerCode;
    }

    public String getOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(String otpVerified) {
        this.otpVerified = otpVerified;
    }

    public int getSpinChannelCount() {
        return spinChannelCount;
    }

    public void setSpinChannelCount(int spinChannelCount) {
        this.spinChannelCount = spinChannelCount;
    }

    public int getSpinSubChannelCount() {
        return spinSubChannelCount;
    }

    public void setSpinSubChannelCount(int spinSubChannelCount) {
        this.spinSubChannelCount = spinSubChannelCount;
    }

    public int getSpinGroupCount() {
        return spinGroupCount;
    }

    public void setSpinGroupCount(int spinGroupCount) {
        this.spinGroupCount = spinGroupCount;
    }

    public int getSpinClassCount() {
        return spinClassCount;
    }

    public void setSpinClassCount(int spinClassCount) {
        this.spinClassCount = spinClassCount;
    }

    public int getSpinSubStockistCount() {
        return spinSubStockistCount;
    }

    public void setSpinSubStockistCount(int spinSubStockistCount) {
        this.spinSubStockistCount = spinSubStockistCount;
    }

    public int getSpinRoutePosition() {
        return spinRoutePosition;
    }

    public void setSpinRoutePosition(int spinRoutePosition) {
        this.spinRoutePosition = spinRoutePosition;
    }

    public int getSpinStatePosition() {
        return spinStatePosition;
    }

    public void setSpinStatePosition(int spinStatePosition) {
        this.spinStatePosition = spinStatePosition;
    }

    public String getIsSingleReason() {
        return isSingleReason;
    }

    public void setIsSingleReason(String isSingleReason) {
        this.isSingleReason = isSingleReason;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getSubChannelName() {
        return subChannelName;
    }

    public void setSubChannelName(String subChannelName) {
        this.subChannelName = subChannelName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRetrLatitude() {
        return retrLatitude;
    }

    public void setRetrLatitude(String retrLatitude) {
        this.retrLatitude = retrLatitude;
    }

    public String getRetrLongitude() {
        return retrLongitude;
    }

    public void setRetrLongitude(String retrLongitude) {
        this.retrLongitude = retrLongitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getCoverageDate() {
        return coverageDate;
    }

    public void setCoverageDate(String coverageDate) {
        this.coverageDate = coverageDate;
    }

    public String getTodayBeat() {
        return todayBeat;
    }

    public void setTodayBeat(String todayBeat) {
        this.todayBeat = todayBeat;
    }

    public String getGstStateCode() {
        return gstStateCode;
    }

    public void setGstStateCode(String gstStateCode) {
        this.gstStateCode = gstStateCode;
    }

    public String getBillVisit() {
        return billVisit;
    }

    public void setBillVisit(String billVisit) {
        this.billVisit = billVisit;
    }

    public String getBillUploaded() {
        return billUploaded;
    }

    public void setBillUploaded(String billUploaded) {
        this.billUploaded = billUploaded;
    }

    public int getBarColor() {
        return barColor;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    public String getGstType() {
        return gstType;
    }

    public void setGstType(String gstType) {
        this.gstType = gstType;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTinNo() {
        return tinNo;
    }

    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRetailerAddr2() {
        return retailerAddr2;
    }

    public void setRetailerAddr2(String retailerAddr2) {
        this.retailerAddr2 = retailerAddr2;
    }

    public String getRetailerAddr3() {
        return retailerAddr3;
    }

    public void setRetailerAddr3(String retailerAddr3) {
        this.retailerAddr3 = retailerAddr3;
    }

    public boolean isTodayBeat() {
        return isTodayBeat;
    }

    public void setTodayBeat(boolean todayBeat) {
        isTodayBeat = todayBeat;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getOrderVisit() {
        return orderVisit;
    }

    public void setOrderVisit(String orderVisit) {
        this.orderVisit = orderVisit;
    }

    public String getReturnVisit() {
        return returnVisit;
    }

    public void setReturnVisit(String returnVisit) {
        this.returnVisit = returnVisit;
    }

    public String getStockVisit() {
        return stockVisit;
    }

    public void setStockVisit(String stockVisit) {
        this.stockVisit = stockVisit;
    }

    public String getCollectionVisit() {
        return collectionVisit;
    }

    public void setCollectionVisit(String collectionVisit) {
        this.collectionVisit = collectionVisit;
    }

    public String getSurveyVisit() {
        return surveyVisit;
    }

    public void setSurveyVisit(String surveyVisit) {
        this.surveyVisit = surveyVisit;
    }

    public String getSyncVisit() {
        return syncVisit;
    }

    public void setSyncVisit(String syncVisit) {
        this.syncVisit = syncVisit;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getCustomerShipCode() {
        return customerShipCode;
    }

    public void setCustomerShipCode(String customerShipCode) {
        this.customerShipCode = customerShipCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerRefNo() {
        return customerRefNo;
    }

    public void setCustomerRefNo(String customerRefNo) {
        this.customerRefNo = customerRefNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRetailerAddr1() {
        return retailerAddr1;
    }

    public void setRetailerAddr1(String retailerAddr1) {
        this.retailerAddr1 = retailerAddr1;
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

    public Double getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(Double monthSales) {
        this.monthSales = monthSales;
    }


    public String getIsVisit() {
        return isVisit;
    }

    public void setIsVisit(String isVisit) {
        this.isVisit = isVisit;
    }

    public String getMenuReason() {
        return menuReason;
    }

    public void setMenuReason(String menuReason) {
        this.menuReason = menuReason;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderUploaded() {
        return orderUploaded;
    }

    public void setOrderUploaded(String orderUploaded) {
        this.orderUploaded = orderUploaded;
    }

    public String getReturnsUploaded() {
        return returnsUploaded;
    }

    public void setReturnsUploaded(String returnsUploaded) {
        this.returnsUploaded = returnsUploaded;
    }

    public String getStockCaptureUploaded() {
        return stockCaptureUploaded;
    }

    public void setStockCaptureUploaded(String stockCaptureUploaded) {
        this.stockCaptureUploaded = stockCaptureUploaded;
    }

    public String getCollectionsUploaded() {
        return collectionsUploaded;
    }

    public void setCollectionsUploaded(String collectionsUploaded) {
        this.collectionsUploaded = collectionsUploaded;
    }

    public String getSurveyUploaded() {
        return surveyUploaded;
    }

    public void setSurveyUploaded(String surveyUploaded) {
        this.surveyUploaded = surveyUploaded;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(routeCode);
        parcel.writeString(routeName);
        parcel.writeString(customerCode);
        parcel.writeString(customerName);
        parcel.writeString(retailerAddr1);
        parcel.writeString(channelCode);
        parcel.writeString(subChannelCode);
        parcel.writeString(groupCode);
        parcel.writeString(classCode);
        parcel.writeString(isVisit);
        parcel.writeString(menuReason);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(mobile);
        parcel.writeString(seqNo);
        parcel.writeString(retailerAddr2);
        parcel.writeString(retailerAddr3);
        parcel.writeString(phoneNo);
        parcel.writeString(tinNo);
        parcel.writeString(photo);
        parcel.writeString(contactPerson);
        parcel.writeString(postalCode);
        parcel.writeString(signature);
        parcel.writeString(gstNo);
        parcel.writeString(gstStateCode);
        parcel.writeString(panNo);
        parcel.writeString(gstType);
        parcel.writeString(orderVisit);
        parcel.writeString(returnVisit);
        parcel.writeString(stockVisit);
        parcel.writeString(collectionVisit);
        parcel.writeString(surveyVisit);
        parcel.writeString(syncVisit);
        parcel.writeString(billVisit);
        parcel.writeString(orderUploaded);
        parcel.writeString(returnsUploaded);
        parcel.writeString(stockCaptureUploaded);
        parcel.writeString(collectionsUploaded);
        parcel.writeString(surveyUploaded);
        parcel.writeString(billUploaded);
        parcel.writeString(coverageDate);
        parcel.writeInt(barColor);
        parcel.writeString(todayBeat);
        parcel.writeString(invoiceNo);
        parcel.writeString(returnType);
        parcel.writeString(channelName);
        parcel.writeString(subChannelName);
        parcel.writeString(groupName);
        parcel.writeString(className);
        parcel.writeString(isSingleReason);
        parcel.writeByte((byte) (isTodayBeat ? 1 : 0));

        parcel.writeInt(spinChannelCount);
        parcel.writeInt(spinSubChannelCount);
        parcel.writeInt(spinGroupCount);
        parcel.writeInt(spinClassCount);
        parcel.writeInt(spinSubStockistCount);
        parcel.writeInt(spinRoutePosition);
        parcel.writeInt(spinStatePosition);
        parcel.writeInt(spinCityCount);

        parcel.writeString(cmpCode);
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDt() {
        return orderDt;
    }

    public void setOrderDt(String orderDt) {
        this.orderDt = orderDt;
    }

    public String getSalesReturnNo() {
        return salesReturnNo;
    }

    public void setSalesReturnNo(String salesReturnNo) {
        this.salesReturnNo = salesReturnNo;
    }

    public String getSalesReturnDt() {
        return salesReturnDt;
    }

    public void setSalesReturnDt(String salesReturnDt) {
        this.salesReturnDt = salesReturnDt;
    }


    public String getIsNewRetailer() {
        return isNewRetailer;
    }

    public void setIsNewRetailer(String isNewRetailer) {
        this.isNewRetailer = isNewRetailer;
    }

    public String getUnLockCode() {
        return unLockCode;
    }

    public void setUnLockCode(String unLockCode) {
        this.unLockCode = unLockCode;
    }

    public String getLicenseExpDate() {
        return licenseExpDate;
    }

    public void setLicenseExpDate(String licenseExpDate) {
        this.licenseExpDate = licenseExpDate;
    }

    public String getCmpCustomerCode() {
        return cmpCustomerCode;
    }

    public void setCmpCustomerCode(String cmpCustomerCode) {
        this.cmpCustomerCode = cmpCustomerCode;
    }

    public String getSharedFlag() {
        return sharedFlag;
    }

    public void setSharedFlag(String sharedFlag) {
        this.sharedFlag = sharedFlag;
    }

    public String getIsNewOutlet() {
        return isNewOutlet;
    }

    public void setIsNewOutlet(String isNewOutlet) {
        this.isNewOutlet = isNewOutlet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeography() {
        return geography;
    }

    public void setGeography(String geography) {
        this.geography = geography;
    }

    public String getDrugLcNo() {
        return drugLcNo;
    }

    public void setDrugLcNo(String drugLcNo) {
        this.drugLcNo = drugLcNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCrBills() {
        return crBills;
    }

    public void setCrBills(String crBills) {
        this.crBills = crBills;
    }

    public String getCrDays() {
        return crDays;
    }

    public void setCrDays(String crDays) {
        this.crDays = crDays;
    }

    public String getCrLimit() {
        return crLimit;
    }

    public void setCrLimit(String crLimit) {
        this.crLimit = crLimit;
    }

    public String getCashDisc() {
        return cashDisc;
    }

    public void setCashDisc(String cashDisc) {
        this.cashDisc = cashDisc;
    }

    public List<RouteModel> getTemprouteList() {
        return temprouteList;
    }

    public void setTemprouteList(List<RouteModel> temprouteList) {
        this.temprouteList = temprouteList;
    }

    public String getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(String relationStatus) {
        this.relationStatus = relationStatus;
    }

    public String getParentMap() {
        return parentMap;
    }

    public void setParentMap(String parentMap) {
        this.parentMap = parentMap;
    }

    public int getSpinRelationCount() {
        return spinRelationCount;
    }

    public void setSpinRelationCount(int spinRelationCount) {
        this.spinRelationCount = spinRelationCount;
    }

    public int getSpinParentMapCount() {
        return spinParentMapCount;
    }

    public void setSpinParentMapCount(int spinParentMapCount) {
        this.spinParentMapCount = spinParentMapCount;
    }

    public String getCityTown() {
        return cityTown;
    }

    public void setCityTown(String cityTown) {
        this.cityTown = cityTown;
    }

    public int getSpinCityCount() {
        return spinCityCount;
    }

    public void setSpinCityCount(int spinCityCount) {
        this.spinCityCount = spinCityCount;
    }

    public boolean isSwitch() {
        return isSwitch;
    }

    public void setSwitch(boolean aSwitch) {
        isSwitch = aSwitch;
    }

    public List<AddressModel> getShippingAddressList() {
        return shippingAddressList;
    }

    public void setShippingAddressList(List<AddressModel> shippingAddressList) {
        this.shippingAddressList = shippingAddressList;
    }

    public String getFfsaLcNo() {
        return ffsaLcNo;
    }

    public void setFfsaLcNo(String ffsaLcNo) {
        this.ffsaLcNo = ffsaLcNo;
    }

    public String getDayOffValue() {
        return dayOffValue;
    }

    public void setDayOffValue(String dayOffValue) {
        this.dayOffValue = dayOffValue;
    }

    public int getSpinDayOffCount() {
        return spinDayOffCount;
    }

    public void setSpinDayOffCount(int spinDayOffCount) {
        this.spinDayOffCount = spinDayOffCount;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getRetailerStatus() {
        return retailerStatus;
    }

    public void setRetailerStatus(String retailerStatus) {
        this.retailerStatus = retailerStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getExpiryDateTimeStamp() {
        return expiryDateTimeStamp;
    }

    public void setExpiryDateTimeStamp(String expiryDateTimeStamp) {
        this.expiryDateTimeStamp = expiryDateTimeStamp;
    }

    @Override
    public String getCmpCode() {
        return cmpCode;
    }

    @Override
    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    @Override
    public String getDistrCode() {
        return distrCode;
    }

    @Override
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

    public int getRouteCount() {
        return routeCount;
    }

    public void setRouteCount(int routeCount) {
        this.routeCount = routeCount;
    }

    public int getSalesmanCount() {
        return salesmanCount;
    }

    public void setSalesmanCount(int salesmanCount) {
        this.salesmanCount = salesmanCount;
    }

    public String getCustomerData() {
        return customerData;
    }

    public void setCustomerData(String customerData) {
        this.customerData = customerData;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public OrderBookingVO getOrderBookingVO() {
        return orderBookingVO;
    }

    public void setOrderBookingVO(OrderBookingVO orderBookingVO) {
        orderBookingVO = orderBookingVO;
    }
}
