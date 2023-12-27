package com.botree.retailerssfa.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static com.botree.retailerssfa.service.JSONConstants.TAG_MTD_FIELD_WORKING_REPORT;
import static com.botree.retailerssfa.service.JSONConstants.TAG_STOCK_LEDGER_LIST;

public class ReportDataModel {

    private static final ReportDataModel reportInstance = new ReportDataModel();
    public static ReportDataModel getInstance() {
        return reportInstance;
    }

    @SerializedName(TAG_MTD_FIELD_WORKING_REPORT)
    private List<SubStockistModel> mtdFieldWorkingList = new ArrayList<>();

    @SerializedName(TAG_STOCK_LEDGER_LIST)
    private List<StockledgerReportModel> stockLedgerList = new ArrayList<>();


    public List<SubStockistModel> getMtdFieldWorkingList() {
        return mtdFieldWorkingList;
    }

    public void setMtdFieldWorkingList(List<SubStockistModel> mtdFieldWorkingList) {
        this.mtdFieldWorkingList = mtdFieldWorkingList;
    }

    public List<StockledgerReportModel> getStockLedgerList() {
        return stockLedgerList;
    }

    public void setStockLedgerList(List<StockledgerReportModel> stockLedgerList) {
        this.stockLedgerList = stockLedgerList;
    }

    private List<DistrReportModel> reportProcessMapping = new ArrayList<>();
    private List<DistrReportModel> reportFieldMapping = new ArrayList<>();

    public List<DistrReportModel> getReportProcessMapping() {
        return reportProcessMapping;
    }

    public void setReportProcessMapping(List<DistrReportModel> reportProcessMapping) {
        this.reportProcessMapping = reportProcessMapping;
    }

    public List<DistrReportModel> getReportFieldMapping() {
        return reportFieldMapping;
    }

    public void setReportFieldMapping(List<DistrReportModel> reportFieldMapping) {
        this.reportFieldMapping = reportFieldMapping;
    }
}
