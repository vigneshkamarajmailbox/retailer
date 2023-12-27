package com.botree.interdbentity.model;

/**
 * Class for banner image template.
 * @author vinodkumar.a
 */
public class BannerImageTemplateEntity extends AbstractEntity {

    /** bannerId. */
    private String bannerId;

    /** bannerDesc. */
    private String bannerDesc;

    /** bannerType. */
    private String bannerType;

    /** fileName. */
    private String fileName;

    /** distrCode. */
    private String distrCode;

    /**
     * @return the bannerId
     */
    public final String getBannerId() {
        return bannerId;
    }

    /**
     * @param bannerIdIn the bannerId to set
     */
    public final void setBannerId(final String bannerIdIn) {
        bannerId = bannerIdIn;
    }

    /**
     * @return the bannerDesc
     */
    public final String getBannerDesc() {
        return bannerDesc;
    }

    /**
     * @param bannerDescIn the bannerDesc to set
     */
    public final void setBannerDesc(final String bannerDescIn) {
        bannerDesc = bannerDescIn;
    }

    /**
     * @return the bannerType
     */
    public final String getBannerType() {
        return bannerType;
    }

    /**
     * @param bannerTypeIn the bannerType to set
     */
    public final void setBannerType(final String bannerTypeIn) {
        bannerType = bannerTypeIn;
    }

    /**
     * @return the fileName
     */
    public final String getFileName() {
        return fileName;
    }

    /**
     * @param fileNameIn the fileName to set
     */
    public final void setFileName(final String fileNameIn) {
        fileName = fileNameIn;
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
}
