package com.botree.reportcompute.service;

import com.botree.common.constants.MessageConstants;
import com.botree.common.constants.ReportConstants;
import com.botree.common.constants.StringConstants;
import com.botree.common.service.HttpMessageService;
import com.botree.common.service.IQueryService;
import com.botree.common.util.PasswordEncryptor;
import com.botree.interdbentity.model.MessageEntity;
import com.botree.interdbentity.model.UserActivationEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * This class is used to send activation message.
 * @author vinodkumar.a
 */
@Service
@Transactional
public class ActivationMessageService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ActivationMessageService.class);

    /** activationTemplate. */
    @Value("${sms.activation.template}")
    private String activationTemplate;

    /** passwordTemplate. */
    @Value("${sms.password.template}")
    private String passwordTemplate;

    /** appUrl. */
    @Value("${app.url}")
    private String appUrl;

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
    public ActivationMessageService(final IQueryService queryServiceIn, final DAORepository repositoryIn,
                                    final HttpMessageService httpMessageServiceIn) {
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
        this.httpMessageService = httpMessageServiceIn;
    }

    /**
     * Method to fetch users for activation.
     * @param data   data
     * @param msgAct msgAct
     * @param msgPwd msgPwd
     */
    @Transactional
    public void activationProcess(final UserActivationEntity data, final String msgAct, final String msgPwd) {
        LOG.info("activation message start :: {} ", data.getLoginCode());
        var messageEntity = sendActivationMessage(data, msgAct);
        var messageEntity1 = sendPasswordMessage(data, msgPwd);
        var messageList = List.of(messageEntity, messageEntity1);
        saveMessage(messageList);
        LOG.info("activation message end :: {} ", data.getLoginCode());
    }

    /**
     * Method to send activation message.
     * @param data   data
     * @param msgAct msgAct
     * @return msg
     */
    private MessageEntity sendActivationMessage(final UserActivationEntity data, final String msgAct) {
        var sdf = new SimpleDateFormat(StringConstants.DATE_PATTERN);
        var stf = new SimpleDateFormat(StringConstants.TIME_PATTERN);
        var messageEntity = new MessageEntity();
        messageEntity.setMessageDate(sdf.format(new Date()));
        messageEntity.setMessageTime(stf.format(new Date()));
        messageEntity.setLoginCode(data.getLoginCode());
        messageEntity.setMobileNo(data.getLoginCode());
        messageEntity.setUserName(data.getUserName());
        messageEntity.setRefCode(msgAct);
        try {
            messageEntity.setMsg(MessageFormat.format(activationTemplate, messageEntity.getUserName(), appUrl));
            httpMessageService.sendMessage(messageEntity, StringConstants.CONST_MSG_ACTIVATION);
            repository.update(queryService.get(ReportConstants.UPDATE_USERS_FOR_ACTIVATION),
                    messageEntity.getLoginCode());
        } catch (Exception e) {
            LOG.error("send user activation exception :: ", e);
            messageEntity.setMessage(e.getMessage());
            messageEntity.setDeliveryStatus(MessageConstants.FAILED_DELIVERY_STATUS);
        }
        return messageEntity;
    }

    /**
     * Method to send activation password.
     * @param data   data
     * @param msgPwd msgPwd
     * @return msg
     */
    private MessageEntity sendPasswordMessage(final UserActivationEntity data, final String msgPwd) {
        var stf1 = new SimpleDateFormat(StringConstants.TIME_PATTERN);
        var sdf1 = new SimpleDateFormat(StringConstants.DATE_PATTERN);
        var messageEntity1 = new MessageEntity();
        messageEntity1.setLoginCode(data.getLoginCode());
        messageEntity1.setMobileNo(data.getLoginCode());
        messageEntity1.setUserName(data.getUserName());
        messageEntity1.setMessageDate(sdf1.format(new Date()));
        messageEntity1.setMessageTime(stf1.format(new Date()));
        messageEntity1.setUploadFlag(StringConstants.CONST_NO);
        messageEntity1.setModDt(new Date());
        messageEntity1.setRefCode(msgPwd);
        try {
            var passwordStr = generateSecureRandomPassword();
            messageEntity1.setPassword(PasswordEncryptor.digest(passwordStr,
                    messageEntity1.getMobileNo(), Boolean.FALSE));
            messageEntity1.setMsg(MessageFormat.format(passwordTemplate, messageEntity1.getUserName(), passwordStr));
            httpMessageService.sendMessage(messageEntity1, StringConstants.CONST_MSG_ACTIVATION_PASSWORD);
            repository.insert(queryService.get(StringConstants.UPDATE_ACTIVATION_USER_PASS_WORD), messageEntity1);
            repository.update(queryService.get(ReportConstants.UPDATE_USERS_FOR_ACTIVATION),
                    messageEntity1.getLoginCode());
        } catch (Exception e) {
            LOG.error("send user password exception :: ", e);
            messageEntity1.setMessage(e.getMessage());
            messageEntity1.setDeliveryStatus(MessageConstants.FAILED_DELIVERY_STATUS);
        }
        return messageEntity1;
    }

    /**
     * Method to save message and update activation user.
     * @param messageList messageList
     */
    private void saveMessage(final List<MessageEntity> messageList) {
        try {
            repository.bulkInsert(queryService.get(StringConstants.INSERT_MASTER_MESSAGEENTITY), messageList);
        } catch (Exception e) {
            LOG.error("save message method exception :: ", e);
        }
    }

    /**
     * Method to generate secure password.
     * @return password
     */
    private String generateSecureRandomPassword() {
        var random = new SecureRandom();
        var pwdStream = Stream.concat(getRandomNumbers(random), Stream.concat(getRandomSpecialChars(random),
                Stream.concat(getRandomAlphabets(true, random), getRandomAlphabets(false, random))));
        var charList = pwdStream.collect(Collectors.toList());
        Collections.shuffle(charList);
        return charList.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    /**
     * Method to generate random numbers.
     * @param random random
     * @return number
     */
    private Stream<Character> getRandomNumbers(final Random random) {
        var numbers = random.ints(StringConstants.CONST_INT_3, StringConstants.CONST_INT_48,
                StringConstants.CONST_INT_57);
        return numbers.mapToObj(data -> (char) data);
    }

    /**
     * Method to generate random alphabets.
     * @param upperCase upperCase
     * @param random    random
     * @return alphabets
     */
    private Stream<Character> getRandomAlphabets(final boolean upperCase, final Random random) {
        IntStream characters;
        if (upperCase) {
            characters = random.ints(StringConstants.CONST_INT_2, StringConstants.CONST_INT_65,
                    StringConstants.CONST_INT_90);
        } else {
            characters = random.ints(StringConstants.CONST_INT_2, StringConstants.CONST_INT_97,
                    StringConstants.CONST_INT_122);
        }
        return characters.mapToObj(data -> (char) data);
    }

    /**
     * Method to generate special chars.
     * @param random random
     * @return special chars
     */
    private Stream<Character> getRandomSpecialChars(final Random random) {
        var specialChars = random.ints(StringConstants.CONST_INT_1, StringConstants.CONST_INT_35,
                StringConstants.CONST_INT_38);
        return specialChars.mapToObj(data -> (char) data);
    }
}
