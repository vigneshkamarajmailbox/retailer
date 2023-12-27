package com.botree.common.model;

public class LoginReferralModel {
    /** CmpCode. */
    private String cmpCode;
    /** DistrCode. */
    private String distrCode;
    /** SalesmanCode. */
    private String salesmanCode;
    /** CustomerCode. */
    private String customerCode;
    /** uploadFlag. */
    private char uploadFlag;
    /** modDt. */
    private String modDt;


    /**
     * @return the CmpCode
     */
    public final String getCmpCode() {
        return cmpCode;
    }

    /**
     * @param cmpCodeIn the CmpCode to set
     */
    public final void setCmpCode(final String cmpCodeIn) {
        cmpCode = cmpCodeIn;
    }

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


    /**
     * @return the uploadFlag
     */
    public final char getUploadFlag() {
        return uploadFlag;
    }

    /**
     * @param uploadFlagIn the uploadFlag to set
     */
    public final void setUploadFlag(final char uploadFlagIn) {
        uploadFlag = uploadFlagIn;
    }

    /**
     * @return the modDt
     */
    public final String getModDt() {
        return modDt;
    }

    /**
     * @param modDtIn the modDt to set
     */
    public final void setModDt(final String modDtIn) {
        modDt = modDtIn;
    }
}
