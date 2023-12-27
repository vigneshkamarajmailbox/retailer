package com.botree.interdb.authentication;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This package will contain controller classes.
 * @author vinodkumar.a
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter("/*")
public class SimpleCorsFilter implements Filter {

    /**
     * Get header value.
     */
    public SimpleCorsFilter() {
        //Init cros filter
    }

    /**
     * Get header value.
     * @param req   request object
     * @param res   response object
     * @param chain filterchain
     */
    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        var response = (HttpServletResponse) res;
        var request = (HttpServletRequest) req;

        var mutableRequest = new MutableHttpServletRequest(request);

        if (mutableRequest.getRequestURI().equals("/oauth/token")) {
            mutableRequest.putHeader("Content-Type", "application/x-www-form-urlencoded");
            mutableRequest.putHeader("Authorization", "Basic U2FtcGxlQ2xpZW50SWQ6c2VjcmV0");
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");

        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(mutableRequest, res);
        }
    }

    /**
     * class destructor.
     */
    @Override
    public void destroy() {
        // Destroy cros filter
    }

    /**
     * Initialize filter config.
     * @param config filtered config
     */
    @Override
    public void init(final FilterConfig config) throws ServletException {
        // Initializing cros filter
    }
}
