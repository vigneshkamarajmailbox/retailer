package com.botree.mdmtointerdb.model;

import java.util.Date;

/**
 * @author SK
 */
public class OrderHeaderModel {

    /** cmpCode. */
    private String cmpCode;
    /** distrCode. */
    private String distrCode;
    /** orderNo. */
    private String orderNo;
    /** customerRefNo. */
    private String customerRefNo;
    /** customerCode. */
    private String customerCode;
    /** customerShipCode. */
    private String customerShipCode;
    /** salesmanCode. */
    private String salesmanCode;
    /** routeCode. */
    private String routeCode;
    /** orderDt. */
    private Date orderDt;
    /** orderDtString. */
    private String orderDtString;

    /**
     * @return the cmpCode
     */
    public final String getCmpCode() {
        return cmpCode;
    }

    /**
     * @param cmpCodeIn the cmpCode to set
     */
    public final void setCmpCode(final String cmpCodeIn) {
        cmpCode = cmpCodeIn;
    }

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
     * @return the salesmanCode
     */
    public final String getSalesmanCode() {
        return salesmanCode;
    }

    /**
     * @param salesmanCodeIn the salesmanCode to set
     */
    public final void setSalesmanCode(final String salesmanCodeIn) {
        salesmanCode = salesmanCodeIn;
    }

    /**
     * @return the routeCode
     */
    public final String getRouteCode() {
        return routeCode;
    }

    /**
     * @param routeCodeIn the routeCode to set
     */
    public final void setRouteCode(final String routeCodeIn) {
        routeCode = routeCodeIn;
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
     * @return the orderDtString
     */
    public final String getOrderDtString() {
        return orderDtString;
    }

    /**
     * @param orderDtStringIn the orderDtString to set
     */
    public final void setOrderDtString(final String orderDtStringIn) {
        orderDtString = orderDtStringIn;
    }
}
