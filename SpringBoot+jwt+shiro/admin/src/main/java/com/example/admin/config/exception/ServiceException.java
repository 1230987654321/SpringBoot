package com.example.admin.config.exception;

import com.example.admin.config.enums.ResponseCodeEnum;

/**
 * @program workspace
 * @description 自定义业务异常
 * @author 贲玉柱
 * @create 2023/3/21 16:11
 **/
public class ServiceException extends RuntimeException{

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String Message;

    public ServiceException(ResponseCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.Message = codeEnum.getMessage();
    }

    public ServiceException(Integer code,String errorMsg) {
        this.code = code;
        this.Message = errorMsg;
    }

    public String getMessage() {
        return Message;
    }

    public Integer getCode() {
        return code;
    }

}
