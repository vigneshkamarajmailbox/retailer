package com.botree.mdmtointerdb.service;

import com.botree.interdbentity.model.LoginReferralEntity;
import com.botree.interdbentity.model.OrderBookingHeaderEntity;
import com.botree.interdbentity.model.OrderReferralEntity;
import com.botree.interdbentity.model.SyncLogEntity;
import com.botree.mdmtointerdb.constants.StringConstants;
import com.botree.mdmtointerdb.dao.DAORepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to save the data.
 * @author vinodkumar.a
 */
@Service
@Transactional
public class DaburOrderService {


    /** repository. */
    private final DAORepository repository;

    /** queryService. */
    private final IQueryService queryService;

    /**
     * Constructor Method.
     * @param repositoryIn   repositoryIn
     * @param queryServiceIn queryServiceIn
     */
    public DaburOrderService(final DAORepository repositoryIn,
                             final IQueryService queryServiceIn) {
        this.repository = repositoryIn;
        this.queryService = queryServiceIn;
    }

    /**
     * Method(convertPostData) fetch all the process query which needs to be deleted
     * from the DB.
     * @param list list
     */
    @Transactional
    public void convertTransactionData(final List<OrderBookingHeaderEntity> list) {
        var headerList = new ArrayList<>();
        var detailList = new ArrayList<>();
        list.forEach(model -> {
            headerList.add(model);
            detailList.addAll(model.getOrderBookingDetailsList());
        });
        repository.bulkInsert(queryService.get(StringConstants.INSERT_ORDER_HEADER_DABUR), headerList);
        repository.bulkInsert(queryService.get(StringConstants.INSERT_ORDER_DETAILS_DABUR), detailList);
    }


    /**
     * method used to save login referral
     * from the DB.
     * @param list list
     */
    @Transactional
    public void saveLoginReferral(final List<LoginReferralEntity> list) {
        repository.bulkInsert(queryService.get(StringConstants.INSERT_LOGIN_REFERRAL_DABUR), list);
    }

    /**
     * method used to save order referral
     * from the DB.
     * @param list list
     */
    @Transactional
    public void saveOrderReferral(final List<OrderReferralEntity> list) {
        repository.bulkInsert(queryService.get(StringConstants.INSERT_ORDER_REFERRAL_DABUR), list);
    }
    /**
     * method used to save order referral
     * from the DB.
     * @param list list
     */
    @Transactional
    public void saveSyncLog(final List<SyncLogEntity> list) {
        repository.bulkInsert(queryService.get(StringConstants.INSERT_SYNC_LOG_DABUR), list);
    }
}
