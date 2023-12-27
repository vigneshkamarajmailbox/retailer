package com.botree.interdbentity.model;

public class LoginReferralEntity extends AbstractEntity {

    /** DistrCode. */
    private String distrCode;
    /** SalesmanCode. */
    private String salesmanCode;
    /** CustomerCode. */
    private String customerCode;



    /**
     * @return the DistrCode
     */
    public final String getDistrCode() {
        return distrCode;
    }

    /**
     * @param distrCodeIn the DistrCode to set
     */
    public final void setDistrCode(final String distrCodeIn) {
        distrCode = distrCodeIn;
    }

    /**
     * @return the SalesmanCode
     */
    public final String getSalesmanCode() {
        return salesmanCode;
    }

    /**
     * @param salesmanCodeIn the SalesmanCode to set
     */
    public final void setSalesmanCode(final String salesmanCodeIn) {
        salesmanCode = salesmanCodeIn;
    }

    /**
     * @return the CustomerCode
     */
    public final String getCustomerCode() {
        return customerCode;
    }

    /**
     * @param customerCodeIn the CustomerCode to set
     */
    public final void setCustomerCode(final String customerCodeIn) {
        customerCode = customerCodeIn;
    }

}
