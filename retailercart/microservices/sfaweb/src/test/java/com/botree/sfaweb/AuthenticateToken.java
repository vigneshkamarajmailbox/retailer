package com.botree.sfaweb;

import com.botree.common.service.IQueryService;
import com.botree.interdbentity.model.AppUserModel;
import com.botree.interdbentity.model.ApplicationUpdateEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class contains the token generation for test cases.
 * @author vinodkumar.a
 */
@Component
public class AuthenticateToken {

    /** testRestTemplate. */
    @Autowired
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    /** queryService. */
    private final IQueryService queryService;

    /** repository. */
    private final DAORepository repository;

    /**
     * Constructor Method.
     * @param queryServiceIn queryServiceIn
     * @param repositoryIn   repositoryIn
     */
    public AuthenticateToken(final IQueryService queryServiceIn,
                             final DAORepository repositoryIn) {
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
    }

    /**
     * Method to login and generate token.
     * @param loginCode loginCode
     * @param password  password
     * @return headers
     */
    public HttpHeaders testTokenCreation(final String loginCode, final String password) {
        var user = new AppUserModel();
        user.setLoginCode(loginCode);
        user.setPassword(password);
        user.setAdvId(StringConstants.CONST_ADV_ID);
        user.setDeviceBrand(StringConstants.CONST_DEVICE_BRAND);
        user.setDeviceModel(StringConstants.CONST_DEVICE_MODEL);
        user.setDeviceVersion(StringConstants.CONST_DEVICE_VERSION);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_LOGIN_AUTHENTICATE,
                user, AppUserModel.class, headers);
        var userModel = responseEntity.getBody();
        headers.set(StringConstants.X_LOGIN_CODE, loginCode);
        headers.set(StringConstants.X_AUTH_TOKEN, Objects.requireNonNull(userModel).getToken());
        return headers;
    }

    /**
     * Method to fetch application version.
     * @return application version
     */
    public String getApplicationVersion() {
        return repository.queryForList(
                queryService.get(com.botree.common.constants.StringConstants.FETCH_APPVERSION),
                new HashMap<>(), ApplicationUpdateEntity.class).get(0).getAppVersion();
    }
}
