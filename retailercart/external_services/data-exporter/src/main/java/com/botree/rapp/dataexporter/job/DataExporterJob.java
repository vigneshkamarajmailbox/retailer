package com.botree.rapp.dataexporter.job;

import com.botree.rapp.dataexporter.service.IDataExporterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This class is used to schedule a task to export data to dashboard in a desired interval.
 * @author vinodkumar.a
 */
@Component
public class DataExporterJob {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(DataExporterJob.class);

    /** service. */
    private final IDataExporterService service;

    /** logExporterFlag. */
    @Value("${cron.log.exporter.flag}")
    private boolean logExporterFlag;

    /**
     * Constructor to inject dependencies.
     * @param serviceIn {@link IDataExporterService}
     */
    public DataExporterJob(final IDataExporterService serviceIn) {
        this.service = serviceIn;
    }

    /**
     * Method used to schedule Stocky Mobile DMS data logging application.
     */
    @Scheduled(cron = "${cron.log.exporter}")
    public final void executeLogger() {
        LOG.info("logger job started");
        if (logExporterFlag) {
            service.sendLog();
        }
        LOG.info("logger job finished");
    }
}
