package com.botree.common.service;

import com.botree.common.dto.SheetDTO;

import java.util.List;

/**
 * This class is used to load queries and report config.
 * @author vinodkumar.a
 */
public interface IQueryService {
    /**
     * This method is used to get the query.
     * @param queryName queryName
     * @return string
     */
    String get(String queryName);

    /**
     * This method is used to get the query report configuration.
     * @param reportName reportName
     * @return shett list
     */
    List<SheetDTO> getReport(String reportName);
}
