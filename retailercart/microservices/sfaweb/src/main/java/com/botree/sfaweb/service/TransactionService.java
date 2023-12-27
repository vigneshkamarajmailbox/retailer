package com.botree.sfaweb.service;

import com.botree.common.exception.ServiceException;
import com.botree.common.model.UploadModel;
import com.botree.interdbentity.model.UploadStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class contains the transaction related activities.
 * @author vinodkumar.a
 */
@Component
public class TransactionService {

    /** imageLocalFilePath. */
    @Value("${image.path}")
    private String imageLocalFilePath;

    /** customerService. */
    private final CustomerService customerService;

    /**
     * Constructor Method.
     * @param customerServiceeIn customerServiceeIn
     */
    public TransactionService(final CustomerService customerServiceeIn) {
        this.customerService = customerServiceeIn;
    }

    /**
     * Method to upload transaction data.
     * @param model upload data
     * @return UploadStatus
     */
    public UploadStatus upload(final UploadModel model) {
        var status = new UploadStatus();
        saveTransactionProcess(model, status);
        return status;
    }

    /**
     * @param uploadModel uploadModel
     * @param status      status
     */
    private void saveTransactionProcess(final UploadModel uploadModel, final UploadStatus status) {
        try {
            if (uploadModel.isEnableCompress()) {
                // Save Order
                customerService.saveOrderInfo(uploadModel, status);

                // Save Key Generator
                customerService.saveKeyGeneratorInfo(uploadModel, status);

                // Save Sync Log
                customerService.saveSyncLogInfo(uploadModel, status);

                // Save Feedback and Store Visibility.
                customerService.saveFeedbackInfo(uploadModel, status, imageLocalFilePath);

                // Save Location Update.
                customerService.saveLocationUpdateInfo(uploadModel, status, imageLocalFilePath);
            } else {
                // save all customer related transactions
                customerService.saveCustomerTransaction(uploadModel, status);
            }
        } catch (Exception e) {
            throw new ServiceException("save customer transaction process exception ::", e);
        }
    }
}
