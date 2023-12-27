package com.botree.reportcompute;

import com.botree.common.constants.ReportConstants;
import com.botree.common.constants.StringConstants;
import com.botree.reportcompute.service.ActionParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class contains the scheduler to perform the action template logic.
 * @author vinodkumar.a
 */
@Component
public class ActionTemplateSchedulerService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ActionTemplateSchedulerService.class);

    /** DELAY. */
    private static final long DELAY = 100000L;

    /** actionEnable. */
    @Value("${cron.action.enable}")
    private Boolean actionEnable;

    /** actionProcess. */
    @Value("${cron.action.process.enable}")
    private String actionProcess;

    /** actionParserService. */
    private final ActionParserService actionParserService;

    /**
     * Constructor Method.
     * @param actionParserServiceIn actionParserServiceIn
     */
    public ActionTemplateSchedulerService(final ActionParserService actionParserServiceIn) {
        this.actionParserService = actionParserServiceIn;
    }

    /**
     * Method to trigger the other specific Notification.
     */
    @Scheduled(fixedDelay = DELAY)
    public final void performActionParser() {
        if (Boolean.TRUE.equals(actionEnable)) {
            LOG.info("perform action start :- ");
            var map = getProcessMap();
            map.forEach((k, v) -> {
                if (Boolean.TRUE.equals(v)) {
                    switch (k) {
                        case ReportConstants.ACTION_CODE_ORDER_STATUS:
                            actionParserService.parseOrderStatus();
                            break;
                        case ReportConstants.ACTION_CODE_ORDER_INVOICE_VALUE:
                            actionParserService.parseOrderInvoiceValue();
                            break;
                        case ReportConstants.ACTION_CODE_ORDER_INVOICE_LINE_LEVEL:
                            actionParserService.parseOrderInvoiceLineLevel();
                            break;
                        case ReportConstants.ACTION_CODE_SMS_NOTIFICATION_FOR_ALL:
                            actionParserService.parseSMSNotificationForAll();
                            break;
                        case ReportConstants.ACTION_CODE_SMS_NOTIFICATION_FOR_DISTR:
                            actionParserService.parseSMSNotificationForDistr();
                            break;
                        case ReportConstants.ACTION_CODE_SMS_NOTIFICATION_FOR_CUST:
                            actionParserService.parseSMSNotificationForCustomer();
                            break;
                        default:
                            break;
                    }
                }
            });
            LOG.info("perform action end :- ");
        }
    }

    /**
     * Method to get process map.
     * @return process map
     */
    private Map<String, Boolean> getProcessMap() {
        Map<String, Boolean> map = new LinkedHashMap<>();
        var processArr = actionProcess.split(StringConstants.CONST_COMMA);
        for (var process : processArr) {
            var str = process.split(StringConstants.CONST_COLON);
            map.put(str[0], Boolean.valueOf(str[1]));
        }
        return map;
    }
}
