package com.chenqixian.cloud.common.core.web.domain;

/**
 * @author 53486
 */

public enum GlobalError implements BaseError {
    OK(ErrorType.INFO, 200, "操作成功"),
    NOT_FOUND(ErrorType.WARN, 404, ""),
    UNAUTHORIZED(ErrorType.WARN, 401, "未通过权限检查"),
    FORBIDDEN(ErrorType.WARN, 402, ""),
    METHOD_NOT_ALLOWED(ErrorType.WARN, 405, "该资源暂未开放"),
    NOT_ACCEPTABLE(ErrorType.WARN, 405, "资源只接受JSON格式数据"),
    GONE(ErrorType.WARN, 500, "访问的数据失效"),
    INVALID_REQUEST(ErrorType.WARN, 422, "请求类型不被允许（POST/PUT/PATCH/GET/DELETE ）"),
    UNPROCESABLE_ENTITY(ErrorType.WARN, 422, "请求格式不满足业务需求-参数错误"),
    INTERNAL_SERVER_ERROR(ErrorType.WARN, 500, "请求失败，请稍后再试");

    private int code;
    private String message;
    private ErrorType type;

    private GlobalError(ErrorType errorType, int code, String message) {
        this.message = message;
        this.code = code;
        this.type = errorType;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public ErrorType getType() {
        return this.type;
    }
}

