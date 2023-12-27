package com.botree.interdbentity.model;

/**
 * Class contains the order booking service detail fields.
 * @author vinodkumar.a
 */
public class OrderBookingServiceDetailsEntity extends AbstractEntity {

    /** distrCode. */
    private String distrCode;

    /** orderNo. */
    private String orderNo;

    /** invoiceNo. */
    private String invoiceNo;

    /** prodCode. */
    private String prodCode;

    /** prodName. */
    private String prodName;

    /** prodBatchCode. */
    private String prodBatchCode;

    /** sellRate. */
    private Double sellRate;

    /** soqQty. */
    private Integer soqQty;

    /** soqValue. */
    private Double soqValue;

    /** orderQty. */
    private Integer orderQty;

    /** orderValue. */
    private Double orderValue;

    /** servicedQty. */
    private Integer servicedQty;

    /** servicedValue. */
    private Double servicedValue;

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
     * @return the orderNo
     */
    public final String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNoIn the orderNo to set
     */
    public final void setOrderNo(final String orderNoIn) {
        orderNo = orderNoIn;
    }

    /**
     * @return the invoiceNo
     */
    public final String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * @param invoiceNoIn the invoiceNo to set
     */
    public final void setInvoiceNo(final String invoiceNoIn) {
        invoiceNo = invoiceNoIn;
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
     * @return the prodName
     */
    public final String getProdName() {
        return prodName;
    }

    /**
     * @param prodNameIn the prodName to set
     */
    public final void setProdName(final String prodNameIn) {
        prodName = prodNameIn;
    }

    /**
     * @return the prodBatchCode
     */
    public final String getProdBatchCode() {
        return prodBatchCode;
    }

    /**
     * @param prodBatchCodeIn the prodBatchCode to set
     */
    public final void setProdBatchCode(final String prodBatchCodeIn) {
        prodBatchCode = prodBatchCodeIn;
    }

    /**
     * @return the sellRate
     */
    public final Double getSellRate() {
        return sellRate;
    }

    /**
     * @param sellRateIn the sellRate to set
     */
    public final void setSellRate(final Double sellRateIn) {
        sellRate = sellRateIn;
    }

    /**
     * @return the soqQty
     */
    public final Integer getSoqQty() {
        return soqQty;
    }

    /**
     * @param soqQtyIn the soqQty to set
     */
    public final void setSoqQty(final Integer soqQtyIn) {
        soqQty = soqQtyIn;
    }

    /**
     * @return the soqValue
     */
    public final Double getSoqValue() {
        return soqValue;
    }

    /**
     * @param soqValueIn the soqValue to set
     */
    public final void setSoqValue(final Double soqValueIn) {
        soqValue = soqValueIn;
    }

    /**
     * @return the orderQty
     */
    public final Integer getOrderQty() {
        return orderQty;
    }

    /**
     * @param orderQtyIn the orderQty to set
     */
    public final void setOrderQty(final Integer orderQtyIn) {
        orderQty = orderQtyIn;
    }

    /**
     * @return the orderValue
     */
    public final Double getOrderValue() {
        return orderValue;
    }

    /**
     * @param orderValueIn the orderValue to set
     */
    public final void setOrderValue(final Double orderValueIn) {
        orderValue = orderValueIn;
    }

    /**
     * @return the servicedQty
     */
    public final Integer getServicedQty() {
        return servicedQty;
    }

    /**
     * @param servicedQtyIn the servicedQty to set
     */
    public final void setServicedQty(final Integer servicedQtyIn) {
        servicedQty = servicedQtyIn;
    }

    /**
     * @return the servicedValue
     */
    public final Double getServicedValue() {
        return servicedValue;
    }

    /**
     * @param servicedValueIn the servicedValue to set
     */
    public final void setServicedValue(final Double servicedValueIn) {
        servicedValue = servicedValueIn;
    }
}
