package com.botree.sfaweb.service;

import com.botree.common.constants.MessageConstants;
import com.botree.common.constants.ReportConstants;
import com.botree.common.constants.StringConstants;
import com.botree.common.service.IQueryService;
import com.botree.common.util.PasswordEncryptor;
import com.botree.common.util.SemaphoreUtil;
import com.botree.interdbentity.model.AppUserEntity;
import com.botree.interdbentity.model.AppUserModel;
import com.botree.interdbentity.model.DeviceInformationEntity;
import com.botree.interdbentity.model.FireBaseUserEntity;
import com.botree.interdbentity.model.UserAccessDetailEntity;
import com.botree.interdbentity.model.UserActivationEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

/**
 * Class contains the user related activities.
 * @author vinodkumar.a
 */
@Component
@Transactional
public class UserService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    /** queryService. */
    private final IQueryService queryService;

    /** repository. */
    private final DAORepository repository;

    /** LOG. */
    private HttpServletRequest request;

    /**
     * Constructor Method.
     * @param queryServiceIn queryServiceIn
     * @param repositoryIn   repositoryIn
     */
    public UserService(final IQueryService queryServiceIn, final DAORepository repositoryIn) {
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
    }

    /**
     * Method to assign the request.
     * @param requestIn requestIn
     */
    @Autowired
    public final void setRequest(final HttpServletRequest requestIn) {
        this.request = requestIn;
    }


    /**
     * Method to validate the login.
     * @param user user
     * @return appuser model
     */
    public AppUserModel validateLogin(final AppUserModel user) {
        checkSemaphoreStatus(user);
        var appUserEntityList = repository.queryForListWithRowMapper(
                queryService.get(StringConstants.FETCH_APPUSER), AppUserEntity.class, user.getLoginCode());
        var customer = repository.queryForListWithRowMapper(
                queryService.get(StringConstants.FETCH_CUSTOMER_CODE), AppUserModel.class, user.getLoginCode());
        if (customer.size() != 0) {
            user.setCustomerCode(customer.get(0).getCustomerCode());
        }
        if (!appUserEntityList.isEmpty()) {
            var appUserEntity = appUserEntityList.get(0);
            checkUserStatus(user, appUserEntity);
            if (user.getMessage().isEmpty()) {
                checkPassword(user, appUserEntity);
            }
            if (user.getMessage().isEmpty()) {
                user.setUserName(appUserEntity.getUserName());
                user.setUserStatus(appUserEntity.getUserStatus());
                user.setNewPassword(appUserEntity.getNewPassword());
                saveUserAccessDetails(user);
                saveFireBaseUser(user);
            }
            if (user.getMessage().isEmpty()) {
                updateUserActivation(user);
                saveUserDeviceDetails(user, appUserEntity);
                user.setAdvId(appUserEntity.getDeviceId());
                user.setLoginStatus(Boolean.TRUE);
                user.setMessage(MessageConstants.SUCCESS);
            }
        } else {
            user.setLoginStatus(Boolean.FALSE);
            user.setMessage(MessageConstants.INVALID_LOGIN);
        }
        return user;
    }

    /**
     * Method to check semaphore acquire status.
     * @param user user
     */
    private void checkSemaphoreStatus(final AppUserModel user) {
        SemaphoreUtil.add(user.getLoginCode());
        var key = user.getLoginCode();
        try {
            SemaphoreUtil.acquire(key);
        } catch (InterruptedException e) {
            LOG.error("semaphore acquire exception :: ", e);
            Thread.currentThread().interrupt();
        } finally {
            SemaphoreUtil.release(key);
        }
    }

    /**
     * Method to save the user mobile details.
     * @param user          user
     * @param appUserEntity appUserEntity
     */
    private void saveUserDeviceDetails(final AppUserModel user, final AppUserEntity appUserEntity) {
        if ((StringConstants.CONST_NO.equalsIgnoreCase(appUserEntity.getLoginStatus())
                && (user.getAdvId() == null || user.getAdvId().isEmpty()))) {
            var session = UUID.randomUUID();
            var deviceInformationEntity = new DeviceInformationEntity();
            deviceInformationEntity.setLoginCode(user.getLoginCode());
            deviceInformationEntity.setAdvId(String.valueOf(session));
            deviceInformationEntity.setDeviceBrand(user.getDeviceBrand());
            deviceInformationEntity.setDeviceModel(user.getDeviceModel());
            deviceInformationEntity.setDeviceVersion(user.getDeviceVersion());
            deviceInformationEntity.setModDt(new Date());
            appUserEntity.setLoginStatus(StringConstants.CONST_YES);
            appUserEntity.setDeviceId(String.valueOf(session));
            try {
                appUserEntity.setHostName(InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                LOG.error("user login - unknown host name exception", e);
            }
            appUserEntity.setLoggedInTime(new Date());
            repository.insert(queryService.get(StringConstants.INSERT_DEVICEINFORMATIONENTITY),
                    deviceInformationEntity);
            repository.insert(queryService.get(StringConstants.UPDATE_USER_LOGIN_STATUS), appUserEntity);
        }

    }

    /**
     * Check user active status.
     * @param user          user
     * @param appUserEntity appUserEntity
     */
    private void checkUserStatus(final AppUserModel user, final AppUserEntity appUserEntity) {
        if (StringConstants.CONST_NO.equalsIgnoreCase(appUserEntity.getUserStatus())) {
            user.setLoginStatus(Boolean.FALSE);
            user.setMessage(MessageConstants.USER_STATUS);
        }
    }

    /**
     * Method to validate password.
     * @param user          user
     * @param appUserEntity appUserEntity
     */
    private void checkPassword(final AppUserModel user, final AppUserEntity appUserEntity) {
        if (user.getPassword() != null && !user.getPassword().isEmpty() && !appUserEntity.getPassword()
                .equals(PasswordEncryptor.digest(user.getPassword(), user.getLoginCode(), Boolean.FALSE))) {
            user.setLoginStatus(Boolean.FALSE);
            user.setMessage(MessageConstants.INVALID_LOGIN);
        }
    }

    /**
     * Method to save the user access details with ipaddress, token and generation
     * time.
     * @param user user
     */
    private void saveUserAccessDetails(final AppUserModel user) {
        var accessDetail = new UserAccessDetailEntity();
        accessDetail.setLoginCode(user.getLoginCode());
        accessDetail.setRemoteAddr(getRemoteAddress());
        var session = UUID.randomUUID();
        accessDetail.setToken(String.valueOf(session));
        accessDetail.setTokenGenerationTime(new Date());
        accessDetail.setUploadFlag(StringConstants.CONST_NO);
        accessDetail.setModDt(new Date());
        repository.insert(queryService.get(StringConstants.INSERT_USERACCESSDETAILENTITY), accessDetail);
        user.setToken(accessDetail.getToken());
    }

    /**
     * Method to get the client request remote IP Address.
     * @return remote addr
     */
    private String getRemoteAddress() {
        var remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader(StringConstants.CONST_X_FORWARDED_FOR);
            if (remoteAddr == null || StringConstants.CONST_EMPTY.equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    /**
     * Method to save the firebase key and user mapping.
     * @param user user
     */
    private void saveFireBaseUser(final AppUserModel user) {
        if (user.getFireBaseKey() != null) {
            var fireBaseUser = new FireBaseUserEntity();
            fireBaseUser.setFireBaseKey(user.getFireBaseKey());
            fireBaseUser.setLoginCode(user.getLoginCode());
            fireBaseUser.setModDt(new Date());
            repository.insert(queryService.get(StringConstants.INSERT_FIREBASEUSERENTITY), fireBaseUser);
        }
    }

    /**
     * Method to update password.
     * @param user user
     */
    public void changePassword(final AppUserModel user) {
        user.setPassword(PasswordEncryptor.digest(user.getPassword(), user.getLoginCode(), Boolean.FALSE));
        repository.insert(queryService.get(StringConstants.UPDATE_USER_PASS_WORD), user);
    }

    /**
     * Method to logout user.
     * @param user user
     * @return user
     */
    public AppUserModel userLogout(final AppUserModel user) {
        var appUserEntityList = repository.queryForListWithRowMapper(
                queryService.get(StringConstants.FETCH_APPUSER), AppUserEntity.class, user.getLoginCode());
        if (!appUserEntityList.isEmpty()) {
            var appUserEntity = appUserEntityList.get(0);
            if (StringConstants.CONST_YES.equalsIgnoreCase(appUserEntity.getLoginStatus())) {
                appUserEntity.setLoginStatus(StringConstants.CONST_NO);
                appUserEntity.setDeviceId(StringConstants.CONST_EMPTY);
                user.setMessage(MessageConstants.SUCCESS);
                user.setAdvId(StringConstants.CONST_EMPTY);
                repository.insert(queryService.get(StringConstants.UPDATE_USER_LOGIN_STATUS), appUserEntity);
            } else {
                user.setMessage(MessageConstants.INVALID_LOG_OUT);
                user.setAdvId(StringConstants.CONST_EMPTY);
            }
        } else {
            user.setMessage(MessageConstants.INVALID_MOBILENO);
            user.setAdvId(StringConstants.CONST_EMPTY);
        }
        return user;
    }

    /**
     * Method to update user activation confirmation status.
     * @param user user
     */
    private void updateUserActivation(final AppUserModel user) {
        var list = repository.queryForListWithRowMapper(
                queryService.get(ReportConstants.FETCH_USERS_FOR_ACTIVATION_CONFIRMATION), UserActivationEntity.class,
                user.getLoginCode());
        if (!list.isEmpty()
                && !StringConstants.CONST_ACTIVATION_CONFIRMATION.equalsIgnoreCase(list.get(0).getUserStatus())) {
            repository.update(queryService.get(ReportConstants.UPDATE_USERS_FOR_ACTIVATION_CONFIRMATION),
                    user.getLoginCode());
        }
    }
}
