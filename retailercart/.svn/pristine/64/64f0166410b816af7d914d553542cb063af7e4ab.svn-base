package com.botree.interdb.authentication;

import com.botree.common.constants.StringConstants;
import com.botree.interdb.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * This package will contain controller classes.
 * @author vinodkumar.a
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    /** loginService. */
    private final LoginService loginService;

    /** LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    /**
     * Constructor Method.
     * @param loginServiceIn loginServiceIn
     */
    public CustomAuthenticationProvider(final LoginService loginServiceIn) {
        this.loginService = loginServiceIn;
    }

    /**
     * custom user validation.
     * @param authentication login info
     * @return Authentication of user
     */
    @Override
    public Authentication authenticate(final Authentication authentication) {
        var name = authentication.getName();
        var password = authentication.getCredentials().toString();
        try {
            var attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            var clientIP = getClientIp(attr.getRequest());
            loginService.login(name, password, clientIP);
            return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
        } catch (Exception e) {
            LOG.error("Login failed", e);
            throw new BadCredentialsException("Bad Credentials");
        }
    }


    /**
     * get requested client ip address.
     * @param request httpservlet object
     * @return returns client ip
     */
    private static String getClientIp(final HttpServletRequest request) {

        var remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader(StringConstants.CONST_X_FORWARDED_FOR);
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    /**
     * Spring security method.
     * @param authentication login info
     * @return boolean value authentication equals to username password token
     */
    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
