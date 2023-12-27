package com.botree.interdbentity.model;

import java.util.Date;

/**
 * This class is used to hold the external application to inter db details.
 * @author vinodkumar.a
 */
public class ExternalProcessDetail extends AbstractEntity {

    /** interDBProcess. */
    private String interDBProcess;

    /** processType. */
    private String processType;

    /** processEnable. */
    private String processEnable;

    /** interDBQuery. */
    private String interDBQuery;

    /** interDBUpdateQuery. */
    private String interDBUpdateQuery;

    /** entity. */
    private String entity;

    /** processSequence. */
    private Integer processSequence;

    /** processStatus. */
    private String processStatus;

    /** startDate. */
    private Date startDate;

    /** endDate. */
    private Date endDate;

    /** distributorWise. */
    private String distributorWise;

    /** incremental. */
    private String incremental;

    /** maxChangeNo. */
    private Integer maxChangeNo;

    /** intervals. */
    private String intervals;

    /**
     * @return the interDBProcess
     */
    public final String getInterDBProcess() {
        return interDBProcess;
    }

    /**
     * @param interDBProcessIn the interDBProcess to set
     */
    public final void setInterDBProcess(final String interDBProcessIn) {
        interDBProcess = interDBProcessIn;
    }

    /**
     * @return the processType
     */
    public final String getProcessType() {
        return processType;
    }

    /**
     * @param processTypeIn the processType to set
     */
    public final void setProcessType(final String processTypeIn) {
        processType = processTypeIn;
    }

    /**
     * @return the processEnable
     */
    public final String getProcessEnable() {
        return processEnable;
    }

    /**
     * @param processEnableIn the processEnable to set
     */
    public final void setProcessEnable(final String processEnableIn) {
        processEnable = processEnableIn;
    }

    /**
     * @return the interDBQuery
     */
    public final String getInterDBQuery() {
        return interDBQuery;
    }

    /**
     * @param interDBQueryIn the interDBQuery to set
     */
    public final void setInterDBQuery(final String interDBQueryIn) {
        interDBQuery = interDBQueryIn;
    }

    /**
     * @return the interDBUpdateQuery
     */
    public final String getInterDBUpdateQuery() {
        return interDBUpdateQuery;
    }

    /**
     * @param interDBUpdateQueryIn the interDBUpdateQuery to set
     */
    public final void setInterDBUpdateQuery(final String interDBUpdateQueryIn) {
        interDBUpdateQuery = interDBUpdateQueryIn;
    }

    /**
     * @return the entity
     */
    public final String getEntity() {
        return entity;
    }

    /**
     * @param entityIn the entity to set
     */
    public final void setEntity(final String entityIn) {
        entity = entityIn;
    }

    /**
     * @return the processSequence
     */
    public final Integer getProcessSequence() {
        return processSequence;
    }

    /**
     * @param processSequenceIn the processSequence to set
     */
    public final void setProcessSequence(final Integer processSequenceIn) {
        processSequence = processSequenceIn;
    }

    /**
     * @return the processStatus
     */
    public final String getProcessStatus() {
        return processStatus;
    }

    /**
     * @param processStatusIn the processStatus to set
     */
    public final void setProcessStatus(final String processStatusIn) {
        processStatus = processStatusIn;
    }

    /**
     * @return the startDate
     */
    public final Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDateIn the startDate to set
     */
    public final void setStartDate(final Date startDateIn) {
        startDate = startDateIn;
    }

    /**
     * @return the endDate
     */
    public final Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDateIn the endDate to set
     */
    public final void setEndDate(final Date endDateIn) {
        endDate = endDateIn;
    }

    /**
     * @return the distributorWise
     */
    public final String getDistributorWise() {
        return distributorWise;
    }

    /**
     * @param distributorWiseIn the distributorWise to set
     */
    public final void setDistributorWise(final String distributorWiseIn) {
        distributorWise = distributorWiseIn;
    }

    /**
     * @return the incremental
     */
    public final String getIncremental() {
        return incremental;
    }

    /**
     * @param incrementalIn the incremental to set
     */
    public final void setIncremental(final String incrementalIn) {
        incremental = incrementalIn;
    }

    /**
     * @return the maxChangeNo
     */
    public final Integer getMaxChangeNo() {
        return maxChangeNo;
    }

    /**
     * @param maxChangeNoIn the maxChangeNo to set
     */
    public final void setMaxChangeNo(final Integer maxChangeNoIn) {
        maxChangeNo = maxChangeNoIn;
    }

    /**
     * @return the intervals
     */
    public final String getIntervals() {
        return intervals;
    }

    /**
     * @param intervalsIn the intervals to set
     */
    public final void setIntervals(final String intervalsIn) {
        intervals = intervalsIn;
    }
}
