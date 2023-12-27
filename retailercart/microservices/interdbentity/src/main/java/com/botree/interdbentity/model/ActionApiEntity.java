package com.botree.interdbentity.model;

import java.util.Date;

/**
 * Class contains the action api related fields.
 * @author vinodkumar.a
 */
public class ActionApiEntity extends AbstractEntity {

    /** syncNo. */
    private Long syncNo;

    /** distrCode. */
    private String distrCode;

    /** actionTime. */
    private Date actionTime;

    /** actionCode. */
    private String actionCode;

    /** actionTemplate. */
    private String actionTemplate;

    /** processedFlag. */
    private String processedFlag;

    /**
     * @return the syncNo
     */
    public final Long getSyncNo() {
        return syncNo;
    }

    /**
     * @param syncNoIn the syncNo to set
     */
    public final void setSyncNo(final Long syncNoIn) {
        syncNo = syncNoIn;
    }

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
     * @return the actionCode
     */
    public final String getActionCode() {
        return actionCode;
    }

    /**
     * @param actionCodeIn the actionCode to set
     */
    public final void setActionCode(final String actionCodeIn) {
        actionCode = actionCodeIn;
    }

    /**
     * @return the actionTemplate
     */
    public final String getActionTemplate() {
        return actionTemplate;
    }

    /**
     * @param actionTemplateIn the actionTemplate to set
     */
    public final void setActionTemplate(final String actionTemplateIn) {
        actionTemplate = actionTemplateIn;
    }

    /**
     * @return the processedFlag
     */
    public final String getProcessedFlag() {
        return processedFlag;
    }

    /**
     * @param processedFlagIn the processedFlag to set
     */
    public final void setProcessedFlag(final String processedFlagIn) {
        processedFlag = processedFlagIn;
    }
}
