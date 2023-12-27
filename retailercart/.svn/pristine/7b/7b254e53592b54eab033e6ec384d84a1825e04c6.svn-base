package com.botree.mdmtointerdb.service;

import com.botree.interdbentity.model.OrderBookingDetailsEntity;
import com.botree.interdbentity.model.OrderBookingHeaderEntity;
import com.botree.mdmtointerdb.constants.StringConstants;
import com.botree.mdmtointerdb.dao.DAORepository;
import com.botree.mdmtointerdb.model.CustomerMasterMappingModel;
import com.botree.mdmtointerdb.model.OrderHeaderModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.text.SimpleDateFormat;

/**
 * This class is used to save the data.
 *
 * @author Rajkumar D
 */
@Service
@Transactional
public class TgblOrderService {

    /**
     * repository.
     */
    private final DAORepository repository;

    /**
     * queryService.
     */
    private final IQueryService queryService;

    /**
     * Constructor Method.
     *
     * @param repositoryIn   repositoryIn
     * @param queryServiceIn queryServiceIn
     */
    public TgblOrderService(final DAORepository repositoryIn,
                            final IQueryService queryServiceIn) {
        this.repository = repositoryIn;
        this.queryService = queryServiceIn;
    }

    /**
     * Method(convertPostData) fetch all the process query which needs to be deleted
     * from the DB.
     *
     * @param cmpCode is company code
     * @param list    list
     */
    @SuppressWarnings("rawtypes")
    @Transactional
    public void convertTransactionData(final List list,
                                       final String cmpCode) {
        final Map<String, Object> paramMap = new HashMap<>();
        var objectArrayList = new ArrayList<>();
        var orderBookingHeader = new ArrayList<OrderBookingHeaderEntity>(list);
        if (!orderBookingHeader.isEmpty()) {
            orderBookingHeader.forEach(
                    orderModel -> objectArrayList.add(orderModel.getDistrCode() + '^' + orderModel.getCustomerCode()));
            paramMap.put(StringConstants.CMP_CODE, cmpCode);
            paramMap.put(StringConstants.DISTR_CODE, objectArrayList);
            // Get the Customer Details like salesman,route etc..
            var mappingList = repository.
                    queryForList(this.queryService.get(StringConstants.FETCH_CUSTOMER_MASTER_ROUTE_MAPPING_TGBL),
                            paramMap, CustomerMasterMappingModel.class);
            List<OrderBookingDetailsEntity> orderDetails = new ArrayList<>();
            var stringListMap =
                    mappingList.stream().collect(groupingBy(CustomerMasterMappingModel::getDistrCustomerCode,
                            mapping((CustomerMasterMappingModel masterMappingModel) -> masterMappingModel,
                                    toList())));
            var orderHeaders = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            //   Iterating the List of order values from SSFA Retailer App
            for (OrderBookingHeaderEntity orderHeader : orderBookingHeader) {
                var orderHeaderModel = new OrderHeaderModel();
                var customerMasterMappingModels =
                        stringListMap.get(orderHeader.getDistrCode() + '^' + orderHeader.getCustomerCode());
                // Based on distributor CustomerCode set the salesman and route code values.
                if (customerMasterMappingModels != null) {
                    orderHeaderModel.setCmpCode(orderHeader.getCmpCode());
                    orderHeaderModel.setDistrCode(orderHeader.getDistrCode());
                    orderHeaderModel.setCustomerCode(orderHeader.getCustomerCode());
                    orderHeaderModel.setOrderNo(orderHeader.getOrderNo());
                    orderHeaderModel.setSalesmanCode(customerMasterMappingModels.get(0).getSalesmanCode());
                    orderHeaderModel.setRouteCode(customerMasterMappingModels.get(0).getRouteCode());
                    orderHeaderModel.setOrderDtString(formatter.format(orderHeader.getOrderDt()));
                    orderHeaderModel.setCustomerShipCode(orderHeader.getCustomerShipCode());
                    orderHeaders.add(orderHeaderModel);
                    for (OrderBookingDetailsEntity detModel : orderHeader.getOrderBookingDetailsList()) {
                         detModel.setGrossValue(detModel.getOrderQty() * detModel.getSellRate());
                    }
                    orderDetails.addAll(orderHeader.getOrderBookingDetailsList());
                }
            }
            repository.bulkInsert(this.queryService.get(StringConstants.INSERT_ORDER_HEADER_TGBL), orderHeaders);
            repository.bulkInsert(this.queryService.get(StringConstants.INSERT_ORDER_DETAILS_TGBL), orderDetails);
            repository.bulkInsert(this.queryService.get(StringConstants.INSERT_ORDER_CHANGELOG_TGBL), orderHeaders);
        }
    }
}
