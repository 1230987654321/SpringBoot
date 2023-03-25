package com.example.admin.config.exception;

import com.example.admin.config.enums.ResponseCodeEnum;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 自定义业务异常
 * @create 2023/3/21 16:11
 **/
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String Message;

    /**
     * 构造函数
     *
     * @param codeEnum
     * @return
     */
    public ServiceException(ResponseCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.Message = codeEnum.getMessage();
    }

    /**
     * 构造函数
     *
     * @param code
     * @param errorMsg
     * @return
     */
    public ServiceException(Integer code, String errorMsg) {
        this.code = code;
        this.Message = errorMsg;
    }

    /**
     * 获取错误信息
     *
     * @return
     */
    public String getMessage() {
        return Message;
    }

    /**
     * 获取错误码
     *
     * @return
     */
    public Integer getCode() {
        return code;
    }

}
