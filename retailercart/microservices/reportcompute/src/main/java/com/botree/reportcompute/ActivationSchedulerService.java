package com.botree.reportcompute;

import com.botree.common.constants.ReportConstants;
import com.botree.common.constants.StringConstants;
import com.botree.common.service.IQueryService;
import com.botree.interdbentity.model.UserActivationEntity;
import com.botree.reportcompute.service.ActivationMessageService;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/**
 * Class contains the scheduler to perform the user activation logic.
 * @author vinodkumar.a
 */
@Component
public class ActivationSchedulerService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ActivationSchedulerService.class);

    /** userActivationEnable. */
    @Value("${cron.user.activation.enable}")
    private Boolean userActivationEnable;

    /** activationMessageService. */
    private final ActivationMessageService activationMessageService;

    /** queryService. */
    private final IQueryService queryService;

    /** repository. */
    private final DAORepository repository;

    /**
     * Constructor Method.
     * @param activationMessageServiceIn activationMessageServiceIn
     * @param queryServiceIn             queryServiceIn
     * @param repositoryIn               repositoryIn
     */
    public ActivationSchedulerService(final ActivationMessageService activationMessageServiceIn,
                                      final IQueryService queryServiceIn,
                                      final DAORepository repositoryIn) {
        this.activationMessageService = activationMessageServiceIn;
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
    }

    /**
     * trigger for user activation scheduler.
     */
    @Scheduled(cron = "${cron.user.activation}")
    public final void triggerActivationScheduler() {
        LOG.info("start user activation scheduler :: {}", new Date());
        if (Boolean.TRUE.equals(userActivationEnable)) {
            var userActivationList = repository.queryForList(
                    queryService.get(ReportConstants.FETCH_USERS_FOR_ACTIVATION),
                    new HashMap<>(), UserActivationEntity.class);
            userActivationList.forEach(data ->
                    activationMessageService.activationProcess(data, StringConstants.CONST_MSG_ACTIVATION,
                            StringConstants.CONST_MSG_ACTIVATION_PASSWORD)
            );
        }
        LOG.info("end user activation scheduler :: {}", new Date());
    }
}
