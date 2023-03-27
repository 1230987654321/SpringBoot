package com.example.admin.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @program workspace
 * @description 对RestController的body体进行统一返回
 * @author 贲玉柱
 * @create 2023-02-13 16:11
 **/
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    private final ObjectMapper objectMapper;

    public ResponseAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 这个方法表示对于哪些请求要执行beforeBodyWrite，返回true执行，返回false不执行
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 对于返回的对象如果不是最终对象ResponseResult，则选包装一下
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 检查对象是否是字符串
        if (o instanceof String) {
            try {
                // 如果是字符串，尝试将其转换为 JSON 对象
                return objectMapper.writeValueAsString(GlobalResponse.success(o));
            } catch (JsonProcessingException e) {
                // 如果出现错误，则返回原始字符串
                return o;
            }
        }
        // 如果对象是整数，则将其转换为 BaseResponse 对象
        if (o instanceof Integer) {
            // 检查整数是否为错误
            if ((Integer) o == 1) {
                return GlobalResponse.success(o);
            } else {
                return GlobalResponse.fail(ResponseCodeEnum.FAILED);
            }
        }
        // 检查对象是否是布尔值。
        if (o instanceof Boolean) {
            // 检查布尔值是否为错误
            if (!((Boolean) o)) {
                // 返回失败的响应
                return GlobalResponse.fail(ResponseCodeEnum.FAILED);
            }
        }
        // 如果对象是字节数组，则转换为 BaseResponse 对象
        if (o instanceof byte[]) {
            return GlobalResponse.success(o);
        }
        // 如果对象是空，则返回失败的响应
        if (o == null) {
            return GlobalResponse.fail(ResponseCodeEnum.FAILED);
        }
        // 如果已经被异常捕获,返回的就是BaseResponse对象,不用再次封装了
        if (o instanceof GlobalResponse) {
            return o;
        }
        return GlobalResponse.success(o);
    }
}
