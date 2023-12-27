package com.botree.interdbentity.model;

/**
 * Product Uom Entity class.
 * @author vinodkumar.a
 */
public class ProductUomEntity extends AbstractEntity {

    /** prodCode. */
    private String prodCode;

    /** uomCode. */
    private String uomCode;

    /** uomDescription. */
    private String uomDescription;

    /** uomConvFactor. */
    private String uomConvFactor;

    /** baseUOM. */
    private String baseUOM;

    /** defaultUOM. */
    private String defaultUOM;

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
     * @return the uomCode
     */
    public final String getUomCode() {
        return uomCode;
    }

    /**
     * @param uomCodeIn the uomCode to set
     */
    public final void setUomCode(final String uomCodeIn) {
        this.uomCode = uomCodeIn;
    }

    /**
     * @return the uomDescription
     */
    public final String getUomDescription() {
        return uomDescription;
    }

    /**
     * @param uomDescriptionIn the uomDescription to set
     */
    public final void setUomDescription(final String uomDescriptionIn) {
        this.uomDescription = uomDescriptionIn;
    }

    /**
     * @return the baseUOM
     */
    public final String getBaseUOM() {
        return baseUOM;
    }

    /**
     * @param baseUOMIn the baseUOM to set
     */
    public final void setBaseUOM(final String baseUOMIn) {
        baseUOM = baseUOMIn;
    }

    /**
     * @return the uomConvFactor
     */
    public final String getUomConvFactor() {
        return uomConvFactor;
    }

    /**
     * @param uomConvFactorIn the uomConvFactor to set
     */
    public final void setUomConvFactor(final String uomConvFactorIn) {
        this.uomConvFactor = uomConvFactorIn;
    }

    /**
     * @return the defaultUOM
     */
    public final String getDefaultUOM() {
        return defaultUOM;
    }

    /**
     * @param defaultUOMIn the defaultUOM to set
     */
    public final void setDefaultUOM(final String defaultUOMIn) {
        defaultUOM = defaultUOMIn;
    }

}
