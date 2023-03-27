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
    //自定义状态码
    NOT_EXIST(-2, "用户不存在"),
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
