package com.botree.rapp.masterengine.util;

import com.botree.rapp.masterengine.exception.ServiceException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 * This class is used to convert XML to POJO and vise-versa.
 * @author vinodkumar.a
 */
public final class Function {


    /**
     * Default constructor.
     */
    private Function() {
        // Default constructor
    }

    /**
     * This method is used to convert XML to POJO.
     * @param xml  XML string
     * @param type Object type
     * @param <T>  Class Type
     * @return POJO
     */
    public static <T> Object fromXML(final String xml, final Class<T> type) {
        try {
            StringReader reader = new StringReader(xml);
            JAXBContext jaxbContext = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
