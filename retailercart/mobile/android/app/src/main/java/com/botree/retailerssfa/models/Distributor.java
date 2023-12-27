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

import static com.botree.retailerssfa.service.JSONConstants.TAG_AADHAR_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_BRANCH_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_BRANCH_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CONTACT_PERSON;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DAY_OFF;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_ADDRESS1;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_ADDRESS2;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_ADDRESS3;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_STATUS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_TYPE_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DRUG_LICENCE_EXPIRY1;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DRUG_LICENCE_NO1;
import static com.botree.retailerssfa.service.JSONConstants.TAG_FSSAI_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GEO_HIER_PATH;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GSTIN_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GST_DISTR_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GST_STATE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MAIL_ID;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MOBILE_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PAN_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PDA_DISTRIBUTOR;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PHONE_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PINCODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UPLOAD;

public class Distributor implements Parcelable {

    public static final Creator<Distributor> CREATOR = new Creator<Distributor>() {
        @Override
        public Distributor createFromParcel(Parcel in) {
            return new Distributor(in);
        }

        @Override
        public Distributor[] newArray(int size) {
            return new Distributor[size];
        }
    };

    @SerializedName(TAG_CMP_CODE)
    private String companyCode;
    @SerializedName(TAG_DISTR_CODE)
    private String distributorCode;
    @SerializedName(TAG_DISTR_NAME)
    private String distributorName;
    @SerializedName(TAG_DISTR_ADDRESS1)
    private String distributorAddress1;
    @SerializedName(TAG_DISTR_ADDRESS2)
    private String distributorAddress2;
    @SerializedName(TAG_DISTR_ADDRESS3)
    private String distributorAddress3;
    @SerializedName(TAG_PINCODE)
    private String pincode;
    @SerializedName(TAG_PHONE_NO)
    private String phoneNumber;
    @SerializedName(TAG_MOBILE_NO)
    private String mobileNumber;
    @SerializedName(TAG_CONTACT_PERSON)
    private String contactPerson;
    @SerializedName(TAG_MAIL_ID)
    private String mailId;
    @SerializedName(TAG_BRANCH_CODE)
    private String branchCode;
    @SerializedName(TAG_BRANCH_NAME)
    private String branchName;
    @SerializedName(TAG_GST_STATE_CODE)
    private String gstStateCode;
    @SerializedName(TAG_GEO_HIER_PATH)
    private String geoHierPath;
    private String geoStateCode;
    @SerializedName(TAG_DRUG_LICENCE_NO1)
    private String drugLicenceNumber1;
    @SerializedName(TAG_DRUG_LICENCE_EXPIRY1)
    private String drugLicenceExpiryDate1;
    @SerializedName(TAG_DAY_OFF)
    private int dayOff;
    @SerializedName(TAG_FSSAI_NO)
    private String fssaiNumber;
    private String fssaiExpiryDate;
    @SerializedName(TAG_DISTR_TYPE_NAME)
    private String distributorTypeName;
    @SerializedName(TAG_GST_DISTR_TYPE)
    private String gstDistributorType;
    @SerializedName(TAG_PDA_DISTRIBUTOR)
    private String pdaDistributor;
    @SerializedName(TAG_DISTR_STATUS)
    private String distrStatus;
    @SerializedName(TAG_GSTIN_NO)
    private String gstinNumber;
    @SerializedName(TAG_PAN_NO)
    private String panNumber;
    @SerializedName(TAG_AADHAR_NO)
    private String aadharNumber;
    @SerializedName(TAG_UPLOAD)
    private String uploadFlag;
    private String prodCode;
    private int saleableQty;
    private String saleableStock;
    private String loadStockProd;

    //New Distributor Info
    private String companyName;

    public String getLoadStockProd() {
        return loadStockProd;
    }

    public void setLoadStockProd(String loadStockProd) {
        this.loadStockProd = loadStockProd;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public int getSaleableQty() {
        return saleableQty;
    }

    public void setSaleableQty(int saleableQty) {
        this.saleableQty = saleableQty;
    }

    public String getSaleableStock() {
        return saleableStock;
    }

    public void setSaleableStock(String saleableStock) {
        this.saleableStock = saleableStock;
    }

    public String getFssaiExpiryDate() {
        return fssaiExpiryDate;
    }

    public void setFssaiExpiryDate(String fssaiExpiryDate) {
        this.fssaiExpiryDate = fssaiExpiryDate;
    }

    public static Creator<Distributor> getCREATOR() {
        return CREATOR;
    }

    public String getPdaDistributor() {
        return pdaDistributor;
    }

    public void setPdaDistributor(String pdaDistributor) {
        this.pdaDistributor = pdaDistributor;
    }

    public String getDistrStatus() {
        return distrStatus;
    }

    public void setDistrStatus(String distrStatus) {
        this.distrStatus = distrStatus;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getDistributorAddress1() {
        return distributorAddress1;
    }

    public void setDistributorAddress1(String distributorAddress1) {
        this.distributorAddress1 = distributorAddress1;
    }

    public String getDistributorAddress2() {
        return distributorAddress2;
    }

    public void setDistributorAddress2(String distributorAddress2) {
        this.distributorAddress2 = distributorAddress2;
    }

    public String getDistributorAddress3() {
        return distributorAddress3;
    }

    public void setDistributorAddress3(String distributorAddress3) {
        this.distributorAddress3 = distributorAddress3;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getGstStateCode() {
        return gstStateCode;
    }

    public void setGstStateCode(String gstStateCode) {
        this.gstStateCode = gstStateCode;
    }

    public String getGeoHierPath() {
        return geoHierPath;
    }

    public void setGeoHierPath(String geoHierPath) {
        this.geoHierPath = geoHierPath;
    }

    public String getDrugLicenceNumber1() {
        return drugLicenceNumber1;
    }

    public void setDrugLicenceNumber1(String drugLicenceNumber1) {
        this.drugLicenceNumber1 = drugLicenceNumber1;
    }

    public String getDrugLicenceExpiryDate1() {
        return drugLicenceExpiryDate1;
    }

    public void setDrugLicenceExpiryDate1(String drugLicenceExpiryDate1) {
        this.drugLicenceExpiryDate1 = drugLicenceExpiryDate1;
    }

    public int getDayOff() {
        return dayOff;
    }

    public void setDayOff(int dayOff) {
        this.dayOff = dayOff;
    }

    public String getFssaiNumber() {
        return fssaiNumber;
    }

    public void setFssaiNumber(String fssaiNumber) {
        this.fssaiNumber = fssaiNumber;
    }

    public String getDistributorTypeName() {
        return distributorTypeName;
    }

    public void setDistributorTypeName(String distributorTypeName) {
        this.distributorTypeName = distributorTypeName;
    }

    public String getGstDistributorType() {
        return gstDistributorType;
    }

    public void setGstDistributorType(String gstDistributorType) {
        this.gstDistributorType = gstDistributorType;
    }

    public String getGstinNumber() {
        return gstinNumber;
    }

    public void setGstinNumber(String gstinNumber) {
        this.gstinNumber = gstinNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getGeoStateCode() {
        return geoStateCode;
    }

    public void setGeoStateCode(String geoStateCode) {
        this.geoStateCode = geoStateCode;
    }

    protected Distributor(Parcel in) {
        companyCode = in.readString();
        distributorCode = in.readString();
        distributorName = in.readString();
        distributorAddress1 = in.readString();
        distributorAddress2 = in.readString();
        distributorAddress3 = in.readString();
        pincode = in.readString();
        phoneNumber = in.readString();
        mobileNumber = in.readString();
        contactPerson = in.readString();
        mailId = in.readString();
        branchCode = in.readString();
        branchName = in.readString();
        gstStateCode = in.readString();
        geoHierPath = in.readString();
        drugLicenceNumber1 = in.readString();
        drugLicenceExpiryDate1 = in.readString();
        dayOff = in.readInt();
        fssaiNumber = in.readString();
        contactPerson = in.readString();
        distributorTypeName = in.readString();
        gstDistributorType = in.readString();
        gstinNumber = in.readString();
        panNumber = in.readString();
        aadharNumber = in.readString();
        uploadFlag = in.readString();
    }

    public Distributor() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(companyCode);
        parcel.writeString(distributorCode);
        parcel.writeString(distributorName);
        parcel.writeString(distributorAddress1);
        parcel.writeString(distributorAddress2);
        parcel.writeString(distributorAddress3);
        parcel.writeString(pincode);
        parcel.writeString(phoneNumber);
        parcel.writeString(mobileNumber);
        parcel.writeString(contactPerson);
        parcel.writeString(mailId);
        parcel.writeString(branchCode);
        parcel.writeString(branchName);
        parcel.writeString(gstStateCode);
        parcel.writeString(geoHierPath);
        parcel.writeString(drugLicenceNumber1);
        parcel.writeString(drugLicenceExpiryDate1);
        parcel.writeInt(dayOff);
        parcel.writeString(fssaiNumber);
        parcel.writeString(contactPerson);
        parcel.writeString(distributorTypeName);
        parcel.writeString(gstDistributorType);
        parcel.writeString(gstinNumber);
        parcel.writeString(panNumber);
        parcel.writeString(aadharNumber);
        parcel.writeString(uploadFlag);
    }

    //New Distributor Info

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
