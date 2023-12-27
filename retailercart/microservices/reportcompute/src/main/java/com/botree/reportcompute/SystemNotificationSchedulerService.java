package com.botree.reportcompute;

import com.botree.common.constants.ReportConstants;
import com.botree.common.constants.StringConstants;
import com.botree.common.service.IQueryService;
import com.botree.interdbentity.model.MessageEntity;
import com.botree.interdbentity.model.SystemNotificationEntity;
import com.botree.interdbentity.model.UserActivationEntity;
import com.botree.reportcompute.service.ActivationMessageService;
import com.botree.reportcompute.service.SystemNotificationService;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Class contains the scheduler to send message/notification to the users by company / system.
 * @author vinodkumar.a
 */
@Component
public class SystemNotificationSchedulerService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SystemNotificationSchedulerService.class);

    /** systemDayNightEnable. */
    @Value("${cron.system.day.night.enable}")
    private Boolean systemDayNightEnable;

    /** systemDayMorningEnable. */
    @Value("${cron.system.day.morning.enable}")
    private Boolean systemDayMorningEnable;

    /** systemWeekOneEnable. */
    @Value("${cron.system.week.one.enable}")
    private Boolean systemWeekOneEnable;

    /** systemWeekTwoEnable. */
    @Value("${cron.system.week.two.enable}")
    private Boolean systemWeekTwoEnable;

    /** systemMonthOneEnable. */
    @Value("${cron.system.month.one.enable}")
    private Boolean systemMonthOneEnable;

    /** systemNotificationService. */
    private final SystemNotificationService systemNotificationService;

    /** activationMessageService. */
    private final ActivationMessageService activationMessageService;

    /** queryService. */
    private final IQueryService queryService;

    /** repository. */
    private final DAORepository repository;

    /**
     * Constructor Method.
     * @param systemNotificationServiceIn systemNotificationServiceIn
     * @param activationMessageServiceIn  activationMessageServiceIn
     * @param queryServiceIn              queryServiceIn
     * @param repositoryIn                repositoryIn
     */
    public SystemNotificationSchedulerService(final SystemNotificationService systemNotificationServiceIn,
                                              final ActivationMessageService activationMessageServiceIn,
                                              final IQueryService queryServiceIn,
                                              final DAORepository repositoryIn) {
        this.systemNotificationService = systemNotificationServiceIn;
        this.activationMessageService = activationMessageServiceIn;
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
    }

    /**
     * trigger scheduler for the day at night.
     */
    @Scheduled(cron = "${cron.system.day.night}")
    public final void triggerSystemDayNightScheduler() {
        LOG.info("start system day night scheduler :: {}", new Date());
        if (Boolean.TRUE.equals(systemDayNightEnable)) {
            convertSystemNotification(ReportConstants.SCH_DAY_NIGHT);
        }
        LOG.info("end system day night scheduler :: {}", new Date());
    }

    /**
     * trigger scheduler for the day at morning.
     */
    @Scheduled(cron = "${cron.system.day.morning}")
    public final void triggerSystemDayMorningScheduler() {
        LOG.info("start system day morning scheduler :: {}", new Date());
        if (Boolean.TRUE.equals(systemDayMorningEnable)) {
            convertSystemNotification(ReportConstants.SCH_DAY_MORNING);
        }
        LOG.info("end system day morning scheduler :: {}", new Date());
    }

    /**
     * trigger scheduler for weekly once.
     */
    @Scheduled(cron = "${cron.system.week.one}")
    public final void triggerSystemWeekOneScheduler() {
        LOG.info("start system weekly once scheduler :: {}", new Date());
        if (Boolean.TRUE.equals(systemWeekOneEnable)) {
            convertSystemNotification(ReportConstants.SCH_WEEK_ONE);
        }
        LOG.info("end system weekly once scheduler :: {}", new Date());
    }

    /**
     * trigger scheduler for weekly twice.
     */
    @Scheduled(cron = "${cron.system.week.two}")
    public final void triggerSystemWeekTwoScheduler() {
        LOG.info("start system weekly twice scheduler :: {}", new Date());
        if (Boolean.TRUE.equals(systemWeekTwoEnable)) {
            convertSystemNotification(ReportConstants.SCH_WEEK_TWO);
        }
        LOG.info("end system weekly twice scheduler :: {}", new Date());
    }

    /**
     * trigger scheduler for the month.
     */
    @Scheduled(cron = "${cron.system.month.one}")
    public final void triggerSystemMonthOneScheduler() {
        LOG.info("start system month scheduler :: {}", new Date());
        if (Boolean.TRUE.equals(systemMonthOneEnable)) {
            convertSystemNotification(ReportConstants.SCH_MONTH_ONE);
        }
        LOG.info("end system month scheduler :: {}", new Date());
    }

    /**
     * Method to convert the system notifications.
     * @param interval interval
     */
    private void convertSystemNotification(final String interval) {
        var list = repository.queryForListWithRowMapper(
                queryService.get(ReportConstants.FETCH_SYSTEM_NOTIFICATION_BY_INTERVALS),
                SystemNotificationEntity.class, interval);
        list.forEach(data -> {
            var notificationList = repository.queryForListWithRowMapper(
                    data.getMessageQuery(), MessageEntity.class);
            if (StringConstants.SYS_MSG_TYPE_RESACT.equalsIgnoreCase(data.getMessageType())) {
                notificationList.forEach(messageEntity -> {
                    var userActivationEntity = new UserActivationEntity();
                    userActivationEntity.setLoginCode(messageEntity.getLoginCode());
                    userActivationEntity.setUserName(messageEntity.getUserName());
                    activationMessageService.activationProcess(userActivationEntity,
                            StringConstants.SYS_MSG_TYPE_RESACT, StringConstants.SYS_MSG_TYPE_RESPWD);
                });
            } else {
                notificationList.forEach(messageEntity ->
                        systemNotificationService.systemMessageProcess(messageEntity, data)
                );
            }
        });
    }
}
