package com.chenqixian.cloud.common.core.web.domain;

import com.chenqixian.cloud.common.core.web.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Result<T> implements Serializable {

    private boolean success = true;
    private int code = 200;
    private String message = "";
    private ErrorType errorType;
    private T data;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ErrorDetail> errorDetails;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ErrorStack errorStack;

    public Result<T> ok() {
        this.ok(null);
        return this;
    }

    public Result<T> ok(T data) {
        this.success = true;
        this.code = GlobalError.OK.getCode();
        this.message = GlobalError.OK.getMessage();
        this.errorType = GlobalError.OK.getType();
        this.data = data;
        return this;
    }

    public Result<T> error(int code, String message, ErrorType errorType) {
        this.error(code, message, errorType, (List)null, (ErrorStack)null);
        return this;
    }

    public Result<T> error(int code, String message, ErrorType errorType, ErrorStack errorStack) {
        this.error(code, message, errorType, (List)null, errorStack);
        return this;
    }

    public Result<T> error(int code, String message, ErrorType errorType, List<ErrorDetail> errorDetails) {
        this.error(code, message, errorType, errorDetails, (ErrorStack)null);
        return this;
    }

    public Result<T> error(int code, String message, ErrorType errorType, List<ErrorDetail> errorDetails, ErrorStack errorStack) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.errorType = errorType;
        this.data = null;
        this.errorDetails = errorDetails;
        this.errorStack = errorStack;
        return this;
    }

    public Result<T> valid() {
        if (!this.success) {
            throw new BusinessException(this.code, this.message, this.errorType, this.errorDetails);
        } else {
            return this;
        }
    }

}
