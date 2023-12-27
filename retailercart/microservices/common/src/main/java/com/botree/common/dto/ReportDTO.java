package com.botree.common.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This class is used to hold report type and mapping data.
 * @author vinodkumar.a
 */
@XmlRootElement(name = "reports")
public class ReportDTO {

    /** title. */
    private String title;

    /** reports. */
    private List<SheetDTO> sheets;

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
     * @return the sheets
     */
    @XmlElement(name = "sheets")
    public final List<SheetDTO> getSheets() {
        return sheets;
    }

    /**
     * @param sheetsIn the sheets to set
     */
    public final void setSheets(final List<SheetDTO> sheetsIn) {
        sheets = sheetsIn;
    }
}
