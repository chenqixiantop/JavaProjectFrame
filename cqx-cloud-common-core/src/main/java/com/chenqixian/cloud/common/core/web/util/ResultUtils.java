package com.chenqixian.cloud.common.core.web.util;

import com.chenqixian.cloud.common.core.web.domain.*;

import java.util.List;

public final class ResultUtils {
    public static <T> Result<T> ok() {
        return (new Result()).ok();
    }

    public static <T> Result<T> ok(T t) {
        return (new Result()).ok(t);
    }

    public static Result fail(int code, String message, ErrorType errorType, List<ErrorDetail> errorDetails, ErrorStack errorStack) {
        return (new Result()).error(code, message, errorType, errorDetails, errorStack);
    }

    public static Result fail(BaseError baseError, List<ErrorDetail> errorDetails, ErrorStack errorStack) {
        return (new Result()).error(baseError.getCode(), baseError.getMessage(), baseError.getType(), errorDetails, errorStack);
    }

    public static Result fail(BaseError baseError, List<ErrorDetail> errorDetails) {
        return (new Result()).error(baseError.getCode(), baseError.getMessage(), baseError.getType(), errorDetails);
    }

    public static Result fail(BaseError baseError) {
        return (new Result()).error(baseError.getCode(), baseError.getMessage(), baseError.getType());
    }

    private ResultUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
