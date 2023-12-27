package com.botree.interdb.service;

import com.botree.common.constants.StringConstants;
import com.botree.common.service.IQueryService;
import com.botree.interdbentity.model.AppUserModel;
import com.botree.sfadbrepository.dao.DAORepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * This class is for login.
 * @author vinodkumar.a
 */
@Component
@Transactional
public class LoginService {

    /** repository. */
    private final DAORepository repository;
    /** queryService. */
    private final IQueryService queryService;

    /**
     * Constructor Method.
     * @param repositoryIn   repositoryIn
     * @param queryServiceIn queryServiceIn
     */
    public LoginService(final DAORepository repositoryIn, final IQueryService queryServiceIn) {
        this.repository = repositoryIn;
        this.queryService = queryServiceIn;
    }


    /**
     * This method is used to validate user login.
     * @param username        unique user name
     * @param password        user password
     * @param clientIpAddress clientIpAddress
     */
    public void login(final String username, final String password, final String clientIpAddress) {
        repository.queryForObjectWithRowMapper(queryService.get(StringConstants.USER_LOGIN),
                AppUserModel.class, username, password);
        repository.update(queryService.get(StringConstants.INSERT_USER_LOGIN_LOG), username,
                new Date(), clientIpAddress, true, null);
    }

    /**
     * This method is used for logout the users.
     * @param userCode userCode
     */
    public void logout(final String userCode) {
        repository.updateStatus(queryService.get(StringConstants.UPDATE_USER_LOGOUT_LOG),
                Map.of(StringConstants.CONST_USER_CODE, userCode, StringConstants.CONST_IS_LOGGED_IN, Boolean.FALSE,
                        StringConstants.CONST_LOGOUT_TIME, new Date()));
    }
}
