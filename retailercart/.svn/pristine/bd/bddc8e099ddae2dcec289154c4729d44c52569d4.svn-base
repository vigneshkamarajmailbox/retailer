package com.botree.rapp.masterengine.jobs;

import com.botree.rapp.masterengine.service.IImportMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This class is used to schedule ImportService in a desired interval.
 * @author vinodkumar.a
 */
@Component
public class ImportMasterJob {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ImportMasterJob.class);

    /** service. */
    private final IImportMasterService service;

    /**
     * Constructor Method.
     * @param serviceIn serviceIn
     */
    public ImportMasterJob(final IImportMasterService serviceIn) {
        this.service = serviceIn;
    }

    /**
     * This method is used to schedule ImportService in a desired interval.
     */
    @Scheduled(cron = "${import.master.corn}")
    public final void process() {
        LOG.info("import master job started");
        try {
            service.importMaster();
        } catch (Exception e) {
            LOG.error("exception while process data ", e);
        }
        LOG.info("import master job finished");
    }
}
