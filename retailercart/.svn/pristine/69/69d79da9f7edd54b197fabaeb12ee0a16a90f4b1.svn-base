package com.botree.sfaweb;

import com.botree.interdbentity.model.AppUserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * LoginControllerTest class for login process.
 * @author vinodkumar.a
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
public class LoginControllerTest {

    /** testRestTemplate. */
    @Autowired
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    /** authenticateToken. */
    @Autowired
    private AuthenticateToken authenticateToken;

    /**
     * Test method for login success.
     */
    @Test
    public void testLoginSuccess() {
        var user = new AppUserModel();
        user.setLoginCode(StringConstants.CONST_LOGIN_CODE);
        user.setPassword(StringConstants.CONST_PASS_WORD);
        user.setFireBaseKey(StringConstants.CONST_FIRE_BASE_KEY);
        user.setAdvId(StringConstants.CONST_EMPTY);
        user.setDeviceBrand(StringConstants.CONST_DEVICE_BRAND);
        user.setDeviceModel(StringConstants.CONST_DEVICE_MODEL);
        user.setDeviceVersion(StringConstants.CONST_DEVICE_VERSION);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_LOGIN_AUTHENTICATE,
                user, AppUserModel.class, httpHeaders);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).getLoginStatus());
    }

    /**
     * Test for login failure.
     */
    @Test
    public void testLoginFailureInValidLoginCode() {
        var user = new AppUserModel();
        user.setLoginCode(StringConstants.CONST_INVALID_LOGIN_CODE);
        user.setPassword(StringConstants.CONST_INVALID_LOGIN_CODE);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_LOGIN_AUTHENTICATE,
                user, AppUserModel.class, httpHeaders);
        var userModel = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(Objects.requireNonNull(userModel).getLoginStatus());
    }

    /**
     * Test for login success but user not active.
     */
    @Test
    public void testUserInActiveStatus() {
        var user = new AppUserModel();
        user.setLoginCode(StringConstants.CONST_IN_ACTIVE_LOGIN_CODE);
        user.setPassword(StringConstants.CONST_IN_ACTIVE_LOGIN_CODE);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_LOGIN_AUTHENTICATE,
                user, AppUserModel.class, httpHeaders);
        var userModel = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(Objects.requireNonNull(userModel).getLoginStatus());
    }

    /**
     * Test for login failure with invalid password.
     */
    @Test
    public void testLoginFailureInValidPassword() {
        var user = new AppUserModel();
        user.setLoginCode(StringConstants.CONST_LOGIN_CODE);
        user.setPassword(StringConstants.CONST_INVALID_LOGIN_CODE);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_LOGIN_AUTHENTICATE,
                user, AppUserModel.class, httpHeaders);
        var userModel = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(Objects.requireNonNull(userModel).getLoginStatus());
    }

    /**
     * Test method to save user device details.
     */
    @Test
    public void testSaveUserDeviceDetails() {
        var user = new AppUserModel();
        user.setLoginCode(StringConstants.CONST_LOGIN_CODE_FOR_DEVICE_INFO);
        user.setPassword(StringConstants.CONST_LOGIN_CODE_FOR_DEVICE_INFO);
        user.setFireBaseKey(StringConstants.CONST_FIRE_BASE_KEY);
        user.setAdvId(StringConstants.CONST_EMPTY);
        user.setDeviceBrand(StringConstants.CONST_DEVICE_BRAND);
        user.setDeviceModel(StringConstants.CONST_DEVICE_MODEL);
        user.setDeviceVersion(StringConstants.CONST_DEVICE_VERSION);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_LOGIN_AUTHENTICATE,
                user, AppUserModel.class, httpHeaders);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Test for change password.
     */
    @Test
    public void testChangePassword() {
        var user = new AppUserModel();
        user.setLoginCode(StringConstants.CONST_LOGIN_CODE_CHANGE_PWD);
        user.setPassword(StringConstants.CONST_CHANGE_PWD);
        HttpEntity<?> request = new HttpEntity<>(user,
                authenticateToken.testTokenCreation(user.getLoginCode(), user.getPassword()));
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_LOGIN_CHANGEPASSWORD,
                request, AppUserModel.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Test for log out.
     */
    @Test
    public void testLogoutIsSuccess() {
        var user = new AppUserModel();
        user.setLoginCode(StringConstants.CONST_LOGIN_CODE);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_LOGIN_OUT,
                user, AppUserModel.class, httpHeaders);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(StringConstants.CONST_MSG_SUCCESS,
                Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    /**
     * Test for invalid log out.
     */
    @Test
    public void testLogoutInValid() {
        var user = new AppUserModel();
        user.setLoginCode(StringConstants.CONST_LOGIN_CODE);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_LOGIN_OUT,
                user, AppUserModel.class, httpHeaders);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(StringConstants.CONST_MSG_INVALID_LOG_OUT,
                Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    /**
     * Test for log out with invalid login code.
     */
    @Test
    public void testLogoutInValidLoginCode() {
        var user = new AppUserModel();
        user.setLoginCode(StringConstants.CONST_INVALID_LOGIN_CODE);
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_LOGIN_OUT,
                user, AppUserModel.class, httpHeaders);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(StringConstants.CONST_MSG_INVALID_MOBILENO,
                Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    /**
     * Test for sent otp.
     */
    @Test
    public void testSentOtp() {
        var user = new AppUserModel();
        user.setLoginCode(StringConstants.CONST_OTP_LOGIN_CODE);
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_SENT_OTP,
                user, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
