package com.botree.interdbentity.model;

/**
 * Class contains the Order Booking Scheme Product Rule Details fields.
 * @author vinodkumar.a
 */
public class OrderBookingSchemeProductRuleEntity extends AbstractEntity {

    /** distrCode. */
    private String distrCode;

    /** orderNo. */
    private String orderNo;

    /** schemeCode. */
    private String schemeCode;

    /** slabNo. */
    private String slabNo;

    /** freeProdCode. */
    private String prodCode;

    /** discPerc. */
    private Double discPerc = 0d;

    /** discAmt. */
    private Double discAmt = 0d;

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
        this.distrCode = distrCodeIn;
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
        this.orderNo = orderNoIn;
    }

    /**
     * @return the schemeCode
     */
    public final String getSchemeCode() {
        return schemeCode;
    }

    /**
     * @param schemeCodeIn the schemeCode to set
     */
    public final void setSchemeCode(final String schemeCodeIn) {
        this.schemeCode = schemeCodeIn;
    }

    /**
     * @return the slabNo
     */
    public final String getSlabNo() {
        return slabNo;
    }

    /**
     * @param slabNoIn the slabNo to set
     */
    public final void setSlabNo(final String slabNoIn) {
        this.slabNo = slabNoIn;
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
        this.prodCode = prodCodeIn;
    }

    /**
     * @return the discPerc
     */
    public final Double getDiscPerc() {
        return discPerc;
    }

    /**
     * @param discPercIn the discPerc to set
     */
    public final void setDiscPerc(final Double discPercIn) {
        this.discPerc = discPercIn;
    }

    /**
     * @return the discAmt
     */
    public final Double getDiscAmt() {
        return discAmt;
    }

    /**
     * @param discAmtIn the discAmt to set
     */
    public final void setDiscAmt(final Double discAmtIn) {
        this.discAmt = discAmtIn;
    }

    /**
     * Method to for the order key.
     * @return key
     */
    public String orderKey() {
        return this.getCmpCode() + this.getDistrCode() + this.getOrderNo();
    }
}
