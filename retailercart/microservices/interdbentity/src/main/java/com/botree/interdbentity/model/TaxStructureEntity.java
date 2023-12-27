package com.botree.interdbentity.model;

/**
 * Tax Structure Entity class.
 * @author vinodkumar.a
 */
public class TaxStructureEntity extends AbstractEntity {

    /** taxStateCode. */
    private String taxStateCode;

    /** prodCode. */
    private String prodCode;

    /** cgst. */
    private Double cgst;

    /** sgst. */
    private Double sgst;

    /** igst. */
    private Double igst;

    /** taxName. */
    private Double cess;

    /** additionalTax. */
    private Double additionalTax;

    /**
     * @return the taxStateCode
     */
    public final String getTaxStateCode() {
        return taxStateCode;
    }

    /**
     * @param taxStateCodeIn the taxStateCode to set
     */
    public final void setTaxStateCode(final String taxStateCodeIn) {
        taxStateCode = taxStateCodeIn;
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
     * @return the cgst
     */
    public final Double getCgst() {
        return cgst;
    }

    /**
     * @param cgstIn the cgst to set
     */
    public final void setCgst(final Double cgstIn) {
        cgst = cgstIn;
    }

    /**
     * @return the sgst
     */
    public final Double getSgst() {
        return sgst;
    }

    /**
     * @param sgstIn the sgst to set
     */
    public final void setSgst(final Double sgstIn) {
        sgst = sgstIn;
    }

    /**
     * @return the igst
     */
    public final Double getIgst() {
        return igst;
    }

    /**
     * @param igstIn the igst to set
     */
    public final void setIgst(final Double igstIn) {
        igst = igstIn;
    }

    /**
     * @return the cess
     */
    public final Double getCess() {
        return cess;
    }

    /**
     * @param cessIn the cess to set
     */
    public final void setCess(final Double cessIn) {
        cess = cessIn;
    }

    /**
     * @return the additionalTax
     */
    public final Double getAdditionalTax() {
        return additionalTax;
    }

    /**
     * @param additionalTaxIn the additionalTax to set
     */
    public final void setAdditionalTax(final Double additionalTaxIn) {
        additionalTax = additionalTaxIn;
    }

}
