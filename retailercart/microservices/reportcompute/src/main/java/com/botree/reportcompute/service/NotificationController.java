package com.botree.reportcompute.service;

import com.botree.common.constants.MessageConstants;
import com.botree.common.constants.StringConstants;
import com.botree.interdbentity.model.NotificationEntity;
import com.botree.reportcompute.model.HeaderRequestInterceptor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Class to control the notification messages.
 * @author vinodkumar.a
 */
@Component
public class NotificationController {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(NotificationController.class);

    /** serverKey. */
    @Value("${firebase.server.key}")
    private String serverKey;

    /** apiUrl. */
    @Value("${firebase.api.url}")
    private String apiUrl;

    /**
     * Method to send notification.
     * @param topic topic
     * @param data  data
     * @return response
     */
    public final ResponseEntity<String> send(final String topic, final NotificationEntity data) {
        var model = new JSONObject();
        model.put(MessageConstants.NF_ID, data.getNotificationId());
        model.put(MessageConstants.NF_TITLE, data.getSubject());
        model.put(MessageConstants.NF_MESSAGE, data.getMessage());
        model.put(MessageConstants.NF_DETAIL, data.getMessageDetail());
        model.put(MessageConstants.NF_FILENAME, data.getFileName());

        var body = new JSONObject();
        body.put(MessageConstants.NF_TO, topic);
        if (StringConstants.CONST_YES.equalsIgnoreCase(data.getImportant())) {
            body.put(MessageConstants.NF_PRIORITY, MessageConstants.NF_HIGH);
        }
        var notification = new JSONObject();
        notification.put(MessageConstants.NF_TITLE, data.getSubject());
        notification.put(MessageConstants.NF_BODY, model);
        body.put(MessageConstants.NF_DATA, notification);
        var request = new HttpEntity<>(body.toString());
        LOG.info(request.getBody());

        var pushNotification = send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            var firebaseResponse = pushNotification.get();
            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("notification send exception : {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }

    /**
     * Method to send the message.
     * @param entity entity
     * @return response
     */
    @Async
    public CompletableFuture<String> send(final HttpEntity<String> entity) {
        var restTemplate = new RestTemplate();
        var interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new HeaderRequestInterceptor(MessageConstants.NF_AUTHORIZATION,
                MessageConstants.NF_KEY + serverKey));
        interceptors.add(new HeaderRequestInterceptor(MessageConstants.NF_CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE));
        restTemplate.setInterceptors(interceptors);
        var firebaseResponse = restTemplate.postForObject(apiUrl, entity, String.class);
        return CompletableFuture.completedFuture(firebaseResponse);
    }

}
