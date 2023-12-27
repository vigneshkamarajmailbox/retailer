package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BillingInfoVO implements Parcelable {
    private String cmpCode;
    private String distrCode;
    private String invoiceNo="";
    private String godownCode;
    private String invoicePattern = "I";
    private String salesmanCode;
    private String routeCode;
    private String customerCode;
    private String customerShipCode;
    private String invoiceDt;
    private String shipDt;
    private String deliveryDt;
    private String invoiceType = "O";
    private String invoiceMode;
    private String paymentMode = "CA";
    private String totSplDiscAmt = "0.00";
    private String totSchDiscAmt = "0.00";
    private String totCashDiscAmt = "0.00";
    private String totDBDiscAmt = "0.00";
    private String totWDDiscAmt = "0.00";
    private String totCrNoteAmt = "0.00";
    private String totDbNoteAmt = "0.00";
    private String totOnAccountAmt = "0.00";
    private String totMarketReturnAmt = "0.00";
    private String totReplacementAmt = "0.00";
    private String totOtherChargesAmt = "0.00";
    private String invLevelDiscAmt = "0.00";
    private String invLevelDiscPerc = "0";
    private String totGrossAmt = "0.00";
    private String totAddition;
    private String totDeduction;
    private String totTaxAmt = "0.00";
    private String roundOffAmt = "0.00";
    private String totNetAmt = "0.00";
    private String orderNo;
    private String invoiceStatus = "D";
    private String remarks;
    private String latitude;
    private String longitude;
    private String startTime;
    private String endTime;
    private String lineCount;
    private String routeName;
    private String salesmanName;
    private String customerName;
    private String customerAddress;
    private String modeDate;
    private double balanceOS=0.0;
    private String balenceAmount = "0.00";
    private String uploadFlag;
    private String completeFlag;//Save - S; Save and Confirm - C;  default - N

    public String getCompleteFlag() {
        return completeFlag;
    }

    public void setCompleteFlag(String completeFlag) {
        this.completeFlag = completeFlag;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getBalenceAmount() {
        return balenceAmount;
    }

    public void setBalenceAmount(String balenceAmount) {
        this.balenceAmount = balenceAmount;
    }

    public double getBalanceOS() {
        return balanceOS;
    }

    public void setBalanceOS(double balanceOS) {
        this.balanceOS = balanceOS;
    }

    public BillingInfoVO() {
    }

    protected BillingInfoVO(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        invoiceNo = in.readString();
        godownCode = in.readString();
        invoicePattern = in.readString();
        salesmanCode = in.readString();
        routeCode = in.readString();
        customerCode = in.readString();
        customerShipCode = in.readString();
        invoiceDt = in.readString();
        shipDt = in.readString();
        deliveryDt = in.readString();
        invoiceType = in.readString();
        invoiceMode = in.readString();
        paymentMode = in.readString();
        totSplDiscAmt = in.readString();
        totSchDiscAmt = in.readString();
        totCashDiscAmt = in.readString();
        totDBDiscAmt = in.readString();
        totWDDiscAmt = in.readString();
        totCrNoteAmt = in.readString();
        totDbNoteAmt = in.readString();
        totOnAccountAmt = in.readString();
        totMarketReturnAmt = in.readString();
        totReplacementAmt = in.readString();
        totOtherChargesAmt = in.readString();
        invLevelDiscAmt = in.readString();
        invLevelDiscPerc = in.readString();
        totGrossAmt = in.readString();
        totAddition = in.readString();
        totDeduction = in.readString();
        totTaxAmt = in.readString();
        roundOffAmt = in.readString();
        totNetAmt = in.readString();
        orderNo = in.readString();
        invoiceStatus = in.readString();
        remarks = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        lineCount = in.readString();
        modeDate = in.readString();
    }

    public static final Creator<BillingInfoVO> CREATOR = new Creator<BillingInfoVO>() {
        @Override
        public BillingInfoVO createFromParcel(Parcel in) {
            return new BillingInfoVO(in);
        }

        @Override
        public BillingInfoVO[] newArray(int size) {
            return new BillingInfoVO[size];
        }
    };

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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getGodownCode() {
        return godownCode;
    }

    public void setGodownCode(String godownCode) {
        this.godownCode = godownCode;
    }

    public String getInvoicePattern() {
        return invoicePattern;
    }

    public void setInvoicePattern(String invoicePattern) {
        this.invoicePattern = invoicePattern;
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

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerShipCode() {
        return customerShipCode;
    }

    public void setCustomerShipCode(String customerShipCode) {
        this.customerShipCode = customerShipCode;
    }

    public String getInvoiceDt() {
        return invoiceDt;
    }

    public void setInvoiceDt(String invoiceDt) {
        this.invoiceDt = invoiceDt;
    }

    public String getShipDt() {
        return shipDt;
    }

    public void setShipDt(String shipDt) {
        this.shipDt = shipDt;
    }

    public String getDeliveryDt() {
        return deliveryDt;
    }

    public void setDeliveryDt(String deliveryDt) {
        this.deliveryDt = deliveryDt;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceMode() {
        return invoiceMode;
    }

    public void setInvoiceMode(String invoiceMode) {
        this.invoiceMode = invoiceMode;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTotSplDiscAmt() {
        return totSplDiscAmt;
    }

    public void setTotSplDiscAmt(String totSplDiscAmt) {
        this.totSplDiscAmt = totSplDiscAmt;
    }

    public String getTotSchDiscAmt() {
        return totSchDiscAmt;
    }

    public void setTotSchDiscAmt(String totSchDiscAmt) {
        this.totSchDiscAmt = totSchDiscAmt;
    }

    public String getTotCashDiscAmt() {
        return totCashDiscAmt;
    }

    public void setTotCashDiscAmt(String totCashDiscAmt) {
        this.totCashDiscAmt = totCashDiscAmt;
    }

    public String getTotDBDiscAmt() {
        return totDBDiscAmt;
    }

    public void setTotDBDiscAmt(String totDBDiscAmt) {
        this.totDBDiscAmt = totDBDiscAmt;
    }

    public String getTotWDDiscAmt() {
        return totWDDiscAmt;
    }

    public void setTotWDDiscAmt(String totWDDiscAmt) {
        this.totWDDiscAmt = totWDDiscAmt;
    }

    public String getTotCrNoteAmt() {
        return totCrNoteAmt;
    }

    public void setTotCrNoteAmt(String totCrNoteAmt) {
        this.totCrNoteAmt = totCrNoteAmt;
    }

    public String getTotDbNoteAmt() {
        return totDbNoteAmt;
    }

    public void setTotDbNoteAmt(String totDbNoteAmt) {
        this.totDbNoteAmt = totDbNoteAmt;
    }

    public String getTotOnAccountAmt() {
        return totOnAccountAmt;
    }

    public void setTotOnAccountAmt(String totOnAccountAmt) {
        this.totOnAccountAmt = totOnAccountAmt;
    }

    public String getTotMarketReturnAmt() {
        return totMarketReturnAmt;
    }

    public void setTotMarketReturnAmt(String totMarketReturnAmt) {
        this.totMarketReturnAmt = totMarketReturnAmt;
    }

    public String getTotReplacementAmt() {
        return totReplacementAmt;
    }

    public void setTotReplacementAmt(String totReplacementAmt) {
        this.totReplacementAmt = totReplacementAmt;
    }

    public String getTotOtherChargesAmt() {
        return totOtherChargesAmt;
    }

    public void setTotOtherChargesAmt(String totOtherChargesAmt) {
        this.totOtherChargesAmt = totOtherChargesAmt;
    }

    public String getInvLevelDiscAmt() {
        return invLevelDiscAmt;
    }

    public void setInvLevelDiscAmt(String invLevelDiscAmt) {
        this.invLevelDiscAmt = invLevelDiscAmt;
    }

    public String getInvLevelDiscPerc() {
        return invLevelDiscPerc;
    }

    public void setInvLevelDiscPerc(String invLevelDiscPerc) {
        this.invLevelDiscPerc = invLevelDiscPerc;
    }

    public String getTotGrossAmt() {
        return totGrossAmt;
    }

    public void setTotGrossAmt(String totGrossAmt) {
        this.totGrossAmt = totGrossAmt;
    }

    public String getTotAddition() {
        return totAddition;
    }

    public void setTotAddition(String totAddition) {
        this.totAddition = totAddition;
    }

    public String getTotDeduction() {
        return totDeduction;
    }

    public void setTotDeduction(String totDeduction) {
        this.totDeduction = totDeduction;
    }

    public String getTotTaxAmt() {
        return totTaxAmt;
    }

    public void setTotTaxAmt(String totTaxAmt) {
        this.totTaxAmt = totTaxAmt;
    }

    public String getRoundOffAmt() {
        return roundOffAmt;
    }

    public void setRoundOffAmt(String roundOffAmt) {
        this.roundOffAmt = roundOffAmt;
    }

    public String getTotNetAmt() {
        return totNetAmt;
    }

    public void setTotNetAmt(String totNetAmt) {
        this.totNetAmt = totNetAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getLineCount() {
        return lineCount;
    }

    public void setLineCount(String lineCount) {
        this.lineCount = lineCount;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getModeDate() {
        return modeDate;
    }

    public void setModeDate(String modeDate) {
        this.modeDate = modeDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cmpCode);
        parcel.writeString(distrCode);
        parcel.writeString(invoiceNo);
        parcel.writeString(godownCode);
        parcel.writeString(invoicePattern);
        parcel.writeString(salesmanCode);
        parcel.writeString(routeCode);
        parcel.writeString(customerCode);
        parcel.writeString(customerShipCode);
        parcel.writeString(invoiceDt);
        parcel.writeString(shipDt);
        parcel.writeString(deliveryDt);
        parcel.writeString(invoiceType);
        parcel.writeString(invoiceMode);
        parcel.writeString(paymentMode);
        parcel.writeString(totSplDiscAmt);
        parcel.writeString(totSchDiscAmt);
        parcel.writeString(totCashDiscAmt);
        parcel.writeString(totDBDiscAmt);
        parcel.writeString(totWDDiscAmt);
        parcel.writeString(totCrNoteAmt);
        parcel.writeString(totDbNoteAmt);
        parcel.writeString(totOnAccountAmt);
        parcel.writeString(totMarketReturnAmt);
        parcel.writeString(totReplacementAmt);
        parcel.writeString(totOtherChargesAmt);
        parcel.writeString(invLevelDiscAmt);
        parcel.writeString(invLevelDiscPerc);
        parcel.writeString(totGrossAmt);
        parcel.writeString(totAddition);
        parcel.writeString(totDeduction);
        parcel.writeString(totTaxAmt);
        parcel.writeString(roundOffAmt);
        parcel.writeString(totNetAmt);
        parcel.writeString(orderNo);
        parcel.writeString(invoiceStatus);
        parcel.writeString(remarks);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(startTime);
        parcel.writeString(endTime);
        parcel.writeString(lineCount);
        parcel.writeString(modeDate);
    }
}
