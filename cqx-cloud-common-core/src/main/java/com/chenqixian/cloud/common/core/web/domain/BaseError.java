package com.chenqixian.cloud.common.core.web.domain;


public interface BaseError {
    int getCode();

    ErrorType getType();

    String getMessage();
}

