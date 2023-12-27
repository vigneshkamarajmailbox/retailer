package com.botree.interdbentity.model;

import java.util.Date;

/**
 * ProductBatch Entity class.
 * @author vinodkumar.a
 */
public class ProductBatchEntity extends AbstractEntity {

    /** batchLevel. */
    private String batchLevel;

    /** prodCode. */
    private String prodCode;

    /** prodBatchCode. */
    private String prodBatchCode;

    /** mrp. */
    private Double mrp;

    /** sellRate. */
    private Double sellRate;

    /** sellRateWithTax. */
    private Double sellRateWithTax;

    /** manfDate. */
    private Date manfDate;

    /** expiryDate. */
    private Date expiryDate;

    /** latestBatch. */
    private String latestBatch;

    /** geoLevelBatch. */
    private String geoLevelBatch;

    /**
     * @return the batchLevel
     */
    public final String getBatchLevel() {
        return batchLevel;
    }

    /**
     * @param batchLevelIn the batchLevel to set
     */
    public final void setBatchLevel(final String batchLevelIn) {
        batchLevel = batchLevelIn;
    }

    /**
     * @return the prodCode
     */
    public final String getProdCode() {
        return prodCode;
    }

    /**
     * @param prodCodeIn the prodCode to set
     */
    public final void setProdCode(final String prodCodeIn) {
        prodCode = prodCodeIn;
    }

    /**
     * @return the prodBatchCode
     */
    public final String getProdBatchCode() {
        return prodBatchCode;
    }

    /**
     * @param prodBatchCodeIn the prodBatchCode to set
     */
    public final void setProdBatchCode(final String prodBatchCodeIn) {
        prodBatchCode = prodBatchCodeIn;
    }

    /**
     * @return the mrp
     */
    public final Double getMrp() {
        return mrp;
    }

    /**
     * @param mrpIn the mrp to set
     */
    public final void setMrp(final Double mrpIn) {
        mrp = mrpIn;
    }

    /**
     * @return the sellRate
     */
    public final Double getSellRate() {
        return sellRate;
    }

    /**
     * @param sellRateIn the sellRate to set
     */
    public final void setSellRate(final Double sellRateIn) {
        sellRate = sellRateIn;
    }

    /**
     * @return the sellRateWithTax
     */
    public final Double getSellRateWithTax() {
        return sellRateWithTax;
    }

    /**
     * @param sellRateWithTaxIn the sellRateWithTax to set
     */
    public final void setSellRateWithTax(final Double sellRateWithTaxIn) {
        sellRateWithTax = sellRateWithTaxIn;
    }

    /**
     * @return the manfDate
     */
    public final Date getManfDate() {
        return manfDate;
    }

    /**
     * @param manfDateIn the manfDate to set
     */
    public final void setManfDate(final Date manfDateIn) {
        manfDate = manfDateIn;
    }

    /**
     * @return the expiryDate
     */
    public final Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDateIn the expiryDate to set
     */
    public final void setExpiryDate(final Date expiryDateIn) {
        expiryDate = expiryDateIn;
    }

    /**
     * @return the latestBatch
     */
    public final String getLatestBatch() {
        return latestBatch;
    }

    /**
     * @param latestBatchIn the latestBatch to set
     */
    public final void setLatestBatch(final String latestBatchIn) {
        latestBatch = latestBatchIn;
    }

    /**
     * @return the geoLevelBatch
     */
    public final String getGeoLevelBatch() {
        return geoLevelBatch;
    }

    /**
     * @param geoLevelBatchIn the geoLevelBatch to set
     */
    public final void setGeoLevelBatch(final String geoLevelBatchIn) {
        geoLevelBatch = geoLevelBatchIn;
    }

    /**
     * Method to fetch product key.
     * @return product key
     */
    public final String productKey() {
        return this.getCmpCode() + this.getBatchLevel() + this.getProdCode();
    }
}
