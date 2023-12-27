package com.botree.reportcompute.service;

import com.botree.common.constants.StringConstants;
import com.botree.common.service.IQueryService;
import com.botree.common.util.Function;
import com.botree.interdbentity.model.ReportDataEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * This class is used to compute the report data.
 * @author vinodkumara
 */
@Service
@Transactional
public class ReportComputeService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ReportComputeService.class);

    /** queryService. */
    @Autowired
    private IQueryService queryService;

    /** repository. */
    @Autowired
    private DAORepository repository;

    /**
     * Method to compute report and persist in DB.
     * @param data data
     */
    @Transactional
    public void computeReportData(final ReportDataEntity data) {
        var queryName = StringConstants.FETCH_REPORT + data.getProcessName() + StringConstants.CONST_UNDER_SCORE
                + data.getProcessType();
        LOG.info("start compute report data :: {} {} ", data.getUserCode(), queryName);
        var list = repository.queryForList(queryService.get(queryName),
                Map.of(StringConstants.CMPCODE, data.getCmpCode(), StringConstants.APPUSERCODE, data.getUserCode()));
        data.setProcessData(Function.compress(list, true));
        repository.updateStatus(queryService.get(StringConstants.UPDATE_REPORTDATAENTITY),
                Map.of(StringConstants.CMPCODE, data.getCmpCode(), StringConstants.APPUSERCODE, data.getUserCode(),
                        StringConstants.PROCESSNAME, data.getProcessName(), StringConstants.PROCESSTYPE,
                        data.getProcessType(), StringConstants.PROCESSDATA, data.getProcessData()));
        LOG.info("end compute report data :: {} {} {} ", data.getUserCode(), queryName, list.size());
    }
}
