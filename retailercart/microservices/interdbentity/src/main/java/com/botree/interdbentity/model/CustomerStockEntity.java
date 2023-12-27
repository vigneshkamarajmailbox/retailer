package com.botree.interdbentity.model;

/**
 * Customer Stock Entity class.
 * @author vinodkumar.a
 */
public class CustomerStockEntity extends AbstractEntity {

    /** distrCode. */
    private String distrCode;

    /** customerCode. */
    private String customerCode;

    /** stock. */
    private String stock;

    /** prodCode. */
    private String prodCode;

    /** soq. */
    private Integer soq;

    /** ppq. */
    private String ppq;

    /** enableProduct. */
    private String enableProduct = "Y";

    /** productColor. */
    private String productColor;

    /**
     * @return the distrCode
     */
    public final String getDistrCode() {
        return distrCode;
    }

    /**
     * @param distrCodeIn the distrCode to set
     */
    public final void setDistrCode(final String distrCodeIn) {
        distrCode = distrCodeIn;
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
        customerCode = customerCodeIn;
    }

    /**
     * @return the stock
     */
    public final String getStock() {
        return stock;
    }

    /**
     * @param stockIn the stock to set
     */
    public final void setStock(final String stockIn) {
        stock = stockIn;
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
        prodCode = prodCodeIn;
    }

    /**
     * @return the soq
     */
    public final Integer getSoq() {
        return soq;
    }

    /**
     * @param soqIn the soq to set
     */
    public final void setSoq(final Integer soqIn) {
        soq = soqIn;
    }

    /**
     * @return the ppq
     */
    public final String getPpq() {
        return ppq;
    }

    /**
     * @param ppqIn the ppq to set
     */
    public final void setPpq(final String ppqIn) {
        ppq = ppqIn;
    }

    /**
     * @return the enableProduct
     */
    public final String getEnableProduct() {
        return enableProduct;
    }

    /**
     * @param enableProductIn the enableProduct to set
     */
    public final void setEnableProduct(final String enableProductIn) {
        enableProduct = enableProductIn;
    }

    /**
     * @return the productColor
     */
    public final String getProductColor() {
        return productColor;
    }

    /**
     * @param productColorIn the productColor to set
     */
    public final void setProductColor(final String productColorIn) {
        productColor = productColorIn;
    }
}
