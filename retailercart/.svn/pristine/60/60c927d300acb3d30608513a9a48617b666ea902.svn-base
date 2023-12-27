package com.botree.reportcompute.service;

import com.botree.common.constants.MessageConstants;
import com.botree.common.constants.ReportConstants;
import com.botree.common.constants.StringConstants;
import com.botree.common.service.HttpMessageService;
import com.botree.common.service.IQueryService;
import com.botree.interdbentity.model.MessageEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class is used to send usage report message.
 * @author vinodkumar.a
 */
@Service
@Transactional
public class UsageReportMessageService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(UsageReportMessageService.class);

    /** usageReportTemplate. */
    @Value("${sms.usage.report.template}")
    private String usageReportTemplate;

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
    public UsageReportMessageService(final IQueryService queryServiceIn,
                                     final DAORepository repositoryIn,
                                     final HttpMessageService httpMessageServiceIn) {
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
        this.httpMessageService = httpMessageServiceIn;
    }

    /**
     * Method to send user usage report message.
     * @param map map
     */
    @Transactional
    public void usageMessageProcess(final Map<String, Object> map) {
        LOG.info("usage report message start :: {} ", map.get(ReportConstants.REPORT_COL_LOGINCODE));
        var messageEntity = sendUsageMessage(map);
        var messageList = List.of(messageEntity);
        saveMessage(messageList);
        LOG.info("usage report message end :: {} ", map.get(ReportConstants.REPORT_COL_LOGINCODE));
    }

    /**
     * Method to send usage message.
     * @param map map
     * @return msg
     */
    private MessageEntity sendUsageMessage(final Map<String, Object> map) {
        var sdf = new SimpleDateFormat(StringConstants.DATE_PATTERN);
        var sdf1 = new SimpleDateFormat(StringConstants.DATE_PATTERN_1);
        var stf = new SimpleDateFormat(StringConstants.TIME_PATTERN);
        var messageEntity = new MessageEntity();
        messageEntity.setMessageDate(sdf.format(new Date()));
        messageEntity.setMessageTime(stf.format(new Date()));
        messageEntity.setLoginCode((String) map.get(ReportConstants.REPORT_COL_LOGINCODE));
        messageEntity.setMobileNo((String) map.get(ReportConstants.REPORT_COL_MOBILENO));
        messageEntity.setUserName((String) map.get(ReportConstants.REPORT_COL_USERNAME));
        messageEntity.setRefCode(StringConstants.CONST_MSG_USAGE_REPORT);
        try {
            messageEntity.setMsg(MessageFormat.format(usageReportTemplate, messageEntity.getUserName(),
                    sdf1.format(new Date()), map.get(ReportConstants.REPORT_COL_CUSTOMERCOUNT),
                    map.get(ReportConstants.REPORT_COL_ORDERCOUNT), map.get(ReportConstants.REPORT_COL_TOTALLINES),
                    map.get(ReportConstants.REPORT_COL_UNIQUELINES), map.get(ReportConstants.REPORT_COL_ORDERVALUE)));
            httpMessageService.sendMessage(messageEntity, StringConstants.CONST_MSG_USAGE_REPORT);
        } catch (Exception e) {
            LOG.error("send usage report exception :: ", e);
            messageEntity.setMessage(e.getMessage());
            messageEntity.setDeliveryStatus(MessageConstants.FAILED_DELIVERY_STATUS);
        }
        return messageEntity;
    }

    /**
     * Method to save message.
     * @param messageList messageList
     */
    private void saveMessage(final List<MessageEntity> messageList) {
        try {
            repository.bulkInsert(queryService.get(StringConstants.INSERT_MASTER_MESSAGEENTITY), messageList);
        } catch (Exception e) {
            LOG.error("save message method exception :: ", e);
        }
    }
}
