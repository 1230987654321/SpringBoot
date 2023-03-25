package com.example.admin.common;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 系统错误类型, 自定义枚举
 * @create 2023-02-13 16:11
 **/
public enum ResponseCodeEnum {
    //系统基本码
    SUCCESS(200, "请求成功"),
    FAILED(400, "请求失败"),
    NOT_FOUND(404, "接口不存在"),
    SERVER_ERROR(500, "服务器内部出错"),
    PARAM_INVALID(-1, "参数不合法"),
    REQUEST_TYPE_ERROR(501, "请求类型错误"),
    UPLOAD_ERROR(-10, "上传异常"),
    MY_BATIS_SYSTEM_ERROR(-11, "MyBatisSystem问题"),
    STRING_INDEX_OUT_OF_BOUNDS_ERROR(-12, "字符串越界异常"),
    ARRAY_INDEX_OUT_OF_BOUNDS_ERROR(-13, "数组越界异常"),
    SQL_ERROR(-14, "SQL异常"),
    SIGN_ERROR(-15, "无效签名"),
    TOKEN_ERROR(-16, "TOKEN过期"),
    ALGORITHM_ERROR(-17, "TOKEN算法不一致"),
    TOKEN_NULL(-18, "TOKEN不存在"),
    AUTHENTICATION_EXCEPTION(-19, "身份验证异常"),
    UNAUTHORIZED_EXCEPTION(-20, "权限不足"),
    //自定义状态码
    NOT_EXIST(-2, "用户不存在"),
    LOGIN_FAILED(-3, "登陆失败,用户名或密码错误"),
    ACCOUNT_DISABLED(-4, "账号已禁用"),
    ACCOUNT_DELETED(-5, "账户已删除"),
    NOT_LOGIN(-6, "NOT_LOGIN");
    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;

    ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
