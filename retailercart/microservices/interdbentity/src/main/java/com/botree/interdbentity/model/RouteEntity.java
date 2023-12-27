package com.botree.interdbentity.model;

/**
 * Route Entity class.
 * @author vinodkumar.a
 */
public class RouteEntity extends AbstractEntity {

    /** distrCode. */
    private String distrCode;

    /** routeCode. */
    private String routeCode;

    /** routeName. */
    private String routeName;

    /** routeType. */
    private String routeType;

    /** routeStatus. */
    private String routeStatus;

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
     * @return the routeName
     */
    public final String getRouteName() {
        return routeName;
    }

    /**
     * @param routeNameIn the routeName to set
     */
    public final void setRouteName(final String routeNameIn) {
        routeName = routeNameIn;
    }

    /**
     * @return the routeType
     */
    public final String getRouteType() {
        return routeType;
    }

    /**
     * @param routeTypeIn the routeType to set
     */
    public final void setRouteType(final String routeTypeIn) {
        routeType = routeTypeIn;
    }

    /**
     * @return the routeStatus
     */
    public final String getRouteStatus() {
        return routeStatus;
    }

    /**
     * @param routeStatusIn the routeStatus to set
     */
    public final void setRouteStatus(final String routeStatusIn) {
        routeStatus = routeStatusIn;
    }
}
