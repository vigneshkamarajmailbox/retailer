package com.botree.interdbentity.model;

/**
 * Class contains the Order Booking Scheme Details fields.
 * @author vinodkumar.a
 */
public class OrderBookingSchemeDetailsEntity extends AbstractEntity {

    /** distrCode. */
    private String distrCode;

    /** orderNo. */
    private String orderNo;

    /** schemeCode. */
    private String schemeCode;

    /** slabNo. */
    private String slabNo;

    /** freeProdCode. */
    private String freeProdCode;

    /** freeQty. */
    private int freeQty = 0;

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
     * @return the freeProdCode
     */
    public final String getFreeProdCode() {
        return freeProdCode;
    }

    /**
     * @param freeProdCodeIn the freeProdCode to set
     */
    public final void setFreeProdCode(final String freeProdCodeIn) {
        this.freeProdCode = freeProdCodeIn;
    }

    /**
     * @return the freeQty
     */
    public final int getFreeQty() {
        return freeQty;
    }

    /**
     * @param freeQtyIn the freeQty to set
     */
    public final void setFreeQty(final int freeQtyIn) {
        this.freeQty = freeQtyIn;
    }

    /**
     * Method to for the order key.
     * @return key
     */
    public String orderKey() {
        return this.getCmpCode() + this.getDistrCode() + this.getOrderNo();
    }
}
