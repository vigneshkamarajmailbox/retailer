package com.botree.interdb.service;

import com.botree.common.constants.StringConstants;
import com.botree.common.exception.ServiceException;
import com.botree.common.service.IQueryService;
import com.botree.common.util.PasswordEncryptor;
import com.botree.interdbentity.model.AppUserEntity;
import com.botree.interdbentity.model.CustomerEntity;
import com.botree.interdbentity.model.CustomerStockEntity;
import com.botree.interdbentity.model.DashboardEntity;
import com.botree.interdbentity.model.DistributorStockEntity;
import com.botree.interdbentity.model.KeyGeneratorEntity;
import com.botree.interdbentity.model.ProductBatchEntity;
import com.botree.interdbentity.model.UserActivationEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is call the repository with respective data.
 * @author vinodkumar.a
 */
@Component
public class InterDBService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(InterDBService.class);

    /** queryService. */
    private final IQueryService queryService;

    /** repository. */
    private final DAORepository repository;

    /**
     * Constructor Method.
     * @param queryServiceIn queryServiceIn
     * @param repositoryIn   repositoryIn
     */
    public InterDBService(final IQueryService queryServiceIn, final DAORepository repositoryIn) {
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
    }


    /**
     * Method to save the data in DB.
     * @param list   data
     * @param entity entity
     */
    @SuppressWarnings("unchecked")
    public final void save(final List<?> list, final String entity) {
        LOG.info("save start :: {} {}", entity, list.size());
        try {
            var insertQuery = queryService.get(StringConstants.INSERT_MASTER
                    + Class.forName(entity).getSimpleName().toUpperCase());
            var deleteQuery = queryService.get(StringConstants.DELETE_MASTER
                    + Class.forName(entity).getSimpleName().toUpperCase());
            switch (entity) {
                case StringConstants.CUSTOMER_ENTITY:
                    createAppUser((List<CustomerEntity>) list);
                    createKeyGenerator((List<CustomerEntity>) list);
                    createUserActivation((List<CustomerEntity>) list);
                    break;
                case StringConstants.PRODUCT_BATCH_ENTITY:
                    processProductBatchLatest((List<ProductBatchEntity>) list);
                    break;
                case StringConstants.DASHBOARD_ENTITY:
                    createDashboardWidget((List<DashboardEntity>) list);
                    break;
                case StringConstants.DISTRIBUTOR_STOCK_ENTITY:
                    saveDistributorStock((List<DistributorStockEntity>) list);
                    break;
                case StringConstants.CUSTOMER_STOCK_ENTITY:
                    saveCustomerStock((List<CustomerStockEntity>) list);
                    break;
                default:
                    break;
            }
            if (deleteQuery != null && !deleteQuery.isEmpty()) {
                repository.bulkInsert(deleteQuery, list);
            }
            if (insertQuery != null && !insertQuery.isEmpty()) {
                repository.bulkInsert(insertQuery, list);
            }
        } catch (ClassNotFoundException e) {
            throw new ServiceException("save exception", e);
        }
    }

    /**
     * Method to save app user in DB.
     * @param list app user list
     */
    private void createAppUser(final List<CustomerEntity> list) {
        var userList = new ArrayList<>();
        list.forEach(data -> {
            if (data.getMobileNo() != null && !data.getMobileNo().isEmpty()
                    && data.getMobileNo().length() == StringConstants.CONST_INT_10) {
                var user = new AppUserEntity();
                user.setLoginCode(data.getMobileNo());
                user.setPassword(PasswordEncryptor.digest(data.getMobileNo(), data.getMobileNo(), Boolean.FALSE));
                user.setUserName(data.getCustomerName());
                user.setUserStatus(data.getRetailerStatus());
                user.setNewPassword(StringConstants.CONST_YES);
                user.setGroupCode(StringConstants.CONST_DEFAULT);
                user.setUploadFlag(StringConstants.CONST_NO);
                user.setModDt(new Date());
                userList.add(user);
            }
        });
        repository.bulkInsert(queryService.get(StringConstants.INSERT_MASTER_APPUSERENTITY), userList);
    }

    /**
     * Method to save default key generator for customer in DB.
     * @param list customer list
     */
    private void createKeyGenerator(final List<CustomerEntity> list) {
        var keyGeneratorList = new ArrayList<>();
        var existingKeyGeneratorList =
                repository.queryForListWithRowMapper(queryService.get(StringConstants.FETCH_DEFAULT_KEYGENERATOR),
                        KeyGeneratorEntity.class);
        list.forEach(data -> {
            if (data.getMobileNo() != null && !data.getMobileNo().isEmpty()
                    && data.getMobileNo().length() == StringConstants.CONST_INT_10) {
                existingKeyGeneratorList.forEach(keyGenerator -> {
                    var model = new KeyGeneratorEntity();
                    model.setLoginCode(data.getMobileNo());
                    model.setScreenName(keyGenerator.getScreenName());
                    model.setPrefix(keyGenerator.getPrefix());
                    model.setSuffixYY(keyGenerator.getSuffixYY());
                    model.setSuffixNN(keyGenerator.getSuffixNN());
                    keyGeneratorList.add(model);
                });
            }
        });
        repository.bulkInsert(queryService.get(StringConstants.INSERT_DEFAULT_KEYGENERATORENTITY), keyGeneratorList);
    }

    /**
     * Method to create user activation process.
     * @param list list
     */
    private void createUserActivation(final List<CustomerEntity> list) {
        var userCreationList = new ArrayList<UserActivationEntity>();
        var userUpdateList = new ArrayList<UserActivationEntity>();
        var userDeactivationList = new ArrayList<UserActivationEntity>();
        var activeUserList = repository.queryForList(
                queryService.get(StringConstants.FETCH_USER_ACTIVE_LIST), String.class);
        var inActiveUserList = repository.queryForList(
                queryService.get(StringConstants.FETCH_USER_INACTIVE_LIST), String.class);
        list.forEach(data ->
                checkUserStatus(userCreationList, userUpdateList, userDeactivationList, activeUserList,
                        inActiveUserList, data)
        );
        repository.bulkInsert(queryService.get(StringConstants.INSERT_USER_CREATION_LIST), userCreationList);
        repository.bulkInsert(queryService.get(StringConstants.UPDATE_USER_DEACTIVATION_LIST), userDeactivationList);
        repository.bulkInsert(queryService.get(StringConstants.UPDATE_USER_CREATION_LIST), userUpdateList);
    }

    /**
     * Check user status and assign to list.
     * @param userCreationList     userCreationList
     * @param userUpdateList       userUpdateList
     * @param userDeactivationList userDeactivationList
     * @param activeUserList       activeUserList
     * @param inActiveUserList     inActiveUserList
     * @param data                 data
     */
    private void checkUserStatus(final List<UserActivationEntity> userCreationList,
                                 final List<UserActivationEntity> userUpdateList,
                                 final List<UserActivationEntity> userDeactivationList,
                                 final List<String> activeUserList,
                                 final List<String> inActiveUserList, final CustomerEntity data) {
        if (data.getMobileNo() != null && !data.getMobileNo().isEmpty()
                && data.getMobileNo().length() == StringConstants.CONST_INT_10) {
            var userActivation = new UserActivationEntity();
            userActivation.setLoginCode(data.getMobileNo());
            userActivation.setUserName(data.getCustomerName());
            setUserActivationStatus(data, userActivation);
            formUserActivationList(userCreationList, userUpdateList, userDeactivationList, activeUserList,
                    inActiveUserList, data, userActivation);
            userActivation.setUploadFlag(StringConstants.CONST_NO);
            userActivation.setModDt(new Date());
        }
    }

    /**
     * Method to set user activation status.
     * @param data           data
     * @param userActivation userActivation
     */
    private void setUserActivationStatus(final CustomerEntity data, final UserActivationEntity userActivation) {
        if (StringConstants.CONST_YES.equalsIgnoreCase(data.getRetailerStatus())) {
            userActivation.setUserStatus(StringConstants.CONST_ACTIVATION);
        } else {
            userActivation.setUserStatus(StringConstants.CONST_DEACTIVATION);
        }
    }

    /**
     * Method to form activation, deactivation and update list.
     * @param userCreationList     userCreationList
     * @param userUpdateList       userUpdateList
     * @param userDeactivationList userDeactivationList
     * @param activeUserList       activeUserList
     * @param inActiveUserList     inActiveUserList
     * @param data                 data
     * @param userActivation       userActivation
     */
    private void formUserActivationList(final List<UserActivationEntity> userCreationList,
                                        final List<UserActivationEntity> userUpdateList,
                                        final List<UserActivationEntity> userDeactivationList,
                                        final List<String> activeUserList,
                                        final List<String> inActiveUserList,
                                        final CustomerEntity data,
                                        final UserActivationEntity userActivation) {
        if (!activeUserList.contains(data.getMobileNo())
                && !inActiveUserList.contains(data.getMobileNo())) {
            userActivation.setCreationDt(new Date());
            userCreationList.add(userActivation);
        } else if (activeUserList.contains(data.getMobileNo())
                && StringConstants.CONST_NO.equalsIgnoreCase(data.getRetailerStatus())) {
            userActivation.setDeactivationDt(new Date());
            userDeactivationList.add(userActivation);
        } else {
            userUpdateList.add(userActivation);
        }
    }

    /**
     * Method to process the latest product batch.
     * @param list list
     */
    public void processProductBatchLatest(final List<ProductBatchEntity> list) {
        var updateList = new ArrayList<>();
        var existingProd = new ArrayList<>();
        list.forEach(data -> {
            if (StringConstants.CONST_YES.equalsIgnoreCase(data.getLatestBatch())) {
                updateList.add(data);
                if (existingProd.contains(data.productKey())) {
                    data.setLatestBatch(StringConstants.CONST_NO);
                }
                existingProd.add(data.productKey());
            }
        });
        if (!updateList.isEmpty()) {
            repository.bulkInsert(queryService.get(StringConstants.UPDATE_PRODUCT_BATCH_LATEST), updateList);
        }
    }

    /**
     * Method to save dashboard widget in DB.
     * @param list app user list
     */
    private void createDashboardWidget(final List<DashboardEntity> list) {
        repository.bulkInsert(queryService.get(StringConstants.INSERT_MASTER_DASHBOARDENTITY), list);
    }


    /**
     * Method to save distributor stock into DB.
     * @param list customer stock
     */
    private void saveDistributorStock(final List<DistributorStockEntity> list) {
        repository.bulkInsert(queryService.get(StringConstants.INSERT_MASTER_DISTRIBUTORSTOCKENTITY), list);
    }

    /**
     * Method to save customer stock into DB.
     * @param list customer stock
     */
    private void saveCustomerStock(final List<CustomerStockEntity> list) {
        repository.bulkInsert(queryService.get(StringConstants.INSERT_MASTER_CUSTOMERSTOCKENTITY), list);
    }

}
