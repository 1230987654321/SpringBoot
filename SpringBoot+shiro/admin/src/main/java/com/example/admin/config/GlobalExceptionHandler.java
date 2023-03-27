package com.example.admin.config;

import com.example.admin.common.GlobalResponse;
import io.lettuce.core.RedisCommandExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.shiro.authz.UnauthorizedException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * 请求类型错误 返回405
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<GlobalResponse<Object>> NotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("请求类型错误:" + e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, "请求类型错误");
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * mybatis-plus问题(数据库相关问题,例如数据库配置错误) 返回500
     *
     * @param e MyBatisSystemException
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ExceptionHandler(value = MyBatisSystemException.class)
    @ResponseBody
    public ResponseEntity<GlobalResponse<Object>> MyBatisSystemExceptionHandler(MyBatisSystemException e) {
        log.error("mybatis-plus问题:" + e.getMessage(), e);
        Throwable cause = e.getCause();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String message;
        if (cause instanceof PersistenceException) {
            message = "访问数据库时出错";
        } else {
            message = "出现内部服务器错误";
        }
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, message);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * SQL异常 返回422
     *
     * @param e SQLIntegrityConstraintViolationException
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<GlobalResponse<Object>> SQLIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e) {
        log.error("SQL异常:" + e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        String message = "由于数据库约束冲突,无法处理该请求.";
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, message);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * 权限异常 返回401
     *
     * @param e UnauthorizedException
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<GlobalResponse<Object>> UnauthorizedExceptionHandler(UnauthorizedException e) {
        log.error("权限不足:" + e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        String message = "权限不足";
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, message);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Redis 链接异常 返回503
     *
     * @param e RedisCommandExecutionException
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ExceptionHandler(value = RedisCommandExecutionException.class)
    @ResponseBody
    public ResponseEntity<GlobalResponse<Object>> RedisCommandExecutionExceptionHandler(RedisCommandExecutionException e) {
        log.error("Redis 异常:" + e.getMessage(), e);
        HttpStatus httpStatus;
        String message;
        Throwable cause = e.getCause();
        if (cause instanceof RedisConnectionFailureException) {
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            message = "服务器当前不可用,请稍后再试";
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            message = "由于Redis命令无效或未知,无法处理该请求.";
        }
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, message);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * 空指针异常 返回422
     *
     * @param e NullPointerException
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResponseEntity<GlobalResponse<Object>> NullPointerExceptionHandler(NullPointerException e) {
        log.error("空指针异常:" + e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        String message = "由于值为null,无法处理该请求.";
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, message);
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * 404异常 返回404
     *
     * @param e NoHandlerFoundException
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<GlobalResponse<Object>> NoHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        log.error("接口或资源不存在:" + e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, "接口或资源不存在");
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * 请求参数问题 返回422
     *
     * @param e MethodArgumentTypeMismatchException
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<GlobalResponse<Object>> MethodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        log.error("请求参数问题:" + e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, "由于参数类型无效,无法处理该请求");
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * 上传问题 返回422
     *
     * @param e MultipartException
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ExceptionHandler(value = MultipartException.class)
    @ResponseBody
    public ResponseEntity<GlobalResponse<Object>> MultipartExceptionHandler(MultipartException e) {
        log.error("上传发生异常:" + e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, "上传发生异常");
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * 字符串越界异常 返回422
     *
     * @param e StringIndexOutOfBoundsException
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ExceptionHandler(value = StringIndexOutOfBoundsException.class)
    @ResponseBody
    public ResponseEntity<GlobalResponse<Object>> StringIndexOutOfBoundsExceptionHandler(StringIndexOutOfBoundsException e) {
        log.error("字符串越界异常:" + e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, "由于字符串值的索引无效,无法处理该请求.");
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * 数组下标越界异常 返回422
     *
     * @param e ArrayIndexOutOfBoundsException
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ExceptionHandler(value = ArrayIndexOutOfBoundsException.class)
    @ResponseBody
    public ResponseEntity<GlobalResponse<Object>> ArrayIndexOutOfBoundsExceptionHandler(ArrayIndexOutOfBoundsException e) {
        log.error("字符串越界异常:" + e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, "由于数组值的索引无效,无法处理请求.");
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * 其他异常 返回500
     *
     * @param e Exception
     * @return ResponseEntity<GlobalResponse < T>>
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<Object>> exception(Exception e) {
        log.error(e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        GlobalResponse<Object> response = GlobalResponse.fail(httpStatus, "内部服务器错误");
        return new ResponseEntity<>(response, httpStatus);
    }
}