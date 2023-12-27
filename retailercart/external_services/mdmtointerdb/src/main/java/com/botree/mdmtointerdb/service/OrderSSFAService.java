package com.botree.mdmtointerdb.service;

import com.botree.interdbentity.model.ExternalProcessDetail;
import com.botree.interdbentity.model.LoginReferralEntity;
import com.botree.interdbentity.model.OrderBookingHeaderEntity;
import com.botree.interdbentity.model.OrderReferralEntity;
import com.botree.interdbentity.model.SyncLogEntity;
import com.botree.mdmtointerdb.constants.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class is used to initialize the SSFA order classes.
 */
@Component
public class OrderSSFAService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(OrderSSFAService.class);

    /** OrderService. */
    private final OrderService orderService;

    /** nestleOrderService. */
    private final NestleOrderService nestleOrderService;

    /** tgblOrderService. */
    private final TgblOrderService tgblOrderService;

    /** daburOrderService. */
    private final DaburOrderService daburOrderService;

    /**
     * Constructor Method.
     * @param orderServiceIn       orderServiceIn
     * @param nestleOrderServiceIn nestleOrderServiceIn
     * @param tgblOrderServiceIn   tgblOrderServiceIn
     * @param daburOrderServiceIn  daburOrderServiceIn
     */
    public OrderSSFAService(final OrderService orderServiceIn,
                            final NestleOrderService nestleOrderServiceIn,
                            final TgblOrderService tgblOrderServiceIn,
                            final DaburOrderService daburOrderServiceIn) {
        this.orderService = orderServiceIn;
        this.nestleOrderService = nestleOrderServiceIn;
        this.tgblOrderService = tgblOrderServiceIn;
        this.daburOrderService = daburOrderServiceIn;
    }

    /**
     * Method(convertOrderTransactionData) fetch all the process query which needs to be deleted
     * from the DB.
     * @param cmpCode is company code
     * @param data    ExternalProcessDetail
     * @param list    list
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void convertOrderTransactionData(final List list, final String cmpCode,
                                            final ExternalProcessDetail data) {
        switch (cmpCode) {
            case StringConstants.CMP_PERFETTI:
                orderService.convertTransactionData(list, cmpCode);
                break;
            case StringConstants.CMP_IN_14:
                nestleOrderService.convertTransactionData(list, cmpCode);
                break;
            case StringConstants.CMP_TGBL:
                tgblOrderService.convertTransactionData(list, cmpCode);
                break;
            case StringConstants.CMP_DIL:
                daburOrderService.convertTransactionData((List<OrderBookingHeaderEntity>) list);
                break;
            default:
                LOG.info("No data to process :: ");
        }
    }

    /**
     * the process based operation for Dabur client.
     * from the DB.
     * @param data ExternalProcessDetail
     * @param list list
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void processBasedOperation(final List list,
                                      final ExternalProcessDetail data) {
        switch (data.getInterDBProcess()) {
            case StringConstants.PROCESS_TYPE_LOGIN_REFERRAL:
                daburOrderService.saveLoginReferral((List<LoginReferralEntity>) list);
                break;
            case StringConstants.PROCESS_TYPE_ORDER_REFERRAL:
                daburOrderService.saveOrderReferral((List<OrderReferralEntity>) list);
                break;
            case StringConstants.PROCESS_TYPE_SYNC_LOG:
                daburOrderService.saveSyncLog((List<SyncLogEntity>) list);
                break;
            default:
                LOG.info("No data to process :: {}", data.getInterDBProcess());
        }

    }

}
