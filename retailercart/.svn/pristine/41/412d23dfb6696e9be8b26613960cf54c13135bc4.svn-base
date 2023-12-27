/*
 * Copyright (C) 2016 Botree Software International Private Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DEFAULT_UOM_ID;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_SALESMAN_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_KG_RATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LOAD_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MRP;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PRIMARY_DISC;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_BATCH_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_BRAND_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_BRAND_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_CATEGORY_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_CATEGORY_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_LEVEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_PATH;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_PATH_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_VALUE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_NET_WGT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_PURCHASE_PRICE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_SHORT_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_WGT_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SELL_PRICE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SOQ;
import static com.botree.retailerssfa.service.JSONConstants.TAG_STOCK_IN_HAND;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UOM;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UOM_CODE;

public class ProductsVO {

    @SerializedName(TAG_CMP_CODE)
    private String cmpCodeCaps = "";

    @SerializedName(TAG_DISTR_SALESMAN_CODE)
    private String salesmanCodeCaps = "";

    @SerializedName(TAG_DISTR_CODE)
    private String distrCodeCaps = "";

    @SerializedName(TAG_DISTR_NAME)
    private String distrName = "";

    private String noOfDays;

    private String lastBillDate;

    @SerializedName(TAG_PROD_CODE)
    private String prodCodeCaps = "";

    @SerializedName(TAG_PROD_HIER_LEVEL_CODE)
    private String prodHierValCodeCaps = "";

    @SerializedName(TAG_PROD_HIER_VALUE_CODE)
    private String prodHierValNameCaps = "";

    @SerializedName(TAG_PROD_HIER_PATH)
    private String prodHierPath = "";

    @SerializedName(TAG_PROD_NAME)
    private String prodNameCaps = "";

    @SerializedName(TAG_PROD_SHORT_NAME)
    private String prodShortNameCaps = "";

    @SerializedName(TAG_PROD_HIER_PATH_NAME)
    private String prodHierPathName = "";

    @SerializedName(TAG_SELL_PRICE)
    private Double sellPriceCaps = 0d;

    @SerializedName(TAG_MRP)
    private Double mRPCaps = 0d;

    @SerializedName(TAG_PRIMARY_DISC)
    private Double primaryDisc = 0d;

    @SerializedName(TAG_PROD_PURCHASE_PRICE)
    private Double purchPrice = 0d;

    @SerializedName(TAG_STOCK_IN_HAND)
    private Double stockInHandCaps = 0d;

    @SerializedName(value = TAG_UOM, alternate = {TAG_DEFAULT_UOM_ID,TAG_UOM_CODE})
    private String defaultUomidCaps = "";

    @SerializedName(TAG_CUSTOMER_CODE)
    private String retlrCodeCaps = "";

    @SerializedName(TAG_SOQ)
    private Integer suggestedQuantityCaps = 0;

    @SerializedName(TAG_PROD_BATCH_CODE)
    private String prodBatchCode = "";

    @SerializedName(TAG_PROD_NET_WGT)
    private String prodNetWgt = "";

    @SerializedName(TAG_PROD_WGT_TYPE)
    private String prodWgtType = "";

    @SerializedName(TAG_PROD_CATEGORY_CODE)
    private String categoryCode = "";

    @SerializedName(TAG_PROD_CATEGORY_NAME)
    private String categoryName = "";

    @SerializedName(TAG_PROD_BRAND_CODE)
    private String brandCode = "";

    @SerializedName(TAG_PROD_BRAND_NAME)
    private String brandName = "";
    /* Product category*/
    private String categoryCaps = "";

    @SerializedName(TAG_LOAD_QTY)
    private Integer loadQty = 0;

    @SerializedName(TAG_KG_RATE)
    private double kgRate = 0;

    public double getKgRate() {
        return kgRate;
    }

    public void setKgRate(double kgRate) {
        this.kgRate = kgRate;
    }

    public Integer getLoadQty() {
        return loadQty;
    }

    public void setLoadQty(Integer loadQty) {
        this.loadQty = loadQty;
    }

    /* Product Scheme */
    private String schemeCodeCaps = "";
    private boolean isExpand = false;
    private boolean containsMultipleCategory = false;
    private List<String> categoryList;
    private boolean isCatalogAvailable = false;
    private String catalogFileName = "";

    public String getDistrName() {
        return distrName;
    }

    public void setDistrName(String distrName) {
        this.distrName = distrName;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getLastBillDate() {
        return lastBillDate;
    }

    public void setLastBillDate(String lastBillDate) {
        this.lastBillDate = lastBillDate;
    }

    public boolean isCatalogAvailable() {
        return isCatalogAvailable;
    }

    public void setCatalogAvailable(boolean catalogAvailable, String catalogFileName) {
        isCatalogAvailable = catalogAvailable;
        this.catalogFileName = catalogFileName;
    }

    public String getCatalogFileName() {
        return catalogFileName;
    }

    public String getProdHierPath() {
        return prodHierPath;
    }

    public void setProdHierPath(String prodHierPath) {
        this.prodHierPath = prodHierPath;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProdWgtType() {
        return prodWgtType;
    }

    public void setProdWgtType(String prodWgtType) {
        this.prodWgtType = prodWgtType;
    }

    public String getProdNetWgt() {
        return prodNetWgt;
    }

    public void setProdNetWgt(String prodNetWgt) {
        this.prodNetWgt = prodNetWgt;
    }

    public Double getPurchPrice() {
        return purchPrice;
    }

    public void setPurchPrice(Double purchPrice) {
        this.purchPrice = purchPrice;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public boolean isContainsMultipleCategory() {
        return containsMultipleCategory;
    }

    public void setContainsMultipleCategory(boolean containsMultipleCategory) {
        this.containsMultipleCategory = containsMultipleCategory;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public String getProdBatchCode() {
        return prodBatchCode;
    }

    public void setProdBatchCode(String prodBatchCode) {
        this.prodBatchCode = prodBatchCode;
    }

    public String getCategoryCaps() {
        return categoryCaps;
    }

    public void setCategoryCaps(String categoryCaps) {
        this.categoryCaps = categoryCaps;
    }

    public String getRetlrCodeCaps() {
        return retlrCodeCaps;
    }


    public Integer getSuggestedQuantityCaps() {
        return suggestedQuantityCaps;
    }


    public String getSalesmanCodeCaps() {
        return salesmanCodeCaps;
    }

    public void setSalesmanCodeCaps(String salesmanCodeCaps) {
        this.salesmanCodeCaps = salesmanCodeCaps;
    }

    public String getDistrCodeCaps() {
        return distrCodeCaps;
    }

    public void setDistrCodeCaps(String distrCodeCaps) {
        this.distrCodeCaps = distrCodeCaps;
    }

    public String getCmpCodeCaps() {
        return cmpCodeCaps;
    }

    public void setCmpCodeCaps(String cmpCodeCaps) {
        this.cmpCodeCaps = cmpCodeCaps;
    }

    public String getProdCodeCaps() {
        return prodCodeCaps;
    }

    public void setProdCodeCaps(String prodCodeCaps) {
        this.prodCodeCaps = prodCodeCaps;
    }

    public String getProdHierValCodeCaps() {
        return prodHierValCodeCaps;
    }

    public void setProdHierValCodeCaps(String prodHierValCodeCaps) {
        this.prodHierValCodeCaps = prodHierValCodeCaps;
    }

    public String getProdHierValNameCaps() {
        return prodHierValNameCaps;
    }

    public void setProdHierValNameCaps(String prodHierValNameCaps) {
        this.prodHierValNameCaps = prodHierValNameCaps;
    }

    public String getProdNameCaps() {
        return prodNameCaps;
    }

    public void setProdNameCaps(String prodNameCaps) {
        this.prodNameCaps = prodNameCaps;
    }

    public String getProdShortNameCaps() {
        return prodShortNameCaps;
    }

    public void setProdShortNameCaps(String prodShortNameCaps) {
        this.prodShortNameCaps = prodShortNameCaps;
    }

    public Double getSellPriceCaps() {
        return sellPriceCaps;
    }

    public void setSellPriceCaps(Double sellPriceCaps) {
        this.sellPriceCaps = sellPriceCaps;
    }

    public Double getmRPCaps() {
        return mRPCaps;
    }

    public void setmRPCaps(Double mRPCaps) {
        this.mRPCaps = mRPCaps;
    }

    public Double getStockInHandCaps() {
        return stockInHandCaps;
    }

    public void setStockInHandCaps(Double stockInHandCaps) {
        this.stockInHandCaps = stockInHandCaps;
    }

    public String getDefaultUomidCaps() {
        return defaultUomidCaps;
    }

    public void setDefaultUomidCaps(String defaultUomidCaps) {
        this.defaultUomidCaps = defaultUomidCaps;
    }


    public String getSchemeCodeCaps() {
        return schemeCodeCaps;
    }

    public void setSchemeCodeCaps(String schemeCodeCaps) {
        this.schemeCodeCaps = schemeCodeCaps;
    }

    public String getProdHierPathName() {
        return prodHierPathName;
    }

    public void setProdHierPathName(String prodHierPathName) {
        this.prodHierPathName = prodHierPathName;
    }

    public Double getPrimaryDisc() {
        return primaryDisc;
    }

    public void setPrimaryDisc(Double primaryDisc) {
        this.primaryDisc = primaryDisc;
    }
}
