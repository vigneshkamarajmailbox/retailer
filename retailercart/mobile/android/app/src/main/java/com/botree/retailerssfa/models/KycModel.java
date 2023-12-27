package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_KYC_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_KYC_DESC;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UPLOAD_FLAG;

public class KycModel {

    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";

    @SerializedName(TAG_KYC_CODE)
    private String kycCode = "";

    @SerializedName(TAG_KYC_DESC)
    private String kycDesc = "";

    @SerializedName(TAG_UPLOAD_FLAG)
    private String upload = "";

    public String getKycCode() {
        return kycCode;
    }

    public void setKycCode(String kycCode) {
        this.kycCode = kycCode;
    }

    public String getKycDesc() {
        return kycDesc;
    }

    public void setKycDesc(String kycDesc) {
        this.kycDesc = kycDesc;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }
}
