package com.chenqixian.cloud.common.core.web.exception;

import com.chenqixian.cloud.common.core.web.domain.BaseError;
import com.chenqixian.cloud.common.core.web.domain.ErrorDetail;
import com.chenqixian.cloud.common.core.web.domain.ErrorType;
import com.chenqixian.cloud.common.core.web.domain.GlobalError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 56263647416715478L;
    private Logger logger;
    int code;
    String message;
    ErrorType errorType;
    List<ErrorDetail> errorDetails;

    public BusinessException(String message) {
        super(message);
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.code = GlobalError.INTERNAL_SERVER_ERROR.getCode();
        this.message = message;
        this.errorType = ErrorType.ERROR;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.code = GlobalError.INTERNAL_SERVER_ERROR.getCode();
        this.message = message;
        this.errorType = ErrorType.ERROR;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.code = code;
        this.message = message;
        this.errorType = ErrorType.ERROR;
    }

    public BusinessException(int code, String message, ErrorType errorType) {
        super(message);
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.code = code;
        this.message = message;
        this.errorType = errorType;
    }

    public BusinessException(int code, String message, ErrorType errorType, Throwable cause) {
        super(message, cause);
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.code = code;
        this.message = message;
        this.errorType = errorType;
    }

    public BusinessException(BaseError baseError, List<ErrorDetail> errorDetails) {
        this(baseError.getCode(), baseError.getMessage(), baseError.getType(), errorDetails);
    }

    public BusinessException(BaseError baseError) {
        this(baseError.getCode(), baseError.getMessage(), baseError.getType(), Collections.emptyList());
    }

    public BusinessException(int code, String message, ErrorType errorType, List<ErrorDetail> errorDetails) {
        super(message);
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.code = code;
        this.message = message;
        this.errorType = errorType;
        this.errorType = ErrorType.ERROR;
        this.errorDetails = errorDetails;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public List<ErrorDetail> getErrorDetails() {
        return this.errorDetails;
    }

    public void setErrorDetails(List<ErrorDetail> errorDetails) {
        this.errorDetails = errorDetails;
    }
}
