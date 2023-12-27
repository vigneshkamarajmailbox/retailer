/*
 * Copyright (C) 2016 Botree Software International Private Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.botree.retailerssfa.models.FreeProdModel;
import com.botree.retailerssfa.models.SchemeModel;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.botree.retailerssfa.service.JSONConstants.TAG_CMP_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_CUSTOMER_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_DISTR_SALESMAN_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GRN_DT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_GRN_STATUS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_DT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_INVOICE_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_KG_RATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_MRP;
import static com.botree.retailerssfa.service.JSONConstants.TAG_NO_OF_ITEMS;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ORDER_NO;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ORDER_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_ORDER_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_BATCH_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_CODE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PROD_NAME;
import static com.botree.retailerssfa.service.JSONConstants.TAG_PUR_PRICE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_RECEIVED_QTY;
import static com.botree.retailerssfa.service.JSONConstants.TAG_SYNC_ORDER_DATE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_TOTAL_LINE_LEVEL_VALUE;
import static com.botree.retailerssfa.service.JSONConstants.TAG_UOM_CODE;

public class OrderBookingVO implements Parcelable {


    public static final Creator<OrderBookingVO> CREATOR = new Creator<OrderBookingVO>() {
        @Override
        public OrderBookingVO createFromParcel(Parcel in) {
            return new OrderBookingVO(in);
        }

        @Override
        public OrderBookingVO[] newArray(int size) {
            return new OrderBookingVO[size];
        }
    };
    @SerializedName(TAG_CMP_CODE)
    private String cmpCode;

    @SerializedName(TAG_DISTR_CODE)
    private String distrCode;

    @SerializedName(TAG_DISTR_SALESMAN_CODE)
    private String salesmanCode;

    private String routeCode;

    private String routeName = "";
    private String latitude = "";
    private String longitude = "";
    private String startTime = "";
    private String endTime = "";

    @SerializedName(TAG_CUSTOMER_CODE)
    private String retailerCode;

    private String retailerName = "";

    private String address = "";

    @SerializedName(TAG_PROD_CODE)
    private String prodCode;

    @SerializedName(TAG_PROD_NAME)
    private String prodName = "";

    @SerializedName(TAG_PROD_BATCH_CODE)
    private String prodBatchCode;

    private String prodShortName = "";

    private String productHierPath;

    private String productHierPathName = "";

    private String mobileNo = "";

    @SerializedName(value = "sellPrice", alternate = "sellRate")
    private BigDecimal sellPrice = new BigDecimal(0);
    private BigDecimal actualSellRate = new BigDecimal(0);

    @SerializedName(TAG_MRP)
    private BigDecimal mrp = new BigDecimal(0);

    private double primaryDisc = 0d;

    @SerializedName(value = TAG_ORDER_QTY, alternate = {TAG_INVOICE_QTY})
    private double quantity = 0;

    private double confirmQuantity = 0;

    @SerializedName(TAG_RECEIVED_QTY)
    private double receivedQty = 0;

    private Integer suggestedQty = 0;
    private String ppq = "";

    private double stockCheckQty = 0;

    private double netWeight = 0;

    private String weightType = "";

    private String productStatus = "";

    private String description = "";

    @SerializedName(value = TAG_ORDER_VALUE, alternate = {TAG_TOTAL_LINE_LEVEL_VALUE})
    private BigDecimal orderValue = new BigDecimal(0);

    private BigDecimal primDiscOrderValue = new BigDecimal(0);

    private BigDecimal totalAmount = new BigDecimal(0);

    private BigDecimal stockOrderValue = new BigDecimal(0);

    private Integer stockInHand = 0;

    private String uomGroupId = "";

    private String defaultUomid = "";

    private String category = "";
    private String distrStateCode = "";
    private String retailerStateCode = "";

    @SerializedName(value = TAG_SYNC_ORDER_DATE, alternate = {TAG_INVOICE_DT})
    private String orderDate = "";

    @SerializedName(TAG_GRN_DT)
    private String grnDate = "";

    @SerializedName(TAG_GRN_STATUS)
    private String grnStatus = "";
    private String OrderStatus = "";

    @SerializedName(value = TAG_UOM_CODE)
    private String uomId = "";

    private String confirmUomId = "";

    private String stockUomId = "";

    private String prodHierValName = "";

    private String remarks = "";

    private Map<String, Integer> conversionFactor;

    @SerializedName(TAG_INVOICE_NO)
    private String orderInvoiceNo = "";
    private String readableInvNo = "";
    @SerializedName(value = "tax", alternate = "taxAmt")
    private Double tax = 0.0;

    @SerializedName(TAG_ORDER_NO)
    private String orderNo = "";
    @SerializedName(value = "lGrossAmt", alternate = "grossValue")
    private BigDecimal lGrossAmt = new BigDecimal(0);

    private BigDecimal dicountAmt = new BigDecimal(0);

    private boolean containsMultipleCategory = false;

    private List<String> categoryList;

    private Integer orderFreeQty = 0;

    private Integer colorValue = 0;

    @SerializedName(TAG_NO_OF_ITEMS)
    private Integer noOfItems = 0;

    @SerializedName(value = "schemeAmount", alternate = "schAmt")
    private BigDecimal schemeAmount = new BigDecimal(0);

    private BigDecimal schemePercentage = new BigDecimal(0);

    private BigDecimal netAmount = new BigDecimal(0);

    @SerializedName(value = "cgstvalue", alternate = "cgstAmt")
    private BigDecimal cgstvalue = new BigDecimal(0);
    @SerializedName(value = "sgstvalue", alternate = "sgstAmt")
    private BigDecimal sgstValue = new BigDecimal(0);
    @SerializedName(value = "igstvalue", alternate = "igstAmt")
    private BigDecimal igstvalue = new BigDecimal(0);
    @SerializedName(value = "ugstvalue", alternate = "ugstAmt")
    private BigDecimal utgstValue = new BigDecimal(0);
    private BigDecimal cessValue = new BigDecimal(0);
    @SerializedName(value = "cgstperc", alternate = "cgstPerc")
    private double cgstperc = 0.0;
    private double sgstPerc = 0.0;
    private double igstPerc = 0.0;
    @SerializedName(value = "utgstPerc", alternate = "ugstPerc")
    private double utgstPerc = 0.0;
    private double cessPerc = 0.0;

    private String focusCategory = "";

    private String mustcategory = "";

    private String freeProdCode = "";

    private String brandName = "";

    private String brandCode = "";

    private String taxCode = "";

    private String channelName = "";

    private String isNewRetailer = "";

    private Integer totQty = 0;

    private Boolean isExpand = false;

    private Integer stockConversionFactor = 0;

    private String stockDefaultUOMId = "";

    private Integer availQty = 0;

    private boolean isFreeProdAvail = false;

    private List<FreeProdModel> freeProdModelList = new ArrayList<>();

    @SerializedName(TAG_PUR_PRICE)
    private Double purchasePrice = 0.0;

    private String baseUOM = "";

    private String unSalUOM = "";

    private Integer unSalQty = 0;

    private Integer freeQty = 0;

    private String freeUOM = "";

    private String confirmDate = "";

    private String stockTakeReason = "";

    private Integer lastOrdQty = 0;

    private boolean itemSelected = false;

    private boolean isCheckBoxEnabled = true;

    private String priority = "";

    private String inputStr = "";

    private Integer mustSellFocusSort = 0;

    public BigDecimal getActualSellRate() {
        return actualSellRate;
    }

    public void setActualSellRate(BigDecimal actualSellRate) {
        this.actualSellRate = actualSellRate;
    }

    public Integer getMustSellFocusSort() {
        return mustSellFocusSort;
    }

    public void setMustSellFocusSort(Integer mustSellFocusSort) {
        this.mustSellFocusSort = mustSellFocusSort;
    }

    private Integer mustSellQty = 0;

    private Integer tempMustSellQty = 0;

    private String prodType = "";

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public Integer getTempMustSellQty() {
        return tempMustSellQty;
    }

    public void setTempMustSellQty(Integer tempMustSellQty) {
        this.tempMustSellQty = tempMustSellQty;
    }

    public Integer getMustSellQty() {
        return mustSellQty;
    }

    public void setMustSellQty(Integer mustSellQty) {
        this.mustSellQty = mustSellQty;
    }

    public String getInputStr() {
        return inputStr;
    }

    public void setInputStr(String inputStr) {
        this.inputStr = inputStr;
    }

    @SerializedName(TAG_KG_RATE)
    private double kgRate = 0;

    List<SchemeModel> schemeModelList = new ArrayList<>();

    List<SchemeModel> schemeslablist = new ArrayList<>();

    private Integer topSKU = 0;


    public Integer getTopSKU() {
        return topSKU;
    }

    public void setTopSKU(Integer topSKU) {
        this.topSKU = topSKU;
    }

    private String top10ProductCategory = "";

    public String getTop10ProductCategory() {
        return top10ProductCategory;
    }

    public void setTop10ProductCategory(String top10ProductCategory) {
        this.top10ProductCategory = top10ProductCategory;
    }

    public OrderBookingVO() {

    }

    protected OrderBookingVO(Parcel in) {
        prodCode = in.readString();
        prodName = in.readString();
        prodShortName = in.readString();
        productStatus = in.readString();
        description = in.readString();
        uomGroupId = in.readString();
        defaultUomid = in.readString();
        category = in.readString();
        orderDate = in.readString();
        uomId = in.readString();
        prodHierValName = in.readString();
        remarks = in.readString();
        categoryList = in.createStringArrayList();
        containsMultipleCategory = Boolean.parseBoolean(in.readString());
        freeProdCode = in.readString();
        confirmDate = in.readString();
        schemePercentage = BigDecimal.valueOf(in.readDouble());

    }

    public OrderBookingVO(String uomGroupId, String defaultUomId) {
        this.uomGroupId = uomGroupId;
        this.defaultUomid = defaultUomId;
    }

    public String getPpq() {
        return ppq;
    }

    public void setPpq(String ppq) {
        this.ppq = ppq;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public static Creator<OrderBookingVO> getCREATOR() {
        return CREATOR;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public BigDecimal getSchemePercentage() {
        return schemePercentage;
    }

    public void setSchemePercentage(BigDecimal schemePercentage) {
        this.schemePercentage = schemePercentage;
    }

    public boolean isCheckBoxEnabled() {
        return isCheckBoxEnabled;
    }

    public void setCheckBoxEnabled(boolean checkBoxEnabled) {
        isCheckBoxEnabled = checkBoxEnabled;
    }

    public boolean isItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(boolean itemSelected) {
        this.itemSelected = itemSelected;
    }

    public String getStockTakeReason() {
        return stockTakeReason;
    }

    public void setStockTakeReason(String stockTakeReason) {
        this.stockTakeReason = stockTakeReason;
    }

    public Integer getLastOrdQty() {
        return lastOrdQty;
    }

    public void setLastOrdQty(Integer lastOrdQty) {
        this.lastOrdQty = lastOrdQty;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getUnSalUOM() {
        return unSalUOM;
    }

    public void setUnSalUOM(String unSalUOM) {
        this.unSalUOM = unSalUOM;
    }

    public Integer getUnSalQty() {
        return unSalQty;
    }

    public void setUnSalQty(Integer unSalQty) {
        this.unSalQty = unSalQty;
    }

    public Integer getFreeQty() {
        return freeQty;
    }

    public void setFreeQty(Integer freeQty) {
        this.freeQty = freeQty;
    }

    public String getFreeUOM() {
        return freeUOM;
    }

    public void setFreeUOM(String freeUOM) {
        this.freeUOM = freeUOM;
    }

    public String getBaseUOM() {
        return baseUOM;
    }

    public void setBaseUOM(String baseUOM) {
        this.baseUOM = baseUOM;
    }

    public String getDistrStateCode() {
        return distrStateCode;
    }

    public void setDistrStateCode(String distrStateCode) {
        this.distrStateCode = distrStateCode;
    }

    public String getRetailerStateCode() {
        return retailerStateCode;
    }

    public void setRetailerStateCode(String retailerStateCode) {
        this.retailerStateCode = retailerStateCode;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getAvailQty() {
        return availQty;
    }

    public void setAvailQty(Integer availQty) {
        this.availQty = availQty;
    }

    public Integer getStockConversionFactor() {
        return stockConversionFactor;
    }

    public void setStockConversionFactor(Integer stockConversionFactor) {
        this.stockConversionFactor = stockConversionFactor;
    }

    public String getStockDefaultUOMId() {
        return stockDefaultUOMId;
    }

    public void setStockDefaultUOMId(String stockDefaultUOMId) {
        this.stockDefaultUOMId = stockDefaultUOMId;
    }

    public Integer getTotQty() {
        return totQty;
    }

    public void setTotQty(Integer totQty) {
        this.totQty = totQty;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<FreeProdModel> getFreeProdModelList() {
        return freeProdModelList;
    }

    public void setFreeProdModelList(List<FreeProdModel> freeProdModelList) {
        this.freeProdModelList = freeProdModelList;
    }

    public String getFreeProdCode() {
        return freeProdCode;
    }

    public void setFreeProdCode(String freeProdCode) {
        this.freeProdCode = freeProdCode;
    }

    public String getFocusCategory() {
        return focusCategory;
    }

    public void setFocusCategory(String focusCategory) {
        this.focusCategory = focusCategory;
    }

    public String getMustcategory() {
        return mustcategory;
    }

    public void setMustcategory(String mustcategory) {
        this.mustcategory = mustcategory;
    }

    public boolean isContainsMultipleCategory() {
        return containsMultipleCategory;
    }

    public void setContainsMultipleCategory(boolean containsMultipleCategory) {
        this.containsMultipleCategory = containsMultipleCategory;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getProdBatchCode() {
        return prodBatchCode;
    }

    public void setProdBatchCode(String prodBatchCode) {
        this.prodBatchCode = prodBatchCode;
    }

    public BigDecimal getlGrossAmt() {
        return lGrossAmt;
    }

    public void setlGrossAmt(BigDecimal lGrossAmt) {
        this.lGrossAmt = lGrossAmt;
    }

    public BigDecimal getDicountAmt() {
        return dicountAmt;
    }

    public void setDicountAmt(BigDecimal dicountAmt) {
        this.dicountAmt = dicountAmt;
    }

    public Integer getOrderFreeQty() {
        return orderFreeQty;
    }

    public void setOrderFreeQty(Integer orderFreeQty) {
        this.orderFreeQty = orderFreeQty;
    }

    public String getOrderInvoiceNo() {
        return orderInvoiceNo;
    }

    public void setOrderInvoiceNo(String orderInvoiceNo) {
        this.orderInvoiceNo = orderInvoiceNo;
    }


    public String getReadableInvNo() {
        return readableInvNo;
    }

    public void setReadableInvNo(String readableInvNo) {
        this.readableInvNo = readableInvNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getProdHierValName() {
        return prodHierValName;
    }

    public void setProdHierValName(String prodHierValName) {
        this.prodHierValName = prodHierValName;
    }

    public Map<String, Integer> getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(Map<String, Integer> conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUomId() {
        return uomId;
    }

    public void setUomId(String uomId) {
        this.uomId = uomId;
    }

    public String getUomGroupId() {
        return uomGroupId;
    }

    public void setUomGroupId(String uomGroupId) {
        this.uomGroupId = uomGroupId;
    }

    public String getDefaultUomid() {
        return defaultUomid;
    }

    public void setDefaultUomid(String defaultUomid) {
        this.defaultUomid = defaultUomid;
    }

    public String getProdShortName() {
        return prodShortName;
    }

    public void setProdShortName(String prodShortName) {
        this.prodShortName = prodShortName;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getMrp() {
        return mrp;
    }

    public void setMrp(BigDecimal mrp) {
        this.mrp = mrp.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Integer getSuggestedQty() {
        return suggestedQty;
    }

    public void setSuggestedQty(Integer suggestedQty) {
        this.suggestedQty = suggestedQty;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(BigDecimal orderValue) {
        this.orderValue = orderValue.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public Integer getStockInHand() {
        return stockInHand;
    }

    public void setStockInHand(Integer stockInHand) {
        this.stockInHand = stockInHand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prodCode);
        dest.writeString(prodName);
        dest.writeString(prodShortName);
        dest.writeString(productStatus);
        dest.writeString(description);
        dest.writeString(uomGroupId);
        dest.writeString(defaultUomid);
        dest.writeString(category);
        dest.writeString(orderDate);
        dest.writeString(uomId);
        dest.writeString(prodHierValName);
        dest.writeString(remarks);
        dest.writeStringList(categoryList);
        dest.writeString(String.valueOf(containsMultipleCategory));
        dest.writeString(String.valueOf(freeProdCode));
        dest.writeString(String.valueOf(schemePercentage.doubleValue()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderBookingVO that = (OrderBookingVO) o;

        return (prodBatchCode != null ? prodBatchCode.equals(that.prodBatchCode) : that.prodBatchCode == null) &&
                (prodCode != null ? prodCode.equals(that.prodCode) : that.prodCode == null);

    }


    @Override
    public int hashCode() {
        return (prodBatchCode != null ? prodBatchCode.hashCode() : 0);
    }

    public String getStockUomId() {
        return stockUomId;
    }

    public void setStockUomId(String stockUomId) {
        this.stockUomId = stockUomId;
    }

    public double getStockCheckQty() {
        return stockCheckQty;
    }

    public void setStockCheckQty(double stockCheckQty) {
        this.stockCheckQty = stockCheckQty;
    }

    public BigDecimal getStockOrderValue() {
        return stockOrderValue;
    }

    public void setStockOrderValue(BigDecimal stockOrderValue) {
        this.stockOrderValue = stockOrderValue;
    }

    public Integer getColorValue() {
        return colorValue;
    }

    public void setColorValue(Integer colorValue) {
        this.colorValue = colorValue;
    }

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRetailerCode() {
        return retailerCode;
    }

    public void setRetailerCode(String retailerCode) {
        this.retailerCode = retailerCode;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public BigDecimal getSchemeAmount() {
        return schemeAmount;
    }

    public void setSchemeAmount(BigDecimal schemeAmount) {
        this.schemeAmount = schemeAmount;
    }

    public BigDecimal getCgstvalue() {
        return cgstvalue;
    }

    public void setCgstvalue(BigDecimal cgstvalue) {
        this.cgstvalue = cgstvalue;
    }

    public BigDecimal getCessValue() {
        return cessValue;
    }

    public void setCessValue(BigDecimal cessValue) {
        this.cessValue = cessValue;
    }

    public BigDecimal getSgstValue() {
        return sgstValue;
    }

    public void setSgstValue(BigDecimal sgstValue) {
        this.sgstValue = sgstValue;
    }

    public BigDecimal getIgstvalue() {
        return igstvalue;
    }

    public void setIgstvalue(BigDecimal igstvalue) {
        this.igstvalue = igstvalue;
    }

    public BigDecimal getUtgstValue() {
        return utgstValue;
    }

    public void setUtgstValue(BigDecimal utgstValue) {
        this.utgstValue = utgstValue;
    }

    public double getCgstperc() {
        return cgstperc;
    }

    public void setCgstperc(double cgstperc) {
        this.cgstperc = cgstperc;
    }

    public double getCessPerc() {
        return cessPerc;
    }

    public void setCessPerc(double cessPerc) {
        this.cessPerc = cessPerc;
    }

    public double getSgstPerc() {
        return sgstPerc;
    }

    public void setSgstPerc(double sgstPerc) {
        this.sgstPerc = sgstPerc;
    }

    public double getIgstPerc() {
        return igstPerc;
    }

    public void setIgstPerc(double igstPerc) {
        this.igstPerc = igstPerc;
    }

    public double getUtgstPerc() {
        return utgstPerc;
    }

    public void setUtgstPerc(double utgstPerc) {
        this.utgstPerc = utgstPerc;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(Integer noOfItems) {
        this.noOfItems = noOfItems;
    }

    public Boolean getExpand() {
        return isExpand;
    }

    public void setExpand(Boolean expand) {
        isExpand = expand;
    }

    public boolean isFreeProdAvail() {
        return isFreeProdAvail;
    }

    public void setFreeProdAvail(boolean freeProdAvail) {
        isFreeProdAvail = freeProdAvail;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getIsNewRetailer() {
        return isNewRetailer;
    }

    public void setIsNewRetailer(String isNewRetailer) {
        this.isNewRetailer = isNewRetailer;
    }

    public double getConfirmQuantity() {
        return confirmQuantity;
    }

    public void setConfirmQuantity(double confirmQuantity) {
        this.confirmQuantity = confirmQuantity;
    }

    public String getConfirmUomId() {
        return confirmUomId;
    }

    public void setConfirmUomId(String confirmUomId) {
        this.confirmUomId = confirmUomId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getGrnDate() {
        return grnDate;
    }

    public void setGrnDate(String grnDate) {
        this.grnDate = grnDate;
    }

    public double getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(double receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getGrnStatus() {
        return grnStatus;
    }

    public void setGrnStatus(String grnStatus) {
        this.grnStatus = grnStatus;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String OrderStatus) {
        this.OrderStatus = OrderStatus;
    }

    public String getProductHierPath() {
        return productHierPath;
    }

    public void setProductHierPath(String productHierPath) {
        this.productHierPath = productHierPath;
    }

    public String getProductHierPathName() {
        return productHierPathName;
    }

    public void setProductHierPathName(String productHierPathName) {
        this.productHierPathName = productHierPathName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channeName) {
        this.channelName = channeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public String getWeightType() {
        return weightType;
    }

    public void setWeightType(String weightType) {
        this.weightType = weightType;
    }

    public double getPrimaryDisc() {
        return primaryDisc;
    }

    public void setPrimaryDisc(double primaryDisc) {
        this.primaryDisc = primaryDisc;
    }

    public BigDecimal getPrimDiscOrderValue() {
        return primDiscOrderValue;
    }

    public void setPrimDiscOrderValue(BigDecimal primDiscOrderValue) {
        this.primDiscOrderValue = primDiscOrderValue;
    }

    public double getKgRate() {
        return kgRate;
    }

    public void setKgRate(double kgRate) {
        this.kgRate = kgRate;
    }

    public List<SchemeModel> getSchemeModelList() {
        return schemeModelList;
    }

    public void setSchemeModelList(List<SchemeModel> schemeModelList) {
        this.schemeModelList = schemeModelList;
    }

    public List<SchemeModel> getSchemeslablist() {
        return schemeslablist;
    }

    public void setSchemeslablist(List<SchemeModel> schemeslablist) {
        this.schemeslablist = schemeslablist;
    }

}
