package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CLOSING_STOCK_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_OPENING_STOCK_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_REF_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALESMAN_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TOT_ORDER_VALUE;

public class SubStockistModel {

    @SerializedName(TAG_CMP_CODE)
    private String cmpCode="";

    @SerializedName(TAG_DISTR_CODE)
    private String distCode="";

    @SerializedName(TAG_DISTR_NAME)
    private String distName="";

    @SerializedName(TAG_SALESMAN_CODE)
    private String salesmanCode="";

    @SerializedName(TAG_OPENING_STOCK_VALUE)
    private Double openingStock=0.0;

    @SerializedName(TAG_CLOSING_STOCK_VALUE)
    private Double closingStock=0.0;

    @SerializedName(TAG_TOT_ORDER_VALUE)
    private Double orderValue=0.0;

    @SerializedName(TAG_REF_DATE)
    private String stockDate="";

    public String getStockDate() {
        return stockDate;
    }

    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

    public Double getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(Double openingStock) {
        this.openingStock = openingStock;
    }

    public Double getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(Double closingStock) {
        this.closingStock = closingStock;
    }

    public Double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Double orderValue) {
        this.orderValue = orderValue;
    }
}
