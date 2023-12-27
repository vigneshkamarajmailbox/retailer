package com.botree.retailerssfa.models;

import java.io.Serializable;

public class SalesmanMasterCheckBoxModel implements Serializable {

    private String mappedName = "";

    private String mappedCode = "";

    private boolean isCheckBoxEnabled = false;

    public String getMappedName() {
        return mappedName;
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    public boolean isCheckBoxEnabled() {
        return isCheckBoxEnabled;
    }

    public void setCheckBoxEnabled(boolean checkBoxEnabled) {
        isCheckBoxEnabled = checkBoxEnabled;
    }

    public String getMappedCode() {
        return mappedCode;
    }

    public void setMappedCode(String mappedCode) {
        this.mappedCode = mappedCode;
    }


}
