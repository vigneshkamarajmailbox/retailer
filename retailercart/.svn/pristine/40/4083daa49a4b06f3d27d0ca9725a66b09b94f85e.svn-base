package com.botree.sfaweb;

import com.botree.common.model.DownloadModel;

import com.botree.interdbentity.model.AppUserModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Download Process related test cases.
 * @author vinodkumar.a
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
public class DownloadProcessTest {

    /** testRestTemplate. */
    @Autowired
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    /** authenticateToken. */
    @Autowired
    private AuthenticateToken authenticateToken;


    /**
     * Method for download Failure System Date.
     */
    @Before
    public void testDownloadFailureInvalidSystemDate() {
        var userModel = new AppUserModel();
        var date = Date.from(LocalDate.now().minusDays(StringConstants.CONST_INT_1).atStartOfDay(
                ZoneId.systemDefault()).toInstant());
        userModel.setLoginCode(StringConstants.CONST_LOGIN_CODE);
        userModel.setPassword(StringConstants.CONST_PASS_WORD);
        userModel.setAppVersion(authenticateToken.getApplicationVersion());
        userModel.setSystemDate(date);
        HttpEntity<?> request = new HttpEntity<>(userModel,
                authenticateToken.testTokenCreation(userModel.getLoginCode(), userModel.getPassword()));
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_CONTROLLER_DOWNLOAD,
                request, DownloadModel.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(Objects.requireNonNull(responseEntity.getBody()).getSystemDateAuthFlag());
    }

    /**
     * Method for download success.
     */
    @Test
    public void testDownloadSuccess() {
        var userModel = new AppUserModel();
        userModel.setLoginCode(StringConstants.CONST_LOGIN_CODE);
        userModel.setPassword(StringConstants.CONST_PASS_WORD);
        userModel.setAppVersion(authenticateToken.getApplicationVersion());
        userModel.setSystemDate(new Date());
        HttpEntity<?> request = new HttpEntity<>(userModel,
                authenticateToken.testTokenCreation(userModel.getLoginCode(), userModel.getPassword()));
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_CONTROLLER_DOWNLOAD,
                request, DownloadModel.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).getSystemDateAuthFlag());
    }



    /**
     * test fetch screen access.
     */
    @Test
    public void testFetchScreenAccess() {
        var userModel = new AppUserModel();
        var date = Date.from(LocalDate.now().minusDays(StringConstants.CONST_INT_1).atStartOfDay(
                ZoneId.systemDefault()).toInstant());
        userModel.setLoginCode(StringConstants.CONST_LOGIN_CODE);
        userModel.setPassword(StringConstants.CONST_PASS_WORD);
        userModel.setAppVersion(authenticateToken.getApplicationVersion());
        userModel.setSystemDate(date);
        HttpEntity<?> request = new HttpEntity<>(userModel,
                authenticateToken.testTokenCreation(userModel.getLoginCode(), userModel.getPassword()));
        var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_CONTROLLER_FETCHSCREENACCESS,
                request, DownloadModel.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Test  Download Product Image info.
     */
    @Test
    public void testDownloadProductImage() {
        var responseEntity = testRestTemplate.getForEntity(StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE,
                String.class, StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE_ID,
                StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE_TYPE);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Test  Download Company Image info.
     */
    @Test
    public void testDownloadCompanyImage() {
        var responseEntity = testRestTemplate.getForEntity(StringConstants.PATH_CONTROLLER_FETCH_COMPANYIMAGE,
                String.class, StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE_ID,
                StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE_TYPE);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Test  Download Company Image info.
     */
    @Test
    public void testDownloadBannerImageID() {
        var responseEntity = testRestTemplate.getForEntity(StringConstants.PATH_CONTROLLER_FETCH_BANNERIMAGE_ID,
                String.class, StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE_ID,
                StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE_TYPE);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Test  Download Banner Image info.
     */
    @Test
    public void testDownloadBannerImage() {
        var responseEntity = testRestTemplate.getForEntity(StringConstants.PATH_CONTROLLER_FETCH_BANNERIMAGE,
                String.class, StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE_TYPE,
                StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE_ID);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Test  Download Mapped Company info.
     */
    @Test
    public void testDownloadMappedCompany() {
        HttpEntity httpEntity = getHttpEntity();
        var responseEntity = testRestTemplate.
                exchange(StringConstants.PATH_CONTROLLER_FETCH_DOWNLOAD, HttpMethod.GET, httpEntity,
                        DownloadModel.class, StringConstants.CONST_LANGUAGE_CODE,
                        StringConstants.CONST_LOGIN_CODE_RATAILSTATUS_ACTIVE);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    /**
     * Test  Download Scheme Master info.
     */
    @Test
    public void testDownloadSchemeMaster() {
        HttpEntity httpEntity = getHttpEntity();
        var responseEntity = testRestTemplate.
                exchange(StringConstants.PATH_CONTROLLER_FETCH_SCHEME, HttpMethod.GET, httpEntity,
                        DownloadModel.class, StringConstants.CONST_LANGUAGE_CODE,
                        StringConstants.CONST_LOGIN_CODE);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    /**
     * Test  Download Fetch Screen Access info.
     */
    @Test
    public void testDownloadFetchScreenAccess() {
        HttpEntity httpEntity = getHttpEntity();
        var responseEntity = testRestTemplate.
                exchange(StringConstants.PATH_CONTROLLER_FETCH_SCREEN_ACCESS, HttpMethod.GET, httpEntity,
                        DownloadModel.class, StringConstants.CONST_LANGUAGE_CODE,
                        StringConstants.CONST_LOGIN_CODE);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    /**
     * Test  Download Fetch Notification info.
     */
    @Test
    public void testDownloadFetchNotification() {
        HttpEntity httpEntity = getHttpEntity();
        var responseEntity = testRestTemplate.
                exchange(StringConstants.PATH_CONTROLLER_FETCH_NOTIFICATION, HttpMethod.GET, httpEntity,
                        DownloadModel.class, StringConstants.CONST_LANGUAGE_CODE,
                        StringConstants.CONST_LOGIN_CODE_RATAILSTATUS_ACTIVE);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }


    /**
     * Test  Download Previous OrderReport info.
     */
    @Test
    public void testDownloadPreviousOrderReport() {
        HttpEntity httpEntity = getHttpEntity();
        var responseEntity = testRestTemplate.
                exchange(StringConstants.PATH_CONTROLLER_FETCH_PREVIOUSREPORT, HttpMethod.GET, httpEntity,
                        DownloadModel.class, StringConstants.CONST_LANGUAGE_CODE,
                        StringConstants.CONST_LOGIN_CODE_RATAILSTATUS_ACTIVE);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    /**
     * Test  Download Distributor Info info.
     */
    @Test
    public void testDownloadDistributorInfo() {
        HttpEntity httpEntity = getHttpEntity();
        var responseEntity = testRestTemplate.
                exchange(StringConstants.PATH_CONTROLLER_FETCH_DISTRIBUTORINFO, HttpMethod.GET, httpEntity,
                        DownloadModel.class, StringConstants.CONST_LANGUAGE_CODE,
                        StringConstants.CONST_LOGIN_CODE_RATAILSTATUS_ACTIVE);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    /**
     * Test  Download Scheme  info.
     */
    @Test
    public void testDownloadScheme() {
            var userModel = new AppUserModel();
            userModel.setLoginCode(StringConstants.CONST_LOGIN_CODE_RATAILSTATUS_ACTIVE);
            userModel.setPassword(StringConstants.CONST_LOGIN_CODE_RATAILSTATUS_ACTIVE_PASSWORD);
            userModel.setAppVersion(authenticateToken.getApplicationVersion());
            userModel.setSystemDate(new Date());
            HttpEntity<?> request = new HttpEntity<>(userModel,
                    authenticateToken.testTokenCreation(userModel.getLoginCode(), userModel.getPassword()));
            var responseEntity = testRestTemplate.postForEntity(StringConstants.PATH_CONTROLLER_DOWNLOAD_SCHEME,
                    request, DownloadModel.class);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertTrue(Objects.requireNonNull(responseEntity.getBody()).getSystemDateAuthFlag());

    }


    /**
     * method to extract httpEntity.
     * @return HttpEntity
     */
    private HttpEntity<?> getHttpEntity() {
        var userModel = new AppUserModel();
        userModel.setLoginCode(StringConstants.CONST_LOGIN_CODE);
        userModel.setPassword(StringConstants.CONST_PASS_WORD);
        userModel.setAppVersion(authenticateToken.getApplicationVersion());
        userModel.setSystemDate(new Date());
        return new HttpEntity<>(authenticateToken.testTokenCreation(
                userModel.getLoginCode(), userModel.getPassword()));
    }

    /**
     * Test  Download Company Image Id info.
     */
    @Test
    public void testDownloadCompanyImageId() {
        var responseEntity = testRestTemplate.getForEntity(StringConstants.PATH_CONTROLLER_FETCH_COMPANYIMAGE_ID,
                String.class, StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE_ID);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Test  Download Product Image Id info.
     */
    @Test
    public void testDownloadProductImageId() {
        var responseEntity = testRestTemplate.getForEntity(StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE_IDS,
                String.class, StringConstants.PATH_CONTROLLER_FETCH_PRODUCTIMAGE_ID);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }



}
