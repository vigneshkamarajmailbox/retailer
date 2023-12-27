package com.botree.interdbentity.model;

/**
 * Geo Hier Value Entity class.
 * @author vinodkumar.a
 */
public class GeoHierValueEntity extends AbstractEntity {

    /** geoHierLevelCode. */
    private Integer geoHierLevelCode;

    /** geoCode. */
    private String geoCode;

    /** geoName. */
    private String geoName;

    /** parentCode. */
    private String parentCode;

    /**
     * @return the geoHierLevelCode
     */
    public final Integer getGeoHierLevelCode() {
        return geoHierLevelCode;
    }

    /**
     * @param geoHierLevelCodeIn the geoHierLevelCode to set
     */
    public final void setGeoHierLevelCode(final Integer geoHierLevelCodeIn) {
        geoHierLevelCode = geoHierLevelCodeIn;
    }

    /**
     * @return the geoCode
     */
    public final String getGeoCode() {
        return geoCode;
    }

    /**
     * @param geoCodeIn the geoCode to set
     */
    public final void setGeoCode(final String geoCodeIn) {
        geoCode = geoCodeIn;
    }

    /**
     * @return the geoName
     */
    public final String getGeoName() {
        return geoName;
    }

    /**
     * @param geoNameIn the geoName to set
     */
    public final void setGeoName(final String geoNameIn) {
        geoName = geoNameIn;
    }

    /**
     * @return the parentCode
     */
    public final String getParentCode() {
        return parentCode;
    }

    /**
     * @param parentCodeIn the parentCode to set
     */
    public final void setParentCode(final String parentCodeIn) {
        parentCode = parentCodeIn;
    }
}
