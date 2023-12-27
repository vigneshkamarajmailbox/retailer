package com.botree.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is a custom exception used for all modules.
 * @author Naveen Kumar R.(Emp ID : 1261)
 * Copyright © 2023 to present Botree Software. All rights reserved.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class CommonExceptionHandler extends RuntimeException {

    /**
     * Constructor method.
     */
    public CommonExceptionHandler() {
        super();
    }

    /**
     * Constructor method.
     * @param message - error message
     */
    public CommonExceptionHandler(final String message) {
        super(message);
    }

    /**
     * Constructor method.
     * @param throwable - exception object
     */
    public CommonExceptionHandler(final Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructor method.
     * @param message   - error message
     * @param throwable - exception object
     */
    public CommonExceptionHandler(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
