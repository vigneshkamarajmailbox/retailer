package com.botree.reportcompute;

import com.botree.reportcompute.service.PushNotificationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class contains the scheduler to perform the notification action.
 * @author vinodkumar.a
 */
@Component
public class NotificationSchedulerService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(NotificationSchedulerService.class);

    /** DELAY. */
    private static final long DELAY = 100000L;

    /** notificationEnable. */
    @Value("${cron.notification.enable}")
    private Boolean notificationEnable;

    /** pushNotificationsService. */
    private final PushNotificationsService pushNotificationsService;

    /**
     * Constructor Method.
     * @param pushNotificationsServiceIn pushNotificationsServiceIn
     */
    public NotificationSchedulerService(
            final PushNotificationsService pushNotificationsServiceIn) {
        this.pushNotificationsService = pushNotificationsServiceIn;
    }

    /**
     * Method to trigger the other specific Notification.
     */
    @Scheduled(fixedDelay = DELAY)
    public final void otherNotification() {
        if (Boolean.TRUE.equals(notificationEnable)) {
            LOG.info("otherNotification start :- ");
            pushNotificationsService.sendNotification();
            LOG.info("otherNotification end :- ");
        }
    }
}
