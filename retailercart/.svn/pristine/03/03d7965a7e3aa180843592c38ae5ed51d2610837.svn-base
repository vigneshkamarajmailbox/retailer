package com.botree.sfaweb.service;

import com.botree.common.constants.StringConstants;
import com.botree.common.model.DownloadModel;
import com.botree.common.service.IQueryService;
import com.botree.common.util.Function;
import com.botree.interdbentity.model.AppUserModel;
import com.botree.interdbentity.model.ApplicationUpdateEntity;
import com.botree.interdbentity.model.SchemeCombiProductEntity;
import com.botree.interdbentity.model.SchemeCustomerMappingModel;
import com.botree.interdbentity.model.SchemeDefinitionEntity;
import com.botree.interdbentity.model.SchemeSlabEntity;
import com.botree.interdbentity.model.SchemeSlabProductEntity;
import com.botree.sfadbrepository.dao.DAORepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 * Class contains the scheme related data.
 * @author vinodkumara
 */
@Component
@Transactional
public class SchemeService {

    /** applicationUpdate. */
    private ApplicationUpdateEntity applicationUpdate;

    /** repository. */
    private final DAORepository repository;

    /** queryService. */
    private final IQueryService queryService;

    /** masterService. */
    private final MasterService masterService;

    /**
     * Constructor Method.
     * @param repositoryIn    repositoryIn
     * @param queryServiceIn  queryServiceIn
     * @param masterServiceIn masterServiceIn
     */
    public SchemeService(final DAORepository repositoryIn, final IQueryService queryServiceIn,
                         final MasterService masterServiceIn) {
        this.repository = repositoryIn;
        this.queryService = queryServiceIn;
        this.masterService = masterServiceIn;
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
     * Method to fetch the download scheme data for the user.
     * @param appUser appUser
     * @return download model
     */
    public DownloadModel downloadScheme(final AppUserModel appUser) {
        var downloadModel = new DownloadModel();
        validateUserActiveStatus(appUser, downloadModel);
        return downloadModel;
    }


    /**
     * Method to validate user active status.
     * @param appUser       appUser
     * @param downloadModel downloadModel
     */
    private void validateUserActiveStatus(final AppUserModel appUser, final DownloadModel downloadModel) {
        if (LocalDate.now().isEqual(appUser.getSystemDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
            downloadModel.setSystemDateAuthFlag(Boolean.TRUE);
            if (repository.queryForObject(queryService.get(StringConstants.FETCH_USER_STATUS), Integer.class,
                    appUser.getLoginCode()) > 0) {
                if (appUser.getAppVersion().equalsIgnoreCase(applicationUpdate.getAppVersion())) {
                    fetchSchemeData(appUser, downloadModel);
                } else {
                    downloadModel.setNewAppDownloadURL(applicationUpdate.getUrl() + applicationUpdate.getFileName());
                    downloadModel.setNewAppVersion(applicationUpdate.getAppVersion());
                }
                downloadModel.setAuthFlag(Boolean.TRUE);
            } else {
                downloadModel.setAuthFlag(Boolean.FALSE);
            }
        } else {
            downloadModel.setSystemDateAuthFlag(Boolean.FALSE);
        }
    }

    /**
     * Method to fetch the download data for the user.
     * @param user user
     * @return download model
     */
    public DownloadModel downloadSchemeMaster(final AppUserModel user) {
        var downloadModel = new DownloadModel();
        if (repository.queryForObject(queryService.get(StringConstants.FETCH_USER_STATUS), Integer.class,
                user.getLoginCode()) > 0) {
            fetchSchemeData(user, downloadModel);
            downloadModel.setAuthFlag(Boolean.TRUE);
        } else {
            downloadModel.setAuthFlag(Boolean.FALSE);
        }
        return downloadModel;
    }

    /**
     * Method to fetch scheme data for distributor user.
     * @param appUser appUser
     * @param model   model
     */
    private void fetchSchemeData(final AppUserModel appUser, final DownloadModel model) {
        masterService.setUserMapping(appUser, model);
        if (model.getMappedCompany() != null && !model.getMappedCompany().isEmpty()) {
            setSchemeDefinition(appUser, model);
            setSchemeSlab(appUser, model);
            setSchemeSlabProduct(appUser, model);
            setSchemeCustomerMapping(appUser, model);
            setSchemeCombiProduct(appUser, model);
        }
    }

    /**
     * Method to fetch scheme definition data.
     * @param appUser appUser
     * @param model   model
     */
    private void setSchemeDefinition(final AppUserModel appUser, final DownloadModel model) {
        model.setSchemeDefinition(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_SCHEMEDEFINITIONENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, appUser.getLanguageCode()),
                        SchemeDefinitionEntity.class), appUser.isEnableCompress()));
    }

    /**
     * Method to fetch scheme slab data.
     * @param appUser appUser
     * @param model   model
     */
    private void setSchemeSlab(final AppUserModel appUser, final DownloadModel model) {
        model.setSchemeSlab(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_SCHEMESLABENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor()),
                        SchemeSlabEntity.class), appUser.isEnableCompress()));
    }

    /**
     * Method to fetch scheme slab product data.
     * @param appUser appUser
     * @param model   model
     */
    private void setSchemeSlabProduct(final AppUserModel appUser, final DownloadModel model) {
        model.setSchemeSlabProduct(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_SCHEMESLABPRODUCTENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor()),
                        SchemeSlabProductEntity.class), appUser.isEnableCompress()));
    }

    /**
     * Method to fetch scheme combi product data for user.
     * @param appUser appUser
     * @param model   model
     */
    private void setSchemeCombiProduct(final AppUserModel appUser, final DownloadModel model) {
        model.setSchemeCombiProduct(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_SCHEMECOMBIPRODUCTENTITY),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor()),
                        SchemeCombiProductEntity.class), appUser.isEnableCompress()));
    }

    /**
     * Method to fetch scheme customer mapping data.
     * @param appUser appUser
     * @param model   model
     */
    private void setSchemeCustomerMapping(final AppUserModel appUser, final DownloadModel model) {
        model.setSchemeCustomerMapping(Function.compress(repository
                .queryForList(queryService.get(StringConstants.FETCH_SCHEME_CUSTOMER_MAPPING),
                        Map.of(StringConstants.CONST_PARAM_1, model.getMappedCompany(),
                                StringConstants.CONST_PARAM_2, model.getMappedDistributor(),
                                StringConstants.CONST_PARAM_3, model.getMappedCustomerCode()),
                        SchemeCustomerMappingModel.class), appUser.isEnableCompress()));
    }
}
