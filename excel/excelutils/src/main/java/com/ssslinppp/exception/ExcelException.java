package com.ssslinppp.exception;

/**
 * To change this template use File | Settings | File Templates.
 */
public class ExcelException extends RuntimeException {
    public ExcelException() {
        super();
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }
}
