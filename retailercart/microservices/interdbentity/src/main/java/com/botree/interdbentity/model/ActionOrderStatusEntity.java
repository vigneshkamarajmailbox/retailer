package com.botree.interdbentity.model;

import java.util.Date;

/**
 * Class contains the order status detail fields in action api.
 * @author vinodkumar.a
 */
public class ActionOrderStatusEntity extends AbstractEntity {

    /** distrCode. */
    private String distrCode;

    /** orderNo. */
    private String orderNo;

    /** orderStatus. */
    private String orderStatus;

    /** actionTime. */
    private Date actionTime;

    /** freeText. */
    private String freeText;

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
     * @return the orderStatus
     */
    public final String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatusIn the orderStatus to set
     */
    public final void setOrderStatus(final String orderStatusIn) {
        orderStatus = orderStatusIn;
    }

    /**
     * @return the actionTime
     */
    public final Date getActionTime() {
        return actionTime;
    }

    /**
     * @param actionTimeIn the actionTime to set
     */
    public final void setActionTime(final Date actionTimeIn) {
        actionTime = actionTimeIn;
    }

    /**
     * @return the freeText
     */
    public final String getFreeText() {
        return freeText;
    }

    /**
     * @param freeTextIn the freeText to set
     */
    public final void setFreeText(final String freeTextIn) {
        freeText = freeTextIn;
    }

    /**
     * Method to for the order key.
     * @return key
     */
    public String orderKey() {
        return this.getCmpCode() + this.getDistrCode() + this.getOrderNo();
    }
}
