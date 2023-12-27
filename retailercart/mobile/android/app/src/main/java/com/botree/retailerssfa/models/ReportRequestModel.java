package com.botree.retailerssfa.models;

public class ReportRequestModel {

    private String id;
    private String type;
    private Boolean isLastLevel;
    private String hierLevel;
    private String distrCode;
    private String userCode;

    public ReportRequestModel(String id, String type, Boolean isLastLevel, String hierLevel, String distrCode, String userCode) {

        this.id = id;
        this.type = type;
        this.isLastLevel = isLastLevel;
        this.hierLevel = hierLevel;
        this.distrCode = distrCode;
        this.userCode = userCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getLastLevel() {
        return isLastLevel;
    }

    public void setLastLevel(Boolean lastLevel) {
        isLastLevel = lastLevel;
    }

    public String getHierLevel() {
        return hierLevel;
    }

    public void setHierLevel(String hierLevel) {
        this.hierLevel = hierLevel;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
