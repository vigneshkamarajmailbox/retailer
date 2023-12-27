package com.botree.rapp.masterengine.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This class is used to perform db operations.
 * @author vinodkumar.a
 */
@Repository
public class DAORepository implements IDAORepository {

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
     * This method is used to persist data in db using batch process.
     * @param sql  SQL query
     * @param data Data
     */
    @Override
    @Transactional
    public void update(final String sql, final List<Object[]> data) {
        jdbcTemplate.batchUpdate(sql, data);
    }

    /**
     * This method is used to execute the SQL.
     * @param sql SQL query
     */
    @Override
    public void execute(final String sql) {
        jdbcTemplate.execute(sql);
    }

    /**
     * Method to fetch the data.
     * @param query  query
     * @param clazz  clazz
     * @param params params
     * @param <T>    type
     * @return list
     */
    @Override
    public <T> List<T> queryForListWithRowMapper(final String query, final Class<T> clazz, final Object... params) {
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(clazz), params);
    }

    /**
     * Method to insert the bulk records.
     * @param query query
     * @param list  list
     */
    @Transactional
    public void bulkInsert(final String query, final List<?> list) {
        var batch = SqlParameterSourceUtils.createBatch(list.toArray());
        namedParameterJdbcTemplate.batchUpdate(query, batch);
    }

    /**
     * Method to fetch the data.
     * @param query query
     * @param clazz clazz
     * @param <T>   type
     * @return list
     */
    public <T> List<T> queryForList(final String query, final Class<T> clazz) {
        return jdbcTemplate.queryForList(query, clazz);
    }
}
