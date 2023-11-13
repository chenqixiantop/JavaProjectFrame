package com.chenqixian.cloud.common.core.web.exception;

/**
 * @author 53486
 */
public class PreAuthorizeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PreAuthorizeException() {
    }

    public PreAuthorizeException(String message) {
        super(message);
    }
}
