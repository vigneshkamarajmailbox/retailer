package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CASH_DISCOUNT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DB_DISCOUNT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INPUT_APPLY_ON;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INPUT_TAX_PERC;
import static com.botree.retailerssfa.service.JSONConstants.TAG_OUTPUT_APPLY_ON;
import static com.botree.retailerssfa.service.JSONConstants.TAG_OUTPUT_TAX_PERC;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_PATH;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_PATH_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_REDUCE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TAX_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TAX_EFFECTIVE_FROM;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TAX_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TAX_STATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TAX_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UPLOAD_FLAG;
import static com.botree.retailerssfa.service.JSONConstants.TTAG_AX_DESC;

/**
 * Created by vinothbaskaran on 5/3/18.
 */

public class TaxModel {

    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";

    @SerializedName(TAG_DISTR_CODE)
    private String distrCode = "";

    @SerializedName(TAG_PROD_CODE)
    private String prodCode = "";

    @SerializedName(TAG_TAX_STATE)
    private String taxState = "";

    @SerializedName(TAG_TAX_TYPE)
    private String taxType = "";

    @SerializedName(TAG_TAX_CODE)
    private String taxCode = "";

    @SerializedName(TTAG_AX_DESC)
    private String taxDesc = "";

    @SerializedName(TAG_TAX_NAME)
    private String taxName = "";

    @SerializedName(TAG_TAX_EFFECTIVE_FROM)
    private String taxEffectiveFrom = "";

    @SerializedName(TAG_SCHEME_REDUCE)
    private String schemeReduce = "";

    @SerializedName(TAG_CASH_DISCOUNT)
    private String cashDiscount = "";

    @SerializedName(TAG_DB_DISCOUNT)
    private String dbDiscount = "";

    @SerializedName(TAG_INPUT_TAX_PERC)
    private double inputTaxPerc = 0;

    @SerializedName(TAG_INPUT_APPLY_ON)
    private String inputApplyOn = "";

    @SerializedName(TAG_OUTPUT_TAX_PERC)
    private double outputTaxPerc = 0;

    @SerializedName(TAG_OUTPUT_APPLY_ON)
    private String outputAppluOn = "";

    @SerializedName(TAG_UPLOAD_FLAG)
    private String uploadFlag = "";

    @SerializedName(TAG_PROD_HIER_PATH)
    private String prodHierPath = "";

    @SerializedName(TAG_PROD_HIER_PATH_NAME)
    private String prodHierPathName = "";

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

    public String getTaxState() {
        return taxState;
    }

    public void setTaxState(String taxState) {
        this.taxState = taxState;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getTaxDesc() {
        return taxDesc;
    }

    public void setTaxDesc(String taxDesc) {
        this.taxDesc = taxDesc;
    }

    public String getTaxEffectiveFrom() {
        return taxEffectiveFrom;
    }

    public void setTaxEffectiveFrom(String taxEffectiveFrom) {
        this.taxEffectiveFrom = taxEffectiveFrom;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getSchemeReduce() {
        return schemeReduce;
    }

    public void setSchemeReduce(String schemeReduce) {
        this.schemeReduce = schemeReduce;
    }

    public String getCashDiscount() {
        return cashDiscount;
    }

    public void setCashDiscount(String cashDiscount) {
        this.cashDiscount = cashDiscount;
    }

    public String getDbDiscount() {
        return dbDiscount;
    }

    public void setDbDiscount(String dbDiscount) {
        this.dbDiscount = dbDiscount;
    }

    public double getInputTaxPerc() {
        return inputTaxPerc;
    }

    public void setInputTaxPerc(double inputTaxPerc) {
        this.inputTaxPerc = inputTaxPerc;
    }

    public String getInputApplyOn() {
        return inputApplyOn;
    }

    public void setInputApplyOn(String inputApplyOn) {
        this.inputApplyOn = inputApplyOn;
    }

    public double getOutputTaxPerc() {
        return outputTaxPerc;
    }

    public void setOutputTaxPerc(double outputTaxPerc) {
        this.outputTaxPerc = outputTaxPerc;
    }

    public String getOutputAppluOn() {
        return outputAppluOn;
    }

    public void setOutputAppluOn(String outputAppluOn) {
        this.outputAppluOn = outputAppluOn;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
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
