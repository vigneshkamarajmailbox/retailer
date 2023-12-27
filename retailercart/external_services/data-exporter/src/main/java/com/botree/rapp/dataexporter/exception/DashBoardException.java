package com.botree.rapp.dataexporter.exception;

/**
 * This is a custom exception used for all modules.
 * @author vinodkumar.a
 */
public class DashBoardException extends RuntimeException {

    /**
     * Constructor method.
     */
    public DashBoardException() {
        super();
    }

    /**
     * Constructor method.
     * @param message - error message
     */
    public DashBoardException(final String message) {
        super(message);
    }

    /**
     * Constructor method.
     * @param throwable - exception object
     */
    public DashBoardException(final Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructor method.
     * @param message   - error message
     * @param throwable - exception object
     */
    public DashBoardException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
