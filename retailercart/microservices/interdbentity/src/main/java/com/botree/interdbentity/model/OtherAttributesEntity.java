package com.botree.interdbentity.model;

/**
 * Class contains the Other Attributes fields.
 * @author vinodkumar.a
 */
public class OtherAttributesEntity extends AbstractEntity {

    /** distrCode. */
    private String distrCode;

    /** attributeCode. */
    private String attributeCode;

    /** attributeValueCode. */
    private String attributeValueCode;

    /** attrInputValues. */
    private String attrInputValues;

    /** refNo. */
    private String refNo;

    /** sNo. */
    private int sNo;

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
        this.distrCode = distrCodeIn;
    }

    /**
     * @return the attributeCode
     */
    public final String getAttributeCode() {
        return attributeCode;
    }

    /**
     * @param attributeCodeIn the attributeCode to set
     */
    public final void setAttributeCode(final String attributeCodeIn) {
        this.attributeCode = attributeCodeIn;
    }

    /**
     * @return the attributeValueCode
     */
    public final String getAttributeValueCode() {
        return attributeValueCode;
    }

    /**
     * @param attributeValueCodeIn the attributeValueCode to set
     */
    public final void setAttributeValueCode(final String attributeValueCodeIn) {
        this.attributeValueCode = attributeValueCodeIn;
    }

    /**
     * @return the attrInputValues
     */
    public final String getAttrInputValues() {
        return attrInputValues;
    }

    /**
     * @param attrInputValuesIn the attrInputValues to set
     */
    public final void setAttrInputValues(final String attrInputValuesIn) {
        this.attrInputValues = attrInputValuesIn;
    }

    /**
     * @return the refNo
     */
    public final String getRefNo() {
        return refNo;
    }

    /**
     * @param refNoIn the refNo to set
     */
    public final void setRefNo(final String refNoIn) {
        this.refNo = refNoIn;
    }

    /**
     * @return the sNo
     */
    public final int getsNo() {
        return sNo;
    }

    /**
     * @param sNoIn the sNo to set
     */
    public final void setsNo(final int sNoIn) {
        this.sNo = sNoIn;
    }
}
