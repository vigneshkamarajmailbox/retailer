package com.botree.interdbentity.model;

/**
 * Customer Route Mapping Entity class.
 * @author vinodkumar.a
 */
public class CustomerRouteMappingEntity extends AbstractEntity {

    /** distrCode. */
    private String distrCode;

    /** customerCode. */
    private String customerCode;

    /** routeCode. */
    private String routeCode;

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
}
