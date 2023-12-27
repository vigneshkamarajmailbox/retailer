package com.botree.sfaweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * SessionExpiredException class for session expired.
 * @author vinodkumar.a
 */
@ResponseStatus(HttpStatus.GONE)
public class SessionExpiredException extends RuntimeException {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor method.
     */
    public SessionExpiredException() {
        super();
    }

    /**
     * Constructor method.
     * @param message - error message
     */
    public SessionExpiredException(final String message) {
        super(message);
    }

}
