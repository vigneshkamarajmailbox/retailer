package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class PurchaseInvoiceModel implements Parcelable {
    private String cmpCode;
    private String consoleFlag;
    private String uploadFlag;
    private String modDt;
    private String distrCode;
    private String invoiceNo;
    private String grnNo;
    private String invoiceDt;
    private String grnDt;
    private String supplierCode;
    private String prodCode;
    private String prodBatchCode;
    private double purchPrice;
    private BigDecimal mrp;
    private double invoiceQty;
    private double receivedQty;
    private String uomCode;
    private double lineLevelGrossAmt;
    private double lineLevelTaxAmt;
    private double lineLevelDiscAmt;
    private double lineLevelNetAmt;
    private String grnStatus;

    private int itemCount;
    private BigDecimal orderValue;
    private String confirmStatus;
    private String confirmDate;
    private String prodName;

    public PurchaseInvoiceModel() {
    }

    protected PurchaseInvoiceModel(Parcel in) {
        cmpCode = in.readString();
        consoleFlag = in.readString();
        uploadFlag = in.readString();
        modDt = in.readString();
        distrCode = in.readString();
        invoiceNo = in.readString();
        grnNo = in.readString();
        invoiceDt = in.readString();
        grnDt = in.readString();
        supplierCode = in.readString();
        prodCode = in.readString();
        prodName = in.readString();
        prodBatchCode = in.readString();
        purchPrice = in.readDouble();
        invoiceQty = in.readDouble();
        receivedQty = in.readDouble();
        uomCode = in.readString();
        lineLevelGrossAmt = in.readDouble();
        lineLevelTaxAmt = in.readDouble();
        lineLevelDiscAmt = in.readDouble();
        lineLevelNetAmt = in.readDouble();
        grnStatus = in.readString();
        itemCount = in.readInt();
        confirmStatus = in.readString();
        confirmDate = in.readString();
    }

    public static final Creator<PurchaseInvoiceModel> CREATOR = new Creator<PurchaseInvoiceModel>() {
        @Override
        public PurchaseInvoiceModel createFromParcel(Parcel in) {
            return new PurchaseInvoiceModel(in);
        }

        @Override
        public PurchaseInvoiceModel[] newArray(int size) {
            return new PurchaseInvoiceModel[size];
        }
    };

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getConsoleFlag() {
        return consoleFlag;
    }

    public void setConsoleFlag(String consoleFlag) {
        this.consoleFlag = consoleFlag;
    }

    public String getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(String uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
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

    public String getGrnNo() {
        return grnNo;
    }

    public void setGrnNo(String grnNo) {
        this.grnNo = grnNo;
    }

    public String getInvoiceDt() {
        return invoiceDt;
    }

    public void setInvoiceDt(String invoiceDt) {
        this.invoiceDt = invoiceDt;
    }

    public String getGrnDt() {
        return grnDt;
    }

    public void setGrnDt(String grnDt) {
        this.grnDt = grnDt;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdBatchCode() {
        return prodBatchCode;
    }

    public void setProdBatchCode(String prodBatchCode) {
        this.prodBatchCode = prodBatchCode;
    }

    public double getPurchPrice() {
        return purchPrice;
    }

    public void setPurchPrice(double purchPrice) {
        this.purchPrice = purchPrice;
    }

    public BigDecimal getMrp() {
        return mrp;
    }

    public void setMrp(BigDecimal mrp) {
        this.mrp = mrp;
    }

    public double getInvoiceQty() {
        return invoiceQty;
    }

    public void setInvoiceQty(double invoiceQty) {
        this.invoiceQty = invoiceQty;
    }

    public double getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(double receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getUomCode() {
        return uomCode;
    }

    public void setUomCode(String uomCode) {
        this.uomCode = uomCode;
    }

    public double getLineLevelGrossAmt() {
        return lineLevelGrossAmt;
    }

    public void setLineLevelGrossAmt(double lineLevelGrossAmt) {
        this.lineLevelGrossAmt = lineLevelGrossAmt;
    }

    public double getLineLevelTaxAmt() {
        return lineLevelTaxAmt;
    }

    public void setLineLevelTaxAmt(double lineLevelTaxAmt) {
        this.lineLevelTaxAmt = lineLevelTaxAmt;
    }

    public double getLineLevelDiscAmt() {
        return lineLevelDiscAmt;
    }

    public void setLineLevelDiscAmt(double lineLevelDiscAmt) {
        this.lineLevelDiscAmt = lineLevelDiscAmt;
    }

    public double getLineLevelNetAmt() {
        return lineLevelNetAmt;
    }

    public void setLineLevelNetAmt(double lineLevelNetAmt) {
        this.lineLevelNetAmt = lineLevelNetAmt;
    }

    public String getGrnStatus() {
        return grnStatus;
    }

    public void setGrnStatus(String grnStatus) {
        this.grnStatus = grnStatus;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public BigDecimal getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(BigDecimal orderValue) {
        this.orderValue = orderValue;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    @Override
    public String toString() {
        return "PurchaseInvoiceModel{" +
                "cmpCode='" + cmpCode + '\'' +
                ", consoleFlag='" + consoleFlag + '\'' +
                ", uploadFlag='" + uploadFlag + '\'' +
                ", modDt='" + modDt + '\'' +
                ", distrCode='" + distrCode + '\'' +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", grnNo='" + grnNo + '\'' +
                ", invoiceDt='" + invoiceDt + '\'' +
                ", grnDt='" + grnDt + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", prodCode='" + prodCode + '\'' +
                ", prodName='" + prodName + '\'' +
                ", prodBatchCode='" + prodBatchCode + '\'' +
                ", purchPrice=" + purchPrice +
                ", mrp=" + mrp +
                ", invoiceQty=" + invoiceQty +
                ", receivedQty=" + receivedQty +
                ", uomCode='" + uomCode + '\'' +
                ", lineLevelGrossAmt=" + lineLevelGrossAmt +
                ", lineLevelTaxAmt=" + lineLevelTaxAmt +
                ", lineLevelDiscAmt=" + lineLevelDiscAmt +
                ", lineLevelNetAmt=" + lineLevelNetAmt +
                ", grnStatus='" + grnStatus + '\'' +
                ", itemCount=" + itemCount +
                ", orderValue=" + orderValue +
                ", confirmStatus='" + confirmStatus + '\'' +
                ", confirmDate='" + confirmDate + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(consoleFlag);
        dest.writeString(uploadFlag);
        dest.writeString(modDt);
        dest.writeString(distrCode);
        dest.writeString(invoiceNo);
        dest.writeString(grnNo);
        dest.writeString(invoiceDt);
        dest.writeString(grnDt);
        dest.writeString(supplierCode);
        dest.writeString(prodCode);
        dest.writeString(prodName);
        dest.writeString(prodBatchCode);
        dest.writeDouble(purchPrice);
        dest.writeDouble(invoiceQty);
        dest.writeDouble(receivedQty);
        dest.writeString(uomCode);
        dest.writeDouble(lineLevelGrossAmt);
        dest.writeDouble(lineLevelTaxAmt);
        dest.writeDouble(lineLevelDiscAmt);
        dest.writeDouble(lineLevelNetAmt);
        dest.writeString(grnStatus);
        dest.writeInt(itemCount);
        dest.writeString(confirmStatus);
        dest.writeString(confirmDate);
    }
}