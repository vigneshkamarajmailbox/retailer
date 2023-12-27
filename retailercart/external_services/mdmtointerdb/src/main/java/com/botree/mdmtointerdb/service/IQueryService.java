package com.botree.mdmtointerdb.service;

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
}
