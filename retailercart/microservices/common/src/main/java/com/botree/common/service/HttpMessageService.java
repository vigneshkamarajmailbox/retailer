package com.botree.common.service;

import com.botree.common.constants.MessageConstants;
import com.botree.common.constants.StringConstants;
import com.botree.interdbentity.model.MessageEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is used to send http url message.
 * @author vinodkumar.a
 */
@Service
public class HttpMessageService {

    /** smsURL. */
    @Value("${sms.url}")
    private String smsURL;

    /** smsType. */
    @Value("${sms.type}")
    private String smsType;

    /** smsParam1. */
    @Value("${sms.param1}")
    private String smsParam1;

    /** smsParam2. */
    @Value("${sms.param2}")
    private String smsParam2;

    /** smsParam3. */
    @Value("${sms.param3}")
    private String smsParam3;

    /** smsParam4. */
    @Value("${sms.param4}")
    private String smsParam4;

    /** smsParam5. */
    @Value("${sms.param5}")
    private String smsParam5;

    /** recipient. */
    @Value("${sms.recipient}")
    private String recipient;

    /** message. */
    @Value("${sms.message}")
    private String message;

    /** proxyEnable. */
    @Value("${proxy.enable}")
    private Boolean proxyEnable;

    /** proxyType. */
    @Value("${proxy.type}")
    private String proxyType;

    /** proxyUrl. */
    @Value("${proxy.url}")
    private String proxyUrl;

    /** proxyPort. */
    @Value("${proxy.port}")
    private Integer proxyPort;

    /**
     * Method to send message based on type.
     * @param messageEntity messageEntity
     * @param msgType       msgType
     * @throws URISyntaxException URISyntaxException
     * @throws IOException        IOException
     */
    public void sendMessage(final MessageEntity messageEntity, final String msgType)
            throws URISyntaxException, IOException {
        if (StringConstants.SMS_TYPE_1.equals(smsType)) {
            sendMessageType1(messageEntity);
        } else if (StringConstants.SMS_TYPE_2.equals(smsType)) {
            sendMessageType2(messageEntity);
        } else if (StringConstants.SMS_TYPE_3.equals(smsType)) {
            sendMessageType3(messageEntity, msgType);
        }
    }

    /**
     * Method to send otp message in post.
     * @param entity1 entity1
     * @throws URISyntaxException URISyntaxException
     */
    private void sendMessageType1(final MessageEntity entity1) throws URISyntaxException {
        var restTemplate = new RestTemplate(getClientHttpRequestFactory());
        entity1.setFrom(smsParam3);
        entity1.setTo(smsParam4 + entity1.getMobileNo());
        var smsLink = new URI(smsURL);
        var smsHeader = new HttpHeaders();
        smsHeader.setContentType(MediaType.APPLICATION_JSON);
        var smsAuthStr = smsParam1 + ":" + smsParam2;
        var smsBasicAuth = Base64.getEncoder().encode(smsAuthStr.getBytes());
        smsHeader.set("Authorization", "Basic " + new String(smsBasicAuth, StandardCharsets.UTF_8));
        var request = new HttpEntity<>(entity1, smsHeader);
        ResponseEntity<?> response = restTemplate.exchange(smsLink, HttpMethod.POST, request, MessageEntity.class);
        entity1.setDeliveryStatus(response.getStatusCode().toString());
        if (response.getBody() != null) {
            var entity = (MessageEntity) response.getBody();
            if (entity != null) {
                entity1.setMessage(entity.getId());
            }
        }
    }

    /**
     * This method is used to configure the http timeout.
     * @return ClientHttpRequestFactory
     */
    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        var smsTimeout = 0;
        var smsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        smsClientHttpRequestFactory.setConnectTimeout(smsTimeout);
        return smsClientHttpRequestFactory;
    }

    /**
     * Method to send message in http url.
     * @param messageEntity messageEntity
     * @throws IOException exception
     */
    private void sendMessageType2(final MessageEntity messageEntity) throws IOException {
        var requestUrl = smsURL + checkParam(smsParam1) + checkParam(smsParam2) + checkParam(smsParam3)
                + checkParam(smsParam4) + checkParam(smsParam5) + recipient
                + checkParam(MessageConstants.DIAL_CODE + messageEntity.getMobileNo()) + message
                + URLEncoder.encode(messageEntity.getMsg(), MessageConstants.UNICODE);
        var url = new URL(requestUrl);
        var proxy = new Proxy(Proxy.Type.valueOf(proxyType), new InetSocketAddress(proxyUrl, proxyPort));
        HttpURLConnection uc;
        if (Boolean.TRUE.equals(proxyEnable)) {
            uc = (HttpURLConnection) url.openConnection(proxy);
        } else {
            uc = (HttpURLConnection) url.openConnection();
        }
        readMessageResponse(messageEntity, uc);
    }

    /**
     * Method to concatenate the param value.
     * @param param param
     * @return param value
     */
    private String checkParam(final String param) {
        if (!param.isEmpty()) {
            return param + "&";
        }
        return param;
    }

    /**
     * Method to send otp message in http url.
     * @param entity1 entity1
     * @param msgType msgType
     * @throws IOException IOException
     */
    private void sendMessageType3(final MessageEntity entity1, final String msgType) throws IOException {
        Map<String, String> map = getTemplate();
        var requestUrl = smsURL + StringConstants.FORWARD_SLASH + smsParam2 + StringConstants.FORWARD_SLASH
                + MessageConstants.DIAL_CODE + entity1.getMobileNo() + StringConstants.FORWARD_SLASH
                + URLEncoder.encode(entity1.getMsg(), MessageConstants.UNICODE) + StringConstants.FORWARD_SLASH
                + checkParam(smsParam3) + smsParam4 + map.get(msgType);
        var proxy = new Proxy(Proxy.Type.valueOf(proxyType), new InetSocketAddress(proxyUrl, proxyPort));
        HttpURLConnection uc;
        var req = new URL(requestUrl);
        if (Boolean.TRUE.equals(proxyEnable)) {
            uc = (HttpURLConnection) req.openConnection(proxy);
        } else {
            uc = (HttpURLConnection) req.openConnection();
        }
        uc.setRequestProperty(MessageConstants.NF_CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        uc.setRequestProperty(MessageConstants.NF_API_KEY, smsParam1);
        readMessageResponse(entity1, uc);
    }

    /**
     * Method to parse the http response.
     * @param entity entity
     * @param uc     uc
     * @throws IOException IOException
     */
    private void readMessageResponse(final MessageEntity entity, final HttpURLConnection uc) throws IOException {
        InputStream inputStream;
        var resp = uc.getResponseCode();
        if (MessageConstants.INT_200 <= resp && resp <= MessageConstants.INT_299) {
            inputStream = uc.getInputStream();
        } else {
            inputStream = uc.getErrorStream();
        }
        var in = new BufferedReader(new InputStreamReader(inputStream));
        var response = new StringBuilder();
        String currentLine;
        while ((currentLine = in.readLine()) != null) {
            response.append(currentLine);
        }
        entity.setMessage(response.toString());
        entity.setDeliveryStatus(String.valueOf(resp));
        in.close();
        uc.disconnect();
    }

    /**
     * Method to get process map.
     * @return process map
     */
    private Map<String, String> getTemplate() {
        Map<String, String> map = new LinkedHashMap<>();
        String[] processArr = smsParam5.split(",");
        for (String process : processArr) {
            String[] str = process.split(":");
            map.put(str[0], str[1]);
        }
        return map;
    }
}
