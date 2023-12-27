package com.botree.mdmtointerdb;

import com.botree.interdbentity.model.ExternalProcessDetail;
import com.botree.mdmtointerdb.constants.StringConstants;
import com.botree.mdmtointerdb.constants.WebServicePathConstants;
import com.botree.mdmtointerdb.dao.DAORepository;
import com.botree.mdmtointerdb.model.ActionAPIModel;
import com.botree.mdmtointerdb.service.OrderSSFAService;
import com.botree.mdmtointerdb.service.TransactionService;
import com.botree.mdmtointerdb.service.InterDBWebService;
import com.botree.mdmtointerdb.service.IQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to configure scheduler's and call the dao repository for
 * db access.
 * @author vinodkumar.a
 */
@Component
public class SchedulerService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

    /** mdmtointerdb1hrs. */
    @Value("${mdmtointerdb1hrsEnabled}")
    private boolean mdmtointerdb1hrs;

    /** mdmtointerdb2hrs. */
    @Value("${mdmtointerdb2hrsEnabled}")
    private boolean mdmtointerdb2hrs;

    /** mdmtointerdb4hrs. */
    @Value("${mdmtointerdb4hrsEnabled}")
    private boolean mdmtointerdb4hrs;

    /** mdmtointerdb8hrs. */
    @Value("${mdmtointerdb8hrsEnabled}")
    private boolean mdmtointerdb8hrs;

    /** mdmtointerdb24hrs. */
    @Value("${mdmtointerdb24hrsEnabled}")
    private boolean mdmtointerdb24hrs;

    /** transactionEnabled. */
    @Value("${transactionEnabled}")
    private boolean transactionEnabled;

    /** distributorCount. */
    @Value("${distributor.count}")
    private Integer distributorCount;

    /** dataCount. */
    @Value("${data.count}")
    private Integer dataCount;

    /** specificDistributorEnable. */
    @Value("${specificdistributorenable}")
    private Boolean specificDistributorEnable;

    /** distributors. */
    @Value("${distributor}")
    private String distributor;

    /** changeLogEnable. */
    @Value("${changelog.enable}")
    private Boolean changeLogEnable;

    /** cmpCode. */
    @Value("${cmpCode}")
    private String cmpCode;

    /** actionAPIEnabled. */
    @Value("${actionAPIEnabled}")
    private boolean actionAPIEnabled;

    /** repository. */
    private final DAORepository repository;

    /** queryService. */
    private final IQueryService queryService;

    /** service. */
    private final InterDBWebService service;

    /** transactionService. */
    private final TransactionService transactionService;

    /** orderSSFAService. */
    private final OrderSSFAService orderSSFAService;

    /**
     * Constructor Method.
     * @param repositoryIn         repositoryIn
     * @param queryServiceIn       queryServiceIn
     * @param serviceIn            serviceIn
     * @param transactionServiceIn transactionServiceIn
     * @param orderSSFAServiceIn   orderSSFAServiceIn
     */
    public SchedulerService(final DAORepository repositoryIn,
                            final IQueryService queryServiceIn,
                            final InterDBWebService serviceIn,
                            final TransactionService transactionServiceIn,
                            final OrderSSFAService orderSSFAServiceIn) {
        this.repository = repositoryIn;
        this.queryService = queryServiceIn;
        this.service = serviceIn;
        this.transactionService = transactionServiceIn;
        this.orderSSFAService = orderSSFAServiceIn;
    }

    /**
     * Method for every 1 hr scheduler.
     */
    @Scheduled(cron = "${cron.mdmtointerdb1hrs}")
    public final void trigger1hrsScheduler() {
        if (mdmtointerdb1hrs) {
            triggerScheduler(StringConstants.INTERVAL_1HRS);
        }
    }

    /**
     * Method for every 2 hr scheduler.
     */
    @Scheduled(cron = "${cron.mdmtointerdb2hrs}")
    public final void trigger2hrsScheduler() {
        if (mdmtointerdb2hrs) {
            triggerScheduler(StringConstants.INTERVAL_2HRS);
        }
    }

    /**
     * Method for every 4 hr scheduler.
     */
    @Scheduled(cron = "${cron.mdmtointerdb4hrs}")
    public final void trigger4hrsScheduler() {
        if (mdmtointerdb4hrs) {
            triggerScheduler(StringConstants.INTERVAL_4HRS);
        }
    }

    /**
     * Method for every 8 hr scheduler.
     */
    @Scheduled(cron = "${cron.mdmtointerdb8hrs}")
    public final void trigger8hrsScheduler() {
        if (mdmtointerdb8hrs) {
            triggerScheduler(StringConstants.INTERVAL_8HRS);
        }
    }

    /**
     * Method for every 24 hr scheduler.
     */
    @Scheduled(cron = "${cron.mdmtointerdb24hrs}")
    public final void trigger24hrsScheduler() {
        if (mdmtointerdb24hrs) {
            triggerScheduler(StringConstants.INTERVAL_24HRS);
        }
    }

    /**
     * Method for every 24 hr scheduler.
     */
    @Scheduled(cron = "${cron.transaction}")
    public final void triggerTransactionScheduler() {
        if (transactionEnabled) {
            triggerTransaction(StringConstants.CONST_NO);
        }
    }

    /**
     * Method for trigger action api.
     */
    @Scheduled(cron = "${cron.actionapi}")
    public final void triggerActionAPIScheduler() {
        if (actionAPIEnabled) {
            triggerActionAPI(StringConstants.CONST_NO);
        }
    }

    /**
     * Method(triggerScheduler) will trigger at the scheduled time.
     * @param interval interval
     */
    public final void triggerScheduler(final String interval) {
        try {
            LOG.info("trigger master scheduler start :: {}", new Date());
            var distributors = fetchDistributor();
            if (!distributors.isEmpty()) {
                postData(distributors, interval);
            }
            LOG.info("trigger master scheduler end :: {}", new Date());
        } catch (Exception e) {
            LOG.error("master exception :: ", e);
        }

    }

    /**
     * Method(triggerTransactionScheduler) will trigger at the scheduled time.
     * @param interval interval
     */
    public final void triggerTransaction(final String interval) {
        try {
            LOG.info("trigger transaction scheduler start :: {} ", new Date());
            var distributors = fetchDistributor();
            if (!distributors.isEmpty()) {
                fetchData(distributors, interval);
            }
            LOG.info("trigger transaction scheduler end :: {} ", new Date());
        } catch (Exception e) {
            LOG.error("transaction exception :: ", e);
        }
    }

    /**
     * Method(triggerActionAPIScheduler) will trigger at the scheduled time.
     * @param interval interval
     */
    public final void triggerActionAPI(final String interval) {
        try {
            LOG.info("trigger action api scheduler start :: {} ", new Date());
            var distributors = fetchDistributor();
            if (!distributors.isEmpty()) {
                fetchActionAPIData(distributors, interval);
            }
            LOG.info("trigger action api scheduler end :: {} ", new Date());
        } catch (Exception e) {
            LOG.error("action api exception :: ", e);
        }
    }

    /**
     * Method(fetchDistributor) is used to fetch all the PDA applicable distributor
     * list.
     * @return List of distributors
     */
    private List<String> fetchDistributor() {
        if (specificDistributorEnable) {
            return Arrays.asList((distributor.split(StringConstants.CONST_COMMA)));
        } else {
            return repository.queryForListWrapper(queryService.get(StringConstants.FETCH_DISTRIBUTOR_FOR_INTEGRATION),
                    new HashMap<>(), String.class);
        }
    }

    /**
     * Method(postData) fetch all the process query which needs to be processed.
     * @param distributorsList distributorsList
     * @param interval         interval
     */
    private void postData(final List<String> distributorsList, final String interval) {
        LOG.info("post data start :: ");
        var token = service.oauthLogin();
        var changeNo = 0;
        if (changeLogEnable) {
            changeNo = repository.queryForObject(queryService.get(StringConstants.FETCH_MAX_CHANGE_NO), Integer.class);
        }
        var processes = repository.queryForListWithRowMapper(
                queryService.get(StringConstants.FETCH_PROCESSDETAILS), ExternalProcessDetail.class,
                StringConstants.PROCESSENABLE, StringConstants.PROCESSTYPE_MASTER, interval);
        for (var data : processes) {
            try {
                LOG.info("post data :: {} ", data.getInterDBProcess());
                List<String> responseList = new ArrayList<>();
                data.setStartDate(new Date());
                if (StringConstants.CONST_YES.equalsIgnoreCase(data.getDistributorWise())) {
                    for (var j = 0; j < distributorsList.size(); j += distributorCount) {
                        var distributors =
                                distributorsList.subList(j, Math.min(distributorsList.size(), j + distributorCount));
                        var params = convertParams(distributors, data.getMaxChangeNo(), changeNo);
                        if (StringConstants.SMS_NOTIFY_CUST_ACTION_API.equalsIgnoreCase(data.getInterDBProcess())) {
                            getActionAPIData(data, distributors, token);
                        } else {
                            convertPostData(token, data, params, responseList);
                        }
                    }
                } else {
                    var params = convertParams(new ArrayList<>(), data.getMaxChangeNo(), changeNo);
                    if (StringConstants.SMS_NOTIFY_CUST_ACTION_API.equalsIgnoreCase(data.getInterDBProcess())) {
                        getActionAPIData(data, new ArrayList<>(), token);
                    } else {
                        convertPostData(token, data, params, responseList);
                    }
                }

                if (responseList.contains(StringConstants.VALIDATIONFAILURE)) {
                    data.setProcessStatus(StringConstants.VALIDATIONFAILURE);
                } else {
                    data.setMaxChangeNo(changeNo);
                    data.setProcessStatus(StringConstants.VALIDATIONSUCCESS);
                }
                data.setEndDate(new Date());
            } catch (Exception e) {
                LOG.error("exception in post data method :: ", e);
                data.setProcessStatus(StringConstants.VALIDATIONFAILURE);
                data.setEndDate(new Date());
            }
            repository.updateStatus(queryService.get(StringConstants.UPDATE_PROCESSDETAILS), List.of(data));
        }
        service.oauthLogout(token);
        LOG.info("post data end :: ");
    }


    /**
     * Method(convertPostData) fetch all the process query which needs to be deleted
     * from the DB.
     * @param token        token
     * @param data         data
     * @param params       params
     * @param responseList responseList
     * @throws ClassNotFoundException ClassNotFoundException
     */
    @SuppressWarnings({"rawtypes"})
    private void convertPostData(final String token, final ExternalProcessDetail data,
                                 final Map<String, Object> params,
                                 final List<String> responseList)
            throws ClassNotFoundException {
        List list = repository.queryForList(data.getInterDBQuery(), params, Class.forName(data.getEntity()));
        for (var i = 0; i < list.size(); i += dataCount) {
            var sub = list.subList(i, Math.min(list.size(), i + dataCount));
            var response = service.postData(token, WebServicePathConstants.REDIRECT_TO_CONTROLLER,
                    sub, data.getEntity());
            responseList.add(response);
            if (StringConstants.VALIDATIONSUCCESS.equalsIgnoreCase(response)) {
                transactionService.updateOrDelete(data, sub);
            }
        }
    }

    /**
     * Method to fetch transaction data and update.
     * @param distributorsList distributorsList
     * @param interval         interval
     */
    private void fetchData(final List<String> distributorsList, final String interval) {
        var token = service.oauthLogin();
        List<ExternalProcessDetail> processes = repository.queryForListWithRowMapper(
                queryService.get(StringConstants.FETCH_PROCESSDETAILS), ExternalProcessDetail.class,
                StringConstants.PROCESSENABLE, StringConstants.PROCESSTYPE_TRANSACTION, interval);
        processes.forEach(data -> {
            try {
                if (!data.getInterDBProcess().equalsIgnoreCase(StringConstants.ACTION_API)) {
                    data.setStartDate(new Date());
                    convertTransactionData(token, data, distributorsList);
                    data.setProcessStatus(StringConstants.VALIDATIONSUCCESS);
                    data.setEndDate(new Date());
                    data.setMaxChangeNo(0);
                }
            } catch (Exception e) {
                LOG.error("exception in fetch data method :: ", e);
                data.setProcessStatus(StringConstants.VALIDATIONFAILURE);
                data.setEndDate(new Date());
            }
            repository.updateStatus(queryService.get(StringConstants.UPDATE_PROCESSDETAILS), List.of(data));
        });
        service.oauthLogout(token);
    }

    /**
     * Method to fetch action api data and update.
     * @param distributorsList distributorsList
     * @param interval         interval
     */
    private void fetchActionAPIData(final List<String> distributorsList, final String interval) {
        var token = service.oauthLogin();
        List<ExternalProcessDetail> processes = repository.queryForListWithRowMapper(
                queryService.get(StringConstants.FETCH_PROCESSDETAILS), ExternalProcessDetail.class,
                StringConstants.PROCESSENABLE, StringConstants.PROCESSTYPE_TRANSACTION, interval);
        processes.forEach(data -> getActionAPIData(data, distributorsList, token));
        service.oauthLogout(token);
    }

    /**
     * Method to get action api data
     * from the DB.
     * @param data             data
     * @param distributorsList distributorsList
     * @param token            token
     */
    private void getActionAPIData(final ExternalProcessDetail data, final List<String> distributorsList,
                                  final String token) {
        try {
            if (checkActionAPIProcess(data.getInterDBProcess())) {
                var changeNo = 0;
                if (changeLogEnable) {
                    changeNo = repository.queryForObject(queryService.get(StringConstants.FETCH_MAX_CHANGE_NO),
                            Integer.class);
                }
                data.setStartDate(new Date());
                if (convertActionApiData(token, data, distributorsList, changeNo)) {
                    data.setProcessStatus(StringConstants.VALIDATIONSUCCESS);
                    data.setEndDate(new Date());
                    data.setMaxChangeNo(changeNo);
                } else {
                    data.setProcessStatus(StringConstants.VALIDATIONFAILURE);
                    data.setEndDate(new Date());
                }
            }
        } catch (Exception e) {
            LOG.error("exception in getActionAPIData method :: ", e);
            data.setProcessStatus(StringConstants.VALIDATIONFAILURE);
            data.setEndDate(new Date());
        }
        repository.updateStatus(queryService.get(StringConstants.UPDATE_PROCESSDETAILS), List.of(data));
    }

    /**
     * Method(convertPostData) fetch all the process query which needs to be deleted
     * from the DB.
     * @param token        token
     * @param data         data
     * @param distributors distributors
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void convertTransactionData(final String token, final ExternalProcessDetail data,
                                        final List distributors) {
        var list = (List) service.fetchData(token, WebServicePathConstants.REDIRECT_TO_TRANSACTION_FETCHDATA,
                distributors, data);
        LOG.info("response list ::{} : {}", data.getProcessType(), list.size());
        if (StringConstants.PROCESS_TYPE_ORDER.equalsIgnoreCase(data.getInterDBProcess())) {
            orderSSFAService.convertOrderTransactionData(list, cmpCode, data);
        } else {
            orderSSFAService.processBasedOperation(list, data);
        }
        service.updateTransaction(token, WebServicePathConstants.REDIRECT_TO_UPDATE_TRANSACTION_STATUS,
                list, new ArrayList<>(), data);
    }

    /**
     * Method(convertActionApiData) fetch all the process query which needs to be deleted
     * from the DB.
     * @param token        token
     * @param data         data
     * @param distributors distributors
     * @param changeNo     changeNo
     * @return boolean
     */
    @SuppressWarnings({"rawtypes"})
    private boolean convertActionApiData(final String token, final ExternalProcessDetail data,
                                         final List distributors, final Integer changeNo) {
        try {
            final Map<String, Object> paramMap = new HashMap<>();
            List list;
            if (data.getInterDBProcess().equalsIgnoreCase(StringConstants.SMS_NOTIFY_CUST_ACTION_API)) {
                paramMap.put(StringConstants.CMP_CODE, cmpCode);
                paramMap.put(StringConstants.DISTR_CODE, distributors);
            } else {
                paramMap.put(StringConstants.CMP_CODE, cmpCode);
                paramMap.put(StringConstants.DISTR_CODE, distributors);
                paramMap.put(StringConstants.PARAM_MIN_CHANGE_NO, data.getMaxChangeNo());
                paramMap.put(StringConstants.PARAM_MAX_CHANGE_NO, changeNo);
            }
            list = repository.queryForList(data.getInterDBQuery(), paramMap, ActionAPIModel.class);
            service.updateTransaction(token, getActionAPIURL(), new ArrayList<>(), list, data);
        } catch (Exception e) {
            LOG.error("exception in convertActionApiData method :: ", e);
            return false;
        }
        return true;
    }

    private String getActionAPIURL() {
        return WebServicePathConstants.REDIRECT_TO_UPDATE_ACTIONAPI_STATUS;
    }

    private boolean checkActionAPIProcess(final String interDBProcess) {
        return interDBProcess.equalsIgnoreCase(StringConstants.ACTION_API)
                || interDBProcess.equalsIgnoreCase(StringConstants.SMS_NOTIFY_CUST_ACTION_API);
    }

    /**
     * Method to form the params.
     * @param distributors distributors
     * @param minChangeNo  minChangeNo
     * @param maxChangeNo  maxChangeNo
     * @return map
     */
    private Map<String, Object> convertParams(final List<String> distributors, final Integer minChangeNo,
                                              final Integer maxChangeNo) {
        return Map.of(StringConstants.PARAM_MIN_CHANGE_NO, minChangeNo,
                StringConstants.PARAM_MAX_CHANGE_NO, maxChangeNo,
                StringConstants.PARAM_IDS, distributors);
    }
}
