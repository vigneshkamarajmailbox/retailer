package com.botree.reportcompute;

import com.botree.common.constants.StringConstants;
import com.botree.common.service.IQueryService;
import com.botree.interdbentity.model.ReportDataEntity;
import com.botree.reportcompute.service.ReportComputeService;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * This class is used to configure scheduler's.
 * @author vinodkumara
 */
@Service
public class ReportComputeSchedulerService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ReportComputeSchedulerService.class);

    /** computeEnable. */
    @Value("${cron.compute.enable}")
    private Boolean computeEnable;

    /** queryService. */
    @Autowired
    private IQueryService queryService;

    /** repository. */
    @Autowired
    private DAORepository repository;

    /** reportComputeService. */
    @Autowired
    private ReportComputeService reportComputeService;

    /**
     * trigger for report data compute.
     */
    @Scheduled(cron = "${cron.compute}")
    public final void triggerScheduler() {
        LOG.info("start compute scheduler :: {}", new Date());
        if (Boolean.TRUE.equals(computeEnable)) {
            var reportDataList = repository
                    .queryForListWithRowMapper(queryService.get(StringConstants.FETCH_REPORTDATAENTITY),
                            ReportDataEntity.class);
            reportDataList.forEach(data ->
                    reportComputeService.computeReportData(data)
            );
        }
        LOG.info("end compute scheduler :: {}", new Date());
    }
}
