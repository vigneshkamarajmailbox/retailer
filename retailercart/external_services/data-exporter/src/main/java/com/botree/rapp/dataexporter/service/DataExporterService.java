package com.botree.rapp.dataexporter.service;

import com.botree.rapp.dataexporter.constants.StringConstants;
import com.botree.rapp.dataexporter.dao.IDAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * This service class is used to export data to external system.
 * @author vinodkumar.a
 */
@Service
public class DataExporterService implements IDataExporterService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(DataExporterService.class);

    /** dao. */
    private final IDAORepository dao;

    /** queryService. */
    private final IQueryService queryService;

    /** loggerUrl. */
    @Value("${log.exporter.url}")
    private String loggerUrl;

    /** appName. */
    @Value("${app.name}")
    private String appName;

    /**
     * Constructor to inject dependencies.
     * @param daoIn          {@link IDAORepository}
     * @param queryServiceIn {@link IQueryService}
     */
    public DataExporterService(final IDAORepository daoIn, final IQueryService queryServiceIn) {
        this.dao = daoIn;
        this.queryService = queryServiceIn;
    }

    /**
     * Method used to fetch and send user activity to log application.
     */
    @Override
    public void sendLog() {
        List<Map<String, Object>> dataList = dao.queryForList(queryService.get(StringConstants.FETCH_USER_LOGGING));
        for (Map<String, Object> dataMap : dataList) {
            RestTemplate template = new RestTemplate();
            String log = StringConstants.SERVER_PARAM_1 + appName
                    + StringConstants.LOGGER_PARAM_1 + dataMap.get(StringConstants.QUERY_RS_1)
                    + StringConstants.LOGGER_PARAM_2 + dataMap.get(StringConstants.QUERY_RS_2)
                    + StringConstants.LOGGER_PARAM_3 + dataMap.get(StringConstants.QUERY_RS_3)
                    + StringConstants.LOGGER_PARAM_4 + dataMap.get(StringConstants.QUERY_RS_4);
            LOG.info("logger info :: {} ", log);
            template.postForLocation(loggerUrl, log);
        }
    }
}
