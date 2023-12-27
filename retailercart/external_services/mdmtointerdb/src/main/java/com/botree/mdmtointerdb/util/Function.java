package com.botree.mdmtointerdb.util;

import com.botree.mdmtointerdb.exception.ServiceException;

import javax.xml.bind.JAXBContext;
import java.io.StringReader;

/**
 * This class is used to convert json to POJO.
 * @author vinodkumar.a
 */
public final class Function {

    /**
     * Default constructor.
     */
    private Function() {

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
            var reader = new StringReader(xml);
            var jaxbContext = JAXBContext.newInstance(type);
            var unmarshaller = jaxbContext.createUnmarshaller();
            return unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
