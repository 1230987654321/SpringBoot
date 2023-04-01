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
    // 40 代码
    FAILED(400, "请求失败"),

    //自定义状态码
    NOT_EXIST(-2, "用户不存在"),
    LOGIN_FAILED(-3, "登陆失败,用户名或密码错误"),
    ACCOUNT_DISABLED(-4, "账号已禁用");
    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;

    /**
     * 构造函数
     *
     * @param code    Integer
     * @param message String
     */
    ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取状态码
     *
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取描述
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }
}
