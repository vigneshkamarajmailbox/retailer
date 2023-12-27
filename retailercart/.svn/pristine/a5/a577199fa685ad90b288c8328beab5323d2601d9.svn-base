package com.botree.reportcompute;

import com.botree.common.constants.ReportConstants;
import com.botree.common.model.ExportModel;
import com.botree.common.service.IQueryService;
import com.botree.interdbentity.model.CompanyUserEntity;
import com.botree.reportcompute.service.ExportService;
import com.botree.reportcompute.service.UsageReportMessageService;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class contains the scheduler to perform the users usage report logic.
 * @author vinodkumar.a
 */
@Component
public class UsageReportSchedulerService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(UsageReportSchedulerService.class);

    /** userUsageEnable. */
    @Value("${cron.user.usage.enable}")
    private Boolean userUsageEnable;

    /** userUsageEmailEnable. */
    @Value("${cron.user.usage.email.enable}")
    private Boolean userUsageEmailEnable;

    /** usageReportMessageService. */
    private final UsageReportMessageService usageReportMessageService;

    /** queryService. */
    private final IQueryService queryService;

    /** repository. */
    private final DAORepository repository;

    /** exportService. */
    private final ExportService exportService;

    /**
     * Constructor Method.
     * @param usageReportMessageServiceIn usageReportMessageServiceIn
     * @param queryServiceIn              queryServiceIn
     * @param repositoryIn                repositoryIn
     * @param exportServiceIn             exportServiceIn
     */
    public UsageReportSchedulerService(final UsageReportMessageService usageReportMessageServiceIn,
                                       final IQueryService queryServiceIn, final DAORepository repositoryIn,
                                       final ExportService exportServiceIn) {
        this.usageReportMessageService = usageReportMessageServiceIn;
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
        this.exportService = exportServiceIn;
    }

    /**
     * trigger for user usage report scheduler.
     */
    @Scheduled(cron = "${cron.user.usage}")
    public final void triggerUsageReportScheduler() {
        LOG.info("start user usage report scheduler :: {}", new Date());
        if (Boolean.TRUE.equals(userUsageEnable)) {
            var usageList = repository.queryForList(queryService.get(ReportConstants.FETCH_USERS_USAGE_REPORT_TODAY));
            usageList.forEach(usageReportMessageService::usageMessageProcess);
        }
        LOG.info("end user usage report scheduler :: {}", new Date());
    }

    /**
     * trigger for user usage report scheduler for email.
     */
    @Scheduled(cron = "${cron.user.usage.email}")
    public final void triggerUsageReportEmailScheduler() {
        LOG.info("start user usage report email scheduler :: {}", new Date());
        if (Boolean.TRUE.equals(userUsageEmailEnable)) {
            var orderedMap = repository.queryForList(queryService.get(
                    ReportConstants.FETCH_ORDERED_RETAILERS_EMAIL_TEMPLATE)).stream().collect(
                    Collectors.groupingBy(d -> d.get(ReportConstants.REPORT_COL_LOGINCODE)));
            var unOrderedMap = repository.queryForList(queryService.get(
                    ReportConstants.FETCH_NOT_ORDERED_RETAILERS_EMAIL_TEMPLATE)).stream().collect(
                    Collectors.groupingBy(d -> d.get(ReportConstants.REPORT_COL_LOGINCODE)));

            var userList = repository.queryForList(queryService.get(
                    ReportConstants.FETCH_USERS_FOR_EMAIL), new HashMap<>(), CompanyUserEntity.class);
            userList.forEach(data -> {
                var map = new HashMap<String, List<Map<String, Object>>>();
                map.put(ReportConstants.FETCH_ORDERED_RETAILERS_EMAIL_TEMPLATE, orderedMap.get(data.getLoginCode()));
                map.put(ReportConstants.FETCH_NOT_ORDERED_RETAILERS_EMAIL_TEMPLATE,
                        unOrderedMap.get(data.getLoginCode()));
                var exportModel = new ExportModel();
                exportModel.setTitle(ReportConstants.USAGE_REPORT);
                exportModel.setCmpCode(data.getCmpCode());
                exportModel.setLoginCode(data.getLoginCode());
                exportModel.setUserName(data.getUserName());
                exportModel.setEmailId(data.getEmailId());
                exportModel.setExportDataMap(map);
                exportService.extractReportFile(exportModel);
            });
        }
        LOG.info("end user usage report email scheduler :: {}", new Date());
    }
}
