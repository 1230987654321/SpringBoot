package com.example.admin.config.response;

import com.example.admin.config.enums.ResponseCodeEnum;
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
 * @create 2023/3/22 13:16
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
        //如果是字符类型,输出json字符串
        if(o instanceof String){
            return objectMapper.writeValueAsString(GlobalResponse.success(o));
        }
        //如果已经被异常捕获,返回的就是BaseResponse对象,不用再次封装了
        if(o instanceof GlobalResponse){
            return o;
        }
        //如果返回Boolean,并且值为false,返回失败提醒
        if(o instanceof Boolean ){
            if(!((Boolean) o)){
                return GlobalResponse.fail(ResponseCodeEnum.FAILED);
            }
        }
        return GlobalResponse.success(o);
    }
}
