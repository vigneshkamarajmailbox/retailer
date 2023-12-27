package com.botree.rapp.masterengine.dao;

import java.util.List;

/**
 * This class is used to perform db operations.
 * @author vinodkumar.a
 */
public interface IDAORepository {

    /**
     * This method is used to persist data in db using batch process.
     * @param sql  SQL query
     * @param data Data
     */
    void update(String sql, List<Object[]> data);

    /**
     * This method is used to execute the SQL.
     * @param sql SQL query
     */
    void execute(String sql);

    /**
     * Method to fetch the data.
     * @param query  query
     * @param clazz  clazz
     * @param params params
     * @param <T>    type
     * @return list
     */
    <T> List<T> queryForListWithRowMapper(String query, Class<T> clazz, Object... params);

    /**
     * Method to insert the bulk records.
     * @param query query
     * @param list  list
     */
    void bulkInsert(String query, List<?> list);

    /**
     * Method to fetch the data.
     * @param query query
     * @param clazz clazz
     * @param <T>   type
     * @return list
     */
    <T> List<T> queryForList(String query, Class<T> clazz);
}
