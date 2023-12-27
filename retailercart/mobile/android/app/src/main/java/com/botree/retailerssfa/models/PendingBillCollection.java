package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_BALANCE_OS;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CMP_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_CUSTOMER_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_DISTR_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_INVOICE_DT;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_INVOICE_NO;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_SALESMAN_CODE;
import static com.botree.retailerssfa.db.query.IDBColumns.COLUMN_TOT_NET_AMT;

public class PendingBillCollection implements Parcelable {

    @SerializedName(COLUMN_CMP_CODE)
    private String cmpCode;
    @SerializedName(COLUMN_DISTR_CODE)
    private String distrCode;
    @SerializedName(COLUMN_INVOICE_NO)
    private String invoiceNo;
    @SerializedName(COLUMN_INVOICE_DT)
    private String invoiceDt;
    @SerializedName(COLUMN_SALESMAN_CODE)
    private String salesmanCode;
    @SerializedName(COLUMN_CUSTOMER_CODE)
    private String customerCode;
    @SerializedName(COLUMN_TOT_NET_AMT)
    private String totNetAmt;
    @SerializedName(COLUMN_BALANCE_OS)
    private String balanceOs;

    public PendingBillCollection() {
    }

    public PendingBillCollection(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        invoiceNo = in.readString();
        invoiceDt = in.readString();
        salesmanCode = in.readString();
        customerCode = in.readString();
        totNetAmt = in.readString();
        balanceOs = in.readString();
    }

    public static final Creator<PendingBillCollection> CREATOR = new Creator<PendingBillCollection>() {
        @Override
        public PendingBillCollection createFromParcel(Parcel in) {
            return new PendingBillCollection(in);
        }

        @Override
        public PendingBillCollection[] newArray(int size) {
            return new PendingBillCollection[size];
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

    public String getInvoiceDt() {
        return invoiceDt;
    }

    public void setInvoiceDt(String invoiceDt) {
        this.invoiceDt = invoiceDt;
    }

    public String getSalesmanCode() {
        return salesmanCode;
    }

    public void setSalesmanCode(String salesmanCode) {
        this.salesmanCode = salesmanCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getTotNetAmt() {
        return totNetAmt;
    }

    public void setTotNetAmt(String totNetAmt) {
        this.totNetAmt = totNetAmt;
    }

    public String getBalanceOs() {
        return balanceOs;
    }

    public void setBalanceOs(String balanceOs) {
        this.balanceOs = balanceOs;
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
        parcel.writeString(invoiceDt);
        parcel.writeString(salesmanCode);
        parcel.writeString(customerCode);
        parcel.writeString(totNetAmt);
        parcel.writeString(balanceOs);
    }
}
