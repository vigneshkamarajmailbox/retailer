package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.service.JSONConstants.TAG_ACTIVE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ATTRIBUTE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ATTRIBUTE_INPUT_VALUES;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ATTRIBUTE_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ATTRIBUTE_VALUE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ATTRIBUTE_VALUE_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CHANNEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CHANNEL_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CLASS_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_MONTH_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_MONTH_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_YEAR_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CURRENT_YEAR_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GROUP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LOB_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_LPC_RANGE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MTD_NO_OF_INVOICE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_OF_INVOICE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_OF_INVOICES;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_OF_ORDERS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_OF_OUTLETS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_MONTH_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_MONTH_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_YEAR_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PREV_YEAR_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_BRAND_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_BRAND_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_LEVEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_HIER_VALUE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_TYPE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_REF_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_REMARKS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALESMAN_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALESMAN_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALES_FORCE_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALES_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SALES_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUB_CHANNEL_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SUB_CHANNEL_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_S_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TRENDS_MONTH;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TRENDS_REPORT_WEEKS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_VALID_FROM;
import static com.botree.retailerssfa.service.JSONConstants.TAG_VALID_TILL;

/**
 * Created by shantarao on 5/1/18.
 */

public class ProductModel extends UserLevelModel implements Parcelable {

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };
    private String id = "";

    @SerializedName(TAG_DISTR_CODE)
    private String distCode = "";

    @SerializedName(TAG_CMP_CODE)
    private String cmpCode = "";

    @SerializedName(TAG_SALESMAN_CODE)
    private String salesmanCode = "";

    @SerializedName(TAG_SALESMAN_NAME)
    private String salesmanName = "";

    @SerializedName(TAG_CUSTOMER_CODE)
    private String customerCode = "";

    @SerializedName(TAG_CUSTOMER_NAME)
    private String customerName = "";

    @SerializedName(value = TAG_SALES_VALUE, alternate = TAG_INVOICE_VALUE)
    private Double salesValue = 0.0;

    @SerializedName(TAG_PROD_CODE)
    private String prodCode = "";

    @SerializedName(TAG_PROD_TYPE)
    private String prodType = "";

    @SerializedName(TAG_PROD_NAME)
    private String prodName = "";

    @SerializedName(TAG_SALES_FORCE_CODE)
    private String salesForceCode = "";

    @SerializedName(TAG_LOB_CODE)
    private String lobCode = "";

    @SerializedName(TAG_PROD_HIER_LEVEL_CODE)
    private String prodHierLvlCode = "";

    @SerializedName(TAG_PROD_HIER_VALUE_CODE)
    private String prodHierValCode = "";

    @SerializedName(value = TAG_NO_OF_INVOICE, alternate = {TAG_MTD_NO_OF_INVOICE, TAG_NO_OF_INVOICES})
    private Integer noOfInvoice = 0;

    @SerializedName(value = TAG_SALES_QTY, alternate = TAG_INVOICE_QTY)
    private Integer invoiceQty = 0;

    @SerializedName(TAG_NO_OF_ORDERS)
    private Integer noOfOrders = 0;

    @SerializedName(TAG_CHANNEL_CODE)
    private String channelCode = "";

    @SerializedName(TAG_CHANNEL_NAME)
    private String channelName = "";

    @SerializedName(TAG_GROUP_CODE)
    private String groupCode = "";

    @SerializedName(TAG_CLASS_CODE)
    private String classCode = "";

    private String date = "";

    @SerializedName(TAG_TRENDS_MONTH)
    private Integer month = 0;

    @SerializedName(value = TAG_CURRENT_MONTH_SALES_VALUE, alternate = TAG_CURRENT_YEAR_SALES_VALUE)
    private Double currSalesValue = 0d;

    @SerializedName(value = TAG_PREV_MONTH_SALES_VALUE, alternate = TAG_PREV_YEAR_SALES_VALUE)
    private Double prevSalesValue = 0d;

    @SerializedName(value = TAG_CURRENT_YEAR_SALES_QTY, alternate = TAG_CURRENT_MONTH_SALES_QTY)
    private Integer currSalesQty = 0;

    @SerializedName(value = TAG_PREV_YEAR_SALES_QTY, alternate = TAG_PREV_MONTH_SALES_QTY)
    private Integer prevSalesQty = 0;

    @SerializedName(TAG_TRENDS_REPORT_WEEKS)
    private String week = "";

    @SerializedName(TAG_SUB_CHANNEL_CODE)
    private String subChannelCode = "";

    @SerializedName(TAG_SUB_CHANNEL_NAME)
    private String subChannelName = "";

    private String routeCode = "";

    private String routeName = "";

    @SerializedName(TAG_PROD_BRAND_CODE)
    private String brandCode = "";

    @SerializedName(TAG_PROD_BRAND_NAME)
    private String brandName = "";

    @SerializedName(TAG_NO_OF_OUTLETS)
    private String noOfOutlets = "";

    @SerializedName(TAG_LPC_RANGE)
    private String lpcRange = "";

    private String focusBrand = "";

    private String mustSell = "";

    @SerializedName(TAG_VALID_FROM)
    private String validFrom = "";

    @SerializedName(TAG_VALID_TILL)
    private String validTill = "";

    @SerializedName(TAG_ACTIVE)
    private String isActive = "";

    private String uploadFlag = "";

    @SerializedName(TAG_REF_NO)
    private String refrenceNo = "";

    @SerializedName(TAG_S_NO)
    private String sNO = "";

    @SerializedName(TAG_ATTRIBUTE_CODE)
    private String attributeCode = "";

    @SerializedName(TAG_ATTRIBUTE_NAME)
    private String attributeName = "";

    @SerializedName(TAG_ATTRIBUTE_VALUE_CODE)
    private String attributeValueCode = "";

    @SerializedName(TAG_ATTRIBUTE_VALUE_NAME)
    private String attributeValueName = "";

    @SerializedName(TAG_ATTRIBUTE_INPUT_VALUES)
    private String attrInputValues = "";

    @SerializedName(TAG_REMARKS)
    private String remarks = "";

    private boolean isExpand = false;

    public ProductModel(String channelCode, String channelName) {
        this.channelCode = channelCode;
        this.channelName = channelName;

    }

    public ProductModel() {

    }

    protected ProductModel(Parcel in) {
        id = in.readString();
        distCode = in.readString();
        cmpCode = in.readString();
        salesmanCode = in.readString();
        customerCode = in.readString();
        customerName = in.readString();
        if (in.readByte() == 0) {
            salesValue = null;
        } else {
            salesValue = in.readDouble();
        }
        prodCode = in.readString();
        prodType = in.readString();
        prodName = in.readString();
        salesForceCode = in.readString();
        lobCode = in.readString();
        prodHierLvlCode = in.readString();
        prodHierValCode = in.readString();
        if (in.readByte() == 0) {
            noOfInvoice = null;
        } else {
            noOfInvoice = in.readInt();
        }
        if (in.readByte() == 0) {
            invoiceQty = null;
        } else {
            invoiceQty = in.readInt();
        }
        channelCode = in.readString();
        channelName = in.readString();
        groupCode = in.readString();
        classCode = in.readString();
        date = in.readString();
        month = in.readInt();
        if (in.readByte() == 0) {
            currSalesValue = null;
        } else {
            currSalesValue = in.readDouble();
        }
        if (in.readByte() == 0) {
            prevSalesValue = null;
        } else {
            prevSalesValue = in.readDouble();
        }
        week = in.readString();
        subChannelCode = in.readString();
        subChannelName = in.readString();
        routeCode = in.readString();
        routeName = in.readString();
        brandCode = in.readString();
        brandName = in.readString();
        noOfOutlets = in.readString();
        lpcRange = in.readString();
        focusBrand = in.readString();
        mustSell = in.readString();
        validFrom = in.readString();
        validTill = in.readString();
        isActive = in.readString();
        uploadFlag = in.readString();
        refrenceNo = in.readString();
        sNO = in.readString();
        attributeCode = in.readString();
        attributeName = in.readString();
        attributeValueCode = in.readString();
        attributeValueName = in.readString();
        attrInputValues = in.readString();
        remarks = in.readString();
        isExpand = in.readByte() != 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubChannelCode() {
        return subChannelCode;
    }

    public void setSubChannelCode(String subChannelCode) {
        this.subChannelCode = subChannelCode;
    }

    public String getSubChannelName() {
        return subChannelName;
    }

    public void setSubChannelName(String subChannelName) {
        this.subChannelName = subChannelName;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
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

    public String getNoOfOutlets() {
        return noOfOutlets;
    }

    public void setNoOfOutlets(String noOfOutlets) {
        this.noOfOutlets = noOfOutlets;
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public Double getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(Double salesValue) {
        this.salesValue = salesValue;
    }

    public Double getCurrSalesValue() {
        return currSalesValue;
    }

    public void setCurrSalesValue(Double currSalesValue) {
        this.currSalesValue = currSalesValue;
    }

    public Double getPrevSalesValue() {
        return prevSalesValue;
    }

    public void setPrevSalesValue(Double prevSalesValue) {
        this.prevSalesValue = prevSalesValue;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getFocusBrand() {
        return focusBrand;
    }

    public void setFocusBrand(String focusBrand) {
        this.focusBrand = focusBrand;
    }

    public String getMustSell() {
        return mustSell;
    }

    public void setMustSell(String mustSell) {
        this.mustSell = mustSell;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getRefrenceNo() {
        return refrenceNo;
    }

    public void setRefrenceNo(String refrenceNo) {
        this.refrenceNo = refrenceNo;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
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

    public String getAttributeCode() {
        return attributeCode;
    }

    public void setAttributeCode(String attributeCode) {
        this.attributeCode = attributeCode;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValueCode() {
        return attributeValueCode;
    }

    public void setAttributeValueCode(String attributeValueCode) {
        this.attributeValueCode = attributeValueCode;
    }

    public String getAttributeValueName() {
        return attributeValueName;
    }

    public void setAttributeValueName(String attributeValueName) {
        this.attributeValueName = attributeValueName;
    }

    public String getAttrInputValues() {
        return attrInputValues;
    }

    public void setAttrInputValues(String attrInputValues) {
        this.attrInputValues = attrInputValues;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getsNO() {
        return sNO;
    }

    public void setsNO(String sNO) {
        this.sNO = sNO;
    }

    public String getSalesForceCode() {
        return salesForceCode;
    }

    public void setSalesForceCode(String salesForceCode) {
        this.salesForceCode = salesForceCode;
    }

    public String getLobCode() {
        return lobCode;
    }

    public void setLobCode(String lobCode) {
        this.lobCode = lobCode;
    }

    public String getProdHierLvlCode() {
        return prodHierLvlCode;
    }

    public void setProdHierLvlCode(String prodHierLvlCode) {
        this.prodHierLvlCode = prodHierLvlCode;
    }

    public String getProdHierValCode() {
        return prodHierValCode;
    }

    public void setProdHierValCode(String prodHierValCode) {
        this.prodHierValCode = prodHierValCode;
    }

    public Integer getNoOfInvoice() {
        return noOfInvoice;
    }

    public void setNoOfInvoice(Integer noOfInvoice) {
        this.noOfInvoice = noOfInvoice;
    }

    public Integer getInvoiceQty() {
        return invoiceQty;
    }

    public void setInvoiceQty(Integer invoiceQty) {
        this.invoiceQty = invoiceQty;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getLpcRange() {
        return lpcRange;
    }

    public void setLpcRange(String lpcRange) {
        this.lpcRange = lpcRange;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(distCode);
        dest.writeString(cmpCode);
        dest.writeString(salesmanCode);
        dest.writeString(customerCode);
        dest.writeString(customerName);
        if (salesValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(salesValue);
        }
        dest.writeString(prodCode);
        dest.writeString(prodType);
        dest.writeString(prodName);
        dest.writeString(salesForceCode);
        dest.writeString(lobCode);
        dest.writeString(prodHierLvlCode);
        dest.writeString(prodHierValCode);
        if (noOfInvoice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(noOfInvoice);
        }
        if (invoiceQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(invoiceQty);
        }
        dest.writeString(channelCode);
        dest.writeString(channelName);
        dest.writeString(groupCode);
        dest.writeString(classCode);
        dest.writeString(date);
        dest.writeInt(month);
        if (currSalesValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(currSalesValue);
        }
        if (prevSalesValue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(prevSalesValue);
        }
        dest.writeString(week);
        dest.writeString(subChannelCode);
        dest.writeString(subChannelName);
        dest.writeString(routeCode);
        dest.writeString(routeName);
        dest.writeString(brandCode);
        dest.writeString(brandName);
        dest.writeString(noOfOutlets);
        dest.writeString(lpcRange);
        dest.writeString(focusBrand);
        dest.writeString(mustSell);
        dest.writeString(validFrom);
        dest.writeString(validTill);
        dest.writeString(isActive);
        dest.writeString(uploadFlag);
        dest.writeString(refrenceNo);
        dest.writeString(sNO);
        dest.writeString(attributeCode);
        dest.writeString(attributeName);
        dest.writeString(attributeValueCode);
        dest.writeString(attributeValueName);
        dest.writeString(attrInputValues);
        dest.writeString(remarks);
        dest.writeByte((byte) (isExpand ? 1 : 0));
    }

    public Integer getCurrSalesQty() {
        return currSalesQty;
    }

    public void setCurrSalesQty(Integer currSalesQty) {
        this.currSalesQty = currSalesQty;
    }

    public Integer getPrevSalesQty() {
        return prevSalesQty;
    }

    public void setPrevSalesQty(Integer prevSalesQty) {
        this.prevSalesQty = prevSalesQty;
    }

    public Integer getNoOfOrders() {
        return noOfOrders;
    }

    public void setNoOfOrders(Integer noOfOrders) {
        this.noOfOrders = noOfOrders;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }
}
