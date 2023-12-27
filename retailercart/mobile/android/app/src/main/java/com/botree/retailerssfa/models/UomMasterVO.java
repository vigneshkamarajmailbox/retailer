/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_BASE_UOM;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CONVERSION_FACTOR;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_PATH;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_PATH_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UOM_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UOM_DESCRIPTION;

public class UomMasterVO {

    @SerializedName(TAG_DISTR_CODE)
    private String distrCode = "";

    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";


    private String salesmanCode = "";

    @SerializedName(TAG_PROD_CODE)
    private String prodCode = "";

    @SerializedName(TAG_UOM_CODE)
    private String uomCode = "";

    @SerializedName(TAG_UOM_DESCRIPTION)
    private String uomDescription = "";

    @SerializedName(TAG_BASE_UOM)
    private String baseUom = "";

    @SerializedName(TAG_PROD_HIER_PATH)
    private String prodHierPath="";

    @SerializedName(TAG_PROD_HIER_PATH_NAME)
    private String prodHierPathName="";

    @SerializedName(TAG_CONVERSION_FACTOR)
    private Integer conversionFactor = 0;

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

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public String getUomDescription() {
        return uomDescription;
    }

    public void setUomDescription(String uomDescription) {
        this.uomDescription = uomDescription;
    }

    public String getBaseUom() {
        return baseUom;
    }

    public void setBaseUom(String baseUom) {
        this.baseUom = baseUom;
    }

    public Integer getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(Integer conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getProdHierPath() {
        return prodHierPath;
    }

    public void setProdHierPath(String prodHierPath) {
        this.prodHierPath = prodHierPath;
    }

    public String getProdHierPathName() {
        return prodHierPathName;
    }

    public void setProdHierPathName(String prodHierPathName) {
        this.prodHierPathName = prodHierPathName;
    }
}
