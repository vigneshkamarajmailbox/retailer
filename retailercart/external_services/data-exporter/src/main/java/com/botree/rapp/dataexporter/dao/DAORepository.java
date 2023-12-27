package com.botree.rapp.dataexporter.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * This class is used to perform db operations.
 * @author vinodkumar.a
 */
@Repository
@Transactional
public class DAORepository implements IDAORepository {

    /** jdbcTemplate. */
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor to inject dependency.
     * @param jdbcTemplateIn {@link JdbcTemplate}
     */
    public DAORepository(final JdbcTemplate jdbcTemplateIn) {
        this.jdbcTemplate = jdbcTemplateIn;
    }

    /**
     * Method to fetch the data.
     * @param sql sql
     * @return list
     */
    public List<Map<String, Object>> queryForList(final String sql) {
        return jdbcTemplate.queryForList(sql);
    }
}
