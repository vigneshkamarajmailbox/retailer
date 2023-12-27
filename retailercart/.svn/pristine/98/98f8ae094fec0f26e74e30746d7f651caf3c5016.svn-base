/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_ACTIVE_OUTLETS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_BILLED_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CHANNEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CHANNEL_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_COVERAGE_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DIFF_DAYS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LAST_BILL_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_OF_INVOICES;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ROUTE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ROUTE_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALES_VALUES;

public class SalesmanDashBoardVO extends SalesmanModel {

    @SerializedName(TAG_ROUTE_CODE)
    private String routeCode = "";

    @SerializedName(TAG_ROUTE_NAME)
    private String routeName = "";

    @SerializedName(TAG_CUSTOMER_CODE)
    private String customerCode = "";

    @SerializedName(TAG_CUSTOMER_NAME)
    private String customerName = "";

    @SerializedName(TAG_CHANNEL_CODE)
    private String channelCode = "";

    @SerializedName(TAG_CHANNEL_NAME)
    private String channelName = "";

    @SerializedName(TAG_SALES_VALUES)
    private Double mtdSalesValue = 0d;

    private Double target = 0d;

    @SerializedName(TAG_NO_OF_INVOICES)
    private Integer noOfInvoices = 0;

    @SerializedName(TAG_ACTIVE_OUTLETS)
    private Integer activeOutlets = 0;

    @SerializedName(value = TAG_LAST_BILL_DATE,alternate = TAG_BILLED_DATE)
    private String lastBillDate = "";

    @SerializedName(TAG_DIFF_DAYS)
    private Integer diffDays = 0;

    private String months = "";

    private String distName = "";

    @SerializedName(TAG_COVERAGE_DATE)
    private String coverageDate = "";

    public String getCoverageDate() {
        return coverageDate;
    }

    public void setCoverageDate(String coverageDate) {
        this.coverageDate = coverageDate;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLastBillDate() {
        return lastBillDate;
    }

    public void setLastBillDate(String lastBillDate) {
        this.lastBillDate = lastBillDate;
    }

    public Integer getDiffDays() {
        return diffDays;
    }

    public void setDiffDays(Integer diffDays) {
        this.diffDays = diffDays;
    }

    /**
     * @return the mtdSalesValue
     */
    public Double getMtdSalesValue() {
        return mtdSalesValue;
    }

    /**
     * @param mtdSalesValue the mtdSalesValue to set
     */
    public void setMtdSalesValue(Double mtdSalesValue) {
        this.mtdSalesValue = mtdSalesValue;
    }

    /**
     * @return the target
     */
    public Double getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(Double target) {
        this.target = target;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public Integer getActiveOutlets() {
        return activeOutlets;
    }

    public void setActiveOutlets(Integer activeOutlets) {
        this.activeOutlets = activeOutlets;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getNoOfInvoices() {
        return noOfInvoices;
    }

    public void setNoOfInvoices(Integer noOfInvoices) {
        this.noOfInvoices = noOfInvoices;
    }
}
