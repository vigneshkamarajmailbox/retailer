package com.botree.retailerssfa.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class MTDReportModel implements Parcelable {

    private String cmpCode;
    private String distrCode;
    private String monthName;
    private Integer invoiceDay = 0;
    private Double totNetAmt = 0.00;
    private String invoiceWeek;
    private String customerCode = "";
    private String customerName = "";
    private String salesmanCode = "";
    private String salesmanName = "";
    private String routeCode = "";
    private String routeName = "";
    private String channelCode = "";
    private String channelName = "";
    private String subChannelCode = "";
    private String subChannelName = "";
    private String groupCode = "";
    private String groupName = "";
    private String classCode = "";
    private String className = "";
    private Integer invoiceCount = 0;
    private Integer noOfCustomer = 0;
    private Integer totalInvoiceQty = 0;
    private String levelCode1 = "";
    private String levelCode2 = "";
    private String levelCode3 = "";
    private String levelCode4 = "";
    private String levelCode5 = "";
    private String levelCode6 = "";
    private String levelCode7 = "";
    private String levelCode8 = "";
    private String levelCode9 = "";
    private String levelCode10 = "";
    private String levelCode11 = "";
    private String levelCode12 = "";
    private String levelCode13 = "";
    private String levelCode14 = "";
    private String levelCode15 = "";
    private String levelName1 = "";
    private String levelName2 = "";
    private String levelName3 = "";
    private String levelName4 = "";
    private String levelName5 = "";
    private String levelName6 = "";
    private String levelName7 = "";
    private String levelName8 = "";
    private String levelName9 = "";
    private String levelName10 = "";
    private String levelName11 = "";
    private String levelName12 = "";
    private String levelName13 = "";
    private String levelName14 = "";
    private String levelName15 = "";
    private String prodCode = "";
    private String prodName = "";
    private Map<String, Integer> conversionFactor = new HashMap<>();

    public MTDReportModel() {}

    protected MTDReportModel(Parcel in) {
        cmpCode = in.readString();
        distrCode = in.readString();
        monthName = in.readString();
        if (in.readByte() == 0) {
            invoiceDay = null;
        } else {
            invoiceDay = in.readInt();
        }
        if (in.readByte() == 0) {
            totNetAmt = null;
        } else {
            totNetAmt = in.readDouble();
        }
        invoiceWeek = in.readString();
        customerCode = in.readString();
        customerName = in.readString();
        salesmanCode = in.readString();
        salesmanName = in.readString();
        routeCode = in.readString();
        routeName = in.readString();
        channelCode = in.readString();
        channelName = in.readString();
        subChannelCode = in.readString();
        subChannelName = in.readString();
        groupCode = in.readString();
        groupName = in.readString();
        classCode = in.readString();
        className = in.readString();
        if (in.readByte() == 0) {
            invoiceCount = null;
        } else {
            invoiceCount = in.readInt();
        }
        if (in.readByte() == 0) {
            noOfCustomer = null;
        } else {
            noOfCustomer = in.readInt();
        }
        if (in.readByte() == 0) {
            totalInvoiceQty = null;
        } else {
            totalInvoiceQty = in.readInt();
        }
        levelCode1 = in.readString();
        levelCode2 = in.readString();
        levelCode3 = in.readString();
        levelCode4 = in.readString();
        levelCode5 = in.readString();
        levelCode6 = in.readString();
        levelCode7 = in.readString();
        levelCode8 = in.readString();
        levelCode9 = in.readString();
        levelCode10 = in.readString();
        levelCode11 = in.readString();
        levelCode12 = in.readString();
        levelCode13 = in.readString();
        levelCode14 = in.readString();
        levelCode15 = in.readString();
        levelName1 = in.readString();
        levelName2 = in.readString();
        levelName3 = in.readString();
        levelName4 = in.readString();
        levelName5 = in.readString();
        levelName6 = in.readString();
        levelName7 = in.readString();
        levelName8 = in.readString();
        levelName9 = in.readString();
        levelName10 = in.readString();
        levelName11 = in.readString();
        levelName12 = in.readString();
        levelName13 = in.readString();
        levelName14 = in.readString();
        levelName15 = in.readString();
        prodCode = in.readString();
        prodName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmpCode);
        dest.writeString(distrCode);
        dest.writeString(monthName);
        if (invoiceDay == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(invoiceDay);
        }
        if (totNetAmt == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(totNetAmt);
        }
        dest.writeString(invoiceWeek);
        dest.writeString(customerCode);
        dest.writeString(customerName);
        dest.writeString(salesmanCode);
        dest.writeString(salesmanName);
        dest.writeString(routeCode);
        dest.writeString(routeName);
        dest.writeString(channelCode);
        dest.writeString(channelName);
        dest.writeString(subChannelCode);
        dest.writeString(subChannelName);
        dest.writeString(groupCode);
        dest.writeString(groupName);
        dest.writeString(classCode);
        dest.writeString(className);
        if (invoiceCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(invoiceCount);
        }
        if (noOfCustomer == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(noOfCustomer);
        }
        if (totalInvoiceQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalInvoiceQty);
        }
        dest.writeString(levelCode1);
        dest.writeString(levelCode2);
        dest.writeString(levelCode3);
        dest.writeString(levelCode4);
        dest.writeString(levelCode5);
        dest.writeString(levelCode6);
        dest.writeString(levelCode7);
        dest.writeString(levelCode8);
        dest.writeString(levelCode9);
        dest.writeString(levelCode10);
        dest.writeString(levelCode11);
        dest.writeString(levelCode12);
        dest.writeString(levelCode13);
        dest.writeString(levelCode14);
        dest.writeString(levelCode15);
        dest.writeString(levelName1);
        dest.writeString(levelName2);
        dest.writeString(levelName3);
        dest.writeString(levelName4);
        dest.writeString(levelName5);
        dest.writeString(levelName6);
        dest.writeString(levelName7);
        dest.writeString(levelName8);
        dest.writeString(levelName9);
        dest.writeString(levelName10);
        dest.writeString(levelName11);
        dest.writeString(levelName12);
        dest.writeString(levelName13);
        dest.writeString(levelName14);
        dest.writeString(levelName15);
        dest.writeString(prodCode);
        dest.writeString(prodName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MTDReportModel> CREATOR = new Creator<MTDReportModel>() {
        @Override
        public MTDReportModel createFromParcel(Parcel in) {
            return new MTDReportModel(in);
        }

        @Override
        public MTDReportModel[] newArray(int size) {
            return new MTDReportModel[size];
        }
    };

    public Map<String, Integer> getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(Map<String, Integer> conversionFactor) {
        this.conversionFactor = conversionFactor;
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

    public Integer getTotalInvoiceQty() {
        return totalInvoiceQty;
    }

    public void setTotalInvoiceQty(Integer totalInvoiceQty) {
        this.totalInvoiceQty = totalInvoiceQty;
    }

    public String getLevelCode1() {
        return levelCode1;
    }

    public void setLevelCode1(String levelCode1) {
        this.levelCode1 = levelCode1;
    }

    public String getLevelCode2() {
        return levelCode2;
    }

    public void setLevelCode2(String levelCode2) {
        this.levelCode2 = levelCode2;
    }

    public String getLevelCode3() {
        return levelCode3;
    }

    public void setLevelCode3(String levelCode3) {
        this.levelCode3 = levelCode3;
    }

    public String getLevelCode4() {
        return levelCode4;
    }

    public void setLevelCode4(String levelCode4) {
        this.levelCode4 = levelCode4;
    }

    public String getLevelCode5() {
        return levelCode5;
    }

    public void setLevelCode5(String levelCode5) {
        this.levelCode5 = levelCode5;
    }

    public String getLevelCode6() {
        return levelCode6;
    }

    public void setLevelCode6(String levelCode6) {
        this.levelCode6 = levelCode6;
    }

    public String getLevelCode7() {
        return levelCode7;
    }

    public void setLevelCode7(String levelCode7) {
        this.levelCode7 = levelCode7;
    }

    public String getLevelCode8() {
        return levelCode8;
    }

    public void setLevelCode8(String levelCode8) {
        this.levelCode8 = levelCode8;
    }

    public String getLevelCode9() {
        return levelCode9;
    }

    public void setLevelCode9(String levelCode9) {
        this.levelCode9 = levelCode9;
    }

    public String getLevelCode10() {
        return levelCode10;
    }

    public void setLevelCode10(String levelCode10) {
        this.levelCode10 = levelCode10;
    }

    public String getLevelCode11() {
        return levelCode11;
    }

    public void setLevelCode11(String levelCode11) {
        this.levelCode11 = levelCode11;
    }

    public String getLevelCode12() {
        return levelCode12;
    }

    public void setLevelCode12(String levelCode12) {
        this.levelCode12 = levelCode12;
    }

    public String getLevelCode13() {
        return levelCode13;
    }

    public void setLevelCode13(String levelCode13) {
        this.levelCode13 = levelCode13;
    }

    public String getLevelCode14() {
        return levelCode14;
    }

    public void setLevelCode14(String levelCode14) {
        this.levelCode14 = levelCode14;
    }

    public String getLevelCode15() {
        return levelCode15;
    }

    public void setLevelCode15(String levelCode15) {
        this.levelCode15 = levelCode15;
    }

    public String getLevelName1() {
        return levelName1;
    }

    public void setLevelName1(String levelName1) {
        this.levelName1 = levelName1;
    }

    public String getLevelName2() {
        return levelName2;
    }

    public void setLevelName2(String levelName2) {
        this.levelName2 = levelName2;
    }

    public String getLevelName3() {
        return levelName3;
    }

    public void setLevelName3(String levelName3) {
        this.levelName3 = levelName3;
    }

    public String getLevelName4() {
        return levelName4;
    }

    public void setLevelName4(String levelName4) {
        this.levelName4 = levelName4;
    }

    public String getLevelName5() {
        return levelName5;
    }

    public void setLevelName5(String levelName5) {
        this.levelName5 = levelName5;
    }

    public String getLevelName6() {
        return levelName6;
    }

    public void setLevelName6(String levelName6) {
        this.levelName6 = levelName6;
    }

    public String getLevelName7() {
        return levelName7;
    }

    public void setLevelName7(String levelName7) {
        this.levelName7 = levelName7;
    }

    public String getLevelName8() {
        return levelName8;
    }

    public void setLevelName8(String levelName8) {
        this.levelName8 = levelName8;
    }

    public String getLevelName9() {
        return levelName9;
    }

    public void setLevelName9(String levelName9) {
        this.levelName9 = levelName9;
    }

    public String getLevelName10() {
        return levelName10;
    }

    public void setLevelName10(String levelName10) {
        this.levelName10 = levelName10;
    }

    public String getLevelName11() {
        return levelName11;
    }

    public void setLevelName11(String levelName11) {
        this.levelName11 = levelName11;
    }

    public String getLevelName12() {
        return levelName12;
    }

    public void setLevelName12(String levelName12) {
        this.levelName12 = levelName12;
    }

    public String getLevelName13() {
        return levelName13;
    }

    public void setLevelName13(String levelName13) {
        this.levelName13 = levelName13;
    }

    public String getLevelName14() {
        return levelName14;
    }

    public void setLevelName14(String levelName14) {
        this.levelName14 = levelName14;
    }

    public String getLevelName15() {
        return levelName15;
    }

    public void setLevelName15(String levelName15) {
        this.levelName15 = levelName15;
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

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Integer getInvoiceDay() {
        return invoiceDay;
    }

    public void setInvoiceDay(Integer invoiceDay) {
        this.invoiceDay = invoiceDay;
    }

    public Double getTotNetAmt() {
        return totNetAmt;
    }

    public void setTotNetAmt(Double totNetAmt) {
        this.totNetAmt = totNetAmt;
    }

    public String getInvoiceWeek() {
        return invoiceWeek;
    }

    public void setInvoiceWeek(String invoiceWeek) {
        this.invoiceWeek = invoiceWeek;
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

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(Integer invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public Integer getNoOfCustomer() {
        return noOfCustomer;
    }

    public void setNoOfCustomer(Integer noOfCustomer) {
        this.noOfCustomer = noOfCustomer;
    }

    @Override
    public String toString() {
        return "MTDReportModel{" +
                "cmpCode='" + cmpCode + '\'' +
                ", distrCode='" + distrCode + '\'' +
                ", monthName='" + monthName + '\'' +
                ", invoiceDay=" + invoiceDay +
                ", totNetAmt=" + totNetAmt +
                ", invoiceWeek='" + invoiceWeek + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", customerName='" + customerName + '\'' +
                ", salesmanCode='" + salesmanCode + '\'' +
                ", salesmanName='" + salesmanName + '\'' +
                ", routeCode='" + routeCode + '\'' +
                ", routeName='" + routeName + '\'' +
                ", channelCode='" + channelCode + '\'' +
                ", channelName='" + channelName + '\'' +
                ", subChannelCode='" + subChannelCode + '\'' +
                ", subChannelName='" + subChannelName + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", groupName='" + groupName + '\'' +
                ", classCode='" + classCode + '\'' +
                ", className='" + className + '\'' +
                ", invoiceCount=" + invoiceCount +
                ", noOfCustomer=" + noOfCustomer +
                ", totalInvoiceQty=" + totalInvoiceQty +
                ", levelCode1='" + levelCode1 + '\'' +
                ", levelCode2='" + levelCode2 + '\'' +
                ", levelCode3='" + levelCode3 + '\'' +
                ", levelCode4='" + levelCode4 + '\'' +
                ", levelCode5='" + levelCode5 + '\'' +
                ", levelCode6='" + levelCode6 + '\'' +
                ", levelCode7='" + levelCode7 + '\'' +
                ", levelCode8='" + levelCode8 + '\'' +
                ", levelCode9='" + levelCode9 + '\'' +
                ", levelCode10='" + levelCode10 + '\'' +
                ", levelCode11='" + levelCode11 + '\'' +
                ", levelCode12='" + levelCode12 + '\'' +
                ", levelCode13='" + levelCode13 + '\'' +
                ", levelCode14='" + levelCode14 + '\'' +
                ", levelCode15='" + levelCode15 + '\'' +
                ", levelName1='" + levelName1 + '\'' +
                ", levelName2='" + levelName2 + '\'' +
                ", levelName3='" + levelName3 + '\'' +
                ", levelName4='" + levelName4 + '\'' +
                ", levelName5='" + levelName5 + '\'' +
                ", levelName6='" + levelName6 + '\'' +
                ", levelName7='" + levelName7 + '\'' +
                ", levelName8='" + levelName8 + '\'' +
                ", levelName9='" + levelName9 + '\'' +
                ", levelName10='" + levelName10 + '\'' +
                ", levelName11='" + levelName11 + '\'' +
                ", levelName12='" + levelName12 + '\'' +
                ", levelName13='" + levelName13 + '\'' +
                ", levelName14='" + levelName14 + '\'' +
                ", levelName15='" + levelName15 + '\'' +
                ", prodCode='" + prodCode + '\'' +
                ", prodName='" + prodName + '\'' +
                '}';
    }
}
