package com.botree.reportcompute.model;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

/**
 * HeaderRequest interceptor class.
 * @author vinodkumar.a
 */
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    /** headerName. */
    private final String headerName;

    /** headerValue. */
    private final String headerValue;

    /**
     * Constructor.
     * @param headerNameIn  headerName
     * @param headerValueIn headerValue
     */
    public HeaderRequestInterceptor(final String headerNameIn, final String headerValueIn) {
        this.headerName = headerNameIn;
        this.headerValue = headerValueIn;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.http.client.ClientHttpRequestInterceptor#intercept(org.
     * springframework.http.HttpRequest, byte[],
     * org.springframework.http.client.ClientHttpRequestExecution)
     */
    @Override
    public final ClientHttpResponse intercept(final HttpRequest requestIn, final byte[] bodyIn,
                                              final ClientHttpRequestExecution executionIn) throws IOException {
        HttpRequest wrapper = new HttpRequestWrapper(requestIn);
        wrapper.getHeaders().set(headerName, headerValue);
        return executionIn.execute(wrapper, bodyIn);
    }
}
