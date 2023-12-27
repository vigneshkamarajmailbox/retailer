package com.botree.mdmtointerdb.service;

import com.botree.interdbentity.model.ExternalProcessDetail;
import com.botree.interdbentity.model.TransactionModel;
import com.botree.interdbentity.model.UploadProcess;
import com.botree.mdmtointerdb.constants.StringConstants;
import com.botree.mdmtointerdb.constants.WebServicePathConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class contains the web service methods.
 * @author vinodkumara
 */
@Component
public class InterDBWebService {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(InterDBWebService.class);

    /** interdbURL. */
    @Value("${interdb.url}")
    private String interdbURL;

    /** username. */
    @Value("${oauth.username}")
    private String username;

    /** password. */
    @Value("${oauth.password}")
    private String password;

    /**
     * This method is used to call the delete web service with query and distributor
     * list.
     * @param token    token
     * @param path     remote url
     * @param saveData data
     * @param entity   entity
     * @return status
     */
    @SuppressWarnings("rawtypes")
    public final String postData(final String token, final String path, final List saveData, final String entity) {
        var data = new UploadProcess();
        var mapper = new ObjectMapper();
        try {
            data.setJsonArr(mapper.writeValueAsBytes(saveData));
            data.setEntity(entity);
        } catch (JsonProcessingException e) {
            LOG.error("exception in post data json conversion :: ", e);
        }
        return postDataService(token, path + "/save/", data);
    }

    /**
     * This method is used to call the delete web service with query and distributor
     * list.
     * @param token token
     * @param path  remote url
     * @param data  data
     * @return status
     */
    private String postDataService(final String token, final String path, final UploadProcess data) {
        try {
            var response = callWebService(token, path, data, String.class);
            return (String) response.getBody();
        } catch (URISyntaxException e) {
            LOG.error("exception in post data service ::", e);
        }
        return StringConstants.VALIDATIONFAILURE;
    }

    /**
     * This method is used to call the delete web service with query and distributor
     * list.
     * @param <T>          object
     * @param token        token
     * @param path         remote url
     * @param distributors distributors
     * @param data         data
     * @return <T>
     */
    public final <T> T fetchData(final String token, final String path, final List<String> distributors,
                                 final ExternalProcessDetail data) {
        var transactionModel = new TransactionModel();
        transactionModel.setCmpCode(data.getCmpCode());
        transactionModel.setDistrCode(distributors);
        transactionModel.setInterDBProcess(data.getInterDBProcess());
        transactionModel.setEntity(data.getEntity());
        return processResponse(fetchDataService(token, path, transactionModel), data.getEntity());
    }

    /**
     * This method is used to call the delete web service with query and distributor
     * list.
     * @param token token
     * @param path  remote url
     * @param data  data
     * @return status
     */
    private Object fetchDataService(final String token, final String path, final TransactionModel data) {
        try {
            var response = callWebService(token, path, data, List.class);
            if (HttpStatus.OK.equals(response.getStatusCode())) {
                return response.getBody();
            }
        } catch (URISyntaxException e) {
            LOG.error("exception in fetch data service :: ", e);
        }
        return new ArrayList<>();
    }


    /**
     * This method is used to call the delete web service with query and distributor list.
     * @param token           token
     * @param path            remote url
     * @param transactionList transactionList
     * @param actionAPIList   actionAPIList
     * @param data            data
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public final void updateTransaction(final String token, final String path, final List transactionList,
                                        final List actionAPIList, final ExternalProcessDetail data) {
        var transactionModel = new TransactionModel();
        transactionModel.setCmpCode(data.getCmpCode());
        transactionModel.setEntity(data.getEntity());
        if (!actionAPIList.isEmpty()) {
            transactionModel.setUpdateList(new ArrayList());
            transactionModel.setActionList(actionAPIList);
        } else {
            transactionModel.setActionList(new ArrayList<>());
            transactionModel.setUpdateList(transactionList);
        }
        transactionModel.setInterDBProcess(data.getInterDBProcess());
        fetchDataUpdateService(token, path, transactionModel);
    }

    /**
     * This method is used to call the delete web service with query and distributor
     * list.
     * @param token token
     * @param path  remote url
     * @param model model
     */
    private void fetchDataUpdateService(final String token, final String path, final TransactionModel model) {
        try {
            var response = callWebService(token, path, model, Object.class);
            if (HttpStatus.OK.equals(response.getStatusCode())) {
                LOG.info("fetch data update service response :: {} ", response.getBody());
            }
        } catch (URISyntaxException e) {
            LOG.error("exception in fetch data update service ::", e);
        }
    }

    /**
     * This method is used to configure the http timeout.
     * @return ClientHttpRequestFactory
     */
    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        var timeout = 0;
        var clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

    /**
     * Method to call web service.
     * @param token token
     * @param path  path
     * @param data  data
     * @param clazz clazz
     * @return response entity
     * @throws URISyntaxException URISyntaxException
     */
    private ResponseEntity<?> callWebService(final String token, final String path, final Object data,
                                             final Class<?> clazz)
            throws URISyntaxException {
        var postDataClient = new RestTemplate(getClientHttpRequestFactory());
        var url = new URI(interdbURL + path);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        var request = new HttpEntity<>(data, headers);
        return postDataClient.exchange(url, HttpMethod.POST, request, clazz);
    }

    /**
     * Method to process the web service response.
     * @param response response object
     * @param type     class type
     * @param <T>      object
     * @return <T>
     */
    @SuppressWarnings("rawtypes")
    private <T> T processResponse(final Object response, final String type) {
        var mapper = new ObjectMapper();
        try {
            Class clazz = Class.forName(type);
            JavaType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return mapper.convertValue(response, collectionType);
        } catch (ClassNotFoundException e) {
            LOG.error("process response exception :: ", e);
        }
        return null;
    }

    /**
     * Method for OAuth Login.
     * @return token
     */
    public String oauthLogin() {
        try {
            var client = new RestTemplate(getClientHttpRequestFactory());
            var url = new URI(interdbURL + WebServicePathConstants.REDIRECT_TO_OAUTH_LOGIN
                    + "grant_type=password&scope=user_info&username=" + username + "&password="
                    + getMd5(username + password));
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            var request = new HttpEntity<>(null, headers);
            var response = client.exchange(url, HttpMethod.POST, request, String.class);
            if (HttpStatus.OK.equals(response.getStatusCode())) {
                ObjectMapper mapper = new ObjectMapper();
                var map = mapper.readValue(Objects.requireNonNull(response.getBody()), Map.class);
                LOG.info("login token :: {}", map.get("access_token"));
                return String.valueOf(map.get("access_token"));
            }
        } catch (URISyntaxException | JsonProcessingException e) {
            LOG.error("exception in oauth login :: ", e);
        }
        return null;
    }

    /**
     * Method for OAuth Logout.
     * @param token token
     */
    public void oauthLogout(final String token) {
        try {
            var client = new RestTemplate(getClientHttpRequestFactory());
            var url = new URI(interdbURL + WebServicePathConstants.REDIRECT_TO_OAUTH_LOGOUT);
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);
            var request = new HttpEntity<>(null, headers);
            var response = client.exchange(url, HttpMethod.POST, request, Object.class);
            if (HttpStatus.OK.equals(response.getStatusCode())) {
                LOG.info("logout successfully.");
            }
        } catch (URISyntaxException e) {
            LOG.error("exception in oauth logout :: ", e);
        }
    }

    /**
     * This method is used to generate Md5 hash.
     * @param input Raw String
     * @return MD5 hash for the input string
     */
    private String getMd5(final String input) {
        try {
            // Static getInstance method is called with hashing SHA
            var md = MessageDigest.getInstance("MD5");

            // digest() method called
            // to calculate message digest of an input
            // and return array of byte
            var messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            var no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            var hashText = new StringBuilder();
            hashText.append(no.toString(StringConstants.CONST_INT_16));

            while (hashText.length() < StringConstants.CONST_INT_32) {
                hashText.append("0").append(hashText);
            }

            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            LOG.error("exception while generating md5 hash :: ", e);
            return null;
        }
    }
}
