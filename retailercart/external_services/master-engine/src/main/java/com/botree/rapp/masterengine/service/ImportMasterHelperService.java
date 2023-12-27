package com.botree.rapp.masterengine.service;

import com.botree.rapp.masterengine.constants.StringConstants;
import com.botree.rapp.masterengine.dao.IDAORepository;
import com.botree.rapp.masterengine.dto.AppUserEntity;
import com.botree.rapp.masterengine.dto.CustomerEntity;
import com.botree.rapp.masterengine.dto.KeyGeneratorEntity;
import com.botree.rapp.masterengine.dto.UserActivationEntity;
import com.botree.rapp.masterengine.exception.ServiceException;
import com.botree.rapp.masterengine.util.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This pojo class is used to save records in db.
 * @author vinodkumar.a
 */
@Service
public class ImportMasterHelperService implements IImportMasterHelperService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(ImportMasterHelperService.class);

    /** blobLocalPath. */
    @Value("${master.blob.local.path}")
    private String blobLocalPath;

    /** batchSize. */
    @Value("${batch.size}")
    private Integer batchSize;

    /** queryService. */
    private final QueryService queryService;

    /** repository. */
    private final IDAORepository repository;

    /**
     * Constructor Method.
     * @param queryServiceIn queryServiceIn
     * @param repositoryIn   repositoryIn
     */
    public ImportMasterHelperService(final QueryService queryServiceIn, final IDAORepository repositoryIn) {
        this.queryService = queryServiceIn;
        this.repository = repositoryIn;
    }

    /**
     * This method is used to import company master.
     * @param folder folder
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void importMasterFiles(final String folder) {
        try {
            var isTruncate = false;
            for (File file : Objects.requireNonNull(new File(blobLocalPath + File.separator
                    + StringConstants.FOLDER_INPUT + File.separator + folder).listFiles())) {
                try {
                    if (!isTruncate) {
                        truncateData(folder);
                        isTruncate = true;
                    }
                    String inputPath = blobLocalPath + File.separator + StringConstants.FOLDER_INPUT + File.separator
                            + folder + File.separator + file.getName();
                    int size = importData(folder, inputPath);
                    Path toFolderPath = Paths.get(blobLocalPath + File.separator + StringConstants.FOLDER_PROCESSED
                            + File.separator + folder + File.separator + file.getName());
                    Files.move(file.toPath(), toFolderPath, StandardCopyOption.REPLACE_EXISTING);
                    LOG.info("processed file :: {} {} ", file.getName(), size);
                } catch (Exception e) {
                    LOG.error("exception while processing", e);
                    Path toFolderPath = Paths.get(blobLocalPath + File.separator
                            + StringConstants.FOLDER_ERROR + File.separator + folder + File.separator + file.getName());
                    Files.move(file.toPath(), toFolderPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Method to import the master master data in each files.
     * @param folder    folder
     * @param inputPath inputPath
     * @return size
     * @throws IOException IOException
     */
    private int importData(final String folder, final String inputPath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(inputPath));
        lines.remove(0);
        for (int j = 0; j < lines.size(); j += batchSize) {
            var subList = lines.subList(j,
                    Math.min(lines.size(), j + batchSize));
            List<Object[]> data =
                    subList.stream().map(l -> l.split(StringConstants.REGEX_CAP, -1)).collect(Collectors.toList());
            persistData(folder, data);
        }
        return lines.size();
    }

    /**
     * Method to truncate the data.
     * @param folder folder
     */
    public void truncateData(final String folder) {
        String truncateQuery = null;
        if (folder != null) {
            truncateQuery = queryService.get(StringConstants.TRUNCATE + folder.toUpperCase());
        }
        if (truncateQuery != null) {
            repository.execute(truncateQuery);
        }
    }

    /**
     * This method is used to persist data in db.
     * @param folder folder
     * @param data   Data lines
     */
    private void persistData(final String folder, final List<Object[]> data) {
        if (data != null) {
            String insertQuery = null;
            if (folder != null) {
                insertQuery = queryService.get(StringConstants.INSERT + folder.toUpperCase());
            }
            repository.update(insertQuery, data);
        }
    }

    /**
     * This method is used to create app user, default key generator.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createAppUser() {
        List<CustomerEntity> customerList = repository.queryForListWithRowMapper(
                queryService.get(StringConstants.FETCH_CUSTOMERENTITY), CustomerEntity.class);
        List<KeyGeneratorEntity> existingKeyGeneratorList = repository.queryForListWithRowMapper(
                queryService.get(StringConstants.FETCH_DEFAULT_KEYGENERATOR), KeyGeneratorEntity.class);
        List<String> activeUserList = repository.queryForList(
                queryService.get(StringConstants.FETCH_USER_ACTIVE_LIST), String.class);
        List<String> inActiveUserList = repository.queryForList(
                queryService.get(StringConstants.FETCH_USER_INACTIVE_LIST), String.class);
        var userList = new ArrayList<>();
        var keyGeneratorList = new ArrayList<>();
        var userCreationList = new ArrayList<UserActivationEntity>();
        var userUpdateList = new ArrayList<UserActivationEntity>();
        var userDeactivationList = new ArrayList<UserActivationEntity>();
        customerList.forEach(data -> {
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
            checkUserStatus(userCreationList, userUpdateList, userDeactivationList, activeUserList,
                    inActiveUserList, data);
        });
        repository.bulkInsert(queryService.get(StringConstants.INSERT_MASTER_APPUSERENTITY), userList);
        repository.bulkInsert(queryService.get(StringConstants.INSERT_DEFAULT_KEYGENERATORENTITY), keyGeneratorList);
        repository.bulkInsert(queryService.get(StringConstants.INSERT_USER_CREATION_LIST), userCreationList);
        repository.bulkInsert(queryService.get(StringConstants.UPDATE_USER_DEACTIVATION_LIST), userDeactivationList);
        repository.bulkInsert(queryService.get(StringConstants.UPDATE_USER_CREATION_LIST), userUpdateList);
        repository.bulkInsert(queryService.get(StringConstants.UPDATE_CUSTOMERENTITY), userList);
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
}
