package com.example.admin.config.response;

import com.example.admin.config.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * @program workspace
 * @description 统一返回对象
 * @author 贲玉柱
 * @create 2023/3/21 16:14
 **/
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

    public GlobalResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
        this.timestamp = System.currentTimeMillis();
    }

    public GlobalResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }


    /**
     * 成功操作,携带自定义状态码,消息和数据
     */
    public static <T> GlobalResponse<T> success(T data){
        return new GlobalResponse<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> GlobalResponse<T> success(int code, String message,T data){
        return new GlobalResponse<>(code, message, data);
    }

    public static <T> GlobalResponse<T> success(ResponseCodeEnum responseCodeEnum,T data){
        return new GlobalResponse<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), data);
    }

    /**
     * 失败操作,携带自定义状态码和消息
     */
    public static <T> GlobalResponse<T> fail(int code, String message) {
        return new GlobalResponse<>(code, message);
    }
    public static <T> GlobalResponse<T> fail(ResponseCodeEnum responseCodeEnum) {
        return new GlobalResponse<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage());
    }
    public static <T> GlobalResponse<T> fail(ResponseCodeEnum responseCodeEnum,String message) {
        return new GlobalResponse<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage()+":"+message );
    }
}
