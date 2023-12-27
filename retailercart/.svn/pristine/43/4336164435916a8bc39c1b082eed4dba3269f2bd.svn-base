package com.botree.reportcompute.service;

import com.botree.common.constants.ReportConstants;
import com.botree.common.constants.StringConstants;
import com.botree.common.service.IQueryService;
import com.botree.interdbentity.model.ActionApiEntity;
import com.botree.interdbentity.model.ActionOrderInvoiceLineLevelEntity;
import com.botree.interdbentity.model.ActionOrderInvoiceValueEntity;
import com.botree.interdbentity.model.ActionOrderStatusEntity;
import com.botree.interdbentity.model.CustomerEntity;
import com.botree.interdbentity.model.MessageEntity;
import com.botree.interdbentity.model.SystemNotificationEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.botree.common.util.Function.checkDouble;
import static com.botree.common.util.Function.checkInteger;
import static com.botree.common.util.Function.trimString;

/**
 * Class to perform the action template logic.
 * @author vinodkumar.a
 */
@Component
@Transactional
public class ActionParserService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ActionParserService.class);

    /** queryService. */
    private final IQueryService queryService;

    /** repository. */
    private final DAORepository repository;

    /** systemNotificationService. */
    private final SystemNotificationService systemNotificationService;

    /**
     * Constructor Method.
     * @param queryServiceIn              queryServiceIn
     * @param repositoryIn                repositoryIn
     * @param systemNotificationServiceIn systemNotificationServiceIn
     */
    public ActionParserService(final IQueryService queryServiceIn, final DAORepository repositoryIn,
                               final SystemNotificationService systemNotificationServiceIn) {
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
        this.systemNotificationService = systemNotificationServiceIn;
    }

    /**
     * Method to parse order status action.
     */
    @Transactional
    public void parseOrderStatus() {
        LOG.info("parse order status start :: ");
        var finalOrderStatusList = new ArrayList<>();
        var orderStatusList = repository.queryForList(queryService.get(ReportConstants.FETCH_PENDING_ACTION_TEMPLATE),
                Map.of(StringConstants.CONST_PARAM_1, ReportConstants.ACTION_CODE_ORDER_STATUS), ActionApiEntity.class);
        orderStatusList.forEach(data -> {
            var entity = new ActionOrderStatusEntity();
            entity.setCmpCode(data.getCmpCode());
            entity.setDistrCode(data.getDistrCode());
            entity.setActionTime(data.getActionTime());
            if (data.getActionTemplate() != null && !data.getActionTemplate().isEmpty()) {
                var arr = data.getActionTemplate().split(StringConstants.CONST_REGEX_CAP);
                if (StringConstants.CONST_INT_2 <= arr.length) {
                    entity.setOrderNo(trimString(arr[StringConstants.CONST_INT_0]));
                    entity.setOrderStatus(trimString(arr[StringConstants.CONST_INT_1]));
                }
                if (StringConstants.CONST_INT_3 <= arr.length) {
                    entity.setFreeText(trimString(arr[StringConstants.CONST_INT_2]));
                }
            }
            entity.setUploadFlag(StringConstants.CONST_NO);
            entity.setModDt(new Date());
            finalOrderStatusList.add(entity);
        });
        repository.bulkInsert(queryService.get(ReportConstants.INSERT_ACTION_ORDER_STATUS), finalOrderStatusList);
        repository.updateStatus(queryService.get(ReportConstants.UPDATE_PARSED_ACTION_TEMPLATE), orderStatusList);
        LOG.info("parse order status end :: ");
    }

    /**
     * Method to parse order and invoice value action.
     */
    @Transactional
    public void parseOrderInvoiceValue() {
        LOG.info("parse order invoice value start :: ");
        var finalOrderInvoiceValueList = new ArrayList<>();
        var orderInvoiceValueList = repository.queryForList(queryService.get(
                ReportConstants.FETCH_PENDING_ACTION_TEMPLATE),
                Map.of(StringConstants.CONST_PARAM_1, ReportConstants.ACTION_CODE_ORDER_INVOICE_VALUE),
                ActionApiEntity.class);
        orderInvoiceValueList.forEach(data -> {
            var orderInvoiceValueEntity = new ActionOrderInvoiceValueEntity();
            orderInvoiceValueEntity.setCmpCode(data.getCmpCode());
            orderInvoiceValueEntity.setDistrCode(data.getDistrCode());
            orderInvoiceValueEntity.setActionTime(data.getActionTime());
            if (data.getActionTemplate() != null && !data.getActionTemplate().isEmpty()) {
                var valueArr = data.getActionTemplate().split(StringConstants.CONST_TILDE);
                if (StringConstants.CONST_INT_2 == valueArr.length) {
                    var orderArr = valueArr[StringConstants.CONST_INT_0].split(StringConstants.CONST_REGEX_CAP);
                    orderInvoiceValueEntity.setOrderNo(trimString(orderArr[StringConstants.CONST_INT_0]));
                    orderInvoiceValueEntity.setOrderValue(
                            checkDouble(trimString(orderArr[StringConstants.CONST_INT_1])));

                    var invoiceArr = valueArr[StringConstants.CONST_INT_1].split(StringConstants.CONST_REGEX_CAP);
                    orderInvoiceValueEntity.setInvoiceNo(trimString(invoiceArr[StringConstants.CONST_INT_0]));
                    orderInvoiceValueEntity.setInvoiceValue(
                            checkDouble(trimString(invoiceArr[StringConstants.CONST_INT_1])));
                }
            }
            orderInvoiceValueEntity.setUploadFlag(StringConstants.CONST_NO);
            orderInvoiceValueEntity.setModDt(new Date());
            finalOrderInvoiceValueList.add(orderInvoiceValueEntity);
        });
        repository.bulkInsert(queryService.get(ReportConstants.INSERT_ACTION_ORDER_INVOICE_VALUE),
                finalOrderInvoiceValueList);
        repository.updateStatus(queryService.get(ReportConstants.UPDATE_PARSED_ACTION_TEMPLATE), orderInvoiceValueList);
        LOG.info("parse order invoice value end :: ");
    }

    /**
     * Method to parse order and invoice line level action.
     */
    @Transactional
    public void parseOrderInvoiceLineLevel() {
        LOG.info("parse order invoice line level start :: ");
        var finalOrderInvoiceLineLevelList = new ArrayList<>();
        var orderInvoiceLineLevelList = repository.queryForList(queryService.get(
                ReportConstants.FETCH_PENDING_ACTION_TEMPLATE),
                Map.of(StringConstants.CONST_PARAM_1, ReportConstants.ACTION_CODE_ORDER_INVOICE_LINE_LEVEL),
                ActionApiEntity.class);
        orderInvoiceLineLevelList.forEach(data -> {
            if (data.getActionTemplate() != null && !data.getActionTemplate().isEmpty()) {
                var valueArr = data.getActionTemplate().split(StringConstants.CONST_TILDE);
                if (StringConstants.CONST_INT_2 == valueArr.length) {
                    var orderArr = valueArr[StringConstants.CONST_INT_0].split(StringConstants.CONST_REGEX_CAP);
                    for (var str : valueArr[StringConstants.CONST_INT_1]
                            .split(StringConstants.CONST_QUESTION_MARK)) {
                        var orderInvoiceLineLevelEntity = new ActionOrderInvoiceLineLevelEntity();
                        orderInvoiceLineLevelEntity.setCmpCode(data.getCmpCode());
                        orderInvoiceLineLevelEntity.setDistrCode(data.getDistrCode());
                        orderInvoiceLineLevelEntity.setActionTime(data.getActionTime());
                        orderInvoiceLineLevelEntity.setOrderNo(trimString(orderArr[StringConstants.CONST_INT_0]));
                        orderInvoiceLineLevelEntity.setInvoiceNo(trimString(orderArr[StringConstants.CONST_INT_1]));
                        var arr = str.split(StringConstants.CONST_REGEX_CAP);
                        orderInvoiceLineLevelEntity.setLineType(trimString(arr[StringConstants.CONST_INT_0]));
                        orderInvoiceLineLevelEntity.setProdCode(trimString(arr[StringConstants.CONST_INT_1]));
                        orderInvoiceLineLevelEntity.setProdBatchCode(trimString(arr[StringConstants.CONST_INT_2]));
                        orderInvoiceLineLevelEntity
                                .setOrderQty(checkInteger(trimString(arr[StringConstants.CONST_INT_3])));
                        orderInvoiceLineLevelEntity
                                .setInvoiceQty(checkInteger(trimString(arr[StringConstants.CONST_INT_4])));
                        orderInvoiceLineLevelEntity.setUploadFlag(StringConstants.CONST_NO);
                        orderInvoiceLineLevelEntity.setModDt(new Date());
                        finalOrderInvoiceLineLevelList.add(orderInvoiceLineLevelEntity);
                    }
                }
            }
        });
        repository.bulkInsert(queryService.get(ReportConstants.INSERT_ACTION_ORDER_INVOICE_LINE_LEVEL),
                finalOrderInvoiceLineLevelList);
        repository.updateStatus(queryService.get(ReportConstants.UPDATE_PARSED_ACTION_TEMPLATE),
                orderInvoiceLineLevelList);
        LOG.info("parse order invoice line level end :: ");
    }

    /**
     * Method to parse sms notification for all users in company.
     */
    @Transactional
    public void parseSMSNotificationForAll() {
        LOG.info("parse sms notification for all start :: ");
        var actionList = repository.queryForList(queryService.get(
                ReportConstants.FETCH_PENDING_ACTION_TEMPLATE),
                Map.of(StringConstants.CONST_PARAM_1, ReportConstants.ACTION_CODE_SMS_NOTIFICATION_FOR_ALL),
                ActionApiEntity.class);
        actionList.forEach(data -> {
            if (data.getCmpCode() != null) {
                var userList = repository.queryForList(queryService.get(
                        ReportConstants.FETCH_SMS_NOTIFICATION_FOR_ALL_USERS),
                        Map.of(StringConstants.CONST_PARAM_1, data.getCmpCode()),
                        CustomerEntity.class);
                convertSystemNotification(data.getActionTemplate(), userList, StringConstants.SYS_MSG_TYPE_ALLUSR);
            }
        });
        repository.updateStatus(queryService.get(ReportConstants.UPDATE_PARSED_ACTION_TEMPLATE), actionList);
        LOG.info("parse sms notification for all end :: ");
    }

    /**
     * Method to parse sms notification for all users in distributor.
     */
    @Transactional
    public void parseSMSNotificationForDistr() {
        LOG.info("parse sms notification for all users in distributor start :: ");
        var actionList = repository.queryForList(queryService.get(
                ReportConstants.FETCH_PENDING_ACTION_TEMPLATE),
                Map.of(StringConstants.CONST_PARAM_1, ReportConstants.ACTION_CODE_SMS_NOTIFICATION_FOR_DISTR),
                ActionApiEntity.class);
        actionList.forEach(data -> {
            if (data.getCmpCode() != null && data.getDistrCode() != null) {
                var userList = repository.queryForList(queryService.get(
                        ReportConstants.FETCH_SMS_NOTIFICATION_FOR_DISTR_USERS),
                        Map.of(StringConstants.CONST_PARAM_1, data.getCmpCode(),
                                StringConstants.CONST_PARAM_2, data.getDistrCode()),
                        CustomerEntity.class);
                convertSystemNotification(data.getActionTemplate(), userList, StringConstants.SYS_MSG_TYPE_DISUSR);
            }
        });
        repository.updateStatus(queryService.get(ReportConstants.UPDATE_PARSED_ACTION_TEMPLATE), actionList);
        LOG.info("parse sms notification for all users in distributor end :: ");
    }

    /**
     * Method to parse sms notification for mentioned customers.
     */
    @Transactional
    public void parseSMSNotificationForCustomer() {
        LOG.info("parse sms notification for mentioned customers start :: ");
        var actionList = repository.queryForList(queryService.get(
                ReportConstants.FETCH_PENDING_ACTION_TEMPLATE),
                Map.of(StringConstants.CONST_PARAM_1, ReportConstants.ACTION_CODE_SMS_NOTIFICATION_FOR_CUST),
                ActionApiEntity.class);
        actionList.forEach(data -> {
            if (data.getCmpCode() != null && data.getActionTemplate() != null) {
                var arr = data.getActionTemplate().split(StringConstants.CONST_TILDE);
                if (arr.length == StringConstants.CONST_INT_2) {
                    var userList = repository.queryForList(queryService.get(
                            ReportConstants.FETCH_SMS_NOTIFICATION_FOR_CUSTOMER),
                            Map.of(StringConstants.CONST_PARAM_1, data.getCmpCode(),
                                    StringConstants.CONST_PARAM_2,
                                    Arrays.asList(arr[0].split(StringConstants.CONST_REGEX_CAP))),
                            CustomerEntity.class);
                    convertSystemNotification(arr[1], userList, StringConstants.SYS_MSG_TYPE_PARUSR);
                }
            }
        });
        repository.updateStatus(queryService.get(ReportConstants.UPDATE_PARSED_ACTION_TEMPLATE), actionList);
        LOG.info("parse sms notification for mentioned customers end :: ");
    }

    /**
     * Method to convert system notification.
     * @param msg      msg
     * @param userList userList
     * @param msgType  msgType
     */
    private void convertSystemNotification(final String msg, final List<CustomerEntity> userList,
                                           final String msgType) {
        var stf2 = new SimpleDateFormat(StringConstants.TIME_PATTERN);
        var sdf2 = new SimpleDateFormat(StringConstants.DATE_PATTERN);
        userList.forEach(user -> {
            var message = new MessageEntity();
            message.setLoginCode(user.getMobileNo());
            message.setMessageDate(sdf2.format(new Date()));
            message.setMessageTime(stf2.format(new Date()));
            message.setUserName(user.getCustomerName());
            message.setMobileNo(user.getMobileNo());

            var systemNotification = new SystemNotificationEntity();
            systemNotification.setMessageType(msgType);
            systemNotification.setMessage(msg);
            systemNotificationService.systemMessageProcess(message, systemNotification);
        });
    }
}
