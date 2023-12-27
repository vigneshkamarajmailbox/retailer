package com.botree.rapp.masterengine.service;

/**
 * This class is used to load queries.
 * @author vinodkumar.a
 */
public interface IQueryService {

    /**
     * This method is used to get start date.
     * @param queryName queryName
     * @return string
     */
    String get(String queryName);
}
