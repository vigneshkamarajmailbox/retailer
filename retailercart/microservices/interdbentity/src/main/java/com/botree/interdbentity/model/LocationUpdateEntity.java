package com.botree.interdbentity.model;

import java.util.Date;

/**
 * Class to update the location, image and pin code.
 * @author vinodkumar.a
 */
public class LocationUpdateEntity extends AbstractEntity {

    /** distrCode. */
    private String distrCode;

    /** loginCode. */
    private String loginCode;

    /** updateDt. */
    private Date updateDt;

    /** latitude. */
    private String latitude;

    /** longitude. */
    private String longitude;

    /** image. */
    private String image;

    /** imagePath. */
    private String imagePath;

    /** postalCode. */
    private Integer postalCode;

    /** uploadTo. */
    private String uploadTo;

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
     * @return the loginCode
     */
    public final String getLoginCode() {
        return loginCode;
    }

    /**
     * @param loginCodeIn the loginCode to set
     */
    public final void setLoginCode(final String loginCodeIn) {
        loginCode = loginCodeIn;
    }

    /**
     * @return the updateDt
     */
    public final Date getUpdateDt() {
        return updateDt;
    }

    /**
     * @param updateDtIn the updateDt to set
     */
    public final void setUpdateDt(final Date updateDtIn) {
        this.updateDt = updateDtIn;
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
        this.latitude = latitudeIn;
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
        this.longitude = longitudeIn;
    }

    /**
     * @return the image
     */
    public final String getImage() {
        return image;
    }

    /**
     * @param imageIn the image to set
     */
    public final void setImage(final String imageIn) {
        this.image = imageIn;
    }

    /**
     * @return the imagePath
     */
    public final String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePathIn the imagePath to set
     */
    public final void setImagePath(final String imagePathIn) {
        this.imagePath = imagePathIn;
    }

    /**
     * @return the postalCode
     */
    public final Integer getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCodeIn the postalCode to set
     */
    public final void setPostalCode(final Integer postalCodeIn) {
        this.postalCode = postalCodeIn;
    }

    /**
     * @return the uploadTo
     */
    public final String getUploadTo() {
        return uploadTo;
    }

    /**
     * @param uploadToIn the uploadTo to set
     */
    public final void setUploadTo(final String uploadToIn) {
        uploadTo = uploadToIn;
    }
}
