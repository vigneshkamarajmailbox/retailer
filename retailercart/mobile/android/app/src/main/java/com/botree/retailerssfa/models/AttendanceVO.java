package com.botree.retailerssfa.models;

/**
 * Created by vinothbaskaran on 23/3/18.
 */

public class AttendanceVO extends SalesmanModel {

    private String reasonCode = "";
    private String reasonName = "";
    private String remarks = "";
    private String attDate = "";
    private String reportingDayLabel = "";
    private String startTime = "";
    private String endTime = "";

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAttDate() {
        return attDate;
    }

    public void setAttDate(String attDate) {
        this.attDate = attDate;
    }

    public String getReportingDayLabel() {
        return reportingDayLabel;
    }

    public void setReportingDayLabel(String reportingDayLabel) {
        this.reportingDayLabel = reportingDayLabel;
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
}
