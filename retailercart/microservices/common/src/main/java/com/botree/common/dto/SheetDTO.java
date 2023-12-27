package com.botree.common.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is used to hold report sheet mapping data.
 * @author vinodkumar.a
 */
@XmlRootElement(name = "sheet")
public class SheetDTO {

    /** sheetno. */
    private Integer sheetno;

    /** sheetname. */
    private String sheetname;

    /** index. */
    private Integer index;

    /** header. */
    private String header;

    /** field. */
    private String field;

    /** summary. */
    private String summary;

    /** query. */
    private String query;

    /**
     * @return the sheetno
     */
    public final Integer getSheetno() {
        return sheetno;
    }

    /**
     * @param sheetnoIn the sheetno to set
     */
    public final void setSheetno(final Integer sheetnoIn) {
        sheetno = sheetnoIn;
    }

    /**
     * @return the sheetname
     */
    public final String getSheetname() {
        return sheetname;
    }

    /**
     * @param sheetnameIn the sheetname to set
     */
    public final void setSheetname(final String sheetnameIn) {
        sheetname = sheetnameIn;
    }

    /**
     * @return the index
     */
    public final Integer getIndex() {
        return index;
    }

    /**
     * @param indexIn the index to set
     */
    public final void setIndex(final Integer indexIn) {
        index = indexIn;
    }

    /**
     * @return the header
     */
    public final String getHeader() {
        return header;
    }

    /**
     * @param headerIn the header to set
     */
    public final void setHeader(final String headerIn) {
        header = headerIn;
    }

    /**
     * @return the field
     */
    public final String getField() {
        return field;
    }

    /**
     * @param fieldIn the field to set
     */
    public final void setField(final String fieldIn) {
        field = fieldIn;
    }

    /**
     * @return the summary
     */
    public final String getSummary() {
        return summary;
    }

    /**
     * @param summaryIn the summary to set
     */
    public final void setSummary(final String summaryIn) {
        summary = summaryIn;
    }

    /**
     * @return the query
     */
    public final String getQuery() {
        return query;
    }

    /**
     * @param queryIn the query to set
     */
    public final void setQuery(final String queryIn) {
        query = queryIn;
    }
}
