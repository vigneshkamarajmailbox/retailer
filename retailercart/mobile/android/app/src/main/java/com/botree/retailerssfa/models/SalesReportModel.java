package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class SalesReportModel implements Parcelable {

    private String cmpCode = "";
    private String distrCode = "";
    private String salesmanCode = "";
    private String salesmanName = "";
    private String routeCode = "";
    private String routeName = "";
    private String customerCode = "";
    private String customerName = "";
    private String InvoiceNo = "";
    private String InvoiceDt = "";
    private String prodCode = "";
    private String prodName = "";
    private String prodBatchCode = "";
    private double TotalInvoiceQty = 0.0d;
    private double MRP = 0.0d;
    private double sellRate = 0.0d;
    private double actualSellRate = 0.0d;
    private double grossAmt = 0.0d;
    private double splDiscAmt = 0.0d;
    private double splDiscPerc = 0.0d;
    private double schDiscAmt = 0.0d;
    private double schDiscPerc = 0.0d;
    private double cashDiscAmt = 0.0d;
    private double cashDiscPerc = 0.0d;
    private double dbDiscAmt = 0.0d;
    private double dbDiscPerc = 0.0d;
    private String inputStr = "";
    private double cgstPerc = 0.0d;
    private double cgstAmt = 0.0d;
    private double sgstPerc = 0.0d;
    private double sgstAmt = 0.0d;
    private double igstPerc = 0.0d;
    private double igstAmt = 0.0d;
    private double taxAmt = 0.0d;
    private double netAmt = 0.0d;

    public SalesReportModel() {
    }

    protected SalesReportModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        salesmanCode = in.readString();
        salesmanName = in.readString();
        routeCode = in.readString();
        routeName = in.readString();
        customerCode = in.readString();
        customerName = in.readString();
        InvoiceNo = in.readString();
        InvoiceDt = in.readString();
        prodCode = in.readString();
        prodName = in.readString();
        prodBatchCode = in.readString();
        TotalInvoiceQty = in.readDouble();
        MRP = in.readDouble();
        sellRate = in.readDouble();
        actualSellRate = in.readDouble();
        grossAmt = in.readDouble();
        splDiscAmt = in.readDouble();
        splDiscPerc = in.readDouble();
        schDiscAmt = in.readDouble();
        schDiscPerc = in.readDouble();
        cashDiscAmt = in.readDouble();
        cashDiscPerc = in.readDouble();
        dbDiscAmt = in.readDouble();
        dbDiscPerc = in.readDouble();
        inputStr = in.readString();
        cgstPerc = in.readDouble();
        cgstAmt = in.readDouble();
        sgstPerc = in.readDouble();
        sgstAmt = in.readDouble();
        igstPerc = in.readDouble();
        igstAmt = in.readDouble();
        taxAmt = in.readDouble();
        netAmt = in.readDouble();
    }

    public static final Creator<SalesReportModel> CREATOR = new Creator<SalesReportModel>() {
        @Override
        public SalesReportModel createFromParcel(Parcel in) {
            return new SalesReportModel(in);
        }

        @Override
        public SalesReportModel[] newArray(int size) {
            return new SalesReportModel[size];
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

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
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

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    public String getInvoiceDt() {
        return InvoiceDt;
    }

    public void setInvoiceDt(String invoiceDt) {
        InvoiceDt = invoiceDt;
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

    public String getProdBatchCode() {
        return prodBatchCode;
    }

    public void setProdBatchCode(String prodBatchCode) {
        this.prodBatchCode = prodBatchCode;
    }

    public double getTotalInvoiceQty() {
        return TotalInvoiceQty;
    }

    public void setTotalInvoiceQty(double totalInvoiceQty) {
        TotalInvoiceQty = totalInvoiceQty;
    }

    public double getMRP() {
        return MRP;
    }

    public void setMRP(double MRP) {
        this.MRP = MRP;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    public double getActualSellRate() {
        return actualSellRate;
    }

    public void setActualSellRate(double actualSellRate) {
        this.actualSellRate = actualSellRate;
    }

    public double getGrossAmt() {
        return grossAmt;
    }

    public void setGrossAmt(double grossAmt) {
        this.grossAmt = grossAmt;
    }

    public double getSplDiscAmt() {
        return splDiscAmt;
    }

    public void setSplDiscAmt(double splDiscAmt) {
        this.splDiscAmt = splDiscAmt;
    }

    public double getSplDiscPerc() {
        return splDiscPerc;
    }

    public void setSplDiscPerc(double splDiscPerc) {
        this.splDiscPerc = splDiscPerc;
    }

    public double getSchDiscAmt() {
        return schDiscAmt;
    }

    public void setSchDiscAmt(double schDiscAmt) {
        this.schDiscAmt = schDiscAmt;
    }

    public double getSchDiscPerc() {
        return schDiscPerc;
    }

    public void setSchDiscPerc(double schDiscPerc) {
        this.schDiscPerc = schDiscPerc;
    }

    public double getCashDiscAmt() {
        return cashDiscAmt;
    }

    public void setCashDiscAmt(double cashDiscAmt) {
        this.cashDiscAmt = cashDiscAmt;
    }

    public double getCashDiscPerc() {
        return cashDiscPerc;
    }

    public void setCashDiscPerc(double cashDiscPerc) {
        this.cashDiscPerc = cashDiscPerc;
    }

    public double getDbDiscAmt() {
        return dbDiscAmt;
    }

    public void setDbDiscAmt(double dbDiscAmt) {
        this.dbDiscAmt = dbDiscAmt;
    }

    public double getDbDiscPerc() {
        return dbDiscPerc;
    }

    public void setDbDiscPerc(double dbDiscPerc) {
        this.dbDiscPerc = dbDiscPerc;
    }

    public String getInputStr() {
        return inputStr;
    }

    public void setInputStr(String inputStr) {
        this.inputStr = inputStr;
    }

    public double getCgstPerc() {
        return cgstPerc;
    }

    public void setCgstPerc(double cgstPerc) {
        this.cgstPerc = cgstPerc;
    }

    public double getCgstAmt() {
        return cgstAmt;
    }

    public void setCgstAmt(double cgstAmt) {
        this.cgstAmt = cgstAmt;
    }

    public double getSgstPerc() {
        return sgstPerc;
    }

    public void setSgstPerc(double sgstPerc) {
        this.sgstPerc = sgstPerc;
    }

    public double getSgstAmt() {
        return sgstAmt;
    }

    public void setSgstAmt(double sgstAmt) {
        this.sgstAmt = sgstAmt;
    }

    public double getIgstPerc() {
        return igstPerc;
    }

    public void setIgstPerc(double igstPerc) {
        this.igstPerc = igstPerc;
    }

    public double getIgstAmt() {
        return igstAmt;
    }

    public void setIgstAmt(double igstAmt) {
        this.igstAmt = igstAmt;
    }

    public double getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(double taxAmt) {
        this.taxAmt = taxAmt;
    }

    public double getNetAmt() {
        return netAmt;
    }

    public void setNetAmt(double netAmt) {
        this.netAmt = netAmt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(salesmanCode);
        dest.writeString(salesmanName);
        dest.writeString(routeCode);
        dest.writeString(routeName);
        dest.writeString(customerCode);
        dest.writeString(customerName);
        dest.writeString(InvoiceNo);
        dest.writeString(InvoiceDt);
        dest.writeString(prodCode);
        dest.writeString(prodName);
        dest.writeString(prodBatchCode);
        dest.writeDouble(TotalInvoiceQty);
        dest.writeDouble(MRP);
        dest.writeDouble(sellRate);
        dest.writeDouble(actualSellRate);
        dest.writeDouble(grossAmt);
        dest.writeDouble(splDiscAmt);
        dest.writeDouble(splDiscPerc);
        dest.writeDouble(schDiscAmt);
        dest.writeDouble(schDiscPerc);
        dest.writeDouble(cashDiscAmt);
        dest.writeDouble(cashDiscPerc);
        dest.writeDouble(dbDiscAmt);
        dest.writeDouble(dbDiscPerc);
        dest.writeString(inputStr);
        dest.writeDouble(cgstPerc);
        dest.writeDouble(cgstAmt);
        dest.writeDouble(sgstPerc);
        dest.writeDouble(sgstAmt);
        dest.writeDouble(igstPerc);
        dest.writeDouble(igstAmt);
        dest.writeDouble(taxAmt);
        dest.writeDouble(netAmt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalesReportModel that = (SalesReportModel) o;

        return (Objects.equals(InvoiceNo, that.InvoiceNo));

    }


    @Override
    public int hashCode() {
        return (InvoiceNo != null ? InvoiceNo.hashCode() : 0);
    }
}
