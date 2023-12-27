package com.botree.interdbentity.model;

/**
 * Scheme Product Entity class.
 * @author vinodkumar.a
 */
public class SchemeProductEntity extends AbstractEntity {

    /** schemeCode. */
    private String schemeCode;

    /** prodCode. */
    private String prodCode;

    /** prodName. */
    private String prodName;

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
     * @return the prodName
     */
    public final String getProdName() {
        return prodName;
    }

    /**
     * @param prodNameIn the prodName to set
     */
    public final void setProdName(final String prodNameIn) {
        this.prodName = prodNameIn;
    }
}
