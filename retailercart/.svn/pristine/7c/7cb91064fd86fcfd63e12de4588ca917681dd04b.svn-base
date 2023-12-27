package com.botree.sfaweb.util;

import com.botree.common.constants.StringConstants;
import com.botree.common.service.IQueryService;
import com.botree.interdbentity.model.AppUserEntity;
import com.botree.interdbentity.model.UserAccessDetailEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

/**
 * RequestValidator to handle all the request.
 * @author vinodkumara
 */
@Component
public class RequestValidator {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(RequestValidator.class);

    /** timeout. */
    @Value("${session.timeout}")
    private Integer timeout;

    /** repository. */
    private final DAORepository repository;

    /** queryService. */
    private final IQueryService queryService;

    /**
     * Constructor Method.
     * @param repositoryIn   repositoryIn
     * @param queryServiceIn queryServiceIn
     */
    public RequestValidator(final DAORepository repositoryIn, final IQueryService queryServiceIn) {
        this.repository = repositoryIn;
        this.queryService = queryServiceIn;
    }

    /**
     * Method to validate the request.
     * @param request request
     */
    public final void validateRequest(final HttpServletRequest request) {
        var userCode = request.getHeader(StringConstants.AUTH_LOGIN_CODE);
        LOG.info("validate request :: {} {} ", request.getRequestURL(), userCode);
        if (userCode != null && !userCode.isEmpty()) {
            var appUser = new AppUserEntity();
            try {
                appUser.setHostName(InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                LOG.error("validate request - unknown host name exception", e);
            }
            appUser.setLoggedInTime(new Date());
            appUser.setLoginStatus(StringConstants.CONST_YES);
            appUser.setLoginCode(userCode);
            repository.updateStatus(queryService.get(StringConstants.UPDATE_USER_HOST_NAME),
                    Map.of(StringConstants.CONST_PARAM_1, appUser.getLoginStatus(),
                            StringConstants.CONST_PARAM_2, appUser.getHostName(),
                            StringConstants.CONST_PARAM_3, appUser.getLoggedInTime(),
                            StringConstants.CONST_PARAM_4, appUser.getLoginCode()));
        }
    }

    /**
     * Method to validate the request.
     * @param request request
     * @return response
     */
    public final ResponseEntity<Object> preHandle(final HttpServletRequest request) {
        var userCode = request.getHeader(StringConstants.AUTH_LOGIN_CODE);
        var accessKey = request.getHeader(StringConstants.AUTHORIZATION);
        LOG.info("token :: {}", accessKey);
        if (userCode == null || userCode.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(StringConstants.PARAMETER_USERCODE_MISSING);
        }
        if (accessKey == null || accessKey.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(StringConstants.PARAMETER_AUTHORIZATION_MISSING);
        }

        var userAccessDetailEntityList =
                repository.queryForListWithRowMapper(queryService.get(StringConstants.FETCH_USERACCESSDETAILENTITY),
                        UserAccessDetailEntity.class, userCode);
        if (userAccessDetailEntityList.isEmpty()
                || !accessKey.equalsIgnoreCase(userAccessDetailEntityList.get(0).getToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(StringConstants.INVALIDPARAMETERS);
        } else {
            var ldt = LocalDateTime.ofInstant(userAccessDetailEntityList.get(0).getTokenGenerationTime().toInstant(),
                    ZoneId.systemDefault());
            var duration = Duration.between(ldt, LocalDateTime.now());
            if (duration.toMinutes() > timeout) {
                return ResponseEntity.status(HttpStatus.GONE).body(StringConstants.SESSIONEXPIRED);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(StringConstants.VALIDATIONSUCCESS);
    }
}
