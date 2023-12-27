package com.botree.retailerssfa.models;

/**
 * Created by shantarao on 12/3/18.
 */

public class FreeProdModel {

    private String prodCode="";
    private String freeProdCode="";
    private String freeQty="";
    private String freeProdName="";
    private String freeProdMRP="";

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getFreeProdCode() {
        return freeProdCode;
    }

    public void setFreeProdCode(String freeProdCode) {
        this.freeProdCode = freeProdCode;
    }

    public String getFreeQty() {
        return freeQty;
    }

    public void setFreeQty(String freeQty) {
        this.freeQty = freeQty;
    }

    public String getFreeProdName() {
        return freeProdName;
    }

    public void setFreeProdName(String freeProdName) {
        this.freeProdName = freeProdName;
    }

    public String getFreeProdMRP() {
        return freeProdMRP;
    }

    public void setFreeProdMRP(String freeProdMRP) {
        this.freeProdMRP = freeProdMRP;
    }
}
