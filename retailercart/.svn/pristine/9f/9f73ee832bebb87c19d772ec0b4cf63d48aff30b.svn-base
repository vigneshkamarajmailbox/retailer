package com.botree.sfaweb.service;

import com.botree.common.constants.StringConstants;
import com.botree.common.model.UploadModel;
import com.botree.common.service.IQueryService;
import com.botree.common.util.Function;
import com.botree.interdbentity.model.FavoriteProductEntity;
import com.botree.interdbentity.model.FavoriteProductStatusModel;
import com.botree.interdbentity.model.FeedBackEntity;
import com.botree.interdbentity.model.FeedBackStatusModel;
import com.botree.interdbentity.model.KeyGeneratorEntity;
import com.botree.interdbentity.model.KeyGeneratorStatusModel;
import com.botree.interdbentity.model.LocationUpdateEntity;
import com.botree.interdbentity.model.LocationUpdateStatusModel;
import com.botree.interdbentity.model.OrderBookingHeaderEntity;
import com.botree.interdbentity.model.OrderBookingStatusModel;
import com.botree.interdbentity.model.SyncLogEntity;
import com.botree.interdbentity.model.UploadStatus;
import com.botree.sfadbrepository.dao.DAORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Class contains the customer related transactions.
 * @author vinodkumara
 */
@Component
@Transactional
public class CustomerService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    /** queryService. */
    private final IQueryService queryService;

    /** repository. */
    private final DAORepository repository;

    /**
     * Constructor Method.
     * @param queryServiceIn queryServiceIn
     * @param repositoryIn   repositoryIn
     */
    public CustomerService(final IQueryService queryServiceIn, final DAORepository repositoryIn) {
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
    }

    /**
     * Method to save or update order info.
     * @param uploadModel uploadModel
     * @param status      status
     */
    @Transactional
    public void saveOrderInfo(final UploadModel uploadModel, final UploadStatus status) {
        if (uploadModel.getOrderBookingHeader() != null && !uploadModel.getOrderBookingHeader().isEmpty()) {
            try {
                status.setOrderBookingStatusList(saveOrder(
                        Function.decompress(uploadModel.getOrderBookingHeader(), OrderBookingHeaderEntity.class,
                                Boolean.TRUE)));
            } catch (Exception e) {
                LOG.error("save order method exception :: ", e);
                status.setOrderBookingStatusList(new ArrayList<>());
            }
        }
    }

    /**
     * Method to save or update order info.
     * @param orderBookingHeaderList orderBookingHeaderList
     * @return order status
     */
    private List<OrderBookingStatusModel> saveOrder(final List<OrderBookingHeaderEntity> orderBookingHeaderList) {
        var statusList = new ArrayList<OrderBookingStatusModel>();
        var headerList = new ArrayList<>();
        var detailList = new ArrayList<>();
        var serviceDetailList = new ArrayList<>();
        var schemeDetailList = new ArrayList<>();
        var schemeProdRuleList = new ArrayList<>();
        orderBookingHeaderList.forEach(model -> {
            var status = new OrderBookingStatusModel();
            status.setCmpCode(model.getCmpCode());
            status.setDistrCode(model.getDistrCode());
            status.setOrderNo(model.getOrderNo());
            status.setUploadStatus(Boolean.TRUE);
            headerList.add(model);
            detailList.addAll(model.getOrderBookingDetailsList());
            serviceDetailList.addAll(model.getOrderBookingServiceDetailsList());
            schemeDetailList.addAll(model.getOrderBookingSchemeDetailsList());
            schemeProdRuleList.addAll(model.getOrderBookingSchemeProductRuleList());
            statusList.add(status);
        });
        repository.bulkInsert(queryService.get(StringConstants.INSERT_ORDERBOOKINGHEADERENTITY), headerList);
        repository.bulkInsert(queryService.get(StringConstants.INSERT_ORDERBOOKINGDETAILSENTITY), detailList);
        repository.bulkInsert(queryService.get(StringConstants.INSERT_ORDERBOOKINGSERVICEDETAILSENTITY),
                serviceDetailList);
        repository.bulkInsert(queryService.get(StringConstants.INSERT_ORDERBOOKINGSCHEMEDETAILSENTITY),
                schemeDetailList);
        repository.bulkInsert(queryService.get(StringConstants.INSERT_ORDERBOOKINGSCHEMEPRODUCTRULEENTITY),
                schemeProdRuleList);
        return statusList;

    }

    /**
     * Method to save or update key generator info.
     * @param uploadModel uploadModel
     * @param status      status
     */
    @Transactional
    public void saveKeyGeneratorInfo(final UploadModel uploadModel, final UploadStatus status) {
        if (uploadModel.getKeyGenerator() != null && !uploadModel.getKeyGenerator().isEmpty()) {
            try {
                status.setKeyGeneratorStatusList(saveKeyGenerator(
                        Function.decompress(uploadModel.getKeyGenerator(), KeyGeneratorEntity.class, Boolean.TRUE)));
            } catch (Exception e) {
                LOG.error("save key generator method exception :: ", e);
                status.setKeyGeneratorStatusList(new ArrayList<>());
            }
        }
    }

    /**
     * Method to save or update key generator.
     * @param keyGeneratorList keyGeneratorList
     * @return key generator status list
     */
    private List<KeyGeneratorStatusModel> saveKeyGenerator(final List<KeyGeneratorEntity> keyGeneratorList) {
        var statusList = new ArrayList<KeyGeneratorStatusModel>();
        var finalList = new ArrayList<>();
        keyGeneratorList.forEach(model -> {
            var status = new KeyGeneratorStatusModel();
            status.setCmpCode(model.getCmpCode());
            status.setLoginCode(model.getLoginCode());
            status.setScreenName(model.getScreenName());
            status.setUploadStatus(Boolean.TRUE);
            finalList.add(model);
            statusList.add(status);
        });
        repository.bulkInsert(queryService.get(StringConstants.INSERT_KEYGENERATORENTITY), finalList);
        return statusList;
    }

    /**
     * Method to save synclog info.
     * @param uploadModel uploadModel
     * @param status      status
     */
    @Transactional
    public void saveSyncLogInfo(final UploadModel uploadModel, final UploadStatus status) {
        if (uploadModel.getSyncLog() != null && !uploadModel.getSyncLog().isEmpty()) {
            status.setSyncLogEntityStatusList(saveSyncLog(Function.decompress(uploadModel.getSyncLog(),
                    SyncLogEntity.class, Boolean.TRUE)));
        }
    }

    /**
     * Method to save sync log info.
     * @param syncLogList syncLogList
     * @return syncLog status
     */
    private List<SyncLogEntity> saveSyncLog(final List<SyncLogEntity> syncLogList) {
        try {
            repository.bulkInsert(queryService.get(StringConstants.INSERT_SYNCLOGENTITY), syncLogList);
        } catch (Exception e) {
            LOG.error("save sync Log method exception :: ", e);
        }
        return syncLogList;
    }

    /**
     * Save customer feedback and store visibility.
     * @param uploadModel uploadModel
     * @param status      status
     * @param imagePath   imagePath
     */
    @Transactional
    public void saveFeedbackInfo(final UploadModel uploadModel, final UploadStatus status,
                                 final String imagePath) {
        if (uploadModel.getFeedBack() != null && !uploadModel.getFeedBack().isEmpty()) {
            status.setFeedBackStatusList(saveFeedback(
                    Function.decompress(uploadModel.getFeedBack(), FeedBackEntity.class, Boolean.TRUE), imagePath));
        }
    }

    /**
     * Save customer feedback and store visibility.
     * @param feedBackList feedBackList
     * @param imagePath    imagePath
     * @return status
     */
    private List<FeedBackStatusModel> saveFeedback(final List<FeedBackEntity> feedBackList, final String imagePath) {
        var statusList = new ArrayList<FeedBackStatusModel>();
        var finalList = new ArrayList<>();
        try {
            feedBackList.forEach(model -> {
                var status = new FeedBackStatusModel();
                status.setCmpCode(model.getCmpCode());
                status.setDistrCode(model.getDistrCode());
                status.setFeedbackNo(model.getFeedbackNo());
                status.setUploadStatus(Boolean.TRUE);
                if (model.getImage() != null && !model.getImage().isEmpty()) {
                    writeFeedBackImage(model, imagePath);
                }
                finalList.add(model);
                statusList.add(status);
            });
            repository.bulkInsert(queryService.get(StringConstants.INSERT_FEEDBACKENTITY), finalList);
            return statusList;
        } catch (Exception e) {
            LOG.error("save feedback method exception :: ", e);
            return new ArrayList<>();
        }
    }

    /**
     * Method to save feedback image.
     * @param model     model
     * @param imagePath imagePath
     */
    private void writeFeedBackImage(final FeedBackEntity model, final String imagePath) {
        model.setImagePath(Function.writeImage(model.getImage(), imagePath, StringConstants.FEEDBACK_IMAGES,
                model.getCmpCode() + StringConstants.CONST_UNDER_SCORE + model.getDistrCode()
                        + StringConstants.CONST_UNDER_SCORE + System.nanoTime()));
    }

    /**
     * Method to save location update.
     * @param uploadModel uploadModel
     * @param status      status
     * @param imagePath   imagePath
     */
    public void saveLocationUpdateInfo(final UploadModel uploadModel, final UploadStatus status,
                                       final String imagePath) {
        if (uploadModel.getLocationUpdate() != null && !uploadModel.getLocationUpdate().isEmpty()) {
            status.setLocationUpdateStatusList(saveLocationUpdate(
                    Function.decompress(uploadModel.getLocationUpdate(), LocationUpdateEntity.class, Boolean.TRUE),
                    imagePath));
        }
    }

    /**
     * Method to save the location update.
     * @param locationUpdateList locationUpdateList
     * @param imagePath          imagePath
     * @return status
     */
    @Transactional
    public List<LocationUpdateStatusModel> saveLocationUpdate(
            final List<LocationUpdateEntity> locationUpdateList, final String imagePath) {
        var statusList = new ArrayList<LocationUpdateStatusModel>();
        var finalList = new ArrayList<>();
        var finalUploadStatusList = new ArrayList<LocationUpdateEntity>();
        try {
            locationUpdateList.forEach(model -> {
                convertLocationUploadStatus(finalUploadStatusList, model);
                var status = new LocationUpdateStatusModel();
                status.setLoginCode(model.getLoginCode());
                status.setUpdateDt(model.getUpdateDt());
                status.setUploadStatus(Boolean.TRUE);
                if (model.getImage() != null && !model.getImage().isEmpty()) {
                    writeLocationUpdateImage(model, imagePath);
                }
                finalList.add(model);
                statusList.add(status);
            });
            repository.bulkInsert(queryService.get(StringConstants.INSERT_LOCATION_UPDATE_ENTITY), finalList);
            repository.bulkInsert(queryService.get(StringConstants.INSERT_LOCATION_UPDATE_STATUS_ENTITY),
                    finalUploadStatusList);
            return statusList;
        } catch (Exception e) {
            LOG.error("save location update method exception :: ", e);
            return new ArrayList<>();
        }
    }

    /**
     * Method to convert location update status.
     * @param finalUploadStatusList finalUploadStatusList
     * @param model                 model
     */
    private void convertLocationUploadStatus(final List<LocationUpdateEntity> finalUploadStatusList,
                                             final LocationUpdateEntity model) {
        for (var mappedUser : model.getUploadTo().split(StringConstants.CONST_COMMA)) {
            var arr = mappedUser.split(StringConstants.CONST_TILDE);
            if (arr.length > 1) {
                var locationUpdateStatus = new LocationUpdateEntity();
                locationUpdateStatus.setCmpCode(arr[0]);
                locationUpdateStatus.setDistrCode(arr[1]);
                locationUpdateStatus.setLoginCode(model.getLoginCode());
                locationUpdateStatus.setUpdateDt(model.getUpdateDt());
                locationUpdateStatus.setUploadFlag(model.getUploadFlag());
                locationUpdateStatus.setModDt(model.getModDt());
                finalUploadStatusList.add(locationUpdateStatus);
            }
        }
    }

    /**
     * Method to save location update image.
     * @param model     model
     * @param imagePath imagePath
     */
    private void writeLocationUpdateImage(final LocationUpdateEntity model, final String imagePath) {
        model.setImagePath(Function.writeImage(model.getImage(), imagePath, StringConstants.LOCATION_UPDATE_IMAGES,
                model.getLoginCode() + StringConstants.CONST_UNDER_SCORE + System.nanoTime()));
    }

    /**
     * Method to save all customer related transactions.
     * @param uploadModel uploadModel
     * @param status      status
     */
    @Transactional
    public void saveCustomerTransaction(final UploadModel uploadModel, final UploadStatus status) {
        if (uploadModel.getOrderBookingHeaderList() != null && !uploadModel.getOrderBookingHeaderList().isEmpty()) {
            status.setOrderBookingStatusList(saveOrder(uploadModel.getOrderBookingHeaderList()));
        }

        if (uploadModel.getKeyGeneratorList() != null && !uploadModel.getKeyGeneratorList().isEmpty()) {
            status.setKeyGeneratorStatusList(saveKeyGenerator(uploadModel.getKeyGeneratorList()));
        }

        if (uploadModel.getFavoriteProductList() != null && !uploadModel.getFavoriteProductList().isEmpty()) {
            status.setFavoriteProductStatusList(saveFavoriteProduct(uploadModel.getFavoriteProductList()));
        }

        if (uploadModel.getSyncLogList() != null && !uploadModel.getSyncLogList().isEmpty()) {
            status.setSyncLogEntityStatusList(saveSyncLog(uploadModel.getSyncLogList()));
        }
    }

    /**
     * Method to save or update favorite products.
     * @param favoriteProductList favoriteProductList
     * @return favorite products
     */
    private List<FavoriteProductStatusModel> saveFavoriteProduct(
            final List<FavoriteProductEntity> favoriteProductList) {
        var statusList = new ArrayList<FavoriteProductStatusModel>();
        var finalList = new ArrayList<>();
        favoriteProductList.forEach(model -> {
            var status = new FavoriteProductStatusModel();
            status.setCmpCode(model.getCmpCode());
            status.setDistrCode(model.getDistrCode());
            status.setCustomerCode(model.getCustomerCode());
            status.setUploadStatus(Boolean.TRUE);
            finalList.add(model);
            statusList.add(status);
        });
        repository.bulkInsert(queryService.get(StringConstants.INSERT_FAVORITE_PRODUCT_ENTITY), finalList);
        return statusList;
    }
}
