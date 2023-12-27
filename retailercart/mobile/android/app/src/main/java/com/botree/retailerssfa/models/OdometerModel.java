package com.botree.retailerssfa.models;

public class OdometerModel {

    private String latitude = "";
    private String longitude = "";

    private String distrCode = "";
    private String salesManCode = "";
    private String refCode = "";

    private String startLatitude = "";
    private String startLongitude = "";
    private String endLatitude = "";
    private String endLongitude = "";
    private String startTime = "";
    private String startTimeMillis = "";
    private String endTime = "";
    private String endTimeMillis = "";
    private String totalKms = "";

    private String date = "";

    private String manualStartMeterReading = "";
    private String manualEndMeterReading = "";
    private String manualMeterReadingDiff = "";
    private String totalReadingKms = "";

    private int id = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(String distrCode) {
        this.distrCode = distrCode;
    }

    public String getSalesManCode() {
        return salesManCode;
    }

    public void setSalesManCode(String salesManCode) {
        this.salesManCode = salesManCode;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        this.startLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(String endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude;
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

    public String getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(String startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public String getEndTimeMillis() {
        return endTimeMillis;
    }

    public void setEndTimeMillis(String endTimeMillis) {
        this.endTimeMillis = endTimeMillis;
    }

    public String getTotalKms() {
        return totalKms;
    }

    public void setTotalKms(String totalKms) {
        this.totalKms = totalKms;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getManualStartMeterReading() {
        return manualStartMeterReading;
    }

    public void setManualStartMeterReading(String manualStartMeterReading) {
        this.manualStartMeterReading = manualStartMeterReading;
    }

    public String getManualEndMeterReading() {
        return manualEndMeterReading;
    }

    public void setManualEndMeterReading(String manualEndMeterReading) {
        this.manualEndMeterReading = manualEndMeterReading;
    }

    public String getManualMeterReadingDiff() {
        return manualMeterReadingDiff;
    }

    public void setManualMeterReadingDiff(String manualMeterReadingDiff) {
        this.manualMeterReadingDiff = manualMeterReadingDiff;
    }

    public String getTotalReadingKms() {
        return totalReadingKms;
    }

    public void setTotalReadingKms(String totalReadingKms) {
        this.totalReadingKms = totalReadingKms;
    }
}
