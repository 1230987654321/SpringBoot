package com.example.admin.config.response;

import com.example.admin.config.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * 统一返回对象
 * @param <T>
 */
@Data
public class GlobalResponse<T> {
    /**
     * 返回数据
     */
    private T data;
    /**
     * 状态码,具体状态码参见GlobalResponse.java
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回时间戳
     */
    private Long timestamp;

    public GlobalResponse(){
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功操作,携带自定义状态码,消息和数据
     */
    public static <T> GlobalResponse<T> success(T data){
        GlobalResponse<T> globalResponse = new GlobalResponse<>();
        globalResponse.setCode(ResponseCodeEnum.SUCCESS.getCode());
        globalResponse.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
        globalResponse.setData(data);
        return globalResponse;
    }
    /**
     * 失败操作,携带自定义状态码和消息
     */
    public static <T> GlobalResponse<T> fail(int code, String message) {
        GlobalResponse<T> globalResponse = new GlobalResponse<>();
        globalResponse.setCode(code);
        globalResponse.setMessage(message);
        return globalResponse;
    }
    public static <T> GlobalResponse<T> fail(ResponseCodeEnum responseCodeEnum) {
        GlobalResponse<T> globalResponse = new GlobalResponse<>();
        globalResponse.setCode(responseCodeEnum.getCode());
        globalResponse.setMessage(responseCodeEnum.getMessage());
        return globalResponse;
    }

    public static <T> GlobalResponse<T> fail(ResponseCodeEnum responseCodeEnum,String message) {
        GlobalResponse<T> globalResponse = new GlobalResponse<>();
        globalResponse.setCode(responseCodeEnum.getCode());
        globalResponse.setMessage(responseCodeEnum.getMessage()+":"+message);
        return globalResponse;
    }
}
