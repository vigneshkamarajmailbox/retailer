package com.botree.common.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This class is used to hold report default data.
 * @author vinodkumar.a
 */
@XmlRootElement(name = "config")
public class ConfigDTO {

    /** reports. */
    private List<ReportDTO> reports;

    /**
     * @return the reports
     */
    @XmlElement(name = "reports", type = ReportDTO.class)
    public final List<ReportDTO> getReports() {
        return reports;
    }

    /**
     * @param reportsIn the reports to set
     */
    public final void setReports(final List<ReportDTO> reportsIn) {
        reports = reportsIn;
    }
}
