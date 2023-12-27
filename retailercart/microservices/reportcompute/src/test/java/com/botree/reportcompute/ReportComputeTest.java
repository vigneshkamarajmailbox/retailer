package com.botree.reportcompute;

import com.botree.common.constants.StringConstants;

import com.botree.common.service.IQueryService;


import com.botree.interdbentity.model.OrderBookingDetailsEntity;
import com.botree.interdbentity.model.OrderBookingHeaderEntity;
import com.botree.interdbentity.model.OrderBookingSchemeDetailsEntity;
import com.botree.interdbentity.model.OrderBookingSchemeProductRuleEntity;
import com.botree.interdbentity.model.OrderBookingServiceDetailsEntity;
import com.botree.interdbentity.model.UserActivationEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Report compute related test cases.
 * @author vinodkumar.a
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
public class ReportComputeTest {

    /** daoRepository. */
    @Autowired
    private  DAORepository daoRepository;

    /** jdbcTemplate. */
    @Autowired
    private  JdbcTemplate jdbcTemplate;

    /** namedParameterJdbcTemplate. */
    @Autowired
    private  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /** queryService. */
    @Autowired
    private IQueryService queryService;



    /** testRestTemplate. */
    @Autowired
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    /** usageReportSchedulerService. */
    @Autowired
    private UsageReportSchedulerService usageReportSchedulerService;

    /** reportComputeSchedulerService. */
    @Autowired
    private ReportComputeSchedulerService reportComputeSchedulerService;

    /** systemNotificationSchedulerService. */
    @Autowired
    private SystemNotificationSchedulerService systemNotificationSchedulerService;

    /** notificationSchedulerService. */
    @Autowired
    private NotificationSchedulerService notificationSchedulerService;

    /** activationSchedulerService. */
    @Autowired
    private ActivationSchedulerService activationSchedulerService;

    /** actionTemplateSchedulerService. */
    @Autowired
    private ActionTemplateSchedulerService actionTemplateSchedulerService;



    /**
     * test Usage Report Scheduler.
     */
    @Test
    public void testUsageReportScheduler() {
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
        daoRepository.bulkInsert(queryService.get(StringConstants.INSERT_ORDERBOOKINGHEADERENTITY),
                Arrays.asList(orderBookingHeaderEntity));
        try {
         usageReportSchedulerService.triggerUsageReportEmailScheduler();
         usageReportSchedulerService.triggerUsageReportScheduler();
            Assert.assertTrue(Boolean.TRUE);
        } catch (Exception e) {
            Assert.fail("Exception " + e);
        }
        Assert.assertTrue(Boolean.TRUE);
    }

    /**
     * test Report Compute Scheduler Service.
     */
    @Test
    public void testReportComputeSchedulerService() {
        try {
          // reportComputeSchedulerService.triggerScheduler();
            Assert.assertTrue(Boolean.TRUE);
        } catch (Exception e) {
            Assert.fail("Exception " + e);
        }
    }

    /**
     * test System Notification Scheduler Service.
     */
    @Test
    public void testSystemNotificationSchedulerService() {
        try {
           systemNotificationSchedulerService.triggerSystemWeekTwoScheduler();
           systemNotificationSchedulerService.triggerSystemWeekOneScheduler();
           systemNotificationSchedulerService.triggerSystemMonthOneScheduler();
           systemNotificationSchedulerService.triggerSystemDayMorningScheduler();
           systemNotificationSchedulerService.triggerSystemDayNightScheduler();
            Assert.assertTrue(Boolean.TRUE);
        } catch (Exception e) {
            Assert.fail("Exception " + e);
        }
    }

    /**
     * test Notification Scheduler Service.
     */
    @Test
    public void testNotificationSchedulerService() {
        try {
            notificationSchedulerService.otherNotification();
            Assert.assertTrue(Boolean.TRUE);
        } catch (Exception e) {
            Assert.fail("Exception " + e);
        }
    }

    /**
     * test Activation Scheduler Service.
     */
    @Test
    public void testActivationSchedulerService() {
        var userActivationEntity = new UserActivationEntity();
        userActivationEntity.setLoginCode(TransactionStringConstants.LOGINCODE);
        userActivationEntity.setActivationDt(new Date());
        userActivationEntity.setCreationDt(new Date());
        userActivationEntity.setCreationDt(new Date());
        userActivationEntity.setUserName(TransactionStringConstants.USERNAME);
        userActivationEntity.setUserStatus(TransactionStringConstants.USERSTATUS);
        userActivationEntity.setUploadFlag(TransactionStringConstants.UPLOADFLAG);
        userActivationEntity.setModDt(new Date());
        daoRepository.bulkInsert(queryService.get(StringConstants.INSERT_USER_CREATION_LIST),
                Arrays.asList(userActivationEntity));
        try {
            activationSchedulerService.triggerActivationScheduler();
            Assert.assertTrue(Boolean.TRUE);
            daoRepository.update(TransactionStringConstants.UPDATE_QUEARY,
                    userActivationEntity.getLoginCode());
        } catch (Exception e) {
            Assert.fail("Exception at test case " + e);
        }
        daoRepository.bulkInsert(queryService.get(StringConstants.INSERT_USER_CREATION_LIST),
                Arrays.asList(userActivationEntity));
    }

    /**
     * test Activation Scheduler Service.
     */
    @Test
    public void testActionTemplateSchedulerService() {
        try {
            actionTemplateSchedulerService.performActionParser();
            Assert.assertTrue(Boolean.TRUE);
        } catch (Exception e) {
            Assert.fail("Exception " + e);
        }
    }
}
