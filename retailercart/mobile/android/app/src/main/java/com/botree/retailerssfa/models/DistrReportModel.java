package com.botree.retailerssfa.models;

public class DistrReportModel {
    private String cmpCode;
    private String consoleFlag="";
    private String uploadFlag;
    private String modDt;
    private String changeNo="";
    private String userCode;
    private String processName;
    private String processType;
    private String processEnable;
    private String processData;
    private String processId;
    private String queryName;
    private String tableName;
    private String columnHeader;
    private String columnGroup;
    private String columnType;
    private String columnField;
    private String columnEnable;
    private String chartEnable;
    private String sequence;
    private boolean isEnable = false;
    private String filterEnable;
    private String columnFunction;
    private String selectedColFn;

    @Override
    public String toString() {
        return "DistrReportModel{" +
                "cmpCode='" + cmpCode + '\'' +
                ", consoleFlag='" + consoleFlag + '\'' +
                ", uploadFlag='" + uploadFlag + '\'' +
                ", modDt='" + modDt + '\'' +
                ", changeNo='" + changeNo + '\'' +
                ", userCode='" + userCode + '\'' +
                ", processName='" + processName + '\'' +
                ", processType='" + processType + '\'' +
                ", processEnable='" + processEnable + '\'' +
                ", processData='" + processData + '\'' +
                '}';
    }

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

    public String getChangeNo() {
        return changeNo;
    }

    public void setChangeNo(String changeNo) {
        this.changeNo = changeNo;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getProcessEnable() {
        return processEnable;
    }

    public void setProcessEnable(String processEnable) {
        this.processEnable = processEnable;
    }

    public String getProcessData() {
        return processData;
    }

    public void setProcessData(String processData) {
        this.processData = processData;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnHeader() {
        return columnHeader;
    }

    public void setColumnHeader(String columnHeader) {
        this.columnHeader = columnHeader;
    }

    public String getColumnGroup() {
        return columnGroup;
    }

    public void setColumnGroup(String columnGroup) {
        this.columnGroup = columnGroup;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnField() {
        return columnField;
    }

    public void setColumnField(String columnField) {
        this.columnField = columnField;
    }

    public String getColumnEnable() {
        return columnEnable;
    }

    public void setColumnEnable(String columnEnable) {
        this.columnEnable = columnEnable;
    }

    public String getChartEnable() {
        return chartEnable;
    }

    public void setChartEnable(String chartEnable) {
        this.chartEnable = chartEnable;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getFilterEnable() {
        return filterEnable;
    }

    public void setFilterEnable(String filterEnable) {
        this.filterEnable = filterEnable;
    }

    public String getColumnFunction() {
        return columnFunction;
    }

    public void setColumnFunction(String columnFunction) {
        this.columnFunction = columnFunction;
    }

    public String getSelectedColFn() {
        return selectedColFn;
    }

    public void setSelectedColFn(String selectedColFn) {
        this.selectedColFn = selectedColFn;
    }
}
