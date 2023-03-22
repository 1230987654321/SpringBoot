package com.example.admin.config.handler;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.admin.config.response.GlobalResponse;
import com.example.admin.config.enums.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @program workspace
 * @description 全局异常处理控制器
 * @author 贲玉柱
 * @create 2023-02-13 16:11
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public GlobalResponse<String> exception(Exception e){
        log.error(e.getMessage(), e);
        if (e instanceof NoHandlerFoundException) { // 接口不存在
            return GlobalResponse.fail(ResponseCodeEnum.NOT_FOUND);
        } else if (e instanceof HttpRequestMethodNotSupportedException) { // 请求类型错误(例如应为POST,实际请求为GET)
            return GlobalResponse.fail(ResponseCodeEnum.REQUEST_TYPE_ERROR,e.getMessage());
        } else if (e instanceof MethodArgumentTypeMismatchException) { // 请求参数问题(应该Integer,实为String)
            return GlobalResponse.fail(ResponseCodeEnum.PARAM_INVALID,e.getMessage());
        } else if (e instanceof MultipartException) { // 上传问题 (文件超过默认大小)
            return GlobalResponse.fail(ResponseCodeEnum.UPLOAD_ERROR,e.getMessage());
        } else if (e instanceof MyBatisSystemException) { // mybatis-plus问题(数据库相关问题,例如数据库配置错误)
            return GlobalResponse.fail(ResponseCodeEnum.MY_BATIS_SYSTEM_ERROR,e.getMessage());
        } else if (e instanceof StringIndexOutOfBoundsException) { // 字符串越界异常(例如上传文件没有后缀名)
            return GlobalResponse.fail(ResponseCodeEnum.STRING_INDEX_OUT_OF_BOUNDS_ERROR,e.getMessage());
        } else if (e instanceof ArrayIndexOutOfBoundsException) { // 数组下标越界异常(例如数组长度3,调用数组[4])
            return GlobalResponse.fail(ResponseCodeEnum.ARRAY_INDEX_OUT_OF_BOUNDS_ERROR,e.getMessage());
        } else if (e instanceof SQLIntegrityConstraintViolationException){// SQL异常
            return GlobalResponse.fail(ResponseCodeEnum.SQL_ERROR,e.getMessage());
        } else if (e instanceof SignatureVerificationException){// 签名错误
            return GlobalResponse.fail(ResponseCodeEnum.SIGN_ERROR,e.getMessage());
        } else if (e instanceof TokenExpiredException){// TOKEN过期
            return GlobalResponse.fail(ResponseCodeEnum.TOKEN_ERROR);
        } else if (e instanceof AlgorithmMismatchException){// TOKEN算法不一致
            return GlobalResponse.fail(ResponseCodeEnum.ALGORITHM_ERROR);
        } else if (e instanceof JWTDecodeException){// TOKEN不存在
            return GlobalResponse.fail(ResponseCodeEnum.TOKEN_NULL);
        } else if (e instanceof UnknownAccountException) { // 用户不存在
            return GlobalResponse.fail(ResponseCodeEnum.NOT_EXIST);
        } else if (e instanceof IncorrectCredentialsException) { // 登录错误
            return GlobalResponse.fail(ResponseCodeEnum.LOGIN_FAILED);
        } else if (e instanceof DisabledAccountException) { // 账户被禁用
            return GlobalResponse.fail(ResponseCodeEnum.ACCOUNT_DISABLED);
        } else if (e instanceof AuthenticationException) { // 身份验证异常
            return GlobalResponse.fail(ResponseCodeEnum.AUTHENTICATION_EXCEPTION,e.getMessage());
        } else if (e instanceof UnauthorizedException) { // 权限不足
            return GlobalResponse.fail(ResponseCodeEnum.UNAUTHORIZED_EXCEPTION,e.getMessage());
        } else {
            return GlobalResponse.fail(ResponseCodeEnum.SERVER_ERROR);
        }
    }
}
