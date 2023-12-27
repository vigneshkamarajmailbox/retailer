package com.botree.interdbentity.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class contains the order booking header fields.
 * @author vinodkumar.a
 */
public class OrderBookingHeaderEntity extends AbstractEntity {

    /** distrCode. */
    private String distrCode;

    /** distrName. */
    private String distrName;

    /** orderNo. */
    private String orderNo;

    /** customerRefNo. */
    private String customerRefNo;

    /** customerCode. */
    private String customerCode;

    /** customerShipCode. */
    private String customerShipCode;

    /** orderDt. */
    private Date orderDt;

    /** remarks. */
    private String remarks;

    /** latitude. */
    private String latitude;

    /** longitude. */
    private String longitude;

    /** startTime. */
    private Date startTime;

    /** endTime. */
    private Date endTime;

    /** orderStatus. */
    private String orderStatus;

    /** totalGrossValue. */
    private Double totalGrossValue;

    /** totalDiscount. */
    private Double totalDiscount;

    /** totalTax. */
    private Double totalTax;

    /** totalOrderValue. */
    private Double totalOrderValue;

    /** orderBookingDetailsList. */
    private List<OrderBookingDetailsEntity> orderBookingDetailsList = new ArrayList<>();

    /** orderBookingServiceDetailsList. */
    private List<OrderBookingServiceDetailsEntity> orderBookingServiceDetailsList = new ArrayList<>();

    /** orderBookingSchemeDetailsList. */
    private List<OrderBookingSchemeDetailsEntity> orderBookingSchemeDetailsList = new ArrayList<>();

    /** orderBookingSchemeProductRuleList. */
    private List<OrderBookingSchemeProductRuleEntity> orderBookingSchemeProductRuleList = new ArrayList<>();

    /** actionOrderStatusList. */
    private List<ActionOrderStatusEntity> actionOrderStatusList = new ArrayList<>();

    /**
     * @return the distrCode
     */
    public final String getDistrCode() {
        return distrCode;
    }

    /**
     * @param distrCodeIn the distrCode to set
     */
    public final void setDistrCode(final String distrCodeIn) {
        distrCode = distrCodeIn;
    }

    /**
     * @return the distrName
     */
    public final String getDistrName() {
        return distrName;
    }

    /**
     * @param distrNameIn the distrName to set
     */
    public final void setDistrName(final String distrNameIn) {
        distrName = distrNameIn;
    }

    /**
     * @return the orderNo
     */
    public final String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNoIn the orderNo to set
     */
    public final void setOrderNo(final String orderNoIn) {
        orderNo = orderNoIn;
    }

    /**
     * @return the customerRefNo
     */
    public final String getCustomerRefNo() {
        return customerRefNo;
    }

    /**
     * @param customerRefNoIn the customerRefNo to set
     */
    public final void setCustomerRefNo(final String customerRefNoIn) {
        customerRefNo = customerRefNoIn;
    }

    /**
     * @return the customerCode
     */
    public final String getCustomerCode() {
        return customerCode;
    }

    /**
     * @param customerCodeIn the customerCode to set
     */
    public final void setCustomerCode(final String customerCodeIn) {
        customerCode = customerCodeIn;
    }

    /**
     * @return the customerShipCode
     */
    public final String getCustomerShipCode() {
        return customerShipCode;
    }

    /**
     * @param customerShipCodeIn the customerShipCode to set
     */
    public final void setCustomerShipCode(final String customerShipCodeIn) {
        customerShipCode = customerShipCodeIn;
    }

    /**
     * @return the orderDt
     */
    public final Date getOrderDt() {
        return orderDt;
    }

    /**
     * @param orderDtIn the orderDt to set
     */
    public final void setOrderDt(final Date orderDtIn) {
        orderDt = orderDtIn;
    }

    /**
     * @return the remarks
     */
    public final String getRemarks() {
        return remarks;
    }

    /**
     * @param remarksIn the remarks to set
     */
    public final void setRemarks(final String remarksIn) {
        remarks = remarksIn;
    }

    /**
     * @return the latitude
     */
    public final String getLatitude() {
        return latitude;
    }

    /**
     * @param latitudeIn the latitude to set
     */
    public final void setLatitude(final String latitudeIn) {
        latitude = latitudeIn;
    }

    /**
     * @return the longitude
     */
    public final String getLongitude() {
        return longitude;
    }

    /**
     * @param longitudeIn the longitude to set
     */
    public final void setLongitude(final String longitudeIn) {
        longitude = longitudeIn;
    }

    /**
     * @return the startTime
     */
    public final Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTimeIn the startTime to set
     */
    public final void setStartTime(final Date startTimeIn) {
        startTime = startTimeIn;
    }

    /**
     * @return the endTime
     */
    public final Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTimeIn the endTime to set
     */
    public final void setEndTime(final Date endTimeIn) {
        endTime = endTimeIn;
    }

    /**
     * @return the orderStatus
     */
    public final String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatusIn the orderStatus to set
     */
    public final void setOrderStatus(final String orderStatusIn) {
        orderStatus = orderStatusIn;
    }

    /**
     * @return the totalGrossValue
     */
    public final Double getTotalGrossValue() {
        return totalGrossValue;
    }

    /**
     * @param totalGrossValueIn the totalGrossValue to set
     */
    public final void setTotalGrossValue(final Double totalGrossValueIn) {
        totalGrossValue = totalGrossValueIn;
    }

    /**
     * @return the totalDiscount
     */
    public final Double getTotalDiscount() {
        return totalDiscount;
    }

    /**
     * @param totalDiscountIn the totalDiscount to set
     */
    public final void setTotalDiscount(final Double totalDiscountIn) {
        totalDiscount = totalDiscountIn;
    }

    /**
     * @return the totalTax
     */
    public final Double getTotalTax() {
        return totalTax;
    }

    /**
     * @param totalTaxIn the totalTax to set
     */
    public final void setTotalTax(final Double totalTaxIn) {
        totalTax = totalTaxIn;
    }

    /**
     * @return the totalOrderValue
     */
    public final Double getTotalOrderValue() {
        return totalOrderValue;
    }

    /**
     * @param totalOrderValueIn the totalOrderValue to set
     */
    public final void setTotalOrderValue(final Double totalOrderValueIn) {
        totalOrderValue = totalOrderValueIn;
    }

    /**
     * @return the orderBookingDetailsList
     */
    public final List<OrderBookingDetailsEntity> getOrderBookingDetailsList() {
        return orderBookingDetailsList;
    }

    /**
     * @param orderBookingDetailsListIn the orderBookingDetailsList to set
     */
    public final void setOrderBookingDetailsList(
            final List<OrderBookingDetailsEntity> orderBookingDetailsListIn) {
        orderBookingDetailsList = orderBookingDetailsListIn;
    }

    /**
     * @return the orderBookingServiceDetailsList
     */
    public final List<OrderBookingServiceDetailsEntity> getOrderBookingServiceDetailsList() {
        return orderBookingServiceDetailsList;
    }

    /**
     * @param orderBookingServiceDetailsListIn the orderBookingServiceDetailsList to set
     */
    public final void setOrderBookingServiceDetailsList(
            final List<OrderBookingServiceDetailsEntity> orderBookingServiceDetailsListIn) {
        orderBookingServiceDetailsList = orderBookingServiceDetailsListIn;
    }

    /**
     * @return the orderBookingSchemeDetailsList
     */
    public final List<OrderBookingSchemeDetailsEntity> getOrderBookingSchemeDetailsList() {
        return orderBookingSchemeDetailsList;
    }

    /**
     * @param orderBookingSchemeDetailsListIn the orderBookingSchemeDetailsList to set
     */
    public final void setOrderBookingSchemeDetailsList(
            final List<OrderBookingSchemeDetailsEntity> orderBookingSchemeDetailsListIn) {
        orderBookingSchemeDetailsList = orderBookingSchemeDetailsListIn;
    }

    /**
     * @return the orderBookingSchemeProductRuleList
     */
    public final List<OrderBookingSchemeProductRuleEntity> getOrderBookingSchemeProductRuleList() {
        return orderBookingSchemeProductRuleList;
    }

    /**
     * @param orderBookingSchemeProductRuleListIn the orderBookingSchemeProductRuleList to set
     */
    public final void setOrderBookingSchemeProductRuleList(
            final List<OrderBookingSchemeProductRuleEntity> orderBookingSchemeProductRuleListIn) {
        orderBookingSchemeProductRuleList = orderBookingSchemeProductRuleListIn;
    }

    /**
     * @return the actionOrderStatusList
     */
    public final List<ActionOrderStatusEntity> getActionOrderStatusList() {
        return actionOrderStatusList;
    }

    /**
     * @param actionOrderStatusListIn the actionOrderStatusList to set
     */
    public final void setActionOrderStatusList(
            final List<ActionOrderStatusEntity> actionOrderStatusListIn) {
        actionOrderStatusList = actionOrderStatusListIn;
    }

    /**
     * Method to for the order key.
     * @return key
     */
    public String orderKey() {
        return this.getCmpCode() + this.getDistrCode() + this.getOrderNo();
    }
}
