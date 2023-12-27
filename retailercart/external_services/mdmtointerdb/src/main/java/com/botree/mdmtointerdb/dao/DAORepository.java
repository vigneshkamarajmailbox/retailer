package com.botree.mdmtointerdb.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * This class is used to perform db operations.
 * @author vinodkumara
 */
@Repository
public class DAORepository {

    /** jdbcTemplate. */
    private final JdbcTemplate jdbcTemplate;

    /** namedParameterJdbcTemplate. */
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructor Method.
     * @param jdbcTemplateIn               jdbcTemplateIn
     * @param namedParameterJdbcTemplateIn namedParameterJdbcTemplateIn
     */
    public DAORepository(final JdbcTemplate jdbcTemplateIn,
                         final NamedParameterJdbcTemplate namedParameterJdbcTemplateIn) {
        this.jdbcTemplate = jdbcTemplateIn;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplateIn;
    }


    /**
     * Method to fetch the respective data.
     * @param <T>    class type
     * @param query  query
     * @param params params
     * @param clazz  class
     * @return response
     */
    public <T> T queryForObject(final String query, final Class<T> clazz, final Object... params) {
        return jdbcTemplate.queryForObject(query, clazz, params);
    }

    /**
     * Method to insert the bulk records.
     * @param query query
     * @param list  list
     */
    @SuppressWarnings("rawtypes")
    @Transactional
    public void bulkInsert(final String query, final List list) {
        saveOrUpdate(query, list);
    }

    /**
     * Method to update the status.
     * @param query query
     * @param list  list
     */
    @SuppressWarnings("rawtypes")
    @Transactional
    public void updateStatus(final String query, final List list) {
        saveOrUpdate(query, list);
    }

    /**
     * Method to save or update.
     * @param query query
     * @param list  list
     */
    @SuppressWarnings("rawtypes")
    private void saveOrUpdate(final String query, final List list) {
        var batch = SqlParameterSourceUtils.createBatch(list.toArray());
        namedParameterJdbcTemplate.batchUpdate(query, batch);
    }

    /**
     * Method to fetch the data.
     * @param query  query
     * @param params params
     * @param clazz  clazz
     * @param <T>    type
     * @return list
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List<T> queryForList(final String query, final Map<String, Object> params, final Class<T> clazz) {
        return namedParameterJdbcTemplate.query(query, params, new BeanPropertyRowMapper(clazz));
    }

    /**
     * Method to fetch the data.
     * @param query  query
     * @param clazz  clazz
     * @param params params
     * @param <T>    type
     * @return list
     */
    public <T> List<T> queryForListWithRowMapper(final String query, final Class<T> clazz, final Object... params) {
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(clazz), params);
    }

    /**
     * Method to fetch the data.
     * @param query  query
     * @param params params
     * @param clazz  clazz
     * @param <T>    type
     * @return list
     */
    public <T> List<T> queryForListWrapper(final String query, final Map<String, Object> params, final Class<T> clazz) {
        return namedParameterJdbcTemplate.queryForList(query, params, clazz);
    }
}
