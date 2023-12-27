package com.botree.sfaweb;

import com.botree.common.model.UploadModel;
import com.botree.common.util.Function;
import com.botree.interdbentity.model.AppUserModel;
import com.botree.interdbentity.model.FavoriteProductEntity;
import com.botree.interdbentity.model.FeedBackEntity;
import com.botree.interdbentity.model.KeyGeneratorEntity;
import com.botree.interdbentity.model.LocationUpdateEntity;
import com.botree.interdbentity.model.OrderBookingDetailsEntity;
import com.botree.interdbentity.model.OrderBookingHeaderEntity;
import com.botree.interdbentity.model.OrderBookingSchemeDetailsEntity;
import com.botree.interdbentity.model.OrderBookingSchemeProductRuleEntity;
import com.botree.interdbentity.model.OrderBookingServiceDetailsEntity;
import com.botree.interdbentity.model.SyncLogEntity;
import com.botree.interdbentity.model.UploadStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * LoginControllerTest class for login process.
 * @author vinodkumar.a
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
public class UploadProcessTest {

    /** testRestTemplate. */
    @Autowired
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    /** authenticateToken. */
    @Autowired
    private AuthenticateToken authenticateToken;


    /**
     * empty constructor.
     */
    public UploadProcessTest() {
    }

    /**
     * test for order booking header upload.
     */
    @Test
    public void testOrderBookingHeader() {
        var orderBookingDetailsEntity = new OrderBookingDetailsEntity();
        orderBookingDetailsEntity.setCmpCode(TransactionStringConstants.CMPCODE);
        orderBookingDetailsEntity.setDistrCode(TransactionStringConstants.DISTRCODE);
        orderBookingDetailsEntity.setOrderNo(TransactionStringConstants.ORDER_BOOKING_DETAILS_ORDERNO);
        orderBookingDetailsEntity.setProdCode(TransactionStringConstants.ORDER_BOOKING_DETAILS_PRODCODE);
        orderBookingDetailsEntity.setProdName(TransactionStringConstants.ORDER_BOOKING_DETAILS_PRODNAME);
        orderBookingDetailsEntity.setProdBatchCode(TransactionStringConstants
                .ORDER_BOOKING_DETAILS_PRODBATCHCODE);
        orderBookingDetailsEntity.setOrderQty(TransactionStringConstants.ORDER_BOOKING_DETAILS_ORDERQTY);
        orderBookingDetailsEntity.setServicedQty(TransactionStringConstants
                .ORDER_BOOKING_DETAILS_SERVICEDQTY);
        orderBookingDetailsEntity.setUomCode(TransactionStringConstants.ORDER_BOOKING_DETAILS_UOMCODE);
        orderBookingDetailsEntity.setInputStr(TransactionStringConstants.ORDER_BOOKING_DETAILS_INPUTSTR);
        orderBookingDetailsEntity.setSellRate(TransactionStringConstants.ORDER_BOOKING_DETAILS_SELLRATE);
        orderBookingDetailsEntity.setActualSellRate(TransactionStringConstants
                .ORDER_BOOKING_DETAILS_ACTUALSELLRATE);
        orderBookingDetailsEntity.setGrossValue(TransactionStringConstants
                .ORDER_BOOKING_DETAILS_GROSSVALUE);
        orderBookingDetailsEntity.setSchAmt(TransactionStringConstants.ORDER_BOOKING_DETAILS_SCHAMT);
        orderBookingDetailsEntity.setTaxAmt(TransactionStringConstants.ORDER_BOOKING_DETAILS_TAXAMT);
        orderBookingDetailsEntity.setTaxCode(TransactionStringConstants.ORDER_BOOKING_DETAILS_TAXCODE);
        orderBookingDetailsEntity.setCgstAmt(TransactionStringConstants.ORDER_BOOKING_DETAILS_CGSTAMT);
        orderBookingDetailsEntity.setCgstPerc(TransactionStringConstants.ORDER_BOOKING_DETAILS_CGSTPERC);
        orderBookingDetailsEntity.setSgstAmt(TransactionStringConstants.ORDER_BOOKING_DETAILS_SGSTAMT);
        orderBookingDetailsEntity.setSgstPerc(TransactionStringConstants.ORDER_BOOKING_DETAILS_SGSTPERC);
        orderBookingDetailsEntity.setUgstAmt(TransactionStringConstants.ORDER_BOOKING_DETAILS_UGSTAMT);
        orderBookingDetailsEntity.setUgstPerc(TransactionStringConstants.ORDER_BOOKING_DETAILS_UGSTPERC);
        orderBookingDetailsEntity.setIgstAmt(TransactionStringConstants.ORDER_BOOKING_DETAILS_IGSTAMT);
        orderBookingDetailsEntity.setIgstPerc(TransactionStringConstants.ORDER_BOOKING_DETAILS_IGSTPERC);
        orderBookingDetailsEntity.setOrderValue(TransactionStringConstants
                .ORDER_BOOKING_DETAILS_ORDERVALUE);
        orderBookingDetailsEntity.setProdType(TransactionStringConstants.ORDER_BOOKING_DETAILS_PRODTYPE);
        orderBookingDetailsEntity.setUploadFlag(TransactionStringConstants.UPLOADFLAG);
        orderBookingDetailsEntity.setModDt(new Date());

        var orderBookingServiceDetails = new OrderBookingServiceDetailsEntity();
        orderBookingServiceDetails.setCmpCode(TransactionStringConstants.CMPCODE);
        orderBookingServiceDetails.setDistrCode(TransactionStringConstants.DISTRCODE);
        orderBookingServiceDetails.setOrderNo(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_ORDERNO);
        orderBookingServiceDetails.setInvoiceNo(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_INVOICENO);
        orderBookingServiceDetails.setProdCode(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_PRODCODE);
        orderBookingServiceDetails.setProdName(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_PRODNAME);
        orderBookingServiceDetails.setProdBatchCode(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_PRODBATCHCODE);
        orderBookingServiceDetails.setSellRate(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_SELLRATE);
        orderBookingServiceDetails.setSoqQty(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_SOQQTY);
        orderBookingServiceDetails.setSoqValue(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_SOQVALUE);
        orderBookingServiceDetails.setOrderQty(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_ORDERQTY);
        orderBookingServiceDetails.setOrderValue(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_ORDERVALUE);
        orderBookingServiceDetails.setServicedQty(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_SERVICEDQTY);
        orderBookingServiceDetails.setServicedValue(TransactionStringConstants
                .ORDERBOOKINGSERVICEDETAILS_SERVICEDVALUE);
        orderBookingServiceDetails.setUploadFlag(TransactionStringConstants.UPLOADFLAG);
        orderBookingServiceDetails.setModDt(new Date());

        var orderBookingSchemeDetails = new OrderBookingSchemeDetailsEntity();
        orderBookingSchemeDetails.setCmpCode(TransactionStringConstants.CMPCODE);
        orderBookingSchemeDetails.setDistrCode(TransactionStringConstants.DISTRCODE);
        orderBookingSchemeDetails.setOrderNo(TransactionStringConstants
                .ORDERBOOKINGSCHEMEDETAILS_ORDERNO);
        orderBookingSchemeDetails.setSchemeCode(TransactionStringConstants
                .ORDERBOOKINGSCHEMEDETAILS_SCHEMECODE);
        orderBookingSchemeDetails.setSlabNo(TransactionStringConstants
                .ORDERBOOKINGSCHEMEDETAILS_SLABNO);
        orderBookingSchemeDetails.setFreeProdCode(TransactionStringConstants
                .ORDERBOOKINGSCHEMEDETAILS_FREEPRODCODE);
        orderBookingSchemeDetails.setFreeQty(TransactionStringConstants
                .ORDERBOOKINGSCHEMEDETAILS_FREEQTY);
        orderBookingSchemeDetails.setUploadFlag(TransactionStringConstants.UPLOADFLAG);
        orderBookingSchemeDetails.setModDt(new Date());

        var orderBookingSchemeProductRule = new OrderBookingSchemeProductRuleEntity();
        orderBookingSchemeProductRule.setCmpCode(TransactionStringConstants.CMPCODE);
        orderBookingSchemeProductRule.setDistrCode(TransactionStringConstants.DISTRCODE);
        orderBookingSchemeProductRule.
                setOrderNo(TransactionStringConstants.ORDERBOOKINGSCHEMEPRODUCTRULE_ORDERNO);
        orderBookingSchemeProductRule.
                setSchemeCode(TransactionStringConstants.ORDERBOOKINGSCHEMEPRODUCTRULE_SCHEMECODE);
        orderBookingSchemeProductRule.
                setSlabNo(TransactionStringConstants.ORDERBOOKINGSCHEMEPRODUCTRULE_SLABNO);
        orderBookingSchemeProductRule.
                setProdCode(TransactionStringConstants.ORDERBOOKINGSCHEMEPRODUCTRULE_PRODCODE);
        orderBookingSchemeProductRule.
                setDiscPerc(TransactionStringConstants.ORDERBOOKINGSCHEMEPRODUCTRULE_DISCPERC);
        orderBookingSchemeProductRule.
                setDiscAmt(TransactionStringConstants.ORDERBOOKINGSCHEMEPRODUCTRULE_DISCAMT);
        orderBookingSchemeProductRule.
                setUploadFlag(TransactionStringConstants.UPLOADFLAG);
        orderBookingSchemeProductRule.
                setModDt(new Date());

        var orderBookingHeaderEntity = new OrderBookingHeaderEntity();
        orderBookingHeaderEntity.setCmpCode(TransactionStringConstants.CMPCODE);
        orderBookingHeaderEntity.setDistrCode(TransactionStringConstants.DISTRCODE);
        orderBookingHeaderEntity.setOrderNo(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_ORDERNO);
        orderBookingHeaderEntity.setCustomerRefNo(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_CUSTOMERREFNO);
        orderBookingHeaderEntity.setCustomerCode(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_CUSTOMERCODE);
        orderBookingHeaderEntity.setCustomerShipCode(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_CUSTOMERSHIPCODE);
        orderBookingHeaderEntity.setOrderDt(new Date());
        orderBookingHeaderEntity.setRemarks(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_REMARKS);
        orderBookingHeaderEntity.setLatitude(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_LATITUDE);
        orderBookingHeaderEntity.setLongitude(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_LONGITUDE);
        orderBookingHeaderEntity.setStartTime(new Date());
        orderBookingHeaderEntity.setEndTime(new Date());
        orderBookingHeaderEntity.setOrderStatus(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_ORDERSTATUS);
        orderBookingHeaderEntity.setTotalGrossValue(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_TOTALGROSSVALUE);
        orderBookingHeaderEntity.setTotalDiscount(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_TOTALDISCOUNT);
        orderBookingHeaderEntity.setTotalTax(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_TOTALTAX);
        orderBookingHeaderEntity.setTotalOrderValue(TransactionStringConstants
                .ORDERBOOKINGHEADERENTITY_TOTALORDERVALUE);
        orderBookingHeaderEntity.setModDt(new Date());
        orderBookingHeaderEntity.setUploadFlag(TransactionStringConstants.UPLOADFLAG);
        orderBookingHeaderEntity.setOrderBookingDetailsList(List.of(orderBookingDetailsEntity));
        orderBookingHeaderEntity.setOrderBookingSchemeDetailsList(List.of(orderBookingSchemeDetails));
        orderBookingHeaderEntity.setOrderBookingSchemeProductRuleList(List.of(orderBookingSchemeProductRule));
        orderBookingHeaderEntity.setOrderBookingServiceDetailsList(List.of(orderBookingServiceDetails));

        var uploadModel = new UploadModel();
        uploadModel.setOrderBookingHeader(String.valueOf(Function.compress(
                List.of(orderBookingHeaderEntity), Boolean.TRUE)));
        var request = getHttpEntity(uploadModel);
        var uploadStatusResponseEntity = testRestTemplate.
                postForEntity(StringConstants.PATH_CONTROLLER_UPLOAD, request, UploadStatus.class);
        var body = uploadStatusResponseEntity.getBody();
        body.getOrderBookingStatusList().forEach(d -> Assert.assertTrue(d.getUploadStatus()));

    }

    /**
     * method to extract httpEntity.
     * @param uploadModel uploadModel
     * @return HttpEntity
     */
    private HttpEntity<?> getHttpEntity(final UploadModel uploadModel) {
        var userModel = new AppUserModel();
        userModel.setLoginCode(StringConstants.CONST_LOGIN_CODE);
        userModel.setPassword(StringConstants.CONST_PASS_WORD);
        userModel.setAppVersion(authenticateToken.getApplicationVersion());
        userModel.setSystemDate(new Date());
        return new HttpEntity<>(uploadModel, authenticateToken.testTokenCreation(
                userModel.getLoginCode(), userModel.getPassword()));
    }

    /**
     * test for keygenerator upload.
     */
    @Test
    public void testKeyGeneratorUpload() {
        var uploadModel = new UploadModel();
        var keyGeneratorEntity = new KeyGeneratorEntity();
        keyGeneratorEntity.setCmpCode(TransactionStringConstants.CMPCODE);
        keyGeneratorEntity.setLoginCode(TransactionStringConstants.LOGINCODE);
        keyGeneratorEntity.setScreenName(TransactionStringConstants.KEYGENERATOR_SCREENNAME);
        keyGeneratorEntity.setSuffixYY(TransactionStringConstants.KEYGENERATOR_SUFFIXYY);
        keyGeneratorEntity.setSuffixNN(TransactionStringConstants.KEYGENERATOR_SUFFIXNN);
        keyGeneratorEntity.setPrefix(TransactionStringConstants.KEYGENERATOR_PREFIX);
        keyGeneratorEntity.setUploadFlag(TransactionStringConstants.UPLOADFLAG);
        keyGeneratorEntity.setModDt(new Date());
        uploadModel.setKeyGenerator(String.valueOf(Function.compress(
                List.of(keyGeneratorEntity), Boolean.TRUE)));
        var request = getHttpEntity(uploadModel);
        var uploadStatusResponseEntity = testRestTemplate.
                postForEntity(StringConstants.PATH_CONTROLLER_UPLOAD, request, UploadStatus.class);
        var body = uploadStatusResponseEntity.getBody();
        body.getKeyGeneratorStatusList().forEach(d -> Assert.assertTrue(d.getUploadStatus()));
    }

    /**
     * test synclog upload test.
     */
    @Test
    public void testSyncLogUpload() {
        var uploadModel = new UploadModel();
        var syncLogEntity = new SyncLogEntity();
        syncLogEntity.setCmpCode(TransactionStringConstants.CMPCODE);
        syncLogEntity.setLoginCode(TransactionStringConstants.SYNCLOG_LOGINCODE);
        syncLogEntity.setSyncDt(new Date());
        syncLogEntity.setSyncStartTime(TransactionStringConstants.SYNCLOG_LOGTIME);
        syncLogEntity.setSyncEndTime(TransactionStringConstants.SYNCLOG_LOGTIME);
        syncLogEntity.setMode(TransactionStringConstants.SYNCLOG_MODE);
        syncLogEntity.setProcessName(TransactionStringConstants.SYNCLOG_PROCESSNAME);
        syncLogEntity.setUserName(TransactionStringConstants.SYNCLOG_USERNAME);
        syncLogEntity.setAppVersion(TransactionStringConstants.SYNCLOG_APPVERSION);
        syncLogEntity.setUploadFlag(TransactionStringConstants.UPLOADFLAG);
        syncLogEntity.setModDt(new Date());
        uploadModel.setSyncLog(String.valueOf(Function.compress(List.of(syncLogEntity), Boolean.TRUE)));
        var request = getHttpEntity(uploadModel);
        var uploadStatusResponseEntity = testRestTemplate.
                postForEntity(StringConstants.PATH_CONTROLLER_UPLOAD, request, UploadStatus.class);
        var body = uploadStatusResponseEntity.getBody();
        body.getSyncLogEntityStatusList().forEach(d -> Assert.assertNotNull(d));
    }

    /**
     * test to upload feedback.
     */
    @Test
    public void testFeedbackUpload() {
        var uploadModel = new UploadModel();
        var feedBackEntity = new FeedBackEntity();
        feedBackEntity.setCmpCode(TransactionStringConstants.CMPCODE);
        feedBackEntity.setDistrCode(TransactionStringConstants.DISTRCODE);
        feedBackEntity.setCustomerCode(TransactionStringConstants.CUSTOMERCODE);
        feedBackEntity.setFeedBackDt(new Date());
        feedBackEntity.setFeedbackNo(TransactionStringConstants.FEEDBACK_NO);
        feedBackEntity.setFeedbackType(TransactionStringConstants.FEEDBACK_FEEDBACKTYPE);
        feedBackEntity.setMessage(TransactionStringConstants.MESSAGE);
        feedBackEntity.setImage(TransactionStringConstants.IMAGE);
        feedBackEntity.setImagePath(TransactionStringConstants.IMAGEPATH);
        feedBackEntity.setModDt(new Date());
        uploadModel.setFeedBack(String.valueOf(Function.compress(List.of(feedBackEntity), Boolean.TRUE)));
        var request = getHttpEntity(uploadModel);
        var uploadStatusResponseEntity = testRestTemplate.
                postForEntity(StringConstants.PATH_CONTROLLER_UPLOAD, request, UploadStatus.class);
        var body = uploadStatusResponseEntity.getBody();
        body.getFeedBackStatusList().forEach(d -> Assert.assertTrue(d.getUploadStatus()));
    }

    /**
     * test upload location update.
     */
    @Test
    public void testLocationUpdateUpload() {
        var locationUpdateEntity = new LocationUpdateEntity();
        locationUpdateEntity.setDistrCode(TransactionStringConstants.DISTRCODE);
        locationUpdateEntity.setCmpCode(TransactionStringConstants.CMPCODE);
        locationUpdateEntity.setImage(TransactionStringConstants.IMAGE);
        locationUpdateEntity.setImagePath(TransactionStringConstants.IMAGEPATH);
        locationUpdateEntity.setLoginCode(TransactionStringConstants.LOGINCODE);
        locationUpdateEntity.setLatitude(TransactionStringConstants.LOCATIONUPDATEENTITY_LATITUDE);
        locationUpdateEntity.setLongitude(TransactionStringConstants.LOCATIONUPDATEENTITY_LONGITUDE);
        locationUpdateEntity.setPostalCode(TransactionStringConstants.LOCATIONUPDATEENTITY_POSTALCODE);
        locationUpdateEntity.setUpdateDt(new Date());
        locationUpdateEntity.setUploadTo(TransactionStringConstants.LOCATIONUPDATEENTITY_UPLOADTO);
        locationUpdateEntity.setModDt(new Date());
        var uploadModel = new UploadModel();
        uploadModel.setLocationUpdate(String.valueOf(Function.compress(List.of(locationUpdateEntity), Boolean.TRUE)));
        var request = getHttpEntity(uploadModel);
        var uploadStatusResponseEntity = testRestTemplate.
                postForEntity(StringConstants.PATH_CONTROLLER_UPLOAD, request, UploadStatus.class);
        var body = uploadStatusResponseEntity.getBody();
        body.getLocationUpdateStatusList().forEach(d -> Assert.assertTrue(d.getUploadStatus()));
    }

    /**
     * test non compress upload data.
     */
    @Test
    public void testNonCompressUpload() {
        var favoriteProductEntity = new FavoriteProductEntity();
        favoriteProductEntity.setCmpCode(TransactionStringConstants.CMPCODE);
        favoriteProductEntity.setDistrCode(TransactionStringConstants.DISTRCODE);
        favoriteProductEntity.setCustomerCode(TransactionStringConstants.CUSTOMERCODE);
        favoriteProductEntity.setFavProdCode(TransactionStringConstants.FAVORITEPRODUCT_FAVPRODCODE);
        favoriteProductEntity.setUploadFlag(TransactionStringConstants.UPLOADFLAG);
        favoriteProductEntity.setModDt(new Date());

        var syncLogEntity = new SyncLogEntity();
        syncLogEntity.setCmpCode(TransactionStringConstants.CMPCODE);
        syncLogEntity.setLoginCode(TransactionStringConstants.SYNCLOG_LOGINCODE);
        syncLogEntity.setSyncDt(new Date());
        syncLogEntity.setSyncStartTime(TransactionStringConstants.SYNCLOG_LOGTIME);
        syncLogEntity.setSyncEndTime(TransactionStringConstants.SYNCLOG_LOGTIME);
        syncLogEntity.setMode(TransactionStringConstants.SYNCLOG_MODE);
        syncLogEntity.setProcessName(TransactionStringConstants.SYNCLOG_PROCESSNAME);
        syncLogEntity.setUserName(TransactionStringConstants.SYNCLOG_USERNAME);
        syncLogEntity.setAppVersion(TransactionStringConstants.SYNCLOG_APPVERSION);
        syncLogEntity.setUploadFlag(TransactionStringConstants.UPLOADFLAG);
        syncLogEntity.setModDt(new Date());

        var keyGeneratorEntity = new KeyGeneratorEntity();
        keyGeneratorEntity.setCmpCode(TransactionStringConstants.CMPCODE);
        keyGeneratorEntity.setLoginCode(TransactionStringConstants.LOGINCODE);
        keyGeneratorEntity.setScreenName(TransactionStringConstants.KEYGENERATOR_SCREENNAME);
        keyGeneratorEntity.setSuffixYY(TransactionStringConstants.KEYGENERATOR_SUFFIXYY);
        keyGeneratorEntity.setSuffixNN(TransactionStringConstants.KEYGENERATOR_SUFFIXNN);
        keyGeneratorEntity.setPrefix(TransactionStringConstants.KEYGENERATOR_PREFIX);
        keyGeneratorEntity.setUploadFlag(TransactionStringConstants.UPLOADFLAG);
        keyGeneratorEntity.setModDt(new Date());

        var orderBookingHeaderEntity = new OrderBookingHeaderEntity();

        var uploadModel = new UploadModel();
        uploadModel.setEnableCompress(Boolean.FALSE);
        uploadModel.setFavoriteProductList(List.of(favoriteProductEntity));
        uploadModel.setSyncLogList(List.of(syncLogEntity));
        uploadModel.setKeyGeneratorList(List.of(keyGeneratorEntity));
        var request = getHttpEntity(uploadModel);
        var uploadStatusResponseEntity = testRestTemplate.
                postForEntity(StringConstants.PATH_CONTROLLER_UPLOAD, request, UploadStatus.class);
        var body = uploadStatusResponseEntity.getBody();
        body.getFavoriteProductStatusList().forEach(d -> Assert.assertTrue(d.getUploadStatus()));
        body.getSyncLogEntityStatusList().forEach(d -> Assert.assertNotNull(d));
        body.getKeyGeneratorStatusList().forEach(d -> Assert.assertTrue(d.getUploadStatus()));

    }
}
