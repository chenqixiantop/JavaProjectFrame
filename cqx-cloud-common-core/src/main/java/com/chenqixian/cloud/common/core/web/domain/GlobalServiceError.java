package com.chenqixian.cloud.common.core.web.domain;

public enum GlobalServiceError implements BaseError{
    NAME_EXIST(ErrorType.WARN, 1001, "名称已存在，请重新录入"),
    OBJECT_NOT_NULL(ErrorType.WARN, 1002, "传入的对象不能为空"),
    ID_NOT_NULL(ErrorType.WARN, 1003, "传入的ID不能为空"),
    VALUE_NOT_NULL(ErrorType.WARN, 1004, "传入的值不能为空"),
    CONFIGURATION_ERROR(ErrorType.WARN, 1005, "配置错误！"),
    ADD_OBJECT_NOT_NULL(ErrorType.WARN, 1006, "添加对象不能为空"),
    UPDATE_OBJECT_NOT_NULL(ErrorType.WARN, 1007, "修改对象不能为空"),
    USER_NEED_LOGIN(ErrorType.WARN, 11001, "用户未登录，请登陆后进行访问"),
    USER_MAX_LOGIN(ErrorType.WARN, 11002, "该用户已在其它地方登录"),
    USER_LOGIN_TIMEOUT(ErrorType.WARN, 11003, "长时间未操作，自动退出"),
    USER_DISABLED(ErrorType.WARN, 11004, "用户被禁11005用"),
    USER_LOCKED(ErrorType.WARN, 11005, "用户被锁定"),
    USER_PASSWORD_ERROR(ErrorType.WARN, 11006, "用户名或密码错误"),
    USER_PASSWORD_EXPIRED(ErrorType.WARN, 11007, "用户密码过期"),
    USER_ACCOUNT_EXPIRED(ErrorType.WARN, 11008, "用户账号过期"),
    USER_NOT_EXIST(ErrorType.WARN, 11009, "没有该用户"),
    USER_LOGIN_FAIL(ErrorType.WARN, 11010, "用户登录失败"),
    VERIFY_CODE_ERROR(ErrorType.WARN, 11011, "验证码错误"),
    USER_IS_EXIST(ErrorType.WARN, 11012, "用户已存在"),
    NO_AUTHENTICATION(ErrorType.WARN, 1003006, "无权访问");

    private int code;
    private String message;
    private ErrorType errorType;

    private GlobalServiceError(ErrorType errorType, int code, String message) {
        this.message = message;
        this.code = code;
        this.errorType = errorType;
    }

    @Override
    public ErrorType getType() {
        return this.errorType;
    }

    @Override
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
}
