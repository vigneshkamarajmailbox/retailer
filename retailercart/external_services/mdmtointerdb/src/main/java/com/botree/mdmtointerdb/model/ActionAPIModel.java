package com.botree.mdmtointerdb.model;

import java.util.Date;

/**
 *
 */
public class ActionAPIModel {
    /**
     * cmpCode.
     **/
    private String cmpCode;
    /**
     * distrCode.
     **/
    private String distrCode;
    /**
     * actionTime.
     **/
    private Date actionTime;
    /**
     * actionCode.
     **/
    private String actionCode;
    /**
     * actionTemplate.
     **/
    private String actionTemplate;

    /**
     * @return the cmpCode
     */
    public final String getCmpCode() {
        return cmpCode;
    }

    /**
     * @param cmpCodeIn the cmpCode to set
     */
    public final void setCmpCode(final String cmpCodeIn) {
        cmpCode = cmpCodeIn;
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
    public Date getActionTime() {
        return actionTime;
    }

    /**
     * @param actionTimeIn the actionTime to set
     */
    public void setActionTime(final Date actionTimeIn) {
        this.actionTime = actionTimeIn;
    }

    /**
     * @return the actionCode
     */
    public String getActionCode() {
        return actionCode;
    }

    /**
     * @param actionCodeIn the actionCode to set
     */
    public void setActionCode(final String actionCodeIn) {
        this.actionCode = actionCodeIn;
    }

    /**
     * @return the actionTemplate
     */
    public String getActionTemplate() {
        return actionTemplate;
    }

    /**
     * @param actionTemplateIn the actionTemplate to set
     */
    public void setActionTemplate(final String actionTemplateIn) {
       this.actionTemplate = actionTemplateIn;
    }
}
