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

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_BALANCE_OUTSTANDING;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CREDIT_DAYS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_SALESMAN_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_SALESMAN_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_DT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_NUMBER;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PAID_AMOUNT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PENDING_BILLS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALESMAN_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALESMAN_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TOTAL_NET_AMOUNT;

public class PendingBillVO extends UserLevelModel {

    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";

    @SerializedName(TAG_TOTAL_NET_AMOUNT)
    private Double totNetAmt = 0d;

    @SerializedName(TAG_PAID_AMOUNT)
    private Double paidAmt = 0d;

    @SerializedName(TAG_DISTR_CODE)
    private String distrCode = "";

    @SerializedName(value = TAG_SALESMAN_CODE, alternate = {TAG_DISTR_SALESMAN_CODE})
    private String salesmanCode = "";

    @SerializedName(value = TAG_SALESMAN_NAME, alternate = {TAG_DISTR_SALESMAN_NAME})
    private String salesmanName = "";

    private String routeCode = "";

    @SerializedName(TAG_CUSTOMER_CODE)
    private String customerCode = "";

    @SerializedName(TAG_CUSTOMER_NAME)
    private String customerName = "";

    @SerializedName(value = TAG_INVOICE_NO, alternate = {TAG_INVOICE_NUMBER})
    private String invoiceNo = "";

    @SerializedName(value = TAG_INVOICE_DATE, alternate = {TAG_INVOICE_DT})

    private String invoiceDate = "";

    @SerializedName(value = TAG_SALES_VALUE, alternate = {TAG_BALANCE_OUTSTANDING})
    private Double pendingAmount = 0d;

    @SerializedName(TAG_PENDING_BILLS)
    private Integer pendingBills = 0;

    @SerializedName(TAG_CREDIT_DAYS)
    private Integer creditDays = 0;


    @SerializedName(TAG_DISTR_NAME)
    private String distrName = "";


    public String getDistrName() {
        return distrName;
    }

    public void setDistrName(String distrName) {
        this.distrName = distrName;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public Double getTotNetAmt() {
        return totNetAmt;
    }

    public void setTotNetAmt(Double totNetAmt) {
        this.totNetAmt = totNetAmt;
    }

    public Double getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(Double paidAmt) {
        this.paidAmt = paidAmt;
    }

    /**
     * @return the distrCode
     */
    public String getDistrCode() {
        return distrCode;
    }

    /**
     * @param distrCode the distrCode to set
     */
    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    /**
     * @return the salesmanCode
     */
    public String getSalesmanCode() {
        return salesmanCode;
    }

    /**
     * @param salesmanCode the salesmanCode to set
     */
    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
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
     * @return the customerCode
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * @param customerCode the customerCode to set
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**
     * @return the invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * @param invoiceNo the invoiceNo to set
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * @return the invoiceDate
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * @param invoiceDate the invoiceDate to set
     */
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * @return the pendingAmount
     */
    public Double getPendingAmount() {
        return pendingAmount;
    }

    /**
     * @param pendingAmount the pendingAmount to set
     */
    public void setPendingAmount(Double pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public Integer getPendingBills() {
        return pendingBills;
    }

    public void setPendingBills(Integer pendingBills) {
        this.pendingBills = pendingBills;
    }

    public Integer getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(Integer creditDays) {
        this.creditDays = creditDays;
    }
}
