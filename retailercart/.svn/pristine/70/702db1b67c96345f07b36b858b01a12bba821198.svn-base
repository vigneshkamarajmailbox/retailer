package com.botree.sfaweb.service;

import com.botree.common.constants.MessageConstants;
import com.botree.common.constants.StringConstants;
import com.botree.common.service.HttpMessageService;
import com.botree.common.service.IQueryService;
import com.botree.common.util.Function;
import com.botree.interdbentity.model.AppUserModel;
import com.botree.interdbentity.model.MessageEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Class contains the message related activities.
 * @author vinodkumar.a
 */
@Component
@Transactional
public class MessageService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    /** template. */
    @Value("${sms.template}")
    private String template;

    /** smsRefNo. */
    @Value("${sms.otp.refno}")
    private Integer refNo;

    /** noOfDigit. */
    @Value("${sms.otp.noofdigit}")
    private Integer noOfDigit;

    /** repository. */
    private final DAORepository repository;

    /** queryService. */
    private final IQueryService queryService;

    /** httpMessageService. */
    private final HttpMessageService httpMessageService;

    /**
     * Constructor Method.
     * @param repositoryIn         repositoryIn
     * @param queryServiceIn       queryServiceIn
     * @param httpMessageServiceIn httpMessageServiceIn
     */
    public MessageService(final DAORepository repositoryIn, final IQueryService queryServiceIn,
                          final HttpMessageService httpMessageServiceIn) {
        this.repository = repositoryIn;
        this.queryService = queryServiceIn;
        this.httpMessageService = httpMessageServiceIn;
    }

    /**
     * Method to send otp for the user.
     * @param user user
     * @return message template
     */
    @Transactional
    public Object sendOtp(final AppUserModel user) {
        var sdf = new SimpleDateFormat(StringConstants.DATE_PATTERN);
        var stf = new SimpleDateFormat(StringConstants.TIME_PATTERN);
        var messageEntity = new MessageEntity();
        messageEntity.setMessageDate(sdf.format(new Date()));
        messageEntity.setMessageTime(stf.format(new Date()));
        try {
            var otp = String.valueOf(generateOtp(noOfDigit));
            var otpRefNo = generateOtpRefNo(refNo);
            messageEntity.setOtpCode(otp);
            messageEntity.setRefCode(otpRefNo);
            messageEntity.setMsg(MessageFormat.format(template, otp, otpRefNo));
            messageEntity.setLoginCode(user.getLoginCode());
            var userList = repository.queryForListWithRowMapper(
                    queryService.get(StringConstants.FETCH_USER_MOBILENO), MessageEntity.class, user.getLoginCode());
            if (userList.isEmpty()) {
                messageEntity.setMessage(MessageConstants.INVALID_MOBILENO);
                messageEntity.setDeliveryStatus(MessageConstants.FAILED_INVALID_MOBILE_NO);
            } else {
                var appUser = userList.get(0);
                messageEntity.setUserName(appUser.getUserName());
                messageEntity.setMobileNo(appUser.getLoginCode());
                if (appUser.getLoginCode() == null || appUser.getLoginCode().isEmpty()
                        || MessageConstants.INVALID_MOBILENO_0.equalsIgnoreCase(appUser.getLoginCode())
                        || appUser.getLoginCode().length() < MessageConstants.MOBILENO_LENGTH) {
                    messageEntity.setMessage(MessageConstants.INVALID_MOBILENO);
                    messageEntity.setDeliveryStatus(MessageConstants.FAILED_INVALID_MOBILE_NO);
                } else {
                    httpMessageService.sendMessage(messageEntity, StringConstants.CONST_MSG_OTP);
                }
            }
        } catch (Exception e) {
            LOG.error("send otp exception :: ", e);
            messageEntity.setMessage(e.getMessage());
            messageEntity.setDeliveryStatus(MessageConstants.FAILED_DELIVERY_STATUS);
        }
        var messageList = List.of(messageEntity);
        saveMessage(messageList);
        return Function.compress(messageEntity, user.isEnableCompress());
    }

    /**
     * Method to save message info.
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
     * Method to generate OTP.
     * @param n length
     * @return otp
     */
    private Long generateOtp(final Integer n) {
        var m = (int) Math.pow(MessageConstants.INT_10, n - 1d);
        return m + (long) new Random().nextInt(MessageConstants.INT_9 * m);
    }

    /**
     * Method to generate OTP Reference.
     * @param strLen strLen
     * @return ref no
     */
    private String generateOtpRefNo(final int strLen) {
        var chars = MessageConstants.CHARS_SEQUENCE;
        return new Random().ints(strLen, 0, chars.length()).mapToObj(i -> "" + chars.charAt(i))
                .collect(Collectors.joining());
    }
}
