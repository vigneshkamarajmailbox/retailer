package com.botree.rapp.dataexporter.util;

import com.botree.rapp.dataexporter.exception.DashBoardException;

import javax.xml.bind.JAXBContext;
import java.io.StringReader;

/**
 * This class is used to convert XML to POJO and vise-versa.
 * @author vinodkumar.a
 */
public final class Function {

    /** Default constructor. */
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
            var reader = new StringReader(xml);
            var jaxbContext = JAXBContext.newInstance(type);
            var unmarshaller = jaxbContext.createUnmarshaller();
            return unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            throw new DashBoardException(e);
        }
    }
}
