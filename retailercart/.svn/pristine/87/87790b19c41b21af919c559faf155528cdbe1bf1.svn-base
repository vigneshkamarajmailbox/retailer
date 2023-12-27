package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_SALESMAN_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_SALESMAN_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALESMAN_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALESMAN_NAME;

public class SalesmanModel extends UserLevelModel {

    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";

    @SerializedName(TAG_DISTR_CODE)
    private String distrCode = "";

    @SerializedName(TAG_DISTR_NAME)
    private String distrName = "";

    @SerializedName(value = TAG_SALESMAN_CODE, alternate = TAG_DISTR_SALESMAN_CODE)
    private String salesmanCode = "";

    @SerializedName(value = TAG_SALESMAN_NAME, alternate = TAG_DISTR_SALESMAN_NAME)
    private String salesmanName = "";

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getDistrName() {
        return distrName;
    }

    public void setDistrName(String distrName) {
        this.distrName = distrName;
    }
}
