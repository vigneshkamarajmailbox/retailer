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

import static com.botree.retailerssfa.service.JSONConstants.TAG_REASON_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_REASON_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_REASON_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCREEN_NAME;

public class SalesReturnVO implements Parcelable {

    public static final int SALES_RETURN_TYPE = 0;
    public static final int PURCHASE_RETURN_TYPE = 1;
    public static final Creator<SalesReturnVO> CREATOR = new Creator<SalesReturnVO>() {
        @Override
        public SalesReturnVO createFromParcel(Parcel in) {
            return new SalesReturnVO(in);
        }

        @Override
        public SalesReturnVO[] newArray(int size) {
            return new SalesReturnVO[size];
        }
    };
    private int unSalQty;
    private String hsnCode;

    private String prodShortName = "";

    @SerializedName(TAG_REASON_NAME)
    private String reasonName = "";

    private String referenceType = "";

    private String referenceNo = "";

    private double sellPrice;

    @SerializedName(TAG_REASON_CODE)
    private String reasonCode = "";

    private String unSalReasonCode = "";

    private String unSalReasonName = "";

    /* Used to add reasons to DB */
    @SerializedName(TAG_SCREEN_NAME)
    private String screenName = "";

    private String returnDate = "";

    private String salesreturnNo = "";

    private String retlrCode = "";

    private String prodCode = "";

    private String prodName = "";

    private String prodBatchCode = "";

    private int salQty;

    @SerializedName(TAG_REASON_TYPE)
    private String reasonType = "";

    private String invoiceNo = "";

    private String grnNo = "";

    private String uomCode = "";

    private String unSalUomCode = "";

    private String returnImage = "";

    private double batchDetail =0;
    private String stockType="";
    private int returnQty;
    private double returnAmt;
    private double mrp;
    private String distrCode;
    private String salesmanCode;
    private String routeCode;
    private String returnType;
    private String remarks;

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public int getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(int returnQty) {
        this.returnQty = returnQty;
    }

    public double getReturnAmt() {
        return returnAmt;
    }

    public void setReturnAmt(double returnAmt) {
        this.returnAmt = returnAmt;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getBatchDetail() {
        return batchDetail;
    }

    public void setBatchDetail(double batchDetail) {
        this.batchDetail = batchDetail;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public SalesReturnVO(Parcel in) {
        unSalQty = in.readInt();
        prodShortName = in.readString();
        reasonName = in.readString();
        referenceType = in.readString();
        referenceNo = in.readString();
        sellPrice = in.readDouble();
        reasonCode = in.readString();
        unSalReasonCode = in.readString();
        unSalReasonName = in.readString();
        screenName = in.readString();
        returnDate = in.readString();
        salesreturnNo = in.readString();
        retlrCode = in.readString();
        prodCode = in.readString();
        prodName = in.readString();
        prodBatchCode = in.readString();
        salQty = in.readInt();
        reasonType = in.readString();
        invoiceNo = in.readString();
        grnNo = in.readString();
        uomCode = in.readString();
        unSalUomCode = in.readString();
        returnImage = in.readString();
    }

    public SalesReturnVO() {

    }

    public String getReturnImage() {
        return returnImage;
    }

    public void setReturnImage(String returnImage) {
        this.returnImage = returnImage;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getGrnNo() {
        return grnNo;
    }

    public void setGrnNo(String grnNo) {
        this.grnNo = grnNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public String getProdBatchCode() {
        return prodBatchCode;
    }

    public void setProdBatchCode(String prodBatchCode) {
        this.prodBatchCode = prodBatchCode;
    }

    public String getSalesreturnNo() {
        return salesreturnNo;
    }

    public void setSalesreturnNo(String salesreturnNo) {
        this.salesreturnNo = salesreturnNo;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the retlrCode
     */
    public String getRetlrCode() {
        return retlrCode;
    }

    /**
     * @param retlrCode the retlrCode to set
     */
    public void setRetlrCode(String retlrCode) {
        this.retlrCode = retlrCode;
    }

    /**
     * @return the prodCode
     */
    public String getProdCode() {
        return prodCode;
    }

    /**
     * @param prodCode the prodCode to set
     */
    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    /**
     * @return the salQty
     */
    public int getSalQty() {
        return salQty;
    }

    /**
     * @param salQty the salQty to set
     */
    public void setSalQty(int salQty) {
        this.salQty = salQty;
    }

    /**
     * @return the unSalQty
     */
    public int getUnSalQty() {
        return unSalQty;
    }

    /**
     * @param unSalQty the unSalQty to set
     */
    public void setUnSalQty(int unSalQty) {
        this.unSalQty = unSalQty;
    }

    /**
     * @return the prodShortName
     */
    public String getProdShortName() {
        return prodShortName;
    }

    /**
     * @param prodShortName the prodShortName to set
     */
    public void setProdShortName(String prodShortName) {
        this.prodShortName = prodShortName;
    }

    /**
     * @return the reasonName
     */
    public String getReasonName() {
        return reasonName;
    }

    /**
     * @param reasonName the reasonName to set
     */
    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    /**
     * @return the sellPrice
     */
    public double getSellPrice() {
        return sellPrice;
    }

    /**
     * @param sellPrice the sellPrice to set
     */
    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * @return the reasonCode
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * @param reasonCode the reasonCode to set
     */
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getUnSalReasonName() {
        return unSalReasonName;
    }

    public void setUnSalReasonName(String unSalReasonName) {
        this.unSalReasonName = unSalReasonName;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getUnSalReasonCode() {
        return unSalReasonCode;
    }

    public void setUnSalReasonCode(String unSalReasonCode) {
        this.unSalReasonCode = unSalReasonCode;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getUnSalUomCode() {
        return unSalUomCode;
    }

    public void setUnSalUomCode(String unSalUomCode) {
        this.unSalUomCode = unSalUomCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(unSalQty);
        dest.writeString(prodShortName);
        dest.writeString(reasonName);
        dest.writeString(referenceType);
        dest.writeString(referenceNo);
        dest.writeDouble(sellPrice);
        dest.writeString(reasonCode);
        dest.writeString(unSalReasonCode);
        dest.writeString(unSalReasonName);
        dest.writeString(screenName);
        dest.writeString(returnDate);
        dest.writeString(salesreturnNo);
        dest.writeString(retlrCode);
        dest.writeString(prodCode);
        dest.writeString(prodName);
        dest.writeString(prodBatchCode);
        dest.writeInt(salQty);
        dest.writeString(reasonType);
        dest.writeString(invoiceNo);
        dest.writeString(grnNo);
        dest.writeString(uomCode);
        dest.writeString(unSalUomCode);
        dest.writeString(returnImage);
    }
}
