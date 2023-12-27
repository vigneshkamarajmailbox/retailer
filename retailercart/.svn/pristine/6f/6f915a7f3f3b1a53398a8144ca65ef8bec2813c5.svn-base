package com.botree.interdb.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This package will contain controller classes.
 * @author vinodkumar.a
 */
public class MutableHttpServletRequest extends HttpServletRequestWrapper {

    /**
     * Constructor.
     */
    private final Map<String, String> customHeaders;

    /**
     * Constructor.
     * @param request login info
     */
    public MutableHttpServletRequest(final HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }

    /**
     * put header.
     * @param name  login info
     * @param value login info
     */
    public void putHeader(final String name, final String value) {
        this.customHeaders.put(name, value);
    }

    /**
     * Get header value.
     * @param name login info
     * @return returns header value
     */
    @Override
    public String getHeader(final String name) {
        // check the custom headers first
        var headerValue = customHeaders.get(name);

        if (headerValue != null) {
            return headerValue;
        }
        // else return from into the original wrapped object
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    /**
     * Get all header values.
     * @return list of header values
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        // create a set of the custom header names
        Set<String> set = new HashSet<>(customHeaders.keySet());

        // now add the headers from the wrapped request object
        @SuppressWarnings("unchecked")
        var e = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (e.hasMoreElements()) {
            // add the names of the request headers into the list
            var n = e.nextElement();
            set.add(n);
        }

        // create an enumeration from the set and return
        return Collections.enumeration(set);
    }
}
