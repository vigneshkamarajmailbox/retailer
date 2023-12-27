package com.botree.sfaweb.service;

import com.botree.common.constants.StringConstants;
import com.botree.common.dto.CompanyUserDTO;
import com.botree.common.model.DashboardModel;
import com.botree.common.model.DownloadModel;
import com.botree.common.model.WidgetModel;
import com.botree.common.service.IQueryService;
import com.botree.common.util.Function;
import com.botree.interdbentity.model.ActionOrderStatusEntity;
import com.botree.interdbentity.model.AppUserModel;
import com.botree.interdbentity.model.ApplicationUpdateEntity;
import com.botree.interdbentity.model.BannerImageTemplateEntity;
import com.botree.interdbentity.model.CompanyEntity;
import com.botree.interdbentity.model.ConfigurationEntity;
import com.botree.interdbentity.model.CustomerEntity;
import com.botree.interdbentity.model.CustomerSalesmanMappingModel;
import com.botree.interdbentity.model.CustomerShipAddressEntity;
import com.botree.interdbentity.model.CustomerStockEntity;
import com.botree.interdbentity.model.DistributorEntity;
import com.botree.interdbentity.model.DistributorLOBMappingEntity;
import com.botree.interdbentity.model.DistributorStockEntity;
import com.botree.interdbentity.model.FavoriteProductEntity;
import com.botree.interdbentity.model.FeedBackMasterEntity;
import com.botree.interdbentity.model.GSTStateMasterEntity;
import com.botree.interdbentity.model.KeyGeneratorEntity;
import com.botree.interdbentity.model.LOBMasterEntity;
import com.botree.interdbentity.model.NotificationEntity;
import com.botree.interdbentity.model.NotificationTypeMasterEntity;
import com.botree.interdbentity.model.OrderBookingDetailsEntity;
import com.botree.interdbentity.model.OrderBookingHeaderEntity;
import com.botree.interdbentity.model.ProductBatchEntity;
import com.botree.interdbentity.model.ProductEntity;
import com.botree.interdbentity.model.ProductHierLevelEntity;
import com.botree.interdbentity.model.ProductHierValueEntity;
import com.botree.interdbentity.model.ProductUomEntity;
import com.botree.interdbentity.model.RecommendedProductEntity;
import com.botree.interdbentity.model.TaxStructureEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.botree.common.constants.StringConstants.DISPLAY_COLOR;

/**
 * Class contains the master related activities.
 * @author vinodkumara
 */
@Component
public class MasterService {

    /** imageLocalFilePath. */
    @Value("${image.path}")
    private String imageLocalFilePath;

    /** s3BucketEnable. */
    @Value("${aws.s3bucket.enable}")
    private Boolean s3BucketEnable;

    /** queryService. */
    private final IQueryService queryService;

    /** repository. */
    private final DAORepository repository;

    /** bucketService. */
    private final S3BucketService bucketService;

    /** applicationUpdate. */
    private ApplicationUpdateEntity applicationUpdate;

    /**
     * Constructor Method.
     * @param queryServiceIn  queryServiceIn
     * @param repositoryIn    repositoryIn
     * @param bucketServiceIn bucketServiceIn
     */
    public MasterService(final IQueryService queryServiceIn, final DAORepository repositoryIn,
                         final S3BucketService bucketServiceIn) {
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
        this.bucketService = bucketServiceIn;
    }

    /**
     * Method to fetch the configuration and store it in key value pairs.
     */
    @PostConstruct
    void init() {
        applicationUpdate = repository.queryForList(queryService.get(StringConstants.FETCH_APPVERSION),
                new HashMap<>(), ApplicationUpdateEntity.class).get(0);
    }

    /**
     * Method to fetch the download data for the user.
     * @param user user
     * @return download model
     */
    public DownloadModel download(final AppUserModel user) {
        var model = new DownloadModel();
        validateUserActiveStatus(user, model);
        return model;
    }


    /**
     * Method to validate user active status.
     * @param user  user
     * @param model model
     */
    private void validateUserActiveStatus(final AppUserModel user, final DownloadModel model) {
        if (LocalDate.now().isEqual(user.getSystemDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
            model.setSystemDateAuthFlag(Boolean.TRUE);
            if (repository.queryForObject(queryService.get(StringConstants.FETCH_USER_STATUS), Integer.class,
                    user.getLoginCode()) > 0) {
                if (user.getAppVersion().equalsIgnoreCase(applicationUpdate.getAppVersion())) {
                    fetchData(user, model);
                } else {
                    model.setNewAppDownloadURL(applicationUpdate.getUrl() + applicationUpdate.getFileName());
                    model.setNewAppVersion(applicationUpdate.getAppVersion());
                }
                model.setAuthFlag(Boolean.TRUE);
            } else {
                model.setAuthFlag(Boolean.FALSE);
            }
        } else {
            model.setSystemDateAuthFlag(Boolean.FALSE);
        }
    }

    /**
     * Method to fetch the download data for the user.
     * @param user user
     * @return download model
     */
    public DownloadModel downloadMaster(final AppUserModel user) {
        var model = new DownloadModel();
        if (repository.queryForObject(queryService.get(StringConstants.FETCH_USER_STATUS), Integer.class,
                user.getLoginCode()) > 0) {
            fetchOrderBookingMaster(user, model);
            model.setAuthFlag(Boolean.TRUE);
        } else {
            model.setAuthFlag(Boolean.FALSE);
        }
        return model;
    }

    /**
     * Method to fetch the data for distributor user.
     * @param user  user
     * @param model model
     */
    private void fetchData(final AppUserModel user, final DownloadModel model) {
        setUserMapping(user, model);
        if (model.getMappedCompany() != null && !model.getMappedCompany().isEmpty()) {
            setCompany(user, model);
            setConfiguration(user, model);
            setKeyGenerator(user, model);
            setGSTStateMaster(user, model);
            setLOBMaster(user, model);
            setDistributor(user, model);
            setDistributorLOBMapping(user, model);
            setCustomer(user, model);
            setCustomerShipAddress(user, model);
            setProductHierLevel(user, model);
            setProductHierValue(user, model);
            setProduct(user, model);
            setProductUom(user, model);
            setTaxStructure(user, model);
            setProductBatch(user, model);
            setFeedBackMaster(user, model);
            setNotificationTypeMaster(user, model);
            setPreviousOrderDetail(user, model);
            setDistributorStock(user, model);
            setCustomerStock(user, model);
            setCategorySequence(user, model);
        }
    }

    /**
     * Method to fetch the data for distributor user to load product in order booking screen.
     * @param user  user
     * @param model model
     */
    private void fetchOrderBookingMaster(final AppUserModel user, final DownloadModel model) {
        setUserMapping(user, model);
        if (model.getMappedCompany() != null && !model.getMappedCompany().isEmpty()) {
            setConfiguration(user, model);
            setKeyGenerator(user, model);
            setGSTStateMaster(user, model);
            setLOBMaster(user, model);
            setDistributor(user, model);
            setDistributorLOBMapping(user, model);
            setDistributorStock(user, model);
            setCustomer(user, model);
            setCustomerShipAddress(user, model);
            setCustomerStock(user, model);
            setProductHierLevel(user, model);
            setProductHierValue(user, model);
            setProduct(user, model);
            setProductUom(user, model);
            setProductBatch(user, model);
            setTaxStructure(user, model);
            setRecommendedProducts(user, model);
            setFavoriteProducts(user, model);
            setCategorySequence(user, model);
        }
    }

    /**
     * Method to set user mapping.
     * @param user  user
     * @param model model
     */
    public void setUserMapping(final AppUserModel user, final DownloadModel model) {
        var mappingList = repository.queryForList(
                queryService.get(StringConstants.FETCH_USER_MAPPING), user.getLoginCode());
        if (!mappingList.isEmpty() && mappingList.get(0).get(StringConstants.MAPPED_COMPANY) != null) {
            model.setMappedCompany(Arrays.asList(String.valueOf(mappingList.get(0).get(StringConstants.MAPPED_COMPANY))
                    .split(StringConstants.CONST_COMMA)));
            model.setMappedUser(Arrays.asList(String.valueOf(mappingList.get(0).get(StringConstants.MAPPED_USER))
                    .split(StringConstants.CONST_COMMA)));
            model.setMappedCustomer(Arrays.asList(String.valueOf(
                    mappingList.get(0).get(StringConstants.MAPPED_CUSTOMER)).split(StringConstants.CONST_COMMA)));
            model.setMappedDistributor(Arrays.asList(String.valueOf(
                    mappingList.get(0).get(StringConstants.MAPPED_DISTRIBUTOR)).split(StringConstants.CONST_COMMA)));
            model.setMappedCustomerCode(Arrays.asList(String.valueOf(
                    mappingList.get(0).get(StringConstants.MAPPED_CUSTOMER_CODE)).split(StringConstants.CONST_COMMA)));
        }
    }

    /**
     * Method to fetch company data.
     * @param user  user
     * @param model downloadModel
     */
    private void setCompany(final AppUserModel user, final DownloadModel model) {
        model.setCompany(Function.compress(repository.queryForList(
                        queryService.get(StringConstants.FETCH_COMPANY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, user.getLanguageCode()), CompanyEntity.class),
                user.isEnableCompress()));
    }

    /**
     * Method to fetch configuration data.
     * @param user  user
     * @param model downloadModel
     */
    private void setConfiguration(final AppUserModel user, final DownloadModel model) {
        model.setConfiguration(Function.compress(repository.queryForListWithRowMapper(
                        queryService.get(StringConstants.FETCH_CONFIGURATION), ConfigurationEntity.class),
                user.isEnableCompress()));
    }

    /**
     * Method to fetch key generator data.
     * @param user  user
     * @param model downloadModel
     */
    private void setKeyGenerator(final AppUserModel user, final DownloadModel model) {
        model.setKeyGenerator(Function.compress(
                repository.queryForList(queryService.get(StringConstants.FETCH_KEYGENERATOR),
                        Map.of(StringConstants.CONST_PARAM_1, user.getLoginCode()), KeyGeneratorEntity.class),
                user.isEnableCompress()));
    }

    /**
     * Method to fetch GST State Master data.
     * @param user  user
     * @param model model
     */
    private void setGSTStateMaster(final AppUserModel user, final DownloadModel model) {
        model.setGstStateMaster(Function.compress(repository.queryForList(
                        queryService.get(StringConstants.FETCH_GSTSTATEMASTERENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, user.getLanguageCode()), GSTStateMasterEntity.class),
                user.isEnableCompress()));
    }

    /**
     * Method to fetch LOB Master data.
     * @param user  user
     * @param model model
     */
    private void setLOBMaster(final AppUserModel user, final DownloadModel model) {
        model.setLobMaster(Function.compress(repository.queryForList(
                        queryService.get(StringConstants.FETCH_LOBMASTERENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, user.getLanguageCode()), LOBMasterEntity.class),
                user.isEnableCompress()));
    }

    /**
     * Method to fetch distributor data.
     * @param user  user
     * @param model downloadModel
     */
    private void setDistributor(final AppUserModel user, final DownloadModel model) {
        model.setDistributor(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_DISTRIBUTORENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, user.getLanguageCode()),
                        DistributorEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch distributor lob mapping data.
     * @param user  user
     * @param model downloadModel
     */
    private void setDistributorLOBMapping(final AppUserModel user, final DownloadModel model) {
        model.setDistributorLOBMapping(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_DISTRIBUTORLOBMAPPINGENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor()),
                        DistributorLOBMappingEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch the customer data.
     * @param user  user
     * @param model model
     */
    private void setCustomer(final AppUserModel user, final DownloadModel model) {
        model.setCustomer(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_CUSTOMERENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, model.getMappedCustomerCode(),
                                StringConstants.CONST_PARAM_4, user.getLanguageCode()),
                        CustomerEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch the customer ship address data.
     * @param user  user
     * @param model model
     */
    private void setCustomerShipAddress(final AppUserModel user, final DownloadModel model) {
        model.setCustomerShipAddress(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_CUSTOMERSHIPADDRESSENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, model.getMappedCustomerCode(),
                                StringConstants.CONST_PARAM_4, user.getLanguageCode()),
                        CustomerShipAddressEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch the product hier level data.
     * @param user  user
     * @param model downloadModel
     */
    private void setProductHierLevel(final AppUserModel user, final DownloadModel model) {
        model.setProductHierLevel(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_PRODUCTHIERLEVELENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, user.getLanguageCode()),
                        ProductHierLevelEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch the product hier value data.
     * @param user  user
     * @param model downloadModel
     */
    private void setProductHierValue(final AppUserModel user, final DownloadModel model) {
        model.setProductHierValue(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_PRODUCTHIERVALUEENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, user.getLanguageCode()),
                        ProductHierValueEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch the product data.
     * @param user  user
     * @param model downloadModel
     */
    private void setProduct(final AppUserModel user, final DownloadModel model) {
        model.setProduct(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_PRODUCTENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, user.getLanguageCode()),
                        ProductEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch product uom list.
     * @param user  user
     * @param model downloadModel
     */
    private void setProductUom(final AppUserModel user, final DownloadModel model) {
        model.setProductUom(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_PRODUCTUOMENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor()),
                        ProductUomEntity.class), user.isEnableCompress()));
    }


    /**
     * Method to fetch tax structure list.
     * @param user  user
     * @param model downloadModel
     */
    private void setTaxStructure(final AppUserModel user, final DownloadModel model) {
        model.setTaxStructure(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_TAXSTRUCTUREENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor()),
                        TaxStructureEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch product batch list.
     * @param user  user
     * @param model downloadModel
     */
    private void setProductBatch(final AppUserModel user, final DownloadModel model) {
        model.setProductBatch(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_PRODUCTBATCHENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor()),
                        ProductBatchEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch feedback master list.
     * @param user  user
     * @param model downloadModel
     */
    private void setFeedBackMaster(final AppUserModel user, final DownloadModel model) {
        model.setFeedbackMaster(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_FEEDBACKMASTER),
                        Map.of(StringConstants.CONST_PARAM_1, user.getLanguageCode()),
                        FeedBackMasterEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch notification type master list.
     * @param user  user
     * @param model downloadModel
     */
    private void setNotificationTypeMaster(final AppUserModel user, final DownloadModel model) {
        model.setNotificationTypeMaster(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_NOTIFICATIONTYPE_MASTER),
                        Map.of(StringConstants.CONST_PARAM_1, user.getLanguageCode()),
                        NotificationTypeMasterEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch previous order detail.
     * @param user  user
     * @param model downloadModel
     */
    private void setPreviousOrderDetail(final AppUserModel user, final DownloadModel model) {
        var orderHeaderList = repository.queryForList(queryService.get(
                        StringConstants.FETCH_ORDERBOOKINGHEADERENTITY_FOR_REPORT),
                Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                        StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                        StringConstants.CONST_PARAM_3, model.getMappedCustomerCode()), OrderBookingHeaderEntity.class);
        var orderDetailMap = repository.queryForList(queryService.get(
                                StringConstants.FETCH_ORDERBOOKINGDETAILSENTITY_FOR_REPORT),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, model.getMappedCustomerCode()),
                        OrderBookingDetailsEntity.class)
                .stream().collect(Collectors.groupingBy(OrderBookingDetailsEntity::orderKey));
        var actionMap = repository.queryForList(queryService.get(
                                StringConstants.FETCH_ACTIONORDERSTATUSENTITY_FOR_REPORT),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, model.getMappedCustomerCode()),
                        ActionOrderStatusEntity.class)
                .stream().collect(Collectors.groupingBy(ActionOrderStatusEntity::orderKey));
        List<OrderBookingHeaderEntity> finalList = new ArrayList<>();
        orderHeaderList.forEach(data -> {
            if (actionMap.containsKey(data.orderKey())) {
                data.setActionOrderStatusList(actionMap.get(data.orderKey()));
            }
            if (orderDetailMap.containsKey(data.orderKey())) {
                data.setOrderBookingDetailsList(orderDetailMap.get(data.orderKey()));
                finalList.add(data);
            }
        });
        model.setPreviousOrderDetail(Function.compress(finalList, user.isEnableCompress()));
    }

    /**
     * Method to fetch distributor stock.
     * @param user  user
     * @param model model
     */
    private void setDistributorStock(final AppUserModel user, final DownloadModel model) {
        var finalList = new ArrayList<>();
        repository.queryForList(queryService.get(StringConstants.FETCH_DISTRIBUTORSTOCKENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor()),
                        DistributorStockEntity.class)
                .forEach(stock -> {
                    for (var s : stock.getSaleableStock().split(StringConstants.CONST_COMMA)) {
                        var arr = s.split(StringConstants.CONST_REGEX_CAP);
                        if (arr.length == StringConstants.CONST_INT_2) {
                            var entity = new DistributorStockEntity();
                            entity.setCmpCode(stock.getCmpCode());
                            entity.setDistrCode(stock.getDistrCode());
                            entity.setProdCode(arr[0].trim());
                            entity.setSaleableQty(Integer.valueOf(arr[1].trim()));
                            finalList.add(entity);
                        }
                    }
                });
        model.setDistributorStock(Function.compress(finalList, user.isEnableCompress()));
    }

    /**
     * Method to fetch customer stock.
     * @param user  user
     * @param model model
     */
    private void setCustomerStock(final AppUserModel user, final DownloadModel model) {
        var finalList = new ArrayList<>();
        repository.queryForList(queryService.get(StringConstants.FETCH_CUSTOMERSTOCKENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, model.getMappedCustomerCode()),
                        CustomerStockEntity.class)
                .forEach(stock -> {
                    for (var s : stock.getStock().split(StringConstants.CONST_COMMA)) {
                        var stockArr = s.split(StringConstants.CONST_REGEX_CAP);
                        var entity = new CustomerStockEntity();
                        entity.setCmpCode(stock.getCmpCode());
                        entity.setDistrCode(stock.getDistrCode());
                        entity.setCustomerCode(stock.getCustomerCode());
                        entity.setProdCode(stockArr[0].trim());
                        if (StringConstants.CONST_INT_2 <= stockArr.length) {
                            entity.setSoq(Function.checkInteger(stockArr[StringConstants.CONST_INT_1].trim()));
                        }
                        if (StringConstants.CONST_INT_3 <= stockArr.length) {
                            entity.setPpq(stockArr[StringConstants.CONST_INT_2].trim());
                        }
                        if (StringConstants.CONST_INT_4 <= stockArr.length) {
                            entity.setEnableProduct(stockArr[StringConstants.CONST_INT_3].trim());
                        }
                        if (StringConstants.CONST_INT_5 <= stockArr.length) {
                            entity.setProductColor(stockArr[StringConstants.CONST_INT_4].trim());
                        }
                        finalList.add(entity);
                    }
                });
        model.setCustomerStock(Function.compress(finalList, user.isEnableCompress()));
    }

    /**
     * Method to fetch category sequence.
     * @param user  user
     * @param model model
     */
    private void setCategorySequence(final AppUserModel user, final DownloadModel model) {
        model.setCategorySequence(Function.compress(
                repository.queryForList(queryService.get(StringConstants.FETCH_CATEGORY_SEQUENCE)),
                user.isEnableCompress()));
    }

    /**
     * Method to fetch screen related data.
     * @param user user
     * @return downloadModel
     */
    public DownloadModel fetchScreenAccess(final AppUserModel user) {
        var downloadModel = new DownloadModel();
        downloadModel.setScreenAccess(Function.compress(
                repository.queryForList(queryService.get(StringConstants.FETCH_SCREENACCESS),
                        Map.of(StringConstants.CONST_PARAM_1, user.getLoginCode())), user.isEnableCompress()));
        return downloadModel;
    }

    /**
     * Method to fetch image.
     * @param id file name
     * @return uri
     */
    public URI getProductImage(final String id) {
        return getFile(StringConstants.PRODUCT_IMAGES, id, Boolean.TRUE);
    }

    /**
     * Method to fetch notification file.
     * @param id   file name
     * @param type type
     * @return uri
     */
    public URI getNotificationFile(final String id, final String type) {
        if (Boolean.TRUE.equals(s3BucketEnable)) {
            var fileName = id + StringConstants.CONST_DOT + type;
            return bucketService.readImageFromS3(StringConstants.NOTIFICATION_PATH, fileName);
        } else {
            return new File(imageLocalFilePath + File.separatorChar
                    + StringConstants.NOTIFICATION_PATH + File.separatorChar + id + StringConstants.CONST_DOT + type)
                    .toURI();
        }
    }

    /**
     * Method to fetch company image.
     * @param id file name
     * @return uri
     */
    public URI getCompanyImage(final String id) {
        return getFile(StringConstants.COMPANY_IMAGES, id, Boolean.FALSE);
    }

    /**
     * Method to fetch banner image.
     * @param id file name
     * @return uri
     */
    public URI getBannerImage(final String id) {
        return getFile(StringConstants.BANNER_IMAGES, id, Boolean.FALSE);
    }

    /**
     * Method to search and return file without extension.
     * @param processName processName
     * @param fileName    fileName
     * @param isProduct   isProduct
     * @return file
     */
    private URI getFile(final String processName, final String fileName, final boolean isProduct) {
        if (Boolean.TRUE.equals(s3BucketEnable)) {
            return fetchS3Storage(processName, fileName, isProduct);
        } else {
            return fetchLocalStorage(processName, fileName, isProduct);
        }
    }

    /**
     * Method to fetch file from s3 storage.
     * @param processName processName
     * @param fileName    fileName
     * @param isProduct   isProduct
     * @return file
     */
    private URI fetchS3Storage(final String processName, final String fileName, final boolean isProduct) {
        URI uri;
        var webpImage = fileName + StringConstants.EXT_WEBP;
        uri = bucketService.readImageFromS3(processName, webpImage);

        if (uri == null) {
            var jpegImage = fileName + StringConstants.EXT_JPEG;
            uri = bucketService.readImageFromS3(processName, jpegImage);
        }

        if (uri == null) {
            var jpgImage = fileName + StringConstants.EXT_JPG;
            uri = bucketService.readImageFromS3(processName, jpgImage);
        }

        if (uri == null) {
            var pngImage = fileName + StringConstants.EXT_PNG;
            uri = bucketService.readImageFromS3(processName, pngImage);
        }
        if (uri != null) {
            return uri;
        } else if (isProduct) {
            return bucketService.readImageFromS3(StringConstants.COMPANY_IMAGES, StringConstants.DEFAULT_IMAGE);
        } else {
            return null;
        }
    }

    /**
     * Method to fetch file from local storage.
     * @param processName processName
     * @param fileName    fileName
     * @param isProduct   isProduct
     * @return file
     */
    private URI fetchLocalStorage(final String processName, final String fileName, final boolean isProduct) {
        var path = imageLocalFilePath + File.separator + processName + File.separator;
        var webpImage = new File(path + fileName + StringConstants.EXT_WEBP);
        if (webpImage.exists()) {
            return webpImage.toURI();
        }
        var jpegImage = new File(path + fileName + StringConstants.EXT_JPEG);
        if (jpegImage.exists()) {
            return jpegImage.toURI();
        }
        var jpgImage = new File(path + fileName + StringConstants.EXT_JPG);
        if (jpgImage.exists()) {
            return jpgImage.toURI();
        }
        var pngImage = new File(path + fileName + StringConstants.EXT_PNG);
        if (pngImage.exists()) {
            return pngImage.toURI();
        }
        if (isProduct) {
            return new File(imageLocalFilePath + File.separator
                    + StringConstants.COMPANY_IMAGES + File.separator + StringConstants.DEFAULT_IMAGE).toURI();
        } else {
            return null;
        }
    }

    /**
     * Method to fetch notification related data.
     * @param user user
     * @return downloadModel
     */
    public DownloadModel fetchNotification(final AppUserModel user) {
        var model = new DownloadModel();
        setUserMapping(user, model);
        if (model.getMappedCompany() != null && !model.getMappedCompany().isEmpty()) {
            setNotificationTypeMaster(user, model);
            setNotification(user, model);
            setBannerImage(user, model);
        }
        return model;
    }

    /**
     * Method to set notification list.
     * @param user  user
     * @param model downloadModel
     */
    private void setNotification(final AppUserModel user, final DownloadModel model) {
        model.setNotification(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_NOTIFICATION_BY_USER),
                        Map.of(StringConstants.CONST_PARAM_1, user.getLoginCode()),
                        NotificationEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to set banner image template list.
     * @param user  user
     * @param model downloadModel
     */
    private void setBannerImage(final AppUserModel user, final DownloadModel model) {
        var finalList = new ArrayList<>();
        for (var str : model.getMappedUser()) {
            finalList.addAll(repository.queryForList(queryService.get(StringConstants.FETCH_BANNER_IMAGE_TEMPLATE),
                    Map.of(StringConstants.CONST_PARAM_1, str.split(StringConstants.CONST_TILDE)[0],
                            StringConstants.CONST_PARAM_2, str.split(StringConstants.CONST_TILDE)[1]),
                    BannerImageTemplateEntity.class));
        }
        model.setBannerImageTemplate(Function.compress(finalList, user.isEnableCompress()));
    }

    /**
     * Method to fetch previous order report.
     * @param user user
     * @return report data
     */
    public DownloadModel fetchPreviousOrderReport(final AppUserModel user) {
        var model = new DownloadModel();
        setUserMapping(user, model);
        if (model.getMappedCompany() != null && !model.getMappedCompany().isEmpty()) {
            setPreviousOrderDetail(user, model);
        }
        return model;
    }

    /**
     * Method to fetch distributor info.
     * @param user user
     * @return report data
     */
    public DownloadModel fetchDistributorInfo(final AppUserModel user) {
        var model = new DownloadModel();
        setUserMapping(user, model);
        if (model.getMappedCompany() != null && !model.getMappedCompany().isEmpty()) {
            setCompany(user, model);
            setGSTStateMaster(user, model);
            setLOBMaster(user, model);
            setDistributor(user, model);
            setDistributorLOBMapping(user, model);
        }
        return model;
    }

    /**
     * Method to fetch recommended products.
     * @param user  user
     * @param model model
     */
    private void setRecommendedProducts(final AppUserModel user, final DownloadModel model) {
        model.setRecommendedProducts(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_RECOMMENDED_PRODUCTS),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, model.getMappedCustomerCode()),
                        RecommendedProductEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to fetch favorite products.
     * @param user  user
     * @param model model
     */
    private void setFavoriteProducts(final AppUserModel user, final DownloadModel model) {
        model.setFavoriteProducts(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_FAVORITE_PRODUCTS),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, model.getMappedCustomerCode()),
                        FavoriteProductEntity.class), user.isEnableCompress()));
    }

    /**
     * Method to download banner video.
     * @param fileName fileName
     * @return video
     */
    public byte[] getBannerVideo(final String fileName) {
        if (Boolean.TRUE.equals(s3BucketEnable)) {
            return bucketService.downloadFileFromS3(StringConstants.BANNER_IMAGES, fileName);
        }
        return new byte[0];
    }

    /**
     * Method to fetch profile info.
     * @param user user
     * @return report data
     */
    public DownloadModel fetchProfileInfo(final AppUserModel user) {
        var model = new DownloadModel();
        setUserMapping(user, model);
        setSalesOfficer(user, model);
        if (model.getMappedCompany() != null && !model.getMappedCompany().isEmpty()) {
            setCustomer(user, model);
            setCustomerShipAddress(user, model);
            setCustomerSalesmanMapping(user, model);
        }
        return model;
    }

    /**
     * Method to fetch customer salesman mapping.
     * @param user  user
     * @param model model
     */
    private void setCustomerSalesmanMapping(final AppUserModel user, final DownloadModel model) {
        model.setCustomerSalesmanMapping(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_CUSTOMER_SALESMAN_MAPPING),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, model.getMappedCustomerCode()),
                        CustomerSalesmanMappingModel.class), user.isEnableCompress()));
    }

    /**
     * Method to set sales officer.
     * @param user  user
     * @param model model
     */
    private void setSalesOfficer(final AppUserModel user, final DownloadModel model) {
        model.setSalesOfficer(repository.queryForList(
                queryService.get(StringConstants.FETCH_SALES_OFFICER),
                Map.of(StringConstants.CONST_PARAM_1, user.getLoginCode()), CompanyUserDTO.class));
    }

    /**
     * Method to fetch dashboard widget.
     * @param user user
     * @return dashboardwidget
     */
    public List<WidgetModel> fetchDashboardWidget(final AppUserModel user)
            throws IllegalAccessException {
        var data = repository.queryForList(
                queryService.get(StringConstants.FETCH_DASHBOARD_WIDGET),
                Map.of(StringConstants.CONST_PARAM_1, user.getLoginCode()), DashboardModel.class);
        List<WidgetModel> widgetModelList = new ArrayList<>();
        if (data.size() == 0) {
            return widgetModelList;
        } else {
            WidgetModel widgetModel;
            HashMap<String, String> map = convertHashMap(data.get(0));
            for (var i = 1; i < StringConstants.CONST_INT_11; i++) {
                widgetModel = new WidgetModel();
                if (map.get(StringConstants.DISPLAY_CODE + i) != null) {
                    widgetModel.setDisplayCode(map.get(StringConstants.DISPLAY_CODE + i));
                }
                if (map.get(StringConstants.DISPLAY_ICON + i) != null) {
                    widgetModel.setDisplayIcon(map.get(StringConstants.DISPLAY_ICON + i));
                }
                if (map.get(DISPLAY_COLOR + i) != null) {
                    widgetModel.setDisplayColor(map.get(DISPLAY_COLOR + i));
                }
                if (map.get(StringConstants.DISPLAY_VALUE + i) != null) {
                    widgetModel.setDisplayValue(map.get(StringConstants.DISPLAY_VALUE + i));
                    widgetModelList.add(widgetModel);
                }
            }
        }
        return widgetModelList;
    }

    private HashMap<String, String> convertHashMap(final Object object) throws IllegalAccessException {
        HashMap<String, String> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName() != null && field.get(object) != null) {
                map.put(field.getName(), field.get(object).toString());
            }
        }
        return map;
    }
}
