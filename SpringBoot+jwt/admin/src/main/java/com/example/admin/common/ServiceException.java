package com.example.admin.common;

/**
 * 自定义业务异常
 */
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String Message;

    public ServiceException(ResponseCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        System.out.println(this.code);

        this.Message = codeEnum.getMessage();
        System.out.println(this.Message);
    }

    public ServiceException(Integer code, String errorMsg) {
        this.code = code;
        this.Message = errorMsg;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String errorMsg) {
        this.Message = errorMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
