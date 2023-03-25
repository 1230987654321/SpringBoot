package com.example.admin.config;

import com.example.admin.common.ResponseCodeEnum;
import com.example.admin.common.ServiceException;
import com.example.admin.common.GlobalResponse;
import io.lettuce.core.RedisCommandExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 贲玉柱
 * @program workspace
 * @description 统一异常处理
 * @create 2023-02-13 16:11
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常 统一处理
     *
     * @param e ServiceException
     * @return GlobalResponse<T>
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public GlobalResponse<String> ServiceExceptionHandler(ServiceException e) {
        log.error(e.getMessage(), e);
        return GlobalResponse.fail(e.getCode(), e.getMessage());
    }

    /**
     * 请求类型错误, 统一处理
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return GlobalResponse<T>
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public GlobalResponse<String> NotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error(ResponseCodeEnum.REQUEST_TYPE_ERROR + ":" + e.getMessage(), e);
        return GlobalResponse.fail(ResponseCodeEnum.REQUEST_TYPE_ERROR, e.getMessage());
    }

    /**
     * mybatis-plus问题(数据库相关问题,例如数据库配置错误)
     *
     * @param e MyBatisSystemException
     * @return GlobalResponse<T>
     */
    @ExceptionHandler(value = MyBatisSystemException.class)
    @ResponseBody
    public GlobalResponse<String> MyBatisSystemExceptionHandler(MyBatisSystemException e) {
        log.error(ResponseCodeEnum.MY_BATIS_SYSTEM_ERROR + ":" + e.getMessage(), e);
        return GlobalResponse.fail(ResponseCodeEnum.MY_BATIS_SYSTEM_ERROR, e.getMessage());
    }

    /**
     * SQL异常
     *
     * @param e SQLIntegrityConstraintViolationException
     * @return GlobalResponse<T>
     */
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public GlobalResponse<String> SQLIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e) {
        log.error(ResponseCodeEnum.SQL_ERROR + ":" + e.getMessage(), e);
        return GlobalResponse.fail(ResponseCodeEnum.SQL_ERROR, e.getMessage());
    }

    /**
     * 权限异常
     *
     * @param e UnauthorizedException
     * @return GlobalResponse<T>
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public GlobalResponse<String> UnauthorizedExceptionHandler(UnauthorizedException e) {
        log.error(ResponseCodeEnum.UNAUTHORIZED_EXCEPTION + ":" + e.getMessage(), e);
        return GlobalResponse.fail(ResponseCodeEnum.UNAUTHORIZED_EXCEPTION);
    }

    /**
     * Redis 链接异常
     *
     * @param e RedisCommandExecutionException
     * @return GlobalResponse<T>
     */
    @ExceptionHandler(value = RedisCommandExecutionException.class)
    @ResponseBody
    public GlobalResponse<String> RedisCommandExecutionExceptionHandler(RedisCommandExecutionException e) {
        log.error(ResponseCodeEnum.UNAUTHORIZED_EXCEPTION + ":" + e.getMessage(), e);
        return GlobalResponse.fail(ResponseCodeEnum.REDIS_COMMAND_EXECUTION_EXCEPTION);
    }

    /**
     * 空指针异常
     *
     * @param e NullPointerException
     * @return GlobalResponse<T>
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public GlobalResponse<String> handleMyException(NullPointerException e) {
        log.error(ResponseCodeEnum.NULL_POINTER_EXECUTION + ":" + e.getMessage(), e);
        return GlobalResponse.fail(ResponseCodeEnum.NULL_POINTER_EXECUTION, e.getMessage());
    }

    /**
     * 其他异常
     *
     * @param e Exception
     * @return GlobalResponse<T>
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public GlobalResponse<String> exception(Exception e) {
        log.error(e.getMessage(), e);
        if (e instanceof NoHandlerFoundException) { // 接口不存在
            return GlobalResponse.fail(ResponseCodeEnum.NOT_FOUND);
        } else if (e instanceof MethodArgumentTypeMismatchException) { // 请求参数问题(应该Integer,实为String)
            return GlobalResponse.fail(ResponseCodeEnum.PARAM_INVALID, e.getMessage());
        } else if (e instanceof MultipartException) { // 上传问题 (文件超过默认大小)
            return GlobalResponse.fail(ResponseCodeEnum.UPLOAD_ERROR, e.getMessage());
        } else if (e instanceof StringIndexOutOfBoundsException) { // 字符串越界异常(例如上传文件没有后缀名)
            return GlobalResponse.fail(ResponseCodeEnum.STRING_INDEX_OUT_OF_BOUNDS_ERROR, e.getMessage());
        } else if (e instanceof ArrayIndexOutOfBoundsException) { // 数组下标越界异常(例如数组长度3,调用数组[4])
            return GlobalResponse.fail(ResponseCodeEnum.ARRAY_INDEX_OUT_OF_BOUNDS_ERROR, e.getMessage());
        } else {
            return GlobalResponse.fail(ResponseCodeEnum.SERVER_ERROR);
        }
    }
}