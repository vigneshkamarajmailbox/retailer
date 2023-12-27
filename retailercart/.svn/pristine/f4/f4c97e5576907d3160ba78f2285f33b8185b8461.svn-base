package com.botree.rapp.masterengine.service;

import com.botree.rapp.masterengine.dto.QueriesDTO;
import com.botree.rapp.masterengine.util.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;


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

    /**
     * This method is used to load all queries from xml.
     * @throws IOException if any i/o exception
     */
    @PostConstruct
    public final void init() throws IOException {
        String xmlStr = new String(Files.readAllBytes(new File(queryLocation).toPath()));
        QueriesDTO queries = (QueriesDTO) Function.fromXML(xmlStr, QueriesDTO.class);
        queries.getQueries().forEach(q -> queryMap.put(q.getName(), q.getQuery()));
    }

    /**
     * This method is used to get start date.
     * @param queryName queryName
     * @return string
     */
    @Override
    public final String get(final String queryName) {
        return queryMap.get(queryName);
    }
}
