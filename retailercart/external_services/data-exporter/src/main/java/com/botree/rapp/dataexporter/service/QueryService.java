package com.botree.rapp.dataexporter.service;

import com.botree.rapp.dataexporter.dto.QueriesDTO;
import com.botree.rapp.dataexporter.util.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class is used to load queries.
 * @author vinodkumar.a
 */
@Service
public class QueryService implements IQueryService {

    /** queryMap. */
    private final Map<String, String> queryMap = new HashMap<>();

    /** queryLocation. */
    @Value("${query.location}")
    private String queryLocation;

    /** companyCode. */
    @Value("${company.code}")
    private String companyCode;

    /**
     * This method is used to load all queries from xml.
     * @throws IOException if any i/o exception
     */
    @PostConstruct
    public final void init() throws IOException {
        // load client specific query
        var clientQueryPath = new File(queryLocation + File.separator + companyCode);
        for (var file : Objects.requireNonNull(clientQueryPath.listFiles(f -> f.getName().endsWith(".xml")))) {
            loadQueries(file);
        }
    }

    /**
     * This method is used to load SQL queries from external file.
     * @param file Query File
     * @throws IOException if any i/o exception
     */
    private void loadQueries(final File file) throws IOException {
        var xmlStr = new String(Files.readAllBytes(file.toPath()));
        var queries = (QueriesDTO) Function.fromXML(xmlStr, QueriesDTO.class);
        queries.getQueries().forEach(q -> queryMap.put(q.getName(), q.getQuery()));
    }

    /**
     * This method is used to get SQL query by name.
     * @param queryName Query name
     * @return SQL query
     */
    @Override
    public final String get(final String queryName) {
        return queryMap.get(queryName);
    }
}
