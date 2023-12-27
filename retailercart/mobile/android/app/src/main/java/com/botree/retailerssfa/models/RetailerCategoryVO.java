/*
 * Copyright (C) 2016 Botree Software International Private Limited
 */

package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CHANNEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CHANNEL_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CLASS_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CLASS_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GROUP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GROUP_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUB_CHANNEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUB_CHANNEL_NAME;

public class RetailerCategoryVO {
    private String noOutlets = "";
    private String noOrders = "";
    private String noBills = "";

    @SerializedName(TAG_CMP_CODE)
    private String strCmpCode = "";
    private String strDistrCode = "";

    @SerializedName(TAG_CHANNEL_NAME)
    private String strChannelName = "";

    @SerializedName(TAG_CHANNEL_CODE)
    private String strChannelCode = "";

    @SerializedName(TAG_SUB_CHANNEL_NAME)
    private String strSubChannelName = "";

    @SerializedName(TAG_SUB_CHANNEL_CODE)
    private String strSubChannelCode = "";

    @SerializedName(TAG_GROUP_NAME)
    private String strGroupName = "";

    @SerializedName(TAG_GROUP_CODE)
    private String strGroupCode = "";

    @SerializedName(TAG_CLASS_CODE)
    private String strClassCode = "";

    @SerializedName(TAG_CLASS_NAME)
    private String strClassName = "";
    private boolean isExpand = false;
    private Double channelAmount = 0.0;

    public String getNoOutlets() {
        return noOutlets;
    }

    public void setNoOutlets(String noOutlets) {
        this.noOutlets = noOutlets;
    }

    public String getNoOrders() {
        return noOrders;
    }

    public void setNoOrders(String noOrders) {
        this.noOrders = noOrders;
    }

    public String getNoBills() {
        return noBills;
    }

    public void setNoBills(String noBills) {
        this.noBills = noBills;
    }

    public Double getChannelAmount() {
        return channelAmount;
    }

    public void setChannelAmount(Double channelAmount) {
        this.channelAmount = channelAmount;
    }


    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    /**
     * @return the strChannelName
     */
    public String getStrChannelName() {
        return strChannelName;
    }

    /**
     * @param strChannelName the strChannelName to set
     */
    public void setStrChannelName(String strChannelName) {
        this.strChannelName = strChannelName;
    }

    /**
     * @return the strChannelCode
     */
    public String getStrChannelCode() {
        return strChannelCode;
    }

    /**
     * @param strChannelCode the strChannelCode to set
     */
    public void setStrChannelCode(String strChannelCode) {
        this.strChannelCode = strChannelCode;
    }

    /**
     * @return the strSubChannelName
     */
    public String getStrSubChannelName() {
        return strSubChannelName;
    }

    /**
     * @param strSubChannelName the strSubChannelName to set
     */
    public void setStrSubChannelName(String strSubChannelName) {
        this.strSubChannelName = strSubChannelName;
    }

    /**
     * @return the strSubChannelCode
     */
    public String getStrSubChannelCode() {
        return strSubChannelCode;
    }

    /**
     * @param strSubChannelCode the strSubChannelCode to set
     */
    public void setStrSubChannelCode(String strSubChannelCode) {
        this.strSubChannelCode = strSubChannelCode;
    }

    /**
     * @return the strGroupName
     */
    public String getStrGroupName() {
        return strGroupName;
    }

    /**
     * @param strGroupName the strGroupName to set
     */
    public void setStrGroupName(String strGroupName) {
        this.strGroupName = strGroupName;
    }

    /**
     * @return the strGroupCode
     */
    public String getStrGroupCode() {
        return strGroupCode;
    }

    /**
     * @param strGroupCode the strGroupCode to set
     */
    public void setStrGroupCode(String strGroupCode) {
        this.strGroupCode = strGroupCode;
    }

    public String getStrCmpCode() {
        return strCmpCode;
    }

    public void setStrCmpCode(String strCmpCode) {
        this.strCmpCode = strCmpCode;
    }

    public String getStrDistrCode() {
        return strDistrCode;
    }

    public void setStrDistrCode(String strDistrCode) {
        this.strDistrCode = strDistrCode;
    }

    public String getStrClassCode() {
        return strClassCode;
    }

    public void setStrClassCode(String strClassCode) {
        this.strClassCode = strClassCode;
    }

    public String getStrClassName() {
        return strClassName;
    }

    public void setStrClassName(String strClassName) {
        this.strClassName = strClassName;
    }

}
