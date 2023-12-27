package com.botree.interdbentity.model;

/**
 * Scheme Customer Entity class.
 * @author vinodkumar.a
 */
public class SchemeCustomerEntity extends AbstractEntity {

    /** schemeCode. */
    private String schemeCode;

    /** customerCode. */
    private String customerCode;

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
     * @return the customerCode
     */
    public final String getCustomerCode() {
        return customerCode;
    }

    /**
     * @param customerCodeIn the customerCode to set
     */
    public final void setCustomerCode(final String customerCodeIn) {
        this.customerCode = customerCodeIn;
    }
}
