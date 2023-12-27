package com.botree.common.model;

import java.util.List;
import java.util.Map;

/**
 * Export model params for export data.
 * @author vinodkumar.a
 */
public class ExportModel {

    /** cmpCode. */
    private String cmpCode;

    /** distrCode. */
    private String distrCode;

    /** userName. */
    private String userName;

    /** month. */
    private Integer month;

    /** year. */
    private Integer year;

    /** title. */
    private String title;

    /** emailId. */
    private String emailId;

    /** header. */
    private List<String> header;

    /** field. */
    private List<String> field;

    /** summary. */
    private List<String> summary;

    /** ExportData. */
    private List<Map<String, Object>> exportData;

    /** loginCode. */
    private String loginCode;

    /** exportDataMap. */
    private Map<String, List<Map<String, Object>>> exportDataMap;

    /**
     * @return the cmpCode
     */
    public final String getCmpCode() {
        return cmpCode;
    }

    /**
     * @param cmpCodeIn the cmpCode to set
     */
    public final void setCmpCode(final String cmpCodeIn) {
        cmpCode = cmpCodeIn;
    }

    /**
     * @return the distrCode
     */
    public final String getDistrCode() {
        return distrCode;
    }

    /**
     * @param distrCodeIn the distrCode to set
     */
    public final void setDistrCode(final String distrCodeIn) {
        distrCode = distrCodeIn;
    }

    /**
     * @return the userName
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * @param userNameIn the userName to set
     */
    public final void setUserName(final String userNameIn) {
        userName = userNameIn;
    }

    /**
     * @return the month
     */
    public final Integer getMonth() {
        return month;
    }

    /**
     * @param monthIn the month to set
     */
    public final void setMonth(final Integer monthIn) {
        month = monthIn;
    }

    /**
     * @return the year
     */
    public final Integer getYear() {
        return year;
    }

    /**
     * @param yearIn the year to set
     */
    public final void setYear(final Integer yearIn) {
        year = yearIn;
    }

    /**
     * @return the title
     */
    public final String getTitle() {
        return title;
    }

    /**
     * @param titleIn the title to set
     */
    public final void setTitle(final String titleIn) {
        title = titleIn;
    }

    /**
     * @return the emailId
     */
    public final String getEmailId() {
        return emailId;
    }

    /**
     * @param emailIdIn the emailId to set
     */
    public final void setEmailId(final String emailIdIn) {
        emailId = emailIdIn;
    }

    /**
     * @return the header
     */
    public final List<String> getHeader() {
        return header;
    }

    /**
     * @param headerIn the header to set
     */
    public final void setHeader(final List<String> headerIn) {
        header = headerIn;
    }

    /**
     * @return the field
     */
    public final List<String> getField() {
        return field;
    }

    /**
     * @param fieldIn the field to set
     */
    public final void setField(final List<String> fieldIn) {
        field = fieldIn;
    }

    /**
     * @return the summary
     */
    public final List<String> getSummary() {
        return summary;
    }

    /**
     * @param summaryIn the summary to set
     */
    public final void setSummary(final List<String> summaryIn) {
        summary = summaryIn;
    }

    /**
     * @return the exportData
     */
    public final List<Map<String, Object>> getExportData() {
        return exportData;
    }

    /**
     * @param exportDataIn the exportData to set
     */
    public final void setExportData(final List<Map<String, Object>> exportDataIn) {
        exportData = exportDataIn;
    }

    /**
     * @return the loginCode
     */
    public final String getLoginCode() {
        return loginCode;
    }

    /**
     * @param loginCodeIn the loginCode to set
     */
    public final void setLoginCode(final String loginCodeIn) {
        loginCode = loginCodeIn;
    }

    /**
     * @return the exportDataMap
     */
    public final Map<String, List<Map<String, Object>>> getExportDataMap() {
        return exportDataMap;
    }

    /**
     * @param exportDataMapIn the exportDataMap to set
     */
    public final void setExportDataMap(final Map<String, List<Map<String, Object>>> exportDataMapIn) {
        exportDataMap = exportDataMapIn;
    }
}
