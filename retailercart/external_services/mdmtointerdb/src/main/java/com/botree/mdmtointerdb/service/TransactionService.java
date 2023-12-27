package com.botree.mdmtointerdb.service;

import com.botree.interdbentity.model.ExternalProcessDetail;
import com.botree.mdmtointerdb.dao.DAORepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This class is used to save the data.
 * @author vinodkumara
 */
@Service
@Transactional
public class TransactionService {

    /** repository. */
    private final DAORepository repository;

    /**
     * Constructor Method.
     * @param repositoryIn repositoryIn
     */
    public TransactionService(final DAORepository repositoryIn) {
        this.repository = repositoryIn;
    }

    /**
     * Method(convertPostData) fetch all the process query which needs to be deleted
     * from the DB.
     * @param data data
     * @param list list
     */
    @SuppressWarnings("rawtypes")
    @Transactional
    public void convertTransactionData(final ExternalProcessDetail data,
                                       final List list) {
        updateOrDelete(data, list);
        insert(data, list);
    }

    /**
     * Method to insert data.
     * @param data data
     * @param list list
     */
    @SuppressWarnings("rawtypes")
    private void insert(final ExternalProcessDetail data, final List list) {
        repository.bulkInsert(data.getInterDBQuery(), list);
    }

    /**
     * Method to update or delete data.
     * @param data data
     * @param list list
     */
    @SuppressWarnings("rawtypes")
    public void updateOrDelete(final ExternalProcessDetail data, final List list) {
        if (data.getInterDBUpdateQuery() != null && !data.getInterDBUpdateQuery().isEmpty()) {
            repository.updateStatus(data.getInterDBUpdateQuery(), list);
        }
    }
}
