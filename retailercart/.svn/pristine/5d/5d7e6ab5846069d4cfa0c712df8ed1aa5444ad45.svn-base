package com.botree.interdb.service;

import com.botree.common.constants.StringConstants;
import com.botree.interdbentity.model.UploadProcess;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * InterDBWebServiceController controller to get all the request and redirect to
 * service.
 * @author vinodkumar.a
 */
@RestController
@RequestMapping(value = "/interdb/controller")
public class InterDBWebServiceController {

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(InterDBWebServiceController.class);

    /** service. */
    private final InterDBService service;

    /**
     * Constructor Method.
     * @param serviceIn serviceIn
     */
    public InterDBWebServiceController(final InterDBService serviceIn) {
        this.service = serviceIn;
    }

    /**
     * Method(save) is used to save the uploaded data.
     * @param data upload data
     * @return status
     */
    @PostMapping("/save/")
    public final String save(@RequestBody final UploadProcess data) {
        try {
            var mapper = new ObjectMapper();
            var clazz = Class.forName(data.getEntity());
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JavaType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            List<?> readValue;
            if (data.getJsonArr() != null && data.getJsonArr().length > 0) {
                readValue = mapper.readValue(new String(data.getJsonArr()), collectionType);
            } else {
                readValue = mapper.readValue(data.getJsonStr(), collectionType);
            }
            service.save(readValue, data.getEntity());
            return StringConstants.VALIDATIONSUCCESS;
        } catch (Exception e) {
            LOG.error("exception in save method : ", e);
            return StringConstants.VALIDATIONFAILURE;
        }
    }

}
