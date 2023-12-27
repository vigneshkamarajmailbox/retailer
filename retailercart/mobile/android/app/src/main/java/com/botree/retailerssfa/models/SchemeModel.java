package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import static com.botree.retailerssfa.service.JSONConstants.TAG_BUDGET_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CHANNEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CLASS_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_COMBI;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_END_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_FOR_EVERY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GROUP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ISMANDATORY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_OF_INVOICE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PAYOUT_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_BASE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_DESC;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_DESCRIPTION;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_FREE_APPLIED;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_FROM_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_MIN_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_PAYOUT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_SLAB_FROM;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_SLAB_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_SLAB_RANGE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_SLAB_TO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_TO_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SCHEME_UTILIZED;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SLNO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_START_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUB_CHANNEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TRENDS_REPORT_WEEKS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UOM_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UPLOAD_FLAG;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UTILIZED_AMT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UTILIZED_END_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UTILIZED_START_DATE;

/**
 * Created by shantarao on 6/2/17.
 */

public class SchemeModel implements Parcelable {

    private String cmpCode = "";
    private String distrCode = "";
    private String readableInvNo = "";

    @SerializedName(TAG_CUSTOMER_CODE)
    private String customerCode = "";

    @SerializedName(TAG_CUSTOMER_NAME)
    private String customerName = "";

    @SerializedName(TAG_PROD_CODE)
    private String productCode = "";

    private String prodName = "";

    @SerializedName(TAG_SCHEME_CODE)
    private String schemeCode = "";

    @SerializedName(TAG_SCHEME_BASE)
    private String schemeBase = "";

    @SerializedName(TAG_SCHEME_FROM_DATE)
    private String schemeFromDt = "";

    @SerializedName(TAG_SCHEME_TO_DATE)
    private String schemeToDt = "";

    @SerializedName(TAG_SCHEME_SLAB_NO)
    private String slabNo = "";

    @SerializedName(TAG_SCHEME_SLAB_FROM)
    private Double slabFrom = 0.0;

    @SerializedName(TAG_SCHEME_SLAB_TO)
    private Double slabTo = 0.0;

    @SerializedName(TAG_SCHEME_SLAB_RANGE)
    private String slabRange = "";

    @SerializedName(TAG_SCHEME_NAME)
    private String schemeName = "";

    @SerializedName(TAG_SCHEME_PAYOUT)
    private Double payoutValue = 0.0d;

    @SerializedName(TAG_PAYOUT_TYPE)
    private String payoutType = "";

    @SerializedName(TAG_START_DATE)
    private String startDate = "";

    @SerializedName(TAG_END_DATE)
    private String endDate = "";

    @SerializedName(TAG_BUDGET_TYPE)
    private String budgetType = "";

    private String claimable = "";

    @SerializedName(TAG_SLNO)
    private Integer slno = 0;

    @SerializedName(TAG_SCHEME_TYPE)
    private String schemeType = "";

    private Boolean isSelected = false;

    @SerializedName(value = TAG_SCHEME_DESC, alternate = {TAG_SCHEME_DESCRIPTION})
    private String schemeDescription = "";

    @SerializedName(TAG_SCHEME_UTILIZED)
    private Double schemeUtilize = 0.0d;

    private String isSchemeUtilize = "";

    @SerializedName(TAG_CHANNEL_CODE)
    private String channelCode = "";

    @SerializedName(TAG_SUB_CHANNEL_CODE)
    private String subChannelCode = "";

    @SerializedName(TAG_GROUP_CODE)
    private String groupCode = "";

    @SerializedName(TAG_CLASS_CODE)
    private String classCode = "";

    private double flatAmount = 0.0;

    @SerializedName(TAG_TRENDS_REPORT_WEEKS)
    private String reportWeek = "";

    @SerializedName(TAG_UTILIZED_AMT)
    private double schemeUtilizeAmt = 0.0;

    @SerializedName(TAG_UTILIZED_START_DATE)
    private String utilizeStartDate = "";

    @SerializedName(TAG_UTILIZED_END_DATE)
    private String utilizedEndDate = "";

    private Integer percentage = 0;

    @SerializedName(TAG_NO_OF_INVOICE)
    private Integer noOfInvoice = 0;

    private Integer freeQty = 0;

    private String freeProdCode = "";
    private String freeProdBatchCode = "";

    @SerializedName(TAG_UPLOAD_FLAG)
    private String uploadFlag = "";

    @SerializedName(TAG_QTY)
    private int quantity;

    @SerializedName(TAG_ISMANDATORY)
    private String isMandatory = "";

    private String freeProdName = "";

    private String invoiceNo = "";

    private String dicountAmount = "";

    private String dicountPercentage = "";

    @SerializedName(TAG_UOM_CODE)
    private String uom = "";

    @SerializedName(TAG_FOR_EVERY)
    private Double forEvery = 0d;

    @SerializedName(TAG_COMBI)
    private String combi = "N";

    @SerializedName(TAG_SCHEME_MIN_VALUE)
    private Integer minValue = 0;

    @SerializedName(TAG_SCHEME_FREE_APPLIED)
    private String isFreeApplied = "";
    private String isSkuLevel;
    private String isActive;

    BigDecimal sellPrice;
    BigDecimal mrp;

    private String prodBatchCode = "";

    public SchemeModel() {

    }

    public SchemeModel(String schemeDesc, String slabNo, double primaryDiscValue) {
        this.schemeDescription = schemeDesc;
        this.slabNo = slabNo;
        this.flatAmount = primaryDiscValue;
    }

    protected SchemeModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        customerCode = in.readString();
        customerName = in.readString();
        productCode = in.readString();
        prodName = in.readString();
        schemeCode = in.readString();
        schemeBase = in.readString();
        schemeFromDt = in.readString();
        schemeToDt = in.readString();
        slabNo = in.readString();
        if (in.readByte() == 0) {
            slabFrom = null;
        } else {
            slabFrom = in.readDouble();
        }
        if (in.readByte() == 0) {
            slabTo = null;
        } else {
            slabTo = in.readDouble();
        }
        slabRange = in.readString();
        schemeName = in.readString();
        if (in.readByte() == 0) {
            payoutValue = null;
        } else {
            payoutValue = in.readDouble();
        }
        payoutType = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        budgetType = in.readString();
        claimable = in.readString();
        if (in.readByte() == 0) {
            slno = null;
        } else {
            slno = in.readInt();
        }
        schemeType = in.readString();
        byte tmpIsSelected = in.readByte();
        isSelected = tmpIsSelected == 0 ? null : tmpIsSelected == 1;
        schemeDescription = in.readString();
        if (in.readByte() == 0) {
            schemeUtilize = null;
        } else {
            schemeUtilize = in.readDouble();
        }
        isSchemeUtilize = in.readString();
        channelCode = in.readString();
        subChannelCode = in.readString();
        groupCode = in.readString();
        classCode = in.readString();
        flatAmount = in.readDouble();
        reportWeek = in.readString();
        schemeUtilizeAmt = in.readDouble();
        utilizeStartDate = in.readString();
        utilizedEndDate = in.readString();
        if (in.readByte() == 0) {
            percentage = null;
        } else {
            percentage = in.readInt();
        }
        if (in.readByte() == 0) {
            noOfInvoice = null;
        } else {
            noOfInvoice = in.readInt();
        }
        if (in.readByte() == 0) {
            freeQty = null;
        } else {
            freeQty = in.readInt();
        }
        freeProdCode = in.readString();
        freeProdBatchCode = in.readString();
        uploadFlag = in.readString();
        quantity = in.readInt();
        isMandatory = in.readString();
        freeProdName = in.readString();
        invoiceNo = in.readString();
        dicountAmount = in.readString();
        dicountPercentage = in.readString();
        uom = in.readString();
        if (in.readByte() == 0) {
            forEvery = null;
        } else {
            forEvery = in.readDouble();
        }
        combi = in.readString();
        if (in.readByte() == 0) {
            minValue = null;
        } else {
            minValue = in.readInt();
        }
        isFreeApplied = in.readString();
        isSkuLevel = in.readString();
        isActive = in.readString();
        prodBatchCode = in.readString();
    }

    public static final Creator<SchemeModel> CREATOR = new Creator<SchemeModel>() {
        @Override
        public SchemeModel createFromParcel(Parcel in) {
            return new SchemeModel(in);
        }

        @Override
        public SchemeModel[] newArray(int size) {
            return new SchemeModel[size];
        }
    };

    public String getReadableInvNo() {
        return readableInvNo;
    }

    public void setReadableInvNo(String readableInvNo) {
        this.readableInvNo = readableInvNo;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getMrp() {
        return mrp;
    }

    public void setMrp(BigDecimal mrp) {
        this.mrp = mrp;
    }

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

    public String getIsSkuLevel() {
        return isSkuLevel;
    }

    public void setIsSkuLevel(String isSkuLevel) {
        this.isSkuLevel = isSkuLevel;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public String getCombi() {
        return combi;
    }

    public void setCombi(String combi) {
        this.combi = combi;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Double getForEvery() {
        return forEvery;
    }

    public void setForEvery(Double forEvery) {
        this.forEvery = forEvery;
    }

    public String getDicountAmount() {
        return dicountAmount;
    }

    public void setDicountAmount(String dicountAmount) {
        this.dicountAmount = dicountAmount;
    }

    public String getDicountPercentage() {
        return dicountPercentage;
    }

    public void setDicountPercentage(String dicountPercentage) {
        this.dicountPercentage = dicountPercentage;
    }

    public String getFreeProdBatchCode() {
        return freeProdBatchCode;
    }

    public void setFreeProdBatchCode(String freeProdBatchCode) {
        this.freeProdBatchCode = freeProdBatchCode;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getFreeProdName() {
        return freeProdName;
    }

    public void setFreeProdName(String freeProdName) {
        this.freeProdName = freeProdName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getClaimable() {
        return claimable;
    }

    public void setClaimable(String claimable) {
        this.claimable = claimable;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getSchemeDescription() {
        return schemeDescription;
    }

    public void setSchemeDescription(String schemeDescription) {
        this.schemeDescription = schemeDescription;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public Double getPayoutValue() {
        return payoutValue;
    }

    public void setPayoutValue(Double payoutValue) {
        this.payoutValue = payoutValue;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getSchemeBase() {
        return schemeBase;
    }

    public void setSchemeBase(String schemeBase) {
        this.schemeBase = schemeBase;
    }

    public String getSchemeFromDt() {
        return schemeFromDt;
    }

    public void setSchemeFromDt(String schemeFromDt) {
        this.schemeFromDt = schemeFromDt;
    }

    public String getSchemeToDt() {
        return schemeToDt;
    }

    public void setSchemeToDt(String schemeToDt) {
        this.schemeToDt = schemeToDt;
    }

    public String getSlabNo() {
        return slabNo;
    }

    public void setSlabNo(String slabNo) {
        this.slabNo = slabNo;
    }

    public Double getSlabFrom() {
        return slabFrom;
    }

    public void setSlabFrom(Double slabFrom) {
        this.slabFrom = slabFrom;
    }

    public Double getSlabTo() {
        return slabTo;
    }

    public void setSlabTo(Double slabTo) {
        this.slabTo = slabTo;
    }

    public String getSlabRange() {
        return slabRange;
    }

    public void setSlabRange(String slabRange) {
        this.slabRange = slabRange;
    }

    public String getPayoutType() {
        return payoutType;
    }

    public void setPayoutType(String payoutType) {
        this.payoutType = payoutType;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getSubChannelCode() {
        return subChannelCode;
    }

    public void setSubChannelCode(String subChannelCode) {
        this.subChannelCode = subChannelCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public double getFlatAmount() {
        return flatAmount;
    }

    public void setFlatAmount(double flatAmount) {
        this.flatAmount = flatAmount;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getFreeQty() {
        return freeQty;
    }

    public void setFreeQty(Integer freeQty) {
        this.freeQty = freeQty;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(String isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getFreeProdCode() {
        return freeProdCode;
    }

    public void setFreeProdCode(String freeProdCode) {
        this.freeProdCode = freeProdCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Integer getNoOfInvoice() {
        return noOfInvoice;
    }

    public void setNoOfInvoice(Integer noOfInvoice) {
        this.noOfInvoice = noOfInvoice;
    }

    public double getSchemeUtilizeAmt() {
        return schemeUtilizeAmt;
    }

    public void setSchemeUtilizeAmt(double schemeUtilizeAmt) {
        this.schemeUtilizeAmt = schemeUtilizeAmt;
    }

    public String getIsFreeApplied() {
        return isFreeApplied;
    }

    public void setIsFreeApplied(String isFreeApplied) {
        this.isFreeApplied = isFreeApplied;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchemeModel)) return false;

        SchemeModel class1 = (SchemeModel) o;

        return schemeCode.equals(class1.schemeCode);
    }

    @Override
    public int hashCode() {
        return schemeCode.hashCode();
    }


    public Integer getSlno() {
        return slno;
    }

    public void setSlno(Integer slno) {
        this.slno = slno;
    }

    public String getUtilizeStartDate() {
        return utilizeStartDate;
    }

    public void setUtilizeStartDate(String utilizeStartDate) {
        this.utilizeStartDate = utilizeStartDate;
    }

    public String getUtilizedEndDate() {
        return utilizedEndDate;
    }

    public void setUtilizedEndDate(String utilizedEndDate) {
        this.utilizedEndDate = utilizedEndDate;
    }

    public String getReportWeek() {
        return reportWeek;
    }

    public void setReportWeek(String reportWeek) {
        this.reportWeek = reportWeek;
    }

    public Double getSchemeUtilize() {
        return schemeUtilize;
    }

    public void setSchemeUtilize(Double schemeUtilize) {
        this.schemeUtilize = schemeUtilize;
    }

    public String getIsSchemeUtilize() {
        return isSchemeUtilize;
    }

    public void setIsSchemeUtilize(String isSchemeUtilize) {
        this.isSchemeUtilize = isSchemeUtilize;
    }

    public String getProdBatchCode() {
        return prodBatchCode;
    }

    public void setProdBatchCode(String prodBatchCode) {
        this.prodBatchCode = prodBatchCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(customerCode);
        dest.writeString(customerName);
        dest.writeString(productCode);
        dest.writeString(prodName);
        dest.writeString(schemeCode);
        dest.writeString(schemeBase);
        dest.writeString(schemeFromDt);
        dest.writeString(schemeToDt);
        dest.writeString(slabNo);
        if (slabFrom == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(slabFrom);
        }
        if (slabTo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(slabTo);
        }
        dest.writeString(slabRange);
        dest.writeString(schemeName);
        if (payoutValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(payoutValue);
        }
        dest.writeString(payoutType);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(budgetType);
        dest.writeString(claimable);
        if (slno == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(slno);
        }
        dest.writeString(schemeType);
        dest.writeByte((byte) (isSelected == null ? 0 : isSelected ? 1 : 2));
        dest.writeString(schemeDescription);
        if (schemeUtilize == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(schemeUtilize);
        }
        dest.writeString(isSchemeUtilize);
        dest.writeString(channelCode);
        dest.writeString(subChannelCode);
        dest.writeString(groupCode);
        dest.writeString(classCode);
        dest.writeDouble(flatAmount);
        dest.writeString(reportWeek);
        dest.writeDouble(schemeUtilizeAmt);
        dest.writeString(utilizeStartDate);
        dest.writeString(utilizedEndDate);
        if (percentage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(percentage);
        }
        if (noOfInvoice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(noOfInvoice);
        }
        if (freeQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(freeQty);
        }
        dest.writeString(freeProdCode);
        dest.writeString(freeProdBatchCode);
        dest.writeString(uploadFlag);
        dest.writeInt(quantity);
        dest.writeString(isMandatory);
        dest.writeString(freeProdName);
        dest.writeString(invoiceNo);
        dest.writeString(dicountAmount);
        dest.writeString(dicountPercentage);
        dest.writeString(uom);
        if (forEvery == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(forEvery);
        }
        dest.writeString(combi);
        if (minValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(minValue);
        }
        dest.writeString(isFreeApplied);
        dest.writeString(isSkuLevel);
        dest.writeString(isActive);
        dest.writeString(prodBatchCode);
    }
}
