package com.botree.reportcompute.service;

import com.botree.common.constants.StringConstants;
import com.botree.common.service.QueryService;
import com.botree.interdbentity.model.FireBaseUserEntity;
import com.botree.interdbentity.model.NotificationEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Class to handle push notification with fire base.
 * @author vinodkumara
 */
@Service
public class PushNotificationsService {

    /** daoRepository. */
    private final DAORepository daoRepository;

    /** queryService. */
    private final QueryService queryService;

    /** notificationController. */
    private final NotificationController notificationController;

    /**
     * Constructor Method.
     * @param daoRepositoryIn          daoRepositoryIn
     * @param queryServiceIn           queryServiceIn
     * @param notificationControllerIn notificationControllerIn
     */
    public PushNotificationsService(final DAORepository daoRepositoryIn,
                                    final QueryService queryServiceIn,
                                    final NotificationController notificationControllerIn) {
        this.daoRepository = daoRepositoryIn;
        this.queryService = queryServiceIn;
        this.notificationController = notificationControllerIn;
    }

    /**
     * Method to send the DSR Sync notification.
     */
    public final void sendNotification() {
        var userMap = daoRepository.queryForListWithRowMapper(queryService.get(StringConstants.FETCH_FIREBASE_USER),
                FireBaseUserEntity.class).stream()
                .collect(Collectors.groupingBy(FireBaseUserEntity::getLoginCode));
        var notifications = daoRepository.queryForListWithRowMapper(queryService.get(
                StringConstants.FETCH_NOTIFICATION_BY_UPLOADSTATUS), NotificationEntity.class);
        if (userMap != null && !userMap.isEmpty()) {
            notifications.forEach(data -> {
                if (userMap.get(data.getLoginCode()) != null && !userMap.get(data.getLoginCode()).isEmpty()) {
                    userMap.get(data.getLoginCode()).forEach(user -> {
                        var response = notificationController.send(user.getFireBaseKey(), data);
                        if (HttpStatus.OK.equals(response.getStatusCode())) {
                            data.setUploadStatus(StringConstants.CONST_YES);
                        } else {
                            data.setUploadStatus(StringConstants.CONST_NO);
                        }
                    });
                }
            });
        }
        daoRepository.updateStatus(queryService.get(
                StringConstants.UPDATE_NOTIFICATION_BY_UPLOADSTATUS), notifications);
    }
}
