/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TABLE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TABLE_DESC;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TABLE_NAME;

/**
 * Created by shantarao on 14/10/16.
 */
public class ConfigModel {

    @SerializedName(TAG_TABLE_NAME)
    private String tableName = "";

    @SerializedName(TAG_TABLE_CODE)
    private String code = "";

    @SerializedName(TAG_TABLE_DESC)
    private String description = "";

    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";

    private String userCode = "";

    public ConfigModel(String userCode, String screenName, String value) {
        this.userCode = userCode;
        this.tableName = screenName;
        this.code = value;
    }

    public ConfigModel() {

    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
