package com.botree.reportcompute.service;

import com.botree.common.constants.MessageConstants;
import com.botree.common.constants.StringConstants;
import com.botree.common.service.HttpMessageService;
import com.botree.common.service.IQueryService;
import com.botree.interdbentity.model.MessageEntity;
import com.botree.interdbentity.model.SystemNotificationEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

/**
 * This class is used to send system generated message.
 * @author vinodkumar.a
 */
@Service
@Transactional
public class SystemNotificationService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SystemNotificationService.class);

    /** queryService. */
    private final IQueryService queryService;

    /** repository. */
    private final DAORepository repository;

    /** httpMessageService. */
    private final HttpMessageService httpMessageService;

    /**
     * Constructor method.
     * @param queryServiceIn       queryServiceIn
     * @param repositoryIn         repositoryIn
     * @param httpMessageServiceIn httpMessageServiceIn
     */
    public SystemNotificationService(final IQueryService queryServiceIn,
                                     final DAORepository repositoryIn,
                                     final HttpMessageService httpMessageServiceIn) {
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
        this.httpMessageService = httpMessageServiceIn;
    }

    /**
     * Method to send system generated message.
     * @param messageEntity messageEntity
     * @param data          data
     */
    @Transactional
    public void systemMessageProcess(final MessageEntity messageEntity,
                                     final SystemNotificationEntity data) {
        LOG.info("system message start :: {} ", messageEntity.getLoginCode());
        sendSystemMessage(messageEntity, data);
        var messageList = List.of(messageEntity);
        saveSystemMessage(messageList);
        LOG.info("system message end :: {} ", messageEntity.getLoginCode());
    }

    /**
     * Method to send usage message.
     * @param messageEntity messageEntity
     * @param data          data
     */
    private void sendSystemMessage(final MessageEntity messageEntity,
                                   final SystemNotificationEntity data) {
        try {
            messageEntity.setRefCode(data.getMessageType());
            messageEntity.setMsg(MessageFormat.format(data.getMessage(), messageEntity.getUserName()));
            httpMessageService.sendMessage(messageEntity, data.getMessageType());
        } catch (Exception e) {
            LOG.error("send system message exception :: ", e);
            messageEntity.setMessage(e.getMessage());
            messageEntity.setDeliveryStatus(MessageConstants.FAILED_DELIVERY_STATUS);
        }
    }

    /**
     * Method to save system message.
     * @param messageList messageList
     */
    private void saveSystemMessage(final List<MessageEntity> messageList) {
        try {
            repository.bulkInsert(queryService.get(StringConstants.INSERT_MASTER_MESSAGEENTITY), messageList);
        } catch (Exception e) {
            LOG.error("save system message method exception :: ", e);
        }
    }
}
