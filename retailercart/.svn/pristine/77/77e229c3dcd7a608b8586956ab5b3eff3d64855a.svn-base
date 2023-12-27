package com.botree.common.service;

import com.botree.common.dto.ConfigDTO;
import com.botree.common.dto.QueriesDTO;
import com.botree.common.dto.SheetDTO;
import com.botree.common.util.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
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

    /** configMap. */
    private final Map<String, List<SheetDTO>> configMap = new HashMap<>();

    /** queryLocation. */
    @Value("${query.location}")
    private String queryLocation;

    /** configLocation. */
    @Value("${config.location}")
    private String configLocation;

    /**
     * This method is used to load all queries and report config from xml.
     * @throws IOException if any i/o exception
     */
    @PostConstruct
    public final void init() throws IOException {
        // load queries
        var commonQueryPath = new File(queryLocation);
        for (var file : Objects.requireNonNull(commonQueryPath.listFiles(f -> f.getName().endsWith(".xml")))) {
            addQueries(file);
        }
        var reportStr = new String(Files.readAllBytes(new File(configLocation).toPath()));
        var reports = (ConfigDTO) Function.fromXML(reportStr, ConfigDTO.class);
        reports.getReports().forEach(r -> configMap.put(r.getTitle(), r.getSheets()));
    }

    /**
     * Method to add queires.
     * @param file file
     * @throws IOException if any i/o exception
     */
    private void addQueries(final File file) throws IOException {
        var xmlStr = new String(Files.readAllBytes(file.toPath()));
        var queries = (QueriesDTO) Function.fromXML(xmlStr, QueriesDTO.class);
        queries.getQueries().forEach(q -> queryMap.put(q.getName(), q.getQuery()));
    }

    /**
     * This method is used to get query.
     * @param queryName queryName
     * @return SQL query
     */
    @Override
    public final String get(final String queryName) {
        return queryMap.get(queryName);
    }

    /**
     * This method is used to get report configuration.
     * @param reportName reportName
     * @return sheet details
     */
    @Override
    public List<SheetDTO> getReport(final String reportName) {
        return configMap.get(reportName);
    }
}
