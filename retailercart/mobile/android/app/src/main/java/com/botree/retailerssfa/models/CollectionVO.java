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

import static com.botree.retailerssfa.service.JSONConstants.TAG_BANK_BRANCH_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_BANK_BRANCH_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_BANK_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_RTR_BANK_NAME;

public class CollectionVO implements Parcelable {


    public static final Creator<CollectionVO> CREATOR = new Creator<CollectionVO>() {
        @Override
        public CollectionVO createFromParcel(Parcel in) {
            return new CollectionVO(in);
        }

        @Override
        public CollectionVO[] newArray(int size) {
            return new CollectionVO[size];
        }
    };
    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";

    @SerializedName(TAG_DISTR_CODE)
    private String distrCode = "";

    private String salesmanCode = "";

    @SerializedName(TAG_RTR_BANK_NAME)
    private String bankName = "";

    @SerializedName(TAG_BANK_CODE)
    private String bankCode = "";

    @SerializedName(TAG_BANK_BRANCH_CODE)
    private String bankBranchCode = "";

    @SerializedName(TAG_BANK_BRANCH_NAME)
    private String bankBranchName = "";
    private String cheque = "";
    private String date = "";
    private int amount = 0;
    private String mode = "";
    /* Used for banknames Insertion */
    private Integer distDefaultBank = 0;
    private String bankIdBranchId = "";
    /* Used for day summary */
    private String routeName = "";
    private String routeCode = "";
    private String retailerName = "";
    private String retailerCode = "";
    private String collectionDate = "";
    private String collectionNo = "";
    private String billAdjAmt="0";
    private String crDbAdjAmt="0";
    private String cancelledStatus="N";
    private String distrBankName="";
    private String distrBankCode="";
    private String distrBankBrCode="";
    private String collectionType="D";
    private String remarks="";

    public CollectionVO(Parcel in) {
        cmpCode = in.readString();
        bankName = in.readString();
        bankCode = in.readString();
        bankBranchCode = in.readString();
        bankBranchName = in.readString();
        cheque = in.readString();
        date = in.readString();
        amount = in.readInt();
        mode = in.readString();
        distrCode = in.readString();
        salesmanCode = in.readString();
        if (in.readByte() == 0) {
            distDefaultBank = null;
        } else {
            distDefaultBank = in.readInt();
        }
        bankIdBranchId = in.readString();
        routeName = in.readString();
        routeCode = in.readString();
        retailerName = in.readString();
        retailerCode = in.readString();
        collectionDate = in.readString();
        collectionNo = in.readString();
    }

    public CollectionVO() {

    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getCollectionNo() {
        return collectionNo;
    }

    public void setCollectionNo(String collectionNo) {
        this.collectionNo = collectionNo;
    }


    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }


    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return the cheque
     */
    public String getCheque() {
        return cheque;
    }

    /**
     * @param cheque the cheque to set
     */
    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return the routeName
     */
    public String getRouteName() {
        return routeName;
    }

    /**
     * @param routeName the routeName to set
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    /**
     * @return the routeCode
     */
    public String getRouteCode() {
        return routeCode;
    }

    /**
     * @param routeCode the routeCode to set
     */
    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    /**
     * @return the retailerName
     */
    public String getRetailerName() {
        return retailerName;
    }

    /**
     * @param retailerName the retailerName to set
     */
    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    /**
     * @return the retailerCode
     */
    public String getRetailerCode() {
        return retailerCode;
    }

    /**
     * @param retailerCode the retailerCode to set
     */
    public void setRetailerCode(String retailerCode) {
        this.retailerCode = retailerCode;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public Integer getDistDefaultBank() {
        return distDefaultBank;
    }

    public void setDistDefaultBank(Integer distDefaultBank) {
        this.distDefaultBank = distDefaultBank;
    }

    public String getBankIdBranchId() {
        return bankIdBranchId;
    }

    public void setBankIdBranchId(String bankIdBranchId) {
        this.bankIdBranchId = bankIdBranchId;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(bankName);
        dest.writeString(bankCode);
        dest.writeString(bankBranchCode);
        dest.writeString(bankBranchName);
        dest.writeString(cheque);
        dest.writeString(date);
        dest.writeInt(amount);
        dest.writeString(mode);
        dest.writeString(distrCode);
        dest.writeString(salesmanCode);
        if (distDefaultBank == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(distDefaultBank);
        }
        dest.writeString(bankIdBranchId);
        dest.writeString(routeName);
        dest.writeString(routeCode);
        dest.writeString(retailerName);
        dest.writeString(retailerCode);
        dest.writeString(collectionDate);
        dest.writeString(collectionNo);
    }

    @Override
    public String toString() {
        return "CollectionVO{" +
                "cmpCode='" + cmpCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankBranchCode='" + bankBranchCode + '\'' +
                ", bankBranchName='" + bankBranchName + '\'' +
                ", cheque='" + cheque + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", mode='" + mode + '\'' +
                ", distrCode='" + distrCode + '\'' +
                ", salesmanCode='" + salesmanCode + '\'' +
                ", distDefaultBank=" + distDefaultBank +
                ", bankIdBranchId='" + bankIdBranchId + '\'' +
                ", routeName='" + routeName + '\'' +
                ", routeCode='" + routeCode + '\'' +
                ", retailerName='" + retailerName + '\'' +
                ", retailerCode='" + retailerCode + '\'' +
                ", collectionDate='" + collectionDate + '\'' +
                ", collectionNo='" + collectionNo + '\'' +
                '}';
    }

    public String getBillAdjAmt() {
        return billAdjAmt;
    }

    public void setBillAdjAmt(String billAdjAmt) {
        this.billAdjAmt = billAdjAmt;
    }

    public String getCrDbAdjAmt() {
        return crDbAdjAmt;
    }

    public void setCrDbAdjAmt(String crDbAdjAmt) {
        this.crDbAdjAmt = crDbAdjAmt;
    }

    public String getCancelledStatus() {
        return cancelledStatus;
    }

    public void setCancelledStatus(String cancelledStatus) {
        this.cancelledStatus = cancelledStatus;
    }

    public String getDistrBankName() {
        return distrBankName;
    }

    public void setDistrBankName(String distrBankName) {
        this.distrBankName = distrBankName;
    }

    public String getDistrBankCode() {
        return distrBankCode;
    }

    public void setDistrBankCode(String distrBankCode) {
        this.distrBankCode = distrBankCode;
    }

    public String getDistrBankBrCode() {
        return distrBankBrCode;
    }

    public void setDistrBankBrCode(String distrBankBrCode) {
        this.distrBankBrCode = distrBankBrCode;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
