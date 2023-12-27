package com.botree.rapp.masterengine.service;

import com.botree.rapp.masterengine.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * This class is used to import masters from blob.
 * @author vinodkumar.a
 */
@Service
public class ImportMasterService implements IImportMasterService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ImportMasterService.class);

    /** processSequence. */
    @Value("${process.sequence}")
    private String processSequence;

    /** helperService. */
    private final ImportMasterHelperService helperService;

    /**
     * Constructor Method.
     * @param helperServiceIn helperServiceIn
     */
    public ImportMasterService(final ImportMasterHelperService helperServiceIn) {
        this.helperService = helperServiceIn;
    }

    /**
     * This class is used to import masters from path.
     */
    @Override
    public void importMaster() {
        //Load all files from input folder.
        for (String folder : Objects.requireNonNull(processSequence.split(StringConstants.REGEX_COMMA))) {
            try {
                LOG.info("folder name :: {} ", folder);
                helperService.importMasterFiles(folder);
                helperService.createAppUser();
            } catch (Exception e) {
                LOG.error("exception while import masters", e);
            }
        }
    }
}
