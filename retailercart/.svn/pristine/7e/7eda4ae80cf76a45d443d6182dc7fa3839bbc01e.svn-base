/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.models;

import java.math.BigDecimal;

public class StockTakeVO {

    private String prodCode = "";
    private String prodName = "";
    private String prodShortName = "";
    private BigDecimal sellPrice = new BigDecimal(0);
    private BigDecimal mrp = new BigDecimal(0);
    private Integer quantity = 0;
    private String productStatus = "";
    private BigDecimal orderValue = new BigDecimal(0);
    private String prodBatchCode="";
    private BigDecimal sellRateWithTax = new BigDecimal(0);

    public BigDecimal getSellRateWithTax() {
        return sellRateWithTax;
    }

    public void setSellRateWithTax(BigDecimal sellRateWithTax) {
        this.sellRateWithTax = sellRateWithTax;
    }

    public String getProdBatchCode() {
        return prodBatchCode;
    }

    public void setProdBatchCode(String prodBatchCode) {
        this.prodBatchCode = prodBatchCode;
    }


    public String getProdShortName() {
        return prodShortName;
    }

    public void setProdShortName(String prodShortName) {
        this.prodShortName = prodShortName;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice.setScale(6, BigDecimal.ROUND_UNNECESSARY);
    }

    public BigDecimal getMrp() {
        return mrp;
    }

    public void setMrp(BigDecimal mrp) {
        this.mrp = mrp.setScale(6, BigDecimal.ROUND_UNNECESSARY);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public BigDecimal getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(BigDecimal orderValue) {
        this.orderValue = orderValue.setScale(6, BigDecimal.ROUND_UNNECESSARY);
    }

}
