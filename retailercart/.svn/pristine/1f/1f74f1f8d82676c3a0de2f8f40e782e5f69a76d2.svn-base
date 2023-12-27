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

import android.os.Build;

import java.util.Date;
import java.util.Objects;

public class PendingVO {

    private String strRetailerName = "";
    private String strMonthSale = "";
    private String strPendingBill = "";
    private String strPendingAmount = "";
    private String strCreditDays = "";
    private String strComment = "";
    private String strIsVisit = "";
    private String strOrder = "";
    private String strCollection = "";
    private String strReturn = "";
    private String strSurvey = "";
    private String strStockTake = "";
    private String strSync = "";
    private String strCustomerCode = "";
    private String strRouteCode = "";
    private boolean checked;
    private String latitude = "";
    private String longitude = "";
    private String strMobileNo = "";
    private String className = "";
    private String gstTinNo = "";
    private String panNo = "";
    private String gstType = "";

    private Double amount = 0.0;
    private String retailerAddress = "";

    private String channelCode = "";
    private String l3mSales = "";
    private String channelName = "";
    private String istodayBeat = "";
    private String isRetailerByPassGeo = "N";
    private Date date;
    private Double distance = 0.0;
    private RetailerDashboardVO retailerDashboardVO = new RetailerDashboardVO();

    private String geoUnlockCode = "";
    private Date licenseExpDate = null;
    private String otpVerified = "";

    public PendingVO() {

    }

    public PendingVO(String dateString, Date date, String strOrder, String retailerName, Double value) {
        this.strCreditDays = dateString;
        this.date = date;
        this.strRetailerName = retailerName;
        this.amount = value;
        this.strOrder = strOrder;

    }


    public PendingVO(String dateString, Date date, String strOrder, String retailerName, Double value, String status) {
        this.strCreditDays = dateString;
        this.date = date;
        this.strRetailerName = retailerName;
        this.amount = value;
        this.strOrder = strOrder;
        this.strComment = status;
    }

    public PendingVO(String date, String strOrder, String retailerName, Double value) {
        this.strCreditDays = date;
        this.strRetailerName = retailerName;
        this.amount = value;
        this.strOrder = strOrder;

    }

    public PendingVO(String date, String retailerName, String amount) {
        this.strCreditDays = date;
        this.strOrder = retailerName;
        this.amount = Double.parseDouble(amount);

    }

    public String getOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(String otpVerified) {
        this.otpVerified = otpVerified;
    }

    public String getGeoUnlockCode() {
        return geoUnlockCode;
    }

    public void setGeoUnlockCode(String geoUnlockCode) {
        this.geoUnlockCode = geoUnlockCode;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getIstodayBeat() {
        return istodayBeat;
    }

    public void setIstodayBeat(String istodayBeat) {
        this.istodayBeat = istodayBeat;
    }

    @Override
    public String toString() {
        return "PendingVO{" +
                "strRetailerName='" + strRetailerName + '\'' +
                ", strCreditDays='" + strCreditDays + '\'' +
                ", strOrder='" + strOrder + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getL3mSales() {
        return l3mSales;
    }

    public void setL3mSales(String l3mSales) {
        this.l3mSales = l3mSales;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getGstType() {
        return gstType;
    }

    public void setGstType(String gstType) {
        this.gstType = gstType;
    }

    public String getGstTinNo() {
        return gstTinNo;
    }

    public void setGstTinNo(String gstTinNo) {
        this.gstTinNo = gstTinNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRetailerAddress() {
        return retailerAddress;
    }

    public void setRetailerAddress(String retailerAddress) {
        this.retailerAddress = retailerAddress;
    }


    /**
     * @return the strRetailerName
     */
    public String getStrRetailerName() {
        return strRetailerName;
    }

    /**
     * @param strRetailerName the strRetailerName to set
     */
    public void setStrRetailerName(String strRetailerName) {
        this.strRetailerName = strRetailerName;
    }

    /**
     * @return the strMonthSale
     */
    public String getStrMonthSale() {
        return strMonthSale;
    }

    /**
     * @param strMonthSale the strMonthSale to set
     */
    public void setStrMonthSale(String strMonthSale) {
        this.strMonthSale = strMonthSale;
    }

    /**
     * @return the strPendingAmount
     */
    public String getStrPendingAmount() {
        return strPendingAmount;
    }

    /**
     * @param strPendingAmount the strPendingAmount to set
     */
    public void setStrPendingAmount(String strPendingAmount) {
        this.strPendingAmount = strPendingAmount;
    }

    /**
     * @return the strCreditDays
     */
    public String getStrCreditDays() {
        return strCreditDays;
    }

    /**
     * @param strCreditDays the strCreditDays to set
     */
    public void setStrCreditDays(String strCreditDays) {
        this.strCreditDays = strCreditDays;
    }

    /**
     * @return the strComment
     */
    public String getStrComment() {
        return strComment;
    }

    /**
     * @param strComment the strComment to set
     */
    public void setStrComment(String strComment) {
        this.strComment = strComment;
    }

    /**
     * @return the strPendingBill
     */
    public String getStrPendingBill() {
        return strPendingBill;
    }

    /**
     * @param strPendingBill the strPendingBill to set
     */
    public void setStrPendingBill(String strPendingBill) {
        this.strPendingBill = strPendingBill;
    }

    /**
     * @return the strIsVisit
     */
    public String getStrIsVisit() {
        return strIsVisit;
    }

    /**
     * @param strIsVisit the strIsVisit to set
     */
    public void setStrIsVisit(String strIsVisit) {
        this.strIsVisit = strIsVisit;
    }

    /**
     * @return the strOrder
     */
    public String getStrOrder() {
        return strOrder;
    }

    /**
     * @param strOrder the strOrder to set
     */
    public void setStrOrder(String strOrder) {
        this.strOrder = strOrder;
    }

    /**
     * @return the strCollection
     */
    public String getStrCollection() {
        return strCollection;
    }

    /**
     * @param strCollection the strCollection to set
     */
    public void setStrCollection(String strCollection) {
        this.strCollection = strCollection;
    }

    /**
     * @return the strReturn
     */
    public String getStrReturn() {
        return strReturn;
    }

    /**
     * @param strReturn the strReturn to set
     */
    public void setStrReturn(String strReturn) {
        this.strReturn = strReturn;
    }

    /**
     * @return the strSurvey
     */
    public String getStrSurvey() {
        return strSurvey;
    }

    /**
     * @param strSurvey the strSurvey to set
     */
    public void setStrSurvey(String strSurvey) {
        this.strSurvey = strSurvey;
    }

    /**
     * @return the strStockTake
     */
    public String getStrStockTake() {
        return strStockTake;
    }

    /**
     * @param strStockTake the strStockTake to set
     */
    public void setStrStockTake(String strStockTake) {
        this.strStockTake = strStockTake;
    }

    /**
     * @return the strSync
     */
    public String getStrSync() {
        return strSync;
    }

    /**
     * @param strSync the strSync to set
     */
    public void setStrSync(String strSync) {
        this.strSync = strSync;
    }

    /**
     * @return the strCustomerCode
     */
    public String getStrCustomerCode() {
        return strCustomerCode;
    }

    /**
     * @param strCustomerCode the strCustomerCode to set
     */
    public void setStrCustomerCode(String strCustomerCode) {
        this.strCustomerCode = strCustomerCode;
    }

    /**
     * @return the strRouteCode
     */
    public String getStrRouteCode() {
        return strRouteCode;
    }

    /**
     * @param strRouteCode the strRouteCode to set
     */
    public void setStrRouteCode(String strRouteCode) {
        this.strRouteCode = strRouteCode;
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the strMobileNo
     */
    public String getStrMobileNo() {
        return strMobileNo;
    }

    /**
     * @param strMobileNo the strMobileNo to set
     */
    public void setStrMobileNo(String strMobileNo) {
        this.strMobileNo = strMobileNo;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return this.getStrCustomerCode().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PendingVO that = (PendingVO) obj;
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Objects.equals(strCreditDays, that.strCreditDays) && Objects.equals(date, that.date);
    }

    public RetailerDashboardVO getRetailerDashboardVO() {
        return retailerDashboardVO;
    }

    public void setRetailerDashboardVO(RetailerDashboardVO retailerDashboardVO) {
        this.retailerDashboardVO = retailerDashboardVO;
    }

    public String getIsRetailerByPassGeo() {
        return isRetailerByPassGeo;
    }

    public void setIsRetailerByPassGeo(String isRetailerByPassGeo) {
        this.isRetailerByPassGeo = isRetailerByPassGeo;
    }

    public Date getLicenseExpDate() {
        return licenseExpDate;
    }

    public void setLicenseExpDate(Date licenseExpDate) {
        this.licenseExpDate = licenseExpDate;
    }
}
