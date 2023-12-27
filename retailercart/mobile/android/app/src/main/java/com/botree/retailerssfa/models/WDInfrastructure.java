package com.botree.retailerssfa.models;

public class WDInfrastructure {
    private String cmpCode;
    private String distrCode;

    private String wetsqft = "";
    private String frozensqft = "";
    private String modDt = "";

    private String freezerCapacity = "";
    private String unit = "";
    private String totalCapacity = "";

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getWetsqft() {
        return wetsqft;
    }

    public void setWetsqft(String wetsqft) {
        this.wetsqft = wetsqft;
    }

    public String getFrozensqft() {
        return frozensqft;
    }

    public void setFrozensqft(String frozensqft) {
        this.frozensqft = frozensqft;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    public String getFreezerCapacity() {
        return freezerCapacity;
    }

    public void setFreezerCapacity(String freezerCapacity) {
        this.freezerCapacity = freezerCapacity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(String totalCapacity) {
        this.totalCapacity = totalCapacity;
    }
}
