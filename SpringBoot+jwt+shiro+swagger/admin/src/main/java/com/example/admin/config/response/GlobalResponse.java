package com.example.admin.config.response;

import com.example.admin.config.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 统一返回对象
 * @create 2023/3/21 16:14
 **/
@Data
public class GlobalResponse<T> {
    private T data; // 给客户端返回数据
    private Integer code; // 状态代码.
    private String message; // 描述状态的消息
    private Long timestamp; // 创建响应的时间.

    /**
     * @param code    响应代码
     * @param message 响应消息
     */
    public GlobalResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * @param code    响应代码
     * @param message 响应消息
     * @param data    响应数据
     */
    public GlobalResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 此函数返回具有状态成功和提供的数据的全局响应对象
     *
     * @param data 响应数据.
     * @param <T>  响应数据的类型
     * @return GlobalResponse
     */
    public static <T> GlobalResponse<T> success(T data) {
        return new GlobalResponse<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 此函数返回具有状态成功和提供的数据的全局响应对象
     *
     * @param code    响应代码
     * @param message 响应消息
     * @param data    响应数据
     * @param <T>     响应数据的类型
     * @return GlobalResponse
     */
    public static <T> GlobalResponse<T> success(int code, String message, T data) {
        return new GlobalResponse<>(code, message, data);
    }

    /**
     * 此函数返回具有状态成功和提供的数据的全局响应对象
     *
     * @param responseCodeEnum 响应代码枚举.
     * @param data             响应数据.
     * @return GlobalResponse
     */
    public static <T> GlobalResponse<T> success(ResponseCodeEnum responseCodeEnum, T data) {
        return new GlobalResponse<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), data);
    }

    /**
     * 此函数返回具有状态失败的全局响应对象
     *
     * @param code    响应代码.
     * @param message 响应消息.
     * @return GlobalResponse
     */
    public static <T> GlobalResponse<T> fail(int code, String message) {
        return new GlobalResponse<>(code, message);
    }

    /**
     * 此函数返回具有状态失败的全局响应对象
     *
     * @param responseCodeEnum 响应代码枚举
     * @return GlobalResponse
     */
    public static <T> GlobalResponse<T> fail(ResponseCodeEnum responseCodeEnum) {
        return new GlobalResponse<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage());
    }

    /**
     * 此函数返回具有状态失败的全局响应对象
     *
     * @param responseCodeEnum 响应代码枚举
     * @param message          响应消息
     * @param <T>              响应数据的类型
     * @return GlobalResponse
     */
    public static <T> GlobalResponse<T> fail(ResponseCodeEnum responseCodeEnum, String message) {
        return new GlobalResponse<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage() + ":" + message);
    }
}
